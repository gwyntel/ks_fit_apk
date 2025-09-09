package androidx.health.connect.client;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.RestrictTo;
import androidx.health.connect.client.contracts.HealthPermissionsRequestContract;
import androidx.health.connect.client.permission.HealthPermissionsRequestAppContract;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u0000 \b2\u00020\u0001:\u0001\bJ\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H¦@ø\u0001\u0000¢\u0006\u0002\u0010\u0005J\u0011\u0010\u0006\u001a\u00020\u0007H¦@ø\u0001\u0000¢\u0006\u0002\u0010\u0005ø\u0001\u0001\u0082\u0002\n\n\u0002\b\u0019\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0003"}, d2 = {"Landroidx/health/connect/client/PermissionController;", "", "getGrantedPermissions", "", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "revokeAllPermissions", "", "Companion", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public interface PermissionController {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = Companion.f4328a;

    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\u0003\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00042\b\b\u0002\u0010\u0007\u001a\u00020\u0006H\u0007J*\u0010\b\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00042\b\b\u0002\u0010\u0007\u001a\u00020\u0006H\u0007¨\u0006\t"}, d2 = {"Landroidx/health/connect/client/PermissionController$Companion;", "", "()V", "createRequestPermissionResultContract", "Landroidx/activity/result/contract/ActivityResultContract;", "", "", "providerPackageName", "createRequestPermissionResultContractLegacy", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ Companion f4328a = new Companion();

        private Companion() {
        }

        public static /* synthetic */ ActivityResultContract createRequestPermissionResultContract$default(Companion companion, String str, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                str = "com.google.android.apps.healthdata";
            }
            return companion.createRequestPermissionResultContract(str);
        }

        public static /* synthetic */ ActivityResultContract createRequestPermissionResultContractLegacy$default(Companion companion, String str, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                str = "com.google.android.apps.healthdata";
            }
            return companion.createRequestPermissionResultContractLegacy(str);
        }

        @JvmStatic
        @JvmOverloads
        @NotNull
        public final ActivityResultContract<Set<String>, Set<String>> createRequestPermissionResultContract() {
            return createRequestPermissionResultContract$default(this, null, 1, null);
        }

        @JvmStatic
        @NotNull
        @JvmOverloads
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public final ActivityResultContract<Set<String>, Set<String>> createRequestPermissionResultContractLegacy() {
            return createRequestPermissionResultContractLegacy$default(this, null, 1, null);
        }

        @JvmStatic
        @JvmOverloads
        @NotNull
        public final ActivityResultContract<Set<String>, Set<String>> createRequestPermissionResultContract(@NotNull String providerPackageName) {
            Intrinsics.checkNotNullParameter(providerPackageName, "providerPackageName");
            return new HealthPermissionsRequestContract(providerPackageName);
        }

        @JvmStatic
        @NotNull
        @JvmOverloads
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public final ActivityResultContract<Set<String>, Set<String>> createRequestPermissionResultContractLegacy(@NotNull String providerPackageName) {
            Intrinsics.checkNotNullParameter(providerPackageName, "providerPackageName");
            return new HealthPermissionsRequestAppContract(providerPackageName);
        }
    }

    @Nullable
    Object getGrantedPermissions(@NotNull Continuation<? super Set<String>> continuation);

    @Nullable
    Object revokeAllPermissions(@NotNull Continuation<? super Unit> continuation);
}
