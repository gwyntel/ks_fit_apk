package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;

/* loaded from: classes3.dex */
public abstract class zzkm implements Serializable, Iterable<Byte> {
    public static final zzkm zza = new zzkw(zzlz.zzb);
    private static final zzkp zzb = new zzkv();
    private static final Comparator<zzkm> zzc = new zzko();
    private int zzd = 0;

    zzkm() {
    }

    static /* synthetic */ int zza(byte b2) {
        return b2 & 255;
    }

    static zzkr zzc(int i2) {
        return new zzkr(i2);
    }

    public abstract boolean equals(Object obj);

    public final int hashCode() {
        int iZzb = this.zzd;
        if (iZzb == 0) {
            int iZzb2 = zzb();
            iZzb = zzb(iZzb2, 0, iZzb2);
            if (iZzb == 0) {
                iZzb = 1;
            }
            this.zzd = iZzb;
        }
        return iZzb;
    }

    @Override // java.lang.Iterable
    public /* synthetic */ Iterator<Byte> iterator() {
        return new zzkl(this);
    }

    public final String toString() {
        String strA;
        Locale locale = Locale.ROOT;
        String hexString = Integer.toHexString(System.identityHashCode(this));
        Integer numValueOf = Integer.valueOf(zzb());
        if (zzb() <= 50) {
            strA = zzos.a(this);
        } else {
            strA = zzos.a(zza(0, 47)) + "...";
        }
        return String.format(locale, "<ByteString@%s size=%d contents=\"%s\">", hexString, numValueOf, strA);
    }

    public abstract byte zza(int i2);

    public abstract zzkm zza(int i2, int i3);

    protected abstract String zza(Charset charset);

    abstract void zza(zzkj zzkjVar) throws IOException;

    abstract byte zzb(int i2);

    public abstract int zzb();

    protected abstract int zzb(int i2, int i3, int i4);

    public abstract boolean zzd();

    static int zza(int i2, int i3, int i4) {
        int i5 = i3 - i2;
        if ((i2 | i3 | i5 | (i4 - i3)) >= 0) {
            return i5;
        }
        if (i2 < 0) {
            throw new IndexOutOfBoundsException("Beginning index: " + i2 + " < 0");
        }
        if (i3 < i2) {
            throw new IndexOutOfBoundsException("Beginning index larger than ending index: " + i2 + ", " + i3);
        }
        throw new IndexOutOfBoundsException("End index: " + i3 + " >= " + i4);
    }

    public final String zzc() {
        return zzb() == 0 ? "" : zza(zzlz.f13231a);
    }

    protected final int zza() {
        return this.zzd;
    }

    public static zzkm zza(byte[] bArr, int i2, int i3) {
        zza(i2, i2 + i3, bArr.length);
        return new zzkw(zzb.zza(bArr, i2, i3));
    }

    public static zzkm zza(String str) {
        return new zzkw(str.getBytes(zzlz.f13231a));
    }

    static zzkm zza(byte[] bArr) {
        return new zzkw(bArr);
    }
}
