package debarking.blocks;

import debarking.client.DBCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDebarkedLog extends BlockLog {
    private static final PropertyInteger TYPE = PropertyInteger.create("variant", 0, 3);
    private Block baseBlock;
    private String[] variants;

    public BlockDebarkedLog(Block base, String[] variants) {
        this(variants);
        this.baseBlock = base;
    }

    public BlockDebarkedLog(String[] variants) {
        this.setDefaultState(this.getDefaultState().withProperty(LOG_AXIS, EnumAxis.Y));
        this.variants = variants;
        this.setCreativeTab(DBCreativeTabs.DBTAB);
    }

    public void setBaseBlock(Block block) {
        baseBlock = block;
    }

    public Block getBaseBlock() {
        return baseBlock;
    }

    public String[] getVariants() {
        return variants;
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for (int i = 0; i < getVariants().length; i++) {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public float getBlockHardness(IBlockState state, World world, BlockPos pos) {
        return baseBlock.getBlockHardness(baseBlock.getStateFromMeta(state.getBlock().getMetaFromState(state)), world, pos);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(TYPE);
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return baseBlock.getFireSpreadSpeed(world, pos, face);
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return baseBlock.getFlammability(world, pos, face);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = state.getValue(TYPE);
        switch (state.getValue(LOG_AXIS))
        {
            case X:
                meta |= 4;
                break;
            case Z:
                meta |= 8;
                break;
            case NONE:
                meta |= 12;
        }
        return meta;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState().withProperty(TYPE, meta & 3);

        switch (meta & 12)
        {
            case 0:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
                break;
            case 4:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
                break;
            case 8:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
                break;
            default:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
        }

        return iblockstate;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LOG_AXIS, TYPE);
    }
}
