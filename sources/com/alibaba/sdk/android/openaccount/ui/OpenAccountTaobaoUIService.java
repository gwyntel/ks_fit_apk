package com.alibaba.sdk.android.openaccount.ui;

import android.content.Context;
import com.alibaba.sdk.android.openaccount.callback.LoginCallback;

/* loaded from: classes2.dex */
public interface OpenAccountTaobaoUIService {
    void openLogin(Context context, boolean z2, LoginCallback loginCallback);

    void showLogin(Context context, boolean z2, LoginCallback loginCallback);
}
