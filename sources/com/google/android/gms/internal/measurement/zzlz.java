package com.google.android.gms.internal.measurement;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* loaded from: classes3.dex */
public final class zzlz {
    public static final byte[] zzb;
    private static final ByteBuffer zze;
    private static final zzkx zzf;
    private static final Charset zzc = Charset.forName("US-ASCII");

    /* renamed from: a, reason: collision with root package name */
    static final Charset f13231a = Charset.forName("UTF-8");
    private static final Charset zzd = Charset.forName("ISO-8859-1");

    static {
        byte[] bArr = new byte[0];
        zzb = bArr;
        zze = ByteBuffer.wrap(bArr);
        zzf = zzkx.a(bArr, 0, bArr.length, false);
    }

    static int a(int i2, byte[] bArr, int i3, int i4) {
        for (int i5 = i3; i5 < i3 + i4; i5++) {
            i2 = (i2 * 31) + bArr[i5];
        }
        return i2;
    }

    static Object b(Object obj) {
        obj.getClass();
        return obj;
    }

    static Object c(Object obj, String str) {
        if (obj != null) {
            return obj;
        }
        throw new NullPointerException(str);
    }

    static boolean d(zznj zznjVar) {
        boolean z2 = zznjVar instanceof zzke;
        return false;
    }

    public static int zza(long j2) {
        return (int) (j2 ^ (j2 >>> 32));
    }

    public static String zzb(byte[] bArr) {
        return new String(bArr, f13231a);
    }

    public static boolean zzc(byte[] bArr) {
        return zzpg.d(bArr);
    }

    public static int zza(boolean z2) {
        return z2 ? 1231 : 1237;
    }

    public static int zza(byte[] bArr) {
        int length = bArr.length;
        int iA = a(length, bArr, 0, length);
        if (iA == 0) {
            return 1;
        }
        return iA;
    }
}
