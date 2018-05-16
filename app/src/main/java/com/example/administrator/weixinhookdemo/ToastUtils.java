package com.example.administrator.weixinhookdemo;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/4/23.
 */

public class ToastUtils {
    private static Handler handler = new Handler(Looper.getMainLooper());
    private static WeakReference<Toast> sWeakToast;

    /**
     * Toast发送消息，默认Toast.LENGTH_SHORT
     *
     * @param msg
     * @author WikerYong   Email:<a href="#">yw_312@foxmail.com</a>
     * @version 2012-5-22 上午11:13:10
     */
    public static void showMessageShort(final String msg) {
        showMessage(msg, Toast.LENGTH_SHORT);
    }

    /**
     * Toast发送消息，默认Toast.LENGTH_LONG
     *
     * @param msg
     * @author WikerYong   Email:<a href="#">yw_312@foxmail.com</a>
     * @version 2012-5-22 上午11:13:10
     */
    public static void showMessageLong(final String msg) {
        showMessage(msg, Toast.LENGTH_LONG);
    }

    /**
     * Toast发送消息，默认Toast.LENGTH_SHORT
     *
     * @param msg
     * @author WikerYong   Email:<a href="#">yw_312@foxmail.com</a>
     * @version 2012-5-22 上午11:13:35
     */
    public static void showMessageShort(final int msg) {
        showMessage(msg, Toast.LENGTH_SHORT);
    }

    /**
     * Toast发送消息，默认Toast.LENGTH_LONG
     *
     * @param msg
     * @author WikerYong   Email:<a href="#">yw_312@foxmail.com</a>
     * @version 2012-5-22 上午11:13:35
     */
    public static void showMessageLong(final int msg) {
        showMessage(msg, Toast.LENGTH_LONG);
    }

    /**
     * Toast发送消息
     *
     * @param msg
     * @param len
     * @author WikerYong   Email:<a href="#">yw_312@foxmail.com</a>
     * @version 2012-5-22 上午11:14:09
     */
    public static void showMessage(final int msg,
                                   final int len) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                cancel();
                final Toast toast = Toast.makeText(MyApplication.getContext(), msg, len);
                sWeakToast = new WeakReference<>(toast);
                toast.show();
            }
        });
    }

    /**
     * Toast发送消息
     *
     * @param msg
     * @param len
     * @author WikerYong   Email:<a href="#">yw_312@foxmail.com</a>
     * @version 2012-5-22 上午11:14:27
     */
    public static void showMessage(final String msg,
                                   final int len) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                cancel();
                final Toast toast = Toast.makeText(MyApplication.getContext(), msg, len);
                sWeakToast = new WeakReference<>(toast);
                toast.show();
            }
        });
    }

    /**
     * Cancel the toast.
     */
    public static void cancel() {
        final Toast toast;
        if (sWeakToast != null && (toast = sWeakToast.get()) != null) {
            toast.cancel();
            sWeakToast = null;
        }
    }
}
