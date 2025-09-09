package com.google.android.gms.internal.auth;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

/* loaded from: classes3.dex */
final class zzhj {

    /* renamed from: a, reason: collision with root package name */
    static final boolean f13028a;
    private static final Unsafe zzb;
    private static final Class zzc;
    private static final boolean zzd;
    private static final zzhi zze;
    private static final boolean zzf;
    private static final boolean zzg;

    /* JADX WARN: Removed duplicated region for block: B:11:0x003f  */
    static {
        /*
            Method dump skipped, instructions count: 303
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.auth.zzhj.<clinit>():void");
    }

    private zzhj() {
    }

    static double a(Object obj, long j2) {
        return zze.zza(obj, j2);
    }

    static float b(Object obj, long j2) {
        return zze.zzb(obj, j2);
    }

    static int c(Object obj, long j2) {
        return zze.f13027a.getInt(obj, j2);
    }

    static long d(Object obj, long j2) {
        return zze.f13027a.getLong(obj, j2);
    }

    static Object e(Class cls) {
        try {
            return zzb.allocateInstance(cls);
        } catch (InstantiationException e2) {
            throw new IllegalStateException(e2);
        }
    }

    static Object f(Object obj, long j2) {
        return zze.f13027a.getObject(obj, j2);
    }

    static Unsafe g() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zzhf());
        } catch (Throwable unused) {
            return null;
        }
    }

    static /* bridge */ /* synthetic */ void h(Throwable th) {
        Logger.getLogger(zzhj.class.getName()).logp(Level.WARNING, "com.google.protobuf.UnsafeUtil", "logMissingMethod", "platform method missing - proto runtime falling back to safer methods: ".concat(th.toString()));
    }

    static /* synthetic */ void i(Object obj, long j2, boolean z2) {
        zzhi zzhiVar = zze;
        long j3 = (-4) & j2;
        int i2 = zzhiVar.f13027a.getInt(obj, j3);
        int i3 = ((~((int) j2)) & 3) << 3;
        zzhiVar.f13027a.putInt(obj, j3, ((z2 ? 1 : 0) << i3) | ((~(255 << i3)) & i2));
    }

    static /* synthetic */ void j(Object obj, long j2, boolean z2) {
        zzhi zzhiVar = zze;
        long j3 = (-4) & j2;
        int i2 = (((int) j2) & 3) << 3;
        zzhiVar.f13027a.putInt(obj, j3, ((z2 ? 1 : 0) << i2) | ((~(255 << i2)) & zzhiVar.f13027a.getInt(obj, j3)));
    }

    static void k(Object obj, long j2, boolean z2) {
        zze.zzc(obj, j2, z2);
    }

    static void l(Object obj, long j2, double d2) {
        zze.zzd(obj, j2, d2);
    }

    static void m(Object obj, long j2, float f2) {
        zze.zze(obj, j2, f2);
    }

    static void n(Object obj, long j2, int i2) {
        zze.f13027a.putInt(obj, j2, i2);
    }

    static void o(Object obj, long j2, long j3) {
        zze.f13027a.putLong(obj, j2, j3);
    }

    static void p(Object obj, long j2, Object obj2) {
        zze.f13027a.putObject(obj, j2, obj2);
    }

    static /* bridge */ /* synthetic */ boolean q(Object obj, long j2) {
        return ((byte) ((zze.f13027a.getInt(obj, (-4) & j2) >>> ((int) (((~j2) & 3) << 3))) & 255)) != 0;
    }

    static /* bridge */ /* synthetic */ boolean r(Object obj, long j2) {
        return ((byte) ((zze.f13027a.getInt(obj, (-4) & j2) >>> ((int) ((j2 & 3) << 3))) & 255)) != 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static boolean s(Class cls) {
        int i2 = zzds.zza;
        try {
            Class cls2 = zzc;
            Class cls3 = Boolean.TYPE;
            cls2.getMethod("peekLong", cls, cls3);
            cls2.getMethod("pokeLong", cls, Long.TYPE, cls3);
            Class cls4 = Integer.TYPE;
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

    static boolean t(Object obj, long j2) {
        return zze.zzf(obj, j2);
    }

    static boolean u() {
        return zzg;
    }

    static boolean v() {
        return zzf;
    }

    private static int zzw(Class cls) {
        if (zzg) {
            return zze.f13027a.arrayBaseOffset(cls);
        }
        return -1;
    }

    private static int zzx(Class cls) {
        if (zzg) {
            return zze.f13027a.arrayIndexScale(cls);
        }
        return -1;
    }

    private static Field zzy() {
        int i2 = zzds.zza;
        Field fieldZzz = zzz(Buffer.class, "effectiveDirectAddress");
        if (fieldZzz != null) {
            return fieldZzz;
        }
        Field fieldZzz2 = zzz(Buffer.class, "address");
        if (fieldZzz2 == null || fieldZzz2.getType() != Long.TYPE) {
            return null;
        }
        return fieldZzz2;
    }

    private static Field zzz(Class cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (Throwable unused) {
            return null;
        }
    }
}
