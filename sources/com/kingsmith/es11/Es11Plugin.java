package com.kingsmith.es11;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.media3.exoplayer.rtsp.RtspMediaSource;
import com.kingsmith.es11.utils.ToastUtil;
import com.tekartik.sqflite.Constant;
import com.umeng.analytics.pro.f;
import com.yc.utesdk.bean.SportsModeControlInfo;
import com.yc.utesdk.ble.open.DeviceMode;
import com.yc.utesdk.ble.open.UteBleClient;
import com.yc.utesdk.ble.open.UteBleConnection;
import com.yc.utesdk.listener.BleConnectStateListener;
import com.yc.utesdk.listener.HeartRateChangeListener;
import com.yc.utesdk.listener.SimpleCallbackListener;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.scan.UteScanCallback;
import com.yc.utesdk.scan.UteScanDevice;
import com.yc.utesdk.utils.open.CalendarUtils;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u001a\u001a\u00020\u001bH\u0002J\u0018\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0002J\u0018\u0010\"\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0002J\b\u0010#\u001a\u00020$H\u0003J\u0010\u0010%\u001a\u00020\u001d2\u0006\u0010&\u001a\u00020'H\u0016J\u0010\u0010(\u001a\u00020\u001d2\u0006\u0010)\u001a\u00020'H\u0016J\u0018\u0010*\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0017J\u0010\u0010+\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020!H\u0002J\u0010\u0010,\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020!H\u0002J\u0010\u0010-\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020!H\u0002J\u0010\u0010.\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020!H\u0002J\u0010\u0010/\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020!H\u0002J\u0010\u00100\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020!H\u0002J\u0010\u00101\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020!H\u0003J\u0018\u00102\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000¨\u00063"}, d2 = {"Lcom/kingsmith/es11/Es11Plugin;", "Lio/flutter/embedding/engine/plugins/FlutterPlugin;", "Lio/flutter/plugin/common/MethodChannel$MethodCallHandler;", "()V", "bleConnectStateListener", "Lcom/yc/utesdk/listener/BleConnectStateListener;", "getBleConnectStateListener", "()Lcom/yc/utesdk/listener/BleConnectStateListener;", "channel", "Lio/flutter/plugin/common/MethodChannel;", f.X, "Landroid/content/Context;", "heartRateChangeListener", "Lcom/yc/utesdk/listener/HeartRateChangeListener;", "getHeartRateChangeListener", "()Lcom/yc/utesdk/listener/HeartRateChangeListener;", "mSimpleCallbackListener", "Lcom/yc/utesdk/listener/SimpleCallbackListener;", "getMSimpleCallbackListener", "()Lcom/yc/utesdk/listener/SimpleCallbackListener;", "setMSimpleCallbackListener", "(Lcom/yc/utesdk/listener/SimpleCallbackListener;)V", "uteBleClient", "Lcom/yc/utesdk/ble/open/UteBleClient;", "uteBleConnection", "Lcom/yc/utesdk/ble/open/UteBleConnection;", "checkPermissions", "", "connectToDevice", "", NotificationCompat.CATEGORY_CALL, "Lio/flutter/plugin/common/MethodCall;", "result", "Lio/flutter/plugin/common/MethodChannel$Result;", "disconnectToDevice", "getSyncTime", "", "onAttachedToEngine", "flutterPluginBinding", "Lio/flutter/embedding/engine/plugins/FlutterPlugin$FlutterPluginBinding;", "onDetachedFromEngine", "binding", "onMethodCall", "pauseSportMode", "resumeSportMode", "startScan", "startSportMode", "stopScan", "stopSportMode", "syncHeartRate", "writeSportData", "es11_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nEs11Plugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Es11Plugin.kt\ncom/kingsmith/es11/Es11Plugin\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,461:1\n12271#2,2:462\n*S KotlinDebug\n*F\n+ 1 Es11Plugin.kt\ncom/kingsmith/es11/Es11Plugin\n*L\n439#1:462,2\n*E\n"})
/* loaded from: classes4.dex */
public final class Es11Plugin implements FlutterPlugin, MethodChannel.MethodCallHandler {
    private MethodChannel channel;
    private Context context;

    @Nullable
    private UteBleClient uteBleClient;

    @Nullable
    private UteBleConnection uteBleConnection;

    @NotNull
    private final BleConnectStateListener bleConnectStateListener = new Es11Plugin$bleConnectStateListener$1(this);

    @NotNull
    private final HeartRateChangeListener heartRateChangeListener = new Es11Plugin$heartRateChangeListener$1(this);

    @NotNull
    private SimpleCallbackListener mSimpleCallbackListener = new SimpleCallbackListener() { // from class: com.kingsmith.es11.Es11Plugin$mSimpleCallbackListener$1
        @Override // com.yc.utesdk.listener.SimpleCallbackListener
        public void onSimpleCallback(boolean result, int status) {
            LogUtils.i("onSimpleCallback result = " + result + ",status =" + status);
            if (status != 1) {
                if (status != 29) {
                    return;
                }
                LogUtils.i("重启回调");
                return;
            }
            LogUtils.d("发送用户ID");
            if (DeviceMode.isHasFunction_8(8388608)) {
                UteBleConnection uteBleConnection = this.f18568a.uteBleConnection;
                if (uteBleConnection != null) {
                    uteBleConnection.sendAccountIdAndRandomCode(12345, 0, true);
                    return;
                }
                return;
            }
            UteBleConnection uteBleConnection2 = this.f18568a.uteBleConnection;
            if (uteBleConnection2 != null) {
                uteBleConnection2.sendAccountId(12345);
            }
        }
    };

    private final boolean checkPermissions() {
        for (String str : Build.VERSION.SDK_INT >= 31 ? new String[]{"android.permission.BLUETOOTH_SCAN", "android.permission.BLUETOOTH_CONNECT"} : new String[]{"android.permission.BLUETOOTH", "android.permission.BLUETOOTH_ADMIN", "android.permission.ACCESS_FINE_LOCATION"}) {
            Context context = this.context;
            if (context == null) {
                Intrinsics.throwUninitializedPropertyAccessException(f.X);
                context = null;
            }
            if (ContextCompat.checkSelfPermission(context, str) != 0) {
                return false;
            }
        }
        return true;
    }

    private final void connectToDevice(MethodCall call, MethodChannel.Result result) {
        String str = (String) call.argument("address");
        Context context = null;
        if (str == null || str.length() == 0) {
            result.error("INVALID_ADDRESS", "Device address is null or empty", null);
            return;
        }
        if (!checkPermissions()) {
            result.error("PERMISSION_DENIED", "Bluetooth permissions not granted", null);
            return;
        }
        UteBleClient uteBleClient = this.uteBleClient;
        Intrinsics.checkNotNull(uteBleClient);
        if (!uteBleClient.isBluetoothEnable()) {
            result.error("BLUETOOTH_DISABLED", "Bluetooth is not enabled", null);
            return;
        }
        UteBleClient uteBleClient2 = this.uteBleClient;
        Intrinsics.checkNotNull(uteBleClient2);
        if (uteBleClient2.isConnected(str)) {
            result.error("ALREADY_CONNECTED", "Device is already connected", null);
            Context context2 = this.context;
            if (context2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(f.X);
            } else {
                context = context2;
            }
            ToastUtil.showToast(context, "Connected, no need to connect again");
            return;
        }
        UteBleClient uteBleClient3 = this.uteBleClient;
        Intrinsics.checkNotNull(uteBleClient3);
        UteBleConnection uteBleConnection = uteBleClient3.getUteBleConnection();
        this.uteBleConnection = uteBleConnection;
        if (uteBleConnection != null) {
            uteBleConnection.setConnectStateListener(this.bleConnectStateListener);
        }
        UteBleConnection uteBleConnection2 = this.uteBleConnection;
        if (uteBleConnection2 != null) {
            uteBleConnection2.setHeartRateChangeListener(this.heartRateChangeListener);
        }
        UteBleConnection uteBleConnection3 = this.uteBleConnection;
        if (uteBleConnection3 != null) {
            uteBleConnection3.setSimpleCallbackListener(this.mSimpleCallbackListener);
        }
        try {
            UteBleClient uteBleClient4 = this.uteBleClient;
            Intrinsics.checkNotNull(uteBleClient4);
            this.uteBleConnection = uteBleClient4.connect(str);
            result.success(null);
        } catch (Exception e2) {
            result.error("CONNECTION_FAILED", "Failed to connect: " + e2.getMessage(), null);
        }
    }

    private final void disconnectToDevice(MethodCall call, MethodChannel.Result result) {
        String str = (String) call.argument("address");
        if (str == null || str.length() == 0) {
            result.error("INVALID_ADDRESS", "Device address is null or empty", null);
            return;
        }
        if (!checkPermissions()) {
            result.error("PERMISSION_DENIED", "Bluetooth permissions not granted", null);
            return;
        }
        UteBleClient uteBleClient = this.uteBleClient;
        Intrinsics.checkNotNull(uteBleClient);
        if (!uteBleClient.isBluetoothEnable()) {
            result.error("BLUETOOTH_DISABLED", "Bluetooth is not enabled", null);
            return;
        }
        try {
            UteBleClient uteBleClient2 = this.uteBleClient;
            Intrinsics.checkNotNull(uteBleClient2);
            uteBleClient2.disconnect();
            result.success(null);
        } catch (Exception e2) {
            result.error("CONNECTION_FAILED", "Failed to connect: " + e2.getMessage(), null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(26)
    public final String getSyncTime() {
        String str = LocalDateTime.now().minusHours(24L).format(DateTimeFormatter.ofPattern(CalendarUtils.yyyyMMddHHmm));
        Intrinsics.checkNotNull(str);
        return str;
    }

    private final void pauseSportMode(MethodChannel.Result result) {
        UteBleClient uteBleClient = this.uteBleClient;
        if (uteBleClient != null) {
            String deviceAddress = uteBleClient != null ? uteBleClient.getDeviceAddress() : null;
            if (deviceAddress == null) {
                deviceAddress = "";
            }
            if (uteBleClient.isConnected(deviceAddress)) {
                try {
                    result.success(null);
                    return;
                } catch (Exception e2) {
                    result.error("PAUSE_FAILED", "Failed to pause sport mode: " + e2.getMessage(), null);
                    return;
                }
            }
        }
        result.error("NOT_CONNECTED", "Device not connected", null);
    }

    private final void resumeSportMode(MethodChannel.Result result) {
        UteBleClient uteBleClient = this.uteBleClient;
        if (uteBleClient != null) {
            String deviceAddress = uteBleClient != null ? uteBleClient.getDeviceAddress() : null;
            if (deviceAddress == null) {
                deviceAddress = "";
            }
            if (uteBleClient.isConnected(deviceAddress)) {
                try {
                    result.success(null);
                    return;
                } catch (Exception e2) {
                    result.error("RESUME_FAILED", "Failed to resume sport mode: " + e2.getMessage(), null);
                    return;
                }
            }
        }
        result.error("NOT_CONNECTED", "Device not connected", null);
    }

    private final void startScan(final MethodChannel.Result result) {
        if (!checkPermissions()) {
            result.error("PERMISSION_DENIED", "Bluetooth permissions not granted", null);
            return;
        }
        UteBleClient uteBleClient = this.uteBleClient;
        Intrinsics.checkNotNull(uteBleClient);
        if (!uteBleClient.isBluetoothEnable()) {
            result.error("BLUETOOTH_DISABLED", "Bluetooth is not enabled", null);
            return;
        }
        UteBleClient uteBleClient2 = this.uteBleClient;
        Intrinsics.checkNotNull(uteBleClient2);
        uteBleClient2.scanDevice(new UteScanCallback() { // from class: com.kingsmith.es11.Es11Plugin.startScan.1
            @Override // com.yc.utesdk.scan.UteScanCallback
            public void onScanComplete(@NotNull List<? extends UteScanDevice> scanDeviceList) {
                Intrinsics.checkNotNullParameter(scanDeviceList, "scanDeviceList");
                LogUtils.i("onScanComplete UteScanDevice = " + scanDeviceList.size());
                result.success(null);
            }

            @Override // com.yc.utesdk.scan.UteScanCallback
            @RequiresPermission("android.permission.BLUETOOTH_CONNECT")
            public void onScanning(@NotNull UteScanDevice device) {
                Intrinsics.checkNotNullParameter(device, "device");
                String name = device.getDevice().getName();
                if (name == null) {
                    name = "Unknown Device";
                }
                String str = name;
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                Locale locale = Locale.ROOT;
                String lowerCase = str.toLowerCase(locale);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                MethodChannel methodChannel = null;
                if (!StringsKt.contains$default((CharSequence) lowerCase, (CharSequence) "es11", false, 2, (Object) null)) {
                    String lowerCase2 = str.toLowerCase(locale);
                    Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
                    if (!StringsKt.contains$default((CharSequence) lowerCase2, (CharSequence) "p02", false, 2, (Object) null)) {
                        String lowerCase3 = str.toLowerCase(locale);
                        Intrinsics.checkNotNullExpressionValue(lowerCase3, "toLowerCase(...)");
                        if (!StringsKt.contains$default((CharSequence) lowerCase3, (CharSequence) "cs5", false, 2, (Object) null)) {
                            String lowerCase4 = str.toLowerCase(locale);
                            Intrinsics.checkNotNullExpressionValue(lowerCase4, "toLowerCase(...)");
                            if (!StringsKt.contains$default((CharSequence) lowerCase4, (CharSequence) "d26", false, 2, (Object) null)) {
                                return;
                            }
                        }
                    }
                }
                try {
                    Map mapMapOf = MapsKt.mapOf(TuplesKt.to("name", "SC01(" + ((String) StringsKt.split$default((CharSequence) str, new String[]{"("}, false, 0, 6, (Object) null).get(1))), TuplesKt.to("address", device.getDevice().getAddress()));
                    MethodChannel methodChannel2 = Es11Plugin.this.channel;
                    if (methodChannel2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("channel");
                    } else {
                        methodChannel = methodChannel2;
                    }
                    methodChannel.invokeMethod("onDeviceFound", mapMapOf);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }, RtspMediaSource.DEFAULT_TIMEOUT_MS);
    }

    private final void startSportMode(MethodChannel.Result result) {
        UteBleClient uteBleClient = this.uteBleClient;
        if (uteBleClient != null) {
            String deviceAddress = uteBleClient != null ? uteBleClient.getDeviceAddress() : null;
            if (deviceAddress == null) {
                deviceAddress = "";
            }
            if (uteBleClient.isConnected(deviceAddress)) {
                try {
                    UteBleConnection uteBleConnection = this.uteBleConnection;
                    Intrinsics.checkNotNull(uteBleConnection);
                    uteBleConnection.startSports(18, 3);
                    result.success(null);
                    return;
                } catch (Exception e2) {
                    result.error("START_FAILED", "Failed to start sport mode: " + e2.getMessage(), null);
                    return;
                }
            }
        }
        result.error("NOT_CONNECTED", "Device not connected", null);
    }

    private final void stopScan(MethodChannel.Result result) {
        LogUtils.i("stopScan = " + this.uteBleConnection);
        result.success(null);
    }

    private final void stopSportMode(MethodChannel.Result result) {
        UteBleClient uteBleClient = this.uteBleClient;
        if (uteBleClient != null) {
            String deviceAddress = uteBleClient != null ? uteBleClient.getDeviceAddress() : null;
            if (deviceAddress == null) {
                deviceAddress = "";
            }
            if (uteBleClient.isConnected(deviceAddress)) {
                try {
                    UteBleConnection uteBleConnection = this.uteBleConnection;
                    if (uteBleConnection != null) {
                        uteBleConnection.stopSports(2);
                    }
                    result.success(null);
                    return;
                } catch (Exception e2) {
                    result.error("STOP_FAILED", "Failed to stop sport mode: " + e2.getMessage(), null);
                    return;
                }
            }
        }
        result.error("NOT_CONNECTED", "Device not connected", null);
    }

    @RequiresApi(26)
    private final void syncHeartRate(MethodChannel.Result result) {
        UteBleClient uteBleClient = this.uteBleClient;
        if (uteBleClient != null) {
            String deviceAddress = uteBleClient != null ? uteBleClient.getDeviceAddress() : null;
            if (deviceAddress == null) {
                deviceAddress = "";
            }
            if (uteBleClient.isConnected(deviceAddress)) {
                try {
                    UteBleConnection uteBleConnection = this.uteBleConnection;
                    if (uteBleConnection != null) {
                        uteBleConnection.autoTestHeartRate(true);
                    }
                    Log.i("------", "getSyncTime():" + getSyncTime());
                    UteBleConnection uteBleConnection2 = this.uteBleConnection;
                    if (uteBleConnection2 != null) {
                        uteBleConnection2.syncHeartRateData(getSyncTime());
                    }
                    result.success(null);
                    return;
                } catch (Exception e2) {
                    result.error("STOP_FAILED", "Failed to stop sport mode: " + e2.getMessage(), null);
                    return;
                }
            }
        }
        result.error("NOT_CONNECTED", "Device not connected", null);
    }

    private final void writeSportData(MethodCall call, MethodChannel.Result result) {
        Map map = (Map) call.arguments();
        if (map == null) {
            result.error("INVALID_DATA", "Data is null or invalid", null);
            return;
        }
        UteBleClient uteBleClient = this.uteBleClient;
        if (uteBleClient != null) {
            String deviceAddress = uteBleClient != null ? uteBleClient.getDeviceAddress() : null;
            if (deviceAddress == null) {
                deviceAddress = "";
            }
            if (uteBleClient.isConnected(deviceAddress)) {
                try {
                    Object obj = map.get("duration");
                    Integer num = obj instanceof Integer ? (Integer) obj : null;
                    int iIntValue = num != null ? num.intValue() : 0;
                    Object obj2 = map.get("calorie");
                    String str = obj2 instanceof String ? (String) obj2 : null;
                    if (str == null) {
                        str = "0";
                    }
                    SportsModeControlInfo sportsModeControlInfo = new SportsModeControlInfo(true, iIntValue, 0.0f, Integer.parseInt(str), 0.0f);
                    UteBleConnection uteBleConnection = this.uteBleConnection;
                    Intrinsics.checkNotNull(uteBleConnection);
                    uteBleConnection.sendSportsDataToBle(sportsModeControlInfo);
                    result.success(null);
                    return;
                } catch (Exception e2) {
                    result.error("WRITE_FAILED", "Failed to write sport data: " + e2.getMessage(), null);
                    return;
                }
            }
        }
        result.error("NOT_CONNECTED", "Device not connected", null);
    }

    @NotNull
    public final BleConnectStateListener getBleConnectStateListener() {
        return this.bleConnectStateListener;
    }

    @NotNull
    public final HeartRateChangeListener getHeartRateChangeListener() {
        return this.heartRateChangeListener;
    }

    @NotNull
    public final SimpleCallbackListener getMSimpleCallbackListener() {
        return this.mSimpleCallbackListener;
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NotNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        Intrinsics.checkNotNullParameter(flutterPluginBinding, "flutterPluginBinding");
        Context applicationContext = flutterPluginBinding.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        this.context = applicationContext;
        MethodChannel methodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "com.kingsmith.es11/bracelet");
        this.channel = methodChannel;
        methodChannel.setMethodCallHandler(this);
        MethodChannel methodChannel2 = null;
        try {
            Context context = this.context;
            if (context == null) {
                Intrinsics.throwUninitializedPropertyAccessException(f.X);
                context = null;
            }
            this.uteBleClient = UteBleClient.initialize(context);
            LogUtils.setPrintEnable(true);
            LogUtils.e("onAttachedToEngine----");
        } catch (ClassCastException e2) {
            LogUtils.e("Application is not Es11Application: " + e2.getMessage());
            MethodChannel methodChannel3 = this.channel;
            if (methodChannel3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("channel");
            } else {
                methodChannel2 = methodChannel3;
            }
            methodChannel2.invokeMethod("onError", MapsKt.mapOf(TuplesKt.to("code", "INVALID_APPLICATION"), TuplesKt.to("message", "Application must be Es11Application")));
        }
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NotNull FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        MethodChannel methodChannel = this.channel;
        if (methodChannel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("channel");
            methodChannel = null;
        }
        methodChannel.setMethodCallHandler(null);
        this.uteBleClient = null;
        this.uteBleConnection = null;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    @RequiresApi(26)
    public void onMethodCall(@NotNull MethodCall call, @NotNull MethodChannel.Result result) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        if (this.uteBleClient == null) {
            result.error("CLIENT_NOT_INITIALIZED", "Bluetooth client not initialized", null);
            return;
        }
        String str = call.method;
        if (str != null) {
            switch (str.hashCode()) {
                case -2129330689:
                    if (str.equals("startScan")) {
                        startScan(result);
                        return;
                    }
                    break;
                case -1845530259:
                    if (str.equals("disconnectToDevice")) {
                        disconnectToDevice(call, result);
                        return;
                    }
                    break;
                case -1324997387:
                    if (str.equals("startSportMode")) {
                        startSportMode(result);
                        return;
                    }
                    break;
                case -1109204501:
                    if (str.equals("syncHeartRate")) {
                        syncHeartRate(result);
                        return;
                    }
                    break;
                case -852281974:
                    if (str.equals("resumeSportMode")) {
                        resumeSportMode(result);
                        return;
                    }
                    break;
                case 908806625:
                    if (str.equals("pauseSportMode")) {
                        pauseSportMode(result);
                        return;
                    }
                    break;
                case 1312617813:
                    if (str.equals("stopSportMode")) {
                        stopSportMode(result);
                        return;
                    }
                    break;
                case 1385449135:
                    if (str.equals(Constant.METHOD_GET_PLATFORM_VERSION)) {
                        result.success("Android " + Build.VERSION.RELEASE);
                        return;
                    }
                    break;
                case 1590715323:
                    if (str.equals("connectToDevice")) {
                        connectToDevice(call, result);
                        return;
                    }
                    break;
                case 1714778527:
                    if (str.equals("stopScan")) {
                        stopScan(result);
                        return;
                    }
                    break;
                case 1920721951:
                    if (str.equals("writeSportData")) {
                        writeSportData(call, result);
                        return;
                    }
                    break;
            }
        }
        result.notImplemented();
    }

    public final void setMSimpleCallbackListener(@NotNull SimpleCallbackListener simpleCallbackListener) {
        Intrinsics.checkNotNullParameter(simpleCallbackListener, "<set-?>");
        this.mSimpleCallbackListener = simpleCallbackListener;
    }
}
