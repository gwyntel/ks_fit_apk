package com.google.android.gms.common.api.internal;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.UnsupportedApiCallException;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import org.checkerframework.checker.initialization.qual.NotOnlyInitialized;

/* loaded from: classes3.dex */
public final class zabq implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, zau {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ GoogleApiManager f12746a;

    @NotOnlyInitialized
    private final Api.Client zac;
    private final ApiKey zad;
    private final zaad zae;
    private final int zah;

    @Nullable
    private final zact zai;
    private boolean zaj;
    private final Queue zab = new LinkedList();
    private final Set zaf = new HashSet();
    private final Map zag = new HashMap();
    private final List zak = new ArrayList();

    @Nullable
    private ConnectionResult zal = null;
    private int zam = 0;

    @WorkerThread
    public zabq(GoogleApiManager googleApiManager, GoogleApi googleApi) {
        this.f12746a = googleApiManager;
        Api.Client clientZab = googleApi.zab(googleApiManager.zar.getLooper(), this);
        this.zac = clientZab;
        this.zad = googleApi.getApiKey();
        this.zae = new zaad();
        this.zah = googleApi.zaa();
        if (clientZab.requiresSignIn()) {
            this.zai = googleApi.zac(googleApiManager.zai, googleApiManager.zar);
        } else {
            this.zai = null;
        }
    }

    static /* bridge */ /* synthetic */ void g(zabq zabqVar, zabs zabsVar) {
        if (zabqVar.zak.contains(zabsVar) && !zabqVar.zaj) {
            if (zabqVar.zac.isConnected()) {
                zabqVar.zaG();
            } else {
                zabqVar.zao();
            }
        }
    }

    static /* bridge */ /* synthetic */ void h(zabq zabqVar, zabs zabsVar) {
        Feature[] featureArrZab;
        if (zabqVar.zak.remove(zabsVar)) {
            zabqVar.f12746a.zar.removeMessages(15, zabsVar);
            zabqVar.f12746a.zar.removeMessages(16, zabsVar);
            Feature feature = zabsVar.zab;
            ArrayList arrayList = new ArrayList(zabqVar.zab.size());
            for (zai zaiVar : zabqVar.zab) {
                if ((zaiVar instanceof zac) && (featureArrZab = ((zac) zaiVar).zab(zabqVar)) != null && ArrayUtils.contains(featureArrZab, feature)) {
                    arrayList.add(zaiVar);
                }
            }
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                zai zaiVar2 = (zai) arrayList.get(i2);
                zabqVar.zab.remove(zaiVar2);
                zaiVar2.zae(new UnsupportedApiCallException(feature));
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    @WorkerThread
    private final Feature zaC(@Nullable Feature[] featureArr) {
        if (featureArr != null && featureArr.length != 0) {
            Feature[] availableFeatures = this.zac.getAvailableFeatures();
            if (availableFeatures == null) {
                availableFeatures = new Feature[0];
            }
            ArrayMap arrayMap = new ArrayMap(availableFeatures.length);
            for (Feature feature : availableFeatures) {
                arrayMap.put(feature.getName(), Long.valueOf(feature.getVersion()));
            }
            for (Feature feature2 : featureArr) {
                Long l2 = (Long) arrayMap.get(feature2.getName());
                if (l2 == null || l2.longValue() < feature2.getVersion()) {
                    return feature2;
                }
            }
        }
        return null;
    }

    @WorkerThread
    private final void zaD(ConnectionResult connectionResult) {
        Iterator it = this.zaf.iterator();
        while (it.hasNext()) {
            ((zal) it.next()).zac(this.zad, connectionResult, Objects.equal(connectionResult, ConnectionResult.RESULT_SUCCESS) ? this.zac.getEndpointPackageName() : null);
        }
        this.zaf.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zaE(Status status) {
        Preconditions.checkHandlerThread(this.f12746a.zar);
        zaF(status, null, false);
    }

    @WorkerThread
    private final void zaF(@Nullable Status status, @Nullable Exception exc, boolean z2) {
        Preconditions.checkHandlerThread(this.f12746a.zar);
        if ((status == null) == (exc == null)) {
            throw new IllegalArgumentException("Status XOR exception should be null");
        }
        Iterator it = this.zab.iterator();
        while (it.hasNext()) {
            zai zaiVar = (zai) it.next();
            if (!z2 || zaiVar.zac == 2) {
                if (status != null) {
                    zaiVar.zad(status);
                } else {
                    zaiVar.zae(exc);
                }
                it.remove();
            }
        }
    }

    @WorkerThread
    private final void zaG() {
        ArrayList arrayList = new ArrayList(this.zab);
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            zai zaiVar = (zai) arrayList.get(i2);
            if (!this.zac.isConnected()) {
                return;
            }
            if (zaM(zaiVar)) {
                this.zab.remove(zaiVar);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zaH() {
        zan();
        zaD(ConnectionResult.RESULT_SUCCESS);
        zaL();
        Iterator it = this.zag.values().iterator();
        while (it.hasNext()) {
            zaci zaciVar = (zaci) it.next();
            if (zaC(zaciVar.zaa.getRequiredFeatures()) != null) {
                it.remove();
            } else {
                try {
                    zaciVar.zaa.a(this.zac, new TaskCompletionSource());
                } catch (DeadObjectException unused) {
                    onConnectionSuspended(3);
                    this.zac.disconnect("DeadObjectException thrown while calling register listener method.");
                } catch (RemoteException unused2) {
                    it.remove();
                }
            }
        }
        zaG();
        zaJ();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zaI(int i2) {
        zan();
        this.zaj = true;
        this.zae.e(i2, this.zac.getLastDisconnectMessage());
        ApiKey apiKey = this.zad;
        GoogleApiManager googleApiManager = this.f12746a;
        googleApiManager.zar.sendMessageDelayed(Message.obtain(googleApiManager.zar, 9, apiKey), 5000L);
        ApiKey apiKey2 = this.zad;
        GoogleApiManager googleApiManager2 = this.f12746a;
        googleApiManager2.zar.sendMessageDelayed(Message.obtain(googleApiManager2.zar, 11, apiKey2), 120000L);
        this.f12746a.zak.zac();
        Iterator it = this.zag.values().iterator();
        while (it.hasNext()) {
            ((zaci) it.next()).zac.run();
        }
    }

    private final void zaJ() {
        this.f12746a.zar.removeMessages(12, this.zad);
        ApiKey apiKey = this.zad;
        GoogleApiManager googleApiManager = this.f12746a;
        googleApiManager.zar.sendMessageDelayed(googleApiManager.zar.obtainMessage(12, apiKey), this.f12746a.zae);
    }

    @WorkerThread
    private final void zaK(zai zaiVar) {
        zaiVar.zag(this.zae, zaA());
        try {
            zaiVar.zaf(this);
        } catch (DeadObjectException unused) {
            onConnectionSuspended(1);
            this.zac.disconnect("DeadObjectException thrown while running ApiCallRunner.");
        }
    }

    @WorkerThread
    private final void zaL() {
        if (this.zaj) {
            GoogleApiManager googleApiManager = this.f12746a;
            googleApiManager.zar.removeMessages(11, this.zad);
            GoogleApiManager googleApiManager2 = this.f12746a;
            googleApiManager2.zar.removeMessages(9, this.zad);
            this.zaj = false;
        }
    }

    @WorkerThread
    private final boolean zaM(zai zaiVar) throws Resources.NotFoundException {
        if (!(zaiVar instanceof zac)) {
            zaK(zaiVar);
            return true;
        }
        zac zacVar = (zac) zaiVar;
        Feature featureZaC = zaC(zacVar.zab(this));
        if (featureZaC == null) {
            zaK(zaiVar);
            return true;
        }
        Log.w("GoogleApiManager", this.zac.getClass().getName() + " could not execute call because it requires feature (" + featureZaC.getName() + ", " + featureZaC.getVersion() + ").");
        if (!this.f12746a.zas || !zacVar.zaa(this)) {
            zacVar.zae(new UnsupportedApiCallException(featureZaC));
            return true;
        }
        zabs zabsVar = new zabs(this.zad, featureZaC, null);
        int iIndexOf = this.zak.indexOf(zabsVar);
        if (iIndexOf >= 0) {
            zabs zabsVar2 = (zabs) this.zak.get(iIndexOf);
            this.f12746a.zar.removeMessages(15, zabsVar2);
            GoogleApiManager googleApiManager = this.f12746a;
            googleApiManager.zar.sendMessageDelayed(Message.obtain(googleApiManager.zar, 15, zabsVar2), 5000L);
            return false;
        }
        this.zak.add(zabsVar);
        GoogleApiManager googleApiManager2 = this.f12746a;
        googleApiManager2.zar.sendMessageDelayed(Message.obtain(googleApiManager2.zar, 15, zabsVar), 5000L);
        GoogleApiManager googleApiManager3 = this.f12746a;
        googleApiManager3.zar.sendMessageDelayed(Message.obtain(googleApiManager3.zar, 16, zabsVar), 120000L);
        ConnectionResult connectionResult = new ConnectionResult(2, null);
        if (zaN(connectionResult)) {
            return false;
        }
        this.f12746a.d(connectionResult, this.zah);
        return false;
    }

    @WorkerThread
    private final boolean zaN(@NonNull ConnectionResult connectionResult) {
        synchronized (GoogleApiManager.zac) {
            try {
                GoogleApiManager googleApiManager = this.f12746a;
                if (googleApiManager.zao == null || !googleApiManager.zap.contains(this.zad)) {
                    return false;
                }
                this.f12746a.zao.zah(connectionResult, this.zah);
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final boolean zaO(boolean z2) {
        Preconditions.checkHandlerThread(this.f12746a.zar);
        if (!this.zac.isConnected() || !this.zag.isEmpty()) {
            return false;
        }
        if (!this.zae.f()) {
            this.zac.disconnect("Timing out service connection.");
            return true;
        }
        if (!z2) {
            return false;
        }
        zaJ();
        return false;
    }

    final int a() {
        return this.zam;
    }

    final void i() {
        this.zam++;
    }

    final boolean l() {
        return this.zac.isConnected();
    }

    @Override // com.google.android.gms.common.api.internal.ConnectionCallbacks
    public final void onConnected(@Nullable Bundle bundle) {
        if (Looper.myLooper() == this.f12746a.zar.getLooper()) {
            zaH();
        } else {
            this.f12746a.zar.post(new zabm(this));
        }
    }

    @Override // com.google.android.gms.common.api.internal.OnConnectionFailedListener
    @WorkerThread
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        zar(connectionResult, null);
    }

    @Override // com.google.android.gms.common.api.internal.ConnectionCallbacks
    public final void onConnectionSuspended(int i2) {
        if (Looper.myLooper() == this.f12746a.zar.getLooper()) {
            zaI(i2);
        } else {
            this.f12746a.zar.post(new zabn(this, i2));
        }
    }

    public final boolean zaA() {
        return this.zac.requiresSignIn();
    }

    @ResultIgnorabilityUnspecified
    @WorkerThread
    public final boolean zaB() {
        return zaO(true);
    }

    @Override // com.google.android.gms.common.api.internal.zau
    public final void zaa(ConnectionResult connectionResult, Api api, boolean z2) {
        throw null;
    }

    public final int zab() {
        return this.zah;
    }

    @Nullable
    @WorkerThread
    public final ConnectionResult zad() {
        Preconditions.checkHandlerThread(this.f12746a.zar);
        return this.zal;
    }

    public final Api.Client zaf() {
        return this.zac;
    }

    public final Map zah() {
        return this.zag;
    }

    @WorkerThread
    public final void zan() {
        Preconditions.checkHandlerThread(this.f12746a.zar);
        this.zal = null;
    }

    @WorkerThread
    public final void zao() {
        Preconditions.checkHandlerThread(this.f12746a.zar);
        if (this.zac.isConnected() || this.zac.isConnecting()) {
            return;
        }
        try {
            GoogleApiManager googleApiManager = this.f12746a;
            int iZab = googleApiManager.zak.zab(googleApiManager.zai, this.zac);
            if (iZab == 0) {
                GoogleApiManager googleApiManager2 = this.f12746a;
                Api.Client client = this.zac;
                zabu zabuVar = new zabu(googleApiManager2, client, this.zad);
                if (client.requiresSignIn()) {
                    ((zact) Preconditions.checkNotNull(this.zai)).zae(zabuVar);
                }
                try {
                    this.zac.connect(zabuVar);
                    return;
                } catch (SecurityException e2) {
                    zar(new ConnectionResult(10), e2);
                    return;
                }
            }
            ConnectionResult connectionResult = new ConnectionResult(iZab, null);
            Log.w("GoogleApiManager", "The service for " + this.zac.getClass().getName() + " is not available: " + connectionResult.toString());
            zar(connectionResult, null);
        } catch (IllegalStateException e3) {
            zar(new ConnectionResult(10), e3);
        }
    }

    @WorkerThread
    public final void zap(zai zaiVar) {
        Preconditions.checkHandlerThread(this.f12746a.zar);
        if (this.zac.isConnected()) {
            if (zaM(zaiVar)) {
                zaJ();
                return;
            } else {
                this.zab.add(zaiVar);
                return;
            }
        }
        this.zab.add(zaiVar);
        ConnectionResult connectionResult = this.zal;
        if (connectionResult == null || !connectionResult.hasResolution()) {
            zao();
        } else {
            zar(this.zal, null);
        }
    }

    @WorkerThread
    public final void zar(@NonNull ConnectionResult connectionResult, @Nullable Exception exc) {
        Preconditions.checkHandlerThread(this.f12746a.zar);
        zact zactVar = this.zai;
        if (zactVar != null) {
            zactVar.zaf();
        }
        zan();
        this.f12746a.zak.zac();
        zaD(connectionResult);
        if ((this.zac instanceof com.google.android.gms.common.internal.service.zap) && connectionResult.getErrorCode() != 24) {
            this.f12746a.zaf = true;
            GoogleApiManager googleApiManager = this.f12746a;
            googleApiManager.zar.sendMessageDelayed(googleApiManager.zar.obtainMessage(19), 300000L);
        }
        if (connectionResult.getErrorCode() == 4) {
            zaE(GoogleApiManager.zab);
            return;
        }
        if (this.zab.isEmpty()) {
            this.zal = connectionResult;
            return;
        }
        if (exc != null) {
            Preconditions.checkHandlerThread(this.f12746a.zar);
            zaF(null, exc, false);
            return;
        }
        if (!this.f12746a.zas) {
            zaE(GoogleApiManager.zaF(this.zad, connectionResult));
            return;
        }
        zaF(GoogleApiManager.zaF(this.zad, connectionResult), null, true);
        if (this.zab.isEmpty() || zaN(connectionResult) || this.f12746a.d(connectionResult, this.zah)) {
            return;
        }
        if (connectionResult.getErrorCode() == 18) {
            this.zaj = true;
        }
        if (!this.zaj) {
            zaE(GoogleApiManager.zaF(this.zad, connectionResult));
            return;
        }
        GoogleApiManager googleApiManager2 = this.f12746a;
        googleApiManager2.zar.sendMessageDelayed(Message.obtain(googleApiManager2.zar, 9, this.zad), 5000L);
    }

    @WorkerThread
    public final void zas(@NonNull ConnectionResult connectionResult) {
        Preconditions.checkHandlerThread(this.f12746a.zar);
        Api.Client client = this.zac;
        client.disconnect("onSignInFailed for " + client.getClass().getName() + " with " + String.valueOf(connectionResult));
        zar(connectionResult, null);
    }

    @WorkerThread
    public final void zat(zal zalVar) {
        Preconditions.checkHandlerThread(this.f12746a.zar);
        this.zaf.add(zalVar);
    }

    @WorkerThread
    public final void zau() {
        Preconditions.checkHandlerThread(this.f12746a.zar);
        if (this.zaj) {
            zao();
        }
    }

    @WorkerThread
    public final void zav() {
        Preconditions.checkHandlerThread(this.f12746a.zar);
        zaE(GoogleApiManager.zaa);
        this.zae.zaf();
        for (ListenerHolder.ListenerKey listenerKey : (ListenerHolder.ListenerKey[]) this.zag.keySet().toArray(new ListenerHolder.ListenerKey[0])) {
            zap(new zah(listenerKey, new TaskCompletionSource()));
        }
        zaD(new ConnectionResult(4));
        if (this.zac.isConnected()) {
            this.zac.onUserSignOut(new zabp(this));
        }
    }

    @WorkerThread
    public final void zaw() {
        Preconditions.checkHandlerThread(this.f12746a.zar);
        if (this.zaj) {
            zaL();
            GoogleApiManager googleApiManager = this.f12746a;
            zaE(googleApiManager.zaj.isGooglePlayServicesAvailable(googleApiManager.zai) == 18 ? new Status(21, "Connection timed out waiting for Google Play services update to complete.") : new Status(22, "API failed to connect while resuming due to an unknown error."));
            this.zac.disconnect("Timing out connection while resuming.");
        }
    }
}
