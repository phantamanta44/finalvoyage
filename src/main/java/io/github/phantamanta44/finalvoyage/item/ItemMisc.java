package io.github.phantamanta44.finalvoyage.item;

import io.github.phantamanta44.finalvoyage.FVMod;
import io.github.phantamanta44.finalvoyage.constant.LangConst;
import io.github.phantamanta44.finalvoyage.item.base.ItemModSubs;

public class ItemMisc extends ItemModSubs {

    public ItemMisc() {
        super(LangConst.ITEM_MISC_NAME, 1);
        FVMod.PROXY.registerItemModel(this, 0, "entropic_dust");
    }

}
