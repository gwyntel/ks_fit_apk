package io.flutter.plugins.webviewflutter;

import android.webkit.PermissionRequest;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class PermissionRequestFlutterApiImpl {
    private GeneratedAndroidWebView.PermissionRequestFlutterApi api;
    private final BinaryMessenger binaryMessenger;
    private final InstanceManager instanceManager;

    public PermissionRequestFlutterApiImpl(@NonNull BinaryMessenger binaryMessenger, @NonNull InstanceManager instanceManager) {
        this.binaryMessenger = binaryMessenger;
        this.instanceManager = instanceManager;
        this.api = new GeneratedAndroidWebView.PermissionRequestFlutterApi(binaryMessenger);
    }

    public void create(@NonNull PermissionRequest permissionRequest, @NonNull String[] strArr, @NonNull GeneratedAndroidWebView.PermissionRequestFlutterApi.Reply<Void> reply) {
        if (this.instanceManager.containsInstance(permissionRequest)) {
            return;
        }
        this.api.create(Long.valueOf(this.instanceManager.addHostCreatedInstance(permissionRequest)), Arrays.asList(strArr), reply);
    }

    @VisibleForTesting
    void setApi(@NonNull GeneratedAndroidWebView.PermissionRequestFlutterApi permissionRequestFlutterApi) {
        this.api = permissionRequestFlutterApi;
    }
}
