package com.example.administrator.weixinhookdemo;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;

import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.Utils;
import com.facebook.stetho.Stetho;

/** Created by Administrator on 2018/3/30. */
public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Stetho.initializeWithDefaults(this);
        Utils.init(this);
    }

    public static Context getContext() {
        return context;
    }
}
