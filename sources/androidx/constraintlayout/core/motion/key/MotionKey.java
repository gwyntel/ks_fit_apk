package androidx.constraintlayout.core.motion.key;

import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.HashMap;
import java.util.HashSet;

/* loaded from: classes.dex */
public abstract class MotionKey implements TypedValues {
    public static final String ALPHA = "alpha";
    public static final String CUSTOM = "CUSTOM";
    public static final String ELEVATION = "elevation";
    public static final String ROTATION = "rotationZ";
    public static final String ROTATION_X = "rotationX";
    public static final String SCALE_X = "scaleX";
    public static final String SCALE_Y = "scaleY";
    public static final String TRANSITION_PATH_ROTATE = "transitionPathRotate";
    public static final String TRANSLATION_X = "translationX";
    public static final String TRANSLATION_Y = "translationY";
    public static int UNSET = -1;
    public static final String VISIBILITY = "visibility";

    /* renamed from: a, reason: collision with root package name */
    int f2637a;

    /* renamed from: b, reason: collision with root package name */
    String f2638b;
    public HashMap<String, CustomVariable> mCustom;
    public int mFramePosition;
    public int mType;

    public MotionKey() {
        int i2 = UNSET;
        this.mFramePosition = i2;
        this.f2637a = i2;
        this.f2638b = null;
    }

    float a(Object obj) {
        return obj instanceof Float ? ((Float) obj).floatValue() : Float.parseFloat(obj.toString());
    }

    public abstract void addValues(HashMap<String, SplineSet> map);

    int b(Object obj) {
        return obj instanceof Integer ? ((Integer) obj).intValue() : Integer.parseInt(obj.toString());
    }

    @Override // 
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public abstract MotionKey mo3clone();

    public MotionKey copy(MotionKey motionKey) {
        this.mFramePosition = motionKey.mFramePosition;
        this.f2637a = motionKey.f2637a;
        this.f2638b = motionKey.f2638b;
        this.mType = motionKey.mType;
        return this;
    }

    public abstract void getAttributeNames(HashSet<String> hashSet);

    public int getFramePosition() {
        return this.mFramePosition;
    }

    public void setCustomAttribute(String str, int i2, float f2) {
        this.mCustom.put(str, new CustomVariable(str, i2, f2));
    }

    public void setFramePosition(int i2) {
        this.mFramePosition = i2;
    }

    public void setInterpolation(HashMap<String, Integer> map) {
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, float f2) {
        return false;
    }

    public MotionKey setViewId(int i2) {
        this.f2637a = i2;
        return this;
    }

    public void setCustomAttribute(String str, int i2, int i3) {
        this.mCustom.put(str, new CustomVariable(str, i2, i3));
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, boolean z2) {
        return false;
    }

    public void setCustomAttribute(String str, int i2, boolean z2) {
        this.mCustom.put(str, new CustomVariable(str, i2, z2));
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, int i3) {
        if (i2 != 100) {
            return false;
        }
        this.mFramePosition = i3;
        return true;
    }

    public void setCustomAttribute(String str, int i2, String str2) {
        this.mCustom.put(str, new CustomVariable(str, i2, str2));
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, String str) {
        if (i2 != 101) {
            return false;
        }
        this.f2638b = str;
        return true;
    }
}
