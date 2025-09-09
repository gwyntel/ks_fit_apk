package com.yc.utesdk.data;

import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.google.common.base.Ascii;
import com.google.gson.Gson;
import com.yc.utesdk.bean.ActivityTimeInfo;
import com.yc.utesdk.bean.StandingTimeInfo;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.GBUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class StandingTimeProcessing {
    private static StandingTimeProcessing instance;
    private List<StandingTimeInfo> standingTimeInfos = new ArrayList();
    private List<ActivityTimeInfo> mActivityTimeInfos = new ArrayList();
    private int appActivityTimeCrc = 0;

    public static StandingTimeProcessing getInstance() {
        if (instance == null) {
            instance = new StandingTimeProcessing();
        }
        return instance;
    }

    public ActivityTimeInfo convertActivityTimeData(byte[] bArr) {
        int i2 = ((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[3] & 255);
        int i3 = bArr[4] & 255;
        int i4 = bArr[5] & 255;
        int i5 = bArr[6] & 255;
        Locale locale = Locale.US;
        ActivityTimeInfo activityTimeInfo = new ActivityTimeInfo(String.format(locale, "%1$04d%2$02d%3$02d", Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4)), String.format(locale, "%1$04d%2$02d%3$02d%4$02d", Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5)), i5, ((bArr[17] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[18] & 255), ((bArr[13] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[14] & 255), (bArr[15] & 255) + ((bArr[16] & 255) / 100.0f), ((bArr[7] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[8] & 255), ((bArr[9] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[10] & 255), ((bArr[11] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[12] & 255), ((bArr[19] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[20] & 255), ((bArr[21] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[22] & 255), ((bArr[23] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[24] & 255), ((bArr[25] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[26] & 255), ((bArr[27] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[28] & 255), ((bArr[29] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[30] & 255), ((bArr[31] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[32] & 255), ((bArr[33] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[34] & 255), ((bArr[35] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[36] & 255), ((bArr[37] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[38] & 255));
        LogUtils.i("ActivityTimeInfo =" + new Gson().toJson(activityTimeInfo));
        return activityTimeInfo;
    }

    public void dealWithActivityTimeData(byte[] bArr) {
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        int i2 = bArr[1] & 255;
        if (i2 == 251) {
            UteListenerManager.getInstance().onActivityTimeSyncing();
            CommandTimeOutUtils.getInstance().setCommandTimeOut(144);
            for (int i3 = 2; i3 < bArr.length; i3++) {
                this.appActivityTimeCrc ^= bArr[i3];
            }
            this.mActivityTimeInfos.add(convertActivityTimeData(bArr));
            return;
        }
        if (i2 != 253) {
            return;
        }
        boolean z2 = (bArr[2] & 255) == (this.appActivityTimeCrc & 255);
        LogUtils.i("crcResult =" + z2 + ",appActivityTimeCrc =" + (this.appActivityTimeCrc & 255) + ",bleActivityTimeCrc =" + (bArr[2] & 255));
        UteListenerManager uteListenerManager = UteListenerManager.getInstance();
        if (z2) {
            uteListenerManager.onActivityTimeSyncSuccess(this.mActivityTimeInfos);
        } else {
            uteListenerManager.onActivityTimeSyncFail();
        }
        this.mActivityTimeInfos = new ArrayList();
        this.appActivityTimeCrc = 0;
    }

    public void dealWithTodayTarget(byte[] bArr) {
        int i2 = bArr[2] & 255;
        if (i2 == 0 || i2 == 1) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            switch (bArr[1] & 255) {
                case 1:
                    UteListenerManager.getInstance().onTodayTargetStatus(true, 1);
                    break;
                case 2:
                    UteListenerManager.getInstance().onTodayTargetStatus(true, 2);
                    break;
                case 3:
                    UteListenerManager.getInstance().onTodayTargetStatus(true, 3);
                    break;
                case 4:
                    UteListenerManager.getInstance().onTodayTargetStatus(true, 4);
                    break;
                case 5:
                    UteListenerManager.getInstance().onTodayTargetStatus(true, 5);
                    break;
                case 6:
                    UteListenerManager.getInstance().onTodayTargetStatus(true, 6);
                    break;
            }
            return;
        }
        if (i2 != 2 || (bArr[1] & 255) != 1) {
            if ((bArr[1] & 255) == 170) {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                switch (bArr[2] & 255) {
                    case 1:
                        UteListenerManager.getInstance().onQueryTodayTarget(true, 1, (bArr[6] & 255) | ((bArr[5] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK));
                        break;
                    case 2:
                        UteListenerManager.getInstance().onQueryTodayTarget(true, 2, (bArr[6] & 255) | ((bArr[5] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK));
                        break;
                    case 3:
                        UteListenerManager.getInstance().onQueryTodayTarget(true, 3, (bArr[6] & 255) | ((bArr[5] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK));
                        break;
                    case 4:
                        UteListenerManager.getInstance().onQueryTodayTarget(true, 4, (bArr[8] & 255) | ((bArr[5] << Ascii.CAN) & ViewCompat.MEASURED_STATE_MASK) | ((bArr[6] << 16) & 16711680) | ((bArr[7] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK));
                        break;
                    case 5:
                        UteListenerManager.getInstance().onQueryTodayTarget(true, 5, (bArr[8] & 255) | ((bArr[5] << Ascii.CAN) & ViewCompat.MEASURED_STATE_MASK) | ((bArr[6] << 16) & 16711680) | ((bArr[7] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK));
                        break;
                    case 6:
                        UteListenerManager.getInstance().onQueryTodayTarget(true, 6, (bArr[6] & 255) | ((bArr[5] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK));
                        break;
                }
                return;
            }
            return;
        }
        if (bArr.length == 5 && (bArr[3] & 255) == 250) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().onStandingTimeSyncSuccess(this.standingTimeInfos);
            this.standingTimeInfos = new ArrayList();
            return;
        }
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        CommandTimeOutUtils.getInstance().setCommandTimeOut(95);
        String calendar = getCalendar(bArr);
        for (int i3 = 0; i3 < bArr.length - 7; i3 += 4) {
            int i4 = bArr[i3 + 7] & 255;
            int i5 = bArr[i3 + 8] & 255;
            int i6 = ((bArr[i3 + 9] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[i3 + 10] & 255);
            String startTime = getStartTime(calendar, i4, i5);
            String endTime = getEndTime(calendar, i4, i5, i6);
            LogUtils.i("standing history calendar=" + calendar + ",startTime=" + startTime + ",endTime=" + endTime + ",duration=" + i6);
            this.standingTimeInfos.add(new StandingTimeInfo(calendar, startTime, endTime, i6));
        }
        UteListenerManager.getInstance().onStandingTimeSyncing();
    }

    public String getCalendar(byte[] bArr) {
        int i2 = bArr[6] & 255;
        int i3 = bArr[5] & 255;
        int i4 = (bArr[4] & 255) | ((bArr[3] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
        String strValueOf = String.valueOf(i2);
        String strValueOf2 = String.valueOf(i3);
        if (i2 < 10) {
            strValueOf = "0" + i2;
        }
        if (i3 < 10) {
            strValueOf2 = "0" + i3;
        }
        return i4 + strValueOf2 + strValueOf;
    }

    public String getEndTime(String str, int i2, int i3, int i4) {
        int i5 = (i2 * 60) + i3 + i4;
        return getStartTime(str, i5 / 60, i5 % 60);
    }

    public String getStartTime(String str, int i2, int i3) {
        return str + GBUtils.getInstance().formatTwoCharacters(i2, i3);
    }

    public void resetCrc() {
        this.appActivityTimeCrc = 0;
    }
}
