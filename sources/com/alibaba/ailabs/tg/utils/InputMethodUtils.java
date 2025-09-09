package com.alibaba.ailabs.tg.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/* loaded from: classes2.dex */
public class InputMethodUtils {
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager;
        if (activity == null || activity.isFinishing() || (inputMethodManager = (InputMethodManager) activity.getSystemService("input_method")) == null || activity.getWindow() == null || activity.getWindow().getDecorView() == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }

    public static void showSoftKeyboard(Activity activity, View view) {
        InputMethodManager inputMethodManager;
        if (activity == null || activity.isFinishing() || view == null || (inputMethodManager = (InputMethodManager) activity.getSystemService("input_method")) == null) {
            return;
        }
        inputMethodManager.showSoftInput(view, 2);
    }

    public static void toggleSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
        if (inputMethodManager == null) {
            return;
        }
        inputMethodManager.toggleSoftInput(2, 0);
    }

    public static void showSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager;
        if (activity == null || activity.isFinishing() || (inputMethodManager = (InputMethodManager) activity.getSystemService("input_method")) == null) {
            return;
        }
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus == null) {
            currentFocus = new View(activity);
        }
        inputMethodManager.showSoftInput(currentFocus, 2);
    }
}
