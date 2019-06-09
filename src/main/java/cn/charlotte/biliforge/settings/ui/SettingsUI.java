/*
 *  * Copyright © Wynntils - 2019.
 */

package cn.charlotte.biliforge.settings.ui;

import cn.charlotte.biliforge.instance.containers.ModuleContainer;
import cn.charlotte.biliforge.manager.FrameworkManager;
import cn.charlotte.biliforge.settings.SettingsContainer;
import cn.charlotte.biliforge.settings.annotations.Setting;
import cn.charlotte.biliforge.util.mouse.MouseButton;
import cn.charlotte.biliforge.util.overlay.Overlay;
import cn.charlotte.biliforge.util.render.ScreenRenderer;
import cn.charlotte.biliforge.util.render.SmartFontRenderer;
import cn.charlotte.biliforge.util.render.colors.CommonColors;
import cn.charlotte.biliforge.util.render.colors.CustomColor;
import cn.charlotte.biliforge.util.render.textures.Textures;
import cn.charlotte.biliforge.util.ui.UI;
import cn.charlotte.biliforge.util.ui.UIElement;
import cn.charlotte.biliforge.util.ui.elements.*;
import lombok.ToString;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class SettingsUI extends UI {
    public static final int settingHeight = 45;
    public UIEList holders = new UIEList(0.5f, 0.5f, -170, -87);
    public UIEList settings = new UIEList(0.5f, 0.5f, 5, -90);
    public UIESlider holdersScrollbar = new UIESlider.Vertical(null, Textures.UIs.button_scrollbar, 0.5f, 0.5f, -178, -88, 161, false, -85, 1, 1f, 0, null);
    public UIESlider settingsScrollbar = new UIESlider.Vertical(CommonColors.LIGHT_GRAY, Textures.UIs.button_scrollbar, 0.5f, 0.5f, 185, -100, 200, true, -95, -150, 1f, 0, null);
    public SettingsUI thisScreen = this;
    HashSet<String> changedSettings = new HashSet<>();
    private GuiScreen parentScreen;
    private String currentSettingsPath = "";
    private Map<String, SettingsContainer> registeredSettings = new HashMap<>();
    public UIEButton cancelButton = new UIEButton("取消", Textures.UIs.button_a, 0.5f, 0.5f, -170, 85, -10, true, (ui, mouseButton) -> {
        changedSettings.forEach(c -> {
            try {
                registeredSettings.get(c).tryToLoad();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        onClose();
    });

    public UIEButton applyButton = new UIEButton("应用", Textures.UIs.button_a, 0.5f, 0.5f, -120, 85, -10, true, (ui, mouseButton) -> {
        changedSettings.forEach(c -> {
            try {
                SettingsContainer settingsContainer = registeredSettings.get(c);

                settingsContainer.saveSettings();
                settingsContainer.getHolder().onSettingsSaved();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        onClose();
    });
    private List<String> sortedSettings = new ArrayList<>();

    public SettingsUI(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    @Override
    public void onInit() {
        this.holders.visible = false;
        this.settings.visible = false;

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
        holdersScrollbar.max = holdersScrollbar.min;
        for (String path : sortedSettings) {
            holders.add(new HolderButton(path));
            holdersScrollbar.max -= 11;
        }
        if (holdersScrollbar.min - holdersScrollbar.max > 160) {
            holders.position.offsetY = (int) holdersScrollbar.getValue();
            holdersScrollbar.active = true;
        } else {
            holders.position.offsetY = (int) holdersScrollbar.min;
            holdersScrollbar.active = false;
            holdersScrollbar.progress = 0f;
        }
        Mouse.setGrabbed(false);
        holdersScrollbar.max += 160;
    }

    @Override
    public void onClose() {
        mc.currentScreen = null;
        mc.displayGuiScreen(parentScreen);
    }

    @Override
    public void onTick() {

    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        if (settingsScrollbar.active) {
            float i = Mouse.getEventDWheel();
            if (i != 0) {
                i = MathHelper.clamp_float(i, -1, 1) * settingsScrollbar.precision * 8;

                if (mouseX >= screenWidth / 2 + 5 && mouseX < screenWidth / 2 + 185 && mouseY >= screenHeight / 2 - 100 && mouseY < screenHeight / 2 + 100) {
                    settingsScrollbar.setValue(settingsScrollbar.getValue() + i);
                }
            }
        }
        if (holdersScrollbar.active) {
            float i = Mouse.getEventDWheel();
            if (i != 0) {
                if (mouseX <= screenWidth / 2 - 5 && mouseX > screenWidth / 2 - 185 && mouseY >= screenHeight / 2 - 100 && mouseY < screenHeight / 2 + 100) {
                    i = MathHelper.clamp_float(i, -1, 1) * holdersScrollbar.precision * 8;
                    holdersScrollbar.setValue(holdersScrollbar.getValue() + i);
                }
            }
        }
    }

    @Override
    public void onRenderPreUIE(ScreenRenderer render) {
        drawDefaultBackground();
        CommonUIFeatures.drawBook();
        CommonUIFeatures.drawScrollArea();

        settings.position.offsetY = (int) settingsScrollbar.getValue();
        holders.position.offsetY = (int) holdersScrollbar.getValue();

        ScreenRenderer.createMask(Textures.Masks.full, screenWidth / 2 - 165, screenHeight / 2 - 88, screenWidth / 2 - 25, screenHeight / 2 + 73);
        holders.render(mouseX, mouseY);
        ScreenRenderer.clearMask();

        ScreenRenderer.createMask(Textures.Masks.full, screenWidth / 2 + 5, screenHeight / 2 - 100, screenWidth / 2 + 185, screenHeight / 2 + 100);
        settings.elements.forEach(setting -> {
            setting.position.anchorX = settings.position.anchorX;
            setting.position.anchorY = settings.position.anchorY;
            setting.position.offsetX += settings.position.offsetX;
            setting.position.offsetY += settings.position.offsetY;
            setting.position.refresh();
            if (setting.visible = setting.position.getDrawingY() < screenHeight / 2 + 100 && setting.position.getDrawingY() > screenHeight / 2 - 100 - settingHeight) {
                ((UIEList) setting).elements.forEach(settingElement -> {
                    settingElement.position.anchorX = settings.position.anchorX;
                    settingElement.position.anchorY = settings.position.anchorY;
                    settingElement.position.offsetX += setting.position.offsetX;
                    settingElement.position.offsetY += setting.position.offsetY;
                    settingElement.position.refresh();
                    settingElement.position.offsetX -= setting.position.offsetX;
                    settingElement.position.offsetY -= setting.position.offsetY;
                    settingElement.render(mouseX, mouseY);
                });
                if (setting != settings.elements.get(0))
                    render.drawRect(CommonColors.LIGHT_GRAY, setting.position.getDrawingX(), setting.position.getDrawingY() - 1, setting.position.getDrawingX() + 175, setting.position.getDrawingY());
                ScreenRenderer.scale(0.8f);
                render.drawString(((SettingElement) setting).info.displayName(), (setting.position.getDrawingX() + 33f) / 0.8f, (setting.position.getDrawingY() + 7) / 0.8f, CommonColors.BLACK, SmartFontRenderer.TextAlignment.LEFT_RIGHT, SmartFontRenderer.TextShadow.NONE);
                ScreenRenderer.resetScale();
            }
            setting.position.offsetX -= settings.position.offsetX;
            setting.position.offsetY -= settings.position.offsetY;
        });
        ScreenRenderer.clearMask();
    }

    @Override
    public void onRenderPostUIE(ScreenRenderer render) {
        ScreenRenderer.scale(0.7f);
        render.drawString(this.currentSettingsPath.replace('/', '>'), (screenWidth / 2f + 10) / 0.7f, (screenHeight / 2f - 106) / 0.7f, CommonColors.BLACK, SmartFontRenderer.TextAlignment.LEFT_RIGHT, SmartFontRenderer.TextShadow.NONE);
        ScreenRenderer.resetScale();
        settings.elements.forEach(setting -> {
            if (setting.visible && mouseX >= screenWidth / 2 + 5 && mouseX < screenWidth / 2 + 185 && mouseY > screenHeight / 2 - 100 && mouseY < screenHeight / 2 + 100 && mouseY >= setting.position.getDrawingY() && mouseY < setting.position.getDrawingY() + settingHeight) {
                List<String> lines = Arrays.asList(((SettingElement) setting).info.description().split("_nl"));
                this.drawHoveringText(lines, mouseX, mouseY, ScreenRenderer.fontRenderer);
            }
        });
    }

    @Override
    public void onWindowUpdate() {

    }

    public void setCurrentSettingsPath(String path) {
        currentSettingsPath = path;
        settings.elements.clear();
        settingsScrollbar.max = settingsScrollbar.min;
        try {
            List<Field> notSorted = new ArrayList<>(registeredSettings.get(path).getValues().keySet());
            List<Field> sorted = notSorted.stream().filter(c -> c.getAnnotation(Setting.class) != null && !c.getAnnotation(Setting.class).displayName().isEmpty()).sorted(Comparator.comparing(o -> o.getAnnotation(Setting.class).displayName())).sorted(Comparator.comparingInt(o -> o.getAnnotation(Setting.class).order())).collect(Collectors.toList());

            for (Field field : sorted) {
                try {
                    settings.add(new SettingElement(field));
                    settingsScrollbar.max -= settingHeight;
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                    //no @Setting
                }
            }
            if (settingsScrollbar.min - settingsScrollbar.max > 185) {
                settings.position.offsetY = (int) settingsScrollbar.getValue();
                settingsScrollbar.active = true;
            } else {
                settings.position.offsetY = (int) settingsScrollbar.min;
                settingsScrollbar.active = false;
                settingsScrollbar.progress = 0f;
            }
            settingsScrollbar.max += 185;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class HolderButton extends UIEButton {
        public String path;

        public HolderButton(String path) {
            super("", null, 0f, 0f, 0, 0, -1, true, null);
            String[] paths = path.split("/");
            this.height = 9;
            this.path = path;
            this.text = paths[paths.length - 1];
            this.position.offsetY = 11 * holders.elements.size();
            this.position.offsetX = 10 * paths.length;
        }

        @Override
        public void render(int mouseX, int mouseY) {
            if (!visible) return;
            hovering = mouseX >= position.getDrawingX() && mouseX < position.getDrawingX() + width && mouseY >= position.getDrawingY() && mouseY < position.getDrawingY() + height;
            active = !currentSettingsPath.equals(this.path);
            width = Math.max(this.setWidth < 0 ? (int) getStringWidth(text) - this.setWidth : this.setWidth, 0);

            if (!active) {
                drawString(text, this.position.getDrawingX() + width / 2f, this.position.getDrawingY() + height / 2f - 4f, TEXTCOLOR_NOTACTIVE, SmartFontRenderer.TextAlignment.MIDDLE, SmartFontRenderer.TextShadow.NORMAL);
            } else if (hovering) {
                drawString(text, this.position.getDrawingX() + width / 2f, this.position.getDrawingY() + height / 2f - 4f, TEXTCOLOR_HOVERING, SmartFontRenderer.TextAlignment.MIDDLE, SmartFontRenderer.TextShadow.NORMAL);
            } else {
                drawString(text, this.position.getDrawingX() + width / 2f, this.position.getDrawingY() + height / 2f - 4f, TEXTCOLOR_NORMAL, SmartFontRenderer.TextAlignment.MIDDLE, SmartFontRenderer.TextShadow.NORMAL);
            }
        }

        @Override
        public void click(int mouseX, int mouseY, MouseButton button, UI ui) {
            hovering = mouseX >= position.getDrawingX() && mouseX <= position.getDrawingX() + width && mouseY >= position.getDrawingY() && mouseY <= position.getDrawingY() + height;
            if (active && hovering) {
                setCurrentSettingsPath(path);
            }
        }
    }

    @ToString
    private class SettingElement extends UIEList {
        public Field field;
        public Setting info;
        public UIElement valueElement;

        public SettingElement(Field field) throws NullPointerException {
            super(0f, 0f, 0, 0);
            this.field = field;

            this.info = field.getAnnotation(Setting.class);
            if (info == null) throw new NullPointerException();

            this.position.offsetY = settingHeight * settings.elements.size();

            add(new UIEButton("重置", Textures.UIs.button_a, 0f, 0f, 0, 0, -5, true, (ui, mouseButton) -> {
                try {
                    registeredSettings.get(currentSettingsPath).resetValue(field);

                    setCurrentSettingsPath(currentSettingsPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));

            updateValue();
        }

        private void updateValue() {
            if (valueElement != null)
                return;

            try {
                Object value = registeredSettings.get(currentSettingsPath).getValues().get(field);
                if (value instanceof String) {
                    valueElement = new UIETextBox(0f, 0f, 0, 16, 170, true, ((String) value).replace("§", "&"), false, (ui, oldString) -> {
                        try {
                            registeredSettings.get(currentSettingsPath).setValue(field, ((UIETextBox) valueElement).getText().replace("&", "§"), false);
                            changedSettings.add(currentSettingsPath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    //((UIETextBox) valueElement).textField.setEnableBackgroundDrawing(false);
                    Setting.Limitations.StringLimit limit = field.getAnnotation(Setting.Limitations.StringLimit.class);
                    if (limit != null)
                        ((UIETextBox) valueElement).textField.setMaxStringLength(limit.maxLength());
                    else ((UIETextBox) valueElement).textField.setMaxStringLength(120);
                } else if (field.getType().isAssignableFrom(boolean.class)) {
                    valueElement = new UIEButton.Toggle("已开启", Textures.UIs.button_b, "已关闭", Textures.UIs.button_b, (boolean) value, 0f, 0f, 0, 15, -10, true, (ui, mouseButton) -> {
                        try {
                            registeredSettings.get(currentSettingsPath).setValue(field, ((UIEButton.Toggle) valueElement).value, false);
                            changedSettings.add(currentSettingsPath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } else if (value instanceof Enum) {
                    valueElement = new UIEButton.Enum(s -> s, Textures.UIs.button_b, (Class<? extends Enum>) field.getType(), value, 0f, 0f, 0, 15, -10, true, (ui, mouseButton) -> {
                        try {
                            registeredSettings.get(currentSettingsPath).setValue(field, ((UIEButton.Enum) valueElement).value, false);
                            changedSettings.add(currentSettingsPath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } else if (field.getType().isAssignableFrom(int.class)) {
                    Setting.Limitations.IntLimit limit = field.getAnnotation(Setting.Limitations.IntLimit.class);
                    int limitMin = Integer.MIN_VALUE, limitMax = Integer.MAX_VALUE, limitPrecision = 1;

                    if (limit != null) {
                        limitMin = limit.min();
                        limitMax = limit.max();
                        limitPrecision = limit.precision();
                    }

                    valueElement = new UIESlider.Horizontal(CommonColors.GRAY, Textures.UIs.button_a, 0f, 0f, 0, 15, 175, true, limitMin, limitMax, limitPrecision, 0, (ui, aFloat) -> {
                        try {
                            registeredSettings.get(currentSettingsPath).setValue(field, (int) ((UIESlider) valueElement).getValue(), false);
                            changedSettings.add(currentSettingsPath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    ((UIESlider) valueElement).setValue((int) value);
                    ((UIESlider) valueElement).decimalFormat = new DecimalFormat("#");

                } else if (field.getType().isAssignableFrom(float.class)) {
                    Setting.Limitations.FloatLimit limit = field.getAnnotation(Setting.Limitations.FloatLimit.class);
                    float limitMin = Float.MIN_VALUE, limitMax = Float.MAX_VALUE, limitPrecision = 0.1F;

                    if (limit != null) {
                        limitMin = limit.min();
                        limitMax = limit.max();
                        limitPrecision = limit.precision();
                    }

                    valueElement = new UIESlider.Horizontal(CommonColors.GRAY, Textures.UIs.button_a, 0f, 0f, 0, 15, 175, true, limitMin, limitMax, limitPrecision, 0, (ui, aFloat) -> {
                        try {
                            registeredSettings.get(currentSettingsPath).setValue(field, (float) ((UIESlider) valueElement).getValue(), false);
                            changedSettings.add(currentSettingsPath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    ((UIESlider) valueElement).setValue((float) value);
                    ((UIESlider) valueElement).decimalFormat = new DecimalFormat("#.#");
                } else if (field.getType().isAssignableFrom(double.class)) {
                    Setting.Limitations.DoubleLimit limit = field.getAnnotation(Setting.Limitations.DoubleLimit.class);
                    double limitMin = Double.MIN_VALUE, limitMax = Double.MAX_VALUE, limitPrecision = 0.1D;

                    if (limit != null) {
                        limitMin = limit.min();
                        limitMax = limit.max();
                        limitPrecision = limit.precision();
                    }

                    valueElement = new UIESlider.Horizontal(CommonColors.GRAY, Textures.UIs.button_a, 0f, 0f, 0, 15, 175, true, (float) limitMin, (float) limitMax, (float) limitPrecision, 0, (ui, aFloat) -> {
                        try {
                            registeredSettings.get(currentSettingsPath).setValue(field, (float) (double) ((UIESlider) valueElement).getValue(), false);
                            changedSettings.add(currentSettingsPath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    ((UIESlider) valueElement).setValue((float) (double) value);
                    ((UIESlider) valueElement).decimalFormat = new DecimalFormat("#.#");
                } else if (field.getType().isAssignableFrom(CustomColor.class)) {
                    valueElement = new UIEColorWheel(0, 0, 0, 17, 20, 20, true, (color) -> {
                        try {
                            registeredSettings.get(currentSettingsPath).setValue(field, color, false);
                            changedSettings.add(currentSettingsPath);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }, thisScreen);
                    ((UIEColorWheel) valueElement).setColor((CustomColor) value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            elements.add(valueElement);
        }
    }
}
