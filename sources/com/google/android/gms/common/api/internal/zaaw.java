package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.Preconditions;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.kingsmith.miot.KsProperty;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;

/* loaded from: classes3.dex */
public final class zaaw implements zabf {
    private final zabi zaa;
    private final Lock zab;
    private final Context zac;
    private final GoogleApiAvailabilityLight zad;

    @Nullable
    private ConnectionResult zae;
    private int zaf;
    private int zah;

    @Nullable
    private com.google.android.gms.signin.zae zak;
    private boolean zal;
    private boolean zam;
    private boolean zan;

    @Nullable
    private IAccountAccessor zao;
    private boolean zap;
    private boolean zaq;

    @Nullable
    private final ClientSettings zar;
    private final Map zas;

    @Nullable
    private final Api.AbstractClientBuilder zat;
    private int zag = 0;
    private final Bundle zai = new Bundle();
    private final Set zaj = new HashSet();
    private final ArrayList zau = new ArrayList();

    public zaaw(zabi zabiVar, @Nullable ClientSettings clientSettings, Map map, GoogleApiAvailabilityLight googleApiAvailabilityLight, @Nullable Api.AbstractClientBuilder abstractClientBuilder, Lock lock, Context context) {
        this.zaa = zabiVar;
        this.zar = clientSettings;
        this.zas = map;
        this.zad = googleApiAvailabilityLight;
        this.zat = abstractClientBuilder;
        this.zab = lock;
        this.zac = context;
    }

    static /* bridge */ /* synthetic */ Set g(zaaw zaawVar) {
        ClientSettings clientSettings = zaawVar.zar;
        if (clientSettings == null) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet(clientSettings.getRequiredScopes());
        Map mapZad = zaawVar.zar.zad();
        for (Api api : mapZad.keySet()) {
            zabi zabiVar = zaawVar.zaa;
            if (!zabiVar.f12733b.containsKey(api.zab())) {
                hashSet.addAll(((com.google.android.gms.common.internal.zab) mapZad.get(api)).zaa);
            }
        }
        return hashSet;
    }

    static /* bridge */ /* synthetic */ void j(zaaw zaawVar, com.google.android.gms.signin.internal.zak zakVar) {
        if (zaawVar.zaG(0)) {
            ConnectionResult connectionResultZaa = zakVar.zaa();
            if (!connectionResultZaa.isSuccess()) {
                if (!zaawVar.zaI(connectionResultZaa)) {
                    zaawVar.zaD(connectionResultZaa);
                    return;
                } else {
                    zaawVar.zaA();
                    zaawVar.zaF();
                    return;
                }
            }
            com.google.android.gms.common.internal.zav zavVar = (com.google.android.gms.common.internal.zav) Preconditions.checkNotNull(zakVar.zab());
            ConnectionResult connectionResultZaa2 = zavVar.zaa();
            if (!connectionResultZaa2.isSuccess()) {
                String strValueOf = String.valueOf(connectionResultZaa2);
                Log.wtf("GACConnecting", "Sign-in succeeded with resolve account failure: ".concat(strValueOf), new Exception());
                zaawVar.zaD(connectionResultZaa2);
                return;
            }
            zaawVar.zan = true;
            zaawVar.zao = (IAccountAccessor) Preconditions.checkNotNull(zavVar.zab());
            zaawVar.zap = zavVar.zac();
            zaawVar.zaq = zavVar.zad();
            zaawVar.zaF();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy(KsProperty.Lock)
    public final void zaA() {
        this.zam = false;
        this.zaa.f12738g.f12725d = Collections.emptySet();
        for (Api.AnyClientKey anyClientKey : this.zaj) {
            if (!this.zaa.f12733b.containsKey(anyClientKey)) {
                zabi zabiVar = this.zaa;
                zabiVar.f12733b.put(anyClientKey, new ConnectionResult(17, null));
            }
        }
    }

    @GuardedBy(KsProperty.Lock)
    private final void zaB(boolean z2) {
        com.google.android.gms.signin.zae zaeVar = this.zak;
        if (zaeVar != null) {
            if (zaeVar.isConnected() && z2) {
                zaeVar.zaa();
            }
            zaeVar.disconnect();
            this.zao = null;
        }
    }

    @GuardedBy(KsProperty.Lock)
    private final void zaC() {
        this.zaa.c();
        zabj.zaa().execute(new zaak(this));
        com.google.android.gms.signin.zae zaeVar = this.zak;
        if (zaeVar != null) {
            if (this.zap) {
                zaeVar.zac((IAccountAccessor) Preconditions.checkNotNull(this.zao), this.zaq);
            }
            zaB(false);
        }
        Iterator it = this.zaa.f12733b.keySet().iterator();
        while (it.hasNext()) {
            ((Api.Client) Preconditions.checkNotNull((Api.Client) this.zaa.f12732a.get((Api.AnyClientKey) it.next()))).disconnect();
        }
        this.zaa.f12739h.zab(this.zai.isEmpty() ? null : this.zai);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy(KsProperty.Lock)
    public final void zaD(ConnectionResult connectionResult) {
        zaz();
        zaB(!connectionResult.hasResolution());
        this.zaa.e(connectionResult);
        this.zaa.f12739h.zaa(connectionResult);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy(KsProperty.Lock)
    public final void zaE(ConnectionResult connectionResult, Api api, boolean z2) {
        int priority = api.zac().getPriority();
        if ((!z2 || connectionResult.hasResolution() || this.zad.getErrorResolutionIntent(connectionResult.getErrorCode()) != null) && (this.zae == null || priority < this.zaf)) {
            this.zae = connectionResult;
            this.zaf = priority;
        }
        zabi zabiVar = this.zaa;
        zabiVar.f12733b.put(api.zab(), connectionResult);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy(KsProperty.Lock)
    public final void zaF() {
        if (this.zah != 0) {
            return;
        }
        if (!this.zam || this.zan) {
            ArrayList arrayList = new ArrayList();
            this.zag = 1;
            this.zah = this.zaa.f12732a.size();
            for (Api.AnyClientKey anyClientKey : this.zaa.f12732a.keySet()) {
                if (!this.zaa.f12733b.containsKey(anyClientKey)) {
                    arrayList.add((Api.Client) this.zaa.f12732a.get(anyClientKey));
                } else if (zaH()) {
                    zaC();
                }
            }
            if (arrayList.isEmpty()) {
                return;
            }
            this.zau.add(zabj.zaa().submit(new zaap(this, arrayList)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy(KsProperty.Lock)
    public final boolean zaG(int i2) {
        if (this.zag == i2) {
            return true;
        }
        Log.w("GACConnecting", this.zaa.f12738g.c());
        Log.w("GACConnecting", "Unexpected callback in ".concat(toString()));
        Log.w("GACConnecting", "mRemainingConnections=" + this.zah);
        Log.e("GACConnecting", "GoogleApiClient connecting is in step " + zaJ(this.zag) + " but received callback for step " + zaJ(i2), new Exception());
        zaD(new ConnectionResult(8, null));
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy(KsProperty.Lock)
    public final boolean zaH() {
        int i2 = this.zah - 1;
        this.zah = i2;
        if (i2 > 0) {
            return false;
        }
        if (i2 < 0) {
            Log.w("GACConnecting", this.zaa.f12738g.c());
            Log.wtf("GACConnecting", "GoogleApiClient received too many callbacks for the given step. Clients may be in an unexpected state; GoogleApiClient will now disconnect.", new Exception());
            zaD(new ConnectionResult(8, null));
            return false;
        }
        ConnectionResult connectionResult = this.zae;
        if (connectionResult == null) {
            return true;
        }
        this.zaa.f12737f = this.zaf;
        zaD(connectionResult);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy(KsProperty.Lock)
    public final boolean zaI(ConnectionResult connectionResult) {
        return this.zal && !connectionResult.hasResolution();
    }

    private static final String zaJ(int i2) {
        return i2 != 0 ? "STEP_GETTING_REMOTE_SERVICE" : "STEP_SERVICE_BINDINGS_AND_SIGN_IN";
    }

    private final void zaz() {
        ArrayList arrayList = this.zau;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((Future) arrayList.get(i2)).cancel(true);
        }
        this.zau.clear();
    }

    @Override // com.google.android.gms.common.api.internal.zabf
    public final BaseImplementation.ApiMethodImpl zaa(BaseImplementation.ApiMethodImpl apiMethodImpl) {
        this.zaa.f12738g.f12722a.add(apiMethodImpl);
        return apiMethodImpl;
    }

    @Override // com.google.android.gms.common.api.internal.zabf
    public final BaseImplementation.ApiMethodImpl zab(BaseImplementation.ApiMethodImpl apiMethodImpl) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }

    /* JADX WARN: Type inference failed for: r0v13, types: [com.google.android.gms.common.api.Api$Client, com.google.android.gms.signin.zae] */
    @Override // com.google.android.gms.common.api.internal.zabf
    @GuardedBy(KsProperty.Lock)
    public final void zad() {
        this.zaa.f12733b.clear();
        this.zam = false;
        zaas zaasVar = null;
        this.zae = null;
        this.zag = 0;
        this.zal = true;
        this.zan = false;
        this.zap = false;
        HashMap map = new HashMap();
        boolean z2 = false;
        for (Api api : this.zas.keySet()) {
            Api.Client client = (Api.Client) Preconditions.checkNotNull((Api.Client) this.zaa.f12732a.get(api.zab()));
            z2 |= api.zac().getPriority() == 1;
            boolean zBooleanValue = ((Boolean) this.zas.get(api)).booleanValue();
            if (client.requiresSignIn()) {
                this.zam = true;
                if (zBooleanValue) {
                    this.zaj.add(api.zab());
                } else {
                    this.zal = false;
                }
            }
            map.put(client, new zaal(this, api, zBooleanValue));
        }
        if (z2) {
            this.zam = false;
        }
        if (this.zam) {
            Preconditions.checkNotNull(this.zar);
            Preconditions.checkNotNull(this.zat);
            this.zar.zae(Integer.valueOf(System.identityHashCode(this.zaa.f12738g)));
            zaat zaatVar = new zaat(this, zaasVar);
            Api.AbstractClientBuilder abstractClientBuilder = this.zat;
            Context context = this.zac;
            zabi zabiVar = this.zaa;
            ClientSettings clientSettings = this.zar;
            this.zak = abstractClientBuilder.buildClient(context, zabiVar.f12738g.getLooper(), clientSettings, (ClientSettings) clientSettings.zaa(), (GoogleApiClient.ConnectionCallbacks) zaatVar, (GoogleApiClient.OnConnectionFailedListener) zaatVar);
        }
        this.zah = this.zaa.f12732a.size();
        this.zau.add(zabj.zaa().submit(new zaao(this, map)));
    }

    @Override // com.google.android.gms.common.api.internal.zabf
    public final void zae() {
    }

    @Override // com.google.android.gms.common.api.internal.zabf
    @GuardedBy(KsProperty.Lock)
    public final void zag(@Nullable Bundle bundle) {
        if (zaG(1)) {
            if (bundle != null) {
                this.zai.putAll(bundle);
            }
            if (zaH()) {
                zaC();
            }
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabf
    @GuardedBy(KsProperty.Lock)
    public final void zah(ConnectionResult connectionResult, Api api, boolean z2) {
        if (zaG(1)) {
            zaE(connectionResult, api, z2);
            if (zaH()) {
                zaC();
            }
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabf
    @GuardedBy(KsProperty.Lock)
    public final void zai(int i2) {
        zaD(new ConnectionResult(8, null));
    }

    @Override // com.google.android.gms.common.api.internal.zabf
    @GuardedBy(KsProperty.Lock)
    public final boolean zaj() {
        zaz();
        zaB(true);
        this.zaa.e(null);
        return true;
    }
}
