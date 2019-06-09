/*
 *  * Copyright ? Wynntils - 2019.
 */

package cn.charlotte.biliforge.manager;

import cn.charlotte.biliforge.BiliForge;
import cn.charlotte.biliforge.instance.KeyHolder;
import cn.charlotte.biliforge.instance.Module;
import cn.charlotte.biliforge.instance.containers.ModuleContainer;
import cn.charlotte.biliforge.interfaces.Listener;
import cn.charlotte.biliforge.interfaces.annotations.ModuleInfo;
import cn.charlotte.biliforge.settings.SettingsContainer;
import cn.charlotte.biliforge.settings.annotations.SettingsInfo;
import cn.charlotte.biliforge.settings.instances.SettingsHolder;
import cn.charlotte.biliforge.util.mouse.Priority;
import cn.charlotte.biliforge.util.overlay.Overlay;
import cn.charlotte.biliforge.util.reflection.ReflectionFields;
import cn.charlotte.biliforge.util.render.ScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.HashMap;

public class FrameworkManager {

    public static HashMap<String, ModuleContainer> availableModules = new HashMap<>();
    public static HashMap<Priority, ArrayList<Overlay>> registeredOverlays = new HashMap<>();
    private static long tick = 0;
    private static EventBus eventBus = new EventBus();

    static {
        registeredOverlays.put(Priority.LOWEST, new ArrayList<>());
        registeredOverlays.put(Priority.LOW, new ArrayList<>());
        registeredOverlays.put(Priority.NORMAL, new ArrayList<>());
        registeredOverlays.put(Priority.HIGH, new ArrayList<>());
        registeredOverlays.put(Priority.HIGHEST, new ArrayList<>());
    }

    public static void registerModule(Module module) {
        ModuleInfo info = module.getClass().getAnnotation(ModuleInfo.class);
        if (info == null) {
            return;
        }

        module.setLogger(LogManager.getFormatterLogger(BiliForge.MODID + "-" + info.name().toLowerCase()));

        availableModules.put(info.name(), new ModuleContainer(info, module));
    }

    public static void registerEvents(Module module, Listener listener) {
        ModuleInfo info = module.getClass().getAnnotation(ModuleInfo.class);
        if (info == null) {
            return;
        }

        availableModules.get(info.name()).registerEvents(listener);
    }

    public static void registerSettings(Module module, Class<? extends SettingsHolder> settingsClass) {
        ModuleInfo info = module.getClass().getAnnotation(ModuleInfo.class);
        if (info == null)
            return;

        availableModules.get(info.name()).registerSettings(settingsClass);
    }


    public static void registerOverlay(Module module, Overlay overlay, Priority priority) {
        ModuleInfo info = module.getClass().getAnnotation(ModuleInfo.class);
        if (info == null)
            return;

        ModuleContainer mc = availableModules.get(info.name());

        overlay.module = mc;

        mc.registerSettings("overlay" + overlay.displayName, overlay);

        registeredOverlays.get(priority).add(overlay);
    }

    public static KeyHolder registerKeyBinding(Module module, KeyHolder holder) {
        ModuleInfo info = module.getClass().getAnnotation(ModuleInfo.class);
        if (info == null) {
            return null;
        }

        availableModules.get(info.name()).registerKeyBinding(holder);
        return holder;
    }

    public static void reloadSettings() {
        availableModules.values().forEach(ModuleContainer::reloadSettings);
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

    public static void triggerHud(RenderGameOverlayEvent e) {
        if (!BiliForge.getInstance().getMinecraft().playerController.isSpectator()) {
            if (e.type == RenderGameOverlayEvent.ElementType.AIR || //move it to somewhere else if you want, it seems pretty core to wynncraft tho..
                    e.type == RenderGameOverlayEvent.ElementType.ARMOR) {
                e.setCanceled(true);
                return;
            }
            Minecraft.getMinecraft().mcProfiler.startSection("preRenOverlay");
            for (ArrayList<Overlay> overlays : registeredOverlays.values()) {
                for (Overlay overlay : overlays) {
                    if (!overlay.active) continue;

                    if (overlay.overrideElements.length != 0) {
                        boolean contained = false;
                        for (RenderGameOverlayEvent.ElementType type : overlay.overrideElements) {
                            if (e.type == type) {
                                contained = true;
                                break;
                            }
                        }
                        if (contained)
                            e.setCanceled(true);
                        else
                            continue;
                    }
                    if ((overlay.module == null || overlay.module.getModule().isActive()) && overlay.visible && overlay.active) {
                        Minecraft.getMinecraft().mcProfiler.startSection(overlay.displayName);
                        ScreenRenderer.beginGL(overlay.position.getDrawingX(), overlay.position.getDrawingY());
                        overlay.render(e);
                        ScreenRenderer.endGL();
                        Minecraft.getMinecraft().mcProfiler.endSection();
                    }
                }
            }
            Minecraft.getMinecraft().mcProfiler.endSection();
        }
    }

    public static void triggerPreHud(RenderGameOverlayEvent.Pre e) {
        if (!BiliForge.getInstance().getMinecraft().playerController.isSpectator()) {
            if (e.type == RenderGameOverlayEvent.ElementType.AIR || //move it to somewhere else if you want, it seems pretty core to wynncraft tho..
                    e.type == RenderGameOverlayEvent.ElementType.ARMOR) {
                e.setCanceled(true);
                return;
            }
            Minecraft.getMinecraft().mcProfiler.startSection("preRenOverlay");
            for (ArrayList<Overlay> overlays : registeredOverlays.values()) {
                for (Overlay overlay : overlays) {
                    if (!overlay.active) continue;

                    if (overlay.overrideElements.length != 0) {
                        boolean contained = false;
                        for (RenderGameOverlayEvent.ElementType type : overlay.overrideElements) {
                            if (e.type == type) {
                                contained = true;
                                break;
                            }
                        }
                        if (contained)
                            e.setCanceled(true);
                        else
                            continue;
                    }
                    if ((overlay.module == null || overlay.module.getModule().isActive()) && overlay.visible && overlay.active) {
                        Minecraft.getMinecraft().mcProfiler.startSection(overlay.displayName);
                        ScreenRenderer.beginGL(overlay.position.getDrawingX(), overlay.position.getDrawingY());
                        overlay.render(e);
                        ScreenRenderer.endGL();
                        Minecraft.getMinecraft().mcProfiler.endSection();
                    }
                }
            }
            Minecraft.getMinecraft().mcProfiler.endSection();
        }
    }

    public static void triggerPostHud(RenderGameOverlayEvent.Post e) {
        if (!BiliForge.getInstance().getMinecraft().playerController.isSpectator()) {

            Minecraft.getMinecraft().mcProfiler.startSection("posRenOverlay");
            for (ArrayList<Overlay> overlays : registeredOverlays.values()) {
                for (Overlay overlay : overlays) {
                    if (!overlay.active) continue;

                    if (overlay.overrideElements.length != 0) {
                        boolean contained = false;
                        for (RenderGameOverlayEvent.ElementType type : overlay.overrideElements) {
                            if (e.type == type) {
                                contained = true;
                                break;
                            }
                        }
                        if (contained)
                            e.setCanceled(true);
                        else
                            continue;
                    }
                    if ((overlay.module == null || overlay.module.getModule().isActive()) && overlay.visible && overlay.active) {
                        Minecraft.getMinecraft().mcProfiler.startSection(overlay.displayName);
                        ScreenRenderer.beginGL(overlay.position.getDrawingX(), overlay.position.getDrawingY());
                        overlay.render(e);
                        ScreenRenderer.endGL();
                        Minecraft.getMinecraft().mcProfiler.endSection();
                    }
                }
            }
            Minecraft.getMinecraft().mcProfiler.endSection();

        }
    }

    public static void triggerHudTick(TickEvent.ClientTickEvent e) {
        tick++;
        for (ArrayList<Overlay> overlays : registeredOverlays.values()) {
            for (Overlay overlay : overlays) {
                if ((overlay.module == null || overlay.module.getModule().isActive()) && overlay.active) {
                    overlay.position.refresh(ScreenRenderer.screen);
                    overlay.tick(e, tick);
                }
            }
        }
    }

    public static void triggerKeyPress() {
        availableModules.values().forEach(ModuleContainer::triggerKeyBinding);
    }

    public static SettingsContainer getSettings(Module module, SettingsHolder holder) {
        ModuleInfo info = module.getClass().getAnnotation(ModuleInfo.class);
        if (info == null) {
            return null;
        }

        SettingsInfo info2 = holder.getClass().getAnnotation(SettingsInfo.class);
        if (info2 == null) {
            if (holder instanceof Overlay)
                return availableModules.get(info.name()).getRegisteredSettings().get("overlay" + ((Overlay) holder).displayName);
            else
                return null;
        }

        return availableModules.get(info.name()).getRegisteredSettings().get(info2.name());
    }

    public static EventBus getEventBus() {
        return eventBus;
    }

}
