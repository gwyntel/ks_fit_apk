package com.google.android.gms.internal.fido;

import com.alipay.sdk.m.n.a;
import java.io.IOException;
import java.math.RoundingMode;
import javax.annotation.CheckForNull;

/* loaded from: classes3.dex */
class zzbe extends zzbf {

    /* renamed from: a, reason: collision with root package name */
    final zzbb f13059a;

    /* renamed from: b, reason: collision with root package name */
    final Character f13060b;

    zzbe(zzbb zzbbVar, Character ch) {
        this.f13059a = zzbbVar;
        if (ch != null && zzbbVar.zzb(a.f9565h)) {
            throw new IllegalArgumentException(zzan.zza("Padding character %s was already in alphabet", ch));
        }
        this.f13060b = ch;
    }

    @Override // com.google.android.gms.internal.fido.zzbf
    void a(Appendable appendable, byte[] bArr, int i2, int i3) throws IOException {
        int i4 = 0;
        zzam.zze(0, i3, bArr.length);
        while (i4 < i3) {
            c(appendable, bArr, i4, Math.min(this.f13059a.f13057d, i3 - i4));
            i4 += this.f13059a.f13057d;
        }
    }

    @Override // com.google.android.gms.internal.fido.zzbf
    final int b(int i2) {
        zzbb zzbbVar = this.f13059a;
        return zzbbVar.f13056c * zzbh.zza(i2, zzbbVar.f13057d, RoundingMode.CEILING);
    }

    final void c(Appendable appendable, byte[] bArr, int i2, int i3) throws IOException {
        zzam.zze(i2, i2 + i3, bArr.length);
        int i4 = 0;
        zzam.zzc(i3 <= this.f13059a.f13057d);
        long j2 = 0;
        for (int i5 = 0; i5 < i3; i5++) {
            j2 = (j2 | (bArr[i2 + i5] & 255)) << 8;
        }
        int i6 = ((i3 + 1) * 8) - this.f13059a.f13055b;
        while (i4 < i3 * 8) {
            zzbb zzbbVar = this.f13059a;
            appendable.append(zzbbVar.a(zzbbVar.f13054a & ((int) (j2 >>> (i6 - i4)))));
            i4 += this.f13059a.f13055b;
        }
        if (this.f13060b != null) {
            while (i4 < this.f13059a.f13057d * 8) {
                this.f13060b.charValue();
                appendable.append(a.f9565h);
                i4 += this.f13059a.f13055b;
            }
        }
    }

    public final boolean equals(@CheckForNull Object obj) {
        if (obj instanceof zzbe) {
            zzbe zzbeVar = (zzbe) obj;
            if (this.f13059a.equals(zzbeVar.f13059a)) {
                Character ch = this.f13060b;
                Character ch2 = zzbeVar.f13060b;
                if (ch == ch2) {
                    return true;
                }
                if (ch != null && ch.equals(ch2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = this.f13059a.hashCode();
        Character ch = this.f13060b;
        return iHashCode ^ (ch == null ? 0 : ch.hashCode());
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BaseEncoding.");
        sb.append(this.f13059a);
        if (8 % this.f13059a.f13055b != 0) {
            if (this.f13060b == null) {
                sb.append(".omitPadding()");
            } else {
                sb.append(".withPadChar('");
                sb.append(this.f13060b);
                sb.append("')");
            }
        }
        return sb.toString();
    }

    zzbe(String str, String str2, Character ch) {
        this(new zzbb(str, str2.toCharArray()), ch);
    }
}
