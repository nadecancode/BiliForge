package me.cunzai.bilibilichecker;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import scala.util.parsing.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Checker {
    public static int getUid(){
        ConfigLoader.load();
        return ConfigLoader.uid;
    }

    public static int getFans(){

        URL url = null;
        try {
            url = new URL("https://api.bilibili.com/x/relation/stat?vmid=" + getUid() );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection urlConnection = null;
        try {
            urlConnection = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream inputStream = null;
        try {
            inputStream = urlConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer bs = new StringBuffer();
        String l = null;
        while (true) {
            try {
                if (!((l = buffer.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            bs.append(l);
        }
         String resp =  bs.toString();
        JsonParser parser = new JsonParser();
        JsonObject json =(JsonObject) parser.parse(resp);
        if (json.get("code").getAsInt()==0){
            return json.get("data").getAsJsonObject().get("follower").getAsInt();
        }else {
            return 0;
        }


    }




}
