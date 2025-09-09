package com.kingsmith.es11;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.RequiresApi;
import com.kingsmith.es11.Es11Plugin$bleConnectStateListener$1;
import com.yc.utesdk.ble.open.DeviceMode;
import com.yc.utesdk.ble.open.UteBleClient;
import com.yc.utesdk.ble.open.UteBleConnection;
import com.yc.utesdk.listener.BleConnectStateListener;
import com.yc.utesdk.log.LogUtils;
import io.flutter.plugin.common.MethodChannel;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000#\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u00020\u00032\n\u0010\u0004\u001a\u00060\u0005j\u0002`\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u0017¨\u0006\n"}, d2 = {"com/kingsmith/es11/Es11Plugin$bleConnectStateListener$1", "Lcom/yc/utesdk/listener/BleConnectStateListener;", "onConnectException", "", "e", "Ljava/lang/Exception;", "Lkotlin/Exception;", "onConnecteStateChange", "status", "", "es11_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class Es11Plugin$bleConnectStateListener$1 implements BleConnectStateListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Es11Plugin f18566a;

    Es11Plugin$bleConnectStateListener$1(Es11Plugin es11Plugin) {
        this.f18566a = es11Plugin;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onConnectException$lambda$3(Es11Plugin this$0, Exception e2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(e2, "$e");
        MethodChannel methodChannel = this$0.channel;
        if (methodChannel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("channel");
            methodChannel = null;
        }
        methodChannel.invokeMethod("onDisconnected", MapsKt.mapOf(TuplesKt.to("error", e2.getMessage())));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onConnecteStateChange$lambda$0(Es11Plugin this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        MethodChannel methodChannel = this$0.channel;
        if (methodChannel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("channel");
            methodChannel = null;
        }
        methodChannel.invokeMethod("onDisconnected", null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onConnecteStateChange$lambda$1(Es11Plugin this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        MethodChannel methodChannel = this$0.channel;
        if (methodChannel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("channel");
            methodChannel = null;
        }
        methodChannel.invokeMethod("onConnecting", null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onConnecteStateChange$lambda$2(Es11Plugin this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        MethodChannel methodChannel = this$0.channel;
        if (methodChannel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("channel");
            methodChannel = null;
        }
        UteBleClient uteBleClient = this$0.uteBleClient;
        String deviceName = uteBleClient != null ? uteBleClient.getDeviceName() : null;
        if (deviceName == null) {
            deviceName = "Unknown Device";
        }
        Pair pair = TuplesKt.to("name", deviceName);
        UteBleClient uteBleClient2 = this$0.uteBleClient;
        String deviceAddress = uteBleClient2 != null ? uteBleClient2.getDeviceAddress() : null;
        if (deviceAddress == null) {
            deviceAddress = "";
        }
        methodChannel.invokeMethod("onConnected", MapsKt.mapOf(pair, TuplesKt.to("address", deviceAddress)));
    }

    @Override // com.yc.utesdk.listener.BleConnectStateListener
    public void onConnectException(@NotNull final Exception e2) {
        Intrinsics.checkNotNullParameter(e2, "e");
        LogUtils.e("onConnectException = " + e2);
        Handler handler = new Handler(Looper.getMainLooper());
        final Es11Plugin es11Plugin = this.f18566a;
        handler.post(new Runnable() { // from class: f0.f
            @Override // java.lang.Runnable
            public final void run() {
                Es11Plugin$bleConnectStateListener$1.onConnectException$lambda$3(es11Plugin, e2);
            }
        });
    }

    @Override // com.yc.utesdk.listener.BleConnectStateListener
    @RequiresApi(26)
    public void onConnecteStateChange(int status) {
        UteBleConnection uteBleConnection;
        if (status == 0) {
            LogUtils.i("disconnected");
            Handler handler = new Handler(Looper.getMainLooper());
            final Es11Plugin es11Plugin = this.f18566a;
            handler.post(new Runnable() { // from class: f0.c
                @Override // java.lang.Runnable
                public final void run() {
                    Es11Plugin$bleConnectStateListener$1.onConnecteStateChange$lambda$0(es11Plugin);
                }
            });
            return;
        }
        if (status == 1) {
            LogUtils.i("connecting");
            Handler handler2 = new Handler(Looper.getMainLooper());
            final Es11Plugin es11Plugin2 = this.f18566a;
            handler2.post(new Runnable() { // from class: f0.d
                @Override // java.lang.Runnable
                public final void run() {
                    Es11Plugin$bleConnectStateListener$1.onConnecteStateChange$lambda$1(es11Plugin2);
                }
            });
            return;
        }
        if (status != 2) {
            return;
        }
        UteBleClient uteBleClient = this.f18566a.uteBleClient;
        String deviceName = uteBleClient != null ? uteBleClient.getDeviceName() : null;
        UteBleClient uteBleClient2 = this.f18566a.uteBleClient;
        LogUtils.i("connected, " + deviceName + ", " + (uteBleClient2 != null ? uteBleClient2.getDeviceAddress() : null));
        Handler handler3 = new Handler(Looper.getMainLooper());
        final Es11Plugin es11Plugin3 = this.f18566a;
        handler3.post(new Runnable() { // from class: f0.e
            @Override // java.lang.Runnable
            public final void run() {
                Es11Plugin$bleConnectStateListener$1.onConnecteStateChange$lambda$2(es11Plugin3);
            }
        });
        UteBleConnection uteBleConnection2 = this.f18566a.uteBleConnection;
        if (uteBleConnection2 != null) {
            uteBleConnection2.syncDeviceTime();
        }
        if (DeviceMode.isHasFunction_4(1048576) && (uteBleConnection = this.f18566a.uteBleConnection) != null) {
            uteBleConnection.setDeviceLanguage(1);
        }
        UteBleConnection uteBleConnection3 = this.f18566a.uteBleConnection;
        if (uteBleConnection3 != null) {
            uteBleConnection3.syncHeartRateData(this.f18566a.getSyncTime());
        }
    }
}
