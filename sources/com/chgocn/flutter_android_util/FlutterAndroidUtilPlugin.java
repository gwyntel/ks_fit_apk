package com.chgocn.flutter_android_util;

import android.app.Activity;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.tekartik.sqflite.Constant;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* loaded from: classes3.dex */
public class FlutterAndroidUtilPlugin implements FlutterPlugin, ActivityAware, MethodChannel.MethodCallHandler {
    private static final int REQUEST_BLUETOOTH_TURN_ON = 1077521;
    private Activity activity;
    private PluginRegistry.ActivityResultListener activityResultListener = new PluginRegistry.ActivityResultListener() { // from class: com.chgocn.flutter_android_util.FlutterAndroidUtilPlugin.1
        @Override // io.flutter.plugin.common.PluginRegistry.ActivityResultListener
        public boolean onActivityResult(int i2, int i3, @Nullable Intent intent) {
            if (i3 == -1) {
                if (i2 != FlutterAndroidUtilPlugin.REQUEST_BLUETOOTH_TURN_ON) {
                    return false;
                }
                FlutterAndroidUtilPlugin.this.methodChannel.invokeMethod("OpenBluetoothResponse", 1);
                return true;
            }
            if (i2 != FlutterAndroidUtilPlugin.REQUEST_BLUETOOTH_TURN_ON) {
                return false;
            }
            FlutterAndroidUtilPlugin.this.methodChannel.invokeMethod("OpenBluetoothResponse", 2);
            return true;
        }
    };
    private MethodChannel methodChannel;

    public static String getCountryCode(Context context) {
        String upperCase = ((TelephonyManager) context.getSystemService("phone")).getSimCountryIso().toUpperCase();
        Log.d("ss", "CountryID--->>>" + upperCase);
        return upperCase;
    }

    private void setupMethodChannel(BinaryMessenger binaryMessenger, Context context) {
        MethodChannel methodChannel = new MethodChannel(binaryMessenger, "chgocn/flutter_android_util");
        this.methodChannel = methodChannel;
        methodChannel.setMethodCallHandler(this);
    }

    String b() throws IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("logcat -d").getInputStream()));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    return sb.toString();
                }
                sb.append(line);
                sb.append('\n');
            }
        } catch (IOException e2) {
            return "EXCEPTION" + e2.toString();
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(@NonNull ActivityPluginBinding activityPluginBinding) {
        this.activity = activityPluginBinding.getActivity();
        activityPluginBinding.addActivityResultListener(this.activityResultListener);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        setupMethodChannel(flutterPluginBinding.getBinaryMessenger(), flutterPluginBinding.getApplicationContext());
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        this.activity = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.methodChannel.setMethodCallHandler(null);
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        if (methodCall.method.equals(Constant.METHOD_GET_PLATFORM_VERSION)) {
            result.success("Android " + Build.VERSION.RELEASE);
            return;
        }
        if (methodCall.method.equals("openBluetooth")) {
            if (((BluetoothManager) this.activity.getSystemService("bluetooth")).getAdapter() == null) {
                this.methodChannel.invokeMethod("OpenBluetoothResponse", 0);
            } else {
                this.activity.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), REQUEST_BLUETOOTH_TURN_ON);
            }
            result.success(null);
            return;
        }
        if (methodCall.method.equals("execCommand")) {
            result.success(ShellUtils.execCommand((String) methodCall.argument("command"), ((Boolean) methodCall.argument("isRoot")).booleanValue()).toString());
            return;
        }
        if (methodCall.method.equals("execCommands")) {
            result.success(ShellUtils.execCommand((String[]) methodCall.argument("commands"), ((Boolean) methodCall.argument("isRoot")).booleanValue()).toString());
            return;
        }
        if (methodCall.method.equals("getLogcat")) {
            result.success(b());
        } else if (methodCall.method.equals("getCountryCode")) {
            result.success(getCountryCode(this.activity));
        } else {
            result.notImplemented();
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding activityPluginBinding) {
        Log.d("FlutterAndroidUtilPlugin", "onReattachedToActivityForConfigChanges: ");
        this.activity = activityPluginBinding.getActivity();
    }
}
