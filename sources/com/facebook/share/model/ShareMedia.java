package com.facebook.share.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class ShareMedia implements ShareModel {
    private final Bundle params;

    public static abstract class Builder<M extends ShareMedia, B extends Builder> implements ShareModelBuilder<M, B> {
        private Bundle params = new Bundle();

        static List<ShareMedia> readListFrom(Parcel parcel) {
            Parcelable[] parcelableArray = parcel.readParcelableArray(ShareMedia.class.getClassLoader());
            ArrayList arrayList = new ArrayList(parcelableArray.length);
            for (Parcelable parcelable : parcelableArray) {
                arrayList.add((ShareMedia) parcelable);
            }
            return arrayList;
        }

        static void writeListTo(Parcel parcel, int i2, List<ShareMedia> list) {
            parcel.writeParcelableArray((ShareMedia[]) list.toArray(), i2);
        }

        @Deprecated
        public B setParameter(String str, String str2) {
            this.params.putString(str, str2);
            return this;
        }

        @Deprecated
        public B setParameters(Bundle bundle) {
            this.params.putAll(bundle);
            return this;
        }

        @Override // com.facebook.share.model.ShareModelBuilder
        public B readFrom(M m2) {
            return m2 == null ? this : (B) setParameters(m2.getParameters());
        }
    }

    public enum Type {
        PHOTO,
        VIDEO
    }

    protected ShareMedia(Builder builder) {
        this.params = new Bundle(builder.params);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public abstract Type getMediaType();

    @Deprecated
    public Bundle getParameters() {
        return new Bundle(this.params);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeBundle(this.params);
    }

    ShareMedia(Parcel parcel) {
        this.params = parcel.readBundle();
    }
}
