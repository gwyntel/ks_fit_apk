package com.yc.utesdk.data;

import androidx.core.view.MotionEventCompat;
import com.yc.utesdk.bean.TemperatureInfo;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.CalendarUtils;
import com.yc.utesdk.utils.open.RoundingUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class TemperatureDataProcessing {
    private static TemperatureDataProcessing instance;
    private int tempTemperatureCRC;
    private List<TemperatureInfo> temperatureInfoList = new ArrayList();

    public static TemperatureDataProcessing getInstance() {
        if (instance == null) {
            instance = new TemperatureDataProcessing();
        }
        return instance;
    }

    public void clearTemperatureInfoList() {
        this.temperatureInfoList = new ArrayList();
    }

    public void dealWithTemperature(byte[] bArr) {
        String str;
        UteListenerManager uteListenerManager;
        int i2;
        String str2;
        float f2;
        TemperatureInfo temperatureInfo;
        float f3;
        float f4;
        float f5;
        int i3 = bArr[1] & 255;
        int i4 = 0;
        if (i3 != 1) {
            if (i3 == 250) {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                CommandTimeOutUtils.getInstance().setCommandTimeOut(32);
                for (int i5 = 2; i5 < bArr.length; i5++) {
                    this.tempTemperatureCRC ^= bArr[i5];
                }
                String calendar = getCalendar(bArr);
                int hour = getHour(bArr) * 60;
                for (int i6 = 7; i4 < bArr.length - i6; i6 = 7) {
                    if ((bArr[i4 + 7] & 255) == 255 && (bArr[i4 + 8] & 255) == 255) {
                        str = "FA i =" + i4 + ",FFFF";
                    } else {
                        float fRoundingToFloat = RoundingUtils.getInstance().roundingToFloat(2, (((r6 << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[i4 + 8] & 255)) / 100.0f);
                        int i7 = ((i4 / 2) * 10) + hour;
                        String calendarTime = CalendarUtils.getCalendarTime(calendar, i7);
                        this.temperatureInfoList.add(new TemperatureInfo(0, calendar, calendarTime, i7 * 60, fRoundingToFloat));
                        str = "FA i =" + i4 + ",calendar =" + calendar + ",startDate =" + calendarTime + ",minute =" + i7 + ",bodyTemperature =" + fRoundingToFloat;
                    }
                    LogUtils.i(str);
                    i4 += 2;
                }
                UteListenerManager.getInstance().onTemperatureSyncing();
                return;
            }
            if (i3 != 253) {
                switch (i3) {
                    case 3:
                        LogUtils.i("设置自动测试开或关");
                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                        uteListenerManager = UteListenerManager.getInstance();
                        i2 = 3;
                        uteListenerManager.onTemperatureStatus(true, i2);
                        break;
                    case 4:
                        LogUtils.i("设置自动测试时间段");
                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                        uteListenerManager = UteListenerManager.getInstance();
                        i2 = 4;
                        uteListenerManager.onTemperatureStatus(true, i2);
                        break;
                    case 5:
                        LogUtils.i("删除历史数据");
                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                        uteListenerManager = UteListenerManager.getInstance();
                        i2 = 6;
                        uteListenerManager.onTemperatureStatus(true, i2);
                        break;
                    case 6:
                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                        uteListenerManager = UteListenerManager.getInstance();
                        i2 = 5;
                        uteListenerManager.onTemperatureStatus(true, i2);
                        break;
                    case 7:
                        LogUtils.i("体温校准(通过支持体温校准功能标志位判断)");
                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                        UteListenerManager.getInstance().onTemperatureStatus(true, 7);
                        break;
                    case 8:
                        str2 = "设置采集体温原始数据开关";
                        LogUtils.i(str2);
                        break;
                    case 9:
                        str2 = "查询采集体温原始数据开关状态，0表示关闭，1表示打开 =" + (bArr[2] & 255);
                        LogUtils.i(str2);
                        break;
                    case 10:
                        LogUtils.i("达到设定警报值持续1分钟后自动发送");
                        uteListenerManager = UteListenerManager.getInstance();
                        i2 = 8;
                        uteListenerManager.onTemperatureStatus(true, i2);
                        break;
                    case 11:
                        int i8 = bArr[2] & 255;
                        LogUtils.i("手环端切换了温度单位为:" + i8);
                        UteListenerManager.getInstance().onTemperatureStatus(i8 == 0, 9);
                        break;
                    default:
                        switch (i3) {
                            case 240:
                                String calendar2 = getCalendar(bArr);
                                String startDate = getStartDate(bArr);
                                int secondTime = getSecondTime(bArr);
                                float bodyTemperature = getBodyTemperature(false, bArr);
                                LogUtils.i("F0 calendar =" + calendar2 + ",startDate =" + startDate + ",secondTime =" + secondTime + ",bodyTemperature =" + bodyTemperature);
                                if ((bArr[9] & 255) == 255 && (bArr[10] & 255) == 255) {
                                    LogUtils.i("F0 =,FFFF");
                                    f2 = 0.0f;
                                } else {
                                    f2 = bodyTemperature;
                                }
                                temperatureInfo = new TemperatureInfo(0, calendar2, startDate, secondTime, f2);
                                break;
                            case 241:
                                String calendar3 = getCalendar(bArr);
                                String startDate2 = getStartDate(bArr);
                                int secondTime2 = getSecondTime(bArr);
                                float bodyTemperature2 = getBodyTemperature(true, bArr);
                                float bodySurfaceTemperature = getBodySurfaceTemperature(bArr);
                                LogUtils.i("F1 calendar =" + calendar3 + ",startDate =" + startDate2 + ",secondTime =" + secondTime2 + ",bodyTemperature =" + bodyTemperature2 + ",bodySurfaceTemperature =" + bodySurfaceTemperature);
                                if ((bArr[11] & 255) == 255 && (bArr[12] & 255) == 255) {
                                    LogUtils.i("F1 bodyTemperature =,FFFF");
                                    bodyTemperature2 = 0.0f;
                                }
                                if ((bArr[9] & 255) == 255 && (bArr[10] & 255) == 255) {
                                    LogUtils.i("F1 bodySurfaceTemperature =,FFFF");
                                    f3 = 0.0f;
                                } else {
                                    f3 = bodySurfaceTemperature;
                                }
                                temperatureInfo = new TemperatureInfo(1, calendar3, startDate2, secondTime2, f3, bodyTemperature2, 0.0f);
                                break;
                            case 242:
                                String calendar4 = getCalendar(bArr);
                                String startDate3 = getStartDate(bArr);
                                int secondTime3 = getSecondTime(bArr);
                                float ambientTemperature = getAmbientTemperature(bArr);
                                float bodySurfaceTemperature2 = getBodySurfaceTemperature(bArr);
                                LogUtils.i("F2 calendar =" + calendar4 + ",startDate =" + startDate3 + ",secondTime =" + secondTime3 + ",ambientTemperature =" + ambientTemperature + ",bodySurfaceTemperature =" + bodySurfaceTemperature2);
                                if ((bArr[11] & 255) == 255 && (bArr[12] & 255) == 255) {
                                    LogUtils.i("F2 ambientTemperature =,FFFF");
                                    f4 = 0.0f;
                                } else {
                                    f4 = ambientTemperature;
                                }
                                if ((bArr[9] & 255) == 255 && (bArr[10] & 255) == 255) {
                                    LogUtils.i("F2 bodySurfaceTemperature =,FFFF");
                                    f5 = 0.0f;
                                } else {
                                    f5 = bodySurfaceTemperature2;
                                }
                                temperatureInfo = new TemperatureInfo(2, calendar4, startDate3, secondTime3, f5, 0.0f, f4);
                                break;
                        }
                        UteListenerManager.getInstance().onTemperatureRealTime(temperatureInfo);
                        break;
                }
                return;
            }
            if (bArr.length != 2) {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                int i9 = bArr[2] & 255;
                LogUtils.i("同步完成，并给出检验结果 bleCrc =" + i9 + ",tempTemperatureCRC =" + (this.tempTemperatureCRC & 255));
                if (i9 == (this.tempTemperatureCRC & 255)) {
                    this.tempTemperatureCRC = 0;
                    LogUtils.i("同步完成,检验成功,保存数据");
                    UteListenerManager.getInstance().onTemperatureSyncSuccess(this.temperatureInfoList);
                    ArrayList arrayList = new ArrayList();
                    this.temperatureInfoList = arrayList;
                    arrayList.clear();
                    return;
                }
                LogUtils.i("同步完成,检验失败，重新同步");
                this.tempTemperatureCRC = 0;
                ArrayList arrayList2 = new ArrayList();
                this.temperatureInfoList = arrayList2;
                arrayList2.clear();
                UteListenerManager.getInstance().onTemperatureSyncFail();
                return;
            }
            LogUtils.i("体温测试超时");
        } else {
            String calendar5 = getCalendar(bArr);
            String startDate4 = getStartDate(bArr);
            int secondTime4 = getSecondTime(bArr);
            float bodyTemperature3 = getBodyTemperature(false, bArr);
            if ((bArr[9] & 255) == 255 && (bArr[10] & 255) == 255) {
                bodyTemperature3 = 0.0f;
            }
            LogUtils.i("01 calendar =" + calendar5 + ",startDate =" + startDate4 + ",secondTime =" + secondTime4 + ",bodyTemperature =" + bodyTemperature3);
            TemperatureInfo temperatureInfo2 = new TemperatureInfo(0, calendar5, startDate4, secondTime4, bodyTemperature3);
            if ((bArr[9] & 255) != 255 || (bArr[10] & 255) != 255) {
                UteListenerManager.getInstance().onTemperatureRealTime(temperatureInfo2);
                return;
            }
        }
        UteListenerManager.getInstance().onTemperatureStatus(true, 2);
    }

    public float getAmbientTemperature(byte[] bArr) {
        return RoundingUtils.getInstance().roundingToFloat(2, ((bArr[12] & 255) | ((bArr[11] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) / 100.0f);
    }

    public float getBodySurfaceTemperature(byte[] bArr) {
        return RoundingUtils.getInstance().roundingToFloat(2, ((bArr[10] & 255) | ((bArr[9] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) / 100.0f);
    }

    public float getBodyTemperature(boolean z2, byte[] bArr) {
        int i2;
        byte b2;
        if (z2) {
            i2 = (bArr[11] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK;
            b2 = bArr[12];
        } else {
            i2 = (bArr[9] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK;
            b2 = bArr[10];
        }
        return RoundingUtils.getInstance().roundingToFloat(2, (i2 | (b2 & 255)) / 100.0f);
    }

    public String getCalendar(byte[] bArr) {
        int i2 = bArr[5] & 255;
        int i3 = bArr[4] & 255;
        int i4 = (bArr[3] & 255) | ((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
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

    public int getHour(byte[] bArr) {
        return bArr[6] & 255;
    }

    public float getMaxAlarmTemperature(byte[] bArr) {
        return RoundingUtils.getInstance().roundingToFloat(2, ((bArr[4] & 255) | ((bArr[3] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) / 100.0f);
    }

    public float getMinAlarmTemperature(byte[] bArr) {
        return RoundingUtils.getInstance().roundingToFloat(2, ((bArr[6] & 255) | ((bArr[5] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) / 100.0f);
    }

    public int getSecondTime(byte[] bArr) {
        return ((bArr[6] & 255) * 3600) + ((bArr[7] & 255) * 60) + (bArr[8] & 255);
    }

    public String getStartDate(byte[] bArr) {
        int i2 = bArr[6] & 255;
        int i3 = bArr[7] & 255;
        int i4 = bArr[8] & 255;
        String strValueOf = String.valueOf(i2);
        String strValueOf2 = String.valueOf(i3);
        String strValueOf3 = String.valueOf(i4);
        if (i2 < 10) {
            strValueOf = "0" + i2;
        }
        if (i3 < 10) {
            strValueOf2 = "0" + i3;
        }
        if (i4 < 10) {
            strValueOf3 = "0" + i4;
        }
        return getCalendar(bArr) + strValueOf + strValueOf2 + strValueOf3;
    }
}
