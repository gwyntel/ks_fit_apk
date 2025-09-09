package com.facebook.share;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookGraphResponseException;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.internal.CollectionMapper;
import com.facebook.internal.Mutable;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.share.Sharer;
import com.facebook.share.internal.ShareConstants;
import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.internal.VideoUploader;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideoContent;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public final class ShareApi {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String DEFAULT_GRAPH_NODE = "me";
    private static final String GRAPH_PATH_FORMAT = "%s/%s";
    private static final String PHOTOS_EDGE = "photos";
    private static final String TAG = "ShareApi";
    private String graphNode = DEFAULT_GRAPH_NODE;
    private String message;
    private final ShareContent shareContent;

    public ShareApi(ShareContent shareContent) {
        this.shareContent = shareContent;
    }

    private void addCommonParameters(Bundle bundle, ShareContent shareContent) {
        List<String> peopleIds = shareContent.getPeopleIds();
        if (!Utility.isNullOrEmpty(peopleIds)) {
            bundle.putString("tags", TextUtils.join(", ", peopleIds));
        }
        if (!Utility.isNullOrEmpty(shareContent.getPlaceId())) {
            bundle.putString("place", shareContent.getPlaceId());
        }
        if (!Utility.isNullOrEmpty(shareContent.getPageId())) {
            bundle.putString("page", shareContent.getPageId());
        }
        if (Utility.isNullOrEmpty(shareContent.getRef())) {
            return;
        }
        bundle.putString("ref", shareContent.getRef());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getGraphPath(String str) {
        try {
            return String.format(Locale.ROOT, GRAPH_PATH_FORMAT, URLEncoder.encode(getGraphNode(), "UTF-8"), str);
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    private Bundle getSharePhotoCommonParameters(SharePhoto sharePhoto, SharePhotoContent sharePhotoContent) throws JSONException {
        Bundle parameters = sharePhoto.getParameters();
        if (!parameters.containsKey("place") && !Utility.isNullOrEmpty(sharePhotoContent.getPlaceId())) {
            parameters.putString("place", sharePhotoContent.getPlaceId());
        }
        if (!parameters.containsKey("tags") && !Utility.isNullOrEmpty(sharePhotoContent.getPeopleIds())) {
            List<String> peopleIds = sharePhotoContent.getPeopleIds();
            if (!Utility.isNullOrEmpty(peopleIds)) {
                JSONArray jSONArray = new JSONArray();
                for (String str : peopleIds) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("tag_uid", str);
                    jSONArray.put(jSONObject);
                }
                parameters.putString("tags", jSONArray.toString());
            }
        }
        if (!parameters.containsKey("ref") && !Utility.isNullOrEmpty(sharePhotoContent.getRef())) {
            parameters.putString("ref", sharePhotoContent.getRef());
        }
        return parameters;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void handleImagesOnAction(Bundle bundle) {
        String string = bundle.getString("image");
        if (string != null) {
            try {
                try {
                    JSONArray jSONArray = new JSONArray(string);
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i2);
                        if (jSONObjectOptJSONObject != null) {
                            putImageInBundleWithArrayFormat(bundle, i2, jSONObjectOptJSONObject);
                        } else {
                            bundle.putString(String.format(Locale.ROOT, "image[%d][url]", Integer.valueOf(i2)), jSONArray.getString(i2));
                        }
                    }
                    bundle.remove("image");
                } catch (JSONException unused) {
                    putImageInBundleWithArrayFormat(bundle, 0, new JSONObject(string));
                    bundle.remove("image");
                }
            } catch (JSONException unused2) {
            }
        }
    }

    private static void putImageInBundleWithArrayFormat(Bundle bundle, int i2, JSONObject jSONObject) throws JSONException {
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            bundle.putString(String.format(Locale.ROOT, "image[%d][%s]", Integer.valueOf(i2), next), jSONObject.get(next).toString());
        }
    }

    public static void share(ShareContent shareContent, FacebookCallback<Sharer.Result> facebookCallback) {
        new ShareApi(shareContent).share(facebookCallback);
    }

    private void shareLinkContent(ShareLinkContent shareLinkContent, final FacebookCallback<Sharer.Result> facebookCallback) {
        GraphRequest.Callback callback = new GraphRequest.Callback() { // from class: com.facebook.share.ShareApi.4
            @Override // com.facebook.GraphRequest.Callback
            public void onCompleted(GraphResponse graphResponse) {
                JSONObject jSONObject = graphResponse.getJSONObject();
                ShareInternalUtility.invokeCallbackWithResults(facebookCallback, jSONObject == null ? null : jSONObject.optString("id"), graphResponse);
            }
        };
        Bundle bundle = new Bundle();
        addCommonParameters(bundle, shareLinkContent);
        bundle.putString("message", getMessage());
        bundle.putString("link", Utility.getUriString(shareLinkContent.getContentUrl()));
        bundle.putString("picture", Utility.getUriString(shareLinkContent.getImageUrl()));
        bundle.putString("name", shareLinkContent.getContentTitle());
        bundle.putString("description", shareLinkContent.getContentDescription());
        bundle.putString("ref", shareLinkContent.getRef());
        new GraphRequest(AccessToken.getCurrentAccessToken(), getGraphPath("feed"), bundle, HttpMethod.POST, callback).executeAsync();
    }

    private void shareOpenGraphContent(ShareOpenGraphContent shareOpenGraphContent, final FacebookCallback<Sharer.Result> facebookCallback) {
        final GraphRequest.Callback callback = new GraphRequest.Callback() { // from class: com.facebook.share.ShareApi.1
            @Override // com.facebook.GraphRequest.Callback
            public void onCompleted(GraphResponse graphResponse) {
                JSONObject jSONObject = graphResponse.getJSONObject();
                ShareInternalUtility.invokeCallbackWithResults(facebookCallback, jSONObject == null ? null : jSONObject.optString("id"), graphResponse);
            }
        };
        final ShareOpenGraphAction action = shareOpenGraphContent.getAction();
        final Bundle bundle = action.getBundle();
        addCommonParameters(bundle, shareOpenGraphContent);
        if (!Utility.isNullOrEmpty(getMessage())) {
            bundle.putString("message", getMessage());
        }
        stageOpenGraphAction(bundle, new CollectionMapper.OnMapperCompleteListener() { // from class: com.facebook.share.ShareApi.2
            @Override // com.facebook.internal.CollectionMapper.OnMapperCompleteListener
            public void onComplete() {
                try {
                    ShareApi.handleImagesOnAction(bundle);
                    new GraphRequest(AccessToken.getCurrentAccessToken(), ShareApi.this.getGraphPath(URLEncoder.encode(action.getActionType(), "UTF-8")), bundle, HttpMethod.POST, callback).executeAsync();
                } catch (UnsupportedEncodingException e2) {
                    ShareInternalUtility.invokeCallbackWithException(facebookCallback, e2);
                }
            }

            @Override // com.facebook.internal.CollectionMapper.OnErrorListener
            public void onError(FacebookException facebookException) {
                ShareInternalUtility.invokeCallbackWithException(facebookCallback, facebookException);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v9, types: [T, java.lang.Integer] */
    private void sharePhotoContent(SharePhotoContent sharePhotoContent, final FacebookCallback<Sharer.Result> facebookCallback) {
        ArrayList arrayList;
        final Mutable mutable = new Mutable(0);
        AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
        ArrayList arrayList2 = new ArrayList();
        final ArrayList arrayList3 = new ArrayList();
        final ArrayList arrayList4 = new ArrayList();
        GraphRequest.Callback callback = new GraphRequest.Callback() { // from class: com.facebook.share.ShareApi.3
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r1v4, types: [T, java.lang.Integer] */
            @Override // com.facebook.GraphRequest.Callback
            public void onCompleted(GraphResponse graphResponse) {
                JSONObject jSONObject = graphResponse.getJSONObject();
                if (jSONObject != null) {
                    arrayList3.add(jSONObject);
                }
                if (graphResponse.getError() != null) {
                    arrayList4.add(graphResponse);
                }
                mutable.value = Integer.valueOf(((Integer) r0.value).intValue() - 1);
                if (((Integer) mutable.value).intValue() == 0) {
                    if (!arrayList4.isEmpty()) {
                        ShareInternalUtility.invokeCallbackWithResults(facebookCallback, null, (GraphResponse) arrayList4.get(0));
                    } else {
                        if (arrayList3.isEmpty()) {
                            return;
                        }
                        ShareInternalUtility.invokeCallbackWithResults(facebookCallback, ((JSONObject) arrayList3.get(0)).optString("id"), graphResponse);
                    }
                }
            }
        };
        try {
            for (SharePhoto sharePhoto : sharePhotoContent.getPhotos()) {
                try {
                    Bundle sharePhotoCommonParameters = getSharePhotoCommonParameters(sharePhoto, sharePhotoContent);
                    Bitmap bitmap = sharePhoto.getBitmap();
                    Uri imageUrl = sharePhoto.getImageUrl();
                    String caption = sharePhoto.getCaption();
                    if (caption == null) {
                        caption = getMessage();
                    }
                    String str = caption;
                    if (bitmap != null) {
                        arrayList = arrayList2;
                        arrayList.add(GraphRequest.newUploadPhotoRequest(currentAccessToken, getGraphPath(PHOTOS_EDGE), bitmap, str, sharePhotoCommonParameters, callback));
                    } else {
                        arrayList = arrayList2;
                        if (imageUrl != null) {
                            arrayList.add(GraphRequest.newUploadPhotoRequest(currentAccessToken, getGraphPath(PHOTOS_EDGE), imageUrl, str, sharePhotoCommonParameters, callback));
                        }
                    }
                    arrayList2 = arrayList;
                } catch (JSONException e2) {
                    ShareInternalUtility.invokeCallbackWithException(facebookCallback, e2);
                    return;
                }
            }
            ArrayList arrayList5 = arrayList2;
            mutable.value = Integer.valueOf(((Integer) mutable.value).intValue() + arrayList5.size());
            Iterator it = arrayList5.iterator();
            while (it.hasNext()) {
                ((GraphRequest) it.next()).executeAsync();
            }
        } catch (FileNotFoundException e3) {
            ShareInternalUtility.invokeCallbackWithException(facebookCallback, e3);
        }
    }

    private void shareVideoContent(ShareVideoContent shareVideoContent, FacebookCallback<Sharer.Result> facebookCallback) {
        try {
            VideoUploader.uploadAsync(shareVideoContent, getGraphNode(), facebookCallback);
        } catch (FileNotFoundException e2) {
            ShareInternalUtility.invokeCallbackWithException(facebookCallback, e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stageArrayList(final ArrayList arrayList, final CollectionMapper.OnMapValueCompleteListener onMapValueCompleteListener) {
        final JSONArray jSONArray = new JSONArray();
        stageCollectionValues(new CollectionMapper.Collection<Integer>() { // from class: com.facebook.share.ShareApi.5
            @Override // com.facebook.internal.CollectionMapper.Collection
            public Iterator<Integer> keyIterator() {
                final int size = arrayList.size();
                final Mutable mutable = new Mutable(0);
                return new Iterator<Integer>() { // from class: com.facebook.share.ShareApi.5.1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        return ((Integer) mutable.value).intValue() < size;
                    }

                    @Override // java.util.Iterator
                    public void remove() {
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Type inference failed for: r1v4, types: [T, java.lang.Integer] */
                    @Override // java.util.Iterator
                    public Integer next() {
                        Mutable mutable2 = mutable;
                        T t2 = mutable2.value;
                        Integer num = (Integer) t2;
                        mutable2.value = Integer.valueOf(((Integer) t2).intValue() + 1);
                        return num;
                    }
                };
            }

            @Override // com.facebook.internal.CollectionMapper.Collection
            public Object get(Integer num) {
                return arrayList.get(num.intValue());
            }

            @Override // com.facebook.internal.CollectionMapper.Collection
            public void set(Integer num, Object obj, CollectionMapper.OnErrorListener onErrorListener) throws JSONException {
                try {
                    jSONArray.put(num.intValue(), obj);
                } catch (JSONException e2) {
                    String localizedMessage = e2.getLocalizedMessage();
                    if (localizedMessage == null) {
                        localizedMessage = "Error staging object.";
                    }
                    onErrorListener.onError(new FacebookException(localizedMessage));
                }
            }
        }, new CollectionMapper.OnMapperCompleteListener() { // from class: com.facebook.share.ShareApi.6
            @Override // com.facebook.internal.CollectionMapper.OnMapperCompleteListener
            public void onComplete() {
                onMapValueCompleteListener.onComplete(jSONArray);
            }

            @Override // com.facebook.internal.CollectionMapper.OnErrorListener
            public void onError(FacebookException facebookException) {
                onMapValueCompleteListener.onError(facebookException);
            }
        });
    }

    private <T> void stageCollectionValues(CollectionMapper.Collection<T> collection, CollectionMapper.OnMapperCompleteListener onMapperCompleteListener) {
        CollectionMapper.iterate(collection, new CollectionMapper.ValueMapper() { // from class: com.facebook.share.ShareApi.7
            @Override // com.facebook.internal.CollectionMapper.ValueMapper
            public void mapValue(Object obj, CollectionMapper.OnMapValueCompleteListener onMapValueCompleteListener) {
                if (obj instanceof ArrayList) {
                    ShareApi.this.stageArrayList((ArrayList) obj, onMapValueCompleteListener);
                    return;
                }
                if (obj instanceof ShareOpenGraphObject) {
                    ShareApi.this.stageOpenGraphObject((ShareOpenGraphObject) obj, onMapValueCompleteListener);
                } else if (obj instanceof SharePhoto) {
                    ShareApi.this.stagePhoto((SharePhoto) obj, onMapValueCompleteListener);
                } else {
                    onMapValueCompleteListener.onComplete(obj);
                }
            }
        }, onMapperCompleteListener);
    }

    private void stageOpenGraphAction(final Bundle bundle, CollectionMapper.OnMapperCompleteListener onMapperCompleteListener) {
        stageCollectionValues(new CollectionMapper.Collection<String>() { // from class: com.facebook.share.ShareApi.8
            @Override // com.facebook.internal.CollectionMapper.Collection
            public Iterator<String> keyIterator() {
                return bundle.keySet().iterator();
            }

            @Override // com.facebook.internal.CollectionMapper.Collection
            public Object get(String str) {
                return bundle.get(str);
            }

            @Override // com.facebook.internal.CollectionMapper.Collection
            public void set(String str, Object obj, CollectionMapper.OnErrorListener onErrorListener) {
                if (Utility.putJSONValueInBundle(bundle, str, obj)) {
                    return;
                }
                onErrorListener.onError(new FacebookException("Unexpected value: " + obj.toString()));
            }
        }, onMapperCompleteListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stageOpenGraphObject(final ShareOpenGraphObject shareOpenGraphObject, final CollectionMapper.OnMapValueCompleteListener onMapValueCompleteListener) {
        String string = shareOpenGraphObject.getString("type");
        if (string == null) {
            string = shareOpenGraphObject.getString("og:type");
        }
        final String str = string;
        if (str == null) {
            onMapValueCompleteListener.onError(new FacebookException("Open Graph objects must contain a type value."));
            return;
        }
        final JSONObject jSONObject = new JSONObject();
        CollectionMapper.Collection<String> collection = new CollectionMapper.Collection<String>() { // from class: com.facebook.share.ShareApi.9
            @Override // com.facebook.internal.CollectionMapper.Collection
            public Iterator<String> keyIterator() {
                return shareOpenGraphObject.keySet().iterator();
            }

            @Override // com.facebook.internal.CollectionMapper.Collection
            public Object get(String str2) {
                return shareOpenGraphObject.get(str2);
            }

            @Override // com.facebook.internal.CollectionMapper.Collection
            public void set(String str2, Object obj, CollectionMapper.OnErrorListener onErrorListener) throws JSONException {
                try {
                    jSONObject.put(str2, obj);
                } catch (JSONException e2) {
                    String localizedMessage = e2.getLocalizedMessage();
                    if (localizedMessage == null) {
                        localizedMessage = "Error staging object.";
                    }
                    onErrorListener.onError(new FacebookException(localizedMessage));
                }
            }
        };
        final GraphRequest.Callback callback = new GraphRequest.Callback() { // from class: com.facebook.share.ShareApi.10
            @Override // com.facebook.GraphRequest.Callback
            public void onCompleted(GraphResponse graphResponse) {
                FacebookRequestError error = graphResponse.getError();
                if (error != null) {
                    String errorMessage = error.getErrorMessage();
                    onMapValueCompleteListener.onError(new FacebookGraphResponseException(graphResponse, errorMessage != null ? errorMessage : "Error staging Open Graph object."));
                    return;
                }
                JSONObject jSONObject2 = graphResponse.getJSONObject();
                if (jSONObject2 == null) {
                    onMapValueCompleteListener.onError(new FacebookGraphResponseException(graphResponse, "Error staging Open Graph object."));
                    return;
                }
                String strOptString = jSONObject2.optString("id");
                if (strOptString == null) {
                    onMapValueCompleteListener.onError(new FacebookGraphResponseException(graphResponse, "Error staging Open Graph object."));
                } else {
                    onMapValueCompleteListener.onComplete(strOptString);
                }
            }
        };
        stageCollectionValues(collection, new CollectionMapper.OnMapperCompleteListener() { // from class: com.facebook.share.ShareApi.11
            @Override // com.facebook.internal.CollectionMapper.OnMapperCompleteListener
            public void onComplete() {
                String string2 = jSONObject.toString();
                Bundle bundle = new Bundle();
                bundle.putString("object", string2);
                try {
                    new GraphRequest(AccessToken.getCurrentAccessToken(), ShareApi.this.getGraphPath("objects/" + URLEncoder.encode(str, "UTF-8")), bundle, HttpMethod.POST, callback).executeAsync();
                } catch (UnsupportedEncodingException e2) {
                    String localizedMessage = e2.getLocalizedMessage();
                    if (localizedMessage == null) {
                        localizedMessage = "Error staging Open Graph object.";
                    }
                    onMapValueCompleteListener.onError(new FacebookException(localizedMessage));
                }
            }

            @Override // com.facebook.internal.CollectionMapper.OnErrorListener
            public void onError(FacebookException facebookException) {
                onMapValueCompleteListener.onError(facebookException);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stagePhoto(final SharePhoto sharePhoto, final CollectionMapper.OnMapValueCompleteListener onMapValueCompleteListener) {
        Bitmap bitmap = sharePhoto.getBitmap();
        Uri imageUrl = sharePhoto.getImageUrl();
        if (bitmap == null && imageUrl == null) {
            onMapValueCompleteListener.onError(new FacebookException("Photos must have an imageURL or bitmap."));
            return;
        }
        GraphRequest.Callback callback = new GraphRequest.Callback() { // from class: com.facebook.share.ShareApi.12
            @Override // com.facebook.GraphRequest.Callback
            public void onCompleted(GraphResponse graphResponse) throws JSONException {
                FacebookRequestError error = graphResponse.getError();
                if (error != null) {
                    String errorMessage = error.getErrorMessage();
                    onMapValueCompleteListener.onError(new FacebookGraphResponseException(graphResponse, errorMessage != null ? errorMessage : "Error staging photo."));
                    return;
                }
                JSONObject jSONObject = graphResponse.getJSONObject();
                if (jSONObject == null) {
                    onMapValueCompleteListener.onError(new FacebookException("Error staging photo."));
                    return;
                }
                String strOptString = jSONObject.optString(ShareConstants.MEDIA_URI);
                if (strOptString == null) {
                    onMapValueCompleteListener.onError(new FacebookException("Error staging photo."));
                    return;
                }
                JSONObject jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put("url", strOptString);
                    jSONObject2.put(NativeProtocol.IMAGE_USER_GENERATED_KEY, sharePhoto.getUserGenerated());
                    onMapValueCompleteListener.onComplete(jSONObject2);
                } catch (JSONException e2) {
                    String localizedMessage = e2.getLocalizedMessage();
                    onMapValueCompleteListener.onError(new FacebookException(localizedMessage != null ? localizedMessage : "Error staging photo."));
                }
            }
        };
        if (bitmap != null) {
            ShareInternalUtility.newUploadStagingResourceWithImageRequest(AccessToken.getCurrentAccessToken(), bitmap, callback).executeAsync();
            return;
        }
        try {
            ShareInternalUtility.newUploadStagingResourceWithImageRequest(AccessToken.getCurrentAccessToken(), imageUrl, callback).executeAsync();
        } catch (FileNotFoundException e2) {
            String localizedMessage = e2.getLocalizedMessage();
            if (localizedMessage == null) {
                localizedMessage = "Error staging photo.";
            }
            onMapValueCompleteListener.onError(new FacebookException(localizedMessage));
        }
    }

    public boolean canShare() {
        if (getShareContent() == null) {
            return false;
        }
        AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
        if (!AccessToken.isCurrentAccessTokenActive()) {
            return false;
        }
        Set<String> permissions = currentAccessToken.getPermissions();
        if (permissions != null && permissions.contains("publish_actions")) {
            return true;
        }
        Log.w(TAG, "The publish_actions permissions are missing, the share will fail unless this app was authorized to publish in another installation.");
        return true;
    }

    public String getGraphNode() {
        return this.graphNode;
    }

    public String getMessage() {
        return this.message;
    }

    public ShareContent getShareContent() {
        return this.shareContent;
    }

    public void setGraphNode(String str) {
        this.graphNode = str;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void share(FacebookCallback<Sharer.Result> facebookCallback) {
        if (!canShare()) {
            ShareInternalUtility.invokeCallbackWithError(facebookCallback, "Insufficient permissions for sharing content via Api.");
            return;
        }
        ShareContent shareContent = getShareContent();
        try {
            ShareContentValidation.validateForApiShare(shareContent);
            if (shareContent instanceof ShareLinkContent) {
                shareLinkContent((ShareLinkContent) shareContent, facebookCallback);
                return;
            }
            if (shareContent instanceof SharePhotoContent) {
                sharePhotoContent((SharePhotoContent) shareContent, facebookCallback);
            } else if (shareContent instanceof ShareVideoContent) {
                shareVideoContent((ShareVideoContent) shareContent, facebookCallback);
            } else if (shareContent instanceof ShareOpenGraphContent) {
                shareOpenGraphContent((ShareOpenGraphContent) shareContent, facebookCallback);
            }
        } catch (FacebookException e2) {
            ShareInternalUtility.invokeCallbackWithException(facebookCallback, e2);
        }
    }
}
