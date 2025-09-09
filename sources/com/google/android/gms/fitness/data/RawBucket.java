package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.aliyun.alink.linksdk.tools.ut.AUserTrack;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@KeepName
@SafeParcelable.Class(creator = "RawBucketCreator")
@SafeParcelable.Reserved({7, 1000})
/* loaded from: classes3.dex */
public final class RawBucket extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<RawBucket> CREATOR = new zzy();

    @SafeParcelable.Field(id = 1)
    public final long zza;

    @SafeParcelable.Field(id = 2)
    public final long zzb;

    @Nullable
    @SafeParcelable.Field(id = 3)
    public final Session zzc;

    @SafeParcelable.Field(id = 4)
    public final int zzd;

    @NonNull
    @SafeParcelable.Field(id = 5)
    public final List zze;

    @SafeParcelable.Field(id = 6)
    public final int zzf;

    @SafeParcelable.Constructor
    public RawBucket(@SafeParcelable.Param(id = 1) long j2, @SafeParcelable.Param(id = 2) long j3, @Nullable @SafeParcelable.Param(id = 3) Session session, @SafeParcelable.Param(id = 4) int i2, @NonNull @SafeParcelable.Param(id = 5) List list, @SafeParcelable.Param(id = 6) int i3) {
        this.zza = j2;
        this.zzb = j3;
        this.zzc = session;
        this.zzd = i2;
        this.zze = list;
        this.zzf = i3;
    }

    public final boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RawBucket)) {
            return false;
        }
        RawBucket rawBucket = (RawBucket) obj;
        return this.zza == rawBucket.zza && this.zzb == rawBucket.zzb && this.zzd == rawBucket.zzd && Objects.equal(this.zze, rawBucket.zze) && this.zzf == rawBucket.zzf;
    }

    public final int hashCode() {
        return Objects.hashCode(Long.valueOf(this.zza), Long.valueOf(this.zzb), Integer.valueOf(this.zzf));
    }

    @NonNull
    public final String toString() {
        return Objects.toStringHelper(this).add("startTime", Long.valueOf(this.zza)).add(AUserTrack.UTKEY_END_TIME, Long.valueOf(this.zzb)).add(PushConstants.INTENT_ACTIVITY_NAME, Integer.valueOf(this.zzd)).add("dataSets", this.zze).add("bucketType", Integer.valueOf(this.zzf)).toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeLong(parcel, 1, this.zza);
        SafeParcelWriter.writeLong(parcel, 2, this.zzb);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzc, i2, false);
        SafeParcelWriter.writeInt(parcel, 4, this.zzd);
        SafeParcelWriter.writeTypedList(parcel, 5, this.zze, false);
        SafeParcelWriter.writeInt(parcel, 6, this.zzf);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public RawBucket(@NonNull Bucket bucket, @NonNull List list) {
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        this.zza = bucket.getStartTime(timeUnit);
        this.zzb = bucket.getEndTime(timeUnit);
        this.zzc = bucket.getSession();
        this.zzd = bucket.zza();
        this.zzf = bucket.getBucketType();
        List<DataSet> dataSets = bucket.getDataSets();
        this.zze = new ArrayList(dataSets.size());
        Iterator<DataSet> it = dataSets.iterator();
        while (it.hasNext()) {
            this.zze.add(new RawDataSet(it.next(), list));
        }
    }
}
