package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.aliyun.alink.linksdk.tools.ut.AUserTrack;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fitness.zzfv;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SafeParcelable.Class(creator = "BucketCreator")
@SafeParcelable.Reserved({7, 1000})
/* loaded from: classes3.dex */
public class Bucket extends AbstractSafeParcelable implements ReflectedParcelable {

    @NonNull
    public static final Parcelable.Creator<Bucket> CREATOR = new zze();
    public static final int TYPE_ACTIVITY_SEGMENT = 4;
    public static final int TYPE_ACTIVITY_TYPE = 3;
    public static final int TYPE_SESSION = 2;
    public static final int TYPE_TIME = 1;

    @SafeParcelable.Field(getter = "getStartTimeMillis", id = 1)
    private final long zza;

    @SafeParcelable.Field(getter = "getEndTimeMillis", id = 2)
    private final long zzb;

    @Nullable
    @SafeParcelable.Field(getter = "getSession", id = 3)
    private final Session zzc;

    @SafeParcelable.Field(getter = "getActivityType", id = 4)
    private final int zzd;

    @SafeParcelable.Field(getter = "getDataSets", id = 5)
    private final List zze;

    @SafeParcelable.Field(getter = "getBucketType", id = 6)
    private final int zzf;

    Bucket(long j2, long j3, Session session, int i2, List list, int i3) {
        this.zza = j2;
        this.zzb = j3;
        this.zzc = session;
        this.zzd = i2;
        this.zze = list;
        this.zzf = i3;
    }

    @NonNull
    @ShowFirstParty
    public static String zzb(int i2) {
        return i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 5 ? "bug" : "intervals" : "segment" : "type" : "session" : "time" : "none";
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Bucket)) {
            return false;
        }
        Bucket bucket = (Bucket) obj;
        return this.zza == bucket.zza && this.zzb == bucket.zzb && this.zzd == bucket.zzd && Objects.equal(this.zze, bucket.zze) && this.zzf == bucket.zzf;
    }

    @NonNull
    public String getActivity() {
        return zzfv.zzb(this.zzd);
    }

    public int getBucketType() {
        return this.zzf;
    }

    @Nullable
    public DataSet getDataSet(@NonNull DataType dataType) {
        for (DataSet dataSet : this.zze) {
            if (dataSet.getDataType().equals(dataType)) {
                return dataSet;
            }
        }
        return null;
    }

    @NonNull
    public List<DataSet> getDataSets() {
        return this.zze;
    }

    public long getEndTime(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.zzb, TimeUnit.MILLISECONDS);
    }

    @Nullable
    public Session getSession() {
        return this.zzc;
    }

    public long getStartTime(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.zza, TimeUnit.MILLISECONDS);
    }

    public int hashCode() {
        return Objects.hashCode(Long.valueOf(this.zza), Long.valueOf(this.zzb), Integer.valueOf(this.zzd), Integer.valueOf(this.zzf));
    }

    @NonNull
    public String toString() {
        return Objects.toStringHelper(this).add("startTime", Long.valueOf(this.zza)).add(AUserTrack.UTKEY_END_TIME, Long.valueOf(this.zzb)).add(PushConstants.INTENT_ACTIVITY_NAME, Integer.valueOf(this.zzd)).add("dataSets", this.zze).add("bucketType", zzb(this.zzf)).toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeLong(parcel, 1, this.zza);
        SafeParcelWriter.writeLong(parcel, 2, this.zzb);
        SafeParcelWriter.writeParcelable(parcel, 3, getSession(), i2, false);
        SafeParcelWriter.writeInt(parcel, 4, this.zzd);
        SafeParcelWriter.writeTypedList(parcel, 5, getDataSets(), false);
        SafeParcelWriter.writeInt(parcel, 6, getBucketType());
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @ShowFirstParty
    public final int zza() {
        return this.zzd;
    }

    public final boolean zzc(@NonNull Bucket bucket) {
        return this.zza == bucket.zza && this.zzb == bucket.zzb && this.zzd == bucket.zzd && this.zzf == bucket.zzf;
    }

    public Bucket(@NonNull RawBucket rawBucket, @NonNull List list) {
        long j2 = rawBucket.zza;
        long j3 = rawBucket.zzb;
        Session session = rawBucket.zzc;
        int i2 = rawBucket.zzd;
        List list2 = rawBucket.zze;
        ArrayList arrayList = new ArrayList(list2.size());
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(new DataSet((RawDataSet) it.next(), list));
        }
        this(j2, j3, session, i2, arrayList, rawBucket.zzf);
    }
}
