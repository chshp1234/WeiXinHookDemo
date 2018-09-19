package com.example.administrator.weixinhookdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;

import com.blankj.utilcode.util.LogUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NetworkSignalUtil {
    public static final String TAG = "MicroMsg.NetworkSignalUtil";
    private static Context context = null;
    private static long strength = 10000;

    @SuppressLint("WrongConstant")
    public static void initNetworkSignalUtil(Context context) {
        NetworkSignalUtil.context = context;
        final TelephonyManager telephonyManager = ((TelephonyManager) context.getSystemService("phone"));
        if (telephonyManager != null) {
            telephonyManager.listen(new PhoneStateListener() {
                @Override
                public final void onSignalStrengthsChanged(SignalStrength signalStrength) {
                    super.onSignalStrengthsChanged(signalStrength);
                    NetworkSignalUtil.calSignalStrength(telephonyManager, signalStrength);
                }
            }, 256);
        }
    }

    public static long getNetworkSignalStrength(boolean z) {
        return 0;
    }

    public static long getGSMSignalStrength() {
        return strength;
    }

    public static long getWifiSignalStrength() {
        @SuppressLint("WrongConstant") WifiInfo connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
        if (connectionInfo == null || connectionInfo.getBSSID() == null) {
            LogUtils.d("Can Not Get Wifi Signal");
            return 0;
        }
        int calculateSignalLevel = WifiManager.calculateSignalLevel(connectionInfo.getRssi(), 11);
        LogUtils.d("Wifi Signal:" + (calculateSignalLevel * 10));
        if (calculateSignalLevel > 10) {
            calculateSignalLevel = 10;
        }
        if (calculateSignalLevel < 0) {
            calculateSignalLevel = 0;
        }
        return (long) (calculateSignalLevel * 10);
    }

    private static void calSignalStrength(TelephonyManager telephonyManager, SignalStrength signalStrength) {
        int gsmSignalStrength;
        long j;
        if (telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_LTE) {
                        /*String signalInfo = signalStrength.toString();
                        String[] params = signalInfo.split(" ");


                        LogUtils.d("LTE Signal:" + Integer.parseInt(params[9]));
                        for (String s : params) {
                            LogUtils.d("signalInfo:" + s);
                        }*/

            try {
                Method method;
                method = signalStrength.getClass().getMethod("getDbm");
                gsmSignalStrength = (int) method.invoke(signalStrength);
                gsmSignalStrength = (gsmSignalStrength + 113) / 2;
                LogUtils.d("LTE Signal (reflect):" + method.invoke(signalStrength));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                gsmSignalStrength = 0;
            }


        } else if (signalStrength.isGsm()) {
            gsmSignalStrength = signalStrength.getGsmSignalStrength();
        } else {
            gsmSignalStrength = (signalStrength.getCdmaDbm() + 113) / 2;
        }
        if (signalStrength.isGsm() && gsmSignalStrength == 99) {
            LogUtils.d("Can Not Get GSM Signal");
            j = -1;
        } else {
            j = (long) (((float) gsmSignalStrength) * 3.22580645f);
            strength = j;
            j = j > 100 ? 100 : strength;
            strength = j;
            j = j < 0 ? 0 : strength;
        }
        LogUtils.d("gsmSignalStrength", gsmSignalStrength);
        strength = j;
    }
}
