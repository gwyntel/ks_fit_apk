package kotlinx.coroutines.internal;

import androidx.exifinterface.media.ExifInterface;
import com.facebook.share.internal.MessengerShareContentUtility;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.Volatile;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B!\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\u0002\u0010\u0007J\u0006\u0010\f\u001a\u00020\rJ\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u000fJ\r\u0010\u0010\u001a\u00020\u0011H\u0000¢\u0006\u0002\b\u0012J\b\u0010\u0013\u001a\u00020\u0011H\u0016J\t\u0010\u0014\u001a\u00020\u0004H\u0082\bJ\r\u0010\u0015\u001a\u00020\r*\u00020\u0004H\u0082\bR\t\u0010\b\u001a\u00020\tX\u0082\u0004R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u00000\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u000bX\u0082\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lkotlinx/coroutines/internal/OnDemandAllocatingPool;", ExifInterface.GPS_DIRECTION_TRUE, "", "maxCapacity", "", "create", "Lkotlin/Function1;", "(ILkotlin/jvm/functions/Function1;)V", "controlState", "Lkotlinx/atomicfu/AtomicInt;", MessengerShareContentUtility.ELEMENTS, "Lkotlinx/atomicfu/AtomicArray;", "allocate", "", "close", "", "stateRepresentation", "", "stateRepresentation$kotlinx_coroutines_core", "toString", "tryForbidNewElements", "isClosed", "kotlinx-coroutines-core"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nOnDemandAllocatingPool.kt\nKotlin\n*S Kotlin\n*F\n+ 1 OnDemandAllocatingPool.kt\nkotlinx/coroutines/internal/OnDemandAllocatingPool\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 OnDemandAllocatingPool.kt\nkotlinx/coroutines/internal/OnDemandAllocatingPoolKt\n*L\n1#1,107:1\n41#1:108\n41#1:109\n35#1,7:110\n41#1:127\n1549#2:117\n1620#2,2:118\n1622#2:122\n1549#2:123\n1620#2,3:124\n101#3,2:120\n*S KotlinDebug\n*F\n+ 1 OnDemandAllocatingPool.kt\nkotlinx/coroutines/internal/OnDemandAllocatingPool\n*L\n35#1:108\n54#1:109\n76#1:110,7\n92#1:127\n77#1:117\n77#1:118,2\n77#1:122\n91#1:123\n91#1:124,3\n79#1:120,2\n*E\n"})
/* loaded from: classes5.dex */
public final class OnDemandAllocatingPool<T> {

    @NotNull
    private static final AtomicIntegerFieldUpdater controlState$FU = AtomicIntegerFieldUpdater.newUpdater(OnDemandAllocatingPool.class, "controlState");

    @Volatile
    private volatile int controlState;

    @NotNull
    private final Function1<Integer, T> create;

    @NotNull
    private final AtomicReferenceArray elements;
    private final int maxCapacity;

    /* JADX WARN: Multi-variable type inference failed */
    public OnDemandAllocatingPool(int i2, @NotNull Function1<? super Integer, ? extends T> function1) {
        this.maxCapacity = i2;
        this.create = function1;
        this.elements = new AtomicReferenceArray(i2);
    }

    private final boolean isClosed(int i2) {
        return (i2 & Integer.MIN_VALUE) != 0;
    }

    private final void loop$atomicfu(AtomicIntegerFieldUpdater atomicIntegerFieldUpdater, Function1<? super Integer, Unit> function1, Object obj) {
        while (true) {
            function1.invoke(Integer.valueOf(atomicIntegerFieldUpdater.get(obj)));
        }
    }

    private final int tryForbidNewElements() {
        int i2;
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = controlState$FU;
        do {
            i2 = atomicIntegerFieldUpdater.get(this);
            if ((i2 & Integer.MIN_VALUE) != 0) {
                return 0;
            }
        } while (!controlState$FU.compareAndSet(this, i2, Integer.MIN_VALUE | i2));
        return i2;
    }

    public final boolean allocate() {
        int i2;
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = controlState$FU;
        do {
            i2 = atomicIntegerFieldUpdater.get(this);
            if ((Integer.MIN_VALUE & i2) != 0) {
                return false;
            }
            if (i2 >= this.maxCapacity) {
                return true;
            }
        } while (!controlState$FU.compareAndSet(this, i2, i2 + 1));
        this.elements.set(i2, this.create.invoke(Integer.valueOf(i2)));
        return true;
    }

    @NotNull
    public final List<T> close() {
        int i2;
        Object andSet;
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = controlState$FU;
        while (true) {
            i2 = atomicIntegerFieldUpdater.get(this);
            if ((i2 & Integer.MIN_VALUE) != 0) {
                i2 = 0;
                break;
            }
            if (controlState$FU.compareAndSet(this, i2, Integer.MIN_VALUE | i2)) {
                break;
            }
        }
        IntRange intRangeUntil = RangesKt.until(0, i2);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRangeUntil, 10));
        Iterator<Integer> it = intRangeUntil.iterator();
        while (it.hasNext()) {
            int iNextInt = ((IntIterator) it).nextInt();
            do {
                andSet = this.elements.getAndSet(iNextInt, null);
            } while (andSet == null);
            arrayList.add(andSet);
        }
        return arrayList;
    }

    @NotNull
    public final String stateRepresentation$kotlinx_coroutines_core() {
        int i2 = controlState$FU.get(this);
        IntRange intRangeUntil = RangesKt.until(0, Integer.MAX_VALUE & i2);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRangeUntil, 10));
        Iterator<Integer> it = intRangeUntil.iterator();
        while (it.hasNext()) {
            arrayList.add(this.elements.get(((IntIterator) it).nextInt()));
        }
        return arrayList.toString() + ((i2 & Integer.MIN_VALUE) != 0 ? "[closed]" : "");
    }

    @NotNull
    public String toString() {
        return "OnDemandAllocatingPool(" + stateRepresentation$kotlinx_coroutines_core() + ')';
    }
}
