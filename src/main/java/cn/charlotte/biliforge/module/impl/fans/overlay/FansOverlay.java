package cn.charlotte.biliforge.module.impl.fans.overlay;

import cn.charlotte.biliforge.module.impl.core.CoreModule;
import cn.charlotte.biliforge.module.impl.core.settings.CoreModuleConfig;
import cn.charlotte.biliforge.module.impl.fans.settings.FansModuleConfig;
import cn.charlotte.biliforge.util.overlay.Overlay;
import cn.charlotte.biliforge.util.render.SmartFontRenderer;
import cn.charlotte.biliforge.util.render.colors.CommonColors;
import cn.charlotte.biliforge.wrapper.bilibili.account.BilibiliAccountInfo;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.apache.commons.lang3.math.NumberUtils;

public class FansOverlay extends Overlay {

    public FansOverlay() {
        super("", 100, 50, true, 0, 0, 10, 10, OverlayGrowFrom.MIDDLE_CENTRE);
    }

    @Override
    public void render(RenderGameOverlayEvent event) {
        if (event.type == RenderGameOverlayEvent.ElementType.TEXT && !event.isCancelable()) {
            this.drawOverlay();
        }
    }

    private void drawOverlay() {
        if (!NumberUtils.isNumber(CoreModuleConfig.INSTANCE.uid)) {
            drawString("UID必须全是数字, 我求求你了看一下怎么用吧", 0, 0, CommonColors.LIGHT_BLUE, SmartFontRenderer.TextAlignment.LEFT_RIGHT, SmartFontRenderer.TextShadow.NONE);
            return;
        }

        if (CoreModule.getCoreModule().getBilibiliAccountInfo() == null) {
            drawString("无法获取粉丝数, 可能是服务器问题也可能是你输入的UID不正确.", 0, 0, CommonColors.LIGHT_BLUE, SmartFontRenderer.TextAlignment.LEFT_RIGHT, SmartFontRenderer.TextShadow.NONE);
            return;
        }

        BilibiliAccountInfo bilibiliAccountInfo = CoreModule.getCoreModule().getBilibiliAccountInfo();

        String display = FansModuleConfig.INSTANCE.displayText.replace("[fans]", Integer.toString(bilibiliAccountInfo.getData().getCard().getSubscribers()));
        if (display.contains("%next%")) {
            String[] multiLines = display.split("%next%");
            int currentY = 0;
            for (String line : multiLines) {
                drawString(line, 0, 10 + currentY, FansModuleConfig.INSTANCE.chroma ? CommonColors.RAINBOW : FansModuleConfig.INSTANCE.overlayColor, SmartFontRenderer.TextAlignment.MIDDLE, FansModuleConfig.INSTANCE.shadow ? SmartFontRenderer.TextShadow.OUTLINE : SmartFontRenderer.TextShadow.NORMAL);
                currentY += 10;
            }
        } else {
            drawString(display, 0, 0, FansModuleConfig.INSTANCE.chroma ? CommonColors.RAINBOW : FansModuleConfig.INSTANCE.overlayColor, SmartFontRenderer.TextAlignment.LEFT_RIGHT, FansModuleConfig.INSTANCE.shadow ? SmartFontRenderer.TextShadow.OUTLINE : SmartFontRenderer.TextShadow.NORMAL);
        }

    }
}