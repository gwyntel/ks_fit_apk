package androidx.core.content;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.concurrent.futures.ResolvableFuture;
import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportCallback;
import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService;

/* loaded from: classes.dex */
class UnusedAppRestrictionsBackportServiceConnection implements ServiceConnection {

    /* renamed from: b, reason: collision with root package name */
    ResolvableFuture f3501b;
    private final Context mContext;

    /* renamed from: a, reason: collision with root package name */
    IUnusedAppRestrictionsBackportService f3500a = null;
    private boolean mHasBoundService = false;

    UnusedAppRestrictionsBackportServiceConnection(Context context) {
        this.mContext = context;
    }

    private IUnusedAppRestrictionsBackportCallback getBackportCallback() {
        return new IUnusedAppRestrictionsBackportCallback.Stub() { // from class: androidx.core.content.UnusedAppRestrictionsBackportServiceConnection.1
            @Override // androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportCallback
            public void onIsPermissionRevocationEnabledForAppResult(boolean z2, boolean z3) throws RemoteException {
                if (!z2) {
                    UnusedAppRestrictionsBackportServiceConnection.this.f3501b.set(0);
                    Log.e(PackageManagerCompat.LOG_TAG, "Unable to retrieve the permission revocation setting from the backport");
                } else if (z3) {
                    UnusedAppRestrictionsBackportServiceConnection.this.f3501b.set(3);
                } else {
                    UnusedAppRestrictionsBackportServiceConnection.this.f3501b.set(2);
                }
            }
        };
    }

    public void connectAndFetchResult(@NonNull ResolvableFuture<Integer> resolvableFuture) {
        if (this.mHasBoundService) {
            throw new IllegalStateException("Each UnusedAppRestrictionsBackportServiceConnection can only be bound once.");
        }
        this.mHasBoundService = true;
        this.f3501b = resolvableFuture;
        this.mContext.bindService(new Intent(UnusedAppRestrictionsBackportService.ACTION_UNUSED_APP_RESTRICTIONS_BACKPORT_CONNECTION).setPackage(PackageManagerCompat.getPermissionRevocationVerifierApp(this.mContext.getPackageManager())), this, 1);
    }

    public void disconnectFromService() {
        if (!this.mHasBoundService) {
            throw new IllegalStateException("bindService must be called before unbind");
        }
        this.mHasBoundService = false;
        this.mContext.unbindService(this);
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        IUnusedAppRestrictionsBackportService iUnusedAppRestrictionsBackportServiceAsInterface = IUnusedAppRestrictionsBackportService.Stub.asInterface(iBinder);
        this.f3500a = iUnusedAppRestrictionsBackportServiceAsInterface;
        try {
            iUnusedAppRestrictionsBackportServiceAsInterface.isPermissionRevocationEnabledForApp(getBackportCallback());
        } catch (RemoteException unused) {
            this.f3501b.set(0);
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        this.f3500a = null;
    }
}
