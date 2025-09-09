package com.aliyun.alink.linksdk.alcs.lpbs.bridge.a;

import com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalAuthRegister;
import com.aliyun.alink.linksdk.alcs.lpbs.component.auth.IAuthProvider;

/* loaded from: classes2.dex */
public class g implements IPalAuthRegister {

    /* renamed from: a, reason: collision with root package name */
    private IAuthProvider f10818a;

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalAuthRegister
    public IAuthProvider getProvider() {
        return this.f10818a;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalAuthRegister
    public boolean setAuthProvider(IAuthProvider iAuthProvider) {
        this.f10818a = iAuthProvider;
        return true;
    }
}
