package com.huawei.hms.common.internal;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.huawei.hms.common.HuaweiApi;
import com.huawei.hms.common.internal.BaseHmsClient;
import com.huawei.hms.core.aidl.IAIDLInvoke;
import com.huawei.hms.support.log.HMSLog;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/* loaded from: classes4.dex */
public class RequestManager implements Handler.Callback {
    public static final int NOTIFY_CONNECT_FAILED = 10012;
    public static final int NOTIFY_CONNECT_SUCCESS = 10011;
    public static final int NOTIFY_CONNECT_SUSPENDED = 10013;

    /* renamed from: b, reason: collision with root package name */
    private static volatile RequestManager f15980b;

    /* renamed from: c, reason: collision with root package name */
    private static Handler f15981c;

    /* renamed from: a, reason: collision with root package name */
    private static final Object f15979a = new Object();

    /* renamed from: d, reason: collision with root package name */
    private static Queue<HuaweiApi.RequestHandler> f15982d = new ConcurrentLinkedQueue();

    /* renamed from: e, reason: collision with root package name */
    private static Map<String, HuaweiApi.RequestHandler> f15983e = new LinkedHashMap();

    private RequestManager(Looper looper) {
        f15981c = new Handler(looper, this);
    }

    public static void addRequestToQueue(HuaweiApi.RequestHandler requestHandler) {
        f15982d.add(requestHandler);
    }

    public static void addToConnectedReqMap(final String str, final HuaweiApi.RequestHandler requestHandler) {
        if (f15981c == null) {
            return;
        }
        HMSLog.i("RequestManager", "addToConnectedReqMap");
        f15981c.post(new Runnable() { // from class: com.huawei.hms.common.internal.RequestManager.1
            @Override // java.lang.Runnable
            public void run() {
                RequestManager.f15983e.put(str, requestHandler);
            }
        });
    }

    private void b() {
        while (!f15982d.isEmpty()) {
            HuaweiApi.RequestHandler requestHandlerPoll = f15982d.poll();
            if (requestHandlerPoll != null) {
                Object client = requestHandlerPoll.getClient();
                if (client instanceof BaseHmsClient) {
                    BaseHmsClient baseHmsClient = (BaseHmsClient) client;
                    baseHmsClient.setService(IAIDLInvoke.Stub.asInterface(baseHmsClient.getAdapter().getServiceBinder()));
                    requestHandlerPoll.onConnected();
                }
            }
        }
    }

    private void c() {
        HMSLog.i("RequestManager", "NOTIFY_CONNECT_SUSPENDED.");
        while (!f15982d.isEmpty()) {
            f15982d.poll().onConnectionSuspended(1);
        }
        d();
    }

    private void d() {
        HMSLog.i("RequestManager", "notifyRunningRequestConnectSuspend, connectedReqMap.size(): " + f15983e.size());
        Iterator<Map.Entry<String, HuaweiApi.RequestHandler>> it = f15983e.entrySet().iterator();
        while (it.hasNext()) {
            try {
                it.next().getValue().onConnectionSuspended(1);
            } catch (RuntimeException e2) {
                HMSLog.e("RequestManager", "NOTIFY_CONNECT_SUSPENDED Exception: " + e2.getMessage());
            }
            it.remove();
        }
    }

    public static Handler getHandler() {
        return f15981c;
    }

    public static RequestManager getInstance() {
        synchronized (f15979a) {
            try {
                if (f15980b == null) {
                    HandlerThread handlerThread = new HandlerThread("RequestManager");
                    handlerThread.start();
                    f15980b = new RequestManager(handlerThread.getLooper());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return f15980b;
    }

    public static void removeReqByTransId(final String str) {
        if (f15981c == null) {
            return;
        }
        HMSLog.i("RequestManager", "removeReqByTransId");
        f15981c.post(new Runnable() { // from class: com.huawei.hms.common.internal.RequestManager.2
            @Override // java.lang.Runnable
            public void run() {
                RequestManager.f15983e.remove(str);
            }
        });
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        if (message == null) {
            return false;
        }
        HMSLog.i("RequestManager", "RequestManager handleMessage.");
        switch (message.what) {
            case 10011:
                b();
                break;
            case 10012:
                a(message);
                break;
            case 10013:
                c();
                break;
            default:
                HMSLog.i("RequestManager", "handleMessage unknown msg:" + message.what);
                break;
        }
        return false;
    }

    private void a(Message message) {
        HMSLog.i("RequestManager", "NOTIFY_CONNECT_FAILED.");
        try {
            BaseHmsClient.ConnectionResultWrapper connectionResultWrapper = (BaseHmsClient.ConnectionResultWrapper) message.obj;
            HuaweiApi.RequestHandler request = connectionResultWrapper.getRequest();
            f15982d.remove(request);
            request.onConnectionFailed(connectionResultWrapper.getConnectionResult());
        } catch (RuntimeException e2) {
            HMSLog.e("RequestManager", "<handleConnectFailed> handle Failed" + e2.getMessage());
        }
    }
}
