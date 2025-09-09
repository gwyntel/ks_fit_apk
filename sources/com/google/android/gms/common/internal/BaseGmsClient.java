package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Scope;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.kingsmith.miot.KsProperty;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

@KeepForSdk
/* loaded from: classes3.dex */
public abstract class BaseGmsClient<T extends IInterface> {

    @KeepForSdk
    public static final int CONNECT_STATE_CONNECTED = 4;

    @KeepForSdk
    public static final int CONNECT_STATE_DISCONNECTED = 1;

    @KeepForSdk
    public static final int CONNECT_STATE_DISCONNECTING = 5;

    @NonNull
    @KeepForSdk
    public static final String DEFAULT_ACCOUNT = "<<default account>>";

    @NonNull
    @KeepForSdk
    public static final String KEY_PENDING_INTENT = "pendingIntent";

    /* renamed from: a, reason: collision with root package name */
    zzv f12808a;

    /* renamed from: b, reason: collision with root package name */
    final Handler f12809b;

    /* renamed from: c, reason: collision with root package name */
    protected ConnectionProgressReportCallbacks f12810c;

    /* renamed from: d, reason: collision with root package name */
    protected AtomicInteger f12811d;

    @Nullable
    private volatile String zzA;

    @Nullable
    private ConnectionResult zzB;
    private boolean zzC;

    @Nullable
    private volatile zzk zzD;
    private int zzf;
    private long zzg;
    private long zzh;
    private int zzi;
    private long zzj;

    @Nullable
    private volatile String zzk;
    private final Context zzl;
    private final Looper zzm;
    private final GmsClientSupervisor zzn;
    private final GoogleApiAvailabilityLight zzo;
    private final Object zzp;
    private final Object zzq;

    @Nullable
    @GuardedBy("serviceBrokerLock")
    private IGmsServiceBroker zzr;

    @Nullable
    @GuardedBy(KsProperty.Lock)
    private IInterface zzs;
    private final ArrayList zzt;

    @Nullable
    @GuardedBy(KsProperty.Lock)
    private zze zzu;

    @GuardedBy(KsProperty.Lock)
    private int zzv;

    @Nullable
    private final BaseConnectionCallbacks zzw;

    @Nullable
    private final BaseOnConnectionFailedListener zzx;
    private final int zzy;

    @Nullable
    private final String zzz;
    private static final Feature[] zze = new Feature[0];

    @NonNull
    @KeepForSdk
    public static final String[] GOOGLE_PLUS_REQUIRED_FEATURES = {"service_esmobile", "service_googleme"};

    @KeepForSdk
    public interface BaseConnectionCallbacks {

        @KeepForSdk
        public static final int CAUSE_DEAD_OBJECT_EXCEPTION = 3;

        @KeepForSdk
        public static final int CAUSE_SERVICE_DISCONNECTED = 1;

        @KeepForSdk
        void onConnected(@Nullable Bundle bundle);

        @KeepForSdk
        void onConnectionSuspended(int i2);
    }

    @KeepForSdk
    public interface BaseOnConnectionFailedListener {
        @KeepForSdk
        void onConnectionFailed(@NonNull ConnectionResult connectionResult);
    }

    @KeepForSdk
    public interface ConnectionProgressReportCallbacks {
        @KeepForSdk
        void onReportServiceBinding(@NonNull ConnectionResult connectionResult);
    }

    protected class LegacyClientCallbackAdapter implements ConnectionProgressReportCallbacks {
        @KeepForSdk
        public LegacyClientCallbackAdapter() {
        }

        @Override // com.google.android.gms.common.internal.BaseGmsClient.ConnectionProgressReportCallbacks
        public final void onReportServiceBinding(@NonNull ConnectionResult connectionResult) {
            if (connectionResult.isSuccess()) {
                BaseGmsClient baseGmsClient = BaseGmsClient.this;
                baseGmsClient.getRemoteService(null, baseGmsClient.f());
            } else if (BaseGmsClient.this.zzx != null) {
                BaseGmsClient.this.zzx.onConnectionFailed(connectionResult);
            }
        }
    }

    @KeepForSdk
    public interface SignOutCallbacks {
        @KeepForSdk
        void onSignOutComplete();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    protected BaseGmsClient(Context context, Looper looper, int i2, BaseConnectionCallbacks baseConnectionCallbacks, BaseOnConnectionFailedListener baseOnConnectionFailedListener, String str) {
        GmsClientSupervisor gmsClientSupervisor = GmsClientSupervisor.getInstance(context);
        GoogleApiAvailabilityLight googleApiAvailabilityLight = GoogleApiAvailabilityLight.getInstance();
        Preconditions.checkNotNull(baseConnectionCallbacks);
        Preconditions.checkNotNull(baseOnConnectionFailedListener);
        this(context, looper, gmsClientSupervisor, googleApiAvailabilityLight, i2, baseConnectionCallbacks, baseOnConnectionFailedListener, str);
    }

    static /* bridge */ /* synthetic */ boolean A(BaseGmsClient baseGmsClient) throws ClassNotFoundException {
        if (baseGmsClient.zzC || TextUtils.isEmpty(baseGmsClient.getServiceDescriptor()) || TextUtils.isEmpty(baseGmsClient.e())) {
            return false;
        }
        try {
            Class.forName(baseGmsClient.getServiceDescriptor());
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    static /* bridge */ /* synthetic */ void v(BaseGmsClient baseGmsClient, zzk zzkVar) {
        baseGmsClient.zzD = zzkVar;
        if (baseGmsClient.usesClientTelemetry()) {
            ConnectionTelemetryConfiguration connectionTelemetryConfiguration = zzkVar.f12860d;
            RootTelemetryConfigManager.getInstance().zza(connectionTelemetryConfiguration == null ? null : connectionTelemetryConfiguration.zza());
        }
    }

    static /* bridge */ /* synthetic */ void w(BaseGmsClient baseGmsClient, int i2) {
        int i3;
        int i4;
        synchronized (baseGmsClient.zzp) {
            i3 = baseGmsClient.zzv;
        }
        if (i3 == 3) {
            baseGmsClient.zzC = true;
            i4 = 5;
        } else {
            i4 = 4;
        }
        Handler handler = baseGmsClient.f12809b;
        handler.sendMessage(handler.obtainMessage(i4, baseGmsClient.f12811d.get(), 16));
    }

    static /* bridge */ /* synthetic */ boolean z(BaseGmsClient baseGmsClient, int i2, int i3, IInterface iInterface) {
        synchronized (baseGmsClient.zzp) {
            try {
                if (baseGmsClient.zzv != i2) {
                    return false;
                }
                baseGmsClient.zzp(i3, iInterface);
                return true;
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzp(int i2, @Nullable IInterface iInterface) {
        zzv zzvVar;
        Preconditions.checkArgument((i2 == 4) == (iInterface != null));
        synchronized (this.zzp) {
            try {
                this.zzv = i2;
                this.zzs = iInterface;
                if (i2 == 1) {
                    zze zzeVar = this.zzu;
                    if (zzeVar != null) {
                        GmsClientSupervisor gmsClientSupervisor = this.zzn;
                        String strB = this.f12808a.b();
                        Preconditions.checkNotNull(strB);
                        gmsClientSupervisor.zzb(strB, this.f12808a.a(), 4225, zzeVar, q(), this.f12808a.c());
                        this.zzu = null;
                    }
                } else if (i2 == 2 || i2 == 3) {
                    zze zzeVar2 = this.zzu;
                    if (zzeVar2 != null && (zzvVar = this.f12808a) != null) {
                        Log.e("GmsClient", "Calling connect() while still connected, missing disconnect() for " + zzvVar.b() + " on " + zzvVar.a());
                        GmsClientSupervisor gmsClientSupervisor2 = this.zzn;
                        String strB2 = this.f12808a.b();
                        Preconditions.checkNotNull(strB2);
                        gmsClientSupervisor2.zzb(strB2, this.f12808a.a(), 4225, zzeVar2, q(), this.f12808a.c());
                        this.f12811d.incrementAndGet();
                    }
                    zze zzeVar3 = new zze(this, this.f12811d.get());
                    this.zzu = zzeVar3;
                    zzv zzvVar2 = (this.zzv != 3 || e() == null) ? new zzv(g(), getStartServiceAction(), false, 4225, h()) : new zzv(getContext().getPackageName(), e(), true, 4225, false);
                    this.f12808a = zzvVar2;
                    if (zzvVar2.c() && getMinApkVersion() < 17895000) {
                        throw new IllegalStateException("Internal Error, the minimum apk version of this BaseGmsClient is too low to support dynamic lookup. Start service action: ".concat(String.valueOf(this.f12808a.b())));
                    }
                    GmsClientSupervisor gmsClientSupervisor3 = this.zzn;
                    String strB3 = this.f12808a.b();
                    Preconditions.checkNotNull(strB3);
                    if (!gmsClientSupervisor3.b(new zzo(strB3, this.f12808a.a(), 4225, this.f12808a.c()), zzeVar3, q(), c())) {
                        Log.w("GmsClient", "unable to connect to service: " + this.f12808a.b() + " on " + this.f12808a.a());
                        x(16, null, this.f12811d.get());
                    }
                } else if (i2 == 4) {
                    Preconditions.checkNotNull(iInterface);
                    i(iInterface);
                }
            } finally {
            }
        }
    }

    protected final void a() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    protected boolean b() {
        return false;
    }

    protected Executor c() {
        return null;
    }

    @KeepForSdk
    public void checkAvailabilityAndConnect() {
        int iIsGooglePlayServicesAvailable = this.zzo.isGooglePlayServicesAvailable(this.zzl, getMinApkVersion());
        if (iIsGooglePlayServicesAvailable == 0) {
            connect(new LegacyClientCallbackAdapter());
        } else {
            zzp(1, null);
            l(new LegacyClientCallbackAdapter(), iIsGooglePlayServicesAvailable, null);
        }
    }

    @KeepForSdk
    public void connect(@NonNull ConnectionProgressReportCallbacks connectionProgressReportCallbacks) {
        Preconditions.checkNotNull(connectionProgressReportCallbacks, "Connection progress callbacks cannot be null.");
        this.f12810c = connectionProgressReportCallbacks;
        zzp(2, null);
    }

    protected abstract IInterface createServiceInterface(IBinder iBinder);

    protected Bundle d() {
        return new Bundle();
    }

    @KeepForSdk
    public void disconnect() {
        this.f12811d.incrementAndGet();
        synchronized (this.zzt) {
            try {
                int size = this.zzt.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((zzc) this.zzt.get(i2)).zzf();
                }
                this.zzt.clear();
            } catch (Throwable th) {
                throw th;
            }
        }
        synchronized (this.zzq) {
            this.zzr = null;
        }
        zzp(1, null);
    }

    @KeepForSdk
    public void dump(@NonNull String str, @NonNull FileDescriptor fileDescriptor, @NonNull PrintWriter printWriter, @NonNull String[] strArr) {
        int i2;
        IInterface iInterface;
        IGmsServiceBroker iGmsServiceBroker;
        synchronized (this.zzp) {
            i2 = this.zzv;
            iInterface = this.zzs;
        }
        synchronized (this.zzq) {
            iGmsServiceBroker = this.zzr;
        }
        printWriter.append((CharSequence) str).append("mConnectState=");
        if (i2 == 1) {
            printWriter.print("DISCONNECTED");
        } else if (i2 == 2) {
            printWriter.print("REMOTE_CONNECTING");
        } else if (i2 == 3) {
            printWriter.print("LOCAL_CONNECTING");
        } else if (i2 == 4) {
            printWriter.print("CONNECTED");
        } else if (i2 != 5) {
            printWriter.print("UNKNOWN");
        } else {
            printWriter.print("DISCONNECTING");
        }
        printWriter.append(" mService=");
        if (iInterface == null) {
            printWriter.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            printWriter.append((CharSequence) getServiceDescriptor()).append("@").append((CharSequence) Integer.toHexString(System.identityHashCode(iInterface.asBinder())));
        }
        printWriter.append(" mServiceBroker=");
        if (iGmsServiceBroker == null) {
            printWriter.println(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            printWriter.append("IGmsServiceBroker@").println(Integer.toHexString(System.identityHashCode(iGmsServiceBroker.asBinder())));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        if (this.zzh > 0) {
            PrintWriter printWriterAppend = printWriter.append((CharSequence) str).append("lastConnectedTime=");
            long j2 = this.zzh;
            printWriterAppend.println(j2 + " " + simpleDateFormat.format(new Date(j2)));
        }
        if (this.zzg > 0) {
            printWriter.append((CharSequence) str).append("lastSuspendedCause=");
            int i3 = this.zzf;
            if (i3 == 1) {
                printWriter.append("CAUSE_SERVICE_DISCONNECTED");
            } else if (i3 == 2) {
                printWriter.append("CAUSE_NETWORK_LOST");
            } else if (i3 != 3) {
                printWriter.append((CharSequence) String.valueOf(i3));
            } else {
                printWriter.append("CAUSE_DEAD_OBJECT_EXCEPTION");
            }
            PrintWriter printWriterAppend2 = printWriter.append(" lastSuspendedTime=");
            long j3 = this.zzg;
            printWriterAppend2.println(j3 + " " + simpleDateFormat.format(new Date(j3)));
        }
        if (this.zzj > 0) {
            printWriter.append((CharSequence) str).append("lastFailedStatus=").append((CharSequence) CommonStatusCodes.getStatusCodeString(this.zzi));
            PrintWriter printWriterAppend3 = printWriter.append(" lastFailedTime=");
            long j4 = this.zzj;
            printWriterAppend3.println(j4 + " " + simpleDateFormat.format(new Date(j4)));
        }
    }

    protected String e() {
        return null;
    }

    protected Set f() {
        return Collections.emptySet();
    }

    protected String g() {
        return "com.google.android.gms";
    }

    @Nullable
    @KeepForSdk
    public Account getAccount() {
        return null;
    }

    @NonNull
    @KeepForSdk
    public Feature[] getApiFeatures() {
        return zze;
    }

    @Nullable
    @KeepForSdk
    public final Feature[] getAvailableFeatures() {
        zzk zzkVar = this.zzD;
        if (zzkVar == null) {
            return null;
        }
        return zzkVar.f12858b;
    }

    @Nullable
    @KeepForSdk
    public Bundle getConnectionHint() {
        return null;
    }

    @NonNull
    @KeepForSdk
    public final Context getContext() {
        return this.zzl;
    }

    @NonNull
    @KeepForSdk
    public String getEndpointPackageName() {
        zzv zzvVar;
        if (!isConnected() || (zzvVar = this.f12808a) == null) {
            throw new RuntimeException("Failed to connect when checking package");
        }
        return zzvVar.a();
    }

    @KeepForSdk
    public int getGCoreServiceId() {
        return this.zzy;
    }

    @Nullable
    @KeepForSdk
    public String getLastDisconnectMessage() {
        return this.zzk;
    }

    @NonNull
    @KeepForSdk
    public final Looper getLooper() {
        return this.zzm;
    }

    @KeepForSdk
    public int getMinApkVersion() {
        return GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    }

    @KeepForSdk
    @WorkerThread
    public void getRemoteService(@Nullable IAccountAccessor iAccountAccessor, @NonNull Set<Scope> set) {
        Bundle bundleD = d();
        String str = this.zzA;
        int i2 = GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
        Scope[] scopeArr = GetServiceRequest.f12813n;
        Bundle bundle = new Bundle();
        int i3 = this.zzy;
        Feature[] featureArr = GetServiceRequest.f12814o;
        GetServiceRequest getServiceRequest = new GetServiceRequest(6, i3, i2, null, null, scopeArr, bundle, null, featureArr, featureArr, true, 0, false, str);
        getServiceRequest.f12818d = this.zzl.getPackageName();
        getServiceRequest.f12821g = bundleD;
        if (set != null) {
            getServiceRequest.f12820f = (Scope[]) set.toArray(new Scope[0]);
        }
        if (requiresSignIn()) {
            Account account = getAccount();
            if (account == null) {
                account = new Account("<<default account>>", "com.google");
            }
            getServiceRequest.f12822h = account;
            if (iAccountAccessor != null) {
                getServiceRequest.f12819e = iAccountAccessor.asBinder();
            }
        } else if (requiresAccount()) {
            getServiceRequest.f12822h = getAccount();
        }
        getServiceRequest.f12823i = zze;
        getServiceRequest.f12824j = getApiFeatures();
        if (usesClientTelemetry()) {
            getServiceRequest.f12827m = true;
        }
        try {
            synchronized (this.zzq) {
                try {
                    IGmsServiceBroker iGmsServiceBroker = this.zzr;
                    if (iGmsServiceBroker != null) {
                        iGmsServiceBroker.getService(new zzd(this, this.f12811d.get()), getServiceRequest);
                    } else {
                        Log.w("GmsClient", "mServiceBroker is null, client disconnected");
                    }
                } finally {
                }
            }
        } catch (DeadObjectException e2) {
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e2);
            triggerConnectionSuspended(3);
        } catch (RemoteException e3) {
            e = e3;
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e);
            k(8, null, null, this.f12811d.get());
        } catch (SecurityException e4) {
            throw e4;
        } catch (RuntimeException e5) {
            e = e5;
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e);
            k(8, null, null, this.f12811d.get());
        }
    }

    @NonNull
    @KeepForSdk
    public final T getService() throws DeadObjectException {
        T t2;
        synchronized (this.zzp) {
            try {
                if (this.zzv == 5) {
                    throw new DeadObjectException();
                }
                a();
                t2 = (T) this.zzs;
                Preconditions.checkNotNull(t2, "Client is connected but service is null");
            } catch (Throwable th) {
                throw th;
            }
        }
        return t2;
    }

    @Nullable
    @KeepForSdk
    public IBinder getServiceBrokerBinder() {
        synchronized (this.zzq) {
            try {
                IGmsServiceBroker iGmsServiceBroker = this.zzr;
                if (iGmsServiceBroker == null) {
                    return null;
                }
                return iGmsServiceBroker.asBinder();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    protected abstract String getServiceDescriptor();

    @NonNull
    @KeepForSdk
    public Intent getSignInIntent() {
        throw new UnsupportedOperationException("Not a sign in API");
    }

    protected abstract String getStartServiceAction();

    @Nullable
    @KeepForSdk
    public ConnectionTelemetryConfiguration getTelemetryConfiguration() {
        zzk zzkVar = this.zzD;
        if (zzkVar == null) {
            return null;
        }
        return zzkVar.f12860d;
    }

    protected boolean h() {
        return getMinApkVersion() >= 211700000;
    }

    @KeepForSdk
    public boolean hasConnectionInfo() {
        return this.zzD != null;
    }

    protected void i(IInterface iInterface) {
        this.zzh = System.currentTimeMillis();
    }

    @KeepForSdk
    public boolean isConnected() {
        boolean z2;
        synchronized (this.zzp) {
            z2 = this.zzv == 4;
        }
        return z2;
    }

    @KeepForSdk
    public boolean isConnecting() {
        boolean z2;
        synchronized (this.zzp) {
            int i2 = this.zzv;
            z2 = true;
            if (i2 != 2 && i2 != 3) {
                z2 = false;
            }
        }
        return z2;
    }

    protected void j(ConnectionResult connectionResult) {
        this.zzi = connectionResult.getErrorCode();
        this.zzj = System.currentTimeMillis();
    }

    protected void k(int i2, IBinder iBinder, Bundle bundle, int i3) {
        this.f12809b.sendMessage(this.f12809b.obtainMessage(1, i3, -1, new zzf(this, i2, iBinder, bundle)));
    }

    protected void l(ConnectionProgressReportCallbacks connectionProgressReportCallbacks, int i2, PendingIntent pendingIntent) {
        Preconditions.checkNotNull(connectionProgressReportCallbacks, "Connection progress callbacks cannot be null.");
        this.f12810c = connectionProgressReportCallbacks;
        this.f12809b.sendMessage(this.f12809b.obtainMessage(3, this.f12811d.get(), i2, pendingIntent));
    }

    protected void onConnectionSuspended(int i2) {
        this.zzf = i2;
        this.zzg = System.currentTimeMillis();
    }

    @KeepForSdk
    public void onUserSignOut(@NonNull SignOutCallbacks signOutCallbacks) {
        signOutCallbacks.onSignOutComplete();
    }

    @KeepForSdk
    public boolean providesSignIn() {
        return false;
    }

    protected final String q() {
        String str = this.zzz;
        return str == null ? this.zzl.getClass().getName() : str;
    }

    @KeepForSdk
    public boolean requiresAccount() {
        return false;
    }

    @KeepForSdk
    public boolean requiresGooglePlayServices() {
        return true;
    }

    @KeepForSdk
    public boolean requiresSignIn() {
        return false;
    }

    @KeepForSdk
    public void setAttributionTag(@NonNull String str) {
        this.zzA = str;
    }

    @KeepForSdk
    public void triggerConnectionSuspended(int i2) {
        this.f12809b.sendMessage(this.f12809b.obtainMessage(6, this.f12811d.get(), i2));
    }

    @KeepForSdk
    public boolean usesClientTelemetry() {
        return false;
    }

    protected final void x(int i2, Bundle bundle, int i3) {
        this.f12809b.sendMessage(this.f12809b.obtainMessage(7, i3, -1, new zzg(this, i2, null)));
    }

    protected BaseGmsClient(Context context, Looper looper, GmsClientSupervisor gmsClientSupervisor, GoogleApiAvailabilityLight googleApiAvailabilityLight, int i2, BaseConnectionCallbacks baseConnectionCallbacks, BaseOnConnectionFailedListener baseOnConnectionFailedListener, String str) {
        this.zzk = null;
        this.zzp = new Object();
        this.zzq = new Object();
        this.zzt = new ArrayList();
        this.zzv = 1;
        this.zzB = null;
        this.zzC = false;
        this.zzD = null;
        this.f12811d = new AtomicInteger(0);
        Preconditions.checkNotNull(context, "Context must not be null");
        this.zzl = context;
        Preconditions.checkNotNull(looper, "Looper must not be null");
        this.zzm = looper;
        Preconditions.checkNotNull(gmsClientSupervisor, "Supervisor must not be null");
        this.zzn = gmsClientSupervisor;
        Preconditions.checkNotNull(googleApiAvailabilityLight, "API availability must not be null");
        this.zzo = googleApiAvailabilityLight;
        this.f12809b = new zzb(this, looper);
        this.zzy = i2;
        this.zzw = baseConnectionCallbacks;
        this.zzx = baseOnConnectionFailedListener;
        this.zzz = str;
    }

    @KeepForSdk
    public void disconnect(@NonNull String str) {
        this.zzk = str;
        disconnect();
    }
}
