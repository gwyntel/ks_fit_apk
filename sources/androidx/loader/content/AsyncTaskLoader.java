package androidx.loader.content;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.text.format.DateUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.os.OperationCanceledException;
import com.xiaomi.mipush.sdk.Constants;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public abstract class AsyncTaskLoader<D> extends Loader<D> {
    private static final boolean DEBUG = false;
    private static final String TAG = "AsyncTaskLoader";
    private volatile AsyncTaskLoader<D>.LoadTask mCancellingTask;
    private Executor mExecutor;
    private Handler mHandler;
    private long mLastLoadCompleteTime;
    private volatile AsyncTaskLoader<D>.LoadTask mTask;
    private long mUpdateThrottle;

    final class LoadTask extends ModernAsyncTask<D> implements Runnable {

        /* renamed from: c, reason: collision with root package name */
        boolean f4603c;

        LoadTask() {
        }

        @Override // androidx.loader.content.ModernAsyncTask
        protected Object a() {
            try {
                return AsyncTaskLoader.this.k();
            } catch (OperationCanceledException e2) {
                if (isCancelled()) {
                    return null;
                }
                throw e2;
            }
        }

        @Override // androidx.loader.content.ModernAsyncTask
        protected void c(Object obj) {
            AsyncTaskLoader.this.g(this, obj);
        }

        @Override // androidx.loader.content.ModernAsyncTask
        protected void d(Object obj) {
            AsyncTaskLoader.this.h(this, obj);
        }

        @Override // java.lang.Runnable
        public void run() {
            this.f4603c = false;
            AsyncTaskLoader.this.i();
        }
    }

    public AsyncTaskLoader(@NonNull Context context) {
        super(context);
        this.mLastLoadCompleteTime = -10000L;
    }

    @Override // androidx.loader.content.Loader
    protected boolean b() {
        if (this.mTask == null) {
            return false;
        }
        if (!isStarted()) {
            onContentChanged();
        }
        if (this.mCancellingTask != null) {
            if (this.mTask.f4603c) {
                this.mTask.f4603c = false;
                this.mHandler.removeCallbacks(this.mTask);
            }
            this.mTask = null;
            return false;
        }
        if (this.mTask.f4603c) {
            this.mTask.f4603c = false;
            this.mHandler.removeCallbacks(this.mTask);
            this.mTask = null;
            return false;
        }
        boolean zCancel = this.mTask.cancel(false);
        if (zCancel) {
            this.mCancellingTask = this.mTask;
            cancelLoadInBackground();
        }
        this.mTask = null;
        return zCancel;
    }

    @Override // androidx.loader.content.Loader
    protected void c() {
        super.c();
        cancelLoad();
        this.mTask = new LoadTask();
        i();
    }

    public void cancelLoadInBackground() {
    }

    @Override // androidx.loader.content.Loader
    @Deprecated
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str2;
        super.dump(str, fileDescriptor, printWriter, strArr);
        if (this.mTask != null) {
            printWriter.print(str);
            printWriter.print("mTask=");
            printWriter.print(this.mTask);
            printWriter.print(" waiting=");
            printWriter.println(this.mTask.f4603c);
        }
        if (this.mCancellingTask != null) {
            printWriter.print(str);
            printWriter.print("mCancellingTask=");
            printWriter.print(this.mCancellingTask);
            printWriter.print(" waiting=");
            printWriter.println(this.mCancellingTask.f4603c);
        }
        if (this.mUpdateThrottle != 0) {
            printWriter.print(str);
            printWriter.print("mUpdateThrottle=");
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            printWriter.print(DateUtils.formatElapsedTime(timeUnit.toSeconds(this.mUpdateThrottle)));
            printWriter.print(" mLastLoadCompleteTime=");
            if (this.mLastLoadCompleteTime == -10000) {
                str2 = "--";
            } else {
                str2 = Constants.ACCEPT_TIME_SEPARATOR_SERVER + DateUtils.formatElapsedTime(timeUnit.toSeconds(SystemClock.uptimeMillis() - this.mLastLoadCompleteTime));
            }
            printWriter.print(str2);
            printWriter.println();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    void g(LoadTask loadTask, Object obj) {
        onCanceled(obj);
        if (this.mCancellingTask == loadTask) {
            rollbackContentChanged();
            this.mLastLoadCompleteTime = SystemClock.uptimeMillis();
            this.mCancellingTask = null;
            deliverCancellation();
            i();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    void h(LoadTask loadTask, Object obj) {
        if (this.mTask != loadTask) {
            g(loadTask, obj);
            return;
        }
        if (isAbandoned()) {
            onCanceled(obj);
            return;
        }
        commitContentChanged();
        this.mLastLoadCompleteTime = SystemClock.uptimeMillis();
        this.mTask = null;
        deliverResult(obj);
    }

    void i() {
        if (this.mCancellingTask != null || this.mTask == null) {
            return;
        }
        if (this.mTask.f4603c) {
            this.mTask.f4603c = false;
            this.mHandler.removeCallbacks(this.mTask);
        }
        if (this.mUpdateThrottle > 0 && SystemClock.uptimeMillis() < this.mLastLoadCompleteTime + this.mUpdateThrottle) {
            this.mTask.f4603c = true;
            this.mHandler.postAtTime(this.mTask, this.mLastLoadCompleteTime + this.mUpdateThrottle);
        } else {
            if (this.mExecutor == null) {
                this.mExecutor = j();
            }
            this.mTask.executeOnExecutor(this.mExecutor);
        }
    }

    public boolean isLoadInBackgroundCanceled() {
        return this.mCancellingTask != null;
    }

    protected Executor j() {
        return AsyncTask.THREAD_POOL_EXECUTOR;
    }

    protected Object k() {
        return loadInBackground();
    }

    @Nullable
    public abstract D loadInBackground();

    public void onCanceled(@Nullable D d2) {
    }

    public void setUpdateThrottle(long j2) {
        this.mUpdateThrottle = j2;
        if (j2 != 0) {
            this.mHandler = new Handler();
        }
    }
}
