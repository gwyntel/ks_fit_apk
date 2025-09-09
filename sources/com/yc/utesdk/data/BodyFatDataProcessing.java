package com.yc.utesdk.data;

import com.yc.utesdk.bean.BodyInfo;
import com.yc.utesdk.bean.BodySyncInfo;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class BodyFatDataProcessing {
    private static BodyFatDataProcessing instance;
    private BodySyncInfo bodySyncInfo;
    private List<BodySyncInfo> bodySyncInfoList = new ArrayList();
    private byte[] firstSectionData;
    private int tempBodyCRC;

    public static BodyFatDataProcessing getInstance() {
        if (instance == null) {
            instance = new BodyFatDataProcessing();
        }
        return instance;
    }

    public void dealWithBodyComposition(String str, byte[] bArr) {
        UteListenerManager uteListenerManager;
        int i2;
        String str2;
        int i3 = bArr[1] & 255;
        if (i3 == 0) {
            LogUtils.i("人体成分:结束测试");
            int i4 = bArr[2] & 255;
            if (i4 == 1) {
                LogUtils.i("人体成分:结束测试数据---第1段");
                this.firstSectionData = bArr;
                return;
            } else {
                if (i4 != 2) {
                    return;
                }
                LogUtils.i("人体成分:结束测试数据---第2段");
                BodyUtil.getInstance().AnalysisBleBodyTestData(this.firstSectionData, bArr);
                return;
            }
        }
        if (i3 == 1) {
            LogUtils.i("人体成分:测试过程中，从佩戴变为脱手");
            uteListenerManager = UteListenerManager.getInstance();
            i2 = 5;
        } else {
            if (i3 == 2) {
                LogUtils.i("人体成分:测试过程中，从脱手变为佩戴");
                UteListenerManager.getInstance().onBodyFatStatus(true, 6);
                return;
            }
            if (i3 == 17) {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                if (bArr.length >= 8) {
                    int i5 = bArr[7] & 255;
                    if (i5 == 0) {
                        LogUtils.i("人体成分:手机控制设备只设置参数");
                        UteListenerManager.getInstance().onBodyFatStatus(true, 1);
                        return;
                    } else if (i5 != 17) {
                        return;
                    } else {
                        str2 = "人体成分:手机控制设备开始测试2";
                    }
                } else {
                    str2 = "人体成分:手机控制设备开始测试1";
                }
                LogUtils.i(str2);
                UteListenerManager.getInstance().onBodyFatStatus(true, 2);
                return;
            }
            if (i3 == 170) {
                LogUtils.i("人体成分:查询当前设备测试状");
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                int i6 = bArr[2] & 255;
                if (i6 != 0) {
                    if (i6 != 17) {
                        return;
                    }
                    LogUtils.i("人体成分:表示正在测试");
                    UteListenerManager.getInstance().onBodyFatStatus(true, 8);
                    return;
                }
                LogUtils.i("人体成分:表示不测试");
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 9;
            } else {
                if (i3 == 250) {
                    if (str.length() != 6) {
                        for (byte b2 : bArr) {
                            this.tempBodyCRC ^= b2;
                        }
                        int i7 = bArr[2] & 255;
                        if (i7 == 1) {
                            LogUtils.i("人体成分:同步人体成分历史数据---第1段");
                            this.firstSectionData = bArr;
                            BodySyncInfo bodySyncInfo = new BodySyncInfo();
                            this.bodySyncInfo = bodySyncInfo;
                            bodySyncInfo.setFirstSectionData(this.firstSectionData);
                        } else if (i7 == 2) {
                            LogUtils.i("人体成分:同步人体成分历史数据---第2段");
                            this.bodySyncInfo.setSecondSectionData(bArr);
                            this.bodySyncInfoList.add(this.bodySyncInfo);
                        }
                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                        CommandTimeOutUtils.getInstance().setCommandTimeOut(39);
                        return;
                    }
                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                    int i8 = bArr[2] & 255;
                    LogUtils.i("同步完成，并给出检验结果 bleCrc =" + i8 + ",tempBodyCRC =" + (this.tempBodyCRC & 255));
                    if (i8 == (this.tempBodyCRC & 255)) {
                        this.tempBodyCRC = 0;
                        List<BodyInfo> listAnalysisBleBodyHistoryData = BodyUtil.getInstance().AnalysisBleBodyHistoryData(this.bodySyncInfoList);
                        ArrayList arrayList = new ArrayList();
                        this.bodySyncInfoList = arrayList;
                        arrayList.clear();
                        UteListenerManager.getInstance().onBodyFatSyncSuccess(listAnalysisBleBodyHistoryData);
                        return;
                    }
                    LogUtils.i("同步完成，并给出检验结果 检验失败");
                    this.tempBodyCRC = 0;
                    ArrayList arrayList2 = new ArrayList();
                    this.bodySyncInfoList = arrayList2;
                    arrayList2.clear();
                    UteListenerManager.getInstance().onBodyFatSyncFail();
                    return;
                }
                if (i3 != 253) {
                    return;
                }
                LogUtils.i("人体成分:设备测试脱手并结束");
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 4;
            }
        }
        uteListenerManager.onBodyFatStatus(true, i2);
    }
}
