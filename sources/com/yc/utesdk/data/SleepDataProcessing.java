package com.yc.utesdk.data;

import android.text.TextUtils;
import androidx.core.view.MotionEventCompat;
import com.google.gson.Gson;
import com.yc.utesdk.bean.CyweeSleepSectionInfo;
import com.yc.utesdk.bean.CyweeSleepTimeInfo;
import com.yc.utesdk.bean.SleepInfo;
import com.yc.utesdk.bean.SleepStateInfo;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.close.SleepRawDataInfo;
import com.yc.utesdk.utils.open.GBUtils;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTimeConstants;

/* loaded from: classes4.dex */
public class SleepDataProcessing {
    private static final String TAG = "SleepData--";
    private static SleepDataProcessing instance;
    private String bandAlgorithmSleepData = "";
    private List<SleepRawDataInfo> mSleepRawDataInfoList = new ArrayList();
    private SleepRawDataInfo mSleepRawDataInfo = new SleepRawDataInfo();

    public static SleepDataProcessing getInstance() {
        if (instance == null) {
            instance = new SleepDataProcessing();
        }
        return instance;
    }

    public void dealWithBandAlgorithm(String str, String str2, byte[] bArr) {
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        if (!str.equals("31")) {
            if (str.equals("32")) {
                CommandTimeOutUtils.getInstance().setCommandTimeOut(7);
                this.bandAlgorithmSleepData += str2.substring(2);
                return;
            }
            return;
        }
        int i2 = bArr[1] & 255;
        if (i2 != 1) {
            if (i2 != 2) {
                return;
            }
            if (!TextUtils.isEmpty(this.bandAlgorithmSleepData)) {
                this.mSleepRawDataInfo.setRawData(this.bandAlgorithmSleepData);
                this.mSleepRawDataInfoList.add(this.mSleepRawDataInfo);
                this.bandAlgorithmSleepData = "";
            }
            UteListenerManager.getInstance().onSleepSyncSuccess(utendo(this.mSleepRawDataInfoList));
            return;
        }
        CommandTimeOutUtils.getInstance().setCommandTimeOut(7);
        if (!TextUtils.isEmpty(this.bandAlgorithmSleepData)) {
            this.mSleepRawDataInfo.setRawData(this.bandAlgorithmSleepData);
            this.mSleepRawDataInfoList.add(this.mSleepRawDataInfo);
            this.bandAlgorithmSleepData = "";
        }
        SleepRawDataInfo sleepRawDataInfo = new SleepRawDataInfo();
        this.mSleepRawDataInfo = sleepRawDataInfo;
        sleepRawDataInfo.setCalendar(utendo(bArr));
        LogUtils.i(TAG, "mCalendar =" + utendo(bArr) + ",bandAlgorithmSleepCount =" + (bArr[6] & 255));
        UteListenerManager.getInstance().onSleepSyncing();
    }

    public void dealWithCyweeSleep(String str, byte[] bArr) {
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        int i2 = bArr[1] & 255;
        if (i2 != 1) {
            if (i2 == 2) {
                CommandTimeOutUtils.getInstance().setCommandTimeOut(7);
                this.bandAlgorithmSleepData += str.substring(4);
                return;
            }
            if (i2 != 3) {
                return;
            }
            if (!TextUtils.isEmpty(this.bandAlgorithmSleepData)) {
                this.mSleepRawDataInfo.setRawData(this.bandAlgorithmSleepData);
                this.mSleepRawDataInfoList.add(this.mSleepRawDataInfo);
                this.bandAlgorithmSleepData = "";
            }
            UteListenerManager.getInstance().onCyweeSleepSyncSuccess(saveCyweeSleepBleData(this.mSleepRawDataInfoList));
            return;
        }
        CommandTimeOutUtils.getInstance().setCommandTimeOut(7);
        if (!TextUtils.isEmpty(this.bandAlgorithmSleepData)) {
            this.mSleepRawDataInfo.setRawData(this.bandAlgorithmSleepData);
            this.mSleepRawDataInfoList.add(this.mSleepRawDataInfo);
            this.bandAlgorithmSleepData = "";
        }
        SleepRawDataInfo sleepRawDataInfo = new SleepRawDataInfo();
        this.mSleepRawDataInfo = sleepRawDataInfo;
        sleepRawDataInfo.setCalendar(utendo(bArr));
        LogUtils.i(TAG, "mCalendar =" + utendo(bArr) + ",bandAlgorithmSleepCount =" + (bArr[6] & 255));
        UteListenerManager.getInstance().onSleepSyncing();
    }

    public synchronized List<CyweeSleepTimeInfo> saveCyweeSleepBleData(List<SleepRawDataInfo> list) {
        ArrayList arrayList;
        int i2;
        CyweeSleepTimeInfo cyweeSleepTimeInfo;
        ArrayList arrayList2;
        int i3;
        int i4;
        byte[] bArr;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        try {
            LogUtils.i(TAG, "list =" + new Gson().toJson(list));
            arrayList = new ArrayList();
            for (int i13 = 0; i13 < list.size(); i13++) {
                byte[] bArrHexStringToBytes = GBUtils.getInstance().hexStringToBytes(list.get(i13).getRawData());
                if (bArrHexStringToBytes != null) {
                    int i14 = 6;
                    int length = bArrHexStringToBytes.length / 6;
                    LogUtils.i(TAG, "data.length =" + bArrHexStringToBytes.length + ",sectionCount =" + length);
                    CyweeSleepTimeInfo cyweeSleepTimeInfo2 = new CyweeSleepTimeInfo();
                    new SleepStateInfo();
                    ArrayList arrayList3 = new ArrayList();
                    new SleepStateInfo();
                    ArrayList arrayList4 = new ArrayList();
                    CyweeSleepSectionInfo cyweeSleepSectionInfo = new CyweeSleepSectionInfo();
                    ArrayList arrayList5 = new ArrayList();
                    int i15 = 0;
                    int i16 = 0;
                    int i17 = 0;
                    int i18 = 0;
                    int i19 = 0;
                    int i20 = 0;
                    int i21 = 0;
                    int i22 = 0;
                    int i23 = 0;
                    int i24 = 0;
                    boolean z2 = true;
                    int i25 = 0;
                    int i26 = 0;
                    int i27 = 0;
                    int i28 = 0;
                    int i29 = 0;
                    int i30 = 0;
                    while (i16 < length) {
                        int i31 = i14 * i16;
                        int i32 = bArrHexStringToBytes[i31] & 255;
                        ArrayList arrayList6 = arrayList;
                        int i33 = bArrHexStringToBytes[i31 + 1] & 255;
                        if (i32 > 23 || i33 > 59) {
                            i2 = length;
                            cyweeSleepTimeInfo = cyweeSleepTimeInfo2;
                            arrayList2 = arrayList3;
                            i3 = i16;
                            i4 = i21;
                            bArr = bArrHexStringToBytes;
                            i15 = i15;
                            i20 = i20;
                            i18 = i18;
                        } else {
                            int i34 = (i32 * 60) + i33;
                            cyweeSleepTimeInfo = cyweeSleepTimeInfo2;
                            int i35 = ((bArrHexStringToBytes[i31 + 4] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArrHexStringToBytes[i31 + 5] & 255);
                            int i36 = (i34 + i35) % DateTimeConstants.MINUTES_PER_DAY;
                            ArrayList arrayList7 = arrayList3;
                            int i37 = (bArrHexStringToBytes[i31 + 2] & 255) - 1;
                            if (i37 < 0) {
                                i37 = 0;
                            }
                            if (i16 == 0) {
                                i23 = i34;
                            }
                            if (i16 == length - 1) {
                                i22 = i36;
                            }
                            int i38 = i21;
                            if (i35 >= 0) {
                                i24 += i35;
                                i38 += i35;
                            }
                            bArr = bArrHexStringToBytes;
                            int i39 = i24;
                            i2 = length;
                            if (z2) {
                                i5 = i34;
                                z2 = false;
                            } else {
                                i5 = i30;
                            }
                            i3 = i16;
                            if (i37 == 0) {
                                i20 += i35;
                                i6 = i35;
                                i8 = i15;
                                i7 = i38;
                                i12 = i25 + i35;
                                i9 = i26;
                                i10 = i28;
                                i11 = i29;
                            } else {
                                int i40 = i20;
                                if (i37 == 1) {
                                    i19 += i35;
                                    i26 += i35;
                                } else if (i37 == 2) {
                                    i18 += i35;
                                    i15++;
                                    i27 += i35;
                                    i28++;
                                } else {
                                    int i41 = i18;
                                    int i42 = i15;
                                    if (i37 == 3) {
                                        i17 += i35;
                                        i29 += i35;
                                    }
                                    i6 = i35;
                                    i7 = i38;
                                    i8 = i42;
                                    i9 = i26;
                                    i10 = i28;
                                    i11 = i29;
                                    i20 = i40;
                                    i18 = i41;
                                    i12 = i25;
                                }
                                i6 = i35;
                                i8 = i15;
                                i7 = i38;
                                i12 = i25;
                                i9 = i26;
                                i10 = i28;
                                i11 = i29;
                                i20 = i40;
                            }
                            int i43 = i17;
                            int i44 = i27;
                            int i45 = i37 + 1;
                            int i46 = i37;
                            if (i45 == 255) {
                                cyweeSleepSectionInfo.setSleepTotalTime(i39);
                                cyweeSleepSectionInfo.setStartTime(i5);
                                cyweeSleepSectionInfo.setEndTime(i36);
                                cyweeSleepSectionInfo.setDeepTime(i12);
                                cyweeSleepSectionInfo.setLightTime(i9);
                                cyweeSleepSectionInfo.setAwakeTime(i44);
                                cyweeSleepSectionInfo.setAwakeCount(i10);
                                cyweeSleepSectionInfo.setRemTime(i11);
                                cyweeSleepSectionInfo.setSleepInfoList(arrayList4);
                                arrayList5.add(cyweeSleepSectionInfo);
                                arrayList4 = new ArrayList();
                                cyweeSleepSectionInfo = new CyweeSleepSectionInfo();
                                arrayList2 = arrayList7;
                                i39 = 0;
                                i36 = 0;
                                i12 = 0;
                                z2 = true;
                                i26 = 0;
                                i27 = 0;
                                i28 = 0;
                                i29 = 0;
                                i30 = 0;
                            } else {
                                arrayList4.add(new SleepStateInfo(list.get(i13).getCalendar(), i34, i36, i6, i45));
                                SleepStateInfo sleepStateInfo = new SleepStateInfo(list.get(i13).getCalendar(), i34, i36, i6, i45);
                                arrayList2 = arrayList7;
                                arrayList2.add(sleepStateInfo);
                                i29 = i11;
                                i26 = i9;
                                i30 = i5;
                                i28 = i10;
                                i27 = i44;
                            }
                            StringBuilder sb = new StringBuilder();
                            sb.append("startTime =");
                            sb.append(i34);
                            sb.append(",endTime =");
                            sb.append(i36);
                            sb.append(",duration =");
                            sb.append(i6);
                            sb.append(",sleepStatus =");
                            sb.append(i46);
                            sb.append(",sleepTotalTime=");
                            i4 = i7;
                            sb.append(i4);
                            LogUtils.i(TAG, sb.toString());
                            i15 = i8;
                            i17 = i43;
                            i24 = i39;
                            i25 = i12;
                        }
                        i16 = i3 + 1;
                        arrayList3 = arrayList2;
                        bArrHexStringToBytes = bArr;
                        length = i2;
                        arrayList = arrayList6;
                        i14 = 6;
                        i21 = i4;
                        cyweeSleepTimeInfo2 = cyweeSleepTimeInfo;
                    }
                    ArrayList arrayList8 = arrayList;
                    CyweeSleepTimeInfo cyweeSleepTimeInfo3 = cyweeSleepTimeInfo2;
                    int i47 = i21;
                    cyweeSleepTimeInfo3.setCalendar(list.get(i13).getCalendar());
                    cyweeSleepTimeInfo3.setStartSleepTime(i23);
                    cyweeSleepTimeInfo3.setEndSleepTime(i22);
                    cyweeSleepTimeInfo3.setTotalSleepTime(i47);
                    cyweeSleepTimeInfo3.setTotalDeepSleepTime(i20);
                    cyweeSleepTimeInfo3.setTotalLightSleepTime(i19);
                    cyweeSleepTimeInfo3.setTotalAwakeSleepTime(i18);
                    cyweeSleepTimeInfo3.setTotalAwakeCount(i15);
                    cyweeSleepTimeInfo3.setTotalRemSleepTime(i17);
                    cyweeSleepTimeInfo3.setCyweeSleepSectionInfos(arrayList5);
                    cyweeSleepTimeInfo3.setAllCyweeSleepInfos(arrayList3);
                    String json = new Gson().toJson(cyweeSleepTimeInfo3);
                    if (TextUtils.isEmpty(list.get(i13).getCalendar())) {
                        arrayList = arrayList8;
                    } else {
                        LogUtils.i(TAG, "统一保存睡眠数据 bandAlgorithmSleepDate =" + list.get(i13).getCalendar() + ",sleepTotalTime =" + i47);
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("统一保存睡眠数据 小睡 =");
                        sb2.append(json);
                        LogUtils.i(TAG, sb2.toString());
                        arrayList = arrayList8;
                        arrayList.add(cyweeSleepTimeInfo3);
                    }
                }
            }
            this.mSleepRawDataInfoList.clear();
            this.mSleepRawDataInfoList = new ArrayList();
        } catch (Throwable th) {
            throw th;
        }
        return arrayList;
    }

    public final String utendo(byte[] bArr) {
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

    public final synchronized List utendo(List list) {
        ArrayList arrayList;
        int i2;
        List list2 = list;
        synchronized (this) {
            try {
                arrayList = new ArrayList();
                int i3 = 0;
                while (i3 < list.size()) {
                    String calendar = ((SleepRawDataInfo) list2.get(i3)).getCalendar();
                    byte[] bArrHexStringToBytes = GBUtils.getInstance().hexStringToBytes(((SleepRawDataInfo) list2.get(i3)).getRawData());
                    if (bArrHexStringToBytes != null) {
                        int i4 = 6;
                        int length = bArrHexStringToBytes.length / 6;
                        LogUtils.i(TAG, "data.length =" + bArrHexStringToBytes.length + ",sectionCount =" + length);
                        ArrayList arrayList2 = new ArrayList();
                        int i5 = 0;
                        int i6 = 0;
                        int i7 = 0;
                        int i8 = 0;
                        int i9 = 0;
                        int i10 = 0;
                        int i11 = 0;
                        int i12 = 0;
                        int i13 = 0;
                        while (i8 < length) {
                            int i14 = i4 * i8;
                            int i15 = (bArrHexStringToBytes[i14 + 1] & 255) + ((bArrHexStringToBytes[i14] & 255) * 60);
                            int i16 = (bArrHexStringToBytes[i14 + 5] & 255) | ((bArrHexStringToBytes[i14 + 4] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
                            int i17 = (i15 + i16) % DateTimeConstants.MINUTES_PER_DAY;
                            int i18 = bArrHexStringToBytes[i14 + 2] & 255;
                            int i19 = i3;
                            int i20 = i5;
                            int i21 = i6;
                            int i22 = i7;
                            byte[] bArr = bArrHexStringToBytes;
                            int i23 = i8;
                            arrayList2.add(new SleepStateInfo(calendar, i15, i17, i16, i18));
                            i7 = i23 == 0 ? i15 : i22;
                            i6 = i23 == length + (-1) ? i17 : i21;
                            i5 = i16 >= 0 ? i20 + i16 : i20;
                            if (i18 == 1 && i16 >= 0) {
                                i9 += i16;
                            }
                            if (i18 == 2 && i16 >= 0) {
                                i10 += i16;
                            }
                            if (i18 == 3 && i16 >= 0) {
                                i11 += i16;
                                i12++;
                            }
                            if (i18 == 4 && i16 >= 0) {
                                i13 += i16;
                            }
                            LogUtils.i(TAG, "startTime =" + i15 + ",endTime =" + i17 + ",durationTime =" + i16 + ",sleepStatus =" + i18);
                            i8 = i23 + 1;
                            i3 = i19;
                            bArrHexStringToBytes = bArr;
                            i4 = 6;
                        }
                        i2 = i3;
                        int i24 = i5;
                        int i25 = i6;
                        int i26 = i7;
                        arrayList.add(new SleepInfo(calendar, i26, i25, i24, i9, i10, i11, i12, i13, arrayList2));
                        LogUtils.i(TAG, "calendar =" + calendar + ",startSleepTime =" + i26 + ",endSleepTime =" + i25 + ",totalSleepTime =" + i24);
                    } else {
                        i2 = i3;
                    }
                    i3 = i2 + 1;
                    list2 = list;
                }
                this.mSleepRawDataInfoList.clear();
                this.mSleepRawDataInfoList = new ArrayList();
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }
}
