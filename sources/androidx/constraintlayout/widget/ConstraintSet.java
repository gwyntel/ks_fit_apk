package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.HelperWidget;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.R;
import androidx.exifinterface.media.ExifInterface;
import androidx.media3.extractor.text.ttml.TtmlNode;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.huawei.hms.framework.common.ContainerUtils;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class ConstraintSet {
    private static final int ALPHA = 43;
    private static final int ANIMATE_CIRCLE_ANGLE_TO = 82;
    private static final int ANIMATE_RELATIVE_TO = 64;
    private static final int BARRIER_ALLOWS_GONE_WIDGETS = 75;
    private static final int BARRIER_DIRECTION = 72;
    private static final int BARRIER_MARGIN = 73;
    private static final int BARRIER_TYPE = 1;
    public static final int BASELINE = 5;
    private static final int BASELINE_MARGIN = 93;
    private static final int BASELINE_TO_BASELINE = 1;
    private static final int BASELINE_TO_BOTTOM = 92;
    private static final int BASELINE_TO_TOP = 91;
    public static final int BOTTOM = 4;
    private static final int BOTTOM_MARGIN = 2;
    private static final int BOTTOM_TO_BOTTOM = 3;
    private static final int BOTTOM_TO_TOP = 4;
    public static final int CHAIN_PACKED = 2;
    public static final int CHAIN_SPREAD = 0;
    public static final int CHAIN_SPREAD_INSIDE = 1;
    private static final int CHAIN_USE_RTL = 71;
    private static final int CIRCLE = 61;
    private static final int CIRCLE_ANGLE = 63;
    private static final int CIRCLE_RADIUS = 62;
    public static final int CIRCLE_REFERENCE = 8;
    private static final int CONSTRAINED_HEIGHT = 81;
    private static final int CONSTRAINED_WIDTH = 80;
    private static final int CONSTRAINT_REFERENCED_IDS = 74;
    private static final int CONSTRAINT_TAG = 77;
    private static final boolean DEBUG = false;
    private static final int DIMENSION_RATIO = 5;
    private static final int DRAW_PATH = 66;
    private static final int EDITOR_ABSOLUTE_X = 6;
    private static final int EDITOR_ABSOLUTE_Y = 7;
    private static final int ELEVATION = 44;
    public static final int END = 7;
    private static final int END_MARGIN = 8;
    private static final int END_TO_END = 9;
    private static final int END_TO_START = 10;
    private static final String ERROR_MESSAGE = "XML parser error must be within a Constraint ";
    public static final int GONE = 8;
    private static final int GONE_BASELINE_MARGIN = 94;
    private static final int GONE_BOTTOM_MARGIN = 11;
    private static final int GONE_END_MARGIN = 12;
    private static final int GONE_LEFT_MARGIN = 13;
    private static final int GONE_RIGHT_MARGIN = 14;
    private static final int GONE_START_MARGIN = 15;
    private static final int GONE_TOP_MARGIN = 16;
    private static final int GUIDELINE_USE_RTL = 99;
    private static final int GUIDE_BEGIN = 17;
    private static final int GUIDE_END = 18;
    private static final int GUIDE_PERCENT = 19;
    private static final int HEIGHT_DEFAULT = 55;
    private static final int HEIGHT_MAX = 57;
    private static final int HEIGHT_MIN = 59;
    private static final int HEIGHT_PERCENT = 70;
    public static final int HORIZONTAL = 0;
    private static final int HORIZONTAL_BIAS = 20;
    public static final int HORIZONTAL_GUIDELINE = 0;
    private static final int HORIZONTAL_STYLE = 41;
    private static final int HORIZONTAL_WEIGHT = 39;
    private static final int INTERNAL_MATCH_CONSTRAINT = -3;
    private static final int INTERNAL_MATCH_PARENT = -1;
    private static final int INTERNAL_WRAP_CONTENT = -2;
    private static final int INTERNAL_WRAP_CONTENT_CONSTRAINED = -4;
    public static final int INVISIBLE = 4;
    private static final String KEY_PERCENT_PARENT = "parent";
    private static final String KEY_RATIO = "ratio";
    private static final String KEY_WEIGHT = "weight";
    private static final int LAYOUT_CONSTRAINT_HEIGHT = 96;
    private static final int LAYOUT_CONSTRAINT_WIDTH = 95;
    private static final int LAYOUT_HEIGHT = 21;
    private static final int LAYOUT_VISIBILITY = 22;
    private static final int LAYOUT_WIDTH = 23;
    private static final int LAYOUT_WRAP_BEHAVIOR = 97;
    public static final int LEFT = 1;
    private static final int LEFT_MARGIN = 24;
    private static final int LEFT_TO_LEFT = 25;
    private static final int LEFT_TO_RIGHT = 26;
    public static final int MATCH_CONSTRAINT = 0;
    public static final int MATCH_CONSTRAINT_PERCENT = 2;
    public static final int MATCH_CONSTRAINT_SPREAD = 0;
    public static final int MATCH_CONSTRAINT_WRAP = 1;
    private static final int MOTION_STAGGER = 79;
    private static final int MOTION_TARGET = 98;
    private static final int ORIENTATION = 27;
    public static final int PARENT_ID = 0;
    private static final int PATH_MOTION_ARC = 76;
    private static final int PROGRESS = 68;
    private static final int QUANTIZE_MOTION_INTERPOLATOR = 86;
    private static final int QUANTIZE_MOTION_INTERPOLATOR_ID = 89;
    private static final int QUANTIZE_MOTION_INTERPOLATOR_STR = 90;
    private static final int QUANTIZE_MOTION_INTERPOLATOR_TYPE = 88;
    private static final int QUANTIZE_MOTION_PHASE = 85;
    private static final int QUANTIZE_MOTION_STEPS = 84;
    public static final int RIGHT = 2;
    private static final int RIGHT_MARGIN = 28;
    private static final int RIGHT_TO_LEFT = 29;
    private static final int RIGHT_TO_RIGHT = 30;
    public static final int ROTATE_LEFT_OF_PORTRATE = 4;
    public static final int ROTATE_NONE = 0;
    public static final int ROTATE_PORTRATE_OF_LEFT = 2;
    public static final int ROTATE_PORTRATE_OF_RIGHT = 1;
    public static final int ROTATE_RIGHT_OF_PORTRATE = 3;
    private static final int ROTATION = 60;
    private static final int ROTATION_X = 45;
    private static final int ROTATION_Y = 46;
    private static final int SCALE_X = 47;
    private static final int SCALE_Y = 48;
    public static final int START = 6;
    private static final int START_MARGIN = 31;
    private static final int START_TO_END = 32;
    private static final int START_TO_START = 33;
    private static final String TAG = "ConstraintSet";
    public static final int TOP = 3;
    private static final int TOP_MARGIN = 34;
    private static final int TOP_TO_BOTTOM = 35;
    private static final int TOP_TO_TOP = 36;
    private static final int TRANSFORM_PIVOT_TARGET = 83;
    private static final int TRANSFORM_PIVOT_X = 49;
    private static final int TRANSFORM_PIVOT_Y = 50;
    private static final int TRANSITION_EASING = 65;
    private static final int TRANSITION_PATH_ROTATE = 67;
    private static final int TRANSLATION_X = 51;
    private static final int TRANSLATION_Y = 52;
    private static final int TRANSLATION_Z = 53;
    public static final int UNSET = -1;
    private static final int UNUSED = 87;
    public static final int VERTICAL = 1;
    private static final int VERTICAL_BIAS = 37;
    public static final int VERTICAL_GUIDELINE = 1;
    private static final int VERTICAL_STYLE = 42;
    private static final int VERTICAL_WEIGHT = 40;
    private static final int VIEW_ID = 38;
    private static final int VISIBILITY_MODE = 78;
    public static final int VISIBILITY_MODE_IGNORE = 1;
    public static final int VISIBILITY_MODE_NORMAL = 0;
    public static final int VISIBLE = 0;
    private static final int WIDTH_DEFAULT = 54;
    private static final int WIDTH_MAX = 56;
    private static final int WIDTH_MIN = 58;
    private static final int WIDTH_PERCENT = 69;
    public static final int WRAP_CONTENT = -2;
    public String mIdString;
    private boolean mValidate;
    private static final int[] VISIBILITY_FLAGS = {0, 4, 8};
    private static SparseIntArray mapToConstant = new SparseIntArray();
    private static SparseIntArray overrideMapToConstant = new SparseIntArray();
    public String derivedState = "";
    public int mRotate = 0;
    private HashMap<String, ConstraintAttribute> mSavedAttributes = new HashMap<>();
    private boolean mForceId = true;
    private HashMap<Integer, Constraint> mConstraints = new HashMap<>();

    public static class Constraint {

        /* renamed from: a, reason: collision with root package name */
        int f3281a;

        /* renamed from: b, reason: collision with root package name */
        String f3282b;

        /* renamed from: c, reason: collision with root package name */
        Delta f3283c;
        public final PropertySet propertySet = new PropertySet();
        public final Motion motion = new Motion();
        public final Layout layout = new Layout();
        public final Transform transform = new Transform();
        public HashMap<String, ConstraintAttribute> mCustomConstraints = new HashMap<>();

        static class Delta {
            private static final int INITIAL_BOOLEAN = 4;
            private static final int INITIAL_FLOAT = 10;
            private static final int INITIAL_INT = 10;
            private static final int INITIAL_STRING = 5;

            /* renamed from: a, reason: collision with root package name */
            int[] f3284a = new int[10];

            /* renamed from: b, reason: collision with root package name */
            int[] f3285b = new int[10];

            /* renamed from: c, reason: collision with root package name */
            int f3286c = 0;

            /* renamed from: d, reason: collision with root package name */
            int[] f3287d = new int[10];

            /* renamed from: e, reason: collision with root package name */
            float[] f3288e = new float[10];

            /* renamed from: f, reason: collision with root package name */
            int f3289f = 0;

            /* renamed from: g, reason: collision with root package name */
            int[] f3290g = new int[5];

            /* renamed from: h, reason: collision with root package name */
            String[] f3291h = new String[5];

            /* renamed from: i, reason: collision with root package name */
            int f3292i = 0;

            /* renamed from: j, reason: collision with root package name */
            int[] f3293j = new int[4];

            /* renamed from: k, reason: collision with root package name */
            boolean[] f3294k = new boolean[4];

            /* renamed from: l, reason: collision with root package name */
            int f3295l = 0;

            Delta() {
            }

            void a(int i2, float f2) {
                int i3 = this.f3289f;
                int[] iArr = this.f3287d;
                if (i3 >= iArr.length) {
                    this.f3287d = Arrays.copyOf(iArr, iArr.length * 2);
                    float[] fArr = this.f3288e;
                    this.f3288e = Arrays.copyOf(fArr, fArr.length * 2);
                }
                int[] iArr2 = this.f3287d;
                int i4 = this.f3289f;
                iArr2[i4] = i2;
                float[] fArr2 = this.f3288e;
                this.f3289f = i4 + 1;
                fArr2[i4] = f2;
            }

            void b(int i2, int i3) {
                int i4 = this.f3286c;
                int[] iArr = this.f3284a;
                if (i4 >= iArr.length) {
                    this.f3284a = Arrays.copyOf(iArr, iArr.length * 2);
                    int[] iArr2 = this.f3285b;
                    this.f3285b = Arrays.copyOf(iArr2, iArr2.length * 2);
                }
                int[] iArr3 = this.f3284a;
                int i5 = this.f3286c;
                iArr3[i5] = i2;
                int[] iArr4 = this.f3285b;
                this.f3286c = i5 + 1;
                iArr4[i5] = i3;
            }

            void c(int i2, String str) {
                int i3 = this.f3292i;
                int[] iArr = this.f3290g;
                if (i3 >= iArr.length) {
                    this.f3290g = Arrays.copyOf(iArr, iArr.length * 2);
                    String[] strArr = this.f3291h;
                    this.f3291h = (String[]) Arrays.copyOf(strArr, strArr.length * 2);
                }
                int[] iArr2 = this.f3290g;
                int i4 = this.f3292i;
                iArr2[i4] = i2;
                String[] strArr2 = this.f3291h;
                this.f3292i = i4 + 1;
                strArr2[i4] = str;
            }

            void d(int i2, boolean z2) {
                int i3 = this.f3295l;
                int[] iArr = this.f3293j;
                if (i3 >= iArr.length) {
                    this.f3293j = Arrays.copyOf(iArr, iArr.length * 2);
                    boolean[] zArr = this.f3294k;
                    this.f3294k = Arrays.copyOf(zArr, zArr.length * 2);
                }
                int[] iArr2 = this.f3293j;
                int i4 = this.f3295l;
                iArr2[i4] = i2;
                boolean[] zArr2 = this.f3294k;
                this.f3295l = i4 + 1;
                zArr2[i4] = z2;
            }

            void e(Constraint constraint) {
                for (int i2 = 0; i2 < this.f3286c; i2++) {
                    ConstraintSet.setDeltaValue(constraint, this.f3284a[i2], this.f3285b[i2]);
                }
                for (int i3 = 0; i3 < this.f3289f; i3++) {
                    ConstraintSet.setDeltaValue(constraint, this.f3287d[i3], this.f3288e[i3]);
                }
                for (int i4 = 0; i4 < this.f3292i; i4++) {
                    ConstraintSet.setDeltaValue(constraint, this.f3290g[i4], this.f3291h[i4]);
                }
                for (int i5 = 0; i5 < this.f3295l; i5++) {
                    ConstraintSet.setDeltaValue(constraint, this.f3293j[i5], this.f3294k[i5]);
                }
            }

            void f(String str) {
                Log.v(str, "int");
                for (int i2 = 0; i2 < this.f3286c; i2++) {
                    Log.v(str, this.f3284a[i2] + " = " + this.f3285b[i2]);
                }
                Log.v(str, "float");
                for (int i3 = 0; i3 < this.f3289f; i3++) {
                    Log.v(str, this.f3287d[i3] + " = " + this.f3288e[i3]);
                }
                Log.v(str, "strings");
                for (int i4 = 0; i4 < this.f3292i; i4++) {
                    Log.v(str, this.f3290g[i4] + " = " + this.f3291h[i4]);
                }
                Log.v(str, TypedValues.Custom.S_BOOLEAN);
                for (int i5 = 0; i5 < this.f3295l; i5++) {
                    Log.v(str, this.f3293j[i5] + " = " + this.f3294k[i5]);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void fillFrom(int i2, ConstraintLayout.LayoutParams layoutParams) {
            this.f3281a = i2;
            Layout layout = this.layout;
            layout.leftToLeft = layoutParams.leftToLeft;
            layout.leftToRight = layoutParams.leftToRight;
            layout.rightToLeft = layoutParams.rightToLeft;
            layout.rightToRight = layoutParams.rightToRight;
            layout.topToTop = layoutParams.topToTop;
            layout.topToBottom = layoutParams.topToBottom;
            layout.bottomToTop = layoutParams.bottomToTop;
            layout.bottomToBottom = layoutParams.bottomToBottom;
            layout.baselineToBaseline = layoutParams.baselineToBaseline;
            layout.baselineToTop = layoutParams.baselineToTop;
            layout.baselineToBottom = layoutParams.baselineToBottom;
            layout.startToEnd = layoutParams.startToEnd;
            layout.startToStart = layoutParams.startToStart;
            layout.endToStart = layoutParams.endToStart;
            layout.endToEnd = layoutParams.endToEnd;
            layout.horizontalBias = layoutParams.horizontalBias;
            layout.verticalBias = layoutParams.verticalBias;
            layout.dimensionRatio = layoutParams.dimensionRatio;
            layout.circleConstraint = layoutParams.circleConstraint;
            layout.circleRadius = layoutParams.circleRadius;
            layout.circleAngle = layoutParams.circleAngle;
            layout.editorAbsoluteX = layoutParams.editorAbsoluteX;
            layout.editorAbsoluteY = layoutParams.editorAbsoluteY;
            layout.orientation = layoutParams.orientation;
            layout.guidePercent = layoutParams.guidePercent;
            layout.guideBegin = layoutParams.guideBegin;
            layout.guideEnd = layoutParams.guideEnd;
            layout.mWidth = ((ViewGroup.MarginLayoutParams) layoutParams).width;
            layout.mHeight = ((ViewGroup.MarginLayoutParams) layoutParams).height;
            layout.leftMargin = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
            layout.rightMargin = ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
            layout.topMargin = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
            layout.bottomMargin = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
            layout.baselineMargin = layoutParams.baselineMargin;
            layout.verticalWeight = layoutParams.verticalWeight;
            layout.horizontalWeight = layoutParams.horizontalWeight;
            layout.verticalChainStyle = layoutParams.verticalChainStyle;
            layout.horizontalChainStyle = layoutParams.horizontalChainStyle;
            layout.constrainedWidth = layoutParams.constrainedWidth;
            layout.constrainedHeight = layoutParams.constrainedHeight;
            layout.widthDefault = layoutParams.matchConstraintDefaultWidth;
            layout.heightDefault = layoutParams.matchConstraintDefaultHeight;
            layout.widthMax = layoutParams.matchConstraintMaxWidth;
            layout.heightMax = layoutParams.matchConstraintMaxHeight;
            layout.widthMin = layoutParams.matchConstraintMinWidth;
            layout.heightMin = layoutParams.matchConstraintMinHeight;
            layout.widthPercent = layoutParams.matchConstraintPercentWidth;
            layout.heightPercent = layoutParams.matchConstraintPercentHeight;
            layout.mConstraintTag = layoutParams.constraintTag;
            layout.goneTopMargin = layoutParams.goneTopMargin;
            layout.goneBottomMargin = layoutParams.goneBottomMargin;
            layout.goneLeftMargin = layoutParams.goneLeftMargin;
            layout.goneRightMargin = layoutParams.goneRightMargin;
            layout.goneStartMargin = layoutParams.goneStartMargin;
            layout.goneEndMargin = layoutParams.goneEndMargin;
            layout.goneBaselineMargin = layoutParams.goneBaselineMargin;
            layout.mWrapBehavior = layoutParams.wrapBehaviorInParent;
            layout.endMargin = layoutParams.getMarginEnd();
            this.layout.startMargin = layoutParams.getMarginStart();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void fillFromConstraints(ConstraintHelper constraintHelper, int i2, Constraints.LayoutParams layoutParams) {
            fillFromConstraints(i2, layoutParams);
            if (constraintHelper instanceof Barrier) {
                Layout layout = this.layout;
                layout.mHelperType = 1;
                Barrier barrier = (Barrier) constraintHelper;
                layout.mBarrierDirection = barrier.getType();
                this.layout.mReferenceIds = barrier.getReferencedIds();
                this.layout.mBarrierMargin = barrier.getMargin();
            }
        }

        private ConstraintAttribute get(String str, ConstraintAttribute.AttributeType attributeType) {
            if (!this.mCustomConstraints.containsKey(str)) {
                ConstraintAttribute constraintAttribute = new ConstraintAttribute(str, attributeType);
                this.mCustomConstraints.put(str, constraintAttribute);
                return constraintAttribute;
            }
            ConstraintAttribute constraintAttribute2 = this.mCustomConstraints.get(str);
            if (constraintAttribute2.getType() == attributeType) {
                return constraintAttribute2;
            }
            throw new IllegalArgumentException("ConstraintAttribute is already a " + constraintAttribute2.getType().name());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setColorValue(String str, int i2) {
            get(str, ConstraintAttribute.AttributeType.COLOR_TYPE).setColorValue(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setFloatValue(String str, float f2) {
            get(str, ConstraintAttribute.AttributeType.FLOAT_TYPE).setFloatValue(f2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setIntValue(String str, int i2) {
            get(str, ConstraintAttribute.AttributeType.INT_TYPE).setIntValue(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setStringValue(String str, String str2) {
            get(str, ConstraintAttribute.AttributeType.STRING_TYPE).setStringValue(str2);
        }

        public void applyDelta(Constraint constraint) {
            Delta delta = this.f3283c;
            if (delta != null) {
                delta.e(constraint);
            }
        }

        public void applyTo(ConstraintLayout.LayoutParams layoutParams) {
            Layout layout = this.layout;
            layoutParams.leftToLeft = layout.leftToLeft;
            layoutParams.leftToRight = layout.leftToRight;
            layoutParams.rightToLeft = layout.rightToLeft;
            layoutParams.rightToRight = layout.rightToRight;
            layoutParams.topToTop = layout.topToTop;
            layoutParams.topToBottom = layout.topToBottom;
            layoutParams.bottomToTop = layout.bottomToTop;
            layoutParams.bottomToBottom = layout.bottomToBottom;
            layoutParams.baselineToBaseline = layout.baselineToBaseline;
            layoutParams.baselineToTop = layout.baselineToTop;
            layoutParams.baselineToBottom = layout.baselineToBottom;
            layoutParams.startToEnd = layout.startToEnd;
            layoutParams.startToStart = layout.startToStart;
            layoutParams.endToStart = layout.endToStart;
            layoutParams.endToEnd = layout.endToEnd;
            ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = layout.leftMargin;
            ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = layout.rightMargin;
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = layout.topMargin;
            ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = layout.bottomMargin;
            layoutParams.goneStartMargin = layout.goneStartMargin;
            layoutParams.goneEndMargin = layout.goneEndMargin;
            layoutParams.goneTopMargin = layout.goneTopMargin;
            layoutParams.goneBottomMargin = layout.goneBottomMargin;
            layoutParams.horizontalBias = layout.horizontalBias;
            layoutParams.verticalBias = layout.verticalBias;
            layoutParams.circleConstraint = layout.circleConstraint;
            layoutParams.circleRadius = layout.circleRadius;
            layoutParams.circleAngle = layout.circleAngle;
            layoutParams.dimensionRatio = layout.dimensionRatio;
            layoutParams.editorAbsoluteX = layout.editorAbsoluteX;
            layoutParams.editorAbsoluteY = layout.editorAbsoluteY;
            layoutParams.verticalWeight = layout.verticalWeight;
            layoutParams.horizontalWeight = layout.horizontalWeight;
            layoutParams.verticalChainStyle = layout.verticalChainStyle;
            layoutParams.horizontalChainStyle = layout.horizontalChainStyle;
            layoutParams.constrainedWidth = layout.constrainedWidth;
            layoutParams.constrainedHeight = layout.constrainedHeight;
            layoutParams.matchConstraintDefaultWidth = layout.widthDefault;
            layoutParams.matchConstraintDefaultHeight = layout.heightDefault;
            layoutParams.matchConstraintMaxWidth = layout.widthMax;
            layoutParams.matchConstraintMaxHeight = layout.heightMax;
            layoutParams.matchConstraintMinWidth = layout.widthMin;
            layoutParams.matchConstraintMinHeight = layout.heightMin;
            layoutParams.matchConstraintPercentWidth = layout.widthPercent;
            layoutParams.matchConstraintPercentHeight = layout.heightPercent;
            layoutParams.orientation = layout.orientation;
            layoutParams.guidePercent = layout.guidePercent;
            layoutParams.guideBegin = layout.guideBegin;
            layoutParams.guideEnd = layout.guideEnd;
            ((ViewGroup.MarginLayoutParams) layoutParams).width = layout.mWidth;
            ((ViewGroup.MarginLayoutParams) layoutParams).height = layout.mHeight;
            String str = layout.mConstraintTag;
            if (str != null) {
                layoutParams.constraintTag = str;
            }
            layoutParams.wrapBehaviorInParent = layout.mWrapBehavior;
            layoutParams.setMarginStart(layout.startMargin);
            layoutParams.setMarginEnd(this.layout.endMargin);
            layoutParams.validate();
        }

        public void printDelta(String str) {
            Delta delta = this.f3283c;
            if (delta != null) {
                delta.f(str);
            } else {
                Log.v(str, "DELTA IS NULL");
            }
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public Constraint m5clone() {
            Constraint constraint = new Constraint();
            constraint.layout.copyFrom(this.layout);
            constraint.motion.copyFrom(this.motion);
            constraint.propertySet.copyFrom(this.propertySet);
            constraint.transform.copyFrom(this.transform);
            constraint.f3281a = this.f3281a;
            constraint.f3283c = this.f3283c;
            return constraint;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void fillFromConstraints(int i2, Constraints.LayoutParams layoutParams) {
            fillFrom(i2, layoutParams);
            this.propertySet.alpha = layoutParams.alpha;
            Transform transform = this.transform;
            transform.rotation = layoutParams.rotation;
            transform.rotationX = layoutParams.rotationX;
            transform.rotationY = layoutParams.rotationY;
            transform.scaleX = layoutParams.scaleX;
            transform.scaleY = layoutParams.scaleY;
            transform.transformPivotX = layoutParams.transformPivotX;
            transform.transformPivotY = layoutParams.transformPivotY;
            transform.translationX = layoutParams.translationX;
            transform.translationY = layoutParams.translationY;
            transform.translationZ = layoutParams.translationZ;
            transform.elevation = layoutParams.elevation;
            transform.applyElevation = layoutParams.applyElevation;
        }
    }

    public static class Layout {
        private static final int BARRIER_ALLOWS_GONE_WIDGETS = 75;
        private static final int BARRIER_DIRECTION = 72;
        private static final int BARRIER_MARGIN = 73;
        private static final int BASELINE_MARGIN = 80;
        private static final int BASELINE_TO_BASELINE = 1;
        private static final int BASELINE_TO_BOTTOM = 78;
        private static final int BASELINE_TO_TOP = 77;
        private static final int BOTTOM_MARGIN = 2;
        private static final int BOTTOM_TO_BOTTOM = 3;
        private static final int BOTTOM_TO_TOP = 4;
        private static final int CHAIN_USE_RTL = 71;
        private static final int CIRCLE = 61;
        private static final int CIRCLE_ANGLE = 63;
        private static final int CIRCLE_RADIUS = 62;
        private static final int CONSTRAINED_HEIGHT = 88;
        private static final int CONSTRAINED_WIDTH = 87;
        private static final int CONSTRAINT_REFERENCED_IDS = 74;
        private static final int CONSTRAINT_TAG = 89;
        private static final int DIMENSION_RATIO = 5;
        private static final int EDITOR_ABSOLUTE_X = 6;
        private static final int EDITOR_ABSOLUTE_Y = 7;
        private static final int END_MARGIN = 8;
        private static final int END_TO_END = 9;
        private static final int END_TO_START = 10;
        private static final int GONE_BASELINE_MARGIN = 79;
        private static final int GONE_BOTTOM_MARGIN = 11;
        private static final int GONE_END_MARGIN = 12;
        private static final int GONE_LEFT_MARGIN = 13;
        private static final int GONE_RIGHT_MARGIN = 14;
        private static final int GONE_START_MARGIN = 15;
        private static final int GONE_TOP_MARGIN = 16;
        private static final int GUIDE_BEGIN = 17;
        private static final int GUIDE_END = 18;
        private static final int GUIDE_PERCENT = 19;
        private static final int GUIDE_USE_RTL = 90;
        private static final int HEIGHT_DEFAULT = 82;
        private static final int HEIGHT_MAX = 83;
        private static final int HEIGHT_MIN = 85;
        private static final int HEIGHT_PERCENT = 70;
        private static final int HORIZONTAL_BIAS = 20;
        private static final int HORIZONTAL_STYLE = 39;
        private static final int HORIZONTAL_WEIGHT = 37;
        private static final int LAYOUT_CONSTRAINT_HEIGHT = 42;
        private static final int LAYOUT_CONSTRAINT_WIDTH = 41;
        private static final int LAYOUT_HEIGHT = 21;
        private static final int LAYOUT_WIDTH = 22;
        private static final int LAYOUT_WRAP_BEHAVIOR = 76;
        private static final int LEFT_MARGIN = 23;
        private static final int LEFT_TO_LEFT = 24;
        private static final int LEFT_TO_RIGHT = 25;
        private static final int ORIENTATION = 26;
        private static final int RIGHT_MARGIN = 27;
        private static final int RIGHT_TO_LEFT = 28;
        private static final int RIGHT_TO_RIGHT = 29;
        private static final int START_MARGIN = 30;
        private static final int START_TO_END = 31;
        private static final int START_TO_START = 32;
        private static final int TOP_MARGIN = 33;
        private static final int TOP_TO_BOTTOM = 34;
        private static final int TOP_TO_TOP = 35;
        public static final int UNSET = -1;
        public static final int UNSET_GONE_MARGIN = Integer.MIN_VALUE;
        private static final int UNUSED = 91;
        private static final int VERTICAL_BIAS = 36;
        private static final int VERTICAL_STYLE = 40;
        private static final int VERTICAL_WEIGHT = 38;
        private static final int WIDTH_DEFAULT = 81;
        private static final int WIDTH_MAX = 84;
        private static final int WIDTH_MIN = 86;
        private static final int WIDTH_PERCENT = 69;
        private static SparseIntArray mapToConstant;
        public String mConstraintTag;
        public int mHeight;
        public String mReferenceIdString;
        public int[] mReferenceIds;
        public int mWidth;
        public boolean mIsGuideline = false;
        public boolean mApply = false;
        public boolean mOverride = false;
        public int guideBegin = -1;
        public int guideEnd = -1;
        public float guidePercent = -1.0f;
        public boolean guidelineUseRtl = true;
        public int leftToLeft = -1;
        public int leftToRight = -1;
        public int rightToLeft = -1;
        public int rightToRight = -1;
        public int topToTop = -1;
        public int topToBottom = -1;
        public int bottomToTop = -1;
        public int bottomToBottom = -1;
        public int baselineToBaseline = -1;
        public int baselineToTop = -1;
        public int baselineToBottom = -1;
        public int startToEnd = -1;
        public int startToStart = -1;
        public int endToStart = -1;
        public int endToEnd = -1;
        public float horizontalBias = 0.5f;
        public float verticalBias = 0.5f;
        public String dimensionRatio = null;
        public int circleConstraint = -1;
        public int circleRadius = 0;
        public float circleAngle = 0.0f;
        public int editorAbsoluteX = -1;
        public int editorAbsoluteY = -1;
        public int orientation = -1;
        public int leftMargin = 0;
        public int rightMargin = 0;
        public int topMargin = 0;
        public int bottomMargin = 0;
        public int endMargin = 0;
        public int startMargin = 0;
        public int baselineMargin = 0;
        public int goneLeftMargin = Integer.MIN_VALUE;
        public int goneTopMargin = Integer.MIN_VALUE;
        public int goneRightMargin = Integer.MIN_VALUE;
        public int goneBottomMargin = Integer.MIN_VALUE;
        public int goneEndMargin = Integer.MIN_VALUE;
        public int goneStartMargin = Integer.MIN_VALUE;
        public int goneBaselineMargin = Integer.MIN_VALUE;
        public float verticalWeight = -1.0f;
        public float horizontalWeight = -1.0f;
        public int horizontalChainStyle = 0;
        public int verticalChainStyle = 0;
        public int widthDefault = 0;
        public int heightDefault = 0;
        public int widthMax = 0;
        public int heightMax = 0;
        public int widthMin = 0;
        public int heightMin = 0;
        public float widthPercent = 1.0f;
        public float heightPercent = 1.0f;
        public int mBarrierDirection = -1;
        public int mBarrierMargin = 0;
        public int mHelperType = -1;
        public boolean constrainedWidth = false;
        public boolean constrainedHeight = false;
        public boolean mBarrierAllowsGoneWidgets = true;
        public int mWrapBehavior = 0;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mapToConstant = sparseIntArray;
            sparseIntArray.append(R.styleable.Layout_layout_constraintLeft_toLeftOf, 24);
            mapToConstant.append(R.styleable.Layout_layout_constraintLeft_toRightOf, 25);
            mapToConstant.append(R.styleable.Layout_layout_constraintRight_toLeftOf, 28);
            mapToConstant.append(R.styleable.Layout_layout_constraintRight_toRightOf, 29);
            mapToConstant.append(R.styleable.Layout_layout_constraintTop_toTopOf, 35);
            mapToConstant.append(R.styleable.Layout_layout_constraintTop_toBottomOf, 34);
            mapToConstant.append(R.styleable.Layout_layout_constraintBottom_toTopOf, 4);
            mapToConstant.append(R.styleable.Layout_layout_constraintBottom_toBottomOf, 3);
            mapToConstant.append(R.styleable.Layout_layout_constraintBaseline_toBaselineOf, 1);
            mapToConstant.append(R.styleable.Layout_layout_editor_absoluteX, 6);
            mapToConstant.append(R.styleable.Layout_layout_editor_absoluteY, 7);
            mapToConstant.append(R.styleable.Layout_layout_constraintGuide_begin, 17);
            mapToConstant.append(R.styleable.Layout_layout_constraintGuide_end, 18);
            mapToConstant.append(R.styleable.Layout_layout_constraintGuide_percent, 19);
            mapToConstant.append(R.styleable.Layout_guidelineUseRtl, 90);
            mapToConstant.append(R.styleable.Layout_android_orientation, 26);
            mapToConstant.append(R.styleable.Layout_layout_constraintStart_toEndOf, 31);
            mapToConstant.append(R.styleable.Layout_layout_constraintStart_toStartOf, 32);
            mapToConstant.append(R.styleable.Layout_layout_constraintEnd_toStartOf, 10);
            mapToConstant.append(R.styleable.Layout_layout_constraintEnd_toEndOf, 9);
            mapToConstant.append(R.styleable.Layout_layout_goneMarginLeft, 13);
            mapToConstant.append(R.styleable.Layout_layout_goneMarginTop, 16);
            mapToConstant.append(R.styleable.Layout_layout_goneMarginRight, 14);
            mapToConstant.append(R.styleable.Layout_layout_goneMarginBottom, 11);
            mapToConstant.append(R.styleable.Layout_layout_goneMarginStart, 15);
            mapToConstant.append(R.styleable.Layout_layout_goneMarginEnd, 12);
            mapToConstant.append(R.styleable.Layout_layout_constraintVertical_weight, 38);
            mapToConstant.append(R.styleable.Layout_layout_constraintHorizontal_weight, 37);
            mapToConstant.append(R.styleable.Layout_layout_constraintHorizontal_chainStyle, 39);
            mapToConstant.append(R.styleable.Layout_layout_constraintVertical_chainStyle, 40);
            mapToConstant.append(R.styleable.Layout_layout_constraintHorizontal_bias, 20);
            mapToConstant.append(R.styleable.Layout_layout_constraintVertical_bias, 36);
            mapToConstant.append(R.styleable.Layout_layout_constraintDimensionRatio, 5);
            mapToConstant.append(R.styleable.Layout_layout_constraintLeft_creator, 91);
            mapToConstant.append(R.styleable.Layout_layout_constraintTop_creator, 91);
            mapToConstant.append(R.styleable.Layout_layout_constraintRight_creator, 91);
            mapToConstant.append(R.styleable.Layout_layout_constraintBottom_creator, 91);
            mapToConstant.append(R.styleable.Layout_layout_constraintBaseline_creator, 91);
            mapToConstant.append(R.styleable.Layout_android_layout_marginLeft, 23);
            mapToConstant.append(R.styleable.Layout_android_layout_marginRight, 27);
            mapToConstant.append(R.styleable.Layout_android_layout_marginStart, 30);
            mapToConstant.append(R.styleable.Layout_android_layout_marginEnd, 8);
            mapToConstant.append(R.styleable.Layout_android_layout_marginTop, 33);
            mapToConstant.append(R.styleable.Layout_android_layout_marginBottom, 2);
            mapToConstant.append(R.styleable.Layout_android_layout_width, 22);
            mapToConstant.append(R.styleable.Layout_android_layout_height, 21);
            mapToConstant.append(R.styleable.Layout_layout_constraintWidth, 41);
            mapToConstant.append(R.styleable.Layout_layout_constraintHeight, 42);
            mapToConstant.append(R.styleable.Layout_layout_constrainedWidth, 41);
            mapToConstant.append(R.styleable.Layout_layout_constrainedHeight, 42);
            mapToConstant.append(R.styleable.Layout_layout_wrapBehaviorInParent, 76);
            mapToConstant.append(R.styleable.Layout_layout_constraintCircle, 61);
            mapToConstant.append(R.styleable.Layout_layout_constraintCircleRadius, 62);
            mapToConstant.append(R.styleable.Layout_layout_constraintCircleAngle, 63);
            mapToConstant.append(R.styleable.Layout_layout_constraintWidth_percent, 69);
            mapToConstant.append(R.styleable.Layout_layout_constraintHeight_percent, 70);
            mapToConstant.append(R.styleable.Layout_chainUseRtl, 71);
            mapToConstant.append(R.styleable.Layout_barrierDirection, 72);
            mapToConstant.append(R.styleable.Layout_barrierMargin, 73);
            mapToConstant.append(R.styleable.Layout_constraint_referenced_ids, 74);
            mapToConstant.append(R.styleable.Layout_barrierAllowsGoneWidgets, 75);
        }

        void a(Context context, AttributeSet attributeSet) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Layout);
            this.mApply = true;
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                int i3 = mapToConstant.get(index);
                switch (i3) {
                    case 1:
                        this.baselineToBaseline = ConstraintSet.lookupID(typedArrayObtainStyledAttributes, index, this.baselineToBaseline);
                        break;
                    case 2:
                        this.bottomMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.bottomMargin);
                        break;
                    case 3:
                        this.bottomToBottom = ConstraintSet.lookupID(typedArrayObtainStyledAttributes, index, this.bottomToBottom);
                        break;
                    case 4:
                        this.bottomToTop = ConstraintSet.lookupID(typedArrayObtainStyledAttributes, index, this.bottomToTop);
                        break;
                    case 5:
                        this.dimensionRatio = typedArrayObtainStyledAttributes.getString(index);
                        break;
                    case 6:
                        this.editorAbsoluteX = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.editorAbsoluteX);
                        break;
                    case 7:
                        this.editorAbsoluteY = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.editorAbsoluteY);
                        break;
                    case 8:
                        this.endMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.endMargin);
                        break;
                    case 9:
                        this.endToEnd = ConstraintSet.lookupID(typedArrayObtainStyledAttributes, index, this.endToEnd);
                        break;
                    case 10:
                        this.endToStart = ConstraintSet.lookupID(typedArrayObtainStyledAttributes, index, this.endToStart);
                        break;
                    case 11:
                        this.goneBottomMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.goneBottomMargin);
                        break;
                    case 12:
                        this.goneEndMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.goneEndMargin);
                        break;
                    case 13:
                        this.goneLeftMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.goneLeftMargin);
                        break;
                    case 14:
                        this.goneRightMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.goneRightMargin);
                        break;
                    case 15:
                        this.goneStartMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.goneStartMargin);
                        break;
                    case 16:
                        this.goneTopMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.goneTopMargin);
                        break;
                    case 17:
                        this.guideBegin = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.guideBegin);
                        break;
                    case 18:
                        this.guideEnd = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.guideEnd);
                        break;
                    case 19:
                        this.guidePercent = typedArrayObtainStyledAttributes.getFloat(index, this.guidePercent);
                        break;
                    case 20:
                        this.horizontalBias = typedArrayObtainStyledAttributes.getFloat(index, this.horizontalBias);
                        break;
                    case 21:
                        this.mHeight = typedArrayObtainStyledAttributes.getLayoutDimension(index, this.mHeight);
                        break;
                    case 22:
                        this.mWidth = typedArrayObtainStyledAttributes.getLayoutDimension(index, this.mWidth);
                        break;
                    case 23:
                        this.leftMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.leftMargin);
                        break;
                    case 24:
                        this.leftToLeft = ConstraintSet.lookupID(typedArrayObtainStyledAttributes, index, this.leftToLeft);
                        break;
                    case 25:
                        this.leftToRight = ConstraintSet.lookupID(typedArrayObtainStyledAttributes, index, this.leftToRight);
                        break;
                    case 26:
                        this.orientation = typedArrayObtainStyledAttributes.getInt(index, this.orientation);
                        break;
                    case 27:
                        this.rightMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.rightMargin);
                        break;
                    case 28:
                        this.rightToLeft = ConstraintSet.lookupID(typedArrayObtainStyledAttributes, index, this.rightToLeft);
                        break;
                    case 29:
                        this.rightToRight = ConstraintSet.lookupID(typedArrayObtainStyledAttributes, index, this.rightToRight);
                        break;
                    case 30:
                        this.startMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.startMargin);
                        break;
                    case 31:
                        this.startToEnd = ConstraintSet.lookupID(typedArrayObtainStyledAttributes, index, this.startToEnd);
                        break;
                    case 32:
                        this.startToStart = ConstraintSet.lookupID(typedArrayObtainStyledAttributes, index, this.startToStart);
                        break;
                    case 33:
                        this.topMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.topMargin);
                        break;
                    case 34:
                        this.topToBottom = ConstraintSet.lookupID(typedArrayObtainStyledAttributes, index, this.topToBottom);
                        break;
                    case 35:
                        this.topToTop = ConstraintSet.lookupID(typedArrayObtainStyledAttributes, index, this.topToTop);
                        break;
                    case 36:
                        this.verticalBias = typedArrayObtainStyledAttributes.getFloat(index, this.verticalBias);
                        break;
                    case 37:
                        this.horizontalWeight = typedArrayObtainStyledAttributes.getFloat(index, this.horizontalWeight);
                        break;
                    case 38:
                        this.verticalWeight = typedArrayObtainStyledAttributes.getFloat(index, this.verticalWeight);
                        break;
                    case 39:
                        this.horizontalChainStyle = typedArrayObtainStyledAttributes.getInt(index, this.horizontalChainStyle);
                        break;
                    case 40:
                        this.verticalChainStyle = typedArrayObtainStyledAttributes.getInt(index, this.verticalChainStyle);
                        break;
                    case 41:
                        ConstraintSet.i(this, typedArrayObtainStyledAttributes, index, 0);
                        break;
                    case 42:
                        ConstraintSet.i(this, typedArrayObtainStyledAttributes, index, 1);
                        break;
                    default:
                        switch (i3) {
                            case 61:
                                this.circleConstraint = ConstraintSet.lookupID(typedArrayObtainStyledAttributes, index, this.circleConstraint);
                                break;
                            case 62:
                                this.circleRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.circleRadius);
                                break;
                            case 63:
                                this.circleAngle = typedArrayObtainStyledAttributes.getFloat(index, this.circleAngle);
                                break;
                            default:
                                switch (i3) {
                                    case 69:
                                        this.widthPercent = typedArrayObtainStyledAttributes.getFloat(index, 1.0f);
                                        break;
                                    case 70:
                                        this.heightPercent = typedArrayObtainStyledAttributes.getFloat(index, 1.0f);
                                        break;
                                    case 71:
                                        Log.e(ConstraintSet.TAG, "CURRENTLY UNSUPPORTED");
                                        break;
                                    case 72:
                                        this.mBarrierDirection = typedArrayObtainStyledAttributes.getInt(index, this.mBarrierDirection);
                                        break;
                                    case 73:
                                        this.mBarrierMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.mBarrierMargin);
                                        break;
                                    case 74:
                                        this.mReferenceIdString = typedArrayObtainStyledAttributes.getString(index);
                                        break;
                                    case 75:
                                        this.mBarrierAllowsGoneWidgets = typedArrayObtainStyledAttributes.getBoolean(index, this.mBarrierAllowsGoneWidgets);
                                        break;
                                    case 76:
                                        this.mWrapBehavior = typedArrayObtainStyledAttributes.getInt(index, this.mWrapBehavior);
                                        break;
                                    case 77:
                                        this.baselineToTop = ConstraintSet.lookupID(typedArrayObtainStyledAttributes, index, this.baselineToTop);
                                        break;
                                    case 78:
                                        this.baselineToBottom = ConstraintSet.lookupID(typedArrayObtainStyledAttributes, index, this.baselineToBottom);
                                        break;
                                    case 79:
                                        this.goneBaselineMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.goneBaselineMargin);
                                        break;
                                    case 80:
                                        this.baselineMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.baselineMargin);
                                        break;
                                    case 81:
                                        this.widthDefault = typedArrayObtainStyledAttributes.getInt(index, this.widthDefault);
                                        break;
                                    case 82:
                                        this.heightDefault = typedArrayObtainStyledAttributes.getInt(index, this.heightDefault);
                                        break;
                                    case 83:
                                        this.heightMax = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.heightMax);
                                        break;
                                    case 84:
                                        this.widthMax = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.widthMax);
                                        break;
                                    case 85:
                                        this.heightMin = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.heightMin);
                                        break;
                                    case 86:
                                        this.widthMin = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.widthMin);
                                        break;
                                    case 87:
                                        this.constrainedWidth = typedArrayObtainStyledAttributes.getBoolean(index, this.constrainedWidth);
                                        break;
                                    case 88:
                                        this.constrainedHeight = typedArrayObtainStyledAttributes.getBoolean(index, this.constrainedHeight);
                                        break;
                                    case 89:
                                        this.mConstraintTag = typedArrayObtainStyledAttributes.getString(index);
                                        break;
                                    case 90:
                                        this.guidelineUseRtl = typedArrayObtainStyledAttributes.getBoolean(index, this.guidelineUseRtl);
                                        break;
                                    case 91:
                                        Log.w(ConstraintSet.TAG, "unused attribute 0x" + Integer.toHexString(index) + "   " + mapToConstant.get(index));
                                        break;
                                    default:
                                        Log.w(ConstraintSet.TAG, "Unknown attribute 0x" + Integer.toHexString(index) + "   " + mapToConstant.get(index));
                                        break;
                                }
                        }
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }

        public void copyFrom(Layout layout) {
            this.mIsGuideline = layout.mIsGuideline;
            this.mWidth = layout.mWidth;
            this.mApply = layout.mApply;
            this.mHeight = layout.mHeight;
            this.guideBegin = layout.guideBegin;
            this.guideEnd = layout.guideEnd;
            this.guidePercent = layout.guidePercent;
            this.guidelineUseRtl = layout.guidelineUseRtl;
            this.leftToLeft = layout.leftToLeft;
            this.leftToRight = layout.leftToRight;
            this.rightToLeft = layout.rightToLeft;
            this.rightToRight = layout.rightToRight;
            this.topToTop = layout.topToTop;
            this.topToBottom = layout.topToBottom;
            this.bottomToTop = layout.bottomToTop;
            this.bottomToBottom = layout.bottomToBottom;
            this.baselineToBaseline = layout.baselineToBaseline;
            this.baselineToTop = layout.baselineToTop;
            this.baselineToBottom = layout.baselineToBottom;
            this.startToEnd = layout.startToEnd;
            this.startToStart = layout.startToStart;
            this.endToStart = layout.endToStart;
            this.endToEnd = layout.endToEnd;
            this.horizontalBias = layout.horizontalBias;
            this.verticalBias = layout.verticalBias;
            this.dimensionRatio = layout.dimensionRatio;
            this.circleConstraint = layout.circleConstraint;
            this.circleRadius = layout.circleRadius;
            this.circleAngle = layout.circleAngle;
            this.editorAbsoluteX = layout.editorAbsoluteX;
            this.editorAbsoluteY = layout.editorAbsoluteY;
            this.orientation = layout.orientation;
            this.leftMargin = layout.leftMargin;
            this.rightMargin = layout.rightMargin;
            this.topMargin = layout.topMargin;
            this.bottomMargin = layout.bottomMargin;
            this.endMargin = layout.endMargin;
            this.startMargin = layout.startMargin;
            this.baselineMargin = layout.baselineMargin;
            this.goneLeftMargin = layout.goneLeftMargin;
            this.goneTopMargin = layout.goneTopMargin;
            this.goneRightMargin = layout.goneRightMargin;
            this.goneBottomMargin = layout.goneBottomMargin;
            this.goneEndMargin = layout.goneEndMargin;
            this.goneStartMargin = layout.goneStartMargin;
            this.goneBaselineMargin = layout.goneBaselineMargin;
            this.verticalWeight = layout.verticalWeight;
            this.horizontalWeight = layout.horizontalWeight;
            this.horizontalChainStyle = layout.horizontalChainStyle;
            this.verticalChainStyle = layout.verticalChainStyle;
            this.widthDefault = layout.widthDefault;
            this.heightDefault = layout.heightDefault;
            this.widthMax = layout.widthMax;
            this.heightMax = layout.heightMax;
            this.widthMin = layout.widthMin;
            this.heightMin = layout.heightMin;
            this.widthPercent = layout.widthPercent;
            this.heightPercent = layout.heightPercent;
            this.mBarrierDirection = layout.mBarrierDirection;
            this.mBarrierMargin = layout.mBarrierMargin;
            this.mHelperType = layout.mHelperType;
            this.mConstraintTag = layout.mConstraintTag;
            int[] iArr = layout.mReferenceIds;
            if (iArr == null || layout.mReferenceIdString != null) {
                this.mReferenceIds = null;
            } else {
                this.mReferenceIds = Arrays.copyOf(iArr, iArr.length);
            }
            this.mReferenceIdString = layout.mReferenceIdString;
            this.constrainedWidth = layout.constrainedWidth;
            this.constrainedHeight = layout.constrainedHeight;
            this.mBarrierAllowsGoneWidgets = layout.mBarrierAllowsGoneWidgets;
            this.mWrapBehavior = layout.mWrapBehavior;
        }

        public void dump(MotionScene motionScene, StringBuilder sb) throws IllegalAccessException, IllegalArgumentException {
            Field[] declaredFields = getClass().getDeclaredFields();
            sb.append("\n");
            for (Field field : declaredFields) {
                String name = field.getName();
                if (!Modifier.isStatic(field.getModifiers())) {
                    try {
                        Object obj = field.get(this);
                        Class<?> type = field.getType();
                        if (type == Integer.TYPE) {
                            Integer num = (Integer) obj;
                            if (num.intValue() != -1) {
                                Object objLookUpConstraintName = motionScene.lookUpConstraintName(num.intValue());
                                sb.append("    ");
                                sb.append(name);
                                sb.append(" = \"");
                                sb.append(objLookUpConstraintName == null ? num : objLookUpConstraintName);
                                sb.append("\"\n");
                            }
                        } else if (type == Float.TYPE) {
                            Float f2 = (Float) obj;
                            if (f2.floatValue() != -1.0f) {
                                sb.append("    ");
                                sb.append(name);
                                sb.append(" = \"");
                                sb.append(f2);
                                sb.append("\"\n");
                            }
                        }
                    } catch (IllegalAccessException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }

    public static class Motion {
        private static final int ANIMATE_CIRCLE_ANGLE_TO = 6;
        private static final int ANIMATE_RELATIVE_TO = 5;
        private static final int INTERPOLATOR_REFERENCE_ID = -2;
        private static final int INTERPOLATOR_UNDEFINED = -3;
        private static final int MOTION_DRAW_PATH = 4;
        private static final int MOTION_STAGGER = 7;
        private static final int PATH_MOTION_ARC = 2;
        private static final int QUANTIZE_MOTION_INTERPOLATOR = 10;
        private static final int QUANTIZE_MOTION_PHASE = 9;
        private static final int QUANTIZE_MOTION_STEPS = 8;
        private static final int SPLINE_STRING = -1;
        private static final int TRANSITION_EASING = 3;
        private static final int TRANSITION_PATH_ROTATE = 1;
        private static SparseIntArray mapToConstant;
        public boolean mApply = false;
        public int mAnimateRelativeTo = -1;
        public int mAnimateCircleAngleTo = 0;
        public String mTransitionEasing = null;
        public int mPathMotionArc = -1;
        public int mDrawPath = 0;
        public float mMotionStagger = Float.NaN;
        public int mPolarRelativeTo = -1;
        public float mPathRotate = Float.NaN;
        public float mQuantizeMotionPhase = Float.NaN;
        public int mQuantizeMotionSteps = -1;
        public String mQuantizeInterpolatorString = null;
        public int mQuantizeInterpolatorType = -3;
        public int mQuantizeInterpolatorID = -1;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mapToConstant = sparseIntArray;
            sparseIntArray.append(R.styleable.Motion_motionPathRotate, 1);
            mapToConstant.append(R.styleable.Motion_pathMotionArc, 2);
            mapToConstant.append(R.styleable.Motion_transitionEasing, 3);
            mapToConstant.append(R.styleable.Motion_drawPath, 4);
            mapToConstant.append(R.styleable.Motion_animateRelativeTo, 5);
            mapToConstant.append(R.styleable.Motion_animateCircleAngleTo, 6);
            mapToConstant.append(R.styleable.Motion_motionStagger, 7);
            mapToConstant.append(R.styleable.Motion_quantizeMotionSteps, 8);
            mapToConstant.append(R.styleable.Motion_quantizeMotionPhase, 9);
            mapToConstant.append(R.styleable.Motion_quantizeMotionInterpolator, 10);
        }

        void a(Context context, AttributeSet attributeSet) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Motion);
            this.mApply = true;
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                switch (mapToConstant.get(index)) {
                    case 1:
                        this.mPathRotate = typedArrayObtainStyledAttributes.getFloat(index, this.mPathRotate);
                        break;
                    case 2:
                        this.mPathMotionArc = typedArrayObtainStyledAttributes.getInt(index, this.mPathMotionArc);
                        break;
                    case 3:
                        if (typedArrayObtainStyledAttributes.peekValue(index).type == 3) {
                            this.mTransitionEasing = typedArrayObtainStyledAttributes.getString(index);
                            break;
                        } else {
                            this.mTransitionEasing = Easing.NAMED_EASING[typedArrayObtainStyledAttributes.getInteger(index, 0)];
                            break;
                        }
                    case 4:
                        this.mDrawPath = typedArrayObtainStyledAttributes.getInt(index, 0);
                        break;
                    case 5:
                        this.mAnimateRelativeTo = ConstraintSet.lookupID(typedArrayObtainStyledAttributes, index, this.mAnimateRelativeTo);
                        break;
                    case 6:
                        this.mAnimateCircleAngleTo = typedArrayObtainStyledAttributes.getInteger(index, this.mAnimateCircleAngleTo);
                        break;
                    case 7:
                        this.mMotionStagger = typedArrayObtainStyledAttributes.getFloat(index, this.mMotionStagger);
                        break;
                    case 8:
                        this.mQuantizeMotionSteps = typedArrayObtainStyledAttributes.getInteger(index, this.mQuantizeMotionSteps);
                        break;
                    case 9:
                        this.mQuantizeMotionPhase = typedArrayObtainStyledAttributes.getFloat(index, this.mQuantizeMotionPhase);
                        break;
                    case 10:
                        int i3 = typedArrayObtainStyledAttributes.peekValue(index).type;
                        if (i3 == 1) {
                            int resourceId = typedArrayObtainStyledAttributes.getResourceId(index, -1);
                            this.mQuantizeInterpolatorID = resourceId;
                            if (resourceId != -1) {
                                this.mQuantizeInterpolatorType = -2;
                                break;
                            } else {
                                break;
                            }
                        } else if (i3 == 3) {
                            String string = typedArrayObtainStyledAttributes.getString(index);
                            this.mQuantizeInterpolatorString = string;
                            if (string.indexOf("/") > 0) {
                                this.mQuantizeInterpolatorID = typedArrayObtainStyledAttributes.getResourceId(index, -1);
                                this.mQuantizeInterpolatorType = -2;
                                break;
                            } else {
                                this.mQuantizeInterpolatorType = -1;
                                break;
                            }
                        } else {
                            this.mQuantizeInterpolatorType = typedArrayObtainStyledAttributes.getInteger(index, this.mQuantizeInterpolatorID);
                            break;
                        }
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }

        public void copyFrom(Motion motion) {
            this.mApply = motion.mApply;
            this.mAnimateRelativeTo = motion.mAnimateRelativeTo;
            this.mTransitionEasing = motion.mTransitionEasing;
            this.mPathMotionArc = motion.mPathMotionArc;
            this.mDrawPath = motion.mDrawPath;
            this.mPathRotate = motion.mPathRotate;
            this.mMotionStagger = motion.mMotionStagger;
            this.mPolarRelativeTo = motion.mPolarRelativeTo;
        }
    }

    public static class PropertySet {
        public boolean mApply = false;
        public int visibility = 0;
        public int mVisibilityMode = 0;
        public float alpha = 1.0f;
        public float mProgress = Float.NaN;

        void a(Context context, AttributeSet attributeSet) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PropertySet);
            this.mApply = true;
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                if (index == R.styleable.PropertySet_android_alpha) {
                    this.alpha = typedArrayObtainStyledAttributes.getFloat(index, this.alpha);
                } else if (index == R.styleable.PropertySet_android_visibility) {
                    this.visibility = typedArrayObtainStyledAttributes.getInt(index, this.visibility);
                    this.visibility = ConstraintSet.VISIBILITY_FLAGS[this.visibility];
                } else if (index == R.styleable.PropertySet_visibilityMode) {
                    this.mVisibilityMode = typedArrayObtainStyledAttributes.getInt(index, this.mVisibilityMode);
                } else if (index == R.styleable.PropertySet_motionProgress) {
                    this.mProgress = typedArrayObtainStyledAttributes.getFloat(index, this.mProgress);
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }

        public void copyFrom(PropertySet propertySet) {
            this.mApply = propertySet.mApply;
            this.visibility = propertySet.visibility;
            this.alpha = propertySet.alpha;
            this.mProgress = propertySet.mProgress;
            this.mVisibilityMode = propertySet.mVisibilityMode;
        }
    }

    public static class Transform {
        private static final int ELEVATION = 11;
        private static final int ROTATION = 1;
        private static final int ROTATION_X = 2;
        private static final int ROTATION_Y = 3;
        private static final int SCALE_X = 4;
        private static final int SCALE_Y = 5;
        private static final int TRANSFORM_PIVOT_TARGET = 12;
        private static final int TRANSFORM_PIVOT_X = 6;
        private static final int TRANSFORM_PIVOT_Y = 7;
        private static final int TRANSLATION_X = 8;
        private static final int TRANSLATION_Y = 9;
        private static final int TRANSLATION_Z = 10;
        private static SparseIntArray mapToConstant;
        public boolean mApply = false;
        public float rotation = 0.0f;
        public float rotationX = 0.0f;
        public float rotationY = 0.0f;
        public float scaleX = 1.0f;
        public float scaleY = 1.0f;
        public float transformPivotX = Float.NaN;
        public float transformPivotY = Float.NaN;
        public int transformPivotTarget = -1;
        public float translationX = 0.0f;
        public float translationY = 0.0f;
        public float translationZ = 0.0f;
        public boolean applyElevation = false;
        public float elevation = 0.0f;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mapToConstant = sparseIntArray;
            sparseIntArray.append(R.styleable.Transform_android_rotation, 1);
            mapToConstant.append(R.styleable.Transform_android_rotationX, 2);
            mapToConstant.append(R.styleable.Transform_android_rotationY, 3);
            mapToConstant.append(R.styleable.Transform_android_scaleX, 4);
            mapToConstant.append(R.styleable.Transform_android_scaleY, 5);
            mapToConstant.append(R.styleable.Transform_android_transformPivotX, 6);
            mapToConstant.append(R.styleable.Transform_android_transformPivotY, 7);
            mapToConstant.append(R.styleable.Transform_android_translationX, 8);
            mapToConstant.append(R.styleable.Transform_android_translationY, 9);
            mapToConstant.append(R.styleable.Transform_android_translationZ, 10);
            mapToConstant.append(R.styleable.Transform_android_elevation, 11);
            mapToConstant.append(R.styleable.Transform_transformPivotTarget, 12);
        }

        void a(Context context, AttributeSet attributeSet) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Transform);
            this.mApply = true;
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                switch (mapToConstant.get(index)) {
                    case 1:
                        this.rotation = typedArrayObtainStyledAttributes.getFloat(index, this.rotation);
                        break;
                    case 2:
                        this.rotationX = typedArrayObtainStyledAttributes.getFloat(index, this.rotationX);
                        break;
                    case 3:
                        this.rotationY = typedArrayObtainStyledAttributes.getFloat(index, this.rotationY);
                        break;
                    case 4:
                        this.scaleX = typedArrayObtainStyledAttributes.getFloat(index, this.scaleX);
                        break;
                    case 5:
                        this.scaleY = typedArrayObtainStyledAttributes.getFloat(index, this.scaleY);
                        break;
                    case 6:
                        this.transformPivotX = typedArrayObtainStyledAttributes.getDimension(index, this.transformPivotX);
                        break;
                    case 7:
                        this.transformPivotY = typedArrayObtainStyledAttributes.getDimension(index, this.transformPivotY);
                        break;
                    case 8:
                        this.translationX = typedArrayObtainStyledAttributes.getDimension(index, this.translationX);
                        break;
                    case 9:
                        this.translationY = typedArrayObtainStyledAttributes.getDimension(index, this.translationY);
                        break;
                    case 10:
                        this.translationZ = typedArrayObtainStyledAttributes.getDimension(index, this.translationZ);
                        break;
                    case 11:
                        this.applyElevation = true;
                        this.elevation = typedArrayObtainStyledAttributes.getDimension(index, this.elevation);
                        break;
                    case 12:
                        this.transformPivotTarget = ConstraintSet.lookupID(typedArrayObtainStyledAttributes, index, this.transformPivotTarget);
                        break;
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }

        public void copyFrom(Transform transform) {
            this.mApply = transform.mApply;
            this.rotation = transform.rotation;
            this.rotationX = transform.rotationX;
            this.rotationY = transform.rotationY;
            this.scaleX = transform.scaleX;
            this.scaleY = transform.scaleY;
            this.transformPivotX = transform.transformPivotX;
            this.transformPivotY = transform.transformPivotY;
            this.transformPivotTarget = transform.transformPivotTarget;
            this.translationX = transform.translationX;
            this.translationY = transform.translationY;
            this.translationZ = transform.translationZ;
            this.applyElevation = transform.applyElevation;
            this.elevation = transform.elevation;
        }
    }

    class WriteJsonEngine {
        private static final String SPACE = "       ";

        /* renamed from: a, reason: collision with root package name */
        Writer f3296a;

        /* renamed from: b, reason: collision with root package name */
        ConstraintLayout f3297b;

        /* renamed from: c, reason: collision with root package name */
        Context f3298c;

        /* renamed from: d, reason: collision with root package name */
        int f3299d;

        /* renamed from: e, reason: collision with root package name */
        int f3300e = 0;

        /* renamed from: f, reason: collision with root package name */
        final String f3301f = "'left'";

        /* renamed from: g, reason: collision with root package name */
        final String f3302g = "'right'";

        /* renamed from: h, reason: collision with root package name */
        final String f3303h = "'baseline'";

        /* renamed from: i, reason: collision with root package name */
        final String f3304i = "'bottom'";

        /* renamed from: j, reason: collision with root package name */
        final String f3305j = "'top'";

        /* renamed from: k, reason: collision with root package name */
        final String f3306k = "'start'";

        /* renamed from: l, reason: collision with root package name */
        final String f3307l = "'end'";

        /* renamed from: m, reason: collision with root package name */
        HashMap f3308m = new HashMap();

        WriteJsonEngine(Writer writer, ConstraintLayout constraintLayout, int i2) {
            this.f3296a = writer;
            this.f3297b = constraintLayout;
            this.f3298c = constraintLayout.getContext();
            this.f3299d = i2;
        }

        private void writeDimension(String str, int i2, int i3, float f2, int i4, int i5, boolean z2) throws IOException {
            if (i2 != 0) {
                if (i2 == -2) {
                    this.f3296a.write(SPACE + str + ": 'wrap'\n");
                    return;
                }
                if (i2 == -1) {
                    this.f3296a.write(SPACE + str + ": 'parent'\n");
                    return;
                }
                this.f3296a.write(SPACE + str + ": " + i2 + ",\n");
                return;
            }
            if (i5 == -1 && i4 == -1) {
                if (i3 == 1) {
                    this.f3296a.write(SPACE + str + ": '???????????',\n");
                    return;
                }
                if (i3 != 2) {
                    return;
                }
                this.f3296a.write(SPACE + str + ": '" + f2 + "%',\n");
                return;
            }
            if (i3 == 0) {
                this.f3296a.write(SPACE + str + ": {'spread' ," + i4 + ", " + i5 + "}\n");
                return;
            }
            if (i3 == 1) {
                this.f3296a.write(SPACE + str + ": {'wrap' ," + i4 + ", " + i5 + "}\n");
                return;
            }
            if (i3 != 2) {
                return;
            }
            this.f3296a.write(SPACE + str + ": {'" + f2 + "'% ," + i4 + ", " + i5 + "}\n");
        }

        private void writeGuideline(int i2, int i3, int i4, float f2) {
        }

        String a(int i2) {
            if (this.f3308m.containsKey(Integer.valueOf(i2))) {
                return "'" + ((String) this.f3308m.get(Integer.valueOf(i2))) + "'";
            }
            if (i2 == 0) {
                return "'parent'";
            }
            String strB = b(i2);
            this.f3308m.put(Integer.valueOf(i2), strB);
            return "'" + strB + "'";
        }

        String b(int i2) {
            try {
                if (i2 != -1) {
                    return this.f3298c.getResources().getResourceEntryName(i2);
                }
                StringBuilder sb = new StringBuilder();
                sb.append("unknown");
                int i3 = this.f3300e + 1;
                this.f3300e = i3;
                sb.append(i3);
                return sb.toString();
            } catch (Exception unused) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("unknown");
                int i4 = this.f3300e + 1;
                this.f3300e = i4;
                sb2.append(i4);
                return sb2.toString();
            }
        }

        void c(int i2, float f2, int i3) throws IOException {
            if (i2 == -1) {
                return;
            }
            this.f3296a.write("       circle");
            this.f3296a.write(":[");
            this.f3296a.write(a(i2));
            this.f3296a.write(", " + f2);
            this.f3296a.write(i3 + "]");
        }

        void d(String str, int i2, String str2, int i3, int i4) throws IOException {
            if (i2 == -1) {
                return;
            }
            this.f3296a.write(SPACE + str);
            this.f3296a.write(":[");
            this.f3296a.write(a(i2));
            this.f3296a.write(" , ");
            this.f3296a.write(str2);
            if (i3 != 0) {
                this.f3296a.write(" , " + i3);
            }
            this.f3296a.write("],\n");
        }

        void e() throws IOException {
            this.f3296a.write("\n'ConstraintSet':{\n");
            for (Integer num : ConstraintSet.this.mConstraints.keySet()) {
                Constraint constraint = (Constraint) ConstraintSet.this.mConstraints.get(num);
                String strA = a(num.intValue());
                this.f3296a.write(strA + ":{\n");
                Layout layout = constraint.layout;
                writeDimension(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY, layout.mHeight, layout.heightDefault, layout.heightPercent, layout.heightMin, layout.heightMax, layout.constrainedHeight);
                writeDimension(ViewHierarchyConstants.DIMENSION_WIDTH_KEY, layout.mWidth, layout.widthDefault, layout.widthPercent, layout.widthMin, layout.widthMax, layout.constrainedWidth);
                d("'left'", layout.leftToLeft, "'left'", layout.leftMargin, layout.goneLeftMargin);
                d("'left'", layout.leftToRight, "'right'", layout.leftMargin, layout.goneLeftMargin);
                d("'right'", layout.rightToLeft, "'left'", layout.rightMargin, layout.goneRightMargin);
                d("'right'", layout.rightToRight, "'right'", layout.rightMargin, layout.goneRightMargin);
                d("'baseline'", layout.baselineToBaseline, "'baseline'", -1, layout.goneBaselineMargin);
                d("'baseline'", layout.baselineToTop, "'top'", -1, layout.goneBaselineMargin);
                d("'baseline'", layout.baselineToBottom, "'bottom'", -1, layout.goneBaselineMargin);
                d("'top'", layout.topToBottom, "'bottom'", layout.topMargin, layout.goneTopMargin);
                d("'top'", layout.topToTop, "'top'", layout.topMargin, layout.goneTopMargin);
                d("'bottom'", layout.bottomToBottom, "'bottom'", layout.bottomMargin, layout.goneBottomMargin);
                d("'bottom'", layout.bottomToTop, "'top'", layout.bottomMargin, layout.goneBottomMargin);
                d("'start'", layout.startToStart, "'start'", layout.startMargin, layout.goneStartMargin);
                d("'start'", layout.startToEnd, "'end'", layout.startMargin, layout.goneStartMargin);
                d("'end'", layout.endToStart, "'start'", layout.endMargin, layout.goneEndMargin);
                d("'end'", layout.endToEnd, "'end'", layout.endMargin, layout.goneEndMargin);
                g("'horizontalBias'", layout.horizontalBias, 0.5f);
                g("'verticalBias'", layout.verticalBias, 0.5f);
                c(layout.circleConstraint, layout.circleAngle, layout.circleRadius);
                writeGuideline(layout.orientation, layout.guideBegin, layout.guideEnd, layout.guidePercent);
                i("'dimensionRatio'", layout.dimensionRatio);
                h("'barrierMargin'", layout.mBarrierMargin);
                h("'type'", layout.mHelperType);
                i("'ReferenceId'", layout.mReferenceIdString);
                j("'mBarrierAllowsGoneWidgets'", layout.mBarrierAllowsGoneWidgets, true);
                h("'WrapBehavior'", layout.mWrapBehavior);
                f("'verticalWeight'", layout.verticalWeight);
                f("'horizontalWeight'", layout.horizontalWeight);
                h("'horizontalChainStyle'", layout.horizontalChainStyle);
                h("'verticalChainStyle'", layout.verticalChainStyle);
                h("'barrierDirection'", layout.mBarrierDirection);
                int[] iArr = layout.mReferenceIds;
                if (iArr != null) {
                    k("'ReferenceIds'", iArr);
                }
                this.f3296a.write("}\n");
            }
            this.f3296a.write("}\n");
        }

        void f(String str, float f2) throws IOException {
            if (f2 == -1.0f) {
                return;
            }
            this.f3296a.write(SPACE + str);
            this.f3296a.write(": " + f2);
            this.f3296a.write(",\n");
        }

        void g(String str, float f2, float f3) throws IOException {
            if (f2 == f3) {
                return;
            }
            this.f3296a.write(SPACE + str);
            this.f3296a.write(": " + f2);
            this.f3296a.write(",\n");
        }

        void h(String str, int i2) throws IOException {
            if (i2 == 0 || i2 == -1) {
                return;
            }
            this.f3296a.write(SPACE + str);
            this.f3296a.write(":");
            this.f3296a.write(", " + i2);
            this.f3296a.write("\n");
        }

        void i(String str, String str2) throws IOException {
            if (str2 == null) {
                return;
            }
            this.f3296a.write(SPACE + str);
            this.f3296a.write(":");
            this.f3296a.write(", " + str2);
            this.f3296a.write("\n");
        }

        void j(String str, boolean z2, boolean z3) throws IOException {
            if (z2 == z3) {
                return;
            }
            this.f3296a.write(SPACE + str);
            this.f3296a.write(": " + z2);
            this.f3296a.write(",\n");
        }

        void k(String str, int[] iArr) throws IOException {
            if (iArr == null) {
                return;
            }
            this.f3296a.write(SPACE + str);
            this.f3296a.write(": ");
            int i2 = 0;
            while (i2 < iArr.length) {
                Writer writer = this.f3296a;
                StringBuilder sb = new StringBuilder();
                sb.append(i2 == 0 ? "[" : ", ");
                sb.append(a(iArr[i2]));
                writer.write(sb.toString());
                i2++;
            }
            this.f3296a.write("],\n");
        }
    }

    class WriteXmlEngine {
        private static final String SPACE = "\n       ";

        /* renamed from: a, reason: collision with root package name */
        Writer f3310a;

        /* renamed from: b, reason: collision with root package name */
        ConstraintLayout f3311b;

        /* renamed from: c, reason: collision with root package name */
        Context f3312c;

        /* renamed from: d, reason: collision with root package name */
        int f3313d;

        /* renamed from: e, reason: collision with root package name */
        int f3314e = 0;

        /* renamed from: f, reason: collision with root package name */
        final String f3315f = "'left'";

        /* renamed from: g, reason: collision with root package name */
        final String f3316g = "'right'";

        /* renamed from: h, reason: collision with root package name */
        final String f3317h = "'baseline'";

        /* renamed from: i, reason: collision with root package name */
        final String f3318i = "'bottom'";

        /* renamed from: j, reason: collision with root package name */
        final String f3319j = "'top'";

        /* renamed from: k, reason: collision with root package name */
        final String f3320k = "'start'";

        /* renamed from: l, reason: collision with root package name */
        final String f3321l = "'end'";

        /* renamed from: m, reason: collision with root package name */
        HashMap f3322m = new HashMap();

        WriteXmlEngine(Writer writer, ConstraintLayout constraintLayout, int i2) {
            this.f3310a = writer;
            this.f3311b = constraintLayout;
            this.f3312c = constraintLayout.getContext();
            this.f3313d = i2;
        }

        private void writeBaseDimension(String str, int i2, int i3) throws IOException {
            if (i2 != i3) {
                if (i2 == -2) {
                    this.f3310a.write(SPACE + str + "=\"wrap_content\"");
                    return;
                }
                if (i2 == -1) {
                    this.f3310a.write(SPACE + str + "=\"match_parent\"");
                    return;
                }
                this.f3310a.write(SPACE + str + "=\"" + i2 + "dp\"");
            }
        }

        private void writeBoolen(String str, boolean z2, boolean z3) throws IOException {
            if (z2 != z3) {
                this.f3310a.write(SPACE + str + "=\"" + z2 + "dp\"");
            }
        }

        private void writeDimension(String str, int i2, int i3) throws IOException {
            if (i2 != i3) {
                this.f3310a.write(SPACE + str + "=\"" + i2 + "dp\"");
            }
        }

        private void writeEnum(String str, int i2, String[] strArr, int i3) throws IOException {
            if (i2 != i3) {
                this.f3310a.write(SPACE + str + "=\"" + strArr[i2] + "\"");
            }
        }

        String a(int i2) {
            if (this.f3322m.containsKey(Integer.valueOf(i2))) {
                return "@+id/" + ((String) this.f3322m.get(Integer.valueOf(i2))) + "";
            }
            if (i2 == 0) {
                return ConstraintSet.KEY_PERCENT_PARENT;
            }
            String strB = b(i2);
            this.f3322m.put(Integer.valueOf(i2), strB);
            return "@+id/" + strB + "";
        }

        String b(int i2) {
            try {
                if (i2 != -1) {
                    return this.f3312c.getResources().getResourceEntryName(i2);
                }
                StringBuilder sb = new StringBuilder();
                sb.append("unknown");
                int i3 = this.f3314e + 1;
                this.f3314e = i3;
                sb.append(i3);
                return sb.toString();
            } catch (Exception unused) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("unknown");
                int i4 = this.f3314e + 1;
                this.f3314e = i4;
                sb2.append(i4);
                return sb2.toString();
            }
        }

        void c() throws IOException {
            this.f3310a.write("\n<ConstraintSet>\n");
            for (Integer num : ConstraintSet.this.mConstraints.keySet()) {
                Constraint constraint = (Constraint) ConstraintSet.this.mConstraints.get(num);
                String strA = a(num.intValue());
                this.f3310a.write("  <Constraint");
                this.f3310a.write("\n       android:id=\"" + strA + "\"");
                Layout layout = constraint.layout;
                writeBaseDimension("android:layout_width", layout.mWidth, -5);
                writeBaseDimension("android:layout_height", layout.mHeight, -5);
                d("app:layout_constraintGuide_begin", (float) layout.guideBegin, -1.0f);
                d("app:layout_constraintGuide_end", layout.guideEnd, -1.0f);
                d("app:layout_constraintGuide_percent", layout.guidePercent, -1.0f);
                d("app:layout_constraintHorizontal_bias", layout.horizontalBias, 0.5f);
                d("app:layout_constraintVertical_bias", layout.verticalBias, 0.5f);
                f("app:layout_constraintDimensionRatio", layout.dimensionRatio, null);
                h("app:layout_constraintCircle", layout.circleConstraint);
                d("app:layout_constraintCircleRadius", layout.circleRadius, 0.0f);
                d("app:layout_constraintCircleAngle", layout.circleAngle, 0.0f);
                d("android:orientation", layout.orientation, -1.0f);
                d("app:layout_constraintVertical_weight", layout.verticalWeight, -1.0f);
                d("app:layout_constraintHorizontal_weight", layout.horizontalWeight, -1.0f);
                d("app:layout_constraintHorizontal_chainStyle", layout.horizontalChainStyle, 0.0f);
                d("app:layout_constraintVertical_chainStyle", layout.verticalChainStyle, 0.0f);
                d("app:barrierDirection", layout.mBarrierDirection, -1.0f);
                d("app:barrierMargin", layout.mBarrierMargin, 0.0f);
                writeDimension("app:layout_marginLeft", layout.leftMargin, 0);
                writeDimension("app:layout_goneMarginLeft", layout.goneLeftMargin, Integer.MIN_VALUE);
                writeDimension("app:layout_marginRight", layout.rightMargin, 0);
                writeDimension("app:layout_goneMarginRight", layout.goneRightMargin, Integer.MIN_VALUE);
                writeDimension("app:layout_marginStart", layout.startMargin, 0);
                writeDimension("app:layout_goneMarginStart", layout.goneStartMargin, Integer.MIN_VALUE);
                writeDimension("app:layout_marginEnd", layout.endMargin, 0);
                writeDimension("app:layout_goneMarginEnd", layout.goneEndMargin, Integer.MIN_VALUE);
                writeDimension("app:layout_marginTop", layout.topMargin, 0);
                writeDimension("app:layout_goneMarginTop", layout.goneTopMargin, Integer.MIN_VALUE);
                writeDimension("app:layout_marginBottom", layout.bottomMargin, 0);
                writeDimension("app:layout_goneMarginBottom", layout.goneBottomMargin, Integer.MIN_VALUE);
                writeDimension("app:goneBaselineMargin", layout.goneBaselineMargin, Integer.MIN_VALUE);
                writeDimension("app:baselineMargin", layout.baselineMargin, 0);
                writeBoolen("app:layout_constrainedWidth", layout.constrainedWidth, false);
                writeBoolen("app:layout_constrainedHeight", layout.constrainedHeight, false);
                writeBoolen("app:barrierAllowsGoneWidgets", layout.mBarrierAllowsGoneWidgets, true);
                d("app:layout_wrapBehaviorInParent", layout.mWrapBehavior, 0.0f);
                h("app:baselineToBaseline", layout.baselineToBaseline);
                h("app:baselineToBottom", layout.baselineToBottom);
                h("app:baselineToTop", layout.baselineToTop);
                h("app:layout_constraintBottom_toBottomOf", layout.bottomToBottom);
                h("app:layout_constraintBottom_toTopOf", layout.bottomToTop);
                h("app:layout_constraintEnd_toEndOf", layout.endToEnd);
                h("app:layout_constraintEnd_toStartOf", layout.endToStart);
                h("app:layout_constraintLeft_toLeftOf", layout.leftToLeft);
                h("app:layout_constraintLeft_toRightOf", layout.leftToRight);
                h("app:layout_constraintRight_toLeftOf", layout.rightToLeft);
                h("app:layout_constraintRight_toRightOf", layout.rightToRight);
                h("app:layout_constraintStart_toEndOf", layout.startToEnd);
                h("app:layout_constraintStart_toStartOf", layout.startToStart);
                h("app:layout_constraintTop_toBottomOf", layout.topToBottom);
                h("app:layout_constraintTop_toTopOf", layout.topToTop);
                String[] strArr = {"spread", "wrap", "percent"};
                writeEnum("app:layout_constraintHeight_default", layout.heightDefault, strArr, 0);
                d("app:layout_constraintHeight_percent", layout.heightPercent, 1.0f);
                writeDimension("app:layout_constraintHeight_min", layout.heightMin, 0);
                writeDimension("app:layout_constraintHeight_max", layout.heightMax, 0);
                writeBoolen("android:layout_constrainedHeight", layout.constrainedHeight, false);
                writeEnum("app:layout_constraintWidth_default", layout.widthDefault, strArr, 0);
                d("app:layout_constraintWidth_percent", layout.widthPercent, 1.0f);
                writeDimension("app:layout_constraintWidth_min", layout.widthMin, 0);
                writeDimension("app:layout_constraintWidth_max", layout.widthMax, 0);
                writeBoolen("android:layout_constrainedWidth", layout.constrainedWidth, false);
                d("app:layout_constraintVertical_weight", layout.verticalWeight, -1.0f);
                d("app:layout_constraintHorizontal_weight", layout.horizontalWeight, -1.0f);
                e("app:layout_constraintHorizontal_chainStyle", layout.horizontalChainStyle);
                e("app:layout_constraintVertical_chainStyle", layout.verticalChainStyle);
                writeEnum("app:barrierDirection", layout.mBarrierDirection, new String[]{"left", TtmlNode.RIGHT, ViewHierarchyConstants.DIMENSION_TOP_KEY, "bottom", "start", TtmlNode.END}, -1);
                f("app:layout_constraintTag", layout.mConstraintTag, null);
                int[] iArr = layout.mReferenceIds;
                if (iArr != null) {
                    g("'ReferenceIds'", iArr);
                }
                this.f3310a.write(" />\n");
            }
            this.f3310a.write("</ConstraintSet>\n");
        }

        void d(String str, float f2, float f3) throws IOException {
            if (f2 == f3) {
                return;
            }
            this.f3310a.write(SPACE + str);
            this.f3310a.write("=\"" + f2 + "\"");
        }

        void e(String str, int i2) throws IOException {
            if (i2 == 0 || i2 == -1) {
                return;
            }
            this.f3310a.write(SPACE + str + "=\"" + i2 + "\"\n");
        }

        void f(String str, String str2, String str3) throws IOException {
            if (str2 == null || str2.equals(str3)) {
                return;
            }
            this.f3310a.write(SPACE + str);
            this.f3310a.write("=\"" + str2 + "\"");
        }

        void g(String str, int[] iArr) throws IOException {
            if (iArr == null) {
                return;
            }
            this.f3310a.write(SPACE + str);
            this.f3310a.write(":");
            int i2 = 0;
            while (i2 < iArr.length) {
                Writer writer = this.f3310a;
                StringBuilder sb = new StringBuilder();
                sb.append(i2 == 0 ? "[" : ", ");
                sb.append(a(iArr[i2]));
                writer.write(sb.toString());
                i2++;
            }
            this.f3310a.write("],\n");
        }

        void h(String str, int i2) throws IOException {
            if (i2 == -1) {
                return;
            }
            this.f3310a.write(SPACE + str);
            this.f3310a.write("=\"" + a(i2) + "\"");
        }
    }

    static {
        mapToConstant.append(R.styleable.Constraint_layout_constraintLeft_toLeftOf, 25);
        mapToConstant.append(R.styleable.Constraint_layout_constraintLeft_toRightOf, 26);
        mapToConstant.append(R.styleable.Constraint_layout_constraintRight_toLeftOf, 29);
        mapToConstant.append(R.styleable.Constraint_layout_constraintRight_toRightOf, 30);
        mapToConstant.append(R.styleable.Constraint_layout_constraintTop_toTopOf, 36);
        mapToConstant.append(R.styleable.Constraint_layout_constraintTop_toBottomOf, 35);
        mapToConstant.append(R.styleable.Constraint_layout_constraintBottom_toTopOf, 4);
        mapToConstant.append(R.styleable.Constraint_layout_constraintBottom_toBottomOf, 3);
        mapToConstant.append(R.styleable.Constraint_layout_constraintBaseline_toBaselineOf, 1);
        mapToConstant.append(R.styleable.Constraint_layout_constraintBaseline_toTopOf, 91);
        mapToConstant.append(R.styleable.Constraint_layout_constraintBaseline_toBottomOf, 92);
        mapToConstant.append(R.styleable.Constraint_layout_editor_absoluteX, 6);
        mapToConstant.append(R.styleable.Constraint_layout_editor_absoluteY, 7);
        mapToConstant.append(R.styleable.Constraint_layout_constraintGuide_begin, 17);
        mapToConstant.append(R.styleable.Constraint_layout_constraintGuide_end, 18);
        mapToConstant.append(R.styleable.Constraint_layout_constraintGuide_percent, 19);
        mapToConstant.append(R.styleable.Constraint_guidelineUseRtl, 99);
        mapToConstant.append(R.styleable.Constraint_android_orientation, 27);
        mapToConstant.append(R.styleable.Constraint_layout_constraintStart_toEndOf, 32);
        mapToConstant.append(R.styleable.Constraint_layout_constraintStart_toStartOf, 33);
        mapToConstant.append(R.styleable.Constraint_layout_constraintEnd_toStartOf, 10);
        mapToConstant.append(R.styleable.Constraint_layout_constraintEnd_toEndOf, 9);
        mapToConstant.append(R.styleable.Constraint_layout_goneMarginLeft, 13);
        mapToConstant.append(R.styleable.Constraint_layout_goneMarginTop, 16);
        mapToConstant.append(R.styleable.Constraint_layout_goneMarginRight, 14);
        mapToConstant.append(R.styleable.Constraint_layout_goneMarginBottom, 11);
        mapToConstant.append(R.styleable.Constraint_layout_goneMarginStart, 15);
        mapToConstant.append(R.styleable.Constraint_layout_goneMarginEnd, 12);
        mapToConstant.append(R.styleable.Constraint_layout_constraintVertical_weight, 40);
        mapToConstant.append(R.styleable.Constraint_layout_constraintHorizontal_weight, 39);
        mapToConstant.append(R.styleable.Constraint_layout_constraintHorizontal_chainStyle, 41);
        mapToConstant.append(R.styleable.Constraint_layout_constraintVertical_chainStyle, 42);
        mapToConstant.append(R.styleable.Constraint_layout_constraintHorizontal_bias, 20);
        mapToConstant.append(R.styleable.Constraint_layout_constraintVertical_bias, 37);
        mapToConstant.append(R.styleable.Constraint_layout_constraintDimensionRatio, 5);
        mapToConstant.append(R.styleable.Constraint_layout_constraintLeft_creator, 87);
        mapToConstant.append(R.styleable.Constraint_layout_constraintTop_creator, 87);
        mapToConstant.append(R.styleable.Constraint_layout_constraintRight_creator, 87);
        mapToConstant.append(R.styleable.Constraint_layout_constraintBottom_creator, 87);
        mapToConstant.append(R.styleable.Constraint_layout_constraintBaseline_creator, 87);
        mapToConstant.append(R.styleable.Constraint_android_layout_marginLeft, 24);
        mapToConstant.append(R.styleable.Constraint_android_layout_marginRight, 28);
        mapToConstant.append(R.styleable.Constraint_android_layout_marginStart, 31);
        mapToConstant.append(R.styleable.Constraint_android_layout_marginEnd, 8);
        mapToConstant.append(R.styleable.Constraint_android_layout_marginTop, 34);
        mapToConstant.append(R.styleable.Constraint_android_layout_marginBottom, 2);
        mapToConstant.append(R.styleable.Constraint_android_layout_width, 23);
        mapToConstant.append(R.styleable.Constraint_android_layout_height, 21);
        mapToConstant.append(R.styleable.Constraint_layout_constraintWidth, 95);
        mapToConstant.append(R.styleable.Constraint_layout_constraintHeight, 96);
        mapToConstant.append(R.styleable.Constraint_android_visibility, 22);
        mapToConstant.append(R.styleable.Constraint_android_alpha, 43);
        mapToConstant.append(R.styleable.Constraint_android_elevation, 44);
        mapToConstant.append(R.styleable.Constraint_android_rotationX, 45);
        mapToConstant.append(R.styleable.Constraint_android_rotationY, 46);
        mapToConstant.append(R.styleable.Constraint_android_rotation, 60);
        mapToConstant.append(R.styleable.Constraint_android_scaleX, 47);
        mapToConstant.append(R.styleable.Constraint_android_scaleY, 48);
        mapToConstant.append(R.styleable.Constraint_android_transformPivotX, 49);
        mapToConstant.append(R.styleable.Constraint_android_transformPivotY, 50);
        mapToConstant.append(R.styleable.Constraint_android_translationX, 51);
        mapToConstant.append(R.styleable.Constraint_android_translationY, 52);
        mapToConstant.append(R.styleable.Constraint_android_translationZ, 53);
        mapToConstant.append(R.styleable.Constraint_layout_constraintWidth_default, 54);
        mapToConstant.append(R.styleable.Constraint_layout_constraintHeight_default, 55);
        mapToConstant.append(R.styleable.Constraint_layout_constraintWidth_max, 56);
        mapToConstant.append(R.styleable.Constraint_layout_constraintHeight_max, 57);
        mapToConstant.append(R.styleable.Constraint_layout_constraintWidth_min, 58);
        mapToConstant.append(R.styleable.Constraint_layout_constraintHeight_min, 59);
        mapToConstant.append(R.styleable.Constraint_layout_constraintCircle, 61);
        mapToConstant.append(R.styleable.Constraint_layout_constraintCircleRadius, 62);
        mapToConstant.append(R.styleable.Constraint_layout_constraintCircleAngle, 63);
        mapToConstant.append(R.styleable.Constraint_animateRelativeTo, 64);
        mapToConstant.append(R.styleable.Constraint_transitionEasing, 65);
        mapToConstant.append(R.styleable.Constraint_drawPath, 66);
        mapToConstant.append(R.styleable.Constraint_transitionPathRotate, 67);
        mapToConstant.append(R.styleable.Constraint_motionStagger, 79);
        mapToConstant.append(R.styleable.Constraint_android_id, 38);
        mapToConstant.append(R.styleable.Constraint_motionProgress, 68);
        mapToConstant.append(R.styleable.Constraint_layout_constraintWidth_percent, 69);
        mapToConstant.append(R.styleable.Constraint_layout_constraintHeight_percent, 70);
        mapToConstant.append(R.styleable.Constraint_layout_wrapBehaviorInParent, 97);
        mapToConstant.append(R.styleable.Constraint_chainUseRtl, 71);
        mapToConstant.append(R.styleable.Constraint_barrierDirection, 72);
        mapToConstant.append(R.styleable.Constraint_barrierMargin, 73);
        mapToConstant.append(R.styleable.Constraint_constraint_referenced_ids, 74);
        mapToConstant.append(R.styleable.Constraint_barrierAllowsGoneWidgets, 75);
        mapToConstant.append(R.styleable.Constraint_pathMotionArc, 76);
        mapToConstant.append(R.styleable.Constraint_layout_constraintTag, 77);
        mapToConstant.append(R.styleable.Constraint_visibilityMode, 78);
        mapToConstant.append(R.styleable.Constraint_layout_constrainedWidth, 80);
        mapToConstant.append(R.styleable.Constraint_layout_constrainedHeight, 81);
        mapToConstant.append(R.styleable.Constraint_polarRelativeTo, 82);
        mapToConstant.append(R.styleable.Constraint_transformPivotTarget, 83);
        mapToConstant.append(R.styleable.Constraint_quantizeMotionSteps, 84);
        mapToConstant.append(R.styleable.Constraint_quantizeMotionPhase, 85);
        mapToConstant.append(R.styleable.Constraint_quantizeMotionInterpolator, 86);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_editor_absoluteY, 6);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_editor_absoluteY, 7);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_android_orientation, 27);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_goneMarginLeft, 13);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_goneMarginTop, 16);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_goneMarginRight, 14);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_goneMarginBottom, 11);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_goneMarginStart, 15);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_goneMarginEnd, 12);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintVertical_weight, 40);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintHorizontal_weight, 39);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintHorizontal_chainStyle, 41);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintVertical_chainStyle, 42);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintHorizontal_bias, 20);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintVertical_bias, 37);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintDimensionRatio, 5);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintLeft_creator, 87);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintTop_creator, 87);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintRight_creator, 87);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintBottom_creator, 87);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintBaseline_creator, 87);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_android_layout_marginLeft, 24);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_android_layout_marginRight, 28);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_android_layout_marginStart, 31);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_android_layout_marginEnd, 8);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_android_layout_marginTop, 34);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_android_layout_marginBottom, 2);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_android_layout_width, 23);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_android_layout_height, 21);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintWidth, 95);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintHeight, 96);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_android_visibility, 22);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_android_alpha, 43);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_android_elevation, 44);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_android_rotationX, 45);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_android_rotationY, 46);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_android_rotation, 60);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_android_scaleX, 47);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_android_scaleY, 48);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_android_transformPivotX, 49);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_android_transformPivotY, 50);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_android_translationX, 51);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_android_translationY, 52);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_android_translationZ, 53);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintWidth_default, 54);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintHeight_default, 55);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintWidth_max, 56);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintHeight_max, 57);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintWidth_min, 58);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintHeight_min, 59);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintCircleRadius, 62);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintCircleAngle, 63);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_animateRelativeTo, 64);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_transitionEasing, 65);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_drawPath, 66);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_transitionPathRotate, 67);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_motionStagger, 79);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_android_id, 38);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_motionTarget, 98);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_motionProgress, 68);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintWidth_percent, 69);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintHeight_percent, 70);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_chainUseRtl, 71);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_barrierDirection, 72);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_barrierMargin, 73);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_constraint_referenced_ids, 74);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_barrierAllowsGoneWidgets, 75);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_pathMotionArc, 76);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintTag, 77);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_visibilityMode, 78);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constrainedWidth, 80);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constrainedHeight, 81);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_polarRelativeTo, 82);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_transformPivotTarget, 83);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_quantizeMotionSteps, 84);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_quantizeMotionPhase, 85);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_quantizeMotionInterpolator, 86);
        overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_wrapBehaviorInParent, 97);
    }

    private void addAttributes(ConstraintAttribute.AttributeType attributeType, String... strArr) {
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if (this.mSavedAttributes.containsKey(strArr[i2])) {
                ConstraintAttribute constraintAttribute = this.mSavedAttributes.get(strArr[i2]);
                if (constraintAttribute != null && constraintAttribute.getType() != attributeType) {
                    throw new IllegalArgumentException("ConstraintAttribute is already a " + constraintAttribute.getType().name());
                }
            } else {
                this.mSavedAttributes.put(strArr[i2], new ConstraintAttribute(strArr[i2], attributeType));
            }
        }
    }

    public static Constraint buildDelta(Context context, XmlPullParser xmlPullParser) {
        AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlPullParser);
        Constraint constraint = new Constraint();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSetAsAttributeSet, R.styleable.ConstraintOverride);
        populateOverride(context, constraint, typedArrayObtainStyledAttributes);
        typedArrayObtainStyledAttributes.recycle();
        return constraint;
    }

    private int[] convertReferenceString(View view, String str) throws IllegalAccessException, IllegalArgumentException {
        int iIntValue;
        Object designInformation;
        String[] strArrSplit = str.split(",");
        Context context = view.getContext();
        int[] iArr = new int[strArrSplit.length];
        int i2 = 0;
        int i3 = 0;
        while (i2 < strArrSplit.length) {
            String strTrim = strArrSplit[i2].trim();
            try {
                iIntValue = R.id.class.getField(strTrim).getInt(null);
            } catch (Exception unused) {
                iIntValue = 0;
            }
            if (iIntValue == 0) {
                iIntValue = context.getResources().getIdentifier(strTrim, "id", context.getPackageName());
            }
            if (iIntValue == 0 && view.isInEditMode() && (view.getParent() instanceof ConstraintLayout) && (designInformation = ((ConstraintLayout) view.getParent()).getDesignInformation(0, strTrim)) != null && (designInformation instanceof Integer)) {
                iIntValue = ((Integer) designInformation).intValue();
            }
            iArr[i3] = iIntValue;
            i2++;
            i3++;
        }
        return i3 != strArrSplit.length ? Arrays.copyOf(iArr, i3) : iArr;
    }

    private Constraint fillFromAttributeList(Context context, AttributeSet attributeSet, boolean z2) {
        Constraint constraint = new Constraint();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, z2 ? R.styleable.ConstraintOverride : R.styleable.Constraint);
        populateConstraint(context, constraint, typedArrayObtainStyledAttributes, z2);
        typedArrayObtainStyledAttributes.recycle();
        return constraint;
    }

    private Constraint get(int i2) {
        if (!this.mConstraints.containsKey(Integer.valueOf(i2))) {
            this.mConstraints.put(Integer.valueOf(i2), new Constraint());
        }
        return this.mConstraints.get(Integer.valueOf(i2));
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0041  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static void i(java.lang.Object r4, android.content.res.TypedArray r5, int r6, int r7) {
        /*
            if (r4 != 0) goto L3
            return
        L3:
            android.util.TypedValue r0 = r5.peekValue(r6)
            int r0 = r0.type
            r1 = 3
            if (r0 == r1) goto L71
            r1 = 5
            r2 = 0
            if (r0 == r1) goto L2a
            int r5 = r5.getInt(r6, r2)
            r6 = -4
            r0 = -2
            if (r5 == r6) goto L26
            r6 = -3
            if (r5 == r6) goto L20
            if (r5 == r0) goto L22
            r6 = -1
            if (r5 == r6) goto L22
        L20:
            r5 = r2
            goto L2f
        L22:
            r3 = r2
            r2 = r5
            r5 = r3
            goto L2f
        L26:
            r2 = 1
            r5 = r2
            r2 = r0
            goto L2f
        L2a:
            int r5 = r5.getDimensionPixelSize(r6, r2)
            goto L22
        L2f:
            boolean r6 = r4 instanceof androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
            if (r6 == 0) goto L41
            androidx.constraintlayout.widget.ConstraintLayout$LayoutParams r4 = (androidx.constraintlayout.widget.ConstraintLayout.LayoutParams) r4
            if (r7 != 0) goto L3c
            r4.width = r2
            r4.constrainedWidth = r5
            goto L70
        L3c:
            r4.height = r2
            r4.constrainedHeight = r5
            goto L70
        L41:
            boolean r6 = r4 instanceof androidx.constraintlayout.widget.ConstraintSet.Layout
            if (r6 == 0) goto L53
            androidx.constraintlayout.widget.ConstraintSet$Layout r4 = (androidx.constraintlayout.widget.ConstraintSet.Layout) r4
            if (r7 != 0) goto L4e
            r4.mWidth = r2
            r4.constrainedWidth = r5
            goto L70
        L4e:
            r4.mHeight = r2
            r4.constrainedHeight = r5
            goto L70
        L53:
            boolean r6 = r4 instanceof androidx.constraintlayout.widget.ConstraintSet.Constraint.Delta
            if (r6 == 0) goto L70
            androidx.constraintlayout.widget.ConstraintSet$Constraint$Delta r4 = (androidx.constraintlayout.widget.ConstraintSet.Constraint.Delta) r4
            if (r7 != 0) goto L66
            r6 = 23
            r4.b(r6, r2)
            r6 = 80
            r4.d(r6, r5)
            goto L70
        L66:
            r6 = 21
            r4.b(r6, r2)
            r6 = 81
            r4.d(r6, r5)
        L70:
            return
        L71:
            java.lang.String r5 = r5.getString(r6)
            j(r4, r5, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.ConstraintSet.i(java.lang.Object, android.content.res.TypedArray, int, int):void");
    }

    static void j(Object obj, String str, int i2) throws NumberFormatException {
        if (str == null) {
            return;
        }
        int iIndexOf = str.indexOf(61);
        int length = str.length();
        if (iIndexOf <= 0 || iIndexOf >= length - 1) {
            return;
        }
        String strSubstring = str.substring(0, iIndexOf);
        String strSubstring2 = str.substring(iIndexOf + 1);
        if (strSubstring2.length() > 0) {
            String strTrim = strSubstring.trim();
            String strTrim2 = strSubstring2.trim();
            if (KEY_RATIO.equalsIgnoreCase(strTrim)) {
                if (obj instanceof ConstraintLayout.LayoutParams) {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) obj;
                    if (i2 == 0) {
                        ((ViewGroup.MarginLayoutParams) layoutParams).width = 0;
                    } else {
                        ((ViewGroup.MarginLayoutParams) layoutParams).height = 0;
                    }
                    k(layoutParams, strTrim2);
                    return;
                }
                if (obj instanceof Layout) {
                    ((Layout) obj).dimensionRatio = strTrim2;
                    return;
                } else {
                    if (obj instanceof Constraint.Delta) {
                        ((Constraint.Delta) obj).c(5, strTrim2);
                        return;
                    }
                    return;
                }
            }
            try {
                if (KEY_WEIGHT.equalsIgnoreCase(strTrim)) {
                    float f2 = Float.parseFloat(strTrim2);
                    if (obj instanceof ConstraintLayout.LayoutParams) {
                        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) obj;
                        if (i2 == 0) {
                            ((ViewGroup.MarginLayoutParams) layoutParams2).width = 0;
                            layoutParams2.horizontalWeight = f2;
                        } else {
                            ((ViewGroup.MarginLayoutParams) layoutParams2).height = 0;
                            layoutParams2.verticalWeight = f2;
                        }
                    } else if (obj instanceof Layout) {
                        Layout layout = (Layout) obj;
                        if (i2 == 0) {
                            layout.mWidth = 0;
                            layout.horizontalWeight = f2;
                        } else {
                            layout.mHeight = 0;
                            layout.verticalWeight = f2;
                        }
                    } else if (obj instanceof Constraint.Delta) {
                        Constraint.Delta delta = (Constraint.Delta) obj;
                        if (i2 == 0) {
                            delta.b(23, 0);
                            delta.a(39, f2);
                        } else {
                            delta.b(21, 0);
                            delta.a(40, f2);
                        }
                    }
                } else {
                    if (!KEY_PERCENT_PARENT.equalsIgnoreCase(strTrim)) {
                        return;
                    }
                    float fMax = Math.max(0.0f, Math.min(1.0f, Float.parseFloat(strTrim2)));
                    if (obj instanceof ConstraintLayout.LayoutParams) {
                        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) obj;
                        if (i2 == 0) {
                            ((ViewGroup.MarginLayoutParams) layoutParams3).width = 0;
                            layoutParams3.matchConstraintPercentWidth = fMax;
                            layoutParams3.matchConstraintDefaultWidth = 2;
                        } else {
                            ((ViewGroup.MarginLayoutParams) layoutParams3).height = 0;
                            layoutParams3.matchConstraintPercentHeight = fMax;
                            layoutParams3.matchConstraintDefaultHeight = 2;
                        }
                    } else if (obj instanceof Layout) {
                        Layout layout2 = (Layout) obj;
                        if (i2 == 0) {
                            layout2.mWidth = 0;
                            layout2.widthPercent = fMax;
                            layout2.widthDefault = 2;
                        } else {
                            layout2.mHeight = 0;
                            layout2.heightPercent = fMax;
                            layout2.heightDefault = 2;
                        }
                    } else if (obj instanceof Constraint.Delta) {
                        Constraint.Delta delta2 = (Constraint.Delta) obj;
                        if (i2 == 0) {
                            delta2.b(23, 0);
                            delta2.b(54, 2);
                        } else {
                            delta2.b(21, 0);
                            delta2.b(55, 2);
                        }
                    }
                }
            } catch (NumberFormatException unused) {
            }
        }
    }

    static void k(ConstraintLayout.LayoutParams layoutParams, String str) {
        float fAbs = Float.NaN;
        int i2 = -1;
        if (str != null) {
            int length = str.length();
            int iIndexOf = str.indexOf(44);
            int i3 = 0;
            if (iIndexOf > 0 && iIndexOf < length - 1) {
                String strSubstring = str.substring(0, iIndexOf);
                if (strSubstring.equalsIgnoreCase(ExifInterface.LONGITUDE_WEST)) {
                    i2 = 0;
                } else if (strSubstring.equalsIgnoreCase("H")) {
                    i2 = 1;
                }
                i3 = iIndexOf + 1;
            }
            int iIndexOf2 = str.indexOf(58);
            try {
                if (iIndexOf2 < 0 || iIndexOf2 >= length - 1) {
                    String strSubstring2 = str.substring(i3);
                    if (strSubstring2.length() > 0) {
                        fAbs = Float.parseFloat(strSubstring2);
                    }
                } else {
                    String strSubstring3 = str.substring(i3, iIndexOf2);
                    String strSubstring4 = str.substring(iIndexOf2 + 1);
                    if (strSubstring3.length() > 0 && strSubstring4.length() > 0) {
                        float f2 = Float.parseFloat(strSubstring3);
                        float f3 = Float.parseFloat(strSubstring4);
                        if (f2 > 0.0f && f3 > 0.0f) {
                            fAbs = i2 == 1 ? Math.abs(f3 / f2) : Math.abs(f2 / f3);
                        }
                    }
                }
            } catch (NumberFormatException unused) {
            }
        }
        layoutParams.dimensionRatio = str;
        layoutParams.f3238c = fAbs;
        layoutParams.f3239d = i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int lookupID(TypedArray typedArray, int i2, int i3) {
        int resourceId = typedArray.getResourceId(i2, i3);
        return resourceId == -1 ? typedArray.getInt(i2, -1) : resourceId;
    }

    private void populateConstraint(Context context, Constraint constraint, TypedArray typedArray, boolean z2) {
        if (z2) {
            populateOverride(context, constraint, typedArray);
            return;
        }
        int indexCount = typedArray.getIndexCount();
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = typedArray.getIndex(i2);
            if (index != R.styleable.Constraint_android_id && R.styleable.Constraint_android_layout_marginStart != index && R.styleable.Constraint_android_layout_marginEnd != index) {
                constraint.motion.mApply = true;
                constraint.layout.mApply = true;
                constraint.propertySet.mApply = true;
                constraint.transform.mApply = true;
            }
            switch (mapToConstant.get(index)) {
                case 1:
                    Layout layout = constraint.layout;
                    layout.baselineToBaseline = lookupID(typedArray, index, layout.baselineToBaseline);
                    break;
                case 2:
                    Layout layout2 = constraint.layout;
                    layout2.bottomMargin = typedArray.getDimensionPixelSize(index, layout2.bottomMargin);
                    break;
                case 3:
                    Layout layout3 = constraint.layout;
                    layout3.bottomToBottom = lookupID(typedArray, index, layout3.bottomToBottom);
                    break;
                case 4:
                    Layout layout4 = constraint.layout;
                    layout4.bottomToTop = lookupID(typedArray, index, layout4.bottomToTop);
                    break;
                case 5:
                    constraint.layout.dimensionRatio = typedArray.getString(index);
                    break;
                case 6:
                    Layout layout5 = constraint.layout;
                    layout5.editorAbsoluteX = typedArray.getDimensionPixelOffset(index, layout5.editorAbsoluteX);
                    break;
                case 7:
                    Layout layout6 = constraint.layout;
                    layout6.editorAbsoluteY = typedArray.getDimensionPixelOffset(index, layout6.editorAbsoluteY);
                    break;
                case 8:
                    Layout layout7 = constraint.layout;
                    layout7.endMargin = typedArray.getDimensionPixelSize(index, layout7.endMargin);
                    break;
                case 9:
                    Layout layout8 = constraint.layout;
                    layout8.endToEnd = lookupID(typedArray, index, layout8.endToEnd);
                    break;
                case 10:
                    Layout layout9 = constraint.layout;
                    layout9.endToStart = lookupID(typedArray, index, layout9.endToStart);
                    break;
                case 11:
                    Layout layout10 = constraint.layout;
                    layout10.goneBottomMargin = typedArray.getDimensionPixelSize(index, layout10.goneBottomMargin);
                    break;
                case 12:
                    Layout layout11 = constraint.layout;
                    layout11.goneEndMargin = typedArray.getDimensionPixelSize(index, layout11.goneEndMargin);
                    break;
                case 13:
                    Layout layout12 = constraint.layout;
                    layout12.goneLeftMargin = typedArray.getDimensionPixelSize(index, layout12.goneLeftMargin);
                    break;
                case 14:
                    Layout layout13 = constraint.layout;
                    layout13.goneRightMargin = typedArray.getDimensionPixelSize(index, layout13.goneRightMargin);
                    break;
                case 15:
                    Layout layout14 = constraint.layout;
                    layout14.goneStartMargin = typedArray.getDimensionPixelSize(index, layout14.goneStartMargin);
                    break;
                case 16:
                    Layout layout15 = constraint.layout;
                    layout15.goneTopMargin = typedArray.getDimensionPixelSize(index, layout15.goneTopMargin);
                    break;
                case 17:
                    Layout layout16 = constraint.layout;
                    layout16.guideBegin = typedArray.getDimensionPixelOffset(index, layout16.guideBegin);
                    break;
                case 18:
                    Layout layout17 = constraint.layout;
                    layout17.guideEnd = typedArray.getDimensionPixelOffset(index, layout17.guideEnd);
                    break;
                case 19:
                    Layout layout18 = constraint.layout;
                    layout18.guidePercent = typedArray.getFloat(index, layout18.guidePercent);
                    break;
                case 20:
                    Layout layout19 = constraint.layout;
                    layout19.horizontalBias = typedArray.getFloat(index, layout19.horizontalBias);
                    break;
                case 21:
                    Layout layout20 = constraint.layout;
                    layout20.mHeight = typedArray.getLayoutDimension(index, layout20.mHeight);
                    break;
                case 22:
                    PropertySet propertySet = constraint.propertySet;
                    propertySet.visibility = typedArray.getInt(index, propertySet.visibility);
                    PropertySet propertySet2 = constraint.propertySet;
                    propertySet2.visibility = VISIBILITY_FLAGS[propertySet2.visibility];
                    break;
                case 23:
                    Layout layout21 = constraint.layout;
                    layout21.mWidth = typedArray.getLayoutDimension(index, layout21.mWidth);
                    break;
                case 24:
                    Layout layout22 = constraint.layout;
                    layout22.leftMargin = typedArray.getDimensionPixelSize(index, layout22.leftMargin);
                    break;
                case 25:
                    Layout layout23 = constraint.layout;
                    layout23.leftToLeft = lookupID(typedArray, index, layout23.leftToLeft);
                    break;
                case 26:
                    Layout layout24 = constraint.layout;
                    layout24.leftToRight = lookupID(typedArray, index, layout24.leftToRight);
                    break;
                case 27:
                    Layout layout25 = constraint.layout;
                    layout25.orientation = typedArray.getInt(index, layout25.orientation);
                    break;
                case 28:
                    Layout layout26 = constraint.layout;
                    layout26.rightMargin = typedArray.getDimensionPixelSize(index, layout26.rightMargin);
                    break;
                case 29:
                    Layout layout27 = constraint.layout;
                    layout27.rightToLeft = lookupID(typedArray, index, layout27.rightToLeft);
                    break;
                case 30:
                    Layout layout28 = constraint.layout;
                    layout28.rightToRight = lookupID(typedArray, index, layout28.rightToRight);
                    break;
                case 31:
                    Layout layout29 = constraint.layout;
                    layout29.startMargin = typedArray.getDimensionPixelSize(index, layout29.startMargin);
                    break;
                case 32:
                    Layout layout30 = constraint.layout;
                    layout30.startToEnd = lookupID(typedArray, index, layout30.startToEnd);
                    break;
                case 33:
                    Layout layout31 = constraint.layout;
                    layout31.startToStart = lookupID(typedArray, index, layout31.startToStart);
                    break;
                case 34:
                    Layout layout32 = constraint.layout;
                    layout32.topMargin = typedArray.getDimensionPixelSize(index, layout32.topMargin);
                    break;
                case 35:
                    Layout layout33 = constraint.layout;
                    layout33.topToBottom = lookupID(typedArray, index, layout33.topToBottom);
                    break;
                case 36:
                    Layout layout34 = constraint.layout;
                    layout34.topToTop = lookupID(typedArray, index, layout34.topToTop);
                    break;
                case 37:
                    Layout layout35 = constraint.layout;
                    layout35.verticalBias = typedArray.getFloat(index, layout35.verticalBias);
                    break;
                case 38:
                    constraint.f3281a = typedArray.getResourceId(index, constraint.f3281a);
                    break;
                case 39:
                    Layout layout36 = constraint.layout;
                    layout36.horizontalWeight = typedArray.getFloat(index, layout36.horizontalWeight);
                    break;
                case 40:
                    Layout layout37 = constraint.layout;
                    layout37.verticalWeight = typedArray.getFloat(index, layout37.verticalWeight);
                    break;
                case 41:
                    Layout layout38 = constraint.layout;
                    layout38.horizontalChainStyle = typedArray.getInt(index, layout38.horizontalChainStyle);
                    break;
                case 42:
                    Layout layout39 = constraint.layout;
                    layout39.verticalChainStyle = typedArray.getInt(index, layout39.verticalChainStyle);
                    break;
                case 43:
                    PropertySet propertySet3 = constraint.propertySet;
                    propertySet3.alpha = typedArray.getFloat(index, propertySet3.alpha);
                    break;
                case 44:
                    Transform transform = constraint.transform;
                    transform.applyElevation = true;
                    transform.elevation = typedArray.getDimension(index, transform.elevation);
                    break;
                case 45:
                    Transform transform2 = constraint.transform;
                    transform2.rotationX = typedArray.getFloat(index, transform2.rotationX);
                    break;
                case 46:
                    Transform transform3 = constraint.transform;
                    transform3.rotationY = typedArray.getFloat(index, transform3.rotationY);
                    break;
                case 47:
                    Transform transform4 = constraint.transform;
                    transform4.scaleX = typedArray.getFloat(index, transform4.scaleX);
                    break;
                case 48:
                    Transform transform5 = constraint.transform;
                    transform5.scaleY = typedArray.getFloat(index, transform5.scaleY);
                    break;
                case 49:
                    Transform transform6 = constraint.transform;
                    transform6.transformPivotX = typedArray.getDimension(index, transform6.transformPivotX);
                    break;
                case 50:
                    Transform transform7 = constraint.transform;
                    transform7.transformPivotY = typedArray.getDimension(index, transform7.transformPivotY);
                    break;
                case 51:
                    Transform transform8 = constraint.transform;
                    transform8.translationX = typedArray.getDimension(index, transform8.translationX);
                    break;
                case 52:
                    Transform transform9 = constraint.transform;
                    transform9.translationY = typedArray.getDimension(index, transform9.translationY);
                    break;
                case 53:
                    Transform transform10 = constraint.transform;
                    transform10.translationZ = typedArray.getDimension(index, transform10.translationZ);
                    break;
                case 54:
                    Layout layout40 = constraint.layout;
                    layout40.widthDefault = typedArray.getInt(index, layout40.widthDefault);
                    break;
                case 55:
                    Layout layout41 = constraint.layout;
                    layout41.heightDefault = typedArray.getInt(index, layout41.heightDefault);
                    break;
                case 56:
                    Layout layout42 = constraint.layout;
                    layout42.widthMax = typedArray.getDimensionPixelSize(index, layout42.widthMax);
                    break;
                case 57:
                    Layout layout43 = constraint.layout;
                    layout43.heightMax = typedArray.getDimensionPixelSize(index, layout43.heightMax);
                    break;
                case 58:
                    Layout layout44 = constraint.layout;
                    layout44.widthMin = typedArray.getDimensionPixelSize(index, layout44.widthMin);
                    break;
                case 59:
                    Layout layout45 = constraint.layout;
                    layout45.heightMin = typedArray.getDimensionPixelSize(index, layout45.heightMin);
                    break;
                case 60:
                    Transform transform11 = constraint.transform;
                    transform11.rotation = typedArray.getFloat(index, transform11.rotation);
                    break;
                case 61:
                    Layout layout46 = constraint.layout;
                    layout46.circleConstraint = lookupID(typedArray, index, layout46.circleConstraint);
                    break;
                case 62:
                    Layout layout47 = constraint.layout;
                    layout47.circleRadius = typedArray.getDimensionPixelSize(index, layout47.circleRadius);
                    break;
                case 63:
                    Layout layout48 = constraint.layout;
                    layout48.circleAngle = typedArray.getFloat(index, layout48.circleAngle);
                    break;
                case 64:
                    Motion motion = constraint.motion;
                    motion.mAnimateRelativeTo = lookupID(typedArray, index, motion.mAnimateRelativeTo);
                    break;
                case 65:
                    if (typedArray.peekValue(index).type == 3) {
                        constraint.motion.mTransitionEasing = typedArray.getString(index);
                        break;
                    } else {
                        constraint.motion.mTransitionEasing = Easing.NAMED_EASING[typedArray.getInteger(index, 0)];
                        break;
                    }
                case 66:
                    constraint.motion.mDrawPath = typedArray.getInt(index, 0);
                    break;
                case 67:
                    Motion motion2 = constraint.motion;
                    motion2.mPathRotate = typedArray.getFloat(index, motion2.mPathRotate);
                    break;
                case 68:
                    PropertySet propertySet4 = constraint.propertySet;
                    propertySet4.mProgress = typedArray.getFloat(index, propertySet4.mProgress);
                    break;
                case 69:
                    constraint.layout.widthPercent = typedArray.getFloat(index, 1.0f);
                    break;
                case 70:
                    constraint.layout.heightPercent = typedArray.getFloat(index, 1.0f);
                    break;
                case 71:
                    Log.e(TAG, "CURRENTLY UNSUPPORTED");
                    break;
                case 72:
                    Layout layout49 = constraint.layout;
                    layout49.mBarrierDirection = typedArray.getInt(index, layout49.mBarrierDirection);
                    break;
                case 73:
                    Layout layout50 = constraint.layout;
                    layout50.mBarrierMargin = typedArray.getDimensionPixelSize(index, layout50.mBarrierMargin);
                    break;
                case 74:
                    constraint.layout.mReferenceIdString = typedArray.getString(index);
                    break;
                case 75:
                    Layout layout51 = constraint.layout;
                    layout51.mBarrierAllowsGoneWidgets = typedArray.getBoolean(index, layout51.mBarrierAllowsGoneWidgets);
                    break;
                case 76:
                    Motion motion3 = constraint.motion;
                    motion3.mPathMotionArc = typedArray.getInt(index, motion3.mPathMotionArc);
                    break;
                case 77:
                    constraint.layout.mConstraintTag = typedArray.getString(index);
                    break;
                case 78:
                    PropertySet propertySet5 = constraint.propertySet;
                    propertySet5.mVisibilityMode = typedArray.getInt(index, propertySet5.mVisibilityMode);
                    break;
                case 79:
                    Motion motion4 = constraint.motion;
                    motion4.mMotionStagger = typedArray.getFloat(index, motion4.mMotionStagger);
                    break;
                case 80:
                    Layout layout52 = constraint.layout;
                    layout52.constrainedWidth = typedArray.getBoolean(index, layout52.constrainedWidth);
                    break;
                case 81:
                    Layout layout53 = constraint.layout;
                    layout53.constrainedHeight = typedArray.getBoolean(index, layout53.constrainedHeight);
                    break;
                case 82:
                    Motion motion5 = constraint.motion;
                    motion5.mAnimateCircleAngleTo = typedArray.getInteger(index, motion5.mAnimateCircleAngleTo);
                    break;
                case 83:
                    Transform transform12 = constraint.transform;
                    transform12.transformPivotTarget = lookupID(typedArray, index, transform12.transformPivotTarget);
                    break;
                case 84:
                    Motion motion6 = constraint.motion;
                    motion6.mQuantizeMotionSteps = typedArray.getInteger(index, motion6.mQuantizeMotionSteps);
                    break;
                case 85:
                    Motion motion7 = constraint.motion;
                    motion7.mQuantizeMotionPhase = typedArray.getFloat(index, motion7.mQuantizeMotionPhase);
                    break;
                case 86:
                    int i3 = typedArray.peekValue(index).type;
                    if (i3 == 1) {
                        constraint.motion.mQuantizeInterpolatorID = typedArray.getResourceId(index, -1);
                        Motion motion8 = constraint.motion;
                        if (motion8.mQuantizeInterpolatorID != -1) {
                            motion8.mQuantizeInterpolatorType = -2;
                            break;
                        } else {
                            break;
                        }
                    } else if (i3 == 3) {
                        constraint.motion.mQuantizeInterpolatorString = typedArray.getString(index);
                        if (constraint.motion.mQuantizeInterpolatorString.indexOf("/") > 0) {
                            constraint.motion.mQuantizeInterpolatorID = typedArray.getResourceId(index, -1);
                            constraint.motion.mQuantizeInterpolatorType = -2;
                            break;
                        } else {
                            constraint.motion.mQuantizeInterpolatorType = -1;
                            break;
                        }
                    } else {
                        Motion motion9 = constraint.motion;
                        motion9.mQuantizeInterpolatorType = typedArray.getInteger(index, motion9.mQuantizeInterpolatorID);
                        break;
                    }
                case 87:
                    Log.w(TAG, "unused attribute 0x" + Integer.toHexString(index) + "   " + mapToConstant.get(index));
                    break;
                case 88:
                case 89:
                case 90:
                default:
                    Log.w(TAG, "Unknown attribute 0x" + Integer.toHexString(index) + "   " + mapToConstant.get(index));
                    break;
                case 91:
                    Layout layout54 = constraint.layout;
                    layout54.baselineToTop = lookupID(typedArray, index, layout54.baselineToTop);
                    break;
                case 92:
                    Layout layout55 = constraint.layout;
                    layout55.baselineToBottom = lookupID(typedArray, index, layout55.baselineToBottom);
                    break;
                case 93:
                    Layout layout56 = constraint.layout;
                    layout56.baselineMargin = typedArray.getDimensionPixelSize(index, layout56.baselineMargin);
                    break;
                case 94:
                    Layout layout57 = constraint.layout;
                    layout57.goneBaselineMargin = typedArray.getDimensionPixelSize(index, layout57.goneBaselineMargin);
                    break;
                case 95:
                    i(constraint.layout, typedArray, index, 0);
                    break;
                case 96:
                    i(constraint.layout, typedArray, index, 1);
                    break;
                case 97:
                    Layout layout58 = constraint.layout;
                    layout58.mWrapBehavior = typedArray.getInt(index, layout58.mWrapBehavior);
                    break;
            }
        }
        Layout layout59 = constraint.layout;
        if (layout59.mReferenceIdString != null) {
            layout59.mReferenceIds = null;
        }
    }

    private static void populateOverride(Context context, Constraint constraint, TypedArray typedArray) {
        int indexCount = typedArray.getIndexCount();
        Constraint.Delta delta = new Constraint.Delta();
        constraint.f3283c = delta;
        constraint.motion.mApply = false;
        constraint.layout.mApply = false;
        constraint.propertySet.mApply = false;
        constraint.transform.mApply = false;
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = typedArray.getIndex(i2);
            switch (overrideMapToConstant.get(index)) {
                case 2:
                    delta.b(2, typedArray.getDimensionPixelSize(index, constraint.layout.bottomMargin));
                    break;
                case 3:
                case 4:
                case 9:
                case 10:
                case 25:
                case 26:
                case 29:
                case 30:
                case 32:
                case 33:
                case 35:
                case 36:
                case 61:
                case 88:
                case 89:
                case 90:
                case 91:
                case 92:
                default:
                    Log.w(TAG, "Unknown attribute 0x" + Integer.toHexString(index) + "   " + mapToConstant.get(index));
                    break;
                case 5:
                    delta.c(5, typedArray.getString(index));
                    break;
                case 6:
                    delta.b(6, typedArray.getDimensionPixelOffset(index, constraint.layout.editorAbsoluteX));
                    break;
                case 7:
                    delta.b(7, typedArray.getDimensionPixelOffset(index, constraint.layout.editorAbsoluteY));
                    break;
                case 8:
                    delta.b(8, typedArray.getDimensionPixelSize(index, constraint.layout.endMargin));
                    break;
                case 11:
                    delta.b(11, typedArray.getDimensionPixelSize(index, constraint.layout.goneBottomMargin));
                    break;
                case 12:
                    delta.b(12, typedArray.getDimensionPixelSize(index, constraint.layout.goneEndMargin));
                    break;
                case 13:
                    delta.b(13, typedArray.getDimensionPixelSize(index, constraint.layout.goneLeftMargin));
                    break;
                case 14:
                    delta.b(14, typedArray.getDimensionPixelSize(index, constraint.layout.goneRightMargin));
                    break;
                case 15:
                    delta.b(15, typedArray.getDimensionPixelSize(index, constraint.layout.goneStartMargin));
                    break;
                case 16:
                    delta.b(16, typedArray.getDimensionPixelSize(index, constraint.layout.goneTopMargin));
                    break;
                case 17:
                    delta.b(17, typedArray.getDimensionPixelOffset(index, constraint.layout.guideBegin));
                    break;
                case 18:
                    delta.b(18, typedArray.getDimensionPixelOffset(index, constraint.layout.guideEnd));
                    break;
                case 19:
                    delta.a(19, typedArray.getFloat(index, constraint.layout.guidePercent));
                    break;
                case 20:
                    delta.a(20, typedArray.getFloat(index, constraint.layout.horizontalBias));
                    break;
                case 21:
                    delta.b(21, typedArray.getLayoutDimension(index, constraint.layout.mHeight));
                    break;
                case 22:
                    delta.b(22, VISIBILITY_FLAGS[typedArray.getInt(index, constraint.propertySet.visibility)]);
                    break;
                case 23:
                    delta.b(23, typedArray.getLayoutDimension(index, constraint.layout.mWidth));
                    break;
                case 24:
                    delta.b(24, typedArray.getDimensionPixelSize(index, constraint.layout.leftMargin));
                    break;
                case 27:
                    delta.b(27, typedArray.getInt(index, constraint.layout.orientation));
                    break;
                case 28:
                    delta.b(28, typedArray.getDimensionPixelSize(index, constraint.layout.rightMargin));
                    break;
                case 31:
                    delta.b(31, typedArray.getDimensionPixelSize(index, constraint.layout.startMargin));
                    break;
                case 34:
                    delta.b(34, typedArray.getDimensionPixelSize(index, constraint.layout.topMargin));
                    break;
                case 37:
                    delta.a(37, typedArray.getFloat(index, constraint.layout.verticalBias));
                    break;
                case 38:
                    int resourceId = typedArray.getResourceId(index, constraint.f3281a);
                    constraint.f3281a = resourceId;
                    delta.b(38, resourceId);
                    break;
                case 39:
                    delta.a(39, typedArray.getFloat(index, constraint.layout.horizontalWeight));
                    break;
                case 40:
                    delta.a(40, typedArray.getFloat(index, constraint.layout.verticalWeight));
                    break;
                case 41:
                    delta.b(41, typedArray.getInt(index, constraint.layout.horizontalChainStyle));
                    break;
                case 42:
                    delta.b(42, typedArray.getInt(index, constraint.layout.verticalChainStyle));
                    break;
                case 43:
                    delta.a(43, typedArray.getFloat(index, constraint.propertySet.alpha));
                    break;
                case 44:
                    delta.d(44, true);
                    delta.a(44, typedArray.getDimension(index, constraint.transform.elevation));
                    break;
                case 45:
                    delta.a(45, typedArray.getFloat(index, constraint.transform.rotationX));
                    break;
                case 46:
                    delta.a(46, typedArray.getFloat(index, constraint.transform.rotationY));
                    break;
                case 47:
                    delta.a(47, typedArray.getFloat(index, constraint.transform.scaleX));
                    break;
                case 48:
                    delta.a(48, typedArray.getFloat(index, constraint.transform.scaleY));
                    break;
                case 49:
                    delta.a(49, typedArray.getDimension(index, constraint.transform.transformPivotX));
                    break;
                case 50:
                    delta.a(50, typedArray.getDimension(index, constraint.transform.transformPivotY));
                    break;
                case 51:
                    delta.a(51, typedArray.getDimension(index, constraint.transform.translationX));
                    break;
                case 52:
                    delta.a(52, typedArray.getDimension(index, constraint.transform.translationY));
                    break;
                case 53:
                    delta.a(53, typedArray.getDimension(index, constraint.transform.translationZ));
                    break;
                case 54:
                    delta.b(54, typedArray.getInt(index, constraint.layout.widthDefault));
                    break;
                case 55:
                    delta.b(55, typedArray.getInt(index, constraint.layout.heightDefault));
                    break;
                case 56:
                    delta.b(56, typedArray.getDimensionPixelSize(index, constraint.layout.widthMax));
                    break;
                case 57:
                    delta.b(57, typedArray.getDimensionPixelSize(index, constraint.layout.heightMax));
                    break;
                case 58:
                    delta.b(58, typedArray.getDimensionPixelSize(index, constraint.layout.widthMin));
                    break;
                case 59:
                    delta.b(59, typedArray.getDimensionPixelSize(index, constraint.layout.heightMin));
                    break;
                case 60:
                    delta.a(60, typedArray.getFloat(index, constraint.transform.rotation));
                    break;
                case 62:
                    delta.b(62, typedArray.getDimensionPixelSize(index, constraint.layout.circleRadius));
                    break;
                case 63:
                    delta.a(63, typedArray.getFloat(index, constraint.layout.circleAngle));
                    break;
                case 64:
                    delta.b(64, lookupID(typedArray, index, constraint.motion.mAnimateRelativeTo));
                    break;
                case 65:
                    if (typedArray.peekValue(index).type == 3) {
                        delta.c(65, typedArray.getString(index));
                        break;
                    } else {
                        delta.c(65, Easing.NAMED_EASING[typedArray.getInteger(index, 0)]);
                        break;
                    }
                case 66:
                    delta.b(66, typedArray.getInt(index, 0));
                    break;
                case 67:
                    delta.a(67, typedArray.getFloat(index, constraint.motion.mPathRotate));
                    break;
                case 68:
                    delta.a(68, typedArray.getFloat(index, constraint.propertySet.mProgress));
                    break;
                case 69:
                    delta.a(69, typedArray.getFloat(index, 1.0f));
                    break;
                case 70:
                    delta.a(70, typedArray.getFloat(index, 1.0f));
                    break;
                case 71:
                    Log.e(TAG, "CURRENTLY UNSUPPORTED");
                    break;
                case 72:
                    delta.b(72, typedArray.getInt(index, constraint.layout.mBarrierDirection));
                    break;
                case 73:
                    delta.b(73, typedArray.getDimensionPixelSize(index, constraint.layout.mBarrierMargin));
                    break;
                case 74:
                    delta.c(74, typedArray.getString(index));
                    break;
                case 75:
                    delta.d(75, typedArray.getBoolean(index, constraint.layout.mBarrierAllowsGoneWidgets));
                    break;
                case 76:
                    delta.b(76, typedArray.getInt(index, constraint.motion.mPathMotionArc));
                    break;
                case 77:
                    delta.c(77, typedArray.getString(index));
                    break;
                case 78:
                    delta.b(78, typedArray.getInt(index, constraint.propertySet.mVisibilityMode));
                    break;
                case 79:
                    delta.a(79, typedArray.getFloat(index, constraint.motion.mMotionStagger));
                    break;
                case 80:
                    delta.d(80, typedArray.getBoolean(index, constraint.layout.constrainedWidth));
                    break;
                case 81:
                    delta.d(81, typedArray.getBoolean(index, constraint.layout.constrainedHeight));
                    break;
                case 82:
                    delta.b(82, typedArray.getInteger(index, constraint.motion.mAnimateCircleAngleTo));
                    break;
                case 83:
                    delta.b(83, lookupID(typedArray, index, constraint.transform.transformPivotTarget));
                    break;
                case 84:
                    delta.b(84, typedArray.getInteger(index, constraint.motion.mQuantizeMotionSteps));
                    break;
                case 85:
                    delta.a(85, typedArray.getFloat(index, constraint.motion.mQuantizeMotionPhase));
                    break;
                case 86:
                    int i3 = typedArray.peekValue(index).type;
                    if (i3 == 1) {
                        constraint.motion.mQuantizeInterpolatorID = typedArray.getResourceId(index, -1);
                        delta.b(89, constraint.motion.mQuantizeInterpolatorID);
                        Motion motion = constraint.motion;
                        if (motion.mQuantizeInterpolatorID != -1) {
                            motion.mQuantizeInterpolatorType = -2;
                            delta.b(88, -2);
                            break;
                        } else {
                            break;
                        }
                    } else if (i3 == 3) {
                        constraint.motion.mQuantizeInterpolatorString = typedArray.getString(index);
                        delta.c(90, constraint.motion.mQuantizeInterpolatorString);
                        if (constraint.motion.mQuantizeInterpolatorString.indexOf("/") > 0) {
                            constraint.motion.mQuantizeInterpolatorID = typedArray.getResourceId(index, -1);
                            delta.b(89, constraint.motion.mQuantizeInterpolatorID);
                            constraint.motion.mQuantizeInterpolatorType = -2;
                            delta.b(88, -2);
                            break;
                        } else {
                            constraint.motion.mQuantizeInterpolatorType = -1;
                            delta.b(88, -1);
                            break;
                        }
                    } else {
                        Motion motion2 = constraint.motion;
                        motion2.mQuantizeInterpolatorType = typedArray.getInteger(index, motion2.mQuantizeInterpolatorID);
                        delta.b(88, constraint.motion.mQuantizeInterpolatorType);
                        break;
                    }
                case 87:
                    Log.w(TAG, "unused attribute 0x" + Integer.toHexString(index) + "   " + mapToConstant.get(index));
                    break;
                case 93:
                    delta.b(93, typedArray.getDimensionPixelSize(index, constraint.layout.baselineMargin));
                    break;
                case 94:
                    delta.b(94, typedArray.getDimensionPixelSize(index, constraint.layout.goneBaselineMargin));
                    break;
                case 95:
                    i(delta, typedArray, index, 0);
                    break;
                case 96:
                    i(delta, typedArray, index, 1);
                    break;
                case 97:
                    delta.b(97, typedArray.getInt(index, constraint.layout.mWrapBehavior));
                    break;
                case 98:
                    if (MotionLayout.IS_IN_EDIT_MODE) {
                        int resourceId2 = typedArray.getResourceId(index, constraint.f3281a);
                        constraint.f3281a = resourceId2;
                        if (resourceId2 == -1) {
                            constraint.f3282b = typedArray.getString(index);
                            break;
                        } else {
                            break;
                        }
                    } else if (typedArray.peekValue(index).type == 3) {
                        constraint.f3282b = typedArray.getString(index);
                        break;
                    } else {
                        constraint.f3281a = typedArray.getResourceId(index, constraint.f3281a);
                        break;
                    }
                case 99:
                    delta.d(99, typedArray.getBoolean(index, constraint.layout.guidelineUseRtl));
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setDeltaValue(Constraint constraint, int i2, float f2) {
        if (i2 == 19) {
            constraint.layout.guidePercent = f2;
        }
        if (i2 == 20) {
            constraint.layout.horizontalBias = f2;
            return;
        }
        if (i2 == 37) {
            constraint.layout.verticalBias = f2;
            return;
        }
        if (i2 == 60) {
            constraint.transform.rotation = f2;
            return;
        }
        if (i2 == 63) {
            constraint.layout.circleAngle = f2;
            return;
        }
        if (i2 == 79) {
            constraint.motion.mMotionStagger = f2;
            return;
        }
        if (i2 == 85) {
            constraint.motion.mQuantizeMotionPhase = f2;
            return;
        }
        if (i2 != 87) {
            if (i2 == 39) {
                constraint.layout.horizontalWeight = f2;
                return;
            }
            if (i2 == 40) {
                constraint.layout.verticalWeight = f2;
                return;
            }
            switch (i2) {
                case 43:
                    constraint.propertySet.alpha = f2;
                    break;
                case 44:
                    Transform transform = constraint.transform;
                    transform.elevation = f2;
                    transform.applyElevation = true;
                    break;
                case 45:
                    constraint.transform.rotationX = f2;
                    break;
                case 46:
                    constraint.transform.rotationY = f2;
                    break;
                case 47:
                    constraint.transform.scaleX = f2;
                    break;
                case 48:
                    constraint.transform.scaleY = f2;
                    break;
                case 49:
                    constraint.transform.transformPivotX = f2;
                    break;
                case 50:
                    constraint.transform.transformPivotY = f2;
                    break;
                case 51:
                    constraint.transform.translationX = f2;
                    break;
                case 52:
                    constraint.transform.translationY = f2;
                    break;
                case 53:
                    constraint.transform.translationZ = f2;
                    break;
                default:
                    switch (i2) {
                        case 67:
                            constraint.motion.mPathRotate = f2;
                            break;
                        case 68:
                            constraint.propertySet.mProgress = f2;
                            break;
                        case 69:
                            constraint.layout.widthPercent = f2;
                            break;
                        case 70:
                            constraint.layout.heightPercent = f2;
                            break;
                        default:
                            Log.w(TAG, "Unknown attribute 0x");
                            break;
                    }
            }
        }
    }

    private String sideToString(int i2) {
        switch (i2) {
            case 1:
                return "left";
            case 2:
                return TtmlNode.RIGHT;
            case 3:
                return ViewHierarchyConstants.DIMENSION_TOP_KEY;
            case 4:
                return "bottom";
            case 5:
                return "baseline";
            case 6:
                return "start";
            case 7:
                return TtmlNode.END;
            default:
                return "undefined";
        }
    }

    private static String[] splitString(String str) {
        char[] charArray = str.toCharArray();
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        boolean z2 = false;
        for (int i3 = 0; i3 < charArray.length; i3++) {
            char c2 = charArray[i3];
            if (c2 == ',' && !z2) {
                arrayList.add(new String(charArray, i2, i3 - i2));
                i2 = i3 + 1;
            } else if (c2 == '\"') {
                z2 = !z2;
            }
        }
        arrayList.add(new String(charArray, i2, charArray.length - i2));
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public void addColorAttributes(String... strArr) {
        addAttributes(ConstraintAttribute.AttributeType.COLOR_TYPE, strArr);
    }

    public void addFloatAttributes(String... strArr) {
        addAttributes(ConstraintAttribute.AttributeType.FLOAT_TYPE, strArr);
    }

    public void addIntAttributes(String... strArr) {
        addAttributes(ConstraintAttribute.AttributeType.INT_TYPE, strArr);
    }

    public void addStringAttributes(String... strArr) {
        addAttributes(ConstraintAttribute.AttributeType.STRING_TYPE, strArr);
    }

    public void addToHorizontalChain(int i2, int i3, int i4) {
        connect(i2, 1, i3, i3 == 0 ? 1 : 2, 0);
        connect(i2, 2, i4, i4 == 0 ? 2 : 1, 0);
        if (i3 != 0) {
            connect(i3, 2, i2, 1, 0);
        }
        if (i4 != 0) {
            connect(i4, 1, i2, 2, 0);
        }
    }

    public void addToHorizontalChainRTL(int i2, int i3, int i4) {
        connect(i2, 6, i3, i3 == 0 ? 6 : 7, 0);
        connect(i2, 7, i4, i4 == 0 ? 7 : 6, 0);
        if (i3 != 0) {
            connect(i3, 7, i2, 6, 0);
        }
        if (i4 != 0) {
            connect(i4, 6, i2, 7, 0);
        }
    }

    public void addToVerticalChain(int i2, int i3, int i4) {
        connect(i2, 3, i3, i3 == 0 ? 3 : 4, 0);
        connect(i2, 4, i4, i4 == 0 ? 4 : 3, 0);
        if (i3 != 0) {
            connect(i3, 4, i2, 3, 0);
        }
        if (i4 != 0) {
            connect(i4, 3, i2, 4, 0);
        }
    }

    public void applyCustomAttributes(ConstraintLayout constraintLayout) {
        Constraint constraint;
        int childCount = constraintLayout.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = constraintLayout.getChildAt(i2);
            int id = childAt.getId();
            if (!this.mConstraints.containsKey(Integer.valueOf(id))) {
                Log.w(TAG, "id unknown " + Debug.getName(childAt));
            } else {
                if (this.mForceId && id == -1) {
                    throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
                }
                if (this.mConstraints.containsKey(Integer.valueOf(id)) && (constraint = this.mConstraints.get(Integer.valueOf(id))) != null) {
                    ConstraintAttribute.setAttributes(childAt, constraint.mCustomConstraints);
                }
            }
        }
    }

    public void applyDeltaFrom(ConstraintSet constraintSet) {
        for (Constraint constraint : constraintSet.mConstraints.values()) {
            if (constraint.f3283c != null) {
                if (constraint.f3282b != null) {
                    Iterator<Integer> it = this.mConstraints.keySet().iterator();
                    while (it.hasNext()) {
                        Constraint constraint2 = getConstraint(it.next().intValue());
                        String str = constraint2.layout.mConstraintTag;
                        if (str != null && constraint.f3282b.matches(str)) {
                            constraint.f3283c.e(constraint2);
                            constraint2.mCustomConstraints.putAll((HashMap) constraint.mCustomConstraints.clone());
                        }
                    }
                } else {
                    constraint.f3283c.e(getConstraint(constraint.f3281a));
                }
            }
        }
    }

    public void applyTo(ConstraintLayout constraintLayout) {
        h(constraintLayout, true);
        constraintLayout.setConstraintSet(null);
        constraintLayout.requestLayout();
    }

    public void applyToHelper(ConstraintHelper constraintHelper, ConstraintWidget constraintWidget, ConstraintLayout.LayoutParams layoutParams, SparseArray<ConstraintWidget> sparseArray) {
        Constraint constraint;
        int id = constraintHelper.getId();
        if (this.mConstraints.containsKey(Integer.valueOf(id)) && (constraint = this.mConstraints.get(Integer.valueOf(id))) != null && (constraintWidget instanceof HelperWidget)) {
            constraintHelper.loadParameters(constraint, (HelperWidget) constraintWidget, layoutParams, sparseArray);
        }
    }

    public void applyToLayoutParams(int i2, ConstraintLayout.LayoutParams layoutParams) {
        Constraint constraint;
        if (!this.mConstraints.containsKey(Integer.valueOf(i2)) || (constraint = this.mConstraints.get(Integer.valueOf(i2))) == null) {
            return;
        }
        constraint.applyTo(layoutParams);
    }

    public void applyToWithoutCustom(ConstraintLayout constraintLayout) {
        h(constraintLayout, false);
        constraintLayout.setConstraintSet(null);
    }

    public void center(int i2, int i3, int i4, int i5, int i6, int i7, int i8, float f2) {
        if (i5 < 0) {
            throw new IllegalArgumentException("margin must be > 0");
        }
        if (i8 < 0) {
            throw new IllegalArgumentException("margin must be > 0");
        }
        if (f2 <= 0.0f || f2 > 1.0f) {
            throw new IllegalArgumentException("bias must be between 0 and 1 inclusive");
        }
        if (i4 == 1 || i4 == 2) {
            connect(i2, 1, i3, i4, i5);
            connect(i2, 2, i6, i7, i8);
            Constraint constraint = this.mConstraints.get(Integer.valueOf(i2));
            if (constraint != null) {
                constraint.layout.horizontalBias = f2;
                return;
            }
            return;
        }
        if (i4 == 6 || i4 == 7) {
            connect(i2, 6, i3, i4, i5);
            connect(i2, 7, i6, i7, i8);
            Constraint constraint2 = this.mConstraints.get(Integer.valueOf(i2));
            if (constraint2 != null) {
                constraint2.layout.horizontalBias = f2;
                return;
            }
            return;
        }
        connect(i2, 3, i3, i4, i5);
        connect(i2, 4, i6, i7, i8);
        Constraint constraint3 = this.mConstraints.get(Integer.valueOf(i2));
        if (constraint3 != null) {
            constraint3.layout.verticalBias = f2;
        }
    }

    public void centerHorizontally(int i2, int i3, int i4, int i5, int i6, int i7, int i8, float f2) {
        connect(i2, 1, i3, i4, i5);
        connect(i2, 2, i6, i7, i8);
        Constraint constraint = this.mConstraints.get(Integer.valueOf(i2));
        if (constraint != null) {
            constraint.layout.horizontalBias = f2;
        }
    }

    public void centerHorizontallyRtl(int i2, int i3, int i4, int i5, int i6, int i7, int i8, float f2) {
        connect(i2, 6, i3, i4, i5);
        connect(i2, 7, i6, i7, i8);
        Constraint constraint = this.mConstraints.get(Integer.valueOf(i2));
        if (constraint != null) {
            constraint.layout.horizontalBias = f2;
        }
    }

    public void centerVertically(int i2, int i3, int i4, int i5, int i6, int i7, int i8, float f2) {
        connect(i2, 3, i3, i4, i5);
        connect(i2, 4, i6, i7, i8);
        Constraint constraint = this.mConstraints.get(Integer.valueOf(i2));
        if (constraint != null) {
            constraint.layout.verticalBias = f2;
        }
    }

    public void clear(int i2) {
        this.mConstraints.remove(Integer.valueOf(i2));
    }

    public void clone(Context context, int i2) {
        clone((ConstraintLayout) LayoutInflater.from(context).inflate(i2, (ViewGroup) null));
    }

    public void connect(int i2, int i3, int i4, int i5, int i6) {
        if (!this.mConstraints.containsKey(Integer.valueOf(i2))) {
            this.mConstraints.put(Integer.valueOf(i2), new Constraint());
        }
        Constraint constraint = this.mConstraints.get(Integer.valueOf(i2));
        if (constraint == null) {
            return;
        }
        switch (i3) {
            case 1:
                if (i5 == 1) {
                    Layout layout = constraint.layout;
                    layout.leftToLeft = i4;
                    layout.leftToRight = -1;
                } else {
                    if (i5 != 2) {
                        throw new IllegalArgumentException("Left to " + sideToString(i5) + " undefined");
                    }
                    Layout layout2 = constraint.layout;
                    layout2.leftToRight = i4;
                    layout2.leftToLeft = -1;
                }
                constraint.layout.leftMargin = i6;
                return;
            case 2:
                if (i5 == 1) {
                    Layout layout3 = constraint.layout;
                    layout3.rightToLeft = i4;
                    layout3.rightToRight = -1;
                } else {
                    if (i5 != 2) {
                        throw new IllegalArgumentException("right to " + sideToString(i5) + " undefined");
                    }
                    Layout layout4 = constraint.layout;
                    layout4.rightToRight = i4;
                    layout4.rightToLeft = -1;
                }
                constraint.layout.rightMargin = i6;
                return;
            case 3:
                if (i5 == 3) {
                    Layout layout5 = constraint.layout;
                    layout5.topToTop = i4;
                    layout5.topToBottom = -1;
                    layout5.baselineToBaseline = -1;
                    layout5.baselineToTop = -1;
                    layout5.baselineToBottom = -1;
                } else {
                    if (i5 != 4) {
                        throw new IllegalArgumentException("right to " + sideToString(i5) + " undefined");
                    }
                    Layout layout6 = constraint.layout;
                    layout6.topToBottom = i4;
                    layout6.topToTop = -1;
                    layout6.baselineToBaseline = -1;
                    layout6.baselineToTop = -1;
                    layout6.baselineToBottom = -1;
                }
                constraint.layout.topMargin = i6;
                return;
            case 4:
                if (i5 == 4) {
                    Layout layout7 = constraint.layout;
                    layout7.bottomToBottom = i4;
                    layout7.bottomToTop = -1;
                    layout7.baselineToBaseline = -1;
                    layout7.baselineToTop = -1;
                    layout7.baselineToBottom = -1;
                } else {
                    if (i5 != 3) {
                        throw new IllegalArgumentException("right to " + sideToString(i5) + " undefined");
                    }
                    Layout layout8 = constraint.layout;
                    layout8.bottomToTop = i4;
                    layout8.bottomToBottom = -1;
                    layout8.baselineToBaseline = -1;
                    layout8.baselineToTop = -1;
                    layout8.baselineToBottom = -1;
                }
                constraint.layout.bottomMargin = i6;
                return;
            case 5:
                if (i5 == 5) {
                    Layout layout9 = constraint.layout;
                    layout9.baselineToBaseline = i4;
                    layout9.bottomToBottom = -1;
                    layout9.bottomToTop = -1;
                    layout9.topToTop = -1;
                    layout9.topToBottom = -1;
                    return;
                }
                if (i5 == 3) {
                    Layout layout10 = constraint.layout;
                    layout10.baselineToTop = i4;
                    layout10.bottomToBottom = -1;
                    layout10.bottomToTop = -1;
                    layout10.topToTop = -1;
                    layout10.topToBottom = -1;
                    return;
                }
                if (i5 != 4) {
                    throw new IllegalArgumentException("right to " + sideToString(i5) + " undefined");
                }
                Layout layout11 = constraint.layout;
                layout11.baselineToBottom = i4;
                layout11.bottomToBottom = -1;
                layout11.bottomToTop = -1;
                layout11.topToTop = -1;
                layout11.topToBottom = -1;
                return;
            case 6:
                if (i5 == 6) {
                    Layout layout12 = constraint.layout;
                    layout12.startToStart = i4;
                    layout12.startToEnd = -1;
                } else {
                    if (i5 != 7) {
                        throw new IllegalArgumentException("right to " + sideToString(i5) + " undefined");
                    }
                    Layout layout13 = constraint.layout;
                    layout13.startToEnd = i4;
                    layout13.startToStart = -1;
                }
                constraint.layout.startMargin = i6;
                return;
            case 7:
                if (i5 == 7) {
                    Layout layout14 = constraint.layout;
                    layout14.endToEnd = i4;
                    layout14.endToStart = -1;
                } else {
                    if (i5 != 6) {
                        throw new IllegalArgumentException("right to " + sideToString(i5) + " undefined");
                    }
                    Layout layout15 = constraint.layout;
                    layout15.endToStart = i4;
                    layout15.endToEnd = -1;
                }
                constraint.layout.endMargin = i6;
                return;
            default:
                throw new IllegalArgumentException(sideToString(i3) + " to " + sideToString(i5) + " unknown");
        }
    }

    public void constrainCircle(int i2, int i3, int i4, float f2) {
        Layout layout = get(i2).layout;
        layout.circleConstraint = i3;
        layout.circleRadius = i4;
        layout.circleAngle = f2;
    }

    public void constrainDefaultHeight(int i2, int i3) {
        get(i2).layout.heightDefault = i3;
    }

    public void constrainDefaultWidth(int i2, int i3) {
        get(i2).layout.widthDefault = i3;
    }

    public void constrainHeight(int i2, int i3) {
        get(i2).layout.mHeight = i3;
    }

    public void constrainMaxHeight(int i2, int i3) {
        get(i2).layout.heightMax = i3;
    }

    public void constrainMaxWidth(int i2, int i3) {
        get(i2).layout.widthMax = i3;
    }

    public void constrainMinHeight(int i2, int i3) {
        get(i2).layout.heightMin = i3;
    }

    public void constrainMinWidth(int i2, int i3) {
        get(i2).layout.widthMin = i3;
    }

    public void constrainPercentHeight(int i2, float f2) {
        get(i2).layout.heightPercent = f2;
    }

    public void constrainPercentWidth(int i2, float f2) {
        get(i2).layout.widthPercent = f2;
    }

    public void constrainWidth(int i2, int i3) {
        get(i2).layout.mWidth = i3;
    }

    public void constrainedHeight(int i2, boolean z2) {
        get(i2).layout.constrainedHeight = z2;
    }

    public void constrainedWidth(int i2, boolean z2) {
        get(i2).layout.constrainedWidth = z2;
    }

    public void create(int i2, int i3) {
        Layout layout = get(i2).layout;
        layout.mIsGuideline = true;
        layout.orientation = i3;
    }

    public void createBarrier(int i2, int i3, int i4, int... iArr) {
        Layout layout = get(i2).layout;
        layout.mHelperType = 1;
        layout.mBarrierDirection = i3;
        layout.mBarrierMargin = i4;
        layout.mIsGuideline = false;
        layout.mReferenceIds = iArr;
    }

    public void createHorizontalChain(int i2, int i3, int i4, int i5, int[] iArr, float[] fArr, int i6) {
        createHorizontalChain(i2, i3, i4, i5, iArr, fArr, i6, 1, 2);
    }

    public void createHorizontalChainRtl(int i2, int i3, int i4, int i5, int[] iArr, float[] fArr, int i6) {
        createHorizontalChain(i2, i3, i4, i5, iArr, fArr, i6, 6, 7);
    }

    public void createVerticalChain(int i2, int i3, int i4, int i5, int[] iArr, float[] fArr, int i6) {
        if (iArr.length < 2) {
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        }
        if (fArr != null && fArr.length != iArr.length) {
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        }
        if (fArr != null) {
            get(iArr[0]).layout.verticalWeight = fArr[0];
        }
        get(iArr[0]).layout.verticalChainStyle = i6;
        connect(iArr[0], 3, i2, i3, 0);
        for (int i7 = 1; i7 < iArr.length; i7++) {
            int i8 = i7 - 1;
            connect(iArr[i7], 3, iArr[i8], 4, 0);
            connect(iArr[i8], 4, iArr[i7], 3, 0);
            if (fArr != null) {
                get(iArr[i7]).layout.verticalWeight = fArr[i7];
            }
        }
        connect(iArr[iArr.length - 1], 4, i4, i5, 0);
    }

    public void dump(MotionScene motionScene, int... iArr) throws IllegalAccessException, IllegalArgumentException {
        HashSet hashSet;
        Set<Integer> setKeySet = this.mConstraints.keySet();
        if (iArr.length != 0) {
            hashSet = new HashSet();
            for (int i2 : iArr) {
                hashSet.add(Integer.valueOf(i2));
            }
        } else {
            hashSet = new HashSet(setKeySet);
        }
        System.out.println(hashSet.size() + " constraints");
        StringBuilder sb = new StringBuilder();
        for (Integer num : (Integer[]) hashSet.toArray(new Integer[0])) {
            Constraint constraint = this.mConstraints.get(num);
            if (constraint != null) {
                sb.append("<Constraint id=");
                sb.append(num);
                sb.append(" \n");
                constraint.layout.dump(motionScene, sb);
                sb.append("/>\n");
            }
        }
        System.out.println(sb.toString());
    }

    public boolean getApplyElevation(int i2) {
        return get(i2).transform.applyElevation;
    }

    public Constraint getConstraint(int i2) {
        if (this.mConstraints.containsKey(Integer.valueOf(i2))) {
            return this.mConstraints.get(Integer.valueOf(i2));
        }
        return null;
    }

    public HashMap<String, ConstraintAttribute> getCustomAttributeSet() {
        return this.mSavedAttributes;
    }

    public int getHeight(int i2) {
        return get(i2).layout.mHeight;
    }

    public int[] getKnownIds() {
        Integer[] numArr = (Integer[]) this.mConstraints.keySet().toArray(new Integer[0]);
        int length = numArr.length;
        int[] iArr = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = numArr[i2].intValue();
        }
        return iArr;
    }

    public Constraint getParameters(int i2) {
        return get(i2);
    }

    public int[] getReferencedIds(int i2) {
        int[] iArr = get(i2).layout.mReferenceIds;
        return iArr == null ? new int[0] : Arrays.copyOf(iArr, iArr.length);
    }

    public int getVisibility(int i2) {
        return get(i2).propertySet.visibility;
    }

    public int getVisibilityMode(int i2) {
        return get(i2).propertySet.mVisibilityMode;
    }

    public int getWidth(int i2) {
        return get(i2).layout.mWidth;
    }

    void h(ConstraintLayout constraintLayout, boolean z2) {
        int childCount = constraintLayout.getChildCount();
        HashSet hashSet = new HashSet(this.mConstraints.keySet());
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = constraintLayout.getChildAt(i2);
            int id = childAt.getId();
            if (!this.mConstraints.containsKey(Integer.valueOf(id))) {
                Log.w(TAG, "id unknown " + Debug.getName(childAt));
            } else {
                if (this.mForceId && id == -1) {
                    throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
                }
                if (id != -1) {
                    if (this.mConstraints.containsKey(Integer.valueOf(id))) {
                        hashSet.remove(Integer.valueOf(id));
                        Constraint constraint = this.mConstraints.get(Integer.valueOf(id));
                        if (constraint != null) {
                            if (childAt instanceof Barrier) {
                                constraint.layout.mHelperType = 1;
                                Barrier barrier = (Barrier) childAt;
                                barrier.setId(id);
                                barrier.setType(constraint.layout.mBarrierDirection);
                                barrier.setMargin(constraint.layout.mBarrierMargin);
                                barrier.setAllowsGoneWidget(constraint.layout.mBarrierAllowsGoneWidgets);
                                Layout layout = constraint.layout;
                                int[] iArr = layout.mReferenceIds;
                                if (iArr != null) {
                                    barrier.setReferencedIds(iArr);
                                } else {
                                    String str = layout.mReferenceIdString;
                                    if (str != null) {
                                        layout.mReferenceIds = convertReferenceString(barrier, str);
                                        barrier.setReferencedIds(constraint.layout.mReferenceIds);
                                    }
                                }
                            }
                            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) childAt.getLayoutParams();
                            layoutParams.validate();
                            constraint.applyTo(layoutParams);
                            if (z2) {
                                ConstraintAttribute.setAttributes(childAt, constraint.mCustomConstraints);
                            }
                            childAt.setLayoutParams(layoutParams);
                            PropertySet propertySet = constraint.propertySet;
                            if (propertySet.mVisibilityMode == 0) {
                                childAt.setVisibility(propertySet.visibility);
                            }
                            childAt.setAlpha(constraint.propertySet.alpha);
                            childAt.setRotation(constraint.transform.rotation);
                            childAt.setRotationX(constraint.transform.rotationX);
                            childAt.setRotationY(constraint.transform.rotationY);
                            childAt.setScaleX(constraint.transform.scaleX);
                            childAt.setScaleY(constraint.transform.scaleY);
                            Transform transform = constraint.transform;
                            if (transform.transformPivotTarget != -1) {
                                if (((View) childAt.getParent()).findViewById(constraint.transform.transformPivotTarget) != null) {
                                    float top2 = (r4.getTop() + r4.getBottom()) / 2.0f;
                                    float left = (r4.getLeft() + r4.getRight()) / 2.0f;
                                    if (childAt.getRight() - childAt.getLeft() > 0 && childAt.getBottom() - childAt.getTop() > 0) {
                                        childAt.setPivotX(left - childAt.getLeft());
                                        childAt.setPivotY(top2 - childAt.getTop());
                                    }
                                }
                            } else {
                                if (!Float.isNaN(transform.transformPivotX)) {
                                    childAt.setPivotX(constraint.transform.transformPivotX);
                                }
                                if (!Float.isNaN(constraint.transform.transformPivotY)) {
                                    childAt.setPivotY(constraint.transform.transformPivotY);
                                }
                            }
                            childAt.setTranslationX(constraint.transform.translationX);
                            childAt.setTranslationY(constraint.transform.translationY);
                            childAt.setTranslationZ(constraint.transform.translationZ);
                            Transform transform2 = constraint.transform;
                            if (transform2.applyElevation) {
                                childAt.setElevation(transform2.elevation);
                            }
                        }
                    } else {
                        Log.v(TAG, "WARNING NO CONSTRAINTS for view " + id);
                    }
                }
            }
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            Constraint constraint2 = this.mConstraints.get(num);
            if (constraint2 != null) {
                if (constraint2.layout.mHelperType == 1) {
                    Barrier barrier2 = new Barrier(constraintLayout.getContext());
                    barrier2.setId(num.intValue());
                    Layout layout2 = constraint2.layout;
                    int[] iArr2 = layout2.mReferenceIds;
                    if (iArr2 != null) {
                        barrier2.setReferencedIds(iArr2);
                    } else {
                        String str2 = layout2.mReferenceIdString;
                        if (str2 != null) {
                            layout2.mReferenceIds = convertReferenceString(barrier2, str2);
                            barrier2.setReferencedIds(constraint2.layout.mReferenceIds);
                        }
                    }
                    barrier2.setType(constraint2.layout.mBarrierDirection);
                    barrier2.setMargin(constraint2.layout.mBarrierMargin);
                    ConstraintLayout.LayoutParams layoutParamsGenerateDefaultLayoutParams = constraintLayout.generateDefaultLayoutParams();
                    barrier2.validateParams();
                    constraint2.applyTo(layoutParamsGenerateDefaultLayoutParams);
                    constraintLayout.addView(barrier2, layoutParamsGenerateDefaultLayoutParams);
                }
                if (constraint2.layout.mIsGuideline) {
                    View guideline = new Guideline(constraintLayout.getContext());
                    guideline.setId(num.intValue());
                    ConstraintLayout.LayoutParams layoutParamsGenerateDefaultLayoutParams2 = constraintLayout.generateDefaultLayoutParams();
                    constraint2.applyTo(layoutParamsGenerateDefaultLayoutParams2);
                    constraintLayout.addView(guideline, layoutParamsGenerateDefaultLayoutParams2);
                }
            }
        }
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt2 = constraintLayout.getChildAt(i3);
            if (childAt2 instanceof ConstraintHelper) {
                ((ConstraintHelper) childAt2).c(constraintLayout);
            }
        }
    }

    public boolean isForceId() {
        return this.mForceId;
    }

    public void load(Context context, int i2) {
        XmlResourceParser xml = context.getResources().getXml(i2);
        try {
            for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                if (eventType == 0) {
                    xml.getName();
                } else if (eventType == 2) {
                    String name = xml.getName();
                    Constraint constraintFillFromAttributeList = fillFromAttributeList(context, Xml.asAttributeSet(xml), false);
                    if (name.equalsIgnoreCase("Guideline")) {
                        constraintFillFromAttributeList.layout.mIsGuideline = true;
                    }
                    this.mConstraints.put(Integer.valueOf(constraintFillFromAttributeList.f3281a), constraintFillFromAttributeList);
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (XmlPullParserException e3) {
            e3.printStackTrace();
        }
    }

    public void parseColorAttributes(Constraint constraint, String str) {
        String[] strArrSplit = str.split(",");
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            String[] strArrSplit2 = strArrSplit[i2].split(ContainerUtils.KEY_VALUE_DELIMITER);
            if (strArrSplit2.length != 2) {
                Log.w(TAG, " Unable to parse " + strArrSplit[i2]);
            } else {
                constraint.setColorValue(strArrSplit2[0], Color.parseColor(strArrSplit2[1]));
            }
        }
    }

    public void parseFloatAttributes(Constraint constraint, String str) {
        String[] strArrSplit = str.split(",");
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            String[] strArrSplit2 = strArrSplit[i2].split(ContainerUtils.KEY_VALUE_DELIMITER);
            if (strArrSplit2.length != 2) {
                Log.w(TAG, " Unable to parse " + strArrSplit[i2]);
            } else {
                constraint.setFloatValue(strArrSplit2[0], Float.parseFloat(strArrSplit2[1]));
            }
        }
    }

    public void parseIntAttributes(Constraint constraint, String str) {
        String[] strArrSplit = str.split(",");
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            String[] strArrSplit2 = strArrSplit[i2].split(ContainerUtils.KEY_VALUE_DELIMITER);
            if (strArrSplit2.length != 2) {
                Log.w(TAG, " Unable to parse " + strArrSplit[i2]);
            } else {
                constraint.setFloatValue(strArrSplit2[0], Integer.decode(strArrSplit2[1]).intValue());
            }
        }
    }

    public void parseStringAttributes(Constraint constraint, String str) {
        String[] strArrSplitString = splitString(str);
        for (int i2 = 0; i2 < strArrSplitString.length; i2++) {
            String[] strArrSplit = strArrSplitString[i2].split(ContainerUtils.KEY_VALUE_DELIMITER);
            Log.w(TAG, " Unable to parse " + strArrSplitString[i2]);
            constraint.setStringValue(strArrSplit[0], strArrSplit[1]);
        }
    }

    public void readFallback(ConstraintSet constraintSet) {
        for (Integer num : constraintSet.mConstraints.keySet()) {
            num.intValue();
            Constraint constraint = constraintSet.mConstraints.get(num);
            if (!this.mConstraints.containsKey(num)) {
                this.mConstraints.put(num, new Constraint());
            }
            Constraint constraint2 = this.mConstraints.get(num);
            if (constraint2 != null) {
                Layout layout = constraint2.layout;
                if (!layout.mApply) {
                    layout.copyFrom(constraint.layout);
                }
                PropertySet propertySet = constraint2.propertySet;
                if (!propertySet.mApply) {
                    propertySet.copyFrom(constraint.propertySet);
                }
                Transform transform = constraint2.transform;
                if (!transform.mApply) {
                    transform.copyFrom(constraint.transform);
                }
                Motion motion = constraint2.motion;
                if (!motion.mApply) {
                    motion.copyFrom(constraint.motion);
                }
                for (String str : constraint.mCustomConstraints.keySet()) {
                    if (!constraint2.mCustomConstraints.containsKey(str)) {
                        constraint2.mCustomConstraints.put(str, constraint.mCustomConstraints.get(str));
                    }
                }
            }
        }
    }

    public void removeAttribute(String str) {
        this.mSavedAttributes.remove(str);
    }

    public void removeFromHorizontalChain(int i2) {
        Constraint constraint;
        if (!this.mConstraints.containsKey(Integer.valueOf(i2)) || (constraint = this.mConstraints.get(Integer.valueOf(i2))) == null) {
            return;
        }
        Layout layout = constraint.layout;
        int i3 = layout.leftToRight;
        int i4 = layout.rightToLeft;
        if (i3 != -1 || i4 != -1) {
            if (i3 == -1 || i4 == -1) {
                int i5 = layout.rightToRight;
                if (i5 != -1) {
                    connect(i3, 2, i5, 2, 0);
                } else {
                    int i6 = layout.leftToLeft;
                    if (i6 != -1) {
                        connect(i4, 1, i6, 1, 0);
                    }
                }
            } else {
                connect(i3, 2, i4, 1, 0);
                connect(i4, 1, i3, 2, 0);
            }
            clear(i2, 1);
            clear(i2, 2);
            return;
        }
        int i7 = layout.startToEnd;
        int i8 = layout.endToStart;
        if (i7 != -1 || i8 != -1) {
            if (i7 != -1 && i8 != -1) {
                connect(i7, 7, i8, 6, 0);
                connect(i8, 6, i3, 7, 0);
            } else if (i8 != -1) {
                int i9 = layout.rightToRight;
                if (i9 != -1) {
                    connect(i3, 7, i9, 7, 0);
                } else {
                    int i10 = layout.leftToLeft;
                    if (i10 != -1) {
                        connect(i8, 6, i10, 6, 0);
                    }
                }
            }
        }
        clear(i2, 6);
        clear(i2, 7);
    }

    public void removeFromVerticalChain(int i2) {
        if (this.mConstraints.containsKey(Integer.valueOf(i2))) {
            Constraint constraint = this.mConstraints.get(Integer.valueOf(i2));
            if (constraint == null) {
                return;
            }
            Layout layout = constraint.layout;
            int i3 = layout.topToBottom;
            int i4 = layout.bottomToTop;
            if (i3 != -1 || i4 != -1) {
                if (i3 == -1 || i4 == -1) {
                    int i5 = layout.bottomToBottom;
                    if (i5 != -1) {
                        connect(i3, 4, i5, 4, 0);
                    } else {
                        int i6 = layout.topToTop;
                        if (i6 != -1) {
                            connect(i4, 3, i6, 3, 0);
                        }
                    }
                } else {
                    connect(i3, 4, i4, 3, 0);
                    connect(i4, 3, i3, 4, 0);
                }
            }
        }
        clear(i2, 3);
        clear(i2, 4);
    }

    public void setAlpha(int i2, float f2) {
        get(i2).propertySet.alpha = f2;
    }

    public void setApplyElevation(int i2, boolean z2) {
        get(i2).transform.applyElevation = z2;
    }

    public void setBarrierType(int i2, int i3) {
        get(i2).layout.mHelperType = i3;
    }

    public void setColorValue(int i2, String str, int i3) {
        get(i2).setColorValue(str, i3);
    }

    public void setDimensionRatio(int i2, String str) {
        get(i2).layout.dimensionRatio = str;
    }

    public void setEditorAbsoluteX(int i2, int i3) {
        get(i2).layout.editorAbsoluteX = i3;
    }

    public void setEditorAbsoluteY(int i2, int i3) {
        get(i2).layout.editorAbsoluteY = i3;
    }

    public void setElevation(int i2, float f2) {
        get(i2).transform.elevation = f2;
        get(i2).transform.applyElevation = true;
    }

    public void setFloatValue(int i2, String str, float f2) {
        get(i2).setFloatValue(str, f2);
    }

    public void setForceId(boolean z2) {
        this.mForceId = z2;
    }

    public void setGoneMargin(int i2, int i3, int i4) {
        Constraint constraint = get(i2);
        switch (i3) {
            case 1:
                constraint.layout.goneLeftMargin = i4;
                return;
            case 2:
                constraint.layout.goneRightMargin = i4;
                return;
            case 3:
                constraint.layout.goneTopMargin = i4;
                return;
            case 4:
                constraint.layout.goneBottomMargin = i4;
                return;
            case 5:
                constraint.layout.goneBaselineMargin = i4;
                return;
            case 6:
                constraint.layout.goneStartMargin = i4;
                return;
            case 7:
                constraint.layout.goneEndMargin = i4;
                return;
            default:
                throw new IllegalArgumentException("unknown constraint");
        }
    }

    public void setGuidelineBegin(int i2, int i3) {
        get(i2).layout.guideBegin = i3;
        get(i2).layout.guideEnd = -1;
        get(i2).layout.guidePercent = -1.0f;
    }

    public void setGuidelineEnd(int i2, int i3) {
        get(i2).layout.guideEnd = i3;
        get(i2).layout.guideBegin = -1;
        get(i2).layout.guidePercent = -1.0f;
    }

    public void setGuidelinePercent(int i2, float f2) {
        get(i2).layout.guidePercent = f2;
        get(i2).layout.guideEnd = -1;
        get(i2).layout.guideBegin = -1;
    }

    public void setHorizontalBias(int i2, float f2) {
        get(i2).layout.horizontalBias = f2;
    }

    public void setHorizontalChainStyle(int i2, int i3) {
        get(i2).layout.horizontalChainStyle = i3;
    }

    public void setHorizontalWeight(int i2, float f2) {
        get(i2).layout.horizontalWeight = f2;
    }

    public void setIntValue(int i2, String str, int i3) {
        get(i2).setIntValue(str, i3);
    }

    public void setLayoutWrapBehavior(int i2, int i3) {
        if (i3 < 0 || i3 > 3) {
            return;
        }
        get(i2).layout.mWrapBehavior = i3;
    }

    public void setMargin(int i2, int i3, int i4) {
        Constraint constraint = get(i2);
        switch (i3) {
            case 1:
                constraint.layout.leftMargin = i4;
                return;
            case 2:
                constraint.layout.rightMargin = i4;
                return;
            case 3:
                constraint.layout.topMargin = i4;
                return;
            case 4:
                constraint.layout.bottomMargin = i4;
                return;
            case 5:
                constraint.layout.baselineMargin = i4;
                return;
            case 6:
                constraint.layout.startMargin = i4;
                return;
            case 7:
                constraint.layout.endMargin = i4;
                return;
            default:
                throw new IllegalArgumentException("unknown constraint");
        }
    }

    public void setReferencedIds(int i2, int... iArr) {
        get(i2).layout.mReferenceIds = iArr;
    }

    public void setRotation(int i2, float f2) {
        get(i2).transform.rotation = f2;
    }

    public void setRotationX(int i2, float f2) {
        get(i2).transform.rotationX = f2;
    }

    public void setRotationY(int i2, float f2) {
        get(i2).transform.rotationY = f2;
    }

    public void setScaleX(int i2, float f2) {
        get(i2).transform.scaleX = f2;
    }

    public void setScaleY(int i2, float f2) {
        get(i2).transform.scaleY = f2;
    }

    public void setStringValue(int i2, String str, String str2) {
        get(i2).setStringValue(str, str2);
    }

    public void setTransformPivot(int i2, float f2, float f3) {
        Transform transform = get(i2).transform;
        transform.transformPivotY = f3;
        transform.transformPivotX = f2;
    }

    public void setTransformPivotX(int i2, float f2) {
        get(i2).transform.transformPivotX = f2;
    }

    public void setTransformPivotY(int i2, float f2) {
        get(i2).transform.transformPivotY = f2;
    }

    public void setTranslation(int i2, float f2, float f3) {
        Transform transform = get(i2).transform;
        transform.translationX = f2;
        transform.translationY = f3;
    }

    public void setTranslationX(int i2, float f2) {
        get(i2).transform.translationX = f2;
    }

    public void setTranslationY(int i2, float f2) {
        get(i2).transform.translationY = f2;
    }

    public void setTranslationZ(int i2, float f2) {
        get(i2).transform.translationZ = f2;
    }

    public void setValidateOnParse(boolean z2) {
        this.mValidate = z2;
    }

    public void setVerticalBias(int i2, float f2) {
        get(i2).layout.verticalBias = f2;
    }

    public void setVerticalChainStyle(int i2, int i3) {
        get(i2).layout.verticalChainStyle = i3;
    }

    public void setVerticalWeight(int i2, float f2) {
        get(i2).layout.verticalWeight = f2;
    }

    public void setVisibility(int i2, int i3) {
        get(i2).propertySet.visibility = i3;
    }

    public void setVisibilityMode(int i2, int i3) {
        get(i2).propertySet.mVisibilityMode = i3;
    }

    public void writeState(Writer writer, ConstraintLayout constraintLayout, int i2) throws IOException {
        writer.write("\n---------------------------------------------\n");
        if ((i2 & 1) == 1) {
            new WriteXmlEngine(writer, constraintLayout, i2).c();
        } else {
            new WriteJsonEngine(writer, constraintLayout, i2).e();
        }
        writer.write("\n---------------------------------------------\n");
    }

    private void createHorizontalChain(int i2, int i3, int i4, int i5, int[] iArr, float[] fArr, int i6, int i7, int i8) {
        if (iArr.length < 2) {
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        }
        if (fArr != null && fArr.length != iArr.length) {
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        }
        if (fArr != null) {
            get(iArr[0]).layout.horizontalWeight = fArr[0];
        }
        get(iArr[0]).layout.horizontalChainStyle = i6;
        connect(iArr[0], i7, i2, i3, -1);
        for (int i9 = 1; i9 < iArr.length; i9++) {
            int i10 = i9 - 1;
            connect(iArr[i9], i7, iArr[i10], i8, -1);
            connect(iArr[i10], i8, iArr[i9], i7, -1);
            if (fArr != null) {
                get(iArr[i9]).layout.horizontalWeight = fArr[i9];
            }
        }
        connect(iArr[iArr.length - 1], i8, i4, i5, -1);
    }

    public void clear(int i2, int i3) {
        Constraint constraint;
        if (!this.mConstraints.containsKey(Integer.valueOf(i2)) || (constraint = this.mConstraints.get(Integer.valueOf(i2))) == null) {
            return;
        }
        switch (i3) {
            case 1:
                Layout layout = constraint.layout;
                layout.leftToRight = -1;
                layout.leftToLeft = -1;
                layout.leftMargin = -1;
                layout.goneLeftMargin = Integer.MIN_VALUE;
                return;
            case 2:
                Layout layout2 = constraint.layout;
                layout2.rightToRight = -1;
                layout2.rightToLeft = -1;
                layout2.rightMargin = -1;
                layout2.goneRightMargin = Integer.MIN_VALUE;
                return;
            case 3:
                Layout layout3 = constraint.layout;
                layout3.topToBottom = -1;
                layout3.topToTop = -1;
                layout3.topMargin = 0;
                layout3.goneTopMargin = Integer.MIN_VALUE;
                return;
            case 4:
                Layout layout4 = constraint.layout;
                layout4.bottomToTop = -1;
                layout4.bottomToBottom = -1;
                layout4.bottomMargin = 0;
                layout4.goneBottomMargin = Integer.MIN_VALUE;
                return;
            case 5:
                Layout layout5 = constraint.layout;
                layout5.baselineToBaseline = -1;
                layout5.baselineToTop = -1;
                layout5.baselineToBottom = -1;
                layout5.baselineMargin = 0;
                layout5.goneBaselineMargin = Integer.MIN_VALUE;
                return;
            case 6:
                Layout layout6 = constraint.layout;
                layout6.startToEnd = -1;
                layout6.startToStart = -1;
                layout6.startMargin = 0;
                layout6.goneStartMargin = Integer.MIN_VALUE;
                return;
            case 7:
                Layout layout7 = constraint.layout;
                layout7.endToStart = -1;
                layout7.endToEnd = -1;
                layout7.endMargin = 0;
                layout7.goneEndMargin = Integer.MIN_VALUE;
                return;
            case 8:
                Layout layout8 = constraint.layout;
                layout8.circleAngle = -1.0f;
                layout8.circleRadius = -1;
                layout8.circleConstraint = -1;
                return;
            default:
                throw new IllegalArgumentException("unknown constraint");
        }
    }

    public void clone(ConstraintSet constraintSet) {
        this.mConstraints.clear();
        for (Integer num : constraintSet.mConstraints.keySet()) {
            Constraint constraint = constraintSet.mConstraints.get(num);
            if (constraint != null) {
                this.mConstraints.put(num, constraint.m5clone());
            }
        }
    }

    public void centerHorizontally(int i2, int i3) {
        if (i3 == 0) {
            center(i2, 0, 1, 0, 0, 2, 0, 0.5f);
        } else {
            center(i2, i3, 2, 0, i3, 1, 0, 0.5f);
        }
    }

    public void centerHorizontallyRtl(int i2, int i3) {
        if (i3 == 0) {
            center(i2, 0, 6, 0, 0, 7, 0, 0.5f);
        } else {
            center(i2, i3, 7, 0, i3, 6, 0, 0.5f);
        }
    }

    public void centerVertically(int i2, int i3) {
        if (i3 == 0) {
            center(i2, 0, 3, 0, 0, 4, 0, 0.5f);
        } else {
            center(i2, i3, 4, 0, i3, 3, 0, 0.5f);
        }
    }

    public void clone(ConstraintLayout constraintLayout) {
        int childCount = constraintLayout.getChildCount();
        this.mConstraints.clear();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = constraintLayout.getChildAt(i2);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) childAt.getLayoutParams();
            int id = childAt.getId();
            if (this.mForceId && id == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (!this.mConstraints.containsKey(Integer.valueOf(id))) {
                this.mConstraints.put(Integer.valueOf(id), new Constraint());
            }
            Constraint constraint = this.mConstraints.get(Integer.valueOf(id));
            if (constraint != null) {
                constraint.mCustomConstraints = ConstraintAttribute.extractAttributes(this.mSavedAttributes, childAt);
                constraint.fillFrom(id, layoutParams);
                constraint.propertySet.visibility = childAt.getVisibility();
                constraint.propertySet.alpha = childAt.getAlpha();
                constraint.transform.rotation = childAt.getRotation();
                constraint.transform.rotationX = childAt.getRotationX();
                constraint.transform.rotationY = childAt.getRotationY();
                constraint.transform.scaleX = childAt.getScaleX();
                constraint.transform.scaleY = childAt.getScaleY();
                float pivotX = childAt.getPivotX();
                float pivotY = childAt.getPivotY();
                if (pivotX != 0.0d || pivotY != 0.0d) {
                    Transform transform = constraint.transform;
                    transform.transformPivotX = pivotX;
                    transform.transformPivotY = pivotY;
                }
                constraint.transform.translationX = childAt.getTranslationX();
                constraint.transform.translationY = childAt.getTranslationY();
                constraint.transform.translationZ = childAt.getTranslationZ();
                Transform transform2 = constraint.transform;
                if (transform2.applyElevation) {
                    transform2.elevation = childAt.getElevation();
                }
                if (childAt instanceof Barrier) {
                    Barrier barrier = (Barrier) childAt;
                    constraint.layout.mBarrierAllowsGoneWidgets = barrier.getAllowsGoneWidget();
                    constraint.layout.mReferenceIds = barrier.getReferencedIds();
                    constraint.layout.mBarrierDirection = barrier.getType();
                    constraint.layout.mBarrierMargin = barrier.getMargin();
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:123:0x01cf, code lost:
    
        continue;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void load(android.content.Context r10, org.xmlpull.v1.XmlPullParser r11) {
        /*
            Method dump skipped, instructions count: 562
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.ConstraintSet.load(android.content.Context, org.xmlpull.v1.XmlPullParser):void");
    }

    public void readFallback(ConstraintLayout constraintLayout) {
        int childCount = constraintLayout.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = constraintLayout.getChildAt(i2);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) childAt.getLayoutParams();
            int id = childAt.getId();
            if (this.mForceId && id == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (!this.mConstraints.containsKey(Integer.valueOf(id))) {
                this.mConstraints.put(Integer.valueOf(id), new Constraint());
            }
            Constraint constraint = this.mConstraints.get(Integer.valueOf(id));
            if (constraint != null) {
                if (!constraint.layout.mApply) {
                    constraint.fillFrom(id, layoutParams);
                    if (childAt instanceof ConstraintHelper) {
                        constraint.layout.mReferenceIds = ((ConstraintHelper) childAt).getReferencedIds();
                        if (childAt instanceof Barrier) {
                            Barrier barrier = (Barrier) childAt;
                            constraint.layout.mBarrierAllowsGoneWidgets = barrier.getAllowsGoneWidget();
                            constraint.layout.mBarrierDirection = barrier.getType();
                            constraint.layout.mBarrierMargin = barrier.getMargin();
                        }
                    }
                    constraint.layout.mApply = true;
                }
                PropertySet propertySet = constraint.propertySet;
                if (!propertySet.mApply) {
                    propertySet.visibility = childAt.getVisibility();
                    constraint.propertySet.alpha = childAt.getAlpha();
                    constraint.propertySet.mApply = true;
                }
                Transform transform = constraint.transform;
                if (!transform.mApply) {
                    transform.mApply = true;
                    transform.rotation = childAt.getRotation();
                    constraint.transform.rotationX = childAt.getRotationX();
                    constraint.transform.rotationY = childAt.getRotationY();
                    constraint.transform.scaleX = childAt.getScaleX();
                    constraint.transform.scaleY = childAt.getScaleY();
                    float pivotX = childAt.getPivotX();
                    float pivotY = childAt.getPivotY();
                    if (pivotX != 0.0d || pivotY != 0.0d) {
                        Transform transform2 = constraint.transform;
                        transform2.transformPivotX = pivotX;
                        transform2.transformPivotY = pivotY;
                    }
                    constraint.transform.translationX = childAt.getTranslationX();
                    constraint.transform.translationY = childAt.getTranslationY();
                    constraint.transform.translationZ = childAt.getTranslationZ();
                    Transform transform3 = constraint.transform;
                    if (transform3.applyElevation) {
                        transform3.elevation = childAt.getElevation();
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setDeltaValue(Constraint constraint, int i2, int i3) {
        if (i2 == 6) {
            constraint.layout.editorAbsoluteX = i3;
            return;
        }
        if (i2 == 7) {
            constraint.layout.editorAbsoluteY = i3;
            return;
        }
        if (i2 == 8) {
            constraint.layout.endMargin = i3;
            return;
        }
        if (i2 == 27) {
            constraint.layout.orientation = i3;
            return;
        }
        if (i2 == 28) {
            constraint.layout.rightMargin = i3;
            return;
        }
        if (i2 == 41) {
            constraint.layout.horizontalChainStyle = i3;
            return;
        }
        if (i2 == 42) {
            constraint.layout.verticalChainStyle = i3;
            return;
        }
        if (i2 == 61) {
            constraint.layout.circleConstraint = i3;
            return;
        }
        if (i2 == 62) {
            constraint.layout.circleRadius = i3;
            return;
        }
        if (i2 == 72) {
            constraint.layout.mBarrierDirection = i3;
            return;
        }
        if (i2 != 73) {
            switch (i2) {
                case 2:
                    constraint.layout.bottomMargin = i3;
                    break;
                case 11:
                    constraint.layout.goneBottomMargin = i3;
                    break;
                case 12:
                    constraint.layout.goneEndMargin = i3;
                    break;
                case 13:
                    constraint.layout.goneLeftMargin = i3;
                    break;
                case 14:
                    constraint.layout.goneRightMargin = i3;
                    break;
                case 15:
                    constraint.layout.goneStartMargin = i3;
                    break;
                case 16:
                    constraint.layout.goneTopMargin = i3;
                    break;
                case 17:
                    constraint.layout.guideBegin = i3;
                    break;
                case 18:
                    constraint.layout.guideEnd = i3;
                    break;
                case 31:
                    constraint.layout.startMargin = i3;
                    break;
                case 34:
                    constraint.layout.topMargin = i3;
                    break;
                case 38:
                    constraint.f3281a = i3;
                    break;
                case 64:
                    constraint.motion.mAnimateRelativeTo = i3;
                    break;
                case 66:
                    constraint.motion.mDrawPath = i3;
                    break;
                case 76:
                    constraint.motion.mPathMotionArc = i3;
                    break;
                case 78:
                    constraint.propertySet.mVisibilityMode = i3;
                    break;
                case 93:
                    constraint.layout.baselineMargin = i3;
                    break;
                case 94:
                    constraint.layout.goneBaselineMargin = i3;
                    break;
                case 97:
                    constraint.layout.mWrapBehavior = i3;
                    break;
                default:
                    switch (i2) {
                        case 21:
                            constraint.layout.mHeight = i3;
                            break;
                        case 22:
                            constraint.propertySet.visibility = i3;
                            break;
                        case 23:
                            constraint.layout.mWidth = i3;
                            break;
                        case 24:
                            constraint.layout.leftMargin = i3;
                            break;
                        default:
                            switch (i2) {
                                case 54:
                                    constraint.layout.widthDefault = i3;
                                    break;
                                case 55:
                                    constraint.layout.heightDefault = i3;
                                    break;
                                case 56:
                                    constraint.layout.widthMax = i3;
                                    break;
                                case 57:
                                    constraint.layout.heightMax = i3;
                                    break;
                                case 58:
                                    constraint.layout.widthMin = i3;
                                    break;
                                case 59:
                                    constraint.layout.heightMin = i3;
                                    break;
                                default:
                                    switch (i2) {
                                        case 82:
                                            constraint.motion.mAnimateCircleAngleTo = i3;
                                            break;
                                        case 83:
                                            constraint.transform.transformPivotTarget = i3;
                                            break;
                                        case 84:
                                            constraint.motion.mQuantizeMotionSteps = i3;
                                            break;
                                        default:
                                            switch (i2) {
                                                case 87:
                                                    break;
                                                case 88:
                                                    constraint.motion.mQuantizeInterpolatorType = i3;
                                                    break;
                                                case 89:
                                                    constraint.motion.mQuantizeInterpolatorID = i3;
                                                    break;
                                                default:
                                                    Log.w(TAG, "Unknown attribute 0x");
                                                    break;
                                            }
                                    }
                            }
                    }
            }
            return;
        }
        constraint.layout.mBarrierMargin = i3;
    }

    public void clone(Constraints constraints) {
        int childCount = constraints.getChildCount();
        this.mConstraints.clear();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = constraints.getChildAt(i2);
            Constraints.LayoutParams layoutParams = (Constraints.LayoutParams) childAt.getLayoutParams();
            int id = childAt.getId();
            if (this.mForceId && id == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (!this.mConstraints.containsKey(Integer.valueOf(id))) {
                this.mConstraints.put(Integer.valueOf(id), new Constraint());
            }
            Constraint constraint = this.mConstraints.get(Integer.valueOf(id));
            if (constraint != null) {
                if (childAt instanceof ConstraintHelper) {
                    constraint.fillFromConstraints((ConstraintHelper) childAt, id, layoutParams);
                }
                constraint.fillFromConstraints(id, layoutParams);
            }
        }
    }

    public void connect(int i2, int i3, int i4, int i5) {
        if (!this.mConstraints.containsKey(Integer.valueOf(i2))) {
            this.mConstraints.put(Integer.valueOf(i2), new Constraint());
        }
        Constraint constraint = this.mConstraints.get(Integer.valueOf(i2));
        if (constraint == null) {
            return;
        }
        switch (i3) {
            case 1:
                if (i5 == 1) {
                    Layout layout = constraint.layout;
                    layout.leftToLeft = i4;
                    layout.leftToRight = -1;
                    return;
                } else if (i5 == 2) {
                    Layout layout2 = constraint.layout;
                    layout2.leftToRight = i4;
                    layout2.leftToLeft = -1;
                    return;
                } else {
                    throw new IllegalArgumentException("left to " + sideToString(i5) + " undefined");
                }
            case 2:
                if (i5 == 1) {
                    Layout layout3 = constraint.layout;
                    layout3.rightToLeft = i4;
                    layout3.rightToRight = -1;
                    return;
                } else {
                    if (i5 != 2) {
                        throw new IllegalArgumentException("right to " + sideToString(i5) + " undefined");
                    }
                    Layout layout4 = constraint.layout;
                    layout4.rightToRight = i4;
                    layout4.rightToLeft = -1;
                    return;
                }
            case 3:
                if (i5 == 3) {
                    Layout layout5 = constraint.layout;
                    layout5.topToTop = i4;
                    layout5.topToBottom = -1;
                    layout5.baselineToBaseline = -1;
                    layout5.baselineToTop = -1;
                    layout5.baselineToBottom = -1;
                    return;
                }
                if (i5 != 4) {
                    throw new IllegalArgumentException("right to " + sideToString(i5) + " undefined");
                }
                Layout layout6 = constraint.layout;
                layout6.topToBottom = i4;
                layout6.topToTop = -1;
                layout6.baselineToBaseline = -1;
                layout6.baselineToTop = -1;
                layout6.baselineToBottom = -1;
                return;
            case 4:
                if (i5 == 4) {
                    Layout layout7 = constraint.layout;
                    layout7.bottomToBottom = i4;
                    layout7.bottomToTop = -1;
                    layout7.baselineToBaseline = -1;
                    layout7.baselineToTop = -1;
                    layout7.baselineToBottom = -1;
                    return;
                }
                if (i5 != 3) {
                    throw new IllegalArgumentException("right to " + sideToString(i5) + " undefined");
                }
                Layout layout8 = constraint.layout;
                layout8.bottomToTop = i4;
                layout8.bottomToBottom = -1;
                layout8.baselineToBaseline = -1;
                layout8.baselineToTop = -1;
                layout8.baselineToBottom = -1;
                return;
            case 5:
                if (i5 == 5) {
                    Layout layout9 = constraint.layout;
                    layout9.baselineToBaseline = i4;
                    layout9.bottomToBottom = -1;
                    layout9.bottomToTop = -1;
                    layout9.topToTop = -1;
                    layout9.topToBottom = -1;
                    return;
                }
                if (i5 == 3) {
                    Layout layout10 = constraint.layout;
                    layout10.baselineToTop = i4;
                    layout10.bottomToBottom = -1;
                    layout10.bottomToTop = -1;
                    layout10.topToTop = -1;
                    layout10.topToBottom = -1;
                    return;
                }
                if (i5 != 4) {
                    throw new IllegalArgumentException("right to " + sideToString(i5) + " undefined");
                }
                Layout layout11 = constraint.layout;
                layout11.baselineToBottom = i4;
                layout11.bottomToBottom = -1;
                layout11.bottomToTop = -1;
                layout11.topToTop = -1;
                layout11.topToBottom = -1;
                return;
            case 6:
                if (i5 == 6) {
                    Layout layout12 = constraint.layout;
                    layout12.startToStart = i4;
                    layout12.startToEnd = -1;
                    return;
                } else {
                    if (i5 != 7) {
                        throw new IllegalArgumentException("right to " + sideToString(i5) + " undefined");
                    }
                    Layout layout13 = constraint.layout;
                    layout13.startToEnd = i4;
                    layout13.startToStart = -1;
                    return;
                }
            case 7:
                if (i5 == 7) {
                    Layout layout14 = constraint.layout;
                    layout14.endToEnd = i4;
                    layout14.endToStart = -1;
                    return;
                } else {
                    if (i5 != 6) {
                        throw new IllegalArgumentException("right to " + sideToString(i5) + " undefined");
                    }
                    Layout layout15 = constraint.layout;
                    layout15.endToStart = i4;
                    layout15.endToEnd = -1;
                    return;
                }
            default:
                throw new IllegalArgumentException(sideToString(i3) + " to " + sideToString(i5) + " unknown");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setDeltaValue(Constraint constraint, int i2, String str) {
        if (i2 == 5) {
            constraint.layout.dimensionRatio = str;
            return;
        }
        if (i2 == 65) {
            constraint.motion.mTransitionEasing = str;
            return;
        }
        if (i2 == 74) {
            Layout layout = constraint.layout;
            layout.mReferenceIdString = str;
            layout.mReferenceIds = null;
        } else if (i2 == 77) {
            constraint.layout.mConstraintTag = str;
        } else if (i2 != 87) {
            if (i2 != 90) {
                Log.w(TAG, "Unknown attribute 0x");
            } else {
                constraint.motion.mQuantizeInterpolatorString = str;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setDeltaValue(Constraint constraint, int i2, boolean z2) {
        if (i2 == 44) {
            constraint.transform.applyElevation = z2;
            return;
        }
        if (i2 == 75) {
            constraint.layout.mBarrierAllowsGoneWidgets = z2;
            return;
        }
        if (i2 != 87) {
            if (i2 == 80) {
                constraint.layout.constrainedWidth = z2;
            } else if (i2 != 81) {
                Log.w(TAG, "Unknown attribute 0x");
            } else {
                constraint.layout.constrainedHeight = z2;
            }
        }
    }
}
