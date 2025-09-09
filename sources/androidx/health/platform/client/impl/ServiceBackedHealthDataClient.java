package androidx.health.platform.client.impl;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.annotation.RestrictTo;
import androidx.health.platform.client.HealthDataAsyncClient;
import androidx.health.platform.client.impl.internal.ProviderConnectionManager;
import androidx.health.platform.client.impl.ipc.Client;
import androidx.health.platform.client.impl.ipc.ClientConfiguration;
import androidx.health.platform.client.impl.ipc.RemoteFutureOperation;
import androidx.health.platform.client.impl.ipc.RemoteOperation;
import androidx.health.platform.client.impl.ipc.internal.ConnectionManager;
import androidx.health.platform.client.impl.permission.foregroundstate.ForegroundStateChecker;
import androidx.health.platform.client.impl.permission.token.PermissionTokenManager;
import androidx.health.platform.client.permission.Permission;
import androidx.health.platform.client.proto.DataProto;
import androidx.health.platform.client.proto.PermissionProto;
import androidx.health.platform.client.proto.RequestProto;
import androidx.health.platform.client.proto.ResponseProto;
import androidx.health.platform.client.request.AggregateDataRequest;
import androidx.health.platform.client.request.DeleteDataRangeRequest;
import androidx.health.platform.client.request.DeleteDataRequest;
import androidx.health.platform.client.request.GetChangesRequest;
import androidx.health.platform.client.request.GetChangesTokenRequest;
import androidx.health.platform.client.request.ReadDataRangeRequest;
import androidx.health.platform.client.request.ReadDataRequest;
import androidx.health.platform.client.request.RegisterForDataNotificationsRequest;
import androidx.health.platform.client.request.RequestContext;
import androidx.health.platform.client.request.UnregisterFromDataNotificationsRequest;
import androidx.health.platform.client.request.UpsertDataRequest;
import androidx.health.platform.client.service.IHealthDataService;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ShareConstants;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000ª\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0017\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bB\u001d\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0016\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J*\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u00102\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017H\u0016J\u0016\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00150\u00102\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\"\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0\u001e0\u00102\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eH\u0016J\u0016\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u00102\u0006\u0010\u0012\u001a\u00020#H\u0016J\u0016\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\u00102\u0006\u0010\u0012\u001a\u00020&H\u0016J\"\u0010'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0\u001e0\u00102\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eH\u0016J\b\u0010(\u001a\u00020)H\u0002J\"\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00170\u00102\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020+0\u0017H\u0016J\u0016\u0010,\u001a\b\u0012\u0004\u0012\u00020+0\u00102\u0006\u0010\u001b\u001a\u00020-H\u0016J\u0016\u0010.\u001a\b\u0012\u0004\u0012\u00020/0\u00102\u0006\u0010\u001b\u001a\u000200H\u0016J\u0016\u00101\u001a\b\u0012\u0004\u0012\u0002020\u00102\u0006\u0010\u0012\u001a\u000203H\u0016J\u000e\u00104\u001a\b\u0012\u0004\u0012\u00020\u00150\u0010H\u0016J\u0016\u00105\u001a\b\u0012\u0004\u0012\u0002020\u00102\u0006\u0010\u0012\u001a\u000206H\u0016J\u001c\u00107\u001a\b\u0012\u0004\u0012\u00020\u00150\u00102\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020+0\u0017H\u0016R\u0016\u0010\f\u001a\n \u000e*\u0004\u0018\u00010\r0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00068"}, d2 = {"Landroidx/health/platform/client/impl/ServiceBackedHealthDataClient;", "Landroidx/health/platform/client/impl/ipc/Client;", "Landroidx/health/platform/client/service/IHealthDataService;", "Landroidx/health/platform/client/HealthDataAsyncClient;", com.umeng.analytics.pro.f.X, "Landroid/content/Context;", "clientConfiguration", "Landroidx/health/platform/client/impl/ipc/ClientConfiguration;", "(Landroid/content/Context;Landroidx/health/platform/client/impl/ipc/ClientConfiguration;)V", "connectionManager", "Landroidx/health/platform/client/impl/ipc/internal/ConnectionManager;", "(Landroid/content/Context;Landroidx/health/platform/client/impl/ipc/ClientConfiguration;Landroidx/health/platform/client/impl/ipc/internal/ConnectionManager;)V", "callingPackageName", "", "kotlin.jvm.PlatformType", "aggregate", "Lcom/google/common/util/concurrent/ListenableFuture;", "Landroidx/health/platform/client/proto/ResponseProto$AggregateDataResponse;", ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID, "Landroidx/health/platform/client/proto/RequestProto$AggregateDataRequest;", "deleteData", "", "uidsCollection", "", "Landroidx/health/platform/client/proto/RequestProto$DataTypeIdPair;", "clientIdsCollection", "deleteDataRange", "dataCollection", "Landroidx/health/platform/client/proto/RequestProto$DeleteDataRangeRequest;", "filterGrantedPermissions", "", "Landroidx/health/platform/client/proto/PermissionProto$Permission;", NativeProtocol.RESULT_ARGS_PERMISSIONS, "getChanges", "Landroidx/health/platform/client/proto/ResponseProto$GetChangesResponse;", "Landroidx/health/platform/client/proto/RequestProto$GetChangesRequest;", "getChangesToken", "Landroidx/health/platform/client/proto/ResponseProto$GetChangesTokenResponse;", "Landroidx/health/platform/client/proto/RequestProto$GetChangesTokenRequest;", "getGrantedPermissions", "getRequestContext", "Landroidx/health/platform/client/request/RequestContext;", "insertData", "Landroidx/health/platform/client/proto/DataProto$DataPoint;", "readData", "Landroidx/health/platform/client/proto/RequestProto$ReadDataRequest;", "readDataRange", "Landroidx/health/platform/client/proto/ResponseProto$ReadDataRangeResponse;", "Landroidx/health/platform/client/proto/RequestProto$ReadDataRangeRequest;", "registerForDataNotifications", "Ljava/lang/Void;", "Landroidx/health/platform/client/proto/RequestProto$RegisterForDataNotificationsRequest;", "revokeAllPermissions", "unregisterFromDataNotifications", "Landroidx/health/platform/client/proto/RequestProto$UnregisterFromDataNotificationsRequest;", "updateData", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@RestrictTo({RestrictTo.Scope.LIBRARY})
@SourceDebugExtension({"SMAP\nServiceBackedHealthDataClient.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ServiceBackedHealthDataClient.kt\nandroidx/health/platform/client/impl/ServiceBackedHealthDataClient\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,229:1\n1549#2:230\n1620#2,3:231\n1549#2:234\n1620#2,3:235\n*S KotlinDebug\n*F\n+ 1 ServiceBackedHealthDataClient.kt\nandroidx/health/platform/client/impl/ServiceBackedHealthDataClient\n*L\n87#1:230\n87#1:231,3\n99#1:234\n99#1:235,3\n*E\n"})
/* loaded from: classes.dex */
public final class ServiceBackedHealthDataClient extends Client<IHealthDataService> implements HealthDataAsyncClient {
    private final String callingPackageName;

    @NotNull
    private final Context context;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ServiceBackedHealthDataClient(@NotNull Context context, @NotNull ClientConfiguration clientConfiguration, @NotNull ConnectionManager connectionManager) {
        super(clientConfiguration, connectionManager, new Client.ServiceGetter() { // from class: androidx.health.platform.client.impl.a
            @Override // androidx.health.platform.client.impl.ipc.Client.ServiceGetter
            public final Object getService(IBinder iBinder) {
                return IHealthDataService.Stub.asInterface(iBinder);
            }
        }, new RemoteOperation() { // from class: androidx.health.platform.client.impl.h
            @Override // androidx.health.platform.client.impl.ipc.RemoteOperation
            public final Object execute(Object obj) {
                return Integer.valueOf(((IHealthDataService) obj).getApiVersion());
            }
        });
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(clientConfiguration, "clientConfiguration");
        Intrinsics.checkNotNullParameter(connectionManager, "connectionManager");
        this.context = context;
        this.callingPackageName = context.getPackageName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void aggregate$lambda$11(ServiceBackedHealthDataClient this$0, RequestProto.AggregateDataRequest request, IHealthDataService iHealthDataService, SettableFuture resultFuture) throws RemoteException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(request, "$request");
        RequestContext requestContext = this$0.getRequestContext();
        AggregateDataRequest aggregateDataRequest = new AggregateDataRequest(request);
        Intrinsics.checkNotNullExpressionValue(resultFuture, "resultFuture");
        iHealthDataService.aggregate(requestContext, aggregateDataRequest, new AggregateDataCallback(resultFuture));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void deleteData$lambda$7(ServiceBackedHealthDataClient this$0, DeleteDataRequest request, IHealthDataService iHealthDataService, SettableFuture resultFuture) throws RemoteException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(request, "$request");
        RequestContext requestContext = this$0.getRequestContext();
        Intrinsics.checkNotNullExpressionValue(resultFuture, "resultFuture");
        iHealthDataService.deleteData(requestContext, request, new DeleteDataCallback(resultFuture));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void deleteDataRange$lambda$8(ServiceBackedHealthDataClient this$0, DeleteDataRangeRequest request, IHealthDataService iHealthDataService, SettableFuture resultFuture) throws RemoteException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(request, "$request");
        RequestContext requestContext = this$0.getRequestContext();
        Intrinsics.checkNotNullExpressionValue(resultFuture, "resultFuture");
        iHealthDataService.deleteDataRange(requestContext, request, new DeleteDataRangeCallback(resultFuture));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void filterGrantedPermissions$lambda$3(ServiceBackedHealthDataClient this$0, Set permissions, IHealthDataService iHealthDataService, SettableFuture resultFuture) throws RemoteException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(permissions, "$permissions");
        RequestContext requestContext = this$0.getRequestContext();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(permissions, 10));
        Iterator it = permissions.iterator();
        while (it.hasNext()) {
            arrayList.add(new Permission((PermissionProto.Permission) it.next()));
        }
        List<Permission> list = CollectionsKt.toList(arrayList);
        Intrinsics.checkNotNullExpressionValue(resultFuture, "resultFuture");
        iHealthDataService.filterGrantedPermissions(requestContext, list, new FilterGrantedPermissionsCallback(resultFuture));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getChanges$lambda$13(ServiceBackedHealthDataClient this$0, RequestProto.GetChangesRequest request, IHealthDataService iHealthDataService, SettableFuture resultFuture) throws RemoteException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(request, "$request");
        RequestContext requestContext = this$0.getRequestContext();
        GetChangesRequest getChangesRequest = new GetChangesRequest(request);
        Intrinsics.checkNotNullExpressionValue(resultFuture, "resultFuture");
        iHealthDataService.getChanges(requestContext, getChangesRequest, new GetChangesCallback(resultFuture));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getChangesToken$lambda$12(ServiceBackedHealthDataClient this$0, RequestProto.GetChangesTokenRequest request, IHealthDataService iHealthDataService, SettableFuture resultFuture) throws RemoteException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(request, "$request");
        RequestContext requestContext = this$0.getRequestContext();
        GetChangesTokenRequest getChangesTokenRequest = new GetChangesTokenRequest(request);
        Intrinsics.checkNotNullExpressionValue(resultFuture, "resultFuture");
        iHealthDataService.getChangesToken(requestContext, getChangesTokenRequest, new GetChangesTokenCallback(resultFuture));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getGrantedPermissions$lambda$1(ServiceBackedHealthDataClient this$0, Set permissions, IHealthDataService iHealthDataService, SettableFuture resultFuture) throws RemoteException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(permissions, "$permissions");
        RequestContext requestContext = this$0.getRequestContext();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(permissions, 10));
        Iterator it = permissions.iterator();
        while (it.hasNext()) {
            arrayList.add(new Permission((PermissionProto.Permission) it.next()));
        }
        List<Permission> list = CollectionsKt.toList(arrayList);
        Intrinsics.checkNotNullExpressionValue(resultFuture, "resultFuture");
        iHealthDataService.getGrantedPermissions(requestContext, list, new GetGrantedPermissionsCallback(resultFuture));
    }

    private final RequestContext getRequestContext() {
        String callingPackageName = this.callingPackageName;
        Intrinsics.checkNotNullExpressionValue(callingPackageName, "callingPackageName");
        return new RequestContext(callingPackageName, 112, PermissionTokenManager.getCurrentToken(this.context), ForegroundStateChecker.isInForeground());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void insertData$lambda$5(ServiceBackedHealthDataClient this$0, UpsertDataRequest request, IHealthDataService iHealthDataService, SettableFuture resultFuture) throws RemoteException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(request, "$request");
        RequestContext requestContext = this$0.getRequestContext();
        Intrinsics.checkNotNullExpressionValue(resultFuture, "resultFuture");
        iHealthDataService.insertData(requestContext, request, new InsertDataCallback(resultFuture));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void readData$lambda$9(ServiceBackedHealthDataClient this$0, ReadDataRequest request, IHealthDataService iHealthDataService, SettableFuture resultFuture) throws RemoteException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(request, "$request");
        RequestContext requestContext = this$0.getRequestContext();
        Intrinsics.checkNotNullExpressionValue(resultFuture, "resultFuture");
        iHealthDataService.readData(requestContext, request, new ReadDataCallback(resultFuture));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void readDataRange$lambda$10(ServiceBackedHealthDataClient this$0, ReadDataRangeRequest request, IHealthDataService iHealthDataService, SettableFuture resultFuture) throws RemoteException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(request, "$request");
        RequestContext requestContext = this$0.getRequestContext();
        Intrinsics.checkNotNullExpressionValue(resultFuture, "resultFuture");
        iHealthDataService.readDataRange(requestContext, request, new ReadDataRangeCallback(resultFuture));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void registerForDataNotifications$lambda$14(ServiceBackedHealthDataClient this$0, RequestProto.RegisterForDataNotificationsRequest request, IHealthDataService iHealthDataService, SettableFuture resultFuture) throws RemoteException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(request, "$request");
        RequestContext requestContext = this$0.getRequestContext();
        RegisterForDataNotificationsRequest registerForDataNotificationsRequest = new RegisterForDataNotificationsRequest(request);
        Intrinsics.checkNotNullExpressionValue(resultFuture, "resultFuture");
        iHealthDataService.registerForDataNotifications(requestContext, registerForDataNotificationsRequest, new RegisterForDataNotificationsCallback(resultFuture));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void revokeAllPermissions$lambda$4(ServiceBackedHealthDataClient this$0, IHealthDataService iHealthDataService, SettableFuture resultFuture) throws RemoteException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        RequestContext requestContext = this$0.getRequestContext();
        Intrinsics.checkNotNullExpressionValue(resultFuture, "resultFuture");
        iHealthDataService.revokeAllPermissions(requestContext, new RevokeAllPermissionsCallback(resultFuture));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void unregisterFromDataNotifications$lambda$15(ServiceBackedHealthDataClient this$0, RequestProto.UnregisterFromDataNotificationsRequest request, IHealthDataService iHealthDataService, SettableFuture resultFuture) throws RemoteException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(request, "$request");
        RequestContext requestContext = this$0.getRequestContext();
        UnregisterFromDataNotificationsRequest unregisterFromDataNotificationsRequest = new UnregisterFromDataNotificationsRequest(request);
        Intrinsics.checkNotNullExpressionValue(resultFuture, "resultFuture");
        iHealthDataService.unregisterFromDataNotifications(requestContext, unregisterFromDataNotificationsRequest, new UnregisterFromDataNotificationsCallback(resultFuture));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateData$lambda$6(ServiceBackedHealthDataClient this$0, UpsertDataRequest request, IHealthDataService iHealthDataService, SettableFuture resultFuture) throws RemoteException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(request, "$request");
        RequestContext requestContext = this$0.getRequestContext();
        Intrinsics.checkNotNullExpressionValue(resultFuture, "resultFuture");
        iHealthDataService.updateData(requestContext, request, new UpdateDataCallback(resultFuture));
    }

    @Override // androidx.health.platform.client.HealthDataAsyncClient
    @NotNull
    public ListenableFuture<ResponseProto.AggregateDataResponse> aggregate(@NotNull final RequestProto.AggregateDataRequest request) {
        Intrinsics.checkNotNullParameter(request, "request");
        ListenableFuture<ResponseProto.AggregateDataResponse> listenableFutureF = f(1, new RemoteFutureOperation() { // from class: androidx.health.platform.client.impl.d
            @Override // androidx.health.platform.client.impl.ipc.RemoteFutureOperation
            public final void execute(Object obj, SettableFuture settableFuture) throws RemoteException {
                ServiceBackedHealthDataClient.aggregate$lambda$11(this.f4337a, request, (IHealthDataService) obj, settableFuture);
            }
        });
        Intrinsics.checkNotNullExpressionValue(listenableFutureF, "executeWithVersionCheck(…)\n            )\n        }");
        return listenableFutureF;
    }

    @Override // androidx.health.platform.client.HealthDataAsyncClient
    @NotNull
    public ListenableFuture<Unit> deleteData(@NotNull List<RequestProto.DataTypeIdPair> uidsCollection, @NotNull List<RequestProto.DataTypeIdPair> clientIdsCollection) {
        Intrinsics.checkNotNullParameter(uidsCollection, "uidsCollection");
        Intrinsics.checkNotNullParameter(clientIdsCollection, "clientIdsCollection");
        final DeleteDataRequest deleteDataRequest = new DeleteDataRequest(uidsCollection, clientIdsCollection);
        ListenableFuture<Unit> listenableFutureF = f(1, new RemoteFutureOperation() { // from class: androidx.health.platform.client.impl.g
            @Override // androidx.health.platform.client.impl.ipc.RemoteFutureOperation
            public final void execute(Object obj, SettableFuture settableFuture) throws RemoteException {
                ServiceBackedHealthDataClient.deleteData$lambda$7(this.f4344a, deleteDataRequest, (IHealthDataService) obj, settableFuture);
            }
        });
        Intrinsics.checkNotNullExpressionValue(listenableFutureF, "executeWithVersionCheck(…(resultFuture))\n        }");
        return listenableFutureF;
    }

    @Override // androidx.health.platform.client.HealthDataAsyncClient
    @NotNull
    public ListenableFuture<Unit> deleteDataRange(@NotNull RequestProto.DeleteDataRangeRequest dataCollection) {
        Intrinsics.checkNotNullParameter(dataCollection, "dataCollection");
        final DeleteDataRangeRequest deleteDataRangeRequest = new DeleteDataRangeRequest(dataCollection);
        ListenableFuture<Unit> listenableFutureF = f(1, new RemoteFutureOperation() { // from class: androidx.health.platform.client.impl.i
            @Override // androidx.health.platform.client.impl.ipc.RemoteFutureOperation
            public final void execute(Object obj, SettableFuture settableFuture) throws RemoteException {
                ServiceBackedHealthDataClient.deleteDataRange$lambda$8(this.f4346a, deleteDataRangeRequest, (IHealthDataService) obj, settableFuture);
            }
        });
        Intrinsics.checkNotNullExpressionValue(listenableFutureF, "executeWithVersionCheck(…)\n            )\n        }");
        return listenableFutureF;
    }

    @Override // androidx.health.platform.client.HealthDataAsyncClient
    @NotNull
    public ListenableFuture<Set<PermissionProto.Permission>> filterGrantedPermissions(@NotNull final Set<PermissionProto.Permission> permissions) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        ListenableFuture<Set<PermissionProto.Permission>> listenableFutureF = f(Math.min(1, 5), new RemoteFutureOperation() { // from class: androidx.health.platform.client.impl.j
            @Override // androidx.health.platform.client.impl.ipc.RemoteFutureOperation
            public final void execute(Object obj, SettableFuture settableFuture) throws RemoteException {
                ServiceBackedHealthDataClient.filterGrantedPermissions$lambda$3(this.f4368a, permissions, (IHealthDataService) obj, settableFuture);
            }
        });
        Intrinsics.checkNotNullExpressionValue(listenableFutureF, "executeWithVersionCheck(…)\n            )\n        }");
        return listenableFutureF;
    }

    @Override // androidx.health.platform.client.HealthDataAsyncClient
    @NotNull
    public ListenableFuture<ResponseProto.GetChangesResponse> getChanges(@NotNull final RequestProto.GetChangesRequest request) {
        Intrinsics.checkNotNullParameter(request, "request");
        ListenableFuture<ResponseProto.GetChangesResponse> listenableFutureF = f(1, new RemoteFutureOperation() { // from class: androidx.health.platform.client.impl.b
            @Override // androidx.health.platform.client.impl.ipc.RemoteFutureOperation
            public final void execute(Object obj, SettableFuture settableFuture) throws RemoteException {
                ServiceBackedHealthDataClient.getChanges$lambda$13(this.f4333a, request, (IHealthDataService) obj, settableFuture);
            }
        });
        Intrinsics.checkNotNullExpressionValue(listenableFutureF, "executeWithVersionCheck(…)\n            )\n        }");
        return listenableFutureF;
    }

    @Override // androidx.health.platform.client.HealthDataAsyncClient
    @NotNull
    public ListenableFuture<ResponseProto.GetChangesTokenResponse> getChangesToken(@NotNull final RequestProto.GetChangesTokenRequest request) {
        Intrinsics.checkNotNullParameter(request, "request");
        ListenableFuture<ResponseProto.GetChangesTokenResponse> listenableFutureF = f(1, new RemoteFutureOperation() { // from class: androidx.health.platform.client.impl.c
            @Override // androidx.health.platform.client.impl.ipc.RemoteFutureOperation
            public final void execute(Object obj, SettableFuture settableFuture) throws RemoteException {
                ServiceBackedHealthDataClient.getChangesToken$lambda$12(this.f4335a, request, (IHealthDataService) obj, settableFuture);
            }
        });
        Intrinsics.checkNotNullExpressionValue(listenableFutureF, "executeWithVersionCheck(…)\n            )\n        }");
        return listenableFutureF;
    }

    @Override // androidx.health.platform.client.HealthDataAsyncClient
    @NotNull
    public ListenableFuture<Set<PermissionProto.Permission>> getGrantedPermissions(@NotNull final Set<PermissionProto.Permission> permissions) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        ListenableFuture<Set<PermissionProto.Permission>> listenableFutureF = f(1, new RemoteFutureOperation() { // from class: androidx.health.platform.client.impl.e
            @Override // androidx.health.platform.client.impl.ipc.RemoteFutureOperation
            public final void execute(Object obj, SettableFuture settableFuture) throws RemoteException {
                ServiceBackedHealthDataClient.getGrantedPermissions$lambda$1(this.f4340a, permissions, (IHealthDataService) obj, settableFuture);
            }
        });
        Intrinsics.checkNotNullExpressionValue(listenableFutureF, "executeWithVersionCheck(…)\n            )\n        }");
        return listenableFutureF;
    }

    @Override // androidx.health.platform.client.HealthDataAsyncClient
    @NotNull
    public ListenableFuture<List<String>> insertData(@NotNull List<DataProto.DataPoint> dataCollection) {
        Intrinsics.checkNotNullParameter(dataCollection, "dataCollection");
        final UpsertDataRequest upsertDataRequest = new UpsertDataRequest(dataCollection);
        ListenableFuture<List<String>> listenableFutureF = f(1, new RemoteFutureOperation() { // from class: androidx.health.platform.client.impl.n
            @Override // androidx.health.platform.client.impl.ipc.RemoteFutureOperation
            public final void execute(Object obj, SettableFuture settableFuture) throws RemoteException {
                ServiceBackedHealthDataClient.insertData$lambda$5(this.f4375a, upsertDataRequest, (IHealthDataService) obj, settableFuture);
            }
        });
        Intrinsics.checkNotNullExpressionValue(listenableFutureF, "executeWithVersionCheck(…(resultFuture))\n        }");
        return listenableFutureF;
    }

    @Override // androidx.health.platform.client.HealthDataAsyncClient
    @NotNull
    public ListenableFuture<DataProto.DataPoint> readData(@NotNull RequestProto.ReadDataRequest dataCollection) {
        Intrinsics.checkNotNullParameter(dataCollection, "dataCollection");
        final ReadDataRequest readDataRequest = new ReadDataRequest(dataCollection);
        ListenableFuture<DataProto.DataPoint> listenableFutureF = f(1, new RemoteFutureOperation() { // from class: androidx.health.platform.client.impl.p
            @Override // androidx.health.platform.client.impl.ipc.RemoteFutureOperation
            public final void execute(Object obj, SettableFuture settableFuture) throws RemoteException {
                ServiceBackedHealthDataClient.readData$lambda$9(this.f4379a, readDataRequest, (IHealthDataService) obj, settableFuture);
            }
        });
        Intrinsics.checkNotNullExpressionValue(listenableFutureF, "executeWithVersionCheck(…(resultFuture))\n        }");
        return listenableFutureF;
    }

    @Override // androidx.health.platform.client.HealthDataAsyncClient
    @NotNull
    public ListenableFuture<ResponseProto.ReadDataRangeResponse> readDataRange(@NotNull RequestProto.ReadDataRangeRequest dataCollection) {
        Intrinsics.checkNotNullParameter(dataCollection, "dataCollection");
        final ReadDataRangeRequest readDataRangeRequest = new ReadDataRangeRequest(dataCollection);
        ListenableFuture<ResponseProto.ReadDataRangeResponse> listenableFutureF = f(1, new RemoteFutureOperation() { // from class: androidx.health.platform.client.impl.m
            @Override // androidx.health.platform.client.impl.ipc.RemoteFutureOperation
            public final void execute(Object obj, SettableFuture settableFuture) throws RemoteException {
                ServiceBackedHealthDataClient.readDataRange$lambda$10(this.f4373a, readDataRangeRequest, (IHealthDataService) obj, settableFuture);
            }
        });
        Intrinsics.checkNotNullExpressionValue(listenableFutureF, "executeWithVersionCheck(…(resultFuture))\n        }");
        return listenableFutureF;
    }

    @Override // androidx.health.platform.client.HealthDataAsyncClient
    @NotNull
    public ListenableFuture<Void> registerForDataNotifications(@NotNull final RequestProto.RegisterForDataNotificationsRequest request) {
        Intrinsics.checkNotNullParameter(request, "request");
        ListenableFuture<Void> listenableFutureF = f(Math.min(1, 2), new RemoteFutureOperation() { // from class: androidx.health.platform.client.impl.l
            @Override // androidx.health.platform.client.impl.ipc.RemoteFutureOperation
            public final void execute(Object obj, SettableFuture settableFuture) throws RemoteException {
                ServiceBackedHealthDataClient.registerForDataNotifications$lambda$14(this.f4371a, request, (IHealthDataService) obj, settableFuture);
            }
        });
        Intrinsics.checkNotNullExpressionValue(listenableFutureF, "executeWithVersionCheck(…,\n            )\n        }");
        return listenableFutureF;
    }

    @Override // androidx.health.platform.client.HealthDataAsyncClient
    @NotNull
    public ListenableFuture<Unit> revokeAllPermissions() {
        ListenableFuture<Unit> listenableFutureF = f(1, new RemoteFutureOperation() { // from class: androidx.health.platform.client.impl.k
            @Override // androidx.health.platform.client.impl.ipc.RemoteFutureOperation
            public final void execute(Object obj, SettableFuture settableFuture) throws RemoteException {
                ServiceBackedHealthDataClient.revokeAllPermissions$lambda$4(this.f4370a, (IHealthDataService) obj, settableFuture);
            }
        });
        Intrinsics.checkNotNullExpressionValue(listenableFutureF, "executeWithVersionCheck(…)\n            )\n        }");
        return listenableFutureF;
    }

    @Override // androidx.health.platform.client.HealthDataAsyncClient
    @NotNull
    public ListenableFuture<Void> unregisterFromDataNotifications(@NotNull final RequestProto.UnregisterFromDataNotificationsRequest request) {
        Intrinsics.checkNotNullParameter(request, "request");
        ListenableFuture<Void> listenableFutureF = f(Math.min(1, 2), new RemoteFutureOperation() { // from class: androidx.health.platform.client.impl.o
            @Override // androidx.health.platform.client.impl.ipc.RemoteFutureOperation
            public final void execute(Object obj, SettableFuture settableFuture) throws RemoteException {
                ServiceBackedHealthDataClient.unregisterFromDataNotifications$lambda$15(this.f4377a, request, (IHealthDataService) obj, settableFuture);
            }
        });
        Intrinsics.checkNotNullExpressionValue(listenableFutureF, "executeWithVersionCheck(…,\n            )\n        }");
        return listenableFutureF;
    }

    @Override // androidx.health.platform.client.HealthDataAsyncClient
    @NotNull
    public ListenableFuture<Unit> updateData(@NotNull List<DataProto.DataPoint> dataCollection) {
        Intrinsics.checkNotNullParameter(dataCollection, "dataCollection");
        final UpsertDataRequest upsertDataRequest = new UpsertDataRequest(dataCollection);
        ListenableFuture<Unit> listenableFutureF = f(1, new RemoteFutureOperation() { // from class: androidx.health.platform.client.impl.f
            @Override // androidx.health.platform.client.impl.ipc.RemoteFutureOperation
            public final void execute(Object obj, SettableFuture settableFuture) throws RemoteException {
                ServiceBackedHealthDataClient.updateData$lambda$6(this.f4342a, upsertDataRequest, (IHealthDataService) obj, settableFuture);
            }
        });
        Intrinsics.checkNotNullExpressionValue(listenableFutureF, "executeWithVersionCheck(…(resultFuture))\n        }");
        return listenableFutureF;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ServiceBackedHealthDataClient(@NotNull Context context, @NotNull ClientConfiguration clientConfiguration) {
        this(context, clientConfiguration, ProviderConnectionManager.INSTANCE.getInstance(context));
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(clientConfiguration, "clientConfiguration");
    }
}
