package cn.charlotte.biliforge.module;

import cn.charlotte.biliforge.manager.FrameworkManager;
import cn.charlotte.biliforge.module.impl.core.CoreModule;
import cn.charlotte.biliforge.module.impl.fans.FansModule;
import cn.charlotte.biliforge.module.impl.live.LiveModule;

public class ModuleManager {

    public static void registerModules() {
        FrameworkManager.registerModule(new CoreModule());
        FrameworkManager.registerModule(new LiveModule());
        FrameworkManager.registerModule(new FansModule());
    }

}
