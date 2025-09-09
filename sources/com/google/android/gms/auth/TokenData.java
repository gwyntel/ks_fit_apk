package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.List;

@ShowFirstParty
@SafeParcelable.Class(creator = "TokenDataCreator")
/* loaded from: classes3.dex */
public class TokenData extends AbstractSafeParcelable implements ReflectedParcelable {

    @NonNull
    public static final Parcelable.Creator<TokenData> CREATOR = new zzm();

    /* renamed from: a, reason: collision with root package name */
    final int f12618a;

    @SafeParcelable.Field(getter = "getToken", id = 2)
    private final String zzb;

    @Nullable
    @SafeParcelable.Field(getter = "getExpirationTimeSecs", id = 3)
    private final Long zzc;

    @SafeParcelable.Field(getter = "isCached", id = 4)
    private final boolean zzd;

    @SafeParcelable.Field(getter = "isSnowballed", id = 5)
    private final boolean zze;

    @Nullable
    @SafeParcelable.Field(getter = "getGrantedScopes", id = 6)
    private final List zzf;

    @Nullable
    @SafeParcelable.Field(getter = "getScopeData", id = 7)
    private final String zzg;

    TokenData(int i2, String str, Long l2, boolean z2, boolean z3, List list, String str2) {
        this.f12618a = i2;
        this.zzb = Preconditions.checkNotEmpty(str);
        this.zzc = l2;
        this.zzd = z2;
        this.zze = z3;
        this.zzf = list;
        this.zzg = str2;
    }

    public final boolean equals(@Nullable Object obj) {
        if (!(obj instanceof TokenData)) {
            return false;
        }
        TokenData tokenData = (TokenData) obj;
        return TextUtils.equals(this.zzb, tokenData.zzb) && Objects.equal(this.zzc, tokenData.zzc) && this.zzd == tokenData.zzd && this.zze == tokenData.zze && Objects.equal(this.zzf, tokenData.zzf) && Objects.equal(this.zzg, tokenData.zzg);
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzb, this.zzc, Boolean.valueOf(this.zzd), Boolean.valueOf(this.zze), this.zzf, this.zzg);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.f12618a);
        SafeParcelWriter.writeString(parcel, 2, this.zzb, false);
        SafeParcelWriter.writeLongObject(parcel, 3, this.zzc, false);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzd);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zze);
        SafeParcelWriter.writeStringList(parcel, 6, this.zzf, false);
        SafeParcelWriter.writeString(parcel, 7, this.zzg, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @NonNull
    public final String zza() {
        return this.zzb;
    }
}
