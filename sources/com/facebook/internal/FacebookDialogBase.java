package com.facebook.internal;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookDialog;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class FacebookDialogBase<CONTENT, RESULT> implements FacebookDialog<CONTENT, RESULT> {
    protected static final Object BASE_AUTOMATIC_MODE = new Object();
    private static final String TAG = "FacebookDialog";
    private final Activity activity;
    private final FragmentWrapper fragmentWrapper;
    private List<FacebookDialogBase<CONTENT, RESULT>.ModeHandler> modeHandlers;
    private int requestCode;

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract class ModeHandler {
        protected ModeHandler() {
        }

        public abstract boolean canShow(CONTENT content, boolean z2);

        public abstract AppCall createAppCall(CONTENT content);

        public Object getMode() {
            return FacebookDialogBase.BASE_AUTOMATIC_MODE;
        }
    }

    protected FacebookDialogBase(Activity activity, int i2) {
        Validate.notNull(activity, PushConstants.INTENT_ACTIVITY_NAME);
        this.activity = activity;
        this.fragmentWrapper = null;
        this.requestCode = i2;
    }

    private List<FacebookDialogBase<CONTENT, RESULT>.ModeHandler> cachedModeHandlers() {
        if (this.modeHandlers == null) {
            this.modeHandlers = getOrderedModeHandlers();
        }
        return this.modeHandlers;
    }

    private AppCall createAppCallForMode(CONTENT content, Object obj) throws PackageManager.NameNotFoundException {
        AppCall appCallCreateAppCall;
        boolean z2 = obj == BASE_AUTOMATIC_MODE;
        Iterator<FacebookDialogBase<CONTENT, RESULT>.ModeHandler> it = cachedModeHandlers().iterator();
        while (true) {
            if (!it.hasNext()) {
                appCallCreateAppCall = null;
                break;
            }
            FacebookDialogBase<CONTENT, RESULT>.ModeHandler next = it.next();
            if (z2 || Utility.areObjectsEqual(next.getMode(), obj)) {
                if (next.canShow(content, true)) {
                    try {
                        appCallCreateAppCall = next.createAppCall(content);
                        break;
                    } catch (FacebookException e2) {
                        AppCall appCallCreateBaseAppCall = createBaseAppCall();
                        DialogPresenter.setupAppCallForValidationError(appCallCreateBaseAppCall, e2);
                        appCallCreateAppCall = appCallCreateBaseAppCall;
                    }
                }
            }
        }
        if (appCallCreateAppCall != null) {
            return appCallCreateAppCall;
        }
        AppCall appCallCreateBaseAppCall2 = createBaseAppCall();
        DialogPresenter.setupAppCallForCannotShowError(appCallCreateBaseAppCall2);
        return appCallCreateBaseAppCall2;
    }

    @Override // com.facebook.FacebookDialog
    public boolean canShow(CONTENT content) {
        return canShowImpl(content, BASE_AUTOMATIC_MODE);
    }

    protected boolean canShowImpl(CONTENT content, Object obj) {
        boolean z2 = obj == BASE_AUTOMATIC_MODE;
        for (FacebookDialogBase<CONTENT, RESULT>.ModeHandler modeHandler : cachedModeHandlers()) {
            if (z2 || Utility.areObjectsEqual(modeHandler.getMode(), obj)) {
                if (modeHandler.canShow(content, false)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected abstract AppCall createBaseAppCall();

    protected Activity getActivityContext() {
        Activity activity = this.activity;
        if (activity != null) {
            return activity;
        }
        FragmentWrapper fragmentWrapper = this.fragmentWrapper;
        if (fragmentWrapper != null) {
            return fragmentWrapper.getActivity();
        }
        return null;
    }

    protected abstract List<FacebookDialogBase<CONTENT, RESULT>.ModeHandler> getOrderedModeHandlers();

    public int getRequestCode() {
        return this.requestCode;
    }

    @Override // com.facebook.FacebookDialog
    public final void registerCallback(CallbackManager callbackManager, FacebookCallback<RESULT> facebookCallback) {
        if (!(callbackManager instanceof CallbackManagerImpl)) {
            throw new FacebookException("Unexpected CallbackManager, please use the provided Factory.");
        }
        registerCallbackImpl((CallbackManagerImpl) callbackManager, facebookCallback);
    }

    protected abstract void registerCallbackImpl(CallbackManagerImpl callbackManagerImpl, FacebookCallback<RESULT> facebookCallback);

    protected void setRequestCode(int i2) {
        if (!FacebookSdk.isFacebookRequestCode(i2)) {
            this.requestCode = i2;
            return;
        }
        throw new IllegalArgumentException("Request code " + i2 + " cannot be within the range reserved by the Facebook SDK.");
    }

    @Override // com.facebook.FacebookDialog
    public void show(CONTENT content) throws PackageManager.NameNotFoundException {
        showImpl(content, BASE_AUTOMATIC_MODE);
    }

    protected void showImpl(CONTENT content, Object obj) throws PackageManager.NameNotFoundException {
        AppCall appCallCreateAppCallForMode = createAppCallForMode(content, obj);
        if (appCallCreateAppCallForMode == null) {
            Log.e(TAG, "No code path should ever result in a null appCall");
            if (FacebookSdk.isDebugEnabled()) {
                throw new IllegalStateException("No code path should ever result in a null appCall");
            }
        } else {
            FragmentWrapper fragmentWrapper = this.fragmentWrapper;
            if (fragmentWrapper != null) {
                DialogPresenter.present(appCallCreateAppCallForMode, fragmentWrapper);
            } else {
                DialogPresenter.present(appCallCreateAppCallForMode, this.activity);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void startActivityForResult(android.content.Intent r3, int r4) {
        /*
            r2 = this;
            android.app.Activity r0 = r2.activity
            if (r0 == 0) goto L8
            r0.startActivityForResult(r3, r4)
            goto L2f
        L8:
            com.facebook.internal.FragmentWrapper r0 = r2.fragmentWrapper
            java.lang.String r1 = "Failed to find Activity or Fragment to startActivityForResult "
            if (r0 == 0) goto L30
            android.app.Fragment r0 = r0.getNativeFragment()
            if (r0 == 0) goto L1e
            com.facebook.internal.FragmentWrapper r0 = r2.fragmentWrapper
            android.app.Fragment r0 = r0.getNativeFragment()
            r0.startActivityForResult(r3, r4)
            goto L2f
        L1e:
            com.facebook.internal.FragmentWrapper r0 = r2.fragmentWrapper
            androidx.fragment.app.Fragment r0 = r0.getSupportFragment()
            if (r0 == 0) goto L30
            com.facebook.internal.FragmentWrapper r0 = r2.fragmentWrapper
            androidx.fragment.app.Fragment r0 = r0.getSupportFragment()
            r0.startActivityForResult(r3, r4)
        L2f:
            r1 = 0
        L30:
            if (r1 == 0) goto L40
            com.facebook.LoggingBehavior r3 = com.facebook.LoggingBehavior.DEVELOPER_ERRORS
            java.lang.Class r4 = r2.getClass()
            java.lang.String r4 = r4.getName()
            r0 = 6
            com.facebook.internal.Logger.log(r3, r0, r4, r1)
        L40:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.FacebookDialogBase.startActivityForResult(android.content.Intent, int):void");
    }

    @Override // com.facebook.FacebookDialog
    public final void registerCallback(CallbackManager callbackManager, FacebookCallback<RESULT> facebookCallback, int i2) {
        setRequestCode(i2);
        registerCallback(callbackManager, facebookCallback);
    }

    protected FacebookDialogBase(FragmentWrapper fragmentWrapper, int i2) {
        Validate.notNull(fragmentWrapper, "fragmentWrapper");
        this.fragmentWrapper = fragmentWrapper;
        this.activity = null;
        this.requestCode = i2;
        if (fragmentWrapper.getActivity() == null) {
            throw new IllegalArgumentException("Cannot use a fragment that is not attached to an activity");
        }
    }
}
