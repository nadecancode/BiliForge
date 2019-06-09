package cn.charlotte.biliforge.event;

import cn.charlotte.biliforge.manager.FrameworkManager;
import cn.charlotte.biliforge.util.render.ScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ClientEvents {


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void handleFrameworkEvents(Event e) {
        FrameworkManager.triggerEvent(e);
    }

    @SubscribeEvent()
    public void handleFrameworkPreHud(RenderGameOverlayEvent.Pre e) {
        FrameworkManager.triggerPreHud(e);
    }

    @SubscribeEvent()
    public void handleFrameworkHud(RenderGameOverlayEvent e) {
        FrameworkManager.triggerHud(e);
    }

    @SubscribeEvent()
    public void handleFrameworkPostHud(RenderGameOverlayEvent.Post e) {
        FrameworkManager.triggerPostHud(e);
    }


    @SubscribeEvent()
    public void onTick(TickEvent.ClientTickEvent e) {
        ScreenRenderer.refresh();
        if (Minecraft.getMinecraft().thePlayer == null) return;
        FrameworkManager.triggerHudTick(e);
        FrameworkManager.triggerKeyPress();
    }

}
