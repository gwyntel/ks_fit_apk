package androidx.health.connect.client;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.ChecksSdkIntAtLeast;
import androidx.annotation.RestrictTo;
import androidx.core.content.pm.PackageInfoCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.health.connect.client.aggregate.AggregationResult;
import androidx.health.connect.client.aggregate.AggregationResultGroupedByDuration;
import androidx.health.connect.client.aggregate.AggregationResultGroupedByPeriod;
import androidx.health.connect.client.impl.HealthConnectClientImpl;
import androidx.health.connect.client.impl.HealthConnectClientUpsideDownImpl;
import androidx.health.connect.client.records.Record;
import androidx.health.connect.client.request.AggregateGroupByDurationRequest;
import androidx.health.connect.client.request.AggregateGroupByPeriodRequest;
import androidx.health.connect.client.request.AggregateRequest;
import androidx.health.connect.client.request.ChangesTokenRequest;
import androidx.health.connect.client.request.ReadRecordsRequest;
import androidx.health.connect.client.response.ChangesResponse;
import androidx.health.connect.client.response.InsertRecordsResponse;
import androidx.health.connect.client.response.ReadRecordResponse;
import androidx.health.connect.client.response.ReadRecordsResponse;
import androidx.health.connect.client.time.TimeRangeFilter;
import androidx.health.platform.client.HealthDataService;
import com.facebook.share.internal.ShareConstants;
import com.umeng.analytics.pro.f;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.annotation.AnnotationRetention;
import kotlin.coroutines.Continuation;
import kotlin.jvm.JvmName;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u0000 52\u00020\u0001:\u00015J\u0019\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH¦@ø\u0001\u0000¢\u0006\u0002\u0010\nJ\u001f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\b\u001a\u00020\u000eH¦@ø\u0001\u0000¢\u0006\u0002\u0010\u000fJ\u001f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\f2\u0006\u0010\b\u001a\u00020\u0012H¦@ø\u0001\u0000¢\u0006\u0002\u0010\u0013J)\u0010\u0014\u001a\u00020\u00152\u000e\u0010\u0016\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00180\u00172\u0006\u0010\u0019\u001a\u00020\u001aH¦@ø\u0001\u0000¢\u0006\u0002\u0010\u001bJ=\u0010\u0014\u001a\u00020\u00152\u000e\u0010\u0016\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00180\u00172\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\f2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001d0\fH¦@ø\u0001\u0000¢\u0006\u0002\u0010\u001fJ\u0019\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u001dH¦@ø\u0001\u0000¢\u0006\u0002\u0010#J\u0019\u0010$\u001a\u00020\u001d2\u0006\u0010\b\u001a\u00020%H¦@ø\u0001\u0000¢\u0006\u0002\u0010&J\u001f\u0010'\u001a\u00020(2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00180\fH¦@ø\u0001\u0000¢\u0006\u0002\u0010*J7\u0010+\u001a\b\u0012\u0004\u0012\u0002H-0,\"\b\b\u0000\u0010-*\u00020\u00182\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H-0\u00172\u0006\u0010.\u001a\u00020\u001dH¦@ø\u0001\u0000¢\u0006\u0002\u0010/J/\u00100\u001a\b\u0012\u0004\u0012\u0002H-01\"\b\b\u0000\u0010-*\u00020\u00182\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H-02H¦@ø\u0001\u0000¢\u0006\u0002\u00103J\u001f\u00104\u001a\u00020\u00152\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00180\fH¦@ø\u0001\u0000¢\u0006\u0002\u0010*R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0001\u0082\u0002\n\n\u0002\b\u0019\n\u0004\b!0\u0001¨\u00066À\u0006\u0003"}, d2 = {"Landroidx/health/connect/client/HealthConnectClient;", "", "permissionController", "Landroidx/health/connect/client/PermissionController;", "getPermissionController", "()Landroidx/health/connect/client/PermissionController;", "aggregate", "Landroidx/health/connect/client/aggregate/AggregationResult;", ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID, "Landroidx/health/connect/client/request/AggregateRequest;", "(Landroidx/health/connect/client/request/AggregateRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "aggregateGroupByDuration", "", "Landroidx/health/connect/client/aggregate/AggregationResultGroupedByDuration;", "Landroidx/health/connect/client/request/AggregateGroupByDurationRequest;", "(Landroidx/health/connect/client/request/AggregateGroupByDurationRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "aggregateGroupByPeriod", "Landroidx/health/connect/client/aggregate/AggregationResultGroupedByPeriod;", "Landroidx/health/connect/client/request/AggregateGroupByPeriodRequest;", "(Landroidx/health/connect/client/request/AggregateGroupByPeriodRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteRecords", "", "recordType", "Lkotlin/reflect/KClass;", "Landroidx/health/connect/client/records/Record;", "timeRangeFilter", "Landroidx/health/connect/client/time/TimeRangeFilter;", "(Lkotlin/reflect/KClass;Landroidx/health/connect/client/time/TimeRangeFilter;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "recordIdsList", "", "clientRecordIdsList", "(Lkotlin/reflect/KClass;Ljava/util/List;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getChanges", "Landroidx/health/connect/client/response/ChangesResponse;", "changesToken", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getChangesToken", "Landroidx/health/connect/client/request/ChangesTokenRequest;", "(Landroidx/health/connect/client/request/ChangesTokenRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertRecords", "Landroidx/health/connect/client/response/InsertRecordsResponse;", "records", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "readRecord", "Landroidx/health/connect/client/response/ReadRecordResponse;", ExifInterface.GPS_DIRECTION_TRUE, "recordId", "(Lkotlin/reflect/KClass;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "readRecords", "Landroidx/health/connect/client/response/ReadRecordsResponse;", "Landroidx/health/connect/client/request/ReadRecordsRequest;", "(Landroidx/health/connect/client/request/ReadRecordsRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateRecords", "Companion", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public interface HealthConnectClient {

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final String ACTION_HEALTH_CONNECT_SETTINGS_LEGACY = "androidx.health.ACTION_HEALTH_CONNECT_SETTINGS";

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = Companion.f4327a;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final int DEFAULT_PROVIDER_MIN_VERSION_CODE = 68623;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final String DEFAULT_PROVIDER_PACKAGE_NAME = "com.google.android.apps.healthdata";

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final String HEALTH_CONNECT_CLIENT_TAG = "HealthConnectClient";
    public static final int SDK_AVAILABLE = 3;
    public static final int SDK_UNAVAILABLE = 1;
    public static final int SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED = 2;

    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001+B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0004H\u0007J\u001a\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0004H\u0007J\u001a\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0004H\u0007J\u001a\u0010\u001a\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0004H\u0007J\u001a\u0010\u001b\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0004H\u0007J\u001d\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0004H\u0000¢\u0006\u0002\b!J\"\u0010\"\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00042\b\b\u0002\u0010#\u001a\u00020\fH\u0002J)\u0010$\u001a\u00020\u001d2\u0006\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00042\b\b\u0002\u0010%\u001a\u00020\fH\u0000¢\u0006\u0002\b&J\u001f\u0010'\u001a\u00020\u001d2\u0006\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0004H\u0000¢\u0006\u0002\b(J\r\u0010)\u001a\u00020\u001dH\u0001¢\u0006\u0002\b*R\u0016\u0010\u0003\u001a\u00020\u00048\u0000X\u0081\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001c\u0010\u0007\u001a\u00020\u00048GX\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\b\u0010\u0002\u001a\u0004\b\t\u0010\u0006R\u0010\u0010\n\u001a\u00020\u00048\u0006X\u0087T¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u00020\f8\u0000X\u0081T¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u00048\u0000X\u0081T¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u00020\u00048\u0000X\u0081T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\fX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\fX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\fX\u0086T¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Landroidx/health/connect/client/HealthConnectClient$Companion;", "", "()V", "ACTION_HEALTH_CONNECT_MANAGE_DATA", "", "getACTION_HEALTH_CONNECT_MANAGE_DATA$connect_client_release", "()Ljava/lang/String;", "ACTION_HEALTH_CONNECT_SETTINGS", "getHealthConnectSettingsAction$annotations", "getHealthConnectSettingsAction", "ACTION_HEALTH_CONNECT_SETTINGS_LEGACY", "DEFAULT_PROVIDER_MIN_VERSION_CODE", "", "DEFAULT_PROVIDER_PACKAGE_NAME", "HEALTH_CONNECT_CLIENT_TAG", "SDK_AVAILABLE", "SDK_UNAVAILABLE", "SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED", "getHealthConnectManageDataIntent", "Landroid/content/Intent;", f.X, "Landroid/content/Context;", "providerPackageName", "getOrCreate", "Landroidx/health/connect/client/HealthConnectClient;", "getOrCreateLegacy", "getSdkStatus", "getSdkStatusLegacy", "hasBindableService", "", "packageManager", "Landroid/content/pm/PackageManager;", "packageName", "hasBindableService$connect_client_release", "isPackageInstalled", "versionCode", "isProviderAvailable", "providerVersionCode", "isProviderAvailable$connect_client_release", "isProviderAvailableLegacy", "isProviderAvailableLegacy$connect_client_release", "isSdkVersionSufficient", "isSdkVersionSufficient$connect_client_release", "AvailabilityStatus", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {

        @RestrictTo({RestrictTo.Scope.LIBRARY})
        @NotNull
        private static final String ACTION_HEALTH_CONNECT_MANAGE_DATA;

        @NotNull
        private static final String ACTION_HEALTH_CONNECT_SETTINGS;

        @RestrictTo({RestrictTo.Scope.LIBRARY})
        @NotNull
        public static final String ACTION_HEALTH_CONNECT_SETTINGS_LEGACY = "androidx.health.ACTION_HEALTH_CONNECT_SETTINGS";

        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public static final int DEFAULT_PROVIDER_MIN_VERSION_CODE = 68623;

        @RestrictTo({RestrictTo.Scope.LIBRARY})
        @NotNull
        public static final String DEFAULT_PROVIDER_PACKAGE_NAME = "com.google.android.apps.healthdata";

        @RestrictTo({RestrictTo.Scope.LIBRARY})
        @NotNull
        public static final String HEALTH_CONNECT_CLIENT_TAG = "HealthConnectClient";
        public static final int SDK_AVAILABLE = 3;
        public static final int SDK_UNAVAILABLE = 1;
        public static final int SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED = 2;

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ Companion f4327a = new Companion();

        @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Landroidx/health/connect/client/HealthConnectClient$Companion$AvailabilityStatus;", "", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        @Retention(RetentionPolicy.SOURCE)
        @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public @interface AvailabilityStatus {
        }

        static {
            int i2 = Build.VERSION.SDK_INT;
            ACTION_HEALTH_CONNECT_SETTINGS = i2 >= 34 ? "android.health.connect.action.HEALTH_HOME_SETTINGS" : "androidx.health.ACTION_HEALTH_CONNECT_SETTINGS";
            ACTION_HEALTH_CONNECT_MANAGE_DATA = i2 >= 34 ? "android.health.connect.action.MANAGE_HEALTH_DATA" : "androidx.health.ACTION_MANAGE_HEALTH_DATA";
        }

        private Companion() {
        }

        static /* synthetic */ boolean a(Companion companion, PackageManager packageManager, String str, int i2, int i3, Object obj) {
            if ((i3 & 4) != 0) {
                i2 = 68623;
            }
            return companion.isPackageInstalled(packageManager, str, i2);
        }

        public static /* synthetic */ Intent getHealthConnectManageDataIntent$default(Companion companion, Context context, String str, int i2, Object obj) {
            if ((i2 & 2) != 0) {
                str = "com.google.android.apps.healthdata";
            }
            return companion.getHealthConnectManageDataIntent(context, str);
        }

        @JvmStatic
        public static /* synthetic */ void getHealthConnectSettingsAction$annotations() {
        }

        public static /* synthetic */ HealthConnectClient getOrCreate$default(Companion companion, Context context, String str, int i2, Object obj) {
            if ((i2 & 2) != 0) {
                str = "com.google.android.apps.healthdata";
            }
            return companion.getOrCreate(context, str);
        }

        public static /* synthetic */ HealthConnectClient getOrCreateLegacy$default(Companion companion, Context context, String str, int i2, Object obj) {
            if ((i2 & 2) != 0) {
                str = "com.google.android.apps.healthdata";
            }
            return companion.getOrCreateLegacy(context, str);
        }

        public static /* synthetic */ int getSdkStatus$default(Companion companion, Context context, String str, int i2, Object obj) {
            if ((i2 & 2) != 0) {
                str = "com.google.android.apps.healthdata";
            }
            return companion.getSdkStatus(context, str);
        }

        public static /* synthetic */ int getSdkStatusLegacy$default(Companion companion, Context context, String str, int i2, Object obj) {
            if ((i2 & 2) != 0) {
                str = "com.google.android.apps.healthdata";
            }
            return companion.getSdkStatusLegacy(context, str);
        }

        private final boolean isPackageInstalled(PackageManager packageManager, String packageName, int versionCode) throws PackageManager.NameNotFoundException {
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
                Intrinsics.checkNotNullExpressionValue(packageInfo, "{\n                    @S…= */ 0)\n                }");
                if (packageInfo.applicationInfo.enabled) {
                    return (!Intrinsics.areEqual(packageName, "com.google.android.apps.healthdata") || PackageInfoCompat.getLongVersionCode(packageInfo) >= ((long) versionCode)) && hasBindableService$connect_client_release(packageManager, packageName);
                }
                return false;
            } catch (PackageManager.NameNotFoundException unused) {
                return false;
            }
        }

        public static /* synthetic */ boolean isProviderAvailable$connect_client_release$default(Companion companion, Context context, String str, int i2, int i3, Object obj) {
            if ((i3 & 2) != 0) {
                str = "com.google.android.apps.healthdata";
            }
            if ((i3 & 4) != 0) {
                i2 = 68623;
            }
            return companion.isProviderAvailable$connect_client_release(context, str, i2);
        }

        public static /* synthetic */ boolean isProviderAvailableLegacy$connect_client_release$default(Companion companion, Context context, String str, int i2, Object obj) {
            if ((i2 & 2) != 0) {
                str = "com.google.android.apps.healthdata";
            }
            return companion.isProviderAvailableLegacy$connect_client_release(context, str);
        }

        @NotNull
        public final String getACTION_HEALTH_CONNECT_MANAGE_DATA$connect_client_release() {
            return ACTION_HEALTH_CONNECT_MANAGE_DATA;
        }

        @JvmStatic
        @JvmOverloads
        @NotNull
        public final Intent getHealthConnectManageDataIntent(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return getHealthConnectManageDataIntent$default(this, context, null, 2, null);
        }

        @JvmName(name = "getHealthConnectSettingsAction")
        @NotNull
        public final String getHealthConnectSettingsAction() {
            return ACTION_HEALTH_CONNECT_SETTINGS;
        }

        @JvmStatic
        @JvmOverloads
        @NotNull
        public final HealthConnectClient getOrCreate(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return getOrCreate$default(this, context, null, 2, null);
        }

        @JvmStatic
        @NotNull
        @JvmOverloads
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public final HealthConnectClient getOrCreateLegacy(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return getOrCreateLegacy$default(this, context, null, 2, null);
        }

        @JvmStatic
        @JvmOverloads
        public final int getSdkStatus(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return getSdkStatus$default(this, context, null, 2, null);
        }

        @JvmStatic
        @JvmOverloads
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public final int getSdkStatusLegacy(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return getSdkStatusLegacy$default(this, context, null, 2, null);
        }

        public final boolean hasBindableService$connect_client_release(@NotNull PackageManager packageManager, @NotNull String packageName) {
            Intrinsics.checkNotNullParameter(packageManager, "packageManager");
            Intrinsics.checkNotNullParameter(packageName, "packageName");
            Intent intent = new Intent();
            intent.setPackage(packageName);
            intent.setAction(HealthDataService.ANDROID_HEALTH_PLATFORM_SERVICE_BIND_ACTION);
            Intrinsics.checkNotNullExpressionValue(packageManager.queryIntentServices(intent, 0), "packageManager.queryIntentServices(bindIntent, 0)");
            return !r2.isEmpty();
        }

        public final boolean isProviderAvailable$connect_client_release(@NotNull Context context, @NotNull String providerPackageName, int providerVersionCode) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(providerPackageName, "providerPackageName");
            if (Build.VERSION.SDK_INT >= 34) {
                return true;
            }
            PackageManager packageManager = context.getPackageManager();
            Intrinsics.checkNotNullExpressionValue(packageManager, "context.packageManager");
            return isPackageInstalled(packageManager, providerPackageName, providerVersionCode);
        }

        public final boolean isProviderAvailableLegacy$connect_client_release(@NotNull Context context, @NotNull String providerPackageName) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(providerPackageName, "providerPackageName");
            PackageManager packageManager = context.getPackageManager();
            Intrinsics.checkNotNullExpressionValue(packageManager, "context.packageManager");
            return a(this, packageManager, providerPackageName, 0, 4, null);
        }

        @ChecksSdkIntAtLeast(api = 28)
        public final boolean isSdkVersionSufficient$connect_client_release() {
            return Build.VERSION.SDK_INT >= 28;
        }

        @JvmStatic
        @JvmOverloads
        @NotNull
        public final Intent getHealthConnectManageDataIntent(@NotNull Context context, @NotNull String providerPackageName) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(providerPackageName, "providerPackageName");
            PackageManager packageManager = context.getPackageManager();
            Intent intent = new Intent(ACTION_HEALTH_CONNECT_MANAGE_DATA);
            return (!isProviderAvailable$connect_client_release$default(this, context, providerPackageName, 0, 4, null) || packageManager.resolveActivity(intent, 0) == null) ? new Intent(ACTION_HEALTH_CONNECT_SETTINGS) : intent;
        }

        @JvmStatic
        @JvmOverloads
        @NotNull
        public final HealthConnectClient getOrCreate(@NotNull Context context, @NotNull String providerPackageName) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(providerPackageName, "providerPackageName");
            int sdkStatus = getSdkStatus(context, providerPackageName);
            if (sdkStatus == 1) {
                throw new UnsupportedOperationException("SDK version too low");
            }
            int i2 = 2;
            if (sdkStatus != 2) {
                return Build.VERSION.SDK_INT >= 34 ? new HealthConnectClientUpsideDownImpl(context) : new HealthConnectClientImpl(HealthDataService.INSTANCE.getClient(context, providerPackageName), null, i2, 0 == true ? 1 : 0);
            }
            throw new IllegalStateException("Service not available");
        }

        @JvmStatic
        @NotNull
        @JvmOverloads
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public final HealthConnectClient getOrCreateLegacy(@NotNull Context context, @NotNull String providerPackageName) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(providerPackageName, "providerPackageName");
            int sdkStatusLegacy = getSdkStatusLegacy(context, providerPackageName);
            if (sdkStatusLegacy == 1) {
                throw new UnsupportedOperationException("SDK version too low");
            }
            int i2 = 2;
            if (sdkStatusLegacy != 2) {
                return new HealthConnectClientImpl(HealthDataService.INSTANCE.getClient(context, providerPackageName), null, i2, 0 == true ? 1 : 0);
            }
            throw new IllegalStateException("Service not available");
        }

        @JvmStatic
        @JvmOverloads
        public final int getSdkStatus(@NotNull Context context, @NotNull String providerPackageName) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(providerPackageName, "providerPackageName");
            if (isSdkVersionSufficient$connect_client_release()) {
                return !isProviderAvailable$connect_client_release$default(this, context, providerPackageName, 0, 4, null) ? 2 : 3;
            }
            return 1;
        }

        @JvmStatic
        @JvmOverloads
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public final int getSdkStatusLegacy(@NotNull Context context, @NotNull String providerPackageName) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(providerPackageName, "providerPackageName");
            if (isSdkVersionSufficient$connect_client_release()) {
                return !isProviderAvailableLegacy$connect_client_release(context, providerPackageName) ? 2 : 3;
            }
            return 1;
        }
    }

    @Nullable
    Object aggregate(@NotNull AggregateRequest aggregateRequest, @NotNull Continuation<? super AggregationResult> continuation);

    @Nullable
    Object aggregateGroupByDuration(@NotNull AggregateGroupByDurationRequest aggregateGroupByDurationRequest, @NotNull Continuation<? super List<AggregationResultGroupedByDuration>> continuation);

    @Nullable
    Object aggregateGroupByPeriod(@NotNull AggregateGroupByPeriodRequest aggregateGroupByPeriodRequest, @NotNull Continuation<? super List<AggregationResultGroupedByPeriod>> continuation);

    @Nullable
    Object deleteRecords(@NotNull KClass<? extends Record> kClass, @NotNull TimeRangeFilter timeRangeFilter, @NotNull Continuation<? super Unit> continuation);

    @Nullable
    Object deleteRecords(@NotNull KClass<? extends Record> kClass, @NotNull List<String> list, @NotNull List<String> list2, @NotNull Continuation<? super Unit> continuation);

    @Nullable
    Object getChanges(@NotNull String str, @NotNull Continuation<? super ChangesResponse> continuation);

    @Nullable
    Object getChangesToken(@NotNull ChangesTokenRequest changesTokenRequest, @NotNull Continuation<? super String> continuation);

    @NotNull
    PermissionController getPermissionController();

    @Nullable
    Object insertRecords(@NotNull List<? extends Record> list, @NotNull Continuation<? super InsertRecordsResponse> continuation);

    @Nullable
    <T extends Record> Object readRecord(@NotNull KClass<T> kClass, @NotNull String str, @NotNull Continuation<? super ReadRecordResponse<T>> continuation);

    @Nullable
    <T extends Record> Object readRecords(@NotNull ReadRecordsRequest<T> readRecordsRequest, @NotNull Continuation<? super ReadRecordsResponse<T>> continuation);

    @Nullable
    Object updateRecords(@NotNull List<? extends Record> list, @NotNull Continuation<? super Unit> continuation);
}
