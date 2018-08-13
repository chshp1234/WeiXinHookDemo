package com.example.administrator.weixinhookdemo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Administrator on 2018/4/28.
 */

public class PermissionCheckUtil {
    public static boolean hasReadPhoneState(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) ==
                PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasPermission(Context context, String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }


}
