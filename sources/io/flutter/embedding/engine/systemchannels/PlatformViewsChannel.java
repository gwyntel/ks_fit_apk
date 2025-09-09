package io.flutter.embedding.engine.systemchannels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import io.flutter.Log;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.embedding.engine.systemchannels.PlatformViewsChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMethodCodec;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class PlatformViewsChannel {
    private static final String TAG = "PlatformViewsChannel";
    private final MethodChannel channel;
    private PlatformViewsHandler handler;
    private final MethodChannel.MethodCallHandler parsingHandler;

    /* renamed from: io.flutter.embedding.engine.systemchannels.PlatformViewsChannel$1, reason: invalid class name */
    class AnonymousClass1 implements MethodChannel.MethodCallHandler {
        AnonymousClass1() {
        }

        private void clearFocus(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
            try {
                PlatformViewsChannel.this.handler.clearFocus(((Integer) methodCall.arguments()).intValue());
                result.success(null);
            } catch (IllegalStateException e2) {
                result.error("error", PlatformViewsChannel.detailedExceptionString(e2), null);
            }
        }

        private void create(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
            Map map = (Map) methodCall.arguments();
            boolean z2 = false;
            boolean z3 = map.containsKey("hybrid") && ((Boolean) map.get("hybrid")).booleanValue();
            ByteBuffer byteBufferWrap = map.containsKey("params") ? ByteBuffer.wrap((byte[]) map.get("params")) : null;
            try {
                if (z3) {
                    PlatformViewsChannel.this.handler.createForPlatformViewLayer(new PlatformViewCreationRequest(((Integer) map.get("id")).intValue(), (String) map.get("viewType"), 0.0d, 0.0d, 0.0d, 0.0d, ((Integer) map.get(HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION)).intValue(), PlatformViewCreationRequest.RequestedDisplayMode.HYBRID_ONLY, byteBufferWrap));
                    result.success(null);
                    return;
                }
                if (map.containsKey("hybridFallback") && ((Boolean) map.get("hybridFallback")).booleanValue()) {
                    z2 = true;
                }
                long jCreateForTextureLayer = PlatformViewsChannel.this.handler.createForTextureLayer(new PlatformViewCreationRequest(((Integer) map.get("id")).intValue(), (String) map.get("viewType"), map.containsKey(ViewHierarchyConstants.DIMENSION_TOP_KEY) ? ((Double) map.get(ViewHierarchyConstants.DIMENSION_TOP_KEY)).doubleValue() : 0.0d, map.containsKey("left") ? ((Double) map.get("left")).doubleValue() : 0.0d, ((Double) map.get(ViewHierarchyConstants.DIMENSION_WIDTH_KEY)).doubleValue(), ((Double) map.get(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY)).doubleValue(), ((Integer) map.get(HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION)).intValue(), z2 ? PlatformViewCreationRequest.RequestedDisplayMode.TEXTURE_WITH_HYBRID_FALLBACK : PlatformViewCreationRequest.RequestedDisplayMode.TEXTURE_WITH_VIRTUAL_FALLBACK, byteBufferWrap));
                if (jCreateForTextureLayer != -2) {
                    result.success(Long.valueOf(jCreateForTextureLayer));
                } else {
                    if (!z2) {
                        throw new AssertionError("Platform view attempted to fall back to hybrid mode when not requested.");
                    }
                    result.success(null);
                }
            } catch (IllegalStateException e2) {
                result.error("error", PlatformViewsChannel.detailedExceptionString(e2), null);
            }
        }

        private void dispose(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
            try {
                PlatformViewsChannel.this.handler.dispose(((Integer) ((Map) methodCall.arguments()).get("id")).intValue());
                result.success(null);
            } catch (IllegalStateException e2) {
                result.error("error", PlatformViewsChannel.detailedExceptionString(e2), null);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$resize$0(MethodChannel.Result result, PlatformViewBufferSize platformViewBufferSize) {
            if (platformViewBufferSize == null) {
                result.error("error", "Failed to resize the platform view", null);
                return;
            }
            HashMap map = new HashMap();
            map.put(ViewHierarchyConstants.DIMENSION_WIDTH_KEY, Double.valueOf(platformViewBufferSize.width));
            map.put(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY, Double.valueOf(platformViewBufferSize.height));
            result.success(map);
        }

        private void offset(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
            Map map = (Map) methodCall.arguments();
            try {
                PlatformViewsChannel.this.handler.offset(((Integer) map.get("id")).intValue(), ((Double) map.get(ViewHierarchyConstants.DIMENSION_TOP_KEY)).doubleValue(), ((Double) map.get("left")).doubleValue());
                result.success(null);
            } catch (IllegalStateException e2) {
                result.error("error", PlatformViewsChannel.detailedExceptionString(e2), null);
            }
        }

        private void resize(@NonNull MethodCall methodCall, @NonNull final MethodChannel.Result result) {
            Map map = (Map) methodCall.arguments();
            try {
                PlatformViewsChannel.this.handler.resize(new PlatformViewResizeRequest(((Integer) map.get("id")).intValue(), ((Double) map.get(ViewHierarchyConstants.DIMENSION_WIDTH_KEY)).doubleValue(), ((Double) map.get(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY)).doubleValue()), new PlatformViewBufferResized() { // from class: io.flutter.embedding.engine.systemchannels.g
                    @Override // io.flutter.embedding.engine.systemchannels.PlatformViewsChannel.PlatformViewBufferResized
                    public final void run(PlatformViewsChannel.PlatformViewBufferSize platformViewBufferSize) {
                        PlatformViewsChannel.AnonymousClass1.lambda$resize$0(result, platformViewBufferSize);
                    }
                });
            } catch (IllegalStateException e2) {
                result.error("error", PlatformViewsChannel.detailedExceptionString(e2), null);
            }
        }

        private void setDirection(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
            Map map = (Map) methodCall.arguments();
            try {
                PlatformViewsChannel.this.handler.setDirection(((Integer) map.get("id")).intValue(), ((Integer) map.get(HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION)).intValue());
                result.success(null);
            } catch (IllegalStateException e2) {
                result.error("error", PlatformViewsChannel.detailedExceptionString(e2), null);
            }
        }

        private void synchronizeToNativeViewHierarchy(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
            try {
                PlatformViewsChannel.this.handler.synchronizeToNativeViewHierarchy(((Boolean) methodCall.arguments()).booleanValue());
                result.success(null);
            } catch (IllegalStateException e2) {
                result.error("error", PlatformViewsChannel.detailedExceptionString(e2), null);
            }
        }

        private void touch(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
            MethodChannel.Result result2;
            List list = (List) methodCall.arguments();
            try {
                PlatformViewsChannel.this.handler.onTouch(new PlatformViewTouch(((Integer) list.get(0)).intValue(), (Number) list.get(1), (Number) list.get(2), ((Integer) list.get(3)).intValue(), ((Integer) list.get(4)).intValue(), list.get(5), list.get(6), ((Integer) list.get(7)).intValue(), ((Integer) list.get(8)).intValue(), (float) ((Double) list.get(9)).doubleValue(), (float) ((Double) list.get(10)).doubleValue(), ((Integer) list.get(11)).intValue(), ((Integer) list.get(12)).intValue(), ((Integer) list.get(13)).intValue(), ((Integer) list.get(14)).intValue(), ((Number) list.get(15)).longValue()));
                result2 = result;
                try {
                    result2.success(null);
                } catch (IllegalStateException e2) {
                    e = e2;
                    result2.error("error", PlatformViewsChannel.detailedExceptionString(e), null);
                }
            } catch (IllegalStateException e3) {
                e = e3;
                result2 = result;
            }
        }

        @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
        public void onMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
            if (PlatformViewsChannel.this.handler == null) {
            }
            Log.v(PlatformViewsChannel.TAG, "Received '" + methodCall.method + "' message.");
            String str = methodCall.method;
            str.hashCode();
            switch (str) {
                case "create":
                    create(methodCall, result);
                    break;
                case "offset":
                    offset(methodCall, result);
                    break;
                case "resize":
                    resize(methodCall, result);
                    break;
                case "clearFocus":
                    clearFocus(methodCall, result);
                    break;
                case "synchronizeToNativeViewHierarchy":
                    synchronizeToNativeViewHierarchy(methodCall, result);
                    break;
                case "touch":
                    touch(methodCall, result);
                    break;
                case "setDirection":
                    setDirection(methodCall, result);
                    break;
                case "dispose":
                    dispose(methodCall, result);
                    break;
                default:
                    result.notImplemented();
                    break;
            }
        }
    }

    public interface PlatformViewBufferResized {
        void run(@Nullable PlatformViewBufferSize platformViewBufferSize);
    }

    public static class PlatformViewBufferSize {
        public final int height;
        public final int width;

        public PlatformViewBufferSize(int i2, int i3) {
            this.width = i2;
            this.height = i3;
        }
    }

    public static class PlatformViewCreationRequest {
        public final int direction;
        public final RequestedDisplayMode displayMode;
        public final double logicalHeight;
        public final double logicalLeft;
        public final double logicalTop;
        public final double logicalWidth;

        @Nullable
        public final ByteBuffer params;
        public final int viewId;

        @NonNull
        public final String viewType;

        public enum RequestedDisplayMode {
            TEXTURE_WITH_VIRTUAL_FALLBACK,
            TEXTURE_WITH_HYBRID_FALLBACK,
            HYBRID_ONLY
        }

        public PlatformViewCreationRequest(int i2, @NonNull String str, double d2, double d3, double d4, double d5, int i3, @Nullable ByteBuffer byteBuffer) {
            this(i2, str, d2, d3, d4, d5, i3, RequestedDisplayMode.TEXTURE_WITH_VIRTUAL_FALLBACK, byteBuffer);
        }

        public PlatformViewCreationRequest(int i2, @NonNull String str, double d2, double d3, double d4, double d5, int i3, RequestedDisplayMode requestedDisplayMode, @Nullable ByteBuffer byteBuffer) {
            this.viewId = i2;
            this.viewType = str;
            this.logicalTop = d2;
            this.logicalLeft = d3;
            this.logicalWidth = d4;
            this.logicalHeight = d5;
            this.direction = i3;
            this.displayMode = requestedDisplayMode;
            this.params = byteBuffer;
        }
    }

    public static class PlatformViewResizeRequest {
        public final double newLogicalHeight;
        public final double newLogicalWidth;
        public final int viewId;

        public PlatformViewResizeRequest(int i2, double d2, double d3) {
            this.viewId = i2;
            this.newLogicalWidth = d2;
            this.newLogicalHeight = d3;
        }
    }

    public static class PlatformViewTouch {
        public final int action;
        public final int buttonState;
        public final int deviceId;

        @NonNull
        public final Number downTime;
        public final int edgeFlags;

        @NonNull
        public final Number eventTime;
        public final int flags;
        public final int metaState;
        public final long motionEventId;
        public final int pointerCount;

        @NonNull
        public final Object rawPointerCoords;

        @NonNull
        public final Object rawPointerPropertiesList;
        public final int source;
        public final int viewId;
        public final float xPrecision;
        public final float yPrecision;

        public PlatformViewTouch(int i2, @NonNull Number number, @NonNull Number number2, int i3, int i4, @NonNull Object obj, @NonNull Object obj2, int i5, int i6, float f2, float f3, int i7, int i8, int i9, int i10, long j2) {
            this.viewId = i2;
            this.downTime = number;
            this.eventTime = number2;
            this.action = i3;
            this.pointerCount = i4;
            this.rawPointerPropertiesList = obj;
            this.rawPointerCoords = obj2;
            this.metaState = i5;
            this.buttonState = i6;
            this.xPrecision = f2;
            this.yPrecision = f3;
            this.deviceId = i7;
            this.edgeFlags = i8;
            this.source = i9;
            this.flags = i10;
            this.motionEventId = j2;
        }
    }

    public interface PlatformViewsHandler {
        public static final long NON_TEXTURE_FALLBACK = -2;

        void clearFocus(int i2);

        void createForPlatformViewLayer(@NonNull PlatformViewCreationRequest platformViewCreationRequest);

        long createForTextureLayer(@NonNull PlatformViewCreationRequest platformViewCreationRequest);

        void dispose(int i2);

        void offset(int i2, double d2, double d3);

        void onTouch(@NonNull PlatformViewTouch platformViewTouch);

        void resize(@NonNull PlatformViewResizeRequest platformViewResizeRequest, @NonNull PlatformViewBufferResized platformViewBufferResized);

        void setDirection(int i2, int i3);

        void synchronizeToNativeViewHierarchy(boolean z2);
    }

    public PlatformViewsChannel(@NonNull DartExecutor dartExecutor) {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.parsingHandler = anonymousClass1;
        MethodChannel methodChannel = new MethodChannel(dartExecutor, "flutter/platform_views", StandardMethodCodec.INSTANCE);
        this.channel = methodChannel;
        methodChannel.setMethodCallHandler(anonymousClass1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String detailedExceptionString(Exception exc) {
        return Log.getStackTraceString(exc);
    }

    public void invokeViewFocused(int i2) {
        MethodChannel methodChannel = this.channel;
        if (methodChannel == null) {
            return;
        }
        methodChannel.invokeMethod("viewFocused", Integer.valueOf(i2));
    }

    public void setPlatformViewsHandler(@Nullable PlatformViewsHandler platformViewsHandler) {
        this.handler = platformViewsHandler;
    }
}
