package com.google.firebase.installations;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.components.Lazy;
import com.google.firebase.inject.Provider;
import com.google.firebase.installations.FirebaseInstallationsException;
import com.google.firebase.installations.internal.FidListener;
import com.google.firebase.installations.internal.FidListenerHandle;
import com.google.firebase.installations.local.IidStore;
import com.google.firebase.installations.local.PersistedInstallation;
import com.google.firebase.installations.local.PersistedInstallationEntry;
import com.google.firebase.installations.remote.FirebaseInstallationServiceClient;
import com.google.firebase.installations.remote.InstallationResponse;
import com.google.firebase.installations.remote.TokenResult;
import com.kingsmith.miot.KsProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes3.dex */
public class FirebaseInstallations implements FirebaseInstallationsApi {
    private static final String API_KEY_VALIDATION_MSG = "Please set a valid API key. A Firebase API key is required to communicate with Firebase server APIs: It authenticates your project with Google.Please refer to https://firebase.google.com/support/privacy/init-options.";
    private static final String APP_ID_VALIDATION_MSG = "Please set your Application ID. A valid Firebase App ID is required to communicate with Firebase server APIs: It identifies your application with Firebase.Please refer to https://firebase.google.com/support/privacy/init-options.";
    private static final String AUTH_ERROR_MSG = "Installation ID could not be validated with the Firebase servers (maybe it was deleted). Firebase Installations will need to create a new Installation ID and auth token. Please retry your last request.";
    private static final String CHIME_FIREBASE_APP_NAME = "CHIME_ANDROID_SDK";
    private static final int CORE_POOL_SIZE = 0;
    private static final long KEEP_ALIVE_TIME_IN_SECONDS = 30;
    private static final String LOCKFILE_NAME_GENERATE_FID = "generatefid.lock";
    private static final int MAXIMUM_POOL_SIZE = 1;
    private static final String PROJECT_ID_VALIDATION_MSG = "Please set your Project ID. A valid Firebase Project ID is required to communicate with Firebase server APIs: It identifies your application with Firebase.Please refer to https://firebase.google.com/support/privacy/init-options.";
    private final ExecutorService backgroundExecutor;

    @GuardedBy("this")
    private String cachedFid;
    private final RandomFidGenerator fidGenerator;

    @GuardedBy("FirebaseInstallations.this")
    private Set<FidListener> fidListeners;
    private final FirebaseApp firebaseApp;
    private final Lazy<IidStore> iidStore;

    @GuardedBy(KsProperty.Lock)
    private final List<StateListener> listeners;
    private final Object lock;
    private final Executor networkExecutor;
    private final PersistedInstallation persistedInstallation;
    private final FirebaseInstallationServiceClient serviceClient;
    private final Utils utils;
    private static final Object lockGenerateFid = new Object();
    private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() { // from class: com.google.firebase.installations.FirebaseInstallations.1
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override // java.util.concurrent.ThreadFactory
        @SuppressLint({"ThreadPoolCreation"})
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, String.format("firebase-installations-executor-%d", Integer.valueOf(this.mCount.getAndIncrement())));
        }
    };

    /* renamed from: com.google.firebase.installations.FirebaseInstallations$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f15080a;

        /* renamed from: b, reason: collision with root package name */
        static final /* synthetic */ int[] f15081b;

        static {
            int[] iArr = new int[TokenResult.ResponseCode.values().length];
            f15081b = iArr;
            try {
                iArr[TokenResult.ResponseCode.OK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f15081b[TokenResult.ResponseCode.BAD_CONFIG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f15081b[TokenResult.ResponseCode.AUTH_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[InstallationResponse.ResponseCode.values().length];
            f15080a = iArr2;
            try {
                iArr2[InstallationResponse.ResponseCode.OK.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f15080a[InstallationResponse.ResponseCode.BAD_CONFIG.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    FirebaseInstallations(final FirebaseApp firebaseApp, Provider provider, ExecutorService executorService, Executor executor) {
        this(executorService, executor, firebaseApp, new FirebaseInstallationServiceClient(firebaseApp.getApplicationContext(), provider), new PersistedInstallation(firebaseApp), Utils.getInstance(), new Lazy(new Provider() { // from class: com.google.firebase.installations.a
            @Override // com.google.firebase.inject.Provider
            public final Object get() {
                return FirebaseInstallations.lambda$new$0(firebaseApp);
            }
        }), new RandomFidGenerator());
    }

    private Task<InstallationTokenResult> addGetAuthTokenListener() {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        addStateListeners(new GetAuthTokenListener(this.utils, taskCompletionSource));
        return taskCompletionSource.getTask();
    }

    private Task<String> addGetIdListener() {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        addStateListeners(new GetIdListener(taskCompletionSource));
        return taskCompletionSource.getTask();
    }

    private void addStateListeners(StateListener stateListener) {
        synchronized (this.lock) {
            this.listeners.add(stateListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Void deleteFirebaseInstallationId() throws FirebaseInstallationsException {
        updateCacheFid(null);
        PersistedInstallationEntry multiProcessSafePrefs = getMultiProcessSafePrefs();
        if (multiProcessSafePrefs.isRegistered()) {
            this.serviceClient.deleteFirebaseInstallation(g(), multiProcessSafePrefs.getFirebaseInstallationId(), i(), multiProcessSafePrefs.getRefreshToken());
        }
        insertOrUpdatePrefs(multiProcessSafePrefs.withNoGeneratedFid());
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: doNetworkCallIfNecessary, reason: merged with bridge method [inline-methods] */
    public void lambda$doRegistrationOrRefresh$3(boolean z2) {
        PersistedInstallationEntry persistedInstallationEntryRegisterFidWithServer;
        PersistedInstallationEntry multiProcessSafePrefs = getMultiProcessSafePrefs();
        try {
            if (multiProcessSafePrefs.isErrored() || multiProcessSafePrefs.isUnregistered()) {
                persistedInstallationEntryRegisterFidWithServer = registerFidWithServer(multiProcessSafePrefs);
            } else {
                if (!z2 && !this.utils.isAuthTokenExpired(multiProcessSafePrefs)) {
                    return;
                }
                persistedInstallationEntryRegisterFidWithServer = fetchAuthTokenFromServer(multiProcessSafePrefs);
            }
            insertOrUpdatePrefs(persistedInstallationEntryRegisterFidWithServer);
            updateFidListener(multiProcessSafePrefs, persistedInstallationEntryRegisterFidWithServer);
            if (persistedInstallationEntryRegisterFidWithServer.isRegistered()) {
                updateCacheFid(persistedInstallationEntryRegisterFidWithServer.getFirebaseInstallationId());
            }
            if (persistedInstallationEntryRegisterFidWithServer.isErrored()) {
                triggerOnException(new FirebaseInstallationsException(FirebaseInstallationsException.Status.BAD_CONFIG));
            } else if (persistedInstallationEntryRegisterFidWithServer.isNotGenerated()) {
                triggerOnException(new IOException(AUTH_ERROR_MSG));
            } else {
                triggerOnStateReached(persistedInstallationEntryRegisterFidWithServer);
            }
        } catch (FirebaseInstallationsException e2) {
            triggerOnException(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: doRegistrationOrRefresh, reason: merged with bridge method [inline-methods] */
    public final void lambda$getToken$2(final boolean z2) {
        PersistedInstallationEntry prefsWithGeneratedIdMultiProcessSafe = getPrefsWithGeneratedIdMultiProcessSafe();
        if (z2) {
            prefsWithGeneratedIdMultiProcessSafe = prefsWithGeneratedIdMultiProcessSafe.withClearedAuthToken();
        }
        triggerOnStateReached(prefsWithGeneratedIdMultiProcessSafe);
        this.networkExecutor.execute(new Runnable() { // from class: com.google.firebase.installations.b
            @Override // java.lang.Runnable
            public final void run() {
                this.f15086a.lambda$doRegistrationOrRefresh$3(z2);
            }
        });
    }

    private PersistedInstallationEntry fetchAuthTokenFromServer(@NonNull PersistedInstallationEntry persistedInstallationEntry) throws FirebaseInstallationsException {
        TokenResult tokenResultGenerateAuthToken = this.serviceClient.generateAuthToken(g(), persistedInstallationEntry.getFirebaseInstallationId(), i(), persistedInstallationEntry.getRefreshToken());
        int i2 = AnonymousClass3.f15081b[tokenResultGenerateAuthToken.getResponseCode().ordinal()];
        if (i2 == 1) {
            return persistedInstallationEntry.withAuthToken(tokenResultGenerateAuthToken.getToken(), tokenResultGenerateAuthToken.getTokenExpirationTimestamp(), this.utils.currentTimeInSecs());
        }
        if (i2 == 2) {
            return persistedInstallationEntry.withFisError("BAD CONFIG");
        }
        if (i2 != 3) {
            throw new FirebaseInstallationsException("Firebase Installations Service is unavailable. Please try again later.", FirebaseInstallationsException.Status.UNAVAILABLE);
        }
        updateCacheFid(null);
        return persistedInstallationEntry.withNoGeneratedFid();
    }

    private synchronized String getCacheFid() {
        return this.cachedFid;
    }

    private IidStore getIidStore() {
        return this.iidStore.get();
    }

    @NonNull
    public static FirebaseInstallations getInstance() {
        return getInstance(FirebaseApp.getInstance());
    }

    /* JADX WARN: Finally extract failed */
    private PersistedInstallationEntry getMultiProcessSafePrefs() {
        PersistedInstallationEntry persistedInstallationEntryValue;
        synchronized (lockGenerateFid) {
            try {
                CrossProcessLock crossProcessLockA = CrossProcessLock.a(this.firebaseApp.getApplicationContext(), LOCKFILE_NAME_GENERATE_FID);
                try {
                    persistedInstallationEntryValue = this.persistedInstallation.readPersistedInstallationEntryValue();
                    if (crossProcessLockA != null) {
                        crossProcessLockA.b();
                    }
                } catch (Throwable th) {
                    if (crossProcessLockA != null) {
                        crossProcessLockA.b();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        return persistedInstallationEntryValue;
    }

    /* JADX WARN: Finally extract failed */
    private PersistedInstallationEntry getPrefsWithGeneratedIdMultiProcessSafe() {
        PersistedInstallationEntry persistedInstallationEntryValue;
        synchronized (lockGenerateFid) {
            try {
                CrossProcessLock crossProcessLockA = CrossProcessLock.a(this.firebaseApp.getApplicationContext(), LOCKFILE_NAME_GENERATE_FID);
                try {
                    persistedInstallationEntryValue = this.persistedInstallation.readPersistedInstallationEntryValue();
                    if (persistedInstallationEntryValue.isNotGenerated()) {
                        persistedInstallationEntryValue = this.persistedInstallation.insertOrUpdatePersistedInstallationEntry(persistedInstallationEntryValue.withUnregisteredFid(readExistingIidOrCreateFid(persistedInstallationEntryValue)));
                    }
                    if (crossProcessLockA != null) {
                        crossProcessLockA.b();
                    }
                } catch (Throwable th) {
                    if (crossProcessLockA != null) {
                        crossProcessLockA.b();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        return persistedInstallationEntryValue;
    }

    /* JADX WARN: Finally extract failed */
    private void insertOrUpdatePrefs(PersistedInstallationEntry persistedInstallationEntry) {
        synchronized (lockGenerateFid) {
            try {
                CrossProcessLock crossProcessLockA = CrossProcessLock.a(this.firebaseApp.getApplicationContext(), LOCKFILE_NAME_GENERATE_FID);
                try {
                    this.persistedInstallation.insertOrUpdatePersistedInstallationEntry(persistedInstallationEntry);
                    if (crossProcessLockA != null) {
                        crossProcessLockA.b();
                    }
                } catch (Throwable th) {
                    if (crossProcessLockA != null) {
                        crossProcessLockA.b();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getId$1() {
        lambda$getToken$2(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ IidStore lambda$new$0(FirebaseApp firebaseApp) {
        return new IidStore(firebaseApp);
    }

    private void preConditionChecks() {
        Preconditions.checkNotEmpty(h(), APP_ID_VALIDATION_MSG);
        Preconditions.checkNotEmpty(i(), PROJECT_ID_VALIDATION_MSG);
        Preconditions.checkNotEmpty(g(), API_KEY_VALIDATION_MSG);
        Preconditions.checkArgument(Utils.b(h()), APP_ID_VALIDATION_MSG);
        Preconditions.checkArgument(Utils.a(g()), API_KEY_VALIDATION_MSG);
    }

    private String readExistingIidOrCreateFid(PersistedInstallationEntry persistedInstallationEntry) {
        if ((!this.firebaseApp.getName().equals(CHIME_FIREBASE_APP_NAME) && !this.firebaseApp.isDefaultApp()) || !persistedInstallationEntry.shouldAttemptMigration()) {
            return this.fidGenerator.createRandomFid();
        }
        String iid = getIidStore().readIid();
        return TextUtils.isEmpty(iid) ? this.fidGenerator.createRandomFid() : iid;
    }

    private PersistedInstallationEntry registerFidWithServer(PersistedInstallationEntry persistedInstallationEntry) throws FirebaseInstallationsException {
        InstallationResponse installationResponseCreateFirebaseInstallation = this.serviceClient.createFirebaseInstallation(g(), persistedInstallationEntry.getFirebaseInstallationId(), i(), h(), (persistedInstallationEntry.getFirebaseInstallationId() == null || persistedInstallationEntry.getFirebaseInstallationId().length() != 11) ? null : getIidStore().readToken());
        int i2 = AnonymousClass3.f15080a[installationResponseCreateFirebaseInstallation.getResponseCode().ordinal()];
        if (i2 == 1) {
            return persistedInstallationEntry.withRegisteredFid(installationResponseCreateFirebaseInstallation.getFid(), installationResponseCreateFirebaseInstallation.getRefreshToken(), this.utils.currentTimeInSecs(), installationResponseCreateFirebaseInstallation.getAuthToken().getToken(), installationResponseCreateFirebaseInstallation.getAuthToken().getTokenExpirationTimestamp());
        }
        if (i2 == 2) {
            return persistedInstallationEntry.withFisError("BAD CONFIG");
        }
        throw new FirebaseInstallationsException("Firebase Installations Service is unavailable. Please try again later.", FirebaseInstallationsException.Status.UNAVAILABLE);
    }

    private void triggerOnException(Exception exc) {
        synchronized (this.lock) {
            try {
                Iterator<StateListener> it = this.listeners.iterator();
                while (it.hasNext()) {
                    if (it.next().onException(exc)) {
                        it.remove();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void triggerOnStateReached(PersistedInstallationEntry persistedInstallationEntry) {
        synchronized (this.lock) {
            try {
                Iterator<StateListener> it = this.listeners.iterator();
                while (it.hasNext()) {
                    if (it.next().onStateReached(persistedInstallationEntry)) {
                        it.remove();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private synchronized void updateCacheFid(String str) {
        this.cachedFid = str;
    }

    private synchronized void updateFidListener(PersistedInstallationEntry persistedInstallationEntry, PersistedInstallationEntry persistedInstallationEntry2) {
        if (this.fidListeners.size() != 0 && !TextUtils.equals(persistedInstallationEntry.getFirebaseInstallationId(), persistedInstallationEntry2.getFirebaseInstallationId())) {
            Iterator<FidListener> it = this.fidListeners.iterator();
            while (it.hasNext()) {
                it.next().onFidChanged(persistedInstallationEntry2.getFirebaseInstallationId());
            }
        }
    }

    @Override // com.google.firebase.installations.FirebaseInstallationsApi
    @NonNull
    public Task<Void> delete() {
        return Tasks.call(this.backgroundExecutor, new Callable() { // from class: com.google.firebase.installations.d
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f15089a.deleteFirebaseInstallationId();
            }
        });
    }

    String g() {
        return this.firebaseApp.getOptions().getApiKey();
    }

    @Override // com.google.firebase.installations.FirebaseInstallationsApi
    @NonNull
    public Task<String> getId() {
        preConditionChecks();
        String cacheFid = getCacheFid();
        if (cacheFid != null) {
            return Tasks.forResult(cacheFid);
        }
        Task<String> taskAddGetIdListener = addGetIdListener();
        this.backgroundExecutor.execute(new Runnable() { // from class: com.google.firebase.installations.c
            @Override // java.lang.Runnable
            public final void run() {
                this.f15088a.lambda$getId$1();
            }
        });
        return taskAddGetIdListener;
    }

    @Override // com.google.firebase.installations.FirebaseInstallationsApi
    @NonNull
    public Task<InstallationTokenResult> getToken(final boolean z2) {
        preConditionChecks();
        Task<InstallationTokenResult> taskAddGetAuthTokenListener = addGetAuthTokenListener();
        this.backgroundExecutor.execute(new Runnable() { // from class: com.google.firebase.installations.e
            @Override // java.lang.Runnable
            public final void run() {
                this.f15090a.lambda$getToken$2(z2);
            }
        });
        return taskAddGetAuthTokenListener;
    }

    String h() {
        return this.firebaseApp.getOptions().getApplicationId();
    }

    String i() {
        return this.firebaseApp.getOptions().getProjectId();
    }

    @Override // com.google.firebase.installations.FirebaseInstallationsApi
    @NonNull
    public synchronized FidListenerHandle registerFidListener(@NonNull final FidListener fidListener) {
        this.fidListeners.add(fidListener);
        return new FidListenerHandle() { // from class: com.google.firebase.installations.FirebaseInstallations.2
            @Override // com.google.firebase.installations.internal.FidListenerHandle
            public void unregister() {
                synchronized (FirebaseInstallations.this) {
                    FirebaseInstallations.this.fidListeners.remove(fidListener);
                }
            }
        };
    }

    @NonNull
    public static FirebaseInstallations getInstance(@NonNull FirebaseApp firebaseApp) {
        Preconditions.checkArgument(firebaseApp != null, "Null is not a valid value of FirebaseApp.");
        return (FirebaseInstallations) firebaseApp.get(FirebaseInstallationsApi.class);
    }

    FirebaseInstallations(ExecutorService executorService, Executor executor, FirebaseApp firebaseApp, FirebaseInstallationServiceClient firebaseInstallationServiceClient, PersistedInstallation persistedInstallation, Utils utils, Lazy lazy, RandomFidGenerator randomFidGenerator) {
        this.lock = new Object();
        this.fidListeners = new HashSet();
        this.listeners = new ArrayList();
        this.firebaseApp = firebaseApp;
        this.serviceClient = firebaseInstallationServiceClient;
        this.persistedInstallation = persistedInstallation;
        this.utils = utils;
        this.iidStore = lazy;
        this.fidGenerator = randomFidGenerator;
        this.backgroundExecutor = executorService;
        this.networkExecutor = executor;
    }
}
