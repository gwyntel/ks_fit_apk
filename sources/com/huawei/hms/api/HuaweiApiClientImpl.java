package com.huawei.hms.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import androidx.media3.exoplayer.ExoPlayer;
import com.huawei.hms.api.Api;
import com.huawei.hms.api.HuaweiApiClient;
import com.huawei.hms.common.api.ConnectionPostProcessor;
import com.huawei.hms.common.internal.AutoLifecycleFragment;
import com.huawei.hms.core.aidl.CodecLookup;
import com.huawei.hms.core.aidl.DataBuffer;
import com.huawei.hms.core.aidl.IAIDLCallback;
import com.huawei.hms.core.aidl.IAIDLInvoke;
import com.huawei.hms.core.aidl.IMessageEntity;
import com.huawei.hms.core.aidl.MessageCodec;
import com.huawei.hms.core.aidl.RequestHeader;
import com.huawei.hms.core.aidl.ResponseHeader;
import com.huawei.hms.support.api.PendingResultImpl;
import com.huawei.hms.support.api.ResolveResult;
import com.huawei.hms.support.api.client.ApiClient;
import com.huawei.hms.support.api.client.BundleResult;
import com.huawei.hms.support.api.client.InnerApiClient;
import com.huawei.hms.support.api.client.PendingResult;
import com.huawei.hms.support.api.client.ResultCallback;
import com.huawei.hms.support.api.client.Status;
import com.huawei.hms.support.api.client.SubAppInfo;
import com.huawei.hms.support.api.core.ConnectService;
import com.huawei.hms.support.api.entity.auth.PermissionInfo;
import com.huawei.hms.support.api.entity.auth.Scope;
import com.huawei.hms.support.api.entity.core.CheckConnectInfo;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.huawei.hms.support.api.entity.core.ConnectInfo;
import com.huawei.hms.support.api.entity.core.ConnectResp;
import com.huawei.hms.support.api.entity.core.DisconnectInfo;
import com.huawei.hms.support.api.entity.core.DisconnectResp;
import com.huawei.hms.support.api.entity.core.JosGetNoticeResp;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.Checker;
import com.huawei.hms.utils.HMSPackageManager;
import com.huawei.hms.utils.PackageManagerHelper;
import com.huawei.hms.utils.UIUtil;
import com.huawei.hms.utils.Util;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes.dex */
public class HuaweiApiClientImpl extends HuaweiApiClient implements InnerApiClient, ServiceConnection {
    private static final Object A = new Object();
    private static final Object B = new Object();
    public static final String DEFAULT_ACCOUNT = "<<default account>>";
    public static final int SIGN_IN_MODE_OPTIONAL = 2;
    public static final int SIGN_IN_MODE_REQUIRED = 1;

    /* renamed from: b, reason: collision with root package name */
    private final Context f15826b;

    /* renamed from: c, reason: collision with root package name */
    private final String f15827c;

    /* renamed from: d, reason: collision with root package name */
    private String f15828d;

    /* renamed from: e, reason: collision with root package name */
    private String f15829e;

    /* renamed from: f, reason: collision with root package name */
    private volatile IAIDLInvoke f15830f;

    /* renamed from: g, reason: collision with root package name */
    private String f15831g;

    /* renamed from: h, reason: collision with root package name */
    private WeakReference<Activity> f15832h;

    /* renamed from: i, reason: collision with root package name */
    private WeakReference<Activity> f15833i;

    /* renamed from: l, reason: collision with root package name */
    private List<Scope> f15836l;

    /* renamed from: m, reason: collision with root package name */
    private List<PermissionInfo> f15837m;

    /* renamed from: n, reason: collision with root package name */
    private Map<Api<?>, Api.ApiOptions> f15838n;

    /* renamed from: o, reason: collision with root package name */
    private SubAppInfo f15839o;

    /* renamed from: s, reason: collision with root package name */
    private final ReentrantLock f15843s;

    /* renamed from: t, reason: collision with root package name */
    private final Condition f15844t;

    /* renamed from: u, reason: collision with root package name */
    private ConnectionResult f15845u;

    /* renamed from: v, reason: collision with root package name */
    private HuaweiApiClient.ConnectionCallbacks f15846v;

    /* renamed from: w, reason: collision with root package name */
    private HuaweiApiClient.OnConnectionFailedListener f15847w;

    /* renamed from: x, reason: collision with root package name */
    private Handler f15848x;

    /* renamed from: y, reason: collision with root package name */
    private Handler f15849y;

    /* renamed from: z, reason: collision with root package name */
    private CheckUpdatelistener f15850z;

    /* renamed from: a, reason: collision with root package name */
    private int f15825a = -1;

    /* renamed from: j, reason: collision with root package name */
    private boolean f15834j = false;

    /* renamed from: k, reason: collision with root package name */
    private AtomicInteger f15835k = new AtomicInteger(1);

    /* renamed from: p, reason: collision with root package name */
    private long f15840p = 0;

    /* renamed from: q, reason: collision with root package name */
    private int f15841q = 0;

    /* renamed from: r, reason: collision with root package name */
    private final Object f15842r = new Object();

    class a implements Handler.Callback {
        a() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message == null || message.what != 2) {
                return false;
            }
            HMSLog.e("HuaweiApiClientImpl", "In connect, bind core service time out");
            if (HuaweiApiClientImpl.this.f15835k.get() == 5) {
                HuaweiApiClientImpl.this.c(1);
                HuaweiApiClientImpl.this.b();
            }
            return true;
        }
    }

    class b implements Handler.Callback {
        b() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message == null || message.what != 3) {
                return false;
            }
            HMSLog.e("HuaweiApiClientImpl", "In connect, process time out");
            if (HuaweiApiClientImpl.this.f15835k.get() == 2) {
                HuaweiApiClientImpl.this.c(1);
                HuaweiApiClientImpl.this.b();
            }
            return true;
        }
    }

    class c extends IAIDLCallback.Stub {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ ResultCallback f15853a;

        c(ResultCallback resultCallback) {
            this.f15853a = resultCallback;
        }

        @Override // com.huawei.hms.core.aidl.IAIDLCallback
        public void call(DataBuffer dataBuffer) {
            if (dataBuffer == null) {
                HMSLog.i("HuaweiApiClientImpl", "Exit asyncRequest onResult -1");
                this.f15853a.onResult(new BundleResult(-1, null));
                return;
            }
            MessageCodec messageCodecFind = CodecLookup.find(dataBuffer.getProtocol());
            ResponseHeader responseHeader = new ResponseHeader();
            messageCodecFind.decode(dataBuffer.header, responseHeader);
            BundleResult bundleResult = new BundleResult(responseHeader.getStatusCode(), dataBuffer.getBody());
            HMSLog.i("HuaweiApiClientImpl", "Exit asyncRequest onResult");
            this.f15853a.onResult(bundleResult);
        }
    }

    static class d extends PendingResultImpl<Status, IMessageEntity> {
        public d(ApiClient apiClient, String str, IMessageEntity iMessageEntity) {
            super(apiClient, str, iMessageEntity);
        }

        @Override // com.huawei.hms.support.api.PendingResultImpl
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public Status onComplete(IMessageEntity iMessageEntity) {
            return new Status(0);
        }
    }

    private class e implements ResultCallback<ResolveResult<ConnectResp>> {

        class a implements Runnable {

            /* renamed from: a, reason: collision with root package name */
            final /* synthetic */ ResolveResult f15856a;

            a(ResolveResult resolveResult) {
                this.f15856a = resolveResult;
            }

            @Override // java.lang.Runnable
            public void run() {
                HuaweiApiClientImpl.this.a((ResolveResult<ConnectResp>) this.f15856a);
            }
        }

        private e() {
        }

        @Override // com.huawei.hms.support.api.client.ResultCallback
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onResult(ResolveResult<ConnectResp> resolveResult) {
            new Handler(Looper.getMainLooper()).post(new a(resolveResult));
        }

        /* synthetic */ e(HuaweiApiClientImpl huaweiApiClientImpl, a aVar) {
            this();
        }
    }

    private class f implements ResultCallback<ResolveResult<DisconnectResp>> {

        class a implements Runnable {

            /* renamed from: a, reason: collision with root package name */
            final /* synthetic */ ResolveResult f15859a;

            a(ResolveResult resolveResult) {
                this.f15859a = resolveResult;
            }

            @Override // java.lang.Runnable
            public void run() {
                HuaweiApiClientImpl.this.b((ResolveResult<DisconnectResp>) this.f15859a);
            }
        }

        private f() {
        }

        @Override // com.huawei.hms.support.api.client.ResultCallback
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onResult(ResolveResult<DisconnectResp> resolveResult) {
            new Handler(Looper.getMainLooper()).post(new a(resolveResult));
        }

        /* synthetic */ f(HuaweiApiClientImpl huaweiApiClientImpl, a aVar) {
            this();
        }
    }

    private class g implements ResultCallback<ResolveResult<JosGetNoticeResp>> {
        private g() {
        }

        @Override // com.huawei.hms.support.api.client.ResultCallback
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onResult(ResolveResult<JosGetNoticeResp> resolveResult) {
            JosGetNoticeResp value;
            Intent noticeIntent;
            if (resolveResult == null || !resolveResult.getStatus().isSuccess() || (noticeIntent = (value = resolveResult.getValue()).getNoticeIntent()) == null || value.getStatusCode() != 0) {
                return;
            }
            HMSLog.i("HuaweiApiClientImpl", "get notice has intent.");
            Activity validActivity = Util.getValidActivity((Activity) HuaweiApiClientImpl.this.f15832h.get(), HuaweiApiClientImpl.this.getTopActivity());
            if (validActivity == null) {
                HMSLog.e("HuaweiApiClientImpl", "showNotice no valid activity!");
            } else {
                HuaweiApiClientImpl.this.f15834j = true;
                validActivity.startActivity(noticeIntent);
            }
        }

        /* synthetic */ g(HuaweiApiClientImpl huaweiApiClientImpl, a aVar) {
            this();
        }
    }

    public HuaweiApiClientImpl(Context context) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.f15843s = reentrantLock;
        this.f15844t = reentrantLock.newCondition();
        this.f15848x = null;
        this.f15849y = null;
        this.f15850z = null;
        this.f15826b = context;
        String appId = Util.getAppId(context);
        this.f15827c = appId;
        this.f15828d = appId;
        this.f15829e = Util.getCpId(context);
    }

    private DisconnectInfo d() {
        ArrayList arrayList = new ArrayList();
        Map<Api<?>, Api.ApiOptions> map = this.f15838n;
        if (map != null) {
            Iterator<Api<?>> it = map.keySet().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getApiName());
            }
        }
        return new DisconnectInfo(this.f15836l, arrayList);
    }

    private int e() {
        int hmsVersion = Util.getHmsVersion(this.f15826b);
        if (hmsVersion != 0 && hmsVersion >= 20503000) {
            return hmsVersion;
        }
        int iF = f();
        if (g()) {
            if (iF < 20503000) {
                return 20503000;
            }
            return iF;
        }
        if (iF < 20600000) {
            return 20600000;
        }
        return iF;
    }

    private int f() {
        Integer num;
        int iIntValue;
        Map<Api<?>, Api.ApiOptions> apiMap = getApiMap();
        int i2 = 0;
        if (apiMap == null) {
            return 0;
        }
        Iterator<Api<?>> it = apiMap.keySet().iterator();
        while (it.hasNext()) {
            String apiName = it.next().getApiName();
            if (!TextUtils.isEmpty(apiName) && (num = HuaweiApiAvailability.getApiMap().get(apiName)) != null && (iIntValue = num.intValue()) > i2) {
                i2 = iIntValue;
            }
        }
        return i2;
    }

    private boolean g() {
        Map<Api<?>, Api.ApiOptions> map = this.f15838n;
        if (map == null) {
            return false;
        }
        Iterator<Api<?>> it = map.keySet().iterator();
        while (it.hasNext()) {
            if (HuaweiApiAvailability.HMS_API_NAME_GAME.equals(it.next().getApiName())) {
                return true;
            }
        }
        return false;
    }

    private void h() {
        Handler handler = this.f15848x;
        if (handler != null) {
            handler.removeMessages(2);
        } else {
            this.f15848x = new Handler(Looper.getMainLooper(), new a());
        }
        this.f15848x.sendEmptyMessageDelayed(2, 5000L);
    }

    private void i() {
        synchronized (B) {
            try {
                Handler handler = this.f15849y;
                if (handler != null) {
                    handler.removeMessages(3);
                } else {
                    this.f15849y = new Handler(Looper.getMainLooper(), new b());
                }
                HMSLog.d("HuaweiApiClientImpl", "sendEmptyMessageDelayed for onConnectionResult 3 seconds. the result is : " + this.f15849y.sendEmptyMessageDelayed(3, 3000L));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void j() {
        HMSLog.i("HuaweiApiClientImpl", "Enter sendConnectApiServceRequest.");
        ConnectService.connect(this, c()).setResultCallback(new e(this, null));
    }

    private void k() {
        ConnectService.disconnect(this, d()).setResultCallback(new f(this, null));
    }

    private void l() {
        HMSLog.i("HuaweiApiClientImpl", "Enter sendForceConnectApiServceRequest.");
        ConnectService.forceConnect(this, c()).setResultCallback(new e(this, null));
    }

    private void m() {
        if (this.f15834j) {
            HMSLog.i("HuaweiApiClientImpl", "Connect notice has been shown.");
        } else if (HuaweiApiAvailability.getInstance().isHuaweiMobileNoticeAvailable(this.f15826b) == 0) {
            ConnectService.getNotice(this, 0, "6.8.0.300").setResultCallback(new g(this, null));
        }
    }

    private void n() {
        Util.unBindServiceCatchException(this.f15826b, this);
        this.f15830f = null;
    }

    public int asyncRequest(Bundle bundle, String str, int i2, ResultCallback<BundleResult> resultCallback) {
        HMSLog.i("HuaweiApiClientImpl", "Enter asyncRequest.");
        if (resultCallback == null || str == null || bundle == null) {
            HMSLog.e("HuaweiApiClientImpl", "arguments is invalid.");
            return CommonCode.ErrorCode.ARGUMENTS_INVALID;
        }
        if (!innerIsConnected()) {
            HMSLog.e("HuaweiApiClientImpl", "client is unConnect.");
            return CommonCode.ErrorCode.CLIENT_API_INVALID;
        }
        DataBuffer dataBuffer = new DataBuffer(str, i2);
        MessageCodec messageCodecFind = CodecLookup.find(dataBuffer.getProtocol());
        dataBuffer.addBody(bundle);
        RequestHeader requestHeader = new RequestHeader(getAppID(), getPackageName(), 60800300, getSessionId());
        requestHeader.setApiNameList(getApiNameList());
        dataBuffer.header = messageCodecFind.encode(requestHeader, new Bundle());
        try {
            getService().asyncCall(dataBuffer, new c(resultCallback));
            return 0;
        } catch (RemoteException e2) {
            HMSLog.e("HuaweiApiClientImpl", "remote exception:" + e2.getMessage());
            return CommonCode.ErrorCode.INTERNAL_ERROR;
        }
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void checkUpdate(Activity activity, CheckUpdatelistener checkUpdatelistener) throws IllegalAccessException, InstantiationException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        if (checkUpdatelistener == null) {
            HMSLog.e("HuaweiApiClientImpl", "listener is null!");
            return;
        }
        if (activity == null || activity.isFinishing()) {
            HMSLog.e("HuaweiApiClientImpl", "checkUpdate, activity is illegal: " + activity);
            checkUpdatelistener.onResult(-1);
            return;
        }
        this.f15850z = checkUpdatelistener;
        try {
            Class<?> cls = Class.forName("com.huawei.hms.update.manager.CheckUpdateLegacy");
            cls.getMethod("initCheckUpdateCallBack", Object.class, Activity.class).invoke(cls.getDeclaredConstructor(null).newInstance(null), this, activity);
        } catch (ClassCastException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | InvocationTargetException e2) {
            HMSLog.e("HuaweiApiClientImpl", "invoke CheckUpdateLegacy.initCheckUpdateCallBack fail. " + e2.getMessage());
            checkUpdatelistener.onResult(-1);
        }
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void connect(Activity activity) {
        HMSLog.i("HuaweiApiClientImpl", "====== HMSSDK version: 60800300 ======");
        int i2 = this.f15835k.get();
        HMSLog.i("HuaweiApiClientImpl", "Enter connect, Connection Status: " + i2);
        if (i2 == 3 || i2 == 5 || i2 == 2 || i2 == 4) {
            return;
        }
        if (activity != null) {
            this.f15832h = new WeakReference<>(activity);
            this.f15833i = new WeakReference<>(activity);
        }
        this.f15828d = TextUtils.isEmpty(this.f15827c) ? Util.getAppId(this.f15826b) : this.f15827c;
        int iE = e();
        HMSLog.i("HuaweiApiClientImpl", "connect minVersion:" + iE);
        HuaweiApiAvailability.setServicesVersionCode(iE);
        int iIsHuaweiMobileServicesAvailable = HuaweiMobileServicesUtil.isHuaweiMobileServicesAvailable(this.f15826b, iE);
        HMSLog.i("HuaweiApiClientImpl", "In connect, isHuaweiMobileServicesAvailable result: " + iIsHuaweiMobileServicesAvailable);
        this.f15841q = HMSPackageManager.getInstance(this.f15826b).getHmsMultiServiceVersion();
        if (iIsHuaweiMobileServicesAvailable != 0) {
            if (this.f15847w != null) {
                b(iIsHuaweiMobileServicesAvailable);
                return;
            }
            return;
        }
        c(5);
        if (this.f15830f == null) {
            a();
            return;
        }
        c(2);
        j();
        i();
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void connectForeground() {
        HMSLog.i("HuaweiApiClientImpl", "====== HMSSDK version: 60800300 ======");
        int i2 = this.f15835k.get();
        HMSLog.i("HuaweiApiClientImpl", "Enter forceConnect, Connection Status: " + i2);
        if (i2 == 3 || i2 == 5 || i2 == 2 || i2 == 4) {
            return;
        }
        this.f15828d = TextUtils.isEmpty(this.f15827c) ? Util.getAppId(this.f15826b) : this.f15827c;
        l();
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void disableLifeCycleManagement(Activity activity) {
        if (this.f15825a < 0) {
            throw new IllegalStateException("disableLifeCycleManagement failed");
        }
        AutoLifecycleFragment.getInstance(activity).stopAutoManage(this.f15825a);
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public PendingResult<Status> discardAndReconnect() {
        return new d(this, null, null);
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void disconnect() {
        int i2 = this.f15835k.get();
        HMSLog.i("HuaweiApiClientImpl", "Enter disconnect, Connection Status: " + i2);
        if (i2 == 2) {
            c(4);
            return;
        }
        if (i2 == 3) {
            c(4);
            k();
        } else {
            if (i2 != 5) {
                return;
            }
            a(2);
            c(4);
        }
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public Map<Api<?>, Api.ApiOptions> getApiMap() {
        return this.f15838n;
    }

    @Override // com.huawei.hms.support.api.client.AidlApiClient
    public List<String> getApiNameList() {
        ArrayList arrayList = new ArrayList();
        Map<Api<?>, Api.ApiOptions> map = this.f15838n;
        if (map != null) {
            Iterator<Api<?>> it = map.keySet().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getApiName());
            }
        }
        return arrayList;
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public String getAppID() {
        return this.f15828d;
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public ConnectionResult getConnectionResult(Api<?> api) {
        if (isConnected()) {
            this.f15845u = null;
            return new ConnectionResult(0, (PendingIntent) null);
        }
        ConnectionResult connectionResult = this.f15845u;
        return connectionResult != null ? connectionResult : new ConnectionResult(13, (PendingIntent) null);
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public Context getContext() {
        return this.f15826b;
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public String getCpID() {
        return this.f15829e;
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public String getPackageName() {
        return this.f15826b.getPackageName();
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public List<PermissionInfo> getPermissionInfos() {
        return this.f15837m;
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public List<Scope> getScopes() {
        return this.f15836l;
    }

    @Override // com.huawei.hms.support.api.client.AidlApiClient
    public IAIDLInvoke getService() {
        return this.f15830f;
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public String getSessionId() {
        return this.f15831g;
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public final SubAppInfo getSubAppInfo() {
        return this.f15839o;
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public Activity getTopActivity() {
        WeakReference<Activity> weakReference = this.f15833i;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public String getTransportName() {
        return IPCTransport.class.getName();
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public boolean hasConnectedApi(Api<?> api) {
        return isConnected();
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public boolean hasConnectionFailureListener(HuaweiApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        Checker.checkNonNull(onConnectionFailedListener, "onConnectionFailedListener should not be null");
        synchronized (this.f15842r) {
            try {
                return this.f15847w == onConnectionFailedListener;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public boolean hasConnectionSuccessListener(HuaweiApiClient.ConnectionCallbacks connectionCallbacks) {
        Checker.checkNonNull(connectionCallbacks, "connectionCallbacksListener should not be null");
        synchronized (this.f15842r) {
            try {
                return this.f15846v == connectionCallbacks;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public ConnectionResult holdUpConnect() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("blockingConnect must not be called on the UI thread");
        }
        this.f15843s.lock();
        try {
            connect((Activity) null);
            while (isConnecting()) {
                this.f15844t.await();
            }
            if (isConnected()) {
                this.f15845u = null;
                return new ConnectionResult(0, (PendingIntent) null);
            }
            ConnectionResult connectionResult = this.f15845u;
            return connectionResult != null ? connectionResult : new ConnectionResult(13, (PendingIntent) null);
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            return new ConnectionResult(15, (PendingIntent) null);
        } finally {
            this.f15843s.unlock();
        }
    }

    @Override // com.huawei.hms.support.api.client.InnerApiClient
    public boolean innerIsConnected() {
        return this.f15835k.get() == 3 || this.f15835k.get() == 4;
    }

    @Override // com.huawei.hms.api.HuaweiApiClient, com.huawei.hms.support.api.client.ApiClient
    public boolean isConnected() {
        if (this.f15841q == 0) {
            this.f15841q = HMSPackageManager.getInstance(this.f15826b).getHmsMultiServiceVersion();
        }
        if (this.f15841q >= 20504000) {
            return innerIsConnected();
        }
        long jCurrentTimeMillis = System.currentTimeMillis() - this.f15840p;
        if (jCurrentTimeMillis > 0 && jCurrentTimeMillis < 300000) {
            return innerIsConnected();
        }
        if (!innerIsConnected()) {
            return false;
        }
        Status status = ((ResolveResult) ConnectService.checkconnect(this, new CheckConnectInfo()).awaitOnAnyThread(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS, TimeUnit.MILLISECONDS)).getStatus();
        if (status.isSuccess()) {
            this.f15840p = System.currentTimeMillis();
            return true;
        }
        int statusCode = status.getStatusCode();
        HMSLog.i("HuaweiApiClientImpl", "isConnected is false, statuscode:" + statusCode);
        if (statusCode == 907135004) {
            return false;
        }
        n();
        c(1);
        this.f15840p = System.currentTimeMillis();
        return false;
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public boolean isConnecting() {
        int i2 = this.f15835k.get();
        return i2 == 2 || i2 == 5;
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void onPause(Activity activity) {
        HMSLog.i("HuaweiApiClientImpl", "onPause");
    }

    public void onResult(int i2) {
        this.f15850z.onResult(i2);
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void onResume(Activity activity) {
        if (activity != null) {
            HMSLog.i("HuaweiApiClientImpl", "onResume");
            this.f15833i = new WeakReference<>(activity);
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        HMSLog.i("HuaweiApiClientImpl", "HuaweiApiClientImpl Enter onServiceConnected.");
        a(2);
        this.f15830f = IAIDLInvoke.Stub.asInterface(iBinder);
        if (this.f15830f != null) {
            if (this.f15835k.get() == 5) {
                c(2);
                j();
                i();
                return;
            } else {
                if (this.f15835k.get() != 3) {
                    n();
                    return;
                }
                return;
            }
        }
        HMSLog.e("HuaweiApiClientImpl", "In onServiceConnected, mCoreService must not be null.");
        n();
        c(1);
        if (this.f15847w != null) {
            WeakReference<Activity> weakReference = this.f15832h;
            ConnectionResult connectionResult = new ConnectionResult(10, (weakReference == null || weakReference.get() == null) ? null : HuaweiApiAvailability.getInstance().getResolveErrorPendingIntent(this.f15832h.get(), 10));
            this.f15847w.onConnectionFailed(connectionResult);
            this.f15845u = connectionResult;
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        HMSLog.i("HuaweiApiClientImpl", "Enter onServiceDisconnected.");
        this.f15830f = null;
        c(1);
        HuaweiApiClient.ConnectionCallbacks connectionCallbacks = this.f15846v;
        if (connectionCallbacks != null) {
            connectionCallbacks.onConnectionSuspended(1);
        }
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void print(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void reconnect() {
        disconnect();
        connect((Activity) null);
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void removeConnectionFailureListener(HuaweiApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        Checker.checkNonNull(onConnectionFailedListener, "onConnectionFailedListener should not be null");
        synchronized (this.f15842r) {
            try {
                if (this.f15847w != onConnectionFailedListener) {
                    HMSLog.w("HuaweiApiClientImpl", "unregisterConnectionFailedListener: this onConnectionFailedListener has not been registered");
                } else {
                    this.f15847w = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void removeConnectionSuccessListener(HuaweiApiClient.ConnectionCallbacks connectionCallbacks) {
        Checker.checkNonNull(connectionCallbacks, "connectionCallbacksListener should not be null");
        synchronized (this.f15842r) {
            try {
                if (this.f15846v != connectionCallbacks) {
                    HMSLog.w("HuaweiApiClientImpl", "unregisterConnectionCallback: this connectionCallbacksListener has not been registered");
                } else {
                    this.f15846v = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void resetListener() {
        this.f15850z = null;
    }

    public void setApiMap(Map<Api<?>, Api.ApiOptions> map) {
        this.f15838n = map;
    }

    protected void setAutoLifecycleClientId(int i2) {
        this.f15825a = i2;
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void setConnectionCallbacks(HuaweiApiClient.ConnectionCallbacks connectionCallbacks) {
        this.f15846v = connectionCallbacks;
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void setConnectionFailedListener(HuaweiApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.f15847w = onConnectionFailedListener;
    }

    public void setHasShowNotice(boolean z2) {
        this.f15834j = z2;
    }

    public void setPermissionInfos(List<PermissionInfo> list) {
        this.f15837m = list;
    }

    public void setScopes(List<Scope> list) {
        this.f15836l = list;
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public boolean setSubAppInfo(SubAppInfo subAppInfo) {
        HMSLog.i("HuaweiApiClientImpl", "Enter setSubAppInfo");
        if (subAppInfo == null) {
            HMSLog.e("HuaweiApiClientImpl", "subAppInfo is null");
            return false;
        }
        String subAppID = subAppInfo.getSubAppID();
        if (TextUtils.isEmpty(subAppID)) {
            HMSLog.e("HuaweiApiClientImpl", "subAppId is empty");
            return false;
        }
        if (subAppID.equals(TextUtils.isEmpty(this.f15827c) ? Util.getAppId(this.f15826b) : this.f15827c)) {
            HMSLog.e("HuaweiApiClientImpl", "subAppId is host appid");
            return false;
        }
        this.f15839o = new SubAppInfo(subAppInfo);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(int i2) {
        this.f15835k.set(i2);
        if (i2 == 1 || i2 == 3 || i2 == 2) {
            this.f15843s.lock();
            try {
                this.f15844t.signalAll();
            } finally {
                this.f15843s.unlock();
            }
        }
    }

    private void b(int i2) {
        PendingIntent resolveErrorPendingIntent;
        WeakReference<Activity> weakReference = this.f15832h;
        if (weakReference == null || weakReference.get() == null) {
            resolveErrorPendingIntent = null;
        } else {
            resolveErrorPendingIntent = HuaweiApiAvailability.getInstance().getResolveErrorPendingIntent(this.f15832h.get(), i2);
            HMSLog.i("HuaweiApiClientImpl", "connect 2.0 fail: " + i2);
        }
        ConnectionResult connectionResult = new ConnectionResult(i2, resolveErrorPendingIntent);
        this.f15847w.onConnectionFailed(connectionResult);
        this.f15845u = connectionResult;
    }

    private void a() {
        Intent intent = new Intent(HMSPackageManager.getInstance(this.f15826b).getServiceAction());
        HMSPackageManager.getInstance(this.f15826b).refreshForMultiService();
        try {
            intent.setPackage(HMSPackageManager.getInstance(this.f15826b).getHMSPackageNameForMultiService());
            synchronized (A) {
                try {
                    if (this.f15826b.bindService(intent, this, 1)) {
                        h();
                        return;
                    }
                    c(1);
                    HMSLog.e("HuaweiApiClientImpl", "In connect, bind core service fail");
                    b();
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (IllegalArgumentException unused) {
            HMSLog.e("HuaweiApiClientImpl", "IllegalArgumentException when bindCoreService intent.setPackage");
            c(1);
            HMSLog.e("HuaweiApiClientImpl", "In connect, bind core service fail");
            b();
        }
    }

    private ConnectInfo c() throws PackageManager.NameNotFoundException {
        String packageSignature = new PackageManagerHelper(this.f15826b).getPackageSignature(this.f15826b.getPackageName());
        if (packageSignature == null) {
            packageSignature = "";
        }
        SubAppInfo subAppInfo = this.f15839o;
        return new ConnectInfo(getApiNameList(), this.f15836l, packageSignature, subAppInfo == null ? null : subAppInfo.getSubAppID());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        n();
        if (this.f15847w != null) {
            int i2 = UIUtil.isBackground(this.f15826b) ? 7 : 6;
            WeakReference<Activity> weakReference = this.f15832h;
            ConnectionResult connectionResult = new ConnectionResult(i2, (weakReference == null || weakReference.get() == null) ? null : HuaweiApiAvailability.getInstance().getResolveErrorPendingIntent(this.f15832h.get(), i2));
            this.f15847w.onConnectionFailed(connectionResult);
            this.f15845u = connectionResult;
        }
    }

    private void c(ResolveResult<ConnectResp> resolveResult) {
        if (resolveResult.getValue() != null) {
            ProtocolNegotiate.getInstance().negotiate(resolveResult.getValue().protocolVersion);
        }
        c(3);
        this.f15845u = null;
        HuaweiApiClient.ConnectionCallbacks connectionCallbacks = this.f15846v;
        if (connectionCallbacks != null) {
            connectionCallbacks.onConnected();
        }
        if (this.f15832h != null) {
            m();
        }
        for (Map.Entry<Api<?>, Api.ApiOptions> entry : getApiMap().entrySet()) {
            if (entry.getKey().getmConnetctPostList() != null && !entry.getKey().getmConnetctPostList().isEmpty()) {
                HMSLog.i("HuaweiApiClientImpl", "Enter onConnectionResult, get the ConnetctPostList ");
                for (ConnectionPostProcessor connectionPostProcessor : entry.getKey().getmConnetctPostList()) {
                    HMSLog.i("HuaweiApiClientImpl", "Enter onConnectionResult, processor.run");
                    connectionPostProcessor.run(this, this.f15832h);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(ResolveResult<DisconnectResp> resolveResult) {
        HMSLog.i("HuaweiApiClientImpl", "Enter onDisconnectionResult, disconnect from server result: " + resolveResult.getStatus().getStatusCode());
        n();
        c(1);
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public ConnectionResult holdUpConnect(long j2, TimeUnit timeUnit) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            this.f15843s.lock();
            try {
                connect((Activity) null);
                long nanos = timeUnit.toNanos(j2);
                while (isConnecting()) {
                    if (nanos <= 0) {
                        disconnect();
                        return new ConnectionResult(14, (PendingIntent) null);
                    }
                    nanos = this.f15844t.awaitNanos(nanos);
                }
                if (isConnected()) {
                    this.f15845u = null;
                    return new ConnectionResult(0, (PendingIntent) null);
                }
                ConnectionResult connectionResult = this.f15845u;
                return connectionResult != null ? connectionResult : new ConnectionResult(13, (PendingIntent) null);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, (PendingIntent) null);
            } finally {
                this.f15843s.unlock();
            }
        }
        throw new IllegalStateException("blockingConnect must not be called on the UI thread");
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void connect(int i2) {
        connect((Activity) null);
    }

    private void a(int i2) {
        if (i2 == 2) {
            synchronized (A) {
                try {
                    Handler handler = this.f15848x;
                    if (handler != null) {
                        handler.removeMessages(i2);
                        this.f15848x = null;
                    }
                } finally {
                }
            }
        }
        if (i2 == 3) {
            synchronized (B) {
                try {
                    Handler handler2 = this.f15849y;
                    if (handler2 != null) {
                        handler2.removeMessages(i2);
                        this.f15849y = null;
                    }
                } finally {
                }
            }
        }
        synchronized (A) {
            try {
                Handler handler3 = this.f15848x;
                if (handler3 != null) {
                    handler3.removeMessages(2);
                    this.f15848x = null;
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ResolveResult<ConnectResp> resolveResult) {
        HMSLog.i("HuaweiApiClientImpl", "Enter onConnectionResult");
        if (this.f15830f != null && this.f15835k.get() == 2) {
            a(3);
            ConnectResp value = resolveResult.getValue();
            if (value != null) {
                this.f15831g = value.sessionId;
            }
            SubAppInfo subAppInfo = this.f15839o;
            PendingIntent resolveErrorPendingIntent = null;
            String subAppID = subAppInfo == null ? null : subAppInfo.getSubAppID();
            if (!TextUtils.isEmpty(subAppID)) {
                this.f15828d = subAppID;
            }
            int statusCode = resolveResult.getStatus().getStatusCode();
            HMSLog.i("HuaweiApiClientImpl", "Enter onConnectionResult, connect to server result: " + statusCode);
            if (Status.SUCCESS.equals(resolveResult.getStatus())) {
                c(resolveResult);
                return;
            }
            if (resolveResult.getStatus() != null && resolveResult.getStatus().getStatusCode() == 1001) {
                n();
                c(1);
                HuaweiApiClient.ConnectionCallbacks connectionCallbacks = this.f15846v;
                if (connectionCallbacks != null) {
                    connectionCallbacks.onConnectionSuspended(3);
                    return;
                }
                return;
            }
            n();
            c(1);
            if (this.f15847w != null) {
                WeakReference<Activity> weakReference = this.f15832h;
                if (weakReference != null && weakReference.get() != null) {
                    resolveErrorPendingIntent = HuaweiApiAvailability.getInstance().getResolveErrorPendingIntent(this.f15832h.get(), statusCode);
                }
                ConnectionResult connectionResult = new ConnectionResult(statusCode, resolveErrorPendingIntent);
                this.f15847w.onConnectionFailed(connectionResult);
                this.f15845u = connectionResult;
                return;
            }
            return;
        }
        HMSLog.e("HuaweiApiClientImpl", "Invalid onConnectionResult");
    }
}
