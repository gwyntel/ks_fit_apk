package com.aliyun.alink.sdk.bone.plugins.ut;

import android.R;
import android.app.Activity;
import android.graphics.Rect;
import android.util.Log;
import android.view.ViewTreeObserver;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class KeyBoardListenerHelper {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference<Activity> f11492a;

    /* renamed from: b, reason: collision with root package name */
    private OnKeyBoardChangeListener f11493b;

    /* renamed from: c, reason: collision with root package name */
    private final ViewTreeObserver.OnGlobalLayoutListener f11494c = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.aliyun.alink.sdk.bone.plugins.ut.KeyBoardListenerHelper.1
        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            ((Activity) KeyBoardListenerHelper.this.f11492a.get()).runOnUiThread(new Runnable() { // from class: com.aliyun.alink.sdk.bone.plugins.ut.KeyBoardListenerHelper.1.1
                @Override // java.lang.Runnable
                public void run() {
                    if (!KeyBoardListenerHelper.this.isActivityValid() || KeyBoardListenerHelper.this.f11493b == null) {
                        return;
                    }
                    try {
                        Rect rect = new Rect();
                        ((Activity) KeyBoardListenerHelper.this.f11492a.get()).getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                        int height = ((Activity) KeyBoardListenerHelper.this.f11492a.get()).getWindow().getDecorView().getHeight() - rect.bottom;
                        KeyBoardListenerHelper.this.f11493b.OnKeyBoardChange(height > 200, (int) ((height * 1.0f) / ((Activity) KeyBoardListenerHelper.this.f11492a.get()).getResources().getDisplayMetrics().density));
                    } catch (Exception e2) {
                        Log.e("BoneSystemPlugin", "onGlobalLayout error:" + e2.getMessage());
                    }
                }
            });
        }
    };

    public interface OnKeyBoardChangeListener {
        void OnKeyBoardChange(boolean z2, int i2);
    }

    public KeyBoardListenerHelper(final Activity activity) {
        this.f11492a = null;
        Log.d("BoneSystemPlugin", "KeyBoardListenerHelper() called with: activity = [" + activity + "]");
        if (activity == null) {
            return;
        }
        try {
            this.f11492a = new WeakReference<>(activity);
            activity.runOnUiThread(new Runnable() { // from class: com.aliyun.alink.sdk.bone.plugins.ut.KeyBoardListenerHelper.2
                @Override // java.lang.Runnable
                public void run() {
                    activity.getWindow().setSoftInputMode(16);
                    ((Activity) KeyBoardListenerHelper.this.f11492a.get()).findViewById(R.id.content).getViewTreeObserver().addOnGlobalLayoutListener(KeyBoardListenerHelper.this.f11494c);
                }
            });
        } catch (Exception e2) {
            Log.e("BoneSystemPlugin", "KeyBoardListenerHelper error:" + e2.getMessage());
        }
    }

    public void destroy() {
        Log.i("BoneSystemPlugin", "destroy");
        if (isActivityValid()) {
            try {
                this.f11492a.get().runOnUiThread(new Runnable() { // from class: com.aliyun.alink.sdk.bone.plugins.ut.KeyBoardListenerHelper.3
                    @Override // java.lang.Runnable
                    public void run() {
                        ((Activity) KeyBoardListenerHelper.this.f11492a.get()).findViewById(R.id.content).getViewTreeObserver().removeOnGlobalLayoutListener(KeyBoardListenerHelper.this.f11494c);
                    }
                });
            } catch (Exception e2) {
                Log.e("BoneSystemPlugin", "destroy error:" + e2.getMessage());
            }
        }
    }

    public boolean isActivityValid() {
        WeakReference<Activity> weakReference = this.f11492a;
        return (weakReference == null || weakReference.get() == null) ? false : true;
    }

    public void setOnKeyBoardChangeListener(OnKeyBoardChangeListener onKeyBoardChangeListener) {
        Log.i("BoneSystemPlugin", "setOnKeyBoardChangeListener");
        this.f11493b = onKeyBoardChangeListener;
    }
}
