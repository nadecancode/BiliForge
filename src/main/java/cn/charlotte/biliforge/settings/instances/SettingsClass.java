/*
 *  * Copyright © Wynntils - 2019.
 */

package cn.charlotte.biliforge.settings.instances;

import cn.charlotte.biliforge.instance.Module;
import cn.charlotte.biliforge.manager.FrameworkManager;

public abstract class SettingsClass implements SettingsHolder {

    @Override
    public void saveSettings(Module m) {
        try {
            FrameworkManager.getSettings(m, this).saveSettings();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onSettingChanged(String name) {

    }

    @Override
    public void onSettingsSaved() {

    }

}
