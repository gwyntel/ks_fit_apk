package com.yc.utesdk.data;

import android.os.Message;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.google.common.base.Ascii;
import com.yc.utesdk.bean.CSBPDevicePmInfo;
import com.yc.utesdk.bean.CSBPHeartRateAndOxygenInfo;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.command.WriteCommandToBLE;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.watchface.open.WatchFaceUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public class CsbpCommandProcessing {
    private static CsbpCommandProcessing instance;
    private int csbpCRC;
    private int lastPmSn;
    private int pmSn;
    private String temCsbpPmData = "";
    private List<CSBPDevicePmInfo> mCSBPDevicePmInfoList = new ArrayList();
    private List<CSBPHeartRateAndOxygenInfo> mCSBPHeartRateAndOxygenInfoList = new ArrayList();
    private WriteCommandToBLE mWriteCommandToBLE = WriteCommandToBLE.getInstance();

    public static CsbpCommandProcessing getInstance() {
        if (instance == null) {
            instance = new CsbpCommandProcessing();
        }
        return instance;
    }

    public void dealWithcSBPCalibration(String str, byte[] bArr) {
        boolean z2;
        UteListenerManager uteListenerManager;
        int i2;
        String strSubstring;
        UteListenerManager uteListenerManager2;
        boolean z3;
        int i3 = bArr[1] & 255;
        if (i3 == 9) {
            if ((bArr[2] & 255) == 253) {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onSyncHeartRateAndOxygenSuccess(true, this.mCSBPHeartRateAndOxygenInfoList);
                this.mCSBPHeartRateAndOxygenInfoList = new ArrayList();
                return;
            } else {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                CommandTimeOutUtils.getInstance().setCommandTimeOut(160);
                this.mCSBPHeartRateAndOxygenInfoList.add(new CSBPHeartRateAndOxygenInfo(utendo(bArr), bArr[8] & 255, bArr[9] & 255));
                return;
            }
        }
        if (i3 == 17) {
            LogUtils.i("标定过程中设备返回实时血压波形");
            return;
        }
        if (i3 == 170) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            int i4 = bArr[2] & 255;
            if (i4 == 0) {
                LogUtils.i("已标定，不需要标定");
                UteListenerManager.getInstance().onCsbpStatus(true, 2);
                return;
            } else {
                if (i4 != 1) {
                    return;
                }
                LogUtils.i("未标定，需要标定");
                UteListenerManager.getInstance().onCsbpStatus(true, 3);
                return;
            }
        }
        if (i3 != 255) {
            switch (i3) {
                case 0:
                    if (bArr.length == 2) {
                        LogUtils.i("开始标定指令成功");
                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                        this.mCSBPDevicePmInfoList = new ArrayList();
                        this.lastPmSn = 0;
                        this.temCsbpPmData = "";
                        this.csbpCRC = 0;
                        UteListenerManager.getInstance().onCsbpStatus(true, 4);
                        return;
                    }
                    int i5 = bArr[2] & 255;
                    if (i5 == 250) {
                        for (int i6 = 5; i6 < bArr.length; i6++) {
                            this.csbpCRC ^= bArr[i6];
                        }
                        this.pmSn = bArr[3] & 255;
                        LogUtils.i("pmSn =" + this.pmSn + ",lastPmSn =" + this.lastPmSn + ",bleSn =" + (bArr[4] & 255));
                        if (this.lastPmSn == this.pmSn) {
                            strSubstring = this.temCsbpPmData + str.substring(10);
                        } else {
                            String str2 = this.temCsbpPmData;
                            this.mCSBPDevicePmInfoList.add(new CSBPDevicePmInfo(str2, str2.length() / 2));
                            strSubstring = str.substring(10);
                        }
                        this.temCsbpPmData = strSubstring;
                        this.lastPmSn = this.pmSn;
                        return;
                    }
                    if (i5 != 253) {
                        return;
                    }
                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                    if (bArr.length == 3) {
                        LogUtils.i("BLE端主动结束标定（标定过程中退出标定）0x3E00+FD");
                        UteListenerManager.getInstance().onCsbpStatus(false, 6);
                        return;
                    }
                    int i7 = bArr[3] & 255;
                    LogUtils.i("csbp bleCrc =" + i7 + ",csbpCRC =" + (this.csbpCRC & 255));
                    String str3 = this.temCsbpPmData;
                    this.mCSBPDevicePmInfoList.add(new CSBPDevicePmInfo(str3, str3.length() / 2));
                    if (i7 != (this.csbpCRC & 255)) {
                        LogUtils.i("标定结束,检验失败");
                        this.csbpCRC = 0;
                        this.lastPmSn = 0;
                        this.temCsbpPmData = "";
                        this.mCSBPDevicePmInfoList = new ArrayList();
                        UteListenerManager.getInstance().onCsbpStatus(false, 10);
                        return;
                    }
                    LogUtils.i("标定结束,检验成功");
                    this.csbpCRC = 0;
                    this.lastPmSn = 0;
                    this.temCsbpPmData = "";
                    this.mWriteCommandToBLE.cSBpAPPResponsePMCrcOKCommand(true);
                    if (this.mCSBPDevicePmInfoList.size() >= 5) {
                        UteListenerManager.getInstance().onDevicePmSuccess(this.mCSBPDevicePmInfoList);
                    } else {
                        LogUtils.i("返回的PM值不够5组 标定失败");
                        UteListenerManager.getInstance().onCsbpStatus(false, 10);
                    }
                    this.mCSBPDevicePmInfoList = new ArrayList();
                    return;
                case 1:
                    int i8 = bArr[2] & 255;
                    if (i8 == 250) {
                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                        int i9 = bArr[3] & 255;
                        LogUtils.i("BLE应答返回指令 COSn =" + i9);
                        this.mWriteCommandToBLE.NOsectionCSBpSendCalibrationCO = i9 + 1;
                        Message message = new Message();
                        Objects.requireNonNull(this.mWriteCommandToBLE);
                        message.what = 3;
                        this.mWriteCommandToBLE.mHandler.sendMessageDelayed(message, WatchFaceUtil.DELAY_SYNC_WATCH_FACE_DATA_TIME);
                        return;
                    }
                    if (i8 == 253) {
                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                        LogUtils.i("APP下发CO数据结束指令，校验成功");
                        uteListenerManager2 = UteListenerManager.getInstance();
                        z3 = true;
                    } else {
                        if (i8 != 255) {
                            return;
                        }
                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                        z3 = false;
                        this.mWriteCommandToBLE.NOsectionCSBpSendCalibrationCO = 0;
                        LogUtils.i("APP下发CO数据结束指令，校验失败，发出失败回调");
                        uteListenerManager2 = UteListenerManager.getInstance();
                    }
                    uteListenerManager2.onCsbpStatus(z3, 9);
                    return;
                case 2:
                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                    LogUtils.i("APP主动退出标定");
                    UteListenerManager.getInstance().onCsbpStatus(true, 5);
                    return;
                case 3:
                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                    LogUtils.i("设置高血压和服药信息成功");
                    UteListenerManager.getInstance().onCsbpStatus(true, 7);
                    return;
                case 4:
                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                    LogUtils.i("清除BLE标定信息");
                    UteListenerManager.getInstance().onCsbpStatus(true, 8);
                    return;
                case 5:
                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                    int i10 = bArr[2] & 255;
                    if (i10 == 170) {
                        int i11 = bArr[3] & 255;
                        if (i11 == 0) {
                            LogUtils.i("csbp 设备未激活");
                            UteListenerManager.getInstance().onCsbpStatus(true, 16);
                            return;
                        }
                        z2 = true;
                        if (i11 == 1) {
                            LogUtils.i("csbp 设备已激活");
                            String strSubstring2 = str.substring(8);
                            LogUtils.i("csbp 设备已激活 deviceSn = " + strSubstring2);
                            UteListenerManager.getInstance().onDeviceSnSuccess(strSubstring2);
                            return;
                        }
                        if (i11 == 255) {
                            LogUtils.i("设备不支持激活功能");
                            uteListenerManager = UteListenerManager.getInstance();
                            i2 = 17;
                            break;
                        } else {
                            return;
                        }
                    } else {
                        if (i10 == 1) {
                            LogUtils.i("获取长桑血压模块ID");
                            int i12 = bArr[3] & 255;
                            String strSubstring3 = str.substring(8);
                            LogUtils.i("csbp 获取长桑血压模块ID moduleId = " + strSubstring3);
                            UteListenerManager.getInstance().onQueryDeviceModuleIdSuccess(i12, strSubstring3);
                            return;
                        }
                        if (i10 == 2) {
                            LogUtils.i("给BLE下发激活码ok");
                            LogUtils.i("给BLE下发激活码ok activateCodeSize =" + (bArr[3] & 255));
                            uteListenerManager = UteListenerManager.getInstance();
                            i2 = 19;
                            z2 = true;
                            break;
                        } else {
                            return;
                        }
                    }
                case 6:
                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                    int i13 = bArr[3] & 255;
                    int i14 = ((bArr[4] << Ascii.CAN) & ViewCompat.MEASURED_STATE_MASK) | ((bArr[5] << 16) & 16711680) | ((bArr[6] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[7] & 255);
                    String str4 = (bArr[8] & 255) + "." + (bArr[9] & 255) + "." + (bArr[10] & 255);
                    LogUtils.i("芯片型号 chip=" + i13 + ",dataSource=" + i14 + ",bpFirmwareVersion=" + str4);
                    UteListenerManager.getInstance().onQueryDeviceChipSuccess(i13, i14, str4);
                    return;
                default:
                    return;
            }
        } else {
            LogUtils.i("标定过程中BLE端测试异常 " + (bArr[2] & 255));
            int i15 = bArr[2] & 255;
            if (i15 == 1) {
                z2 = false;
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 11;
            } else if (i15 == 2) {
                z2 = false;
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 12;
            } else {
                if (i15 != 3) {
                    return;
                }
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 13;
                z2 = false;
            }
        }
        uteListenerManager.onCsbpStatus(z2, i2);
    }

    public final String utendo(byte[] bArr) {
        int i2 = bArr[5] & 255;
        int i3 = bArr[4] & 255;
        int i4 = ((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[3] & 255);
        int i5 = bArr[6] & 255;
        int i6 = bArr[7] & 255;
        String strValueOf = String.valueOf(i2);
        String strValueOf2 = String.valueOf(i3);
        String strValueOf3 = String.valueOf(i5);
        String strValueOf4 = String.valueOf(i6);
        if (i2 < 10) {
            strValueOf = "0" + i2;
        }
        if (i3 < 10) {
            strValueOf2 = "0" + i3;
        }
        if (i5 < 10) {
            strValueOf3 = "0" + i5;
        }
        if (i6 < 10) {
            strValueOf4 = "0" + i6;
        }
        return i4 + strValueOf2 + strValueOf + strValueOf3 + strValueOf4;
    }
}
