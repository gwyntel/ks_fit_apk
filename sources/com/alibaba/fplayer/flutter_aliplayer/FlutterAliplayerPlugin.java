package com.alibaba.fplayer.flutter_aliplayer;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerView;
import com.aliyun.player.AliPlayerFactory;
import com.aliyun.player.AliPlayerGlobalSettings;
import com.aliyun.player.IPlayer;
import com.aliyun.player.VidPlayerConfigGen;
import com.aliyun.private_service.PrivateService;
import com.cicada.player.utils.Logger;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.CharUtils;

/* loaded from: classes2.dex */
public class FlutterAliplayerPlugin extends PlatformViewFactory implements FlutterPlugin, MethodChannel.MethodCallHandler, EventChannel.StreamHandler, FlutterAliPlayerView.FlutterAliPlayerViewListener {
    private static final String TAG = "FlutterAliplayerPlugin";
    private static final Handler sMainHandler = new Handler(Looper.getMainLooper());
    private FlutterAliFloatWindowManager flutterAliFloatWindowManager;
    private FlutterPlugin.FlutterPluginBinding flutterPluginBinding;
    private FlutterAliPlayerGlobalSettings mAliPlayerGlobalSettings;
    private FlutterAliDownloader mAliyunDownload;
    private FlutterAliMediaLoader mAliyunMediaLoader;
    private Context mContext;
    private EventChannel mEventChannel;
    private EventChannel.EventSink mEventSink;
    private Map<String, FlutterAliListPlayer> mFlutterAliListPlayerMap;
    private Map<String, FlutterAliLiveShiftPlayer> mFlutterAliLiveShiftPlayerMap;
    private Map<String, FlutterAliPlayer> mFlutterAliPlayerMap;
    private Map<Integer, FlutterAliPlayerView> mFlutterAliPlayerViewMap;
    private MethodChannel mMethodChannel;
    private Logger.OnLogCallback mOnLogCallback;
    private VidPlayerConfigGen mVidPlayerConfigGen;
    private Integer playerType;

    public FlutterAliplayerPlugin() {
        super(StandardMessageCodec.INSTANCE);
        this.mFlutterAliPlayerMap = new HashMap();
        this.mFlutterAliListPlayerMap = new HashMap();
        this.mFlutterAliLiveShiftPlayerMap = new HashMap();
        this.mFlutterAliPlayerViewMap = new HashMap();
        this.playerType = -1;
    }

    private void addBlackDevice(String str, String str2) {
        AliPlayerFactory.DeviceInfo deviceInfo = new AliPlayerFactory.DeviceInfo();
        deviceInfo.model = str2;
        AliPlayerFactory.addBlackDevice((TextUtils.isEmpty(str) || !str.equals("HW_Decode_H264")) ? AliPlayerFactory.BlackType.HW_Decode_HEVC : AliPlayerFactory.BlackType.HW_Decode_H264, deviceInfo);
    }

    private void checkAvailableRts() {
        if (this.mContext.getResources().getBoolean(R.bool.use_aio_framework)) {
            return;
        }
        System.loadLibrary("RtsSDK");
        System.loadLibrary("cicada_plugin_artcSource");
    }

    private String createDeviceInfo() {
        AliPlayerFactory.DeviceInfo deviceInfo = new AliPlayerFactory.DeviceInfo();
        String str = Build.MODEL;
        deviceInfo.model = str;
        return str;
    }

    private void enableConsoleLog(Boolean bool) {
        Logger.getInstance(this.flutterPluginBinding.getApplicationContext()).enableConsoleLog(bool.booleanValue());
    }

    private Integer getLogLevel() {
        return Integer.valueOf(Logger.getInstance(this.flutterPluginBinding.getApplicationContext()).getLogLevel().getValue());
    }

    private void initListener(FlutterPlayerBase flutterPlayerBase) {
        flutterPlayerBase.setOnFlutterListener(new FlutterAliPlayerListener() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliplayerPlugin.3
            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public int onChooseTrackIndex(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
                return 0;
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onCompletion(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onError(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onInfo(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onLoadingBegin(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onLoadingEnd(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onLoadingProgress(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onPrepared(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onRenderingStart(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onReportEventListener(final Map<String, Object> map) {
                FlutterAliplayerPlugin.sMainHandler.post(new Runnable() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliplayerPlugin.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        FlutterAliplayerPlugin.this.mEventSink.success(map);
                    }
                });
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onSeekComplete(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onSeekLiveCompletion(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onSeiData(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onSnapShot(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onStateChanged(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onStreamSwitchedFail(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onStreamSwitchedSuccess(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onSubTrackReady(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onSubtitleExtAdded(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onSubtitleHeader(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onSubtitleHide(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onSubtitleShow(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onThumbnailGetFail(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onThumbnailGetSuccess(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onThumbnailPrepareFail(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onThumbnailPrepareSuccess(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onTimeShiftUpdater(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onTrackChangedFail(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onTrackChangedSuccess(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onTrackReady(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onVideoRendered(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }

            @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerListener
            public void onVideoSizeChanged(Map<String, Object> map) {
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0011 A[PHI: r1
      0x0011: PHI (r1v7 com.cicada.player.utils.Logger$LogLevel) = 
      (r1v1 com.cicada.player.utils.Logger$LogLevel)
      (r1v2 com.cicada.player.utils.Logger$LogLevel)
      (r1v3 com.cicada.player.utils.Logger$LogLevel)
      (r1v4 com.cicada.player.utils.Logger$LogLevel)
      (r1v5 com.cicada.player.utils.Logger$LogLevel)
      (r1v6 com.cicada.player.utils.Logger$LogLevel)
     binds: [B:6:0x000f, B:9:0x0019, B:12:0x0022, B:15:0x002b, B:18:0x0034, B:21:0x003d] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void setLogLevel(int r4) {
        /*
            r3 = this;
            com.cicada.player.utils.Logger$LogLevel r0 = com.cicada.player.utils.Logger.LogLevel.AF_LOG_LEVEL_NONE
            int r1 = r0.getValue()
            if (r4 != r1) goto L9
            goto L40
        L9:
            com.cicada.player.utils.Logger$LogLevel r1 = com.cicada.player.utils.Logger.LogLevel.AF_LOG_LEVEL_FATAL
            int r2 = r1.getValue()
            if (r4 != r2) goto L13
        L11:
            r0 = r1
            goto L40
        L13:
            com.cicada.player.utils.Logger$LogLevel r1 = com.cicada.player.utils.Logger.LogLevel.AF_LOG_LEVEL_ERROR
            int r2 = r1.getValue()
            if (r4 != r2) goto L1c
            goto L11
        L1c:
            com.cicada.player.utils.Logger$LogLevel r1 = com.cicada.player.utils.Logger.LogLevel.AF_LOG_LEVEL_WARNING
            int r2 = r1.getValue()
            if (r4 != r2) goto L25
            goto L11
        L25:
            com.cicada.player.utils.Logger$LogLevel r1 = com.cicada.player.utils.Logger.LogLevel.AF_LOG_LEVEL_INFO
            int r2 = r1.getValue()
            if (r4 != r2) goto L2e
            goto L11
        L2e:
            com.cicada.player.utils.Logger$LogLevel r1 = com.cicada.player.utils.Logger.LogLevel.AF_LOG_LEVEL_DEBUG
            int r2 = r1.getValue()
            if (r4 != r2) goto L37
            goto L11
        L37:
            com.cicada.player.utils.Logger$LogLevel r1 = com.cicada.player.utils.Logger.LogLevel.AF_LOG_LEVEL_TRACE
            int r2 = r1.getValue()
            if (r4 != r2) goto L40
            goto L11
        L40:
            io.flutter.embedding.engine.plugins.FlutterPlugin$FlutterPluginBinding r4 = r3.flutterPluginBinding
            android.content.Context r4 = r4.getApplicationContext()
            com.cicada.player.utils.Logger r4 = com.cicada.player.utils.Logger.getInstance(r4)
            r4.setLogLevel(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fplayer.flutter_aliplayer.FlutterAliplayerPlugin.setLogLevel(int):void");
    }

    private void setOnLogCallback(Boolean bool) {
        this.mOnLogCallback = bool.booleanValue() ? new Logger.OnLogCallback() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliplayerPlugin.2
            @Override // com.cicada.player.utils.Logger.OnLogCallback
            public void onLog(Logger.LogLevel logLevel, String str) {
                HashMap map = new HashMap();
                map.put("method", "AliPlayer_LogCallBackInfo");
                map.put("logLevel", logLevel.getValue() + "");
                map.put("strLog", str);
                FlutterAliplayerPlugin.this.mEventSink.success(map);
            }
        } : null;
        Logger.getInstance(this.flutterPluginBinding.getApplicationContext()).setLogCallback(this.mOnLogCallback);
    }

    @Override // io.flutter.plugin.platform.PlatformViewFactory
    @NonNull
    public PlatformView create(Context context, int i2, Object obj) {
        FlutterAliPlayerView flutterAliPlayerView = new FlutterAliPlayerView(context, i2, obj);
        flutterAliPlayerView.setFlutterAliPlayerViewListener(this);
        this.mFlutterAliPlayerViewMap.put(Integer.valueOf(i2), flutterAliPlayerView);
        return flutterAliPlayerView;
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.flutterPluginBinding = flutterPluginBinding;
        this.mContext = flutterPluginBinding.getApplicationContext();
        checkAvailableRts();
        flutterPluginBinding.getPlatformViewRegistry().registerViewFactory("flutter_aliplayer_render_view", this);
        AliPlayerGlobalSettings.setOption(3, FlutterAliPlayerUtils.sdkVersion());
        this.mAliyunDownload = new FlutterAliDownloader(flutterPluginBinding.getApplicationContext(), flutterPluginBinding);
        this.mAliyunMediaLoader = new FlutterAliMediaLoader(flutterPluginBinding.getApplicationContext(), flutterPluginBinding);
        this.mAliPlayerGlobalSettings = new FlutterAliPlayerGlobalSettings(flutterPluginBinding.getApplicationContext(), flutterPluginBinding);
        MethodChannel methodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "plugins.flutter_aliplayer_factory");
        this.mMethodChannel = methodChannel;
        methodChannel.setMethodCallHandler(this);
        EventChannel eventChannel = new EventChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "flutter_aliplayer_event");
        this.mEventChannel = eventChannel;
        eventChannel.setStreamHandler(this);
        this.flutterAliFloatWindowManager = new FlutterAliFloatWindowManager(flutterPluginBinding.getApplicationContext());
        AliPlayerGlobalSettings.setCacheUrlHashCallback(new AliPlayerGlobalSettings.OnGetUrlHashCallback() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliplayerPlugin.1
            @Override // com.aliyun.player.AliPlayerGlobalSettings.OnGetUrlHashCallback
            public String getUrlHashCallback(String str) {
                if (str.contains("Ciphertext")) {
                    return FlutterAliPlayerStringUtils.stringToMD5(str);
                }
                if (str.contains("?")) {
                    str = str.split("\\?")[0];
                }
                return FlutterAliPlayerStringUtils.stringToMD5(str);
            }
        });
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onCancel(Object obj) {
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
    }

    @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerView.FlutterAliPlayerViewListener
    public void onDispose(int i2) {
        this.mFlutterAliPlayerViewMap.remove(Integer.valueOf(i2));
        AliChannelPool.getInstance().clear();
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onListen(Object obj, EventChannel.EventSink eventSink) {
        this.mEventSink = eventSink;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
        String str = methodCall.method;
        str.hashCode();
        char c2 = 65535;
        switch (str.hashCode()) {
            case -2061552162:
                if (str.equals("clearCaches")) {
                    c2 = 0;
                    break;
                }
                break;
            case -2059586179:
                if (str.equals("setEncryptType")) {
                    c2 = 1;
                    break;
                }
                break;
            case -1932877698:
                if (str.equals("enableHttpDns")) {
                    c2 = 2;
                    break;
                }
                break;
            case -1722681560:
                if (str.equals("setPlayerView")) {
                    c2 = 3;
                    break;
                }
                break;
            case -1688580544:
                if (str.equals("createDeviceInfo")) {
                    c2 = 4;
                    break;
                }
                break;
            case -1404166629:
                if (str.equals("createVidPlayerConfigGenerator")) {
                    c2 = 5;
                    break;
                }
                break;
            case -1120402493:
                if (str.equals("isFeatureSupport")) {
                    c2 = 6;
                    break;
                }
                break;
            case -990763131:
                if (str.equals("initService")) {
                    c2 = 7;
                    break;
                }
                break;
            case -921450857:
                if (str.equals("setLogOption")) {
                    c2 = '\b';
                    break;
                }
                break;
            case -573576112:
                if (str.equals("enableConsoleLog")) {
                    c2 = '\t';
                    break;
                }
                break;
            case -357913469:
                if (str.equals("createAliPlayer")) {
                    c2 = '\n';
                    break;
                }
                break;
            case -332402390:
                if (str.equals("showFloatViewForAndroid")) {
                    c2 = 11;
                    break;
                }
                break;
            case -309915358:
                if (str.equals("setLogLevel")) {
                    c2 = '\f';
                    break;
                }
                break;
            case -221335530:
                if (str.equals("getLogLevel")) {
                    c2 = CharUtils.CR;
                    break;
                }
                break;
            case -191766732:
                if (str.equals("getSDKVersion")) {
                    c2 = 14;
                    break;
                }
                break;
            case -189255803:
                if (str.equals("forceAudioRendingFormat")) {
                    c2 = 15;
                    break;
                }
                break;
            case 100338993:
                if (str.equals("enableNetworkBalance")) {
                    c2 = 16;
                    break;
                }
                break;
            case 143765125:
                if (str.equals("setDNSResolve")) {
                    c2 = 17;
                    break;
                }
                break;
            case 483436295:
                if (str.equals("getDeviceUUID")) {
                    c2 = 18;
                    break;
                }
                break;
            case 591741171:
                if (str.equals("setPreviewTime")) {
                    c2 = 19;
                    break;
                }
                break;
            case 597397460:
                if (str.equals("addBlackDevice")) {
                    c2 = 20;
                    break;
                }
                break;
            case 954459251:
                if (str.equals("setCacheFileClearConfig")) {
                    c2 = 21;
                    break;
                }
                break;
            case 1042678970:
                if (str.equals("enableLocalCache")) {
                    c2 = 22;
                    break;
                }
                break;
            case 1113084396:
                if (str.equals("addVidPlayerConfigByIntValue")) {
                    c2 = 23;
                    break;
                }
                break;
            case 1305922192:
                if (str.equals("loadRtsLibrary")) {
                    c2 = 24;
                    break;
                }
                break;
            case 1529404218:
                if (str.equals("setHlsUriToken")) {
                    c2 = 25;
                    break;
                }
                break;
            case 1630417014:
                if (str.equals("addVidPlayerConfigByStringValue")) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                break;
            case 1665585789:
                if (str.equals("setIPResolveType")) {
                    c2 = 27;
                    break;
                }
                break;
            case 1685016037:
                if (str.equals("setUseHttp2")) {
                    c2 = 28;
                    break;
                }
                break;
            case 1847763741:
                if (str.equals("setLogInfoBlock")) {
                    c2 = 29;
                    break;
                }
                break;
            case 1856009925:
                if (str.equals("hideFloatViewForAndroid")) {
                    c2 = 30;
                    break;
                }
                break;
            case 1887403928:
                if (str.equals("generatePlayerConfig")) {
                    c2 = 31;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                AliPlayerGlobalSettings.clearCaches();
                break;
            case 1:
                int iIntValue = ((Integer) methodCall.arguments).intValue();
                VidPlayerConfigGen vidPlayerConfigGen = this.mVidPlayerConfigGen;
                if (vidPlayerConfigGen != null) {
                    vidPlayerConfigGen.setEncryptType(VidPlayerConfigGen.EncryptType.values()[iIntValue]);
                }
                result.success(null);
                break;
            case 2:
                AliPlayerGlobalSettings.enableHttpDns(((Boolean) methodCall.arguments()).booleanValue());
                break;
            case 3:
                FlutterAliPlayerView flutterAliPlayerView = this.mFlutterAliPlayerViewMap.get((Integer) methodCall.argument("arg"));
                if (this.playerType.intValue() == 0) {
                    FlutterAliPlayer flutterAliPlayer = this.mFlutterAliPlayerMap.get((String) methodCall.argument("playerId"));
                    if (flutterAliPlayerView != null && flutterAliPlayer != null) {
                        flutterAliPlayerView.setPlayer(flutterAliPlayer.getAliPlayer());
                        break;
                    }
                } else if (this.playerType.intValue() == 1) {
                    FlutterAliListPlayer flutterAliListPlayer = this.mFlutterAliListPlayerMap.get((String) methodCall.argument("playerId"));
                    if (flutterAliPlayerView != null && flutterAliListPlayer != null) {
                        flutterAliPlayerView.setPlayer(flutterAliListPlayer.getAliPlayer());
                        break;
                    }
                } else if (this.playerType.intValue() == 2) {
                    FlutterAliLiveShiftPlayer flutterAliLiveShiftPlayer = this.mFlutterAliLiveShiftPlayerMap.get((String) methodCall.argument("playerId"));
                    if (flutterAliPlayerView != null && flutterAliLiveShiftPlayer != null) {
                        flutterAliPlayerView.setPlayer(flutterAliLiveShiftPlayer.getAliPlayer());
                        break;
                    }
                }
                break;
            case 4:
                result.success(createDeviceInfo());
                break;
            case 5:
                this.mVidPlayerConfigGen = new VidPlayerConfigGen();
                result.success(null);
                break;
            case 6:
                result.success(Boolean.valueOf(AliPlayerFactory.isFeatureSupport(AliPlayerFactory.SupportFeatureType.FeatureDolbyAudio)));
                break;
            case 7:
                PrivateService.initService(this.flutterPluginBinding.getApplicationContext(), (byte[]) methodCall.arguments);
                result.success(null);
                break;
            case '\b':
                Logger.getInstance(this.mContext).setLogOption(Logger.LogOption.FRAME_LEVEL_LOGGING_ENABLED, ((Integer) methodCall.arguments).intValue());
                result.success(null);
                break;
            case '\t':
                enableConsoleLog((Boolean) methodCall.arguments);
                result.success(null);
                break;
            case '\n':
                Integer num = (Integer) methodCall.argument("arg");
                this.playerType = num;
                if (num.intValue() == 0) {
                    String str2 = (String) methodCall.argument("playerId");
                    FlutterAliPlayer flutterAliPlayer2 = new FlutterAliPlayer(this.flutterPluginBinding, str2);
                    initListener(flutterAliPlayer2);
                    this.mFlutterAliPlayerMap.put(str2, flutterAliPlayer2);
                } else if (1 == this.playerType.intValue()) {
                    String str3 = (String) methodCall.argument("playerId");
                    FlutterAliListPlayer flutterAliListPlayer2 = new FlutterAliListPlayer(this.flutterPluginBinding, str3);
                    initListener(flutterAliListPlayer2);
                    this.mFlutterAliListPlayerMap.put(str3, flutterAliListPlayer2);
                } else if (2 == this.playerType.intValue()) {
                    String str4 = (String) methodCall.argument("playerId");
                    FlutterAliLiveShiftPlayer flutterAliLiveShiftPlayer2 = new FlutterAliLiveShiftPlayer(this.flutterPluginBinding, str4);
                    initListener(flutterAliLiveShiftPlayer2);
                    this.mFlutterAliLiveShiftPlayerMap.put(str4, flutterAliLiveShiftPlayer2);
                }
                result.success(null);
                break;
            case 11:
                Integer num2 = (Integer) methodCall.arguments;
                num2.intValue();
                this.flutterAliFloatWindowManager.showFloatWindow(this.mFlutterAliPlayerViewMap.get(num2));
                result.success(null);
                break;
            case '\f':
                setLogLevel(((Integer) methodCall.arguments).intValue());
                result.success(null);
                break;
            case '\r':
                result.success(getLogLevel());
                break;
            case 14:
                result.success(AliPlayerFactory.getSdkVersion());
                break;
            case 15:
                Map map = (Map) methodCall.arguments();
                AliPlayerGlobalSettings.forceAudioRendingFormat(Boolean.getBoolean((String) map.get("force")), (String) map.get("fmt"), Integer.valueOf((String) map.get("channels")).intValue(), Integer.valueOf((String) map.get("sample_rate")).intValue());
                break;
            case 16:
                AliPlayerGlobalSettings.enableNetworkBalance(((Boolean) methodCall.arguments()).booleanValue());
                result.success(null);
                break;
            case 17:
                Map map2 = (Map) methodCall.arguments();
                if (map2 != null) {
                    AliPlayerGlobalSettings.setDNSResolve((String) map2.get("host"), (String) map2.get("ip"));
                }
                result.success(null);
                break;
            case 18:
                result.success(AliPlayerFactory.getDeviceUUID());
                break;
            case 19:
                String str5 = (String) methodCall.arguments();
                if (this.mVidPlayerConfigGen != null && !TextUtils.isEmpty(str5)) {
                    this.mVidPlayerConfigGen.setPreviewTime(Integer.parseInt(str5));
                }
                result.success(null);
                break;
            case 20:
                Map map3 = (Map) methodCall.arguments();
                addBlackDevice((String) map3.get("black_type"), (String) map3.get("black_device"));
                result.success(null);
                break;
            case 21:
                Map map4 = (Map) methodCall.arguments();
                if (map4 != null) {
                    AliPlayerGlobalSettings.setCacheFileClearConfig(Long.parseLong((String) map4.get("expireMin")), Long.parseLong((String) map4.get("maxCapacityMB")), Long.parseLong((String) map4.get("freeStorageMB")));
                    break;
                }
                break;
            case 22:
                Map map5 = (Map) methodCall.arguments();
                if (map5 != null) {
                    AliPlayerGlobalSettings.enableLocalCache(((Boolean) map5.get("enable")).booleanValue(), Integer.valueOf((String) map5.get("maxBufferMemoryKB")).intValue(), (String) map5.get("localCacheDir"));
                    break;
                }
                break;
            case 23:
            case 26:
                Map map6 = (Map) methodCall.arguments;
                if (this.mVidPlayerConfigGen != null) {
                    for (String str6 : map6.keySet()) {
                        this.mVidPlayerConfigGen.addPlayerConfig(str6, (String) map6.get(str6));
                    }
                }
                result.success(null);
                break;
            case 24:
                if (((Boolean) methodCall.arguments).booleanValue()) {
                    System.loadLibrary("RtsSDK");
                }
                result.success(null);
                break;
            case 25:
                String str7 = (String) methodCall.arguments();
                VidPlayerConfigGen vidPlayerConfigGen2 = this.mVidPlayerConfigGen;
                if (vidPlayerConfigGen2 != null) {
                    vidPlayerConfigGen2.setMtsHlsUriToken(str7);
                }
                result.success(null);
                break;
            case 27:
                String str8 = methodCall.arguments() == null ? "" : (String) methodCall.arguments();
                str8.hashCode();
                if (str8.equals("v4")) {
                    AliPlayerGlobalSettings.setIPResolveType(IPlayer.IPResolveType.IpResolveV4);
                    break;
                } else if (str8.equals("v6")) {
                    AliPlayerGlobalSettings.setIPResolveType(IPlayer.IPResolveType.IpResolveV6);
                    break;
                } else {
                    AliPlayerGlobalSettings.setIPResolveType(IPlayer.IPResolveType.IpResolveWhatEver);
                    break;
                }
            case 28:
                AliPlayerGlobalSettings.setUseHttp2(((Boolean) methodCall.arguments()).booleanValue());
                break;
            case 29:
                setOnLogCallback(Boolean.valueOf(methodCall.hasArgument("arg") ? Boolean.TRUE.equals(methodCall.argument("arg")) : false));
                result.success(null);
                break;
            case 30:
                this.flutterAliFloatWindowManager.hideFloatWindow();
                result.success(null);
                break;
            case 31:
                result.success(this.mVidPlayerConfigGen.genConfig());
                break;
            default:
                String str9 = (String) methodCall.argument("playerId");
                if (this.mFlutterAliPlayerMap.containsKey(str9)) {
                    String str10 = (String) methodCall.argument("playerId");
                    FlutterAliPlayer flutterAliPlayer3 = this.mFlutterAliPlayerMap.get(str10);
                    if (methodCall.method.equals("destroy")) {
                        this.mFlutterAliPlayerMap.remove(str10);
                    }
                    if (methodCall.method.equals("releaseAsync")) {
                        this.mFlutterAliPlayerMap.remove(str10);
                    }
                    if (flutterAliPlayer3 != null) {
                        flutterAliPlayer3.onMethodCall(methodCall, result);
                        break;
                    }
                } else if (this.mFlutterAliLiveShiftPlayerMap.containsKey(str9)) {
                    FlutterAliLiveShiftPlayer flutterAliLiveShiftPlayer3 = this.mFlutterAliLiveShiftPlayerMap.get(str9);
                    if (methodCall.method.equals("destroy")) {
                        this.mFlutterAliLiveShiftPlayerMap.remove(str9);
                    }
                    if (methodCall.method.equals("releaseAsync")) {
                        this.mFlutterAliLiveShiftPlayerMap.remove(str9);
                    }
                    if (flutterAliLiveShiftPlayer3 != null) {
                        flutterAliLiveShiftPlayer3.onMethodCall(methodCall, result);
                        break;
                    }
                } else if (this.mFlutterAliListPlayerMap.containsKey(str9)) {
                    FlutterAliListPlayer flutterAliListPlayer3 = this.mFlutterAliListPlayerMap.get(str9);
                    if (methodCall.method.equals("destroy")) {
                        this.mFlutterAliListPlayerMap.remove(str9);
                    }
                    if (methodCall.method.equals("releaseAsync")) {
                        this.mFlutterAliListPlayerMap.remove(str9);
                    }
                    if (flutterAliListPlayer3 != null) {
                        flutterAliListPlayer3.onMethodCall(methodCall, result);
                        break;
                    }
                }
                break;
        }
    }
}
