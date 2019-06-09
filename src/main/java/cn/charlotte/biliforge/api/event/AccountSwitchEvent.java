package cn.charlotte.biliforge.api.event;

import cn.charlotte.biliforge.wrapper.bilibili.account.BilibiliAccountInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraftforge.fml.common.eventhandler.Event;

@Data
@AllArgsConstructor
public class AccountSwitchEvent extends Event {
    private BilibiliAccountInfo oldAccount, newAccount;
}
