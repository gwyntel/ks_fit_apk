package io.flutter.embedding.engine.systemchannels;

import android.annotation.TargetApi;
import android.window.BackEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import io.flutter.Log;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMethodCodec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class BackGestureChannel {
    private static final String TAG = "BackGestureChannel";

    @NonNull
    public final MethodChannel channel;
    private final MethodChannel.MethodCallHandler defaultHandler;

    public BackGestureChannel(@NonNull DartExecutor dartExecutor) {
        MethodChannel.MethodCallHandler methodCallHandler = new MethodChannel.MethodCallHandler() { // from class: io.flutter.embedding.engine.systemchannels.BackGestureChannel.1
            @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
            public void onMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
                result.success(null);
            }
        };
        this.defaultHandler = methodCallHandler;
        MethodChannel methodChannel = new MethodChannel(dartExecutor, "flutter/backgesture", StandardMethodCodec.INSTANCE);
        this.channel = methodChannel;
        methodChannel.setMethodCallHandler(methodCallHandler);
    }

    @RequiresApi(34)
    @TargetApi(34)
    private Map<String, Object> backEventToJsonMap(@NonNull BackEvent backEvent) {
        HashMap map = new HashMap(3);
        float touchX = backEvent.getTouchX();
        float touchY = backEvent.getTouchY();
        map.put("touchOffset", (Float.isNaN(touchX) || Float.isNaN(touchY)) ? null : Arrays.asList(Float.valueOf(touchX), Float.valueOf(touchY)));
        map.put("progress", Float.valueOf(backEvent.getProgress()));
        map.put("swipeEdge", Integer.valueOf(backEvent.getSwipeEdge()));
        return map;
    }

    @RequiresApi(34)
    @TargetApi(34)
    public void cancelBackGesture() {
        Log.v(TAG, "Sending message to cancel back gesture");
        this.channel.invokeMethod("cancelBackGesture", null);
    }

    @RequiresApi(34)
    @TargetApi(34)
    public void commitBackGesture() {
        Log.v(TAG, "Sending message to commit back gesture");
        this.channel.invokeMethod("commitBackGesture", null);
    }

    public void setMethodCallHandler(@Nullable MethodChannel.MethodCallHandler methodCallHandler) {
        this.channel.setMethodCallHandler(methodCallHandler);
    }

    @RequiresApi(34)
    @TargetApi(34)
    public void startBackGesture(@NonNull BackEvent backEvent) {
        Log.v(TAG, "Sending message to start back gesture");
        this.channel.invokeMethod("startBackGesture", backEventToJsonMap(backEvent));
    }

    @RequiresApi(34)
    @TargetApi(34)
    public void updateBackGestureProgress(@NonNull BackEvent backEvent) {
        Log.v(TAG, "Sending message to update back gesture progress");
        this.channel.invokeMethod("updateBackGestureProgress", backEventToJsonMap(backEvent));
    }
}
