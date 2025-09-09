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
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.fitness.zzbv;
import com.google.android.gms.internal.fitness.zzbw;
import com.google.android.gms.internal.fitness.zzfv;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SafeParcelable.Class(creator = "GoalsReadRequestCreator")
@SafeParcelable.Reserved({1000})
/* loaded from: classes3.dex */
public class GoalsReadRequest extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<GoalsReadRequest> CREATOR = new zzy();

    @Nullable
    @SafeParcelable.Field(getter = "getCallbackBinder", id = 1, type = "android.os.IBinder")
    private final zzbw zza;

    @SafeParcelable.Field(getter = "getDataTypes", id = 2, type = "java.util.List")
    private final List zzb;

    @SafeParcelable.Field(getter = "getObjectiveTypeList", id = 3, type = "java.util.List")
    private final List zzc;

    @SafeParcelable.Field(getter = "getActivities", id = 4, type = "java.util.List")
    private final List zzd;

    public static class Builder {
        private final List zza = new ArrayList();
        private final List zzb = new ArrayList();
        private final List zzc = new ArrayList();

        @NonNull
        public Builder addActivity(@NonNull String str) {
            int iZza = zzfv.zza(str);
            Preconditions.checkState(iZza != 4, "Attempting to add an unknown activity");
            com.google.android.gms.internal.fitness.zzd.zza(Integer.valueOf(iZza), this.zzc);
            return this;
        }

        @NonNull
        public Builder addDataType(@NonNull DataType dataType) {
            Preconditions.checkNotNull(dataType, "Attempting to use a null data type");
            if (!this.zza.contains(dataType)) {
                this.zza.add(dataType);
            }
            return this;
        }

        @NonNull
        public Builder addObjectiveType(int i2) {
            boolean z2 = true;
            if (i2 != 1 && i2 != 2) {
                if (i2 == 3) {
                    i2 = 3;
                } else {
                    z2 = false;
                }
            }
            Preconditions.checkState(z2, "Attempting to add an invalid objective type");
            List list = this.zzb;
            Integer numValueOf = Integer.valueOf(i2);
            if (!list.contains(numValueOf)) {
                this.zzb.add(numValueOf);
            }
            return this;
        }

        @NonNull
        public GoalsReadRequest build() {
            Preconditions.checkState(!this.zza.isEmpty(), "At least one data type should be specified.");
            return new GoalsReadRequest(this);
        }
    }

    GoalsReadRequest(IBinder iBinder, List list, List list2, List list3) {
        this.zza = iBinder == null ? null : zzbv.zzb(iBinder);
        this.zzb = list;
        this.zzc = list2;
        this.zzd = list3;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GoalsReadRequest)) {
            return false;
        }
        GoalsReadRequest goalsReadRequest = (GoalsReadRequest) obj;
        return Objects.equal(this.zzb, goalsReadRequest.zzb) && Objects.equal(this.zzc, goalsReadRequest.zzc) && Objects.equal(this.zzd, goalsReadRequest.zzd);
    }

    @Nullable
    public List<String> getActivityNames() {
        if (this.zzd.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = this.zzd.iterator();
        while (it.hasNext()) {
            arrayList.add(zzfv.zzb(((Integer) it.next()).intValue()));
        }
        return arrayList;
    }

    @NonNull
    public List<DataType> getDataTypes() {
        return this.zzb;
    }

    @Nullable
    public List<Integer> getObjectiveTypes() {
        if (this.zzc.isEmpty()) {
            return null;
        }
        return this.zzc;
    }

    public int hashCode() {
        return Objects.hashCode(this.zzb, this.zzc, getActivityNames());
    }

    @NonNull
    public String toString() {
        return Objects.toStringHelper(this).add("dataTypes", this.zzb).add("objectiveTypes", this.zzc).add("activities", getActivityNames()).toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        zzbw zzbwVar = this.zza;
        SafeParcelWriter.writeIBinder(parcel, 1, zzbwVar == null ? null : zzbwVar.asBinder(), false);
        SafeParcelWriter.writeList(parcel, 2, getDataTypes(), false);
        SafeParcelWriter.writeList(parcel, 3, this.zzc, false);
        SafeParcelWriter.writeList(parcel, 4, this.zzd, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.os.IBinder] */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3 */
    private GoalsReadRequest(@Nullable zzbw zzbwVar, List list, List list2, List list3) {
        this((IBinder) (zzbwVar == null ? 0 : zzbwVar), list, list2, list3);
    }

    public GoalsReadRequest(GoalsReadRequest goalsReadRequest, zzbw zzbwVar) {
        this(zzbwVar, goalsReadRequest.getDataTypes(), goalsReadRequest.zzc, goalsReadRequest.zzd);
    }
}
