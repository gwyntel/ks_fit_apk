package androidx.health.platform.client.response;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.health.platform.client.impl.data.ProtoParcelable;
import androidx.health.platform.client.impl.data.SharedMemory27Impl;
import androidx.health.platform.client.proto.InvalidProtocolBufferException;
import androidx.health.platform.client.proto.ResponseProto;
import androidx.health.platform.client.response.InsertDataResponse;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\b\u0018\u0000 \f2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\fB\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\r"}, d2 = {"Landroidx/health/platform/client/response/InsertDataResponse;", "Landroidx/health/platform/client/impl/data/ProtoParcelable;", "Landroidx/health/platform/client/proto/ResponseProto$InsertDataResponse;", "dataPointUids", "", "", "(Ljava/util/List;)V", "getDataPointUids", "()Ljava/util/List;", "proto", "getProto", "()Landroidx/health/platform/client/proto/ResponseProto$InsertDataResponse;", "Companion", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nInsertDataResponse.kt\nKotlin\n*S Kotlin\n*F\n+ 1 InsertDataResponse.kt\nandroidx/health/platform/client/response/InsertDataResponse\n+ 2 ProtoParcelable.kt\nandroidx/health/platform/client/impl/data/ProtoParcelable$Companion\n*L\n1#1,49:1\n72#2:50\n*S KotlinDebug\n*F\n+ 1 InsertDataResponse.kt\nandroidx/health/platform/client/response/InsertDataResponse\n*L\n37#1:50\n*E\n"})
/* loaded from: classes.dex */
public final class InsertDataResponse extends ProtoParcelable<ResponseProto.InsertDataResponse> {

    @NotNull
    private final List<String> dataPointUids;

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @JvmField
    @NotNull
    public static final Parcelable.Creator<InsertDataResponse> CREATOR = new Parcelable.Creator<InsertDataResponse>() { // from class: androidx.health.platform.client.response.InsertDataResponse$special$$inlined$newCreator$connect_client_release$1
        /* JADX WARN: Can't rename method to resolve collision */
        /* JADX WARN: Type inference failed for: r4v7, types: [androidx.health.platform.client.impl.data.ProtoParcelable, androidx.health.platform.client.response.InsertDataResponse] */
        @Override // android.os.Parcelable.Creator
        @SuppressLint({"NewApi"})
        @Nullable
        public InsertDataResponse createFromParcel(@NotNull Parcel source) throws InvalidProtocolBufferException {
            Intrinsics.checkNotNullParameter(source, "source");
            int i2 = source.readInt();
            if (i2 != 0) {
                if (i2 == 1) {
                    return (ProtoParcelable) SharedMemory27Impl.INSTANCE.parseParcelUsingSharedMemory(source, new Function1<byte[], InsertDataResponse>() { // from class: androidx.health.platform.client.response.InsertDataResponse$special$$inlined$newCreator$connect_client_release$1.1
                        @Override // kotlin.jvm.functions.Function1
                        @NotNull
                        public final InsertDataResponse invoke(@NotNull byte[] it) throws InvalidProtocolBufferException {
                            Intrinsics.checkNotNullParameter(it, "it");
                            ResponseProto.InsertDataResponse proto = ResponseProto.InsertDataResponse.parseFrom(it);
                            InsertDataResponse.Companion companion = InsertDataResponse.INSTANCE;
                            Intrinsics.checkNotNullExpressionValue(proto, "proto");
                            return companion.fromProto$connect_client_release(proto);
                        }
                    });
                }
                throw new IllegalArgumentException("Unknown storage: " + i2);
            }
            byte[] bArrCreateByteArray = source.createByteArray();
            if (bArrCreateByteArray == null) {
                return null;
            }
            ResponseProto.InsertDataResponse proto = ResponseProto.InsertDataResponse.parseFrom(bArrCreateByteArray);
            InsertDataResponse.Companion companion = InsertDataResponse.INSTANCE;
            Intrinsics.checkNotNullExpressionValue(proto, "proto");
            return companion.fromProto$connect_client_release(proto);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NotNull
        public InsertDataResponse[] newArray(int size) {
            return new InsertDataResponse[size];
        }
    };

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0015\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0000¢\u0006\u0002\b\tR\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Landroidx/health/platform/client/response/InsertDataResponse$Companion;", "", "()V", "CREATOR", "Landroid/os/Parcelable$Creator;", "Landroidx/health/platform/client/response/InsertDataResponse;", "fromProto", "proto", "Landroidx/health/platform/client/proto/ResponseProto$InsertDataResponse;", "fromProto$connect_client_release", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final InsertDataResponse fromProto$connect_client_release(@NotNull ResponseProto.InsertDataResponse proto) {
            Intrinsics.checkNotNullParameter(proto, "proto");
            List<String> dataPointUidList = proto.getDataPointUidList();
            Intrinsics.checkNotNullExpressionValue(dataPointUidList, "proto.dataPointUidList");
            return new InsertDataResponse(dataPointUidList);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public InsertDataResponse(@NotNull List<String> dataPointUids) {
        Intrinsics.checkNotNullParameter(dataPointUids, "dataPointUids");
        this.dataPointUids = dataPointUids;
    }

    @NotNull
    public final List<String> getDataPointUids() {
        return this.dataPointUids;
    }

    @Override // androidx.health.platform.client.impl.data.ProtoData
    @NotNull
    public ResponseProto.InsertDataResponse getProto() {
        ResponseProto.InsertDataResponse insertDataResponseBuild = ResponseProto.InsertDataResponse.newBuilder().addAllDataPointUid(this.dataPointUids).build();
        Intrinsics.checkNotNullExpressionValue(insertDataResponseBuild, "newBuilder()\n           …\n                .build()");
        return insertDataResponseBuild;
    }
}
