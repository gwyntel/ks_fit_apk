package com.yc.utesdk.data;

import android.text.TextUtils;
import androidx.core.view.MotionEventCompat;
import com.yc.utesdk.bean.StepOneDayAllInfo;
import com.yc.utesdk.bean.StepOneHourInfo;
import com.yc.utesdk.bean.StepRunHourInfo;
import com.yc.utesdk.bean.StepWalkHourInfo;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.close.SWAlgorithmUtil;
import com.yc.utesdk.utils.close.UteCalculatorImp;
import com.yc.utesdk.utils.open.CalendarUtils;
import com.yc.utesdk.utils.open.SPUtil;
import java.util.ArrayList;
import java.util.Calendar;

/* loaded from: classes4.dex */
public class DataProcessing {
    private static DataProcessing instance = null;
    public static boolean isSyncStep = false;
    private final String STEP_DATA = "LogUtils";
    private int mStepCount = 0;
    private float mStepDistance = 0.0f;
    private float mStepCalories = 0.0f;
    private int[] colorIndex = {0, 1, 2, 3, 4};
    private int lastHourStep = 0;
    private float lastHourDistance = 0.0f;
    private float lastHourCalories = 0.0f;
    private int lastHourRunSteps = 0;
    private int lastHourRunDurationTime = 0;
    private int lastHourWalkSteps = 0;
    private int lastHourWalkDurationTime = 0;
    private String lastCalendarB1 = "";
    private String lastCalendarB2 = "";
    private String lastCalendarRate = "";
    private int lastTimeB3 = 0;
    private int lastHourB2 = 25;
    private String lastCalendarB3 = "";
    private int offLineOneDayTotalstep = 0;
    private float offLineOneDayTotalDistance = 0.0f;
    private float offLineOneDayTotalCalories = 0.0f;
    private int offLineOneDayRunSteps = 0;
    private int offLineOneDayRunDurationTime = 0;
    private int offLineOneDayWalkSteps = 0;
    private int offLineOneDayWalkDurationTime = 0;
    private String CRC_LAST_CHECK = "";
    private int tempSteps = 0;
    private float tempDistance = 0.0f;
    private float tempCalories = 0.0f;
    private int offLineOneDayRunStepsTemp = 0;
    private int offLineOneDayRunDurationTimeTemp = 0;
    private int offLineOneDayWalkStepsTemp = 0;
    private int offLineOneDayWalkDurationTimeTemp = 0;
    private int currentRunSteps = 0;
    private int currentRunDurationTime = 0;
    private int currentWalkSteps = 0;
    private int currentWalkDurationTime = 0;
    private ArrayList<StepOneHourInfo> mStepOneHourArrayInfo = new ArrayList<>();
    private ArrayList<StepRunHourInfo> mStepRunHourArrayInfo = new ArrayList<>();
    private ArrayList<StepWalkHourInfo> mStepWalkHourArrayInfo = new ArrayList<>();
    private ArrayList<StepOneDayAllInfo> mStepOneDayAllArrayInfo = new ArrayList<>();
    private ArrayList<StepOneDayAllInfo> mStepOneDayAllArrayInfoTemp = new ArrayList<>();
    private final int TYPE_START_RUN_TIME = 0;
    private final int TYPE_END_RUN_TIME = 1;
    private final int TYPE_START_WALK_TIME = 2;
    private final int TYPE_END_WALK_TIME = 3;
    private UteCalculatorImp mPedometerSettings = UteCalculatorImp.getInstance();

    public static synchronized DataProcessing getInstance() {
        try {
            if (instance == null) {
                instance = new DataProcessing();
            }
        } catch (Throwable th) {
            throw th;
        }
        return instance;
    }

    public void clearStepList() {
        this.mStepOneHourArrayInfo = new ArrayList<>();
        this.mStepRunHourArrayInfo = new ArrayList<>();
        this.mStepWalkHourArrayInfo = new ArrayList<>();
    }

    public void clearStepTotalList() {
        this.mStepOneDayAllArrayInfo = new ArrayList<>();
    }

    public void dealWithCyweeStep(String str, byte[] bArr) {
        int i2 = bArr[1] & 255;
        if (i2 == 1) {
            stepRealTimeDataOperateForSW(bArr);
        } else {
            if (i2 != 2) {
                return;
            }
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            stepOffLineDataOperateForSW(bArr, str, true);
        }
    }

    public int getStepCount() {
        return this.mStepCount;
    }

    public void setStepCount(int i2) {
        this.mStepCount = i2;
    }

    public void stepOffLineDataOperate(byte[] bArr, String str, boolean z2) {
        float fCalculateDistance;
        float fCalculateCalories;
        int iUtenbyte;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        String str2;
        int i9;
        String str3;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        float fCalculateDistance2;
        float fCalculateCalories2;
        int i19;
        int i20;
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        UteListenerManager.getInstance().onStepSyncing();
        if (str.equals("B2FD")) {
            float fCalculateCalories3 = this.mPedometerSettings.calculateCalories(this.offLineOneDayRunSteps, 1);
            float fCalculateDistance3 = this.mPedometerSettings.calculateDistance(this.offLineOneDayRunSteps, 1);
            float fCalculateCalories4 = this.mPedometerSettings.calculateCalories(this.offLineOneDayWalkSteps, 0);
            float fCalculateDistance4 = this.mPedometerSettings.calculateDistance(this.offLineOneDayWalkSteps, 0);
            if (z2) {
                fCalculateDistance = fCalculateDistance3 + fCalculateDistance4;
                fCalculateCalories = fCalculateCalories3 + fCalculateCalories4;
            } else {
                fCalculateDistance = this.mPedometerSettings.calculateDistance(this.offLineOneDayTotalstep, 0);
                fCalculateCalories = this.mPedometerSettings.calculateCalories(this.offLineOneDayTotalstep, 0);
                LogUtils.d("LogUtils", "今天的卡路里到1 calories =" + fCalculateCalories);
            }
            StepOneDayAllInfo stepOneDayAllInfo = new StepOneDayAllInfo(this.lastCalendarB2, this.offLineOneDayTotalstep, fCalculateCalories, fCalculateDistance, this.offLineOneDayRunSteps, fCalculateCalories3, fCalculateDistance3, this.offLineOneDayRunDurationTime, this.offLineOneDayWalkSteps, fCalculateCalories4, fCalculateDistance4, this.offLineOneDayWalkDurationTime, this.mStepOneHourArrayInfo, this.mStepRunHourArrayInfo, this.mStepWalkHourArrayInfo);
            if (this.offLineOneDayTotalstep > 0) {
                this.mStepOneDayAllArrayInfo.add(stepOneDayAllInfo);
            }
            LogUtils.i("LogUtils", "同步完成,最大数组加一天 lastCalendarB2 =" + this.lastCalendarB2 + "，mStepOneHourArrayInfo.size() =" + this.mStepOneHourArrayInfo.size() + ",offLineOneDayTotalstep =" + this.offLineOneDayTotalstep);
            this.mStepOneDayAllArrayInfoTemp = new ArrayList<>();
            if (this.lastCalendarB2.equals(CalendarUtils.getCalendar(0))) {
                this.mStepOneDayAllArrayInfoTemp.add(stepOneDayAllInfo);
                if (this.mStepOneDayAllArrayInfoTemp.size() > 0) {
                    UteListenerManager.getInstance().onStepChange(this.mStepOneDayAllArrayInfoTemp.get(0));
                }
            }
            isSyncStep = false;
            UteListenerManager.getInstance().onStepSyncSuccess(this.mStepOneDayAllArrayInfo);
            clearStepList();
            clearStepTotalList();
            this.offLineOneDayTotalstep = 0;
            this.offLineOneDayRunSteps = 0;
            this.offLineOneDayRunDurationTime = 0;
            this.offLineOneDayWalkSteps = 0;
            this.offLineOneDayWalkDurationTime = 0;
            this.lastCalendarB2 = "";
            return;
        }
        isSyncStep = true;
        CommandTimeOutUtils.getInstance().setCommandTimeOut(6);
        String strUtenfor = utenfor(bArr);
        int iUtendo = utendo(bArr);
        int iUtenif = utenif(bArr);
        if (z2) {
            int iUtendo2 = utendo(bArr, 0);
            int iUtendo3 = utendo(bArr, 1);
            int iUtenint = utenint(bArr);
            int iUtennew = utennew(bArr);
            int iUtendo4 = utendo(bArr, 2);
            int iUtendo5 = utendo(bArr, 3);
            int iUtentry = utentry(bArr);
            iUtenbyte = utenbyte(bArr);
            i8 = iUtendo5;
            i6 = iUtenint;
            i3 = iUtendo3;
            i2 = iUtendo2;
            i5 = iUtennew;
            i4 = iUtentry;
            i7 = iUtendo4;
        } else {
            iUtenbyte = 0;
            i2 = 0;
            i3 = 0;
            i4 = 0;
            i5 = 0;
            i6 = 0;
            i7 = 0;
            i8 = 0;
        }
        LogUtils.i("LogUtils", "mCalendar = " + strUtenfor + ",currentHour =" + iUtendo + ",currentHourStep =" + iUtenif + ",startRunTime =" + (i2 / 60) + "：" + (i2 % 60) + ",endRunTime =" + (i3 / 60) + "：" + (i3 % 60) + ",runDurationTime =" + i6 + ",runSteps =" + i5 + ",startWalkTime =" + (i7 / 60) + "：" + (i7 % 60) + ",endWalkTime =" + (i8 / 60) + "：" + (i8 % 60) + ",walkDurationTime =" + i4 + ",walkSteps =" + iUtenbyte);
        if (strUtenfor.length() <= 2 || !strUtenfor.substring(0, 3).equals("000")) {
            int i21 = iUtendo * 60;
            StepOneHourInfo stepOneHourInfo = new StepOneHourInfo(strUtenfor, i21, iUtenif);
            float fCalculateDistance5 = this.mPedometerSettings.calculateDistance(i5, 1);
            float fCalculateCalories5 = this.mPedometerSettings.calculateCalories(i5, 1);
            float fCalculateDistance6 = this.mPedometerSettings.calculateDistance(iUtenbyte, 0);
            float fCalculateCalories6 = this.mPedometerSettings.calculateCalories(iUtenbyte, 0);
            int i22 = i4;
            int i23 = i5;
            int i24 = i6;
            StepRunHourInfo stepRunHourInfo = new StepRunHourInfo(strUtenfor, i21, i2, i3, i6, i5, fCalculateDistance5, fCalculateCalories5);
            StepWalkHourInfo stepWalkHourInfo = new StepWalkHourInfo(strUtenfor, i21, i7, i8, i22, iUtenbyte, fCalculateDistance6, fCalculateCalories6);
            if (!strUtenfor.equals(this.lastCalendarB2)) {
                str2 = "LogUtils";
                LogUtils.i(str2, "不同一天 mStepOneHourArrayInfo.size() = " + this.mStepOneHourArrayInfo.size());
                if (this.mStepOneHourArrayInfo.size() != 0) {
                    float fCalculateCalories7 = this.mPedometerSettings.calculateCalories(this.offLineOneDayRunSteps, 1);
                    float fCalculateDistance7 = this.mPedometerSettings.calculateDistance(this.offLineOneDayRunSteps, 1);
                    float fCalculateCalories8 = this.mPedometerSettings.calculateCalories(this.offLineOneDayWalkSteps, 0);
                    float fCalculateDistance8 = this.mPedometerSettings.calculateDistance(this.offLineOneDayWalkSteps, 0);
                    if (z2) {
                        fCalculateDistance2 = fCalculateDistance7 + fCalculateDistance8;
                        fCalculateCalories2 = fCalculateCalories7 + fCalculateCalories8;
                    } else {
                        fCalculateDistance2 = this.mPedometerSettings.calculateDistance(this.offLineOneDayTotalstep, 0);
                        fCalculateCalories2 = this.mPedometerSettings.calculateCalories(this.offLineOneDayTotalstep, 0);
                    }
                    i11 = iUtendo;
                    str3 = strUtenfor;
                    i12 = i22;
                    i9 = iUtenbyte;
                    i10 = i24;
                    i13 = i23;
                    StepOneDayAllInfo stepOneDayAllInfo2 = new StepOneDayAllInfo(this.lastCalendarB2, this.offLineOneDayTotalstep, fCalculateCalories2, fCalculateDistance2, this.offLineOneDayRunSteps, fCalculateCalories7, fCalculateDistance7, this.offLineOneDayRunDurationTime, this.offLineOneDayWalkSteps, fCalculateCalories8, fCalculateDistance8, this.offLineOneDayWalkDurationTime, this.mStepOneHourArrayInfo, this.mStepRunHourArrayInfo, this.mStepWalkHourArrayInfo);
                    if (!TextUtils.isEmpty(this.lastCalendarB2)) {
                        this.mStepOneDayAllArrayInfo.add(stepOneDayAllInfo2);
                        LogUtils.i(str2, "最大数组加1天的数据 lastCalendarB2=" + this.lastCalendarB2);
                    }
                    clearStepList();
                } else {
                    i9 = iUtenbyte;
                    str3 = strUtenfor;
                    i10 = i24;
                    i11 = iUtendo;
                    i12 = i22;
                    i13 = i23;
                }
                LogUtils.i(str2, "开始同步后的第一条数据或新一天的第一条数据");
                this.mStepOneHourArrayInfo.add(stepOneHourInfo);
                this.mStepRunHourArrayInfo.add(stepRunHourInfo);
                this.mStepWalkHourArrayInfo.add(stepWalkHourInfo);
                this.offLineOneDayTotalstep = iUtenif;
                i14 = i13;
                this.offLineOneDayRunSteps = i14;
                i15 = i10;
                this.offLineOneDayRunDurationTime = i15;
                i16 = i9;
                this.offLineOneDayWalkSteps = i16;
                i17 = i12;
                this.offLineOneDayWalkDurationTime = i17;
                i18 = 0;
            } else if (this.lastHourB2 != iUtendo) {
                this.offLineOneDayTotalstep += iUtenif;
                i14 = i23;
                this.offLineOneDayRunSteps += i14;
                this.offLineOneDayRunDurationTime += i24;
                this.offLineOneDayWalkSteps += iUtenbyte;
                this.offLineOneDayWalkDurationTime += i22;
                this.mStepOneHourArrayInfo.add(stepOneHourInfo);
                this.mStepRunHourArrayInfo.add(stepRunHourInfo);
                this.mStepWalkHourArrayInfo.add(stepWalkHourInfo);
                str3 = strUtenfor;
                i11 = iUtendo;
                str2 = "LogUtils";
                i18 = 0;
                i16 = iUtenbyte;
                i15 = i24;
                i17 = i22;
            } else {
                str3 = strUtenfor;
                i11 = iUtendo;
                str2 = "LogUtils";
                i14 = i23;
                i18 = 0;
                i16 = iUtenbyte;
                i15 = i24;
                i17 = i22;
            }
            String str4 = str3;
            if (str4.equals(CalendarUtils.getCalendar(i18))) {
                i19 = i11;
                if (i19 >= utendo()) {
                    this.tempSteps = this.offLineOneDayTotalstep - iUtenif;
                    this.offLineOneDayRunStepsTemp = this.offLineOneDayRunSteps - i14;
                    this.offLineOneDayRunDurationTimeTemp = this.offLineOneDayRunDurationTime - i15;
                    this.offLineOneDayWalkStepsTemp = this.offLineOneDayWalkSteps - i16;
                    i20 = this.offLineOneDayWalkDurationTime - i17;
                } else {
                    this.tempSteps = this.offLineOneDayTotalstep;
                    this.offLineOneDayRunStepsTemp = this.offLineOneDayRunSteps;
                    this.offLineOneDayRunDurationTimeTemp = this.offLineOneDayRunDurationTime;
                    this.offLineOneDayWalkStepsTemp = this.offLineOneDayWalkSteps;
                    i20 = this.offLineOneDayWalkDurationTime;
                }
                this.offLineOneDayWalkDurationTimeTemp = i20;
                this.mStepCount = this.offLineOneDayTotalstep;
                this.currentRunSteps = this.offLineOneDayRunSteps;
                this.currentRunDurationTime = this.offLineOneDayRunDurationTime;
                this.currentWalkSteps = this.offLineOneDayWalkSteps;
                this.currentWalkDurationTime = this.offLineOneDayWalkDurationTime;
            } else {
                i19 = i11;
            }
            LogUtils.i(str2, "offLineOneDayWalkStepsTemp =" + this.offLineOneDayWalkStepsTemp);
            this.lastCalendarB2 = str4;
            this.lastHourB2 = i19;
            SPUtil.getInstance().setYcPedLastHourValue(i19);
        }
    }

    public void stepOffLineDataOperateForSW(byte[] bArr, String str, boolean z2) {
        float f2;
        String str2;
        int i2;
        int i3;
        float f3;
        int i4;
        float f4;
        float f5;
        int i5;
        if (str.substring(0, 6).toUpperCase().equals("EB02FD")) {
            float f6 = this.offLineOneDayTotalCalories;
            float f7 = this.offLineOneDayTotalDistance;
            StepOneDayAllInfo stepOneDayAllInfo = new StepOneDayAllInfo(this.lastCalendarB2, this.offLineOneDayTotalstep, f6, f7, this.offLineOneDayRunSteps, 0.0f, 0.0f, this.offLineOneDayRunDurationTime, this.offLineOneDayWalkSteps, f6, f7, this.offLineOneDayWalkDurationTime, this.mStepOneHourArrayInfo, this.mStepRunHourArrayInfo, this.mStepWalkHourArrayInfo);
            this.mStepOneDayAllArrayInfo.add(stepOneDayAllInfo);
            this.mStepOneDayAllArrayInfoTemp = new ArrayList<>();
            if (this.lastCalendarB2.equals(CalendarUtils.getCalendar(0))) {
                this.mStepOneDayAllArrayInfoTemp.add(stepOneDayAllInfo);
            }
            clearStepList();
            UteListenerManager.getInstance().onStepSyncSuccess(this.mStepOneDayAllArrayInfo);
            clearStepTotalList();
            this.offLineOneDayTotalstep = 0;
            this.offLineOneDayTotalDistance = 0.0f;
            this.offLineOneDayTotalCalories = 0.0f;
            this.offLineOneDayRunSteps = 0;
            this.offLineOneDayRunDurationTime = 0;
            this.offLineOneDayWalkSteps = 0;
            this.offLineOneDayWalkDurationTime = 0;
            this.lastCalendarB2 = "";
            return;
        }
        String bleDate = SWAlgorithmUtil.getInstance().getBleDate(bArr);
        int bleCurrentHour = SWAlgorithmUtil.getInstance().getBleCurrentHour(bArr);
        int bleCurrentHourStep = SWAlgorithmUtil.getInstance().getBleCurrentHourStep(bArr);
        float bleCurrentHourDistance = SWAlgorithmUtil.getInstance().getBleCurrentHourDistance(bArr);
        float bleCurrentHourCalories = SWAlgorithmUtil.getInstance().getBleCurrentHourCalories(bArr);
        int i6 = bleCurrentHour * 60;
        int i7 = i6 + 60;
        LogUtils.i("LogUtils", "离线calendar = " + bleDate + ",currentHour =" + bleCurrentHour + ",currentHourStep =" + bleCurrentHourStep + ",currentDistance =" + bleCurrentHourDistance + ",currentCalories =" + bleCurrentHourCalories + ",tempSteps =" + this.tempSteps + ",startRunTime =0：0,endRunTime =0：0,runDurationTime =0,runSteps =0,startWalkTime =" + (i6 / 60) + "：" + (i6 % 60) + ",endWalkTime =" + (i7 / 60) + "：" + (i7 % 60) + ",walkDurationTime =60,walkSteps =" + bleCurrentHourStep);
        StringBuilder sb = new StringBuilder();
        sb.append("离线currentDistance = ");
        sb.append(bleCurrentHourDistance);
        sb.append(",currentCalories=");
        sb.append(bleCurrentHourCalories);
        LogUtils.i("LogUtils", sb.toString());
        if (bleDate.length() <= 2 || !bleDate.substring(0, 3).equals("000")) {
            StepOneHourInfo stepOneHourInfo = new StepOneHourInfo(bleDate, i6, bleCurrentHourStep);
            StepRunHourInfo stepRunHourInfo = new StepRunHourInfo(bleDate, i6, 0, 0, 0, 0, 0.0f, 0.0f);
            StepWalkHourInfo stepWalkHourInfo = new StepWalkHourInfo(bleDate, i6, i6, i7, 60, bleCurrentHourStep, bleCurrentHourDistance, bleCurrentHourCalories);
            if (bleDate.equals(this.lastCalendarB2)) {
                if (this.lastHourB2 != bleCurrentHour) {
                    this.offLineOneDayTotalstep += bleCurrentHourStep;
                    this.offLineOneDayTotalDistance += bleCurrentHourDistance;
                    this.offLineOneDayTotalCalories += bleCurrentHourCalories;
                    this.offLineOneDayRunSteps = this.offLineOneDayRunSteps;
                    this.offLineOneDayRunDurationTime = this.offLineOneDayRunDurationTime;
                    this.offLineOneDayWalkSteps += bleCurrentHourStep;
                    this.offLineOneDayWalkDurationTime += 60;
                    this.mStepOneHourArrayInfo.add(stepOneHourInfo);
                    this.mStepRunHourArrayInfo.add(stepRunHourInfo);
                    this.mStepWalkHourArrayInfo.add(stepWalkHourInfo);
                }
                f5 = bleCurrentHourCalories;
                str2 = bleDate;
                i2 = bleCurrentHour;
                i4 = bleCurrentHourStep;
                f4 = bleCurrentHourDistance;
            } else {
                if (this.mStepOneHourArrayInfo.size() != 0) {
                    float f8 = this.offLineOneDayTotalCalories;
                    float f9 = this.offLineOneDayTotalDistance;
                    i2 = bleCurrentHour;
                    str2 = bleDate;
                    f2 = bleCurrentHourCalories;
                    f3 = bleCurrentHourDistance;
                    i3 = bleCurrentHourStep;
                    StepOneDayAllInfo stepOneDayAllInfo2 = new StepOneDayAllInfo(this.lastCalendarB2, this.offLineOneDayTotalstep, f8, f9, this.offLineOneDayRunSteps, 0.0f, 0.0f, this.offLineOneDayRunDurationTime, this.offLineOneDayWalkSteps, f8, f9, this.offLineOneDayWalkDurationTime, this.mStepOneHourArrayInfo, this.mStepRunHourArrayInfo, this.mStepWalkHourArrayInfo);
                    if (!TextUtils.isEmpty(this.lastCalendarB2)) {
                        this.mStepOneDayAllArrayInfo.add(stepOneDayAllInfo2);
                    }
                    clearStepList();
                } else {
                    f2 = bleCurrentHourCalories;
                    str2 = bleDate;
                    i2 = bleCurrentHour;
                    i3 = bleCurrentHourStep;
                    f3 = bleCurrentHourDistance;
                }
                this.mStepOneHourArrayInfo.add(stepOneHourInfo);
                this.mStepRunHourArrayInfo.add(stepRunHourInfo);
                this.mStepWalkHourArrayInfo.add(stepWalkHourInfo);
                i4 = i3;
                this.offLineOneDayTotalstep = i4;
                f4 = f3;
                this.offLineOneDayTotalDistance = f4;
                f5 = f2;
                this.offLineOneDayTotalCalories = f5;
                this.offLineOneDayRunSteps = 0;
                this.offLineOneDayRunDurationTime = 0;
                this.offLineOneDayWalkSteps = i4;
                this.offLineOneDayWalkDurationTime = 60;
            }
            String str3 = str2;
            if (str3.equals(CalendarUtils.getCalendar(0))) {
                i5 = i2;
                if (i5 >= utendo()) {
                    this.tempSteps = this.offLineOneDayTotalstep - i4;
                    this.tempDistance = this.offLineOneDayTotalDistance - f4;
                    this.tempCalories = this.offLineOneDayTotalCalories - f5;
                    this.offLineOneDayRunStepsTemp = this.offLineOneDayRunSteps;
                    this.offLineOneDayRunDurationTimeTemp = this.offLineOneDayRunDurationTime;
                    this.offLineOneDayWalkStepsTemp = this.offLineOneDayWalkSteps - i4;
                    this.offLineOneDayWalkDurationTimeTemp = this.offLineOneDayWalkDurationTime - 60;
                } else {
                    this.tempSteps = this.offLineOneDayTotalstep;
                    this.tempDistance = this.offLineOneDayTotalDistance;
                    this.tempCalories = this.offLineOneDayTotalCalories;
                    this.offLineOneDayRunStepsTemp = this.offLineOneDayRunSteps;
                    this.offLineOneDayRunDurationTimeTemp = this.offLineOneDayRunDurationTime;
                    this.offLineOneDayWalkStepsTemp = this.offLineOneDayWalkSteps;
                    this.offLineOneDayWalkDurationTimeTemp = this.offLineOneDayWalkDurationTime;
                    this.lastHourStep = 0;
                }
                this.mStepCount = this.offLineOneDayTotalstep;
                this.mStepDistance = this.offLineOneDayTotalDistance;
                this.mStepCalories = this.offLineOneDayTotalCalories;
                this.currentRunSteps = this.offLineOneDayRunSteps;
                this.currentRunDurationTime = this.offLineOneDayRunDurationTime;
                this.currentWalkSteps = this.offLineOneDayWalkSteps;
                this.currentWalkDurationTime = this.offLineOneDayWalkDurationTime;
            } else {
                i5 = i2;
            }
            this.lastCalendarB2 = str3;
            this.lastHourB2 = i5;
            SPUtil.getInstance().setYcPedLastHourValue(i5);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x01e1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void stepRealTimeDataOperate(byte[] r40, boolean r41) {
        /*
            Method dump skipped, instructions count: 1023
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.data.DataProcessing.stepRealTimeDataOperate(byte[], boolean):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0120  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void stepRealTimeDataOperateForSW(byte[] r35) {
        /*
            Method dump skipped, instructions count: 980
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.data.DataProcessing.stepRealTimeDataOperateForSW(byte[]):void");
    }

    public final int utenbyte(byte[] bArr) {
        return ((bArr[16] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[17] & 255);
    }

    public final int utendo(byte[] bArr) {
        return bArr[5] & 255;
    }

    public final String utenfor(byte[] bArr) {
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
        return String.valueOf(i4) + strValueOf2 + strValueOf;
    }

    public final int utenif(byte[] bArr) {
        return ((bArr[6] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[7] & 255);
    }

    public final int utenint(byte[] bArr) {
        return bArr[10] & 255;
    }

    public final int utennew(byte[] bArr) {
        return ((bArr[11] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[12] & 255);
    }

    public final int utentry(byte[] bArr) {
        return bArr[15] & 255;
    }

    public final int utendo() {
        return Calendar.getInstance().get(11);
    }

    public final int utendo(byte[] bArr, int i2) {
        byte b2;
        int i3;
        int i4 = bArr[5] & 255;
        if (i2 == 0) {
            b2 = bArr[8];
        } else if (i2 == 1) {
            b2 = bArr[9];
        } else if (i2 == 2) {
            b2 = bArr[13];
        } else {
            if (i2 != 3) {
                i3 = 0;
                return (i4 * 60) + i3;
            }
            b2 = bArr[14];
        }
        i3 = b2 & 255;
        return (i4 * 60) + i3;
    }
}
