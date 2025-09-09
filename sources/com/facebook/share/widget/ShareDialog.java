package com.facebook.share.widget;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.facebook.AccessToken;
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
import com.facebook.internal.NativeAppCallAttachmentStore;
import com.facebook.internal.Utility;
import com.facebook.share.Sharer;
import com.facebook.share.internal.CameraEffectFeature;
import com.facebook.share.internal.LegacyNativeDialogParameters;
import com.facebook.share.internal.NativeDialogParameters;
import com.facebook.share.internal.OpenGraphActionDialogFeature;
import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.internal.ShareDialogFeature;
import com.facebook.share.internal.ShareFeedContent;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.internal.ShareStoryFeature;
import com.facebook.share.internal.WebDialogParameters;
import com.facebook.share.model.ShareCameraEffectContent;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareStoryContent;
import com.facebook.share.model.ShareVideoContent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* loaded from: classes3.dex */
public final class ShareDialog extends FacebookDialogBase<ShareContent, Sharer.Result> implements Sharer {
    private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.Share.toRequestCode();
    private static final String FEED_DIALOG = "feed";
    private static final String TAG = "ShareDialog";
    private static final String WEB_OG_SHARE_DIALOG = "share_open_graph";
    public static final String WEB_SHARE_DIALOG = "share";
    private boolean isAutomaticMode;
    private boolean shouldFailOnDataError;

    /* renamed from: com.facebook.share.widget.ShareDialog$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$share$widget$ShareDialog$Mode;

        static {
            int[] iArr = new int[Mode.values().length];
            $SwitchMap$com$facebook$share$widget$ShareDialog$Mode = iArr;
            try {
                iArr[Mode.AUTOMATIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$facebook$share$widget$ShareDialog$Mode[Mode.WEB.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$facebook$share$widget$ShareDialog$Mode[Mode.NATIVE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private class CameraEffectHandler extends FacebookDialogBase<ShareContent, Sharer.Result>.ModeHandler {
        private CameraEffectHandler() {
            super();
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        public Object getMode() {
            return Mode.NATIVE;
        }

        /* synthetic */ CameraEffectHandler(ShareDialog shareDialog, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        public boolean canShow(ShareContent shareContent, boolean z2) {
            return (shareContent instanceof ShareCameraEffectContent) && ShareDialog.canShowNative(shareContent.getClass());
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        public AppCall createAppCall(final ShareContent shareContent) throws FacebookException {
            ShareContentValidation.validateForNativeShare(shareContent);
            final AppCall appCallCreateBaseAppCall = ShareDialog.this.createBaseAppCall();
            final boolean shouldFailOnDataError = ShareDialog.this.getShouldFailOnDataError();
            DialogPresenter.setupAppCallForNativeDialog(appCallCreateBaseAppCall, new DialogPresenter.ParameterProvider() { // from class: com.facebook.share.widget.ShareDialog.CameraEffectHandler.1
                @Override // com.facebook.internal.DialogPresenter.ParameterProvider
                public Bundle getLegacyParameters() {
                    return LegacyNativeDialogParameters.create(appCallCreateBaseAppCall.getCallId(), shareContent, shouldFailOnDataError);
                }

                @Override // com.facebook.internal.DialogPresenter.ParameterProvider
                public Bundle getParameters() {
                    return NativeDialogParameters.create(appCallCreateBaseAppCall.getCallId(), shareContent, shouldFailOnDataError);
                }
            }, ShareDialog.getFeature(shareContent.getClass()));
            return appCallCreateBaseAppCall;
        }
    }

    private class FeedHandler extends FacebookDialogBase<ShareContent, Sharer.Result>.ModeHandler {
        private FeedHandler() {
            super();
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        public Object getMode() {
            return Mode.FEED;
        }

        /* synthetic */ FeedHandler(ShareDialog shareDialog, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        public boolean canShow(ShareContent shareContent, boolean z2) {
            return (shareContent instanceof ShareLinkContent) || (shareContent instanceof ShareFeedContent);
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        public AppCall createAppCall(ShareContent shareContent) throws FacebookException, PackageManager.NameNotFoundException {
            Bundle bundleCreateForFeed;
            ShareDialog shareDialog = ShareDialog.this;
            shareDialog.logDialogShare(shareDialog.getActivityContext(), shareContent, Mode.FEED);
            AppCall appCallCreateBaseAppCall = ShareDialog.this.createBaseAppCall();
            if (shareContent instanceof ShareLinkContent) {
                ShareLinkContent shareLinkContent = (ShareLinkContent) shareContent;
                ShareContentValidation.validateForWebShare(shareLinkContent);
                bundleCreateForFeed = WebDialogParameters.createForFeed(shareLinkContent);
            } else {
                bundleCreateForFeed = WebDialogParameters.createForFeed((ShareFeedContent) shareContent);
            }
            DialogPresenter.setupAppCallForWebDialog(appCallCreateBaseAppCall, ShareDialog.FEED_DIALOG, bundleCreateForFeed);
            return appCallCreateBaseAppCall;
        }
    }

    public enum Mode {
        AUTOMATIC,
        NATIVE,
        WEB,
        FEED
    }

    private class NativeHandler extends FacebookDialogBase<ShareContent, Sharer.Result>.ModeHandler {
        private NativeHandler() {
            super();
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        public Object getMode() {
            return Mode.NATIVE;
        }

        /* synthetic */ NativeHandler(ShareDialog shareDialog, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        public boolean canShow(ShareContent shareContent, boolean z2) {
            boolean zCanPresentNativeDialogWithFeature;
            if (shareContent == null || (shareContent instanceof ShareCameraEffectContent) || (shareContent instanceof ShareStoryContent)) {
                return false;
            }
            if (z2) {
                zCanPresentNativeDialogWithFeature = true;
            } else {
                zCanPresentNativeDialogWithFeature = shareContent.getShareHashtag() != null ? DialogPresenter.canPresentNativeDialogWithFeature(ShareDialogFeature.HASHTAG) : true;
                if ((shareContent instanceof ShareLinkContent) && !Utility.isNullOrEmpty(((ShareLinkContent) shareContent).getQuote())) {
                    zCanPresentNativeDialogWithFeature &= DialogPresenter.canPresentNativeDialogWithFeature(ShareDialogFeature.LINK_SHARE_QUOTES);
                }
            }
            return zCanPresentNativeDialogWithFeature && ShareDialog.canShowNative(shareContent.getClass());
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        public AppCall createAppCall(final ShareContent shareContent) throws FacebookException {
            ShareDialog shareDialog = ShareDialog.this;
            shareDialog.logDialogShare(shareDialog.getActivityContext(), shareContent, Mode.NATIVE);
            ShareContentValidation.validateForNativeShare(shareContent);
            final AppCall appCallCreateBaseAppCall = ShareDialog.this.createBaseAppCall();
            final boolean shouldFailOnDataError = ShareDialog.this.getShouldFailOnDataError();
            DialogPresenter.setupAppCallForNativeDialog(appCallCreateBaseAppCall, new DialogPresenter.ParameterProvider() { // from class: com.facebook.share.widget.ShareDialog.NativeHandler.1
                @Override // com.facebook.internal.DialogPresenter.ParameterProvider
                public Bundle getLegacyParameters() {
                    return LegacyNativeDialogParameters.create(appCallCreateBaseAppCall.getCallId(), shareContent, shouldFailOnDataError);
                }

                @Override // com.facebook.internal.DialogPresenter.ParameterProvider
                public Bundle getParameters() {
                    return NativeDialogParameters.create(appCallCreateBaseAppCall.getCallId(), shareContent, shouldFailOnDataError);
                }
            }, ShareDialog.getFeature(shareContent.getClass()));
            return appCallCreateBaseAppCall;
        }
    }

    private class ShareStoryHandler extends FacebookDialogBase<ShareContent, Sharer.Result>.ModeHandler {
        private ShareStoryHandler() {
            super();
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        public Object getMode() {
            return Mode.NATIVE;
        }

        /* synthetic */ ShareStoryHandler(ShareDialog shareDialog, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        public boolean canShow(ShareContent shareContent, boolean z2) {
            return (shareContent instanceof ShareStoryContent) && ShareDialog.canShowNative(shareContent.getClass());
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        public AppCall createAppCall(final ShareContent shareContent) throws FacebookException {
            ShareContentValidation.validateForStoryShare(shareContent);
            final AppCall appCallCreateBaseAppCall = ShareDialog.this.createBaseAppCall();
            final boolean shouldFailOnDataError = ShareDialog.this.getShouldFailOnDataError();
            DialogPresenter.setupAppCallForNativeDialog(appCallCreateBaseAppCall, new DialogPresenter.ParameterProvider() { // from class: com.facebook.share.widget.ShareDialog.ShareStoryHandler.1
                @Override // com.facebook.internal.DialogPresenter.ParameterProvider
                public Bundle getLegacyParameters() {
                    return LegacyNativeDialogParameters.create(appCallCreateBaseAppCall.getCallId(), shareContent, shouldFailOnDataError);
                }

                @Override // com.facebook.internal.DialogPresenter.ParameterProvider
                public Bundle getParameters() {
                    return NativeDialogParameters.create(appCallCreateBaseAppCall.getCallId(), shareContent, shouldFailOnDataError);
                }
            }, ShareDialog.getFeature(shareContent.getClass()));
            return appCallCreateBaseAppCall;
        }
    }

    private class WebShareHandler extends FacebookDialogBase<ShareContent, Sharer.Result>.ModeHandler {
        private WebShareHandler() {
            super();
        }

        private SharePhotoContent createAndMapAttachments(SharePhotoContent sharePhotoContent, UUID uuid) {
            SharePhotoContent.Builder from = new SharePhotoContent.Builder().readFrom(sharePhotoContent);
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (int i2 = 0; i2 < sharePhotoContent.getPhotos().size(); i2++) {
                SharePhoto sharePhotoBuild = sharePhotoContent.getPhotos().get(i2);
                Bitmap bitmap = sharePhotoBuild.getBitmap();
                if (bitmap != null) {
                    NativeAppCallAttachmentStore.Attachment attachmentCreateAttachment = NativeAppCallAttachmentStore.createAttachment(uuid, bitmap);
                    sharePhotoBuild = new SharePhoto.Builder().readFrom(sharePhotoBuild).setImageUrl(Uri.parse(attachmentCreateAttachment.getAttachmentUrl())).setBitmap(null).build();
                    arrayList2.add(attachmentCreateAttachment);
                }
                arrayList.add(sharePhotoBuild);
            }
            from.setPhotos(arrayList);
            NativeAppCallAttachmentStore.addAttachments(arrayList2);
            return from.build();
        }

        private String getActionName(ShareContent shareContent) {
            if ((shareContent instanceof ShareLinkContent) || (shareContent instanceof SharePhotoContent)) {
                return "share";
            }
            if (shareContent instanceof ShareOpenGraphContent) {
                return ShareDialog.WEB_OG_SHARE_DIALOG;
            }
            return null;
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        public Object getMode() {
            return Mode.WEB;
        }

        /* synthetic */ WebShareHandler(ShareDialog shareDialog, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        public boolean canShow(ShareContent shareContent, boolean z2) {
            return shareContent != null && ShareDialog.canShowWebCheck(shareContent);
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        public AppCall createAppCall(ShareContent shareContent) throws FacebookException, PackageManager.NameNotFoundException {
            ShareDialog shareDialog = ShareDialog.this;
            shareDialog.logDialogShare(shareDialog.getActivityContext(), shareContent, Mode.WEB);
            AppCall appCallCreateBaseAppCall = ShareDialog.this.createBaseAppCall();
            ShareContentValidation.validateForWebShare(shareContent);
            DialogPresenter.setupAppCallForWebDialog(appCallCreateBaseAppCall, getActionName(shareContent), shareContent instanceof ShareLinkContent ? WebDialogParameters.create((ShareLinkContent) shareContent) : shareContent instanceof SharePhotoContent ? WebDialogParameters.create(createAndMapAttachments((SharePhotoContent) shareContent, appCallCreateBaseAppCall.getCallId())) : WebDialogParameters.create((ShareOpenGraphContent) shareContent));
            return appCallCreateBaseAppCall;
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public ShareDialog(Activity activity) {
        int i2 = DEFAULT_REQUEST_CODE;
        super(activity, i2);
        this.shouldFailOnDataError = false;
        this.isAutomaticMode = true;
        ShareInternalUtility.registerStaticShareCallback(i2);
    }

    public static boolean canShow(Class<? extends ShareContent> cls) {
        return canShowWebTypeCheck(cls) || canShowNative(cls);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean canShowNative(Class<? extends ShareContent> cls) {
        DialogFeature feature = getFeature(cls);
        return feature != null && DialogPresenter.canPresentNativeDialogWithFeature(feature);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean canShowWebCheck(ShareContent shareContent) {
        if (!canShowWebTypeCheck(shareContent.getClass())) {
            return false;
        }
        if (!(shareContent instanceof ShareOpenGraphContent)) {
            return true;
        }
        try {
            ShareInternalUtility.toJSONObjectForWeb((ShareOpenGraphContent) shareContent);
            return true;
        } catch (Exception e2) {
            Utility.logd(TAG, "canShow returned false because the content of the Opem Graph object can't be shared via the web dialog", e2);
            return false;
        }
    }

    private static boolean canShowWebTypeCheck(Class<? extends ShareContent> cls) {
        return ShareLinkContent.class.isAssignableFrom(cls) || ShareOpenGraphContent.class.isAssignableFrom(cls) || (SharePhotoContent.class.isAssignableFrom(cls) && AccessToken.isCurrentAccessTokenActive());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static DialogFeature getFeature(Class<? extends ShareContent> cls) {
        if (ShareLinkContent.class.isAssignableFrom(cls)) {
            return ShareDialogFeature.SHARE_DIALOG;
        }
        if (SharePhotoContent.class.isAssignableFrom(cls)) {
            return ShareDialogFeature.PHOTOS;
        }
        if (ShareVideoContent.class.isAssignableFrom(cls)) {
            return ShareDialogFeature.VIDEO;
        }
        if (ShareOpenGraphContent.class.isAssignableFrom(cls)) {
            return OpenGraphActionDialogFeature.OG_ACTION_DIALOG;
        }
        if (ShareMediaContent.class.isAssignableFrom(cls)) {
            return ShareDialogFeature.MULTIMEDIA;
        }
        if (ShareCameraEffectContent.class.isAssignableFrom(cls)) {
            return CameraEffectFeature.SHARE_CAMERA_EFFECT;
        }
        if (ShareStoryContent.class.isAssignableFrom(cls)) {
            return ShareStoryFeature.SHARE_STORY_ASSET;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logDialogShare(Context context, ShareContent shareContent, Mode mode) {
        if (this.isAutomaticMode) {
            mode = Mode.AUTOMATIC;
        }
        int i2 = AnonymousClass1.$SwitchMap$com$facebook$share$widget$ShareDialog$Mode[mode.ordinal()];
        String str = "unknown";
        String str2 = i2 != 1 ? i2 != 2 ? i2 != 3 ? "unknown" : AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_NATIVE : AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_WEB : AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_AUTOMATIC;
        DialogFeature feature = getFeature(shareContent.getClass());
        if (feature == ShareDialogFeature.SHARE_DIALOG) {
            str = "status";
        } else if (feature == ShareDialogFeature.PHOTOS) {
            str = AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_PHOTO;
        } else if (feature == ShareDialogFeature.VIDEO) {
            str = "video";
        } else if (feature == OpenGraphActionDialogFeature.OG_ACTION_DIALOG) {
            str = "open_graph";
        }
        InternalAppEventsLogger internalAppEventsLogger = new InternalAppEventsLogger(context);
        Bundle bundle = new Bundle();
        bundle.putString("fb_share_dialog_show", str2);
        bundle.putString(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_TYPE, str);
        internalAppEventsLogger.logEventImplicitly("fb_share_dialog_show", bundle);
    }

    public static void show(Activity activity, ShareContent shareContent) throws PackageManager.NameNotFoundException {
        new ShareDialog(activity).show(shareContent);
    }

    @Override // com.facebook.internal.FacebookDialogBase
    protected AppCall createBaseAppCall() {
        return new AppCall(getRequestCode());
    }

    @Override // com.facebook.internal.FacebookDialogBase
    protected List<FacebookDialogBase<ShareContent, Sharer.Result>.ModeHandler> getOrderedModeHandlers() {
        ArrayList arrayList = new ArrayList();
        AnonymousClass1 anonymousClass1 = null;
        arrayList.add(new NativeHandler(this, anonymousClass1));
        arrayList.add(new FeedHandler(this, anonymousClass1));
        arrayList.add(new WebShareHandler(this, anonymousClass1));
        arrayList.add(new CameraEffectHandler(this, anonymousClass1));
        arrayList.add(new ShareStoryHandler(this, anonymousClass1));
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

    public boolean canShow(ShareContent shareContent, Mode mode) {
        Object obj = mode;
        if (mode == Mode.AUTOMATIC) {
            obj = FacebookDialogBase.BASE_AUTOMATIC_MODE;
        }
        return canShowImpl(shareContent, obj);
    }

    public static void show(android.app.Fragment fragment, ShareContent shareContent) throws PackageManager.NameNotFoundException {
        show(new FragmentWrapper(fragment), shareContent);
    }

    private static void show(FragmentWrapper fragmentWrapper, ShareContent shareContent) throws PackageManager.NameNotFoundException {
        new ShareDialog(fragmentWrapper).show(shareContent);
    }

    public ShareDialog(Fragment fragment) {
        this(new FragmentWrapper(fragment));
    }

    public void show(ShareContent shareContent, Mode mode) throws PackageManager.NameNotFoundException {
        boolean z2 = mode == Mode.AUTOMATIC;
        this.isAutomaticMode = z2;
        Object obj = mode;
        if (z2) {
            obj = FacebookDialogBase.BASE_AUTOMATIC_MODE;
        }
        showImpl(shareContent, obj);
    }

    public ShareDialog(android.app.Fragment fragment) {
        this(new FragmentWrapper(fragment));
    }

    /* JADX WARN: Illegal instructions before constructor call */
    private ShareDialog(FragmentWrapper fragmentWrapper) {
        int i2 = DEFAULT_REQUEST_CODE;
        super(fragmentWrapper, i2);
        this.shouldFailOnDataError = false;
        this.isAutomaticMode = true;
        ShareInternalUtility.registerStaticShareCallback(i2);
    }

    ShareDialog(Activity activity, int i2) {
        super(activity, i2);
        this.shouldFailOnDataError = false;
        this.isAutomaticMode = true;
        ShareInternalUtility.registerStaticShareCallback(i2);
    }

    ShareDialog(Fragment fragment, int i2) {
        this(new FragmentWrapper(fragment), i2);
    }

    ShareDialog(android.app.Fragment fragment, int i2) {
        this(new FragmentWrapper(fragment), i2);
    }

    private ShareDialog(FragmentWrapper fragmentWrapper, int i2) {
        super(fragmentWrapper, i2);
        this.shouldFailOnDataError = false;
        this.isAutomaticMode = true;
        ShareInternalUtility.registerStaticShareCallback(i2);
    }
}
