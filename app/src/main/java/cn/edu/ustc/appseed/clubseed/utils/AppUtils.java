package cn.edu.ustc.appseed.clubseed.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import cn.edu.ustc.appseed.clubseed.data.Event;
import cn.edu.ustc.appseed.clubseed.data.ListPhp;

/**
 * Created by Hengruo on 2015/2/7.
 * This class contains some global variables and static methods.
 */
public class AppUtils extends Activity {
    public static Context sAppContext;
    public static SharedPreferences sSharedPreferences;
    public static boolean isReadingMode;
    public static ListPhp sNullListPhp;
    public static String version = "0.0.9";
    public static String phoneInfo;
    final static OkHttpClient client = new OkHttpClient();
    public static HashMap<Integer, Event> savedEvents;

    public static String changeFilename(String filename) {
        return filename.replace('/', '|');
    }

    public static boolean existCache(String filename) {

        File file = new File(sAppContext.getCacheDir().getPath() + filename);
        Log.d("HR", file.exists() ? filename + ":EXIST" : filename + ":NOT EXIST");
        return file.exists();
    }

    /**
     * Clear the cache directory.
     *
     * @return whether the cache directory is cleared.
     */
    public static boolean clearCache() {
        File[] files = sAppContext.getCacheDir().listFiles();
        if (files == null) return false;
        for (File file : files) {
            file.deleteOnExit();
        }
        return true;
    }

    /**
     * fetch the json string from url
     *
     * @param url
     * @return
     */
    public static String getJSONString(String url) throws IOException {
        Cache cache = new Cache(Environment.getExternalStorageDirectory(), 10 * 1024 * 1024);
        client.setCache(cache);
        Request listRequest = new Request.Builder()
                .url(url)
                .build();
        String jsonString = client.newCall(listRequest).execute().body().string();
        return jsonString;
    }

    public static Bitmap getBitmapFromURL(String url) throws Exception {
        Request listRequest = new Request.Builder()
                .url(url)
                .build();
        byte[] bytes = client.newCall(listRequest).execute().body().bytes();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

    public static boolean saveString(String filename, String jsonString) throws Exception {
        File file = new File(sAppContext.getFilesDir().getPath() + "/" + filename);
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buff = jsonString.getBytes();
        fos.write(buff);
        fos.flush();
        fos.close();
        return true;
    }

    public static void saveBitmap(String filename, Bitmap bitmap) throws Exception {
        File file = new File(sAppContext.getFilesDir().getPath() + "/" + filename);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.flush();
        fos.close();
    }

    public static String loadString(String filename) throws Exception {
        File file = new File(sAppContext.getFilesDir().getPath() + "/" + filename);
        byte[] buff = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(buff);
        String string = new String(buff);
        return string;
    }

    public static Bitmap loadBitmap(String filename) throws Exception{
        return BitmapFactory.decodeFile(sAppContext.getFilesDir().getPath() + "/" + filename);
    }

    public static void deleteStar(String filename) throws Exception{
        File file = new File(sAppContext.getFilesDir().getPath() + "/" + filename);
        file.delete();
    }
}
