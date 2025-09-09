package androidx.health.connect.client.impl;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.health.connect.AggregateRecordsGroupedByDurationResponse;
import android.health.connect.AggregateRecordsGroupedByPeriodResponse;
import android.health.connect.AggregateRecordsResponse;
import android.health.connect.HealthConnectManager;
import android.health.connect.RecordIdFilter;
import android.health.connect.changelog.ChangeLogTokenResponse;
import android.support.v4.media.session.PlaybackStateCompat;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import androidx.core.os.OutcomeReceiverKt;
import androidx.exifinterface.media.ExifInterface;
import androidx.health.connect.client.HealthConnectClient;
import androidx.health.connect.client.PermissionController;
import androidx.health.connect.client.impl.platform.records.RecordConvertersKt;
import androidx.health.connect.client.impl.platform.records.RequestConvertersKt;
import androidx.health.connect.client.permission.HealthPermission;
import androidx.health.connect.client.records.Record;
import androidx.health.connect.client.request.AggregateGroupByDurationRequest;
import androidx.health.connect.client.request.AggregateGroupByPeriodRequest;
import androidx.health.connect.client.request.AggregateRequest;
import androidx.health.connect.client.request.ChangesTokenRequest;
import androidx.health.connect.client.time.TimeRangeFilter;
import com.facebook.share.internal.ShareConstants;
import com.yc.utesdk.ble.close.KeyType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KClass;
import kotlin.text.StringsKt;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.ExecutorsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiresApi(api = 34)
@Metadata(d1 = {"\u0000´\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B)\b\u0011\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0018\u0010\u0006\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b\u0012\u0004\u0012\u00020\n0\u0007¢\u0006\u0002\u0010\u000bJ\u0019\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0017J\u001f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00192\u0006\u0010\u0015\u001a\u00020\u001bH\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u001cJ\u001f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u00192\u0006\u0010\u0015\u001a\u00020\u001fH\u0096@ø\u0001\u0000¢\u0006\u0002\u0010 J)\u0010!\u001a\u00020\n2\u000e\u0010\"\u001a\n\u0012\u0006\b\u0001\u0012\u00020$0#2\u0006\u0010%\u001a\u00020&H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010'J=\u0010!\u001a\u00020\n2\u000e\u0010\"\u001a\n\u0012\u0006\b\u0001\u0012\u00020$0#2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\t0\u00192\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\t0\u0019H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010*J\u0019\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\tH\u0096@ø\u0001\u0000¢\u0006\u0002\u0010.J\u0019\u0010/\u001a\u00020\t2\u0006\u0010\u0015\u001a\u000200H\u0096@ø\u0001\u0000¢\u0006\u0002\u00101J\u0017\u00102\u001a\b\u0012\u0004\u0012\u00020\t03H\u0096@ø\u0001\u0000¢\u0006\u0002\u00104J\u001f\u00105\u001a\u0002062\f\u00107\u001a\b\u0012\u0004\u0012\u00020$0\u0019H\u0096@ø\u0001\u0000¢\u0006\u0002\u00108J7\u00109\u001a\b\u0012\u0004\u0012\u0002H;0:\"\b\b\u0000\u0010;*\u00020$2\f\u0010\"\u001a\b\u0012\u0004\u0012\u0002H;0#2\u0006\u0010<\u001a\u00020\tH\u0096@ø\u0001\u0000¢\u0006\u0002\u0010=J/\u0010>\u001a\b\u0012\u0004\u0012\u0002H;0?\"\b\b\u0000\u0010;*\u00020$2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H;0@H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010AJ\u0011\u0010B\u001a\u00020\nH\u0096@ø\u0001\u0000¢\u0006\u0002\u00104J\u001f\u0010C\u001a\u00020\n2\f\u00107\u001a\b\u0012\u0004\u0012\u00020$0\u0019H\u0096@ø\u0001\u0000¢\u0006\u0002\u00108J5\u0010D\u001a\u0002H;\"\u0004\b\u0000\u0010;2\u001c\u0010E\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H;0F\u0012\u0006\u0012\u0004\u0018\u00010G0\u0007H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010HR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R \u0010\u0006\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b\u0012\u0004\u0012\u00020\n0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006I"}, d2 = {"Landroidx/health/connect/client/impl/HealthConnectClientUpsideDownImpl;", "Landroidx/health/connect/client/HealthConnectClient;", "Landroidx/health/connect/client/PermissionController;", com.umeng.analytics.pro.f.X, "Landroid/content/Context;", "(Landroid/content/Context;)V", "revokePermissionsFunction", "Lkotlin/Function1;", "", "", "", "(Landroid/content/Context;Lkotlin/jvm/functions/Function1;)V", "executor", "Ljava/util/concurrent/Executor;", "healthConnectManager", "Landroid/health/connect/HealthConnectManager;", "permissionController", "getPermissionController", "()Landroidx/health/connect/client/PermissionController;", "aggregate", "Landroidx/health/connect/client/aggregate/AggregationResult;", ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID, "Landroidx/health/connect/client/request/AggregateRequest;", "(Landroidx/health/connect/client/request/AggregateRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "aggregateGroupByDuration", "", "Landroidx/health/connect/client/aggregate/AggregationResultGroupedByDuration;", "Landroidx/health/connect/client/request/AggregateGroupByDurationRequest;", "(Landroidx/health/connect/client/request/AggregateGroupByDurationRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "aggregateGroupByPeriod", "Landroidx/health/connect/client/aggregate/AggregationResultGroupedByPeriod;", "Landroidx/health/connect/client/request/AggregateGroupByPeriodRequest;", "(Landroidx/health/connect/client/request/AggregateGroupByPeriodRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteRecords", "recordType", "Lkotlin/reflect/KClass;", "Landroidx/health/connect/client/records/Record;", "timeRangeFilter", "Landroidx/health/connect/client/time/TimeRangeFilter;", "(Lkotlin/reflect/KClass;Landroidx/health/connect/client/time/TimeRangeFilter;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "recordIdsList", "clientRecordIdsList", "(Lkotlin/reflect/KClass;Ljava/util/List;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getChanges", "Landroidx/health/connect/client/response/ChangesResponse;", "changesToken", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getChangesToken", "Landroidx/health/connect/client/request/ChangesTokenRequest;", "(Landroidx/health/connect/client/request/ChangesTokenRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getGrantedPermissions", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertRecords", "Landroidx/health/connect/client/response/InsertRecordsResponse;", "records", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "readRecord", "Landroidx/health/connect/client/response/ReadRecordResponse;", ExifInterface.GPS_DIRECTION_TRUE, "recordId", "(Lkotlin/reflect/KClass;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "readRecords", "Landroidx/health/connect/client/response/ReadRecordsResponse;", "Landroidx/health/connect/client/request/ReadRecordsRequest;", "(Landroidx/health/connect/client/request/ReadRecordsRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "revokeAllPermissions", "updateRecords", "wrapPlatformException", "function", "Lkotlin/coroutines/Continuation;", "", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nHealthConnectClientUpsideDownImpl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HealthConnectClientUpsideDownImpl.kt\nandroidx/health/connect/client/impl/HealthConnectClientUpsideDownImpl\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 CancellableContinuation.kt\nkotlinx/coroutines/CancellableContinuationKt\n+ 5 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,353:1\n1549#2:354\n1620#2,3:355\n1549#2:359\n1620#2,3:360\n1559#2:363\n1590#2,4:364\n1855#2,2:379\n1855#2,2:381\n1#3:358\n314#4,11:368\n3792#5:383\n4307#5,2:384\n*S KotlinDebug\n*F\n+ 1 HealthConnectClientUpsideDownImpl.kt\nandroidx/health/connect/client/impl/HealthConnectClientUpsideDownImpl\n*L\n198#1:354\n198#1:355,3\n229#1:359\n229#1:360,3\n245#1:363\n245#1:364,4\n298#1:379,2\n299#1:381,2\n289#1:368,11\n341#1:383\n341#1:384,2\n*E\n"})
/* loaded from: classes.dex */
public final class HealthConnectClientUpsideDownImpl implements HealthConnectClient, PermissionController {

    @NotNull
    private final Context context;

    @NotNull
    private final Executor executor;

    @NotNull
    private final HealthConnectManager healthConnectManager;

    @NotNull
    private final Function1<Collection<String>, Unit> revokePermissionsFunction;

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$1, reason: invalid class name */
    /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1<Collection<String>, Unit> {
        AnonymousClass1(Object obj) {
            super(1, obj, Context.class, "revokeSelfPermissionsOnKill", "revokeSelfPermissionsOnKill(Ljava/util/Collection;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Collection<String> collection) {
            invoke2(collection);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull Collection<String> p02) {
            Intrinsics.checkNotNullParameter(p02, "p0");
            ((Context) this.receiver).revokeSelfPermissionsOnKill(p02);
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl", f = "HealthConnectClientUpsideDownImpl.kt", i = {0}, l = {204}, m = "aggregate", n = {ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID}, s = {"L$0"})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$aggregate$1, reason: invalid class name and case insensitive filesystem */
    static final class C03061 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C03061(Continuation<? super C03061> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HealthConnectClientUpsideDownImpl.this.aggregate(null, this);
        }
    }

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\u0010\u0000\u001a&\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00020\u0002 \u0003*\u0012\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00020\u0002\u0018\u00010\u00010\u0001H\u008a@"}, d2 = {"<anonymous>", "Landroid/health/connect/AggregateRecordsResponse;", "", "kotlin.jvm.PlatformType"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$aggregate$2", f = "HealthConnectClientUpsideDownImpl.kt", i = {}, l = {354}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nHealthConnectClientUpsideDownImpl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HealthConnectClientUpsideDownImpl.kt\nandroidx/health/connect/client/impl/HealthConnectClientUpsideDownImpl$aggregate$2\n+ 2 CancellableContinuation.kt\nkotlinx/coroutines/CancellableContinuationKt\n*L\n1#1,353:1\n314#2,11:354\n*S KotlinDebug\n*F\n+ 1 HealthConnectClientUpsideDownImpl.kt\nandroidx/health/connect/client/impl/HealthConnectClientUpsideDownImpl$aggregate$2\n*L\n205#1:354,11\n*E\n"})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$aggregate$2, reason: invalid class name */
    static final class AnonymousClass2 extends SuspendLambda implements Function1<Continuation<? super AggregateRecordsResponse<Object>>, Object> {
        final /* synthetic */ AggregateRequest $request;
        Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(AggregateRequest aggregateRequest, Continuation<? super AnonymousClass2> continuation) {
            super(1, continuation);
            this.$request = aggregateRequest;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@NotNull Continuation<?> continuation) {
            return HealthConnectClientUpsideDownImpl.this.new AnonymousClass2(this.$request, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                HealthConnectClientUpsideDownImpl healthConnectClientUpsideDownImpl = HealthConnectClientUpsideDownImpl.this;
                AggregateRequest aggregateRequest = this.$request;
                this.L$0 = healthConnectClientUpsideDownImpl;
                this.L$1 = aggregateRequest;
                this.label = 1;
                CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(this), 1);
                cancellableContinuationImpl.initCancellability();
                healthConnectClientUpsideDownImpl.healthConnectManager.aggregate(RequestConvertersKt.toPlatformRequest(aggregateRequest), healthConnectClientUpsideDownImpl.executor, OutcomeReceiverKt.asOutcomeReceiver(cancellableContinuationImpl));
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
        public final Object invoke(@Nullable Continuation<? super AggregateRecordsResponse<Object>> continuation) {
            return ((AnonymousClass2) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl", f = "HealthConnectClientUpsideDownImpl.kt", i = {0}, l = {219}, m = "aggregateGroupByDuration", n = {ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID}, s = {"L$0"})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$aggregateGroupByDuration$1, reason: invalid class name and case insensitive filesystem */
    static final class C03071 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C03071(Continuation<? super C03071> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HealthConnectClientUpsideDownImpl.this.aggregateGroupByDuration(null, this);
        }
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \u0010\u0000\u001a^\u0012(\u0012&\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003 \u0004*\u0012\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003\u0018\u00010\u00020\u0002 \u0004*.\u0012(\u0012&\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003 \u0004*\u0012\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003\u0018\u00010\u00020\u0002\u0018\u00010\u00050\u0001H\u008a@"}, d2 = {"<anonymous>", "", "Landroid/health/connect/AggregateRecordsGroupedByDurationResponse;", "", "kotlin.jvm.PlatformType", ""}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$aggregateGroupByDuration$2", f = "HealthConnectClientUpsideDownImpl.kt", i = {}, l = {354}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nHealthConnectClientUpsideDownImpl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HealthConnectClientUpsideDownImpl.kt\nandroidx/health/connect/client/impl/HealthConnectClientUpsideDownImpl$aggregateGroupByDuration$2\n+ 2 CancellableContinuation.kt\nkotlinx/coroutines/CancellableContinuationKt\n*L\n1#1,353:1\n314#2,11:354\n*S KotlinDebug\n*F\n+ 1 HealthConnectClientUpsideDownImpl.kt\nandroidx/health/connect/client/impl/HealthConnectClientUpsideDownImpl$aggregateGroupByDuration$2\n*L\n220#1:354,11\n*E\n"})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$aggregateGroupByDuration$2, reason: invalid class name and case insensitive filesystem */
    static final class C03082 extends SuspendLambda implements Function1<Continuation<? super List<AggregateRecordsGroupedByDurationResponse<Object>>>, Object> {
        final /* synthetic */ AggregateGroupByDurationRequest $request;
        Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03082(AggregateGroupByDurationRequest aggregateGroupByDurationRequest, Continuation<? super C03082> continuation) {
            super(1, continuation);
            this.$request = aggregateGroupByDurationRequest;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@NotNull Continuation<?> continuation) {
            return HealthConnectClientUpsideDownImpl.this.new C03082(this.$request, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                HealthConnectClientUpsideDownImpl healthConnectClientUpsideDownImpl = HealthConnectClientUpsideDownImpl.this;
                AggregateGroupByDurationRequest aggregateGroupByDurationRequest = this.$request;
                this.L$0 = healthConnectClientUpsideDownImpl;
                this.L$1 = aggregateGroupByDurationRequest;
                this.label = 1;
                CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(this), 1);
                cancellableContinuationImpl.initCancellability();
                healthConnectClientUpsideDownImpl.healthConnectManager.aggregateGroupByDuration(RequestConvertersKt.toPlatformRequest(aggregateGroupByDurationRequest), aggregateGroupByDurationRequest.getTimeRangeSlicer(), healthConnectClientUpsideDownImpl.executor, OutcomeReceiverKt.asOutcomeReceiver(cancellableContinuationImpl));
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
        public final Object invoke(@Nullable Continuation<? super List<AggregateRecordsGroupedByDurationResponse<Object>>> continuation) {
            return ((C03082) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl", f = "HealthConnectClientUpsideDownImpl.kt", i = {0}, l = {235}, m = "aggregateGroupByPeriod", n = {ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID}, s = {"L$0"})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$aggregateGroupByPeriod$1, reason: invalid class name and case insensitive filesystem */
    static final class C03091 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C03091(Continuation<? super C03091> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HealthConnectClientUpsideDownImpl.this.aggregateGroupByPeriod(null, this);
        }
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \u0010\u0000\u001a^\u0012(\u0012&\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003 \u0004*\u0012\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003\u0018\u00010\u00020\u0002 \u0004*.\u0012(\u0012&\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003 \u0004*\u0012\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003\u0018\u00010\u00020\u0002\u0018\u00010\u00050\u0001H\u008a@"}, d2 = {"<anonymous>", "", "Landroid/health/connect/AggregateRecordsGroupedByPeriodResponse;", "", "kotlin.jvm.PlatformType", ""}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$aggregateGroupByPeriod$2", f = "HealthConnectClientUpsideDownImpl.kt", i = {}, l = {354}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nHealthConnectClientUpsideDownImpl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HealthConnectClientUpsideDownImpl.kt\nandroidx/health/connect/client/impl/HealthConnectClientUpsideDownImpl$aggregateGroupByPeriod$2\n+ 2 CancellableContinuation.kt\nkotlinx/coroutines/CancellableContinuationKt\n*L\n1#1,353:1\n314#2,11:354\n*S KotlinDebug\n*F\n+ 1 HealthConnectClientUpsideDownImpl.kt\nandroidx/health/connect/client/impl/HealthConnectClientUpsideDownImpl$aggregateGroupByPeriod$2\n*L\n236#1:354,11\n*E\n"})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$aggregateGroupByPeriod$2, reason: invalid class name and case insensitive filesystem */
    static final class C03102 extends SuspendLambda implements Function1<Continuation<? super List<AggregateRecordsGroupedByPeriodResponse<Object>>>, Object> {
        final /* synthetic */ AggregateGroupByPeriodRequest $request;
        Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03102(AggregateGroupByPeriodRequest aggregateGroupByPeriodRequest, Continuation<? super C03102> continuation) {
            super(1, continuation);
            this.$request = aggregateGroupByPeriodRequest;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@NotNull Continuation<?> continuation) {
            return HealthConnectClientUpsideDownImpl.this.new C03102(this.$request, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                HealthConnectClientUpsideDownImpl healthConnectClientUpsideDownImpl = HealthConnectClientUpsideDownImpl.this;
                AggregateGroupByPeriodRequest aggregateGroupByPeriodRequest = this.$request;
                this.L$0 = healthConnectClientUpsideDownImpl;
                this.L$1 = aggregateGroupByPeriodRequest;
                this.label = 1;
                CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(this), 1);
                cancellableContinuationImpl.initCancellability();
                healthConnectClientUpsideDownImpl.healthConnectManager.aggregateGroupByPeriod(RequestConvertersKt.toPlatformRequest(aggregateGroupByPeriodRequest), aggregateGroupByPeriodRequest.getTimeRangeSlicer(), healthConnectClientUpsideDownImpl.executor, OutcomeReceiverKt.asOutcomeReceiver(cancellableContinuationImpl));
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
        public final Object invoke(@Nullable Continuation<? super List<AggregateRecordsGroupedByPeriodResponse<Object>>> continuation) {
            return ((C03102) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001H\u008a@"}, d2 = {"<anonymous>", "Ljava/lang/Void;", "kotlin.jvm.PlatformType"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$deleteRecords$2", f = "HealthConnectClientUpsideDownImpl.kt", i = {}, l = {354}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nHealthConnectClientUpsideDownImpl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HealthConnectClientUpsideDownImpl.kt\nandroidx/health/connect/client/impl/HealthConnectClientUpsideDownImpl$deleteRecords$2\n+ 2 CancellableContinuation.kt\nkotlinx/coroutines/CancellableContinuationKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,353:1\n314#2,9:354\n323#2,2:367\n1855#3,2:363\n1855#3,2:365\n*S KotlinDebug\n*F\n+ 1 HealthConnectClientUpsideDownImpl.kt\nandroidx/health/connect/client/impl/HealthConnectClientUpsideDownImpl$deleteRecords$2\n*L\n124#1:354,9\n124#1:367,2\n127#1:363,2\n130#1:365,2\n*E\n"})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$deleteRecords$2, reason: invalid class name and case insensitive filesystem */
    static final class C03112 extends SuspendLambda implements Function1<Continuation<? super Void>, Object> {
        final /* synthetic */ List<String> $clientRecordIdsList;
        final /* synthetic */ List<String> $recordIdsList;
        final /* synthetic */ KClass<? extends Record> $recordType;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03112(List<String> list, List<String> list2, KClass<? extends Record> kClass, Continuation<? super C03112> continuation) {
            super(1, continuation);
            this.$recordIdsList = list;
            this.$clientRecordIdsList = list2;
            this.$recordType = kClass;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@NotNull Continuation<?> continuation) {
            return HealthConnectClientUpsideDownImpl.this.new C03112(this.$recordIdsList, this.$clientRecordIdsList, this.$recordType, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                HealthConnectClientUpsideDownImpl healthConnectClientUpsideDownImpl = HealthConnectClientUpsideDownImpl.this;
                List<String> list = this.$recordIdsList;
                List<String> list2 = this.$clientRecordIdsList;
                KClass<? extends Record> kClass = this.$recordType;
                this.L$0 = healthConnectClientUpsideDownImpl;
                this.L$1 = list;
                this.L$2 = list2;
                this.L$3 = kClass;
                this.label = 1;
                CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(this), 1);
                cancellableContinuationImpl.initCancellability();
                HealthConnectManager healthConnectManager = healthConnectClientUpsideDownImpl.healthConnectManager;
                List listCreateListBuilder = CollectionsKt.createListBuilder();
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    listCreateListBuilder.add(RecordIdFilter.fromId(RecordConvertersKt.toPlatformRecordClass(kClass), (String) it.next()));
                }
                Iterator<T> it2 = list2.iterator();
                while (it2.hasNext()) {
                    listCreateListBuilder.add(RecordIdFilter.fromClientRecordId(RecordConvertersKt.toPlatformRecordClass(kClass), (String) it2.next()));
                }
                healthConnectManager.deleteRecords(CollectionsKt.build(listCreateListBuilder), healthConnectClientUpsideDownImpl.executor, OutcomeReceiverKt.asOutcomeReceiver(cancellableContinuationImpl));
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
        public final Object invoke(@Nullable Continuation<? super Void> continuation) {
            return ((C03112) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001H\u008a@"}, d2 = {"<anonymous>", "Ljava/lang/Void;", "kotlin.jvm.PlatformType"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$deleteRecords$4", f = "HealthConnectClientUpsideDownImpl.kt", i = {}, l = {354}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nHealthConnectClientUpsideDownImpl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HealthConnectClientUpsideDownImpl.kt\nandroidx/health/connect/client/impl/HealthConnectClientUpsideDownImpl$deleteRecords$4\n+ 2 CancellableContinuation.kt\nkotlinx/coroutines/CancellableContinuationKt\n*L\n1#1,353:1\n314#2,11:354\n*S KotlinDebug\n*F\n+ 1 HealthConnectClientUpsideDownImpl.kt\nandroidx/health/connect/client/impl/HealthConnectClientUpsideDownImpl$deleteRecords$4\n*L\n151#1:354,11\n*E\n"})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$deleteRecords$4, reason: invalid class name */
    static final class AnonymousClass4 extends SuspendLambda implements Function1<Continuation<? super Void>, Object> {
        final /* synthetic */ KClass<? extends Record> $recordType;
        final /* synthetic */ TimeRangeFilter $timeRangeFilter;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass4(KClass<? extends Record> kClass, TimeRangeFilter timeRangeFilter, Continuation<? super AnonymousClass4> continuation) {
            super(1, continuation);
            this.$recordType = kClass;
            this.$timeRangeFilter = timeRangeFilter;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@NotNull Continuation<?> continuation) {
            return HealthConnectClientUpsideDownImpl.this.new AnonymousClass4(this.$recordType, this.$timeRangeFilter, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                HealthConnectClientUpsideDownImpl healthConnectClientUpsideDownImpl = HealthConnectClientUpsideDownImpl.this;
                KClass<? extends Record> kClass = this.$recordType;
                TimeRangeFilter timeRangeFilter = this.$timeRangeFilter;
                this.L$0 = healthConnectClientUpsideDownImpl;
                this.L$1 = kClass;
                this.L$2 = timeRangeFilter;
                this.label = 1;
                CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(this), 1);
                cancellableContinuationImpl.initCancellability();
                healthConnectClientUpsideDownImpl.healthConnectManager.deleteRecords(RecordConvertersKt.toPlatformRecordClass(kClass), RequestConvertersKt.toPlatformTimeRangeFilter(timeRangeFilter), healthConnectClientUpsideDownImpl.executor, OutcomeReceiverKt.asOutcomeReceiver(cancellableContinuationImpl));
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
        public final Object invoke(@Nullable Continuation<? super Void> continuation) {
            return ((AnonymousClass4) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl", f = "HealthConnectClientUpsideDownImpl.kt", i = {0, 0}, l = {354}, m = "getChanges", n = {"this", "changesToken"}, s = {"L$0", "L$1"})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$getChanges$1, reason: invalid class name and case insensitive filesystem */
    static final class C03121 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        C03121(Continuation<? super C03121> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HealthConnectClientUpsideDownImpl.this.getChanges(null, this);
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl", f = "HealthConnectClientUpsideDownImpl.kt", i = {}, l = {KeyType.ABORT_DIAL_BIN}, m = "getChangesToken", n = {}, s = {})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$getChangesToken$1, reason: invalid class name and case insensitive filesystem */
    static final class C03131 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C03131(Continuation<? super C03131> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HealthConnectClientUpsideDownImpl.this.getChangesToken(null, this);
        }
    }

    @Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001H\u008a@"}, d2 = {"<anonymous>", "Landroid/health/connect/changelog/ChangeLogTokenResponse;", "kotlin.jvm.PlatformType"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$getChangesToken$2", f = "HealthConnectClientUpsideDownImpl.kt", i = {}, l = {354}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nHealthConnectClientUpsideDownImpl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HealthConnectClientUpsideDownImpl.kt\nandroidx/health/connect/client/impl/HealthConnectClientUpsideDownImpl$getChangesToken$2\n+ 2 CancellableContinuation.kt\nkotlinx/coroutines/CancellableContinuationKt\n*L\n1#1,353:1\n314#2,11:354\n*S KotlinDebug\n*F\n+ 1 HealthConnectClientUpsideDownImpl.kt\nandroidx/health/connect/client/impl/HealthConnectClientUpsideDownImpl$getChangesToken$2\n*L\n276#1:354,11\n*E\n"})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$getChangesToken$2, reason: invalid class name and case insensitive filesystem */
    static final class C03142 extends SuspendLambda implements Function1<Continuation<? super ChangeLogTokenResponse>, Object> {
        final /* synthetic */ ChangesTokenRequest $request;
        Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03142(ChangesTokenRequest changesTokenRequest, Continuation<? super C03142> continuation) {
            super(1, continuation);
            this.$request = changesTokenRequest;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@NotNull Continuation<?> continuation) {
            return HealthConnectClientUpsideDownImpl.this.new C03142(this.$request, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                HealthConnectClientUpsideDownImpl healthConnectClientUpsideDownImpl = HealthConnectClientUpsideDownImpl.this;
                ChangesTokenRequest changesTokenRequest = this.$request;
                this.L$0 = healthConnectClientUpsideDownImpl;
                this.L$1 = changesTokenRequest;
                this.label = 1;
                CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(this), 1);
                cancellableContinuationImpl.initCancellability();
                healthConnectClientUpsideDownImpl.healthConnectManager.getChangeLogToken(RequestConvertersKt.toPlatformRequest(changesTokenRequest), healthConnectClientUpsideDownImpl.executor, OutcomeReceiverKt.asOutcomeReceiver(cancellableContinuationImpl));
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
        public final Object invoke(@Nullable Continuation<? super ChangeLogTokenResponse> continuation) {
            return ((C03142) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl", f = "HealthConnectClientUpsideDownImpl.kt", i = {}, l = {94}, m = "insertRecords", n = {}, s = {})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$insertRecords$1, reason: invalid class name and case insensitive filesystem */
    static final class C03151 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C03151(Continuation<? super C03151> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HealthConnectClientUpsideDownImpl.this.insertRecords(null, this);
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl", f = "HealthConnectClientUpsideDownImpl.kt", i = {}, l = {167}, m = "readRecord", n = {}, s = {})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$readRecord$1, reason: invalid class name and case insensitive filesystem */
    static final class C03161<T extends Record> extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C03161(Continuation<? super C03161> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HealthConnectClientUpsideDownImpl.this.readRecord(null, null, this);
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl", f = "HealthConnectClientUpsideDownImpl.kt", i = {}, l = {188}, m = "readRecords", n = {}, s = {})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$readRecords$1, reason: invalid class name and case insensitive filesystem */
    static final class C03171<T extends Record> extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C03171(Continuation<? super C03171> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HealthConnectClientUpsideDownImpl.this.readRecords(null, this);
        }
    }

    @Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001H\u008a@"}, d2 = {"<anonymous>", "Ljava/lang/Void;", "kotlin.jvm.PlatformType"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$updateRecords$2", f = "HealthConnectClientUpsideDownImpl.kt", i = {}, l = {354}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nHealthConnectClientUpsideDownImpl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HealthConnectClientUpsideDownImpl.kt\nandroidx/health/connect/client/impl/HealthConnectClientUpsideDownImpl$updateRecords$2\n+ 2 CancellableContinuation.kt\nkotlinx/coroutines/CancellableContinuationKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,353:1\n314#2,9:354\n323#2,2:367\n1549#3:363\n1620#3,3:364\n*S KotlinDebug\n*F\n+ 1 HealthConnectClientUpsideDownImpl.kt\nandroidx/health/connect/client/impl/HealthConnectClientUpsideDownImpl$updateRecords$2\n*L\n108#1:354,9\n108#1:367,2\n110#1:363\n110#1:364,3\n*E\n"})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$updateRecords$2, reason: invalid class name and case insensitive filesystem */
    static final class C03182 extends SuspendLambda implements Function1<Continuation<? super Void>, Object> {
        final /* synthetic */ List<Record> $records;
        Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        C03182(List<? extends Record> list, Continuation<? super C03182> continuation) {
            super(1, continuation);
            this.$records = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@NotNull Continuation<?> continuation) {
            return HealthConnectClientUpsideDownImpl.this.new C03182(this.$records, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                HealthConnectClientUpsideDownImpl healthConnectClientUpsideDownImpl = HealthConnectClientUpsideDownImpl.this;
                List<Record> list = this.$records;
                this.L$0 = healthConnectClientUpsideDownImpl;
                this.L$1 = list;
                this.label = 1;
                CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(this), 1);
                cancellableContinuationImpl.initCancellability();
                HealthConnectManager healthConnectManager = healthConnectClientUpsideDownImpl.healthConnectManager;
                List<Record> list2 = list;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                Iterator<T> it = list2.iterator();
                while (it.hasNext()) {
                    arrayList.add(RecordConvertersKt.toPlatformRecord((Record) it.next()));
                }
                healthConnectManager.updateRecords(arrayList, healthConnectClientUpsideDownImpl.executor, OutcomeReceiverKt.asOutcomeReceiver(cancellableContinuationImpl));
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
        public final Object invoke(@Nullable Continuation<? super Void> continuation) {
            return ((C03182) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl", f = "HealthConnectClientUpsideDownImpl.kt", i = {}, l = {347}, m = "wrapPlatformException", n = {}, s = {})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$wrapPlatformException$1, reason: invalid class name and case insensitive filesystem */
    static final class C03191<T> extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C03191(Continuation<? super C03191> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HealthConnectClientUpsideDownImpl.this.wrapPlatformException(null, this);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public HealthConnectClientUpsideDownImpl(@NotNull Context context) {
        this(context, new AnonymousClass1(context));
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final <T> java.lang.Object wrapPlatformException(kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> r5, kotlin.coroutines.Continuation<? super T> r6) throws java.lang.Exception {
        /*
            r4 = this;
            boolean r0 = r6 instanceof androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.C03191
            if (r0 == 0) goto L13
            r0 = r6
            androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$wrapPlatformException$1 r0 = (androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.C03191) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$wrapPlatformException$1 r0 = new androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$wrapPlatformException$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: android.health.connect.HealthConnectException -> L29
            goto L3f
        L29:
            r5 = move-exception
            goto L40
        L2b:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L33:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.label = r3     // Catch: android.health.connect.HealthConnectException -> L29
            java.lang.Object r6 = r5.invoke(r0)     // Catch: android.health.connect.HealthConnectException -> L29
            if (r6 != r1) goto L3f
            return r1
        L3f:
            return r6
        L40:
            java.lang.Exception r5 = androidx.health.connect.client.impl.platform.ExceptionConverterKt.toKtException(r5)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.wrapPlatformException(kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.health.connect.client.HealthConnectClient
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object aggregate(@org.jetbrains.annotations.NotNull androidx.health.connect.client.request.AggregateRequest r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super androidx.health.connect.client.aggregate.AggregationResult> r6) throws java.lang.Exception {
        /*
            r4 = this;
            boolean r0 = r6 instanceof androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.C03061
            if (r0 == 0) goto L13
            r0 = r6
            androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$aggregate$1 r0 = (androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.C03061) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$aggregate$1 r0 = new androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$aggregate$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 != r3) goto L2d
            java.lang.Object r5 = r0.L$0
            androidx.health.connect.client.request.AggregateRequest r5 = (androidx.health.connect.client.request.AggregateRequest) r5
            kotlin.ResultKt.throwOnFailure(r6)
            goto L49
        L2d:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L35:
            kotlin.ResultKt.throwOnFailure(r6)
            androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$aggregate$2 r6 = new androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$aggregate$2
            r2 = 0
            r6.<init>(r5, r2)
            r0.L$0 = r5
            r0.label = r3
            java.lang.Object r6 = r4.wrapPlatformException(r6, r0)
            if (r6 != r1) goto L49
            return r1
        L49:
            java.lang.String r0 = "override suspend fun agg…se(request.metrics)\n    }"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r6, r0)
            android.health.connect.AggregateRecordsResponse r6 = androidx.health.connect.client.impl.u.a(r6)
            java.util.Set r5 = r5.getMetrics$connect_client_release()
            androidx.health.connect.client.aggregate.AggregationResult r5 = androidx.health.connect.client.impl.platform.records.ResponseConvertersKt.toSdkResponse(r6, r5)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.aggregate(androidx.health.connect.client.request.AggregateRequest, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.health.connect.client.HealthConnectClient
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object aggregateGroupByDuration(@org.jetbrains.annotations.NotNull androidx.health.connect.client.request.AggregateGroupByDurationRequest r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super java.util.List<androidx.health.connect.client.aggregate.AggregationResultGroupedByDuration>> r6) throws java.lang.Exception {
        /*
            r4 = this;
            boolean r0 = r6 instanceof androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.C03071
            if (r0 == 0) goto L13
            r0 = r6
            androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$aggregateGroupByDuration$1 r0 = (androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.C03071) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$aggregateGroupByDuration$1 r0 = new androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$aggregateGroupByDuration$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 != r3) goto L2d
            java.lang.Object r5 = r0.L$0
            androidx.health.connect.client.request.AggregateGroupByDurationRequest r5 = (androidx.health.connect.client.request.AggregateGroupByDurationRequest) r5
            kotlin.ResultKt.throwOnFailure(r6)
            goto L49
        L2d:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L35:
            kotlin.ResultKt.throwOnFailure(r6)
            androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$aggregateGroupByDuration$2 r6 = new androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$aggregateGroupByDuration$2
            r2 = 0
            r6.<init>(r5, r2)
            r0.L$0 = r5
            r0.label = r3
            java.lang.Object r6 = r4.wrapPlatformException(r6, r0)
            if (r6 != r1) goto L49
            return r1
        L49:
            java.lang.String r0 = "override suspend fun agg…(request.metrics) }\n    }"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r6, r0)
            java.lang.Iterable r6 = (java.lang.Iterable) r6
            java.util.ArrayList r0 = new java.util.ArrayList
            r1 = 10
            int r1 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r6, r1)
            r0.<init>(r1)
            java.util.Iterator r6 = r6.iterator()
        L5f:
            boolean r1 = r6.hasNext()
            if (r1 == 0) goto L7e
            java.lang.Object r1 = r6.next()
            android.health.connect.AggregateRecordsGroupedByDurationResponse r1 = androidx.health.connect.client.impl.v.a(r1)
            java.lang.String r2 = "it"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r2)
            java.util.Set r2 = r5.getMetrics$connect_client_release()
            androidx.health.connect.client.aggregate.AggregationResultGroupedByDuration r1 = androidx.health.connect.client.impl.platform.records.ResponseConvertersKt.toSdkResponse(r1, r2)
            r0.add(r1)
            goto L5f
        L7e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.aggregateGroupByDuration(androidx.health.connect.client.request.AggregateGroupByDurationRequest, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.health.connect.client.HealthConnectClient
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object aggregateGroupByPeriod(@org.jetbrains.annotations.NotNull androidx.health.connect.client.request.AggregateGroupByPeriodRequest r11, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super java.util.List<androidx.health.connect.client.aggregate.AggregationResultGroupedByPeriod>> r12) throws java.lang.Exception {
        /*
            r10 = this;
            boolean r0 = r12 instanceof androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.C03091
            if (r0 == 0) goto L13
            r0 = r12
            androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$aggregateGroupByPeriod$1 r0 = (androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.C03091) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$aggregateGroupByPeriod$1 r0 = new androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$aggregateGroupByPeriod$1
            r0.<init>(r12)
        L18:
            java.lang.Object r12 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 != r3) goto L2d
            java.lang.Object r11 = r0.L$0
            androidx.health.connect.client.request.AggregateGroupByPeriodRequest r11 = (androidx.health.connect.client.request.AggregateGroupByPeriodRequest) r11
            kotlin.ResultKt.throwOnFailure(r12)
            goto L49
        L2d:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L35:
            kotlin.ResultKt.throwOnFailure(r12)
            androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$aggregateGroupByPeriod$2 r12 = new androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$aggregateGroupByPeriod$2
            r2 = 0
            r12.<init>(r11, r2)
            r0.L$0 = r11
            r0.label = r3
            java.lang.Object r12 = r10.wrapPlatformException(r12, r0)
            if (r12 != r1) goto L49
            return r1
        L49:
            java.lang.String r0 = "override suspend fun agg…    }\n            }\n    }"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r12, r0)
            java.lang.Iterable r12 = (java.lang.Iterable) r12
            java.util.ArrayList r0 = new java.util.ArrayList
            r1 = 10
            int r2 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r12, r1)
            r0.<init>(r2)
            java.util.Iterator r12 = r12.iterator()
            r2 = 0
        L60:
            boolean r3 = r12.hasNext()
            if (r3 == 0) goto Lfe
            java.lang.Object r3 = r12.next()
            int r4 = r2 + 1
            if (r2 >= 0) goto L71
            kotlin.collections.CollectionsKt.throwIndexOverflow()
        L71:
            android.health.connect.AggregateRecordsGroupedByPeriodResponse r3 = androidx.health.connect.client.impl.z.a(r3)
            r5 = 34
            int r5 = c.b.a(r5)
            java.lang.String r6 = "platformResponse"
            if (r5 >= r1) goto Led
            java.time.Period r5 = r11.getTimeRangeSlicer()
            int r5 = androidx.health.connect.client.impl.b0.a(r5)
            if (r5 != 0) goto L94
            java.time.Period r5 = r11.getTimeRangeSlicer()
            int r5 = androidx.health.connect.client.impl.c0.a(r5)
            if (r5 != 0) goto L94
            goto Led
        L94:
            androidx.health.connect.client.time.TimeRangeFilter r5 = r11.getTimeRangeFilter()
            android.health.connect.LocalTimeRangeFilter r5 = androidx.health.connect.client.impl.platform.records.RequestConvertersKt.toPlatformLocalTimeRangeFilter(r5)
            java.time.LocalDateTime r7 = androidx.health.connect.client.impl.d0.a(r5)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r7)
            java.time.Period r8 = r11.getTimeRangeSlicer()
            java.time.Period r2 = androidx.health.connect.client.impl.e0.a(r8, r2)
            java.time.temporal.TemporalAmount r2 = androidx.health.connect.client.impl.f0.a(r2)
            java.time.LocalDateTime r2 = androidx.health.connect.client.impl.b.a(r7, r2)
            java.time.Period r7 = r11.getTimeRangeSlicer()
            java.time.temporal.TemporalAmount r7 = androidx.health.connect.client.impl.f0.a(r7)
            java.time.LocalDateTime r7 = androidx.health.connect.client.impl.b.a(r2, r7)
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r6)
            java.util.Set r6 = r11.getMetrics$connect_client_release()
            java.lang.String r8 = "bucketStartTime"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r8)
            java.time.LocalDateTime r8 = androidx.health.connect.client.impl.a0.a(r5)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r8)
            java.time.chrono.ChronoLocalDateTime r9 = androidx.health.connect.client.aggregate.g.a(r7)
            boolean r8 = androidx.health.connect.client.aggregate.h.a(r8, r9)
            if (r8 == 0) goto Le3
            java.time.LocalDateTime r7 = androidx.health.connect.client.impl.a0.a(r5)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r7)
        Le3:
            java.lang.String r5 = "if (requestTimeRangeFilt…                        }"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r7, r5)
            androidx.health.connect.client.aggregate.AggregationResultGroupedByPeriod r2 = androidx.health.connect.client.impl.platform.records.ResponseConvertersKt.toSdkResponse(r3, r6, r2, r7)
            goto Lf8
        Led:
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r6)
            java.util.Set r2 = r11.getMetrics$connect_client_release()
            androidx.health.connect.client.aggregate.AggregationResultGroupedByPeriod r2 = androidx.health.connect.client.impl.platform.records.ResponseConvertersKt.toSdkResponse(r3, r2)
        Lf8:
            r0.add(r2)
            r2 = r4
            goto L60
        Lfe:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.aggregateGroupByPeriod(androidx.health.connect.client.request.AggregateGroupByPeriodRequest, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // androidx.health.connect.client.HealthConnectClient
    @Nullable
    public Object deleteRecords(@NotNull KClass<? extends Record> kClass, @NotNull List<String> list, @NotNull List<String> list2, @NotNull Continuation<? super Unit> continuation) throws Exception {
        Object objWrapPlatformException = wrapPlatformException(new C03112(list, list2, kClass, null), continuation);
        return objWrapPlatformException == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objWrapPlatformException : Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.health.connect.client.HealthConnectClient
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object getChanges(@org.jetbrains.annotations.NotNull java.lang.String r8, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super androidx.health.connect.client.response.ChangesResponse> r9) throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 273
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.getChanges(java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.health.connect.client.HealthConnectClient
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object getChangesToken(@org.jetbrains.annotations.NotNull androidx.health.connect.client.request.ChangesTokenRequest r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super java.lang.String> r6) throws java.lang.Exception {
        /*
            r4 = this;
            boolean r0 = r6 instanceof androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.C03131
            if (r0 == 0) goto L13
            r0 = r6
            androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$getChangesToken$1 r0 = (androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.C03131) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$getChangesToken$1 r0 = new androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$getChangesToken$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L31
            if (r2 != r3) goto L29
            kotlin.ResultKt.throwOnFailure(r6)
            goto L43
        L29:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L31:
            kotlin.ResultKt.throwOnFailure(r6)
            androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$getChangesToken$2 r6 = new androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$getChangesToken$2
            r2 = 0
            r6.<init>(r5, r2)
            r0.label = r3
            java.lang.Object r6 = r4.wrapPlatformException(r6, r0)
            if (r6 != r1) goto L43
            return r1
        L43:
            android.health.connect.changelog.ChangeLogTokenResponse r5 = androidx.health.connect.client.impl.f.a(r6)
            java.lang.String r5 = androidx.health.connect.client.impl.g.a(r5)
            java.lang.String r6 = "override suspend fun get…\n            .token\n    }"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r6)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.getChangesToken(androidx.health.connect.client.request.ChangesTokenRequest, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // androidx.health.connect.client.PermissionController
    @Nullable
    public Object getGrantedPermissions(@NotNull Continuation<? super Set<String>> continuation) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), PackageManager.PackageInfoFlags.of(PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM));
        Set setCreateSetBuilder = SetsKt.createSetBuilder();
        int length = packageInfo.requestedPermissions.length;
        for (int i2 = 0; i2 < length; i2++) {
            String str = packageInfo.requestedPermissions[i2];
            Intrinsics.checkNotNullExpressionValue(str, "it.requestedPermissions[i]");
            if (StringsKt.startsWith$default(str, HealthPermission.PERMISSION_PREFIX, false, 2, (Object) null) && (packageInfo.requestedPermissionsFlags[i2] & 2) > 0) {
                String str2 = packageInfo.requestedPermissions[i2];
                Intrinsics.checkNotNullExpressionValue(str2, "it.requestedPermissions[i]");
                setCreateSetBuilder.add(str2);
            }
        }
        return SetsKt.build(setCreateSetBuilder);
    }

    @Override // androidx.health.connect.client.HealthConnectClient
    @NotNull
    public PermissionController getPermissionController() {
        return this;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.health.connect.client.HealthConnectClient
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object insertRecords(@org.jetbrains.annotations.NotNull java.util.List<? extends androidx.health.connect.client.records.Record> r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super androidx.health.connect.client.response.InsertRecordsResponse> r6) throws java.lang.Exception {
        /*
            r4 = this;
            boolean r0 = r6 instanceof androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.C03151
            if (r0 == 0) goto L13
            r0 = r6
            androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$insertRecords$1 r0 = (androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.C03151) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$insertRecords$1 r0 = new androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$insertRecords$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L31
            if (r2 != r3) goto L29
            kotlin.ResultKt.throwOnFailure(r6)
            goto L43
        L29:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L31:
            kotlin.ResultKt.throwOnFailure(r6)
            androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$insertRecords$response$1 r6 = new androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$insertRecords$response$1
            r2 = 0
            r6.<init>(r4, r5, r2)
            r0.label = r3
            java.lang.Object r6 = r4.wrapPlatformException(r6, r0)
            if (r6 != r1) goto L43
            return r1
        L43:
            android.health.connect.InsertRecordsResponse r5 = androidx.health.connect.client.impl.e.a(r6)
            java.lang.String r6 = "response"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r6)
            androidx.health.connect.client.response.InsertRecordsResponse r5 = androidx.health.connect.client.impl.platform.response.InsertRecordsResponseConverterKt.toKtResponse(r5)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.insertRecords(java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.health.connect.client.HealthConnectClient
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public <T extends androidx.health.connect.client.records.Record> java.lang.Object readRecord(@org.jetbrains.annotations.NotNull kotlin.reflect.KClass<T> r5, @org.jetbrains.annotations.NotNull java.lang.String r6, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super androidx.health.connect.client.response.ReadRecordResponse<T>> r7) throws java.lang.Exception {
        /*
            r4 = this;
            boolean r0 = r7 instanceof androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.C03161
            if (r0 == 0) goto L13
            r0 = r7
            androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$readRecord$1 r0 = (androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.C03161) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$readRecord$1 r0 = new androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$readRecord$1
            r0.<init>(r7)
        L18:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L31
            if (r2 != r3) goto L29
            kotlin.ResultKt.throwOnFailure(r7)
            goto L43
        L29:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L31:
            kotlin.ResultKt.throwOnFailure(r7)
            androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$readRecord$response$1 r7 = new androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$readRecord$response$1
            r2 = 0
            r7.<init>(r4, r5, r6, r2)
            r0.label = r3
            java.lang.Object r7 = r4.wrapPlatformException(r7, r0)
            if (r7 != r1) goto L43
            return r1
        L43:
            android.health.connect.ReadRecordsResponse r5 = androidx.health.connect.client.impl.a.a(r7)
            java.util.List r6 = androidx.health.connect.client.impl.l.a(r5)
            boolean r6 = r6.isEmpty()
            if (r6 != 0) goto L72
            androidx.health.connect.client.response.ReadRecordResponse r6 = new androidx.health.connect.client.response.ReadRecordResponse
            java.util.List r5 = androidx.health.connect.client.impl.l.a(r5)
            r7 = 0
            java.lang.Object r5 = r5.get(r7)
            java.lang.String r7 = "response.records[0]"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r7)
            android.health.connect.datatypes.Record r5 = androidx.health.connect.client.impl.w.a(r5)
            androidx.health.connect.client.records.Record r5 = androidx.health.connect.client.impl.platform.records.RecordConvertersKt.toSdkRecord(r5)
            java.lang.String r7 = "null cannot be cast to non-null type T of androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.readRecord"
            kotlin.jvm.internal.Intrinsics.checkNotNull(r5, r7)
            r6.<init>(r5)
            return r6
        L72:
            android.os.RemoteException r5 = new android.os.RemoteException
            java.lang.String r6 = "No records"
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.readRecord(kotlin.reflect.KClass, java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.health.connect.client.HealthConnectClient
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public <T extends androidx.health.connect.client.records.Record> java.lang.Object readRecords(@org.jetbrains.annotations.NotNull androidx.health.connect.client.request.ReadRecordsRequest<T> r8, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super androidx.health.connect.client.response.ReadRecordsResponse<T>> r9) throws java.lang.Exception {
        /*
            r7 = this;
            boolean r0 = r9 instanceof androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.C03171
            if (r0 == 0) goto L13
            r0 = r9
            androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$readRecords$1 r0 = (androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.C03171) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$readRecords$1 r0 = new androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$readRecords$1
            r0.<init>(r9)
        L18:
            java.lang.Object r9 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r9)
            goto L43
        L2a:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L32:
            kotlin.ResultKt.throwOnFailure(r9)
            androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$readRecords$response$1 r9 = new androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl$readRecords$response$1
            r9.<init>(r7, r8, r4)
            r0.label = r3
            java.lang.Object r9 = r7.wrapPlatformException(r9, r0)
            if (r9 != r1) goto L43
            return r1
        L43:
            android.health.connect.ReadRecordsResponse r8 = androidx.health.connect.client.impl.a.a(r9)
            java.util.List r9 = androidx.health.connect.client.impl.l.a(r8)
            java.lang.String r0 = "response.records"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r9, r0)
            java.lang.Iterable r9 = (java.lang.Iterable) r9
            java.util.ArrayList r0 = new java.util.ArrayList
            r1 = 10
            int r1 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r9, r1)
            r0.<init>(r1)
            java.util.Iterator r9 = r9.iterator()
        L61:
            boolean r1 = r9.hasNext()
            if (r1 == 0) goto L81
            java.lang.Object r1 = r9.next()
            android.health.connect.datatypes.Record r1 = androidx.health.connect.client.impl.w.a(r1)
            java.lang.String r2 = "it"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r2)
            androidx.health.connect.client.records.Record r1 = androidx.health.connect.client.impl.platform.records.RecordConvertersKt.toSdkRecord(r1)
            java.lang.String r2 = "null cannot be cast to non-null type T of androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.readRecords$lambda$0"
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1, r2)
            r0.add(r1)
            goto L61
        L81:
            long r8 = androidx.health.connect.client.impl.h.a(r8)
            java.lang.Long r8 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r8)
            long r1 = r8.longValue()
            r5 = -1
            int r9 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r9 != 0) goto L94
            r8 = r4
        L94:
            if (r8 == 0) goto L9a
            java.lang.String r4 = r8.toString()
        L9a:
            androidx.health.connect.client.response.ReadRecordsResponse r8 = new androidx.health.connect.client.response.ReadRecordsResponse
            r8.<init>(r0, r4)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl.readRecords(androidx.health.connect.client.request.ReadRecordsRequest, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // androidx.health.connect.client.PermissionController
    @Nullable
    public Object revokeAllPermissions(@NotNull Continuation<? super Unit> continuation) {
        String[] strArr = this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), PackageManager.PackageInfoFlags.of(PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM)).requestedPermissions;
        Intrinsics.checkNotNullExpressionValue(strArr, "context.packageManager\n …    .requestedPermissions");
        ArrayList arrayList = new ArrayList();
        for (String it : strArr) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            if (StringsKt.startsWith$default(it, HealthPermission.PERMISSION_PREFIX, false, 2, (Object) null)) {
                arrayList.add(it);
            }
        }
        this.revokePermissionsFunction.invoke(arrayList);
        return Unit.INSTANCE;
    }

    @Override // androidx.health.connect.client.HealthConnectClient
    @Nullable
    public Object updateRecords(@NotNull List<? extends Record> list, @NotNull Continuation<? super Unit> continuation) throws Exception {
        Object objWrapPlatformException = wrapPlatformException(new C03182(list, null), continuation);
        return objWrapPlatformException == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objWrapPlatformException : Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @VisibleForTesting
    public HealthConnectClientUpsideDownImpl(@NotNull Context context, @NotNull Function1<? super Collection<String>, Unit> revokePermissionsFunction) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(revokePermissionsFunction, "revokePermissionsFunction");
        this.executor = ExecutorsKt.asExecutor(Dispatchers.getDefault());
        this.context = context;
        Object systemService = context.getSystemService("healthconnect");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.health.connect.HealthConnectManager");
        this.healthConnectManager = i.a(systemService);
        this.revokePermissionsFunction = revokePermissionsFunction;
    }

    @Override // androidx.health.connect.client.HealthConnectClient
    @Nullable
    public Object deleteRecords(@NotNull KClass<? extends Record> kClass, @NotNull TimeRangeFilter timeRangeFilter, @NotNull Continuation<? super Unit> continuation) throws Exception {
        Object objWrapPlatformException = wrapPlatformException(new AnonymousClass4(kClass, timeRangeFilter, null), continuation);
        return objWrapPlatformException == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objWrapPlatformException : Unit.INSTANCE;
    }
}
