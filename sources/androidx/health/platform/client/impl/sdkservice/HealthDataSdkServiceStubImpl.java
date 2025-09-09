package androidx.health.platform.client.impl.sdkservice;

import android.content.Context;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import androidx.annotation.BinderThread;
import androidx.annotation.NonNull;
import androidx.health.platform.client.impl.permission.foregroundstate.ForegroundStateChecker;
import androidx.health.platform.client.impl.permission.token.PermissionTokenManager;
import androidx.health.platform.client.impl.sdkservice.IHealthDataSdkService;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.function.Predicate;

/* loaded from: classes.dex */
final class HealthDataSdkServiceStubImpl extends IHealthDataSdkService.Stub {
    private static final String TAG = "HealthDataSdkServiceStubImpl";
    private final Context mContext;
    private final Executor mExecutor;

    HealthDataSdkServiceStubImpl(Context context, Executor executor) {
        this.mContext = context;
        this.mExecutor = executor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getIsInForeground$2(IGetIsInForegroundCallback iGetIsInForegroundCallback) {
        try {
            iGetIsInForegroundCallback.onSuccess(ForegroundStateChecker.isInForeground());
        } catch (RemoteException e2) {
            Log.e(TAG, String.format("HealthDataSdkService#getIsInForeground failed: %s", e2.getMessage()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getPermissionToken$1(IGetPermissionTokenCallback iGetPermissionTokenCallback) {
        try {
            String currentToken = PermissionTokenManager.getCurrentToken(this.mContext);
            if (currentToken == null) {
                currentToken = "";
            }
            iGetPermissionTokenCallback.onSuccess(currentToken);
        } catch (RemoteException e2) {
            Log.e(TAG, String.format("HealthDataSdkService#getPermissionToken failed: %s", e2.getMessage()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPermissionToken$0(String str, ISetPermissionTokenCallback iSetPermissionTokenCallback) {
        PermissionTokenManager.setCurrentToken(this.mContext, str);
        try {
            iSetPermissionTokenCallback.onSuccess();
        } catch (RemoteException e2) {
            Log.e(TAG, String.format("HealthDataSdkService#setPermissionToken failed: %s", e2.getMessage()));
        }
    }

    @BinderThread
    private void verifyPackageName(@NonNull final String str) {
        String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(Binder.getCallingUid());
        if (str == null || packagesForUid == null || Arrays.stream(packagesForUid).noneMatch(new Predicate() { // from class: androidx.health.platform.client.impl.sdkservice.f
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return str.equals((String) obj);
            }
        })) {
            throw new SecurityException("Invalid package name!");
        }
        if (!"com.google.android.apps.healthdata".equals(str)) {
            throw new SecurityException("Not allowed!");
        }
    }

    @Override // androidx.health.platform.client.impl.sdkservice.IHealthDataSdkService
    public void getIsInForeground(@NonNull String str, @NonNull final IGetIsInForegroundCallback iGetIsInForegroundCallback) {
        verifyPackageName(str);
        this.mExecutor.execute(new Runnable() { // from class: androidx.health.platform.client.impl.sdkservice.d
            @Override // java.lang.Runnable
            public final void run() {
                HealthDataSdkServiceStubImpl.lambda$getIsInForeground$2(iGetIsInForegroundCallback);
            }
        });
    }

    @Override // androidx.health.platform.client.impl.sdkservice.IHealthDataSdkService
    public void getPermissionToken(@NonNull String str, @NonNull final IGetPermissionTokenCallback iGetPermissionTokenCallback) {
        verifyPackageName(str);
        this.mExecutor.execute(new Runnable() { // from class: androidx.health.platform.client.impl.sdkservice.e
            @Override // java.lang.Runnable
            public final void run() {
                this.f4385a.lambda$getPermissionToken$1(iGetPermissionTokenCallback);
            }
        });
    }

    @Override // androidx.health.platform.client.impl.sdkservice.IHealthDataSdkService
    public void setPermissionToken(@NonNull String str, @NonNull final String str2, @NonNull final ISetPermissionTokenCallback iSetPermissionTokenCallback) {
        verifyPackageName(str);
        this.mExecutor.execute(new Runnable() { // from class: androidx.health.platform.client.impl.sdkservice.c
            @Override // java.lang.Runnable
            public final void run() {
                this.f4381a.lambda$setPermissionToken$0(str2, iSetPermissionTokenCallback);
            }
        });
    }
}
