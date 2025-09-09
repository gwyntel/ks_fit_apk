package com.google.android.gms.common.internal;

import android.util.Log;
import androidx.annotation.Nullable;

/* loaded from: classes3.dex */
public abstract class zzc {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ BaseGmsClient f12852a;

    @Nullable
    private Object zza;
    private boolean zzb = false;

    public zzc(BaseGmsClient baseGmsClient, Object obj) {
        this.f12852a = baseGmsClient;
        this.zza = obj;
    }

    protected abstract void a(Object obj);

    protected abstract void b();

    public final void zze() {
        Object obj;
        synchronized (this) {
            try {
                obj = this.zza;
                if (this.zzb) {
                    Log.w("GmsClient", "Callback proxy " + toString() + " being reused. This is not safe.");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (obj != null) {
            a(obj);
        }
        synchronized (this) {
            this.zzb = true;
        }
        zzg();
    }

    public final void zzf() {
        synchronized (this) {
            this.zza = null;
        }
    }

    public final void zzg() {
        zzf();
        synchronized (this.f12852a.zzt) {
            this.f12852a.zzt.remove(this);
        }
    }
}
