package top.huic.perfect_volume_control.perfect_volume_control;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import androidx.annotation.NonNull;
import com.facebook.share.internal.MessengerShareContentUtility;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

/* loaded from: classes5.dex */
public class PerfectVolumeControlPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler {
    private AudioManager audioManager;
    private MethodChannel channel;
    private Boolean hideUI = Boolean.FALSE;

    private class VolumeReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
                PerfectVolumeControlPlugin.this.channel.invokeMethod("volumeChangeListener", Double.valueOf(PerfectVolumeControlPlugin.this.audioManager.getStreamVolume(3) / PerfectVolumeControlPlugin.this.audioManager.getStreamMaxVolume(3)));
            }
        }

        private VolumeReceiver() {
        }
    }

    public void getVolume(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
        result.success(Double.valueOf(this.audioManager.getStreamVolume(3) / this.audioManager.getStreamMaxVolume(3)));
    }

    public void hideUI(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
        this.hideUI = (Boolean) methodCall.argument(MessengerShareContentUtility.SHARE_BUTTON_HIDE);
        result.success(null);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        MethodChannel methodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "perfect_volume_control");
        this.channel = methodChannel;
        methodChannel.setMethodCallHandler(this);
        this.audioManager = (AudioManager) flutterPluginBinding.getApplicationContext().getSystemService("audio");
        VolumeReceiver volumeReceiver = new VolumeReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
        flutterPluginBinding.getApplicationContext().registerReceiver(volumeReceiver, intentFilter);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.channel.setMethodCallHandler(null);
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
        String str = methodCall.method;
        str.hashCode();
        switch (str) {
            case "hideUI":
                hideUI(methodCall, result);
                break;
            case "setVolume":
                setVolume(methodCall, result);
                break;
            case "getVolume":
                getVolume(methodCall, result);
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    public void setVolume(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
        Double d2 = (Double) methodCall.argument("volume");
        if (d2 == null) {
            result.error("-1", "Volume cannot be empty", "Volume cannot be empty");
            return;
        }
        this.audioManager.setStreamVolume(3, (int) Math.round(this.audioManager.getStreamMaxVolume(3) * d2.doubleValue()), this.hideUI.booleanValue() ? 8 : 1);
        result.success(null);
    }
}
