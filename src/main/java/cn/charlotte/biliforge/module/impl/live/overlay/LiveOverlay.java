package cn.charlotte.biliforge.module.impl.live.overlay;

import cn.charlotte.biliforge.color.ChatColor;
import cn.charlotte.biliforge.module.impl.live.LiveModule;
import cn.charlotte.biliforge.module.impl.live.object.DanMu;
import cn.charlotte.biliforge.module.impl.live.object.Gift;
import cn.charlotte.biliforge.module.impl.live.object.Live;
import cn.charlotte.biliforge.module.impl.live.object.LiveObject;
import cn.charlotte.biliforge.module.impl.live.settings.LiveModuleConfig;
import cn.charlotte.biliforge.util.overlay.Overlay;
import cn.charlotte.biliforge.util.render.SmartFontRenderer;
import cn.charlotte.biliforge.util.render.colors.CommonColors;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class LiveOverlay extends Overlay {

    public LiveOverlay() {
        super("Live", 30, 50, true, 0, 0, 10, 10, OverlayGrowFrom.MIDDLE_LEFT);
    }

    @Override
    public void render(RenderGameOverlayEvent event) {
        if (!LiveModuleConfig.INSTANCE.live) return;

        if (event.type == RenderGameOverlayEvent.ElementType.TEXT && !event.isCancelable()) {
            this.drawOverlay();
        }
    }

    private void drawOverlay() {
        int currentY = 0;
        String topFormat = LiveModuleConfig.INSTANCE.topDescription.replace("{popularity}", Integer.toString(LiveModule.getLiveModule().getLive().getWatching()));
        if (LiveModule.getLiveModule().getLive().getLiveStatus() == Live.LiveStatus.ERROR) {
            topFormat = "直播间信息获取错误";
        } else if (LiveModule.getLiveModule().getLive().getLiveStatus() == Live.LiveStatus.WAITING) {
            topFormat = "等待获取直播间信息中...";
        }

        drawString(ChatColor.translateAlternateColorCodes('&', topFormat), position.getDrawingX() + (staticSize.x / 2), position.getDrawingY() + (staticSize.y / 2) - 24, CommonColors.WHITE, SmartFontRenderer.TextAlignment.LEFT_RIGHT, SmartFontRenderer.TextShadow.NORMAL);
        for (LiveObject liveObject : LiveModule.getLiveModule().getLive().getLiveObjects()) {
            String display = null;
            if (liveObject instanceof DanMu) {
                DanMu danMu = (DanMu) liveObject;
                display = ChatColor.translateAlternateColorCodes('&', LiveModuleConfig.INSTANCE.danMuFormat
                        .replace("{name}", danMu.getUsername())
                        .replace("{content}", danMu.getMessage()))
                        .replace("{rank}", danMu.getUserRank())
                        .replace("{level}", danMu.getUserLevel());
            } else if (liveObject instanceof Gift) {
                Gift gift = (Gift) liveObject;
                display = ChatColor.translateAlternateColorCodes('&', LiveModuleConfig.INSTANCE.giftFormat
                        .replace("{name}", gift.getUsername())
                        .replace("{gift}", gift.getGiftName())
                        .replace("{uid}", gift.getUid())
                        .replace("{count}", Integer.toString(gift.getCount()))
                        .replace("{price}", Integer.toString(gift.getPrice())));
            }

            if (display != null) {
                drawString(display, position.getDrawingX() + (staticSize.x / 2), position.getDrawingY() + (staticSize.y / 2) - 4 - 10 + currentY, CommonColors.WHITE, SmartFontRenderer.TextAlignment.LEFT_RIGHT, SmartFontRenderer.TextShadow.NORMAL);
                currentY += 10;
            }
        }
    }
}
