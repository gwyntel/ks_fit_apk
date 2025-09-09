package androidx.health.platform.client.response;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.health.platform.client.impl.data.ProtoParcelable;
import androidx.health.platform.client.impl.data.SharedMemory27Impl;
import androidx.health.platform.client.proto.InvalidProtocolBufferException;
import androidx.health.platform.client.proto.ResponseProto;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u00072\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0007B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0003\u001a\u00020\u0002X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"Landroidx/health/platform/client/response/ReadExerciseRouteResponse;", "Landroidx/health/platform/client/impl/data/ProtoParcelable;", "Landroidx/health/platform/client/proto/ResponseProto$ReadExerciseRouteResponse;", "proto", "(Landroidx/health/platform/client/proto/ResponseProto$ReadExerciseRouteResponse;)V", "getProto", "()Landroidx/health/platform/client/proto/ResponseProto$ReadExerciseRouteResponse;", "Companion", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nReadExerciseRouteResponse.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReadExerciseRouteResponse.kt\nandroidx/health/platform/client/response/ReadExerciseRouteResponse\n+ 2 ProtoParcelable.kt\nandroidx/health/platform/client/impl/data/ProtoParcelable$Companion\n*L\n1#1,35:1\n72#2:36\n*S KotlinDebug\n*F\n+ 1 ReadExerciseRouteResponse.kt\nandroidx/health/platform/client/response/ReadExerciseRouteResponse\n*L\n29#1:36\n*E\n"})
/* loaded from: classes.dex */
public final class ReadExerciseRouteResponse extends ProtoParcelable<ResponseProto.ReadExerciseRouteResponse> {

    @NotNull
    private final ResponseProto.ReadExerciseRouteResponse proto;

    @JvmField
    @NotNull
    public static final Parcelable.Creator<ReadExerciseRouteResponse> CREATOR = new Parcelable.Creator<ReadExerciseRouteResponse>() { // from class: androidx.health.platform.client.response.ReadExerciseRouteResponse$special$$inlined$newCreator$connect_client_release$1
        /* JADX WARN: Can't rename method to resolve collision */
        /* JADX WARN: Type inference failed for: r4v6, types: [androidx.health.platform.client.impl.data.ProtoParcelable, androidx.health.platform.client.response.ReadExerciseRouteResponse] */
        @Override // android.os.Parcelable.Creator
        @SuppressLint({"NewApi"})
        @Nullable
        public ReadExerciseRouteResponse createFromParcel(@NotNull Parcel source) throws InvalidProtocolBufferException {
            Intrinsics.checkNotNullParameter(source, "source");
            int i2 = source.readInt();
            if (i2 != 0) {
                if (i2 == 1) {
                    return (ProtoParcelable) SharedMemory27Impl.INSTANCE.parseParcelUsingSharedMemory(source, new Function1<byte[], ReadExerciseRouteResponse>() { // from class: androidx.health.platform.client.response.ReadExerciseRouteResponse$special$$inlined$newCreator$connect_client_release$1.1
                        @Override // kotlin.jvm.functions.Function1
                        @NotNull
                        public final ReadExerciseRouteResponse invoke(@NotNull byte[] it) throws InvalidProtocolBufferException {
                            Intrinsics.checkNotNullParameter(it, "it");
                            ResponseProto.ReadExerciseRouteResponse proto = ResponseProto.ReadExerciseRouteResponse.parseFrom(it);
                            Intrinsics.checkNotNullExpressionValue(proto, "proto");
                            return new ReadExerciseRouteResponse(proto);
                        }
                    });
                }
                throw new IllegalArgumentException("Unknown storage: " + i2);
            }
            byte[] bArrCreateByteArray = source.createByteArray();
            if (bArrCreateByteArray == null) {
                return null;
            }
            ResponseProto.ReadExerciseRouteResponse proto = ResponseProto.ReadExerciseRouteResponse.parseFrom(bArrCreateByteArray);
            Intrinsics.checkNotNullExpressionValue(proto, "proto");
            return new ReadExerciseRouteResponse(proto);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NotNull
        public ReadExerciseRouteResponse[] newArray(int size) {
            return new ReadExerciseRouteResponse[size];
        }
    };

    public ReadExerciseRouteResponse(@NotNull ResponseProto.ReadExerciseRouteResponse proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        this.proto = proto;
    }

    @Override // androidx.health.platform.client.impl.data.ProtoData
    @NotNull
    public ResponseProto.ReadExerciseRouteResponse getProto() {
        return this.proto;
    }
}
