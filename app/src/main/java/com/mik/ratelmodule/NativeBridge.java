package com.mik.ratelmodule;

import android.content.Context;
import androidx.annotation.Keep;
import android.util.Log;


import com.mik.ratelmodule.utils.Types;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 给native调用的桥接器，单独抽离做混淆白名单。进而减少业务代码混淆
 */
@Keep
public class NativeBridge {
    public static String RATEL_NATIVE_LIB_NAME="ratelnative";
    public static String TAG="NativeBridge";

    static {
        System.loadLibrary(RATEL_NATIVE_LIB_NAME);
    }

    public static final long s = System.currentTimeMillis();

    private static native String bridgeInitNative(Map<String, String> bindData, Context context, String originPkgName);


    public static void nativeBridgeInit(Context context,String packageName) {
        Map<String, String> bindData = new HashMap<>();
        bindData.put("RATEL_NATIVE", Types.getNativeClassName(RatelNative.class));
//        bindData.put("RATEL_PROPERTIES_MOCK_ITEM", Types.getNativeClassName(PropertiesMockItem.class));
//        bindData.put("RATEL_A", Types.getNativeClassName(SelfExplosion.class));

        String errorMessage = bridgeInitNative(bindData, context,packageName);
        if (errorMessage == null) {
            return;
        }
        throw new IllegalStateException("ratel native engine init failed: " + errorMessage);
    }

    public static void showStackTrace() {
        Log.i(TAG, "java stack: ", new Throwable());
    }
}
