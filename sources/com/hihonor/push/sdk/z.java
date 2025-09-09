package com.hihonor.push.sdk;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.hihonor.push.framework.aidl.DataBuffer;
import com.hihonor.push.framework.aidl.IMessageEntity;
import com.hihonor.push.framework.aidl.IPushInvoke;
import com.hihonor.push.framework.aidl.MessageCodec;
import com.hihonor.push.framework.aidl.entity.RequestHeader;
import com.hihonor.push.sdk.b0;
import com.hihonor.push.sdk.bean.RemoteServiceBean;
import com.hihonor.push.sdk.internal.HonorPushErrorEnum;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public class z implements Handler.Callback {

    /* renamed from: c, reason: collision with root package name */
    public static final z f15569c = new z();

    /* renamed from: a, reason: collision with root package name */
    public final Handler f15570a;

    /* renamed from: b, reason: collision with root package name */
    public final Map<w, a> f15571b = new ConcurrentHashMap(5, 0.75f, 1);

    public static class b implements i0 {

        /* renamed from: a, reason: collision with root package name */
        public f1<?> f15578a;

        public b(f1<?> f1Var) {
            this.f15578a = f1Var;
        }
    }

    public z() {
        HandlerThread handlerThread = new HandlerThread("HonorApiManager");
        handlerThread.start();
        this.f15570a = new Handler(handlerThread.getLooper(), this);
    }

    public <TResult> a1 a(f1<TResult> f1Var) {
        n0<TResult> n0Var = new n0<>();
        f1Var.f15488a = n0Var;
        Log.i("HonorApiManager", "sendRequest start");
        Handler handler = this.f15570a;
        handler.sendMessage(handler.obtainMessage(1, f1Var));
        return n0Var.f15522a;
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        a aVar;
        int i2 = message.what;
        if (i2 != 1) {
            if (i2 != 2) {
                return false;
            }
            f1 f1Var = (f1) message.obj;
            w wVar = f1Var.f15491d;
            if (wVar != null && this.f15571b.containsKey(wVar) && (aVar = this.f15571b.get(wVar)) != null) {
                synchronized (aVar) {
                    try {
                        aVar.f15573b.remove(f1Var);
                        if (aVar.f15572a.peek() == null || aVar.f15573b.peek() == null) {
                            aVar.a();
                            z.this.f15571b.remove(aVar.f15576e);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            return true;
        }
        f1<?> f1Var2 = (f1) message.obj;
        w wVar2 = f1Var2.f15491d;
        a aVar2 = this.f15571b.get(wVar2);
        if (aVar2 == null) {
            Log.i("HonorApiManager", "connect and send request, create new connection manager.");
            aVar2 = new a(wVar2);
            this.f15571b.put(wVar2, aVar2);
        }
        synchronized (aVar2) {
            try {
                com.hihonor.push.sdk.b.a(z.this.f15570a);
                if (((d0) aVar2.f15574c).a()) {
                    aVar2.a(f1Var2);
                } else {
                    aVar2.f15572a.add(f1Var2);
                    HonorPushErrorEnum honorPushErrorEnum = aVar2.f15575d;
                    if (honorPushErrorEnum == null || honorPushErrorEnum.getErrorCode() == 0) {
                        synchronized (aVar2) {
                            try {
                                com.hihonor.push.sdk.b.a(z.this.f15570a);
                                if (((d0) aVar2.f15574c).a()) {
                                    Log.i("HonorApiManager", "client is connected");
                                } else if (((d0) aVar2.f15574c).f15473a.get() == 5) {
                                    Log.i("HonorApiManager", "client is isConnecting");
                                } else {
                                    d0 d0Var = (d0) aVar2.f15574c;
                                    d0Var.getClass();
                                    Log.i("PushConnectionClient", " ==== PUSHSDK VERSION 70041301 ====");
                                    int i3 = d0Var.f15473a.get();
                                    Log.i("PushConnectionClient", "enter connect, connection Status: " + i3);
                                    if (i3 != 3 && i3 != 5 && i3 != 4) {
                                        l lVar = l.f15511e;
                                        int iB = com.hihonor.push.sdk.b.b(lVar.a());
                                        if (iB == HonorPushErrorEnum.SUCCESS.getErrorCode()) {
                                            d0Var.f15473a.set(5);
                                            RemoteServiceBean remoteServiceBeanA = com.hihonor.push.sdk.b.a(lVar.a());
                                            Log.i("PushConnectionClient", "enter bindCoreService.");
                                            f0 f0Var = new f0(remoteServiceBeanA);
                                            d0Var.f15476d = f0Var;
                                            f0Var.f15485b = new c0(d0Var);
                                            if (remoteServiceBeanA.checkServiceInfo()) {
                                                Intent intent = new Intent();
                                                String packageName = f0Var.f15484a.getPackageName();
                                                String packageAction = f0Var.f15484a.getPackageAction();
                                                String packageServiceName = f0Var.f15484a.getPackageServiceName();
                                                if (TextUtils.isEmpty(packageServiceName)) {
                                                    intent.setAction(packageAction);
                                                    intent.setPackage(packageName);
                                                } else {
                                                    intent.setComponent(new ComponentName(packageName, packageServiceName));
                                                }
                                                synchronized (f0.f15483e) {
                                                    if (lVar.a().bindService(intent, f0Var, 1)) {
                                                        Handler handler = f0Var.f15486c;
                                                        if (handler != null) {
                                                            handler.removeMessages(1001);
                                                        } else {
                                                            f0Var.f15486c = new Handler(Looper.getMainLooper(), new e0(f0Var));
                                                        }
                                                        f0Var.f15486c.sendEmptyMessageDelayed(1001, 10000L);
                                                    } else {
                                                        f0Var.f15487d = true;
                                                        f0Var.a(8002001);
                                                    }
                                                }
                                            } else {
                                                Objects.toString(f0Var.f15484a);
                                                f0Var.a(8002004);
                                            }
                                        } else {
                                            d0Var.a(iB);
                                        }
                                    }
                                }
                            } catch (Throwable th2) {
                                throw th2;
                            } finally {
                            }
                        }
                    } else {
                        aVar2.a(aVar2.f15575d);
                    }
                }
            } catch (Throwable th3) {
                throw th3;
            }
        }
        return true;
    }

    public class a implements b0.a {

        /* renamed from: a, reason: collision with root package name */
        public final Queue<f1<?>> f15572a = new LinkedList();

        /* renamed from: b, reason: collision with root package name */
        public final Queue<f1<?>> f15573b = new LinkedList();

        /* renamed from: c, reason: collision with root package name */
        public final b0 f15574c = new d0(this);

        /* renamed from: d, reason: collision with root package name */
        public HonorPushErrorEnum f15575d = null;

        /* renamed from: e, reason: collision with root package name */
        public final w f15576e;

        public a(w wVar) {
            this.f15576e = wVar;
        }

        public final synchronized void a(HonorPushErrorEnum honorPushErrorEnum) {
            try {
                Log.i("HonorApiManager", "onConnectionFailed");
                com.hihonor.push.sdk.b.a(z.this.f15570a);
                Iterator<f1<?>> it = this.f15572a.iterator();
                while (it.hasNext()) {
                    it.next().b(honorPushErrorEnum.toApiException(), null);
                }
                this.f15572a.clear();
                this.f15575d = honorPushErrorEnum;
                a();
                z.this.f15571b.remove(this.f15576e);
            } catch (Throwable th) {
                throw th;
            }
        }

        public final synchronized void b() {
            try {
                Log.i("HonorApiManager", "onConnected");
                com.hihonor.push.sdk.b.a(z.this.f15570a);
                this.f15575d = null;
                Iterator<f1<?>> it = this.f15572a.iterator();
                while (it.hasNext()) {
                    a(it.next());
                }
                this.f15572a.clear();
            } catch (Throwable th) {
                throw th;
            }
        }

        public final synchronized void a(f1<?> f1Var) {
            Type type;
            try {
                this.f15573b.add(f1Var);
                b0 b0Var = this.f15574c;
                b bVar = new b(f1Var);
                f1Var.getClass();
                Object objNewInstance = null;
                try {
                    Type genericSuperclass = f1Var.getClass().getGenericSuperclass();
                    Class cls = (genericSuperclass == null || (type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0]) == null) ? null : (Class) type;
                    if (cls != null && !cls.isPrimitive()) {
                        objNewInstance = cls.newInstance();
                    }
                } catch (Exception e2) {
                    c.a("In newResponseInstance, instancing exception." + e2.getMessage());
                }
                h0 h0Var = new h0(objNewInstance, bVar);
                Log.i("IpcTransport", "start transport parse. " + f1Var.f15489b);
                IPushInvoke iPushInvoke = ((d0) b0Var).f15474b;
                String str = f1Var.f15489b;
                RequestHeader requestHeader = f1Var.f15492e;
                IMessageEntity iMessageEntity = f1Var.f15490c;
                Bundle bundle = new Bundle();
                Bundle bundle2 = new Bundle();
                MessageCodec.formMessageEntity(requestHeader, bundle);
                MessageCodec.formMessageEntity(iMessageEntity, bundle2);
                DataBuffer dataBuffer = new DataBuffer(str, bundle, bundle2);
                if (iPushInvoke != null) {
                    try {
                        iPushInvoke.call(dataBuffer, h0Var);
                    } catch (Exception e3) {
                        e3.toString();
                    }
                }
                Log.i("IpcTransport", "end transport parse.");
            } catch (Throwable th) {
                throw th;
            }
        }

        public void a() {
            com.hihonor.push.sdk.b.a(z.this.f15570a);
            d0 d0Var = (d0) this.f15574c;
            int i2 = d0Var.f15473a.get();
            Log.i("PushConnectionClient", "enter disconnect, connection Status: " + i2);
            if (i2 != 3) {
                if (i2 != 5) {
                    return;
                }
                d0Var.f15473a.set(4);
            } else {
                f0 f0Var = d0Var.f15476d;
                if (f0Var != null) {
                    f0Var.b();
                }
                d0Var.f15473a.set(1);
            }
        }
    }
}
