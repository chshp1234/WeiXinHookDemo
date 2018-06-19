package com.example.administrator.weixinhookdemo;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

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

        if ("com.example.administrator.weixinhookdemo".equals(lpparam.packageName)) {
            findAndHookMethod(
                    "com.example.administrator.weixinhookdemo.MainActivity",
                    lpparam.classLoader,
                    "isHooked",
                    XC_MethodReplacement.returnConstant(true));
        }

        if ("com.tencent.mm".equals(lpparam.packageName)) {
            // 打印日志
            //            xposedLog663.findAndPrintLog(lpparam);

            Class x = XposedHelpers.findClass("com.tencent.mm.storage.x", lpparam.classLoader);
            Class arp =
                    XposedHelpers.findClass("com.tencent.mm.protocal.c.arp", lpparam.classLoader);
            Class ol = XposedHelpers.findClass("com.tencent.mm.protocal.c.ol", lpparam.classLoader);
            Class aVar = lpparam.classLoader.loadClass("com.tencent.mm.ae.d$a");
            Class rVar =
                    lpparam.classLoader.loadClass("com.tencent.mm.plugin.messenger.foundation.a.r");
            Class g$a = lpparam.classLoader.loadClass("com.tencent.mm.y.g$a");
            Class a = lpparam.classLoader.loadClass("com.tencent.mm.y.a");
            Class keep_SceneResult =
                    lpparam.classLoader.loadClass("com.tencent.mm.modelcdntran.keep_SceneResult");

            // 数据库操作
            findAndHookMethod(
                    "com.tencent.mm.by.h",
                    lpparam.classLoader,
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
                    lpparam.classLoader,
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
                    lpparam.classLoader,
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
                    lpparam.classLoader,
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

            // 发送图片函数
            findAndHookMethod(
                    "com.tencent.mm.aq.i",
                    lpparam.classLoader,
                    "a",
                    ArrayList.class,
                    String.class,
                    String.class,
                    ArrayList.class,
                    int.class,
                    boolean.class,
                    int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            ArrayList arrayList = (ArrayList) param.args[0];
                            Log.d(
                                    "what_?",
                                    param.args[0]
                                            + "\n"
                                            + param.args[1]
                                            + "\n"
                                            + param.args[2]
                                            + "\n"
                                            + param.args[3]
                                            + "\n"
                                            + param.args[4]
                                            + "\n"
                                            + param.args[5]
                                            + "\n"
                                            + param.args[6]
                                            + "\n");
                        }
                    });

            /*findAndHookMethod(
            "com.tencent.mm.booter.ClickFlowReceiver",
            lpparam.classLoader,
            "onReceive",
            Context.class,
            Intent.class,
            new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Context context = (Context) param.args[0];
                    Intent intent = (Intent) param.args[1];
                    Log.d(
                            "ClickFlowReceiver——hook_BroadcastReceiver",
                            "======================onReceive=======================");
                    if (context != null) {
                        Log.d(
                                "ClickFlowReceiver——hook_BroadcastReceiver",
                                context.getClass().getName());
                    }
                    if (intent.getExtras() != null) {
                        for (String s : intent.getExtras().keySet()) {
                            String value =
                                    intent.getExtras().get(s) == null
                                            ? ""
                                            : intent.getExtras().get(s).toString();

                            Log.d("ClickFlowReceiver——intent_key", s);
                            Log.d(
                                    "ClickFlowReceiver——intent_value",
                                    value == null || value.trim().length() == 0
                                            ? " "
                                            : value);
                        }
                    }
                }
            });*/

            /*findAndHookMethod(
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
                    });*/

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

            XposedHelpers.findAndHookMethod(
                    "android.app.Application",
                    lpparam.classLoader,
                    "onCreate",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            Application application = (Application) param.thisObject;
                            application.registerActivityLifecycleCallbacks(
                                    new Application.ActivityLifecycleCallbacks() {
                                        @Override
                                        public void onActivityCreated(
                                                Activity activity, Bundle savedInstanceState) {
                                            Log.d(
                                                    activity.getLocalClassName()
                                                            + "——hook_onActivityCreated",
                                                    "=======================onActivityCreated=======================");
                                            //                                            Activity
                                            // activity = (Activity) param.thisObject;

                                            Intent intent = activity.getIntent();

                                            StringBuilder sb = new StringBuilder("");
                                            if (intent.getExtras() != null) {
                                                for (String s : intent.getExtras().keySet()) {
                                                    String value =
                                                            intent.getExtras().get(s) == null
                                                                    ? ""
                                                                    : intent.getExtras()
                                                                            .get(s)
                                                                            .toString();
                                                    sb.append(s)
                                                            .append(" = ")
                                                            .append(value)
                                                            .append("\n");
                                                }
                                            }
                                            Log.d(
                                                    activity.getLocalClassName()
                                                            + "——intent_key=value",
                                                    sb.toString());
                                        }

                                        @Override
                                        public void onActivityStarted(Activity activity) {
                                            Log.d(
                                                    activity.getLocalClassName()
                                                            + "——hook_onActivityStarted",
                                                    "onActivityStarted");
                                        }

                                        @Override
                                        public void onActivityResumed(Activity activity) {
                                            Log.d(
                                                    activity.getLocalClassName()
                                                            + "——hook_onActivityResumed",
                                                    "onActivityResumed");
                                        }

                                        @Override
                                        public void onActivityPaused(Activity activity) {
                                            Log.d(
                                                    activity.getLocalClassName()
                                                            + "——hook_onActivityPaused",
                                                    "onActivityPaused");
                                        }

                                        @Override
                                        public void onActivityStopped(Activity activity) {
                                            Log.d(
                                                    activity.getLocalClassName()
                                                            + "——hook_onActivityStopped",
                                                    "onActivityStopped");
                                        }

                                        @Override
                                        public void onActivitySaveInstanceState(
                                                Activity activity, Bundle outState) {
                                            Log.d(
                                                    activity.getLocalClassName()
                                                            + "——hook_onActivitySaveInstanceState",
                                                    "onActivitySaveInstanceState");
                                        }

                                        @Override
                                        public void onActivityDestroyed(Activity activity) {
                                            Log.d(
                                                    activity.getLocalClassName()
                                                            + "——hook_onActivityDestroyed",
                                                    "=======================onActivityDestroyed======================="
                                                            + "\n"
                                                            + " ");
                                        }
                                    });
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
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {}
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
                            //                            Activity activity = (Activity)
                            // param.thisObject;
                            //                            Log.d("hook_onResume",
                            // activity.getLocalClassName());
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
                            StringBuilder sb = new StringBuilder("");
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
                                    sb.append(s).append(" = ").append(value).append("\n");
                                }
                            }
                            Log.d(
                                    activity.getLocalClassName() + "——intent_key=value",
                                    sb.toString());
                        }
                    });

            findAndHookMethod(
                    "com.tencent.mm.plugin.gallery.ui.AlbumPreviewUI",
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
                            StringBuilder sb = new StringBuilder("");
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
                                    sb.append(s).append(" = ").append(value).append("\n");
                                }
                            }
                            Log.d(
                                    activity.getLocalClassName() + "——intent_key=value",
                                    sb.toString());
                        }
                    });

            findAndHookMethod(
                    "com.tencent.mm.ui.chatting.ChattingUI$a",
                    lpparam.classLoader,
                    "onActivityResult",
                    int.class,
                    int.class,
                    Intent.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            //                            Activity activity = (Activity)
                            // param.thisObject;
                            //                            String className =
                            // param.thisObject.getClass().getSimpleName();
                            String className = "ChattingUI";
                            Intent onActivityResultIntent = (Intent) param.args[2];
                            Log.d(
                                    className + "——hook_onActivityResult",
                                    "=====================onActivityResult=====================");
                            Log.d(
                                    "hook_onActivityResult",
                                    className
                                            + "\n"
                                            + "requestCode="
                                            + param.args[0]
                                            + "\n"
                                            + "resultCode="
                                            + param.args[1]);
                            StringBuilder sb = new StringBuilder("");
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
                                    sb.append(s).append(" = ").append(value).append("\n");
                                }
                            }
                            Log.d(className + "——intent_key=value", sb.toString());
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

    public String object2Log(Object o) {
        if (o == null) {
            return "";
        } else if (o instanceof Object[]) {
            return Arrays.asList(o).toString();
        } else {
            return o.toString();
        }
    }
}

//            XposedHelpers.findAndHookMethod("android.support.v4.app.FragmentActivity",
// lpparam.classLoader, "onActivityResult",
//                    int.class, int.class, Intent.class, new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                            int i = (int) param.args[0];
//                            int i2 = (int) param.args[1];
//                            Intent intent = (Intent) param.args[2];
//                            Log.d("requestCode", i + "");
//                            Log.d("resultCode", i2 + "");
//
//                            if (i == 10086) {
//                                intent.putExtra("KSightPath",
// intent.getStringExtra("K_SEGMENTVIDEOPATH"));
//                                intent.putExtra("KSightThumbPath",
// intent.getStringExtra("KSEGMENTVIDEOTHUMBPATH"));
//                                intent.putExtra("sight_md5",
// XposedInit.bV(intent.getStringExtra("K_SEGMENTVIDEOPATH")));
//                                intent.putExtra("KSnsPostManu", true);
//                                intent.putExtra("KTouchCameraTime", System.currentTimeMillis() /
// 1000);
//                                intent.putExtra("Ksnsupload_type", 14);
//                                intent.putExtra("Kis_take_photo", false);
//                                intent.setClassName(activitys.get(activitys.size() -
// 1).getPackageName(),
//                                        "com.tencent.mm.plugin.sns.ui.SnsUploadUI");
//                                context.startActivity(intent);
//                            }
//                        }
//                    });

/*
        for (int i = 0; i < XposedInit.activitys.size(); i++) {
        Log.d("activity_stack", XposedInit.activitys.get(i).getLocalClassName());
        }

        //开启更新页面
//        intent.setClassName(XposedInit.activitys.get(XposedInit.activitys.size() - 1),
//                "com.tencent.mm.sandbox.updater.AppInstallerUI");
//        XposedInit.activitys.get(XposedInit.activitys.size() - 1).startActivity(intent);

        //发送图片
        */
/*intent.setClassName(XposedInit.activitys.get(XposedInit.activitys.size() - 1),
                "com.tencent.mm.ui.chatting.SendImgProxyUI");
        XposedInit.activitys.get(XposedInit.activitys.size() - 1).startActivity(intent);*//*


        Class homeUi = XposedHelpers.findClass("com.tencent.mm.ui.HomeUI", classLoader);
        Class z = XposedHelpers.findClass("com.tencent.mm.ui.z", classLoader);
        Class ChattingUI$a = XposedHelpers.findClass("com.tencent.mm.ui.chatting.ChattingUI$a", classLoader);
        Class v1 = XposedHelpers.findClass("com.tencent.mm.ui.chatting.b.v$1", classLoader);

        Class n = XposedHelpers.findClass("com.tencent.mm.aq.n", classLoader);

        Object xGS=XposedHelpers.newInstance(homeUi);
//        Object xGT = XposedHelpers.getObjectField(XposedInit.context, "xGT");
        Object xGT = XposedHelpers.newInstance(z,xGS);
        Object xLx = XposedHelpers.getObjectField(xGT, "xLx");
//        Object xLx = XposedHelpers.newInstance(ChattingUI$a);
        Object yvT = XposedHelpers.getObjectField(xLx, "yvT");
        Object v1Object = XposedHelpers.newInstance(v1, yvT, intent, "wxid_l3bq5y4o45kt22", 217);
        XposedHelpers.callMethod(v1Object, "aOk");

//        Class i = XposedHelpers.findClass("com.tencent.mm.aq.i", classLoader);
//        Object hCy = XposedHelpers.newInstance(i);
//        Object hBY = XposedHelpers.callStaticMethod(n, "Pn");
//        ArrayList lp = (ArrayList) XposedHelpers.callMethod(hBY, "lp", "csp594027259");
//        if (lp==null){
//            lp = new ArrayList();
//        }else if (lp.size()==0){
//            lp.add(200);
//        }
//        XposedHelpers.callMethod(hCy, "a", lp, "wxid_2fj5waxiuj0d22", "csp594027259",
//                intent.getStringArrayListExtra("CropImage_OutputPath_List"), 0, false, 2130837979);

//        XposedHelpers.callMethod(vObject, "aOk");

        */
/*AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000L);
                    Intent intent1 = new Intent("com.tencent.mm.Intent.ACTION_CLICK_FLOW_REPORT");
                    intent1.putExtra("opCode", 4);
                    intent1.putExtra("ui", XposedInit.activitys.get(XposedInit.activitys.size() - 1)
                            .getComponentName().getClassName());
                    intent1.putExtra("uiHashCode", XposedInit.activitys.get(XposedInit.activitys.size() - 1).hashCode());
                    intent1.putExtra("nowMilliSecond", System.currentTimeMillis());
                    intent1.putExtra("elapsedRealtime", SystemClock.elapsedRealtime());
                    XposedInit.activitys.get(XposedInit.activitys.size() - 1).sendBroadcast(intent1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });*//*


*/
