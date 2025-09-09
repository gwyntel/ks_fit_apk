package com.alibaba.sdk.android.oauth.callback;

import com.alibaba.sdk.android.openaccount.callback.FailureCallback;
import com.alibaba.sdk.android.openaccount.model.OpenAccountLink;

/* loaded from: classes2.dex */
public interface OABindCallback extends FailureCallback {
    void onSuccess(OpenAccountLink openAccountLink);
}
