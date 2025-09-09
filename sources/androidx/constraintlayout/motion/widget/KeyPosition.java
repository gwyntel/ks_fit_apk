package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.widget.R;
import java.util.HashMap;

/* loaded from: classes.dex */
public class KeyPosition extends KeyPositionBase {
    public static final String DRAWPATH = "drawPath";
    public static final String PERCENT_HEIGHT = "percentHeight";
    public static final String PERCENT_WIDTH = "percentWidth";
    public static final String PERCENT_X = "percentX";
    public static final String PERCENT_Y = "percentY";
    public static final String SIZE_PERCENT = "sizePercent";
    private static final String TAG = "KeyPosition";
    public static final String TRANSITION_EASING = "transitionEasing";
    public static final int TYPE_CARTESIAN = 0;
    public static final int TYPE_PATH = 1;
    public static final int TYPE_SCREEN = 2;

    /* renamed from: g, reason: collision with root package name */
    String f3003g = null;

    /* renamed from: h, reason: collision with root package name */
    int f3004h = Key.UNSET;

    /* renamed from: i, reason: collision with root package name */
    int f3005i = 0;

    /* renamed from: j, reason: collision with root package name */
    float f3006j = Float.NaN;

    /* renamed from: k, reason: collision with root package name */
    float f3007k = Float.NaN;

    /* renamed from: l, reason: collision with root package name */
    float f3008l = Float.NaN;

    /* renamed from: m, reason: collision with root package name */
    float f3009m = Float.NaN;

    /* renamed from: n, reason: collision with root package name */
    float f3010n = Float.NaN;

    /* renamed from: o, reason: collision with root package name */
    float f3011o = Float.NaN;

    /* renamed from: p, reason: collision with root package name */
    int f3012p = 0;
    private float mCalculatedPositionX = Float.NaN;
    private float mCalculatedPositionY = Float.NaN;

    private static class Loader {
        private static final int CURVE_FIT = 4;
        private static final int DRAW_PATH = 5;
        private static final int FRAME_POSITION = 2;
        private static final int PATH_MOTION_ARC = 10;
        private static final int PERCENT_HEIGHT = 12;
        private static final int PERCENT_WIDTH = 11;
        private static final int PERCENT_X = 6;
        private static final int PERCENT_Y = 7;
        private static final int SIZE_PERCENT = 8;
        private static final int TARGET_ID = 1;
        private static final int TRANSITION_EASING = 3;
        private static final int TYPE = 9;
        private static SparseIntArray mAttrMap;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mAttrMap = sparseIntArray;
            sparseIntArray.append(R.styleable.KeyPosition_motionTarget, 1);
            mAttrMap.append(R.styleable.KeyPosition_framePosition, 2);
            mAttrMap.append(R.styleable.KeyPosition_transitionEasing, 3);
            mAttrMap.append(R.styleable.KeyPosition_curveFit, 4);
            mAttrMap.append(R.styleable.KeyPosition_drawPath, 5);
            mAttrMap.append(R.styleable.KeyPosition_percentX, 6);
            mAttrMap.append(R.styleable.KeyPosition_percentY, 7);
            mAttrMap.append(R.styleable.KeyPosition_keyPositionType, 9);
            mAttrMap.append(R.styleable.KeyPosition_sizePercent, 8);
            mAttrMap.append(R.styleable.KeyPosition_percentWidth, 11);
            mAttrMap.append(R.styleable.KeyPosition_percentHeight, 12);
            mAttrMap.append(R.styleable.KeyPosition_pathMotionArc, 10);
        }

        private Loader() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void read(KeyPosition keyPosition, TypedArray typedArray) {
            int indexCount = typedArray.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArray.getIndex(i2);
                switch (mAttrMap.get(index)) {
                    case 1:
                        if (MotionLayout.IS_IN_EDIT_MODE) {
                            int resourceId = typedArray.getResourceId(index, keyPosition.f2998b);
                            keyPosition.f2998b = resourceId;
                            if (resourceId == -1) {
                                keyPosition.f2999c = typedArray.getString(index);
                                break;
                            } else {
                                break;
                            }
                        } else if (typedArray.peekValue(index).type == 3) {
                            keyPosition.f2999c = typedArray.getString(index);
                            break;
                        } else {
                            keyPosition.f2998b = typedArray.getResourceId(index, keyPosition.f2998b);
                            break;
                        }
                    case 2:
                        keyPosition.f2997a = typedArray.getInt(index, keyPosition.f2997a);
                        break;
                    case 3:
                        if (typedArray.peekValue(index).type == 3) {
                            keyPosition.f3003g = typedArray.getString(index);
                            break;
                        } else {
                            keyPosition.f3003g = Easing.NAMED_EASING[typedArray.getInteger(index, 0)];
                            break;
                        }
                    case 4:
                        keyPosition.f3013f = typedArray.getInteger(index, keyPosition.f3013f);
                        break;
                    case 5:
                        keyPosition.f3005i = typedArray.getInt(index, keyPosition.f3005i);
                        break;
                    case 6:
                        keyPosition.f3008l = typedArray.getFloat(index, keyPosition.f3008l);
                        break;
                    case 7:
                        keyPosition.f3009m = typedArray.getFloat(index, keyPosition.f3009m);
                        break;
                    case 8:
                        float f2 = typedArray.getFloat(index, keyPosition.f3007k);
                        keyPosition.f3006j = f2;
                        keyPosition.f3007k = f2;
                        break;
                    case 9:
                        keyPosition.f3012p = typedArray.getInt(index, keyPosition.f3012p);
                        break;
                    case 10:
                        keyPosition.f3004h = typedArray.getInt(index, keyPosition.f3004h);
                        break;
                    case 11:
                        keyPosition.f3006j = typedArray.getFloat(index, keyPosition.f3006j);
                        break;
                    case 12:
                        keyPosition.f3007k = typedArray.getFloat(index, keyPosition.f3007k);
                        break;
                    default:
                        Log.e(KeyPosition.TAG, "unused attribute 0x" + Integer.toHexString(index) + "   " + mAttrMap.get(index));
                        break;
                }
            }
            if (keyPosition.f2997a == -1) {
                Log.e(KeyPosition.TAG, "no frame position");
            }
        }
    }

    public KeyPosition() {
        this.f3000d = 2;
    }

    private void calcCartesianPosition(float f2, float f3, float f4, float f5) {
        float f6 = f4 - f2;
        float f7 = f5 - f3;
        float f8 = Float.isNaN(this.f3008l) ? 0.0f : this.f3008l;
        float f9 = Float.isNaN(this.f3011o) ? 0.0f : this.f3011o;
        float f10 = Float.isNaN(this.f3009m) ? 0.0f : this.f3009m;
        this.mCalculatedPositionX = (int) (f2 + (f8 * f6) + ((Float.isNaN(this.f3010n) ? 0.0f : this.f3010n) * f7));
        this.mCalculatedPositionY = (int) (f3 + (f6 * f9) + (f7 * f10));
    }

    private void calcPathPosition(float f2, float f3, float f4, float f5) {
        float f6 = f4 - f2;
        float f7 = f5 - f3;
        float f8 = this.f3008l;
        float f9 = this.f3009m;
        this.mCalculatedPositionX = f2 + (f6 * f8) + ((-f7) * f9);
        this.mCalculatedPositionY = f3 + (f7 * f8) + (f6 * f9);
    }

    private void calcScreenPosition(int i2, int i3) {
        float f2 = this.f3008l;
        float f3 = 0;
        this.mCalculatedPositionX = (i2 * f2) + f3;
        this.mCalculatedPositionY = (i3 * f2) + f3;
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void addValues(HashMap<String, ViewSpline> map) {
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public Key copy(Key key) {
        super.copy(key);
        KeyPosition keyPosition = (KeyPosition) key;
        this.f3003g = keyPosition.f3003g;
        this.f3004h = keyPosition.f3004h;
        this.f3005i = keyPosition.f3005i;
        this.f3006j = keyPosition.f3006j;
        this.f3007k = Float.NaN;
        this.f3008l = keyPosition.f3008l;
        this.f3009m = keyPosition.f3009m;
        this.f3010n = keyPosition.f3010n;
        this.f3011o = keyPosition.f3011o;
        this.mCalculatedPositionX = keyPosition.mCalculatedPositionX;
        this.mCalculatedPositionY = keyPosition.mCalculatedPositionY;
        return this;
    }

    void e(int i2, int i3, float f2, float f3, float f4, float f5) {
        int i4 = this.f3012p;
        if (i4 == 1) {
            calcPathPosition(f2, f3, f4, f5);
        } else if (i4 != 2) {
            calcCartesianPosition(f2, f3, f4, f5);
        } else {
            calcScreenPosition(i2, i3);
        }
    }

    void f(RectF rectF, RectF rectF2, float f2, float f3, String[] strArr, float[] fArr) {
        float fCenterX = rectF.centerX();
        float fCenterY = rectF.centerY();
        float fCenterX2 = rectF2.centerX() - fCenterX;
        float fCenterY2 = rectF2.centerY() - fCenterY;
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

    void g(RectF rectF, RectF rectF2, float f2, float f3, String[] strArr, float[] fArr) {
        float fCenterX = rectF.centerX();
        float fCenterY = rectF.centerY();
        float fCenterX2 = rectF2.centerX() - fCenterX;
        float fCenterY2 = rectF2.centerY() - fCenterY;
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

    void h(View view, RectF rectF, RectF rectF2, float f2, float f3, String[] strArr, float[] fArr) {
        rectF.centerX();
        rectF.centerY();
        rectF2.centerX();
        rectF2.centerY();
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        int width = viewGroup.getWidth();
        int height = viewGroup.getHeight();
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

    @Override // androidx.constraintlayout.motion.widget.KeyPositionBase
    public boolean intersects(int i2, int i3, RectF rectF, RectF rectF2, float f2, float f3) {
        e(i2, i3, rectF.centerX(), rectF.centerY(), rectF2.centerX(), rectF2.centerY());
        return Math.abs(f2 - this.mCalculatedPositionX) < 20.0f && Math.abs(f3 - this.mCalculatedPositionY) < 20.0f;
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void load(Context context, AttributeSet attributeSet) {
        Loader.read(this, context.obtainStyledAttributes(attributeSet, R.styleable.KeyPosition));
    }

    @Override // androidx.constraintlayout.motion.widget.KeyPositionBase
    public void positionAttributes(View view, RectF rectF, RectF rectF2, float f2, float f3, String[] strArr, float[] fArr) {
        int i2 = this.f3012p;
        if (i2 == 1) {
            g(rectF, rectF2, f2, f3, strArr, fArr);
        } else if (i2 != 2) {
            f(rectF, rectF2, f2, f3, strArr, fArr);
        } else {
            h(view, rectF, rectF2, f2, f3, strArr, fArr);
        }
    }

    public void setType(int i2) {
        this.f3012p = i2;
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void setValue(String str, Object obj) {
        str.hashCode();
        switch (str) {
            case "transitionEasing":
                this.f3003g = obj.toString();
                break;
            case "percentWidth":
                this.f3006j = c(obj);
                break;
            case "percentHeight":
                this.f3007k = c(obj);
                break;
            case "drawPath":
                this.f3005i = d(obj);
                break;
            case "sizePercent":
                float fC = c(obj);
                this.f3006j = fC;
                this.f3007k = fC;
                break;
            case "percentX":
                this.f3008l = c(obj);
                break;
            case "percentY":
                this.f3009m = c(obj);
                break;
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    /* renamed from: clone */
    public Key mo4clone() {
        return new KeyPosition().copy(this);
    }
}
