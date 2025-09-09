package com.google.android.gms.internal.auth;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* loaded from: classes3.dex */
public final class zzfa {

    /* renamed from: a, reason: collision with root package name */
    static final Charset f13016a = Charset.forName("US-ASCII");

    /* renamed from: b, reason: collision with root package name */
    static final Charset f13017b = Charset.forName("UTF-8");

    /* renamed from: c, reason: collision with root package name */
    static final Charset f13018c = Charset.forName("ISO-8859-1");
    public static final byte[] zzd;
    public static final ByteBuffer zze;
    public static final zzej zzf;

    static {
        byte[] bArr = new byte[0];
        zzd = bArr;
        zze = ByteBuffer.wrap(bArr);
        int i2 = zzej.zza;
        zzeh zzehVar = new zzeh(bArr, 0, 0, false, null);
        try {
            zzehVar.zza(0);
            zzf = zzehVar;
        } catch (zzfb e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    static int a(int i2, byte[] bArr, int i3, int i4) {
        for (int i5 = 0; i5 < i4; i5++) {
            i2 = (i2 * 31) + bArr[i5];
        }
        return i2;
    }

    static Object b(Object obj, String str) {
        if (obj != null) {
            return obj;
        }
        throw new NullPointerException("messageType");
    }

    public static int zza(boolean z2) {
        return z2 ? 1231 : 1237;
    }

    public static String zzd(byte[] bArr) {
        return new String(bArr, f13017b);
    }
}
