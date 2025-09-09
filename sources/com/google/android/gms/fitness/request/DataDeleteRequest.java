package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.internal.fitness.zzco;
import com.google.android.gms.internal.fitness.zzcp;
import com.umeng.analytics.pro.f;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SafeParcelable.Class(creator = "DataDeleteRequestCreator")
@SafeParcelable.Reserved({9, 1000})
/* loaded from: classes3.dex */
public class DataDeleteRequest extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<DataDeleteRequest> CREATOR = new zzj();

    @SafeParcelable.Field(getter = "getStartTimeMillis", id = 1)
    private final long zza;

    @SafeParcelable.Field(getter = "getEndTimeMillis", id = 2)
    private final long zzb;

    @SafeParcelable.Field(getter = "getDataSources", id = 3)
    private final List zzc;

    @SafeParcelable.Field(getter = "getDataTypes", id = 4)
    private final List zzd;

    @SafeParcelable.Field(getter = "getSessions", id = 5)
    private final List zze;

    @SafeParcelable.Field(getter = "deleteAllData", id = 6)
    private final boolean zzf;

    @SafeParcelable.Field(getter = "deleteAllSessions", id = 7)
    private final boolean zzg;

    @Nullable
    @SafeParcelable.Field(getter = "getCallbackBinder", id = 8, type = "android.os.IBinder")
    private final zzcp zzh;

    @SafeParcelable.Field(getter = "deleteByTimeRange", id = 10)
    private final boolean zzi;

    @SafeParcelable.Field(getter = "enableLocationCleanup", id = 11)
    private final boolean zzj;

    public static class Builder {
        private long zza;
        private long zzb;
        private final List zzc = new ArrayList();
        private final List zzd = new ArrayList();
        private final List zze = new ArrayList();
        private boolean zzf = false;
        private boolean zzg = false;

        @NonNull
        public Builder addDataSource(@NonNull DataSource dataSource) {
            Preconditions.checkArgument(!this.zzf, "All data is already marked for deletion.  addDataSource() cannot be combined with deleteAllData()");
            Preconditions.checkArgument(dataSource != null, "Must specify a valid data source");
            if (!this.zzc.contains(dataSource)) {
                this.zzc.add(dataSource);
            }
            return this;
        }

        @NonNull
        public Builder addDataType(@NonNull DataType dataType) {
            Preconditions.checkArgument(!this.zzf, "All data is already marked for deletion.  addDataType() cannot be combined with deleteAllData()");
            Preconditions.checkArgument(dataType != null, "Must specify a valid data type");
            if (!this.zzd.contains(dataType)) {
                this.zzd.add(dataType);
            }
            return this;
        }

        @NonNull
        public Builder addSession(@NonNull Session session) {
            Preconditions.checkArgument(!this.zzg, "All sessions already marked for deletion.  addSession() cannot be combined with deleteAllSessions()");
            Preconditions.checkArgument(session != null, "Must specify a valid session");
            Preconditions.checkArgument(session.getEndTime(TimeUnit.MILLISECONDS) > 0, "Cannot delete an ongoing session. Please stop the session prior to deleting it");
            this.zze.add(session);
            return this;
        }

        @NonNull
        public DataDeleteRequest build() {
            long j2 = this.zza;
            Preconditions.checkState(j2 > 0 && this.zzb > j2, "Must specify a valid time interval");
            Preconditions.checkState((this.zzf || !this.zzc.isEmpty() || !this.zzd.isEmpty()) || (this.zzg || !this.zze.isEmpty()), "No data or session marked for deletion");
            if (!this.zze.isEmpty()) {
                for (Session session : this.zze) {
                    TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                    Preconditions.checkState(session.getStartTime(timeUnit) >= this.zza && session.getEndTime(timeUnit) <= this.zzb, "Session %s is outside the time interval [%d, %d]", session, Long.valueOf(this.zza), Long.valueOf(this.zzb));
                }
            }
            return new DataDeleteRequest(this.zza, this.zzb, this.zzc, this.zzd, this.zze, this.zzf, this.zzg, false, false, (zzcp) null);
        }

        @NonNull
        public Builder deleteAllData() {
            Preconditions.checkArgument(this.zzd.isEmpty(), "Specific data type already added for deletion. deleteAllData() will delete all data types and cannot be combined with addDataType()");
            Preconditions.checkArgument(this.zzc.isEmpty(), "Specific data source already added for deletion. deleteAllData() will delete all data sources and cannot be combined with addDataSource()");
            this.zzf = true;
            return this;
        }

        @NonNull
        public Builder deleteAllSessions() {
            Preconditions.checkArgument(this.zze.isEmpty(), "Specific session already added for deletion. deleteAllData() will delete all sessions and cannot be combined with addSession()");
            this.zzg = true;
            return this;
        }

        @NonNull
        public Builder setTimeInterval(long j2, long j3, @NonNull TimeUnit timeUnit) {
            Preconditions.checkArgument(j2 > 0, "Invalid start time: %d", Long.valueOf(j2));
            Preconditions.checkArgument(j3 > j2, "Invalid end time: %d", Long.valueOf(j3));
            this.zza = timeUnit.toMillis(j2);
            this.zzb = timeUnit.toMillis(j3);
            return this;
        }
    }

    DataDeleteRequest(long j2, long j3, List list, List list2, List list3, boolean z2, boolean z3, boolean z4, boolean z5, IBinder iBinder) {
        this.zza = j2;
        this.zzb = j3;
        this.zzc = Collections.unmodifiableList(list);
        this.zzd = Collections.unmodifiableList(list2);
        this.zze = list3;
        this.zzf = z2;
        this.zzg = z3;
        this.zzi = z4;
        this.zzj = z5;
        this.zzh = iBinder == null ? null : zzco.zzb(iBinder);
    }

    public boolean deleteAllData() {
        return this.zzf;
    }

    public boolean deleteAllSessions() {
        return this.zzg;
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DataDeleteRequest)) {
            return false;
        }
        DataDeleteRequest dataDeleteRequest = (DataDeleteRequest) obj;
        return this.zza == dataDeleteRequest.zza && this.zzb == dataDeleteRequest.zzb && Objects.equal(this.zzc, dataDeleteRequest.zzc) && Objects.equal(this.zzd, dataDeleteRequest.zzd) && Objects.equal(this.zze, dataDeleteRequest.zze) && this.zzf == dataDeleteRequest.zzf && this.zzg == dataDeleteRequest.zzg && this.zzi == dataDeleteRequest.zzi && this.zzj == dataDeleteRequest.zzj;
    }

    @NonNull
    public List<DataSource> getDataSources() {
        return this.zzc;
    }

    @NonNull
    public List<DataType> getDataTypes() {
        return this.zzd;
    }

    public long getEndTime(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.zzb, TimeUnit.MILLISECONDS);
    }

    @NonNull
    public List<Session> getSessions() {
        return this.zze;
    }

    public long getStartTime(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.zza, TimeUnit.MILLISECONDS);
    }

    public int hashCode() {
        return Objects.hashCode(Long.valueOf(this.zza), Long.valueOf(this.zzb));
    }

    @NonNull
    public String toString() {
        Objects.ToStringHelper toStringHelperAdd = Objects.toStringHelper(this).add("startTimeMillis", Long.valueOf(this.zza)).add("endTimeMillis", Long.valueOf(this.zzb)).add("dataSources", this.zzc).add("dateTypes", this.zzd).add(f.f21692n, this.zze).add("deleteAllData", Boolean.valueOf(this.zzf)).add("deleteAllSessions", Boolean.valueOf(this.zzg));
        if (this.zzi) {
            toStringHelperAdd.add("deleteByTimeRange", Boolean.TRUE);
        }
        return toStringHelperAdd.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeLong(parcel, 1, this.zza);
        SafeParcelWriter.writeLong(parcel, 2, this.zzb);
        SafeParcelWriter.writeTypedList(parcel, 3, getDataSources(), false);
        SafeParcelWriter.writeTypedList(parcel, 4, getDataTypes(), false);
        SafeParcelWriter.writeTypedList(parcel, 5, getSessions(), false);
        SafeParcelWriter.writeBoolean(parcel, 6, deleteAllData());
        SafeParcelWriter.writeBoolean(parcel, 7, deleteAllSessions());
        zzcp zzcpVar = this.zzh;
        SafeParcelWriter.writeIBinder(parcel, 8, zzcpVar == null ? null : zzcpVar.asBinder(), false);
        SafeParcelWriter.writeBoolean(parcel, 10, this.zzi);
        SafeParcelWriter.writeBoolean(parcel, 11, this.zzj);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public DataDeleteRequest(long j2, long j3, List list, List list2, List list3, boolean z2, boolean z3, boolean z4, boolean z5, @Nullable zzcp zzcpVar) {
        this.zza = j2;
        this.zzb = j3;
        this.zzc = Collections.unmodifiableList(list);
        this.zzd = Collections.unmodifiableList(list2);
        this.zze = list3;
        this.zzf = z2;
        this.zzg = z3;
        this.zzi = z4;
        this.zzj = z5;
        this.zzh = zzcpVar;
    }

    public DataDeleteRequest(DataDeleteRequest dataDeleteRequest, zzcp zzcpVar) {
        this(dataDeleteRequest.zza, dataDeleteRequest.zzb, dataDeleteRequest.zzc, dataDeleteRequest.zzd, dataDeleteRequest.zze, dataDeleteRequest.zzf, dataDeleteRequest.zzg, dataDeleteRequest.zzi, dataDeleteRequest.zzj, zzcpVar);
    }
}
