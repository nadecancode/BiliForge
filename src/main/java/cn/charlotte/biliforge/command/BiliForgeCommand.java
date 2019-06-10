package cn.charlotte.biliforge.command;

import cn.charlotte.biliforge.BiliForge;
import cn.charlotte.biliforge.color.ChatColor;
import cn.charlotte.biliforge.ui.impl.SettingsUI;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;

public class BiliForgeCommand implements ICommand {

    @Override
    public String getCommandName() {
        return "biliforge";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "输入 /biliforge settings 打开配置菜单";
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("bilibiliforge", "bforge", "bf");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length <= 0) {
            sender.addChatMessage(wrapComponent(ChatColor.AQUA + "/biliforge settings - 打开配置菜单"));
            //sender.addChatMessage(wrapComponent(ChatColor.AQUA + "/biliforge overlay - 拖动修改界面布局"));
            return;
        }

        switch (args[0]) {
            case "settings":
                BiliForge.getInstance()
                        .getTimer()
                        .schedule(new TimerTask() {
                            @Override
                            public void run() {
                                SettingsUI ui = new SettingsUI(BiliForge.getInstance().getMinecraft().currentScreen, BiliForge.getInstance().getSettingsRegistry());
                                ui.display();
                            }
                        }, 50L);
                break;
                /*
            case "overlay":
                BiliForge.getInstance()
                        .getTimer()
                        .schedule(new TimerTask() {
                            @Override
                            public void run() {
                                OverlayPositionsUI ui = new OverlayPositionsUI(BiliForge.getInstance().getMinecraft().currentScreen);
                                ui.show();
                            }
                        }, 50L);
                break;

                 */
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }

    private ChatComponentText wrapComponent(String message) {
        return new ChatComponentText(message);
    }
}
