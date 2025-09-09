package androidx.health.connect.client.impl;

import android.health.connect.ReadRecordsResponse;
import android.health.connect.datatypes.Record;
import androidx.core.os.OutcomeReceiverKt;
import androidx.exifinterface.media.ExifInterface;
import androidx.health.connect.client.impl.platform.records.RequestConvertersKt;
import androidx.health.connect.client.request.ReadRecordsRequest;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.CancellableContinuationImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a*\u0012\u000e\b\u0001\u0012\n \u0003*\u0004\u0018\u00010\u00020\u0002 \u0003*\u0014\u0012\u000e\b\u0001\u0012\n \u0003*\u0004\u0018\u00010\u00020\u0002\u0018\u00010\u00010\u0001\"\b\b\u0000\u0010\u0004*\u00020\u0005H\u008a@"}, d2 = {"<anonymous>", "Landroid/health/connect/ReadRecordsResponse;", "Landroid/health/connect/datatypes/Record;", "kotlin.jvm.PlatformType", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/health/connect/client/records/Record;"}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$readRecords$response$1", f = "HealthConnectClientUpsideDownImpl.kt", i = {}, l = {354}, m = "invokeSuspend", n = {}, s = {})
@SourceDebugExtension({"SMAP\nHealthConnectClientUpsideDownImpl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HealthConnectClientUpsideDownImpl.kt\nandroidx/health/connect/client/impl/HealthConnectClientUpsideDownImpl$readRecords$response$1\n+ 2 CancellableContinuation.kt\nkotlinx/coroutines/CancellableContinuationKt\n*L\n1#1,353:1\n314#2,11:354\n*S KotlinDebug\n*F\n+ 1 HealthConnectClientUpsideDownImpl.kt\nandroidx/health/connect/client/impl/HealthConnectClientUpsideDownImpl$readRecords$response$1\n*L\n189#1:354,11\n*E\n"})
/* loaded from: classes.dex */
final class HealthConnectClientUpsideDownImpl$readRecords$response$1 extends SuspendLambda implements Function1<Continuation<? super ReadRecordsResponse<? extends Record>>, Object> {
    final /* synthetic */ ReadRecordsRequest<T> $request;
    Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ HealthConnectClientUpsideDownImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    HealthConnectClientUpsideDownImpl$readRecords$response$1(HealthConnectClientUpsideDownImpl healthConnectClientUpsideDownImpl, ReadRecordsRequest<T> readRecordsRequest, Continuation<? super HealthConnectClientUpsideDownImpl$readRecords$response$1> continuation) {
        super(1, continuation);
        this.this$0 = healthConnectClientUpsideDownImpl;
        this.$request = readRecordsRequest;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @NotNull
    public final Continuation<Unit> create(@NotNull Continuation<?> continuation) {
        return new HealthConnectClientUpsideDownImpl$readRecords$response$1(this.this$0, this.$request, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Nullable
    public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            HealthConnectClientUpsideDownImpl healthConnectClientUpsideDownImpl = this.this$0;
            ReadRecordsRequest<T> readRecordsRequest = this.$request;
            this.L$0 = healthConnectClientUpsideDownImpl;
            this.L$1 = readRecordsRequest;
            this.label = 1;
            CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(this), 1);
            cancellableContinuationImpl.initCancellability();
            healthConnectClientUpsideDownImpl.healthConnectManager.readRecords(s0.a(RequestConvertersKt.toPlatformRequest((ReadRecordsRequest<? extends androidx.health.connect.client.records.Record>) readRecordsRequest)), healthConnectClientUpsideDownImpl.executor, OutcomeReceiverKt.asOutcomeReceiver(cancellableContinuationImpl));
            obj = cancellableContinuationImpl.getResult();
            if (obj == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended(this);
            }
            if (obj == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return obj;
    }

    @Override // kotlin.jvm.functions.Function1
    @Nullable
    public final Object invoke(@Nullable Continuation<? super ReadRecordsResponse<? extends Record>> continuation) {
        return ((HealthConnectClientUpsideDownImpl$readRecords$response$1) create(continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
