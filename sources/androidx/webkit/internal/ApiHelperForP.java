package androidx.webkit.internal;

import android.os.Looper;
import android.webkit.TracingController;
import android.webkit.WebView;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.webkit.TracingConfig;
import java.io.OutputStream;
import java.util.Collection;
import java.util.concurrent.Executor;

@RequiresApi(28)
/* loaded from: classes2.dex */
public class ApiHelperForP {
    private ApiHelperForP() {
    }

    @NonNull
    @DoNotInline
    public static TracingController getTracingControllerInstance() {
        return TracingController.getInstance();
    }

    @NonNull
    @DoNotInline
    public static ClassLoader getWebViewClassLoader() {
        return WebView.getWebViewClassLoader();
    }

    @NonNull
    @DoNotInline
    public static Looper getWebViewLooper(@NonNull WebView webView) {
        return webView.getWebViewLooper();
    }

    @DoNotInline
    public static boolean isTracing(@NonNull TracingController tracingController) {
        return tracingController.isTracing();
    }

    @DoNotInline
    public static void setDataDirectorySuffix(@NonNull String str) {
        WebView.setDataDirectorySuffix(str);
    }

    @DoNotInline
    public static void start(@NonNull TracingController tracingController, @NonNull TracingConfig tracingConfig) {
        tracingController.start(c0.a().addCategories(tracingConfig.getPredefinedCategories()).addCategories((Collection<String>) tracingConfig.getCustomIncludedCategories()).setTracingMode(tracingConfig.getTracingMode()).build());
    }

    @DoNotInline
    public static boolean stop(@NonNull TracingController tracingController, @Nullable OutputStream outputStream, @NonNull Executor executor) {
        return tracingController.stop(outputStream, executor);
    }
}
