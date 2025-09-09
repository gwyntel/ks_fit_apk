package h;

import android.content.Context;
import android.content.Intent;
import androidx.health.connect.client.HealthConnectClient;

/* loaded from: classes.dex */
public abstract /* synthetic */ class a {
    static {
        HealthConnectClient.Companion companion = HealthConnectClient.INSTANCE;
    }

    public static Intent a(Context context) {
        return HealthConnectClient.INSTANCE.getHealthConnectManageDataIntent(context);
    }

    public static Intent b(Context context, String str) {
        return HealthConnectClient.INSTANCE.getHealthConnectManageDataIntent(context, str);
    }

    public static String c() {
        return HealthConnectClient.INSTANCE.getHealthConnectSettingsAction();
    }

    public static HealthConnectClient d(Context context) {
        return HealthConnectClient.INSTANCE.getOrCreate(context);
    }

    public static HealthConnectClient e(Context context, String str) {
        return HealthConnectClient.INSTANCE.getOrCreate(context, str);
    }

    public static HealthConnectClient f(Context context) {
        return HealthConnectClient.INSTANCE.getOrCreateLegacy(context);
    }

    public static HealthConnectClient g(Context context, String str) {
        return HealthConnectClient.INSTANCE.getOrCreateLegacy(context, str);
    }

    public static int h(Context context) {
        return HealthConnectClient.INSTANCE.getSdkStatus(context);
    }

    public static int i(Context context, String str) {
        return HealthConnectClient.INSTANCE.getSdkStatus(context, str);
    }

    public static int j(Context context) {
        return HealthConnectClient.INSTANCE.getSdkStatusLegacy(context);
    }

    public static int k(Context context, String str) {
        return HealthConnectClient.INSTANCE.getSdkStatusLegacy(context, str);
    }
}
