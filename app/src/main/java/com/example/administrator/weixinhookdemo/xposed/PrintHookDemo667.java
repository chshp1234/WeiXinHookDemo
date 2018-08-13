package com.example.administrator.weixinhookdemo.xposed;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * Created by Administrator on 2018/8/13.
 */

public class PrintHookDemo667 {
    public static void print(final ClassLoader classLoader) throws ClassNotFoundException {

        XposedHelpers.findAndHookMethod(
                "com.tencent.mm.plugin.sns.ui.SnsHeader",
                classLoader,
                "bDo",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        boolean b = (boolean) param.getResult();
                        Log.d("hook_SnsHeader", String.valueOf(b));
                        Class aj =
                                XposedHelpers.findClass(
                                        "com.tencent.mm.plugin.sns.model.aj", classLoader);
                        List byH = (List) XposedHelpers.callStaticMethod(aj, "byH");
                        List linkedList = new LinkedList();
                        for (int i = 0; i < byH.size(); i++) {
                            Object n = byH.get(i);
                            Log.d("hook_field_type", String.valueOf(XposedHelpers.getIntField(n, "field_type")));
                        }
                    }
                });
    }
}
