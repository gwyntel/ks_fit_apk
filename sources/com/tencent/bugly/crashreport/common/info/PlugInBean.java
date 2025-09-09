package com.tencent.bugly.crashreport.common.info;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class PlugInBean implements Parcelable {
    public static final Parcelable.Creator<PlugInBean> CREATOR = new Parcelable.Creator<PlugInBean>() { // from class: com.tencent.bugly.crashreport.common.info.PlugInBean.1
        @Override // android.os.Parcelable.Creator
        public final /* synthetic */ PlugInBean createFromParcel(Parcel parcel) {
            return new PlugInBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final /* bridge */ /* synthetic */ PlugInBean[] newArray(int i2) {
            return new PlugInBean[i2];
        }
    };

    /* renamed from: a, reason: collision with root package name */
    public final String f20594a;

    /* renamed from: b, reason: collision with root package name */
    public final String f20595b;

    /* renamed from: c, reason: collision with root package name */
    public final String f20596c;

    public PlugInBean(String str, String str2, String str3) {
        this.f20594a = str;
        this.f20595b = str2;
        this.f20596c = str3;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "plid:" + this.f20594a + " plV:" + this.f20595b + " plUUID:" + this.f20596c;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.f20594a);
        parcel.writeString(this.f20595b);
        parcel.writeString(this.f20596c);
    }

    public PlugInBean(Parcel parcel) {
        this.f20594a = parcel.readString();
        this.f20595b = parcel.readString();
        this.f20596c = parcel.readString();
    }
}
