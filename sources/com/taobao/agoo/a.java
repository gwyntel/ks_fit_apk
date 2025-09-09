package com.taobao.agoo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.taobao.accs.utl.ALog;
import org.android.agoo.control.AgooFactory;
import org.android.agoo.control.NotifManager;

/* loaded from: classes4.dex */
class a implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Intent f20418a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ BaseNotifyClick f20419b;

    a(BaseNotifyClick baseNotifyClick, Intent intent) {
        this.f20419b = baseNotifyClick;
        this.f20418a = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        Intent intent = null;
        try {
            Intent intent2 = this.f20418a;
            if (intent2 != null) {
                String msgByThirdPush = this.f20419b.parseMsgByThirdPush(intent2);
                if (TextUtils.isEmpty(msgByThirdPush) || TextUtils.isEmpty(this.f20419b.msgSource)) {
                    ALog.e("accs.BaseNotifyClick", "parseMsgFromNotifyListener null!!", "source", this.f20419b.msgSource);
                } else {
                    if (this.f20419b.notifyManager == null) {
                        this.f20419b.notifyManager = new NotifManager();
                    }
                    if (this.f20419b.agooFactory == null) {
                        this.f20419b.agooFactory = new AgooFactory();
                        this.f20419b.agooFactory.init(this.f20419b.context, this.f20419b.notifyManager, null);
                    }
                    Bundle bundleMsgReceiverPreHandler = this.f20419b.agooFactory.msgReceiverPreHandler(msgByThirdPush.getBytes("UTF-8"), this.f20419b.msgSource, null, false);
                    String string = bundleMsgReceiverPreHandler.getString("body");
                    ALog.i("accs.BaseNotifyClick", "begin parse EncryptedMsg", new Object[0]);
                    String encryptedMsg = AgooFactory.parseEncryptedMsg(string);
                    if (TextUtils.isEmpty(encryptedMsg)) {
                        ALog.e("accs.BaseNotifyClick", "parse EncryptedMsg fail, empty", new Object[0]);
                    } else {
                        bundleMsgReceiverPreHandler.putString("body", encryptedMsg);
                    }
                    Intent intent3 = new Intent();
                    try {
                        intent3.putExtras(bundleMsgReceiverPreHandler);
                        this.f20419b.agooFactory.saveMsg(msgByThirdPush.getBytes("UTF-8"), "2");
                        this.f20419b.reportClickNotifyMsg(intent3);
                        intent = intent3;
                    } catch (Throwable th) {
                        intent = intent3;
                        th = th;
                        try {
                            ALog.e("accs.BaseNotifyClick", "buildMessage", th, new Object[0]);
                            try {
                                this.f20419b.onMessage(intent);
                                return;
                            } catch (Throwable th2) {
                                ALog.e("accs.BaseNotifyClick", "onMessage", th2, new Object[0]);
                                return;
                            }
                        } catch (Throwable th3) {
                            try {
                                this.f20419b.onMessage(intent);
                            } catch (Throwable th4) {
                                ALog.e("accs.BaseNotifyClick", "onMessage", th4, new Object[0]);
                            }
                            throw th3;
                        }
                    }
                }
            }
            try {
                this.f20419b.onMessage(intent);
            } catch (Throwable th5) {
                ALog.e("accs.BaseNotifyClick", "onMessage", th5, new Object[0]);
            }
        } catch (Throwable th6) {
            th = th6;
        }
    }
}
