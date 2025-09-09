package com.aliyun.player;

import com.aliyun.player.source.LiveShift;

/* loaded from: classes3.dex */
public interface AliLiveShiftPlayer extends IPlayer {

    public interface OnSeekLiveCompletionListener {
        void onSeekLiveCompletion(long j2);
    }

    public interface OnTimeShiftUpdaterListener {
        void onUpdater(long j2, long j3, long j4);
    }

    long getCurrentLiveTime();

    long getCurrentTime();

    void seekToLiveTime(long j2);

    void setDataSource(LiveShift liveShift);

    void setOnSeekLiveCompletionListener(OnSeekLiveCompletionListener onSeekLiveCompletionListener);

    void setOnTimeShiftUpdaterListener(OnTimeShiftUpdaterListener onTimeShiftUpdaterListener);
}
