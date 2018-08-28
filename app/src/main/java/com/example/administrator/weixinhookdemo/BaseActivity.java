package com.example.administrator.weixinhookdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/** Created by Administrator on 2018/4/25. */
abstract class BaseActivity extends SubActivity {

    Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentId());
        unbinder = ButterKnife.bind(this);
        initData();
        initView();
    }

    /** 初始化数据 */
    protected abstract void initData();

    /** 初始化布局 */
    protected abstract void initView();



    /**
     * 获得布局ID
     *
     * @return 布局ID
     */
    abstract int getContentId();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
