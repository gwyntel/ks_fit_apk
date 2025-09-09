package com.yalantis.ucrop;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;

/* loaded from: classes4.dex */
public class UCropInitializer {
    public UCropInitializer setOkHttpClient(@NonNull OkHttpClient okHttpClient) {
        OkHttpClientStore.INSTANCE.a(okHttpClient);
        return this;
    }
}
