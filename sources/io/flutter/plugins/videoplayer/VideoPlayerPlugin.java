package io.flutter.plugins.videoplayer;

import android.content.Context;
import android.util.LongSparseArray;
import androidx.annotation.NonNull;
import io.flutter.FlutterInjector;
import io.flutter.Log;
import io.flutter.embedding.engine.loader.FlutterLoader;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugins.videoplayer.Messages;
import io.flutter.plugins.videoplayer.VideoAsset;
import io.flutter.view.TextureRegistry;
import java.util.Objects;

/* loaded from: classes4.dex */
public class VideoPlayerPlugin implements FlutterPlugin, Messages.AndroidVideoPlayerApi {
    private static final String TAG = "VideoPlayerPlugin";
    private FlutterState flutterState;
    private final LongSparseArray<VideoPlayer> videoPlayers = new LongSparseArray<>();
    private final VideoPlayerOptions options = new VideoPlayerOptions();

    private static final class FlutterState {
        final Context applicationContext;
        final BinaryMessenger binaryMessenger;
        final KeyForAssetFn keyForAsset;
        final KeyForAssetAndPackageName keyForAssetAndPackageName;
        final TextureRegistry textureRegistry;

        FlutterState(Context context, BinaryMessenger binaryMessenger, KeyForAssetFn keyForAssetFn, KeyForAssetAndPackageName keyForAssetAndPackageName, TextureRegistry textureRegistry) {
            this.applicationContext = context;
            this.binaryMessenger = binaryMessenger;
            this.keyForAsset = keyForAssetFn;
            this.keyForAssetAndPackageName = keyForAssetAndPackageName;
            this.textureRegistry = textureRegistry;
        }

        void startListening(VideoPlayerPlugin videoPlayerPlugin, BinaryMessenger binaryMessenger) {
            l.m(binaryMessenger, videoPlayerPlugin);
        }

        void stopListening(BinaryMessenger binaryMessenger) {
            l.m(binaryMessenger, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    interface KeyForAssetAndPackageName {
        String get(String str, String str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    interface KeyForAssetFn {
        String get(String str);
    }

    private void disposeAllPlayers() {
        for (int i2 = 0; i2 < this.videoPlayers.size(); i2++) {
            this.videoPlayers.valueAt(i2).dispose();
        }
        this.videoPlayers.clear();
    }

    @Override // io.flutter.plugins.videoplayer.Messages.AndroidVideoPlayerApi
    @NonNull
    public Messages.TextureMessage create(@NonNull Messages.CreateMessage createMessage) {
        VideoAsset videoAssetFromRemoteUrl;
        TextureRegistry.SurfaceTextureEntry surfaceTextureEntryCreateSurfaceTexture = this.flutterState.textureRegistry.createSurfaceTexture();
        EventChannel eventChannel = new EventChannel(this.flutterState.binaryMessenger, "flutter.io/videoPlayer/videoEvents" + surfaceTextureEntryCreateSurfaceTexture.id());
        if (createMessage.getAsset() != null) {
            videoAssetFromRemoteUrl = VideoAsset.fromAssetUrl("asset:///" + (createMessage.getPackageName() != null ? this.flutterState.keyForAssetAndPackageName.get(createMessage.getAsset(), createMessage.getPackageName()) : this.flutterState.keyForAsset.get(createMessage.getAsset())));
        } else if (createMessage.getUri().startsWith("rtsp://")) {
            videoAssetFromRemoteUrl = VideoAsset.fromRtspUrl(createMessage.getUri());
        } else {
            createMessage.getHttpHeaders();
            VideoAsset.StreamingFormat streamingFormat = VideoAsset.StreamingFormat.UNKNOWN;
            String formatHint = createMessage.getFormatHint();
            if (formatHint != null) {
                switch (formatHint) {
                    case "ss":
                        streamingFormat = VideoAsset.StreamingFormat.SMOOTH;
                        break;
                    case "hls":
                        streamingFormat = VideoAsset.StreamingFormat.HTTP_LIVE;
                        break;
                    case "dash":
                        streamingFormat = VideoAsset.StreamingFormat.DYNAMIC_ADAPTIVE;
                        break;
                }
            }
            videoAssetFromRemoteUrl = VideoAsset.fromRemoteUrl(createMessage.getUri(), streamingFormat, createMessage.getHttpHeaders());
        }
        this.videoPlayers.put(surfaceTextureEntryCreateSurfaceTexture.id(), VideoPlayer.create(this.flutterState.applicationContext, VideoPlayerEventCallbacks.bindTo(eventChannel), surfaceTextureEntryCreateSurfaceTexture, videoAssetFromRemoteUrl, this.options));
        return new Messages.TextureMessage.Builder().setTextureId(Long.valueOf(surfaceTextureEntryCreateSurfaceTexture.id())).build();
    }

    @Override // io.flutter.plugins.videoplayer.Messages.AndroidVideoPlayerApi
    public void dispose(@NonNull Messages.TextureMessage textureMessage) {
        this.videoPlayers.get(textureMessage.getTextureId().longValue()).dispose();
        this.videoPlayers.remove(textureMessage.getTextureId().longValue());
    }

    @Override // io.flutter.plugins.videoplayer.Messages.AndroidVideoPlayerApi
    public void initialize() {
        disposeAllPlayers();
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        FlutterInjector flutterInjectorInstance = FlutterInjector.instance();
        Context applicationContext = flutterPluginBinding.getApplicationContext();
        BinaryMessenger binaryMessenger = flutterPluginBinding.getBinaryMessenger();
        final FlutterLoader flutterLoader = flutterInjectorInstance.flutterLoader();
        Objects.requireNonNull(flutterLoader);
        KeyForAssetFn keyForAssetFn = new KeyForAssetFn() { // from class: io.flutter.plugins.videoplayer.m
            @Override // io.flutter.plugins.videoplayer.VideoPlayerPlugin.KeyForAssetFn
            public final String get(String str) {
                return flutterLoader.getLookupKeyForAsset(str);
            }
        };
        final FlutterLoader flutterLoader2 = flutterInjectorInstance.flutterLoader();
        Objects.requireNonNull(flutterLoader2);
        FlutterState flutterState = new FlutterState(applicationContext, binaryMessenger, keyForAssetFn, new KeyForAssetAndPackageName() { // from class: io.flutter.plugins.videoplayer.n
            @Override // io.flutter.plugins.videoplayer.VideoPlayerPlugin.KeyForAssetAndPackageName
            public final String get(String str, String str2) {
                return flutterLoader2.getLookupKeyForAsset(str, str2);
            }
        }, flutterPluginBinding.getTextureRegistry());
        this.flutterState = flutterState;
        flutterState.startListening(this, flutterPluginBinding.getBinaryMessenger());
    }

    public void onDestroy() {
        disposeAllPlayers();
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        if (this.flutterState == null) {
            Log.wtf(TAG, "Detached from the engine before registering to it.");
        }
        this.flutterState.stopListening(flutterPluginBinding.getBinaryMessenger());
        this.flutterState = null;
        onDestroy();
    }

    @Override // io.flutter.plugins.videoplayer.Messages.AndroidVideoPlayerApi
    public void pause(@NonNull Messages.TextureMessage textureMessage) {
        this.videoPlayers.get(textureMessage.getTextureId().longValue()).pause();
    }

    @Override // io.flutter.plugins.videoplayer.Messages.AndroidVideoPlayerApi
    public void play(@NonNull Messages.TextureMessage textureMessage) {
        this.videoPlayers.get(textureMessage.getTextureId().longValue()).play();
    }

    @Override // io.flutter.plugins.videoplayer.Messages.AndroidVideoPlayerApi
    @NonNull
    public Messages.PositionMessage position(@NonNull Messages.TextureMessage textureMessage) {
        VideoPlayer videoPlayer = this.videoPlayers.get(textureMessage.getTextureId().longValue());
        Messages.PositionMessage positionMessageBuild = new Messages.PositionMessage.Builder().setPosition(Long.valueOf(videoPlayer.getPosition())).setTextureId(textureMessage.getTextureId()).build();
        videoPlayer.sendBufferingUpdate();
        return positionMessageBuild;
    }

    @Override // io.flutter.plugins.videoplayer.Messages.AndroidVideoPlayerApi
    public void seekTo(@NonNull Messages.PositionMessage positionMessage) {
        this.videoPlayers.get(positionMessage.getTextureId().longValue()).seekTo(positionMessage.getPosition().intValue());
    }

    @Override // io.flutter.plugins.videoplayer.Messages.AndroidVideoPlayerApi
    public void setLooping(@NonNull Messages.LoopingMessage loopingMessage) {
        this.videoPlayers.get(loopingMessage.getTextureId().longValue()).setLooping(loopingMessage.getIsLooping().booleanValue());
    }

    @Override // io.flutter.plugins.videoplayer.Messages.AndroidVideoPlayerApi
    public void setMixWithOthers(@NonNull Messages.MixWithOthersMessage mixWithOthersMessage) {
        this.options.mixWithOthers = mixWithOthersMessage.getMixWithOthers().booleanValue();
    }

    @Override // io.flutter.plugins.videoplayer.Messages.AndroidVideoPlayerApi
    public void setPlaybackSpeed(@NonNull Messages.PlaybackSpeedMessage playbackSpeedMessage) {
        this.videoPlayers.get(playbackSpeedMessage.getTextureId().longValue()).setPlaybackSpeed(playbackSpeedMessage.getSpeed().doubleValue());
    }

    @Override // io.flutter.plugins.videoplayer.Messages.AndroidVideoPlayerApi
    public void setVolume(@NonNull Messages.VolumeMessage volumeMessage) {
        this.videoPlayers.get(volumeMessage.getTextureId().longValue()).setVolume(volumeMessage.getVolume().doubleValue());
    }
}
