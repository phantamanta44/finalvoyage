package io.github.phantamanta44.finalvoyage;

import io.github.phantamanta44.finalvoyage.block.FVBlocks;
import io.github.phantamanta44.finalvoyage.constant.FVConst;
import io.github.phantamanta44.finalvoyage.constant.LangConst;
import io.github.phantamanta44.finalvoyage.item.FVItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = FVConst.MOD_ID, version = FVConst.MOD_VERSION)
public class FVMod {

    @Mod.Instance(FVConst.MOD_ID)
    public static FVMod INSTANCE;

    @SidedProxy(
            serverSide = "io.github.phantamanta44.finalvoyage.CommonProxy",
            clientSide = "io.github.phantamanta44.finalvoyage.client.ClientProxy")
    public static CommonProxy PROXY;

    public static final Logger LOGGER = LogManager.getLogger(FVConst.MOD_ID);
    public static final CreativeTabs CREATIVE_TAB = new CreativeTabFinalVoyage();

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        FVItems.init();
        FVBlocks.init();
        PROXY.onPreInit();
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        PROXY.onInit();
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        PROXY.onPostInit();
    }

    @Mod.EventHandler
    public void onServerAboutToStart(FMLServerAboutToStartEvent event) {
        PROXY.onServerAboutToStart(event.getServer());
    }

    private static class CreativeTabFinalVoyage extends CreativeTabs {

        public CreativeTabFinalVoyage() {
            super(LangConst.CREATIVE_TAB_NAME);
        }

        @Override
        public Item getTabIconItem() {
            return Items.NETHERBRICK;
        }

    }

}
