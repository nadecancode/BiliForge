package cn.charlotte.biliforge.module.impl.core.thread;

import cn.charlotte.biliforge.BiliForge;
import cn.charlotte.biliforge.api.event.AccountSwitchEvent;
import cn.charlotte.biliforge.color.ChatColor;
import cn.charlotte.biliforge.module.impl.core.CoreModule;
import cn.charlotte.biliforge.module.impl.core.settings.CoreModuleConfig;
import cn.charlotte.biliforge.wrapper.bilibili.BilibiliConnection;
import cn.charlotte.biliforge.wrapper.bilibili.account.BilibiliAccountInfo;
import lombok.SneakyThrows;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.concurrent.TimeUnit;

public class RetrieveFansThread extends Thread {

    @Override
    @SneakyThrows
    public void run() {
        while (true) {
            BilibiliAccountInfo original = CoreModule.getCoreModule().getBilibiliAccountInfo();
            if (!NumberUtils.isNumber(CoreModuleConfig.INSTANCE.uid)) {
                BiliForge.getInstance().sendMessage(ChatColor.RED + "UID必须全部是数, 我求求你看一下怎么用吧");
            } else {
                BilibiliAccountInfo bilibiliAccountInfo = new BilibiliConnection(Long.parseLong(CoreModuleConfig.INSTANCE.uid)).makeConnection();
                CoreModule.getCoreModule().setBilibiliAccountInfo(bilibiliAccountInfo);

                if (original != null && !bilibiliAccountInfo.getData().getCard().getUid().equals(original.getData().getCard().getUid())) {
                    AccountSwitchEvent event = new AccountSwitchEvent(original, bilibiliAccountInfo);
                    MinecraftForge.EVENT_BUS.post(event);
                    if (event.isCanceled()) {
                        CoreModule.getCoreModule().setBilibiliAccountInfo(original);
                        CoreModuleConfig.INSTANCE.uid = Long.toString(original.getData().getCard().getUid());
                    }
                }
            }

            Thread.sleep(TimeUnit.SECONDS.toMillis(CoreModuleConfig.INSTANCE.interval));
        }
    }

}
