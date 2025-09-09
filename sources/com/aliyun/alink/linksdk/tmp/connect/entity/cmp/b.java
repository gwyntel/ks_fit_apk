package com.aliyun.alink.linksdk.tmp.connect.entity.cmp;

import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.listener.IDiscoveryListener;
import com.aliyun.alink.linksdk.cmp.manager.discovery.DiscoveryMessage;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tools.AError;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class b extends com.aliyun.alink.linksdk.tmp.connect.f implements IDiscoveryListener {
    public b(com.aliyun.alink.linksdk.tmp.connect.c cVar, com.aliyun.alink.linksdk.tmp.connect.d dVar) {
        super(cVar, dVar);
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IDiscoveryListener
    public void onDiscovery(DiscoveryMessage discoveryMessage) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        AResponse aResponse = new AResponse();
        aResponse.data = discoveryMessage;
        a(this.f11186d, new i(aResponse));
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IDiscoveryListener
    public void onFailure(AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        a(this.f11186d, new ErrorInfo(aError.getCode(), aError.getMsg()));
    }
}
