package com.example.administrator.weixinhookdemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.blankj.utilcode.util.*;
import com.blankj.utilcode.util.FileUtils;
import com.facebook.stetho.common.LogUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.qrcode)
    ImageView imageView;

    @BindView(R.id.jump)
    Button jump;

    @BindView(R.id.install)
    Button install;

    @BindView(R.id.uninstall)
    Button uninstall;

    @BindView(R.id.reboot)
    Button reboot;

    private Handler timeHandler =
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
//                    Toast.makeText(MyApplication.getContext(),"哦",Toast.LENGTH_SHORT).show();


                    LogUtils.d(msg.getData().getString("time") + " [百度]");
                }
            };

    @Override
    protected void initView() {
        LogUtils.d("isHooked:" + isHooked());

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LogUtils.d("IMEI:" + PhoneUtils.getIMEI());

        LogUtils.d("wechat_version:" + AppUtils.getAppVersionCode("com.tencent.mm"));
        LogUtils.d("wechat_name:" + AppUtils.getAppVersionName("com.tencent.mm"));

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

        //        String webUrl1 = "http://www.bjtime.cn"; // bjTime
        String webUrl2 = "http://www.baidu.com"; // 百度
        //        String webUrl3 = "http://www.taobao.com"; // 淘宝
        //        String webUrl4 = "http://www.ntsc.ac.cn"; // 中国科学院国家授时中心
        //        String webUrl5 = "http://www.360.cn"; // 360
        //        String webUrl6 = "http://www.beijing-time.org"; // beijing-time
        getWebsiteDatetime(webUrl2);

        LogUtils.d(initDbPassword(initPhoneIMEI(), "1968249727"));

        Bitmap bitmap = BitmapFactory.decodeFile(fileName);
        LogUtils.d(bitmap == null ? "null" : bitmap.getHeight());
        LogUtils.d("fileName: " + fileName);
        LogUtils.d("isFileExists: " + FileUtils.isFileExists(fileName));
        imageView.setImageBitmap(bitmap);
        jump.setOnClickListener(
                new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(View v) {
                        try {
                            Class two =
                                    Class.forName(
                                            "com.example.administrator.weixinhookdemo.TwoActivity");

                            Intent intent =
                                    new Intent(MainActivity.this, two.newInstance().getClass());
                            startActivityForResult(intent, 1);
                        } catch (ClassNotFoundException
                                | InstantiationException
                                | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                });

        jump.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        //                        Intent intent = new Intent(MainActivity.this,
                        // EncryptUtilsUi.class);
                        //                        startActivity(intent);

                        return false;
                    }
                });



        AsyncTask.execute(
                new Runnable() {
                    @Override
                    public void run() {

                        LogUtils.d("network:" + NetworkUtils.isConnected());
                        LogUtils.d("isAvailableByPing:" + NetworkUtils.isAvailableByPing());
                    }
                });

        /*install.setOnClickListener(
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(
                        new Runnable() {
                            @Override
                            public void run() {
                                LogUtils.d("开始安装...");
                                LogUtils.d(
                                        "静默安装："
                                                + InstallUtils.installAppSilent(
                                                        Environment
                                                                        .getExternalStorageDirectory()
                                                                + File.separator
                                                                + "SogouInput_android_v8.20.1_sweb.apk"));
                            }
                        });
            }
        });*/

        /*uninstall.setOnClickListener(
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(
                        new Runnable() {
                            @Override
                            public void run() {
                                LogUtils.d("开始卸载...");
                                LogUtils.d(
                                        "静默卸载："
                                                + InstallUtils.uninstallAppSilent(
                                                        "com.sohu.inputmethod.sogou",
                                                        true));
                            }
                        });
            }
        });*/

        install.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AsyncTask.execute(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        LogUtils.d("开始安装...");
                                        AppUtils.installApp(
                                                Environment.getExternalStorageDirectory()
                                                        + File.separator
                                                        + "SogouInput_android_v8.20.1_sweb.apk");
                                    }
                                });
                    }
                });

        uninstall.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AsyncTask.execute(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        LogUtils.d("开始卸载...");
                                        AppUtils.uninstallAppSilent("com.tencent.mm", false, true);
                                    }
                                });
                    }
                });

        reboot.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeviceUtils.reboot();
                    }
                });

//        LogUtils.d(EncryptUtils.dea );
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
        TelephonyManager tm =
                (TelephonyManager) MyApplication.getContext().getSystemService(TELEPHONY_SERVICE);
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

    /**
     * 获取指定网站的日期时间
     *
     * @param webUrl
     * @return
     * @author SHANHY
     * @date 2015年11月27日
     */
    private void getWebsiteDatetime(final String webUrl) {
        AsyncTask.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // 取得资源对象
                            URL url = new URL(webUrl);
                            // 生成连接对象
                            URLConnection uc = url.openConnection();
                            // 发出连接
                            uc.connect();
                            // 读取网站日期时间
                            long ld = uc.getDate();
                            // 转换为标准时间对象
                            Date date = new Date(ld);
                            // 输出北京时间
                            SimpleDateFormat sdf =
                                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                            String time = sdf.format(date);

                            Message msg = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("time", time);
                            msg.what = 0;
                            msg.setData(bundle);
                            timeHandler.sendMessage(msg);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void gotoWechat() {
        Intent intent = new Intent();
        ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(cmp);
        startActivity(intent);
    }

    private boolean isHooked() {
        return false;
    }
}
