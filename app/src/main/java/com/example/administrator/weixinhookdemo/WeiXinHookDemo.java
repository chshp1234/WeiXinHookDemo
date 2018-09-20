package com.example.administrator.weixinhookdemo;

import android.content.Context;
import android.util.Log;

import com.example.administrator.weixinhookdemo.XposedDEF.DefenseInit;
import com.example.administrator.weixinhookdemo.xposed.HookLog.XposedLog667_Tinker;
import com.example.administrator.weixinhookdemo.xposed.PrintHookDemo663;
import com.example.administrator.weixinhookdemo.xposed.PrintHookDemo667;
import com.example.administrator.weixinhookdemo.xposed.PrintIntent;
import com.example.administrator.weixinhookdemo.xposed.PrintMessage663;
import com.example.administrator.weixinhookdemo.xposed.PrintMessage667;
import com.example.administrator.weixinhookdemo.xposed.PrintSQL663;
import com.example.administrator.weixinhookdemo.xposed.PrintSQL667;
import com.example.administrator.weixinhookdemo.xposed.HookLog.XposedLog663;
import com.example.administrator.weixinhookdemo.xposed.HookLog.XposedLog667_TXLog;
import com.example.administrator.weixinhookdemo.xposed.HookLog.XposedLog667_log;
import com.example.administrator.weixinhookdemo.xposed.HookLog.XposedLog667_x;
import com.example.administrator.weixinhookdemo.xposed.HookLog.XposedLog667_xlog;

import java.lang.reflect.Method;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.callStaticMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;

/**
 * @author Administrator
 * @date 2018/3/30
 */
public class WeiXinHookDemo implements IXposedHookLoadPackage {
    XposedLog663 xposedLog663 = new XposedLog663();
    XposedLog667_x xposedLog667 = new XposedLog667_x();
    XposedLog667_xlog xposedLog667_xlog = new XposedLog667_xlog();
    XposedLog667_log xposedLog667_log = new XposedLog667_log();
    XposedLog667_TXLog xposedLog667TxLog = new XposedLog667_TXLog();
    XposedLog667_Tinker xposedLog667_tinker = new XposedLog667_Tinker();
    XSharedPreferences xsp;
    public static String WECHAT_VERSION = "";
    public static Context context;
    private boolean isLog;
    //    public static boolean
    // isLog=MyApplication.getContext().getSharedPreferences("",Context.MODE_PRIVATE).getBoolean("isWeChatLg",false);

    public void printLog(XC_LoadPackage.LoadPackageParam lpparam) {
        Log.d("WECHAT_VERSION", WECHAT_VERSION);
        switch (WECHAT_VERSION) {
            case "6.6.3":
                xposedLog663.findAndPrintLog(lpparam);
                break;
            case "6.6.7":
                xposedLog667.findAndPrintLog(lpparam);
                break;
            default:
                break;
        }
    }

    private void printMessage(ClassLoader classLoader) throws ClassNotFoundException {
        switch (WECHAT_VERSION) {
            case "6.6.3":
                PrintMessage663.print(classLoader);
                PrintSQL663.print(classLoader);
                PrintHookDemo663.print(classLoader);
                break;
            case "6.6.7":
                PrintMessage667.print(classLoader);
                PrintSQL667.print(classLoader);
                PrintHookDemo667.print(classLoader);
                break;
            default:
                break;
        }
    }

    // log关键字
    // ——intent_|——hook_|WXMessage

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        if ("com.example.administrator.weixinhookdemo".equals(lpparam.packageName)) {
            findAndHookMethod(
                    "com.example.administrator.weixinhookdemo.MainActivity",
                    lpparam.classLoader,
                    "isHooked",
                    XC_MethodReplacement.returnConstant(true));
        }

        if ("com.tencent.wework.api".equals(lpparam.packageName)) {
            xposedLog667_log.findAndPrintLog(lpparam);

            XposedHelpers.findAndHookMethod("com.tencent.wework.api.WWAPIImpl", lpparam.classLoader, "adu",
                    String.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            Log.i("WWAPIImpl", String.valueOf(param.args[0]));
                        }
                    });

            XposedHelpers.findAndHookMethod("com.tencent.wework.api.WWAPIImpl", lpparam.classLoader, "adt",
                    String.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            Log.i("WWAPIImpl", String.valueOf(param.args[0]));
                        }
                    });
        }

        if ("com.tencent.tinker".equals(lpparam.packageName)) {
            xposedLog667_tinker.findAndPrintLog(lpparam);
        }

        if ("com.tencent.wcdb".equals(lpparam.packageName)) {
            xposedLog667_xlog.findAndPrintLog(lpparam);
        }

        if ("com.tencent.rtmp".equals(lpparam.packageName)) {
            xposedLog667TxLog.findAndPrintLog(lpparam);
        }

        if ("com.tencent.mm".equals(lpparam.packageName)) {

            xsp = new XSharedPreferences("com.example.administrator.weixinhookdemo", "spUtils");
            xsp.makeWorldReadable();
            xsp.reload();
            isLog = xsp.getBoolean("isWeChatLg", false);
            XposedBridge.log("是否开启日志：" + isLog);
            DefenseInit.getInstance().deleteWechatDex();
            context =
                    (Context)
                            callMethod(
                                    callStaticMethod(
                                            findClass("android.app.ActivityThread", null),
                                            "currentActivityThread",
                                            new Object[0]),
                                    "getSystemContext",
                                    new Object[0]);
            WECHAT_VERSION =
                    context.getPackageManager().getPackageInfo(lpparam.packageName, 0).versionName;
            DefenseInit.getInstance().DefenseWX(lpparam.classLoader, WECHAT_VERSION);

//            XSharedPreferences

            // 打印日志
            if (isLog) {
                printLog(lpparam);
            }

            // 打印activity跳转时Intent携带的信息
            PrintIntent.print(lpparam.classLoader);

            // 打印接受的消息
            printMessage(lpparam.classLoader);

            findAndHookMethod("java.io.OutputStream", lpparam.classLoader, "write",
                    byte[].class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            Log.d("OutputStream_write", new String((byte[]) param.args[0], "UTF-8"));
//                            printCallStack();
                        }
                    });

            findAndHookMethod("java.io.DataOutputStream", lpparam.classLoader, "writeBytes",
                    String.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            Log.d("OutputStream_writeBytes", (String) param.args[0]);
                        }
                    });
        }
    }

    /**
     * 获取方法参数，并打印参数名、参数类型
     */
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

    public static void a(String str) {
        Log.d("AntiPatch", str);
    }

    public static void printCallStack() {
        Throwable ex = new Throwable();
        StackTraceElement[] stackElements = ex.getStackTrace();
        if (stackElements != null) {
            for (int i = 0; i < stackElements.length; i++) {
                Log.d("Stack", stackElements[i].getClassName() + "/t");
                Log.d("Stack", stackElements[i].getFileName() + "/t");
                Log.d("Stack", stackElements[i].getLineNumber() + "/t");
                Log.d("Stack", stackElements[i].getMethodName());
                Log.d("Stack", "-----------------------------------");
            }
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
//        XposedInit.activitys.get(XposedInit.activitys.size() - 1).startActivity(intent);*/
