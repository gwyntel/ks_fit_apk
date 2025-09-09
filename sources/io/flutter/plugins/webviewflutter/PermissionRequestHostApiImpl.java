package io.flutter.plugins.webviewflutter;

import android.webkit.PermissionRequest;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;
import java.util.List;
import java.util.Objects;

@RequiresApi(api = 21)
/* loaded from: classes4.dex */
public class PermissionRequestHostApiImpl implements GeneratedAndroidWebView.PermissionRequestHostApi {
    private final BinaryMessenger binaryMessenger;
    private final InstanceManager instanceManager;

    public PermissionRequestHostApiImpl(@NonNull BinaryMessenger binaryMessenger, @NonNull InstanceManager instanceManager) {
        this.binaryMessenger = binaryMessenger;
        this.instanceManager = instanceManager;
    }

    private PermissionRequest getPermissionRequestInstance(@NonNull Long l2) {
        PermissionRequest permissionRequest = (PermissionRequest) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(permissionRequest);
        return permissionRequest;
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.PermissionRequestHostApi
    public void deny(@NonNull Long l2) {
        getPermissionRequestInstance(l2).deny();
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.PermissionRequestHostApi
    public void grant(@NonNull Long l2, @NonNull List<String> list) {
        getPermissionRequestInstance(l2).grant((String[]) list.toArray(new String[0]));
    }
}
