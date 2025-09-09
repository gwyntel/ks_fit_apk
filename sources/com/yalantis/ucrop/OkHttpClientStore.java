package com.yalantis.ucrop;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;

/* loaded from: classes4.dex */
public class OkHttpClientStore {
    public static final OkHttpClientStore INSTANCE = new OkHttpClientStore();
    private OkHttpClient client;

    private OkHttpClientStore() {
    }

    void a(OkHttpClient okHttpClient) {
        this.client = okHttpClient;
    }

    @NonNull
    public OkHttpClient getClient() {
        if (this.client == null) {
            this.client = new OkHttpClient();
        }
        return this.client;
    }
}
