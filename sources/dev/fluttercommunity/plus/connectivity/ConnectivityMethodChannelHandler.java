package dev.fluttercommunity.plus.connectivity;

import androidx.annotation.NonNull;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

/* loaded from: classes4.dex */
class ConnectivityMethodChannelHandler implements MethodChannel.MethodCallHandler {
    private final Connectivity connectivity;

    ConnectivityMethodChannelHandler(Connectivity connectivity) {
        this.connectivity = connectivity;
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall methodCall, @NonNull MethodChannel.Result result) {
        if ("check".equals(methodCall.method)) {
            result.success(this.connectivity.a());
        } else {
            result.notImplemented();
        }
    }
}
