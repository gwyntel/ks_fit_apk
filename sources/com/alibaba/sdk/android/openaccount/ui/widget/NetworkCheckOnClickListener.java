package com.alibaba.sdk.android.openaccount.ui.widget;

import android.view.View;
import com.alibaba.sdk.android.openaccount.ui.util.ToastUtils;
import com.alibaba.sdk.android.openaccount.util.NetworkUtils;

/* loaded from: classes2.dex */
public abstract class NetworkCheckOnClickListener implements View.OnClickListener {
    public abstract void afterCheck(View view);

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (NetworkUtils.isNetworkAvaiable(view.getContext())) {
            afterCheck(view);
        } else {
            ToastUtils.toastNetworkError(view.getContext());
        }
    }
}
