package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
final class zzh implements Parcelable.Creator {
    zzh() {
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        return new BinderWrapper(parcel, null);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i2) {
        return new BinderWrapper[i2];
    }
}
