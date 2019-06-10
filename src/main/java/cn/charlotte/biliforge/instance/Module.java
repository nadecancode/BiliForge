package cn.charlotte.biliforge.instance;

import cn.charlotte.biliforge.BiliForge;
import cn.charlotte.biliforge.interfaces.Listener;
import cn.charlotte.biliforge.manager.FrameworkManager;
import cn.charlotte.biliforge.overlay.Overlay;
import lombok.SneakyThrows;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Logger;

public abstract class Module {

    private Logger logger;

    public abstract void onEnable();

    public void postEnable() {

    }

    public void onDisable() {

    }

    public boolean isActive() {
        return true;
    }

    public void registerEvents(Listener listenerClass) {
        FrameworkManager.registerEvents(this, listenerClass);
    }

    @SneakyThrows
    public void registerSettings(Class settingsClass) {
        BiliForge.getInstance().getSettingsRegistry().register(settingsClass);
    }

    public void registerOverlay(Overlay overlay) {
        MinecraftForge.EVENT_BUS.register(overlay);
    }

    public KeyHolder registerKeyBinding(String name, int key, String tab, boolean press, Runnable onPress) {
        return FrameworkManager.registerKeyBinding(this, new KeyHolder(name, key, tab, press, onPress));
    }

    public Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

}
