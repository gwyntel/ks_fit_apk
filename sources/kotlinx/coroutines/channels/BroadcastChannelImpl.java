package kotlinx.coroutines.channels;

import androidx.exifinterface.media.ExifInterface;
import com.alipay.sdk.m.u.i;
import com.kingsmith.miot.KsProperty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.selects.SelectInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u00022\b\u0012\u0004\u0012\u00028\u00000\u0003:\u0002?@B\u000f\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\u001d\u0010\u000b\u001a\u00020\n2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0002¢\u0006\u0004\b\u000b\u0010\fJ\u0015\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016¢\u0006\u0004\b\r\u0010\u000eJ\u001b\u0010\u0010\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00028\u0000H\u0096@ø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u0011J&\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u000f\u001a\u00028\u0000H\u0016ø\u0001\u0001ø\u0001\u0002ø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\u0014J%\u0010\u0019\u001a\u00020\n2\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u00162\b\u0010\u000f\u001a\u0004\u0018\u00010\u0018H\u0014¢\u0006\u0004\b\u0019\u0010\u001aJ\u0019\u0010\u001e\u001a\u00020\u001d2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001bH\u0016¢\u0006\u0004\b\u001e\u0010\u001fJ\u0019\u0010!\u001a\u00020\u001d2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001bH\u0010¢\u0006\u0004\b \u0010\u001fJ\u000f\u0010#\u001a\u00020\"H\u0016¢\u0006\u0004\b#\u0010$R\u0017\u0010\u0005\u001a\u00020\u00048\u0006¢\u0006\f\n\u0004\b\u0005\u0010%\u001a\u0004\b&\u0010'R\u0018\u0010*\u001a\u00060(j\u0002`)8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b*\u0010+R\"\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00020,8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b-\u0010.R\u0018\u0010/\u001a\u0004\u0018\u00010\u00188\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b/\u00100R<\u00103\u001a*\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u001801j\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0018`28\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b3\u00104R\u0017\u00109\u001a\u00028\u00008F¢\u0006\f\u0012\u0004\b7\u00108\u001a\u0004\b5\u00106R\u0019\u0010<\u001a\u0004\u0018\u00018\u00008F¢\u0006\f\u0012\u0004\b;\u00108\u001a\u0004\b:\u00106R\u0014\u0010=\u001a\u00020\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b=\u0010>\u0082\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006A"}, d2 = {"Lkotlinx/coroutines/channels/BroadcastChannelImpl;", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/BufferedChannel;", "Lkotlinx/coroutines/channels/BroadcastChannel;", "", "capacity", "<init>", "(I)V", "Lkotlinx/coroutines/channels/ReceiveChannel;", "s", "", "removeSubscriber", "(Lkotlinx/coroutines/channels/ReceiveChannel;)V", "openSubscription", "()Lkotlinx/coroutines/channels/ReceiveChannel;", "element", "send", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ChannelResult;", "trySend-JP2dKIU", "(Ljava/lang/Object;)Ljava/lang/Object;", "trySend", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "", "l", "(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;)V", "", "cause", "", "close", "(Ljava/lang/Throwable;)Z", "cancelImpl$kotlinx_coroutines_core", "cancelImpl", "", "toString", "()Ljava/lang/String;", "I", "getCapacity", "()I", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", KsProperty.Lock, "Ljava/util/concurrent/locks/ReentrantLock;", "", "subscribers", "Ljava/util/List;", "lastConflatedElement", "Ljava/lang/Object;", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "onSendInternalResult", "Ljava/util/HashMap;", "getValue", "()Ljava/lang/Object;", "getValue$annotations", "()V", "value", "getValueOrNull", "getValueOrNull$annotations", "valueOrNull", "isClosedForSend", "()Z", "SubscriberBuffered", "SubscriberConflated", "kotlinx-coroutines-core"}, k = 1, mv = {1, 8, 0})
@SourceDebugExtension({"SMAP\nBroadcastChannel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BroadcastChannel.kt\nkotlinx/coroutines/channels/BroadcastChannelImpl\n+ 2 Concurrent.kt\nkotlinx/coroutines/internal/ConcurrentKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,414:1\n15#2:415\n15#2:416\n15#2:420\n15#2:423\n15#2:429\n15#2:430\n15#2:436\n15#2:439\n15#2:440\n15#2:441\n766#3:417\n857#3,2:418\n1855#3,2:421\n1747#3,3:424\n1855#3,2:427\n1855#3,2:431\n766#3:433\n857#3,2:434\n1855#3,2:437\n*S KotlinDebug\n*F\n+ 1 BroadcastChannel.kt\nkotlinx/coroutines/channels/BroadcastChannelImpl\n*L\n166#1:415\n188#1:416\n213#1:420\n237#1:423\n279#1:429\n331#1:430\n343#1:436\n355#1:439\n382#1:440\n394#1:441\n189#1:417\n189#1:418,2\n226#1:421,2\n242#1:424,3\n251#1:427,2\n333#1:431,2\n338#1:433\n338#1:434,2\n346#1:437,2\n*E\n"})
/* loaded from: classes5.dex */
public final class BroadcastChannelImpl<E> extends BufferedChannel<E> implements BroadcastChannel<E> {
    private final int capacity;

    @Nullable
    private Object lastConflatedElement;

    @NotNull
    private final ReentrantLock lock;

    @NotNull
    private final HashMap<SelectInstance<?>, Object> onSendInternalResult;

    @NotNull
    private List<? extends BufferedChannel<E>> subscribers;

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0003\n\u0000\b\u0082\u0004\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lkotlinx/coroutines/channels/BroadcastChannelImpl$SubscriberBuffered;", "Lkotlinx/coroutines/channels/BufferedChannel;", "(Lkotlinx/coroutines/channels/BroadcastChannelImpl;)V", "cancelImpl", "", "cause", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nBroadcastChannel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BroadcastChannel.kt\nkotlinx/coroutines/channels/BroadcastChannelImpl$SubscriberBuffered\n+ 2 Concurrent.kt\nkotlinx/coroutines/internal/ConcurrentKt\n*L\n1#1,414:1\n15#2:415\n*S KotlinDebug\n*F\n+ 1 BroadcastChannel.kt\nkotlinx/coroutines/channels/BroadcastChannelImpl$SubscriberBuffered\n*L\n362#1:415\n*E\n"})
    private final class SubscriberBuffered extends BufferedChannel<E> {
        public SubscriberBuffered() {
            super(BroadcastChannelImpl.this.getCapacity(), null, 2, 0 == true ? 1 : 0);
        }

        @Override // kotlinx.coroutines.channels.BufferedChannel
        /* renamed from: cancelImpl, reason: merged with bridge method [inline-methods] */
        public boolean cancelImpl$kotlinx_coroutines_core(@Nullable Throwable cause) {
            ReentrantLock reentrantLock = BroadcastChannelImpl.this.lock;
            BroadcastChannelImpl broadcastChannelImpl = BroadcastChannelImpl.this;
            reentrantLock.lock();
            try {
                broadcastChannelImpl.removeSubscriber(this);
                return super.cancelImpl$kotlinx_coroutines_core(cause);
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0003\n\u0000\b\u0082\u0004\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lkotlinx/coroutines/channels/BroadcastChannelImpl$SubscriberConflated;", "Lkotlinx/coroutines/channels/ConflatedBufferedChannel;", "(Lkotlinx/coroutines/channels/BroadcastChannelImpl;)V", "cancelImpl", "", "cause", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private final class SubscriberConflated extends ConflatedBufferedChannel<E> {
        public SubscriberConflated() {
            super(1, BufferOverflow.DROP_OLDEST, null, 4, null);
        }

        @Override // kotlinx.coroutines.channels.BufferedChannel
        /* renamed from: cancelImpl, reason: merged with bridge method [inline-methods] */
        public boolean cancelImpl$kotlinx_coroutines_core(@Nullable Throwable cause) {
            BroadcastChannelImpl.this.removeSubscriber(this);
            return super.cancelImpl$kotlinx_coroutines_core(cause);
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.BroadcastChannelImpl", f = "BroadcastChannel.kt", i = {0, 0}, l = {230}, m = "send", n = {"this", "element"}, s = {"L$0", "L$1"})
    /* renamed from: kotlinx.coroutines.channels.BroadcastChannelImpl$send$1, reason: invalid class name */
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ BroadcastChannelImpl<E> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(BroadcastChannelImpl<E> broadcastChannelImpl, Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
            this.this$0 = broadcastChannelImpl;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.send(null, this);
        }
    }

    public BroadcastChannelImpl(int i2) {
        super(0, null);
        this.capacity = i2;
        if (i2 >= 1 || i2 == -1) {
            this.lock = new ReentrantLock();
            this.subscribers = CollectionsKt.emptyList();
            this.lastConflatedElement = BroadcastChannelKt.NO_ELEMENT;
            this.onSendInternalResult = new HashMap<>();
            return;
        }
        throw new IllegalArgumentException(("BroadcastChannel capacity must be positive or Channel.CONFLATED, but " + i2 + " was specified").toString());
    }

    public static /* synthetic */ void getValue$annotations() {
    }

    public static /* synthetic */ void getValueOrNull$annotations() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void removeSubscriber(ReceiveChannel<? extends E> s2) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            List<? extends BufferedChannel<E>> list = this.subscribers;
            ArrayList arrayList = new ArrayList();
            for (Object obj : list) {
                if (((BufferedChannel) obj) != s2) {
                    arrayList.add(obj);
                }
            }
            this.subscribers = arrayList;
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    @Override // kotlinx.coroutines.channels.BufferedChannel
    public boolean cancelImpl$kotlinx_coroutines_core(@Nullable Throwable cause) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            Iterator<T> it = this.subscribers.iterator();
            while (it.hasNext()) {
                ((BufferedChannel) it.next()).cancelImpl$kotlinx_coroutines_core(cause);
            }
            this.lastConflatedElement = BroadcastChannelKt.NO_ELEMENT;
            boolean zCancelImpl$kotlinx_coroutines_core = super.cancelImpl$kotlinx_coroutines_core(cause);
            reentrantLock.unlock();
            return zCancelImpl$kotlinx_coroutines_core;
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlinx.coroutines.channels.BufferedChannel, kotlinx.coroutines.channels.SendChannel
    public boolean close(@Nullable Throwable cause) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            Iterator<T> it = this.subscribers.iterator();
            while (it.hasNext()) {
                ((BufferedChannel) it.next()).close(cause);
            }
            List<? extends BufferedChannel<E>> list = this.subscribers;
            ArrayList arrayList = new ArrayList();
            for (Object obj : list) {
                if (((BufferedChannel) obj).hasElements$kotlinx_coroutines_core()) {
                    arrayList.add(obj);
                }
            }
            this.subscribers = arrayList;
            boolean zClose = super.close(cause);
            reentrantLock.unlock();
            return zClose;
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    public final int getCapacity() {
        return this.capacity;
    }

    public final E getValue() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            if (isClosedForSend()) {
                Throwable thC = c();
                if (thC == null) {
                    throw new IllegalStateException("This broadcast channel is closed");
                }
                throw thC;
            }
            if (this.lastConflatedElement == BroadcastChannelKt.NO_ELEMENT) {
                throw new IllegalStateException("No value".toString());
            }
            E e2 = (E) this.lastConflatedElement;
            reentrantLock.unlock();
            return e2;
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    @Nullable
    public final E getValueOrNull() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            E e2 = null;
            if (!isClosedForReceive() && this.lastConflatedElement != BroadcastChannelKt.NO_ELEMENT) {
                e2 = (E) this.lastConflatedElement;
            }
            return e2;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // kotlinx.coroutines.channels.BufferedChannel, kotlinx.coroutines.channels.SendChannel
    public boolean isClosedForSend() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            return super.isClosedForSend();
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // kotlinx.coroutines.channels.BufferedChannel
    protected void l(SelectInstance select, Object element) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            Object objRemove = this.onSendInternalResult.remove(select);
            if (objRemove != null) {
                select.selectInRegistrationPhase(objRemove);
                return;
            }
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
            BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(select.getContext()), null, CoroutineStart.UNDISPATCHED, new BroadcastChannelImpl$registerSelectForSend$2(this, element, select, null), 1, null);
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // kotlinx.coroutines.channels.BroadcastChannel
    @NotNull
    public ReceiveChannel<E> openSubscription() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            SubscriberBuffered subscriberConflated = this.capacity == -1 ? new SubscriberConflated() : new SubscriberBuffered();
            if (isClosedForSend() && this.lastConflatedElement == BroadcastChannelKt.NO_ELEMENT) {
                ((BufferedChannel) subscriberConflated).close(c());
                reentrantLock.unlock();
                return subscriberConflated;
            }
            if (this.lastConflatedElement != BroadcastChannelKt.NO_ELEMENT) {
                ((BufferedChannel) subscriberConflated).mo2326trySendJP2dKIU(getValue());
            }
            this.subscribers = CollectionsKt.plus((Collection<? extends ReceiveChannel<E>>) this.subscribers, subscriberConflated);
            reentrantLock.unlock();
            return subscriberConflated;
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:29:0x007d -> B:30:0x0080). Please report as a decompilation issue!!! */
    @Override // kotlinx.coroutines.channels.BufferedChannel, kotlinx.coroutines.channels.SendChannel
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object send(E r7, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super kotlin.Unit> r8) throws java.lang.Throwable {
        /*
            r6 = this;
            boolean r0 = r8 instanceof kotlinx.coroutines.channels.BroadcastChannelImpl.AnonymousClass1
            if (r0 == 0) goto L13
            r0 = r8
            kotlinx.coroutines.channels.BroadcastChannelImpl$send$1 r0 = (kotlinx.coroutines.channels.BroadcastChannelImpl.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.channels.BroadcastChannelImpl$send$1 r0 = new kotlinx.coroutines.channels.BroadcastChannelImpl$send$1
            r0.<init>(r6, r8)
        L18:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3b
            if (r2 != r3) goto L33
            java.lang.Object r7 = r0.L$2
            java.util.Iterator r7 = (java.util.Iterator) r7
            java.lang.Object r2 = r0.L$1
            java.lang.Object r4 = r0.L$0
            kotlinx.coroutines.channels.BroadcastChannelImpl r4 = (kotlinx.coroutines.channels.BroadcastChannelImpl) r4
            kotlin.ResultKt.throwOnFailure(r8)
            goto L80
        L33:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L3b:
            kotlin.ResultKt.throwOnFailure(r8)
            java.util.concurrent.locks.ReentrantLock r8 = r6.lock
            r8.lock()
            boolean r2 = r6.isClosedForSend()     // Catch: java.lang.Throwable -> L51
            if (r2 != 0) goto L99
            int r2 = r6.capacity     // Catch: java.lang.Throwable -> L51
            r4 = -1
            if (r2 != r4) goto L53
            r6.lastConflatedElement = r7     // Catch: java.lang.Throwable -> L51
            goto L53
        L51:
            r7 = move-exception
            goto L9e
        L53:
            java.util.List<? extends kotlinx.coroutines.channels.BufferedChannel<E>> r2 = r6.subscribers     // Catch: java.lang.Throwable -> L51
            r8.unlock()
            java.lang.Iterable r2 = (java.lang.Iterable) r2
            java.util.Iterator r8 = r2.iterator()
            r4 = r6
            r5 = r8
            r8 = r7
            r7 = r5
        L62:
            boolean r2 = r7.hasNext()
            if (r2 == 0) goto L96
            java.lang.Object r2 = r7.next()
            kotlinx.coroutines.channels.BufferedChannel r2 = (kotlinx.coroutines.channels.BufferedChannel) r2
            r0.L$0 = r4
            r0.L$1 = r8
            r0.L$2 = r7
            r0.label = r3
            java.lang.Object r2 = r2.sendBroadcast$kotlinx_coroutines_core(r8, r0)
            if (r2 != r1) goto L7d
            return r1
        L7d:
            r5 = r2
            r2 = r8
            r8 = r5
        L80:
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 != 0) goto L94
            boolean r8 = r4.isClosedForSend()
            if (r8 != 0) goto L8f
            goto L94
        L8f:
            java.lang.Throwable r7 = r4.d()
            throw r7
        L94:
            r8 = r2
            goto L62
        L96:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        L99:
            java.lang.Throwable r7 = r6.d()     // Catch: java.lang.Throwable -> L51
            throw r7     // Catch: java.lang.Throwable -> L51
        L9e:
            r8.unlock()
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BroadcastChannelImpl.send(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // kotlinx.coroutines.channels.BufferedChannel
    @NotNull
    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        if (this.lastConflatedElement != BroadcastChannelKt.NO_ELEMENT) {
            str = "CONFLATED_ELEMENT=" + this.lastConflatedElement + "; ";
        } else {
            str = "";
        }
        sb.append(str);
        sb.append("BROADCAST=<");
        sb.append(super.toString());
        sb.append(">; SUBSCRIBERS=");
        sb.append(CollectionsKt.joinToString$default(this.subscribers, i.f9802b, "<", ">", 0, null, null, 56, null));
        return sb.toString();
    }

    @Override // kotlinx.coroutines.channels.BufferedChannel, kotlinx.coroutines.channels.SendChannel
    @NotNull
    /* renamed from: trySend-JP2dKIU, reason: not valid java name */
    public Object mo2326trySendJP2dKIU(E element) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            if (isClosedForSend()) {
                return super.mo2326trySendJP2dKIU(element);
            }
            List<? extends BufferedChannel<E>> list = this.subscribers;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    if (((BufferedChannel) it.next()).shouldSendSuspend$kotlinx_coroutines_core()) {
                        return ChannelResult.INSTANCE.m2349failurePtdJZtk();
                    }
                }
            }
            if (this.capacity == -1) {
                this.lastConflatedElement = element;
            }
            Iterator<T> it2 = this.subscribers.iterator();
            while (it2.hasNext()) {
                ((BufferedChannel) it2.next()).mo2326trySendJP2dKIU(element);
            }
            return ChannelResult.INSTANCE.m2350successJP2dKIU(Unit.INSTANCE);
        } finally {
            reentrantLock.unlock();
        }
    }
}
