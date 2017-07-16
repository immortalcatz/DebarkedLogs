package debarking;

import debarking.event.DebarkingEvent;
import debarking.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "debarkedlogs", name = "Debarked Logs", version = "${version}", dependencies = "after:betterwithmods")
public class Debarking {
    @SidedProxy(clientSide = "debarking.proxy.ClientProxy", serverSide = "debarking.proxy.CommonProxy")
    public static CommonProxy proxy;
    @Mod.Instance("debarkedlogs")
    public static Debarking INSTANCE;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent evt) {
        DebarkRegistry.preInit();
        MinecraftForge.EVENT_BUS.register(new DebarkingEvent());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent evt) {
        DebarkRegistry.postInit();
    }
}
