package com.aliyun.alink.linksdk.tmp.connect.entity.cmp;

import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.listener.IDiscoveryListener;
import com.aliyun.alink.linksdk.cmp.manager.discovery.DiscoveryMessage;
import com.aliyun.alink.linksdk.tmp.event.INotifyHandler;
import com.aliyun.alink.linksdk.tools.AError;

/* loaded from: classes2.dex */
public class e implements IDiscoveryListener {

    /* renamed from: a, reason: collision with root package name */
    protected INotifyHandler f11163a;

    /* renamed from: b, reason: collision with root package name */
    protected com.aliyun.alink.linksdk.tmp.connect.d f11164b = new f(null);

    public e(INotifyHandler iNotifyHandler) {
        this.f11163a = iNotifyHandler;
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IDiscoveryListener
    public void onDiscovery(DiscoveryMessage discoveryMessage) {
        AResponse aResponse = new AResponse();
        aResponse.data = discoveryMessage;
        this.f11163a.onMessage(this.f11164b, new i(aResponse));
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IDiscoveryListener
    public void onFailure(AError aError) {
    }
}
