package debarking.proxy;

import debarking.DebarkRegistry;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = "debarkedlogs", value = Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent evt) {
        DebarkRegistry.itemBlocks.forEach(DebarkRegistry::setInventoryModel);
        DebarkRegistry.toRegister.forEach(DebarkRegistry::setInventoryModel);
    }
}
