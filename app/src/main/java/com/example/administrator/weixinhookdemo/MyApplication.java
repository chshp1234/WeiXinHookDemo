package com.example.administrator.weixinhookdemo;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;

/** Created by Administrator on 2018/3/30. */
public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        //        Stetho.initializeWithDefaults(this);
        Utils.init(this);
    }

    public static Context getContext() {
        return context;
    }
}
