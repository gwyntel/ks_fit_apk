package com.aliyun.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes3.dex */
public abstract class b {
    public static final String DATA_KEY_EXTRA = "data_extra";
    public static final int WHAT_FAIL = 0;
    public static final int WHAT_SUCCESS = 1;
    private static ExecutorService sThreadPool = Executors.newCachedThreadPool();
    public WeakReference<Context> mContextWeak;
    private d outerListener;
    protected boolean wantStop = false;
    private c handler = null;
    private d innerListener = new a();

    class a implements d {
        a() {
        }

        @Override // com.aliyun.utils.b.d
        public void onFail(int i2, String str, String str2) {
            if (b.this.outerListener != null) {
                b.this.outerListener.onFail(i2, str, str2);
            }
        }

        @Override // com.aliyun.utils.b.d
        public void onSuccess(Object obj, String str) {
            if (b.this.outerListener != null) {
                b.this.outerListener.onSuccess(obj, str);
            }
        }
    }

    /* renamed from: com.aliyun.utils.b$b, reason: collision with other inner class name */
    class RunnableC0100b implements Runnable {
        RunnableC0100b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            b.this.runInBackground();
        }
    }

    private static class c extends Handler {

        /* renamed from: a, reason: collision with root package name */
        private b f12123a;

        public c(b bVar) {
            this.f12123a = bVar;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            b bVar = this.f12123a;
            if (bVar != null) {
                bVar.dealMsg(message);
            }
        }
    }

    public interface d<Result> {
        void onFail(int i2, String str, String str2);

        void onSuccess(Result result, String str);
    }

    public b(Context context, d dVar) {
        this.outerListener = null;
        this.mContextWeak = new WeakReference<>(context);
        this.outerListener = dVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dealMsg(Message message) {
        Bundle data = message.getData();
        String string = data != null ? data.getString("data_extra", "") : "";
        int i2 = message.what;
        if (i2 == 1) {
            this.innerListener.onSuccess(message.obj, string);
        } else if (i2 == 0) {
            this.innerListener.onFail(message.arg1, (String) message.obj, string);
        }
    }

    public void getAsync() {
        this.handler = new c(this);
        sThreadPool.execute(new RunnableC0100b());
    }

    public void getSync() {
        runInBackground();
    }

    public abstract void runInBackground();

    public void sendFailResult(int i2, String str, String str2) {
        if (this.wantStop) {
            return;
        }
        c cVar = this.handler;
        if (cVar == null) {
            this.innerListener.onFail(i2, str, str2);
            return;
        }
        Message messageObtainMessage = cVar.obtainMessage(0);
        messageObtainMessage.what = 0;
        messageObtainMessage.arg1 = i2;
        messageObtainMessage.obj = str;
        Bundle bundle = new Bundle();
        bundle.putString("data_extra", str2);
        messageObtainMessage.setData(bundle);
        this.handler.sendMessage(messageObtainMessage);
    }

    public void sendSuccessResult(Object obj, String str) {
        if (this.wantStop) {
            return;
        }
        c cVar = this.handler;
        if (cVar == null) {
            this.innerListener.onSuccess(obj, str);
            return;
        }
        Message messageObtainMessage = cVar.obtainMessage(1);
        messageObtainMessage.obj = obj;
        Bundle bundle = new Bundle();
        bundle.putString("data_extra", str);
        messageObtainMessage.setData(bundle);
        this.handler.sendMessage(messageObtainMessage);
    }

    public void stop() {
        this.wantStop = true;
        stopInner();
    }

    public abstract void stopInner();
}
