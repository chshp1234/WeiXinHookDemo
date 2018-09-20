package com.example.administrator.weixinhookdemo.xposed.HookLog;

import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by Administrator on 2018/2/26.
 */

public class XposedLog667_Tinker {
    private final String TAG = "tinker.lib.f.a";
    private final String LOG_METHOD = "com.tencent.tinker.lib.f.a";

    private void printLog(StringBuffer sb) {
        StackTraceElement[] elements = new Throwable().getStackTrace();
        for (StackTraceElement element : elements) {
            sb.append(element.getClassName() + ": " + element.getMethodName() + "\n");
        }
    }

    private void initPrint(XC_MethodHook.MethodHookParam param) {
        String params1 = param.args[0].toString();
        String params2 = param.args[1].toString();
        StringBuffer sb = new StringBuffer("");
        sb.append("===========start======================\n");
        sb.append(params1).append(": ").append(params2 + "\n");
        if (param.args.length > 2) {
            if (param.args[2] != null) {
                StackTraceElement[] elements = (StackTraceElement[]) param.args[2];
                for (StackTraceElement element : elements) {
                    sb.append(element.getClassName() + ": " + element.getMethodName() + "\n");
                }

            }
        }
        printLog(sb);
        sb.append("====================end==================\n");
        sb.append(" \n");
        Log.i(TAG, sb.toString());
    }

    public void printLog(XC_MethodHook.MethodHookParam param, String type) {
        switch (type) {
            case "f":

                break;
            case "j":
                break;
            case "k":
                break;
            case "e":
                break;
            case "w":
                break;
            case "i":
                break;
            case "d":
                break;
            case "v":
                break;
            default:
                break;
        }
    }

    public void findAndPrintLog(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        XposedHelpers.findAndHookMethod(LOG_METHOD, loadPackageParam.classLoader,
                "e", String.class, String.class,Object[].class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        initPrint(param);
                    }
                });
        XposedHelpers.findAndHookMethod(LOG_METHOD, loadPackageParam.classLoader,
                "w", String.class, String.class,Object[].class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        initPrint(param);
                    }
                });
        XposedHelpers.findAndHookMethod(LOG_METHOD, loadPackageParam.classLoader,
                "i", String.class, String.class,Object[].class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        initPrint(param);
                    }
                });

    }

}
