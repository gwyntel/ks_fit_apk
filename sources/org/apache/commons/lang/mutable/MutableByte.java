package org.apache.commons.lang.mutable;

/* loaded from: classes5.dex */
public class MutableByte extends Number implements Comparable, Mutable {
    private static final long serialVersionUID = -1585823265;
    private byte value;

    public MutableByte() {
    }

    public void add(byte b2) {
        this.value = (byte) (this.value + b2);
    }

    @Override // java.lang.Number
    public byte byteValue() {
        return this.value;
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        byte b2 = ((MutableByte) obj).value;
        byte b3 = this.value;
        if (b3 < b2) {
            return -1;
        }
        return b3 == b2 ? 0 : 1;
    }

    public void decrement() {
        this.value = (byte) (this.value - 1);
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return this.value;
    }

    public boolean equals(Object obj) {
        return (obj instanceof MutableByte) && this.value == ((MutableByte) obj).byteValue();
    }

    @Override // java.lang.Number
    public float floatValue() {
        return this.value;
    }

    @Override // org.apache.commons.lang.mutable.Mutable
    public Object getValue() {
        return new Byte(this.value);
    }

    public int hashCode() {
        return this.value;
    }

    public void increment() {
        this.value = (byte) (this.value + 1);
    }

    @Override // java.lang.Number
    public int intValue() {
        return this.value;
    }

    @Override // java.lang.Number
    public long longValue() {
        return this.value;
    }

    public void setValue(byte b2) {
        this.value = b2;
    }

    public void subtract(byte b2) {
        this.value = (byte) (this.value - b2);
    }

    public Byte toByte() {
        return new Byte(byteValue());
    }

    public String toString() {
        return String.valueOf((int) this.value);
    }

    public MutableByte(byte b2) {
        this.value = b2;
    }

    public void add(Number number) {
        this.value = (byte) (this.value + number.byteValue());
    }

    @Override // org.apache.commons.lang.mutable.Mutable
    public void setValue(Object obj) {
        setValue(((Number) obj).byteValue());
    }

    public void subtract(Number number) {
        this.value = (byte) (this.value - number.byteValue());
    }

    public MutableByte(Number number) {
        this.value = number.byteValue();
    }

    public MutableByte(String str) throws NumberFormatException {
        this.value = Byte.parseByte(str);
    }
}
