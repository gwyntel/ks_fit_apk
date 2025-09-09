package com.facebook.appevents.suggestedevents;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import androidx.annotation.Nullable;
import com.facebook.appevents.codeless.internal.SensitiveUserDataUtils;
import com.facebook.appevents.codeless.internal.ViewHierarchy;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
final class ViewObserver implements ViewTreeObserver.OnGlobalLayoutListener {
    private static final int MAX_TEXT_LENGTH = 300;
    private static final String TAG = "com.facebook.appevents.suggestedevents.ViewObserver";
    private static final Map<Integer, ViewObserver> observers = new HashMap();
    private WeakReference<Activity> activityWeakReference;
    private final Handler uiThreadHandler = new Handler(Looper.getMainLooper());
    private AtomicBoolean isTracking = new AtomicBoolean(false);

    private ViewObserver(Activity activity) {
        this.activityWeakReference = new WeakReference<>(activity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Nullable
    public View getRootView() {
        Window window;
        Activity activity = this.activityWeakReference.get();
        if (activity == null || (window = activity.getWindow()) == null) {
            return null;
        }
        return window.getDecorView().getRootView();
    }

    private void process() {
        Runnable runnable = new Runnable() { // from class: com.facebook.appevents.suggestedevents.ViewObserver.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    View rootView = ViewObserver.this.getRootView();
                    Activity activity = (Activity) ViewObserver.this.activityWeakReference.get();
                    if (rootView != null && activity != null) {
                        for (View view : SuggestedEventViewHierarchy.getAllClickableViews(rootView)) {
                            if (!SensitiveUserDataUtils.isSensitiveUserData(view)) {
                                String textOfView = ViewHierarchy.getTextOfView(view);
                                if (!textOfView.isEmpty() && textOfView.length() <= 300) {
                                    ViewOnClickListener.attachListener(view, rootView, activity.getLocalClassName());
                                }
                            }
                        }
                    }
                } catch (Exception unused) {
                }
            }
        };
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            runnable.run();
        } else {
            this.uiThreadHandler.post(runnable);
        }
    }

    private void startTracking() {
        View rootView;
        if (this.isTracking.getAndSet(true) || (rootView = getRootView()) == null) {
            return;
        }
        ViewTreeObserver viewTreeObserver = rootView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(this);
            process();
        }
    }

    static void startTrackingActivity(Activity activity) {
        int iHashCode = activity.hashCode();
        Map<Integer, ViewObserver> map = observers;
        if (map.containsKey(Integer.valueOf(iHashCode))) {
            return;
        }
        ViewObserver viewObserver = new ViewObserver(activity);
        map.put(Integer.valueOf(iHashCode), viewObserver);
        viewObserver.startTracking();
    }

    private void stopTracking() {
        View rootView;
        if (this.isTracking.getAndSet(false) && (rootView = getRootView()) != null) {
            ViewTreeObserver viewTreeObserver = rootView.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.removeOnGlobalLayoutListener(this);
            }
        }
    }

    static void stopTrackingActivity(Activity activity) {
        int iHashCode = activity.hashCode();
        Map<Integer, ViewObserver> map = observers;
        if (map.containsKey(Integer.valueOf(iHashCode))) {
            ViewObserver viewObserver = map.get(Integer.valueOf(iHashCode));
            map.remove(Integer.valueOf(iHashCode));
            viewObserver.stopTracking();
        }
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        process();
    }
}
