package com.google.android.gms.internal.measurement;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

/* loaded from: classes3.dex */
final class zzpc {

    /* renamed from: a, reason: collision with root package name */
    static final boolean f13234a;
    private static final Unsafe zzb;
    private static final Class<?> zzc;
    private static final boolean zzd;
    private static final boolean zze;
    private static final zzb zzf;
    private static final boolean zzg;
    private static final boolean zzh;
    private static final long zzi;
    private static final long zzj;
    private static final long zzk;
    private static final long zzl;
    private static final long zzm;
    private static final long zzn;
    private static final long zzo;
    private static final long zzp;
    private static final long zzq;
    private static final long zzr;
    private static final long zzs;
    private static final long zzt;
    private static final long zzu;
    private static final long zzv;
    private static final int zzw;

    private static final class zza extends zzb {
        zza(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.google.android.gms.internal.measurement.zzpc.zzb
        public final double zza(Object obj, long j2) {
            return Double.longBitsToDouble(zze(obj, j2));
        }

        @Override // com.google.android.gms.internal.measurement.zzpc.zzb
        public final float zzb(Object obj, long j2) {
            return Float.intBitsToFloat(zzd(obj, j2));
        }

        @Override // com.google.android.gms.internal.measurement.zzpc.zzb
        public final boolean zzc(Object obj, long j2) {
            return zzpc.f13234a ? zzpc.w(obj, j2) : zzpc.x(obj, j2);
        }

        @Override // com.google.android.gms.internal.measurement.zzpc.zzb
        public final void zza(Object obj, long j2, boolean z2) {
            if (zzpc.f13234a) {
                zzpc.j(obj, j2, z2);
            } else {
                zzpc.p(obj, j2, z2);
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzpc.zzb
        public final void zza(Object obj, long j2, byte b2) {
            if (zzpc.f13234a) {
                zzpc.zzc(obj, j2, b2);
            } else {
                zzpc.zzd(obj, j2, b2);
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzpc.zzb
        public final void zza(Object obj, long j2, double d2) {
            zza(obj, j2, Double.doubleToLongBits(d2));
        }

        @Override // com.google.android.gms.internal.measurement.zzpc.zzb
        public final void zza(Object obj, long j2, float f2) {
            zza(obj, j2, Float.floatToIntBits(f2));
        }
    }

    private static abstract class zzb {

        /* renamed from: a, reason: collision with root package name */
        Unsafe f13235a;

        zzb(Unsafe unsafe) {
            this.f13235a = unsafe;
        }

        public abstract double zza(Object obj, long j2);

        public abstract void zza(Object obj, long j2, byte b2);

        public abstract void zza(Object obj, long j2, double d2);

        public abstract void zza(Object obj, long j2, float f2);

        public final void zza(Object obj, long j2, int i2) {
            this.f13235a.putInt(obj, j2, i2);
        }

        public abstract void zza(Object obj, long j2, boolean z2);

        public abstract float zzb(Object obj, long j2);

        public final boolean zzb() {
            Unsafe unsafe = this.f13235a;
            if (unsafe == null) {
                return false;
            }
            try {
                Class<?> cls = unsafe.getClass();
                cls.getMethod("objectFieldOffset", Field.class);
                cls.getMethod("getLong", Object.class, Long.TYPE);
                return zzpc.zze() != null;
            } catch (Throwable th) {
                zzpc.k(th);
                return false;
            }
        }

        public abstract boolean zzc(Object obj, long j2);

        public final int zzd(Object obj, long j2) {
            return this.f13235a.getInt(obj, j2);
        }

        public final long zze(Object obj, long j2) {
            return this.f13235a.getLong(obj, j2);
        }

        public final void zza(Object obj, long j2, long j3) {
            this.f13235a.putLong(obj, j2, j3);
        }

        public final boolean zza() {
            Unsafe unsafe = this.f13235a;
            if (unsafe == null) {
                return false;
            }
            try {
                Class<?> cls = unsafe.getClass();
                cls.getMethod("objectFieldOffset", Field.class);
                cls.getMethod("arrayBaseOffset", Class.class);
                cls.getMethod("arrayIndexScale", Class.class);
                Class<?> cls2 = Long.TYPE;
                cls.getMethod("getInt", Object.class, cls2);
                cls.getMethod("putInt", Object.class, cls2, Integer.TYPE);
                cls.getMethod("getLong", Object.class, cls2);
                cls.getMethod("putLong", Object.class, cls2, cls2);
                cls.getMethod("getObject", Object.class, cls2);
                cls.getMethod("putObject", Object.class, cls2, Object.class);
                return true;
            } catch (Throwable th) {
                zzpc.k(th);
                return false;
            }
        }
    }

    private static final class zzc extends zzb {
        zzc(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.google.android.gms.internal.measurement.zzpc.zzb
        public final double zza(Object obj, long j2) {
            return Double.longBitsToDouble(zze(obj, j2));
        }

        @Override // com.google.android.gms.internal.measurement.zzpc.zzb
        public final float zzb(Object obj, long j2) {
            return Float.intBitsToFloat(zzd(obj, j2));
        }

        @Override // com.google.android.gms.internal.measurement.zzpc.zzb
        public final boolean zzc(Object obj, long j2) {
            return zzpc.f13234a ? zzpc.w(obj, j2) : zzpc.x(obj, j2);
        }

        @Override // com.google.android.gms.internal.measurement.zzpc.zzb
        public final void zza(Object obj, long j2, boolean z2) {
            if (zzpc.f13234a) {
                zzpc.j(obj, j2, z2);
            } else {
                zzpc.p(obj, j2, z2);
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzpc.zzb
        public final void zza(Object obj, long j2, byte b2) {
            if (zzpc.f13234a) {
                zzpc.zzc(obj, j2, b2);
            } else {
                zzpc.zzd(obj, j2, b2);
            }
        }

        @Override // com.google.android.gms.internal.measurement.zzpc.zzb
        public final void zza(Object obj, long j2, double d2) {
            zza(obj, j2, Double.doubleToLongBits(d2));
        }

        @Override // com.google.android.gms.internal.measurement.zzpc.zzb
        public final void zza(Object obj, long j2, float f2) {
            zza(obj, j2, Float.floatToIntBits(f2));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x002e  */
    static {
        /*
            sun.misc.Unsafe r0 = n()
            com.google.android.gms.internal.measurement.zzpc.zzb = r0
            java.lang.Class r1 = com.google.android.gms.internal.measurement.zzkf.a()
            com.google.android.gms.internal.measurement.zzpc.zzc = r1
            java.lang.Class r1 = java.lang.Long.TYPE
            boolean r1 = zzd(r1)
            com.google.android.gms.internal.measurement.zzpc.zzd = r1
            java.lang.Class r2 = java.lang.Integer.TYPE
            boolean r2 = zzd(r2)
            com.google.android.gms.internal.measurement.zzpc.zze = r2
            if (r0 == 0) goto L2e
            if (r1 == 0) goto L26
            com.google.android.gms.internal.measurement.zzpc$zzc r1 = new com.google.android.gms.internal.measurement.zzpc$zzc
            r1.<init>(r0)
            goto L2f
        L26:
            if (r2 == 0) goto L2e
            com.google.android.gms.internal.measurement.zzpc$zza r1 = new com.google.android.gms.internal.measurement.zzpc$zza
            r1.<init>(r0)
            goto L2f
        L2e:
            r1 = 0
        L2f:
            com.google.android.gms.internal.measurement.zzpc.zzf = r1
            r0 = 0
            if (r1 != 0) goto L36
            r2 = r0
            goto L3a
        L36:
            boolean r2 = r1.zzb()
        L3a:
            com.google.android.gms.internal.measurement.zzpc.zzg = r2
            if (r1 != 0) goto L40
            r2 = r0
            goto L44
        L40:
            boolean r2 = r1.zza()
        L44:
            com.google.android.gms.internal.measurement.zzpc.zzh = r2
            java.lang.Class<byte[]> r2 = byte[].class
            int r2 = zzb(r2)
            long r2 = (long) r2
            com.google.android.gms.internal.measurement.zzpc.zzi = r2
            java.lang.Class<boolean[]> r4 = boolean[].class
            int r5 = zzb(r4)
            long r5 = (long) r5
            com.google.android.gms.internal.measurement.zzpc.zzj = r5
            int r4 = zzc(r4)
            long r4 = (long) r4
            com.google.android.gms.internal.measurement.zzpc.zzk = r4
            java.lang.Class<int[]> r4 = int[].class
            int r5 = zzb(r4)
            long r5 = (long) r5
            com.google.android.gms.internal.measurement.zzpc.zzl = r5
            int r4 = zzc(r4)
            long r4 = (long) r4
            com.google.android.gms.internal.measurement.zzpc.zzm = r4
            java.lang.Class<long[]> r4 = long[].class
            int r5 = zzb(r4)
            long r5 = (long) r5
            com.google.android.gms.internal.measurement.zzpc.zzn = r5
            int r4 = zzc(r4)
            long r4 = (long) r4
            com.google.android.gms.internal.measurement.zzpc.zzo = r4
            java.lang.Class<float[]> r4 = float[].class
            int r5 = zzb(r4)
            long r5 = (long) r5
            com.google.android.gms.internal.measurement.zzpc.zzp = r5
            int r4 = zzc(r4)
            long r4 = (long) r4
            com.google.android.gms.internal.measurement.zzpc.zzq = r4
            java.lang.Class<double[]> r4 = double[].class
            int r5 = zzb(r4)
            long r5 = (long) r5
            com.google.android.gms.internal.measurement.zzpc.zzr = r5
            int r4 = zzc(r4)
            long r4 = (long) r4
            com.google.android.gms.internal.measurement.zzpc.zzs = r4
            java.lang.Class<java.lang.Object[]> r4 = java.lang.Object[].class
            int r5 = zzb(r4)
            long r5 = (long) r5
            com.google.android.gms.internal.measurement.zzpc.zzt = r5
            int r4 = zzc(r4)
            long r4 = (long) r4
            com.google.android.gms.internal.measurement.zzpc.zzu = r4
            java.lang.reflect.Field r4 = zze()
            if (r4 == 0) goto Lbf
            if (r1 != 0) goto Lb8
            goto Lbf
        Lb8:
            sun.misc.Unsafe r1 = r1.f13235a
            long r4 = r1.objectFieldOffset(r4)
            goto Lc1
        Lbf:
            r4 = -1
        Lc1:
            com.google.android.gms.internal.measurement.zzpc.zzv = r4
            r4 = 7
            long r1 = r2 & r4
            int r1 = (int) r1
            com.google.android.gms.internal.measurement.zzpc.zzw = r1
            java.nio.ByteOrder r1 = java.nio.ByteOrder.nativeOrder()
            java.nio.ByteOrder r2 = java.nio.ByteOrder.BIG_ENDIAN
            if (r1 != r2) goto Ld3
            r0 = 1
        Ld3:
            com.google.android.gms.internal.measurement.zzpc.f13234a = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzpc.<clinit>():void");
    }

    private zzpc() {
    }

    static double a(Object obj, long j2) {
        return zzf.zza(obj, j2);
    }

    static Object b(Class cls) {
        try {
            return zzb.allocateInstance(cls);
        } catch (InstantiationException e2) {
            throw new IllegalStateException(e2);
        }
    }

    static void e(Object obj, long j2, double d2) {
        zzf.zza(obj, j2, d2);
    }

    static void f(Object obj, long j2, float f2) {
        zzf.zza(obj, j2, f2);
    }

    static void g(Object obj, long j2, int i2) {
        zzf.zza(obj, j2, i2);
    }

    static void h(Object obj, long j2, long j3) {
        zzf.zza(obj, j2, j3);
    }

    static void i(Object obj, long j2, Object obj2) {
        zzf.f13235a.putObject(obj, j2, obj2);
    }

    static /* synthetic */ void j(Object obj, long j2, boolean z2) {
        zzc(obj, j2, z2 ? (byte) 1 : (byte) 0);
    }

    static /* synthetic */ void k(Throwable th) {
        Logger.getLogger(zzpc.class.getName()).logp(Level.WARNING, "com.google.protobuf.UnsafeUtil", "logMissingMethod", "platform method missing - proto runtime falling back to safer methods: " + String.valueOf(th));
    }

    static void l(byte[] bArr, long j2, byte b2) {
        zzf.zza((Object) bArr, zzi + j2, b2);
    }

    static float m(Object obj, long j2) {
        return zzf.zzb(obj, j2);
    }

    static Unsafe n() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zzpe());
        } catch (Throwable unused) {
            return null;
        }
    }

    static /* synthetic */ void p(Object obj, long j2, boolean z2) {
        zzd(obj, j2, z2 ? (byte) 1 : (byte) 0);
    }

    static int q(Object obj, long j2) {
        return zzf.zzd(obj, j2);
    }

    static void r(Object obj, long j2, boolean z2) {
        zzf.zza(obj, j2, z2);
    }

    static boolean s() {
        return zzh;
    }

    static long t(Object obj, long j2) {
        return zzf.zze(obj, j2);
    }

    static boolean u() {
        return zzg;
    }

    static Object v(Object obj, long j2) {
        return zzf.f13235a.getObject(obj, j2);
    }

    static /* synthetic */ boolean w(Object obj, long j2) {
        return ((byte) (q(obj, (-4) & j2) >>> ((int) (((~j2) & 3) << 3)))) != 0;
    }

    static /* synthetic */ boolean x(Object obj, long j2) {
        return ((byte) (q(obj, (-4) & j2) >>> ((int) ((j2 & 3) << 3)))) != 0;
    }

    static boolean y(Object obj, long j2) {
        return zzf.zzc(obj, j2);
    }

    private static Field zza(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    private static int zzb(Class<?> cls) {
        if (zzh) {
            return zzf.f13235a.arrayBaseOffset(cls);
        }
        return -1;
    }

    private static int zzc(Class<?> cls) {
        if (zzh) {
            return zzf.f13235a.arrayIndexScale(cls);
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zzd(Object obj, long j2, byte b2) {
        long j3 = (-4) & j2;
        int i2 = (((int) j2) & 3) << 3;
        g(obj, j3, ((255 & b2) << i2) | (q(obj, j3) & (~(255 << i2))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Field zze() {
        Field fieldZza = zza(Buffer.class, "effectiveDirectAddress");
        if (fieldZza != null) {
            return fieldZza;
        }
        Field fieldZza2 = zza(Buffer.class, "address");
        if (fieldZza2 == null || fieldZza2.getType() != Long.TYPE) {
            return null;
        }
        return fieldZza2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zzc(Object obj, long j2, byte b2) {
        long j3 = (-4) & j2;
        int iQ = q(obj, j3);
        int i2 = ((~((int) j2)) & 3) << 3;
        g(obj, j3, ((255 & b2) << i2) | (iQ & (~(255 << i2))));
    }

    private static boolean zzd(Class<?> cls) {
        try {
            Class<?> cls2 = zzc;
            Class<?> cls3 = Boolean.TYPE;
            cls2.getMethod("peekLong", cls, cls3);
            cls2.getMethod("pokeLong", cls, Long.TYPE, cls3);
            Class<?> cls4 = Integer.TYPE;
            cls2.getMethod("pokeInt", cls, cls4, cls3);
            cls2.getMethod("peekInt", cls, cls3);
            cls2.getMethod("pokeByte", cls, Byte.TYPE);
            cls2.getMethod("peekByte", cls);
            cls2.getMethod("pokeByteArray", cls, byte[].class, cls4, cls4);
            cls2.getMethod("peekByteArray", cls, byte[].class, cls4, cls4);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }
}
