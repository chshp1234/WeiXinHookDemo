package com.example.administrator.weixinhookdemo.xposed;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.weixinhookdemo.WeiXinHookDemo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/** Created by Administrator on 2018/7/10. */
public class PrintMessage667 {
    public static void print(final ClassLoader classLoader) throws ClassNotFoundException {

        Class aVar = classLoader.loadClass("com.tencent.mm.ab.d$a");
        Class sVar = classLoader.loadClass("com.tencent.mm.plugin.messenger.foundation.a.s");
        //             hook接受消息方法，并打印出消息具体参数
        findAndHookMethod(
                "com.tencent.mm.plugin.messenger.foundation.c",
                classLoader,
                "a",
                aVar,
                sVar,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                        Class aVar2 = param.args[0].getClass();
                        Log.d("foundation_c", aVar2.toString());
                        for (Field f : aVar2.getFields()) {
                            Log.d("Field_name", f.getName());
                            Log.d("Field_getDeclaringClass", f.getDeclaringClass().getName());
                        }

                        Object dIN = aVar2.getField("dIN").get(param.args[0]);
                        Log.d("Field_name", dIN.getClass().getName());

                        Class dINClass = dIN.getClass();

                        Object rcj = dINClass.getField("rcj").get(dIN);
                        Object from = rcj.getClass().getField("siM").get(rcj);

                        Object rck = dINClass.getField("rck").get(dIN);
                        Object to = rck.getClass().getField("siM").get(rck);

                        Object rcq = dINClass.getField("rcq").get(dIN);

                        Object rci = dINClass.getField("rci").get(dIN);

                        Object rcr = dINClass.getField("rcr").get(dIN);

                        Object hcd = dINClass.getField("hcd").get(dIN);

                        Object jQd = dINClass.getField("jQd").get(dIN);

                        Integer lOH = (Integer) dINClass.getField("lOH").get(dIN);

                        Object rcm = dINClass.getField("rcm").get(dIN);

                        Object rcn = dINClass.getField("rcn").get(dIN);
                        byte[] bytes = new byte[0];
                        String bytesString = null;
                        if (rcn != null) {
                            Object siK = rcn.getClass().getField("siK").get(rcn);
                            if (siK != null) {
                                Method method = siK.getClass().getMethod("toByteArray");
                                Method methodString = siK.getClass().getMethod("cfV");
                                bytes = (byte[]) method.invoke(siK);
                                bytesString = (String) methodString.invoke(siK);
                                //                                    Bitmap bitmap =
                                //
                                // BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                //
                                // ImageUtils.saveImageToGallery(bitmap);
                            }
                        }

                        String rco = "";
                        String rcp = "";
                        Object rcoObject = dINClass.getField("rco").get(dIN);
                        if (rcoObject != null) {
                            rco = rcoObject.toString();
                        }

                        Object rcpObject = dINClass.getField("rcp").get(dIN);
                        if (rcpObject != null) {
                            rcp = rcpObject.toString();
                        }

                        String content = "";
                        Object rcl = dINClass.getField("rcl").get(dIN);
                        if (rcl != null) {
                            content = (String) rcl.getClass().getField("siM").get(rcl);
                        }

                        Log.d("WXMessage", "from：" + from.toString() + "\n");
                        Log.d("WXMessage", "to：" + to.toString() + "\n");
                        Log.d("WXMessage", "id_1：" + rcq.toString() + "\n");
                        Log.d("WXMessage", "id_2：" + rci.toString() + "\n");
                        Log.d("WXMessage", "id_3：" + rcr.toString() + "\n");
                        Log.d("WXMessage", "status：" + hcd.toString() + "\n");
                        Log.d("WXMessage", "type：" + jQd.toString() + "\n");
                        Log.d("WXMessage", "time_1：" + lOH.toString() + "\n");
                        Log.d(
                                "WXMessage",
                                "time_2："
                                        + new SimpleDateFormat("[yy-MM-dd HH:mm:ss]")
                                                .format(
                                                        new java.util.Date(
                                                                1000 * Long.valueOf(lOH)))
                                        + "\n");
                        Log.d(
                                "WXMessage",
                                "diff："
                                        + (System.currentTimeMillis() / 1000 - Long.valueOf(lOH))
                                        + "\n");
                        Log.d("WXMessage", "imgstatus：" + rcm.toString() + "\n");
                        Log.d("WXMessage", "imgbuf：" + bytesString + "\n");
                        Log.d("WXMessage", "imgbuf.length：" + bytes.length + "\n");
                        Log.d("WXMessage", "src：" + rco + "\n");
                        Log.d("WXMessage", "src.length：" + rco.length() + "\n");
                        Log.d("WXMessage", "push：" + rcp + "\n");
                        Log.d("WXMessage", "push.length：" + rcp.length() + "\n");
                        Log.d("WXMessage", "content：" + content + "\n" + " ");
                    }
                });

        findAndHookMethod(
                "com.tencent.mm.plugin.sns.ui.SettingSnsBackgroundUI",
                classLoader,
                "onResume",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Log.d(
                                "getAccSnsTmpPath",
                                String.valueOf(
                                        XposedHelpers.callStaticMethod(
                                                XposedHelpers.findClass(
                                                        "com.tencent.mm.plugin.sns.model.af",
                                                        classLoader),
                                                "getAccSnsTmpPath")));
                    }
                });
    }
}
