package cn.charlotte.biliforge.module.impl.core.settings;

import cn.charlotte.biliforge.module.impl.core.CoreModule;
import cn.charlotte.biliforge.settings.annotations.Setting;
import cn.charlotte.biliforge.settings.annotations.SettingsInfo;
import cn.charlotte.biliforge.settings.instances.SettingsClass;

@SettingsInfo(name = "core", displayPath = "Core")
public class CoreModuleConfig extends SettingsClass {

    public static CoreModuleConfig INSTANCE;

    @Setting(displayName = "Bilibili UID", description = "你的 Bilibili 用户UID (不是名称)")
    public String uid = "22980940";

    @Setting(displayName = "Refresh Interval", description = "从 Bilibili API 发送请求的时间间隔 (单位: 秒)")
    @Setting.Limitations.IntLimit(min = 30, max = 300, precision = 1)
    public int interval = 30;

    @Override
    public void onSettingsSaved() {
        CoreModule.getCoreModule().update();
    }

}
