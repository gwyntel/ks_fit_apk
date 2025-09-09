package com.tencent.bugly.crashreport.biz;

import android.os.Parcel;
import android.os.Parcelable;
import com.tencent.bugly.proguard.ap;
import java.util.Map;

/* loaded from: classes4.dex */
public class UserInfoBean implements Parcelable {
    public static final Parcelable.Creator<UserInfoBean> CREATOR = new Parcelable.Creator<UserInfoBean>() { // from class: com.tencent.bugly.crashreport.biz.UserInfoBean.1
        @Override // android.os.Parcelable.Creator
        public final /* synthetic */ UserInfoBean createFromParcel(Parcel parcel) {
            return new UserInfoBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final /* bridge */ /* synthetic */ UserInfoBean[] newArray(int i2) {
            return new UserInfoBean[i2];
        }
    };

    /* renamed from: a, reason: collision with root package name */
    public long f20575a;

    /* renamed from: b, reason: collision with root package name */
    public int f20576b;

    /* renamed from: c, reason: collision with root package name */
    public String f20577c;

    /* renamed from: d, reason: collision with root package name */
    public String f20578d;

    /* renamed from: e, reason: collision with root package name */
    public long f20579e;

    /* renamed from: f, reason: collision with root package name */
    public long f20580f;

    /* renamed from: g, reason: collision with root package name */
    public long f20581g;

    /* renamed from: h, reason: collision with root package name */
    public long f20582h;

    /* renamed from: i, reason: collision with root package name */
    public long f20583i;

    /* renamed from: j, reason: collision with root package name */
    public String f20584j;

    /* renamed from: k, reason: collision with root package name */
    public long f20585k;

    /* renamed from: l, reason: collision with root package name */
    public boolean f20586l;

    /* renamed from: m, reason: collision with root package name */
    public String f20587m;

    /* renamed from: n, reason: collision with root package name */
    public String f20588n;

    /* renamed from: o, reason: collision with root package name */
    public int f20589o;

    /* renamed from: p, reason: collision with root package name */
    public int f20590p;

    /* renamed from: q, reason: collision with root package name */
    public int f20591q;

    /* renamed from: r, reason: collision with root package name */
    public Map<String, String> f20592r;

    /* renamed from: s, reason: collision with root package name */
    public Map<String, String> f20593s;

    public UserInfoBean() {
        this.f20585k = 0L;
        this.f20586l = false;
        this.f20587m = "unknown";
        this.f20590p = -1;
        this.f20591q = -1;
        this.f20592r = null;
        this.f20593s = null;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.f20576b);
        parcel.writeString(this.f20577c);
        parcel.writeString(this.f20578d);
        parcel.writeLong(this.f20579e);
        parcel.writeLong(this.f20580f);
        parcel.writeLong(this.f20581g);
        parcel.writeLong(this.f20582h);
        parcel.writeLong(this.f20583i);
        parcel.writeString(this.f20584j);
        parcel.writeLong(this.f20585k);
        parcel.writeByte(this.f20586l ? (byte) 1 : (byte) 0);
        parcel.writeString(this.f20587m);
        parcel.writeInt(this.f20590p);
        parcel.writeInt(this.f20591q);
        ap.b(parcel, this.f20592r);
        ap.b(parcel, this.f20593s);
        parcel.writeString(this.f20588n);
        parcel.writeInt(this.f20589o);
    }

    public UserInfoBean(Parcel parcel) {
        this.f20585k = 0L;
        this.f20586l = false;
        this.f20587m = "unknown";
        this.f20590p = -1;
        this.f20591q = -1;
        this.f20592r = null;
        this.f20593s = null;
        this.f20576b = parcel.readInt();
        this.f20577c = parcel.readString();
        this.f20578d = parcel.readString();
        this.f20579e = parcel.readLong();
        this.f20580f = parcel.readLong();
        this.f20581g = parcel.readLong();
        this.f20582h = parcel.readLong();
        this.f20583i = parcel.readLong();
        this.f20584j = parcel.readString();
        this.f20585k = parcel.readLong();
        this.f20586l = parcel.readByte() == 1;
        this.f20587m = parcel.readString();
        this.f20590p = parcel.readInt();
        this.f20591q = parcel.readInt();
        this.f20592r = ap.b(parcel);
        this.f20593s = ap.b(parcel);
        this.f20588n = parcel.readString();
        this.f20589o = parcel.readInt();
    }
}
