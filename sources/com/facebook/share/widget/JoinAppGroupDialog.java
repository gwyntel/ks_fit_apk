package com.facebook.share.widget;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.facebook.FacebookCallback;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.internal.FragmentWrapper;
import com.facebook.share.internal.ResultProcessor;
import com.facebook.share.internal.ShareInternalUtility;
import java.util.ArrayList;
import java.util.List;

@Deprecated
/* loaded from: classes3.dex */
public class JoinAppGroupDialog extends FacebookDialogBase<String, Result> {
    private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.AppGroupJoin.toRequestCode();
    private static final String JOIN_GAME_GROUP_DIALOG = "game_group_join";

    @Deprecated
    public static final class Result {
        private final Bundle data;

        public Bundle getData() {
            return this.data;
        }

        private Result(Bundle bundle) {
            this.data = bundle;
        }
    }

    private class WebHandler extends FacebookDialogBase<String, Result>.ModeHandler {
        private WebHandler() {
            super();
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        public boolean canShow(String str, boolean z2) {
            return true;
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        public AppCall createAppCall(String str) throws PackageManager.NameNotFoundException {
            AppCall appCallCreateBaseAppCall = JoinAppGroupDialog.this.createBaseAppCall();
            Bundle bundle = new Bundle();
            bundle.putString("id", str);
            DialogPresenter.setupAppCallForWebDialog(appCallCreateBaseAppCall, JoinAppGroupDialog.JOIN_GAME_GROUP_DIALOG, bundle);
            return appCallCreateBaseAppCall;
        }
    }

    @Deprecated
    public JoinAppGroupDialog(Activity activity) {
        super(activity, DEFAULT_REQUEST_CODE);
    }

    @Deprecated
    public static boolean canShow() {
        return true;
    }

    @Deprecated
    public static void show(Activity activity, String str) throws PackageManager.NameNotFoundException {
        new JoinAppGroupDialog(activity).show(str);
    }

    @Override // com.facebook.internal.FacebookDialogBase
    protected AppCall createBaseAppCall() {
        return new AppCall(getRequestCode());
    }

    @Override // com.facebook.internal.FacebookDialogBase
    protected List<FacebookDialogBase<String, Result>.ModeHandler> getOrderedModeHandlers() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new WebHandler());
        return arrayList;
    }

    @Override // com.facebook.internal.FacebookDialogBase
    protected void registerCallbackImpl(CallbackManagerImpl callbackManagerImpl, final FacebookCallback<Result> facebookCallback) {
        final ResultProcessor resultProcessor = facebookCallback == null ? null : new ResultProcessor(facebookCallback) { // from class: com.facebook.share.widget.JoinAppGroupDialog.1
            @Override // com.facebook.share.internal.ResultProcessor
            public void onSuccess(AppCall appCall, Bundle bundle) {
                facebookCallback.onSuccess(new Result(bundle));
            }
        };
        callbackManagerImpl.registerCallback(getRequestCode(), new CallbackManagerImpl.Callback() { // from class: com.facebook.share.widget.JoinAppGroupDialog.2
            @Override // com.facebook.internal.CallbackManagerImpl.Callback
            public boolean onActivityResult(int i2, Intent intent) {
                return ShareInternalUtility.handleActivityResult(JoinAppGroupDialog.this.getRequestCode(), i2, intent, resultProcessor);
            }
        });
    }

    @Deprecated
    public JoinAppGroupDialog(Fragment fragment) {
        this(new FragmentWrapper(fragment));
    }

    @Deprecated
    public static void show(Fragment fragment, String str) throws PackageManager.NameNotFoundException {
        show(new FragmentWrapper(fragment), str);
    }

    @Deprecated
    public JoinAppGroupDialog(android.app.Fragment fragment) {
        this(new FragmentWrapper(fragment));
    }

    @Deprecated
    public static void show(android.app.Fragment fragment, String str) throws PackageManager.NameNotFoundException {
        show(new FragmentWrapper(fragment), str);
    }

    private JoinAppGroupDialog(FragmentWrapper fragmentWrapper) {
        super(fragmentWrapper, DEFAULT_REQUEST_CODE);
    }

    private static void show(FragmentWrapper fragmentWrapper, String str) throws PackageManager.NameNotFoundException {
        new JoinAppGroupDialog(fragmentWrapper).show(str);
    }
}
