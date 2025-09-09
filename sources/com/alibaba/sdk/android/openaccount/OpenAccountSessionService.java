package com.alibaba.sdk.android.openaccount;

import com.alibaba.sdk.android.openaccount.model.Result;

/* loaded from: classes2.dex */
public interface OpenAccountSessionService {
    Result<String> getSessionId();

    Result<String> refreshSession(boolean z2);
}
