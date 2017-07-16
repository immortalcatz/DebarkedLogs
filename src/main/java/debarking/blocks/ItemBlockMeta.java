package debarking.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMeta extends ItemBlock {
    public ItemBlockMeta(Block block) {
        super(block);
        this.setMaxDamage(0).setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + block.damageDropped(block.getStateFromMeta(stack.getMetadata()));
    }
}
