package cn.edu.ustc.appseed.clubseed.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.Cache;
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
    public static Context sAppContext;
    public static SharedPreferences sSharedPreferences;
    public static boolean isReadingMode;
    public static ListPhp NullListPhp;
    public static String version = "0.0.9";
    public static String phoneInfo;
    final static OkHttpClient client = new OkHttpClient();

    public static String changeFilename(String filename){
        return filename.replace('/', '|');
    }

    public static boolean saveCache(String filename, String content) {
        filename = changeFilename(filename);
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
        filename = changeFilename(filename);
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
        if(files==null)return false;
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
    public static String getJSONString(String url) throws IOException {
        Cache cache = new Cache(Environment.getExternalStorageDirectory(),10 * 1024 * 1024);
        client.setCache(cache);
            Request listRequest = new Request.Builder()
                    .url(url)
                    .build();
            String jsonString = client.newCall(listRequest).execute().body().string();
            return jsonString;
    }

    public static Bitmap getBitmapFromURL(String url) throws Exception{
            Request listRequest = new Request.Builder()
                    .url(url)
                    .build();
            byte[] bytes = client.newCall(listRequest).execute().body().bytes();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            return bitmap;
    }
}
