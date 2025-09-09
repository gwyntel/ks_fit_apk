package com.zaihui.installplugin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.taobao.accs.common.Constants;
import com.tekartik.sqflite.Constant;
import com.umeng.analytics.pro.f;
import com.zaihui.installplugin.InstallPlugin;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u001c\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\n0\u00122\u000e\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\u0012J&\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0002J\u001c\u0010\u0019\u001a\u00020\u00152\b\u0010\u001a\u001a\u0004\u0018\u00010\n2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0002J\u001a\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\bH\u0002J\b\u0010\u001c\u001a\u00020\u0015H\u0002J\u0012\u0010\u001d\u001a\u00020\u00102\b\u0010\u001e\u001a\u0004\u0018\u00010\nH\u0007J\u0012\u0010\u001f\u001a\u00020\u00152\b\u0010 \u001a\u0004\u0018\u00010\nH\u0002J\u0010\u0010!\u001a\u00020\u00152\u0006\u0010\"\u001a\u00020#H\u0016J\u0010\u0010$\u001a\u00020\u00152\u0006\u0010%\u001a\u00020&H\u0016J\b\u0010'\u001a\u00020\u0015H\u0016J\b\u0010(\u001a\u00020\u0015H\u0016J\u0010\u0010)\u001a\u00020\u00152\u0006\u0010\"\u001a\u00020&H\u0016J\u0018\u0010*\u001a\u00020\u00152\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0016J\u0010\u0010/\u001a\u00020\u00152\u0006\u0010\"\u001a\u00020#H\u0016J\u0010\u00100\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0006\u00101\u001a\u00020\u0015J\u001a\u00101\u001a\u00020\u00152\b\u00102\u001a\u0004\u0018\u00010\n2\b\u00103\u001a\u0004\u0018\u00010\nJ\u0006\u00104\u001a\u00020\u0015R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082D¢\u0006\u0002\n\u0000¨\u00065"}, d2 = {"Lcom/zaihui/installplugin/InstallPlugin;", "Lio/flutter/embedding/engine/plugins/FlutterPlugin;", "Lio/flutter/plugin/common/MethodChannel$MethodCallHandler;", "Lio/flutter/embedding/engine/plugins/activity/ActivityAware;", "()V", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", "apkFile", "Ljava/io/File;", "appId", "", "channel", "Lio/flutter/plugin/common/MethodChannel;", "installRequestCode", "", "canRequestPackageInstalls", "", "getInstallMarket", "", Constants.KEY_PACKAGES, "install24", "", f.X, "Landroid/content/Context;", "file", "installApk", "filePath", "installBelow24", "installPermission", "isPackageExist", "packageName", "launchBrowse", "url", "onAttachedToActivity", "binding", "Lio/flutter/embedding/engine/plugins/activity/ActivityPluginBinding;", "onAttachedToEngine", "flutterPluginBinding", "Lio/flutter/embedding/engine/plugins/FlutterPlugin$FlutterPluginBinding;", "onDetachedFromActivity", "onDetachedFromActivityForConfigChanges", "onDetachedFromEngine", "onMethodCall", NotificationCompat.CATEGORY_CALL, "Lio/flutter/plugin/common/MethodCall;", "result", "Lio/flutter/plugin/common/MethodChannel$Result;", "onReattachedToActivityForConfigChanges", "showSettingPackageInstall", "toMarket", "marketPackageName", "marketClassName", "toMarketGooglePlay", "install_plugin_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class InstallPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler, ActivityAware {

    @Nullable
    private Activity activity;

    @Nullable
    private File apkFile;

    @Nullable
    private String appId;
    private MethodChannel channel;
    private final int installRequestCode = 1234;

    private final boolean canRequestPackageInstalls(Activity activity) {
        return Build.VERSION.SDK_INT <= 26 || activity.getPackageManager().canRequestPackageInstalls();
    }

    private final void install24(Context context, File file, String appId) {
        if (context == null) {
            throw new NullPointerException("context is null!");
        }
        if (file == null) {
            throw new NullPointerException("file is null!");
        }
        if (appId == null) {
            throw new NullPointerException("appId is null!");
        }
        Log.d("SettingPackageInstall", "install24");
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(268435456);
        intent.addFlags(1);
        Uri uriForFile = FileProvider.getUriForFile(context, appId + ".fileProvider.install", file);
        Intrinsics.checkNotNullExpressionValue(uriForFile, "getUriForFile(...)");
        intent.setDataAndType(uriForFile, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    private final void installApk(String filePath, String appId) throws FileNotFoundException {
        if (filePath == null) {
            throw new NullPointerException("fillPath is null!");
        }
        Activity activity = this.activity;
        if (activity == null) {
            throw new NullPointerException("context is null!");
        }
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath + " is not exist! or check permission");
        }
        if (Build.VERSION.SDK_INT < 24) {
            installBelow24(activity, file);
        } else {
            if (canRequestPackageInstalls(activity)) {
                install24(activity, file, appId);
                return;
            }
            showSettingPackageInstall(activity);
            this.apkFile = file;
            this.appId = appId;
        }
    }

    private final void installBelow24(Context context, File file) {
        Log.d("SettingPackageInstall", ">=installBelow24");
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(268435456);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    private final void installPermission() {
        if (Build.VERSION.SDK_INT >= 24) {
            Activity activity = this.activity;
            if (activity == null) {
                throw new NullPointerException("context is null!");
            }
            showSettingPackageInstall(activity);
        }
    }

    private final void launchBrowse(String url) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(url));
        Activity activity = this.activity;
        if (activity != null) {
            activity.startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onAttachedToActivity$lambda$0(InstallPlugin this$0, int i2, int i3, Intent intent) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Log.d("ActivityResultListener", "requestCode=" + i2 + ", resultCode = " + i3 + ", intent = " + intent);
        if (i3 != -1 || i2 != this$0.installRequestCode) {
            return false;
        }
        this$0.install24(this$0.activity, this$0.apkFile, this$0.appId);
        return true;
    }

    private final void showSettingPackageInstall(Activity activity) {
        if (Build.VERSION.SDK_INT < 26) {
            throw new RuntimeException("VERSION.SDK_INT < O");
        }
        Log.d("SettingPackageInstall", ">= Build.VERSION_CODES.O");
        Intent intent = new Intent("android.settings.MANAGE_UNKNOWN_APP_SOURCES");
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivityForResult(intent, this.installRequestCode);
    }

    @NotNull
    public final List<String> getInstallMarket(@Nullable List<String> packages) {
        ArrayList arrayList = new ArrayList();
        if (packages != null) {
            int size = packages.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (isPackageExist(packages.get(i2))) {
                    arrayList.add(packages.get(i2));
                }
            }
        }
        return arrayList;
    }

    @SuppressLint({"WrongConstant"})
    public final boolean isPackageExist(@Nullable String packageName) {
        Activity activity = this.activity;
        PackageManager packageManager = activity != null ? activity.getPackageManager() : null;
        Intent intent = new Intent().setPackage(packageName);
        Intrinsics.checkNotNullExpressionValue(intent, "setPackage(...)");
        List<ResolveInfo> listQueryIntentActivities = packageManager != null ? packageManager.queryIntentActivities(intent, 32) : null;
        return listQueryIntentActivities != null && listQueryIntentActivities.size() >= 1;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(@NotNull ActivityPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        this.activity = binding.getActivity();
        binding.addActivityResultListener(new PluginRegistry.ActivityResultListener() { // from class: m0.a
            @Override // io.flutter.plugin.common.PluginRegistry.ActivityResultListener
            public final boolean onActivityResult(int i2, int i3, Intent intent) {
                return InstallPlugin.onAttachedToActivity$lambda$0(this.f26021a, i2, i3, intent);
            }
        });
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NotNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        Intrinsics.checkNotNullParameter(flutterPluginBinding, "flutterPluginBinding");
        MethodChannel methodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "install_plugin");
        this.channel = methodChannel;
        methodChannel.setMethodCallHandler(this);
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
    public void onDetachedFromEngine(@NotNull FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        MethodChannel methodChannel = this.channel;
        if (methodChannel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("channel");
            methodChannel = null;
        }
        methodChannel.setMethodCallHandler(null);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(@NotNull MethodCall call, @NotNull MethodChannel.Result result) throws PackageManager.NameNotFoundException {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        String str = call.method;
        if (str != null) {
            switch (str.hashCode()) {
                case -1966242153:
                    if (str.equals("toMarket")) {
                        String str2 = (String) call.argument("marketPackageName");
                        String str3 = (String) call.argument("marketClassName");
                        if (StringsKt.equals$default(str3, "com.google.android.gms", false, 2, null)) {
                            toMarketGooglePlay();
                            return;
                        } else {
                            toMarket(str2, str3);
                            return;
                        }
                    }
                    break;
                case 686218487:
                    if (str.equals("checkPermission")) {
                        launchBrowse((String) call.argument("url"));
                        result.success("Success");
                        return;
                    }
                    break;
                case 900412033:
                    if (str.equals("installApk")) {
                        String str4 = (String) call.argument("filePath");
                        String str5 = (String) call.argument("appId");
                        Log.d("android plugin", "installApk " + str4 + " " + str5);
                        try {
                            installApk(str4, str5);
                            result.success("Success");
                            return;
                        } catch (Throwable th) {
                            result.error(th.getClass().getSimpleName(), th.getMessage(), null);
                            return;
                        }
                    }
                    break;
                case 1316323841:
                    if (str.equals("getInstallMarket")) {
                        result.success(getInstallMarket((List) call.argument(Constants.KEY_PACKAGES)));
                        return;
                    }
                    break;
                case 1385449135:
                    if (str.equals(Constant.METHOD_GET_PLATFORM_VERSION)) {
                        result.success("Android " + Build.VERSION.RELEASE);
                        return;
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
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x001f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void toMarket() throws android.content.pm.PackageManager.NameNotFoundException {
        /*
            r4 = this;
            android.app.Activity r0 = r4.activity
            if (r0 != 0) goto L5
            return
        L5:
            r1 = 0
            r2 = 0
            if (r0 == 0) goto L1f
            android.content.pm.PackageManager r0 = r0.getPackageManager()     // Catch: android.content.ActivityNotFoundException -> L1d
            if (r0 == 0) goto L1f
            android.app.Activity r3 = r4.activity     // Catch: android.content.ActivityNotFoundException -> L1d
            kotlin.jvm.internal.Intrinsics.checkNotNull(r3)     // Catch: android.content.ActivityNotFoundException -> L1d
            java.lang.String r3 = r3.getPackageName()     // Catch: android.content.ActivityNotFoundException -> L1d
            android.content.pm.PackageInfo r0 = r0.getPackageInfo(r3, r2)     // Catch: android.content.ActivityNotFoundException -> L1d
            goto L20
        L1d:
            r0 = move-exception
            goto L4d
        L1f:
            r0 = r1
        L20:
            if (r0 == 0) goto L24
            java.lang.String r1 = r0.packageName     // Catch: android.content.ActivityNotFoundException -> L1d
        L24:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: android.content.ActivityNotFoundException -> L1d
            r0.<init>()     // Catch: android.content.ActivityNotFoundException -> L1d
            java.lang.String r3 = "market://details?id="
            r0.append(r3)     // Catch: android.content.ActivityNotFoundException -> L1d
            r0.append(r1)     // Catch: android.content.ActivityNotFoundException -> L1d
            java.lang.String r0 = r0.toString()     // Catch: android.content.ActivityNotFoundException -> L1d
            android.net.Uri r0 = android.net.Uri.parse(r0)     // Catch: android.content.ActivityNotFoundException -> L1d
            android.content.Intent r1 = new android.content.Intent     // Catch: android.content.ActivityNotFoundException -> L1d
            java.lang.String r3 = "android.intent.action.VIEW"
            r1.<init>(r3, r0)     // Catch: android.content.ActivityNotFoundException -> L1d
            r0 = 268435456(0x10000000, float:2.524355E-29)
            r1.addFlags(r0)     // Catch: android.content.ActivityNotFoundException -> L1d
            android.app.Activity r0 = r4.activity     // Catch: android.content.ActivityNotFoundException -> L1d
            if (r0 == 0) goto L5b
            r0.startActivity(r1)     // Catch: android.content.ActivityNotFoundException -> L1d
            goto L5b
        L4d:
            r0.printStackTrace()
            android.app.Activity r0 = r4.activity
            java.lang.String r1 = "您的手机没有安装应用商店"
            android.widget.Toast r0 = android.widget.Toast.makeText(r0, r1, r2)
            r0.show()
        L5b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zaihui.installplugin.InstallPlugin.toMarket():void");
    }

    public final void toMarketGooglePlay() throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo;
        PackageManager packageManager;
        Activity activity = this.activity;
        if (activity == null) {
            return;
        }
        if (activity == null || (packageManager = activity.getPackageManager()) == null) {
            packageInfo = null;
        } else {
            Activity activity2 = this.activity;
            Intrinsics.checkNotNull(activity2);
            packageInfo = packageManager.getPackageInfo(activity2.getPackageName(), 0);
        }
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + (packageInfo != null ? packageInfo.packageName : null)));
        intent.setPackage("com.android.vending");
        Activity activity3 = this.activity;
        if (activity3 != null) {
            activity3.startActivity(intent);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x001f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void toMarket(@org.jetbrains.annotations.Nullable java.lang.String r5, @org.jetbrains.annotations.Nullable java.lang.String r6) throws android.content.pm.PackageManager.NameNotFoundException {
        /*
            r4 = this;
            android.app.Activity r0 = r4.activity
            if (r0 != 0) goto L5
            return
        L5:
            r1 = 0
            r2 = 0
            if (r0 == 0) goto L1f
            android.content.pm.PackageManager r0 = r0.getPackageManager()     // Catch: android.content.ActivityNotFoundException -> L1d
            if (r0 == 0) goto L1f
            android.app.Activity r3 = r4.activity     // Catch: android.content.ActivityNotFoundException -> L1d
            kotlin.jvm.internal.Intrinsics.checkNotNull(r3)     // Catch: android.content.ActivityNotFoundException -> L1d
            java.lang.String r3 = r3.getPackageName()     // Catch: android.content.ActivityNotFoundException -> L1d
            android.content.pm.PackageInfo r0 = r0.getPackageInfo(r3, r2)     // Catch: android.content.ActivityNotFoundException -> L1d
            goto L20
        L1d:
            r6 = move-exception
            goto L54
        L1f:
            r0 = r1
        L20:
            if (r0 == 0) goto L24
            java.lang.String r1 = r0.packageName     // Catch: android.content.ActivityNotFoundException -> L1d
        L24:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: android.content.ActivityNotFoundException -> L1d
            r0.<init>()     // Catch: android.content.ActivityNotFoundException -> L1d
            java.lang.String r3 = "market://details?id="
            r0.append(r3)     // Catch: android.content.ActivityNotFoundException -> L1d
            r0.append(r1)     // Catch: android.content.ActivityNotFoundException -> L1d
            java.lang.String r0 = r0.toString()     // Catch: android.content.ActivityNotFoundException -> L1d
            android.net.Uri r0 = android.net.Uri.parse(r0)     // Catch: android.content.ActivityNotFoundException -> L1d
            if (r5 == 0) goto L3f
            int r1 = r5.length()     // Catch: android.content.ActivityNotFoundException -> L1d
        L3f:
            if (r6 == 0) goto L45
            int r6 = r6.length()     // Catch: android.content.ActivityNotFoundException -> L1d
        L45:
            android.content.Intent r6 = new android.content.Intent     // Catch: android.content.ActivityNotFoundException -> L1d
            java.lang.String r1 = "android.intent.action.VIEW"
            r6.<init>(r1, r0)     // Catch: android.content.ActivityNotFoundException -> L1d
            android.app.Activity r0 = r4.activity     // Catch: android.content.ActivityNotFoundException -> L1d
            if (r0 == 0) goto L76
            r0.startActivity(r6)     // Catch: android.content.ActivityNotFoundException -> L1d
            goto L76
        L54:
            r6.printStackTrace()
            android.app.Activity r6 = r4.activity
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "您的手机没有安装应用商店("
            r0.append(r1)
            r0.append(r5)
            java.lang.String r5 = ")"
            r0.append(r5)
            java.lang.String r5 = r0.toString()
            android.widget.Toast r5 = android.widget.Toast.makeText(r6, r5, r2)
            r5.show()
        L76:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zaihui.installplugin.InstallPlugin.toMarket(java.lang.String, java.lang.String):void");
    }
}
