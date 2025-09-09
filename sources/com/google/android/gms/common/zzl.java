package com.google.android.gms.common;

import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
abstract class zzl extends zzj {
    private static final WeakReference zza = new WeakReference(null);
    private WeakReference zzb;

    zzl(byte[] bArr) {
        super(bArr);
        this.zzb = zza;
    }

    @Override // com.google.android.gms.common.zzj
    final byte[] g() {
        byte[] bArrH;
        synchronized (this) {
            try {
                bArrH = (byte[]) this.zzb.get();
                if (bArrH == null) {
                    bArrH = h();
                    this.zzb = new WeakReference(bArrH);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return bArrH;
    }

    protected abstract byte[] h();
}
