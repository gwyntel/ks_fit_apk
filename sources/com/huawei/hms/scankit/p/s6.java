package com.huawei.hms.scankit.p;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class s6 implements Parcelable {
    public static final Parcelable.Creator<s6> CREATOR = new a();

    /* renamed from: a, reason: collision with root package name */
    private final String f17777a;

    /* renamed from: b, reason: collision with root package name */
    private final byte[] f17778b;

    /* renamed from: c, reason: collision with root package name */
    private final int f17779c;

    /* renamed from: d, reason: collision with root package name */
    private u6[] f17780d;

    /* renamed from: e, reason: collision with root package name */
    private BarcodeFormat f17781e;

    /* renamed from: f, reason: collision with root package name */
    private final long f17782f;

    /* renamed from: g, reason: collision with root package name */
    private boolean f17783g;

    /* renamed from: h, reason: collision with root package name */
    private final boolean f17784h;

    /* renamed from: i, reason: collision with root package name */
    private final float f17785i;

    /* renamed from: j, reason: collision with root package name */
    private int f17786j;

    /* renamed from: k, reason: collision with root package name */
    private List<Rect> f17787k;

    /* renamed from: l, reason: collision with root package name */
    private boolean f17788l;

    /* renamed from: m, reason: collision with root package name */
    private int f17789m;

    /* renamed from: n, reason: collision with root package name */
    private List<Rect> f17790n;

    /* renamed from: o, reason: collision with root package name */
    private long f17791o;

    /* renamed from: p, reason: collision with root package name */
    private long f17792p;

    /* renamed from: q, reason: collision with root package name */
    private boolean f17793q;

    /* renamed from: r, reason: collision with root package name */
    private boolean f17794r;

    class a implements Parcelable.Creator<s6> {
        a() {
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public s6 createFromParcel(Parcel parcel) {
            return new s6(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public s6[] newArray(int i2) {
            return new s6[i2];
        }
    }

    public s6(float f2) {
        this.f17783g = false;
        this.f17793q = false;
        this.f17794r = false;
        this.f17785i = f2;
        this.f17777a = null;
        this.f17778b = new byte[0];
        this.f17779c = 0;
        this.f17780d = new u6[0];
        this.f17781e = BarcodeFormat.NONE;
        this.f17782f = 0L;
        this.f17784h = false;
        this.f17786j = 0;
        this.f17788l = false;
        this.f17789m = 0;
        this.f17787k = new ArrayList();
        this.f17790n = new ArrayList();
    }

    public void a(float f2) {
        if (f2 < 20.0f) {
            this.f17786j = 0;
            return;
        }
        if (f2 < 50.0f) {
            this.f17786j = 2;
            return;
        }
        if (f2 < 90.0f) {
            this.f17786j = 1;
            return;
        }
        if (f2 < 140.0f) {
            this.f17786j = 0;
        } else if (f2 < 190.0f) {
            this.f17786j = -1;
        } else if (f2 <= 255.0f) {
            this.f17786j = -2;
        }
    }

    public void b(float f2) {
        if (f2 < 50.0f) {
            this.f17789m = 2;
            return;
        }
        if (f2 < 90.0f) {
            this.f17789m = 1;
            return;
        }
        if (f2 < 140.0f) {
            this.f17789m = 0;
        } else if (f2 < 190.0f) {
            this.f17789m = -1;
        } else if (f2 <= 255.0f) {
            this.f17789m = -2;
        }
    }

    public void c(boolean z2) {
        this.f17783g = z2;
    }

    public List<Rect> d() {
        return this.f17787k;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public long e() {
        return this.f17791o;
    }

    public int f() {
        return this.f17786j;
    }

    public List<Rect> g() {
        return this.f17790n;
    }

    public int h() {
        return this.f17789m;
    }

    public byte[] i() {
        return this.f17778b;
    }

    public u6[] j() {
        return this.f17780d;
    }

    public String k() {
        return this.f17777a;
    }

    public float l() {
        return this.f17785i;
    }

    public boolean m() {
        return this.f17793q;
    }

    public boolean n() {
        return this.f17788l;
    }

    public boolean o() {
        return this.f17794r;
    }

    public boolean p() {
        return this.f17783g;
    }

    public String toString() {
        return this.f17777a;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.f17777a);
        parcel.writeByteArray(this.f17778b);
        parcel.writeInt(this.f17779c);
        parcel.writeTypedArray(this.f17780d, i2);
        parcel.writeParcelable(this.f17781e, i2);
        parcel.writeLong(this.f17782f);
        parcel.writeInt(this.f17783g ? 1 : 0);
        parcel.writeInt(this.f17784h ? 1 : 0);
        parcel.writeFloat(this.f17785i);
        parcel.writeInt(this.f17786j);
        parcel.writeList(this.f17787k);
        parcel.writeLong(this.f17791o);
        parcel.writeLong(this.f17792p);
        parcel.writeInt(this.f17793q ? 1 : 0);
    }

    public BarcodeFormat c() {
        return this.f17781e;
    }

    public void b(i2 i2Var) {
        int iD = (int) i2Var.d();
        int iE = (int) i2Var.e();
        this.f17790n.add(new Rect(iD, iE, ((int) i2Var.f()) + iD, ((int) i2Var.c()) + iE));
    }

    public void a(i2 i2Var) {
        int iD = (int) i2Var.d();
        int iE = (int) i2Var.e();
        this.f17787k.add(new Rect(iD, iE, ((int) i2Var.f()) + iD, ((int) i2Var.c()) + iE));
    }

    public void b(boolean z2) {
        this.f17788l = z2;
    }

    public void a(int i2) {
        this.f17789m = i2;
    }

    public void b(long j2) {
        this.f17791o = j2;
    }

    public void a(u6[] u6VarArr) {
        u6[] u6VarArr2 = this.f17780d;
        if (u6VarArr2 == null) {
            this.f17780d = u6VarArr;
            return;
        }
        if (u6VarArr == null || u6VarArr.length <= 0) {
            return;
        }
        u6[] u6VarArr3 = new u6[u6VarArr2.length + u6VarArr.length];
        System.arraycopy(u6VarArr2, 0, u6VarArr3, 0, u6VarArr2.length);
        System.arraycopy(u6VarArr, 0, u6VarArr3, u6VarArr2.length, u6VarArr.length);
        this.f17780d = u6VarArr3;
    }

    public long b() {
        return this.f17792p;
    }

    public void b(u6[] u6VarArr) {
        this.f17780d = u6VarArr;
    }

    public s6(float f2, boolean z2) {
        this.f17783g = false;
        this.f17793q = false;
        this.f17794r = false;
        this.f17785i = f2;
        this.f17777a = null;
        this.f17778b = new byte[0];
        this.f17779c = 0;
        this.f17780d = new u6[0];
        this.f17781e = BarcodeFormat.NONE;
        this.f17782f = 0L;
        this.f17784h = false;
        this.f17786j = 0;
        this.f17788l = false;
        this.f17789m = 0;
        this.f17794r = z2;
        this.f17787k = new ArrayList();
        this.f17790n = new ArrayList();
    }

    public void a(long j2) {
        this.f17792p = j2;
    }

    public void a(boolean z2) {
        this.f17793q = z2;
    }

    public void a() {
        this.f17780d = new u6[0];
    }

    public s6(String str, byte[] bArr, u6[] u6VarArr, BarcodeFormat barcodeFormat) {
        this(str, bArr, u6VarArr, barcodeFormat, System.currentTimeMillis());
    }

    public s6(String str, byte[] bArr, u6[] u6VarArr, BarcodeFormat barcodeFormat, long j2) {
        this(str, bArr, bArr == null ? 0 : bArr.length * 8, u6VarArr, barcodeFormat, j2);
    }

    public s6(String str, byte[] bArr, int i2, u6[] u6VarArr, BarcodeFormat barcodeFormat, long j2) {
        this.f17783g = false;
        this.f17793q = false;
        this.f17794r = false;
        this.f17777a = str;
        this.f17778b = bArr;
        this.f17779c = i2;
        this.f17780d = u6VarArr;
        this.f17781e = barcodeFormat;
        this.f17782f = j2;
        this.f17785i = 1.0f;
        this.f17784h = false;
    }

    protected s6(Parcel parcel) {
        this.f17783g = false;
        this.f17793q = false;
        this.f17794r = false;
        this.f17777a = parcel.readString();
        this.f17778b = parcel.createByteArray();
        this.f17779c = parcel.readInt();
        this.f17780d = (u6[]) parcel.createTypedArray(u6.CREATOR);
        this.f17781e = (BarcodeFormat) parcel.readParcelable(s6.class.getClassLoader());
        this.f17782f = parcel.readLong();
        this.f17783g = parcel.readInt() == 1;
        this.f17784h = parcel.readInt() == 1;
        this.f17785i = parcel.readFloat();
        this.f17786j = parcel.readInt();
        if (this.f17787k == null) {
            this.f17787k = new ArrayList();
        }
        parcel.readList(this.f17787k, s6.class.getClassLoader());
        this.f17791o = parcel.readLong();
        this.f17792p = parcel.readLong();
        this.f17793q = parcel.readInt() == 1;
    }
}
