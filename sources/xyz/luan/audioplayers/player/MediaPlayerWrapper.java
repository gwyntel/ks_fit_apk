package xyz.luan.audioplayers.player;

import android.media.MediaPlayer;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.luan.audioplayers.AudioContextAndroid;
import xyz.luan.audioplayers.source.Source;

@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\r\u0010\b\u001a\u00020\tH\u0016¢\u0006\u0002\u0010\nJ\u000f\u0010\u000b\u001a\u0004\u0018\u00010\tH\u0016¢\u0006\u0002\u0010\nJ\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u000fH\u0016J\b\u0010\u0011\u001a\u00020\u000fH\u0016J\b\u0010\u0012\u001a\u00020\u000fH\u0016J\u0010\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\tH\u0016J\u0010\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\rH\u0016J\u0010\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0018\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020\u0019H\u0016J\b\u0010 \u001a\u00020\u000fH\u0016J\b\u0010!\u001a\u00020\u000fH\u0016J\u0010\u0010\"\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020$H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lxyz/luan/audioplayers/player/MediaPlayerWrapper;", "Lxyz/luan/audioplayers/player/PlayerWrapper;", "wrappedPlayer", "Lxyz/luan/audioplayers/player/WrappedPlayer;", "(Lxyz/luan/audioplayers/player/WrappedPlayer;)V", "mediaPlayer", "Landroid/media/MediaPlayer;", "createMediaPlayer", "getCurrentPosition", "", "()Ljava/lang/Integer;", "getDuration", "isLiveStream", "", "pause", "", "prepare", "release", "reset", "seekTo", RequestParameters.POSITION, "setLooping", "looping", "setRate", "rate", "", "setSource", "source", "Lxyz/luan/audioplayers/source/Source;", "setVolume", "leftVolume", "rightVolume", "start", "stop", "updateContext", com.umeng.analytics.pro.f.X, "Lxyz/luan/audioplayers/AudioContextAndroid;", "audioplayers_android_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nMediaPlayerWrapper.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MediaPlayerWrapper.kt\nxyz/luan/audioplayers/player/MediaPlayerWrapper\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,100:1\n1#2:101\n*E\n"})
/* loaded from: classes5.dex */
public final class MediaPlayerWrapper implements PlayerWrapper {

    @NotNull
    private final MediaPlayer mediaPlayer;

    @NotNull
    private final WrappedPlayer wrappedPlayer;

    public MediaPlayerWrapper(@NotNull WrappedPlayer wrappedPlayer) {
        Intrinsics.checkNotNullParameter(wrappedPlayer, "wrappedPlayer");
        this.wrappedPlayer = wrappedPlayer;
        this.mediaPlayer = createMediaPlayer(wrappedPlayer);
    }

    private final MediaPlayer createMediaPlayer(final WrappedPlayer wrappedPlayer) throws IllegalArgumentException {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: xyz.luan.audioplayers.player.b
            @Override // android.media.MediaPlayer.OnPreparedListener
            public final void onPrepared(MediaPlayer mediaPlayer2) {
                MediaPlayerWrapper.createMediaPlayer$lambda$5$lambda$0(wrappedPlayer, mediaPlayer2);
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: xyz.luan.audioplayers.player.c
            @Override // android.media.MediaPlayer.OnCompletionListener
            public final void onCompletion(MediaPlayer mediaPlayer2) {
                MediaPlayerWrapper.createMediaPlayer$lambda$5$lambda$1(wrappedPlayer, mediaPlayer2);
            }
        });
        mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() { // from class: xyz.luan.audioplayers.player.d
            @Override // android.media.MediaPlayer.OnSeekCompleteListener
            public final void onSeekComplete(MediaPlayer mediaPlayer2) {
                MediaPlayerWrapper.createMediaPlayer$lambda$5$lambda$2(wrappedPlayer, mediaPlayer2);
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: xyz.luan.audioplayers.player.e
            @Override // android.media.MediaPlayer.OnErrorListener
            public final boolean onError(MediaPlayer mediaPlayer2, int i2, int i3) {
                return MediaPlayerWrapper.createMediaPlayer$lambda$5$lambda$3(wrappedPlayer, mediaPlayer2, i2, i3);
            }
        });
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() { // from class: xyz.luan.audioplayers.player.f
            @Override // android.media.MediaPlayer.OnBufferingUpdateListener
            public final void onBufferingUpdate(MediaPlayer mediaPlayer2, int i2) {
                MediaPlayerWrapper.createMediaPlayer$lambda$5$lambda$4(wrappedPlayer, mediaPlayer2, i2);
            }
        });
        wrappedPlayer.getContext().setAttributesOnPlayer(mediaPlayer);
        return mediaPlayer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createMediaPlayer$lambda$5$lambda$0(WrappedPlayer wrappedPlayer, MediaPlayer mediaPlayer) {
        Intrinsics.checkNotNullParameter(wrappedPlayer, "$wrappedPlayer");
        wrappedPlayer.onPrepared();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createMediaPlayer$lambda$5$lambda$1(WrappedPlayer wrappedPlayer, MediaPlayer mediaPlayer) {
        Intrinsics.checkNotNullParameter(wrappedPlayer, "$wrappedPlayer");
        wrappedPlayer.onCompletion();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createMediaPlayer$lambda$5$lambda$2(WrappedPlayer wrappedPlayer, MediaPlayer mediaPlayer) {
        Intrinsics.checkNotNullParameter(wrappedPlayer, "$wrappedPlayer");
        wrappedPlayer.onSeekComplete();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean createMediaPlayer$lambda$5$lambda$3(WrappedPlayer wrappedPlayer, MediaPlayer mediaPlayer, int i2, int i3) {
        Intrinsics.checkNotNullParameter(wrappedPlayer, "$wrappedPlayer");
        return wrappedPlayer.onError(i2, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createMediaPlayer$lambda$5$lambda$4(WrappedPlayer wrappedPlayer, MediaPlayer mediaPlayer, int i2) {
        Intrinsics.checkNotNullParameter(wrappedPlayer, "$wrappedPlayer");
        wrappedPlayer.onBuffering(i2);
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    @NotNull
    public Integer getCurrentPosition() {
        return Integer.valueOf(this.mediaPlayer.getCurrentPosition());
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    @Nullable
    public Integer getDuration() {
        Integer numValueOf = Integer.valueOf(this.mediaPlayer.getDuration());
        if (numValueOf.intValue() == -1) {
            return null;
        }
        return numValueOf;
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public boolean isLiveStream() {
        Integer duration = getDuration();
        return duration == null || duration.intValue() == 0;
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public void pause() throws IllegalStateException {
        this.mediaPlayer.pause();
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public void prepare() throws IllegalStateException {
        this.mediaPlayer.prepareAsync();
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public void release() {
        this.mediaPlayer.reset();
        this.mediaPlayer.release();
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public void reset() {
        this.mediaPlayer.reset();
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public void seekTo(int position) throws IllegalStateException {
        this.mediaPlayer.seekTo(position);
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public void setLooping(boolean looping) {
        this.mediaPlayer.setLooping(looping);
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public void setRate(float rate) {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(rate));
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public void setSource(@NotNull Source source) {
        Intrinsics.checkNotNullParameter(source, "source");
        reset();
        source.setForMediaPlayer(this.mediaPlayer);
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public void setVolume(float leftVolume, float rightVolume) {
        this.mediaPlayer.setVolume(leftVolume, rightVolume);
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public void start() {
        setRate(this.wrappedPlayer.getRate());
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public void stop() throws IllegalStateException {
        this.mediaPlayer.stop();
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public void updateContext(@NotNull AudioContextAndroid context) throws IllegalArgumentException {
        Intrinsics.checkNotNullParameter(context, "context");
        context.setAttributesOnPlayer(this.mediaPlayer);
        if (context.getStayAwake()) {
            this.mediaPlayer.setWakeMode(this.wrappedPlayer.getApplicationContext(), 1);
        }
    }
}
