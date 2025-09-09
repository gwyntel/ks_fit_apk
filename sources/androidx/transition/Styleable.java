package androidx.transition;

import android.annotation.SuppressLint;
import androidx.annotation.StyleableRes;

@SuppressLint({"InlinedApi"})
/* loaded from: classes2.dex */
class Styleable {

    /* renamed from: a, reason: collision with root package name */
    static final int[] f6305a = {android.R.attr.targetClass, android.R.attr.targetId, android.R.attr.excludeId, android.R.attr.excludeClass, android.R.attr.targetName, android.R.attr.excludeName};

    /* renamed from: b, reason: collision with root package name */
    static final int[] f6306b = {android.R.attr.fromScene, android.R.attr.toScene, android.R.attr.transition};

    /* renamed from: c, reason: collision with root package name */
    static final int[] f6307c = {android.R.attr.interpolator, android.R.attr.duration, android.R.attr.startDelay, android.R.attr.matchOrder};

    /* renamed from: d, reason: collision with root package name */
    static final int[] f6308d = {android.R.attr.resizeClip};

    /* renamed from: e, reason: collision with root package name */
    static final int[] f6309e = {android.R.attr.transitionVisibilityMode};

    /* renamed from: f, reason: collision with root package name */
    static final int[] f6310f = {android.R.attr.fadingMode};

    /* renamed from: g, reason: collision with root package name */
    static final int[] f6311g = {android.R.attr.reparent, android.R.attr.reparentWithOverlay};

    /* renamed from: h, reason: collision with root package name */
    static final int[] f6312h = {android.R.attr.slideEdge};

    /* renamed from: i, reason: collision with root package name */
    static final int[] f6313i = {android.R.attr.transitionOrdering};

    /* renamed from: j, reason: collision with root package name */
    static final int[] f6314j = {android.R.attr.minimumHorizontalAngle, android.R.attr.minimumVerticalAngle, android.R.attr.maximumAngle};

    /* renamed from: k, reason: collision with root package name */
    static final int[] f6315k = {android.R.attr.patternPathData};

    interface ArcMotion {

        @StyleableRes
        public static final int MAXIMUM_ANGLE = 2;

        @StyleableRes
        public static final int MINIMUM_HORIZONTAL_ANGLE = 0;

        @StyleableRes
        public static final int MINIMUM_VERTICAL_ANGLE = 1;
    }

    interface ChangeBounds {

        @StyleableRes
        public static final int RESIZE_CLIP = 0;
    }

    interface ChangeTransform {

        @StyleableRes
        public static final int REPARENT = 0;

        @StyleableRes
        public static final int REPARENT_WITH_OVERLAY = 1;
    }

    interface Fade {

        @StyleableRes
        public static final int FADING_MODE = 0;
    }

    interface PatternPathMotion {

        @StyleableRes
        public static final int PATTERN_PATH_DATA = 0;
    }

    interface Slide {

        @StyleableRes
        public static final int SLIDE_EDGE = 0;
    }

    interface Transition {

        @StyleableRes
        public static final int DURATION = 1;

        @StyleableRes
        public static final int INTERPOLATOR = 0;

        @StyleableRes
        public static final int MATCH_ORDER = 3;

        @StyleableRes
        public static final int START_DELAY = 2;
    }

    interface TransitionManager {

        @StyleableRes
        public static final int FROM_SCENE = 0;

        @StyleableRes
        public static final int TO_SCENE = 1;

        @StyleableRes
        public static final int TRANSITION = 2;
    }

    interface TransitionSet {

        @StyleableRes
        public static final int TRANSITION_ORDERING = 0;
    }

    interface TransitionTarget {

        @StyleableRes
        public static final int EXCLUDE_CLASS = 3;

        @StyleableRes
        public static final int EXCLUDE_ID = 2;

        @StyleableRes
        public static final int EXCLUDE_NAME = 5;

        @StyleableRes
        public static final int TARGET_CLASS = 0;

        @StyleableRes
        public static final int TARGET_ID = 1;

        @StyleableRes
        public static final int TARGET_NAME = 4;
    }

    interface VisibilityTransition {

        @StyleableRes
        public static final int TRANSITION_VISIBILITY_MODE = 0;
    }

    private Styleable() {
    }
}
