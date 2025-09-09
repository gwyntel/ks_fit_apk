package com.baseflow.permissionhandler;

import android.content.Context;
import androidx.annotation.NonNull;
import com.baseflow.permissionhandler.AppSettingsManager;
import com.baseflow.permissionhandler.PermissionManager;
import com.baseflow.permissionhandler.ServiceManager;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes3.dex */
final class MethodCallHandlerImpl implements MethodChannel.MethodCallHandler {
    private final AppSettingsManager appSettingsManager;
    private final Context applicationContext;
    private final PermissionManager permissionManager;
    private final ServiceManager serviceManager;

    MethodCallHandlerImpl(Context context, AppSettingsManager appSettingsManager, PermissionManager permissionManager, ServiceManager serviceManager) {
        this.applicationContext = context;
        this.appSettingsManager = appSettingsManager;
        this.permissionManager = permissionManager;
        this.serviceManager = serviceManager;
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(@NonNull MethodCall methodCall, @NonNull final MethodChannel.Result result) throws NumberFormatException {
        String str = methodCall.method;
        str.hashCode();
        switch (str) {
            case "checkServiceStatus":
                int i2 = Integer.parseInt(methodCall.arguments.toString());
                ServiceManager serviceManager = this.serviceManager;
                Context context = this.applicationContext;
                Objects.requireNonNull(result);
                serviceManager.a(i2, context, new ServiceManager.SuccessCallback() { // from class: com.baseflow.permissionhandler.a
                    @Override // com.baseflow.permissionhandler.ServiceManager.SuccessCallback
                    public final void onSuccess(int i3) {
                        result.success(Integer.valueOf(i3));
                    }
                }, new ErrorCallback() { // from class: com.baseflow.permissionhandler.b
                    @Override // com.baseflow.permissionhandler.ErrorCallback
                    public final void onError(String str2, String str3) {
                        result.error(str2, str3, null);
                    }
                });
                break;
            case "shouldShowRequestPermissionRationale":
                int i3 = Integer.parseInt(methodCall.arguments.toString());
                PermissionManager permissionManager = this.permissionManager;
                Objects.requireNonNull(result);
                permissionManager.c(i3, new PermissionManager.ShouldShowRequestPermissionRationaleSuccessCallback() { // from class: com.baseflow.permissionhandler.f
                    @Override // com.baseflow.permissionhandler.PermissionManager.ShouldShowRequestPermissionRationaleSuccessCallback
                    public final void onSuccess(boolean z2) {
                        result.success(Boolean.valueOf(z2));
                    }
                }, new ErrorCallback() { // from class: com.baseflow.permissionhandler.g
                    @Override // com.baseflow.permissionhandler.ErrorCallback
                    public final void onError(String str2, String str3) {
                        result.error(str2, str3, null);
                    }
                });
                break;
            case "checkPermissionStatus":
                int i4 = Integer.parseInt(methodCall.arguments.toString());
                PermissionManager permissionManager2 = this.permissionManager;
                Objects.requireNonNull(result);
                permissionManager2.a(i4, new PermissionManager.CheckPermissionsSuccessCallback() { // from class: com.baseflow.permissionhandler.c
                    @Override // com.baseflow.permissionhandler.PermissionManager.CheckPermissionsSuccessCallback
                    public final void onSuccess(int i5) {
                        result.success(Integer.valueOf(i5));
                    }
                });
                break;
            case "openAppSettings":
                AppSettingsManager appSettingsManager = this.appSettingsManager;
                Context context2 = this.applicationContext;
                Objects.requireNonNull(result);
                appSettingsManager.a(context2, new AppSettingsManager.OpenAppSettingsSuccessCallback() { // from class: com.baseflow.permissionhandler.h
                    @Override // com.baseflow.permissionhandler.AppSettingsManager.OpenAppSettingsSuccessCallback
                    public final void onSuccess(boolean z2) {
                        result.success(Boolean.valueOf(z2));
                    }
                }, new ErrorCallback() { // from class: com.baseflow.permissionhandler.i
                    @Override // com.baseflow.permissionhandler.ErrorCallback
                    public final void onError(String str2, String str3) {
                        result.error(str2, str3, null);
                    }
                });
                break;
            case "requestPermissions":
                List list = (List) methodCall.arguments();
                PermissionManager permissionManager3 = this.permissionManager;
                Objects.requireNonNull(result);
                permissionManager3.b(list, new PermissionManager.RequestPermissionsSuccessCallback() { // from class: com.baseflow.permissionhandler.d
                    @Override // com.baseflow.permissionhandler.PermissionManager.RequestPermissionsSuccessCallback
                    public final void onSuccess(Map map) {
                        result.success(map);
                    }
                }, new ErrorCallback() { // from class: com.baseflow.permissionhandler.e
                    @Override // com.baseflow.permissionhandler.ErrorCallback
                    public final void onError(String str2, String str3) {
                        result.error(str2, str3, null);
                    }
                });
                break;
            default:
                result.notImplemented();
                break;
        }
    }
}
