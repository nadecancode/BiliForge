package cn.charlotte.biliforge.module.impl.live.listener;


import cn.charlotte.biliforge.BiliForge;
import cn.charlotte.biliforge.module.impl.live.LiveModule;
import cn.charlotte.biliforge.module.impl.live.object.DanMu;
import cn.charlotte.biliforge.module.impl.live.object.Gift;
import cn.charlotte.biliforge.module.impl.live.object.Live;
import cn.charlotte.biliforge.module.impl.live.settings.LiveModuleConfig;
import cn.charlotte.biliforge.wrapper.bilibili.live.danmaku.DanmakuReceiver;
import cn.charlotte.biliforge.wrapper.bilibili.live.danmaku.datamodel.Danmaku;
import cn.charlotte.biliforge.wrapper.bilibili.live.danmaku.datamodel.GiveGiftInfo;
import cn.charlotte.biliforge.wrapper.bilibili.live.danmaku.dispatch.DanmakuDispatcher;
import cn.charlotte.biliforge.wrapper.bilibili.live.danmaku.event.DanmakuAdapter;
import cn.charlotte.biliforge.wrapper.bilibili.live.danmaku.event.DanmakuEvent;
import cn.charlotte.biliforge.wrapper.bilibili.live.room.Room;
import org.jetbrains.annotations.NotNull;

public class LiveListener extends DanmakuAdapter implements Runnable {

    private String roomUrlId;
    private DanmakuReceiver receiver;

    public LiveListener(String roomUrlId) {
        this.roomUrlId = roomUrlId;
    }

    @Override
    public void run() {
        if (!LiveModuleConfig.INSTANCE.live) return;
        try {
            if (receiver != null) receiver.disconnect();
        } catch (Exception ex) { /* Just ignore that */ }
        try {
            receiver = new DanmakuReceiver(Room.getRealRoomID(Integer.parseInt(roomUrlId)));
            receiver.getDispatchManager().registerDispatcher(new DanmakuDispatcher());
            receiver.addDanmakuListener(this);
            receiver.connect();
        } catch (Exception e) {
            BiliForge.getInstance().sendMessage("[BiliForge] 连接失败。" + e);
        }
    }

    @Override
    public void danmakuEvent(@NotNull DanmakuEvent event) {
        Danmaku danmaku = (Danmaku) event.getParam();
        DanMu danMu = new DanMu(danmaku.getUser().getName(), danmaku.getContent(), danmaku.getUser().getTitle(), Integer.toString(danmaku.getUser().getLevel()));
        LiveModule.getLiveModule().getLive().addLiveObject(danMu);
    }

    @Override
    public void watcherCountEvent(@NotNull DanmakuEvent event) {
        LiveModule.getLiveModule().getLive().setWatching((Integer) event.getParam());
    }

    @Override
    public void errorEvent(@NotNull DanmakuEvent event) {
        LiveModule.getLiveModule().getLive().setLiveStatus(Live.LiveStatus.ERROR);
    }

    @Override
    public void statusEvent(@NotNull DanmakuEvent event) {
        if (event.getKind().equals(DanmakuEvent.Kind.JOINED)) {
            LiveModule.getLiveModule().getLive().setLiveStatus(Live.LiveStatus.CONNECTED);
        }
    }

    @Override
    public void giveGiftEvent(@NotNull DanmakuEvent event) {
        GiveGiftInfo giveGiftInfo = (GiveGiftInfo) event.getParam();
        Gift gift = new Gift(giveGiftInfo.getContent().getUsername(), giveGiftInfo.getContent().getGiftName(), Integer.toString(giveGiftInfo.getContent().getUid()), giveGiftInfo.getContent().getCount(), giveGiftInfo.getContent().getPrice());
        LiveModule.getLiveModule().getLive().addLiveObject(gift);
    }
}
