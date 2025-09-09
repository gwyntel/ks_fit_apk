package androidx.constraintlayout.core.motion.key;

import androidx.constraintlayout.core.motion.MotionWidget;
import androidx.constraintlayout.core.motion.utils.FloatRect;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.motion.utils.f;
import java.util.HashMap;
import java.util.HashSet;

/* loaded from: classes.dex */
public class MotionKeyPosition extends MotionKey {
    public static final int TYPE_CARTESIAN = 0;
    public static final int TYPE_PATH = 1;
    public static final int TYPE_SCREEN = 2;
    public float mAltPercentX;
    public float mAltPercentY;
    private float mCalculatedPositionX;
    private float mCalculatedPositionY;
    public int mCurveFit;
    public int mDrawPath;
    public int mPathMotionArc;
    public float mPercentHeight;
    public float mPercentWidth;
    public float mPercentX;
    public float mPercentY;
    public int mPositionType;
    public String mTransitionEasing;

    public MotionKeyPosition() {
        int i2 = MotionKey.UNSET;
        this.mCurveFit = i2;
        this.mTransitionEasing = null;
        this.mPathMotionArc = i2;
        this.mDrawPath = 0;
        this.mPercentWidth = Float.NaN;
        this.mPercentHeight = Float.NaN;
        this.mPercentX = Float.NaN;
        this.mPercentY = Float.NaN;
        this.mAltPercentX = Float.NaN;
        this.mAltPercentY = Float.NaN;
        this.mPositionType = 0;
        this.mCalculatedPositionX = Float.NaN;
        this.mCalculatedPositionY = Float.NaN;
        this.mType = 2;
    }

    private void calcCartesianPosition(float f2, float f3, float f4, float f5) {
        float f6 = f4 - f2;
        float f7 = f5 - f3;
        float f8 = Float.isNaN(this.mPercentX) ? 0.0f : this.mPercentX;
        float f9 = Float.isNaN(this.mAltPercentY) ? 0.0f : this.mAltPercentY;
        float f10 = Float.isNaN(this.mPercentY) ? 0.0f : this.mPercentY;
        this.mCalculatedPositionX = (int) (f2 + (f8 * f6) + ((Float.isNaN(this.mAltPercentX) ? 0.0f : this.mAltPercentX) * f7));
        this.mCalculatedPositionY = (int) (f3 + (f6 * f9) + (f7 * f10));
    }

    private void calcPathPosition(float f2, float f3, float f4, float f5) {
        float f6 = f4 - f2;
        float f7 = f5 - f3;
        float f8 = this.mPercentX;
        float f9 = this.mPercentY;
        this.mCalculatedPositionX = f2 + (f6 * f8) + ((-f7) * f9);
        this.mCalculatedPositionY = f3 + (f7 * f8) + (f6 * f9);
    }

    private void calcScreenPosition(int i2, int i3) {
        float f2 = this.mPercentX;
        float f3 = 0;
        this.mCalculatedPositionX = (i2 * f2) + f3;
        this.mCalculatedPositionY = (i3 * f2) + f3;
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    public void addValues(HashMap<String, SplineSet> map) {
    }

    void c(int i2, int i3, float f2, float f3, float f4, float f5) {
        int i4 = this.mPositionType;
        if (i4 == 1) {
            calcPathPosition(f2, f3, f4, f5);
        } else if (i4 != 2) {
            calcCartesianPosition(f2, f3, f4, f5);
        } else {
            calcScreenPosition(i2, i3);
        }
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    public MotionKey copy(MotionKey motionKey) {
        super.copy(motionKey);
        MotionKeyPosition motionKeyPosition = (MotionKeyPosition) motionKey;
        this.mTransitionEasing = motionKeyPosition.mTransitionEasing;
        this.mPathMotionArc = motionKeyPosition.mPathMotionArc;
        this.mDrawPath = motionKeyPosition.mDrawPath;
        this.mPercentWidth = motionKeyPosition.mPercentWidth;
        this.mPercentHeight = Float.NaN;
        this.mPercentX = motionKeyPosition.mPercentX;
        this.mPercentY = motionKeyPosition.mPercentY;
        this.mAltPercentX = motionKeyPosition.mAltPercentX;
        this.mAltPercentY = motionKeyPosition.mAltPercentY;
        this.mCalculatedPositionX = motionKeyPosition.mCalculatedPositionX;
        this.mCalculatedPositionY = motionKeyPosition.mCalculatedPositionY;
        return this;
    }

    void d(FloatRect floatRect, FloatRect floatRect2, float f2, float f3, String[] strArr, float[] fArr) {
        float fCenterX = floatRect.centerX();
        float fCenterY = floatRect.centerY();
        float fCenterX2 = floatRect2.centerX() - fCenterX;
        float fCenterY2 = floatRect2.centerY() - fCenterY;
        String str = strArr[0];
        if (str == null) {
            strArr[0] = "percentX";
            fArr[0] = (f2 - fCenterX) / fCenterX2;
            strArr[1] = "percentY";
            fArr[1] = (f3 - fCenterY) / fCenterY2;
            return;
        }
        if ("percentX".equals(str)) {
            fArr[0] = (f2 - fCenterX) / fCenterX2;
            fArr[1] = (f3 - fCenterY) / fCenterY2;
        } else {
            fArr[1] = (f2 - fCenterX) / fCenterX2;
            fArr[0] = (f3 - fCenterY) / fCenterY2;
        }
    }

    void e(FloatRect floatRect, FloatRect floatRect2, float f2, float f3, String[] strArr, float[] fArr) {
        float fCenterX = floatRect.centerX();
        float fCenterY = floatRect.centerY();
        float fCenterX2 = floatRect2.centerX() - fCenterX;
        float fCenterY2 = floatRect2.centerY() - fCenterY;
        float fHypot = (float) Math.hypot(fCenterX2, fCenterY2);
        if (fHypot < 1.0E-4d) {
            System.out.println("distance ~ 0");
            fArr[0] = 0.0f;
            fArr[1] = 0.0f;
            return;
        }
        float f4 = fCenterX2 / fHypot;
        float f5 = fCenterY2 / fHypot;
        float f6 = f3 - fCenterY;
        float f7 = f2 - fCenterX;
        float f8 = ((f4 * f6) - (f7 * f5)) / fHypot;
        float f9 = ((f4 * f7) + (f5 * f6)) / fHypot;
        String str = strArr[0];
        if (str != null) {
            if ("percentX".equals(str)) {
                fArr[0] = f9;
                fArr[1] = f8;
                return;
            }
            return;
        }
        strArr[0] = "percentX";
        strArr[1] = "percentY";
        fArr[0] = f9;
        fArr[1] = f8;
    }

    void f(MotionWidget motionWidget, FloatRect floatRect, FloatRect floatRect2, float f2, float f3, String[] strArr, float[] fArr) {
        floatRect.centerX();
        floatRect.centerY();
        floatRect2.centerX();
        floatRect2.centerY();
        MotionWidget parent = motionWidget.getParent();
        int width = parent.getWidth();
        int height = parent.getHeight();
        String str = strArr[0];
        if (str == null) {
            strArr[0] = "percentX";
            fArr[0] = f2 / width;
            strArr[1] = "percentY";
            fArr[1] = f3 / height;
            return;
        }
        if ("percentX".equals(str)) {
            fArr[0] = f2 / width;
            fArr[1] = f3 / height;
        } else {
            fArr[1] = f2 / width;
            fArr[0] = f3 / height;
        }
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    public void getAttributeNames(HashSet<String> hashSet) {
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public int getId(String str) {
        return f.a(str);
    }

    public boolean intersects(int i2, int i3, FloatRect floatRect, FloatRect floatRect2, float f2, float f3) {
        c(i2, i3, floatRect.centerX(), floatRect.centerY(), floatRect2.centerX(), floatRect2.centerY());
        return Math.abs(f2 - this.mCalculatedPositionX) < 20.0f && Math.abs(f3 - this.mCalculatedPositionY) < 20.0f;
    }

    public void positionAttributes(MotionWidget motionWidget, FloatRect floatRect, FloatRect floatRect2, float f2, float f3, String[] strArr, float[] fArr) {
        int i2 = this.mPositionType;
        if (i2 == 1) {
            e(floatRect, floatRect2, f2, f3, strArr, fArr);
        } else if (i2 != 2) {
            d(floatRect, floatRect2, f2, f3, strArr, fArr);
        } else {
            f(motionWidget, floatRect, floatRect2, f2, f3, strArr, fArr);
        }
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, int i3) {
        if (i2 == 100) {
            this.mFramePosition = i3;
            return true;
        }
        if (i2 == 508) {
            this.mCurveFit = i3;
            return true;
        }
        if (i2 != 510) {
            return super.setValue(i2, i3);
        }
        this.mPositionType = i3;
        return true;
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    /* renamed from: clone */
    public MotionKey mo3clone() {
        return new MotionKeyPosition().copy(this);
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, float f2) {
        switch (i2) {
            case 503:
                this.mPercentWidth = f2;
                return true;
            case 504:
                this.mPercentHeight = f2;
                return true;
            case 505:
                this.mPercentWidth = f2;
                this.mPercentHeight = f2;
                return true;
            case 506:
                this.mPercentX = f2;
                return true;
            case TypedValues.PositionType.TYPE_PERCENT_Y /* 507 */:
                this.mPercentY = f2;
                return true;
            default:
                return super.setValue(i2, f2);
        }
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, String str) {
        if (i2 != 501) {
            return super.setValue(i2, str);
        }
        this.mTransitionEasing = str.toString();
        return true;
    }
}
