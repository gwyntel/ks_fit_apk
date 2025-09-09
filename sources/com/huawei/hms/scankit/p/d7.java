package com.huawei.hms.scankit.p;

import com.aliyun.alink.linksdk.tmp.utils.ErrorCode;
import com.luck.picture.lib.config.Crop;
import com.yc.utesdk.ble.close.KeyType;

/* loaded from: classes4.dex */
public class d7 {

    /* renamed from: i, reason: collision with root package name */
    static final d7[] f17133i;

    /* renamed from: j, reason: collision with root package name */
    private static d7[] f17134j;

    /* renamed from: a, reason: collision with root package name */
    private final boolean f17135a;

    /* renamed from: b, reason: collision with root package name */
    private final int f17136b;

    /* renamed from: c, reason: collision with root package name */
    private final int f17137c;

    /* renamed from: d, reason: collision with root package name */
    public final int f17138d;

    /* renamed from: e, reason: collision with root package name */
    public final int f17139e;

    /* renamed from: f, reason: collision with root package name */
    private final int f17140f;

    /* renamed from: g, reason: collision with root package name */
    private final int f17141g;

    /* renamed from: h, reason: collision with root package name */
    private final int f17142h;

    static {
        d7[] d7VarArr = {new d7(false, 3, 5, 8, 8, 1), new d7(false, 5, 7, 10, 10, 1), new d7(true, 5, 7, 16, 6, 1), new d7(false, 8, 10, 12, 12, 1), new d7(true, 10, 11, 14, 6, 2), new d7(false, 12, 12, 14, 14, 1), new d7(true, 16, 14, 24, 10, 1), new d7(false, 18, 14, 16, 16, 1), new d7(false, 22, 18, 18, 18, 1), new d7(true, 22, 18, 16, 10, 2), new d7(false, 30, 20, 20, 20, 1), new d7(true, 32, 24, 16, 14, 2), new d7(false, 36, 24, 22, 22, 1), new d7(false, 44, 28, 24, 24, 1), new d7(true, 49, 28, 22, 14, 2), new d7(false, 62, 36, 14, 14, 4), new d7(false, 86, 42, 16, 16, 4), new d7(false, 114, 48, 18, 18, 4), new d7(false, 144, 56, 20, 20, 4), new d7(false, 174, 68, 22, 22, 4), new d7(false, 204, 84, 24, 24, 4, 102, 42), new d7(false, KeyType.SET_AI_WATCH_VOICE_CONTENT, 112, 14, 14, 16, 140, 56), new d7(false, 368, 144, 16, 16, 16, 92, 36), new d7(false, 456, 192, 18, 18, 16, 114, 48), new d7(false, 576, 224, 20, 20, 16, 144, 56), new d7(false, Crop.REQUEST_EDIT_CROP, KeyType.SEND_MENSTRUAL_START_DAY, 22, 22, 16, 174, 68), new d7(false, 816, 336, 24, 24, 16, 136, 56), new d7(false, 1050, ErrorCode.ERROR_CODE_TIMEOUT, 18, 18, 36, 175, 68), new d7(false, 1304, 496, 20, 20, 36, 163, 62), new i1()};
        f17133i = d7VarArr;
        f17134j = d7VarArr;
    }

    public d7(boolean z2, int i2, int i3, int i4, int i5, int i6) {
        this(z2, i2, i3, i4, i5, i6, i2, i3);
    }

    public static d7 a(int i2, e7 e7Var, l2 l2Var, l2 l2Var2, boolean z2) {
        for (d7 d7Var : f17134j) {
            if (!(e7Var == e7.FORCE_SQUARE && d7Var.f17135a) && ((e7Var != e7.FORCE_RECTANGLE || d7Var.f17135a) && ((l2Var == null || (d7Var.h() >= l2Var.b() && d7Var.g() >= l2Var.a())) && ((l2Var2 == null || (d7Var.h() <= l2Var2.b() && d7Var.g() <= l2Var2.a())) && i2 <= d7Var.f17136b)))) {
                return d7Var;
            }
        }
        if (!z2) {
            return null;
        }
        throw new IllegalArgumentException("Can't find a symbol arrangement that matches the message. Data codewords: " + i2);
    }

    private int c() {
        int i2 = this.f17140f;
        int i3 = 1;
        if (i2 != 1) {
            i3 = 2;
            if (i2 != 2 && i2 != 4) {
                if (i2 == 16) {
                    return 4;
                }
                if (i2 == 36) {
                    return 6;
                }
                throw new IllegalStateException("Cannot handle this number of data regions");
            }
        }
        return i3;
    }

    private int i() {
        int i2 = this.f17140f;
        if (i2 == 1 || i2 == 2) {
            return 1;
        }
        if (i2 == 4) {
            return 2;
        }
        if (i2 == 16) {
            return 4;
        }
        if (i2 == 36) {
            return 6;
        }
        throw new IllegalStateException("Cannot handle this number of data regions");
    }

    public final int b() {
        return this.f17137c;
    }

    public int d() {
        return this.f17136b / this.f17141g;
    }

    public final int e() {
        return i() * this.f17139e;
    }

    public final int f() {
        return c() * this.f17138d;
    }

    public final int g() {
        return e() + (i() * 2);
    }

    public final int h() {
        return f() + (c() * 2);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.f17135a ? "Rectangular Symbol:" : "Square Symbol:");
        sb.append(" data region ");
        sb.append(this.f17138d);
        sb.append('x');
        sb.append(this.f17139e);
        sb.append(", symbol size ");
        sb.append(h());
        sb.append('x');
        sb.append(g());
        sb.append(", symbol data size ");
        sb.append(f());
        sb.append('x');
        sb.append(e());
        sb.append(", codewords ");
        sb.append(this.f17136b);
        sb.append('+');
        sb.append(this.f17137c);
        return sb.toString();
    }

    d7(boolean z2, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this.f17135a = z2;
        this.f17136b = i2;
        this.f17137c = i3;
        this.f17138d = i4;
        this.f17139e = i5;
        this.f17140f = i6;
        this.f17141g = i7;
        this.f17142h = i8;
    }

    public final int b(int i2) {
        return this.f17142h;
    }

    public final int a() {
        return this.f17136b;
    }

    public int a(int i2) {
        return this.f17141g;
    }
}
