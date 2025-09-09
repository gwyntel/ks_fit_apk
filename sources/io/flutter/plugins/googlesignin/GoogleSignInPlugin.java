package io.flutter.plugins.googlesignin;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugins.googlesignin.BackgroundTaskRunner;
import io.flutter.plugins.googlesignin.GoogleSignInPlugin;
import io.flutter.plugins.googlesignin.Messages;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/* loaded from: classes4.dex */
public class GoogleSignInPlugin implements FlutterPlugin, ActivityAware {
    private ActivityPluginBinding activityPluginBinding;
    private Delegate delegate;

    @Nullable
    private BinaryMessenger messenger;

    /* renamed from: io.flutter.plugins.googlesignin.GoogleSignInPlugin$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$flutter$plugins$googlesignin$Messages$SignInType;

        static {
            int[] iArr = new int[Messages.SignInType.values().length];
            $SwitchMap$io$flutter$plugins$googlesignin$Messages$SignInType = iArr;
            try {
                iArr[Messages.SignInType.GAMES.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$flutter$plugins$googlesignin$Messages$SignInType[Messages.SignInType.STANDARD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static class Delegate implements IDelegate, PluginRegistry.ActivityResultListener, Messages.GoogleSignInApi {
        private static final String DEFAULT_GAMES_SIGN_IN = "SignInOption.games";
        private static final String DEFAULT_SIGN_IN = "SignInOption.standard";
        private static final String ERROR_FAILURE_TO_RECOVER_AUTH = "failed_to_recover_auth";
        private static final String ERROR_REASON_EXCEPTION = "exception";
        private static final String ERROR_REASON_NETWORK_ERROR = "network_error";
        private static final String ERROR_REASON_SIGN_IN_CANCELED = "sign_in_canceled";
        private static final String ERROR_REASON_SIGN_IN_FAILED = "sign_in_failed";
        private static final String ERROR_REASON_SIGN_IN_REQUIRED = "sign_in_required";
        private static final String ERROR_REASON_STATUS = "status";
        private static final String ERROR_USER_RECOVERABLE_AUTH = "user_recoverable_auth";
        private static final int REQUEST_CODE_RECOVER_AUTH = 53294;

        @VisibleForTesting
        static final int REQUEST_CODE_REQUEST_SCOPE = 53295;
        private static final int REQUEST_CODE_SIGNIN = 53293;

        @Nullable
        private Activity activity;
        private final BackgroundTaskRunner backgroundTaskRunner = new BackgroundTaskRunner(1);

        @NonNull
        private final Context context;
        private final GoogleSignInWrapper googleSignInWrapper;
        private PendingOperation pendingOperation;
        private List<String> requestedScopes;
        private GoogleSignInClient signInClient;

        private static class PendingOperation {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            @Nullable
            final Messages.Result<Boolean> boolResult;

            @Nullable
            final Object data;

            @NonNull
            final String method;

            @Nullable
            final Messages.Result<String> stringResult;

            @Nullable
            final Messages.Result<Messages.UserData> userDataResult;

            @Nullable
            final Messages.Result<Void> voidResult;

            PendingOperation(@NonNull String str, @Nullable Messages.Result<Messages.UserData> result, @Nullable Messages.Result<Void> result2, @Nullable Messages.Result<Boolean> result3, @Nullable Messages.Result<String> result4, @Nullable Object obj) {
                this.method = str;
                this.userDataResult = result;
                this.voidResult = result2;
                this.boolResult = result3;
                this.stringResult = result4;
                this.data = obj;
            }
        }

        public Delegate(@NonNull Context context, @NonNull GoogleSignInWrapper googleSignInWrapper) {
            this.context = context;
            this.googleSignInWrapper = googleSignInWrapper;
        }

        private void checkAndSetPendingAccessTokenOperation(String str, Messages.Result<String> result, @NonNull Object obj) {
            checkAndSetPendingStringOperation(str, result, obj);
        }

        private void checkAndSetPendingBoolOperation(String str, @NonNull Messages.Result<Boolean> result) {
            checkAndSetPendingOperation(str, null, null, result, null, null);
        }

        private void checkAndSetPendingOperation(String str, Messages.Result<Messages.UserData> result, Messages.Result<Void> result2, Messages.Result<Boolean> result3, Messages.Result<String> result4, Object obj) {
            if (this.pendingOperation == null) {
                this.pendingOperation = new PendingOperation(str, result, result2, result3, result4, obj);
                return;
            }
            throw new IllegalStateException("Concurrent operations detected: " + this.pendingOperation.method + ", " + str);
        }

        private void checkAndSetPendingSignInOperation(String str, @NonNull Messages.Result<Messages.UserData> result) {
            checkAndSetPendingOperation(str, result, null, null, null, null);
        }

        private void checkAndSetPendingStringOperation(String str, @NonNull Messages.Result<String> result, @Nullable Object obj) {
            checkAndSetPendingOperation(str, null, null, null, result, obj);
        }

        private void checkAndSetPendingVoidOperation(String str, @NonNull Messages.Result<Void> result) {
            checkAndSetPendingOperation(str, null, result, null, null, null);
        }

        private String errorCodeForStatus(int i2) {
            return i2 != 4 ? i2 != 7 ? i2 != 12501 ? ERROR_REASON_SIGN_IN_FAILED : ERROR_REASON_SIGN_IN_CANCELED : ERROR_REASON_NETWORK_ERROR : ERROR_REASON_SIGN_IN_REQUIRED;
        }

        private void finishWithBoolean(Boolean bool) {
            Messages.Result<Boolean> result = this.pendingOperation.boolResult;
            Objects.requireNonNull(result);
            result.success(bool);
            this.pendingOperation = null;
        }

        private void finishWithError(String str, String str2) {
            PendingOperation pendingOperation = this.pendingOperation;
            Messages.Result result = pendingOperation.userDataResult;
            if (result == null && (result = pendingOperation.boolResult) == null && (result = pendingOperation.stringResult) == null) {
                result = pendingOperation.voidResult;
            }
            Objects.requireNonNull(result);
            result.error(new Messages.FlutterError(str, str2, null));
            this.pendingOperation = null;
        }

        private void finishWithSuccess() {
            Messages.Result<Void> result = this.pendingOperation.voidResult;
            Objects.requireNonNull(result);
            result.success(null);
            this.pendingOperation = null;
        }

        private void finishWithUserData(Messages.UserData userData) {
            Messages.Result<Messages.UserData> result = this.pendingOperation.userDataResult;
            Objects.requireNonNull(result);
            result.success(userData);
            this.pendingOperation = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Void lambda$clearAuthCache$2(String str) throws Exception {
            GoogleAuthUtil.clearToken(this.context, str);
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$clearAuthCache$3(Messages.Result result, Future future) {
            try {
                result.success((Void) future.get());
            } catch (InterruptedException e2) {
                result.error(new Messages.FlutterError("exception", e2.getMessage(), null));
                Thread.currentThread().interrupt();
            } catch (ExecutionException e3) {
                Throwable cause = e3.getCause();
                result.error(new Messages.FlutterError("exception", cause == null ? null : cause.getMessage(), null));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$disconnect$1(Task task) {
            if (task.isSuccessful()) {
                finishWithSuccess();
            } else {
                finishWithError("status", "Failed to disconnect.");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ String lambda$getAccessToken$4(String str) throws Exception {
            return GoogleAuthUtil.getToken(this.context, new Account(str, "com.google"), "oauth2:" + Joiner.on(' ').join(this.requestedScopes));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getAccessToken$5(Messages.Result result, Boolean bool, String str, Future future) {
            try {
                result.success((String) future.get());
            } catch (InterruptedException e2) {
                result.error(new Messages.FlutterError("exception", e2.getMessage(), null));
                Thread.currentThread().interrupt();
            } catch (ExecutionException e3) {
                if (!(e3.getCause() instanceof UserRecoverableAuthException)) {
                    Throwable cause = e3.getCause();
                    result.error(new Messages.FlutterError("exception", cause == null ? null : cause.getMessage(), null));
                    return;
                }
                if (!bool.booleanValue() || this.pendingOperation != null) {
                    result.error(new Messages.FlutterError(ERROR_USER_RECOVERABLE_AUTH, e3.getLocalizedMessage(), null));
                    return;
                }
                Activity activity = getActivity();
                if (activity != null) {
                    checkAndSetPendingAccessTokenOperation("getTokens", result, str);
                    activity.startActivityForResult(((UserRecoverableAuthException) e3.getCause()).getIntent(), REQUEST_CODE_RECOVER_AUTH);
                } else {
                    result.error(new Messages.FlutterError(ERROR_USER_RECOVERABLE_AUTH, "Cannot recover auth because app is not in foreground. " + e3.getLocalizedMessage(), null));
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$signOut$0(Task task) {
            if (task.isSuccessful()) {
                finishWithSuccess();
            } else {
                finishWithError("status", "Failed to signout.");
            }
        }

        private void onSignInAccount(GoogleSignInAccount googleSignInAccount) {
            Messages.UserData.Builder displayName = new Messages.UserData.Builder().setEmail(googleSignInAccount.getEmail()).setId(googleSignInAccount.getId()).setIdToken(googleSignInAccount.getIdToken()).setServerAuthCode(googleSignInAccount.getServerAuthCode()).setDisplayName(googleSignInAccount.getDisplayName());
            if (googleSignInAccount.getPhotoUrl() != null) {
                displayName.setPhotoUrl(googleSignInAccount.getPhotoUrl().toString());
            }
            finishWithUserData(displayName.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onSignInResult(Task<GoogleSignInAccount> task) {
            try {
                onSignInAccount(task.getResult(ApiException.class));
            } catch (ApiException e2) {
                finishWithError(errorCodeForStatus(e2.getStatusCode()), e2.toString());
            } catch (RuntimeExecutionException e3) {
                finishWithError("exception", e3.toString());
            }
        }

        @Override // io.flutter.plugins.googlesignin.Messages.GoogleSignInApi
        public void clearAuthCache(@NonNull final String str, @NonNull final Messages.Result<Void> result) {
            this.backgroundTaskRunner.runInBackground(new Callable() { // from class: io.flutter.plugins.googlesignin.c
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return this.f25099a.lambda$clearAuthCache$2(str);
                }
            }, new BackgroundTaskRunner.Callback() { // from class: io.flutter.plugins.googlesignin.d
                @Override // io.flutter.plugins.googlesignin.BackgroundTaskRunner.Callback
                public final void run(Future future) {
                    GoogleSignInPlugin.Delegate.lambda$clearAuthCache$3(result, future);
                }
            });
        }

        @Override // io.flutter.plugins.googlesignin.Messages.GoogleSignInApi
        public void disconnect(@NonNull Messages.Result<Void> result) {
            checkAndSetPendingVoidOperation("disconnect", result);
            this.signInClient.revokeAccess().addOnCompleteListener(new OnCompleteListener() { // from class: io.flutter.plugins.googlesignin.g
                @Override // com.google.android.gms.tasks.OnCompleteListener
                public final void onComplete(Task task) {
                    this.f25108a.lambda$disconnect$1(task);
                }
            });
        }

        @Override // io.flutter.plugins.googlesignin.Messages.GoogleSignInApi
        public void getAccessToken(@NonNull final String str, @NonNull final Boolean bool, @NonNull final Messages.Result<String> result) {
            this.backgroundTaskRunner.runInBackground(new Callable() { // from class: io.flutter.plugins.googlesignin.e
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return this.f25102a.lambda$getAccessToken$4(str);
                }
            }, new BackgroundTaskRunner.Callback() { // from class: io.flutter.plugins.googlesignin.f
                @Override // io.flutter.plugins.googlesignin.BackgroundTaskRunner.Callback
                public final void run(Future future) {
                    this.f25104a.lambda$getAccessToken$5(result, bool, str, future);
                }
            });
        }

        @Nullable
        public Activity getActivity() {
            return this.activity;
        }

        @Override // io.flutter.plugins.googlesignin.GoogleSignInPlugin.IDelegate
        public void getTokens(@NonNull MethodChannel.Result result, @NonNull String str, boolean z2) {
            getAccessToken(str, Boolean.valueOf(z2), new ErrorConvertingMethodChannelResult<String>(result) { // from class: io.flutter.plugins.googlesignin.GoogleSignInPlugin.Delegate.2
                @Override // io.flutter.plugins.googlesignin.Messages.Result
                public void success(String str2) {
                    HashMap map = new HashMap();
                    map.put("accessToken", str2);
                    this.result.success(map);
                }
            });
        }

        @Override // io.flutter.plugins.googlesignin.Messages.GoogleSignInApi
        public void init(@NonNull Messages.InitParams initParams) {
            GoogleSignInOptions.Builder builder;
            int identifier;
            try {
                int i2 = AnonymousClass1.$SwitchMap$io$flutter$plugins$googlesignin$Messages$SignInType[initParams.getSignInType().ordinal()];
                if (i2 == 1) {
                    builder = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
                } else {
                    if (i2 != 2) {
                        throw new IllegalStateException("Unknown signInOption");
                    }
                    builder = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail();
                }
                String serverClientId = initParams.getServerClientId();
                if (!Strings.isNullOrEmpty(initParams.getClientId()) && Strings.isNullOrEmpty(serverClientId)) {
                    Log.w("google_sign_in", "clientId is not supported on Android and is interpreted as serverClientId. Use serverClientId instead to suppress this warning.");
                    serverClientId = initParams.getClientId();
                }
                if (Strings.isNullOrEmpty(serverClientId) && (identifier = this.context.getResources().getIdentifier("default_web_client_id", "string", this.context.getPackageName())) != 0) {
                    serverClientId = this.context.getString(identifier);
                }
                if (!Strings.isNullOrEmpty(serverClientId)) {
                    builder.requestIdToken(serverClientId);
                    builder.requestServerAuthCode(serverClientId, initParams.getForceCodeForRefreshToken().booleanValue());
                }
                List<String> scopes = initParams.getScopes();
                this.requestedScopes = scopes;
                Iterator<String> it = scopes.iterator();
                while (it.hasNext()) {
                    builder.requestScopes(new Scope(it.next()), new Scope[0]);
                }
                if (!Strings.isNullOrEmpty(initParams.getHostedDomain())) {
                    builder.setHostedDomain(initParams.getHostedDomain());
                }
                this.signInClient = this.googleSignInWrapper.getClient(this.context, builder.build());
            } catch (Exception e2) {
                throw new Messages.FlutterError("exception", e2.getMessage(), null);
            }
        }

        @Override // io.flutter.plugins.googlesignin.Messages.GoogleSignInApi
        @NonNull
        public Boolean isSignedIn() {
            return Boolean.valueOf(GoogleSignIn.getLastSignedInAccount(this.context) != null);
        }

        @Override // io.flutter.plugin.common.PluginRegistry.ActivityResultListener
        public boolean onActivityResult(int i2, int i3, @Nullable Intent intent) {
            PendingOperation pendingOperation = this.pendingOperation;
            if (pendingOperation == null) {
                return false;
            }
            switch (i2) {
                case REQUEST_CODE_SIGNIN /* 53293 */:
                    if (intent == null) {
                        finishWithError(ERROR_REASON_SIGN_IN_FAILED, "Signin failed");
                        break;
                    } else {
                        onSignInResult(GoogleSignIn.getSignedInAccountFromIntent(intent));
                        break;
                    }
                case REQUEST_CODE_RECOVER_AUTH /* 53294 */:
                    if (i3 != -1) {
                        finishWithError(ERROR_FAILURE_TO_RECOVER_AUTH, "Failed attempt to recover authentication");
                        break;
                    } else {
                        Messages.Result<String> result = pendingOperation.stringResult;
                        Objects.requireNonNull(result);
                        Object obj = this.pendingOperation.data;
                        Objects.requireNonNull(obj);
                        this.pendingOperation = null;
                        getAccessToken((String) obj, Boolean.FALSE, result);
                        break;
                    }
                case REQUEST_CODE_REQUEST_SCOPE /* 53295 */:
                    finishWithBoolean(Boolean.valueOf(i3 == -1));
                    break;
            }
            return false;
        }

        @Override // io.flutter.plugins.googlesignin.Messages.GoogleSignInApi
        public void requestScopes(@NonNull List<String> list, @NonNull Messages.Result<Boolean> result) {
            checkAndSetPendingBoolOperation("requestScopes", result);
            GoogleSignInAccount lastSignedInAccount = this.googleSignInWrapper.getLastSignedInAccount(this.context);
            if (lastSignedInAccount == null) {
                finishWithError(ERROR_REASON_SIGN_IN_REQUIRED, "No account to grant scopes.");
                return;
            }
            ArrayList arrayList = new ArrayList();
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                Scope scope = new Scope(it.next());
                if (!this.googleSignInWrapper.hasPermissions(lastSignedInAccount, scope)) {
                    arrayList.add(scope);
                }
            }
            if (arrayList.isEmpty()) {
                finishWithBoolean(Boolean.TRUE);
            } else {
                this.googleSignInWrapper.requestPermissions(getActivity(), REQUEST_CODE_REQUEST_SCOPE, lastSignedInAccount, (Scope[]) arrayList.toArray(new Scope[0]));
            }
        }

        public void setActivity(@Nullable Activity activity) {
            this.activity = activity;
        }

        @Override // io.flutter.plugins.googlesignin.Messages.GoogleSignInApi
        public void signIn(@NonNull Messages.Result<Messages.UserData> result) {
            if (getActivity() == null) {
                throw new IllegalStateException("signIn needs a foreground activity");
            }
            checkAndSetPendingSignInOperation("signIn", result);
            getActivity().startActivityForResult(this.signInClient.getSignInIntent(), REQUEST_CODE_SIGNIN);
        }

        @Override // io.flutter.plugins.googlesignin.Messages.GoogleSignInApi
        public void signInSilently(@NonNull Messages.Result<Messages.UserData> result) {
            checkAndSetPendingSignInOperation("signInSilently", result);
            Task<GoogleSignInAccount> taskSilentSignIn = this.signInClient.silentSignIn();
            if (taskSilentSignIn.isComplete()) {
                onSignInResult(taskSilentSignIn);
            } else {
                taskSilentSignIn.addOnCompleteListener(new OnCompleteListener() { // from class: io.flutter.plugins.googlesignin.h
                    @Override // com.google.android.gms.tasks.OnCompleteListener
                    public final void onComplete(Task task) {
                        this.f25109a.onSignInResult(task);
                    }
                });
            }
        }

        @Override // io.flutter.plugins.googlesignin.Messages.GoogleSignInApi
        public void signOut(@NonNull Messages.Result<Void> result) {
            checkAndSetPendingVoidOperation("signOut", result);
            this.signInClient.signOut().addOnCompleteListener(new OnCompleteListener() { // from class: io.flutter.plugins.googlesignin.i
                @Override // com.google.android.gms.tasks.OnCompleteListener
                public final void onComplete(Task task) {
                    this.f25110a.lambda$signOut$0(task);
                }
            });
        }

        @Override // io.flutter.plugins.googlesignin.GoogleSignInPlugin.IDelegate
        public void isSignedIn(@NonNull MethodChannel.Result result) {
            result.success(isSignedIn());
        }

        @Override // io.flutter.plugins.googlesignin.GoogleSignInPlugin.IDelegate
        public void clearAuthCache(@NonNull MethodChannel.Result result, @NonNull String str) {
            clearAuthCache(str, new VoidMethodChannelResult(result));
        }

        @Override // io.flutter.plugins.googlesignin.GoogleSignInPlugin.IDelegate
        public void disconnect(@NonNull MethodChannel.Result result) {
            signOut(new VoidMethodChannelResult(result));
        }

        @Override // io.flutter.plugins.googlesignin.GoogleSignInPlugin.IDelegate
        public void signOut(@NonNull MethodChannel.Result result) {
            signOut(new VoidMethodChannelResult(result));
        }

        @Override // io.flutter.plugins.googlesignin.GoogleSignInPlugin.IDelegate
        public void signIn(@NonNull MethodChannel.Result result) {
            signIn(new UserDataMethodChannelResult(result));
        }

        @Override // io.flutter.plugins.googlesignin.GoogleSignInPlugin.IDelegate
        public void signInSilently(@NonNull MethodChannel.Result result) {
            signInSilently(new UserDataMethodChannelResult(result));
        }

        @Override // io.flutter.plugins.googlesignin.GoogleSignInPlugin.IDelegate
        public void requestScopes(@NonNull MethodChannel.Result result, @NonNull List<String> list) {
            requestScopes(list, new ErrorConvertingMethodChannelResult<Boolean>(result) { // from class: io.flutter.plugins.googlesignin.GoogleSignInPlugin.Delegate.1
                @Override // io.flutter.plugins.googlesignin.Messages.Result
                public void success(Boolean bool) {
                    this.result.success(bool);
                }
            });
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x0026  */
        @Override // io.flutter.plugins.googlesignin.GoogleSignInPlugin.IDelegate
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void init(@androidx.annotation.NonNull io.flutter.plugin.common.MethodChannel.Result r4, @androidx.annotation.NonNull java.lang.String r5, @androidx.annotation.NonNull java.util.List<java.lang.String> r6, @androidx.annotation.Nullable java.lang.String r7, @androidx.annotation.Nullable java.lang.String r8, @androidx.annotation.Nullable java.lang.String r9, boolean r10) {
            /*
                r3 = this;
                int r0 = r5.hashCode()     // Catch: io.flutter.plugins.googlesignin.Messages.FlutterError -> L1a
                r1 = 849126666(0x329ca50a, float:1.8235841E-8)
                r2 = 1
                if (r0 == r1) goto L1c
                r1 = 2056100820(0x7a8d9bd4, float:3.676372E35)
                if (r0 == r1) goto L10
                goto L26
            L10:
                java.lang.String r0 = "SignInOption.standard"
                boolean r5 = r5.equals(r0)     // Catch: io.flutter.plugins.googlesignin.Messages.FlutterError -> L1a
                if (r5 == 0) goto L26
                r5 = r2
                goto L27
            L1a:
                r5 = move-exception
                goto L65
            L1c:
                java.lang.String r0 = "SignInOption.games"
                boolean r5 = r5.equals(r0)     // Catch: io.flutter.plugins.googlesignin.Messages.FlutterError -> L1a
                if (r5 == 0) goto L26
                r5 = 0
                goto L27
            L26:
                r5 = -1
            L27:
                if (r5 == 0) goto L36
                if (r5 != r2) goto L2e
                io.flutter.plugins.googlesignin.Messages$SignInType r5 = io.flutter.plugins.googlesignin.Messages.SignInType.STANDARD     // Catch: io.flutter.plugins.googlesignin.Messages.FlutterError -> L1a
                goto L38
            L2e:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException     // Catch: io.flutter.plugins.googlesignin.Messages.FlutterError -> L1a
                java.lang.String r6 = "Unknown signInOption"
                r5.<init>(r6)     // Catch: io.flutter.plugins.googlesignin.Messages.FlutterError -> L1a
                throw r5     // Catch: io.flutter.plugins.googlesignin.Messages.FlutterError -> L1a
            L36:
                io.flutter.plugins.googlesignin.Messages$SignInType r5 = io.flutter.plugins.googlesignin.Messages.SignInType.GAMES     // Catch: io.flutter.plugins.googlesignin.Messages.FlutterError -> L1a
            L38:
                io.flutter.plugins.googlesignin.Messages$InitParams$Builder r0 = new io.flutter.plugins.googlesignin.Messages$InitParams$Builder     // Catch: io.flutter.plugins.googlesignin.Messages.FlutterError -> L1a
                r0.<init>()     // Catch: io.flutter.plugins.googlesignin.Messages.FlutterError -> L1a
                io.flutter.plugins.googlesignin.Messages$InitParams$Builder r5 = r0.setSignInType(r5)     // Catch: io.flutter.plugins.googlesignin.Messages.FlutterError -> L1a
                io.flutter.plugins.googlesignin.Messages$InitParams$Builder r5 = r5.setScopes(r6)     // Catch: io.flutter.plugins.googlesignin.Messages.FlutterError -> L1a
                io.flutter.plugins.googlesignin.Messages$InitParams$Builder r5 = r5.setHostedDomain(r7)     // Catch: io.flutter.plugins.googlesignin.Messages.FlutterError -> L1a
                io.flutter.plugins.googlesignin.Messages$InitParams$Builder r5 = r5.setClientId(r8)     // Catch: io.flutter.plugins.googlesignin.Messages.FlutterError -> L1a
                io.flutter.plugins.googlesignin.Messages$InitParams$Builder r5 = r5.setServerClientId(r9)     // Catch: io.flutter.plugins.googlesignin.Messages.FlutterError -> L1a
                java.lang.Boolean r6 = java.lang.Boolean.valueOf(r10)     // Catch: io.flutter.plugins.googlesignin.Messages.FlutterError -> L1a
                io.flutter.plugins.googlesignin.Messages$InitParams$Builder r5 = r5.setForceCodeForRefreshToken(r6)     // Catch: io.flutter.plugins.googlesignin.Messages.FlutterError -> L1a
                io.flutter.plugins.googlesignin.Messages$InitParams r5 = r5.build()     // Catch: io.flutter.plugins.googlesignin.Messages.FlutterError -> L1a
                r3.init(r5)     // Catch: io.flutter.plugins.googlesignin.Messages.FlutterError -> L1a
                r5 = 0
                r4.success(r5)     // Catch: io.flutter.plugins.googlesignin.Messages.FlutterError -> L1a
                goto L70
            L65:
                java.lang.String r6 = r5.code
                java.lang.String r7 = r5.getMessage()
                java.lang.Object r5 = r5.details
                r4.error(r6, r7, r5)
            L70:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.flutter.plugins.googlesignin.GoogleSignInPlugin.Delegate.init(io.flutter.plugin.common.MethodChannel$Result, java.lang.String, java.util.List, java.lang.String, java.lang.String, java.lang.String, boolean):void");
        }
    }

    private static abstract class ErrorConvertingMethodChannelResult<T> implements Messages.Result<T> {

        @NonNull
        final MethodChannel.Result result;

        public ErrorConvertingMethodChannelResult(@NonNull MethodChannel.Result result) {
            this.result = result;
        }

        @Override // io.flutter.plugins.googlesignin.Messages.Result
        public void error(@NonNull Throwable th) {
            if (!(th instanceof Messages.FlutterError)) {
                this.result.error("exception", th.getMessage(), null);
            } else {
                Messages.FlutterError flutterError = (Messages.FlutterError) th;
                this.result.error(flutterError.code, flutterError.getMessage(), flutterError.details);
            }
        }
    }

    public interface IDelegate {
        void clearAuthCache(@NonNull MethodChannel.Result result, @NonNull String str);

        void disconnect(@NonNull MethodChannel.Result result);

        void getTokens(@NonNull MethodChannel.Result result, @NonNull String str, boolean z2);

        void init(@NonNull MethodChannel.Result result, @NonNull String str, @NonNull List<String> list, @Nullable String str2, @Nullable String str3, @Nullable String str4, boolean z2);

        void isSignedIn(@NonNull MethodChannel.Result result);

        void requestScopes(@NonNull MethodChannel.Result result, @NonNull List<String> list);

        void signIn(@NonNull MethodChannel.Result result);

        void signInSilently(@NonNull MethodChannel.Result result);

        void signOut(@NonNull MethodChannel.Result result);
    }

    private static class UserDataMethodChannelResult extends ErrorConvertingMethodChannelResult<Messages.UserData> {
        public UserDataMethodChannelResult(MethodChannel.Result result) {
            super(result);
        }

        @Override // io.flutter.plugins.googlesignin.Messages.Result
        public void success(Messages.UserData userData) {
            HashMap map = new HashMap();
            map.put("email", userData.getEmail());
            map.put("id", userData.getId());
            map.put("idToken", userData.getIdToken());
            map.put("serverAuthCode", userData.getServerAuthCode());
            map.put("displayName", userData.getDisplayName());
            if (userData.getPhotoUrl() != null) {
                map.put("photoUrl", userData.getPhotoUrl());
            }
            this.result.success(map);
        }
    }

    private static class VoidMethodChannelResult extends ErrorConvertingMethodChannelResult<Void> {
        public VoidMethodChannelResult(MethodChannel.Result result) {
            super(result);
        }

        @Override // io.flutter.plugins.googlesignin.Messages.Result
        public void success(Void r2) {
            this.result.success(null);
        }
    }

    private void attachToActivity(ActivityPluginBinding activityPluginBinding) {
        this.activityPluginBinding = activityPluginBinding;
        activityPluginBinding.addActivityResultListener(this.delegate);
        this.delegate.setActivity(activityPluginBinding.getActivity());
    }

    private void dispose() {
        this.delegate = null;
        BinaryMessenger binaryMessenger = this.messenger;
        if (binaryMessenger != null) {
            Messages.GoogleSignInApi.CC.k(binaryMessenger, null);
            this.messenger = null;
        }
    }

    private void disposeActivity() {
        this.activityPluginBinding.removeActivityResultListener(this.delegate);
        this.delegate.setActivity(null);
        this.activityPluginBinding = null;
    }

    @VisibleForTesting
    public void initInstance(@NonNull BinaryMessenger binaryMessenger, @NonNull Context context, @NonNull GoogleSignInWrapper googleSignInWrapper) {
        this.messenger = binaryMessenger;
        Delegate delegate = new Delegate(context, googleSignInWrapper);
        this.delegate = delegate;
        Messages.GoogleSignInApi.CC.k(binaryMessenger, delegate);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(@NonNull ActivityPluginBinding activityPluginBinding) {
        attachToActivity(activityPluginBinding);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        initInstance(flutterPluginBinding.getBinaryMessenger(), flutterPluginBinding.getApplicationContext(), new GoogleSignInWrapper());
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        disposeActivity();
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        disposeActivity();
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        dispose();
    }

    @VisibleForTesting
    void onMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
        String str = methodCall.method;
        str.hashCode();
        switch (str) {
            case "signIn":
                this.delegate.signIn(result);
                break;
            case "signInSilently":
                this.delegate.signInSilently(result);
                break;
            case "isSignedIn":
                this.delegate.isSignedIn(result);
                break;
            case "init":
                String str2 = (String) methodCall.argument("signInOption");
                Objects.requireNonNull(str2);
                List<String> list = (List) methodCall.argument("scopes");
                Objects.requireNonNull(list);
                String str3 = (String) methodCall.argument("hostedDomain");
                String str4 = (String) methodCall.argument(TmpConstant.KEY_CLIENT_ID);
                String str5 = (String) methodCall.argument("serverClientId");
                Boolean bool = (Boolean) methodCall.argument("forceCodeForRefreshToken");
                Objects.requireNonNull(bool);
                this.delegate.init(result, str2, list, str3, str4, str5, bool.booleanValue());
                break;
            case "clearAuthCache":
                String str6 = (String) methodCall.argument("token");
                Objects.requireNonNull(str6);
                this.delegate.clearAuthCache(result, str6);
                break;
            case "disconnect":
                this.delegate.disconnect(result);
                break;
            case "getTokens":
                String str7 = (String) methodCall.argument("email");
                Objects.requireNonNull(str7);
                Boolean bool2 = (Boolean) methodCall.argument("shouldRecoverAuth");
                Objects.requireNonNull(bool2);
                this.delegate.getTokens(result, str7, bool2.booleanValue());
                break;
            case "requestScopes":
                List<String> list2 = (List) methodCall.argument("scopes");
                Objects.requireNonNull(list2);
                this.delegate.requestScopes(result, list2);
                break;
            case "signOut":
                this.delegate.signOut(result);
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding activityPluginBinding) {
        attachToActivity(activityPluginBinding);
    }
}
