package com.google.android.gms.fido.fido2.api.common;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.google.android.gms.fido.fido2.api.common.PublicKeyCredentialType;

/* loaded from: classes3.dex */
public enum PublicKeyCredentialType implements Parcelable {
    PUBLIC_KEY("public-key");


    @NonNull
    public static final Parcelable.Creator<PublicKeyCredentialType> CREATOR = new Parcelable.Creator() { // from class: com.google.android.gms.fido.fido2.api.common.zzaq
        @Override // android.os.Parcelable.Creator
        public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            try {
                return PublicKeyCredentialType.fromString(parcel.readString());
            } catch (PublicKeyCredentialType.UnsupportedPublicKeyCredTypeException e2) {
                throw new RuntimeException(e2);
            }
        }

        @Override // android.os.Parcelable.Creator
        public final /* synthetic */ Object[] newArray(int i2) {
            return new PublicKeyCredentialType[i2];
        }
    };

    @NonNull
    private final String zzb = "public-key";

    public static class UnsupportedPublicKeyCredTypeException extends Exception {
        public UnsupportedPublicKeyCredTypeException(@NonNull String str) {
            super(str);
        }
    }

    PublicKeyCredentialType(String str) {
    }

    @NonNull
    public static PublicKeyCredentialType fromString(@NonNull String str) throws UnsupportedPublicKeyCredTypeException {
        for (PublicKeyCredentialType publicKeyCredentialType : values()) {
            if (str.equals(publicKeyCredentialType.zzb)) {
                return publicKeyCredentialType;
            }
        }
        throw new UnsupportedPublicKeyCredTypeException(String.format("PublicKeyCredentialType %s not supported", str));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // java.lang.Enum
    @NonNull
    public String toString() {
        return this.zzb;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        parcel.writeString(this.zzb);
    }
}
