package com.example.administrator.weixinhookdemo.xposed;

import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * Created by Administrator on 2018/8/13.
 */

public class PrintSQL667 {
    public static void print(ClassLoader classLoader) throws ClassNotFoundException {

        Class SQLiteDatabase=XposedHelpers.findClass("com.tencent.wcdb.database.SQLiteDatabase",classLoader);
        Class CancellationSignal=XposedHelpers.findClass("com.tencent.wcdb.support.CancellationSignal",classLoader);
        XposedHelpers.findAndHookConstructor(
                "com.tencent.wcdb.database.SQLiteDirectCursorDriver",
                classLoader,
                SQLiteDatabase,
                String.class,
                String.class,
                CancellationSignal,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Log.d("SQLiteDirectCursorDriver", "mEditTable=" + param.args[2]);
                        Log.d("SQLiteDirectCursorDriver", "mSql=" + param.args[1]);
                    }
                });
    }
}
