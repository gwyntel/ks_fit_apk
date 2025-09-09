package org.apache.commons.lang.math;

import java.io.Serializable;
import org.apache.commons.lang.text.StrBuilder;

/* loaded from: classes5.dex */
public final class DoubleRange extends Range implements Serializable {
    private static final long serialVersionUID = 71849363892740L;
    private transient int hashCode;
    private final double max;
    private transient Double maxObject;
    private final double min;
    private transient Double minObject;
    private transient String toString;

    public DoubleRange(double d2) {
        this.minObject = null;
        this.maxObject = null;
        this.hashCode = 0;
        this.toString = null;
        if (Double.isNaN(d2)) {
            throw new IllegalArgumentException("The number must not be NaN");
        }
        this.min = d2;
        this.max = d2;
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean containsDouble(double d2) {
        return d2 >= this.min && d2 <= this.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean containsNumber(Number number) {
        if (number == null) {
            return false;
        }
        return containsDouble(number.doubleValue());
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean containsRange(Range range) {
        return range != null && containsDouble(range.getMinimumDouble()) && containsDouble(range.getMaximumDouble());
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DoubleRange)) {
            return false;
        }
        DoubleRange doubleRange = (DoubleRange) obj;
        return Double.doubleToLongBits(this.min) == Double.doubleToLongBits(doubleRange.min) && Double.doubleToLongBits(this.max) == Double.doubleToLongBits(doubleRange.max);
    }

    @Override // org.apache.commons.lang.math.Range
    public double getMaximumDouble() {
        return this.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public float getMaximumFloat() {
        return (float) this.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public int getMaximumInteger() {
        return (int) this.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public long getMaximumLong() {
        return (long) this.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public Number getMaximumNumber() {
        if (this.maxObject == null) {
            this.maxObject = new Double(this.max);
        }
        return this.maxObject;
    }

    @Override // org.apache.commons.lang.math.Range
    public double getMinimumDouble() {
        return this.min;
    }

    @Override // org.apache.commons.lang.math.Range
    public float getMinimumFloat() {
        return (float) this.min;
    }

    @Override // org.apache.commons.lang.math.Range
    public int getMinimumInteger() {
        return (int) this.min;
    }

    @Override // org.apache.commons.lang.math.Range
    public long getMinimumLong() {
        return (long) this.min;
    }

    @Override // org.apache.commons.lang.math.Range
    public Number getMinimumNumber() {
        if (this.minObject == null) {
            this.minObject = new Double(this.min);
        }
        return this.minObject;
    }

    @Override // org.apache.commons.lang.math.Range
    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = 17;
            this.hashCode = (17 * 37) + DoubleRange.class.hashCode();
            long jDoubleToLongBits = Double.doubleToLongBits(this.min);
            this.hashCode = (this.hashCode * 37) + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >> 32)));
            long jDoubleToLongBits2 = Double.doubleToLongBits(this.max);
            this.hashCode = (this.hashCode * 37) + ((int) (jDoubleToLongBits2 ^ (jDoubleToLongBits2 >> 32)));
        }
        return this.hashCode;
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean overlapsRange(Range range) {
        if (range == null) {
            return false;
        }
        return range.containsDouble(this.min) || range.containsDouble(this.max) || containsDouble(range.getMinimumDouble());
    }

    @Override // org.apache.commons.lang.math.Range
    public String toString() {
        if (this.toString == null) {
            StrBuilder strBuilder = new StrBuilder(32);
            strBuilder.append("Range[");
            strBuilder.append(this.min);
            strBuilder.append(',');
            strBuilder.append(this.max);
            strBuilder.append(']');
            this.toString = strBuilder.toString();
        }
        return this.toString;
    }

    public DoubleRange(Number number) {
        this.minObject = null;
        this.maxObject = null;
        this.hashCode = 0;
        this.toString = null;
        if (number != null) {
            double dDoubleValue = number.doubleValue();
            this.min = dDoubleValue;
            double dDoubleValue2 = number.doubleValue();
            this.max = dDoubleValue2;
            if (!Double.isNaN(dDoubleValue) && !Double.isNaN(dDoubleValue2)) {
                if (number instanceof Double) {
                    Double d2 = (Double) number;
                    this.minObject = d2;
                    this.maxObject = d2;
                    return;
                }
                return;
            }
            throw new IllegalArgumentException("The number must not be NaN");
        }
        throw new IllegalArgumentException("The number must not be null");
    }

    public DoubleRange(double d2, double d3) {
        this.minObject = null;
        this.maxObject = null;
        this.hashCode = 0;
        this.toString = null;
        if (Double.isNaN(d2) || Double.isNaN(d3)) {
            throw new IllegalArgumentException("The numbers must not be NaN");
        }
        if (d3 < d2) {
            this.min = d3;
            this.max = d2;
        } else {
            this.min = d2;
            this.max = d3;
        }
    }

    public DoubleRange(Number number, Number number2) {
        this.minObject = null;
        this.maxObject = null;
        this.hashCode = 0;
        this.toString = null;
        if (number != null && number2 != null) {
            double dDoubleValue = number.doubleValue();
            double dDoubleValue2 = number2.doubleValue();
            if (Double.isNaN(dDoubleValue) || Double.isNaN(dDoubleValue2)) {
                throw new IllegalArgumentException("The numbers must not be NaN");
            }
            if (dDoubleValue2 < dDoubleValue) {
                this.min = dDoubleValue2;
                this.max = dDoubleValue;
                if (number2 instanceof Double) {
                    this.minObject = (Double) number2;
                }
                if (number instanceof Double) {
                    this.maxObject = (Double) number;
                    return;
                }
                return;
            }
            this.min = dDoubleValue;
            this.max = dDoubleValue2;
            if (number instanceof Double) {
                this.minObject = (Double) number;
            }
            if (number2 instanceof Double) {
                this.maxObject = (Double) number2;
                return;
            }
            return;
        }
        throw new IllegalArgumentException("The numbers must not be null");
    }
}
