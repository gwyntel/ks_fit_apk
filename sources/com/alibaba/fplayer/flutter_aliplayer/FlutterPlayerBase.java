package com.alibaba.fplayer.flutter_aliplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import com.aliyun.alink.business.devicecenter.config.genie.smartconfig.constants.DeviceCommonConstants;
import com.aliyun.alink.business.devicecenter.config.genie.smartconfig.constants.WifiProvisionUtConst;
import com.aliyun.player.AliLiveShiftPlayer;
import com.aliyun.player.IPlayer;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.bean.InfoBean;
import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.nativeclass.TrackInfo;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.google.gson.Gson;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/* loaded from: classes2.dex */
public abstract class FlutterPlayerBase {
    protected Context mContext;
    protected FlutterAliPlayerListener mFlutterAliPlayerListener;
    protected String mPlayerId;
    protected String mSnapShotPath;
    private ExecutorService executorService = new ScheduledThreadPoolExecutor(10);
    private Handler mHandler = new Handler(Looper.getMainLooper());

    /* renamed from: com.alibaba.fplayer.flutter_aliplayer.FlutterPlayerBase$5, reason: invalid class name */
    class AnonymousClass5 implements IPlayer.OnSnapShotListener {
        AnonymousClass5() {
        }

        @Override // com.aliyun.player.IPlayer.OnSnapShotListener
        public void onSnapShot(final Bitmap bitmap, int i2, int i3) {
            final HashMap map = new HashMap();
            map.put("method", "onSnapShot");
            map.put("snapShotPath", FlutterPlayerBase.this.mSnapShotPath);
            map.put("playerId", FlutterPlayerBase.this.mPlayerId);
            FlutterPlayerBase.this.executorService.execute(new Runnable() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterPlayerBase.5.1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.lang.Runnable
                public void run() throws Throwable {
                    if (bitmap != null) {
                        File file = new File(FlutterPlayerBase.this.mSnapShotPath);
                        if (file.exists()) {
                            file.delete();
                        }
                        FileOutputStream fileOutputStream = null;
                        fileOutputStream = null;
                        fileOutputStream = null;
                        fileOutputStream = null;
                        try {
                            try {
                                try {
                                    FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                                    try {
                                        Bitmap bitmap2 = bitmap;
                                        Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.PNG;
                                        bitmap2.compress(compressFormat, 100, fileOutputStream2);
                                        fileOutputStream2.flush();
                                        fileOutputStream2.close();
                                        fileOutputStream2.close();
                                        fileOutputStream = compressFormat;
                                    } catch (FileNotFoundException e2) {
                                        e = e2;
                                        fileOutputStream = fileOutputStream2;
                                        e.printStackTrace();
                                        if (fileOutputStream != null) {
                                            fileOutputStream.close();
                                            fileOutputStream = fileOutputStream;
                                        }
                                        FlutterPlayerBase.this.mHandler.post(new Runnable() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterPlayerBase.5.1.1
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                                FlutterAliPlayerListener flutterAliPlayerListener = FlutterPlayerBase.this.mFlutterAliPlayerListener;
                                                if (flutterAliPlayerListener != null) {
                                                    flutterAliPlayerListener.onSnapShot(map);
                                                }
                                            }
                                        });
                                    } catch (IOException e3) {
                                        e = e3;
                                        fileOutputStream = fileOutputStream2;
                                        e.printStackTrace();
                                        if (fileOutputStream != null) {
                                            fileOutputStream.close();
                                            fileOutputStream = fileOutputStream;
                                        }
                                        FlutterPlayerBase.this.mHandler.post(new Runnable() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterPlayerBase.5.1.1
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                                FlutterAliPlayerListener flutterAliPlayerListener = FlutterPlayerBase.this.mFlutterAliPlayerListener;
                                                if (flutterAliPlayerListener != null) {
                                                    flutterAliPlayerListener.onSnapShot(map);
                                                }
                                            }
                                        });
                                    } catch (Throwable th) {
                                        th = th;
                                        fileOutputStream = fileOutputStream2;
                                        if (fileOutputStream != null) {
                                            try {
                                                fileOutputStream.close();
                                            } catch (IOException e4) {
                                                e4.printStackTrace();
                                            }
                                        }
                                        throw th;
                                    }
                                } catch (FileNotFoundException e5) {
                                    e = e5;
                                } catch (IOException e6) {
                                    e = e6;
                                }
                            } catch (IOException e7) {
                                e7.printStackTrace();
                            }
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    }
                    FlutterPlayerBase.this.mHandler.post(new Runnable() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterPlayerBase.5.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                            FlutterAliPlayerListener flutterAliPlayerListener = FlutterPlayerBase.this.mFlutterAliPlayerListener;
                            if (flutterAliPlayerListener != null) {
                                flutterAliPlayerListener.onSnapShot(map);
                            }
                        }
                    });
                }
            });
        }
    }

    public abstract IPlayer getAliPlayer();

    public void initListener(IPlayer iPlayer) {
        iPlayer.setOnChooseTrackIndexListener(new IPlayer.OnChooseTrackIndexListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterPlayerBase.1
            @Override // com.aliyun.player.IPlayer.OnChooseTrackIndexListener
            public int onChooseTrackIndex(TrackInfo[] trackInfoArr) {
                HashMap map = new HashMap();
                map.put("method", "onChooseTrackIndex");
                map.put("trackInfos", trackInfoArr);
                map.put("playerId", FlutterPlayerBase.this.mPlayerId);
                FlutterAliPlayerListener flutterAliPlayerListener = FlutterPlayerBase.this.mFlutterAliPlayerListener;
                if (flutterAliPlayerListener != null) {
                    return flutterAliPlayerListener.onChooseTrackIndex(map);
                }
                return 0;
            }
        });
        iPlayer.setOnReportEventListener(new IPlayer.OnReportEventListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterPlayerBase.2
            @Override // com.aliyun.player.IPlayer.OnReportEventListener
            public void onEventParam(Map<String, String> map) {
                HashMap map2 = new HashMap();
                map2.put("method", "onEventReportParams");
                map2.put("params", map);
                map2.put("playerId", FlutterPlayerBase.this.mPlayerId);
                FlutterAliPlayerListener flutterAliPlayerListener = FlutterPlayerBase.this.mFlutterAliPlayerListener;
                if (flutterAliPlayerListener != null) {
                    flutterAliPlayerListener.onReportEventListener(map2);
                }
            }
        });
        iPlayer.setOnVideoSizeChangedListener(new IPlayer.OnVideoSizeChangedListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterPlayerBase.3
            @Override // com.aliyun.player.IPlayer.OnVideoSizeChangedListener
            public void onVideoSizeChanged(int i2, int i3) {
                HashMap map = new HashMap();
                map.put("method", "onVideoSizeChanged");
                map.put(ViewHierarchyConstants.DIMENSION_WIDTH_KEY, Integer.valueOf(i2));
                map.put(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY, Integer.valueOf(i3));
                map.put("playerId", FlutterPlayerBase.this.mPlayerId);
                FlutterAliPlayerListener flutterAliPlayerListener = FlutterPlayerBase.this.mFlutterAliPlayerListener;
                if (flutterAliPlayerListener != null) {
                    flutterAliPlayerListener.onVideoSizeChanged(map);
                }
            }
        });
        iPlayer.setOnVideoRenderedListener(new IPlayer.OnVideoRenderedListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterPlayerBase.4
            @Override // com.aliyun.player.IPlayer.OnVideoRenderedListener
            public void onVideoRendered(long j2, long j3) {
                HashMap map = new HashMap();
                map.put("method", "onVideoRendered");
                map.put("playerId", FlutterPlayerBase.this.mPlayerId);
                map.put("timeMs", Long.valueOf(j2));
                map.put("pts", Long.valueOf(j3));
                FlutterAliPlayerListener flutterAliPlayerListener = FlutterPlayerBase.this.mFlutterAliPlayerListener;
                if (flutterAliPlayerListener != null) {
                    flutterAliPlayerListener.onVideoRendered(map);
                }
            }
        });
        iPlayer.setOnSnapShotListener(new AnonymousClass5());
        iPlayer.setOnTrackChangedListener(new IPlayer.OnTrackChangedListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterPlayerBase.6
            @Override // com.aliyun.player.IPlayer.OnTrackChangedListener
            public void onChangedFail(TrackInfo trackInfo, ErrorInfo errorInfo) {
                HashMap map = new HashMap();
                map.put("method", "onChangedFail");
                map.put("playerId", FlutterPlayerBase.this.mPlayerId);
                FlutterAliPlayerListener flutterAliPlayerListener = FlutterPlayerBase.this.mFlutterAliPlayerListener;
                if (flutterAliPlayerListener != null) {
                    flutterAliPlayerListener.onTrackChangedFail(map);
                }
            }

            @Override // com.aliyun.player.IPlayer.OnTrackChangedListener
            public void onChangedSuccess(TrackInfo trackInfo) {
                HashMap map = new HashMap();
                map.put("method", "onTrackChanged");
                map.put("playerId", FlutterPlayerBase.this.mPlayerId);
                HashMap map2 = new HashMap();
                map2.put("vodFormat", trackInfo.getVodFormat());
                map2.put("videoHeight", Integer.valueOf(trackInfo.getVideoHeight()));
                map2.put("videoWidth", Integer.valueOf(trackInfo.getVideoHeight()));
                map2.put("subtitleLanguage", trackInfo.getSubtitleLang());
                map2.put("trackBitrate", Integer.valueOf(trackInfo.getVideoBitrate()));
                map2.put("vodFileSize", Long.valueOf(trackInfo.getVodFileSize()));
                map2.put("trackIndex", Integer.valueOf(trackInfo.getIndex()));
                map2.put("trackDefinition", trackInfo.getVodDefinition());
                map2.put("audioSampleFormat", Integer.valueOf(trackInfo.getAudioSampleFormat()));
                map2.put("audioLanguage", trackInfo.getAudioLang());
                map2.put("vodPlayUrl", trackInfo.getVodPlayUrl());
                map2.put("trackType", Integer.valueOf(trackInfo.getType().ordinal()));
                map2.put("audioSamplerate", Integer.valueOf(trackInfo.getAudioSampleRate()));
                map2.put("audioChannels", Integer.valueOf(trackInfo.getAudioChannels()));
                map.put(XiaomiOAuthConstants.EXTRA_INFO, map2);
                FlutterAliPlayerListener flutterAliPlayerListener = FlutterPlayerBase.this.mFlutterAliPlayerListener;
                if (flutterAliPlayerListener != null) {
                    flutterAliPlayerListener.onTrackChangedSuccess(map);
                }
            }
        });
        iPlayer.setOnSeekCompleteListener(new IPlayer.OnSeekCompleteListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterPlayerBase.7
            @Override // com.aliyun.player.IPlayer.OnSeekCompleteListener
            public void onSeekComplete() {
                HashMap map = new HashMap();
                map.put("method", "onSeekComplete");
                map.put("playerId", FlutterPlayerBase.this.mPlayerId);
                FlutterAliPlayerListener flutterAliPlayerListener = FlutterPlayerBase.this.mFlutterAliPlayerListener;
                if (flutterAliPlayerListener != null) {
                    flutterAliPlayerListener.onSeekComplete(map);
                }
            }
        });
        iPlayer.setOnSeiDataListener(new IPlayer.OnSeiDataListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterPlayerBase.8
            @Override // com.aliyun.player.IPlayer.OnSeiDataListener
            public void onSeiData(int i2, byte[] bArr, byte[] bArr2) {
                HashMap map = new HashMap();
                map.put("method", "onSeiData");
                map.put("type", Integer.valueOf(i2));
                map.put(DeviceCommonConstants.KEY_DEVICE_ID, bArr);
                map.put("data", bArr2);
                map.put("playerId", FlutterPlayerBase.this.mPlayerId);
                FlutterAliPlayerListener flutterAliPlayerListener = FlutterPlayerBase.this.mFlutterAliPlayerListener;
                if (flutterAliPlayerListener != null) {
                    flutterAliPlayerListener.onSeiData(map);
                }
            }
        });
        iPlayer.setOnLoadingStatusListener(new IPlayer.OnLoadingStatusListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterPlayerBase.9
            @Override // com.aliyun.player.IPlayer.OnLoadingStatusListener
            public void onLoadingBegin() {
                HashMap map = new HashMap();
                map.put("method", "onLoadingBegin");
                map.put("playerId", FlutterPlayerBase.this.mPlayerId);
                FlutterAliPlayerListener flutterAliPlayerListener = FlutterPlayerBase.this.mFlutterAliPlayerListener;
                if (flutterAliPlayerListener != null) {
                    flutterAliPlayerListener.onLoadingBegin(map);
                }
            }

            @Override // com.aliyun.player.IPlayer.OnLoadingStatusListener
            public void onLoadingEnd() {
                HashMap map = new HashMap();
                map.put("method", "onLoadingEnd");
                map.put("playerId", FlutterPlayerBase.this.mPlayerId);
                FlutterAliPlayerListener flutterAliPlayerListener = FlutterPlayerBase.this.mFlutterAliPlayerListener;
                if (flutterAliPlayerListener != null) {
                    flutterAliPlayerListener.onLoadingEnd(map);
                }
            }

            @Override // com.aliyun.player.IPlayer.OnLoadingStatusListener
            public void onLoadingProgress(int i2, float f2) {
                HashMap map = new HashMap();
                map.put("method", "onLoadingProgress");
                map.put("percent", Integer.valueOf(i2));
                map.put("netSpeed", Float.valueOf(f2));
                map.put("playerId", FlutterPlayerBase.this.mPlayerId);
                FlutterAliPlayerListener flutterAliPlayerListener = FlutterPlayerBase.this.mFlutterAliPlayerListener;
                if (flutterAliPlayerListener != null) {
                    flutterAliPlayerListener.onLoadingProgress(map);
                }
            }
        });
        iPlayer.setOnSubtitleDisplayListener(new IPlayer.OnSubtitleDisplayListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterPlayerBase.10
            @Override // com.aliyun.player.IPlayer.OnSubtitleDisplayListener
            public void onSubtitleExtAdded(int i2, String str) {
                HashMap map = new HashMap();
                map.put("method", "onSubtitleExtAdded");
                map.put("trackIndex", Integer.valueOf(i2));
                map.put("url", str);
                map.put("playerId", FlutterPlayerBase.this.mPlayerId);
                FlutterAliPlayerListener flutterAliPlayerListener = FlutterPlayerBase.this.mFlutterAliPlayerListener;
                if (flutterAliPlayerListener != null) {
                    flutterAliPlayerListener.onSubtitleExtAdded(map);
                }
            }

            @Override // com.aliyun.player.IPlayer.OnSubtitleDisplayListener
            public void onSubtitleHeader(int i2, String str) {
                HashMap map = new HashMap();
                map.put("method", "onSubtitleHeader");
                map.put("trackIndex", Integer.valueOf(i2));
                map.put("header", str);
                map.put("playerId", FlutterPlayerBase.this.mPlayerId);
                FlutterAliPlayerListener flutterAliPlayerListener = FlutterPlayerBase.this.mFlutterAliPlayerListener;
                if (flutterAliPlayerListener != null) {
                    flutterAliPlayerListener.onSubtitleHeader(map);
                }
            }

            @Override // com.aliyun.player.IPlayer.OnSubtitleDisplayListener
            public void onSubtitleHide(int i2, long j2) {
                HashMap map = new HashMap();
                map.put("method", "onSubtitleHide");
                map.put("trackIndex", Integer.valueOf(i2));
                map.put("subtitleID", Long.valueOf(j2));
                map.put("playerId", FlutterPlayerBase.this.mPlayerId);
                FlutterAliPlayerListener flutterAliPlayerListener = FlutterPlayerBase.this.mFlutterAliPlayerListener;
                if (flutterAliPlayerListener != null) {
                    flutterAliPlayerListener.onSubtitleHide(map);
                }
            }

            @Override // com.aliyun.player.IPlayer.OnSubtitleDisplayListener
            public void onSubtitleShow(int i2, long j2, String str) {
                HashMap map = new HashMap();
                map.put("method", "onSubtitleShow");
                map.put("trackIndex", Integer.valueOf(i2));
                map.put("subtitleID", Long.valueOf(j2));
                map.put(MessengerShareContentUtility.SUBTITLE, str);
                map.put("playerId", FlutterPlayerBase.this.mPlayerId);
                FlutterAliPlayerListener flutterAliPlayerListener = FlutterPlayerBase.this.mFlutterAliPlayerListener;
                if (flutterAliPlayerListener != null) {
                    flutterAliPlayerListener.onSubtitleShow(map);
                }
            }
        });
        iPlayer.setOnInfoListener(new IPlayer.OnInfoListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterPlayerBase.11
            @Override // com.aliyun.player.IPlayer.OnInfoListener
            public void onInfo(InfoBean infoBean) {
                HashMap map = new HashMap();
                map.put("method", "onInfo");
                map.put("infoCode", Integer.valueOf(infoBean.getCode().getValue()));
                map.put("extraValue", Long.valueOf(infoBean.getExtraValue()));
                map.put("extraMsg", infoBean.getExtraMsg());
                map.put("playerId", FlutterPlayerBase.this.mPlayerId);
                FlutterAliPlayerListener flutterAliPlayerListener = FlutterPlayerBase.this.mFlutterAliPlayerListener;
                if (flutterAliPlayerListener != null) {
                    flutterAliPlayerListener.onInfo(map);
                }
            }
        });
        iPlayer.setOnErrorListener(new IPlayer.OnErrorListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterPlayerBase.12
            @Override // com.aliyun.player.IPlayer.OnErrorListener
            public void onError(ErrorInfo errorInfo) {
                HashMap map = new HashMap();
                map.put("method", "onError");
                map.put("errorCode", Integer.valueOf(errorInfo.getCode().getValue()));
                map.put("errorExtra", errorInfo.getExtra());
                map.put(WifiProvisionUtConst.KEY_ERROR_MSG, errorInfo.getMsg());
                map.put("playerId", FlutterPlayerBase.this.mPlayerId);
                FlutterAliPlayerListener flutterAliPlayerListener = FlutterPlayerBase.this.mFlutterAliPlayerListener;
                if (flutterAliPlayerListener != null) {
                    flutterAliPlayerListener.onError(map);
                }
            }
        });
        iPlayer.setOnTrackReadyListener(new IPlayer.OnTrackReadyListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterPlayerBase.13
            @Override // com.aliyun.player.IPlayer.OnTrackReadyListener
            public void onTrackReady(MediaInfo mediaInfo) {
                HashMap map = new HashMap();
                map.put("method", "onTrackReady");
                map.put("playerId", FlutterPlayerBase.this.mPlayerId);
                FlutterAliPlayerListener flutterAliPlayerListener = FlutterPlayerBase.this.mFlutterAliPlayerListener;
                if (flutterAliPlayerListener != null) {
                    flutterAliPlayerListener.onTrackReady(map);
                }
            }
        });
        iPlayer.setOnSubTrackReadyListener(new IPlayer.OnSubTrackReadyListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterPlayerBase.14
            @Override // com.aliyun.player.IPlayer.OnSubTrackReadyListener
            public void onSubTrackReady(MediaInfo mediaInfo) {
                HashMap map = new HashMap();
                map.put("method", "onSubTrackReady");
                map.put("playerId", FlutterPlayerBase.this.mPlayerId);
                FlutterAliPlayerListener flutterAliPlayerListener = FlutterPlayerBase.this.mFlutterAliPlayerListener;
                if (flutterAliPlayerListener != null) {
                    flutterAliPlayerListener.onSubTrackReady(map);
                }
            }
        });
        iPlayer.setOnCompletionListener(new IPlayer.OnCompletionListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterPlayerBase.15
            @Override // com.aliyun.player.IPlayer.OnCompletionListener
            public void onCompletion() {
                HashMap map = new HashMap();
                map.put("method", "onCompletion");
                map.put("playerId", FlutterPlayerBase.this.mPlayerId);
                FlutterAliPlayerListener flutterAliPlayerListener = FlutterPlayerBase.this.mFlutterAliPlayerListener;
                if (flutterAliPlayerListener != null) {
                    flutterAliPlayerListener.onCompletion(map);
                }
            }
        });
        if (iPlayer instanceof AliLiveShiftPlayer) {
            AliLiveShiftPlayer aliLiveShiftPlayer = (AliLiveShiftPlayer) iPlayer;
            aliLiveShiftPlayer.setOnTimeShiftUpdaterListener(new AliLiveShiftPlayer.OnTimeShiftUpdaterListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterPlayerBase.16
                @Override // com.aliyun.player.AliLiveShiftPlayer.OnTimeShiftUpdaterListener
                public void onUpdater(long j2, long j3, long j4) {
                    HashMap map = new HashMap();
                    map.put("method", "onUpdater");
                    map.put("currentTime", Long.valueOf(j2));
                    map.put("shiftStartTime", Long.valueOf(j3));
                    map.put("shiftEndTime", Long.valueOf(j4));
                    map.put("playerId", FlutterPlayerBase.this.mPlayerId);
                    FlutterAliPlayerListener flutterAliPlayerListener = FlutterPlayerBase.this.mFlutterAliPlayerListener;
                    if (flutterAliPlayerListener != null) {
                        flutterAliPlayerListener.onTimeShiftUpdater(map);
                    }
                }
            });
            aliLiveShiftPlayer.setOnSeekLiveCompletionListener(new AliLiveShiftPlayer.OnSeekLiveCompletionListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterPlayerBase.17
                @Override // com.aliyun.player.AliLiveShiftPlayer.OnSeekLiveCompletionListener
                public void onSeekLiveCompletion(long j2) {
                    HashMap map = new HashMap();
                    map.put("method", "onSeekLiveCompletion");
                    map.put("playTime", Long.valueOf(j2));
                    map.put("playerId", FlutterPlayerBase.this.mPlayerId);
                    FlutterAliPlayerListener flutterAliPlayerListener = FlutterPlayerBase.this.mFlutterAliPlayerListener;
                    if (flutterAliPlayerListener != null) {
                        flutterAliPlayerListener.onSeekLiveCompletion(map);
                    }
                }
            });
        }
    }

    public Gson loadGson() {
        return GsonLoader.getInstance();
    }

    public void setOnFlutterListener(FlutterAliPlayerListener flutterAliPlayerListener) {
        this.mFlutterAliPlayerListener = flutterAliPlayerListener;
    }
}
