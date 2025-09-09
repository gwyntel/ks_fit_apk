package com.huawei.hms.feature.dynamic.e;

import android.content.Context;
import android.os.Bundle;
import com.huawei.hms.common.util.Logger;
import com.huawei.hms.feature.dynamic.DynamicModule;

/* loaded from: classes4.dex */
public class c implements DynamicModule.VersionPolicy {

    /* renamed from: a, reason: collision with root package name */
    public static final String f16153a = "c";

    @Override // com.huawei.hms.feature.dynamic.DynamicModule.VersionPolicy
    public Bundle getModuleInfo(Context context, String str) throws DynamicModule.LoadingException {
        DynamicModule.LoadingException loadingException;
        Bundle bundle;
        try {
            bundle = DynamicModule.getRemoteModuleInfo(context, str);
            loadingException = null;
        } catch (DynamicModule.LoadingException e2) {
            loadingException = e2.getBundle() != null ? new DynamicModule.LoadingException(e2.getMessage(), e2.getBundle()) : new DynamicModule.LoadingException(e2.getMessage());
            Logger.w(f16153a, "Get remote module info failed: " + e2.getMessage() + ". try to query local.");
            bundle = new Bundle();
        }
        Bundle localModuleInfo = DynamicModule.getLocalModuleInfo(context, str);
        String str2 = f16153a;
        Logger.i(str2, "The version of remote module " + str + ":" + bundle.getInt(com.huawei.hms.feature.dynamic.b.f16127k));
        Logger.i(str2, "The version of local module " + str + ":" + localModuleInfo.getInt(com.huawei.hms.feature.dynamic.b.f16128l));
        if (localModuleInfo.getInt(com.huawei.hms.feature.dynamic.b.f16128l) > 0 && localModuleInfo.getInt(com.huawei.hms.feature.dynamic.b.f16128l) >= bundle.getInt(com.huawei.hms.feature.dynamic.b.f16127k)) {
            Logger.i(str2, "Choose local module info.");
            return localModuleInfo;
        }
        if (loadingException != null && bundle.getInt(com.huawei.hms.feature.dynamic.b.f16127k) == 0) {
            throw loadingException;
        }
        Logger.i(str2, "Choose remote module info.");
        return bundle;
    }
}
