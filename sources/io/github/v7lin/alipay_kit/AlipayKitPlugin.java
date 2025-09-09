package io.github.v7lin.alipay_kit;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.m.u.n;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.lang.ref.WeakReference;
import java.util.Map;

/* loaded from: classes4.dex */
public class AlipayKitPlugin implements FlutterPlugin, ActivityAware, MethodChannel.MethodCallHandler {
    private Activity activity;
    private Context applicationContext;
    private MethodChannel channel;

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(@NonNull ActivityPluginBinding activityPluginBinding) {
        this.activity = activityPluginBinding.getActivity();
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        MethodChannel methodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "v7lin.github.io/alipay_kit");
        this.channel = methodChannel;
        methodChannel.setMethodCallHandler(this);
        this.applicationContext = flutterPluginBinding.getApplicationContext();
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        this.activity = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity();
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.channel.setMethodCallHandler(null);
        this.channel = null;
        this.applicationContext = null;
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
        boolean z2 = false;
        if ("isInstalled".equals(methodCall.method)) {
            try {
                if (this.applicationContext.getPackageManager().getPackageInfo(n.f9817b, 64) != null) {
                    z2 = true;
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
            result.success(Boolean.valueOf(z2));
            return;
        }
        if ("pay".equals(methodCall.method)) {
            final String str = (String) methodCall.argument("orderInfo");
            final boolean zBooleanValue = ((Boolean) methodCall.argument("isShowLoading")).booleanValue();
            final WeakReference weakReference = new WeakReference(this.activity);
            final WeakReference weakReference2 = new WeakReference(this.channel);
            new AsyncTask<String, String, Map<String, String>>() { // from class: io.github.v7lin.alipay_kit.AlipayKitPlugin.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                /* renamed from: a, reason: merged with bridge method [inline-methods] */
                public Map doInBackground(String... strArr) {
                    Activity activity = (Activity) weakReference.get();
                    if (activity == null || activity.isFinishing()) {
                        return null;
                    }
                    return new PayTask(activity).payV2(str, zBooleanValue);
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                /* renamed from: b, reason: merged with bridge method [inline-methods] */
                public void onPostExecute(Map map) {
                    if (map != null) {
                        Activity activity = (Activity) weakReference.get();
                        MethodChannel methodChannel = (MethodChannel) weakReference2.get();
                        if (activity == null || activity.isFinishing() || methodChannel == null) {
                            return;
                        }
                        methodChannel.invokeMethod("onPayResp", map);
                    }
                }
            }.execute(new String[0]);
            result.success(null);
            return;
        }
        if (!"auth".equals(methodCall.method)) {
            result.notImplemented();
            return;
        }
        final String str2 = (String) methodCall.argument("authInfo");
        final boolean zBooleanValue2 = ((Boolean) methodCall.argument("isShowLoading")).booleanValue();
        final WeakReference weakReference3 = new WeakReference(this.activity);
        final WeakReference weakReference4 = new WeakReference(this.channel);
        new AsyncTask<String, String, Map<String, String>>() { // from class: io.github.v7lin.alipay_kit.AlipayKitPlugin.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Map doInBackground(String... strArr) {
                Activity activity = (Activity) weakReference3.get();
                if (activity == null || activity.isFinishing()) {
                    return null;
                }
                return new AuthTask(activity).authV2(str2, zBooleanValue2);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            /* renamed from: b, reason: merged with bridge method [inline-methods] */
            public void onPostExecute(Map map) {
                if (map != null) {
                    Activity activity = (Activity) weakReference3.get();
                    MethodChannel methodChannel = (MethodChannel) weakReference4.get();
                    if (activity == null || activity.isFinishing() || methodChannel == null) {
                        return;
                    }
                    methodChannel.invokeMethod("onAuthResp", map);
                }
            }
        }.execute(new String[0]);
        result.success(null);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding activityPluginBinding) {
        onAttachedToActivity(activityPluginBinding);
    }
}
