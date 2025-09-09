package io.flutter.plugins.imagepicker;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.embedding.engine.plugins.lifecycle.FlutterLifecycleAdapter;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugins.imagepicker.ImagePickerDelegate;
import io.flutter.plugins.imagepicker.Messages;
import java.util.List;

/* loaded from: classes4.dex */
public class ImagePickerPlugin implements FlutterPlugin, ActivityAware, Messages.ImagePickerApi {
    ActivityState activityState;
    private FlutterPlugin.FlutterPluginBinding pluginBinding;

    /* renamed from: io.flutter.plugins.imagepicker.ImagePickerPlugin$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$flutter$plugins$imagepicker$Messages$SourceCamera;
        static final /* synthetic */ int[] $SwitchMap$io$flutter$plugins$imagepicker$Messages$SourceType;

        static {
            int[] iArr = new int[Messages.SourceType.values().length];
            $SwitchMap$io$flutter$plugins$imagepicker$Messages$SourceType = iArr;
            try {
                iArr[Messages.SourceType.GALLERY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$flutter$plugins$imagepicker$Messages$SourceType[Messages.SourceType.CAMERA.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[Messages.SourceCamera.values().length];
            $SwitchMap$io$flutter$plugins$imagepicker$Messages$SourceCamera = iArr2;
            try {
                iArr2[Messages.SourceCamera.FRONT.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$io$flutter$plugins$imagepicker$Messages$SourceCamera[Messages.SourceCamera.REAR.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private class LifeCycleObserver implements Application.ActivityLifecycleCallbacks, DefaultLifecycleObserver {
        private final Activity thisActivity;

        LifeCycleObserver(Activity activity) {
            this.thisActivity = activity;
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
            if (this.thisActivity != activity || activity.getApplicationContext() == null) {
                return;
            }
            ((Application) activity.getApplicationContext()).unregisterActivityLifecycleCallbacks(this);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
            if (this.thisActivity == activity) {
                ImagePickerPlugin.this.activityState.getDelegate().saveStateBeforeResult();
            }
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public void onCreate(@NonNull LifecycleOwner lifecycleOwner) {
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public void onDestroy(@NonNull LifecycleOwner lifecycleOwner) {
            onActivityDestroyed(this.thisActivity);
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public void onPause(@NonNull LifecycleOwner lifecycleOwner) {
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public void onResume(@NonNull LifecycleOwner lifecycleOwner) {
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public void onStart(@NonNull LifecycleOwner lifecycleOwner) {
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public void onStop(@NonNull LifecycleOwner lifecycleOwner) {
            onActivityStopped(this.thisActivity);
        }
    }

    public ImagePickerPlugin() {
    }

    @Nullable
    private ImagePickerDelegate getImagePickerDelegate() {
        ActivityState activityState = this.activityState;
        if (activityState == null || activityState.getActivity() == null) {
            return null;
        }
        return this.activityState.getDelegate();
    }

    private void setCameraDevice(@NonNull ImagePickerDelegate imagePickerDelegate, @NonNull Messages.SourceSpecification sourceSpecification) {
        Messages.SourceCamera camera = sourceSpecification.getCamera();
        if (camera != null) {
            imagePickerDelegate.setCameraDevice(AnonymousClass1.$SwitchMap$io$flutter$plugins$imagepicker$Messages$SourceCamera[camera.ordinal()] != 1 ? ImagePickerDelegate.CameraDevice.REAR : ImagePickerDelegate.CameraDevice.FRONT);
        }
    }

    private void setup(BinaryMessenger binaryMessenger, Application application, Activity activity, ActivityPluginBinding activityPluginBinding) {
        this.activityState = new ActivityState(application, activity, binaryMessenger, this, activityPluginBinding);
    }

    private void tearDown() {
        ActivityState activityState = this.activityState;
        if (activityState != null) {
            activityState.release();
            this.activityState = null;
        }
    }

    @VisibleForTesting
    final ImagePickerDelegate constructDelegate(Activity activity) {
        return new ImagePickerDelegate(activity, new ImageResizer(activity, new ExifDataCopier()), new ImagePickerCache(activity));
    }

    @VisibleForTesting
    final ActivityState getActivityState() {
        return this.activityState;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(@NonNull ActivityPluginBinding activityPluginBinding) {
        setup(this.pluginBinding.getBinaryMessenger(), (Application) this.pluginBinding.getApplicationContext(), activityPluginBinding.getActivity(), activityPluginBinding);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.pluginBinding = flutterPluginBinding;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        tearDown();
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity();
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.pluginBinding = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding activityPluginBinding) {
        onAttachedToActivity(activityPluginBinding);
    }

    @Override // io.flutter.plugins.imagepicker.Messages.ImagePickerApi
    public void pickImages(@NonNull Messages.SourceSpecification sourceSpecification, @NonNull Messages.ImageSelectionOptions imageSelectionOptions, @NonNull Messages.GeneralOptions generalOptions, @NonNull Messages.Result<List<String>> result) {
        ImagePickerDelegate imagePickerDelegate = getImagePickerDelegate();
        if (imagePickerDelegate == null) {
            result.error(new Messages.FlutterError("no_activity", "image_picker plugin requires a foreground activity.", null));
            return;
        }
        setCameraDevice(imagePickerDelegate, sourceSpecification);
        if (generalOptions.getAllowMultiple().booleanValue()) {
            imagePickerDelegate.chooseMultiImageFromGallery(imageSelectionOptions, generalOptions.getUsePhotoPicker().booleanValue(), ImagePickerUtils.getLimitFromOption(generalOptions), result);
            return;
        }
        int i2 = AnonymousClass1.$SwitchMap$io$flutter$plugins$imagepicker$Messages$SourceType[sourceSpecification.getType().ordinal()];
        if (i2 == 1) {
            imagePickerDelegate.chooseImageFromGallery(imageSelectionOptions, generalOptions.getUsePhotoPicker().booleanValue(), result);
        } else {
            if (i2 != 2) {
                return;
            }
            imagePickerDelegate.takeImageWithCamera(imageSelectionOptions, result);
        }
    }

    @Override // io.flutter.plugins.imagepicker.Messages.ImagePickerApi
    public void pickMedia(@NonNull Messages.MediaSelectionOptions mediaSelectionOptions, @NonNull Messages.GeneralOptions generalOptions, @NonNull Messages.Result<List<String>> result) {
        ImagePickerDelegate imagePickerDelegate = getImagePickerDelegate();
        if (imagePickerDelegate == null) {
            result.error(new Messages.FlutterError("no_activity", "image_picker plugin requires a foreground activity.", null));
        } else {
            imagePickerDelegate.chooseMediaFromGallery(mediaSelectionOptions, generalOptions, result);
        }
    }

    @Override // io.flutter.plugins.imagepicker.Messages.ImagePickerApi
    public void pickVideos(@NonNull Messages.SourceSpecification sourceSpecification, @NonNull Messages.VideoSelectionOptions videoSelectionOptions, @NonNull Messages.GeneralOptions generalOptions, @NonNull Messages.Result<List<String>> result) {
        ImagePickerDelegate imagePickerDelegate = getImagePickerDelegate();
        if (imagePickerDelegate == null) {
            result.error(new Messages.FlutterError("no_activity", "image_picker plugin requires a foreground activity.", null));
            return;
        }
        setCameraDevice(imagePickerDelegate, sourceSpecification);
        if (generalOptions.getAllowMultiple().booleanValue()) {
            result.error(new RuntimeException("Multi-video selection is not implemented"));
            return;
        }
        int i2 = AnonymousClass1.$SwitchMap$io$flutter$plugins$imagepicker$Messages$SourceType[sourceSpecification.getType().ordinal()];
        if (i2 == 1) {
            imagePickerDelegate.chooseVideoFromGallery(videoSelectionOptions, generalOptions.getUsePhotoPicker().booleanValue(), result);
        } else {
            if (i2 != 2) {
                return;
            }
            imagePickerDelegate.takeVideoWithCamera(videoSelectionOptions, result);
        }
    }

    @Override // io.flutter.plugins.imagepicker.Messages.ImagePickerApi
    @Nullable
    public Messages.CacheRetrievalResult retrieveLostResults() {
        ImagePickerDelegate imagePickerDelegate = getImagePickerDelegate();
        if (imagePickerDelegate != null) {
            return imagePickerDelegate.retrieveLostImage();
        }
        throw new Messages.FlutterError("no_activity", "image_picker plugin requires a foreground activity.", null);
    }

    @VisibleForTesting
    ImagePickerPlugin(ImagePickerDelegate imagePickerDelegate, Activity activity) {
        this.activityState = new ActivityState(imagePickerDelegate, activity);
    }

    private class ActivityState {
        private Activity activity;
        private ActivityPluginBinding activityBinding;
        private Application application;
        private ImagePickerDelegate delegate;
        private Lifecycle lifecycle;
        private BinaryMessenger messenger;
        private LifeCycleObserver observer;

        ActivityState(Application application, Activity activity, BinaryMessenger binaryMessenger, Messages.ImagePickerApi imagePickerApi, ActivityPluginBinding activityPluginBinding) {
            this.application = application;
            this.activity = activity;
            this.activityBinding = activityPluginBinding;
            this.messenger = binaryMessenger;
            this.delegate = ImagePickerPlugin.this.constructDelegate(activity);
            Messages.ImagePickerApi.CC.f(binaryMessenger, imagePickerApi);
            this.observer = ImagePickerPlugin.this.new LifeCycleObserver(activity);
            activityPluginBinding.addActivityResultListener(this.delegate);
            activityPluginBinding.addRequestPermissionsResultListener(this.delegate);
            Lifecycle activityLifecycle = FlutterLifecycleAdapter.getActivityLifecycle(activityPluginBinding);
            this.lifecycle = activityLifecycle;
            activityLifecycle.addObserver(this.observer);
        }

        Activity getActivity() {
            return this.activity;
        }

        ImagePickerDelegate getDelegate() {
            return this.delegate;
        }

        void release() {
            ActivityPluginBinding activityPluginBinding = this.activityBinding;
            if (activityPluginBinding != null) {
                activityPluginBinding.removeActivityResultListener(this.delegate);
                this.activityBinding.removeRequestPermissionsResultListener(this.delegate);
                this.activityBinding = null;
            }
            Lifecycle lifecycle = this.lifecycle;
            if (lifecycle != null) {
                lifecycle.removeObserver(this.observer);
                this.lifecycle = null;
            }
            Messages.ImagePickerApi.CC.f(this.messenger, null);
            Application application = this.application;
            if (application != null) {
                application.unregisterActivityLifecycleCallbacks(this.observer);
                this.application = null;
            }
            this.activity = null;
            this.observer = null;
            this.delegate = null;
        }

        ActivityState(ImagePickerDelegate imagePickerDelegate, Activity activity) {
            this.activity = activity;
            this.delegate = imagePickerDelegate;
        }
    }
}
