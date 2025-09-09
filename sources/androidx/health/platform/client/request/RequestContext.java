package androidx.health.platform.client.request;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.health.platform.client.impl.data.ProtoParcelable;
import androidx.health.platform.client.impl.data.SharedMemory27Impl;
import androidx.health.platform.client.proto.InvalidProtocolBufferException;
import androidx.health.platform.client.proto.RequestProto;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0010\u0018\u0000 \u00182\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0018B'\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\rR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR!\u0010\u000f\u001a\u00020\u00028VX\u0096\u0084\u0002¢\u0006\u0012\n\u0004\b\u0014\u0010\u0015\u0012\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u0006\u0019"}, d2 = {"Landroidx/health/platform/client/request/RequestContext;", "Landroidx/health/platform/client/impl/data/ProtoParcelable;", "Landroidx/health/platform/client/proto/RequestProto$RequestContext;", "callingPackage", "", "sdkVersion", "", "permissionToken", "isInForeground", "", "(Ljava/lang/String;ILjava/lang/String;Z)V", "getCallingPackage", "()Ljava/lang/String;", "()Z", "getPermissionToken", "proto", "getProto$annotations", "()V", "getProto", "()Landroidx/health/platform/client/proto/RequestProto$RequestContext;", "proto$delegate", "Lkotlin/Lazy;", "getSdkVersion", "()I", "Companion", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nRequestContext.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RequestContext.kt\nandroidx/health/platform/client/request/RequestContext\n+ 2 ProtoParcelable.kt\nandroidx/health/platform/client/impl/data/ProtoParcelable$Companion\n*L\n1#1,54:1\n72#2:55\n*S KotlinDebug\n*F\n+ 1 RequestContext.kt\nandroidx/health/platform/client/request/RequestContext\n*L\n47#1:55\n*E\n"})
/* loaded from: classes.dex */
public final class RequestContext extends ProtoParcelable<RequestProto.RequestContext> {

    @NotNull
    private final String callingPackage;
    private final boolean isInForeground;

    @Nullable
    private final String permissionToken;

    /* renamed from: proto$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy proto;
    private final int sdkVersion;

    @JvmField
    @NotNull
    public static final Parcelable.Creator<RequestContext> CREATOR = new Parcelable.Creator<RequestContext>() { // from class: androidx.health.platform.client.request.RequestContext$special$$inlined$newCreator$connect_client_release$1
        /* JADX WARN: Can't rename method to resolve collision */
        /* JADX WARN: Type inference failed for: r5v7, types: [androidx.health.platform.client.impl.data.ProtoParcelable, androidx.health.platform.client.request.RequestContext] */
        @Override // android.os.Parcelable.Creator
        @SuppressLint({"NewApi"})
        @Nullable
        public RequestContext createFromParcel(@NotNull Parcel source) throws InvalidProtocolBufferException {
            Intrinsics.checkNotNullParameter(source, "source");
            int i2 = source.readInt();
            if (i2 != 0) {
                if (i2 == 1) {
                    return (ProtoParcelable) SharedMemory27Impl.INSTANCE.parseParcelUsingSharedMemory(source, new Function1<byte[], RequestContext>() { // from class: androidx.health.platform.client.request.RequestContext$special$$inlined$newCreator$connect_client_release$1.1
                        @Override // kotlin.jvm.functions.Function1
                        @NotNull
                        public final RequestContext invoke(@NotNull byte[] it) throws InvalidProtocolBufferException {
                            Intrinsics.checkNotNullParameter(it, "it");
                            RequestProto.RequestContext from = RequestProto.RequestContext.parseFrom(it);
                            String callingPackage = from.getCallingPackage();
                            Intrinsics.checkNotNullExpressionValue(callingPackage, "callingPackage");
                            return new RequestContext(callingPackage, from.getSdkVersion(), from.getPermissionToken(), from.getIsInForeground());
                        }
                    });
                }
                throw new IllegalArgumentException("Unknown storage: " + i2);
            }
            byte[] bArrCreateByteArray = source.createByteArray();
            if (bArrCreateByteArray == null) {
                return null;
            }
            RequestProto.RequestContext from = RequestProto.RequestContext.parseFrom(bArrCreateByteArray);
            String callingPackage = from.getCallingPackage();
            Intrinsics.checkNotNullExpressionValue(callingPackage, "callingPackage");
            return new RequestContext(callingPackage, from.getSdkVersion(), from.getPermissionToken(), from.getIsInForeground());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NotNull
        public RequestContext[] newArray(int size) {
            return new RequestContext[size];
        }
    };

    public RequestContext(@NotNull String callingPackage, int i2, @Nullable String str, boolean z2) {
        Intrinsics.checkNotNullParameter(callingPackage, "callingPackage");
        this.callingPackage = callingPackage;
        this.sdkVersion = i2;
        this.permissionToken = str;
        this.isInForeground = z2;
        this.proto = LazyKt.lazy(new Function0<RequestProto.RequestContext>() { // from class: androidx.health.platform.client.request.RequestContext$proto$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final RequestProto.RequestContext invoke() {
                RequestContext requestContext = this.this$0;
                RequestProto.RequestContext.Builder sdkVersion = RequestProto.RequestContext.newBuilder().setCallingPackage(requestContext.getCallingPackage()).setSdkVersion(requestContext.getSdkVersion());
                String permissionToken = requestContext.getPermissionToken();
                if (permissionToken != null) {
                    sdkVersion.setPermissionToken(permissionToken);
                }
                return sdkVersion.setIsInForeground(requestContext.getIsInForeground()).build();
            }
        });
    }

    public static /* synthetic */ void getProto$annotations() {
    }

    @NotNull
    public final String getCallingPackage() {
        return this.callingPackage;
    }

    @Nullable
    public final String getPermissionToken() {
        return this.permissionToken;
    }

    public final int getSdkVersion() {
        return this.sdkVersion;
    }

    /* renamed from: isInForeground, reason: from getter */
    public final boolean getIsInForeground() {
        return this.isInForeground;
    }

    @Override // androidx.health.platform.client.impl.data.ProtoData
    @NotNull
    public RequestProto.RequestContext getProto() {
        Object value = this.proto.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-proto>(...)");
        return (RequestProto.RequestContext) value;
    }
}
