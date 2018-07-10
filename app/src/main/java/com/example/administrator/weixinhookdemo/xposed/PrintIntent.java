package com.example.administrator.weixinhookdemo.xposed;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Created by Administrator on 2018/7/10.
 */

public class PrintIntent {

    public static void print(ClassLoader classLoader){


        XposedHelpers.findAndHookMethod(
                "android.app.Application",
                classLoader,
                "onCreate",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Application application = (Application) param.thisObject;
                        application.registerActivityLifecycleCallbacks(
                                new Application.ActivityLifecycleCallbacks() {
                                    @Override
                                    public void onActivityCreated(
                                            Activity activity, Bundle savedInstanceState) {
                                        Log.d(
                                                activity.getLocalClassName()
                                                        + "——hook_onActivityCreated",
                                                "=======================onActivityCreated=======================");
                                        //                                            Activity
                                        // activity = (Activity) param.thisObject;

                                        Intent intent = activity.getIntent();

                                        StringBuilder sb = new StringBuilder("");
                                        if (intent.getExtras() != null) {
                                            for (String s : intent.getExtras().keySet()) {
                                                String value =
                                                        intent.getExtras().get(s) == null
                                                                ? ""
                                                                : intent.getExtras()
                                                                .get(s)
                                                                .toString();
                                                sb.append(s)
                                                        .append(" = ")
                                                        .append(value)
                                                        .append("\n");
                                            }
                                        }
                                        Log.d(
                                                activity.getLocalClassName()
                                                        + "——intent_key=value",
                                                sb.toString());
                                    }

                                    @Override
                                    public void onActivityStarted(Activity activity) {
                                        Log.d(
                                                activity.getLocalClassName()
                                                        + "——hook_onActivityStarted",
                                                "onActivityStarted");
                                    }

                                    @Override
                                    public void onActivityResumed(Activity activity) {
                                        Log.d(
                                                activity.getLocalClassName()
                                                        + "——hook_onActivityResumed",
                                                "onActivityResumed");
                                    }

                                    @Override
                                    public void onActivityPaused(Activity activity) {
                                        Log.d(
                                                activity.getLocalClassName()
                                                        + "——hook_onActivityPaused",
                                                "onActivityPaused");
                                    }

                                    @Override
                                    public void onActivityStopped(Activity activity) {
                                        Log.d(
                                                activity.getLocalClassName()
                                                        + "——hook_onActivityStopped",
                                                "onActivityStopped");
                                    }

                                    @Override
                                    public void onActivitySaveInstanceState(
                                            Activity activity, Bundle outState) {
                                        Log.d(
                                                activity.getLocalClassName()
                                                        + "——hook_onActivitySaveInstanceState",
                                                "onActivitySaveInstanceState");
                                    }

                                    @Override
                                    public void onActivityDestroyed(Activity activity) {
                                        Log.d(
                                                activity.getLocalClassName()
                                                        + "——hook_onActivityDestroyed",
                                                "=======================onActivityDestroyed======================="
                                                        + "\n"
                                                        + " ");
                                    }
                                });
                    }
                });

        // hook onCreate方法，并输出intent信息
        findAndHookMethod(
                "android.app.Activity",
                classLoader,
                "onCreate",
                Bundle.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {}
                });

        // hook onResume方法
        findAndHookMethod(
                "android.app.Activity",
                classLoader,
                "onResume",
                new XC_MethodHook() {

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        //                            Activity activity = (Activity)
                        // param.thisObject;
                        //                            Log.d("hook_onResume",
                        // activity.getLocalClassName());
                    }
                });

        // hook onResume方法
        findAndHookMethod(
                "android.app.Activity",
                classLoader,
                "onActivityResult",
                int.class,
                int.class,
                Intent.class,
                new XC_MethodHook() {

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Activity activity = (Activity) param.thisObject;
                        Intent onActivityResultIntent = (Intent) param.args[2];
                        Log.d(
                                activity.getLocalClassName() + "——hook_onActivityResult",
                                "=====================onActivityResult=====================");
                        Log.d(
                                "hook_onActivityResult",
                                activity.getLocalClassName()
                                        + "\n"
                                        + "requestCode="
                                        + param.args[0]
                                        + "\n"
                                        + "resultCode="
                                        + param.args[1]);
                        StringBuilder sb = new StringBuilder("");
                        if (onActivityResultIntent != null
                                && onActivityResultIntent.getExtras() != null) {
                            for (String s : onActivityResultIntent.getExtras().keySet()) {
                                String value =
                                        onActivityResultIntent.getExtras().get(s) == null
                                                ? ""
                                                : onActivityResultIntent
                                                .getExtras()
                                                .get(s)
                                                .toString();
                                sb.append(s).append(" = ").append(value).append("\n");
                            }
                        }
                        Log.d(
                                activity.getLocalClassName() + "——intent_key=value",
                                sb.toString());
                    }
                });

        findAndHookMethod(
                "com.tencent.mm.plugin.gallery.ui.AlbumPreviewUI",
                classLoader,
                "onActivityResult",
                int.class,
                int.class,
                Intent.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Activity activity = (Activity) param.thisObject;
                        Intent onActivityResultIntent = (Intent) param.args[2];
                        Log.d(
                                activity.getLocalClassName() + "——hook_onActivityResult",
                                "=====================onActivityResult=====================");
                        Log.d(
                                "hook_onActivityResult",
                                activity.getLocalClassName()
                                        + "\n"
                                        + "requestCode="
                                        + param.args[0]
                                        + "\n"
                                        + "resultCode="
                                        + param.args[1]);
                        StringBuilder sb = new StringBuilder("");
                        if (onActivityResultIntent != null
                                && onActivityResultIntent.getExtras() != null) {
                            for (String s : onActivityResultIntent.getExtras().keySet()) {
                                String value =
                                        onActivityResultIntent.getExtras().get(s) == null
                                                ? ""
                                                : onActivityResultIntent
                                                .getExtras()
                                                .get(s)
                                                .toString();
                                sb.append(s).append(" = ").append(value).append("\n");
                            }
                        }
                        Log.d(
                                activity.getLocalClassName() + "——intent_key=value",
                                sb.toString());
                    }
                });

        /*findAndHookMethod(
            "com.tencent.mm.ui.chatting.ChattingUI$a",
            lpparam.classLoader,
            "onActivityResult",
            int.class,
            int.class,
            Intent.class,
            new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    //                            Activity activity = (Activity)
                    // param.thisObject;
                    //                            String className =
                    // param.thisObject.getClass().getSimpleName();
                    String className = "ChattingUI";
                    Intent onActivityResultIntent = (Intent) param.args[2];
                    Log.d(
                            className + "——hook_onActivityResult",
                            "=====================onActivityResult=====================");
                    Log.d(
                            "hook_onActivityResult",
                            className
                                    + "\n"
                                    + "requestCode="
                                    + param.args[0]
                                    + "\n"
                                    + "resultCode="
                                    + param.args[1]);
                    StringBuilder sb = new StringBuilder("");
                    if (onActivityResultIntent != null
                            && onActivityResultIntent.getExtras() != null) {
                        for (String s : onActivityResultIntent.getExtras().keySet()) {
                            String value =
                                    onActivityResultIntent.getExtras().get(s) == null
                                            ? ""
                                            : onActivityResultIntent
                                                    .getExtras()
                                                    .get(s)
                                                    .toString();
                            sb.append(s).append(" = ").append(value).append("\n");
                        }
                    }
                    Log.d(className + "——intent_key=value", sb.toString());
                }
            });*/
    }
}
