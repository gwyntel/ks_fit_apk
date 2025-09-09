package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import androidx.annotation.Nullable;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareContent.Builder;
import com.facebook.share.model.ShareHashtag;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class ShareContent<P extends ShareContent, E extends Builder> implements ShareModel {
    private final Uri contentUrl;
    private final ShareHashtag hashtag;
    private final String pageId;
    private final List<String> peopleIds;
    private final String placeId;
    private final String ref;

    public static abstract class Builder<P extends ShareContent, E extends Builder> implements ShareModelBuilder<P, E> {
        private Uri contentUrl;
        private ShareHashtag hashtag;
        private String pageId;
        private List<String> peopleIds;
        private String placeId;
        private String ref;

        public E setContentUrl(@Nullable Uri uri) {
            this.contentUrl = uri;
            return this;
        }

        public E setPageId(@Nullable String str) {
            this.pageId = str;
            return this;
        }

        public E setPeopleIds(@Nullable List<String> list) {
            this.peopleIds = list == null ? null : Collections.unmodifiableList(list);
            return this;
        }

        public E setPlaceId(@Nullable String str) {
            this.placeId = str;
            return this;
        }

        public E setRef(@Nullable String str) {
            this.ref = str;
            return this;
        }

        public E setShareHashtag(@Nullable ShareHashtag shareHashtag) {
            this.hashtag = shareHashtag;
            return this;
        }

        @Override // com.facebook.share.model.ShareModelBuilder
        public E readFrom(P p2) {
            return p2 == null ? this : (E) setContentUrl(p2.getContentUrl()).setPeopleIds(p2.getPeopleIds()).setPlaceId(p2.getPlaceId()).setPageId(p2.getPageId()).setRef(p2.getRef()).setShareHashtag(p2.getShareHashtag());
        }
    }

    protected ShareContent(Builder builder) {
        this.contentUrl = builder.contentUrl;
        this.peopleIds = builder.peopleIds;
        this.placeId = builder.placeId;
        this.pageId = builder.pageId;
        this.ref = builder.ref;
        this.hashtag = builder.hashtag;
    }

    private List<String> readUnmodifiableStringList(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        parcel.readStringList(arrayList);
        if (arrayList.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(arrayList);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Nullable
    public Uri getContentUrl() {
        return this.contentUrl;
    }

    @Nullable
    public String getPageId() {
        return this.pageId;
    }

    @Nullable
    public List<String> getPeopleIds() {
        return this.peopleIds;
    }

    @Nullable
    public String getPlaceId() {
        return this.placeId;
    }

    @Nullable
    public String getRef() {
        return this.ref;
    }

    @Nullable
    public ShareHashtag getShareHashtag() {
        return this.hashtag;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeParcelable(this.contentUrl, 0);
        parcel.writeStringList(this.peopleIds);
        parcel.writeString(this.placeId);
        parcel.writeString(this.pageId);
        parcel.writeString(this.ref);
        parcel.writeParcelable(this.hashtag, 0);
    }

    protected ShareContent(Parcel parcel) {
        this.contentUrl = (Uri) parcel.readParcelable(Uri.class.getClassLoader());
        this.peopleIds = readUnmodifiableStringList(parcel);
        this.placeId = parcel.readString();
        this.pageId = parcel.readString();
        this.ref = parcel.readString();
        this.hashtag = new ShareHashtag.Builder().readFrom(parcel).build();
    }
}
