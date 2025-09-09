package com.huawei.hms.common;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.TaskCompletionSource;
import com.huawei.hms.adapter.BinderAdapter;
import com.huawei.hms.api.Api;
import com.huawei.hms.api.Api.ApiOptions;
import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.common.internal.AbstractClientBuilder;
import com.huawei.hms.common.internal.AnyClient;
import com.huawei.hms.common.internal.BaseHmsClient;
import com.huawei.hms.common.internal.BindResolveClients;
import com.huawei.hms.common.internal.ClientSettings;
import com.huawei.hms.common.internal.HmsClient;
import com.huawei.hms.common.internal.RequestHeader;
import com.huawei.hms.common.internal.RequestManager;
import com.huawei.hms.common.internal.ResolveClientBean;
import com.huawei.hms.common.internal.ResponseHeader;
import com.huawei.hms.common.internal.TaskApiCall;
import com.huawei.hms.common.internal.TaskApiCallWrapper;
import com.huawei.hms.common.internal.TransactionIdCreater;
import com.huawei.hms.core.aidl.IAIDLInvoke;
import com.huawei.hms.core.aidl.IMessageEntity;
import com.huawei.hms.support.api.client.Status;
import com.huawei.hms.support.api.client.SubAppInfo;
import com.huawei.hms.support.api.entity.auth.Scope;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.huawei.hms.support.hianalytics.HiAnalyticsInnerClient;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.Checker;
import com.huawei.hms.utils.HMSBIInitializer;
import com.huawei.hms.utils.HMSPackageManager;
import com.huawei.hms.utils.Util;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public class HuaweiApi<TOption extends Api.ApiOptions> {

    /* renamed from: a, reason: collision with root package name */
    private Context f15892a;

    /* renamed from: b, reason: collision with root package name */
    private TOption f15893b;

    /* renamed from: c, reason: collision with root package name */
    private Context f15894c;

    /* renamed from: d, reason: collision with root package name */
    private AbstractClientBuilder<?, TOption> f15895d;

    /* renamed from: e, reason: collision with root package name */
    private String f15896e;

    /* renamed from: f, reason: collision with root package name */
    private String f15897f;

    /* renamed from: g, reason: collision with root package name */
    private SubAppInfo f15898g;

    /* renamed from: h, reason: collision with root package name */
    private WeakReference<Activity> f15899h;

    /* renamed from: i, reason: collision with root package name */
    private int f15900i;

    /* renamed from: j, reason: collision with root package name */
    private int f15901j = 1;

    /* renamed from: k, reason: collision with root package name */
    private boolean f15902k = false;

    /* renamed from: l, reason: collision with root package name */
    private String f15903l;

    /* renamed from: m, reason: collision with root package name */
    private boolean f15904m;

    /* renamed from: n, reason: collision with root package name */
    private RequestManager f15905n;

    public static class RequestHandler<OptionsT extends Api.ApiOptions> implements BaseHmsClient.ConnectionCallbacks, BaseHmsClient.OnConnectionFailedListener {

        /* renamed from: b, reason: collision with root package name */
        private final AnyClient f15910b;

        /* renamed from: d, reason: collision with root package name */
        private final HuaweiApi<OptionsT> f15912d;

        /* renamed from: e, reason: collision with root package name */
        private ResolveClientBean f15913e;
        public final Queue<TaskApiCallbackWrapper> callbackWaitQueue = new LinkedList();

        /* renamed from: a, reason: collision with root package name */
        private final Queue<TaskApiCallbackWrapper> f15909a = new LinkedList();

        /* renamed from: c, reason: collision with root package name */
        private ConnectionResult f15911c = null;

        RequestHandler(HuaweiApi<OptionsT> huaweiApi) {
            this.f15912d = huaweiApi;
            this.f15910b = huaweiApi.getClient(RequestManager.getHandler().getLooper(), this);
        }

        public AnyClient getClient() {
            return this.f15910b;
        }

        @Override // com.huawei.hms.common.internal.BaseHmsClient.ConnectionCallbacks
        public void onConnected() {
            HMSLog.i("HuaweiApi", "onConnected");
            BindResolveClients.getInstance().unRegister(this.f15913e);
            this.f15913e = null;
            RequestManager.getHandler().post(new Runnable() { // from class: com.huawei.hms.common.HuaweiApi.RequestHandler.4
                @Override // java.lang.Runnable
                public void run() {
                    RequestHandler.this.b();
                }
            });
        }

        @Override // com.huawei.hms.common.internal.BaseHmsClient.OnConnectionFailedListener
        public void onConnectionFailed(final ConnectionResult connectionResult) {
            HMSLog.i("HuaweiApi", "onConnectionFailed");
            BindResolveClients.getInstance().unRegister(this.f15913e);
            this.f15913e = null;
            RequestManager.getHandler().post(new Runnable() { // from class: com.huawei.hms.common.HuaweiApi.RequestHandler.3
                @Override // java.lang.Runnable
                public void run() {
                    RequestHandler.this.b(connectionResult);
                }
            });
        }

        @Override // com.huawei.hms.common.internal.BaseHmsClient.ConnectionCallbacks
        public void onConnectionSuspended(int i2) {
            HMSLog.i("HuaweiApi", "onConnectionSuspended");
            BindResolveClients.getInstance().unRegister(this.f15913e);
            this.f15913e = null;
            RequestManager.getHandler().post(new Runnable() { // from class: com.huawei.hms.common.HuaweiApi.RequestHandler.5
                @Override // java.lang.Runnable
                public void run() {
                    RequestHandler.this.c();
                }
            });
        }

        public void postMessage(final TaskApiCallbackWrapper taskApiCallbackWrapper) {
            RequestManager.addToConnectedReqMap(taskApiCallbackWrapper.a().getTaskApiCall().getTransactionId(), this);
            this.f15909a.add(taskApiCallbackWrapper);
            String uri = taskApiCallbackWrapper.a().getTaskApiCall().getUri();
            String packageName = (((HuaweiApi) this.f15912d).f15894c == null ? this.f15912d.getContext() : ((HuaweiApi) this.f15912d).f15894c).getPackageName();
            if (((HuaweiApi) this.f15912d).f15894c != null) {
                HuaweiApi<OptionsT> huaweiApi = this.f15912d;
                huaweiApi.b(((HuaweiApi) huaweiApi).f15894c);
            }
            final RequestHeader requestHeader = new RequestHeader();
            requestHeader.setSrvName(uri.split("\\.")[0]);
            requestHeader.setApiName(uri);
            requestHeader.setAppID(this.f15912d.getAppID() + "|" + this.f15912d.getSubAppID());
            requestHeader.setPkgName(packageName);
            requestHeader.setSessionId(this.f15910b.getSessionId());
            TaskApiCall taskApiCall = taskApiCallbackWrapper.a().getTaskApiCall();
            requestHeader.setTransactionId(a(taskApiCall.getTransactionId(), uri));
            requestHeader.setParcelable(taskApiCall.getParcelable());
            requestHeader.setKitSdkVersion(this.f15912d.getKitSdkVersion());
            requestHeader.setApiLevel(Math.max(this.f15912d.getApiLevel(), taskApiCall.getApiLevel()));
            this.f15910b.post(requestHeader, taskApiCall.getRequestJson(), new AnyClient.CallBack() { // from class: com.huawei.hms.common.HuaweiApi.RequestHandler.2
                @Override // com.huawei.hms.common.internal.AnyClient.CallBack
                public void onCallback(IMessageEntity iMessageEntity, String str) {
                    AnyClient.CallBack callBackB = taskApiCallbackWrapper.b();
                    if (callBackB != null) {
                        callBackB.onCallback(iMessageEntity, str);
                    }
                    RequestManager.removeReqByTransId(requestHeader.getTransactionId());
                    RequestManager.getHandler().post(new Runnable() { // from class: com.huawei.hms.common.HuaweiApi.RequestHandler.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            RequestHandler.this.f15909a.remove(taskApiCallbackWrapper);
                        }
                    });
                }
            });
        }

        private TaskApiCallbackWrapper b(final TaskApiCallWrapper taskApiCallWrapper) {
            return new TaskApiCallbackWrapper(taskApiCallWrapper, new AnyClient.CallBack() { // from class: com.huawei.hms.common.HuaweiApi.RequestHandler.1

                /* renamed from: a, reason: collision with root package name */
                private AtomicBoolean f15914a = new AtomicBoolean(true);

                @Override // com.huawei.hms.common.internal.AnyClient.CallBack
                public void onCallback(IMessageEntity iMessageEntity, String str) {
                    if (!(iMessageEntity instanceof ResponseHeader)) {
                        HMSLog.e("HuaweiApi", "header is not instance of ResponseHeader");
                        return;
                    }
                    ResponseHeader responseHeader = (ResponseHeader) iMessageEntity;
                    if (responseHeader.getErrorCode() == 11) {
                        RequestHandler.this.a();
                        HMSLog.i("HuaweiApi", "unbind service");
                    }
                    if (!TextUtils.isEmpty(responseHeader.getResolution())) {
                        HMSLog.e("HuaweiApi", "Response has resolution: " + responseHeader.getResolution());
                    }
                    if (this.f15914a.compareAndSet(true, false)) {
                        HiAnalyticsInnerClient.reportEntryExit(RequestHandler.this.f15912d.getContext(), responseHeader, String.valueOf(RequestHandler.this.f15912d.getKitSdkVersion()));
                    }
                    taskApiCallWrapper.getTaskApiCall().onResponse(RequestHandler.this.f15910b, responseHeader, str, taskApiCallWrapper.getTaskCompletionSource());
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c() {
            HMSLog.i("HuaweiApi", "wait queue size = " + this.callbackWaitQueue.size());
            HMSLog.i("HuaweiApi", "run queue size = " + this.f15909a.size());
            Iterator<TaskApiCallbackWrapper> it = this.callbackWaitQueue.iterator();
            while (it.hasNext()) {
                a(it.next());
            }
            Iterator<TaskApiCallbackWrapper> it2 = this.f15909a.iterator();
            while (it2.hasNext()) {
                a(it2.next());
            }
            this.callbackWaitQueue.clear();
            this.f15909a.clear();
            this.f15911c = null;
            this.f15910b.disconnect();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(ConnectionResult connectionResult) {
            this.f15911c = connectionResult;
            Iterator<TaskApiCallbackWrapper> it = this.callbackWaitQueue.iterator();
            boolean z2 = true;
            while (it.hasNext()) {
                TaskApiCallWrapper taskApiCallWrapperA = it.next().a();
                ResponseHeader responseHeader = new ResponseHeader(1, CommonCode.ErrorCode.CLIENT_API_INVALID, "Connection Failed:" + a(connectionResult) + "(" + connectionResult.getErrorCode() + ")");
                responseHeader.setTransactionId(taskApiCallWrapperA.getTaskApiCall().getTransactionId());
                HiAnalyticsInnerClient.reportEntryExit(this.f15912d.getContext(), responseHeader, String.valueOf(this.f15912d.getKitSdkVersion()));
                if (this.f15911c.getResolution() != null && z2) {
                    responseHeader.setParcelable(this.f15911c.getResolution());
                    if (Util.isAvailableLibExist(this.f15912d.getContext()) && this.f15911c.getErrorCode() == 26) {
                        responseHeader.setResolution(CommonCode.Resolution.HAS_RESOLUTION);
                    }
                    z2 = false;
                }
                int errorCode = this.f15911c.getErrorCode();
                if (errorCode == 30 || errorCode == 31) {
                    responseHeader.setErrorCode(errorCode);
                }
                taskApiCallWrapperA.getTaskApiCall().onResponse(this.f15910b, responseHeader, null, taskApiCallWrapperA.getTaskCompletionSource());
            }
            this.callbackWaitQueue.clear();
            this.f15909a.clear();
            this.f15911c = null;
            this.f15910b.disconnect();
        }

        void a(TaskApiCallWrapper taskApiCallWrapper) {
            HMSLog.i("HuaweiApi", "sendRequest");
            TaskApiCallbackWrapper taskApiCallbackWrapperB = b(taskApiCallWrapper);
            int hmsVersionCode = HMSPackageManager.getInstance(((HuaweiApi) this.f15912d).f15892a).getHmsVersionCode();
            if (hmsVersionCode < 40000000 && hmsVersionCode > 0 && this.f15910b.isConnected() && !((HuaweiApi) this.f15912d).f15904m && ((BaseHmsClient) this.f15910b).getAdapter().getServiceAction().equals("com.huawei.hms.core.aidlservice")) {
                int requestHmsVersionCode = this.f15910b.getRequestHmsVersionCode();
                if (requestHmsVersionCode <= taskApiCallWrapper.getTaskApiCall().getMinApkVersion()) {
                    requestHmsVersionCode = taskApiCallWrapper.getTaskApiCall().getMinApkVersion();
                }
                if (requestHmsVersionCode > hmsVersionCode) {
                    this.f15910b.disconnect();
                }
            }
            if (this.f15910b.isConnected()) {
                HMSLog.i("HuaweiApi", "isConnected:true.");
                BinderAdapter adapter = ((BaseHmsClient) this.f15910b).getAdapter();
                adapter.updateDelayTask();
                ((HmsClient) this.f15910b).setService(IAIDLInvoke.Stub.asInterface(adapter.getServiceBinder()));
                postMessage(taskApiCallbackWrapperB);
                return;
            }
            HMSLog.i("HuaweiApi", "isConnected:false.");
            this.callbackWaitQueue.add(taskApiCallbackWrapperB);
            ConnectionResult connectionResult = this.f15911c;
            if (connectionResult != null && connectionResult.getErrorCode() != 0) {
                HMSLog.i("HuaweiApi", "onConnectionFailed, ErrorCode:" + this.f15911c.getErrorCode());
                onConnectionFailed(this.f15911c);
                return;
            }
            RequestManager.addRequestToQueue(this);
            Object obj = this.f15910b;
            if (obj instanceof BaseHmsClient) {
                ((BaseHmsClient) obj).setInternalRequest(this);
            }
            a(taskApiCallWrapper.getTaskApiCall().getMinApkVersion(), taskApiCallbackWrapperB);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b() {
            this.f15911c = null;
            this.f15909a.clear();
            Iterator<TaskApiCallbackWrapper> it = this.callbackWaitQueue.iterator();
            while (it.hasNext()) {
                postMessage(it.next());
            }
            this.callbackWaitQueue.clear();
        }

        private String a(String str, String str2) {
            return TextUtils.isEmpty(str) ? TransactionIdCreater.getId(this.f15912d.getAppID(), str2) : str;
        }

        synchronized void a(int i2, TaskApiCallbackWrapper taskApiCallbackWrapper) {
            if (this.f15910b.isConnected()) {
                HMSLog.d("HuaweiApi", "client is connected");
                return;
            }
            if (this.f15910b.isConnecting()) {
                HMSLog.d("HuaweiApi", "client is isConnecting");
                return;
            }
            if (this.f15912d.getActivity() != null) {
                if (this.f15913e == null) {
                    this.f15913e = new ResolveClientBean(this.f15910b, i2);
                }
                if (BindResolveClients.getInstance().isClientRegistered(this.f15913e)) {
                    HMSLog.i("HuaweiApi", "mResolveClientBean has already register, return!");
                    return;
                }
                BindResolveClients.getInstance().register(this.f15913e);
            }
            this.f15910b.connect(i2);
        }

        void a() {
            this.f15910b.disconnect();
        }

        private void a(TaskApiCallbackWrapper taskApiCallbackWrapper) {
            TaskApiCallWrapper taskApiCallWrapperA = taskApiCallbackWrapper.a();
            ResponseHeader responseHeader = new ResponseHeader(1, CommonCode.ErrorCode.CLIENT_API_INVALID, "Connection Suspended");
            responseHeader.setTransactionId(taskApiCallWrapperA.getTaskApiCall().getTransactionId());
            taskApiCallWrapperA.getTaskApiCall().onResponse(this.f15910b, responseHeader, null, taskApiCallWrapperA.getTaskCompletionSource());
        }

        /* JADX WARN: Code restructure failed: missing block: B:27:0x0051, code lost:
        
            if (r9 != 10) goto L15;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private java.lang.String a(com.huawei.hms.api.ConnectionResult r9) {
            /*
                r8 = this;
                com.huawei.hms.common.HuaweiApi<OptionsT extends com.huawei.hms.api.Api$ApiOptions> r0 = r8.f15912d
                android.content.Context r0 = r0.getContext()
                boolean r0 = com.huawei.hms.utils.Util.isAvailableLibExist(r0)
                java.lang.String r1 = "get update result, but has other error codes"
                java.lang.String r2 = "internal error"
                java.lang.String r3 = "application configuration error, please developer check configuration"
                java.lang.String r4 = "unknown errorReason"
                r5 = 10
                r6 = 8
                r7 = -1
                if (r0 == 0) goto L49
                int r9 = r9.getErrorCode()
                if (r9 == r7) goto L54
                r0 = 3
                if (r9 == r0) goto L46
                if (r9 == r6) goto L44
                if (r9 == r5) goto L42
                r0 = 13
                if (r9 == r0) goto L3f
                r0 = 21
                if (r9 == r0) goto L3c
                switch(r9) {
                    case 25: goto L39;
                    case 26: goto L36;
                    case 27: goto L33;
                    default: goto L31;
                }
            L31:
                r1 = r4
                goto L54
            L33:
                java.lang.String r1 = "there is already an update popup at the front desk, but it hasn't been clicked or it is not effective for a while"
                goto L54
            L36:
                java.lang.String r1 = "update failed, because no activity incoming, can't pop update page"
                goto L54
            L39:
                java.lang.String r1 = "failed to get update result"
                goto L54
            L3c:
                java.lang.String r1 = "device is too old to be support"
                goto L54
            L3f:
                java.lang.String r1 = "update cancelled"
                goto L54
            L42:
                r1 = r3
                goto L54
            L44:
                r1 = r2
                goto L54
            L46:
                java.lang.String r1 = "HuaWei Mobile Service is disabled"
                goto L54
            L49:
                int r9 = r9.getErrorCode()
                if (r9 == r7) goto L54
                if (r9 == r6) goto L44
                if (r9 == r5) goto L42
                goto L31
            L54:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.common.HuaweiApi.RequestHandler.a(com.huawei.hms.api.ConnectionResult):java.lang.String");
        }
    }

    public static class TaskApiCallbackWrapper {

        /* renamed from: a, reason: collision with root package name */
        private final TaskApiCallWrapper f15925a;

        /* renamed from: b, reason: collision with root package name */
        private final AnyClient.CallBack f15926b;

        TaskApiCallbackWrapper(TaskApiCallWrapper taskApiCallWrapper, AnyClient.CallBack callBack) {
            this.f15925a = taskApiCallWrapper;
            this.f15926b = callBack;
        }

        TaskApiCallWrapper a() {
            return this.f15925a;
        }

        AnyClient.CallBack b() {
            return this.f15926b;
        }
    }

    private static class a<OptionsT extends Api.ApiOptions> implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        private final HuaweiApi<OptionsT> f15927a;

        /* renamed from: b, reason: collision with root package name */
        private final TaskApiCallWrapper f15928b;

        public a(HuaweiApi<OptionsT> huaweiApi, TaskApiCallWrapper taskApiCallWrapper) {
            this.f15927a = huaweiApi;
            this.f15928b = taskApiCallWrapper;
        }

        /* JADX WARN: Removed duplicated region for block: B:22:0x0056 A[ADDED_TO_REGION] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void a(com.huawei.hms.common.HuaweiApi.RequestHandler r8, java.lang.Throwable r9) {
            /*
                r7 = this;
                java.lang.String r0 = "HuaweiApi"
                r1 = 0
                com.huawei.hms.common.internal.AnyClient r8 = r8.getClient()     // Catch: java.lang.Throwable -> L36
                com.huawei.hms.common.internal.ResponseHeader r2 = new com.huawei.hms.common.internal.ResponseHeader     // Catch: java.lang.Throwable -> L33
                java.lang.String r9 = r9.getMessage()     // Catch: java.lang.Throwable -> L33
                r3 = 1
                r4 = 907135001(0x3611c819, float:2.1723156E-6)
                r2.<init>(r3, r4, r9)     // Catch: java.lang.Throwable -> L33
                org.json.JSONObject r9 = new org.json.JSONObject     // Catch: java.lang.Throwable -> L2f
                r9.<init>()     // Catch: java.lang.Throwable -> L2f
                java.lang.String r9 = r9.toString()     // Catch: java.lang.Throwable -> L2f
                com.huawei.hms.common.internal.TaskApiCallWrapper r3 = r7.f15928b     // Catch: java.lang.Throwable -> L2c
                com.huawei.hmf.tasks.TaskCompletionSource r3 = r3.getTaskCompletionSource()     // Catch: java.lang.Throwable -> L2c
                com.huawei.hms.common.internal.TaskApiCallWrapper r4 = r7.f15928b     // Catch: java.lang.Throwable -> L2a
                com.huawei.hms.common.internal.TaskApiCall r1 = r4.getTaskApiCall()     // Catch: java.lang.Throwable -> L2a
                goto L54
            L2a:
                r4 = move-exception
                goto L3c
            L2c:
                r4 = move-exception
                r3 = r1
                goto L3c
            L2f:
                r4 = move-exception
                r9 = r1
                r3 = r9
                goto L3c
            L33:
                r9 = move-exception
                r4 = r9
                goto L39
            L36:
                r8 = move-exception
                r4 = r8
                r8 = r1
            L39:
                r9 = r1
                r2 = r9
                r3 = r2
            L3c:
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r6 = "<notifyCpException> "
                r5.append(r6)
                java.lang.String r4 = r4.getMessage()
                r5.append(r4)
                java.lang.String r4 = r5.toString()
                com.huawei.hms.support.log.HMSLog.e(r0, r4)
            L54:
                if (r8 == 0) goto L62
                if (r2 == 0) goto L62
                if (r9 == 0) goto L62
                if (r3 == 0) goto L62
                if (r1 == 0) goto L62
                r1.onResponse(r8, r2, r9, r3)
                goto L67
            L62:
                java.lang.String r8 = "<notifyCpException> isNotify is false, Can not notify CP."
                com.huawei.hms.support.log.HMSLog.e(r0, r8)
            L67:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.common.HuaweiApi.a.a(com.huawei.hms.common.HuaweiApi$RequestHandler, java.lang.Throwable):void");
        }

        @Override // java.lang.Runnable
        public void run() {
            RequestHandler requestHandler = new RequestHandler(this.f15927a);
            try {
                requestHandler.a(this.f15928b);
            } catch (Throwable th) {
                a(requestHandler, th);
            }
        }
    }

    public HuaweiApi(Activity activity, Api<TOption> api, TOption toption, AbstractClientBuilder abstractClientBuilder, int i2) {
        Checker.checkNonNull(activity, "Null activity is not permitted.");
        this.f15899h = new WeakReference<>(activity);
        a(activity, api, toption, abstractClientBuilder, i2, null);
    }

    @Deprecated
    public Task<Boolean> disconnectService() {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        RequestManager.getInstance();
        RequestManager.getHandler().post(new Runnable() { // from class: com.huawei.hms.common.HuaweiApi.1
            @Override // java.lang.Runnable
            public void run() {
                HuaweiApi.this.a((HuaweiApi<?>) this, (TaskCompletionSource<Boolean>) taskCompletionSource);
            }
        });
        return taskCompletionSource.getTask();
    }

    public <TResult, TClient extends AnyClient> Task<TResult> doWrite(TaskApiCall<TClient, TResult> taskApiCall) {
        this.f15902k = true;
        if (taskApiCall == null) {
            HMSLog.e("HuaweiApi", "in doWrite:taskApiCall is null");
            TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
            taskCompletionSource.setException(new ApiException(Status.FAILURE));
            return taskCompletionSource.getTask();
        }
        HiAnalyticsInnerClient.reportEntryClient(this.f15892a, taskApiCall.getUri(), TextUtils.isEmpty(this.f15898g.getSubAppID()) ? this.f15897f : this.f15898g.getSubAppID(), taskApiCall.getTransactionId(), String.valueOf(getKitSdkVersion()));
        if (this.f15905n == null) {
            this.f15905n = RequestManager.getInstance();
        }
        return a(taskApiCall);
    }

    public Activity getActivity() {
        WeakReference<Activity> weakReference = this.f15899h;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public int getApiLevel() {
        return this.f15901j;
    }

    public String getAppID() {
        return this.f15897f;
    }

    public AnyClient getClient(Looper looper, RequestHandler requestHandler) {
        return this.f15895d.buildClient(this.f15892a, getClientSetting(), requestHandler, requestHandler);
    }

    protected ClientSettings getClientSetting() {
        ClientSettings clientSettings = new ClientSettings(this.f15892a.getPackageName(), this.f15892a.getClass().getName(), getScopes(), this.f15896e, null, this.f15898g);
        if (!this.f15904m) {
            this.f15903l = HMSPackageManager.getInstance(this.f15892a).getHMSPackageNameForMultiService();
            HMSLog.i("HuaweiApi", "No setInnerHms, hms pkg name is " + this.f15903l);
        }
        clientSettings.setInnerHmsPkg(this.f15903l);
        clientSettings.setUseInnerHms(this.f15904m);
        WeakReference<Activity> weakReference = this.f15899h;
        if (weakReference != null) {
            clientSettings.setCpActivity(weakReference.get());
        }
        return clientSettings;
    }

    public Context getContext() {
        return this.f15892a;
    }

    public int getKitSdkVersion() {
        return this.f15900i;
    }

    public TOption getOption() {
        return this.f15893b;
    }

    protected List<Scope> getScopes() {
        return Collections.emptyList();
    }

    public String getSubAppID() {
        return this.f15898g.getSubAppID();
    }

    public void setApiLevel(int i2) {
        this.f15901j = i2;
    }

    public void setHostContext(Context context) {
        this.f15894c = context;
    }

    public void setInnerHms() {
        this.f15903l = this.f15892a.getPackageName();
        this.f15904m = true;
        HMSLog.i("HuaweiApi", "<setInnerHms> init inner hms pkg info:" + this.f15903l);
    }

    public void setKitSdkVersion(int i2) {
        this.f15900i = i2;
    }

    public void setSubAppId(String str) throws ApiException {
        if (!setSubAppInfo(new SubAppInfo(str))) {
            throw new ApiException(Status.FAILURE);
        }
    }

    @Deprecated
    public boolean setSubAppInfo(SubAppInfo subAppInfo) {
        HMSLog.i("HuaweiApi", "Enter setSubAppInfo");
        SubAppInfo subAppInfo2 = this.f15898g;
        if (subAppInfo2 != null && !TextUtils.isEmpty(subAppInfo2.getSubAppID())) {
            HMSLog.e("HuaweiApi", "subAppInfo is already set");
            return false;
        }
        if (subAppInfo == null) {
            HMSLog.e("HuaweiApi", "subAppInfo is null");
            return false;
        }
        String subAppID = subAppInfo.getSubAppID();
        if (TextUtils.isEmpty(subAppID)) {
            HMSLog.e("HuaweiApi", "subAppId is empty");
            return false;
        }
        if (subAppID.equals(this.f15896e)) {
            HMSLog.e("HuaweiApi", "subAppId is host appid");
            return false;
        }
        if (this.f15902k) {
            HMSLog.e("HuaweiApi", "Client has sent request to Huawei Mobile Services, setting subAppId is not allowed");
            return false;
        }
        this.f15898g = new SubAppInfo(subAppInfo);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Context context) {
        String appId = Util.getAppId(context);
        this.f15896e = appId;
        this.f15897f = appId;
    }

    private void a(Context context, Api<TOption> api, TOption toption, AbstractClientBuilder abstractClientBuilder, int i2, String str) {
        this.f15892a = context.getApplicationContext();
        this.f15893b = toption;
        this.f15895d = abstractClientBuilder;
        b(context);
        this.f15898g = new SubAppInfo("");
        this.f15900i = i2;
        if (!TextUtils.isEmpty(str)) {
            if (str.equals(this.f15896e)) {
                HMSLog.e("HuaweiApi", "subAppId is host appid");
            } else {
                HMSLog.i("HuaweiApi", "subAppId is " + str);
                this.f15898g = new SubAppInfo(str);
            }
        }
        a(context);
    }

    public HuaweiApi(Activity activity, Api<TOption> api, TOption toption, AbstractClientBuilder abstractClientBuilder, int i2, String str) {
        Checker.checkNonNull(activity, "Null activity is not permitted.");
        this.f15899h = new WeakReference<>(activity);
        a(activity, api, toption, abstractClientBuilder, i2, str);
    }

    public HuaweiApi(Activity activity, Api<TOption> api, TOption toption, AbstractClientBuilder abstractClientBuilder) {
        Checker.checkNonNull(activity, "Null activity is not permitted.");
        this.f15899h = new WeakReference<>(activity);
        a(activity, api, toption, abstractClientBuilder, 0, null);
    }

    private void a(Context context) {
        HMSBIInitializer.getInstance(context).initBI();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(HuaweiApi<?> huaweiApi, TaskCompletionSource<Boolean> taskCompletionSource) {
        HMSLog.i("HuaweiApi", "innerDisconnect.");
        try {
            huaweiApi.getClient(RequestManager.getHandler().getLooper(), null).disconnect();
            taskCompletionSource.setResult(Boolean.TRUE);
        } catch (Exception e2) {
            HMSLog.w("HuaweiApi", "disconnect the binder failed for:" + e2.getMessage());
        }
    }

    public HuaweiApi(Context context, Api<TOption> api, TOption toption, AbstractClientBuilder abstractClientBuilder, int i2) {
        Checker.checkNonNull(context, "Null context is not permitted.");
        a(context, api, toption, abstractClientBuilder, i2, null);
    }

    private <TResult, TClient extends AnyClient> Task<TResult> a(TaskApiCall<TClient, TResult> taskApiCall) {
        TaskCompletionSource taskCompletionSource;
        if (taskApiCall.getToken() == null) {
            taskCompletionSource = new TaskCompletionSource();
        } else {
            taskCompletionSource = new TaskCompletionSource(taskApiCall.getToken());
        }
        RequestManager.getHandler().post(new a(this, new TaskApiCallWrapper(taskApiCall, taskCompletionSource)));
        return taskCompletionSource.getTask();
    }

    public HuaweiApi(Context context, Api<TOption> api, TOption toption, AbstractClientBuilder abstractClientBuilder, int i2, String str) {
        Checker.checkNonNull(context, "Null context is not permitted.");
        a(context, api, toption, abstractClientBuilder, i2, str);
    }

    public HuaweiApi(Context context, Api<TOption> api, TOption toption, AbstractClientBuilder abstractClientBuilder) {
        Checker.checkNonNull(context, "Null context is not permitted.");
        a(context, api, toption, abstractClientBuilder, 0, null);
    }
}
