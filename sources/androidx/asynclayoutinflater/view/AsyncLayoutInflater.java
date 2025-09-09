package androidx.asynclayoutinflater.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.core.util.Pools;
import java.util.concurrent.ArrayBlockingQueue;

/* loaded from: classes.dex */
public final class AsyncLayoutInflater {
    private static final String TAG = "AsyncLayoutInflater";

    /* renamed from: a, reason: collision with root package name */
    LayoutInflater f2373a;
    private Handler.Callback mHandlerCallback = new Handler.Callback() { // from class: androidx.asynclayoutinflater.view.AsyncLayoutInflater.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            InflateRequest inflateRequest = (InflateRequest) message.obj;
            if (inflateRequest.f2380d == null) {
                inflateRequest.f2380d = AsyncLayoutInflater.this.f2373a.inflate(inflateRequest.f2379c, inflateRequest.f2378b, false);
            }
            inflateRequest.f2381e.onInflateFinished(inflateRequest.f2380d, inflateRequest.f2379c, inflateRequest.f2378b);
            AsyncLayoutInflater.this.f2375c.releaseRequest(inflateRequest);
            return true;
        }
    };

    /* renamed from: b, reason: collision with root package name */
    Handler f2374b = new Handler(this.mHandlerCallback);

    /* renamed from: c, reason: collision with root package name */
    InflateThread f2375c = InflateThread.getInstance();

    private static class BasicInflater extends LayoutInflater {
        private static final String[] sClassPrefixList = {"android.widget.", "android.webkit.", "android.app."};

        BasicInflater(Context context) {
            super(context);
        }

        @Override // android.view.LayoutInflater
        public LayoutInflater cloneInContext(Context context) {
            return new BasicInflater(context);
        }

        @Override // android.view.LayoutInflater
        protected View onCreateView(String str, AttributeSet attributeSet) throws InflateException, ClassNotFoundException {
            View viewCreateView;
            for (String str2 : sClassPrefixList) {
                try {
                    viewCreateView = createView(str, str2, attributeSet);
                } catch (ClassNotFoundException unused) {
                }
                if (viewCreateView != null) {
                    return viewCreateView;
                }
            }
            return super.onCreateView(str, attributeSet);
        }
    }

    private static class InflateRequest {

        /* renamed from: a, reason: collision with root package name */
        AsyncLayoutInflater f2377a;

        /* renamed from: b, reason: collision with root package name */
        ViewGroup f2378b;

        /* renamed from: c, reason: collision with root package name */
        int f2379c;

        /* renamed from: d, reason: collision with root package name */
        View f2380d;

        /* renamed from: e, reason: collision with root package name */
        OnInflateFinishedListener f2381e;

        InflateRequest() {
        }
    }

    private static class InflateThread extends Thread {
        private static final InflateThread sInstance;
        private ArrayBlockingQueue<InflateRequest> mQueue = new ArrayBlockingQueue<>(10);
        private Pools.SynchronizedPool<InflateRequest> mRequestPool = new Pools.SynchronizedPool<>(10);

        static {
            InflateThread inflateThread = new InflateThread();
            sInstance = inflateThread;
            inflateThread.start();
        }

        private InflateThread() {
        }

        public static InflateThread getInstance() {
            return sInstance;
        }

        public void enqueue(InflateRequest inflateRequest) throws InterruptedException {
            try {
                this.mQueue.put(inflateRequest);
            } catch (InterruptedException e2) {
                throw new RuntimeException("Failed to enqueue async inflate request", e2);
            }
        }

        public InflateRequest obtainRequest() {
            InflateRequest inflateRequestAcquire = this.mRequestPool.acquire();
            return inflateRequestAcquire == null ? new InflateRequest() : inflateRequestAcquire;
        }

        public void releaseRequest(InflateRequest inflateRequest) {
            inflateRequest.f2381e = null;
            inflateRequest.f2377a = null;
            inflateRequest.f2378b = null;
            inflateRequest.f2379c = 0;
            inflateRequest.f2380d = null;
            this.mRequestPool.release(inflateRequest);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() throws InterruptedException {
            while (true) {
                runInner();
            }
        }

        public void runInner() throws InterruptedException {
            try {
                InflateRequest inflateRequestTake = this.mQueue.take();
                try {
                    inflateRequestTake.f2380d = inflateRequestTake.f2377a.f2373a.inflate(inflateRequestTake.f2379c, inflateRequestTake.f2378b, false);
                } catch (RuntimeException e2) {
                    Log.w(AsyncLayoutInflater.TAG, "Failed to inflate resource in the background! Retrying on the UI thread", e2);
                }
                Message.obtain(inflateRequestTake.f2377a.f2374b, 0, inflateRequestTake).sendToTarget();
            } catch (InterruptedException e3) {
                Log.w(AsyncLayoutInflater.TAG, e3);
            }
        }
    }

    public interface OnInflateFinishedListener {
        void onInflateFinished(@NonNull View view, @LayoutRes int i2, @Nullable ViewGroup viewGroup);
    }

    public AsyncLayoutInflater(@NonNull Context context) {
        this.f2373a = new BasicInflater(context);
    }

    @UiThread
    public void inflate(@LayoutRes int i2, @Nullable ViewGroup viewGroup, @NonNull OnInflateFinishedListener onInflateFinishedListener) throws InterruptedException {
        if (onInflateFinishedListener == null) {
            throw new NullPointerException("callback argument may not be null!");
        }
        InflateRequest inflateRequestObtainRequest = this.f2375c.obtainRequest();
        inflateRequestObtainRequest.f2377a = this;
        inflateRequestObtainRequest.f2379c = i2;
        inflateRequestObtainRequest.f2378b = viewGroup;
        inflateRequestObtainRequest.f2381e = onInflateFinishedListener;
        this.f2375c.enqueue(inflateRequestObtainRequest);
    }
}
