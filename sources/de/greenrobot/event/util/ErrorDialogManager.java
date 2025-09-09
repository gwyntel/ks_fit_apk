package de.greenrobot.event.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.FragmentActivity;
import de.greenrobot.event.EventBus;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes4.dex */
public class ErrorDialogManager {
    public static final String KEY_EVENT_TYPE_ON_CLOSE = "de.greenrobot.eventbus.errordialog.event_type_on_close";
    public static final String KEY_FINISH_AFTER_DIALOG = "de.greenrobot.eventbus.errordialog.finish_after_dialog";
    public static final String KEY_ICON_ID = "de.greenrobot.eventbus.errordialog.icon_id";
    public static final String KEY_MESSAGE = "de.greenrobot.eventbus.errordialog.message";
    public static final String KEY_TITLE = "de.greenrobot.eventbus.errordialog.title";
    public static ErrorDialogFragmentFactory<?> factory;

    @TargetApi(11)
    public static class HoneycombManagerFragment extends Fragment {

        /* renamed from: a, reason: collision with root package name */
        protected boolean f24995a;

        /* renamed from: b, reason: collision with root package name */
        protected Bundle f24996b;
        private EventBus eventBus;

        public static void attachTo(Activity activity, boolean z2, Bundle bundle) {
            FragmentManager fragmentManager = activity.getFragmentManager();
            HoneycombManagerFragment honeycombManagerFragment = (HoneycombManagerFragment) fragmentManager.findFragmentByTag("de.greenrobot.eventbus.error_dialog_manager");
            if (honeycombManagerFragment == null) {
                honeycombManagerFragment = new HoneycombManagerFragment();
                fragmentManager.beginTransaction().add(honeycombManagerFragment, "de.greenrobot.eventbus.error_dialog_manager").commit();
                fragmentManager.executePendingTransactions();
            }
            honeycombManagerFragment.f24995a = z2;
            honeycombManagerFragment.f24996b = bundle;
        }

        public void onEventMainThread(ThrowableFailureEvent throwableFailureEvent) {
            ErrorDialogManager.a(throwableFailureEvent);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.executePendingTransactions();
            DialogFragment dialogFragment = (DialogFragment) fragmentManager.findFragmentByTag("de.greenrobot.eventbus.error_dialog");
            if (dialogFragment != null) {
                dialogFragment.dismiss();
            }
            DialogFragment dialogFragment2 = (DialogFragment) ErrorDialogManager.factory.d(throwableFailureEvent, this.f24995a, this.f24996b);
            if (dialogFragment2 != null) {
                dialogFragment2.show(fragmentManager, "de.greenrobot.eventbus.error_dialog");
            }
        }

        @Override // android.app.Fragment
        public void onPause() {
            this.eventBus.unregister(this);
            super.onPause();
        }

        @Override // android.app.Fragment
        public void onResume() throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
            super.onResume();
            EventBus eventBusA = ErrorDialogManager.factory.f24994a.a();
            this.eventBus = eventBusA;
            eventBusA.register(this);
        }
    }

    public static class SupportManagerFragment extends androidx.fragment.app.Fragment {

        /* renamed from: a, reason: collision with root package name */
        protected boolean f24997a;

        /* renamed from: b, reason: collision with root package name */
        protected Bundle f24998b;
        private EventBus eventBus;
        private boolean skipRegisterOnNextResume;

        public static void attachTo(Activity activity, boolean z2, Bundle bundle) {
            androidx.fragment.app.FragmentManager supportFragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
            SupportManagerFragment supportManagerFragment = (SupportManagerFragment) supportFragmentManager.findFragmentByTag("de.greenrobot.eventbus.error_dialog_manager");
            if (supportManagerFragment == null) {
                supportManagerFragment = new SupportManagerFragment();
                supportFragmentManager.beginTransaction().add(supportManagerFragment, "de.greenrobot.eventbus.error_dialog_manager").commit();
                supportFragmentManager.executePendingTransactions();
            }
            supportManagerFragment.f24997a = z2;
            supportManagerFragment.f24998b = bundle;
        }

        @Override // androidx.fragment.app.Fragment
        public void onCreate(Bundle bundle) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
            super.onCreate(bundle);
            EventBus eventBusA = ErrorDialogManager.factory.f24994a.a();
            this.eventBus = eventBusA;
            eventBusA.register(this);
            this.skipRegisterOnNextResume = true;
        }

        public void onEventMainThread(ThrowableFailureEvent throwableFailureEvent) {
            ErrorDialogManager.a(throwableFailureEvent);
            androidx.fragment.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.executePendingTransactions();
            androidx.fragment.app.DialogFragment dialogFragment = (androidx.fragment.app.DialogFragment) fragmentManager.findFragmentByTag("de.greenrobot.eventbus.error_dialog");
            if (dialogFragment != null) {
                dialogFragment.dismiss();
            }
            androidx.fragment.app.DialogFragment dialogFragment2 = (androidx.fragment.app.DialogFragment) ErrorDialogManager.factory.d(throwableFailureEvent, this.f24997a, this.f24998b);
            if (dialogFragment2 != null) {
                dialogFragment2.show(fragmentManager, "de.greenrobot.eventbus.error_dialog");
            }
        }

        @Override // androidx.fragment.app.Fragment
        public void onPause() {
            this.eventBus.unregister(this);
            super.onPause();
        }

        @Override // androidx.fragment.app.Fragment
        public void onResume() throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
            super.onResume();
            if (this.skipRegisterOnNextResume) {
                this.skipRegisterOnNextResume = false;
                return;
            }
            EventBus eventBusA = ErrorDialogManager.factory.f24994a.a();
            this.eventBus = eventBusA;
            eventBusA.register(this);
        }
    }

    protected static void a(ThrowableFailureEvent throwableFailureEvent) {
        ErrorDialogConfig errorDialogConfig = factory.f24994a;
        if (errorDialogConfig.f24990f) {
            String str = errorDialogConfig.f24991g;
            if (str == null) {
                str = EventBus.TAG;
            }
            Log.i(str, "Error dialog manager received exception", throwableFailureEvent.f24999a);
        }
    }

    public static void attachTo(Activity activity) {
        attachTo(activity, false, null);
    }

    private static boolean isSupportActivity(Activity activity) {
        String name;
        Class<?> superclass = activity.getClass();
        do {
            superclass = superclass.getSuperclass();
            if (superclass == null) {
                throw new RuntimeException("Illegal activity type: " + activity.getClass());
            }
            name = superclass.getName();
            if (name.equals("androidx.fragment.app.FragmentActivity")) {
                return true;
            }
            if (name.startsWith("com.actionbarsherlock.app") && (name.endsWith(".SherlockActivity") || name.endsWith(".SherlockListActivity") || name.endsWith(".SherlockPreferenceActivity"))) {
                throw new RuntimeException("Please use SherlockFragmentActivity. Illegal activity: " + name);
            }
        } while (!name.equals("android.app.Activity"));
        return false;
    }

    public static void attachTo(Activity activity, boolean z2) {
        attachTo(activity, z2, null);
    }

    public static void attachTo(Activity activity, boolean z2, Bundle bundle) {
        if (factory != null) {
            if (isSupportActivity(activity)) {
                SupportManagerFragment.attachTo(activity, z2, bundle);
                return;
            } else {
                HoneycombManagerFragment.attachTo(activity, z2, bundle);
                return;
            }
        }
        throw new RuntimeException("You must set the static factory field to configure error dialogs for your app.");
    }
}
