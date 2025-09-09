package com.kingsmith.es11;

import android.os.Handler;
import android.os.Looper;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.google.gson.Gson;
import com.kingsmith.es11.Es11Plugin$heartRateChangeListener$1;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.yc.utesdk.bean.HeartRateBestValueInfo;
import com.yc.utesdk.bean.HeartRateHourBestValueInfo;
import com.yc.utesdk.bean.HeartRateInfo;
import com.yc.utesdk.bean.HeartRateRestInfo;
import com.yc.utesdk.listener.HeartRateChangeListener;
import com.yc.utesdk.log.LogUtils;
import io.flutter.plugin.common.MethodChannel;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000A\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016J\u0012\u0010\u0006\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0007H\u0016J\u0012\u0010\b\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\tH\u0016J\u0012\u0010\n\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u000bH\u0016J\b\u0010\f\u001a\u00020\u0003H\u0016J\u001a\u0010\r\u001a\u00020\u00032\u0010\u0010\u000e\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0018\u00010\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0003H\u0016J\u0018\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0003H\u0016J\u001a\u0010\u0017\u001a\u00020\u00032\u0010\u0010\u000e\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\u000fH\u0016J\b\u0010\u0018\u001a\u00020\u0003H\u0016¨\u0006\u0019"}, d2 = {"com/kingsmith/es11/Es11Plugin$heartRateChangeListener$1", "Lcom/yc/utesdk/listener/HeartRateChangeListener;", "onHeartRateBestValue", "", XiaomiOAuthConstants.EXTRA_INFO, "Lcom/yc/utesdk/bean/HeartRateBestValueInfo;", "onHeartRateHourRestBestValue", "Lcom/yc/utesdk/bean/HeartRateHourBestValueInfo;", "onHeartRateRealTime", "Lcom/yc/utesdk/bean/HeartRateInfo;", "onHeartRateRest", "Lcom/yc/utesdk/bean/HeartRateRestInfo;", "onHeartRateRestSyncFail", "onHeartRateRestSyncSuccess", AlinkConstants.KEY_LIST, "", "onHeartRateRestSyncing", "onHeartRateStatus", "result", "", "status", "", "onHeartRateSyncFail", "onHeartRateSyncSuccess", "onHeartRateSyncing", "es11_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class Es11Plugin$heartRateChangeListener$1 implements HeartRateChangeListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Es11Plugin f18567a;

    Es11Plugin$heartRateChangeListener$1(Es11Plugin es11Plugin) {
        this.f18567a = es11Plugin;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onHeartRateRealTime$lambda$0(Es11Plugin this$0, Map info2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(info2, "$info");
        MethodChannel methodChannel = this$0.channel;
        if (methodChannel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("channel");
            methodChannel = null;
        }
        methodChannel.invokeMethod("onHeartRate", info2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onHeartRateSyncFail$lambda$3(Es11Plugin this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        MethodChannel methodChannel = this$0.channel;
        if (methodChannel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("channel");
            methodChannel = null;
        }
        methodChannel.invokeMethod("onHeartRateSyncFail", null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onHeartRateSyncSuccess$lambda$1(Es11Plugin this$0, List list) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        MethodChannel methodChannel = this$0.channel;
        if (methodChannel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("channel");
            methodChannel = null;
        }
        methodChannel.invokeMethod("onHeartRateSyncSuccess", new Gson().toJson(list));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onHeartRateSyncing$lambda$2(Es11Plugin this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        MethodChannel methodChannel = this$0.channel;
        if (methodChannel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("channel");
            methodChannel = null;
        }
        methodChannel.invokeMethod("onHeartRateSyncing", null);
    }

    @Override // com.yc.utesdk.listener.HeartRateChangeListener
    public void onHeartRateBestValue(@Nullable HeartRateBestValueInfo info2) {
        LogUtils.i("onHeartRateBestValue info =" + new Gson().toJson(info2));
    }

    @Override // com.yc.utesdk.listener.HeartRateChangeListener
    public void onHeartRateHourRestBestValue(@Nullable HeartRateHourBestValueInfo info2) {
        LogUtils.i("onHeartRateHourRestBestValue info =" + new Gson().toJson(info2));
    }

    @Override // com.yc.utesdk.listener.HeartRateChangeListener
    public void onHeartRateRealTime(@Nullable HeartRateInfo info2) {
        LogUtils.i("onHeartRateRealTime info =" + new Gson().toJson(info2));
        final Map mapMapOf = MapsKt.mapOf(TuplesKt.to("heartRate", info2 != null ? Integer.valueOf(info2.getHeartRate()) : null));
        Handler handler = new Handler(Looper.getMainLooper());
        final Es11Plugin es11Plugin = this.f18567a;
        handler.post(new Runnable() { // from class: f0.i
            @Override // java.lang.Runnable
            public final void run() {
                Es11Plugin$heartRateChangeListener$1.onHeartRateRealTime$lambda$0(es11Plugin, mapMapOf);
            }
        });
    }

    @Override // com.yc.utesdk.listener.HeartRateChangeListener
    public void onHeartRateRest(@Nullable HeartRateRestInfo info2) {
        LogUtils.i("onHeartRateRest info =" + new Gson().toJson(info2));
    }

    @Override // com.yc.utesdk.listener.HeartRateChangeListener
    public void onHeartRateRestSyncFail() {
        LogUtils.i("onHeartRateRestSyncFail");
    }

    @Override // com.yc.utesdk.listener.HeartRateChangeListener
    public void onHeartRateRestSyncSuccess(@Nullable List<? extends HeartRateRestInfo> list) {
        LogUtils.i("onHeartRateRestSyncSuccess list =" + new Gson().toJson(list));
    }

    @Override // com.yc.utesdk.listener.HeartRateChangeListener
    public void onHeartRateRestSyncing() {
        LogUtils.i("onHeartRateRestSyncing");
    }

    @Override // com.yc.utesdk.listener.HeartRateChangeListener
    public void onHeartRateStatus(boolean result, int status) {
        LogUtils.i("onHeartRateStatus result =" + result + ",status =" + status);
    }

    @Override // com.yc.utesdk.listener.HeartRateChangeListener
    public void onHeartRateSyncFail() {
        LogUtils.i("onHeartRateSyncFail");
        Handler handler = new Handler(Looper.getMainLooper());
        final Es11Plugin es11Plugin = this.f18567a;
        handler.post(new Runnable() { // from class: f0.j
            @Override // java.lang.Runnable
            public final void run() {
                Es11Plugin$heartRateChangeListener$1.onHeartRateSyncFail$lambda$3(es11Plugin);
            }
        });
    }

    @Override // com.yc.utesdk.listener.HeartRateChangeListener
    public void onHeartRateSyncSuccess(@Nullable final List<? extends HeartRateInfo> list) {
        Handler handler = new Handler(Looper.getMainLooper());
        final Es11Plugin es11Plugin = this.f18567a;
        handler.post(new Runnable() { // from class: f0.h
            @Override // java.lang.Runnable
            public final void run() {
                Es11Plugin$heartRateChangeListener$1.onHeartRateSyncSuccess$lambda$1(es11Plugin, list);
            }
        });
    }

    @Override // com.yc.utesdk.listener.HeartRateChangeListener
    public void onHeartRateSyncing() {
        LogUtils.i("onHeartRateSyncing");
        Handler handler = new Handler(Looper.getMainLooper());
        final Es11Plugin es11Plugin = this.f18567a;
        handler.post(new Runnable() { // from class: f0.g
            @Override // java.lang.Runnable
            public final void run() {
                Es11Plugin$heartRateChangeListener$1.onHeartRateSyncing$lambda$2(es11Plugin);
            }
        });
    }
}
