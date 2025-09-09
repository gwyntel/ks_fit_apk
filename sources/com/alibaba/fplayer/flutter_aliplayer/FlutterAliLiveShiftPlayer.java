package com.alibaba.fplayer.flutter_aliplayer;

import android.content.Context;
import com.aliyun.player.AliLiveShiftPlayer;
import com.aliyun.player.AliPlayerFactory;
import com.aliyun.player.IPlayer;
import com.aliyun.player.source.LiveShift;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.util.Map;

/* loaded from: classes2.dex */
public class FlutterAliLiveShiftPlayer extends FlutterPlayerBase {
    private final AliLiveShiftPlayer mAliLiveShiftPlayer;

    public FlutterAliLiveShiftPlayer(FlutterPlugin.FlutterPluginBinding flutterPluginBinding, String str) {
        this.mPlayerId = str;
        Context applicationContext = flutterPluginBinding.getApplicationContext();
        this.mContext = applicationContext;
        AliLiveShiftPlayer aliLiveShiftPlayerCreateAliLiveShiftPlayer = AliPlayerFactory.createAliLiveShiftPlayer(applicationContext);
        this.mAliLiveShiftPlayer = aliLiveShiftPlayerCreateAliLiveShiftPlayer;
        initListener(aliLiveShiftPlayerCreateAliLiveShiftPlayer);
    }

    private long getCurrentLiveTime() {
        AliLiveShiftPlayer aliLiveShiftPlayer = this.mAliLiveShiftPlayer;
        if (aliLiveShiftPlayer != null) {
            return aliLiveShiftPlayer.getCurrentLiveTime();
        }
        return 0L;
    }

    private long getCurrentTime() {
        AliLiveShiftPlayer aliLiveShiftPlayer = this.mAliLiveShiftPlayer;
        if (aliLiveShiftPlayer != null) {
            return aliLiveShiftPlayer.getCurrentTime();
        }
        return 0L;
    }

    private void pause() {
        AliLiveShiftPlayer aliLiveShiftPlayer = this.mAliLiveShiftPlayer;
        if (aliLiveShiftPlayer != null) {
            aliLiveShiftPlayer.pause();
        }
    }

    private void prepare() {
        AliLiveShiftPlayer aliLiveShiftPlayer = this.mAliLiveShiftPlayer;
        if (aliLiveShiftPlayer != null) {
            aliLiveShiftPlayer.prepare();
        }
    }

    private void release() {
        AliLiveShiftPlayer aliLiveShiftPlayer = this.mAliLiveShiftPlayer;
        if (aliLiveShiftPlayer != null) {
            aliLiveShiftPlayer.release();
        }
    }

    private void releaseAsync() {
        AliLiveShiftPlayer aliLiveShiftPlayer = this.mAliLiveShiftPlayer;
        if (aliLiveShiftPlayer != null) {
            aliLiveShiftPlayer.releaseAsync();
        }
    }

    private void seekToLiveTime(long j2) {
        AliLiveShiftPlayer aliLiveShiftPlayer = this.mAliLiveShiftPlayer;
        if (aliLiveShiftPlayer != null) {
            aliLiveShiftPlayer.seekToLiveTime(j2);
        }
    }

    private void setDataSource(LiveShift liveShift) {
        AliLiveShiftPlayer aliLiveShiftPlayer = this.mAliLiveShiftPlayer;
        if (aliLiveShiftPlayer != null) {
            aliLiveShiftPlayer.setDataSource(liveShift);
        }
    }

    private void start() {
        AliLiveShiftPlayer aliLiveShiftPlayer = this.mAliLiveShiftPlayer;
        if (aliLiveShiftPlayer != null) {
            aliLiveShiftPlayer.start();
        }
    }

    private void stop() {
        AliLiveShiftPlayer aliLiveShiftPlayer = this.mAliLiveShiftPlayer;
        if (aliLiveShiftPlayer != null) {
            aliLiveShiftPlayer.stop();
        }
    }

    @Override // com.alibaba.fplayer.flutter_aliplayer.FlutterPlayerBase
    public IPlayer getAliPlayer() {
        return this.mAliLiveShiftPlayer;
    }

    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        String str = methodCall.method;
        str.hashCode();
        switch (str) {
            case "releaseAsync":
                releaseAsync();
                result.success(null);
                break;
            case "seekToLiveTime":
                seekToLiveTime(((Integer) methodCall.argument("arg")).intValue());
                result.success(null);
                break;
            case "getCurrentLiveTime":
                result.success(Long.valueOf(getCurrentLiveTime()));
                break;
            case "prepare":
                prepare();
                result.success(null);
                break;
            case "getCurrentTime":
                result.success(Long.valueOf(getCurrentTime()));
                break;
            case "play":
                start();
                result.success(null);
                break;
            case "stop":
                stop();
                result.success(null);
                break;
            case "pause":
                pause();
                result.success(null);
                break;
            case "destroy":
                release();
                result.success(null);
                break;
            case "setDataSource":
                Map map = (Map) methodCall.argument("arg");
                LiveShift liveShift = new LiveShift();
                liveShift.setTimeLineUrl((String) map.get("timeLineUrl"));
                liveShift.setUrl((String) map.get("url"));
                liveShift.setCoverPath((String) map.get("coverPath"));
                liveShift.setFormat((String) map.get("format"));
                liveShift.setTitle((String) map.get("title"));
                setDataSource(liveShift);
                result.success(null);
                break;
        }
    }
}
