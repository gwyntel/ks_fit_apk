package com.facebook.appevents.codeless;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.Nullable;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.codeless.CodelessLoggingEventListener;
import com.facebook.appevents.codeless.RCTCodelessLoggingEventListener;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.appevents.codeless.internal.EventBinding;
import com.facebook.appevents.codeless.internal.ParameterComponent;
import com.facebook.appevents.codeless.internal.PathComponent;
import com.facebook.appevents.codeless.internal.ViewHierarchy;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.InternalSettings;
import com.facebook.internal.Utility;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

/* loaded from: classes3.dex */
class CodelessMatcher {
    private static final String CURRENT_CLASS_NAME = ".";
    private static final String PARENT_CLASS_NAME = "..";
    private static final String TAG = "com.facebook.appevents.codeless.CodelessMatcher";
    private static CodelessMatcher codelessMatcher;
    private final Handler uiThreadHandler = new Handler(Looper.getMainLooper());
    private Set<Activity> activitiesSet = Collections.newSetFromMap(new WeakHashMap());
    private Set<ViewMatcher> viewMatchers = new HashSet();
    private HashSet<String> listenerSet = new HashSet<>();
    private HashMap<Integer, HashSet<String>> activityToListenerMap = new HashMap<>();

    public static class MatchedView {
        private WeakReference<View> view;
        private String viewMapKey;

        public MatchedView(View view, String str) {
            this.view = new WeakReference<>(view);
            this.viewMapKey = str;
        }

        @Nullable
        public View getView() {
            WeakReference<View> weakReference = this.view;
            if (weakReference == null) {
                return null;
            }
            return weakReference.get();
        }

        public String getViewMapKey() {
            return this.viewMapKey;
        }
    }

    protected static class ViewMatcher implements ViewTreeObserver.OnGlobalLayoutListener, ViewTreeObserver.OnScrollChangedListener, Runnable {
        private final String activityName;

        @Nullable
        private List<EventBinding> eventBindings;
        private final Handler handler;
        private HashSet<String> listenerSet;
        private WeakReference<View> rootView;

        public ViewMatcher(View view, Handler handler, HashSet<String> hashSet, String str) {
            this.rootView = new WeakReference<>(view);
            this.handler = handler;
            this.listenerSet = hashSet;
            this.activityName = str;
            handler.postDelayed(this, 200L);
        }

        private void attachListener(MatchedView matchedView, View view, EventBinding eventBinding) {
            if (eventBinding == null) {
                return;
            }
            try {
                View view2 = matchedView.getView();
                if (view2 == null) {
                    return;
                }
                View viewFindRCTRootView = ViewHierarchy.findRCTRootView(view2);
                if (viewFindRCTRootView != null && ViewHierarchy.isRCTButton(view2, viewFindRCTRootView)) {
                    attachRCTListener(matchedView, view, eventBinding);
                    return;
                }
                if (view2.getClass().getName().startsWith("com.facebook.react")) {
                    return;
                }
                if (!(view2 instanceof AdapterView)) {
                    attachOnClickListener(matchedView, view, eventBinding);
                } else if (view2 instanceof ListView) {
                    attachOnItemClickListener(matchedView, view, eventBinding);
                }
            } catch (Exception e2) {
                Utility.logd(CodelessMatcher.TAG, e2);
            }
        }

        private void attachOnClickListener(MatchedView matchedView, View view, EventBinding eventBinding) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
            View view2 = matchedView.getView();
            if (view2 == null) {
                return;
            }
            String viewMapKey = matchedView.getViewMapKey();
            View.OnClickListener existingOnClickListener = ViewHierarchy.getExistingOnClickListener(view2);
            boolean z2 = (existingOnClickListener instanceof CodelessLoggingEventListener.AutoLoggingOnClickListener) && ((CodelessLoggingEventListener.AutoLoggingOnClickListener) existingOnClickListener).getSupportCodelessLogging();
            if (this.listenerSet.contains(viewMapKey) || z2) {
                return;
            }
            view2.setOnClickListener(CodelessLoggingEventListener.getOnClickListener(eventBinding, view, view2));
            this.listenerSet.add(viewMapKey);
        }

        private void attachOnItemClickListener(MatchedView matchedView, View view, EventBinding eventBinding) {
            AdapterView adapterView = (AdapterView) matchedView.getView();
            if (adapterView == null) {
                return;
            }
            String viewMapKey = matchedView.getViewMapKey();
            AdapterView.OnItemClickListener onItemClickListener = adapterView.getOnItemClickListener();
            boolean z2 = (onItemClickListener instanceof CodelessLoggingEventListener.AutoLoggingOnItemClickListener) && ((CodelessLoggingEventListener.AutoLoggingOnItemClickListener) onItemClickListener).getSupportCodelessLogging();
            if (this.listenerSet.contains(viewMapKey) || z2) {
                return;
            }
            adapterView.setOnItemClickListener(CodelessLoggingEventListener.getOnItemClickListener(eventBinding, view, adapterView));
            this.listenerSet.add(viewMapKey);
        }

        private void attachRCTListener(MatchedView matchedView, View view, EventBinding eventBinding) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
            View view2 = matchedView.getView();
            if (view2 == null) {
                return;
            }
            String viewMapKey = matchedView.getViewMapKey();
            View.OnTouchListener existingOnTouchListener = ViewHierarchy.getExistingOnTouchListener(view2);
            boolean z2 = (existingOnTouchListener instanceof RCTCodelessLoggingEventListener.AutoLoggingOnTouchListener) && ((RCTCodelessLoggingEventListener.AutoLoggingOnTouchListener) existingOnTouchListener).getSupportCodelessLogging();
            if (this.listenerSet.contains(viewMapKey) || z2) {
                return;
            }
            view2.setOnTouchListener(RCTCodelessLoggingEventListener.getOnTouchListener(eventBinding, view, view2));
            this.listenerSet.add(viewMapKey);
        }

        public static List<MatchedView> findViewByPath(EventBinding eventBinding, View view, List<PathComponent> list, int i2, int i3, String str) {
            String str2 = str + "." + String.valueOf(i3);
            ArrayList arrayList = new ArrayList();
            if (view == null) {
                return arrayList;
            }
            if (i2 >= list.size()) {
                arrayList.add(new MatchedView(view, str2));
            } else {
                PathComponent pathComponent = list.get(i2);
                if (pathComponent.className.equals(CodelessMatcher.PARENT_CLASS_NAME)) {
                    ViewParent parent = view.getParent();
                    if (parent instanceof ViewGroup) {
                        List<View> listFindVisibleChildren = findVisibleChildren((ViewGroup) parent);
                        int size = listFindVisibleChildren.size();
                        for (int i4 = 0; i4 < size; i4++) {
                            arrayList.addAll(findViewByPath(eventBinding, listFindVisibleChildren.get(i4), list, i2 + 1, i4, str2));
                        }
                    }
                    return arrayList;
                }
                if (pathComponent.className.equals(".")) {
                    arrayList.add(new MatchedView(view, str2));
                    return arrayList;
                }
                if (!isTheSameView(view, pathComponent, i3)) {
                    return arrayList;
                }
                if (i2 == list.size() - 1) {
                    arrayList.add(new MatchedView(view, str2));
                }
            }
            if (view instanceof ViewGroup) {
                List<View> listFindVisibleChildren2 = findVisibleChildren((ViewGroup) view);
                int size2 = listFindVisibleChildren2.size();
                for (int i5 = 0; i5 < size2; i5++) {
                    arrayList.addAll(findViewByPath(eventBinding, listFindVisibleChildren2.get(i5), list, i2 + 1, i5, str2));
                }
            }
            return arrayList;
        }

        private static List<View> findVisibleChildren(ViewGroup viewGroup) {
            ArrayList arrayList = new ArrayList();
            int childCount = viewGroup.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = viewGroup.getChildAt(i2);
                if (childAt.getVisibility() == 0) {
                    arrayList.add(childAt);
                }
            }
            return arrayList;
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x003f, code lost:
        
            if (r5.getClass().getSimpleName().equals(r7[r7.length - 1]) == false) goto L14;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private static boolean isTheSameView(android.view.View r5, com.facebook.appevents.codeless.internal.PathComponent r6, int r7) {
            /*
                Method dump skipped, instructions count: 265
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.appevents.codeless.CodelessMatcher.ViewMatcher.isTheSameView(android.view.View, com.facebook.appevents.codeless.internal.PathComponent, int):boolean");
        }

        private void startMatch() {
            if (this.eventBindings == null || this.rootView.get() == null) {
                return;
            }
            for (int i2 = 0; i2 < this.eventBindings.size(); i2++) {
                findView(this.eventBindings.get(i2), this.rootView.get());
            }
        }

        public void findView(EventBinding eventBinding, View view) {
            if (eventBinding == null || view == null) {
                return;
            }
            if (TextUtils.isEmpty(eventBinding.getActivityName()) || eventBinding.getActivityName().equals(this.activityName)) {
                List<PathComponent> viewPath = eventBinding.getViewPath();
                if (viewPath.size() > 25) {
                    return;
                }
                Iterator<MatchedView> it = findViewByPath(eventBinding, view, viewPath, 0, -1, this.activityName).iterator();
                while (it.hasNext()) {
                    attachListener(it.next(), view, eventBinding);
                }
            }
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            startMatch();
        }

        @Override // android.view.ViewTreeObserver.OnScrollChangedListener
        public void onScrollChanged() {
            startMatch();
        }

        @Override // java.lang.Runnable
        public void run() {
            View view;
            FetchedAppSettings appSettingsWithoutQuery = FetchedAppSettingsManager.getAppSettingsWithoutQuery(FacebookSdk.getApplicationId());
            if (appSettingsWithoutQuery == null || !appSettingsWithoutQuery.getCodelessEventsEnabled()) {
                return;
            }
            List<EventBinding> array = EventBinding.parseArray(appSettingsWithoutQuery.getEventBindings());
            this.eventBindings = array;
            if (array == null || (view = this.rootView.get()) == null) {
                return;
            }
            ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(this);
                viewTreeObserver.addOnScrollChangedListener(this);
            }
            startMatch();
        }
    }

    private CodelessMatcher() {
    }

    public static synchronized CodelessMatcher getInstance() {
        try {
            if (codelessMatcher == null) {
                codelessMatcher = new CodelessMatcher();
            }
        } catch (Throwable th) {
            throw th;
        }
        return codelessMatcher;
    }

    public static Bundle getParameters(EventBinding eventBinding, View view, View view2) {
        List<ParameterComponent> viewParameters;
        Bundle bundle = new Bundle();
        if (eventBinding != null && (viewParameters = eventBinding.getViewParameters()) != null) {
            for (ParameterComponent parameterComponent : viewParameters) {
                String str = parameterComponent.value;
                if (str != null && str.length() > 0) {
                    bundle.putString(parameterComponent.name, parameterComponent.value);
                } else if (parameterComponent.path.size() > 0) {
                    Iterator<MatchedView> it = (parameterComponent.pathType.equals(Constants.PATH_TYPE_RELATIVE) ? ViewMatcher.findViewByPath(eventBinding, view2, parameterComponent.path, 0, -1, view2.getClass().getSimpleName()) : ViewMatcher.findViewByPath(eventBinding, view, parameterComponent.path, 0, -1, view.getClass().getSimpleName())).iterator();
                    while (true) {
                        if (it.hasNext()) {
                            MatchedView next = it.next();
                            if (next.getView() != null) {
                                String textOfView = ViewHierarchy.getTextOfView(next.getView());
                                if (textOfView.length() > 0) {
                                    bundle.putString(parameterComponent.name, textOfView);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return bundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void matchViews() {
        for (Activity activity : this.activitiesSet) {
            if (activity != null) {
                this.viewMatchers.add(new ViewMatcher(activity.getWindow().getDecorView().getRootView(), this.uiThreadHandler, this.listenerSet, activity.getClass().getSimpleName()));
            }
        }
    }

    private void startTracking() {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            matchViews();
        } else {
            this.uiThreadHandler.post(new Runnable() { // from class: com.facebook.appevents.codeless.CodelessMatcher.1
                @Override // java.lang.Runnable
                public void run() {
                    CodelessMatcher.this.matchViews();
                }
            });
        }
    }

    public void add(Activity activity) {
        if (InternalSettings.isUnityApp()) {
            return;
        }
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            throw new FacebookException("Can't add activity to CodelessMatcher on non-UI thread");
        }
        this.activitiesSet.add(activity);
        this.listenerSet.clear();
        if (this.activityToListenerMap.containsKey(Integer.valueOf(activity.hashCode()))) {
            this.listenerSet = this.activityToListenerMap.get(Integer.valueOf(activity.hashCode()));
        }
        startTracking();
    }

    public void destroy(Activity activity) {
        this.activityToListenerMap.remove(Integer.valueOf(activity.hashCode()));
    }

    public void remove(Activity activity) {
        if (InternalSettings.isUnityApp()) {
            return;
        }
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            throw new FacebookException("Can't remove activity from CodelessMatcher on non-UI thread");
        }
        this.activitiesSet.remove(activity);
        this.viewMatchers.clear();
        this.activityToListenerMap.put(Integer.valueOf(activity.hashCode()), (HashSet) this.listenerSet.clone());
        this.listenerSet.clear();
    }
}
