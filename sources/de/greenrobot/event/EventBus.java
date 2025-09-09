package de.greenrobot.event;

import android.os.Looper;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes4.dex */
public class EventBus {
    private static /* synthetic */ int[] $SWITCH_TABLE$de$greenrobot$event$ThreadMode;
    private static volatile EventBus defaultInstance;
    private boolean subscribed;

    /* renamed from: a, reason: collision with root package name */
    static ExecutorService f24971a = Executors.newCachedThreadPool();
    public static String TAG = "Event";
    private static final Map<Class<?>, List<Class<?>>> eventTypesCache = new HashMap();
    private final ThreadLocal<List<Object>> currentThreadEventQueue = new ThreadLocal<List<Object>>() { // from class: de.greenrobot.event.EventBus.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public List initialValue() {
            return new ArrayList();
        }
    };
    private final ThreadLocal<BooleanWrapper> currentThreadIsPosting = new ThreadLocal<BooleanWrapper>() { // from class: de.greenrobot.event.EventBus.2
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public BooleanWrapper initialValue() {
            return new BooleanWrapper();
        }
    };
    private String defaultMethodName = "onEvent";
    private final Map<Class<?>, CopyOnWriteArrayList<Subscription>> subscriptionsByEventType = new HashMap();
    private final Map<Object, List<Class<?>>> typesBySubscriber = new HashMap();
    private final Map<Class<?>, Object> stickyEvents = new ConcurrentHashMap();
    private final HandlerPoster mainThreadPoster = new HandlerPoster(this, Looper.getMainLooper(), 10);
    private final BackgroundPoster backgroundPoster = new BackgroundPoster(this);
    private final AsyncPoster asyncPoster = new AsyncPoster(this);
    private final SubscriberMethodFinder subscriberMethodFinder = new SubscriberMethodFinder();
    private boolean logSubscriberExceptions = true;

    static final class BooleanWrapper {

        /* renamed from: a, reason: collision with root package name */
        boolean f24974a;

        BooleanWrapper() {
        }
    }

    interface PostCallback {
        void onPostCompleted(List<SubscriberExceptionEvent> list);
    }

    static /* synthetic */ int[] a() {
        int[] iArr = $SWITCH_TABLE$de$greenrobot$event$ThreadMode;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[ThreadMode.valuesCustom().length];
        try {
            iArr2[ThreadMode.Async.ordinal()] = 4;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[ThreadMode.BackgroundThread.ordinal()] = 3;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[ThreadMode.MainThread.ordinal()] = 2;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr2[ThreadMode.PostThread.ordinal()] = 1;
        } catch (NoSuchFieldError unused4) {
        }
        $SWITCH_TABLE$de$greenrobot$event$ThreadMode = iArr2;
        return iArr2;
    }

    static void b(List list, Class[] clsArr) {
        for (Class cls : clsArr) {
            if (!list.contains(cls)) {
                list.add(cls);
                b(list, cls.getInterfaces());
            }
        }
    }

    public static void clearCaches() {
        SubscriberMethodFinder.a();
        eventTypesCache.clear();
    }

    public static void clearSkipMethodNameVerifications() {
        SubscriberMethodFinder.clearSkipMethodNameVerifications();
    }

    private List<Class<?>> findEventTypes(Class<?> cls) {
        List<Class<?>> arrayList;
        Map<Class<?>, List<Class<?>>> map = eventTypesCache;
        synchronized (map) {
            try {
                arrayList = map.get(cls);
                if (arrayList == null) {
                    arrayList = new ArrayList<>();
                    for (Class<?> superclass = cls; superclass != null; superclass = superclass.getSuperclass()) {
                        arrayList.add(superclass);
                        b(arrayList, superclass.getInterfaces());
                    }
                    eventTypesCache.put(cls, arrayList);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public static EventBus getDefault() {
        if (defaultInstance == null) {
            synchronized (EventBus.class) {
                try {
                    if (defaultInstance == null) {
                        defaultInstance = new EventBus();
                    }
                } finally {
                }
            }
        }
        return defaultInstance;
    }

    private void postSingleEvent(Object obj, boolean z2) throws IllegalAccessException, Error, IllegalArgumentException, InvocationTargetException {
        CopyOnWriteArrayList<Subscription> copyOnWriteArrayList;
        Class<?> cls = obj.getClass();
        List<Class<?>> listFindEventTypes = findEventTypes(cls);
        int size = listFindEventTypes.size();
        boolean z3 = false;
        for (int i2 = 0; i2 < size; i2++) {
            Class<?> cls2 = listFindEventTypes.get(i2);
            synchronized (this) {
                copyOnWriteArrayList = this.subscriptionsByEventType.get(cls2);
            }
            if (copyOnWriteArrayList != null) {
                Iterator<Subscription> it = copyOnWriteArrayList.iterator();
                while (it.hasNext()) {
                    postToSubscription(it.next(), obj, z2);
                }
                z3 = true;
            }
        }
        if (z3) {
            return;
        }
        Log.d(TAG, "No subscripers registered for event " + cls);
        if (cls == NoSubscriberEvent.class || cls == SubscriberExceptionEvent.class) {
            return;
        }
        post(new NoSubscriberEvent(this, obj));
    }

    private void postToSubscription(Subscription subscription, Object obj, boolean z2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int i2 = a()[subscription.f24983b.f24979b.ordinal()];
        if (i2 == 1) {
            d(subscription, obj);
            return;
        }
        if (i2 == 2) {
            if (z2) {
                d(subscription, obj);
                return;
            } else {
                this.mainThreadPoster.a(subscription, obj);
                return;
            }
        }
        if (i2 == 3) {
            if (z2) {
                this.backgroundPoster.enqueue(subscription, obj);
                return;
            } else {
                d(subscription, obj);
                return;
            }
        }
        if (i2 == 4) {
            this.asyncPoster.enqueue(subscription, obj);
        } else {
            throw new IllegalStateException("Unknown thread mode: " + subscription.f24983b.f24979b);
        }
    }

    public static void skipMethodNameVerificationFor(Class<?> cls) {
        SubscriberMethodFinder.c(cls);
    }

    private void subscribe(Object obj, SubscriberMethod subscriberMethod, boolean z2) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Object obj2;
        this.subscribed = true;
        Class<?> cls = subscriberMethod.f24980c;
        CopyOnWriteArrayList<Subscription> copyOnWriteArrayList = this.subscriptionsByEventType.get(cls);
        Subscription subscription = new Subscription(obj, subscriberMethod);
        if (copyOnWriteArrayList == null) {
            copyOnWriteArrayList = new CopyOnWriteArrayList<>();
            this.subscriptionsByEventType.put(cls, copyOnWriteArrayList);
        } else {
            Iterator<Subscription> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                if (it.next().equals(subscription)) {
                    throw new EventBusException("Subscriber " + obj.getClass() + " already registered to event " + cls);
                }
            }
        }
        subscriberMethod.f24978a.setAccessible(true);
        copyOnWriteArrayList.add(subscription);
        List<Class<?>> arrayList = this.typesBySubscriber.get(obj);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.typesBySubscriber.put(obj, arrayList);
        }
        arrayList.add(cls);
        if (z2) {
            synchronized (this.stickyEvents) {
                obj2 = this.stickyEvents.get(cls);
            }
            if (obj2 != null) {
                postToSubscription(subscription, obj2, Looper.getMainLooper() == Looper.myLooper());
            }
        }
    }

    private void unubscribeByEventType(Object obj, Class<?> cls) {
        CopyOnWriteArrayList<Subscription> copyOnWriteArrayList = this.subscriptionsByEventType.get(cls);
        if (copyOnWriteArrayList != null) {
            int size = copyOnWriteArrayList.size();
            int i2 = 0;
            while (i2 < size) {
                if (copyOnWriteArrayList.get(i2).f24982a == obj) {
                    copyOnWriteArrayList.remove(i2);
                    i2--;
                    size--;
                }
                i2++;
            }
        }
    }

    void c(PendingPost pendingPost) {
        Object obj = pendingPost.f24975a;
        Subscription subscription = pendingPost.f24976b;
        PendingPost.b(pendingPost);
        d(subscription, obj);
    }

    public void configureLogSubscriberExceptions(boolean z2) {
        if (this.subscribed) {
            throw new EventBusException("This method must be called before any registration");
        }
        this.logSubscriberExceptions = z2;
    }

    void d(Subscription subscription, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            subscription.f24983b.f24978a.invoke(subscription.f24982a, obj);
        } catch (IllegalAccessException e2) {
            throw new IllegalStateException("Unexpected exception", e2);
        } catch (InvocationTargetException e3) {
            Throwable cause = e3.getCause();
            if (!(obj instanceof SubscriberExceptionEvent)) {
                if (this.logSubscriberExceptions) {
                    Log.e(TAG, "Could not dispatch event: " + obj.getClass() + " to subscribing class " + subscription.f24982a.getClass(), cause);
                }
                post(new SubscriberExceptionEvent(this, cause, obj, subscription.f24982a));
                return;
            }
            Log.e(TAG, "SubscriberExceptionEvent subscriber " + subscription.f24982a.getClass() + " threw an exception", cause);
            SubscriberExceptionEvent subscriberExceptionEvent = (SubscriberExceptionEvent) obj;
            Log.e(TAG, "Initial event " + subscriberExceptionEvent.causingEvent + " caused exception in " + subscriberExceptionEvent.causingSubscriber, subscriberExceptionEvent.throwable);
        }
    }

    public Object getStickyEvent(Class<?> cls) {
        Object obj;
        synchronized (this.stickyEvents) {
            obj = this.stickyEvents.get(cls);
        }
        return obj;
    }

    public void post(Object obj) {
        List<Object> list = this.currentThreadEventQueue.get();
        list.add(obj);
        BooleanWrapper booleanWrapper = this.currentThreadIsPosting.get();
        if (booleanWrapper.f24974a) {
            return;
        }
        boolean z2 = Looper.getMainLooper() == Looper.myLooper();
        booleanWrapper.f24974a = true;
        while (!list.isEmpty()) {
            try {
                postSingleEvent(list.remove(0), z2);
            } finally {
                booleanWrapper.f24974a = false;
            }
        }
    }

    public void postSticky(Object obj) {
        synchronized (this.stickyEvents) {
            this.stickyEvents.put(obj.getClass(), obj);
        }
        post(obj);
    }

    public void register(Object obj) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        register(obj, this.defaultMethodName, false);
    }

    public void registerSticky(Object obj) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        register(obj, this.defaultMethodName, true);
    }

    public Object removeStickyEvent(Class<?> cls) {
        Object objRemove;
        synchronized (this.stickyEvents) {
            objRemove = this.stickyEvents.remove(cls);
        }
        return objRemove;
    }

    public synchronized void unregister(Object obj, Class<?>... clsArr) {
        try {
            if (clsArr.length == 0) {
                throw new IllegalArgumentException("Provide at least one event class");
            }
            List<Class<?>> list = this.typesBySubscriber.get(obj);
            if (list != null) {
                for (Class<?> cls : clsArr) {
                    unubscribeByEventType(obj, cls);
                    list.remove(cls);
                }
                if (list.isEmpty()) {
                    this.typesBySubscriber.remove(obj);
                }
            } else {
                Log.w(TAG, "Subscriber to unregister was not registered before: " + obj.getClass());
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public void register(Object obj, String str) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        register(obj, str, false);
    }

    public void registerSticky(Object obj, String str) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        register(obj, str, true);
    }

    private void register(Object obj, String str, boolean z2) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Iterator it = this.subscriberMethodFinder.b(obj.getClass(), str).iterator();
        while (it.hasNext()) {
            subscribe(obj, (SubscriberMethod) it.next(), z2);
        }
    }

    public void registerSticky(Object obj, Class<?> cls, Class<?>... clsArr) {
        register(obj, this.defaultMethodName, true, cls, clsArr);
    }

    public synchronized void registerSticky(Object obj, String str, Class<?> cls, Class<?>... clsArr) {
        register(obj, str, true, cls, clsArr);
    }

    public boolean removeStickyEvent(Object obj) {
        synchronized (this.stickyEvents) {
            try {
                Class<?> cls = obj.getClass();
                if (!obj.equals(this.stickyEvents.get(cls))) {
                    return false;
                }
                this.stickyEvents.remove(cls);
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void register(Object obj, Class<?> cls, Class<?>... clsArr) {
        register(obj, this.defaultMethodName, false, cls, clsArr);
    }

    public synchronized void register(Object obj, String str, Class<?> cls, Class<?>... clsArr) {
        register(obj, str, false, cls, clsArr);
    }

    private synchronized void register(Object obj, String str, boolean z2, Class<?> cls, Class<?>... clsArr) {
        try {
            for (SubscriberMethod subscriberMethod : this.subscriberMethodFinder.b(obj.getClass(), str)) {
                if (cls == subscriberMethod.f24980c) {
                    subscribe(obj, subscriberMethod, z2);
                } else if (clsArr != null) {
                    int length = clsArr.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 < length) {
                            if (clsArr[i2] == subscriberMethod.f24980c) {
                                subscribe(obj, subscriberMethod, z2);
                                break;
                            }
                            i2++;
                        }
                    }
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void unregister(Object obj) {
        try {
            List<Class<?>> list = this.typesBySubscriber.get(obj);
            if (list != null) {
                Iterator<Class<?>> it = list.iterator();
                while (it.hasNext()) {
                    unubscribeByEventType(obj, it.next());
                }
                this.typesBySubscriber.remove(obj);
            } else {
                Log.w(TAG, "Subscriber to unregister was not registered before: " + obj.getClass());
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
