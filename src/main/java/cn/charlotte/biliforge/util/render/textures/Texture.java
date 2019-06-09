/*
 *  * Copyright © Wynntils - 2019.
 */

package cn.charlotte.biliforge.util.render.textures;

import cn.charlotte.biliforge.util.mouse.ActionResult;

public abstract class Texture {

    public boolean loaded = false;
    public float width, height;

    public abstract ActionResult load();

    public abstract ActionResult unload();

    public abstract ActionResult bind();

}
