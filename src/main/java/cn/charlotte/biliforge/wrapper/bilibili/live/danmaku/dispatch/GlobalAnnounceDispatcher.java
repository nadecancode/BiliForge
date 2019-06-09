package cn.charlotte.biliforge.wrapper.bilibili.live.danmaku.dispatch;

import cn.charlotte.biliforge.wrapper.bilibili.live.Globals;
import cn.charlotte.biliforge.wrapper.bilibili.live.danmaku.datamodel.GlobalAnnounceInfo;
import cn.charlotte.biliforge.wrapper.bilibili.live.danmaku.event.DanmakuEvent;
import cn.charlotte.biliforge.wrapper.bilibili.live.danmaku.event.DanmakuListener;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 分发公告的分发器。对COMMAND为'SYS_MSG'的事件有效。
 *
 * @author Charlie Jiang
 * @since rv1
 */
public class GlobalAnnounceDispatcher extends AbstractJSONDispatcher {
    public static final String[] ACCEPTABLE_COMMANDS = {
            "SYS_MSG"
    };
    private static GlobalAnnounceDispatcher GLOBAL_INSTANCE;

    @NotNull
    public static GlobalAnnounceDispatcher getGlobalInstance() {
        if (GLOBAL_INSTANCE == null) {
            GLOBAL_INSTANCE = new GlobalAnnounceDispatcher();
        }
        return GLOBAL_INSTANCE;
    }

    @Override
    protected String[] getAcceptableCommands() {
        return ACCEPTABLE_COMMANDS;
    }

    /**
     * {@inheritDoc}
     *
     * @param listeners  监听器列表
     * @param rootObject 事件内容产生的JSON对象结构
     * @param source     事件发生源
     */
    @Override
    public void dispatch(List<DanmakuListener> listeners, JsonObject rootObject, Object source) {
        if (rootObject.has("tv_id")) return; //SMALL TV.

        GlobalAnnounceInfo announce = Globals.get().gson()
                .fromJson(rootObject, GlobalAnnounceInfo.class);
        DanmakuEvent event = new DanmakuEvent(source, announce, DanmakuEvent.Kind.GLOBAL_ANNOUNCE);
        for (DanmakuListener listener : listeners) {
            listener.globalAnnounceEvent(event);
        }
    }
}
