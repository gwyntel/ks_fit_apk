package androidx.constraintlayout.core.state;

import androidx.constraintlayout.core.widgets.ConstraintWidget;

/* loaded from: classes.dex */
public class Dimension {
    private final int WRAP_CONTENT;

    /* renamed from: a, reason: collision with root package name */
    int f2829a;

    /* renamed from: b, reason: collision with root package name */
    int f2830b;

    /* renamed from: c, reason: collision with root package name */
    float f2831c;

    /* renamed from: d, reason: collision with root package name */
    int f2832d;

    /* renamed from: e, reason: collision with root package name */
    String f2833e;

    /* renamed from: f, reason: collision with root package name */
    Object f2834f;

    /* renamed from: g, reason: collision with root package name */
    boolean f2835g;
    public static final Object FIXED_DIMENSION = new Object();
    public static final Object WRAP_DIMENSION = new Object();
    public static final Object SPREAD_DIMENSION = new Object();
    public static final Object PARENT_DIMENSION = new Object();
    public static final Object PERCENT_DIMENSION = new Object();
    public static final Object RATIO_DIMENSION = new Object();

    public enum Type {
        FIXED,
        WRAP,
        MATCH_PARENT,
        MATCH_CONSTRAINT
    }

    private Dimension() {
        this.WRAP_CONTENT = -2;
        this.f2829a = 0;
        this.f2830b = Integer.MAX_VALUE;
        this.f2831c = 1.0f;
        this.f2832d = 0;
        this.f2833e = null;
        this.f2834f = WRAP_DIMENSION;
        this.f2835g = false;
    }

    public static Dimension Fixed(int i2) {
        Dimension dimension = new Dimension(FIXED_DIMENSION);
        dimension.fixed(i2);
        return dimension;
    }

    public static Dimension Parent() {
        return new Dimension(PARENT_DIMENSION);
    }

    public static Dimension Percent(Object obj, float f2) {
        Dimension dimension = new Dimension(PERCENT_DIMENSION);
        dimension.percent(obj, f2);
        return dimension;
    }

    public static Dimension Ratio(String str) {
        Dimension dimension = new Dimension(RATIO_DIMENSION);
        dimension.ratio(str);
        return dimension;
    }

    public static Dimension Spread() {
        return new Dimension(SPREAD_DIMENSION);
    }

    public static Dimension Suggested(int i2) {
        Dimension dimension = new Dimension();
        dimension.suggested(i2);
        return dimension;
    }

    public static Dimension Wrap() {
        return new Dimension(WRAP_DIMENSION);
    }

    int a() {
        return this.f2832d;
    }

    public void apply(State state, ConstraintWidget constraintWidget, int i2) {
        String str = this.f2833e;
        if (str != null) {
            constraintWidget.setDimensionRatio(str);
        }
        int i3 = 2;
        if (i2 == 0) {
            if (this.f2835g) {
                constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
                Object obj = this.f2834f;
                if (obj == WRAP_DIMENSION) {
                    i3 = 1;
                } else if (obj != PERCENT_DIMENSION) {
                    i3 = 0;
                }
                constraintWidget.setHorizontalMatchStyle(i3, this.f2829a, this.f2830b, this.f2831c);
                return;
            }
            int i4 = this.f2829a;
            if (i4 > 0) {
                constraintWidget.setMinWidth(i4);
            }
            int i5 = this.f2830b;
            if (i5 < Integer.MAX_VALUE) {
                constraintWidget.setMaxWidth(i5);
            }
            Object obj2 = this.f2834f;
            if (obj2 == WRAP_DIMENSION) {
                constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
                return;
            }
            if (obj2 == PARENT_DIMENSION) {
                constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
                return;
            } else {
                if (obj2 == null) {
                    constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                    constraintWidget.setWidth(this.f2832d);
                    return;
                }
                return;
            }
        }
        if (this.f2835g) {
            constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
            Object obj3 = this.f2834f;
            if (obj3 == WRAP_DIMENSION) {
                i3 = 1;
            } else if (obj3 != PERCENT_DIMENSION) {
                i3 = 0;
            }
            constraintWidget.setVerticalMatchStyle(i3, this.f2829a, this.f2830b, this.f2831c);
            return;
        }
        int i6 = this.f2829a;
        if (i6 > 0) {
            constraintWidget.setMinHeight(i6);
        }
        int i7 = this.f2830b;
        if (i7 < Integer.MAX_VALUE) {
            constraintWidget.setMaxHeight(i7);
        }
        Object obj4 = this.f2834f;
        if (obj4 == WRAP_DIMENSION) {
            constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
            return;
        }
        if (obj4 == PARENT_DIMENSION) {
            constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
        } else if (obj4 == null) {
            constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
            constraintWidget.setHeight(this.f2832d);
        }
    }

    public boolean equalsFixedValue(int i2) {
        return this.f2834f == null && this.f2832d == i2;
    }

    public Dimension fixed(Object obj) {
        this.f2834f = obj;
        if (obj instanceof Integer) {
            this.f2832d = ((Integer) obj).intValue();
            this.f2834f = null;
        }
        return this;
    }

    public Dimension max(int i2) {
        if (this.f2830b >= 0) {
            this.f2830b = i2;
        }
        return this;
    }

    public Dimension min(int i2) {
        if (i2 >= 0) {
            this.f2829a = i2;
        }
        return this;
    }

    public Dimension percent(Object obj, float f2) {
        this.f2831c = f2;
        return this;
    }

    public Dimension ratio(String str) {
        this.f2833e = str;
        return this;
    }

    public Dimension suggested(int i2) {
        this.f2835g = true;
        if (i2 >= 0) {
            this.f2830b = i2;
        }
        return this;
    }

    public Dimension min(Object obj) {
        if (obj == WRAP_DIMENSION) {
            this.f2829a = -2;
        }
        return this;
    }

    public static Dimension Fixed(Object obj) {
        Dimension dimension = new Dimension(FIXED_DIMENSION);
        dimension.fixed(obj);
        return dimension;
    }

    public static Dimension Suggested(Object obj) {
        Dimension dimension = new Dimension();
        dimension.suggested(obj);
        return dimension;
    }

    public Dimension max(Object obj) {
        Object obj2 = WRAP_DIMENSION;
        if (obj == obj2 && this.f2835g) {
            this.f2834f = obj2;
            this.f2830b = Integer.MAX_VALUE;
        }
        return this;
    }

    public Dimension suggested(Object obj) {
        this.f2834f = obj;
        this.f2835g = true;
        return this;
    }

    public Dimension fixed(int i2) {
        this.f2834f = null;
        this.f2832d = i2;
        return this;
    }

    private Dimension(Object obj) {
        this.WRAP_CONTENT = -2;
        this.f2829a = 0;
        this.f2830b = Integer.MAX_VALUE;
        this.f2831c = 1.0f;
        this.f2832d = 0;
        this.f2833e = null;
        this.f2835g = false;
        this.f2834f = obj;
    }
}
