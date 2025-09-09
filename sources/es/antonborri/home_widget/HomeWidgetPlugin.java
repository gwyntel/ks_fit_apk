package es.antonborri.home_widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tekartik.sqflite.Constant;
import com.umeng.analytics.pro.f;
import com.yc.utesdk.watchface.open.WatchFaceUtil;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 -2\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u0005:\u0001-B\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0011\u001a\u00020\u00102\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0012\u0010\u0018\u001a\u00020\u00152\b\b\u0001\u0010\u0019\u001a\u00020\u001aH\u0016J\u0012\u0010\u001b\u001a\u00020\u00152\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u0015H\u0016J\b\u0010\u001f\u001a\u00020\u0015H\u0016J\u0012\u0010 \u001a\u00020\u00152\b\b\u0001\u0010\u0016\u001a\u00020\u001aH\u0016J\u001c\u0010!\u001a\u00020\u00152\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J\u001c\u0010\"\u001a\u00020\u00152\b\b\u0001\u0010#\u001a\u00020$2\b\b\u0001\u0010%\u001a\u00020&H\u0016J\u0010\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0016J\u0010\u0010+\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010,\u001a\u00020\u0015H\u0002R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Les/antonborri/home_widget/HomeWidgetPlugin;", "Lio/flutter/embedding/engine/plugins/FlutterPlugin;", "Lio/flutter/plugin/common/MethodChannel$MethodCallHandler;", "Lio/flutter/embedding/engine/plugins/activity/ActivityAware;", "Lio/flutter/plugin/common/EventChannel$StreamHandler;", "Lio/flutter/plugin/common/PluginRegistry$NewIntentListener;", "()V", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", "channel", "Lio/flutter/plugin/common/MethodChannel;", f.X, "Landroid/content/Context;", "eventChannel", "Lio/flutter/plugin/common/EventChannel;", "receiver", "Landroid/content/BroadcastReceiver;", "createReceiver", "events", "Lio/flutter/plugin/common/EventChannel$EventSink;", "onAttachedToActivity", "", "binding", "Lio/flutter/embedding/engine/plugins/activity/ActivityPluginBinding;", "onAttachedToEngine", "flutterPluginBinding", "Lio/flutter/embedding/engine/plugins/FlutterPlugin$FlutterPluginBinding;", "onCancel", Constant.PARAM_SQL_ARGUMENTS, "", "onDetachedFromActivity", "onDetachedFromActivityForConfigChanges", "onDetachedFromEngine", "onListen", "onMethodCall", NotificationCompat.CATEGORY_CALL, "Lio/flutter/plugin/common/MethodCall;", "result", "Lio/flutter/plugin/common/MethodChannel$Result;", "onNewIntent", "", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, "Landroid/content/Intent;", "onReattachedToActivityForConfigChanges", "unregisterReceiver", "Companion", "home_widget_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class HomeWidgetPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler, ActivityAware, EventChannel.StreamHandler, PluginRegistry.NewIntentListener {

    @NotNull
    private static final String CALLBACK_DISPATCHER_HANDLE = "callbackDispatcherHandle";

    @NotNull
    private static final String CALLBACK_HANDLE = "callbackHandle";

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final String INTERNAL_PREFERENCES = "InternalHomeWidgetPreferences";

    @NotNull
    private static final String PREFERENCES = "HomeWidgetPreferences";

    @Nullable
    private Activity activity;
    private MethodChannel channel;
    private Context context;
    private EventChannel eventChannel;

    @Nullable
    private BroadcastReceiver receiver;

    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\u000e\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000bJ \u0010\u000f\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\rH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Les/antonborri/home_widget/HomeWidgetPlugin$Companion;", "", "()V", "CALLBACK_DISPATCHER_HANDLE", "", "CALLBACK_HANDLE", "INTERNAL_PREFERENCES", "PREFERENCES", "getData", "Landroid/content/SharedPreferences;", f.X, "Landroid/content/Context;", "getDispatcherHandle", "", "getHandle", "saveCallbackHandle", "", "dispatcher", "handle", "home_widget_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void saveCallbackHandle(Context context, long dispatcher, long handle) {
            context.getSharedPreferences(HomeWidgetPlugin.INTERNAL_PREFERENCES, 0).edit().putLong(HomeWidgetPlugin.CALLBACK_DISPATCHER_HANDLE, dispatcher).putLong(HomeWidgetPlugin.CALLBACK_HANDLE, handle).apply();
        }

        @NotNull
        public final SharedPreferences getData(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            SharedPreferences sharedPreferences = context.getSharedPreferences(HomeWidgetPlugin.PREFERENCES, 0);
            Intrinsics.checkNotNullExpressionValue(sharedPreferences, "getSharedPreferences(...)");
            return sharedPreferences;
        }

        public final long getDispatcherHandle(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return context.getSharedPreferences(HomeWidgetPlugin.INTERNAL_PREFERENCES, 0).getLong(HomeWidgetPlugin.CALLBACK_DISPATCHER_HANDLE, 0L);
        }

        public final long getHandle(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return context.getSharedPreferences(HomeWidgetPlugin.INTERNAL_PREFERENCES, 0).getLong(HomeWidgetPlugin.CALLBACK_HANDLE, 0L);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private final BroadcastReceiver createReceiver(final EventChannel.EventSink events) {
        return new BroadcastReceiver() { // from class: es.antonborri.home_widget.HomeWidgetPlugin.createReceiver.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(@Nullable Context context, @Nullable Intent intent) {
                EventChannel.EventSink eventSink;
                Uri data;
                Object string = null;
                if (!StringsKt.equals$default(intent != null ? intent.getAction() : null, HomeWidgetLaunchIntent.INSTANCE.getHOME_WIDGET_LAUNCH_ACTION(), false, 2, null) || (eventSink = events) == null) {
                    return;
                }
                if (intent != null && (data = intent.getData()) != null) {
                    string = data.toString();
                }
                if (string == null) {
                    string = Boolean.TRUE;
                }
                eventSink.success(string);
            }
        };
    }

    private final void unregisterReceiver() {
        try {
            if (this.receiver != null) {
                Context context = this.context;
                if (context == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(f.X);
                    context = null;
                }
                context.unregisterReceiver(this.receiver);
            }
        } catch (IllegalArgumentException unused) {
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(@NotNull ActivityPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        this.activity = binding.getActivity();
        binding.addOnNewIntentListener(this);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull @NotNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        Intrinsics.checkNotNullParameter(flutterPluginBinding, "flutterPluginBinding");
        MethodChannel methodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "home_widget");
        this.channel = methodChannel;
        methodChannel.setMethodCallHandler(this);
        EventChannel eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), "home_widget/updates");
        this.eventChannel = eventChannel;
        eventChannel.setStreamHandler(this);
        Context applicationContext = flutterPluginBinding.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        this.context = applicationContext;
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onCancel(@Nullable Object arguments) {
        unregisterReceiver();
        this.receiver = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        unregisterReceiver();
        this.activity = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        unregisterReceiver();
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
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onListen(@Nullable Object arguments, @Nullable EventChannel.EventSink events) {
        this.receiver = createReceiver(events);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(@NonNull @NotNull MethodCall call, @NonNull @NotNull MethodChannel.Result result) throws ClassNotFoundException {
        Intent intent;
        String action;
        String string;
        Intent intent2;
        Uri data;
        Context context;
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        Log.e("HomeWidgetPlugin", call.method);
        String str = call.method;
        if (str != null) {
            Context context2 = null;
            switch (str.hashCode()) {
                case -2070339408:
                    if (str.equals("initiallyLaunchedFromHomeWidget")) {
                        Activity activity = this.activity;
                        if (activity == null || (intent = activity.getIntent()) == null || (action = intent.getAction()) == null || !action.equals(HomeWidgetLaunchIntent.INSTANCE.getHOME_WIDGET_LAUNCH_ACTION())) {
                            result.success(null);
                            return;
                        }
                        Activity activity2 = this.activity;
                        if (activity2 == null || (intent2 = activity2.getIntent()) == null || (data = intent2.getData()) == null || (string = data.toString()) == null) {
                            string = "";
                        }
                        result.success(string);
                        return;
                    }
                    break;
                case -2065784469:
                    if (str.equals("saveWidgetData")) {
                        if (!call.hasArgument("id") || !call.hasArgument("data")) {
                            result.error("-1", "InvalidArguments saveWidgetData must be called with id and data", new IllegalArgumentException());
                            return;
                        }
                        String str2 = (String) call.argument("id");
                        Object objArgument = call.argument("data");
                        Context context3 = this.context;
                        if (context3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException(f.X);
                        } else {
                            context2 = context3;
                        }
                        SharedPreferences.Editor editorEdit = context2.getSharedPreferences(PREFERENCES, 0).edit();
                        if (objArgument == null) {
                            editorEdit.remove(str2);
                        } else if (objArgument instanceof Boolean) {
                            editorEdit.putBoolean(str2, ((Boolean) objArgument).booleanValue());
                        } else if (objArgument instanceof Float) {
                            editorEdit.putFloat(str2, ((Number) objArgument).floatValue());
                        } else if (objArgument instanceof String) {
                            editorEdit.putString(str2, (String) objArgument);
                        } else if (objArgument instanceof Double) {
                            editorEdit.putLong(str2, Double.doubleToRawLongBits(((Number) objArgument).doubleValue()));
                        } else if (objArgument instanceof Integer) {
                            editorEdit.putInt(str2, ((Number) objArgument).intValue());
                        } else {
                            result.error("-10", "Invalid Type " + objArgument.getClass().getSimpleName() + ". Supported types are Boolean, Float, String, Double, Long", new IllegalArgumentException());
                        }
                        result.success(Boolean.valueOf(editorEdit.commit()));
                        return;
                    }
                    break;
                case -836303763:
                    if (str.equals("updateWidget")) {
                        String str3 = (String) call.argument("qualifiedAndroidName");
                        String str4 = (String) call.argument("android");
                        if (str4 == null) {
                            str4 = (String) call.argument("name");
                        }
                        if (str3 == null) {
                            try {
                                Context context4 = this.context;
                                if (context4 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException(f.X);
                                    context4 = null;
                                }
                                str3 = context4.getPackageName() + "." + str4;
                            } catch (ClassNotFoundException e2) {
                                result.error("-3", "No Widget found with Name " + str4 + ". Argument 'name' must be the same as your AppWidgetProvider you wish to update", e2);
                                return;
                            }
                        }
                        Class<?> cls = Class.forName(str3);
                        Context context5 = this.context;
                        if (context5 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException(f.X);
                            context5 = null;
                        }
                        Intent intent3 = new Intent(context5, cls);
                        intent3.setAction("android.appwidget.action.APPWIDGET_UPDATE");
                        Context context6 = this.context;
                        if (context6 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException(f.X);
                            context6 = null;
                        }
                        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context6.getApplicationContext());
                        Context context7 = this.context;
                        if (context7 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException(f.X);
                            context7 = null;
                        }
                        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context7, cls));
                        Intrinsics.checkNotNullExpressionValue(appWidgetIds, "getAppWidgetIds(...)");
                        intent3.putExtra("appWidgetIds", appWidgetIds);
                        Context context8 = this.context;
                        if (context8 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException(f.X);
                        } else {
                            context2 = context8;
                        }
                        context2.sendBroadcast(intent3);
                        result.success(Boolean.TRUE);
                        return;
                    }
                    break;
                case -605441020:
                    if (str.equals("getWidgetData")) {
                        if (!call.hasArgument("id")) {
                            result.error(WatchFaceUtil.IMAGE_WATCH_FACE_BLE_ID, "InvalidArguments getWidgetData must be called with id", new IllegalArgumentException());
                            return;
                        }
                        String str5 = (String) call.argument("id");
                        Object objArgument2 = call.argument("defaultValue");
                        Context context9 = this.context;
                        if (context9 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException(f.X);
                        } else {
                            context2 = context9;
                        }
                        Object obj = context2.getSharedPreferences(PREFERENCES, 0).getAll().get(str5);
                        if (obj != null) {
                            objArgument2 = obj;
                        }
                        if (objArgument2 instanceof Long) {
                            result.success(Double.valueOf(Double.longBitsToDouble(((Number) objArgument2).longValue())));
                            return;
                        } else {
                            result.success(objArgument2);
                            return;
                        }
                    }
                    break;
                case 1174565782:
                    if (str.equals("registerBackgroundCallback")) {
                        Object obj2 = call.arguments;
                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.collections.Iterable<*>");
                        Object obj3 = CollectionsKt.toList((Iterable) obj2).get(0);
                        Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Number");
                        long jLongValue = ((Number) obj3).longValue();
                        Object obj4 = call.arguments;
                        Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type kotlin.collections.Iterable<*>");
                        Object obj5 = CollectionsKt.toList((Iterable) obj4).get(1);
                        Intrinsics.checkNotNull(obj5, "null cannot be cast to non-null type kotlin.Number");
                        long jLongValue2 = ((Number) obj5).longValue();
                        Companion companion = INSTANCE;
                        Context context10 = this.context;
                        if (context10 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException(f.X);
                            context = null;
                        } else {
                            context = context10;
                        }
                        companion.saveCallbackHandle(context, jLongValue, jLongValue2);
                        result.success(Boolean.TRUE);
                        return;
                    }
                    break;
                case 1411403610:
                    if (str.equals("isRequestPinWidgetSupported")) {
                        if (Build.VERSION.SDK_INT < 26) {
                            result.success(Boolean.FALSE);
                            return;
                        }
                        Context context11 = this.context;
                        if (context11 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException(f.X);
                        } else {
                            context2 = context11;
                        }
                        result.success(Boolean.valueOf(AppWidgetManager.getInstance(context2.getApplicationContext()).isRequestPinAppWidgetSupported()));
                        return;
                    }
                    break;
                case 1902315675:
                    if (str.equals("setAppGroupId")) {
                        result.success(Boolean.TRUE);
                        return;
                    }
                    break;
                case 2122273386:
                    if (str.equals("requestPinWidget")) {
                        if (Build.VERSION.SDK_INT < 26) {
                            result.success(null);
                            return;
                        }
                        String str6 = (String) call.argument("qualifiedAndroidName");
                        String str7 = (String) call.argument("android");
                        if (str7 == null) {
                            str7 = (String) call.argument("name");
                        }
                        if (str6 == null) {
                            try {
                                Context context12 = this.context;
                                if (context12 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException(f.X);
                                    context12 = null;
                                }
                                str6 = context12.getPackageName() + "." + str7;
                            } catch (ClassNotFoundException e3) {
                                result.error("-4", "No Widget found with Name " + str7 + ". Argument 'name' must be the same as your AppWidgetProvider you wish to update", e3);
                                return;
                            }
                        }
                        Class<?> cls2 = Class.forName(str6);
                        Context context13 = this.context;
                        if (context13 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException(f.X);
                            context13 = null;
                        }
                        ComponentName componentName = new ComponentName(context13, cls2);
                        Context context14 = this.context;
                        if (context14 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException(f.X);
                            context14 = null;
                        }
                        AppWidgetManager appWidgetManager2 = AppWidgetManager.getInstance(context14.getApplicationContext());
                        if (appWidgetManager2.isRequestPinAppWidgetSupported()) {
                            appWidgetManager2.requestPinAppWidget(componentName, null, null);
                        }
                        result.success(null);
                        return;
                    }
                    break;
            }
        }
        result.notImplemented();
    }

    @Override // io.flutter.plugin.common.PluginRegistry.NewIntentListener
    public boolean onNewIntent(@NotNull Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        BroadcastReceiver broadcastReceiver = this.receiver;
        if (broadcastReceiver != null) {
            Context context = this.context;
            if (context == null) {
                Intrinsics.throwUninitializedPropertyAccessException(f.X);
                context = null;
            }
            broadcastReceiver.onReceive(context, intent);
        }
        return this.receiver != null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(@NotNull ActivityPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        this.activity = binding.getActivity();
        binding.addOnNewIntentListener(this);
    }
}
