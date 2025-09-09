package com.amap.flutter.location;

import android.content.Context;
import android.text.TextUtils;
import com.amap.api.location.AMapLocationClient;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public class AMapFlutterLocationPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler, EventChannel.StreamHandler {
    private static final String CHANNEL_METHOD_LOCATION = "amap_flutter_location";
    private static final String CHANNEL_STREAM_LOCATION = "amap_flutter_location_stream";
    public static EventChannel.EventSink mEventSink;
    private Context mContext = null;
    private Map<String, AMapLocationClientImpl> locationClientMap = new ConcurrentHashMap(8);

    private void destroy(Map map) {
        AMapLocationClientImpl locationClientImp = getLocationClientImp(map);
        if (locationClientImp != null) {
            locationClientImp.destroy();
            this.locationClientMap.remove(getPluginKeyFromArgs(map));
        }
    }

    private AMapLocationClientImpl getLocationClientImp(Map map) {
        if (this.locationClientMap == null) {
            this.locationClientMap = new ConcurrentHashMap(8);
        }
        String pluginKeyFromArgs = getPluginKeyFromArgs(map);
        if (TextUtils.isEmpty(pluginKeyFromArgs)) {
            return null;
        }
        if (!this.locationClientMap.containsKey(pluginKeyFromArgs)) {
            this.locationClientMap.put(pluginKeyFromArgs, new AMapLocationClientImpl(this.mContext, pluginKeyFromArgs, mEventSink));
        }
        return this.locationClientMap.get(pluginKeyFromArgs);
    }

    private String getPluginKeyFromArgs(Map map) {
        if (map == null) {
            return null;
        }
        try {
            return (String) map.get("pluginKey");
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    private void setApiKey(Map map) {
        if (map == null || !map.containsKey("android") || TextUtils.isEmpty((String) map.get("android"))) {
            return;
        }
        AMapLocationClient.setApiKey((String) map.get("android"));
    }

    private void setLocationOption(Map map) {
        AMapLocationClientImpl locationClientImp = getLocationClientImp(map);
        if (locationClientImp != null) {
            locationClientImp.setLocationOption(map);
        }
    }

    private void startLocation(Map map) {
        AMapLocationClientImpl locationClientImp = getLocationClientImp(map);
        if (locationClientImp != null) {
            locationClientImp.startLocation();
        }
    }

    private void stopLocation(Map map) {
        AMapLocationClientImpl locationClientImp = getLocationClientImp(map);
        if (locationClientImp != null) {
            locationClientImp.stopLocation();
        }
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        if (this.mContext == null) {
            this.mContext = flutterPluginBinding.getApplicationContext();
            new MethodChannel(flutterPluginBinding.getBinaryMessenger(), CHANNEL_METHOD_LOCATION).setMethodCallHandler(this);
            new EventChannel(flutterPluginBinding.getBinaryMessenger(), CHANNEL_STREAM_LOCATION).setStreamHandler(this);
        }
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onCancel(Object obj) {
        Iterator<Map.Entry<String, AMapLocationClientImpl>> it = this.locationClientMap.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().stopLocation();
        }
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        Iterator<Map.Entry<String, AMapLocationClientImpl>> it = this.locationClientMap.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().destroy();
        }
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onListen(Object obj, EventChannel.EventSink eventSink) {
        mEventSink = eventSink;
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        String str = methodCall.method;
        str.hashCode();
        switch (str) {
            case "setLocationOption":
                setLocationOption((Map) methodCall.arguments);
                break;
            case "setApiKey":
                setApiKey((Map) methodCall.arguments);
                break;
            case "stopLocation":
                stopLocation((Map) methodCall.arguments);
                break;
            case "destroy":
                destroy((Map) methodCall.arguments);
                break;
            case "startLocation":
                startLocation((Map) methodCall.arguments);
                break;
            default:
                result.notImplemented();
                break;
        }
    }
}
