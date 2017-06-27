package io.github.phantamanta44.finalvoyage.block;

import io.github.phantamanta44.finalvoyage.FVMod;
import io.github.phantamanta44.finalvoyage.block.base.BlockModSubs;
import io.github.phantamanta44.finalvoyage.constant.FVConst;
import io.github.phantamanta44.finalvoyage.constant.LangConst;
import io.github.phantamanta44.finalvoyage.item.block.ItemBlockNetherOre;
import io.github.phantamanta44.finalvoyage.util.OreDictHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BlockNetherOre extends BlockModSubs {

    private static int nextIndex = 0;

    public int index;
    public IProperty<Type> propType;

    public BlockNetherOre() {
        super(Material.ROCK, Math.min(Type.values().length - nextIndex * 16, 16));
        ModelLoader.setCustomStateMapper(this, new StateMapper(propType));
        registerBlock(LangConst.BLOCK_NETHER_ORE_NAME + index);
        setHardness(3F);
        setResistance(5F);
    }

    @Override
    public ItemBlock registerBlock(String name) {
        ItemBlock item = super.registerBlock(name);
        int offset = index * 16;
        for (int i = 0; i < subs; i++) {
            Type type = Type.values()[offset + i];
            switch (type.type) {
                case METAL:
                    FVMod.PROXY.registerItemModel(item, i, "ore_metal");
                    break;
                case GEM:
                    FVMod.PROXY.registerItemModel(item, i, "ore_gem");
                    break;
                case RAW:
                    FVMod.PROXY.registerItemModel(item, i, "ore_raw");
                    break;
            }
            OreDictionary.registerOre("ore" + type.oreKey, new ItemStack(this, 1, i));
        }
        return item;
    }

    @Override
    protected ItemBlock generateItemBlock() {
        return new ItemBlockNetherOre(this);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        this.index = nextIndex++;
        this.propType = PropertyEnum.create(
                "type", Type.class, val -> val.ordinal() >= index * 16 && val.ordinal() < (index + 1) * 16);
        return new BlockStateContainer(this, propType);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(propType, Type.values()[index * 16 + meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(propType).ordinal() % 16;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(state));
    }

    @Nullable
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        Type type = Type.resolve(state);
        switch (type.type) {
            case METAL:
                return Item.getItemFromBlock(this);
            case GEM:
                return OreDictHelper.getStack("gem" + type.dict, 1).getItem();
            case RAW:
                return OreDictHelper.getStack(type.dict, 1).getItem();
        }
        throw new IllegalStateException("Impossible state!");
    }

    @Override
    public int damageDropped(IBlockState state) {
        Type type = Type.resolve(state);
        switch (type.type) {
            case METAL:
                return type.ordinal() % 16;
            case GEM:
                return OreDictHelper.getStack("gem" + type.dict, 1).getMetadata();
            case RAW:
                return OreDictHelper.getStack(type.dict, 1).getMetadata();
        }
        throw new IllegalStateException("Impossible state!");
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        Type type = Type.resolve(state);
        return type.type == OreType.METAL ? 1 : calculateQuantity(type, fortune, random);
    }

    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
        if (state.getValue(propType).type != OreType.GEM)
            return 0;
        Random random = world instanceof World ? ((World)world).rand : new Random();
        return 2 + random.nextInt(3);
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Type type = Type.resolve(state);
        switch (type.type) {
            case METAL:
                return Collections.singletonList(generateStack(1, type));
            case GEM:
                return Collections.singletonList(
                        OreDictHelper.getStack("gem" + type.dict, calculateQuantity(type, fortune)));
            case RAW:
                return Collections.singletonList(
                        OreDictHelper.getStack(type.dict, calculateQuantity(type, fortune)));
        }
        throw new IllegalStateException("Impossible state!");
    }

    private static int calculateQuantity(Type type, int fortune, Random random) {
        return type.dropBase + random.nextInt(type.dropVariant + fortune + 1);
    }

    private static int calculateQuantity(Type type, int fortune) {
        return type.dropBase + (int)Math.floor(Math.random() * (type.dropVariant + fortune + 1));
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
        return true;
    }

    public enum Type implements IStringSerializable {

        GOLD("Gold", OreType.METAL, 0xD8BB34),
        IRON("Iron", OreType.METAL, 0xD8AF93),
        COAL("coal", OreType.RAW, 0x363636, "Coal", 1, 0),
        LAPIS("Lapis", OreType.GEM, 0x18377C, "Lapis", 3, 3),
        DIAMOND("Diamond", OreType.GEM, 0x69A9B7),
        REDSTONE("dustRedstone", OreType.RAW, 0x990303, "Redstone", 3, 2),
        EMERALD("Emerald", OreType.GEM, 0x14B548),
        RUBY("Ruby", OreType.GEM, 0xC0123B),
        PERIDOT("Peridot", OreType.GEM, 0x6F921E),
        SAPPHIRE("Sapphire", OreType.GEM, 0x186FA6),
        OSMIUM("Osmium", OreType.METAL, 0x8DA190),
        COPPER("Copper", OreType.METAL, 0xA76141),
        TIN("Tin", OreType.METAL, 0xBFBFBF),
        SILVER("Silver", OreType.METAL, 0xB5CAC1),
        LEAD("Lead", OreType.METAL, 0x6A708A),
        ALUMINIUM("Aluminum", OreType.METAL, 0xE5A77A),
        NICKEL("Nickel", OreType.METAL, 0xC8BE99),
        PLATINUM("Platinum", OreType.METAL, 0xA8DADE),
        IRIDIUM("Iridium", OreType.METAL, 0xB5AEB6),
        MITHRIL("Mithril", OreType.METAL, 0x5EC9E4),
        DRACONIUM("Draconium", OreType.METAL, 0x5E3B5E),
        COBALT("Cobalt", OreType.METAL, 0x1103CC),
        ARDITE("Ardite", OreType.METAL, 0x94854A);

        public final String dict, oreKey;
        public final OreType type;
        public final int colour;
        public final int dropBase;
        public final int dropVariant;

        Type(String dict, OreType type, int colour) {
            this(dict, type, colour, dict, 1, 0);
        }

        Type(String dict, OreType type, int colour, String oreKey, int dropBase, int dropVariant) {
            this.dict = dict;
            this.type = type;
            this.colour = colour;
            this.oreKey = oreKey;
            this.dropBase = dropBase;
            this.dropVariant = dropVariant;
        }

        @Override
        public String getName() {
            return name().toLowerCase();
        }

        public String getLocalizedName() {
            return LangConst.get(LangConst.ORE_KEY + getName());
        }

        public IBlockState getState() {
            int meta = ordinal() % 16;
            return FVBlocks.netherOre[(ordinal() - meta) / 16].getStateFromMeta(meta);
        }

        public static Type resolve(IBlockState state) {
            BlockNetherOre block = (BlockNetherOre)state.getBlock();
            return values()[16 * block.index + block.getMetaFromState(state)];
        }

        public static Type resolve(ItemStack stack) {
            return values()[16 * ((BlockNetherOre)((ItemBlock)stack.getItem()).getBlock()).index + stack.getMetadata()];
        }

    }

    public enum OreType {

        METAL, GEM, RAW

    }

    private static class StateMapper extends StateMapperBase {

        private final IProperty<Type> prop;

        public StateMapper(IProperty<Type> prop) {
            this.prop = prop;
        }

        @Override
        protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
            return new ModelResourceLocation(
                    FVConst.MOD_PREF + LangConst.BLOCK_NETHER_ORE_NAME + "#type=" + state.getValue(prop).getName());
        }

    }

    public static ItemStack generateStack(int qty, BlockNetherOre.Type type) {
        int meta = type.ordinal() % 16;
        return new ItemStack(FVBlocks.netherOre[(type.ordinal() - meta) / 16], 1, meta);
    }

}
