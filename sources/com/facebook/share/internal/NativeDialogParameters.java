package com.facebook.share.internal;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.facebook.FacebookException;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.share.model.ShareCameraEffectContent;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.ShareMessengerGenericTemplateContent;
import com.facebook.share.model.ShareMessengerMediaTemplateContent;
import com.facebook.share.model.ShareMessengerOpenGraphMusicTemplateContent;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareStoryContent;
import com.facebook.share.model.ShareVideoContent;
import com.huawei.hms.api.FailedBinderCallBack;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class NativeDialogParameters {
    public static Bundle create(UUID uuid, ShareContent shareContent, boolean z2) {
        Validate.notNull(shareContent, "shareContent");
        Validate.notNull(uuid, FailedBinderCallBack.CALLER_ID);
        if (shareContent instanceof ShareLinkContent) {
            return create((ShareLinkContent) shareContent, z2);
        }
        if (shareContent instanceof SharePhotoContent) {
            SharePhotoContent sharePhotoContent = (SharePhotoContent) shareContent;
            return create(sharePhotoContent, ShareInternalUtility.getPhotoUrls(sharePhotoContent, uuid), z2);
        }
        if (shareContent instanceof ShareVideoContent) {
            ShareVideoContent shareVideoContent = (ShareVideoContent) shareContent;
            return create(shareVideoContent, ShareInternalUtility.getVideoUrl(shareVideoContent, uuid), z2);
        }
        if (shareContent instanceof ShareOpenGraphContent) {
            ShareOpenGraphContent shareOpenGraphContent = (ShareOpenGraphContent) shareContent;
            try {
                return create(shareOpenGraphContent, ShareInternalUtility.removeNamespacesFromOGJsonObject(ShareInternalUtility.toJSONObjectForCall(uuid, shareOpenGraphContent), false), z2);
            } catch (JSONException e2) {
                throw new FacebookException("Unable to create a JSON Object from the provided ShareOpenGraphContent: " + e2.getMessage());
            }
        }
        if (shareContent instanceof ShareMediaContent) {
            ShareMediaContent shareMediaContent = (ShareMediaContent) shareContent;
            return create(shareMediaContent, ShareInternalUtility.getMediaInfos(shareMediaContent, uuid), z2);
        }
        if (shareContent instanceof ShareCameraEffectContent) {
            ShareCameraEffectContent shareCameraEffectContent = (ShareCameraEffectContent) shareContent;
            return create(shareCameraEffectContent, ShareInternalUtility.getTextureUrlBundle(shareCameraEffectContent, uuid), z2);
        }
        if (shareContent instanceof ShareMessengerGenericTemplateContent) {
            return create((ShareMessengerGenericTemplateContent) shareContent, z2);
        }
        if (shareContent instanceof ShareMessengerOpenGraphMusicTemplateContent) {
            return create((ShareMessengerOpenGraphMusicTemplateContent) shareContent, z2);
        }
        if (shareContent instanceof ShareMessengerMediaTemplateContent) {
            return create((ShareMessengerMediaTemplateContent) shareContent, z2);
        }
        if (!(shareContent instanceof ShareStoryContent)) {
            return null;
        }
        ShareStoryContent shareStoryContent = (ShareStoryContent) shareContent;
        return create(shareStoryContent, ShareInternalUtility.getBackgroundAssetMediaInfo(shareStoryContent, uuid), ShareInternalUtility.getStickerUrl(shareStoryContent, uuid), z2);
    }

    private static Bundle createBaseParameters(ShareContent shareContent, boolean z2) {
        Bundle bundle = new Bundle();
        Utility.putUri(bundle, ShareConstants.CONTENT_URL, shareContent.getContentUrl());
        Utility.putNonEmptyString(bundle, ShareConstants.PLACE_ID, shareContent.getPlaceId());
        Utility.putNonEmptyString(bundle, ShareConstants.PAGE_ID, shareContent.getPageId());
        Utility.putNonEmptyString(bundle, ShareConstants.REF, shareContent.getRef());
        bundle.putBoolean(ShareConstants.DATA_FAILURES_FATAL, z2);
        List<String> peopleIds = shareContent.getPeopleIds();
        if (!Utility.isNullOrEmpty(peopleIds)) {
            bundle.putStringArrayList(ShareConstants.PEOPLE_IDS, new ArrayList<>(peopleIds));
        }
        ShareHashtag shareHashtag = shareContent.getShareHashtag();
        if (shareHashtag != null) {
            Utility.putNonEmptyString(bundle, ShareConstants.HASHTAG, shareHashtag.getHashtag());
        }
        return bundle;
    }

    private static Bundle create(ShareCameraEffectContent shareCameraEffectContent, Bundle bundle, boolean z2) {
        Bundle bundleCreateBaseParameters = createBaseParameters(shareCameraEffectContent, z2);
        Utility.putNonEmptyString(bundleCreateBaseParameters, ShareConstants.EFFECT_ID, shareCameraEffectContent.getEffectId());
        if (bundle != null) {
            bundleCreateBaseParameters.putBundle(ShareConstants.EFFECT_TEXTURES, bundle);
        }
        try {
            JSONObject jSONObjectConvertToJSON = CameraEffectJSONUtility.convertToJSON(shareCameraEffectContent.getArguments());
            if (jSONObjectConvertToJSON != null) {
                Utility.putNonEmptyString(bundleCreateBaseParameters, ShareConstants.EFFECT_ARGS, jSONObjectConvertToJSON.toString());
            }
            return bundleCreateBaseParameters;
        } catch (JSONException e2) {
            throw new FacebookException("Unable to create a JSON Object from the provided CameraEffectArguments: " + e2.getMessage());
        }
    }

    private static Bundle create(ShareLinkContent shareLinkContent, boolean z2) {
        Bundle bundleCreateBaseParameters = createBaseParameters(shareLinkContent, z2);
        Utility.putNonEmptyString(bundleCreateBaseParameters, ShareConstants.TITLE, shareLinkContent.getContentTitle());
        Utility.putNonEmptyString(bundleCreateBaseParameters, ShareConstants.DESCRIPTION, shareLinkContent.getContentDescription());
        Utility.putUri(bundleCreateBaseParameters, ShareConstants.IMAGE_URL, shareLinkContent.getImageUrl());
        Utility.putNonEmptyString(bundleCreateBaseParameters, ShareConstants.QUOTE, shareLinkContent.getQuote());
        Utility.putUri(bundleCreateBaseParameters, ShareConstants.MESSENGER_URL, shareLinkContent.getContentUrl());
        Utility.putUri(bundleCreateBaseParameters, ShareConstants.TARGET_DISPLAY, shareLinkContent.getContentUrl());
        return bundleCreateBaseParameters;
    }

    private static Bundle create(SharePhotoContent sharePhotoContent, List<String> list, boolean z2) {
        Bundle bundleCreateBaseParameters = createBaseParameters(sharePhotoContent, z2);
        bundleCreateBaseParameters.putStringArrayList(ShareConstants.PHOTOS, new ArrayList<>(list));
        return bundleCreateBaseParameters;
    }

    private static Bundle create(ShareVideoContent shareVideoContent, String str, boolean z2) {
        Bundle bundleCreateBaseParameters = createBaseParameters(shareVideoContent, z2);
        Utility.putNonEmptyString(bundleCreateBaseParameters, ShareConstants.TITLE, shareVideoContent.getContentTitle());
        Utility.putNonEmptyString(bundleCreateBaseParameters, ShareConstants.DESCRIPTION, shareVideoContent.getContentDescription());
        Utility.putNonEmptyString(bundleCreateBaseParameters, ShareConstants.VIDEO_URL, str);
        return bundleCreateBaseParameters;
    }

    private static Bundle create(ShareMediaContent shareMediaContent, List<Bundle> list, boolean z2) {
        Bundle bundleCreateBaseParameters = createBaseParameters(shareMediaContent, z2);
        bundleCreateBaseParameters.putParcelableArrayList(ShareConstants.MEDIA, new ArrayList<>(list));
        return bundleCreateBaseParameters;
    }

    private static Bundle create(ShareOpenGraphContent shareOpenGraphContent, JSONObject jSONObject, boolean z2) {
        Bundle bundleCreateBaseParameters = createBaseParameters(shareOpenGraphContent, z2);
        Utility.putNonEmptyString(bundleCreateBaseParameters, ShareConstants.PREVIEW_PROPERTY_NAME, (String) ShareInternalUtility.getFieldNameAndNamespaceFromFullName(shareOpenGraphContent.getPreviewPropertyName()).second);
        Utility.putNonEmptyString(bundleCreateBaseParameters, ShareConstants.ACTION_TYPE, shareOpenGraphContent.getAction().getActionType());
        Utility.putNonEmptyString(bundleCreateBaseParameters, ShareConstants.ACTION, jSONObject.toString());
        return bundleCreateBaseParameters;
    }

    private static Bundle create(ShareMessengerGenericTemplateContent shareMessengerGenericTemplateContent, boolean z2) {
        Bundle bundleCreateBaseParameters = createBaseParameters(shareMessengerGenericTemplateContent, z2);
        try {
            MessengerShareContentUtility.addGenericTemplateContent(bundleCreateBaseParameters, shareMessengerGenericTemplateContent);
            return bundleCreateBaseParameters;
        } catch (JSONException e2) {
            throw new FacebookException("Unable to create a JSON Object from the provided ShareMessengerGenericTemplateContent: " + e2.getMessage());
        }
    }

    private static Bundle create(ShareMessengerOpenGraphMusicTemplateContent shareMessengerOpenGraphMusicTemplateContent, boolean z2) {
        Bundle bundleCreateBaseParameters = createBaseParameters(shareMessengerOpenGraphMusicTemplateContent, z2);
        try {
            MessengerShareContentUtility.addOpenGraphMusicTemplateContent(bundleCreateBaseParameters, shareMessengerOpenGraphMusicTemplateContent);
            return bundleCreateBaseParameters;
        } catch (JSONException e2) {
            throw new FacebookException("Unable to create a JSON Object from the provided ShareMessengerOpenGraphMusicTemplateContent: " + e2.getMessage());
        }
    }

    private static Bundle create(ShareMessengerMediaTemplateContent shareMessengerMediaTemplateContent, boolean z2) {
        Bundle bundleCreateBaseParameters = createBaseParameters(shareMessengerMediaTemplateContent, z2);
        try {
            MessengerShareContentUtility.addMediaTemplateContent(bundleCreateBaseParameters, shareMessengerMediaTemplateContent);
            return bundleCreateBaseParameters;
        } catch (JSONException e2) {
            throw new FacebookException("Unable to create a JSON Object from the provided ShareMessengerMediaTemplateContent: " + e2.getMessage());
        }
    }

    private static Bundle create(ShareStoryContent shareStoryContent, @Nullable Bundle bundle, @Nullable Bundle bundle2, boolean z2) {
        Bundle bundleCreateBaseParameters = createBaseParameters(shareStoryContent, z2);
        if (bundle != null) {
            bundleCreateBaseParameters.putParcelable(ShareConstants.STORY_BG_ASSET, bundle);
        }
        if (bundle2 != null) {
            bundleCreateBaseParameters.putParcelable(ShareConstants.STORY_INTERACTIVE_ASSET_URI, bundle2);
        }
        List<String> backgroundColorList = shareStoryContent.getBackgroundColorList();
        if (!Utility.isNullOrEmpty(backgroundColorList)) {
            bundleCreateBaseParameters.putStringArrayList(ShareConstants.STORY_INTERACTIVE_COLOR_LIST, new ArrayList<>(backgroundColorList));
        }
        Utility.putNonEmptyString(bundleCreateBaseParameters, ShareConstants.STORY_DEEP_LINK_URL, shareStoryContent.getAttributionLink());
        return bundleCreateBaseParameters;
    }
}
