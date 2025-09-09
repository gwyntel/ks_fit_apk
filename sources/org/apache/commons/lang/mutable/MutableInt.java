package org.apache.commons.lang.mutable;

/* loaded from: classes5.dex */
public class MutableInt extends Number implements Comparable, Mutable {
    private static final long serialVersionUID = 512176391864L;
    private int value;

    public MutableInt() {
    }

    public void add(int i2) {
        this.value += i2;
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        int i2 = ((MutableInt) obj).value;
        int i3 = this.value;
        if (i3 < i2) {
            return -1;
        }
        return i3 == i2 ? 0 : 1;
    }

    public void decrement() {
        this.value--;
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return this.value;
    }

    public boolean equals(Object obj) {
        return (obj instanceof MutableInt) && this.value == ((MutableInt) obj).intValue();
    }

    @Override // java.lang.Number
    public float floatValue() {
        return this.value;
    }

    @Override // org.apache.commons.lang.mutable.Mutable
    public Object getValue() {
        return new Integer(this.value);
    }

    public int hashCode() {
        return this.value;
    }

    public void increment() {
        this.value++;
    }

    @Override // java.lang.Number
    public int intValue() {
        return this.value;
    }

    @Override // java.lang.Number
    public long longValue() {
        return this.value;
    }

    public void setValue(int i2) {
        this.value = i2;
    }

    public void subtract(int i2) {
        this.value -= i2;
    }

    public Integer toInteger() {
        return new Integer(intValue());
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    public MutableInt(int i2) {
        this.value = i2;
    }

    public void add(Number number) {
        this.value += number.intValue();
    }

    @Override // org.apache.commons.lang.mutable.Mutable
    public void setValue(Object obj) {
        setValue(((Number) obj).intValue());
    }

    public void subtract(Number number) {
        this.value -= number.intValue();
    }

    public MutableInt(Number number) {
        this.value = number.intValue();
    }

    public MutableInt(String str) throws NumberFormatException {
        this.value = Integer.parseInt(str);
    }
}
