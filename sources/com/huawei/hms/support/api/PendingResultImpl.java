package com.huawei.hms.support.api;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Pair;
import com.huawei.hms.adapter.BaseAdapter;
import com.huawei.hms.common.internal.TransactionIdCreater;
import com.huawei.hms.core.aidl.AbstractMessageEntity;
import com.huawei.hms.core.aidl.IMessageEntity;
import com.huawei.hms.support.api.client.ApiClient;
import com.huawei.hms.support.api.client.InnerPendingResult;
import com.huawei.hms.support.api.client.Result;
import com.huawei.hms.support.api.client.ResultCallback;
import com.huawei.hms.support.api.client.Status;
import com.huawei.hms.support.api.client.SubAppInfo;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.huawei.hms.support.api.transport.DatagramTransport;
import com.huawei.hms.support.gentyref.GenericTypeReflector;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.huawei.hms.support.hianalytics.HiAnalyticsUtil;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.Util;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public abstract class PendingResultImpl<R extends Result, T extends IMessageEntity> extends InnerPendingResult<R> {

    /* renamed from: a, reason: collision with root package name */
    private CountDownLatch f18089a;

    /* renamed from: c, reason: collision with root package name */
    private WeakReference<ApiClient> f18091c;
    protected DatagramTransport transport = null;

    /* renamed from: b, reason: collision with root package name */
    private R f18090b = null;

    /* renamed from: d, reason: collision with root package name */
    private String f18092d = null;

    /* renamed from: e, reason: collision with root package name */
    private String f18093e = null;

    /* renamed from: f, reason: collision with root package name */
    private boolean f18094f = true;

    class a implements DatagramTransport.a {
        a() {
        }

        @Override // com.huawei.hms.support.api.transport.DatagramTransport.a
        public void a(int i2, IMessageEntity iMessageEntity) {
            PendingResultImpl.this.a(i2, iMessageEntity);
            PendingResultImpl.this.f18089a.countDown();
        }
    }

    class b implements DatagramTransport.a {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ AtomicBoolean f18096a;

        b(AtomicBoolean atomicBoolean) {
            this.f18096a = atomicBoolean;
        }

        @Override // com.huawei.hms.support.api.transport.DatagramTransport.a
        public void a(int i2, IMessageEntity iMessageEntity) {
            if (!this.f18096a.get()) {
                PendingResultImpl.this.a(i2, iMessageEntity);
            }
            PendingResultImpl.this.f18089a.countDown();
        }
    }

    class c implements DatagramTransport.a {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ d f18098a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ ResultCallback f18099b;

        c(d dVar, ResultCallback resultCallback) {
            this.f18098a = dVar;
            this.f18099b = resultCallback;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.huawei.hms.support.api.transport.DatagramTransport.a
        public void a(int i2, IMessageEntity iMessageEntity) {
            PendingResultImpl.this.a(i2, iMessageEntity);
            this.f18098a.a(this.f18099b, PendingResultImpl.this.f18090b);
        }
    }

    protected static class d<R extends Result> extends Handler {
        public d(Looper looper) {
            super(looper);
        }

        public void a(ResultCallback<? super R> resultCallback, R r2) {
            sendMessage(obtainMessage(1, new Pair(resultCallback, r2)));
        }

        /* JADX WARN: Multi-variable type inference failed */
        protected void b(ResultCallback<? super R> resultCallback, R r2) {
            resultCallback.onResult(r2);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            Pair pair = (Pair) message.obj;
            b((ResultCallback) pair.first, (Result) pair.second);
        }
    }

    public PendingResultImpl(ApiClient apiClient, String str, IMessageEntity iMessageEntity) {
        a(apiClient, str, iMessageEntity, getResponseType(), 0);
    }

    @Override // com.huawei.hms.support.api.client.PendingResult
    public final R await() {
        HMSLog.i("PendingResultImpl", "await");
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return (R) awaitOnAnyThread();
        }
        HMSLog.e("PendingResultImpl", "await in main thread");
        throw new IllegalStateException("await must not be called on the UI thread");
    }

    @Override // com.huawei.hms.support.api.client.InnerPendingResult
    public final R awaitOnAnyThread() throws InterruptedException {
        HMSLog.i("PendingResultImpl", "awaitOnAnyThread");
        WeakReference<ApiClient> weakReference = this.f18091c;
        if (weakReference == null) {
            HMSLog.e("PendingResultImpl", "api is null");
            a(CommonCode.ErrorCode.CLIENT_API_INVALID, (IMessageEntity) null);
            return this.f18090b;
        }
        ApiClient apiClient = weakReference.get();
        if (!checkApiClient(apiClient)) {
            HMSLog.e("PendingResultImpl", "client invalid");
            a(CommonCode.ErrorCode.CLIENT_API_INVALID, (IMessageEntity) null);
            return this.f18090b;
        }
        if (this.f18094f) {
            a(0, 1);
        }
        this.transport.send(apiClient, new a());
        try {
            this.f18089a.await();
        } catch (InterruptedException unused) {
            HMSLog.e("PendingResultImpl", "await in anythread InterruptedException");
            a(CommonCode.ErrorCode.INTERNAL_ERROR, (IMessageEntity) null);
        }
        return this.f18090b;
    }

    @Override // com.huawei.hms.support.api.client.PendingResult
    @Deprecated
    public void cancel() {
    }

    protected boolean checkApiClient(ApiClient apiClient) {
        return true;
    }

    protected Class<T> getResponseType() {
        Type type;
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass == null || (type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[1]) == null) {
            return null;
        }
        return (Class) type;
    }

    @Override // com.huawei.hms.support.api.client.PendingResult
    @Deprecated
    public boolean isCanceled() {
        return false;
    }

    public abstract R onComplete(T t2);

    protected R onError(int i2) {
        Type genericSuperclass = getClass().getGenericSuperclass();
        Type type = genericSuperclass != null ? ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0] : null;
        Class<?> type2 = type != null ? GenericTypeReflector.getType(type) : null;
        if (type2 != null) {
            try {
                R r2 = (R) type2.newInstance();
                this.f18090b = r2;
                r2.setStatus(new Status(i2));
            } catch (Exception e2) {
                HMSLog.e("PendingResultImpl", "on Error:" + e2.getMessage());
                return null;
            }
        }
        return this.f18090b;
    }

    @Override // com.huawei.hms.support.api.client.PendingResult
    public void setResultCallback(ResultCallback<R> resultCallback) {
        this.f18094f = !(resultCallback instanceof BaseAdapter.BaseRequestResultCallback);
        setResultCallback(Looper.getMainLooper(), resultCallback);
    }

    private void a(ApiClient apiClient, String str, IMessageEntity iMessageEntity, Class<T> cls, int i2) {
        HMSLog.i("PendingResultImpl", "init uri:" + str);
        this.f18092d = str;
        if (apiClient == null) {
            HMSLog.e("PendingResultImpl", "client is null");
            return;
        }
        this.f18091c = new WeakReference<>(apiClient);
        this.f18089a = new CountDownLatch(1);
        try {
            this.transport = (DatagramTransport) Class.forName(apiClient.getTransportName()).getConstructor(String.class, IMessageEntity.class, Class.class, Integer.TYPE).newInstance(str, iMessageEntity, cls, Integer.valueOf(i2));
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | InvocationTargetException e2) {
            HMSLog.e("PendingResultImpl", "gen transport error:" + e2.getMessage());
            throw new IllegalStateException("Instancing transport exception, " + e2.getMessage(), e2);
        }
    }

    @Override // com.huawei.hms.support.api.client.PendingResult
    public final void setResultCallback(Looper looper, ResultCallback<R> resultCallback) {
        HMSLog.i("PendingResultImpl", "setResultCallback");
        if (looper == null) {
            looper = Looper.myLooper();
        }
        d dVar = new d(looper);
        WeakReference<ApiClient> weakReference = this.f18091c;
        if (weakReference == null) {
            HMSLog.e("PendingResultImpl", "api is null");
            a(CommonCode.ErrorCode.CLIENT_API_INVALID, (IMessageEntity) null);
            return;
        }
        ApiClient apiClient = weakReference.get();
        if (!checkApiClient(apiClient)) {
            HMSLog.e("PendingResultImpl", "client is invalid");
            a(CommonCode.ErrorCode.CLIENT_API_INVALID, (IMessageEntity) null);
            dVar.a(resultCallback, this.f18090b);
        } else {
            if (this.f18094f) {
                a(0, 1);
            }
            this.transport.post(apiClient, new c(dVar, resultCallback));
        }
    }

    @Override // com.huawei.hms.support.api.client.PendingResult
    public R await(long j2, TimeUnit timeUnit) {
        HMSLog.i("PendingResultImpl", "await timeout:" + j2 + " unit:" + timeUnit.toString());
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return (R) awaitOnAnyThread(j2, timeUnit);
        }
        HMSLog.i("PendingResultImpl", "await in main thread");
        throw new IllegalStateException("await must not be called on the UI thread");
    }

    public PendingResultImpl(ApiClient apiClient, String str, IMessageEntity iMessageEntity, Class<T> cls) {
        a(apiClient, str, iMessageEntity, cls, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i2, IMessageEntity iMessageEntity) {
        Status status;
        HMSLog.i("PendingResultImpl", "setResult:" + i2);
        Status commonStatus = iMessageEntity instanceof AbstractMessageEntity ? ((AbstractMessageEntity) iMessageEntity).getCommonStatus() : null;
        if (i2 == 0) {
            this.f18090b = (R) onComplete(iMessageEntity);
        } else {
            this.f18090b = (R) onError(i2);
        }
        if (this.f18094f) {
            a(i2, 2);
        }
        R r2 = this.f18090b;
        if (r2 == null || (status = r2.getStatus()) == null || commonStatus == null) {
            return;
        }
        int statusCode = status.getStatusCode();
        String statusMessage = status.getStatusMessage();
        int statusCode2 = commonStatus.getStatusCode();
        String statusMessage2 = commonStatus.getStatusMessage();
        if (statusCode != statusCode2) {
            HMSLog.e("PendingResultImpl", "rstStatus code (" + statusCode + ") is not equal commonStatus code (" + statusCode2 + ")");
            HMSLog.e("PendingResultImpl", "rstStatus msg (" + statusMessage + ") is not equal commonStatus msg (" + statusMessage2 + ")");
            return;
        }
        if (!TextUtils.isEmpty(statusMessage) || TextUtils.isEmpty(statusMessage2)) {
            return;
        }
        HMSLog.i("PendingResultImpl", "rstStatus msg (" + statusMessage + ") is not equal commonStatus msg (" + statusMessage2 + ")");
        this.f18090b.setStatus(new Status(statusCode, statusMessage2, status.getResolution()));
    }

    public PendingResultImpl(ApiClient apiClient, String str, IMessageEntity iMessageEntity, int i2) {
        a(apiClient, str, iMessageEntity, getResponseType(), i2);
    }

    @Override // com.huawei.hms.support.api.client.PendingResult
    @Deprecated
    public void setResultCallback(ResultCallback<R> resultCallback, long j2, TimeUnit timeUnit) {
        setResultCallback(resultCallback);
    }

    @Override // com.huawei.hms.support.api.client.InnerPendingResult
    public final R awaitOnAnyThread(long j2, TimeUnit timeUnit) {
        HMSLog.i("PendingResultImpl", "awaitOnAnyThread timeout:" + j2 + " unit:" + timeUnit.toString());
        WeakReference<ApiClient> weakReference = this.f18091c;
        if (weakReference == null) {
            HMSLog.e("PendingResultImpl", "api is null");
            a(CommonCode.ErrorCode.CLIENT_API_INVALID, (IMessageEntity) null);
            return this.f18090b;
        }
        ApiClient apiClient = weakReference.get();
        if (!checkApiClient(apiClient)) {
            HMSLog.e("PendingResultImpl", "client invalid");
            a(CommonCode.ErrorCode.CLIENT_API_INVALID, (IMessageEntity) null);
            return this.f18090b;
        }
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        if (this.f18094f) {
            a(0, 1);
        }
        this.transport.post(apiClient, new b(atomicBoolean));
        try {
            if (!this.f18089a.await(j2, timeUnit)) {
                atomicBoolean.set(true);
                a(CommonCode.ErrorCode.EXECUTE_TIMEOUT, (IMessageEntity) null);
            }
        } catch (InterruptedException unused) {
            HMSLog.e("PendingResultImpl", "awaitOnAnyThread InterruptedException");
            a(CommonCode.ErrorCode.INTERNAL_ERROR, (IMessageEntity) null);
        }
        return this.f18090b;
    }

    private void a(int i2, int i3) {
        SubAppInfo subAppInfo;
        HMSLog.i("PendingResultImpl", "biReportEvent ====== ");
        ApiClient apiClient = this.f18091c.get();
        if (apiClient != null && this.f18092d != null && !HiAnalyticsUtil.getInstance().hasError(apiClient.getContext())) {
            HashMap map = new HashMap();
            map.put("package", apiClient.getPackageName());
            map.put(HiAnalyticsConstant.HaKey.BI_KEY_BASE_VERSION, "6.8.0.300");
            if (i3 == 1) {
                map.put(HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "req");
            } else {
                map.put(HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, HiAnalyticsConstant.Direction.RESPONSE);
                map.put("result", String.valueOf(i2));
                R r2 = this.f18090b;
                if (r2 != null && r2.getStatus() != null) {
                    map.put(HiAnalyticsConstant.HaKey.BI_KEY_RESULT, String.valueOf(this.f18090b.getStatus().getStatusCode()));
                }
            }
            map.put("version", "0");
            String appId = Util.getAppId(apiClient.getContext());
            if (TextUtils.isEmpty(appId) && (subAppInfo = apiClient.getSubAppInfo()) != null) {
                appId = subAppInfo.getSubAppID();
            }
            map.put("appid", appId);
            if (TextUtils.isEmpty(this.f18093e)) {
                String id = TransactionIdCreater.getId(appId, this.f18092d);
                this.f18093e = id;
                map.put(HiAnalyticsConstant.HaKey.BI_KEY_TRANSID, id);
            } else {
                map.put(HiAnalyticsConstant.HaKey.BI_KEY_TRANSID, this.f18093e);
                this.f18093e = null;
            }
            String[] strArrSplit = this.f18092d.split("\\.");
            if (strArrSplit.length >= 2) {
                map.put("service", strArrSplit[0]);
                map.put("apiName", strArrSplit[1]);
            }
            map.put("callTime", String.valueOf(System.currentTimeMillis()));
            map.put(HiAnalyticsConstant.HaKey.BI_KEY_PHONETYPE, Util.getSystemProperties("ro.logsystem.usertype", ""));
            HiAnalyticsUtil.getInstance().onEvent(apiClient.getContext(), HiAnalyticsConstant.HMS_SDK_BASE_CALL_AIDL, map);
            return;
        }
        HMSLog.e("PendingResultImpl", "<biReportEvent> has some error.");
    }
}
