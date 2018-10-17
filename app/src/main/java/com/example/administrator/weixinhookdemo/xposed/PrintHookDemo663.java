package com.example.administrator.weixinhookdemo.xposed;

import android.util.Log;

import com.example.administrator.weixinhookdemo.WeiXinHookDemo;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static com.example.administrator.weixinhookdemo.xposed.PrintHookDemo667.getObject;

/**
 * Created by Administrator on 2018/8/13.
 */

public class PrintHookDemo663 {
    public static void print(ClassLoader classLoader) throws ClassNotFoundException {
        Class boi = XposedHelpers.findClass("com.tencent.mm.protocal.c.bjf", classLoader);
        XposedHelpers.findAndHookConstructor("com.tencent.mm.plugin.sns.model.n", classLoader, boi, String.class
                , new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Object model_o = param.thisObject;
                        Object boi = param.args[0];
                        String str = (String) param.args[1];
                        Log.i("sns_info_str", str);
//                        Log.i("sns_info_str_test", byteToMd5(String.valueOf(SystemClock.elapsedRealtime()).getBytes()));
                        new Thread(() -> getObject(boi)).start();

                    }
                });

        Class e$a = XposedHelpers.findClass("com.tencent.mm.plugin.scanner.util.e$a", classLoader);
        XposedHelpers.findAndHookMethod("com.tencent.mm.plugin.scanner.ui.BaseScanUI", classLoader, "a"
                , String.class, int.class, int.class, int.class, e$a, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        new Thread(() -> getObject(param.thisObject)).start();
                        Log.d("BaseScanUI_", String.valueOf(param.args[0]));
                        Log.d("BaseScanUI_", String.valueOf(param.args[1]));
                        Log.d("BaseScanUI_", String.valueOf(param.args[2]));
                        Log.d("BaseScanUI_", String.valueOf(param.args[3]));

                        WeiXinHookDemo.printCallStack("BaseScanUI_");
                    }
                });
    }
}
