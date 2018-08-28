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
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.UriUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;

import static android.Manifest.permission.READ_PHONE_STATE;

public class MainActivity extends BaseActivity {

    @BindView(R.id.wechat_log)
    CheckBox wechatLog;

    @BindView(R.id.jump)
    Button jump;

    @BindView(R.id.install)
    Button install;

    @BindView(R.id.uninstall)
    Button uninstall;

    @BindView(R.id.reboot)
    Button reboot;

    private boolean isWeChatLg;

    private Handler timeHandler =
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    LogUtils.d(msg.getData().getString("time") + " [百度]");
                }
            };

    private String[] permissions =
            new String[] {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS,
            };

    private static final int PERMISSION_REQUEST = 1;
    private static final int PHONE_PERMISSION = 2;

    @Override
    protected void initView() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        } else {
            LogUtils.d(initDbPassword(initPhoneIMEI(), "1297987475"));
        }

//        SwipeRefreshLayout swipeRefreshLayout = new SwipeRefreshLayout(this);
//        swipeRefreshLayout.setRefreshing(true);

        if (NetworkUtils.getWifiEnabled() && NetworkUtils.isWifiConnected()) {
            WifiManager wifiManager =
                    (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            LogUtils.d("wifi:" + wifiInfo.getSSID());
        } else if (NetworkUtils.isMobileData()) {
            LogUtils.d("getNetworkOperatorName:" + NetworkUtils.getNetworkOperatorName());
            LogUtils.d("getNetworkType:" + NetworkUtils.getNetworkType());
        } else {
            LogUtils.d("");
        }

        if (SPUtils.getInstance().getBoolean("isWeChatLg")) {
            wechatLog.setChecked(true);
            isWeChatLg = true;
        } else {
            wechatLog.setChecked(false);
            isWeChatLg = false;
        }

        wechatLog.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        isWeChatLg = isChecked;
                        SPUtils.getInstance().put("isWeChatLg", isChecked);
                    }
                });

        LogUtils.d("isHooked:" + isHooked());
        //        LogUtils.d("15557939758",EncryptUtils.encryptMD5ToString("15557939758"));
        //        LogUtils.d(4 & 0x0f);

        //                LogUtils.d("IMEI:" + PhoneUtils.getIMEI());

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

        Bitmap bitmap = BitmapFactory.decodeFile(fileName);
        LogUtils.d(bitmap == null ? "null" : bitmap.getHeight());
        LogUtils.d("fileName: " + fileName);
        LogUtils.d("isFileExists: " + FileUtils.isFileExists(fileName));

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
    @RequiresPermission(READ_PHONE_STATE)
    private String initPhoneIMEI() {

        TelephonyManager tm =
                (TelephonyManager) MyApplication.getContext().getSystemService(TELEPHONY_SERVICE);
        LogUtils.d("tm.getDeviceId():" + tm.getDeviceId());
        LogUtils.d("tm.getImei():" + tm.getImei());
        LogUtils.d("PhoneUtils.getIMEI():" + PhoneUtils.getIMEI());
        LogUtils.d("PhoneUtils.getIMSI():" + PhoneUtils.getIMSI());
        LogUtils.d("PhoneUtils.getMEID():" + PhoneUtils.getMEID());
        LogUtils.d("getWeChatIMEI:" + getWeChatIMEI());
        AsyncTask.execute(
                new Runnable() {
                    @Override
                    public void run() {

                        if (values != null && values.size() > 0) {
                            for (Integer integer : values.keySet()) {
                                LogUtils.d("key:" + integer, "values:" + values.get(integer));
                            }
                        }
                    }
                });
        return tm.getDeviceId();
    }

    private Map<Integer, Object> values = null;

    public String getWeChatIMEI() {
        String filePath = "/data/user/0/com.tencent.mm/MicroMsg/MM_stepcounter.cfg";
        LogUtils.d("filePath:" + getFilesDir().getParentFile().getAbsolutePath());
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        Throwable e;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.length() == 0) {
                values = new HashMap();
            } else {
                fileInputStream = new FileInputStream(file);
                try {
                    objectInputStream = new ObjectInputStream(fileInputStream);
                    try {
                        values = (Map) objectInputStream.readObject();
                        objectInputStream.close();
                        fileInputStream.close();
                        try {
                            fileInputStream.close();
                        } catch (Throwable e2) {
                        }
                        try {
                            objectInputStream.close();
                        } catch (Throwable e22) {
                        }
                    } catch (Exception e3) {
                        e = e3;
                        try {
                            values = new HashMap();
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (Throwable e222) {
                                }
                            }
                            if (objectInputStream != null) {
                                try {
                                    objectInputStream.close();
                                } catch (Throwable e2222) {
                                }
                            }
                        } catch (Throwable th) {
                            e = th;
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (Throwable e4) {
                                }
                            }
                            if (objectInputStream != null) {
                                try {
                                    objectInputStream.close();
                                } catch (Throwable e5) {
                                }
                            }
                            throw e;
                        }
                    }
                } catch (Exception e6) {
                    e = e6;
                    objectInputStream = null;
                    values = new HashMap();
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    if (objectInputStream != null) {
                        objectInputStream.close();
                    }
                } catch (Throwable th2) {
                    e = th2;
                    objectInputStream = null;
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    if (objectInputStream != null) {
                        objectInputStream.close();
                    }
                    throw e;
                }
            }
        } catch (Exception e7) {
            e = e7;
            objectInputStream = null;
            fileInputStream = null;
            values = new HashMap();
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (Throwable th3) {
            e = th3;
            objectInputStream = null;
            fileInputStream = null;
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            try {
                throw e;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        return (String) values.get(Integer.valueOf(258));
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
            LogUtils.d("初始化数据库密码失败：imei或uid为空");
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

    private void checkPermission() {
        if (!PermissionCheckUtil.hasReadPhoneState(this)) {
            ActivityCompat.requestPermissions(this, permissions, PHONE_PERMISSION);
        }
        if (!PermissionCheckUtil.hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST:
                Log.i(
                        "onRequestPermissionsResult",
                        "onActivityResult: request code is : "
                                + requestCode
                                + " result code is : "
                                + Arrays.toString(grantResults));
                //                LogUtils.d(initDbPassword(initPhoneIMEI(), "1297987475"));
                break;
            case PHONE_PERMISSION:
                Log.i("onRequestPermissionsResult", "onRequestPermissionsResult: phone permission");
                //                ((TextView) findViewById(R.id.imei)).setText(AppUtil.getImei());

                LogUtils.d(initDbPassword(initPhoneIMEI(), "1297987475"));
                break;
            default:
                break;
        }
    }
}
