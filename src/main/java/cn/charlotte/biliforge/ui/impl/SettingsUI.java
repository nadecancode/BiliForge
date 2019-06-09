package cn.charlotte.biliforge.ui.impl;

import cn.charlotte.biliforge.settings.SettingsRegistry;
import cn.charlotte.biliforge.util.render.colors.CommonColors;
import cn.charlotte.biliforge.util.render.colors.CustomColor;
import com.google.common.collect.Lists;
import de.johni0702.minecraft.gui.container.AbstractGuiScreen;
import de.johni0702.minecraft.gui.container.GuiPanel;
import de.johni0702.minecraft.gui.element.*;
import de.johni0702.minecraft.gui.element.advanced.GuiColorPicker;
import de.johni0702.minecraft.gui.layout.CustomLayout;
import de.johni0702.minecraft.gui.layout.HorizontalLayout;
import de.johni0702.minecraft.gui.layout.VerticalLayout;

import java.util.List;

public class SettingsUI extends AbstractGuiScreen<SettingsUI> {

    public SettingsUI(net.minecraft.client.gui.GuiScreen parent, SettingsRegistry settingsRegistry) {
        final GuiButton doneButton = new GuiButton(this).setLabel("应用").setSize(200, 20).onClick(() -> {
            settingsRegistry.save();
            getMinecraft().displayGuiScreen(parent);
        });

        final GuiPanel allElements = new GuiPanel(this).setLayout(new HorizontalLayout().setSpacing(10));
        GuiPanel leftColumn = new GuiPanel().setLayout(new VerticalLayout().setSpacing(4));
        GuiPanel rightColumn = new GuiPanel().setLayout(new VerticalLayout().setSpacing(4));
        allElements.addElements(new VerticalLayout.Data(0), leftColumn, rightColumn);
        HorizontalLayout.Data leftHorizontalData = new HorizontalLayout.Data(1);
        HorizontalLayout.Data rightHorizontalData = new HorizontalLayout.Data(0);

        int i = 0;
        for (final SettingsRegistry.SettingKey key : settingsRegistry.getSettings()) {
            if (key.getDisplayString() != null) {
                List<GuiElement<?>> elements = Lists.newArrayList();
                if (key.getDefault() instanceof Boolean) {
                    @SuppressWarnings("unchecked") final SettingsRegistry.SettingKey<Boolean> booleanKey = (SettingsRegistry.SettingKey<Boolean>) key;
                    final GuiToggleButton button = new GuiToggleButton<>().setSize(150, 20)
                            .setLabel(key.getDisplayString()).setTooltip(new GuiTextField().setText(booleanKey.getDescription())).setSelected(settingsRegistry.get(booleanKey) ? 0 : 1)
                            .setValues("开启", "关闭");
                    elements.add(button.onClick(() -> settingsRegistry.set(booleanKey, button.getSelected() == 0)));
                } else if (key.getDefault() instanceof String) {
                    final SettingsRegistry.SettingKey<String> stringKey = (SettingsRegistry.SettingKey<String>) key;
                    String currentValue = settingsRegistry.get(stringKey);
                    elements.add(new GuiLabel().setText(stringKey.getDisplayString()));
                    elements.add(
                            new GuiPanel()
                                    .setLayout(new HorizontalLayout().setSpacing(2))
                                    .addElements(new HorizontalLayout.Data(0.5), newGuiTextField()
                                            .setText(currentValue)
                                            .setTooltip(new GuiTextField().setText(stringKey.getDescription()))
                                            .onTextChanged(text -> settingsRegistry.set(stringKey, text))
                                    )
                    );
                } else if (key.getDefault() instanceof Integer) {
                    final SettingsRegistry.SettingKey<Integer> integerKey = (SettingsRegistry.SettingKey<Integer>) key;
                    Integer currentValue = settingsRegistry.get(integerKey);
                    elements.add(new GuiLabel().setText(integerKey.getDisplayString()));
                    elements.add(
                            new GuiPanel()
                                    .setLayout(new HorizontalLayout().setSpacing(2))
                                    .addElements(new HorizontalLayout.Data(0.5), newGuiNumberField()
                                            .setValue(currentValue)
                                            .setTooltip(new GuiTextField().setText(integerKey.getDescription()))
                                            .onTextChanged(text -> settingsRegistry.set(integerKey, Integer.parseInt(text))))
                    );
                } else if (key.getDefault() instanceof Long) {
                    final SettingsRegistry.SettingKey<Long> longKey = (SettingsRegistry.SettingKey<Long>) key;
                    Long currentValue = settingsRegistry.get(longKey);
                    elements.add(new GuiLabel().setText(longKey.getDisplayString()));
                    elements.add(
                            new GuiPanel()
                                    .setLayout(new HorizontalLayout().setSpacing(2))
                                    .addElements(new HorizontalLayout.Data(0.5), newGuiNumberField()
                                            .setValue(currentValue)
                                            .setTooltip(new GuiTextField().setText(longKey.getDescription()))
                                            .onTextChanged(text -> settingsRegistry.set(longKey, Long.parseLong(text))))
                    );
                } else if (key.getDefault() instanceof Float) {
                    final SettingsRegistry.SettingKey<Float> floatKey = (SettingsRegistry.SettingKey<Float>) key;
                    Float currentValue = settingsRegistry.get(floatKey);
                    elements.add(new GuiLabel().setText(floatKey.getDisplayString()));
                    elements.add(
                            new GuiPanel()
                                    .setLayout(new HorizontalLayout().setSpacing(2))
                                    .addElements(new HorizontalLayout.Data(0.5), newGuiNumberField()
                                            .setValue(currentValue)
                                            .setTooltip(new GuiTextField().setText(floatKey.getDescription()))
                                            .onTextChanged(text -> settingsRegistry.set(floatKey, Float.parseFloat(text))))
                    );
                } else if (key.getDefault() instanceof Double) {
                    final SettingsRegistry.SettingKey<Double> doubleKey = (SettingsRegistry.SettingKey<Double>) key;
                    Double currentValue = settingsRegistry.get(doubleKey);
                    elements.add(new GuiLabel().setText(doubleKey.getDisplayString()));
                    elements.add(
                            new GuiPanel()
                                    .setLayout(new HorizontalLayout().setSpacing(2))
                                    .addElements(new HorizontalLayout.Data(0.5), newGuiNumberField()
                                            .setValue(currentValue)
                                            .setTooltip(new GuiTextField().setText(doubleKey.getDescription()))
                                            .onTextChanged(text -> settingsRegistry.set(doubleKey, Double.parseDouble(text))))
                    );
                } else if (key.getDefault() instanceof CustomColor) {
                    final SettingsRegistry.SettingKey<CustomColor> customColorKey = (SettingsRegistry.SettingKey<CustomColor>) key;
                    CustomColor currentValue = settingsRegistry.get(customColorKey);
                    elements.add(new GuiLabel().setText(customColorKey.getDisplayString()));
                    elements.add(
                            new GuiPanel()
                                    .setLayout(new HorizontalLayout().setSpacing(2))
                                    .addElements(new HorizontalLayout.Data(0.5),
                                            new GuiColorPicker(this)
                                                    .setColor(currentValue)
                                                    .setTooltip(new GuiTextField().setText(customColorKey.getDescription()))
                                                    .onSelection(color -> settingsRegistry.set(customColorKey, color))
                                    )
                    );
                } else {
                    throw new IllegalArgumentException("Type " + key.getDefault().getClass() + " not supported.");
                }

                if (i++ % 2 == 0) {
                    leftColumn.addElements(leftHorizontalData, elements.toArray(new GuiElement<?>[]{}));
                } else {
                    rightColumn.addElements(rightHorizontalData, elements.toArray(new GuiElement<?>[]{}));
                }
            }
        }

        setLayout(new CustomLayout<SettingsUI>() {
            @Override
            protected void layout(SettingsUI container, int width, int height) {
                pos(allElements, width / 2 - 155, height / 6);
                pos(doneButton, width / 2 - 100, height - 27);
            }
        });

        setTitle(new GuiLabel().setText("BiliForge Settings").setColor(CommonColors.CYAN));
    }

    @Override
    protected SettingsUI getThis() {
        return this;
    }

    private static GuiNumberField newGuiNumberField() {
        return new GuiNumberField().setMaxLength(2).setSize(20, 20).setValidateOnFocusChange(true);
    }

    private static GuiTextField newGuiTextField() {
        return new GuiTextField().setMaxLength(2).setSize(20, 20);
    }
}
