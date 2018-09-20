package com.example.administrator.weixinhookdemo.XposedDEF;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * Created by Administrator on 2018/7/5.
 */

public class HookStackTrace {

    public boolean hook(ClassLoader classLoader) {
        try {
            a(classLoader);
            return true;
        } catch (Throwable th) {
            XposedBridge.log(Log.getStackTraceString(th));
            return false;
        }
    }

    private void a(ClassLoader classLoader) {
        try {
            XposedHelpers.findAndHookMethod("java.lang.String", classLoader, "contains"
                    , CharSequence.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            if ("Class ref in pre-verified class resolved to unexpected implementation".equals(param.args[0])) {
                                param.setResult(false);
                            }

                        }
                    });
        } catch (Throwable throwable) {
            XposedBridge.log(Log.getStackTraceString(throwable));
        }

        try {
            XposedHelpers.findAndHookMethod(Throwable.class, "getStackTrace", new XC_MethodReplacement() {

                @Override
                protected Object replaceHookedMethod(MethodHookParam methodHookParam) {
                    int i = 0;
                    try {
                        StackTraceElement[] stackTraceElementArr = (StackTraceElement[]) XposedBridge.invokeOriginalMethod(methodHookParam.method, methodHookParam.thisObject, methodHookParam.args);
                        Throwable th = (Throwable) methodHookParam.thisObject;
                        if (!(stackTraceElementArr == null || stackTraceElementArr.length == 0)) {
                            List arrayList = new ArrayList();
                            for (StackTraceElement stackTraceElement : stackTraceElementArr) {
                                String className = stackTraceElement.getClassName();
                                if (className == null || !(className.contains("de.robv.android.xposed.XposedBridge") || className.contains("com.zte.heartyservice.SCC.FrameworkBridge"))) {
                                    arrayList.add(stackTraceElement);
                                }
                            }
                            Object[] obj = new StackTraceElement[arrayList.size()];
                            while (i < arrayList.size()) {
                                obj[i] = arrayList.get(i);
                                i++;
                            }
                            return obj;
                        }
                    } catch (Throwable th2) {
                        XposedBridge.log(Log.getStackTraceString(th2));
                    }
                    return null;
                }
            });
        } catch (Throwable th) {
            XposedBridge.log(Log.getStackTraceString(th));
        }
        try {
            XposedHelpers.findAndHookMethod(File.class, "exists", new XC_MethodReplacement() {

                @Override
                protected Object replaceHookedMethod(MethodHookParam methodHookParam) {
                    String file = methodHookParam.thisObject.toString();
                    if ("/system/bin/su".equals(file) || "/system/xbin/su".equals(file) || "/sbin/su".equals(file) || "/su/xbin/su".equals(file)) {
                        return Boolean.FALSE;
                    }
                    try {
                        return XposedBridge.invokeOriginalMethod(methodHookParam.method, methodHookParam.thisObject, methodHookParam.args);
                    } catch (Throwable th) {
                        XposedBridge.log(Log.getStackTraceString(th));
                        return false;
                    }

                }
            });
        } catch (Throwable th2) {
            XposedBridge.log(Log.getStackTraceString(th2));
        }
        new HookInstalledApp().hook(classLoader);
    }

}
