package com.aliyun.liveshift;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.aliyun.liveshift.bean.TimeLineContent;
import com.aliyun.liveshift.bean.TimeLineInfo;
import com.aliyun.liveshift.request.GetTimeShiftRequest;
import com.aliyun.player.AliLiveShiftPlayer;
import com.aliyun.player.nativeclass.PlayerConfig;
import com.aliyun.player.source.LiveShift;
import com.aliyun.utils.b;
import java.lang.ref.WeakReference;
import java.util.List;

/* loaded from: classes3.dex */
public class LiveTimeUpdater {
    private static final String TAG = "LiveTimeUpdater";
    private static int WHAT_UPDATE_LIVE_TIME = 0;
    private static int WHAT_UPDATE_PLAY_TIME = 1;
    private WeakReference<Context> contextWeak;
    private long liveTime;
    private final LiveShift mTimeShift;
    private long playTime;
    private AliLiveShiftPlayer.OnTimeShiftUpdaterListener timeShiftUpdaterListener;
    private PlayerConfig mConfig = null;
    private Handler timer = new Handler(Looper.getMainLooper()) { // from class: com.aliyun.liveshift.LiveTimeUpdater.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == LiveTimeUpdater.WHAT_UPDATE_LIVE_TIME) {
                LiveTimeUpdater.this.updateLiveTimer();
                LiveTimeUpdater.this.startUpdateLiveTimerDelay(60);
            } else if (message.what == LiveTimeUpdater.WHAT_UPDATE_PLAY_TIME) {
                if (!LiveTimeUpdater.this.needPause) {
                    LiveTimeUpdater.access$508(LiveTimeUpdater.this);
                }
                LiveTimeUpdater.access$608(LiveTimeUpdater.this);
                LiveTimeUpdater.this.startUpdatePlayTimerDelay(1);
            }
        }
    };
    private boolean needPause = false;

    public LiveTimeUpdater(Context context, LiveShift liveShift) {
        this.contextWeak = new WeakReference<>(context);
        this.mTimeShift = liveShift;
    }

    static /* synthetic */ long access$508(LiveTimeUpdater liveTimeUpdater) {
        long j2 = liveTimeUpdater.playTime;
        liveTimeUpdater.playTime = 1 + j2;
        return j2;
    }

    static /* synthetic */ long access$608(LiveTimeUpdater liveTimeUpdater) {
        long j2 = liveTimeUpdater.liveTime;
        liveTimeUpdater.liveTime = 1 + j2;
        return j2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getEndTime(TimeLineContent timeLineContent) {
        List<TimeLineInfo> list = timeLineContent.timelines;
        if (list == null || list.size() <= 0) {
            return 0L;
        }
        return list.get(list.size() - 1).end;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getStartTime(TimeLineContent timeLineContent) {
        List<TimeLineInfo> list = timeLineContent.timelines;
        if (list == null || list.size() <= 0) {
            return 0L;
        }
        return list.get(0).start;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startUpdateLiveTimerDelay(int i2) {
        stopUpdateLiveTimer();
        this.timer.sendEmptyMessageDelayed(WHAT_UPDATE_LIVE_TIME, i2 * 1000);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startUpdatePlayTimerDelay(int i2) {
        stopUpdatePlayTimer();
        this.timer.sendEmptyMessageDelayed(WHAT_UPDATE_PLAY_TIME, i2 * 1000);
    }

    private void stopUpdateLiveTimer() {
        this.timer.removeMessages(WHAT_UPDATE_LIVE_TIME);
    }

    private void stopUpdatePlayTimer() {
        this.timer.removeMessages(WHAT_UPDATE_PLAY_TIME);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLiveTimer() {
        GetTimeShiftRequest getTimeShiftRequest = new GetTimeShiftRequest(this.contextWeak.get(), this.mTimeShift, new b.d<TimeLineContent>() { // from class: com.aliyun.liveshift.LiveTimeUpdater.2
            @Override // com.aliyun.utils.b.d
            public void onFail(int i2, String str, String str2) {
            }

            @Override // com.aliyun.utils.b.d
            public void onSuccess(TimeLineContent timeLineContent, String str) {
                if (LiveTimeUpdater.this.timeShiftUpdaterListener != null) {
                    long j2 = timeLineContent.current;
                    long startTime = LiveTimeUpdater.this.getStartTime(timeLineContent);
                    long endTime = LiveTimeUpdater.this.getEndTime(timeLineContent);
                    LiveTimeUpdater.this.liveTime = j2;
                    if (LiveTimeUpdater.this.playTime < 0) {
                        LiveTimeUpdater liveTimeUpdater = LiveTimeUpdater.this;
                        liveTimeUpdater.playTime = liveTimeUpdater.liveTime;
                    }
                    LiveTimeUpdater.this.startUpdatePlayTimerDelay(0);
                    LiveTimeUpdater.this.timeShiftUpdaterListener.onUpdater(j2, startTime, endTime);
                }
            }
        });
        PlayerConfig playerConfig = this.mConfig;
        if (playerConfig != null) {
            getTimeShiftRequest.setRefer(playerConfig.mReferrer);
            getTimeShiftRequest.setTimeout(this.mConfig.mNetworkTimeout);
            getTimeShiftRequest.setHttpProxy(this.mConfig.mHttpProxy);
            getTimeShiftRequest.setUerAgent(this.mConfig.mUserAgent);
            getTimeShiftRequest.setCustomHeaders(this.mConfig.getCustomHeaders());
        }
        getTimeShiftRequest.getAsync();
    }

    public long getLiveTime() {
        return this.liveTime;
    }

    public long getPlayTime() {
        return this.playTime;
    }

    public void pauseUpdater() {
        this.needPause = true;
    }

    public void resumeUpdater() {
        this.needPause = false;
    }

    public void setConfig(PlayerConfig playerConfig) {
        this.mConfig = playerConfig;
    }

    public void setStartPlayTime(long j2) {
        this.playTime = j2;
    }

    public void setUpdaterListener(AliLiveShiftPlayer.OnTimeShiftUpdaterListener onTimeShiftUpdaterListener) {
        this.timeShiftUpdaterListener = onTimeShiftUpdaterListener;
    }

    public void startUpdater() {
        stopUpdater();
        startUpdateLiveTimerDelay(0);
    }

    public void stopUpdater() {
        stopUpdateLiveTimer();
        stopUpdatePlayTimer();
    }
}
