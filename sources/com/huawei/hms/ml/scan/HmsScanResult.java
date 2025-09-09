package com.huawei.hms.ml.scan;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class HmsScanResult implements Parcelable {
    public static final Parcelable.Creator<HmsScanResult> CREATOR = new Parcelable.Creator<HmsScanResult>() { // from class: com.huawei.hms.ml.scan.HmsScanResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HmsScanResult createFromParcel(Parcel parcel) {
            return new HmsScanResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HmsScanResult[] newArray(int i2) {
            return new HmsScanResult[i2];
        }
    };
    public static final int SCAN_NEED_ZOOM = 4098;
    public static final int SCAN_OVEREXPOSED = 4099;
    public static final int SCAN_PARSE_FAILED = 4097;
    public static final int SCAN_UNDEREXPOSED = 4100;
    private HmsScan[] hmsScans;
    private int state;

    public HmsScanResult(int i2, HmsScan[] hmsScanArr) {
        this.state = i2;
        this.hmsScans = hmsScanArr;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public HmsScan[] getHmsScans() {
        return this.hmsScans;
    }

    public int getState() {
        return this.state;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.state);
        parcel.writeTypedArray(this.hmsScans, i2);
    }

    protected HmsScanResult(Parcel parcel) {
        this.state = parcel.readInt();
        this.hmsScans = (HmsScan[]) parcel.createTypedArray(HmsScan.CREATOR);
    }
}
