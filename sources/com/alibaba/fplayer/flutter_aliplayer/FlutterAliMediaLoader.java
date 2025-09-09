package com.alibaba.fplayer.flutter_aliplayer;

import android.content.Context;
import androidx.annotation.NonNull;
import com.aliyun.loader.MediaLoader;
import com.aliyun.player.bean.ErrorInfo;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class FlutterAliMediaLoader implements FlutterPlugin, MethodChannel.MethodCallHandler, EventChannel.StreamHandler {
    private Context mContext;
    private EventChannel mEventChannel;
    private EventChannel.EventSink mEventSink;
    private final MediaLoader mMediaLoader;
    private MethodChannel mMethodChannel;

    public FlutterAliMediaLoader(Context context, FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.mContext = context;
        MediaLoader mediaLoader = MediaLoader.getInstance();
        this.mMediaLoader = mediaLoader;
        MethodChannel methodChannel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "plugins.flutter_aliplayer_media_loader");
        this.mMethodChannel = methodChannel;
        methodChannel.setMethodCallHandler(this);
        EventChannel eventChannel = new EventChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "flutter_aliplayer_media_loader_event");
        this.mEventChannel = eventChannel;
        eventChannel.setStreamHandler(this);
        mediaLoader.setOnLoadStatusListener(new MediaLoader.OnLoadStatusListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliMediaLoader.1
            @Override // com.aliyun.loader.MediaLoader.OnLoadStatusListener
            public void onCanceled(String str) {
                HashMap map = new HashMap();
                map.put("method", "onCanceled");
                map.put("url", str);
                FlutterAliMediaLoader.this.mEventSink.success(map);
            }

            @Override // com.aliyun.loader.MediaLoader.OnLoadStatusListener
            public void onCompleted(String str) {
                HashMap map = new HashMap();
                map.put("method", "onCompleted");
                map.put("url", str);
                FlutterAliMediaLoader.this.mEventSink.success(map);
            }

            @Override // com.aliyun.loader.MediaLoader.OnLoadStatusListener
            public void onError(String str, int i2, String str2) {
                HashMap map = new HashMap();
                map.put("method", "onError");
                map.put("url", str);
                map.put("code", String.valueOf(i2));
                map.put("msg", str2);
                FlutterAliMediaLoader.this.mEventSink.success(map);
            }

            @Override // com.aliyun.loader.MediaLoader.OnLoadStatusListener
            public void onErrorV2(String str, ErrorInfo errorInfo) {
                HashMap map = new HashMap();
                map.put("method", "onErrorV2");
                map.put("url", str);
                map.put("code", errorInfo.getCode().name());
                map.put("msg", errorInfo.getMsg());
                FlutterAliMediaLoader.this.mEventSink.success(map);
            }
        });
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onCancel(Object obj) {
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onListen(Object obj, EventChannel.EventSink eventSink) {
        this.mEventSink = eventSink;
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) throws NumberFormatException {
        String str = methodCall.method;
        str.hashCode();
        switch (str) {
            case "cancel":
                this.mMediaLoader.cancel((String) methodCall.arguments());
                break;
            case "resume":
                this.mMediaLoader.resume((String) methodCall.arguments());
                break;
            case "load":
                Map map = (Map) methodCall.arguments();
                if (map != null) {
                    String str2 = (String) map.get("url");
                    String str3 = (String) map.get("duration");
                    if (!map.containsKey("bandWidth")) {
                        this.mMediaLoader.load(str2, Long.parseLong(str3));
                        break;
                    } else {
                        this.mMediaLoader.load(str2, Long.parseLong(str3), Integer.parseInt((String) map.get("bandWidth")));
                        break;
                    }
                }
                break;
            case "pause":
                this.mMediaLoader.pause((String) methodCall.arguments());
                break;
        }
    }
}
