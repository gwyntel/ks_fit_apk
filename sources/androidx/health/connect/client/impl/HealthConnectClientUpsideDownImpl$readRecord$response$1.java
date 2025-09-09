package androidx.health.connect.client.impl;

import android.health.connect.HealthConnectManager;
import android.health.connect.ReadRecordsResponse;
import android.health.connect.datatypes.Record;
import androidx.core.os.OutcomeReceiverKt;
import androidx.exifinterface.media.ExifInterface;
import androidx.health.connect.client.impl.platform.records.RecordConvertersKt;
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
import kotlin.reflect.KClass;
import kotlinx.coroutines.CancellableContinuationImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a>\u0012\u0018\b\u0001\u0012\u0014 \u0004*\n\u0018\u00010\u0002j\u0004\u0018\u0001`\u00030\u0002j\u0002`\u0003 \u0004*\u001e\u0012\u0018\b\u0001\u0012\u0014 \u0004*\n\u0018\u00010\u0002j\u0004\u0018\u0001`\u00030\u0002j\u0002`\u0003\u0018\u00010\u00010\u0001\"\b\b\u0000\u0010\u0005*\u00020\u0006H\u008a@"}, d2 = {"<anonymous>", "Landroid/health/connect/ReadRecordsResponse;", "Landroid/health/connect/datatypes/Record;", "Landroidx/health/connect/client/impl/platform/records/PlatformRecord;", "kotlin.jvm.PlatformType", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/health/connect/client/records/Record;"}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$readRecord$response$1", f = "HealthConnectClientUpsideDownImpl.kt", i = {}, l = {354}, m = "invokeSuspend", n = {}, s = {})
@SourceDebugExtension({"SMAP\nHealthConnectClientUpsideDownImpl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HealthConnectClientUpsideDownImpl.kt\nandroidx/health/connect/client/impl/HealthConnectClientUpsideDownImpl$readRecord$response$1\n+ 2 CancellableContinuation.kt\nkotlinx/coroutines/CancellableContinuationKt\n*L\n1#1,353:1\n314#2,11:354\n*S KotlinDebug\n*F\n+ 1 HealthConnectClientUpsideDownImpl.kt\nandroidx/health/connect/client/impl/HealthConnectClientUpsideDownImpl$readRecord$response$1\n*L\n168#1:354,11\n*E\n"})
/* loaded from: classes.dex */
final class HealthConnectClientUpsideDownImpl$readRecord$response$1 extends SuspendLambda implements Function1<Continuation<? super ReadRecordsResponse<? extends Record>>, Object> {
    final /* synthetic */ String $recordId;
    final /* synthetic */ KClass<T> $recordType;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ HealthConnectClientUpsideDownImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    HealthConnectClientUpsideDownImpl$readRecord$response$1(HealthConnectClientUpsideDownImpl healthConnectClientUpsideDownImpl, KClass<T> kClass, String str, Continuation<? super HealthConnectClientUpsideDownImpl$readRecord$response$1> continuation) {
        super(1, continuation);
        this.this$0 = healthConnectClientUpsideDownImpl;
        this.$recordType = kClass;
        this.$recordId = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @NotNull
    public final Continuation<Unit> create(@NotNull Continuation<?> continuation) {
        return new HealthConnectClientUpsideDownImpl$readRecord$response$1(this.this$0, this.$recordType, this.$recordId, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Nullable
    public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            HealthConnectClientUpsideDownImpl healthConnectClientUpsideDownImpl = this.this$0;
            KClass<T> kClass = this.$recordType;
            String str = this.$recordId;
            this.L$0 = healthConnectClientUpsideDownImpl;
            this.L$1 = kClass;
            this.L$2 = str;
            this.label = 1;
            CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(this), 1);
            cancellableContinuationImpl.initCancellability();
            HealthConnectManager healthConnectManager = healthConnectClientUpsideDownImpl.healthConnectManager;
            v0.a();
            healthConnectManager.readRecords(s0.a(u0.a(RecordConvertersKt.toPlatformRecordClass(kClass)).addId(str).build()), healthConnectClientUpsideDownImpl.executor, OutcomeReceiverKt.asOutcomeReceiver(cancellableContinuationImpl));
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
        return ((HealthConnectClientUpsideDownImpl$readRecord$response$1) create(continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
