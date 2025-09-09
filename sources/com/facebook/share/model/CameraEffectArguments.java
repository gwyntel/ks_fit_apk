package com.facebook.share.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import java.util.Set;

/* loaded from: classes3.dex */
public class CameraEffectArguments implements ShareModel {
    public static final Parcelable.Creator<CameraEffectArguments> CREATOR = new Parcelable.Creator<CameraEffectArguments>() { // from class: com.facebook.share.model.CameraEffectArguments.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CameraEffectArguments createFromParcel(Parcel parcel) {
            return new CameraEffectArguments(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CameraEffectArguments[] newArray(int i2) {
            return new CameraEffectArguments[i2];
        }
    };
    private final Bundle params;

    public static class Builder implements ShareModelBuilder<CameraEffectArguments, Builder> {
        private Bundle params = new Bundle();

        public Builder putArgument(String str, String str2) {
            this.params.putString(str, str2);
            return this;
        }

        @Override // com.facebook.share.ShareBuilder
        public CameraEffectArguments build() {
            return new CameraEffectArguments(this);
        }

        public Builder putArgument(String str, String[] strArr) {
            this.params.putStringArray(str, strArr);
            return this;
        }

        @Override // com.facebook.share.model.ShareModelBuilder
        public Builder readFrom(CameraEffectArguments cameraEffectArguments) {
            if (cameraEffectArguments != null) {
                this.params.putAll(cameraEffectArguments.params);
            }
            return this;
        }

        public Builder readFrom(Parcel parcel) {
            return readFrom((CameraEffectArguments) parcel.readParcelable(CameraEffectArguments.class.getClassLoader()));
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Nullable
    public Object get(String str) {
        return this.params.get(str);
    }

    @Nullable
    public String getString(String str) {
        return this.params.getString(str);
    }

    @Nullable
    public String[] getStringArray(String str) {
        return this.params.getStringArray(str);
    }

    public Set<String> keySet() {
        return this.params.keySet();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeBundle(this.params);
    }

    private CameraEffectArguments(Builder builder) {
        this.params = builder.params;
    }

    CameraEffectArguments(Parcel parcel) {
        this.params = parcel.readBundle(getClass().getClassLoader());
    }
}
