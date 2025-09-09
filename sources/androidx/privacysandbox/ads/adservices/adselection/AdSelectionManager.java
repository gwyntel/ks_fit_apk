package androidx.privacysandbox.ads.adservices.adselection;

import android.adservices.common.AdSelectionSignals;
import android.adservices.common.AdTechIdentifier;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresExtension;
import androidx.annotation.RequiresPermission;
import androidx.core.os.OutcomeReceiverKt;
import androidx.privacysandbox.ads.adservices.internal.AdServicesInfo;
import com.facebook.share.internal.ShareConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000 \u000e2\u00020\u0001:\u0002\r\u000eB\u0007\b\u0000¢\u0006\u0002\u0010\u0002J\u0019\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J\u0019\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH§@ø\u0001\u0000¢\u0006\u0002\u0010\f\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000f"}, d2 = {"Landroidx/privacysandbox/ads/adservices/adselection/AdSelectionManager;", "", "()V", "reportImpression", "", "reportImpressionRequest", "Landroidx/privacysandbox/ads/adservices/adselection/ReportImpressionRequest;", "(Landroidx/privacysandbox/ads/adservices/adselection/ReportImpressionRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "selectAds", "Landroidx/privacysandbox/ads/adservices/adselection/AdSelectionOutcome;", "adSelectionConfig", "Landroidx/privacysandbox/ads/adservices/adselection/AdSelectionConfig;", "(Landroidx/privacysandbox/ads/adservices/adselection/AdSelectionConfig;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Api33Ext4Impl", "Companion", "ads-adservices_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public abstract class AdSelectionManager {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Landroidx/privacysandbox/ads/adservices/adselection/AdSelectionManager$Companion;", "", "()V", "obtain", "Landroidx/privacysandbox/ads/adservices/adselection/AdSelectionManager;", com.umeng.analytics.pro.f.X, "Landroid/content/Context;", "ads-adservices_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        @JvmStatic
        @SuppressLint({"NewApi", "ClassVerificationFailure"})
        @Nullable
        public final AdSelectionManager obtain(@NotNull Context context) {
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
    public static final AdSelectionManager obtain(@NotNull Context context) {
        return INSTANCE.obtain(context);
    }

    @RequiresPermission("android.permission.ACCESS_ADSERVICES_CUSTOM_AUDIENCE")
    @Nullable
    public abstract Object reportImpression(@NotNull ReportImpressionRequest reportImpressionRequest, @NotNull Continuation<? super Unit> continuation);

    @RequiresPermission("android.permission.ACCESS_ADSERVICES_CUSTOM_AUDIENCE")
    @Nullable
    public abstract Object selectAds(@NotNull AdSelectionConfig adSelectionConfig, @NotNull Continuation<? super AdSelectionOutcome> continuation);

    /* JADX INFO: Access modifiers changed from: private */
    @Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\b\u0003\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000eH\u0002J\u001c\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u0002J*\u0010\u0015\u001a\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0006\u0012\u0004\u0018\u00010\r0\u00162\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u000e0\u0016H\u0002J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\n\u001a\u00020\u0019H\u0002J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0019\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0019H\u0097@ø\u0001\u0000¢\u0006\u0002\u0010!J\u0019\u0010\"\u001a\u00020\u001b2\u0006\u0010#\u001a\u00020\u000bH\u0097@ø\u0001\u0000¢\u0006\u0002\u0010$J\u0019\u0010%\u001a\u00020\u001d2\u0006\u0010#\u001a\u00020\tH\u0083@ø\u0001\u0000¢\u0006\u0002\u0010&R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006'"}, d2 = {"Landroidx/privacysandbox/ads/adservices/adselection/AdSelectionManager$Api33Ext4Impl;", "Landroidx/privacysandbox/ads/adservices/adselection/AdSelectionManager;", com.umeng.analytics.pro.f.X, "Landroid/content/Context;", "(Landroid/content/Context;)V", "mAdSelectionManager", "Landroid/adservices/adselection/AdSelectionManager;", "(Landroid/adservices/adselection/AdSelectionManager;)V", "convertAdSelectionConfig", "Landroid/adservices/adselection/AdSelectionConfig;", ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID, "Landroidx/privacysandbox/ads/adservices/adselection/AdSelectionConfig;", "convertAdSelectionSignals", "Landroid/adservices/common/AdSelectionSignals;", "Landroidx/privacysandbox/ads/adservices/common/AdSelectionSignals;", "convertBuyers", "", "Landroid/adservices/common/AdTechIdentifier;", "buyers", "", "Landroidx/privacysandbox/ads/adservices/common/AdTechIdentifier;", "convertPerBuyerSignals", "", "convertReportImpressionRequest", "Landroid/adservices/adselection/ReportImpressionRequest;", "Landroidx/privacysandbox/ads/adservices/adselection/ReportImpressionRequest;", "convertResponse", "Landroidx/privacysandbox/ads/adservices/adselection/AdSelectionOutcome;", "response", "Landroid/adservices/adselection/AdSelectionOutcome;", "reportImpression", "", "reportImpressionRequest", "(Landroidx/privacysandbox/ads/adservices/adselection/ReportImpressionRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "selectAds", "adSelectionConfig", "(Landroidx/privacysandbox/ads/adservices/adselection/AdSelectionConfig;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "selectAdsInternal", "(Landroid/adservices/adselection/AdSelectionConfig;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "ads-adservices_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SuppressLint({"NewApi", "ClassVerificationFailure"})
    @RequiresExtension(extension = 1000000, version = 4)
    @SourceDebugExtension({"SMAP\nAdSelectionManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AdSelectionManager.kt\nandroidx/privacysandbox/ads/adservices/adselection/AdSelectionManager$Api33Ext4Impl\n+ 2 CancellableContinuation.kt\nkotlinx/coroutines/CancellableContinuationKt\n*L\n1#1,199:1\n314#2,11:200\n314#2,11:211\n*S KotlinDebug\n*F\n+ 1 AdSelectionManager.kt\nandroidx/privacysandbox/ads/adservices/adselection/AdSelectionManager$Api33Ext4Impl\n*L\n98#1:200,11\n162#1:211,11\n*E\n"})
    static final class Api33Ext4Impl extends AdSelectionManager {

        @NotNull
        private final android.adservices.adselection.AdSelectionManager mAdSelectionManager;

        public Api33Ext4Impl(@NotNull android.adservices.adselection.AdSelectionManager mAdSelectionManager) {
            Intrinsics.checkNotNullParameter(mAdSelectionManager, "mAdSelectionManager");
            this.mAdSelectionManager = mAdSelectionManager;
        }

        private final android.adservices.adselection.AdSelectionConfig convertAdSelectionConfig(AdSelectionConfig request) {
            android.adservices.adselection.AdSelectionConfig adSelectionConfigBuild = k.a().setAdSelectionSignals(convertAdSelectionSignals(request.getAdSelectionSignals())).setCustomAudienceBuyers(convertBuyers(request.getCustomAudienceBuyers())).setDecisionLogicUri(request.getDecisionLogicUri()).setSeller(AdTechIdentifier.fromString(request.getSeller().getIdentifier())).setPerBuyerSignals(convertPerBuyerSignals(request.getPerBuyerSignals())).setSellerSignals(convertAdSelectionSignals(request.getSellerSignals())).setTrustedScoringSignalsUri(request.getTrustedScoringSignalsUri()).build();
            Intrinsics.checkNotNullExpressionValue(adSelectionConfigBuild, "Builder()\n              …\n                .build()");
            return adSelectionConfigBuild;
        }

        private final AdSelectionSignals convertAdSelectionSignals(androidx.privacysandbox.ads.adservices.common.AdSelectionSignals request) {
            AdSelectionSignals adSelectionSignalsFromString = AdSelectionSignals.fromString(request.getSignals());
            Intrinsics.checkNotNullExpressionValue(adSelectionSignalsFromString, "fromString(request.signals)");
            return adSelectionSignalsFromString;
        }

        private final List<AdTechIdentifier> convertBuyers(List<androidx.privacysandbox.ads.adservices.common.AdTechIdentifier> buyers) {
            ArrayList arrayList = new ArrayList();
            Iterator<androidx.privacysandbox.ads.adservices.common.AdTechIdentifier> it = buyers.iterator();
            while (it.hasNext()) {
                AdTechIdentifier adTechIdentifierFromString = AdTechIdentifier.fromString(it.next().getIdentifier());
                Intrinsics.checkNotNullExpressionValue(adTechIdentifierFromString, "fromString(buyer.identifier)");
                arrayList.add(adTechIdentifierFromString);
            }
            return arrayList;
        }

        private final Map<AdTechIdentifier, AdSelectionSignals> convertPerBuyerSignals(Map<androidx.privacysandbox.ads.adservices.common.AdTechIdentifier, androidx.privacysandbox.ads.adservices.common.AdSelectionSignals> request) {
            AdSelectionSignals adSelectionSignalsConvertAdSelectionSignals;
            HashMap map = new HashMap();
            for (androidx.privacysandbox.ads.adservices.common.AdTechIdentifier adTechIdentifier : request.keySet()) {
                AdTechIdentifier adTechIdentifierFromString = AdTechIdentifier.fromString(adTechIdentifier.getIdentifier());
                Intrinsics.checkNotNullExpressionValue(adTechIdentifierFromString, "fromString(key.identifier)");
                if (request.get(adTechIdentifier) != null) {
                    androidx.privacysandbox.ads.adservices.common.AdSelectionSignals adSelectionSignals = request.get(adTechIdentifier);
                    Intrinsics.checkNotNull(adSelectionSignals);
                    adSelectionSignalsConvertAdSelectionSignals = convertAdSelectionSignals(adSelectionSignals);
                } else {
                    adSelectionSignalsConvertAdSelectionSignals = null;
                }
                map.put(adTechIdentifierFromString, adSelectionSignalsConvertAdSelectionSignals);
            }
            return map;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final android.adservices.adselection.ReportImpressionRequest convertReportImpressionRequest(ReportImpressionRequest request) {
            j.a();
            return i.a(request.getAdSelectionId(), convertAdSelectionConfig(request.getAdSelectionConfig()));
        }

        private final AdSelectionOutcome convertResponse(android.adservices.adselection.AdSelectionOutcome response) {
            long adSelectionId = response.getAdSelectionId();
            Uri renderUri = response.getRenderUri();
            Intrinsics.checkNotNullExpressionValue(renderUri, "response.renderUri");
            return new AdSelectionOutcome(adSelectionId, renderUri);
        }

        /* JADX INFO: Access modifiers changed from: private */
        @RequiresPermission("android.permission.ACCESS_ADSERVICES_CUSTOM_AUDIENCE")
        public final Object selectAdsInternal(android.adservices.adselection.AdSelectionConfig adSelectionConfig, Continuation<? super android.adservices.adselection.AdSelectionOutcome> continuation) {
            CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
            cancellableContinuationImpl.initCancellability();
            this.mAdSelectionManager.selectAds(adSelectionConfig, new androidx.media3.exoplayer.dash.offline.a(), OutcomeReceiverKt.asOutcomeReceiver(cancellableContinuationImpl));
            Object result = cancellableContinuationImpl.getResult();
            if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended(continuation);
            }
            return result;
        }

        @Override // androidx.privacysandbox.ads.adservices.adselection.AdSelectionManager
        @RequiresPermission("android.permission.ACCESS_ADSERVICES_CUSTOM_AUDIENCE")
        @DoNotInline
        @Nullable
        public Object reportImpression(@NotNull ReportImpressionRequest reportImpressionRequest, @NotNull Continuation<? super Unit> continuation) {
            CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
            cancellableContinuationImpl.initCancellability();
            this.mAdSelectionManager.reportImpression(convertReportImpressionRequest(reportImpressionRequest), new androidx.media3.exoplayer.dash.offline.a(), OutcomeReceiverKt.asOutcomeReceiver(cancellableContinuationImpl));
            Object result = cancellableContinuationImpl.getResult();
            if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended(continuation);
            }
            return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
        }

        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        @Override // androidx.privacysandbox.ads.adservices.adselection.AdSelectionManager
        @androidx.annotation.RequiresPermission("android.permission.ACCESS_ADSERVICES_CUSTOM_AUDIENCE")
        @androidx.annotation.DoNotInline
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public java.lang.Object selectAds(@org.jetbrains.annotations.NotNull androidx.privacysandbox.ads.adservices.adselection.AdSelectionConfig r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super androidx.privacysandbox.ads.adservices.adselection.AdSelectionOutcome> r6) throws java.lang.Throwable {
            /*
                r4 = this;
                boolean r0 = r6 instanceof androidx.privacysandbox.ads.adservices.adselection.AdSelectionManager$Api33Ext4Impl$selectAds$1
                if (r0 == 0) goto L13
                r0 = r6
                androidx.privacysandbox.ads.adservices.adselection.AdSelectionManager$Api33Ext4Impl$selectAds$1 r0 = (androidx.privacysandbox.ads.adservices.adselection.AdSelectionManager$Api33Ext4Impl$selectAds$1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                androidx.privacysandbox.ads.adservices.adselection.AdSelectionManager$Api33Ext4Impl$selectAds$1 r0 = new androidx.privacysandbox.ads.adservices.adselection.AdSelectionManager$Api33Ext4Impl$selectAds$1
                r0.<init>(r4, r6)
            L18:
                java.lang.Object r6 = r0.result
                java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r2 = r0.label
                r3 = 1
                if (r2 == 0) goto L35
                if (r2 != r3) goto L2d
                java.lang.Object r5 = r0.L$0
                androidx.privacysandbox.ads.adservices.adselection.AdSelectionManager$Api33Ext4Impl r5 = (androidx.privacysandbox.ads.adservices.adselection.AdSelectionManager.Api33Ext4Impl) r5
                kotlin.ResultKt.throwOnFailure(r6)
                goto L48
            L2d:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                r5.<init>(r6)
                throw r5
            L35:
                kotlin.ResultKt.throwOnFailure(r6)
                android.adservices.adselection.AdSelectionConfig r5 = r4.convertAdSelectionConfig(r5)
                r0.L$0 = r4
                r0.label = r3
                java.lang.Object r6 = r4.selectAdsInternal(r5, r0)
                if (r6 != r1) goto L47
                return r1
            L47:
                r5 = r4
            L48:
                android.adservices.adselection.AdSelectionOutcome r6 = androidx.privacysandbox.ads.adservices.adselection.n.a(r6)
                androidx.privacysandbox.ads.adservices.adselection.AdSelectionOutcome r5 = r5.convertResponse(r6)
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.privacysandbox.ads.adservices.adselection.AdSelectionManager.Api33Ext4Impl.selectAds(androidx.privacysandbox.ads.adservices.adselection.AdSelectionConfig, kotlin.coroutines.Continuation):java.lang.Object");
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public Api33Ext4Impl(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            Object systemService = context.getSystemService((Class<Object>) a.a());
            Intrinsics.checkNotNullExpressionValue(systemService, "context.getSystemService…:class.java\n            )");
            this(l.a(systemService));
        }
    }
}
