package com.tencent.bugly.crashreport.common.strategy;

import android.os.Parcel;
import android.os.Parcelable;
import com.tencent.bugly.proguard.ap;
import java.util.Map;

/* loaded from: classes4.dex */
public class StrategyBean implements Parcelable {
    public static final Parcelable.Creator<StrategyBean> CREATOR = new Parcelable.Creator<StrategyBean>() { // from class: com.tencent.bugly.crashreport.common.strategy.StrategyBean.1
        @Override // android.os.Parcelable.Creator
        public final /* synthetic */ StrategyBean createFromParcel(Parcel parcel) {
            return new StrategyBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final /* bridge */ /* synthetic */ StrategyBean[] newArray(int i2) {
            return new StrategyBean[i2];
        }
    };

    /* renamed from: a, reason: collision with root package name */
    public static String f20597a = "https://android.bugly.qq.com/rqd/async";

    /* renamed from: b, reason: collision with root package name */
    public static String f20598b = "https://android.bugly.qq.com/rqd/async";

    /* renamed from: c, reason: collision with root package name */
    public static String f20599c;

    /* renamed from: d, reason: collision with root package name */
    public long f20600d;

    /* renamed from: e, reason: collision with root package name */
    public long f20601e;

    /* renamed from: f, reason: collision with root package name */
    public boolean f20602f;

    /* renamed from: g, reason: collision with root package name */
    public boolean f20603g;

    /* renamed from: h, reason: collision with root package name */
    public boolean f20604h;

    /* renamed from: i, reason: collision with root package name */
    public boolean f20605i;

    /* renamed from: j, reason: collision with root package name */
    public boolean f20606j;

    /* renamed from: k, reason: collision with root package name */
    public boolean f20607k;

    /* renamed from: l, reason: collision with root package name */
    public boolean f20608l;

    /* renamed from: m, reason: collision with root package name */
    public boolean f20609m;

    /* renamed from: n, reason: collision with root package name */
    public boolean f20610n;

    /* renamed from: o, reason: collision with root package name */
    public long f20611o;

    /* renamed from: p, reason: collision with root package name */
    public long f20612p;

    /* renamed from: q, reason: collision with root package name */
    public String f20613q;

    /* renamed from: r, reason: collision with root package name */
    public String f20614r;

    /* renamed from: s, reason: collision with root package name */
    public String f20615s;

    /* renamed from: t, reason: collision with root package name */
    public Map<String, String> f20616t;

    /* renamed from: u, reason: collision with root package name */
    public int f20617u;

    /* renamed from: v, reason: collision with root package name */
    public long f20618v;

    /* renamed from: w, reason: collision with root package name */
    public long f20619w;

    public StrategyBean() {
        this.f20600d = -1L;
        this.f20601e = -1L;
        this.f20602f = true;
        this.f20603g = true;
        this.f20604h = true;
        this.f20605i = true;
        this.f20606j = false;
        this.f20607k = true;
        this.f20608l = true;
        this.f20609m = true;
        this.f20610n = true;
        this.f20612p = 30000L;
        this.f20613q = f20597a;
        this.f20614r = f20598b;
        this.f20617u = 10;
        this.f20618v = 300000L;
        this.f20619w = -1L;
        this.f20601e = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append("S(@L@L@)");
        f20599c = sb.toString();
        sb.setLength(0);
        sb.append("*^@K#K@!");
        this.f20615s = sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeLong(this.f20601e);
        parcel.writeByte(this.f20602f ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.f20603g ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.f20604h ? (byte) 1 : (byte) 0);
        parcel.writeString(this.f20613q);
        parcel.writeString(this.f20614r);
        parcel.writeString(this.f20615s);
        ap.b(parcel, this.f20616t);
        parcel.writeByte(this.f20605i ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.f20606j ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.f20609m ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.f20610n ? (byte) 1 : (byte) 0);
        parcel.writeLong(this.f20612p);
        parcel.writeByte(this.f20607k ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.f20608l ? (byte) 1 : (byte) 0);
        parcel.writeLong(this.f20611o);
        parcel.writeInt(this.f20617u);
        parcel.writeLong(this.f20618v);
        parcel.writeLong(this.f20619w);
    }

    public StrategyBean(Parcel parcel) {
        this.f20600d = -1L;
        this.f20601e = -1L;
        boolean z2 = true;
        this.f20602f = true;
        this.f20603g = true;
        this.f20604h = true;
        this.f20605i = true;
        this.f20606j = false;
        this.f20607k = true;
        this.f20608l = true;
        this.f20609m = true;
        this.f20610n = true;
        this.f20612p = 30000L;
        this.f20613q = f20597a;
        this.f20614r = f20598b;
        this.f20617u = 10;
        this.f20618v = 300000L;
        this.f20619w = -1L;
        try {
            f20599c = "S(@L@L@)";
            this.f20601e = parcel.readLong();
            this.f20602f = parcel.readByte() == 1;
            this.f20603g = parcel.readByte() == 1;
            this.f20604h = parcel.readByte() == 1;
            this.f20613q = parcel.readString();
            this.f20614r = parcel.readString();
            this.f20615s = parcel.readString();
            this.f20616t = ap.b(parcel);
            this.f20605i = parcel.readByte() == 1;
            this.f20606j = parcel.readByte() == 1;
            this.f20609m = parcel.readByte() == 1;
            this.f20610n = parcel.readByte() == 1;
            this.f20612p = parcel.readLong();
            this.f20607k = parcel.readByte() == 1;
            if (parcel.readByte() != 1) {
                z2 = false;
            }
            this.f20608l = z2;
            this.f20611o = parcel.readLong();
            this.f20617u = parcel.readInt();
            this.f20618v = parcel.readLong();
            this.f20619w = parcel.readLong();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
