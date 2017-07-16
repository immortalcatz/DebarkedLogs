package debarking.compat;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

public class ModCompat {
    public static boolean betterwithmods = false;

    public static void preInit() {
        betterwithmods = Loader.isModLoaded("betterwithmods");
        if (betterwithmods)
            BWM.preInit();
    }

    public static void init() {
        if (betterwithmods)
            BWM.init();
    }

    public static void postInit() {

    }

    public static ItemStack getBarkStack(String str) {
        if (!betterwithmods) {
            return ItemStack.EMPTY;
        }
        return BWM.getBarkStack(str);
    }
}
