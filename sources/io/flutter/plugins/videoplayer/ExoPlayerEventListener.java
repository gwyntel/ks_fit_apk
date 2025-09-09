package io.flutter.plugins.videoplayer;

import androidx.annotation.NonNull;
import androidx.media3.common.AudioAttributes;
import androidx.media3.common.DeviceInfo;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;
import androidx.media3.common.Metadata;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.PlaybackParameters;
import androidx.media3.common.Player;
import androidx.media3.common.Timeline;
import androidx.media3.common.TrackSelectionParameters;
import androidx.media3.common.Tracks;
import androidx.media3.common.VideoSize;
import androidx.media3.common.p;
import androidx.media3.common.text.CueGroup;
import androidx.media3.exoplayer.ExoPlayer;
import java.util.List;

/* loaded from: classes4.dex */
final class ExoPlayerEventListener implements Player.Listener {
    private final VideoPlayerCallbacks events;
    private final ExoPlayer exoPlayer;
    private boolean isBuffering = false;
    private boolean isInitialized = false;

    ExoPlayerEventListener(ExoPlayer exoPlayer, VideoPlayerCallbacks videoPlayerCallbacks) {
        this.exoPlayer = exoPlayer;
        this.events = videoPlayerCallbacks;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x002c A[PHI: r1 r2
      0x002c: PHI (r1v1 int) = (r1v0 int), (r1v0 int), (r1v3 int) binds: [B:6:0x0013, B:7:0x0015, B:14:0x0026] A[DONT_GENERATE, DONT_INLINE]
      0x002c: PHI (r2v1 int) = (r2v0 int), (r2v0 int), (r2v3 int) binds: [B:6:0x0013, B:7:0x0015, B:14:0x0026] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void sendInitialized() {
        /*
            r12 = this;
            boolean r0 = r12.isInitialized
            if (r0 == 0) goto L5
            return
        L5:
            r0 = 1
            r12.isInitialized = r0
            androidx.media3.exoplayer.ExoPlayer r0 = r12.exoPlayer
            androidx.media3.common.VideoSize r0 = r0.getVideoSize()
            int r1 = r0.width
            int r2 = r0.height
            r3 = 0
            if (r1 == 0) goto L2c
            if (r2 == 0) goto L2c
            int r0 = r0.unappliedRotationDegrees
            r4 = 90
            if (r0 == r4) goto L21
            r4 = 270(0x10e, float:3.78E-43)
            if (r0 != r4) goto L24
        L21:
            r11 = r2
            r2 = r1
            r1 = r11
        L24:
            r4 = 180(0xb4, float:2.52E-43)
            if (r0 != r4) goto L2c
            r10 = r0
            r6 = r1
            r7 = r2
            goto L2f
        L2c:
            r6 = r1
            r7 = r2
            r10 = r3
        L2f:
            io.flutter.plugins.videoplayer.VideoPlayerCallbacks r5 = r12.events
            androidx.media3.exoplayer.ExoPlayer r0 = r12.exoPlayer
            long r8 = r0.getDuration()
            r5.onInitialized(r6, r7, r8, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.flutter.plugins.videoplayer.ExoPlayerEventListener.sendInitialized():void");
    }

    private void setBuffering(boolean z2) {
        if (this.isBuffering == z2) {
            return;
        }
        this.isBuffering = z2;
        if (z2) {
            this.events.onBufferingStart();
        } else {
            this.events.onBufferingEnd();
        }
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onAudioAttributesChanged(AudioAttributes audioAttributes) {
        p.a(this, audioAttributes);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onAudioSessionIdChanged(int i2) {
        p.b(this, i2);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onAvailableCommandsChanged(Player.Commands commands) {
        p.c(this, commands);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onCues(CueGroup cueGroup) {
        p.d(this, cueGroup);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onDeviceInfoChanged(DeviceInfo deviceInfo) {
        p.f(this, deviceInfo);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onDeviceVolumeChanged(int i2, boolean z2) {
        p.g(this, i2, z2);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onEvents(Player player, Player.Events events) {
        p.h(this, player, events);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onIsLoadingChanged(boolean z2) {
        p.i(this, z2);
    }

    @Override // androidx.media3.common.Player.Listener
    public void onIsPlayingChanged(boolean z2) {
        this.events.onIsPlayingStateUpdate(z2);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onLoadingChanged(boolean z2) {
        p.k(this, z2);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onMaxSeekToPreviousPositionChanged(long j2) {
        p.l(this, j2);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onMediaItemTransition(MediaItem mediaItem, int i2) {
        p.m(this, mediaItem, i2);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
        p.n(this, mediaMetadata);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onMetadata(Metadata metadata) {
        p.o(this, metadata);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onPlayWhenReadyChanged(boolean z2, int i2) {
        p.p(this, z2, i2);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        p.q(this, playbackParameters);
    }

    @Override // androidx.media3.common.Player.Listener
    public void onPlaybackStateChanged(int i2) {
        if (i2 == 2) {
            setBuffering(true);
            this.events.onBufferingUpdate(this.exoPlayer.getBufferedPosition());
        } else if (i2 == 3) {
            sendInitialized();
        } else if (i2 == 4) {
            this.events.onCompleted();
        }
        if (i2 != 2) {
            setBuffering(false);
        }
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onPlaybackSuppressionReasonChanged(int i2) {
        p.s(this, i2);
    }

    @Override // androidx.media3.common.Player.Listener
    public void onPlayerError(@NonNull PlaybackException playbackException) {
        setBuffering(false);
        if (playbackException.errorCode == 1002) {
            this.exoPlayer.seekToDefaultPosition();
            this.exoPlayer.prepare();
            return;
        }
        this.events.onError("VideoError", "Video player had error " + playbackException, null);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onPlayerErrorChanged(PlaybackException playbackException) {
        p.u(this, playbackException);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onPlayerStateChanged(boolean z2, int i2) {
        p.v(this, z2, i2);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onPlaylistMetadataChanged(MediaMetadata mediaMetadata) {
        p.w(this, mediaMetadata);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onPositionDiscontinuity(int i2) {
        p.x(this, i2);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onRenderedFirstFrame() {
        p.z(this);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onRepeatModeChanged(int i2) {
        p.A(this, i2);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onSeekBackIncrementChanged(long j2) {
        p.B(this, j2);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onSeekForwardIncrementChanged(long j2) {
        p.C(this, j2);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onShuffleModeEnabledChanged(boolean z2) {
        p.D(this, z2);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onSkipSilenceEnabledChanged(boolean z2) {
        p.E(this, z2);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onSurfaceSizeChanged(int i2, int i3) {
        p.F(this, i2, i3);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onTimelineChanged(Timeline timeline, int i2) {
        p.G(this, timeline, i2);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onTrackSelectionParametersChanged(TrackSelectionParameters trackSelectionParameters) {
        p.H(this, trackSelectionParameters);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onTracksChanged(Tracks tracks) {
        p.I(this, tracks);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onVideoSizeChanged(VideoSize videoSize) {
        p.J(this, videoSize);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onVolumeChanged(float f2) {
        p.K(this, f2);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onCues(List list) {
        p.e(this, list);
    }

    @Override // androidx.media3.common.Player.Listener
    public /* synthetic */ void onPositionDiscontinuity(Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i2) {
        p.y(this, positionInfo, positionInfo2, i2);
    }
}
