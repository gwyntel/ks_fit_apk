package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzlw;
import java.util.List;
import kotlin.text.Typography;

/* loaded from: classes3.dex */
public final class zzev {

    public static final class zza extends zzlw<zza, C0105zza> implements zznl {
        private static final zza zzc;
        private static volatile zzns<zza> zzd;
        private int zze;
        private int zzf;
        private zzmf<zze> zzg = zzlw.q();
        private zzmf<zzb> zzh = zzlw.q();
        private boolean zzi;
        private boolean zzj;

        /* renamed from: com.google.android.gms.internal.measurement.zzev$zza$zza, reason: collision with other inner class name */
        public static final class C0105zza extends zzlw.zza<zza, C0105zza> implements zznl {
            private C0105zza() {
                super(zza.zzc);
            }

            public final int zza() {
                return ((zza) this.f13230a).zzb();
            }

            public final int zzb() {
                return ((zza) this.f13230a).zzc();
            }

            /* synthetic */ C0105zza(zzew zzewVar) {
                this();
            }

            public final C0105zza zza(int i2, zzb.zza zzaVar) {
                a();
                ((zza) this.f13230a).zza(i2, (zzb) ((zzlw) zzaVar.zzab()));
                return this;
            }

            public final zze zzb(int i2) {
                return ((zza) this.f13230a).zzb(i2);
            }

            public final C0105zza zza(int i2, zze.zza zzaVar) {
                a();
                ((zza) this.f13230a).zza(i2, (zze) ((zzlw) zzaVar.zzab()));
                return this;
            }

            public final zzb zza(int i2) {
                return ((zza) this.f13230a).zza(i2);
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
            zzew zzewVar = null;
            switch (zzew.f13191a[i2 - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new C0105zza(zzewVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0002\u0000\u0001င\u0000\u0002\u001b\u0003\u001b\u0004ဇ\u0001\u0005ဇ\u0002", new Object[]{"zze", "zzf", "zzg", zze.class, "zzh", zzb.class, "zzi", "zzj"});
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
            return this.zzf;
        }

        public final int zzb() {
            return this.zzh.size();
        }

        public final int zzc() {
            return this.zzg.size();
        }

        public final List<zzb> zze() {
            return this.zzh;
        }

        public final List<zze> zzf() {
            return this.zzg;
        }

        public final boolean zzg() {
            return (this.zze & 1) != 0;
        }

        public final zzb zza(int i2) {
            return this.zzh.get(i2);
        }

        public final zze zzb(int i2) {
            return this.zzg.get(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(int i2, zzb zzbVar) {
            zzbVar.getClass();
            zzmf<zzb> zzmfVar = this.zzh;
            if (!zzmfVar.zzc()) {
                this.zzh = zzlw.g(zzmfVar);
            }
            this.zzh.set(i2, zzbVar);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(int i2, zze zzeVar) {
            zzeVar.getClass();
            zzmf<zze> zzmfVar = this.zzg;
            if (!zzmfVar.zzc()) {
                this.zzg = zzlw.g(zzmfVar);
            }
            this.zzg.set(i2, zzeVar);
        }
    }

    public static final class zzb extends zzlw<zzb, zza> implements zznl {
        private static final zzb zzc;
        private static volatile zzns<zzb> zzd;
        private int zze;
        private int zzf;
        private String zzg = "";
        private zzmf<zzc> zzh = zzlw.q();
        private boolean zzi;
        private zzd zzj;
        private boolean zzk;
        private boolean zzl;
        private boolean zzm;

        public static final class zza extends zzlw.zza<zzb, zza> implements zznl {
            private zza() {
                super(zzb.zzc);
            }

            public final int zza() {
                return ((zzb) this.f13230a).zza();
            }

            public final String zzb() {
                return ((zzb) this.f13230a).zzf();
            }

            /* synthetic */ zza(zzew zzewVar) {
                this();
            }

            public final zza zza(String str) {
                a();
                ((zzb) this.f13230a).zza(str);
                return this;
            }

            public final zza zza(int i2, zzc zzcVar) {
                a();
                ((zzb) this.f13230a).zza(i2, zzcVar);
                return this;
            }

            public final zzc zza(int i2) {
                return ((zzb) this.f13230a).zza(i2);
            }
        }

        static {
            zzb zzbVar = new zzb();
            zzc = zzbVar;
            zzlw.k(zzb.class, zzbVar);
        }

        private zzb() {
        }

        public static zza zzc() {
            return (zza) zzc.m();
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzew zzewVar = null;
            switch (zzew.f13191a[i2 - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza(zzewVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\b\u0000\u0001\u0001\b\b\u0000\u0001\u0000\u0001င\u0000\u0002ဈ\u0001\u0003\u001b\u0004ဇ\u0002\u0005ဉ\u0003\u0006ဇ\u0004\u0007ဇ\u0005\bဇ\u0006", new Object[]{"zze", "zzf", "zzg", "zzh", zzc.class, "zzi", "zzj", "zzk", "zzl", "zzm"});
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

        public final int zza() {
            return this.zzh.size();
        }

        public final int zzb() {
            return this.zzf;
        }

        public final zzd zze() {
            zzd zzdVar = this.zzj;
            return zzdVar == null ? zzd.zzc() : zzdVar;
        }

        public final String zzf() {
            return this.zzg;
        }

        public final List<zzc> zzg() {
            return this.zzh;
        }

        public final boolean zzh() {
            return this.zzk;
        }

        public final boolean zzi() {
            return this.zzl;
        }

        public final boolean zzj() {
            return this.zzm;
        }

        public final boolean zzk() {
            return (this.zze & 8) != 0;
        }

        public final boolean zzl() {
            return (this.zze & 1) != 0;
        }

        public final boolean zzm() {
            return (this.zze & 64) != 0;
        }

        public final zzc zza(int i2) {
            return this.zzh.get(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zze |= 2;
            this.zzg = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(int i2, zzc zzcVar) {
            zzcVar.getClass();
            zzmf<zzc> zzmfVar = this.zzh;
            if (!zzmfVar.zzc()) {
                this.zzh = zzlw.g(zzmfVar);
            }
            this.zzh.set(i2, zzcVar);
        }
    }

    public static final class zzc extends zzlw<zzc, zza> implements zznl {
        private static final zzc zzc;
        private static volatile zzns<zzc> zzd;
        private int zze;
        private zzf zzf;
        private zzd zzg;
        private boolean zzh;
        private String zzi = "";

        public static final class zza extends zzlw.zza<zzc, zza> implements zznl {
            private zza() {
                super(zzc.zzc);
            }

            public final zza zza(String str) {
                a();
                ((zzc) this.f13230a).zza(str);
                return this;
            }

            /* synthetic */ zza(zzew zzewVar) {
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

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zze |= 8;
            this.zzi = str;
        }

        public static zzc zzb() {
            return zzc;
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzew zzewVar = null;
            switch (zzew.f13191a[i2 - 1]) {
                case 1:
                    return new zzc();
                case 2:
                    return new zza(zzewVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဇ\u0002\u0004ဈ\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi"});
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

        public final zzd zzc() {
            zzd zzdVar = this.zzg;
            return zzdVar == null ? zzd.zzc() : zzdVar;
        }

        public final zzf zzd() {
            zzf zzfVar = this.zzf;
            return zzfVar == null ? zzf.zzd() : zzfVar;
        }

        public final String zze() {
            return this.zzi;
        }

        public final boolean zzf() {
            return this.zzh;
        }

        public final boolean zzg() {
            return (this.zze & 4) != 0;
        }

        public final boolean zzh() {
            return (this.zze & 2) != 0;
        }

        public final boolean zzi() {
            return (this.zze & 8) != 0;
        }

        public final boolean zzj() {
            return (this.zze & 1) != 0;
        }
    }

    public static final class zzd extends zzlw<zzd, zzb> implements zznl {
        private static final zzd zzc;
        private static volatile zzns<zzd> zzd;
        private int zze;
        private int zzf;
        private boolean zzg;
        private String zzh = "";
        private String zzi = "";
        private String zzj = "";

        public enum zza implements zzly {
            UNKNOWN_COMPARISON_TYPE(0),
            LESS_THAN(1),
            GREATER_THAN(2),
            EQUAL(3),
            BETWEEN(4);

            private static final zzmb<zza> zzf = new zzey();
            private final int zzh;

            zza(int i2) {
                this.zzh = i2;
            }

            public static zzma zzb() {
                return zzex.f13192a;
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
                    return UNKNOWN_COMPARISON_TYPE;
                }
                if (i2 == 1) {
                    return LESS_THAN;
                }
                if (i2 == 2) {
                    return GREATER_THAN;
                }
                if (i2 == 3) {
                    return EQUAL;
                }
                if (i2 != 4) {
                    return null;
                }
                return BETWEEN;
            }
        }

        public static final class zzb extends zzlw.zza<zzd, zzb> implements zznl {
            private zzb() {
                super(zzd.zzc);
            }

            /* synthetic */ zzb(zzew zzewVar) {
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

        public static zzd zzc() {
            return zzc;
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzew zzewVar = null;
            switch (zzew.f13191a[i2 - 1]) {
                case 1:
                    return new zzd();
                case 2:
                    return new zzb(zzewVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001᠌\u0000\u0002ဇ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005ဈ\u0004", new Object[]{"zze", "zzf", zza.zzb(), "zzg", "zzh", "zzi", "zzj"});
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

        public final zza zza() {
            zza zzaVarZza = zza.zza(this.zzf);
            return zzaVarZza == null ? zza.UNKNOWN_COMPARISON_TYPE : zzaVarZza;
        }

        public final String zzd() {
            return this.zzh;
        }

        public final String zze() {
            return this.zzj;
        }

        public final String zzf() {
            return this.zzi;
        }

        public final boolean zzg() {
            return this.zzg;
        }

        public final boolean zzh() {
            return (this.zze & 1) != 0;
        }

        public final boolean zzi() {
            return (this.zze & 4) != 0;
        }

        public final boolean zzj() {
            return (this.zze & 2) != 0;
        }

        public final boolean zzk() {
            return (this.zze & 16) != 0;
        }

        public final boolean zzl() {
            return (this.zze & 8) != 0;
        }
    }

    public static final class zze extends zzlw<zze, zza> implements zznl {
        private static final zze zzc;
        private static volatile zzns<zze> zzd;
        private int zze;
        private int zzf;
        private String zzg = "";
        private zzc zzh;
        private boolean zzi;
        private boolean zzj;
        private boolean zzk;

        public static final class zza extends zzlw.zza<zze, zza> implements zznl {
            private zza() {
                super(zze.zzc);
            }

            public final zza zza(String str) {
                a();
                ((zze) this.f13230a).zza(str);
                return this;
            }

            /* synthetic */ zza(zzew zzewVar) {
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

        public static zza zzc() {
            return (zza) zzc.m();
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzew zzewVar = null;
            switch (zzew.f13191a[i2 - 1]) {
                case 1:
                    return new zze();
                case 2:
                    return new zza(zzewVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001င\u0000\u0002ဈ\u0001\u0003ဉ\u0002\u0004ဇ\u0003\u0005ဇ\u0004\u0006ဇ\u0005", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk"});
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
            return this.zzf;
        }

        public final zzc zzb() {
            zzc zzcVar = this.zzh;
            return zzcVar == null ? zzc.zzb() : zzcVar;
        }

        public final String zze() {
            return this.zzg;
        }

        public final boolean zzf() {
            return this.zzi;
        }

        public final boolean zzg() {
            return this.zzj;
        }

        public final boolean zzh() {
            return this.zzk;
        }

        public final boolean zzi() {
            return (this.zze & 1) != 0;
        }

        public final boolean zzj() {
            return (this.zze & 32) != 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zze |= 2;
            this.zzg = str;
        }
    }

    public static final class zzf extends zzlw<zzf, zza> implements zznl {
        private static final zzf zzc;
        private static volatile zzns<zzf> zzd;
        private int zze;
        private int zzf;
        private boolean zzh;
        private String zzg = "";
        private zzmf<String> zzi = zzlw.q();

        public static final class zza extends zzlw.zza<zzf, zza> implements zznl {
            private zza() {
                super(zzf.zzc);
            }

            /* synthetic */ zza(zzew zzewVar) {
                this();
            }
        }

        public enum zzb implements zzly {
            UNKNOWN_MATCH_TYPE(0),
            REGEXP(1),
            BEGINS_WITH(2),
            ENDS_WITH(3),
            PARTIAL(4),
            EXACT(5),
            IN_LIST(6);

            private static final zzmb<zzb> zzh = new zzez();
            private final int zzj;

            zzb(int i2) {
                this.zzj = i2;
            }

            public static zzma zzb() {
                return zzfb.f13195a;
            }

            @Override // java.lang.Enum
            public final String toString() {
                return "<" + zzb.class.getName() + '@' + Integer.toHexString(System.identityHashCode(this)) + " number=" + this.zzj + " name=" + name() + Typography.greater;
            }

            @Override // com.google.android.gms.internal.measurement.zzly
            public final int zza() {
                return this.zzj;
            }

            public static zzb zza(int i2) {
                switch (i2) {
                    case 0:
                        return UNKNOWN_MATCH_TYPE;
                    case 1:
                        return REGEXP;
                    case 2:
                        return BEGINS_WITH;
                    case 3:
                        return ENDS_WITH;
                    case 4:
                        return PARTIAL;
                    case 5:
                        return EXACT;
                    case 6:
                        return IN_LIST;
                    default:
                        return null;
                }
            }
        }

        static {
            zzf zzfVar = new zzf();
            zzc = zzfVar;
            zzlw.k(zzf.class, zzfVar);
        }

        private zzf() {
        }

        public static zzf zzd() {
            return zzc;
        }

        @Override // com.google.android.gms.internal.measurement.zzlw
        protected final Object h(int i2, Object obj, Object obj2) {
            zzew zzewVar = null;
            switch (zzew.f13191a[i2 - 1]) {
                case 1:
                    return new zzf();
                case 2:
                    return new zza(zzewVar);
                case 3:
                    return zzlw.i(zzc, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0001\u0000\u0001᠌\u0000\u0002ဈ\u0001\u0003ဇ\u0002\u0004\u001a", new Object[]{"zze", "zzf", zzb.zzb(), "zzg", "zzh", "zzi"});
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

        public final int zza() {
            return this.zzi.size();
        }

        public final zzb zzb() {
            zzb zzbVarZza = zzb.zza(this.zzf);
            return zzbVarZza == null ? zzb.UNKNOWN_MATCH_TYPE : zzbVarZza;
        }

        public final String zze() {
            return this.zzg;
        }

        public final List<String> zzf() {
            return this.zzi;
        }

        public final boolean zzg() {
            return this.zzh;
        }

        public final boolean zzh() {
            return (this.zze & 4) != 0;
        }

        public final boolean zzi() {
            return (this.zze & 2) != 0;
        }

        public final boolean zzj() {
            return (this.zze & 1) != 0;
        }
    }
}
