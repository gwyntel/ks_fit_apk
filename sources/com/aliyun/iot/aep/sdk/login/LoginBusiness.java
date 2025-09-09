package com.aliyun.iot.aep.sdk.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.aliyun.alink.sdk.jsbridge.BonePluginRegistry;
import com.aliyun.iot.aep.sdk.login.data.UserInfo;
import com.aliyun.iot.aep.sdk.login.plugin.BoneUserAccountPlugin;
import java.util.concurrent.Executors;

/* loaded from: classes3.dex */
public class LoginBusiness {
    public static final String LOGIN_CHANGE_ACTION = "com.aliyun.iot.sdk.LoginStatusChange";

    /* renamed from: a, reason: collision with root package name */
    private static ILoginAdapter f11879a = null;

    /* renamed from: b, reason: collision with root package name */
    private static Context f11880b = null;

    /* renamed from: c, reason: collision with root package name */
    private static String f11881c = "";

    private static class a implements ILoginStatusChangeListener {
        private a() {
        }

        @Override // com.aliyun.iot.aep.sdk.login.ILoginStatusChangeListener
        public void onLoginStatusChange() {
            Intent intent = new Intent(LoginBusiness.LOGIN_CHANGE_ACTION);
            intent.addFlags(32);
            LocalBroadcastManager.getInstance(LoginBusiness.f11880b).sendBroadcast(intent);
        }
    }

    private LoginBusiness() {
    }

    public static void authCodeLogin(String str, ILoginCallback iLoginCallback) {
        ILoginAdapter iLoginAdapter = f11879a;
        if (iLoginAdapter != null) {
            iLoginAdapter.authCodeLogin(str, iLoginCallback);
        }
    }

    public static String getEnv() {
        return f11881c;
    }

    public static ILoginAdapter getLoginAdapter() {
        return f11879a;
    }

    public static String getSessionId() {
        ILoginAdapter iLoginAdapter = f11879a;
        if (iLoginAdapter != null) {
            return iLoginAdapter.getSessionId();
        }
        return null;
    }

    public static Object getSessionInfo() {
        ILoginAdapter iLoginAdapter = f11879a;
        if (iLoginAdapter != null) {
            return iLoginAdapter.getSessionData();
        }
        return null;
    }

    public static UserInfo getUserInfo() {
        ILoginAdapter iLoginAdapter = f11879a;
        if (iLoginAdapter != null) {
            return iLoginAdapter.getUserData();
        }
        return null;
    }

    public static void init(Context context, ILoginAdapter iLoginAdapter, String str) {
        if (iLoginAdapter == null) {
            throw new IllegalArgumentException("LoginAdapter must not be null");
        }
        f11879a = iLoginAdapter;
        f11881c = str;
        f11880b = context;
        iLoginAdapter.registerLoginListener(new a());
        if (isJsbridgeAvailable()) {
            BonePluginRegistry.register(BoneUserAccountPlugin.API_NAME, BoneUserAccountPlugin.class);
        }
    }

    public static boolean isJsbridgeAvailable() {
        return a("com.aliyun.alink.sdk.jsbridge.BonePluginRegistry");
    }

    public static boolean isLogin() {
        ILoginAdapter iLoginAdapter = f11879a;
        if (iLoginAdapter != null) {
            return iLoginAdapter.isLogin();
        }
        return false;
    }

    public static void login(ILoginCallback iLoginCallback) {
        ILoginAdapter iLoginAdapter = f11879a;
        if (iLoginAdapter != null) {
            iLoginAdapter.login(iLoginCallback);
        }
    }

    public static void logout(final ILogoutCallback iLogoutCallback) {
        Executors.newSingleThreadExecutor().execute(new Runnable() { // from class: com.aliyun.iot.aep.sdk.login.LoginBusiness.1
            @Override // java.lang.Runnable
            public void run() {
                if (LoginBusiness.f11879a != null) {
                    LoginBusiness.f11879a.logout(iLogoutCallback);
                }
            }
        });
    }

    public static void oauthLogin(Activity activity, ILoginCallback iLoginCallback) {
        ILoginAdapter iLoginAdapter = f11879a;
        if (iLoginAdapter != null) {
            iLoginAdapter.oauthLogin(activity, iLoginCallback);
        }
    }

    public static void refreshSession(boolean z2, IRefreshSessionCallback iRefreshSessionCallback) {
        ILoginAdapter iLoginAdapter = f11879a;
        if (iLoginAdapter != null) {
            iLoginAdapter.refreshSession(z2, iRefreshSessionCallback);
        }
    }

    public static void showEmailRegister(Context context, Class<?> cls, ILoginCallback iLoginCallback) {
        ILoginAdapter iLoginAdapter = f11879a;
        if (iLoginAdapter != null) {
            iLoginAdapter.showEmailRegister(context, cls, iLoginCallback);
        }
    }

    public static void showRegister(Context context, Class<?> cls, ILoginCallback iLoginCallback) {
        ILoginAdapter iLoginAdapter = f11879a;
        if (iLoginAdapter != null) {
            iLoginAdapter.showRegister(context, cls, iLoginCallback);
        }
    }

    private static boolean a(String str) {
        try {
            Class.forName(str);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    @Deprecated
    public static void login(String str, String str2, ILoginCallback iLoginCallback) {
        ILoginAdapter iLoginAdapter = f11879a;
        if (iLoginAdapter != null) {
            iLoginAdapter.login(str, str2, iLoginCallback);
        }
    }

    public static void oauthLogin(Activity activity, int i2, ILoginCallback iLoginCallback) {
        ILoginAdapter iLoginAdapter = f11879a;
        if (iLoginAdapter != null) {
            iLoginAdapter.oauthLogin(activity, i2, iLoginCallback);
        }
    }

    @Deprecated
    public static void init(Context context, ILoginAdapter iLoginAdapter, boolean z2, String str) {
        f11881c = str;
        f11880b = context;
        if (iLoginAdapter != null) {
            f11879a = iLoginAdapter;
            iLoginAdapter.init(str);
            f11879a.setIsDebuggable(z2);
            f11879a.registerLoginListener(new a());
            if (isJsbridgeAvailable()) {
                BonePluginRegistry.register(BoneUserAccountPlugin.API_NAME, BoneUserAccountPlugin.class);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("LoginAdapter must not be null");
    }
}
