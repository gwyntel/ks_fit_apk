package com.google.android.gms.internal.auth;

import com.google.protobuf.GeneratedMessage;

/* loaded from: classes3.dex */
final class zzgk {
    public static final /* synthetic */ int zza = 0;
    private static final Class zzb;
    private static final zzgz zzc;
    private static final zzgz zzd;

    static {
        Class<GeneratedMessage> cls;
        Class<?> cls2;
        zzgz zzgzVar = null;
        try {
            cls = GeneratedMessage.class;
            int i2 = GeneratedMessage.f15234a;
        } catch (Throwable unused) {
            cls = null;
        }
        zzb = cls;
        try {
            cls2 = Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable unused2) {
            cls2 = null;
        }
        if (cls2 != null) {
            try {
                zzgzVar = (zzgz) cls2.getConstructor(null).newInstance(null);
            } catch (Throwable unused3) {
            }
        }
        zzc = zzgzVar;
        zzd = new zzhb();
    }

    static Object a(Object obj, int i2, int i3, Object obj2, zzgz zzgzVar) {
        if (obj2 == null) {
            obj2 = zzgzVar.a(obj);
        }
        zzgzVar.d(obj2, i2, i3);
        return obj2;
    }

    static void b(zzgz zzgzVar, Object obj, Object obj2) {
        zzgzVar.f(obj, zzgzVar.c(zzgzVar.b(obj), zzgzVar.b(obj2)));
    }

    static boolean c(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    public static zzgz zza() {
        return zzc;
    }

    public static zzgz zzb() {
        return zzd;
    }

    public static void zze(Class cls) {
        Class cls2;
        if (!zzev.class.isAssignableFrom(cls) && (cls2 = zzb) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }
}
