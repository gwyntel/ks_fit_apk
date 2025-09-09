package com.facebook.internal;

import android.content.Context;
import android.net.Uri;
import com.facebook.FacebookSdk;
import java.util.Locale;

/* loaded from: classes3.dex */
public class ImageRequest {
    private static final String ACCESS_TOKEN_PARAM = "access_token";
    private static final String HEIGHT_PARAM = "height";
    private static final String MIGRATION_PARAM = "migration_overrides";
    private static final String MIGRATION_VALUE = "{october_2012:true}";
    private static final String PATH = "%s/%s/picture";
    public static final int UNSPECIFIED_DIMENSION = 0;
    private static final String WIDTH_PARAM = "width";
    private boolean allowCachedRedirects;
    private Callback callback;
    private Object callerTag;
    private Context context;
    private Uri imageUri;

    public static class Builder {
        private boolean allowCachedRedirects;
        private Callback callback;
        private Object callerTag;
        private Context context;
        private Uri imageUrl;

        public Builder(Context context, Uri uri) {
            Validate.notNull(uri, "imageUri");
            this.context = context;
            this.imageUrl = uri;
        }

        public ImageRequest build() {
            return new ImageRequest(this);
        }

        public Builder setAllowCachedRedirects(boolean z2) {
            this.allowCachedRedirects = z2;
            return this;
        }

        public Builder setCallback(Callback callback) {
            this.callback = callback;
            return this;
        }

        public Builder setCallerTag(Object obj) {
            this.callerTag = obj;
            return this;
        }
    }

    public interface Callback {
        void onCompleted(ImageResponse imageResponse);
    }

    public static Uri getProfilePictureUri(String str, int i2, int i3) {
        return getProfilePictureUri(str, i2, i3, "");
    }

    public Callback getCallback() {
        return this.callback;
    }

    public Object getCallerTag() {
        return this.callerTag;
    }

    public Context getContext() {
        return this.context;
    }

    public Uri getImageUri() {
        return this.imageUri;
    }

    public boolean isCachedRedirectAllowed() {
        return this.allowCachedRedirects;
    }

    private ImageRequest(Builder builder) {
        this.context = builder.context;
        this.imageUri = builder.imageUrl;
        this.callback = builder.callback;
        this.allowCachedRedirects = builder.allowCachedRedirects;
        this.callerTag = builder.callerTag == null ? new Object() : builder.callerTag;
    }

    public static Uri getProfilePictureUri(String str, int i2, int i3, String str2) {
        Validate.notNullOrEmpty(str, "userId");
        int iMax = Math.max(i2, 0);
        int iMax2 = Math.max(i3, 0);
        if (iMax == 0 && iMax2 == 0) {
            throw new IllegalArgumentException("Either width or height must be greater than 0");
        }
        Uri.Builder builderPath = Uri.parse(ServerProtocol.getGraphUrlBase()).buildUpon().path(String.format(Locale.US, PATH, FacebookSdk.getGraphApiVersion(), str));
        if (iMax2 != 0) {
            builderPath.appendQueryParameter("height", String.valueOf(iMax2));
        }
        if (iMax != 0) {
            builderPath.appendQueryParameter("width", String.valueOf(iMax));
        }
        builderPath.appendQueryParameter(MIGRATION_PARAM, MIGRATION_VALUE);
        if (!Utility.isNullOrEmpty(str2)) {
            builderPath.appendQueryParameter("access_token", str2);
        }
        return builderPath.build();
    }
}
