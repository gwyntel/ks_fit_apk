package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.facebook.GraphRequest;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.internal.fitness.zzbs;
import com.google.android.gms.internal.fitness.zzbt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SafeParcelable.Class(creator = "DataTypeCreateRequestCreator")
@SafeParcelable.Reserved({4, 1000})
@Deprecated
/* loaded from: classes3.dex */
public class DataTypeCreateRequest extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<DataTypeCreateRequest> CREATOR = new zzo();

    @SafeParcelable.Field(getter = "getName", id = 1)
    private final String zza;

    @SafeParcelable.Field(getter = "getFields", id = 2)
    private final List zzb;

    @Nullable
    @SafeParcelable.Field(getter = "getCallbackBinder", id = 3, type = "android.os.IBinder")
    private final zzbt zzc;

    public DataTypeCreateRequest(DataTypeCreateRequest dataTypeCreateRequest, zzbt zzbtVar) {
        this(dataTypeCreateRequest.zza, dataTypeCreateRequest.zzb, zzbtVar);
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DataTypeCreateRequest)) {
            return false;
        }
        DataTypeCreateRequest dataTypeCreateRequest = (DataTypeCreateRequest) obj;
        return Objects.equal(this.zza, dataTypeCreateRequest.zza) && Objects.equal(this.zzb, dataTypeCreateRequest.zzb);
    }

    @NonNull
    public List<Field> getFields() {
        return this.zzb;
    }

    @NonNull
    public String getName() {
        return this.zza;
    }

    public int hashCode() {
        return Objects.hashCode(this.zza, this.zzb);
    }

    @NonNull
    public String toString() {
        return Objects.toStringHelper(this).add("name", this.zza).add(GraphRequest.FIELDS_PARAM, this.zzb).toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, getName(), false);
        SafeParcelWriter.writeTypedList(parcel, 2, getFields(), false);
        zzbt zzbtVar = this.zzc;
        SafeParcelWriter.writeIBinder(parcel, 3, zzbtVar == null ? null : zzbtVar.asBinder(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public static class Builder {
        private String zza;
        private final List zzb = new ArrayList();

        @NonNull
        public Builder addField(@NonNull Field field) {
            if (!this.zzb.contains(field)) {
                this.zzb.add(field);
            }
            return this;
        }

        @NonNull
        public DataTypeCreateRequest build() {
            Preconditions.checkState(this.zza != null, "Must set the name");
            Preconditions.checkState(!this.zzb.isEmpty(), "Must specify the data fields");
            return new DataTypeCreateRequest(this.zza, this.zzb, (zzbt) null);
        }

        @NonNull
        public Builder setName(@NonNull String str) {
            this.zza = str;
            return this;
        }

        @NonNull
        public Builder addField(@NonNull String str, int i2) {
            boolean z2 = false;
            if (str != null && !str.isEmpty()) {
                z2 = true;
            }
            Preconditions.checkArgument(z2, "Invalid name specified");
            return addField(new Field(str, i2, null));
        }
    }

    DataTypeCreateRequest(String str, List list, IBinder iBinder) {
        this.zza = str;
        this.zzb = Collections.unmodifiableList(list);
        this.zzc = iBinder == null ? null : zzbs.zzb(iBinder);
    }

    public DataTypeCreateRequest(String str, List list, @Nullable zzbt zzbtVar) {
        this.zza = str;
        this.zzb = Collections.unmodifiableList(list);
        this.zzc = zzbtVar;
    }
}
