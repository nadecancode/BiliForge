package cn.charlotte.biliforge.module.impl.fans.settings;

import cn.charlotte.biliforge.module.impl.core.CoreModule;
import cn.charlotte.biliforge.settings.annotations.Setting;
import cn.charlotte.biliforge.settings.annotations.SettingsInfo;
import cn.charlotte.biliforge.settings.instances.SettingsClass;
import cn.charlotte.biliforge.util.render.colors.CommonColors;
import cn.charlotte.biliforge.util.render.colors.CustomColor;

@SettingsInfo(name = "fans", displayPath = "FansOverlay")
public class FansModuleConfig extends SettingsClass {

    public static FansModuleConfig INSTANCE;

    @Setting(displayName = "Overlay Text", description = "你的游戏界面显示的粉丝数量信息, 使用 [fans] 作为变量来显示真实粉丝数")
    public String displayText = "欧尼酱大人有 [fans] 名可爱小萌豚呢~";

    @Setting(displayName = "Overlay Color", description = "字体颜色")
    public CustomColor overlayColor = CommonColors.LIGHT_BLUE;

    @Setting(displayName = "Chroma", description = "显示字体是否为彩虹色")
    public boolean chroma = false;

    @Setting(displayName = "Shadow", description = "显示字体是否附带阴影")
    public boolean shadow = false;

    @Override
    public void onSettingsSaved() {
        CoreModule.getCoreModule().update();
    }
}
