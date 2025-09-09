package com.huawei.agconnect.core.service.auth;

import com.huawei.hmf.tasks.Task;

/* loaded from: classes3.dex */
public interface AuthProvider {
    void addTokenListener(OnTokenListener onTokenListener);

    Task<Token> getTokens();

    Task<Token> getTokens(boolean z2);

    String getUid();

    void removeTokenListener(OnTokenListener onTokenListener);
}
