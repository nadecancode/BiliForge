package cn.charlotte.biliforge.overlay;

import cn.charlotte.biliforge.BiliForge;
import cn.charlotte.biliforge.util.render.SmartFontRenderer;
import cn.charlotte.biliforge.util.render.colors.CustomColor;
import lombok.Data;
import lombok.Getter;

@Data
public abstract class Overlay {

    private int x, y;
    private int sizeX, sizeY;

    public Overlay(int x, int y) {
        this.x = x;
        this.y = y;
    }

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
