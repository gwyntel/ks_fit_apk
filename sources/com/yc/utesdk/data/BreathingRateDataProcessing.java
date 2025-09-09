package com.yc.utesdk.data;

import androidx.core.view.MotionEventCompat;
import com.yc.utesdk.bean.BreatheInfo;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.ByteDataUtil;
import com.yc.utesdk.utils.open.CalendarUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class BreathingRateDataProcessing {
    private static BreathingRateDataProcessing instance;
    private int breatheCRC;
    private List<BreatheInfo> breatheInfoList = new ArrayList();
    private byte[] mOneDayBreatheData = new byte[0];
    private List<byte[]> mOneDayBreatheDataList = new ArrayList();

    public static BreathingRateDataProcessing getInstance() {
        if (instance == null) {
            instance = new BreathingRateDataProcessing();
        }
        return instance;
    }

    public void clearBreatheInfoList() {
        this.breatheInfoList = new ArrayList();
    }

    public void dealWithBreathe(byte[] bArr) throws ParseException {
        BreatheInfo breatheInfo;
        ArrayList arrayList;
        int i2 = bArr[1] & 255;
        int i3 = 2;
        if (i2 != 0) {
            if (i2 != 17) {
                if (i2 == 170) {
                    LogUtils.i("查询当前BLE测试呼吸率的状态");
                    if (bArr.length > 2) {
                        if ((bArr[2] & 255) != 17) {
                            UteListenerManager.getInstance().onBreathingRateStatus(true, 8);
                        } else {
                            UteListenerManager.getInstance().onBreathingRateStatus(true, 7);
                        }
                    }
                } else {
                    if (i2 == 234) {
                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                        CommandTimeOutUtils.getInstance().setCommandTimeOut(45);
                        if (bArr.length < 6) {
                            return;
                        }
                        while (i3 < bArr.length) {
                            this.breatheCRC ^= bArr[i3];
                            i3++;
                        }
                        int i4 = bArr[3] & 255;
                        if ((255 & bArr[5]) != 1) {
                            int length = bArr.length - 6;
                            byte[] bArr2 = new byte[length];
                            System.arraycopy(bArr, 6, bArr2, 0, length);
                            this.mOneDayBreatheData = ByteDataUtil.getInstance().copyTwoArrays(this.mOneDayBreatheData, bArr2);
                            return;
                        }
                        if (i4 == 1) {
                            this.mOneDayBreatheDataList = new ArrayList();
                        } else {
                            this.mOneDayBreatheDataList.add(this.mOneDayBreatheData);
                        }
                        int length2 = bArr.length - 6;
                        byte[] bArr3 = new byte[length2];
                        System.arraycopy(bArr, 6, bArr3, 0, length2);
                        this.mOneDayBreatheData = bArr3;
                        return;
                    }
                    if (i2 == 237) {
                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                        int i5 = bArr[2] & 255;
                        LogUtils.i("breatheCRC =" + (this.breatheCRC & 255) + ",bleCrc =" + i5);
                        if (i5 != (this.breatheCRC & 255)) {
                            this.breatheCRC = 0;
                            UteListenerManager.getInstance().onBreathingRateSyncFail();
                            return;
                        }
                        byte[] bArr4 = this.mOneDayBreatheData;
                        if (bArr4.length > 0) {
                            this.mOneDayBreatheDataList.add(bArr4);
                        }
                        this.breatheCRC = 0;
                        UteListenerManager.getInstance().onBreathingRateSyncSuccess(utendo(this.mOneDayBreatheDataList));
                        return;
                    }
                    if (i2 != 250) {
                        if (i2 == 253) {
                            if (bArr.length == 2) {
                                UteListenerManager.getInstance().onBreathingRateStatus(true, 3);
                                return;
                            }
                            return;
                        } else if (i2 == 3) {
                            LogUtils.i("呼吸率自动测试设置成功");
                            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                            UteListenerManager.getInstance().onBreathingRateStatus(true, 4);
                            return;
                        } else {
                            if (i2 != 4) {
                                return;
                            }
                            LogUtils.i("设置呼吸率自动测试时间段");
                            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                            UteListenerManager.getInstance().onBreathingRateStatus(true, 5);
                            return;
                        }
                    }
                    if ((bArr[2] & 255) != 253) {
                        while (i3 < bArr.length) {
                            this.breatheCRC ^= bArr[i3];
                            i3++;
                        }
                        String calendar = getCalendar(bArr);
                        int hour = getHour(bArr);
                        if (hour == 0) {
                            calendar = utendo(calendar);
                            hour = 24;
                        }
                        int i6 = hour * 60;
                        while (i < bArr.length - 8) {
                            int i7 = bArr[i + 8] & 255;
                            if (i7 != 255) {
                                int i8 = i6 - ((11 - i) * 10);
                                String calendarTime = CalendarUtils.getCalendarTime(calendar, i8);
                                this.breatheInfoList.add(new BreatheInfo(calendar, calendarTime, i8, i7));
                                LogUtils.i("calendar =" + calendar + ",startDate =" + calendarTime + ",time =" + i8 + ",breatheValue =" + i7);
                            }
                            i++;
                        }
                        UteListenerManager.getInstance().onBreathingRateSyncing();
                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                        CommandTimeOutUtils.getInstance().setCommandTimeOut(45);
                        return;
                    }
                    int i9 = bArr[3] & 255;
                    LogUtils.i("同步完成，并给出检验结果 bleCrc =" + i9 + ",breatheCRC =" + (this.breatheCRC & 255));
                    if (i9 == (this.breatheCRC & 255)) {
                        this.breatheCRC = 0;
                        LogUtils.i("同步完成,检验成功,保存数据");
                        UteListenerManager.getInstance().onBreathingRateSyncSuccess(this.breatheInfoList);
                        arrayList = new ArrayList();
                    } else {
                        LogUtils.i("同步完成,检验失败，重新同步");
                        UteListenerManager.getInstance().onBreathingRateSyncFail();
                        this.breatheCRC = 0;
                        arrayList = new ArrayList();
                    }
                    this.breatheInfoList = arrayList;
                    arrayList.clear();
                }
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                return;
            }
            if (bArr.length == 2) {
                LogUtils.i("打开呼吸率测试");
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onBreathingRateStatus(true, 1);
                return;
            }
            int i10 = bArr[2] & 255;
            int i11 = bArr[3] & 255;
            LogUtils.i("实时 breatheValueStatus =" + i10 + ",breatheValue =" + i11);
            i = i10 == 0 ? i11 : 0;
            breatheInfo = new BreatheInfo();
            String calendar2 = CalendarUtils.getCalendar();
            int phoneCurrentMinute = CalendarUtils.getPhoneCurrentMinute();
            String calendarTime2 = CalendarUtils.getCalendarTime(calendar2, phoneCurrentMinute);
            breatheInfo.setCalendar(calendar2);
            breatheInfo.setTime(phoneCurrentMinute);
            breatheInfo.setStartDate(calendarTime2);
            breatheInfo.setBreatheValue(i);
        } else {
            if (bArr.length == 2) {
                LogUtils.i("关闭呼吸率测试");
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onBreathingRateStatus(true, 2);
                return;
            }
            int i12 = bArr[2] & 255;
            int i13 = bArr[3] & 255;
            LogUtils.i("结束 breatheValueStatus =" + i12 + ",breatheValue =" + i13);
            i = i12 == 0 ? i13 : 0;
            breatheInfo = new BreatheInfo();
            String calendar3 = CalendarUtils.getCalendar();
            int phoneCurrentMinute2 = CalendarUtils.getPhoneCurrentMinute();
            String calendarTime3 = CalendarUtils.getCalendarTime(calendar3, phoneCurrentMinute2);
            breatheInfo.setCalendar(calendar3);
            breatheInfo.setTime(phoneCurrentMinute2);
            breatheInfo.setStartDate(calendarTime3);
            breatheInfo.setBreatheValue(i);
            breatheInfo.setBreatheValueStatus(i12);
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().onBreathingRateStatus(true, 2);
        }
        UteListenerManager.getInstance().onBreathingRateRealTime(breatheInfo);
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

    public final List utendo(List list) {
        ArrayList arrayList = new ArrayList();
        if (list != null && !list.isEmpty()) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                byte[] bArr = (byte[]) list.get(i2);
                int i3 = bArr[3] & 255;
                int i4 = bArr[2] & 255;
                int i5 = ((bArr[0] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[1] & 255);
                String strValueOf = String.valueOf(i3);
                String strValueOf2 = String.valueOf(i4);
                if (i3 < 10) {
                    strValueOf = "0" + i3;
                }
                if (i4 < 10) {
                    strValueOf2 = "0" + i4;
                }
                String str = i5 + strValueOf2 + strValueOf;
                for (int i6 = 4; i6 < bArr.length; i6++) {
                    int i7 = bArr[i6] & 255;
                    if (i7 != 255 && i7 != 0) {
                        int i8 = i6 - 4;
                        arrayList.add(new BreatheInfo(str, CalendarUtils.getCalendarTime(str, i8), i8, i7));
                    }
                }
            }
        }
        return arrayList;
    }

    public final String utendo(String str) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        try {
            date = new SimpleDateFormat(CalendarUtils.yyyyMMdd, Locale.US).parse(str);
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        calendar.setTime(date);
        calendar.set(5, calendar.get(5) - 1);
        return new SimpleDateFormat(CalendarUtils.yyyyMMdd, Locale.US).format(calendar.getTime());
    }
}
