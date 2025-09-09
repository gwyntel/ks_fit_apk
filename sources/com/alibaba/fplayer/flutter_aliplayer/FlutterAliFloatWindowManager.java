package com.alibaba.fplayer.flutter_aliplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import androidx.annotation.NonNull;

/* loaded from: classes2.dex */
public class FlutterAliFloatWindowManager {
    private Boolean isMove = Boolean.FALSE;
    private int lastX;
    private int lastY;
    private Context mContext;
    private FlutterAliPlayerView mCurrentView;
    private final DisplayMetrics mDisplayMetrics;
    private final WindowManager.LayoutParams mLayoutParams;
    private final SurfaceView mSurfaceView;
    private final WindowManager mWindowManager;
    private int paramX;
    private int paramY;

    @SuppressLint({"ClickableViewAccessibility"})
    public FlutterAliFloatWindowManager(Context context) {
        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        this.mDisplayMetrics = this.mContext.getResources().getDisplayMetrics();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(350, 450, 0, 0, -2);
        this.mLayoutParams = layoutParams;
        if (Build.VERSION.SDK_INT >= 26) {
            layoutParams.type = 2038;
        } else {
            layoutParams.type = 2003;
        }
        layoutParams.format = 1;
        layoutParams.flags = 40;
        layoutParams.x = r0.widthPixels - 100;
        layoutParams.y = r0.heightPixels - 100;
        SurfaceView surfaceView = new SurfaceView(context);
        this.mSurfaceView = surfaceView;
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliFloatWindowManager.1
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
                if (FlutterAliFloatWindowManager.this.mCurrentView != null) {
                    FlutterAliFloatWindowManager.this.mCurrentView.getPlayer().surfaceChanged();
                }
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                if (FlutterAliFloatWindowManager.this.mCurrentView != null) {
                    FlutterAliFloatWindowManager.this.mCurrentView.getPlayer().setDisplay(surfaceHolder);
                }
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
            }
        });
        surfaceView.setOnTouchListener(new View.OnTouchListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliFloatWindowManager.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == 0) {
                    FlutterAliFloatWindowManager.this.lastX = (int) motionEvent.getRawX();
                    FlutterAliFloatWindowManager.this.lastY = (int) motionEvent.getRawY();
                    FlutterAliFloatWindowManager flutterAliFloatWindowManager = FlutterAliFloatWindowManager.this;
                    flutterAliFloatWindowManager.paramX = flutterAliFloatWindowManager.mLayoutParams.x;
                    FlutterAliFloatWindowManager flutterAliFloatWindowManager2 = FlutterAliFloatWindowManager.this;
                    flutterAliFloatWindowManager2.paramY = flutterAliFloatWindowManager2.mLayoutParams.y;
                    return true;
                }
                if (action != 2) {
                    return true;
                }
                int rawX = ((int) motionEvent.getRawX()) - FlutterAliFloatWindowManager.this.lastX;
                int rawY = ((int) motionEvent.getRawY()) - FlutterAliFloatWindowManager.this.lastY;
                FlutterAliFloatWindowManager.this.mLayoutParams.x = FlutterAliFloatWindowManager.this.paramX + rawX;
                FlutterAliFloatWindowManager.this.mLayoutParams.y = FlutterAliFloatWindowManager.this.paramY + rawY;
                FlutterAliFloatWindowManager.this.mWindowManager.updateViewLayout(FlutterAliFloatWindowManager.this.mSurfaceView, FlutterAliFloatWindowManager.this.mLayoutParams);
                return true;
            }
        });
    }

    public void hideFloatWindow() {
        SurfaceView surfaceView = this.mSurfaceView;
        if (surfaceView != null) {
            this.mWindowManager.removeView(surfaceView);
        }
        FlutterAliPlayerView flutterAliPlayerView = this.mCurrentView;
        if (flutterAliPlayerView != null) {
            flutterAliPlayerView.setRenderView();
            this.mCurrentView = null;
        }
    }

    public void showFloatWindow(FlutterAliPlayerView flutterAliPlayerView) {
        this.mCurrentView = flutterAliPlayerView;
        this.mWindowManager.addView(this.mSurfaceView, this.mLayoutParams);
    }
}
