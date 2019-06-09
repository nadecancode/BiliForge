package cn.charlotte.biliforge.module.impl.fans;

import cn.charlotte.biliforge.instance.Module;
import cn.charlotte.biliforge.interfaces.annotations.ModuleInfo;
import cn.charlotte.biliforge.module.impl.fans.overlay.FansOverlay;
import cn.charlotte.biliforge.module.impl.fans.settings.FansModuleConfig;
import cn.charlotte.biliforge.util.mouse.Priority;
import lombok.Getter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@ModuleInfo(name = "fans", displayName = "Fans Overlay")
@Getter
public class FansModule extends Module {

    @Getter
    private static FansModule fansModule;

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void onEnable() {
        fansModule = this;
        this.registerSettings(FansModuleConfig.class);
        this.registerOverlay(new FansOverlay(), Priority.NORMAL);
    }
}
