package com.google.android.gms.fido.u2f.api.common;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@SafeParcelable.Class(creator = "SignRequestParamsCreator")
@SafeParcelable.Reserved({1})
@Deprecated
/* loaded from: classes3.dex */
public class SignRequestParams extends RequestParams {

    @NonNull
    public static final Parcelable.Creator<SignRequestParams> CREATOR = new zzk();
    public static final int MAX_DISPLAY_HINT_LENGTH = 80;

    @SafeParcelable.Field(getter = "getRequestId", id = 2)
    private final Integer zza;

    @Nullable
    @SafeParcelable.Field(getter = "getTimeoutSeconds", id = 3)
    private final Double zzb;

    @SafeParcelable.Field(getter = "getAppId", id = 4)
    private final Uri zzc;

    @SafeParcelable.Field(getter = "getDefaultSignChallenge", id = 5)
    private final byte[] zzd;

    @SafeParcelable.Field(getter = "getRegisteredKeys", id = 6)
    private final List zze;

    @SafeParcelable.Field(getter = "getChannelIdValue", id = 7)
    private final ChannelIdValue zzf;

    @SafeParcelable.Field(getter = "getDisplayHint", id = 8)
    private final String zzg;
    private final Set zzh;

    public static final class Builder {

        /* renamed from: a, reason: collision with root package name */
        Integer f12946a;

        /* renamed from: b, reason: collision with root package name */
        Double f12947b;

        /* renamed from: c, reason: collision with root package name */
        Uri f12948c;

        /* renamed from: d, reason: collision with root package name */
        byte[] f12949d;

        /* renamed from: e, reason: collision with root package name */
        List f12950e;

        /* renamed from: f, reason: collision with root package name */
        ChannelIdValue f12951f;

        /* renamed from: g, reason: collision with root package name */
        String f12952g;

        @NonNull
        public SignRequestParams build() {
            return new SignRequestParams(this.f12946a, this.f12947b, this.f12948c, this.f12949d, this.f12950e, this.f12951f, this.f12952g);
        }

        @NonNull
        public Builder setAppId(@NonNull Uri uri) {
            this.f12948c = uri;
            return this;
        }

        @NonNull
        public Builder setChannelIdValue(@NonNull ChannelIdValue channelIdValue) {
            this.f12951f = channelIdValue;
            return this;
        }

        @NonNull
        public Builder setDefaultSignChallenge(@NonNull byte[] bArr) {
            this.f12949d = bArr;
            return this;
        }

        @NonNull
        public Builder setDisplayHint(@NonNull String str) {
            this.f12952g = str;
            return this;
        }

        @NonNull
        public Builder setRegisteredKeys(@NonNull List<RegisteredKey> list) {
            this.f12950e = list;
            return this;
        }

        @NonNull
        public Builder setRequestId(@NonNull Integer num) {
            this.f12946a = num;
            return this;
        }

        @NonNull
        public Builder setTimeoutSeconds(@Nullable Double d2) {
            this.f12947b = d2;
            return this;
        }
    }

    SignRequestParams(Integer num, Double d2, Uri uri, byte[] bArr, List list, ChannelIdValue channelIdValue, String str) {
        this.zza = num;
        this.zzb = d2;
        this.zzc = uri;
        this.zzd = bArr;
        Preconditions.checkArgument((list == null || list.isEmpty()) ? false : true, "registeredKeys must not be null or empty");
        this.zze = list;
        this.zzf = channelIdValue;
        HashSet hashSet = new HashSet();
        if (uri != null) {
            hashSet.add(uri);
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            RegisteredKey registeredKey = (RegisteredKey) it.next();
            Preconditions.checkArgument((registeredKey.getAppId() == null && uri == null) ? false : true, "registered key has null appId and no request appId is provided");
            registeredKey.getChallengeValue();
            Preconditions.checkArgument(true, "register request has null challenge and no default challenge isprovided");
            if (registeredKey.getAppId() != null) {
                hashSet.add(Uri.parse(registeredKey.getAppId()));
            }
        }
        this.zzh = hashSet;
        Preconditions.checkArgument(str == null || str.length() <= 80, "Display Hint cannot be longer than 80 characters");
        this.zzg = str;
    }

    public boolean equals(@NonNull Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SignRequestParams)) {
            return false;
        }
        SignRequestParams signRequestParams = (SignRequestParams) obj;
        return Objects.equal(this.zza, signRequestParams.zza) && Objects.equal(this.zzb, signRequestParams.zzb) && Objects.equal(this.zzc, signRequestParams.zzc) && Arrays.equals(this.zzd, signRequestParams.zzd) && this.zze.containsAll(signRequestParams.zze) && signRequestParams.zze.containsAll(this.zze) && Objects.equal(this.zzf, signRequestParams.zzf) && Objects.equal(this.zzg, signRequestParams.zzg);
    }

    @Override // com.google.android.gms.fido.u2f.api.common.RequestParams
    @NonNull
    public Set<Uri> getAllAppIds() {
        return this.zzh;
    }

    @Override // com.google.android.gms.fido.u2f.api.common.RequestParams
    @NonNull
    public Uri getAppId() {
        return this.zzc;
    }

    @Override // com.google.android.gms.fido.u2f.api.common.RequestParams
    @NonNull
    public ChannelIdValue getChannelIdValue() {
        return this.zzf;
    }

    @NonNull
    public byte[] getDefaultSignChallenge() {
        return this.zzd;
    }

    @Override // com.google.android.gms.fido.u2f.api.common.RequestParams
    @NonNull
    public String getDisplayHint() {
        return this.zzg;
    }

    @Override // com.google.android.gms.fido.u2f.api.common.RequestParams
    @NonNull
    public List<RegisteredKey> getRegisteredKeys() {
        return this.zze;
    }

    @Override // com.google.android.gms.fido.u2f.api.common.RequestParams
    @NonNull
    public Integer getRequestId() {
        return this.zza;
    }

    @Override // com.google.android.gms.fido.u2f.api.common.RequestParams
    @Nullable
    public Double getTimeoutSeconds() {
        return this.zzb;
    }

    public int hashCode() {
        return Objects.hashCode(this.zza, this.zzc, this.zzb, this.zze, this.zzf, this.zzg, Integer.valueOf(Arrays.hashCode(this.zzd)));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeIntegerObject(parcel, 2, getRequestId(), false);
        SafeParcelWriter.writeDoubleObject(parcel, 3, getTimeoutSeconds(), false);
        SafeParcelWriter.writeParcelable(parcel, 4, getAppId(), i2, false);
        SafeParcelWriter.writeByteArray(parcel, 5, getDefaultSignChallenge(), false);
        SafeParcelWriter.writeTypedList(parcel, 6, getRegisteredKeys(), false);
        SafeParcelWriter.writeParcelable(parcel, 7, getChannelIdValue(), i2, false);
        SafeParcelWriter.writeString(parcel, 8, getDisplayHint(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
