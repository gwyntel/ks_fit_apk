package kotlinx.coroutines.channels;

import androidx.exifinterface.media.ExifInterface;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.Waiter;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.selects.SelectInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\n\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002B;\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\"\b\u0002\u0010\n\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\t¢\u0006\u0004\b\u000b\u0010\fJ.\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\b0\u00102\u0006\u0010\r\u001a\u00028\u00002\u0006\u0010\u000f\u001a\u00020\u000eH\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\b\u0011\u0010\u0012J.\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\b0\u00102\u0006\u0010\r\u001a\u00028\u00002\u0006\u0010\u000f\u001a\u00020\u000eH\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\b\u0014\u0010\u0012J&\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\b0\u00102\u0006\u0010\r\u001a\u00028\u0000H\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\b\u0016\u0010\u0017J\u001b\u0010\u0019\u001a\u00020\b2\u0006\u0010\r\u001a\u00028\u0000H\u0096@ø\u0001\u0002¢\u0006\u0004\b\u0019\u0010\u001aJ\u001b\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00028\u0000H\u0090@ø\u0001\u0002¢\u0006\u0004\b\u001b\u0010\u001aJ&\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\b0\u00102\u0006\u0010\r\u001a\u00028\u0000H\u0016ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\b\u001d\u0010\u0017J%\u0010\"\u001a\u00020\b2\n\u0010 \u001a\u0006\u0012\u0002\b\u00030\u001f2\b\u0010\r\u001a\u0004\u0018\u00010!H\u0014¢\u0006\u0004\b\"\u0010#J\u000f\u0010&\u001a\u00020\u000eH\u0010¢\u0006\u0004\b$\u0010%R\u0014\u0010\u0004\u001a\u00020\u00038\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0004\u0010'R\u0014\u0010\u0006\u001a\u00020\u00058\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0006\u0010(R\u0014\u0010*\u001a\u00020\u000e8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b)\u0010%\u0082\u0002\u000f\n\u0002\b!\n\u0005\b¡\u001e0\u0001\n\u0002\b\u0019¨\u0006+"}, d2 = {"Lkotlinx/coroutines/channels/ConflatedBufferedChannel;", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/BufferedChannel;", "", "capacity", "Lkotlinx/coroutines/channels/BufferOverflow;", "onBufferOverflow", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "onUndeliveredElement", "<init>", "(ILkotlinx/coroutines/channels/BufferOverflow;Lkotlin/jvm/functions/Function1;)V", "element", "", "isSendOp", "Lkotlinx/coroutines/channels/ChannelResult;", "trySendImpl-Mj0NB7M", "(Ljava/lang/Object;Z)Ljava/lang/Object;", "trySendImpl", "trySendDropLatest-Mj0NB7M", "trySendDropLatest", "trySendDropOldest-JP2dKIU", "(Ljava/lang/Object;)Ljava/lang/Object;", "trySendDropOldest", "send", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendBroadcast$kotlinx_coroutines_core", "sendBroadcast", "trySend-JP2dKIU", "trySend", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "", "l", "(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;)V", "shouldSendSuspend$kotlinx_coroutines_core", "()Z", "shouldSendSuspend", "I", "Lkotlinx/coroutines/channels/BufferOverflow;", "f", "isConflatedDropOldest", "kotlinx-coroutines-core"}, k = 1, mv = {1, 8, 0})
@SourceDebugExtension({"SMAP\nConflatedBufferedChannel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ConflatedBufferedChannel.kt\nkotlinx/coroutines/channels/ConflatedBufferedChannel\n+ 2 Channel.kt\nkotlinx/coroutines/channels/ChannelKt\n+ 3 BufferedChannel.kt\nkotlinx/coroutines/channels/BufferedChannel\n+ 4 BufferedChannel.kt\nkotlinx/coroutines/channels/BufferedChannelKt\n+ 5 BufferedChannel.kt\nkotlinx/coroutines/channels/BufferedChannel$sendImpl$1\n*L\n1#1,119:1\n548#2,5:120\n514#2,6:125\n514#2,6:212\n548#2,5:218\n244#3:131\n269#3,10:132\n280#3,68:143\n3038#4:142\n269#5:211\n*S KotlinDebug\n*F\n+ 1 ConflatedBufferedChannel.kt\nkotlinx/coroutines/channels/ConflatedBufferedChannel\n*L\n41#1:120,5\n53#1:125,6\n106#1:212,6\n109#1:218,5\n80#1:131\n80#1:132,10\n80#1:143,68\n80#1:142\n80#1:211\n*E\n"})
/* loaded from: classes5.dex */
public class ConflatedBufferedChannel<E> extends BufferedChannel<E> {
    private final int capacity;

    @NotNull
    private final BufferOverflow onBufferOverflow;

    public /* synthetic */ ConflatedBufferedChannel(int i2, BufferOverflow bufferOverflow, Function1 function1, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i2, bufferOverflow, (i3 & 4) != 0 ? null : function1);
    }

    static /* synthetic */ Object o(ConflatedBufferedChannel conflatedBufferedChannel, Object obj, Continuation continuation) throws Throwable {
        UndeliveredElementException undeliveredElementExceptionCallUndeliveredElementCatchingException$default;
        Object objM2353trySendImplMj0NB7M = conflatedBufferedChannel.m2353trySendImplMj0NB7M(obj, true);
        if (!(objM2353trySendImplMj0NB7M instanceof ChannelResult.Closed)) {
            return Unit.INSTANCE;
        }
        ChannelResult.m2339exceptionOrNullimpl(objM2353trySendImplMj0NB7M);
        Function1<E, Unit> function1 = conflatedBufferedChannel.onUndeliveredElement;
        if (function1 == null || (undeliveredElementExceptionCallUndeliveredElementCatchingException$default = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, obj, null, 2, null)) == null) {
            throw conflatedBufferedChannel.d();
        }
        ExceptionsKt.addSuppressed(undeliveredElementExceptionCallUndeliveredElementCatchingException$default, conflatedBufferedChannel.d());
        throw undeliveredElementExceptionCallUndeliveredElementCatchingException$default;
    }

    static /* synthetic */ Object p(ConflatedBufferedChannel conflatedBufferedChannel, Object obj, Continuation continuation) {
        Object objM2353trySendImplMj0NB7M = conflatedBufferedChannel.m2353trySendImplMj0NB7M(obj, true);
        if (objM2353trySendImplMj0NB7M instanceof ChannelResult.Failed) {
            return Boxing.boxBoolean(false);
        }
        return Boxing.boxBoolean(true);
    }

    /* renamed from: trySendDropLatest-Mj0NB7M, reason: not valid java name */
    private final Object m2351trySendDropLatestMj0NB7M(E element, boolean isSendOp) {
        Function1<E, Unit> function1;
        UndeliveredElementException undeliveredElementExceptionCallUndeliveredElementCatchingException$default;
        Object objMo2326trySendJP2dKIU = super.mo2326trySendJP2dKIU(element);
        if (ChannelResult.m2345isSuccessimpl(objMo2326trySendJP2dKIU) || ChannelResult.m2343isClosedimpl(objMo2326trySendJP2dKIU)) {
            return objMo2326trySendJP2dKIU;
        }
        if (!isSendOp || (function1 = this.onUndeliveredElement) == null || (undeliveredElementExceptionCallUndeliveredElementCatchingException$default = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, element, null, 2, null)) == null) {
            return ChannelResult.INSTANCE.m2350successJP2dKIU(Unit.INSTANCE);
        }
        throw undeliveredElementExceptionCallUndeliveredElementCatchingException$default;
    }

    /* renamed from: trySendDropOldest-JP2dKIU, reason: not valid java name */
    private final Object m2352trySendDropOldestJP2dKIU(E element) {
        ChannelSegment channelSegment;
        Object obj = BufferedChannelKt.BUFFERED;
        ChannelSegment channelSegment2 = (ChannelSegment) BufferedChannel.sendSegment$FU.get(this);
        while (true) {
            long andIncrement = BufferedChannel.sendersAndCloseStatus$FU.getAndIncrement(this);
            long j2 = andIncrement & 1152921504606846975L;
            boolean zIsClosedForSend0 = isClosedForSend0(andIncrement);
            int i2 = BufferedChannelKt.SEGMENT_SIZE;
            long j3 = j2 / i2;
            int i3 = (int) (j2 % i2);
            if (channelSegment2.id != j3) {
                ChannelSegment channelSegmentFindSegmentSend = findSegmentSend(j3, channelSegment2);
                if (channelSegmentFindSegmentSend != null) {
                    channelSegment = channelSegmentFindSegmentSend;
                } else if (zIsClosedForSend0) {
                    return ChannelResult.INSTANCE.m2348closedJP2dKIU(d());
                }
            } else {
                channelSegment = channelSegment2;
            }
            int iUpdateCellSend = updateCellSend(channelSegment, i3, element, j2, obj, zIsClosedForSend0);
            if (iUpdateCellSend == 0) {
                channelSegment.cleanPrev();
                return ChannelResult.INSTANCE.m2350successJP2dKIU(Unit.INSTANCE);
            }
            if (iUpdateCellSend == 1) {
                return ChannelResult.INSTANCE.m2350successJP2dKIU(Unit.INSTANCE);
            }
            if (iUpdateCellSend == 2) {
                if (zIsClosedForSend0) {
                    channelSegment.onSlotCleaned();
                    return ChannelResult.INSTANCE.m2348closedJP2dKIU(d());
                }
                Waiter waiter = obj instanceof Waiter ? (Waiter) obj : null;
                if (waiter != null) {
                    prepareSenderForSuspension(waiter, channelSegment, i3);
                }
                b((channelSegment.id * i2) + i3);
                return ChannelResult.INSTANCE.m2350successJP2dKIU(Unit.INSTANCE);
            }
            if (iUpdateCellSend == 3) {
                throw new IllegalStateException("unexpected".toString());
            }
            if (iUpdateCellSend == 4) {
                if (j2 < getReceiversCounter$kotlinx_coroutines_core()) {
                    channelSegment.cleanPrev();
                }
                return ChannelResult.INSTANCE.m2348closedJP2dKIU(d());
            }
            if (iUpdateCellSend == 5) {
                channelSegment.cleanPrev();
            }
            channelSegment2 = channelSegment;
        }
    }

    /* renamed from: trySendImpl-Mj0NB7M, reason: not valid java name */
    private final Object m2353trySendImplMj0NB7M(E element, boolean isSendOp) {
        return this.onBufferOverflow == BufferOverflow.DROP_LATEST ? m2351trySendDropLatestMj0NB7M(element, isSendOp) : m2352trySendDropOldestJP2dKIU(element);
    }

    @Override // kotlinx.coroutines.channels.BufferedChannel
    protected boolean f() {
        return this.onBufferOverflow == BufferOverflow.DROP_OLDEST;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlinx.coroutines.channels.BufferedChannel
    protected void l(SelectInstance select, Object element) {
        Object objMo2326trySendJP2dKIU = mo2326trySendJP2dKIU(element);
        if (!(objMo2326trySendJP2dKIU instanceof ChannelResult.Failed)) {
            select.selectInRegistrationPhase(Unit.INSTANCE);
        } else {
            if (!(objMo2326trySendJP2dKIU instanceof ChannelResult.Closed)) {
                throw new IllegalStateException("unreachable".toString());
            }
            ChannelResult.m2339exceptionOrNullimpl(objMo2326trySendJP2dKIU);
            select.selectInRegistrationPhase(BufferedChannelKt.getCHANNEL_CLOSED());
        }
    }

    @Override // kotlinx.coroutines.channels.BufferedChannel, kotlinx.coroutines.channels.SendChannel
    @Nullable
    public Object send(E e2, @NotNull Continuation<? super Unit> continuation) {
        return o(this, e2, continuation);
    }

    @Override // kotlinx.coroutines.channels.BufferedChannel
    @Nullable
    public Object sendBroadcast$kotlinx_coroutines_core(E e2, @NotNull Continuation<? super Boolean> continuation) {
        return p(this, e2, continuation);
    }

    @Override // kotlinx.coroutines.channels.BufferedChannel
    public boolean shouldSendSuspend$kotlinx_coroutines_core() {
        return false;
    }

    @Override // kotlinx.coroutines.channels.BufferedChannel, kotlinx.coroutines.channels.SendChannel
    @NotNull
    /* renamed from: trySend-JP2dKIU */
    public Object mo2326trySendJP2dKIU(E element) {
        return m2353trySendImplMj0NB7M(element, false);
    }

    public ConflatedBufferedChannel(int i2, @NotNull BufferOverflow bufferOverflow, @Nullable Function1<? super E, Unit> function1) {
        super(i2, function1);
        this.capacity = i2;
        this.onBufferOverflow = bufferOverflow;
        if (bufferOverflow == BufferOverflow.SUSPEND) {
            throw new IllegalArgumentException(("This implementation does not support suspension for senders, use " + Reflection.getOrCreateKotlinClass(BufferedChannel.class).getSimpleName() + " instead").toString());
        }
        if (i2 >= 1) {
            return;
        }
        throw new IllegalArgumentException(("Buffered channel capacity must be at least 1, but " + i2 + " was specified").toString());
    }
}
