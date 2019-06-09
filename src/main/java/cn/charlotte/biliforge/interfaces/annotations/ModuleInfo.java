/*
 *  * Copyright © Wynntils - 2019.
 */

package cn.charlotte.biliforge.interfaces.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {

    String name();

    String displayName();
}
