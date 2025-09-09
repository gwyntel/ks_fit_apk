package org.apache.commons.lang.math;

import java.io.Serializable;
import org.apache.commons.lang.text.StrBuilder;

/* loaded from: classes5.dex */
public final class LongRange extends Range implements Serializable {
    private static final long serialVersionUID = 71849363892720L;
    private transient int hashCode;
    private final long max;
    private transient Long maxObject;
    private final long min;
    private transient Long minObject;
    private transient String toString;

    public LongRange(long j2) {
        this.minObject = null;
        this.maxObject = null;
        this.hashCode = 0;
        this.toString = null;
        this.min = j2;
        this.max = j2;
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean containsLong(long j2) {
        return j2 >= this.min && j2 <= this.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean containsNumber(Number number) {
        if (number == null) {
            return false;
        }
        return containsLong(number.longValue());
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean containsRange(Range range) {
        return range != null && containsLong(range.getMinimumLong()) && containsLong(range.getMaximumLong());
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LongRange)) {
            return false;
        }
        LongRange longRange = (LongRange) obj;
        return this.min == longRange.min && this.max == longRange.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public double getMaximumDouble() {
        return this.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public float getMaximumFloat() {
        return this.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public int getMaximumInteger() {
        return (int) this.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public long getMaximumLong() {
        return this.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public Number getMaximumNumber() {
        if (this.maxObject == null) {
            this.maxObject = new Long(this.max);
        }
        return this.maxObject;
    }

    @Override // org.apache.commons.lang.math.Range
    public double getMinimumDouble() {
        return this.min;
    }

    @Override // org.apache.commons.lang.math.Range
    public float getMinimumFloat() {
        return this.min;
    }

    @Override // org.apache.commons.lang.math.Range
    public int getMinimumInteger() {
        return (int) this.min;
    }

    @Override // org.apache.commons.lang.math.Range
    public long getMinimumLong() {
        return this.min;
    }

    @Override // org.apache.commons.lang.math.Range
    public Number getMinimumNumber() {
        if (this.minObject == null) {
            this.minObject = new Long(this.min);
        }
        return this.minObject;
    }

    @Override // org.apache.commons.lang.math.Range
    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = 17;
            int iHashCode = ((17 * 37) + LongRange.class.hashCode()) * 37;
            long j2 = this.min;
            int i2 = (iHashCode + ((int) (j2 ^ (j2 >> 32)))) * 37;
            long j3 = this.max;
            this.hashCode = i2 + ((int) (j3 ^ (j3 >> 32)));
        }
        return this.hashCode;
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean overlapsRange(Range range) {
        if (range == null) {
            return false;
        }
        return range.containsLong(this.min) || range.containsLong(this.max) || containsLong(range.getMinimumLong());
    }

    public long[] toArray() {
        int i2 = (int) ((this.max - this.min) + 1);
        long[] jArr = new long[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            jArr[i3] = this.min + i3;
        }
        return jArr;
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

    public LongRange(Number number) {
        this.minObject = null;
        this.maxObject = null;
        this.hashCode = 0;
        this.toString = null;
        if (number != null) {
            this.min = number.longValue();
            this.max = number.longValue();
            if (number instanceof Long) {
                Long l2 = (Long) number;
                this.minObject = l2;
                this.maxObject = l2;
                return;
            }
            return;
        }
        throw new IllegalArgumentException("The number must not be null");
    }

    public LongRange(long j2, long j3) {
        this.minObject = null;
        this.maxObject = null;
        this.hashCode = 0;
        this.toString = null;
        if (j3 < j2) {
            this.min = j3;
            this.max = j2;
        } else {
            this.min = j2;
            this.max = j3;
        }
    }

    public LongRange(Number number, Number number2) {
        this.minObject = null;
        this.maxObject = null;
        this.hashCode = 0;
        this.toString = null;
        if (number != null && number2 != null) {
            long jLongValue = number.longValue();
            long jLongValue2 = number2.longValue();
            if (jLongValue2 < jLongValue) {
                this.min = jLongValue2;
                this.max = jLongValue;
                if (number2 instanceof Long) {
                    this.minObject = (Long) number2;
                }
                if (number instanceof Long) {
                    this.maxObject = (Long) number;
                    return;
                }
                return;
            }
            this.min = jLongValue;
            this.max = jLongValue2;
            if (number instanceof Long) {
                this.minObject = (Long) number;
            }
            if (number2 instanceof Long) {
                this.maxObject = (Long) number2;
                return;
            }
            return;
        }
        throw new IllegalArgumentException("The numbers must not be null");
    }
}
