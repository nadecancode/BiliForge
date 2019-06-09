/*
 *  * Copyright © Wynntils - 2019.
 */

package cn.charlotte.biliforge.util.mouse;

public enum Priority {
    LOWEST,
    LOW,
    NORMAL,
    HIGH,
    HIGHEST;

    public static Priority valueOf(int i) {
        return Priority.values()[i];
    }

}
