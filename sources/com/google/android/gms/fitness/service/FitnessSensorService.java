package com.google.android.gms.fitness.service;

import android.app.AppOpsManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class FitnessSensorService extends Service {

    @NonNull
    public static final String SERVICE_INTERFACE = "com.google.android.gms.fitness.service.FitnessSensorService";
    private zzb zza;

    protected final void a() {
        int callingUid = Binder.getCallingUid();
        if (PlatformVersion.isAtLeastKitKat()) {
            ((AppOpsManager) getSystemService("appops")).checkPackage(callingUid, "com.google.android.gms");
            return;
        }
        String[] packagesForUid = getPackageManager().getPackagesForUid(callingUid);
        if (packagesForUid != null) {
            for (String str : packagesForUid) {
                if (str.equals("com.google.android.gms")) {
                    return;
                }
            }
        }
        throw new SecurityException("Unauthorized caller");
    }

    @Override // android.app.Service
    @Nullable
    @CallSuper
    public IBinder onBind(@NonNull Intent intent) {
        if (!SERVICE_INTERFACE.equals(intent.getAction())) {
            return null;
        }
        if (Log.isLoggable("FitnessSensorService", 3)) {
            Log.d("FitnessSensorService", "Intent " + String.valueOf(intent) + " received by " + getClass().getName());
        }
        return this.zza;
    }

    @Override // android.app.Service
    @CallSuper
    public void onCreate() {
        super.onCreate();
        this.zza = new zzb(this, null);
    }

    @NonNull
    public abstract List<DataSource> onFindDataSources(@NonNull List<DataType> list);

    public abstract boolean onRegister(@NonNull FitnessSensorServiceRequest fitnessSensorServiceRequest);

    public abstract boolean onUnregister(@NonNull DataSource dataSource);
}
