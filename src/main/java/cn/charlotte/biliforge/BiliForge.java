package cn.charlotte.biliforge;

import cn.charlotte.biliforge.color.ChatColor;
import cn.charlotte.biliforge.command.BiliForgeCommand;
import cn.charlotte.biliforge.event.ClientEvents;
import cn.charlotte.biliforge.manager.FrameworkManager;
import cn.charlotte.biliforge.module.ModuleManager;
import cn.charlotte.biliforge.util.render.textures.Textures;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.command.ICommand;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Timer;

@Mod(name = BiliForge.MODID,
        modid = BiliForge.NAME,
        version = BiliForge.VERSION,
        acceptableRemoteVersions = BiliForge.ACCEPTED_VERSIONS,
        guiFactory = "cn.charlotte.biliforge.ui.SettingsGuiFactory"
)
@Getter
public class BiliForge {
    public static final String MODID = "BiliForge";
    public static final File MOD_STORAGE_ROOT = new File("biliforge");
    public static final File MOD_ASSETS_ROOT = new File(MOD_STORAGE_ROOT + "\\assets");
    public static final String NAME = "BiliForge";
    public static final String VERSION = "1.0.0 - Beta";
    public static final String ACCEPTED_VERSIONS = "[1.8.9]";
    @Mod.Instance(BiliForge.MODID)
    @Getter
    private static BiliForge instance;
    private Logger logger = LogManager.getLogger("BiliForge");
    private Minecraft minecraft;

    private Timer timer;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        instance = this;
        this.timer = new Timer();
        this.minecraft = Minecraft.getMinecraft();
        this.registerCommand(new BiliForgeCommand());
        //DependencyInjector.injectDependencies();
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
        ModuleManager.registerModules();
        FrameworkManager.startModules();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        FrameworkManager.postEnableModules();
        FrameworkManager.registerCommands();
        Textures.loadTextures();
        ((SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(resourceManager -> {
            Textures.loadTextures();
        });
    }

    private void registerCommand(ICommand command) {
        ClientCommandHandler.instance.registerCommand(command);
    }

    public void sendMessage(String message) {
        this.minecraft.thePlayer.addChatMessage(new ChatComponentText(ChatColor.translateAlternateColorCodes('&', message)));
    }
}
