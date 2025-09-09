package androidx.appcompat.app;

import android.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.annotation.CallSuper;
import androidx.annotation.DoNotInline;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.StandaloneActionMode;
import androidx.appcompat.view.SupportActionModeWrapper;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.WindowCallbackWrapper;
import androidx.appcompat.view.menu.ListMenuPresenter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.appcompat.widget.DecorContentParent;
import androidx.appcompat.widget.FitWindowsViewGroup;
import androidx.appcompat.widget.TintTypedArray;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.appcompat.widget.ViewStubCompat;
import androidx.appcompat.widget.ViewUtils;
import androidx.collection.SimpleArrayMap;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.os.LocaleListCompat;
import androidx.core.util.ObjectsCompat;
import androidx.core.view.KeyEventDispatcher;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.PopupWindowCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.kingsmith.miot.KsProperty;
import java.lang.Thread;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
class AppCompatDelegateImpl extends AppCompatDelegate implements MenuBuilder.Callback, LayoutInflater.Factory2 {
    private static boolean sInstalledExceptionHandler;

    /* renamed from: b, reason: collision with root package name */
    final Object f1976b;

    /* renamed from: c, reason: collision with root package name */
    final Context f1977c;

    /* renamed from: d, reason: collision with root package name */
    Window f1978d;

    /* renamed from: e, reason: collision with root package name */
    final AppCompatCallback f1979e;

    /* renamed from: f, reason: collision with root package name */
    ActionBar f1980f;

    /* renamed from: g, reason: collision with root package name */
    MenuInflater f1981g;

    /* renamed from: h, reason: collision with root package name */
    ActionMode f1982h;

    /* renamed from: i, reason: collision with root package name */
    ActionBarContextView f1983i;

    /* renamed from: j, reason: collision with root package name */
    PopupWindow f1984j;

    /* renamed from: k, reason: collision with root package name */
    Runnable f1985k;

    /* renamed from: l, reason: collision with root package name */
    ViewPropertyAnimatorCompat f1986l;

    /* renamed from: m, reason: collision with root package name */
    ViewGroup f1987m;
    private ActionMenuPresenterCallback mActionMenuPresenterCallback;
    private int mActivityHandlesConfigFlags;
    private boolean mActivityHandlesConfigFlagsChecked;
    private AppCompatViewInflater mAppCompatViewInflater;
    private AppCompatWindowCallback mAppCompatWindowCallback;
    private AutoNightModeManager mAutoBatteryNightModeManager;
    private AutoNightModeManager mAutoTimeNightModeManager;
    private OnBackInvokedCallback mBackCallback;
    private boolean mBaseContextAttached;
    private boolean mClosingActionMenu;
    private boolean mCreated;
    private DecorContentParent mDecorContentParent;
    private OnBackInvokedDispatcher mDispatcher;
    private Configuration mEffectiveConfiguration;
    private boolean mEnableDefaultActionBarUp;
    private boolean mFeatureIndeterminateProgress;
    private boolean mFeatureProgress;
    private boolean mHandleNativeActionModes;
    private final Runnable mInvalidatePanelMenuRunnable;
    private LayoutIncludeDetector mLayoutIncludeDetector;
    private int mLocalNightMode;
    private boolean mLongPressBackDown;
    private PanelMenuPresenterCallback mPanelMenuPresenterCallback;
    private PanelFeatureState[] mPanels;
    private PanelFeatureState mPreparedPanel;
    private View mStatusGuard;
    private boolean mSubDecorInstalled;
    private Rect mTempRect1;
    private Rect mTempRect2;
    private int mThemeResId;
    private CharSequence mTitle;
    private TextView mTitleView;

    /* renamed from: n, reason: collision with root package name */
    boolean f1988n;

    /* renamed from: o, reason: collision with root package name */
    boolean f1989o;

    /* renamed from: p, reason: collision with root package name */
    boolean f1990p;

    /* renamed from: q, reason: collision with root package name */
    boolean f1991q;

    /* renamed from: r, reason: collision with root package name */
    boolean f1992r;

    /* renamed from: s, reason: collision with root package name */
    boolean f1993s;

    /* renamed from: t, reason: collision with root package name */
    boolean f1994t;

    /* renamed from: u, reason: collision with root package name */
    int f1995u;
    private static final SimpleArrayMap<String, Integer> sLocalNightModes = new SimpleArrayMap<>();
    private static final boolean IS_PRE_LOLLIPOP = false;
    private static final int[] sWindowBackgroundStyleable = {R.attr.windowBackground};
    private static final boolean sCanReturnDifferentContext = !"robolectric".equals(Build.FINGERPRINT);
    private static final boolean sCanApplyOverrideConfiguration = true;

    /* renamed from: androidx.appcompat.app.AppCompatDelegateImpl$1, reason: invalid class name */
    class AnonymousClass1 implements Thread.UncaughtExceptionHandler {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Thread.UncaughtExceptionHandler f1996a;

        private boolean shouldWrapException(Throwable th) {
            String message;
            if (!(th instanceof Resources.NotFoundException) || (message = th.getMessage()) == null) {
                return false;
            }
            return message.contains("drawable") || message.contains("Drawable");
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(@NonNull Thread thread, @NonNull Throwable th) {
            if (!shouldWrapException(th)) {
                this.f1996a.uncaughtException(thread, th);
                return;
            }
            Resources.NotFoundException notFoundException = new Resources.NotFoundException(th.getMessage() + ". If the resource you are trying to use is a vector resource, you may be referencing it in an unsupported way. See AppCompatDelegate.setCompatVectorFromResourcesEnabled() for more info.");
            notFoundException.initCause(th.getCause());
            notFoundException.setStackTrace(th.getStackTrace());
            this.f1996a.uncaughtException(thread, notFoundException);
        }
    }

    /* renamed from: androidx.appcompat.app.AppCompatDelegateImpl$4, reason: invalid class name */
    class AnonymousClass4 implements FitWindowsViewGroup.OnFitSystemWindowsListener {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ AppCompatDelegateImpl f1999a;

        @Override // androidx.appcompat.widget.FitWindowsViewGroup.OnFitSystemWindowsListener
        public void onFitSystemWindows(Rect rect) {
            rect.top = this.f1999a.P(null, rect);
        }
    }

    private class ActionBarDrawableToggleImpl implements ActionBarDrawerToggle.Delegate {
        ActionBarDrawableToggleImpl() {
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public Context getActionBarThemedContext() {
            return AppCompatDelegateImpl.this.v();
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public Drawable getThemeUpIndicator() {
            TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(getActionBarThemedContext(), (AttributeSet) null, new int[]{androidx.appcompat.R.attr.homeAsUpIndicator});
            Drawable drawable = tintTypedArrayObtainStyledAttributes.getDrawable(0);
            tintTypedArrayObtainStyledAttributes.recycle();
            return drawable;
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public boolean isNavigationVisible() {
            ActionBar supportActionBar = AppCompatDelegateImpl.this.getSupportActionBar();
            return (supportActionBar == null || (supportActionBar.getDisplayOptions() & 4) == 0) ? false : true;
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public void setActionBarDescription(int i2) {
            ActionBar supportActionBar = AppCompatDelegateImpl.this.getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setHomeActionContentDescription(i2);
            }
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public void setActionBarUpIndicator(Drawable drawable, int i2) {
            ActionBar supportActionBar = AppCompatDelegateImpl.this.getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setHomeAsUpIndicator(drawable);
                supportActionBar.setHomeActionContentDescription(i2);
            }
        }
    }

    interface ActionBarMenuCallback {
        @Nullable
        View onCreatePanelView(int i2);

        boolean onPreparePanel(int i2);
    }

    private final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        ActionMenuPresenterCallback() {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public void onCloseMenu(@NonNull MenuBuilder menuBuilder, boolean z2) {
            AppCompatDelegateImpl.this.n(menuBuilder);
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(@NonNull MenuBuilder menuBuilder) {
            Window.Callback callbackZ = AppCompatDelegateImpl.this.z();
            if (callbackZ == null) {
                return true;
            }
            callbackZ.onMenuOpened(108, menuBuilder);
            return true;
        }
    }

    class ActionModeCallbackWrapperV9 implements ActionMode.Callback {
        private ActionMode.Callback mWrapped;

        public ActionModeCallbackWrapperV9(ActionMode.Callback callback) {
            this.mWrapped = callback;
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mWrapped.onActionItemClicked(actionMode, menuItem);
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return this.mWrapped.onCreateActionMode(actionMode, menu);
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
            this.mWrapped.onDestroyActionMode(actionMode);
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (appCompatDelegateImpl.f1984j != null) {
                appCompatDelegateImpl.f1978d.getDecorView().removeCallbacks(AppCompatDelegateImpl.this.f1985k);
            }
            AppCompatDelegateImpl appCompatDelegateImpl2 = AppCompatDelegateImpl.this;
            if (appCompatDelegateImpl2.f1983i != null) {
                appCompatDelegateImpl2.t();
                AppCompatDelegateImpl appCompatDelegateImpl3 = AppCompatDelegateImpl.this;
                appCompatDelegateImpl3.f1986l = ViewCompat.animate(appCompatDelegateImpl3.f1983i).alpha(0.0f);
                AppCompatDelegateImpl.this.f1986l.setListener(new ViewPropertyAnimatorListenerAdapter() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.ActionModeCallbackWrapperV9.1
                    @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                    public void onAnimationEnd(View view) {
                        AppCompatDelegateImpl.this.f1983i.setVisibility(8);
                        AppCompatDelegateImpl appCompatDelegateImpl4 = AppCompatDelegateImpl.this;
                        PopupWindow popupWindow = appCompatDelegateImpl4.f1984j;
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        } else if (appCompatDelegateImpl4.f1983i.getParent() instanceof View) {
                            ViewCompat.requestApplyInsets((View) AppCompatDelegateImpl.this.f1983i.getParent());
                        }
                        AppCompatDelegateImpl.this.f1983i.killMode();
                        AppCompatDelegateImpl.this.f1986l.setListener(null);
                        AppCompatDelegateImpl appCompatDelegateImpl5 = AppCompatDelegateImpl.this;
                        appCompatDelegateImpl5.f1986l = null;
                        ViewCompat.requestApplyInsets(appCompatDelegateImpl5.f1987m);
                    }
                });
            }
            AppCompatDelegateImpl appCompatDelegateImpl4 = AppCompatDelegateImpl.this;
            AppCompatCallback appCompatCallback = appCompatDelegateImpl4.f1979e;
            if (appCompatCallback != null) {
                appCompatCallback.onSupportActionModeFinished(appCompatDelegateImpl4.f1982h);
            }
            AppCompatDelegateImpl appCompatDelegateImpl5 = AppCompatDelegateImpl.this;
            appCompatDelegateImpl5.f1982h = null;
            ViewCompat.requestApplyInsets(appCompatDelegateImpl5.f1987m);
            AppCompatDelegateImpl.this.O();
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            ViewCompat.requestApplyInsets(AppCompatDelegateImpl.this.f1987m);
            return this.mWrapped.onPrepareActionMode(actionMode, menu);
        }
    }

    @RequiresApi(17)
    static class Api17Impl {
        private Api17Impl() {
        }

        static Context a(Context context, Configuration configuration) {
            return context.createConfigurationContext(configuration);
        }

        static void b(Configuration configuration, Configuration configuration2, Configuration configuration3) {
            int i2 = configuration.densityDpi;
            int i3 = configuration2.densityDpi;
            if (i2 != i3) {
                configuration3.densityDpi = i3;
            }
        }

        @DoNotInline
        static void c(Configuration configuration, Locale locale) {
            configuration.setLayoutDirection(locale);
        }

        @DoNotInline
        static void d(Configuration configuration, Locale locale) {
            configuration.setLocale(locale);
        }
    }

    @RequiresApi(21)
    static class Api21Impl {
        private Api21Impl() {
        }

        static boolean a(PowerManager powerManager) {
            return powerManager.isPowerSaveMode();
        }

        @DoNotInline
        static String b(Locale locale) {
            return locale.toLanguageTag();
        }
    }

    @RequiresApi(24)
    static class Api24Impl {
        private Api24Impl() {
        }

        @DoNotInline
        static void a(@NonNull Configuration configuration, @NonNull Configuration configuration2, @NonNull Configuration configuration3) {
            LocaleList locales = configuration.getLocales();
            LocaleList locales2 = configuration2.getLocales();
            if (locales.equals(locales2)) {
                return;
            }
            configuration3.setLocales(locales2);
            configuration3.locale = configuration2.locale;
        }

        @DoNotInline
        static LocaleListCompat b(Configuration configuration) {
            return LocaleListCompat.forLanguageTags(configuration.getLocales().toLanguageTags());
        }

        @DoNotInline
        static void c(Configuration configuration, LocaleListCompat localeListCompat) {
            configuration.setLocales(LocaleList.forLanguageTags(localeListCompat.toLanguageTags()));
        }

        @DoNotInline
        public static void setDefaultLocales(LocaleListCompat localeListCompat) {
            LocaleList.setDefault(LocaleList.forLanguageTags(localeListCompat.toLanguageTags()));
        }
    }

    @RequiresApi(26)
    static class Api26Impl {
        private Api26Impl() {
        }

        static void a(Configuration configuration, Configuration configuration2, Configuration configuration3) {
            if ((configuration.colorMode & 3) != (configuration2.colorMode & 3)) {
                configuration3.colorMode |= configuration2.colorMode & 3;
            }
            if ((configuration.colorMode & 12) != (configuration2.colorMode & 12)) {
                configuration3.colorMode |= configuration2.colorMode & 12;
            }
        }
    }

    @RequiresApi(33)
    static class Api33Impl {
        private Api33Impl() {
        }

        @DoNotInline
        static OnBackInvokedDispatcher a(Activity activity) {
            return activity.getOnBackInvokedDispatcher();
        }

        @DoNotInline
        static OnBackInvokedCallback b(Object obj, final AppCompatDelegateImpl appCompatDelegateImpl) {
            Objects.requireNonNull(appCompatDelegateImpl);
            OnBackInvokedCallback onBackInvokedCallback = new OnBackInvokedCallback() { // from class: androidx.appcompat.app.p
                public final void onBackInvoked() {
                    appCompatDelegateImpl.B();
                }
            };
            l.a(obj).registerOnBackInvokedCallback(1000000, onBackInvokedCallback);
            return onBackInvokedCallback;
        }

        @DoNotInline
        static void c(Object obj, Object obj2) {
            l.a(obj).unregisterOnBackInvokedCallback(k.a(obj2));
        }
    }

    class AppCompatWindowCallback extends WindowCallbackWrapper {
        private ActionBarMenuCallback mActionBarCallback;
        private boolean mDispatchKeyEventBypassEnabled;
        private boolean mOnContentChangedBypassEnabled;
        private boolean mOnPanelClosedBypassEnabled;

        AppCompatWindowCallback(Window.Callback callback) {
            super(callback);
        }

        void a(ActionBarMenuCallback actionBarMenuCallback) {
            this.mActionBarCallback = actionBarMenuCallback;
        }

        final android.view.ActionMode b(ActionMode.Callback callback) {
            SupportActionModeWrapper.CallbackWrapper callbackWrapper = new SupportActionModeWrapper.CallbackWrapper(AppCompatDelegateImpl.this.f1977c, callback);
            androidx.appcompat.view.ActionMode actionModeStartSupportActionMode = AppCompatDelegateImpl.this.startSupportActionMode(callbackWrapper);
            if (actionModeStartSupportActionMode != null) {
                return callbackWrapper.getActionModeWrapper(actionModeStartSupportActionMode);
            }
            return null;
        }

        public boolean bypassDispatchKeyEvent(Window.Callback callback, KeyEvent keyEvent) {
            try {
                this.mDispatchKeyEventBypassEnabled = true;
                return callback.dispatchKeyEvent(keyEvent);
            } finally {
                this.mDispatchKeyEventBypassEnabled = false;
            }
        }

        public void bypassOnContentChanged(Window.Callback callback) {
            try {
                this.mOnContentChangedBypassEnabled = true;
                callback.onContentChanged();
            } finally {
                this.mOnContentChangedBypassEnabled = false;
            }
        }

        public void bypassOnPanelClosed(Window.Callback callback, int i2, Menu menu) {
            try {
                this.mOnPanelClosedBypassEnabled = true;
                callback.onPanelClosed(i2, menu);
            } finally {
                this.mOnPanelClosedBypassEnabled = false;
            }
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return this.mDispatchKeyEventBypassEnabled ? getWrapped().dispatchKeyEvent(keyEvent) : AppCompatDelegateImpl.this.r(keyEvent) || super.dispatchKeyEvent(keyEvent);
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
            return super.dispatchKeyShortcutEvent(keyEvent) || AppCompatDelegateImpl.this.D(keyEvent.getKeyCode(), keyEvent);
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public void onContentChanged() {
            if (this.mOnContentChangedBypassEnabled) {
                getWrapped().onContentChanged();
            }
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean onCreatePanelMenu(int i2, Menu menu) {
            if (i2 != 0 || (menu instanceof MenuBuilder)) {
                return super.onCreatePanelMenu(i2, menu);
            }
            return false;
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public View onCreatePanelView(int i2) {
            View viewOnCreatePanelView;
            ActionBarMenuCallback actionBarMenuCallback = this.mActionBarCallback;
            return (actionBarMenuCallback == null || (viewOnCreatePanelView = actionBarMenuCallback.onCreatePanelView(i2)) == null) ? super.onCreatePanelView(i2) : viewOnCreatePanelView;
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean onMenuOpened(int i2, Menu menu) {
            super.onMenuOpened(i2, menu);
            AppCompatDelegateImpl.this.F(i2);
            return true;
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public void onPanelClosed(int i2, Menu menu) {
            if (this.mOnPanelClosedBypassEnabled) {
                getWrapped().onPanelClosed(i2, menu);
            } else {
                super.onPanelClosed(i2, menu);
                AppCompatDelegateImpl.this.G(i2);
            }
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean onPreparePanel(int i2, View view, Menu menu) {
            MenuBuilder menuBuilder = menu instanceof MenuBuilder ? (MenuBuilder) menu : null;
            if (i2 == 0 && menuBuilder == null) {
                return false;
            }
            if (menuBuilder != null) {
                menuBuilder.setOverrideVisibleItems(true);
            }
            ActionBarMenuCallback actionBarMenuCallback = this.mActionBarCallback;
            boolean zOnPreparePanel = actionBarMenuCallback != null && actionBarMenuCallback.onPreparePanel(i2);
            if (!zOnPreparePanel) {
                zOnPreparePanel = super.onPreparePanel(i2, view, menu);
            }
            if (menuBuilder != null) {
                menuBuilder.setOverrideVisibleItems(false);
            }
            return zOnPreparePanel;
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        @RequiresApi(24)
        public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> list, Menu menu, int i2) {
            MenuBuilder menuBuilder;
            PanelFeatureState panelFeatureStateX = AppCompatDelegateImpl.this.x(0, true);
            if (panelFeatureStateX == null || (menuBuilder = panelFeatureStateX.f2023j) == null) {
                super.onProvideKeyboardShortcuts(list, menu, i2);
            } else {
                super.onProvideKeyboardShortcuts(list, menuBuilder, i2);
            }
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            return null;
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        @RequiresApi(23)
        public android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i2) {
            return (AppCompatDelegateImpl.this.isHandleNativeActionModesEnabled() && i2 == 0) ? b(callback) : super.onWindowStartingActionMode(callback, i2);
        }
    }

    private class AutoBatteryNightModeManager extends AutoNightModeManager {
        private final PowerManager mPowerManager;

        AutoBatteryNightModeManager(Context context) {
            super();
            this.mPowerManager = (PowerManager) context.getApplicationContext().getSystemService(KsProperty.Power);
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        IntentFilter b() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
            return intentFilter;
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        public int getApplyableNightMode() {
            return Api21Impl.a(this.mPowerManager) ? 2 : 1;
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        public void onChange() {
            AppCompatDelegateImpl.this.applyDayNight();
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @VisibleForTesting
    abstract class AutoNightModeManager {
        private BroadcastReceiver mReceiver;

        AutoNightModeManager() {
        }

        void a() {
            BroadcastReceiver broadcastReceiver = this.mReceiver;
            if (broadcastReceiver != null) {
                try {
                    AppCompatDelegateImpl.this.f1977c.unregisterReceiver(broadcastReceiver);
                } catch (IllegalArgumentException unused) {
                }
                this.mReceiver = null;
            }
        }

        abstract IntentFilter b();

        void c() {
            a();
            IntentFilter intentFilterB = b();
            if (intentFilterB == null || intentFilterB.countActions() == 0) {
                return;
            }
            if (this.mReceiver == null) {
                this.mReceiver = new BroadcastReceiver() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager.1
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(Context context, Intent intent) {
                        AutoNightModeManager.this.onChange();
                    }
                };
            }
            AppCompatDelegateImpl.this.f1977c.registerReceiver(this.mReceiver, intentFilterB);
        }

        abstract int getApplyableNightMode();

        abstract void onChange();
    }

    private class AutoTimeNightModeManager extends AutoNightModeManager {
        private final TwilightManager mTwilightManager;

        AutoTimeNightModeManager(TwilightManager twilightManager) {
            super();
            this.mTwilightManager = twilightManager;
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        IntentFilter b() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.TIME_SET");
            intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            intentFilter.addAction("android.intent.action.TIME_TICK");
            return intentFilter;
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        public int getApplyableNightMode() {
            return this.mTwilightManager.b() ? 2 : 1;
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        public void onChange() {
            AppCompatDelegateImpl.this.applyDayNight();
        }
    }

    @RequiresApi(17)
    private static class ContextThemeWrapperCompatApi17Impl {
        private ContextThemeWrapperCompatApi17Impl() {
        }

        static void a(ContextThemeWrapper contextThemeWrapper, Configuration configuration) {
            contextThemeWrapper.applyOverrideConfiguration(configuration);
        }
    }

    private class ListMenuDecorView extends ContentFrameLayout {
        public ListMenuDecorView(Context context) {
            super(context);
        }

        private boolean isOutOfBounds(int i2, int i3) {
            return i2 < -5 || i3 < -5 || i2 > getWidth() + 5 || i3 > getHeight() + 5;
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return AppCompatDelegateImpl.this.r(keyEvent) || super.dispatchKeyEvent(keyEvent);
        }

        @Override // android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() != 0 || !isOutOfBounds((int) motionEvent.getX(), (int) motionEvent.getY())) {
                return super.onInterceptTouchEvent(motionEvent);
            }
            AppCompatDelegateImpl.this.o(0);
            return true;
        }

        @Override // android.view.View
        public void setBackgroundResource(int i2) {
            setBackgroundDrawable(AppCompatResources.getDrawable(getContext(), i2));
        }
    }

    protected static final class PanelFeatureState {

        /* renamed from: a, reason: collision with root package name */
        int f2014a;

        /* renamed from: b, reason: collision with root package name */
        int f2015b;

        /* renamed from: c, reason: collision with root package name */
        int f2016c;

        /* renamed from: d, reason: collision with root package name */
        int f2017d;

        /* renamed from: e, reason: collision with root package name */
        int f2018e;

        /* renamed from: f, reason: collision with root package name */
        int f2019f;

        /* renamed from: g, reason: collision with root package name */
        ViewGroup f2020g;

        /* renamed from: h, reason: collision with root package name */
        View f2021h;

        /* renamed from: i, reason: collision with root package name */
        View f2022i;

        /* renamed from: j, reason: collision with root package name */
        MenuBuilder f2023j;

        /* renamed from: k, reason: collision with root package name */
        ListMenuPresenter f2024k;

        /* renamed from: l, reason: collision with root package name */
        Context f2025l;

        /* renamed from: m, reason: collision with root package name */
        boolean f2026m;

        /* renamed from: n, reason: collision with root package name */
        boolean f2027n;

        /* renamed from: o, reason: collision with root package name */
        boolean f2028o;

        /* renamed from: p, reason: collision with root package name */
        boolean f2029p = false;

        /* renamed from: q, reason: collision with root package name */
        boolean f2030q;
        public boolean qwertyMode;

        /* renamed from: r, reason: collision with root package name */
        Bundle f2031r;

        @SuppressLint({"BanParcelableUsage"})
        private static class SavedState implements Parcelable {
            public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.PanelFeatureState.SavedState.1
                @Override // android.os.Parcelable.Creator
                public SavedState[] newArray(int i2) {
                    return new SavedState[i2];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.ClassLoaderCreator
                public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                    return SavedState.a(parcel, classLoader);
                }

                @Override // android.os.Parcelable.Creator
                public SavedState createFromParcel(Parcel parcel) {
                    return SavedState.a(parcel, null);
                }
            };

            /* renamed from: a, reason: collision with root package name */
            int f2032a;

            /* renamed from: b, reason: collision with root package name */
            boolean f2033b;

            /* renamed from: c, reason: collision with root package name */
            Bundle f2034c;

            SavedState() {
            }

            static SavedState a(Parcel parcel, ClassLoader classLoader) {
                SavedState savedState = new SavedState();
                savedState.f2032a = parcel.readInt();
                boolean z2 = parcel.readInt() == 1;
                savedState.f2033b = z2;
                if (z2) {
                    savedState.f2034c = parcel.readBundle(classLoader);
                }
                return savedState;
            }

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            @Override // android.os.Parcelable
            public void writeToParcel(Parcel parcel, int i2) {
                parcel.writeInt(this.f2032a);
                parcel.writeInt(this.f2033b ? 1 : 0);
                if (this.f2033b) {
                    parcel.writeBundle(this.f2034c);
                }
            }
        }

        PanelFeatureState(int i2) {
            this.f2014a = i2;
        }

        MenuView a(MenuPresenter.Callback callback) {
            if (this.f2023j == null) {
                return null;
            }
            if (this.f2024k == null) {
                ListMenuPresenter listMenuPresenter = new ListMenuPresenter(this.f2025l, androidx.appcompat.R.layout.abc_list_menu_item_layout);
                this.f2024k = listMenuPresenter;
                listMenuPresenter.setCallback(callback);
                this.f2023j.addMenuPresenter(this.f2024k);
            }
            return this.f2024k.getMenuView(this.f2020g);
        }

        void b(MenuBuilder menuBuilder) {
            ListMenuPresenter listMenuPresenter;
            MenuBuilder menuBuilder2 = this.f2023j;
            if (menuBuilder == menuBuilder2) {
                return;
            }
            if (menuBuilder2 != null) {
                menuBuilder2.removeMenuPresenter(this.f2024k);
            }
            this.f2023j = menuBuilder;
            if (menuBuilder == null || (listMenuPresenter = this.f2024k) == null) {
                return;
            }
            menuBuilder.addMenuPresenter(listMenuPresenter);
        }

        void c(Context context) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme themeNewTheme = context.getResources().newTheme();
            themeNewTheme.setTo(context.getTheme());
            themeNewTheme.resolveAttribute(androidx.appcompat.R.attr.actionBarPopupTheme, typedValue, true);
            int i2 = typedValue.resourceId;
            if (i2 != 0) {
                themeNewTheme.applyStyle(i2, true);
            }
            themeNewTheme.resolveAttribute(androidx.appcompat.R.attr.panelMenuListTheme, typedValue, true);
            int i3 = typedValue.resourceId;
            if (i3 != 0) {
                themeNewTheme.applyStyle(i3, true);
            } else {
                themeNewTheme.applyStyle(androidx.appcompat.R.style.Theme_AppCompat_CompactMenu, true);
            }
            androidx.appcompat.view.ContextThemeWrapper contextThemeWrapper = new androidx.appcompat.view.ContextThemeWrapper(context, 0);
            contextThemeWrapper.getTheme().setTo(themeNewTheme);
            this.f2025l = contextThemeWrapper;
            TypedArray typedArrayObtainStyledAttributes = contextThemeWrapper.obtainStyledAttributes(androidx.appcompat.R.styleable.AppCompatTheme);
            this.f2015b = typedArrayObtainStyledAttributes.getResourceId(androidx.appcompat.R.styleable.AppCompatTheme_panelBackground, 0);
            this.f2019f = typedArrayObtainStyledAttributes.getResourceId(androidx.appcompat.R.styleable.AppCompatTheme_android_windowAnimationStyle, 0);
            typedArrayObtainStyledAttributes.recycle();
        }

        public void clearMenuPresenters() {
            MenuBuilder menuBuilder = this.f2023j;
            if (menuBuilder != null) {
                menuBuilder.removeMenuPresenter(this.f2024k);
            }
            this.f2024k = null;
        }

        public boolean hasPanelItems() {
            if (this.f2021h == null) {
                return false;
            }
            return this.f2022i != null || this.f2024k.getAdapter().getCount() > 0;
        }
    }

    private final class PanelMenuPresenterCallback implements MenuPresenter.Callback {
        PanelMenuPresenterCallback() {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public void onCloseMenu(@NonNull MenuBuilder menuBuilder, boolean z2) {
            MenuBuilder rootMenu = menuBuilder.getRootMenu();
            boolean z3 = rootMenu != menuBuilder;
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (z3) {
                menuBuilder = rootMenu;
            }
            PanelFeatureState panelFeatureStateU = appCompatDelegateImpl.u(menuBuilder);
            if (panelFeatureStateU != null) {
                if (!z3) {
                    AppCompatDelegateImpl.this.p(panelFeatureStateU, z2);
                } else {
                    AppCompatDelegateImpl.this.m(panelFeatureStateU.f2014a, panelFeatureStateU, rootMenu);
                    AppCompatDelegateImpl.this.p(panelFeatureStateU, true);
                }
            }
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(@NonNull MenuBuilder menuBuilder) {
            Window.Callback callbackZ;
            if (menuBuilder != menuBuilder.getRootMenu()) {
                return true;
            }
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (!appCompatDelegateImpl.f1988n || (callbackZ = appCompatDelegateImpl.z()) == null || AppCompatDelegateImpl.this.f1993s) {
                return true;
            }
            callbackZ.onMenuOpened(108, menuBuilder);
            return true;
        }
    }

    AppCompatDelegateImpl(Activity activity, AppCompatCallback appCompatCallback) {
        this(activity, null, appCompatCallback, activity);
    }

    private boolean applyApplicationSpecificConfig(boolean z2) {
        return applyApplicationSpecificConfig(z2, true);
    }

    private void applyFixedSizeWindow() {
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout) this.f1987m.findViewById(R.id.content);
        View decorView = this.f1978d.getDecorView();
        contentFrameLayout.setDecorPadding(decorView.getPaddingLeft(), decorView.getPaddingTop(), decorView.getPaddingRight(), decorView.getPaddingBottom());
        TypedArray typedArrayObtainStyledAttributes = this.f1977c.obtainStyledAttributes(androidx.appcompat.R.styleable.AppCompatTheme);
        typedArrayObtainStyledAttributes.getValue(androidx.appcompat.R.styleable.AppCompatTheme_windowMinWidthMajor, contentFrameLayout.getMinWidthMajor());
        typedArrayObtainStyledAttributes.getValue(androidx.appcompat.R.styleable.AppCompatTheme_windowMinWidthMinor, contentFrameLayout.getMinWidthMinor());
        if (typedArrayObtainStyledAttributes.hasValue(androidx.appcompat.R.styleable.AppCompatTheme_windowFixedWidthMajor)) {
            typedArrayObtainStyledAttributes.getValue(androidx.appcompat.R.styleable.AppCompatTheme_windowFixedWidthMajor, contentFrameLayout.getFixedWidthMajor());
        }
        if (typedArrayObtainStyledAttributes.hasValue(androidx.appcompat.R.styleable.AppCompatTheme_windowFixedWidthMinor)) {
            typedArrayObtainStyledAttributes.getValue(androidx.appcompat.R.styleable.AppCompatTheme_windowFixedWidthMinor, contentFrameLayout.getFixedWidthMinor());
        }
        if (typedArrayObtainStyledAttributes.hasValue(androidx.appcompat.R.styleable.AppCompatTheme_windowFixedHeightMajor)) {
            typedArrayObtainStyledAttributes.getValue(androidx.appcompat.R.styleable.AppCompatTheme_windowFixedHeightMajor, contentFrameLayout.getFixedHeightMajor());
        }
        if (typedArrayObtainStyledAttributes.hasValue(androidx.appcompat.R.styleable.AppCompatTheme_windowFixedHeightMinor)) {
            typedArrayObtainStyledAttributes.getValue(androidx.appcompat.R.styleable.AppCompatTheme_windowFixedHeightMinor, contentFrameLayout.getFixedHeightMinor());
        }
        typedArrayObtainStyledAttributes.recycle();
        contentFrameLayout.requestLayout();
    }

    private void attachToWindow(@NonNull Window window) {
        if (this.f1978d != null) {
            throw new IllegalStateException("AppCompat has already installed itself into the Window");
        }
        Window.Callback callback = window.getCallback();
        if (callback instanceof AppCompatWindowCallback) {
            throw new IllegalStateException("AppCompat has already installed itself into the Window");
        }
        AppCompatWindowCallback appCompatWindowCallback = new AppCompatWindowCallback(callback);
        this.mAppCompatWindowCallback = appCompatWindowCallback;
        window.setCallback(appCompatWindowCallback);
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.f1977c, (AttributeSet) null, sWindowBackgroundStyleable);
        Drawable drawableIfKnown = tintTypedArrayObtainStyledAttributes.getDrawableIfKnown(0);
        if (drawableIfKnown != null) {
            window.setBackgroundDrawable(drawableIfKnown);
        }
        tintTypedArrayObtainStyledAttributes.recycle();
        this.f1978d = window;
        if (Build.VERSION.SDK_INT < 33 || this.mDispatcher != null) {
            return;
        }
        setOnBackInvokedDispatcher(null);
    }

    private int calculateNightMode() {
        int i2 = this.mLocalNightMode;
        return i2 != -100 ? i2 : AppCompatDelegate.getDefaultNightMode();
    }

    private void cleanupAutoManagers() {
        AutoNightModeManager autoNightModeManager = this.mAutoTimeNightModeManager;
        if (autoNightModeManager != null) {
            autoNightModeManager.a();
        }
        AutoNightModeManager autoNightModeManager2 = this.mAutoBatteryNightModeManager;
        if (autoNightModeManager2 != null) {
            autoNightModeManager2.a();
        }
    }

    @NonNull
    private Configuration createOverrideAppConfiguration(@NonNull Context context, int i2, @Nullable LocaleListCompat localeListCompat, @Nullable Configuration configuration, boolean z2) {
        int i3 = i2 != 1 ? i2 != 2 ? z2 ? 0 : context.getApplicationContext().getResources().getConfiguration().uiMode & 48 : 32 : 16;
        Configuration configuration2 = new Configuration();
        configuration2.fontScale = 0.0f;
        if (configuration != null) {
            configuration2.setTo(configuration);
        }
        configuration2.uiMode = i3 | (configuration2.uiMode & (-49));
        if (localeListCompat != null) {
            J(configuration2, localeListCompat);
        }
        return configuration2;
    }

    private ViewGroup createSubDecor() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        ViewGroup viewGroup;
        TypedArray typedArrayObtainStyledAttributes = this.f1977c.obtainStyledAttributes(androidx.appcompat.R.styleable.AppCompatTheme);
        if (!typedArrayObtainStyledAttributes.hasValue(androidx.appcompat.R.styleable.AppCompatTheme_windowActionBar)) {
            typedArrayObtainStyledAttributes.recycle();
            throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
        }
        if (typedArrayObtainStyledAttributes.getBoolean(androidx.appcompat.R.styleable.AppCompatTheme_windowNoTitle, false)) {
            requestWindowFeature(1);
        } else if (typedArrayObtainStyledAttributes.getBoolean(androidx.appcompat.R.styleable.AppCompatTheme_windowActionBar, false)) {
            requestWindowFeature(108);
        }
        if (typedArrayObtainStyledAttributes.getBoolean(androidx.appcompat.R.styleable.AppCompatTheme_windowActionBarOverlay, false)) {
            requestWindowFeature(109);
        }
        if (typedArrayObtainStyledAttributes.getBoolean(androidx.appcompat.R.styleable.AppCompatTheme_windowActionModeOverlay, false)) {
            requestWindowFeature(10);
        }
        this.f1991q = typedArrayObtainStyledAttributes.getBoolean(androidx.appcompat.R.styleable.AppCompatTheme_android_windowIsFloating, false);
        typedArrayObtainStyledAttributes.recycle();
        ensureWindow();
        this.f1978d.getDecorView();
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.f1977c);
        if (this.f1992r) {
            viewGroup = this.f1990p ? (ViewGroup) layoutInflaterFrom.inflate(androidx.appcompat.R.layout.abc_screen_simple_overlay_action_mode, (ViewGroup) null) : (ViewGroup) layoutInflaterFrom.inflate(androidx.appcompat.R.layout.abc_screen_simple, (ViewGroup) null);
        } else if (this.f1991q) {
            viewGroup = (ViewGroup) layoutInflaterFrom.inflate(androidx.appcompat.R.layout.abc_dialog_title_material, (ViewGroup) null);
            this.f1989o = false;
            this.f1988n = false;
        } else if (this.f1988n) {
            TypedValue typedValue = new TypedValue();
            this.f1977c.getTheme().resolveAttribute(androidx.appcompat.R.attr.actionBarTheme, typedValue, true);
            viewGroup = (ViewGroup) LayoutInflater.from(typedValue.resourceId != 0 ? new androidx.appcompat.view.ContextThemeWrapper(this.f1977c, typedValue.resourceId) : this.f1977c).inflate(androidx.appcompat.R.layout.abc_screen_toolbar, (ViewGroup) null);
            DecorContentParent decorContentParent = (DecorContentParent) viewGroup.findViewById(androidx.appcompat.R.id.decor_content_parent);
            this.mDecorContentParent = decorContentParent;
            decorContentParent.setWindowCallback(z());
            if (this.f1989o) {
                this.mDecorContentParent.initFeature(109);
            }
            if (this.mFeatureProgress) {
                this.mDecorContentParent.initFeature(2);
            }
            if (this.mFeatureIndeterminateProgress) {
                this.mDecorContentParent.initFeature(5);
            }
        } else {
            viewGroup = null;
        }
        if (viewGroup == null) {
            throw new IllegalArgumentException("AppCompat does not support the current theme features: { windowActionBar: " + this.f1988n + ", windowActionBarOverlay: " + this.f1989o + ", android:windowIsFloating: " + this.f1991q + ", windowActionModeOverlay: " + this.f1990p + ", windowNoTitle: " + this.f1992r + " }");
        }
        ViewCompat.setOnApplyWindowInsetsListener(viewGroup, new OnApplyWindowInsetsListener() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.3
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                int systemWindowInsetTop = windowInsetsCompat.getSystemWindowInsetTop();
                int iP = AppCompatDelegateImpl.this.P(windowInsetsCompat, null);
                if (systemWindowInsetTop != iP) {
                    windowInsetsCompat = windowInsetsCompat.replaceSystemWindowInsets(windowInsetsCompat.getSystemWindowInsetLeft(), iP, windowInsetsCompat.getSystemWindowInsetRight(), windowInsetsCompat.getSystemWindowInsetBottom());
                }
                return ViewCompat.onApplyWindowInsets(view, windowInsetsCompat);
            }
        });
        if (this.mDecorContentParent == null) {
            this.mTitleView = (TextView) viewGroup.findViewById(androidx.appcompat.R.id.title);
        }
        ViewUtils.makeOptionalFitsSystemWindows(viewGroup);
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout) viewGroup.findViewById(androidx.appcompat.R.id.action_bar_activity_content);
        ViewGroup viewGroup2 = (ViewGroup) this.f1978d.findViewById(R.id.content);
        if (viewGroup2 != null) {
            while (viewGroup2.getChildCount() > 0) {
                View childAt = viewGroup2.getChildAt(0);
                viewGroup2.removeViewAt(0);
                contentFrameLayout.addView(childAt);
            }
            viewGroup2.setId(-1);
            contentFrameLayout.setId(R.id.content);
            if (viewGroup2 instanceof FrameLayout) {
                ((FrameLayout) viewGroup2).setForeground(null);
            }
        }
        this.f1978d.setContentView(viewGroup);
        contentFrameLayout.setAttachListener(new ContentFrameLayout.OnAttachListener() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.5
            @Override // androidx.appcompat.widget.ContentFrameLayout.OnAttachListener
            public void onAttachedFromWindow() {
            }

            @Override // androidx.appcompat.widget.ContentFrameLayout.OnAttachListener
            public void onDetachedFromWindow() {
                AppCompatDelegateImpl.this.q();
            }
        });
        return viewGroup;
    }

    private void ensureSubDecor() {
        if (this.mSubDecorInstalled) {
            return;
        }
        this.f1987m = createSubDecor();
        CharSequence charSequenceY = y();
        if (!TextUtils.isEmpty(charSequenceY)) {
            DecorContentParent decorContentParent = this.mDecorContentParent;
            if (decorContentParent != null) {
                decorContentParent.setWindowTitle(charSequenceY);
            } else if (I() != null) {
                I().setWindowTitle(charSequenceY);
            } else {
                TextView textView = this.mTitleView;
                if (textView != null) {
                    textView.setText(charSequenceY);
                }
            }
        }
        applyFixedSizeWindow();
        H(this.f1987m);
        this.mSubDecorInstalled = true;
        PanelFeatureState panelFeatureStateX = x(0, false);
        if (this.f1993s) {
            return;
        }
        if (panelFeatureStateX == null || panelFeatureStateX.f2023j == null) {
            invalidatePanelMenu(108);
        }
    }

    private void ensureWindow() {
        if (this.f1978d == null) {
            Object obj = this.f1976b;
            if (obj instanceof Activity) {
                attachToWindow(((Activity) obj).getWindow());
            }
        }
        if (this.f1978d == null) {
            throw new IllegalStateException("We have not been given a Window");
        }
    }

    @NonNull
    private static Configuration generateConfigDelta(@NonNull Configuration configuration, @Nullable Configuration configuration2) {
        Configuration configuration3 = new Configuration();
        configuration3.fontScale = 0.0f;
        if (configuration2 != null && configuration.diff(configuration2) != 0) {
            float f2 = configuration.fontScale;
            float f3 = configuration2.fontScale;
            if (f2 != f3) {
                configuration3.fontScale = f3;
            }
            int i2 = configuration.mcc;
            int i3 = configuration2.mcc;
            if (i2 != i3) {
                configuration3.mcc = i3;
            }
            int i4 = configuration.mnc;
            int i5 = configuration2.mnc;
            if (i4 != i5) {
                configuration3.mnc = i5;
            }
            int i6 = Build.VERSION.SDK_INT;
            if (i6 >= 24) {
                Api24Impl.a(configuration, configuration2, configuration3);
            } else if (!ObjectsCompat.equals(configuration.locale, configuration2.locale)) {
                configuration3.locale = configuration2.locale;
            }
            int i7 = configuration.touchscreen;
            int i8 = configuration2.touchscreen;
            if (i7 != i8) {
                configuration3.touchscreen = i8;
            }
            int i9 = configuration.keyboard;
            int i10 = configuration2.keyboard;
            if (i9 != i10) {
                configuration3.keyboard = i10;
            }
            int i11 = configuration.keyboardHidden;
            int i12 = configuration2.keyboardHidden;
            if (i11 != i12) {
                configuration3.keyboardHidden = i12;
            }
            int i13 = configuration.navigation;
            int i14 = configuration2.navigation;
            if (i13 != i14) {
                configuration3.navigation = i14;
            }
            int i15 = configuration.navigationHidden;
            int i16 = configuration2.navigationHidden;
            if (i15 != i16) {
                configuration3.navigationHidden = i16;
            }
            int i17 = configuration.orientation;
            int i18 = configuration2.orientation;
            if (i17 != i18) {
                configuration3.orientation = i18;
            }
            int i19 = configuration.screenLayout & 15;
            int i20 = configuration2.screenLayout;
            if (i19 != (i20 & 15)) {
                configuration3.screenLayout |= i20 & 15;
            }
            int i21 = configuration.screenLayout & 192;
            int i22 = configuration2.screenLayout;
            if (i21 != (i22 & 192)) {
                configuration3.screenLayout |= i22 & 192;
            }
            int i23 = configuration.screenLayout & 48;
            int i24 = configuration2.screenLayout;
            if (i23 != (i24 & 48)) {
                configuration3.screenLayout |= i24 & 48;
            }
            int i25 = configuration.screenLayout & 768;
            int i26 = configuration2.screenLayout;
            if (i25 != (i26 & 768)) {
                configuration3.screenLayout |= i26 & 768;
            }
            if (i6 >= 26) {
                Api26Impl.a(configuration, configuration2, configuration3);
            }
            int i27 = configuration.uiMode & 15;
            int i28 = configuration2.uiMode;
            if (i27 != (i28 & 15)) {
                configuration3.uiMode |= i28 & 15;
            }
            int i29 = configuration.uiMode & 48;
            int i30 = configuration2.uiMode;
            if (i29 != (i30 & 48)) {
                configuration3.uiMode |= i30 & 48;
            }
            int i31 = configuration.screenWidthDp;
            int i32 = configuration2.screenWidthDp;
            if (i31 != i32) {
                configuration3.screenWidthDp = i32;
            }
            int i33 = configuration.screenHeightDp;
            int i34 = configuration2.screenHeightDp;
            if (i33 != i34) {
                configuration3.screenHeightDp = i34;
            }
            int i35 = configuration.smallestScreenWidthDp;
            int i36 = configuration2.smallestScreenWidthDp;
            if (i35 != i36) {
                configuration3.smallestScreenWidthDp = i36;
            }
            Api17Impl.b(configuration, configuration2, configuration3);
        }
        return configuration3;
    }

    private int getActivityHandlesConfigChangesFlags(Context context) throws PackageManager.NameNotFoundException {
        if (!this.mActivityHandlesConfigFlagsChecked && (this.f1976b instanceof Activity)) {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                return 0;
            }
            try {
                int i2 = Build.VERSION.SDK_INT;
                ActivityInfo activityInfo = packageManager.getActivityInfo(new ComponentName(context, this.f1976b.getClass()), i2 >= 29 ? 269221888 : i2 >= 24 ? 786432 : 0);
                if (activityInfo != null) {
                    this.mActivityHandlesConfigFlags = activityInfo.configChanges;
                }
            } catch (PackageManager.NameNotFoundException e2) {
                Log.d("AppCompatDelegate", "Exception while getting ActivityInfo", e2);
                this.mActivityHandlesConfigFlags = 0;
            }
        }
        this.mActivityHandlesConfigFlagsChecked = true;
        return this.mActivityHandlesConfigFlags;
    }

    private AutoNightModeManager getAutoBatteryNightModeManager(@NonNull Context context) {
        if (this.mAutoBatteryNightModeManager == null) {
            this.mAutoBatteryNightModeManager = new AutoBatteryNightModeManager(context);
        }
        return this.mAutoBatteryNightModeManager;
    }

    private AutoNightModeManager getAutoTimeNightModeManager(@NonNull Context context) {
        if (this.mAutoTimeNightModeManager == null) {
            this.mAutoTimeNightModeManager = new AutoTimeNightModeManager(TwilightManager.a(context));
        }
        return this.mAutoTimeNightModeManager;
    }

    private void initWindowDecorActionBar() {
        ensureSubDecor();
        if (this.f1988n && this.f1980f == null) {
            Object obj = this.f1976b;
            if (obj instanceof Activity) {
                this.f1980f = new WindowDecorActionBar((Activity) this.f1976b, this.f1989o);
            } else if (obj instanceof Dialog) {
                this.f1980f = new WindowDecorActionBar((Dialog) this.f1976b);
            }
            ActionBar actionBar = this.f1980f;
            if (actionBar != null) {
                actionBar.setDefaultDisplayHomeAsUpEnabled(this.mEnableDefaultActionBarUp);
            }
        }
    }

    private boolean initializePanelContent(PanelFeatureState panelFeatureState) {
        View view = panelFeatureState.f2022i;
        if (view != null) {
            panelFeatureState.f2021h = view;
            return true;
        }
        if (panelFeatureState.f2023j == null) {
            return false;
        }
        if (this.mPanelMenuPresenterCallback == null) {
            this.mPanelMenuPresenterCallback = new PanelMenuPresenterCallback();
        }
        View view2 = (View) panelFeatureState.a(this.mPanelMenuPresenterCallback);
        panelFeatureState.f2021h = view2;
        return view2 != null;
    }

    private boolean initializePanelDecor(PanelFeatureState panelFeatureState) {
        panelFeatureState.c(v());
        panelFeatureState.f2020g = new ListMenuDecorView(panelFeatureState.f2025l);
        panelFeatureState.f2016c = 81;
        return true;
    }

    private boolean initializePanelMenu(PanelFeatureState panelFeatureState) {
        Resources.Theme themeNewTheme;
        Context context = this.f1977c;
        int i2 = panelFeatureState.f2014a;
        if ((i2 == 0 || i2 == 108) && this.mDecorContentParent != null) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = context.getTheme();
            theme.resolveAttribute(androidx.appcompat.R.attr.actionBarTheme, typedValue, true);
            if (typedValue.resourceId != 0) {
                themeNewTheme = context.getResources().newTheme();
                themeNewTheme.setTo(theme);
                themeNewTheme.applyStyle(typedValue.resourceId, true);
                themeNewTheme.resolveAttribute(androidx.appcompat.R.attr.actionBarWidgetTheme, typedValue, true);
            } else {
                theme.resolveAttribute(androidx.appcompat.R.attr.actionBarWidgetTheme, typedValue, true);
                themeNewTheme = null;
            }
            if (typedValue.resourceId != 0) {
                if (themeNewTheme == null) {
                    themeNewTheme = context.getResources().newTheme();
                    themeNewTheme.setTo(theme);
                }
                themeNewTheme.applyStyle(typedValue.resourceId, true);
            }
            if (themeNewTheme != null) {
                androidx.appcompat.view.ContextThemeWrapper contextThemeWrapper = new androidx.appcompat.view.ContextThemeWrapper(context, 0);
                contextThemeWrapper.getTheme().setTo(themeNewTheme);
                context = contextThemeWrapper;
            }
        }
        MenuBuilder menuBuilder = new MenuBuilder(context);
        menuBuilder.setCallback(this);
        panelFeatureState.b(menuBuilder);
        return true;
    }

    private void invalidatePanelMenu(int i2) {
        this.f1995u = (1 << i2) | this.f1995u;
        if (this.f1994t) {
            return;
        }
        ViewCompat.postOnAnimation(this.f1978d.getDecorView(), this.mInvalidatePanelMenuRunnable);
        this.f1994t = true;
    }

    private boolean onKeyDownPanel(int i2, KeyEvent keyEvent) {
        if (keyEvent.getRepeatCount() != 0) {
            return false;
        }
        PanelFeatureState panelFeatureStateX = x(i2, true);
        if (panelFeatureStateX.f2028o) {
            return false;
        }
        return preparePanel(panelFeatureStateX, keyEvent);
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0062  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean onKeyUpPanel(int r5, android.view.KeyEvent r6) {
        /*
            r4 = this;
            androidx.appcompat.view.ActionMode r0 = r4.f1982h
            r1 = 0
            if (r0 == 0) goto L6
            return r1
        L6:
            r0 = 1
            androidx.appcompat.app.AppCompatDelegateImpl$PanelFeatureState r2 = r4.x(r5, r0)
            if (r5 != 0) goto L43
            androidx.appcompat.widget.DecorContentParent r5 = r4.mDecorContentParent
            if (r5 == 0) goto L43
            boolean r5 = r5.canShowOverflowMenu()
            if (r5 == 0) goto L43
            android.content.Context r5 = r4.f1977c
            android.view.ViewConfiguration r5 = android.view.ViewConfiguration.get(r5)
            boolean r5 = r5.hasPermanentMenuKey()
            if (r5 != 0) goto L43
            androidx.appcompat.widget.DecorContentParent r5 = r4.mDecorContentParent
            boolean r5 = r5.isOverflowMenuShowing()
            if (r5 != 0) goto L3c
            boolean r5 = r4.f1993s
            if (r5 != 0) goto L62
            boolean r5 = r4.preparePanel(r2, r6)
            if (r5 == 0) goto L62
            androidx.appcompat.widget.DecorContentParent r5 = r4.mDecorContentParent
            boolean r0 = r5.showOverflowMenu()
            goto L68
        L3c:
            androidx.appcompat.widget.DecorContentParent r5 = r4.mDecorContentParent
            boolean r0 = r5.hideOverflowMenu()
            goto L68
        L43:
            boolean r5 = r2.f2028o
            if (r5 != 0) goto L64
            boolean r3 = r2.f2027n
            if (r3 == 0) goto L4c
            goto L64
        L4c:
            boolean r5 = r2.f2026m
            if (r5 == 0) goto L62
            boolean r5 = r2.f2030q
            if (r5 == 0) goto L5b
            r2.f2026m = r1
            boolean r5 = r4.preparePanel(r2, r6)
            goto L5c
        L5b:
            r5 = r0
        L5c:
            if (r5 == 0) goto L62
            r4.openPanel(r2, r6)
            goto L68
        L62:
            r0 = r1
            goto L68
        L64:
            r4.p(r2, r0)
            r0 = r5
        L68:
            if (r0 == 0) goto L85
            android.content.Context r5 = r4.f1977c
            android.content.Context r5 = r5.getApplicationContext()
            java.lang.String r6 = "audio"
            java.lang.Object r5 = r5.getSystemService(r6)
            android.media.AudioManager r5 = (android.media.AudioManager) r5
            if (r5 == 0) goto L7e
            r5.playSoundEffect(r1)
            goto L85
        L7e:
            java.lang.String r5 = "AppCompatDelegate"
            java.lang.String r6 = "Couldn't get audio manager"
            android.util.Log.w(r5, r6)
        L85:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.onKeyUpPanel(int, android.view.KeyEvent):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:64:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:69:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void openPanel(androidx.appcompat.app.AppCompatDelegateImpl.PanelFeatureState r12, android.view.KeyEvent r13) {
        /*
            Method dump skipped, instructions count: 244
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.openPanel(androidx.appcompat.app.AppCompatDelegateImpl$PanelFeatureState, android.view.KeyEvent):void");
    }

    private boolean performPanelShortcut(PanelFeatureState panelFeatureState, int i2, KeyEvent keyEvent, int i3) {
        MenuBuilder menuBuilder;
        boolean zPerformShortcut = false;
        if (keyEvent.isSystem()) {
            return false;
        }
        if ((panelFeatureState.f2026m || preparePanel(panelFeatureState, keyEvent)) && (menuBuilder = panelFeatureState.f2023j) != null) {
            zPerformShortcut = menuBuilder.performShortcut(i2, keyEvent, i3);
        }
        if (zPerformShortcut && (i3 & 1) == 0 && this.mDecorContentParent == null) {
            p(panelFeatureState, true);
        }
        return zPerformShortcut;
    }

    private boolean preparePanel(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        DecorContentParent decorContentParent;
        DecorContentParent decorContentParent2;
        DecorContentParent decorContentParent3;
        if (this.f1993s) {
            return false;
        }
        if (panelFeatureState.f2026m) {
            return true;
        }
        PanelFeatureState panelFeatureState2 = this.mPreparedPanel;
        if (panelFeatureState2 != null && panelFeatureState2 != panelFeatureState) {
            p(panelFeatureState2, false);
        }
        Window.Callback callbackZ = z();
        if (callbackZ != null) {
            panelFeatureState.f2022i = callbackZ.onCreatePanelView(panelFeatureState.f2014a);
        }
        int i2 = panelFeatureState.f2014a;
        boolean z2 = i2 == 0 || i2 == 108;
        if (z2 && (decorContentParent3 = this.mDecorContentParent) != null) {
            decorContentParent3.setMenuPrepared();
        }
        if (panelFeatureState.f2022i == null && (!z2 || !(I() instanceof ToolbarActionBar))) {
            MenuBuilder menuBuilder = panelFeatureState.f2023j;
            if (menuBuilder == null || panelFeatureState.f2030q) {
                if (menuBuilder == null && (!initializePanelMenu(panelFeatureState) || panelFeatureState.f2023j == null)) {
                    return false;
                }
                if (z2 && this.mDecorContentParent != null) {
                    if (this.mActionMenuPresenterCallback == null) {
                        this.mActionMenuPresenterCallback = new ActionMenuPresenterCallback();
                    }
                    this.mDecorContentParent.setMenu(panelFeatureState.f2023j, this.mActionMenuPresenterCallback);
                }
                panelFeatureState.f2023j.stopDispatchingItemsChanged();
                if (!callbackZ.onCreatePanelMenu(panelFeatureState.f2014a, panelFeatureState.f2023j)) {
                    panelFeatureState.b(null);
                    if (z2 && (decorContentParent = this.mDecorContentParent) != null) {
                        decorContentParent.setMenu(null, this.mActionMenuPresenterCallback);
                    }
                    return false;
                }
                panelFeatureState.f2030q = false;
            }
            panelFeatureState.f2023j.stopDispatchingItemsChanged();
            Bundle bundle = panelFeatureState.f2031r;
            if (bundle != null) {
                panelFeatureState.f2023j.restoreActionViewStates(bundle);
                panelFeatureState.f2031r = null;
            }
            if (!callbackZ.onPreparePanel(0, panelFeatureState.f2022i, panelFeatureState.f2023j)) {
                if (z2 && (decorContentParent2 = this.mDecorContentParent) != null) {
                    decorContentParent2.setMenu(null, this.mActionMenuPresenterCallback);
                }
                panelFeatureState.f2023j.startDispatchingItemsChanged();
                return false;
            }
            boolean z3 = KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1;
            panelFeatureState.qwertyMode = z3;
            panelFeatureState.f2023j.setQwertyMode(z3);
            panelFeatureState.f2023j.startDispatchingItemsChanged();
        }
        panelFeatureState.f2026m = true;
        panelFeatureState.f2027n = false;
        this.mPreparedPanel = panelFeatureState;
        return true;
    }

    private void reopenMenu(boolean z2) {
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent == null || !decorContentParent.canShowOverflowMenu() || (ViewConfiguration.get(this.f1977c).hasPermanentMenuKey() && !this.mDecorContentParent.isOverflowMenuShowPending())) {
            PanelFeatureState panelFeatureStateX = x(0, true);
            panelFeatureStateX.f2029p = true;
            p(panelFeatureStateX, false);
            openPanel(panelFeatureStateX, null);
            return;
        }
        Window.Callback callbackZ = z();
        if (this.mDecorContentParent.isOverflowMenuShowing() && z2) {
            this.mDecorContentParent.hideOverflowMenu();
            if (this.f1993s) {
                return;
            }
            callbackZ.onPanelClosed(108, x(0, true).f2023j);
            return;
        }
        if (callbackZ == null || this.f1993s) {
            return;
        }
        if (this.f1994t && (this.f1995u & 1) != 0) {
            this.f1978d.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
            this.mInvalidatePanelMenuRunnable.run();
        }
        PanelFeatureState panelFeatureStateX2 = x(0, true);
        MenuBuilder menuBuilder = panelFeatureStateX2.f2023j;
        if (menuBuilder == null || panelFeatureStateX2.f2030q || !callbackZ.onPreparePanel(0, panelFeatureStateX2.f2022i, menuBuilder)) {
            return;
        }
        callbackZ.onMenuOpened(108, panelFeatureStateX2.f2023j);
        this.mDecorContentParent.showOverflowMenu();
    }

    private int sanitizeWindowFeatureId(int i2) {
        if (i2 == 8) {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
            return 108;
        }
        if (i2 != 9) {
            return i2;
        }
        Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
        return 109;
    }

    private boolean shouldInheritContext(ViewParent viewParent) {
        if (viewParent == null) {
            return false;
        }
        View decorView = this.f1978d.getDecorView();
        while (viewParent != null) {
            if (viewParent == decorView || !(viewParent instanceof View) || ViewCompat.isAttachedToWindow((View) viewParent)) {
                return false;
            }
            viewParent = viewParent.getParent();
        }
        return true;
    }

    private void throwFeatureRequestIfSubDecorInstalled() {
        if (this.mSubDecorInstalled) {
            throw new AndroidRuntimeException("Window feature must be requested before adding content");
        }
    }

    @Nullable
    private AppCompatActivity tryUnwrapContext() {
        for (Context baseContext = this.f1977c; baseContext != null; baseContext = ((ContextWrapper) baseContext).getBaseContext()) {
            if (baseContext instanceof AppCompatActivity) {
                return (AppCompatActivity) baseContext;
            }
            if (!(baseContext instanceof ContextWrapper)) {
                break;
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void updateActivityConfiguration(Configuration configuration) {
        Activity activity = (Activity) this.f1976b;
        if (activity instanceof LifecycleOwner) {
            if (((LifecycleOwner) activity).getLifecycle().getState().isAtLeast(Lifecycle.State.CREATED)) {
                activity.onConfigurationChanged(configuration);
            }
        } else {
            if (!this.mCreated || this.f1993s) {
                return;
            }
            activity.onConfigurationChanged(configuration);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x006f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean updateAppConfiguration(int r9, @androidx.annotation.Nullable androidx.core.os.LocaleListCompat r10, boolean r11) throws java.lang.IllegalAccessException, java.lang.NoSuchFieldException, android.content.pm.PackageManager.NameNotFoundException, java.lang.SecurityException, java.lang.IllegalArgumentException {
        /*
            r8 = this;
            android.content.Context r1 = r8.f1977c
            r4 = 0
            r5 = 0
            r0 = r8
            r2 = r9
            r3 = r10
            android.content.res.Configuration r0 = r0.createOverrideAppConfiguration(r1, r2, r3, r4, r5)
            android.content.Context r1 = r8.f1977c
            int r1 = r8.getActivityHandlesConfigChangesFlags(r1)
            android.content.res.Configuration r2 = r8.mEffectiveConfiguration
            if (r2 != 0) goto L1f
            android.content.Context r2 = r8.f1977c
            android.content.res.Resources r2 = r2.getResources()
            android.content.res.Configuration r2 = r2.getConfiguration()
        L1f:
            int r3 = r2.uiMode
            r3 = r3 & 48
            int r4 = r0.uiMode
            r4 = r4 & 48
            androidx.core.os.LocaleListCompat r2 = r8.w(r2)
            r5 = 0
            if (r10 != 0) goto L30
            r0 = r5
            goto L34
        L30:
            androidx.core.os.LocaleListCompat r0 = r8.w(r0)
        L34:
            r6 = 0
            if (r3 == r4) goto L3a
            r3 = 512(0x200, float:7.17E-43)
            goto L3b
        L3a:
            r3 = r6
        L3b:
            if (r0 == 0) goto L45
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L45
            r3 = r3 | 8196(0x2004, float:1.1485E-41)
        L45:
            int r2 = ~r1
            r2 = r2 & r3
            r7 = 1
            if (r2 == 0) goto L6f
            if (r11 == 0) goto L6f
            boolean r11 = r8.mBaseContextAttached
            if (r11 == 0) goto L6f
            boolean r11 = androidx.appcompat.app.AppCompatDelegateImpl.sCanReturnDifferentContext
            if (r11 != 0) goto L58
            boolean r11 = r8.mCreated
            if (r11 == 0) goto L6f
        L58:
            java.lang.Object r11 = r8.f1976b
            boolean r2 = r11 instanceof android.app.Activity
            if (r2 == 0) goto L6f
            android.app.Activity r11 = (android.app.Activity) r11
            boolean r11 = r11.isChild()
            if (r11 != 0) goto L6f
            java.lang.Object r11 = r8.f1976b
            android.app.Activity r11 = (android.app.Activity) r11
            androidx.core.app.ActivityCompat.recreate(r11)
            r11 = r7
            goto L70
        L6f:
            r11 = r6
        L70:
            if (r11 != 0) goto L7d
            if (r3 == 0) goto L7d
            r11 = r3 & r1
            if (r11 != r3) goto L79
            r6 = r7
        L79:
            r8.updateResourcesConfiguration(r4, r0, r6, r5)
            goto L7e
        L7d:
            r7 = r11
        L7e:
            if (r7 == 0) goto L9a
            java.lang.Object r11 = r8.f1976b
            boolean r1 = r11 instanceof androidx.appcompat.app.AppCompatActivity
            if (r1 == 0) goto L9a
            r1 = r3 & 512(0x200, float:7.17E-43)
            if (r1 == 0) goto L8f
            androidx.appcompat.app.AppCompatActivity r11 = (androidx.appcompat.app.AppCompatActivity) r11
            r11.onNightModeChanged(r9)
        L8f:
            r9 = r3 & 4
            if (r9 == 0) goto L9a
            java.lang.Object r9 = r8.f1976b
            androidx.appcompat.app.AppCompatActivity r9 = (androidx.appcompat.app.AppCompatActivity) r9
            r9.onLocalesChanged(r10)
        L9a:
            if (r7 == 0) goto Laf
            if (r0 == 0) goto Laf
            android.content.Context r9 = r8.f1977c
            android.content.res.Resources r9 = r9.getResources()
            android.content.res.Configuration r9 = r9.getConfiguration()
            androidx.core.os.LocaleListCompat r9 = r8.w(r9)
            r8.K(r9)
        Laf:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.updateAppConfiguration(int, androidx.core.os.LocaleListCompat, boolean):boolean");
    }

    private void updateResourcesConfiguration(int i2, @Nullable LocaleListCompat localeListCompat, boolean z2, @Nullable Configuration configuration) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Resources resources = this.f1977c.getResources();
        Configuration configuration2 = new Configuration(resources.getConfiguration());
        if (configuration != null) {
            configuration2.updateFrom(configuration);
        }
        configuration2.uiMode = i2 | (resources.getConfiguration().uiMode & (-49));
        if (localeListCompat != null) {
            J(configuration2, localeListCompat);
        }
        resources.updateConfiguration(configuration2, null);
        if (Build.VERSION.SDK_INT < 26) {
            ResourcesFlusher.a(resources);
        }
        int i3 = this.mThemeResId;
        if (i3 != 0) {
            this.f1977c.setTheme(i3);
            this.f1977c.getTheme().applyStyle(this.mThemeResId, true);
        }
        if (z2 && (this.f1976b instanceof Activity)) {
            updateActivityConfiguration(configuration2);
        }
    }

    private void updateStatusGuardColor(View view) {
        view.setBackgroundColor((ViewCompat.getWindowSystemUiVisibility(view) & 8192) != 0 ? ContextCompat.getColor(this.f1977c, androidx.appcompat.R.color.abc_decor_view_status_guard_light) : ContextCompat.getColor(this.f1977c, androidx.appcompat.R.color.abc_decor_view_status_guard));
    }

    int A(Context context, int i2) {
        if (i2 == -100) {
            return -1;
        }
        if (i2 != -1) {
            if (i2 == 0) {
                if (((UiModeManager) context.getApplicationContext().getSystemService("uimode")).getNightMode() == 0) {
                    return -1;
                }
                return getAutoTimeNightModeManager(context).getApplyableNightMode();
            }
            if (i2 != 1 && i2 != 2) {
                if (i2 == 3) {
                    return getAutoBatteryNightModeManager(context).getApplyableNightMode();
                }
                throw new IllegalStateException("Unknown value set for night mode. Please use one of the MODE_NIGHT values from AppCompatDelegate.");
            }
        }
        return i2;
    }

    boolean B() {
        boolean z2 = this.mLongPressBackDown;
        this.mLongPressBackDown = false;
        PanelFeatureState panelFeatureStateX = x(0, false);
        if (panelFeatureStateX != null && panelFeatureStateX.f2028o) {
            if (!z2) {
                p(panelFeatureStateX, true);
            }
            return true;
        }
        androidx.appcompat.view.ActionMode actionMode = this.f1982h;
        if (actionMode != null) {
            actionMode.finish();
            return true;
        }
        ActionBar supportActionBar = getSupportActionBar();
        return supportActionBar != null && supportActionBar.collapseActionView();
    }

    boolean C(int i2, KeyEvent keyEvent) {
        if (i2 == 4) {
            this.mLongPressBackDown = (keyEvent.getFlags() & 128) != 0;
        } else if (i2 == 82) {
            onKeyDownPanel(0, keyEvent);
            return true;
        }
        return false;
    }

    boolean D(int i2, KeyEvent keyEvent) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null && supportActionBar.onKeyShortcut(i2, keyEvent)) {
            return true;
        }
        PanelFeatureState panelFeatureState = this.mPreparedPanel;
        if (panelFeatureState != null && performPanelShortcut(panelFeatureState, keyEvent.getKeyCode(), keyEvent, 1)) {
            PanelFeatureState panelFeatureState2 = this.mPreparedPanel;
            if (panelFeatureState2 != null) {
                panelFeatureState2.f2027n = true;
            }
            return true;
        }
        if (this.mPreparedPanel == null) {
            PanelFeatureState panelFeatureStateX = x(0, true);
            preparePanel(panelFeatureStateX, keyEvent);
            boolean zPerformPanelShortcut = performPanelShortcut(panelFeatureStateX, keyEvent.getKeyCode(), keyEvent, 1);
            panelFeatureStateX.f2026m = false;
            if (zPerformPanelShortcut) {
                return true;
            }
        }
        return false;
    }

    boolean E(int i2, KeyEvent keyEvent) {
        if (i2 != 4) {
            if (i2 == 82) {
                onKeyUpPanel(0, keyEvent);
                return true;
            }
        } else if (B()) {
            return true;
        }
        return false;
    }

    void F(int i2) {
        ActionBar supportActionBar;
        if (i2 != 108 || (supportActionBar = getSupportActionBar()) == null) {
            return;
        }
        supportActionBar.dispatchMenuVisibilityChanged(true);
    }

    void G(int i2) {
        if (i2 == 108) {
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.dispatchMenuVisibilityChanged(false);
                return;
            }
            return;
        }
        if (i2 == 0) {
            PanelFeatureState panelFeatureStateX = x(i2, true);
            if (panelFeatureStateX.f2028o) {
                p(panelFeatureStateX, false);
            }
        }
    }

    void H(ViewGroup viewGroup) {
    }

    final ActionBar I() {
        return this.f1980f;
    }

    void J(Configuration configuration, LocaleListCompat localeListCompat) {
        if (Build.VERSION.SDK_INT >= 24) {
            Api24Impl.c(configuration, localeListCompat);
        } else {
            Api17Impl.d(configuration, localeListCompat.get(0));
            Api17Impl.c(configuration, localeListCompat.get(0));
        }
    }

    void K(LocaleListCompat localeListCompat) {
        if (Build.VERSION.SDK_INT >= 24) {
            Api24Impl.setDefaultLocales(localeListCompat);
        } else {
            Locale.setDefault(localeListCompat.get(0));
        }
    }

    final boolean L() {
        ViewGroup viewGroup;
        return this.mSubDecorInstalled && (viewGroup = this.f1987m) != null && ViewCompat.isLaidOut(viewGroup);
    }

    boolean M() {
        if (this.mDispatcher == null) {
            return false;
        }
        PanelFeatureState panelFeatureStateX = x(0, false);
        return (panelFeatureStateX != null && panelFeatureStateX.f2028o) || this.f1982h != null;
    }

    androidx.appcompat.view.ActionMode N(ActionMode.Callback callback) {
        androidx.appcompat.view.ActionMode actionModeOnWindowStartingSupportActionMode;
        Context contextThemeWrapper;
        AppCompatCallback appCompatCallback;
        t();
        androidx.appcompat.view.ActionMode actionMode = this.f1982h;
        if (actionMode != null) {
            actionMode.finish();
        }
        if (!(callback instanceof ActionModeCallbackWrapperV9)) {
            callback = new ActionModeCallbackWrapperV9(callback);
        }
        AppCompatCallback appCompatCallback2 = this.f1979e;
        if (appCompatCallback2 == null || this.f1993s) {
            actionModeOnWindowStartingSupportActionMode = null;
        } else {
            try {
                actionModeOnWindowStartingSupportActionMode = appCompatCallback2.onWindowStartingSupportActionMode(callback);
            } catch (AbstractMethodError unused) {
            }
        }
        if (actionModeOnWindowStartingSupportActionMode != null) {
            this.f1982h = actionModeOnWindowStartingSupportActionMode;
        } else {
            if (this.f1983i == null) {
                if (this.f1991q) {
                    TypedValue typedValue = new TypedValue();
                    Resources.Theme theme = this.f1977c.getTheme();
                    theme.resolveAttribute(androidx.appcompat.R.attr.actionBarTheme, typedValue, true);
                    if (typedValue.resourceId != 0) {
                        Resources.Theme themeNewTheme = this.f1977c.getResources().newTheme();
                        themeNewTheme.setTo(theme);
                        themeNewTheme.applyStyle(typedValue.resourceId, true);
                        contextThemeWrapper = new androidx.appcompat.view.ContextThemeWrapper(this.f1977c, 0);
                        contextThemeWrapper.getTheme().setTo(themeNewTheme);
                    } else {
                        contextThemeWrapper = this.f1977c;
                    }
                    this.f1983i = new ActionBarContextView(contextThemeWrapper);
                    PopupWindow popupWindow = new PopupWindow(contextThemeWrapper, (AttributeSet) null, androidx.appcompat.R.attr.actionModePopupWindowStyle);
                    this.f1984j = popupWindow;
                    PopupWindowCompat.setWindowLayoutType(popupWindow, 2);
                    this.f1984j.setContentView(this.f1983i);
                    this.f1984j.setWidth(-1);
                    contextThemeWrapper.getTheme().resolveAttribute(androidx.appcompat.R.attr.actionBarSize, typedValue, true);
                    this.f1983i.setContentHeight(TypedValue.complexToDimensionPixelSize(typedValue.data, contextThemeWrapper.getResources().getDisplayMetrics()));
                    this.f1984j.setHeight(-2);
                    this.f1985k = new Runnable() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.6
                        @Override // java.lang.Runnable
                        public void run() {
                            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
                            appCompatDelegateImpl.f1984j.showAtLocation(appCompatDelegateImpl.f1983i, 55, 0, 0);
                            AppCompatDelegateImpl.this.t();
                            if (!AppCompatDelegateImpl.this.L()) {
                                AppCompatDelegateImpl.this.f1983i.setAlpha(1.0f);
                                AppCompatDelegateImpl.this.f1983i.setVisibility(0);
                            } else {
                                AppCompatDelegateImpl.this.f1983i.setAlpha(0.0f);
                                AppCompatDelegateImpl appCompatDelegateImpl2 = AppCompatDelegateImpl.this;
                                appCompatDelegateImpl2.f1986l = ViewCompat.animate(appCompatDelegateImpl2.f1983i).alpha(1.0f);
                                AppCompatDelegateImpl.this.f1986l.setListener(new ViewPropertyAnimatorListenerAdapter() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.6.1
                                    @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                                    public void onAnimationEnd(View view) {
                                        AppCompatDelegateImpl.this.f1983i.setAlpha(1.0f);
                                        AppCompatDelegateImpl.this.f1986l.setListener(null);
                                        AppCompatDelegateImpl.this.f1986l = null;
                                    }

                                    @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                                    public void onAnimationStart(View view) {
                                        AppCompatDelegateImpl.this.f1983i.setVisibility(0);
                                    }
                                });
                            }
                        }
                    };
                } else {
                    ViewStubCompat viewStubCompat = (ViewStubCompat) this.f1987m.findViewById(androidx.appcompat.R.id.action_mode_bar_stub);
                    if (viewStubCompat != null) {
                        viewStubCompat.setLayoutInflater(LayoutInflater.from(v()));
                        this.f1983i = (ActionBarContextView) viewStubCompat.inflate();
                    }
                }
            }
            if (this.f1983i != null) {
                t();
                this.f1983i.killMode();
                StandaloneActionMode standaloneActionMode = new StandaloneActionMode(this.f1983i.getContext(), this.f1983i, callback, this.f1984j == null);
                if (callback.onCreateActionMode(standaloneActionMode, standaloneActionMode.getMenu())) {
                    standaloneActionMode.invalidate();
                    this.f1983i.initForMode(standaloneActionMode);
                    this.f1982h = standaloneActionMode;
                    if (L()) {
                        this.f1983i.setAlpha(0.0f);
                        ViewPropertyAnimatorCompat viewPropertyAnimatorCompatAlpha = ViewCompat.animate(this.f1983i).alpha(1.0f);
                        this.f1986l = viewPropertyAnimatorCompatAlpha;
                        viewPropertyAnimatorCompatAlpha.setListener(new ViewPropertyAnimatorListenerAdapter() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.7
                            @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                            public void onAnimationEnd(View view) {
                                AppCompatDelegateImpl.this.f1983i.setAlpha(1.0f);
                                AppCompatDelegateImpl.this.f1986l.setListener(null);
                                AppCompatDelegateImpl.this.f1986l = null;
                            }

                            @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                            public void onAnimationStart(View view) {
                                AppCompatDelegateImpl.this.f1983i.setVisibility(0);
                                if (AppCompatDelegateImpl.this.f1983i.getParent() instanceof View) {
                                    ViewCompat.requestApplyInsets((View) AppCompatDelegateImpl.this.f1983i.getParent());
                                }
                            }
                        });
                    } else {
                        this.f1983i.setAlpha(1.0f);
                        this.f1983i.setVisibility(0);
                        if (this.f1983i.getParent() instanceof View) {
                            ViewCompat.requestApplyInsets((View) this.f1983i.getParent());
                        }
                    }
                    if (this.f1984j != null) {
                        this.f1978d.getDecorView().post(this.f1985k);
                    }
                } else {
                    this.f1982h = null;
                }
            }
        }
        androidx.appcompat.view.ActionMode actionMode2 = this.f1982h;
        if (actionMode2 != null && (appCompatCallback = this.f1979e) != null) {
            appCompatCallback.onSupportActionModeStarted(actionMode2);
        }
        O();
        return this.f1982h;
    }

    void O() {
        OnBackInvokedCallback onBackInvokedCallback;
        if (Build.VERSION.SDK_INT >= 33) {
            boolean zM = M();
            if (zM && this.mBackCallback == null) {
                this.mBackCallback = Api33Impl.b(this.mDispatcher, this);
            } else {
                if (zM || (onBackInvokedCallback = this.mBackCallback) == null) {
                    return;
                }
                Api33Impl.c(this.mDispatcher, onBackInvokedCallback);
            }
        }
    }

    final int P(WindowInsetsCompat windowInsetsCompat, Rect rect) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        boolean z2;
        boolean z3;
        int systemWindowInsetTop = windowInsetsCompat != null ? windowInsetsCompat.getSystemWindowInsetTop() : rect != null ? rect.top : 0;
        ActionBarContextView actionBarContextView = this.f1983i;
        if (actionBarContextView == null || !(actionBarContextView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            z2 = false;
        } else {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.f1983i.getLayoutParams();
            if (this.f1983i.isShown()) {
                if (this.mTempRect1 == null) {
                    this.mTempRect1 = new Rect();
                    this.mTempRect2 = new Rect();
                }
                Rect rect2 = this.mTempRect1;
                Rect rect3 = this.mTempRect2;
                if (windowInsetsCompat == null) {
                    rect2.set(rect);
                } else {
                    rect2.set(windowInsetsCompat.getSystemWindowInsetLeft(), windowInsetsCompat.getSystemWindowInsetTop(), windowInsetsCompat.getSystemWindowInsetRight(), windowInsetsCompat.getSystemWindowInsetBottom());
                }
                ViewUtils.computeFitSystemWindows(this.f1987m, rect2, rect3);
                int i2 = rect2.top;
                int i3 = rect2.left;
                int i4 = rect2.right;
                WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(this.f1987m);
                int systemWindowInsetLeft = rootWindowInsets == null ? 0 : rootWindowInsets.getSystemWindowInsetLeft();
                int systemWindowInsetRight = rootWindowInsets == null ? 0 : rootWindowInsets.getSystemWindowInsetRight();
                if (marginLayoutParams.topMargin == i2 && marginLayoutParams.leftMargin == i3 && marginLayoutParams.rightMargin == i4) {
                    z3 = false;
                } else {
                    marginLayoutParams.topMargin = i2;
                    marginLayoutParams.leftMargin = i3;
                    marginLayoutParams.rightMargin = i4;
                    z3 = true;
                }
                if (i2 <= 0 || this.mStatusGuard != null) {
                    View view = this.mStatusGuard;
                    if (view != null) {
                        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                        int i5 = marginLayoutParams2.height;
                        int i6 = marginLayoutParams.topMargin;
                        if (i5 != i6 || marginLayoutParams2.leftMargin != systemWindowInsetLeft || marginLayoutParams2.rightMargin != systemWindowInsetRight) {
                            marginLayoutParams2.height = i6;
                            marginLayoutParams2.leftMargin = systemWindowInsetLeft;
                            marginLayoutParams2.rightMargin = systemWindowInsetRight;
                            this.mStatusGuard.setLayoutParams(marginLayoutParams2);
                        }
                    }
                } else {
                    View view2 = new View(this.f1977c);
                    this.mStatusGuard = view2;
                    view2.setVisibility(8);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, marginLayoutParams.topMargin, 51);
                    layoutParams.leftMargin = systemWindowInsetLeft;
                    layoutParams.rightMargin = systemWindowInsetRight;
                    this.f1987m.addView(this.mStatusGuard, -1, layoutParams);
                }
                View view3 = this.mStatusGuard;
                z = view3 != null;
                if (z && view3.getVisibility() != 0) {
                    updateStatusGuardColor(this.mStatusGuard);
                }
                if (!this.f1990p && z) {
                    systemWindowInsetTop = 0;
                }
                z2 = z;
                z = z3;
            } else if (marginLayoutParams.topMargin != 0) {
                marginLayoutParams.topMargin = 0;
                z2 = false;
            } else {
                z2 = false;
                z = false;
            }
            if (z) {
                this.f1983i.setLayoutParams(marginLayoutParams);
            }
        }
        View view4 = this.mStatusGuard;
        if (view4 != null) {
            view4.setVisibility(z2 ? 0 : 8);
        }
        return systemWindowInsetTop;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        ensureSubDecor();
        ((ViewGroup) this.f1987m.findViewById(R.id.content)).addView(view, layoutParams);
        this.mAppCompatWindowCallback.bypassOnContentChanged(this.f1978d.getCallback());
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public boolean applyDayNight() {
        return applyApplicationSpecificConfig(true);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    @NonNull
    @CallSuper
    public Context attachBaseContext2(@NonNull Context context) {
        this.mBaseContextAttached = true;
        int iA = A(context, calculateNightMode());
        if (AppCompatDelegate.i(context)) {
            AppCompatDelegate.k(context);
        }
        LocaleListCompat localeListCompatL = l(context);
        if (sCanApplyOverrideConfiguration && (context instanceof ContextThemeWrapper)) {
            try {
                ContextThemeWrapperCompatApi17Impl.a((ContextThemeWrapper) context, createOverrideAppConfiguration(context, iA, localeListCompatL, null, false));
                return context;
            } catch (IllegalStateException unused) {
            }
        }
        if (context instanceof androidx.appcompat.view.ContextThemeWrapper) {
            try {
                ((androidx.appcompat.view.ContextThemeWrapper) context).applyOverrideConfiguration(createOverrideAppConfiguration(context, iA, localeListCompatL, null, false));
                return context;
            } catch (IllegalStateException unused2) {
            }
        }
        if (!sCanReturnDifferentContext) {
            return super.attachBaseContext2(context);
        }
        Configuration configuration = new Configuration();
        configuration.uiMode = -1;
        configuration.fontScale = 0.0f;
        Configuration configuration2 = Api17Impl.a(context, configuration).getResources().getConfiguration();
        Configuration configuration3 = context.getResources().getConfiguration();
        configuration2.uiMode = configuration3.uiMode;
        Configuration configurationCreateOverrideAppConfiguration = createOverrideAppConfiguration(context, iA, localeListCompatL, !configuration2.equals(configuration3) ? generateConfigDelta(configuration2, configuration3) : null, true);
        androidx.appcompat.view.ContextThemeWrapper contextThemeWrapper = new androidx.appcompat.view.ContextThemeWrapper(context, androidx.appcompat.R.style.Theme_AppCompat_Empty);
        contextThemeWrapper.applyOverrideConfiguration(configurationCreateOverrideAppConfiguration);
        try {
            if (context.getTheme() != null) {
                ResourcesCompat.ThemeCompat.rebase(contextThemeWrapper.getTheme());
            }
        } catch (NullPointerException unused3) {
        }
        return super.attachBaseContext2(contextThemeWrapper);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.appcompat.app.AppCompatDelegate
    public View createView(View view, String str, @NonNull Context context, @NonNull AttributeSet attributeSet) {
        boolean z2;
        if (this.mAppCompatViewInflater == null) {
            String string = this.f1977c.obtainStyledAttributes(androidx.appcompat.R.styleable.AppCompatTheme).getString(androidx.appcompat.R.styleable.AppCompatTheme_viewInflaterClass);
            if (string == null) {
                this.mAppCompatViewInflater = new AppCompatViewInflater();
            } else {
                try {
                    this.mAppCompatViewInflater = (AppCompatViewInflater) this.f1977c.getClassLoader().loadClass(string).getDeclaredConstructor(null).newInstance(null);
                } catch (Throwable th) {
                    Log.i("AppCompatDelegate", "Failed to instantiate custom view inflater " + string + ". Falling back to default.", th);
                    this.mAppCompatViewInflater = new AppCompatViewInflater();
                }
            }
        }
        boolean z3 = IS_PRE_LOLLIPOP;
        boolean zShouldInheritContext = false;
        if (z3) {
            if (this.mLayoutIncludeDetector == null) {
                this.mLayoutIncludeDetector = new LayoutIncludeDetector();
            }
            if (this.mLayoutIncludeDetector.a(attributeSet)) {
                z2 = true;
            } else {
                if (!(attributeSet instanceof XmlPullParser)) {
                    zShouldInheritContext = shouldInheritContext((ViewParent) view);
                } else if (((XmlPullParser) attributeSet).getDepth() > 1) {
                    zShouldInheritContext = true;
                }
                z2 = zShouldInheritContext;
            }
        } else {
            z2 = zShouldInheritContext;
        }
        return this.mAppCompatViewInflater.createView(view, str, context, attributeSet, z2, z3, true, VectorEnabledTintResources.shouldBeUsed());
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    boolean d() {
        if (AppCompatDelegate.i(this.f1977c) && AppCompatDelegate.g() != null && !AppCompatDelegate.g().equals(AppCompatDelegate.h())) {
            e(this.f1977c);
        }
        return applyApplicationSpecificConfig(true);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    @Nullable
    public <T extends View> T findViewById(@IdRes int i2) {
        ensureSubDecor();
        return (T) this.f1978d.findViewById(i2);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public Context getContextForDelegate() {
        return this.f1977c;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final ActionBarDrawerToggle.Delegate getDrawerToggleDelegate() {
        return new ActionBarDrawableToggleImpl();
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public int getLocalNightMode() {
        return this.mLocalNightMode;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public MenuInflater getMenuInflater() {
        if (this.f1981g == null) {
            initWindowDecorActionBar();
            ActionBar actionBar = this.f1980f;
            this.f1981g = new SupportMenuInflater(actionBar != null ? actionBar.getThemedContext() : this.f1977c);
        }
        return this.f1981g;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public ActionBar getSupportActionBar() {
        initWindowDecorActionBar();
        return this.f1980f;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public boolean hasWindowFeature(int i2) {
        int iSanitizeWindowFeatureId = sanitizeWindowFeatureId(i2);
        return (iSanitizeWindowFeatureId != 1 ? iSanitizeWindowFeatureId != 2 ? iSanitizeWindowFeatureId != 5 ? iSanitizeWindowFeatureId != 10 ? iSanitizeWindowFeatureId != 108 ? iSanitizeWindowFeatureId != 109 ? false : this.f1989o : this.f1988n : this.f1990p : this.mFeatureIndeterminateProgress : this.mFeatureProgress : this.f1992r) || this.f1978d.hasFeature(i2);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void installViewFactory() {
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.f1977c);
        if (layoutInflaterFrom.getFactory() == null) {
            LayoutInflaterCompat.setFactory2(layoutInflaterFrom, this);
        } else {
            if (layoutInflaterFrom.getFactory2() instanceof AppCompatDelegateImpl) {
                return;
            }
            Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void invalidateOptionsMenu() {
        if (I() == null || getSupportActionBar().invalidateOptionsMenu()) {
            return;
        }
        invalidatePanelMenu(0);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public boolean isHandleNativeActionModesEnabled() {
        return this.mHandleNativeActionModes;
    }

    LocaleListCompat l(Context context) {
        LocaleListCompat localeListCompatG;
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 33 || (localeListCompatG = AppCompatDelegate.g()) == null) {
            return null;
        }
        LocaleListCompat localeListCompatW = w(context.getApplicationContext().getResources().getConfiguration());
        LocaleListCompat localeListCompatA = i2 >= 24 ? LocaleOverlayHelper.a(localeListCompatG, localeListCompatW) : localeListCompatG.isEmpty() ? LocaleListCompat.getEmptyLocaleList() : LocaleListCompat.forLanguageTags(localeListCompatG.get(0).toString());
        return localeListCompatA.isEmpty() ? localeListCompatW : localeListCompatA;
    }

    void m(int i2, PanelFeatureState panelFeatureState, Menu menu) {
        if (menu == null) {
            if (panelFeatureState == null && i2 >= 0) {
                PanelFeatureState[] panelFeatureStateArr = this.mPanels;
                if (i2 < panelFeatureStateArr.length) {
                    panelFeatureState = panelFeatureStateArr[i2];
                }
            }
            if (panelFeatureState != null) {
                menu = panelFeatureState.f2023j;
            }
        }
        if ((panelFeatureState == null || panelFeatureState.f2028o) && !this.f1993s) {
            this.mAppCompatWindowCallback.bypassOnPanelClosed(this.f1978d.getCallback(), i2, menu);
        }
    }

    void n(MenuBuilder menuBuilder) {
        if (this.mClosingActionMenu) {
            return;
        }
        this.mClosingActionMenu = true;
        this.mDecorContentParent.dismissPopups();
        Window.Callback callbackZ = z();
        if (callbackZ != null && !this.f1993s) {
            callbackZ.onPanelClosed(108, menuBuilder);
        }
        this.mClosingActionMenu = false;
    }

    void o(int i2) {
        p(x(i2, true), true);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void onConfigurationChanged(Configuration configuration) throws IllegalAccessException, NoSuchFieldException, PackageManager.NameNotFoundException, SecurityException, IllegalArgumentException {
        ActionBar supportActionBar;
        if (this.f1988n && this.mSubDecorInstalled && (supportActionBar = getSupportActionBar()) != null) {
            supportActionBar.onConfigurationChanged(configuration);
        }
        AppCompatDrawableManager.get().onConfigurationChanged(this.f1977c);
        this.mEffectiveConfiguration = new Configuration(this.f1977c.getResources().getConfiguration());
        applyApplicationSpecificConfig(false, false);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void onCreate(Bundle bundle) {
        String parentActivityName;
        this.mBaseContextAttached = true;
        applyApplicationSpecificConfig(false);
        ensureWindow();
        Object obj = this.f1976b;
        if (obj instanceof Activity) {
            try {
                parentActivityName = NavUtils.getParentActivityName((Activity) obj);
            } catch (IllegalArgumentException unused) {
                parentActivityName = null;
            }
            if (parentActivityName != null) {
                ActionBar actionBarI = I();
                if (actionBarI == null) {
                    this.mEnableDefaultActionBarUp = true;
                } else {
                    actionBarI.setDefaultDisplayHomeAsUpEnabled(true);
                }
            }
            AppCompatDelegate.c(this);
        }
        this.mEffectiveConfiguration = new Configuration(this.f1977c.getResources().getConfiguration());
        this.mCreated = true;
    }

    @Override // android.view.LayoutInflater.Factory2
    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return createView(view, str, context, attributeSet);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0045  */
    @Override // androidx.appcompat.app.AppCompatDelegate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onDestroy() {
        /*
            r3 = this;
            java.lang.Object r0 = r3.f1976b
            boolean r0 = r0 instanceof android.app.Activity
            if (r0 == 0) goto L9
            androidx.appcompat.app.AppCompatDelegate.j(r3)
        L9:
            boolean r0 = r3.f1994t
            if (r0 == 0) goto L18
            android.view.Window r0 = r3.f1978d
            android.view.View r0 = r0.getDecorView()
            java.lang.Runnable r1 = r3.mInvalidatePanelMenuRunnable
            r0.removeCallbacks(r1)
        L18:
            r0 = 1
            r3.f1993s = r0
            int r0 = r3.mLocalNightMode
            r1 = -100
            if (r0 == r1) goto L45
            java.lang.Object r0 = r3.f1976b
            boolean r1 = r0 instanceof android.app.Activity
            if (r1 == 0) goto L45
            android.app.Activity r0 = (android.app.Activity) r0
            boolean r0 = r0.isChangingConfigurations()
            if (r0 == 0) goto L45
            androidx.collection.SimpleArrayMap<java.lang.String, java.lang.Integer> r0 = androidx.appcompat.app.AppCompatDelegateImpl.sLocalNightModes
            java.lang.Object r1 = r3.f1976b
            java.lang.Class r1 = r1.getClass()
            java.lang.String r1 = r1.getName()
            int r2 = r3.mLocalNightMode
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r0.put(r1, r2)
            goto L54
        L45:
            androidx.collection.SimpleArrayMap<java.lang.String, java.lang.Integer> r0 = androidx.appcompat.app.AppCompatDelegateImpl.sLocalNightModes
            java.lang.Object r1 = r3.f1976b
            java.lang.Class r1 = r1.getClass()
            java.lang.String r1 = r1.getName()
            r0.remove(r1)
        L54:
            androidx.appcompat.app.ActionBar r0 = r3.f1980f
            if (r0 == 0) goto L5b
            r0.a()
        L5b:
            r3.cleanupAutoManagers()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.onDestroy():void");
    }

    @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
    public boolean onMenuItemSelected(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem) {
        PanelFeatureState panelFeatureStateU;
        Window.Callback callbackZ = z();
        if (callbackZ == null || this.f1993s || (panelFeatureStateU = u(menuBuilder.getRootMenu())) == null) {
            return false;
        }
        return callbackZ.onMenuItemSelected(panelFeatureStateU.f2014a, menuItem);
    }

    @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
    public void onMenuModeChange(@NonNull MenuBuilder menuBuilder) {
        reopenMenu(true);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void onPostCreate(Bundle bundle) {
        ensureSubDecor();
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void onPostResume() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setShowHideAnimationEnabled(true);
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void onSaveInstanceState(Bundle bundle) {
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void onStart() throws IllegalAccessException, NoSuchFieldException, PackageManager.NameNotFoundException, SecurityException, IllegalArgumentException {
        applyApplicationSpecificConfig(true, false);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void onStop() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setShowHideAnimationEnabled(false);
        }
    }

    void p(PanelFeatureState panelFeatureState, boolean z2) {
        ViewGroup viewGroup;
        DecorContentParent decorContentParent;
        if (z2 && panelFeatureState.f2014a == 0 && (decorContentParent = this.mDecorContentParent) != null && decorContentParent.isOverflowMenuShowing()) {
            n(panelFeatureState.f2023j);
            return;
        }
        WindowManager windowManager = (WindowManager) this.f1977c.getSystemService("window");
        if (windowManager != null && panelFeatureState.f2028o && (viewGroup = panelFeatureState.f2020g) != null) {
            windowManager.removeView(viewGroup);
            if (z2) {
                m(panelFeatureState.f2014a, panelFeatureState, null);
            }
        }
        panelFeatureState.f2026m = false;
        panelFeatureState.f2027n = false;
        panelFeatureState.f2028o = false;
        panelFeatureState.f2021h = null;
        panelFeatureState.f2029p = true;
        if (this.mPreparedPanel == panelFeatureState) {
            this.mPreparedPanel = null;
        }
        if (panelFeatureState.f2014a == 0) {
            O();
        }
    }

    void q() {
        MenuBuilder menuBuilder;
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent != null) {
            decorContentParent.dismissPopups();
        }
        if (this.f1984j != null) {
            this.f1978d.getDecorView().removeCallbacks(this.f1985k);
            if (this.f1984j.isShowing()) {
                try {
                    this.f1984j.dismiss();
                } catch (IllegalArgumentException unused) {
                }
            }
            this.f1984j = null;
        }
        t();
        PanelFeatureState panelFeatureStateX = x(0, false);
        if (panelFeatureStateX == null || (menuBuilder = panelFeatureStateX.f2023j) == null) {
            return;
        }
        menuBuilder.close();
    }

    boolean r(KeyEvent keyEvent) {
        View decorView;
        Object obj = this.f1976b;
        if (((obj instanceof KeyEventDispatcher.Component) || (obj instanceof AppCompatDialog)) && (decorView = this.f1978d.getDecorView()) != null && KeyEventDispatcher.dispatchBeforeHierarchy(decorView, keyEvent)) {
            return true;
        }
        if (keyEvent.getKeyCode() == 82 && this.mAppCompatWindowCallback.bypassDispatchKeyEvent(this.f1978d.getCallback(), keyEvent)) {
            return true;
        }
        int keyCode = keyEvent.getKeyCode();
        return keyEvent.getAction() == 0 ? C(keyCode, keyEvent) : E(keyCode, keyEvent);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public boolean requestWindowFeature(int i2) {
        int iSanitizeWindowFeatureId = sanitizeWindowFeatureId(i2);
        if (this.f1992r && iSanitizeWindowFeatureId == 108) {
            return false;
        }
        if (this.f1988n && iSanitizeWindowFeatureId == 1) {
            this.f1988n = false;
        }
        if (iSanitizeWindowFeatureId == 1) {
            throwFeatureRequestIfSubDecorInstalled();
            this.f1992r = true;
            return true;
        }
        if (iSanitizeWindowFeatureId == 2) {
            throwFeatureRequestIfSubDecorInstalled();
            this.mFeatureProgress = true;
            return true;
        }
        if (iSanitizeWindowFeatureId == 5) {
            throwFeatureRequestIfSubDecorInstalled();
            this.mFeatureIndeterminateProgress = true;
            return true;
        }
        if (iSanitizeWindowFeatureId == 10) {
            throwFeatureRequestIfSubDecorInstalled();
            this.f1990p = true;
            return true;
        }
        if (iSanitizeWindowFeatureId == 108) {
            throwFeatureRequestIfSubDecorInstalled();
            this.f1988n = true;
            return true;
        }
        if (iSanitizeWindowFeatureId != 109) {
            return this.f1978d.requestFeature(iSanitizeWindowFeatureId);
        }
        throwFeatureRequestIfSubDecorInstalled();
        this.f1989o = true;
        return true;
    }

    void s(int i2) {
        PanelFeatureState panelFeatureStateX;
        PanelFeatureState panelFeatureStateX2 = x(i2, true);
        if (panelFeatureStateX2.f2023j != null) {
            Bundle bundle = new Bundle();
            panelFeatureStateX2.f2023j.saveActionViewStates(bundle);
            if (bundle.size() > 0) {
                panelFeatureStateX2.f2031r = bundle;
            }
            panelFeatureStateX2.f2023j.stopDispatchingItemsChanged();
            panelFeatureStateX2.f2023j.clear();
        }
        panelFeatureStateX2.f2030q = true;
        panelFeatureStateX2.f2029p = true;
        if ((i2 != 108 && i2 != 0) || this.mDecorContentParent == null || (panelFeatureStateX = x(0, false)) == null) {
            return;
        }
        panelFeatureStateX.f2026m = false;
        preparePanel(panelFeatureStateX, null);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void setContentView(View view) {
        ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup) this.f1987m.findViewById(R.id.content);
        viewGroup.removeAllViews();
        viewGroup.addView(view);
        this.mAppCompatWindowCallback.bypassOnContentChanged(this.f1978d.getCallback());
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void setHandleNativeActionModesEnabled(boolean z2) {
        this.mHandleNativeActionModes = z2;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    @RequiresApi(17)
    public void setLocalNightMode(int i2) {
        if (this.mLocalNightMode != i2) {
            this.mLocalNightMode = i2;
            if (this.mBaseContextAttached) {
                applyDayNight();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x002c  */
    @Override // androidx.appcompat.app.AppCompatDelegate
    @androidx.annotation.RequiresApi(33)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setOnBackInvokedDispatcher(@androidx.annotation.Nullable android.window.OnBackInvokedDispatcher r3) {
        /*
            r2 = this;
            super.setOnBackInvokedDispatcher(r3)
            android.window.OnBackInvokedDispatcher r0 = r2.mDispatcher
            if (r0 == 0) goto L11
            android.window.OnBackInvokedCallback r1 = r2.mBackCallback
            if (r1 == 0) goto L11
            androidx.appcompat.app.AppCompatDelegateImpl.Api33Impl.c(r0, r1)
            r0 = 0
            r2.mBackCallback = r0
        L11:
            if (r3 != 0) goto L2c
            java.lang.Object r0 = r2.f1976b
            boolean r1 = r0 instanceof android.app.Activity
            if (r1 == 0) goto L2c
            android.app.Activity r0 = (android.app.Activity) r0
            android.view.Window r0 = r0.getWindow()
            if (r0 == 0) goto L2c
            java.lang.Object r3 = r2.f1976b
            android.app.Activity r3 = (android.app.Activity) r3
            android.window.OnBackInvokedDispatcher r3 = androidx.appcompat.app.AppCompatDelegateImpl.Api33Impl.a(r3)
            r2.mDispatcher = r3
            goto L2e
        L2c:
            r2.mDispatcher = r3
        L2e:
            r2.O()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.setOnBackInvokedDispatcher(android.window.OnBackInvokedDispatcher):void");
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void setSupportActionBar(Toolbar toolbar) {
        if (this.f1976b instanceof Activity) {
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar instanceof WindowDecorActionBar) {
                throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
            }
            this.f1981g = null;
            if (supportActionBar != null) {
                supportActionBar.a();
            }
            this.f1980f = null;
            if (toolbar != null) {
                ToolbarActionBar toolbarActionBar = new ToolbarActionBar(toolbar, y(), this.mAppCompatWindowCallback);
                this.f1980f = toolbarActionBar;
                this.mAppCompatWindowCallback.a(toolbarActionBar.f2041c);
                toolbar.setBackInvokedCallbackEnabled(true);
            } else {
                this.mAppCompatWindowCallback.a(null);
            }
            invalidateOptionsMenu();
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void setTheme(@StyleRes int i2) {
        this.mThemeResId = i2;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent != null) {
            decorContentParent.setWindowTitle(charSequence);
            return;
        }
        if (I() != null) {
            I().setWindowTitle(charSequence);
            return;
        }
        TextView textView = this.mTitleView;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public androidx.appcompat.view.ActionMode startSupportActionMode(@NonNull ActionMode.Callback callback) {
        AppCompatCallback appCompatCallback;
        if (callback == null) {
            throw new IllegalArgumentException("ActionMode callback can not be null.");
        }
        androidx.appcompat.view.ActionMode actionMode = this.f1982h;
        if (actionMode != null) {
            actionMode.finish();
        }
        ActionModeCallbackWrapperV9 actionModeCallbackWrapperV9 = new ActionModeCallbackWrapperV9(callback);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            androidx.appcompat.view.ActionMode actionModeStartActionMode = supportActionBar.startActionMode(actionModeCallbackWrapperV9);
            this.f1982h = actionModeStartActionMode;
            if (actionModeStartActionMode != null && (appCompatCallback = this.f1979e) != null) {
                appCompatCallback.onSupportActionModeStarted(actionModeStartActionMode);
            }
        }
        if (this.f1982h == null) {
            this.f1982h = N(actionModeCallbackWrapperV9);
        }
        O();
        return this.f1982h;
    }

    void t() {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = this.f1986l;
        if (viewPropertyAnimatorCompat != null) {
            viewPropertyAnimatorCompat.cancel();
        }
    }

    PanelFeatureState u(Menu menu) {
        PanelFeatureState[] panelFeatureStateArr = this.mPanels;
        int length = panelFeatureStateArr != null ? panelFeatureStateArr.length : 0;
        for (int i2 = 0; i2 < length; i2++) {
            PanelFeatureState panelFeatureState = panelFeatureStateArr[i2];
            if (panelFeatureState != null && panelFeatureState.f2023j == menu) {
                return panelFeatureState;
            }
        }
        return null;
    }

    final Context v() {
        ActionBar supportActionBar = getSupportActionBar();
        Context themedContext = supportActionBar != null ? supportActionBar.getThemedContext() : null;
        return themedContext == null ? this.f1977c : themedContext;
    }

    LocaleListCompat w(Configuration configuration) {
        return Build.VERSION.SDK_INT >= 24 ? Api24Impl.b(configuration) : LocaleListCompat.forLanguageTags(Api21Impl.b(configuration.locale));
    }

    protected PanelFeatureState x(int i2, boolean z2) {
        PanelFeatureState[] panelFeatureStateArr = this.mPanels;
        if (panelFeatureStateArr == null || panelFeatureStateArr.length <= i2) {
            PanelFeatureState[] panelFeatureStateArr2 = new PanelFeatureState[i2 + 1];
            if (panelFeatureStateArr != null) {
                System.arraycopy(panelFeatureStateArr, 0, panelFeatureStateArr2, 0, panelFeatureStateArr.length);
            }
            this.mPanels = panelFeatureStateArr2;
            panelFeatureStateArr = panelFeatureStateArr2;
        }
        PanelFeatureState panelFeatureState = panelFeatureStateArr[i2];
        if (panelFeatureState != null) {
            return panelFeatureState;
        }
        PanelFeatureState panelFeatureState2 = new PanelFeatureState(i2);
        panelFeatureStateArr[i2] = panelFeatureState2;
        return panelFeatureState2;
    }

    final CharSequence y() {
        Object obj = this.f1976b;
        return obj instanceof Activity ? ((Activity) obj).getTitle() : this.mTitle;
    }

    final Window.Callback z() {
        return this.f1978d.getCallback();
    }

    AppCompatDelegateImpl(Dialog dialog, AppCompatCallback appCompatCallback) {
        this(dialog.getContext(), dialog.getWindow(), appCompatCallback, dialog);
    }

    private boolean applyApplicationSpecificConfig(boolean z2, boolean z3) throws IllegalAccessException, NoSuchFieldException, PackageManager.NameNotFoundException, SecurityException, IllegalArgumentException {
        if (this.f1993s) {
            return false;
        }
        int iCalculateNightMode = calculateNightMode();
        int iA = A(this.f1977c, iCalculateNightMode);
        LocaleListCompat localeListCompatL = Build.VERSION.SDK_INT < 33 ? l(this.f1977c) : null;
        if (!z3 && localeListCompatL != null) {
            localeListCompatL = w(this.f1977c.getResources().getConfiguration());
        }
        boolean zUpdateAppConfiguration = updateAppConfiguration(iA, localeListCompatL, z2);
        if (iCalculateNightMode == 0) {
            getAutoTimeNightModeManager(this.f1977c).c();
        } else {
            AutoNightModeManager autoNightModeManager = this.mAutoTimeNightModeManager;
            if (autoNightModeManager != null) {
                autoNightModeManager.a();
            }
        }
        if (iCalculateNightMode == 3) {
            getAutoBatteryNightModeManager(this.f1977c).c();
        } else {
            AutoNightModeManager autoNightModeManager2 = this.mAutoBatteryNightModeManager;
            if (autoNightModeManager2 != null) {
                autoNightModeManager2.a();
            }
        }
        return zUpdateAppConfiguration;
    }

    @Override // android.view.LayoutInflater.Factory
    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }

    AppCompatDelegateImpl(Context context, Window window, AppCompatCallback appCompatCallback) {
        this(context, window, appCompatCallback, context);
    }

    AppCompatDelegateImpl(Context context, Activity activity, AppCompatCallback appCompatCallback) {
        this(context, null, appCompatCallback, activity);
    }

    private AppCompatDelegateImpl(Context context, Window window, AppCompatCallback appCompatCallback, Object obj) {
        SimpleArrayMap<String, Integer> simpleArrayMap;
        Integer num;
        AppCompatActivity appCompatActivityTryUnwrapContext;
        this.f1986l = null;
        this.mHandleNativeActionModes = true;
        this.mLocalNightMode = -100;
        this.mInvalidatePanelMenuRunnable = new Runnable() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.2
            @Override // java.lang.Runnable
            public void run() {
                AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
                if ((appCompatDelegateImpl.f1995u & 1) != 0) {
                    appCompatDelegateImpl.s(0);
                }
                AppCompatDelegateImpl appCompatDelegateImpl2 = AppCompatDelegateImpl.this;
                if ((appCompatDelegateImpl2.f1995u & 4096) != 0) {
                    appCompatDelegateImpl2.s(108);
                }
                AppCompatDelegateImpl appCompatDelegateImpl3 = AppCompatDelegateImpl.this;
                appCompatDelegateImpl3.f1994t = false;
                appCompatDelegateImpl3.f1995u = 0;
            }
        };
        this.f1977c = context;
        this.f1979e = appCompatCallback;
        this.f1976b = obj;
        if (this.mLocalNightMode == -100 && (obj instanceof Dialog) && (appCompatActivityTryUnwrapContext = tryUnwrapContext()) != null) {
            this.mLocalNightMode = appCompatActivityTryUnwrapContext.getDelegate().getLocalNightMode();
        }
        if (this.mLocalNightMode == -100 && (num = (simpleArrayMap = sLocalNightModes).get(obj.getClass().getName())) != null) {
            this.mLocalNightMode = num.intValue();
            simpleArrayMap.remove(obj.getClass().getName());
        }
        if (window != null) {
            attachToWindow(window);
        }
        AppCompatDrawableManager.preload();
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void setContentView(int i2) {
        ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup) this.f1987m.findViewById(R.id.content);
        viewGroup.removeAllViews();
        LayoutInflater.from(this.f1977c).inflate(i2, viewGroup);
        this.mAppCompatWindowCallback.bypassOnContentChanged(this.f1978d.getCallback());
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup) this.f1987m.findViewById(R.id.content);
        viewGroup.removeAllViews();
        viewGroup.addView(view, layoutParams);
        this.mAppCompatWindowCallback.bypassOnContentChanged(this.f1978d.getCallback());
    }
}
