package com.xiaomi.push.service;

import android.text.TextUtils;
import com.xiaomi.push.in;
import com.xiaomi.push.ja;
import com.xiaomi.push.jj;
import com.xiaomi.push.jm;
import com.xiaomi.push.jx;
import com.xiaomi.push.service.XMPushService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
class t extends XMPushService.j {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ s f24621a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f1109a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ List f1110a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f24622b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    t(s sVar, int i2, String str, List list, String str2) {
        super(i2);
        this.f24621a = sVar;
        this.f1109a = str;
        this.f1110a = list;
        this.f24622b = str2;
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    /* renamed from: a */
    public void mo433a() throws NumberFormatException {
        String strA = this.f24621a.a(this.f1109a);
        ArrayList<jm> arrayListA = ca.a(this.f1110a, this.f1109a, strA, 32768);
        if (arrayListA == null) {
            com.xiaomi.channel.commonutils.logger.b.d("TinyData LongConnUploader.upload Get a null XmPushActionNotification list when TinyDataHelper.pack() in XMPushService.");
            return;
        }
        Iterator<jm> it = arrayListA.iterator();
        while (it.hasNext()) {
            jm next = it.next();
            next.a("uploadWay", "longXMPushService");
            jj jjVarA = ai.a(this.f1109a, strA, next, in.Notification);
            if (!TextUtils.isEmpty(this.f24622b) && !TextUtils.equals(this.f1109a, this.f24622b)) {
                if (jjVarA.m593a() == null) {
                    ja jaVar = new ja();
                    jaVar.a("-1");
                    jjVarA.a(jaVar);
                }
                jjVarA.m593a().b("ext_traffic_source_pkg", this.f24622b);
            }
            this.f24621a.f24620a.a(this.f1109a, jx.a(jjVarA), true);
        }
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    public String a() {
        return "Send tiny data.";
    }
}
