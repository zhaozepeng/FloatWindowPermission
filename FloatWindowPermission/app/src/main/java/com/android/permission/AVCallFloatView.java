/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.android.permission;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.facishare.fs.common_utils.FSScreen;
import com.facishare.fs.pluginapi.HostInterfaceManager;
import com.fxiaoke.plugin.avcall.R;
import com.fxiaoke.plugin.avcall.common.utils.AVLogUtils;
import com.fxiaoke.plugin.avcall.common.utils.CommonUtils;
import com.fxiaoke.plugin.avcall.ui.AVActivity;

/**
 * Description:
 *
 * @author zhaozp
 * @since 2016-05-19
 */
public class AVCallFloatView extends FrameLayout {
    private static final String TAG = "AVCallFloatView";

    /**
     * 记录手指按下时在小悬浮窗的View上的横坐标的值
     */
    private float xInView;

    /**
     * 记录手指按下时在小悬浮窗的View上的纵坐标的值
     */
    private float yInView;
    /**
     * 记录当前手指位置在屏幕上的横坐标值
     */
    private float xInScreen;

    /**
     * 记录当前手指位置在屏幕上的纵坐标值
     */
    private float yInScreen;

    /**
     * 记录手指按下时在屏幕上的横坐标的值
     */
    private float xDownInScreen;

    /**
     * 记录手指按下时在屏幕上的纵坐标的值
     */
    private float yDownInScreen;

    private boolean isAnchoring = false;
    private boolean isShowing = false;

    private View floatView = null;
    private ImageView avcall_phone_a;
    private ImageView avcall_phone_b;
    private ImageView avcall_phone_c;
    private TextView tv_call_time = null;
    private WindowManager windowManager = null;
    private WindowManager.LayoutParams mParams = null;

    private AnimationSet alphaAnimation = null;
    private boolean isFloatClick = false;

    public AVCallFloatView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        windowManager = (WindowManager) HostInterfaceManager.getHostInterface().getApp()
                .getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        floatView = inflater.inflate(R.layout.float_window_layout, null);
        tv_call_time = (TextView) floatView.findViewById(R.id.tv_call_time);
        avcall_phone_a = (ImageView) floatView.findViewById(R.id.avcall_phone_a);
        avcall_phone_b = (ImageView) floatView.findViewById(R.id.avcall_phone_b);
        avcall_phone_c = (ImageView) floatView.findViewById(R.id.avcall_phone_c);

        alphaAnimation = (AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.avcall_phone_alpha);
        alphaAnimation.setRepeatCount(-1000);
        alphaAnimation.setFillAfter(true);
        runAlphaAnimation();
        isFloatClick = false;

        addView(floatView);

    }

    private void runAlphaAnimation() {
        final int per_delay = 334;
        avcall_phone_a.postDelayed(new AlphaAnimationRunnable(avcall_phone_a), per_delay);
        avcall_phone_b.postDelayed(new AlphaAnimationRunnable(avcall_phone_b), per_delay * 3);
        avcall_phone_c.postDelayed(new AlphaAnimationRunnable(avcall_phone_c), per_delay * 5);
    }

    private void alphaAnimator(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0, 1, 1, 1, 0, 0, 0);
        animator.setDuration(2000);
        animator.setRepeatCount(-1);
        animator.start();
    }

    private class AlphaAnimationRunnable implements Runnable {
        private View v;

        public AlphaAnimationRunnable(View v) {
            this.v = v;
        }

        @Override
        public void run() {
            alphaAnimator(v);
        }
    }

    public void setParams(WindowManager.LayoutParams params) {
        mParams = params;
    }

    public void setTime(long time) {
        if (!isShowing) {
            AVLogUtils.e(TAG, "window has been removed, can not be update");
            return;
        }
        if (time <= 0) {
            tv_call_time.setText(getContext().getString(R.string.fav_float_window_disconnect));
        } else {
            tv_call_time.setText(CommonUtils.getTalkTimeString(time));
        }
        windowManager.updateViewLayout(this, mParams);
    }

    public void setIsShowing(boolean isShowing) {
        this.isShowing = isShowing;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isAnchoring) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xInView = event.getX();
                yInView = event.getY();
                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY();
                // 手指移动的时候更新小悬浮窗的位置
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(xDownInScreen - xInScreen) <= ViewConfiguration.get(getContext()).getScaledTouchSlop()
                        && Math.abs(yDownInScreen - yInScreen) <= ViewConfiguration.get(getContext()).getScaledTouchSlop()
                        && !isFloatClick) {
                    // 点击效果
                    AVLogUtils.e(TAG, "float window click");
                    isFloatClick = true;
                    Intent intent = new Intent(getContext(), AVActivity.class);
                    HostInterfaceManager.getAVNotification().floatViewClick(getContext(), intent);
                } else {
                    //吸附效果
                    anchorToSide();
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void anchorToSide() {
        isAnchoring = true;
        Point size = new Point();
        windowManager.getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        int middleX = mParams.x + getWidth() / 2;


        int animTime = 0;
        int xDistance = 0;
        int yDistance = 0;

        int dp_25 = FSScreen.dp2px(getContext(), 15);

        //1
        if (middleX <= dp_25 + getWidth() / 2) {
            xDistance = dp_25 - mParams.x;
        }
        //2
        else if (middleX <= screenWidth / 2) {
            xDistance = dp_25 - mParams.x;
        }
        //3
        else if (middleX >= screenWidth - getWidth() / 2 - FSScreen.dp2px(getContext(), 25)) {
            xDistance = screenWidth - mParams.x - getWidth() - dp_25;
        }
        //4
        else {
            xDistance = screenWidth - mParams.x - getWidth() - dp_25;
        }

        //1
        if (mParams.y < dp_25) {
            yDistance = dp_25 - mParams.y;
        }
        //2
        else if (mParams.y + getHeight() + dp_25 >= screenHeight) {
            yDistance = screenHeight - dp_25 - mParams.y - getHeight();
        }
        Log.e(TAG, "xDistance  " + xDistance + "   yDistance" + yDistance);

        animTime = Math.abs(xDistance) > Math.abs(yDistance) ? (int) (((float) xDistance / (float) screenWidth) * 600f)
                : (int) (((float) yDistance / (float) screenHeight) * 900f);
        this.post(new AnchorAnimRunnable(Math.abs(animTime), xDistance, yDistance, System.currentTimeMillis()));
    }

    private class AnchorAnimRunnable implements Runnable {

        private int animTime;
        private long currentStartTime;
        private Interpolator interpolator;
        private int xDistance;
        private int yDistance;
        private int startX;
        private int startY;

        public AnchorAnimRunnable(int animTime, int xDistance, int yDistance, long currentStartTime) {
            this.animTime = animTime;
            this.currentStartTime = currentStartTime;
            interpolator = new AccelerateDecelerateInterpolator();
            this.xDistance = xDistance;
            this.yDistance = yDistance;
            startX = mParams.x;
            startY = mParams.y;
        }

        @Override
        public void run() {
            if (System.currentTimeMillis() >= currentStartTime + animTime) {
                isAnchoring = false;
                return;
            }
            float delta = interpolator.getInterpolation((System.currentTimeMillis() - currentStartTime) / (float) animTime);
            int xMoveDistance = (int) (xDistance * delta);
            int yMoveDistance = (int) (yDistance * delta);
            Log.e(TAG, "delta:  " + delta + "  xMoveDistance  " + xMoveDistance + "   yMoveDistance  " + yMoveDistance);
            mParams.x = startX + xMoveDistance;
            mParams.y = startY + yMoveDistance;
            if (!isShowing) {
                return;
            }
            windowManager.updateViewLayout(AVCallFloatView.this, mParams);
            AVCallFloatView.this.postDelayed(this, 16);
        }
    }

    private void updateViewPosition() {
        //增加移动误差
        mParams.x = (int) (xInScreen - xInView);
        mParams.y = (int) (yInScreen - yInView);
        Log.e(TAG, "x  " + mParams.x + "   y  " + mParams.y);
        windowManager.updateViewLayout(this, mParams);
    }
}
