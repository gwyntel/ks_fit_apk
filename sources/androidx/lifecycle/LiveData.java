package androidx.lifecycle;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.lifecycle.Lifecycle;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class LiveData<T> {

    /* renamed from: d, reason: collision with root package name */
    static final Object f4565d = new Object();

    /* renamed from: a, reason: collision with root package name */
    final Object f4566a;

    /* renamed from: b, reason: collision with root package name */
    int f4567b;

    /* renamed from: c, reason: collision with root package name */
    volatile Object f4568c;
    private boolean mChangingActiveState;
    private volatile Object mData;
    private boolean mDispatchInvalidated;
    private boolean mDispatchingValue;
    private SafeIterableMap<Observer<? super T>, LiveData<T>.ObserverWrapper> mObservers;
    private final Runnable mPostValueRunnable;
    private int mVersion;

    private class AlwaysActiveObserver extends LiveData<T>.ObserverWrapper {
        AlwaysActiveObserver(Observer observer) {
            super(observer);
        }

        @Override // androidx.lifecycle.LiveData.ObserverWrapper
        boolean d() {
            return true;
        }
    }

    class LifecycleBoundObserver extends LiveData<T>.ObserverWrapper implements LifecycleEventObserver {

        /* renamed from: e, reason: collision with root package name */
        final LifecycleOwner f4571e;

        LifecycleBoundObserver(LifecycleOwner lifecycleOwner, Observer observer) {
            super(observer);
            this.f4571e = lifecycleOwner;
        }

        @Override // androidx.lifecycle.LiveData.ObserverWrapper
        void b() {
            this.f4571e.getLifecycle().removeObserver(this);
        }

        @Override // androidx.lifecycle.LiveData.ObserverWrapper
        boolean c(LifecycleOwner lifecycleOwner) {
            return this.f4571e == lifecycleOwner;
        }

        @Override // androidx.lifecycle.LiveData.ObserverWrapper
        boolean d() {
            return this.f4571e.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
        }

        @Override // androidx.lifecycle.LifecycleEventObserver
        public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
            Lifecycle.State currentState = this.f4571e.getLifecycle().getCurrentState();
            if (currentState == Lifecycle.State.DESTROYED) {
                LiveData.this.removeObserver(this.f4573a);
                return;
            }
            Lifecycle.State state = null;
            while (state != currentState) {
                a(d());
                state = currentState;
                currentState = this.f4571e.getLifecycle().getCurrentState();
            }
        }
    }

    private abstract class ObserverWrapper {

        /* renamed from: a, reason: collision with root package name */
        final Observer f4573a;

        /* renamed from: b, reason: collision with root package name */
        boolean f4574b;

        /* renamed from: c, reason: collision with root package name */
        int f4575c = -1;

        ObserverWrapper(Observer observer) {
            this.f4573a = observer;
        }

        void a(boolean z2) {
            if (z2 == this.f4574b) {
                return;
            }
            this.f4574b = z2;
            LiveData.this.b(z2 ? 1 : -1);
            if (this.f4574b) {
                LiveData.this.c(this);
            }
        }

        void b() {
        }

        boolean c(LifecycleOwner lifecycleOwner) {
            return false;
        }

        abstract boolean d();
    }

    public LiveData(T t2) {
        this.f4566a = new Object();
        this.mObservers = new SafeIterableMap<>();
        this.f4567b = 0;
        this.f4568c = f4565d;
        this.mPostValueRunnable = new Runnable() { // from class: androidx.lifecycle.LiveData.1
            @Override // java.lang.Runnable
            public void run() {
                Object obj;
                synchronized (LiveData.this.f4566a) {
                    obj = LiveData.this.f4568c;
                    LiveData.this.f4568c = LiveData.f4565d;
                }
                LiveData.this.setValue(obj);
            }
        };
        this.mData = t2;
        this.mVersion = 0;
    }

    static void a(String str) {
        if (ArchTaskExecutor.getInstance().isMainThread()) {
            return;
        }
        throw new IllegalStateException("Cannot invoke " + str + " on a background thread");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void considerNotify(LiveData<T>.ObserverWrapper observerWrapper) {
        if (observerWrapper.f4574b) {
            if (!observerWrapper.d()) {
                observerWrapper.a(false);
                return;
            }
            int i2 = observerWrapper.f4575c;
            int i3 = this.mVersion;
            if (i2 >= i3) {
                return;
            }
            observerWrapper.f4575c = i3;
            observerWrapper.f4573a.onChanged(this.mData);
        }
    }

    void b(int i2) {
        int i3 = this.f4567b;
        this.f4567b = i2 + i3;
        if (this.mChangingActiveState) {
            return;
        }
        this.mChangingActiveState = true;
        while (true) {
            try {
                int i4 = this.f4567b;
                if (i3 == i4) {
                    this.mChangingActiveState = false;
                    return;
                }
                boolean z2 = i3 == 0 && i4 > 0;
                boolean z3 = i3 > 0 && i4 == 0;
                if (z2) {
                    d();
                } else if (z3) {
                    e();
                }
                i3 = i4;
            } catch (Throwable th) {
                this.mChangingActiveState = false;
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    void c(ObserverWrapper observerWrapper) {
        if (this.mDispatchingValue) {
            this.mDispatchInvalidated = true;
            return;
        }
        this.mDispatchingValue = true;
        do {
            this.mDispatchInvalidated = false;
            if (observerWrapper != null) {
                considerNotify(observerWrapper);
                observerWrapper = null;
            } else {
                SafeIterableMap<Observer<? super T>, LiveData<T>.ObserverWrapper>.IteratorWithAdditions iteratorWithAdditions = this.mObservers.iteratorWithAdditions();
                while (iteratorWithAdditions.hasNext()) {
                    considerNotify((ObserverWrapper) iteratorWithAdditions.next().getValue());
                    if (this.mDispatchInvalidated) {
                        break;
                    }
                }
            }
        } while (this.mDispatchInvalidated);
        this.mDispatchingValue = false;
    }

    protected void d() {
    }

    protected void e() {
    }

    @Nullable
    public T getValue() {
        T t2 = (T) this.mData;
        if (t2 != f4565d) {
            return t2;
        }
        return null;
    }

    public boolean hasActiveObservers() {
        return this.f4567b > 0;
    }

    public boolean hasObservers() {
        return this.mObservers.size() > 0;
    }

    public boolean isInitialized() {
        return this.mData != f4565d;
    }

    @MainThread
    public void observe(@NonNull LifecycleOwner lifecycleOwner, @NonNull Observer<? super T> observer) {
        a("observe");
        if (lifecycleOwner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
            return;
        }
        LifecycleBoundObserver lifecycleBoundObserver = new LifecycleBoundObserver(lifecycleOwner, observer);
        LiveData<T>.ObserverWrapper observerWrapperPutIfAbsent = this.mObservers.putIfAbsent(observer, lifecycleBoundObserver);
        if (observerWrapperPutIfAbsent != null && !observerWrapperPutIfAbsent.c(lifecycleOwner)) {
            throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
        }
        if (observerWrapperPutIfAbsent != null) {
            return;
        }
        lifecycleOwner.getLifecycle().addObserver(lifecycleBoundObserver);
    }

    @MainThread
    public void observeForever(@NonNull Observer<? super T> observer) {
        a("observeForever");
        AlwaysActiveObserver alwaysActiveObserver = new AlwaysActiveObserver(observer);
        LiveData<T>.ObserverWrapper observerWrapperPutIfAbsent = this.mObservers.putIfAbsent(observer, alwaysActiveObserver);
        if (observerWrapperPutIfAbsent instanceof LifecycleBoundObserver) {
            throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
        }
        if (observerWrapperPutIfAbsent != null) {
            return;
        }
        alwaysActiveObserver.a(true);
    }

    protected void postValue(Object obj) {
        boolean z2;
        synchronized (this.f4566a) {
            z2 = this.f4568c == f4565d;
            this.f4568c = obj;
        }
        if (z2) {
            ArchTaskExecutor.getInstance().postToMainThread(this.mPostValueRunnable);
        }
    }

    @MainThread
    public void removeObserver(@NonNull Observer<? super T> observer) {
        a("removeObserver");
        LiveData<T>.ObserverWrapper observerWrapperRemove = this.mObservers.remove(observer);
        if (observerWrapperRemove == null) {
            return;
        }
        observerWrapperRemove.b();
        observerWrapperRemove.a(false);
    }

    @MainThread
    public void removeObservers(@NonNull LifecycleOwner lifecycleOwner) {
        a("removeObservers");
        Iterator<Map.Entry<Observer<? super T>, LiveData<T>.ObserverWrapper>> it = this.mObservers.iterator();
        while (it.hasNext()) {
            Map.Entry<Observer<? super T>, LiveData<T>.ObserverWrapper> next = it.next();
            if (next.getValue().c(lifecycleOwner)) {
                removeObserver(next.getKey());
            }
        }
    }

    protected void setValue(Object obj) {
        a("setValue");
        this.mVersion++;
        this.mData = obj;
        c(null);
    }

    public LiveData() {
        this.f4566a = new Object();
        this.mObservers = new SafeIterableMap<>();
        this.f4567b = 0;
        Object obj = f4565d;
        this.f4568c = obj;
        this.mPostValueRunnable = new Runnable() { // from class: androidx.lifecycle.LiveData.1
            @Override // java.lang.Runnable
            public void run() {
                Object obj2;
                synchronized (LiveData.this.f4566a) {
                    obj2 = LiveData.this.f4568c;
                    LiveData.this.f4568c = LiveData.f4565d;
                }
                LiveData.this.setValue(obj2);
            }
        };
        this.mData = obj;
        this.mVersion = -1;
    }
}
