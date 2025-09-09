package com.google.android.gms.internal.measurement;

import androidx.media3.common.C;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.DiskLruHelper;
import com.google.android.gms.internal.measurement.zzlw;
import java.util.Collections;
import java.util.List;
import kotlin.text.Typography;

/* loaded from: classes3.dex */
public final class zzfh {

    public static final class zza extends zzlw<zza, C0110zza> implements zznl {
        private static final zza zzc;
        private static volatile zzns<zza> zzd;
        private int zze;
        private String zzf = "";
        private String zzg = "";
        private String zzh = "";
        private String zzi = "";
        private String zzj = "";
        private String zzk = "";
        private String zzl = "";

        /* renamed from: com.google.android.gms.internal.measurement.zzfh$zza$zza, reason: collision with other inner class name */
        public static final class C0110zza extends zzlw.zza<zza, C0110zza> implements zznl {
            private C0110zza() {
                super(zza.zzc);
            }

            /* synthetic */ C0110zza(zzfj zzfjVar) {
                this();
            }
        }

        static {
            zza zzaVar = new zza();
            zzc = zzaVar;
            zzlw.k(zza.class, zzaVar);
        }

        private zza() {
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzfj zzfjVar = null;
            switch (zzfj.f13199a[i2 - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new C0110zza(zzfjVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005ဈ\u0004\u0006ဈ\u0005\u0007ဈ\u0006", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl"});
                case 4:
                    return zzc;
                case 5:
                    zzns<zza> zzcVar = zzd;
                    if (zzcVar == null) {
                        synchronized (zza.class) {
                            try {
                                zzcVar = zzd;
                                if (zzcVar == null) {
                                    zzcVar = new zzlw.zzc<>(zzc);
                                    zzd = zzcVar;
                                }
                            } finally {
                            }
                        }
                    }
                    return zzcVar;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }

    public static final class zzb extends zzlw<zzb, zza> implements zznl {
        private static final zzb zzc;
        private static volatile zzns<zzb> zzd;
        private int zze;
        private boolean zzf;
        private boolean zzg;
        private boolean zzh;
        private boolean zzi;
        private boolean zzj;
        private boolean zzk;
        private boolean zzl;

        public static final class zza extends zzlw.zza<zzb, zza> implements zznl {
            private zza() {
                super(zzb.zzc);
            }

            public final zza zza(boolean z2) {
                a();
                ((zzb) this.f13230a).zza(z2);
                return this;
            }

            public final zza zzb(boolean z2) {
                a();
                ((zzb) this.f13230a).zzb(z2);
                return this;
            }

            public final zza zzc(boolean z2) {
                a();
                ((zzb) this.f13230a).zzc(z2);
                return this;
            }

            public final zza zzd(boolean z2) {
                a();
                ((zzb) this.f13230a).zzd(z2);
                return this;
            }

            public final zza zze(boolean z2) {
                a();
                ((zzb) this.f13230a).zze(z2);
                return this;
            }

            public final zza zzf(boolean z2) {
                a();
                ((zzb) this.f13230a).zzf(z2);
                return this;
            }

            public final zza zzg(boolean z2) {
                a();
                ((zzb) this.f13230a).zzg(z2);
                return this;
            }

            /* synthetic */ zza(zzfj zzfjVar) {
                this();
            }
        }

        static {
            zzb zzbVar = new zzb();
            zzc = zzbVar;
            zzlw.k(zzb.class, zzbVar);
        }

        private zzb() {
        }

        public static zza zza() {
            return (zza) zzc.m();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(boolean z2) {
            this.zze |= 16;
            this.zzj = z2;
        }

        public static zzb zzc() {
            return zzc;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzd(boolean z2) {
            this.zze |= 64;
            this.zzl = z2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zze(boolean z2) {
            this.zze |= 2;
            this.zzg = z2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzf(boolean z2) {
            this.zze |= 4;
            this.zzh = z2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzg(boolean z2) {
            this.zze |= 8;
            this.zzi = z2;
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzfj zzfjVar = null;
            switch (zzfj.f13199a[i2 - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza(zzfjVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0000\u0000\u0001ဇ\u0000\u0002ဇ\u0001\u0003ဇ\u0002\u0004ဇ\u0003\u0005ဇ\u0004\u0006ဇ\u0005\u0007ဇ\u0006", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl"});
                case 4:
                    return zzc;
                case 5:
                    zzns<zzb> zzcVar = zzd;
                    if (zzcVar == null) {
                        synchronized (zzb.class) {
                            try {
                                zzcVar = zzd;
                                if (zzcVar == null) {
                                    zzcVar = new zzlw.zzc<>(zzc);
                                    zzd = zzcVar;
                                }
                            } finally {
                            }
                        }
                    }
                    return zzcVar;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public final boolean zzh() {
            return this.zzg;
        }

        public final boolean zzi() {
            return this.zzh;
        }

        public final boolean zzj() {
            return this.zzi;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(boolean z2) {
            this.zze |= 32;
            this.zzk = z2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzc(boolean z2) {
            this.zze |= 1;
            this.zzf = z2;
        }

        public final boolean zzd() {
            return this.zzk;
        }

        public final boolean zze() {
            return this.zzj;
        }

        public final boolean zzf() {
            return this.zzf;
        }

        public final boolean zzg() {
            return this.zzl;
        }
    }

    public static final class zzc extends zzlw<zzc, zza> implements zznl {
        private static final zzc zzc;
        private static volatile zzns<zzc> zzd;
        private int zze;
        private int zzf;
        private zzl zzg;
        private zzl zzh;
        private boolean zzi;

        public static final class zza extends zzlw.zza<zzc, zza> implements zznl {
            private zza() {
                super(zzc.zzc);
            }

            public final zza zza(int i2) {
                a();
                ((zzc) this.f13230a).zza(i2);
                return this;
            }

            /* synthetic */ zza(zzfj zzfjVar) {
                this();
            }

            public final zza zza(zzl.zza zzaVar) {
                a();
                ((zzc) this.f13230a).zza((zzl) ((zzlw) zzaVar.zzab()));
                return this;
            }

            public final zza zza(boolean z2) {
                a();
                ((zzc) this.f13230a).zza(z2);
                return this;
            }

            public final zza zza(zzl zzlVar) {
                a();
                ((zzc) this.f13230a).zzb(zzlVar);
                return this;
            }
        }

        static {
            zzc zzcVar = new zzc();
            zzc = zzcVar;
            zzlw.k(zzc.class, zzcVar);
        }

        private zzc() {
        }

        public static zza zzb() {
            return (zza) zzc.m();
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzfj zzfjVar = null;
            switch (zzfj.f13199a[i2 - 1]) {
                case 1:
                    return new zzc();
                case 2:
                    return new zza(zzfjVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001င\u0000\u0002ဉ\u0001\u0003ဉ\u0002\u0004ဇ\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi"});
                case 4:
                    return zzc;
                case 5:
                    zzns<zzc> zzcVar = zzd;
                    if (zzcVar == null) {
                        synchronized (zzc.class) {
                            try {
                                zzcVar = zzd;
                                if (zzcVar == null) {
                                    zzcVar = new zzlw.zzc<>(zzc);
                                    zzd = zzcVar;
                                }
                            } finally {
                            }
                        }
                    }
                    return zzcVar;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public final int zza() {
            return this.zzf;
        }

        public final zzl zzd() {
            zzl zzlVar = this.zzg;
            return zzlVar == null ? zzl.zzg() : zzlVar;
        }

        public final zzl zze() {
            zzl zzlVar = this.zzh;
            return zzlVar == null ? zzl.zzg() : zzlVar;
        }

        public final boolean zzf() {
            return this.zzi;
        }

        public final boolean zzg() {
            return (this.zze & 1) != 0;
        }

        public final boolean zzh() {
            return (this.zze & 8) != 0;
        }

        public final boolean zzi() {
            return (this.zze & 4) != 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(int i2) {
            this.zze |= 1;
            this.zzf = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(zzl zzlVar) {
            zzlVar.getClass();
            this.zzh = zzlVar;
            this.zze |= 4;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzl zzlVar) {
            zzlVar.getClass();
            this.zzg = zzlVar;
            this.zze |= 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(boolean z2) {
            this.zze |= 8;
            this.zzi = z2;
        }
    }

    public static final class zzd extends zzlw<zzd, zza> implements zznl {
        private static final zzd zzc;
        private static volatile zzns<zzd> zzd;
        private int zze;
        private int zzf;
        private long zzg;

        public static final class zza extends zzlw.zza<zzd, zza> implements zznl {
            private zza() {
                super(zzd.zzc);
            }

            public final zza zza(long j2) {
                a();
                ((zzd) this.f13230a).zza(j2);
                return this;
            }

            /* synthetic */ zza(zzfj zzfjVar) {
                this();
            }

            public final zza zza(int i2) {
                a();
                ((zzd) this.f13230a).zza(i2);
                return this;
            }
        }

        static {
            zzd zzdVar = new zzd();
            zzc = zzdVar;
            zzlw.k(zzd.class, zzdVar);
        }

        private zzd() {
        }

        public static zza zzc() {
            return (zza) zzc.m();
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzfj zzfjVar = null;
            switch (zzfj.f13199a[i2 - 1]) {
                case 1:
                    return new zzd();
                case 2:
                    return new zza(zzfjVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001င\u0000\u0002ဂ\u0001", new Object[]{"zze", "zzf", "zzg"});
                case 4:
                    return zzc;
                case 5:
                    zzns<zzd> zzcVar = zzd;
                    if (zzcVar == null) {
                        synchronized (zzd.class) {
                            try {
                                zzcVar = zzd;
                                if (zzcVar == null) {
                                    zzcVar = new zzlw.zzc<>(zzc);
                                    zzd = zzcVar;
                                }
                            } finally {
                            }
                        }
                    }
                    return zzcVar;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public final int zza() {
            return this.zzf;
        }

        public final long zzb() {
            return this.zzg;
        }

        public final boolean zze() {
            return (this.zze & 2) != 0;
        }

        public final boolean zzf() {
            return (this.zze & 1) != 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(long j2) {
            this.zze |= 2;
            this.zzg = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(int i2) {
            this.zze |= 1;
            this.zzf = i2;
        }
    }

    public static final class zze extends zzlw<zze, zza> implements zznl {
        private static final zze zzc;
        private static volatile zzns<zze> zzd;
        private int zze;
        private zzmf<zzg> zzf = zzlw.q();
        private String zzg = "";
        private long zzh;
        private long zzi;
        private int zzj;

        public static final class zza extends zzlw.zza<zze, zza> implements zznl {
            private zza() {
                super(zze.zzc);
            }

            public final int zza() {
                return ((zze) this.f13230a).zzb();
            }

            public final long zzb() {
                return ((zze) this.f13230a).zzc();
            }

            public final long zzc() {
                return ((zze) this.f13230a).zzd();
            }

            public final zza zzd() {
                a();
                ((zze) this.f13230a).zzl();
                return this;
            }

            public final String zze() {
                return ((zze) this.f13230a).zzg();
            }

            public final List<zzg> zzf() {
                return Collections.unmodifiableList(((zze) this.f13230a).zzh());
            }

            public final boolean zzg() {
                return ((zze) this.f13230a).zzk();
            }

            /* synthetic */ zza(zzfj zzfjVar) {
                this();
            }

            public final zza zza(Iterable<? extends zzg> iterable) {
                a();
                ((zze) this.f13230a).zza(iterable);
                return this;
            }

            public final zza zzb(long j2) {
                a();
                ((zze) this.f13230a).zzb(j2);
                return this;
            }

            public final zza zza(zzg.zza zzaVar) {
                a();
                ((zze) this.f13230a).zza((zzg) ((zzlw) zzaVar.zzab()));
                return this;
            }

            public final zzg zzb(int i2) {
                return ((zze) this.f13230a).zza(i2);
            }

            public final zza zza(zzg zzgVar) {
                a();
                ((zze) this.f13230a).zza(zzgVar);
                return this;
            }

            public final zza zza(int i2) {
                a();
                ((zze) this.f13230a).zzb(i2);
                return this;
            }

            public final zza zza(String str) {
                a();
                ((zze) this.f13230a).zza(str);
                return this;
            }

            public final zza zza(int i2, zzg.zza zzaVar) {
                a();
                ((zze) this.f13230a).zza(i2, (zzg) ((zzlw) zzaVar.zzab()));
                return this;
            }

            public final zza zza(int i2, zzg zzgVar) {
                a();
                ((zze) this.f13230a).zza(i2, zzgVar);
                return this;
            }

            public final zza zza(long j2) {
                a();
                ((zze) this.f13230a).zza(j2);
                return this;
            }
        }

        static {
            zze zzeVar = new zze();
            zzc = zzeVar;
            zzlw.k(zze.class, zzeVar);
        }

        private zze() {
        }

        public static zza zze() {
            return (zza) zzc.m();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzl() {
            this.zzf = zzlw.q();
        }

        private final void zzm() {
            zzmf<zzg> zzmfVar = this.zzf;
            if (zzmfVar.zzc()) {
                return;
            }
            this.zzf = zzlw.g(zzmfVar);
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzfj zzfjVar = null;
            switch (zzfj.f13199a[i2 - 1]) {
                case 1:
                    return new zze();
                case 2:
                    return new zza(zzfjVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0001\u0000\u0001\u001b\u0002ဈ\u0000\u0003ဂ\u0001\u0004ဂ\u0002\u0005င\u0003", new Object[]{"zze", "zzf", zzg.class, "zzg", "zzh", "zzi", "zzj"});
                case 4:
                    return zzc;
                case 5:
                    zzns<zze> zzcVar = zzd;
                    if (zzcVar == null) {
                        synchronized (zze.class) {
                            try {
                                zzcVar = zzd;
                                if (zzcVar == null) {
                                    zzcVar = new zzlw.zzc<>(zzc);
                                    zzd = zzcVar;
                                }
                            } finally {
                            }
                        }
                    }
                    return zzcVar;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public final int zza() {
            return this.zzj;
        }

        public final int zzb() {
            return this.zzf.size();
        }

        public final long zzc() {
            return this.zzi;
        }

        public final long zzd() {
            return this.zzh;
        }

        public final String zzg() {
            return this.zzg;
        }

        public final List<zzg> zzh() {
            return this.zzf;
        }

        public final boolean zzi() {
            return (this.zze & 8) != 0;
        }

        public final boolean zzj() {
            return (this.zze & 4) != 0;
        }

        public final boolean zzk() {
            return (this.zze & 2) != 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(int i2) {
            zzm();
            this.zzf.remove(i2);
        }

        public final zzg zza(int i2) {
            return this.zzf.get(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(Iterable<? extends zzg> iterable) {
            zzm();
            zzkc.b(iterable, this.zzf);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(long j2) {
            this.zze |= 2;
            this.zzh = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzg zzgVar) {
            zzgVar.getClass();
            zzm();
            this.zzf.add(zzgVar);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zze |= 1;
            this.zzg = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(int i2, zzg zzgVar) {
            zzgVar.getClass();
            zzm();
            this.zzf.set(i2, zzgVar);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(long j2) {
            this.zze |= 4;
            this.zzi = j2;
        }
    }

    public static final class zzf extends zzlw<zzf, zza> implements zznl {
        private static final zzf zzc;
        private static volatile zzns<zzf> zzd;
        private int zze;
        private String zzf = "";
        private long zzg;

        public static final class zza extends zzlw.zza<zzf, zza> implements zznl {
            private zza() {
                super(zzf.zzc);
            }

            public final zza zza(long j2) {
                a();
                ((zzf) this.f13230a).zza(j2);
                return this;
            }

            /* synthetic */ zza(zzfj zzfjVar) {
                this();
            }

            public final zza zza(String str) {
                a();
                ((zzf) this.f13230a).zza(str);
                return this;
            }
        }

        static {
            zzf zzfVar = new zzf();
            zzc = zzfVar;
            zzlw.k(zzf.class, zzfVar);
        }

        private zzf() {
        }

        public static zza zza() {
            return (zza) zzc.m();
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzfj zzfjVar = null;
            switch (zzfj.f13199a[i2 - 1]) {
                case 1:
                    return new zzf();
                case 2:
                    return new zza(zzfjVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဂ\u0001", new Object[]{"zze", "zzf", "zzg"});
                case 4:
                    return zzc;
                case 5:
                    zzns<zzf> zzcVar = zzd;
                    if (zzcVar == null) {
                        synchronized (zzf.class) {
                            try {
                                zzcVar = zzd;
                                if (zzcVar == null) {
                                    zzcVar = new zzlw.zzc<>(zzc);
                                    zzd = zzcVar;
                                }
                            } finally {
                            }
                        }
                    }
                    return zzcVar;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(long j2) {
            this.zze |= 2;
            this.zzg = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zze |= 1;
            this.zzf = str;
        }
    }

    public static final class zzg extends zzlw<zzg, zza> implements zznl {
        private static final zzg zzc;
        private static volatile zzns<zzg> zzd;
        private int zze;
        private long zzh;
        private float zzi;
        private double zzj;
        private String zzf = "";
        private String zzg = "";
        private zzmf<zzg> zzk = zzlw.q();

        public static final class zza extends zzlw.zza<zzg, zza> implements zznl {
            private zza() {
                super(zzg.zzc);
            }

            public final int zza() {
                return ((zzg) this.f13230a).zzc();
            }

            public final zza zzb() {
                a();
                ((zzg) this.f13230a).zzo();
                return this;
            }

            public final zza zzc() {
                a();
                ((zzg) this.f13230a).zzp();
                return this;
            }

            public final zza zzd() {
                a();
                ((zzg) this.f13230a).zzq();
                return this;
            }

            public final zza zze() {
                a();
                ((zzg) this.f13230a).zzr();
                return this;
            }

            /* synthetic */ zza(zzfj zzfjVar) {
                this();
            }

            public final zza zza(Iterable<? extends zzg> iterable) {
                a();
                ((zzg) this.f13230a).zza(iterable);
                return this;
            }

            public final zza zzb(String str) {
                a();
                ((zzg) this.f13230a).zzb(str);
                return this;
            }

            public final zza zza(zza zzaVar) {
                a();
                ((zzg) this.f13230a).zze((zzg) ((zzlw) zzaVar.zzab()));
                return this;
            }

            public final zza zza(double d2) {
                a();
                ((zzg) this.f13230a).zza(d2);
                return this;
            }

            public final zza zza(long j2) {
                a();
                ((zzg) this.f13230a).zza(j2);
                return this;
            }

            public final zza zza(String str) {
                a();
                ((zzg) this.f13230a).zza(str);
                return this;
            }
        }

        static {
            zzg zzgVar = new zzg();
            zzc = zzgVar;
            zzlw.k(zzg.class, zzgVar);
        }

        private zzg() {
        }

        public static zza zze() {
            return (zza) zzc.m();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzo() {
            this.zze &= -17;
            this.zzj = 0.0d;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzp() {
            this.zze &= -5;
            this.zzh = 0L;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzq() {
            this.zzk = zzlw.q();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzr() {
            this.zze &= -3;
            this.zzg = zzc.zzg;
        }

        private final void zzs() {
            zzmf<zzg> zzmfVar = this.zzk;
            if (zzmfVar.zzc()) {
                return;
            }
            this.zzk = zzlw.g(zzmfVar);
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzfj zzfjVar = null;
            switch (zzfj.f13199a[i2 - 1]) {
                case 1:
                    return new zzg();
                case 2:
                    return new zza(zzfjVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0001\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဂ\u0002\u0004ခ\u0003\u0005က\u0004\u0006\u001b", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", zzg.class});
                case 4:
                    return zzc;
                case 5:
                    zzns<zzg> zzcVar = zzd;
                    if (zzcVar == null) {
                        synchronized (zzg.class) {
                            try {
                                zzcVar = zzd;
                                if (zzcVar == null) {
                                    zzcVar = new zzlw.zzc<>(zzc);
                                    zzd = zzcVar;
                                }
                            } finally {
                            }
                        }
                    }
                    return zzcVar;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public final double zza() {
            return this.zzj;
        }

        public final float zzb() {
            return this.zzi;
        }

        public final int zzc() {
            return this.zzk.size();
        }

        public final long zzd() {
            return this.zzh;
        }

        public final String zzg() {
            return this.zzf;
        }

        public final String zzh() {
            return this.zzg;
        }

        public final List<zzg> zzi() {
            return this.zzk;
        }

        public final boolean zzj() {
            return (this.zze & 16) != 0;
        }

        public final boolean zzk() {
            return (this.zze & 8) != 0;
        }

        public final boolean zzl() {
            return (this.zze & 4) != 0;
        }

        public final boolean zzm() {
            return (this.zze & 1) != 0;
        }

        public final boolean zzn() {
            return (this.zze & 2) != 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(String str) {
            str.getClass();
            this.zze |= 2;
            this.zzg = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zze(zzg zzgVar) {
            zzgVar.getClass();
            zzs();
            this.zzk.add(zzgVar);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(Iterable<? extends zzg> iterable) {
            zzs();
            zzkc.b(iterable, this.zzk);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(double d2) {
            this.zze |= 16;
            this.zzj = d2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(long j2) {
            this.zze |= 4;
            this.zzh = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zze |= 1;
            this.zzf = str;
        }
    }

    public static final class zzh extends zzlw<zzh, zza> implements zznl {
        private static final zzh zzc;
        private static volatile zzns<zzh> zzd;
        private int zze;
        private String zzf = "";
        private String zzg = "";
        private zza zzh;

        public static final class zza extends zzlw.zza<zzh, zza> implements zznl {
            private zza() {
                super(zzh.zzc);
            }

            /* synthetic */ zza(zzfj zzfjVar) {
                this();
            }
        }

        static {
            zzh zzhVar = new zzh();
            zzc = zzhVar;
            zzlw.k(zzh.class, zzhVar);
        }

        private zzh() {
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzfj zzfjVar = null;
            switch (zzfj.f13199a[i2 - 1]) {
                case 1:
                    return new zzh();
                case 2:
                    return new zza(zzfjVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဉ\u0002", new Object[]{"zze", "zzf", "zzg", "zzh"});
                case 4:
                    return zzc;
                case 5:
                    zzns<zzh> zzcVar = zzd;
                    if (zzcVar == null) {
                        synchronized (zzh.class) {
                            try {
                                zzcVar = zzd;
                                if (zzcVar == null) {
                                    zzcVar = new zzlw.zzc<>(zzc);
                                    zzd = zzcVar;
                                }
                            } finally {
                            }
                        }
                    }
                    return zzcVar;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }

    public static final class zzi extends zzlw<zzi, zza> implements zznl {
        private static final zzi zzc;
        private static volatile zzns<zzi> zzd;
        private int zze;
        private zzmf<zzj> zzf = zzlw.q();
        private String zzg = "";

        public static final class zza extends zzlw.zza<zzi, zza> implements zznl {
            private zza() {
                super(zzi.zzc);
            }

            public final int zza() {
                return ((zzi) this.f13230a).zza();
            }

            /* synthetic */ zza(zzfj zzfjVar) {
                this();
            }

            public final zza zza(zzj.zza zzaVar) {
                a();
                ((zzi) this.f13230a).zza((zzj) ((zzlw) zzaVar.zzab()));
                return this;
            }

            public final zzj zza(int i2) {
                return ((zzi) this.f13230a).zza(0);
            }
        }

        static {
            zzi zziVar = new zzi();
            zzc = zziVar;
            zzlw.k(zzi.class, zziVar);
        }

        private zzi() {
        }

        public static zza zzb() {
            return (zza) zzc.m();
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzfj zzfjVar = null;
            switch (zzfj.f13199a[i2 - 1]) {
                case 1:
                    return new zzi();
                case 2:
                    return new zza(zzfjVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0002\u0000\u0001\u0001\u0007\u0002\u0000\u0001\u0000\u0001\u001b\u0007ဈ\u0000", new Object[]{"zze", "zzf", zzj.class, "zzg"});
                case 4:
                    return zzc;
                case 5:
                    zzns<zzi> zzcVar = zzd;
                    if (zzcVar == null) {
                        synchronized (zzi.class) {
                            try {
                                zzcVar = zzd;
                                if (zzcVar == null) {
                                    zzcVar = new zzlw.zzc<>(zzc);
                                    zzd = zzcVar;
                                }
                            } finally {
                            }
                        }
                    }
                    return zzcVar;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public final int zza() {
            return this.zzf.size();
        }

        public final List<zzj> zzd() {
            return this.zzf;
        }

        public final zzj zza(int i2) {
            return this.zzf.get(0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzj zzjVar) {
            zzjVar.getClass();
            zzmf<zzj> zzmfVar = this.zzf;
            if (!zzmfVar.zzc()) {
                this.zzf = zzlw.g(zzmfVar);
            }
            this.zzf.add(zzjVar);
        }
    }

    public static final class zzj extends zzlw<zzj, zza> implements zznl {
        private static final zzj zzc;
        private static volatile zzns<zzj> zzd;
        private long zzab;
        private int zzac;
        private boolean zzaf;
        private int zzai;
        private int zzaj;
        private int zzak;
        private long zzam;
        private long zzan;
        private int zzaq;
        private zzk zzas;
        private long zzau;
        private long zzav;
        private int zzay;
        private boolean zzaz;
        private boolean zzbb;
        private zzh zzbc;
        private long zzbg;
        private boolean zzbh;
        private boolean zzbj;
        private int zzbl;
        private zzb zzbn;
        private int zze;
        private int zzf;
        private int zzg;
        private long zzj;
        private long zzk;
        private long zzl;
        private long zzm;
        private long zzn;
        private int zzs;
        private long zzw;
        private long zzx;
        private boolean zzz;
        private zzmf<zze> zzh = zzlw.q();
        private zzmf<zzn> zzi = zzlw.q();
        private String zzo = "";
        private String zzp = "";
        private String zzq = "";
        private String zzr = "";
        private String zzt = "";
        private String zzu = "";
        private String zzv = "";
        private String zzy = "";
        private String zzaa = "";
        private String zzad = "";
        private String zzae = "";
        private zzmf<zzc> zzag = zzlw.q();
        private String zzah = "";
        private String zzal = "";
        private String zzao = "";
        private String zzap = "";
        private String zzar = "";
        private zzmd zzat = zzlw.o();
        private String zzaw = "";
        private String zzax = "";
        private String zzba = "";
        private String zzbd = "";
        private zzmf<String> zzbe = zzlw.q();
        private String zzbf = "";
        private String zzbi = "";
        private String zzbk = "";
        private String zzbm = "";

        public static final class zza extends zzlw.zza<zzj, zza> implements zznl {
            private zza() {
                super(zzj.zzc);
            }

            public final int zza() {
                return ((zzj) this.f13230a).zzd();
            }

            public final int zzb() {
                return ((zzj) this.f13230a).zzh();
            }

            public final long zzc() {
                return ((zzj) this.f13230a).zzl();
            }

            public final long zzd() {
                return ((zzj) this.f13230a).zzp();
            }

            public final zza zze(Iterable<? extends zzn> iterable) {
                a();
                ((zzj) this.f13230a).zze(iterable);
                return this;
            }

            public final zza zzf() {
                a();
                ((zzj) this.f13230a).zzcj();
                return this;
            }

            public final zza zzg() {
                a();
                ((zzj) this.f13230a).zzck();
                return this;
            }

            public final zza zzh() {
                a();
                ((zzj) this.f13230a).zzcl();
                return this;
            }

            public final zza zzi() {
                a();
                ((zzj) this.f13230a).zzcm();
                return this;
            }

            public final zza zzj() {
                a();
                ((zzj) this.f13230a).zzcn();
                return this;
            }

            public final zza zzk() {
                a();
                ((zzj) this.f13230a).zzco();
                return this;
            }

            public final zza zzl() {
                a();
                ((zzj) this.f13230a).zzcp();
                return this;
            }

            public final zza zzm() {
                a();
                ((zzj) this.f13230a).zzcq();
                return this;
            }

            public final zza zzn() {
                a();
                ((zzj) this.f13230a).zzcr();
                return this;
            }

            public final zza zzo() {
                a();
                ((zzj) this.f13230a).zzcs();
                return this;
            }

            public final zza zzp() {
                a();
                ((zzj) this.f13230a).zzct();
                return this;
            }

            public final zza zzq() {
                a();
                ((zzj) this.f13230a).zzcu();
                return this;
            }

            public final zza zzr(String str) {
                a();
                ((zzj) this.f13230a).zzr(str);
                return this;
            }

            public final zza zzs(String str) {
                a();
                ((zzj) this.f13230a).zzs(str);
                return this;
            }

            public final String zzt() {
                return ((zzj) this.f13230a).zzah();
            }

            public final String zzu() {
                return ((zzj) this.f13230a).zzaj();
            }

            public final String zzv() {
                return ((zzj) this.f13230a).zzal();
            }

            public final List<zze> zzw() {
                return Collections.unmodifiableList(((zzj) this.f13230a).zzap());
            }

            public final List<zzn> zzx() {
                return Collections.unmodifiableList(((zzj) this.f13230a).zzaq());
            }

            /* synthetic */ zza(zzfj zzfjVar) {
                this();
            }

            public final zze zza(int i2) {
                return ((zzj) this.f13230a).zza(i2);
            }

            public final zza zzb(Iterable<? extends zze> iterable) {
                a();
                ((zzj) this.f13230a).zzb(iterable);
                return this;
            }

            public final zza zzc(Iterable<? extends Integer> iterable) {
                a();
                ((zzj) this.f13230a).zzc(iterable);
                return this;
            }

            public final zza zzd(Iterable<String> iterable) {
                a();
                ((zzj) this.f13230a).zzd(iterable);
                return this;
            }

            public final zza zza(Iterable<? extends zzc> iterable) {
                a();
                ((zzj) this.f13230a).zza(iterable);
                return this;
            }

            public final zza zze() {
                a();
                ((zzj) this.f13230a).zzci();
                return this;
            }

            public final zza zzf(int i2) {
                a();
                ((zzj) this.f13230a).zzh(i2);
                return this;
            }

            public final zza zzg(String str) {
                a();
                ((zzj) this.f13230a).zzg(str);
                return this;
            }

            public final zza zzh(String str) {
                a();
                ((zzj) this.f13230a).zzh(str);
                return this;
            }

            public final zza zzi(String str) {
                a();
                ((zzj) this.f13230a).zzi(str);
                return this;
            }

            public final zza zzj(String str) {
                a();
                ((zzj) this.f13230a).zzj((String) null);
                return this;
            }

            public final zza zzk(String str) {
                a();
                ((zzj) this.f13230a).zzk(str);
                return this;
            }

            public final zza zzl(String str) {
                a();
                ((zzj) this.f13230a).zzl(str);
                return this;
            }

            public final zza zzm(String str) {
                a();
                ((zzj) this.f13230a).zzm(str);
                return this;
            }

            public final zza zzn(String str) {
                a();
                ((zzj) this.f13230a).zzn(str);
                return this;
            }

            public final zza zzo(String str) {
                a();
                ((zzj) this.f13230a).zzo(str);
                return this;
            }

            public final zza zzp(String str) {
                a();
                ((zzj) this.f13230a).zzp(str);
                return this;
            }

            public final zza zzq(String str) {
                a();
                ((zzj) this.f13230a).zzq(str);
                return this;
            }

            public final String zzr() {
                return ((zzj) this.f13230a).zzx();
            }

            public final String zzs() {
                return ((zzj) this.f13230a).zzab();
            }

            public final zza zzb(int i2) {
                a();
                ((zzj) this.f13230a).zzd(i2);
                return this;
            }

            public final zza zzc(int i2) {
                a();
                ((zzj) this.f13230a).zze(i2);
                return this;
            }

            public final zza zzd(int i2) {
                a();
                ((zzj) this.f13230a).zzf(i2);
                return this;
            }

            public final zza zza(zze.zza zzaVar) {
                a();
                ((zzj) this.f13230a).zza((zze) ((zzlw) zzaVar.zzab()));
                return this;
            }

            public final zza zze(String str) {
                a();
                ((zzj) this.f13230a).zze(str);
                return this;
            }

            public final zza zzf(String str) {
                a();
                ((zzj) this.f13230a).zzf(str);
                return this;
            }

            public final zza zzg(long j2) {
                a();
                ((zzj) this.f13230a).zzg(j2);
                return this;
            }

            public final zza zzh(long j2) {
                a();
                ((zzj) this.f13230a).zzh(j2);
                return this;
            }

            public final zza zzi(long j2) {
                a();
                ((zzj) this.f13230a).zzi(j2);
                return this;
            }

            public final zza zzj(long j2) {
                a();
                ((zzj) this.f13230a).zzj(j2);
                return this;
            }

            public final zza zzk(long j2) {
                a();
                ((zzj) this.f13230a).zzk(j2);
                return this;
            }

            public final zza zzl(long j2) {
                a();
                ((zzj) this.f13230a).zzl(j2);
                return this;
            }

            public final zza zzb(String str) {
                a();
                ((zzj) this.f13230a).zzb(str);
                return this;
            }

            public final zza zzc(String str) {
                a();
                ((zzj) this.f13230a).zzc(str);
                return this;
            }

            public final zza zzd(String str) {
                a();
                ((zzj) this.f13230a).zzd(str);
                return this;
            }

            public final zza zza(zzn.zza zzaVar) {
                a();
                ((zzj) this.f13230a).zza((zzn) ((zzlw) zzaVar.zzab()));
                return this;
            }

            public final zza zze(int i2) {
                a();
                ((zzj) this.f13230a).zzg(i2);
                return this;
            }

            public final zza zzf(long j2) {
                a();
                ((zzj) this.f13230a).zzf(j2);
                return this;
            }

            public final zza zzg(int i2) {
                a();
                ((zzj) this.f13230a).zzi(1);
                return this;
            }

            public final zza zzh(int i2) {
                a();
                ((zzj) this.f13230a).zzj(i2);
                return this;
            }

            public final zza zzi(int i2) {
                a();
                ((zzj) this.f13230a).zzk(i2);
                return this;
            }

            public final zzn zzj(int i2) {
                return ((zzj) this.f13230a).zzb(i2);
            }

            public final zza zzb(long j2) {
                a();
                ((zzj) this.f13230a).zzb(j2);
                return this;
            }

            public final zza zzc(long j2) {
                a();
                ((zzj) this.f13230a).zzc(j2);
                return this;
            }

            public final zza zzd(long j2) {
                a();
                ((zzj) this.f13230a).zzd(j2);
                return this;
            }

            public final zza zza(zzn zznVar) {
                a();
                ((zzj) this.f13230a).zza(zznVar);
                return this;
            }

            public final zza zze(long j2) {
                a();
                ((zzj) this.f13230a).zze(j2);
                return this;
            }

            public final zza zzb(boolean z2) {
                a();
                ((zzj) this.f13230a).zzb(z2);
                return this;
            }

            public final zza zzc(boolean z2) {
                a();
                ((zzj) this.f13230a).zzc(z2);
                return this;
            }

            public final zza zzd(boolean z2) {
                a();
                ((zzj) this.f13230a).zzd(z2);
                return this;
            }

            public final zza zza(String str) {
                a();
                ((zzj) this.f13230a).zza(str);
                return this;
            }

            public final zza zza(zzb zzbVar) {
                a();
                ((zzj) this.f13230a).zza(zzbVar);
                return this;
            }

            public final zza zza(long j2) {
                a();
                ((zzj) this.f13230a).zza(j2);
                return this;
            }

            public final zza zza(boolean z2) {
                a();
                ((zzj) this.f13230a).zza(z2);
                return this;
            }

            public final zza zza(int i2, zze.zza zzaVar) {
                a();
                ((zzj) this.f13230a).zza(i2, (zze) ((zzlw) zzaVar.zzab()));
                return this;
            }

            public final zza zza(int i2, zze zzeVar) {
                a();
                ((zzj) this.f13230a).zza(i2, zzeVar);
                return this;
            }

            public final zza zza(zzk.zza zzaVar) {
                a();
                ((zzj) this.f13230a).zza((zzk) ((zzlw) zzaVar.zzab()));
                return this;
            }

            public final zza zza(int i2, zzn zznVar) {
                a();
                ((zzj) this.f13230a).zza(i2, zznVar);
                return this;
            }
        }

        static {
            zzj zzjVar = new zzj();
            zzc = zzjVar;
            zzlw.k(zzj.class, zzjVar);
        }

        private zzj() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzci() {
            this.zze &= -262145;
            this.zzaa = zzc.zzaa;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzcj() {
            this.zzag = zzlw.q();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzck() {
            this.zze &= -257;
            this.zzq = zzc.zzq;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzcl() {
            this.zze &= Integer.MAX_VALUE;
            this.zzao = zzc.zzao;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzcm() {
            this.zzh = zzlw.q();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzcn() {
            this.zze &= -2097153;
            this.zzad = zzc.zzad;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzco() {
            this.zze &= -131073;
            this.zzz = false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzcp() {
            this.zze &= -129;
            this.zzp = zzc.zzp;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzcq() {
            this.zze &= -33;
            this.zzn = 0L;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzcr() {
            this.zze &= -17;
            this.zzm = 0L;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzcs() {
            this.zze &= -65537;
            this.zzy = zzc.zzy;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzct() {
            this.zzf &= -8193;
            this.zzbd = zzc.zzbd;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzcu() {
            this.zze &= -268435457;
            this.zzal = zzc.zzal;
        }

        private final void zzcv() {
            zzmf<zze> zzmfVar = this.zzh;
            if (zzmfVar.zzc()) {
                return;
            }
            this.zzh = zzlw.g(zzmfVar);
        }

        private final void zzcw() {
            zzmf<zzn> zzmfVar = this.zzi;
            if (zzmfVar.zzc()) {
                return;
            }
            this.zzi = zzlw.g(zzmfVar);
        }

        public static zza zzu() {
            return (zza) zzc.m();
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzfj zzfjVar = null;
            switch (zzfj.f13199a[i2 - 1]) {
                case 1:
                    return new zzj();
                case 2:
                    return new zza(zzfjVar);
                case 3:
                    return zzlw.i(zzc, "\u0001<\u0000\u0002\u0001L<\u0000\u0005\u0000\u0001င\u0000\u0002\u001b\u0003\u001b\u0004ဂ\u0001\u0005ဂ\u0002\u0006ဂ\u0003\u0007ဂ\u0005\bဈ\u0006\tဈ\u0007\nဈ\b\u000bဈ\t\fင\n\rဈ\u000b\u000eဈ\f\u0010ဈ\r\u0011ဂ\u000e\u0012ဂ\u000f\u0013ဈ\u0010\u0014ဇ\u0011\u0015ဈ\u0012\u0016ဂ\u0013\u0017င\u0014\u0018ဈ\u0015\u0019ဈ\u0016\u001aဂ\u0004\u001cဇ\u0017\u001d\u001b\u001eဈ\u0018\u001fင\u0019 င\u001a!င\u001b\"ဈ\u001c#ဂ\u001d$ဂ\u001e%ဈ\u001f&ဈ 'င!)ဈ\",ဉ#-\u001d.ဂ$/ဂ%2ဈ&4ဈ'5᠌(7ဇ)9ဈ*:ဇ+;ဉ,?ဈ-@\u001aAဈ.Cဂ/Dဇ0Gဈ1Hဇ2Iဈ3Jင4Kဈ5Lဉ6", new Object[]{"zze", "zzf", "zzg", "zzh", zze.class, "zzi", zzn.class, "zzj", "zzk", "zzl", "zzn", "zzo", "zzp", "zzq", "zzr", "zzs", "zzt", "zzu", "zzv", "zzw", "zzx", "zzy", "zzz", "zzaa", "zzab", "zzac", "zzad", "zzae", "zzm", "zzaf", "zzag", zzc.class, "zzah", "zzai", "zzaj", "zzak", "zzal", "zzam", "zzan", "zzao", "zzap", "zzaq", "zzar", "zzas", "zzat", "zzau", "zzav", "zzaw", "zzax", "zzay", zzfi.zzb(), "zzaz", "zzba", "zzbb", "zzbc", "zzbd", "zzbe", "zzbf", "zzbg", "zzbh", "zzbi", "zzbj", "zzbk", "zzbl", "zzbm", "zzbn"});
                case 4:
                    return zzc;
                case 5:
                    zzns<zzj> zzcVar = zzd;
                    if (zzcVar == null) {
                        synchronized (zzj.class) {
                            try {
                                zzcVar = zzd;
                                if (zzcVar == null) {
                                    zzcVar = new zzlw.zzc<>(zzc);
                                    zzd = zzcVar;
                                }
                            } finally {
                            }
                        }
                    }
                    return zzcVar;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public final int zza() {
            return this.zzbl;
        }

        public final String zzaa() {
            return this.zzv;
        }

        public final String zzab() {
            return this.zzbi;
        }

        public final String zzac() {
            return this.zzax;
        }

        public final String zzad() {
            return this.zzbk;
        }

        public final String zzae() {
            return this.zzq;
        }

        public final String zzaf() {
            return this.zzao;
        }

        public final String zzag() {
            return this.zzah;
        }

        public final String zzah() {
            return this.zzae;
        }

        public final String zzai() {
            return this.zzad;
        }

        public final String zzaj() {
            return this.zzp;
        }

        public final String zzak() {
            return this.zzo;
        }

        public final String zzal() {
            return this.zzy;
        }

        public final String zzam() {
            return this.zzbd;
        }

        public final String zzan() {
            return this.zzr;
        }

        public final List<zzc> zzao() {
            return this.zzag;
        }

        public final List<zze> zzap() {
            return this.zzh;
        }

        public final List<zzn> zzaq() {
            return this.zzi;
        }

        public final boolean zzar() {
            return this.zzbh;
        }

        public final boolean zzas() {
            return this.zzbj;
        }

        public final boolean zzat() {
            return this.zzz;
        }

        public final boolean zzau() {
            return this.zzaf;
        }

        public final boolean zzav() {
            return (this.zze & DiskLruHelper.DEFAULT_MAXSIZE) != 0;
        }

        public final boolean zzaw() {
            return (this.zzf & 4194304) != 0;
        }

        public final boolean zzax() {
            return (this.zze & 1048576) != 0;
        }

        public final boolean zzay() {
            return (this.zze & C.BUFFER_FLAG_LAST_SAMPLE) != 0;
        }

        public final boolean zzaz() {
            return (this.zzf & 131072) != 0;
        }

        public final int zzb() {
            return this.zzai;
        }

        public final boolean zzba() {
            return (this.zzf & 128) != 0;
        }

        public final boolean zzbb() {
            return (this.zzf & 524288) != 0;
        }

        public final boolean zzbc() {
            return (this.zze & 524288) != 0;
        }

        public final boolean zzbd() {
            return (this.zzf & 16) != 0;
        }

        public final boolean zzbe() {
            return (this.zze & 8) != 0;
        }

        public final boolean zzbf() {
            return (this.zze & 16384) != 0;
        }

        public final boolean zzbg() {
            return (this.zzf & 262144) != 0;
        }

        public final boolean zzbh() {
            return (this.zze & 131072) != 0;
        }

        public final boolean zzbi() {
            return (this.zze & 32) != 0;
        }

        public final boolean zzbj() {
            return (this.zze & 16) != 0;
        }

        public final boolean zzbk() {
            return (this.zze & 1) != 0;
        }

        public final boolean zzbl() {
            return (this.zzf & 2) != 0;
        }

        public final boolean zzbm() {
            return (this.zze & 8388608) != 0;
        }

        public final boolean zzbn() {
            return (this.zzf & 8192) != 0;
        }

        public final boolean zzbo() {
            return (this.zze & 4) != 0;
        }

        public final boolean zzbp() {
            return (this.zzf & 32768) != 0;
        }

        public final boolean zzbq() {
            return (this.zze & 1024) != 0;
        }

        public final boolean zzbr() {
            return (this.zze & 2) != 0;
        }

        public final boolean zzbs() {
            return (this.zze & 32768) != 0;
        }

        public final int zzc() {
            return this.zzac;
        }

        public final int zzd() {
            return this.zzh.size();
        }

        public final int zze() {
            return this.zzg;
        }

        public final int zzf() {
            return this.zzaq;
        }

        public final int zzg() {
            return this.zzs;
        }

        public final int zzh() {
            return this.zzi.size();
        }

        public final long zzi() {
            return this.zzam;
        }

        public final long zzj() {
            return this.zzab;
        }

        public final long zzk() {
            return this.zzau;
        }

        public final long zzl() {
            return this.zzl;
        }

        public final long zzm() {
            return this.zzw;
        }

        public final long zzn() {
            return this.zzn;
        }

        public final long zzo() {
            return this.zzm;
        }

        public final long zzp() {
            return this.zzk;
        }

        public final long zzq() {
            return this.zzbg;
        }

        public final long zzr() {
            return this.zzj;
        }

        public final long zzs() {
            return this.zzx;
        }

        public final zzb zzt() {
            zzb zzbVar = this.zzbn;
            return zzbVar == null ? zzb.zzc() : zzbVar;
        }

        public final String zzw() {
            return this.zzar;
        }

        public final String zzx() {
            return this.zzu;
        }

        public final String zzy() {
            return this.zzaa;
        }

        public final String zzz() {
            return this.zzt;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzc(Iterable<? extends Integer> iterable) {
            zzmd zzmdVar = this.zzat;
            if (!zzmdVar.zzc()) {
                int size = zzmdVar.size();
                this.zzat = zzmdVar.zza(size == 0 ? 10 : size << 1);
            }
            zzkc.b(iterable, this.zzat);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzd(Iterable<String> iterable) {
            zzmf<String> zzmfVar = this.zzbe;
            if (!zzmfVar.zzc()) {
                this.zzbe = zzlw.g(zzmfVar);
            }
            zzkc.b(iterable, this.zzbe);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zze(Iterable<? extends zzn> iterable) {
            zzcw();
            zzkc.b(iterable, this.zzi);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzf(int i2) {
            this.zzf |= 1048576;
            this.zzbl = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzg(int i2) {
            this.zze |= DiskLruHelper.DEFAULT_MAXSIZE;
            this.zzai = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzh(int i2) {
            this.zze |= 1048576;
            this.zzac = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzi(String str) {
            str.getClass();
            this.zze |= 256;
            this.zzq = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzj(String str) {
            str.getClass();
            this.zze |= Integer.MIN_VALUE;
            this.zzao = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzk(String str) {
            str.getClass();
            this.zzf |= 16384;
            this.zzbf = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzl(String str) {
            str.getClass();
            this.zze |= 16777216;
            this.zzah = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzm(String str) {
            str.getClass();
            this.zze |= 4194304;
            this.zzae = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzn(String str) {
            str.getClass();
            this.zze |= 2097152;
            this.zzad = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzo(String str) {
            str.getClass();
            this.zze |= 128;
            this.zzp = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzp(String str) {
            str.getClass();
            this.zze |= 64;
            this.zzo = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzq(String str) {
            str.getClass();
            this.zze |= 65536;
            this.zzy = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzr(String str) {
            str.getClass();
            this.zzf |= 8192;
            this.zzbd = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzs(String str) {
            str.getClass();
            this.zze |= 512;
            this.zzr = str;
        }

        public final zze zza(int i2) {
            return this.zzh.get(i2);
        }

        public final zzn zzb(int i2) {
            return this.zzi.get(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(Iterable<? extends zze> iterable) {
            zzcv();
            zzkc.b(iterable, this.zzh);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(Iterable<? extends zzc> iterable) {
            zzmf<zzc> zzmfVar = this.zzag;
            if (!zzmfVar.zzc()) {
                this.zzag = zzlw.g(zzmfVar);
            }
            zzkc.b(iterable, this.zzag);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zze(int i2) {
            zzcw();
            this.zzi.remove(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzf(String str) {
            str.getClass();
            this.zzf |= 131072;
            this.zzbi = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzg(String str) {
            str.getClass();
            this.zzf |= 128;
            this.zzax = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzh(String str) {
            str.getClass();
            this.zzf |= 524288;
            this.zzbk = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(String str) {
            str.getClass();
            this.zze |= 4096;
            this.zzu = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzi(int i2) {
            this.zze |= 1;
            this.zzg = 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzj(int i2) {
            this.zzf |= 2;
            this.zzaq = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzk(int i2) {
            this.zze |= 1024;
            this.zzs = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzl(long j2) {
            this.zze |= 32768;
            this.zzx = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzd(int i2) {
            zzcv();
            this.zzh.remove(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zze(String str) {
            str.getClass();
            this.zze |= 8192;
            this.zzv = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzf(long j2) {
            this.zze |= 16384;
            this.zzw = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzg(long j2) {
            this.zze |= 32;
            this.zzn = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzh(long j2) {
            this.zze |= 16;
            this.zzm = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzi(long j2) {
            this.zze |= 4;
            this.zzk = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzj(long j2) {
            this.zzf |= 32768;
            this.zzbg = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzk(long j2) {
            this.zze |= 2;
            this.zzj = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zze zzeVar) {
            zzeVar.getClass();
            zzcv();
            this.zzh.add(zzeVar);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(long j2) {
            this.zze |= C.BUFFER_FLAG_LAST_SAMPLE;
            this.zzam = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzc(String str) {
            str.getClass();
            this.zze |= 262144;
            this.zzaa = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzd(String str) {
            str.getClass();
            this.zze |= 2048;
            this.zzt = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zze(long j2) {
            this.zze |= 8;
            this.zzl = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(boolean z2) {
            this.zzf |= 262144;
            this.zzbj = z2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzn zznVar) {
            zznVar.getClass();
            zzcw();
            this.zzi.add(zznVar);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzc(long j2) {
            this.zze |= 524288;
            this.zzab = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzd(long j2) {
            this.zzf |= 16;
            this.zzau = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzc(boolean z2) {
            this.zze |= 131072;
            this.zzz = z2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzd(boolean z2) {
            this.zze |= 8388608;
            this.zzaf = z2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zzf |= 4;
            this.zzar = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzb zzbVar) {
            zzbVar.getClass();
            this.zzbn = zzbVar;
            this.zzf |= 4194304;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(long j2) {
            this.zzf |= 32;
            this.zzav = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(boolean z2) {
            this.zzf |= 65536;
            this.zzbh = z2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(int i2, zze zzeVar) {
            zzeVar.getClass();
            zzcv();
            this.zzh.set(i2, zzeVar);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzk zzkVar) {
            zzkVar.getClass();
            this.zzas = zzkVar;
            this.zzf |= 8;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(int i2, zzn zznVar) {
            zznVar.getClass();
            zzcw();
            this.zzi.set(i2, zznVar);
        }
    }

    public static final class zzk extends zzlw<zzk, zza> implements zznl {
        private static final zzk zzc;
        private static volatile zzns<zzk> zzd;
        private int zze;
        private int zzf = 1;
        private zzmf<zzf> zzg = zzlw.q();

        public static final class zza extends zzlw.zza<zzk, zza> implements zznl {
            private zza() {
                super(zzk.zzc);
            }

            public final zza zza(zzf.zza zzaVar) {
                a();
                ((zzk) this.f13230a).zza((zzf) ((zzlw) zzaVar.zzab()));
                return this;
            }

            /* synthetic */ zza(zzfj zzfjVar) {
                this();
            }
        }

        public enum zzb implements zzly {
            RADS(1),
            PROVISIONING(2);

            private static final zzmb<zzb> zzc = new zzfm();
            private final int zze;

            zzb(int i2) {
                this.zze = i2;
            }

            public static zzma zzb() {
                return zzfn.f13201a;
            }

            @Override // java.lang.Enum
            public final String toString() {
                return "<" + zzb.class.getName() + '@' + Integer.toHexString(System.identityHashCode(this)) + " number=" + this.zze + " name=" + name() + Typography.greater;
            }

            @Override // com.google.android.gms.internal.measurement.zzly
            public final int zza() {
                return this.zze;
            }

            public static zzb zza(int i2) {
                if (i2 == 1) {
                    return RADS;
                }
                if (i2 != 2) {
                    return null;
                }
                return PROVISIONING;
            }
        }

        static {
            zzk zzkVar = new zzk();
            zzc = zzkVar;
            zzlw.k(zzk.class, zzkVar);
        }

        private zzk() {
        }

        public static zza zza() {
            return (zza) zzc.m();
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzfj zzfjVar = null;
            switch (zzfj.f13199a[i2 - 1]) {
                case 1:
                    return new zzk();
                case 2:
                    return new zza(zzfjVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001᠌\u0000\u0002\u001b", new Object[]{"zze", "zzf", zzb.zzb(), "zzg", zzf.class});
                case 4:
                    return zzc;
                case 5:
                    zzns<zzk> zzcVar = zzd;
                    if (zzcVar == null) {
                        synchronized (zzk.class) {
                            try {
                                zzcVar = zzd;
                                if (zzcVar == null) {
                                    zzcVar = new zzlw.zzc<>(zzc);
                                    zzd = zzcVar;
                                }
                            } finally {
                            }
                        }
                    }
                    return zzcVar;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzf zzfVar) {
            zzfVar.getClass();
            zzmf<zzf> zzmfVar = this.zzg;
            if (!zzmfVar.zzc()) {
                this.zzg = zzlw.g(zzmfVar);
            }
            this.zzg.add(zzfVar);
        }
    }

    public static final class zzl extends zzlw<zzl, zza> implements zznl {
        private static final zzl zzc;
        private static volatile zzns<zzl> zzd;
        private zzmc zze = zzlw.p();
        private zzmc zzf = zzlw.p();
        private zzmf<zzd> zzg = zzlw.q();
        private zzmf<zzm> zzh = zzlw.q();

        public static final class zza extends zzlw.zza<zzl, zza> implements zznl {
            private zza() {
                super(zzl.zzc);
            }

            public final zza zza(Iterable<? extends zzd> iterable) {
                a();
                ((zzl) this.f13230a).zza(iterable);
                return this;
            }

            public final zza zzb(Iterable<? extends Long> iterable) {
                a();
                ((zzl) this.f13230a).zzb(iterable);
                return this;
            }

            public final zza zzc(Iterable<? extends zzm> iterable) {
                a();
                ((zzl) this.f13230a).zzc(iterable);
                return this;
            }

            public final zza zzd(Iterable<? extends Long> iterable) {
                a();
                ((zzl) this.f13230a).zzd(iterable);
                return this;
            }

            /* synthetic */ zza(zzfj zzfjVar) {
                this();
            }

            public final zza zza() {
                a();
                ((zzl) this.f13230a).zzl();
                return this;
            }

            public final zza zzb() {
                a();
                ((zzl) this.f13230a).zzm();
                return this;
            }

            public final zza zzc() {
                a();
                ((zzl) this.f13230a).zzn();
                return this;
            }

            public final zza zzd() {
                a();
                ((zzl) this.f13230a).zzo();
                return this;
            }
        }

        static {
            zzl zzlVar = new zzl();
            zzc = zzlVar;
            zzlw.k(zzl.class, zzlVar);
        }

        private zzl() {
        }

        public static zza zze() {
            return (zza) zzc.m();
        }

        public static zzl zzg() {
            return zzc;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzl() {
            this.zzg = zzlw.q();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzm() {
            this.zzf = zzlw.p();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzn() {
            this.zzh = zzlw.q();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzo() {
            this.zze = zzlw.p();
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzfj zzfjVar = null;
            switch (zzfj.f13199a[i2 - 1]) {
                case 1:
                    return new zzl();
                case 2:
                    return new zza(zzfjVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0004\u0000\u0001\u0015\u0002\u0015\u0003\u001b\u0004\u001b", new Object[]{"zze", "zzf", "zzg", zzd.class, "zzh", zzm.class});
                case 4:
                    return zzc;
                case 5:
                    zzns<zzl> zzcVar = zzd;
                    if (zzcVar == null) {
                        synchronized (zzl.class) {
                            try {
                                zzcVar = zzd;
                                if (zzcVar == null) {
                                    zzcVar = new zzlw.zzc<>(zzc);
                                    zzd = zzcVar;
                                }
                            } finally {
                            }
                        }
                    }
                    return zzcVar;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public final int zza() {
            return this.zzg.size();
        }

        public final int zzb() {
            return this.zzf.size();
        }

        public final int zzc() {
            return this.zzh.size();
        }

        public final int zzd() {
            return this.zze.size();
        }

        public final List<zzd> zzh() {
            return this.zzg;
        }

        public final List<Long> zzi() {
            return this.zzf;
        }

        public final List<zzm> zzj() {
            return this.zzh;
        }

        public final List<Long> zzk() {
            return this.zze;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzc(Iterable<? extends zzm> iterable) {
            zzmf<zzm> zzmfVar = this.zzh;
            if (!zzmfVar.zzc()) {
                this.zzh = zzlw.g(zzmfVar);
            }
            zzkc.b(iterable, this.zzh);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzd(Iterable<? extends Long> iterable) {
            zzmc zzmcVar = this.zze;
            if (!zzmcVar.zzc()) {
                this.zze = zzlw.f(zzmcVar);
            }
            zzkc.b(iterable, this.zze);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(Iterable<? extends zzd> iterable) {
            zzmf<zzd> zzmfVar = this.zzg;
            if (!zzmfVar.zzc()) {
                this.zzg = zzlw.g(zzmfVar);
            }
            zzkc.b(iterable, this.zzg);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(Iterable<? extends Long> iterable) {
            zzmc zzmcVar = this.zzf;
            if (!zzmcVar.zzc()) {
                this.zzf = zzlw.f(zzmcVar);
            }
            zzkc.b(iterable, this.zzf);
        }
    }

    public static final class zzm extends zzlw<zzm, zza> implements zznl {
        private static final zzm zzc;
        private static volatile zzns<zzm> zzd;
        private int zze;
        private int zzf;
        private zzmc zzg = zzlw.p();

        public static final class zza extends zzlw.zza<zzm, zza> implements zznl {
            private zza() {
                super(zzm.zzc);
            }

            public final zza zza(Iterable<? extends Long> iterable) {
                a();
                ((zzm) this.f13230a).zza(iterable);
                return this;
            }

            /* synthetic */ zza(zzfj zzfjVar) {
                this();
            }

            public final zza zza(int i2) {
                a();
                ((zzm) this.f13230a).zzb(i2);
                return this;
            }
        }

        static {
            zzm zzmVar = new zzm();
            zzc = zzmVar;
            zzlw.k(zzm.class, zzmVar);
        }

        private zzm() {
        }

        public static zza zzc() {
            return (zza) zzc.m();
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzfj zzfjVar = null;
            switch (zzfj.f13199a[i2 - 1]) {
                case 1:
                    return new zzm();
                case 2:
                    return new zza(zzfjVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001င\u0000\u0002\u0014", new Object[]{"zze", "zzf", "zzg"});
                case 4:
                    return zzc;
                case 5:
                    zzns<zzm> zzcVar = zzd;
                    if (zzcVar == null) {
                        synchronized (zzm.class) {
                            try {
                                zzcVar = zzd;
                                if (zzcVar == null) {
                                    zzcVar = new zzlw.zzc<>(zzc);
                                    zzd = zzcVar;
                                }
                            } finally {
                            }
                        }
                    }
                    return zzcVar;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public final int zza() {
            return this.zzg.size();
        }

        public final int zzb() {
            return this.zzf;
        }

        public final List<Long> zze() {
            return this.zzg;
        }

        public final boolean zzf() {
            return (this.zze & 1) != 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(int i2) {
            this.zze |= 1;
            this.zzf = i2;
        }

        public final long zza(int i2) {
            return this.zzg.zzb(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(Iterable<? extends Long> iterable) {
            zzmc zzmcVar = this.zzg;
            if (!zzmcVar.zzc()) {
                this.zzg = zzlw.f(zzmcVar);
            }
            zzkc.b(iterable, this.zzg);
        }
    }

    public static final class zzn extends zzlw<zzn, zza> implements zznl {
        private static final zzn zzc;
        private static volatile zzns<zzn> zzd;
        private int zze;
        private long zzf;
        private String zzg = "";
        private String zzh = "";
        private long zzi;
        private float zzj;
        private double zzk;

        public static final class zza extends zzlw.zza<zzn, zza> implements zznl {
            private zza() {
                super(zzn.zzc);
            }

            public final zza zza() {
                a();
                ((zzn) this.f13230a).zzn();
                return this;
            }

            public final zza zzb() {
                a();
                ((zzn) this.f13230a).zzo();
                return this;
            }

            public final zza zzc() {
                a();
                ((zzn) this.f13230a).zzp();
                return this;
            }

            /* synthetic */ zza(zzfj zzfjVar) {
                this();
            }

            public final zza zza(double d2) {
                a();
                ((zzn) this.f13230a).zza(d2);
                return this;
            }

            public final zza zzb(long j2) {
                a();
                ((zzn) this.f13230a).zzb(j2);
                return this;
            }

            public final zza zza(long j2) {
                a();
                ((zzn) this.f13230a).zza(j2);
                return this;
            }

            public final zza zzb(String str) {
                a();
                ((zzn) this.f13230a).zzb(str);
                return this;
            }

            public final zza zza(String str) {
                a();
                ((zzn) this.f13230a).zza(str);
                return this;
            }
        }

        static {
            zzn zznVar = new zzn();
            zzc = zznVar;
            zzlw.k(zzn.class, zznVar);
        }

        private zzn() {
        }

        public static zza zze() {
            return (zza) zzc.m();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzn() {
            this.zze &= -33;
            this.zzk = 0.0d;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzo() {
            this.zze &= -9;
            this.zzi = 0L;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzp() {
            this.zze &= -5;
            this.zzh = zzc.zzh;
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzfj zzfjVar = null;
            switch (zzfj.f13199a[i2 - 1]) {
                case 1:
                    return new zzn();
                case 2:
                    return new zza(zzfjVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001ဂ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဂ\u0003\u0005ခ\u0004\u0006က\u0005", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk"});
                case 4:
                    return zzc;
                case 5:
                    zzns<zzn> zzcVar = zzd;
                    if (zzcVar == null) {
                        synchronized (zzn.class) {
                            try {
                                zzcVar = zzd;
                                if (zzcVar == null) {
                                    zzcVar = new zzlw.zzc<>(zzc);
                                    zzd = zzcVar;
                                }
                            } finally {
                            }
                        }
                    }
                    return zzcVar;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public final double zza() {
            return this.zzk;
        }

        public final float zzb() {
            return this.zzj;
        }

        public final long zzc() {
            return this.zzi;
        }

        public final long zzd() {
            return this.zzf;
        }

        public final String zzg() {
            return this.zzg;
        }

        public final String zzh() {
            return this.zzh;
        }

        public final boolean zzi() {
            return (this.zze & 32) != 0;
        }

        public final boolean zzj() {
            return (this.zze & 16) != 0;
        }

        public final boolean zzk() {
            return (this.zze & 8) != 0;
        }

        public final boolean zzl() {
            return (this.zze & 1) != 0;
        }

        public final boolean zzm() {
            return (this.zze & 4) != 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(long j2) {
            this.zze |= 1;
            this.zzf = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(double d2) {
            this.zze |= 32;
            this.zzk = d2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(String str) {
            str.getClass();
            this.zze |= 4;
            this.zzh = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(long j2) {
            this.zze |= 8;
            this.zzi = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zze |= 2;
            this.zzg = str;
        }
    }
}
