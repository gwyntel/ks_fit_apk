package io.flutter.plugins.videoplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.app.NotificationCompat;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import io.flutter.plugin.common.EventChannel;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/* loaded from: classes4.dex */
final class VideoPlayerEventCallbacks implements VideoPlayerCallbacks {
    private final EventChannel.EventSink eventSink;

    private VideoPlayerEventCallbacks(EventChannel.EventSink eventSink) {
        this.eventSink = eventSink;
    }

    static VideoPlayerEventCallbacks bindTo(EventChannel eventChannel) {
        final QueuingEventSink queuingEventSink = new QueuingEventSink();
        eventChannel.setStreamHandler(new EventChannel.StreamHandler() { // from class: io.flutter.plugins.videoplayer.VideoPlayerEventCallbacks.1
            @Override // io.flutter.plugin.common.EventChannel.StreamHandler
            public void onCancel(Object obj) {
                queuingEventSink.setDelegate(null);
            }

            @Override // io.flutter.plugin.common.EventChannel.StreamHandler
            public void onListen(Object obj, EventChannel.EventSink eventSink) {
                queuingEventSink.setDelegate(eventSink);
            }
        });
        return withSink(queuingEventSink);
    }

    @VisibleForTesting
    static VideoPlayerEventCallbacks withSink(EventChannel.EventSink eventSink) {
        return new VideoPlayerEventCallbacks(eventSink);
    }

    @Override // io.flutter.plugins.videoplayer.VideoPlayerCallbacks
    public void onBufferingEnd() {
        HashMap map = new HashMap();
        map.put(NotificationCompat.CATEGORY_EVENT, "bufferingEnd");
        this.eventSink.success(map);
    }

    @Override // io.flutter.plugins.videoplayer.VideoPlayerCallbacks
    public void onBufferingStart() {
        HashMap map = new HashMap();
        map.put(NotificationCompat.CATEGORY_EVENT, "bufferingStart");
        this.eventSink.success(map);
    }

    @Override // io.flutter.plugins.videoplayer.VideoPlayerCallbacks
    public void onBufferingUpdate(long j2) {
        HashMap map = new HashMap();
        map.put(NotificationCompat.CATEGORY_EVENT, "bufferingUpdate");
        map.put("values", Collections.singletonList(Arrays.asList(0, Long.valueOf(j2))));
        this.eventSink.success(map);
    }

    @Override // io.flutter.plugins.videoplayer.VideoPlayerCallbacks
    public void onCompleted() {
        HashMap map = new HashMap();
        map.put(NotificationCompat.CATEGORY_EVENT, "completed");
        this.eventSink.success(map);
    }

    @Override // io.flutter.plugins.videoplayer.VideoPlayerCallbacks
    public void onError(@NonNull String str, @Nullable String str2, @Nullable Object obj) {
        this.eventSink.error(str, str2, obj);
    }

    @Override // io.flutter.plugins.videoplayer.VideoPlayerCallbacks
    public void onInitialized(int i2, int i3, long j2, int i4) {
        HashMap map = new HashMap();
        map.put(NotificationCompat.CATEGORY_EVENT, "initialized");
        map.put(ViewHierarchyConstants.DIMENSION_WIDTH_KEY, Integer.valueOf(i2));
        map.put(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY, Integer.valueOf(i3));
        map.put("duration", Long.valueOf(j2));
        if (i4 != 0) {
            map.put("rotationCorrection", Integer.valueOf(i4));
        }
        this.eventSink.success(map);
    }

    @Override // io.flutter.plugins.videoplayer.VideoPlayerCallbacks
    public void onIsPlayingStateUpdate(boolean z2) {
        HashMap map = new HashMap();
        map.put(NotificationCompat.CATEGORY_EVENT, "isPlayingStateUpdate");
        map.put("isPlaying", Boolean.valueOf(z2));
        this.eventSink.success(map);
    }
}
