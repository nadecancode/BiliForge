/*
 *  * Copyright © Wynntils - 2019.
 */

package cn.charlotte.biliforge.util.ui;

import cn.charlotte.biliforge.util.Position;
import cn.charlotte.biliforge.util.render.ScreenRenderer;
import lombok.ToString;

@ToString
public abstract class UIElement extends ScreenRenderer {
    private static int topID = Integer.MIN_VALUE;
    public Position position = new Position();
    public boolean visible = true;
    private int id;

    public UIElement(float anchorX, float anchorY, int offsetX, int offsetY) {
        this.id = topID++;
        position.anchorX = anchorX;
        position.anchorY = anchorY;
        position.offsetX = offsetX;
        position.offsetY = offsetY;
    }

    public abstract void render(int mouseX, int mouseY);

    public abstract void tick(long ticks);

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UIElement && ((UIElement) obj).getId() == this.id;
    }
}
