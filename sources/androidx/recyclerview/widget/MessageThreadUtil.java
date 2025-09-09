package androidx.recyclerview.widget;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.recyclerview.widget.ThreadUtil;
import androidx.recyclerview.widget.TileList;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
class MessageThreadUtil<T> implements ThreadUtil<T> {

    /* renamed from: androidx.recyclerview.widget.MessageThreadUtil$1, reason: invalid class name */
    class AnonymousClass1 implements ThreadUtil.MainThreadCallback<T> {

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ ThreadUtil.MainThreadCallback f6000b;

        /* renamed from: a, reason: collision with root package name */
        final MessageQueue f5999a = new MessageQueue();
        private final Handler mMainThreadHandler = new Handler(Looper.getMainLooper());
        private Runnable mMainThreadRunnable = new Runnable() { // from class: androidx.recyclerview.widget.MessageThreadUtil.1.1
            @Override // java.lang.Runnable
            public void run() {
                SyncQueueItem syncQueueItemA = AnonymousClass1.this.f5999a.a();
                while (syncQueueItemA != null) {
                    int i2 = syncQueueItemA.what;
                    if (i2 == 1) {
                        AnonymousClass1.this.f6000b.updateItemCount(syncQueueItemA.arg1, syncQueueItemA.arg2);
                    } else if (i2 == 2) {
                        AnonymousClass1.this.f6000b.addTile(syncQueueItemA.arg1, (TileList.Tile) syncQueueItemA.data);
                    } else if (i2 != 3) {
                        Log.e("ThreadUtil", "Unsupported message, what=" + syncQueueItemA.what);
                    } else {
                        AnonymousClass1.this.f6000b.removeTile(syncQueueItemA.arg1, syncQueueItemA.arg2);
                    }
                    syncQueueItemA = AnonymousClass1.this.f5999a.a();
                }
            }
        };

        AnonymousClass1(ThreadUtil.MainThreadCallback mainThreadCallback) {
            this.f6000b = mainThreadCallback;
        }

        private void sendMessage(SyncQueueItem syncQueueItem) {
            this.f5999a.c(syncQueueItem);
            this.mMainThreadHandler.post(this.mMainThreadRunnable);
        }

        @Override // androidx.recyclerview.widget.ThreadUtil.MainThreadCallback
        public void addTile(int i2, TileList.Tile<T> tile) {
            sendMessage(SyncQueueItem.c(2, i2, tile));
        }

        @Override // androidx.recyclerview.widget.ThreadUtil.MainThreadCallback
        public void removeTile(int i2, int i3) {
            sendMessage(SyncQueueItem.a(3, i2, i3));
        }

        @Override // androidx.recyclerview.widget.ThreadUtil.MainThreadCallback
        public void updateItemCount(int i2, int i3) {
            sendMessage(SyncQueueItem.a(1, i2, i3));
        }
    }

    /* renamed from: androidx.recyclerview.widget.MessageThreadUtil$2, reason: invalid class name */
    class AnonymousClass2 implements ThreadUtil.BackgroundCallback<T> {

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ ThreadUtil.BackgroundCallback f6005c;

        /* renamed from: a, reason: collision with root package name */
        final MessageQueue f6003a = new MessageQueue();
        private final Executor mExecutor = AsyncTask.THREAD_POOL_EXECUTOR;

        /* renamed from: b, reason: collision with root package name */
        AtomicBoolean f6004b = new AtomicBoolean(false);
        private Runnable mBackgroundRunnable = new Runnable() { // from class: androidx.recyclerview.widget.MessageThreadUtil.2.1
            @Override // java.lang.Runnable
            public void run() {
                while (true) {
                    SyncQueueItem syncQueueItemA = AnonymousClass2.this.f6003a.a();
                    if (syncQueueItemA == null) {
                        AnonymousClass2.this.f6004b.set(false);
                        return;
                    }
                    int i2 = syncQueueItemA.what;
                    if (i2 == 1) {
                        AnonymousClass2.this.f6003a.b(1);
                        AnonymousClass2.this.f6005c.refresh(syncQueueItemA.arg1);
                    } else if (i2 == 2) {
                        AnonymousClass2.this.f6003a.b(2);
                        AnonymousClass2.this.f6003a.b(3);
                        AnonymousClass2.this.f6005c.updateRange(syncQueueItemA.arg1, syncQueueItemA.arg2, syncQueueItemA.arg3, syncQueueItemA.arg4, syncQueueItemA.arg5);
                    } else if (i2 == 3) {
                        AnonymousClass2.this.f6005c.loadTile(syncQueueItemA.arg1, syncQueueItemA.arg2);
                    } else if (i2 != 4) {
                        Log.e("ThreadUtil", "Unsupported message, what=" + syncQueueItemA.what);
                    } else {
                        AnonymousClass2.this.f6005c.recycleTile((TileList.Tile) syncQueueItemA.data);
                    }
                }
            }
        };

        AnonymousClass2(ThreadUtil.BackgroundCallback backgroundCallback) {
            this.f6005c = backgroundCallback;
        }

        private void maybeExecuteBackgroundRunnable() {
            if (this.f6004b.compareAndSet(false, true)) {
                this.mExecutor.execute(this.mBackgroundRunnable);
            }
        }

        private void sendMessage(SyncQueueItem syncQueueItem) {
            this.f6003a.c(syncQueueItem);
            maybeExecuteBackgroundRunnable();
        }

        private void sendMessageAtFrontOfQueue(SyncQueueItem syncQueueItem) {
            this.f6003a.d(syncQueueItem);
            maybeExecuteBackgroundRunnable();
        }

        @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
        public void loadTile(int i2, int i3) {
            sendMessage(SyncQueueItem.a(3, i2, i3));
        }

        @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
        public void recycleTile(TileList.Tile<T> tile) {
            sendMessage(SyncQueueItem.c(4, 0, tile));
        }

        @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
        public void refresh(int i2) {
            sendMessageAtFrontOfQueue(SyncQueueItem.c(1, i2, null));
        }

        @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
        public void updateRange(int i2, int i3, int i4, int i5, int i6) {
            sendMessageAtFrontOfQueue(SyncQueueItem.b(2, i2, i3, i4, i5, i6, null));
        }
    }

    static class MessageQueue {
        private SyncQueueItem mRoot;

        MessageQueue() {
        }

        synchronized SyncQueueItem a() {
            SyncQueueItem syncQueueItem = this.mRoot;
            if (syncQueueItem == null) {
                return null;
            }
            this.mRoot = syncQueueItem.f6008a;
            return syncQueueItem;
        }

        synchronized void b(int i2) {
            SyncQueueItem syncQueueItem;
            while (true) {
                try {
                    syncQueueItem = this.mRoot;
                    if (syncQueueItem == null || syncQueueItem.what != i2) {
                        break;
                    }
                    this.mRoot = syncQueueItem.f6008a;
                    syncQueueItem.d();
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (syncQueueItem != null) {
                SyncQueueItem syncQueueItem2 = syncQueueItem.f6008a;
                while (syncQueueItem2 != null) {
                    SyncQueueItem syncQueueItem3 = syncQueueItem2.f6008a;
                    if (syncQueueItem2.what == i2) {
                        syncQueueItem.f6008a = syncQueueItem3;
                        syncQueueItem2.d();
                    } else {
                        syncQueueItem = syncQueueItem2;
                    }
                    syncQueueItem2 = syncQueueItem3;
                }
            }
        }

        synchronized void c(SyncQueueItem syncQueueItem) {
            SyncQueueItem syncQueueItem2 = this.mRoot;
            if (syncQueueItem2 == null) {
                this.mRoot = syncQueueItem;
                return;
            }
            while (true) {
                SyncQueueItem syncQueueItem3 = syncQueueItem2.f6008a;
                if (syncQueueItem3 == null) {
                    syncQueueItem2.f6008a = syncQueueItem;
                    return;
                }
                syncQueueItem2 = syncQueueItem3;
            }
        }

        synchronized void d(SyncQueueItem syncQueueItem) {
            syncQueueItem.f6008a = this.mRoot;
            this.mRoot = syncQueueItem;
        }
    }

    static class SyncQueueItem {
        private static SyncQueueItem sPool;
        private static final Object sPoolLock = new Object();

        /* renamed from: a, reason: collision with root package name */
        SyncQueueItem f6008a;
        public int arg1;
        public int arg2;
        public int arg3;
        public int arg4;
        public int arg5;
        public Object data;
        public int what;

        SyncQueueItem() {
        }

        static SyncQueueItem a(int i2, int i3, int i4) {
            return b(i2, i3, i4, 0, 0, 0, null);
        }

        static SyncQueueItem b(int i2, int i3, int i4, int i5, int i6, int i7, Object obj) {
            SyncQueueItem syncQueueItem;
            synchronized (sPoolLock) {
                try {
                    syncQueueItem = sPool;
                    if (syncQueueItem == null) {
                        syncQueueItem = new SyncQueueItem();
                    } else {
                        sPool = syncQueueItem.f6008a;
                        syncQueueItem.f6008a = null;
                    }
                    syncQueueItem.what = i2;
                    syncQueueItem.arg1 = i3;
                    syncQueueItem.arg2 = i4;
                    syncQueueItem.arg3 = i5;
                    syncQueueItem.arg4 = i6;
                    syncQueueItem.arg5 = i7;
                    syncQueueItem.data = obj;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return syncQueueItem;
        }

        static SyncQueueItem c(int i2, int i3, Object obj) {
            return b(i2, i3, 0, 0, 0, 0, obj);
        }

        void d() {
            this.f6008a = null;
            this.arg5 = 0;
            this.arg4 = 0;
            this.arg3 = 0;
            this.arg2 = 0;
            this.arg1 = 0;
            this.what = 0;
            this.data = null;
            synchronized (sPoolLock) {
                try {
                    SyncQueueItem syncQueueItem = sPool;
                    if (syncQueueItem != null) {
                        this.f6008a = syncQueueItem;
                    }
                    sPool = this;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    MessageThreadUtil() {
    }

    @Override // androidx.recyclerview.widget.ThreadUtil
    public ThreadUtil.BackgroundCallback<T> getBackgroundProxy(ThreadUtil.BackgroundCallback<T> backgroundCallback) {
        return new AnonymousClass2(backgroundCallback);
    }

    @Override // androidx.recyclerview.widget.ThreadUtil
    public ThreadUtil.MainThreadCallback<T> getMainThreadProxy(ThreadUtil.MainThreadCallback<T> mainThreadCallback) {
        return new AnonymousClass1(mainThreadCallback);
    }
}
