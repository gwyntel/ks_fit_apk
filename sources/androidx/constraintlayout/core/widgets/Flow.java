package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes.dex */
public class Flow extends VirtualLayout {
    public static final int HORIZONTAL_ALIGN_CENTER = 2;
    public static final int HORIZONTAL_ALIGN_END = 1;
    public static final int HORIZONTAL_ALIGN_START = 0;
    public static final int VERTICAL_ALIGN_BASELINE = 3;
    public static final int VERTICAL_ALIGN_BOTTOM = 1;
    public static final int VERTICAL_ALIGN_CENTER = 2;
    public static final int VERTICAL_ALIGN_TOP = 0;
    public static final int WRAP_ALIGNED = 2;
    public static final int WRAP_CHAIN = 1;
    public static final int WRAP_CHAIN_NEW = 3;
    public static final int WRAP_NONE = 0;
    private ConstraintWidget[] mDisplayedWidgets;
    private int mHorizontalStyle = -1;
    private int mVerticalStyle = -1;
    private int mFirstHorizontalStyle = -1;
    private int mFirstVerticalStyle = -1;
    private int mLastHorizontalStyle = -1;
    private int mLastVerticalStyle = -1;
    private float mHorizontalBias = 0.5f;
    private float mVerticalBias = 0.5f;
    private float mFirstHorizontalBias = 0.5f;
    private float mFirstVerticalBias = 0.5f;
    private float mLastHorizontalBias = 0.5f;
    private float mLastVerticalBias = 0.5f;
    private int mHorizontalGap = 0;
    private int mVerticalGap = 0;
    private int mHorizontalAlign = 2;
    private int mVerticalAlign = 2;
    private int mWrapMode = 0;
    private int mMaxElementsWrap = -1;
    private int mOrientation = 0;
    private ArrayList<WidgetsList> mChainList = new ArrayList<>();
    private ConstraintWidget[] mAlignedBiggestElementsInRows = null;
    private ConstraintWidget[] mAlignedBiggestElementsInCols = null;
    private int[] mAlignedDimensions = null;
    private int mDisplayedWidgetsCount = 0;

    private class WidgetsList {
        private ConstraintAnchor mBottom;
        private ConstraintAnchor mLeft;
        private int mMax;
        private int mOrientation;
        private int mPaddingBottom;
        private int mPaddingLeft;
        private int mPaddingRight;
        private int mPaddingTop;
        private ConstraintAnchor mRight;
        private ConstraintAnchor mTop;
        private ConstraintWidget biggest = null;

        /* renamed from: a, reason: collision with root package name */
        int f2918a = 0;
        private int mWidth = 0;
        private int mHeight = 0;
        private int mStartIndex = 0;
        private int mCount = 0;
        private int mNbMatchConstraintsWidgets = 0;

        public WidgetsList(int i2, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, ConstraintAnchor constraintAnchor3, ConstraintAnchor constraintAnchor4, int i3) {
            this.mPaddingLeft = 0;
            this.mPaddingTop = 0;
            this.mPaddingRight = 0;
            this.mPaddingBottom = 0;
            this.mMax = 0;
            this.mOrientation = i2;
            this.mLeft = constraintAnchor;
            this.mTop = constraintAnchor2;
            this.mRight = constraintAnchor3;
            this.mBottom = constraintAnchor4;
            this.mPaddingLeft = Flow.this.getPaddingLeft();
            this.mPaddingTop = Flow.this.getPaddingTop();
            this.mPaddingRight = Flow.this.getPaddingRight();
            this.mPaddingBottom = Flow.this.getPaddingBottom();
            this.mMax = i3;
        }

        private void recomputeDimensions() {
            this.mWidth = 0;
            this.mHeight = 0;
            this.biggest = null;
            this.f2918a = 0;
            int i2 = this.mCount;
            for (int i3 = 0; i3 < i2 && this.mStartIndex + i3 < Flow.this.mDisplayedWidgetsCount; i3++) {
                ConstraintWidget constraintWidget = Flow.this.mDisplayedWidgets[this.mStartIndex + i3];
                if (this.mOrientation == 0) {
                    int width = constraintWidget.getWidth();
                    int i4 = Flow.this.mHorizontalGap;
                    if (constraintWidget.getVisibility() == 8) {
                        i4 = 0;
                    }
                    this.mWidth += width + i4;
                    int widgetHeight = Flow.this.getWidgetHeight(constraintWidget, this.mMax);
                    if (this.biggest == null || this.f2918a < widgetHeight) {
                        this.biggest = constraintWidget;
                        this.f2918a = widgetHeight;
                        this.mHeight = widgetHeight;
                    }
                } else {
                    int widgetWidth = Flow.this.getWidgetWidth(constraintWidget, this.mMax);
                    int widgetHeight2 = Flow.this.getWidgetHeight(constraintWidget, this.mMax);
                    int i5 = Flow.this.mVerticalGap;
                    if (constraintWidget.getVisibility() == 8) {
                        i5 = 0;
                    }
                    this.mHeight += widgetHeight2 + i5;
                    if (this.biggest == null || this.f2918a < widgetWidth) {
                        this.biggest = constraintWidget;
                        this.f2918a = widgetWidth;
                        this.mWidth = widgetWidth;
                    }
                }
            }
        }

        public void add(ConstraintWidget constraintWidget) {
            if (this.mOrientation == 0) {
                int widgetWidth = Flow.this.getWidgetWidth(constraintWidget, this.mMax);
                if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    this.mNbMatchConstraintsWidgets++;
                    widgetWidth = 0;
                }
                this.mWidth += widgetWidth + (constraintWidget.getVisibility() != 8 ? Flow.this.mHorizontalGap : 0);
                int widgetHeight = Flow.this.getWidgetHeight(constraintWidget, this.mMax);
                if (this.biggest == null || this.f2918a < widgetHeight) {
                    this.biggest = constraintWidget;
                    this.f2918a = widgetHeight;
                    this.mHeight = widgetHeight;
                }
            } else {
                int widgetWidth2 = Flow.this.getWidgetWidth(constraintWidget, this.mMax);
                int widgetHeight2 = Flow.this.getWidgetHeight(constraintWidget, this.mMax);
                if (constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    this.mNbMatchConstraintsWidgets++;
                    widgetHeight2 = 0;
                }
                this.mHeight += widgetHeight2 + (constraintWidget.getVisibility() != 8 ? Flow.this.mVerticalGap : 0);
                if (this.biggest == null || this.f2918a < widgetWidth2) {
                    this.biggest = constraintWidget;
                    this.f2918a = widgetWidth2;
                    this.mWidth = widgetWidth2;
                }
            }
            this.mCount++;
        }

        public void clear() {
            this.f2918a = 0;
            this.biggest = null;
            this.mWidth = 0;
            this.mHeight = 0;
            this.mStartIndex = 0;
            this.mCount = 0;
            this.mNbMatchConstraintsWidgets = 0;
        }

        public void createConstraints(boolean z2, int i2, boolean z3) {
            ConstraintWidget constraintWidget;
            char c2;
            float f2;
            float f3;
            int i3 = this.mCount;
            for (int i4 = 0; i4 < i3 && this.mStartIndex + i4 < Flow.this.mDisplayedWidgetsCount; i4++) {
                ConstraintWidget constraintWidget2 = Flow.this.mDisplayedWidgets[this.mStartIndex + i4];
                if (constraintWidget2 != null) {
                    constraintWidget2.resetAnchors();
                }
            }
            if (i3 == 0 || this.biggest == null) {
                return;
            }
            boolean z4 = z3 && i2 == 0;
            int i5 = -1;
            int i6 = -1;
            for (int i7 = 0; i7 < i3; i7++) {
                int i8 = z2 ? (i3 - 1) - i7 : i7;
                if (this.mStartIndex + i8 >= Flow.this.mDisplayedWidgetsCount) {
                    break;
                }
                ConstraintWidget constraintWidget3 = Flow.this.mDisplayedWidgets[this.mStartIndex + i8];
                if (constraintWidget3 != null && constraintWidget3.getVisibility() == 0) {
                    if (i5 == -1) {
                        i5 = i7;
                    }
                    i6 = i7;
                }
            }
            ConstraintWidget constraintWidget4 = null;
            if (this.mOrientation != 0) {
                ConstraintWidget constraintWidget5 = this.biggest;
                constraintWidget5.setHorizontalChainStyle(Flow.this.mHorizontalStyle);
                int i9 = this.mPaddingLeft;
                if (i2 > 0) {
                    i9 += Flow.this.mHorizontalGap;
                }
                if (z2) {
                    constraintWidget5.mRight.connect(this.mRight, i9);
                    if (z3) {
                        constraintWidget5.mLeft.connect(this.mLeft, this.mPaddingRight);
                    }
                    if (i2 > 0) {
                        this.mRight.mOwner.mLeft.connect(constraintWidget5.mRight, 0);
                    }
                } else {
                    constraintWidget5.mLeft.connect(this.mLeft, i9);
                    if (z3) {
                        constraintWidget5.mRight.connect(this.mRight, this.mPaddingRight);
                    }
                    if (i2 > 0) {
                        this.mLeft.mOwner.mRight.connect(constraintWidget5.mLeft, 0);
                    }
                }
                for (int i10 = 0; i10 < i3 && this.mStartIndex + i10 < Flow.this.mDisplayedWidgetsCount; i10++) {
                    ConstraintWidget constraintWidget6 = Flow.this.mDisplayedWidgets[this.mStartIndex + i10];
                    if (constraintWidget6 != null) {
                        if (i10 == 0) {
                            constraintWidget6.connect(constraintWidget6.mTop, this.mTop, this.mPaddingTop);
                            int i11 = Flow.this.mVerticalStyle;
                            float f4 = Flow.this.mVerticalBias;
                            if (this.mStartIndex == 0 && Flow.this.mFirstVerticalStyle != -1) {
                                i11 = Flow.this.mFirstVerticalStyle;
                                f4 = Flow.this.mFirstVerticalBias;
                            } else if (z3 && Flow.this.mLastVerticalStyle != -1) {
                                i11 = Flow.this.mLastVerticalStyle;
                                f4 = Flow.this.mLastVerticalBias;
                            }
                            constraintWidget6.setVerticalChainStyle(i11);
                            constraintWidget6.setVerticalBiasPercent(f4);
                        }
                        if (i10 == i3 - 1) {
                            constraintWidget6.connect(constraintWidget6.mBottom, this.mBottom, this.mPaddingBottom);
                        }
                        if (constraintWidget4 != null) {
                            constraintWidget6.mTop.connect(constraintWidget4.mBottom, Flow.this.mVerticalGap);
                            if (i10 == i5) {
                                constraintWidget6.mTop.setGoneMargin(this.mPaddingTop);
                            }
                            constraintWidget4.mBottom.connect(constraintWidget6.mTop, 0);
                            if (i10 == i6 + 1) {
                                constraintWidget4.mBottom.setGoneMargin(this.mPaddingBottom);
                            }
                        }
                        if (constraintWidget6 == constraintWidget5) {
                            constraintWidget4 = constraintWidget6;
                        } else if (z2) {
                            int i12 = Flow.this.mHorizontalAlign;
                            if (i12 == 0) {
                                constraintWidget6.mRight.connect(constraintWidget5.mRight, 0);
                            } else if (i12 == 1) {
                                constraintWidget6.mLeft.connect(constraintWidget5.mLeft, 0);
                            } else if (i12 == 2) {
                                constraintWidget6.mLeft.connect(constraintWidget5.mLeft, 0);
                                constraintWidget6.mRight.connect(constraintWidget5.mRight, 0);
                            }
                            constraintWidget4 = constraintWidget6;
                        } else {
                            int i13 = Flow.this.mHorizontalAlign;
                            if (i13 == 0) {
                                constraintWidget6.mLeft.connect(constraintWidget5.mLeft, 0);
                            } else if (i13 == 1) {
                                constraintWidget6.mRight.connect(constraintWidget5.mRight, 0);
                            } else if (i13 == 2) {
                                if (z4) {
                                    constraintWidget6.mLeft.connect(this.mLeft, this.mPaddingLeft);
                                    constraintWidget6.mRight.connect(this.mRight, this.mPaddingRight);
                                } else {
                                    constraintWidget6.mLeft.connect(constraintWidget5.mLeft, 0);
                                    constraintWidget6.mRight.connect(constraintWidget5.mRight, 0);
                                }
                            }
                            constraintWidget4 = constraintWidget6;
                        }
                    }
                }
                return;
            }
            ConstraintWidget constraintWidget7 = this.biggest;
            constraintWidget7.setVerticalChainStyle(Flow.this.mVerticalStyle);
            int i14 = this.mPaddingTop;
            if (i2 > 0) {
                i14 += Flow.this.mVerticalGap;
            }
            constraintWidget7.mTop.connect(this.mTop, i14);
            if (z3) {
                constraintWidget7.mBottom.connect(this.mBottom, this.mPaddingBottom);
            }
            if (i2 > 0) {
                this.mTop.mOwner.mBottom.connect(constraintWidget7.mTop, 0);
            }
            char c3 = 3;
            if (Flow.this.mVerticalAlign != 3 || constraintWidget7.hasBaseline()) {
                constraintWidget = constraintWidget7;
            } else {
                for (int i15 = 0; i15 < i3; i15++) {
                    int i16 = z2 ? (i3 - 1) - i15 : i15;
                    if (this.mStartIndex + i16 >= Flow.this.mDisplayedWidgetsCount) {
                        break;
                    }
                    constraintWidget = Flow.this.mDisplayedWidgets[this.mStartIndex + i16];
                    if (constraintWidget.hasBaseline()) {
                        break;
                    }
                }
                constraintWidget = constraintWidget7;
            }
            int i17 = 0;
            while (i17 < i3) {
                int i18 = z2 ? (i3 - 1) - i17 : i17;
                if (this.mStartIndex + i18 >= Flow.this.mDisplayedWidgetsCount) {
                    return;
                }
                ConstraintWidget constraintWidget8 = Flow.this.mDisplayedWidgets[this.mStartIndex + i18];
                if (constraintWidget8 == null) {
                    constraintWidget8 = constraintWidget4;
                    c2 = c3;
                } else {
                    if (i17 == 0) {
                        constraintWidget8.connect(constraintWidget8.mLeft, this.mLeft, this.mPaddingLeft);
                    }
                    if (i18 == 0) {
                        int i19 = Flow.this.mHorizontalStyle;
                        float f5 = Flow.this.mHorizontalBias;
                        if (z2) {
                            f5 = 1.0f - f5;
                        }
                        if (this.mStartIndex != 0 || Flow.this.mFirstHorizontalStyle == -1) {
                            if (z3 && Flow.this.mLastHorizontalStyle != -1) {
                                i19 = Flow.this.mLastHorizontalStyle;
                                if (z2) {
                                    f3 = Flow.this.mLastHorizontalBias;
                                    f2 = 1.0f - f3;
                                    f5 = f2;
                                } else {
                                    f2 = Flow.this.mLastHorizontalBias;
                                    f5 = f2;
                                }
                            }
                            constraintWidget8.setHorizontalChainStyle(i19);
                            constraintWidget8.setHorizontalBiasPercent(f5);
                        } else {
                            i19 = Flow.this.mFirstHorizontalStyle;
                            if (z2) {
                                f3 = Flow.this.mFirstHorizontalBias;
                                f2 = 1.0f - f3;
                                f5 = f2;
                                constraintWidget8.setHorizontalChainStyle(i19);
                                constraintWidget8.setHorizontalBiasPercent(f5);
                            } else {
                                f2 = Flow.this.mFirstHorizontalBias;
                                f5 = f2;
                                constraintWidget8.setHorizontalChainStyle(i19);
                                constraintWidget8.setHorizontalBiasPercent(f5);
                            }
                        }
                    }
                    if (i17 == i3 - 1) {
                        constraintWidget8.connect(constraintWidget8.mRight, this.mRight, this.mPaddingRight);
                    }
                    if (constraintWidget4 != null) {
                        constraintWidget8.mLeft.connect(constraintWidget4.mRight, Flow.this.mHorizontalGap);
                        if (i17 == i5) {
                            constraintWidget8.mLeft.setGoneMargin(this.mPaddingLeft);
                        }
                        constraintWidget4.mRight.connect(constraintWidget8.mLeft, 0);
                        if (i17 == i6 + 1) {
                            constraintWidget4.mRight.setGoneMargin(this.mPaddingRight);
                        }
                    }
                    if (constraintWidget8 != constraintWidget7) {
                        c2 = 3;
                        if (Flow.this.mVerticalAlign == 3 && constraintWidget.hasBaseline() && constraintWidget8 != constraintWidget && constraintWidget8.hasBaseline()) {
                            constraintWidget8.mBaseline.connect(constraintWidget.mBaseline, 0);
                        } else {
                            int i20 = Flow.this.mVerticalAlign;
                            if (i20 == 0) {
                                constraintWidget8.mTop.connect(constraintWidget7.mTop, 0);
                            } else if (i20 == 1) {
                                constraintWidget8.mBottom.connect(constraintWidget7.mBottom, 0);
                            } else if (z4) {
                                constraintWidget8.mTop.connect(this.mTop, this.mPaddingTop);
                                constraintWidget8.mBottom.connect(this.mBottom, this.mPaddingBottom);
                            } else {
                                constraintWidget8.mTop.connect(constraintWidget7.mTop, 0);
                                constraintWidget8.mBottom.connect(constraintWidget7.mBottom, 0);
                            }
                        }
                    } else {
                        c2 = 3;
                    }
                }
                i17++;
                c3 = c2;
                constraintWidget4 = constraintWidget8;
            }
        }

        public int getHeight() {
            return this.mOrientation == 1 ? this.mHeight - Flow.this.mVerticalGap : this.mHeight;
        }

        public int getWidth() {
            return this.mOrientation == 0 ? this.mWidth - Flow.this.mHorizontalGap : this.mWidth;
        }

        public void measureMatchConstraints(int i2) {
            int i3 = this.mNbMatchConstraintsWidgets;
            if (i3 == 0) {
                return;
            }
            int i4 = this.mCount;
            int i5 = i2 / i3;
            for (int i6 = 0; i6 < i4 && this.mStartIndex + i6 < Flow.this.mDisplayedWidgetsCount; i6++) {
                ConstraintWidget constraintWidget = Flow.this.mDisplayedWidgets[this.mStartIndex + i6];
                if (this.mOrientation == 0) {
                    if (constraintWidget != null && constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultWidth == 0) {
                        Flow.this.e(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, i5, constraintWidget.getVerticalDimensionBehaviour(), constraintWidget.getHeight());
                    }
                } else if (constraintWidget != null && constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultHeight == 0) {
                    Flow.this.e(constraintWidget, constraintWidget.getHorizontalDimensionBehaviour(), constraintWidget.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, i5);
                }
            }
            recomputeDimensions();
        }

        public void setStartIndex(int i2) {
            this.mStartIndex = i2;
        }

        public void setup(int i2, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, ConstraintAnchor constraintAnchor3, ConstraintAnchor constraintAnchor4, int i3, int i4, int i5, int i6, int i7) {
            this.mOrientation = i2;
            this.mLeft = constraintAnchor;
            this.mTop = constraintAnchor2;
            this.mRight = constraintAnchor3;
            this.mBottom = constraintAnchor4;
            this.mPaddingLeft = i3;
            this.mPaddingTop = i4;
            this.mPaddingRight = i5;
            this.mPaddingBottom = i6;
            this.mMax = i7;
        }
    }

    private void createAlignedConstraints(boolean z2) {
        ConstraintWidget constraintWidget;
        float f2;
        int i2;
        if (this.mAlignedDimensions == null || this.mAlignedBiggestElementsInCols == null || this.mAlignedBiggestElementsInRows == null) {
            return;
        }
        for (int i3 = 0; i3 < this.mDisplayedWidgetsCount; i3++) {
            this.mDisplayedWidgets[i3].resetAnchors();
        }
        int[] iArr = this.mAlignedDimensions;
        int i4 = iArr[0];
        int i5 = iArr[1];
        float f3 = this.mHorizontalBias;
        ConstraintWidget constraintWidget2 = null;
        int i6 = 0;
        while (i6 < i4) {
            if (z2) {
                i2 = (i4 - i6) - 1;
                f2 = 1.0f - this.mHorizontalBias;
            } else {
                f2 = f3;
                i2 = i6;
            }
            ConstraintWidget constraintWidget3 = this.mAlignedBiggestElementsInCols[i2];
            if (constraintWidget3 != null && constraintWidget3.getVisibility() != 8) {
                if (i6 == 0) {
                    constraintWidget3.connect(constraintWidget3.mLeft, this.mLeft, getPaddingLeft());
                    constraintWidget3.setHorizontalChainStyle(this.mHorizontalStyle);
                    constraintWidget3.setHorizontalBiasPercent(f2);
                }
                if (i6 == i4 - 1) {
                    constraintWidget3.connect(constraintWidget3.mRight, this.mRight, getPaddingRight());
                }
                if (i6 > 0 && constraintWidget2 != null) {
                    constraintWidget3.connect(constraintWidget3.mLeft, constraintWidget2.mRight, this.mHorizontalGap);
                    constraintWidget2.connect(constraintWidget2.mRight, constraintWidget3.mLeft, 0);
                }
                constraintWidget2 = constraintWidget3;
            }
            i6++;
            f3 = f2;
        }
        for (int i7 = 0; i7 < i5; i7++) {
            ConstraintWidget constraintWidget4 = this.mAlignedBiggestElementsInRows[i7];
            if (constraintWidget4 != null && constraintWidget4.getVisibility() != 8) {
                if (i7 == 0) {
                    constraintWidget4.connect(constraintWidget4.mTop, this.mTop, getPaddingTop());
                    constraintWidget4.setVerticalChainStyle(this.mVerticalStyle);
                    constraintWidget4.setVerticalBiasPercent(this.mVerticalBias);
                }
                if (i7 == i5 - 1) {
                    constraintWidget4.connect(constraintWidget4.mBottom, this.mBottom, getPaddingBottom());
                }
                if (i7 > 0 && constraintWidget2 != null) {
                    constraintWidget4.connect(constraintWidget4.mTop, constraintWidget2.mBottom, this.mVerticalGap);
                    constraintWidget2.connect(constraintWidget2.mBottom, constraintWidget4.mTop, 0);
                }
                constraintWidget2 = constraintWidget4;
            }
        }
        for (int i8 = 0; i8 < i4; i8++) {
            for (int i9 = 0; i9 < i5; i9++) {
                int i10 = (i9 * i4) + i8;
                if (this.mOrientation == 1) {
                    i10 = (i8 * i5) + i9;
                }
                ConstraintWidget[] constraintWidgetArr = this.mDisplayedWidgets;
                if (i10 < constraintWidgetArr.length && (constraintWidget = constraintWidgetArr[i10]) != null && constraintWidget.getVisibility() != 8) {
                    ConstraintWidget constraintWidget5 = this.mAlignedBiggestElementsInCols[i8];
                    ConstraintWidget constraintWidget6 = this.mAlignedBiggestElementsInRows[i9];
                    if (constraintWidget != constraintWidget5) {
                        constraintWidget.connect(constraintWidget.mLeft, constraintWidget5.mLeft, 0);
                        constraintWidget.connect(constraintWidget.mRight, constraintWidget5.mRight, 0);
                    }
                    if (constraintWidget != constraintWidget6) {
                        constraintWidget.connect(constraintWidget.mTop, constraintWidget6.mTop, 0);
                        constraintWidget.connect(constraintWidget.mBottom, constraintWidget6.mBottom, 0);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getWidgetHeight(ConstraintWidget constraintWidget, int i2) {
        if (constraintWidget == null) {
            return 0;
        }
        if (constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            int i3 = constraintWidget.mMatchConstraintDefaultHeight;
            if (i3 == 0) {
                return 0;
            }
            if (i3 == 2) {
                int i4 = (int) (constraintWidget.mMatchConstraintPercentHeight * i2);
                if (i4 != constraintWidget.getHeight()) {
                    constraintWidget.setMeasureRequested(true);
                    e(constraintWidget, constraintWidget.getHorizontalDimensionBehaviour(), constraintWidget.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, i4);
                }
                return i4;
            }
            if (i3 == 1) {
                return constraintWidget.getHeight();
            }
            if (i3 == 3) {
                return (int) ((constraintWidget.getWidth() * constraintWidget.mDimensionRatio) + 0.5f);
            }
        }
        return constraintWidget.getHeight();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getWidgetWidth(ConstraintWidget constraintWidget, int i2) {
        if (constraintWidget == null) {
            return 0;
        }
        if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            int i3 = constraintWidget.mMatchConstraintDefaultWidth;
            if (i3 == 0) {
                return 0;
            }
            if (i3 == 2) {
                int i4 = (int) (constraintWidget.mMatchConstraintPercentWidth * i2);
                if (i4 != constraintWidget.getWidth()) {
                    constraintWidget.setMeasureRequested(true);
                    e(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, i4, constraintWidget.getVerticalDimensionBehaviour(), constraintWidget.getHeight());
                }
                return i4;
            }
            if (i3 == 1) {
                return constraintWidget.getWidth();
            }
            if (i3 == 3) {
                return (int) ((constraintWidget.getHeight() * constraintWidget.mDimensionRatio) + 0.5f);
            }
        }
        return constraintWidget.getWidth();
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x005e  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:105:0x010d -> B:42:0x0059). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:106:0x010f -> B:42:0x0059). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:108:0x0115 -> B:42:0x0059). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:109:0x0117 -> B:42:0x0059). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void measureAligned(androidx.constraintlayout.core.widgets.ConstraintWidget[] r11, int r12, int r13, int r14, int[] r15) {
        /*
            Method dump skipped, instructions count: 292
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.Flow.measureAligned(androidx.constraintlayout.core.widgets.ConstraintWidget[], int, int, int, int[]):void");
    }

    private void measureChainWrap(ConstraintWidget[] constraintWidgetArr, int i2, int i3, int i4, int[] iArr) {
        int i5;
        int i6;
        int i7;
        ConstraintAnchor constraintAnchor;
        int paddingRight;
        ConstraintAnchor constraintAnchor2;
        int paddingBottom;
        int i8;
        if (i2 == 0) {
            return;
        }
        this.mChainList.clear();
        WidgetsList widgetsList = new WidgetsList(i3, this.mLeft, this.mTop, this.mRight, this.mBottom, i4);
        this.mChainList.add(widgetsList);
        if (i3 == 0) {
            i5 = 0;
            int i9 = 0;
            int i10 = 0;
            while (i10 < i2) {
                ConstraintWidget constraintWidget = constraintWidgetArr[i10];
                int widgetWidth = getWidgetWidth(constraintWidget, i4);
                if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    i5++;
                }
                int i11 = i5;
                boolean z2 = (i9 == i4 || (this.mHorizontalGap + i9) + widgetWidth > i4) && widgetsList.biggest != null;
                if (!z2 && i10 > 0 && (i8 = this.mMaxElementsWrap) > 0 && i10 % i8 == 0) {
                    z2 = true;
                }
                if (z2) {
                    widgetsList = new WidgetsList(i3, this.mLeft, this.mTop, this.mRight, this.mBottom, i4);
                    widgetsList.setStartIndex(i10);
                    this.mChainList.add(widgetsList);
                } else {
                    if (i10 > 0) {
                        i9 += this.mHorizontalGap + widgetWidth;
                    }
                    widgetsList.add(constraintWidget);
                    i10++;
                    i5 = i11;
                }
                i9 = widgetWidth;
                widgetsList.add(constraintWidget);
                i10++;
                i5 = i11;
            }
        } else {
            i5 = 0;
            int i12 = 0;
            int i13 = 0;
            while (i13 < i2) {
                ConstraintWidget constraintWidget2 = constraintWidgetArr[i13];
                int widgetHeight = getWidgetHeight(constraintWidget2, i4);
                if (constraintWidget2.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    i5++;
                }
                int i14 = i5;
                boolean z3 = (i12 == i4 || (this.mVerticalGap + i12) + widgetHeight > i4) && widgetsList.biggest != null;
                if (!z3 && i13 > 0 && (i6 = this.mMaxElementsWrap) > 0 && i13 % i6 == 0) {
                    z3 = true;
                }
                if (z3) {
                    widgetsList = new WidgetsList(i3, this.mLeft, this.mTop, this.mRight, this.mBottom, i4);
                    widgetsList.setStartIndex(i13);
                    this.mChainList.add(widgetsList);
                } else {
                    if (i13 > 0) {
                        i12 += this.mVerticalGap + widgetHeight;
                    }
                    widgetsList.add(constraintWidget2);
                    i13++;
                    i5 = i14;
                }
                i12 = widgetHeight;
                widgetsList.add(constraintWidget2);
                i13++;
                i5 = i14;
            }
        }
        int size = this.mChainList.size();
        ConstraintAnchor constraintAnchor3 = this.mLeft;
        ConstraintAnchor constraintAnchor4 = this.mTop;
        ConstraintAnchor constraintAnchor5 = this.mRight;
        ConstraintAnchor constraintAnchor6 = this.mBottom;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight2 = getPaddingRight();
        int paddingBottom2 = getPaddingBottom();
        ConstraintWidget.DimensionBehaviour horizontalDimensionBehaviour = getHorizontalDimensionBehaviour();
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        boolean z4 = horizontalDimensionBehaviour == dimensionBehaviour || getVerticalDimensionBehaviour() == dimensionBehaviour;
        if (i5 > 0 && z4) {
            for (int i15 = 0; i15 < size; i15++) {
                WidgetsList widgetsList2 = this.mChainList.get(i15);
                if (i3 == 0) {
                    widgetsList2.measureMatchConstraints(i4 - widgetsList2.getWidth());
                } else {
                    widgetsList2.measureMatchConstraints(i4 - widgetsList2.getHeight());
                }
            }
        }
        int i16 = paddingTop;
        int i17 = paddingRight2;
        int height = 0;
        int width = 0;
        int i18 = 0;
        int i19 = paddingLeft;
        ConstraintAnchor constraintAnchor7 = constraintAnchor4;
        ConstraintAnchor constraintAnchor8 = constraintAnchor3;
        int i20 = paddingBottom2;
        while (i18 < size) {
            WidgetsList widgetsList3 = this.mChainList.get(i18);
            if (i3 == 0) {
                if (i18 < size - 1) {
                    constraintAnchor2 = this.mChainList.get(i18 + 1).biggest.mTop;
                    paddingBottom = 0;
                } else {
                    constraintAnchor2 = this.mBottom;
                    paddingBottom = getPaddingBottom();
                }
                ConstraintAnchor constraintAnchor9 = widgetsList3.biggest.mBottom;
                ConstraintAnchor constraintAnchor10 = constraintAnchor8;
                ConstraintAnchor constraintAnchor11 = constraintAnchor8;
                int i21 = height;
                ConstraintAnchor constraintAnchor12 = constraintAnchor7;
                int i22 = width;
                ConstraintAnchor constraintAnchor13 = constraintAnchor5;
                ConstraintAnchor constraintAnchor14 = constraintAnchor5;
                i7 = i18;
                widgetsList3.setup(i3, constraintAnchor10, constraintAnchor12, constraintAnchor13, constraintAnchor2, i19, i16, i17, paddingBottom, i4);
                int iMax = Math.max(i22, widgetsList3.getWidth());
                height = i21 + widgetsList3.getHeight();
                if (i7 > 0) {
                    height += this.mVerticalGap;
                }
                constraintAnchor8 = constraintAnchor11;
                width = iMax;
                i16 = 0;
                constraintAnchor7 = constraintAnchor9;
                constraintAnchor = constraintAnchor14;
                int i23 = paddingBottom;
                constraintAnchor6 = constraintAnchor2;
                i20 = i23;
            } else {
                ConstraintAnchor constraintAnchor15 = constraintAnchor8;
                int i24 = height;
                int i25 = width;
                i7 = i18;
                if (i7 < size - 1) {
                    constraintAnchor = this.mChainList.get(i7 + 1).biggest.mLeft;
                    paddingRight = 0;
                } else {
                    constraintAnchor = this.mRight;
                    paddingRight = getPaddingRight();
                }
                ConstraintAnchor constraintAnchor16 = widgetsList3.biggest.mRight;
                widgetsList3.setup(i3, constraintAnchor15, constraintAnchor7, constraintAnchor, constraintAnchor6, i19, i16, paddingRight, i20, i4);
                width = i25 + widgetsList3.getWidth();
                int iMax2 = Math.max(i24, widgetsList3.getHeight());
                if (i7 > 0) {
                    width += this.mHorizontalGap;
                }
                height = iMax2;
                i19 = 0;
                i17 = paddingRight;
                constraintAnchor8 = constraintAnchor16;
            }
            i18 = i7 + 1;
            constraintAnchor5 = constraintAnchor;
        }
        iArr[0] = width;
        iArr[1] = height;
    }

    private void measureChainWrap_new(ConstraintWidget[] constraintWidgetArr, int i2, int i3, int i4, int[] iArr) {
        int i5;
        int i6;
        int i7;
        ConstraintAnchor constraintAnchor;
        int paddingRight;
        ConstraintAnchor constraintAnchor2;
        int paddingBottom;
        int i8;
        if (i2 == 0) {
            return;
        }
        this.mChainList.clear();
        WidgetsList widgetsList = new WidgetsList(i3, this.mLeft, this.mTop, this.mRight, this.mBottom, i4);
        this.mChainList.add(widgetsList);
        if (i3 == 0) {
            int i9 = 0;
            i5 = 0;
            int i10 = 0;
            int i11 = 0;
            while (i11 < i2) {
                int i12 = i9 + 1;
                ConstraintWidget constraintWidget = constraintWidgetArr[i11];
                int widgetWidth = getWidgetWidth(constraintWidget, i4);
                if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    i5++;
                }
                int i13 = i5;
                boolean z2 = (i10 == i4 || (this.mHorizontalGap + i10) + widgetWidth > i4) && widgetsList.biggest != null;
                if (!z2 && i11 > 0 && (i8 = this.mMaxElementsWrap) > 0 && i12 > i8) {
                    z2 = true;
                }
                if (z2) {
                    widgetsList = new WidgetsList(i3, this.mLeft, this.mTop, this.mRight, this.mBottom, i4);
                    widgetsList.setStartIndex(i11);
                    this.mChainList.add(widgetsList);
                    i9 = i12;
                    i10 = widgetWidth;
                } else {
                    i10 = i11 > 0 ? i10 + this.mHorizontalGap + widgetWidth : widgetWidth;
                    i9 = 0;
                }
                widgetsList.add(constraintWidget);
                i11++;
                i5 = i13;
            }
        } else {
            int i14 = 0;
            i5 = 0;
            int i15 = 0;
            while (i15 < i2) {
                ConstraintWidget constraintWidget2 = constraintWidgetArr[i15];
                int widgetHeight = getWidgetHeight(constraintWidget2, i4);
                if (constraintWidget2.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    i5++;
                }
                int i16 = i5;
                boolean z3 = (i14 == i4 || (this.mVerticalGap + i14) + widgetHeight > i4) && widgetsList.biggest != null;
                if (!z3 && i15 > 0 && (i6 = this.mMaxElementsWrap) > 0 && i6 < 0) {
                    z3 = true;
                }
                if (z3) {
                    widgetsList = new WidgetsList(i3, this.mLeft, this.mTop, this.mRight, this.mBottom, i4);
                    widgetsList.setStartIndex(i15);
                    this.mChainList.add(widgetsList);
                } else {
                    if (i15 > 0) {
                        i14 += this.mVerticalGap + widgetHeight;
                    }
                    widgetsList.add(constraintWidget2);
                    i15++;
                    i5 = i16;
                }
                i14 = widgetHeight;
                widgetsList.add(constraintWidget2);
                i15++;
                i5 = i16;
            }
        }
        int size = this.mChainList.size();
        ConstraintAnchor constraintAnchor3 = this.mLeft;
        ConstraintAnchor constraintAnchor4 = this.mTop;
        ConstraintAnchor constraintAnchor5 = this.mRight;
        ConstraintAnchor constraintAnchor6 = this.mBottom;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight2 = getPaddingRight();
        int paddingBottom2 = getPaddingBottom();
        ConstraintWidget.DimensionBehaviour horizontalDimensionBehaviour = getHorizontalDimensionBehaviour();
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        boolean z4 = horizontalDimensionBehaviour == dimensionBehaviour || getVerticalDimensionBehaviour() == dimensionBehaviour;
        if (i5 > 0 && z4) {
            for (int i17 = 0; i17 < size; i17++) {
                WidgetsList widgetsList2 = this.mChainList.get(i17);
                if (i3 == 0) {
                    widgetsList2.measureMatchConstraints(i4 - widgetsList2.getWidth());
                } else {
                    widgetsList2.measureMatchConstraints(i4 - widgetsList2.getHeight());
                }
            }
        }
        int i18 = paddingTop;
        int i19 = paddingRight2;
        int height = 0;
        int width = 0;
        int i20 = 0;
        int i21 = paddingLeft;
        ConstraintAnchor constraintAnchor7 = constraintAnchor4;
        ConstraintAnchor constraintAnchor8 = constraintAnchor3;
        int i22 = paddingBottom2;
        while (i20 < size) {
            WidgetsList widgetsList3 = this.mChainList.get(i20);
            if (i3 == 0) {
                if (i20 < size - 1) {
                    constraintAnchor2 = this.mChainList.get(i20 + 1).biggest.mTop;
                    paddingBottom = 0;
                } else {
                    constraintAnchor2 = this.mBottom;
                    paddingBottom = getPaddingBottom();
                }
                ConstraintAnchor constraintAnchor9 = widgetsList3.biggest.mBottom;
                ConstraintAnchor constraintAnchor10 = constraintAnchor8;
                ConstraintAnchor constraintAnchor11 = constraintAnchor8;
                int i23 = height;
                ConstraintAnchor constraintAnchor12 = constraintAnchor7;
                int i24 = width;
                ConstraintAnchor constraintAnchor13 = constraintAnchor5;
                ConstraintAnchor constraintAnchor14 = constraintAnchor5;
                i7 = i20;
                widgetsList3.setup(i3, constraintAnchor10, constraintAnchor12, constraintAnchor13, constraintAnchor2, i21, i18, i19, paddingBottom, i4);
                int iMax = Math.max(i24, widgetsList3.getWidth());
                height = i23 + widgetsList3.getHeight();
                if (i7 > 0) {
                    height += this.mVerticalGap;
                }
                constraintAnchor8 = constraintAnchor11;
                width = iMax;
                i18 = 0;
                constraintAnchor7 = constraintAnchor9;
                constraintAnchor = constraintAnchor14;
                int i25 = paddingBottom;
                constraintAnchor6 = constraintAnchor2;
                i22 = i25;
            } else {
                ConstraintAnchor constraintAnchor15 = constraintAnchor8;
                int i26 = height;
                int i27 = width;
                i7 = i20;
                if (i7 < size - 1) {
                    constraintAnchor = this.mChainList.get(i7 + 1).biggest.mLeft;
                    paddingRight = 0;
                } else {
                    constraintAnchor = this.mRight;
                    paddingRight = getPaddingRight();
                }
                ConstraintAnchor constraintAnchor16 = widgetsList3.biggest.mRight;
                widgetsList3.setup(i3, constraintAnchor15, constraintAnchor7, constraintAnchor, constraintAnchor6, i21, i18, paddingRight, i22, i4);
                width = i27 + widgetsList3.getWidth();
                int iMax2 = Math.max(i26, widgetsList3.getHeight());
                if (i7 > 0) {
                    width += this.mHorizontalGap;
                }
                height = iMax2;
                i21 = 0;
                i19 = paddingRight;
                constraintAnchor8 = constraintAnchor16;
            }
            i20 = i7 + 1;
            constraintAnchor5 = constraintAnchor;
        }
        iArr[0] = width;
        iArr[1] = height;
    }

    private void measureNoWrap(ConstraintWidget[] constraintWidgetArr, int i2, int i3, int i4, int[] iArr) {
        WidgetsList widgetsList;
        if (i2 == 0) {
            return;
        }
        if (this.mChainList.size() == 0) {
            widgetsList = new WidgetsList(i3, this.mLeft, this.mTop, this.mRight, this.mBottom, i4);
            this.mChainList.add(widgetsList);
        } else {
            WidgetsList widgetsList2 = this.mChainList.get(0);
            widgetsList2.clear();
            widgetsList = widgetsList2;
            widgetsList.setup(i3, this.mLeft, this.mTop, this.mRight, this.mBottom, getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom(), i4);
        }
        for (int i5 = 0; i5 < i2; i5++) {
            widgetsList.add(constraintWidgetArr[i5]);
        }
        iArr[0] = widgetsList.getWidth();
        iArr[1] = widgetsList.getHeight();
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public void addToSolver(LinearSystem linearSystem, boolean z2) {
        super.addToSolver(linearSystem, z2);
        boolean z3 = getParent() != null && ((ConstraintWidgetContainer) getParent()).isRtl();
        int i2 = this.mWrapMode;
        if (i2 != 0) {
            if (i2 == 1) {
                int size = this.mChainList.size();
                int i3 = 0;
                while (i3 < size) {
                    this.mChainList.get(i3).createConstraints(z3, i3, i3 == size + (-1));
                    i3++;
                }
            } else if (i2 == 2) {
                createAlignedConstraints(z3);
            } else if (i2 == 3) {
                int size2 = this.mChainList.size();
                int i4 = 0;
                while (i4 < size2) {
                    this.mChainList.get(i4).createConstraints(z3, i4, i4 == size2 + (-1));
                    i4++;
                }
            }
        } else if (this.mChainList.size() > 0) {
            this.mChainList.get(0).createConstraints(z3, 0, true);
        }
        g(false);
    }

    @Override // androidx.constraintlayout.core.widgets.HelperWidget, androidx.constraintlayout.core.widgets.ConstraintWidget
    public void copy(ConstraintWidget constraintWidget, HashMap<ConstraintWidget, ConstraintWidget> map) {
        super.copy(constraintWidget, map);
        Flow flow = (Flow) constraintWidget;
        this.mHorizontalStyle = flow.mHorizontalStyle;
        this.mVerticalStyle = flow.mVerticalStyle;
        this.mFirstHorizontalStyle = flow.mFirstHorizontalStyle;
        this.mFirstVerticalStyle = flow.mFirstVerticalStyle;
        this.mLastHorizontalStyle = flow.mLastHorizontalStyle;
        this.mLastVerticalStyle = flow.mLastVerticalStyle;
        this.mHorizontalBias = flow.mHorizontalBias;
        this.mVerticalBias = flow.mVerticalBias;
        this.mFirstHorizontalBias = flow.mFirstHorizontalBias;
        this.mFirstVerticalBias = flow.mFirstVerticalBias;
        this.mLastHorizontalBias = flow.mLastHorizontalBias;
        this.mLastVerticalBias = flow.mLastVerticalBias;
        this.mHorizontalGap = flow.mHorizontalGap;
        this.mVerticalGap = flow.mVerticalGap;
        this.mHorizontalAlign = flow.mHorizontalAlign;
        this.mVerticalAlign = flow.mVerticalAlign;
        this.mWrapMode = flow.mWrapMode;
        this.mMaxElementsWrap = flow.mMaxElementsWrap;
        this.mOrientation = flow.mOrientation;
    }

    public float getMaxElementsWrap() {
        return this.mMaxElementsWrap;
    }

    @Override // androidx.constraintlayout.core.widgets.VirtualLayout
    public void measure(int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int[] iArr;
        boolean z2;
        if (this.mWidgetsCount > 0 && !f()) {
            setMeasure(0, 0);
            g(false);
            return;
        }
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int[] iArr2 = new int[2];
        int i8 = (i3 - paddingLeft) - paddingRight;
        int i9 = this.mOrientation;
        if (i9 == 1) {
            i8 = (i5 - paddingTop) - paddingBottom;
        }
        int i10 = i8;
        if (i9 == 0) {
            if (this.mHorizontalStyle == -1) {
                this.mHorizontalStyle = 0;
            }
            if (this.mVerticalStyle == -1) {
                this.mVerticalStyle = 0;
            }
        } else {
            if (this.mHorizontalStyle == -1) {
                this.mHorizontalStyle = 0;
            }
            if (this.mVerticalStyle == -1) {
                this.mVerticalStyle = 0;
            }
        }
        ConstraintWidget[] constraintWidgetArr = this.mWidgets;
        int i11 = 0;
        int i12 = 0;
        while (true) {
            i6 = this.mWidgetsCount;
            if (i11 >= i6) {
                break;
            }
            if (this.mWidgets[i11].getVisibility() == 8) {
                i12++;
            }
            i11++;
        }
        if (i12 > 0) {
            constraintWidgetArr = new ConstraintWidget[i6 - i12];
            int i13 = 0;
            for (int i14 = 0; i14 < this.mWidgetsCount; i14++) {
                ConstraintWidget constraintWidget = this.mWidgets[i14];
                if (constraintWidget.getVisibility() != 8) {
                    constraintWidgetArr[i13] = constraintWidget;
                    i13++;
                }
            }
            i7 = i13;
        } else {
            i7 = i6;
        }
        this.mDisplayedWidgets = constraintWidgetArr;
        this.mDisplayedWidgetsCount = i7;
        int i15 = this.mWrapMode;
        if (i15 == 0) {
            iArr = iArr2;
            z2 = true;
            measureNoWrap(constraintWidgetArr, i7, this.mOrientation, i10, iArr2);
        } else if (i15 == 1) {
            z2 = true;
            iArr = iArr2;
            measureChainWrap(constraintWidgetArr, i7, this.mOrientation, i10, iArr2);
        } else if (i15 == 2) {
            z2 = true;
            iArr = iArr2;
            measureAligned(constraintWidgetArr, i7, this.mOrientation, i10, iArr2);
        } else if (i15 != 3) {
            z2 = true;
            iArr = iArr2;
        } else {
            z2 = true;
            iArr = iArr2;
            measureChainWrap_new(constraintWidgetArr, i7, this.mOrientation, i10, iArr2);
        }
        int iMin = iArr[0] + paddingLeft + paddingRight;
        int iMin2 = iArr[z2 ? 1 : 0] + paddingTop + paddingBottom;
        if (i2 == 1073741824) {
            iMin = i3;
        } else if (i2 == Integer.MIN_VALUE) {
            iMin = Math.min(iMin, i3);
        } else if (i2 != 0) {
            iMin = 0;
        }
        if (i4 == 1073741824) {
            iMin2 = i5;
        } else if (i4 == Integer.MIN_VALUE) {
            iMin2 = Math.min(iMin2, i5);
        } else if (i4 != 0) {
            iMin2 = 0;
        }
        setMeasure(iMin, iMin2);
        setWidth(iMin);
        setHeight(iMin2);
        if (this.mWidgetsCount <= 0) {
            z2 = false;
        }
        g(z2);
    }

    public void setFirstHorizontalBias(float f2) {
        this.mFirstHorizontalBias = f2;
    }

    public void setFirstHorizontalStyle(int i2) {
        this.mFirstHorizontalStyle = i2;
    }

    public void setFirstVerticalBias(float f2) {
        this.mFirstVerticalBias = f2;
    }

    public void setFirstVerticalStyle(int i2) {
        this.mFirstVerticalStyle = i2;
    }

    public void setHorizontalAlign(int i2) {
        this.mHorizontalAlign = i2;
    }

    public void setHorizontalBias(float f2) {
        this.mHorizontalBias = f2;
    }

    public void setHorizontalGap(int i2) {
        this.mHorizontalGap = i2;
    }

    public void setHorizontalStyle(int i2) {
        this.mHorizontalStyle = i2;
    }

    public void setLastHorizontalBias(float f2) {
        this.mLastHorizontalBias = f2;
    }

    public void setLastHorizontalStyle(int i2) {
        this.mLastHorizontalStyle = i2;
    }

    public void setLastVerticalBias(float f2) {
        this.mLastVerticalBias = f2;
    }

    public void setLastVerticalStyle(int i2) {
        this.mLastVerticalStyle = i2;
    }

    public void setMaxElementsWrap(int i2) {
        this.mMaxElementsWrap = i2;
    }

    public void setOrientation(int i2) {
        this.mOrientation = i2;
    }

    public void setVerticalAlign(int i2) {
        this.mVerticalAlign = i2;
    }

    public void setVerticalBias(float f2) {
        this.mVerticalBias = f2;
    }

    public void setVerticalGap(int i2) {
        this.mVerticalGap = i2;
    }

    public void setVerticalStyle(int i2) {
        this.mVerticalStyle = i2;
    }

    public void setWrapMode(int i2) {
        this.mWrapMode = i2;
    }
}
