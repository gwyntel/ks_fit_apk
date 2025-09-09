package com.huawei.hms.feature.dynamic.e;

import android.content.Context;
import android.os.Bundle;
import com.huawei.hms.common.util.Logger;
import com.huawei.hms.feature.dynamic.DynamicModule;

/* loaded from: classes4.dex */
public class e implements DynamicModule.VersionPolicy {

    /* renamed from: a, reason: collision with root package name */
    public static final String f16155a = "e";

    @Override // com.huawei.hms.feature.dynamic.DynamicModule.VersionPolicy
    public Bundle getModuleInfo(Context context, String str) throws DynamicModule.LoadingException {
        Bundle remoteModuleInfo = DynamicModule.getRemoteModuleInfo(context, str);
        if (remoteModuleInfo.getInt(com.huawei.hms.feature.dynamic.b.f16127k) > 0) {
            Logger.i(f16155a, "Prefer remote: The version of remote module " + str + ":" + remoteModuleInfo.getInt(com.huawei.hms.feature.dynamic.b.f16127k));
            return remoteModuleInfo;
        }
        Bundle localModuleInfo = DynamicModule.getLocalModuleInfo(context, str);
        if (localModuleInfo.getInt(com.huawei.hms.feature.dynamic.b.f16128l) <= 0) {
            Logger.i(f16155a, "Cannot get module info in remote or local.");
            return new Bundle();
        }
        Logger.i(f16155a, "Choose local: The version of local module " + str + ":" + localModuleInfo.getInt(com.huawei.hms.feature.dynamic.b.f16128l));
        return localModuleInfo;
    }
}
