package io.flutter.embedding.engine;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import io.flutter.Log;
import io.flutter.embedding.android.ExclusiveAppComponent;
import io.flutter.embedding.engine.loader.FlutterLoader;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.PluginRegistry;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityControlSurface;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.embedding.engine.plugins.broadcastreceiver.BroadcastReceiverAware;
import io.flutter.embedding.engine.plugins.broadcastreceiver.BroadcastReceiverControlSurface;
import io.flutter.embedding.engine.plugins.broadcastreceiver.BroadcastReceiverPluginBinding;
import io.flutter.embedding.engine.plugins.contentprovider.ContentProviderAware;
import io.flutter.embedding.engine.plugins.contentprovider.ContentProviderControlSurface;
import io.flutter.embedding.engine.plugins.contentprovider.ContentProviderPluginBinding;
import io.flutter.embedding.engine.plugins.lifecycle.HiddenLifecycleReference;
import io.flutter.embedding.engine.plugins.service.ServiceAware;
import io.flutter.embedding.engine.plugins.service.ServiceControlSurface;
import io.flutter.embedding.engine.plugins.service.ServicePluginBinding;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.util.TraceSection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
class FlutterEngineConnectionRegistry implements PluginRegistry, ActivityControlSurface, ServiceControlSurface, BroadcastReceiverControlSurface, ContentProviderControlSurface {
    private static final String TAG = "FlutterEngineCxnRegstry";

    @Nullable
    private FlutterEngineActivityPluginBinding activityPluginBinding;

    @Nullable
    private BroadcastReceiver broadcastReceiver;

    @Nullable
    private FlutterEngineBroadcastReceiverPluginBinding broadcastReceiverPluginBinding;

    @Nullable
    private ContentProvider contentProvider;

    @Nullable
    private FlutterEngineContentProviderPluginBinding contentProviderPluginBinding;

    @Nullable
    private ExclusiveAppComponent<Activity> exclusiveActivity;

    @NonNull
    private final FlutterEngine flutterEngine;

    @NonNull
    private final FlutterPlugin.FlutterPluginBinding pluginBinding;

    @Nullable
    private Service service;

    @Nullable
    private FlutterEngineServicePluginBinding servicePluginBinding;

    @NonNull
    private final Map<Class<? extends FlutterPlugin>, FlutterPlugin> plugins = new HashMap();

    @NonNull
    private final Map<Class<? extends FlutterPlugin>, ActivityAware> activityAwarePlugins = new HashMap();
    private boolean isWaitingForActivityReattachment = false;

    @NonNull
    private final Map<Class<? extends FlutterPlugin>, ServiceAware> serviceAwarePlugins = new HashMap();

    @NonNull
    private final Map<Class<? extends FlutterPlugin>, BroadcastReceiverAware> broadcastReceiverAwarePlugins = new HashMap();

    @NonNull
    private final Map<Class<? extends FlutterPlugin>, ContentProviderAware> contentProviderAwarePlugins = new HashMap();

    private static class DefaultFlutterAssets implements FlutterPlugin.FlutterAssets {
        final FlutterLoader flutterLoader;

        @Override // io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterAssets
        public String getAssetFilePathByName(@NonNull String str) {
            return this.flutterLoader.getLookupKeyForAsset(str);
        }

        @Override // io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterAssets
        public String getAssetFilePathBySubpath(@NonNull String str) {
            return this.flutterLoader.getLookupKeyForAsset(str);
        }

        private DefaultFlutterAssets(@NonNull FlutterLoader flutterLoader) {
            this.flutterLoader = flutterLoader;
        }

        @Override // io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterAssets
        public String getAssetFilePathByName(@NonNull String str, @NonNull String str2) {
            return this.flutterLoader.getLookupKeyForAsset(str, str2);
        }

        @Override // io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterAssets
        public String getAssetFilePathBySubpath(@NonNull String str, @NonNull String str2) {
            return this.flutterLoader.getLookupKeyForAsset(str, str2);
        }
    }

    private static class FlutterEngineActivityPluginBinding implements ActivityPluginBinding {

        @NonNull
        private final Activity activity;

        @NonNull
        private final HiddenLifecycleReference hiddenLifecycleReference;

        @NonNull
        private final Set<PluginRegistry.RequestPermissionsResultListener> onRequestPermissionsResultListeners = new HashSet();

        @NonNull
        private final Set<PluginRegistry.ActivityResultListener> onActivityResultListeners = new HashSet();

        @NonNull
        private final Set<PluginRegistry.NewIntentListener> onNewIntentListeners = new HashSet();

        @NonNull
        private final Set<PluginRegistry.UserLeaveHintListener> onUserLeaveHintListeners = new HashSet();

        @NonNull
        private final Set<PluginRegistry.WindowFocusChangedListener> onWindowFocusChangedListeners = new HashSet();

        @NonNull
        private final Set<ActivityPluginBinding.OnSaveInstanceStateListener> onSaveInstanceStateListeners = new HashSet();

        public FlutterEngineActivityPluginBinding(@NonNull Activity activity, @NonNull Lifecycle lifecycle) {
            this.activity = activity;
            this.hiddenLifecycleReference = new HiddenLifecycleReference(lifecycle);
        }

        @Override // io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
        public void addActivityResultListener(@NonNull PluginRegistry.ActivityResultListener activityResultListener) {
            this.onActivityResultListeners.add(activityResultListener);
        }

        @Override // io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
        public void addOnNewIntentListener(@NonNull PluginRegistry.NewIntentListener newIntentListener) {
            this.onNewIntentListeners.add(newIntentListener);
        }

        @Override // io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
        public void addOnSaveStateListener(@NonNull ActivityPluginBinding.OnSaveInstanceStateListener onSaveInstanceStateListener) {
            this.onSaveInstanceStateListeners.add(onSaveInstanceStateListener);
        }

        @Override // io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
        public void addOnUserLeaveHintListener(@NonNull PluginRegistry.UserLeaveHintListener userLeaveHintListener) {
            this.onUserLeaveHintListeners.add(userLeaveHintListener);
        }

        @Override // io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
        public void addOnWindowFocusChangedListener(@NonNull PluginRegistry.WindowFocusChangedListener windowFocusChangedListener) {
            this.onWindowFocusChangedListeners.add(windowFocusChangedListener);
        }

        @Override // io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
        public void addRequestPermissionsResultListener(@NonNull PluginRegistry.RequestPermissionsResultListener requestPermissionsResultListener) {
            this.onRequestPermissionsResultListeners.add(requestPermissionsResultListener);
        }

        @Override // io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
        @NonNull
        public Activity getActivity() {
            return this.activity;
        }

        @Override // io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
        @NonNull
        public Object getLifecycle() {
            return this.hiddenLifecycleReference;
        }

        boolean onActivityResult(int i2, int i3, @Nullable Intent intent) {
            Iterator it = new HashSet(this.onActivityResultListeners).iterator();
            while (true) {
                boolean z2 = false;
                while (it.hasNext()) {
                    if (((PluginRegistry.ActivityResultListener) it.next()).onActivityResult(i2, i3, intent) || z2) {
                        z2 = true;
                    }
                }
                return z2;
            }
        }

        void onNewIntent(@Nullable Intent intent) {
            Iterator<PluginRegistry.NewIntentListener> it = this.onNewIntentListeners.iterator();
            while (it.hasNext()) {
                it.next().onNewIntent(intent);
            }
        }

        boolean onRequestPermissionsResult(int i2, @NonNull String[] strArr, @NonNull int[] iArr) {
            Iterator<PluginRegistry.RequestPermissionsResultListener> it = this.onRequestPermissionsResultListeners.iterator();
            while (true) {
                boolean z2 = false;
                while (it.hasNext()) {
                    if (it.next().onRequestPermissionsResult(i2, strArr, iArr) || z2) {
                        z2 = true;
                    }
                }
                return z2;
            }
        }

        void onRestoreInstanceState(@Nullable Bundle bundle) {
            Iterator<ActivityPluginBinding.OnSaveInstanceStateListener> it = this.onSaveInstanceStateListeners.iterator();
            while (it.hasNext()) {
                it.next().onRestoreInstanceState(bundle);
            }
        }

        void onSaveInstanceState(@NonNull Bundle bundle) {
            Iterator<ActivityPluginBinding.OnSaveInstanceStateListener> it = this.onSaveInstanceStateListeners.iterator();
            while (it.hasNext()) {
                it.next().onSaveInstanceState(bundle);
            }
        }

        void onUserLeaveHint() {
            Iterator<PluginRegistry.UserLeaveHintListener> it = this.onUserLeaveHintListeners.iterator();
            while (it.hasNext()) {
                it.next().onUserLeaveHint();
            }
        }

        void onWindowFocusChanged(boolean z2) {
            Iterator<PluginRegistry.WindowFocusChangedListener> it = this.onWindowFocusChangedListeners.iterator();
            while (it.hasNext()) {
                it.next().onWindowFocusChanged(z2);
            }
        }

        @Override // io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
        public void removeActivityResultListener(@NonNull PluginRegistry.ActivityResultListener activityResultListener) {
            this.onActivityResultListeners.remove(activityResultListener);
        }

        @Override // io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
        public void removeOnNewIntentListener(@NonNull PluginRegistry.NewIntentListener newIntentListener) {
            this.onNewIntentListeners.remove(newIntentListener);
        }

        @Override // io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
        public void removeOnSaveStateListener(@NonNull ActivityPluginBinding.OnSaveInstanceStateListener onSaveInstanceStateListener) {
            this.onSaveInstanceStateListeners.remove(onSaveInstanceStateListener);
        }

        @Override // io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
        public void removeOnUserLeaveHintListener(@NonNull PluginRegistry.UserLeaveHintListener userLeaveHintListener) {
            this.onUserLeaveHintListeners.remove(userLeaveHintListener);
        }

        @Override // io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
        public void removeOnWindowFocusChangedListener(@NonNull PluginRegistry.WindowFocusChangedListener windowFocusChangedListener) {
            this.onWindowFocusChangedListeners.remove(windowFocusChangedListener);
        }

        @Override // io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
        public void removeRequestPermissionsResultListener(@NonNull PluginRegistry.RequestPermissionsResultListener requestPermissionsResultListener) {
            this.onRequestPermissionsResultListeners.remove(requestPermissionsResultListener);
        }
    }

    private static class FlutterEngineBroadcastReceiverPluginBinding implements BroadcastReceiverPluginBinding {

        @NonNull
        private final BroadcastReceiver broadcastReceiver;

        FlutterEngineBroadcastReceiverPluginBinding(@NonNull BroadcastReceiver broadcastReceiver) {
            this.broadcastReceiver = broadcastReceiver;
        }

        @Override // io.flutter.embedding.engine.plugins.broadcastreceiver.BroadcastReceiverPluginBinding
        @NonNull
        public BroadcastReceiver getBroadcastReceiver() {
            return this.broadcastReceiver;
        }
    }

    private static class FlutterEngineContentProviderPluginBinding implements ContentProviderPluginBinding {

        @NonNull
        private final ContentProvider contentProvider;

        FlutterEngineContentProviderPluginBinding(@NonNull ContentProvider contentProvider) {
            this.contentProvider = contentProvider;
        }

        @Override // io.flutter.embedding.engine.plugins.contentprovider.ContentProviderPluginBinding
        @NonNull
        public ContentProvider getContentProvider() {
            return this.contentProvider;
        }
    }

    private static class FlutterEngineServicePluginBinding implements ServicePluginBinding {

        @Nullable
        private final HiddenLifecycleReference hiddenLifecycleReference;

        @NonNull
        private final Set<ServiceAware.OnModeChangeListener> onModeChangeListeners = new HashSet();

        @NonNull
        private final Service service;

        FlutterEngineServicePluginBinding(@NonNull Service service, @Nullable Lifecycle lifecycle) {
            this.service = service;
            this.hiddenLifecycleReference = lifecycle != null ? new HiddenLifecycleReference(lifecycle) : null;
        }

        @Override // io.flutter.embedding.engine.plugins.service.ServicePluginBinding
        public void addOnModeChangeListener(@NonNull ServiceAware.OnModeChangeListener onModeChangeListener) {
            this.onModeChangeListeners.add(onModeChangeListener);
        }

        @Override // io.flutter.embedding.engine.plugins.service.ServicePluginBinding
        @Nullable
        public Object getLifecycle() {
            return this.hiddenLifecycleReference;
        }

        @Override // io.flutter.embedding.engine.plugins.service.ServicePluginBinding
        @NonNull
        public Service getService() {
            return this.service;
        }

        void onMoveToBackground() {
            Iterator<ServiceAware.OnModeChangeListener> it = this.onModeChangeListeners.iterator();
            while (it.hasNext()) {
                it.next().onMoveToBackground();
            }
        }

        void onMoveToForeground() {
            Iterator<ServiceAware.OnModeChangeListener> it = this.onModeChangeListeners.iterator();
            while (it.hasNext()) {
                it.next().onMoveToForeground();
            }
        }

        @Override // io.flutter.embedding.engine.plugins.service.ServicePluginBinding
        public void removeOnModeChangeListener(@NonNull ServiceAware.OnModeChangeListener onModeChangeListener) {
            this.onModeChangeListeners.remove(onModeChangeListener);
        }
    }

    FlutterEngineConnectionRegistry(@NonNull Context context, @NonNull FlutterEngine flutterEngine, @NonNull FlutterLoader flutterLoader, @Nullable FlutterEngineGroup flutterEngineGroup) {
        this.flutterEngine = flutterEngine;
        this.pluginBinding = new FlutterPlugin.FlutterPluginBinding(context, flutterEngine, flutterEngine.getDartExecutor(), flutterEngine.getRenderer(), flutterEngine.getPlatformViewsController().getRegistry(), new DefaultFlutterAssets(flutterLoader), flutterEngineGroup);
    }

    private void attachToActivityInternal(@NonNull Activity activity, @NonNull Lifecycle lifecycle) {
        this.activityPluginBinding = new FlutterEngineActivityPluginBinding(activity, lifecycle);
        this.flutterEngine.getPlatformViewsController().setSoftwareRendering(activity.getIntent() != null ? activity.getIntent().getBooleanExtra(FlutterShellArgs.ARG_KEY_ENABLE_SOFTWARE_RENDERING, false) : false);
        this.flutterEngine.getPlatformViewsController().attach(activity, this.flutterEngine.getRenderer(), this.flutterEngine.getDartExecutor());
        for (ActivityAware activityAware : this.activityAwarePlugins.values()) {
            if (this.isWaitingForActivityReattachment) {
                activityAware.onReattachedToActivityForConfigChanges(this.activityPluginBinding);
            } else {
                activityAware.onAttachedToActivity(this.activityPluginBinding);
            }
        }
        this.isWaitingForActivityReattachment = false;
    }

    private Activity attachedActivity() {
        ExclusiveAppComponent<Activity> exclusiveAppComponent = this.exclusiveActivity;
        if (exclusiveAppComponent != null) {
            return exclusiveAppComponent.getAppComponent();
        }
        return null;
    }

    private void detachFromActivityInternal() {
        this.flutterEngine.getPlatformViewsController().detach();
        this.exclusiveActivity = null;
        this.activityPluginBinding = null;
    }

    private void detachFromAppComponent() {
        if (isAttachedToActivity()) {
            detachFromActivity();
            return;
        }
        if (isAttachedToService()) {
            detachFromService();
        } else if (isAttachedToBroadcastReceiver()) {
            detachFromBroadcastReceiver();
        } else if (isAttachedToContentProvider()) {
            detachFromContentProvider();
        }
    }

    private boolean isAttachedToActivity() {
        return this.exclusiveActivity != null;
    }

    private boolean isAttachedToBroadcastReceiver() {
        return this.broadcastReceiver != null;
    }

    private boolean isAttachedToContentProvider() {
        return this.contentProvider != null;
    }

    private boolean isAttachedToService() {
        return this.service != null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.flutter.embedding.engine.plugins.PluginRegistry
    public void add(@NonNull FlutterPlugin flutterPlugin) {
        TraceSection traceSectionScoped = TraceSection.scoped("FlutterEngineConnectionRegistry#add " + flutterPlugin.getClass().getSimpleName());
        try {
            if (has(flutterPlugin.getClass())) {
                Log.w(TAG, "Attempted to register plugin (" + flutterPlugin + ") but it was already registered with this FlutterEngine (" + this.flutterEngine + ").");
                if (traceSectionScoped != null) {
                    traceSectionScoped.close();
                    return;
                }
                return;
            }
            Log.v(TAG, "Adding plugin: " + flutterPlugin);
            this.plugins.put(flutterPlugin.getClass(), flutterPlugin);
            flutterPlugin.onAttachedToEngine(this.pluginBinding);
            if (flutterPlugin instanceof ActivityAware) {
                ActivityAware activityAware = (ActivityAware) flutterPlugin;
                this.activityAwarePlugins.put(flutterPlugin.getClass(), activityAware);
                if (isAttachedToActivity()) {
                    activityAware.onAttachedToActivity(this.activityPluginBinding);
                }
            }
            if (flutterPlugin instanceof ServiceAware) {
                ServiceAware serviceAware = (ServiceAware) flutterPlugin;
                this.serviceAwarePlugins.put(flutterPlugin.getClass(), serviceAware);
                if (isAttachedToService()) {
                    serviceAware.onAttachedToService(this.servicePluginBinding);
                }
            }
            if (flutterPlugin instanceof BroadcastReceiverAware) {
                BroadcastReceiverAware broadcastReceiverAware = (BroadcastReceiverAware) flutterPlugin;
                this.broadcastReceiverAwarePlugins.put(flutterPlugin.getClass(), broadcastReceiverAware);
                if (isAttachedToBroadcastReceiver()) {
                    broadcastReceiverAware.onAttachedToBroadcastReceiver(this.broadcastReceiverPluginBinding);
                }
            }
            if (flutterPlugin instanceof ContentProviderAware) {
                ContentProviderAware contentProviderAware = (ContentProviderAware) flutterPlugin;
                this.contentProviderAwarePlugins.put(flutterPlugin.getClass(), contentProviderAware);
                if (isAttachedToContentProvider()) {
                    contentProviderAware.onAttachedToContentProvider(this.contentProviderPluginBinding);
                }
            }
            if (traceSectionScoped != null) {
                traceSectionScoped.close();
            }
        } catch (Throwable th) {
            if (traceSectionScoped != null) {
                try {
                    traceSectionScoped.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityControlSurface
    public void attachToActivity(@NonNull ExclusiveAppComponent<Activity> exclusiveAppComponent, @NonNull Lifecycle lifecycle) {
        TraceSection traceSectionScoped = TraceSection.scoped("FlutterEngineConnectionRegistry#attachToActivity");
        try {
            ExclusiveAppComponent<Activity> exclusiveAppComponent2 = this.exclusiveActivity;
            if (exclusiveAppComponent2 != null) {
                exclusiveAppComponent2.detachFromFlutterEngine();
            }
            detachFromAppComponent();
            this.exclusiveActivity = exclusiveAppComponent;
            attachToActivityInternal(exclusiveAppComponent.getAppComponent(), lifecycle);
            if (traceSectionScoped != null) {
                traceSectionScoped.close();
            }
        } catch (Throwable th) {
            if (traceSectionScoped != null) {
                try {
                    traceSectionScoped.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.broadcastreceiver.BroadcastReceiverControlSurface
    public void attachToBroadcastReceiver(@NonNull BroadcastReceiver broadcastReceiver, @NonNull Lifecycle lifecycle) {
        TraceSection traceSectionScoped = TraceSection.scoped("FlutterEngineConnectionRegistry#attachToBroadcastReceiver");
        try {
            detachFromAppComponent();
            this.broadcastReceiver = broadcastReceiver;
            this.broadcastReceiverPluginBinding = new FlutterEngineBroadcastReceiverPluginBinding(broadcastReceiver);
            Iterator<BroadcastReceiverAware> it = this.broadcastReceiverAwarePlugins.values().iterator();
            while (it.hasNext()) {
                it.next().onAttachedToBroadcastReceiver(this.broadcastReceiverPluginBinding);
            }
            if (traceSectionScoped != null) {
                traceSectionScoped.close();
            }
        } catch (Throwable th) {
            if (traceSectionScoped != null) {
                try {
                    traceSectionScoped.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.contentprovider.ContentProviderControlSurface
    public void attachToContentProvider(@NonNull ContentProvider contentProvider, @NonNull Lifecycle lifecycle) {
        TraceSection traceSectionScoped = TraceSection.scoped("FlutterEngineConnectionRegistry#attachToContentProvider");
        try {
            detachFromAppComponent();
            this.contentProvider = contentProvider;
            this.contentProviderPluginBinding = new FlutterEngineContentProviderPluginBinding(contentProvider);
            Iterator<ContentProviderAware> it = this.contentProviderAwarePlugins.values().iterator();
            while (it.hasNext()) {
                it.next().onAttachedToContentProvider(this.contentProviderPluginBinding);
            }
            if (traceSectionScoped != null) {
                traceSectionScoped.close();
            }
        } catch (Throwable th) {
            if (traceSectionScoped != null) {
                try {
                    traceSectionScoped.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.service.ServiceControlSurface
    public void attachToService(@NonNull Service service, @Nullable Lifecycle lifecycle, boolean z2) {
        TraceSection traceSectionScoped = TraceSection.scoped("FlutterEngineConnectionRegistry#attachToService");
        try {
            detachFromAppComponent();
            this.service = service;
            this.servicePluginBinding = new FlutterEngineServicePluginBinding(service, lifecycle);
            Iterator<ServiceAware> it = this.serviceAwarePlugins.values().iterator();
            while (it.hasNext()) {
                it.next().onAttachedToService(this.servicePluginBinding);
            }
            if (traceSectionScoped != null) {
                traceSectionScoped.close();
            }
        } catch (Throwable th) {
            if (traceSectionScoped != null) {
                try {
                    traceSectionScoped.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public void destroy() {
        Log.v(TAG, "Destroying.");
        detachFromAppComponent();
        removeAll();
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityControlSurface
    public void detachFromActivity() {
        if (!isAttachedToActivity()) {
            Log.e(TAG, "Attempted to detach plugins from an Activity when no Activity was attached.");
            return;
        }
        TraceSection traceSectionScoped = TraceSection.scoped("FlutterEngineConnectionRegistry#detachFromActivity");
        try {
            Iterator<ActivityAware> it = this.activityAwarePlugins.values().iterator();
            while (it.hasNext()) {
                it.next().onDetachedFromActivity();
            }
            detachFromActivityInternal();
            if (traceSectionScoped != null) {
                traceSectionScoped.close();
            }
        } catch (Throwable th) {
            if (traceSectionScoped != null) {
                try {
                    traceSectionScoped.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityControlSurface
    public void detachFromActivityForConfigChanges() {
        if (!isAttachedToActivity()) {
            Log.e(TAG, "Attempted to detach plugins from an Activity when no Activity was attached.");
            return;
        }
        TraceSection traceSectionScoped = TraceSection.scoped("FlutterEngineConnectionRegistry#detachFromActivityForConfigChanges");
        try {
            this.isWaitingForActivityReattachment = true;
            Iterator<ActivityAware> it = this.activityAwarePlugins.values().iterator();
            while (it.hasNext()) {
                it.next().onDetachedFromActivityForConfigChanges();
            }
            detachFromActivityInternal();
            if (traceSectionScoped != null) {
                traceSectionScoped.close();
            }
        } catch (Throwable th) {
            if (traceSectionScoped != null) {
                try {
                    traceSectionScoped.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.broadcastreceiver.BroadcastReceiverControlSurface
    public void detachFromBroadcastReceiver() {
        if (!isAttachedToBroadcastReceiver()) {
            Log.e(TAG, "Attempted to detach plugins from a BroadcastReceiver when no BroadcastReceiver was attached.");
            return;
        }
        TraceSection traceSectionScoped = TraceSection.scoped("FlutterEngineConnectionRegistry#detachFromBroadcastReceiver");
        try {
            Iterator<BroadcastReceiverAware> it = this.broadcastReceiverAwarePlugins.values().iterator();
            while (it.hasNext()) {
                it.next().onDetachedFromBroadcastReceiver();
            }
            if (traceSectionScoped != null) {
                traceSectionScoped.close();
            }
        } catch (Throwable th) {
            if (traceSectionScoped != null) {
                try {
                    traceSectionScoped.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.contentprovider.ContentProviderControlSurface
    public void detachFromContentProvider() {
        if (!isAttachedToContentProvider()) {
            Log.e(TAG, "Attempted to detach plugins from a ContentProvider when no ContentProvider was attached.");
            return;
        }
        TraceSection traceSectionScoped = TraceSection.scoped("FlutterEngineConnectionRegistry#detachFromContentProvider");
        try {
            Iterator<ContentProviderAware> it = this.contentProviderAwarePlugins.values().iterator();
            while (it.hasNext()) {
                it.next().onDetachedFromContentProvider();
            }
            if (traceSectionScoped != null) {
                traceSectionScoped.close();
            }
        } catch (Throwable th) {
            if (traceSectionScoped != null) {
                try {
                    traceSectionScoped.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.service.ServiceControlSurface
    public void detachFromService() {
        if (!isAttachedToService()) {
            Log.e(TAG, "Attempted to detach plugins from a Service when no Service was attached.");
            return;
        }
        TraceSection traceSectionScoped = TraceSection.scoped("FlutterEngineConnectionRegistry#detachFromService");
        try {
            Iterator<ServiceAware> it = this.serviceAwarePlugins.values().iterator();
            while (it.hasNext()) {
                it.next().onDetachedFromService();
            }
            this.service = null;
            this.servicePluginBinding = null;
            if (traceSectionScoped != null) {
                traceSectionScoped.close();
            }
        } catch (Throwable th) {
            if (traceSectionScoped != null) {
                try {
                    traceSectionScoped.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.PluginRegistry
    public FlutterPlugin get(@NonNull Class<? extends FlutterPlugin> cls) {
        return this.plugins.get(cls);
    }

    @Override // io.flutter.embedding.engine.plugins.PluginRegistry
    public boolean has(@NonNull Class<? extends FlutterPlugin> cls) {
        return this.plugins.containsKey(cls);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityControlSurface
    public boolean onActivityResult(int i2, int i3, @Nullable Intent intent) {
        if (!isAttachedToActivity()) {
            Log.e(TAG, "Attempted to notify ActivityAware plugins of onActivityResult, but no Activity was attached.");
            return false;
        }
        TraceSection traceSectionScoped = TraceSection.scoped("FlutterEngineConnectionRegistry#onActivityResult");
        try {
            boolean zOnActivityResult = this.activityPluginBinding.onActivityResult(i2, i3, intent);
            if (traceSectionScoped != null) {
                traceSectionScoped.close();
            }
            return zOnActivityResult;
        } catch (Throwable th) {
            if (traceSectionScoped != null) {
                try {
                    traceSectionScoped.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.service.ServiceControlSurface
    public void onMoveToBackground() {
        if (isAttachedToService()) {
            TraceSection traceSectionScoped = TraceSection.scoped("FlutterEngineConnectionRegistry#onMoveToBackground");
            try {
                this.servicePluginBinding.onMoveToBackground();
                if (traceSectionScoped != null) {
                    traceSectionScoped.close();
                }
            } catch (Throwable th) {
                if (traceSectionScoped != null) {
                    try {
                        traceSectionScoped.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }

    @Override // io.flutter.embedding.engine.plugins.service.ServiceControlSurface
    public void onMoveToForeground() {
        if (isAttachedToService()) {
            TraceSection traceSectionScoped = TraceSection.scoped("FlutterEngineConnectionRegistry#onMoveToForeground");
            try {
                this.servicePluginBinding.onMoveToForeground();
                if (traceSectionScoped != null) {
                    traceSectionScoped.close();
                }
            } catch (Throwable th) {
                if (traceSectionScoped != null) {
                    try {
                        traceSectionScoped.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityControlSurface
    public void onNewIntent(@NonNull Intent intent) {
        if (!isAttachedToActivity()) {
            Log.e(TAG, "Attempted to notify ActivityAware plugins of onNewIntent, but no Activity was attached.");
            return;
        }
        TraceSection traceSectionScoped = TraceSection.scoped("FlutterEngineConnectionRegistry#onNewIntent");
        try {
            this.activityPluginBinding.onNewIntent(intent);
            if (traceSectionScoped != null) {
                traceSectionScoped.close();
            }
        } catch (Throwable th) {
            if (traceSectionScoped != null) {
                try {
                    traceSectionScoped.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityControlSurface
    public boolean onRequestPermissionsResult(int i2, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (!isAttachedToActivity()) {
            Log.e(TAG, "Attempted to notify ActivityAware plugins of onRequestPermissionsResult, but no Activity was attached.");
            return false;
        }
        TraceSection traceSectionScoped = TraceSection.scoped("FlutterEngineConnectionRegistry#onRequestPermissionsResult");
        try {
            boolean zOnRequestPermissionsResult = this.activityPluginBinding.onRequestPermissionsResult(i2, strArr, iArr);
            if (traceSectionScoped != null) {
                traceSectionScoped.close();
            }
            return zOnRequestPermissionsResult;
        } catch (Throwable th) {
            if (traceSectionScoped != null) {
                try {
                    traceSectionScoped.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityControlSurface
    public void onRestoreInstanceState(@Nullable Bundle bundle) {
        if (!isAttachedToActivity()) {
            Log.e(TAG, "Attempted to notify ActivityAware plugins of onRestoreInstanceState, but no Activity was attached.");
            return;
        }
        TraceSection traceSectionScoped = TraceSection.scoped("FlutterEngineConnectionRegistry#onRestoreInstanceState");
        try {
            this.activityPluginBinding.onRestoreInstanceState(bundle);
            if (traceSectionScoped != null) {
                traceSectionScoped.close();
            }
        } catch (Throwable th) {
            if (traceSectionScoped != null) {
                try {
                    traceSectionScoped.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityControlSurface
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        if (!isAttachedToActivity()) {
            Log.e(TAG, "Attempted to notify ActivityAware plugins of onSaveInstanceState, but no Activity was attached.");
            return;
        }
        TraceSection traceSectionScoped = TraceSection.scoped("FlutterEngineConnectionRegistry#onSaveInstanceState");
        try {
            this.activityPluginBinding.onSaveInstanceState(bundle);
            if (traceSectionScoped != null) {
                traceSectionScoped.close();
            }
        } catch (Throwable th) {
            if (traceSectionScoped != null) {
                try {
                    traceSectionScoped.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityControlSurface
    public void onUserLeaveHint() {
        if (!isAttachedToActivity()) {
            Log.e(TAG, "Attempted to notify ActivityAware plugins of onUserLeaveHint, but no Activity was attached.");
            return;
        }
        TraceSection traceSectionScoped = TraceSection.scoped("FlutterEngineConnectionRegistry#onUserLeaveHint");
        try {
            this.activityPluginBinding.onUserLeaveHint();
            if (traceSectionScoped != null) {
                traceSectionScoped.close();
            }
        } catch (Throwable th) {
            if (traceSectionScoped != null) {
                try {
                    traceSectionScoped.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.PluginRegistry
    public void remove(@NonNull Class<? extends FlutterPlugin> cls) {
        FlutterPlugin flutterPlugin = this.plugins.get(cls);
        if (flutterPlugin == null) {
            return;
        }
        TraceSection traceSectionScoped = TraceSection.scoped("FlutterEngineConnectionRegistry#remove " + cls.getSimpleName());
        try {
            if (flutterPlugin instanceof ActivityAware) {
                if (isAttachedToActivity()) {
                    ((ActivityAware) flutterPlugin).onDetachedFromActivity();
                }
                this.activityAwarePlugins.remove(cls);
            }
            if (flutterPlugin instanceof ServiceAware) {
                if (isAttachedToService()) {
                    ((ServiceAware) flutterPlugin).onDetachedFromService();
                }
                this.serviceAwarePlugins.remove(cls);
            }
            if (flutterPlugin instanceof BroadcastReceiverAware) {
                if (isAttachedToBroadcastReceiver()) {
                    ((BroadcastReceiverAware) flutterPlugin).onDetachedFromBroadcastReceiver();
                }
                this.broadcastReceiverAwarePlugins.remove(cls);
            }
            if (flutterPlugin instanceof ContentProviderAware) {
                if (isAttachedToContentProvider()) {
                    ((ContentProviderAware) flutterPlugin).onDetachedFromContentProvider();
                }
                this.contentProviderAwarePlugins.remove(cls);
            }
            flutterPlugin.onDetachedFromEngine(this.pluginBinding);
            this.plugins.remove(cls);
            if (traceSectionScoped != null) {
                traceSectionScoped.close();
            }
        } catch (Throwable th) {
            if (traceSectionScoped != null) {
                try {
                    traceSectionScoped.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.PluginRegistry
    public void removeAll() {
        remove(new HashSet(this.plugins.keySet()));
        this.plugins.clear();
    }

    @Override // io.flutter.embedding.engine.plugins.PluginRegistry
    public void remove(@NonNull Set<Class<? extends FlutterPlugin>> set) {
        Iterator<Class<? extends FlutterPlugin>> it = set.iterator();
        while (it.hasNext()) {
            remove(it.next());
        }
    }

    @Override // io.flutter.embedding.engine.plugins.PluginRegistry
    public void add(@NonNull Set<FlutterPlugin> set) {
        Iterator<FlutterPlugin> it = set.iterator();
        while (it.hasNext()) {
            add(it.next());
        }
    }
}
