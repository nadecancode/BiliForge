/*
 *  * Copyright © Wynntils - 2019.
 */

package cn.charlotte.biliforge.settings.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SettingsInfo {
    String name();

    String displayPath() default "";

    @Retention(RetentionPolicy.RUNTIME)
    @interface Instance {
    }
}
