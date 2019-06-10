/*
 *  * Copyright © Wynntils - 2019.
 */

package cn.charlotte.biliforge.instance.containers;

import cn.charlotte.biliforge.instance.KeyHolder;
import cn.charlotte.biliforge.instance.Module;
import cn.charlotte.biliforge.interfaces.annotations.ModuleInfo;
import cn.charlotte.biliforge.manager.FrameworkManager;

import java.util.ArrayList;
import java.util.HashSet;

public class ModuleContainer {

    ModuleInfo info;
    Module module;

    ArrayList<KeyHolder> keyHolders = new ArrayList<>();
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
}
