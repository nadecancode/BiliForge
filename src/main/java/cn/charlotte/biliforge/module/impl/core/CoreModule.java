package cn.charlotte.biliforge.module.impl.core;

import cn.charlotte.biliforge.instance.Module;
import cn.charlotte.biliforge.interfaces.annotations.ModuleInfo;
import cn.charlotte.biliforge.module.impl.core.settings.CoreModuleConfig;
import cn.charlotte.biliforge.module.impl.core.thread.RetrieveFansThread;
import cn.charlotte.biliforge.wrapper.bilibili.account.BilibiliAccountInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@ModuleInfo(name = "core", displayName = "Core")
@Getter
public class CoreModule extends Module {

    @Getter
    private static CoreModule coreModule;

    @Setter
    private volatile BilibiliAccountInfo bilibiliAccountInfo;

    private ScheduledExecutorService EXECUTOR_SERVICE = Executors.newScheduledThreadPool(5);

    private RetrieveFansThread retrieveFansThread;

    @Override
    public void onEnable() {
        coreModule = this;
        this.registerSettings(CoreModuleConfig.class);
        this.update();
    }

    @SneakyThrows
    public void update() {
        if (this.retrieveFansThread != null) this.retrieveFansThread.stop();
        this.retrieveFansThread = new RetrieveFansThread();
        this.retrieveFansThread.start();
    }
}
