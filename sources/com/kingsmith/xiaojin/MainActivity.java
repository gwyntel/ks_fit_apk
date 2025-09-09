package com.kingsmith.xiaojin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.media3.exoplayer.ExoPlayer;
import com.google.gson.Gson;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.kingsmith.xiaojin.MainActivity;
import io.flutter.embedding.android.FlutterFragmentActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0012\u0010\u0017\u001a\u00020\u00142\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0014J\u0010\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u001cH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lcom/kingsmith/xiaojin/MainActivity;", "Lio/flutter/embedding/android/FlutterFragmentActivity;", "()V", "CHANNEL", "", "action", "appChannel", "Lio/flutter/plugin/common/MethodChannel;", "channel", "homeWidget", "Lcom/kingsmith/xiaojin/HomeWidget;", "isNewIntent", "", "tag", "getTag", "()Ljava/lang/String;", "setTag", "(Ljava/lang/String;)V", "widgetType", "configureFlutterEngine", "", "flutterEngine", "Lio/flutter/embedding/engine/FlutterEngine;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onNewIntent", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, "Landroid/content/Intent;", "app_externalRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class MainActivity extends FlutterFragmentActivity {
    private MethodChannel appChannel;
    private MethodChannel channel;
    private HomeWidget homeWidget;
    private boolean isNewIntent;

    @Nullable
    private String widgetType;

    @NotNull
    private String tag = "HomeWidgetChannel";

    @NotNull
    private final String CHANNEL = "HomeWidgetChannel";

    @NotNull
    private String action = "es.antonborri.home_widget.action.LAUNCH";

    /* JADX INFO: Access modifiers changed from: private */
    public static final void configureFlutterEngine$lambda$0(MainActivity this$0, MethodCall method, MethodChannel.Result result) throws InterruptedException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(result, "result");
        Log.e(this$0.tag, " setMethodCallHandler: " + method.method);
        if (!Intrinsics.areEqual(method.method, "widgetChannelNotify") || this$0.isNewIntent || this$0.widgetType == null) {
            return;
        }
        if (this$0.homeWidget == null) {
            this$0.homeWidget = new HomeWidget();
        }
        HomeWidget homeWidget = this$0.homeWidget;
        HomeWidget homeWidget2 = null;
        if (homeWidget == null) {
            Intrinsics.throwUninitializedPropertyAccessException("homeWidget");
            homeWidget = null;
        }
        String str = this$0.widgetType;
        Intrinsics.checkNotNull(str, "null cannot be cast to non-null type kotlin.String");
        homeWidget.setHomeWidgetType(str);
        String str2 = this$0.tag;
        Intent intent = this$0.getIntent();
        String action = intent != null ? intent.getAction() : null;
        Intent intent2 = this$0.getIntent();
        String type = intent2 != null ? intent2.getType() : null;
        Gson gson = new Gson();
        HomeWidget homeWidget3 = this$0.homeWidget;
        if (homeWidget3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("homeWidget");
            homeWidget3 = null;
        }
        Log.e(str2, " onCreate: " + action + " onNewIntent type: " + type + ",,," + gson.toJson(homeWidget3));
        Thread.sleep(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
        Log.e(this$0.tag, " 休眠2秒发送");
        MethodChannel methodChannel = this$0.channel;
        if (methodChannel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("channel");
            methodChannel = null;
        }
        Gson gson2 = new Gson();
        HomeWidget homeWidget4 = this$0.homeWidget;
        if (homeWidget4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("homeWidget");
        } else {
            homeWidget2 = homeWidget4;
        }
        methodChannel.invokeMethod("HomeWidgetJumpType", gson2.toJson(homeWidget2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void configureFlutterEngine$lambda$1(MainActivity this$0) {
        Uri data;
        Uri data2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String str = this$0.tag;
        Intent intent = this$0.getIntent();
        String query = null;
        Log.e(str, "configureFlutterEngine data: " + ((intent == null || (data2 = intent.getData()) == null) ? null : data2.getQuery()));
        MethodChannel methodChannel = this$0.appChannel;
        if (methodChannel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appChannel");
            methodChannel = null;
        }
        Intent intent2 = this$0.getIntent();
        if (intent2 != null && (data = intent2.getData()) != null) {
            query = data.getQuery();
        }
        methodChannel.invokeMethod("eventFromNative", query);
    }

    @Override // io.flutter.embedding.android.FlutterFragmentActivity, io.flutter.embedding.android.FlutterEngineConfigurator
    public void configureFlutterEngine(@NotNull FlutterEngine flutterEngine) {
        Intrinsics.checkNotNullParameter(flutterEngine, "flutterEngine");
        flutterEngine.getPlugins().add(new UMHelperPlugin());
        super.configureFlutterEngine(flutterEngine);
        this.channel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), this.CHANNEL);
        this.appChannel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), "app_channel");
        String str = this.tag;
        Intent intent = getIntent();
        String action = intent != null ? intent.getAction() : null;
        Intent intent2 = getIntent();
        Log.e(str, "configureFlutterEngine intent" + action + " onNewIntent data: " + (intent2 != null ? intent2.getData() : null));
        MethodChannel methodChannel = this.channel;
        if (methodChannel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("channel");
            methodChannel = null;
        }
        methodChannel.setMethodCallHandler(new MethodChannel.MethodCallHandler() { // from class: g0.a
            @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
            public final void onMethodCall(MethodCall methodCall, MethodChannel.Result result) throws InterruptedException {
                MainActivity.configureFlutterEngine$lambda$0(this.f25022a, methodCall, result);
            }
        });
        Intent intent3 = getIntent();
        Uri data = intent3 != null ? intent3.getData() : null;
        String host = data != null ? data.getHost() : null;
        Log.e(this.tag, "MethodChannel已创建 uri: " + data + " host: " + host);
        if (host == null || !host.equals("xiaojin-eu")) {
            return;
        }
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: g0.b
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.configureFlutterEngine$lambda$1(this.f25023a);
            }
        }, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }

    @NotNull
    public final String getTag() {
        return this.tag;
    }

    @Override // io.flutter.embedding.android.FlutterFragmentActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String str = this.tag;
        Intent intent = getIntent();
        String action = intent != null ? intent.getAction() : null;
        Intent intent2 = getIntent();
        Log.e(str, "MainActivity onCreate" + action + " onNewIntent data: " + (intent2 != null ? intent2.getData() : null));
        Intent intent3 = getIntent();
        if (Intrinsics.areEqual(intent3 != null ? intent3.getAction() : null, this.action)) {
            Intent intent4 = getIntent();
            this.widgetType = String.valueOf(intent4 != null ? intent4.getType() : null);
        }
    }

    @Override // io.flutter.embedding.android.FlutterFragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        super.onNewIntent(intent);
        this.isNewIntent = true;
        Log.e(this.tag, " onNewIntent: " + intent.getAction() + " onNewIntent data: " + intent.getData());
        if (!Intrinsics.areEqual(intent.getAction(), this.action)) {
            if (Intrinsics.areEqual(intent.getAction(), "android.intent.action.VIEW")) {
                if (this.channel == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("channel");
                }
                String str = this.tag;
                Uri data = intent.getData();
                Log.e(str, "configureFlutterEngine ACTION_VIEW data" + (data != null ? data.getQuery() : null));
                MethodChannel methodChannel = this.channel;
                if (methodChannel == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("channel");
                    methodChannel = null;
                }
                Uri data2 = intent.getData();
                methodChannel.invokeMethod("eventFromNative", data2 != null ? data2.getQuery() : null);
                return;
            }
            return;
        }
        this.widgetType = String.valueOf(intent.getType());
        if (this.homeWidget == null) {
            this.homeWidget = new HomeWidget();
        }
        HomeWidget homeWidget = this.homeWidget;
        if (homeWidget == null) {
            Intrinsics.throwUninitializedPropertyAccessException("homeWidget");
            homeWidget = null;
        }
        String str2 = this.widgetType;
        Intrinsics.checkNotNull(str2, "null cannot be cast to non-null type kotlin.String");
        homeWidget.setHomeWidgetType(str2);
        String str3 = this.tag;
        String action = intent.getAction();
        String type = intent.getType();
        Gson gson = new Gson();
        HomeWidget homeWidget2 = this.homeWidget;
        if (homeWidget2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("homeWidget");
            homeWidget2 = null;
        }
        Log.e(str3, " onNewIntent: " + action + " onNewIntent type: " + type + ",,," + gson.toJson(homeWidget2));
        MethodChannel methodChannel2 = this.channel;
        if (methodChannel2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("channel");
            methodChannel2 = null;
        }
        Gson gson2 = new Gson();
        Object obj = this.homeWidget;
        if (obj == null) {
            Intrinsics.throwUninitializedPropertyAccessException("homeWidget");
        } else {
            obj = obj;
        }
        methodChannel2.invokeMethod("HomeWidgetJumpType", gson2.toJson(obj));
    }

    public final void setTag(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.tag = str;
    }
}
