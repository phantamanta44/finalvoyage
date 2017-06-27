package io.github.phantamanta44.finalvoyage;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import io.github.phantamanta44.finalvoyage.event.DedicatedServerStartListener;
import io.github.phantamanta44.finalvoyage.event.PlayerListener;
import io.github.phantamanta44.finalvoyage.event.ThirstHandler;
import io.github.phantamanta44.finalvoyage.event.WorldListener;
import io.github.phantamanta44.finalvoyage.integration.IntHelper;
import io.github.phantamanta44.finalvoyage.integration.IntMod;
import io.github.phantamanta44.finalvoyage.integration.IntRecipes;
import io.github.phantamanta44.finalvoyage.integration.IntRegistration;
import io.github.phantamanta44.finalvoyage.item.FVItems;
import io.github.phantamanta44.finalvoyage.item.ItemDrink;
import io.github.phantamanta44.finalvoyage.item.ItemFood;
import io.github.phantamanta44.finalvoyage.item.base.IThirstMutator;
import io.github.phantamanta44.finalvoyage.recipe.RecipeManager;
import io.github.phantamanta44.finalvoyage.tile.TileInterdictionMatrix;
import io.github.phantamanta44.finalvoyage.util.BuildableTagCompound;
import io.github.phantamanta44.finalvoyage.world.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.server.management.PlayerList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.*;
import net.minecraft.world.demo.DemoWorldManager;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.MapGenRavine;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CommonProxy {

    public static final DamageSource dmgThirst = new DamageSource("thirst").setDamageBypassesArmor().setDamageIsAbsolute();

    private static final String[] AUTHOR_NAMES = {
            // All these names were created by and are owned by their respective creators and/or owners
            "Dr. Logan Igotta", "Dr. Ari Igotta", "Dr. Tae Takemi", "Dr. Douglas Davenport",
            "Dr. Donald Davenport", "Dr. Heinz Doofenshmirtz", "Dr. Frederick Fronkensteen",
            "Dr. Victor Frankenstein", "Dr. Jeremy Stone", "Dr. Peter Leavitt", "Dr. Mark Hall",
            "Dr. Charles Burton", "Sir Derek Smithers", "Dr. Viola Orsino", "Dr. David Tennant",
            "Dr. John Watson", "Prof. Cuthbert Calculus", "Prof. Decimus Phostle", "Dr. Frank Wolff",
            "Dr. George Claw", "Dr. Elizabeth Wilson", "Dr. Yi Suchong", "Dr. Brigid Tenenbaum",
            "Dr. Sofia Lamb", "Dr. Gordon Freeman", "Dr. Issac Kleiner", "Dr. Eli Vance", "Dr. Mundo",
            "Dr. Strangelove", "Sir Pascal Sauvage", "Dr. Angela Ziegler", "Prof. Joanna Chambers",
            // Randomly generated
            "Dr. Abby Fujioka", "Dr. Annelies Ljungborg", "Dr. Tam Caicedo", "Dr. Shirely Vass",
            "Dr. Kyoko Fore", "Dr. Agustin Wehr", "Dr. Camilla Rizzuto", "Dr. Eloise Vantassel",
            "Dr. Shelba Shanklin", "Dr. Tien Magnusson", "Dr. Isabelle Darwin", "Dr. Kasha Ostlund",
            "Dr. Farrah Cullison", "Dr. Kim Bosak", "Dr. Rogelio Rone", "Dr. Evelin Bouie",
            "Dr. Sandra Sakamoto", "Dr. Terrie McCracken", "Dr. Laine Journey", "Dr. Bo Shipton",
            "Dr. Farah Poulin", "Dr. Patti Laxson", "Dr. Natasha Wheelwright", "Dr. Shirlee Dimaio",
            "Dr. Karlyn Scheidt", "Dr. Jay Pearlman", "Dr. Jacob Javier", "Dr. Jeanetta Plaisted",
            "Dr. Elmira Demby", "Dr. Lilia Rich", "Dr. Paulina Manner", "Dr. Meta Usher",
            "Dr. Felice Lagrange", "Dr. Dagny Tremper", "Dr. Dahlia Lesane", "Dr. Arline Glazier",
            "Dr. Harriett Saylor", "Dr. Danika Wiens", "Dr. Dania Galeano", "Dr. Refugio Spratling",
            "Dr. Adella Gore", "Dr. Florida Esterline", "Dr. Sibyl Cupp", "Dr. Soledad Climer",
            "Dr. Malisa Kelleher", "Dr. Lura Blackman", "Dr. Earleen Longley", "Dr. Nguyet Sherry",
            "Dr. Lenora Motz", "Dr. Merrilee Kingrey", "Dr. Madaline Duda", "Dr. Dell Addie"
    };
    private static final Collection<Class<? extends Biome>> BANNED_BIOMES = Arrays.asList(
            BiomeHills.class, BiomeMushroomIsland.class, BiomeSavannaMutated.class, BiomeTaiga.class);

    protected final Collection<Consumer<MinecraftServer>> serverStartHooks = new LinkedList<>();
    private final Collection<Pair<Predicate<ItemStack>, Float>> thirstRegistry = new LinkedList<>();
    private ThirstHandler thirstHandler;
    private WorldListener worldListener;

    public void onPreInit() {
        addTileMapping(TileInterdictionMatrix.class);
        try {
            Field mtField = Class.forName("minetweaker.mc1102.util.MineTweakerHacks").getDeclaredField("SLOTCRAFTING_PLAYER");
            mtField.setAccessible(true);
            Field modField = Field.class.getDeclaredField("modifiers");
            modField.setAccessible(true);
            modField.setInt(mtField, mtField.getModifiers() & ~Modifier.FINAL);
            mtField.set(null, ReflectionHelper.findField(SlotCrafting.class, "thePlayer", "player", "field_75238_b"));
        } catch (Exception e) {
            FVMod.LOGGER.warn("CraftTweaker hack failed!", e);
        }
    }

    public void onInit() {
        GameRegistry.registerWorldGenerator(new NetherOreGenerator(), 1);
        GameRegistry.registerWorldGenerator(new NetherDirtGenerator(), 1);

        RecipeManager.init();
        IntRecipes.init();

        MinecraftForge.EVENT_BUS.register(new PlayerListener());
        MinecraftForge.EVENT_BUS.register(thirstHandler = new ThirstHandler());
        MinecraftForge.EVENT_BUS.register(worldListener = new WorldListener());
        MinecraftForge.TERRAIN_GEN_BUS.register(worldListener);
        registerServerStartHook();

        setThirstHealed(Items.MILK_BUCKET, 0, 7F);
        setThirstHealed(Items.POTIONITEM, 0, 1.33F);
        setThirstHealed(Items.MUSHROOM_STEW, 0, 9F);
        setThirstHealed(Items.RABBIT_STEW, 0, 9F);
        setThirstHealed(Items.BEETROOT_SOUP, 0, 8F);
        for (int i = 2; i <= 9; i++)
            setThirstHealed(IntHelper.itemStack(IntMod.NAT, "edibles", 1, i), 1F);
        setThirstHealed(IntHelper.itemStack(IntMod.NAT, "edibles", 1, 11), 4F);
        for (int i = 0; i <= 3; i++)
            setThirstHealed(IntHelper.itemStack(IntMod.NAT, "soups", 1, i), 9F);
        for (int i = 4; i <= 8; i++)
            setThirstHealed(IntHelper.itemStack(IntMod.NAT, "soups", 1, i), 7F);
        setThirstHealed(IntHelper.itemStack(IntMod.NAT, "soups", 1, 9), 3F);
        for (int i = 1; i <= 4;i ++)
            setThirstHealed(IntHelper.itemStack(IntMod.TCON, "edible", 1, i), 2F);
    }

    @SuppressWarnings("unchecked")
    public void onPostInit() {
        IntRegistration.init();

        DimensionManager.unregisterDimension(0);
        DimensionManager.registerDimension(0, DimensionType.register("Overworld", "", 0, FVOverworldProvider.class, true));

        DimensionType hellType = DimensionManager.getProviderType(-1);
        FVHellProvider.factory = hellType::createDimension;
        DimensionManager.unregisterDimension(-1);
        DimensionManager.registerDimension(-1, DimensionType.register(
                hellType.name(), hellType.getSuffix(), hellType.getId(), FVHellProvider.class, true));

        DimensionType endType = DimensionManager.getProviderType(1);
        FVEndProvider.factory = endType::createDimension;
        DimensionManager.unregisterDimension(1);
        DimensionManager.registerDimension(1, DimensionType.register(
                endType.name(), endType.getSuffix(), endType.getId(), FVEndProvider.class, false));

        List<Biome.SpawnListEntry> hellSpawns = Biomes.HELL.getSpawnableList(EnumCreatureType.MONSTER);
        hellSpawns.removeIf(e -> e.entityClass == EntityEnderman.class || e.entityClass == EntityMagmaCube.class);
        hellSpawns.add(new Biome.SpawnListEntry(EntityMagmaCube.class, 4, 4, 4));
        hellSpawns.add(new Biome.SpawnListEntry(EntityEnderman.class, 4, 4, 4));

        Predicate<BiomeManager.BiomeEntry> biomeMatcher = b ->
                BANNED_BIOMES.stream().anyMatch(c -> c.isAssignableFrom(b.biome.getBiomeClass()));
        for (BiomeManager.BiomeType type : BiomeManager.BiomeType.values()) {
            BiomeManager.getBiomes(type).stream()
                    .filter(biomeMatcher)
                    .collect(Collectors.toList())
                    .forEach(b -> BiomeManager.removeBiome(type, b));
        }
        IBlockState coarseDirt = Blocks.DIRT.getDefaultState()
                .withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);
        Biome.REGISTRY.forEach(b -> {
            if (b.topBlock.getBlock() == Blocks.GRASS)
                b.topBlock = coarseDirt;
        });

        try {
            IBlockState blood = Block.REGISTRY.getObject(new ResourceLocation(IntMod.TCON.modId, "blood")).getDefaultState();
            IBlockState lava = Blocks.LAVA.getDefaultState();
            Field modField = Field.class.getDeclaredField("modifiers");
            modField.setAccessible(true);

            Field field = ReflectionHelper.findField(Biome.class, "WATER", "field_185372_h");
            field.setAccessible(true);
            modField.set(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(null, blood);

            field = ReflectionHelper.findField(Biome.class, "ICE", "field_185371_g");
            field.setAccessible(true);
            modField.set(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(null, blood);

            field = ReflectionHelper.findField(MapGenCaves.class, "BLK_AIR", "field_186127_b");
            field.setAccessible(true);
            modField.set(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(null, lava);

            field = ReflectionHelper.findField(MapGenRavine.class, "AIR", "field_186136_b");
            field.setAccessible(true);
            modField.set(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(null, lava);
        } catch (Exception e) {
            FVMod.LOGGER.error("World gen hack failed!", e);
        }
    }

    public void onServerAboutToStart(MinecraftServer server) {
        serverStartHooks.forEach(h -> h.accept(server));
    }

    public void addTileMapping(Class<? extends TileEntity> clazz) {
        TileEntity.addMapping(clazz, clazz.getName());
    }

    protected void registerServerStartHook() {
        serverStartHooks.add(new DedicatedServerStartListener());
    }

    public void procureStarterKit(EntityPlayer player) {
        player.inventory.addItemStackToInventory(new ItemStack(FVItems.drink, 32, ItemDrink.Type.WATER_SACK.ordinal()));
        player.inventory.addItemStackToInventory(new ItemStack(FVItems.food, 16, ItemFood.Type.MRE.ordinal()));
        ItemStack nightVision = IntHelper.itemStack(IntMod.RELIQ, "potion", 3, 0);
        nightVision.setTagCompound(new BuildableTagCompound()
                .withByte("hasPotion", 1)
                .withList("effects",
                        new BuildableTagCompound()
                                .withInt("duration", 6000)
                                .withInt("id", 16)
                                .withInt("potency", 0)));
        player.inventory.addItemStackToInventory(nightVision);
        ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
        book.setTagCompound(new BuildableTagCompound()
                .withStr("author", AUTHOR_NAMES[(int)(Math.abs(player.worldObj.getWorldInfo().getSeed()) % AUTHOR_NAMES.length)])
                .withStr("title", "A Quick Note")
                .withList("pages", // TODO Localization?
                        new NBTTagString("{\"text\":\"Hi there.\\n\\nI\\u0027m the lead researcher in charge of the project you\\u0027ve volunteered for. The details are unimportant right now; all that matters is that you made it to the other side of the portal in one piece.\"}"),
                        new NBTTagString("{\"text\":\"The reason I\\u0027m writing this in the first place is that I\\u0027m unsure of how well your memory will hold up under the stress of interdimensional travel. With hope, you\\u0027ll experience no adverse effects whatsoever, but I figured I\\u0027d slip this note in with the\"}"),
                        new NBTTagString("{\"text\":\"rest of your supplies anyways.\\n\\nSo to reiterate upon the procedure, we\\u0027ve sent you to a previously unknown and uncharted place. Somewhere that exists outside our \\\"normal\\\" perception of the universe. We\\u0027re doing this because we need\"}"),
                        new NBTTagString("{\"text\":\"somewhere to escape the evils that plague our own reality. With luck, you no longer remember what I\\u0027m referring to...\\n\\nSince we have no clue what the environment on the other side is like, we\\u0027ve given you a rather large set of supplies to live on until\"}"),
                        new NBTTagString("{\"text\":\"you, with hope, find a way to produce the supplies yourself using the resources there. We\\u0027ve also sent a number of supply caches through preemptively, although the teleportation process may unfortunately have caused them to scatter about a bit. If you see\"}"),
                        new NBTTagString("{\"text\":\"any suspicious glass boxes, be sure to crack \\u0027em open!\\n\\nIt\\u0027s also worth noting that our analysis of gas samples indicates that the atmosphere over there might be a little... foggy. To counteract this, we\\u0027ve developed a formula that, when ingested, gives\"}"),
                        new NBTTagString("{\"text\":\"the drinker the ability to see straight through any mist. We\\u0027ve packed a few vials with you, and we expect you\\u0027ll find a way to synthesize it yourself.\\n\\nThe ultimate goal of this little excursion is the establishment of a base we can use as a starting\"}"),
                        new NBTTagString("{\"text\":\"point for the reconstruction of civilization in the event of a global apocalyptic catastrophe. We\\u0027re not saying one is going to happen, but why take chances?\\n\\nAnyways, we wish you the best of luck. We expect great things to come...\"}")));
        player.inventory.addItemStackToInventory(book);
    }

    public void registerItemModel(Item item, int meta, String name) {
        // NO-OP
    }

    public void setThirstHealed(Predicate<ItemStack> item, float amount) {
        thirstRegistry.add(Pair.of(item, amount));
    }

    public void setThirstHealed(Item item, int meta, float amount) {
        setThirstHealed(s -> s.getItem() == item && s.getMetadata() == meta, amount);
    }

    public void setThirstHealed(ItemStack item, float amount) {
        setThirstHealed(item.getItem(), item.getMetadata(), amount);
    }

    public void setThirstHealed(Item item, float amount) {
        setThirstHealed(s -> s.getItem() == item, amount);
    }

    public float getThirstHealed(ItemStack item) {
        if (item.getItem() instanceof IThirstMutator)
            return ((IThirstMutator)item.getItem()).getThirstOffset(item);
        return thirstRegistry.stream().filter(e -> e.getKey().test(item)).map(Pair::getValue).findAny().orElse(0F);
    }

    public float getThirst(EntityPlayer player) {
        return thirstHandler.propertiesOf(player).getThirst();
    }

    public static EntityPlayerMP createPlayerForUser(PlayerList backing, GameProfile profile) {
        UUID uuid = EntityPlayer.getUUID(profile);
        List<EntityPlayerMP> list = Lists.newArrayList();
        for (int i = 0; i < backing.getPlayerList().size(); ++i) {
            EntityPlayerMP entityplayermp = backing.getPlayerList().get(i);
            if (entityplayermp.getUniqueID().equals(uuid))
                list.add(entityplayermp);
        }
        EntityPlayerMP entityplayermp2 = backing.getPlayerByUUID(profile.getId());
        if (entityplayermp2 != null && !list.contains(entityplayermp2))
            list.add(entityplayermp2);
        for (EntityPlayerMP entityplayermp1 : list)
            entityplayermp1.connection.kickPlayerFromServer("You logged in from another location");
        PlayerInteractionManager playerinteractionmanager;
        MinecraftServer server = backing.getServerInstance();
        if (server.isDemo())
            playerinteractionmanager = new DemoWorldManager(server.worldServerForDimension(-1));
        else
            playerinteractionmanager = new PlayerInteractionManager(server.worldServerForDimension(-1));
        return new EntityPlayerMP(server, server.worldServerForDimension(-1), profile, playerinteractionmanager);
    }

}
