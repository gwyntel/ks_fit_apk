package io.flutter.plugins.webviewflutter;

import android.webkit.GeolocationPermissions;
import androidx.annotation.NonNull;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;
import java.util.Objects;

/* loaded from: classes4.dex */
public class GeolocationPermissionsCallbackHostApiImpl implements GeneratedAndroidWebView.GeolocationPermissionsCallbackHostApi {
    private final BinaryMessenger binaryMessenger;
    private final InstanceManager instanceManager;

    public GeolocationPermissionsCallbackHostApiImpl(@NonNull BinaryMessenger binaryMessenger, @NonNull InstanceManager instanceManager) {
        this.binaryMessenger = binaryMessenger;
        this.instanceManager = instanceManager;
    }

    private GeolocationPermissions.Callback getGeolocationPermissionsCallbackInstance(@NonNull Long l2) {
        GeolocationPermissions.Callback callback = (GeolocationPermissions.Callback) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(callback);
        return callback;
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.GeolocationPermissionsCallbackHostApi
    public void invoke(@NonNull Long l2, @NonNull String str, @NonNull Boolean bool, @NonNull Boolean bool2) {
        getGeolocationPermissionsCallbackInstance(l2).invoke(str, bool.booleanValue(), bool2.booleanValue());
    }
}
