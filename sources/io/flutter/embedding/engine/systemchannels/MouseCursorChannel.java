package io.flutter.embedding.engine.systemchannels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import io.flutter.Log;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMethodCodec;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class MouseCursorChannel {
    private static final String TAG = "MouseCursorChannel";

    @NonNull
    public final MethodChannel channel;

    @Nullable
    private MouseCursorMethodHandler mouseCursorMethodHandler;

    @NonNull
    private final MethodChannel.MethodCallHandler parsingMethodCallHandler;

    public interface MouseCursorMethodHandler {
        void activateSystemCursor(@NonNull String str);
    }

    public MouseCursorChannel(@NonNull DartExecutor dartExecutor) {
        MethodChannel.MethodCallHandler methodCallHandler = new MethodChannel.MethodCallHandler() { // from class: io.flutter.embedding.engine.systemchannels.MouseCursorChannel.1
            @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
            public void onMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
                if (MouseCursorChannel.this.mouseCursorMethodHandler == null) {
                    return;
                }
                String str = methodCall.method;
                Log.v(MouseCursorChannel.TAG, "Received '" + str + "' message.");
                try {
                    if (str.hashCode() == -1307105544 && str.equals("activateSystemCursor")) {
                        try {
                            MouseCursorChannel.this.mouseCursorMethodHandler.activateSystemCursor((String) ((HashMap) methodCall.arguments).get("kind"));
                            result.success(Boolean.TRUE);
                        } catch (Exception e2) {
                            result.error("error", "Error when setting cursors: " + e2.getMessage(), null);
                        }
                    }
                } catch (Exception e3) {
                    result.error("error", "Unhandled error: " + e3.getMessage(), null);
                }
            }
        };
        this.parsingMethodCallHandler = methodCallHandler;
        MethodChannel methodChannel = new MethodChannel(dartExecutor, "flutter/mousecursor", StandardMethodCodec.INSTANCE);
        this.channel = methodChannel;
        methodChannel.setMethodCallHandler(methodCallHandler);
    }

    public void setMethodHandler(@Nullable MouseCursorMethodHandler mouseCursorMethodHandler) {
        this.mouseCursorMethodHandler = mouseCursorMethodHandler;
    }

    @VisibleForTesting
    public void synthesizeMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
        this.parsingMethodCallHandler.onMethodCall(methodCall, result);
    }
}
