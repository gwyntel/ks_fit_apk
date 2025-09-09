package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzev;
import com.google.android.gms.internal.measurement.zzfo;
import com.google.android.gms.internal.measurement.zzlw;
import java.util.Collections;
import java.util.List;
import kotlin.text.Typography;

/* loaded from: classes3.dex */
public final class zzfa {

    public static final class zza extends zzlw<zza, zzb> implements zznl {
        private static final zza zzc;
        private static volatile zzns<zza> zzd;
        private int zze;
        private boolean zzi;
        private zzmf<C0106zza> zzf = zzlw.q();
        private zzmf<zzc> zzg = zzlw.q();
        private zzmf<zzf> zzh = zzlw.q();
        private zzmf<C0106zza> zzj = zzlw.q();

        /* renamed from: com.google.android.gms.internal.measurement.zzfa$zza$zza, reason: collision with other inner class name */
        public static final class C0106zza extends zzlw<C0106zza, C0107zza> implements zznl {
            private static final C0106zza zzc;
            private static volatile zzns<C0106zza> zzd;
            private int zze;
            private int zzf;
            private int zzg;

            /* renamed from: com.google.android.gms.internal.measurement.zzfa$zza$zza$zza, reason: collision with other inner class name */
            public static final class C0107zza extends zzlw.zza<C0106zza, C0107zza> implements zznl {
                private C0107zza() {
                    super(C0106zza.zzc);
                }

                /* synthetic */ C0107zza(zzfc zzfcVar) {
                    this();
                }
            }

            static {
                C0106zza c0106zza = new C0106zza();
                zzc = c0106zza;
                zzlw.k(C0106zza.class, c0106zza);
            }

            private C0106zza() {
            }

            @Override // com.google.android.gms.internal.measurement.zzlw
            protected final Object h(int i2, Object obj, Object obj2) {
                zzfc zzfcVar = null;
                switch (zzfc.f13196a[i2 - 1]) {
                    case 1:
                        return new C0106zza();
                    case 2:
                        return new C0107zza(zzfcVar);
                    case 3:
                        return zzlw.i(zzc, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001᠌\u0000\u0002᠌\u0001", new Object[]{"zze", "zzf", zze.zzb(), "zzg", zzd.zzb()});
                    case 4:
                        return zzc;
                    case 5:
                        zzns<C0106zza> zzcVar = zzd;
                        if (zzcVar == null) {
                            synchronized (C0106zza.class) {
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

            public final zzd zzb() {
                zzd zzdVarZza = zzd.zza(this.zzg);
                return zzdVarZza == null ? zzd.CONSENT_STATUS_UNSPECIFIED : zzdVarZza;
            }

            public final zze zzc() {
                zze zzeVarZza = zze.zza(this.zzf);
                return zzeVarZza == null ? zze.CONSENT_TYPE_UNSPECIFIED : zzeVarZza;
            }
        }

        public static final class zzb extends zzlw.zza<zza, zzb> implements zznl {
            private zzb() {
                super(zza.zzc);
            }

            /* synthetic */ zzb(zzfc zzfcVar) {
                this();
            }
        }

        public static final class zzc extends zzlw<zzc, C0108zza> implements zznl {
            private static final zzc zzc;
            private static volatile zzns<zzc> zzd;
            private int zze;
            private int zzf;
            private int zzg;

            /* renamed from: com.google.android.gms.internal.measurement.zzfa$zza$zzc$zza, reason: collision with other inner class name */
            public static final class C0108zza extends zzlw.zza<zzc, C0108zza> implements zznl {
                private C0108zza() {
                    super(zzc.zzc);
                }

                /* synthetic */ C0108zza(zzfc zzfcVar) {
                    this();
                }
            }

            static {
                zzc zzcVar = new zzc();
                zzc = zzcVar;
                zzlw.k(zzc.class, zzcVar);
            }

            private zzc() {
            }

            @Override // com.google.android.gms.internal.measurement.zzlw
            protected final Object h(int i2, Object obj, Object obj2) {
                zzfc zzfcVar = null;
                switch (zzfc.f13196a[i2 - 1]) {
                    case 1:
                        return new zzc();
                    case 2:
                        return new C0108zza(zzfcVar);
                    case 3:
                        return zzlw.i(zzc, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001᠌\u0000\u0002᠌\u0001", new Object[]{"zze", "zzf", zze.zzb(), "zzg", zze.zzb()});
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

            public final zze zzb() {
                zze zzeVarZza = zze.zza(this.zzg);
                return zzeVarZza == null ? zze.CONSENT_TYPE_UNSPECIFIED : zzeVarZza;
            }

            public final zze zzc() {
                zze zzeVarZza = zze.zza(this.zzf);
                return zzeVarZza == null ? zze.CONSENT_TYPE_UNSPECIFIED : zzeVarZza;
            }
        }

        public enum zzd implements zzly {
            CONSENT_STATUS_UNSPECIFIED(0),
            GRANTED(1),
            DENIED(2);

            private static final zzmb<zzd> zzd = new zzfe();
            private final int zzf;

            zzd(int i2) {
                this.zzf = i2;
            }

            public static zzma zzb() {
                return zzfd.f13197a;
            }

            @Override // java.lang.Enum
            public final String toString() {
                return "<" + zzd.class.getName() + '@' + Integer.toHexString(System.identityHashCode(this)) + " number=" + this.zzf + " name=" + name() + Typography.greater;
            }

            @Override // com.google.android.gms.internal.measurement.zzly
            public final int zza() {
                return this.zzf;
            }

            public static zzd zza(int i2) {
                if (i2 == 0) {
                    return CONSENT_STATUS_UNSPECIFIED;
                }
                if (i2 == 1) {
                    return GRANTED;
                }
                if (i2 != 2) {
                    return null;
                }
                return DENIED;
            }
        }

        public enum zze implements zzly {
            CONSENT_TYPE_UNSPECIFIED(0),
            AD_STORAGE(1),
            ANALYTICS_STORAGE(2),
            AD_USER_DATA(3),
            AD_PERSONALIZATION(4);

            private static final zzmb<zze> zzf = new zzff();
            private final int zzh;

            zze(int i2) {
                this.zzh = i2;
            }

            public static zzma zzb() {
                return zzfg.f13198a;
            }

            @Override // java.lang.Enum
            public final String toString() {
                return "<" + zze.class.getName() + '@' + Integer.toHexString(System.identityHashCode(this)) + " number=" + this.zzh + " name=" + name() + Typography.greater;
            }

            @Override // com.google.android.gms.internal.measurement.zzly
            public final int zza() {
                return this.zzh;
            }

            public static zze zza(int i2) {
                if (i2 == 0) {
                    return CONSENT_TYPE_UNSPECIFIED;
                }
                if (i2 == 1) {
                    return AD_STORAGE;
                }
                if (i2 == 2) {
                    return ANALYTICS_STORAGE;
                }
                if (i2 == 3) {
                    return AD_USER_DATA;
                }
                if (i2 != 4) {
                    return null;
                }
                return AD_PERSONALIZATION;
            }
        }

        public static final class zzf extends zzlw<zzf, C0109zza> implements zznl {
            private static final zzf zzc;
            private static volatile zzns<zzf> zzd;
            private int zze;
            private String zzf = "";
            private String zzg = "";

            /* renamed from: com.google.android.gms.internal.measurement.zzfa$zza$zzf$zza, reason: collision with other inner class name */
            public static final class C0109zza extends zzlw.zza<zzf, C0109zza> implements zznl {
                private C0109zza() {
                    super(zzf.zzc);
                }

                /* synthetic */ C0109zza(zzfc zzfcVar) {
                    this();
                }
            }

            static {
                zzf zzfVar = new zzf();
                zzc = zzfVar;
                zzlw.k(zzf.class, zzfVar);
            }

            private zzf() {
            }

            @Override // com.google.android.gms.internal.measurement.zzlw
            protected final Object h(int i2, Object obj, Object obj2) {
                zzfc zzfcVar = null;
                switch (zzfc.f13196a[i2 - 1]) {
                    case 1:
                        return new zzf();
                    case 2:
                        return new C0109zza(zzfcVar);
                    case 3:
                        return zzlw.i(zzc, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001", new Object[]{"zze", "zzf", "zzg"});
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

            public final String zzb() {
                return this.zzf;
            }
        }

        static {
            zza zzaVar = new zza();
            zzc = zzaVar;
            zzlw.k(zza.class, zzaVar);
        }

        private zza() {
        }

        public static zza zzb() {
            return zzc;
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzfc zzfcVar = null;
            switch (zzfc.f13196a[i2 - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new zzb(zzfcVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0004\u0000\u0001\u001b\u0002\u001b\u0003\u001b\u0004ဇ\u0000\u0005\u001b", new Object[]{"zze", "zzf", C0106zza.class, "zzg", zzc.class, "zzh", zzf.class, "zzi", "zzj", C0106zza.class});
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

        public final List<zzf> zzc() {
            return this.zzh;
        }

        public final List<C0106zza> zzd() {
            return this.zzf;
        }

        public final List<zzc> zze() {
            return this.zzg;
        }

        public final boolean zzf() {
            return this.zzi;
        }

        public final boolean zzg() {
            return (this.zze & 1) != 0;
        }
    }

    public static final class zzb extends zzlw<zzb, zza> implements zznl {
        private static final zzb zzc;
        private static volatile zzns<zzb> zzd;
        private int zze;
        private String zzf = "";
        private zzmf<zzf> zzg = zzlw.q();
        private boolean zzh;

        public static final class zza extends zzlw.zza<zzb, zza> implements zznl {
            private zza() {
                super(zzb.zzc);
            }

            /* synthetic */ zza(zzfc zzfcVar) {
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

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzfc zzfcVar = null;
            switch (zzfc.f13196a[i2 - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza(zzfcVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0001\u0000\u0001ဈ\u0000\u0002\u001b\u0003ဇ\u0001", new Object[]{"zze", "zzf", "zzg", zzf.class, "zzh"});
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

        public final String zzb() {
            return this.zzf;
        }
    }

    public static final class zzc extends zzlw<zzc, zza> implements zznl {
        private static final zzc zzc;
        private static volatile zzns<zzc> zzd;
        private int zze;
        private String zzf = "";
        private boolean zzg;
        private boolean zzh;
        private int zzi;

        public static final class zza extends zzlw.zza<zzc, zza> implements zznl {
            private zza() {
                super(zzc.zzc);
            }

            public final int zza() {
                return ((zzc) this.f13230a).zza();
            }

            public final String zzb() {
                return ((zzc) this.f13230a).zzc();
            }

            public final boolean zzc() {
                return ((zzc) this.f13230a).zzd();
            }

            public final boolean zzd() {
                return ((zzc) this.f13230a).zze();
            }

            public final boolean zze() {
                return ((zzc) this.f13230a).zzf();
            }

            public final boolean zzf() {
                return ((zzc) this.f13230a).zzg();
            }

            public final boolean zzg() {
                return ((zzc) this.f13230a).zzh();
            }

            /* synthetic */ zza(zzfc zzfcVar) {
                this();
            }

            public final zza zza(String str) {
                a();
                ((zzc) this.f13230a).zza(str);
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

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzfc zzfcVar = null;
            switch (zzfc.f13196a[i2 - 1]) {
                case 1:
                    return new zzc();
                case 2:
                    return new zza(zzfcVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဇ\u0001\u0003ဇ\u0002\u0004င\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi"});
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
            return this.zzi;
        }

        public final String zzc() {
            return this.zzf;
        }

        public final boolean zzd() {
            return this.zzg;
        }

        public final boolean zze() {
            return this.zzh;
        }

        public final boolean zzf() {
            return (this.zze & 2) != 0;
        }

        public final boolean zzg() {
            return (this.zze & 4) != 0;
        }

        public final boolean zzh() {
            return (this.zze & 8) != 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zze |= 1;
            this.zzf = str;
        }
    }

    public static final class zzd extends zzlw<zzd, zza> implements zznl {
        private static final zzd zzc;
        private static volatile zzns<zzd> zzd;
        private int zze;
        private long zzf;
        private int zzh;
        private boolean zzm;
        private zza zzt;
        private zze zzu;
        private String zzg = "";
        private zzmf<zzg> zzi = zzlw.q();
        private zzmf<zzc> zzj = zzlw.q();
        private zzmf<zzev.zza> zzk = zzlw.q();
        private String zzl = "";
        private zzmf<zzfo.zzc> zzn = zzlw.q();
        private zzmf<zzb> zzo = zzlw.q();
        private String zzp = "";
        private String zzq = "";
        private String zzr = "";
        private String zzs = "";

        public static final class zza extends zzlw.zza<zzd, zza> implements zznl {
            private zza() {
                super(zzd.zzc);
            }

            public final int zza() {
                return ((zzd) this.f13230a).zzb();
            }

            public final zza zzb() {
                a();
                ((zzd) this.f13230a).zzt();
                return this;
            }

            public final String zzc() {
                return ((zzd) this.f13230a).zzk();
            }

            public final List<zzev.zza> zzd() {
                return Collections.unmodifiableList(((zzd) this.f13230a).zzl());
            }

            public final List<zzb> zze() {
                return Collections.unmodifiableList(((zzd) this.f13230a).zzm());
            }

            /* synthetic */ zza(zzfc zzfcVar) {
                this();
            }

            public final zzc zza(int i2) {
                return ((zzd) this.f13230a).zza(i2);
            }

            public final zza zza(int i2, zzc.zza zzaVar) {
                a();
                ((zzd) this.f13230a).zza(i2, (zzc) ((zzlw) zzaVar.zzab()));
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

        public static zza zze() {
            return (zza) zzc.m();
        }

        public static zzd zzg() {
            return zzc;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzt() {
            this.zzk = zzlw.q();
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzfc zzfcVar = null;
            switch (zzfc.f13196a[i2 - 1]) {
                case 1:
                    return new zzd();
                case 2:
                    return new zza(zzfcVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0010\u0000\u0001\u0001\u0010\u0010\u0000\u0005\u0000\u0001ဂ\u0000\u0002ဈ\u0001\u0003င\u0002\u0004\u001b\u0005\u001b\u0006\u001b\u0007ဈ\u0003\bဇ\u0004\t\u001b\n\u001b\u000bဈ\u0005\fဈ\u0006\rဈ\u0007\u000eဈ\b\u000fဉ\t\u0010ဉ\n", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", zzg.class, "zzj", zzc.class, "zzk", zzev.zza.class, "zzl", "zzm", "zzn", zzfo.zzc.class, "zzo", zzb.class, "zzp", "zzq", "zzr", "zzs", "zzt", "zzu"});
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
            return this.zzn.size();
        }

        public final int zzb() {
            return this.zzj.size();
        }

        public final long zzc() {
            return this.zzf;
        }

        public final zza zzd() {
            zza zzaVar = this.zzt;
            return zzaVar == null ? zza.zzb() : zzaVar;
        }

        public final String zzh() {
            return this.zzg;
        }

        public final String zzi() {
            return this.zzr;
        }

        public final String zzj() {
            return this.zzq;
        }

        public final String zzk() {
            return this.zzp;
        }

        public final List<zzev.zza> zzl() {
            return this.zzk;
        }

        public final List<zzb> zzm() {
            return this.zzo;
        }

        public final List<zzfo.zzc> zzn() {
            return this.zzn;
        }

        public final List<zzg> zzo() {
            return this.zzi;
        }

        public final boolean zzp() {
            return this.zzm;
        }

        public final boolean zzq() {
            return (this.zze & 512) != 0;
        }

        public final boolean zzr() {
            return (this.zze & 2) != 0;
        }

        public final boolean zzs() {
            return (this.zze & 1) != 0;
        }

        public final zzc zza(int i2) {
            return this.zzj.get(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(int i2, zzc zzcVar) {
            zzcVar.getClass();
            zzmf<zzc> zzmfVar = this.zzj;
            if (!zzmfVar.zzc()) {
                this.zzj = zzlw.g(zzmfVar);
            }
            this.zzj.set(i2, zzcVar);
        }
    }

    public static final class zze extends zzlw<zze, zza> implements zznl {
        private static final zze zzc;
        private static volatile zzns<zze> zzd;
        private int zze;
        private int zzf = 14;
        private int zzg = 11;
        private int zzh = 60;

        public static final class zza extends zzlw.zza<zze, zza> implements zznl {
            private zza() {
                super(zze.zzc);
            }

            /* synthetic */ zza(zzfc zzfcVar) {
                this();
            }
        }

        static {
            zze zzeVar = new zze();
            zzc = zzeVar;
            zzlw.k(zze.class, zzeVar);
        }

        private zze() {
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzfc zzfcVar = null;
            switch (zzfc.f13196a[i2 - 1]) {
                case 1:
                    return new zze();
                case 2:
                    return new zza(zzfcVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001င\u0000\u0002င\u0001\u0003င\u0002", new Object[]{"zze", "zzf", "zzg", "zzh"});
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
    }

    public static final class zzf extends zzlw<zzf, zza> implements zznl {
        private static final zzf zzc;
        private static volatile zzns<zzf> zzd;
        private int zze;
        private String zzf = "";
        private String zzg = "";

        public static final class zza extends zzlw.zza<zzf, zza> implements zznl {
            private zza() {
                super(zzf.zzc);
            }

            /* synthetic */ zza(zzfc zzfcVar) {
                this();
            }
        }

        static {
            zzf zzfVar = new zzf();
            zzc = zzfVar;
            zzlw.k(zzf.class, zzfVar);
        }

        private zzf() {
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzfc zzfcVar = null;
            switch (zzfc.f13196a[i2 - 1]) {
                case 1:
                    return new zzf();
                case 2:
                    return new zza(zzfcVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001", new Object[]{"zze", "zzf", "zzg"});
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
    }

    public static final class zzg extends zzlw<zzg, zza> implements zznl {
        private static final zzg zzc;
        private static volatile zzns<zzg> zzd;
        private int zze;
        private String zzf = "";
        private String zzg = "";

        public static final class zza extends zzlw.zza<zzg, zza> implements zznl {
            private zza() {
                super(zzg.zzc);
            }

            /* synthetic */ zza(zzfc zzfcVar) {
                this();
            }
        }

        static {
            zzg zzgVar = new zzg();
            zzc = zzgVar;
            zzlw.k(zzg.class, zzgVar);
        }

        private zzg() {
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzfc zzfcVar = null;
            switch (zzfc.f13196a[i2 - 1]) {
                case 1:
                    return new zzg();
                case 2:
                    return new zza(zzfcVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001", new Object[]{"zze", "zzf", "zzg"});
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

        public final String zzb() {
            return this.zzf;
        }

        public final String zzc() {
            return this.zzg;
        }
    }
}
