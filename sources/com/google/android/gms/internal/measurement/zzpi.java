package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
abstract class zzpi {
    zzpi() {
    }

    abstract int a(int i2, byte[] bArr, int i3, int i4);

    abstract int b(CharSequence charSequence, byte[] bArr, int i2, int i3);

    abstract String c(byte[] bArr, int i2, int i3);

    final boolean d(byte[] bArr, int i2, int i3) {
        return a(0, bArr, i2, i3) == 0;
    }
}
