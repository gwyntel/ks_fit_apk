package com.yc.utesdk.data;

import android.content.Context;
import androidx.core.view.MotionEventCompat;
import com.alibaba.sdk.android.openaccount.config.LanguageCode;
import com.yc.utesdk.bean.BodyInfo;
import com.yc.utesdk.bean.BodySyncInfo;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.close.BigDecimalUtils;
import com.yc.utesdk.utils.open.RoundingUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class BodyUtil {
    public static final int BODY_COMPOSITION_TYPE_0 = 0;
    public static final int BODY_COMPOSITION_TYPE_1 = 1;
    public static final int BODY_COMPOSITION_TYPE_2 = 2;
    public static final int BODY_COMPOSITION_TYPE_3 = 3;
    public static final int BODY_COMPOSITION_TYPE_4 = 4;
    public static final int BODY_COMPOSITION_TYPE_5 = 5;
    public static final int BODY_COMPOSITION_TYPE_6 = 6;
    public static final int BODY_COMPOSITION_TYPE_7 = 7;
    public static final int BODY_TEST_DISCONNECT = 0;
    public static final int BODY_TEST_SUCCESS = 2;
    public static final int BODY_TEST_TIMEOUT = 1;
    public static final int CHINA = 0;
    public static int COUNTRY = 0;
    public static final int FOREIGN = 1;
    public static final int PADDING_1 = 1;
    public static final int PADDING_2 = 2;
    public static final int PADDING_3 = 3;
    public static final int PADDING_4 = 4;
    public static final int PADDING_5 = 5;
    public static final int ROUNDING_0 = 0;
    public static final int ROUNDING_1 = 1;
    private static BodyUtil instance = null;
    public static boolean isBodyInfoChange = false;

    public BodyUtil() {
        countryStatus();
    }

    public static BodyUtil getInstance() {
        if (instance == null) {
            instance = new BodyUtil();
        }
        return instance;
    }

    public List<BodyInfo> AnalysisBleBodyHistoryData(List<BodySyncInfo> list) {
        String str;
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            byte[] firstSectionData = list.get(i2).getFirstSectionData();
            byte[] secondSectionData = list.get(i2).getSecondSectionData();
            if ((firstSectionData[1] & 255) == 250) {
                String strUtenfor = utenfor(firstSectionData);
                String strUtenthis = utenthis(firstSectionData);
                int iUtenelse = utenelse(firstSectionData);
                String strUtenint = utenint(firstSectionData);
                float fUtengoto = utengoto(firstSectionData);
                float fUtenchar = utenchar(firstSectionData);
                float fUtenif = utenif(firstSectionData);
                float fUtennew = utennew(firstSectionData);
                int iUtendo = utendo(firstSectionData, secondSectionData);
                float fUtencase = utencase(firstSectionData);
                int iUtenbyte = utenbyte(secondSectionData);
                float fUtenlong = utenlong(secondSectionData);
                int iUtendo2 = utendo(secondSectionData);
                int iUtentry = utentry(secondSectionData);
                if (fUtenlong != 0.0f) {
                    BigDecimalUtils bigDecimalUtils = BigDecimalUtils.getInstance();
                    float fDivide = bigDecimalUtils.divide(fUtennew, fUtenlong);
                    float fDivide2 = bigDecimalUtils.divide(fUtengoto, fUtenlong);
                    float fDivide3 = bigDecimalUtils.divide(fUtenchar, fUtenlong);
                    if (fDivide2 == 0.0f && fDivide3 == 0.0f && fUtenif == 0.0f && fDivide == 0.0f && iUtendo == 0 && fUtencase == 0.0f) {
                        str = "AnalysisBleBodyHistoryData ,五项全部为0，不保存数据";
                    } else if (fDivide >= 100.0f || fDivide2 >= 100.0f || fDivide3 >= 100.0f || fUtenif >= fUtenlong || fUtencase >= fUtenlong) {
                        str = "AnalysisBleBodyHistoryData ,脂肪水分蛋白质任何一个，百分比大于等于1，不保存数据;骨盐量，肌肉量大于等于体重，不保存数据";
                    } else {
                        float fCalculateBodyBMI = calculateBodyBMI(fUtenlong, iUtenbyte);
                        arrayList.add(new BodyInfo(strUtenfor, strUtenint, strUtenthis, iUtenelse, iUtentry, iUtendo2, iUtenbyte, fUtenlong, fDivide, fDivide2, fDivide3, iUtendo, fUtenif, fUtencase, fCalculateBodyBMI, calculateBodyScore(fDivide, fDivide2, fDivide3, iUtendo, fUtenif, fUtencase, fCalculateBodyBMI, fUtenlong, iUtenbyte, iUtendo2, iUtentry)));
                    }
                    LogUtils.i(str);
                }
            }
        }
        return arrayList;
    }

    public void AnalysisBleBodyTestData(byte[] bArr, byte[] bArr2) {
        String str;
        int i2 = bArr[1] & 255;
        BodyInfo bodyInfo = new BodyInfo();
        boolean z2 = false;
        if (i2 == 0) {
            String strUtenfor = utenfor(bArr);
            String strUtenthis = utenthis(bArr);
            int iUtenelse = utenelse(bArr);
            String strUtenint = utenint(bArr);
            float fUtengoto = utengoto(bArr);
            float fUtenchar = utenchar(bArr);
            float fUtenif = utenif(bArr);
            float fUtennew = utennew(bArr);
            int iUtendo = utendo(bArr, bArr2);
            float fUtencase = utencase(bArr);
            int iUtenbyte = utenbyte(bArr2);
            float fUtenlong = utenlong(bArr2);
            int iUtendo2 = utendo(bArr2);
            int iUtentry = utentry(bArr2);
            if (fUtenlong != 0.0f) {
                BigDecimalUtils bigDecimalUtils = BigDecimalUtils.getInstance();
                float fDivide = bigDecimalUtils.divide(fUtennew, fUtenlong);
                float fDivide2 = bigDecimalUtils.divide(fUtengoto, fUtenlong);
                float fDivide3 = bigDecimalUtils.divide(fUtenchar, fUtenlong);
                if (fDivide2 == 0.0f && fDivide3 == 0.0f && fUtenif == 0.0f && fDivide == 0.0f && iUtendo == 0 && fUtencase == 0.0f) {
                    str = "AnalysisBleBodyTestData ,五项全部为0，不保存数据";
                } else if (fDivide >= 100.0f || fDivide2 >= 100.0f || fDivide3 >= 100.0f || fUtenif >= fUtenlong || fUtencase >= fUtenlong) {
                    str = "AnalysisBleBodyTestData ,脂肪水分蛋白质任何一个，百分比大于等于1，不保存数据;骨盐量，肌肉量大于等于体重，不保存数据";
                } else {
                    float fCalculateBodyBMI = calculateBodyBMI(fUtenlong, iUtenbyte);
                    bodyInfo = new BodyInfo(strUtenfor, strUtenint, strUtenthis, iUtenelse, iUtentry, iUtendo2, iUtenbyte, fUtenlong, fDivide, fDivide2, fDivide3, iUtendo, fUtenif, fUtencase, fCalculateBodyBMI, calculateBodyScore(fDivide, fDivide2, fDivide3, iUtendo, fUtenif, fUtencase, fCalculateBodyBMI, fUtenlong, iUtenbyte, iUtendo2, iUtentry));
                    z2 = true;
                }
                LogUtils.i(str);
            }
        }
        UteListenerManager uteListenerManager = UteListenerManager.getInstance();
        if (z2) {
            uteListenerManager.onBodyFatRealTime(bodyInfo);
        } else {
            uteListenerManager.onBodyFatStatus(true, 3);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00bb A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int bodyTypePaddingStatus(int r5, com.yc.utesdk.bean.BodyInfo r6) {
        /*
            r4 = this;
            float[] r0 = r4.calculatePaddingInterval(r5, r6)
            r1 = 3
            r2 = 1
            r3 = 2
            switch(r5) {
                case 0: goto La0;
                case 1: goto L8e;
                case 2: goto L7c;
                case 3: goto L69;
                case 4: goto L57;
                case 5: goto L45;
                case 6: goto L29;
                case 7: goto Ld;
                default: goto La;
            }
        La:
            r1 = r3
            goto Lbc
        Ld:
            float r5 = r6.getBodyBMI()
            r6 = r0[r2]
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 >= 0) goto L19
            goto Laa
        L19:
            r6 = r0[r3]
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 > 0) goto L21
            goto Lb2
        L21:
            r6 = r0[r1]
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 > 0) goto Lbb
            goto Lbc
        L29:
            float r5 = r6.getBodyWeight()
            r6 = r0[r2]
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 >= 0) goto L35
            goto Laa
        L35:
            r6 = r0[r3]
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 > 0) goto L3d
            goto Lb2
        L3d:
            r6 = r0[r1]
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 > 0) goto Lbb
            goto Lbc
        L45:
            float r5 = r6.getBodyMuscle()
            r6 = r0[r2]
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 >= 0) goto L50
            goto Laa
        L50:
            r6 = r0[r3]
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 > 0) goto Lbc
            goto Lb2
        L57:
            float r5 = r6.getBodyBoneSalt()
            r6 = r0[r2]
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 >= 0) goto L62
            goto Laa
        L62:
            r6 = r0[r3]
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 > 0) goto Lbc
            goto Lb2
        L69:
            int r5 = r6.getBodyBMR()
            float r5 = (float) r5
            r6 = r0[r2]
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 >= 0) goto L75
            goto Laa
        L75:
            r6 = r0[r3]
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 > 0) goto Lbc
            goto Lb2
        L7c:
            float r5 = r6.getBodyProtein()
            r6 = r0[r2]
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 >= 0) goto L87
            goto Laa
        L87:
            r6 = r0[r3]
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 > 0) goto Lbc
            goto Lb2
        L8e:
            float r5 = r6.getBodyWater()
            r6 = r0[r2]
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 >= 0) goto L99
            goto Laa
        L99:
            r6 = r0[r3]
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 > 0) goto Lbc
            goto Lb2
        La0:
            float r5 = r6.getBodyFat()
            r6 = r0[r2]
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 >= 0) goto Lac
        Laa:
            r1 = r2
            goto Lbc
        Lac:
            r6 = r0[r3]
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 > 0) goto Lb4
        Lb2:
            goto La
        Lb4:
            r6 = r0[r1]
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 > 0) goto Lbb
            goto Lbc
        Lbb:
            r1 = 4
        Lbc:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.data.BodyUtil.bodyTypePaddingStatus(int, com.yc.utesdk.bean.BodyInfo):int");
    }

    public float bodyWeightStandard(int i2, int i3) {
        float f2;
        float f3;
        float f4 = i2;
        if (i3 == 1) {
            f2 = f4 - 80.0f;
            f3 = 0.7f;
        } else {
            f2 = f4 - 70.0f;
            f3 = 0.6f;
        }
        float f5 = f2 * f3;
        if (f5 < 0.0f) {
            f5 = 0.0f;
        }
        return RoundingUtils.getInstance().roundingToFloat(1, f5);
    }

    public int calculateBodyAge(float f2, int i2, int i3, float f3) {
        float f4;
        float f5;
        if (f2 <= 0.0f) {
            return i3;
        }
        float f6 = f3 / f2;
        if (i2 == 1) {
            f4 = f6 - 1.4f;
            f5 = 0.03f;
        } else {
            f4 = f6 - 1.7f;
            f5 = 0.04f;
        }
        int iRoundingToInt = i3 + RoundingUtils.getInstance().roundingToInt(f4 / f5);
        if (iRoundingToInt < 18) {
            return 18;
        }
        return iRoundingToInt;
    }

    public float calculateBodyBMI(float f2, int i2) {
        return RoundingUtils.getInstance().roundingToFloat(1, (f2 * 10000.0f) / (i2 * i2));
    }

    public int calculateBodyScore(float f2, float f3, float f4, int i2, float f5, float f6, float f7, float f8, int i3, int i4, int i5) {
        float fUtenint = utenint(f2, i5, i4, i3, f8);
        float fUtenbyte = utenbyte(f3, i5, i4, i3, f8);
        float fUtentry = utentry(f4, i5, i4, i3, f8);
        float fUtenif = utenif(i2, i5, i4, i3, f8);
        float fUtenfor = utenfor(f5, i5, i4, i3, f8);
        int iRoundingToInt = RoundingUtils.getInstance().roundingToInt(fUtenint + fUtenbyte + fUtentry + fUtenif + fUtenfor + utennew(f6, i5, i4, i3, f8) + utendo(f7, i5, i4, i3, f8));
        if (iRoundingToInt > 100) {
            return 100;
        }
        if (iRoundingToInt < 0) {
            return 0;
        }
        return iRoundingToInt;
    }

    public float calculateBodyStandardBMR(int i2, int i3, float f2, int i4) {
        float f3;
        float f4;
        float f5;
        if (i2 == 1) {
            f3 = (f2 * 13.7f) + 66.0f + (i4 * 5.0f);
            f4 = i3;
            f5 = 6.8f;
        } else {
            f3 = (f2 * 9.6f) + 655.0f + (i4 * 1.8f);
            f4 = i3;
            f5 = 4.7f;
        }
        return f3 - (f4 * f5);
    }

    public float[] calculateCompositionArea(BodyInfo bodyInfo) {
        float[] fArr = {5.0f, 3.0f, 3.0f, 4.0f, 5.0f, 4.0f, 5.0f};
        float bodyFat = bodyInfo.getBodyFat();
        float bodyWater = bodyInfo.getBodyWater();
        float bodyProtein = bodyInfo.getBodyProtein();
        int bodyBMR = bodyInfo.getBodyBMR();
        float bodyBoneSalt = bodyInfo.getBodyBoneSalt();
        float bodyMuscle = bodyInfo.getBodyMuscle();
        float bodyBMI = bodyInfo.getBodyBMI();
        float bodyWeight = bodyInfo.getBodyWeight();
        int bodyHeight = bodyInfo.getBodyHeight();
        int bodyAge = bodyInfo.getBodyAge();
        int bodyGender = bodyInfo.getBodyGender();
        float fUtenint = utenint(bodyFat, bodyGender, bodyAge, bodyHeight, bodyWeight);
        float fUtenbyte = utenbyte(bodyWater, bodyGender, bodyAge, bodyHeight, bodyWeight);
        float fUtentry = utentry(bodyProtein, bodyGender, bodyAge, bodyHeight, bodyWeight);
        float fUtenif = utenif(bodyBMR, bodyGender, bodyAge, bodyHeight, bodyWeight);
        float fUtenfor = utenfor(bodyBoneSalt, bodyGender, bodyAge, bodyHeight, bodyWeight);
        float fUtennew = utennew(bodyMuscle, bodyGender, bodyAge, bodyHeight, bodyWeight);
        float fUtendo = utendo(bodyBMI, bodyGender, bodyAge, bodyHeight, bodyWeight);
        for (int i2 = 0; i2 <= 7; i2++) {
            if (i2 == 0) {
                fArr[6] = (fUtenint * 5.0f) / 14.28f;
            } else if (i2 == 1) {
                fArr[i2 - 1] = (fUtenbyte * 5.0f) / 14.28f;
            } else if (i2 == 2) {
                fArr[i2 - 1] = (fUtentry * 5.0f) / 14.28f;
            } else if (i2 == 3) {
                fArr[i2 - 1] = (fUtenif * 5.0f) / 14.28f;
            } else if (i2 == 4) {
                fArr[i2 - 1] = (fUtenfor * 5.0f) / 14.28f;
            } else if (i2 == 5) {
                fArr[i2 - 1] = (fUtennew * 5.0f) / 14.28f;
            } else if (i2 == 7) {
                fArr[5] = (fUtendo * 5.0f) / 14.28f;
            }
        }
        return fArr;
    }

    public float[] calculatePaddingInterval(int i2, BodyInfo bodyInfo) {
        float[] fArr;
        float bodyWeight = bodyInfo.getBodyWeight();
        int bodyHeight = bodyInfo.getBodyHeight();
        int bodyGender = bodyInfo.getBodyGender();
        int bodyAge = bodyInfo.getBodyAge();
        switch (i2) {
            case 0:
                float[] fArr2 = new float[5];
                if (bodyGender == 1) {
                    if (bodyAge <= 39) {
                        fArr2[0] = 5.0f;
                        fArr2[1] = 11.0f;
                        fArr2[2] = 22.0f;
                        fArr2[3] = 27.0f;
                        fArr2[4] = 45.0f;
                        return fArr2;
                    }
                    if (bodyAge <= 59) {
                        fArr2[0] = 5.0f;
                        fArr2[1] = 12.0f;
                        fArr2[2] = 23.0f;
                        fArr2[3] = 28.0f;
                        fArr2[4] = 45.0f;
                        return fArr2;
                    }
                    fArr2[0] = 5.0f;
                    fArr2[1] = 14.0f;
                    fArr2[2] = 25.0f;
                    fArr2[3] = 30.0f;
                    fArr2[4] = 45.0f;
                    return fArr2;
                }
                if (bodyAge <= 39) {
                    fArr2[0] = 5.0f;
                    fArr2[1] = 21.0f;
                    fArr2[2] = 35.0f;
                    fArr2[3] = 40.0f;
                    fArr2[4] = 45.0f;
                    return fArr2;
                }
                if (bodyAge <= 59) {
                    fArr2[0] = 5.0f;
                    fArr2[1] = 22.0f;
                    fArr2[2] = 36.0f;
                    fArr2[3] = 41.0f;
                    fArr2[4] = 45.0f;
                    return fArr2;
                }
                fArr2[0] = 5.0f;
                fArr2[1] = 23.0f;
                fArr2[2] = 37.0f;
                fArr2[3] = 42.0f;
                fArr2[4] = 45.0f;
                return fArr2;
            case 1:
                float[] fArr3 = new float[4];
                if (bodyGender == 1) {
                    if (bodyAge <= 30) {
                        fArr3[0] = 37.8f;
                        fArr3[1] = 53.6f;
                        fArr3[2] = 57.0f;
                        fArr3[3] = 66.0f;
                        return fArr3;
                    }
                    fArr3[0] = 37.8f;
                    fArr3[1] = 52.3f;
                    fArr3[2] = 55.6f;
                    fArr3[3] = 66.0f;
                    return fArr3;
                }
                if (bodyAge <= 30) {
                    fArr3[0] = 37.8f;
                    fArr3[1] = 49.5f;
                    fArr3[2] = 52.9f;
                    fArr3[3] = 66.0f;
                    return fArr3;
                }
                fArr3[0] = 37.8f;
                fArr3[1] = 48.1f;
                fArr3[2] = 51.5f;
                fArr3[3] = 66.0f;
                return fArr3;
            case 2:
                return new float[]{0.0f, 16.0f, 20.0f, 36.0f};
            case 3:
                float fCalculateBodyStandardBMR = calculateBodyStandardBMR(bodyGender, bodyAge, bodyWeight, bodyHeight);
                RoundingUtils roundingUtils = RoundingUtils.getInstance();
                fArr = new float[]{roundingUtils.roundingToFloat(1, 0.75f * fCalculateBodyStandardBMR), roundingUtils.roundingToFloat(1, 0.85f * fCalculateBodyStandardBMR), roundingUtils.roundingToFloat(1, 1.15f * fCalculateBodyStandardBMR), roundingUtils.roundingToFloat(1, fCalculateBodyStandardBMR * 1.3f)};
                break;
            case 4:
                fArr = new float[4];
                if (bodyGender != 1) {
                    if (bodyWeight >= 45.0f) {
                        if (bodyWeight >= 45.0f && bodyWeight <= 60.0f) {
                            fArr[0] = 1.3f;
                            fArr[1] = 1.9f;
                            fArr[2] = 2.5f;
                            fArr[3] = 3.1f;
                            break;
                        } else {
                            fArr[0] = 1.6f;
                            fArr[1] = 2.2f;
                            fArr[2] = 2.8f;
                            fArr[3] = 3.4f;
                            break;
                        }
                    } else {
                        fArr[0] = 0.9f;
                        fArr[1] = 1.5f;
                        fArr[2] = 2.1f;
                        fArr[3] = 2.7f;
                        break;
                    }
                } else if (bodyWeight >= 60.0f) {
                    if (bodyWeight >= 60.0f && bodyWeight <= 75.0f) {
                        fArr[0] = 1.7f;
                        fArr[1] = 2.5f;
                        fArr[2] = 3.3f;
                        fArr[3] = 4.1f;
                        break;
                    } else {
                        fArr[0] = 2.0f;
                        fArr[1] = 2.8f;
                        fArr[2] = 3.6f;
                        fArr[3] = 4.4f;
                        break;
                    }
                } else {
                    fArr[0] = 1.3f;
                    fArr[1] = 2.1f;
                    fArr[2] = 2.9f;
                    fArr[3] = 3.7f;
                    break;
                }
                break;
            case 5:
                float[] fArr4 = new float[4];
                if (bodyGender == 1) {
                    if (bodyHeight < 160) {
                        fArr4[0] = 18.5f;
                        fArr4[1] = 38.5f;
                        fArr4[2] = 46.5f;
                        fArr4[3] = 66.5f;
                        return fArr4;
                    }
                    if (bodyHeight < 160 || bodyHeight > 170) {
                        fArr4[0] = 29.4f;
                        fArr4[1] = 49.4f;
                        fArr4[2] = 59.4f;
                        fArr4[3] = 79.4f;
                        return fArr4;
                    }
                    fArr4[0] = 24.0f;
                    fArr4[1] = 44.0f;
                    fArr4[2] = 52.4f;
                    fArr4[3] = 72.4f;
                    return fArr4;
                }
                if (bodyHeight < 150) {
                    fArr4[0] = 6.9f;
                    fArr4[1] = 21.9f;
                    fArr4[2] = 37.4f;
                    fArr4[3] = 49.7f;
                    return fArr4;
                }
                if (bodyHeight < 150 || bodyHeight > 160) {
                    fArr4[0] = 21.5f;
                    fArr4[1] = 36.5f;
                    fArr4[2] = 42.5f;
                    fArr4[3] = 57.5f;
                    return fArr4;
                }
                fArr4[0] = 17.9f;
                fArr4[1] = 32.9f;
                fArr4[2] = 37.5f;
                fArr4[3] = 52.5f;
                return fArr4;
            case 6:
                float fBodyWeightStandard = bodyWeightStandard(bodyHeight, bodyGender);
                RoundingUtils roundingUtils2 = RoundingUtils.getInstance();
                return new float[]{roundingUtils2.roundingToFloat(1, 0.8f * fBodyWeightStandard), roundingUtils2.roundingToFloat(1, 0.9f * fBodyWeightStandard), roundingUtils2.roundingToFloat(1, 1.1f * fBodyWeightStandard), roundingUtils2.roundingToFloat(1, 1.2f * fBodyWeightStandard), roundingUtils2.roundingToFloat(1, fBodyWeightStandard * 1.3f)};
            case 7:
                float[] fArr5 = new float[5];
                if (countryStatus() == 0) {
                    fArr5[0] = 10.0f;
                    fArr5[1] = 18.5f;
                    fArr5[2] = 24.0f;
                    fArr5[3] = 28.0f;
                    fArr5[4] = 40.0f;
                    return fArr5;
                }
                fArr5[0] = 10.0f;
                fArr5[1] = 18.5f;
                fArr5[2] = 25.0f;
                fArr5[3] = 30.0f;
                fArr5[4] = 45.0f;
                return fArr5;
            default:
                return null;
        }
        return fArr;
    }

    public int countryStatus() {
        String string = Locale.getDefault().toString();
        COUNTRY = (string.contains(LanguageCode.CHINESE) || string.contains("zh_TW") || string.contains("zh_MO") || string.contains("zh_HK")) ? 0 : 1;
        return COUNTRY;
    }

    public String getStringResources(Context context, int i2) {
        return context.getResources().getString(i2);
    }

    public final float utenbyte(float f2, int i2, int i3, int i4, float f3) {
        BodyInfo bodyInfo = new BodyInfo();
        bodyInfo.setBodyGender(i2);
        bodyInfo.setBodyAge(i3);
        bodyInfo.setBodyHeight(i4);
        bodyInfo.setBodyWeight(f3);
        float[] fArrCalculatePaddingInterval = calculatePaddingInterval(1, bodyInfo);
        float f4 = fArrCalculatePaddingInterval[0];
        float f5 = fArrCalculatePaddingInterval[1];
        float f6 = f5 - f4;
        float f7 = ((f6 * 1.0f) / 3.0f) + f4;
        float f8 = ((f6 * 2.0f) / 3.0f) + f4;
        float f9 = fArrCalculatePaddingInterval[2];
        float f10 = f9 - f5;
        float f11 = ((f10 * 1.0f) / 3.0f) + f5;
        float f12 = ((f10 * 2.0f) / 3.0f) + f5;
        float f13 = fArrCalculatePaddingInterval[3];
        float f14 = f13 - f9;
        float f15 = ((1.0f * f14) / 3.0f) + f9;
        float f16 = ((f14 * 2.0f) / 3.0f) + f9;
        if (f2 < f4 || f2 > f13) {
            return 7.14f;
        }
        if (f2 >= f7) {
            if (f2 >= f8) {
                if (f2 >= f5) {
                    if (f2 >= f11) {
                        if (f2 <= f12) {
                            return 14.28f;
                        }
                        if (f2 > f9) {
                            if (f2 > f15) {
                                if (f2 > f16) {
                                    if (f2 > f13) {
                                        return 14.28f;
                                    }
                                }
                            }
                        }
                    }
                    return 12.851999f;
                }
                return 11.424f;
            }
            return 9.996f;
        }
        return 8.568f;
    }

    public final float utencase(byte[] bArr) {
        return BigDecimalUtils.getInstance().divide((bArr[12] & 255) | ((bArr[11] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK), 100.0f);
    }

    public final float utenchar(byte[] bArr) {
        return (bArr[14] & 255) | ((bArr[13] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    }

    public final float utendo(float f2, int i2, int i3, int i4, float f3) {
        BodyInfo bodyInfo = new BodyInfo();
        bodyInfo.setBodyGender(i2);
        bodyInfo.setBodyAge(i3);
        bodyInfo.setBodyHeight(i4);
        bodyInfo.setBodyWeight(f3);
        float[] fArrCalculatePaddingInterval = calculatePaddingInterval(7, bodyInfo);
        float f4 = fArrCalculatePaddingInterval[0];
        float f5 = fArrCalculatePaddingInterval[1];
        float f6 = f5 - f4;
        float f7 = ((f6 * 1.0f) / 3.0f) + f4;
        float f8 = ((f6 * 2.0f) / 3.0f) + f4;
        float f9 = fArrCalculatePaddingInterval[2];
        float f10 = f9 - f5;
        float f11 = ((f10 * 1.0f) / 3.0f) + f5;
        float f12 = ((f10 * 2.0f) / 3.0f) + f5;
        float f13 = fArrCalculatePaddingInterval[3];
        float f14 = f13 - f9;
        float f15 = ((f14 * 1.0f) / 3.0f) + f9;
        float f16 = ((f14 * 2.0f) / 3.0f) + f9;
        float f17 = fArrCalculatePaddingInterval[4];
        float f18 = f17 - f13;
        float f19 = ((1.0f * f18) / 3.0f) + f13;
        float f20 = ((f18 * 2.0f) / 3.0f) + f13;
        if (f2 >= f4) {
            if (f2 >= f7) {
                if (f2 >= f8) {
                    if (f2 >= f5) {
                        if (f2 >= f11) {
                            if (f2 <= f12) {
                                return 14.28f;
                            }
                            if (f2 > f9) {
                                if (f2 > f15) {
                                    if (f2 > f16) {
                                        if (f2 > f13) {
                                            if (f2 > f19) {
                                                if (f2 <= f20) {
                                                    return 5.712f;
                                                }
                                                if (f2 <= f17) {
                                                    return 4.2839994f;
                                                }
                                                return f2 > f17 ? 2.856f : 14.28f;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        return 12.851999f;
                    }
                    return 11.424f;
                }
                return 9.996f;
            }
            return 8.568f;
        }
        return 7.14f;
    }

    public final int utenelse(byte[] bArr) {
        return ((bArr[7] & 255) * 60) + (bArr[8] & 255);
    }

    public final float utenfor(float f2, int i2, int i3, int i4, float f3) {
        BodyInfo bodyInfo = new BodyInfo();
        bodyInfo.setBodyGender(i2);
        bodyInfo.setBodyAge(i3);
        bodyInfo.setBodyHeight(i4);
        bodyInfo.setBodyWeight(f3);
        float[] fArrCalculatePaddingInterval = calculatePaddingInterval(4, bodyInfo);
        float f4 = fArrCalculatePaddingInterval[0];
        float f5 = fArrCalculatePaddingInterval[1];
        float f6 = f5 - f4;
        float f7 = ((f6 * 1.0f) / 3.0f) + f4;
        float f8 = ((f6 * 2.0f) / 3.0f) + f4;
        float f9 = fArrCalculatePaddingInterval[2];
        float f10 = f9 - f5;
        float f11 = ((f10 * 1.0f) / 3.0f) + f5;
        float f12 = ((f10 * 2.0f) / 3.0f) + f5;
        float f13 = fArrCalculatePaddingInterval[3];
        float f14 = f13 - f9;
        float f15 = ((1.0f * f14) / 3.0f) + f9;
        float f16 = ((f14 * 2.0f) / 3.0f) + f9;
        if (f2 < f4 || f2 > f13) {
            return 7.14f;
        }
        if (f2 >= f7) {
            if (f2 >= f8) {
                if (f2 >= f5) {
                    if (f2 >= f11) {
                        if (f2 <= f12) {
                            return 14.28f;
                        }
                        if (f2 > f9) {
                            if (f2 > f15) {
                                if (f2 > f16) {
                                    if (f2 > f13) {
                                        return 14.28f;
                                    }
                                }
                            }
                        }
                    }
                    return 12.851999f;
                }
                return 11.424f;
            }
            return 9.996f;
        }
        return 8.568f;
    }

    public final float utengoto(byte[] bArr) {
        return (bArr[10] & 255) | ((bArr[9] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    }

    public final float utenif(float f2, int i2, int i3, int i4, float f3) {
        BodyInfo bodyInfo = new BodyInfo();
        bodyInfo.setBodyGender(i2);
        bodyInfo.setBodyAge(i3);
        bodyInfo.setBodyHeight(i4);
        bodyInfo.setBodyWeight(f3);
        float[] fArrCalculatePaddingInterval = calculatePaddingInterval(3, bodyInfo);
        float f4 = fArrCalculatePaddingInterval[0];
        float f5 = fArrCalculatePaddingInterval[1];
        float f6 = f5 - f4;
        float f7 = ((f6 * 1.0f) / 3.0f) + f4;
        float f8 = ((f6 * 2.0f) / 3.0f) + f4;
        float f9 = fArrCalculatePaddingInterval[2];
        float f10 = f9 - f5;
        float f11 = ((f10 * 1.0f) / 3.0f) + f5;
        float f12 = ((f10 * 2.0f) / 3.0f) + f5;
        float f13 = fArrCalculatePaddingInterval[3];
        float f14 = f13 - f9;
        float f15 = ((1.0f * f14) / 3.0f) + f9;
        float f16 = ((f14 * 2.0f) / 3.0f) + f9;
        if (f2 < f4 || f2 > f13) {
            return 7.14f;
        }
        if (f2 >= f7) {
            if (f2 >= f8) {
                if (f2 >= f5) {
                    if (f2 >= f11) {
                        if (f2 <= f12) {
                            return 14.28f;
                        }
                        if (f2 > f9) {
                            if (f2 > f15) {
                                if (f2 > f16) {
                                    if (f2 > f13) {
                                        return 14.28f;
                                    }
                                }
                            }
                        }
                    }
                    return 12.851999f;
                }
                return 11.424f;
            }
            return 9.996f;
        }
        return 8.568f;
    }

    public final float utenint(float f2, int i2, int i3, int i4, float f3) {
        BodyInfo bodyInfo = new BodyInfo();
        bodyInfo.setBodyGender(i2);
        bodyInfo.setBodyAge(i3);
        bodyInfo.setBodyHeight(i4);
        bodyInfo.setBodyWeight(f3);
        float[] fArrCalculatePaddingInterval = calculatePaddingInterval(0, bodyInfo);
        float f4 = fArrCalculatePaddingInterval[0];
        float f5 = fArrCalculatePaddingInterval[1];
        float f6 = f5 - f4;
        float f7 = ((f6 * 1.0f) / 3.0f) + f4;
        float f8 = ((f6 * 2.0f) / 3.0f) + f4;
        float f9 = fArrCalculatePaddingInterval[2];
        float f10 = f9 - f5;
        float f11 = ((f10 * 1.0f) / 3.0f) + f5;
        float f12 = ((f10 * 2.0f) / 3.0f) + f5;
        float f13 = fArrCalculatePaddingInterval[3];
        float f14 = f13 - f9;
        float f15 = ((f14 * 1.0f) / 3.0f) + f9;
        float f16 = ((f14 * 2.0f) / 3.0f) + f9;
        float f17 = fArrCalculatePaddingInterval[4];
        float f18 = f17 - f13;
        float f19 = ((1.0f * f18) / 3.0f) + f13;
        float f20 = ((f18 * 2.0f) / 3.0f) + f13;
        if (f2 >= f4) {
            if (f2 >= f7) {
                if (f2 >= f8) {
                    if (f2 >= f5) {
                        if (f2 >= f11) {
                            if (f2 <= f12) {
                                return 14.28f;
                            }
                            if (f2 > f9) {
                                if (f2 > f15) {
                                    if (f2 > f16) {
                                        if (f2 > f13) {
                                            if (f2 > f19) {
                                                if (f2 <= f20) {
                                                    return 5.712f;
                                                }
                                                if (f2 <= f17) {
                                                    return 4.2839994f;
                                                }
                                                return f2 > f17 ? 2.856f : 14.28f;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        return 12.851999f;
                    }
                    return 11.424f;
                }
                return 9.996f;
            }
            return 8.568f;
        }
        return 7.14f;
    }

    public final float utenlong(byte[] bArr) {
        return ((bArr[6] & 255) | ((bArr[5] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) / 100.0f;
    }

    public final float utennew(float f2, int i2, int i3, int i4, float f3) {
        BodyInfo bodyInfo = new BodyInfo();
        bodyInfo.setBodyGender(i2);
        bodyInfo.setBodyAge(i3);
        bodyInfo.setBodyHeight(i4);
        bodyInfo.setBodyWeight(f3);
        float[] fArrCalculatePaddingInterval = calculatePaddingInterval(5, bodyInfo);
        float f4 = fArrCalculatePaddingInterval[0];
        float f5 = fArrCalculatePaddingInterval[1];
        float f6 = f5 - f4;
        float f7 = ((f6 * 1.0f) / 3.0f) + f4;
        float f8 = ((f6 * 2.0f) / 3.0f) + f4;
        float f9 = fArrCalculatePaddingInterval[2];
        float f10 = f9 - f5;
        float f11 = ((f10 * 1.0f) / 3.0f) + f5;
        float f12 = ((f10 * 2.0f) / 3.0f) + f5;
        float f13 = fArrCalculatePaddingInterval[3];
        float f14 = f13 - f9;
        float f15 = ((1.0f * f14) / 3.0f) + f9;
        float f16 = ((f14 * 2.0f) / 3.0f) + f9;
        if (f2 < f4 || f2 > f13) {
            return 7.14f;
        }
        if (f2 >= f7) {
            if (f2 >= f8) {
                if (f2 >= f5) {
                    if (f2 >= f11) {
                        if (f2 <= f12) {
                            return 14.28f;
                        }
                        if (f2 > f9) {
                            if (f2 > f15) {
                                if (f2 > f16) {
                                    if (f2 > f13) {
                                        return 14.28f;
                                    }
                                }
                            }
                        }
                    }
                    return 12.851999f;
                }
                return 11.424f;
            }
            return 9.996f;
        }
        return 8.568f;
    }

    public final String utenthis(byte[] bArr) {
        int i2 = bArr[5] & 255;
        int i3 = (bArr[4] & 255) | ((bArr[3] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
        String strValueOf = String.valueOf(i2);
        if (i2 < 10) {
            strValueOf = "0" + i2;
        }
        return String.valueOf(i3) + strValueOf;
    }

    public final float utentry(float f2, int i2, int i3, int i4, float f3) {
        BodyInfo bodyInfo = new BodyInfo();
        bodyInfo.setBodyGender(i2);
        bodyInfo.setBodyAge(i3);
        bodyInfo.setBodyHeight(i4);
        bodyInfo.setBodyWeight(f3);
        float[] fArrCalculatePaddingInterval = calculatePaddingInterval(2, bodyInfo);
        float f4 = fArrCalculatePaddingInterval[0];
        float f5 = fArrCalculatePaddingInterval[1];
        float f6 = f5 - f4;
        float f7 = ((f6 * 1.0f) / 3.0f) + f4;
        float f8 = ((f6 * 2.0f) / 3.0f) + f4;
        float f9 = fArrCalculatePaddingInterval[2];
        float f10 = f9 - f5;
        float f11 = ((f10 * 1.0f) / 3.0f) + f5;
        float f12 = ((f10 * 2.0f) / 3.0f) + f5;
        float f13 = fArrCalculatePaddingInterval[3];
        float f14 = f13 - f9;
        float f15 = ((1.0f * f14) / 3.0f) + f9;
        float f16 = ((f14 * 2.0f) / 3.0f) + f9;
        if (f2 < f4 || f2 > f13) {
            return 7.14f;
        }
        if (f2 >= f7) {
            if (f2 >= f8) {
                if (f2 >= f5) {
                    if (f2 >= f11) {
                        if (f2 <= f12) {
                            return 14.28f;
                        }
                        if (f2 > f9) {
                            if (f2 > f15) {
                                if (f2 > f16) {
                                    if (f2 > f13) {
                                        return 14.28f;
                                    }
                                }
                            }
                        }
                    }
                    return 12.851999f;
                }
                return 11.424f;
            }
            return 9.996f;
        }
        return 8.568f;
    }

    public final int utenbyte(byte[] bArr) {
        return bArr[4] & 255;
    }

    public final int utendo(byte[] bArr) {
        return bArr[7] & 255;
    }

    public final String utenfor(byte[] bArr) {
        int i2 = bArr[6] & 255;
        int i3 = bArr[5] & 255;
        int i4 = (bArr[4] & 255) | ((bArr[3] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
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

    public final float utenif(byte[] bArr) {
        return BigDecimalUtils.getInstance().divide((bArr[16] & 255) | ((bArr[15] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK), 100.0f);
    }

    public final String utenint(byte[] bArr) {
        String strUtenfor = utenfor(bArr);
        int iUtenelse = utenelse(bArr);
        int i2 = iUtenelse / 60;
        int i3 = iUtenelse % 60;
        String strValueOf = String.valueOf(i2);
        String strValueOf2 = String.valueOf(i3);
        if (i2 < 10) {
            strValueOf = "0" + strValueOf;
        }
        if (i3 < 10) {
            strValueOf2 = "0" + strValueOf2;
        }
        return strUtenfor + strValueOf + strValueOf2;
    }

    public final float utennew(byte[] bArr) {
        return (bArr[18] & 255) | ((bArr[17] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    }

    public final int utentry(byte[] bArr) {
        return (bArr[8] & 255) == 0 ? 0 : 1;
    }

    public final int utendo(byte[] bArr, byte[] bArr2) {
        return ((bArr[19] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr2[3] & 255);
    }
}
