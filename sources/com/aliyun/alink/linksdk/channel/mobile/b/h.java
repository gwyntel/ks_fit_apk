package com.aliyun.alink.linksdk.channel.mobile.b;

import com.aliyun.alink.linksdk.channel.mobile.api.MobileConnectState;
import com.aliyun.alink.linksdk.channel.mobile.b.b;

/* loaded from: classes2.dex */
class h implements b.a {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ d f11040a;

    h(d dVar) {
        this.f11040a = dVar;
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.b.b.a
    public void a(l lVar) {
        if (lVar != null && lVar.a()) {
            if (!m.a().a(this.f11040a.f11017e, lVar)) {
                com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "save trilpe error");
            }
            this.f11040a.a(lVar);
            return;
        }
        com.aliyun.alink.linksdk.channel.mobile.a.a.d("MobileChannelImpl", "mobile Auth onSuccess but value empty");
        d dVar = this.f11040a;
        MobileConnectState mobileConnectState = MobileConnectState.CONNECTFAIL;
        dVar.f11019g = mobileConnectState;
        if (this.f11040a.f11018f != null) {
            this.f11040a.f11018f.onConnectStateChange(mobileConnectState);
        }
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.b.b.a
    public void a(String str) {
        com.aliyun.alink.linksdk.channel.mobile.a.a.d("MobileChannelImpl", "mobile Auth onFailed,msg =" + str);
        d dVar = this.f11040a;
        MobileConnectState mobileConnectState = MobileConnectState.CONNECTFAIL;
        dVar.f11019g = mobileConnectState;
        if (this.f11040a.f11018f != null) {
            this.f11040a.f11018f.onConnectStateChange(mobileConnectState);
        }
    }
}
