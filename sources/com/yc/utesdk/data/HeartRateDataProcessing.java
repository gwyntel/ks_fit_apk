package com.yc.utesdk.data;

import androidx.core.view.MotionEventCompat;
import com.yc.utesdk.bean.HeartRateBestValueInfo;
import com.yc.utesdk.bean.HeartRateHourBestValueInfo;
import com.yc.utesdk.bean.HeartRateInfo;
import com.yc.utesdk.bean.HeartRateRestInfo;
import com.yc.utesdk.ble.open.DeviceMode;
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
public class HeartRateDataProcessing {
    public static final int HEART_RATE_FILTER_MAX_VALUE = 200;
    public static final int HEART_RATE_FILTER_MIN_VALUE = 40;
    private static HeartRateDataProcessing instance;
    private int oxygenCRC;
    private List<HeartRateInfo> mHeartRateList = new ArrayList();
    private byte[] restHRSyncData = new byte[0];
    private byte[] mOneDayOxygenData = new byte[0];
    private List<byte[]> mOneDayOxygenDataList = new ArrayList();

    public static HeartRateDataProcessing getInstance() {
        if (instance == null) {
            instance = new HeartRateDataProcessing();
        }
        return instance;
    }

    public void clearHeartRateList() {
        this.mHeartRateList = new ArrayList();
    }

    public void dealWithHeartRate24(byte[] bArr, String str) throws ParseException {
        UteListenerManager uteListenerManager;
        int i2;
        if (str.equals("F7FD")) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().onHeartRateSyncSuccess(this.mHeartRateList);
            return;
        }
        if (str.equals("F701")) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().onHeartRateStatus(true, 1);
            return;
        }
        if (str.equals("F702")) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().onHeartRateStatus(true, 2);
            return;
        }
        if (str.equals("F703")) {
            utenchar(bArr);
            return;
        }
        if (str.equals("F704")) {
            utenbyte(bArr);
            return;
        }
        if (str.equals("F705")) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            utendo(bArr);
            return;
        }
        if (str.equals("F707") && bArr.length == 9) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            utenfor(bArr);
            return;
        }
        if (str.equals("F708")) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            if ((bArr[2] & 255) == 253) {
                utenif(this.restHRSyncData);
                this.restHRSyncData = new byte[0];
                return;
            }
            CommandTimeOutUtils.getInstance().setCommandTimeOut(153);
            if ((((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[3] & 255)) == 1) {
                int length = bArr.length - 4;
                byte[] bArr2 = new byte[length];
                System.arraycopy(bArr, 4, bArr2, 0, length);
                this.restHRSyncData = bArr2;
                return;
            }
            int length2 = bArr.length - 4;
            byte[] bArr3 = new byte[length2];
            System.arraycopy(bArr, 4, bArr3, 0, length2);
            this.restHRSyncData = ByteDataUtil.getInstance().copyTwoArrays(this.restHRSyncData, bArr3);
            return;
        }
        if (str.equals("F709")) {
            int i3 = bArr[2] & 255;
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            if (i3 == 1) {
                UteListenerManager.getInstance().onHeartRateStatus(true, 9);
                return;
            } else {
                if (i3 != 0) {
                    return;
                }
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 10;
            }
        } else {
            if (!str.equals("F70A")) {
                if (bArr.length > 5) {
                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                    CommandTimeOutUtils.getInstance().setCommandTimeOut(19);
                    utencase(bArr);
                    UteListenerManager.getInstance().onHeartRateSyncing();
                    return;
                }
                return;
            }
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            uteListenerManager = UteListenerManager.getInstance();
            i2 = 11;
        }
        uteListenerManager.onHeartRateStatus(true, i2);
    }

    public void dealWithHeartRateCommon(byte[] bArr, String str) {
        UteListenerManager uteListenerManager;
        int i2;
        if (str.equals("D601")) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            uteListenerManager = UteListenerManager.getInstance();
            i2 = 12;
        } else if (str.equals("D602")) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            uteListenerManager = UteListenerManager.getInstance();
            i2 = 13;
        } else {
            if (!str.equals("D610")) {
                return;
            }
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            uteListenerManager = UteListenerManager.getInstance();
            i2 = 14;
        }
        uteListenerManager.onHeartRateStatus(true, i2);
    }

    public void dealWithHeartRateNormal(byte[] bArr, String str, String str2) {
        if (str.equals("E5FD")) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().onHeartRateStatus(false, 5);
            return;
        }
        if (str2.equals("E511")) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().onHeartRateStatus(true, 3);
            return;
        }
        if (str2.equals("E500")) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        } else {
            if (str2.length() != 8 || !str2.startsWith("E500")) {
                realTimeDataNormal(bArr);
                return;
            }
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            if (str2.startsWith("E50000")) {
                realTimeDataNormalStop(bArr);
                return;
            }
        }
        UteListenerManager.getInstance().onHeartRateStatus(true, 5);
    }

    public void dealWithHeartRateSaveCustom(byte[] bArr) {
        int i2 = bArr[1] & 255;
        if (i2 != 234) {
            if (i2 != 237) {
                return;
            }
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            int i3 = bArr[2] & 255;
            LogUtils.i("oxygenCRC =" + (this.oxygenCRC & 255) + ",bleCrc =" + i3);
            if (i3 != (this.oxygenCRC & 255)) {
                this.oxygenCRC = 0;
                UteListenerManager.getInstance().onHeartRateSyncFail();
                return;
            }
            byte[] bArr2 = this.mOneDayOxygenData;
            if (bArr2.length > 0) {
                this.mOneDayOxygenDataList.add(bArr2);
            }
            this.oxygenCRC = 0;
            UteListenerManager.getInstance().onHeartRateSyncSuccess(utendo(this.mOneDayOxygenDataList));
            return;
        }
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        CommandTimeOutUtils.getInstance().setCommandTimeOut(19);
        if (bArr.length < 6) {
            return;
        }
        for (int i4 = 2; i4 < bArr.length; i4++) {
            this.oxygenCRC ^= bArr[i4];
        }
        int i5 = bArr[3] & 255;
        if ((bArr[5] & 255) != 1) {
            int length = bArr.length - 6;
            byte[] bArr3 = new byte[length];
            System.arraycopy(bArr, 6, bArr3, 0, length);
            this.mOneDayOxygenData = ByteDataUtil.getInstance().copyTwoArrays(this.mOneDayOxygenData, bArr3);
            return;
        }
        if (i5 == 1) {
            this.mOneDayOxygenDataList = new ArrayList();
        } else {
            this.mOneDayOxygenDataList.add(this.mOneDayOxygenData);
        }
        int length2 = bArr.length - 6;
        byte[] bArr4 = new byte[length2];
        System.arraycopy(bArr, 6, bArr4, 0, length2);
        this.mOneDayOxygenData = bArr4;
    }

    public void offLineDataNormal(byte[] bArr, String str) {
        if (str.equals("E6FD")) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().onHeartRateSyncSuccess(this.mHeartRateList);
            return;
        }
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        CommandTimeOutUtils.getInstance().setCommandTimeOut(19);
        String strUtenint = utenint(bArr);
        int iUtennew = utennew(bArr);
        int i2 = bArr[7] & 255;
        if (i2 <= 40 || i2 >= 200) {
            return;
        }
        String calendarTime = CalendarUtils.getCalendarTime(strUtenint, iUtennew);
        LogUtils.i("同步普通心率，calendar = " + strUtenint + ",calendarTime=" + calendarTime + ",time=" + iUtennew + ",heartRate=" + i2);
        this.mHeartRateList.add(new HeartRateInfo(strUtenint, calendarTime, iUtennew, i2));
    }

    public void realTimeDataNormal(byte[] bArr) {
        int i2;
        boolean zUtentry = utentry(bArr);
        LogUtils.i("isEffectiveRate=" + zUtentry);
        if (!zUtentry || (i2 = bArr[3] & 255) <= 0) {
            return;
        }
        String calendar = CalendarUtils.getCalendar();
        int phoneCurrentMinute = CalendarUtils.getPhoneCurrentMinute();
        String calendarTime = CalendarUtils.getCalendarTime(calendar, phoneCurrentMinute);
        LogUtils.i("普通心率，calendar = " + calendar + ",calendarTime=" + calendarTime + ",time=" + phoneCurrentMinute + ",heartRate=" + i2);
        UteListenerManager.getInstance().onHeartRateRealTime(new HeartRateInfo(calendar, calendarTime, phoneCurrentMinute, i2));
    }

    public void realTimeDataNormalStop(byte[] bArr) {
        int i2 = bArr[3] & 255;
        if (i2 <= 0) {
            UteListenerManager.getInstance().onHeartRateStatus(true, 5);
            return;
        }
        UteListenerManager.getInstance().onHeartRateStatus(true, 4);
        String calendar = CalendarUtils.getCalendar();
        int phoneCurrentMinute = CalendarUtils.getPhoneCurrentMinute();
        String calendarTime = CalendarUtils.getCalendarTime(calendar, phoneCurrentMinute);
        LogUtils.i("普通心率，calendar = " + calendar + ",calendarTime=" + calendarTime + ",time=" + phoneCurrentMinute + ",heartRate=" + i2);
        UteListenerManager.getInstance().onHeartRateRealTime(new HeartRateInfo(calendar, calendarTime, phoneCurrentMinute, i2));
    }

    public final void utenbyte(byte[] bArr) {
        int i2 = bArr[8] & 255;
        int i3 = bArr[9] & 255;
        int i4 = bArr[10] & 255;
        int i5 = ((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[3] & 255);
        int i6 = bArr[4] & 255;
        int i7 = bArr[5] & 255;
        int i8 = bArr[6] & 255;
        int i9 = bArr[7] & 255;
        Locale locale = Locale.US;
        String str = String.format(locale, "%1$04d%2$02d%3$02d", Integer.valueOf(i5), Integer.valueOf(i6), Integer.valueOf(i7));
        String str2 = String.format(locale, "%1$04d%2$02d%3$02d%4$02d%5$02d", Integer.valueOf(i5), Integer.valueOf(i6), Integer.valueOf(i7), Integer.valueOf(i8), Integer.valueOf(i9));
        LogUtils.i("highestRate =" + i2 + ",lowestRate=" + i3 + ",verageRate=" + i4);
        UteListenerManager.getInstance().onHeartRateBestValue(new HeartRateBestValueInfo(str, str2, i2, i3, i4));
    }

    public final void utencase(byte[] bArr) throws ParseException {
        String strUtenint = utenint(bArr);
        int i2 = bArr[5] & 255;
        if (i2 == 0) {
            strUtenint = utendo(strUtenint);
            i2 = 24;
        }
        int i3 = DeviceMode.isHasFunction_8(512) ? 5 : 10;
        int i4 = i2 * 60;
        for (int i5 = 6; i5 < bArr.length; i5++) {
            int i6 = i4 - ((17 - i5) * i3);
            int i7 = bArr[i5] & 255;
            if (i7 > 40 && i7 < 200) {
                this.mHeartRateList.add(new HeartRateInfo(strUtenint, CalendarUtils.getCalendarTime(strUtenint, i6), i6, i7));
            }
        }
    }

    public final void utenchar(byte[] bArr) throws ParseException {
        int i2 = bArr[5] & 255;
        int i3 = bArr[4] & 255;
        int i4 = ((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[3] & 255);
        String strValueOf = String.valueOf(i2);
        String strValueOf2 = String.valueOf(i3);
        if (i2 < 10) {
            strValueOf = "0" + i2;
        }
        if (i3 < 10) {
            strValueOf2 = "0" + i3;
        }
        String strUtendo = i4 + strValueOf2 + strValueOf;
        int i5 = bArr[6] & 255;
        if (i5 == 0) {
            strUtendo = utendo(strUtendo);
            i5 = 24;
        }
        int i6 = (i5 * 60) + (bArr[7] & 255);
        int i7 = bArr[8] & 255;
        if (i7 <= 40 || i7 >= 200) {
            return;
        }
        String calendarTime = CalendarUtils.getCalendarTime(strUtendo, i6);
        LogUtils.i("24小时实时心率，calendar = " + strUtendo + ",calendarTime=" + calendarTime + ",time=" + i6 + ",heartRate=" + i7);
        UteListenerManager.getInstance().onHeartRateRealTime(new HeartRateInfo(strUtendo, calendarTime, i6, i7));
    }

    public final void utendo(byte[] bArr) {
        int i2 = bArr[8] & 255;
        int i3 = ((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[3] & 255);
        int i4 = bArr[4] & 255;
        int i5 = bArr[5] & 255;
        int i6 = bArr[6] & 255;
        int i7 = bArr[7] & 255;
        Locale locale = Locale.US;
        String str = String.format(locale, "%1$04d%2$02d%3$02d", Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5));
        String str2 = String.format(locale, "%1$04d%2$02d%3$02d%4$02d%5$02d", Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6), Integer.valueOf(i7));
        LogUtils.i("doHeartRateRest calendar =" + str + ",calendarTime=" + str2 + ",heartRateRest=" + i2);
        UteListenerManager.getInstance().onHeartRateRest(new HeartRateRestInfo(str, str2, i2));
    }

    public final void utenfor(byte[] bArr) {
        int i2 = bArr[7] & 255;
        int i3 = bArr[8] & 255;
        int i4 = ((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[3] & 255);
        int i5 = bArr[4] & 255;
        int i6 = bArr[5] & 255;
        int i7 = bArr[6] & 255;
        Locale locale = Locale.US;
        String str = String.format(locale, "%1$04d%2$02d%3$02d", Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6));
        String str2 = String.format(locale, "%1$04d%2$02d%3$02d%4$02d", Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6), Integer.valueOf(i7));
        LogUtils.i("calendar =" + str + ",calendarTime=" + str2 + ",highestRate=" + i2 + ",lowestRate=" + i3);
        UteListenerManager.getInstance().onHeartRateHourRestBestValue(new HeartRateHourBestValueInfo(str, str2, i2, i3));
    }

    public final void utenif(byte[] bArr) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < bArr.length; i2 += 5) {
            int i3 = ((bArr[i2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[i2 + 1] & 255);
            int i4 = bArr[i2 + 2] & 255;
            int i5 = bArr[i2 + 3] & 255;
            int i6 = bArr[i2 + 4] & 255;
            String str = String.format(Locale.US, "%1$04d%2$02d%3$02d", Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5));
            LogUtils.i("doHeartRateRestSync calendar =" + str + ",heartRateRest=" + i6);
            arrayList.add(new HeartRateRestInfo(str, i6));
        }
        UteListenerManager.getInstance().onHeartRateRestSyncSuccess(arrayList);
    }

    public final String utenint(byte[] bArr) {
        int i2 = bArr[4] & 255;
        int i3 = bArr[3] & 255;
        int i4 = (bArr[2] & 255) | ((bArr[1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
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

    public final int utennew(byte[] bArr) {
        return ((bArr[5] & 255) * 60) + (bArr[6] & 255);
    }

    public final boolean utentry(byte[] bArr) {
        return (bArr[2] & 255) == 0;
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
                    if (i7 != 255 && i7 != 0 && i7 > 40 && i7 < 200) {
                        int i8 = i6 - 4;
                        arrayList.add(new HeartRateInfo(str, CalendarUtils.getCalendarTime(str, i8), i8, i7));
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
