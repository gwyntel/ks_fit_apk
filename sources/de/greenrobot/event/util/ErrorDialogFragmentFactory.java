package de.greenrobot.event.util;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Bundle;
import de.greenrobot.event.util.ErrorDialogFragments;

/* loaded from: classes4.dex */
public abstract class ErrorDialogFragmentFactory<T> {

    /* renamed from: a, reason: collision with root package name */
    protected final ErrorDialogConfig f24994a;

    @TargetApi(11)
    public static class Honeycomb extends ErrorDialogFragmentFactory<Fragment> {
        public Honeycomb(ErrorDialogConfig errorDialogConfig) {
            super(errorDialogConfig);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // de.greenrobot.event.util.ErrorDialogFragmentFactory
        /* renamed from: e, reason: merged with bridge method [inline-methods] */
        public Fragment a(ThrowableFailureEvent throwableFailureEvent, Bundle bundle) {
            ErrorDialogFragments.Honeycomb honeycomb = new ErrorDialogFragments.Honeycomb();
            honeycomb.setArguments(bundle);
            return honeycomb;
        }
    }

    public static class Support extends ErrorDialogFragmentFactory<androidx.fragment.app.Fragment> {
        public Support(ErrorDialogConfig errorDialogConfig) {
            super(errorDialogConfig);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // de.greenrobot.event.util.ErrorDialogFragmentFactory
        /* renamed from: e, reason: merged with bridge method [inline-methods] */
        public androidx.fragment.app.Fragment a(ThrowableFailureEvent throwableFailureEvent, Bundle bundle) {
            ErrorDialogFragments.Support support = new ErrorDialogFragments.Support();
            support.setArguments(bundle);
            return support;
        }
    }

    protected ErrorDialogFragmentFactory(ErrorDialogConfig errorDialogConfig) {
        this.f24994a = errorDialogConfig;
    }

    protected abstract Object a(ThrowableFailureEvent throwableFailureEvent, Bundle bundle);

    protected String b(ThrowableFailureEvent throwableFailureEvent, Bundle bundle) {
        return this.f24994a.f24985a.getString(this.f24994a.getMessageIdForThrowable(throwableFailureEvent.f24999a));
    }

    protected String c(ThrowableFailureEvent throwableFailureEvent, Bundle bundle) {
        ErrorDialogConfig errorDialogConfig = this.f24994a;
        return errorDialogConfig.f24985a.getString(errorDialogConfig.f24986b);
    }

    protected Object d(ThrowableFailureEvent throwableFailureEvent, boolean z2, Bundle bundle) {
        int i2;
        Class cls;
        if (throwableFailureEvent.isSuppressErrorUi()) {
            return null;
        }
        Bundle bundle2 = bundle != null ? (Bundle) bundle.clone() : new Bundle();
        if (!bundle2.containsKey(ErrorDialogManager.KEY_TITLE)) {
            bundle2.putString(ErrorDialogManager.KEY_TITLE, c(throwableFailureEvent, bundle2));
        }
        if (!bundle2.containsKey(ErrorDialogManager.KEY_MESSAGE)) {
            bundle2.putString(ErrorDialogManager.KEY_MESSAGE, b(throwableFailureEvent, bundle2));
        }
        if (!bundle2.containsKey(ErrorDialogManager.KEY_FINISH_AFTER_DIALOG)) {
            bundle2.putBoolean(ErrorDialogManager.KEY_FINISH_AFTER_DIALOG, z2);
        }
        if (!bundle2.containsKey(ErrorDialogManager.KEY_EVENT_TYPE_ON_CLOSE) && (cls = this.f24994a.f24993i) != null) {
            bundle2.putSerializable(ErrorDialogManager.KEY_EVENT_TYPE_ON_CLOSE, cls);
        }
        if (!bundle2.containsKey(ErrorDialogManager.KEY_ICON_ID) && (i2 = this.f24994a.f24992h) != 0) {
            bundle2.putInt(ErrorDialogManager.KEY_ICON_ID, i2);
        }
        return a(throwableFailureEvent, bundle2);
    }
}
