package cn.charlotte.biliforge.module.impl.live.settings;

import cn.charlotte.biliforge.BiliForge;
import cn.charlotte.biliforge.module.impl.live.LiveModule;
import cn.charlotte.biliforge.settings.SettingsRegistry;

public class LiveModuleConfig {

    public static LiveModuleConfig INSTANCE;

    public SettingsRegistry.SettingKeys<String> roomId = new SettingsRegistry.SettingKeys<String>("live",
            "roomId",
            "Room ID",
            "Bilibili 直播房间ID",
            "123456") {
        @Override
        public void onChanged() {
            if (BiliForge.getInstance().getSettingsRegistry().get(live)) {
                LiveModule.getLiveModule().update();
            }
        }
    };
    public SettingsRegistry.SettingKeys<Boolean> live = new SettingsRegistry.SettingKeys<Boolean>(
            "live",
            "enabled",
            "Live",
            "是否开启直播模式",
            false) {
        @Override
        public void onChanged() {
            if (BiliForge.getInstance().getSettingsRegistry().get(live)) {
                LiveModule.getLiveModule().update();
            }
        }
    };
    public SettingsRegistry.SettingKeys<String> topDescription = new SettingsRegistry.SettingKeys<>(
            "live",
            "topDescription",
            "Top Description",
            "在所有直播弹幕上方显示的信息, 使用 {popularity} 来替换直播间人气",
            "&7直播间弹幕如下 (观看: &f{popularity}&7)");

    public SettingsRegistry.SettingKeys<String> danMuFormat = new SettingsRegistry.SettingKeys<>("live",
            "danMuFormat",
            "FanMu Format",
            "弹幕的格式, {name} 为用户名称, {content} 为弹幕内容, {level} 为用户守护等级",
            "&7{level}{name}&f {content}");

    public SettingsRegistry.SettingKeys<String> giftFormat = new SettingsRegistry.SettingKeys<>(
            "live",
            "giftFormat",
            "Gift Format",
            "送礼物的格式, {name} 为用户名称, {uid} 为用户UID, {gift} 为礼物名称, {count} 为礼物数量, {price} 为礼物价格",
            "&f{name}&7为欧尼酱大人送出了 &f{gift} &7x&f{count}&f哟");
}
