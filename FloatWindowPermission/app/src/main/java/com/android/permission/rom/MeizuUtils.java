/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.android.permission.rom;

import android.content.Context;
import android.content.Intent;

public class MeizuUtils {
    private static final String TAG = "MeizuUtils";

    /**
     * 去魅族权限申请页面
     */
    public static void applyPermission(Context context){
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.setClassName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity");
        intent.putExtra("packageName", context.getPackageName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
