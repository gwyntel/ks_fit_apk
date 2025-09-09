package kotlinx.coroutines.channels;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.tekartik.sqflite.Constant;
import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.AbstractCoroutine;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobCancellationException;
import kotlinx.coroutines.JobSupport;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.selects.SelectClause2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0012\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00020\u00030\u00022\b\u0012\u0004\u0012\u00028\u00000\u00042\b\u0012\u0004\u0012\u00028\u00000\u0005B%\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0006\u0010\n\u001a\u00020\t¢\u0006\u0004\b\u000b\u0010\fJ5\u0010\u0013\u001a\u00020\u00032#\u0010\u0012\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u000e¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00030\rH\u0096\u0001¢\u0006\u0004\b\u0013\u0010\u0014J\u0018\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00028\u0000H\u0097\u0001¢\u0006\u0004\b\u0016\u0010\u0017J\u0016\u0010\u0019\u001a\b\u0012\u0004\u0012\u00028\u00000\u0018H\u0096\u0001¢\u0006\u0004\b\u0019\u0010\u001aJ\u001b\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00028\u0000H\u0096Aø\u0001\u0000¢\u0006\u0004\b\u001b\u0010\u001cJ'\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00030\u001d2\u0006\u0010\u0015\u001a\u00028\u0000H\u0096\u0001ø\u0001\u0001ø\u0001\u0002ø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001fJ\u0019\u0010!\u001a\u00020\t2\b\u0010\u0011\u001a\u0004\u0018\u00010\u000eH\u0007¢\u0006\u0004\b!\u0010\"J\u001d\u0010!\u001a\u00020\u00032\u000e\u0010\u0011\u001a\n\u0018\u00010#j\u0004\u0018\u0001`$¢\u0006\u0004\b!\u0010%J\u0017\u0010&\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u000eH\u0016¢\u0006\u0004\b&\u0010'J\u0017\u0010)\u001a\u00020\u00032\u0006\u0010(\u001a\u00020\u0003H\u0014¢\u0006\u0004\b)\u0010*J\u001f\u0010,\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020\tH\u0014¢\u0006\u0004\b,\u0010-J\u0019\u0010.\u001a\u00020\t2\b\u0010\u0011\u001a\u0004\u0018\u00010\u000eH\u0016¢\u0006\u0004\b.\u0010\"R \u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00058\u0004X\u0084\u0004¢\u0006\f\n\u0004\b\b\u0010/\u001a\u0004\b0\u00101R\u0014\u00102\u001a\u00020\t8\u0016X\u0097\u0005¢\u0006\u0006\u001a\u0004\b2\u00103R&\u00108\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u000005048\u0016X\u0096\u0005¢\u0006\u0006\u001a\u0004\b6\u00107R\u0014\u00109\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b9\u00103R\u001a\u0010<\u001a\b\u0012\u0004\u0012\u00028\u0000058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b:\u0010;\u0082\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006="}, d2 = {"Lkotlinx/coroutines/channels/BroadcastCoroutine;", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/AbstractCoroutine;", "", "Lkotlinx/coroutines/channels/ProducerScope;", "Lkotlinx/coroutines/channels/BroadcastChannel;", "Lkotlin/coroutines/CoroutineContext;", "parentContext", "_channel", "", AppMeasurementSdk.ConditionalUserProperty.ACTIVE, "<init>", "(Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/channels/BroadcastChannel;Z)V", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "handler", "invokeOnClose", "(Lkotlin/jvm/functions/Function1;)V", "element", "offer", "(Ljava/lang/Object;)Z", "Lkotlinx/coroutines/channels/ReceiveChannel;", "openSubscription", "()Lkotlinx/coroutines/channels/ReceiveChannel;", "send", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ChannelResult;", "trySend-JP2dKIU", "(Ljava/lang/Object;)Ljava/lang/Object;", "trySend", Constant.PARAM_CANCEL, "(Ljava/lang/Throwable;)Z", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "(Ljava/util/concurrent/CancellationException;)V", "cancelInternal", "(Ljava/lang/Throwable;)V", "value", "o", "(Lkotlin/Unit;)V", "handled", "m", "(Ljava/lang/Throwable;Z)V", "close", "Lkotlinx/coroutines/channels/BroadcastChannel;", "n", "()Lkotlinx/coroutines/channels/BroadcastChannel;", "isClosedForSend", "()Z", "Lkotlinx/coroutines/selects/SelectClause2;", "Lkotlinx/coroutines/channels/SendChannel;", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "onSend", "isActive", "getChannel", "()Lkotlinx/coroutines/channels/SendChannel;", "channel", "kotlinx-coroutines-core"}, k = 1, mv = {1, 8, 0})
@SourceDebugExtension({"SMAP\nBroadcast.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Broadcast.kt\nkotlinx/coroutines/channels/BroadcastCoroutine\n+ 2 JobSupport.kt\nkotlinx/coroutines/JobSupport\n*L\n1#1,202:1\n706#2,2:203\n706#2,2:205\n*S KotlinDebug\n*F\n+ 1 Broadcast.kt\nkotlinx/coroutines/channels/BroadcastCoroutine\n*L\n152#1:203,2\n157#1:205,2\n*E\n"})
/* loaded from: classes5.dex */
class BroadcastCoroutine<E> extends AbstractCoroutine<Unit> implements ProducerScope<E>, BroadcastChannel<E> {

    @NotNull
    private final BroadcastChannel<E> _channel;

    public BroadcastCoroutine(@NotNull CoroutineContext coroutineContext, @NotNull BroadcastChannel<E> broadcastChannel, boolean z2) {
        super(coroutineContext, false, z2);
        this._channel = broadcastChannel;
        f((Job) coroutineContext.get(Job.INSTANCE));
    }

    @Override // kotlinx.coroutines.JobSupport, kotlinx.coroutines.Job
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    public final /* synthetic */ boolean cancel(Throwable cause) {
        if (cause == null) {
            cause = new JobCancellationException(c(), null, this);
        }
        cancelInternal(cause);
        return true;
    }

    @Override // kotlinx.coroutines.JobSupport
    public void cancelInternal(@NotNull Throwable cause) {
        CancellationException cancellationException$default = JobSupport.toCancellationException$default(this, cause, null, 1, null);
        this._channel.cancel(cancellationException$default);
        cancelCoroutine(cancellationException$default);
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public boolean close(@Nullable Throwable cause) {
        boolean zClose = this._channel.close(cause);
        start();
        return zClose;
    }

    @Override // kotlinx.coroutines.channels.ProducerScope
    @NotNull
    public SendChannel<E> getChannel() {
        return this;
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @NotNull
    public SelectClause2<E, SendChannel<E>> getOnSend() {
        return this._channel.getOnSend();
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public void invokeOnClose(@NotNull Function1<? super Throwable, Unit> handler) {
        this._channel.invokeOnClose(handler);
    }

    @Override // kotlinx.coroutines.AbstractCoroutine, kotlinx.coroutines.JobSupport, kotlinx.coroutines.Job
    public boolean isActive() {
        return super.isActive();
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public boolean isClosedForSend() {
        return this._channel.isClosedForSend();
    }

    @Override // kotlinx.coroutines.AbstractCoroutine
    protected void m(Throwable cause, boolean handled) {
        if (this._channel.close(cause) || handled) {
            return;
        }
        CoroutineExceptionHandlerKt.handleCoroutineException(getF25396a(), cause);
    }

    /* renamed from: n, reason: from getter */
    protected final BroadcastChannel get_channel() {
        return this._channel;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // kotlinx.coroutines.AbstractCoroutine
    /* renamed from: o, reason: merged with bridge method [inline-methods] */
    public void onCompleted(Unit value) {
        SendChannel.DefaultImpls.close$default(this._channel, null, 1, null);
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'trySend' method", replaceWith = @ReplaceWith(expression = "trySend(element).isSuccess", imports = {}))
    public boolean offer(E element) {
        return this._channel.offer(element);
    }

    @Override // kotlinx.coroutines.channels.BroadcastChannel
    @NotNull
    public ReceiveChannel<E> openSubscription() {
        return this._channel.openSubscription();
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @Nullable
    public Object send(E e2, @NotNull Continuation<? super Unit> continuation) {
        return this._channel.send(e2, continuation);
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @NotNull
    /* renamed from: trySend-JP2dKIU */
    public Object mo2326trySendJP2dKIU(E element) {
        return this._channel.mo2326trySendJP2dKIU(element);
    }

    @Override // kotlinx.coroutines.JobSupport, kotlinx.coroutines.Job
    public final void cancel(@Nullable CancellationException cause) {
        if (cause == null) {
            cause = new JobCancellationException(c(), null, this);
        }
        cancelInternal(cause);
    }
}
