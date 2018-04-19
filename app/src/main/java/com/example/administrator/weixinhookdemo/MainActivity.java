package com.example.administrator.weixinhookdemo;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PhoneUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.qrcode)
    ImageView imageView;
    @BindView(R.id.jump)
    Button jump;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

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

                            Intent intent = new Intent(MainActivity.this, two.newInstance().getClass());
                            startActivity(intent);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private String getRunningActivityName(){
        ActivityManager activityManager=(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity=activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        return runningActivity;
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();

    }
}
