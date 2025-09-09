package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: classes3.dex */
public abstract class zzld extends zzkj {
    private static final Logger zzb = Logger.getLogger(zzld.class.getName());
    private static final boolean zzc = zzpc.s();

    /* renamed from: a, reason: collision with root package name */
    zzlf f13223a;

    public static class zza extends IOException {
        zza() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }

        zza(Throwable th) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.", th);
        }

        zza(String str, Throwable th) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.: " + str, th);
        }
    }

    private static class zzb extends zzld {
        private final byte[] zzb;
        private final int zzc;
        private final int zzd;
        private int zze;

        zzb(byte[] bArr, int i2, int i3) {
            super();
            if (bArr == null) {
                throw new NullPointerException("buffer");
            }
            if (((bArr.length - i3) | i3) < 0) {
                throw new IllegalArgumentException(String.format("Array range is invalid. Buffer.length=%d, offset=%d, length=%d", Integer.valueOf(bArr.length), 0, Integer.valueOf(i3)));
            }
            this.zzb = bArr;
            this.zzc = 0;
            this.zze = 0;
            this.zzd = i3;
        }

        private final void zzc(byte[] bArr, int i2, int i3) throws IOException {
            try {
                System.arraycopy(bArr, i2, this.zzb, this.zze, i3);
                this.zze += i3;
            } catch (IndexOutOfBoundsException e2) {
                throw new zza(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.zze), Integer.valueOf(this.zzd), Integer.valueOf(i3)), e2);
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzld
        final void e(int i2, zznj zznjVar, zzob zzobVar) throws IOException {
            zzj(i2, 2);
            zzk(((zzkc) zznjVar).a(zzobVar));
            zzobVar.zza((zzob) zznjVar, (zzpw) this.f13223a);
        }

        @Override // com.google.android.gms.internal.measurement.zzld
        public final int zza() {
            return this.zzd - this.zze;
        }

        @Override // com.google.android.gms.internal.measurement.zzld
        public final void zzb(int i2, boolean z2) throws IOException {
            zzj(i2, 0);
            zza(z2 ? (byte) 1 : (byte) 0);
        }

        @Override // com.google.android.gms.internal.measurement.zzld
        public final void zzd(int i2, zzkm zzkmVar) throws IOException {
            zzj(1, 3);
            zzk(2, i2);
            zzc(3, zzkmVar);
            zzj(1, 4);
        }

        @Override // com.google.android.gms.internal.measurement.zzld
        public final void zzf(int i2, long j2) throws IOException {
            zzj(i2, 1);
            zzf(j2);
        }

        @Override // com.google.android.gms.internal.measurement.zzld
        public final void zzg(int i2, int i3) throws IOException {
            zzj(i2, 5);
            zzh(i3);
        }

        @Override // com.google.android.gms.internal.measurement.zzld
        public final void zzh(int i2) throws IOException {
            try {
                byte[] bArr = this.zzb;
                int i3 = this.zze;
                int i4 = i3 + 1;
                this.zze = i4;
                bArr[i3] = (byte) i2;
                int i5 = i3 + 2;
                this.zze = i5;
                bArr[i4] = (byte) (i2 >> 8);
                int i6 = i3 + 3;
                this.zze = i6;
                bArr[i5] = (byte) (i2 >> 16);
                this.zze = i3 + 4;
                bArr[i6] = (byte) (i2 >>> 24);
            } catch (IndexOutOfBoundsException e2) {
                throw new zza(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.zze), Integer.valueOf(this.zzd), 1), e2);
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzld
        public final void zzi(int i2) throws IOException {
            if (i2 >= 0) {
                zzk(i2);
            } else {
                zzh(i2);
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzld
        public final void zzj(int i2, int i3) throws IOException {
            zzk((i2 << 3) | i3);
        }

        @Override // com.google.android.gms.internal.measurement.zzld
        public final void zzk(int i2, int i3) throws IOException {
            zzj(i2, 0);
            zzk(i3);
        }

        @Override // com.google.android.gms.internal.measurement.zzld
        public final void zza(byte b2) throws IOException {
            try {
                byte[] bArr = this.zzb;
                int i2 = this.zze;
                this.zze = i2 + 1;
                bArr[i2] = b2;
            } catch (IndexOutOfBoundsException e2) {
                throw new zza(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.zze), Integer.valueOf(this.zzd), 1), e2);
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzld
        public final void zzb(byte[] bArr, int i2, int i3) throws IOException {
            zzk(i3);
            zzc(bArr, 0, i3);
        }

        @Override // com.google.android.gms.internal.measurement.zzld
        public final void zzf(long j2) throws IOException {
            try {
                byte[] bArr = this.zzb;
                int i2 = this.zze;
                int i3 = i2 + 1;
                this.zze = i3;
                bArr[i2] = (byte) j2;
                int i4 = i2 + 2;
                this.zze = i4;
                bArr[i3] = (byte) (j2 >> 8);
                int i5 = i2 + 3;
                this.zze = i5;
                bArr[i4] = (byte) (j2 >> 16);
                int i6 = i2 + 4;
                this.zze = i6;
                bArr[i5] = (byte) (j2 >> 24);
                int i7 = i2 + 5;
                this.zze = i7;
                bArr[i6] = (byte) (j2 >> 32);
                int i8 = i2 + 6;
                this.zze = i8;
                bArr[i7] = (byte) (j2 >> 40);
                int i9 = i2 + 7;
                this.zze = i9;
                bArr[i8] = (byte) (j2 >> 48);
                this.zze = i2 + 8;
                bArr[i9] = (byte) (j2 >> 56);
            } catch (IndexOutOfBoundsException e2) {
                throw new zza(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.zze), Integer.valueOf(this.zzd), 1), e2);
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzld
        public final void zzk(int i2) throws IOException {
            while ((i2 & (-128)) != 0) {
                try {
                    byte[] bArr = this.zzb;
                    int i3 = this.zze;
                    this.zze = i3 + 1;
                    bArr[i3] = (byte) ((i2 & 127) | 128);
                    i2 >>>= 7;
                } catch (IndexOutOfBoundsException e2) {
                    throw new zza(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.zze), Integer.valueOf(this.zzd), 1), e2);
                }
            }
            byte[] bArr2 = this.zzb;
            int i4 = this.zze;
            this.zze = i4 + 1;
            bArr2[i4] = (byte) i2;
        }

        @Override // com.google.android.gms.internal.measurement.zzkj
        public final void zza(byte[] bArr, int i2, int i3) throws IOException {
            zzc(bArr, i2, i3);
        }

        @Override // com.google.android.gms.internal.measurement.zzld
        public final void zzb(zzkm zzkmVar) throws IOException {
            zzk(zzkmVar.zzb());
            zzkmVar.zza(this);
        }

        @Override // com.google.android.gms.internal.measurement.zzld
        public final void zzc(int i2, zzkm zzkmVar) throws IOException {
            zzj(i2, 2);
            zzb(zzkmVar);
        }

        @Override // com.google.android.gms.internal.measurement.zzld
        public final void zzb(int i2, zznj zznjVar) throws IOException {
            zzj(1, 3);
            zzk(2, i2);
            zzj(3, 2);
            zzc(zznjVar);
            zzj(1, 4);
        }

        @Override // com.google.android.gms.internal.measurement.zzld
        public final void zzc(zznj zznjVar) throws IOException {
            zzk(zznjVar.zzbw());
            zznjVar.zza(this);
        }

        @Override // com.google.android.gms.internal.measurement.zzld
        public final void zzh(int i2, int i3) throws IOException {
            zzj(i2, 0);
            zzi(i3);
        }

        @Override // com.google.android.gms.internal.measurement.zzld
        public final void zzh(int i2, long j2) throws IOException {
            zzj(i2, 0);
            zzh(j2);
        }

        @Override // com.google.android.gms.internal.measurement.zzld
        public final void zzh(long j2) throws IOException {
            if (zzld.zzc && zza() >= 10) {
                while ((j2 & (-128)) != 0) {
                    byte[] bArr = this.zzb;
                    int i2 = this.zze;
                    this.zze = i2 + 1;
                    zzpc.l(bArr, i2, (byte) ((((int) j2) & 127) | 128));
                    j2 >>>= 7;
                }
                byte[] bArr2 = this.zzb;
                int i3 = this.zze;
                this.zze = 1 + i3;
                zzpc.l(bArr2, i3, (byte) j2);
                return;
            }
            while ((j2 & (-128)) != 0) {
                try {
                    byte[] bArr3 = this.zzb;
                    int i4 = this.zze;
                    this.zze = i4 + 1;
                    bArr3[i4] = (byte) ((((int) j2) & 127) | 128);
                    j2 >>>= 7;
                } catch (IndexOutOfBoundsException e2) {
                    throw new zza(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.zze), Integer.valueOf(this.zzd), 1), e2);
                }
            }
            byte[] bArr4 = this.zzb;
            int i5 = this.zze;
            this.zze = i5 + 1;
            bArr4[i5] = (byte) j2;
        }

        @Override // com.google.android.gms.internal.measurement.zzld
        public final void zzb(int i2, String str) throws IOException {
            zzj(i2, 2);
            zzb(str);
        }

        @Override // com.google.android.gms.internal.measurement.zzld
        public final void zzb(String str) throws IOException {
            int i2 = this.zze;
            try {
                int iZzg = zzld.zzg(str.length() * 3);
                int iZzg2 = zzld.zzg(str.length());
                if (iZzg2 == iZzg) {
                    int i3 = i2 + iZzg2;
                    this.zze = i3;
                    int iB = zzpg.b(str, this.zzb, i3, zza());
                    this.zze = i2;
                    zzk((iB - i2) - iZzg2);
                    this.zze = iB;
                    return;
                }
                zzk(zzpg.a(str));
                this.zze = zzpg.b(str, this.zzb, this.zze, zza());
            } catch (zzpk e2) {
                this.zze = i2;
                c(str, e2);
            } catch (IndexOutOfBoundsException e3) {
                throw new zza(e3);
            }
        }
    }

    static int a(int i2, zznj zznjVar, zzob zzobVar) {
        return (zzg(i2 << 3) << 1) + ((zzkc) zznjVar).a(zzobVar);
    }

    static int b(zznj zznjVar, zzob zzobVar) {
        int iA = ((zzkc) zznjVar).a(zzobVar);
        return zzg(iA) + iA;
    }

    static int d(int i2, zznj zznjVar, zzob zzobVar) {
        return zzg(i2 << 3) + b(zznjVar, zzobVar);
    }

    public static int zza(double d2) {
        return 8;
    }

    public static int zzb(int i2) {
        return 4;
    }

    public static int zzc(long j2) {
        return 8;
    }

    public static int zzd(int i2) {
        return 4;
    }

    public static int zze(long j2) {
        int i2;
        if (((-128) & j2) == 0) {
            return 1;
        }
        if (j2 < 0) {
            return 10;
        }
        if (((-34359738368L) & j2) != 0) {
            j2 >>>= 28;
            i2 = 6;
        } else {
            i2 = 2;
        }
        if (((-2097152) & j2) != 0) {
            i2 += 2;
            j2 >>>= 14;
        }
        return (j2 & (-16384)) != 0 ? i2 + 1 : i2;
    }

    public static int zzf(int i2) {
        return zzg(i2 << 3);
    }

    public static int zzg(int i2) {
        if ((i2 & (-128)) == 0) {
            return 1;
        }
        if ((i2 & (-16384)) == 0) {
            return 2;
        }
        if (((-2097152) & i2) == 0) {
            return 3;
        }
        return (i2 & (-268435456)) == 0 ? 4 : 5;
    }

    private static long zzi(long j2) {
        return (j2 >> 63) ^ (j2 << 1);
    }

    private static int zzl(int i2) {
        return (i2 >> 31) ^ (i2 << 1);
    }

    final void c(String str, zzpk zzpkVar) throws IOException {
        zzb.logp(Level.WARNING, "com.google.protobuf.CodedOutputStream", "inefficientWriteStringNoTag", "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", (Throwable) zzpkVar);
        byte[] bytes = str.getBytes(zzlz.f13231a);
        try {
            zzk(bytes.length);
            zza(bytes, 0, bytes.length);
        } catch (IndexOutOfBoundsException e2) {
            throw new zza(e2);
        }
    }

    abstract void e(int i2, zznj zznjVar, zzob zzobVar);

    public abstract int zza();

    public abstract void zza(byte b2) throws IOException;

    public abstract void zzb(int i2, zznj zznjVar) throws IOException;

    public abstract void zzb(int i2, String str) throws IOException;

    public abstract void zzb(int i2, boolean z2) throws IOException;

    public abstract void zzb(zzkm zzkmVar) throws IOException;

    public abstract void zzb(String str) throws IOException;

    abstract void zzb(byte[] bArr, int i2, int i3);

    public abstract void zzc(int i2, zzkm zzkmVar) throws IOException;

    public abstract void zzc(zznj zznjVar) throws IOException;

    public abstract void zzd(int i2, zzkm zzkmVar) throws IOException;

    public abstract void zzf(int i2, long j2) throws IOException;

    public abstract void zzf(long j2) throws IOException;

    public abstract void zzg(int i2, int i3) throws IOException;

    public abstract void zzh(int i2) throws IOException;

    public abstract void zzh(int i2, int i3) throws IOException;

    public abstract void zzh(int i2, long j2) throws IOException;

    public abstract void zzh(long j2) throws IOException;

    public abstract void zzi(int i2) throws IOException;

    public final void zzj(int i2) throws IOException {
        zzk(zzl(i2));
    }

    public abstract void zzj(int i2, int i3) throws IOException;

    public abstract void zzk(int i2) throws IOException;

    public abstract void zzk(int i2, int i3) throws IOException;

    private zzld() {
    }

    public static int zza(float f2) {
        return 4;
    }

    public static int zzb(int i2, int i3) {
        return zzg(i2 << 3) + 4;
    }

    public static int zzc(int i2, int i3) {
        return zzg(i2 << 3) + zzc(i3);
    }

    public static int zzd(int i2, int i3) {
        return zzg(i2 << 3) + 4;
    }

    public static int zze(int i2, int i3) {
        return zzg(i2 << 3) + zzg(zzl(i3));
    }

    public static int zzf(int i2, int i3) {
        return zzg(i2 << 3) + zzg(i3);
    }

    public final void zzg(int i2, long j2) throws IOException {
        zzh(i2, zzi(j2));
    }

    public final void zzi(int i2, int i3) throws IOException {
        zzk(i2, zzl(i3));
    }

    public static int zza(long j2) {
        return 8;
    }

    public static int zzb(int i2, long j2) {
        return zzg(i2 << 3) + zze(j2);
    }

    public static int zzd(int i2, long j2) {
        return zzg(i2 << 3) + zze(zzi(j2));
    }

    public final void zzg(long j2) throws IOException {
        zzh(zzi(j2));
    }

    public static int zza(boolean z2) {
        return 1;
    }

    public static int zzc(int i2) {
        if (i2 >= 0) {
            return zzg(i2);
        }
        return 10;
    }

    public static int zze(int i2) {
        return zzg(zzl(i2));
    }

    public static int zza(int i2, boolean z2) {
        return zzg(i2 << 3) + 1;
    }

    public static int zzb(long j2) {
        return zze(j2);
    }

    public static int zzc(int i2, long j2) {
        return zzg(i2 << 3) + 8;
    }

    public static int zzd(long j2) {
        return zze(zzi(j2));
    }

    public static int zze(int i2, long j2) {
        return zzg(i2 << 3) + zze(j2);
    }

    public static int zza(byte[] bArr) {
        int length = bArr.length;
        return zzg(length) + length;
    }

    public static int zzb(int i2, zzmn zzmnVar) {
        int iZzg = zzg(i2 << 3);
        int iZzb = zzmnVar.zzb();
        return iZzg + zzg(iZzb) + iZzb;
    }

    public static int zza(int i2, zzkm zzkmVar) {
        int iZzg = zzg(i2 << 3);
        int iZzb = zzkmVar.zzb();
        return iZzg + zzg(iZzb) + iZzb;
    }

    public static int zzb(zznj zznjVar) {
        int iZzbw = zznjVar.zzbw();
        return zzg(iZzbw) + iZzbw;
    }

    public static int zza(zzkm zzkmVar) {
        int iZzb = zzkmVar.zzb();
        return zzg(iZzb) + iZzb;
    }

    public static int zzb(int i2, zzkm zzkmVar) {
        return (zzg(8) << 1) + zzf(2, i2) + zza(3, zzkmVar);
    }

    public static int zza(int i2, double d2) {
        return zzg(i2 << 3) + 8;
    }

    public static int zza(int i2, int i3) {
        return zzg(i2 << 3) + zzc(i3);
    }

    public static zzld zzb(byte[] bArr) {
        return new zzb(bArr, 0, bArr.length);
    }

    public static int zza(int i2) {
        return zzc(i2);
    }

    public final void zzb() {
        if (zza() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    public static int zza(int i2, long j2) {
        return zzg(i2 << 3) + 8;
    }

    public static int zza(int i2, float f2) {
        return zzg(i2 << 3) + 4;
    }

    public final void zzb(boolean z2) throws IOException {
        zza(z2 ? (byte) 1 : (byte) 0);
    }

    @Deprecated
    public static int zza(zznj zznjVar) {
        return zznjVar.zzbw();
    }

    public final void zzb(int i2, double d2) throws IOException {
        zzf(i2, Double.doubleToRawLongBits(d2));
    }

    public static int zza(int i2, zzmn zzmnVar) {
        return (zzg(8) << 1) + zzf(2, i2) + zzb(3, zzmnVar);
    }

    public final void zzb(double d2) throws IOException {
        zzf(Double.doubleToRawLongBits(d2));
    }

    public final void zzb(int i2, float f2) throws IOException {
        zzg(i2, Float.floatToRawIntBits(f2));
    }

    public final void zzb(float f2) throws IOException {
        zzh(Float.floatToRawIntBits(f2));
    }

    public static int zza(zzmn zzmnVar) {
        int iZzb = zzmnVar.zzb();
        return zzg(iZzb) + iZzb;
    }

    public static int zza(int i2, zznj zznjVar) {
        return (zzg(8) << 1) + zzf(2, i2) + zzg(24) + zzb(zznjVar);
    }

    public static int zza(int i2, String str) {
        return zzg(i2 << 3) + zza(str);
    }

    public static int zza(String str) {
        int length;
        try {
            length = zzpg.a(str);
        } catch (zzpk unused) {
            length = str.getBytes(zzlz.f13231a).length;
        }
        return zzg(length) + length;
    }
}
