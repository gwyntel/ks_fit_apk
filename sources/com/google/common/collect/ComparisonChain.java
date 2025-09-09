package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Comparator;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public abstract class ComparisonChain {
    private static final ComparisonChain ACTIVE = new ComparisonChain() { // from class: com.google.common.collect.ComparisonChain.1
        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compare(Comparable<?> comparable, Comparable<?> comparable2) {
            return d(comparable.compareTo(comparable2));
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compareFalseFirst(boolean z2, boolean z3) {
            return d(Boolean.compare(z2, z3));
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compareTrueFirst(boolean z2, boolean z3) {
            return d(Boolean.compare(z3, z2));
        }

        ComparisonChain d(int i2) {
            return i2 < 0 ? ComparisonChain.LESS : i2 > 0 ? ComparisonChain.GREATER : ComparisonChain.ACTIVE;
        }

        @Override // com.google.common.collect.ComparisonChain
        public int result() {
            return 0;
        }

        @Override // com.google.common.collect.ComparisonChain
        public <T> ComparisonChain compare(@ParametricNullness T t2, @ParametricNullness T t3, Comparator<T> comparator) {
            return d(comparator.compare(t2, t3));
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compare(int i2, int i3) {
            return d(Integer.compare(i2, i3));
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compare(long j2, long j3) {
            return d(Long.compare(j2, j3));
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compare(float f2, float f3) {
            return d(Float.compare(f2, f3));
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compare(double d2, double d3) {
            return d(Double.compare(d2, d3));
        }
    };
    private static final ComparisonChain LESS = new InactiveComparisonChain(-1);
    private static final ComparisonChain GREATER = new InactiveComparisonChain(1);

    private static final class InactiveComparisonChain extends ComparisonChain {

        /* renamed from: a, reason: collision with root package name */
        final int f13940a;

        InactiveComparisonChain(int i2) {
            super();
            this.f13940a = i2;
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compare(double d2, double d3) {
            return this;
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compareFalseFirst(boolean z2, boolean z3) {
            return this;
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compareTrueFirst(boolean z2, boolean z3) {
            return this;
        }

        @Override // com.google.common.collect.ComparisonChain
        public int result() {
            return this.f13940a;
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compare(float f2, float f3) {
            return this;
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compare(int i2, int i3) {
            return this;
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compare(long j2, long j3) {
            return this;
        }

        @Override // com.google.common.collect.ComparisonChain
        public ComparisonChain compare(Comparable<?> comparable, Comparable<?> comparable2) {
            return this;
        }

        @Override // com.google.common.collect.ComparisonChain
        public <T> ComparisonChain compare(@ParametricNullness T t2, @ParametricNullness T t3, Comparator<T> comparator) {
            return this;
        }
    }

    private ComparisonChain() {
    }

    public static ComparisonChain start() {
        return ACTIVE;
    }

    public abstract ComparisonChain compare(double d2, double d3);

    public abstract ComparisonChain compare(float f2, float f3);

    public abstract ComparisonChain compare(int i2, int i3);

    public abstract ComparisonChain compare(long j2, long j3);

    @Deprecated
    public final ComparisonChain compare(Boolean bool, Boolean bool2) {
        return compareFalseFirst(bool.booleanValue(), bool2.booleanValue());
    }

    public abstract ComparisonChain compare(Comparable<?> comparable, Comparable<?> comparable2);

    public abstract <T> ComparisonChain compare(@ParametricNullness T t2, @ParametricNullness T t3, Comparator<T> comparator);

    public abstract ComparisonChain compareFalseFirst(boolean z2, boolean z3);

    public abstract ComparisonChain compareTrueFirst(boolean z2, boolean z3);

    public abstract int result();
}
