package com.yc.utesdk.data;

import androidx.core.view.MotionEventCompat;
import com.yc.utesdk.bean.BloodPressureInfo;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.utils.open.CalendarUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class BloodPressureDataProcessing {
    private static BloodPressureDataProcessing instance;
    private List<BloodPressureInfo> mBloodPressureList = new ArrayList();

    public static BloodPressureDataProcessing getInstance() {
        if (instance == null) {
            instance = new BloodPressureDataProcessing();
        }
        return instance;
    }

    public void clearBloodPressureList() {
        this.mBloodPressureList = new ArrayList();
    }

    public void dealWithBloodPressure(byte[] bArr, String str, String str2) {
        if (str.equals("C7FD")) {
            UteListenerManager.getInstance().onBloodPressureStatus(true, 3);
            return;
        }
        if (!str.equals("C7FF")) {
            if (str.equals("C711")) {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onBloodPressureStatus(true, 1);
                return;
            } else if (str.equals("C700") && str2.length() == 4) {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onBloodPressureStatus(true, 2);
                return;
            } else {
                if (str2.startsWith("C70000")) {
                    UteListenerManager.getInstance().onBloodPressureStatus(true, 2);
                    utenfor(bArr);
                    return;
                }
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            }
        }
        UteListenerManager.getInstance().onBloodPressureStatus(true, 4);
    }

    public void offLineDataOperate(byte[] bArr, String str) {
        if (str.equals("C8FD")) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().onBloodPressureSyncSuccess(this.mBloodPressureList);
            return;
        }
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        CommandTimeOutUtils.getInstance().setCommandTimeOut(24);
        String strUtendo = utendo(bArr);
        int iUtenif = utenif(bArr);
        this.mBloodPressureList.add(new BloodPressureInfo(strUtendo, CalendarUtils.getCalendarTime(strUtendo, iUtenif), iUtenif, bArr[7] & 255, bArr[8] & 255));
    }

    public final String utendo(byte[] bArr) {
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

    public final void utenfor(byte[] bArr) {
        String calendar = CalendarUtils.getCalendar();
        int phoneCurrentMinute = CalendarUtils.getPhoneCurrentMinute();
        UteListenerManager.getInstance().onBloodPressureRealTime(new BloodPressureInfo(calendar, CalendarUtils.getCalendarTime(calendar, phoneCurrentMinute), phoneCurrentMinute, bArr[3] & 255, bArr[4] & 255));
    }

    public final int utenif(byte[] bArr) {
        return ((bArr[5] & 255) * 60) + (bArr[6] & 255);
    }
}
