package androidx.health.connect.client.permission.platform;

import android.content.Context;
import android.content.Intent;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RestrictTo;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.taobao.agoo.a.a.b;
import com.umeng.analytics.pro.f;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0001\u0018\u00002\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0016J,\u0010\f\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002\u0018\u00010\r2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0016J \u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\bH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Landroidx/health/connect/client/permission/platform/HealthPermissionsRequestModuleContract;", "Landroidx/activity/result/contract/ActivityResultContract;", "", "", "()V", "requestPermissions", "Landroidx/activity/result/contract/ActivityResultContracts$RequestMultiplePermissions;", "createIntent", "Landroid/content/Intent;", f.X, "Landroid/content/Context;", "input", "getSynchronousResult", "Landroidx/activity/result/contract/ActivityResultContract$SynchronousResult;", "parseResult", b.JSON_ERRORCODE, "", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@RestrictTo({RestrictTo.Scope.LIBRARY})
@SourceDebugExtension({"SMAP\nHealthPermissionsRequestModuleContract.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HealthPermissionsRequestModuleContract.kt\nandroidx/health/connect/client/permission/platform/HealthPermissionsRequestModuleContract\n+ 2 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,49:1\n37#2,2:50\n37#2,2:59\n483#3,7:52\n483#3,7:61\n*S KotlinDebug\n*F\n+ 1 HealthPermissionsRequestModuleContract.kt\nandroidx/health/connect/client/permission/platform/HealthPermissionsRequestModuleContract\n*L\n36#1:50,2\n45#1:59,2\n39#1:52,7\n46#1:61,7\n*E\n"})
/* loaded from: classes.dex */
public final class HealthPermissionsRequestModuleContract extends ActivityResultContract<Set<? extends String>, Set<? extends String>> {

    @NotNull
    private final ActivityResultContracts.RequestMultiplePermissions requestPermissions = new ActivityResultContracts.RequestMultiplePermissions();

    @Override // androidx.activity.result.contract.ActivityResultContract
    public /* bridge */ /* synthetic */ Intent createIntent(Context context, Set<? extends String> set) {
        return createIntent2(context, (Set<String>) set);
    }

    @Override // androidx.activity.result.contract.ActivityResultContract
    public /* bridge */ /* synthetic */ ActivityResultContract.SynchronousResult<Set<? extends String>> getSynchronousResult(Context context, Set<? extends String> set) {
        return getSynchronousResult2(context, (Set<String>) set);
    }

    @NotNull
    /* renamed from: createIntent, reason: avoid collision after fix types in other method */
    public Intent createIntent2(@NotNull Context context, @NotNull Set<String> input) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(input, "input");
        Intent intentCreateIntent = this.requestPermissions.createIntent(context, (String[]) input.toArray(new String[0]));
        Intrinsics.checkNotNullExpressionValue(intentCreateIntent, "requestPermissions.creat…xt, input.toTypedArray())");
        return intentCreateIntent;
    }

    @Nullable
    /* renamed from: getSynchronousResult, reason: avoid collision after fix types in other method */
    public ActivityResultContract.SynchronousResult<Set<String>> getSynchronousResult2(@NotNull Context context, @NotNull Set<String> input) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(input, "input");
        ActivityResultContract.SynchronousResult<Map<String, Boolean>> synchronousResult = this.requestPermissions.getSynchronousResult(context, (String[]) input.toArray(new String[0]));
        if (synchronousResult == null) {
            return null;
        }
        Map<String, Boolean> value = synchronousResult.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "result.value");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<String, Boolean> entry : value.entrySet()) {
            Boolean it = entry.getValue();
            Intrinsics.checkNotNullExpressionValue(it, "it");
            if (it.booleanValue()) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        return new ActivityResultContract.SynchronousResult<>(linkedHashMap.keySet());
    }

    @Override // androidx.activity.result.contract.ActivityResultContract
    @NotNull
    public Set<? extends String> parseResult(int resultCode, @Nullable Intent intent) {
        Map<String, Boolean> result = this.requestPermissions.parseResult(resultCode, intent);
        Intrinsics.checkNotNullExpressionValue(result, "requestPermissions.parseResult(resultCode, intent)");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<String, Boolean> entry : result.entrySet()) {
            Boolean it = entry.getValue();
            Intrinsics.checkNotNullExpressionValue(it, "it");
            if (it.booleanValue()) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        return linkedHashMap.keySet();
    }
}
