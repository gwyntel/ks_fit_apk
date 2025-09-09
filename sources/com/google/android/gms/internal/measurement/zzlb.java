package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.List;

/* loaded from: classes3.dex */
final class zzlb implements zzny {
    private final zzkx zza;
    private int zzb;
    private int zzc;
    private int zzd = 0;

    private zzlb(zzkx zzkxVar) {
        zzkx zzkxVar2 = (zzkx) zzlz.c(zzkxVar, "input");
        this.zza = zzkxVar2;
        zzkxVar2.f13222c = this;
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final double zza() throws IOException {
        zzb(1);
        return this.zza.zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final float zzb() throws IOException {
        zzb(5);
        return this.zza.zzb();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final int zzc() throws IOException {
        int i2 = this.zzd;
        if (i2 != 0) {
            this.zzb = i2;
            this.zzd = 0;
        } else {
            this.zzb = this.zza.zzi();
        }
        int i3 = this.zzb;
        if (i3 == 0 || i3 == this.zzc) {
            return Integer.MAX_VALUE;
        }
        return i3 >>> 3;
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final int zzd() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final int zze() throws IOException {
        zzb(0);
        return this.zza.zzd();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final int zzf() throws IOException {
        zzb(5);
        return this.zza.zze();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final int zzg() throws IOException {
        zzb(0);
        return this.zza.zzf();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final int zzh() throws IOException {
        zzb(5);
        return this.zza.zzg();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final int zzi() throws IOException {
        zzb(0);
        return this.zza.zzh();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final int zzj() throws IOException {
        zzb(0);
        return this.zza.zzj();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final long zzk() throws IOException {
        zzb(1);
        return this.zza.zzk();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final long zzl() throws IOException {
        zzb(0);
        return this.zza.zzl();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final long zzm() throws IOException {
        zzb(1);
        return this.zza.zzn();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final long zzn() throws IOException {
        zzb(0);
        return this.zza.zzo();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final long zzo() throws IOException {
        zzb(0);
        return this.zza.zzp();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final zzkm zzp() throws IOException {
        zzb(2);
        return this.zza.zzq();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final String zzq() throws IOException {
        zzb(2);
        return this.zza.zzr();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final String zzr() throws IOException {
        zzb(2);
        return this.zza.zzs();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final boolean zzs() throws IOException {
        zzb(0);
        return this.zza.zzu();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final boolean zzt() throws IOException {
        int i2;
        if (this.zza.zzt() || (i2 = this.zzb) == this.zzc) {
            return false;
        }
        return this.zza.zze(i2);
    }

    private final <T> void zzd(T t2, zzob<T> zzobVar, zzlj zzljVar) throws IOException {
        int iZzj = this.zza.zzj();
        zzkx zzkxVar = this.zza;
        if (zzkxVar.f13220a >= zzkxVar.f13221b) {
            throw new zzme("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
        }
        int iZzb = zzkxVar.zzb(iZzj);
        this.zza.f13220a++;
        zzobVar.zza(t2, this, zzljVar);
        this.zza.zzc(0);
        r5.f13220a--;
        this.zza.zzd(iZzb);
    }

    public static zzlb zza(zzkx zzkxVar) {
        zzlb zzlbVar = zzkxVar.f13222c;
        return zzlbVar != null ? zzlbVar : new zzlb(zzkxVar);
    }

    private final <T> T zzb(zzob<T> zzobVar, zzlj zzljVar) throws IOException {
        T tZza = zzobVar.zza();
        zzd(tZza, zzobVar, zzljVar);
        zzobVar.zzc(tZza);
        return tZza;
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final void zze(List<Integer> list) throws IOException {
        int iZzi;
        int iZzi2;
        if (list instanceof zzlx) {
            zzlx zzlxVar = (zzlx) list;
            int i2 = this.zzb & 7;
            if (i2 == 2) {
                int iZzj = this.zza.zzj();
                zzc(iZzj);
                int iZzc = this.zza.zzc() + iZzj;
                do {
                    zzlxVar.zzd(this.zza.zze());
                } while (this.zza.zzc() < iZzc);
                return;
            }
            if (i2 == 5) {
                do {
                    zzlxVar.zzd(this.zza.zze());
                    if (this.zza.zzt()) {
                        return;
                    } else {
                        iZzi2 = this.zza.zzi();
                    }
                } while (iZzi2 == this.zzb);
                this.zzd = iZzi2;
                return;
            }
            throw zzme.zza();
        }
        int i3 = this.zzb & 7;
        if (i3 == 2) {
            int iZzj2 = this.zza.zzj();
            zzc(iZzj2);
            int iZzc2 = this.zza.zzc() + iZzj2;
            do {
                list.add(Integer.valueOf(this.zza.zze()));
            } while (this.zza.zzc() < iZzc2);
            return;
        }
        if (i3 == 5) {
            do {
                list.add(Integer.valueOf(this.zza.zze()));
                if (this.zza.zzt()) {
                    return;
                } else {
                    iZzi = this.zza.zzi();
                }
            } while (iZzi == this.zzb);
            this.zzd = iZzi;
            return;
        }
        throw zzme.zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final void zzf(List<Long> list) throws IOException {
        int iZzi;
        int iZzi2;
        if (list instanceof zzmu) {
            zzmu zzmuVar = (zzmu) list;
            int i2 = this.zzb & 7;
            if (i2 == 1) {
                do {
                    zzmuVar.zza(this.zza.zzk());
                    if (this.zza.zzt()) {
                        return;
                    } else {
                        iZzi2 = this.zza.zzi();
                    }
                } while (iZzi2 == this.zzb);
                this.zzd = iZzi2;
                return;
            }
            if (i2 == 2) {
                int iZzj = this.zza.zzj();
                zzd(iZzj);
                int iZzc = this.zza.zzc() + iZzj;
                do {
                    zzmuVar.zza(this.zza.zzk());
                } while (this.zza.zzc() < iZzc);
                return;
            }
            throw zzme.zza();
        }
        int i3 = this.zzb & 7;
        if (i3 == 1) {
            do {
                list.add(Long.valueOf(this.zza.zzk()));
                if (this.zza.zzt()) {
                    return;
                } else {
                    iZzi = this.zza.zzi();
                }
            } while (iZzi == this.zzb);
            this.zzd = iZzi;
            return;
        }
        if (i3 == 2) {
            int iZzj2 = this.zza.zzj();
            zzd(iZzj2);
            int iZzc2 = this.zza.zzc() + iZzj2;
            do {
                list.add(Long.valueOf(this.zza.zzk()));
            } while (this.zza.zzc() < iZzc2);
            return;
        }
        throw zzme.zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final void zzg(List<Float> list) throws IOException {
        int iZzi;
        int iZzi2;
        if (list instanceof zzls) {
            zzls zzlsVar = (zzls) list;
            int i2 = this.zzb & 7;
            if (i2 == 2) {
                int iZzj = this.zza.zzj();
                zzc(iZzj);
                int iZzc = this.zza.zzc() + iZzj;
                do {
                    zzlsVar.zza(this.zza.zzb());
                } while (this.zza.zzc() < iZzc);
                return;
            }
            if (i2 == 5) {
                do {
                    zzlsVar.zza(this.zza.zzb());
                    if (this.zza.zzt()) {
                        return;
                    } else {
                        iZzi2 = this.zza.zzi();
                    }
                } while (iZzi2 == this.zzb);
                this.zzd = iZzi2;
                return;
            }
            throw zzme.zza();
        }
        int i3 = this.zzb & 7;
        if (i3 == 2) {
            int iZzj2 = this.zza.zzj();
            zzc(iZzj2);
            int iZzc2 = this.zza.zzc() + iZzj2;
            do {
                list.add(Float.valueOf(this.zza.zzb()));
            } while (this.zza.zzc() < iZzc2);
            return;
        }
        if (i3 == 5) {
            do {
                list.add(Float.valueOf(this.zza.zzb()));
                if (this.zza.zzt()) {
                    return;
                } else {
                    iZzi = this.zza.zzi();
                }
            } while (iZzi == this.zzb);
            this.zzd = iZzi;
            return;
        }
        throw zzme.zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final void zzh(List<Integer> list) throws IOException {
        int iZzi;
        int iZzi2;
        if (list instanceof zzlx) {
            zzlx zzlxVar = (zzlx) list;
            int i2 = this.zzb & 7;
            if (i2 == 0) {
                do {
                    zzlxVar.zzd(this.zza.zzf());
                    if (this.zza.zzt()) {
                        return;
                    } else {
                        iZzi2 = this.zza.zzi();
                    }
                } while (iZzi2 == this.zzb);
                this.zzd = iZzi2;
                return;
            }
            if (i2 == 2) {
                int iZzc = this.zza.zzc() + this.zza.zzj();
                do {
                    zzlxVar.zzd(this.zza.zzf());
                } while (this.zza.zzc() < iZzc);
                zza(iZzc);
                return;
            }
            throw zzme.zza();
        }
        int i3 = this.zzb & 7;
        if (i3 == 0) {
            do {
                list.add(Integer.valueOf(this.zza.zzf()));
                if (this.zza.zzt()) {
                    return;
                } else {
                    iZzi = this.zza.zzi();
                }
            } while (iZzi == this.zzb);
            this.zzd = iZzi;
            return;
        }
        if (i3 == 2) {
            int iZzc2 = this.zza.zzc() + this.zza.zzj();
            do {
                list.add(Integer.valueOf(this.zza.zzf()));
            } while (this.zza.zzc() < iZzc2);
            zza(iZzc2);
            return;
        }
        throw zzme.zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final void zzi(List<Long> list) throws IOException {
        int iZzi;
        int iZzi2;
        if (list instanceof zzmu) {
            zzmu zzmuVar = (zzmu) list;
            int i2 = this.zzb & 7;
            if (i2 == 0) {
                do {
                    zzmuVar.zza(this.zza.zzl());
                    if (this.zza.zzt()) {
                        return;
                    } else {
                        iZzi2 = this.zza.zzi();
                    }
                } while (iZzi2 == this.zzb);
                this.zzd = iZzi2;
                return;
            }
            if (i2 == 2) {
                int iZzc = this.zza.zzc() + this.zza.zzj();
                do {
                    zzmuVar.zza(this.zza.zzl());
                } while (this.zza.zzc() < iZzc);
                zza(iZzc);
                return;
            }
            throw zzme.zza();
        }
        int i3 = this.zzb & 7;
        if (i3 == 0) {
            do {
                list.add(Long.valueOf(this.zza.zzl()));
                if (this.zza.zzt()) {
                    return;
                } else {
                    iZzi = this.zza.zzi();
                }
            } while (iZzi == this.zzb);
            this.zzd = iZzi;
            return;
        }
        if (i3 == 2) {
            int iZzc2 = this.zza.zzc() + this.zza.zzj();
            do {
                list.add(Long.valueOf(this.zza.zzl()));
            } while (this.zza.zzc() < iZzc2);
            zza(iZzc2);
            return;
        }
        throw zzme.zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final void zzj(List<Integer> list) throws IOException {
        int iZzi;
        int iZzi2;
        if (list instanceof zzlx) {
            zzlx zzlxVar = (zzlx) list;
            int i2 = this.zzb & 7;
            if (i2 == 2) {
                int iZzj = this.zza.zzj();
                zzc(iZzj);
                int iZzc = this.zza.zzc() + iZzj;
                do {
                    zzlxVar.zzd(this.zza.zzg());
                } while (this.zza.zzc() < iZzc);
                return;
            }
            if (i2 == 5) {
                do {
                    zzlxVar.zzd(this.zza.zzg());
                    if (this.zza.zzt()) {
                        return;
                    } else {
                        iZzi2 = this.zza.zzi();
                    }
                } while (iZzi2 == this.zzb);
                this.zzd = iZzi2;
                return;
            }
            throw zzme.zza();
        }
        int i3 = this.zzb & 7;
        if (i3 == 2) {
            int iZzj2 = this.zza.zzj();
            zzc(iZzj2);
            int iZzc2 = this.zza.zzc() + iZzj2;
            do {
                list.add(Integer.valueOf(this.zza.zzg()));
            } while (this.zza.zzc() < iZzc2);
            return;
        }
        if (i3 == 5) {
            do {
                list.add(Integer.valueOf(this.zza.zzg()));
                if (this.zza.zzt()) {
                    return;
                } else {
                    iZzi = this.zza.zzi();
                }
            } while (iZzi == this.zzb);
            this.zzd = iZzi;
            return;
        }
        throw zzme.zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final void zzk(List<Long> list) throws IOException {
        int iZzi;
        int iZzi2;
        if (list instanceof zzmu) {
            zzmu zzmuVar = (zzmu) list;
            int i2 = this.zzb & 7;
            if (i2 == 1) {
                do {
                    zzmuVar.zza(this.zza.zzn());
                    if (this.zza.zzt()) {
                        return;
                    } else {
                        iZzi2 = this.zza.zzi();
                    }
                } while (iZzi2 == this.zzb);
                this.zzd = iZzi2;
                return;
            }
            if (i2 == 2) {
                int iZzj = this.zza.zzj();
                zzd(iZzj);
                int iZzc = this.zza.zzc() + iZzj;
                do {
                    zzmuVar.zza(this.zza.zzn());
                } while (this.zza.zzc() < iZzc);
                return;
            }
            throw zzme.zza();
        }
        int i3 = this.zzb & 7;
        if (i3 == 1) {
            do {
                list.add(Long.valueOf(this.zza.zzn()));
                if (this.zza.zzt()) {
                    return;
                } else {
                    iZzi = this.zza.zzi();
                }
            } while (iZzi == this.zzb);
            this.zzd = iZzi;
            return;
        }
        if (i3 == 2) {
            int iZzj2 = this.zza.zzj();
            zzd(iZzj2);
            int iZzc2 = this.zza.zzc() + iZzj2;
            do {
                list.add(Long.valueOf(this.zza.zzn()));
            } while (this.zza.zzc() < iZzc2);
            return;
        }
        throw zzme.zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final void zzl(List<Integer> list) throws IOException {
        int iZzi;
        int iZzi2;
        if (list instanceof zzlx) {
            zzlx zzlxVar = (zzlx) list;
            int i2 = this.zzb & 7;
            if (i2 == 0) {
                do {
                    zzlxVar.zzd(this.zza.zzh());
                    if (this.zza.zzt()) {
                        return;
                    } else {
                        iZzi2 = this.zza.zzi();
                    }
                } while (iZzi2 == this.zzb);
                this.zzd = iZzi2;
                return;
            }
            if (i2 == 2) {
                int iZzc = this.zza.zzc() + this.zza.zzj();
                do {
                    zzlxVar.zzd(this.zza.zzh());
                } while (this.zza.zzc() < iZzc);
                zza(iZzc);
                return;
            }
            throw zzme.zza();
        }
        int i3 = this.zzb & 7;
        if (i3 == 0) {
            do {
                list.add(Integer.valueOf(this.zza.zzh()));
                if (this.zza.zzt()) {
                    return;
                } else {
                    iZzi = this.zza.zzi();
                }
            } while (iZzi == this.zzb);
            this.zzd = iZzi;
            return;
        }
        if (i3 == 2) {
            int iZzc2 = this.zza.zzc() + this.zza.zzj();
            do {
                list.add(Integer.valueOf(this.zza.zzh()));
            } while (this.zza.zzc() < iZzc2);
            zza(iZzc2);
            return;
        }
        throw zzme.zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final void zzm(List<Long> list) throws IOException {
        int iZzi;
        int iZzi2;
        if (list instanceof zzmu) {
            zzmu zzmuVar = (zzmu) list;
            int i2 = this.zzb & 7;
            if (i2 == 0) {
                do {
                    zzmuVar.zza(this.zza.zzo());
                    if (this.zza.zzt()) {
                        return;
                    } else {
                        iZzi2 = this.zza.zzi();
                    }
                } while (iZzi2 == this.zzb);
                this.zzd = iZzi2;
                return;
            }
            if (i2 == 2) {
                int iZzc = this.zza.zzc() + this.zza.zzj();
                do {
                    zzmuVar.zza(this.zza.zzo());
                } while (this.zza.zzc() < iZzc);
                zza(iZzc);
                return;
            }
            throw zzme.zza();
        }
        int i3 = this.zzb & 7;
        if (i3 == 0) {
            do {
                list.add(Long.valueOf(this.zza.zzo()));
                if (this.zza.zzt()) {
                    return;
                } else {
                    iZzi = this.zza.zzi();
                }
            } while (iZzi == this.zzb);
            this.zzd = iZzi;
            return;
        }
        if (i3 == 2) {
            int iZzc2 = this.zza.zzc() + this.zza.zzj();
            do {
                list.add(Long.valueOf(this.zza.zzo()));
            } while (this.zza.zzc() < iZzc2);
            zza(iZzc2);
            return;
        }
        throw zzme.zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final void zzn(List<String> list) throws IOException {
        zza(list, false);
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final void zzo(List<String> list) throws IOException {
        zza(list, true);
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final void zzp(List<Integer> list) throws IOException {
        int iZzi;
        int iZzi2;
        if (list instanceof zzlx) {
            zzlx zzlxVar = (zzlx) list;
            int i2 = this.zzb & 7;
            if (i2 == 0) {
                do {
                    zzlxVar.zzd(this.zza.zzj());
                    if (this.zza.zzt()) {
                        return;
                    } else {
                        iZzi2 = this.zza.zzi();
                    }
                } while (iZzi2 == this.zzb);
                this.zzd = iZzi2;
                return;
            }
            if (i2 == 2) {
                int iZzc = this.zza.zzc() + this.zza.zzj();
                do {
                    zzlxVar.zzd(this.zza.zzj());
                } while (this.zza.zzc() < iZzc);
                zza(iZzc);
                return;
            }
            throw zzme.zza();
        }
        int i3 = this.zzb & 7;
        if (i3 == 0) {
            do {
                list.add(Integer.valueOf(this.zza.zzj()));
                if (this.zza.zzt()) {
                    return;
                } else {
                    iZzi = this.zza.zzi();
                }
            } while (iZzi == this.zzb);
            this.zzd = iZzi;
            return;
        }
        if (i3 == 2) {
            int iZzc2 = this.zza.zzc() + this.zza.zzj();
            do {
                list.add(Integer.valueOf(this.zza.zzj()));
            } while (this.zza.zzc() < iZzc2);
            zza(iZzc2);
            return;
        }
        throw zzme.zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final void zzq(List<Long> list) throws IOException {
        int iZzi;
        int iZzi2;
        if (list instanceof zzmu) {
            zzmu zzmuVar = (zzmu) list;
            int i2 = this.zzb & 7;
            if (i2 == 0) {
                do {
                    zzmuVar.zza(this.zza.zzp());
                    if (this.zza.zzt()) {
                        return;
                    } else {
                        iZzi2 = this.zza.zzi();
                    }
                } while (iZzi2 == this.zzb);
                this.zzd = iZzi2;
                return;
            }
            if (i2 == 2) {
                int iZzc = this.zza.zzc() + this.zza.zzj();
                do {
                    zzmuVar.zza(this.zza.zzp());
                } while (this.zza.zzc() < iZzc);
                zza(iZzc);
                return;
            }
            throw zzme.zza();
        }
        int i3 = this.zzb & 7;
        if (i3 == 0) {
            do {
                list.add(Long.valueOf(this.zza.zzp()));
                if (this.zza.zzt()) {
                    return;
                } else {
                    iZzi = this.zza.zzi();
                }
            } while (iZzi == this.zzb);
            this.zzd = iZzi;
            return;
        }
        if (i3 == 2) {
            int iZzc2 = this.zza.zzc() + this.zza.zzj();
            do {
                list.add(Long.valueOf(this.zza.zzp()));
            } while (this.zza.zzc() < iZzc2);
            zza(iZzc2);
            return;
        }
        throw zzme.zza();
    }

    private final Object zza(zzpj zzpjVar, Class<?> cls, zzlj zzljVar) throws IOException {
        switch (zzle.f13224a[zzpjVar.ordinal()]) {
            case 1:
                return Boolean.valueOf(zzs());
            case 2:
                return zzp();
            case 3:
                return Double.valueOf(zza());
            case 4:
                return Integer.valueOf(zze());
            case 5:
                return Integer.valueOf(zzf());
            case 6:
                return Long.valueOf(zzk());
            case 7:
                return Float.valueOf(zzb());
            case 8:
                return Integer.valueOf(zzg());
            case 9:
                return Long.valueOf(zzl());
            case 10:
                zzb(2);
                return zzb(zznx.zza().zza((Class) cls), zzljVar);
            case 11:
                return Integer.valueOf(zzh());
            case 12:
                return Long.valueOf(zzm());
            case 13:
                return Integer.valueOf(zzi());
            case 14:
                return Long.valueOf(zzn());
            case 15:
                return zzr();
            case 16:
                return Integer.valueOf(zzj());
            case 17:
                return Long.valueOf(zzo());
            default:
                throw new IllegalArgumentException("unsupported field type.");
        }
    }

    private final <T> void zzc(T t2, zzob<T> zzobVar, zzlj zzljVar) throws IOException {
        int i2 = this.zzc;
        this.zzc = ((this.zzb >>> 3) << 3) | 4;
        try {
            zzobVar.zza(t2, this, zzljVar);
            if (this.zzb == this.zzc) {
            } else {
                throw zzme.zzg();
            }
        } finally {
            this.zzc = i2;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final <T> void zzb(T t2, zzob<T> zzobVar, zzlj zzljVar) throws IOException {
        zzb(2);
        zzd(t2, zzobVar, zzljVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final void zzb(List<zzkm> list) throws IOException {
        int iZzi;
        if ((this.zzb & 7) == 2) {
            do {
                list.add(zzp());
                if (this.zza.zzt()) {
                    return;
                } else {
                    iZzi = this.zza.zzi();
                }
            } while (iZzi == this.zzb);
            this.zzd = iZzi;
            return;
        }
        throw zzme.zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final void zzd(List<Integer> list) throws IOException {
        int iZzi;
        int iZzi2;
        if (list instanceof zzlx) {
            zzlx zzlxVar = (zzlx) list;
            int i2 = this.zzb & 7;
            if (i2 == 0) {
                do {
                    zzlxVar.zzd(this.zza.zzd());
                    if (this.zza.zzt()) {
                        return;
                    } else {
                        iZzi2 = this.zza.zzi();
                    }
                } while (iZzi2 == this.zzb);
                this.zzd = iZzi2;
                return;
            }
            if (i2 == 2) {
                int iZzc = this.zza.zzc() + this.zza.zzj();
                do {
                    zzlxVar.zzd(this.zza.zzd());
                } while (this.zza.zzc() < iZzc);
                zza(iZzc);
                return;
            }
            throw zzme.zza();
        }
        int i3 = this.zzb & 7;
        if (i3 == 0) {
            do {
                list.add(Integer.valueOf(this.zza.zzd()));
                if (this.zza.zzt()) {
                    return;
                } else {
                    iZzi = this.zza.zzi();
                }
            } while (iZzi == this.zzb);
            this.zzd = iZzi;
            return;
        }
        if (i3 == 2) {
            int iZzc2 = this.zza.zzc() + this.zza.zzj();
            do {
                list.add(Integer.valueOf(this.zza.zzd()));
            } while (this.zza.zzc() < iZzc2);
            zza(iZzc2);
            return;
        }
        throw zzme.zza();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.measurement.zzny
    public final <T> void zzb(List<T> list, zzob<T> zzobVar, zzlj zzljVar) throws IOException {
        int iZzi;
        int i2 = this.zzb;
        if ((i2 & 7) == 2) {
            do {
                list.add(zzb(zzobVar, zzljVar));
                if (this.zza.zzt() || this.zzd != 0) {
                    return;
                } else {
                    iZzi = this.zza.zzi();
                }
            } while (iZzi == i2);
            this.zzd = iZzi;
            return;
        }
        throw zzme.zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final void zzc(List<Double> list) throws IOException {
        int iZzi;
        int iZzi2;
        if (list instanceof zzli) {
            zzli zzliVar = (zzli) list;
            int i2 = this.zzb & 7;
            if (i2 == 1) {
                do {
                    zzliVar.zza(this.zza.zza());
                    if (this.zza.zzt()) {
                        return;
                    } else {
                        iZzi2 = this.zza.zzi();
                    }
                } while (iZzi2 == this.zzb);
                this.zzd = iZzi2;
                return;
            }
            if (i2 == 2) {
                int iZzj = this.zza.zzj();
                zzd(iZzj);
                int iZzc = this.zza.zzc() + iZzj;
                do {
                    zzliVar.zza(this.zza.zza());
                } while (this.zza.zzc() < iZzc);
                return;
            }
            throw zzme.zza();
        }
        int i3 = this.zzb & 7;
        if (i3 == 1) {
            do {
                list.add(Double.valueOf(this.zza.zza()));
                if (this.zza.zzt()) {
                    return;
                } else {
                    iZzi = this.zza.zzi();
                }
            } while (iZzi == this.zzb);
            this.zzd = iZzi;
            return;
        }
        if (i3 == 2) {
            int iZzj2 = this.zza.zzj();
            zzd(iZzj2);
            int iZzc2 = this.zza.zzc() + iZzj2;
            do {
                list.add(Double.valueOf(this.zza.zza()));
            } while (this.zza.zzc() < iZzc2);
            return;
        }
        throw zzme.zza();
    }

    private final void zzb(int i2) throws IOException {
        if ((this.zzb & 7) != i2) {
            throw zzme.zza();
        }
    }

    private final <T> T zza(zzob<T> zzobVar, zzlj zzljVar) throws IOException {
        T tZza = zzobVar.zza();
        zzc(tZza, zzobVar, zzljVar);
        zzobVar.zzc(tZza);
        return tZza;
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final <T> void zza(T t2, zzob<T> zzobVar, zzlj zzljVar) throws IOException {
        zzb(3);
        zzc(t2, zzobVar, zzljVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzny
    public final void zza(List<Boolean> list) throws IOException {
        int iZzi;
        int iZzi2;
        if (list instanceof zzkk) {
            zzkk zzkkVar = (zzkk) list;
            int i2 = this.zzb & 7;
            if (i2 == 0) {
                do {
                    zzkkVar.zza(this.zza.zzu());
                    if (this.zza.zzt()) {
                        return;
                    } else {
                        iZzi2 = this.zza.zzi();
                    }
                } while (iZzi2 == this.zzb);
                this.zzd = iZzi2;
                return;
            }
            if (i2 == 2) {
                int iZzc = this.zza.zzc() + this.zza.zzj();
                do {
                    zzkkVar.zza(this.zza.zzu());
                } while (this.zza.zzc() < iZzc);
                zza(iZzc);
                return;
            }
            throw zzme.zza();
        }
        int i3 = this.zzb & 7;
        if (i3 == 0) {
            do {
                list.add(Boolean.valueOf(this.zza.zzu()));
                if (this.zza.zzt()) {
                    return;
                } else {
                    iZzi = this.zza.zzi();
                }
            } while (iZzi == this.zzb);
            this.zzd = iZzi;
            return;
        }
        if (i3 == 2) {
            int iZzc2 = this.zza.zzc() + this.zza.zzj();
            do {
                list.add(Boolean.valueOf(this.zza.zzu()));
            } while (this.zza.zzc() < iZzc2);
            zza(iZzc2);
            return;
        }
        throw zzme.zza();
    }

    private static void zzd(int i2) throws IOException {
        if ((i2 & 7) != 0) {
            throw zzme.zzg();
        }
    }

    private static void zzc(int i2) throws IOException {
        if ((i2 & 3) != 0) {
            throw zzme.zzg();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.measurement.zzny
    @Deprecated
    public final <T> void zza(List<T> list, zzob<T> zzobVar, zzlj zzljVar) throws IOException {
        int iZzi;
        int i2 = this.zzb;
        if ((i2 & 7) == 3) {
            do {
                list.add(zza(zzobVar, zzljVar));
                if (this.zza.zzt() || this.zzd != 0) {
                    return;
                } else {
                    iZzi = this.zza.zzi();
                }
            } while (iZzi == i2);
            this.zzd = iZzi;
            return;
        }
        throw zzme.zza();
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x005d, code lost:
    
        r8.put(r2, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0060, code lost:
    
        r7.zza.zzd(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0065, code lost:
    
        return;
     */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.measurement.zzny
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final <K, V> void zza(java.util.Map<K, V> r8, com.google.android.gms.internal.measurement.zzna<K, V> r9, com.google.android.gms.internal.measurement.zzlj r10) throws java.io.IOException {
        /*
            r7 = this;
            r0 = 2
            r7.zzb(r0)
            com.google.android.gms.internal.measurement.zzkx r1 = r7.zza
            int r1 = r1.zzj()
            com.google.android.gms.internal.measurement.zzkx r2 = r7.zza
            int r1 = r2.zzb(r1)
            K r2 = r9.zzb
            V r3 = r9.zzd
        L14:
            int r4 = r7.zzc()     // Catch: java.lang.Throwable -> L39
            r5 = 2147483647(0x7fffffff, float:NaN)
            if (r4 == r5) goto L5d
            com.google.android.gms.internal.measurement.zzkx r5 = r7.zza     // Catch: java.lang.Throwable -> L39
            boolean r5 = r5.zzt()     // Catch: java.lang.Throwable -> L39
            if (r5 != 0) goto L5d
            r5 = 1
            java.lang.String r6 = "Unable to parse map entry."
            if (r4 == r5) goto L48
            if (r4 == r0) goto L3b
            boolean r4 = r7.zzt()     // Catch: java.lang.Throwable -> L39 com.google.android.gms.internal.measurement.zzmh -> L50
            if (r4 == 0) goto L33
            goto L14
        L33:
            com.google.android.gms.internal.measurement.zzme r4 = new com.google.android.gms.internal.measurement.zzme     // Catch: java.lang.Throwable -> L39 com.google.android.gms.internal.measurement.zzmh -> L50
            r4.<init>(r6)     // Catch: java.lang.Throwable -> L39 com.google.android.gms.internal.measurement.zzmh -> L50
            throw r4     // Catch: java.lang.Throwable -> L39 com.google.android.gms.internal.measurement.zzmh -> L50
        L39:
            r8 = move-exception
            goto L66
        L3b:
            com.google.android.gms.internal.measurement.zzpj r4 = r9.zzc     // Catch: java.lang.Throwable -> L39 com.google.android.gms.internal.measurement.zzmh -> L50
            V r5 = r9.zzd     // Catch: java.lang.Throwable -> L39 com.google.android.gms.internal.measurement.zzmh -> L50
            java.lang.Class r5 = r5.getClass()     // Catch: java.lang.Throwable -> L39 com.google.android.gms.internal.measurement.zzmh -> L50
            java.lang.Object r3 = r7.zza(r4, r5, r10)     // Catch: java.lang.Throwable -> L39 com.google.android.gms.internal.measurement.zzmh -> L50
            goto L14
        L48:
            com.google.android.gms.internal.measurement.zzpj r4 = r9.zza     // Catch: java.lang.Throwable -> L39 com.google.android.gms.internal.measurement.zzmh -> L50
            r5 = 0
            java.lang.Object r2 = r7.zza(r4, r5, r5)     // Catch: java.lang.Throwable -> L39 com.google.android.gms.internal.measurement.zzmh -> L50
            goto L14
        L50:
            boolean r4 = r7.zzt()     // Catch: java.lang.Throwable -> L39
            if (r4 == 0) goto L57
            goto L14
        L57:
            com.google.android.gms.internal.measurement.zzme r8 = new com.google.android.gms.internal.measurement.zzme     // Catch: java.lang.Throwable -> L39
            r8.<init>(r6)     // Catch: java.lang.Throwable -> L39
            throw r8     // Catch: java.lang.Throwable -> L39
        L5d:
            r8.put(r2, r3)     // Catch: java.lang.Throwable -> L39
            com.google.android.gms.internal.measurement.zzkx r8 = r7.zza
            r8.zzd(r1)
            return
        L66:
            com.google.android.gms.internal.measurement.zzkx r9 = r7.zza
            r9.zzd(r1)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzlb.zza(java.util.Map, com.google.android.gms.internal.measurement.zzna, com.google.android.gms.internal.measurement.zzlj):void");
    }

    private final void zza(List<String> list, boolean z2) throws IOException {
        int iZzi;
        int iZzi2;
        if ((this.zzb & 7) == 2) {
            if ((list instanceof zzmp) && !z2) {
                zzmp zzmpVar = (zzmp) list;
                do {
                    zzmpVar.zza(zzp());
                    if (this.zza.zzt()) {
                        return;
                    } else {
                        iZzi2 = this.zza.zzi();
                    }
                } while (iZzi2 == this.zzb);
                this.zzd = iZzi2;
                return;
            }
            do {
                list.add(z2 ? zzr() : zzq());
                if (this.zza.zzt()) {
                    return;
                } else {
                    iZzi = this.zza.zzi();
                }
            } while (iZzi == this.zzb);
            this.zzd = iZzi;
            return;
        }
        throw zzme.zza();
    }

    private final void zza(int i2) throws IOException {
        if (this.zza.zzc() != i2) {
            throw zzme.zzh();
        }
    }
}
