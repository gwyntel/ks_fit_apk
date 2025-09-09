package com.aliyun.iot.aep.sdk.credential.oa;

import com.aliyun.iot.aep.sdk.credential.utils.ReflectUtils;
import com.aliyun.iot.aep.sdk.log.ALog;
import com.aliyun.iot.aep.sdk.login.ILoginAdapter;
import com.aliyun.iot.aep.sdk.login.ILoginStatusChangeListener;
import com.aliyun.iot.aep.sdk.login.LoginBusiness;

/* loaded from: classes3.dex */
public class OADepBiz {

    public interface OALoginStatusChangeListener extends ILoginStatusChangeListener {
        @Override // com.aliyun.iot.aep.sdk.login.ILoginStatusChangeListener
        void onLoginStatusChange();
    }

    public static String getEnv() {
        return !ReflectUtils.hasOADep() ? "" : LoginBusiness.getEnv();
    }

    public static String getSessionId() {
        ILoginAdapter loginAdapter;
        return (ReflectUtils.hasOADep() && (loginAdapter = LoginBusiness.getLoginAdapter()) != null) ? loginAdapter.getSessionId() : "";
    }

    public static boolean hasOAAdapter() {
        return ReflectUtils.hasOADep() && LoginBusiness.getLoginAdapter() != null;
    }

    public static boolean isLogin() {
        ILoginAdapter loginAdapter;
        if (ReflectUtils.hasOADep() && (loginAdapter = LoginBusiness.getLoginAdapter()) != null) {
            return loginAdapter.isLogin();
        }
        return false;
    }

    public static void refreshSession(boolean z2) {
        ALog.d("OADepBiz", "refreshSession() called with: doRefresh = [" + z2 + "]");
        if (ReflectUtils.hasOADep()) {
            LoginBusiness.refreshSession(true, null);
        }
    }

    public static void registerLoginListener(OALoginStatusChangeListener oALoginStatusChangeListener) {
        ILoginAdapter loginAdapter;
        if (ReflectUtils.hasOADep() && (loginAdapter = LoginBusiness.getLoginAdapter()) != null) {
            loginAdapter.registerLoginListener(oALoginStatusChangeListener);
        }
    }
}
