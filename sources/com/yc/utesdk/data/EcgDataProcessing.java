package com.yc.utesdk.data;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import androidx.core.view.MotionEventCompat;
import com.google.gson.Gson;
import com.yc.utesdk.bean.EcgInfo;
import com.yc.utesdk.ble.close.DeviceBusyLockUtils;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.command.WriteCommandToBLE;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.close.EcgUtil;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class EcgDataProcessing {
    private static EcgDataProcessing instance;
    private byte[] lastHistoryReturnData;
    private Handler mHandler;
    private int tempEcgCRC;
    private StringBuilder ecgSamplingDataBuilder = new StringBuilder();
    private EcgInfo mEcgInfo1 = null;
    public List<EcgInfo> mEcgInfoList = new ArrayList();
    public List<EcgInfo> mEcgInfoListAll = new ArrayList();
    private EcgInfo mEcgInfoSamplingTemp = new EcgInfo();
    public List<EcgInfo> mEcgSamplingListTemp = new ArrayList();
    private final int delayWriteTime20 = 20;
    private final int WRITE_PUSH_COMMAND_MSG = 0;
    private final int SOS_CALL_SECTION_SYNCING = 16;

    public class utendo extends Handler {
        public utendo(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 0) {
                return;
            }
            if (!DeviceBusyLockUtils.getInstance().getDeviceBusy()) {
                EcgDataProcessing.this.utendo(message.arg1, message.arg2);
                return;
            }
            LogUtils.i("设备忙，等20ms");
            Message message2 = new Message();
            message2.what = 0;
            message2.arg1 = message.arg1;
            message2.arg2 = message.arg2;
            EcgDataProcessing.this.mHandler.sendMessageDelayed(message2, 20L);
        }
    }

    public EcgDataProcessing() {
        utendo();
    }

    public static EcgDataProcessing getInstance() {
        if (instance == null) {
            instance = new EcgDataProcessing();
        }
        return instance;
    }

    public void dealWithEcg(String str, byte[] bArr) {
        StringBuilder sb;
        switch (bArr[1] & 255) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                this.ecgSamplingDataBuilder.append(str.substring(4));
                UteListenerManager.getInstance().onEcgRealTimeData(EcgUtil.getInstance().dealEcgSamplingData(this.ecgSamplingDataBuilder.toString()));
                return;
            case 10:
                LogUtils.i("心电:结束测试");
                byte b2 = bArr[2];
                if (b2 == 0) {
                    LogUtils.i("心电:结束测试,数据无效,本次数据不保存");
                    UteListenerManager.getInstance().onEcgStatus(true, 2);
                } else if (b2 == 17) {
                    LogUtils.i("心电:结束测试,数据有效");
                    UteListenerManager.getInstance().onEcgRealTime(new EcgInfo(EcgUtil.getInstance().AnalysisBleEcgRealTimeData(bArr, this.ecgSamplingDataBuilder.toString()), this.ecgSamplingDataBuilder.toString()));
                }
                sb = new StringBuilder();
                break;
            case 11:
                if ((bArr[2] & 255) != 253) {
                    this.mEcgInfoListAll.add(EcgUtil.getInstance().AnalysisBleEcgHistoryData(bArr));
                    return;
                }
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                this.ecgSamplingDataBuilder = new StringBuilder();
                UteListenerManager.getInstance().onEcgSyncSuccess(true, 6, this.mEcgInfoListAll);
                this.mEcgInfoListAll = new ArrayList();
                return;
            case 12:
                int i2 = bArr[2] & 255;
                if (i2 == 253) {
                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                    UteListenerManager.getInstance().onEcgSyncSuccess(true, 7, this.mEcgSamplingListTemp);
                    this.mEcgSamplingListTemp = new ArrayList();
                    byte b3 = bArr[2];
                    this.tempEcgCRC = 0;
                    return;
                }
                UteListenerManager.getInstance().onEcgSyncing();
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                CommandTimeOutUtils.getInstance().setCommandTimeOut(176);
                for (int i3 = 2; i3 < bArr.length; i3++) {
                    this.tempEcgCRC ^= bArr[i3];
                }
                int i4 = bArr[3] & 255;
                int i5 = bArr[4] & 255;
                int i6 = 255 & bArr[5];
                LogUtils.i("ECG采样 总条数=" + i2 + ",，序号=" + i4 + "，总包=" + i5 + ",包=" + i6);
                if (i6 == 1) {
                    this.mEcgInfoSamplingTemp.setCalendarTime(EcgUtil.getInstance().getCalendarTime2(bArr));
                    this.ecgSamplingDataBuilder.append(str.substring(24));
                    return;
                }
                this.ecgSamplingDataBuilder.append(str.substring(10));
                if (i6 == i5) {
                    this.mEcgInfoSamplingTemp.setRealEcgValueArray(new Gson().toJson(EcgUtil.getInstance().dealEcgSamplingData(this.ecgSamplingDataBuilder.toString())));
                    this.mEcgInfoSamplingTemp.setEcgSamplingData(this.ecgSamplingDataBuilder.toString());
                    this.mEcgSamplingListTemp.add(this.mEcgInfoSamplingTemp);
                    this.mEcgInfoSamplingTemp = new EcgInfo();
                    sb = new StringBuilder();
                    break;
                } else {
                    return;
                }
            case 13:
                LogUtils.i("心电测试过程中，从佩戴变为脱手");
                UteListenerManager.getInstance().onEcgStatus(true, 5);
                return;
            case 14:
                LogUtils.i("心电测试过程中，从脱手变为佩戴");
                UteListenerManager.getInstance().onEcgStatus(true, 4);
                return;
            case 15:
                LogUtils.i("获取心电采样频率");
                int i7 = ((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[3] & 255);
                int i8 = bArr[4] & 255;
                EcgUtil.ecgHZ = i7;
                EcgUtil.ecgFilterCnt = i7 * i8;
                LogUtils.i("获取心电采样频率 hz=" + i7 + ",filterTime=" + i8 + ",ecgFilterCnt=" + EcgUtil.ecgFilterCnt);
                UteListenerManager.getInstance().onEcgSamplingFrequency(i7, i8);
                return;
            case 16:
                LogUtils.i("心电:手机控制设备开始测试");
                this.ecgSamplingDataBuilder = new StringBuilder();
                UteListenerManager.getInstance().onEcgStatus(true, 1);
                return;
            case 17:
                LogUtils.i("心电实时心率 ecgRate=" + (bArr[2] & 255));
                return;
            case 18:
                int i9 = bArr[2] & 255;
                if (i9 == 255) {
                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                    UteListenerManager.getInstance().onEcgStatus(true, 8);
                    return;
                } else if (i9 == 253) {
                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                    UteListenerManager.getInstance().onEcgStatus((bArr[3] & 255) == 0, 9);
                    return;
                } else {
                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                    CommandTimeOutUtils.getInstance().setCommandTimeOut(231);
                    utendo(bArr[2] & 255);
                    return;
                }
            default:
                return;
        }
        this.ecgSamplingDataBuilder = sb;
    }

    public final void utendo() {
        if (this.mHandler == null) {
            HandlerThread handlerThread = new HandlerThread("HandlerThread");
            handlerThread.start();
            this.mHandler = new utendo(handlerThread.getLooper());
        }
    }

    public final void utendo(int i2, int i3) {
        if (i2 != 16) {
            return;
        }
        WriteCommandToBLE.getInstance().ecgParsingSuccessSegments(i3);
    }

    public final void utendo(int i2) {
        Message message = new Message();
        message.what = 0;
        message.arg1 = 16;
        message.arg2 = i2;
        this.mHandler.sendMessage(message);
    }
}
