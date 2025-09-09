package com.google.android.gms.internal.auth;

import com.google.android.gms.internal.auth.zzet;
import com.google.android.gms.internal.auth.zzev;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public abstract class zzev<MessageType extends zzev<MessageType, BuilderType>, BuilderType extends zzet<MessageType, BuilderType>> extends zzdq<MessageType, BuilderType> {
    private static final Map zzb = new ConcurrentHashMap();
    private int zzd = -1;
    protected zzha zzc = zzha.zza();

    static zzev b(Class cls) throws ClassNotFoundException {
        Map map = zzb;
        zzev zzevVar = (zzev) map.get(cls);
        if (zzevVar == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                zzevVar = (zzev) map.get(cls);
            } catch (ClassNotFoundException e2) {
                throw new IllegalStateException("Class initialization cannot fail.", e2);
            }
        }
        if (zzevVar == null) {
            zzevVar = (zzev) ((zzev) zzhj.e(cls)).m(6, null, null);
            if (zzevVar == null) {
                throw new IllegalStateException();
            }
            map.put(cls, zzevVar);
        }
        return zzevVar;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0034, code lost:
    
        if (r1 != false) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected static com.google.android.gms.internal.auth.zzev d(com.google.android.gms.internal.auth.zzev r3, byte[] r4) throws com.google.android.gms.internal.auth.zzfb {
        /*
            int r0 = r4.length
            com.google.android.gms.internal.auth.zzel r1 = com.google.android.gms.internal.auth.zzel.f13013a
            r2 = 0
            com.google.android.gms.internal.auth.zzev r3 = zzo(r3, r4, r2, r0, r1)
            if (r3 == 0) goto L44
            r4 = 1
            r0 = 0
            java.lang.Object r1 = r3.m(r4, r0, r0)
            java.lang.Byte r1 = (java.lang.Byte) r1
            byte r1 = r1.byteValue()
            if (r1 != r4) goto L19
            goto L44
        L19:
            if (r1 == 0) goto L37
            java.lang.Class r1 = r3.getClass()
            com.google.android.gms.internal.auth.zzgf r2 = com.google.android.gms.internal.auth.zzgf.zza()
            com.google.android.gms.internal.auth.zzgi r1 = r2.zzb(r1)
            boolean r1 = r1.zzi(r3)
            if (r4 == r1) goto L2f
            r4 = r0
            goto L30
        L2f:
            r4 = r3
        L30:
            r2 = 2
            r3.m(r2, r4, r0)
            if (r1 == 0) goto L37
            goto L44
        L37:
            com.google.android.gms.internal.auth.zzgy r4 = new com.google.android.gms.internal.auth.zzgy
            r4.<init>(r3)
            com.google.android.gms.internal.auth.zzfb r4 = r4.zza()
            r4.zze(r3)
            throw r4
        L44:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.auth.zzev.d(com.google.android.gms.internal.auth.zzev, byte[]):com.google.android.gms.internal.auth.zzev");
    }

    protected static zzez e() {
        return zzgg.zze();
    }

    static Object f(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e2);
        } catch (InvocationTargetException e3) {
            Throwable cause = e3.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            if (cause instanceof Error) {
                throw ((Error) cause);
            }
            throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
        }
    }

    protected static Object g(zzfx zzfxVar, String str, Object[] objArr) {
        return new zzgh(zzfxVar, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001a", objArr);
    }

    protected static void j(Class cls, zzev zzevVar) {
        zzevVar.i();
        zzb.put(cls, zzevVar);
    }

    private static zzev zzo(zzev zzevVar, byte[] bArr, int i2, int i3, zzel zzelVar) throws zzfb {
        zzev zzevVarC = zzevVar.c();
        try {
            zzgi zzgiVarZzb = zzgf.zza().zzb(zzevVarC.getClass());
            zzgiVarZzb.zzg(zzevVarC, bArr, 0, i3, new zzdt(zzelVar));
            zzgiVarZzb.zze(zzevVarC);
            return zzevVarC;
        } catch (zzfb e2) {
            e2.zze(zzevVarC);
            throw e2;
        } catch (zzgy e3) {
            zzfb zzfbVarZza = e3.zza();
            zzfbVarZza.zze(zzevVarC);
            throw zzfbVarZza;
        } catch (IOException e4) {
            if (e4.getCause() instanceof zzfb) {
                throw ((zzfb) e4.getCause());
            }
            zzfb zzfbVar = new zzfb(e4);
            zzfbVar.zze(zzevVarC);
            throw zzfbVar;
        } catch (IndexOutOfBoundsException unused) {
            zzfb zzfbVarZzf = zzfb.zzf();
            zzfbVarZzf.zze(zzevVarC);
            throw zzfbVarZzf;
        }
    }

    final int a() {
        return zzgf.zza().zzb(getClass()).zza(this);
    }

    final zzev c() {
        return (zzev) m(4, null, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return zzgf.zza().zzb(getClass()).zzh(this, (zzev) obj);
    }

    protected final void h() {
        zzgf.zza().zzb(getClass()).zze(this);
        i();
    }

    public final int hashCode() {
        if (l()) {
            return a();
        }
        int i2 = this.zza;
        if (i2 != 0) {
            return i2;
        }
        int iA = a();
        this.zza = iA;
        return iA;
    }

    final void i() {
        this.zzd &= Integer.MAX_VALUE;
    }

    final void k(int i2) {
        this.zzd = (this.zzd & Integer.MIN_VALUE) | Integer.MAX_VALUE;
    }

    final boolean l() {
        return (this.zzd & Integer.MIN_VALUE) != 0;
    }

    protected abstract Object m(int i2, Object obj, Object obj2);

    public final String toString() {
        return zzfz.a(this, super.toString());
    }

    @Override // com.google.android.gms.internal.auth.zzfy
    public final /* synthetic */ zzfx zze() {
        return (zzev) m(6, null, null);
    }
}
