package com.yc.utesdk.data;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import androidx.core.view.MotionEventCompat;
import com.google.gson.Gson;
import com.yc.utesdk.bean.SportsDataInfo;
import com.yc.utesdk.bean.SportsModeInfo;
import com.yc.utesdk.ble.close.DeviceBusyLockUtils;
import com.yc.utesdk.ble.open.DeviceMode;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.command.MultiSportsModesUtils;
import com.yc.utesdk.command.WriteCommandToBLE;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class MultiSportsProcessing {
    private static MultiSportsProcessing instance;
    private Handler mHandler;
    private byte[] heartRateByte = new byte[0];
    private boolean isSportsModesSyncing = false;
    private int sportTimes = 0;
    private SportsDataInfo mSportsDataInfo = new SportsDataInfo();

    /* renamed from: a, reason: collision with root package name */
    List f24899a = new ArrayList();
    private boolean notifyStatus = true;

    /* renamed from: b, reason: collision with root package name */
    List f24900b = new ArrayList();
    private List<SportsDataInfo> mSwimDataList = new ArrayList();
    private MultiSportsModesUtils utils = MultiSportsModesUtils.getInstance();

    public class utendo extends Handler {
        public utendo(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 1) {
                if (!DeviceBusyLockUtils.getInstance().getDeviceBusy()) {
                    WriteCommandToBLE.getInstance().sendSportManagementListsSection();
                    return;
                }
                LogUtils.i("设备忙，等20ms");
                MultiSportsProcessing.this.mHandler.sendEmptyMessageDelayed(1, (long) 20);
            }
        }
    }

    public MultiSportsProcessing() {
        utendo();
    }

    public static MultiSportsProcessing getInstance() {
        if (instance == null) {
            instance = new MultiSportsProcessing();
        }
        return instance;
    }

    public void dealWithCyweeSwimData(String str, byte[] bArr) throws NumberFormatException {
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        if ((bArr[2] & 255) == 253) {
            LogUtils.i("CYWEE SWIM 同步完成");
            WriteCommandToBLE.getInstance().cyweeSwimDataSyncFinish(true);
            return;
        }
        CommandTimeOutUtils.getInstance().setCommandTimeOut(192);
        String swimStartTime = this.utils.getSwimStartTime(bArr);
        String strSubstring = swimStartTime.substring(0, 8);
        int swimStrokeTimes = this.utils.getSwimStrokeTimes(bArr);
        int swimStrokeFrequency = this.utils.getSwimStrokeFrequency(bArr);
        int swimAvgStrokeFrequency = this.utils.getSwimAvgStrokeFrequency(bArr);
        int swimSwolf = this.utils.getSwimSwolf(bArr);
        int swimBestSwolf = this.utils.getSwimBestSwolf(bArr);
        int swimAvgSwolf = this.utils.getSwimAvgSwolf(bArr);
        int swimLaps = this.utils.getSwimLaps(bArr);
        int swimType = this.utils.getSwimType(bArr);
        LogUtils.i("CYWEE SWIM startTime=" + swimStartTime + ",calendar=" + strSubstring + ",strokeTimes=" + swimStrokeTimes + ",strokeFrequency=" + swimStrokeFrequency + ",avgStrokeFrequency=" + swimAvgStrokeFrequency + ",swolf=" + swimSwolf + ",bestSwolf=" + swimBestSwolf + ",avgSwolf=" + swimAvgSwolf + ",laps=" + swimLaps + ",swimType=" + swimType);
        SportsDataInfo sportsDataInfo = new SportsDataInfo();
        sportsDataInfo.setStartDate(swimStartTime);
        sportsDataInfo.setCalendar(strSubstring);
        sportsDataInfo.setSportsMode(4);
        sportsDataInfo.setSportsCount(swimStrokeTimes);
        sportsDataInfo.setSwimStrokeTimes(swimStrokeTimes);
        sportsDataInfo.setSwimStrokeFrequency(swimStrokeFrequency);
        sportsDataInfo.setSwimAverageStrokeFrequency(swimAvgStrokeFrequency);
        sportsDataInfo.setSwimSwolf(swimSwolf);
        sportsDataInfo.setSwimBestSwolf(swimBestSwolf);
        sportsDataInfo.setSwimAverageSwolf(swimAvgSwolf);
        sportsDataInfo.setSwimLaps(swimLaps);
        sportsDataInfo.setSwimType(swimType);
        this.mSwimDataList.add(sportsDataInfo);
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x0136  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void dealWithMultiSportsMode(java.lang.String r17, byte[] r18) {
        /*
            Method dump skipped, instructions count: 1436
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.data.MultiSportsProcessing.dealWithMultiSportsMode(java.lang.String, byte[]):void");
    }

    public void dealWithSportTarget(byte[] bArr) {
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        int i2 = bArr[1] & 255;
        if (i2 == 1) {
            int i3 = bArr[2] & 255;
            if (i3 == 253) {
                UteListenerManager.getInstance().onMultiSportTargetStatus(true, 1);
                return;
            } else {
                if (i3 == 255) {
                    UteListenerManager.getInstance().onMultiSportTargetStatus(false, 1);
                    return;
                }
                return;
            }
        }
        if (i2 == 2) {
            int i4 = bArr[2] & 255;
            if (i4 == 253) {
                UteListenerManager.getInstance().onMultiSportTargetStatus(true, 2);
                return;
            } else {
                if (i4 == 255) {
                    UteListenerManager.getInstance().onMultiSportTargetStatus(false, 2);
                    return;
                }
                return;
            }
        }
        int i5 = 3;
        if (i2 == 3) {
            int i6 = bArr[2] & 255;
            if (i6 != 253) {
                if (i6 != 255) {
                    return;
                }
                UteListenerManager.getInstance().onMultiSportTargetStatus(false, i5);
                return;
            }
            UteListenerManager.getInstance().onMultiSportTargetStatus(true, i5);
            return;
        }
        i5 = 4;
        if (i2 == 4) {
            int i7 = bArr[2] & 255;
            if (i7 != 253) {
                if (i7 != 255) {
                    return;
                }
                UteListenerManager.getInstance().onMultiSportTargetStatus(false, i5);
                return;
            }
            UteListenerManager.getInstance().onMultiSportTargetStatus(true, i5);
            return;
        }
        if (i2 != 5) {
            return;
        }
        int i8 = bArr[2] & 255;
        if (i8 == 253) {
            UteListenerManager.getInstance().onHeartRateStatus(true, 6);
        } else if (i8 == 255) {
            UteListenerManager.getInstance().onHeartRateStatus(false, 6);
        }
    }

    public void setNotifyStatus(boolean z2) {
        this.notifyStatus = z2;
    }

    public final void utenif() {
        this.mHandler.sendEmptyMessage(1);
    }

    public final byte[] utendo(byte[] bArr, byte[] bArr2) {
        if (bArr2 == null || bArr2.length <= 3) {
            return bArr;
        }
        byte[] bArr3 = new byte[(bArr.length + bArr2.length) - 3];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 3, bArr3, bArr.length, bArr2.length - 3);
        return bArr3;
    }

    public final void utendo() {
        if (this.mHandler == null) {
            HandlerThread handlerThread = new HandlerThread("HandlerThread");
            handlerThread.start();
            this.mHandler = new utendo(handlerThread.getLooper());
        }
    }

    public final void utendo(SportsDataInfo sportsDataInfo) {
        List<SportsDataInfo> list = this.mSwimDataList;
        if (list == null || list.isEmpty() || sportsDataInfo.getSportsMode() != 4 || !DeviceMode.isHasFunction_9(512)) {
            return;
        }
        LogUtils.i("CYWEE SWIM 插入前 =" + new Gson().toJson(sportsDataInfo));
        Iterator<SportsDataInfo> it = this.mSwimDataList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            SportsDataInfo next = it.next();
            if (sportsDataInfo.getStartDate().equals(next.getStartDate())) {
                sportsDataInfo.setSportsCount(next.getSportsCount());
                sportsDataInfo.setSwimStrokeTimes(next.getSwimStrokeTimes());
                sportsDataInfo.setSwimStrokeFrequency(next.getSwimStrokeFrequency());
                sportsDataInfo.setSwimAverageStrokeFrequency(next.getSwimAverageStrokeFrequency());
                sportsDataInfo.setSwimSwolf(next.getSwimSwolf());
                sportsDataInfo.setSwimBestSwolf(next.getSwimBestSwolf());
                sportsDataInfo.setSwimAverageSwolf(next.getSwimAverageSwolf());
                sportsDataInfo.setSwimLaps(next.getSwimLaps());
                sportsDataInfo.setSwimType(next.getSwimType());
                break;
            }
        }
        LogUtils.i("CYWEE SWIM 插入后 =" + new Gson().toJson(sportsDataInfo));
    }

    public final void utendo(byte[] bArr, boolean z2) {
        if (!z2) {
            if ((bArr[3] & 255) == 0) {
                this.f24900b = new ArrayList();
            }
            for (int i2 = 4; i2 < bArr.length; i2 += 3) {
                this.f24900b.add(new SportsModeInfo(bArr[i2] & 255, bArr[i2 + 1] & 255, bArr[i2 + 2] & 255));
            }
            return;
        }
        int i3 = (bArr[6] & 255) | ((bArr[5] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
        int i4 = ((bArr[7] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[8] & 255);
        LogUtils.d("maxSet =" + i4 + ",minSet =" + i3);
        StringBuilder sb = new StringBuilder();
        sb.append("mSportsModeInfoList =");
        sb.append(new Gson().toJson(this.f24900b));
        LogUtils.d(sb.toString());
        UteListenerManager.getInstance().onQuerySportsModeList(true, i3, i4, this.f24900b);
        this.f24900b = new ArrayList();
    }

    public final void utendo(int i2, int i3) {
        if (this.notifyStatus) {
            UteListenerManager.getInstance().onSportStatusChange(i2, i3);
        } else {
            this.notifyStatus = true;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0591  */
    /* JADX WARN: Removed duplicated region for block: B:127:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0237  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x027f  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x02d6  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0381  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x03d4  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0479  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x04da  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void utendo(com.yc.utesdk.bean.SportsDataInfo r31, byte[] r32) {
        /*
            Method dump skipped, instructions count: 1542
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.data.MultiSportsProcessing.utendo(com.yc.utesdk.bean.SportsDataInfo, byte[]):void");
    }
}
