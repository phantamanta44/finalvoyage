package io.github.phantamanta44.finalvoyage.integration;

import net.minecraft.item.Item;

public enum IntMod {

    TF("thermalfoundation"), TE("thermalexpansion"), TD("thermaldynamics"),
    MEK("Mekanism"), MEK_GEN("MekanismGenerators"), MEK_TOOL("MekanismTools"),
    NAT("natura"),
    TCON("tconstruct"),
    QS("quantumstorage"), RS("refinedstorage"),
    PL2("practicallogistics2"),
    ADV_GEN("advgenerators"),
    RELIQ("xreliquary"),
    MPS("powersuits"),
    CHUNKS("chickenchunks"),
    OC("opencomputers"), ORADIO("openradio"), OSEC("opensecurity"),
    P_RED("projectred-core"), P_RED_EXP("projectred-expansion"),
    SCORE("sonarcore"),
    SFM("stevesfactorymanager"),
    ZERO_CORE("zerocore"), PLUSTIC("plustic");

    public final String modId, modPref;

    IntMod(String modId) {
        this.modId = modId;
        this.modPref = modId + ":";
    }

    public Item getItem(String name) {
        return Item.getByNameOrId(modPref + name);
    }

}
