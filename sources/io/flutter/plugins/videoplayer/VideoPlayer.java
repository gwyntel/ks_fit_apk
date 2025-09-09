package io.flutter.plugins.videoplayer;

import android.content.Context;
import android.view.Surface;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.media3.common.AudioAttributes;
import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackParameters;
import androidx.media3.exoplayer.ExoPlayer;
import io.flutter.view.TextureRegistry;

/* loaded from: classes4.dex */
final class VideoPlayer {
    private ExoPlayer exoPlayer;
    private final VideoPlayerOptions options;
    private Surface surface;
    private final TextureRegistry.SurfaceTextureEntry textureEntry;
    private final VideoPlayerCallbacks videoPlayerEvents;

    @VisibleForTesting
    VideoPlayer(ExoPlayer.Builder builder, VideoPlayerCallbacks videoPlayerCallbacks, TextureRegistry.SurfaceTextureEntry surfaceTextureEntry, MediaItem mediaItem, VideoPlayerOptions videoPlayerOptions) {
        this.videoPlayerEvents = videoPlayerCallbacks;
        this.textureEntry = surfaceTextureEntry;
        this.options = videoPlayerOptions;
        ExoPlayer exoPlayerBuild = builder.build();
        exoPlayerBuild.setMediaItem(mediaItem);
        exoPlayerBuild.prepare();
        setUpVideoPlayer(exoPlayerBuild);
    }

    @NonNull
    static VideoPlayer create(Context context, VideoPlayerCallbacks videoPlayerCallbacks, TextureRegistry.SurfaceTextureEntry surfaceTextureEntry, VideoAsset videoAsset, VideoPlayerOptions videoPlayerOptions) {
        return new VideoPlayer(new ExoPlayer.Builder(context).setMediaSourceFactory(videoAsset.getMediaSourceFactory(context)), videoPlayerCallbacks, surfaceTextureEntry, videoAsset.getMediaItem(), videoPlayerOptions);
    }

    private static void setAudioAttributes(ExoPlayer exoPlayer, boolean z2) {
        exoPlayer.setAudioAttributes(new AudioAttributes.Builder().setContentType(3).build(), !z2);
    }

    private void setUpVideoPlayer(ExoPlayer exoPlayer) {
        this.exoPlayer = exoPlayer;
        Surface surface = new Surface(this.textureEntry.surfaceTexture());
        this.surface = surface;
        exoPlayer.setVideoSurface(surface);
        setAudioAttributes(exoPlayer, this.options.mixWithOthers);
        exoPlayer.addListener(new ExoPlayerEventListener(exoPlayer, this.videoPlayerEvents));
    }

    void dispose() {
        this.textureEntry.release();
        Surface surface = this.surface;
        if (surface != null) {
            surface.release();
        }
        ExoPlayer exoPlayer = this.exoPlayer;
        if (exoPlayer != null) {
            exoPlayer.release();
        }
    }

    long getPosition() {
        return this.exoPlayer.getCurrentPosition();
    }

    void pause() {
        this.exoPlayer.setPlayWhenReady(false);
    }

    void play() {
        this.exoPlayer.setPlayWhenReady(true);
    }

    void seekTo(int i2) {
        this.exoPlayer.seekTo(i2);
    }

    void sendBufferingUpdate() {
        this.videoPlayerEvents.onBufferingUpdate(this.exoPlayer.getBufferedPosition());
    }

    void setLooping(boolean z2) {
        this.exoPlayer.setRepeatMode(z2 ? 2 : 0);
    }

    void setPlaybackSpeed(double d2) {
        this.exoPlayer.setPlaybackParameters(new PlaybackParameters((float) d2));
    }

    void setVolume(double d2) {
        this.exoPlayer.setVolume((float) Math.max(0.0d, Math.min(1.0d, d2)));
    }
}
