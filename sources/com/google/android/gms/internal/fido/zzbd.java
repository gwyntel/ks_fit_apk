package com.google.android.gms.internal.fido;

import java.io.IOException;

/* loaded from: classes3.dex */
final class zzbd extends zzbe {
    zzbd(String str, String str2, Character ch) {
        zzbb zzbbVar = new zzbb(str, str2.toCharArray());
        super(zzbbVar, ch);
        zzam.zzc(zzbbVar.zzf.length == 64);
    }

    @Override // com.google.android.gms.internal.fido.zzbe, com.google.android.gms.internal.fido.zzbf
    final void a(Appendable appendable, byte[] bArr, int i2, int i3) throws IOException {
        int i4 = 0;
        zzam.zze(0, i3, bArr.length);
        for (int i5 = i3; i5 >= 3; i5 -= 3) {
            int i6 = bArr[i4] & 255;
            int i7 = ((bArr[i4 + 1] & 255) << 8) | (i6 << 16) | (bArr[i4 + 2] & 255);
            appendable.append(this.f13059a.a(i7 >>> 18));
            appendable.append(this.f13059a.a((i7 >>> 12) & 63));
            appendable.append(this.f13059a.a((i7 >>> 6) & 63));
            appendable.append(this.f13059a.a(i7 & 63));
            i4 += 3;
        }
        if (i4 < i3) {
            c(appendable, bArr, i4, i3 - i4);
        }
    }
}
