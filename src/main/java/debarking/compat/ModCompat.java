package debarking.compat;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

public class ModCompat {
    public static void preInit() {

    }

    public static void postInit() {

    }

    public static ItemStack getBarkStack(String str) {
        if (!Loader.isModLoaded("betterwithmods")) {
            return ItemStack.EMPTY;
        }
        return BWM.getBarkStack(str);
    }
}
