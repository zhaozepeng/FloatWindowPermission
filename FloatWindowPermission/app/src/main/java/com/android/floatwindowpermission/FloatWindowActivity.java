/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.android.floatwindowpermission;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.permission.FloatWindowManager;

/**
 * Description:
 *
 * @author zhaozp
 * @since 2016-10-17
 */

public class FloatWindowActivity extends AppCompatActivity {

    private TextView tv_content;
    private String content;
    private View btn_nullPoint;
    private View btn_nullPoint2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        tv_content = ((TextView) findViewById(R.id.tv_content));
        btn_nullPoint = findViewById(R.id.nullPoint);
        btn_nullPoint2 = findViewById(R.id.nullPoint2);
        findViewById(R.id.btn_show_or_apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatWindowManager.getInstance().applyOrShowFloatWindow(FloatWindowActivity.this);
            }
        });

        findViewById(R.id.btn_dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatWindowManager.getInstance().dismissWindow();
            }
        });

        btn_nullPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_content.setText(content);
            }
        });
        btn_nullPoint2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_content = null;
                tv_content.setText(content);
            }
        });
    }

}
