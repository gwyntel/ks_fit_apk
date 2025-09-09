package androidx.transition;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.core.content.res.TypedArrayUtils;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes2.dex */
public class ArcMotion extends PathMotion {
    private static final float DEFAULT_MAX_ANGLE_DEGREES = 70.0f;
    private static final float DEFAULT_MAX_TANGENT = (float) Math.tan(Math.toRadians(35.0d));
    private static final float DEFAULT_MIN_ANGLE_DEGREES = 0.0f;
    private float mMaximumAngle;
    private float mMaximumTangent;
    private float mMinimumHorizontalAngle;
    private float mMinimumHorizontalTangent;
    private float mMinimumVerticalAngle;
    private float mMinimumVerticalTangent;

    public ArcMotion() {
        this.mMinimumHorizontalAngle = 0.0f;
        this.mMinimumVerticalAngle = 0.0f;
        this.mMaximumAngle = DEFAULT_MAX_ANGLE_DEGREES;
        this.mMinimumHorizontalTangent = 0.0f;
        this.mMinimumVerticalTangent = 0.0f;
        this.mMaximumTangent = DEFAULT_MAX_TANGENT;
    }

    private static float toTangent(float f2) {
        if (f2 < 0.0f || f2 > 90.0f) {
            throw new IllegalArgumentException("Arc must be between 0 and 90 degrees");
        }
        return (float) Math.tan(Math.toRadians(f2 / 2.0f));
    }

    public float getMaximumAngle() {
        return this.mMaximumAngle;
    }

    public float getMinimumHorizontalAngle() {
        return this.mMinimumHorizontalAngle;
    }

    public float getMinimumVerticalAngle() {
        return this.mMinimumVerticalAngle;
    }

    @Override // androidx.transition.PathMotion
    @NonNull
    public Path getPath(float f2, float f3, float f4, float f5) {
        float f6;
        float f7;
        float f8;
        Path path = new Path();
        path.moveTo(f2, f3);
        float f9 = f4 - f2;
        float f10 = f5 - f3;
        float f11 = (f9 * f9) + (f10 * f10);
        float f12 = (f2 + f4) / 2.0f;
        float f13 = (f3 + f5) / 2.0f;
        float f14 = 0.25f * f11;
        boolean z2 = f3 > f5;
        if (Math.abs(f9) < Math.abs(f10)) {
            float fAbs = Math.abs(f11 / (f10 * 2.0f));
            if (z2) {
                f7 = fAbs + f5;
                f6 = f4;
            } else {
                f7 = fAbs + f3;
                f6 = f2;
            }
            f8 = this.mMinimumVerticalTangent;
        } else {
            float f15 = f11 / (f9 * 2.0f);
            if (z2) {
                f7 = f3;
                f6 = f15 + f2;
            } else {
                f6 = f4 - f15;
                f7 = f5;
            }
            f8 = this.mMinimumHorizontalTangent;
        }
        float f16 = f14 * f8 * f8;
        float f17 = f12 - f6;
        float f18 = f13 - f7;
        float f19 = (f17 * f17) + (f18 * f18);
        float f20 = this.mMaximumTangent;
        float f21 = f14 * f20 * f20;
        if (f19 >= f16) {
            f16 = f19 > f21 ? f21 : 0.0f;
        }
        if (f16 != 0.0f) {
            float fSqrt = (float) Math.sqrt(f16 / f19);
            f6 = ((f6 - f12) * fSqrt) + f12;
            f7 = f13 + (fSqrt * (f7 - f13));
        }
        path.cubicTo((f2 + f6) / 2.0f, (f3 + f7) / 2.0f, (f6 + f4) / 2.0f, (f7 + f5) / 2.0f, f4, f5);
        return path;
    }

    public void setMaximumAngle(float f2) {
        this.mMaximumAngle = f2;
        this.mMaximumTangent = toTangent(f2);
    }

    public void setMinimumHorizontalAngle(float f2) {
        this.mMinimumHorizontalAngle = f2;
        this.mMinimumHorizontalTangent = toTangent(f2);
    }

    public void setMinimumVerticalAngle(float f2) {
        this.mMinimumVerticalAngle = f2;
        this.mMinimumVerticalTangent = toTangent(f2);
    }

    @SuppressLint({"RestrictedApi"})
    public ArcMotion(@NonNull Context context, @NonNull AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMinimumHorizontalAngle = 0.0f;
        this.mMinimumVerticalAngle = 0.0f;
        this.mMaximumAngle = DEFAULT_MAX_ANGLE_DEGREES;
        this.mMinimumHorizontalTangent = 0.0f;
        this.mMinimumVerticalTangent = 0.0f;
        this.mMaximumTangent = DEFAULT_MAX_TANGENT;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.f6314j);
        XmlPullParser xmlPullParser = (XmlPullParser) attributeSet;
        setMinimumVerticalAngle(TypedArrayUtils.getNamedFloat(typedArrayObtainStyledAttributes, xmlPullParser, "minimumVerticalAngle", 1, 0.0f));
        setMinimumHorizontalAngle(TypedArrayUtils.getNamedFloat(typedArrayObtainStyledAttributes, xmlPullParser, "minimumHorizontalAngle", 0, 0.0f));
        setMaximumAngle(TypedArrayUtils.getNamedFloat(typedArrayObtainStyledAttributes, xmlPullParser, "maximumAngle", 2, DEFAULT_MAX_ANGLE_DEGREES));
        typedArrayObtainStyledAttributes.recycle();
    }
}
