package kotlinx.coroutines.flow;

import androidx.exifinterface.media.ExifInterface;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.flow.internal.ChildCancelledException;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "result", "Lkotlinx/coroutines/channels/ChannelResult;", ""}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2$1$1", f = "Delay.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
@SourceDebugExtension({"SMAP\nDelay.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Delay.kt\nkotlinx/coroutines/flow/FlowKt__DelayKt$sample$2$1$1\n+ 2 Channel.kt\nkotlinx/coroutines/channels/ChannelKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,415:1\n514#2,6:416\n530#2,4:422\n534#2:427\n1#3:426\n*S KotlinDebug\n*F\n+ 1 Delay.kt\nkotlinx/coroutines/flow/FlowKt__DelayKt$sample$2$1$1\n*L\n287#1:416,6\n288#1:422,4\n288#1:427\n*E\n"})
/* loaded from: classes5.dex */
final class FlowKt__DelayKt$sample$2$1$1 extends SuspendLambda implements Function2<ChannelResult<? extends Object>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Ref.ObjectRef<Object> $lastValue;
    final /* synthetic */ ReceiveChannel<Unit> $ticker;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    FlowKt__DelayKt$sample$2$1$1(Ref.ObjectRef<Object> objectRef, ReceiveChannel<Unit> receiveChannel, Continuation<? super FlowKt__DelayKt$sample$2$1$1> continuation) {
        super(2, continuation);
        this.$lastValue = objectRef;
        this.$ticker = receiveChannel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @NotNull
    public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
        FlowKt__DelayKt$sample$2$1$1 flowKt__DelayKt$sample$2$1$1 = new FlowKt__DelayKt$sample$2$1$1(this.$lastValue, this.$ticker, continuation);
        flowKt__DelayKt$sample$2$1$1.L$0 = obj;
        return flowKt__DelayKt$sample$2$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Object mo1invoke(ChannelResult<? extends Object> channelResult, Continuation<? super Unit> continuation) {
        return m2363invokeWpGqRn0(channelResult.getHolder(), continuation);
    }

    @Nullable
    /* renamed from: invoke-WpGqRn0, reason: not valid java name */
    public final Object m2363invokeWpGqRn0(@NotNull Object obj, @Nullable Continuation<? super Unit> continuation) {
        return ((FlowKt__DelayKt$sample$2$1$1) create(ChannelResult.m2335boximpl(obj), continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r4v4, types: [T, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v8, types: [T, kotlinx.coroutines.internal.Symbol] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Nullable
    public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ?? holder = ((ChannelResult) this.L$0).getHolder();
        Ref.ObjectRef<Object> objectRef = this.$lastValue;
        boolean z2 = holder instanceof ChannelResult.Failed;
        if (!z2) {
            objectRef.element = holder;
        }
        ReceiveChannel<Unit> receiveChannel = this.$ticker;
        if (z2) {
            Throwable thM2339exceptionOrNullimpl = ChannelResult.m2339exceptionOrNullimpl(holder);
            if (thM2339exceptionOrNullimpl != null) {
                throw thM2339exceptionOrNullimpl;
            }
            receiveChannel.cancel((CancellationException) new ChildCancelledException());
            objectRef.element = NullSurrogateKt.DONE;
        }
        return Unit.INSTANCE;
    }
}
