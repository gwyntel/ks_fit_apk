package io.flutter.plugins.videoplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes4.dex */
interface VideoPlayerCallbacks {
    void onBufferingEnd();

    void onBufferingStart();

    void onBufferingUpdate(long j2);

    void onCompleted();

    void onError(@NonNull String str, @Nullable String str2, @Nullable Object obj);

    void onInitialized(int i2, int i3, long j2, int i4);

    void onIsPlayingStateUpdate(boolean z2);
}
