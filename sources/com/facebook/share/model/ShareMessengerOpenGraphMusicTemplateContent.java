package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.share.model.ShareContent;

@Deprecated
/* loaded from: classes3.dex */
public final class ShareMessengerOpenGraphMusicTemplateContent extends ShareContent<ShareMessengerOpenGraphMusicTemplateContent, Builder> {
    public static final Parcelable.Creator<ShareMessengerOpenGraphMusicTemplateContent> CREATOR = new Parcelable.Creator<ShareMessengerOpenGraphMusicTemplateContent>() { // from class: com.facebook.share.model.ShareMessengerOpenGraphMusicTemplateContent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ShareMessengerOpenGraphMusicTemplateContent createFromParcel(Parcel parcel) {
            return new ShareMessengerOpenGraphMusicTemplateContent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ShareMessengerOpenGraphMusicTemplateContent[] newArray(int i2) {
            return new ShareMessengerOpenGraphMusicTemplateContent[i2];
        }
    };
    private final ShareMessengerActionButton button;
    private final Uri url;

    public static class Builder extends ShareContent.Builder<ShareMessengerOpenGraphMusicTemplateContent, Builder> {
        private ShareMessengerActionButton button;
        private Uri url;

        public Builder setButton(ShareMessengerActionButton shareMessengerActionButton) {
            this.button = shareMessengerActionButton;
            return this;
        }

        public Builder setUrl(Uri uri) {
            this.url = uri;
            return this;
        }

        @Override // com.facebook.share.ShareBuilder
        public ShareMessengerOpenGraphMusicTemplateContent build() {
            return new ShareMessengerOpenGraphMusicTemplateContent(this);
        }

        @Override // com.facebook.share.model.ShareContent.Builder, com.facebook.share.model.ShareModelBuilder
        public Builder readFrom(ShareMessengerOpenGraphMusicTemplateContent shareMessengerOpenGraphMusicTemplateContent) {
            return shareMessengerOpenGraphMusicTemplateContent == null ? this : ((Builder) super.readFrom((Builder) shareMessengerOpenGraphMusicTemplateContent)).setUrl(shareMessengerOpenGraphMusicTemplateContent.getUrl()).setButton(shareMessengerOpenGraphMusicTemplateContent.getButton());
        }
    }

    @Override // com.facebook.share.model.ShareContent, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ShareMessengerActionButton getButton() {
        return this.button;
    }

    public Uri getUrl() {
        return this.url;
    }

    @Override // com.facebook.share.model.ShareContent, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeParcelable(this.url, i2);
        parcel.writeParcelable(this.button, i2);
    }

    private ShareMessengerOpenGraphMusicTemplateContent(Builder builder) {
        super(builder);
        this.url = builder.url;
        this.button = builder.button;
    }

    ShareMessengerOpenGraphMusicTemplateContent(Parcel parcel) {
        super(parcel);
        this.url = (Uri) parcel.readParcelable(Uri.class.getClassLoader());
        this.button = (ShareMessengerActionButton) parcel.readParcelable(ShareMessengerActionButton.class.getClassLoader());
    }
}
