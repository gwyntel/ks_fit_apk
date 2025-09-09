package com.google.common.math;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Preconditions;

@ElementTypesAreNonnullByDefault
@J2ktIncompatible
@GwtIncompatible
/* loaded from: classes3.dex */
public abstract class LinearTransformation {

    public static final class LinearTransformationBuilder {
        private final double x1;
        private final double y1;

        public LinearTransformation and(double d2, double d3) {
            Preconditions.checkArgument(DoubleUtils.d(d2) && DoubleUtils.d(d3));
            double d4 = this.x1;
            if (d2 != d4) {
                return withSlope((d3 - this.y1) / (d2 - d4));
            }
            Preconditions.checkArgument(d3 != this.y1);
            return new VerticalLinearTransformation(this.x1);
        }

        public LinearTransformation withSlope(double d2) {
            Preconditions.checkArgument(!Double.isNaN(d2));
            return DoubleUtils.d(d2) ? new RegularLinearTransformation(d2, this.y1 - (this.x1 * d2)) : new VerticalLinearTransformation(this.x1);
        }

        private LinearTransformationBuilder(double d2, double d3) {
            this.x1 = d2;
            this.y1 = d3;
        }
    }

    private static final class NaNLinearTransformation extends LinearTransformation {

        /* renamed from: a, reason: collision with root package name */
        static final NaNLinearTransformation f14698a = new NaNLinearTransformation();

        private NaNLinearTransformation() {
        }

        @Override // com.google.common.math.LinearTransformation
        public LinearTransformation inverse() {
            return this;
        }

        @Override // com.google.common.math.LinearTransformation
        public boolean isHorizontal() {
            return false;
        }

        @Override // com.google.common.math.LinearTransformation
        public boolean isVertical() {
            return false;
        }

        @Override // com.google.common.math.LinearTransformation
        public double slope() {
            return Double.NaN;
        }

        public String toString() {
            return "NaN";
        }

        @Override // com.google.common.math.LinearTransformation
        public double transform(double d2) {
            return Double.NaN;
        }
    }

    public static LinearTransformation forNaN() {
        return NaNLinearTransformation.f14698a;
    }

    public static LinearTransformation horizontal(double d2) {
        Preconditions.checkArgument(DoubleUtils.d(d2));
        return new RegularLinearTransformation(0.0d, d2);
    }

    public static LinearTransformationBuilder mapping(double d2, double d3) {
        Preconditions.checkArgument(DoubleUtils.d(d2) && DoubleUtils.d(d3));
        return new LinearTransformationBuilder(d2, d3);
    }

    public static LinearTransformation vertical(double d2) {
        Preconditions.checkArgument(DoubleUtils.d(d2));
        return new VerticalLinearTransformation(d2);
    }

    public abstract LinearTransformation inverse();

    public abstract boolean isHorizontal();

    public abstract boolean isVertical();

    public abstract double slope();

    public abstract double transform(double d2);

    private static final class VerticalLinearTransformation extends LinearTransformation {

        /* renamed from: a, reason: collision with root package name */
        final double f14702a;

        /* renamed from: b, reason: collision with root package name */
        LinearTransformation f14703b;

        VerticalLinearTransformation(double d2) {
            this.f14702a = d2;
            this.f14703b = null;
        }

        private LinearTransformation createInverse() {
            return new RegularLinearTransformation(0.0d, this.f14702a, this);
        }

        @Override // com.google.common.math.LinearTransformation
        public LinearTransformation inverse() {
            LinearTransformation linearTransformation = this.f14703b;
            if (linearTransformation != null) {
                return linearTransformation;
            }
            LinearTransformation linearTransformationCreateInverse = createInverse();
            this.f14703b = linearTransformationCreateInverse;
            return linearTransformationCreateInverse;
        }

        @Override // com.google.common.math.LinearTransformation
        public boolean isHorizontal() {
            return false;
        }

        @Override // com.google.common.math.LinearTransformation
        public boolean isVertical() {
            return true;
        }

        @Override // com.google.common.math.LinearTransformation
        public double slope() {
            throw new IllegalStateException();
        }

        public String toString() {
            return String.format("x = %g", Double.valueOf(this.f14702a));
        }

        @Override // com.google.common.math.LinearTransformation
        public double transform(double d2) {
            throw new IllegalStateException();
        }

        VerticalLinearTransformation(double d2, LinearTransformation linearTransformation) {
            this.f14702a = d2;
            this.f14703b = linearTransformation;
        }
    }

    private static final class RegularLinearTransformation extends LinearTransformation {

        /* renamed from: a, reason: collision with root package name */
        final double f14699a;

        /* renamed from: b, reason: collision with root package name */
        final double f14700b;

        /* renamed from: c, reason: collision with root package name */
        LinearTransformation f14701c;

        RegularLinearTransformation(double d2, double d3) {
            this.f14699a = d2;
            this.f14700b = d3;
            this.f14701c = null;
        }

        private LinearTransformation createInverse() {
            double d2 = this.f14699a;
            return d2 != 0.0d ? new RegularLinearTransformation(1.0d / d2, (this.f14700b * (-1.0d)) / d2, this) : new VerticalLinearTransformation(this.f14700b, this);
        }

        @Override // com.google.common.math.LinearTransformation
        public LinearTransformation inverse() {
            LinearTransformation linearTransformation = this.f14701c;
            if (linearTransformation != null) {
                return linearTransformation;
            }
            LinearTransformation linearTransformationCreateInverse = createInverse();
            this.f14701c = linearTransformationCreateInverse;
            return linearTransformationCreateInverse;
        }

        @Override // com.google.common.math.LinearTransformation
        public boolean isHorizontal() {
            return this.f14699a == 0.0d;
        }

        @Override // com.google.common.math.LinearTransformation
        public boolean isVertical() {
            return false;
        }

        @Override // com.google.common.math.LinearTransformation
        public double slope() {
            return this.f14699a;
        }

        public String toString() {
            return String.format("y = %g * x + %g", Double.valueOf(this.f14699a), Double.valueOf(this.f14700b));
        }

        @Override // com.google.common.math.LinearTransformation
        public double transform(double d2) {
            return (d2 * this.f14699a) + this.f14700b;
        }

        RegularLinearTransformation(double d2, double d3, LinearTransformation linearTransformation) {
            this.f14699a = d2;
            this.f14700b = d3;
            this.f14701c = linearTransformation;
        }
    }
}
