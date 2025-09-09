package xyz.luan.audioplayers;

import androidx.core.app.NotificationCompat;
import com.tekartik.sqflite.Constant;
import io.flutter.plugin.common.EventChannel;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010$\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ$\u0010\t\u001a\u00020\b2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000eJ\u0012\u0010\u000f\u001a\u00020\b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000eH\u0016J\u001c\u0010\u0011\u001a\u00020\b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000e2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0006H\u0016J$\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u000b2\u0014\b\u0002\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000e0\u0015R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lxyz/luan/audioplayers/EventHandler;", "Lio/flutter/plugin/common/EventChannel$StreamHandler;", "eventChannel", "Lio/flutter/plugin/common/EventChannel;", "(Lio/flutter/plugin/common/EventChannel;)V", "eventSink", "Lio/flutter/plugin/common/EventChannel$EventSink;", "dispose", "", "error", "errorCode", "", "errorMessage", "errorDetails", "", "onCancel", Constant.PARAM_SQL_ARGUMENTS, "onListen", "events", "success", "method", "", "audioplayers_android_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class EventHandler implements EventChannel.StreamHandler {

    @NotNull
    private final EventChannel eventChannel;

    @Nullable
    private EventChannel.EventSink eventSink;

    public EventHandler(@NotNull EventChannel eventChannel) {
        Intrinsics.checkNotNullParameter(eventChannel, "eventChannel");
        this.eventChannel = eventChannel;
        eventChannel.setStreamHandler(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void success$default(EventHandler eventHandler, String str, Map map, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            map = new HashMap();
        }
        eventHandler.success(str, map);
    }

    public final void dispose() {
        EventChannel.EventSink eventSink = this.eventSink;
        if (eventSink != null) {
            eventSink.endOfStream();
            onCancel(null);
        }
        this.eventChannel.setStreamHandler(null);
    }

    public final void error(@Nullable String errorCode, @Nullable String errorMessage, @Nullable Object errorDetails) {
        EventChannel.EventSink eventSink = this.eventSink;
        if (eventSink != null) {
            eventSink.error(errorCode, errorMessage, errorDetails);
        }
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onCancel(@Nullable Object arguments) {
        this.eventSink = null;
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onListen(@Nullable Object arguments, @Nullable EventChannel.EventSink events) {
        this.eventSink = events;
    }

    public final void success(@NotNull String method, @NotNull Map<String, ? extends Object> arguments) {
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        EventChannel.EventSink eventSink = this.eventSink;
        if (eventSink != null) {
            eventSink.success(MapsKt.plus(arguments, new Pair(NotificationCompat.CATEGORY_EVENT, method)));
        }
    }
}
