package com.tencent.bugly.crashreport.crash;

import android.os.Parcel;
import android.os.Parcelable;
import com.tencent.bugly.crashreport.common.info.PlugInBean;
import com.tencent.bugly.proguard.ap;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes4.dex */
public class CrashDetailBean implements Parcelable, Comparable<CrashDetailBean> {
    public static final Parcelable.Creator<CrashDetailBean> CREATOR = new Parcelable.Creator<CrashDetailBean>() { // from class: com.tencent.bugly.crashreport.crash.CrashDetailBean.1
        @Override // android.os.Parcelable.Creator
        public final /* synthetic */ CrashDetailBean createFromParcel(Parcel parcel) {
            return new CrashDetailBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final /* bridge */ /* synthetic */ CrashDetailBean[] newArray(int i2) {
            return new CrashDetailBean[i2];
        }
    };
    public String A;
    public String B;
    public long C;
    public long D;
    public long E;
    public long F;
    public long G;
    public long H;
    public long I;
    public long J;
    public long K;
    public String L;
    public String M;
    public String N;
    public String O;
    public String P;
    public long Q;
    public boolean R;
    public Map<String, String> S;
    public Map<String, String> T;
    public int U;
    public int V;
    public Map<String, String> W;
    public Map<String, String> X;
    public byte[] Y;
    public String Z;

    /* renamed from: a, reason: collision with root package name */
    public long f20620a;
    public String aa;

    /* renamed from: b, reason: collision with root package name */
    public int f20621b;

    /* renamed from: c, reason: collision with root package name */
    public String f20622c;

    /* renamed from: d, reason: collision with root package name */
    public boolean f20623d;

    /* renamed from: e, reason: collision with root package name */
    public String f20624e;

    /* renamed from: f, reason: collision with root package name */
    public String f20625f;

    /* renamed from: g, reason: collision with root package name */
    public String f20626g;

    /* renamed from: h, reason: collision with root package name */
    public Map<String, PlugInBean> f20627h;

    /* renamed from: i, reason: collision with root package name */
    public Map<String, PlugInBean> f20628i;

    /* renamed from: j, reason: collision with root package name */
    public boolean f20629j;

    /* renamed from: k, reason: collision with root package name */
    public boolean f20630k;

    /* renamed from: l, reason: collision with root package name */
    public int f20631l;

    /* renamed from: m, reason: collision with root package name */
    public String f20632m;

    /* renamed from: n, reason: collision with root package name */
    public String f20633n;

    /* renamed from: o, reason: collision with root package name */
    public String f20634o;

    /* renamed from: p, reason: collision with root package name */
    public String f20635p;

    /* renamed from: q, reason: collision with root package name */
    public String f20636q;

    /* renamed from: r, reason: collision with root package name */
    public long f20637r;

    /* renamed from: s, reason: collision with root package name */
    public String f20638s;

    /* renamed from: t, reason: collision with root package name */
    public int f20639t;

    /* renamed from: u, reason: collision with root package name */
    public String f20640u;

    /* renamed from: v, reason: collision with root package name */
    public String f20641v;

    /* renamed from: w, reason: collision with root package name */
    public String f20642w;

    /* renamed from: x, reason: collision with root package name */
    public String f20643x;

    /* renamed from: y, reason: collision with root package name */
    public byte[] f20644y;

    /* renamed from: z, reason: collision with root package name */
    public Map<String, String> f20645z;

    public CrashDetailBean() {
        this.f20620a = -1L;
        this.f20621b = 0;
        this.f20622c = UUID.randomUUID().toString();
        this.f20623d = false;
        this.f20624e = "";
        this.f20625f = "";
        this.f20626g = "";
        this.f20627h = null;
        this.f20628i = null;
        this.f20629j = false;
        this.f20630k = false;
        this.f20631l = 0;
        this.f20632m = "";
        this.f20633n = "";
        this.f20634o = "";
        this.f20635p = "";
        this.f20636q = "";
        this.f20637r = -1L;
        this.f20638s = null;
        this.f20639t = 0;
        this.f20640u = "";
        this.f20641v = "";
        this.f20642w = null;
        this.f20643x = null;
        this.f20644y = null;
        this.f20645z = null;
        this.A = "";
        this.B = "";
        this.C = -1L;
        this.D = -1L;
        this.E = -1L;
        this.F = -1L;
        this.G = -1L;
        this.H = -1L;
        this.I = -1L;
        this.J = -1L;
        this.K = -1L;
        this.L = "";
        this.M = "";
        this.N = "";
        this.O = "";
        this.P = "";
        this.Q = -1L;
        this.R = false;
        this.S = null;
        this.T = null;
        this.U = -1;
        this.V = -1;
        this.W = null;
        this.X = null;
        this.Y = null;
        this.Z = null;
        this.aa = null;
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(CrashDetailBean crashDetailBean) {
        CrashDetailBean crashDetailBean2 = crashDetailBean;
        if (crashDetailBean2 == null) {
            return 1;
        }
        long j2 = this.f20637r - crashDetailBean2.f20637r;
        if (j2 <= 0) {
            return j2 < 0 ? -1 : 0;
        }
        return 1;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.f20621b);
        parcel.writeString(this.f20622c);
        parcel.writeByte(this.f20623d ? (byte) 1 : (byte) 0);
        parcel.writeString(this.f20624e);
        parcel.writeString(this.f20625f);
        parcel.writeString(this.f20626g);
        parcel.writeByte(this.f20629j ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.f20630k ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.f20631l);
        parcel.writeString(this.f20632m);
        parcel.writeString(this.f20633n);
        parcel.writeString(this.f20634o);
        parcel.writeString(this.f20635p);
        parcel.writeString(this.f20636q);
        parcel.writeLong(this.f20637r);
        parcel.writeString(this.f20638s);
        parcel.writeInt(this.f20639t);
        parcel.writeString(this.f20640u);
        parcel.writeString(this.f20641v);
        parcel.writeString(this.f20642w);
        ap.b(parcel, this.f20645z);
        parcel.writeString(this.A);
        parcel.writeString(this.B);
        parcel.writeLong(this.C);
        parcel.writeLong(this.D);
        parcel.writeLong(this.E);
        parcel.writeLong(this.F);
        parcel.writeLong(this.G);
        parcel.writeLong(this.H);
        parcel.writeString(this.L);
        parcel.writeString(this.M);
        parcel.writeString(this.N);
        parcel.writeString(this.O);
        parcel.writeString(this.P);
        parcel.writeLong(this.Q);
        parcel.writeByte(this.R ? (byte) 1 : (byte) 0);
        ap.b(parcel, this.S);
        ap.a(parcel, this.f20627h);
        ap.a(parcel, this.f20628i);
        parcel.writeInt(this.U);
        parcel.writeInt(this.V);
        ap.b(parcel, this.W);
        ap.b(parcel, this.X);
        parcel.writeByteArray(this.Y);
        parcel.writeByteArray(this.f20644y);
        parcel.writeString(this.Z);
        parcel.writeString(this.aa);
        parcel.writeString(this.f20643x);
        parcel.writeLong(this.I);
        parcel.writeLong(this.J);
        parcel.writeLong(this.K);
    }

    public CrashDetailBean(Parcel parcel) {
        this.f20620a = -1L;
        this.f20621b = 0;
        this.f20622c = UUID.randomUUID().toString();
        this.f20623d = false;
        this.f20624e = "";
        this.f20625f = "";
        this.f20626g = "";
        this.f20627h = null;
        this.f20628i = null;
        this.f20629j = false;
        this.f20630k = false;
        this.f20631l = 0;
        this.f20632m = "";
        this.f20633n = "";
        this.f20634o = "";
        this.f20635p = "";
        this.f20636q = "";
        this.f20637r = -1L;
        this.f20638s = null;
        this.f20639t = 0;
        this.f20640u = "";
        this.f20641v = "";
        this.f20642w = null;
        this.f20643x = null;
        this.f20644y = null;
        this.f20645z = null;
        this.A = "";
        this.B = "";
        this.C = -1L;
        this.D = -1L;
        this.E = -1L;
        this.F = -1L;
        this.G = -1L;
        this.H = -1L;
        this.I = -1L;
        this.J = -1L;
        this.K = -1L;
        this.L = "";
        this.M = "";
        this.N = "";
        this.O = "";
        this.P = "";
        this.Q = -1L;
        this.R = false;
        this.S = null;
        this.T = null;
        this.U = -1;
        this.V = -1;
        this.W = null;
        this.X = null;
        this.Y = null;
        this.Z = null;
        this.aa = null;
        this.f20621b = parcel.readInt();
        this.f20622c = parcel.readString();
        this.f20623d = parcel.readByte() == 1;
        this.f20624e = parcel.readString();
        this.f20625f = parcel.readString();
        this.f20626g = parcel.readString();
        this.f20629j = parcel.readByte() == 1;
        this.f20630k = parcel.readByte() == 1;
        this.f20631l = parcel.readInt();
        this.f20632m = parcel.readString();
        this.f20633n = parcel.readString();
        this.f20634o = parcel.readString();
        this.f20635p = parcel.readString();
        this.f20636q = parcel.readString();
        this.f20637r = parcel.readLong();
        this.f20638s = parcel.readString();
        this.f20639t = parcel.readInt();
        this.f20640u = parcel.readString();
        this.f20641v = parcel.readString();
        this.f20642w = parcel.readString();
        this.f20645z = ap.b(parcel);
        this.A = parcel.readString();
        this.B = parcel.readString();
        this.C = parcel.readLong();
        this.D = parcel.readLong();
        this.E = parcel.readLong();
        this.F = parcel.readLong();
        this.G = parcel.readLong();
        this.H = parcel.readLong();
        this.L = parcel.readString();
        this.M = parcel.readString();
        this.N = parcel.readString();
        this.O = parcel.readString();
        this.P = parcel.readString();
        this.Q = parcel.readLong();
        this.R = parcel.readByte() == 1;
        this.S = ap.b(parcel);
        this.f20627h = ap.a(parcel);
        this.f20628i = ap.a(parcel);
        this.U = parcel.readInt();
        this.V = parcel.readInt();
        this.W = ap.b(parcel);
        this.X = ap.b(parcel);
        this.Y = parcel.createByteArray();
        this.f20644y = parcel.createByteArray();
        this.Z = parcel.readString();
        this.aa = parcel.readString();
        this.f20643x = parcel.readString();
        this.I = parcel.readLong();
        this.J = parcel.readLong();
        this.K = parcel.readLong();
    }
}
