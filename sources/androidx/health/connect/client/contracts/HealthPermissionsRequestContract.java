package androidx.health.connect.client.contracts;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.health.connect.client.permission.HealthPermission;
import androidx.health.connect.client.permission.HealthPermissionsRequestAppContract;
import androidx.health.connect.client.permission.platform.HealthPermissionsRequestModuleContract;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.taobao.agoo.a.a.b;
import com.umeng.analytics.pro.f;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0016J \u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\bH\u0016R&\u0010\u0006\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Landroidx/health/connect/client/contracts/HealthPermissionsRequestContract;", "Landroidx/activity/result/contract/ActivityResultContract;", "", "", "providerPackageName", "(Ljava/lang/String;)V", "delegate", "createIntent", "Landroid/content/Intent;", f.X, "Landroid/content/Context;", "input", "parseResult", b.JSON_ERRORCODE, "", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nHealthPermissionsRequestContract.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HealthPermissionsRequestContract.kt\nandroidx/health/connect/client/contracts/HealthPermissionsRequestContract\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,71:1\n1726#2,3:72\n1#3:75\n*S KotlinDebug\n*F\n+ 1 HealthPermissionsRequestContract.kt\nandroidx/health/connect/client/contracts/HealthPermissionsRequestContract\n*L\n54#1:72,3\n*E\n"})
/* loaded from: classes.dex */
public final class HealthPermissionsRequestContract extends ActivityResultContract<Set<? extends String>, Set<? extends String>> {

    @NotNull
    private final ActivityResultContract<Set<String>, Set<String>> delegate;

    public HealthPermissionsRequestContract() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    @Override // androidx.activity.result.contract.ActivityResultContract
    public /* bridge */ /* synthetic */ Intent createIntent(Context context, Set<? extends String> set) {
        return createIntent2(context, (Set<String>) set);
    }

    public /* synthetic */ HealthPermissionsRequestContract(String str, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? "com.google.android.apps.healthdata" : str);
    }

    @NotNull
    /* renamed from: createIntent, reason: avoid collision after fix types in other method */
    public Intent createIntent2(@NotNull Context context, @NotNull Set<String> input) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(input, "input");
        if (!(input instanceof Collection) || !input.isEmpty()) {
            Iterator<T> it = input.iterator();
            while (it.hasNext()) {
                if (!StringsKt.startsWith$default((String) it.next(), HealthPermission.PERMISSION_PREFIX, false, 2, (Object) null)) {
                    throw new IllegalArgumentException("Unsupported health connect permission".toString());
                }
            }
        }
        if (!(!input.isEmpty())) {
            throw new IllegalArgumentException("At least one permission is required!".toString());
        }
        Intent intentCreateIntent = this.delegate.createIntent(context, input);
        Intrinsics.checkNotNullExpressionValue(intentCreateIntent, "delegate.createIntent(context, input)");
        return intentCreateIntent;
    }

    @Override // androidx.activity.result.contract.ActivityResultContract
    @NotNull
    public Set<? extends String> parseResult(int resultCode, @Nullable Intent intent) {
        Set<String> result = this.delegate.parseResult(resultCode, intent);
        Intrinsics.checkNotNullExpressionValue(result, "delegate.parseResult(resultCode, intent)");
        return result;
    }

    public HealthPermissionsRequestContract(@NotNull String providerPackageName) {
        ActivityResultContract<Set<String>, Set<String>> healthPermissionsRequestAppContract;
        Intrinsics.checkNotNullParameter(providerPackageName, "providerPackageName");
        if (Build.VERSION.SDK_INT >= 34) {
            healthPermissionsRequestAppContract = new HealthPermissionsRequestModuleContract();
        } else {
            healthPermissionsRequestAppContract = new HealthPermissionsRequestAppContract(providerPackageName);
        }
        this.delegate = healthPermissionsRequestAppContract;
    }
}
