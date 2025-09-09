package com.aliyun.alink.linksdk.channel.mobile.b;

import com.aliyun.alink.linksdk.channel.core.base.AError;
import com.aliyun.alink.linksdk.channel.mobile.api.IMobileSubscrbieListener;

/* loaded from: classes2.dex */
class i implements IMobileSubscrbieListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ d f11041a;

    i(d dVar) {
        this.f11041a = dVar;
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeListener
    public boolean needUISafety() {
        return false;
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeListener
    public void onFailed(String str, AError aError) {
        this.f11041a.f11025m = false;
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "afterConnect(),onFailed, error = " + aError.getMsg().toString());
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeListener
    public void onSuccess(String str) {
        this.f11041a.f11025m = true;
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "afterConnect(),onSuccess, topic=" + str);
    }
}
