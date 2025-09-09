package com.aliyun.alink.linksdk.channel.mobile.b;

import com.aliyun.alink.linksdk.channel.mobile.a.b;
import com.aliyun.alink.linksdk.channel.mobile.api.MobileConnectConfig;
import com.aliyun.alink.linksdk.channel.mobile.api.MobileConnectState;

/* loaded from: classes2.dex */
class e implements b.a {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ MobileConnectConfig f11034a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ d f11035b;

    e(d dVar, MobileConnectConfig mobileConnectConfig) {
        this.f11035b = dVar;
        this.f11034a = mobileConnectConfig;
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.a.b.a
    public void a() {
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "DynamicHostRequest,onSuccess");
        this.f11035b.a(this.f11034a);
    }

    @Override // com.aliyun.alink.linksdk.channel.mobile.a.b.a
    public void b() {
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "DynamicHostRequest,onFailure");
        if (this.f11035b.f11018f != null) {
            this.f11035b.f11018f.onConnectStateChange(MobileConnectState.CONNECTFAIL);
        }
    }
}
