package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.ActivityResultRegistryOwner;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.IdRes;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.core.app.MultiWindowModeChangedInfo;
import androidx.core.app.OnMultiWindowModeChangedProvider;
import androidx.core.app.OnPictureInPictureModeChangedProvider;
import androidx.core.app.PictureInPictureModeChangedInfo;
import androidx.core.content.OnConfigurationChangedProvider;
import androidx.core.content.OnTrimMemoryProvider;
import androidx.core.util.Consumer;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.R;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.strictmode.FragmentStrictMode;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public abstract class FragmentManager implements FragmentResultOwner {
    private static boolean DEBUG = false;
    private static final String EXTRA_CREATED_FILLIN_INTENT = "androidx.fragment.extra.ACTIVITY_OPTIONS_BUNDLE";
    private static final String FRAGMENT_KEY_PREFIX = "fragment_";
    private static final String FRAGMENT_MANAGER_STATE_KEY = "state";
    public static final int POP_BACK_STACK_INCLUSIVE = 1;
    private static final String RESULT_KEY_PREFIX = "result_";
    private static final String SAVED_STATE_KEY = "android:support:fragments";

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String TAG = "FragmentManager";

    /* renamed from: a, reason: collision with root package name */
    ArrayList f4174a;

    /* renamed from: c, reason: collision with root package name */
    Fragment f4176c;
    private ArrayList<OnBackStackChangedListener> mBackStackChangeListeners;
    private FragmentContainer mContainer;
    private ArrayList<Fragment> mCreatedMenus;
    private boolean mDestroyed;
    private boolean mExecutingActions;
    private boolean mHavePendingDeferredStart;
    private FragmentHostCallback<?> mHost;
    private boolean mNeedMenuInvalidate;
    private FragmentManagerViewModel mNonConfig;
    private OnBackPressedDispatcher mOnBackPressedDispatcher;
    private Fragment mParent;
    private ActivityResultLauncher<String[]> mRequestPermissions;
    private ActivityResultLauncher<Intent> mStartActivityForResult;
    private ActivityResultLauncher<IntentSenderRequest> mStartIntentSenderForResult;
    private boolean mStateSaved;
    private boolean mStopped;
    private FragmentStrictMode.Policy mStrictModePolicy;
    private ArrayList<Fragment> mTmpAddedFragments;
    private ArrayList<Boolean> mTmpIsPop;
    private ArrayList<BackStackRecord> mTmpRecords;
    private final ArrayList<OpGenerator> mPendingActions = new ArrayList<>();
    private final FragmentStore mFragmentStore = new FragmentStore();
    private final FragmentLayoutInflaterFactory mLayoutInflaterFactory = new FragmentLayoutInflaterFactory(this);
    private final OnBackPressedCallback mOnBackPressedCallback = new OnBackPressedCallback(false) { // from class: androidx.fragment.app.FragmentManager.1
        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            FragmentManager.this.f0();
        }
    };
    private final AtomicInteger mBackStackIndex = new AtomicInteger();
    private final Map<String, BackStackState> mBackStackStates = Collections.synchronizedMap(new HashMap());
    private final Map<String, Bundle> mResults = Collections.synchronizedMap(new HashMap());
    private final Map<String, LifecycleAwareResultListener> mResultListeners = Collections.synchronizedMap(new HashMap());
    private final FragmentLifecycleCallbacksDispatcher mLifecycleCallbacksDispatcher = new FragmentLifecycleCallbacksDispatcher(this);
    private final CopyOnWriteArrayList<FragmentOnAttachListener> mOnAttachListeners = new CopyOnWriteArrayList<>();
    private final Consumer<Configuration> mOnConfigurationChangedListener = new Consumer() { // from class: androidx.fragment.app.o
        @Override // androidx.core.util.Consumer
        public final void accept(Object obj) {
            this.f4317a.lambda$new$0((Configuration) obj);
        }
    };
    private final Consumer<Integer> mOnTrimMemoryListener = new Consumer() { // from class: androidx.fragment.app.p
        @Override // androidx.core.util.Consumer
        public final void accept(Object obj) {
            this.f4318a.lambda$new$1((Integer) obj);
        }
    };
    private final Consumer<MultiWindowModeChangedInfo> mOnMultiWindowModeChangedListener = new Consumer() { // from class: androidx.fragment.app.q
        @Override // androidx.core.util.Consumer
        public final void accept(Object obj) {
            this.f4319a.lambda$new$2((MultiWindowModeChangedInfo) obj);
        }
    };
    private final Consumer<PictureInPictureModeChangedInfo> mOnPictureInPictureModeChangedListener = new Consumer() { // from class: androidx.fragment.app.r
        @Override // androidx.core.util.Consumer
        public final void accept(Object obj) {
            this.f4320a.lambda$new$3((PictureInPictureModeChangedInfo) obj);
        }
    };
    private final MenuProvider mMenuProvider = new MenuProvider() { // from class: androidx.fragment.app.FragmentManager.2
        @Override // androidx.core.view.MenuProvider
        public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
            FragmentManager.this.x(menu, menuInflater);
        }

        @Override // androidx.core.view.MenuProvider
        public void onMenuClosed(@NonNull Menu menu) {
            FragmentManager.this.F(menu);
        }

        @Override // androidx.core.view.MenuProvider
        public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
            return FragmentManager.this.E(menuItem);
        }

        @Override // androidx.core.view.MenuProvider
        public void onPrepareMenu(@NonNull Menu menu) {
            FragmentManager.this.I(menu);
        }
    };

    /* renamed from: b, reason: collision with root package name */
    int f4175b = -1;
    private FragmentFactory mFragmentFactory = null;
    private FragmentFactory mHostFragmentFactory = new FragmentFactory() { // from class: androidx.fragment.app.FragmentManager.3
        @Override // androidx.fragment.app.FragmentFactory
        @NonNull
        public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String str) {
            return FragmentManager.this.getHost().instantiate(FragmentManager.this.getHost().b(), str, null);
        }
    };
    private SpecialEffectsControllerFactory mSpecialEffectsControllerFactory = null;
    private SpecialEffectsControllerFactory mDefaultSpecialEffectsControllerFactory = new SpecialEffectsControllerFactory() { // from class: androidx.fragment.app.FragmentManager.4
        @Override // androidx.fragment.app.SpecialEffectsControllerFactory
        @NonNull
        public SpecialEffectsController createController(@NonNull ViewGroup viewGroup) {
            return new DefaultSpecialEffectsController(viewGroup);
        }
    };

    /* renamed from: d, reason: collision with root package name */
    ArrayDeque f4177d = new ArrayDeque();
    private Runnable mExecCommit = new Runnable() { // from class: androidx.fragment.app.FragmentManager.5
        @Override // java.lang.Runnable
        public void run() {
            FragmentManager.this.P(true);
        }
    };

    public interface BackStackEntry {
        @Nullable
        @Deprecated
        CharSequence getBreadCrumbShortTitle();

        @StringRes
        @Deprecated
        int getBreadCrumbShortTitleRes();

        @Nullable
        @Deprecated
        CharSequence getBreadCrumbTitle();

        @StringRes
        @Deprecated
        int getBreadCrumbTitleRes();

        int getId();

        @Nullable
        String getName();
    }

    private class ClearBackStackState implements OpGenerator {
        private final String mName;

        ClearBackStackState(String str) {
            this.mName = str;
        }

        @Override // androidx.fragment.app.FragmentManager.OpGenerator
        public boolean generateOps(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2) {
            return FragmentManager.this.p(arrayList, arrayList2, this.mName);
        }
    }

    static class FragmentIntentSenderContract extends ActivityResultContract<IntentSenderRequest, ActivityResult> {
        FragmentIntentSenderContract() {
        }

        @Override // androidx.activity.result.contract.ActivityResultContract
        @NonNull
        public Intent createIntent(@NonNull Context context, IntentSenderRequest intentSenderRequest) {
            Bundle bundleExtra;
            Intent intent = new Intent(ActivityResultContracts.StartIntentSenderForResult.ACTION_INTENT_SENDER_REQUEST);
            Intent fillInIntent = intentSenderRequest.getFillInIntent();
            if (fillInIntent != null && (bundleExtra = fillInIntent.getBundleExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE)) != null) {
                intent.putExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE, bundleExtra);
                fillInIntent.removeExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE);
                if (fillInIntent.getBooleanExtra(FragmentManager.EXTRA_CREATED_FILLIN_INTENT, false)) {
                    intentSenderRequest = new IntentSenderRequest.Builder(intentSenderRequest.getIntentSender()).setFillInIntent(null).setFlags(intentSenderRequest.getFlagsValues(), intentSenderRequest.getFlagsMask()).build();
                }
            }
            intent.putExtra(ActivityResultContracts.StartIntentSenderForResult.EXTRA_INTENT_SENDER_REQUEST, intentSenderRequest);
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v(FragmentManager.TAG, "CreateIntent created the following intent: " + intent);
            }
            return intent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.activity.result.contract.ActivityResultContract
        @NonNull
        public ActivityResult parseResult(int i2, @Nullable Intent intent) {
            return new ActivityResult(i2, intent);
        }
    }

    public static abstract class FragmentLifecycleCallbacks {
        @Deprecated
        public void onFragmentActivityCreated(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @Nullable Bundle bundle) {
        }

        public void onFragmentAttached(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @NonNull Context context) {
        }

        public void onFragmentCreated(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @Nullable Bundle bundle) {
        }

        public void onFragmentDestroyed(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        public void onFragmentDetached(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        public void onFragmentPaused(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        public void onFragmentPreAttached(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @NonNull Context context) {
        }

        public void onFragmentPreCreated(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @Nullable Bundle bundle) {
        }

        public void onFragmentResumed(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        public void onFragmentSaveInstanceState(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @NonNull Bundle bundle) {
        }

        public void onFragmentStarted(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        public void onFragmentStopped(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        public void onFragmentViewCreated(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @NonNull View view, @Nullable Bundle bundle) {
        }

        public void onFragmentViewDestroyed(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }
    }

    private static class LifecycleAwareResultListener implements FragmentResultListener {
        private final Lifecycle mLifecycle;
        private final FragmentResultListener mListener;
        private final LifecycleEventObserver mObserver;

        LifecycleAwareResultListener(Lifecycle lifecycle, FragmentResultListener fragmentResultListener, LifecycleEventObserver lifecycleEventObserver) {
            this.mLifecycle = lifecycle;
            this.mListener = fragmentResultListener;
            this.mObserver = lifecycleEventObserver;
        }

        public boolean isAtLeast(Lifecycle.State state) {
            return this.mLifecycle.getState().isAtLeast(state);
        }

        @Override // androidx.fragment.app.FragmentResultListener
        public void onFragmentResult(@NonNull String str, @NonNull Bundle bundle) {
            this.mListener.onFragmentResult(str, bundle);
        }

        public void removeObserver() {
            this.mLifecycle.removeObserver(this.mObserver);
        }
    }

    public interface OnBackStackChangedListener {
        @MainThread
        void onBackStackChangeCommitted(@NonNull Fragment fragment, boolean z2);

        @MainThread
        void onBackStackChangeStarted(@NonNull Fragment fragment, boolean z2);

        @MainThread
        void onBackStackChanged();
    }

    interface OpGenerator {
        boolean generateOps(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2);
    }

    private class PopBackStackState implements OpGenerator {

        /* renamed from: a, reason: collision with root package name */
        final String f4195a;

        /* renamed from: b, reason: collision with root package name */
        final int f4196b;

        /* renamed from: c, reason: collision with root package name */
        final int f4197c;

        PopBackStackState(String str, int i2, int i3) {
            this.f4195a = str;
            this.f4196b = i2;
            this.f4197c = i3;
        }

        @Override // androidx.fragment.app.FragmentManager.OpGenerator
        public boolean generateOps(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2) {
            Fragment fragment = FragmentManager.this.f4176c;
            if (fragment == null || this.f4196b >= 0 || this.f4195a != null || !fragment.getChildFragmentManager().popBackStackImmediate()) {
                return FragmentManager.this.u0(arrayList, arrayList2, this.f4195a, this.f4196b, this.f4197c);
            }
            return false;
        }
    }

    private class RestoreBackStackState implements OpGenerator {
        private final String mName;

        RestoreBackStackState(String str) {
            this.mName = str;
        }

        @Override // androidx.fragment.app.FragmentManager.OpGenerator
        public boolean generateOps(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2) {
            return FragmentManager.this.y0(arrayList, arrayList2, this.mName);
        }
    }

    private class SaveBackStackState implements OpGenerator {
        private final String mName;

        SaveBackStackState(String str) {
            this.mName = str;
        }

        @Override // androidx.fragment.app.FragmentManager.OpGenerator
        public boolean generateOps(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2) {
            return FragmentManager.this.F0(arrayList, arrayList2, this.mName);
        }
    }

    static int C0(int i2) {
        int i3 = 4097;
        if (i2 == 4097) {
            return 8194;
        }
        if (i2 != 8194) {
            i3 = 8197;
            if (i2 == 8197) {
                return 4100;
            }
            if (i2 == 4099) {
                return 4099;
            }
            if (i2 != 4100) {
                return 0;
            }
        }
        return i3;
    }

    static FragmentManager T(View view) {
        FragmentActivity fragmentActivity;
        Fragment fragmentU = U(view);
        if (fragmentU != null) {
            if (fragmentU.isAdded()) {
                return fragmentU.getChildFragmentManager();
            }
            throw new IllegalStateException("The Fragment " + fragmentU + " that owns View " + view + " has already been destroyed. Nested fragments should always use the child FragmentManager.");
        }
        Context context = view.getContext();
        while (true) {
            if (!(context instanceof ContextWrapper)) {
                fragmentActivity = null;
                break;
            }
            if (context instanceof FragmentActivity) {
                fragmentActivity = (FragmentActivity) context;
                break;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        if (fragmentActivity != null) {
            return fragmentActivity.getSupportFragmentManager();
        }
        throw new IllegalStateException("View " + view + " is not within a subclass of FragmentActivity.");
    }

    static Fragment U(View view) {
        while (view != null) {
            Fragment fragmentD0 = d0(view);
            if (fragmentD0 != null) {
                return fragmentD0;
            }
            Object parent = view.getParent();
            view = parent instanceof View ? (View) parent : null;
        }
        return null;
    }

    private void checkStateLoss() {
        if (isStateSaved()) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
    }

    private void cleanupExec() {
        this.mExecutingActions = false;
        this.mTmpIsPop.clear();
        this.mTmpRecords.clear();
    }

    private void clearBackStackStateViewModels() {
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        if (fragmentHostCallback instanceof ViewModelStoreOwner ? this.mFragmentStore.q().n() : fragmentHostCallback.b() instanceof Activity ? !((Activity) this.mHost.b()).isChangingConfigurations() : true) {
            Iterator<BackStackState> it = this.mBackStackStates.values().iterator();
            while (it.hasNext()) {
                Iterator it2 = it.next().f4109a.iterator();
                while (it2.hasNext()) {
                    this.mFragmentStore.q().g((String) it2.next(), false);
                }
            }
        }
    }

    private Set<SpecialEffectsController> collectAllSpecialEffectsController() {
        HashSet hashSet = new HashSet();
        Iterator it = this.mFragmentStore.l().iterator();
        while (it.hasNext()) {
            ViewGroup viewGroup = ((FragmentStateManager) it.next()).k().mContainer;
            if (viewGroup != null) {
                hashSet.add(SpecialEffectsController.getOrCreateController(viewGroup, c0()));
            }
        }
        return hashSet;
    }

    private Set<SpecialEffectsController> collectChangedControllers(@NonNull ArrayList<BackStackRecord> arrayList, int i2, int i3) {
        ViewGroup viewGroup;
        HashSet hashSet = new HashSet();
        while (i2 < i3) {
            Iterator it = arrayList.get(i2).f4231a.iterator();
            while (it.hasNext()) {
                Fragment fragment = ((FragmentTransaction.Op) it.next()).f4249b;
                if (fragment != null && (viewGroup = fragment.mContainer) != null) {
                    hashSet.add(SpecialEffectsController.getOrCreateController(viewGroup, this));
                }
            }
            i2++;
        }
        return hashSet;
    }

    static Fragment d0(View view) {
        Object tag = view.getTag(R.id.fragment_container_view_tag);
        if (tag instanceof Fragment) {
            return (Fragment) tag;
        }
        return null;
    }

    private void dispatchParentPrimaryNavigationFragmentChanged(@Nullable Fragment fragment) {
        if (fragment == null || !fragment.equals(R(fragment.mWho))) {
            return;
        }
        fragment.performPrimaryNavigationFragmentChanged();
    }

    private void dispatchStateChange(int i2) {
        try {
            this.mExecutingActions = true;
            this.mFragmentStore.d(i2);
            p0(i2, false);
            Iterator<SpecialEffectsController> it = collectAllSpecialEffectsController().iterator();
            while (it.hasNext()) {
                it.next().forceCompleteAllOperations();
            }
            this.mExecutingActions = false;
            P(true);
        } catch (Throwable th) {
            this.mExecutingActions = false;
            throw th;
        }
    }

    private void doPendingDeferredStart() {
        if (this.mHavePendingDeferredStart) {
            this.mHavePendingDeferredStart = false;
            startPendingDeferredFragments();
        }
    }

    @Deprecated
    public static void enableDebugLogging(boolean z2) {
        DEBUG = z2;
    }

    private void endAnimatingAwayFragments() {
        Iterator<SpecialEffectsController> it = collectAllSpecialEffectsController().iterator();
        while (it.hasNext()) {
            it.next().forceCompleteAllOperations();
        }
    }

    private void ensureExecReady(boolean z2) {
        if (this.mExecutingActions) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        }
        if (this.mHost == null) {
            if (!this.mDestroyed) {
                throw new IllegalStateException("FragmentManager has not been attached to a host.");
            }
            throw new IllegalStateException("FragmentManager has been destroyed");
        }
        if (Looper.myLooper() != this.mHost.getHandler().getLooper()) {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        }
        if (!z2) {
            checkStateLoss();
        }
        if (this.mTmpRecords == null) {
            this.mTmpRecords = new ArrayList<>();
            this.mTmpIsPop = new ArrayList<>();
        }
    }

    private static void executeOps(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2, int i2, int i3) {
        while (i2 < i3) {
            BackStackRecord backStackRecord = arrayList.get(i2);
            if (arrayList2.get(i2).booleanValue()) {
                backStackRecord.d(-1);
                backStackRecord.h();
            } else {
                backStackRecord.d(1);
                backStackRecord.g();
            }
            i2++;
        }
    }

    private void executeOpsTogether(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2, int i2, int i3) {
        ArrayList<OnBackStackChangedListener> arrayList3;
        boolean z2 = arrayList.get(i2).f4246p;
        ArrayList<Fragment> arrayList4 = this.mTmpAddedFragments;
        if (arrayList4 == null) {
            this.mTmpAddedFragments = new ArrayList<>();
        } else {
            arrayList4.clear();
        }
        this.mTmpAddedFragments.addAll(this.mFragmentStore.p());
        Fragment primaryNavigationFragment = getPrimaryNavigationFragment();
        boolean z3 = false;
        for (int i4 = i2; i4 < i3; i4++) {
            BackStackRecord backStackRecord = arrayList.get(i4);
            primaryNavigationFragment = !arrayList2.get(i4).booleanValue() ? backStackRecord.i(this.mTmpAddedFragments, primaryNavigationFragment) : backStackRecord.j(this.mTmpAddedFragments, primaryNavigationFragment);
            z3 = z3 || backStackRecord.f4237g;
        }
        this.mTmpAddedFragments.clear();
        if (!z2 && this.f4175b >= 1) {
            for (int i5 = i2; i5 < i3; i5++) {
                Iterator it = arrayList.get(i5).f4231a.iterator();
                while (it.hasNext()) {
                    Fragment fragment = ((FragmentTransaction.Op) it.next()).f4249b;
                    if (fragment != null && fragment.mFragmentManager != null) {
                        this.mFragmentStore.s(q(fragment));
                    }
                }
            }
        }
        executeOps(arrayList, arrayList2, i2, i3);
        boolean zBooleanValue = arrayList2.get(i3 - 1).booleanValue();
        if (z3 && (arrayList3 = this.mBackStackChangeListeners) != null && !arrayList3.isEmpty()) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            Iterator<BackStackRecord> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                linkedHashSet.addAll(fragmentsFromRecord(it2.next()));
            }
            Iterator<OnBackStackChangedListener> it3 = this.mBackStackChangeListeners.iterator();
            while (it3.hasNext()) {
                OnBackStackChangedListener next = it3.next();
                Iterator it4 = linkedHashSet.iterator();
                while (it4.hasNext()) {
                    next.onBackStackChangeStarted((Fragment) it4.next(), zBooleanValue);
                }
            }
            Iterator<OnBackStackChangedListener> it5 = this.mBackStackChangeListeners.iterator();
            while (it5.hasNext()) {
                OnBackStackChangedListener next2 = it5.next();
                Iterator it6 = linkedHashSet.iterator();
                while (it6.hasNext()) {
                    next2.onBackStackChangeCommitted((Fragment) it6.next(), zBooleanValue);
                }
            }
        }
        for (int i6 = i2; i6 < i3; i6++) {
            BackStackRecord backStackRecord2 = arrayList.get(i6);
            if (zBooleanValue) {
                for (int size = backStackRecord2.f4231a.size() - 1; size >= 0; size--) {
                    Fragment fragment2 = ((FragmentTransaction.Op) backStackRecord2.f4231a.get(size)).f4249b;
                    if (fragment2 != null) {
                        q(fragment2).l();
                    }
                }
            } else {
                Iterator it7 = backStackRecord2.f4231a.iterator();
                while (it7.hasNext()) {
                    Fragment fragment3 = ((FragmentTransaction.Op) it7.next()).f4249b;
                    if (fragment3 != null) {
                        q(fragment3).l();
                    }
                }
            }
        }
        p0(this.f4175b, true);
        for (SpecialEffectsController specialEffectsController : collectChangedControllers(arrayList, i2, i3)) {
            specialEffectsController.updateOperationDirection(zBooleanValue);
            specialEffectsController.markPostponedState();
            specialEffectsController.executePendingOperations();
        }
        while (i2 < i3) {
            BackStackRecord backStackRecord3 = arrayList.get(i2);
            if (arrayList2.get(i2).booleanValue() && backStackRecord3.f4093t >= 0) {
                backStackRecord3.f4093t = -1;
            }
            backStackRecord3.runOnCommitRunnables();
            i2++;
        }
        if (z3) {
            reportBackStackChanged();
        }
    }

    private int findBackStackIndex(@Nullable String str, int i2, boolean z2) {
        ArrayList arrayList = this.f4174a;
        if (arrayList == null || arrayList.isEmpty()) {
            return -1;
        }
        if (str == null && i2 < 0) {
            if (z2) {
                return 0;
            }
            return this.f4174a.size() - 1;
        }
        int size = this.f4174a.size() - 1;
        while (size >= 0) {
            BackStackRecord backStackRecord = (BackStackRecord) this.f4174a.get(size);
            if ((str != null && str.equals(backStackRecord.getName())) || (i2 >= 0 && i2 == backStackRecord.f4093t)) {
                break;
            }
            size--;
        }
        if (size < 0) {
            return size;
        }
        if (!z2) {
            if (size == this.f4174a.size() - 1) {
                return -1;
            }
            return size + 1;
        }
        while (size > 0) {
            BackStackRecord backStackRecord2 = (BackStackRecord) this.f4174a.get(size - 1);
            if ((str == null || !str.equals(backStackRecord2.getName())) && (i2 < 0 || i2 != backStackRecord2.f4093t)) {
                return size;
            }
            size--;
        }
        return size;
    }

    @NonNull
    public static <F extends Fragment> F findFragment(@NonNull View view) {
        F f2 = (F) U(view);
        if (f2 != null) {
            return f2;
        }
        throw new IllegalStateException("View " + view + " does not have a Fragment set");
    }

    private void forcePostponedTransactions() {
        Iterator<SpecialEffectsController> it = collectAllSpecialEffectsController().iterator();
        while (it.hasNext()) {
            it.next().forcePostponedExecutePendingOperations();
        }
    }

    private Set<Fragment> fragmentsFromRecord(@NonNull BackStackRecord backStackRecord) {
        HashSet hashSet = new HashSet();
        for (int i2 = 0; i2 < backStackRecord.f4231a.size(); i2++) {
            Fragment fragment = ((FragmentTransaction.Op) backStackRecord.f4231a.get(i2)).f4249b;
            if (fragment != null && backStackRecord.f4237g) {
                hashSet.add(fragment);
            }
        }
        return hashSet;
    }

    private boolean generateOpsForPendingActions(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2) {
        synchronized (this.mPendingActions) {
            if (this.mPendingActions.isEmpty()) {
                return false;
            }
            try {
                int size = this.mPendingActions.size();
                boolean zGenerateOps = false;
                for (int i2 = 0; i2 < size; i2++) {
                    zGenerateOps |= this.mPendingActions.get(i2).generateOps(arrayList, arrayList2);
                }
                return zGenerateOps;
            } finally {
                this.mPendingActions.clear();
                this.mHost.getHandler().removeCallbacks(this.mExecCommit);
            }
        }
    }

    @NonNull
    private FragmentManagerViewModel getChildNonConfig(@NonNull Fragment fragment) {
        return this.mNonConfig.i(fragment);
    }

    private ViewGroup getFragmentContainer(@NonNull Fragment fragment) {
        ViewGroup viewGroup = fragment.mContainer;
        if (viewGroup != null) {
            return viewGroup;
        }
        if (fragment.mContainerId > 0 && this.mContainer.onHasView()) {
            View viewOnFindViewById = this.mContainer.onFindViewById(fragment.mContainerId);
            if (viewOnFindViewById instanceof ViewGroup) {
                return (ViewGroup) viewOnFindViewById;
            }
        }
        return null;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static boolean isLoggingEnabled(int i2) {
        return DEBUG || Log.isLoggable(TAG, i2);
    }

    private boolean isMenuAvailable(@NonNull Fragment fragment) {
        return (fragment.mHasMenu && fragment.mMenuVisible) || fragment.mChildFragmentManager.o();
    }

    private boolean isParentAdded() {
        Fragment fragment = this.mParent;
        if (fragment == null) {
            return true;
        }
        return fragment.isAdded() && this.mParent.getParentFragmentManager().isParentAdded();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Configuration configuration) {
        if (isParentAdded()) {
            u(configuration, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(Integer num) {
        if (isParentAdded() && num.intValue() == 80) {
            A(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(MultiWindowModeChangedInfo multiWindowModeChangedInfo) {
        if (isParentAdded()) {
            B(multiWindowModeChangedInfo.getIsInMultiWindowMode(), false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(PictureInPictureModeChangedInfo pictureInPictureModeChangedInfo) {
        if (isParentAdded()) {
            H(pictureInPictureModeChangedInfo.getIsInPictureInPictureMode(), false);
        }
    }

    private void removeRedundantOperationsAndExecute(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2) {
        if (arrayList.isEmpty()) {
            return;
        }
        if (arrayList.size() != arrayList2.size()) {
            throw new IllegalStateException("Internal error with the back stack records");
        }
        int size = arrayList.size();
        int i2 = 0;
        int i3 = 0;
        while (i2 < size) {
            if (!arrayList.get(i2).f4246p) {
                if (i3 != i2) {
                    executeOpsTogether(arrayList, arrayList2, i3, i2);
                }
                i3 = i2 + 1;
                if (arrayList2.get(i2).booleanValue()) {
                    while (i3 < size && arrayList2.get(i3).booleanValue() && !arrayList.get(i3).f4246p) {
                        i3++;
                    }
                }
                executeOpsTogether(arrayList, arrayList2, i2, i3);
                i2 = i3 - 1;
            }
            i2++;
        }
        if (i3 != size) {
            executeOpsTogether(arrayList, arrayList2, i3, size);
        }
    }

    private void reportBackStackChanged() {
        if (this.mBackStackChangeListeners != null) {
            for (int i2 = 0; i2 < this.mBackStackChangeListeners.size(); i2++) {
                this.mBackStackChangeListeners.get(i2).onBackStackChanged();
            }
        }
    }

    private void setVisibleRemovingFragment(@NonNull Fragment fragment) {
        ViewGroup fragmentContainer = getFragmentContainer(fragment);
        if (fragmentContainer == null || fragment.getEnterAnim() + fragment.getExitAnim() + fragment.getPopEnterAnim() + fragment.getPopExitAnim() <= 0) {
            return;
        }
        if (fragmentContainer.getTag(R.id.visible_removing_fragment_view_tag) == null) {
            fragmentContainer.setTag(R.id.visible_removing_fragment_view_tag, fragment);
        }
        ((Fragment) fragmentContainer.getTag(R.id.visible_removing_fragment_view_tag)).setPopDirection(fragment.getPopDirection());
    }

    private void startPendingDeferredFragments() {
        Iterator it = this.mFragmentStore.l().iterator();
        while (it.hasNext()) {
            s0((FragmentStateManager) it.next());
        }
    }

    private void throwException(RuntimeException runtimeException) {
        Log.e(TAG, runtimeException.getMessage());
        Log.e(TAG, "Activity state:");
        PrintWriter printWriter = new PrintWriter(new LogWriter(TAG));
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null) {
            try {
                fragmentHostCallback.onDump("  ", null, printWriter, new String[0]);
                throw runtimeException;
            } catch (Exception e2) {
                Log.e(TAG, "Failed dumping state", e2);
                throw runtimeException;
            }
        }
        try {
            dump("  ", null, printWriter, new String[0]);
            throw runtimeException;
        } catch (Exception e3) {
            Log.e(TAG, "Failed dumping state", e3);
            throw runtimeException;
        }
    }

    private void updateOnBackPressedCallbackEnabled() {
        synchronized (this.mPendingActions) {
            try {
                if (this.mPendingActions.isEmpty()) {
                    this.mOnBackPressedCallback.setEnabled(getBackStackEntryCount() > 0 && k0(this.mParent));
                } else {
                    this.mOnBackPressedCallback.setEnabled(true);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    void A(boolean z2) {
        if (z2 && (this.mHost instanceof OnTrimMemoryProvider)) {
            throwException(new IllegalStateException("Do not call dispatchLowMemory() on host. Host implements OnTrimMemoryProvider and automatically dispatches low memory callbacks to fragments."));
        }
        for (Fragment fragment : this.mFragmentStore.p()) {
            if (fragment != null) {
                fragment.performLowMemory();
                if (z2) {
                    fragment.mChildFragmentManager.A(true);
                }
            }
        }
    }

    void A0(Parcelable parcelable) {
        FragmentStateManager fragmentStateManager;
        Bundle bundle;
        Bundle bundle2;
        if (parcelable == null) {
            return;
        }
        Bundle bundle3 = (Bundle) parcelable;
        for (String str : bundle3.keySet()) {
            if (str.startsWith(RESULT_KEY_PREFIX) && (bundle2 = bundle3.getBundle(str)) != null) {
                bundle2.setClassLoader(this.mHost.b().getClassLoader());
                this.mResults.put(str.substring(7), bundle2);
            }
        }
        HashMap map = new HashMap();
        for (String str2 : bundle3.keySet()) {
            if (str2.startsWith(FRAGMENT_KEY_PREFIX) && (bundle = bundle3.getBundle(str2)) != null) {
                bundle.setClassLoader(this.mHost.b().getClassLoader());
                map.put(str2.substring(9), bundle);
            }
        }
        this.mFragmentStore.y(map);
        FragmentManagerState fragmentManagerState = (FragmentManagerState) bundle3.getParcelable("state");
        if (fragmentManagerState == null) {
            return;
        }
        this.mFragmentStore.w();
        Iterator it = fragmentManagerState.f4201a.iterator();
        while (it.hasNext()) {
            Bundle bundleC = this.mFragmentStore.C((String) it.next(), null);
            if (bundleC != null) {
                Fragment fragmentH = this.mNonConfig.h(((FragmentState) bundleC.getParcelable("state")).f4210b);
                if (fragmentH != null) {
                    if (isLoggingEnabled(2)) {
                        Log.v(TAG, "restoreSaveState: re-attaching retained " + fragmentH);
                    }
                    fragmentStateManager = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, fragmentH, bundleC);
                } else {
                    fragmentStateManager = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, this.mHost.b().getClassLoader(), getFragmentFactory(), bundleC);
                }
                Fragment fragmentK = fragmentStateManager.k();
                fragmentK.mSavedFragmentState = bundleC;
                fragmentK.mFragmentManager = this;
                if (isLoggingEnabled(2)) {
                    Log.v(TAG, "restoreSaveState: active (" + fragmentK.mWho + "): " + fragmentK);
                }
                fragmentStateManager.n(this.mHost.b().getClassLoader());
                this.mFragmentStore.s(fragmentStateManager);
                fragmentStateManager.s(this.f4175b);
            }
        }
        for (Fragment fragment : this.mNonConfig.k()) {
            if (!this.mFragmentStore.c(fragment.mWho)) {
                if (isLoggingEnabled(2)) {
                    Log.v(TAG, "Discarding retained Fragment " + fragment + " that was not found in the set of active Fragments " + fragmentManagerState.f4201a);
                }
                this.mNonConfig.o(fragment);
                fragment.mFragmentManager = this;
                FragmentStateManager fragmentStateManager2 = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, fragment);
                fragmentStateManager2.s(1);
                fragmentStateManager2.l();
                fragment.mRemoving = true;
                fragmentStateManager2.l();
            }
        }
        this.mFragmentStore.x(fragmentManagerState.f4202b);
        if (fragmentManagerState.f4203c != null) {
            this.f4174a = new ArrayList(fragmentManagerState.f4203c.length);
            int i2 = 0;
            while (true) {
                BackStackRecordState[] backStackRecordStateArr = fragmentManagerState.f4203c;
                if (i2 >= backStackRecordStateArr.length) {
                    break;
                }
                BackStackRecord backStackRecordInstantiate = backStackRecordStateArr[i2].instantiate(this);
                if (isLoggingEnabled(2)) {
                    Log.v(TAG, "restoreAllState: back stack #" + i2 + " (index " + backStackRecordInstantiate.f4093t + "): " + backStackRecordInstantiate);
                    PrintWriter printWriter = new PrintWriter(new LogWriter(TAG));
                    backStackRecordInstantiate.dump("  ", printWriter, false);
                    printWriter.close();
                }
                this.f4174a.add(backStackRecordInstantiate);
                i2++;
            }
        } else {
            this.f4174a = null;
        }
        this.mBackStackIndex.set(fragmentManagerState.f4204d);
        String str3 = fragmentManagerState.f4205e;
        if (str3 != null) {
            Fragment fragmentR = R(str3);
            this.f4176c = fragmentR;
            dispatchParentPrimaryNavigationFragmentChanged(fragmentR);
        }
        ArrayList arrayList = fragmentManagerState.f4206f;
        if (arrayList != null) {
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                this.mBackStackStates.put((String) arrayList.get(i3), (BackStackState) fragmentManagerState.f4207g.get(i3));
            }
        }
        this.f4177d = new ArrayDeque(fragmentManagerState.f4208h);
    }

    void B(boolean z2, boolean z3) {
        if (z3 && (this.mHost instanceof OnMultiWindowModeChangedProvider)) {
            throwException(new IllegalStateException("Do not call dispatchMultiWindowModeChanged() on host. Host implements OnMultiWindowModeChangedProvider and automatically dispatches multi-window mode changes to fragments."));
        }
        for (Fragment fragment : this.mFragmentStore.p()) {
            if (fragment != null) {
                fragment.performMultiWindowModeChanged(z2);
                if (z3) {
                    fragment.mChildFragmentManager.B(z2, true);
                }
            }
        }
    }

    FragmentManagerNonConfig B0() {
        if (this.mHost instanceof ViewModelStoreOwner) {
            throwException(new IllegalStateException("You cannot use retainNonConfig when your FragmentHostCallback implements ViewModelStoreOwner."));
        }
        return this.mNonConfig.l();
    }

    void C(Fragment fragment) {
        Iterator<FragmentOnAttachListener> it = this.mOnAttachListeners.iterator();
        while (it.hasNext()) {
            it.next().onAttachFragment(this, fragment);
        }
    }

    void D() {
        for (Fragment fragment : this.mFragmentStore.m()) {
            if (fragment != null) {
                fragment.onHiddenChanged(fragment.isHidden());
                fragment.mChildFragmentManager.D();
            }
        }
    }

    Parcelable D0() {
        if (this.mHost instanceof SavedStateRegistryOwner) {
            throwException(new IllegalStateException("You cannot use saveAllState when your FragmentHostCallback implements SavedStateRegistryOwner."));
        }
        Bundle bundleLambda$attachController$4 = lambda$attachController$4();
        if (bundleLambda$attachController$4.isEmpty()) {
            return null;
        }
        return bundleLambda$attachController$4;
    }

    boolean E(MenuItem menuItem) {
        if (this.f4175b < 1) {
            return false;
        }
        for (Fragment fragment : this.mFragmentStore.p()) {
            if (fragment != null && fragment.performOptionsItemSelected(menuItem)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: E0, reason: merged with bridge method [inline-methods] */
    public Bundle lambda$attachController$4() {
        BackStackRecordState[] backStackRecordStateArr;
        int size;
        Bundle bundle = new Bundle();
        forcePostponedTransactions();
        endAnimatingAwayFragments();
        P(true);
        this.mStateSaved = true;
        this.mNonConfig.q(true);
        ArrayList arrayListZ = this.mFragmentStore.z();
        HashMap mapN = this.mFragmentStore.n();
        if (!mapN.isEmpty()) {
            ArrayList arrayListA = this.mFragmentStore.A();
            ArrayList arrayList = this.f4174a;
            if (arrayList == null || (size = arrayList.size()) <= 0) {
                backStackRecordStateArr = null;
            } else {
                backStackRecordStateArr = new BackStackRecordState[size];
                for (int i2 = 0; i2 < size; i2++) {
                    backStackRecordStateArr[i2] = new BackStackRecordState((BackStackRecord) this.f4174a.get(i2));
                    if (isLoggingEnabled(2)) {
                        Log.v(TAG, "saveAllState: adding back stack #" + i2 + ": " + this.f4174a.get(i2));
                    }
                }
            }
            FragmentManagerState fragmentManagerState = new FragmentManagerState();
            fragmentManagerState.f4201a = arrayListZ;
            fragmentManagerState.f4202b = arrayListA;
            fragmentManagerState.f4203c = backStackRecordStateArr;
            fragmentManagerState.f4204d = this.mBackStackIndex.get();
            Fragment fragment = this.f4176c;
            if (fragment != null) {
                fragmentManagerState.f4205e = fragment.mWho;
            }
            fragmentManagerState.f4206f.addAll(this.mBackStackStates.keySet());
            fragmentManagerState.f4207g.addAll(this.mBackStackStates.values());
            fragmentManagerState.f4208h = new ArrayList(this.f4177d);
            bundle.putParcelable("state", fragmentManagerState);
            for (String str : this.mResults.keySet()) {
                bundle.putBundle(RESULT_KEY_PREFIX + str, this.mResults.get(str));
            }
            for (String str2 : mapN.keySet()) {
                bundle.putBundle(FRAGMENT_KEY_PREFIX + str2, (Bundle) mapN.get(str2));
            }
        } else if (isLoggingEnabled(2)) {
            Log.v(TAG, "saveAllState: no fragments!");
        }
        return bundle;
    }

    void F(Menu menu) {
        if (this.f4175b < 1) {
            return;
        }
        for (Fragment fragment : this.mFragmentStore.p()) {
            if (fragment != null) {
                fragment.performOptionsMenuClosed(menu);
            }
        }
    }

    boolean F0(ArrayList arrayList, ArrayList arrayList2, String str) {
        int i2;
        int iFindBackStackIndex = findBackStackIndex(str, -1, true);
        if (iFindBackStackIndex < 0) {
            return false;
        }
        for (int i3 = iFindBackStackIndex; i3 < this.f4174a.size(); i3++) {
            BackStackRecord backStackRecord = (BackStackRecord) this.f4174a.get(i3);
            if (!backStackRecord.f4246p) {
                throwException(new IllegalArgumentException("saveBackStack(\"" + str + "\") included FragmentTransactions must use setReorderingAllowed(true) to ensure that the back stack can be restored as an atomic operation. Found " + backStackRecord + " that did not use setReorderingAllowed(true)."));
            }
        }
        HashSet hashSet = new HashSet();
        for (int i4 = iFindBackStackIndex; i4 < this.f4174a.size(); i4++) {
            BackStackRecord backStackRecord2 = (BackStackRecord) this.f4174a.get(i4);
            HashSet hashSet2 = new HashSet();
            HashSet hashSet3 = new HashSet();
            Iterator it = backStackRecord2.f4231a.iterator();
            while (it.hasNext()) {
                FragmentTransaction.Op op = (FragmentTransaction.Op) it.next();
                Fragment fragment = op.f4249b;
                if (fragment != null) {
                    if (!op.f4250c || (i2 = op.f4248a) == 1 || i2 == 2 || i2 == 8) {
                        hashSet.add(fragment);
                        hashSet2.add(fragment);
                    }
                    int i5 = op.f4248a;
                    if (i5 == 1 || i5 == 2) {
                        hashSet3.add(fragment);
                    }
                }
            }
            hashSet2.removeAll(hashSet3);
            if (!hashSet2.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                sb.append("saveBackStack(\"");
                sb.append(str);
                sb.append("\") must be self contained and not reference fragments from non-saved FragmentTransactions. Found reference to fragment");
                sb.append(hashSet2.size() == 1 ? " " + hashSet2.iterator().next() : "s " + hashSet2);
                sb.append(" in ");
                sb.append(backStackRecord2);
                sb.append(" that were previously added to the FragmentManager through a separate FragmentTransaction.");
                throwException(new IllegalArgumentException(sb.toString()));
            }
        }
        ArrayDeque arrayDeque = new ArrayDeque(hashSet);
        while (!arrayDeque.isEmpty()) {
            Fragment fragment2 = (Fragment) arrayDeque.removeFirst();
            if (fragment2.mRetainInstance) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("saveBackStack(\"");
                sb2.append(str);
                sb2.append("\") must not contain retained fragments. Found ");
                sb2.append(hashSet.contains(fragment2) ? "direct reference to retained " : "retained child ");
                sb2.append("fragment ");
                sb2.append(fragment2);
                throwException(new IllegalArgumentException(sb2.toString()));
            }
            for (Fragment fragment3 : fragment2.mChildFragmentManager.W()) {
                if (fragment3 != null) {
                    arrayDeque.addLast(fragment3);
                }
            }
        }
        ArrayList arrayList3 = new ArrayList();
        Iterator it2 = hashSet.iterator();
        while (it2.hasNext()) {
            arrayList3.add(((Fragment) it2.next()).mWho);
        }
        ArrayList arrayList4 = new ArrayList(this.f4174a.size() - iFindBackStackIndex);
        for (int i6 = iFindBackStackIndex; i6 < this.f4174a.size(); i6++) {
            arrayList4.add(null);
        }
        BackStackState backStackState = new BackStackState(arrayList3, arrayList4);
        for (int size = this.f4174a.size() - 1; size >= iFindBackStackIndex; size--) {
            BackStackRecord backStackRecord3 = (BackStackRecord) this.f4174a.remove(size);
            BackStackRecord backStackRecord4 = new BackStackRecord(backStackRecord3);
            backStackRecord4.e();
            arrayList4.set(size - iFindBackStackIndex, new BackStackRecordState(backStackRecord4));
            backStackRecord3.f4094u = true;
            arrayList.add(backStackRecord3);
            arrayList2.add(Boolean.TRUE);
        }
        this.mBackStackStates.put(str, backStackState);
        return true;
    }

    void G() {
        dispatchStateChange(5);
    }

    void G0() {
        synchronized (this.mPendingActions) {
            try {
                if (this.mPendingActions.size() == 1) {
                    this.mHost.getHandler().removeCallbacks(this.mExecCommit);
                    this.mHost.getHandler().post(this.mExecCommit);
                    updateOnBackPressedCallbackEnabled();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    void H(boolean z2, boolean z3) {
        if (z3 && (this.mHost instanceof OnPictureInPictureModeChangedProvider)) {
            throwException(new IllegalStateException("Do not call dispatchPictureInPictureModeChanged() on host. Host implements OnPictureInPictureModeChangedProvider and automatically dispatches picture-in-picture mode changes to fragments."));
        }
        for (Fragment fragment : this.mFragmentStore.p()) {
            if (fragment != null) {
                fragment.performPictureInPictureModeChanged(z2);
                if (z3) {
                    fragment.mChildFragmentManager.H(z2, true);
                }
            }
        }
    }

    void H0(Fragment fragment, boolean z2) {
        ViewGroup fragmentContainer = getFragmentContainer(fragment);
        if (fragmentContainer == null || !(fragmentContainer instanceof FragmentContainerView)) {
            return;
        }
        ((FragmentContainerView) fragmentContainer).setDrawDisappearingViewsLast(!z2);
    }

    boolean I(Menu menu) {
        boolean z2 = false;
        if (this.f4175b < 1) {
            return false;
        }
        for (Fragment fragment : this.mFragmentStore.p()) {
            if (fragment != null && j0(fragment) && fragment.performPrepareOptionsMenu(menu)) {
                z2 = true;
            }
        }
        return z2;
    }

    void I0(Fragment fragment, Lifecycle.State state) {
        if (fragment.equals(R(fragment.mWho)) && (fragment.mHost == null || fragment.mFragmentManager == this)) {
            fragment.mMaxState = state;
            return;
        }
        throw new IllegalArgumentException("Fragment " + fragment + " is not an active fragment of FragmentManager " + this);
    }

    void J() {
        updateOnBackPressedCallbackEnabled();
        dispatchParentPrimaryNavigationFragmentChanged(this.f4176c);
    }

    void J0(Fragment fragment) {
        if (fragment == null || (fragment.equals(R(fragment.mWho)) && (fragment.mHost == null || fragment.mFragmentManager == this))) {
            Fragment fragment2 = this.f4176c;
            this.f4176c = fragment;
            dispatchParentPrimaryNavigationFragmentChanged(fragment2);
            dispatchParentPrimaryNavigationFragmentChanged(this.f4176c);
            return;
        }
        throw new IllegalArgumentException("Fragment " + fragment + " is not an active fragment of FragmentManager " + this);
    }

    void K() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.q(false);
        dispatchStateChange(7);
    }

    void K0(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "show: " + fragment);
        }
        if (fragment.mHidden) {
            fragment.mHidden = false;
            fragment.mHiddenChanged = !fragment.mHiddenChanged;
        }
    }

    void L() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.q(false);
        dispatchStateChange(5);
    }

    void M() {
        this.mStopped = true;
        this.mNonConfig.q(true);
        dispatchStateChange(4);
    }

    void N() {
        dispatchStateChange(2);
    }

    void O(OpGenerator opGenerator, boolean z2) {
        if (!z2) {
            if (this.mHost == null) {
                if (!this.mDestroyed) {
                    throw new IllegalStateException("FragmentManager has not been attached to a host.");
                }
                throw new IllegalStateException("FragmentManager has been destroyed");
            }
            checkStateLoss();
        }
        synchronized (this.mPendingActions) {
            try {
                if (this.mHost == null) {
                    if (!z2) {
                        throw new IllegalStateException("Activity has been destroyed");
                    }
                } else {
                    this.mPendingActions.add(opGenerator);
                    G0();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    boolean P(boolean z2) {
        ensureExecReady(z2);
        boolean z3 = false;
        while (generateOpsForPendingActions(this.mTmpRecords, this.mTmpIsPop)) {
            z3 = true;
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        updateOnBackPressedCallbackEnabled();
        doPendingDeferredStart();
        this.mFragmentStore.b();
        return z3;
    }

    void Q(OpGenerator opGenerator, boolean z2) {
        if (z2 && (this.mHost == null || this.mDestroyed)) {
            return;
        }
        ensureExecReady(z2);
        if (opGenerator.generateOps(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        updateOnBackPressedCallbackEnabled();
        doPendingDeferredStart();
        this.mFragmentStore.b();
    }

    Fragment R(String str) {
        return this.mFragmentStore.f(str);
    }

    Fragment S(String str) {
        return this.mFragmentStore.i(str);
    }

    int V() {
        return this.mFragmentStore.k();
    }

    List W() {
        return this.mFragmentStore.m();
    }

    FragmentContainer X() {
        return this.mContainer;
    }

    FragmentStore Y() {
        return this.mFragmentStore;
    }

    LayoutInflater.Factory2 Z() {
        return this.mLayoutInflaterFactory;
    }

    FragmentLifecycleCallbacksDispatcher a0() {
        return this.mLifecycleCallbacksDispatcher;
    }

    public void addFragmentOnAttachListener(@NonNull FragmentOnAttachListener fragmentOnAttachListener) {
        this.mOnAttachListeners.add(fragmentOnAttachListener);
    }

    public void addOnBackStackChangedListener(@NonNull OnBackStackChangedListener onBackStackChangedListener) {
        if (this.mBackStackChangeListeners == null) {
            this.mBackStackChangeListeners = new ArrayList<>();
        }
        this.mBackStackChangeListeners.add(onBackStackChangedListener);
    }

    Fragment b0() {
        return this.mParent;
    }

    @NonNull
    public FragmentTransaction beginTransaction() {
        return new BackStackRecord(this);
    }

    SpecialEffectsControllerFactory c0() {
        SpecialEffectsControllerFactory specialEffectsControllerFactory = this.mSpecialEffectsControllerFactory;
        if (specialEffectsControllerFactory != null) {
            return specialEffectsControllerFactory;
        }
        Fragment fragment = this.mParent;
        return fragment != null ? fragment.mFragmentManager.c0() : this.mDefaultSpecialEffectsControllerFactory;
    }

    public void clearBackStack(@NonNull String str) {
        O(new ClearBackStackState(str), false);
    }

    @Override // androidx.fragment.app.FragmentResultOwner
    public final void clearFragmentResult(@NonNull String str) {
        this.mResults.remove(str);
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "Clearing fragment result with key " + str);
        }
    }

    @Override // androidx.fragment.app.FragmentResultOwner
    public final void clearFragmentResultListener(@NonNull String str) {
        LifecycleAwareResultListener lifecycleAwareResultListenerRemove = this.mResultListeners.remove(str);
        if (lifecycleAwareResultListenerRemove != null) {
            lifecycleAwareResultListenerRemove.removeObserver();
        }
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "Clearing FragmentResultListener for key " + str);
        }
    }

    public void dump(@NonNull String str, @Nullable FileDescriptor fileDescriptor, @NonNull PrintWriter printWriter, @Nullable String[] strArr) {
        int size;
        int size2;
        String str2 = str + "    ";
        this.mFragmentStore.e(str, fileDescriptor, printWriter, strArr);
        ArrayList<Fragment> arrayList = this.mCreatedMenus;
        if (arrayList != null && (size2 = arrayList.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Fragments Created Menus:");
            for (int i2 = 0; i2 < size2; i2++) {
                Fragment fragment = this.mCreatedMenus.get(i2);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.println(fragment.toString());
            }
        }
        ArrayList arrayList2 = this.f4174a;
        if (arrayList2 != null && (size = arrayList2.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Back Stack:");
            for (int i3 = 0; i3 < size; i3++) {
                BackStackRecord backStackRecord = (BackStackRecord) this.f4174a.get(i3);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i3);
                printWriter.print(": ");
                printWriter.println(backStackRecord.toString());
                backStackRecord.dump(str2, printWriter);
            }
        }
        printWriter.print(str);
        printWriter.println("Back Stack Index: " + this.mBackStackIndex.get());
        synchronized (this.mPendingActions) {
            try {
                int size3 = this.mPendingActions.size();
                if (size3 > 0) {
                    printWriter.print(str);
                    printWriter.println("Pending Actions:");
                    for (int i4 = 0; i4 < size3; i4++) {
                        OpGenerator opGenerator = this.mPendingActions.get(i4);
                        printWriter.print(str);
                        printWriter.print("  #");
                        printWriter.print(i4);
                        printWriter.print(": ");
                        printWriter.println(opGenerator);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        printWriter.print(str);
        printWriter.println("FragmentManager misc state:");
        printWriter.print(str);
        printWriter.print("  mHost=");
        printWriter.println(this.mHost);
        printWriter.print(str);
        printWriter.print("  mContainer=");
        printWriter.println(this.mContainer);
        if (this.mParent != null) {
            printWriter.print(str);
            printWriter.print("  mParent=");
            printWriter.println(this.mParent);
        }
        printWriter.print(str);
        printWriter.print("  mCurState=");
        printWriter.print(this.f4175b);
        printWriter.print(" mStateSaved=");
        printWriter.print(this.mStateSaved);
        printWriter.print(" mStopped=");
        printWriter.print(this.mStopped);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.mDestroyed);
        if (this.mNeedMenuInvalidate) {
            printWriter.print(str);
            printWriter.print("  mNeedMenuInvalidate=");
            printWriter.println(this.mNeedMenuInvalidate);
        }
    }

    ViewModelStore e0(Fragment fragment) {
        return this.mNonConfig.m(fragment);
    }

    @MainThread
    public boolean executePendingTransactions() {
        boolean zP = P(true);
        forcePostponedTransactions();
        return zP;
    }

    void f0() {
        P(true);
        if (this.mOnBackPressedCallback.getIsEnabled()) {
            popBackStackImmediate();
        } else {
            this.mOnBackPressedDispatcher.onBackPressed();
        }
    }

    @Nullable
    public Fragment findFragmentById(@IdRes int i2) {
        return this.mFragmentStore.g(i2);
    }

    @Nullable
    public Fragment findFragmentByTag(@Nullable String str) {
        return this.mFragmentStore.h(str);
    }

    void g0(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "hide: " + fragment);
        }
        if (fragment.mHidden) {
            return;
        }
        fragment.mHidden = true;
        fragment.mHiddenChanged = true ^ fragment.mHiddenChanged;
        setVisibleRemovingFragment(fragment);
    }

    @NonNull
    public BackStackEntry getBackStackEntryAt(int i2) {
        return (BackStackEntry) this.f4174a.get(i2);
    }

    public int getBackStackEntryCount() {
        ArrayList arrayList = this.f4174a;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    @Nullable
    public Fragment getFragment(@NonNull Bundle bundle, @NonNull String str) {
        String string = bundle.getString(str);
        if (string == null) {
            return null;
        }
        Fragment fragmentR = R(string);
        if (fragmentR == null) {
            throwException(new IllegalStateException("Fragment no longer exists for key " + str + ": unique id " + string));
        }
        return fragmentR;
    }

    @NonNull
    public FragmentFactory getFragmentFactory() {
        FragmentFactory fragmentFactory = this.mFragmentFactory;
        if (fragmentFactory != null) {
            return fragmentFactory;
        }
        Fragment fragment = this.mParent;
        return fragment != null ? fragment.mFragmentManager.getFragmentFactory() : this.mHostFragmentFactory;
    }

    @NonNull
    public List<Fragment> getFragments() {
        return this.mFragmentStore.p();
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public FragmentHostCallback<?> getHost() {
        return this.mHost;
    }

    @Nullable
    public Fragment getPrimaryNavigationFragment() {
        return this.f4176c;
    }

    @Nullable
    public FragmentStrictMode.Policy getStrictModePolicy() {
        return this.mStrictModePolicy;
    }

    void h0(Fragment fragment) {
        if (fragment.mAdded && isMenuAvailable(fragment)) {
            this.mNeedMenuInvalidate = true;
        }
    }

    void i(BackStackRecord backStackRecord) {
        if (this.f4174a == null) {
            this.f4174a = new ArrayList();
        }
        this.f4174a.add(backStackRecord);
    }

    boolean i0(Fragment fragment) {
        if (fragment == null) {
            return false;
        }
        return fragment.isHidden();
    }

    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    public boolean isStateSaved() {
        return this.mStateSaved || this.mStopped;
    }

    FragmentStateManager j(Fragment fragment) {
        String str = fragment.mPreviousWho;
        if (str != null) {
            FragmentStrictMode.onFragmentReuse(fragment, str);
        }
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "add: " + fragment);
        }
        FragmentStateManager fragmentStateManagerQ = q(fragment);
        fragment.mFragmentManager = this;
        this.mFragmentStore.s(fragmentStateManagerQ);
        if (!fragment.mDetached) {
            this.mFragmentStore.a(fragment);
            fragment.mRemoving = false;
            if (fragment.mView == null) {
                fragment.mHiddenChanged = false;
            }
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
        }
        return fragmentStateManagerQ;
    }

    boolean j0(Fragment fragment) {
        if (fragment == null) {
            return true;
        }
        return fragment.isMenuVisible();
    }

    void k(Fragment fragment) {
        this.mNonConfig.e(fragment);
    }

    boolean k0(Fragment fragment) {
        if (fragment == null) {
            return true;
        }
        FragmentManager fragmentManager = fragment.mFragmentManager;
        return fragment.equals(fragmentManager.getPrimaryNavigationFragment()) && k0(fragmentManager.mParent);
    }

    int l() {
        return this.mBackStackIndex.getAndIncrement();
    }

    boolean l0(int i2) {
        return this.f4175b >= i2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    void m(FragmentHostCallback fragmentHostCallback, FragmentContainer fragmentContainer, final Fragment fragment) {
        String str;
        if (this.mHost != null) {
            throw new IllegalStateException("Already attached");
        }
        this.mHost = fragmentHostCallback;
        this.mContainer = fragmentContainer;
        this.mParent = fragment;
        if (fragment != null) {
            addFragmentOnAttachListener(new FragmentOnAttachListener() { // from class: androidx.fragment.app.FragmentManager.7
                @Override // androidx.fragment.app.FragmentOnAttachListener
                public void onAttachFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment2) {
                    fragment.onAttachFragment(fragment2);
                }
            });
        } else if (fragmentHostCallback instanceof FragmentOnAttachListener) {
            addFragmentOnAttachListener((FragmentOnAttachListener) fragmentHostCallback);
        }
        if (this.mParent != null) {
            updateOnBackPressedCallbackEnabled();
        }
        if (fragmentHostCallback instanceof OnBackPressedDispatcherOwner) {
            OnBackPressedDispatcherOwner onBackPressedDispatcherOwner = (OnBackPressedDispatcherOwner) fragmentHostCallback;
            OnBackPressedDispatcher onBackPressedDispatcher = onBackPressedDispatcherOwner.getOnBackPressedDispatcher();
            this.mOnBackPressedDispatcher = onBackPressedDispatcher;
            LifecycleOwner lifecycleOwner = onBackPressedDispatcherOwner;
            if (fragment != null) {
                lifecycleOwner = fragment;
            }
            onBackPressedDispatcher.addCallback(lifecycleOwner, this.mOnBackPressedCallback);
        }
        if (fragment != null) {
            this.mNonConfig = fragment.mFragmentManager.getChildNonConfig(fragment);
        } else if (fragmentHostCallback instanceof ViewModelStoreOwner) {
            this.mNonConfig = FragmentManagerViewModel.j(((ViewModelStoreOwner) fragmentHostCallback).getViewModelStore());
        } else {
            this.mNonConfig = new FragmentManagerViewModel(false);
        }
        this.mNonConfig.q(isStateSaved());
        this.mFragmentStore.B(this.mNonConfig);
        Object obj = this.mHost;
        if ((obj instanceof SavedStateRegistryOwner) && fragment == null) {
            SavedStateRegistry savedStateRegistry = ((SavedStateRegistryOwner) obj).getSavedStateRegistry();
            savedStateRegistry.registerSavedStateProvider(SAVED_STATE_KEY, new SavedStateRegistry.SavedStateProvider() { // from class: androidx.fragment.app.s
                @Override // androidx.savedstate.SavedStateRegistry.SavedStateProvider
                public final Bundle saveState() {
                    return this.f4321a.lambda$attachController$4();
                }
            });
            Bundle bundleConsumeRestoredStateForKey = savedStateRegistry.consumeRestoredStateForKey(SAVED_STATE_KEY);
            if (bundleConsumeRestoredStateForKey != null) {
                A0(bundleConsumeRestoredStateForKey);
            }
        }
        Object obj2 = this.mHost;
        if (obj2 instanceof ActivityResultRegistryOwner) {
            ActivityResultRegistry activityResultRegistry = ((ActivityResultRegistryOwner) obj2).getActivityResultRegistry();
            if (fragment != null) {
                str = fragment.mWho + ":";
            } else {
                str = "";
            }
            String str2 = "FragmentManager:" + str;
            this.mStartActivityForResult = activityResultRegistry.register(str2 + "StartActivityForResult", new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: androidx.fragment.app.FragmentManager.8
                @Override // androidx.activity.result.ActivityResultCallback
                public void onActivityResult(ActivityResult activityResult) {
                    LaunchedFragmentInfo launchedFragmentInfo = (LaunchedFragmentInfo) FragmentManager.this.f4177d.pollLast();
                    if (launchedFragmentInfo == null) {
                        Log.w(FragmentManager.TAG, "No Activities were started for result for " + this);
                        return;
                    }
                    String str3 = launchedFragmentInfo.f4193a;
                    int i2 = launchedFragmentInfo.f4194b;
                    Fragment fragmentI = FragmentManager.this.mFragmentStore.i(str3);
                    if (fragmentI != null) {
                        fragmentI.onActivityResult(i2, activityResult.getResultCode(), activityResult.getData());
                        return;
                    }
                    Log.w(FragmentManager.TAG, "Activity result delivered for unknown Fragment " + str3);
                }
            });
            this.mStartIntentSenderForResult = activityResultRegistry.register(str2 + "StartIntentSenderForResult", new FragmentIntentSenderContract(), new ActivityResultCallback<ActivityResult>() { // from class: androidx.fragment.app.FragmentManager.9
                @Override // androidx.activity.result.ActivityResultCallback
                public void onActivityResult(ActivityResult activityResult) {
                    LaunchedFragmentInfo launchedFragmentInfo = (LaunchedFragmentInfo) FragmentManager.this.f4177d.pollFirst();
                    if (launchedFragmentInfo == null) {
                        Log.w(FragmentManager.TAG, "No IntentSenders were started for " + this);
                        return;
                    }
                    String str3 = launchedFragmentInfo.f4193a;
                    int i2 = launchedFragmentInfo.f4194b;
                    Fragment fragmentI = FragmentManager.this.mFragmentStore.i(str3);
                    if (fragmentI != null) {
                        fragmentI.onActivityResult(i2, activityResult.getResultCode(), activityResult.getData());
                        return;
                    }
                    Log.w(FragmentManager.TAG, "Intent Sender result delivered for unknown Fragment " + str3);
                }
            });
            this.mRequestPermissions = activityResultRegistry.register(str2 + "RequestPermissions", new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() { // from class: androidx.fragment.app.FragmentManager.10
                @Override // androidx.activity.result.ActivityResultCallback
                @SuppressLint({"SyntheticAccessor"})
                public void onActivityResult(Map<String, Boolean> map) {
                    String[] strArr = (String[]) map.keySet().toArray(new String[0]);
                    ArrayList arrayList = new ArrayList(map.values());
                    int[] iArr = new int[arrayList.size()];
                    for (int i2 = 0; i2 < arrayList.size(); i2++) {
                        iArr[i2] = ((Boolean) arrayList.get(i2)).booleanValue() ? 0 : -1;
                    }
                    LaunchedFragmentInfo launchedFragmentInfo = (LaunchedFragmentInfo) FragmentManager.this.f4177d.pollFirst();
                    if (launchedFragmentInfo == null) {
                        Log.w(FragmentManager.TAG, "No permissions were requested for " + this);
                        return;
                    }
                    String str3 = launchedFragmentInfo.f4193a;
                    int i3 = launchedFragmentInfo.f4194b;
                    Fragment fragmentI = FragmentManager.this.mFragmentStore.i(str3);
                    if (fragmentI != null) {
                        fragmentI.onRequestPermissionsResult(i3, strArr, iArr);
                        return;
                    }
                    Log.w(FragmentManager.TAG, "Permission request result delivered for unknown Fragment " + str3);
                }
            });
        }
        Object obj3 = this.mHost;
        if (obj3 instanceof OnConfigurationChangedProvider) {
            ((OnConfigurationChangedProvider) obj3).addOnConfigurationChangedListener(this.mOnConfigurationChangedListener);
        }
        Object obj4 = this.mHost;
        if (obj4 instanceof OnTrimMemoryProvider) {
            ((OnTrimMemoryProvider) obj4).addOnTrimMemoryListener(this.mOnTrimMemoryListener);
        }
        Object obj5 = this.mHost;
        if (obj5 instanceof OnMultiWindowModeChangedProvider) {
            ((OnMultiWindowModeChangedProvider) obj5).addOnMultiWindowModeChangedListener(this.mOnMultiWindowModeChangedListener);
        }
        Object obj6 = this.mHost;
        if (obj6 instanceof OnPictureInPictureModeChangedProvider) {
            ((OnPictureInPictureModeChangedProvider) obj6).addOnPictureInPictureModeChangedListener(this.mOnPictureInPictureModeChangedListener);
        }
        Object obj7 = this.mHost;
        if ((obj7 instanceof MenuHost) && fragment == null) {
            ((MenuHost) obj7).addMenuProvider(this.mMenuProvider);
        }
    }

    void m0(Fragment fragment, String[] strArr, int i2) {
        if (this.mRequestPermissions == null) {
            this.mHost.onRequestPermissionsFromFragment(fragment, strArr, i2);
            return;
        }
        this.f4177d.addLast(new LaunchedFragmentInfo(fragment.mWho, i2));
        this.mRequestPermissions.launch(strArr);
    }

    void n(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "attach: " + fragment);
        }
        if (fragment.mDetached) {
            fragment.mDetached = false;
            if (fragment.mAdded) {
                return;
            }
            this.mFragmentStore.a(fragment);
            if (isLoggingEnabled(2)) {
                Log.v(TAG, "add from attach: " + fragment);
            }
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
        }
    }

    void n0(Fragment fragment, Intent intent, int i2, Bundle bundle) {
        if (this.mStartActivityForResult == null) {
            this.mHost.onStartActivityFromFragment(fragment, intent, i2, bundle);
            return;
        }
        this.f4177d.addLast(new LaunchedFragmentInfo(fragment.mWho, i2));
        if (bundle != null) {
            intent.putExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE, bundle);
        }
        this.mStartActivityForResult.launch(intent);
    }

    boolean o() {
        boolean zIsMenuAvailable = false;
        for (Fragment fragment : this.mFragmentStore.m()) {
            if (fragment != null) {
                zIsMenuAvailable = isMenuAvailable(fragment);
            }
            if (zIsMenuAvailable) {
                return true;
            }
        }
        return false;
    }

    void o0(Fragment fragment, IntentSender intentSender, int i2, Intent intent, int i3, int i4, int i5, Bundle bundle) {
        Intent intent2;
        if (this.mStartIntentSenderForResult == null) {
            this.mHost.onStartIntentSenderFromFragment(fragment, intentSender, i2, intent, i3, i4, i5, bundle);
            return;
        }
        if (bundle != null) {
            if (intent == null) {
                intent2 = new Intent();
                intent2.putExtra(EXTRA_CREATED_FILLIN_INTENT, true);
            } else {
                intent2 = intent;
            }
            if (isLoggingEnabled(2)) {
                Log.v(TAG, "ActivityOptions " + bundle + " were added to fillInIntent " + intent2 + " for fragment " + fragment);
            }
            intent2.putExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE, bundle);
        } else {
            intent2 = intent;
        }
        IntentSenderRequest intentSenderRequestBuild = new IntentSenderRequest.Builder(intentSender).setFillInIntent(intent2).setFlags(i4, i3).build();
        this.f4177d.addLast(new LaunchedFragmentInfo(fragment.mWho, i2));
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "Fragment " + fragment + "is launching an IntentSender for result ");
        }
        this.mStartIntentSenderForResult.launch(intentSenderRequestBuild);
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Deprecated
    public FragmentTransaction openTransaction() {
        return beginTransaction();
    }

    boolean p(ArrayList arrayList, ArrayList arrayList2, String str) {
        if (y0(arrayList, arrayList2, str)) {
            return u0(arrayList, arrayList2, str, -1, 1);
        }
        return false;
    }

    void p0(int i2, boolean z2) {
        FragmentHostCallback<?> fragmentHostCallback;
        if (this.mHost == null && i2 != -1) {
            throw new IllegalStateException("No activity");
        }
        if (z2 || i2 != this.f4175b) {
            this.f4175b = i2;
            this.mFragmentStore.u();
            startPendingDeferredFragments();
            if (this.mNeedMenuInvalidate && (fragmentHostCallback = this.mHost) != null && this.f4175b == 7) {
                fragmentHostCallback.onSupportInvalidateOptionsMenu();
                this.mNeedMenuInvalidate = false;
            }
        }
    }

    public void popBackStack() {
        O(new PopBackStackState(null, -1, 0), false);
    }

    @MainThread
    public boolean popBackStackImmediate() {
        return popBackStackImmediate(null, -1, 0);
    }

    public void putFragment(@NonNull Bundle bundle, @NonNull String str, @NonNull Fragment fragment) {
        if (fragment.mFragmentManager != this) {
            throwException(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        bundle.putString(str, fragment.mWho);
    }

    FragmentStateManager q(Fragment fragment) {
        FragmentStateManager fragmentStateManagerO = this.mFragmentStore.o(fragment.mWho);
        if (fragmentStateManagerO != null) {
            return fragmentStateManagerO;
        }
        FragmentStateManager fragmentStateManager = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, fragment);
        fragmentStateManager.n(this.mHost.b().getClassLoader());
        fragmentStateManager.s(this.f4175b);
        return fragmentStateManager;
    }

    void q0() {
        if (this.mHost == null) {
            return;
        }
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.q(false);
        for (Fragment fragment : this.mFragmentStore.p()) {
            if (fragment != null) {
                fragment.noteStateNotSaved();
            }
        }
    }

    void r(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "detach: " + fragment);
        }
        if (fragment.mDetached) {
            return;
        }
        fragment.mDetached = true;
        if (fragment.mAdded) {
            if (isLoggingEnabled(2)) {
                Log.v(TAG, "remove from detach: " + fragment);
            }
            this.mFragmentStore.v(fragment);
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
            setVisibleRemovingFragment(fragment);
        }
    }

    void r0(FragmentContainerView fragmentContainerView) {
        View view;
        for (FragmentStateManager fragmentStateManager : this.mFragmentStore.l()) {
            Fragment fragmentK = fragmentStateManager.k();
            if (fragmentK.mContainerId == fragmentContainerView.getId() && (view = fragmentK.mView) != null && view.getParent() == null) {
                fragmentK.mContainer = fragmentContainerView;
                fragmentStateManager.b();
            }
        }
    }

    public void registerFragmentLifecycleCallbacks(@NonNull FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean z2) {
        this.mLifecycleCallbacksDispatcher.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, z2);
    }

    public void removeFragmentOnAttachListener(@NonNull FragmentOnAttachListener fragmentOnAttachListener) {
        this.mOnAttachListeners.remove(fragmentOnAttachListener);
    }

    public void removeOnBackStackChangedListener(@NonNull OnBackStackChangedListener onBackStackChangedListener) {
        ArrayList<OnBackStackChangedListener> arrayList = this.mBackStackChangeListeners;
        if (arrayList != null) {
            arrayList.remove(onBackStackChangedListener);
        }
    }

    public void restoreBackStack(@NonNull String str) {
        O(new RestoreBackStackState(str), false);
    }

    void s() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.q(false);
        dispatchStateChange(4);
    }

    void s0(FragmentStateManager fragmentStateManager) {
        Fragment fragmentK = fragmentStateManager.k();
        if (fragmentK.mDeferStart) {
            if (this.mExecutingActions) {
                this.mHavePendingDeferredStart = true;
            } else {
                fragmentK.mDeferStart = false;
                fragmentStateManager.l();
            }
        }
    }

    public void saveBackStack(@NonNull String str) {
        O(new SaveBackStackState(str), false);
    }

    @Nullable
    public Fragment.SavedState saveFragmentInstanceState(@NonNull Fragment fragment) {
        FragmentStateManager fragmentStateManagerO = this.mFragmentStore.o(fragment.mWho);
        if (fragmentStateManagerO == null || !fragmentStateManagerO.k().equals(fragment)) {
            throwException(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        return fragmentStateManagerO.p();
    }

    public void setFragmentFactory(@NonNull FragmentFactory fragmentFactory) {
        this.mFragmentFactory = fragmentFactory;
    }

    @Override // androidx.fragment.app.FragmentResultOwner
    public final void setFragmentResult(@NonNull String str, @NonNull Bundle bundle) {
        LifecycleAwareResultListener lifecycleAwareResultListener = this.mResultListeners.get(str);
        if (lifecycleAwareResultListener == null || !lifecycleAwareResultListener.isAtLeast(Lifecycle.State.STARTED)) {
            this.mResults.put(str, bundle);
        } else {
            lifecycleAwareResultListener.onFragmentResult(str, bundle);
        }
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "Setting fragment result with key " + str + " and result " + bundle);
        }
    }

    @Override // androidx.fragment.app.FragmentResultOwner
    @SuppressLint({"SyntheticAccessor"})
    public final void setFragmentResultListener(@NonNull final String str, @NonNull LifecycleOwner lifecycleOwner, @NonNull final FragmentResultListener fragmentResultListener) {
        final Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        if (lifecycle.getState() == Lifecycle.State.DESTROYED) {
            return;
        }
        LifecycleEventObserver lifecycleEventObserver = new LifecycleEventObserver() { // from class: androidx.fragment.app.FragmentManager.6
            @Override // androidx.lifecycle.LifecycleEventObserver
            public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner2, @NonNull Lifecycle.Event event) {
                Bundle bundle;
                if (event == Lifecycle.Event.ON_START && (bundle = (Bundle) FragmentManager.this.mResults.get(str)) != null) {
                    fragmentResultListener.onFragmentResult(str, bundle);
                    FragmentManager.this.clearFragmentResult(str);
                }
                if (event == Lifecycle.Event.ON_DESTROY) {
                    lifecycle.removeObserver(this);
                    FragmentManager.this.mResultListeners.remove(str);
                }
            }
        };
        LifecycleAwareResultListener lifecycleAwareResultListenerPut = this.mResultListeners.put(str, new LifecycleAwareResultListener(lifecycle, fragmentResultListener, lifecycleEventObserver));
        if (lifecycleAwareResultListenerPut != null) {
            lifecycleAwareResultListenerPut.removeObserver();
        }
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "Setting FragmentResultListener with key " + str + " lifecycleOwner " + lifecycle + " and listener " + fragmentResultListener);
        }
        lifecycle.addObserver(lifecycleEventObserver);
    }

    public void setStrictModePolicy(@Nullable FragmentStrictMode.Policy policy) {
        this.mStrictModePolicy = policy;
    }

    void t() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.q(false);
        dispatchStateChange(0);
    }

    void t0(int i2, int i3, boolean z2) {
        if (i2 >= 0) {
            O(new PopBackStackState(null, i2, i3), z2);
            return;
        }
        throw new IllegalArgumentException("Bad id: " + i2);
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        Fragment fragment = this.mParent;
        if (fragment != null) {
            sb.append(fragment.getClass().getSimpleName());
            sb.append("{");
            sb.append(Integer.toHexString(System.identityHashCode(this.mParent)));
            sb.append(com.alipay.sdk.m.u.i.f9804d);
        } else {
            FragmentHostCallback<?> fragmentHostCallback = this.mHost;
            if (fragmentHostCallback != null) {
                sb.append(fragmentHostCallback.getClass().getSimpleName());
                sb.append("{");
                sb.append(Integer.toHexString(System.identityHashCode(this.mHost)));
                sb.append(com.alipay.sdk.m.u.i.f9804d);
            } else {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            }
        }
        sb.append("}}");
        return sb.toString();
    }

    void u(Configuration configuration, boolean z2) {
        if (z2 && (this.mHost instanceof OnConfigurationChangedProvider)) {
            throwException(new IllegalStateException("Do not call dispatchConfigurationChanged() on host. Host implements OnConfigurationChangedProvider and automatically dispatches configuration changes to fragments."));
        }
        for (Fragment fragment : this.mFragmentStore.p()) {
            if (fragment != null) {
                fragment.performConfigurationChanged(configuration);
                if (z2) {
                    fragment.mChildFragmentManager.u(configuration, true);
                }
            }
        }
    }

    boolean u0(ArrayList arrayList, ArrayList arrayList2, String str, int i2, int i3) {
        int iFindBackStackIndex = findBackStackIndex(str, i2, (i3 & 1) != 0);
        if (iFindBackStackIndex < 0) {
            return false;
        }
        for (int size = this.f4174a.size() - 1; size >= iFindBackStackIndex; size--) {
            arrayList.add((BackStackRecord) this.f4174a.remove(size));
            arrayList2.add(Boolean.TRUE);
        }
        return true;
    }

    public void unregisterFragmentLifecycleCallbacks(@NonNull FragmentLifecycleCallbacks fragmentLifecycleCallbacks) {
        this.mLifecycleCallbacksDispatcher.unregisterFragmentLifecycleCallbacks(fragmentLifecycleCallbacks);
    }

    boolean v(MenuItem menuItem) {
        if (this.f4175b < 1) {
            return false;
        }
        for (Fragment fragment : this.mFragmentStore.p()) {
            if (fragment != null && fragment.performContextItemSelected(menuItem)) {
                return true;
            }
        }
        return false;
    }

    void v0(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "remove: " + fragment + " nesting=" + fragment.mBackStackNesting);
        }
        boolean z2 = !fragment.isInBackStack();
        if (!fragment.mDetached || z2) {
            this.mFragmentStore.v(fragment);
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
            fragment.mRemoving = true;
            setVisibleRemovingFragment(fragment);
        }
    }

    void w() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.q(false);
        dispatchStateChange(1);
    }

    void w0(Fragment fragment) {
        this.mNonConfig.o(fragment);
    }

    boolean x(Menu menu, MenuInflater menuInflater) {
        if (this.f4175b < 1) {
            return false;
        }
        ArrayList<Fragment> arrayList = null;
        boolean z2 = false;
        for (Fragment fragment : this.mFragmentStore.p()) {
            if (fragment != null && j0(fragment) && fragment.performCreateOptionsMenu(menu, menuInflater)) {
                if (arrayList == null) {
                    arrayList = new ArrayList<>();
                }
                arrayList.add(fragment);
                z2 = true;
            }
        }
        if (this.mCreatedMenus != null) {
            for (int i2 = 0; i2 < this.mCreatedMenus.size(); i2++) {
                Fragment fragment2 = this.mCreatedMenus.get(i2);
                if (arrayList == null || !arrayList.contains(fragment2)) {
                    fragment2.onDestroyOptionsMenu();
                }
            }
        }
        this.mCreatedMenus = arrayList;
        return z2;
    }

    void x0(Parcelable parcelable, FragmentManagerNonConfig fragmentManagerNonConfig) {
        if (this.mHost instanceof ViewModelStoreOwner) {
            throwException(new IllegalStateException("You must use restoreSaveState when your FragmentHostCallback implements ViewModelStoreOwner"));
        }
        this.mNonConfig.p(fragmentManagerNonConfig);
        A0(parcelable);
    }

    void y() {
        this.mDestroyed = true;
        P(true);
        endAnimatingAwayFragments();
        clearBackStackStateViewModels();
        dispatchStateChange(-1);
        Object obj = this.mHost;
        if (obj instanceof OnTrimMemoryProvider) {
            ((OnTrimMemoryProvider) obj).removeOnTrimMemoryListener(this.mOnTrimMemoryListener);
        }
        Object obj2 = this.mHost;
        if (obj2 instanceof OnConfigurationChangedProvider) {
            ((OnConfigurationChangedProvider) obj2).removeOnConfigurationChangedListener(this.mOnConfigurationChangedListener);
        }
        Object obj3 = this.mHost;
        if (obj3 instanceof OnMultiWindowModeChangedProvider) {
            ((OnMultiWindowModeChangedProvider) obj3).removeOnMultiWindowModeChangedListener(this.mOnMultiWindowModeChangedListener);
        }
        Object obj4 = this.mHost;
        if (obj4 instanceof OnPictureInPictureModeChangedProvider) {
            ((OnPictureInPictureModeChangedProvider) obj4).removeOnPictureInPictureModeChangedListener(this.mOnPictureInPictureModeChangedListener);
        }
        Object obj5 = this.mHost;
        if ((obj5 instanceof MenuHost) && this.mParent == null) {
            ((MenuHost) obj5).removeMenuProvider(this.mMenuProvider);
        }
        this.mHost = null;
        this.mContainer = null;
        this.mParent = null;
        if (this.mOnBackPressedDispatcher != null) {
            this.mOnBackPressedCallback.remove();
            this.mOnBackPressedDispatcher = null;
        }
        ActivityResultLauncher<Intent> activityResultLauncher = this.mStartActivityForResult;
        if (activityResultLauncher != null) {
            activityResultLauncher.unregister();
            this.mStartIntentSenderForResult.unregister();
            this.mRequestPermissions.unregister();
        }
    }

    boolean y0(ArrayList arrayList, ArrayList arrayList2, String str) {
        BackStackState backStackStateRemove = this.mBackStackStates.remove(str);
        if (backStackStateRemove == null) {
            return false;
        }
        HashMap map = new HashMap();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            BackStackRecord backStackRecord = (BackStackRecord) it.next();
            if (backStackRecord.f4094u) {
                Iterator it2 = backStackRecord.f4231a.iterator();
                while (it2.hasNext()) {
                    Fragment fragment = ((FragmentTransaction.Op) it2.next()).f4249b;
                    if (fragment != null) {
                        map.put(fragment.mWho, fragment);
                    }
                }
            }
        }
        Iterator it3 = backStackStateRemove.a(this, map).iterator();
        while (true) {
            boolean z2 = false;
            while (it3.hasNext()) {
                if (((BackStackRecord) it3.next()).generateOps(arrayList, arrayList2) || z2) {
                    z2 = true;
                }
            }
            return z2;
        }
    }

    void z() {
        dispatchStateChange(1);
    }

    void z0(Parcelable parcelable) {
        if (this.mHost instanceof SavedStateRegistryOwner) {
            throwException(new IllegalStateException("You cannot use restoreSaveState when your FragmentHostCallback implements SavedStateRegistryOwner."));
        }
        A0(parcelable);
    }

    public void popBackStack(@Nullable String str, int i2) {
        O(new PopBackStackState(str, -1, i2), false);
    }

    @MainThread
    public boolean popBackStackImmediate(@Nullable String str, int i2) {
        return popBackStackImmediate(str, -1, i2);
    }

    @SuppressLint({"BanParcelableUsage"})
    static class LaunchedFragmentInfo implements Parcelable {
        public static final Parcelable.Creator<LaunchedFragmentInfo> CREATOR = new Parcelable.Creator<LaunchedFragmentInfo>() { // from class: androidx.fragment.app.FragmentManager.LaunchedFragmentInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public LaunchedFragmentInfo createFromParcel(Parcel parcel) {
                return new LaunchedFragmentInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public LaunchedFragmentInfo[] newArray(int i2) {
                return new LaunchedFragmentInfo[i2];
            }
        };

        /* renamed from: a, reason: collision with root package name */
        String f4193a;

        /* renamed from: b, reason: collision with root package name */
        int f4194b;

        LaunchedFragmentInfo(String str, int i2) {
            this.f4193a = str;
            this.f4194b = i2;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeString(this.f4193a);
            parcel.writeInt(this.f4194b);
        }

        LaunchedFragmentInfo(Parcel parcel) {
            this.f4193a = parcel.readString();
            this.f4194b = parcel.readInt();
        }
    }

    public void popBackStack(int i2, int i3) {
        t0(i2, i3, false);
    }

    public boolean popBackStackImmediate(int i2, int i3) {
        if (i2 >= 0) {
            return popBackStackImmediate(null, i2, i3);
        }
        throw new IllegalArgumentException("Bad id: " + i2);
    }

    private boolean popBackStackImmediate(@Nullable String str, int i2, int i3) {
        P(false);
        ensureExecReady(true);
        Fragment fragment = this.f4176c;
        if (fragment != null && i2 < 0 && str == null && fragment.getChildFragmentManager().popBackStackImmediate()) {
            return true;
        }
        boolean zU0 = u0(this.mTmpRecords, this.mTmpIsPop, str, i2, i3);
        if (zU0) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        updateOnBackPressedCallbackEnabled();
        doPendingDeferredStart();
        this.mFragmentStore.b();
        return zU0;
    }
}
