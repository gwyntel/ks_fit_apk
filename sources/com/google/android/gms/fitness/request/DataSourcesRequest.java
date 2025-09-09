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
import com.google.android.gms.internal.fitness.zzbp;
import com.google.android.gms.internal.fitness.zzbq;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SafeParcelable.Class(creator = "DataSourcesRequestCreator")
@SafeParcelable.Reserved({5, 1000})
/* loaded from: classes3.dex */
public class DataSourcesRequest extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<DataSourcesRequest> CREATOR = new zzn();

    @SafeParcelable.Field(getter = "getDataTypes", id = 1)
    private final List zza;

    @SafeParcelable.Field(getter = "getDataSourceTypes", id = 2)
    private final List zzb;

    @SafeParcelable.Field(getter = "includeDbOnlySources", id = 3)
    private final boolean zzc;

    @Nullable
    @SafeParcelable.Field(getter = "getCallbackBinder", id = 4, type = "android.os.IBinder")
    private final zzbq zzd;

    public static class Builder {
        private List zza = new ArrayList();
        private List zzb = Arrays.asList(0, 1);

        @NonNull
        public DataSourcesRequest build() {
            Preconditions.checkState(!this.zza.isEmpty(), "Must add at least one data type");
            Preconditions.checkState(!this.zzb.isEmpty(), "Must add at least one data source type");
            return new DataSourcesRequest(this.zza, this.zzb, false, (zzbq) null);
        }

        @NonNull
        public Builder setDataSourceTypes(@NonNull int... iArr) {
            this.zzb = new ArrayList();
            for (int i2 : iArr) {
                this.zzb.add(Integer.valueOf(i2));
            }
            return this;
        }

        @NonNull
        public Builder setDataTypes(@NonNull DataType... dataTypeArr) {
            this.zza = Arrays.asList(dataTypeArr);
            return this;
        }
    }

    public DataSourcesRequest(DataSourcesRequest dataSourcesRequest, zzbq zzbqVar) {
        this(dataSourcesRequest.zza, dataSourcesRequest.zzb, dataSourcesRequest.zzc, zzbqVar);
    }

    @NonNull
    public List<DataType> getDataTypes() {
        return this.zza;
    }

    @NonNull
    public String toString() {
        Objects.ToStringHelper toStringHelperAdd = Objects.toStringHelper(this).add("dataTypes", this.zza).add("sourceTypes", this.zzb);
        if (this.zzc) {
            toStringHelperAdd.add("includeDbOnlySources", "true");
        }
        return toStringHelperAdd.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 1, getDataTypes(), false);
        SafeParcelWriter.writeIntegerList(parcel, 2, this.zzb, false);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzc);
        zzbq zzbqVar = this.zzd;
        SafeParcelWriter.writeIBinder(parcel, 4, zzbqVar == null ? null : zzbqVar.asBinder(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    DataSourcesRequest(List list, List list2, boolean z2, IBinder iBinder) {
        this.zza = list;
        this.zzb = list2;
        this.zzc = z2;
        this.zzd = iBinder == null ? null : zzbp.zzc(iBinder);
    }

    public DataSourcesRequest(List list, List list2, boolean z2, @Nullable zzbq zzbqVar) {
        this.zza = list;
        this.zzb = list2;
        this.zzc = z2;
        this.zzd = zzbqVar;
    }
}
