package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzlw;
import java.util.List;
import kotlin.text.Typography;

/* loaded from: classes3.dex */
public final class zzfo {

    public static final class zza extends zzlw<zza, C0111zza> implements zznl {
        private static final zza zzc;
        private static volatile zzns<zza> zzd;
        private zzmf<zzb> zze = zzlw.q();

        /* renamed from: com.google.android.gms.internal.measurement.zzfo$zza$zza, reason: collision with other inner class name */
        public static final class C0111zza extends zzlw.zza<zza, C0111zza> implements zznl {
            private C0111zza() {
                super(zza.zzc);
            }

            /* synthetic */ C0111zza(zzfp zzfpVar) {
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

        public static zza zzc() {
            return zzc;
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzfp zzfpVar = null;
            switch (zzfp.f13202a[i2 - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new C0111zza(zzfpVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zze", zzb.class});
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

        public final int zza() {
            return this.zze.size();
        }

        public final List<zzb> zzd() {
            return this.zze;
        }
    }

    public static final class zzb extends zzlw<zzb, zza> implements zznl {
        private static final zzb zzc;
        private static volatile zzns<zzb> zzd;
        private int zze;
        private String zzf = "";
        private zzmf<zzd> zzg = zzlw.q();

        public static final class zza extends zzlw.zza<zzb, zza> implements zznl {
            private zza() {
                super(zzb.zzc);
            }

            /* synthetic */ zza(zzfp zzfpVar) {
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
            zzfp zzfpVar = null;
            switch (zzfp.f13202a[i2 - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza(zzfpVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001ဈ\u0000\u0002\u001b", new Object[]{"zze", "zzf", "zzg", zzd.class});
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

        public final List<zzd> zzc() {
            return this.zzg;
        }
    }

    public static final class zzc extends zzlw<zzc, zza> implements zznl {
        private static final zzc zzc;
        private static volatile zzns<zzc> zzd;
        private int zze;
        private zzmf<zzd> zzf = zzlw.q();
        private zza zzg;

        public static final class zza extends zzlw.zza<zzc, zza> implements zznl {
            private zza() {
                super(zzc.zzc);
            }

            /* synthetic */ zza(zzfp zzfpVar) {
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
            zzfp zzfpVar = null;
            switch (zzfp.f13202a[i2 - 1]) {
                case 1:
                    return new zzc();
                case 2:
                    return new zza(zzfpVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001\u001b\u0002ဉ\u0000", new Object[]{"zze", "zzf", zzd.class, "zzg"});
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

        public final zza zza() {
            zza zzaVar = this.zzg;
            return zzaVar == null ? zza.zzc() : zzaVar;
        }

        public final List<zzd> zzc() {
            return this.zzf;
        }
    }

    public static final class zzd extends zzlw<zzd, zzb> implements zznl {
        private static final zzd zzc;
        private static volatile zzns<zzd> zzd;
        private int zze;
        private int zzf;
        private zzmf<zzd> zzg = zzlw.q();
        private String zzh = "";
        private String zzi = "";
        private boolean zzj;
        private double zzk;

        public enum zza implements zzly {
            UNKNOWN(0),
            STRING(1),
            NUMBER(2),
            BOOLEAN(3),
            STATEMENT(4);

            private static final zzmb<zza> zzf = new zzfr();
            private final int zzh;

            zza(int i2) {
                this.zzh = i2;
            }

            public static zzma zzb() {
                return zzfq.f13203a;
            }

            @Override // java.lang.Enum
            public final String toString() {
                return "<" + zza.class.getName() + '@' + Integer.toHexString(System.identityHashCode(this)) + " number=" + this.zzh + " name=" + name() + Typography.greater;
            }

            @Override // com.google.android.gms.internal.measurement.zzly
            public final int zza() {
                return this.zzh;
            }

            public static zza zza(int i2) {
                if (i2 == 0) {
                    return UNKNOWN;
                }
                if (i2 == 1) {
                    return STRING;
                }
                if (i2 == 2) {
                    return NUMBER;
                }
                if (i2 == 3) {
                    return BOOLEAN;
                }
                if (i2 != 4) {
                    return null;
                }
                return STATEMENT;
            }
        }

        public static final class zzb extends zzlw.zza<zzd, zzb> implements zznl {
            private zzb() {
                super(zzd.zzc);
            }

            /* synthetic */ zzb(zzfp zzfpVar) {
                this();
            }
        }

        static {
            zzd zzdVar = new zzd();
            zzc = zzdVar;
            zzlw.k(zzd.class, zzdVar);
        }

        private zzd() {
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzfp zzfpVar = null;
            switch (zzfp.f13202a[i2 - 1]) {
                case 1:
                    return new zzd();
                case 2:
                    return new zzb(zzfpVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0001\u0000\u0001᠌\u0000\u0002\u001b\u0003ဈ\u0001\u0004ဈ\u0002\u0005ဇ\u0003\u0006က\u0004", new Object[]{"zze", "zzf", zza.zzb(), "zzg", zzd.class, "zzh", "zzi", "zzj", "zzk"});
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

        public final double zza() {
            return this.zzk;
        }

        public final zza zzb() {
            zza zzaVarZza = zza.zza(this.zzf);
            return zzaVarZza == null ? zza.UNKNOWN : zzaVarZza;
        }

        public final String zzd() {
            return this.zzh;
        }

        public final String zze() {
            return this.zzi;
        }

        public final List<zzd> zzf() {
            return this.zzg;
        }

        public final boolean zzg() {
            return this.zzj;
        }

        public final boolean zzh() {
            return (this.zze & 8) != 0;
        }

        public final boolean zzi() {
            return (this.zze & 16) != 0;
        }

        public final boolean zzj() {
            return (this.zze & 4) != 0;
        }
    }
}
