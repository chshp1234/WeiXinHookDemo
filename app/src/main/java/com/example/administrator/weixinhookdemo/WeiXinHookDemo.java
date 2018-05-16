package com.example.administrator.weixinhookdemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * @author Administrator
 * @date 2018/3/30
 */
public class WeiXinHookDemo implements IXposedHookLoadPackage {
    XposedLog663 xposedLog663 = new XposedLog663();

    // log关键字
    // ——intent_|hook_

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        if ("com.tencent.mm".equals(lpparam.packageName)) {
            // 打印日志
            xposedLog663.findAndPrintLog(lpparam);

            Class x = XposedHelpers.findClass("com.tencent.mm.storage.x", lpparam.classLoader);
            Class arp =
                    XposedHelpers.findClass("com.tencent.mm.protocal.c.arp", lpparam.classLoader);
            Class ol = XposedHelpers.findClass("com.tencent.mm.protocal.c.ol", lpparam.classLoader);
            Class aVar = lpparam.classLoader.loadClass("com.tencent.mm.ae.d$a");
            Class rVar =
                    lpparam.classLoader.loadClass("com.tencent.mm.plugin.messenger.foundation.a.r");

            findAndHookMethod(
                    "com.tencent.mm.storage.ad",
                    lpparam.classLoader,
                    "S",
                    x,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                            Log.d(
                                    "hook_insert",
                                    "field_username="
                                            + param.args[0]
                                                    .getClass()
                                                    .getField("field_username")
                                                    .get(param.args[0])
                                                    .toString());
                        }
                    });

            findAndHookMethod(
                    "com.tencent.mm.plugin.bbom.c",
                    lpparam.classLoader,
                    "a",
                    x,
                    arp,
                    boolean.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            Log.d(
                                    "hook_update",
                                    "field_username="
                                            + param.args[0]
                                                    .getClass()
                                                    .getField("field_username")
                                                    .get(param.args[0])
                                                    .toString());
                            Log.d(
                                    "hook_update",
                                    "field_encryptUsername="
                                            + param.args[0]
                                                    .getClass()
                                                    .getField("field_encryptUsername")
                                                    .get(param.args[0])
                                                    .toString());
                        }
                    });

            findAndHookMethod(
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
                    });

            findAndHookMethod(
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
                                            + arp.getClass().getField("wzi").get(arp).toString());
                        }
                    });

            // hook onCreate方法，并输出intent信息
            findAndHookMethod(
                    "android.app.Activity",
                    lpparam.classLoader,
                    "onCreate",
                    Bundle.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            Activity activity = (Activity) param.thisObject;
                            Log.d(
                                    activity.getLocalClassName() + "——hook_onCreate",
                                    "======================onCreate=======================");
                            Log.d("hook_onCreate", activity.getLocalClassName());
                            Intent intent = activity.getIntent();
                            if (intent.getExtras() != null) {
                                for (String s : intent.getExtras().keySet()) {
                                    String value =
                                            intent.getExtras().get(s) == null
                                                    ? ""
                                                    : intent.getExtras().get(s).toString();

                                    Log.d(activity.getLocalClassName() + "——intent_key", s);
                                    Log.d(
                                            activity.getLocalClassName() + "——intent_value",
                                            value == null || value.trim().length() == 0
                                                    ? " "
                                                    : value);
                                }
                            }
                        }
                    });

            // hook扫描二维码（失败）
            /*findAndHookMethod(
            "com.tencent.mm.plugin.scanner.ui.p",
            lpparam.classLoader,
            "hJ",
            boolean.class,
            new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Rect rect = new Rect();
                    String fileName =
                            Environment.getExternalStorageDirectory()
                                    + File.separator
                                    + "vuctrl"
                                    + File.separator
                                    + "QRCODE.jpg";
                    Log.d("fileName", fileName);
                    Bitmap bitmap = BitmapFactory.decodeFile(fileName);
                    rect = new Canvas(bitmap).getClipBounds();
                    param.setResult(rect);
                }
            });*/

            // hook onResume方法
            findAndHookMethod(
                    "android.app.Activity",
                    lpparam.classLoader,
                    "onResume",
                    new XC_MethodHook() {

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            Activity activity = (Activity) param.thisObject;
                            Log.d("hook_onResume", activity.getLocalClassName());
                        }
                    });

            // hook onResume方法
            findAndHookMethod(
                    "android.app.Activity",
                    lpparam.classLoader,
                    "onActivityResult",
                    int.class,
                    int.class,
                    Intent.class,
                    new XC_MethodHook() {

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            Activity activity = (Activity) param.thisObject;
                            Intent onActivityResultIntent = (Intent) param.args[2];
                            Log.d(
                                    activity.getLocalClassName() + "——hook_onActivityResult",
                                    "=====================onActivityResult=====================");
                            Log.d(
                                    "hook_onActivityResult",
                                    activity.getLocalClassName()
                                            + "\n"
                                            + "requestCode="
                                            + param.args[0]
                                            + "\n"
                                            + "resultCode="
                                            + param.args[1]);
                            if (onActivityResultIntent != null
                                    && onActivityResultIntent.getExtras() != null) {

                                for (String s : onActivityResultIntent.getExtras().keySet()) {
                                    String value =
                                            onActivityResultIntent.getExtras().get(s) == null
                                                    ? ""
                                                    : onActivityResultIntent
                                                            .getExtras()
                                                            .get(s)
                                                            .toString();

                                    Log.d(activity.getLocalClassName() + "——intent_key", s);
                                    Log.d(
                                            activity.getLocalClassName() + "——intent_value",
                                            value == null || value.trim().length() == 0
                                                    ? " "
                                                    : value);
                                }
                            }
                        }
                    });

            //             hook接受消息方法，并打印出消息具体参数
            findAndHookMethod(
                    "com.tencent.mm.plugin.messenger.foundation.c",
                    lpparam.classLoader,
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
                            if (vHb != null) {
                                Object wJD = vHb.getClass().getField("wJD").get(vHb);
                                if (wJD != null) {
                                    Method method = wJD.getClass().getMethod("toByteArray");
                                    bytes = (byte[]) method.invoke(wJD);
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

                            Object vGZ = hmqClass.getField("vGZ").get(hmq);
                            Object content = vGZ.getClass().getField("wJF").get(vGZ);

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
                                            + (System.currentTimeMillis() / 1000
                                                    - Long.valueOf(pbl))
                                            + "\n");
                            Log.d("WXMessage", "imgstatus：" + vHa.toString() + "\n");
                            Log.d("WXMessage", "imgbuf：" + bytes.length + "\n");
                            Log.d("WXMessage", "src：" + vHc.length() + "\n");
                            Log.d("WXMessage", "push：" + vHd.length() + "\n");
                            Log.d("WXMessage", "content：" + content.toString() + "\n");
                        }
                    });
        }
    }

    /** 获取方法参数，并打印参数名、参数类型 */
    private void getMethdAndParameter(
            final XC_LoadPackage.LoadPackageParam param, String className) {
        try {
            Class<?> clazz = param.classLoader.loadClass(className);
            // 获取本类的所有方法，存放入数组
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                Log.d("foundation_c", "方法名：" + method.getName());
                // 获取本方法所有参数类型，存入数组
                Class<?>[] getTypeParameters = method.getParameterTypes();
                if (getTypeParameters.length == 0) {
                    Log.d("foundation_c", "此方法无参数");
                }
                for (Class<?> class1 : getTypeParameters) {
                    String parameterName = class1.getName();
                    Log.d("foundation_c", "参数类型：" + parameterName);
                }
                Log.d("foundation_c", "****************************");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
