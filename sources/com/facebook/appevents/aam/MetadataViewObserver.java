package com.facebook.appevents.aam;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.EditText;
import androidx.annotation.Nullable;
import com.facebook.appevents.InternalAppEventsLogger;
import com.facebook.appevents.codeless.internal.ViewHierarchy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
final class MetadataViewObserver implements ViewTreeObserver.OnGlobalFocusChangeListener {
    private static final int MAX_TEXT_LENGTH = 100;
    private static final String TAG = "com.facebook.appevents.aam.MetadataViewObserver";
    private static final Map<Integer, MetadataViewObserver> observers = new HashMap();
    private WeakReference<Activity> activityWeakReference;
    private final Set<String> processedText = new HashSet();
    private final Handler uiThreadHandler = new Handler(Looper.getMainLooper());
    private AtomicBoolean isTracking = new AtomicBoolean(false);

    private MetadataViewObserver(Activity activity) {
        this.activityWeakReference = new WeakReference<>(activity);
    }

    @Nullable
    private View getRootView() {
        Window window;
        Activity activity = this.activityWeakReference.get();
        if (activity == null || (window = activity.getWindow()) == null) {
            return null;
        }
        return window.getDecorView().getRootView();
    }

    private void process(final View view) {
        runOnUIThread(new Runnable() { // from class: com.facebook.appevents.aam.MetadataViewObserver.1
            @Override // java.lang.Runnable
            public void run() {
                View view2 = view;
                if (view2 instanceof EditText) {
                    MetadataViewObserver.this.processEditText(view2);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processEditText(View view) {
        String strTrim = ((EditText) view).getText().toString().trim();
        if (strTrim.isEmpty() || this.processedText.contains(strTrim) || strTrim.length() > 100) {
            return;
        }
        this.processedText.add(strTrim);
        HashMap map = new HashMap();
        List<String> currentViewIndicators = null;
        ArrayList arrayList = null;
        for (MetadataRule metadataRule : MetadataRule.getRules()) {
            if (MetadataMatcher.matchValue(strTrim, metadataRule.getValRule())) {
                if (currentViewIndicators == null) {
                    currentViewIndicators = MetadataMatcher.getCurrentViewIndicators(view);
                }
                if (MetadataMatcher.matchIndicator(currentViewIndicators, metadataRule.getKeyRules())) {
                    map.put(metadataRule.getName(), strTrim);
                } else {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                        ViewGroup parentOfView = ViewHierarchy.getParentOfView(view);
                        if (parentOfView != null) {
                            for (View view2 : ViewHierarchy.getChildrenOfView(parentOfView)) {
                                if (view != view2) {
                                    arrayList.addAll(MetadataMatcher.getTextIndicators(view2));
                                }
                            }
                        }
                    }
                    if (MetadataMatcher.matchIndicator(arrayList, metadataRule.getKeyRules())) {
                        map.put(metadataRule.getName(), strTrim);
                    }
                }
            }
        }
        InternalAppEventsLogger.setInternalUserData(map);
    }

    private void runOnUIThread(Runnable runnable) {
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
            viewTreeObserver.addOnGlobalFocusChangeListener(this);
        }
    }

    static void startTrackingActivity(Activity activity) {
        MetadataViewObserver metadataViewObserver;
        int iHashCode = activity.hashCode();
        Map<Integer, MetadataViewObserver> map = observers;
        if (map.containsKey(Integer.valueOf(iHashCode))) {
            metadataViewObserver = map.get(Integer.valueOf(iHashCode));
        } else {
            metadataViewObserver = new MetadataViewObserver(activity);
            map.put(Integer.valueOf(activity.hashCode()), metadataViewObserver);
        }
        metadataViewObserver.startTracking();
    }

    private void stopTracking() {
        View rootView;
        if (this.isTracking.getAndSet(false) && (rootView = getRootView()) != null) {
            ViewTreeObserver viewTreeObserver = rootView.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.removeOnGlobalFocusChangeListener(this);
            }
        }
    }

    static void stopTrackingActivity(Activity activity) {
        int iHashCode = activity.hashCode();
        Map<Integer, MetadataViewObserver> map = observers;
        if (map.containsKey(Integer.valueOf(iHashCode))) {
            MetadataViewObserver metadataViewObserver = map.get(Integer.valueOf(activity.hashCode()));
            map.remove(Integer.valueOf(iHashCode));
            metadataViewObserver.stopTracking();
        }
    }

    @Override // android.view.ViewTreeObserver.OnGlobalFocusChangeListener
    public void onGlobalFocusChanged(@Nullable View view, @Nullable View view2) {
        if (view != null) {
            process(view);
        }
        if (view2 != null) {
            process(view2);
        }
    }
}
