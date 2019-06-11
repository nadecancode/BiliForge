package cn.charlotte.biliforge.module.impl.fans.settings;

import cn.charlotte.biliforge.settings.SettingInfo;
import cn.charlotte.biliforge.settings.SettingsRegistry;
import cn.charlotte.biliforge.util.render.colors.CommonColors;
import cn.charlotte.biliforge.util.render.colors.CustomColor;

@SettingInfo(name = "Fans")
public class FansModuleConfig {

    public static FansModuleConfig INSTANCE;

    public SettingsRegistry.SettingKeys<String> displayText = new SettingsRegistry.SettingKeys<>(
            "fans",
            "display",
            "Overlay Text",
            "你的游戏界面显示的粉丝数量信息, 使用 [fans] 作为变量来显示真实粉丝数",
            "欧尼酱大人有 [fans] 名可爱小萌豚呢~"
    );

    public SettingsRegistry.SettingKeys<CustomColor> overlayColor = new SettingsRegistry.SettingKeys<>(
            "fans",
            "color",
            "Overlay Color",
            "字体颜色",
            CommonColors.LIGHT_BLUE
    );

    public SettingsRegistry.SettingKeys<Boolean> chroma = new SettingsRegistry.SettingKeys<>(
            "fans",
            "chroma",
            "Chroma",
            "显示字体是否为彩虹色",
            false
    );

    public SettingsRegistry.SettingKeys<Boolean> shadow = new SettingsRegistry.SettingKeys<>(
            "fans",
            "shadow",
            "Shadow",
            "显示字体是否附带阴影",
            false
    );

    public SettingsRegistry.SettingKeys<Integer> positionX = new SettingsRegistry.SettingKeys<>(
            "fans.position",
            "positionX",
            "X轴位置",
            "信息显示位置的X轴",
            100
    );

    public SettingsRegistry.SettingKeys<Integer> positionY = new SettingsRegistry.SettingKeys<>(
            "fans.position",
            "positionY",
            "Y轴位置",
            "信息显示位置的Y轴",
            100
    );
}
