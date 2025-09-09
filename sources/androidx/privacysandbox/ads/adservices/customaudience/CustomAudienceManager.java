package androidx.privacysandbox.ads.adservices.customaudience;

import android.adservices.common.AdData;
import android.adservices.common.AdSelectionSignals;
import android.adservices.common.AdTechIdentifier;
import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresExtension;
import androidx.annotation.RequiresPermission;
import androidx.core.os.OutcomeReceiverKt;
import androidx.privacysandbox.ads.adservices.internal.AdServicesInfo;
import com.facebook.share.internal.ShareConstants;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.CancellableContinuationImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000 \f2\u00020\u0001:\u0002\u000b\fB\u0007\b\u0000¢\u0006\u0002\u0010\u0002J\u0019\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J\u0019\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\tH§@ø\u0001\u0000¢\u0006\u0002\u0010\n\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\r"}, d2 = {"Landroidx/privacysandbox/ads/adservices/customaudience/CustomAudienceManager;", "", "()V", "joinCustomAudience", "", ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID, "Landroidx/privacysandbox/ads/adservices/customaudience/JoinCustomAudienceRequest;", "(Landroidx/privacysandbox/ads/adservices/customaudience/JoinCustomAudienceRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "leaveCustomAudience", "Landroidx/privacysandbox/ads/adservices/customaudience/LeaveCustomAudienceRequest;", "(Landroidx/privacysandbox/ads/adservices/customaudience/LeaveCustomAudienceRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Api33Ext4Impl", "Companion", "ads-adservices_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public abstract class CustomAudienceManager {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Landroidx/privacysandbox/ads/adservices/customaudience/CustomAudienceManager$Companion;", "", "()V", "obtain", "Landroidx/privacysandbox/ads/adservices/customaudience/CustomAudienceManager;", com.umeng.analytics.pro.f.X, "Landroid/content/Context;", "ads-adservices_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        @JvmStatic
        @SuppressLint({"NewApi", "ClassVerificationFailure"})
        @Nullable
        public final CustomAudienceManager obtain(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            if (AdServicesInfo.INSTANCE.version() >= 4) {
                return new Api33Ext4Impl(context);
            }
            return null;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @JvmStatic
    @SuppressLint({"NewApi", "ClassVerificationFailure"})
    @Nullable
    public static final CustomAudienceManager obtain(@NotNull Context context) {
        return INSTANCE.obtain(context);
    }

    @RequiresPermission("android.permission.ACCESS_ADSERVICES_CUSTOM_AUDIENCE")
    @Nullable
    public abstract Object joinCustomAudience(@NotNull JoinCustomAudienceRequest joinCustomAudienceRequest, @NotNull Continuation<? super Unit> continuation);

    @RequiresPermission("android.permission.ACCESS_ADSERVICES_CUSTOM_AUDIENCE")
    @Nullable
    public abstract Object leaveCustomAudience(@NotNull LeaveCustomAudienceRequest leaveCustomAudienceRequest, @NotNull Continuation<? super Unit> continuation);

    @Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0003\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u001c\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\tH\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\u000fH\u0002J\u0014\u0010\u0010\u001a\u0004\u0018\u00010\u00112\b\u0010\u000b\u001a\u0004\u0018\u00010\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0015\u001a\u00020\u0019H\u0002J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0015\u001a\u00020\u001cH\u0002J\u0014\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\b\u0010\u000b\u001a\u0004\u0018\u00010\u001fH\u0002J\u0019\u0010 \u001a\u00020!2\u0006\u0010\u0015\u001a\u00020\u0019H\u0097@ø\u0001\u0000¢\u0006\u0002\u0010\"J\u0019\u0010#\u001a\u00020!2\u0006\u0010\u0015\u001a\u00020\u001cH\u0097@ø\u0001\u0000¢\u0006\u0002\u0010$R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006%"}, d2 = {"Landroidx/privacysandbox/ads/adservices/customaudience/CustomAudienceManager$Api33Ext4Impl;", "Landroidx/privacysandbox/ads/adservices/customaudience/CustomAudienceManager;", com.umeng.analytics.pro.f.X, "Landroid/content/Context;", "(Landroid/content/Context;)V", "customAudienceManager", "Landroid/adservices/customaudience/CustomAudienceManager;", "(Landroid/adservices/customaudience/CustomAudienceManager;)V", "convertAdData", "", "Landroid/adservices/common/AdData;", "input", "Landroidx/privacysandbox/ads/adservices/common/AdData;", "convertAdTechIdentifier", "Landroid/adservices/common/AdTechIdentifier;", "Landroidx/privacysandbox/ads/adservices/common/AdTechIdentifier;", "convertBiddingSignals", "Landroid/adservices/common/AdSelectionSignals;", "Landroidx/privacysandbox/ads/adservices/common/AdSelectionSignals;", "convertCustomAudience", "Landroid/adservices/customaudience/CustomAudience;", ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID, "Landroidx/privacysandbox/ads/adservices/customaudience/CustomAudience;", "convertJoinRequest", "Landroid/adservices/customaudience/JoinCustomAudienceRequest;", "Landroidx/privacysandbox/ads/adservices/customaudience/JoinCustomAudienceRequest;", "convertLeaveRequest", "Landroid/adservices/customaudience/LeaveCustomAudienceRequest;", "Landroidx/privacysandbox/ads/adservices/customaudience/LeaveCustomAudienceRequest;", "convertTrustedSignals", "Landroid/adservices/customaudience/TrustedBiddingData;", "Landroidx/privacysandbox/ads/adservices/customaudience/TrustedBiddingData;", "joinCustomAudience", "", "(Landroidx/privacysandbox/ads/adservices/customaudience/JoinCustomAudienceRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "leaveCustomAudience", "(Landroidx/privacysandbox/ads/adservices/customaudience/LeaveCustomAudienceRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "ads-adservices_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SuppressLint({"ClassVerificationFailure", "NewApi"})
    @RequiresExtension(extension = 1000000, version = 4)
    @SourceDebugExtension({"SMAP\nCustomAudienceManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CustomAudienceManager.kt\nandroidx/privacysandbox/ads/adservices/customaudience/CustomAudienceManager$Api33Ext4Impl\n+ 2 CancellableContinuation.kt\nkotlinx/coroutines/CancellableContinuationKt\n*L\n1#1,220:1\n314#2,11:221\n314#2,11:232\n*S KotlinDebug\n*F\n+ 1 CustomAudienceManager.kt\nandroidx/privacysandbox/ads/adservices/customaudience/CustomAudienceManager$Api33Ext4Impl\n*L\n111#1:221,11\n123#1:232,11\n*E\n"})
    private static final class Api33Ext4Impl extends CustomAudienceManager {

        @NotNull
        private final android.adservices.customaudience.CustomAudienceManager customAudienceManager;

        public Api33Ext4Impl(@NotNull android.adservices.customaudience.CustomAudienceManager customAudienceManager) {
            Intrinsics.checkNotNullParameter(customAudienceManager, "customAudienceManager");
            this.customAudienceManager = customAudienceManager;
        }

        private final List<AdData> convertAdData(List<androidx.privacysandbox.ads.adservices.common.AdData> input) {
            ArrayList arrayList = new ArrayList();
            for (androidx.privacysandbox.ads.adservices.common.AdData adData : input) {
                AdData adDataBuild = v.a().setMetadata(adData.getMetadata()).setRenderUri(adData.getRenderUri()).build();
                Intrinsics.checkNotNullExpressionValue(adDataBuild, "Builder()\n              …                 .build()");
                arrayList.add(adDataBuild);
            }
            return arrayList;
        }

        private final AdTechIdentifier convertAdTechIdentifier(androidx.privacysandbox.ads.adservices.common.AdTechIdentifier input) {
            AdTechIdentifier adTechIdentifierFromString = AdTechIdentifier.fromString(input.getIdentifier());
            Intrinsics.checkNotNullExpressionValue(adTechIdentifierFromString, "fromString(input.identifier)");
            return adTechIdentifierFromString;
        }

        private final AdSelectionSignals convertBiddingSignals(androidx.privacysandbox.ads.adservices.common.AdSelectionSignals input) {
            if (input == null) {
                return null;
            }
            return AdSelectionSignals.fromString(input.getSignals());
        }

        private final android.adservices.customaudience.CustomAudience convertCustomAudience(CustomAudience request) {
            android.adservices.customaudience.CustomAudience customAudienceBuild = r.a().setActivationTime(request.getActivationTime()).setAds(convertAdData(request.getAds())).setBiddingLogicUri(request.getBiddingLogicUri()).setBuyer(convertAdTechIdentifier(request.getBuyer())).setDailyUpdateUri(request.getDailyUpdateUri()).setExpirationTime(request.getExpirationTime()).setName(request.getName()).setTrustedBiddingData(convertTrustedSignals(request.getTrustedBiddingSignals())).setUserBiddingSignals(convertBiddingSignals(request.getUserBiddingSignals())).build();
            Intrinsics.checkNotNullExpressionValue(customAudienceBuild, "Builder()\n              …\n                .build()");
            return customAudienceBuild;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final android.adservices.customaudience.JoinCustomAudienceRequest convertJoinRequest(JoinCustomAudienceRequest request) {
            android.adservices.customaudience.JoinCustomAudienceRequest joinCustomAudienceRequestBuild = t.a().setCustomAudience(convertCustomAudience(request.getCustomAudience())).build();
            Intrinsics.checkNotNullExpressionValue(joinCustomAudienceRequestBuild, "Builder()\n              …\n                .build()");
            return joinCustomAudienceRequestBuild;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final android.adservices.customaudience.LeaveCustomAudienceRequest convertLeaveRequest(LeaveCustomAudienceRequest request) {
            android.adservices.customaudience.LeaveCustomAudienceRequest leaveCustomAudienceRequestBuild = s.a().setBuyer(convertAdTechIdentifier(request.getBuyer())).setName(request.getName()).build();
            Intrinsics.checkNotNullExpressionValue(leaveCustomAudienceRequestBuild, "Builder()\n              …\n                .build()");
            return leaveCustomAudienceRequestBuild;
        }

        private final android.adservices.customaudience.TrustedBiddingData convertTrustedSignals(TrustedBiddingData input) {
            if (input == null) {
                return null;
            }
            return u.a().setTrustedBiddingKeys(input.getTrustedBiddingKeys()).setTrustedBiddingUri(input.getTrustedBiddingUri()).build();
        }

        @Override // androidx.privacysandbox.ads.adservices.customaudience.CustomAudienceManager
        @RequiresPermission("android.permission.ACCESS_ADSERVICES_CUSTOM_AUDIENCE")
        @DoNotInline
        @Nullable
        public Object joinCustomAudience(@NotNull JoinCustomAudienceRequest joinCustomAudienceRequest, @NotNull Continuation<? super Unit> continuation) {
            CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
            cancellableContinuationImpl.initCancellability();
            this.customAudienceManager.joinCustomAudience(convertJoinRequest(joinCustomAudienceRequest), new androidx.media3.exoplayer.dash.offline.a(), OutcomeReceiverKt.asOutcomeReceiver(cancellableContinuationImpl));
            Object result = cancellableContinuationImpl.getResult();
            if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended(continuation);
            }
            return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
        }

        @Override // androidx.privacysandbox.ads.adservices.customaudience.CustomAudienceManager
        @RequiresPermission("android.permission.ACCESS_ADSERVICES_CUSTOM_AUDIENCE")
        @DoNotInline
        @Nullable
        public Object leaveCustomAudience(@NotNull LeaveCustomAudienceRequest leaveCustomAudienceRequest, @NotNull Continuation<? super Unit> continuation) {
            CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
            cancellableContinuationImpl.initCancellability();
            this.customAudienceManager.leaveCustomAudience(convertLeaveRequest(leaveCustomAudienceRequest), new androidx.media3.exoplayer.dash.offline.a(), OutcomeReceiverKt.asOutcomeReceiver(cancellableContinuationImpl));
            Object result = cancellableContinuationImpl.getResult();
            if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended(continuation);
            }
            return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public Api33Ext4Impl(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            Object systemService = context.getSystemService((Class<Object>) l.a());
            Intrinsics.checkNotNullExpressionValue(systemService, "context.getSystemService…:class.java\n            )");
            this(w.a(systemService));
        }
    }
}
