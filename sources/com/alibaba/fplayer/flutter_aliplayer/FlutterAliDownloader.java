package com.alibaba.fplayer.flutter_aliplayer;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.aliyun.alink.business.devicecenter.config.genie.smartconfig.constants.WifiProvisionUtConst;
import com.aliyun.downloader.AliDownloaderFactory;
import com.aliyun.downloader.AliMediaDownloader;
import com.aliyun.downloader.DownloaderConfig;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.source.VidAuth;
import com.aliyun.player.source.VidSts;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class FlutterAliDownloader implements FlutterPlugin, MethodChannel.MethodCallHandler, EventChannel.StreamHandler {
    private static final String SEPARA_SYMBOLS = "_";
    private Map<String, AliMediaDownloader> mAliMediaDownloadMap = new HashMap();
    private Context mContext;
    private EventChannel mEventChannel;
    private EventChannel.EventSink mEventSink;
    private MethodChannel mMethodChannel;
    private String mSavePath;

    public FlutterAliDownloader(Context context, FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.mContext = context;
        this.mMethodChannel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "plugins.flutter_alidownload");
        EventChannel eventChannel = new EventChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "plugins.flutter_alidownload_event");
        this.mEventChannel = eventChannel;
        eventChannel.setStreamHandler(this);
        this.mMethodChannel.setMethodCallHandler(this);
    }

    private void createMediaDownloader() {
        AliDownloaderFactory.create(this.mContext);
    }

    private void delete(AliMediaDownloader aliMediaDownloader) {
        aliMediaDownloader.deleteFile();
    }

    private String getFilePath(AliMediaDownloader aliMediaDownloader) {
        return aliMediaDownloader.getFilePath();
    }

    private void prepare(VidAuth vidAuth, final int i2, final MethodChannel.Result result) {
        final AliMediaDownloader aliMediaDownloaderCreate = AliDownloaderFactory.create(this.mContext);
        this.mAliMediaDownloadMap.put(vidAuth.getVid(), aliMediaDownloaderCreate);
        aliMediaDownloaderCreate.setOnPreparedListener(new AliMediaDownloader.OnPreparedListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliDownloader.1
            @Override // com.aliyun.downloader.AliMediaDownloader.OnPreparedListener
            public void onPrepared(MediaInfo mediaInfo) {
                String json = new Gson().toJson(mediaInfo);
                aliMediaDownloaderCreate.selectItem(i2);
                FlutterAliDownloader.this.mAliMediaDownloadMap.put(mediaInfo.getVideoId() + "_" + i2, aliMediaDownloaderCreate);
                result.success(json);
            }
        });
        aliMediaDownloaderCreate.setOnErrorListener(new AliMediaDownloader.OnErrorListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliDownloader.2
            @Override // com.aliyun.downloader.AliMediaDownloader.OnErrorListener
            public void onError(ErrorInfo errorInfo) {
                result.error(errorInfo.getCode().toString(), errorInfo.getMsg(), errorInfo.getExtra());
            }
        });
        aliMediaDownloaderCreate.prepare(vidAuth);
    }

    private void release(AliMediaDownloader aliMediaDownloader) {
        aliMediaDownloader.release();
    }

    private void selectItem(AliMediaDownloader aliMediaDownloader, int i2) {
        aliMediaDownloader.selectItem(i2);
    }

    private void setDownloaderConfig(AliMediaDownloader aliMediaDownloader, DownloaderConfig downloaderConfig) {
        aliMediaDownloader.setDownloaderConfig(downloaderConfig);
    }

    private void setSaveDir(AliMediaDownloader aliMediaDownloader, String str) {
        aliMediaDownloader.setSaveDir(str);
    }

    private void start(final AliMediaDownloader aliMediaDownloader, final Map<String, Object> map) {
        aliMediaDownloader.setOnErrorListener(new AliMediaDownloader.OnErrorListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliDownloader.9
            @Override // com.aliyun.downloader.AliMediaDownloader.OnErrorListener
            public void onError(ErrorInfo errorInfo) {
                map.put("method", "download_error");
                map.put("errorCode", errorInfo.getCode() + "");
                map.put(WifiProvisionUtConst.KEY_ERROR_MSG, errorInfo.getMsg());
                FlutterAliDownloader.this.mEventSink.success(map);
            }
        });
        aliMediaDownloader.setOnProgressListener(new AliMediaDownloader.OnProgressListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliDownloader.10
            @Override // com.aliyun.downloader.AliMediaDownloader.OnProgressListener
            public void onDownloadingProgress(int i2) {
                map.put("method", "download_progress");
                map.put("download_progress", i2 + "");
                FlutterAliDownloader.this.mEventSink.success(map);
            }

            @Override // com.aliyun.downloader.AliMediaDownloader.OnProgressListener
            public void onProcessingProgress(int i2) {
                map.put("method", "download_process");
                map.put("download_process", i2 + "");
                FlutterAliDownloader.this.mEventSink.success(map);
            }
        });
        aliMediaDownloader.setOnCompletionListener(new AliMediaDownloader.OnCompletionListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliDownloader.11
            @Override // com.aliyun.downloader.AliMediaDownloader.OnCompletionListener
            public void onCompletion() {
                map.put("method", "download_completion");
                map.put("savePath", aliMediaDownloader.getFilePath());
                FlutterAliDownloader.this.mEventSink.success(map);
            }
        });
        aliMediaDownloader.start();
    }

    private void stop(AliMediaDownloader aliMediaDownloader) {
        aliMediaDownloader.stop();
    }

    private void updateSource(AliMediaDownloader aliMediaDownloader, VidSts vidSts) {
        aliMediaDownloader.updateSource(vidSts);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onCancel(Object obj) {
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onListen(Object obj, EventChannel.EventSink eventSink) {
        this.mEventSink = eventSink;
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
        AliMediaDownloader aliMediaDownloader;
        String str = methodCall.method;
        str.hashCode();
        switch (str) {
            case "selectItem":
                Map map = (Map) methodCall.arguments;
                String str2 = (String) map.get("vid");
                Integer num = (Integer) map.get(FirebaseAnalytics.Param.INDEX);
                if (this.mAliMediaDownloadMap.containsKey(str2) && (aliMediaDownloader = this.mAliMediaDownloadMap.get(str2)) != null) {
                    this.mAliMediaDownloadMap.remove(str2);
                    this.mAliMediaDownloadMap.put(str2 + "_" + num, aliMediaDownloader);
                    selectItem(aliMediaDownloader, num.intValue());
                }
                result.success(null);
                break;
            case "create":
                createMediaDownloader();
                break;
            case "delete":
                Map map2 = (Map) methodCall.arguments;
                String str3 = (String) map2.get("vid");
                Integer num2 = (Integer) map2.get(FirebaseAnalytics.Param.INDEX);
                AliMediaDownloader aliMediaDownloader2 = this.mAliMediaDownloadMap.get(str3 + "_" + num2);
                if (aliMediaDownloader2 != null) {
                    delete(aliMediaDownloader2);
                }
                result.success(null);
                break;
            case "updateSource":
                Map map3 = (Map) methodCall.arguments;
                Integer num3 = (Integer) map3.get(FirebaseAnalytics.Param.INDEX);
                String str4 = (String) map3.get("type");
                String str5 = (String) map3.get("vid");
                String str6 = (String) map3.get("region");
                AliMediaDownloader aliMediaDownloaderRemove = this.mAliMediaDownloadMap.remove(str5 + "_" + num3);
                if (aliMediaDownloaderRemove != null) {
                    if (str4 != null && str4.equals("download_sts")) {
                        VidSts vidSts = new VidSts();
                        vidSts.setVid(str5);
                        vidSts.setRegion(str6);
                        vidSts.setAccessKeyId((String) map3.get("accessKeyId"));
                        vidSts.setAccessKeySecret((String) map3.get("accessKeySecret"));
                        vidSts.setSecurityToken((String) map3.get("securityToken"));
                        updateSource(aliMediaDownloaderRemove, vidSts);
                    } else if (str4 != null && str4.equals("download_auth")) {
                        VidAuth vidAuth = new VidAuth();
                        vidAuth.setVid(str5);
                        vidAuth.setRegion(str6);
                        vidAuth.setPlayAuth((String) map3.get("playAuth"));
                        updateSource(aliMediaDownloaderRemove, vidAuth);
                    }
                }
                result.success(null);
                break;
            case "prepare":
                Map map4 = (Map) methodCall.arguments;
                Integer num4 = (Integer) map4.get(FirebaseAnalytics.Param.INDEX);
                String str7 = (String) map4.get("type");
                String str8 = (String) map4.get("vid");
                String str9 = (String) map4.get("region");
                if (str7 != null && str7.equals("download_sts")) {
                    VidSts vidSts2 = new VidSts();
                    vidSts2.setVid(str8);
                    vidSts2.setRegion(str9);
                    vidSts2.setAccessKeyId((String) map4.get("accessKeyId"));
                    vidSts2.setAccessKeySecret((String) map4.get("accessKeySecret"));
                    vidSts2.setSecurityToken((String) map4.get("securityToken"));
                    if (num4 != null) {
                        prepare(vidSts2, num4.intValue(), result);
                        break;
                    } else {
                        prepare(vidSts2, result);
                        break;
                    }
                } else if (str7 != null && str7.equals("download_auth")) {
                    VidAuth vidAuth2 = new VidAuth();
                    vidAuth2.setVid(str8);
                    vidAuth2.setRegion(str9);
                    vidAuth2.setPlayAuth((String) map4.get("playAuth"));
                    if (num4 != null) {
                        prepare(vidAuth2, num4.intValue(), result);
                        break;
                    } else {
                        prepare(vidAuth2, result);
                        break;
                    }
                }
                break;
            case "stop":
                Map map5 = (Map) methodCall.arguments;
                String str10 = (String) map5.get("vid");
                Integer num5 = (Integer) map5.get(FirebaseAnalytics.Param.INDEX);
                AliMediaDownloader aliMediaDownloader3 = this.mAliMediaDownloadMap.get(str10 + "_" + num5);
                if (aliMediaDownloader3 != null) {
                    stop(aliMediaDownloader3);
                }
                result.success(null);
                break;
            case "start":
                Map<String, Object> map6 = (Map) methodCall.arguments;
                String str11 = (String) map6.get("vid");
                Integer num6 = (Integer) map6.get(FirebaseAnalytics.Param.INDEX);
                AliMediaDownloader aliMediaDownloader4 = this.mAliMediaDownloadMap.get(str11 + "_" + num6);
                if (aliMediaDownloader4 != null) {
                    aliMediaDownloader4.setSaveDir(this.mSavePath);
                    start(aliMediaDownloader4, map6);
                }
                result.success(null);
                break;
            case "setSaveDir":
                this.mSavePath = (String) methodCall.arguments;
                result.success(null);
                break;
            case "setDownloaderConfig":
                Map map7 = (Map) methodCall.arguments;
                String str12 = (String) map7.get("vid");
                Integer num7 = (Integer) map7.get(FirebaseAnalytics.Param.INDEX);
                AliMediaDownloader aliMediaDownloaderRemove2 = this.mAliMediaDownloadMap.remove(str12 + "_" + num7);
                if (aliMediaDownloaderRemove2 != null) {
                    DownloaderConfig downloaderConfig = new DownloaderConfig();
                    String str13 = (String) map7.get("UserAgent");
                    if (TextUtils.isEmpty(str13)) {
                        str13 = "";
                    }
                    downloaderConfig.mUserAgent = str13;
                    String str14 = (String) map7.get("Referrer");
                    if (TextUtils.isEmpty(str14)) {
                        str14 = "";
                    }
                    downloaderConfig.mReferrer = str14;
                    String str15 = (String) map7.get("HttpProxy");
                    downloaderConfig.mHttpProxy = TextUtils.isEmpty(str15) ? "" : str15;
                    Integer num8 = (Integer) map7.get("ConnectTimeoutS");
                    downloaderConfig.mConnectTimeoutS = num8 == null ? 0 : num8.intValue();
                    downloaderConfig.mNetworkTimeoutMs = ((Integer) map7.get("NetworkTimeoutMs")) == null ? 0L : r1.intValue();
                    setDownloaderConfig(aliMediaDownloaderRemove2, downloaderConfig);
                }
                result.success(null);
                break;
            case "release":
                Map map8 = (Map) methodCall.arguments;
                String str16 = (String) map8.get("vid");
                Integer num9 = (Integer) map8.get(FirebaseAnalytics.Param.INDEX);
                AliMediaDownloader aliMediaDownloaderRemove3 = this.mAliMediaDownloadMap.remove(str16 + "_" + num9);
                if (aliMediaDownloaderRemove3 != null) {
                    release(aliMediaDownloaderRemove3);
                }
                result.success(null);
                break;
            case "getFilePath":
                Map map9 = (Map) methodCall.arguments;
                String str17 = (String) map9.get("vid");
                Integer num10 = (Integer) map9.get(FirebaseAnalytics.Param.INDEX);
                AliMediaDownloader aliMediaDownloader5 = this.mAliMediaDownloadMap.get(str17 + "_" + num10);
                if (aliMediaDownloader5 != null) {
                    String filePath = getFilePath(aliMediaDownloader5);
                    map9.put("savePath", filePath);
                    result.success(filePath);
                }
                result.success(null);
                break;
        }
    }

    private void updateSource(AliMediaDownloader aliMediaDownloader, VidAuth vidAuth) {
        aliMediaDownloader.updateSource(vidAuth);
    }

    private void prepare(VidAuth vidAuth, final MethodChannel.Result result) {
        AliMediaDownloader aliMediaDownloaderCreate = AliDownloaderFactory.create(this.mContext);
        this.mAliMediaDownloadMap.put(vidAuth.getVid(), aliMediaDownloaderCreate);
        aliMediaDownloaderCreate.setOnPreparedListener(new AliMediaDownloader.OnPreparedListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliDownloader.3
            @Override // com.aliyun.downloader.AliMediaDownloader.OnPreparedListener
            public void onPrepared(MediaInfo mediaInfo) {
                String json = new Gson().toJson(mediaInfo);
                mediaInfo.getTrackInfos();
                result.success(json);
            }
        });
        aliMediaDownloaderCreate.setOnErrorListener(new AliMediaDownloader.OnErrorListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliDownloader.4
            @Override // com.aliyun.downloader.AliMediaDownloader.OnErrorListener
            public void onError(ErrorInfo errorInfo) {
                result.error(errorInfo.getCode().toString(), errorInfo.getMsg(), errorInfo.getExtra());
            }
        });
        aliMediaDownloaderCreate.prepare(vidAuth);
    }

    private void prepare(VidSts vidSts, final int i2, final MethodChannel.Result result) {
        final AliMediaDownloader aliMediaDownloaderCreate = AliDownloaderFactory.create(this.mContext);
        this.mAliMediaDownloadMap.put(vidSts.getVid(), aliMediaDownloaderCreate);
        aliMediaDownloaderCreate.setOnPreparedListener(new AliMediaDownloader.OnPreparedListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliDownloader.5
            @Override // com.aliyun.downloader.AliMediaDownloader.OnPreparedListener
            public void onPrepared(MediaInfo mediaInfo) {
                String json = new Gson().toJson(mediaInfo);
                aliMediaDownloaderCreate.selectItem(i2);
                FlutterAliDownloader.this.mAliMediaDownloadMap.put(mediaInfo.getVideoId() + "_" + i2, aliMediaDownloaderCreate);
                result.success(json);
            }
        });
        aliMediaDownloaderCreate.setOnErrorListener(new AliMediaDownloader.OnErrorListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliDownloader.6
            @Override // com.aliyun.downloader.AliMediaDownloader.OnErrorListener
            public void onError(ErrorInfo errorInfo) {
                result.error(errorInfo.getCode().toString(), errorInfo.getMsg(), errorInfo.getExtra());
            }
        });
        aliMediaDownloaderCreate.prepare(vidSts);
    }

    private void prepare(VidSts vidSts, final MethodChannel.Result result) {
        AliMediaDownloader aliMediaDownloaderCreate = AliDownloaderFactory.create(this.mContext);
        this.mAliMediaDownloadMap.put(vidSts.getVid(), aliMediaDownloaderCreate);
        aliMediaDownloaderCreate.setOnPreparedListener(new AliMediaDownloader.OnPreparedListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliDownloader.7
            @Override // com.aliyun.downloader.AliMediaDownloader.OnPreparedListener
            public void onPrepared(MediaInfo mediaInfo) {
                result.success(new Gson().toJson(mediaInfo));
            }
        });
        aliMediaDownloaderCreate.setOnErrorListener(new AliMediaDownloader.OnErrorListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliDownloader.8
            @Override // com.aliyun.downloader.AliMediaDownloader.OnErrorListener
            public void onError(ErrorInfo errorInfo) {
                result.error(errorInfo.getCode().toString(), errorInfo.getMsg(), errorInfo.getExtra());
            }
        });
        aliMediaDownloaderCreate.prepare(vidSts);
    }
}
