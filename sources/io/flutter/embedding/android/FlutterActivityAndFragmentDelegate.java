package io.flutter.embedding.android;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.window.BackEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.Lifecycle;
import anetwork.channel.util.RequestConstant;
import io.flutter.FlutterInjector;
import io.flutter.Log;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.FlutterEngineGroup;
import io.flutter.embedding.engine.FlutterEngineGroupCache;
import io.flutter.embedding.engine.FlutterShellArgs;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.embedding.engine.renderer.FlutterUiDisplayListener;
import io.flutter.plugin.platform.PlatformPlugin;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
class FlutterActivityAndFragmentDelegate implements ExclusiveAppComponent<Activity> {
    private static final int FLUTTER_SPLASH_VIEW_FALLBACK_ID = 486947586;
    private static final String FRAMEWORK_RESTORATION_BUNDLE_KEY = "framework";
    private static final String PLUGINS_RESTORATION_BUNDLE_KEY = "plugins";
    private static final String TAG = "FlutterActivityAndFragmentDelegate";

    @Nullable
    @VisibleForTesting
    ViewTreeObserver.OnPreDrawListener activePreDrawListener;

    @Nullable
    private FlutterEngineGroup engineGroup;

    @Nullable
    private FlutterEngine flutterEngine;

    @NonNull
    private final FlutterUiDisplayListener flutterUiDisplayListener;

    @Nullable
    @VisibleForTesting
    FlutterView flutterView;

    @NonNull
    private Host host;
    private boolean isAttached;
    private boolean isFirstFrameRendered;
    private boolean isFlutterEngineFromHost;
    private boolean isFlutterUiDisplayed;

    @Nullable
    private PlatformPlugin platformPlugin;
    private Integer previousVisibility;

    public interface DelegateFactory {
        FlutterActivityAndFragmentDelegate createDelegate(Host host);
    }

    interface Host extends FlutterEngineProvider, FlutterEngineConfigurator, PlatformPlugin.PlatformPluginDelegate {
        boolean attachToEngineAutomatically();

        void cleanUpFlutterEngine(@NonNull FlutterEngine flutterEngine);

        void configureFlutterEngine(@NonNull FlutterEngine flutterEngine);

        void detachFromFlutterEngine();

        @Nullable
        Activity getActivity();

        @NonNull
        String getAppBundlePath();

        @Nullable
        String getCachedEngineGroupId();

        @Nullable
        String getCachedEngineId();

        @NonNull
        Context getContext();

        @Nullable
        List<String> getDartEntrypointArgs();

        @NonNull
        String getDartEntrypointFunctionName();

        @Nullable
        String getDartEntrypointLibraryUri();

        ExclusiveAppComponent<Activity> getExclusiveAppComponent();

        @NonNull
        FlutterShellArgs getFlutterShellArgs();

        @Nullable
        String getInitialRoute();

        @NonNull
        Lifecycle getLifecycle();

        @NonNull
        RenderMode getRenderMode();

        @NonNull
        TransparencyMode getTransparencyMode();

        void onFlutterSurfaceViewCreated(@NonNull FlutterSurfaceView flutterSurfaceView);

        void onFlutterTextureViewCreated(@NonNull FlutterTextureView flutterTextureView);

        void onFlutterUiDisplayed();

        void onFlutterUiNoLongerDisplayed();

        @Override // io.flutter.embedding.android.FlutterEngineProvider
        @Nullable
        FlutterEngine provideFlutterEngine(@NonNull Context context);

        @Nullable
        PlatformPlugin providePlatformPlugin(@Nullable Activity activity, @NonNull FlutterEngine flutterEngine);

        boolean shouldAttachEngineToActivity();

        boolean shouldDestroyEngineWithHost();

        boolean shouldDispatchAppLifecycleState();

        @Nullable
        boolean shouldHandleDeeplinking();

        boolean shouldRestoreAndSaveState();

        void updateSystemUiOverlays();
    }

    FlutterActivityAndFragmentDelegate(@NonNull Host host) {
        this(host, null);
    }

    private FlutterEngineGroup.Options addEntrypointOptions(FlutterEngineGroup.Options options) {
        String appBundlePath = this.host.getAppBundlePath();
        if (appBundlePath == null || appBundlePath.isEmpty()) {
            appBundlePath = FlutterInjector.instance().flutterLoader().findAppBundlePath();
        }
        DartExecutor.DartEntrypoint dartEntrypoint = new DartExecutor.DartEntrypoint(appBundlePath, this.host.getDartEntrypointFunctionName());
        String initialRoute = this.host.getInitialRoute();
        if (initialRoute == null && (initialRoute = maybeGetInitialRouteFromIntent(this.host.getActivity().getIntent())) == null) {
            initialRoute = "/";
        }
        return options.setDartEntrypoint(dartEntrypoint).setInitialRoute(initialRoute).setDartEntrypointArgs(this.host.getDartEntrypointArgs());
    }

    private void delayFirstAndroidViewDraw(final FlutterView flutterView) {
        if (this.host.getRenderMode() != RenderMode.surface) {
            throw new IllegalArgumentException("Cannot delay the first Android view draw when the render mode is not set to `RenderMode.surface`.");
        }
        if (this.activePreDrawListener != null) {
            flutterView.getViewTreeObserver().removeOnPreDrawListener(this.activePreDrawListener);
        }
        this.activePreDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: io.flutter.embedding.android.FlutterActivityAndFragmentDelegate.2
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                if (FlutterActivityAndFragmentDelegate.this.isFlutterUiDisplayed && FlutterActivityAndFragmentDelegate.this.activePreDrawListener != null) {
                    flutterView.getViewTreeObserver().removeOnPreDrawListener(this);
                    FlutterActivityAndFragmentDelegate.this.activePreDrawListener = null;
                }
                return FlutterActivityAndFragmentDelegate.this.isFlutterUiDisplayed;
            }
        };
        flutterView.getViewTreeObserver().addOnPreDrawListener(this.activePreDrawListener);
    }

    private void doInitialFlutterViewRun() {
        String str;
        if (this.host.getCachedEngineId() == null && !this.flutterEngine.getDartExecutor().isExecutingDart()) {
            String initialRoute = this.host.getInitialRoute();
            if (initialRoute == null && (initialRoute = maybeGetInitialRouteFromIntent(this.host.getActivity().getIntent())) == null) {
                initialRoute = "/";
            }
            String dartEntrypointLibraryUri = this.host.getDartEntrypointLibraryUri();
            if (("Executing Dart entrypoint: " + this.host.getDartEntrypointFunctionName() + ", library uri: " + dartEntrypointLibraryUri) == null) {
                str = "\"\"";
            } else {
                str = dartEntrypointLibraryUri + ", and sending initial route: " + initialRoute;
            }
            Log.v(TAG, str);
            this.flutterEngine.getNavigationChannel().setInitialRoute(initialRoute);
            String appBundlePath = this.host.getAppBundlePath();
            if (appBundlePath == null || appBundlePath.isEmpty()) {
                appBundlePath = FlutterInjector.instance().flutterLoader().findAppBundlePath();
            }
            this.flutterEngine.getDartExecutor().executeDartEntrypoint(dartEntrypointLibraryUri == null ? new DartExecutor.DartEntrypoint(appBundlePath, this.host.getDartEntrypointFunctionName()) : new DartExecutor.DartEntrypoint(appBundlePath, dartEntrypointLibraryUri, this.host.getDartEntrypointFunctionName()), this.host.getDartEntrypointArgs());
        }
    }

    private void ensureAlive() {
        if (this.host == null) {
            throw new IllegalStateException("Cannot execute method on a destroyed FlutterActivityAndFragmentDelegate.");
        }
    }

    private String maybeGetInitialRouteFromIntent(Intent intent) {
        Uri data;
        if (!this.host.shouldHandleDeeplinking() || (data = intent.getData()) == null) {
            return null;
        }
        return data.toString();
    }

    @RequiresApi(34)
    @TargetApi(34)
    void cancelBackGesture() {
        ensureAlive();
        if (this.flutterEngine == null) {
            Log.w(TAG, "Invoked cancelBackGesture() before FlutterFragment was attached to an Activity.");
        } else {
            Log.v(TAG, "Forwarding cancelBackGesture() to FlutterEngine.");
            this.flutterEngine.getBackGestureChannel().cancelBackGesture();
        }
    }

    @RequiresApi(34)
    @TargetApi(34)
    void commitBackGesture() {
        ensureAlive();
        if (this.flutterEngine == null) {
            Log.w(TAG, "Invoked commitBackGesture() before FlutterFragment was attached to an Activity.");
        } else {
            Log.v(TAG, "Forwarding commitBackGesture() to FlutterEngine.");
            this.flutterEngine.getBackGestureChannel().commitBackGesture();
        }
    }

    @Override // io.flutter.embedding.android.ExclusiveAppComponent
    public void detachFromFlutterEngine() {
        if (!this.host.shouldDestroyEngineWithHost()) {
            this.host.detachFromFlutterEngine();
            return;
        }
        throw new AssertionError("The internal FlutterEngine created by " + this.host + " has been attached to by another activity. To persist a FlutterEngine beyond the ownership of this activity, explicitly create a FlutterEngine");
    }

    @Nullable
    FlutterEngine getFlutterEngine() {
        return this.flutterEngine;
    }

    boolean isAttached() {
        return this.isAttached;
    }

    boolean isFlutterEngineFromHost() {
        return this.isFlutterEngineFromHost;
    }

    void onActivityResult(int i2, int i3, Intent intent) {
        ensureAlive();
        if (this.flutterEngine == null) {
            Log.w(TAG, "onActivityResult() invoked before FlutterFragment was attached to an Activity.");
            return;
        }
        Log.v(TAG, "Forwarding onActivityResult() to FlutterEngine:\nrequestCode: " + i2 + "\nresultCode: " + i3 + "\ndata: " + intent);
        this.flutterEngine.getActivityControlSurface().onActivityResult(i2, i3, intent);
    }

    void onAttach(@NonNull Context context) {
        ensureAlive();
        if (this.flutterEngine == null) {
            setUpFlutterEngine();
        }
        if (this.host.shouldAttachEngineToActivity()) {
            Log.v(TAG, "Attaching FlutterEngine to the Activity that owns this delegate.");
            this.flutterEngine.getActivityControlSurface().attachToActivity(this, this.host.getLifecycle());
        }
        Host host = this.host;
        this.platformPlugin = host.providePlatformPlugin(host.getActivity(), this.flutterEngine);
        this.host.configureFlutterEngine(this.flutterEngine);
        this.isAttached = true;
    }

    void onBackPressed() {
        ensureAlive();
        if (this.flutterEngine == null) {
            Log.w(TAG, "Invoked onBackPressed() before FlutterFragment was attached to an Activity.");
        } else {
            Log.v(TAG, "Forwarding onBackPressed() to FlutterEngine.");
            this.flutterEngine.getNavigationChannel().popRoute();
        }
    }

    @NonNull
    View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle, int i2, boolean z2) {
        Log.v(TAG, "Creating FlutterView.");
        ensureAlive();
        if (this.host.getRenderMode() == RenderMode.surface) {
            FlutterSurfaceView flutterSurfaceView = new FlutterSurfaceView(this.host.getContext(), this.host.getTransparencyMode() == TransparencyMode.transparent);
            this.host.onFlutterSurfaceViewCreated(flutterSurfaceView);
            this.flutterView = new FlutterView(this.host.getContext(), flutterSurfaceView);
        } else {
            FlutterTextureView flutterTextureView = new FlutterTextureView(this.host.getContext());
            flutterTextureView.setOpaque(this.host.getTransparencyMode() == TransparencyMode.opaque);
            this.host.onFlutterTextureViewCreated(flutterTextureView);
            this.flutterView = new FlutterView(this.host.getContext(), flutterTextureView);
        }
        this.flutterView.addOnFirstFrameRenderedListener(this.flutterUiDisplayListener);
        if (this.host.attachToEngineAutomatically()) {
            Log.v(TAG, "Attaching FlutterEngine to FlutterView.");
            this.flutterView.attachToFlutterEngine(this.flutterEngine);
        }
        this.flutterView.setId(i2);
        if (z2) {
            delayFirstAndroidViewDraw(this.flutterView);
        }
        return this.flutterView;
    }

    void onDestroyView() {
        Log.v(TAG, "onDestroyView()");
        ensureAlive();
        if (this.activePreDrawListener != null) {
            this.flutterView.getViewTreeObserver().removeOnPreDrawListener(this.activePreDrawListener);
            this.activePreDrawListener = null;
        }
        FlutterView flutterView = this.flutterView;
        if (flutterView != null) {
            flutterView.detachFromFlutterEngine();
            this.flutterView.removeOnFirstFrameRenderedListener(this.flutterUiDisplayListener);
        }
    }

    void onDetach() {
        FlutterEngine flutterEngine;
        if (this.isAttached) {
            Log.v(TAG, "onDetach()");
            ensureAlive();
            this.host.cleanUpFlutterEngine(this.flutterEngine);
            if (this.host.shouldAttachEngineToActivity()) {
                Log.v(TAG, "Detaching FlutterEngine from the Activity that owns this Fragment.");
                if (this.host.getActivity().isChangingConfigurations()) {
                    this.flutterEngine.getActivityControlSurface().detachFromActivityForConfigChanges();
                } else {
                    this.flutterEngine.getActivityControlSurface().detachFromActivity();
                }
            }
            PlatformPlugin platformPlugin = this.platformPlugin;
            if (platformPlugin != null) {
                platformPlugin.destroy();
                this.platformPlugin = null;
            }
            if (this.host.shouldDispatchAppLifecycleState() && (flutterEngine = this.flutterEngine) != null) {
                flutterEngine.getLifecycleChannel().appIsDetached();
            }
            if (this.host.shouldDestroyEngineWithHost()) {
                this.flutterEngine.destroy();
                if (this.host.getCachedEngineId() != null) {
                    FlutterEngineCache.getInstance().remove(this.host.getCachedEngineId());
                }
                this.flutterEngine = null;
            }
            this.isAttached = false;
        }
    }

    void onNewIntent(@NonNull Intent intent) {
        ensureAlive();
        if (this.flutterEngine == null) {
            Log.w(TAG, "onNewIntent() invoked before FlutterFragment was attached to an Activity.");
            return;
        }
        Log.v(TAG, "Forwarding onNewIntent() to FlutterEngine and sending pushRouteInformation message.");
        this.flutterEngine.getActivityControlSurface().onNewIntent(intent);
        String strMaybeGetInitialRouteFromIntent = maybeGetInitialRouteFromIntent(intent);
        if (strMaybeGetInitialRouteFromIntent == null || strMaybeGetInitialRouteFromIntent.isEmpty()) {
            return;
        }
        this.flutterEngine.getNavigationChannel().pushRouteInformation(strMaybeGetInitialRouteFromIntent);
    }

    void onPause() {
        FlutterEngine flutterEngine;
        Log.v(TAG, "onPause()");
        ensureAlive();
        if (!this.host.shouldDispatchAppLifecycleState() || (flutterEngine = this.flutterEngine) == null) {
            return;
        }
        flutterEngine.getLifecycleChannel().appIsInactive();
    }

    void onPostResume() {
        Log.v(TAG, "onPostResume()");
        ensureAlive();
        if (this.flutterEngine == null) {
            Log.w(TAG, "onPostResume() invoked before FlutterFragment was attached to an Activity.");
        } else {
            updateSystemUiOverlays();
            this.flutterEngine.getPlatformViewsController().onResume();
        }
    }

    void onRequestPermissionsResult(int i2, @NonNull String[] strArr, @NonNull int[] iArr) {
        ensureAlive();
        if (this.flutterEngine == null) {
            Log.w(TAG, "onRequestPermissionResult() invoked before FlutterFragment was attached to an Activity.");
            return;
        }
        Log.v(TAG, "Forwarding onRequestPermissionsResult() to FlutterEngine:\nrequestCode: " + i2 + "\npermissions: " + Arrays.toString(strArr) + "\ngrantResults: " + Arrays.toString(iArr));
        this.flutterEngine.getActivityControlSurface().onRequestPermissionsResult(i2, strArr, iArr);
    }

    void onRestoreInstanceState(@Nullable Bundle bundle) {
        Bundle bundle2;
        byte[] byteArray;
        Log.v(TAG, "onRestoreInstanceState. Giving framework and plugins an opportunity to restore state.");
        ensureAlive();
        if (bundle != null) {
            bundle2 = bundle.getBundle(PLUGINS_RESTORATION_BUNDLE_KEY);
            byteArray = bundle.getByteArray(FRAMEWORK_RESTORATION_BUNDLE_KEY);
        } else {
            bundle2 = null;
            byteArray = null;
        }
        if (this.host.shouldRestoreAndSaveState()) {
            this.flutterEngine.getRestorationChannel().setRestorationData(byteArray);
        }
        if (this.host.shouldAttachEngineToActivity()) {
            this.flutterEngine.getActivityControlSurface().onRestoreInstanceState(bundle2);
        }
    }

    void onResume() {
        FlutterEngine flutterEngine;
        Log.v(TAG, "onResume()");
        ensureAlive();
        if (!this.host.shouldDispatchAppLifecycleState() || (flutterEngine = this.flutterEngine) == null) {
            return;
        }
        flutterEngine.getLifecycleChannel().appIsResumed();
    }

    void onSaveInstanceState(@Nullable Bundle bundle) {
        Log.v(TAG, "onSaveInstanceState. Giving framework and plugins an opportunity to save state.");
        ensureAlive();
        if (this.host.shouldRestoreAndSaveState()) {
            bundle.putByteArray(FRAMEWORK_RESTORATION_BUNDLE_KEY, this.flutterEngine.getRestorationChannel().getRestorationData());
        }
        if (this.host.shouldAttachEngineToActivity()) {
            Bundle bundle2 = new Bundle();
            this.flutterEngine.getActivityControlSurface().onSaveInstanceState(bundle2);
            bundle.putBundle(PLUGINS_RESTORATION_BUNDLE_KEY, bundle2);
        }
    }

    void onStart() {
        Log.v(TAG, "onStart()");
        ensureAlive();
        doInitialFlutterViewRun();
        Integer num = this.previousVisibility;
        if (num != null) {
            this.flutterView.setVisibility(num.intValue());
        }
    }

    void onStop() {
        FlutterEngine flutterEngine;
        Log.v(TAG, "onStop()");
        ensureAlive();
        if (this.host.shouldDispatchAppLifecycleState() && (flutterEngine = this.flutterEngine) != null) {
            flutterEngine.getLifecycleChannel().appIsPaused();
        }
        this.previousVisibility = Integer.valueOf(this.flutterView.getVisibility());
        this.flutterView.setVisibility(8);
        FlutterEngine flutterEngine2 = this.flutterEngine;
        if (flutterEngine2 != null) {
            flutterEngine2.getRenderer().onTrimMemory(40);
        }
    }

    void onTrimMemory(int i2) {
        ensureAlive();
        FlutterEngine flutterEngine = this.flutterEngine;
        if (flutterEngine != null) {
            if (this.isFirstFrameRendered && i2 >= 10) {
                flutterEngine.getDartExecutor().notifyLowMemoryWarning();
                this.flutterEngine.getSystemChannel().sendMemoryPressureWarning();
            }
            this.flutterEngine.getRenderer().onTrimMemory(i2);
            this.flutterEngine.getPlatformViewsController().onTrimMemory(i2);
        }
    }

    void onUserLeaveHint() {
        ensureAlive();
        if (this.flutterEngine == null) {
            Log.w(TAG, "onUserLeaveHint() invoked before FlutterFragment was attached to an Activity.");
        } else {
            Log.v(TAG, "Forwarding onUserLeaveHint() to FlutterEngine.");
            this.flutterEngine.getActivityControlSurface().onUserLeaveHint();
        }
    }

    void onWindowFocusChanged(boolean z2) {
        FlutterEngine flutterEngine;
        ensureAlive();
        StringBuilder sb = new StringBuilder();
        sb.append("Received onWindowFocusChanged: ");
        sb.append(z2 ? "true" : RequestConstant.FALSE);
        Log.v(TAG, sb.toString());
        if (!this.host.shouldDispatchAppLifecycleState() || (flutterEngine = this.flutterEngine) == null) {
            return;
        }
        if (z2) {
            flutterEngine.getLifecycleChannel().aWindowIsFocused();
        } else {
            flutterEngine.getLifecycleChannel().noWindowsAreFocused();
        }
    }

    void release() {
        this.host = null;
        this.flutterEngine = null;
        this.flutterView = null;
        this.platformPlugin = null;
    }

    @VisibleForTesting
    void setUpFlutterEngine() {
        Log.v(TAG, "Setting up FlutterEngine.");
        String cachedEngineId = this.host.getCachedEngineId();
        if (cachedEngineId != null) {
            FlutterEngine flutterEngine = FlutterEngineCache.getInstance().get(cachedEngineId);
            this.flutterEngine = flutterEngine;
            this.isFlutterEngineFromHost = true;
            if (flutterEngine != null) {
                return;
            }
            throw new IllegalStateException("The requested cached FlutterEngine did not exist in the FlutterEngineCache: '" + cachedEngineId + "'");
        }
        Host host = this.host;
        FlutterEngine flutterEngineProvideFlutterEngine = host.provideFlutterEngine(host.getContext());
        this.flutterEngine = flutterEngineProvideFlutterEngine;
        if (flutterEngineProvideFlutterEngine != null) {
            this.isFlutterEngineFromHost = true;
            return;
        }
        String cachedEngineGroupId = this.host.getCachedEngineGroupId();
        if (cachedEngineGroupId == null) {
            Log.v(TAG, "No preferred FlutterEngine was provided. Creating a new FlutterEngine for this FlutterFragment.");
            FlutterEngineGroup flutterEngineGroup = this.engineGroup;
            if (flutterEngineGroup == null) {
                flutterEngineGroup = new FlutterEngineGroup(this.host.getContext(), this.host.getFlutterShellArgs().toArray());
            }
            this.flutterEngine = flutterEngineGroup.createAndRunEngine(addEntrypointOptions(new FlutterEngineGroup.Options(this.host.getContext()).setAutomaticallyRegisterPlugins(false).setWaitForRestorationData(this.host.shouldRestoreAndSaveState())));
            this.isFlutterEngineFromHost = false;
            return;
        }
        FlutterEngineGroup flutterEngineGroup2 = FlutterEngineGroupCache.getInstance().get(cachedEngineGroupId);
        if (flutterEngineGroup2 != null) {
            this.flutterEngine = flutterEngineGroup2.createAndRunEngine(addEntrypointOptions(new FlutterEngineGroup.Options(this.host.getContext())));
            this.isFlutterEngineFromHost = false;
        } else {
            throw new IllegalStateException("The requested cached FlutterEngineGroup did not exist in the FlutterEngineGroupCache: '" + cachedEngineGroupId + "'");
        }
    }

    @RequiresApi(34)
    @TargetApi(34)
    void startBackGesture(@NonNull BackEvent backEvent) {
        ensureAlive();
        if (this.flutterEngine == null) {
            Log.w(TAG, "Invoked startBackGesture() before FlutterFragment was attached to an Activity.");
        } else {
            Log.v(TAG, "Forwarding startBackGesture() to FlutterEngine.");
            this.flutterEngine.getBackGestureChannel().startBackGesture(backEvent);
        }
    }

    @RequiresApi(34)
    @TargetApi(34)
    void updateBackGestureProgress(@NonNull BackEvent backEvent) {
        ensureAlive();
        if (this.flutterEngine == null) {
            Log.w(TAG, "Invoked updateBackGestureProgress() before FlutterFragment was attached to an Activity.");
        } else {
            Log.v(TAG, "Forwarding updateBackGestureProgress() to FlutterEngine.");
            this.flutterEngine.getBackGestureChannel().updateBackGestureProgress(backEvent);
        }
    }

    void updateSystemUiOverlays() {
        PlatformPlugin platformPlugin = this.platformPlugin;
        if (platformPlugin != null) {
            platformPlugin.updateSystemUiOverlays();
        }
    }

    FlutterActivityAndFragmentDelegate(@NonNull Host host, @Nullable FlutterEngineGroup flutterEngineGroup) {
        this.flutterUiDisplayListener = new FlutterUiDisplayListener() { // from class: io.flutter.embedding.android.FlutterActivityAndFragmentDelegate.1
            @Override // io.flutter.embedding.engine.renderer.FlutterUiDisplayListener
            public void onFlutterUiDisplayed() {
                FlutterActivityAndFragmentDelegate.this.host.onFlutterUiDisplayed();
                FlutterActivityAndFragmentDelegate.this.isFlutterUiDisplayed = true;
                FlutterActivityAndFragmentDelegate.this.isFirstFrameRendered = true;
            }

            @Override // io.flutter.embedding.engine.renderer.FlutterUiDisplayListener
            public void onFlutterUiNoLongerDisplayed() {
                FlutterActivityAndFragmentDelegate.this.host.onFlutterUiNoLongerDisplayed();
                FlutterActivityAndFragmentDelegate.this.isFlutterUiDisplayed = false;
            }
        };
        this.host = host;
        this.isFirstFrameRendered = false;
        this.engineGroup = flutterEngineGroup;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.flutter.embedding.android.ExclusiveAppComponent
    @NonNull
    public Activity getAppComponent() {
        Activity activity = this.host.getActivity();
        if (activity != null) {
            return activity;
        }
        throw new AssertionError("FlutterActivityAndFragmentDelegate's getAppComponent should only be queried after onAttach, when the host's activity should always be non-null");
    }
}
