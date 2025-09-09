package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.annotation.CheckForNull;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class TopKSelector<T> {
    private final T[] buffer;
    private int bufferSize;
    private final Comparator<? super T> comparator;

    /* renamed from: k, reason: collision with root package name */
    private final int f14357k;

    @CheckForNull
    private T threshold;

    private TopKSelector(Comparator<? super T> comparator, int i2) {
        this.comparator = (Comparator) Preconditions.checkNotNull(comparator, "comparator");
        this.f14357k = i2;
        Preconditions.checkArgument(i2 >= 0, "k (%s) must be >= 0", i2);
        Preconditions.checkArgument(i2 <= 1073741823, "k (%s) must be <= Integer.MAX_VALUE / 2", i2);
        this.buffer = (T[]) new Object[IntMath.checkedMultiply(i2, 2)];
        this.bufferSize = 0;
        this.threshold = null;
    }

    public static <T extends Comparable<? super T>> TopKSelector<T> greatest(int i2) {
        return greatest(i2, Ordering.natural());
    }

    public static <T extends Comparable<? super T>> TopKSelector<T> least(int i2) {
        return least(i2, Ordering.natural());
    }

    private int partition(int i2, int i3, int i4) {
        Object objA = NullnessCasts.a(this.buffer[i4]);
        T[] tArr = this.buffer;
        tArr[i4] = tArr[i3];
        int i5 = i2;
        while (i2 < i3) {
            if (this.comparator.compare((Object) NullnessCasts.a(this.buffer[i2]), objA) < 0) {
                swap(i5, i2);
                i5++;
            }
            i2++;
        }
        T[] tArr2 = this.buffer;
        tArr2[i3] = tArr2[i5];
        tArr2[i5] = objA;
        return i5;
    }

    private void swap(int i2, int i3) {
        T[] tArr = this.buffer;
        T t2 = tArr[i2];
        tArr[i2] = tArr[i3];
        tArr[i3] = t2;
    }

    private void trim() {
        int i2 = (this.f14357k * 2) - 1;
        int iLog2 = IntMath.log2(i2, RoundingMode.CEILING) * 3;
        int iMax = 0;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (iMax >= i2) {
                break;
            }
            int iPartition = partition(iMax, i2, ((iMax + i2) + 1) >>> 1);
            int i5 = this.f14357k;
            if (iPartition <= i5) {
                if (iPartition >= i5) {
                    break;
                }
                iMax = Math.max(iPartition, iMax + 1);
                i4 = iPartition;
            } else {
                i2 = iPartition - 1;
            }
            i3++;
            if (i3 >= iLog2) {
                Arrays.sort(this.buffer, iMax, i2 + 1, this.comparator);
                break;
            }
        }
        this.bufferSize = this.f14357k;
        this.threshold = (T) NullnessCasts.a(this.buffer[i4]);
        while (true) {
            i4++;
            if (i4 >= this.f14357k) {
                return;
            }
            if (this.comparator.compare((Object) NullnessCasts.a(this.buffer[i4]), (Object) NullnessCasts.a(this.threshold)) > 0) {
                this.threshold = this.buffer[i4];
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    TopKSelector a(TopKSelector topKSelector) {
        for (int i2 = 0; i2 < topKSelector.bufferSize; i2++) {
            offer(NullnessCasts.a(topKSelector.buffer[i2]));
        }
        return this;
    }

    public void offer(@ParametricNullness T t2) {
        int i2 = this.f14357k;
        if (i2 == 0) {
            return;
        }
        int i3 = this.bufferSize;
        if (i3 == 0) {
            this.buffer[0] = t2;
            this.threshold = t2;
            this.bufferSize = 1;
            return;
        }
        if (i3 < i2) {
            T[] tArr = this.buffer;
            this.bufferSize = i3 + 1;
            tArr[i3] = t2;
            if (this.comparator.compare(t2, (Object) NullnessCasts.a(this.threshold)) > 0) {
                this.threshold = t2;
                return;
            }
            return;
        }
        if (this.comparator.compare(t2, (Object) NullnessCasts.a(this.threshold)) < 0) {
            T[] tArr2 = this.buffer;
            int i4 = this.bufferSize;
            int i5 = i4 + 1;
            this.bufferSize = i5;
            tArr2[i4] = t2;
            if (i5 == this.f14357k * 2) {
                trim();
            }
        }
    }

    public void offerAll(Iterable<? extends T> iterable) {
        offerAll(iterable.iterator());
    }

    public List<T> topK() {
        T[] tArr = this.buffer;
        Arrays.sort(tArr, 0, this.bufferSize, this.comparator);
        int i2 = this.bufferSize;
        int i3 = this.f14357k;
        if (i2 > i3) {
            T[] tArr2 = this.buffer;
            Arrays.fill(tArr2, i3, tArr2.length, (Object) null);
            int i4 = this.f14357k;
            this.bufferSize = i4;
            this.threshold = this.buffer[i4 - 1];
        }
        return Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(tArr, this.bufferSize)));
    }

    public static <T> TopKSelector<T> greatest(int i2, Comparator<? super T> comparator) {
        return new TopKSelector<>(Ordering.from(comparator).reverse(), i2);
    }

    public static <T> TopKSelector<T> least(int i2, Comparator<? super T> comparator) {
        return new TopKSelector<>(comparator, i2);
    }

    public void offerAll(Iterator<? extends T> it) {
        while (it.hasNext()) {
            offer(it.next());
        }
    }
}
