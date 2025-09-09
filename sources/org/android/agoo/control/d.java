package org.android.agoo.control;

import android.text.TextUtils;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.BaseMonitor;
import org.android.agoo.common.AgooConstants;
import org.android.agoo.common.Config;
import org.android.agoo.message.MessageService;
import org.json.JSONObject;

/* loaded from: classes5.dex */
class d implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ byte[] f26527a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ boolean f26528b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ AgooFactory f26529c;

    d(AgooFactory agooFactory, byte[] bArr, boolean z2) {
        this.f26529c = agooFactory;
        this.f26527a = bArr;
        this.f26528b = z2;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            String str = new String(this.f26527a, "utf-8");
            if (TextUtils.isEmpty(str)) {
                com.taobao.accs.utl.k.a("accs", BaseMonitor.COUNT_AGOO_FAIL_ACK, "msg==null", 0.0d);
                return;
            }
            ALog.i("AgooFactory", "message = " + str, new Object[0]);
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString("api");
            String string2 = jSONObject.getString("id");
            String string3 = TextUtils.equals(string, "agooReport") ? jSONObject.getString("status") : null;
            if (TextUtils.equals(string, AgooConstants.AGOO_SERVICE_AGOOACK)) {
                com.taobao.accs.utl.k.a("accs", BaseMonitor.COUNT_AGOO_SUCCESS_ACK, "handlerACKMessage", 0.0d);
            }
            if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2) && !TextUtils.isEmpty(string3)) {
                if (ALog.isPrintLog(ALog.Level.I)) {
                    ALog.i("AgooFactory", "updateMsg data begin,api=" + string + ",id=" + string2 + ",status=" + string3 + ",reportTimes=" + Config.f(AgooFactory.mContext), new Object[0]);
                }
                if (TextUtils.equals(string, "agooReport")) {
                    if (TextUtils.equals(string3, "4") && this.f26528b) {
                        this.f26529c.messageService.a(string2, "1");
                    } else if ((TextUtils.equals(string3, MessageService.MSG_ACCS_NOTIFY_CLICK) || TextUtils.equals(string3, MessageService.MSG_ACCS_NOTIFY_DISMISS)) && this.f26528b) {
                        this.f26529c.messageService.a(string2, MessageService.MSG_DB_COMPLETE);
                    }
                    com.taobao.accs.utl.k.a("accs", BaseMonitor.COUNT_AGOO_SUCCESS_ACK, string3, 0.0d);
                    return;
                }
                return;
            }
            com.taobao.accs.utl.k.a("accs", BaseMonitor.COUNT_AGOO_FAIL_ACK, "json key null", 0.0d);
        } catch (Throwable th) {
            ALog.e("AgooFactory", "updateMsg get data error,e=" + th, new Object[0]);
            com.taobao.accs.utl.k.a("accs", BaseMonitor.COUNT_AGOO_FAIL_ACK, "json exception", 0.0d);
        }
    }
}
