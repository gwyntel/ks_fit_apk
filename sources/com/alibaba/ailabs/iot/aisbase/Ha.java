package com.alibaba.ailabs.iot.aisbase;

import android.os.Parcel;
import android.os.Parcelable;
import com.alibaba.ailabs.iot.aisbase.spec.AISManufacturerADData;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class Ha implements Parcelable.Creator<AISManufacturerADData> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public AISManufacturerADData createFromParcel(Parcel parcel) {
        return new AISManufacturerADData(parcel);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public AISManufacturerADData[] newArray(int i2) {
        return new AISManufacturerADData[i2];
    }
}
