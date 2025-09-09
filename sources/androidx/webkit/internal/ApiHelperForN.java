package androidx.webkit.internal;

import android.content.Context;
import android.webkit.ServiceWorkerClient;
import android.webkit.ServiceWorkerController;
import android.webkit.ServiceWorkerWebSettings;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.webkit.ServiceWorkerClientCompat;
import java.io.File;

@RequiresApi(24)
/* loaded from: classes2.dex */
public class ApiHelperForN {
    private ApiHelperForN() {
    }

    @DoNotInline
    public static boolean getAllowContentAccess(@NonNull ServiceWorkerWebSettings serviceWorkerWebSettings) {
        return serviceWorkerWebSettings.getAllowContentAccess();
    }

    @DoNotInline
    public static boolean getAllowFileAccess(@NonNull ServiceWorkerWebSettings serviceWorkerWebSettings) {
        return serviceWorkerWebSettings.getAllowFileAccess();
    }

    @DoNotInline
    public static boolean getBlockNetworkLoads(@NonNull ServiceWorkerWebSettings serviceWorkerWebSettings) {
        return serviceWorkerWebSettings.getBlockNetworkLoads();
    }

    @DoNotInline
    public static int getCacheMode(@NonNull ServiceWorkerWebSettings serviceWorkerWebSettings) {
        return serviceWorkerWebSettings.getCacheMode();
    }

    @NonNull
    @DoNotInline
    public static File getDataDir(@NonNull Context context) {
        return context.getDataDir();
    }

    @DoNotInline
    public static int getDisabledActionModeMenuItems(@NonNull WebSettings webSettings) {
        return webSettings.getDisabledActionModeMenuItems();
    }

    @NonNull
    @DoNotInline
    public static ServiceWorkerController getServiceWorkerControllerInstance() {
        return ServiceWorkerController.getInstance();
    }

    @NonNull
    @DoNotInline
    public static ServiceWorkerWebSettings getServiceWorkerWebSettings(@NonNull ServiceWorkerController serviceWorkerController) {
        return serviceWorkerController.getServiceWorkerWebSettings();
    }

    @NonNull
    @DoNotInline
    public static ServiceWorkerWebSettingsImpl getServiceWorkerWebSettingsImpl(@NonNull ServiceWorkerController serviceWorkerController) {
        return new ServiceWorkerWebSettingsImpl(getServiceWorkerWebSettings(serviceWorkerController));
    }

    @DoNotInline
    public static boolean isRedirect(@NonNull WebResourceRequest webResourceRequest) {
        return webResourceRequest.isRedirect();
    }

    @DoNotInline
    public static void setAllowContentAccess(@NonNull ServiceWorkerWebSettings serviceWorkerWebSettings, boolean z2) {
        serviceWorkerWebSettings.setAllowContentAccess(z2);
    }

    @DoNotInline
    public static void setAllowFileAccess(@NonNull ServiceWorkerWebSettings serviceWorkerWebSettings, boolean z2) {
        serviceWorkerWebSettings.setAllowFileAccess(z2);
    }

    @DoNotInline
    public static void setBlockNetworkLoads(@NonNull ServiceWorkerWebSettings serviceWorkerWebSettings, boolean z2) {
        serviceWorkerWebSettings.setBlockNetworkLoads(z2);
    }

    @DoNotInline
    public static void setCacheMode(@NonNull ServiceWorkerWebSettings serviceWorkerWebSettings, int i2) {
        serviceWorkerWebSettings.setCacheMode(i2);
    }

    @DoNotInline
    public static void setDisabledActionModeMenuItems(@NonNull WebSettings webSettings, int i2) {
        webSettings.setDisabledActionModeMenuItems(i2);
    }

    @DoNotInline
    public static void setServiceWorkerClient(@NonNull ServiceWorkerController serviceWorkerController, @Nullable ServiceWorkerClient serviceWorkerClient) {
        serviceWorkerController.setServiceWorkerClient(serviceWorkerClient);
    }

    @DoNotInline
    public static void setServiceWorkerClientCompat(@NonNull ServiceWorkerController serviceWorkerController, @NonNull ServiceWorkerClientCompat serviceWorkerClientCompat) {
        serviceWorkerController.setServiceWorkerClient(new FrameworkServiceWorkerClient(serviceWorkerClientCompat));
    }
}
