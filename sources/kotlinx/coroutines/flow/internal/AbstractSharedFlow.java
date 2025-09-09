package kotlinx.coroutines.flow.internal;

import androidx.exifinterface.media.ExifInterface;
import com.umeng.analytics.pro.bc;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b \u0018\u0000*\f\b\u0000\u0010\u0002*\u0006\u0012\u0002\b\u00030\u00012\u00060\u0003j\u0002`\u0004B\u0007¢\u0006\u0004\b\u0005\u0010\u0006J\u000f\u0010\u0007\u001a\u00028\u0000H$¢\u0006\u0004\b\u0007\u0010\bJ\u001f\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u000b2\u0006\u0010\n\u001a\u00020\tH$¢\u0006\u0004\b\f\u0010\rJ\u000f\u0010\u000e\u001a\u00028\u0000H\u0004¢\u0006\u0004\b\u000e\u0010\bJ\u0017\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00028\u0000H\u0004¢\u0006\u0004\b\u0011\u0010\u0012R8\u0010\u0014\u001a\f\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0018\u00010\u000b2\u0010\u0010\u0013\u001a\f\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0018\u00010\u000b8\u0004@BX\u0084\u000e¢\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R$\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\t8\u0004@BX\u0084\u000e¢\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u0016\u0010\u001c\u001a\u00020\t8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u001c\u0010\u0019R\u0018\u0010\u001e\u001a\u0004\u0018\u00010\u001d8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u001e\u0010\u001fR\u0017\u0010#\u001a\b\u0012\u0004\u0012\u00020\t0 8F¢\u0006\u0006\u001a\u0004\b!\u0010\"¨\u0006$"}, d2 = {"Lkotlinx/coroutines/flow/internal/AbstractSharedFlow;", "Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", ExifInterface.LATITUDE_SOUTH, "", "Lkotlinx/coroutines/internal/SynchronizedObject;", "<init>", "()V", "createSlot", "()Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "", "size", "", "createSlotArray", "(I)[Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "a", "slot", "", "b", "(Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;)V", "<set-?>", "slots", "[Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "d", "()[Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "nCollectors", "I", bc.aL, "()I", "nextIndex", "Lkotlinx/coroutines/flow/internal/SubscriptionCountStateFlow;", "_subscriptionCount", "Lkotlinx/coroutines/flow/internal/SubscriptionCountStateFlow;", "Lkotlinx/coroutines/flow/StateFlow;", "getSubscriptionCount", "()Lkotlinx/coroutines/flow/StateFlow;", "subscriptionCount", "kotlinx-coroutines-core"}, k = 1, mv = {1, 8, 0})
@SourceDebugExtension({"SMAP\nAbstractSharedFlow.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AbstractSharedFlow.kt\nkotlinx/coroutines/flow/internal/AbstractSharedFlow\n+ 2 Synchronized.common.kt\nkotlinx/coroutines/internal/Synchronized_commonKt\n+ 3 Synchronized.kt\nkotlinx/coroutines/internal/SynchronizedKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 5 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,132:1\n28#2,4:133\n28#2,4:138\n28#2,4:144\n20#3:137\n20#3:142\n20#3:148\n1#4:143\n13579#5,2:149\n*S KotlinDebug\n*F\n+ 1 AbstractSharedFlow.kt\nkotlinx/coroutines/flow/internal/AbstractSharedFlow\n*L\n30#1:133,4\n45#1:138,4\n76#1:144,4\n30#1:137\n45#1:142\n76#1:148\n95#1:149,2\n*E\n"})
/* loaded from: classes5.dex */
public abstract class AbstractSharedFlow<S extends AbstractSharedFlowSlot<?>> {

    @Nullable
    private SubscriptionCountStateFlow _subscriptionCount;
    private int nCollectors;
    private int nextIndex;

    @Nullable
    private S[] slots;

    protected final AbstractSharedFlowSlot a() {
        AbstractSharedFlowSlot[] abstractSharedFlowSlotArr;
        AbstractSharedFlowSlot abstractSharedFlowSlot;
        SubscriptionCountStateFlow subscriptionCountStateFlow;
        synchronized (this) {
            try {
                S[] sArr = this.slots;
                if (sArr == null) {
                    S[] sArr2 = (S[]) createSlotArray(2);
                    this.slots = sArr2;
                    abstractSharedFlowSlotArr = sArr2;
                } else {
                    int i2 = this.nCollectors;
                    int length = sArr.length;
                    abstractSharedFlowSlotArr = sArr;
                    if (i2 >= length) {
                        Object[] objArrCopyOf = Arrays.copyOf(sArr, sArr.length * 2);
                        Intrinsics.checkNotNullExpressionValue(objArrCopyOf, "copyOf(this, newSize)");
                        this.slots = (S[]) ((AbstractSharedFlowSlot[]) objArrCopyOf);
                        abstractSharedFlowSlotArr = (S[]) ((AbstractSharedFlowSlot[]) objArrCopyOf);
                    }
                }
                int i3 = this.nextIndex;
                do {
                    AbstractSharedFlowSlot abstractSharedFlowSlot2 = abstractSharedFlowSlotArr[i3];
                    abstractSharedFlowSlot = abstractSharedFlowSlot2;
                    if (abstractSharedFlowSlot2 == null) {
                        AbstractSharedFlowSlot abstractSharedFlowSlotCreateSlot = createSlot();
                        abstractSharedFlowSlotArr[i3] = abstractSharedFlowSlotCreateSlot;
                        abstractSharedFlowSlot = abstractSharedFlowSlotCreateSlot;
                    }
                    i3++;
                    if (i3 >= abstractSharedFlowSlotArr.length) {
                        i3 = 0;
                    }
                    Intrinsics.checkNotNull(abstractSharedFlowSlot, "null cannot be cast to non-null type kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot<kotlin.Any>");
                } while (!abstractSharedFlowSlot.allocateLocked(this));
                this.nextIndex = i3;
                this.nCollectors++;
                subscriptionCountStateFlow = this._subscriptionCount;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (subscriptionCountStateFlow != null) {
            subscriptionCountStateFlow.increment(1);
        }
        return abstractSharedFlowSlot;
    }

    protected final void b(AbstractSharedFlowSlot slot) {
        SubscriptionCountStateFlow subscriptionCountStateFlow;
        int i2;
        Continuation<Unit>[] continuationArrFreeLocked;
        synchronized (this) {
            try {
                int i3 = this.nCollectors - 1;
                this.nCollectors = i3;
                subscriptionCountStateFlow = this._subscriptionCount;
                if (i3 == 0) {
                    this.nextIndex = 0;
                }
                Intrinsics.checkNotNull(slot, "null cannot be cast to non-null type kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot<kotlin.Any>");
                continuationArrFreeLocked = slot.freeLocked(this);
            } catch (Throwable th) {
                throw th;
            }
        }
        for (Continuation<Unit> continuation : continuationArrFreeLocked) {
            if (continuation != null) {
                Result.Companion companion = Result.INSTANCE;
                continuation.resumeWith(Result.m837constructorimpl(Unit.INSTANCE));
            }
        }
        if (subscriptionCountStateFlow != null) {
            subscriptionCountStateFlow.increment(-1);
        }
    }

    /* renamed from: c, reason: from getter */
    protected final int getNCollectors() {
        return this.nCollectors;
    }

    protected abstract AbstractSharedFlowSlot createSlot();

    protected abstract AbstractSharedFlowSlot[] createSlotArray(int size);

    /* renamed from: d, reason: from getter */
    protected final AbstractSharedFlowSlot[] getSlots() {
        return this.slots;
    }

    @NotNull
    public final StateFlow<Integer> getSubscriptionCount() {
        SubscriptionCountStateFlow subscriptionCountStateFlow;
        synchronized (this) {
            subscriptionCountStateFlow = this._subscriptionCount;
            if (subscriptionCountStateFlow == null) {
                subscriptionCountStateFlow = new SubscriptionCountStateFlow(this.nCollectors);
                this._subscriptionCount = subscriptionCountStateFlow;
            }
        }
        return subscriptionCountStateFlow;
    }
}
