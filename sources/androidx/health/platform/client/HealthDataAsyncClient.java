package androidx.health.platform.client;

import androidx.health.platform.client.proto.DataProto;
import androidx.health.platform.client.proto.PermissionProto;
import androidx.health.platform.client.proto.RequestProto;
import androidx.health.platform.client.proto.ResponseProto;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ShareConstants;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J*\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00032\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH&J\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\b0\u00032\u0006\u0010\u000e\u001a\u00020\u000fH&J\"\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110\u00032\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H&J\u0016\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u00032\u0006\u0010\u0005\u001a\u00020\u0016H&J\u0016\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u00032\u0006\u0010\u0005\u001a\u00020\u0019H&J\"\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110\u00032\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H&J\"\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\n0\u00032\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u001d0\nH&J\u0016\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00032\u0006\u0010\u000e\u001a\u00020\u001fH&J\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u00032\u0006\u0010\u000e\u001a\u00020\"H&J\u0016\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u00032\u0006\u0010\u0005\u001a\u00020%H&J\u000e\u0010&\u001a\b\u0012\u0004\u0012\u00020\b0\u0003H&J\u0016\u0010'\u001a\b\u0012\u0004\u0012\u00020$0\u00032\u0006\u0010\u0005\u001a\u00020(H&J\u001c\u0010)\u001a\b\u0012\u0004\u0012\u00020\b0\u00032\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u001d0\nH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006*À\u0006\u0001"}, d2 = {"Landroidx/health/platform/client/HealthDataAsyncClient;", "", "aggregate", "Lcom/google/common/util/concurrent/ListenableFuture;", "Landroidx/health/platform/client/proto/ResponseProto$AggregateDataResponse;", ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID, "Landroidx/health/platform/client/proto/RequestProto$AggregateDataRequest;", "deleteData", "", "uidsCollection", "", "Landroidx/health/platform/client/proto/RequestProto$DataTypeIdPair;", "clientIdsCollection", "deleteDataRange", "dataCollection", "Landroidx/health/platform/client/proto/RequestProto$DeleteDataRangeRequest;", "filterGrantedPermissions", "", "Landroidx/health/platform/client/proto/PermissionProto$Permission;", NativeProtocol.RESULT_ARGS_PERMISSIONS, "getChanges", "Landroidx/health/platform/client/proto/ResponseProto$GetChangesResponse;", "Landroidx/health/platform/client/proto/RequestProto$GetChangesRequest;", "getChangesToken", "Landroidx/health/platform/client/proto/ResponseProto$GetChangesTokenResponse;", "Landroidx/health/platform/client/proto/RequestProto$GetChangesTokenRequest;", "getGrantedPermissions", "insertData", "", "Landroidx/health/platform/client/proto/DataProto$DataPoint;", "readData", "Landroidx/health/platform/client/proto/RequestProto$ReadDataRequest;", "readDataRange", "Landroidx/health/platform/client/proto/ResponseProto$ReadDataRangeResponse;", "Landroidx/health/platform/client/proto/RequestProto$ReadDataRangeRequest;", "registerForDataNotifications", "Ljava/lang/Void;", "Landroidx/health/platform/client/proto/RequestProto$RegisterForDataNotificationsRequest;", "revokeAllPermissions", "unregisterFromDataNotifications", "Landroidx/health/platform/client/proto/RequestProto$UnregisterFromDataNotificationsRequest;", "updateData", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public interface HealthDataAsyncClient {
    @NotNull
    ListenableFuture<ResponseProto.AggregateDataResponse> aggregate(@NotNull RequestProto.AggregateDataRequest request);

    @NotNull
    ListenableFuture<Unit> deleteData(@NotNull List<RequestProto.DataTypeIdPair> uidsCollection, @NotNull List<RequestProto.DataTypeIdPair> clientIdsCollection);

    @NotNull
    ListenableFuture<Unit> deleteDataRange(@NotNull RequestProto.DeleteDataRangeRequest dataCollection);

    @NotNull
    ListenableFuture<Set<PermissionProto.Permission>> filterGrantedPermissions(@NotNull Set<PermissionProto.Permission> permissions);

    @NotNull
    ListenableFuture<ResponseProto.GetChangesResponse> getChanges(@NotNull RequestProto.GetChangesRequest request);

    @NotNull
    ListenableFuture<ResponseProto.GetChangesTokenResponse> getChangesToken(@NotNull RequestProto.GetChangesTokenRequest request);

    @NotNull
    ListenableFuture<Set<PermissionProto.Permission>> getGrantedPermissions(@NotNull Set<PermissionProto.Permission> permissions);

    @NotNull
    ListenableFuture<List<String>> insertData(@NotNull List<DataProto.DataPoint> dataCollection);

    @NotNull
    ListenableFuture<DataProto.DataPoint> readData(@NotNull RequestProto.ReadDataRequest dataCollection);

    @NotNull
    ListenableFuture<ResponseProto.ReadDataRangeResponse> readDataRange(@NotNull RequestProto.ReadDataRangeRequest dataCollection);

    @NotNull
    ListenableFuture<Void> registerForDataNotifications(@NotNull RequestProto.RegisterForDataNotificationsRequest request);

    @NotNull
    ListenableFuture<Unit> revokeAllPermissions();

    @NotNull
    ListenableFuture<Void> unregisterFromDataNotifications(@NotNull RequestProto.UnregisterFromDataNotificationsRequest request);

    @NotNull
    ListenableFuture<Unit> updateData(@NotNull List<DataProto.DataPoint> dataCollection);
}
