package com.google.android.gms.fido.fido2.api.common;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "UvmEntryCreator")
/* loaded from: classes3.dex */
public class UvmEntry extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<UvmEntry> CREATOR = new zzba();

    @SafeParcelable.Field(getter = "getUserVerificationMethod", id = 1)
    private final int zza;

    @SafeParcelable.Field(getter = "getKeyProtectionType", id = 2)
    private final short zzb;

    @SafeParcelable.Field(getter = "getMatcherProtectionType", id = 3)
    private final short zzc;

    public static final class Builder {
        private int zza;
        private short zzb;
        private short zzc;

        @NonNull
        public UvmEntry build() {
            return new UvmEntry(this.zza, this.zzb, this.zzc);
        }

        @NonNull
        public Builder setKeyProtectionType(short s2) {
            this.zzb = s2;
            return this;
        }

        @NonNull
        public Builder setMatcherProtectionType(short s2) {
            this.zzc = s2;
            return this;
        }

        @NonNull
        public Builder setUserVerificationMethod(int i2) {
            this.zza = i2;
            return this;
        }
    }

    UvmEntry(int i2, short s2, short s3) {
        this.zza = i2;
        this.zzb = s2;
        this.zzc = s3;
    }

    public boolean equals(@NonNull Object obj) {
        if (!(obj instanceof UvmEntry)) {
            return false;
        }
        UvmEntry uvmEntry = (UvmEntry) obj;
        return this.zza == uvmEntry.zza && this.zzb == uvmEntry.zzb && this.zzc == uvmEntry.zzc;
    }

    public short getKeyProtectionType() {
        return this.zzb;
    }

    public short getMatcherProtectionType() {
        return this.zzc;
    }

    public int getUserVerificationMethod() {
        return this.zza;
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zza), Short.valueOf(this.zzb), Short.valueOf(this.zzc));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, getUserVerificationMethod());
        SafeParcelWriter.writeShort(parcel, 2, getKeyProtectionType());
        SafeParcelWriter.writeShort(parcel, 3, getMatcherProtectionType());
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
