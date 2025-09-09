package com.huawei.hms.common.internal;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import com.huawei.hms.adapter.AvailableAdapter;
import com.huawei.hms.adapter.BinderAdapter;
import com.huawei.hms.adapter.InnerBinderAdapter;
import com.huawei.hms.adapter.OuterBinderAdapter;
import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.FailedBinderCallBack;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.huawei.hms.api.IPCTransport;
import com.huawei.hms.common.HuaweiApi;
import com.huawei.hms.core.aidl.IAIDLInvoke;
import com.huawei.hms.support.api.client.AidlApiClient;
import com.huawei.hms.support.api.client.SubAppInfo;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.HMSPackageManager;
import com.huawei.hms.utils.Util;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public abstract class BaseHmsClient implements AidlApiClient {
    protected static final int TIMEOUT_DISCONNECTED = 6;

    /* renamed from: i, reason: collision with root package name */
    private static final Object f15939i = new Object();

    /* renamed from: j, reason: collision with root package name */
    private static final AtomicInteger f15940j = new AtomicInteger(1);

    /* renamed from: k, reason: collision with root package name */
    private static final AtomicInteger f15941k = new AtomicInteger(1);

    /* renamed from: l, reason: collision with root package name */
    private static BinderAdapter f15942l;

    /* renamed from: m, reason: collision with root package name */
    private static BinderAdapter f15943m;

    /* renamed from: a, reason: collision with root package name */
    private final Context f15944a;

    /* renamed from: b, reason: collision with root package name */
    private String f15945b;

    /* renamed from: c, reason: collision with root package name */
    private final ClientSettings f15946c;

    /* renamed from: d, reason: collision with root package name */
    private volatile IAIDLInvoke f15947d;

    /* renamed from: e, reason: collision with root package name */
    private final ConnectionCallbacks f15948e;

    /* renamed from: f, reason: collision with root package name */
    private final OnConnectionFailedListener f15949f;

    /* renamed from: g, reason: collision with root package name */
    private Handler f15950g = null;

    /* renamed from: h, reason: collision with root package name */
    private HuaweiApi.RequestHandler f15951h;
    protected String sessionId;

    public interface ConnectionCallbacks {
        public static final int CAUSE_API_CLIENT_EXPIRED = 3;
        public static final int CAUSE_NETWORK_LOST = 2;
        public static final int CAUSE_SERVICE_DISCONNECTED = 1;

        void onConnected();

        void onConnectionSuspended(int i2);
    }

    public static final class ConnectionResultWrapper {

        /* renamed from: a, reason: collision with root package name */
        private HuaweiApi.RequestHandler f15956a;

        /* renamed from: b, reason: collision with root package name */
        private ConnectionResult f15957b;

        public ConnectionResultWrapper(HuaweiApi.RequestHandler requestHandler, ConnectionResult connectionResult) {
            this.f15956a = requestHandler;
            this.f15957b = connectionResult;
        }

        public ConnectionResult getConnectionResult() {
            return this.f15957b;
        }

        public HuaweiApi.RequestHandler getRequest() {
            return this.f15956a;
        }
    }

    public interface OnConnectionFailedListener {
        void onConnectionFailed(ConnectionResult connectionResult);
    }

    public BaseHmsClient(Context context, ClientSettings clientSettings, OnConnectionFailedListener onConnectionFailedListener, ConnectionCallbacks connectionCallbacks) {
        this.f15944a = context;
        this.f15946c = clientSettings;
        this.f15945b = clientSettings.getAppID();
        this.f15949f = onConnectionFailedListener;
        this.f15948e = connectionCallbacks;
    }

    private BinderAdapter.BinderCallBack c() {
        return new BinderAdapter.BinderCallBack() { // from class: com.huawei.hms.common.internal.BaseHmsClient.1
            @Override // com.huawei.hms.adapter.BinderAdapter.BinderCallBack
            public void onBinderFailed(int i2) {
                onBinderFailed(i2, null);
            }

            @Override // com.huawei.hms.adapter.BinderAdapter.BinderCallBack
            public void onNullBinding(ComponentName componentName) {
                BaseHmsClient.this.b(1);
                BaseHmsClient.this.a(10);
            }

            @Override // com.huawei.hms.adapter.BinderAdapter.BinderCallBack
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                HMSLog.i("BaseHmsClient", "Enter onServiceConnected.");
                BaseHmsClient.this.connectedInternal(iBinder);
            }

            @Override // com.huawei.hms.adapter.BinderAdapter.BinderCallBack
            public void onServiceDisconnected(ComponentName componentName) {
                HMSLog.i("BaseHmsClient", "Enter onServiceDisconnected.");
                BaseHmsClient.this.b(1);
                RequestManager.getHandler().sendEmptyMessage(10013);
                if (BaseHmsClient.this.f15948e == null || (BaseHmsClient.this.f15948e instanceof HuaweiApi.RequestHandler)) {
                    return;
                }
                BaseHmsClient.this.f15948e.onConnectionSuspended(1);
            }

            @Override // com.huawei.hms.adapter.BinderAdapter.BinderCallBack
            public void onTimedDisconnected() {
                BaseHmsClient.this.b(6);
                if (BaseHmsClient.this.f15948e == null || (BaseHmsClient.this.f15948e instanceof HuaweiApi.RequestHandler)) {
                    return;
                }
                BaseHmsClient.this.f15948e.onConnectionSuspended(1);
            }

            @Override // com.huawei.hms.adapter.BinderAdapter.BinderCallBack
            public void onBinderFailed(int i2, Intent intent) {
                if (intent == null) {
                    HMSLog.i("BaseHmsClient", "onBinderFailed: intent is null!");
                    BaseHmsClient.this.a(new ConnectionResult(10, (PendingIntent) null));
                    BaseHmsClient.this.f15947d = null;
                    return;
                }
                Activity activeActivity = Util.getActiveActivity(BaseHmsClient.this.getClientSettings().getCpActivity(), BaseHmsClient.this.getContext());
                if (activeActivity == null) {
                    HMSLog.i("BaseHmsClient", "onBinderFailed: return pendingIntent to kit and cp");
                    BaseHmsClient.this.a(new ConnectionResult(10, PendingIntent.getActivity(BaseHmsClient.this.f15944a, 11, intent, AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL)));
                    BaseHmsClient.this.f15947d = null;
                    return;
                }
                HMSLog.i("BaseHmsClient", "onBinderFailed: SDK try to resolve and reConnect!");
                long time = new Timestamp(System.currentTimeMillis()).getTime();
                FailedBinderCallBack.getInstance().setCallBack(Long.valueOf(time), new FailedBinderCallBack.BinderCallBack() { // from class: com.huawei.hms.common.internal.BaseHmsClient.1.1
                    @Override // com.huawei.hms.api.FailedBinderCallBack.BinderCallBack
                    public void binderCallBack(int i3) {
                        if (i3 != 0) {
                            BaseHmsClient.this.a(new ConnectionResult(10, (PendingIntent) null));
                            BaseHmsClient.this.f15947d = null;
                        }
                    }
                });
                intent.putExtra(FailedBinderCallBack.CALLER_ID, time);
                activeActivity.startActivity(intent);
            }
        };
    }

    private void d() {
        HMSLog.w("BaseHmsClient", "Failed to get service as interface, trying to unbind.");
        if (this.f15946c.isUseInnerHms()) {
            BinderAdapter binderAdapter = f15943m;
            if (binderAdapter == null) {
                HMSLog.w("BaseHmsClient", "mInnerBinderAdapter is null.");
                return;
            }
            binderAdapter.unBind();
        } else {
            BinderAdapter binderAdapter2 = f15942l;
            if (binderAdapter2 == null) {
                HMSLog.w("BaseHmsClient", "mOuterBinderAdapter is null.");
                return;
            }
            binderAdapter2.unBind();
        }
        b(1);
        a(10);
    }

    private void e() {
        if (this.f15946c.isUseInnerHms()) {
            BinderAdapter binderAdapter = f15943m;
            if (binderAdapter != null) {
                binderAdapter.unBind();
                return;
            }
            return;
        }
        BinderAdapter binderAdapter2 = f15942l;
        if (binderAdapter2 != null) {
            binderAdapter2.unBind();
        }
    }

    protected final void checkConnected() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    public void connect(int i2) {
        a(i2, false);
    }

    public void connectedInternal(IBinder iBinder) {
        this.f15947d = IAIDLInvoke.Stub.asInterface(iBinder);
        if (this.f15947d != null) {
            onConnecting();
        } else {
            HMSLog.e("BaseHmsClient", "mService is null, try to unBind.");
            d();
        }
    }

    protected final void connectionConnected() {
        b(3);
        RequestManager.getHandler().sendEmptyMessage(10011);
        ConnectionCallbacks connectionCallbacks = this.f15948e;
        if (connectionCallbacks == null || (connectionCallbacks instanceof HuaweiApi.RequestHandler)) {
            return;
        }
        connectionCallbacks.onConnected();
    }

    public void disconnect() {
        int i2 = (this.f15946c.isUseInnerHms() ? f15941k : f15940j).get();
        HMSLog.i("BaseHmsClient", "Enter disconnect, Connection Status: " + i2);
        if (i2 == 3) {
            e();
            b(1);
        } else {
            if (i2 != 5) {
                return;
            }
            b();
            b(1);
        }
    }

    public BinderAdapter getAdapter() {
        HMSLog.i("BaseHmsClient", "getAdapter:isInner:" + this.f15946c.isUseInnerHms() + ", mInnerBinderAdapter:" + f15943m + ", mOuterBinderAdapter:" + f15942l);
        return this.f15946c.isUseInnerHms() ? f15943m : f15942l;
    }

    @Override // com.huawei.hms.support.api.client.AidlApiClient
    public List<String> getApiNameList() {
        return this.f15946c.getApiName();
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public String getAppID() {
        return this.f15945b;
    }

    protected ClientSettings getClientSettings() {
        return this.f15946c;
    }

    public int getConnectionStatus() {
        return (this.f15946c.isUseInnerHms() ? f15941k : f15940j).get();
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public Context getContext() {
        return this.f15944a;
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public String getCpID() {
        return this.f15946c.getCpID();
    }

    @Deprecated
    public int getMinApkVersion() {
        return 30000000;
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public String getPackageName() {
        return this.f15946c.getClientPackageName();
    }

    public int getRequestHmsVersionCode() {
        return getMinApkVersion();
    }

    @Override // com.huawei.hms.support.api.client.AidlApiClient
    public IAIDLInvoke getService() {
        return this.f15947d;
    }

    public String getServiceAction() {
        HMSPackageManager hMSPackageManager = HMSPackageManager.getInstance(this.f15944a);
        return this.f15946c.isUseInnerHms() ? hMSPackageManager.getInnerServiceAction() : hMSPackageManager.getServiceAction();
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public String getSessionId() {
        return this.sessionId;
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public SubAppInfo getSubAppInfo() {
        return this.f15946c.getSubAppID();
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public String getTransportName() {
        return IPCTransport.class.getName();
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public boolean isConnected() {
        return !this.f15946c.isUseInnerHms() ? f15940j.get() != 3 : f15941k.get() != 3;
    }

    public boolean isConnecting() {
        return (this.f15946c.isUseInnerHms() ? f15941k : f15940j).get() == 5;
    }

    public void onConnecting() {
        connectionConnected();
    }

    public final void setInternalRequest(HuaweiApi.RequestHandler requestHandler) {
        this.f15951h = requestHandler;
    }

    public void setService(IAIDLInvoke iAIDLInvoke) {
        this.f15947d = iAIDLInvoke;
    }

    void b(int i2) {
        if (this.f15946c.isUseInnerHms()) {
            f15941k.set(i2);
        } else {
            f15940j.set(i2);
        }
    }

    public void connect(int i2, boolean z2) {
        a(i2, z2);
    }

    private void b() {
        synchronized (f15939i) {
            try {
                Handler handler = this.f15950g;
                if (handler != null) {
                    handler.removeMessages(2);
                    this.f15950g = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    void a() {
        String innerHmsPkg = this.f15946c.getInnerHmsPkg();
        String serviceAction = getServiceAction();
        HMSLog.i("BaseHmsClient", "enter bindCoreService, packageName is " + innerHmsPkg + ", serviceAction is " + serviceAction);
        a(innerHmsPkg, serviceAction);
    }

    private void a(String str, String str2) {
        if (this.f15946c.isUseInnerHms()) {
            f15943m = InnerBinderAdapter.getInstance(this.f15944a, str2, str);
            if (isConnected()) {
                HMSLog.i("BaseHmsClient", "The binder is already connected.");
                getAdapter().updateDelayTask();
                connectedInternal(getAdapter().getServiceBinder());
                return;
            } else {
                b(5);
                f15943m.binder(c());
                return;
            }
        }
        f15942l = OuterBinderAdapter.getInstance(this.f15944a, str2, str);
        if (isConnected()) {
            HMSLog.i("BaseHmsClient", "The binder is already connected.");
            getAdapter().updateDelayTask();
            connectedInternal(getAdapter().getServiceBinder());
        } else {
            b(5);
            f15942l.binder(c());
        }
    }

    private void b(AvailableAdapter availableAdapter, int i2) {
        HMSLog.i("BaseHmsClient", "enter HmsCore resolution");
        if (!getClientSettings().isHasActivity()) {
            a(new ConnectionResult(26, HuaweiApiAvailability.getInstance().getErrPendingIntent(this.f15944a, i2, 0)));
            return;
        }
        Activity activeActivity = Util.getActiveActivity(getClientSettings().getCpActivity(), getContext());
        if (activeActivity != null) {
            availableAdapter.startResolution(activeActivity, new AvailableAdapter.AvailableCallBack() { // from class: com.huawei.hms.common.internal.BaseHmsClient.3
                @Override // com.huawei.hms.adapter.AvailableAdapter.AvailableCallBack
                public void onComplete(int i3) {
                    if (i3 == 0) {
                        BaseHmsClient.this.a();
                    } else {
                        BaseHmsClient.this.a(i3);
                    }
                }
            });
        } else {
            a(26);
        }
    }

    private void a(int i2, boolean z2) {
        HMSLog.i("BaseHmsClient", "====== HMSSDK version: 60800300 ======");
        int i3 = (this.f15946c.isUseInnerHms() ? f15941k : f15940j).get();
        HMSLog.i("BaseHmsClient", "Enter connect, Connection Status: " + i3);
        if (z2 || !(i3 == 3 || i3 == 5)) {
            if (getMinApkVersion() > i2) {
                i2 = getMinApkVersion();
            }
            HMSLog.i("BaseHmsClient", "connect minVersion:" + i2 + " packageName:" + this.f15946c.getInnerHmsPkg());
            if (this.f15944a.getPackageName().equals(this.f15946c.getInnerHmsPkg())) {
                HMSLog.i("BaseHmsClient", "service packageName is same, bind core service return");
                a();
                return;
            }
            if (Util.isAvailableLibExist(this.f15944a)) {
                AvailableAdapter availableAdapter = new AvailableAdapter(i2);
                int iIsHuaweiMobileServicesAvailable = availableAdapter.isHuaweiMobileServicesAvailable(this.f15944a);
                HMSLog.i("BaseHmsClient", "check available result: " + iIsHuaweiMobileServicesAvailable);
                if (iIsHuaweiMobileServicesAvailable == 0) {
                    a();
                    return;
                }
                if (availableAdapter.isUserResolvableError(iIsHuaweiMobileServicesAvailable)) {
                    HMSLog.i("BaseHmsClient", "bindCoreService3.0 fail, start resolution now.");
                    b(availableAdapter, iIsHuaweiMobileServicesAvailable);
                    return;
                } else {
                    if (availableAdapter.isUserNoticeError(iIsHuaweiMobileServicesAvailable)) {
                        HMSLog.i("BaseHmsClient", "bindCoreService3.0 fail, start notice now.");
                        a(availableAdapter, iIsHuaweiMobileServicesAvailable);
                        return;
                    }
                    HMSLog.i("BaseHmsClient", "bindCoreService3.0 fail: " + iIsHuaweiMobileServicesAvailable + " is not resolvable.");
                    a(iIsHuaweiMobileServicesAvailable);
                    return;
                }
            }
            int iIsHuaweiMobileServicesAvailable2 = HuaweiApiAvailability.getInstance().isHuaweiMobileServicesAvailable(this.f15944a, i2);
            HMSLog.i("BaseHmsClient", "HuaweiApiAvailability check available result: " + iIsHuaweiMobileServicesAvailable2);
            if (iIsHuaweiMobileServicesAvailable2 == 0) {
                a();
            } else {
                a(iIsHuaweiMobileServicesAvailable2);
            }
        }
    }

    private void a(AvailableAdapter availableAdapter, int i2) {
        HMSLog.i("BaseHmsClient", "enter notice");
        if (!getClientSettings().isHasActivity()) {
            if (i2 == 29) {
                i2 = 9;
            }
            a(new ConnectionResult(26, HuaweiApiAvailability.getInstance().getErrPendingIntent(this.f15944a, i2, 0)));
        } else {
            Activity activeActivity = Util.getActiveActivity(getClientSettings().getCpActivity(), getContext());
            if (activeActivity != null) {
                availableAdapter.startNotice(activeActivity, new AvailableAdapter.AvailableCallBack() { // from class: com.huawei.hms.common.internal.BaseHmsClient.2
                    @Override // com.huawei.hms.adapter.AvailableAdapter.AvailableCallBack
                    public void onComplete(int i3) {
                        BaseHmsClient.this.a(i3);
                    }
                });
            } else {
                a(26);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i2) {
        HMSLog.i("BaseHmsClient", "notifyFailed result: " + i2);
        Message message = new Message();
        message.what = 10012;
        message.obj = new ConnectionResultWrapper(this.f15951h, new ConnectionResult(i2));
        RequestManager.getHandler().sendMessage(message);
        OnConnectionFailedListener onConnectionFailedListener = this.f15949f;
        if (onConnectionFailedListener == null || (onConnectionFailedListener instanceof HuaweiApi.RequestHandler)) {
            return;
        }
        onConnectionFailedListener.onConnectionFailed(new ConnectionResult(i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ConnectionResult connectionResult) {
        HMSLog.i("BaseHmsClient", "notifyFailed result: " + connectionResult.getErrorCode());
        Message message = new Message();
        message.what = 10012;
        HuaweiApi.RequestHandler requestHandler = this.f15951h;
        this.f15951h = null;
        message.obj = new ConnectionResultWrapper(requestHandler, connectionResult);
        RequestManager.getHandler().sendMessage(message);
        OnConnectionFailedListener onConnectionFailedListener = this.f15949f;
        if (onConnectionFailedListener == null || (onConnectionFailedListener instanceof HuaweiApi.RequestHandler)) {
            return;
        }
        onConnectionFailedListener.onConnectionFailed(connectionResult);
    }
}
