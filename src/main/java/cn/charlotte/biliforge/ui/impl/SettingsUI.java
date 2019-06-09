package cn.charlotte.biliforge.ui.impl;

import cn.charlotte.biliforge.instance.containers.ModuleContainer;
import cn.charlotte.biliforge.manager.FrameworkManager;
import cn.charlotte.biliforge.settings.SettingsContainer;
import cn.charlotte.biliforge.util.overlay.Overlay;
import de.johni0702.minecraft.gui.container.AbstractGuiScreen;
import de.johni0702.minecraft.gui.container.GuiPanel;
import de.johni0702.minecraft.gui.element.GuiButton;
import de.johni0702.minecraft.gui.element.GuiLabel;
import de.johni0702.minecraft.gui.element.GuiTexturedButton;
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

    public SettingsUI(net.minecraft.client.gui.GuiScreen parent) {
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
        /*
        for (final SettingsRegistry.SettingKey<?> key : settingsRegistry.getSettings()) {
            if (key.getDisplayString() != null) {
                GuiElement<?> element;
                if (key.getDefault() instanceof Boolean) {
                    @SuppressWarnings("unchecked")
                    final SettingsRegistry.SettingKey<Boolean> booleanKey = (SettingsRegistry.SettingKey<Boolean>) key;
                    final GuiToggleButton button = new GuiToggleButton<>().setSize(150, 20)
                            .setLabel(key.getDisplayString()).setSelected(settingsRegistry.get(booleanKey) ? 0 : 1)
                            .setValues("开启", "关闭");
                    element = button.onClick(new Runnable() {
                        @Override
                        public void run() {
                            settingsRegistry.set(booleanKey, button.getSelected() == 0);
                            settingsRegistry.save();
                        }
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
        */
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
