/*
 *  * Copyright © Wynntils - 2019.
 */

package cn.charlotte.biliforge.util.overlay;

import cn.charlotte.biliforge.instance.Module;
import cn.charlotte.biliforge.instance.containers.ModuleContainer;
import cn.charlotte.biliforge.manager.FrameworkManager;
import cn.charlotte.biliforge.settings.instances.SettingsHolder;
import cn.charlotte.biliforge.util.Position;
import cn.charlotte.biliforge.util.render.ScreenRenderer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;

public abstract class Overlay extends ScreenRenderer implements SettingsHolder {
    public transient ModuleContainer module = null;
    public transient String displayName;
    public transient Point staticSize;
    public transient boolean visible;
    public transient OverlayGrowFrom growth;
    public transient RenderGameOverlayEvent.ElementType[] overrideElements;

    public boolean active = true;
    public Position position = new Position();

    public Overlay(String displayName, int sizeX, int sizeY, boolean visible, float anchorX, float anchorY, int offsetX, int offsetY, OverlayGrowFrom growth, RenderGameOverlayEvent.ElementType... overrideElements) {
        this.displayName = displayName;
        this.staticSize = new Point(sizeX, sizeY);
        this.visible = visible;
        this.overrideElements = overrideElements;
        this.position.anchorX = anchorX;
        this.position.anchorY = anchorY;
        this.position.offsetX = offsetX;
        this.position.offsetY = offsetY;
        this.growth = growth;
        this.position.refresh(screen);
    }

    public void render(RenderGameOverlayEvent event) {
    }

    public void render(RenderGameOverlayEvent.Pre event) {
    }

    public void render(RenderGameOverlayEvent.Post event) {
    }

    public void tick(TickEvent.ClientTickEvent event, long ticks) {
    }

    @Override
    public void saveSettings(Module m) {
        try {
            FrameworkManager.getSettings(m == null ? module.getModule() : m, this).saveSettings();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onSettingChanged(String name) {

    }

    @Override
    public void onSettingsSaved() {

    }

    public enum OverlayGrowFrom {
        TOP_LEFT, TOP_CENTRE, TOP_RIGHT,
        MIDDLE_LEFT, MIDDLE_CENTRE, MIDDLE_RIGHT,
        BOTTOM_LEFT, BOTTOM_CENTRE, BOTTOM_RIGHT
    }
}
