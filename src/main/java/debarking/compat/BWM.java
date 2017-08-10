package debarking.compat;

import betterwithmods.common.BWMRecipes;
import betterwithmods.common.BWOreDictionary;
import betterwithmods.common.items.ItemBark;
import betterwithmods.common.items.ItemMaterial;
import betterwithmods.common.registry.ChoppingRecipe;
import betterwithmods.common.registry.blockmeta.managers.KilnManager;
import betterwithmods.common.registry.blockmeta.managers.SawManager;
import betterwithmods.module.ModuleLoader;
import debarking.DebarkRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

public class BWM {
    private static boolean isHCLumber = ModuleLoader.isFeatureEnabled("betterwithmods.module.hardcore.HCLumber");

    public static void preInit() {

    }

    public static void init() {
        addChopping(new BWOreDictionary.Wood(new ItemStack(DebarkRegistry.oldDebarked, 1, 0), new ItemStack(Blocks.PLANKS, 1, 0), ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.SAWDUST)), "oak_planks");
        addChopping(new BWOreDictionary.Wood(new ItemStack(DebarkRegistry.oldDebarked, 1, 1), new ItemStack(Blocks.PLANKS, 1, 1), ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.SAWDUST)), "spruce_planks");
        addChopping(new BWOreDictionary.Wood(new ItemStack(DebarkRegistry.oldDebarked, 1, 2), new ItemStack(Blocks.PLANKS, 1, 2), ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.SAWDUST)), "birch_planks");
        addChopping(new BWOreDictionary.Wood(new ItemStack(DebarkRegistry.oldDebarked, 1, 3), new ItemStack(Blocks.PLANKS, 1, 3), ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.SAWDUST)), "jungle_planks");
        addChopping(new BWOreDictionary.Wood(new ItemStack(DebarkRegistry.newDebarked, 1, 0), new ItemStack(Blocks.PLANKS, 1, 4), ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.SAWDUST)), "acacia_planks");
        addChopping(new BWOreDictionary.Wood(new ItemStack(DebarkRegistry.newDebarked, 1, 1), new ItemStack(Blocks.PLANKS, 1, 5), ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.SAWDUST)), "dark_oak_planks");
    }

    private static void addChopping(BWOreDictionary.Wood wood, String woodType) {
        System.out.println("Adding BWM recipes for plank type: " + woodType);
        BWOreDictionary.woods.add(wood);
        SawManager.WOOD_SAW.addRecipe(wood.getLog(1), wood.getPlank(isHCLumber ? 4 : 5), wood.getSawdust(3));
        KilnManager.INSTANCE.addRecipe(wood.getLog(1), new ItemStack(Items.COAL, 2, 1));
        if (isHCLumber)
            addHCRecipe(new ChoppingRecipe(wood).setRegistryName(new ResourceLocation("debarkedlogs", woodType)));
    }

    private static IRecipe addHCRecipe(IRecipe recipe) {
        return BWMRecipes.addHardcoreRecipe("HCLumber", recipe);
    }

    protected static ItemStack getBarkStack(String string) {
        return ItemBark.getStack(string, 2);
    }
}
