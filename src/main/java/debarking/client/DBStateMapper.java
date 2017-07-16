package debarking.client;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class DBStateMapper extends StateMapperBase {
    private final String blockString;

    public DBStateMapper(String name) {
        this.blockString = name;
    }

    @Nonnull
    @Override
    protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
        return new ModelResourceLocation(blockString, this.getPropertyString(state.getProperties()));
    }
}
