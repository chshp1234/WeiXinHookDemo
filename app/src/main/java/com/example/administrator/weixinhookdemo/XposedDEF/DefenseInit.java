package com.example.administrator.weixinhookdemo.XposedDEF;

import android.util.Log;

import java.io.File;

import de.robv.android.xposed.XposedBridge;

/**
 * Created by Administrator on 2018/7/5.
 */

public class DefenseInit {
    private static DefenseInit defenseInit;

    public static DefenseInit getInstance() {
        if (defenseInit != null) {
            return defenseInit;
        }
        synchronized (DefenseInit.class) {
            DefenseInit hVar;
            if (defenseInit != null) {
                hVar = defenseInit;
                return hVar;
            }
            defenseInit = new DefenseInit();
            hVar = defenseInit;
            return hVar;
        }
    }


    public void DefenseWX(ClassLoader classLoader, String version) {
        try {
            switch (version) {
                case "6.6.3":
                    Defense663.Defense(classLoader);
                    break;
                case "6.6.7":
                    Defense667.Defense(classLoader);
                    break;
                default:
                    break;
            }
        } catch (Throwable throwable) {
            XposedBridge.log(Log.getStackTraceString(throwable));
        }

    }

    public void deleteWechatDex() {
        try {
            deleteDirectory(new File("/data/data/com.tencent.mm/tinker"));
            deleteDirectory(new File("/data/user_de/0/com.tencent.mm/tinker"));
            deleteDirectory(new File("/data/data/com.tencent.mm/tinker_temp"));
            deleteDirectory(new File("/data/user_de/0/com.tencent.mm/tinker_temp"));
        } catch (Throwable th) {
            XposedBridge.log(Log.getStackTraceString(th));
        }
    }

    private void deleteDirectory(File file) {
        try {
            if (file.exists()) {
                File[] listFiles = file.listFiles();
                if (listFiles != null) {
                    for (int i = 0; i < listFiles.length; i++) {
                        if (listFiles[i].isDirectory()) {
                            deleteDirectory(listFiles[i]);
                        } else {
                            listFiles[i].delete();
                        }
                    }
                } else {
                    return;
                }
            }
            file.delete();
        } catch (Throwable th) {
            XposedBridge.log(Log.getStackTraceString(th));
        }
    }
}
