package com.alibaba.ailabs.iot.aisbase;

import aisscanner.ScanResult;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.alibaba.ailabs.iot.aisbase.w, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0432w implements Parcelable.Creator<ScanResult> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public ScanResult createFromParcel(Parcel parcel) {
        return new ScanResult(parcel, null);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public ScanResult[] newArray(int i2) {
        return new ScanResult[i2];
    }
}
