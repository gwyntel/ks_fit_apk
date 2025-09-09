package androidx.health.platform.client.impl;

import androidx.health.platform.client.error.ErrorStatus;
import androidx.health.platform.client.impl.error.ErrorStatusConverterKt;
import androidx.health.platform.client.permission.Permission;
import androidx.health.platform.client.proto.PermissionProto;
import androidx.health.platform.client.service.IFilterGrantedPermissionsCallback;
import com.google.common.util.concurrent.SettableFuture;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0016\u0010\u000b\u001a\u00020\b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0016R\u001a\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Landroidx/health/platform/client/impl/FilterGrantedPermissionsCallback;", "Landroidx/health/platform/client/service/IFilterGrantedPermissionsCallback$Stub;", "resultFuture", "Lcom/google/common/util/concurrent/SettableFuture;", "", "Landroidx/health/platform/client/proto/PermissionProto$Permission;", "(Lcom/google/common/util/concurrent/SettableFuture;)V", "onError", "", "error", "Landroidx/health/platform/client/error/ErrorStatus;", "onSuccess", "response", "", "Landroidx/health/platform/client/permission/Permission;", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nFilterGrantedPermissionsCallback.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FilterGrantedPermissionsCallback.kt\nandroidx/health/platform/client/impl/FilterGrantedPermissionsCallback\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,37:1\n1549#2:38\n1620#2,3:39\n*S KotlinDebug\n*F\n+ 1 FilterGrantedPermissionsCallback.kt\nandroidx/health/platform/client/impl/FilterGrantedPermissionsCallback\n*L\n30#1:38\n30#1:39,3\n*E\n"})
/* loaded from: classes.dex */
public final class FilterGrantedPermissionsCallback extends IFilterGrantedPermissionsCallback.Stub {

    @NotNull
    private final SettableFuture<Set<PermissionProto.Permission>> resultFuture;

    public FilterGrantedPermissionsCallback(@NotNull SettableFuture<Set<PermissionProto.Permission>> resultFuture) {
        Intrinsics.checkNotNullParameter(resultFuture, "resultFuture");
        this.resultFuture = resultFuture;
    }

    @Override // androidx.health.platform.client.service.IFilterGrantedPermissionsCallback
    public void onError(@NotNull ErrorStatus error) {
        Intrinsics.checkNotNullParameter(error, "error");
        this.resultFuture.setException(ErrorStatusConverterKt.toException(error));
    }

    @Override // androidx.health.platform.client.service.IFilterGrantedPermissionsCallback
    public void onSuccess(@NotNull List<Permission> response) {
        Intrinsics.checkNotNullParameter(response, "response");
        SettableFuture<Set<PermissionProto.Permission>> settableFuture = this.resultFuture;
        List<Permission> list = response;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((Permission) it.next()).getProto());
        }
        settableFuture.set(CollectionsKt.toSet(arrayList));
    }
}
