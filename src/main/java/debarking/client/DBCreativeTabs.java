package debarking.client;

import debarking.DebarkRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DBCreativeTabs {
    public static final CreativeTabs DBTAB = new CreativeTabs("debark:dbTab") {
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(DebarkRegistry.oldDebarked);
        }
    };
}
