package dev.fluttercommunity.plus.device_info;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import androidx.core.app.NotificationCompat;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0002J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Ldev/fluttercommunity/plus/device_info/MethodCallHandlerImpl;", "Lio/flutter/plugin/common/MethodChannel$MethodCallHandler;", "packageManager", "Landroid/content/pm/PackageManager;", "activityManager", "Landroid/app/ActivityManager;", "contentResolver", "Landroid/content/ContentResolver;", "(Landroid/content/pm/PackageManager;Landroid/app/ActivityManager;Landroid/content/ContentResolver;)V", "isEmulator", "", "()Z", "getSystemFeatures", "", "", "onMethodCall", "", NotificationCompat.CATEGORY_CALL, "Lio/flutter/plugin/common/MethodCall;", "result", "Lio/flutter/plugin/common/MethodChannel$Result;", "device_info_plus_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nMethodCallHandlerImpl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MethodCallHandlerImpl.kt\ndev/fluttercommunity/plus/device_info/MethodCallHandlerImpl\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,115:1\n4117#2:116\n4217#2,2:117\n1549#3:119\n1620#3,3:120\n*S KotlinDebug\n*F\n+ 1 MethodCallHandlerImpl.kt\ndev/fluttercommunity/plus/device_info/MethodCallHandlerImpl\n*L\n92#1:116\n92#1:117,2\n93#1:119\n93#1:120,3\n*E\n"})
/* loaded from: classes4.dex */
public final class MethodCallHandlerImpl implements MethodChannel.MethodCallHandler {

    @NotNull
    private final ActivityManager activityManager;

    @NotNull
    private final ContentResolver contentResolver;

    @NotNull
    private final PackageManager packageManager;

    public MethodCallHandlerImpl(@NotNull PackageManager packageManager, @NotNull ActivityManager activityManager, @NotNull ContentResolver contentResolver) {
        Intrinsics.checkNotNullParameter(packageManager, "packageManager");
        Intrinsics.checkNotNullParameter(activityManager, "activityManager");
        Intrinsics.checkNotNullParameter(contentResolver, "contentResolver");
        this.packageManager = packageManager;
        this.activityManager = activityManager;
        this.contentResolver = contentResolver;
    }

    private final List<String> getSystemFeatures() {
        FeatureInfo[] systemAvailableFeatures = this.packageManager.getSystemAvailableFeatures();
        Intrinsics.checkNotNullExpressionValue(systemAvailableFeatures, "getSystemAvailableFeatures(...)");
        ArrayList arrayList = new ArrayList();
        for (FeatureInfo featureInfo : systemAvailableFeatures) {
            if (featureInfo.name != null) {
                arrayList.add(featureInfo);
            }
        }
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList, 10));
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(((FeatureInfo) it.next()).name);
        }
        return arrayList2;
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x001f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final boolean isEmulator() {
        /*
            r6 = this;
            java.lang.String r0 = android.os.Build.BRAND
            java.lang.String r1 = "BRAND"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            java.lang.String r1 = "generic"
            r2 = 0
            r3 = 2
            r4 = 0
            boolean r0 = kotlin.text.StringsKt.startsWith$default(r0, r1, r2, r3, r4)
            if (r0 == 0) goto L1f
            java.lang.String r0 = android.os.Build.DEVICE
            java.lang.String r5 = "DEVICE"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r5)
            boolean r0 = kotlin.text.StringsKt.startsWith$default(r0, r1, r2, r3, r4)
            if (r0 != 0) goto Lb5
        L1f:
            java.lang.String r0 = android.os.Build.FINGERPRINT
            java.lang.String r5 = "FINGERPRINT"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r5)
            boolean r1 = kotlin.text.StringsKt.startsWith$default(r0, r1, r2, r3, r4)
            if (r1 != 0) goto Lb5
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r5)
            java.lang.String r1 = "unknown"
            boolean r0 = kotlin.text.StringsKt.startsWith$default(r0, r1, r2, r3, r4)
            if (r0 != 0) goto Lb5
            java.lang.String r0 = android.os.Build.HARDWARE
            java.lang.String r1 = "HARDWARE"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            java.lang.String r5 = "goldfish"
            boolean r5 = kotlin.text.StringsKt.contains$default(r0, r5, r2, r3, r4)
            if (r5 != 0) goto Lb5
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            java.lang.String r1 = "ranchu"
            boolean r0 = kotlin.text.StringsKt.contains$default(r0, r1, r2, r3, r4)
            if (r0 != 0) goto Lb5
            java.lang.String r0 = android.os.Build.MODEL
            java.lang.String r1 = "MODEL"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            java.lang.String r5 = "google_sdk"
            boolean r5 = kotlin.text.StringsKt.contains$default(r0, r5, r2, r3, r4)
            if (r5 != 0) goto Lb5
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            java.lang.String r5 = "Emulator"
            boolean r5 = kotlin.text.StringsKt.contains$default(r0, r5, r2, r3, r4)
            if (r5 != 0) goto Lb5
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            java.lang.String r1 = "Android SDK built for x86"
            boolean r0 = kotlin.text.StringsKt.contains$default(r0, r1, r2, r3, r4)
            if (r0 != 0) goto Lb5
            java.lang.String r0 = android.os.Build.MANUFACTURER
            java.lang.String r1 = "MANUFACTURER"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            java.lang.String r1 = "Genymotion"
            boolean r0 = kotlin.text.StringsKt.contains$default(r0, r1, r2, r3, r4)
            if (r0 != 0) goto Lb5
            java.lang.String r0 = android.os.Build.PRODUCT
            java.lang.String r1 = "PRODUCT"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            java.lang.String r5 = "sdk"
            boolean r5 = kotlin.text.StringsKt.contains$default(r0, r5, r2, r3, r4)
            if (r5 != 0) goto Lb5
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            java.lang.String r5 = "vbox86p"
            boolean r5 = kotlin.text.StringsKt.contains$default(r0, r5, r2, r3, r4)
            if (r5 != 0) goto Lb5
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            java.lang.String r5 = "emulator"
            boolean r5 = kotlin.text.StringsKt.contains$default(r0, r5, r2, r3, r4)
            if (r5 != 0) goto Lb5
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            java.lang.String r1 = "simulator"
            boolean r0 = kotlin.text.StringsKt.contains$default(r0, r1, r2, r3, r4)
            if (r0 == 0) goto Lb6
        Lb5:
            r2 = 1
        Lb6:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: dev.fluttercommunity.plus.device_info.MethodCallHandlerImpl.isEmulator():boolean");
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(@NotNull MethodCall call, @NotNull MethodChannel.Result result) {
        String serial;
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        if (!call.method.equals("getDeviceInfo")) {
            result.notImplemented();
            return;
        }
        HashMap map = new HashMap();
        String BOARD = Build.BOARD;
        Intrinsics.checkNotNullExpressionValue(BOARD, "BOARD");
        map.put("board", BOARD);
        String BOOTLOADER = Build.BOOTLOADER;
        Intrinsics.checkNotNullExpressionValue(BOOTLOADER, "BOOTLOADER");
        map.put("bootloader", BOOTLOADER);
        String BRAND = Build.BRAND;
        Intrinsics.checkNotNullExpressionValue(BRAND, "BRAND");
        map.put("brand", BRAND);
        String DEVICE = Build.DEVICE;
        Intrinsics.checkNotNullExpressionValue(DEVICE, "DEVICE");
        map.put("device", DEVICE);
        String DISPLAY = Build.DISPLAY;
        Intrinsics.checkNotNullExpressionValue(DISPLAY, "DISPLAY");
        map.put("display", DISPLAY);
        String FINGERPRINT = Build.FINGERPRINT;
        Intrinsics.checkNotNullExpressionValue(FINGERPRINT, "FINGERPRINT");
        map.put("fingerprint", FINGERPRINT);
        String HARDWARE = Build.HARDWARE;
        Intrinsics.checkNotNullExpressionValue(HARDWARE, "HARDWARE");
        map.put("hardware", HARDWARE);
        String HOST = Build.HOST;
        Intrinsics.checkNotNullExpressionValue(HOST, "HOST");
        map.put("host", HOST);
        String ID = Build.ID;
        Intrinsics.checkNotNullExpressionValue(ID, "ID");
        map.put("id", ID);
        String MANUFACTURER = Build.MANUFACTURER;
        Intrinsics.checkNotNullExpressionValue(MANUFACTURER, "MANUFACTURER");
        map.put("manufacturer", MANUFACTURER);
        String MODEL = Build.MODEL;
        Intrinsics.checkNotNullExpressionValue(MODEL, "MODEL");
        map.put("model", MODEL);
        String PRODUCT = Build.PRODUCT;
        Intrinsics.checkNotNullExpressionValue(PRODUCT, "PRODUCT");
        map.put("product", PRODUCT);
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 25) {
            String string = Settings.Global.getString(this.contentResolver, "device_name");
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            map.put("name", string);
        }
        String[] SUPPORTED_32_BIT_ABIS = Build.SUPPORTED_32_BIT_ABIS;
        Intrinsics.checkNotNullExpressionValue(SUPPORTED_32_BIT_ABIS, "SUPPORTED_32_BIT_ABIS");
        map.put("supported32BitAbis", CollectionsKt.listOf(Arrays.copyOf(SUPPORTED_32_BIT_ABIS, SUPPORTED_32_BIT_ABIS.length)));
        String[] SUPPORTED_64_BIT_ABIS = Build.SUPPORTED_64_BIT_ABIS;
        Intrinsics.checkNotNullExpressionValue(SUPPORTED_64_BIT_ABIS, "SUPPORTED_64_BIT_ABIS");
        map.put("supported64BitAbis", CollectionsKt.listOf(Arrays.copyOf(SUPPORTED_64_BIT_ABIS, SUPPORTED_64_BIT_ABIS.length)));
        String[] SUPPORTED_ABIS = Build.SUPPORTED_ABIS;
        Intrinsics.checkNotNullExpressionValue(SUPPORTED_ABIS, "SUPPORTED_ABIS");
        map.put("supportedAbis", CollectionsKt.listOf(Arrays.copyOf(SUPPORTED_ABIS, SUPPORTED_ABIS.length)));
        String TAGS = Build.TAGS;
        Intrinsics.checkNotNullExpressionValue(TAGS, "TAGS");
        map.put("tags", TAGS);
        String TYPE = Build.TYPE;
        Intrinsics.checkNotNullExpressionValue(TYPE, "TYPE");
        map.put("type", TYPE);
        map.put("isPhysicalDevice", Boolean.valueOf(!isEmulator()));
        map.put("systemFeatures", getSystemFeatures());
        HashMap map2 = new HashMap();
        String BASE_OS = Build.VERSION.BASE_OS;
        Intrinsics.checkNotNullExpressionValue(BASE_OS, "BASE_OS");
        map2.put("baseOS", BASE_OS);
        map2.put("previewSdkInt", Integer.valueOf(Build.VERSION.PREVIEW_SDK_INT));
        String SECURITY_PATCH = Build.VERSION.SECURITY_PATCH;
        Intrinsics.checkNotNullExpressionValue(SECURITY_PATCH, "SECURITY_PATCH");
        map2.put("securityPatch", SECURITY_PATCH);
        String CODENAME = Build.VERSION.CODENAME;
        Intrinsics.checkNotNullExpressionValue(CODENAME, "CODENAME");
        map2.put("codename", CODENAME);
        String INCREMENTAL = Build.VERSION.INCREMENTAL;
        Intrinsics.checkNotNullExpressionValue(INCREMENTAL, "INCREMENTAL");
        map2.put("incremental", INCREMENTAL);
        String RELEASE = Build.VERSION.RELEASE;
        Intrinsics.checkNotNullExpressionValue(RELEASE, "RELEASE");
        map2.put("release", RELEASE);
        map2.put("sdkInt", Integer.valueOf(i2));
        map.put("version", map2);
        map.put("isLowRamDevice", Boolean.valueOf(this.activityManager.isLowRamDevice()));
        if (i2 >= 26) {
            try {
                serial = Build.getSerial();
            } catch (SecurityException unused) {
                serial = "unknown";
            }
            Intrinsics.checkNotNull(serial);
            map.put("serialNumber", serial);
        } else {
            String SERIAL = Build.SERIAL;
            Intrinsics.checkNotNullExpressionValue(SERIAL, "SERIAL");
            map.put("serialNumber", SERIAL);
        }
        result.success(map);
    }
}
