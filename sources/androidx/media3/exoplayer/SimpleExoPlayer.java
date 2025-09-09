package androidx.media3.exoplayer;

import android.content.Context;
import android.media.AudioDeviceInfo;
import android.os.Looper;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import androidx.media3.common.AudioAttributes;
import androidx.media3.common.AuxEffectInfo;
import androidx.media3.common.BasePlayer;
import androidx.media3.common.DeviceInfo;
import androidx.media3.common.Effect;
import androidx.media3.common.Format;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;
import androidx.media3.common.PlaybackParameters;
import androidx.media3.common.Player;
import androidx.media3.common.PriorityTaskManager;
import androidx.media3.common.Timeline;
import androidx.media3.common.TrackSelectionParameters;
import androidx.media3.common.Tracks;
import androidx.media3.common.VideoSize;
import androidx.media3.common.text.CueGroup;
import androidx.media3.common.util.Clock;
import androidx.media3.common.util.ConditionVariable;
import androidx.media3.common.util.Size;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.PlayerMessage;
import androidx.media3.exoplayer.analytics.AnalyticsCollector;
import androidx.media3.exoplayer.analytics.AnalyticsListener;
import androidx.media3.exoplayer.image.ImageOutput;
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.ShuffleOrder;
import androidx.media3.exoplayer.source.TrackGroupArray;
import androidx.media3.exoplayer.trackselection.TrackSelectionArray;
import androidx.media3.exoplayer.trackselection.TrackSelector;
import androidx.media3.exoplayer.upstream.BandwidthMeter;
import androidx.media3.exoplayer.video.VideoFrameMetadataListener;
import androidx.media3.exoplayer.video.spherical.CameraMotionListener;
import androidx.media3.extractor.ExtractorsFactory;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.List;

@UnstableApi
@Deprecated
/* loaded from: classes.dex */
public class SimpleExoPlayer extends BasePlayer implements ExoPlayer, ExoPlayer.AudioComponent, ExoPlayer.VideoComponent, ExoPlayer.TextComponent, ExoPlayer.DeviceComponent {
    private final ConditionVariable constructorFinished;
    private final ExoPlayerImpl player;

    SimpleExoPlayer(ExoPlayer.Builder builder) {
        ConditionVariable conditionVariable = new ConditionVariable();
        this.constructorFinished = conditionVariable;
        try {
            this.player = new ExoPlayerImpl(builder, this);
            conditionVariable.open();
        } catch (Throwable th) {
            this.constructorFinished.open();
            throw th;
        }
    }

    private void blockUntilConstructorFinished() {
        this.constructorFinished.blockUninterruptible();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public void addAnalyticsListener(AnalyticsListener analyticsListener) {
        blockUntilConstructorFinished();
        this.player.addAnalyticsListener(analyticsListener);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public void addAudioOffloadListener(ExoPlayer.AudioOffloadListener audioOffloadListener) {
        blockUntilConstructorFinished();
        this.player.addAudioOffloadListener(audioOffloadListener);
    }

    @Override // androidx.media3.common.Player
    public void addListener(Player.Listener listener) {
        blockUntilConstructorFinished();
        this.player.addListener(listener);
    }

    @Override // androidx.media3.common.Player
    public void addMediaItems(int i2, List<MediaItem> list) {
        blockUntilConstructorFinished();
        this.player.addMediaItems(i2, list);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public void addMediaSource(MediaSource mediaSource) {
        blockUntilConstructorFinished();
        this.player.addMediaSource(mediaSource);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public void addMediaSources(List<MediaSource> list) {
        blockUntilConstructorFinished();
        this.player.addMediaSources(list);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer, androidx.media3.exoplayer.ExoPlayer.AudioComponent
    public void clearAuxEffectInfo() {
        blockUntilConstructorFinished();
        this.player.clearAuxEffectInfo();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer, androidx.media3.exoplayer.ExoPlayer.VideoComponent
    public void clearCameraMotionListener(CameraMotionListener cameraMotionListener) {
        blockUntilConstructorFinished();
        this.player.clearCameraMotionListener(cameraMotionListener);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer, androidx.media3.exoplayer.ExoPlayer.VideoComponent
    public void clearVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener) {
        blockUntilConstructorFinished();
        this.player.clearVideoFrameMetadataListener(videoFrameMetadataListener);
    }

    @Override // androidx.media3.common.Player
    public void clearVideoSurface() {
        blockUntilConstructorFinished();
        this.player.clearVideoSurface();
    }

    @Override // androidx.media3.common.Player
    public void clearVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder) {
        blockUntilConstructorFinished();
        this.player.clearVideoSurfaceHolder(surfaceHolder);
    }

    @Override // androidx.media3.common.Player
    public void clearVideoSurfaceView(@Nullable SurfaceView surfaceView) {
        blockUntilConstructorFinished();
        this.player.clearVideoSurfaceView(surfaceView);
    }

    @Override // androidx.media3.common.Player
    public void clearVideoTextureView(@Nullable TextureView textureView) {
        blockUntilConstructorFinished();
        this.player.clearVideoTextureView(textureView);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public PlayerMessage createMessage(PlayerMessage.Target target) {
        blockUntilConstructorFinished();
        return this.player.createMessage(target);
    }

    @Override // androidx.media3.common.Player
    @Deprecated
    public void decreaseDeviceVolume() {
        blockUntilConstructorFinished();
        this.player.decreaseDeviceVolume();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public AnalyticsCollector getAnalyticsCollector() {
        blockUntilConstructorFinished();
        return this.player.getAnalyticsCollector();
    }

    @Override // androidx.media3.common.Player
    public Looper getApplicationLooper() {
        blockUntilConstructorFinished();
        return this.player.getApplicationLooper();
    }

    @Override // androidx.media3.common.Player
    public AudioAttributes getAudioAttributes() {
        blockUntilConstructorFinished();
        return this.player.getAudioAttributes();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    @Nullable
    @Deprecated
    public ExoPlayer.AudioComponent getAudioComponent() {
        return this;
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    @Nullable
    public DecoderCounters getAudioDecoderCounters() {
        blockUntilConstructorFinished();
        return this.player.getAudioDecoderCounters();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    @Nullable
    public Format getAudioFormat() {
        blockUntilConstructorFinished();
        return this.player.getAudioFormat();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer, androidx.media3.exoplayer.ExoPlayer.AudioComponent
    public int getAudioSessionId() {
        blockUntilConstructorFinished();
        return this.player.getAudioSessionId();
    }

    @Override // androidx.media3.common.Player
    public Player.Commands getAvailableCommands() {
        blockUntilConstructorFinished();
        return this.player.getAvailableCommands();
    }

    @Override // androidx.media3.common.Player
    public long getBufferedPosition() {
        blockUntilConstructorFinished();
        return this.player.getBufferedPosition();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public Clock getClock() {
        blockUntilConstructorFinished();
        return this.player.getClock();
    }

    @Override // androidx.media3.common.Player
    public long getContentBufferedPosition() {
        blockUntilConstructorFinished();
        return this.player.getContentBufferedPosition();
    }

    @Override // androidx.media3.common.Player
    public long getContentPosition() {
        blockUntilConstructorFinished();
        return this.player.getContentPosition();
    }

    @Override // androidx.media3.common.Player
    public int getCurrentAdGroupIndex() {
        blockUntilConstructorFinished();
        return this.player.getCurrentAdGroupIndex();
    }

    @Override // androidx.media3.common.Player
    public int getCurrentAdIndexInAdGroup() {
        blockUntilConstructorFinished();
        return this.player.getCurrentAdIndexInAdGroup();
    }

    @Override // androidx.media3.common.Player
    public CueGroup getCurrentCues() {
        blockUntilConstructorFinished();
        return this.player.getCurrentCues();
    }

    @Override // androidx.media3.common.Player
    public int getCurrentMediaItemIndex() {
        blockUntilConstructorFinished();
        return this.player.getCurrentMediaItemIndex();
    }

    @Override // androidx.media3.common.Player
    public int getCurrentPeriodIndex() {
        blockUntilConstructorFinished();
        return this.player.getCurrentPeriodIndex();
    }

    @Override // androidx.media3.common.Player
    public long getCurrentPosition() {
        blockUntilConstructorFinished();
        return this.player.getCurrentPosition();
    }

    @Override // androidx.media3.common.Player
    public Timeline getCurrentTimeline() {
        blockUntilConstructorFinished();
        return this.player.getCurrentTimeline();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    @Deprecated
    public TrackGroupArray getCurrentTrackGroups() {
        blockUntilConstructorFinished();
        return this.player.getCurrentTrackGroups();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    @Deprecated
    public TrackSelectionArray getCurrentTrackSelections() {
        blockUntilConstructorFinished();
        return this.player.getCurrentTrackSelections();
    }

    @Override // androidx.media3.common.Player
    public Tracks getCurrentTracks() {
        blockUntilConstructorFinished();
        return this.player.getCurrentTracks();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    @Nullable
    @Deprecated
    public ExoPlayer.DeviceComponent getDeviceComponent() {
        return this;
    }

    @Override // androidx.media3.common.Player
    public DeviceInfo getDeviceInfo() {
        blockUntilConstructorFinished();
        return this.player.getDeviceInfo();
    }

    @Override // androidx.media3.common.Player
    public int getDeviceVolume() {
        blockUntilConstructorFinished();
        return this.player.getDeviceVolume();
    }

    @Override // androidx.media3.common.Player
    public long getDuration() {
        blockUntilConstructorFinished();
        return this.player.getDuration();
    }

    @Override // androidx.media3.common.Player
    public long getMaxSeekToPreviousPosition() {
        blockUntilConstructorFinished();
        return this.player.getMaxSeekToPreviousPosition();
    }

    @Override // androidx.media3.common.Player
    public MediaMetadata getMediaMetadata() {
        blockUntilConstructorFinished();
        return this.player.getMediaMetadata();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public boolean getPauseAtEndOfMediaItems() {
        blockUntilConstructorFinished();
        return this.player.getPauseAtEndOfMediaItems();
    }

    @Override // androidx.media3.common.Player
    public boolean getPlayWhenReady() {
        blockUntilConstructorFinished();
        return this.player.getPlayWhenReady();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public Looper getPlaybackLooper() {
        blockUntilConstructorFinished();
        return this.player.getPlaybackLooper();
    }

    @Override // androidx.media3.common.Player
    public PlaybackParameters getPlaybackParameters() {
        blockUntilConstructorFinished();
        return this.player.getPlaybackParameters();
    }

    @Override // androidx.media3.common.Player
    public int getPlaybackState() {
        blockUntilConstructorFinished();
        return this.player.getPlaybackState();
    }

    @Override // androidx.media3.common.Player
    public int getPlaybackSuppressionReason() {
        blockUntilConstructorFinished();
        return this.player.getPlaybackSuppressionReason();
    }

    @Override // androidx.media3.common.Player
    public MediaMetadata getPlaylistMetadata() {
        blockUntilConstructorFinished();
        return this.player.getPlaylistMetadata();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public ExoPlayer.PreloadConfiguration getPreloadConfiguration() {
        blockUntilConstructorFinished();
        return this.player.getPreloadConfiguration();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public Renderer getRenderer(int i2) {
        blockUntilConstructorFinished();
        return this.player.getRenderer(i2);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public int getRendererCount() {
        blockUntilConstructorFinished();
        return this.player.getRendererCount();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public int getRendererType(int i2) {
        blockUntilConstructorFinished();
        return this.player.getRendererType(i2);
    }

    @Override // androidx.media3.common.Player
    public int getRepeatMode() {
        blockUntilConstructorFinished();
        return this.player.getRepeatMode();
    }

    @Override // androidx.media3.common.Player
    public long getSeekBackIncrement() {
        blockUntilConstructorFinished();
        return this.player.getSeekBackIncrement();
    }

    @Override // androidx.media3.common.Player
    public long getSeekForwardIncrement() {
        blockUntilConstructorFinished();
        return this.player.getSeekForwardIncrement();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public SeekParameters getSeekParameters() {
        blockUntilConstructorFinished();
        return this.player.getSeekParameters();
    }

    @Override // androidx.media3.common.Player
    public boolean getShuffleModeEnabled() {
        blockUntilConstructorFinished();
        return this.player.getShuffleModeEnabled();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer, androidx.media3.exoplayer.ExoPlayer.AudioComponent
    public boolean getSkipSilenceEnabled() {
        blockUntilConstructorFinished();
        return this.player.getSkipSilenceEnabled();
    }

    @Override // androidx.media3.common.Player
    public Size getSurfaceSize() {
        blockUntilConstructorFinished();
        return this.player.getSurfaceSize();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    @Nullable
    @Deprecated
    public ExoPlayer.TextComponent getTextComponent() {
        return this;
    }

    @Override // androidx.media3.common.Player
    public long getTotalBufferedDuration() {
        blockUntilConstructorFinished();
        return this.player.getTotalBufferedDuration();
    }

    @Override // androidx.media3.common.Player
    public TrackSelectionParameters getTrackSelectionParameters() {
        blockUntilConstructorFinished();
        return this.player.getTrackSelectionParameters();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public TrackSelector getTrackSelector() {
        blockUntilConstructorFinished();
        return this.player.getTrackSelector();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer, androidx.media3.exoplayer.ExoPlayer.VideoComponent
    public int getVideoChangeFrameRateStrategy() {
        blockUntilConstructorFinished();
        return this.player.getVideoChangeFrameRateStrategy();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    @Nullable
    @Deprecated
    public ExoPlayer.VideoComponent getVideoComponent() {
        return this;
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    @Nullable
    public DecoderCounters getVideoDecoderCounters() {
        blockUntilConstructorFinished();
        return this.player.getVideoDecoderCounters();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    @Nullable
    public Format getVideoFormat() {
        blockUntilConstructorFinished();
        return this.player.getVideoFormat();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer, androidx.media3.exoplayer.ExoPlayer.VideoComponent
    public int getVideoScalingMode() {
        blockUntilConstructorFinished();
        return this.player.getVideoScalingMode();
    }

    @Override // androidx.media3.common.Player
    public VideoSize getVideoSize() {
        blockUntilConstructorFinished();
        return this.player.getVideoSize();
    }

    @Override // androidx.media3.common.Player
    public float getVolume() {
        blockUntilConstructorFinished();
        return this.player.getVolume();
    }

    @Override // androidx.media3.common.Player
    @Deprecated
    public void increaseDeviceVolume() {
        blockUntilConstructorFinished();
        this.player.increaseDeviceVolume();
    }

    @Override // androidx.media3.common.Player
    public boolean isDeviceMuted() {
        blockUntilConstructorFinished();
        return this.player.isDeviceMuted();
    }

    @Override // androidx.media3.common.Player
    public boolean isLoading() {
        blockUntilConstructorFinished();
        return this.player.isLoading();
    }

    @Override // androidx.media3.common.Player
    public boolean isPlayingAd() {
        blockUntilConstructorFinished();
        return this.player.isPlayingAd();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public boolean isReleased() {
        return this.player.isReleased();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public boolean isSleepingForOffload() {
        blockUntilConstructorFinished();
        return this.player.isSleepingForOffload();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public boolean isTunnelingEnabled() {
        blockUntilConstructorFinished();
        return this.player.isTunnelingEnabled();
    }

    @Override // androidx.media3.common.Player
    public void moveMediaItems(int i2, int i3, int i4) {
        blockUntilConstructorFinished();
        this.player.moveMediaItems(i2, i3, i4);
    }

    @Override // androidx.media3.common.Player
    public void prepare() {
        blockUntilConstructorFinished();
        this.player.prepare();
    }

    @Override // androidx.media3.common.Player
    public void release() {
        blockUntilConstructorFinished();
        this.player.release();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public void removeAnalyticsListener(AnalyticsListener analyticsListener) {
        blockUntilConstructorFinished();
        this.player.removeAnalyticsListener(analyticsListener);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public void removeAudioOffloadListener(ExoPlayer.AudioOffloadListener audioOffloadListener) {
        blockUntilConstructorFinished();
        this.player.removeAudioOffloadListener(audioOffloadListener);
    }

    @Override // androidx.media3.common.Player
    public void removeListener(Player.Listener listener) {
        blockUntilConstructorFinished();
        this.player.removeListener(listener);
    }

    @Override // androidx.media3.common.Player
    public void removeMediaItems(int i2, int i3) {
        blockUntilConstructorFinished();
        this.player.removeMediaItems(i2, i3);
    }

    @Override // androidx.media3.common.Player
    public void replaceMediaItems(int i2, int i3, List<MediaItem> list) {
        blockUntilConstructorFinished();
        this.player.replaceMediaItems(i2, i3, list);
    }

    @Override // androidx.media3.common.BasePlayer
    @VisibleForTesting(otherwise = 4)
    public void seekTo(int i2, long j2, int i3, boolean z2) {
        blockUntilConstructorFinished();
        this.player.seekTo(i2, j2, i3, z2);
    }

    @Override // androidx.media3.common.Player
    public void setAudioAttributes(AudioAttributes audioAttributes, boolean z2) {
        blockUntilConstructorFinished();
        this.player.setAudioAttributes(audioAttributes, z2);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer, androidx.media3.exoplayer.ExoPlayer.AudioComponent
    public void setAudioSessionId(int i2) {
        blockUntilConstructorFinished();
        this.player.setAudioSessionId(i2);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer, androidx.media3.exoplayer.ExoPlayer.AudioComponent
    public void setAuxEffectInfo(AuxEffectInfo auxEffectInfo) {
        blockUntilConstructorFinished();
        this.player.setAuxEffectInfo(auxEffectInfo);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer, androidx.media3.exoplayer.ExoPlayer.VideoComponent
    public void setCameraMotionListener(CameraMotionListener cameraMotionListener) {
        blockUntilConstructorFinished();
        this.player.setCameraMotionListener(cameraMotionListener);
    }

    @Override // androidx.media3.common.Player
    @Deprecated
    public void setDeviceMuted(boolean z2) {
        blockUntilConstructorFinished();
        this.player.setDeviceMuted(z2);
    }

    @Override // androidx.media3.common.Player
    @Deprecated
    public void setDeviceVolume(int i2) {
        blockUntilConstructorFinished();
        this.player.setDeviceVolume(i2);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public void setForegroundMode(boolean z2) {
        blockUntilConstructorFinished();
        this.player.setForegroundMode(z2);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public void setHandleAudioBecomingNoisy(boolean z2) {
        blockUntilConstructorFinished();
        this.player.setHandleAudioBecomingNoisy(z2);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public void setImageOutput(@Nullable ImageOutput imageOutput) {
        blockUntilConstructorFinished();
        this.player.setImageOutput(imageOutput);
    }

    @Override // androidx.media3.common.Player
    public void setMediaItems(List<MediaItem> list, boolean z2) {
        blockUntilConstructorFinished();
        this.player.setMediaItems(list, z2);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public void setMediaSource(MediaSource mediaSource) {
        blockUntilConstructorFinished();
        this.player.setMediaSource(mediaSource);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public void setMediaSources(List<MediaSource> list) {
        blockUntilConstructorFinished();
        this.player.setMediaSources(list);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public void setPauseAtEndOfMediaItems(boolean z2) {
        blockUntilConstructorFinished();
        this.player.setPauseAtEndOfMediaItems(z2);
    }

    @Override // androidx.media3.common.Player
    public void setPlayWhenReady(boolean z2) {
        blockUntilConstructorFinished();
        this.player.setPlayWhenReady(z2);
    }

    @Override // androidx.media3.common.Player
    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        blockUntilConstructorFinished();
        this.player.setPlaybackParameters(playbackParameters);
    }

    @Override // androidx.media3.common.Player
    public void setPlaylistMetadata(MediaMetadata mediaMetadata) {
        blockUntilConstructorFinished();
        this.player.setPlaylistMetadata(mediaMetadata);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    @RequiresApi(23)
    public void setPreferredAudioDevice(@Nullable AudioDeviceInfo audioDeviceInfo) {
        blockUntilConstructorFinished();
        this.player.setPreferredAudioDevice(audioDeviceInfo);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public void setPreloadConfiguration(ExoPlayer.PreloadConfiguration preloadConfiguration) {
        blockUntilConstructorFinished();
        this.player.setPreloadConfiguration(preloadConfiguration);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public void setPriority(int i2) {
        blockUntilConstructorFinished();
        this.player.setPriority(i2);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public void setPriorityTaskManager(@Nullable PriorityTaskManager priorityTaskManager) {
        blockUntilConstructorFinished();
        this.player.setPriorityTaskManager(priorityTaskManager);
    }

    @Override // androidx.media3.common.Player
    public void setRepeatMode(int i2) {
        blockUntilConstructorFinished();
        this.player.setRepeatMode(i2);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public void setSeekParameters(@Nullable SeekParameters seekParameters) {
        blockUntilConstructorFinished();
        this.player.setSeekParameters(seekParameters);
    }

    @Override // androidx.media3.common.Player
    public void setShuffleModeEnabled(boolean z2) {
        blockUntilConstructorFinished();
        this.player.setShuffleModeEnabled(z2);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public void setShuffleOrder(ShuffleOrder shuffleOrder) {
        blockUntilConstructorFinished();
        this.player.setShuffleOrder(shuffleOrder);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer, androidx.media3.exoplayer.ExoPlayer.AudioComponent
    public void setSkipSilenceEnabled(boolean z2) {
        blockUntilConstructorFinished();
        this.player.setSkipSilenceEnabled(z2);
    }

    @Override // androidx.media3.common.Player
    public void setTrackSelectionParameters(TrackSelectionParameters trackSelectionParameters) {
        blockUntilConstructorFinished();
        this.player.setTrackSelectionParameters(trackSelectionParameters);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer, androidx.media3.exoplayer.ExoPlayer.VideoComponent
    public void setVideoChangeFrameRateStrategy(int i2) {
        blockUntilConstructorFinished();
        this.player.setVideoChangeFrameRateStrategy(i2);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public void setVideoEffects(List<Effect> list) throws NoSuchMethodException, SecurityException {
        blockUntilConstructorFinished();
        this.player.setVideoEffects(list);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer, androidx.media3.exoplayer.ExoPlayer.VideoComponent
    public void setVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener) {
        blockUntilConstructorFinished();
        this.player.setVideoFrameMetadataListener(videoFrameMetadataListener);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer, androidx.media3.exoplayer.ExoPlayer.VideoComponent
    public void setVideoScalingMode(int i2) {
        blockUntilConstructorFinished();
        this.player.setVideoScalingMode(i2);
    }

    @Override // androidx.media3.common.Player
    public void setVideoSurface(@Nullable Surface surface) {
        blockUntilConstructorFinished();
        this.player.setVideoSurface(surface);
    }

    @Override // androidx.media3.common.Player
    public void setVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder) {
        blockUntilConstructorFinished();
        this.player.setVideoSurfaceHolder(surfaceHolder);
    }

    @Override // androidx.media3.common.Player
    public void setVideoSurfaceView(@Nullable SurfaceView surfaceView) {
        blockUntilConstructorFinished();
        this.player.setVideoSurfaceView(surfaceView);
    }

    @Override // androidx.media3.common.Player
    public void setVideoTextureView(@Nullable TextureView textureView) {
        blockUntilConstructorFinished();
        this.player.setVideoTextureView(textureView);
    }

    @Override // androidx.media3.common.Player
    public void setVolume(float f2) {
        blockUntilConstructorFinished();
        this.player.setVolume(f2);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public void setWakeMode(int i2) {
        blockUntilConstructorFinished();
        this.player.setWakeMode(i2);
    }

    @Override // androidx.media3.common.Player
    public void stop() {
        blockUntilConstructorFinished();
        this.player.stop();
    }

    @Deprecated
    public static final class Builder {
        private final ExoPlayer.Builder wrappedBuilder;

        @Deprecated
        public Builder(Context context) {
            this.wrappedBuilder = new ExoPlayer.Builder(context);
        }

        @Deprecated
        public SimpleExoPlayer build() {
            return this.wrappedBuilder.w();
        }

        @CanIgnoreReturnValue
        @Deprecated
        public Builder experimentalSetForegroundModeTimeoutMs(long j2) {
            this.wrappedBuilder.experimentalSetForegroundModeTimeoutMs(j2);
            return this;
        }

        @CanIgnoreReturnValue
        @Deprecated
        public Builder setAnalyticsCollector(AnalyticsCollector analyticsCollector) {
            this.wrappedBuilder.setAnalyticsCollector(analyticsCollector);
            return this;
        }

        @CanIgnoreReturnValue
        @Deprecated
        public Builder setAudioAttributes(AudioAttributes audioAttributes, boolean z2) {
            this.wrappedBuilder.setAudioAttributes(audioAttributes, z2);
            return this;
        }

        @CanIgnoreReturnValue
        @Deprecated
        public Builder setBandwidthMeter(BandwidthMeter bandwidthMeter) {
            this.wrappedBuilder.setBandwidthMeter(bandwidthMeter);
            return this;
        }

        @CanIgnoreReturnValue
        @VisibleForTesting
        @Deprecated
        public Builder setClock(Clock clock) {
            this.wrappedBuilder.setClock(clock);
            return this;
        }

        @CanIgnoreReturnValue
        @Deprecated
        public Builder setDetachSurfaceTimeoutMs(long j2) {
            this.wrappedBuilder.setDetachSurfaceTimeoutMs(j2);
            return this;
        }

        @CanIgnoreReturnValue
        @Deprecated
        public Builder setHandleAudioBecomingNoisy(boolean z2) {
            this.wrappedBuilder.setHandleAudioBecomingNoisy(z2);
            return this;
        }

        @CanIgnoreReturnValue
        @Deprecated
        public Builder setLivePlaybackSpeedControl(LivePlaybackSpeedControl livePlaybackSpeedControl) {
            this.wrappedBuilder.setLivePlaybackSpeedControl(livePlaybackSpeedControl);
            return this;
        }

        @CanIgnoreReturnValue
        @Deprecated
        public Builder setLoadControl(LoadControl loadControl) {
            this.wrappedBuilder.setLoadControl(loadControl);
            return this;
        }

        @CanIgnoreReturnValue
        @Deprecated
        public Builder setLooper(Looper looper) {
            this.wrappedBuilder.setLooper(looper);
            return this;
        }

        @CanIgnoreReturnValue
        @Deprecated
        public Builder setMediaSourceFactory(MediaSource.Factory factory) {
            this.wrappedBuilder.setMediaSourceFactory(factory);
            return this;
        }

        @CanIgnoreReturnValue
        @Deprecated
        public Builder setPauseAtEndOfMediaItems(boolean z2) {
            this.wrappedBuilder.setPauseAtEndOfMediaItems(z2);
            return this;
        }

        @CanIgnoreReturnValue
        @Deprecated
        public Builder setPriorityTaskManager(@Nullable PriorityTaskManager priorityTaskManager) {
            this.wrappedBuilder.setPriorityTaskManager(priorityTaskManager);
            return this;
        }

        @CanIgnoreReturnValue
        @Deprecated
        public Builder setReleaseTimeoutMs(long j2) {
            this.wrappedBuilder.setReleaseTimeoutMs(j2);
            return this;
        }

        @CanIgnoreReturnValue
        @Deprecated
        public Builder setSeekBackIncrementMs(@IntRange(from = 1) long j2) {
            this.wrappedBuilder.setSeekBackIncrementMs(j2);
            return this;
        }

        @CanIgnoreReturnValue
        @Deprecated
        public Builder setSeekForwardIncrementMs(@IntRange(from = 1) long j2) {
            this.wrappedBuilder.setSeekForwardIncrementMs(j2);
            return this;
        }

        @CanIgnoreReturnValue
        @Deprecated
        public Builder setSeekParameters(SeekParameters seekParameters) {
            this.wrappedBuilder.setSeekParameters(seekParameters);
            return this;
        }

        @CanIgnoreReturnValue
        @Deprecated
        public Builder setSkipSilenceEnabled(boolean z2) {
            this.wrappedBuilder.setSkipSilenceEnabled(z2);
            return this;
        }

        @CanIgnoreReturnValue
        @Deprecated
        public Builder setTrackSelector(TrackSelector trackSelector) {
            this.wrappedBuilder.setTrackSelector(trackSelector);
            return this;
        }

        @CanIgnoreReturnValue
        @Deprecated
        public Builder setUseLazyPreparation(boolean z2) {
            this.wrappedBuilder.setUseLazyPreparation(z2);
            return this;
        }

        @CanIgnoreReturnValue
        @Deprecated
        public Builder setVideoChangeFrameRateStrategy(int i2) {
            this.wrappedBuilder.setVideoChangeFrameRateStrategy(i2);
            return this;
        }

        @CanIgnoreReturnValue
        @Deprecated
        public Builder setVideoScalingMode(int i2) {
            this.wrappedBuilder.setVideoScalingMode(i2);
            return this;
        }

        @CanIgnoreReturnValue
        @Deprecated
        public Builder setWakeMode(int i2) {
            this.wrappedBuilder.setWakeMode(i2);
            return this;
        }

        @Deprecated
        public Builder(Context context, RenderersFactory renderersFactory) {
            this.wrappedBuilder = new ExoPlayer.Builder(context, renderersFactory);
        }

        @Deprecated
        public Builder(Context context, ExtractorsFactory extractorsFactory) {
            this.wrappedBuilder = new ExoPlayer.Builder(context, new DefaultMediaSourceFactory(context, extractorsFactory));
        }

        @Deprecated
        public Builder(Context context, RenderersFactory renderersFactory, ExtractorsFactory extractorsFactory) {
            this.wrappedBuilder = new ExoPlayer.Builder(context, renderersFactory, new DefaultMediaSourceFactory(context, extractorsFactory));
        }

        @Deprecated
        public Builder(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, MediaSource.Factory factory, LoadControl loadControl, BandwidthMeter bandwidthMeter, AnalyticsCollector analyticsCollector) {
            this.wrappedBuilder = new ExoPlayer.Builder(context, renderersFactory, factory, trackSelector, loadControl, bandwidthMeter, analyticsCollector);
        }
    }

    @Override // androidx.media3.common.Player
    @Nullable
    public ExoPlaybackException getPlayerError() {
        blockUntilConstructorFinished();
        return this.player.getPlayerError();
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public void addMediaSource(int i2, MediaSource mediaSource) {
        blockUntilConstructorFinished();
        this.player.addMediaSource(i2, mediaSource);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public void addMediaSources(int i2, List<MediaSource> list) {
        blockUntilConstructorFinished();
        this.player.addMediaSources(i2, list);
    }

    @Override // androidx.media3.common.Player
    public void clearVideoSurface(@Nullable Surface surface) {
        blockUntilConstructorFinished();
        this.player.clearVideoSurface(surface);
    }

    @Override // androidx.media3.common.Player
    public void decreaseDeviceVolume(int i2) {
        blockUntilConstructorFinished();
        this.player.decreaseDeviceVolume(i2);
    }

    @Override // androidx.media3.common.Player
    public void increaseDeviceVolume(int i2) {
        blockUntilConstructorFinished();
        this.player.increaseDeviceVolume(i2);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    @Deprecated
    public void prepare(MediaSource mediaSource) {
        blockUntilConstructorFinished();
        this.player.prepare(mediaSource);
    }

    @Override // androidx.media3.common.Player
    public void setDeviceMuted(boolean z2, int i2) {
        blockUntilConstructorFinished();
        this.player.setDeviceMuted(z2, i2);
    }

    @Override // androidx.media3.common.Player
    public void setDeviceVolume(int i2, int i3) {
        blockUntilConstructorFinished();
        this.player.setDeviceVolume(i2, i3);
    }

    @Override // androidx.media3.common.Player
    public void setMediaItems(List<MediaItem> list, int i2, long j2) {
        blockUntilConstructorFinished();
        this.player.setMediaItems(list, i2, j2);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public void setMediaSource(MediaSource mediaSource, boolean z2) {
        blockUntilConstructorFinished();
        this.player.setMediaSource(mediaSource, z2);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public void setMediaSources(List<MediaSource> list, boolean z2) {
        blockUntilConstructorFinished();
        this.player.setMediaSources(list, z2);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    @Deprecated
    public void prepare(MediaSource mediaSource, boolean z2, boolean z3) {
        blockUntilConstructorFinished();
        this.player.prepare(mediaSource, z2, z3);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public void setMediaSource(MediaSource mediaSource, long j2) {
        blockUntilConstructorFinished();
        this.player.setMediaSource(mediaSource, j2);
    }

    @Override // androidx.media3.exoplayer.ExoPlayer
    public void setMediaSources(List<MediaSource> list, int i2, long j2) {
        blockUntilConstructorFinished();
        this.player.setMediaSources(list, i2, j2);
    }
}
