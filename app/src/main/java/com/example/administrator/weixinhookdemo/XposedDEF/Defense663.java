package com.example.administrator.weixinhookdemo.XposedDEF;

import android.util.Log;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * Created by Administrator on 2018/7/5.
 */

public class Defense663 {

    public static void Defense(ClassLoader classLoader) {

        try {
            XposedHelpers.findAndHookMethod("com.tencent.mm.app.n", classLoader, "a"
                    , StackTraceElement[].class, new XC_MethodReplacement() {

                        @Override
                        protected Object replaceHookedMethod(MethodHookParam methodHookParam) {
                            return Boolean.FALSE;
                        }
                    });
        } catch (Throwable th) {
            XposedBridge.log(Log.getStackTraceString(th));
        }

        try {
            XposedHelpers.findAndHookMethod("com.tencent.c.d.a.e", classLoader, "cDs"
                    , new XC_MethodReplacement() {

                        @Override
                        protected Object replaceHookedMethod(MethodHookParam methodHookParam) {
                            return Boolean.FALSE;
                        }
                    });
        } catch (Throwable th2) {
            XposedBridge.log(Log.getStackTraceString(th2));
        }

        try {
            XposedHelpers.findAndHookMethod("oicq.wlogin_sdk.tools.util", classLoader, "isFileExist"
                    , String.class, new XC_MethodReplacement() {

                        @Override
                        protected Object replaceHookedMethod(MethodHookParam methodHookParam) {
                            String str = (String) methodHookParam.args[0];
                            if ("/system/bin/su".equals(str) || "/system/xbin/su".equals(str) || "/sbin/su".equals(str)) {
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
        } catch (Throwable th22) {
            XposedBridge.log(Log.getStackTraceString(th22));
        }

        new HookStackTrace().hook(classLoader);

    }
}
