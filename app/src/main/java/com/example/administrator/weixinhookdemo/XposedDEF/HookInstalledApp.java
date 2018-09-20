package com.example.administrator.weixinhookdemo.XposedDEF;

import android.app.ActivityManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * Created by Administrator on 2018/7/5.
 */

public class HookInstalledApp {

    public void hook(ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", classLoader, "getInstalledApplications", Integer.TYPE, new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) {
                try {
                    List<ApplicationInfo> list = (List) methodHookParam.getResult();
                    List arrayList = new ArrayList();
                    if (list != null) {
                        for (ApplicationInfo applicationInfo : list) {
                            String str = applicationInfo.packageName;
                            ApplicationInfo applicationInfo2 = a(applicationInfo);
                            if (a(str)) {
//                        XposedBridge.log(Log.getStackTraceString(th));
//                        p.a("Hid package: " + str);
                            } else {
                                arrayList.add(applicationInfo2);
                            }
                        }
                        methodHookParam.setResult(arrayList);
                    }
                } catch (Throwable th) {
                    XposedBridge.log(Log.getStackTraceString(th));
                }
            }
        });
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", classLoader, "getInstalledPackages", Integer.TYPE, new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) {
                try {
                    List<PackageInfo> list = (List) methodHookParam.getResult();
                    List arrayList = new ArrayList();
                    if (list != null) {
                        for (PackageInfo packageInfo : list) {
                            String str = packageInfo.packageName;
                            if (a(str)) {
//                        XposedBridge.log(Log.getStackTraceString(th));
//                        p.a("Hid package: " + str);
                            } else {
                                arrayList.add(packageInfo);
                            }
                        }
                        methodHookParam.setResult(arrayList);
                    }
                } catch (Throwable th) {
                    XposedBridge.log(Log.getStackTraceString(th));
                }
            }
        });
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", classLoader, "getPackageInfo", String.class, Integer.TYPE, new XC_MethodHook() {

            @Override
            protected void beforeHookedMethod(MethodHookParam methodHookParam) {
                try {
                    String str = (String) methodHookParam.args[0];
                    if (a(str)) {
                        methodHookParam.args[0] = "com.tencent.mm";
//                    XposedBridge.log(Log.getStackTraceString(th));
//                    p.a("Fake package: " + str + " as " + "com.tencent.mm");
                    }
                } catch (Throwable th) {
                    XposedBridge.log(Log.getStackTraceString(th));
                }
            }
        });
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", classLoader, "getApplicationInfo", String.class, Integer.TYPE, new XC_MethodHook() {

            @Override
            protected void beforeHookedMethod(MethodHookParam methodHookParam) {
                try {
                    String str = (String) methodHookParam.args[0];
                    if (a(str)) {
                        methodHookParam.args[0] = "com.tencent.mm";
//                    XposedBridge.log(Log.getStackTraceString(th));
//                    p.a("Fake package: " + str + " as " + "com.tencent.mm");
                    }
                } catch (Throwable th) {
                    XposedBridge.log(Log.getStackTraceString(th));
                }
            }
        });
        XposedHelpers.findAndHookMethod("android.app.ActivityManager", classLoader, "getRunningServices", Integer.TYPE, new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) {
                try {
                    List<ActivityManager.RunningServiceInfo> list = (List) methodHookParam.getResult();
                    List arrayList = new ArrayList();
                    if (list != null) {
                        for (ActivityManager.RunningServiceInfo runningServiceInfo : list) {
                            String str = runningServiceInfo.process;
                            if (a(str)) {
//                        XposedBridge.log(Log.getStackTraceString(th));
//                        p.a("Hid service: " + str);
                            } else {
                                arrayList.add(runningServiceInfo);
                            }
                        }
                        methodHookParam.setResult(arrayList);
                    }
                } catch (Throwable th) {
                    XposedBridge.log(Log.getStackTraceString(th));
                }
            }
        });
        XposedHelpers.findAndHookMethod("android.app.ActivityManager", classLoader, "getRunningTasks", Integer.TYPE, new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) {
                try {
                    List<ActivityManager.RunningTaskInfo> list = (List) methodHookParam.getResult();
                    List arrayList = new ArrayList();
                    if (list != null) {
                        for (ActivityManager.RunningTaskInfo runningTaskInfo : list) {
                            String flattenToString = runningTaskInfo.baseActivity.flattenToString();
                            if (a(flattenToString)) {
//                        XposedBridge.log(Log.getStackTraceString(th));
//                        p.a("Hid task: " + flattenToString);
                            } else {
                                arrayList.add(runningTaskInfo);
                            }
                        }
                        methodHookParam.setResult(arrayList);
                    }
                } catch (Throwable th) {
                    XposedBridge.log(Log.getStackTraceString(th));
                }
            }
        });
        XposedHelpers.findAndHookMethod("android.app.ActivityManager", classLoader, "getRunningAppProcesses", new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam methodHookParam) {
                try {
                    List<ActivityManager.RunningAppProcessInfo> list = (List) methodHookParam.getResult();

                    List arrayList = new ArrayList();
                    if (list != null) {
                        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : list) {
                            String str = runningAppProcessInfo.processName;
                            if (a(str)) {
//                        XposedBridge.log(Log.getStackTraceString(th));
//                        p.a("Hid process: " + str);
                            } else {
                                arrayList.add(runningAppProcessInfo);
                            }
                        }
                        methodHookParam.setResult(arrayList);
                    }
                } catch (Throwable th) {
                    XposedBridge.log(Log.getStackTraceString(th));
                }

            }
        });
    }

    private boolean a(String str) {
        return str.contains("wechatluckymoney") || str.contains("xposed") || str.contains("forcestopgb") || str.contains("green.running");
    }

    private ApplicationInfo a(ApplicationInfo applicationInfo) {
//        p.a("appInfo : " + applicationInfo.packageName);
        Bundle bundle = applicationInfo.metaData;
        if (bundle != null) {
//            a(bundle, "before");
            if (bundle.containsKey("xposedmodule")) {
                bundle.remove("xposedmodule");
            }
            if (bundle.containsKey("xposedminversion")) {
                bundle.remove("xposedminversion");
            }
            if (bundle.containsKey("xposeddescription")) {
                bundle.remove("xposeddescription");
            }
        }
        applicationInfo.metaData = bundle;
        /*if (bundle != null) {
            a(bundle, "after");
        }*/
        return applicationInfo;
    }

    /*public static void a(Bundle bundle, String str) {
        if (bundle != null) {
            try {
                Object<String> keySet = bundle.keySet();
                List arrayList = new ArrayList();
                arrayList.addAll(keySet);
                p.a(str + " Bundle::keySet:" + arrayList.toString());
                for (String str2 : keySet) {
                    try {
                        Object obj = bundle.get(str2);
                        if (obj == null) {
                            p.a(str2 + " : " + null);
                        } else if (byte[].class.getCanonicalName().equals(obj.getClass().getCanonicalName())) {
                            p.a(str2 + " byte[]: " + ((byte[]) bundle.get(str2)).length);
                        } else {
                            p.a(obj.getClass().getCanonicalName() + "->" + str2 + " : " + bundle.get(str2));
                        }
                    } catch (Throwable th) {
                    }
                }
            } catch (Throwable th2) {
                p.a("error : " + th2);
            }
        }
    }*/

}
