package androidx.health.platform.client.error;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.RestrictTo;
import androidx.health.platform.client.impl.data.ProtoParcelable;
import androidx.health.platform.client.impl.data.SharedMemory27Impl;
import androidx.health.platform.client.proto.ErrorProto;
import androidx.health.platform.client.proto.InvalidProtocolBufferException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\b\u0007\u0018\u0000 \u00112\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0011B\u001b\u0012\b\b\u0001\u0010\u0003\u001a\u00020\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001b\u0010\f\u001a\u00020\u00028VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000e¨\u0006\u0012"}, d2 = {"Landroidx/health/platform/client/error/ErrorStatus;", "Landroidx/health/platform/client/impl/data/ProtoParcelable;", "Landroidx/health/platform/client/proto/ErrorProto$ErrorStatus;", "errorCode", "", "errorMessage", "", "(ILjava/lang/String;)V", "getErrorCode", "()I", "getErrorMessage", "()Ljava/lang/String;", "proto", "getProto", "()Landroidx/health/platform/client/proto/ErrorProto$ErrorStatus;", "proto$delegate", "Lkotlin/Lazy;", "Companion", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@RestrictTo({RestrictTo.Scope.LIBRARY})
@SourceDebugExtension({"SMAP\nErrorStatus.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ErrorStatus.kt\nandroidx/health/platform/client/error/ErrorStatus\n+ 2 ProtoParcelable.kt\nandroidx/health/platform/client/impl/data/ProtoParcelable$Companion\n*L\n1#1,76:1\n72#2:77\n*S KotlinDebug\n*F\n+ 1 ErrorStatus.kt\nandroidx/health/platform/client/error/ErrorStatus\n*L\n67#1:77\n*E\n"})
/* loaded from: classes.dex */
public final class ErrorStatus extends ProtoParcelable<ErrorProto.ErrorStatus> {
    private final int errorCode;

    @Nullable
    private final String errorMessage;

    /* renamed from: proto$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy proto;

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @JvmField
    @NotNull
    public static final Parcelable.Creator<ErrorStatus> CREATOR = new Parcelable.Creator<ErrorStatus>() { // from class: androidx.health.platform.client.error.ErrorStatus$special$$inlined$newCreator$connect_client_release$1
        /* JADX WARN: Can't rename method to resolve collision */
        /* JADX WARN: Type inference failed for: r5v6, types: [androidx.health.platform.client.error.ErrorStatus, androidx.health.platform.client.impl.data.ProtoParcelable] */
        @Override // android.os.Parcelable.Creator
        @SuppressLint({"NewApi"})
        @Nullable
        public ErrorStatus createFromParcel(@NotNull Parcel source) throws InvalidProtocolBufferException {
            Intrinsics.checkNotNullParameter(source, "source");
            int i2 = source.readInt();
            if (i2 == 0) {
                byte[] bArrCreateByteArray = source.createByteArray();
                if (bArrCreateByteArray == null) {
                    return null;
                }
                ErrorProto.ErrorStatus from = ErrorProto.ErrorStatus.parseFrom(bArrCreateByteArray);
                return ErrorStatus.INSTANCE.create(from.getCode(), from.hasMessage() ? from.getMessage() : null);
            }
            if (i2 == 1) {
                return (ProtoParcelable) SharedMemory27Impl.INSTANCE.parseParcelUsingSharedMemory(source, new Function1<byte[], ErrorStatus>() { // from class: androidx.health.platform.client.error.ErrorStatus$special$$inlined$newCreator$connect_client_release$1.1
                    @Override // kotlin.jvm.functions.Function1
                    @NotNull
                    public final ErrorStatus invoke(@NotNull byte[] it) throws InvalidProtocolBufferException {
                        Intrinsics.checkNotNullParameter(it, "it");
                        ErrorProto.ErrorStatus from2 = ErrorProto.ErrorStatus.parseFrom(it);
                        return ErrorStatus.INSTANCE.create(from2.getCode(), from2.hasMessage() ? from2.getMessage() : null);
                    }
                });
            }
            throw new IllegalArgumentException("Unknown storage: " + i2);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NotNull
        public ErrorStatus[] newArray(int size) {
            return new ErrorStatus[size];
        }
    };

    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\nH\u0007J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Landroidx/health/platform/client/error/ErrorStatus$Companion;", "", "()V", "CREATOR", "Landroid/os/Parcelable$Creator;", "Landroidx/health/platform/client/error/ErrorStatus;", "create", "errorCode", "", "errorMessage", "", "safeErrorCode", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nErrorStatus.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ErrorStatus.kt\nandroidx/health/platform/client/error/ErrorStatus$Companion\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,76:1\n3792#2:77\n4307#2,2:78\n1549#3:80\n1620#3,3:81\n288#3,2:84\n*S KotlinDebug\n*F\n+ 1 ErrorStatus.kt\nandroidx/health/platform/client/error/ErrorStatus$Companion\n*L\n54#1:77\n54#1:78,2\n55#1:80\n55#1:81,3\n62#1:84,2\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        public static /* synthetic */ ErrorStatus create$default(Companion companion, int i2, String str, int i3, Object obj) {
            if ((i3 & 2) != 0) {
                str = null;
            }
            return companion.create(i2, str);
        }

        @JvmStatic
        @JvmOverloads
        @NotNull
        public final ErrorStatus create(int i2) {
            return create$default(this, i2, null, 2, null);
        }

        @ErrorCode
        public final int safeErrorCode(int errorCode) throws IllegalAccessException, IllegalArgumentException {
            Object obj;
            Field[] declaredFields = ErrorCode.class.getDeclaredFields();
            Intrinsics.checkNotNullExpressionValue(declaredFields, "ErrorCode::class\n       …          .declaredFields");
            ArrayList arrayList = new ArrayList();
            for (Field field : declaredFields) {
                if (field.getType().isAssignableFrom(Integer.TYPE)) {
                    arrayList.add(field);
                }
            }
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList, 10));
            Iterator it = arrayList.iterator();
            while (true) {
                int iIntValue = 10007;
                obj = null;
                if (!it.hasNext()) {
                    break;
                }
                try {
                    Object obj2 = ((Field) it.next()).get(null);
                    Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                    iIntValue = ((Integer) obj2).intValue();
                } catch (IllegalAccessException unused) {
                }
                arrayList2.add(Integer.valueOf(iIntValue));
            }
            Iterator it2 = arrayList2.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Object next = it2.next();
                if (((Number) next).intValue() == errorCode) {
                    obj = next;
                    break;
                }
            }
            Integer num = (Integer) obj;
            if (num != null) {
                return num.intValue();
            }
            return 10007;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        @JvmOverloads
        @NotNull
        public final ErrorStatus create(int errorCode, @Nullable String errorMessage) {
            return new ErrorStatus(safeErrorCode(errorCode), errorMessage);
        }
    }

    public /* synthetic */ ErrorStatus(int i2, String str, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i2, (i3 & 2) != 0 ? null : str);
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final ErrorStatus create(int i2) {
        return INSTANCE.create(i2);
    }

    public final int getErrorCode() {
        return this.errorCode;
    }

    @Nullable
    public final String getErrorMessage() {
        return this.errorMessage;
    }

    public ErrorStatus(@ErrorCode int i2, @Nullable String str) {
        this.errorCode = i2;
        this.errorMessage = str;
        this.proto = LazyKt.lazy(new Function0<ErrorProto.ErrorStatus>() { // from class: androidx.health.platform.client.error.ErrorStatus$proto$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ErrorProto.ErrorStatus invoke() {
                ErrorProto.ErrorStatus.Builder builder = ErrorProto.ErrorStatus.newBuilder().setCode(this.this$0.getErrorCode());
                String errorMessage = this.this$0.getErrorMessage();
                if (errorMessage != null) {
                    Intrinsics.checkNotNullExpressionValue(builder, "builder");
                    builder.setMessage(errorMessage);
                }
                return builder.build();
            }
        });
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final ErrorStatus create(int i2, @Nullable String str) {
        return INSTANCE.create(i2, str);
    }

    @Override // androidx.health.platform.client.impl.data.ProtoData
    @NotNull
    public ErrorProto.ErrorStatus getProto() {
        Object value = this.proto.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-proto>(...)");
        return (ErrorProto.ErrorStatus) value;
    }
}
