package com.mik.ratelmodule.passRoot;


import android.util.Log;
import com.mik.ratelmodule.RatelNative;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RootDetectUtil {

    private static <E> ArrayList<E> newArrayList(E... elements) {
        // Avoid integer overflow when a large array is passed in
        int capacity = computeArrayListCapacity(elements.length);
        ArrayList<E> list = new ArrayList<E>(capacity);
        Collections.addAll(list, elements);
        return list;
    }
    private static int computeArrayListCapacity(int arraySize) {

        return saturatedCast(5L + arraySize + (arraySize / 10));
    }
    private static int saturatedCast(long value) {
        if (value > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (value < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        return (int) value;
    }

    private static final List<String> SUSPICIOUS_PATHS = newArrayList("/sbin/", "/system/bin/", "/system/xbin/", "/data/local/xbin/", "/data/local/bin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/");

    /**
     * 1. 文件检测："/sbin/", "/system/bin/", "/system/xbin/", "/data/local/xbin/", "/data/local/bin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/" + "su"
     */
    public static void anti() {
        try {
            for (String dir : SUSPICIOUS_PATHS) {
                File su = new File(dir + "su");
                if (su.exists()) {
                    RatelNative.redirectFile(su.getCanonicalPath(), "/data" + su.getCanonicalPath());
                    Log.e("miktest", "root file relocate:  " + su.getCanonicalPath());
                }
            }
        } catch (Throwable e) {
            Log.e("miktest", "root detect anti error:"  + e);
        }
    }

}
