package com.huawei.hms.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.huawei.hms.activity.BridgeActivity;
import com.huawei.hms.api.BindingFailedResolution;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.Util;

/* loaded from: classes.dex */
public class BinderAdapter implements ServiceConnection {

    /* renamed from: a, reason: collision with root package name */
    private final Context f15771a;

    /* renamed from: b, reason: collision with root package name */
    private final String f15772b;

    /* renamed from: c, reason: collision with root package name */
    private final String f15773c;

    /* renamed from: d, reason: collision with root package name */
    private BinderCallBack f15774d;

    /* renamed from: e, reason: collision with root package name */
    private IBinder f15775e;

    /* renamed from: f, reason: collision with root package name */
    private final Object f15776f = new Object();

    /* renamed from: g, reason: collision with root package name */
    private boolean f15777g = false;

    /* renamed from: h, reason: collision with root package name */
    private Handler f15778h = null;

    /* renamed from: i, reason: collision with root package name */
    private Handler f15779i = null;

    public interface BinderCallBack {
        void onBinderFailed(int i2);

        void onBinderFailed(int i2, Intent intent);

        void onNullBinding(ComponentName componentName);

        void onServiceConnected(ComponentName componentName, IBinder iBinder);

        void onServiceDisconnected(ComponentName componentName);

        void onTimedDisconnected();
    }

    public BinderAdapter(Context context, String str, String str2) {
        this.f15771a = context;
        this.f15772b = str;
        this.f15773c = str2;
    }

    private void c() {
        synchronized (this.f15776f) {
            try {
                Handler handler = this.f15778h;
                if (handler != null) {
                    handler.removeMessages(getConnTimeOut());
                    this.f15778h = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void d() {
        Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.huawei.hms.adapter.BinderAdapter.2
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                if (message == null || message.what != BinderAdapter.this.getMsgDelayDisconnect()) {
                    return false;
                }
                HMSLog.i("BinderAdapter", "The serviceConnection has been bind for 1800s, need to unbind.");
                BinderAdapter.this.unBind();
                BinderCallBack binderCallBackF = BinderAdapter.this.f();
                if (binderCallBackF == null) {
                    return true;
                }
                binderCallBackF.onTimedDisconnected();
                return true;
            }
        });
        this.f15779i = handler;
        handler.sendEmptyMessageDelayed(getMsgDelayDisconnect(), 1800000L);
    }

    private void e() {
        HMSLog.e("BinderAdapter", "In connect, bind core service fail");
        try {
            ComponentName componentName = new ComponentName(this.f15771a.getApplicationInfo().packageName, "com.huawei.hms.activity.BridgeActivity");
            Intent intent = new Intent();
            intent.setComponent(componentName);
            intent.putExtra(BridgeActivity.EXTRA_DELEGATE_CLASS_NAME, BindingFailedResolution.class.getName());
            BinderCallBack binderCallBackF = f();
            if (binderCallBackF != null) {
                binderCallBackF.onBinderFailed(-1, intent);
            }
        } catch (RuntimeException e2) {
            HMSLog.e("BinderAdapter", "getBindFailPendingIntent failed " + e2.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BinderCallBack f() {
        return this.f15774d;
    }

    private void g() {
        Handler handler = this.f15778h;
        if (handler != null) {
            handler.removeMessages(getConnTimeOut());
        } else {
            this.f15778h = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.huawei.hms.adapter.BinderAdapter.1
                @Override // android.os.Handler.Callback
                public boolean handleMessage(Message message) {
                    if (message == null || message.what != BinderAdapter.this.getConnTimeOut()) {
                        return false;
                    }
                    HMSLog.e("BinderAdapter", "In connect, bind core service time out");
                    BinderAdapter.this.b();
                    return true;
                }
            });
        }
        this.f15778h.sendEmptyMessageDelayed(getConnTimeOut(), 10000L);
    }

    private void h() {
        HMSLog.d("BinderAdapter", "removeDelayDisconnectTask.");
        synchronized (BinderAdapter.class) {
            try {
                Handler handler = this.f15779i;
                if (handler != null) {
                    handler.removeMessages(getMsgDelayDisconnect());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void binder(BinderCallBack binderCallBack) {
        if (binderCallBack == null) {
            return;
        }
        this.f15774d = binderCallBack;
        a();
    }

    protected int getConnTimeOut() {
        return 0;
    }

    protected int getMsgDelayDisconnect() {
        return 0;
    }

    public String getServiceAction() {
        return this.f15772b;
    }

    public IBinder getServiceBinder() {
        return this.f15775e;
    }

    @Override // android.content.ServiceConnection
    public void onNullBinding(ComponentName componentName) {
        HMSLog.e("BinderAdapter", "Enter onNullBinding, than unBind.");
        if (this.f15777g) {
            this.f15777g = false;
            return;
        }
        unBind();
        c();
        BinderCallBack binderCallBackF = f();
        if (binderCallBackF != null) {
            binderCallBackF.onNullBinding(componentName);
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        HMSLog.i("BinderAdapter", "BinderAdapter Enter onServiceConnected.");
        this.f15775e = iBinder;
        c();
        BinderCallBack binderCallBackF = f();
        if (binderCallBackF != null) {
            binderCallBackF.onServiceConnected(componentName, iBinder);
        }
        d();
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        HMSLog.i("BinderAdapter", "Enter onServiceDisconnected.");
        BinderCallBack binderCallBackF = f();
        if (binderCallBackF != null) {
            binderCallBackF.onServiceDisconnected(componentName);
        }
        h();
    }

    public void unBind() {
        Util.unBindServiceCatchException(this.f15771a, this);
    }

    public void updateDelayTask() {
        HMSLog.d("BinderAdapter", "updateDelayTask.");
        synchronized (BinderAdapter.class) {
            try {
                Handler handler = this.f15779i;
                if (handler != null) {
                    handler.removeMessages(getMsgDelayDisconnect());
                    this.f15779i.sendEmptyMessageDelayed(getMsgDelayDisconnect(), 1800000L);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void a() {
        if (TextUtils.isEmpty(this.f15772b) || TextUtils.isEmpty(this.f15773c)) {
            e();
        }
        Intent intent = new Intent(this.f15772b);
        try {
            intent.setPackage(this.f15773c);
        } catch (IllegalArgumentException unused) {
            HMSLog.e("BinderAdapter", "IllegalArgumentException when bindCoreService intent.setPackage");
            e();
        }
        synchronized (this.f15776f) {
            try {
                if (this.f15771a.bindService(intent, this, 1)) {
                    g();
                } else {
                    this.f15777g = true;
                    e();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        BinderCallBack binderCallBackF = f();
        if (binderCallBackF != null) {
            binderCallBackF.onBinderFailed(-1);
        }
    }
}
