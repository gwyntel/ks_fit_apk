package com.facebook.appevents.codeless;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.appevents.codeless.internal.EventBinding;
import com.facebook.appevents.codeless.internal.ViewHierarchy;
import com.facebook.appevents.internal.AppEventUtility;
import java.lang.ref.WeakReference;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes3.dex */
public class CodelessLoggingEventListener {
    private static final String TAG = "com.facebook.appevents.codeless.CodelessLoggingEventListener";

    public static class AutoLoggingOnClickListener implements View.OnClickListener {

        @Nullable
        private View.OnClickListener existingOnClickListener;
        private WeakReference<View> hostView;
        private EventBinding mapping;
        private WeakReference<View> rootView;
        private boolean supportCodelessLogging;

        public boolean getSupportCodelessLogging() {
            return this.supportCodelessLogging;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            View.OnClickListener onClickListener = this.existingOnClickListener;
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
            if (this.rootView.get() == null || this.hostView.get() == null) {
                return;
            }
            CodelessLoggingEventListener.logEvent(this.mapping, this.rootView.get(), this.hostView.get());
        }

        private AutoLoggingOnClickListener(EventBinding eventBinding, View view, View view2) {
            this.supportCodelessLogging = false;
            if (eventBinding == null || view == null || view2 == null) {
                return;
            }
            this.existingOnClickListener = ViewHierarchy.getExistingOnClickListener(view2);
            this.mapping = eventBinding;
            this.hostView = new WeakReference<>(view2);
            this.rootView = new WeakReference<>(view);
            this.supportCodelessLogging = true;
        }
    }

    public static class AutoLoggingOnItemClickListener implements AdapterView.OnItemClickListener {

        @Nullable
        private AdapterView.OnItemClickListener existingOnItemClickListener;
        private WeakReference<AdapterView> hostView;
        private EventBinding mapping;
        private WeakReference<View> rootView;
        private boolean supportCodelessLogging;

        public boolean getSupportCodelessLogging() {
            return this.supportCodelessLogging;
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) {
            AdapterView.OnItemClickListener onItemClickListener = this.existingOnItemClickListener;
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(adapterView, view, i2, j2);
            }
            if (this.rootView.get() == null || this.hostView.get() == null) {
                return;
            }
            CodelessLoggingEventListener.logEvent(this.mapping, this.rootView.get(), this.hostView.get());
        }

        private AutoLoggingOnItemClickListener(EventBinding eventBinding, View view, AdapterView adapterView) {
            this.supportCodelessLogging = false;
            if (eventBinding == null || view == null || adapterView == null) {
                return;
            }
            this.existingOnItemClickListener = adapterView.getOnItemClickListener();
            this.mapping = eventBinding;
            this.hostView = new WeakReference<>(adapterView);
            this.rootView = new WeakReference<>(view);
            this.supportCodelessLogging = true;
        }
    }

    public static AutoLoggingOnClickListener getOnClickListener(EventBinding eventBinding, View view, View view2) {
        return new AutoLoggingOnClickListener(eventBinding, view, view2);
    }

    public static AutoLoggingOnItemClickListener getOnItemClickListener(EventBinding eventBinding, View view, AdapterView adapterView) {
        return new AutoLoggingOnItemClickListener(eventBinding, view, adapterView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void logEvent(EventBinding eventBinding, View view, View view2) {
        final String eventName = eventBinding.getEventName();
        final Bundle parameters = CodelessMatcher.getParameters(eventBinding, view, view2);
        if (parameters.containsKey(AppEventsConstants.EVENT_PARAM_VALUE_TO_SUM)) {
            parameters.putDouble(AppEventsConstants.EVENT_PARAM_VALUE_TO_SUM, AppEventUtility.normalizePrice(parameters.getString(AppEventsConstants.EVENT_PARAM_VALUE_TO_SUM)));
        }
        parameters.putString(Constants.IS_CODELESS_EVENT_KEY, "1");
        FacebookSdk.getExecutor().execute(new Runnable() { // from class: com.facebook.appevents.codeless.CodelessLoggingEventListener.1
            @Override // java.lang.Runnable
            public void run() {
                AppEventsLogger.newLogger(FacebookSdk.getApplicationContext()).logEvent(eventName, parameters);
            }
        });
    }
}
