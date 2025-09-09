package androidx.webkit;

import android.content.Context;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.lifecycle.e;
import androidx.webkit.internal.ApiHelperForP;
import androidx.webkit.internal.WebViewFeatureInternal;
import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;
import org.chromium.support_lib_boundary.ProcessGlobalConfigConstants;

/* loaded from: classes2.dex */
public class ProcessGlobalConfig {

    /* renamed from: a, reason: collision with root package name */
    String f6517a;

    /* renamed from: b, reason: collision with root package name */
    String f6518b;

    /* renamed from: c, reason: collision with root package name */
    String f6519c;
    private static final AtomicReference<HashMap<String, Object>> sProcessGlobalConfig = new AtomicReference<>();
    private static final Object sLock = new Object();

    @GuardedBy("sLock")
    private static boolean sApplyCalled = false;

    public static void apply(@NonNull ProcessGlobalConfig processGlobalConfig) {
        synchronized (sLock) {
            if (sApplyCalled) {
                throw new IllegalStateException("ProcessGlobalConfig#apply was called more than once, which is an illegal operation. The configuration settings provided by ProcessGlobalConfig take effect only once, when WebView is first loaded into the current process. Every process should only ever create a single instance of ProcessGlobalConfig and apply it once, before any calls to android.webkit APIs, such as during early app startup.");
            }
            sApplyCalled = true;
        }
        HashMap map = new HashMap();
        if (webViewCurrentlyLoaded()) {
            throw new IllegalStateException("WebView has already been loaded in the current process, so any attempt to apply the settings in ProcessGlobalConfig will have no effect. ProcessGlobalConfig#apply needs to be called before any calls to android.webkit APIs, such as during early app startup.");
        }
        if (processGlobalConfig.f6517a != null) {
            if (WebViewFeatureInternal.STARTUP_FEATURE_SET_DATA_DIRECTORY_SUFFIX.isSupportedByFramework()) {
                ApiHelperForP.setDataDirectorySuffix(processGlobalConfig.f6517a);
            } else {
                map.put(ProcessGlobalConfigConstants.DATA_DIRECTORY_SUFFIX, processGlobalConfig.f6517a);
            }
        }
        String str = processGlobalConfig.f6518b;
        if (str != null) {
            map.put(ProcessGlobalConfigConstants.DATA_DIRECTORY_BASE_PATH, str);
        }
        String str2 = processGlobalConfig.f6519c;
        if (str2 != null) {
            map.put(ProcessGlobalConfigConstants.CACHE_DIRECTORY_BASE_PATH, str2);
        }
        if (!e.a(sProcessGlobalConfig, null, map)) {
            throw new RuntimeException("Attempting to set ProcessGlobalConfig#sProcessGlobalConfig when it was already set");
        }
    }

    private static boolean webViewCurrentlyLoaded() throws NoSuchFieldException, SecurityException {
        try {
            Field declaredField = Class.forName("android.webkit.WebViewFactory").getDeclaredField("sProviderInstance");
            declaredField.setAccessible(true);
            return declaredField.get(null) != null;
        } catch (Exception unused) {
            return false;
        }
    }

    @NonNull
    public ProcessGlobalConfig setDataDirectorySuffix(@NonNull Context context, @NonNull String str) {
        if (!WebViewFeatureInternal.STARTUP_FEATURE_SET_DATA_DIRECTORY_SUFFIX.isSupported(context)) {
            throw WebViewFeatureInternal.getUnsupportedOperationException();
        }
        if (str.equals("")) {
            throw new IllegalArgumentException("Suffix cannot be an empty string");
        }
        if (str.indexOf(File.separatorChar) < 0) {
            this.f6517a = str;
            return this;
        }
        throw new IllegalArgumentException("Suffix " + str + " contains a path separator");
    }

    @NonNull
    public ProcessGlobalConfig setDirectoryBasePaths(@NonNull Context context, @NonNull File file, @NonNull File file2) {
        if (!WebViewFeatureInternal.STARTUP_FEATURE_SET_DIRECTORY_BASE_PATH.isSupported(context)) {
            throw WebViewFeatureInternal.getUnsupportedOperationException();
        }
        if (!file.isAbsolute()) {
            throw new IllegalArgumentException("dataDirectoryBasePath must be a non-empty absolute path");
        }
        if (!file2.isAbsolute()) {
            throw new IllegalArgumentException("cacheDirectoryBasePath must be a non-empty absolute path");
        }
        this.f6518b = file.getAbsolutePath();
        this.f6519c = file2.getAbsolutePath();
        return this;
    }
}
