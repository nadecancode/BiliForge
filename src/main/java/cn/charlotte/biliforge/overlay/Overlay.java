package cn.charlotte.biliforge.overlay;

import cn.charlotte.biliforge.BiliForge;
import cn.charlotte.biliforge.util.render.SmartFontRenderer;
import cn.charlotte.biliforge.util.render.colors.CustomColor;
import lombok.Getter;

public abstract class Overlay {

    @Getter
    private static SmartFontRenderer fontRenderer = null;

    public static void refresh() {
        if (fontRenderer == null) {
            try {
                fontRenderer = new SmartFontRenderer();
            } catch (Exception ignored) {

            } finally {
                fontRenderer.onResourceManagerReload(BiliForge.getInstance().getMinecraft().getResourceManager());
            }
        }
    }

    public float drawString(String text, float x, float y, CustomColor customColor, SmartFontRenderer.TextAlignment alignment, SmartFontRenderer.TextShadow shadow) {
        return fontRenderer.drawString(text, x, y, customColor, alignment, shadow);
    }
}
