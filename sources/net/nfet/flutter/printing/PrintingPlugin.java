package net.nfet.flutter.printing;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodChannel;

/* loaded from: classes5.dex */
public class PrintingPlugin implements FlutterPlugin, ActivityAware {
    private MethodChannel channel;
    private Context context;
    private PrintingHandler handler;

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(@NonNull ActivityPluginBinding activityPluginBinding) {
        if (this.context != null) {
            this.context = null;
        }
        Activity activity = activityPluginBinding.getActivity();
        this.context = activity;
        onAttachedToActivity(activity);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.context = flutterPluginBinding.getApplicationContext();
        onAttachedToEngine(flutterPluginBinding.getBinaryMessenger());
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        this.channel.setMethodCallHandler(null);
        this.context = null;
        this.handler = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity();
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.channel.setMethodCallHandler(null);
        this.channel = null;
        this.handler = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding activityPluginBinding) {
        this.context = null;
        Activity activity = activityPluginBinding.getActivity();
        this.context = activity;
        onAttachedToActivity(activity);
    }

    private void onAttachedToEngine(BinaryMessenger binaryMessenger) {
        this.channel = new MethodChannel(binaryMessenger, "net.nfet.printing");
        if (this.context != null) {
            PrintingHandler printingHandler = new PrintingHandler(this.context, this.channel);
            this.handler = printingHandler;
            this.channel.setMethodCallHandler(printingHandler);
        }
    }

    private void onAttachedToActivity(Context context) {
        if (context == null || this.channel == null) {
            return;
        }
        PrintingHandler printingHandler = new PrintingHandler(context, this.channel);
        this.handler = printingHandler;
        this.channel.setMethodCallHandler(printingHandler);
    }
}
