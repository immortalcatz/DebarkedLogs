package debarking.event;

import debarking.DebarkRegistry;
import debarking.compat.ModCompat;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DebarkingEvent {
    @SubscribeEvent
    public void debarkLog(PlayerInteractEvent.RightClickBlock evt) {
        World world = evt.getWorld();
        if (!world.isRemote) {
            BlockPos pos = evt.getPos();
            EntityPlayer player = evt.getEntityPlayer();
            IBlockState state = world.getBlockState(pos);
            ItemStack playerStack = player.getHeldItem(evt.getHand());
            if (playerStack.isEmpty())
                return;
            BlockPos playerPos = pos.offset(evt.getFace());
            if (!playerStack.isEmpty() && (playerStack.getItem().getHarvestLevel(playerStack, "axe", player, world.getBlockState(pos)) >= 0 || playerStack.getItem().getToolClasses(playerStack).contains("axe"))) {
                IBlockState barking = null;
                String bark = "";
                for (DebarkRegistry.Debarking debark : DebarkRegistry.getDebarking()) {
                    if (debark.matches(state)) {
                        barking = debark.getDebarkState(state);
                        bark = debark.getBarkStack();
                        break;
                    }
                }
                if (barking != null) {
                    ejectStackWithOffset(world, playerPos,ModCompat.getBarkStack(bark).copy());
                    world.setBlockState(pos, barking);
                    world.playSound(null, pos, SoundEvents.ENTITY_ZOMBIE_ATTACK_DOOR_WOOD, SoundCategory.BLOCKS, 0.5F, 2.5F);
                    playerStack.damageItem(1, player);
                }
            }
        }
    }

    private void ejectStackWithOffset(World world, BlockPos pos, ItemStack stack) {
        if(!stack.isEmpty()) {
            float xOff = world.rand.nextFloat() * 0.7F + 0.15F;
            float yOff = world.rand.nextFloat() * 0.2F + 0.1F;
            float zOff = world.rand.nextFloat() * 0.7F + 0.15F;
            ejectStack(world, (double)((float)pos.getX() + xOff), (double)((float)pos.getY() + yOff), (double)((float)pos.getZ() + zOff), stack, 10);
        }
    }

    private void ejectStack(World world, double x, double y, double z, ItemStack stack, int pickupDelay) {
        if(!world.isRemote) {
            EntityItem item = new EntityItem(world, x, y, z, stack);
            float velocity = 0.05F;
            item.motionX = (double)((float)world.rand.nextGaussian() * velocity);
            item.motionY = (double)((float)world.rand.nextGaussian() * velocity + 0.2F);
            item.motionZ = (double)((float)world.rand.nextGaussian() * velocity);
            item.setPickupDelay(pickupDelay);
            world.spawnEntity(item);
        }
    }
}
