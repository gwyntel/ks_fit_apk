package androidx.constraintlayout.core.motion;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.view.ViewCompat;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes.dex */
public class CustomVariable {
    private static final String TAG = "TransitionLayout";

    /* renamed from: a, reason: collision with root package name */
    String f2593a;

    /* renamed from: b, reason: collision with root package name */
    boolean f2594b;
    private float mFloatValue;
    private int mIntegerValue;
    private String mStringValue;
    private int mType;

    public CustomVariable(CustomVariable customVariable) {
        this.mIntegerValue = Integer.MIN_VALUE;
        this.mFloatValue = Float.NaN;
        this.mStringValue = null;
        this.f2593a = customVariable.f2593a;
        this.mType = customVariable.mType;
        this.mIntegerValue = customVariable.mIntegerValue;
        this.mFloatValue = customVariable.mFloatValue;
        this.mStringValue = customVariable.mStringValue;
        this.f2594b = customVariable.f2594b;
    }

    private static int clamp(int i2) {
        int i3 = (i2 & (~(i2 >> 31))) - 255;
        return (i3 & (i3 >> 31)) + 255;
    }

    public static String colorString(int i2) {
        return MqttTopic.MULTI_LEVEL_WILDCARD + ("00000000" + Integer.toHexString(i2)).substring(r2.length() - 8);
    }

    public static int hsvToRgb(float f2, float f3, float f4) {
        float f5 = f2 * 6.0f;
        int i2 = (int) f5;
        float f6 = f5 - i2;
        float f7 = f4 * 255.0f;
        int i3 = (int) (((1.0f - f3) * f7) + 0.5f);
        int i4 = (int) (((1.0f - (f6 * f3)) * f7) + 0.5f);
        int i5 = (int) (((1.0f - ((1.0f - f6) * f3)) * f7) + 0.5f);
        int i6 = (int) (f7 + 0.5f);
        if (i2 == 0) {
            return ((i6 << 16) + (i5 << 8) + i3) | ViewCompat.MEASURED_STATE_MASK;
        }
        if (i2 == 1) {
            return ((i4 << 16) + (i6 << 8) + i3) | ViewCompat.MEASURED_STATE_MASK;
        }
        if (i2 == 2) {
            return ((i3 << 16) + (i6 << 8) + i5) | ViewCompat.MEASURED_STATE_MASK;
        }
        if (i2 == 3) {
            return ((i3 << 16) + (i4 << 8) + i6) | ViewCompat.MEASURED_STATE_MASK;
        }
        if (i2 == 4) {
            return ((i5 << 16) + (i3 << 8) + i6) | ViewCompat.MEASURED_STATE_MASK;
        }
        if (i2 != 5) {
            return 0;
        }
        return ((i6 << 16) + (i3 << 8) + i4) | ViewCompat.MEASURED_STATE_MASK;
    }

    public static int rgbaTocColor(float f2, float f3, float f4, float f5) {
        int iClamp = clamp((int) (f2 * 255.0f));
        int iClamp2 = clamp((int) (f3 * 255.0f));
        return (iClamp << 16) | (clamp((int) (f5 * 255.0f)) << 24) | (iClamp2 << 8) | clamp((int) (f4 * 255.0f));
    }

    public void applyToWidget(MotionWidget motionWidget) {
        int i2 = this.mType;
        switch (i2) {
            case TypedValues.Custom.TYPE_INT /* 900 */:
            case TypedValues.Custom.TYPE_COLOR /* 902 */:
            case TypedValues.Custom.TYPE_REFERENCE /* 906 */:
                motionWidget.setCustomAttribute(this.f2593a, i2, this.mIntegerValue);
                break;
            case TypedValues.Custom.TYPE_FLOAT /* 901 */:
            case TypedValues.Custom.TYPE_DIMENSION /* 905 */:
                motionWidget.setCustomAttribute(this.f2593a, i2, this.mFloatValue);
                break;
            case TypedValues.Custom.TYPE_STRING /* 903 */:
                motionWidget.setCustomAttribute(this.f2593a, i2, this.mStringValue);
                break;
            case TypedValues.Custom.TYPE_BOOLEAN /* 904 */:
                motionWidget.setCustomAttribute(this.f2593a, i2, this.f2594b);
                break;
        }
    }

    public CustomVariable copy() {
        return new CustomVariable(this);
    }

    public boolean diff(CustomVariable customVariable) {
        int i2;
        if (customVariable == null || (i2 = this.mType) != customVariable.mType) {
            return false;
        }
        switch (i2) {
            case TypedValues.Custom.TYPE_INT /* 900 */:
            case TypedValues.Custom.TYPE_REFERENCE /* 906 */:
                return this.mIntegerValue == customVariable.mIntegerValue;
            case TypedValues.Custom.TYPE_FLOAT /* 901 */:
                return this.mFloatValue == customVariable.mFloatValue;
            case TypedValues.Custom.TYPE_COLOR /* 902 */:
                return this.mIntegerValue == customVariable.mIntegerValue;
            case TypedValues.Custom.TYPE_STRING /* 903 */:
                return this.mIntegerValue == customVariable.mIntegerValue;
            case TypedValues.Custom.TYPE_BOOLEAN /* 904 */:
                return this.f2594b == customVariable.f2594b;
            case TypedValues.Custom.TYPE_DIMENSION /* 905 */:
                return this.mFloatValue == customVariable.mFloatValue;
            default:
                return false;
        }
    }

    public boolean getBooleanValue() {
        return this.f2594b;
    }

    public int getColorValue() {
        return this.mIntegerValue;
    }

    public float getFloatValue() {
        return this.mFloatValue;
    }

    public int getIntegerValue() {
        return this.mIntegerValue;
    }

    public int getInterpolatedColor(float[] fArr) {
        return (clamp((int) (fArr[3] * 255.0f)) << 24) | (clamp((int) (((float) Math.pow(fArr[0], 0.45454545454545453d)) * 255.0f)) << 16) | (clamp((int) (((float) Math.pow(fArr[1], 0.45454545454545453d)) * 255.0f)) << 8) | clamp((int) (((float) Math.pow(fArr[2], 0.45454545454545453d)) * 255.0f));
    }

    public String getName() {
        return this.f2593a;
    }

    public String getStringValue() {
        return this.mStringValue;
    }

    public int getType() {
        return this.mType;
    }

    public float getValueToInterpolate() {
        switch (this.mType) {
            case TypedValues.Custom.TYPE_INT /* 900 */:
                return this.mIntegerValue;
            case TypedValues.Custom.TYPE_FLOAT /* 901 */:
                return this.mFloatValue;
            case TypedValues.Custom.TYPE_COLOR /* 902 */:
                throw new RuntimeException("Color does not have a single color to interpolate");
            case TypedValues.Custom.TYPE_STRING /* 903 */:
                throw new RuntimeException("Cannot interpolate String");
            case TypedValues.Custom.TYPE_BOOLEAN /* 904 */:
                return this.f2594b ? 1.0f : 0.0f;
            case TypedValues.Custom.TYPE_DIMENSION /* 905 */:
                return this.mFloatValue;
            default:
                return Float.NaN;
        }
    }

    public void getValuesToInterpolate(float[] fArr) {
        switch (this.mType) {
            case TypedValues.Custom.TYPE_INT /* 900 */:
                fArr[0] = this.mIntegerValue;
                return;
            case TypedValues.Custom.TYPE_FLOAT /* 901 */:
                fArr[0] = this.mFloatValue;
                return;
            case TypedValues.Custom.TYPE_COLOR /* 902 */:
                int i2 = (this.mIntegerValue >> 24) & 255;
                float fPow = (float) Math.pow(((r0 >> 16) & 255) / 255.0f, 2.2d);
                float fPow2 = (float) Math.pow(((r0 >> 8) & 255) / 255.0f, 2.2d);
                float fPow3 = (float) Math.pow((r0 & 255) / 255.0f, 2.2d);
                fArr[0] = fPow;
                fArr[1] = fPow2;
                fArr[2] = fPow3;
                fArr[3] = i2 / 255.0f;
                return;
            case TypedValues.Custom.TYPE_STRING /* 903 */:
                throw new RuntimeException("Cannot interpolate String");
            case TypedValues.Custom.TYPE_BOOLEAN /* 904 */:
                fArr[0] = this.f2594b ? 1.0f : 0.0f;
                return;
            case TypedValues.Custom.TYPE_DIMENSION /* 905 */:
                fArr[0] = this.mFloatValue;
                return;
            default:
                return;
        }
    }

    public boolean isContinuous() {
        int i2 = this.mType;
        return (i2 == 903 || i2 == 904 || i2 == 906) ? false : true;
    }

    public int numberOfInterpolatedValues() {
        return this.mType != 902 ? 1 : 4;
    }

    public void setBooleanValue(boolean z2) {
        this.f2594b = z2;
    }

    public void setFloatValue(float f2) {
        this.mFloatValue = f2;
    }

    public void setIntValue(int i2) {
        this.mIntegerValue = i2;
    }

    public void setInterpolatedValue(MotionWidget motionWidget, float[] fArr) {
        int i2 = this.mType;
        switch (i2) {
            case TypedValues.Custom.TYPE_INT /* 900 */:
                motionWidget.setCustomAttribute(this.f2593a, i2, (int) fArr[0]);
                return;
            case TypedValues.Custom.TYPE_FLOAT /* 901 */:
            case TypedValues.Custom.TYPE_DIMENSION /* 905 */:
                motionWidget.setCustomAttribute(this.f2593a, i2, fArr[0]);
                return;
            case TypedValues.Custom.TYPE_COLOR /* 902 */:
                motionWidget.setCustomAttribute(this.f2593a, this.mType, (clamp((int) (fArr[3] * 255.0f)) << 24) | (clamp((int) (((float) Math.pow(fArr[0], 0.45454545454545453d)) * 255.0f)) << 16) | (clamp((int) (((float) Math.pow(fArr[1], 0.45454545454545453d)) * 255.0f)) << 8) | clamp((int) (((float) Math.pow(fArr[2], 0.45454545454545453d)) * 255.0f)));
                return;
            case TypedValues.Custom.TYPE_STRING /* 903 */:
            case TypedValues.Custom.TYPE_REFERENCE /* 906 */:
                throw new RuntimeException("unable to interpolate " + this.f2593a);
            case TypedValues.Custom.TYPE_BOOLEAN /* 904 */:
                motionWidget.setCustomAttribute(this.f2593a, i2, fArr[0] > 0.5f);
                return;
            default:
                return;
        }
    }

    public void setStringValue(String str) {
        this.mStringValue = str;
    }

    public void setValue(float[] fArr) {
        switch (this.mType) {
            case TypedValues.Custom.TYPE_INT /* 900 */:
            case TypedValues.Custom.TYPE_REFERENCE /* 906 */:
                this.mIntegerValue = (int) fArr[0];
                return;
            case TypedValues.Custom.TYPE_FLOAT /* 901 */:
            case TypedValues.Custom.TYPE_DIMENSION /* 905 */:
                this.mFloatValue = fArr[0];
                return;
            case TypedValues.Custom.TYPE_COLOR /* 902 */:
                this.mIntegerValue = ((Math.round(fArr[3] * 255.0f) & 255) << 24) | ((Math.round(((float) Math.pow(fArr[0], 0.5d)) * 255.0f) & 255) << 16) | ((Math.round(((float) Math.pow(fArr[1], 0.5d)) * 255.0f) & 255) << 8) | (Math.round(((float) Math.pow(fArr[2], 0.5d)) * 255.0f) & 255);
                return;
            case TypedValues.Custom.TYPE_STRING /* 903 */:
                throw new RuntimeException("Cannot interpolate String");
            case TypedValues.Custom.TYPE_BOOLEAN /* 904 */:
                this.f2594b = ((double) fArr[0]) > 0.5d;
                return;
            default:
                return;
        }
    }

    public String toString() {
        String str = this.f2593a + ':';
        switch (this.mType) {
            case TypedValues.Custom.TYPE_INT /* 900 */:
                return str + this.mIntegerValue;
            case TypedValues.Custom.TYPE_FLOAT /* 901 */:
                return str + this.mFloatValue;
            case TypedValues.Custom.TYPE_COLOR /* 902 */:
                return str + colorString(this.mIntegerValue);
            case TypedValues.Custom.TYPE_STRING /* 903 */:
                return str + this.mStringValue;
            case TypedValues.Custom.TYPE_BOOLEAN /* 904 */:
                return str + Boolean.valueOf(this.f2594b);
            case TypedValues.Custom.TYPE_DIMENSION /* 905 */:
                return str + this.mFloatValue;
            default:
                return str + "????";
        }
    }

    public CustomVariable(String str, int i2, String str2) {
        this.mIntegerValue = Integer.MIN_VALUE;
        this.mFloatValue = Float.NaN;
        this.f2593a = str;
        this.mType = i2;
        this.mStringValue = str2;
    }

    public void setValue(Object obj) {
        switch (this.mType) {
            case TypedValues.Custom.TYPE_INT /* 900 */:
            case TypedValues.Custom.TYPE_REFERENCE /* 906 */:
                this.mIntegerValue = ((Integer) obj).intValue();
                break;
            case TypedValues.Custom.TYPE_FLOAT /* 901 */:
                this.mFloatValue = ((Float) obj).floatValue();
                break;
            case TypedValues.Custom.TYPE_COLOR /* 902 */:
                this.mIntegerValue = ((Integer) obj).intValue();
                break;
            case TypedValues.Custom.TYPE_STRING /* 903 */:
                this.mStringValue = (String) obj;
                break;
            case TypedValues.Custom.TYPE_BOOLEAN /* 904 */:
                this.f2594b = ((Boolean) obj).booleanValue();
                break;
            case TypedValues.Custom.TYPE_DIMENSION /* 905 */:
                this.mFloatValue = ((Float) obj).floatValue();
                break;
        }
    }

    public CustomVariable(String str, int i2, int i3) {
        this.mIntegerValue = Integer.MIN_VALUE;
        this.mFloatValue = Float.NaN;
        this.mStringValue = null;
        this.f2593a = str;
        this.mType = i2;
        if (i2 == 901) {
            this.mFloatValue = i3;
        } else {
            this.mIntegerValue = i3;
        }
    }

    public CustomVariable(String str, int i2, float f2) {
        this.mIntegerValue = Integer.MIN_VALUE;
        this.mStringValue = null;
        this.f2593a = str;
        this.mType = i2;
        this.mFloatValue = f2;
    }

    public CustomVariable(String str, int i2, boolean z2) {
        this.mIntegerValue = Integer.MIN_VALUE;
        this.mFloatValue = Float.NaN;
        this.mStringValue = null;
        this.f2593a = str;
        this.mType = i2;
        this.f2594b = z2;
    }

    public CustomVariable(String str, int i2) {
        this.mIntegerValue = Integer.MIN_VALUE;
        this.mFloatValue = Float.NaN;
        this.mStringValue = null;
        this.f2593a = str;
        this.mType = i2;
    }

    public CustomVariable(String str, int i2, Object obj) {
        this.mIntegerValue = Integer.MIN_VALUE;
        this.mFloatValue = Float.NaN;
        this.mStringValue = null;
        this.f2593a = str;
        this.mType = i2;
        setValue(obj);
    }

    public CustomVariable(CustomVariable customVariable, Object obj) {
        this.mIntegerValue = Integer.MIN_VALUE;
        this.mFloatValue = Float.NaN;
        this.mStringValue = null;
        this.f2593a = customVariable.f2593a;
        this.mType = customVariable.mType;
        setValue(obj);
    }
}
