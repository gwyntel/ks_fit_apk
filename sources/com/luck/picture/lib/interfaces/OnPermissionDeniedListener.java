package com.luck.picture.lib.interfaces;

import androidx.fragment.app.Fragment;

/* loaded from: classes4.dex */
public interface OnPermissionDeniedListener {
    void onDenied(Fragment fragment, String[] strArr, int i2, OnCallbackListener<Boolean> onCallbackListener);
}
