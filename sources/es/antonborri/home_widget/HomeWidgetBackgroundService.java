package es.antonborri.home_widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.umeng.analytics.pro.f;
import es.antonborri.home_widget.HomeWidgetBackgroundService;
import es.antonborri.home_widget.HomeWidgetPlugin;
import io.flutter.FlutterInjector;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.view.FlutterCallbackInformation;
import java.util.ArrayDeque;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u001c2\u00020\u00012\u00020\u0002:\u0001\u001cB\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0005H\u0016¢\u0006\u0004\b\u0006\u0010\u0004J\u001f\u0010\u000b\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\tH\u0016¢\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\rH\u0014¢\u0006\u0004\b\u000f\u0010\u0010R \u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\u00118\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0014\u0010\u0015R\u0016\u0010\u0017\u001a\u00020\u00168\u0002@\u0002X\u0082.¢\u0006\u0006\n\u0004\b\u0017\u0010\u0018R\u0016\u0010\u001a\u001a\u00020\u00198\u0002@\u0002X\u0082.¢\u0006\u0006\n\u0004\b\u001a\u0010\u001b¨\u0006\u001d"}, d2 = {"Les/antonborri/home_widget/HomeWidgetBackgroundService;", "Lio/flutter/plugin/common/MethodChannel$MethodCallHandler;", "Landroidx/core/app/JobIntentService;", "<init>", "()V", "", "onCreate", "Lio/flutter/plugin/common/MethodCall;", NotificationCompat.CATEGORY_CALL, "Lio/flutter/plugin/common/MethodChannel$Result;", "result", "onMethodCall", "(Lio/flutter/plugin/common/MethodCall;Lio/flutter/plugin/common/MethodChannel$Result;)V", "Landroid/content/Intent;", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, "e", "(Landroid/content/Intent;)V", "Ljava/util/ArrayDeque;", "", "", "queue", "Ljava/util/ArrayDeque;", "Lio/flutter/plugin/common/MethodChannel;", "channel", "Lio/flutter/plugin/common/MethodChannel;", "Landroid/content/Context;", f.X, "Landroid/content/Context;", "Companion", "home_widget_release"}, k = 1, mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class HomeWidgetBackgroundService extends JobIntentService implements MethodChannel.MethodCallHandler {

    @NotNull
    private static final String TAG = "HomeWidgetService";

    @Nullable
    private static FlutterEngine engine;
    private MethodChannel channel;
    private Context context;

    @NotNull
    private final ArrayDeque<List<Object>> queue = new ArrayDeque<>();

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private static final int JOB_ID = (int) UUID.randomUUID().getMostSignificantBits();

    @NotNull
    private static final AtomicBoolean serviceStarted = new AtomicBoolean(false);

    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Les/antonborri/home_widget/HomeWidgetBackgroundService$Companion;", "", "()V", "JOB_ID", "", "TAG", "", "engine", "Lio/flutter/embedding/engine/FlutterEngine;", "serviceStarted", "Ljava/util/concurrent/atomic/AtomicBoolean;", "enqueueWork", "", f.X, "Landroid/content/Context;", "work", "Landroid/content/Intent;", "home_widget_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public final void enqueueWork(@NotNull Context context, @NotNull Intent work) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(work, "work");
            JobIntentService.enqueueWork(context, (Class<?>) HomeWidgetBackgroundService.class, HomeWidgetBackgroundService.JOB_ID, work);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onHandleWork$lambda$3$lambda$2(HomeWidgetBackgroundService this$0, List args) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(args, "$args");
        MethodChannel methodChannel = this$0.channel;
        if (methodChannel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("channel");
            methodChannel = null;
        }
        methodChannel.invokeMethod("", args);
    }

    @Override // androidx.core.app.JobIntentService
    protected void e(Intent intent) {
        String string;
        Intrinsics.checkNotNullParameter(intent, "intent");
        Uri data = intent.getData();
        if (data == null || (string = data.toString()) == null) {
            string = "";
        }
        HomeWidgetPlugin.Companion companion = HomeWidgetPlugin.INSTANCE;
        Context context = this.context;
        Context context2 = null;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException(f.X);
            context = null;
        }
        final List<Object> listListOf = CollectionsKt.listOf(Long.valueOf(companion.getHandle(context)), string);
        AtomicBoolean atomicBoolean = serviceStarted;
        synchronized (atomicBoolean) {
            try {
                if (atomicBoolean.get()) {
                    Context context3 = this.context;
                    if (context3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(f.X);
                    } else {
                        context2 = context3;
                    }
                    new Handler(context2.getMainLooper()).post(new Runnable() { // from class: o0.a
                        @Override // java.lang.Runnable
                        public final void run() {
                            HomeWidgetBackgroundService.onHandleWork$lambda$3$lambda$2(this.f26065a, listListOf);
                        }
                    });
                } else {
                    this.queue.add(listListOf);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // androidx.core.app.JobIntentService, android.app.Service
    public void onCreate() {
        DartExecutor dartExecutor;
        super.onCreate();
        synchronized (serviceStarted) {
            try {
                this.context = this;
                if (engine == null) {
                    long dispatcherHandle = HomeWidgetPlugin.INSTANCE.getDispatcherHandle(this);
                    if (dispatcherHandle == 0) {
                        Log.e(TAG, "No callbackHandle saved. Did you call HomeWidget.registerBackgroundCallback?");
                    }
                    Context context = this.context;
                    Context context2 = null;
                    if (context == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(f.X);
                        context = null;
                    }
                    engine = new FlutterEngine(context);
                    FlutterCallbackInformation flutterCallbackInformationLookupCallbackInformation = FlutterCallbackInformation.lookupCallbackInformation(dispatcherHandle);
                    if (flutterCallbackInformationLookupCallbackInformation == null) {
                        return;
                    }
                    Intrinsics.checkNotNull(flutterCallbackInformationLookupCallbackInformation);
                    Context context3 = this.context;
                    if (context3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(f.X);
                    } else {
                        context2 = context3;
                    }
                    DartExecutor.DartCallback dartCallback = new DartExecutor.DartCallback(context2.getAssets(), FlutterInjector.instance().flutterLoader().findAppBundlePath(), flutterCallbackInformationLookupCallbackInformation);
                    FlutterEngine flutterEngine = engine;
                    if (flutterEngine != null && (dartExecutor = flutterEngine.getDartExecutor()) != null) {
                        dartExecutor.executeDartCallback(dartCallback);
                    }
                }
                Unit unit = Unit.INSTANCE;
                FlutterEngine flutterEngine2 = engine;
                Intrinsics.checkNotNull(flutterEngine2);
                MethodChannel methodChannel = new MethodChannel(flutterEngine2.getDartExecutor().getBinaryMessenger(), "home_widget/background");
                this.channel = methodChannel;
                methodChannel.setMethodCallHandler(this);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(@NotNull MethodCall call, @NotNull MethodChannel.Result result) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        if (Intrinsics.areEqual(call.method, "HomeWidget.backgroundInitialized")) {
            synchronized (serviceStarted) {
                while (!this.queue.isEmpty()) {
                    try {
                        MethodChannel methodChannel = this.channel;
                        if (methodChannel == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("channel");
                            methodChannel = null;
                        }
                        methodChannel.invokeMethod("", this.queue.remove());
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                serviceStarted.set(true);
                Unit unit = Unit.INSTANCE;
            }
        }
    }
}
