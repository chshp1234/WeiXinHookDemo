package com.example.administrator.weixinhookdemo.xposed;

import android.net.Uri;
import android.util.Log;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

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

        Class boh = XposedHelpers.findClass("com.tencent.mm.protocal.c.boh", classLoader);
        XposedHelpers.findAndHookMethod("com.tencent.mm.api.a", classLoader, "cy",
                String.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Log.i("api_param?", String.valueOf(param.args[0]));
                        Log.i("api_result?", String.valueOf(param.getResult()));
                    }
                });

        //他人回复
        XposedHelpers.findAndHookMethod("com.tencent.mm.plugin.sns.model.u", classLoader, "a"
                , boi, boh, long.class, long.class, String.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Log.d("get_snsId", String.valueOf(param.args[2]));
                        Log.d("get_parentID", String.valueOf(param.args[3]));
                        Log.d("get_comment.clientId", String.valueOf(param.args[4]));
                        new Thread(() -> {
                            getObject(param.args[0]);
                            getObject(param.args[1]);

                        }).start();

                    }
                });

        //他人删除回复
        XposedHelpers.findAndHookMethod("com.tencent.mm.plugin.sns.model.aj", classLoader, "b"
                , long.class, boi, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Log.d("get_snsId", String.valueOf(param.args[0]));
                        new Thread(() -> getObject(param.args[1])).start();
                    }
                });

        //自己回复
        Class boy = XposedHelpers.findClass("com.tencent.mm.protocal.c.boy", classLoader);
        Class n = XposedHelpers.findClass("com.tencent.mm.plugin.sns.storage.n", classLoader);
        XposedHelpers.findAndHookMethod("com.tencent.mm.plugin.sns.model.aj", classLoader, "a"
                , n, boy, String.class, int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        new Thread(() -> getObject(param.args[1])).start();
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    }
                });

        XposedHelpers.findAndHookMethod("com.tencent.mm.sdk.platformtools.c", classLoader, "a",
                int.class, String.class, byte[].class, Uri.class, boolean.class, float.class, int.class,
                int.class, XposedHelpers.findClass("com.tencent.mm.sdk.platformtools.MMBitmapFactory$DecodeResultLogger", classLoader),
                int.class, int[].class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Log.d("head_?", String.valueOf(param.args[1]));
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
                        Log.d("test_type", fields[j].getType().getName());
                        if (fields[j].getType().getName().equalsIgnoreCase("java.util.LinkedList")) {
                            LinkedList linkedList = (LinkedList) fields[j].get(object);

                            Log.i("get_object_LinkedList", fields[j].getName() + " \n ");
                            if (linkedList != null && linkedList.size() > 0) {
                                for (int i = 0; i < linkedList.size(); i++) {
                                    Log.i("get_object_name", linkedList.get(i).getClass().getName() + " \n ");
                                    getObject(linkedList.get(i));
                                }
                            }
                        } else if (fields[j].getType().getName().equalsIgnoreCase("java.util.ArrayList")) {
                            ArrayList arrayList = (ArrayList) fields[j].get(object);
                            Log.i("get_object_ArrayList", fields[j].getName() + " \n ");
                            if (arrayList != null && arrayList.size() > 0) {
                                for (int i = 0; i < arrayList.size(); i++) {
                                    Log.i("get_object_name", arrayList.get(i).getClass().getName() + " \n ");
                                    getObject(arrayList.get(i));
                                }
                            }
                        } else if (fields[j].getType().getName().equalsIgnoreCase("java.util.HashSet")) {
                            HashSet hashSet = (HashSet) fields[j].get(object);
                            Log.i("get_object_HashSet", fields[j].getName() + " \n ");
                            if (hashSet != null) {
                                for (Object o : hashSet) {
                                    Log.i("get_object_name", o.getClass().getName() + " \n ");
                                    getObject(o);
                                }
                            }
                        } else if (fields[j].getType().getName().equalsIgnoreCase("java.util.Vector")) {
                            Vector vector = (Vector) fields[j].get(object);
                            Log.i("get_object_Vector", fields[j].getName() + " \n ");
                            if (vector != null && vector.size() > 0) {
                                for (int i = 0; i < vector.size(); i++) {
                                    Log.i("get_object_name", vector.get(i).getClass().getName() + " \n ");
                                    getObject(vector.get(i));
                                }
                            }
                        } else {

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
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 获取数据类型   * @param object   * @return
     */
    public static String getType(Object object) {
        String typeName = object.getClass().getName();
        int length = typeName.lastIndexOf(".");
        String type = typeName.substring(length + 1);
        return type;

    }
}


