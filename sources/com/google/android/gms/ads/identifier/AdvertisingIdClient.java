package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.alipay.sdk.m.u.i;
import com.google.android.gms.common.BlockingServiceConnection;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.annotation.KeepForSdkWithMembers;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.ads_identifier.zze;
import com.google.android.gms.internal.ads_identifier.zzf;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.GuardedBy;

@KeepForSdk
@ParametersAreNonnullByDefault
/* loaded from: classes3.dex */
public class AdvertisingIdClient {

    /* renamed from: a, reason: collision with root package name */
    BlockingServiceConnection f12597a;

    /* renamed from: b, reason: collision with root package name */
    zzf f12598b;

    /* renamed from: c, reason: collision with root package name */
    boolean f12599c;

    /* renamed from: d, reason: collision with root package name */
    final Object f12600d;

    /* renamed from: e, reason: collision with root package name */
    zzb f12601e;

    /* renamed from: f, reason: collision with root package name */
    final long f12602f;

    @GuardedBy("this")
    private final Context zzg;

    @KeepForSdkWithMembers
    public static final class Info {

        @Nullable
        private final String zza;
        private final boolean zzb;

        @Deprecated
        public Info(@Nullable String str, boolean z2) {
            this.zza = str;
            this.zzb = z2;
        }

        @Nullable
        public String getId() {
            return this.zza;
        }

        public boolean isLimitAdTrackingEnabled() {
            return this.zzb;
        }

        @NonNull
        public String toString() {
            String str = this.zza;
            boolean z2 = this.zzb;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 7);
            sb.append("{");
            sb.append(str);
            sb.append(i.f9804d);
            sb.append(z2);
            return sb.toString();
        }
    }

    @KeepForSdk
    public AdvertisingIdClient(@NonNull Context context) {
        this(context, 30000L, false, false);
    }

    @NonNull
    @KeepForSdk
    public static Info getAdvertisingIdInfo(@NonNull Context context) throws GooglePlayServicesRepairableException, IllegalStateException, GooglePlayServicesNotAvailableException, IOException {
        AdvertisingIdClient advertisingIdClient = new AdvertisingIdClient(context, -1L, true, false);
        try {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            advertisingIdClient.a(false);
            Info infoZzd = advertisingIdClient.zzd(-1);
            advertisingIdClient.b(infoZzd, true, 0.0f, SystemClock.elapsedRealtime() - jElapsedRealtime, "", null);
            return infoZzd;
        } finally {
        }
    }

    @KeepForSdk
    public static boolean getIsAdIdFakeForDebugLogging(@NonNull Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException, IOException {
        boolean zZzd;
        AdvertisingIdClient advertisingIdClient = new AdvertisingIdClient(context, -1L, false, false);
        try {
            advertisingIdClient.a(false);
            Preconditions.checkNotMainThread("Calling this from your main thread can lead to deadlock");
            synchronized (advertisingIdClient) {
                try {
                    if (!advertisingIdClient.f12599c) {
                        synchronized (advertisingIdClient.f12600d) {
                            zzb zzbVar = advertisingIdClient.f12601e;
                            if (zzbVar == null || !zzbVar.f12605b) {
                                throw new IOException("AdvertisingIdClient is not connected.");
                            }
                        }
                        try {
                            advertisingIdClient.a(false);
                            if (!advertisingIdClient.f12599c) {
                                throw new IOException("AdvertisingIdClient cannot reconnect.");
                            }
                        } catch (Exception e2) {
                            throw new IOException("AdvertisingIdClient cannot reconnect.", e2);
                        }
                    }
                    Preconditions.checkNotNull(advertisingIdClient.f12597a);
                    Preconditions.checkNotNull(advertisingIdClient.f12598b);
                    try {
                        zZzd = advertisingIdClient.f12598b.zzd();
                    } catch (RemoteException e3) {
                        Log.i("AdvertisingIdClient", "GMS remote exception ", e3);
                        throw new IOException("Remote exception");
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            advertisingIdClient.zze();
            return zZzd;
        } finally {
            advertisingIdClient.zza();
        }
    }

    @ShowFirstParty
    @KeepForSdk
    public static void setShouldSkipGmsCoreVersionCheck(boolean z2) {
    }

    private final Info zzd(int i2) throws IOException {
        Info info2;
        Preconditions.checkNotMainThread("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            try {
                if (!this.f12599c) {
                    synchronized (this.f12600d) {
                        zzb zzbVar = this.f12601e;
                        if (zzbVar == null || !zzbVar.f12605b) {
                            throw new IOException("AdvertisingIdClient is not connected.");
                        }
                    }
                    try {
                        a(false);
                        if (!this.f12599c) {
                            throw new IOException("AdvertisingIdClient cannot reconnect.");
                        }
                    } catch (Exception e2) {
                        throw new IOException("AdvertisingIdClient cannot reconnect.", e2);
                    }
                }
                Preconditions.checkNotNull(this.f12597a);
                Preconditions.checkNotNull(this.f12598b);
                try {
                    info2 = new Info(this.f12598b.zzc(), this.f12598b.zze(true));
                } catch (RemoteException e3) {
                    Log.i("AdvertisingIdClient", "GMS remote exception ", e3);
                    throw new IOException("Remote exception");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        zze();
        return info2;
    }

    private final void zze() {
        synchronized (this.f12600d) {
            zzb zzbVar = this.f12601e;
            if (zzbVar != null) {
                zzbVar.f12604a.countDown();
                try {
                    this.f12601e.join();
                } catch (InterruptedException unused) {
                }
            }
            long j2 = this.f12602f;
            if (j2 > 0) {
                this.f12601e = new zzb(this, j2);
            }
        }
    }

    protected final void a(boolean z2) {
        Preconditions.checkNotMainThread("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            try {
                if (this.f12599c) {
                    zza();
                }
                Context context = this.zzg;
                try {
                    context.getPackageManager().getPackageInfo("com.android.vending", 0);
                    int iIsGooglePlayServicesAvailable = GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(context, 12451000);
                    if (iIsGooglePlayServicesAvailable != 0 && iIsGooglePlayServicesAvailable != 2) {
                        throw new IOException("Google Play services not available");
                    }
                    BlockingServiceConnection blockingServiceConnection = new BlockingServiceConnection();
                    Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
                    intent.setPackage("com.google.android.gms");
                    try {
                        if (!ConnectionTracker.getInstance().bindService(context, intent, blockingServiceConnection, 1)) {
                            throw new IOException("Connection failure");
                        }
                        this.f12597a = blockingServiceConnection;
                        try {
                            this.f12598b = zze.zza(blockingServiceConnection.getServiceWithTimeout(10000L, TimeUnit.MILLISECONDS));
                            this.f12599c = true;
                            if (z2) {
                                zze();
                            }
                        } catch (InterruptedException unused) {
                            throw new IOException("Interrupted exception");
                        } catch (Throwable th) {
                            throw new IOException(th);
                        }
                    } finally {
                        IOException iOException = new IOException(th);
                    }
                } catch (PackageManager.NameNotFoundException unused2) {
                    throw new GooglePlayServicesNotAvailableException(9);
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    final boolean b(Info info2, boolean z2, float f2, long j2, String str, Throwable th) {
        if (Math.random() > 0.0d) {
            return false;
        }
        HashMap map = new HashMap();
        map.put("app_context", "1");
        if (info2 != null) {
            map.put("limit_ad_tracking", true != info2.isLimitAdTrackingEnabled() ? "0" : "1");
            String id = info2.getId();
            if (id != null) {
                map.put("ad_id_size", Integer.toString(id.length()));
            }
        }
        if (th != null) {
            map.put("error", th.getClass().getName());
        }
        map.put("tag", "AdvertisingIdClient");
        map.put("time_spent", Long.toString(j2));
        new zza(this, map).start();
        return true;
    }

    protected final void finalize() throws Throwable {
        zza();
        super.finalize();
    }

    @NonNull
    @KeepForSdk
    public Info getInfo() throws IOException {
        return zzd(-1);
    }

    @KeepForSdk
    public void start() throws GooglePlayServicesRepairableException, IllegalStateException, GooglePlayServicesNotAvailableException, IOException {
        a(true);
    }

    public final void zza() {
        Preconditions.checkNotMainThread("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            try {
                if (this.zzg == null || this.f12597a == null) {
                    return;
                }
                try {
                    if (this.f12599c) {
                        ConnectionTracker.getInstance().unbindService(this.zzg, this.f12597a);
                    }
                } catch (Throwable th) {
                    Log.i("AdvertisingIdClient", "AdvertisingIdClient unbindService failed.", th);
                }
                this.f12599c = false;
                this.f12598b = null;
                this.f12597a = null;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @VisibleForTesting
    public AdvertisingIdClient(@NonNull Context context, long j2, boolean z2, boolean z3) {
        Context applicationContext;
        this.f12600d = new Object();
        Preconditions.checkNotNull(context);
        if (z2 && (applicationContext = context.getApplicationContext()) != null) {
            context = applicationContext;
        }
        this.zzg = context;
        this.f12599c = false;
        this.f12602f = j2;
    }
}
