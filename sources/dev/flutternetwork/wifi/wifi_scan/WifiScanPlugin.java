package dev.flutternetwork.wifi.wifi_scan;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.location.LocationManagerCompat;
import com.facebook.internal.NativeProtocol;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tekartik.sqflite.Constant;
import com.umeng.analytics.pro.f;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000¬\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010%\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0010\u0015\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0010$\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u0005:\u0001MB\u0005¢\u0006\u0002\u0010\u0006J\u001c\u0010&\u001a\u00020'2\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020'0\u001cH\u0002J\u0010\u0010*\u001a\u00020\u001b2\u0006\u0010+\u001a\u00020!H\u0002J\u0010\u0010,\u001a\u00020\u001b2\u0006\u0010+\u001a\u00020!H\u0002J\u001c\u0010-\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0013\u0012\u0006\u0012\u0004\u0018\u0001000/0.H\u0002J\b\u00101\u001a\u00020!H\u0002J\b\u00102\u001a\u00020!H\u0002J\u0010\u00103\u001a\u00020'2\u0006\u00104\u001a\u000205H\u0016J\u0012\u00106\u001a\u00020'2\b\b\u0001\u00107\u001a\u000208H\u0016J\u0012\u00109\u001a\u00020'2\b\u0010:\u001a\u0004\u0018\u000100H\u0016J\b\u0010;\u001a\u00020'H\u0016J\b\u0010<\u001a\u00020'H\u0016J\u0012\u0010=\u001a\u00020'2\b\b\u0001\u00104\u001a\u000208H\u0016J\u001c\u0010>\u001a\u00020'2\b\u0010:\u001a\u0004\u0018\u0001002\b\u0010?\u001a\u0004\u0018\u00010\u0010H\u0016J\u001c\u0010@\u001a\u00020'2\b\b\u0001\u0010A\u001a\u00020B2\b\b\u0001\u0010C\u001a\u00020DH\u0016J\u0010\u0010E\u001a\u00020'2\u0006\u00104\u001a\u000205H\u0016J-\u0010F\u001a\u00020!2\u0006\u0010G\u001a\u00020\u001b2\u000e\u0010H\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00130\u00122\u0006\u0010 \u001a\u00020\u001dH\u0016¢\u0006\u0002\u0010IJ\b\u0010J\u001a\u00020'H\u0002J\b\u0010K\u001a\u00020!H\u0002J\b\u0010L\u001a\u00020!H\u0002R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0014R\u0016\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0014R\u0016\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0014R\u0016\u0010\u0017\u001a\n \u0018*\u0004\u0018\u00010\u00130\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R5\u0010\u0019\u001a)\u0012\u0004\u0012\u00020\u001b\u0012\u001f\u0012\u001d\u0012\u0013\u0012\u00110\u001d¢\u0006\f\b\u001e\u0012\b\b\u001f\u0012\u0004\b\b( \u0012\u0004\u0012\u00020!0\u001c0\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010#X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010$\u001a\u0004\u0018\u00010%X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006N"}, d2 = {"Ldev/flutternetwork/wifi/wifi_scan/WifiScanPlugin;", "Lio/flutter/embedding/engine/plugins/FlutterPlugin;", "Lio/flutter/plugin/common/MethodChannel$MethodCallHandler;", "Lio/flutter/embedding/engine/plugins/activity/ActivityAware;", "Lio/flutter/plugin/common/PluginRegistry$RequestPermissionsResultListener;", "Lio/flutter/plugin/common/EventChannel$StreamHandler;", "()V", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", "channel", "Lio/flutter/plugin/common/MethodChannel;", f.X, "Landroid/content/Context;", "eventChannel", "Lio/flutter/plugin/common/EventChannel;", "eventSink", "Lio/flutter/plugin/common/EventChannel$EventSink;", "locationPermissionBoth", "", "", "[Ljava/lang/String;", "locationPermissionCoarse", "locationPermissionFine", "logTag", "kotlin.jvm.PlatformType", "requestPermissionCookie", "", "", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "grantResults", "", "wifi", "Landroid/net/wifi/WifiManager;", "wifiScanReceiver", "Landroid/content/BroadcastReceiver;", "askForLocationPermission", "", "callback", "Ldev/flutternetwork/wifi/wifi_scan/WifiScanPlugin$AskLocPermResult;", "canGetScannedResults", "askPermission", "canStartScan", "getScannedResults", "", "", "", "hasLocationPermission", "isLocationEnabled", "onAttachedToActivity", "binding", "Lio/flutter/embedding/engine/plugins/activity/ActivityPluginBinding;", "onAttachedToEngine", "flutterPluginBinding", "Lio/flutter/embedding/engine/plugins/FlutterPlugin$FlutterPluginBinding;", "onCancel", Constant.PARAM_SQL_ARGUMENTS, "onDetachedFromActivity", "onDetachedFromActivityForConfigChanges", "onDetachedFromEngine", "onListen", "events", "onMethodCall", NotificationCompat.CATEGORY_CALL, "Lio/flutter/plugin/common/MethodCall;", "result", "Lio/flutter/plugin/common/MethodChannel$Result;", "onReattachedToActivityForConfigChanges", "onRequestPermissionsResult", "requestCode", NativeProtocol.RESULT_ARGS_PERMISSIONS, "(I[Ljava/lang/String;[I)Z", "onScannedResultsAvailable", "requiresFineLocation", "startScan", "AskLocPermResult", "wifi_scan_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nWifiScanPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WifiScanPlugin.kt\ndev/flutternetwork/wifi/wifi_scan/WifiScanPlugin\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,341:1\n1549#2:342\n1620#2,3:343\n12744#3,2:346\n*S KotlinDebug\n*F\n+ 1 WifiScanPlugin.kt\ndev/flutternetwork/wifi/wifi_scan/WifiScanPlugin\n*L\n255#1:342\n255#1:343,3\n289#1:346,2\n*E\n"})
/* loaded from: classes4.dex */
public final class WifiScanPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler, ActivityAware, PluginRegistry.RequestPermissionsResultListener, EventChannel.StreamHandler {

    @Nullable
    private Activity activity;
    private MethodChannel channel;
    private Context context;
    private EventChannel eventChannel;

    @Nullable
    private EventChannel.EventSink eventSink;

    @NotNull
    private final String[] locationPermissionBoth;

    @NotNull
    private final String[] locationPermissionCoarse;

    @NotNull
    private final String[] locationPermissionFine;
    private final String logTag = WifiScanPlugin.class.getSimpleName();

    @NotNull
    private final Map<Integer, Function1<int[], Boolean>> requestPermissionCookie = new LinkedHashMap();

    @Nullable
    private WifiManager wifi;

    @Nullable
    private BroadcastReceiver wifiScanReceiver;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Ldev/flutternetwork/wifi/wifi_scan/WifiScanPlugin$AskLocPermResult;", "", "(Ljava/lang/String;I)V", "GRANTED", "UPGRADE_TO_FINE", "DENIED", "ERROR_NO_ACTIVITY", "wifi_scan_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class AskLocPermResult {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ AskLocPermResult[] $VALUES;
        public static final AskLocPermResult GRANTED = new AskLocPermResult("GRANTED", 0);
        public static final AskLocPermResult UPGRADE_TO_FINE = new AskLocPermResult("UPGRADE_TO_FINE", 1);
        public static final AskLocPermResult DENIED = new AskLocPermResult("DENIED", 2);
        public static final AskLocPermResult ERROR_NO_ACTIVITY = new AskLocPermResult("ERROR_NO_ACTIVITY", 3);

        private static final /* synthetic */ AskLocPermResult[] $values() {
            return new AskLocPermResult[]{GRANTED, UPGRADE_TO_FINE, DENIED, ERROR_NO_ACTIVITY};
        }

        static {
            AskLocPermResult[] askLocPermResultArr$values = $values();
            $VALUES = askLocPermResultArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(askLocPermResultArr$values);
        }

        private AskLocPermResult(String str, int i2) {
        }

        @NotNull
        public static EnumEntries<AskLocPermResult> getEntries() {
            return $ENTRIES;
        }

        public static AskLocPermResult valueOf(String str) {
            return (AskLocPermResult) Enum.valueOf(AskLocPermResult.class, str);
        }

        public static AskLocPermResult[] values() {
            return (AskLocPermResult[]) $VALUES.clone();
        }
    }

    public WifiScanPlugin() {
        String[] strArr = {"android.permission.ACCESS_COARSE_LOCATION"};
        this.locationPermissionCoarse = strArr;
        String[] strArr2 = {"android.permission.ACCESS_FINE_LOCATION"};
        this.locationPermissionFine = strArr2;
        this.locationPermissionBoth = (String[]) ArraysKt.plus((Object[]) strArr, (Object[]) strArr2);
    }

    private final void askForLocationPermission(final Function1<? super AskLocPermResult, Unit> callback) {
        if (this.activity == null) {
            callback.invoke(AskLocPermResult.ERROR_NO_ACTIVITY);
            return;
        }
        boolean zRequiresFineLocation = requiresFineLocation();
        final boolean z2 = zRequiresFineLocation && Build.VERSION.SDK_INT > 30;
        String[] strArr = z2 ? this.locationPermissionBoth : zRequiresFineLocation ? this.locationPermissionFine : this.locationPermissionCoarse;
        int iNextInt = Random.INSTANCE.nextInt(100) + 6567800;
        this.requestPermissionCookie.put(Integer.valueOf(iNextInt), new Function1<int[], Boolean>() { // from class: dev.flutternetwork.wifi.wifi_scan.WifiScanPlugin.askForLocationPermission.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final Boolean invoke(@NotNull int[] grantArray) {
                AskLocPermResult askLocPermResult;
                Intrinsics.checkNotNullParameter(grantArray, "grantArray");
                Log.d(WifiScanPlugin.this.logTag, "permissionResultCallback: args(" + grantArray + ")");
                Function1<AskLocPermResult, Unit> function1 = callback;
                int length = grantArray.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        askLocPermResult = AskLocPermResult.GRANTED;
                        break;
                    }
                    if (grantArray[i2] == 0) {
                        i2++;
                    } else {
                        askLocPermResult = (z2 && ArraysKt.first(grantArray) == 0) ? AskLocPermResult.UPGRADE_TO_FINE : AskLocPermResult.DENIED;
                    }
                }
                function1.invoke(askLocPermResult);
                return Boolean.TRUE;
            }
        });
        Activity activity = this.activity;
        Intrinsics.checkNotNull(activity);
        ActivityCompat.requestPermissions(activity, strArr, iNextInt);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int canGetScannedResults(boolean askPermission) {
        boolean zHasLocationPermission = hasLocationPermission();
        boolean zIsLocationEnabled = isLocationEnabled();
        if (zHasLocationPermission && zIsLocationEnabled) {
            return 1;
        }
        if (zHasLocationPermission) {
            return 5;
        }
        return askPermission ? -1 : 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int canStartScan(boolean askPermission) {
        boolean zHasLocationPermission = hasLocationPermission();
        boolean zIsLocationEnabled = isLocationEnabled();
        if (Build.VERSION.SDK_INT < 28) {
            return 1;
        }
        if (zHasLocationPermission && zIsLocationEnabled) {
            return 1;
        }
        if (zHasLocationPermission) {
            return 5;
        }
        return askPermission ? -1 : 2;
    }

    private final List<Map<String, Object>> getScannedResults() {
        WifiManager wifiManager = this.wifi;
        Intrinsics.checkNotNull(wifiManager);
        List<ScanResult> scanResults = wifiManager.getScanResults();
        Intrinsics.checkNotNullExpressionValue(scanResults, "getScanResults(...)");
        List<ScanResult> list = scanResults;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (Iterator it = list.iterator(); it.hasNext(); it = it) {
            ScanResult scanResult = (ScanResult) it.next();
            arrayList.add(MapsKt.mapOf(TuplesKt.to("ssid", scanResult.SSID), TuplesKt.to("bssid", scanResult.BSSID), TuplesKt.to("capabilities", scanResult.capabilities), TuplesKt.to("frequency", Integer.valueOf(scanResult.frequency)), TuplesKt.to(FirebaseAnalytics.Param.LEVEL, Integer.valueOf(scanResult.level)), TuplesKt.to(com.alipay.sdk.m.t.a.f9743k, Long.valueOf(scanResult.timestamp)), TuplesKt.to("standard", Build.VERSION.SDK_INT >= 30 ? Integer.valueOf(scanResult.getWifiStandard()) : null), TuplesKt.to("centerFrequency0", Integer.valueOf(scanResult.centerFreq0)), TuplesKt.to("centerFrequency1", Integer.valueOf(scanResult.centerFreq1)), TuplesKt.to("channelWidth", Integer.valueOf(scanResult.channelWidth)), TuplesKt.to("isPasspoint", Boolean.valueOf(scanResult.isPasspointNetwork())), TuplesKt.to("operatorFriendlyName", scanResult.operatorFriendlyName), TuplesKt.to("venueName", scanResult.venueName), TuplesKt.to("is80211mcResponder", Boolean.valueOf(scanResult.is80211mcResponder()))));
        }
        return arrayList;
    }

    private final boolean hasLocationPermission() {
        for (String str : requiresFineLocation() ? this.locationPermissionFine : this.locationPermissionBoth) {
            Context context = this.context;
            if (context == null) {
                Intrinsics.throwUninitializedPropertyAccessException(f.X);
                context = null;
            }
            if (ContextCompat.checkSelfPermission(context, str) == 0) {
                return true;
            }
        }
        return false;
    }

    private final boolean isLocationEnabled() {
        Context context = this.context;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException(f.X);
            context = null;
        }
        Object systemService = context.getApplicationContext().getSystemService("location");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.location.LocationManager");
        return LocationManagerCompat.isLocationEnabled((LocationManager) systemService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onScannedResultsAvailable() {
        EventChannel.EventSink eventSink = this.eventSink;
        if (eventSink != null) {
            eventSink.success(getScannedResults());
        }
    }

    private final boolean requiresFineLocation() {
        if (Build.VERSION.SDK_INT >= 29) {
            Context context = this.context;
            if (context == null) {
                Intrinsics.throwUninitializedPropertyAccessException(f.X);
                context = null;
            }
            if (context.getApplicationInfo().targetSdkVersion >= 29) {
                return true;
            }
        }
        return false;
    }

    private final boolean startScan() {
        WifiManager wifiManager = this.wifi;
        Intrinsics.checkNotNull(wifiManager);
        return wifiManager.startScan();
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(@NotNull ActivityPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        this.activity = binding.getActivity();
        binding.addRequestPermissionsResultListener(this);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull @NotNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        Intrinsics.checkNotNullParameter(flutterPluginBinding, "flutterPluginBinding");
        Context applicationContext = flutterPluginBinding.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        this.context = applicationContext;
        Context context = null;
        if (applicationContext == null) {
            Intrinsics.throwUninitializedPropertyAccessException(f.X);
            applicationContext = null;
        }
        Object systemService = applicationContext.getApplicationContext().getSystemService("wifi");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.net.wifi.WifiManager");
        this.wifi = (WifiManager) systemService;
        this.wifiScanReceiver = new BroadcastReceiver() { // from class: dev.flutternetwork.wifi.wifi_scan.WifiScanPlugin.onAttachedToEngine.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(@NotNull Context context2, @NotNull Intent intent) {
                Intrinsics.checkNotNullParameter(context2, "context");
                Intrinsics.checkNotNullParameter(intent, "intent");
                if (intent.getBooleanExtra("resultsUpdated", false)) {
                    WifiScanPlugin.this.onScannedResultsAvailable();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
        Context context2 = this.context;
        if (context2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(f.X);
        } else {
            context = context2;
        }
        context.registerReceiver(this.wifiScanReceiver, intentFilter);
        MethodChannel methodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "wifi_scan");
        this.channel = methodChannel;
        methodChannel.setMethodCallHandler(this);
        EventChannel eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), "wifi_scan/onScannedResultsAvailable");
        this.eventChannel = eventChannel;
        eventChannel.setStreamHandler(this);
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onCancel(@Nullable Object arguments) {
        EventChannel.EventSink eventSink = this.eventSink;
        if (eventSink != null) {
            eventSink.endOfStream();
        }
        this.eventSink = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        this.activity = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        this.activity = null;
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull @NotNull FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        MethodChannel methodChannel = this.channel;
        if (methodChannel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("channel");
            methodChannel = null;
        }
        methodChannel.setMethodCallHandler(null);
        EventChannel eventChannel = this.eventChannel;
        if (eventChannel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("eventChannel");
            eventChannel = null;
        }
        eventChannel.setStreamHandler(null);
        EventChannel.EventSink eventSink = this.eventSink;
        if (eventSink != null) {
            eventSink.endOfStream();
        }
        this.eventSink = null;
        this.wifi = null;
        Context context = this.context;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException(f.X);
            context = null;
        }
        context.unregisterReceiver(this.wifiScanReceiver);
        this.wifiScanReceiver = null;
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onListen(@Nullable Object arguments, @Nullable EventChannel.EventSink events) {
        this.eventSink = events;
        onScannedResultsAvailable();
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(@NonNull @NotNull MethodCall call, @NonNull @NotNull final MethodChannel.Result result) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        String str = call.method;
        if (str != null) {
            switch (str.hashCode()) {
                case -2129330689:
                    if (str.equals("startScan")) {
                        result.success(Boolean.valueOf(startScan()));
                        return;
                    }
                    break;
                case 406419180:
                    if (str.equals("canGetScannedResults")) {
                        Boolean bool = (Boolean) call.argument("askPermissions");
                        if (bool == null) {
                            result.error("InvalidArgs", "askPermissions argument is null", null);
                            return;
                        }
                        int iCanGetScannedResults = canGetScannedResults(bool.booleanValue());
                        if (iCanGetScannedResults == -1) {
                            askForLocationPermission(new Function1<AskLocPermResult, Unit>() { // from class: dev.flutternetwork.wifi.wifi_scan.WifiScanPlugin.onMethodCall.2

                                @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                                /* renamed from: dev.flutternetwork.wifi.wifi_scan.WifiScanPlugin$onMethodCall$2$WhenMappings */
                                public /* synthetic */ class WhenMappings {
                                    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                                    static {
                                        int[] iArr = new int[AskLocPermResult.values().length];
                                        try {
                                            iArr[AskLocPermResult.GRANTED.ordinal()] = 1;
                                        } catch (NoSuchFieldError unused) {
                                        }
                                        try {
                                            iArr[AskLocPermResult.UPGRADE_TO_FINE.ordinal()] = 2;
                                        } catch (NoSuchFieldError unused2) {
                                        }
                                        try {
                                            iArr[AskLocPermResult.DENIED.ordinal()] = 3;
                                        } catch (NoSuchFieldError unused3) {
                                        }
                                        try {
                                            iArr[AskLocPermResult.ERROR_NO_ACTIVITY.ordinal()] = 4;
                                        } catch (NoSuchFieldError unused4) {
                                        }
                                        $EnumSwitchMapping$0 = iArr;
                                    }
                                }

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public /* bridge */ /* synthetic */ Unit invoke(AskLocPermResult askLocPermResult) {
                                    invoke2(askLocPermResult);
                                    return Unit.INSTANCE;
                                }

                                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2(@NotNull AskLocPermResult askResult) {
                                    Intrinsics.checkNotNullParameter(askResult, "askResult");
                                    int i2 = WhenMappings.$EnumSwitchMapping$0[askResult.ordinal()];
                                    if (i2 == 1) {
                                        result.success(Integer.valueOf(this.canGetScannedResults(false)));
                                        return;
                                    }
                                    if (i2 == 2) {
                                        result.success(4);
                                    } else if (i2 == 3) {
                                        result.success(3);
                                    } else {
                                        if (i2 != 4) {
                                            return;
                                        }
                                        result.error("NullActivity", "Cannot ask for location permission.", "Looks like called from non-Activity.");
                                    }
                                }
                            });
                            return;
                        } else {
                            result.success(Integer.valueOf(iCanGetScannedResults));
                            return;
                        }
                    }
                    break;
                case 899751132:
                    if (str.equals("getScannedResults")) {
                        result.success(getScannedResults());
                        return;
                    }
                    break;
                case 1416652815:
                    if (str.equals("canStartScan")) {
                        Boolean bool2 = (Boolean) call.argument("askPermissions");
                        if (bool2 == null) {
                            result.error("InvalidArgs", "askPermissions argument is null", null);
                            return;
                        }
                        int iCanStartScan = canStartScan(bool2.booleanValue());
                        if (iCanStartScan == -1) {
                            askForLocationPermission(new Function1<AskLocPermResult, Unit>() { // from class: dev.flutternetwork.wifi.wifi_scan.WifiScanPlugin.onMethodCall.1

                                @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                                /* renamed from: dev.flutternetwork.wifi.wifi_scan.WifiScanPlugin$onMethodCall$1$WhenMappings */
                                public /* synthetic */ class WhenMappings {
                                    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                                    static {
                                        int[] iArr = new int[AskLocPermResult.values().length];
                                        try {
                                            iArr[AskLocPermResult.GRANTED.ordinal()] = 1;
                                        } catch (NoSuchFieldError unused) {
                                        }
                                        try {
                                            iArr[AskLocPermResult.UPGRADE_TO_FINE.ordinal()] = 2;
                                        } catch (NoSuchFieldError unused2) {
                                        }
                                        try {
                                            iArr[AskLocPermResult.DENIED.ordinal()] = 3;
                                        } catch (NoSuchFieldError unused3) {
                                        }
                                        try {
                                            iArr[AskLocPermResult.ERROR_NO_ACTIVITY.ordinal()] = 4;
                                        } catch (NoSuchFieldError unused4) {
                                        }
                                        $EnumSwitchMapping$0 = iArr;
                                    }
                                }

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public /* bridge */ /* synthetic */ Unit invoke(AskLocPermResult askLocPermResult) {
                                    invoke2(askLocPermResult);
                                    return Unit.INSTANCE;
                                }

                                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2(@NotNull AskLocPermResult askResult) {
                                    Intrinsics.checkNotNullParameter(askResult, "askResult");
                                    int i2 = WhenMappings.$EnumSwitchMapping$0[askResult.ordinal()];
                                    if (i2 == 1) {
                                        result.success(Integer.valueOf(this.canStartScan(false)));
                                        return;
                                    }
                                    if (i2 == 2) {
                                        result.success(4);
                                    } else if (i2 == 3) {
                                        result.success(3);
                                    } else {
                                        if (i2 != 4) {
                                            return;
                                        }
                                        result.error("NullActivity", "Cannot ask for location permission.", "Looks like called from non-Activity.");
                                    }
                                }
                            });
                            return;
                        } else {
                            result.success(Integer.valueOf(iCanStartScan));
                            return;
                        }
                    }
                    break;
            }
        }
        result.notImplemented();
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(@NotNull ActivityPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        this.activity = binding.getActivity();
        binding.addRequestPermissionsResultListener(this);
    }

    @Override // io.flutter.plugin.common.PluginRegistry.RequestPermissionsResultListener
    public boolean onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(grantResults, "grantResults");
        Log.d(this.logTag, "onRequestPermissionsResult: arguments (" + requestCode + ", " + permissions + ", " + grantResults + ")");
        String str = this.logTag;
        Map<Integer, Function1<int[], Boolean>> map = this.requestPermissionCookie;
        StringBuilder sb = new StringBuilder();
        sb.append("requestPermissionCookie: ");
        sb.append(map);
        Log.d(str, sb.toString());
        Function1<int[], Boolean> function1 = this.requestPermissionCookie.get(Integer.valueOf(requestCode));
        if (function1 != null) {
            return function1.invoke(grantResults).booleanValue();
        }
        return false;
    }
}
