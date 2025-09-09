package com.facebook.share.model;

import android.os.Parcel;
import androidx.annotation.Nullable;

@Deprecated
/* loaded from: classes3.dex */
public abstract class ShareMessengerActionButton implements ShareModel {
    private final String title;

    public static abstract class Builder<M extends ShareMessengerActionButton, B extends Builder> implements ShareModelBuilder<M, B> {
        private String title;

        public B setTitle(@Nullable String str) {
            this.title = str;
            return this;
        }

        @Override // com.facebook.share.model.ShareModelBuilder
        public B readFrom(M m2) {
            return m2 == null ? this : (B) setTitle(m2.getTitle());
        }
    }

    protected ShareMessengerActionButton(Builder builder) {
        this.title = builder.title;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getTitle() {
        return this.title;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.title);
    }

    ShareMessengerActionButton(Parcel parcel) {
        this.title = parcel.readString();
    }
}
