package com.huawei.hms.scankit.p;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class u6 implements Parcelable {
    public static final Parcelable.Creator<u6> CREATOR = new a();

    /* renamed from: a, reason: collision with root package name */
    private final float f17860a;

    /* renamed from: b, reason: collision with root package name */
    private final float f17861b;

    /* renamed from: c, reason: collision with root package name */
    private int f17862c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f17863d;

    class a implements Parcelable.Creator<u6> {
        a() {
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public u6 createFromParcel(Parcel parcel) {
            return new u6(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public u6[] newArray(int i2) {
            return new u6[i2];
        }
    }

    public u6(float f2, float f3, int i2) {
        this.f17863d = false;
        this.f17860a = f2;
        this.f17861b = f3;
        this.f17862c = i2;
    }

    public int a() {
        return this.f17862c;
    }

    public final float b() {
        return this.f17860a;
    }

    public final float c() {
        return this.f17861b;
    }

    public boolean d() {
        return this.f17863d;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof u6)) {
            return false;
        }
        u6 u6Var = (u6) obj;
        return ((double) Math.abs(this.f17860a - u6Var.f17860a)) < 1.0E-4d && ((double) Math.abs(this.f17861b - u6Var.f17861b)) < 1.0E-4d;
    }

    public final int hashCode() {
        return (Float.floatToIntBits(this.f17860a) * 31) + Float.floatToIntBits(this.f17861b);
    }

    public final String toString() {
        return "(" + this.f17860a + ',' + this.f17861b + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeFloat(this.f17860a);
        parcel.writeFloat(this.f17861b);
    }

    public static void a(u6[] u6VarArr) {
        float fA = a(u6VarArr[0], u6VarArr[1]);
        float fA2 = a(u6VarArr[1], u6VarArr[2]);
        float fA3 = a(u6VarArr[0], u6VarArr[2]);
        int[] iArrA = a(fA2, fA, fA3);
        int i2 = iArrA[0];
        int i3 = iArrA[1];
        int i4 = iArrA[2];
        u6 u6Var = u6VarArr[i2];
        u6 u6Var2 = u6VarArr[i3];
        u6 u6Var3 = u6VarArr[i4];
        float[] fArr = {fA2, fA3, fA};
        if (r3.f17723j % 2 == 0) {
            int i5 = ((fArr[i3] / fArr[i2]) > 1.1d ? 1 : ((fArr[i3] / fArr[i2]) == 1.1d ? 0 : -1));
        }
        if (a(u6Var2, u6Var, u6Var3) < 0.0f) {
            u6Var2 = u6Var3;
            u6Var3 = u6Var2;
        }
        u6VarArr[0] = u6Var2;
        u6VarArr[1] = u6Var;
        u6VarArr[2] = u6Var3;
    }

    public u6(float f2, float f3) {
        this.f17862c = 0;
        this.f17863d = false;
        this.f17860a = f2;
        this.f17861b = f3;
    }

    public u6(float f2, float f3, boolean z2) {
        this.f17862c = 0;
        this.f17860a = f2;
        this.f17861b = f3;
        this.f17863d = z2;
    }

    protected u6(Parcel parcel) {
        this.f17862c = 0;
        this.f17863d = false;
        this.f17860a = parcel.readFloat();
        this.f17861b = parcel.readFloat();
    }

    private static int[] a(float f2, float f3, float f4) {
        int i2;
        int i3;
        int i4 = 1;
        int i5 = 2;
        int i6 = 0;
        if (f2 < f3 || f2 < f4) {
            if (f4 < f2 || f4 < f3) {
                if (f2 > f2) {
                    i3 = 0;
                    i6 = 1;
                    i4 = 2;
                } else {
                    i2 = 2;
                    i5 = 1;
                    i4 = i2;
                }
            } else if (f2 > f3) {
                i3 = 0;
                i6 = 2;
            }
            i5 = i3;
        } else if (f3 > f4) {
            i2 = 0;
            i6 = 1;
            i4 = i2;
        } else {
            i5 = 1;
            i4 = 0;
            i6 = 2;
        }
        return new int[]{i4, i5, i6};
    }

    public static float a(u6 u6Var, u6 u6Var2) {
        return s4.a(u6Var.f17860a, u6Var.f17861b, u6Var2.f17860a, u6Var2.f17861b);
    }

    private static float a(u6 u6Var, u6 u6Var2, u6 u6Var3) {
        float f2 = u6Var2.f17860a;
        float f3 = u6Var2.f17861b;
        return ((u6Var3.f17860a - f2) * (u6Var.f17861b - f3)) - ((u6Var3.f17861b - f3) * (u6Var.f17860a - f2));
    }
}
