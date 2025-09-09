package com.google.android.gms.internal.fido;

import com.alipay.sdk.m.n.a;
import java.io.IOException;

/* loaded from: classes3.dex */
public abstract class zzbf {
    private static final zzbf zza;
    private static final zzbf zzb;
    private static final zzbf zzc;
    private static final zzbf zzd;
    private static final zzbf zze;

    static {
        Character chValueOf = Character.valueOf(a.f9565h);
        zza = new zzbd("base64()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", chValueOf);
        zzb = new zzbd("base64Url()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_", chValueOf);
        zzc = new zzbe("base32()", "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567", chValueOf);
        zzd = new zzbe("base32Hex()", "0123456789ABCDEFGHIJKLMNOPQRSTUV", chValueOf);
        zze = new zzbc("base16()", "0123456789ABCDEF");
    }

    zzbf() {
    }

    public static zzbf zzd() {
        return zze;
    }

    abstract void a(Appendable appendable, byte[] bArr, int i2, int i3);

    abstract int b(int i2);

    public final String zze(byte[] bArr, int i2, int i3) {
        zzam.zze(0, i3, bArr.length);
        StringBuilder sb = new StringBuilder(b(i3));
        try {
            a(sb, bArr, 0, i3);
            return sb.toString();
        } catch (IOException e2) {
            throw new AssertionError(e2);
        }
    }
}
