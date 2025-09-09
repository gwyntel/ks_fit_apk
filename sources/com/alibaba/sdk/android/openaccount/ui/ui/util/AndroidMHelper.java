package com.alibaba.sdk.android.openaccount.ui.ui.util;

import android.app.Activity;
import android.view.View;

/* loaded from: classes2.dex */
public class AndroidMHelper implements IStatusBarFontHelper {
    @Override // com.alibaba.sdk.android.openaccount.ui.ui.util.IStatusBarFontHelper
    public boolean setStatusBarLightMode(Activity activity, boolean z2) {
        final View decorView = activity.getWindow().getDecorView();
        final int systemUiVisibility = decorView.getSystemUiVisibility();
        if (z2) {
            systemUiVisibility |= 8192;
        }
        decorView.setSystemUiVisibility(systemUiVisibility);
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.util.AndroidMHelper.1
            @Override // android.view.View.OnSystemUiVisibilityChangeListener
            public void onSystemUiVisibilityChange(int i2) {
                if ((i2 & 4) == 0) {
                    decorView.setSystemUiVisibility(systemUiVisibility);
                }
            }
        });
        return true;
    }
}
