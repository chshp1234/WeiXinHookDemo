package com.example.administrator.weixinhookdemo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.facebook.stetho.common.LogUtil;

import java.io.File;
import java.security.MessageDigest;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity {

    @BindView(R.id.qrcode)
    ImageView imageView;

    @BindView(R.id.jump)
    Button jump;

    @Override
    protected void initView() {
        String fileName = Environment.getExternalStorageDirectory() + File.separator + "QRCODE.jpg";

        //        BitmapFactory.Options options = new BitmapFactory.Options();
        //        options.inJustDecodeBounds = true;
        //        try {
        //            FileInputStream stream = new FileInputStream(fileName);
        //            Bitmap bitmap = BitmapFactory.decodeStream(stream);
        //            imageView.setImageBitmap(bitmap);
        //        } catch (FileNotFoundException e) {
        //            e.printStackTrace();
        //        }

        LogUtils.d(initDbPassword(initPhoneIMEI(),"1968249727"));

        Bitmap bitmap = BitmapFactory.decodeFile(fileName);
        LogUtils.d(bitmap == null ? "null" : bitmap.getHeight());
        LogUtils.d("fileName: " + fileName);
        LogUtils.d("isFileExists: " + FileUtils.isFileExists(fileName));
        imageView.setImageBitmap(bitmap);
        jump.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Class two =
                                    Class.forName(
                                            "com.example.administrator.weixinhookdemo.TwoActivity");

                            Intent intent =
                                    new Intent(MainActivity.this, two.newInstance().getClass());
                            startActivityForResult(intent, 1);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                });

        jump.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Intent intent = new Intent(MainActivity.this, EncryptUtilsUi.class);
                        startActivity(intent);
                        return false;
                    }
                });
    }

    @Override
    protected void initData() {}

    @Override
    int getContentId() {
        return R.layout.activity_main;
    }

    private String getRunningActivityName() {
        ActivityManager activityManager =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity =
                activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        return runningActivity;
    }



    /**
     * 获取手机的imei码
     *
     * @return
     */
    private String initPhoneIMEI() {
        TelephonyManager tm = (TelephonyManager) MyApplication.getContext().getSystemService(TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 根据imei和uin生成的md5码，获取数据库的密码（去前七位的小写字母）
     *
     * @param imei
     * @param uin
     * @return
     */
    private String initDbPassword(String imei, String uin) {
        if (TextUtils.isEmpty(imei) || TextUtils.isEmpty(uin)) {
            LogUtil.d("初始化数据库密码失败：imei或uid为空");
            return "";
        }
        String md5 = md5(imei + uin);
        String password = md5.substring(0, 7).toLowerCase();
        return password;
    }

    /**
     * md5加密
     *
     * @param content
     * @return
     */
    private String md5(String content) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(content.getBytes("UTF-8"));
            byte[] encryption = md5.digest(); // 加密
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    sb.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    sb.append(Integer.toHexString(0xff & encryption[i]));
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
