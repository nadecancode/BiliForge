package cn.charlotte.biliforge.util.update;

import cn.charlotte.biliforge.BiliForge;
import cn.charlotte.biliforge.util.download.EagletTask;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


@RequiredArgsConstructor
public class GithubUpdater {
    private final String author, repo, fileName;

    public boolean update() {
        return update(true);
    }

    @SneakyThrows
    public boolean update(boolean download) {
        String version = BiliForge.getInstance().VERSION;
        String parseVersion = version.toLowerCase()
                .replace("beta", "")
                .replace("snapshot", "")
                .replace("-", "")
                .replace(".", "");

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/repos/" + author + "/" + repo + "/releases/latest").build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                System.out.println("Error when making connection to update server.");
                return false;
            }

            String bodyString = responseBody.string();
            GithubUpdate githubUpdate = new ObjectMapper().readValue(bodyString, GithubUpdate.class);

            String parsedTag = githubUpdate.getTag().toLowerCase()
                    .replace("beta", "")
                    .replace("snapshot", "")
                    .replace("-", "")
                    .replace(".", "");

            int latestVersion = Integer.parseInt(parsedTag);

            String downloadURL = "https://github.com/" + author + "/" + repo + "/releases/download/"
                    + parsedTag + "/" + fileName;

            if (latestVersion > Integer.parseInt(parseVersion) && download) {
                EagletTask eagletTask = new EagletTask()
                        .url(downloadURL)
                        .setThreads(5)
                        .file(BiliForge.MOD_STORAGE_ROOT.getPath() + "/BiliForge-" + githubUpdate.getTag() + ".jar")
                        .start();
                //TODO 用GUI呈现下载进度
                return true;
            }
        }

        return false;
    }

}
