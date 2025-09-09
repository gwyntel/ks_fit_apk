package com.huawei.hms.scankit.aiscan.common;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public enum BarcodeFormat implements Parcelable {
    AZTEC,
    CODABAR,
    CODE_39,
    CODE_93,
    CODE_128,
    DATA_MATRIX,
    EAN_8,
    EAN_13,
    ITF,
    PDF_417,
    QR_CODE,
    UPC_A,
    UPC_E,
    UPC_EAN_EXTENSION,
    NONE,
    HARMONY_CODE,
    WXCODE;

    public static final Parcelable.Creator<BarcodeFormat> CREATOR = new Parcelable.Creator<BarcodeFormat>() { // from class: com.huawei.hms.scankit.aiscan.common.BarcodeFormat.a
        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public BarcodeFormat createFromParcel(Parcel parcel) {
            return BarcodeFormat.values()[parcel.readInt()];
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public BarcodeFormat[] newArray(int i2) {
            return new BarcodeFormat[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(ordinal());
    }
}
