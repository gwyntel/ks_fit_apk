package org.apache.commons.lang.math;

import java.math.BigInteger;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.text.StrBuilder;

/* loaded from: classes5.dex */
public final class Fraction extends Number implements Comparable {
    private static final long serialVersionUID = 65382027393090L;
    private final int denominator;
    private final int numerator;
    public static final Fraction ZERO = new Fraction(0, 1);
    public static final Fraction ONE = new Fraction(1, 1);
    public static final Fraction ONE_HALF = new Fraction(1, 2);
    public static final Fraction ONE_THIRD = new Fraction(1, 3);
    public static final Fraction TWO_THIRDS = new Fraction(2, 3);
    public static final Fraction ONE_QUARTER = new Fraction(1, 4);
    public static final Fraction TWO_QUARTERS = new Fraction(2, 4);
    public static final Fraction THREE_QUARTERS = new Fraction(3, 4);
    public static final Fraction ONE_FIFTH = new Fraction(1, 5);
    public static final Fraction TWO_FIFTHS = new Fraction(2, 5);
    public static final Fraction THREE_FIFTHS = new Fraction(3, 5);
    public static final Fraction FOUR_FIFTHS = new Fraction(4, 5);
    private transient int hashCode = 0;
    private transient String toString = null;
    private transient String toProperString = null;

    private Fraction(int i2, int i3) {
        this.numerator = i2;
        this.denominator = i3;
    }

    private static int addAndCheck(int i2, int i3) {
        long j2 = i2 + i3;
        if (j2 < -2147483648L || j2 > 2147483647L) {
            throw new ArithmeticException("overflow: add");
        }
        return (int) j2;
    }

    private Fraction addSub(Fraction fraction, boolean z2) {
        if (fraction == null) {
            throw new IllegalArgumentException("The fraction must not be null");
        }
        if (this.numerator == 0) {
            return z2 ? fraction : fraction.negate();
        }
        if (fraction.numerator == 0) {
            return this;
        }
        int iGreatestCommonDivisor = greatestCommonDivisor(this.denominator, fraction.denominator);
        if (iGreatestCommonDivisor == 1) {
            int iMulAndCheck = mulAndCheck(this.numerator, fraction.denominator);
            int iMulAndCheck2 = mulAndCheck(fraction.numerator, this.denominator);
            return new Fraction(z2 ? addAndCheck(iMulAndCheck, iMulAndCheck2) : subAndCheck(iMulAndCheck, iMulAndCheck2), mulPosAndCheck(this.denominator, fraction.denominator));
        }
        BigInteger bigIntegerMultiply = BigInteger.valueOf(this.numerator).multiply(BigInteger.valueOf(fraction.denominator / iGreatestCommonDivisor));
        BigInteger bigIntegerMultiply2 = BigInteger.valueOf(fraction.numerator).multiply(BigInteger.valueOf(this.denominator / iGreatestCommonDivisor));
        BigInteger bigIntegerAdd = z2 ? bigIntegerMultiply.add(bigIntegerMultiply2) : bigIntegerMultiply.subtract(bigIntegerMultiply2);
        int iIntValue = bigIntegerAdd.mod(BigInteger.valueOf(iGreatestCommonDivisor)).intValue();
        int iGreatestCommonDivisor2 = iIntValue == 0 ? iGreatestCommonDivisor : greatestCommonDivisor(iIntValue, iGreatestCommonDivisor);
        BigInteger bigIntegerDivide = bigIntegerAdd.divide(BigInteger.valueOf(iGreatestCommonDivisor2));
        if (bigIntegerDivide.bitLength() <= 31) {
            return new Fraction(bigIntegerDivide.intValue(), mulPosAndCheck(this.denominator / iGreatestCommonDivisor, fraction.denominator / iGreatestCommonDivisor2));
        }
        throw new ArithmeticException("overflow: numerator too large after multiply");
    }

    public static Fraction getFraction(int i2, int i3) {
        if (i3 == 0) {
            throw new ArithmeticException("The denominator must not be zero");
        }
        if (i3 < 0) {
            if (i2 == Integer.MIN_VALUE || i3 == Integer.MIN_VALUE) {
                throw new ArithmeticException("overflow: can't negate");
            }
            i2 = -i2;
            i3 = -i3;
        }
        return new Fraction(i2, i3);
    }

    public static Fraction getReducedFraction(int i2, int i3) {
        if (i3 == 0) {
            throw new ArithmeticException("The denominator must not be zero");
        }
        if (i2 == 0) {
            return ZERO;
        }
        if (i3 == Integer.MIN_VALUE && (i2 & 1) == 0) {
            i2 /= 2;
            i3 /= 2;
        }
        if (i3 < 0) {
            if (i2 == Integer.MIN_VALUE || i3 == Integer.MIN_VALUE) {
                throw new ArithmeticException("overflow: can't negate");
            }
            i2 = -i2;
            i3 = -i3;
        }
        int iGreatestCommonDivisor = greatestCommonDivisor(i2, i3);
        return new Fraction(i2 / iGreatestCommonDivisor, i3 / iGreatestCommonDivisor);
    }

    private static int greatestCommonDivisor(int i2, int i3) {
        int i4;
        if (Math.abs(i2) <= 1 || Math.abs(i3) <= 1) {
            return 1;
        }
        if (i2 > 0) {
            i2 = -i2;
        }
        if (i3 > 0) {
            i3 = -i3;
        }
        int i5 = 0;
        while (true) {
            i4 = i2 & 1;
            if (i4 != 0 || (i3 & 1) != 0 || i5 >= 31) {
                break;
            }
            i2 /= 2;
            i3 /= 2;
            i5++;
        }
        if (i5 == 31) {
            throw new ArithmeticException("overflow: gcd is 2^31");
        }
        int i6 = i4 == 1 ? i3 : -(i2 / 2);
        while (true) {
            if ((i6 & 1) == 0) {
                i6 /= 2;
            } else {
                if (i6 > 0) {
                    i2 = -i6;
                } else {
                    i3 = i6;
                }
                i6 = (i3 - i2) / 2;
                if (i6 == 0) {
                    return (-i2) * (1 << i5);
                }
            }
        }
    }

    private static int mulAndCheck(int i2, int i3) {
        long j2 = i2 * i3;
        if (j2 < -2147483648L || j2 > 2147483647L) {
            throw new ArithmeticException("overflow: mul");
        }
        return (int) j2;
    }

    private static int mulPosAndCheck(int i2, int i3) {
        long j2 = i2 * i3;
        if (j2 <= 2147483647L) {
            return (int) j2;
        }
        throw new ArithmeticException("overflow: mulPos");
    }

    private static int subAndCheck(int i2, int i3) {
        long j2 = i2 - i3;
        if (j2 < -2147483648L || j2 > 2147483647L) {
            throw new ArithmeticException("overflow: add");
        }
        return (int) j2;
    }

    public Fraction abs() {
        return this.numerator >= 0 ? this : negate();
    }

    public Fraction add(Fraction fraction) {
        return addSub(fraction, true);
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        Fraction fraction = (Fraction) obj;
        if (this == fraction) {
            return 0;
        }
        int i2 = this.numerator;
        int i3 = fraction.numerator;
        if (i2 == i3 && this.denominator == fraction.denominator) {
            return 0;
        }
        long j2 = i2 * fraction.denominator;
        long j3 = i3 * this.denominator;
        if (j2 == j3) {
            return 0;
        }
        return j2 < j3 ? -1 : 1;
    }

    public Fraction divideBy(Fraction fraction) {
        if (fraction == null) {
            throw new IllegalArgumentException("The fraction must not be null");
        }
        if (fraction.numerator != 0) {
            return multiplyBy(fraction.invert());
        }
        throw new ArithmeticException("The fraction to divide by must not be zero");
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return this.numerator / this.denominator;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Fraction)) {
            return false;
        }
        Fraction fraction = (Fraction) obj;
        return getNumerator() == fraction.getNumerator() && getDenominator() == fraction.getDenominator();
    }

    @Override // java.lang.Number
    public float floatValue() {
        return this.numerator / this.denominator;
    }

    public int getDenominator() {
        return this.denominator;
    }

    public int getNumerator() {
        return this.numerator;
    }

    public int getProperNumerator() {
        return Math.abs(this.numerator % this.denominator);
    }

    public int getProperWhole() {
        return this.numerator / this.denominator;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = ((getNumerator() + 629) * 37) + getDenominator();
        }
        return this.hashCode;
    }

    @Override // java.lang.Number
    public int intValue() {
        return this.numerator / this.denominator;
    }

    public Fraction invert() {
        int i2 = this.numerator;
        if (i2 == 0) {
            throw new ArithmeticException("Unable to invert zero.");
        }
        if (i2 != Integer.MIN_VALUE) {
            return i2 < 0 ? new Fraction(-this.denominator, -i2) : new Fraction(this.denominator, i2);
        }
        throw new ArithmeticException("overflow: can't negate numerator");
    }

    @Override // java.lang.Number
    public long longValue() {
        return this.numerator / this.denominator;
    }

    public Fraction multiplyBy(Fraction fraction) {
        if (fraction == null) {
            throw new IllegalArgumentException("The fraction must not be null");
        }
        int i2 = this.numerator;
        if (i2 == 0 || fraction.numerator == 0) {
            return ZERO;
        }
        int iGreatestCommonDivisor = greatestCommonDivisor(i2, fraction.denominator);
        int iGreatestCommonDivisor2 = greatestCommonDivisor(fraction.numerator, this.denominator);
        return getReducedFraction(mulAndCheck(this.numerator / iGreatestCommonDivisor, fraction.numerator / iGreatestCommonDivisor2), mulPosAndCheck(this.denominator / iGreatestCommonDivisor2, fraction.denominator / iGreatestCommonDivisor));
    }

    public Fraction negate() {
        int i2 = this.numerator;
        if (i2 != Integer.MIN_VALUE) {
            return new Fraction(-i2, this.denominator);
        }
        throw new ArithmeticException("overflow: too large to negate");
    }

    public Fraction pow(int i2) {
        if (i2 == 1) {
            return this;
        }
        if (i2 == 0) {
            return ONE;
        }
        if (i2 < 0) {
            return i2 == Integer.MIN_VALUE ? invert().pow(2).pow(-(i2 / 2)) : invert().pow(-i2);
        }
        Fraction fractionMultiplyBy = multiplyBy(this);
        return i2 % 2 == 0 ? fractionMultiplyBy.pow(i2 / 2) : fractionMultiplyBy.pow(i2 / 2).multiplyBy(this);
    }

    public Fraction reduce() {
        int i2 = this.numerator;
        if (i2 == 0) {
            Fraction fraction = ZERO;
            return equals(fraction) ? this : fraction;
        }
        int iGreatestCommonDivisor = greatestCommonDivisor(Math.abs(i2), this.denominator);
        return iGreatestCommonDivisor == 1 ? this : getFraction(this.numerator / iGreatestCommonDivisor, this.denominator / iGreatestCommonDivisor);
    }

    public Fraction subtract(Fraction fraction) {
        return addSub(fraction, false);
    }

    public String toProperString() {
        if (this.toProperString == null) {
            int i2 = this.numerator;
            if (i2 == 0) {
                this.toProperString = "0";
            } else {
                int i3 = this.denominator;
                if (i2 == i3) {
                    this.toProperString = "1";
                } else if (i2 == i3 * (-1)) {
                    this.toProperString = "-1";
                } else {
                    if (i2 > 0) {
                        i2 = -i2;
                    }
                    if (i2 < (-i3)) {
                        int properNumerator = getProperNumerator();
                        if (properNumerator == 0) {
                            this.toProperString = Integer.toString(getProperWhole());
                        } else {
                            this.toProperString = new StrBuilder(32).append(getProperWhole()).append(' ').append(properNumerator).append(IOUtils.DIR_SEPARATOR_UNIX).append(getDenominator()).toString();
                        }
                    } else {
                        this.toProperString = new StrBuilder(32).append(getNumerator()).append(IOUtils.DIR_SEPARATOR_UNIX).append(getDenominator()).toString();
                    }
                }
            }
        }
        return this.toProperString;
    }

    public String toString() {
        if (this.toString == null) {
            this.toString = new StrBuilder(32).append(getNumerator()).append(IOUtils.DIR_SEPARATOR_UNIX).append(getDenominator()).toString();
        }
        return this.toString;
    }

    public static Fraction getFraction(int i2, int i3, int i4) {
        if (i4 == 0) {
            throw new ArithmeticException("The denominator must not be zero");
        }
        if (i4 < 0) {
            throw new ArithmeticException("The denominator must not be negative");
        }
        if (i3 < 0) {
            throw new ArithmeticException("The numerator must not be negative");
        }
        long j2 = i2 < 0 ? (i2 * i4) - i3 : (i2 * i4) + i3;
        if (j2 >= -2147483648L && j2 <= 2147483647L) {
            return new Fraction((int) j2, i4);
        }
        throw new ArithmeticException("Numerator too large to represent as an Integer.");
    }

    public static Fraction getFraction(double d2) {
        int i2;
        int i3 = d2 < 0.0d ? -1 : 1;
        double dAbs = Math.abs(d2);
        if (dAbs > 2.147483647E9d || Double.isNaN(dAbs)) {
            throw new ArithmeticException("The value must not be greater than Integer.MAX_VALUE or NaN");
        }
        int i4 = (int) dAbs;
        double d3 = dAbs - i4;
        int i5 = (int) d3;
        double d4 = d3 - i5;
        double d5 = d3;
        double d6 = Double.MAX_VALUE;
        int i6 = 1;
        int i7 = 1;
        double d7 = 1.0d;
        int i8 = 0;
        int i9 = 0;
        int i10 = 1;
        while (true) {
            int i11 = (int) (d7 / d4);
            double d8 = d7 - (i11 * d4);
            int i12 = (i5 * i10) + i8;
            int i13 = (i5 * i9) + i6;
            double d9 = d4;
            double d10 = d5;
            double dAbs2 = Math.abs(d10 - (i12 / i13));
            i2 = i7 + 1;
            if (d6 <= dAbs2 || i13 > 10000 || i13 <= 0 || i2 >= 25) {
                break;
            }
            d6 = dAbs2;
            d5 = d10;
            i6 = i9;
            i7 = i2;
            d4 = d8;
            i9 = i13;
            i5 = i11;
            i8 = i10;
            i10 = i12;
            d7 = d9;
        }
        if (i2 != 25) {
            return getReducedFraction((i10 + (i4 * i9)) * i3, i9);
        }
        throw new ArithmeticException("Unable to convert double to fraction");
    }

    public static Fraction getFraction(String str) throws NumberFormatException {
        if (str != null) {
            if (str.indexOf(46) >= 0) {
                return getFraction(Double.parseDouble(str));
            }
            int iIndexOf = str.indexOf(32);
            if (iIndexOf > 0) {
                int i2 = Integer.parseInt(str.substring(0, iIndexOf));
                String strSubstring = str.substring(iIndexOf + 1);
                int iIndexOf2 = strSubstring.indexOf(47);
                if (iIndexOf2 >= 0) {
                    return getFraction(i2, Integer.parseInt(strSubstring.substring(0, iIndexOf2)), Integer.parseInt(strSubstring.substring(iIndexOf2 + 1)));
                }
                throw new NumberFormatException("The fraction could not be parsed as the format X Y/Z");
            }
            int iIndexOf3 = str.indexOf(47);
            if (iIndexOf3 < 0) {
                return getFraction(Integer.parseInt(str), 1);
            }
            return getFraction(Integer.parseInt(str.substring(0, iIndexOf3)), Integer.parseInt(str.substring(iIndexOf3 + 1)));
        }
        throw new IllegalArgumentException("The string must not be null");
    }
}
