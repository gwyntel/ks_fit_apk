package com.aliyun.iot.aep.sdk.credential.IotCredentialManager;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClient;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientFactory;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Scheme;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestBuilder;
import com.aliyun.iot.aep.sdk.credential.data.CompanyData;
import com.aliyun.iot.aep.sdk.credential.data.IoTCredentialData;
import com.aliyun.iot.aep.sdk.credential.listener.IoTTokenCreatedListener;
import com.aliyun.iot.aep.sdk.credential.listener.IoTTokenInvalidListener;
import com.aliyun.iot.aep.sdk.credential.listener.OnReqCompanyCallback;
import com.aliyun.iot.aep.sdk.credential.oa.OADepBiz;
import com.aliyun.iot.aep.sdk.credential.utils.ReflectUtils;
import com.aliyun.iot.aep.sdk.log.ALog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class IoTCredentialManageImpl implements IoTCredentialManage {
    public static final String APP_DATA_FILE = "APP_DATA_FILE";
    public static final String AUTH_IOT_TOKEN_IDENTITY_ID_KEY = "identityId";
    public static final String AUTH_IOT_TOKEN_STATUS_CHANGE_BROADCAST = "com.ilop.auth.iotToken.change";
    public static final String AUTH_IOT_TOKEN_STATUS_INVALID = "invalid";
    public static final String AUTH_IOT_TOKEN_STATUS_KEY = "status";
    public static final String AUTH_IOT_TOKEN_STATUS_REFRESH_FAIL = "refreshFail";
    public static final String AUTH_IOT_TOKEN_STATUS_REFRESH_SUCCESS = "refreshSuccess";
    public static final String COMPANY_TYPE = "company";
    public static String DefaultDailyALiYunCreateIotTokenRequestHost = "";
    public static final String KEY_ACCOUNT_TYPE = "KEY_ACCOUNT_TYPE";

    /* renamed from: a, reason: collision with root package name */
    static String f11721a = null;
    public static String appKey = null;

    /* renamed from: b, reason: collision with root package name */
    private static boolean f11722b = false;

    /* renamed from: g, reason: collision with root package name */
    private static IoTCredentialManageImpl f11723g = null;

    /* renamed from: h, reason: collision with root package name */
    private static String f11724h = "";

    /* renamed from: c, reason: collision with root package name */
    private Context f11725c;

    /* renamed from: d, reason: collision with root package name */
    private volatile IoTCredentialData f11726d;

    /* renamed from: i, reason: collision with root package name */
    private IoTTokenInvalidListener f11729i;

    /* renamed from: j, reason: collision with root package name */
    private IoTCallback f11730j;

    /* renamed from: m, reason: collision with root package name */
    private String f11733m;

    /* renamed from: n, reason: collision with root package name */
    private OnReqCompanyCallback f11734n;

    /* renamed from: e, reason: collision with root package name */
    private volatile boolean f11727e = false;

    /* renamed from: f, reason: collision with root package name */
    private volatile List<IoTCredentialListener> f11728f = Collections.synchronizedList(new ArrayList());

    /* renamed from: k, reason: collision with root package name */
    private ICredentialDataSource f11731k = null;

    /* renamed from: l, reason: collision with root package name */
    private List<IoTTokenCreatedListener> f11732l = Collections.synchronizedList(new ArrayList());

    /* renamed from: o, reason: collision with root package name */
    private OnReqCompanyCallback f11735o = new OnReqCompanyCallback() { // from class: com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageImpl.1
        @Override // com.aliyun.iot.aep.sdk.credential.listener.OnReqCompanyCallback
        public void onFailure(IoTRequest ioTRequest, Exception exc) {
            ALog.e("IoTCredentialManage", "defaultOnReqCompanyCallback onFailure:" + exc.toString());
            IoTCredentialManageImpl.this.a(false, new IoTCredentialManageError(6, "companyId params can't empty"), (IoTRequest) null);
        }

        @Override // com.aliyun.iot.aep.sdk.credential.listener.OnReqCompanyCallback
        public void onSuccess(int i2, List<CompanyData> list) {
            if (i2 != 200 || list == null || list.size() <= 0 || list.get(0) == null) {
                ALog.i("IoTCredentialManage", "defaultOnReqCompanyCallback onSuccess empty");
                IoTCredentialManageImpl.this.a(false, new IoTCredentialManageError(6, "companyId params can't empty"), (IoTRequest) null);
                return;
            }
            ALog.i("IoTCredentialManage", "defaultOnReqCompanyCallback onSuccess companyId:" + list.get(0).companyId + " name:" + list.get(0).companyName);
            IoTCredentialManageImpl.this.setCompanyId(list.get(0).companyId);
            IoTCredentialManageImpl.this.asyncRefreshIoTCredential(null);
        }
    };

    class a implements OADepBiz.OALoginStatusChangeListener {
        private a() {
        }

        @Override // com.aliyun.iot.aep.sdk.credential.oa.OADepBiz.OALoginStatusChangeListener, com.aliyun.iot.aep.sdk.login.ILoginStatusChangeListener
        public void onLoginStatusChange() {
            if (ReflectUtils.hasOADep()) {
                if (!OADepBiz.isLogin()) {
                    ALog.i("IoTCredentialManage", "clear iotToken");
                    IoTCredentialManageImpl.this.clearIoTTokenInfo();
                    IoTCredentialManageImpl.this.setCompanyId("");
                    return;
                }
                ALog.i("IoTCredentialManage", "get Login Success info,clear local iotToken");
                if (IoTCredentialManageImpl.this.f11726d != null) {
                    IoTCredentialManageImpl.this.f11726d.clear();
                    IoTCredentialUtils.saveIoTCredentialData(IoTCredentialManageImpl.this.f11725c, IoTCredentialManageImpl.this.f11726d);
                }
                if (TextUtils.equals(IoTCredentialManageImpl.COMPANY_TYPE, IoTCredentialManageImpl.this.getAccountType())) {
                    ALog.i("IoTCredentialManage", "accountType is company, will not refresh IoTToken");
                } else {
                    IoTCredentialManageImpl.this.asyncRefreshIoTCredential(null);
                }
            }
        }
    }

    private IoTCredentialManageImpl(Context context) {
        if (!f11722b) {
            throw new IllegalArgumentException("must call init first");
        }
        ALog.i("IoTCredentialManage", "IoTTokenManager() init");
        if (context == null) {
            throw new IllegalArgumentException("Context Can't Be NULL");
        }
        this.f11725c = context;
        this.f11726d = IoTCredentialUtils.getIoTCredentialData(context);
        StringBuilder sb = new StringBuilder();
        sb.append("IoTCredentialManageImpl(): ioTCredentialData:");
        sb.append(this.f11726d == null ? "" : this.f11726d.toString());
        sb.append(" getIoTToken:");
        sb.append(getIoTToken());
        ALog.i("IoTCredentialManage", sb.toString());
        if (ReflectUtils.hasOADep()) {
            OADepBiz.registerLoginListener(new a());
        }
        f11724h = getAccountType();
    }

    public static IoTCredentialManageImpl getInstance(Application application) {
        if (f11723g == null) {
            synchronized (IoTCredentialManageImpl.class) {
                try {
                    if (f11723g == null) {
                        f11723g = new IoTCredentialManageImpl(application);
                    }
                } finally {
                }
            }
        }
        return f11723g;
    }

    public static void init(String str) {
        if (f11722b) {
            return;
        }
        f11722b = true;
        if (ReflectUtils.hasOADep() && !OADepBiz.hasOAAdapter()) {
            throw new IllegalArgumentException("loginAdapter can't be null, need call LoginBusiness.init first");
        }
        appKey = str;
    }

    @Override // com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManage
    public void asyncRefreshIoTCredential(IoTCredentialListener ioTCredentialListener) {
        ALog.i("IoTCredentialManage", "asyncRefreshIoTCredential ()  isRefreshing? " + this.f11727e);
        if (ioTCredentialListener != null) {
            this.f11728f.add(ioTCredentialListener);
        }
        if (this.f11727e) {
            return;
        }
        this.f11727e = true;
        if (!(ReflectUtils.hasOADep() && OADepBiz.isLogin()) && this.f11731k == null) {
            a(false, new IoTCredentialManageError(0, null), (IoTRequest) null);
        } else {
            a();
        }
    }

    public void clearIoTTokenInfo() {
        if (this.f11726d != null) {
            IoTCallback ioTCallback = new IoTCallback() { // from class: com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageImpl.5
                @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                public void onFailure(IoTRequest ioTRequest, Exception exc) {
                }

                @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                }
            };
            if (this.f11731k != null) {
                ALog.i("IoTCredentialManage", "user external credentialDataSource invalid session.");
                this.f11731k.credentialDidDelete(ioTCallback);
            } else {
                new IoTAPIClientFactory().getClient().send(IoTCredentialUtils.getInvalidSessionRequest(getIoTToken(), this.f11726d.identity, f11724h), ioTCallback);
            }
            a(this.f11725c, this.f11726d == null ? "" : this.f11726d.identity, AUTH_IOT_TOKEN_STATUS_INVALID);
            this.f11726d.clear();
            ALog.i("IoTCredentialManage", "clear token data");
            IoTCredentialUtils.saveIoTCredentialData(this.f11725c, this.f11726d);
        }
    }

    public String getAccountType() {
        Context context = this.f11725c;
        return context == null ? "" : context.getSharedPreferences(APP_DATA_FILE, 0).getString(KEY_ACCOUNT_TYPE, "");
    }

    @Override // com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManage
    public IoTCredentialData getIoTCredential() {
        return this.f11726d;
    }

    @Override // com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManage
    public String getIoTIdentity() {
        if (this.f11726d == null) {
            return null;
        }
        return this.f11726d.identity;
    }

    @Override // com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManage
    public String getIoTRefreshToken() {
        return this.f11726d == null ? "" : this.f11726d.refreshToken;
    }

    @Override // com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManage
    public String getIoTToken() {
        return this.f11726d != null ? this.f11726d.iotToken : "";
    }

    @Override // com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManage
    public boolean isIoTRefreshTokenExpired() {
        if (this.f11726d == null) {
            return true;
        }
        return this.f11726d.isRefreshTokenExpire();
    }

    @Override // com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManage
    public boolean isIoTTokenExpired() {
        if (this.f11726d == null) {
            return true;
        }
        return this.f11726d.isIotTokenExpire();
    }

    public void registerIotTokenCreatedListener(IoTTokenCreatedListener ioTTokenCreatedListener) {
        if (ioTTokenCreatedListener == null) {
            return;
        }
        this.f11732l.add(ioTTokenCreatedListener);
    }

    public void requestCompanyList(final String str, final OnReqCompanyCallback onReqCompanyCallback) {
        HashMap map = new HashMap(2);
        map.put("authCode", str);
        map.put("codeType", "SESSION_ID");
        new IoTAPIClientFactory().getClient().send(new IoTRequestBuilder().setScheme(Scheme.HTTPS).setPath("/user/account/company/listbyauthcode").setApiVersion("1.0.1").setParams(map).build(), new IoTCallback() { // from class: com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageImpl.2
            @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
            public void onFailure(IoTRequest ioTRequest, Exception exc) {
                OnReqCompanyCallback onReqCompanyCallback2 = onReqCompanyCallback;
                if (onReqCompanyCallback2 != null) {
                    onReqCompanyCallback2.onFailure(ioTRequest, exc);
                }
            }

            @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
            public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                if (ioTResponse.getCode() != 200) {
                    IoTCredentialManageImpl.this.a(str, onReqCompanyCallback);
                } else if (JSON.parseArray(ioTResponse.getData().toString()).size() <= 0) {
                    IoTCredentialManageImpl.this.a(str, onReqCompanyCallback);
                } else {
                    onReqCompanyCallback.onSuccess(ioTResponse.getCode(), JSON.parseArray(ioTResponse.getData().toString(), CompanyData.class));
                }
            }
        });
    }

    public void setAccountType(String str) {
        f11724h = str;
        Context context = this.f11725c;
        if (context == null) {
            return;
        }
        context.getSharedPreferences(APP_DATA_FILE, 0).edit().putString(KEY_ACCOUNT_TYPE, str).apply();
    }

    public void setAccountTypeCompany() {
        setAccountType(COMPANY_TYPE);
    }

    public void setAuthCode(String str) {
        f11721a = str;
    }

    public void setCompanyId(String str) {
        this.f11733m = str;
    }

    @Deprecated
    public void setIotCredentialListenerList(IoTTokenInvalidListener ioTTokenInvalidListener) {
        this.f11729i = ioTTokenInvalidListener;
    }

    public void setIotCredentialPlugin(ICredentialDataSource iCredentialDataSource) {
        ALog.d("IoTCredentialManage", "setIotCredentialPlugin() called with: dataSource = [" + iCredentialDataSource + "]");
        this.f11731k = iCredentialDataSource;
    }

    public void setIotTokenInvalidListener(IoTTokenInvalidListener ioTTokenInvalidListener) {
        this.f11729i = ioTTokenInvalidListener;
    }

    public void setOnReqCompanyCallback(OnReqCompanyCallback onReqCompanyCallback) {
        this.f11734n = onReqCompanyCallback;
    }

    public void unRegisterIotTokenCreatedListener(IoTTokenCreatedListener ioTTokenCreatedListener) {
        if (this.f11732l.isEmpty() || ioTTokenCreatedListener == null) {
            return;
        }
        try {
            this.f11732l.remove(ioTTokenCreatedListener);
        } catch (Exception e2) {
            ALog.i("IoTCredentialManage", "unRegisterIotTokenCreatedListener error:" + e2.toString());
        }
    }

    void a(String str, final OnReqCompanyCallback onReqCompanyCallback) {
        HashMap map = new HashMap(2);
        map.put("authCode", str);
        map.put("codeType", "SESSION_ID");
        new IoTAPIClientFactory().getClient().send(new IoTRequestBuilder().setScheme(Scheme.HTTPS).setPath("/user/account/employee/createbyauthcode").setApiVersion("1.0.0").setParams(map).build(), new IoTCallback() { // from class: com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageImpl.3
            @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
            public void onFailure(IoTRequest ioTRequest, Exception exc) {
                OnReqCompanyCallback onReqCompanyCallback2 = onReqCompanyCallback;
                if (onReqCompanyCallback2 != null) {
                    onReqCompanyCallback2.onFailure(ioTRequest, exc);
                }
            }

            @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
            public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                if (ioTResponse.getCode() != 200) {
                    onReqCompanyCallback.onSuccess(ioTResponse.getCode(), null);
                    return;
                }
                if (ioTResponse.getData() == null) {
                    onReqCompanyCallback.onSuccess(ioTResponse.getCode(), null);
                    return;
                }
                JSONObject jSONObject = (JSONObject) ioTResponse.getData();
                CompanyData companyData = new CompanyData();
                companyData.companyId = jSONObject.optString("companyId");
                companyData.companyName = jSONObject.optString("companyName");
                ArrayList arrayList = new ArrayList();
                arrayList.add(companyData);
                onReqCompanyCallback.onSuccess(ioTResponse.getCode(), arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a(boolean z2, IoTCredentialManageError ioTCredentialManageError, IoTRequest ioTRequest) {
        IoTTokenInvalidListener ioTTokenInvalidListener;
        try {
            ALog.i("IoTCredentialManage", "entering dealCacheIoTTokenListeners");
            ALog.i("IoTCredentialManage", "dealCacheIoTTokenListeners()  result:" + z2);
            if (ioTCredentialManageError != null && ioTCredentialManageError.errorCode == 3 && (ioTTokenInvalidListener = this.f11729i) != null) {
                ioTTokenInvalidListener.onIoTTokenInvalid();
            }
            int i2 = 0;
            this.f11727e = false;
            if (z2) {
                IoTCredentialUtils.saveIoTCredentialData(this.f11725c, this.f11726d);
                List<IoTTokenCreatedListener> list = this.f11732l;
                if (list != null && !list.isEmpty() && ioTRequest != null) {
                    ALog.i("IoTCredentialManage", "ioTTokenCreatedListenerList is not empty ,size is :" + this.f11732l.size());
                    while (true) {
                        String[] strArr = IoTCredentialUtils.CREATE_IOTTOKEN_REQUEST_PATH_ARRAY;
                        if (i2 >= strArr.length) {
                            break;
                        }
                        if (TextUtils.equals(strArr[i2], ioTRequest.getPath())) {
                            ArrayList arrayList = new ArrayList();
                            arrayList.addAll(this.f11732l);
                            ALog.i("IoTCredentialManage", "start to exec iottokenCreatedListener callback list");
                            Iterator it = arrayList.iterator();
                            while (it.hasNext()) {
                                ((IoTTokenCreatedListener) it.next()).onIoTTokenCreated();
                            }
                        }
                        i2++;
                    }
                }
                a(this.f11725c, this.f11726d == null ? "" : this.f11726d.identity, AUTH_IOT_TOKEN_STATUS_REFRESH_SUCCESS);
            } else {
                a(this.f11725c, this.f11726d == null ? "" : this.f11726d.identity, AUTH_IOT_TOKEN_STATUS_REFRESH_FAIL);
            }
        } catch (Exception e2) {
            ALog.i("IoTCredentialManage", "dealCacheIoTTokenListeners exception :" + e2.toString());
        } finally {
        }
        if (this.f11728f != null && !this.f11728f.isEmpty()) {
            ArrayList<IoTCredentialListener> arrayList2 = new ArrayList();
            arrayList2.addAll(this.f11728f);
            this.f11728f.clear();
            ALog.i("IoTCredentialManage", "dealCacheIoTTokenListeners listener callback start");
            for (IoTCredentialListener ioTCredentialListener : arrayList2) {
                if (ioTCredentialListener != null) {
                    if (z2) {
                        ioTCredentialListener.onRefreshIoTCredentialSuccess(getIoTCredential());
                    } else {
                        ioTCredentialListener.onRefreshIoTCredentialFailed(ioTCredentialManageError);
                    }
                }
            }
            ALog.i("IoTCredentialManage", "dealCacheIoTTokenListeners listener callback end");
            ALog.i("IoTCredentialManage", "leaving dealCacheIoTTokenListeners");
        }
    }

    private void a(Context context, String str, String str2) {
        ALog.d("IoTCredentialManage", "broadcastIoTRefreshStatus() called with: appContext = [" + context + "], identityId = [" + str + "], status = [" + str2 + "]");
        if (context == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setPackage(context.getPackageName());
        intent.setAction("com.ilop.auth.iotToken.change");
        intent.putExtra("status", str2);
        intent.putExtra(AUTH_IOT_TOKEN_IDENTITY_ID_KEY, str);
        LocalBroadcastManager.getInstance(context.getApplicationContext()).sendBroadcast(intent);
    }

    private void a() {
        IoTRequest refreshIoTCredentialRequest;
        ALog.i("IoTCredentialManage", "refreshIoTTokenLocked()");
        if (TextUtils.isEmpty(getIoTToken())) {
            ALog.i("IoTCredentialManage", "refreshIoTCredentialLocked():iotToken is empty,need create iotToken first, sessionid is: " + OADepBiz.getSessionId());
            refreshIoTCredentialRequest = IoTCredentialUtils.getCreateIoTCredentialRequest(OADepBiz.getSessionId(), appKey, f11724h, this.f11733m);
        } else {
            ALog.i("IoTCredentialManage", "refreshIoTTokenLocked():iotToken is not empty,need refresh iotToken, sessionid is: " + OADepBiz.getSessionId());
            refreshIoTCredentialRequest = IoTCredentialUtils.getRefreshIoTCredentialRequest(getIoTRefreshToken(), getIoTIdentity());
        }
        if (refreshIoTCredentialRequest == null) {
            ALog.i("IoTCredentialManage", "refreshIoTTokenLocked(): accountType is company but companyId is empty");
            OnReqCompanyCallback onReqCompanyCallback = this.f11734n;
            if (onReqCompanyCallback == null) {
                onReqCompanyCallback = this.f11735o;
            }
            requestCompanyList(OADepBiz.getSessionId(), onReqCompanyCallback);
            this.f11727e = false;
            return;
        }
        IoTAPIClient client = new IoTAPIClientFactory().getClient();
        IoTCallback ioTCallback = new IoTCallback() { // from class: com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageImpl.4
            @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
            public void onFailure(IoTRequest ioTRequest, Exception exc) {
                IoTCredentialManageImpl.this.f11727e = false;
                ALog.i("IoTCredentialManage", "refreshIoTTokenLocked() failed: " + exc.toString());
                IoTCredentialManageImpl.this.a(false, new IoTCredentialManageError(-1, exc), ioTRequest);
            }

            @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
            public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                IoTCredentialManageError ioTCredentialManageError;
                boolean z2 = false;
                IoTCredentialManageImpl.this.f11727e = false;
                if (ioTResponse == null) {
                    IoTCredentialManageImpl.this.a(false, new IoTCredentialManageError(-1, ioTResponse), ioTRequest);
                    return;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("onResponse():");
                sb.append(ioTResponse.getData() == null ? TmpConstant.GROUP_ROLE_UNKNOWN : ioTResponse.getData().toString());
                ALog.i("IoTCredentialManage", sb.toString());
                if (ioTResponse.getCode() != 200) {
                    IoTCredentialManageImpl.this.a(false, (ioTResponse.getCode() == 2460 || ioTResponse.getCode() == 2401 || ioTResponse.getCode() == 2405) ? new IoTCredentialManageError(3, ioTResponse) : ioTResponse.getCode() == 2459 ? new IoTCredentialManageError(5, ioTResponse) : (ioTResponse.getCode() == 2462 || ioTResponse.getCode() == 2407) ? new IoTCredentialManageError(2, ioTResponse) : ioTResponse.getCode() == 2461 ? new IoTCredentialManageError(1, ioTResponse) : new IoTCredentialManageError(-1, ioTResponse), ioTRequest);
                } else if (ioTResponse.getData() != null) {
                    try {
                        if (ioTResponse.getData() instanceof JSONObject) {
                            IoTCredentialManageImpl.this.f11726d.update((JSONObject) ioTResponse.getData());
                            ALog.i("IoTCredentialManage", "update ioTCredentialData success, new token data is: " + IoTCredentialManageImpl.this.f11726d.toString());
                        }
                        ioTCredentialManageError = null;
                        z2 = true;
                    } catch (JSONException e2) {
                        IoTCredentialManageError ioTCredentialManageError2 = new IoTCredentialManageError(4, ioTResponse);
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("onResponse(): parse tokenJson error, original json: ");
                        sb2.append(ioTResponse.getData() == null ? "" : ioTResponse.getData().toString());
                        sb2.append(" exception: ");
                        sb2.append(e2.toString());
                        ALog.i("IoTCredentialManage", sb2.toString());
                        ioTCredentialManageError = ioTCredentialManageError2;
                    }
                    ALog.i("IoTCredentialManage", "before enter dealCacheIoTTokenListeners");
                    IoTCredentialManageImpl.this.a(z2, ioTCredentialManageError, ioTRequest);
                    ALog.i("IoTCredentialManage", "after enter dealCacheIoTTokenListeners");
                    return;
                }
                IoTCredentialManageImpl.this.a(false, new IoTCredentialManageError(-1, ioTResponse), ioTRequest);
            }
        };
        this.f11730j = ioTCallback;
        if (this.f11731k != null) {
            ALog.i("IoTCredentialManage", "use external credentialDataSource = " + this.f11731k);
            if (TextUtils.isEmpty(getIoTToken())) {
                this.f11731k.credentialDidCreate(this.f11730j);
                return;
            } else {
                this.f11731k.credentialDidUpdate(this.f11730j);
                return;
            }
        }
        client.send(refreshIoTCredentialRequest, ioTCallback);
    }
}
