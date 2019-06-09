/*
 *  * Copyright © Wynntils - 2019.
 */

package cn.charlotte.biliforge.util.ui.elements;

import cn.charlotte.biliforge.util.mouse.MouseButton;
import cn.charlotte.biliforge.util.ui.UI;
import cn.charlotte.biliforge.util.ui.UIElement;

import java.util.function.BiConsumer;

public class UIEClickZone extends UIElement {
    public int width, height;
    public boolean active;
    protected BiConsumer<UI, MouseButton> onClick;
    protected boolean hovering = false;

    public UIEClickZone(float anchorX, float anchorY, int offsetX, int offsetY, int width, int height, boolean active, BiConsumer<UI, MouseButton> onClick) {
        super(anchorX, anchorY, offsetX, offsetY);
        this.onClick = onClick;
        this.width = width;
        this.height = height;
        this.active = active;
    }

    public boolean isHovering() {
        return hovering;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        hovering = mouseX >= position.getDrawingX() && mouseX < position.getDrawingX() + width && mouseY >= position.getDrawingY() && mouseY < position.getDrawingY() + height;
    }

    @Override
    public void tick(long ticks) {

    }

    public void click(boolean hovering, MouseButton button, UI ui) {
        if (active && hovering) {
            if (onClick != null)
                onClick.accept(ui, button);
        }
    }

    public void click(int mouseX, int mouseY, MouseButton button, UI ui) {
        hovering = mouseX >= position.getDrawingX() && mouseX <= position.getDrawingX() + width && mouseY >= position.getDrawingY() && mouseY <= position.getDrawingY() + height;
        if (active && hovering) {
            if (onClick != null)
                onClick.accept(ui, button);
        }
    }

    public void release(int mouseX, int mouseY, MouseButton button, UI ui) {
        hovering = mouseX >= position.getDrawingX() && mouseX <= position.getDrawingX() + width && mouseY >= position.getDrawingY() && mouseY <= position.getDrawingY() + height;
    }

    public void clickMove(int mouseX, int mouseY, MouseButton button, long timeSinceLastClick, UI ui) {
        hovering = mouseX >= position.getDrawingX() && mouseX <= position.getDrawingX() + width && mouseY >= position.getDrawingY() && mouseY <= position.getDrawingY() + height;
    }
}
