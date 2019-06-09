/*
 *  * Copyright © Wynntils - 2019.
 */

package cn.charlotte.biliforge.settings.instances;

import cn.charlotte.biliforge.instance.Module;

public interface SettingsHolder {

    void onSettingChanged(String name);

    void saveSettings(Module m);

    void onSettingsSaved();

}
