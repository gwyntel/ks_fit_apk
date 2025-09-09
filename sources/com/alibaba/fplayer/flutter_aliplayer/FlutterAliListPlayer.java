package com.alibaba.fplayer.flutter_aliplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import androidx.health.connect.client.records.CervicalMucusRecord;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.aliyun.player.AliListPlayer;
import com.aliyun.player.AliPlayer;
import com.aliyun.player.AliPlayerFactory;
import com.aliyun.player.IPlayer;
import com.aliyun.player.nativeclass.CacheConfig;
import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.nativeclass.PlayerConfig;
import com.aliyun.player.nativeclass.TrackInfo;
import com.aliyun.player.source.StsInfo;
import com.aliyun.player.source.UrlSource;
import com.aliyun.player.source.VidAuth;
import com.aliyun.player.source.VidMps;
import com.aliyun.player.source.VidSts;
import com.aliyun.thumbnail.ThumbnailBitmapInfo;
import com.aliyun.thumbnail.ThumbnailHelper;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.kingsmith.miot.KsAction;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StringCodec;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import kotlin.text.Typography;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class FlutterAliListPlayer extends FlutterPlayerBase implements EventChannel.StreamHandler {
    private FlutterPlugin.FlutterPluginBinding binding;
    private AliListPlayer mAliListPlayer;
    private Context mContext;
    private EventChannel mEventChannel;
    private EventChannel.EventSink mEventSink;
    private Map<Integer, FlutterAliPlayerView> mFlutterAliPlayerViewMap;
    private FlutterInvokeCallback mFlutterInvokeCallback;
    private FlutterPlugin.FlutterPluginBinding mFlutterPluginBinding;
    private ThumbnailHelper mThumbnailHelper;

    public FlutterAliListPlayer(FlutterPlugin.FlutterPluginBinding flutterPluginBinding, String str) {
        this.mFlutterPluginBinding = flutterPluginBinding;
        this.mContext = flutterPluginBinding.getApplicationContext();
        this.mPlayerId = str;
        this.binding = flutterPluginBinding;
        this.mAliListPlayer = AliPlayerFactory.createAliListPlayer(flutterPluginBinding.getApplicationContext());
        EventChannel eventChannel = new EventChannel(this.mFlutterPluginBinding.getFlutterEngine().getDartExecutor(), "flutter_aliplayer_event");
        this.mEventChannel = eventChannel;
        eventChannel.setStreamHandler(this);
        this.mFlutterInvokeCallback = new FlutterInvokeCallback(this.mContext, flutterPluginBinding);
        initListener(this.mAliListPlayer);
    }

    private void addExtSubtitle(String str) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.addExtSubtitle(str);
        }
    }

    private void addUrlSource(String str, String str2) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.addUrl(str, str2);
        }
    }

    private void addVidSource(String str, String str2) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.addVid(str, str2);
        }
    }

    private void clear() {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.clear();
        }
    }

    private void createThumbnailHelper(String str) {
        ThumbnailHelper thumbnailHelper = new ThumbnailHelper(str);
        this.mThumbnailHelper = thumbnailHelper;
        thumbnailHelper.setOnPrepareListener(new ThumbnailHelper.OnPrepareListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliListPlayer.3
            @Override // com.aliyun.thumbnail.ThumbnailHelper.OnPrepareListener
            public void onPrepareFail() {
                HashMap map = new HashMap();
                map.put("method", "thumbnail_onPrepared_Fail");
                FlutterAliListPlayer.this.mEventSink.success(map);
            }

            @Override // com.aliyun.thumbnail.ThumbnailHelper.OnPrepareListener
            public void onPrepareSuccess() {
                HashMap map = new HashMap();
                map.put("method", "thumbnail_onPrepared_Success");
                FlutterAliListPlayer.this.mEventSink.success(map);
            }
        });
        this.mThumbnailHelper.setOnThumbnailGetListener(new ThumbnailHelper.OnThumbnailGetListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliListPlayer.4
            @Override // com.aliyun.thumbnail.ThumbnailHelper.OnThumbnailGetListener
            public void onThumbnailGetFail(long j2, String str2) {
                HashMap map = new HashMap();
                map.put("method", "onThumbnailGetFail");
                FlutterAliListPlayer.this.mEventSink.success(map);
            }

            @Override // com.aliyun.thumbnail.ThumbnailHelper.OnThumbnailGetListener
            public void onThumbnailGetSuccess(long j2, ThumbnailBitmapInfo thumbnailBitmapInfo) {
                if (thumbnailBitmapInfo == null || thumbnailBitmapInfo.getThumbnailBitmap() == null) {
                    return;
                }
                HashMap map = new HashMap();
                Bitmap thumbnailBitmap = thumbnailBitmapInfo.getThumbnailBitmap();
                if (thumbnailBitmap != null) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    thumbnailBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    thumbnailBitmap.recycle();
                    long[] positionRange = thumbnailBitmapInfo.getPositionRange();
                    map.put("method", "onThumbnailGetSuccess");
                    map.put("thumbnailbitmap", byteArrayOutputStream.toByteArray());
                    map.put("thumbnailRange", positionRange);
                    FlutterAliListPlayer.this.mEventSink.success(map);
                }
            }
        });
        this.mThumbnailHelper.prepare();
    }

    private void enableOnPrepared(boolean z2, final String str) {
        if (z2) {
            this.mAliListPlayer.setOnPreparedListener(new IPlayer.OnPreparedListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.a
                @Override // com.aliyun.player.IPlayer.OnPreparedListener
                public final void onPrepared() throws JSONException {
                    this.f8780a.lambda$enableOnPrepared$0(str);
                }
            });
            return;
        }
        this.mAliListPlayer.setOnPreparedListener(null);
        if (AliChannelPool.getInstance().containChannel(str)) {
            AliChannelPool.getInstance().removeChannel(str);
        }
    }

    private void enableOnRenderingStart(boolean z2, final String str) {
        if (z2) {
            this.mAliListPlayer.setOnRenderingStartListener(new IPlayer.OnRenderingStartListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliListPlayer.5
                @Override // com.aliyun.player.IPlayer.OnRenderingStartListener
                public void onRenderingStart() throws JSONException {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("method", "onRenderingStart");
                        jSONObject.put("playerId", FlutterAliListPlayer.this.mPlayerId);
                        if (AliChannelPool.getInstance().containChannel(str)) {
                            AliChannelPool.getInstance().channelForKey(str).send(jSONObject.toString());
                        } else {
                            BasicMessageChannel<String> basicMessageChannel = new BasicMessageChannel<>(FlutterAliListPlayer.this.binding.getBinaryMessenger(), str, StringCodec.INSTANCE);
                            basicMessageChannel.send(jSONObject.toString());
                            AliChannelPool.getInstance().addChannel(str, basicMessageChannel);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            });
            return;
        }
        this.mAliListPlayer.setOnRenderingStartListener(null);
        if (AliChannelPool.getInstance().containChannel(str)) {
            AliChannelPool.getInstance().removeChannel(str);
        }
    }

    private void enableOnStateChanged(boolean z2, final String str) {
        if (z2) {
            this.mAliListPlayer.setOnStateChangedListener(new IPlayer.OnStateChangedListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliListPlayer.6
                @Override // com.aliyun.player.IPlayer.OnStateChangedListener
                public void onStateChanged(int i2) throws JSONException {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("method", "onStateChanged");
                        jSONObject.put("newState", i2);
                        jSONObject.put("playerId", FlutterAliListPlayer.this.mPlayerId);
                        if (AliChannelPool.getInstance().containChannel(str)) {
                            AliChannelPool.getInstance().channelForKey(str).send(jSONObject.toString());
                        } else {
                            BasicMessageChannel<String> basicMessageChannel = new BasicMessageChannel<>(FlutterAliListPlayer.this.binding.getBinaryMessenger(), str, StringCodec.INSTANCE);
                            basicMessageChannel.send(jSONObject.toString());
                            AliChannelPool.getInstance().addChannel(str, basicMessageChannel);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            });
            return;
        }
        this.mAliListPlayer.setOnStateChangedListener(null);
        if (AliChannelPool.getInstance().containChannel(str)) {
            AliChannelPool.getInstance().removeChannel(str);
        }
    }

    private int getAlphaRenderMode() {
        int value = IPlayer.AlphaRenderMode.RENDER_MODE_ALPHA_NONE.getValue();
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        return aliListPlayer != null ? aliListPlayer.getAlphaRenderMode().getValue() : value;
    }

    private CacheConfig getCacheConfig() {
        return new CacheConfig();
    }

    private PlayerConfig getConfig() {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            return aliListPlayer.getConfig();
        }
        return null;
    }

    private TrackInfo getCurrentTrack(int i2) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            return aliListPlayer.currentTrack(i2);
        }
        return null;
    }

    private String getCurrentUid() {
        return this.mAliListPlayer.getCurrentUid();
    }

    private MediaInfo getMediaInfo() {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            return aliListPlayer.getMediaInfo();
        }
        return null;
    }

    private int getMirrorMode() {
        int value = IPlayer.MirrorMode.MIRROR_MODE_NONE.getValue();
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        return aliListPlayer != null ? aliListPlayer.getMirrorMode().getValue() : value;
    }

    private int getRotateMode() {
        int value = IPlayer.RotateMode.ROTATE_0.getValue();
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        return aliListPlayer != null ? aliListPlayer.getRotateMode().getValue() : value;
    }

    private int getScaleMode() {
        int value = IPlayer.ScaleMode.SCALE_ASPECT_FIT.getValue();
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        return aliListPlayer != null ? aliListPlayer.getScaleMode().getValue() : value;
    }

    private double getSpeed() {
        if (this.mAliListPlayer != null) {
            return r0.getSpeed();
        }
        return 0.0d;
    }

    private MediaInfo getSubMediaInfo() {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            return aliListPlayer.getSubMediaInfo();
        }
        return null;
    }

    private double getVolume() {
        if (this.mAliListPlayer != null) {
            return r0.getVolume();
        }
        return 1.0d;
    }

    private Boolean isAutoPlay() {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.isAutoPlay();
        }
        return Boolean.FALSE;
    }

    private Boolean isLoop() {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        return Boolean.valueOf(aliListPlayer != null && aliListPlayer.isLoop());
    }

    private Boolean isMuted() {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.isMute();
        }
        return Boolean.FALSE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$enableOnPrepared$0(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("method", "onPrepared");
            jSONObject.put("playerId", this.mPlayerId);
            if (AliChannelPool.getInstance().containChannel(str)) {
                AliChannelPool.getInstance().channelForKey(str).send(jSONObject.toString());
            } else {
                BasicMessageChannel<String> basicMessageChannel = new BasicMessageChannel<>(this.binding.getBinaryMessenger(), str, StringCodec.INSTANCE);
                basicMessageChannel.send(jSONObject.toString());
                AliChannelPool.getInstance().addChannel(str, basicMessageChannel);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void moveTo(String str, StsInfo stsInfo) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.moveTo(str, stsInfo);
        }
    }

    private void moveToNext(StsInfo stsInfo) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            if (stsInfo == null) {
                aliListPlayer.moveToNext();
            } else {
                aliListPlayer.moveToNext(stsInfo);
            }
        }
    }

    private void moveToPre(StsInfo stsInfo) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            if (stsInfo == null) {
                aliListPlayer.moveToPrev();
            } else {
                aliListPlayer.moveToPrev(stsInfo);
            }
        }
    }

    private void pause() {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.pause();
        }
    }

    private void prepare() {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.prepare();
        }
    }

    private void release() {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.release();
            this.mAliListPlayer = null;
        }
    }

    private void releaseAsync() {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.releaseAsync();
            this.mAliListPlayer = null;
        }
    }

    private void removeSource(String str) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.removeSource(str);
        }
    }

    private void requestBitmapAtPosition(int i2) {
        ThumbnailHelper thumbnailHelper = this.mThumbnailHelper;
        if (thumbnailHelper != null) {
            thumbnailHelper.requestBitmapAtPosition(i2);
        }
    }

    private void seekTo(long j2, int i2) {
        if (this.mAliListPlayer != null) {
            IPlayer.SeekMode seekMode = IPlayer.SeekMode.Accurate;
            if (i2 != seekMode.getValue()) {
                seekMode = IPlayer.SeekMode.Inaccurate;
            }
            this.mAliListPlayer.seekTo(j2, seekMode);
        }
    }

    private void selectExtSubtitle(int i2, boolean z2) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.selectExtSubtitle(i2, z2);
        }
    }

    private void selectTrack(int i2, boolean z2) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.selectTrack(i2, z2);
        }
    }

    private void sendCustomEvent(AliListPlayer aliListPlayer, String str) {
        if (aliListPlayer != null) {
            aliListPlayer.sendCustomEvent(str);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x000e A[PHI: r1
      0x000e: PHI (r1v4 com.aliyun.player.IPlayer$AlphaRenderMode) = 
      (r1v0 com.aliyun.player.IPlayer$AlphaRenderMode)
      (r1v1 com.aliyun.player.IPlayer$AlphaRenderMode)
      (r1v2 com.aliyun.player.IPlayer$AlphaRenderMode)
      (r1v3 com.aliyun.player.IPlayer$AlphaRenderMode)
     binds: [B:5:0x000c, B:8:0x0016, B:11:0x001f, B:14:0x0028] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void setAlphaRenderMode(int r4) {
        /*
            r3 = this;
            com.aliyun.player.AliListPlayer r0 = r3.mAliListPlayer
            if (r0 == 0) goto L30
            com.aliyun.player.IPlayer$AlphaRenderMode r0 = com.aliyun.player.IPlayer.AlphaRenderMode.RENDER_MODE_ALPHA_NONE
            com.aliyun.player.IPlayer$AlphaRenderMode r1 = com.aliyun.player.IPlayer.AlphaRenderMode.RENDER_MODE_ALPHA_AT_LEFT
            int r2 = r1.getValue()
            if (r4 != r2) goto L10
        Le:
            r0 = r1
            goto L2b
        L10:
            com.aliyun.player.IPlayer$AlphaRenderMode r1 = com.aliyun.player.IPlayer.AlphaRenderMode.RENDER_MODE_ALPHA_AT_RIGHT
            int r2 = r1.getValue()
            if (r4 != r2) goto L19
            goto Le
        L19:
            com.aliyun.player.IPlayer$AlphaRenderMode r1 = com.aliyun.player.IPlayer.AlphaRenderMode.RENDER_MODE_ALPHA_AT_TOP
            int r2 = r1.getValue()
            if (r4 != r2) goto L22
            goto Le
        L22:
            com.aliyun.player.IPlayer$AlphaRenderMode r1 = com.aliyun.player.IPlayer.AlphaRenderMode.RENDER_MODE_ALPHA_AT_BOTTOM
            int r2 = r1.getValue()
            if (r4 != r2) goto L2b
            goto Le
        L2b:
            com.aliyun.player.AliListPlayer r4 = r3.mAliListPlayer
            r4.setAlphaRenderMode(r0)
        L30:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fplayer.flutter_aliplayer.FlutterAliListPlayer.setAlphaRenderMode(int):void");
    }

    private void setAutoPlay(Boolean bool) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.setAutoPlay(bool.booleanValue());
        }
    }

    private void setCacheConfig(CacheConfig cacheConfig) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.setCacheConfig(cacheConfig);
        }
    }

    private void setConfig(PlayerConfig playerConfig) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.setConfig(playerConfig);
        }
    }

    private void setDataSource(String str) {
        if (this.mAliListPlayer != null) {
            UrlSource urlSource = new UrlSource();
            urlSource.setUri(str);
            this.mAliListPlayer.setDataSource(urlSource);
        }
    }

    private void setDefinition(String str) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.setDefinition(str);
        }
    }

    private void setEnableHardWareDecoder(Boolean bool) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.enableHardwareDecoder(bool.booleanValue());
        }
    }

    private void setLoop(Boolean bool) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.setLoop(bool.booleanValue());
        }
    }

    private void setMaxPreloadMemorySizeMB(Integer num) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.setMaxPreloadMemorySizeMB(num.intValue());
        }
    }

    private void setMirrorMode(int i2) {
        if (this.mAliListPlayer != null) {
            IPlayer.MirrorMode mirrorMode = IPlayer.MirrorMode.MIRROR_MODE_HORIZONTAL;
            if (i2 != mirrorMode.getValue()) {
                mirrorMode = IPlayer.MirrorMode.MIRROR_MODE_VERTICAL;
                if (i2 != mirrorMode.getValue()) {
                    mirrorMode = IPlayer.MirrorMode.MIRROR_MODE_NONE;
                }
            }
            this.mAliListPlayer.setMirrorMode(mirrorMode);
        }
    }

    private void setMuted(Boolean bool) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.setMute(bool.booleanValue());
        }
    }

    private void setOption(int i2, Object obj) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            if (obj instanceof Integer) {
                aliListPlayer.setOption(i2, ((Integer) obj).intValue());
            } else if (obj instanceof String) {
                aliListPlayer.setOption(i2, (String) obj);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0015 A[PHI: r1
      0x0015: PHI (r1v4 com.aliyun.player.IPlayer$OutputAudioChannel) = (r1v2 com.aliyun.player.IPlayer$OutputAudioChannel), (r1v3 com.aliyun.player.IPlayer$OutputAudioChannel) binds: [B:8:0x0013, B:11:0x001d] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void setOutputAudioChannel(int r4) {
        /*
            r3 = this;
            com.aliyun.player.IPlayer$OutputAudioChannel r0 = com.aliyun.player.IPlayer.OutputAudioChannel.OUTPUT_AUDIO_CHANNEL_NONE
            com.aliyun.player.AliListPlayer r1 = r3.mAliListPlayer
            if (r1 == 0) goto L25
            int r1 = r0.getValue()
            if (r4 != r1) goto Ld
            goto L20
        Ld:
            com.aliyun.player.IPlayer$OutputAudioChannel r1 = com.aliyun.player.IPlayer.OutputAudioChannel.OUTPUT_AUDIO_CHANNEL_LEFT
            int r2 = r1.getValue()
            if (r4 != r2) goto L17
        L15:
            r0 = r1
            goto L20
        L17:
            com.aliyun.player.IPlayer$OutputAudioChannel r1 = com.aliyun.player.IPlayer.OutputAudioChannel.OUTPUT_AUDIO_CHANNEL_RIGHT
            int r2 = r1.getValue()
            if (r4 != r2) goto L20
            goto L15
        L20:
            com.aliyun.player.AliListPlayer r4 = r3.mAliListPlayer
            r4.setOutputAudioChannel(r0)
        L25:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fplayer.flutter_aliplayer.FlutterAliListPlayer.setOutputAudioChannel(int):void");
    }

    private void setPreloadCount(int i2) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.setPreloadCount(i2);
        }
    }

    private void setRenderFrameCallbackConfig(Boolean bool, Boolean bool2) {
        IPlayer.RenderFrameCallbackConfig renderFrameCallbackConfig = new IPlayer.RenderFrameCallbackConfig();
        renderFrameCallbackConfig.mAudioDataAddr = bool.booleanValue();
        renderFrameCallbackConfig.mVideoDataAddr = bool2.booleanValue();
        this.mAliListPlayer.setRenderFrameCallbackConfig(renderFrameCallbackConfig);
    }

    private void setRotateMode(int i2) {
        if (this.mAliListPlayer != null) {
            IPlayer.RotateMode rotateMode = IPlayer.RotateMode.ROTATE_90;
            if (i2 != rotateMode.getValue()) {
                rotateMode = IPlayer.RotateMode.ROTATE_180;
                if (i2 != rotateMode.getValue()) {
                    rotateMode = IPlayer.RotateMode.ROTATE_270;
                    if (i2 != rotateMode.getValue()) {
                        rotateMode = IPlayer.RotateMode.ROTATE_0;
                    }
                }
            }
            this.mAliListPlayer.setRotateMode(rotateMode);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0015 A[PHI: r1
      0x0015: PHI (r1v3 com.aliyun.player.IPlayer$ScaleMode) = (r1v1 com.aliyun.player.IPlayer$ScaleMode), (r1v2 com.aliyun.player.IPlayer$ScaleMode) binds: [B:8:0x0013, B:11:0x001d] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void setScaleMode(int r4) {
        /*
            r3 = this;
            com.aliyun.player.AliListPlayer r0 = r3.mAliListPlayer
            if (r0 == 0) goto L25
            com.aliyun.player.IPlayer$ScaleMode r0 = com.aliyun.player.IPlayer.ScaleMode.SCALE_ASPECT_FIT
            int r1 = r0.getValue()
            if (r4 != r1) goto Ld
            goto L20
        Ld:
            com.aliyun.player.IPlayer$ScaleMode r1 = com.aliyun.player.IPlayer.ScaleMode.SCALE_ASPECT_FILL
            int r2 = r1.getValue()
            if (r4 != r2) goto L17
        L15:
            r0 = r1
            goto L20
        L17:
            com.aliyun.player.IPlayer$ScaleMode r1 = com.aliyun.player.IPlayer.ScaleMode.SCALE_TO_FILL
            int r2 = r1.getValue()
            if (r4 != r2) goto L20
            goto L15
        L20:
            com.aliyun.player.AliListPlayer r4 = r3.mAliListPlayer
            r4.setScaleMode(r0)
        L25:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fplayer.flutter_aliplayer.FlutterAliListPlayer.setScaleMode(int):void");
    }

    private void setSpeed(double d2) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.setSpeed((float) d2);
        }
    }

    private void setStartTime(long j2, int i2) {
        if (this.mAliListPlayer != null) {
            IPlayer.SeekMode seekMode = IPlayer.SeekMode.Accurate;
            if (i2 != seekMode.getValue()) {
                seekMode = IPlayer.SeekMode.Inaccurate;
            }
            this.mAliListPlayer.setStartTime(j2, seekMode);
        }
    }

    private void setStreamDelayTime(int i2, int i3) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.setStreamDelayTime(i2, i3);
        }
    }

    private void setVideoBackgroundColor(int i2) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.setVideoBackgroundColor(i2);
        }
    }

    private void setVolume(double d2) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.setVolume((float) d2);
        }
    }

    private void snapshot() {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.snapshot();
        }
    }

    private void start() {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.start();
        }
    }

    private void stop() {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.stop();
        }
    }

    @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterPlayerBase
    public IPlayer getAliPlayer() {
        return this.mAliListPlayer;
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onCancel(Object obj) {
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onListen(Object obj, EventChannel.EventSink eventSink) {
        this.mEventSink = eventSink;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        String str = methodCall.method;
        str.hashCode();
        char c2 = 65535;
        switch (str.hashCode()) {
            case -2111662467:
                if (str.equals("getPlayedDuration")) {
                    c2 = 0;
                    break;
                }
                break;
            case -2074269692:
                if (str.equals("setMirrorMode")) {
                    c2 = 1;
                    break;
                }
                break;
            case -1993091403:
                if (str.equals("releaseAsync")) {
                    c2 = 2;
                    break;
                }
                break;
            case -1737670682:
                if (str.equals("setOnPrepare")) {
                    c2 = 3;
                    break;
                }
                break;
            case -1722681560:
                if (str.equals("setPlayerView")) {
                    c2 = 4;
                    break;
                }
                break;
            case -1585709253:
                if (str.equals("setOnRenderFrameCallback")) {
                    c2 = 5;
                    break;
                }
                break;
            case -1557553345:
                if (str.equals("moveToNext")) {
                    c2 = 6;
                    break;
                }
                break;
            case -1445342060:
                if (str.equals("getScalingMode")) {
                    c2 = 7;
                    break;
                }
                break;
            case -1386731507:
                if (str.equals("getCurrentUid")) {
                    c2 = '\b';
                    break;
                }
                break;
            case -1209771576:
                if (str.equals("getCurrentTrack")) {
                    c2 = '\t';
                    break;
                }
                break;
            case -1180327186:
                if (str.equals("isLoop")) {
                    c2 = '\n';
                    break;
                }
                break;
            case -1068263892:
                if (str.equals("moveTo")) {
                    c2 = 11;
                    break;
                }
                break;
            case -1021009828:
                if (str.equals("setVideoBackgroundColor")) {
                    c2 = '\f';
                    break;
                }
                break;
            case -987958532:
                if (str.equals("setRenderFrameCallbackConfig")) {
                    c2 = CharUtils.CR;
                    break;
                }
                break;
            case -906224877:
                if (str.equals("seekTo")) {
                    c2 = 14;
                    break;
                }
                break;
            case -867038203:
                if (str.equals("setAutoPlay")) {
                    c2 = 15;
                    break;
                }
                break;
            case -857941569:
                if (str.equals("removeSource")) {
                    c2 = 16;
                    break;
                }
                break;
            case -696759686:
                if (str.equals("setOnPreRenderFrameCallback")) {
                    c2 = 17;
                    break;
                }
                break;
            case -635391346:
                if (str.equals("setStreamDelayTime")) {
                    c2 = 18;
                    break;
                }
                break;
            case -600377823:
                if (str.equals("getAlphaRenderMode")) {
                    c2 = 19;
                    break;
                }
                break;
            case -595122677:
                if (str.equals("setOnVerifyTimeExpireCallback")) {
                    c2 = 20;
                    break;
                }
                break;
            case -589906931:
                if (str.equals("setStartTime")) {
                    c2 = 21;
                    break;
                }
                break;
            case -544850898:
                if (str.equals("getCacheConfig")) {
                    c2 = 22;
                    break;
                }
                break;
            case -538035371:
                if (str.equals("setDefinition")) {
                    c2 = 23;
                    break;
                }
                break;
            case -496314679:
                if (str.equals("addUrlSource")) {
                    c2 = 24;
                    break;
                }
                break;
            case -446448198:
                if (str.equals("requestBitmapAtPosition")) {
                    c2 = 25;
                    break;
                }
                break;
            case -376693356:
                if (str.equals("getRotateMode")) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                break;
            case -318370553:
                if (str.equals("prepare")) {
                    c2 = 27;
                    break;
                }
                break;
            case -271857407:
                if (str.equals("sendCustomEvent")) {
                    c2 = 28;
                    break;
                }
                break;
            case -263724706:
                if (str.equals("setMaxPreloadMemorySizeMB")) {
                    c2 = 29;
                    break;
                }
                break;
            case -183771640:
                if (str.equals("setPreloadCount")) {
                    c2 = 30;
                    break;
                }
                break;
            case -75188906:
                if (str.equals("getRate")) {
                    c2 = 31;
                    break;
                }
                break;
            case 3443508:
                if (str.equals("play")) {
                    c2 = ' ';
                    break;
                }
                break;
            case 3540994:
                if (str.equals("stop")) {
                    c2 = '!';
                    break;
                }
                break;
            case 94746189:
                if (str.equals(CervicalMucusRecord.Appearance.CLEAR)) {
                    c2 = Typography.quote;
                    break;
                }
                break;
            case 106440182:
                if (str.equals("pause")) {
                    c2 = '#';
                    break;
                }
                break;
            case 126605892:
                if (str.equals("setConfig")) {
                    c2 = '$';
                    break;
                }
                break;
            case 205228463:
                if (str.equals("selectTrack")) {
                    c2 = '%';
                    break;
                }
                break;
            case 284874180:
                if (str.equals("snapshot")) {
                    c2 = Typography.amp;
                    break;
                }
                break;
            case 319408503:
                if (str.equals("setOnRenderingStart")) {
                    c2 = '\'';
                    break;
                }
                break;
            case 319655829:
                if (str.equals("setAlphaRenderMode")) {
                    c2 = '(';
                    break;
                }
                break;
            case 341222968:
                if (str.equals("getConfig")) {
                    c2 = ')';
                    break;
                }
                break;
            case 397437856:
                if (str.equals("setRotateMode")) {
                    c2 = '*';
                    break;
                }
                break;
            case 471261047:
                if (str.equals("setOption")) {
                    c2 = '+';
                    break;
                }
                break;
            case 498711032:
                if (str.equals("addExtSubtitle")) {
                    c2 = ',';
                    break;
                }
                break;
            case 645338317:
                if (str.equals("isAutoPlay")) {
                    c2 = '-';
                    break;
                }
                break;
            case 670514716:
                if (str.equals("setVolume")) {
                    c2 = '.';
                    break;
                }
                break;
            case 680712171:
                if (str.equals("addVidSource")) {
                    c2 = IOUtils.DIR_SEPARATOR_UNIX;
                    break;
                }
                break;
            case 693594615:
                if (str.equals("setEnableHardwareDecoder")) {
                    c2 = '0';
                    break;
                }
                break;
            case 885131792:
                if (str.equals("getVolume")) {
                    c2 = '1';
                    break;
                }
                break;
            case 1058137303:
                if (str.equals("moveToPre")) {
                    c2 = '2';
                    break;
                }
                break;
            case 1077889032:
                if (str.equals("setScalingMode")) {
                    c2 = '3';
                    break;
                }
                break;
            case 1224698654:
                if (str.equals("createThumbnailHelper")) {
                    c2 = '4';
                    break;
                }
                break;
            case 1398977065:
                if (str.equals("setMuted")) {
                    c2 = '5';
                    break;
                }
                break;
            case 1404354821:
                if (str.equals(KsAction.setSpeed)) {
                    c2 = '6';
                    break;
                }
                break;
            case 1429489341:
                if (str.equals("selectExtSubtitle")) {
                    c2 = '7';
                    break;
                }
                break;
            case 1446566392:
                if (str.equals("getMirrorMode")) {
                    c2 = '8';
                    break;
                }
                break;
            case 1557372922:
                if (str.equals("destroy")) {
                    c2 = '9';
                    break;
                }
                break;
            case 1566880456:
                if (str.equals("getSubMediaInfo")) {
                    c2 = ':';
                    break;
                }
                break;
            case 1647305060:
                if (str.equals("setOnStateChanged")) {
                    c2 = ';';
                    break;
                }
                break;
            case 1854370800:
                if (str.equals("setOutputAudioChannel")) {
                    c2 = Typography.less;
                    break;
                }
                break;
            case 1906263646:
                if (str.equals("setTraceID")) {
                    c2 = com.alipay.sdk.m.n.a.f9565h;
                    break;
                }
                break;
            case 1978380194:
                if (str.equals("setCacheConfig")) {
                    c2 = Typography.greater;
                    break;
                }
                break;
            case 1984755238:
                if (str.equals("setLoop")) {
                    c2 = '?';
                    break;
                }
                break;
            case 1984920674:
                if (str.equals("setRate")) {
                    c2 = '@';
                    break;
                }
                break;
            case 2065669729:
                if (str.equals("isMuted")) {
                    c2 = 'A';
                    break;
                }
                break;
            case 2099205923:
                if (str.equals("setConvertURLCallback")) {
                    c2 = 'B';
                    break;
                }
                break;
            case 2130520060:
                if (str.equals("getMediaInfo")) {
                    c2 = 'C';
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                result.success(Long.valueOf(this.mAliListPlayer.getPlayedDuration()));
                break;
            case 1:
                setMirrorMode(((Integer) methodCall.argument("arg")).intValue());
                result.success(null);
                break;
            case 2:
                releaseAsync();
                result.success(null);
                break;
            case 3:
                enableOnPrepared(Boolean.TRUE.equals(methodCall.argument("arg")), "listPlayer_onPrepare" + this.mPlayerId);
                break;
            case 4:
                break;
            case 5:
                FlutterAliPlayerUtils.registerRenderFrameCallback(this.mAliListPlayer, this.mFlutterInvokeCallback);
                result.success(null);
                break;
            case 6:
                Map map = (Map) methodCall.argument("arg");
                String str2 = (String) map.get("accId");
                String str3 = (String) map.get("accKey");
                String str4 = (String) map.get("token");
                String str5 = (String) map.get("region");
                if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4)) {
                    moveToNext(null);
                } else {
                    StsInfo stsInfo = new StsInfo();
                    stsInfo.setAccessKeyId(str2);
                    stsInfo.setAccessKeySecret(str3);
                    stsInfo.setSecurityToken(str4);
                    stsInfo.setRegion(str5);
                    moveToNext(stsInfo);
                }
                result.success(null);
                break;
            case 7:
                result.success(Integer.valueOf(getScaleMode()));
                break;
            case '\b':
                result.success(getCurrentUid());
                break;
            case '\t':
                TrackInfo currentTrack = getCurrentTrack(((Integer) methodCall.argument("arg")).intValue());
                if (currentTrack != null) {
                    HashMap map2 = new HashMap();
                    map2.put("vodFormat", currentTrack.getVodFormat());
                    map2.put("videoHeight", Integer.valueOf(currentTrack.getVideoHeight()));
                    map2.put("videoWidth", Integer.valueOf(currentTrack.getVideoHeight()));
                    map2.put("subtitleLanguage", currentTrack.getSubtitleLang());
                    map2.put("trackBitrate", Integer.valueOf(currentTrack.getVideoBitrate()));
                    map2.put("vodFileSize", Long.valueOf(currentTrack.getVodFileSize()));
                    map2.put("trackIndex", Integer.valueOf(currentTrack.getIndex()));
                    map2.put("trackDefinition", currentTrack.getVodDefinition());
                    map2.put("audioSampleFormat", Integer.valueOf(currentTrack.getAudioSampleFormat()));
                    map2.put("audioLanguage", currentTrack.getAudioLang());
                    map2.put("vodPlayUrl", currentTrack.getVodPlayUrl());
                    map2.put("trackType", Integer.valueOf(currentTrack.getType().ordinal()));
                    map2.put("audioSamplerate", Integer.valueOf(currentTrack.getAudioSampleRate()));
                    map2.put("audioChannels", Integer.valueOf(currentTrack.getAudioChannels()));
                    result.success(map2);
                    break;
                }
                break;
            case '\n':
                result.success(isLoop());
                break;
            case 11:
                Map map3 = (Map) methodCall.argument("arg");
                String str6 = (String) map3.get("accId");
                String str7 = (String) map3.get("accKey");
                String str8 = (String) map3.get("token");
                String str9 = (String) map3.get("region");
                String str10 = (String) map3.get("uid");
                if (TextUtils.isEmpty(str6)) {
                    moveTo(str10);
                } else {
                    StsInfo stsInfo2 = new StsInfo();
                    stsInfo2.setAccessKeyId(str6);
                    stsInfo2.setAccessKeySecret(str7);
                    stsInfo2.setSecurityToken(str8);
                    stsInfo2.setRegion(str9);
                    moveTo(str10, stsInfo2);
                }
                result.success(null);
                break;
            case '\f':
                setVideoBackgroundColor(((Integer) methodCall.argument("arg")).intValue());
                result.success(null);
                break;
            case '\r':
                Map map4 = (Map) methodCall.argument("arg");
                setRenderFrameCallbackConfig((Boolean) map4.get("mAudioData"), (Boolean) map4.get("mVideoData"));
                result.success(null);
                break;
            case 14:
                seekTo(((Integer) r1.get(RequestParameters.POSITION)).intValue(), ((Integer) ((Map) methodCall.argument("arg")).get("seekMode")).intValue());
                result.success(null);
                break;
            case 15:
                setAutoPlay((Boolean) methodCall.argument("arg"));
                result.success(null);
                break;
            case 16:
                removeSource((String) methodCall.arguments());
                result.success(null);
                break;
            case 17:
                FlutterAliPlayerUtils.registerPreRenderFrameCallback(this.mAliListPlayer, this.mFlutterInvokeCallback);
                result.success(null);
                break;
            case 18:
                Map map5 = (Map) methodCall.argument("arg");
                setStreamDelayTime(((Integer) map5.get(FirebaseAnalytics.Param.INDEX)).intValue(), ((Integer) map5.get("time")).intValue());
                result.success(null);
                break;
            case 19:
                result.success(Integer.valueOf(getAlphaRenderMode()));
                break;
            case 20:
                this.mAliListPlayer.setOnVerifyTimeExpireCallback(new AliPlayer.OnVerifyTimeExpireCallback() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliListPlayer.1
                    @Override // com.aliyun.player.AliPlayer.OnVerifyTimeExpireCallback
                    public AliPlayer.Status onVerifyAuth(VidAuth vidAuth) {
                        return null;
                    }

                    @Override // com.aliyun.player.AliPlayer.OnVerifyTimeExpireCallback
                    public AliPlayer.Status onVerifySts(StsInfo stsInfo3) {
                        return null;
                    }
                });
                break;
            case 21:
                setStartTime(((Integer) r1.get("time")).intValue(), ((Integer) ((Map) methodCall.argument("arg")).get("seekMode")).intValue());
                result.success(null);
                break;
            case 22:
                result.success((Map) loadGson().fromJson(loadGson().toJson(getCacheConfig()), Map.class));
                break;
            case 23:
                setDefinition((String) methodCall.argument("arg"));
                result.success(null);
                break;
            case 24:
                Map map6 = (Map) methodCall.argument("arg");
                addUrlSource((String) map6.get("url"), (String) map6.get("uid"));
                result.success(null);
                break;
            case 25:
                requestBitmapAtPosition(((Integer) methodCall.argument("arg")).intValue());
                result.success(null);
                break;
            case 26:
                result.success(Integer.valueOf(getRotateMode()));
                break;
            case 27:
                prepare();
                result.success(null);
                break;
            case 28:
                sendCustomEvent(this.mAliListPlayer, (String) ((Map) methodCall.arguments()).get("arg"));
                result.success(null);
                break;
            case 29:
                setMaxPreloadMemorySizeMB((Integer) methodCall.argument("arg"));
                result.success(null);
                break;
            case 30:
                setPreloadCount(((Integer) methodCall.argument("arg")).intValue());
                result.success(null);
                break;
            case 31:
                result.success(Double.valueOf(getSpeed()));
                break;
            case ' ':
                start();
                result.success(null);
                break;
            case '!':
                stop();
                result.success(null);
                break;
            case '\"':
                clear();
                result.success(null);
                break;
            case '#':
                pause();
                result.success(null);
                break;
            case '$':
                Map map7 = (Map) methodCall.argument("arg");
                if (getConfig() != null) {
                    setConfig((PlayerConfig) loadGson().fromJson(loadGson().toJson(map7), PlayerConfig.class));
                }
                result.success(null);
                break;
            case '%':
                Map map8 = (Map) methodCall.argument("arg");
                selectTrack(((Integer) map8.get("trackIdx")).intValue(), ((Integer) map8.get("accurate")).intValue() == 1);
                result.success(null);
                break;
            case '&':
                this.mSnapShotPath = methodCall.argument("arg").toString();
                snapshot();
                result.success(null);
                break;
            case '\'':
                enableOnRenderingStart(Boolean.TRUE.equals(methodCall.argument("arg")), "listPlayer_onRenderingStart" + this.mPlayerId);
                break;
            case '(':
                setAlphaRenderMode(((Integer) methodCall.argument("arg")).intValue());
                result.success(null);
                break;
            case ')':
                result.success((Map) loadGson().fromJson(loadGson().toJson(getConfig()), Map.class));
                break;
            case '*':
                setRotateMode(((Integer) methodCall.argument("arg")).intValue());
                result.success(null);
                break;
            case '+':
                Map map9 = (Map) methodCall.argument("arg");
                setOption(((Integer) map9.get("opt1")).intValue(), map9.get("opt2"));
                result.success(null);
                break;
            case ',':
                addExtSubtitle((String) methodCall.arguments);
                result.success(null);
                break;
            case '-':
                result.success(isAutoPlay());
                break;
            case '.':
                setVolume(((Double) methodCall.argument("arg")).doubleValue());
                result.success(null);
                break;
            case '/':
                Map map10 = (Map) methodCall.argument("arg");
                addVidSource((String) map10.get("vid"), (String) map10.get("uid"));
                result.success(null);
                break;
            case '0':
                setEnableHardWareDecoder((Boolean) methodCall.argument("arg"));
                result.success(null);
                break;
            case '1':
                result.success(Double.valueOf(getVolume()));
                break;
            case '2':
                Map map11 = (Map) methodCall.argument("arg");
                String str11 = (String) map11.get("accId");
                String str12 = (String) map11.get("accKey");
                String str13 = (String) map11.get("token");
                String str14 = (String) map11.get("region");
                if (TextUtils.isEmpty(str11) || TextUtils.isEmpty(str12) || TextUtils.isEmpty(str13) || TextUtils.isEmpty(str14)) {
                    moveToPre(null);
                } else {
                    StsInfo stsInfo3 = new StsInfo();
                    stsInfo3.setAccessKeyId(str11);
                    stsInfo3.setAccessKeySecret(str12);
                    stsInfo3.setSecurityToken(str13);
                    stsInfo3.setRegion(str14);
                    moveToPre(stsInfo3);
                }
                result.success(null);
                break;
            case '3':
                setScaleMode(((Integer) methodCall.argument("arg")).intValue());
                result.success(null);
                break;
            case '4':
                createThumbnailHelper((String) methodCall.argument("arg"));
                result.success(null);
                break;
            case '5':
                setMuted((Boolean) methodCall.argument("arg"));
                result.success(null);
                break;
            case '6':
                if (this.mAliListPlayer != null) {
                    this.mAliListPlayer.setSpeed((float) ((Double) methodCall.argument("arg")).doubleValue());
                }
                result.success(null);
                break;
            case '7':
                Map map12 = (Map) methodCall.argument("arg");
                selectExtSubtitle(((Integer) map12.get("trackIndex")).intValue(), ((Boolean) map12.get("enable")).booleanValue());
                result.success(null);
                break;
            case '8':
                result.success(Integer.valueOf(getMirrorMode()));
                break;
            case '9':
                release();
                result.success(null);
                break;
            case ':':
                FlutterAliPlayerUtils.executeMediaInfo(result, getSubMediaInfo());
                break;
            case ';':
                enableOnStateChanged(Boolean.TRUE.equals(methodCall.argument("arg")), "listPlayer_onStateChanged" + this.mPlayerId);
                break;
            case '<':
                setOutputAudioChannel(((Integer) methodCall.argument("arg")).intValue());
                result.success(null);
                break;
            case '=':
                this.mAliListPlayer.setTraceId((String) methodCall.argument("arg"));
                result.success(null);
                break;
            case '>':
                setCacheConfig((CacheConfig) loadGson().fromJson(loadGson().toJson((Map) methodCall.argument("arg")), CacheConfig.class));
                result.success(null);
                break;
            case '?':
                setLoop((Boolean) methodCall.argument("arg"));
                result.success(null);
                break;
            case '@':
                setSpeed(((Double) methodCall.argument("arg")).doubleValue());
                result.success(null);
                break;
            case 'A':
                result.success(isMuted());
                break;
            case 'B':
                AliPlayerFactory.setConvertURLCallback(new IPlayer.ConvertURLCallback() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliListPlayer.2
                    @Override // com.aliyun.player.IPlayer.ConvertURLCallback
                    public String convertURL(String str15, String str16) {
                        String str17 = (String) FlutterAliListPlayer.this.mFlutterInvokeCallback.invokeTwoParameterFlutterCallback(str15, str16, "convertURL");
                        return str17 != null ? str17 : str15;
                    }
                });
                result.success(null);
                break;
            case 'C':
                FlutterAliPlayerUtils.executeMediaInfo(result, getMediaInfo());
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    public void setViewMap(Map<Integer, FlutterAliPlayerView> map) {
        this.mFlutterAliPlayerViewMap = map;
    }

    private void moveTo(String str) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.moveTo(str);
        }
    }

    private void setDataSource(VidSts vidSts) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.setDataSource(vidSts);
        }
    }

    private void setDataSource(VidAuth vidAuth) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.setDataSource(vidAuth);
        }
    }

    private void setDataSource(VidMps vidMps) {
        AliListPlayer aliListPlayer = this.mAliListPlayer;
        if (aliListPlayer != null) {
            aliListPlayer.setDataSource(vidMps);
        }
    }
}
