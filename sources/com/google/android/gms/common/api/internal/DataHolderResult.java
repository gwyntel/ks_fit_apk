package com.google.android.gms.common.api.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;

@KeepForSdk
/* loaded from: classes3.dex */
public abstract class DataHolderResult implements Result, Releasable {

    /* renamed from: a, reason: collision with root package name */
    protected final Status f12693a;

    /* renamed from: b, reason: collision with root package name */
    protected final DataHolder f12694b;

    @Override // com.google.android.gms.common.api.Result
    @NonNull
    @KeepForSdk
    public Status getStatus() {
        return this.f12693a;
    }

    @Override // com.google.android.gms.common.api.Releasable
    @KeepForSdk
    public void release() {
        DataHolder dataHolder = this.f12694b;
        if (dataHolder != null) {
            dataHolder.close();
        }
    }
}
