package com.alibaba.fplayer.flutter_aliplayer;

import com.aliyun.player.IPlayer;
import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.nativeclass.Thumbnail;
import com.aliyun.player.nativeclass.TrackInfo;
import com.cicada.player.utils.FrameInfo;
import com.cicada.player.utils.media.DrmCallback;
import io.flutter.plugin.common.MethodChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class FlutterAliPlayerUtils {
    public static void executeMediaInfo(MethodChannel.Result result, MediaInfo mediaInfo) {
        if (mediaInfo != null) {
            HashMap map = new HashMap();
            map.put("title", mediaInfo.getTitle());
            map.put("status", mediaInfo.getStatus());
            map.put("mediaType", mediaInfo.getMediaType());
            map.put("duration", Integer.valueOf(mediaInfo.getDuration()));
            map.put("transcodeMode", mediaInfo.getTransCodeMode());
            map.put("coverURL", mediaInfo.getCoverUrl());
            List<Thumbnail> thumbnailList = mediaInfo.getThumbnailList();
            ArrayList arrayList = new ArrayList();
            for (Thumbnail thumbnail : thumbnailList) {
                HashMap map2 = new HashMap();
                map2.put("url", thumbnail.mURL);
                arrayList.add(map2);
                map.put("thumbnails", arrayList);
            }
            List<TrackInfo> trackInfos = mediaInfo.getTrackInfos();
            ArrayList arrayList2 = new ArrayList();
            Iterator<TrackInfo> it = trackInfos.iterator();
            while (it.hasNext()) {
                arrayList2.add(generateMap(it.next()));
            }
            map.put("tracks", arrayList2);
            result.success(map);
        }
    }

    private static Map<String, Object> generateMap(TrackInfo trackInfo) {
        HashMap map = new HashMap();
        map.put("vodFormat", trackInfo.getVodFormat());
        map.put("videoHeight", Integer.valueOf(trackInfo.getVideoHeight()));
        map.put("videoWidth", Integer.valueOf(trackInfo.getVideoWidth()));
        map.put("subtitleLanguage", trackInfo.getSubtitleLang());
        map.put("trackBitrate", Integer.valueOf(trackInfo.getVideoBitrate()));
        map.put("vodFileSize", Long.valueOf(trackInfo.getVodFileSize()));
        map.put("trackIndex", Integer.valueOf(trackInfo.getIndex()));
        map.put("trackDefinition", trackInfo.getVodDefinition());
        map.put("audioSampleFormat", Integer.valueOf(trackInfo.getAudioSampleFormat()));
        map.put("audioLanguage", trackInfo.getAudioLang());
        map.put("vodPlayUrl", trackInfo.getVodPlayUrl());
        map.put("trackType", Integer.valueOf(trackInfo.getType().ordinal()));
        map.put("audioSamplerate", Integer.valueOf(trackInfo.getAudioSampleRate()));
        map.put("audioChannels", Integer.valueOf(trackInfo.getAudioChannels()));
        return map;
    }

    public static void registerDrmCallback(IPlayer iPlayer, final FlutterInvokeCallback flutterInvokeCallback) {
        iPlayer.setDrmCallback(new DrmCallback() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerUtils.3
            @Override // com.cicada.player.utils.media.DrmCallback
            public byte[] requestKey(String str, byte[] bArr) {
                return (byte[]) flutterInvokeCallback.invokeTwoParameterFlutterCallback(str, bArr, "requestKey");
            }

            @Override // com.cicada.player.utils.media.DrmCallback
            public byte[] requestProvision(String str, byte[] bArr) {
                return (byte[]) flutterInvokeCallback.invokeTwoParameterFlutterCallback(str, bArr, "requestProvision");
            }
        });
    }

    public static void registerPreRenderFrameCallback(IPlayer iPlayer, final FlutterInvokeCallback flutterInvokeCallback) {
        iPlayer.setOnPreRenderFrameCallback(new IPlayer.OnPreRenderFrameCallback() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerUtils.2
            @Override // com.aliyun.player.IPlayer.OnPreRenderFrameCallback
            public boolean onPreRenderFrame(FrameInfo frameInfo) {
                Object objInvokeOneParameterFlutterCallback = flutterInvokeCallback.invokeOneParameterFlutterCallback(frameInfo.toString(), "onPreRenderFrame");
                if (objInvokeOneParameterFlutterCallback == null) {
                    return false;
                }
                return ((Boolean) objInvokeOneParameterFlutterCallback).booleanValue();
            }
        });
    }

    public static void registerRenderFrameCallback(IPlayer iPlayer, final FlutterInvokeCallback flutterInvokeCallback) {
        iPlayer.setOnRenderFrameCallback(new IPlayer.OnRenderFrameCallback() { // from class: com.alibaba.fplayer.flutter_aliplayer.FlutterAliPlayerUtils.1
            @Override // com.aliyun.player.IPlayer.OnRenderFrameCallback
            public boolean onRenderFrame(FrameInfo frameInfo) {
                Object objInvokeOneParameterFlutterCallback = flutterInvokeCallback.invokeOneParameterFlutterCallback(frameInfo.toString(), "onRenderFrame");
                if (objInvokeOneParameterFlutterCallback == null) {
                    return false;
                }
                return ((Boolean) objInvokeOneParameterFlutterCallback).booleanValue();
            }
        });
    }

    public static String sdkVersion() {
        return "{\"platform\":\"flutter\",\"flutter-version\":\"7.3.1\"}";
    }
}
