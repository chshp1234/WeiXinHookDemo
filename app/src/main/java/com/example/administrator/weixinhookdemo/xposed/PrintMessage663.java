package com.example.administrator.weixinhookdemo.xposed;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/** Created by Administrator on 2018/7/10. */
public class PrintMessage663 {
    public static void print(ClassLoader classLoader) throws ClassNotFoundException {

        Class aVar = classLoader.loadClass("com.tencent.mm.ae.d$a");
        Class rVar = classLoader.loadClass("com.tencent.mm.plugin.messenger.foundation.a.r");
        Class x = XposedHelpers.findClass("com.tencent.mm.storage.x", classLoader);
        Class arp = XposedHelpers.findClass("com.tencent.mm.protocal.c.arp", classLoader);
        Class ol = XposedHelpers.findClass("com.tencent.mm.protocal.c.ol", classLoader);
        Class g$a = classLoader.loadClass("com.tencent.mm.y.g$a");
        Class a = classLoader.loadClass("com.tencent.mm.y.a");
        Class keep_SceneResult =
                classLoader.loadClass("com.tencent.mm.modelcdntran.keep_SceneResult");

        //             hook接受消息方法，并打印出消息具体参数
        findAndHookMethod(
                "com.tencent.mm.plugin.messenger.foundation.c",
                classLoader,
                "a",
                aVar,
                rVar,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                        Class aVar2 = param.args[0].getClass();
                        Log.d("foundation_c", aVar2.toString());
                        for (Field f : aVar2.getFields()) {
                            Log.d("Field_name", f.getName());
                            Log.d("Field_getDeclaringClass", f.getDeclaringClass().getName());
                        }

                        Object hmq = aVar2.getField("hmq").get(param.args[0]);
                        Log.d("Field_name", hmq.getClass().getName());

                        Class hmqClass = hmq.getClass();

                        Object vGX = hmqClass.getField("vGX").get(hmq);
                        Object from = vGX.getClass().getField("wJF").get(vGX);

                        Object vGY = hmqClass.getField("vGY").get(hmq);
                        Object to = vGY.getClass().getField("wJF").get(vGY);

                        Object vHe = hmqClass.getField("vHe").get(hmq);

                        Object vGW = hmqClass.getField("vGW").get(hmq);

                        Object vHf = hmqClass.getField("vHf").get(hmq);

                        Object ktm = hmqClass.getField("ktm").get(hmq);

                        Object ngq = hmqClass.getField("ngq").get(hmq);

                        Integer pbl = (Integer) hmqClass.getField("pbl").get(hmq);

                        Object vHa = hmqClass.getField("vHa").get(hmq);

                        Object vHb = hmqClass.getField("vHb").get(hmq);
                        byte[] bytes = new byte[0];
                        String bytesString = null;
                        if (vHb != null) {
                            Object wJD = vHb.getClass().getField("wJD").get(vHb);
                            if (wJD != null) {
                                Method method = wJD.getClass().getMethod("toByteArray");
                                Method methodString = wJD.getClass().getMethod("cdp");
                                bytes = (byte[]) method.invoke(wJD);
                                bytesString = (String) methodString.invoke(wJD);
                                //                                    Bitmap bitmap =
                                //
                                // BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                //
                                // ImageUtils.saveImageToGallery(bitmap);
                            }
                        }

                        String vHc = "";
                        String vHd = "";
                        Object OvHc = hmqClass.getField("vHc").get(hmq);
                        if (OvHc != null) {
                            vHc = OvHc.toString();
                        }

                        Object OvHd = hmqClass.getField("vHd").get(hmq);
                        if (OvHd != null) {
                            vHd = OvHd.toString();
                        }

                        String content = "";
                        Object vGZ = hmqClass.getField("vGZ").get(hmq);
                        if (vGZ != null) {
                            content = (String) vGZ.getClass().getField("wJF").get(vGZ);
                        }

                        Log.d("WXMessage", "from：" + from.toString() + "\n");
                        Log.d("WXMessage", "to：" + to.toString() + "\n");
                        Log.d("WXMessage", "id_1：" + vHe.toString() + "\n");
                        Log.d("WXMessage", "id_2：" + vGW.toString() + "\n");
                        Log.d("WXMessage", "id_3：" + vHf.toString() + "\n");
                        Log.d("WXMessage", "status：" + ktm.toString() + "\n");
                        Log.d("WXMessage", "type：" + ngq.toString() + "\n");
                        Log.d("WXMessage", "time_1：" + pbl.toString() + "\n");
                        Log.d(
                                "WXMessage",
                                "time_2："
                                        + new SimpleDateFormat("[yy-MM-dd HH:mm:ss]")
                                                .format(
                                                        new java.util.Date(
                                                                1000 * Long.valueOf(pbl)))
                                        + "\n");
                        Log.d(
                                "WXMessage",
                                "diff："
                                        + (System.currentTimeMillis() / 1000 - Long.valueOf(pbl))
                                        + "\n");
                        Log.d("WXMessage", "imgstatus：" + vHa.toString() + "\n");
                        Log.d("WXMessage", "imgbuf：" + new String(bytes) + "\n");
                        Log.d("WXMessage", "imgbuf.length：" + bytes.length + "\n");
                        Log.d("WXMessage", "src：" + vHc + "\n");
                        Log.d("WXMessage", "src.length：" + vHc.length() + "\n");
                        Log.d("WXMessage", "push：" + vHd + "\n");
                        Log.d("WXMessage", "push.length：" + vHd.length() + "\n");
                        Log.d("WXMessage", "content：" + content.toString() + "\n" + " ");
                    }
                });

        // 数据库操作
        findAndHookMethod(
                "com.tencent.mm.by.h",
                classLoader,
                "a",
                String.class,
                String[].class,
                String.class,
                String[].class,
                String.class,
                String.class,
                String.class,
                int.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Log.d(
                                "database_select",
                                "select "
                                        + object2Log(param.args[1])
                                        + "\n"
                                        + "from "
                                        + object2Log(param.args[0])
                                        + "\n"
                                        + "where "
                                        + object2Log(param.args[2])
                                        + "\n"
                                        + "group by "
                                        + object2Log(param.args[4])
                                        + "\n"
                                        + "having "
                                        + object2Log(param.args[5])
                                        + "\n"
                                        + "order by "
                                        + object2Log(param.args[6])
                                        + "\n"
                                        + "limit "
                                        + " "
                                        + "\n"
                                        + object2Log(param.args[7])
                                        + "\n");
                    }
                });

        findAndHookMethod(
                "com.tencent.mm.y.g$a",
                classLoader,
                "a",
                StringBuilder.class,
                g$a,
                String.class,
                keep_SceneResult,
                int.class,
                int.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        StringBuilder builder = (StringBuilder) param.args[0];
                        if (builder != null) {
                            Log.d("what_?", builder.toString());
                        }
                    }
                });

        findAndHookMethod(
                "com.tencent.mm.modelcdntran.d",
                classLoader,
                "a",
                String.class,
                long.class,
                String.class,
                String.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Log.d("what_?", param.getResult().toString());
                    }
                });

        // 网络请求函数...？
        findAndHookMethod(
                "com.tencent.mm.plugin.report.service.g",
                classLoader,
                "i",
                int.class,
                String.class,
                boolean.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Log.d(
                                "what_?",
                                param.args[0] + "\n" + param.args[1] + "\n" + param.args[2]);
                    }
                });
    }

    /*findAndHookMethod(
    "com.tencent.mm.plugin.messenger.foundation.f",
    lpparam.classLoader,
    "a",
    ol,
    byte[].class,
    boolean.class,
    new XC_MethodHook() {
        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            byte[] bytes = (byte[]) param.args[1];
            Log.d("bytes[]", Arrays.toString(bytes));
            Log.d("bytesToString", new String(bytes));
        }
    });*/

    /*findAndHookMethod(
    "com.tencent.mm.plugin.messenger.foundation.a",
    lpparam.classLoader,
    "a",
    arp,
    String.class,
    byte[].class,
    boolean.class,
    boolean.class,
    new XC_MethodHook() {
        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            Object arp = param.args[0];
            Object bdo = arp.getClass().getField("vYI").get(arp);
            Object wJF = bdo.getClass().getField("wJF").get(bdo);

            Log.d(
                    "WXMessage_Strange?",
                    "userName："
                                            + wJF.toString()
                                            + "\n"
                                            + "encryptUsername："
                                            + arp.getClass()
                                                    .getField("wzi")
                                                    .get(arp)
                                    == null
                            ? ""
                            : arp.getClass().getField("wzi").get(arp).toString());
        }
    });*/

    public static String object2Log(Object o) {
        if (o == null) {
            return "";
        } else if (o instanceof Object[]) {
            return Arrays.asList(o).toString();
        } else {
            return o.toString();
        }
    }
}
