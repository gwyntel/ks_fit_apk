package com.aliyun.alink.linksdk.lpbs.lpbstgmesh;

import com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalAuthRegister;
import com.aliyun.alink.linksdk.alcs.lpbs.component.auth.IAuthProvider;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class AuthRegister implements IPalAuthRegister {
    private static final String TAG = Utils.TAG + "AuthRegister";

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalAuthRegister
    public IAuthProvider getProvider() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "getProvider empty");
        return null;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalAuthRegister
    public boolean setAuthProvider(IAuthProvider iAuthProvider) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "setAuthProvider iAuthProvider:" + iAuthProvider);
        return false;
    }
}
