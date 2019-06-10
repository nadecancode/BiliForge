/*
 *  * Copyright © Wynntils - 2019.
 */

package cn.charlotte.biliforge.util.render.colors;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import org.apache.commons.codec.digest.DigestUtils;
import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;


/**
 * CustomColor
 * will represent color or complex colors
 * in a more efficient way than awt's Color or minecraft's color ints.
 */
public class CustomColor {
    public float
            r, // The RED   value of the color(0.0f -> 1.0f)
            g, // The GREEN value of the color(0.0f -> 1.0f)
            b, // The BLUE  value of the color(0.0f -> 1.0f)
            a; // The ALPHA value of the color(0.0f -> 1.0f)

    public CustomColor(float r, float g, float b) {
        this(r, g, b, 1.0f);
    }

    public CustomColor(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public CustomColor(ReadableColor color) {
        this.r = color.getRed();
        this.g = color.getGreen();
        this.b = color.getBlue();
        this.a = color.getAlpha();
    }

    public CustomColor() {
        this.r = 0;
        this.g = 0;
        this.b = 0;
        this.a = 255;
    }

    public static CustomColor fromString(String string, float a) {
        if (string.length() == 6) {
            try {
                float r = ((float) Integer.parseInt(string.substring(0, 2), 16) / 255f);
                float g = ((float) Integer.parseInt(string.substring(2, 4), 16) / 255f);
                float b = ((float) Integer.parseInt(string.substring(4, 6), 16) / 255f);
                return new CustomColor(r, g, b, a);
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
        return fromString(DigestUtils.sha1Hex(string).substring(0, 6), a);
    }

    public static CustomColor fromHSV(float h, float s, float v, float a) {
        if (v == 0f) {
            return new CustomColor(0.0f, 0.0f, 0.0f, a);
        } else if (s == 0f) {
            return new CustomColor(v, v, v, a);
        } else {
            h = h % 1f;

            float vh = h * 6;
            if (vh == 6)
                vh = 0;
            int vi = MathHelper.floor_double((double) vh);
            float v1 = v * (1 - s);
            float v2 = v * (1 - s * (vh - vi));
            float v3 = v * (1 - s * (1 - (vh - vi)));

            switch (vi) {
                case 0:
                    return new CustomColor(v, v3, v1, a);
                case 1:
                    return new CustomColor(v2, v, v1, a);
                case 2:
                    return new CustomColor(v1, v, v3, a);
                case 3:
                    return new CustomColor(v1, v2, v, a);
                case 4:
                    return new CustomColor(v3, v1, v, a);
                default:
                    return new CustomColor(v, v1, v2, a);
            }
        }
    }

    public void set(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * applyColor
     * Will set the color to OpenGL's active color
     */
    public void applyColor() {
        GlStateManager.color(r, g, b, a);
    }

    public CustomColor setA(float a) {
        this.a = a;
        return this;
    }

    public String toString() { /** HeyZeer0: this is = rgba(1,1,1,1) **/
        return "rgba(" + r + "," + g + "," + b + "," + a + ")";
    }

    public void fromHSB(float hue, float saturation, float brightness) {
        if (saturation == 0.0F) {
            r = g = b = (byte) (brightness * 255F + 0.5F);
        } else {
            float f3 = (hue - (float) Math.floor(hue)) * 6F;
            float f4 = f3 - (float) Math.floor(f3);
            float f5 = brightness * (1.0F - saturation);
            float f6 = brightness * (1.0F - saturation * f4);
            float f7 = brightness * (1.0F - saturation * (1.0F - f4));
            switch ((int) f3) {
                case 0:
                    r = (byte) (brightness * 255F + 0.5F);
                    g = (byte) (f7 * 255F + 0.5F);
                    b = (byte) (f5 * 255F + 0.5F);
                    break;
                case 1:
                    r = (byte) (f6 * 255F + 0.5F);
                    g = (byte) (brightness * 255F + 0.5F);
                    b = (byte) (f5 * 255F + 0.5F);
                    break;
                case 2:
                    r = (byte) (f5 * 255F + 0.5F);
                    g = (byte) (brightness * 255F + 0.5F);
                    b = (byte) (f7 * 255F + 0.5F);
                    break;
                case 3:
                    r = (byte) (f5 * 255F + 0.5F);
                    g = (byte) (f6 * 255F + 0.5F);
                    b = (byte) (brightness * 255F + 0.5F);
                    break;
                case 4:
                    r = (byte) (f7 * 255F + 0.5F);
                    g = (byte) (f5 * 255F + 0.5F);
                    b = (byte) (brightness * 255F + 0.5F);
                    break;
                case 5:
                    r = (byte) (brightness * 255F + 0.5F);
                    g = (byte) (f5 * 255F + 0.5F);
                    b = (byte) (f6 * 255F + 0.5F);
                    break;
            }
        }
    }

    @Override
    public CustomColor clone() {
        return new CustomColor(this.r, this.g, this.b, this.a);
    }

    public ReadableColor toColor() {
        return new Color((int) this.r, (int) this.g, (int) this.b, (int) this.a);
    }

    public int getRed() {
        return (int) this.r;
    }

    public int getBlue() {
        return (int) this.b;
    }

    public int getGreen() {
        return (int) this.g;
    }

    public int getAlpha() {
        return (int) this.a;
    }
}
