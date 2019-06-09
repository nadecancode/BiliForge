/*
 *  * Copyright © Wynntils - 2019.
 */

package cn.charlotte.biliforge.util.ui.elements;

import cn.charlotte.biliforge.util.mouse.MouseButton;
import cn.charlotte.biliforge.util.render.ScreenRenderer;
import cn.charlotte.biliforge.util.render.SmartFontRenderer;
import cn.charlotte.biliforge.util.ui.UI;
import net.minecraft.client.gui.GuiTextField;

import java.util.function.BiConsumer;

public class UIETextBox extends UIEClickZone {
    public GuiTextField textField;
    public boolean textDisappearsOnNextClick;
    public BiConsumer<UI, String> onTextChanged;

    public UIETextBox(float anchorX, float anchorY, int offsetX, int offsetY, int width, boolean active, String text, boolean textDisappearsOnNextClick, BiConsumer<UI, String> onTextChanged) {
        super(anchorX, anchorY, offsetX, offsetY, width, SmartFontRenderer.CHAR_HEIGHT, active, null);
        this.textField = new GuiTextField(this.getId(), ScreenRenderer.fontRenderer, this.position.getDrawingX(), this.position.getDrawingY(), width, 20);
        this.textField.setText(text);
        this.textDisappearsOnNextClick = textDisappearsOnNextClick;
        this.onTextChanged = onTextChanged;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        super.render(mouseX, mouseY);

        this.textField.xPosition = this.position.getDrawingX();
        this.textField.yPosition = this.position.getDrawingY();
        this.textField.setEnabled(active);
        this.textField.drawTextBox();
    }

    public void keyTyped(char c, int i, UI ui) {
        String old = textField.getText();
        this.textField.textboxKeyTyped(c, i);
        this.onTextChanged.accept(ui, old);
    }

    @Override
    public void tick(long ticks) {
        this.textField.updateCursorCounter();
    }

    @Override
    public void click(int mouseX, int mouseY, MouseButton button, UI ui) {
        this.textField.mouseClicked(mouseX, mouseY, button.ordinal());
        if (textDisappearsOnNextClick && (mouseX >= this.textField.xPosition && mouseX < this.textField.xPosition + this.textField.width && mouseY >= this.textField.yPosition && mouseY < this.textField.yPosition + this.textField.height) && button == MouseButton.LEFT) {
            textField.setText("");
            textDisappearsOnNextClick = false;
        }
    }

    public void setColor(int color) {
        textField.setTextColor(color);
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String textIn) {
        textField.setText(textIn);
    }

    public void writeText(String textToWrite) {
        textField.writeText(textToWrite);
    }
}
