package com.yc.utesdk.utils.close;

import android.content.Context;
import android.text.TextUtils;
import androidx.core.view.MotionEventCompat;
import com.google.gson.Gson;
import com.yc.utesdk.bean.EcgAlgoAnalysis;
import com.yc.utesdk.bean.EcgInfo;
import com.yc.utesdk.bean.EcgLabelInfo;
import com.yc.utesdk.ble.open.DeviceMode;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.CalendarUtils;
import com.yc.utesdk.utils.open.GBUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class EcgUtil {
    public static final int CHINA = 0;
    public static int COUNTRY = 0;
    public static final int ECG_COMPOSITION_TYPE_0 = 0;
    public static final int ECG_COMPOSITION_TYPE_1 = 1;
    public static final int ECG_COMPOSITION_TYPE_2 = 2;
    public static final int ECG_COMPOSITION_TYPE_3 = 3;
    public static final int ECG_DIALOG_TYPE_SHARE = 0;
    public static final int ECG_MINE = 0;
    public static final int ECG_OTHER_PERSON = 1;
    public static final String ECG_START_TEST_FROM_BAND_KEY = "ecg_start_test_from_band_key";
    public static final int ECG_TEST_DISCONNECT = 0;
    public static final int ECG_TEST_SUCCESS = 2;
    public static final int ECG_TEST_TIMEOUT = 1;
    public static final int FOREIGN = 1;
    public static final int PADDING_1 = 1;
    public static final int PADDING_2 = 2;
    public static final int PADDING_3 = 3;
    public static final int PADDING_4 = 4;
    public static final int PADDING_5 = 5;
    public static final int ROUNDING_0 = 0;
    public static final int ROUNDING_1 = 1;
    public static int ecgFilterCnt = 1000;
    public static int ecgHZ = 100;
    private static EcgUtil instance = null;
    public static boolean isEcgInfoChange = false;
    private final String BODY_JSON = "body";
    private final String MOOD_JSON = "mood";
    private final String HABIT_JSON = "habit";
    private final String REMARKS_JSON = "remarks";
    private final String FIRST = "first";
    private final String SECOND = "second";
    private final String THIRD = "third";
    private final String FOURTH = "fourth";
    private final String FIFTH = "fifth";
    private final String SIXTH = "sixth";
    public final int SELECTED = 1;
    public final int UNSELECTED = 0;
    private EcgAlgoAnalysis mEcgAlgoAnalysis = EcgAlgoAnalysis.getInstance();
    private ArrayList<Double> ecgDataArray = new ArrayList<>();
    private boolean isSupCsEcg = DeviceMode.isHasFunction_8(4096);

    public static EcgUtil getInstance() {
        if (instance == null) {
            instance = new EcgUtil();
        }
        return instance;
    }

    public static int utendo(double d2, int i2) {
        int i3 = 0;
        while (i2 >= 2) {
            i2 = (int) (i2 / d2);
            i3++;
        }
        return i3;
    }

    public EcgInfo AnalysisBleEcgHistoryData(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        String strUtenfor = utenfor(bArr);
        int iUtenbreak = utenbreak(bArr);
        String calendarTime = getCalendarTime(bArr);
        int iUtendo = utendo(bArr);
        int iUtenchar = utenchar(bArr);
        int iUtenthis = utenthis(bArr);
        int iUtenbyte = utenbyte(bArr);
        int iUtengoto = utengoto(bArr);
        int iUtennew = utennew(bArr);
        this.mEcgAlgoAnalysis.resetVector();
        LogUtils.i("getHistoryDataResult ,calendar =" + strUtenfor + ",calendarTime =" + calendarTime + ",startTime =" + iUtenbreak + ",totalCount =0,ecgAverageRate =" + iUtendo + ",ecgHRV =" + iUtenchar + ",ecgRiskLevel =" + iUtenthis + ",ecgFatigueIndex =" + iUtenbyte + ",ecgStrength =" + iUtengoto);
        EcgInfo ecgInfo = new EcgInfo(strUtenfor, calendarTime, iUtenbreak, iUtendo, iUtenchar, iUtengoto, iUtenthis, iUtenbyte, 0, iUtennew, new Gson().toJson(arrayList));
        this.ecgDataArray = new ArrayList<>();
        return ecgInfo;
    }

    public EcgInfo AnalysisBleEcgRealTimeData(byte[] bArr, String str) {
        String calendar = CalendarUtils.getCalendar(0);
        CalendarUtils.getYearMonth(0);
        int phoneCurrentMinute = CalendarUtils.getPhoneCurrentMinute();
        String calendarAndTime2 = CalendarUtils.getCalendarAndTime2();
        ArrayList<Double> arrayList = new ArrayList<>();
        int iUtenif = utenif(bArr);
        int iUtenelse = utenelse(bArr);
        int iUtenvoid = utenvoid(bArr);
        int iUtencase = utencase(bArr);
        int iUtenlong = utenlong(bArr);
        int iUtentry = utentry(bArr);
        if (this.isSupCsEcg) {
            arrayList = dealEcgSamplingDataCs(str);
        } else {
            int size = this.ecgDataArray.size();
            int i2 = ecgFilterCnt;
            if (size > i2) {
                arrayList.addAll(this.ecgDataArray.subList(i2, size));
            }
        }
        this.mEcgAlgoAnalysis.resetVector();
        LogUtils.i("心电实时结果  ecgDataArray.zize= " + this.ecgDataArray.size() + ",ecgDataArray2.size=" + arrayList.size());
        LogUtils.i("AnalysisBleEcgRealTimeData ,calendar =" + calendar + ",calendarTime =" + calendarAndTime2 + ",startTime =" + phoneCurrentMinute + ",totalCount =0,ecgAverageRate =" + iUtenif + ",ecgHRV =" + iUtenelse + ",ecgRiskLevel =" + iUtenvoid + ",ecgFatigueIndex =" + iUtencase + ",ecgStrength =" + iUtenlong);
        EcgInfo ecgInfo = new EcgInfo(calendar, calendarAndTime2, phoneCurrentMinute, iUtenif, iUtenelse, iUtenlong, iUtenvoid, iUtencase, 0, iUtentry, new Gson().toJson(arrayList));
        this.ecgDataArray = new ArrayList<>();
        return ecgInfo;
    }

    public boolean alreadySyncThisEcgData(byte[] bArr, List<EcgInfo> list) {
        if (list == null) {
            return false;
        }
        String calendarTime = getCalendarTime(bArr);
        for (int i2 = 0; i2 < list.size(); i2++) {
            String calendarTime2 = list.get(i2).getCalendarTime();
            LogUtils.i("alreadySyncThisEcgData ,calendarTime =" + calendarTime + ",lastDate=" + calendarTime2);
            if (calendarTime2.equals(calendarTime)) {
                return true;
            }
        }
        return false;
    }

    public EcgLabelInfo analysisLabelJson(String str) throws JSONException {
        String str2;
        String str3;
        String str4;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        String str5;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        String str6;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        String str7;
        int i20;
        int i21;
        int i22;
        int i23;
        int i24;
        int i25;
        int i26;
        int i27;
        int i28;
        int i29;
        int i30;
        int i31;
        int i32;
        int i33;
        int i34;
        int i35;
        int i36;
        int i37;
        if (str == null || str.equals("")) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            LogUtils.i("jsonObject =" + jSONObject.toString());
            if (jSONObject.has("body")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("body");
                if (jSONObject2.has("first")) {
                    i33 = jSONObject2.getInt("first");
                    i37 = jSONObject2.getInt("second");
                    int i38 = jSONObject2.getInt("third");
                    int i39 = jSONObject2.getInt("fourth");
                    int i40 = jSONObject2.getInt("fifth");
                    i32 = jSONObject2.getInt("sixth");
                    i36 = i38;
                    i34 = i39;
                    str4 = "";
                    i35 = i40;
                    str2 = "remarks";
                } else {
                    str2 = "remarks";
                    str4 = "";
                    i32 = 0;
                    i33 = 0;
                    i34 = 0;
                    i35 = 0;
                    i36 = 0;
                    i37 = 0;
                }
                StringBuilder sb = new StringBuilder();
                str3 = "habit";
                sb.append("bodyFirst =");
                sb.append(i33);
                sb.append(",bodySecond =");
                sb.append(i37);
                sb.append(",bodyThird =");
                sb.append(i36);
                sb.append(",bodyFourth =");
                sb.append(i34);
                sb.append(",bodyFifth =");
                sb.append(i35);
                sb.append(",bodySixth =");
                sb.append(i32);
                LogUtils.i(sb.toString());
                i7 = i32;
                i2 = i33;
                i5 = i34;
                i6 = i35;
                i4 = i36;
                i3 = i37;
            } else {
                str2 = "remarks";
                str3 = "habit";
                str4 = "";
                i2 = 0;
                i3 = 0;
                i4 = 0;
                i5 = 0;
                i6 = 0;
                i7 = 0;
            }
            if (jSONObject.has("mood")) {
                JSONObject jSONObject3 = jSONObject.getJSONObject("mood");
                if (jSONObject3.has("first")) {
                    i27 = jSONObject3.getInt("first");
                    i28 = jSONObject3.getInt("second");
                    i29 = jSONObject3.getInt("third");
                    i30 = jSONObject3.getInt("fourth");
                    i31 = jSONObject3.getInt("fifth");
                    i26 = jSONObject3.getInt("sixth");
                } else {
                    i26 = 0;
                    i27 = 0;
                    i28 = 0;
                    i29 = 0;
                    i30 = 0;
                    i31 = 0;
                }
                LogUtils.i("moodFirst =" + i27 + ",moodSecond =" + i28 + ",moodThird =" + i29 + ",moodFourth =" + i30 + ",moodFifth =" + i31 + ",moodSixth =" + i26);
                i13 = i26;
                i8 = i27;
                i9 = i28;
                i10 = i29;
                i11 = i30;
                i12 = i31;
                str5 = str3;
            } else {
                str5 = str3;
                i8 = 0;
                i9 = 0;
                i10 = 0;
                i11 = 0;
                i12 = 0;
                i13 = 0;
            }
            if (jSONObject.has(str5)) {
                JSONObject jSONObject4 = jSONObject.getJSONObject(str5);
                if (jSONObject4.has("first")) {
                    i25 = jSONObject4.getInt("first");
                    i21 = jSONObject4.getInt("second");
                    i22 = jSONObject4.getInt("third");
                    i23 = jSONObject4.getInt("fourth");
                    i24 = jSONObject4.getInt("fifth");
                    i20 = jSONObject4.getInt("sixth");
                } else {
                    i20 = 0;
                    i21 = 0;
                    i22 = 0;
                    i23 = 0;
                    i24 = 0;
                    i25 = 0;
                }
                LogUtils.i("habitFirst =" + i25 + ",habitSecond =" + i21 + ",habitThird =" + i22 + ",habitFourth =" + i23 + ",habitFifth =" + i24 + ",habitSixth =" + i20);
                i19 = i20;
                i15 = i21;
                i16 = i22;
                i17 = i23;
                i18 = i24;
                i14 = i25;
                str6 = str2;
            } else {
                str6 = str2;
                i14 = 0;
                i15 = 0;
                i16 = 0;
                i17 = 0;
                i18 = 0;
                i19 = 0;
            }
            if (jSONObject.has(str6)) {
                String string = jSONObject.getString(str6);
                LogUtils.i("remarks =" + string);
                str7 = string;
            } else {
                str7 = str4;
            }
            return new EcgLabelInfo(i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16, i17, i18, i19, str7);
        } catch (JSONException unused) {
            return null;
        }
    }

    public float[] calculatePaddingInterval(int i2, EcgInfo ecgInfo) {
        ecgInfo.getBodyWeight();
        ecgInfo.getBodyHeight();
        boolean zIsBodyGender = ecgInfo.isBodyGender();
        int bodyAge = ecgInfo.getBodyAge();
        if (i2 != 0) {
            if (i2 == 1) {
                return new float[]{31.0f, 62.0f, 112.0f, 132.0f, 152.0f};
            }
            if (i2 == 2) {
                return new float[]{0.0f, 1.0f, 2.0f, 3.0f, 4.0f};
            }
            if (i2 != 3) {
                return null;
            }
            return new float[]{0.0f, 10.0f, 20.0f, 30.0f, 40.0f};
        }
        float[] fArr = new float[5];
        fArr[0] = 1.0f;
        fArr[4] = 300.0f;
        if (zIsBodyGender) {
            if (bodyAge < 30) {
                fArr[1] = 36.0f;
                fArr[2] = 54.0f;
                fArr[3] = 150.0f;
                return fArr;
            }
            if (bodyAge < 50) {
                fArr[1] = 22.0f;
                fArr[2] = 35.0f;
                fArr[3] = 150.0f;
                return fArr;
            }
            if (bodyAge < 70) {
                fArr[1] = 15.0f;
                fArr[2] = 23.0f;
                fArr[3] = 150.0f;
                return fArr;
            }
            if (bodyAge <= 70) {
                return fArr;
            }
            fArr[1] = 18.0f;
            fArr[2] = 23.0f;
            fArr[3] = 150.0f;
            return fArr;
        }
        if (bodyAge < 30) {
            fArr[1] = 26.0f;
            fArr[2] = 44.0f;
            fArr[3] = 150.0f;
            return fArr;
        }
        if (bodyAge < 50) {
            fArr[1] = 22.0f;
            fArr[2] = 32.0f;
            fArr[3] = 150.0f;
            return fArr;
        }
        if (bodyAge < 70) {
            fArr[1] = 19.0f;
            fArr[2] = 26.0f;
            fArr[3] = 150.0f;
            return fArr;
        }
        if (bodyAge <= 70) {
            return fArr;
        }
        fArr[1] = 18.0f;
        fArr[2] = 23.0f;
        fArr[3] = 150.0f;
        return fArr;
    }

    public int[] calculatePaddingStatus(ArrayList arrayList, EcgInfo ecgInfo) {
        int[] iArr = new int[4];
        int ecgHRV = ecgInfo.getEcgHRV();
        int ecgStrength = ecgInfo.getEcgStrength();
        int ecgRiskLevel = ecgInfo.getEcgRiskLevel();
        int ecgFatigueIndex = ecgInfo.getEcgFatigueIndex();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            float[] fArr = (float[]) arrayList.get(i2);
            if (i2 == 0) {
                float f2 = ecgHRV;
                if (f2 < fArr[1]) {
                    iArr[i2] = 1;
                } else if (f2 <= fArr[2]) {
                    iArr[i2] = 2;
                } else if (f2 <= fArr[3]) {
                    iArr[i2] = 3;
                } else {
                    iArr[i2] = 4;
                }
            } else if (i2 == 1) {
                float f3 = ecgStrength;
                if (f3 < fArr[1]) {
                    iArr[i2] = 1;
                } else if (f3 <= fArr[2]) {
                    iArr[i2] = 2;
                } else if (f3 <= fArr[3]) {
                    iArr[i2] = 3;
                } else {
                    iArr[i2] = 4;
                }
            } else if (i2 == 2) {
                float f4 = ecgRiskLevel;
                if (f4 < fArr[1]) {
                    iArr[i2] = 1;
                } else if (f4 <= fArr[2]) {
                    iArr[i2] = 2;
                } else if (f4 <= fArr[3]) {
                    iArr[i2] = 3;
                } else {
                    iArr[i2] = 4;
                }
            } else if (i2 == 3) {
                float f5 = ecgFatigueIndex;
                if (f5 < fArr[1]) {
                    iArr[i2] = 1;
                } else if (f5 <= fArr[2]) {
                    iArr[i2] = 2;
                } else if (f5 <= fArr[3]) {
                    iArr[i2] = 3;
                } else {
                    iArr[i2] = 4;
                }
            }
        }
        return iArr;
    }

    public String creatLabelJson(EcgLabelInfo ecgLabelInfo) throws JSONException {
        String remarks;
        try {
            JSONObject jSONObject = new JSONObject();
            LogUtils.i("jsonObject  info =" + ecgLabelInfo);
            if (ecgLabelInfo == null) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("first", 0);
                jSONObject2.put("second", 0);
                jSONObject2.put("third", 0);
                jSONObject2.put("fourth", 0);
                jSONObject2.put("fifth", 0);
                jSONObject2.put("sixth", 0);
                jSONObject.put("body", jSONObject2);
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("first", 0);
                jSONObject3.put("second", 0);
                jSONObject3.put("third", 0);
                jSONObject3.put("fourth", 0);
                jSONObject3.put("fifth", 0);
                jSONObject3.put("sixth", 0);
                jSONObject.put("mood", jSONObject3);
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put("first", 0);
                jSONObject4.put("second", 0);
                jSONObject4.put("third", 0);
                jSONObject4.put("fourth", 0);
                jSONObject4.put("fifth", 0);
                jSONObject4.put("sixth", 0);
                jSONObject.put("habit", jSONObject4);
                remarks = "";
            } else {
                JSONObject jSONObject5 = new JSONObject();
                jSONObject5.put("first", ecgLabelInfo.getBodyFirst());
                jSONObject5.put("second", ecgLabelInfo.getBodySecond());
                jSONObject5.put("third", ecgLabelInfo.getBodyThird());
                jSONObject5.put("fourth", ecgLabelInfo.getBodyFourth());
                jSONObject5.put("fifth", ecgLabelInfo.getBodyFifth());
                jSONObject5.put("sixth", ecgLabelInfo.getBodySixth());
                jSONObject.put("body", jSONObject5);
                JSONObject jSONObject6 = new JSONObject();
                jSONObject6.put("first", ecgLabelInfo.getMoodFirst());
                jSONObject6.put("second", ecgLabelInfo.getMoodSecond());
                jSONObject6.put("third", ecgLabelInfo.getMoodThird());
                jSONObject6.put("fourth", ecgLabelInfo.getMoodFourth());
                jSONObject6.put("fifth", ecgLabelInfo.getMoodFifth());
                jSONObject6.put("sixth", ecgLabelInfo.getMoodSixth());
                jSONObject.put("mood", jSONObject6);
                JSONObject jSONObject7 = new JSONObject();
                jSONObject7.put("first", ecgLabelInfo.getHabitFirst());
                jSONObject7.put("second", ecgLabelInfo.getHabitSecond());
                jSONObject7.put("third", ecgLabelInfo.getHabitThird());
                jSONObject7.put("fourth", ecgLabelInfo.getHabitFourth());
                jSONObject7.put("fifth", ecgLabelInfo.getHabitFifth());
                jSONObject7.put("sixth", ecgLabelInfo.getHabitSixth());
                jSONObject.put("habit", jSONObject7);
                remarks = ecgLabelInfo.getRemarks();
            }
            jSONObject.put("remarks", remarks);
            LogUtils.i("jsonObject  jsonObject=" + jSONObject.toString());
            return jSONObject.toString();
        } catch (JSONException unused) {
            return "";
        }
    }

    public ArrayList<Double> dealEcgSamplingData(String str) {
        new ArrayList();
        return this.isSupCsEcg ? getInstance().dealEcgSamplingDataCs(str) : getInstance().dealEcgSamplingDataUte(str);
    }

    public ArrayList<Double> dealEcgSamplingDataCs(String str) {
        ArrayList<Double> arrayList = new ArrayList<>();
        int length = str.length() / 2;
        int i2 = 0;
        while (i2 < length) {
            int i3 = i2 * 2;
            i2++;
            arrayList.add(Double.valueOf(GBUtils.getInstance().hexStringToAlgorism(str.substring(i3, i2 * 2))));
        }
        return arrayList;
    }

    public ArrayList<Double> dealEcgSamplingDataUte(String str) throws NumberFormatException {
        getInstance().resetECGAlgoAnalysis();
        ArrayList<Double> arrayList = new ArrayList<>();
        int length = str.length() / 2;
        int i2 = 0;
        byte b2 = 0;
        while (i2 < length) {
            int i3 = i2 * 2;
            i2++;
            String strSubstring = str.substring(i3, i2 * 2);
            try {
                b2 = Byte.parseByte(strSubstring, 16);
            } catch (Exception e2) {
                LogUtils.e("parse error=" + e2.toString() + ",data=" + strSubstring);
            }
            arrayList.add(Double.valueOf(this.mEcgAlgoAnalysis.adpcm_decoder16to4(b2, 1)));
        }
        this.ecgDataArray.addAll(arrayList);
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x007b A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int ecgTypePaddingStatus(int r5, com.yc.utesdk.bean.EcgInfo r6) {
        /*
            r4 = this;
            float[] r0 = r4.calculatePaddingInterval(r5, r6)
            r1 = 3
            r2 = 1
            r3 = 2
            if (r5 == 0) goto L60
            if (r5 == r2) goto L46
            if (r5 == r3) goto L2c
            if (r5 == r1) goto L12
        Lf:
            r1 = r3
            goto L7c
        L12:
            int r5 = r6.getEcgFatigueIndex()
            float r5 = (float) r5
            r6 = r0[r2]
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 >= 0) goto L1e
            goto L6b
        L1e:
            r6 = r0[r3]
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 > 0) goto L25
            goto L73
        L25:
            r6 = r0[r1]
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 > 0) goto L7b
            goto L7c
        L2c:
            int r5 = r6.getEcgRiskLevel()
            float r5 = (float) r5
            r6 = r0[r2]
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 >= 0) goto L38
            goto L6b
        L38:
            r6 = r0[r3]
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 > 0) goto L3f
            goto L73
        L3f:
            r6 = r0[r1]
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 > 0) goto L7b
            goto L7c
        L46:
            int r5 = r6.getEcgStrength()
            float r5 = (float) r5
            r6 = r0[r2]
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 >= 0) goto L52
            goto L6b
        L52:
            r6 = r0[r3]
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 > 0) goto L59
            goto L73
        L59:
            r6 = r0[r1]
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 > 0) goto L7b
            goto L7c
        L60:
            int r5 = r6.getEcgHRV()
            float r5 = (float) r5
            r6 = r0[r2]
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 >= 0) goto L6d
        L6b:
            r1 = r2
            goto L7c
        L6d:
            r6 = r0[r3]
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 > 0) goto L74
        L73:
            goto Lf
        L74:
            r6 = r0[r1]
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 > 0) goto L7b
            goto L7c
        L7b:
            r1 = 4
        L7c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.utils.close.EcgUtil.ecgTypePaddingStatus(int, com.yc.utesdk.bean.EcgInfo):int");
    }

    public String getCalendarTime(byte[] bArr) {
        String strUtenfor = utenfor(bArr);
        int iUtenbreak = utenbreak(bArr);
        int i2 = iUtenbreak / 60;
        int i3 = iUtenbreak % 60;
        String strValueOf = String.valueOf(i2);
        String strValueOf2 = String.valueOf(i3);
        if (i2 < 10) {
            strValueOf = "0" + i2;
        }
        if (i3 < 10) {
            strValueOf2 = "0" + i3;
        }
        return strUtenfor + strValueOf + strValueOf2;
    }

    public String getCalendarTime2(byte[] bArr) {
        String strUtenint = utenint(bArr);
        int iUtencatch = utencatch(bArr);
        int i2 = iUtencatch / 60;
        int i3 = iUtencatch % 60;
        String strValueOf = String.valueOf(i2);
        String strValueOf2 = String.valueOf(i3);
        if (i2 < 10) {
            strValueOf = "0" + i2;
        }
        if (i3 < 10) {
            strValueOf2 = "0" + i3;
        }
        return strUtenint + strValueOf + strValueOf2;
    }

    public String getStringResources(Context context, int i2) {
        return context.getResources().getString(i2);
    }

    public EcgAlgoAnalysis getmEcgRealTimeHrAnalysis() {
        return this.mEcgAlgoAnalysis;
    }

    public String jsonToString(String str) {
        EcgLabelInfo ecgLabelInfoAnalysisLabelJson;
        LogUtils.i("json=" + str);
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(str) && (ecgLabelInfoAnalysisLabelJson = analysisLabelJson(str)) != null) {
            if (utendo(ecgLabelInfoAnalysisLabelJson.getBodyFirst())) {
                sb.append(((int) Math.pow(2.0d, 0.0d)) + ",");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getBodySecond())) {
                sb.append(((int) Math.pow(2.0d, 1.0d)) + ",");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getBodyThird())) {
                sb.append(((int) Math.pow(2.0d, 2.0d)) + ",");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getBodyFourth())) {
                sb.append(((int) Math.pow(2.0d, 3.0d)) + ",");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getBodyFifth())) {
                sb.append(((int) Math.pow(2.0d, 4.0d)) + ",");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getBodySixth())) {
                sb.append(((int) Math.pow(2.0d, 5.0d)) + ",");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getMoodFirst())) {
                sb.append(((int) Math.pow(2.0d, 6.0d)) + ",");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getMoodSecond())) {
                sb.append(((int) Math.pow(2.0d, 7.0d)) + ",");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getMoodThird())) {
                sb.append(((int) Math.pow(2.0d, 8.0d)) + ",");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getMoodFourth())) {
                sb.append(((int) Math.pow(2.0d, 9.0d)) + ",");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getMoodFifth())) {
                sb.append(((int) Math.pow(2.0d, 10.0d)) + ",");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getMoodSixth())) {
                sb.append(((int) Math.pow(2.0d, 11.0d)) + ",");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getHabitFirst())) {
                sb.append(((int) Math.pow(2.0d, 12.0d)) + ",");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getHabitSecond())) {
                sb.append(((int) Math.pow(2.0d, 13.0d)) + ",");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getHabitThird())) {
                sb.append(((int) Math.pow(2.0d, 14.0d)) + ",");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getHabitFourth())) {
                sb.append(((int) Math.pow(2.0d, 15.0d)) + ",");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getHabitFifth())) {
                sb.append(((int) Math.pow(2.0d, 16.0d)) + ",");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getHabitSixth())) {
                sb.append(((int) Math.pow(2.0d, 17.0d)) + ",");
            }
        }
        LogUtils.i("updateLabelUi labelString =" + sb.toString());
        return sb.toString();
    }

    public ArrayList<Integer> parseHexToArray(String str) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        int length = str.length() / 4;
        int i2 = 0;
        while (i2 < length) {
            int i3 = i2 * 2;
            i2++;
            arrayList.add(Integer.valueOf(Integer.parseInt(str.substring(i3, i2 * 2), 16)));
        }
        return arrayList;
    }

    public void resetECGAlgoAnalysis() {
        this.mEcgAlgoAnalysis.resetVector();
        this.ecgDataArray = new ArrayList<>();
    }

    public String selectedStrToJson(String str) throws JSONException {
        LogUtils.i("selected=" + str + ",kong =" + TextUtils.isEmpty(str));
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        List listAsList = Arrays.asList(str.split(","));
        LogUtils.i("selectedIndex=" + new Gson().toJson(listAsList) + ",size=" + listAsList.size());
        if (listAsList.size() <= 0 || listAsList.size() > 18) {
            return "";
        }
        int[] iArr = new int[18];
        for (int i2 = 0; i2 < listAsList.size(); i2++) {
            iArr[utendo(2.0d, Integer.valueOf((String) listAsList.get(i2)).intValue())] = 1;
        }
        EcgLabelInfo ecgLabelInfo = new EcgLabelInfo();
        for (int i3 = 0; i3 < 18; i3++) {
            if (i3 == 0) {
                ecgLabelInfo.setBodyFirst(iArr[i3]);
            }
            if (i3 == 1) {
                ecgLabelInfo.setBodySecond(iArr[i3]);
            }
            if (i3 == 2) {
                ecgLabelInfo.setBodyThird(iArr[i3]);
            }
            if (i3 == 3) {
                ecgLabelInfo.setBodyFourth(iArr[i3]);
            }
            if (i3 == 4) {
                ecgLabelInfo.setBodyFifth(iArr[i3]);
            }
            if (i3 == 5) {
                ecgLabelInfo.setBodySixth(iArr[i3]);
            }
            if (i3 == 6) {
                ecgLabelInfo.setMoodFirst(iArr[i3]);
            }
            if (i3 == 7) {
                ecgLabelInfo.setMoodSecond(iArr[i3]);
            }
            if (i3 == 8) {
                ecgLabelInfo.setMoodThird(iArr[i3]);
            }
            if (i3 == 9) {
                ecgLabelInfo.setMoodFourth(iArr[i3]);
            }
            if (i3 == 10) {
                ecgLabelInfo.setMoodFifth(iArr[i3]);
            }
            if (i3 == 11) {
                ecgLabelInfo.setMoodSixth(iArr[i3]);
            }
            if (i3 == 12) {
                ecgLabelInfo.setHabitFirst(iArr[i3]);
            }
            if (i3 == 13) {
                ecgLabelInfo.setHabitSecond(iArr[i3]);
            }
            if (i3 == 14) {
                ecgLabelInfo.setHabitThird(iArr[i3]);
            }
            if (i3 == 15) {
                ecgLabelInfo.setHabitFourth(iArr[i3]);
            }
            if (i3 == 16) {
                ecgLabelInfo.setHabitFifth(iArr[i3]);
            }
            if (i3 == 17) {
                ecgLabelInfo.setHabitSixth(iArr[i3]);
            }
        }
        String strCreatLabelJson = creatLabelJson(ecgLabelInfo);
        LogUtils.i("selectedToJson=" + strCreatLabelJson);
        return strCreatLabelJson;
    }

    public ArrayList<Double> stringToArrayList(String str) {
        ArrayList<Double> arrayList = new ArrayList<>();
        if (!TextUtils.isEmpty(str) && str.length() >= 3) {
            while (str.indexOf("[") != -1) {
                for (String str2 : str.substring(str.indexOf("[") + 1, str.indexOf("]")).split(",")) {
                    arrayList.add(Double.valueOf(Double.parseDouble(str2.trim())));
                }
                str = str.substring(str.indexOf("]") + 1);
            }
        }
        return arrayList;
    }

    public ArrayList<Double> stringToArrayList2(String str) {
        ArrayList<Double> arrayList = new ArrayList<>();
        if (!TextUtils.isEmpty(str) && str.length() >= 3) {
            while (str.indexOf("[") != -1) {
                String[] strArrSplit = str.substring(str.indexOf("[") + 1, str.indexOf("]")).split(",");
                for (int i2 = 0; i2 < strArrSplit.length; i2++) {
                    if (i2 >= 500) {
                        arrayList.add(Double.valueOf(Double.parseDouble(strArrSplit[i2].trim()) * (-1.0d)));
                    }
                }
                str = str.substring(str.indexOf("]") + 1);
            }
        }
        return arrayList;
    }

    public byte[] time2Byte(String str) {
        byte[] bArr = new byte[6];
        if (!TextUtils.isEmpty(str) && str.length() == 12) {
            int iIntValue = Integer.valueOf(str.substring(0, 4)).intValue();
            int iIntValue2 = Integer.valueOf(str.substring(4, 6)).intValue();
            int iIntValue3 = Integer.valueOf(str.substring(6, 8)).intValue();
            int iIntValue4 = Integer.valueOf(str.substring(8, 10)).intValue();
            int iIntValue5 = Integer.valueOf(str.substring(10, 12)).intValue();
            bArr[1] = (byte) (iIntValue & 255);
            bArr[0] = (byte) ((65280 & iIntValue) >> 8);
            bArr[2] = (byte) (iIntValue2 & 255);
            bArr[3] = (byte) (iIntValue3 & 255);
            bArr[4] = (byte) (iIntValue4 & 255);
            bArr[5] = (byte) (iIntValue5 & 255);
        }
        return bArr;
    }

    public final int utenbreak(byte[] bArr) {
        return ((bArr[8] & 255) * 60) + (bArr[9] & 255);
    }

    public final int utenbyte(byte[] bArr) {
        return bArr[13] & 255;
    }

    public final int utencase(byte[] bArr) {
        return bArr[6] & 255;
    }

    public final int utencatch(byte[] bArr) {
        return ((bArr[10] & 255) * 60) + (bArr[11] & 255);
    }

    public final int utenchar(byte[] bArr) {
        return bArr[11] & 255;
    }

    public final int utenelse(byte[] bArr) {
        return bArr[4] & 255;
    }

    public final String utenfor(byte[] bArr) {
        int i2 = bArr[7] & 255;
        int i3 = bArr[6] & 255;
        int i4 = (bArr[5] & 255) | ((bArr[4] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
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

    public final int utengoto(byte[] bArr) {
        return bArr[14] & 255;
    }

    public final int utenif(byte[] bArr) {
        return bArr[3] & 255;
    }

    public final String utenint(byte[] bArr) {
        int i2 = bArr[9] & 255;
        int i3 = bArr[8] & 255;
        int i4 = (bArr[7] & 255) | ((bArr[6] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
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

    public final int utenlong(byte[] bArr) {
        return bArr[7] & 255;
    }

    public final int utennew(byte[] bArr) {
        return bArr[15] & 255;
    }

    public final int utenthis(byte[] bArr) {
        return bArr[12] & 255;
    }

    public final int utentry(byte[] bArr) {
        return bArr[8] & 255;
    }

    public final int utenvoid(byte[] bArr) {
        return bArr[5] & 255;
    }

    public String jsonToString(String str, String[] strArr) {
        EcgLabelInfo ecgLabelInfoAnalysisLabelJson;
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(str) && (ecgLabelInfoAnalysisLabelJson = analysisLabelJson(str)) != null) {
            if (utendo(ecgLabelInfoAnalysisLabelJson.getBodyFirst())) {
                sb.append(strArr[0] + "/");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getBodySecond())) {
                sb.append(strArr[1] + "/");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getBodyThird())) {
                sb.append(strArr[2] + "/");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getBodyFourth())) {
                sb.append(strArr[3] + "/");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getBodyFifth())) {
                sb.append(strArr[4] + "/");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getBodySixth())) {
                sb.append(strArr[5] + "/");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getMoodFirst())) {
                sb.append(strArr[6] + "/");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getMoodSecond())) {
                sb.append(strArr[7] + "/");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getMoodThird())) {
                sb.append(strArr[8] + "/");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getMoodFourth())) {
                sb.append(strArr[9] + "/");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getMoodFifth())) {
                sb.append(strArr[10] + "/");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getMoodSixth())) {
                sb.append(strArr[11] + "/");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getHabitFirst())) {
                sb.append(strArr[12] + "/");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getHabitSecond())) {
                sb.append(strArr[13] + "/");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getHabitThird())) {
                sb.append(strArr[14] + "/");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getHabitFourth())) {
                sb.append(strArr[15] + "/");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getHabitFifth())) {
                sb.append(strArr[16] + "/");
            }
            if (utendo(ecgLabelInfoAnalysisLabelJson.getHabitSixth())) {
                sb.append(strArr[17] + "/");
            }
            if (!TextUtils.isEmpty(ecgLabelInfoAnalysisLabelJson.getRemarks())) {
                sb.append(ecgLabelInfoAnalysisLabelJson.getRemarks());
            }
        }
        LogUtils.i("updateLabelUi labelString =" + sb.toString());
        return sb.toString();
    }

    public final boolean utendo(int i2) {
        return i2 == 1;
    }

    public final int utendo(byte[] bArr) {
        return bArr[10] & 255;
    }
}
