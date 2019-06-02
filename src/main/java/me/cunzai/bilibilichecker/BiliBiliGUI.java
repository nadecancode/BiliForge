package me.cunzai.bilibilichecker;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class BiliBiliGUI {
    private String text;
    private int color;
    private boolean shadow;
    private boolean background;
    private boolean chroma;
    private boolean static_chroma;
    private float scale;
    private double x;
    private double y;

    public BiliBiliGUI(String text, int color, boolean shadow, double x, double y)
    {
        this.text = text;
        this.color = color;
        this.shadow = shadow;
        this.background = true;
        this.chroma = false;
        this.static_chroma = false;
        this.scale = 1.0F;
        this.x = x;
        this.y = y;
    }
    public void draw(){
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        int width = new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth();
        int height = new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight();
        int x = (int) ((width - (fr.getStringWidth(this.getText()) * this.getScale())) * this.getX());
        int y = (int) ((height - (fr.FONT_HEIGHT * this.getScale())) * this.getY());
        x = Math.round(x / this.getScale());
        y = Math.round(y / this.getScale());
        Gui.drawRect(x - 2, y - 2, x + 2 + fr.getStringWidth(this.getText()), y + 1 + fr.FONT_HEIGHT, 8);
        fr.drawString(this.getText(), x, y, this.getColor(), this.isShadow());

    }
    public double getX(){
        return this.x;
    }
    public String getText(){
        return this.text;
    }
    public float getScale(){
        return this.scale;
    }
    public double getY(){
        return this.y;
    }
    public int getColor(){
        return this.color;
    }
    public boolean isShadow(){
        return this.shadow;
    }





}
