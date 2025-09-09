package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.motion.utils.ViewSpline;
import java.util.HashMap;
import java.util.HashSet;

/* loaded from: classes.dex */
public abstract class Key {
    public static final String ALPHA = "alpha";
    public static final String CURVEFIT = "curveFit";
    public static final String CUSTOM = "CUSTOM";
    public static final String ELEVATION = "elevation";
    public static final String MOTIONPROGRESS = "motionProgress";
    public static final String PIVOT_X = "transformPivotX";
    public static final String PIVOT_Y = "transformPivotY";
    public static final String PROGRESS = "progress";
    public static final String ROTATION = "rotation";
    public static final String ROTATION_X = "rotationX";
    public static final String ROTATION_Y = "rotationY";
    public static final String SCALE_X = "scaleX";
    public static final String SCALE_Y = "scaleY";
    public static final String TRANSITIONEASING = "transitionEasing";
    public static final String TRANSITION_PATH_ROTATE = "transitionPathRotate";
    public static final String TRANSLATION_X = "translationX";
    public static final String TRANSLATION_Y = "translationY";
    public static final String TRANSLATION_Z = "translationZ";
    public static int UNSET = -1;
    public static final String VISIBILITY = "visibility";
    public static final String WAVE_OFFSET = "waveOffset";
    public static final String WAVE_PERIOD = "wavePeriod";
    public static final String WAVE_PHASE = "wavePhase";
    public static final String WAVE_VARIES_BY = "waveVariesBy";

    /* renamed from: a, reason: collision with root package name */
    int f2997a;

    /* renamed from: b, reason: collision with root package name */
    int f2998b;

    /* renamed from: c, reason: collision with root package name */
    String f2999c;

    /* renamed from: d, reason: collision with root package name */
    protected int f3000d;

    /* renamed from: e, reason: collision with root package name */
    HashMap f3001e;

    public Key() {
        int i2 = UNSET;
        this.f2997a = i2;
        this.f2998b = i2;
        this.f2999c = null;
    }

    boolean a(String str) {
        String str2 = this.f2999c;
        if (str2 == null || str == null) {
            return false;
        }
        return str.matches(str2);
    }

    public abstract void addValues(HashMap<String, ViewSpline> map);

    boolean b(Object obj) {
        return obj instanceof Boolean ? ((Boolean) obj).booleanValue() : Boolean.parseBoolean(obj.toString());
    }

    float c(Object obj) {
        return obj instanceof Float ? ((Float) obj).floatValue() : Float.parseFloat(obj.toString());
    }

    @Override // 
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public abstract Key mo4clone();

    public Key copy(Key key) {
        this.f2997a = key.f2997a;
        this.f2998b = key.f2998b;
        this.f2999c = key.f2999c;
        this.f3000d = key.f3000d;
        this.f3001e = key.f3001e;
        return this;
    }

    int d(Object obj) {
        return obj instanceof Integer ? ((Integer) obj).intValue() : Integer.parseInt(obj.toString());
    }

    abstract void getAttributeNames(HashSet hashSet);

    public int getFramePosition() {
        return this.f2997a;
    }

    abstract void load(Context context, AttributeSet attributeSet);

    public void setFramePosition(int i2) {
        this.f2997a = i2;
    }

    public void setInterpolation(HashMap<String, Integer> map) {
    }

    public abstract void setValue(String str, Object obj);

    public Key setViewId(int i2) {
        this.f2998b = i2;
        return this;
    }
}
