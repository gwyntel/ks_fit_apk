package com.alibaba.sdk.android.openaccount.callback;

import com.alibaba.sdk.android.openaccount.model.OpenAccountSession;

/* loaded from: classes2.dex */
public interface LoginCallback extends FailureCallback {
    void onSuccess(OpenAccountSession openAccountSession);
}
