package debarking.compat;

import betterwithmods.common.items.ItemBark;
import net.minecraft.item.ItemStack;

public class BWM {


    protected static ItemStack getBarkStack(String string) {
        return ItemBark.getStack(string, 2);
    }
}
