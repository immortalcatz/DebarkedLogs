package debarking;

import debarking.blocks.BlockDebarkedLog;
import debarking.blocks.ItemBlockMeta;
import debarking.client.DBStateMapper;
import debarking.compat.ModCompat;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = "debarkedlogs")
public class DebarkRegistry {
    public static List<Block> toRegister = new ArrayList<>();
    public static List<Item> itemBlocks = new ArrayList<>();
    private static List<Debarking> debark = new ArrayList<>();

    public static Block oldDebarked = new BlockDebarkedLog(Blocks.LOG, new String[] {"axis=y,variant=0", "axis=y,variant=1", "axis=y,variant=2", "axis=y,variant=3"})
            .setUnlocalizedName("debarked:log_old").setRegistryName(new ResourceLocation("debarkedlogs", "log_old"));
    public static Block newDebarked = new BlockDebarkedLog(Blocks.LOG2, new String [] {"axis=y,variant=0", "axis=y,variant=1"})
            .setUnlocalizedName("debarked:log_new").setRegistryName(new ResourceLocation("debarkedlogs", "log_new"));

    public static void preInit() {
        registerBlock(oldDebarked, new ItemBlockMeta(oldDebarked), "oak", "spruce", "birch", "jungle");
        registerBlock(newDebarked, new ItemBlockMeta(newDebarked), "acacia", "dark_oak");
        ModCompat.preInit();
    }

    public static void postInit() {
        ModCompat.postInit();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> evt) {
        toRegister.forEach(evt.getRegistry()::register);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> evt) {
        itemBlocks.forEach(evt.getRegistry()::register);
    }

    public static Block registerBlock(Block block, Item item, String... barkNames) {
        toRegister.add(block);
        itemBlocks.add(item.setRegistryName(block.getRegistryName()));
        if (block instanceof BlockDebarkedLog && barkNames != null && barkNames.length > 0) {
            for (int i = 0; i < barkNames.length; i++) {
                String bark = barkNames[i];
                debark.add(new Debarking((BlockDebarkedLog) block, i, bark));
            }
        }
        return block;
    }

    @SideOnly(Side.CLIENT)
    public static void setInventoryModel(Block block) {
        setInventoryModel(Item.getItemFromBlock(block));
    }

    @SideOnly(Side.CLIENT)
    public static void setInventoryModel(Item item) {
        if (item instanceof ItemBlock)
            setInventoryModel((ItemBlock)item);
    }

    @SideOnly(Side.CLIENT)
    public static void setInventoryModel(ItemBlock item) {
        Block block = item.getBlock();
        if (block instanceof BlockDebarkedLog) {
            ModelLoader.setCustomStateMapper(block, new DBStateMapper(block.getRegistryName().toString()));
            String[] variants = ((BlockDebarkedLog)block).getVariants();
            for (int i = 0; i < variants.length; i++) {
                if (!Objects.equals(variants[i], "")) setModelLocation(item, i, variants[i]);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private static void setModelLocation(Item item, int meta, String variantSettings) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName().toString(), variantSettings));
    }

    public static List<Debarking> getDebarking() {
        return debark;
    }

    public static class Debarking {
        BlockDebarkedLog block;
        int meta;
        String bark;
        public Debarking(BlockDebarkedLog block, int meta, String barkStack) {
            this.block = block;
            this.meta = meta;
            this.bark = barkStack.equals("") ? "oak" : barkStack;
        }

        public boolean matches(IBlockState state) {
            if (block.getBaseBlock() == null) return false;
            return state.getBlock() == block.getBaseBlock() && state.getBlock().damageDropped(state) == this.meta;
        }

        public IBlockState getDebarkState(IBlockState state) {
            return block.getStateFromMeta(state.getBlock().getMetaFromState(state));
        }

        public String getBarkStack() {
            return bark;
        }
    }
}
