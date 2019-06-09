/*
 *  * Copyright © Wynntils - 2019.
 */

package cn.charlotte.biliforge.instance.containers;

import cn.charlotte.biliforge.instance.KeyHolder;
import cn.charlotte.biliforge.instance.Module;
import cn.charlotte.biliforge.interfaces.annotations.ModuleInfo;
import cn.charlotte.biliforge.manager.FrameworkManager;
import cn.charlotte.biliforge.settings.SettingsContainer;
import cn.charlotte.biliforge.settings.annotations.SettingsInfo;
import cn.charlotte.biliforge.settings.instances.SettingsHolder;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ModuleContainer {

    ModuleInfo info;
    Module module;

    ArrayList<KeyHolder> keyHolders = new ArrayList<>();
    HashMap<String, SettingsContainer> registeredSettings = new HashMap<>();
    HashSet<Object> registeredEvents = new HashSet<>();

    public ModuleContainer(ModuleInfo info, Module module) {
        this.info = info;
        this.module = module;
    }

    public Module getModule() {
        return module;
    }

    public ModuleInfo getInfo() {
        return info;
    }

    public void registerKeyBinding(KeyHolder holder) {
        keyHolders.add(holder);
    }

    public void triggerKeyBinding() {
        if (!getModule().isActive()) {
            return;
        }
        if (keyHolders.size() <= 0) {
            return;
        }
        keyHolders.forEach(k -> {
            if (k.isPress() && k.getKeyBinding().isPressed()) {
                k.getOnAction().run();
            } else if (!k.isPress() && k.getKeyBinding().isKeyDown()) {
                k.getOnAction().run();
            }
        });
    }

    public void registerEvents(Object sClass) {
        FrameworkManager.getEventBus().register(sClass);
        registeredEvents.add(sClass);
    }

    public void unregisterAllEvents() {
        registeredEvents.forEach(FrameworkManager.getEventBus()::unregister);
        registeredEvents.clear();
    }

    public void registerSettings(Class<? extends SettingsHolder> holder) {
        SettingsInfo info = holder.getAnnotation(SettingsInfo.class);
        if (info == null) {
            return;
        }

        for (Field field : holder.getDeclaredFields()) {
            if (field.getType() == holder && Modifier.isStatic(field.getModifiers())) {
                try {
                    field.set(null, holder.getConstructor().newInstance());
                    registeredSettings.put(info.name(), new SettingsContainer(this, (SettingsHolder) field.get(null)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }

    public void registerSettings(String name, SettingsHolder sh) {
        registeredSettings.put(name, new SettingsContainer(this, sh));
    }

    public void reloadSettings() {
        registeredSettings.values().forEach(c -> {
            try {
                c.tryToLoad();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public HashMap<String, SettingsContainer> getRegisteredSettings() {
        return registeredSettings;
    }

}
