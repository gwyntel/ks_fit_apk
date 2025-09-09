package com.taobao.agoo;

import android.content.Context;
import android.text.TextUtils;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.IAgooAppReceiver;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.accs.utl.l;
import java.util.Map;
import org.android.agoo.common.Config;

/* loaded from: classes4.dex */
class g implements IAgooAppReceiver {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f20449a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ Context f20450b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ IRegister f20451c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ String f20452d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ String f20453e;

    /* renamed from: f, reason: collision with root package name */
    final /* synthetic */ com.taobao.accs.b f20454f;

    g(Context context, Context context2, IRegister iRegister, String str, String str2, com.taobao.accs.b bVar) {
        this.f20449a = context;
        this.f20450b = context2;
        this.f20451c = iRegister;
        this.f20452d = str;
        this.f20453e = str2;
        this.f20454f = bVar;
    }

    @Override // com.taobao.accs.IAppReceiver
    public Map<String, String> getAllServices() {
        return null;
    }

    @Override // com.taobao.accs.IAgooAppReceiver
    public String getAppkey() {
        return this.f20452d;
    }

    @Override // com.taobao.accs.IAppReceiver
    public String getService(String str) {
        return null;
    }

    @Override // com.taobao.accs.IAppReceiver
    public void onBindApp(int i2) {
    }

    @Override // com.taobao.accs.IAppReceiver
    public void onBindUser(String str, int i2) {
    }

    @Override // com.taobao.accs.IAppReceiver
    public void onData(String str, String str2, byte[] bArr) {
    }

    @Override // com.taobao.accs.IAppReceiver
    public void onSendData(String str, int i2) {
    }

    @Override // com.taobao.accs.IAppReceiver
    public void onUnbindApp(int i2) {
    }

    @Override // com.taobao.accs.IAppReceiver
    public void onUnbindUser(int i2) {
    }

    @Override // com.taobao.accs.IAppReceiverV1
    public void onBindApp(int i2, String str) {
        try {
            ALog.i("TaobaoRegister", "onBindApp", "errorCode", Integer.valueOf(i2));
            if (i2 != 200) {
                IRegister iRegister = this.f20451c;
                if (iRegister != null) {
                    iRegister.onFailure(String.valueOf(i2), "accs bindapp error!");
                    return;
                }
                return;
            }
            if (TaobaoRegister.mRequestListener == null) {
                com.taobao.agoo.a.b unused = TaobaoRegister.mRequestListener = new com.taobao.agoo.a.b(this.f20449a);
            }
            GlobalClientInfo.getInstance(this.f20450b).registerListener("AgooDeviceCmd", TaobaoRegister.mRequestListener);
            if (com.taobao.agoo.a.b.f20447b.b(this.f20449a.getPackageName()) && !UtilityImpl.b(Constants.SP_CHANNEL_FILE_NAME, this.f20450b)) {
                String strG = Config.g(this.f20449a);
                if (!TextUtils.isEmpty(strG)) {
                    boolean unused2 = TaobaoRegister.isRegisterSuccess = true;
                    l.a().b();
                    ALog.i("TaobaoRegister", "agoo already Registered return ", new Object[0]);
                    IRegister iRegister2 = this.f20451c;
                    if (iRegister2 != null) {
                        iRegister2.onSuccess(strG);
                        return;
                    }
                    return;
                }
            }
            byte[] bArrA = com.taobao.agoo.a.a.c.a(this.f20449a, this.f20452d, this.f20453e);
            if (bArrA == null) {
                IRegister iRegister3 = this.f20451c;
                if (iRegister3 != null) {
                    iRegister3.onFailure("503.1", "req data null");
                    return;
                }
                return;
            }
            String strB = this.f20454f.b(this.f20449a, new ACCSManager.AccsRequest(null, "AgooDeviceCmd", bArrA, null));
            if (!TextUtils.isEmpty(strB)) {
                if (this.f20451c != null) {
                    TaobaoRegister.mRequestListener.f20448a.put(strB, this.f20451c);
                }
            } else {
                IRegister iRegister4 = this.f20451c;
                if (iRegister4 != null) {
                    iRegister4.onFailure("503.1", "accs channel disabled!");
                }
            }
        } catch (Throwable th) {
            ALog.e("TaobaoRegister", "register onBindApp", th, new Object[0]);
        }
    }
}
