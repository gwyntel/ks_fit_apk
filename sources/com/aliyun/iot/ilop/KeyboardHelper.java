package com.aliyun.iot.ilop;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/* loaded from: classes3.dex */
public class KeyboardHelper {
    public static KeyboardHelper newInstance() {
        return new KeyboardHelper();
    }

    public void hideSoftInputForHw(Activity activity, View view) {
        InputMethodManager inputMethodManager;
        if (!"HUAWEI".equalsIgnoreCase(Build.MANUFACTURER) || Build.VERSION.SDK_INT < 27 || (inputMethodManager = (InputMethodManager) activity.getSystemService("input_method")) == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 2);
    }
}
