package androidx.core.content.res;

import android.support.v4.media.MediaDescriptionCompat;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.Size;
import androidx.core.graphics.ColorUtils;
import kotlin.jvm.internal.DoubleCompanionObject;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class CamColor {
    private static final float CHROMA_SEARCH_ENDPOINT = 0.4f;
    private static final float DE_MAX = 1.0f;
    private static final float DL_MAX = 0.2f;
    private static final float LIGHTNESS_SEARCH_ENDPOINT = 0.01f;
    private final float mAstar;
    private final float mBstar;
    private final float mChroma;
    private final float mHue;
    private final float mJ;
    private final float mJstar;
    private final float mM;
    private final float mQ;
    private final float mS;

    CamColor(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
        this.mHue = f2;
        this.mChroma = f3;
        this.mJ = f4;
        this.mQ = f5;
        this.mM = f6;
        this.mS = f7;
        this.mJstar = f8;
        this.mAstar = f9;
        this.mBstar = f10;
    }

    static CamColor b(int i2) {
        float[] fArr = new float[7];
        float[] fArr2 = new float[3];
        c(i2, ViewingConditions.f3550a, fArr, fArr2);
        return new CamColor(fArr2[0], fArr2[1], fArr[0], fArr[1], fArr[2], fArr[3], fArr[4], fArr[5], fArr[6]);
    }

    static void c(int i2, ViewingConditions viewingConditions, float[] fArr, float[] fArr2) {
        CamUtils.f(i2, fArr2);
        float[][] fArr3 = CamUtils.f3539a;
        float f2 = fArr2[0];
        float[] fArr4 = fArr3[0];
        float f3 = fArr4[0] * f2;
        float f4 = fArr2[1];
        float f5 = f3 + (fArr4[1] * f4);
        float f6 = fArr2[2];
        float f7 = f5 + (fArr4[2] * f6);
        float[] fArr5 = fArr3[1];
        float f8 = (fArr5[0] * f2) + (fArr5[1] * f4) + (fArr5[2] * f6);
        float[] fArr6 = fArr3[2];
        float f9 = (f2 * fArr6[0]) + (f4 * fArr6[1]) + (f6 * fArr6[2]);
        float f10 = viewingConditions.i()[0] * f7;
        float f11 = viewingConditions.i()[1] * f8;
        float f12 = viewingConditions.i()[2] * f9;
        float fPow = (float) Math.pow((viewingConditions.c() * Math.abs(f10)) / 100.0d, 0.42d);
        float fPow2 = (float) Math.pow((viewingConditions.c() * Math.abs(f11)) / 100.0d, 0.42d);
        float fPow3 = (float) Math.pow((viewingConditions.c() * Math.abs(f12)) / 100.0d, 0.42d);
        float fSignum = ((Math.signum(f10) * 400.0f) * fPow) / (fPow + 27.13f);
        float fSignum2 = ((Math.signum(f11) * 400.0f) * fPow2) / (fPow2 + 27.13f);
        float fSignum3 = ((Math.signum(f12) * 400.0f) * fPow3) / (fPow3 + 27.13f);
        double d2 = fSignum3;
        float f13 = ((float) (((fSignum * 11.0d) + (fSignum2 * (-12.0d))) + d2)) / 11.0f;
        float f14 = ((float) ((fSignum + fSignum2) - (d2 * 2.0d))) / 9.0f;
        float f15 = fSignum2 * 20.0f;
        float f16 = (((fSignum * 20.0f) + f15) + (21.0f * fSignum3)) / 20.0f;
        float f17 = (((fSignum * 40.0f) + f15) + fSignum3) / 20.0f;
        float fAtan2 = (((float) Math.atan2(f14, f13)) * 180.0f) / 3.1415927f;
        if (fAtan2 < 0.0f) {
            fAtan2 += 360.0f;
        } else if (fAtan2 >= 360.0f) {
            fAtan2 -= 360.0f;
        }
        float f18 = (3.1415927f * fAtan2) / 180.0f;
        float fPow4 = ((float) Math.pow((f17 * viewingConditions.f()) / viewingConditions.a(), viewingConditions.b() * viewingConditions.j())) * 100.0f;
        float fB = (4.0f / viewingConditions.b()) * ((float) Math.sqrt(fPow4 / 100.0f)) * (viewingConditions.a() + 4.0f) * viewingConditions.d();
        float fSqrt = ((float) Math.sqrt(fPow4 / 100.0d)) * ((float) Math.pow(1.64d - Math.pow(0.29d, viewingConditions.e()), 0.73d)) * ((float) Math.pow((((((((float) (Math.cos((((((double) fAtan2) < 20.14d ? 360.0f + fAtan2 : fAtan2) * 3.141592653589793d) / 180.0d) + 2.0d) + 3.8d)) * 0.25f) * 3846.1538f) * viewingConditions.g()) * viewingConditions.h()) * ((float) Math.sqrt((f13 * f13) + (f14 * f14)))) / (f16 + 0.305f), 0.9d));
        float fD = viewingConditions.d() * fSqrt;
        float fSqrt2 = ((float) Math.sqrt((r7 * viewingConditions.b()) / (viewingConditions.a() + 4.0f))) * 50.0f;
        float f19 = (1.7f * fPow4) / ((0.007f * fPow4) + 1.0f);
        float fLog = ((float) Math.log((0.0228f * fD) + 1.0f)) * 43.85965f;
        double d3 = f18;
        float fCos = ((float) Math.cos(d3)) * fLog;
        float fSin = fLog * ((float) Math.sin(d3));
        fArr2[0] = fAtan2;
        fArr2[1] = fSqrt;
        if (fArr != null) {
            fArr[0] = fPow4;
            fArr[1] = fB;
            fArr[2] = fD;
            fArr[3] = fSqrt2;
            fArr[4] = f19;
            fArr[5] = fCos;
            fArr[6] = fSin;
        }
    }

    @Nullable
    private static CamColor findCamByJ(@FloatRange(from = 0.0d, to = 360.0d) float f2, @FloatRange(from = 0.0d, to = DoubleCompanionObject.POSITIVE_INFINITY, toInclusive = false) float f3, @FloatRange(from = 0.0d, to = 100.0d) float f4) {
        float f5 = 100.0f;
        float f6 = 1000.0f;
        float f7 = 0.0f;
        CamColor camColor = null;
        float f8 = 1000.0f;
        while (Math.abs(f7 - f5) > LIGHTNESS_SEARCH_ENDPOINT) {
            float f9 = ((f5 - f7) / 2.0f) + f7;
            int iL = fromJch(f9, f3, f2).l();
            float fB = CamUtils.b(iL);
            float fAbs = Math.abs(f4 - fB);
            if (fAbs < 0.2f) {
                CamColor camColorB = b(iL);
                float fA = camColorB.a(fromJch(camColorB.h(), camColorB.f(), f2));
                if (fA <= 1.0f) {
                    camColor = camColorB;
                    f6 = fAbs;
                    f8 = fA;
                }
            }
            if (f6 == 0.0f && f8 == 0.0f) {
                break;
            }
            if (fB < f4) {
                f7 = f9;
            } else {
                f5 = f9;
            }
        }
        return camColor;
    }

    @NonNull
    private static CamColor fromJch(@FloatRange(from = 0.0d, to = 100.0d) float f2, @FloatRange(from = 0.0d, to = DoubleCompanionObject.POSITIVE_INFINITY, toInclusive = false) float f3, @FloatRange(from = 0.0d, to = 360.0d) float f4) {
        return fromJchInFrame(f2, f3, f4, ViewingConditions.f3550a);
    }

    @NonNull
    private static CamColor fromJchInFrame(@FloatRange(from = 0.0d, to = 100.0d) float f2, @FloatRange(from = 0.0d, to = DoubleCompanionObject.POSITIVE_INFINITY, toInclusive = false) float f3, @FloatRange(from = 0.0d, to = 360.0d) float f4, ViewingConditions viewingConditions) {
        float fB = (4.0f / viewingConditions.b()) * ((float) Math.sqrt(f2 / 100.0d)) * (viewingConditions.a() + 4.0f) * viewingConditions.d();
        float fD = f3 * viewingConditions.d();
        float fSqrt = ((float) Math.sqrt(((f3 / ((float) Math.sqrt(r4))) * viewingConditions.b()) / (viewingConditions.a() + 4.0f))) * 50.0f;
        float f5 = (1.7f * f2) / ((0.007f * f2) + 1.0f);
        float fLog = ((float) Math.log((fD * 0.0228d) + 1.0d)) * 43.85965f;
        double d2 = (3.1415927f * f4) / 180.0f;
        return new CamColor(f4, f3, f2, fB, fD, fSqrt, f5, fLog * ((float) Math.cos(d2)), fLog * ((float) Math.sin(d2)));
    }

    public static void getM3HCTfromColor(@ColorInt int i2, @NonNull @Size(MediaDescriptionCompat.BT_FOLDER_TYPE_ARTISTS) float[] fArr) {
        c(i2, ViewingConditions.f3550a, null, fArr);
        fArr[2] = CamUtils.b(i2);
    }

    static int j(float f2, float f3, float f4, ViewingConditions viewingConditions) {
        if (f3 < 1.0d || Math.round(f4) <= 0.0d || Math.round(f4) >= 100.0d) {
            return CamUtils.a(f4);
        }
        float fMin = f2 < 0.0f ? 0.0f : Math.min(360.0f, f2);
        CamColor camColor = null;
        boolean z2 = true;
        float f5 = 0.0f;
        float f6 = f3;
        while (Math.abs(f5 - f3) >= CHROMA_SEARCH_ENDPOINT) {
            CamColor camColorFindCamByJ = findCamByJ(fMin, f6, f4);
            if (!z2) {
                if (camColorFindCamByJ == null) {
                    f3 = f6;
                } else {
                    f5 = f6;
                    camColor = camColorFindCamByJ;
                }
                f6 = ((f3 - f5) / 2.0f) + f5;
            } else {
                if (camColorFindCamByJ != null) {
                    return camColorFindCamByJ.k(viewingConditions);
                }
                f6 = ((f3 - f5) / 2.0f) + f5;
                z2 = false;
            }
        }
        return camColor == null ? CamUtils.a(f4) : camColor.k(viewingConditions);
    }

    public static int toColor(@FloatRange(from = 0.0d, to = 360.0d) float f2, @FloatRange(from = 0.0d, to = DoubleCompanionObject.POSITIVE_INFINITY, toInclusive = false) float f3, @FloatRange(from = 0.0d, to = 100.0d) float f4) {
        return j(f2, f3, f4, ViewingConditions.f3550a);
    }

    float a(CamColor camColor) {
        float fI = i() - camColor.i();
        float fD = d() - camColor.d();
        float fE = e() - camColor.e();
        return (float) (Math.pow(Math.sqrt((fI * fI) + (fD * fD) + (fE * fE)), 0.63d) * 1.41d);
    }

    float d() {
        return this.mAstar;
    }

    float e() {
        return this.mBstar;
    }

    float f() {
        return this.mChroma;
    }

    float g() {
        return this.mHue;
    }

    float h() {
        return this.mJ;
    }

    float i() {
        return this.mJstar;
    }

    int k(ViewingConditions viewingConditions) {
        float fPow = (float) Math.pow(((((double) f()) == 0.0d || ((double) h()) == 0.0d) ? 0.0f : f() / ((float) Math.sqrt(h() / 100.0d))) / Math.pow(1.64d - Math.pow(0.29d, viewingConditions.e()), 0.73d), 1.1111111111111112d);
        double dG = (g() * 3.1415927f) / 180.0f;
        float fCos = ((float) (Math.cos(2.0d + dG) + 3.8d)) * 0.25f;
        float fA = viewingConditions.a() * ((float) Math.pow(h() / 100.0d, (1.0d / viewingConditions.b()) / viewingConditions.j()));
        float fG = fCos * 3846.1538f * viewingConditions.g() * viewingConditions.h();
        float f2 = fA / viewingConditions.f();
        float fSin = (float) Math.sin(dG);
        float fCos2 = (float) Math.cos(dG);
        float f3 = (((0.305f + f2) * 23.0f) * fPow) / (((fG * 23.0f) + ((11.0f * fPow) * fCos2)) + ((fPow * 108.0f) * fSin));
        float f4 = fCos2 * f3;
        float f5 = f3 * fSin;
        float f6 = f2 * 460.0f;
        float f7 = (((451.0f * f4) + f6) + (288.0f * f5)) / 1403.0f;
        float f8 = ((f6 - (891.0f * f4)) - (261.0f * f5)) / 1403.0f;
        float fSignum = Math.signum(f7) * (100.0f / viewingConditions.c()) * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(f7) * 27.13d) / (400.0d - Math.abs(f7))), 2.380952380952381d));
        float fSignum2 = Math.signum(f8) * (100.0f / viewingConditions.c()) * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(f8) * 27.13d) / (400.0d - Math.abs(f8))), 2.380952380952381d));
        float fSignum3 = Math.signum(((f6 - (f4 * 220.0f)) - (f5 * 6300.0f)) / 1403.0f) * (100.0f / viewingConditions.c()) * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(r8) * 27.13d) / (400.0d - Math.abs(r8))), 2.380952380952381d));
        float f9 = fSignum / viewingConditions.i()[0];
        float f10 = fSignum2 / viewingConditions.i()[1];
        float f11 = fSignum3 / viewingConditions.i()[2];
        float[][] fArr = CamUtils.f3540b;
        float[] fArr2 = fArr[0];
        float f12 = (fArr2[0] * f9) + (fArr2[1] * f10) + (fArr2[2] * f11);
        float[] fArr3 = fArr[1];
        float f13 = (fArr3[0] * f9) + (fArr3[1] * f10) + (fArr3[2] * f11);
        float[] fArr4 = fArr[2];
        return ColorUtils.XYZToColor(f12, f13, (f9 * fArr4[0]) + (f10 * fArr4[1]) + (f11 * fArr4[2]));
    }

    int l() {
        return k(ViewingConditions.f3550a);
    }
}
