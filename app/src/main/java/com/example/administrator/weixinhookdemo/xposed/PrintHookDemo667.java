package com.example.administrator.weixinhookdemo.xposed;

import android.util.Log;

import java.lang.reflect.Field;
import java.security.MessageDigest;
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
                        Log.d("——hook_SnsHeader", String.valueOf(b));
                        Class aj =
                                XposedHelpers.findClass(
                                        "com.tencent.mm.plugin.sns.model.aj", classLoader);
                        List byH = (List) XposedHelpers.callStaticMethod(aj, "byH");
                        List linkedList = new LinkedList();
                        for (int i = 0; i < byH.size(); i++) {
                            Object n = byH.get(i);
                            Log.d("——hook_field_type", String.valueOf(XposedHelpers.getIntField(n, "field_type")));
                        }
                    }
                });

        Class boi = XposedHelpers.findClass("com.tencent.mm.protocal.c.boi", classLoader);
        XposedHelpers.findAndHookConstructor("com.tencent.mm.plugin.sns.model.o", classLoader, boi, String.class
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

        Class bon = XposedHelpers.findClass("com.tencent.mm.protocal.c.bon", classLoader);
        XposedHelpers.findAndHookConstructor("com.tencent.mm.plugin.sns.model.r", classLoader, long.class, int.class, bon
                , Object.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Log.i("sns_info_del_", String.valueOf(param.args[0]));
                        Log.i("sns_info_del_", String.valueOf(param.args[1]));
                        new Thread(() -> getObject(param.args[2])).start();
                    }
                });

        XposedHelpers.findAndHookMethod("com.tencent.mm.api.a", classLoader, "cy",
                String.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Log.i("api_param?", String.valueOf(param.args[0]));
                        Log.i("api_result?", String.valueOf(param.getResult()));
                    }
                });

    }

    public static final String byteToMd5(byte[] bArr) {
        char[] cArr = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            byte[] digest = instance.digest();
            int length = digest.length;
            char[] cArr2 = new char[(length * 2)];
            int i = 0;
            int i2 = 0;
            while (i < length) {
                byte b = digest[i];
                int i3 = i2 + 1;
                cArr2[i2] = cArr[(b >>> 4) & 15];
                int i4 = i3 + 1;
                cArr2[i3] = cArr[b & 15];
                i++;
                i2 = i4;
            }
            return new String(cArr2);
        } catch (Exception e) {
            return null;
        }
    }

    public static void getObject(Object object) {

        if (object != null) {
            Log.i("get_object_" + object.getClass().getName(), " \n-------------------------------------------------------------------------------");
            Field[] fields = object.getClass().getDeclaredFields();
            for (int j = 0; j < fields.length; j++) {
                boolean isObject = true;
                String[] types1 = {"int", "java.lang.String", "boolean", "char", "float", "double", "long", "short", "byte"};
                String[] types2 = {"Integer", "java.lang.String", "java.lang.Boolean", "java.lang.Character",
                        "java.lang.Float", "java.lang.Double", "java.lang.Long", "java.lang.Short", "java.lang.Byte"};
                try {
                    if (fields[j].get(object) != null) {

                        for (int i = 0; i < types1.length; i++) {
                            if (fields[j].getType().getName()
                                    .equalsIgnoreCase(types1[i]) || fields[j].getType().getName().equalsIgnoreCase(types2[i])) {

                                Log.i("get_object_" + fields[j].getName(), String.valueOf(fields[j].get(object)));
                                isObject = false;
                            }
                        }
                        if (isObject) {
                            Log.i("get_object_name", fields[j].getName() + " \n ");
                            getObject(fields[j].get(object));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
