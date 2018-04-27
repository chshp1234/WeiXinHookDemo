package com.example.administrator.weixinhookdemo;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.blankj.utilcode.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/30.
 */

public class MyApplication extends Application {

    public List<Activity> activityList;

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        activityList = new ArrayList<>();
        this.registerActivityLifecycleCallbacks(
                new ActivityLifecycleCallbacks() {
                    @Override
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                        activityList.add(activity);
                    }

                    @Override
                    public void onActivityStarted(Activity activity) {}

                    @Override
                    public void onActivityResumed(Activity activity) {}

                    @Override
                    public void onActivityPaused(Activity activity) {}

                    @Override
                    public void onActivityStopped(Activity activity) {}

                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

                    @Override
                    public void onActivityDestroyed(Activity activity) {
                        activityList.remove(activity);
                    }
                });
    }
}
