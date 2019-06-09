package cn.charlotte.biliforge.module.impl.live;

import cn.charlotte.biliforge.instance.Module;
import cn.charlotte.biliforge.interfaces.annotations.ModuleInfo;
import cn.charlotte.biliforge.module.impl.live.listener.LiveListener;
import cn.charlotte.biliforge.module.impl.live.object.Live;
import cn.charlotte.biliforge.module.impl.live.overlay.LiveOverlay;
import cn.charlotte.biliforge.module.impl.live.settings.LiveModuleConfig;
import cn.charlotte.biliforge.util.mouse.Priority;
import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@ModuleInfo(name = "live", displayName = "Live")
@Getter
public class LiveModule extends Module {

    @Getter
    private static LiveModule liveModule;

    private ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private Future futureTask;
    private volatile Live live;
    private LiveListener liveListener;

    @Override
    public void onEnable() {
        liveModule = this;
        this.registerSettings(LiveModuleConfig.class);
        this.registerOverlay(new LiveOverlay(), Priority.NORMAL);
        this.live = new Live();
    }

    @Override
    public void postEnable() {
        this.update();
    }

    public void update() {
        if (this.futureTask != null) this.futureTask.cancel(true);
        this.liveListener = new LiveListener(LiveModuleConfig.INSTANCE.roomId);
        this.live.reset();
        this.liveListener.disconnect();
        this.futureTask = this.executorService.submit(liveListener);
    }
}
