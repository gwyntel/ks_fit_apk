package com.taobao.accs.net;

import android.text.TextUtils;
import anet.channel.IAuth;
import anet.channel.RequestCb;
import anet.channel.bytes.ByteArray;
import anet.channel.statist.RequestStatistic;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.taobao.accs.net.k;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UtilityImpl;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
class u implements RequestCb {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ IAuth.AuthCallback f20261a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ k.a f20262b;

    u(k.a aVar, IAuth.AuthCallback authCallback) {
        this.f20262b = aVar;
        this.f20261a = authCallback;
    }

    @Override // anet.channel.RequestCb
    public void onDataReceive(ByteArray byteArray, boolean z2) {
    }

    @Override // anet.channel.RequestCb
    public void onFinish(int i2, String str, RequestStatistic requestStatistic) {
        if (i2 < 0) {
            ALog.e(this.f20262b.f20239c, "auth onFinish", HiAnalyticsConstant.HaKey.BI_KEY_RESULT, Integer.valueOf(i2));
            this.f20261a.onAuthFail(i2, "onFinish auth fail");
        }
    }

    @Override // anet.channel.RequestCb
    public void onResponseCode(int i2, Map<String, List<String>> map) {
        ALog.e(this.f20262b.f20239c, "auth", "httpStatusCode", Integer.valueOf(i2));
        if (i2 == 200) {
            this.f20261a.onAuthSuccess();
            if (this.f20262b.f20240d instanceof k) {
                ((k) this.f20262b.f20240d).o();
            }
        } else {
            this.f20261a.onAuthFail(i2, "auth fail");
        }
        Map<String, String> mapA = UtilityImpl.a(map);
        ALog.d(this.f20262b.f20239c, "auth", "header", mapA);
        String str = mapA.get("x-at");
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.f20262b.f20240d.f20201k = str;
    }
}
