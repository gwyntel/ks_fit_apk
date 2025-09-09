package com.alibaba.fplayer.flutter_aliplayer;

import java.util.Map;

/* loaded from: classes2.dex */
public interface FlutterAliPlayerListener {
    int onChooseTrackIndex(Map<String, Object> map);

    void onCompletion(Map<String, Object> map);

    void onError(Map<String, Object> map);

    void onInfo(Map<String, Object> map);

    void onLoadingBegin(Map<String, Object> map);

    void onLoadingEnd(Map<String, Object> map);

    void onLoadingProgress(Map<String, Object> map);

    void onPrepared(Map<String, Object> map);

    void onRenderingStart(Map<String, Object> map);

    void onReportEventListener(Map<String, Object> map);

    void onSeekComplete(Map<String, Object> map);

    void onSeekLiveCompletion(Map<String, Object> map);

    void onSeiData(Map<String, Object> map);

    void onSnapShot(Map<String, Object> map);

    void onStateChanged(Map<String, Object> map);

    void onStreamSwitchedFail(Map<String, Object> map);

    void onStreamSwitchedSuccess(Map<String, Object> map);

    void onSubTrackReady(Map<String, Object> map);

    void onSubtitleExtAdded(Map<String, Object> map);

    void onSubtitleHeader(Map<String, Object> map);

    void onSubtitleHide(Map<String, Object> map);

    void onSubtitleShow(Map<String, Object> map);

    void onThumbnailGetFail(Map<String, Object> map);

    void onThumbnailGetSuccess(Map<String, Object> map);

    void onThumbnailPrepareFail(Map<String, Object> map);

    void onThumbnailPrepareSuccess(Map<String, Object> map);

    void onTimeShiftUpdater(Map<String, Object> map);

    void onTrackChangedFail(Map<String, Object> map);

    void onTrackChangedSuccess(Map<String, Object> map);

    void onTrackReady(Map<String, Object> map);

    void onVideoRendered(Map<String, Object> map);

    void onVideoSizeChanged(Map<String, Object> map);
}
