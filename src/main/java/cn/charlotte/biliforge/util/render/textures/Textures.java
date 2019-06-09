/*
 *  * Copyright © Wynntils - 2019.
 */

package cn.charlotte.biliforge.util.render.textures;

import cn.charlotte.biliforge.BiliForge;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Textures {
    public static void loadTextures() {
        List<Class<?>> textureClasses = new ArrayList<>();


        textureClasses.add(Masks.class);
        textureClasses.add(UIs.class);


        for (Class<?> clazz : textureClasses) {
            String path = BiliForge.MODID + ":textures/" + clazz.getName().split("\\$")[1].toLowerCase() + "/";
            for (Field f : clazz.getDeclaredFields()) {
                try {
                    if (f.get(null) == null && f.getType().isAssignableFrom(AssetsTexture.class)) {
                        String file = path + f.getName() + ".png";
                        f.set(null, new AssetsTexture(new ResourceLocation(file)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Masks {
        public static AssetsTexture full;
    }

    public static class UIs {
        public static AssetsTexture book;
        public static AssetsTexture book_scrollarea_settings;

        public static AssetsTexture button_a;
        public static AssetsTexture button_b;
        public static AssetsTexture button_scrollbar;

        public static AssetsTexture color_wheel;

    }
}
