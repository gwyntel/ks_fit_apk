package androidx.health.connect.client.permission;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.RestrictTo;
import androidx.health.platform.client.impl.logger.Logger;
import androidx.health.platform.client.permission.Permission;
import androidx.health.platform.client.proto.PermissionProto;
import androidx.health.platform.client.service.HealthDataServiceConstants;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.taobao.agoo.a.a.b;
import com.umeng.analytics.pro.f;
import java.util.ArrayList;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0001\u0018\u00002\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u001e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0016J,\u0010\u000b\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002\u0018\u00010\f2\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0016J \u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0007H\u0016R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Landroidx/health/connect/client/permission/HealthPermissionsRequestAppContract;", "Landroidx/activity/result/contract/ActivityResultContract;", "", "", "providerPackageName", "(Ljava/lang/String;)V", "createIntent", "Landroid/content/Intent;", f.X, "Landroid/content/Context;", "input", "getSynchronousResult", "Landroidx/activity/result/contract/ActivityResultContract$SynchronousResult;", "parseResult", b.JSON_ERRORCODE, "", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public final class HealthPermissionsRequestAppContract extends ActivityResultContract<Set<? extends String>, Set<? extends String>> {

    @NotNull
    private final String providerPackageName;

    public HealthPermissionsRequestAppContract() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    @Override // androidx.activity.result.contract.ActivityResultContract
    public /* bridge */ /* synthetic */ Intent createIntent(Context context, Set<? extends String> set) {
        return createIntent2(context, (Set<String>) set);
    }

    @Nullable
    /* renamed from: getSynchronousResult, reason: avoid collision after fix types in other method */
    public ActivityResultContract.SynchronousResult<Set<String>> getSynchronousResult2(@NotNull Context context, @NotNull Set<String> input) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(input, "input");
        return null;
    }

    public /* synthetic */ HealthPermissionsRequestAppContract(String str, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? "com.google.android.apps.healthdata" : str);
    }

    @NotNull
    /* renamed from: createIntent, reason: avoid collision after fix types in other method */
    public Intent createIntent2(@NotNull Context context, @NotNull Set<String> input) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(input, "input");
        ArrayList<? extends Parcelable> arrayList = (ArrayList) SequencesKt.toCollection(SequencesKt.map(CollectionsKt.asSequence(input), new Function1<String, Permission>() { // from class: androidx.health.connect.client.permission.HealthPermissionsRequestAppContract$createIntent$protoPermissionList$1
            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final Permission invoke(@NotNull String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                PermissionProto.Permission permissionBuild = PermissionProto.Permission.newBuilder().setPermission(it).build();
                Intrinsics.checkNotNullExpressionValue(permissionBuild, "newBuilder().setPermission(it).build()");
                return new Permission(permissionBuild);
            }
        }), new ArrayList());
        Logger.debug("HealthConnectClient", "Requesting " + input.size() + " permissions.");
        Intent intent = new Intent(HealthDataServiceConstants.ACTION_REQUEST_PERMISSIONS);
        intent.putParcelableArrayListExtra(HealthDataServiceConstants.KEY_REQUESTED_PERMISSIONS_STRING, arrayList);
        if (this.providerPackageName.length() > 0) {
            intent.setPackage(this.providerPackageName);
        }
        return intent;
    }

    @Override // androidx.activity.result.contract.ActivityResultContract
    public /* bridge */ /* synthetic */ ActivityResultContract.SynchronousResult<Set<? extends String>> getSynchronousResult(Context context, Set<? extends String> set) {
        return getSynchronousResult2(context, (Set<String>) set);
    }

    @Override // androidx.activity.result.contract.ActivityResultContract
    @NotNull
    public Set<? extends String> parseResult(int resultCode, @Nullable Intent intent) {
        Set<? extends String> setEmptySet;
        ArrayList parcelableArrayListExtra;
        Sequence sequenceAsSequence;
        Sequence map;
        if (intent == null || (parcelableArrayListExtra = intent.getParcelableArrayListExtra(HealthDataServiceConstants.KEY_GRANTED_PERMISSIONS_STRING)) == null || (sequenceAsSequence = CollectionsKt.asSequence(parcelableArrayListExtra)) == null || (map = SequencesKt.map(sequenceAsSequence, new Function1<Permission, String>() { // from class: androidx.health.connect.client.permission.HealthPermissionsRequestAppContract$parseResult$grantedPermissions$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(Permission permission) {
                return permission.getProto().getPermission();
            }
        })) == null || (setEmptySet = SequencesKt.toSet(map)) == null) {
            setEmptySet = SetsKt.emptySet();
        }
        Logger.debug("HealthConnectClient", "Granted " + setEmptySet.size() + " permissions.");
        return setEmptySet;
    }

    public HealthPermissionsRequestAppContract(@NotNull String providerPackageName) {
        Intrinsics.checkNotNullParameter(providerPackageName, "providerPackageName");
        this.providerPackageName = providerPackageName;
    }
}
