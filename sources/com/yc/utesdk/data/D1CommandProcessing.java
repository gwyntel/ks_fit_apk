package com.yc.utesdk.data;

import androidx.core.view.MotionEventCompat;
import com.yc.utesdk.bean.DoNotDisturbInfo;
import com.yc.utesdk.bean.DrinkWaterRemindInfo;
import com.yc.utesdk.bean.MenstrualParam;
import com.yc.utesdk.bean.SedentaryRemindInfo;
import com.yc.utesdk.ble.open.DeviceMode;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.command.WriteCommandToBLE;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.close.DataUtil;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class D1CommandProcessing {
    private static D1CommandProcessing instance;

    public static D1CommandProcessing getInstance() {
        if (instance == null) {
            instance = new D1CommandProcessing();
        }
        return instance;
    }

    public void dealWithBTDisconnectReminder(byte[] bArr) {
        UteListenerManager uteListenerManager;
        int i2;
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        int i3 = bArr[1] & 255;
        if (i3 == 0) {
            UteListenerManager.getInstance().onBlutoothDisconnectReminder(true, 2);
            return;
        }
        if (i3 == 1) {
            UteListenerManager.getInstance().onBlutoothDisconnectReminder(true, 1);
            return;
        }
        if (i3 != 170) {
            return;
        }
        int i4 = bArr[2] & 255;
        if (i4 == 1) {
            uteListenerManager = UteListenerManager.getInstance();
            i2 = 4;
        } else {
            if (i4 != 0) {
                return;
            }
            uteListenerManager = UteListenerManager.getInstance();
            i2 = 5;
        }
        uteListenerManager.onBlutoothDisconnectReminder(true, i2);
    }

    public void dealWithC1Command(byte[] bArr) {
        if ((bArr[1] & 255) != 4) {
            return;
        }
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        UteListenerManager.getInstance().onCallRemindStatus(true, 1003);
    }

    public void dealWithCustomFunctionSwitch(String str, byte[] bArr) {
        UteListenerManager uteListenerManager;
        int i2;
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        int i3 = bArr[1] & 255;
        if (i3 != 1) {
            if (i3 != 170) {
                return;
            }
            if ((bArr[3] & 255) == 1) {
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 34;
            } else {
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 35;
            }
        } else if ((bArr[2] & 255) == 1) {
            uteListenerManager = UteListenerManager.getInstance();
            i2 = 36;
        } else {
            uteListenerManager = UteListenerManager.getInstance();
            i2 = 37;
        }
        uteListenerManager.onSimpleCallback(true, i2);
    }

    public void dealWithD1Command(byte[] bArr) {
        UteListenerManager uteListenerManager;
        int i2;
        UteListenerManager uteListenerManager2;
        int i3;
        switch (bArr[1] & 255) {
            case 1:
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 9;
                uteListenerManager.onSimpleCallback(true, i2);
                break;
            case 2:
                uteListenerManager2 = UteListenerManager.getInstance();
                i3 = 1004;
                uteListenerManager2.onCallRemindStatus(true, i3);
                break;
            case 3:
                UteListenerManager.getInstance().onDeviceCameraStatus(true, 5);
                break;
            case 4:
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 10;
                uteListenerManager.onSimpleCallback(true, i2);
                break;
            case 5:
                UteListenerManager.getInstance().onSimpleCallback(true, 7);
                break;
            case 6:
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 11;
                uteListenerManager.onSimpleCallback(true, i2);
                break;
            case 7:
                UteListenerManager.getInstance().onDeviceMusicStatus(true, 2);
                break;
            case 8:
                UteListenerManager.getInstance().onDeviceMusicStatus(true, 6);
                break;
            case 9:
                UteListenerManager.getInstance().onDeviceMusicStatus(true, 7);
                break;
            case 10:
                if (bArr.length == 2) {
                    uteListenerManager = UteListenerManager.getInstance();
                    i2 = 8;
                } else if (bArr.length == 3) {
                    int i4 = bArr[2] & 255;
                    if (i4 == 1 || i4 == 0) {
                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                        uteListenerManager = UteListenerManager.getInstance();
                        i2 = 15;
                    }
                } else if (bArr.length == 4) {
                    int i5 = bArr[3] & 255;
                    if (i5 == 0) {
                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                        uteListenerManager = UteListenerManager.getInstance();
                        i2 = 17;
                    } else if (i5 == 253) {
                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                        uteListenerManager = UteListenerManager.getInstance();
                        i2 = 16;
                    }
                }
                uteListenerManager.onSimpleCallback(true, i2);
                break;
            case 12:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onDeviceMusicStatus(true, 1);
                break;
            case 13:
                UteListenerManager.getInstance().onDeviceMusicStatus(true, 4);
                break;
            case 14:
                UteListenerManager.getInstance().onDeviceMusicStatus(true, 5);
                break;
            case 15:
                UteListenerManager.getInstance().onDeviceCameraStatus(true, 3);
                break;
            case 17:
                UteListenerManager.getInstance().onDeviceCameraStatus(true, 4);
                break;
            case 18:
                if (bArr.length == 3) {
                    int i6 = bArr[2] & 255;
                    if (i6 == 0) {
                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                        uteListenerManager2 = UteListenerManager.getInstance();
                        i3 = 1000;
                    } else if (i6 == 1) {
                        uteListenerManager2 = UteListenerManager.getInstance();
                        i3 = 1002;
                    } else if (i6 == 2) {
                        uteListenerManager2 = UteListenerManager.getInstance();
                        i3 = 1001;
                    }
                    uteListenerManager2.onCallRemindStatus(true, i3);
                    break;
                }
                break;
        }
    }

    public void dealWithD3Command(byte[] bArr) {
        int i2 = bArr[1] & 255;
        if (i2 == 0 || i2 == 1) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().onSimpleCallback(true, 18);
        } else {
            if (i2 != 250) {
                return;
            }
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().onQueryDeviceParam(false, 100, utenif(bArr));
        }
    }

    public void dealWithD7Command(byte[] bArr) {
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        if ((bArr[1] & 255) == 250) {
            UteListenerManager.getInstance().onQueryDeviceParam(true, 102, getDoNotDisturbInfo(bArr));
            return;
        }
        int i2 = bArr[6] & 255;
        if (i2 == 0 || i2 == 1) {
            UteListenerManager.getInstance().onSimpleCallback(true, 21);
        }
    }

    public void dealWithDeviceOnScreen61(String str, byte[] bArr) {
        UteListenerManager uteListenerManager;
        int i2;
        int i3 = bArr[1] & 255;
        if (i3 == 0) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            uteListenerManager = UteListenerManager.getInstance();
            i2 = 31;
        } else {
            if (i3 != 1) {
                return;
            }
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            uteListenerManager = UteListenerManager.getInstance();
            i2 = 30;
        }
        uteListenerManager.onSimpleCallback(true, i2);
    }

    public void dealWithDevicePowerSavingMode(String str, byte[] bArr) {
        int i2 = bArr[1] & 255;
        if (i2 == 0 || i2 == 1) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().onSimpleCallback(true, 32);
        }
    }

    public void dealWithDeviceRestart(String str, byte[] bArr) {
        UteListenerManager uteListenerManager;
        int i2;
        int i3 = bArr[1] & 255;
        if (i3 == 17) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            uteListenerManager = UteListenerManager.getInstance();
            i2 = 28;
        } else {
            if (i3 != 18) {
                return;
            }
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            uteListenerManager = UteListenerManager.getInstance();
            i2 = 29;
        }
        uteListenerManager.onSimpleCallback(true, i2);
    }

    public void dealWithDrinkWater(byte[] bArr) {
        int i2 = bArr[1] & 255;
        if (i2 == 0 || i2 == 1) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().onSimpleCallback(true, 19);
        } else {
            if (i2 != 250) {
                return;
            }
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().onQueryDeviceParam(true, 101, utendo(bArr));
        }
    }

    public void dealWithFemaleMenstrualCycle(byte[] bArr) {
        UteListenerManager uteListenerManager;
        int i2;
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        int i3 = bArr[1] & 255;
        if (i3 == 1 || i3 == 0) {
            uteListenerManager = UteListenerManager.getInstance();
            i2 = 23;
        } else {
            if (i3 == 250) {
                UteListenerManager.getInstance().onMenstrualParam(true, new MenstrualParam((bArr[2] & 255) == 1, DataUtil.getInstance().startMenstrualCalendar(bArr), bArr[7] & 255, bArr[8] & 255));
                return;
            }
            if (i3 == 170) {
                ArrayList arrayList = new ArrayList();
                if (bArr.length > 3) {
                    int i4 = bArr[3] & 255;
                    byte[] bArr2 = new byte[i4];
                    System.arraycopy(bArr, 4, bArr2, 0, i4);
                    for (int i5 = 0; i5 < i4; i5++) {
                        arrayList.add(Integer.valueOf(bArr2[i5] & 255));
                    }
                }
                UteListenerManager.getInstance().onMenstrualMonthData(true, arrayList);
                return;
            }
            if (i3 != 171) {
                return;
            }
            int i6 = bArr[2] & 255;
            if (i6 == 1) {
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 39;
            } else {
                if (i6 != 2) {
                    return;
                }
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 40;
            }
        }
        uteListenerManager.onSimpleCallback(true, i2);
    }

    public void dealWithWashHands(byte[] bArr) {
        int i2 = bArr[1] & 255;
        if (i2 == 0 || i2 == 1) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().onSimpleCallback(true, 20);
        }
    }

    public void dealwithMusicControl(byte[] bArr) {
        int i2 = bArr[1] & 255;
        if (i2 == 7) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().onDeviceMusicStatus(true, 9);
        } else {
            if (i2 != 250) {
                return;
            }
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            if (WriteCommandToBLE.musicControlCRC == bArr[2]) {
                LogUtils.i("歌曲名校验成功");
                UteListenerManager.getInstance().onDeviceMusicStatus(true, 8);
            } else {
                LogUtils.i("歌曲名校验失败");
                UteListenerManager.getInstance().onDeviceMusicStatus(false, 8);
            }
            WriteCommandToBLE.musicControlCRC = 0;
        }
    }

    public void doCancelUpgrade01C8(byte[] bArr) {
        if (((bArr[3] & 255) | ((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) != 43777) {
            return;
        }
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        UteListenerManager.getInstance().onSimpleCallback(true, 276);
    }

    public DoNotDisturbInfo getDoNotDisturbInfo(byte[] bArr) {
        byte b2 = bArr[1];
        int i2 = (b2 & 8) == 8 ? 1 : 0;
        int i3 = (b2 & 4) == 4 ? 1 : 0;
        int i4 = (b2 & 2) != 2 ? 0 : 1;
        int i5 = bArr[3] & 255;
        int i6 = bArr[4] & 255;
        int i7 = bArr[5] & 255;
        int i8 = bArr[6] & 255;
        int i9 = bArr[7] & 255;
        DoNotDisturbInfo doNotDisturbInfo = new DoNotDisturbInfo();
        doNotDisturbInfo.setEndCallSwitch(i2);
        doNotDisturbInfo.setMessageSwitch(i3);
        doNotDisturbInfo.setMotorSwitch(i4);
        doNotDisturbInfo.setDisturbTimeSwitch(i9);
        doNotDisturbInfo.setFromTimeHour(i5);
        doNotDisturbInfo.setFromTimeMinute(i6);
        doNotDisturbInfo.setToTimeHour(i7);
        doNotDisturbInfo.setToTimeMinute(i8);
        return doNotDisturbInfo;
    }

    public final DrinkWaterRemindInfo utendo(byte[] bArr) {
        DrinkWaterRemindInfo drinkWaterRemindInfo = new DrinkWaterRemindInfo();
        int i2 = bArr[2] & 255;
        int i3 = bArr[3] & 255;
        drinkWaterRemindInfo.setRemindSwitch(i2);
        int i4 = bArr[8] & 255;
        int i5 = bArr[9] & 255;
        int i6 = bArr[10] & 255;
        int i7 = bArr[11] & 255;
        drinkWaterRemindInfo.setFromTimeHour(i4);
        drinkWaterRemindInfo.setFromTimeMinute(i5);
        drinkWaterRemindInfo.setToTimeHour(i6);
        drinkWaterRemindInfo.setToTimeMinute(i7);
        drinkWaterRemindInfo.setRemindInterval(i3);
        drinkWaterRemindInfo.setLunchBreakNoRemind(bArr[12] & 255);
        if (DeviceMode.isHasFunction_6(65536)) {
            int i8 = bArr[13] & 255;
            int i9 = bArr[14] & 255;
            int i10 = bArr[15] & 255;
            int i11 = bArr[16] & 255;
            drinkWaterRemindInfo.setNoRemindFromTimeHour(i8);
            drinkWaterRemindInfo.setNoRemindFromTimeMinute(i9);
            drinkWaterRemindInfo.setNoRemindToTimeHour(i10);
            drinkWaterRemindInfo.setNoRemindToTimeMinute(i11);
        }
        return drinkWaterRemindInfo;
    }

    public final SedentaryRemindInfo utenif(byte[] bArr) {
        SedentaryRemindInfo sedentaryRemindInfo = new SedentaryRemindInfo();
        int i2 = bArr[2] & 255;
        int i3 = bArr[3] & 255;
        sedentaryRemindInfo.setRemindSwitch(i2);
        if (DeviceMode.isHasFunction_2(16)) {
            int i4 = bArr[8] & 255;
            int i5 = bArr[9] & 255;
            int i6 = bArr[10] & 255;
            int i7 = bArr[11] & 255;
            sedentaryRemindInfo.setFromTimeHour(i4);
            sedentaryRemindInfo.setFromTimeMinute(i5);
            sedentaryRemindInfo.setToTimeHour(i6);
            sedentaryRemindInfo.setToTimeMinute(i7);
            sedentaryRemindInfo.setRemindInterval(i3);
        }
        sedentaryRemindInfo.setLunchBreakNoRemind(bArr[12] & 255);
        if (DeviceMode.isHasFunction_6(65536)) {
            int i8 = bArr[13] & 255;
            int i9 = bArr[14] & 255;
            int i10 = bArr[15] & 255;
            int i11 = bArr[16] & 255;
            sedentaryRemindInfo.setNoRemindFromTimeHour(i8);
            sedentaryRemindInfo.setNoRemindFromTimeMinute(i9);
            sedentaryRemindInfo.setNoRemindToTimeHour(i10);
            sedentaryRemindInfo.setNoRemindToTimeMinute(i11);
        }
        return sedentaryRemindInfo;
    }
}
