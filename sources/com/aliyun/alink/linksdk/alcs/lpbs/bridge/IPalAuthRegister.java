package com.aliyun.alink.linksdk.alcs.lpbs.bridge;

import com.aliyun.alink.linksdk.alcs.lpbs.component.auth.IAuthProvider;

/* loaded from: classes2.dex */
public interface IPalAuthRegister {
    IAuthProvider getProvider();

    boolean setAuthProvider(IAuthProvider iAuthProvider);
}
