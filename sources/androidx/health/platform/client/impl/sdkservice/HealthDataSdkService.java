package androidx.health.platform.client.impl.sdkservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.Executors;

/* loaded from: classes.dex */
public final class HealthDataSdkService extends Service {
    private static final String TAG = "HealthDataSdkService";

    @Override // android.app.Service
    @Nullable
    public IBinder onBind(@NonNull Intent intent) {
        String action = intent.getAction();
        if ("androidx.health.platform.client.ACTION_BIND_SDK_SERVICE".equals(action)) {
            return new HealthDataSdkServiceStubImpl(getApplicationContext(), Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("HealthData-HealthDataSdkService-%d").build()));
        }
        Log.i(TAG, String.format("Bind request with an invalid action [%s]", action));
        return null;
    }
}
