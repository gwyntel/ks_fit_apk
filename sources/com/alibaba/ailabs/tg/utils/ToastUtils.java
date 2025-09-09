package com.alibaba.ailabs.tg.utils;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.alibaba.ailabs.tg.UtilsConfig;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public final class ToastUtils {
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    private static int sLayoutId = -1;
    private static Toast sToast;
    private static WeakReference<View> sViewWeakReference;

    private ToastUtils() {
    }

    public static void cancel() {
        Toast toast = sToast;
        if (toast != null) {
            toast.cancel();
            sToast = null;
        }
    }

    private static View getView(@LayoutRes int i2) {
        WeakReference<View> weakReference;
        View view;
        if (sLayoutId == i2 && (weakReference = sViewWeakReference) != null && (view = weakReference.get()) != null) {
            return view;
        }
        View viewInflate = ((LayoutInflater) UtilsConfig.getInstance().getAppContext().getSystemService("layout_inflater")).inflate(i2, (ViewGroup) null);
        sViewWeakReference = new WeakReference<>(viewInflate);
        sLayoutId = i2;
        return viewInflate;
    }

    private static boolean isBackground() {
        return AppUtils.isAppBackground(UtilsConfig.getInstance().getAppContext());
    }

    private static void show(@StringRes int i2, int i3) {
        show(UtilsConfig.getInstance().getAppContext().getResources().getText(i2).toString(), i3);
    }

    public static View showCustomLong(@LayoutRes int i2) {
        View view = getView(i2);
        show(view, 1);
        return view;
    }

    public static View showCustomShort(@LayoutRes int i2) {
        View view = getView(i2);
        show(view, 0);
        return view;
    }

    private static void showInCenter(final CharSequence charSequence, final int i2) {
        if (isBackground()) {
            return;
        }
        HANDLER.post(new Runnable() { // from class: com.alibaba.ailabs.tg.utils.ToastUtils.2
            @Override // java.lang.Runnable
            public void run() {
                ToastUtils.cancel();
                Toast unused = ToastUtils.sToast = Toast.makeText(UtilsConfig.getInstance().getAppContext(), charSequence, i2);
                ToastUtils.sToast.setGravity(17, 0, 0);
                ToastUtils.sToast.show();
            }
        });
    }

    public static void showLong(@NonNull CharSequence charSequence) {
        show(charSequence, 1);
    }

    public static void showShort(@NonNull CharSequence charSequence) {
        show(charSequence, 0);
    }

    public static void showShortInCenter(@NonNull CharSequence charSequence) {
        showInCenter(charSequence, 0);
    }

    private static void show(@StringRes int i2, int i3, Object... objArr) {
        show(String.format(UtilsConfig.getInstance().getAppContext().getResources().getString(i2), objArr), i3);
    }

    public static void showLong(@StringRes int i2) {
        show(i2, 1);
    }

    public static void showShort(@StringRes int i2) {
        show(i2, 0);
    }

    private static void show(String str, int i2, Object... objArr) {
        show(String.format(str, objArr), i2);
    }

    public static void showLong(@StringRes int i2, Object... objArr) {
        show(i2, 1, objArr);
    }

    public static void showShort(@StringRes int i2, Object... objArr) {
        show(i2, 0, objArr);
    }

    private static void show(final CharSequence charSequence, final int i2) {
        if (isBackground()) {
            return;
        }
        HANDLER.post(new Runnable() { // from class: com.alibaba.ailabs.tg.utils.ToastUtils.1
            @Override // java.lang.Runnable
            public void run() {
                ToastUtils.cancel();
                Toast unused = ToastUtils.sToast = Toast.makeText(UtilsConfig.getInstance().getAppContext(), charSequence, i2);
                ToastUtils.sToast.show();
            }
        });
    }

    public static void showLong(String str, Object... objArr) {
        show(str, 1, objArr);
    }

    public static void showShort(String str, Object... objArr) {
        show(str, 0, objArr);
    }

    private static void show(final View view, final int i2) {
        if (isBackground()) {
            return;
        }
        HANDLER.post(new Runnable() { // from class: com.alibaba.ailabs.tg.utils.ToastUtils.3
            @Override // java.lang.Runnable
            public void run() {
                ToastUtils.cancel();
                Toast unused = ToastUtils.sToast = new Toast(UtilsConfig.getInstance().getAppContext());
                ToastUtils.sToast.setView(view);
                ToastUtils.sToast.setDuration(i2);
                ToastUtils.sToast.show();
            }
        });
    }
}
