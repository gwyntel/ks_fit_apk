package com.facebook.share.widget;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.appevents.InternalAppEventsLogger;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.DialogFeature;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.internal.FragmentWrapper;
import com.facebook.share.Sharer;
import com.facebook.share.internal.LegacyNativeDialogParameters;
import com.facebook.share.internal.MessageDialogFeature;
import com.facebook.share.internal.NativeDialogParameters;
import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMessengerGenericTemplateContent;
import com.facebook.share.model.ShareMessengerMediaTemplateContent;
import com.facebook.share.model.ShareMessengerOpenGraphMusicTemplateContent;
import java.util.ArrayList;
import java.util.List;

@Deprecated
/* loaded from: classes3.dex */
public final class MessageDialog extends FacebookDialogBase<ShareContent, Sharer.Result> implements Sharer {
    private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.Message.toRequestCode();
    private boolean shouldFailOnDataError;

    private class NativeHandler extends FacebookDialogBase<ShareContent, Sharer.Result>.ModeHandler {
        private NativeHandler() {
            super();
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        public boolean canShow(ShareContent shareContent, boolean z2) {
            return shareContent != null && MessageDialog.canShow((Class<? extends ShareContent>) shareContent.getClass());
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        public AppCall createAppCall(final ShareContent shareContent) throws FacebookException {
            ShareContentValidation.validateForMessage(shareContent);
            final AppCall appCallCreateBaseAppCall = MessageDialog.this.createBaseAppCall();
            final boolean shouldFailOnDataError = MessageDialog.this.getShouldFailOnDataError();
            MessageDialog.logDialogShare(MessageDialog.this.getActivityContext(), shareContent, appCallCreateBaseAppCall);
            DialogPresenter.setupAppCallForNativeDialog(appCallCreateBaseAppCall, new DialogPresenter.ParameterProvider() { // from class: com.facebook.share.widget.MessageDialog.NativeHandler.1
                @Override // com.facebook.internal.DialogPresenter.ParameterProvider
                public Bundle getLegacyParameters() {
                    return LegacyNativeDialogParameters.create(appCallCreateBaseAppCall.getCallId(), shareContent, shouldFailOnDataError);
                }

                @Override // com.facebook.internal.DialogPresenter.ParameterProvider
                public Bundle getParameters() {
                    return NativeDialogParameters.create(appCallCreateBaseAppCall.getCallId(), shareContent, shouldFailOnDataError);
                }
            }, MessageDialog.getFeature(shareContent.getClass()));
            return appCallCreateBaseAppCall;
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public MessageDialog(Activity activity) {
        int i2 = DEFAULT_REQUEST_CODE;
        super(activity, i2);
        this.shouldFailOnDataError = false;
        ShareInternalUtility.registerStaticShareCallback(i2);
    }

    public static boolean canShow(Class<? extends ShareContent> cls) {
        DialogFeature feature = getFeature(cls);
        return feature != null && DialogPresenter.canPresentNativeDialogWithFeature(feature);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static DialogFeature getFeature(Class<? extends ShareContent> cls) {
        if (ShareLinkContent.class.isAssignableFrom(cls)) {
            return MessageDialogFeature.MESSAGE_DIALOG;
        }
        if (ShareMessengerGenericTemplateContent.class.isAssignableFrom(cls)) {
            return MessageDialogFeature.MESSENGER_GENERIC_TEMPLATE;
        }
        if (ShareMessengerOpenGraphMusicTemplateContent.class.isAssignableFrom(cls)) {
            return MessageDialogFeature.MESSENGER_OPEN_GRAPH_MUSIC_TEMPLATE;
        }
        if (ShareMessengerMediaTemplateContent.class.isAssignableFrom(cls)) {
            return MessageDialogFeature.MESSENGER_MEDIA_TEMPLATE;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void logDialogShare(Context context, ShareContent shareContent, AppCall appCall) {
        DialogFeature feature = getFeature(shareContent.getClass());
        String str = feature == MessageDialogFeature.MESSAGE_DIALOG ? "status" : feature == MessageDialogFeature.MESSENGER_GENERIC_TEMPLATE ? AnalyticsEvents.PARAMETER_SHARE_MESSENGER_GENERIC_TEMPLATE : feature == MessageDialogFeature.MESSENGER_MEDIA_TEMPLATE ? AnalyticsEvents.PARAMETER_SHARE_MESSENGER_MEDIA_TEMPLATE : feature == MessageDialogFeature.MESSENGER_OPEN_GRAPH_MUSIC_TEMPLATE ? AnalyticsEvents.PARAMETER_SHARE_MESSENGER_OPEN_GRAPH_MUSIC_TEMPLATE : "unknown";
        InternalAppEventsLogger internalAppEventsLogger = new InternalAppEventsLogger(context);
        Bundle bundle = new Bundle();
        bundle.putString(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_TYPE, str);
        bundle.putString(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_UUID, appCall.getCallId().toString());
        bundle.putString(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_PAGE_ID, shareContent.getPageId());
        internalAppEventsLogger.logEventImplicitly(AnalyticsEvents.EVENT_SHARE_MESSENGER_DIALOG_SHOW, bundle);
    }

    public static void show(Activity activity, ShareContent shareContent) throws PackageManager.NameNotFoundException {
        new MessageDialog(activity).show(shareContent);
    }

    @Override // com.facebook.internal.FacebookDialogBase
    protected AppCall createBaseAppCall() {
        return new AppCall(getRequestCode());
    }

    @Override // com.facebook.internal.FacebookDialogBase
    protected List<FacebookDialogBase<ShareContent, Sharer.Result>.ModeHandler> getOrderedModeHandlers() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new NativeHandler());
        return arrayList;
    }

    @Override // com.facebook.share.Sharer
    public boolean getShouldFailOnDataError() {
        return this.shouldFailOnDataError;
    }

    @Override // com.facebook.internal.FacebookDialogBase
    protected void registerCallbackImpl(CallbackManagerImpl callbackManagerImpl, FacebookCallback<Sharer.Result> facebookCallback) {
        ShareInternalUtility.registerSharerCallback(getRequestCode(), callbackManagerImpl, facebookCallback);
    }

    @Override // com.facebook.share.Sharer
    public void setShouldFailOnDataError(boolean z2) {
        this.shouldFailOnDataError = z2;
    }

    public static void show(Fragment fragment, ShareContent shareContent) throws PackageManager.NameNotFoundException {
        show(new FragmentWrapper(fragment), shareContent);
    }

    public static void show(android.app.Fragment fragment, ShareContent shareContent) throws PackageManager.NameNotFoundException {
        show(new FragmentWrapper(fragment), shareContent);
    }

    public MessageDialog(Fragment fragment) {
        this(new FragmentWrapper(fragment));
    }

    private static void show(FragmentWrapper fragmentWrapper, ShareContent shareContent) throws PackageManager.NameNotFoundException {
        new MessageDialog(fragmentWrapper).show(shareContent);
    }

    public MessageDialog(android.app.Fragment fragment) {
        this(new FragmentWrapper(fragment));
    }

    /* JADX WARN: Illegal instructions before constructor call */
    private MessageDialog(FragmentWrapper fragmentWrapper) {
        int i2 = DEFAULT_REQUEST_CODE;
        super(fragmentWrapper, i2);
        this.shouldFailOnDataError = false;
        ShareInternalUtility.registerStaticShareCallback(i2);
    }

    MessageDialog(Activity activity, int i2) {
        super(activity, i2);
        this.shouldFailOnDataError = false;
        ShareInternalUtility.registerStaticShareCallback(i2);
    }

    MessageDialog(Fragment fragment, int i2) {
        this(new FragmentWrapper(fragment), i2);
    }

    MessageDialog(android.app.Fragment fragment, int i2) {
        this(new FragmentWrapper(fragment), i2);
    }

    private MessageDialog(FragmentWrapper fragmentWrapper, int i2) {
        super(fragmentWrapper, i2);
        this.shouldFailOnDataError = false;
        ShareInternalUtility.registerStaticShareCallback(i2);
    }
}
