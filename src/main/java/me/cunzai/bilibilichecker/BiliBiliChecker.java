package me.cunzai.bilibilichecker;

import com.google.common.eventbus.Subscribe;
import me.cunzai.bilibilichecker.Proxy.CommonProxy;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(name=BiliBiliChecker.MODID,modid = BiliBiliChecker.NAME,version=BiliBiliChecker.VERSION,acceptableRemoteVersions = "[1.8.9]")
public class BiliBiliChecker {
    public static final String MODID = "BiliBiliChecker";
    public static final String NAME= "BiliBili Checker";
    public static final String VERSION = "1.0";
    private static final Logger logger;
    public static BiliBiliGUI gui;

    @Mod.Instance(BiliBiliChecker.MODID)
    public static  BiliBiliChecker ins;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ins = this;
        this.gui= new BiliBiliGUI(ConfigLoader.text,ConfigLoader.color,ConfigLoader.isShadow,ConfigLoader.x,ConfigLoader.y);

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent e){
            gui.draw();
            logger.info("12312312312312123");

    }

    static {
        logger = LogManager.getLogger("BiliBili");
    }




}
