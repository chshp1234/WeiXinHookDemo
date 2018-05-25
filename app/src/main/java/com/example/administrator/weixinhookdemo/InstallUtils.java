package com.example.administrator.weixinhookdemo;

import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ShellUtils;

import java.io.File;
import java.util.Locale;

/**
 * @author xiexin on 2017-12-18.
 * @e-mail jobs_xie@enable-ets.com
 * @description
 */
public final class InstallUtils {

    public static boolean installAppSilent(String apkFile) {
        if (TextUtils.isEmpty(apkFile)) {
            return false;
        }
        File file = new File(apkFile);
        if (!FileUtils.isFileExists(file)) {
            return false;
        }
        // 为路径增加引号, 防止静默安装失败
        apkFile = '"' + file.getAbsolutePath() + '"';
        String command = "LD_LIBRARY_PATH=/vendor/lib*:/system/lib* pm install -r " + apkFile;
        boolean isRoot = DeviceUtils.isDeviceRooted();
        // 先调用root
        ShellUtils.CommandResult result = ShellUtils.execCmd(command, isRoot, true);
        String successMsg = result.successMsg;
        if (successMsg != null && successMsg.toLowerCase(Locale.US).contains("success")) {
            return true;
        } else {
            // 不成功则调用取反, 即su/sh各运行一次, 成功就成功了, 要是不成功, 调用手动安装
            LogUtils.e(
                    "installAppSilent successMsg: "
                            + result.successMsg
                            + ", errorMsg: "
                            + result.errorMsg);
            result = ShellUtils.execCmd(command, !isRoot, true);
            successMsg = result.successMsg;
            return successMsg != null && successMsg.toLowerCase(Locale.US).contains("success");
        }
    }

    public static boolean uninstallAppSilent(String packageName, boolean isKeepData) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        boolean isRoot = DeviceUtils.isDeviceRooted();
        String command =
                "LD_LIBRARY_PATH=/vendor/lib*:/system/lib* pm uninstall "
                        + (isKeepData ? "-k " : "")
                        + packageName;
        ShellUtils.CommandResult commandResult = ShellUtils.execCmd(command, isRoot, true);
        if (commandResult.successMsg != null
                && commandResult.successMsg.toLowerCase().contains("success")) {
            return true;
        } else {
            LogUtils.e(
                    "uninstallAppSilent successMsg: "
                            + commandResult.successMsg
                            + ", errorMsg: "
                            + commandResult.errorMsg);
            commandResult = ShellUtils.execCmd(command, !isRoot, true);
            return commandResult.successMsg != null
                    && commandResult.successMsg.toLowerCase().contains("success");
        }
    }
}
