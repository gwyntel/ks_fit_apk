package com.google.android.gms.common.api;

import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Result;

/* loaded from: classes3.dex */
public class Response<T extends Result> {
    private Result zza;

    public Response() {
    }

    protected Result e() {
        return this.zza;
    }

    public void setResult(@NonNull T t2) {
        this.zza = t2;
    }

    protected Response(Result result) {
        this.zza = result;
    }
}
