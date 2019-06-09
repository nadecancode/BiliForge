package cn.charlotte.biliforge.wrapper.bilibili;

import cn.charlotte.biliforge.BiliForge;
import cn.charlotte.biliforge.color.ChatColor;
import cn.charlotte.biliforge.wrapper.bilibili.account.BilibiliAccountInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

@RequiredArgsConstructor
@Data
public class BilibiliConnection {

    private final static String API_URL = "https://api.bilibili.com/x/web-interface/card?mid={uid}";

    private final long uid;
    private final ObjectMapper MAPPER = new ObjectMapper();

    @SneakyThrows
    public BilibiliAccountInfo makeConnection() {
        OkHttpClient apiClient = new OkHttpClient.Builder().build();
        try (Response response = apiClient.newCall(new Request.Builder().url(API_URL.replace("{uid}", Long.toString(this.uid))).build()).execute()) {
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                BiliForge.getInstance().sendMessage(ChatColor.RED + "无法连接到Bilibili服务器! 请检查你的UID是否正确!");
                return null;
            }
            String body = responseBody.string();
            return MAPPER.readValue(body, BilibiliAccountInfo.class);
        }
    }

}
