/*
 *  * Copyright ? Wynntils - 2019.
 */

package cn.charlotte.biliforge.manager;

import cn.charlotte.biliforge.Reference;
import cn.charlotte.biliforge.instance.KeyHolder;
import cn.charlotte.biliforge.instance.Module;
import cn.charlotte.biliforge.instance.containers.ModuleContainer;
import cn.charlotte.biliforge.interfaces.Listener;
import cn.charlotte.biliforge.interfaces.annotations.ModuleInfo;
import cn.charlotte.biliforge.util.reflection.ReflectionFields;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;

import java.util.HashMap;

public class FrameworkManager {

    public static HashMap<String, ModuleContainer> availableModules = new HashMap<>();
    private static long tick = 0;
    private static EventBus eventBus = new EventBus();

    public static void registerModule(Module module) {
        ModuleInfo info = module.getClass().getAnnotation(ModuleInfo.class);
        if (info == null) {
            return;
        }

        module.setLogger(LogManager.getFormatterLogger(Reference.MOD_ID + "-" + info.name().toLowerCase()));

        availableModules.put(info.name(), new ModuleContainer(info, module));
    }

    public static void registerEvents(Module module, Listener listener) {
        ModuleInfo info = module.getClass().getAnnotation(ModuleInfo.class);
        if (info == null) {
            return;
        }

        availableModules.get(info.name()).registerEvents(listener);
    }

    public static KeyHolder registerKeyBinding(Module module, KeyHolder holder) {
        ModuleInfo info = module.getClass().getAnnotation(ModuleInfo.class);
        if (info == null) {
            return null;
        }

        availableModules.get(info.name()).registerKeyBinding(holder);
        return holder;
    }

    public static void startModules() {
        availableModules.values().forEach(c -> c.getModule().onEnable());
    }

    public static void postEnableModules() {
        availableModules.values().forEach(c -> c.getModule().postEnable());
    }

    public static void registerCommands() {

    }

    public static void disableModules() {
        availableModules.values().forEach(c -> {
            c.getModule().onDisable();
            c.unregisterAllEvents();
        });
    }

    public static void triggerEvent(Event e) {
        if (e instanceof TickEvent.RenderTickEvent) {
            ReflectionFields.Event_phase.setValue(e, null);
            eventBus.post(e);
        }
    }

    public static void triggerKeyPress() {
        availableModules.values().forEach(ModuleContainer::triggerKeyBinding);
    }

    public static EventBus getEventBus() {
        return eventBus;
    }

}
