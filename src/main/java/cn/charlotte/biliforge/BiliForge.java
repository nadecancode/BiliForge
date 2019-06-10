package cn.charlotte.biliforge;

import cn.charlotte.biliforge.color.ChatColor;
import cn.charlotte.biliforge.command.BiliForgeCommand;
import cn.charlotte.biliforge.event.ClientEvents;
import cn.charlotte.biliforge.manager.FrameworkManager;
import cn.charlotte.biliforge.module.ModuleManager;
import cn.charlotte.biliforge.settings.SettingsRegistry;
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

import java.util.Timer;

@Mod(name = Reference.MOD_ID,
        modid = Reference.MOD_NAME,
        version = Reference.MOD_VERSION,
        acceptableRemoteVersions = Reference.ACCEPTED_VERSIONS
)
@Getter
public class BiliForge {
    @Getter
    private static BiliForge instance;
    private Logger logger = LogManager.getLogger("BiliForge");
    private Minecraft minecraft;

    private Timer timer;

    private SettingsRegistry settingsRegistry;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        instance = this;
        this.timer = new Timer();
        this.minecraft = Minecraft.getMinecraft();
        this.registerCommand(new BiliForgeCommand());
        //DependencyInjector.injectDependencies();
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
        this.settingsRegistry = new SettingsRegistry();
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
