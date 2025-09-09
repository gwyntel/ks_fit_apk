package vn.hunghd.flutter.plugins.imagecropper;

import android.app.Activity;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

/* loaded from: classes5.dex */
public class ImageCropperPlugin implements MethodChannel.MethodCallHandler, FlutterPlugin, ActivityAware {
    private static final String CHANNEL = "plugins.hunghd.vn/image_cropper";
    private ActivityPluginBinding activityPluginBinding;
    private ImageCropperDelegate delegate;

    private void setupEngine(BinaryMessenger binaryMessenger) {
        new MethodChannel(binaryMessenger, CHANNEL).setMethodCallHandler(this);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(ActivityPluginBinding activityPluginBinding) {
        setupActivity(activityPluginBinding.getActivity());
        this.activityPluginBinding = activityPluginBinding;
        activityPluginBinding.addActivityResultListener(this.delegate);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        setupEngine(flutterPluginBinding.getBinaryMessenger());
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        this.activityPluginBinding.removeActivityResultListener(this.delegate);
        this.activityPluginBinding = null;
        this.delegate = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity();
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        if (methodCall.method.equals("cropImage")) {
            this.delegate.startCrop(methodCall, result);
        } else if (methodCall.method.equals("recoverImage")) {
            this.delegate.recoverImage(methodCall, result);
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding activityPluginBinding) {
        onAttachedToActivity(activityPluginBinding);
    }

    public ImageCropperDelegate setupActivity(Activity activity) {
        ImageCropperDelegate imageCropperDelegate = new ImageCropperDelegate(activity);
        this.delegate = imageCropperDelegate;
        return imageCropperDelegate;
    }
}
