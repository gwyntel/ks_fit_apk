package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.data.zzad;
import com.umeng.analytics.pro.f;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SafeParcelable.Class(creator = "SessionReadResultCreator")
@SafeParcelable.Reserved({4, 1000})
/* loaded from: classes3.dex */
public class SessionReadResult extends AbstractSafeParcelable implements Result {

    @NonNull
    public static final Parcelable.Creator<SessionReadResult> CREATOR = new zzh();

    @SafeParcelable.Field(getter = "getSessions", id = 1)
    private final List zza;

    @SafeParcelable.Field(getter = "getSessionDataSets", id = 2)
    private final List zzb;

    @SafeParcelable.Field(getter = "getStatus", id = 3)
    private final Status zzc;

    @ShowFirstParty
    @SafeParcelable.Constructor
    public SessionReadResult(@NonNull @SafeParcelable.Param(id = 1) List list, @NonNull @SafeParcelable.Param(id = 2) List list2, @NonNull @SafeParcelable.Param(id = 3) Status status) {
        this.zza = list;
        this.zzb = Collections.unmodifiableList(list2);
        this.zzc = status;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SessionReadResult)) {
            return false;
        }
        SessionReadResult sessionReadResult = (SessionReadResult) obj;
        return this.zzc.equals(sessionReadResult.zzc) && Objects.equal(this.zza, sessionReadResult.zza) && Objects.equal(this.zzb, sessionReadResult.zzb);
    }

    @NonNull
    public List<DataSet> getDataSet(@NonNull Session session) {
        Preconditions.checkArgument(this.zza.contains(session), "Attempting to read data for session %s which was not returned", session);
        ArrayList arrayList = new ArrayList();
        for (zzad zzadVar : this.zzb) {
            if (Objects.equal(session, zzadVar.zzb())) {
                arrayList.add(zzadVar.zza());
            }
        }
        return arrayList;
    }

    @NonNull
    public List<Session> getSessions() {
        return this.zza;
    }

    @Override // com.google.android.gms.common.api.Result
    @NonNull
    public Status getStatus() {
        return this.zzc;
    }

    public int hashCode() {
        return Objects.hashCode(this.zzc, this.zza, this.zzb);
    }

    @NonNull
    public String toString() {
        return Objects.toStringHelper(this).add("status", this.zzc).add(f.f21692n, this.zza).add("sessionDataSets", this.zzb).toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 1, getSessions(), false);
        SafeParcelWriter.writeTypedList(parcel, 2, this.zzb, false);
        SafeParcelWriter.writeParcelable(parcel, 3, getStatus(), i2, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @NonNull
    public List<DataSet> getDataSet(@NonNull Session session, @NonNull DataType dataType) {
        Preconditions.checkArgument(this.zza.contains(session), "Attempting to read data for session %s which was not returned", session);
        ArrayList arrayList = new ArrayList();
        for (zzad zzadVar : this.zzb) {
            if (Objects.equal(session, zzadVar.zzb()) && dataType.equals(zzadVar.zza().getDataType())) {
                arrayList.add(zzadVar.zza());
            }
        }
        return arrayList;
    }
}
