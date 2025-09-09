package com.alibaba.fplayer.flutter_aliplayer;

import android.content.Context;
import android.graphics.Bitmap;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.aliyun.player.AliPlayer;
import com.aliyun.player.AliPlayerFactory;
import com.aliyun.player.FilterConfig;
import com.aliyun.player.IPlayer;
import com.aliyun.player.nativeclass.CacheConfig;
import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.nativeclass.PlayerConfig;
import com.aliyun.player.nativeclass.TrackInfo;
import com.aliyun.player.source.LiveSts;
import com.aliyun.player.source.StsInfo;
import com.aliyun.player.source.UrlSource;
import com.aliyun.player.source.VidAuth;
import com.aliyun.player.source.VidMps;
import com.aliyun.player.source.VidSts;
import com.aliyun.thumbnail.ThumbnailBitmapInfo;
import com.aliyun.thumbnail.ThumbnailHelper;
import com.google.gson.reflect.TypeToken;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.StringCodec;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class FlutterAliPlayer extends FlutterPlayerBase {
    private FlutterPlugin.FlutterPluginBinding binding;
    private AliPlayer mAliPlayer;
    private FlutterInvokeCallback mFlutterInvokeCallback;
    private ThumbnailHelper mThumbnailHelper;

    public FlutterAliPlayer(FlutterPlugin.FlutterPluginBinding flutterPluginBinding, String str) {
        this.mPlayerId = str;
        Context applicationContext = flutterPluginBinding.getApplicationContext();
        this.mContext = applicationContext;
        this.mAliPlayer = AliPlayerFactory.createAliPlayer(applicationContext);
        this.mFlutterInvokeCallback = new FlutterInvokeCallback(this.mContext, flutterPluginBinding);
        this.binding = flutterPluginBinding;
        initListener(this.mAliPlayer);
    }

    private void addExtSubtitle(AliPlayer aliPlayer, String str) {
        if (aliPlayer != null) {
            aliPlayer.addExtSubtitle(str);
        }
    }

    private void createThumbnailHelper(String str) {
        ThumbnailHelper thumbnailHelper = new ThumbnailHelper(str);
        this.mThumbnailHelper = thumbnailHelper;
        thumbnailHelper.setOnPrepareListener(new ThumbnailHelper.OnPrepareListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayer.6
            @Override // com.aliyun.thumbnail.ThumbnailHelper.OnPrepareListener
            public void onPrepareFail() {
                HashMap map = new HashMap();
                map.put("method", "thumbnail_onPrepared_Fail");
                FlutterAliPlayerListener flutterAliPlayerListener = FlutterAliPlayer.this.mFlutterAliPlayerListener;
                if (flutterAliPlayerListener != null) {
                    flutterAliPlayerListener.onThumbnailPrepareFail(map);
                }
            }

            @Override // com.aliyun.thumbnail.ThumbnailHelper.OnPrepareListener
            public void onPrepareSuccess() {
                HashMap map = new HashMap();
                map.put("method", "thumbnail_onPrepared_Success");
                FlutterAliPlayerListener flutterAliPlayerListener = FlutterAliPlayer.this.mFlutterAliPlayerListener;
                if (flutterAliPlayerListener != null) {
                    flutterAliPlayerListener.onThumbnailPrepareSuccess(map);
                }
            }
        });
        this.mThumbnailHelper.setOnThumbnailGetListener(new ThumbnailHelper.OnThumbnailGetListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayer.7
            @Override // com.aliyun.thumbnail.ThumbnailHelper.OnThumbnailGetListener
            public void onThumbnailGetFail(long j2, String str2) {
                HashMap map = new HashMap();
                map.put("method", "onThumbnailGetFail");
                FlutterAliPlayerListener flutterAliPlayerListener = FlutterAliPlayer.this.mFlutterAliPlayerListener;
                if (flutterAliPlayerListener != null) {
                    flutterAliPlayerListener.onThumbnailGetFail(map);
                }
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
                    FlutterAliPlayerListener flutterAliPlayerListener = FlutterAliPlayer.this.mFlutterAliPlayerListener;
                    if (flutterAliPlayerListener != null) {
                        flutterAliPlayerListener.onThumbnailGetSuccess(map);
                    }
                }
            }
        });
        this.mThumbnailHelper.prepare();
    }

    private void enableOnPrepared(boolean z2, final String str) {
        if (z2) {
            this.mAliPlayer.setOnPreparedListener(new IPlayer.OnPreparedListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayer.9
                @Override // com.aliyun.player.IPlayer.OnPreparedListener
                public void onPrepared() throws JSONException {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("method", "onPrepared");
                        jSONObject.put("playerId", FlutterAliPlayer.this.mPlayerId);
                        if (AliChannelPool.getInstance().containChannel(str)) {
                            AliChannelPool.getInstance().channelForKey(str).send(jSONObject.toString());
                        } else {
                            BasicMessageChannel<String> basicMessageChannel = new BasicMessageChannel<>(FlutterAliPlayer.this.binding.getBinaryMessenger(), str, StringCodec.INSTANCE);
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
        this.mAliPlayer.setOnPreparedListener(null);
        if (AliChannelPool.getInstance().containChannel(str)) {
            AliChannelPool.getInstance().removeChannel(str);
        }
    }

    private void enableOnRenderingStart(boolean z2, final String str) {
        if (z2) {
            this.mAliPlayer.setOnRenderingStartListener(new IPlayer.OnRenderingStartListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayer.10
                @Override // com.aliyun.player.IPlayer.OnRenderingStartListener
                public void onRenderingStart() throws JSONException {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("method", "onRenderingStart");
                        jSONObject.put("playerId", FlutterAliPlayer.this.mPlayerId);
                        if (AliChannelPool.getInstance().containChannel(str)) {
                            AliChannelPool.getInstance().channelForKey(str).send(jSONObject.toString());
                        } else {
                            BasicMessageChannel<String> basicMessageChannel = new BasicMessageChannel<>(FlutterAliPlayer.this.binding.getBinaryMessenger(), str, StringCodec.INSTANCE);
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
        this.mAliPlayer.setOnRenderingStartListener(null);
        if (AliChannelPool.getInstance().containChannel(str)) {
            AliChannelPool.getInstance().removeChannel(str);
        }
    }

    private void enableOnStateChanged(boolean z2, final String str) {
        if (z2) {
            this.mAliPlayer.setOnStateChangedListener(new IPlayer.OnStateChangedListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayer.8
                @Override // com.aliyun.player.IPlayer.OnStateChangedListener
                public void onStateChanged(int i2) throws JSONException {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("method", "onStateChanged");
                        jSONObject.put("newState", i2);
                        jSONObject.put("playerId", FlutterAliPlayer.this.mPlayerId);
                        if (AliChannelPool.getInstance().containChannel(str)) {
                            AliChannelPool.getInstance().channelForKey(str).send(jSONObject.toString());
                        } else {
                            BasicMessageChannel<String> basicMessageChannel = new BasicMessageChannel<>(FlutterAliPlayer.this.binding.getBinaryMessenger(), "aliPlayer_onStateChanged" + FlutterAliPlayer.this.mPlayerId, StringCodec.INSTANCE);
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
        this.mAliPlayer.setOnStateChangedListener(null);
        if (AliChannelPool.getInstance().containChannel(str)) {
            AliChannelPool.getInstance().removeChannel(str);
        }
    }

    private int getAlphaRenderMode(AliPlayer aliPlayer) {
        return aliPlayer != null ? aliPlayer.getAlphaRenderMode().getValue() : IPlayer.AlphaRenderMode.RENDER_MODE_ALPHA_NONE.getValue();
    }

    private CacheConfig getCacheConfig() {
        return new CacheConfig();
    }

    private String getCacheFilePath(AliPlayer aliPlayer, String str) {
        if (aliPlayer != null) {
            return aliPlayer.getCacheFilePath(str);
        }
        return null;
    }

    private String getCacheFilePathWithVid(AliPlayer aliPlayer, String str, String str2, String str3) {
        if (aliPlayer != null) {
            return aliPlayer.getCacheFilePath(str, str2, str3, 0);
        }
        return null;
    }

    private PlayerConfig getConfig(AliPlayer aliPlayer) {
        if (aliPlayer != null) {
            return aliPlayer.getConfig();
        }
        return null;
    }

    private TrackInfo getCurrentTrack(AliPlayer aliPlayer, int i2) {
        if (aliPlayer == null) {
            return null;
        }
        if (i2 == 0) {
            return aliPlayer.currentTrack(TrackInfo.Type.TYPE_VIDEO);
        }
        if (i2 == 1) {
            return aliPlayer.currentTrack(TrackInfo.Type.TYPE_AUDIO);
        }
        if (i2 == 2) {
            return aliPlayer.currentTrack(TrackInfo.Type.TYPE_SUBTITLE);
        }
        if (i2 != 3) {
            return null;
        }
        return aliPlayer.currentTrack(TrackInfo.Type.TYPE_VOD);
    }

    private MediaInfo getMediaInfo(AliPlayer aliPlayer) {
        if (aliPlayer != null) {
            return aliPlayer.getMediaInfo();
        }
        return null;
    }

    private int getMirrorMode(AliPlayer aliPlayer) {
        return aliPlayer != null ? aliPlayer.getMirrorMode().getValue() : IPlayer.MirrorMode.MIRROR_MODE_NONE.getValue();
    }

    private Object getOption(AliPlayer aliPlayer, IPlayer.Option option) {
        if (aliPlayer != null) {
            return aliPlayer.getOption(option);
        }
        return null;
    }

    private Map<String, Object> getPlayConfig(AliPlayer aliPlayer) {
        if (aliPlayer == null) {
            return null;
        }
        return (Map) loadGson().fromJson(loadGson().toJson(getConfig(aliPlayer)), new TypeToken<Map<String, Object>>() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayer.5
        }.getType());
    }

    private String getPlayerName(AliPlayer aliPlayer) {
        return aliPlayer == null ? "" : aliPlayer.getPlayerName();
    }

    private String getPropertyString(AliPlayer aliPlayer, String str) {
        if (aliPlayer != null) {
            return aliPlayer.getPropertyString(IPlayer.PropertyKey.valueOf(str));
        }
        return null;
    }

    private int getRotateMode(AliPlayer aliPlayer) {
        return aliPlayer != null ? aliPlayer.getRotateMode().getValue() : IPlayer.RotateMode.ROTATE_0.getValue();
    }

    private int getScaleMode(AliPlayer aliPlayer) {
        return aliPlayer != null ? aliPlayer.getScaleMode().getValue() : IPlayer.ScaleMode.SCALE_ASPECT_FIT.getValue();
    }

    private double getSpeed(AliPlayer aliPlayer) {
        if (aliPlayer != null) {
            return aliPlayer.getSpeed();
        }
        return 0.0d;
    }

    private MediaInfo getSubMediaInfo(AliPlayer aliPlayer) {
        if (aliPlayer != null) {
            return aliPlayer.getSubMediaInfo();
        }
        return null;
    }

    private double getVolume(AliPlayer aliPlayer) {
        if (aliPlayer != null) {
            return aliPlayer.getVolume();
        }
        return 1.0d;
    }

    private Boolean isAutoPlay(AliPlayer aliPlayer) {
        return aliPlayer != null ? Boolean.valueOf(aliPlayer.isAutoPlay()) : Boolean.FALSE;
    }

    private Boolean isLoop(AliPlayer aliPlayer) {
        return Boolean.valueOf(aliPlayer != null && aliPlayer.isLoop());
    }

    private Boolean isMuted(AliPlayer aliPlayer) {
        return aliPlayer != null ? Boolean.valueOf(aliPlayer.isMute()) : Boolean.FALSE;
    }

    private PlayerConfig mapCovertToPlayerConfig(Map<String, Object> map, PlayerConfig playerConfig) {
        if (map != null && playerConfig != null) {
            if (map.containsKey("mMaxAllowedAbrVideoPixelNumber") && map.get("mMaxAllowedAbrVideoPixelNumber") != null) {
                playerConfig.mMaxAllowedAbrVideoPixelNumber = ((Integer) map.get("mMaxAllowedAbrVideoPixelNumber")).intValue();
            }
            if (map.containsKey("maxDelayTime") && map.get("maxDelayTime") != null) {
                playerConfig.mMaxDelayTime = ((Integer) map.get("maxDelayTime")).intValue();
            }
            if (map.containsKey("highBufferDuration") && map.get("highBufferDuration") != null) {
                playerConfig.mHighBufferDuration = ((Integer) map.get("highBufferDuration")).intValue();
            }
            if (map.containsKey("startBufferDuration") && map.get("startBufferDuration") != null) {
                playerConfig.mStartBufferDuration = ((Integer) map.get("startBufferDuration")).intValue();
            }
            if (map.containsKey("maxBufferDuration") && map.get("maxBufferDuration") != null) {
                playerConfig.mMaxBufferDuration = ((Integer) map.get("maxBufferDuration")).intValue();
            }
            if (map.containsKey("networkTimeout") && map.get("networkTimeout") != null) {
                playerConfig.mNetworkTimeout = ((Integer) map.get("networkTimeout")).intValue();
            }
            if (map.containsKey("networkRetryCount") && map.get("networkRetryCount") != null) {
                playerConfig.mNetworkTimeout = ((Integer) map.get("networkRetryCount")).intValue();
            }
            if (map.containsKey("maxProbeSize") && map.get("maxProbeSize") != null) {
                playerConfig.mMaxProbeSize = ((Integer) map.get("maxProbeSize")).intValue();
            }
            if (map.containsKey(RequestParameters.SUBRESOURCE_REFERER) && map.get(RequestParameters.SUBRESOURCE_REFERER) != null) {
                playerConfig.mReferrer = (String) map.get(RequestParameters.SUBRESOURCE_REFERER);
            }
            if (map.containsKey("userAgent") && map.get("userAgent") != null) {
                playerConfig.mUserAgent = (String) map.get("userAgent");
            }
            if (map.containsKey("httpProxy") && map.get("httpProxy") != null) {
                playerConfig.mHttpProxy = (String) map.get("httpProxy");
            }
            if (map.containsKey("clearShowWhenStop") && map.get("clearShowWhenStop") != null) {
                playerConfig.mClearFrameWhenStop = ((Boolean) map.get("clearShowWhenStop")).booleanValue();
            }
            if (map.containsKey("enableSEI") && map.get("enableSEI") != null) {
                playerConfig.mEnableSEI = ((Boolean) map.get("enableSEI")).booleanValue();
            }
            if (map.containsKey("enableLocalCache") && map.get("enableLocalCache") != null) {
                playerConfig.mEnableLocalCache = ((Boolean) map.get("enableLocalCache")).booleanValue();
            }
            if (map.containsKey("liveStartIndex") && map.get("liveStartIndex") != null) {
                playerConfig.mLiveStartIndex = ((Integer) map.get("liveStartIndex")).intValue();
            }
            if (map.containsKey("disableAudio") && map.get("disableAudio") != null) {
                playerConfig.mDisableAudio = ((Boolean) map.get("disableAudio")).booleanValue();
            }
            if (map.containsKey("disableVideo") && map.get("disableVideo") != null) {
                playerConfig.mDisableVideo = ((Boolean) map.get("disableVideo")).booleanValue();
            }
            if (map.containsKey("positionTimerIntervalMs") && map.get("positionTimerIntervalMs") != null) {
                playerConfig.mPositionTimerIntervalMs = ((Integer) map.get("positionTimerIntervalMs")).intValue();
            }
            if (map.containsKey("mMAXBackwardDuration") && map.get("mMAXBackwardDuration") != null) {
                playerConfig.mMaxBackwardBufferDurationMs = ((Integer) map.get("mMAXBackwardDuration")).intValue();
            }
            if (map.containsKey("preferAudio") && map.get("preferAudio") != null) {
                playerConfig.mPreferAudio = ((Boolean) map.get("preferAudio")).booleanValue();
            }
            if (map.containsKey("enableHttpDns") && map.get("enableHttpDns") != null) {
                playerConfig.mEnableHttpDns = ((Integer) map.get("enableHttpDns")).intValue();
            }
            if (map.containsKey("enableHttp3") && map.get("enableHttp3") != null) {
                playerConfig.mEnableHttp3 = ((Boolean) map.get("enableHttp3")).booleanValue();
            }
            if (map.containsKey("enableStrictFlvHeader") && map.get("enableStrictFlvHeader") != null) {
                playerConfig.mEnableStrictFlvHeader = ((Boolean) map.get("enableStrictFlvHeader")).booleanValue();
            }
            if (map.containsKey("enableStrictAuthMode") && map.get("enableStrictAuthMode") != null) {
                playerConfig.mEnableStrictAuthMode = ((Boolean) map.get("enableStrictAuthMode")).booleanValue();
            }
            if (map.containsKey("enableProjection") && map.get("enableProjection") != null) {
                playerConfig.mEnableProjection = ((Boolean) map.get("enableProjection")).booleanValue();
            }
            if (map.containsKey("mSelectTrackBufferMode") && map.get("mSelectTrackBufferMode") != null) {
                playerConfig.mSelectTrackBufferMode = ((Integer) map.get("mSelectTrackBufferMode")).intValue();
            }
        }
        return playerConfig;
    }

    private void pause(AliPlayer aliPlayer) {
        if (aliPlayer != null) {
            aliPlayer.pause();
        }
    }

    private void prepare(AliPlayer aliPlayer) {
        if (aliPlayer != null) {
            aliPlayer.prepare();
        }
    }

    private void release(AliPlayer aliPlayer) {
        if (aliPlayer != null) {
            aliPlayer.release();
        }
    }

    private void releaseAsync(AliPlayer aliPlayer) {
        if (aliPlayer != null) {
            aliPlayer.releaseAsync();
        }
    }

    private void reload(AliPlayer aliPlayer) {
        if (aliPlayer != null) {
            aliPlayer.reload();
        }
    }

    private void requestBitmapAtPosition(int i2) {
        ThumbnailHelper thumbnailHelper = this.mThumbnailHelper;
        if (thumbnailHelper != null) {
            thumbnailHelper.requestBitmapAtPosition(i2);
        }
    }

    private void seekTo(AliPlayer aliPlayer, long j2, int i2) {
        if (aliPlayer != null) {
            IPlayer.SeekMode seekMode = IPlayer.SeekMode.Accurate;
            if (i2 != seekMode.getValue()) {
                seekMode = IPlayer.SeekMode.Inaccurate;
            }
            aliPlayer.seekTo(j2, seekMode);
        }
    }

    private void selectExtSubtitle(AliPlayer aliPlayer, int i2, boolean z2) {
        if (aliPlayer != null) {
            aliPlayer.selectExtSubtitle(i2, z2);
        }
    }

    private void selectTrack(AliPlayer aliPlayer, int i2, boolean z2) {
        if (aliPlayer != null) {
            aliPlayer.selectTrack(i2, z2);
        }
    }

    private void sendCustomEvent(AliPlayer aliPlayer, String str) {
        if (aliPlayer != null) {
            aliPlayer.sendCustomEvent(str);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x000c A[PHI: r1
      0x000c: PHI (r1v4 com.aliyun.player.IPlayer$AlphaRenderMode) = 
      (r1v0 com.aliyun.player.IPlayer$AlphaRenderMode)
      (r1v1 com.aliyun.player.IPlayer$AlphaRenderMode)
      (r1v2 com.aliyun.player.IPlayer$AlphaRenderMode)
      (r1v3 com.aliyun.player.IPlayer$AlphaRenderMode)
     binds: [B:4:0x000a, B:7:0x0014, B:10:0x001d, B:13:0x0026] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void setAlphaRenderMode(com.aliyun.player.AliPlayer r4, int r5) {
        /*
            r3 = this;
            if (r4 == 0) goto L2c
            com.aliyun.player.IPlayer$AlphaRenderMode r0 = com.aliyun.player.IPlayer.AlphaRenderMode.RENDER_MODE_ALPHA_NONE
            com.aliyun.player.IPlayer$AlphaRenderMode r1 = com.aliyun.player.IPlayer.AlphaRenderMode.RENDER_MODE_ALPHA_AT_LEFT
            int r2 = r1.getValue()
            if (r5 != r2) goto Le
        Lc:
            r0 = r1
            goto L29
        Le:
            com.aliyun.player.IPlayer$AlphaRenderMode r1 = com.aliyun.player.IPlayer.AlphaRenderMode.RENDER_MODE_ALPHA_AT_RIGHT
            int r2 = r1.getValue()
            if (r5 != r2) goto L17
            goto Lc
        L17:
            com.aliyun.player.IPlayer$AlphaRenderMode r1 = com.aliyun.player.IPlayer.AlphaRenderMode.RENDER_MODE_ALPHA_AT_TOP
            int r2 = r1.getValue()
            if (r5 != r2) goto L20
            goto Lc
        L20:
            com.aliyun.player.IPlayer$AlphaRenderMode r1 = com.aliyun.player.IPlayer.AlphaRenderMode.RENDER_MODE_ALPHA_AT_BOTTOM
            int r2 = r1.getValue()
            if (r5 != r2) goto L29
            goto Lc
        L29:
            r4.setAlphaRenderMode(r0)
        L2c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayer.setAlphaRenderMode(com.aliyun.player.AliPlayer, int):void");
    }

    private void setAutoPlay(AliPlayer aliPlayer, Boolean bool) {
        if (aliPlayer != null) {
            aliPlayer.setAutoPlay(bool.booleanValue());
        }
    }

    private void setCacheConfig(AliPlayer aliPlayer, CacheConfig cacheConfig) {
        if (aliPlayer != null) {
            aliPlayer.setCacheConfig(cacheConfig);
        }
    }

    private void setConfig(AliPlayer aliPlayer, PlayerConfig playerConfig) {
        if (aliPlayer != null) {
            aliPlayer.setConfig(playerConfig);
        }
    }

    private void setDataSource(AliPlayer aliPlayer, String str) {
        if (aliPlayer != null) {
            UrlSource urlSource = new UrlSource();
            urlSource.setUri(str);
            aliPlayer.setDataSource(urlSource);
        }
    }

    private void setDefaultBandWidth(AliPlayer aliPlayer, int i2) {
        if (aliPlayer != null) {
            aliPlayer.setDefaultBandWidth(i2);
        }
    }

    private void setEnableHardWareDecoder(AliPlayer aliPlayer, Boolean bool) {
        if (aliPlayer != null) {
            aliPlayer.enableHardwareDecoder(bool.booleanValue());
        }
    }

    private void setFastStart(AliPlayer aliPlayer, Boolean bool) {
        if (aliPlayer != null) {
            aliPlayer.setFastStart(bool.booleanValue());
        }
    }

    private void setFilterConfig(AliPlayer aliPlayer, FilterConfig filterConfig) {
        if (aliPlayer != null) {
            aliPlayer.setFilterConfig(filterConfig);
        }
    }

    private void setFilterInvalid(AliPlayer aliPlayer, String str, boolean z2) {
        if (aliPlayer != null) {
            aliPlayer.setFilterInvalid(str, z2);
        }
    }

    private void setIPResolveType(AliPlayer aliPlayer, IPlayer.IPResolveType iPResolveType) {
        if (aliPlayer != null) {
            aliPlayer.setIPResolveType(iPResolveType);
        }
    }

    private void setLoop(AliPlayer aliPlayer, Boolean bool) {
        if (aliPlayer != null) {
            aliPlayer.setLoop(bool.booleanValue());
        }
    }

    private void setMaxAccurateSeekDelta(AliPlayer aliPlayer, int i2) {
        if (aliPlayer != null) {
            aliPlayer.setMaxAccurateSeekDelta(i2);
        }
    }

    private void setMirrorMode(AliPlayer aliPlayer, int i2) {
        if (aliPlayer != null) {
            IPlayer.MirrorMode mirrorMode = IPlayer.MirrorMode.MIRROR_MODE_HORIZONTAL;
            if (i2 != mirrorMode.getValue()) {
                mirrorMode = IPlayer.MirrorMode.MIRROR_MODE_VERTICAL;
                if (i2 != mirrorMode.getValue()) {
                    mirrorMode = IPlayer.MirrorMode.MIRROR_MODE_NONE;
                }
            }
            aliPlayer.setMirrorMode(mirrorMode);
        }
    }

    private void setMuted(AliPlayer aliPlayer, Boolean bool) {
        if (aliPlayer != null) {
            aliPlayer.setMute(bool.booleanValue());
        }
    }

    private void setOption(AliPlayer aliPlayer, int i2, Object obj) {
        if (aliPlayer != null) {
            if (obj instanceof Integer) {
                aliPlayer.setOption(i2, ((Integer) obj).intValue());
            } else if (obj instanceof String) {
                aliPlayer.setOption(i2, (String) obj);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0013 A[PHI: r1
      0x0013: PHI (r1v3 com.aliyun.player.IPlayer$OutputAudioChannel) = (r1v1 com.aliyun.player.IPlayer$OutputAudioChannel), (r1v2 com.aliyun.player.IPlayer$OutputAudioChannel) binds: [B:8:0x0011, B:11:0x001b] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void setOutputAudioChannel(com.aliyun.player.AliPlayer r4, int r5) {
        /*
            r3 = this;
            com.aliyun.player.IPlayer$OutputAudioChannel r0 = com.aliyun.player.IPlayer.OutputAudioChannel.OUTPUT_AUDIO_CHANNEL_NONE
            if (r4 == 0) goto L21
            int r1 = r0.getValue()
            if (r5 != r1) goto Lb
            goto L1e
        Lb:
            com.aliyun.player.IPlayer$OutputAudioChannel r1 = com.aliyun.player.IPlayer.OutputAudioChannel.OUTPUT_AUDIO_CHANNEL_LEFT
            int r2 = r1.getValue()
            if (r5 != r2) goto L15
        L13:
            r0 = r1
            goto L1e
        L15:
            com.aliyun.player.IPlayer$OutputAudioChannel r1 = com.aliyun.player.IPlayer.OutputAudioChannel.OUTPUT_AUDIO_CHANNEL_RIGHT
            int r2 = r1.getValue()
            if (r5 != r2) goto L1e
            goto L13
        L1e:
            r4.setOutputAudioChannel(r0)
        L21:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayer.setOutputAudioChannel(com.aliyun.player.AliPlayer, int):void");
    }

    private void setPlayerName(AliPlayer aliPlayer, String str) {
        if (aliPlayer != null) {
            aliPlayer.setPreferPlayerName(str);
        }
    }

    private void setRenderFrameCallbackConfig(Boolean bool, Boolean bool2) {
        IPlayer.RenderFrameCallbackConfig renderFrameCallbackConfig = new IPlayer.RenderFrameCallbackConfig();
        renderFrameCallbackConfig.mAudioDataAddr = bool.booleanValue();
        renderFrameCallbackConfig.mVideoDataAddr = bool2.booleanValue();
        this.mAliPlayer.setRenderFrameCallbackConfig(renderFrameCallbackConfig);
    }

    private void setRotateMode(AliPlayer aliPlayer, int i2) {
        if (aliPlayer != null) {
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
            aliPlayer.setRotateMode(rotateMode);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0013 A[PHI: r1
      0x0013: PHI (r1v3 com.aliyun.player.IPlayer$ScaleMode) = (r1v1 com.aliyun.player.IPlayer$ScaleMode), (r1v2 com.aliyun.player.IPlayer$ScaleMode) binds: [B:7:0x0011, B:10:0x001b] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void setScaleMode(com.aliyun.player.AliPlayer r4, int r5) {
        /*
            r3 = this;
            if (r4 == 0) goto L21
            com.aliyun.player.IPlayer$ScaleMode r0 = com.aliyun.player.IPlayer.ScaleMode.SCALE_ASPECT_FIT
            int r1 = r0.getValue()
            if (r5 != r1) goto Lb
            goto L1e
        Lb:
            com.aliyun.player.IPlayer$ScaleMode r1 = com.aliyun.player.IPlayer.ScaleMode.SCALE_ASPECT_FILL
            int r2 = r1.getValue()
            if (r5 != r2) goto L15
        L13:
            r0 = r1
            goto L1e
        L15:
            com.aliyun.player.IPlayer$ScaleMode r1 = com.aliyun.player.IPlayer.ScaleMode.SCALE_TO_FILL
            int r2 = r1.getValue()
            if (r5 != r2) goto L1e
            goto L13
        L1e:
            r4.setScaleMode(r0)
        L21:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayer.setScaleMode(com.aliyun.player.AliPlayer, int):void");
    }

    private void setSpeed(AliPlayer aliPlayer, double d2) {
        if (aliPlayer != null) {
            aliPlayer.setSpeed((float) d2);
        }
    }

    private void setStartTime(AliPlayer aliPlayer, long j2, long j3) {
        IPlayer.SeekMode seekMode = IPlayer.SeekMode.Accurate;
        if (j3 != seekMode.getValue()) {
            seekMode = IPlayer.SeekMode.Inaccurate;
        }
        if (aliPlayer != null) {
            aliPlayer.setStartTime(j2, seekMode);
        }
    }

    private void setStreamDelayTime(AliPlayer aliPlayer, int i2, int i3) {
        if (aliPlayer != null) {
            aliPlayer.setStreamDelayTime(i2, i3);
        }
    }

    private void setVideoBackgroundColor(AliPlayer aliPlayer, long j2) {
        if (aliPlayer != null) {
            aliPlayer.setVideoBackgroundColor((int) j2);
        }
    }

    private void setVolume(AliPlayer aliPlayer, double d2) {
        if (aliPlayer != null) {
            aliPlayer.setVolume((float) d2);
        }
    }

    private void snapshot(AliPlayer aliPlayer) {
        if (aliPlayer != null) {
            aliPlayer.snapshot();
        }
    }

    private void start(AliPlayer aliPlayer) {
        if (aliPlayer != null) {
            aliPlayer.start();
        }
    }

    private void stop(AliPlayer aliPlayer) {
        if (aliPlayer != null) {
            aliPlayer.stop();
        }
    }

    private void updateFilterConfig(AliPlayer aliPlayer, String str, FilterConfig.FilterOptions filterOptions) {
        if (aliPlayer != null) {
            aliPlayer.updateFilterConfig(str, filterOptions);
        }
    }

    private void updateLiveStsInfo(AliPlayer aliPlayer, StsInfo stsInfo) {
        if (aliPlayer != null) {
            aliPlayer.updateStsInfo(stsInfo);
        }
    }

    @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterPlayerBase
    public IPlayer getAliPlayer() {
        return this.mAliPlayer;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:407:0x0797  */
    /* JADX WARN: Removed duplicated region for block: B:4:0x003e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onMethodCall(io.flutter.plugin.common.MethodCall r24, io.flutter.plugin.common.MethodChannel.Result r25) {
        /*
            Method dump skipped, instructions count: 5190
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayer.onMethodCall(io.flutter.plugin.common.MethodCall, io.flutter.plugin.common.MethodChannel$Result):void");
    }

    @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterPlayerBase
    public void setOnFlutterListener(FlutterAliPlayerListener flutterAliPlayerListener) {
        this.mFlutterAliPlayerListener = flutterAliPlayerListener;
    }

    private void setDataSource(AliPlayer aliPlayer, VidSts vidSts) {
        if (aliPlayer != null) {
            aliPlayer.setDataSource(vidSts);
        }
    }

    private void setDataSource(AliPlayer aliPlayer, VidAuth vidAuth) {
        if (aliPlayer != null) {
            aliPlayer.setDataSource(vidAuth);
        }
    }

    private void setDataSource(AliPlayer aliPlayer, VidMps vidMps) {
        if (aliPlayer != null) {
            aliPlayer.setDataSource(vidMps);
        }
    }

    private void setDataSource(AliPlayer aliPlayer, LiveSts liveSts) {
        if (aliPlayer != null) {
            aliPlayer.setDataSource(liveSts);
        }
    }
}
