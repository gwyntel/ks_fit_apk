package com.yc.utesdk.data;

import androidx.core.view.MotionEventCompat;
import com.yc.utesdk.bean.OxygenInfo;
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
public class OxygenDataProcessing {
    public static final int OXYGEN_FILTER_MAX_VALUE = 100;
    public static final int OXYGEN_FILTER_MIN_VALUE = 70;
    private static OxygenDataProcessing instance;
    private int oxygenCRC;
    private List<OxygenInfo> mOxygenList = new ArrayList();
    private byte[] mOneDayOxygenData = new byte[0];
    private List<byte[]> mOneDayOxygenDataList = new ArrayList();

    public static OxygenDataProcessing getInstance() {
        if (instance == null) {
            instance = new OxygenDataProcessing();
        }
        return instance;
    }

    public void clearOxygenInfoList() {
        this.mOxygenList = new ArrayList();
    }

    public void dealWithOxygen(String str, byte[] bArr) throws ParseException {
        OxygenInfo oxygenInfo;
        if (str.equals("34")) {
            int i2 = bArr[1] & 255;
            int i3 = 2;
            if (i2 == 0) {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                if (bArr.length != 2) {
                    int iFilterOxygenValue = getInstance().filterOxygenValue((bArr[2] & 255) == 0 ? bArr[3] & 255 : 0);
                    if (iFilterOxygenValue > 0) {
                        UteListenerManager.getInstance().onOxygenStatus(true, 4);
                        String calendar = CalendarUtils.getCalendar();
                        int phoneCurrentMinute = CalendarUtils.getPhoneCurrentMinute();
                        oxygenInfo = new OxygenInfo(calendar, CalendarUtils.getCalendarTime(calendar, phoneCurrentMinute), phoneCurrentMinute, iFilterOxygenValue);
                    }
                }
                UteListenerManager.getInstance().onOxygenStatus(true, 3);
                return;
            }
            if (i2 == 17) {
                if (bArr.length == 2) {
                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                } else {
                    int iFilterOxygenValue2 = getInstance().filterOxygenValue((bArr[2] & 255) == 0 ? bArr[3] & 255 : 0);
                    if (iFilterOxygenValue2 > 0) {
                        String calendar2 = CalendarUtils.getCalendar();
                        int phoneCurrentMinute2 = CalendarUtils.getPhoneCurrentMinute();
                        oxygenInfo = new OxygenInfo(calendar2, CalendarUtils.getCalendarTime(calendar2, phoneCurrentMinute2), phoneCurrentMinute2, iFilterOxygenValue2);
                    }
                }
                UteListenerManager.getInstance().onOxygenStatus(true, 1);
                return;
            }
            if (i2 == 234) {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                CommandTimeOutUtils.getInstance().setCommandTimeOut(27);
                if (bArr.length < 6) {
                    return;
                }
                while (i3 < bArr.length) {
                    this.oxygenCRC ^= bArr[i3];
                    i3++;
                }
                int i4 = bArr[3] & 255;
                if ((255 & bArr[5]) != 1) {
                    int length = bArr.length - 6;
                    byte[] bArr2 = new byte[length];
                    System.arraycopy(bArr, 6, bArr2, 0, length);
                    this.mOneDayOxygenData = ByteDataUtil.getInstance().copyTwoArrays(this.mOneDayOxygenData, bArr2);
                    return;
                }
                if (i4 == 1) {
                    this.mOneDayOxygenDataList = new ArrayList();
                } else {
                    this.mOneDayOxygenDataList.add(this.mOneDayOxygenData);
                }
                int length2 = bArr.length - 6;
                byte[] bArr3 = new byte[length2];
                System.arraycopy(bArr, 6, bArr3, 0, length2);
                this.mOneDayOxygenData = bArr3;
                return;
            }
            if (i2 == 237) {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                int i5 = bArr[2] & 255;
                LogUtils.i("oxygenCRC =" + (this.oxygenCRC & 255) + ",bleCrc =" + i5);
                if (i5 == (this.oxygenCRC & 255)) {
                    byte[] bArr4 = this.mOneDayOxygenData;
                    if (bArr4.length > 0) {
                        this.mOneDayOxygenDataList.add(bArr4);
                    }
                    this.oxygenCRC = 0;
                    UteListenerManager.getInstance().onOxygenSyncSuccess(utendo(this.mOneDayOxygenDataList));
                    return;
                }
                this.oxygenCRC = 0;
            } else {
                if (i2 != 250) {
                    if (i2 == 253) {
                        if (bArr.length == 2) {
                            UteListenerManager.getInstance().onOxygenStatus(true, 5);
                            return;
                        }
                        return;
                    } else if (i2 == 3) {
                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                        UteListenerManager.getInstance().onOxygenStatus(true, 6);
                        return;
                    } else {
                        if (i2 != 4) {
                            return;
                        }
                        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                        UteListenerManager.getInstance().onOxygenStatus(true, 7);
                        return;
                    }
                }
                UteListenerManager.getInstance().onOxygenSyncing();
                if ((bArr[2] & 255) != 253) {
                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                    CommandTimeOutUtils.getInstance().setCommandTimeOut(27);
                    while (i3 < bArr.length) {
                        this.oxygenCRC ^= bArr[i3];
                        i3++;
                    }
                    String calendar3 = getInstance().getCalendar(bArr);
                    int hour = getInstance().getHour(bArr);
                    if (hour == 0) {
                        calendar3 = utendo(calendar3);
                        hour = 24;
                    }
                    int i6 = hour * 60;
                    int i7 = DeviceMode.isHasFunction_8(512) ? 5 : 10;
                    while (i < bArr.length - 8) {
                        int i8 = bArr[i + 8] & 255;
                        if (i8 != 255) {
                            int i9 = i6 - ((11 - i) * i7);
                            int iFilterOxygenValue3 = getInstance().filterOxygenValue(i8);
                            if (iFilterOxygenValue3 > 0) {
                                this.mOxygenList.add(new OxygenInfo(calendar3, CalendarUtils.getCalendarTime(calendar3, i9), i9, iFilterOxygenValue3));
                            }
                        }
                        i++;
                    }
                    return;
                }
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                int i10 = bArr[3] & 255;
                LogUtils.i("oxygenCRC =" + (this.oxygenCRC & 255) + ",bleCrc =" + i10);
                int i11 = this.oxygenCRC & 255;
                this.oxygenCRC = 0;
                if (i10 == i11) {
                    UteListenerManager.getInstance().onOxygenSyncSuccess(this.mOxygenList);
                    ArrayList arrayList = new ArrayList();
                    this.mOxygenList = arrayList;
                    arrayList.clear();
                    return;
                }
                ArrayList arrayList2 = new ArrayList();
                this.mOxygenList = arrayList2;
                arrayList2.clear();
            }
            UteListenerManager.getInstance().onOxygenSyncFail();
            return;
            UteListenerManager.getInstance().onOxygenRealTime(oxygenInfo);
        }
    }

    public int filterOxygenValue(int i2) {
        return i2 > 0 ? Math.max(Math.min(i2, 100), 70) : i2;
    }

    public String formatAsDoubleCharacters(String str) {
        if (str.length() % 2 != 1) {
            return str;
        }
        return "0" + str;
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

    public String secondToTimeString(int i2) {
        return formatAsDoubleCharacters((i2 / 3600) + "") + ":" + formatAsDoubleCharacters(((i2 % 3600) / 60) + "") + ":" + formatAsDoubleCharacters((i2 % 60) + "") + "";
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
                        arrayList.add(new OxygenInfo(str, CalendarUtils.getCalendarTime(str, i8), i8, getInstance().filterOxygenValue(i7)));
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
