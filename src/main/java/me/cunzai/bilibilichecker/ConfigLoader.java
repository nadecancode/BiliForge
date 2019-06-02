package me.cunzai.bilibilichecker;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;

public class ConfigLoader {
    private static Configuration config;
    private static Logger logger;
    public static int uid;
    public static String text;
    public static int x;
    public static int y;
    public static boolean isShadow;
    public static int color;
    public ConfigLoader (FMLPreInitializationEvent e){
        logger = e.getModLog();
        config = new Configuration(e.getSuggestedConfigurationFile());
        config.load();


    }
    public static void load(){
        logger.info("loading config.");
        String comment;
        comment = "你的BiliBili UID";
        uid = config.get(Configuration.CATEGORY_GENERAL,"uid",5832391,comment).getInt();
        String comment1 = "显示的文字，不需要加颜色符号 [fans] 为变量，注意变量大小写";
        String unText = config.get(Configuration.CATEGORY_GENERAL,"text","你的粉丝数为: [fans]",comment1).getString();
        unText.replace("[fans]",Checker.getFans()+"");
        String comment2 = "显示的x坐标";
        x = config.get(Configuration.CATEGORY_GENERAL,"x",10,comment2).getInt();
        String comment3 = "显示的y坐标";
        y = config.get(Configuration.CATEGORY_GENERAL,"y",10,comment3).getInt();
        String comment4 = "字体是否为彩色，如果是，那么无视下面的字体颜色";
        isShadow = config.get(Configuration.CATEGORY_GENERAL,"isShadow",false,comment4).getBoolean();
        String comment5 = "字体的颜色";
        color = config.get(Configuration.CATEGORY_GENERAL,"color",comment5).getInt();


        config.save();
        logger.info("loaded");

    }
    public static Logger logger(){
        return logger;
    }
}
