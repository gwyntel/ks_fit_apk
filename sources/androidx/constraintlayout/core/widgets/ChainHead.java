package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class ChainHead {

    /* renamed from: a, reason: collision with root package name */
    protected ConstraintWidget f2868a;

    /* renamed from: b, reason: collision with root package name */
    protected ConstraintWidget f2869b;

    /* renamed from: c, reason: collision with root package name */
    protected ConstraintWidget f2870c;

    /* renamed from: d, reason: collision with root package name */
    protected ConstraintWidget f2871d;

    /* renamed from: e, reason: collision with root package name */
    protected ConstraintWidget f2872e;

    /* renamed from: f, reason: collision with root package name */
    protected ConstraintWidget f2873f;

    /* renamed from: g, reason: collision with root package name */
    protected ConstraintWidget f2874g;

    /* renamed from: h, reason: collision with root package name */
    protected ArrayList f2875h;

    /* renamed from: i, reason: collision with root package name */
    protected int f2876i;

    /* renamed from: j, reason: collision with root package name */
    protected int f2877j;

    /* renamed from: k, reason: collision with root package name */
    protected float f2878k = 0.0f;

    /* renamed from: l, reason: collision with root package name */
    int f2879l;

    /* renamed from: m, reason: collision with root package name */
    int f2880m;
    private boolean mDefined;
    private boolean mIsRtl;
    private int mOrientation;

    /* renamed from: n, reason: collision with root package name */
    int f2881n;

    /* renamed from: o, reason: collision with root package name */
    boolean f2882o;

    /* renamed from: p, reason: collision with root package name */
    protected boolean f2883p;

    /* renamed from: q, reason: collision with root package name */
    protected boolean f2884q;

    /* renamed from: r, reason: collision with root package name */
    protected boolean f2885r;

    /* renamed from: s, reason: collision with root package name */
    protected boolean f2886s;

    public ChainHead(ConstraintWidget constraintWidget, int i2, boolean z2) {
        this.f2868a = constraintWidget;
        this.mOrientation = i2;
        this.mIsRtl = z2;
    }

    private void defineChainProperties() {
        int i2 = this.mOrientation * 2;
        ConstraintWidget constraintWidget = this.f2868a;
        this.f2882o = true;
        ConstraintWidget constraintWidget2 = constraintWidget;
        boolean z2 = false;
        while (!z2) {
            this.f2876i++;
            ConstraintWidget[] constraintWidgetArr = constraintWidget.K;
            int i3 = this.mOrientation;
            ConstraintWidget constraintWidget3 = null;
            constraintWidgetArr[i3] = null;
            constraintWidget.J[i3] = null;
            if (constraintWidget.getVisibility() != 8) {
                this.f2879l++;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = constraintWidget.getDimensionBehaviour(this.mOrientation);
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                if (dimensionBehaviour != dimensionBehaviour2) {
                    this.f2880m += constraintWidget.getLength(this.mOrientation);
                }
                int margin = this.f2880m + constraintWidget.mListAnchors[i2].getMargin();
                this.f2880m = margin;
                int i4 = i2 + 1;
                this.f2880m = margin + constraintWidget.mListAnchors[i4].getMargin();
                int margin2 = this.f2881n + constraintWidget.mListAnchors[i2].getMargin();
                this.f2881n = margin2;
                this.f2881n = margin2 + constraintWidget.mListAnchors[i4].getMargin();
                if (this.f2869b == null) {
                    this.f2869b = constraintWidget;
                }
                this.f2871d = constraintWidget;
                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidget.mListDimensionBehaviors;
                int i5 = this.mOrientation;
                if (dimensionBehaviourArr[i5] == dimensionBehaviour2) {
                    int i6 = constraintWidget.mResolvedMatchConstraintDefault[i5];
                    if (i6 == 0 || i6 == 3 || i6 == 2) {
                        this.f2877j++;
                        float f2 = constraintWidget.mWeight[i5];
                        if (f2 > 0.0f) {
                            this.f2878k += f2;
                        }
                        if (isMatchConstraintEqualityCandidate(constraintWidget, i5)) {
                            if (f2 < 0.0f) {
                                this.f2883p = true;
                            } else {
                                this.f2884q = true;
                            }
                            if (this.f2875h == null) {
                                this.f2875h = new ArrayList();
                            }
                            this.f2875h.add(constraintWidget);
                        }
                        if (this.f2873f == null) {
                            this.f2873f = constraintWidget;
                        }
                        ConstraintWidget constraintWidget4 = this.f2874g;
                        if (constraintWidget4 != null) {
                            constraintWidget4.J[this.mOrientation] = constraintWidget;
                        }
                        this.f2874g = constraintWidget;
                    }
                    if (this.mOrientation == 0) {
                        if (constraintWidget.mMatchConstraintDefaultWidth != 0 || constraintWidget.mMatchConstraintMinWidth != 0 || constraintWidget.mMatchConstraintMaxWidth != 0) {
                            this.f2882o = false;
                        }
                    } else if (constraintWidget.mMatchConstraintDefaultHeight != 0 || constraintWidget.mMatchConstraintMinHeight != 0 || constraintWidget.mMatchConstraintMaxHeight != 0) {
                        this.f2882o = false;
                    }
                    if (constraintWidget.mDimensionRatio != 0.0f) {
                        this.f2882o = false;
                        this.f2886s = true;
                    }
                }
            }
            if (constraintWidget2 != constraintWidget) {
                constraintWidget2.K[this.mOrientation] = constraintWidget;
            }
            ConstraintAnchor constraintAnchor = constraintWidget.mListAnchors[i2 + 1].mTarget;
            if (constraintAnchor != null) {
                ConstraintWidget constraintWidget5 = constraintAnchor.mOwner;
                ConstraintAnchor constraintAnchor2 = constraintWidget5.mListAnchors[i2].mTarget;
                if (constraintAnchor2 != null && constraintAnchor2.mOwner == constraintWidget) {
                    constraintWidget3 = constraintWidget5;
                }
            }
            if (constraintWidget3 == null) {
                constraintWidget3 = constraintWidget;
                z2 = true;
            }
            constraintWidget2 = constraintWidget;
            constraintWidget = constraintWidget3;
        }
        ConstraintWidget constraintWidget6 = this.f2869b;
        if (constraintWidget6 != null) {
            this.f2880m -= constraintWidget6.mListAnchors[i2].getMargin();
        }
        ConstraintWidget constraintWidget7 = this.f2871d;
        if (constraintWidget7 != null) {
            this.f2880m -= constraintWidget7.mListAnchors[i2 + 1].getMargin();
        }
        this.f2870c = constraintWidget;
        if (this.mOrientation == 0 && this.mIsRtl) {
            this.f2872e = constraintWidget;
        } else {
            this.f2872e = this.f2868a;
        }
        this.f2885r = this.f2884q && this.f2883p;
    }

    private static boolean isMatchConstraintEqualityCandidate(ConstraintWidget constraintWidget, int i2) {
        int i3;
        return constraintWidget.getVisibility() != 8 && constraintWidget.mListDimensionBehaviors[i2] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && ((i3 = constraintWidget.mResolvedMatchConstraintDefault[i2]) == 0 || i3 == 3);
    }

    public void define() {
        if (!this.mDefined) {
            defineChainProperties();
        }
        this.mDefined = true;
    }

    public ConstraintWidget getFirst() {
        return this.f2868a;
    }

    public ConstraintWidget getFirstMatchConstraintWidget() {
        return this.f2873f;
    }

    public ConstraintWidget getFirstVisibleWidget() {
        return this.f2869b;
    }

    public ConstraintWidget getHead() {
        return this.f2872e;
    }

    public ConstraintWidget getLast() {
        return this.f2870c;
    }

    public ConstraintWidget getLastMatchConstraintWidget() {
        return this.f2874g;
    }

    public ConstraintWidget getLastVisibleWidget() {
        return this.f2871d;
    }

    public float getTotalWeight() {
        return this.f2878k;
    }
}
