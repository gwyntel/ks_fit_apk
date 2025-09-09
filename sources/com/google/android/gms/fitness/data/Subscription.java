package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Locale;

@SafeParcelable.Class(creator = "SubscriptionCreator")
@SafeParcelable.Reserved({1000})
/* loaded from: classes3.dex */
public class Subscription extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<Subscription> CREATOR = new zzag();

    @SafeParcelable.Field(getter = "getDataSource", id = 1)
    private final DataSource zza;

    @SafeParcelable.Field(getter = "getDataType", id = 2)
    private final DataType zzb;

    @SafeParcelable.Field(getter = "getSamplingRateMicros", id = 3)
    private final long zzc;

    @SafeParcelable.Field(getter = "getAccuracyMode", id = 4)
    private final int zzd;

    @SafeParcelable.Field(getter = "getSubscriptionType", id = 5)
    private final int zze;

    Subscription(DataSource dataSource, DataType dataType, long j2, int i2, int i3) {
        this.zza = dataSource;
        this.zzb = dataType;
        this.zzc = j2;
        this.zzd = i2;
        this.zze = i3;
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Subscription)) {
            return false;
        }
        Subscription subscription = (Subscription) obj;
        return Objects.equal(this.zza, subscription.zza) && Objects.equal(this.zzb, subscription.zzb) && this.zzc == subscription.zzc && this.zzd == subscription.zzd && this.zze == subscription.zze;
    }

    @Nullable
    public DataSource getDataSource() {
        return this.zza;
    }

    @Nullable
    public DataType getDataType() {
        return this.zzb;
    }

    public int hashCode() {
        DataSource dataSource = this.zza;
        return Objects.hashCode(dataSource, dataSource, Long.valueOf(this.zzc), Integer.valueOf(this.zzd), Integer.valueOf(this.zze));
    }

    @NonNull
    public String toDebugString() {
        Locale locale = Locale.US;
        DataSource dataSource = this.zza;
        return String.format(locale, "Subscription{%s}, subscriptionType{%d}", dataSource == null ? this.zzb.getName() : dataSource.zzb(), Integer.valueOf(this.zze));
    }

    @NonNull
    public String toString() {
        return Objects.toStringHelper(this).add("dataSource", this.zza).add("dataType", this.zzb).add("samplingIntervalMicros", Long.valueOf(this.zzc)).add("accuracyMode", Integer.valueOf(this.zzd)).add("subscriptionType", Integer.valueOf(this.zze)).toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, getDataSource(), i2, false);
        SafeParcelWriter.writeParcelable(parcel, 2, getDataType(), i2, false);
        SafeParcelWriter.writeLong(parcel, 3, this.zzc);
        SafeParcelWriter.writeInt(parcel, 4, this.zzd);
        SafeParcelWriter.writeInt(parcel, 5, this.zze);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @NonNull
    @ShowFirstParty
    public final DataType zza() {
        DataType dataType = this.zzb;
        return dataType == null ? this.zza.getDataType() : dataType;
    }
}
