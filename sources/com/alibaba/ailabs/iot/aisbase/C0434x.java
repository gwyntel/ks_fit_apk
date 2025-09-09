package com.alibaba.ailabs.iot.aisbase;

import aisscanner.ScanSettings;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.alibaba.ailabs.iot.aisbase.x, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0434x implements Parcelable.Creator<ScanSettings> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public ScanSettings createFromParcel(Parcel parcel) {
        return new ScanSettings(parcel, null);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public ScanSettings[] newArray(int i2) {
        return new ScanSettings[i2];
    }
}
