package com.example.administrator.weixinhookdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;

/**
 * Created by Administrator on 2018/4/25.
 */

public class BaseActivity extends SubActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
