package androidx.health.connect.client.impl;

import androidx.exifinterface.media.ExifInterface;
import androidx.health.connect.client.HealthConnectClient;
import androidx.health.connect.client.PermissionController;
import androidx.health.connect.client.permission.HealthPermission;
import androidx.health.connect.client.records.Record;
import androidx.health.platform.client.HealthDataAsyncClient;
import com.facebook.share.internal.ShareConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u001f\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\u0019\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0010J\u001f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u00062\u0006\u0010\u000e\u001a\u00020\u0013H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0014J\u001f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u00062\u0006\u0010\u000e\u001a\u00020\u0017H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0018J)\u0010\u0019\u001a\u00020\u001a2\u000e\u0010\u001b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001d0\u001c2\u0006\u0010\u001e\u001a\u00020\u001fH\u0096@ø\u0001\u0000¢\u0006\u0002\u0010 J=\u0010\u0019\u001a\u00020\u001a2\u000e\u0010\u001b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001d0\u001c2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010#J\u0019\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u0007H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010'J\u0019\u0010(\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020)H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010*J\u0017\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00070,H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010-J\u001f\u0010.\u001a\u00020/2\f\u00100\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0006H\u0096@ø\u0001\u0000¢\u0006\u0002\u00101J7\u00102\u001a\b\u0012\u0004\u0012\u0002H403\"\b\b\u0000\u00104*\u00020\u001d2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H40\u001c2\u0006\u00105\u001a\u00020\u0007H\u0096@ø\u0001\u0000¢\u0006\u0002\u00106J/\u00107\u001a\b\u0012\u0004\u0012\u0002H408\"\b\b\u0000\u00104*\u00020\u001d2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H409H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010:J\u0011\u0010;\u001a\u00020\u001aH\u0096@ø\u0001\u0000¢\u0006\u0002\u0010-J\u001f\u0010<\u001a\u00020\u001a2\f\u00100\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0006H\u0096@ø\u0001\u0000¢\u0006\u0002\u00101R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006="}, d2 = {"Landroidx/health/connect/client/impl/HealthConnectClientImpl;", "Landroidx/health/connect/client/HealthConnectClient;", "Landroidx/health/connect/client/PermissionController;", "delegate", "Landroidx/health/platform/client/HealthDataAsyncClient;", "allPermissions", "", "", "(Landroidx/health/platform/client/HealthDataAsyncClient;Ljava/util/List;)V", "permissionController", "getPermissionController", "()Landroidx/health/connect/client/PermissionController;", "aggregate", "Landroidx/health/connect/client/aggregate/AggregationResult;", ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID, "Landroidx/health/connect/client/request/AggregateRequest;", "(Landroidx/health/connect/client/request/AggregateRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "aggregateGroupByDuration", "Landroidx/health/connect/client/aggregate/AggregationResultGroupedByDuration;", "Landroidx/health/connect/client/request/AggregateGroupByDurationRequest;", "(Landroidx/health/connect/client/request/AggregateGroupByDurationRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "aggregateGroupByPeriod", "Landroidx/health/connect/client/aggregate/AggregationResultGroupedByPeriod;", "Landroidx/health/connect/client/request/AggregateGroupByPeriodRequest;", "(Landroidx/health/connect/client/request/AggregateGroupByPeriodRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteRecords", "", "recordType", "Lkotlin/reflect/KClass;", "Landroidx/health/connect/client/records/Record;", "timeRangeFilter", "Landroidx/health/connect/client/time/TimeRangeFilter;", "(Lkotlin/reflect/KClass;Landroidx/health/connect/client/time/TimeRangeFilter;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "recordIdsList", "clientRecordIdsList", "(Lkotlin/reflect/KClass;Ljava/util/List;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getChanges", "Landroidx/health/connect/client/response/ChangesResponse;", "changesToken", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getChangesToken", "Landroidx/health/connect/client/request/ChangesTokenRequest;", "(Landroidx/health/connect/client/request/ChangesTokenRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getGrantedPermissions", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertRecords", "Landroidx/health/connect/client/response/InsertRecordsResponse;", "records", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "readRecord", "Landroidx/health/connect/client/response/ReadRecordResponse;", ExifInterface.GPS_DIRECTION_TRUE, "recordId", "(Lkotlin/reflect/KClass;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "readRecords", "Landroidx/health/connect/client/response/ReadRecordsResponse;", "Landroidx/health/connect/client/request/ReadRecordsRequest;", "(Landroidx/health/connect/client/request/ReadRecordsRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "revokeAllPermissions", "updateRecords", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nHealthConnectClientImpl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HealthConnectClientImpl.kt\nandroidx/health/connect/client/impl/HealthConnectClientImpl\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,232:1\n76#2:233\n96#2,5:234\n1549#3:239\n1620#3,3:240\n1549#3:243\n1620#3,3:244\n1549#3:247\n1620#3,3:248\n1549#3:251\n1620#3,3:252\n1549#3:255\n1620#3,3:256\n1549#3:259\n1620#3,3:260\n1549#3:263\n1620#3,3:264\n1549#3:267\n1620#3,3:268\n*S KotlinDebug\n*F\n+ 1 HealthConnectClientImpl.kt\nandroidx/health/connect/client/impl/HealthConnectClientImpl\n*L\n68#1:233\n68#1:234,5\n85#1:239\n85#1:240,3\n89#1:243\n89#1:244,3\n107#1:247\n107#1:248,3\n113#1:251\n113#1:252,3\n158#1:255\n158#1:256,3\n160#1:259\n160#1:260,3\n212#1:263\n212#1:264,3\n224#1:267\n224#1:268,3\n*E\n"})
/* loaded from: classes.dex */
public final class HealthConnectClientImpl implements HealthConnectClient, PermissionController {

    @NotNull
    private final List<String> allPermissions;

    @NotNull
    private final HealthDataAsyncClient delegate;

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientImpl", f = "HealthConnectClientImpl.kt", i = {}, l = {201}, m = "aggregate", n = {}, s = {})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientImpl$aggregate$1, reason: invalid class name */
    static final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HealthConnectClientImpl.this.aggregate(null, this);
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientImpl", f = "HealthConnectClientImpl.kt", i = {}, l = {211}, m = "aggregateGroupByDuration", n = {}, s = {})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientImpl$aggregateGroupByDuration$1, reason: invalid class name and case insensitive filesystem */
    static final class C02951 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C02951(Continuation<? super C02951> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HealthConnectClientImpl.this.aggregateGroupByDuration(null, this);
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientImpl", f = "HealthConnectClientImpl.kt", i = {}, l = {223}, m = "aggregateGroupByPeriod", n = {}, s = {})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientImpl$aggregateGroupByPeriod$1, reason: invalid class name and case insensitive filesystem */
    static final class C02961 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C02961(Continuation<? super C02961> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HealthConnectClientImpl.this.aggregateGroupByPeriod(null, this);
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientImpl", f = "HealthConnectClientImpl.kt", i = {0, 0}, l = {127}, m = "deleteRecords", n = {"recordIdsList", "clientRecordIdsList"}, s = {"L$0", "L$1"})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientImpl$deleteRecords$1, reason: invalid class name and case insensitive filesystem */
    static final class C02971 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        C02971(Continuation<? super C02971> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HealthConnectClientImpl.this.deleteRecords(null, null, null, this);
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientImpl", f = "HealthConnectClientImpl.kt", i = {}, l = {138}, m = "deleteRecords", n = {}, s = {})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientImpl$deleteRecords$2, reason: invalid class name */
    static final class AnonymousClass2 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HealthConnectClientImpl.this.deleteRecords(null, null, this);
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientImpl", f = "HealthConnectClientImpl.kt", i = {0}, l = {182}, m = "getChanges", n = {"changesToken"}, s = {"L$0"})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientImpl$getChanges$1, reason: invalid class name and case insensitive filesystem */
    static final class C02981 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C02981(Continuation<? super C02981> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HealthConnectClientImpl.this.getChanges(null, this);
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientImpl", f = "HealthConnectClientImpl.kt", i = {}, l = {168}, m = "getChangesToken", n = {}, s = {})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientImpl$getChangesToken$1, reason: invalid class name and case insensitive filesystem */
    static final class C02991 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C02991(Continuation<? super C02991> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HealthConnectClientImpl.this.getChangesToken(null, this);
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientImpl", f = "HealthConnectClientImpl.kt", i = {0}, l = {88}, m = "getGrantedPermissions", n = {"this"}, s = {"L$0"})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientImpl$getGrantedPermissions$1, reason: invalid class name and case insensitive filesystem */
    static final class C03001 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C03001(Continuation<? super C03001> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HealthConnectClientImpl.this.getGrantedPermissions(this);
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientImpl", f = "HealthConnectClientImpl.kt", i = {0}, l = {107}, m = "insertRecords", n = {"records"}, s = {"L$0"})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientImpl$insertRecords$1, reason: invalid class name and case insensitive filesystem */
    static final class C03011 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C03011(Continuation<? super C03011> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HealthConnectClientImpl.this.insertRecords(null, this);
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientImpl", f = "HealthConnectClientImpl.kt", i = {0}, l = {147}, m = "readRecord", n = {"recordId"}, s = {"L$0"})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientImpl$readRecord$1, reason: invalid class name and case insensitive filesystem */
    static final class C03021<T extends Record> extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C03021(Continuation<? super C03021> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HealthConnectClientImpl.this.readRecord(null, null, this);
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientImpl", f = "HealthConnectClientImpl.kt", i = {}, l = {194}, m = "readRecords", n = {}, s = {})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientImpl$readRecords$1, reason: invalid class name and case insensitive filesystem */
    static final class C03031<T extends Record> extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C03031(Continuation<? super C03031> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HealthConnectClientImpl.this.readRecords(null, this);
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientImpl", f = "HealthConnectClientImpl.kt", i = {}, l = {99}, m = "revokeAllPermissions", n = {}, s = {})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientImpl$revokeAllPermissions$1, reason: invalid class name and case insensitive filesystem */
    static final class C03041 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C03041(Continuation<? super C03041> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HealthConnectClientImpl.this.revokeAllPermissions(this);
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.health.connect.client.impl.HealthConnectClientImpl", f = "HealthConnectClientImpl.kt", i = {0}, l = {113}, m = "updateRecords", n = {"records"}, s = {"L$0"})
    /* renamed from: androidx.health.connect.client.impl.HealthConnectClientImpl$updateRecords$1, reason: invalid class name and case insensitive filesystem */
    static final class C03051 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C03051(Continuation<? super C03051> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HealthConnectClientImpl.this.updateRecords(null, this);
        }
    }

    public HealthConnectClientImpl(@NotNull HealthDataAsyncClient delegate, @NotNull List<String> allPermissions) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(allPermissions, "allPermissions");
        this.delegate = delegate;
        this.allPermissions = allPermissions;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.health.connect.client.HealthConnectClient
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object aggregate(@org.jetbrains.annotations.NotNull androidx.health.connect.client.request.AggregateRequest r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super androidx.health.connect.client.aggregate.AggregationResult> r6) throws java.lang.Throwable {
        /*
            r4 = this;
            boolean r0 = r6 instanceof androidx.health.connect.client.impl.HealthConnectClientImpl.AnonymousClass1
            if (r0 == 0) goto L13
            r0 = r6
            androidx.health.connect.client.impl.HealthConnectClientImpl$aggregate$1 r0 = (androidx.health.connect.client.impl.HealthConnectClientImpl.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.health.connect.client.impl.HealthConnectClientImpl$aggregate$1 r0 = new androidx.health.connect.client.impl.HealthConnectClientImpl$aggregate$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L31
            if (r2 != r3) goto L29
            kotlin.ResultKt.throwOnFailure(r6)
            goto L47
        L29:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L31:
            kotlin.ResultKt.throwOnFailure(r6)
            androidx.health.platform.client.HealthDataAsyncClient r6 = r4.delegate
            androidx.health.platform.client.proto.RequestProto$AggregateDataRequest r5 = androidx.health.connect.client.impl.converters.request.AggregateRequestToProtoKt.toProto(r5)
            com.google.common.util.concurrent.ListenableFuture r5 = r6.aggregate(r5)
            r0.label = r3
            java.lang.Object r6 = kotlinx.coroutines.guava.ListenableFutureKt.await(r5, r0)
            if (r6 != r1) goto L47
            return r1
        L47:
            androidx.health.platform.client.proto.ResponseProto$AggregateDataResponse r6 = (androidx.health.platform.client.proto.ResponseProto.AggregateDataResponse) r6
            java.util.List r5 = r6.getRowsList()
            java.lang.String r6 = "responseProto.rowsList"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r6)
            java.lang.Object r5 = kotlin.collections.CollectionsKt.first(r5)
            java.lang.String r6 = "responseProto.rowsList.first()"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r6)
            androidx.health.platform.client.proto.DataProto$AggregateDataRow r5 = (androidx.health.platform.client.proto.DataProto.AggregateDataRow) r5
            androidx.health.connect.client.aggregate.AggregationResult r5 = androidx.health.connect.client.impl.converters.aggregate.ProtoToAggregateDataRowKt.retrieveAggregateDataRow(r5)
            java.util.Map r6 = r5.getLongValues$connect_client_release()
            int r6 = r6.size()
            java.util.Map r0 = r5.getDoubleValues$connect_client_release()
            int r0 = r0.size()
            int r6 = r6 + r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Retrieved "
            r0.append(r1)
            r0.append(r6)
            java.lang.String r6 = " metrics."
            r0.append(r6)
            java.lang.String r6 = r0.toString()
            java.lang.String r0 = "HealthConnectClient"
            androidx.health.platform.client.impl.logger.Logger.debug(r0, r6)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.health.connect.client.impl.HealthConnectClientImpl.aggregate(androidx.health.connect.client.request.AggregateRequest, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.health.connect.client.HealthConnectClient
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object aggregateGroupByDuration(@org.jetbrains.annotations.NotNull androidx.health.connect.client.request.AggregateGroupByDurationRequest r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super java.util.List<androidx.health.connect.client.aggregate.AggregationResultGroupedByDuration>> r6) throws java.lang.Throwable {
        /*
            r4 = this;
            boolean r0 = r6 instanceof androidx.health.connect.client.impl.HealthConnectClientImpl.C02951
            if (r0 == 0) goto L13
            r0 = r6
            androidx.health.connect.client.impl.HealthConnectClientImpl$aggregateGroupByDuration$1 r0 = (androidx.health.connect.client.impl.HealthConnectClientImpl.C02951) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.health.connect.client.impl.HealthConnectClientImpl$aggregateGroupByDuration$1 r0 = new androidx.health.connect.client.impl.HealthConnectClientImpl$aggregateGroupByDuration$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L31
            if (r2 != r3) goto L29
            kotlin.ResultKt.throwOnFailure(r6)
            goto L47
        L29:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L31:
            kotlin.ResultKt.throwOnFailure(r6)
            androidx.health.platform.client.HealthDataAsyncClient r6 = r4.delegate
            androidx.health.platform.client.proto.RequestProto$AggregateDataRequest r5 = androidx.health.connect.client.impl.converters.request.AggregateRequestToProtoKt.toProto(r5)
            com.google.common.util.concurrent.ListenableFuture r5 = r6.aggregate(r5)
            r0.label = r3
            java.lang.Object r6 = kotlinx.coroutines.guava.ListenableFutureKt.await(r5, r0)
            if (r6 != r1) goto L47
            return r1
        L47:
            androidx.health.platform.client.proto.ResponseProto$AggregateDataResponse r6 = (androidx.health.platform.client.proto.ResponseProto.AggregateDataResponse) r6
            java.util.List r5 = r6.getRowsList()
            java.lang.String r6 = "responseProto.rowsList"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r6)
            java.lang.Iterable r5 = (java.lang.Iterable) r5
            java.util.ArrayList r6 = new java.util.ArrayList
            r0 = 10
            int r0 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r5, r0)
            r6.<init>(r0)
            java.util.Iterator r5 = r5.iterator()
        L63:
            boolean r0 = r5.hasNext()
            if (r0 == 0) goto L7c
            java.lang.Object r0 = r5.next()
            androidx.health.platform.client.proto.DataProto$AggregateDataRow r0 = (androidx.health.platform.client.proto.DataProto.AggregateDataRow) r0
            java.lang.String r1 = "it"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            androidx.health.connect.client.aggregate.AggregationResultGroupedByDuration r0 = androidx.health.connect.client.impl.converters.aggregate.ProtoToAggregateDataRowKt.toAggregateDataRowGroupByDuration(r0)
            r6.add(r0)
            goto L63
        L7c:
            java.util.List r5 = kotlin.collections.CollectionsKt.toList(r6)
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r0 = "Retrieved "
            r6.append(r0)
            int r0 = r5.size()
            r6.append(r0)
            java.lang.String r0 = " duration aggregation buckets."
            r6.append(r0)
            java.lang.String r6 = r6.toString()
            java.lang.String r0 = "HealthConnectClient"
            androidx.health.platform.client.impl.logger.Logger.debug(r0, r6)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.health.connect.client.impl.HealthConnectClientImpl.aggregateGroupByDuration(androidx.health.connect.client.request.AggregateGroupByDurationRequest, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.health.connect.client.HealthConnectClient
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object aggregateGroupByPeriod(@org.jetbrains.annotations.NotNull androidx.health.connect.client.request.AggregateGroupByPeriodRequest r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super java.util.List<androidx.health.connect.client.aggregate.AggregationResultGroupedByPeriod>> r6) throws java.lang.Throwable {
        /*
            r4 = this;
            boolean r0 = r6 instanceof androidx.health.connect.client.impl.HealthConnectClientImpl.C02961
            if (r0 == 0) goto L13
            r0 = r6
            androidx.health.connect.client.impl.HealthConnectClientImpl$aggregateGroupByPeriod$1 r0 = (androidx.health.connect.client.impl.HealthConnectClientImpl.C02961) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.health.connect.client.impl.HealthConnectClientImpl$aggregateGroupByPeriod$1 r0 = new androidx.health.connect.client.impl.HealthConnectClientImpl$aggregateGroupByPeriod$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L31
            if (r2 != r3) goto L29
            kotlin.ResultKt.throwOnFailure(r6)
            goto L47
        L29:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L31:
            kotlin.ResultKt.throwOnFailure(r6)
            androidx.health.platform.client.HealthDataAsyncClient r6 = r4.delegate
            androidx.health.platform.client.proto.RequestProto$AggregateDataRequest r5 = androidx.health.connect.client.impl.converters.request.AggregateRequestToProtoKt.toProto(r5)
            com.google.common.util.concurrent.ListenableFuture r5 = r6.aggregate(r5)
            r0.label = r3
            java.lang.Object r6 = kotlinx.coroutines.guava.ListenableFutureKt.await(r5, r0)
            if (r6 != r1) goto L47
            return r1
        L47:
            androidx.health.platform.client.proto.ResponseProto$AggregateDataResponse r6 = (androidx.health.platform.client.proto.ResponseProto.AggregateDataResponse) r6
            java.util.List r5 = r6.getRowsList()
            java.lang.String r6 = "responseProto.rowsList"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r6)
            java.lang.Iterable r5 = (java.lang.Iterable) r5
            java.util.ArrayList r6 = new java.util.ArrayList
            r0 = 10
            int r0 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r5, r0)
            r6.<init>(r0)
            java.util.Iterator r5 = r5.iterator()
        L63:
            boolean r0 = r5.hasNext()
            if (r0 == 0) goto L7c
            java.lang.Object r0 = r5.next()
            androidx.health.platform.client.proto.DataProto$AggregateDataRow r0 = (androidx.health.platform.client.proto.DataProto.AggregateDataRow) r0
            java.lang.String r1 = "it"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            androidx.health.connect.client.aggregate.AggregationResultGroupedByPeriod r0 = androidx.health.connect.client.impl.converters.aggregate.ProtoToAggregateDataRowKt.toAggregateDataRowGroupByPeriod(r0)
            r6.add(r0)
            goto L63
        L7c:
            java.util.List r5 = kotlin.collections.CollectionsKt.toList(r6)
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r0 = "Retrieved "
            r6.append(r0)
            int r0 = r5.size()
            r6.append(r0)
            java.lang.String r0 = " period aggregation buckets."
            r6.append(r0)
            java.lang.String r6 = r6.toString()
            java.lang.String r0 = "HealthConnectClient"
            androidx.health.platform.client.impl.logger.Logger.debug(r0, r6)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.health.connect.client.impl.HealthConnectClientImpl.aggregateGroupByPeriod(androidx.health.connect.client.request.AggregateGroupByPeriodRequest, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.health.connect.client.HealthConnectClient
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object deleteRecords(@org.jetbrains.annotations.NotNull kotlin.reflect.KClass<? extends androidx.health.connect.client.records.Record> r5, @org.jetbrains.annotations.NotNull java.util.List<java.lang.String> r6, @org.jetbrains.annotations.NotNull java.util.List<java.lang.String> r7, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super kotlin.Unit> r8) throws java.lang.Throwable {
        /*
            r4 = this;
            boolean r0 = r8 instanceof androidx.health.connect.client.impl.HealthConnectClientImpl.C02971
            if (r0 == 0) goto L13
            r0 = r8
            androidx.health.connect.client.impl.HealthConnectClientImpl$deleteRecords$1 r0 = (androidx.health.connect.client.impl.HealthConnectClientImpl.C02971) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.health.connect.client.impl.HealthConnectClientImpl$deleteRecords$1 r0 = new androidx.health.connect.client.impl.HealthConnectClientImpl$deleteRecords$1
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3b
            if (r2 != r3) goto L33
            java.lang.Object r5 = r0.L$1
            r7 = r5
            java.util.List r7 = (java.util.List) r7
            java.lang.Object r5 = r0.L$0
            r6 = r5
            java.util.List r6 = (java.util.List) r6
            kotlin.ResultKt.throwOnFailure(r8)
            goto L59
        L33:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L3b:
            kotlin.ResultKt.throwOnFailure(r8)
            androidx.health.platform.client.HealthDataAsyncClient r8 = r4.delegate
            java.util.List r2 = androidx.health.connect.client.impl.converters.datatype.DataTypeIdPairConverterKt.toDataTypeIdPairProtoList(r5, r6)
            java.util.List r5 = androidx.health.connect.client.impl.converters.datatype.DataTypeIdPairConverterKt.toDataTypeIdPairProtoList(r5, r7)
            com.google.common.util.concurrent.ListenableFuture r5 = r8.deleteData(r2, r5)
            r0.L$0 = r6
            r0.L$1 = r7
            r0.label = r3
            java.lang.Object r5 = kotlinx.coroutines.guava.ListenableFutureKt.await(r5, r0)
            if (r5 != r1) goto L59
            return r1
        L59:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            int r6 = r6.size()
            int r7 = r7.size()
            int r6 = r6 + r7
            r5.append(r6)
            java.lang.String r6 = " records deleted."
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            java.lang.String r6 = "HealthConnectClient"
            androidx.health.platform.client.impl.logger.Logger.debug(r6, r5)
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.health.connect.client.impl.HealthConnectClientImpl.deleteRecords(kotlin.reflect.KClass, java.util.List, java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.health.connect.client.HealthConnectClient
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object getChanges(@org.jetbrains.annotations.NotNull java.lang.String r6, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super androidx.health.connect.client.response.ChangesResponse> r7) throws java.lang.Throwable {
        /*
            r5 = this;
            boolean r0 = r7 instanceof androidx.health.connect.client.impl.HealthConnectClientImpl.C02981
            if (r0 == 0) goto L13
            r0 = r7
            androidx.health.connect.client.impl.HealthConnectClientImpl$getChanges$1 r0 = (androidx.health.connect.client.impl.HealthConnectClientImpl.C02981) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.health.connect.client.impl.HealthConnectClientImpl$getChanges$1 r0 = new androidx.health.connect.client.impl.HealthConnectClientImpl$getChanges$1
            r0.<init>(r7)
        L18:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 != r3) goto L2d
            java.lang.Object r6 = r0.L$0
            java.lang.String r6 = (java.lang.String) r6
            kotlin.ResultKt.throwOnFailure(r7)
            goto L5c
        L2d:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L35:
            kotlin.ResultKt.throwOnFailure(r7)
            androidx.health.platform.client.HealthDataAsyncClient r7 = r5.delegate
            androidx.health.platform.client.proto.RequestProto$GetChangesRequest$Builder r2 = androidx.health.platform.client.proto.RequestProto.GetChangesRequest.newBuilder()
            androidx.health.platform.client.proto.RequestProto$GetChangesRequest$Builder r2 = r2.setChangesToken(r6)
            androidx.health.platform.client.proto.GeneratedMessageLite r2 = r2.build()
            java.lang.String r4 = "newBuilder()\n           …                 .build()"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r4)
            androidx.health.platform.client.proto.RequestProto$GetChangesRequest r2 = (androidx.health.platform.client.proto.RequestProto.GetChangesRequest) r2
            com.google.common.util.concurrent.ListenableFuture r7 = r7.getChanges(r2)
            r0.L$0 = r6
            r0.label = r3
            java.lang.Object r7 = kotlinx.coroutines.guava.ListenableFutureKt.await(r7, r0)
            if (r7 != r1) goto L5c
            return r1
        L5c:
            androidx.health.platform.client.proto.ResponseProto$GetChangesResponse r7 = (androidx.health.platform.client.proto.ResponseProto.GetChangesResponse) r7
            java.lang.String r0 = r7.getNextChangesToken()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Retrieved changes successful with "
            r1.append(r2)
            r1.append(r6)
            java.lang.String r6 = ", next token "
            r1.append(r6)
            r1.append(r0)
            r6 = 46
            r1.append(r6)
            java.lang.String r6 = r1.toString()
            java.lang.String r0 = "HealthConnectClient"
            androidx.health.platform.client.impl.logger.Logger.debug(r0, r6)
            androidx.health.connect.client.response.ChangesResponse r6 = androidx.health.connect.client.impl.converters.response.ProtoToChangesResponseKt.toChangesResponse(r7)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.health.connect.client.impl.HealthConnectClientImpl.getChanges(java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.health.connect.client.HealthConnectClient
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object getChangesToken(@org.jetbrains.annotations.NotNull androidx.health.connect.client.request.ChangesTokenRequest r9, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super java.lang.String> r10) throws java.lang.Throwable {
        /*
            r8 = this;
            boolean r0 = r10 instanceof androidx.health.connect.client.impl.HealthConnectClientImpl.C02991
            if (r0 == 0) goto L13
            r0 = r10
            androidx.health.connect.client.impl.HealthConnectClientImpl$getChangesToken$1 r0 = (androidx.health.connect.client.impl.HealthConnectClientImpl.C02991) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.health.connect.client.impl.HealthConnectClientImpl$getChangesToken$1 r0 = new androidx.health.connect.client.impl.HealthConnectClientImpl$getChangesToken$1
            r0.<init>(r10)
        L18:
            java.lang.Object r10 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r10)
            goto Lb5
        L2a:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L32:
            kotlin.ResultKt.throwOnFailure(r10)
            androidx.health.platform.client.HealthDataAsyncClient r10 = r8.delegate
            androidx.health.platform.client.proto.RequestProto$GetChangesTokenRequest$Builder r2 = androidx.health.platform.client.proto.RequestProto.GetChangesTokenRequest.newBuilder()
            java.util.Set r4 = r9.getRecordTypes$connect_client_release()
            java.util.ArrayList r5 = new java.util.ArrayList
            r6 = 10
            int r7 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r4, r6)
            r5.<init>(r7)
            java.util.Iterator r4 = r4.iterator()
        L4e:
            boolean r7 = r4.hasNext()
            if (r7 == 0) goto L62
            java.lang.Object r7 = r4.next()
            kotlin.reflect.KClass r7 = (kotlin.reflect.KClass) r7
            androidx.health.platform.client.proto.DataProto$DataType r7 = androidx.health.connect.client.impl.converters.datatype.DataTypeConverterKt.toDataType(r7)
            r5.add(r7)
            goto L4e
        L62:
            androidx.health.platform.client.proto.RequestProto$GetChangesTokenRequest$Builder r2 = r2.addAllDataType(r5)
            java.util.Set r9 = r9.getDataOriginFilters$connect_client_release()
            java.util.ArrayList r4 = new java.util.ArrayList
            int r5 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r9, r6)
            r4.<init>(r5)
            java.util.Iterator r9 = r9.iterator()
        L77:
            boolean r5 = r9.hasNext()
            if (r5 == 0) goto L99
            java.lang.Object r5 = r9.next()
            androidx.health.connect.client.records.metadata.DataOrigin r5 = (androidx.health.connect.client.records.metadata.DataOrigin) r5
            androidx.health.platform.client.proto.DataProto$DataOrigin$Builder r6 = androidx.health.platform.client.proto.DataProto.DataOrigin.newBuilder()
            java.lang.String r5 = r5.getPackageName()
            androidx.health.platform.client.proto.DataProto$DataOrigin$Builder r5 = r6.setApplicationId(r5)
            androidx.health.platform.client.proto.GeneratedMessageLite r5 = r5.build()
            androidx.health.platform.client.proto.DataProto$DataOrigin r5 = (androidx.health.platform.client.proto.DataProto.DataOrigin) r5
            r4.add(r5)
            goto L77
        L99:
            androidx.health.platform.client.proto.RequestProto$GetChangesTokenRequest$Builder r9 = r2.addAllDataOriginFilters(r4)
            androidx.health.platform.client.proto.GeneratedMessageLite r9 = r9.build()
            java.lang.String r2 = "newBuilder()\n           …                 .build()"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r9, r2)
            androidx.health.platform.client.proto.RequestProto$GetChangesTokenRequest r9 = (androidx.health.platform.client.proto.RequestProto.GetChangesTokenRequest) r9
            com.google.common.util.concurrent.ListenableFuture r9 = r10.getChangesToken(r9)
            r0.label = r3
            java.lang.Object r10 = kotlinx.coroutines.guava.ListenableFutureKt.await(r9, r0)
            if (r10 != r1) goto Lb5
            return r1
        Lb5:
            androidx.health.platform.client.proto.ResponseProto$GetChangesTokenResponse r10 = (androidx.health.platform.client.proto.ResponseProto.GetChangesTokenResponse) r10
            java.lang.String r9 = r10.getChangesToken()
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r0 = "Retrieved change token "
            r10.append(r0)
            r10.append(r9)
            r0 = 46
            r10.append(r0)
            java.lang.String r10 = r10.toString()
            java.lang.String r0 = "HealthConnectClient"
            androidx.health.platform.client.impl.logger.Logger.debug(r0, r10)
            java.lang.String r10 = "changeToken"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r9, r10)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.health.connect.client.impl.HealthConnectClientImpl.getChangesToken(androidx.health.connect.client.request.ChangesTokenRequest, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.health.connect.client.PermissionController
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object getGrantedPermissions(@org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super java.util.Set<java.lang.String>> r9) throws java.lang.Throwable {
        /*
            r8 = this;
            boolean r0 = r9 instanceof androidx.health.connect.client.impl.HealthConnectClientImpl.C03001
            if (r0 == 0) goto L13
            r0 = r9
            androidx.health.connect.client.impl.HealthConnectClientImpl$getGrantedPermissions$1 r0 = (androidx.health.connect.client.impl.HealthConnectClientImpl.C03001) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.health.connect.client.impl.HealthConnectClientImpl$getGrantedPermissions$1 r0 = new androidx.health.connect.client.impl.HealthConnectClientImpl$getGrantedPermissions$1
            r0.<init>(r9)
        L18:
            java.lang.Object r9 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 10
            r4 = 1
            if (r2 == 0) goto L37
            if (r2 != r4) goto L2f
            java.lang.Object r0 = r0.L$0
            androidx.health.connect.client.impl.HealthConnectClientImpl r0 = (androidx.health.connect.client.impl.HealthConnectClientImpl) r0
            kotlin.ResultKt.throwOnFailure(r9)
            goto L7f
        L2f:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r0)
            throw r9
        L37:
            kotlin.ResultKt.throwOnFailure(r9)
            androidx.health.platform.client.HealthDataAsyncClient r9 = r8.delegate
            java.util.List<java.lang.String> r2 = r8.allPermissions
            java.lang.Iterable r2 = (java.lang.Iterable) r2
            java.util.ArrayList r5 = new java.util.ArrayList
            int r6 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r2, r3)
            r5.<init>(r6)
            java.util.Iterator r2 = r2.iterator()
        L4d:
            boolean r6 = r2.hasNext()
            if (r6 == 0) goto L6b
            java.lang.Object r6 = r2.next()
            java.lang.String r6 = (java.lang.String) r6
            androidx.health.platform.client.proto.PermissionProto$Permission$Builder r7 = androidx.health.platform.client.proto.PermissionProto.Permission.newBuilder()
            androidx.health.platform.client.proto.PermissionProto$Permission$Builder r6 = r7.setPermission(r6)
            androidx.health.platform.client.proto.GeneratedMessageLite r6 = r6.build()
            androidx.health.platform.client.proto.PermissionProto$Permission r6 = (androidx.health.platform.client.proto.PermissionProto.Permission) r6
            r5.add(r6)
            goto L4d
        L6b:
            java.util.Set r2 = kotlin.collections.CollectionsKt.toSet(r5)
            com.google.common.util.concurrent.ListenableFuture r9 = r9.filterGrantedPermissions(r2)
            r0.L$0 = r8
            r0.label = r4
            java.lang.Object r9 = kotlinx.coroutines.guava.ListenableFutureKt.await(r9, r0)
            if (r9 != r1) goto L7e
            return r1
        L7e:
            r0 = r8
        L7f:
            java.lang.Iterable r9 = (java.lang.Iterable) r9
            java.util.ArrayList r1 = new java.util.ArrayList
            int r2 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r9, r3)
            r1.<init>(r2)
            java.util.Iterator r9 = r9.iterator()
        L8e:
            boolean r2 = r9.hasNext()
            if (r2 == 0) goto La2
            java.lang.Object r2 = r9.next()
            androidx.health.platform.client.proto.PermissionProto$Permission r2 = (androidx.health.platform.client.proto.PermissionProto.Permission) r2
            java.lang.String r2 = r2.getPermission()
            r1.add(r2)
            goto L8e
        La2:
            java.util.Set r9 = kotlin.collections.CollectionsKt.toSet(r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Granted "
            r1.append(r2)
            int r2 = r9.size()
            r1.append(r2)
            java.lang.String r2 = " out of "
            r1.append(r2)
            java.util.List<java.lang.String> r0 = r0.allPermissions
            int r0 = r0.size()
            r1.append(r0)
            java.lang.String r0 = " permissions."
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            java.lang.String r1 = "HealthConnectClient"
            androidx.health.platform.client.impl.logger.Logger.debug(r1, r0)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.health.connect.client.impl.HealthConnectClientImpl.getGrantedPermissions(kotlin.coroutines.Continuation):java.lang.Object");
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
    public java.lang.Object insertRecords(@org.jetbrains.annotations.NotNull java.util.List<? extends androidx.health.connect.client.records.Record> r7, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super androidx.health.connect.client.response.InsertRecordsResponse> r8) throws java.lang.Throwable {
        /*
            r6 = this;
            boolean r0 = r8 instanceof androidx.health.connect.client.impl.HealthConnectClientImpl.C03011
            if (r0 == 0) goto L13
            r0 = r8
            androidx.health.connect.client.impl.HealthConnectClientImpl$insertRecords$1 r0 = (androidx.health.connect.client.impl.HealthConnectClientImpl.C03011) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.health.connect.client.impl.HealthConnectClientImpl$insertRecords$1 r0 = new androidx.health.connect.client.impl.HealthConnectClientImpl$insertRecords$1
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 != r3) goto L2d
            java.lang.Object r7 = r0.L$0
            java.util.List r7 = (java.util.List) r7
            kotlin.ResultKt.throwOnFailure(r8)
            goto L6f
        L2d:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L35:
            kotlin.ResultKt.throwOnFailure(r8)
            androidx.health.platform.client.HealthDataAsyncClient r8 = r6.delegate
            r2 = r7
            java.lang.Iterable r2 = (java.lang.Iterable) r2
            java.util.ArrayList r4 = new java.util.ArrayList
            r5 = 10
            int r5 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r2, r5)
            r4.<init>(r5)
            java.util.Iterator r2 = r2.iterator()
        L4c:
            boolean r5 = r2.hasNext()
            if (r5 == 0) goto L60
            java.lang.Object r5 = r2.next()
            androidx.health.connect.client.records.Record r5 = (androidx.health.connect.client.records.Record) r5
            androidx.health.platform.client.proto.DataProto$DataPoint r5 = androidx.health.connect.client.impl.converters.records.RecordToProtoConvertersKt.toProto(r5)
            r4.add(r5)
            goto L4c
        L60:
            com.google.common.util.concurrent.ListenableFuture r8 = r8.insertData(r4)
            r0.L$0 = r7
            r0.label = r3
            java.lang.Object r8 = kotlinx.coroutines.guava.ListenableFutureKt.await(r8, r0)
            if (r8 != r1) goto L6f
            return r1
        L6f:
            java.util.List r8 = (java.util.List) r8
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            int r7 = r7.size()
            r0.append(r7)
            java.lang.String r7 = " records inserted."
            r0.append(r7)
            java.lang.String r7 = r0.toString()
            java.lang.String r0 = "HealthConnectClient"
            androidx.health.platform.client.impl.logger.Logger.debug(r0, r7)
            androidx.health.connect.client.response.InsertRecordsResponse r7 = new androidx.health.connect.client.response.InsertRecordsResponse
            r7.<init>(r8)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.health.connect.client.impl.HealthConnectClientImpl.insertRecords(java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.health.connect.client.HealthConnectClient
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public <T extends androidx.health.connect.client.records.Record> java.lang.Object readRecord(@org.jetbrains.annotations.NotNull kotlin.reflect.KClass<T> r5, @org.jetbrains.annotations.NotNull java.lang.String r6, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super androidx.health.connect.client.response.ReadRecordResponse<T>> r7) throws java.lang.Throwable {
        /*
            r4 = this;
            boolean r0 = r7 instanceof androidx.health.connect.client.impl.HealthConnectClientImpl.C03021
            if (r0 == 0) goto L13
            r0 = r7
            androidx.health.connect.client.impl.HealthConnectClientImpl$readRecord$1 r0 = (androidx.health.connect.client.impl.HealthConnectClientImpl.C03021) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.health.connect.client.impl.HealthConnectClientImpl$readRecord$1 r0 = new androidx.health.connect.client.impl.HealthConnectClientImpl$readRecord$1
            r0.<init>(r7)
        L18:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L36
            if (r2 != r3) goto L2e
            java.lang.Object r5 = r0.L$0
            r6 = r5
            java.lang.String r6 = (java.lang.String) r6
            kotlin.ResultKt.throwOnFailure(r7)
            goto L4e
        L2e:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L36:
            kotlin.ResultKt.throwOnFailure(r7)
            androidx.health.platform.client.HealthDataAsyncClient r7 = r4.delegate
            androidx.health.platform.client.proto.RequestProto$ReadDataRequest r5 = androidx.health.connect.client.impl.converters.request.ReadDataRequestToProtoKt.toReadDataRequestProto(r5, r6)
            com.google.common.util.concurrent.ListenableFuture r5 = r7.readData(r5)
            r0.L$0 = r6
            r0.label = r3
            java.lang.Object r7 = kotlinx.coroutines.guava.ListenableFutureKt.await(r5, r0)
            if (r7 != r1) goto L4e
            return r1
        L4e:
            androidx.health.platform.client.proto.DataProto$DataPoint r7 = (androidx.health.platform.client.proto.DataProto.DataPoint) r7
            androidx.health.connect.client.response.ReadRecordResponse r5 = new androidx.health.connect.client.response.ReadRecordResponse
            androidx.health.connect.client.records.Record r7 = androidx.health.connect.client.impl.converters.records.ProtoToRecordConvertersKt.toRecord(r7)
            java.lang.String r0 = "null cannot be cast to non-null type T of androidx.health.connect.client.impl.HealthConnectClientImpl.readRecord"
            kotlin.jvm.internal.Intrinsics.checkNotNull(r7, r0)
            r5.<init>(r7)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r0 = "Reading record of "
            r7.append(r0)
            r7.append(r6)
            java.lang.String r6 = " successful."
            r7.append(r6)
            java.lang.String r6 = r7.toString()
            java.lang.String r7 = "HealthConnectClient"
            androidx.health.platform.client.impl.logger.Logger.debug(r7, r6)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.health.connect.client.impl.HealthConnectClientImpl.readRecord(kotlin.reflect.KClass, java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.health.connect.client.HealthConnectClient
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public <T extends androidx.health.connect.client.records.Record> java.lang.Object readRecords(@org.jetbrains.annotations.NotNull androidx.health.connect.client.request.ReadRecordsRequest<T> r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super androidx.health.connect.client.response.ReadRecordsResponse<T>> r6) throws java.lang.Throwable {
        /*
            r4 = this;
            boolean r0 = r6 instanceof androidx.health.connect.client.impl.HealthConnectClientImpl.C03031
            if (r0 == 0) goto L13
            r0 = r6
            androidx.health.connect.client.impl.HealthConnectClientImpl$readRecords$1 r0 = (androidx.health.connect.client.impl.HealthConnectClientImpl.C03031) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.health.connect.client.impl.HealthConnectClientImpl$readRecords$1 r0 = new androidx.health.connect.client.impl.HealthConnectClientImpl$readRecords$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L31
            if (r2 != r3) goto L29
            kotlin.ResultKt.throwOnFailure(r6)
            goto L47
        L29:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L31:
            kotlin.ResultKt.throwOnFailure(r6)
            androidx.health.platform.client.HealthDataAsyncClient r6 = r4.delegate
            androidx.health.platform.client.proto.RequestProto$ReadDataRangeRequest r5 = androidx.health.connect.client.impl.converters.request.ReadDataRangeRequestToProtoKt.toReadDataRangeRequestProto(r5)
            com.google.common.util.concurrent.ListenableFuture r5 = r6.readDataRange(r5)
            r0.label = r3
            java.lang.Object r6 = kotlinx.coroutines.guava.ListenableFutureKt.await(r5, r0)
            if (r6 != r1) goto L47
            return r1
        L47:
            androidx.health.platform.client.proto.ResponseProto$ReadDataRangeResponse r6 = (androidx.health.platform.client.proto.ResponseProto.ReadDataRangeResponse) r6
            androidx.health.connect.client.response.ReadRecordsResponse r5 = androidx.health.connect.client.impl.converters.response.ProtoToReadRecordsResponseKt.toReadRecordsResponse(r6)
            java.lang.String r6 = "HealthConnectClient"
            java.lang.String r0 = "Retrieve records successful."
            androidx.health.platform.client.impl.logger.Logger.debug(r6, r0)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.health.connect.client.impl.HealthConnectClientImpl.readRecords(androidx.health.connect.client.request.ReadRecordsRequest, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.health.connect.client.PermissionController
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object revokeAllPermissions(@org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super kotlin.Unit> r5) throws java.lang.Throwable {
        /*
            r4 = this;
            boolean r0 = r5 instanceof androidx.health.connect.client.impl.HealthConnectClientImpl.C03041
            if (r0 == 0) goto L13
            r0 = r5
            androidx.health.connect.client.impl.HealthConnectClientImpl$revokeAllPermissions$1 r0 = (androidx.health.connect.client.impl.HealthConnectClientImpl.C03041) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.health.connect.client.impl.HealthConnectClientImpl$revokeAllPermissions$1 r0 = new androidx.health.connect.client.impl.HealthConnectClientImpl$revokeAllPermissions$1
            r0.<init>(r5)
        L18:
            java.lang.Object r5 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L31
            if (r2 != r3) goto L29
            kotlin.ResultKt.throwOnFailure(r5)
            goto L43
        L29:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r0)
            throw r5
        L31:
            kotlin.ResultKt.throwOnFailure(r5)
            androidx.health.platform.client.HealthDataAsyncClient r5 = r4.delegate
            com.google.common.util.concurrent.ListenableFuture r5 = r5.revokeAllPermissions()
            r0.label = r3
            java.lang.Object r5 = kotlinx.coroutines.guava.ListenableFutureKt.await(r5, r0)
            if (r5 != r1) goto L43
            return r1
        L43:
            java.lang.String r5 = "HealthConnectClient"
            java.lang.String r0 = "Revoked all permissions."
            androidx.health.platform.client.impl.logger.Logger.debug(r5, r0)
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.health.connect.client.impl.HealthConnectClientImpl.revokeAllPermissions(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.health.connect.client.HealthConnectClient
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object updateRecords(@org.jetbrains.annotations.NotNull java.util.List<? extends androidx.health.connect.client.records.Record> r7, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super kotlin.Unit> r8) throws java.lang.Throwable {
        /*
            r6 = this;
            boolean r0 = r8 instanceof androidx.health.connect.client.impl.HealthConnectClientImpl.C03051
            if (r0 == 0) goto L13
            r0 = r8
            androidx.health.connect.client.impl.HealthConnectClientImpl$updateRecords$1 r0 = (androidx.health.connect.client.impl.HealthConnectClientImpl.C03051) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.health.connect.client.impl.HealthConnectClientImpl$updateRecords$1 r0 = new androidx.health.connect.client.impl.HealthConnectClientImpl$updateRecords$1
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 != r3) goto L2d
            java.lang.Object r7 = r0.L$0
            java.util.List r7 = (java.util.List) r7
            kotlin.ResultKt.throwOnFailure(r8)
            goto L6f
        L2d:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L35:
            kotlin.ResultKt.throwOnFailure(r8)
            androidx.health.platform.client.HealthDataAsyncClient r8 = r6.delegate
            r2 = r7
            java.lang.Iterable r2 = (java.lang.Iterable) r2
            java.util.ArrayList r4 = new java.util.ArrayList
            r5 = 10
            int r5 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r2, r5)
            r4.<init>(r5)
            java.util.Iterator r2 = r2.iterator()
        L4c:
            boolean r5 = r2.hasNext()
            if (r5 == 0) goto L60
            java.lang.Object r5 = r2.next()
            androidx.health.connect.client.records.Record r5 = (androidx.health.connect.client.records.Record) r5
            androidx.health.platform.client.proto.DataProto$DataPoint r5 = androidx.health.connect.client.impl.converters.records.RecordToProtoConvertersKt.toProto(r5)
            r4.add(r5)
            goto L4c
        L60:
            com.google.common.util.concurrent.ListenableFuture r8 = r8.updateData(r4)
            r0.L$0 = r7
            r0.label = r3
            java.lang.Object r8 = kotlinx.coroutines.guava.ListenableFutureKt.await(r8, r0)
            if (r8 != r1) goto L6f
            return r1
        L6f:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            int r7 = r7.size()
            r8.append(r7)
            java.lang.String r7 = " records updated."
            r8.append(r7)
            java.lang.String r7 = r8.toString()
            java.lang.String r8 = "HealthConnectClient"
            androidx.health.platform.client.impl.logger.Logger.debug(r8, r7)
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.health.connect.client.impl.HealthConnectClientImpl.updateRecords(java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public /* synthetic */ HealthConnectClientImpl(HealthDataAsyncClient healthDataAsyncClient, List list, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i2 & 2) != 0) {
            List listCreateListBuilder = CollectionsKt.createListBuilder();
            Map<KClass<? extends Record>, String> rECORD_TYPE_TO_PERMISSION$connect_client_release = HealthPermission.INSTANCE.getRECORD_TYPE_TO_PERMISSION$connect_client_release();
            ArrayList arrayList = new ArrayList();
            for (Map.Entry<KClass<? extends Record>, String> entry : rECORD_TYPE_TO_PERMISSION$connect_client_release.entrySet()) {
                CollectionsKt.addAll(arrayList, CollectionsKt.listOf((Object[]) new String[]{HealthPermission.WRITE_PERMISSION_PREFIX + entry.getValue(), HealthPermission.READ_PERMISSION_PREFIX + entry.getValue()}));
            }
            listCreateListBuilder.addAll(arrayList);
            listCreateListBuilder.add(HealthPermission.PERMISSION_WRITE_EXERCISE_ROUTE);
            listCreateListBuilder.add(HealthPermission.PERMISSION_READ_HEALTH_DATA_IN_BACKGROUND);
            list = CollectionsKt.build(listCreateListBuilder);
        }
        this(healthDataAsyncClient, list);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.health.connect.client.HealthConnectClient
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object deleteRecords(@org.jetbrains.annotations.NotNull kotlin.reflect.KClass<? extends androidx.health.connect.client.records.Record> r5, @org.jetbrains.annotations.NotNull androidx.health.connect.client.time.TimeRangeFilter r6, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super kotlin.Unit> r7) throws java.lang.Throwable {
        /*
            r4 = this;
            boolean r0 = r7 instanceof androidx.health.connect.client.impl.HealthConnectClientImpl.AnonymousClass2
            if (r0 == 0) goto L13
            r0 = r7
            androidx.health.connect.client.impl.HealthConnectClientImpl$deleteRecords$2 r0 = (androidx.health.connect.client.impl.HealthConnectClientImpl.AnonymousClass2) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.health.connect.client.impl.HealthConnectClientImpl$deleteRecords$2 r0 = new androidx.health.connect.client.impl.HealthConnectClientImpl$deleteRecords$2
            r0.<init>(r7)
        L18:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L31
            if (r2 != r3) goto L29
            kotlin.ResultKt.throwOnFailure(r7)
            goto L47
        L29:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L31:
            kotlin.ResultKt.throwOnFailure(r7)
            androidx.health.platform.client.HealthDataAsyncClient r7 = r4.delegate
            androidx.health.platform.client.proto.RequestProto$DeleteDataRangeRequest r5 = androidx.health.connect.client.impl.converters.request.DeleteDataRangeRequestToProtoKt.toDeleteDataRangeRequestProto(r5, r6)
            com.google.common.util.concurrent.ListenableFuture r5 = r7.deleteDataRange(r5)
            r0.label = r3
            java.lang.Object r5 = kotlinx.coroutines.guava.ListenableFutureKt.await(r5, r0)
            if (r5 != r1) goto L47
            return r1
        L47:
            java.lang.String r5 = "HealthConnectClient"
            java.lang.String r6 = "Records deletion successful."
            androidx.health.platform.client.impl.logger.Logger.debug(r5, r6)
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.health.connect.client.impl.HealthConnectClientImpl.deleteRecords(kotlin.reflect.KClass, androidx.health.connect.client.time.TimeRangeFilter, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
