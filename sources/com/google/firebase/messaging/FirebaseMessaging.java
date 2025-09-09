package com.google.firebase.messaging;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import com.google.android.datatransport.TransportFactory;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.concurrent.NamedThreadFactory;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.DataCollectionDefaultChange;
import com.google.firebase.FirebaseApp;
import com.google.firebase.events.Event;
import com.google.firebase.events.EventHandler;
import com.google.firebase.events.Subscriber;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.google.firebase.inject.Provider;
import com.google.firebase.installations.FirebaseInstallationsApi;
import com.google.firebase.messaging.RequestDeduplicator;
import com.google.firebase.messaging.Store;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class FirebaseMessaging {
    private static final String EXTRA_DUMMY_P_INTENT = "app";

    @Deprecated
    public static final String INSTANCE_ID_SCOPE = "FCM";
    private static final long MAX_DELAY_SEC = TimeUnit.HOURS.toSeconds(8);
    private static final long MIN_DELAY_SEC = 30;
    private static final String SEND_INTENT_ACTION = "com.google.android.gcm.intent.SEND";
    private static final String SUBTYPE_DEFAULT = "";

    /* renamed from: a, reason: collision with root package name */
    static TransportFactory f15100a;

    /* renamed from: b, reason: collision with root package name */
    static ScheduledExecutorService f15101b;

    @GuardedBy("FirebaseMessaging.class")
    private static Store store;
    private final AutoInit autoInit;
    private final Context context;
    private final Executor fileExecutor;
    private final FirebaseApp firebaseApp;
    private final FirebaseInstallationsApi fis;
    private final GmsRpc gmsRpc;

    @Nullable
    private final FirebaseInstanceIdInternal iid;
    private final Executor initExecutor;
    private final Application.ActivityLifecycleCallbacks lifecycleCallbacks;
    private final Metadata metadata;
    private final RequestDeduplicator requestDeduplicator;

    @GuardedBy("this")
    private boolean syncScheduledOrRunning;
    private final Executor taskExecutor;
    private final Task<TopicsSubscriber> topicsSubscriberTask;

    /* JADX INFO: Access modifiers changed from: private */
    class AutoInit {
        private static final String AUTO_INIT_PREF = "auto_init";
        private static final String FCM_PREFERENCES = "com.google.firebase.messaging";
        private static final String MANIFEST_METADATA_AUTO_INIT_ENABLED = "firebase_messaging_auto_init_enabled";

        @Nullable
        @GuardedBy("this")
        private Boolean autoInitEnabled;

        @Nullable
        @GuardedBy("this")
        private EventHandler<DataCollectionDefaultChange> dataCollectionDefaultChangeEventHandler;

        @GuardedBy("this")
        private boolean initialized;
        private final Subscriber subscriber;

        AutoInit(Subscriber subscriber) {
            this.subscriber = subscriber;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$initialize$0(Event event) {
            if (c()) {
                FirebaseMessaging.this.startSyncIfNecessary();
            }
        }

        @Nullable
        private Boolean readEnabled() {
            ApplicationInfo applicationInfo;
            Bundle bundle;
            Context applicationContext = FirebaseMessaging.this.firebaseApp.getApplicationContext();
            SharedPreferences sharedPreferences = applicationContext.getSharedPreferences("com.google.firebase.messaging", 0);
            if (sharedPreferences.contains(AUTO_INIT_PREF)) {
                return Boolean.valueOf(sharedPreferences.getBoolean(AUTO_INIT_PREF, false));
            }
            try {
                PackageManager packageManager = applicationContext.getPackageManager();
                if (packageManager == null || (applicationInfo = packageManager.getApplicationInfo(applicationContext.getPackageName(), 128)) == null || (bundle = applicationInfo.metaData) == null || !bundle.containsKey(MANIFEST_METADATA_AUTO_INIT_ENABLED)) {
                    return null;
                }
                return Boolean.valueOf(applicationInfo.metaData.getBoolean(MANIFEST_METADATA_AUTO_INIT_ENABLED));
            } catch (PackageManager.NameNotFoundException unused) {
                return null;
            }
        }

        synchronized void b() {
            try {
                if (this.initialized) {
                    return;
                }
                Boolean enabled = readEnabled();
                this.autoInitEnabled = enabled;
                if (enabled == null) {
                    EventHandler<DataCollectionDefaultChange> eventHandler = new EventHandler() { // from class: com.google.firebase.messaging.u
                        @Override // com.google.firebase.events.EventHandler
                        public final void handle(Event event) {
                            this.f15160a.lambda$initialize$0(event);
                        }
                    };
                    this.dataCollectionDefaultChangeEventHandler = eventHandler;
                    this.subscriber.subscribe(DataCollectionDefaultChange.class, eventHandler);
                }
                this.initialized = true;
            } catch (Throwable th) {
                throw th;
            }
        }

        synchronized boolean c() {
            Boolean bool;
            try {
                b();
                bool = this.autoInitEnabled;
            } catch (Throwable th) {
                throw th;
            }
            return bool != null ? bool.booleanValue() : FirebaseMessaging.this.firebaseApp.isDataCollectionDefaultEnabled();
        }

        synchronized void d(boolean z2) {
            try {
                b();
                EventHandler<DataCollectionDefaultChange> eventHandler = this.dataCollectionDefaultChangeEventHandler;
                if (eventHandler != null) {
                    this.subscriber.unsubscribe(DataCollectionDefaultChange.class, eventHandler);
                    this.dataCollectionDefaultChangeEventHandler = null;
                }
                SharedPreferences.Editor editorEdit = FirebaseMessaging.this.firebaseApp.getApplicationContext().getSharedPreferences("com.google.firebase.messaging", 0).edit();
                editorEdit.putBoolean(AUTO_INIT_PREF, z2);
                editorEdit.apply();
                if (z2) {
                    FirebaseMessaging.this.startSyncIfNecessary();
                }
                this.autoInitEnabled = Boolean.valueOf(z2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    FirebaseMessaging(FirebaseApp firebaseApp, FirebaseInstanceIdInternal firebaseInstanceIdInternal, Provider provider, Provider provider2, FirebaseInstallationsApi firebaseInstallationsApi, TransportFactory transportFactory, Subscriber subscriber) {
        this(firebaseApp, firebaseInstanceIdInternal, provider, provider2, firebaseInstallationsApi, transportFactory, subscriber, new Metadata(firebaseApp.getApplicationContext()));
    }

    @NonNull
    public static synchronized FirebaseMessaging getInstance() {
        return getInstance(FirebaseApp.getInstance());
    }

    @NonNull
    private static synchronized Store getStore(Context context) {
        try {
            if (store == null) {
                store = new Store(context);
            }
        } catch (Throwable th) {
            throw th;
        }
        return store;
    }

    private String getSubtype() {
        return FirebaseApp.DEFAULT_APP_NAME.equals(this.firebaseApp.getName()) ? "" : this.firebaseApp.getPersistenceKey();
    }

    @Nullable
    public static TransportFactory getTransportFactory() {
        return f15100a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: invokeOnTokenRefresh, reason: merged with bridge method [inline-methods] */
    public void lambda$new$0(String str) {
        if (FirebaseApp.DEFAULT_APP_NAME.equals(this.firebaseApp.getName())) {
            if (Log.isLoggable(Constants.TAG, 3)) {
                Log.d(Constants.TAG, "Invoking onNewToken for app: " + this.firebaseApp.getName());
            }
            Intent intent = new Intent("com.google.firebase.messaging.NEW_TOKEN");
            intent.putExtra("token", str);
            new FcmBroadcastProcessor(this.context).process(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Task lambda$blockingGetToken$10(final String str, final Store.Token token) {
        return this.gmsRpc.c().onSuccessTask(this.fileExecutor, new SuccessContinuation() { // from class: com.google.firebase.messaging.j
            @Override // com.google.android.gms.tasks.SuccessContinuation
            public final Task then(Object obj) {
                return this.f15143a.lambda$blockingGetToken$9(str, token, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Task lambda$blockingGetToken$9(String str, Store.Token token, String str2) throws Exception {
        getStore(this.context).saveToken(getSubtype(), str, str2, this.metadata.a());
        if (token == null || !str2.equals(token.f15108a)) {
            lambda$new$0(str2);
        }
        return Tasks.forResult(str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteToken$5(TaskCompletionSource taskCompletionSource) {
        try {
            this.iid.deleteToken(Metadata.c(this.firebaseApp), "FCM");
            taskCompletionSource.setResult(null);
        } catch (Exception e2) {
            taskCompletionSource.setException(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteToken$6(TaskCompletionSource taskCompletionSource) {
        try {
            Tasks.await(this.gmsRpc.b());
            getStore(this.context).deleteToken(getSubtype(), Metadata.c(this.firebaseApp));
            taskCompletionSource.setResult(null);
        } catch (Exception e2) {
            taskCompletionSource.setException(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getToken$4(TaskCompletionSource taskCompletionSource) {
        try {
            taskCompletionSource.setResult(n());
        } catch (Exception e2) {
            taskCompletionSource.setException(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        if (isAutoInitEnabled()) {
            startSyncIfNecessary();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(TopicsSubscriber topicsSubscriber) {
        if (isAutoInitEnabled()) {
            topicsSubscriber.j();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3() {
        ProxyNotificationInitializer.b(this.context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Task lambda$subscribeToTopic$7(String str, TopicsSubscriber topicsSubscriber) throws Exception {
        return topicsSubscriber.k(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Task lambda$unsubscribeFromTopic$8(String str, TopicsSubscriber topicsSubscriber) throws Exception {
        return topicsSubscriber.n(str);
    }

    private synchronized void startSync() {
        if (!this.syncScheduledOrRunning) {
            t(0L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSyncIfNecessary() {
        FirebaseInstanceIdInternal firebaseInstanceIdInternal = this.iid;
        if (firebaseInstanceIdInternal != null) {
            firebaseInstanceIdInternal.getToken();
        } else if (u(q())) {
            startSync();
        }
    }

    @NonNull
    public Task<Void> deleteToken() {
        if (this.iid != null) {
            final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
            this.initExecutor.execute(new Runnable() { // from class: com.google.firebase.messaging.s
                @Override // java.lang.Runnable
                public final void run() {
                    this.f15156a.lambda$deleteToken$5(taskCompletionSource);
                }
            });
            return taskCompletionSource.getTask();
        }
        if (q() == null) {
            return Tasks.forResult(null);
        }
        final TaskCompletionSource taskCompletionSource2 = new TaskCompletionSource();
        FcmExecutors.d().execute(new Runnable() { // from class: com.google.firebase.messaging.t
            @Override // java.lang.Runnable
            public final void run() {
                this.f15158a.lambda$deleteToken$6(taskCompletionSource2);
            }
        });
        return taskCompletionSource2.getTask();
    }

    @NonNull
    public boolean deliveryMetricsExportToBigQueryEnabled() {
        return MessagingAnalytics.a();
    }

    @NonNull
    public Task<String> getToken() {
        FirebaseInstanceIdInternal firebaseInstanceIdInternal = this.iid;
        if (firebaseInstanceIdInternal != null) {
            return firebaseInstanceIdInternal.getTokenTask();
        }
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.initExecutor.execute(new Runnable() { // from class: com.google.firebase.messaging.o
            @Override // java.lang.Runnable
            public final void run() {
                this.f15150a.lambda$getToken$4(taskCompletionSource);
            }
        });
        return taskCompletionSource.getTask();
    }

    public boolean isAutoInitEnabled() {
        return this.autoInit.c();
    }

    public boolean isNotificationDelegationEnabled() {
        return ProxyNotificationInitializer.c(this.context);
    }

    String n() throws IOException {
        FirebaseInstanceIdInternal firebaseInstanceIdInternal = this.iid;
        if (firebaseInstanceIdInternal != null) {
            try {
                return (String) Tasks.await(firebaseInstanceIdInternal.getTokenTask());
            } catch (InterruptedException | ExecutionException e2) {
                throw new IOException(e2);
            }
        }
        final Store.Token tokenQ = q();
        if (!u(tokenQ)) {
            return tokenQ.f15108a;
        }
        final String strC = Metadata.c(this.firebaseApp);
        try {
            return (String) Tasks.await(this.requestDeduplicator.b(strC, new RequestDeduplicator.GetTokenRequest() { // from class: com.google.firebase.messaging.q
                @Override // com.google.firebase.messaging.RequestDeduplicator.GetTokenRequest
                public final Task start() {
                    return this.f15153a.lambda$blockingGetToken$10(strC, tokenQ);
                }
            }));
        } catch (InterruptedException | ExecutionException e3) {
            throw new IOException(e3);
        }
    }

    void o(Runnable runnable, long j2) {
        synchronized (FirebaseMessaging.class) {
            try {
                if (f15101b == null) {
                    f15101b = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("TAG"));
                }
                f15101b.schedule(runnable, j2, TimeUnit.SECONDS);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    Context p() {
        return this.context;
    }

    Store.Token q() {
        return getStore(this.context).getToken(getSubtype(), Metadata.c(this.firebaseApp));
    }

    boolean r() {
        return this.metadata.f();
    }

    synchronized void s(boolean z2) {
        this.syncScheduledOrRunning = z2;
    }

    @Deprecated
    public void send(@NonNull RemoteMessage remoteMessage) {
        if (TextUtils.isEmpty(remoteMessage.getTo())) {
            throw new IllegalArgumentException("Missing 'to'");
        }
        Intent intent = new Intent(SEND_INTENT_ACTION);
        Intent intent2 = new Intent();
        intent2.setPackage("com.google.example.invalidpackage");
        intent.putExtra("app", PendingIntent.getBroadcast(this.context, 0, intent2, AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL));
        intent.setPackage("com.google.android.gms");
        remoteMessage.a(intent);
        this.context.sendOrderedBroadcast(intent, "com.google.android.gtalkservice.permission.GTALK_SERVICE");
    }

    public void setAutoInitEnabled(boolean z2) {
        this.autoInit.d(z2);
    }

    public void setDeliveryMetricsExportToBigQuery(boolean z2) {
        MessagingAnalytics.s(z2);
    }

    @NonNull
    public Task<Void> setNotificationDelegationEnabled(boolean z2) {
        return ProxyNotificationInitializer.d(this.initExecutor, this.context, z2);
    }

    @NonNull
    @SuppressLint({"TaskMainThread"})
    public Task<Void> subscribeToTopic(@NonNull final String str) {
        return this.topicsSubscriberTask.onSuccessTask(new SuccessContinuation() { // from class: com.google.firebase.messaging.i
            @Override // com.google.android.gms.tasks.SuccessContinuation
            public final Task then(Object obj) {
                return FirebaseMessaging.lambda$subscribeToTopic$7(str, (TopicsSubscriber) obj);
            }
        });
    }

    synchronized void t(long j2) {
        o(new SyncTask(this, Math.min(Math.max(MIN_DELAY_SEC, 2 * j2), MAX_DELAY_SEC)), j2);
        this.syncScheduledOrRunning = true;
    }

    boolean u(Store.Token token) {
        return token == null || token.b(this.metadata.a());
    }

    @NonNull
    @SuppressLint({"TaskMainThread"})
    public Task<Void> unsubscribeFromTopic(@NonNull final String str) {
        return this.topicsSubscriberTask.onSuccessTask(new SuccessContinuation() { // from class: com.google.firebase.messaging.p
            @Override // com.google.android.gms.tasks.SuccessContinuation
            public final Task then(Object obj) {
                return FirebaseMessaging.lambda$unsubscribeFromTopic$8(str, (TopicsSubscriber) obj);
            }
        });
    }

    @NonNull
    @Keep
    static synchronized FirebaseMessaging getInstance(@NonNull FirebaseApp firebaseApp) {
        FirebaseMessaging firebaseMessaging;
        firebaseMessaging = (FirebaseMessaging) firebaseApp.get(FirebaseMessaging.class);
        Preconditions.checkNotNull(firebaseMessaging, "Firebase Messaging component is not present");
        return firebaseMessaging;
    }

    FirebaseMessaging(FirebaseApp firebaseApp, FirebaseInstanceIdInternal firebaseInstanceIdInternal, Provider provider, Provider provider2, FirebaseInstallationsApi firebaseInstallationsApi, TransportFactory transportFactory, Subscriber subscriber, Metadata metadata) {
        this(firebaseApp, firebaseInstanceIdInternal, firebaseInstallationsApi, transportFactory, subscriber, metadata, new GmsRpc(firebaseApp, metadata, provider, provider2, firebaseInstallationsApi), FcmExecutors.e(), FcmExecutors.b(), FcmExecutors.a());
    }

    FirebaseMessaging(FirebaseApp firebaseApp, FirebaseInstanceIdInternal firebaseInstanceIdInternal, FirebaseInstallationsApi firebaseInstallationsApi, TransportFactory transportFactory, Subscriber subscriber, Metadata metadata, GmsRpc gmsRpc, Executor executor, Executor executor2, Executor executor3) {
        this.syncScheduledOrRunning = false;
        f15100a = transportFactory;
        this.firebaseApp = firebaseApp;
        this.iid = firebaseInstanceIdInternal;
        this.fis = firebaseInstallationsApi;
        this.autoInit = new AutoInit(subscriber);
        Context applicationContext = firebaseApp.getApplicationContext();
        this.context = applicationContext;
        FcmLifecycleCallbacks fcmLifecycleCallbacks = new FcmLifecycleCallbacks();
        this.lifecycleCallbacks = fcmLifecycleCallbacks;
        this.metadata = metadata;
        this.taskExecutor = executor;
        this.gmsRpc = gmsRpc;
        this.requestDeduplicator = new RequestDeduplicator(executor);
        this.initExecutor = executor2;
        this.fileExecutor = executor3;
        Context applicationContext2 = firebaseApp.getApplicationContext();
        if (applicationContext2 instanceof Application) {
            ((Application) applicationContext2).registerActivityLifecycleCallbacks(fcmLifecycleCallbacks);
        } else {
            Log.w(Constants.TAG, "Context " + applicationContext2 + " was not an application, can't register for lifecycle callbacks. Some notification events may be dropped as a result.");
        }
        if (firebaseInstanceIdInternal != null) {
            firebaseInstanceIdInternal.addNewTokenListener(new FirebaseInstanceIdInternal.NewTokenListener() { // from class: com.google.firebase.messaging.k
                @Override // com.google.firebase.iid.internal.FirebaseInstanceIdInternal.NewTokenListener
                public final void onNewToken(String str) {
                    this.f15146a.lambda$new$0(str);
                }
            });
        }
        executor2.execute(new Runnable() { // from class: com.google.firebase.messaging.l
            @Override // java.lang.Runnable
            public final void run() {
                this.f15147a.lambda$new$1();
            }
        });
        Task<TopicsSubscriber> taskB = TopicsSubscriber.b(this, metadata, gmsRpc, applicationContext, FcmExecutors.f());
        this.topicsSubscriberTask = taskB;
        taskB.addOnSuccessListener(executor2, new OnSuccessListener() { // from class: com.google.firebase.messaging.m
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                this.f15148a.lambda$new$2((TopicsSubscriber) obj);
            }
        });
        executor2.execute(new Runnable() { // from class: com.google.firebase.messaging.n
            @Override // java.lang.Runnable
            public final void run() {
                this.f15149a.lambda$new$3();
            }
        });
    }
}
