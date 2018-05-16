package com.example.administrator.weixinhookdemo;

import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.EncryptUtils;

import butterknife.BindView;

/** Created by Administrator on 2018/5/2. */
public class EncryptUtilsUi extends BaseActivity {

    @BindView(R.id.encrypt)
    Button button;

    @BindView(R.id.encrypt_text)
    AppCompatEditText editText;

    @Override
    protected void initView() {

        if (!"".equals(editText.getText().toString().trim())){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    EncryptUtils.encrypt
                }
            });
        }
    }

    @Override
    protected void initData() {}

    @Override
    int getContentId() {
        return R.layout.layout_encrypt;
    }
}
