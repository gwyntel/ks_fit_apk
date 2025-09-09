package kotlin.jvm.internal;

import androidx.exifinterface.media.ExifInterface;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.umeng.analytics.pro.bc;
import kotlin.Metadata;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0010\u0011\n\u0002\b\u0005\b&\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0001B\u000f\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\u0013\u0010\u0007\u001a\u00020\u0003*\u00028\u0000H$¢\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\r\u001a\u00020\u0003H\u0004¢\u0006\u0004\b\r\u0010\u000eJ\u001f\u0010\u0011\u001a\u00028\u00002\u0006\u0010\u000f\u001a\u00028\u00002\u0006\u0010\u0010\u001a\u00028\u0000H\u0004¢\u0006\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0004\u001a\u00020\u00038\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0004\u0010\u0013R\"\u0010\u0014\u001a\u00020\u00038\u0004@\u0004X\u0084\u000e¢\u0006\u0012\n\u0004\b\u0014\u0010\u0013\u001a\u0004\b\u0015\u0010\u000e\"\u0004\b\u0016\u0010\u0006R\"\u0010\u0018\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u00178\u0002X\u0082\u0004¢\u0006\f\n\u0004\b\u0018\u0010\u0019\u0012\u0004\b\u001a\u0010\u001b¨\u0006\u001c"}, d2 = {"Lkotlin/jvm/internal/PrimitiveSpreadBuilder;", "", ExifInterface.GPS_DIRECTION_TRUE, "", "size", "<init>", "(I)V", "getSize", "(Ljava/lang/Object;)I", "spreadArgument", "", "addSpread", "(Ljava/lang/Object;)V", bc.aL, "()I", "values", "result", "d", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "I", RequestParameters.POSITION, "a", "b", "", "spreads", "[Ljava/lang/Object;", "getSpreads$annotations", "()V", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0})
/* loaded from: classes4.dex */
public abstract class PrimitiveSpreadBuilder<T> {
    private int position;
    private final int size;

    @NotNull
    private final T[] spreads;

    public PrimitiveSpreadBuilder(int i2) {
        this.size = i2;
        this.spreads = (T[]) new Object[i2];
    }

    private static /* synthetic */ void getSpreads$annotations() {
    }

    /* renamed from: a, reason: from getter */
    protected final int getPosition() {
        return this.position;
    }

    public final void addSpread(@NotNull T spreadArgument) {
        Intrinsics.checkNotNullParameter(spreadArgument, "spreadArgument");
        T[] tArr = this.spreads;
        int i2 = this.position;
        this.position = i2 + 1;
        tArr[i2] = spreadArgument;
    }

    protected final void b(int i2) {
        this.position = i2;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [java.util.Iterator, kotlin.collections.IntIterator] */
    protected final int c() {
        int size = 0;
        ?? it = new IntRange(0, this.size - 1).iterator();
        while (it.hasNext()) {
            T t2 = this.spreads[it.nextInt()];
            size += t2 != null ? getSize(t2) : 1;
        }
        return size;
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [java.util.Iterator, kotlin.collections.IntIterator] */
    protected final Object d(Object values, Object result) {
        Intrinsics.checkNotNullParameter(values, "values");
        Intrinsics.checkNotNullParameter(result, "result");
        ?? it = new IntRange(0, this.size - 1).iterator();
        int i2 = 0;
        int i3 = 0;
        while (it.hasNext()) {
            int iNextInt = it.nextInt();
            T t2 = this.spreads[iNextInt];
            if (t2 != null) {
                if (i2 < iNextInt) {
                    int i4 = iNextInt - i2;
                    System.arraycopy(values, i2, result, i3, i4);
                    i3 += i4;
                }
                int size = getSize(t2);
                System.arraycopy(t2, 0, result, i3, size);
                i3 += size;
                i2 = iNextInt + 1;
            }
        }
        int i5 = this.size;
        if (i2 < i5) {
            System.arraycopy(values, i2, result, i3, i5 - i2);
        }
        return result;
    }

    protected abstract int getSize(Object obj);
}
