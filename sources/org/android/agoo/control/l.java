package org.android.agoo.control;

import android.text.TextUtils;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UTMini;
import java.util.HashMap;
import org.android.agoo.common.AgooConstants;
import org.android.agoo.common.Config;
import org.json.JSONObject;

/* loaded from: classes5.dex */
class l implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f26541a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f26542b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ String f26543c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ boolean f26544d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ NotifManager f26545e;

    l(NotifManager notifManager, String str, String str2, String str3, boolean z2) {
        this.f26545e = notifManager;
        this.f26541a = str;
        this.f26542b = str2;
        this.f26543c = str3;
        this.f26544d = z2;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            HashMap map = new HashMap();
            map.put("thirdTokenType", this.f26541a);
            map.put("token", this.f26542b);
            map.put("appkey", Config.a(this.f26545e.mContext));
            map.put("utdid", com.taobao.accs.utl.j.b(this.f26545e.mContext));
            if (!TextUtils.isEmpty(this.f26543c)) {
                map.put("vendorSdkVersion", this.f26543c);
            }
            ALog.d("NotifManager", "report,utdid=" + com.taobao.accs.utl.j.b(this.f26545e.mContext) + ",regId=" + this.f26542b + ",type=" + this.f26541a, new Object[0]);
            ACCSManager.AccsRequest accsRequest = new ACCSManager.AccsRequest(null, "agooTokenReport", new JSONObject(map).toString().getBytes("UTF-8"), null, null, null, null);
            com.taobao.accs.b accsInstance = ACCSManager.getAccsInstance(this.f26545e.mContext, Config.a(this.f26545e.mContext), Config.c(this.f26545e.mContext));
            String strA = this.f26544d ? accsInstance.a(this.f26545e.mContext, accsRequest) : accsInstance.a(this.f26545e.mContext, accsRequest, new TaoBaseService.ExtraInfo());
            if (ALog.isPrintLog(ALog.Level.D)) {
                ALog.i("NotifManager", "reportThirdPushToken,dataId=" + strA + ",regId=" + this.f26542b + ",type=" + this.f26541a, new Object[0]);
            }
        } catch (Throwable th) {
            UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, "reportThirdPushToken", com.taobao.accs.utl.j.b(this.f26545e.mContext), th.toString());
            if (ALog.isPrintLog(ALog.Level.E)) {
                ALog.e("NotifManager", "[report] is error", th, new Object[0]);
            }
        }
    }
}
