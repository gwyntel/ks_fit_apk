package androidx.constraintlayout.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.media3.extractor.text.ttml.TtmlNode;
import com.facebook.appevents.internal.ViewHierarchyConstants;

/* loaded from: classes.dex */
public class ConstraintProperties {
    public static final int BASELINE = 5;
    public static final int BOTTOM = 4;
    public static final int END = 7;
    public static final int LEFT = 1;
    public static final int MATCH_CONSTRAINT = 0;
    public static final int MATCH_CONSTRAINT_SPREAD = 0;
    public static final int MATCH_CONSTRAINT_WRAP = 1;
    public static final int PARENT_ID = 0;
    public static final int RIGHT = 2;
    public static final int START = 6;
    public static final int TOP = 3;
    public static final int UNSET = -1;
    public static final int WRAP_CONTENT = -2;

    /* renamed from: a, reason: collision with root package name */
    ConstraintLayout.LayoutParams f3279a;

    /* renamed from: b, reason: collision with root package name */
    View f3280b;

    public ConstraintProperties(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof ConstraintLayout.LayoutParams)) {
            throw new RuntimeException("Only children of ConstraintLayout.LayoutParams supported");
        }
        this.f3279a = (ConstraintLayout.LayoutParams) layoutParams;
        this.f3280b = view;
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

    public ConstraintProperties addToHorizontalChain(int i2, int i3) {
        connect(1, i2, i2 == 0 ? 1 : 2, 0);
        connect(2, i3, i3 == 0 ? 2 : 1, 0);
        if (i2 != 0) {
            new ConstraintProperties(((ViewGroup) this.f3280b.getParent()).findViewById(i2)).connect(2, this.f3280b.getId(), 1, 0);
        }
        if (i3 != 0) {
            new ConstraintProperties(((ViewGroup) this.f3280b.getParent()).findViewById(i3)).connect(1, this.f3280b.getId(), 2, 0);
        }
        return this;
    }

    public ConstraintProperties addToHorizontalChainRTL(int i2, int i3) {
        connect(6, i2, i2 == 0 ? 6 : 7, 0);
        connect(7, i3, i3 == 0 ? 7 : 6, 0);
        if (i2 != 0) {
            new ConstraintProperties(((ViewGroup) this.f3280b.getParent()).findViewById(i2)).connect(7, this.f3280b.getId(), 6, 0);
        }
        if (i3 != 0) {
            new ConstraintProperties(((ViewGroup) this.f3280b.getParent()).findViewById(i3)).connect(6, this.f3280b.getId(), 7, 0);
        }
        return this;
    }

    public ConstraintProperties addToVerticalChain(int i2, int i3) {
        connect(3, i2, i2 == 0 ? 3 : 4, 0);
        connect(4, i3, i3 == 0 ? 4 : 3, 0);
        if (i2 != 0) {
            new ConstraintProperties(((ViewGroup) this.f3280b.getParent()).findViewById(i2)).connect(4, this.f3280b.getId(), 3, 0);
        }
        if (i3 != 0) {
            new ConstraintProperties(((ViewGroup) this.f3280b.getParent()).findViewById(i3)).connect(3, this.f3280b.getId(), 4, 0);
        }
        return this;
    }

    public ConstraintProperties alpha(float f2) {
        this.f3280b.setAlpha(f2);
        return this;
    }

    public void apply() {
    }

    public ConstraintProperties center(int i2, int i3, int i4, int i5, int i6, int i7, float f2) {
        if (i4 < 0) {
            throw new IllegalArgumentException("margin must be > 0");
        }
        if (i7 < 0) {
            throw new IllegalArgumentException("margin must be > 0");
        }
        if (f2 <= 0.0f || f2 > 1.0f) {
            throw new IllegalArgumentException("bias must be between 0 and 1 inclusive");
        }
        if (i3 == 1 || i3 == 2) {
            connect(1, i2, i3, i4);
            connect(2, i5, i6, i7);
            this.f3279a.horizontalBias = f2;
        } else if (i3 == 6 || i3 == 7) {
            connect(6, i2, i3, i4);
            connect(7, i5, i6, i7);
            this.f3279a.horizontalBias = f2;
        } else {
            connect(3, i2, i3, i4);
            connect(4, i5, i6, i7);
            this.f3279a.verticalBias = f2;
        }
        return this;
    }

    public ConstraintProperties centerHorizontally(int i2, int i3, int i4, int i5, int i6, int i7, float f2) {
        connect(1, i2, i3, i4);
        connect(2, i5, i6, i7);
        this.f3279a.horizontalBias = f2;
        return this;
    }

    public ConstraintProperties centerHorizontallyRtl(int i2, int i3, int i4, int i5, int i6, int i7, float f2) {
        connect(6, i2, i3, i4);
        connect(7, i5, i6, i7);
        this.f3279a.horizontalBias = f2;
        return this;
    }

    public ConstraintProperties centerVertically(int i2, int i3, int i4, int i5, int i6, int i7, float f2) {
        connect(3, i2, i3, i4);
        connect(4, i5, i6, i7);
        this.f3279a.verticalBias = f2;
        return this;
    }

    public ConstraintProperties connect(int i2, int i3, int i4, int i5) {
        switch (i2) {
            case 1:
                if (i4 == 1) {
                    ConstraintLayout.LayoutParams layoutParams = this.f3279a;
                    layoutParams.leftToLeft = i3;
                    layoutParams.leftToRight = -1;
                } else {
                    if (i4 != 2) {
                        throw new IllegalArgumentException("Left to " + sideToString(i4) + " undefined");
                    }
                    ConstraintLayout.LayoutParams layoutParams2 = this.f3279a;
                    layoutParams2.leftToRight = i3;
                    layoutParams2.leftToLeft = -1;
                }
                ((ViewGroup.MarginLayoutParams) this.f3279a).leftMargin = i5;
                return this;
            case 2:
                if (i4 == 1) {
                    ConstraintLayout.LayoutParams layoutParams3 = this.f3279a;
                    layoutParams3.rightToLeft = i3;
                    layoutParams3.rightToRight = -1;
                } else {
                    if (i4 != 2) {
                        throw new IllegalArgumentException("right to " + sideToString(i4) + " undefined");
                    }
                    ConstraintLayout.LayoutParams layoutParams4 = this.f3279a;
                    layoutParams4.rightToRight = i3;
                    layoutParams4.rightToLeft = -1;
                }
                ((ViewGroup.MarginLayoutParams) this.f3279a).rightMargin = i5;
                return this;
            case 3:
                if (i4 == 3) {
                    ConstraintLayout.LayoutParams layoutParams5 = this.f3279a;
                    layoutParams5.topToTop = i3;
                    layoutParams5.topToBottom = -1;
                    layoutParams5.baselineToBaseline = -1;
                    layoutParams5.baselineToTop = -1;
                    layoutParams5.baselineToBottom = -1;
                } else {
                    if (i4 != 4) {
                        throw new IllegalArgumentException("right to " + sideToString(i4) + " undefined");
                    }
                    ConstraintLayout.LayoutParams layoutParams6 = this.f3279a;
                    layoutParams6.topToBottom = i3;
                    layoutParams6.topToTop = -1;
                    layoutParams6.baselineToBaseline = -1;
                    layoutParams6.baselineToTop = -1;
                    layoutParams6.baselineToBottom = -1;
                }
                ((ViewGroup.MarginLayoutParams) this.f3279a).topMargin = i5;
                return this;
            case 4:
                if (i4 == 4) {
                    ConstraintLayout.LayoutParams layoutParams7 = this.f3279a;
                    layoutParams7.bottomToBottom = i3;
                    layoutParams7.bottomToTop = -1;
                    layoutParams7.baselineToBaseline = -1;
                    layoutParams7.baselineToTop = -1;
                    layoutParams7.baselineToBottom = -1;
                } else {
                    if (i4 != 3) {
                        throw new IllegalArgumentException("right to " + sideToString(i4) + " undefined");
                    }
                    ConstraintLayout.LayoutParams layoutParams8 = this.f3279a;
                    layoutParams8.bottomToTop = i3;
                    layoutParams8.bottomToBottom = -1;
                    layoutParams8.baselineToBaseline = -1;
                    layoutParams8.baselineToTop = -1;
                    layoutParams8.baselineToBottom = -1;
                }
                ((ViewGroup.MarginLayoutParams) this.f3279a).bottomMargin = i5;
                return this;
            case 5:
                if (i4 == 5) {
                    ConstraintLayout.LayoutParams layoutParams9 = this.f3279a;
                    layoutParams9.baselineToBaseline = i3;
                    layoutParams9.bottomToBottom = -1;
                    layoutParams9.bottomToTop = -1;
                    layoutParams9.topToTop = -1;
                    layoutParams9.topToBottom = -1;
                }
                if (i4 == 3) {
                    ConstraintLayout.LayoutParams layoutParams10 = this.f3279a;
                    layoutParams10.baselineToTop = i3;
                    layoutParams10.bottomToBottom = -1;
                    layoutParams10.bottomToTop = -1;
                    layoutParams10.topToTop = -1;
                    layoutParams10.topToBottom = -1;
                } else {
                    if (i4 != 4) {
                        throw new IllegalArgumentException("right to " + sideToString(i4) + " undefined");
                    }
                    ConstraintLayout.LayoutParams layoutParams11 = this.f3279a;
                    layoutParams11.baselineToBottom = i3;
                    layoutParams11.bottomToBottom = -1;
                    layoutParams11.bottomToTop = -1;
                    layoutParams11.topToTop = -1;
                    layoutParams11.topToBottom = -1;
                }
                this.f3279a.baselineMargin = i5;
                return this;
            case 6:
                if (i4 == 6) {
                    ConstraintLayout.LayoutParams layoutParams12 = this.f3279a;
                    layoutParams12.startToStart = i3;
                    layoutParams12.startToEnd = -1;
                } else {
                    if (i4 != 7) {
                        throw new IllegalArgumentException("right to " + sideToString(i4) + " undefined");
                    }
                    ConstraintLayout.LayoutParams layoutParams13 = this.f3279a;
                    layoutParams13.startToEnd = i3;
                    layoutParams13.startToStart = -1;
                }
                this.f3279a.setMarginStart(i5);
                return this;
            case 7:
                if (i4 == 7) {
                    ConstraintLayout.LayoutParams layoutParams14 = this.f3279a;
                    layoutParams14.endToEnd = i3;
                    layoutParams14.endToStart = -1;
                } else {
                    if (i4 != 6) {
                        throw new IllegalArgumentException("right to " + sideToString(i4) + " undefined");
                    }
                    ConstraintLayout.LayoutParams layoutParams15 = this.f3279a;
                    layoutParams15.endToStart = i3;
                    layoutParams15.endToEnd = -1;
                }
                this.f3279a.setMarginEnd(i5);
                return this;
            default:
                throw new IllegalArgumentException(sideToString(i2) + " to " + sideToString(i4) + " unknown");
        }
    }

    public ConstraintProperties constrainDefaultHeight(int i2) {
        this.f3279a.matchConstraintDefaultHeight = i2;
        return this;
    }

    public ConstraintProperties constrainDefaultWidth(int i2) {
        this.f3279a.matchConstraintDefaultWidth = i2;
        return this;
    }

    public ConstraintProperties constrainHeight(int i2) {
        ((ViewGroup.MarginLayoutParams) this.f3279a).height = i2;
        return this;
    }

    public ConstraintProperties constrainMaxHeight(int i2) {
        this.f3279a.matchConstraintMaxHeight = i2;
        return this;
    }

    public ConstraintProperties constrainMaxWidth(int i2) {
        this.f3279a.matchConstraintMaxWidth = i2;
        return this;
    }

    public ConstraintProperties constrainMinHeight(int i2) {
        this.f3279a.matchConstraintMinHeight = i2;
        return this;
    }

    public ConstraintProperties constrainMinWidth(int i2) {
        this.f3279a.matchConstraintMinWidth = i2;
        return this;
    }

    public ConstraintProperties constrainWidth(int i2) {
        ((ViewGroup.MarginLayoutParams) this.f3279a).width = i2;
        return this;
    }

    public ConstraintProperties dimensionRatio(String str) {
        this.f3279a.dimensionRatio = str;
        return this;
    }

    public ConstraintProperties elevation(float f2) {
        this.f3280b.setElevation(f2);
        return this;
    }

    public ConstraintProperties goneMargin(int i2, int i3) {
        switch (i2) {
            case 1:
                this.f3279a.goneLeftMargin = i3;
                return this;
            case 2:
                this.f3279a.goneRightMargin = i3;
                return this;
            case 3:
                this.f3279a.goneTopMargin = i3;
                return this;
            case 4:
                this.f3279a.goneBottomMargin = i3;
                return this;
            case 5:
                throw new IllegalArgumentException("baseline does not support margins");
            case 6:
                this.f3279a.goneStartMargin = i3;
                return this;
            case 7:
                this.f3279a.goneEndMargin = i3;
                return this;
            default:
                throw new IllegalArgumentException("unknown constraint");
        }
    }

    public ConstraintProperties horizontalBias(float f2) {
        this.f3279a.horizontalBias = f2;
        return this;
    }

    public ConstraintProperties horizontalChainStyle(int i2) {
        this.f3279a.horizontalChainStyle = i2;
        return this;
    }

    public ConstraintProperties horizontalWeight(float f2) {
        this.f3279a.horizontalWeight = f2;
        return this;
    }

    public ConstraintProperties margin(int i2, int i3) {
        switch (i2) {
            case 1:
                ((ViewGroup.MarginLayoutParams) this.f3279a).leftMargin = i3;
                return this;
            case 2:
                ((ViewGroup.MarginLayoutParams) this.f3279a).rightMargin = i3;
                return this;
            case 3:
                ((ViewGroup.MarginLayoutParams) this.f3279a).topMargin = i3;
                return this;
            case 4:
                ((ViewGroup.MarginLayoutParams) this.f3279a).bottomMargin = i3;
                return this;
            case 5:
                throw new IllegalArgumentException("baseline does not support margins");
            case 6:
                this.f3279a.setMarginStart(i3);
                return this;
            case 7:
                this.f3279a.setMarginEnd(i3);
                return this;
            default:
                throw new IllegalArgumentException("unknown constraint");
        }
    }

    public ConstraintProperties removeConstraints(int i2) {
        switch (i2) {
            case 1:
                ConstraintLayout.LayoutParams layoutParams = this.f3279a;
                layoutParams.leftToRight = -1;
                layoutParams.leftToLeft = -1;
                ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = -1;
                layoutParams.goneLeftMargin = Integer.MIN_VALUE;
                return this;
            case 2:
                ConstraintLayout.LayoutParams layoutParams2 = this.f3279a;
                layoutParams2.rightToRight = -1;
                layoutParams2.rightToLeft = -1;
                ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin = -1;
                layoutParams2.goneRightMargin = Integer.MIN_VALUE;
                return this;
            case 3:
                ConstraintLayout.LayoutParams layoutParams3 = this.f3279a;
                layoutParams3.topToBottom = -1;
                layoutParams3.topToTop = -1;
                ((ViewGroup.MarginLayoutParams) layoutParams3).topMargin = -1;
                layoutParams3.goneTopMargin = Integer.MIN_VALUE;
                return this;
            case 4:
                ConstraintLayout.LayoutParams layoutParams4 = this.f3279a;
                layoutParams4.bottomToTop = -1;
                layoutParams4.bottomToBottom = -1;
                ((ViewGroup.MarginLayoutParams) layoutParams4).bottomMargin = -1;
                layoutParams4.goneBottomMargin = Integer.MIN_VALUE;
                return this;
            case 5:
                this.f3279a.baselineToBaseline = -1;
                return this;
            case 6:
                ConstraintLayout.LayoutParams layoutParams5 = this.f3279a;
                layoutParams5.startToEnd = -1;
                layoutParams5.startToStart = -1;
                layoutParams5.setMarginStart(-1);
                this.f3279a.goneStartMargin = Integer.MIN_VALUE;
                return this;
            case 7:
                ConstraintLayout.LayoutParams layoutParams6 = this.f3279a;
                layoutParams6.endToStart = -1;
                layoutParams6.endToEnd = -1;
                layoutParams6.setMarginEnd(-1);
                this.f3279a.goneEndMargin = Integer.MIN_VALUE;
                return this;
            default:
                throw new IllegalArgumentException("unknown constraint");
        }
    }

    public ConstraintProperties removeFromHorizontalChain() {
        ConstraintLayout.LayoutParams layoutParams = this.f3279a;
        int i2 = layoutParams.leftToRight;
        int i3 = layoutParams.rightToLeft;
        if (i2 == -1 && i3 == -1) {
            int i4 = layoutParams.startToEnd;
            int i5 = layoutParams.endToStart;
            if (i4 != -1 || i5 != -1) {
                ConstraintProperties constraintProperties = new ConstraintProperties(((ViewGroup) this.f3280b.getParent()).findViewById(i4));
                ConstraintProperties constraintProperties2 = new ConstraintProperties(((ViewGroup) this.f3280b.getParent()).findViewById(i5));
                ConstraintLayout.LayoutParams layoutParams2 = this.f3279a;
                if (i4 != -1 && i5 != -1) {
                    constraintProperties.connect(7, i5, 6, 0);
                    constraintProperties2.connect(6, i2, 7, 0);
                } else if (i2 != -1 || i5 != -1) {
                    int i6 = layoutParams2.rightToRight;
                    if (i6 != -1) {
                        constraintProperties.connect(7, i6, 7, 0);
                    } else {
                        int i7 = layoutParams2.leftToLeft;
                        if (i7 != -1) {
                            constraintProperties2.connect(6, i7, 6, 0);
                        }
                    }
                }
            }
            removeConstraints(6);
            removeConstraints(7);
        } else {
            ConstraintProperties constraintProperties3 = new ConstraintProperties(((ViewGroup) this.f3280b.getParent()).findViewById(i2));
            ConstraintProperties constraintProperties4 = new ConstraintProperties(((ViewGroup) this.f3280b.getParent()).findViewById(i3));
            ConstraintLayout.LayoutParams layoutParams3 = this.f3279a;
            if (i2 != -1 && i3 != -1) {
                constraintProperties3.connect(2, i3, 1, 0);
                constraintProperties4.connect(1, i2, 2, 0);
            } else if (i2 != -1 || i3 != -1) {
                int i8 = layoutParams3.rightToRight;
                if (i8 != -1) {
                    constraintProperties3.connect(2, i8, 2, 0);
                } else {
                    int i9 = layoutParams3.leftToLeft;
                    if (i9 != -1) {
                        constraintProperties4.connect(1, i9, 1, 0);
                    }
                }
            }
            removeConstraints(1);
            removeConstraints(2);
        }
        return this;
    }

    public ConstraintProperties removeFromVerticalChain() {
        ConstraintLayout.LayoutParams layoutParams = this.f3279a;
        int i2 = layoutParams.topToBottom;
        int i3 = layoutParams.bottomToTop;
        if (i2 != -1 || i3 != -1) {
            ConstraintProperties constraintProperties = new ConstraintProperties(((ViewGroup) this.f3280b.getParent()).findViewById(i2));
            ConstraintProperties constraintProperties2 = new ConstraintProperties(((ViewGroup) this.f3280b.getParent()).findViewById(i3));
            ConstraintLayout.LayoutParams layoutParams2 = this.f3279a;
            if (i2 != -1 && i3 != -1) {
                constraintProperties.connect(4, i3, 3, 0);
                constraintProperties2.connect(3, i2, 4, 0);
            } else if (i2 != -1 || i3 != -1) {
                int i4 = layoutParams2.bottomToBottom;
                if (i4 != -1) {
                    constraintProperties.connect(4, i4, 4, 0);
                } else {
                    int i5 = layoutParams2.topToTop;
                    if (i5 != -1) {
                        constraintProperties2.connect(3, i5, 3, 0);
                    }
                }
            }
        }
        removeConstraints(3);
        removeConstraints(4);
        return this;
    }

    public ConstraintProperties rotation(float f2) {
        this.f3280b.setRotation(f2);
        return this;
    }

    public ConstraintProperties rotationX(float f2) {
        this.f3280b.setRotationX(f2);
        return this;
    }

    public ConstraintProperties rotationY(float f2) {
        this.f3280b.setRotationY(f2);
        return this;
    }

    public ConstraintProperties scaleX(float f2) {
        this.f3280b.setScaleY(f2);
        return this;
    }

    public ConstraintProperties scaleY(float f2) {
        return this;
    }

    public ConstraintProperties transformPivot(float f2, float f3) {
        this.f3280b.setPivotX(f2);
        this.f3280b.setPivotY(f3);
        return this;
    }

    public ConstraintProperties transformPivotX(float f2) {
        this.f3280b.setPivotX(f2);
        return this;
    }

    public ConstraintProperties transformPivotY(float f2) {
        this.f3280b.setPivotY(f2);
        return this;
    }

    public ConstraintProperties translation(float f2, float f3) {
        this.f3280b.setTranslationX(f2);
        this.f3280b.setTranslationY(f3);
        return this;
    }

    public ConstraintProperties translationX(float f2) {
        this.f3280b.setTranslationX(f2);
        return this;
    }

    public ConstraintProperties translationY(float f2) {
        this.f3280b.setTranslationY(f2);
        return this;
    }

    public ConstraintProperties translationZ(float f2) {
        this.f3280b.setTranslationZ(f2);
        return this;
    }

    public ConstraintProperties verticalBias(float f2) {
        this.f3279a.verticalBias = f2;
        return this;
    }

    public ConstraintProperties verticalChainStyle(int i2) {
        this.f3279a.verticalChainStyle = i2;
        return this;
    }

    public ConstraintProperties verticalWeight(float f2) {
        this.f3279a.verticalWeight = f2;
        return this;
    }

    public ConstraintProperties visibility(int i2) {
        this.f3280b.setVisibility(i2);
        return this;
    }

    public ConstraintProperties centerHorizontally(int i2) {
        if (i2 == 0) {
            center(0, 1, 0, 0, 2, 0, 0.5f);
        } else {
            center(i2, 2, 0, i2, 1, 0, 0.5f);
        }
        return this;
    }

    public ConstraintProperties centerHorizontallyRtl(int i2) {
        if (i2 == 0) {
            center(0, 6, 0, 0, 7, 0, 0.5f);
        } else {
            center(i2, 7, 0, i2, 6, 0, 0.5f);
        }
        return this;
    }

    public ConstraintProperties centerVertically(int i2) {
        if (i2 == 0) {
            center(0, 3, 0, 0, 4, 0, 0.5f);
        } else {
            center(i2, 4, 0, i2, 3, 0, 0.5f);
        }
        return this;
    }
}
