package cn.edu.ustc.appseed.clubseed.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.edu.ustc.appseed.clubseed.jsondata.ListPhp;

/**
 * Created by Hengruo on 2015/2/7.
 * This class contains some global variables and static methods.
 */
public class AppUtils extends Activity{
    private static final String TAG = "JSON_FILE_ERROR";
    private static final int URLSPLIT = 32;
    public static Context sAppContext;

    public static boolean saveCache(String filename, String content) {
        filename = filename.substring(URLSPLIT);
        FileOutputStream fos = null;
        byte[] buff = content.getBytes();
        try {
            fos = new FileOutputStream(sAppContext.getCacheDir().getPath() + filename);
            int len = content.length();
            fos.write(buff);
            fos.flush();
            fos.close();
            return true;
        } catch (Exception e) {
            Log.d("HR", e.toString());
            return false;
        }
    }

    public static <T> T loadFile(String filename, Class<T> clazz) {
        filename = filename.substring(URLSPLIT);
        File f = new File(sAppContext.getCacheDir().getPath() + filename);
        byte[] buff = new byte[(int)f.length()];
        try {
            FileInputStream fis = new FileInputStream(f);
            fis.read(buff);
            String jsonString = new String(buff);
            return (T) JSON.parseObject(jsonString, clazz);
        } catch (Exception e) {
            Log.d("HR", e.toString());
            return null;
        }
    }

    public static boolean existCache(String filename){
        filename = filename.substring(URLSPLIT);
        File file = new File(sAppContext.getCacheDir().getPath() + filename);
        Log.d("HR",file.exists()?filename+":EXIST":filename+":NOT EXIST");
        return file.exists();
    }

    /**
     * Clear the cache directory.
     * @return whether the cache directory is cleared.
     */
    public static boolean clearCache(){
        File[] files = sAppContext.getCacheDir().listFiles();
        for(File file : files){
            file.deleteOnExit();
        }
        return true;
    }

    /**
     * fetch the json string from url
     * @param url
     * @return
     */
    public static String getJSONString(String url){
        final OkHttpClient client = new OkHttpClient();
        try {
            Request listRequest = new Request.Builder()
                    .url(url)
                    .build();
            String jsonString = client.newCall(listRequest).execute().body().string();
            return jsonString;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
