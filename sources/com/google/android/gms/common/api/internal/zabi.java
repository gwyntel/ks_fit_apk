package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.kingsmith.miot.KsProperty;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import org.checkerframework.checker.initialization.qual.NotOnlyInitialized;

/* loaded from: classes3.dex */
public final class zabi implements zaca, zau {

    /* renamed from: a, reason: collision with root package name */
    final Map f12732a;

    /* renamed from: c, reason: collision with root package name */
    final ClientSettings f12734c;

    /* renamed from: d, reason: collision with root package name */
    final Map f12735d;

    /* renamed from: e, reason: collision with root package name */
    final Api.AbstractClientBuilder f12736e;

    /* renamed from: f, reason: collision with root package name */
    int f12737f;

    /* renamed from: g, reason: collision with root package name */
    final zabe f12738g;

    /* renamed from: h, reason: collision with root package name */
    final zabz f12739h;
    private final Lock zai;
    private final Condition zaj;
    private final Context zak;
    private final GoogleApiAvailabilityLight zal;
    private final zabh zam;

    @NotOnlyInitialized
    private volatile zabf zan;

    /* renamed from: b, reason: collision with root package name */
    final Map f12733b = new HashMap();

    @Nullable
    private ConnectionResult zao = null;

    public zabi(Context context, zabe zabeVar, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map map, @Nullable ClientSettings clientSettings, Map map2, @Nullable Api.AbstractClientBuilder abstractClientBuilder, ArrayList arrayList, zabz zabzVar) {
        this.zak = context;
        this.zai = lock;
        this.zal = googleApiAvailabilityLight;
        this.f12732a = map;
        this.f12734c = clientSettings;
        this.f12735d = map2;
        this.f12736e = abstractClientBuilder;
        this.f12738g = zabeVar;
        this.f12739h = zabzVar;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((zat) arrayList.get(i2)).zaa(this);
        }
        this.zam = new zabh(this, looper);
        this.zaj = lock.newCondition();
        this.zan = new zaax(this);
    }

    final void c() {
        this.zai.lock();
        try {
            this.f12738g.h();
            this.zan = new zaaj(this);
            this.zan.zad();
            this.zaj.signalAll();
        } finally {
            this.zai.unlock();
        }
    }

    final void d() {
        this.zai.lock();
        try {
            this.zan = new zaaw(this, this.f12734c, this.f12735d, this.zal, this.f12736e, this.zai, this.zak);
            this.zan.zad();
            this.zaj.signalAll();
        } finally {
            this.zai.unlock();
        }
    }

    final void e(ConnectionResult connectionResult) {
        this.zai.lock();
        try {
            this.zao = connectionResult;
            this.zan = new zaax(this);
            this.zan.zad();
            this.zaj.signalAll();
        } finally {
            this.zai.unlock();
        }
    }

    final void f(zabg zabgVar) {
        zabh zabhVar = this.zam;
        zabhVar.sendMessage(zabhVar.obtainMessage(1, zabgVar));
    }

    final void g(RuntimeException runtimeException) {
        zabh zabhVar = this.zam;
        zabhVar.sendMessage(zabhVar.obtainMessage(2, runtimeException));
    }

    @Override // com.google.android.gms.common.api.internal.ConnectionCallbacks
    public final void onConnected(@Nullable Bundle bundle) {
        this.zai.lock();
        try {
            this.zan.zag(bundle);
        } finally {
            this.zai.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.ConnectionCallbacks
    public final void onConnectionSuspended(int i2) {
        this.zai.lock();
        try {
            this.zan.zai(i2);
        } finally {
            this.zai.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zau
    public final void zaa(@NonNull ConnectionResult connectionResult, @NonNull Api api, boolean z2) {
        this.zai.lock();
        try {
            this.zan.zah(connectionResult, api, z2);
        } finally {
            this.zai.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zaca
    @GuardedBy(KsProperty.Lock)
    public final ConnectionResult zab() throws InterruptedException {
        zaq();
        while (this.zan instanceof zaaw) {
            try {
                this.zaj.await();
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
        }
        if (this.zan instanceof zaaj) {
            return ConnectionResult.RESULT_SUCCESS;
        }
        ConnectionResult connectionResult = this.zao;
        return connectionResult != null ? connectionResult : new ConnectionResult(13, null);
    }

    @Override // com.google.android.gms.common.api.internal.zaca
    @GuardedBy(KsProperty.Lock)
    public final ConnectionResult zac(long j2, TimeUnit timeUnit) throws InterruptedException {
        zaq();
        long nanos = timeUnit.toNanos(j2);
        while (this.zan instanceof zaaw) {
            if (nanos <= 0) {
                zar();
                return new ConnectionResult(14, null);
            }
            try {
                nanos = this.zaj.awaitNanos(nanos);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
            Thread.currentThread().interrupt();
            return new ConnectionResult(15, null);
        }
        if (this.zan instanceof zaaj) {
            return ConnectionResult.RESULT_SUCCESS;
        }
        ConnectionResult connectionResult = this.zao;
        return connectionResult != null ? connectionResult : new ConnectionResult(13, null);
    }

    @Override // com.google.android.gms.common.api.internal.zaca
    @Nullable
    @GuardedBy(KsProperty.Lock)
    public final ConnectionResult zad(@NonNull Api api) {
        Map map = this.f12732a;
        Api.AnyClientKey anyClientKeyZab = api.zab();
        if (!map.containsKey(anyClientKeyZab)) {
            return null;
        }
        if (((Api.Client) this.f12732a.get(anyClientKeyZab)).isConnected()) {
            return ConnectionResult.RESULT_SUCCESS;
        }
        if (this.f12733b.containsKey(anyClientKeyZab)) {
            return (ConnectionResult) this.f12733b.get(anyClientKeyZab);
        }
        return null;
    }

    @Override // com.google.android.gms.common.api.internal.zaca
    @GuardedBy(KsProperty.Lock)
    public final BaseImplementation.ApiMethodImpl zae(@NonNull BaseImplementation.ApiMethodImpl apiMethodImpl) {
        apiMethodImpl.zak();
        this.zan.zaa(apiMethodImpl);
        return apiMethodImpl;
    }

    @Override // com.google.android.gms.common.api.internal.zaca
    @GuardedBy(KsProperty.Lock)
    public final BaseImplementation.ApiMethodImpl zaf(@NonNull BaseImplementation.ApiMethodImpl apiMethodImpl) {
        apiMethodImpl.zak();
        return this.zan.zab(apiMethodImpl);
    }

    @Override // com.google.android.gms.common.api.internal.zaca
    @GuardedBy(KsProperty.Lock)
    public final void zaq() {
        this.zan.zae();
    }

    @Override // com.google.android.gms.common.api.internal.zaca
    @GuardedBy(KsProperty.Lock)
    public final void zar() {
        if (this.zan.zaj()) {
            this.f12733b.clear();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zaca
    public final void zas(String str, @Nullable FileDescriptor fileDescriptor, PrintWriter printWriter, @Nullable String[] strArr) {
        printWriter.append((CharSequence) str).append("mState=").println(this.zan);
        for (Api api : this.f12735d.keySet()) {
            String strValueOf = String.valueOf(str);
            printWriter.append((CharSequence) str).append((CharSequence) api.zad()).println(":");
            ((Api.Client) Preconditions.checkNotNull((Api.Client) this.f12732a.get(api.zab()))).dump(strValueOf.concat("  "), fileDescriptor, printWriter, strArr);
        }
    }

    @Override // com.google.android.gms.common.api.internal.zaca
    @GuardedBy(KsProperty.Lock)
    public final void zat() {
        if (this.zan instanceof zaaj) {
            ((zaaj) this.zan).b();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zaca
    public final void zau() {
    }

    @Override // com.google.android.gms.common.api.internal.zaca
    public final boolean zaw() {
        return this.zan instanceof zaaj;
    }

    @Override // com.google.android.gms.common.api.internal.zaca
    public final boolean zax() {
        return this.zan instanceof zaaw;
    }

    @Override // com.google.android.gms.common.api.internal.zaca
    public final boolean zay(SignInConnectionListener signInConnectionListener) {
        return false;
    }
}
