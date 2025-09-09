package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* loaded from: classes3.dex */
public abstract class zzkx {
    private static volatile int zzd = 100;

    /* renamed from: a, reason: collision with root package name */
    int f13220a;

    /* renamed from: b, reason: collision with root package name */
    int f13221b;

    /* renamed from: c, reason: collision with root package name */
    zzlb f13222c;
    private int zze;
    private boolean zzf;

    static zzkx a(byte[] bArr, int i2, int i3, boolean z2) {
        zzla zzlaVar = new zzla(bArr, i3);
        try {
            zzlaVar.zzb(i3);
            return zzlaVar;
        } catch (zzme e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    public static int zza(int i2) {
        return (-(i2 & 1)) ^ (i2 >>> 1);
    }

    abstract long b();

    public abstract double zza() throws IOException;

    public abstract float zzb() throws IOException;

    public abstract int zzb(int i2) throws zzme;

    public abstract int zzc();

    public abstract void zzc(int i2) throws zzme;

    public abstract int zzd() throws IOException;

    public abstract void zzd(int i2);

    public abstract int zze() throws IOException;

    public abstract boolean zze(int i2) throws IOException;

    public abstract int zzf() throws IOException;

    public abstract int zzg() throws IOException;

    public abstract int zzh() throws IOException;

    public abstract int zzi() throws IOException;

    public abstract int zzj() throws IOException;

    public abstract long zzk() throws IOException;

    public abstract long zzl() throws IOException;

    public abstract long zzn() throws IOException;

    public abstract long zzo() throws IOException;

    public abstract long zzp() throws IOException;

    public abstract zzkm zzq() throws IOException;

    public abstract String zzr() throws IOException;

    public abstract String zzs() throws IOException;

    public abstract boolean zzt() throws IOException;

    public abstract boolean zzu() throws IOException;

    private zzkx() {
        this.f13221b = zzd;
        this.zze = Integer.MAX_VALUE;
        this.zzf = false;
    }

    public static long zza(long j2) {
        return (-(j2 & 1)) ^ (j2 >>> 1);
    }
}
