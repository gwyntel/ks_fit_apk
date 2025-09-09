package com.alibaba.sdk.android.openaccount.ui.widget;

import android.util.Log;
import android.view.View;

/* loaded from: classes2.dex */
public abstract class NonMultiClickListener implements View.OnClickListener {
    private static final int MIN_GAP = 1000;
    private static String TAG = "login.nonClick";
    private long lastClickTime;

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.lastClickTime <= 1000) {
            Log.d(TAG, "click too fast");
        } else {
            this.lastClickTime = jCurrentTimeMillis;
            onMonMultiClick(view);
        }
    }

    public abstract void onMonMultiClick(View view);
}
