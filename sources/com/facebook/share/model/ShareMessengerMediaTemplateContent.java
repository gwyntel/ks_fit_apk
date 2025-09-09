package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.share.model.ShareContent;

@Deprecated
/* loaded from: classes3.dex */
public final class ShareMessengerMediaTemplateContent extends ShareContent<ShareMessengerMediaTemplateContent, Builder> {
    public static final Parcelable.Creator<ShareMessengerMediaTemplateContent> CREATOR = new Parcelable.Creator<ShareMessengerMediaTemplateContent>() { // from class: com.facebook.share.model.ShareMessengerMediaTemplateContent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ShareMessengerMediaTemplateContent createFromParcel(Parcel parcel) {
            return new ShareMessengerMediaTemplateContent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ShareMessengerMediaTemplateContent[] newArray(int i2) {
            return new ShareMessengerMediaTemplateContent[i2];
        }
    };
    private final String attachmentId;
    private final ShareMessengerActionButton button;
    private final MediaType mediaType;
    private final Uri mediaUrl;

    public static class Builder extends ShareContent.Builder<ShareMessengerMediaTemplateContent, Builder> {
        private String attachmentId;
        private ShareMessengerActionButton button;
        private MediaType mediaType;
        private Uri mediaUrl;

        public Builder setAttachmentId(String str) {
            this.attachmentId = str;
            return this;
        }

        public Builder setButton(ShareMessengerActionButton shareMessengerActionButton) {
            this.button = shareMessengerActionButton;
            return this;
        }

        public Builder setMediaType(MediaType mediaType) {
            this.mediaType = mediaType;
            return this;
        }

        public Builder setMediaUrl(Uri uri) {
            this.mediaUrl = uri;
            return this;
        }

        @Override // com.facebook.share.ShareBuilder
        public ShareMessengerMediaTemplateContent build() {
            return new ShareMessengerMediaTemplateContent(this);
        }

        @Override // com.facebook.share.model.ShareContent.Builder, com.facebook.share.model.ShareModelBuilder
        public Builder readFrom(ShareMessengerMediaTemplateContent shareMessengerMediaTemplateContent) {
            return shareMessengerMediaTemplateContent == null ? this : ((Builder) super.readFrom((Builder) shareMessengerMediaTemplateContent)).setMediaType(shareMessengerMediaTemplateContent.getMediaType()).setAttachmentId(shareMessengerMediaTemplateContent.getAttachmentId()).setMediaUrl(shareMessengerMediaTemplateContent.getMediaUrl()).setButton(shareMessengerMediaTemplateContent.getButton());
        }
    }

    public enum MediaType {
        IMAGE,
        VIDEO
    }

    @Override // com.facebook.share.model.ShareContent, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getAttachmentId() {
        return this.attachmentId;
    }

    public ShareMessengerActionButton getButton() {
        return this.button;
    }

    public MediaType getMediaType() {
        return this.mediaType;
    }

    public Uri getMediaUrl() {
        return this.mediaUrl;
    }

    @Override // com.facebook.share.model.ShareContent, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeSerializable(this.mediaType);
        parcel.writeString(this.attachmentId);
        parcel.writeParcelable(this.mediaUrl, i2);
        parcel.writeParcelable(this.button, i2);
    }

    private ShareMessengerMediaTemplateContent(Builder builder) {
        super(builder);
        this.mediaType = builder.mediaType;
        this.attachmentId = builder.attachmentId;
        this.mediaUrl = builder.mediaUrl;
        this.button = builder.button;
    }

    ShareMessengerMediaTemplateContent(Parcel parcel) {
        super(parcel);
        this.mediaType = (MediaType) parcel.readSerializable();
        this.attachmentId = parcel.readString();
        this.mediaUrl = (Uri) parcel.readParcelable(Uri.class.getClassLoader());
        this.button = (ShareMessengerActionButton) parcel.readParcelable(ShareMessengerActionButton.class.getClassLoader());
    }
}
