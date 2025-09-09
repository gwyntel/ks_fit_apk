package com.alibaba.fplayer.flutter_aliplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import com.aliyun.aio.aio_env.AlivcEnv;
import com.aliyun.common.AlivcBase;
import com.aliyun.player.AliPlayerGlobalSettings;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.nio.ByteBuffer;
import java.util.Map;

@SuppressLint({"LongLogTag"})
/* loaded from: classes2.dex */
public class FlutterAliPlayerGlobalSettings implements FlutterPlugin, MethodChannel.MethodCallHandler {
    private final String TAG = "FlutterAliPlayerGlobalSettings";
    private FlutterInvokeCallback flutterInvokeCallback;
    private Context mContext;
    private EventChannel.EventSink mEventSink;
    private MethodChannel mMethodChannel;

    public FlutterAliPlayerGlobalSettings(Context context, FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.mContext = context;
        this.flutterInvokeCallback = new FlutterInvokeCallback(context, flutterPluginBinding);
        MethodChannel methodChannel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "plugins.flutter_global_setting");
        this.mMethodChannel = methodChannel;
        methodChannel.setMethodCallHandler(this);
    }

    private void setGlobalEnvironment(int i2) {
        if (i2 == 1) {
            AlivcBase.getEnvironmentManager().setGlobalEnvironment(AlivcEnv.GlobalEnv.ENV_CN);
            return;
        }
        if (i2 == 2) {
            AlivcBase.getEnvironmentManager().setGlobalEnvironment(AlivcEnv.GlobalEnv.ENV_SEA);
        } else if (i2 != 3) {
            AlivcBase.getEnvironmentManager().setGlobalEnvironment(AlivcEnv.GlobalEnv.ENV_GLOBAL_DEFAULT);
        } else {
            AlivcBase.getEnvironmentManager().setGlobalEnvironment(AlivcEnv.GlobalEnv.ENV_GLOBAL_OVERSEA);
        }
    }

    private void setOption(int i2, Object obj) {
        if (obj instanceof Integer) {
            AliPlayerGlobalSettings.setOption(i2, ((Integer) obj).intValue());
        } else if (obj instanceof String) {
            AliPlayerGlobalSettings.setOption(i2, (String) obj);
        }
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
        String str = methodCall.method;
        str.hashCode();
        switch (str) {
            case "setNetworkCallback":
                AliPlayerGlobalSettings.setNetworkCallback(new AliPlayerGlobalSettings.OnNetworkCallback() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerGlobalSettings.2
                    @Override // com.aliyun.player.AliPlayerGlobalSettings.OnNetworkCallback
                    public boolean onNetworkDataProcess(String str2, ByteBuffer byteBuffer, long j2, ByteBuffer byteBuffer2) {
                        Object objInvokeFourParameterFlutterCallback = FlutterAliPlayerGlobalSettings.this.flutterInvokeCallback.invokeFourParameterFlutterCallback(str2, byteBuffer, Long.valueOf(j2), byteBuffer2, "onNetworkDataProcess");
                        if (objInvokeFourParameterFlutterCallback == null) {
                            return false;
                        }
                        return ((Boolean) objInvokeFourParameterFlutterCallback).booleanValue();
                    }
                });
                break;
            case "enableEnhancedHttpDns":
                AliPlayerGlobalSettings.enableEnhancedHttpDns(((Boolean) methodCall.arguments()).booleanValue());
                break;
            case "setCacheUrlHashCallback":
                AliPlayerGlobalSettings.setCacheUrlHashCallback(new AliPlayerGlobalSettings.OnGetUrlHashCallback() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerGlobalSettings.1
                    @Override // com.aliyun.player.AliPlayerGlobalSettings.OnGetUrlHashCallback
                    public String getUrlHashCallback(String str2) {
                        String str3 = (String) FlutterAliPlayerGlobalSettings.this.flutterInvokeCallback.invokeOneParameterFlutterCallback(str2, "getUrlHashCallback");
                        return str3 == null ? str2 : str3;
                    }
                });
                break;
            case "disableCrashUpload":
                AliPlayerGlobalSettings.disableCrashUpload(((Boolean) methodCall.arguments()).booleanValue());
                break;
            case "setOption":
                Map map = (Map) methodCall.arguments();
                Integer num = (Integer) map.get("opt1");
                setOption(num.intValue(), map.get("opt2"));
                result.success(null);
                break;
            case "setAdaptiveDecoderGetBackupURLCallback":
                AliPlayerGlobalSettings.setAdaptiveDecoderGetBackupURLCallback(new AliPlayerGlobalSettings.OnGetBackupUrlCallback() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerGlobalSettings.3
                    @Override // com.aliyun.player.AliPlayerGlobalSettings.OnGetBackupUrlCallback
                    public String getBackupUrlCallback(int i2, int i3, String str2) {
                        String str3 = (String) FlutterAliPlayerGlobalSettings.this.flutterInvokeCallback.invokeThreeParameterFlutterCallback(Integer.valueOf(i2), Integer.valueOf(i3), str2, "getBackupUrlCallback");
                        return str3 == null ? str2 : str3;
                    }
                });
                break;
            case "setGlobalEnvironment":
                setGlobalEnvironment(((Integer) methodCall.arguments()).intValue());
                result.success(null);
                break;
        }
    }
}
