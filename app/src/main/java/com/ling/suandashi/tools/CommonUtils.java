package com.ling.suandashi.tools;

import android.annotation.SuppressLint;
import android.app.Service;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ling.suandashi.LSApplication;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Imxu
 * @time 2019/6/23 19:41
 * @des ${TODO}
 */
public class CommonUtils {

    static Long cacheSize;

    public static void showToast(String msg) {
        Toast.makeText(LSApplication.GlobalContext,msg,Toast.LENGTH_SHORT).show();
    }

    private static long calculateCacheFileSize() {
        long length = 0L;
        try {
            String cachePath = Glide.getPhotoCacheDir(LSApplication.GlobalContext).getPath();
            File cacheDir1 = new File(cachePath);
            if (cacheDir1 != null) {
                length += getFileOrDirSize(cacheDir1);
            }
        } catch (Exception e) {

        }
        return length;
    }

    public static long getFileOrDirSize(File file) {
        if (!file.exists()) return 0;
        if (!file.isDirectory()) return file.length();
        long length = 0;
        File[] list = file.listFiles();
        if (list != null) { // 文件夹被删除时, 子文件正在被写入, 文件属性异常返回null.
            for (File item : list) {
                length += getFileOrDirSize(item);
            }
        }

        return length;
    }

    public static String getCacheSize() {
        String result = "";
        cacheSize = calculateCacheFileSize();
        long oneKB = 1024;
        long oneMB = 1024 * 1024;
        long oneGB = 1024 * 1024 * 1024;
        if (cacheSize == 0) {
            return "";
        } else if (cacheSize > 0 && cacheSize < oneKB) {
            return "1K";
        } else if (cacheSize > oneKB && cacheSize < oneMB) {
            long num = cacheSize / oneKB;
            return num + "K";
        } else if (cacheSize > oneMB && cacheSize < oneGB) {
            long num = cacheSize / oneMB;
            return num + "M";
        } else if (cacheSize > oneGB) {
            long num = cacheSize / oneGB;
            return num + "G";
        }
        return result;
    }
    public static void deleteCache() {
        try {
            String cachePath = Glide.getPhotoCacheDir(LSApplication.GlobalContext).getPath();
            File cacheDir1 = new File(cachePath);
            if (cacheDir1.exists()) {
                delete(cacheDir1);
            }
        } catch (Exception e) {

        }
    }
    public static void delete(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File tmpFile = files[i];
                if (tmpFile.isDirectory()) {
                    delete(tmpFile);
                } else {
                    tmpFile.delete();
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    public static String getPhoneIMEI() {
        TelephonyManager tm = (TelephonyManager) LSApplication.GlobalContext.getSystemService(Service.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public static String birthdayToDay(String birthday){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy年MM月dd日");
            return format2.format(format.parse(birthday));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
