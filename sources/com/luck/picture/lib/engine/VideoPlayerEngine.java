package com.luck.picture.lib.engine;

import android.content.Context;
import android.view.View;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnPlayerListener;

/* loaded from: classes4.dex */
public interface VideoPlayerEngine<T> {
    void addPlayListener(OnPlayerListener onPlayerListener);

    void destroy(T t2);

    boolean isPlaying(T t2);

    View onCreateVideoPlayer(Context context);

    void onPause(T t2);

    void onPlayerAttachedToWindow(T t2);

    void onPlayerDetachedFromWindow(T t2);

    void onResume(T t2);

    void onStarPlayer(T t2, LocalMedia localMedia);

    void removePlayListener(OnPlayerListener onPlayerListener);
}
