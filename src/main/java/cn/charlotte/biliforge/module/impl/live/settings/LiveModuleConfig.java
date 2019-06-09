package cn.charlotte.biliforge.module.impl.live.settings;

import cn.charlotte.biliforge.module.impl.live.LiveModule;
import cn.charlotte.biliforge.settings.annotations.Setting;
import cn.charlotte.biliforge.settings.annotations.SettingsInfo;
import cn.charlotte.biliforge.settings.instances.SettingsClass;

@SettingsInfo(name = "Live", displayPath = "Live")
public class LiveModuleConfig extends SettingsClass {

    public static LiveModuleConfig INSTANCE;

    @Setting(displayName = "Enabled", description = "是否开启直播模式")
    public boolean live = false;

    @Setting(displayName = "Room ID", description = "Bilibili 直播房间ID")
    public String roomId = "123456";

    @Setting(displayName = "Top Description", description = "在所有直播弹幕上方显示的信息, 使用 {popularity} 来替换直播间人气")
    public String topDescription = "&7直播间弹幕如下 (观看: &f{popularity}&7)";

    @Setting(displayName = "DanMu Format", description = "弹幕的格式, {name} 为用户名称, {content} 为弹幕内容, {level} 为用户守护等级")
    public String danMuFormat = "&7{level}{name}&f {content}";

    @Setting(displayName = "Gift Format", description = "送礼物的格式, {name} 为用户名称, {uid} 为用户UID, {gift} 为礼物名称, {count} 为礼物数量, {price} 为礼物价格")
    public String giftFormat = "&f{name}&7为欧尼酱大人送出了 &f{gift} &7x&f{count}&f哟";

    @Override
    public void onSettingsSaved() {
        if (this.live) {
            LiveModule.getLiveModule().update();
        }
    }
}
