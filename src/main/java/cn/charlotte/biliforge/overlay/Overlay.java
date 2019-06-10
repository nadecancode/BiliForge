package cn.charlotte.biliforge.overlay;

import cn.charlotte.biliforge.util.render.SmartFontRenderer;
import cn.charlotte.biliforge.util.render.colors.CustomColor;

public abstract class Overlay {

    private SmartFontRenderer fontRenderer;

    public Overlay() {
        this.fontRenderer = new SmartFontRenderer();
    }

    public float drawString(String text, float x, float y, CustomColor customColor, SmartFontRenderer.TextAlignment alignment, SmartFontRenderer.TextShadow shadow) {
        return fontRenderer.drawString(text, x, y, customColor, alignment, shadow);
    }


}
