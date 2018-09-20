package com.example.administrator.weixinhookdemo.xposed.HookLog;

import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by Administrator on 2018/2/26.
 */

public class XposedLog663 {
    private final String TAG = "XposedLog";
    private final String LOG_METHOD = "com.tencent.mm.sdk.platformtools.x";

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
        sb.append(params1 + ": ");
        if (param.args.length > 2) {
            if (param.args[2] != null) {
                if (param.args[2] instanceof String) {
                    sb.append(String.format(params2, ((String) param.args[2]))).append("\n");
                } else {
                    sb.append(String.format(params2, ((Object[]) param.args[2]))).append("\n");
                }
            } else {
                sb.append(params2).append("\n");
            }
        } else {
            sb.append(params2).append("\n");

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
        Object[] objectArray = new Object[1];
        XposedHelpers.findAndHookMethod(LOG_METHOD, loadPackageParam.classLoader,
                "f", String.class, String.class, objectArray.getClass(), new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        initPrint(param);
                    }
                });
        XposedHelpers.findAndHookMethod(LOG_METHOD, loadPackageParam.classLoader,
                "e", String.class, String.class, objectArray.getClass(), new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        initPrint(param);
                    }
                });
        XposedHelpers.findAndHookMethod(LOG_METHOD, loadPackageParam.classLoader,
                "w", String.class, String.class, objectArray.getClass(), new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        initPrint(param);
                    }
                });
        XposedHelpers.findAndHookMethod(LOG_METHOD, loadPackageParam.classLoader,
                "i", String.class, String.class, objectArray.getClass(), new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        initPrint(param);
                    }
                });
        XposedHelpers.findAndHookMethod(LOG_METHOD, loadPackageParam.classLoader,
                "d", String.class, String.class, objectArray.getClass(), new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        initPrint(param);
                    }
                });
        XposedHelpers.findAndHookMethod(LOG_METHOD, loadPackageParam.classLoader,
                "v", String.class, String.class, objectArray.getClass(), new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        initPrint(param);
                    }
                });
        XposedHelpers.findAndHookMethod(LOG_METHOD, loadPackageParam.classLoader,
                "j", String.class, String.class, objectArray.getClass(), new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        initPrint(param);
                    }
                });
        XposedHelpers.findAndHookMethod(LOG_METHOD, loadPackageParam.classLoader,
                "k", String.class, String.class, objectArray.getClass(), new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        initPrint(param);
                    }
                });
        XposedHelpers.findAndHookMethod(LOG_METHOD, loadPackageParam.classLoader,
                "printErrStackTrace", String.class, Throwable.class, String.class, objectArray.getClass(), new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        initPrint(param);
                    }
                });
    }

}
