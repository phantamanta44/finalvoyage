package io.github.phantamanta44.finalvoyage.item;

import io.github.phantamanta44.finalvoyage.FVMod;
import io.github.phantamanta44.finalvoyage.block.BlockNetherOre;
import io.github.phantamanta44.finalvoyage.constant.FVConst;
import io.github.phantamanta44.finalvoyage.constant.LangConst;
import io.github.phantamanta44.finalvoyage.integration.IMCHelper;
import io.github.phantamanta44.finalvoyage.integration.IntHelper;
import io.github.phantamanta44.finalvoyage.item.base.ItemModSubs;
import io.github.phantamanta44.finalvoyage.util.OreDictHelper;
import mekanism.api.gas.GasRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.EnumSet;

public class ItemMekanismOre extends ItemModSubs {

    private static final EnumSet<BlockNetherOre.Type> MEKANISM_TYPES = EnumSet.of(
            BlockNetherOre.Type.IRON, BlockNetherOre.Type.GOLD, BlockNetherOre.Type.OSMIUM,
            BlockNetherOre.Type.COPPER, BlockNetherOre.Type.TIN, BlockNetherOre.Type.SILVER,
            BlockNetherOre.Type.LEAD);

    private static OreStage[] registry;

    public static OreStage getStage(ItemStack stack) {
        return registry[stack.getMetadata()];
    }

    public static OreStage getStage(BlockNetherOre.Type type, Stage stage) {
        return Arrays.stream(registry).filter(s -> s.type == type && s.stage == stage).findAny().orElse(null);
    }

    private static int buildRegistry() {
        registry = Arrays.stream(BlockNetherOre.Type.values())
                .filter(t -> t.type == BlockNetherOre.OreType.METAL)
                .flatMap(t -> Arrays.stream(Stage.values())
                        .filter(s -> !s.exists(t))
                        .map(s -> new OreStage(t, s)))
                .toArray(OreStage[]::new);
        return registry.length;
    }

    public ItemMekanismOre() {
        super(LangConst.ITEM_MEKANISM_ORE_NAME, buildRegistry());
        OreStage stage;
        for (int i = 0; i < registry.length; i++) {
            stage = registry[i];
            OreDictionary.registerOre(stage.getEntry(), new ItemStack(this, 1, i));
            switch (stage.stage) {
                case CRYSTAL:
                    FVMod.PROXY.registerItemModel(this, i, "mek_ore_crystal");
                    break;
                case SHARD:
                    FVMod.PROXY.registerItemModel(this, i, "mek_ore_shard");
                    break;
                case CLUMP:
                    FVMod.PROXY.registerItemModel(this, i, "mek_ore_clump");
                    break;
                case DIRTY_DUST:
                    FVMod.PROXY.registerItemModel(this, i, "mek_ore_dirty_dust");
                    break;
            }
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return getStage(stack).getLocalizedName();
    }

    public void registerRecipes() {
        for (OreStage stage : registry) {
            switch (stage.stage) {
                case CRYSTAL:
                    OreStage shardStage = getStage(stage.type, stage.stage.next());
                    IntHelper.registerOreGas(stage.type);
                    IMCHelper.addChemicalDissolutionRecipe(
                            BlockNetherOre.generateStack(1, stage.type),
                            IntHelper.gasStack(stage.type.getName(), 1000));
                    IMCHelper.addChemicalWasherRecipe(
                            IntHelper.gasStack(stage.type.getName(), 1),
                            IntHelper.gasStack("clean" + stage.type.getName(), 1));
                    IMCHelper.addChemicalCrystalizationRecipe(
                            IntHelper.gasStack("clean" + stage.type.getName(), 200),
                            stage.getOre(1));
                    IMCHelper.addChemicalInjectionRecipe(
                            stage.getOre(1),
                            GasRegistry.getGas("hydrogenchloride"),
                            shardStage.getOre(1));
                    IMCHelper.addChemicalInjectionRecipe(
                            BlockNetherOre.generateStack(1, stage.type),
                            GasRegistry.getGas("hydrogenchloride"),
                            shardStage.getOre(4));
                    break;
                case SHARD:
                    OreStage clumpStage = getStage(stage.type, stage.stage.next());
                    IMCHelper.addPurificationRecipe(
                            stage.getOre(1),
                            GasRegistry.getGas("oxygen"),
                            clumpStage.getOre(1));
                    IMCHelper.addPurificationRecipe(
                            BlockNetherOre.generateStack(1, stage.type),
                            GasRegistry.getGas("oxygen"),
                            clumpStage.getOre(3));
                    break;
                case CLUMP:
                    OreStage dirtyDustStage = getStage(stage.type, stage.stage.next());
                    IMCHelper.addCrusherRecipe(
                            stage.getOre(1),
                            dirtyDustStage.getOre(1));
                    break;
                case DIRTY_DUST:
                    IMCHelper.addEnrichmentRecipe(
                            stage.getOre(1),
                            OreDictHelper.getStack("dust" + stage.type.oreKey, 1));
                    break;
            }
        }
    }

    public static class OreStage {

        public final BlockNetherOre.Type type;
        public final Stage stage;

        OreStage(BlockNetherOre.Type type, Stage stage) {
            this.type = type;
            this.stage = stage;
        }

        public String getEntry() {
            return stage.getEntry(type.oreKey);
        }

        public ItemStack getOre(int qty) {
            return stage.getOre(type.oreKey, qty);
        }

        public String getLocalizedName() {
            return stage.getLocalizedName(type);
        }

    }

    public enum Stage {

        CRYSTAL("crystal"),
        SHARD("shard"),
        CLUMP("clump"),
        DIRTY_DUST("dustDirty");

        public final String prefix;

        Stage(String prefix) {
            this.prefix = prefix;
        }

        public String getEntry(String ore) {
            return prefix + ore;
        }

        public ItemStack getOre(String ore, int qty) {
            return OreDictHelper.getStack(getEntry(ore), qty);
        }

        public Stage next() {
            return values()[ordinal() + 1];
        }

        public boolean exists(BlockNetherOre.Type type) {
            return MEKANISM_TYPES.contains(type) || OreDictHelper.exists(getEntry(type.oreKey));
        }

        public String getLocalizedName(BlockNetherOre.Type type) {
            return LangConst.get("item." + FVConst.MOD_PREF + LangConst.ITEM_MEKANISM_ORE_NAME +
                    "." + prefix + ".name", type.getLocalizedName());
        }

    }

}
