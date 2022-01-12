package com.mik.ratelmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.mik.ratelmodule.passRoot.RootDetectUtil;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NativeBridge.nativeBridgeInit(this,getPackageName());
//        NativeHide.doHide();

        Log.e("miktest","mik 文件是否存在 "+new File("/sbin/su").exists());
        RatelNative.forbid("/sbin/su",true);
        RatelNative.redirectFile("/proc/cpuinfo", "/data/local/tmp/cpuinfo" );
        RootDetectUtil.anti();

        RatelNative.enableIORedirect(this);
        RatelNative.hideMaps();
        String res= FileHelper.ReadFileAll("/proc/cpuinfo");
        Log.e("miktest","mik cpuinfo: "+res);
        Log.e("miktest","mik 文件是否存在 "+new File("/sbin/su").exists());
    }
}