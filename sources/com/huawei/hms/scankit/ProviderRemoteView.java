package com.huawei.hms.scankit;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/* loaded from: classes4.dex */
public class ProviderRemoteView extends LinearLayout {
    public ProviderRemoteView(Context context, boolean z2, boolean z3) {
        super(context);
        a(z2, z3);
    }

    private void a(boolean z2, boolean z3) {
        if (z2) {
            LayoutInflater.from(getContext()).inflate(R.layout.scankit_zxl_capture_customed, this);
        } else if (z3) {
            LayoutInflater.from(getContext()).inflate(R.layout.scankit_zxl_capture, this);
        } else {
            LayoutInflater.from(getContext()).inflate(R.layout.scankit_zxl_capture_new, this);
        }
    }

    public ProviderRemoteView(Context context, boolean z2) {
        this(context, z2, true);
    }
}
