package com.google.android.gms.internal.fido;

import java.io.IOException;

/* loaded from: classes3.dex */
final class zzbc extends zzbe {

    /* renamed from: c, reason: collision with root package name */
    final char[] f13058c;

    zzbc(String str, String str2) {
        zzbb zzbbVar = new zzbb("base16()", "0123456789ABCDEF".toCharArray());
        super(zzbbVar, null);
        this.f13058c = new char[512];
        zzam.zzc(zzbbVar.zzf.length == 16);
        for (int i2 = 0; i2 < 256; i2++) {
            this.f13058c[i2] = zzbbVar.a(i2 >>> 4);
            this.f13058c[i2 | 256] = zzbbVar.a(i2 & 15);
        }
    }

    @Override // com.google.android.gms.internal.fido.zzbe, com.google.android.gms.internal.fido.zzbf
    final void a(Appendable appendable, byte[] bArr, int i2, int i3) throws IOException {
        zzam.zze(0, i3, bArr.length);
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = bArr[i4] & 255;
            appendable.append(this.f13058c[i5]);
            appendable.append(this.f13058c[i5 | 256]);
        }
    }
}
