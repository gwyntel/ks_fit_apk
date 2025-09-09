package kotlinx.coroutines.internal;

import androidx.exifinterface.media.ExifInterface;
import com.google.common.util.concurrent.i0;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000N\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a8\u0010\u0004\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00032!\u0010\b\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\u0004\u0012\u00020\u00050\tH\u0082\b\u001a!\u0010\r\u001a\u0002H\u000e\"\u000e\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\u000f*\u0002H\u000eH\u0000¢\u0006\u0002\u0010\u0010\u001av\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00130\u0012\"\u000e\b\u0000\u0010\u0013*\b\u0012\u0004\u0012\u0002H\u00130\u0014*\b\u0012\u0004\u0012\u0002H\u00130\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u0002H\u001328\b\b\u0010\u0019\u001a2\u0012\u0013\u0012\u00110\u0017¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\u0016\u0012\u0013\u0012\u0011H\u0013¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\u001b\u0012\u0004\u0012\u0002H\u00130\u001aH\u0080\bø\u0001\u0000\u001aj\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00130\u0012\"\u000e\b\u0000\u0010\u0013*\b\u0012\u0004\u0012\u0002H\u00130\u0014*\u0002H\u00132\u0006\u0010\u0016\u001a\u00020\u001726\u0010\u0019\u001a2\u0012\u0013\u0012\u00110\u0017¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\u0016\u0012\u0013\u0012\u0011H\u0013¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\u001b\u0012\u0004\u0012\u0002H\u00130\u001aH\u0000ø\u0001\u0000¢\u0006\u0002\u0010\u001d\u001a+\u0010\u001e\u001a\u00020\u0005\"\u000e\b\u0000\u0010\u0013*\b\u0012\u0004\u0012\u0002H\u00130\u0014*\b\u0012\u0004\u0012\u0002H\u00130\u00152\u0006\u0010\u001f\u001a\u0002H\u0013H\u0080\b\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0003X\u0082T¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006 "}, d2 = {"CLOSED", "Lkotlinx/coroutines/internal/Symbol;", "POINTERS_SHIFT", "", "addConditionally", "", "Lkotlinx/atomicfu/AtomicInt;", "delta", "condition", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "cur", "close", "N", "Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;", "(Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;)Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;", "findSegmentAndMoveForward", "Lkotlinx/coroutines/internal/SegmentOrClosed;", ExifInterface.LATITUDE_SOUTH, "Lkotlinx/coroutines/internal/Segment;", "Lkotlinx/atomicfu/AtomicRef;", "id", "", "startFrom", "createNewSegment", "Lkotlin/Function2;", "prev", "findSegmentInternal", "(Lkotlinx/coroutines/internal/Segment;JLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "moveForward", "to", "kotlinx-coroutines-core"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nConcurrentLinkedList.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ConcurrentLinkedList.kt\nkotlinx/coroutines/internal/ConcurrentLinkedListKt\n+ 2 ConcurrentLinkedList.kt\nkotlinx/coroutines/internal/ConcurrentLinkedListNode\n*L\n1#1,269:1\n46#1,8:284\n107#2,7:270\n107#2,7:277\n*S KotlinDebug\n*F\n+ 1 ConcurrentLinkedList.kt\nkotlinx/coroutines/internal/ConcurrentLinkedListKt\n*L\n74#1:284,8\n27#1:270,7\n85#1:277,7\n*E\n"})
/* loaded from: classes5.dex */
public final class ConcurrentLinkedListKt {

    @NotNull
    private static final Symbol CLOSED = new Symbol("CLOSED");
    private static final int POINTERS_SHIFT = 16;

    private static final boolean addConditionally$atomicfu(Object obj, AtomicIntegerFieldUpdater atomicIntegerFieldUpdater, int i2, Function1<? super Integer, Boolean> function1) {
        int i3;
        do {
            i3 = atomicIntegerFieldUpdater.get(obj);
            if (!function1.invoke(Integer.valueOf(i3)).booleanValue()) {
                return false;
            }
        } while (!atomicIntegerFieldUpdater.compareAndSet(obj, i3, i3 + i2));
        return true;
    }

    private static final boolean addConditionally$atomicfu$array(Object obj, AtomicIntegerArray atomicIntegerArray, int i2, int i3, Function1<? super Integer, Boolean> function1) {
        int i4;
        do {
            i4 = atomicIntegerArray.get(i2);
            if (!function1.invoke(Integer.valueOf(i4)).booleanValue()) {
                return false;
            }
        } while (!atomicIntegerArray.compareAndSet(i2, i4, i4 + i3));
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [kotlinx.coroutines.internal.ConcurrentLinkedListNode] */
    @NotNull
    public static final <N extends ConcurrentLinkedListNode<N>> N close(@NotNull N n2) {
        while (true) {
            Object nextOrClosed = n2.getNextOrClosed();
            if (nextOrClosed == CLOSED) {
                return n2;
            }
            ?? r02 = (ConcurrentLinkedListNode) nextOrClosed;
            if (r02 != 0) {
                n2 = r02;
            } else if (n2.markAsClosed()) {
                return n2;
            }
        }
    }

    @NotNull
    public static final Object findSegmentAndMoveForward$atomicfu(@Nullable Object obj, @NotNull AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, long j2, @NotNull Segment segment, @NotNull Function2 function2) {
        Object objFindSegmentInternal;
        loop0: while (true) {
            objFindSegmentInternal = findSegmentInternal(segment, j2, function2);
            if (!SegmentOrClosed.m2388isClosedimpl(objFindSegmentInternal)) {
                Segment segmentM2386getSegmentimpl = SegmentOrClosed.m2386getSegmentimpl(objFindSegmentInternal);
                while (true) {
                    Segment segment2 = (Segment) atomicReferenceFieldUpdater.get(obj);
                    if (segment2.id >= segmentM2386getSegmentimpl.id) {
                        break loop0;
                    }
                    if (!segmentM2386getSegmentimpl.tryIncPointers$kotlinx_coroutines_core()) {
                        break;
                    }
                    if (androidx.concurrent.futures.a.a(atomicReferenceFieldUpdater, obj, segment2, segmentM2386getSegmentimpl)) {
                        if (segment2.decPointers$kotlinx_coroutines_core()) {
                            segment2.remove();
                        }
                    } else if (segmentM2386getSegmentimpl.decPointers$kotlinx_coroutines_core()) {
                        segmentM2386getSegmentimpl.remove();
                    }
                }
            } else {
                break;
            }
        }
        return objFindSegmentInternal;
    }

    @NotNull
    public static final Object findSegmentAndMoveForward$atomicfu$array(@Nullable Object obj, @NotNull AtomicReferenceArray atomicReferenceArray, int i2, long j2, @NotNull Segment segment, @NotNull Function2 function2) {
        Object objFindSegmentInternal;
        loop0: while (true) {
            objFindSegmentInternal = findSegmentInternal(segment, j2, function2);
            if (!SegmentOrClosed.m2388isClosedimpl(objFindSegmentInternal)) {
                Segment segmentM2386getSegmentimpl = SegmentOrClosed.m2386getSegmentimpl(objFindSegmentInternal);
                while (true) {
                    Segment segment2 = (Segment) atomicReferenceArray.get(i2);
                    if (segment2.id >= segmentM2386getSegmentimpl.id) {
                        break loop0;
                    }
                    if (!segmentM2386getSegmentimpl.tryIncPointers$kotlinx_coroutines_core()) {
                        break;
                    }
                    if (i0.a(atomicReferenceArray, i2, segment2, segmentM2386getSegmentimpl)) {
                        if (segment2.decPointers$kotlinx_coroutines_core()) {
                            segment2.remove();
                        }
                    } else if (segmentM2386getSegmentimpl.decPointers$kotlinx_coroutines_core()) {
                        segmentM2386getSegmentimpl.remove();
                    }
                }
            } else {
                break;
            }
        }
        return objFindSegmentInternal;
    }

    @NotNull
    public static final <S extends Segment<S>> Object findSegmentInternal(@NotNull S s2, long j2, @NotNull Function2<? super Long, ? super S, ? extends S> function2) {
        while (true) {
            if (s2.id >= j2 && !s2.isRemoved()) {
                return SegmentOrClosed.m2383constructorimpl(s2);
            }
            Object nextOrClosed = s2.getNextOrClosed();
            if (nextOrClosed == CLOSED) {
                return SegmentOrClosed.m2383constructorimpl(CLOSED);
            }
            S sMo1invoke = (S) ((ConcurrentLinkedListNode) nextOrClosed);
            if (sMo1invoke == null) {
                sMo1invoke = function2.mo1invoke(Long.valueOf(s2.id + 1), s2);
                if (s2.trySetNext(sMo1invoke)) {
                    if (s2.isRemoved()) {
                        s2.remove();
                    }
                }
            }
            s2 = sMo1invoke;
        }
    }

    public static final boolean moveForward$atomicfu(@Nullable Object obj, @NotNull AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, @NotNull Segment segment) {
        while (true) {
            Segment segment2 = (Segment) atomicReferenceFieldUpdater.get(obj);
            if (segment2.id >= segment.id) {
                return true;
            }
            if (!segment.tryIncPointers$kotlinx_coroutines_core()) {
                return false;
            }
            if (androidx.concurrent.futures.a.a(atomicReferenceFieldUpdater, obj, segment2, segment)) {
                if (segment2.decPointers$kotlinx_coroutines_core()) {
                    segment2.remove();
                }
                return true;
            }
            if (segment.decPointers$kotlinx_coroutines_core()) {
                segment.remove();
            }
        }
    }

    public static final boolean moveForward$atomicfu$array(@Nullable Object obj, @NotNull AtomicReferenceArray atomicReferenceArray, int i2, @NotNull Segment segment) {
        while (true) {
            Segment segment2 = (Segment) atomicReferenceArray.get(i2);
            if (segment2.id >= segment.id) {
                return true;
            }
            if (!segment.tryIncPointers$kotlinx_coroutines_core()) {
                return false;
            }
            if (i0.a(atomicReferenceArray, i2, segment2, segment)) {
                if (segment2.decPointers$kotlinx_coroutines_core()) {
                    segment2.remove();
                }
                return true;
            }
            if (segment.decPointers$kotlinx_coroutines_core()) {
                segment.remove();
            }
        }
    }
}
