package io.flutter.plugins.webviewflutter;

import android.content.Context;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.platform.PlatformViewRegistry;
import io.flutter.plugins.webviewflutter.DownloadListenerHostApiImpl;
import io.flutter.plugins.webviewflutter.FlutterAssetManager;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;
import io.flutter.plugins.webviewflutter.InstanceManager;
import io.flutter.plugins.webviewflutter.JavaScriptChannelHostApiImpl;
import io.flutter.plugins.webviewflutter.WebChromeClientHostApiImpl;
import io.flutter.plugins.webviewflutter.WebSettingsHostApiImpl;
import io.flutter.plugins.webviewflutter.WebStorageHostApiImpl;
import io.flutter.plugins.webviewflutter.WebViewClientHostApiImpl;
import io.flutter.plugins.webviewflutter.WebViewHostApiImpl;

/* loaded from: classes4.dex */
public class WebViewFlutterPlugin implements FlutterPlugin, ActivityAware {

    @Nullable
    private InstanceManager instanceManager;
    private JavaScriptChannelHostApiImpl javaScriptChannelHostApi;
    private FlutterPlugin.FlutterPluginBinding pluginBinding;
    private WebViewHostApiImpl webViewHostApi;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setUp$0(Void r02) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setUp$1(BinaryMessenger binaryMessenger, long j2) {
        new GeneratedAndroidWebView.JavaObjectFlutterApi(binaryMessenger).dispose(Long.valueOf(j2), new GeneratedAndroidWebView.JavaObjectFlutterApi.Reply() { // from class: io.flutter.plugins.webviewflutter.k5
            @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.JavaObjectFlutterApi.Reply
            public final void reply(Object obj) {
                WebViewFlutterPlugin.lambda$setUp$0((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setUp$2() {
        this.instanceManager.clear();
    }

    private void setUp(final BinaryMessenger binaryMessenger, PlatformViewRegistry platformViewRegistry, Context context, FlutterAssetManager flutterAssetManager) {
        this.instanceManager = InstanceManager.create(new InstanceManager.FinalizationListener() { // from class: io.flutter.plugins.webviewflutter.l5
            @Override // io.flutter.plugins.webviewflutter.InstanceManager.FinalizationListener
            public final void onFinalize(long j2) {
                WebViewFlutterPlugin.lambda$setUp$1(binaryMessenger, j2);
            }
        });
        b0.c(binaryMessenger, new GeneratedAndroidWebView.InstanceManagerHostApi() { // from class: io.flutter.plugins.webviewflutter.m5
            @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.InstanceManagerHostApi
            public final void clear() {
                this.f25254a.lambda$setUp$2();
            }
        });
        platformViewRegistry.registerViewFactory("plugins.flutter.io/webview", new FlutterViewFactory(this.instanceManager));
        this.webViewHostApi = new WebViewHostApiImpl(this.instanceManager, binaryMessenger, new WebViewHostApiImpl.WebViewProxy(), context);
        this.javaScriptChannelHostApi = new JavaScriptChannelHostApiImpl(this.instanceManager, new JavaScriptChannelHostApiImpl.JavaScriptChannelCreator(), new JavaScriptChannelFlutterApiImpl(binaryMessenger, this.instanceManager), new Handler(context.getMainLooper()));
        e0.c(binaryMessenger, new JavaObjectHostApiImpl(this.instanceManager));
        GeneratedAndroidWebView.WebViewHostApi.CC.B(binaryMessenger, this.webViewHostApi);
        h0.c(binaryMessenger, this.javaScriptChannelHostApi);
        j2.d(binaryMessenger, new WebViewClientHostApiImpl(this.instanceManager, new WebViewClientHostApiImpl.WebViewClientCreator(), new WebViewClientFlutterApiImpl(binaryMessenger, this.instanceManager)));
        e1.h(binaryMessenger, new WebChromeClientHostApiImpl(this.instanceManager, new WebChromeClientHostApiImpl.WebChromeClientCreator(), new WebChromeClientFlutterApiImpl(binaryMessenger, this.instanceManager)));
        m.c(binaryMessenger, new DownloadListenerHostApiImpl(this.instanceManager, new DownloadListenerHostApiImpl.DownloadListenerCreator(), new DownloadListenerFlutterApiImpl(binaryMessenger, this.instanceManager)));
        u1.q(binaryMessenger, new WebSettingsHostApiImpl(this.instanceManager, new WebSettingsHostApiImpl.WebSettingsCreator()));
        q.d(binaryMessenger, new FlutterAssetManagerHostApiImpl(flutterAssetManager));
        GeneratedAndroidWebView.CookieManagerHostApi.CC.f(binaryMessenger, new CookieManagerHostApiImpl(binaryMessenger, this.instanceManager));
        x1.d(binaryMessenger, new WebStorageHostApiImpl(this.instanceManager, new WebStorageHostApiImpl.WebStorageCreator()));
        l0.d(binaryMessenger, new PermissionRequestHostApiImpl(binaryMessenger, this.instanceManager));
        u.c(binaryMessenger, new GeolocationPermissionsCallbackHostApiImpl(binaryMessenger, this.instanceManager));
        j.c(binaryMessenger, new CustomViewCallbackHostApiImpl(binaryMessenger, this.instanceManager));
        z.e(binaryMessenger, new HttpAuthHandlerHostApiImpl(binaryMessenger, this.instanceManager));
    }

    private void updateContext(Context context) {
        this.webViewHostApi.setContext(context);
        this.javaScriptChannelHostApi.setPlatformThreadHandler(new Handler(context.getMainLooper()));
    }

    @Nullable
    public InstanceManager getInstanceManager() {
        return this.instanceManager;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(@NonNull ActivityPluginBinding activityPluginBinding) {
        updateContext(activityPluginBinding.getActivity());
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.pluginBinding = flutterPluginBinding;
        setUp(flutterPluginBinding.getBinaryMessenger(), flutterPluginBinding.getPlatformViewRegistry(), flutterPluginBinding.getApplicationContext(), new FlutterAssetManager.PluginBindingFlutterAssetManager(flutterPluginBinding.getApplicationContext().getAssets(), flutterPluginBinding.getFlutterAssets()));
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        updateContext(this.pluginBinding.getApplicationContext());
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        updateContext(this.pluginBinding.getApplicationContext());
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        InstanceManager instanceManager = this.instanceManager;
        if (instanceManager != null) {
            instanceManager.stopFinalizationListener();
            this.instanceManager = null;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding activityPluginBinding) {
        updateContext(activityPluginBinding.getActivity());
    }
}
