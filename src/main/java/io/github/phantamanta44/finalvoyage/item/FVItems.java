package io.github.phantamanta44.finalvoyage.item;

public class FVItems {

    public static ItemDrink drink;
    public static ItemFood food;
    public static ItemWaterBottle waterBottle;
    public static ItemMekanismOre mekanismOre;
    public static ItemDust dust;
    public static ItemMisc misc;

    public static void init() {
        drink = new ItemDrink();
        food = new ItemFood();
        waterBottle = new ItemWaterBottle();
        mekanismOre = new ItemMekanismOre();
        dust = new ItemDust();
        misc = new ItemMisc();
    }

}
