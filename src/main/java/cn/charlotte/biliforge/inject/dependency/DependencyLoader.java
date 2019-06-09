package cn.charlotte.biliforge.inject.dependency;

import cn.charlotte.biliforge.BiliForge;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class DependencyLoader {

    public static synchronized void addToPath(URL url) {
        try {
            URLClassLoader sysLoader = (URLClassLoader) BiliForge.getInstance().getClass().getClassLoader();
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(sysLoader, url);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void addToPath(File file) {
        try {
            addToPath(file.toURI().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
