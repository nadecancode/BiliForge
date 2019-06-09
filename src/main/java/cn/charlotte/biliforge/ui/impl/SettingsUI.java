package cn.charlotte.biliforge.ui.impl;

import cn.charlotte.biliforge.instance.containers.ModuleContainer;
import cn.charlotte.biliforge.manager.FrameworkManager;
import cn.charlotte.biliforge.settings.SettingsContainer;
import cn.charlotte.biliforge.settings.SettingsRegistry;
import cn.charlotte.biliforge.util.overlay.Overlay;
import cn.charlotte.biliforge.util.render.colors.CommonColors;
import de.johni0702.minecraft.gui.container.AbstractGuiScreen;
import de.johni0702.minecraft.gui.container.GuiPanel;
import de.johni0702.minecraft.gui.element.*;
import de.johni0702.minecraft.gui.element.advanced.GuiTextArea;
import de.johni0702.minecraft.gui.layout.CustomLayout;
import de.johni0702.minecraft.gui.layout.HorizontalLayout;
import de.johni0702.minecraft.gui.layout.VerticalLayout;
import org.lwjgl.util.ReadableColor;

import java.util.*;

public class SettingsUI extends AbstractGuiScreen<SettingsUI> {

    private String currentSettingsPath = "";
    private Map<String, SettingsContainer> registeredSettings = new HashMap<>();
    private List<String> sortedSettings = new ArrayList<>();
    private HashSet<String> changedSettings = new HashSet<>();

    private GuiTexturedButton applyButton;

    public SettingsUI(net.minecraft.client.gui.GuiScreen parent, SettingsRegistry settingsRegistry) {
        for (ModuleContainer mcn : FrameworkManager.availableModules.values()) {
            for (SettingsContainer scn : mcn.getRegisteredSettings().values()) {
                if (!(scn.getHolder() instanceof Overlay)) {
                    if (!scn.getDisplayPath().equals("")) {
                        registeredSettings.put(scn.getDisplayPath(), scn);
                        sortedSettings.add(scn.getDisplayPath());
                    }
                }
            }
        }

        Collections.sort(sortedSettings);
        final GuiButton doneButton = new GuiButton(this).setI18nLabel("gui.done").setSize(200, 20).onClick(new Runnable() {
            @Override
            public void run() {
                getMinecraft().displayGuiScreen(parent);
            }
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
                GuiElement<?> element;
                if (key.getDefault() instanceof Boolean) {
                    @SuppressWarnings("unchecked") final SettingsRegistry.SettingKey<Boolean> booleanKey = (SettingsRegistry.SettingKey<Boolean>) key;
                    final GuiToggleButton button = new GuiToggleButton<>().setSize(150, 20)
                            .setLabel(key.getDisplayString()).setSelected(settingsRegistry.get(booleanKey) ? 0 : 1)
                            .setValues("开启", "关闭");
                    element = button.onClick(() -> {
                        settingsRegistry.set(booleanKey, button.getSelected() == 0);
                        settingsRegistry.save();
                    });
                } else if (key.getDefault() instanceof String) {
                    final SettingsRegistry.SettingKey<String> stringKey = (SettingsRegistry.SettingKey<String>) key;
                    element = new GuiTextArea()
                            .setText(new String[]{stringKey.getDisplayString()})
                            .setHint(stringKey.getDescription())
                            .setTextColor(CommonColors.WHITE);
                    element = new GuiTextField()
                            .setText(stringKey.getKey())
                            .setSize(200, 20)
                            .onTextChanged(text -> {
                                settingsRegistry.set(stringKey, text);
                                settingsRegistry.save();
                            });

                } else if (key.getDefault() instanceof Integer) {
                    final SettingsRegistry.SettingKey<Integer> integerKey = (SettingsRegistry.SettingKey<Integer>) key;
                    element = new GuiNumberField()
                            .setValidateOnFocusChange(true)
                            .setPrecision(1)
                            .onTextChanged(text -> {
                                settingsRegistry.set(integerKey, Integer.parseInt(integerKey.getKey()));
                                settingsRegistry.save();
                            });
                } else if (key.getDefault() instanceof Long) {
                    final SettingsRegistry.SettingKey<Long> longKey = (SettingsRegistry.SettingKey<Long>) key;
                    element = new GuiNumberField()
                            .setValidateOnFocusChange(true)
                            .setPrecision(1)
                            .onTextChanged(text -> {
                                settingsRegistry.set(longKey, Long.parseLong(longKey.getKey()));
                                settingsRegistry.save();
                            });
                } else if (key.getDefault() instanceof Float) {
                    final SettingsRegistry.SettingKey<Float> floatKey = (SettingsRegistry.SettingKey<Float>) key;
                    element = new GuiNumberField()
                            .setValidateOnFocusChange(true)
                            .setPrecision(1)
                            .onTextChanged(text -> {
                                settingsRegistry.set(floatKey, Float.parseFloat(floatKey.getKey()));
                                settingsRegistry.save();
                            });
                } else if (key.getDefault() instanceof Double) {
                    final SettingsRegistry.SettingKey<Double> doubleKey = (SettingsRegistry.SettingKey<Double>) key;
                    element = new GuiNumberField()
                            .setValidateOnFocusChange(true)
                            .setPrecision(1)
                            .onTextChanged(text -> {
                                settingsRegistry.set(doubleKey, Double.parseDouble(doubleKey.getKey()));
                                settingsRegistry.save();
                            });
                } else {
                    throw new IllegalArgumentException("Type " + key.getDefault().getClass() + " not supported.");
                }

                if (i++ % 2 == 0) {
                    leftColumn.addElements(leftHorizontalData, element);
                } else {
                    rightColumn.addElements(rightHorizontalData, element);
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

        setTitle(new GuiLabel().setText("BiliForge Settings").setColor(ReadableColor.CYAN));
    }

    @Override
    protected SettingsUI getThis() {
        return this;
    }
}
