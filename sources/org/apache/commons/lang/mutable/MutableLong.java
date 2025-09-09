package org.apache.commons.lang.mutable;

/* loaded from: classes5.dex */
public class MutableLong extends Number implements Comparable, Mutable {
    private static final long serialVersionUID = 62986528375L;
    private long value;

    public MutableLong() {
    }

    public void add(long j2) {
        this.value += j2;
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        long j2 = ((MutableLong) obj).value;
        long j3 = this.value;
        if (j3 < j2) {
            return -1;
        }
        return j3 == j2 ? 0 : 1;
    }

    public void decrement() {
        this.value--;
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return this.value;
    }

    public boolean equals(Object obj) {
        return (obj instanceof MutableLong) && this.value == ((MutableLong) obj).longValue();
    }

    @Override // java.lang.Number
    public float floatValue() {
        return this.value;
    }

    @Override // org.apache.commons.lang.mutable.Mutable
    public Object getValue() {
        return new Long(this.value);
    }

    public int hashCode() {
        long j2 = this.value;
        return (int) (j2 ^ (j2 >>> 32));
    }

    public void increment() {
        this.value++;
    }

    @Override // java.lang.Number
    public int intValue() {
        return (int) this.value;
    }

    @Override // java.lang.Number
    public long longValue() {
        return this.value;
    }

    public void setValue(long j2) {
        this.value = j2;
    }

    public void subtract(long j2) {
        this.value -= j2;
    }

    public Long toLong() {
        return new Long(longValue());
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    public MutableLong(long j2) {
        this.value = j2;
    }

    public void add(Number number) {
        this.value += number.longValue();
    }

    @Override // org.apache.commons.lang.mutable.Mutable
    public void setValue(Object obj) {
        setValue(((Number) obj).longValue());
    }

    public void subtract(Number number) {
        this.value -= number.longValue();
    }

    public MutableLong(Number number) {
        this.value = number.longValue();
    }

    public MutableLong(String str) throws NumberFormatException {
        this.value = Long.parseLong(str);
    }
}
