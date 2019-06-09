package cn.charlotte.biliforge.inject.dependency;

import cn.charlotte.biliforge.BiliForge;
import cn.charlotte.biliforge.util.download.EagletTask;
import cn.charlotte.biliforge.util.download.ProgressEvent;
import lombok.SneakyThrows;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

public class Dependency {
    public static final String MAVEN_REPO = "http://repo1.maven.org/maven2";
    public static final String MAVEN_REPO_ALI = "https://maven.aliyun.com/repository/central";
    private static final File configFolder = new File(BiliForge.MOD_STORAGE_ROOT, "libraries");

    @SneakyThrows
    public static boolean requestLib(String type, String repo, String url) {
        if (type.matches(".*:.*:.*")) {
            String[] arr = type.split(":");
            File file = new File(configFolder, (String.join("-", arr) + ".jar").replace(".\\", "").replace("\\.", "").replace("./", "").replace("/.", ""));
            if (file.exists()) {
                DependencyLoader.addToPath(file);
                return true;
            } else {
                file.getParentFile().mkdirs();
                if (downloadMaven(repo, arr[0], arr[1], arr[2], file, url)) {
                    DependencyLoader.addToPath(file);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    private static boolean downloadMaven(String url, String groupId, String artifactId, String version, File target, String dl) {
        AtomicBoolean failed = new AtomicBoolean(false);
        String link = dl.length() == 0 ? url + "/" + groupId.replace('.', '/') + "/" + artifactId + "/" + version + "/" + artifactId + "-" + version + ".jar" : dl;
        new EagletTask()
                .url(link)
                .file(target)
                .setThreads(1)
                .setOnError(event -> event.getException().printStackTrace())
                .setOnConnected(event -> System.out.println("[BiliForge] Starting to Download Maven Dependency: " + String.join(":", new String[]{groupId, artifactId, version}) + " | " + ProgressEvent.format(event.getContentLength())))
                .setOnProgress(event -> System.out.println("[BiliForge] Downloading Maven Dependency: " + event.getSpeedFormatted() + ", " + event.getPercentageFormatted()))
                .setOnComplete(event -> {
                    if (event.isSuccess()) {
                        System.out.println("[BiliForge] Successfully Downloaded Maven Dependency: " + String.join(":", new String[]{groupId, artifactId, version}));
                    } else {
                        failed.set(true);
                        System.out.println("[BiliForge] Failed to Download Maven Dependency: " + String.join(":", new String[]{groupId, artifactId, version}) + "(" + link + target.getName() + ")");
                    }
                }).start().waitUntil();
        return !failed.get();
    }

}
