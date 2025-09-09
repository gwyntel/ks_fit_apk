package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzlw;
import com.google.android.gms.internal.measurement.zzlw.zza;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public abstract class zzlw<MessageType extends zzlw<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzkc<MessageType, BuilderType> {
    private static Map<Object, zzlw<?, ?>> zzc = new ConcurrentHashMap();
    private int zzd = -1;
    protected zzoz zzb = zzoz.zzc();

    public static abstract class zza<MessageType extends zzlw<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzkb<MessageType, BuilderType> {

        /* renamed from: a, reason: collision with root package name */
        protected zzlw f13230a;
        private final MessageType zzb;

        /* JADX WARN: Multi-variable type inference failed */
        protected zza(zzlw zzlwVar) {
            this.zzb = zzlwVar;
            if (zzlwVar.t()) {
                throw new IllegalArgumentException("Default instance must be immutable.");
            }
            this.f13230a = zzlwVar.n();
        }

        private final BuilderType zzb(byte[] bArr, int i2, int i3, zzlj zzljVar) throws zzme {
            if (!this.f13230a.t()) {
                b();
            }
            try {
                zznx.zza().zza((zznx) this.f13230a).zza(this.f13230a, bArr, 0, i3, new zzkh(zzljVar));
                return this;
            } catch (zzme e2) {
                throw e2;
            } catch (IOException e3) {
                throw new RuntimeException("Reading from byte array should not throw IOException.", e3);
            } catch (IndexOutOfBoundsException unused) {
                throw zzme.zzh();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @Override // com.google.android.gms.internal.measurement.zzkb
        /* renamed from: zzc, reason: merged with bridge method [inline-methods] */
        public final BuilderType zzb(zzkx zzkxVar, zzlj zzljVar) throws IOException {
            if (!this.f13230a.t()) {
                b();
            }
            try {
                zznx.zza().zza((zznx) this.f13230a).zza(this.f13230a, zzlb.zza(zzkxVar), zzljVar);
                return this;
            } catch (RuntimeException e2) {
                if (e2.getCause() instanceof IOException) {
                    throw ((IOException) e2.getCause());
                }
                throw e2;
            }
        }

        protected final void a() {
            if (this.f13230a.t()) {
                return;
            }
            b();
        }

        protected void b() {
            zzlw zzlwVarN = this.zzb.n();
            zza(zzlwVarN, this.f13230a);
            this.f13230a = zzlwVarN;
        }

        @Override // com.google.android.gms.internal.measurement.zzkb
        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            zza zzaVar = (zza) this.zzb.h(zzf.zze, null, null);
            zzaVar.f13230a = (zzlw) zzac();
            return zzaVar;
        }

        @Override // com.google.android.gms.internal.measurement.zznl
        public final /* synthetic */ zznj h_() {
            return this.zzb;
        }

        @Override // com.google.android.gms.internal.measurement.zznl
        public final boolean i_() {
            return zzlw.l(this.f13230a, false);
        }

        @Override // com.google.android.gms.internal.measurement.zzkb
        /* renamed from: zza */
        public final /* synthetic */ zzkb zzb(zzkx zzkxVar, zzlj zzljVar) throws IOException {
            return (zza) zzb(zzkxVar, zzljVar);
        }

        @Override // com.google.android.gms.internal.measurement.zzni
        /* renamed from: zzaa, reason: merged with bridge method [inline-methods] */
        public MessageType zzac() {
            if (!this.f13230a.t()) {
                return (MessageType) this.f13230a;
            }
            this.f13230a.r();
            return (MessageType) this.f13230a;
        }

        @Override // com.google.android.gms.internal.measurement.zzkb
        /* renamed from: zzy */
        public final /* synthetic */ zzkb clone() {
            return (zza) clone();
        }

        @Override // com.google.android.gms.internal.measurement.zzni
        /* renamed from: zzz, reason: merged with bridge method [inline-methods] */
        public final MessageType zzab() {
            MessageType messagetype = (MessageType) zzac();
            if (messagetype.i_()) {
                return messagetype;
            }
            throw new zzox(messagetype);
        }

        @Override // com.google.android.gms.internal.measurement.zzkb
        public final /* synthetic */ zzkb zza(byte[] bArr, int i2, int i3) throws zzme {
            return zzb(bArr, 0, i3, zzlj.f13225a);
        }

        @Override // com.google.android.gms.internal.measurement.zzkb
        public final /* synthetic */ zzkb zza(byte[] bArr, int i2, int i3, zzlj zzljVar) throws zzme {
            return zzb(bArr, 0, i3, zzljVar);
        }

        public final BuilderType zza(MessageType messagetype) {
            if (this.zzb.equals(messagetype)) {
                return this;
            }
            if (!this.f13230a.t()) {
                b();
            }
            zza(this.f13230a, messagetype);
            return this;
        }

        private static <MessageType> void zza(MessageType messagetype, MessageType messagetype2) {
            zznx.zza().zza((zznx) messagetype).zza(messagetype, messagetype2);
        }
    }

    public static abstract class zzb<MessageType extends zzb<MessageType, BuilderType>, BuilderType> extends zzlw<MessageType, BuilderType> implements zznl {
        protected zzlm<zze> zzc = zzlm.zzb();

        final zzlm u() {
            if (this.zzc.zzf()) {
                this.zzc = (zzlm) this.zzc.clone();
            }
            return this.zzc;
        }
    }

    protected static class zzc<T extends zzlw<T, ?>> extends zzkd<T> {
        private final T zza;

        public zzc(T t2) {
            this.zza = t2;
        }
    }

    public static class zzd<ContainingType extends zznj, Type> extends zzlh<ContainingType, Type> {
    }

    static final class zze implements zzlo<zze> {
        @Override // java.lang.Comparable
        public final /* synthetic */ int compareTo(Object obj) {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.measurement.zzlo
        public final int zza() {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.measurement.zzlo
        public final zzpj zzb() {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.measurement.zzlo
        public final zzpt zzc() {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.measurement.zzlo
        public final boolean zzd() {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.measurement.zzlo
        public final boolean zze() {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.measurement.zzlo
        public final zzni zza(zzni zzniVar, zznj zznjVar) {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.measurement.zzlo
        public final zzno zza(zzno zznoVar, zzno zznoVar2) {
            throw new NoSuchMethodError();
        }
    }

    public enum zzf {
        public static final int zza = 1;
        public static final int zzb = 2;
        public static final int zzc = 3;
        public static final int zzd = 4;
        public static final int zze = 5;
        public static final int zzf = 6;
        public static final int zzg = 7;
        private static final /* synthetic */ int[] zzh = {1, 2, 3, 4, 5, 6, 7};

        public static int[] zza() {
            return (int[]) zzh.clone();
        }
    }

    static zzlw e(Class cls) throws ClassNotFoundException {
        zzlw<?, ?> zzlwVar = zzc.get(cls);
        if (zzlwVar == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                zzlwVar = zzc.get(cls);
            } catch (ClassNotFoundException e2) {
                throw new IllegalStateException("Class initialization cannot fail.", e2);
            }
        }
        if (zzlwVar == null) {
            zzlwVar = (zzlw) ((zzlw) zzpc.b(cls)).h(zzf.zzf, null, null);
            if (zzlwVar == null) {
                throw new IllegalStateException();
            }
            zzc.put(cls, zzlwVar);
        }
        return zzlwVar;
    }

    protected static zzmc f(zzmc zzmcVar) {
        int size = zzmcVar.size();
        return zzmcVar.zza(size == 0 ? 10 : size << 1);
    }

    protected static zzmf g(zzmf zzmfVar) {
        int size = zzmfVar.size();
        return zzmfVar.zza(size == 0 ? 10 : size << 1);
    }

    protected static Object i(zznj zznjVar, String str, Object[] objArr) {
        return new zznz(zznjVar, str, objArr);
    }

    static Object j(Method method, Object obj, Object... objArr) {
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

    protected static void k(Class cls, zzlw zzlwVar) {
        zzlwVar.s();
        zzc.put(cls, zzlwVar);
    }

    protected static final boolean l(zzlw zzlwVar, boolean z2) {
        byte bByteValue = ((Byte) zzlwVar.h(zzf.zza, null, null)).byteValue();
        if (bByteValue == 1) {
            return true;
        }
        if (bByteValue == 0) {
            return false;
        }
        boolean zZzd = zznx.zza().zza((zznx) zzlwVar).zzd(zzlwVar);
        if (z2) {
            zzlwVar.h(zzf.zzb, zZzd ? zzlwVar : null, null);
        }
        return zZzd;
    }

    protected static zzmd o() {
        return zzlx.zzd();
    }

    protected static zzmc p() {
        return zzmu.zzd();
    }

    protected static zzmf q() {
        return zznw.zzd();
    }

    private final int zza() {
        return zznx.zza().zza((zznx) this).zzb(this);
    }

    private final int zzb(zzob<?> zzobVar) {
        return zzobVar == null ? zznx.zza().zza((zznx) this).zza(this) : zzobVar.zza(this);
    }

    @Override // com.google.android.gms.internal.measurement.zzkc
    final int a(zzob zzobVar) {
        if (!t()) {
            if (c() != Integer.MAX_VALUE) {
                return c();
            }
            int iZzb = zzb(zzobVar);
            d(iZzb);
            return iZzb;
        }
        int iZzb2 = zzb(zzobVar);
        if (iZzb2 >= 0) {
            return iZzb2;
        }
        throw new IllegalStateException("serialized size must be non-negative, was " + iZzb2);
    }

    @Override // com.google.android.gms.internal.measurement.zzkc
    final int c() {
        return this.zzd & Integer.MAX_VALUE;
    }

    @Override // com.google.android.gms.internal.measurement.zzkc
    final void d(int i2) {
        if (i2 >= 0) {
            this.zzd = (i2 & Integer.MAX_VALUE) | (this.zzd & Integer.MIN_VALUE);
        } else {
            throw new IllegalStateException("serialized size must be non-negative, was " + i2);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return zznx.zza().zza((zznx) this).zzb(this, (zzlw) obj);
        }
        return false;
    }

    protected abstract Object h(int i2, Object obj, Object obj2);

    @Override // com.google.android.gms.internal.measurement.zznl
    public final /* synthetic */ zznj h_() {
        return (zzlw) h(zzf.zzf, null, null);
    }

    public int hashCode() {
        if (t()) {
            return zza();
        }
        if (this.zza == 0) {
            this.zza = zza();
        }
        return this.zza;
    }

    @Override // com.google.android.gms.internal.measurement.zznl
    public final boolean i_() {
        return l(this, true);
    }

    protected final zza m() {
        return (zza) h(zzf.zze, null, null);
    }

    final zzlw n() {
        return (zzlw) h(zzf.zzd, null, null);
    }

    protected final void r() {
        zznx.zza().zza((zznx) this).zzc(this);
        s();
    }

    final void s() {
        this.zzd &= Integer.MAX_VALUE;
    }

    final boolean t() {
        return (this.zzd & Integer.MIN_VALUE) != 0;
    }

    public String toString() {
        return zznk.a(this, super.toString());
    }

    @Override // com.google.android.gms.internal.measurement.zznj
    public final int zzbw() {
        return a(null);
    }

    public final BuilderType zzby() {
        return (BuilderType) ((zza) h(zzf.zze, null, null)).zza((zza) this);
    }

    @Override // com.google.android.gms.internal.measurement.zznj
    public final /* synthetic */ zzni zzcd() {
        return (zza) h(zzf.zze, null, null);
    }

    @Override // com.google.android.gms.internal.measurement.zznj
    public final /* synthetic */ zzni zzce() {
        return ((zza) h(zzf.zze, null, null)).zza((zza) this);
    }

    @Override // com.google.android.gms.internal.measurement.zznj
    public final void zza(zzld zzldVar) throws IOException {
        zznx.zza().zza((zznx) this).zza((zzob) this, (zzpw) zzlf.zza(zzldVar));
    }
}
