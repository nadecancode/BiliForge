package cn.charlotte.biliforge.module.impl.core.settings;

import cn.charlotte.biliforge.module.impl.core.CoreModule;
import cn.charlotte.biliforge.settings.SettingsRegistry;

public class CoreModuleConfig {

    public static CoreModuleConfig INSTANCE;

    public SettingsRegistry.SettingKeys<String> uid = new SettingsRegistry.SettingKeys<String>(
            "core",
            "uid",
            "Bilibili UID",
            "你的 Bilibili 用户UID (不是名称)",
            "22980940"
    ) {
        @Override
        public void onChanged() {
            CoreModule.getCoreModule().update();
        }
    };


    public SettingsRegistry.SettingKeys<Integer> interval = new SettingsRegistry.SettingKeys<Integer>(
            "core",
            "interval",
            "Refresh Interval",
            "从 Bilibili API 发送请求的时间间隔 (单位: 秒)",
            30
    ) {
        @Override
        public void onChanged() {
            CoreModule.getCoreModule().update();
        }
    };

}
