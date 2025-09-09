package com.aliyun.iot.aep.sdk.login.oa;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.openaccount.ConfigManager;
import com.alibaba.sdk.android.openaccount.Environment;
import com.alibaba.sdk.android.openaccount.OauthService;
import com.alibaba.sdk.android.openaccount.OpenAccountConstants;
import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.OpenAccountService;
import com.alibaba.sdk.android.openaccount.OpenAccountSessionService;
import com.alibaba.sdk.android.openaccount.callback.InitResultCallback;
import com.alibaba.sdk.android.openaccount.callback.LoginCallback;
import com.alibaba.sdk.android.openaccount.callback.LogoutCallback;
import com.alibaba.sdk.android.openaccount.model.LoginResult;
import com.alibaba.sdk.android.openaccount.model.OpenAccountSession;
import com.alibaba.sdk.android.openaccount.model.Result;
import com.alibaba.sdk.android.openaccount.model.SessionData;
import com.alibaba.sdk.android.openaccount.model.User;
import com.alibaba.sdk.android.openaccount.session.SessionListener;
import com.alibaba.sdk.android.openaccount.session.SessionManagerService;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConfigs;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIService;
import com.alibaba.sdk.android.openaccount.ui.callback.EmailRegisterCallback;
import com.alibaba.sdk.android.openaccount.util.OpenAccountUtils;
import com.alibaba.sdk.android.openaccount.util.RpcUtils;
import com.alibaba.sdk.android.openaccount.util.safe.RSAKey;
import com.alibaba.sdk.android.openaccount.util.safe.Rsa;
import com.aliyun.iot.aep.sdk.log.ALog;
import com.aliyun.iot.aep.sdk.login.ILoginAdapter;
import com.aliyun.iot.aep.sdk.login.ILoginCallback;
import com.aliyun.iot.aep.sdk.login.ILoginStatusChangeListener;
import com.aliyun.iot.aep.sdk.login.ILogoutCallback;
import com.aliyun.iot.aep.sdk.login.IRefreshSessionCallback;
import com.aliyun.iot.aep.sdk.login.LoginBusiness;
import com.aliyun.iot.aep.sdk.login.data.SessionInfo;
import com.aliyun.iot.aep.sdk.login.data.UserInfo;
import com.aliyun.iot.aep.sdk.threadpool.ThreadPool;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class OALoginAdapter implements ILoginAdapter {

    /* renamed from: c, reason: collision with root package name */
    private Context f11887c;

    /* renamed from: j, reason: collision with root package name */
    private SessionListener f11894j;

    /* renamed from: m, reason: collision with root package name */
    private Map<String, String> f11897m;

    /* renamed from: n, reason: collision with root package name */
    private Class<?> f11898n;

    /* renamed from: a, reason: collision with root package name */
    private final String f11885a = "OALoginAdapter";

    /* renamed from: b, reason: collision with root package name */
    private boolean f11886b = false;

    /* renamed from: d, reason: collision with root package name */
    private boolean f11888d = false;

    /* renamed from: e, reason: collision with root package name */
    private List<ILoginStatusChangeListener> f11889e = new ArrayList();

    /* renamed from: f, reason: collision with root package name */
    private volatile List<IRefreshSessionCallback> f11890f = new ArrayList();

    /* renamed from: g, reason: collision with root package name */
    private List<OnBeforeLogoutListener> f11891g = new ArrayList();

    /* renamed from: h, reason: collision with root package name */
    private List<OnCompleteBeforeLogoutListener> f11892h = new ArrayList();

    /* renamed from: i, reason: collision with root package name */
    private volatile boolean f11893i = false;

    /* renamed from: k, reason: collision with root package name */
    private SessionInfo f11895k = new SessionInfo();

    /* renamed from: l, reason: collision with root package name */
    private String f11896l = "";

    public interface ActionOnBeforeLogoutResultCallback {
        public static final int RESULT_FAILED = -1;
        public static final int RESULT_SUCC = 0;

        void onResult(int i2);
    }

    public interface OALoginAdapterInitResultCallback {
        void onInitFailed(int i2, String str);

        void onInitSuccess();
    }

    public class OALoginCallback implements LoginCallback, EmailRegisterCallback {
        public EmailRegisterCallback emailRegisterCallback;
        public ILoginCallback loginCallback;

        public OALoginCallback(ILoginCallback iLoginCallback) {
            this.loginCallback = iLoginCallback;
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.callback.EmailRegisterCallback
        public void onEmailSent(String str) {
        }

        @Override // com.alibaba.sdk.android.openaccount.callback.FailureCallback
        public void onFailure(int i2, String str) {
            OALoginAdapter.this.a("login failed  code:" + i2 + " msg:" + str);
            ILoginCallback iLoginCallback = this.loginCallback;
            if (iLoginCallback != null) {
                iLoginCallback.onLoginFailed(i2, str);
            }
        }

        @Override // com.alibaba.sdk.android.openaccount.callback.LoginCallback
        public void onSuccess(OpenAccountSession openAccountSession) {
            OALoginAdapter.this.a("login Success" + OALoginAdapter.this.a(openAccountSession));
            OALoginAdapter.this.a();
            Iterator it = OALoginAdapter.this.f11889e.iterator();
            while (it.hasNext()) {
                ((ILoginStatusChangeListener) it.next()).onLoginStatusChange();
            }
            ILoginCallback iLoginCallback = this.loginCallback;
            if (iLoginCallback != null) {
                iLoginCallback.onLoginSuccess();
            }
            UserInfo userInfo = LoginBusiness.getUserInfo();
            if (userInfo != null) {
                OALoginAdapter.this.a("onSuccess: " + JSON.toJSONString(userInfo));
                if (TextUtils.isEmpty(userInfo.openId)) {
                    return;
                }
                HashMap map = new HashMap();
                User user = openAccountSession == null ? null : openAccountSession.getUser();
                if (!TextUtils.isEmpty(userInfo.userNick) && user != null && !TextUtils.isEmpty(user.displayName)) {
                    map.put("displayName", userInfo.userNick);
                }
                if (!TextUtils.isEmpty(userInfo.userAvatarUrl) && user != null && !TextUtils.isEmpty(user.avatarUrl)) {
                    map.put("avatarUrl", userInfo.userAvatarUrl);
                }
                if (map.isEmpty()) {
                    return;
                }
                ((OpenAccountUIService) OpenAccountSDK.getService(OpenAccountUIService.class)).updateProfile(OALoginAdapter.this.f11887c, map, new c());
            }
        }
    }

    public interface OnBeforeLogoutListener {
        void doAction();
    }

    public interface OnCompleteBeforeLogoutListener {
        void doAction(ActionOnBeforeLogoutResultCallback actionOnBeforeLogoutResultCallback);
    }

    private class a implements LogoutCallback {

        /* renamed from: b, reason: collision with root package name */
        private ILogoutCallback f11912b;

        public a(ILogoutCallback iLogoutCallback) {
            this.f11912b = iLogoutCallback;
        }

        @Override // com.alibaba.sdk.android.openaccount.callback.FailureCallback
        public void onFailure(int i2, String str) {
            OALoginAdapter.this.a("logout failurecode:" + i2 + " msg:" + str);
            ILogoutCallback iLogoutCallback = this.f11912b;
            if (iLogoutCallback != null) {
                iLogoutCallback.onLogoutFailed(i2, str);
            }
        }

        @Override // com.alibaba.sdk.android.openaccount.callback.LogoutCallback
        public void onSuccess() {
            OALoginAdapter.this.a("logout Success");
            Iterator it = OALoginAdapter.this.f11889e.iterator();
            while (it.hasNext()) {
                ((ILoginStatusChangeListener) it.next()).onLoginStatusChange();
            }
            ILogoutCallback iLogoutCallback = this.f11912b;
            if (iLogoutCallback != null) {
                iLogoutCallback.onLogoutSuccess();
            }
        }
    }

    private class b implements SessionListener {
        private b() {
        }

        @Override // com.alibaba.sdk.android.openaccount.session.SessionListener
        public void onStateChanged(OpenAccountSession openAccountSession) {
            OALoginAdapter.this.a("onStateChanged() refreshCacheList size:" + OALoginAdapter.this.f11890f.size());
            OALoginAdapter.this.a();
            if (!OALoginAdapter.this.f11890f.isEmpty()) {
                OALoginAdapter.this.b();
            }
            OALoginAdapter.this.f11893i = false;
        }
    }

    private class c implements LoginCallback {
        private c() {
        }

        @Override // com.alibaba.sdk.android.openaccount.callback.FailureCallback
        public void onFailure(int i2, String str) {
            OALoginAdapter.this.a("updateProfile onFailure" + i2 + " " + str);
        }

        @Override // com.alibaba.sdk.android.openaccount.callback.LoginCallback
        public void onSuccess(OpenAccountSession openAccountSession) {
            OALoginAdapter.this.a("updateProfile Success" + OALoginAdapter.this.a(openAccountSession));
        }
    }

    public OALoginAdapter(Context context) {
        this.f11887c = context;
    }

    @Override // com.aliyun.iot.aep.sdk.login.ILoginAdapter
    public void authCodeLogin(String str, ILoginCallback iLoginCallback) {
        ((OpenAccountService) OpenAccountSDK.getService(OpenAccountService.class)).authCodeLogin(this.f11887c, str, new OALoginCallback(iLoginCallback));
    }

    @Override // com.aliyun.iot.aep.sdk.login.ILoginAdapter
    public Object getSessionData() {
        return this.f11895k;
    }

    @Override // com.aliyun.iot.aep.sdk.login.ILoginAdapter
    public String getSessionId() {
        return this.f11895k.sessionId;
    }

    @Override // com.aliyun.iot.aep.sdk.login.ILoginAdapter
    public UserInfo getUserData() {
        OpenAccountSession session = ((OpenAccountService) OpenAccountSDK.getService(OpenAccountService.class)).getSession();
        User user = session == null ? null : session.getUser();
        UserInfo userInfo = new UserInfo();
        if (user == null) {
            return null;
        }
        Log.d("OALoginAdapter", "getUserData:" + JSON.toJSONString(user));
        Map<String, Object> otherInfo = session.getOtherInfo();
        if (otherInfo != null) {
            try {
                if (otherInfo.get(OpenAccountConstants.OPEN_ACCOUNT_OTHER_INFO) != null) {
                    Map map = (Map) otherInfo.get(OpenAccountConstants.OPEN_ACCOUNT_OTHER_INFO);
                    if (map.get("avatarUrl") != null) {
                        userInfo.userAvatarUrl = map.get("avatarUrl").toString();
                    }
                    if (map.get("id") != null) {
                        userInfo.userId = map.get("id").toString();
                    }
                    if (map.get("displayName") != null) {
                        userInfo.userNick = map.get("displayName").toString();
                    }
                    if (map.get("country") != null) {
                        userInfo.country = map.get("country").toString();
                    }
                }
                if (!TextUtils.isEmpty(user.openId) && otherInfo.get(OpenAccountConstants.OAUTH_OTHER_INFO) != null) {
                    Map map2 = (Map) otherInfo.get(OpenAccountConstants.OAUTH_OTHER_INFO);
                    if (map2.get("picture") != null) {
                        userInfo.userAvatarUrl = map2.get("picture").toString();
                    }
                    if (map2.get("name") != null) {
                        userInfo.userNick = map2.get("name").toString();
                    }
                    if (map2.get("country") != null) {
                        userInfo.country = map2.get("country").toString();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                Log.e("OALoginAdapter", "getUserData: otherInfo error:" + e2.getMessage());
            }
        }
        userInfo.openId = user.openId;
        userInfo.userId = user.id;
        if (!TextUtils.isEmpty(user.avatarUrl)) {
            userInfo.userAvatarUrl = user.avatarUrl;
        }
        if (TextUtils.isEmpty(userInfo.userNick)) {
            if (!TextUtils.isEmpty(user.nick)) {
                userInfo.userNick = user.nick;
            } else if (!TextUtils.isEmpty(user.displayName)) {
                userInfo.userNick = user.displayName;
            }
        }
        String str = user.mobile;
        if (str != null) {
            userInfo.userPhone = str;
        }
        String str2 = user.email;
        if (str2 != null) {
            userInfo.userEmail = str2;
        }
        String str3 = user.mobileLocationCode;
        if (str3 != null) {
            userInfo.mobileLocationCode = str3;
        }
        return userInfo;
    }

    @Override // com.aliyun.iot.aep.sdk.login.ILoginAdapter
    public void init(String str) {
        String securityImagePostfix = ConfigManager.getInstance().getSecurityImagePostfix();
        if (TextUtils.isEmpty(securityImagePostfix)) {
            securityImagePostfix = "114d";
        }
        init(str, securityImagePostfix);
    }

    @Override // com.aliyun.iot.aep.sdk.login.ILoginAdapter
    public boolean isLogin() {
        OpenAccountSession session = ((OpenAccountService) OpenAccountSDK.getService(OpenAccountService.class)).getSession();
        return session != null && session.isLogin();
    }

    @Override // com.aliyun.iot.aep.sdk.login.ILoginAdapter
    public void login(ILoginCallback iLoginCallback) {
        login(iLoginCallback, null);
    }

    @Override // com.aliyun.iot.aep.sdk.login.ILoginAdapter
    public void logout(ILogoutCallback iLogoutCallback) throws InterruptedException {
        List<OnBeforeLogoutListener> list = this.f11891g;
        if (list != null && !list.isEmpty()) {
            Iterator<OnBeforeLogoutListener> it = this.f11891g.iterator();
            while (it.hasNext()) {
                it.next().doAction();
            }
        }
        List<OnCompleteBeforeLogoutListener> list2 = this.f11892h;
        if (list2 != null && !list2.isEmpty()) {
            int size = this.f11892h.size();
            final CountDownLatch countDownLatch = new CountDownLatch(size);
            ALog.i("OALoginAdapter", "action nums : " + size);
            Iterator<OnCompleteBeforeLogoutListener> it2 = this.f11892h.iterator();
            while (it2.hasNext()) {
                it2.next().doAction(new ActionOnBeforeLogoutResultCallback() { // from class: com.aliyun.iot.aep.sdk.login.oa.OALoginAdapter.4
                    @Override // com.aliyun.iot.aep.sdk.login.oa.OALoginAdapter.ActionOnBeforeLogoutResultCallback
                    public void onResult(int i2) {
                        ALog.i("OALoginAdapter", "doAction onResult : " + i2);
                        countDownLatch.countDown();
                    }
                });
            }
            try {
                countDownLatch.await(10L, TimeUnit.SECONDS);
            } catch (InterruptedException e2) {
                ALog.i("OALoginAdapter", "OnCompleteBeforeLogoutListener doAction await timeout!");
                e2.printStackTrace();
            }
        }
        try {
            ((OpenAccountService) OpenAccountSDK.getService(OpenAccountService.class)).logout(this.f11887c, new a(iLogoutCallback));
        } catch (Exception e3) {
            a("logout failure" + e3.toString());
        }
        try {
            CookieManager.getInstance().removeAllCookies(new ValueCallback<Boolean>() { // from class: com.aliyun.iot.aep.sdk.login.oa.OALoginAdapter.5
                @Override // android.webkit.ValueCallback
                /* renamed from: a, reason: merged with bridge method [inline-methods] */
                public void onReceiveValue(Boolean bool) {
                    Log.d("OALoginAdapter", "Cookie removed: " + bool);
                }
            });
        } catch (Exception unused) {
        }
    }

    @Override // com.aliyun.iot.aep.sdk.login.ILoginAdapter
    public void oauthLogin(Activity activity, ILoginCallback iLoginCallback) {
        ((OauthService) OpenAccountSDK.getService(OauthService.class)).oauth(activity, 32, new OALoginCallback(iLoginCallback));
    }

    @Override // com.aliyun.iot.aep.sdk.login.ILoginAdapter
    public void refreshSession(final boolean z2, IRefreshSessionCallback iRefreshSessionCallback) {
        if (iRefreshSessionCallback != null) {
            this.f11890f.add(iRefreshSessionCallback);
        }
        if (this.f11893i) {
            return;
        }
        ThreadPool.DefaultThreadPool.getInstance().submit(new Runnable() { // from class: com.aliyun.iot.aep.sdk.login.oa.OALoginAdapter.2
            @Override // java.lang.Runnable
            public void run() {
                OALoginAdapter.this.a("refreshSession() is force:" + z2);
                ((OpenAccountSessionService) OpenAccountSDK.getService(OpenAccountSessionService.class)).refreshSession(z2);
            }
        });
    }

    public void registerBeforeLogoutListener(OnBeforeLogoutListener onBeforeLogoutListener) {
        if (onBeforeLogoutListener != null) {
            this.f11891g.add(onBeforeLogoutListener);
        }
    }

    public void registerCompleteBeforeLogoutListener(OnCompleteBeforeLogoutListener onCompleteBeforeLogoutListener) {
        ALog.i("OALoginAdapter", "registerCompleteBeforeLogoutListener ~");
        if (onCompleteBeforeLogoutListener != null) {
            this.f11892h.add(onCompleteBeforeLogoutListener);
        }
    }

    @Override // com.aliyun.iot.aep.sdk.login.ILoginAdapter
    public void registerLoginListener(ILoginStatusChangeListener iLoginStatusChangeListener) {
        this.f11889e.add(iLoginStatusChangeListener);
    }

    public void setDefaultLoginClass(Class<?> cls) {
        this.f11898n = cls;
    }

    public void setDefaultLoginParams(Map<String, String> map) {
        this.f11897m = map;
    }

    public void setDefaultOAHost(String str) {
        this.f11896l = str;
    }

    @Override // com.aliyun.iot.aep.sdk.login.ILoginAdapter
    public void setIsDebuggable(boolean z2) {
        if (z2) {
            OpenAccountSDK.turnOnDebug();
        }
    }

    public void setSupportAliYun(boolean z2) {
        this.f11886b = z2;
    }

    public void showChangePwd(ILoginCallback iLoginCallback) {
        OpenAccountUIService openAccountUIService = (OpenAccountUIService) OpenAccountSDK.getService(OpenAccountUIService.class);
        if (OpenAccountUIConfigs.MobileResetPasswordLoginFlow.resetPasswordPasswordActivityClazz != null) {
            openAccountUIService.showRegister(this.f11887c, OpenAccountUIConfigs.UnifyLoginFlow.resetPasswordActivityClass, new OALoginCallback(iLoginCallback));
        } else {
            openAccountUIService.showRegister(this.f11887c, new OALoginCallback(iLoginCallback));
        }
    }

    @Override // com.aliyun.iot.aep.sdk.login.ILoginAdapter
    public void showEmailRegister(Context context, Class<?> cls, ILoginCallback iLoginCallback) {
        ((OpenAccountUIService) OpenAccountSDK.getService(OpenAccountUIService.class)).showEmailRegister(context, cls, new OALoginCallback(iLoginCallback));
    }

    @Override // com.aliyun.iot.aep.sdk.login.ILoginAdapter
    public void showRegister(Context context, Class<?> cls, ILoginCallback iLoginCallback) {
        ((OpenAccountUIService) OpenAccountSDK.getService(OpenAccountUIService.class)).showRegister(context, cls, new OALoginCallback(iLoginCallback));
    }

    public void unRegisterBeforeLogoutListener(OnBeforeLogoutListener onBeforeLogoutListener) {
        if (onBeforeLogoutListener == null) {
            return;
        }
        try {
            this.f11891g.remove(onBeforeLogoutListener);
        } catch (Exception unused) {
        }
    }

    public void unRegisterCompleteBeforeLogoutListener(OnCompleteBeforeLogoutListener onCompleteBeforeLogoutListener) {
        if (onCompleteBeforeLogoutListener == null) {
            return;
        }
        try {
            this.f11892h.remove(onCompleteBeforeLogoutListener);
        } catch (Exception unused) {
        }
    }

    @Override // com.aliyun.iot.aep.sdk.login.ILoginAdapter
    public void unRegisterLoginListener(ILoginStatusChangeListener iLoginStatusChangeListener) {
        this.f11889e.remove(iLoginStatusChangeListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void b() {
        try {
            a("dealCacheRefreshListeners() Deal cache listener size:" + this.f11890f.size());
            for (IRefreshSessionCallback iRefreshSessionCallback : this.f11890f) {
                if (iRefreshSessionCallback != null) {
                    iRefreshSessionCallback.onRefreshSuccess();
                }
            }
            this.f11890f.clear();
        } catch (Throwable th) {
            throw th;
        }
    }

    public void login(ILoginCallback iLoginCallback, Map<String, String> map) {
        OpenAccountUIService openAccountUIService = (OpenAccountUIService) OpenAccountSDK.getService(OpenAccountUIService.class);
        if (openAccountUIService == null) {
            return;
        }
        if (this.f11897m != null) {
            if (map == null) {
                map = new HashMap<>();
            }
            map.putAll(this.f11897m);
        }
        try {
            Class<?> cls = this.f11898n;
            if (cls != null) {
                if (map != null) {
                    openAccountUIService.showLogin(this.f11887c, cls, map, new OALoginCallback(iLoginCallback));
                    return;
                } else {
                    openAccountUIService.showLogin(this.f11887c, cls, new OALoginCallback(iLoginCallback));
                    return;
                }
            }
            if (map != null) {
                openAccountUIService.showLogin(this.f11887c, map, new OALoginCallback(iLoginCallback));
            } else {
                openAccountUIService.showLogin(this.f11887c, new OALoginCallback(iLoginCallback));
            }
        } catch (Exception e2) {
            a("login failed:" + e2.toString());
        }
    }

    @Override // com.aliyun.iot.aep.sdk.login.ILoginAdapter
    public void oauthLogin(Activity activity, int i2, ILoginCallback iLoginCallback) {
        ((OauthService) OpenAccountSDK.getService(OauthService.class)).oauth(activity, i2, new OALoginCallback(iLoginCallback));
    }

    public void init(String str, String str2) {
        init(str, str2, null);
    }

    public void init(String str, String str2, final OALoginAdapterInitResultCallback oALoginAdapterInitResultCallback) {
        a("init() OALoginAdapter , env is:" + str + "  " + str2 + "    thread:" + Thread.currentThread().getId());
        if ("TEST".equalsIgnoreCase(str)) {
            ConfigManager.getInstance().setEnvironment(Environment.TEST);
        } else if ("PRE".equalsIgnoreCase(str)) {
            ConfigManager.getInstance().setEnvironment(Environment.PRE);
        } else {
            ConfigManager.getInstance().setEnvironment(Environment.ONLINE);
        }
        if (!TextUtils.isEmpty(str2)) {
            ConfigManager.getInstance().setSecGuardImagePostfix(str2);
        } else {
            ConfigManager.getInstance().setSecGuardImagePostfix("114d");
        }
        ConfigManager.getInstance().setUseSingleImage(true);
        ConfigManager.getInstance().setAPIGateway(true);
        if (!TextUtils.isEmpty(this.f11896l)) {
            ConfigManager.getInstance().setApiGatewayHost(this.f11896l);
        }
        if (this.f11888d) {
            OpenAccountSDK.turnOnDebug();
        }
        OpenAccountSDK.syncInit(this.f11887c, new InitResultCallback() { // from class: com.aliyun.iot.aep.sdk.login.oa.OALoginAdapter.1
            @Override // com.alibaba.sdk.android.openaccount.callback.FailureCallback
            public void onFailure(int i2, String str3) {
                OALoginAdapter.this.a("OpenAccountSDK init failed:" + str3);
                OALoginAdapterInitResultCallback oALoginAdapterInitResultCallback2 = oALoginAdapterInitResultCallback;
                if (oALoginAdapterInitResultCallback2 != null) {
                    oALoginAdapterInitResultCallback2.onInitFailed(i2, str3);
                }
            }

            @Override // com.alibaba.sdk.android.openaccount.callback.InitResultCallback
            public void onSuccess() {
                OALoginAdapter.this.a("OpenAccountSDK init success");
                OALoginAdapter oALoginAdapter = OALoginAdapter.this;
                oALoginAdapter.f11894j = new b();
                ((OpenAccountService) OpenAccountSDK.getService(OpenAccountService.class)).addSessionListener(OALoginAdapter.this.f11894j);
                OALoginAdapter.this.a();
                OALoginAdapterInitResultCallback oALoginAdapterInitResultCallback2 = oALoginAdapterInitResultCallback;
                if (oALoginAdapterInitResultCallback2 != null) {
                    oALoginAdapterInitResultCallback2.onInitSuccess();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, String str2, ILoginCallback iLoginCallback) {
        if (iLoginCallback == null) {
            ALog.e("OALoginAdapter", "callback is null");
            return;
        }
        OALoginCallback oALoginCallback = new OALoginCallback(iLoginCallback);
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            HashMap map = new HashMap();
            map.put("loginId", str);
            try {
                String rsaPubkey = RSAKey.getRsaPubkey();
                if (TextUtils.isEmpty(rsaPubkey)) {
                    oALoginCallback.onFailure(-1, "error when encrypt password");
                    return;
                }
                map.put("password", Rsa.encrypt(str2, rsaPubkey));
                Result<LoginResult> loginResult = OpenAccountUtils.toLoginResult(RpcUtils.pureInvokeWithRiskControlInfo("loginRequest", map, FirebaseAnalytics.Event.LOGIN));
                try {
                    if (loginResult == null) {
                        oALoginCallback.onFailure(-1, "login failed");
                    } else {
                        int i2 = loginResult.code;
                        if (i2 != 1 && i2 != 2) {
                            oALoginCallback.onFailure(i2, loginResult.message);
                        } else {
                            a(loginResult);
                            OpenAccountService openAccountService = (OpenAccountService) OpenAccountSDK.getService(OpenAccountService.class);
                            if (openAccountService != null) {
                                oALoginCallback.onSuccess(openAccountService.getSession());
                            } else {
                                iLoginCallback.onLoginSuccess();
                            }
                        }
                    }
                    return;
                } catch (Throwable th) {
                    ALog.e("OALoginAdapter", "Login execute error" + th.toString());
                    oALoginCallback.onFailure(-1, "Login get result error");
                    return;
                }
            } catch (Exception unused) {
                oALoginCallback.onFailure(-1, "error when encrypt password");
                return;
            }
        }
        ALog.e("OALoginAdapter", "error parameter");
        oALoginCallback.onFailure(-1, "error parameter");
    }

    @Override // com.aliyun.iot.aep.sdk.login.ILoginAdapter
    public void login(final String str, final String str2, final ILoginCallback iLoginCallback) {
        ThreadPool.DefaultThreadPool.getInstance().submit(new Runnable() { // from class: com.aliyun.iot.aep.sdk.login.oa.OALoginAdapter.3
            @Override // java.lang.Runnable
            public void run() {
                OALoginAdapter.this.a(str, str2, iLoginCallback);
            }
        });
    }

    private void a(Result<LoginResult> result) {
        LoginResult loginResult = result.data;
        if (loginResult == null || loginResult.loginSuccessResult == null) {
            return;
        }
        SessionManagerService sessionManagerService = (SessionManagerService) OpenAccountSDK.getService(SessionManagerService.class);
        SessionData sessionDataCreateSessionDataFromLoginSuccessResult = OpenAccountUtils.createSessionDataFromLoginSuccessResult(result.data.loginSuccessResult);
        if (sessionDataCreateSessionDataFromLoginSuccessResult.scenario == null) {
            sessionDataCreateSessionDataFromLoginSuccessResult.scenario = 1;
        }
        sessionManagerService.updateSession(sessionDataCreateSessionDataFromLoginSuccessResult);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        SessionManagerService sessionManagerService = (SessionManagerService) OpenAccountSDK.getService(SessionManagerService.class);
        if (sessionManagerService == null) {
            return;
        }
        if (this.f11895k == null) {
            this.f11895k = new SessionInfo();
        }
        try {
            this.f11895k.sessionCreateTime = sessionManagerService.getSessionCreationTime().longValue();
            this.f11895k.sessionId = sessionManagerService.getSessionId();
            this.f11895k.sessionExpire = sessionManagerService.getSessionExpiredIn().intValue();
        } catch (Exception unused) {
        }
        a("updateSession() sessionInfo:" + this.f11895k.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String a(OpenAccountSession openAccountSession) {
        if (openAccountSession == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("userid:");
        sb.append(openAccountSession.getUserId());
        sb.append(" authorizationCode:");
        sb.append(openAccountSession.getAuthorizationCode());
        sb.append(" loginTime:");
        sb.append(openAccountSession.getLoginTime());
        sb.append(" user:");
        sb.append(openAccountSession.getUser() != null ? openAccountSession.getUser().toString() : "");
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        ALog.i("OALoginAdapter", str);
    }
}
