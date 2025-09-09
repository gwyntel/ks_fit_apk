package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.fitness.zzco;
import com.google.android.gms.internal.fitness.zzcp;
import java.util.Collections;
import java.util.List;

@SafeParcelable.Class(creator = "StartBleScanRequestCreator")
@SafeParcelable.Reserved({5, 1000})
@Deprecated
/* loaded from: classes3.dex */
public class StartBleScanRequest extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<StartBleScanRequest> CREATOR = new zzba();

    @SafeParcelable.Field(getter = "getDataTypes", id = 1)
    private final List zza;

    @Nullable
    @SafeParcelable.Field(getter = "getBleScanCallbackBinder", id = 2, type = "android.os.IBinder")
    private final zzab zzb;

    @SafeParcelable.Field(getter = "getTimeoutSecs", id = 3)
    private final int zzc;

    @Nullable
    @SafeParcelable.Field(getter = "getCallbackBinder", id = 4, type = "android.os.IBinder")
    private final zzcp zzd;

    @Nullable
    private final BleScanCallback zze;

    public static class Builder {
        private BleScanCallback zzb;
        private DataType[] zza = new DataType[0];
        private int zzc = 10;

        @NonNull
        public StartBleScanRequest build() {
            Preconditions.checkState(this.zzb != null, "Must set BleScanCallback");
            return new StartBleScanRequest(ArrayUtils.toArrayList(this.zza), this.zzb, this.zzc, (zzaz) null);
        }

        @NonNull
        public Builder setBleScanCallback(@NonNull BleScanCallback bleScanCallback) {
            this.zzb = bleScanCallback;
            return this;
        }

        @NonNull
        public Builder setDataTypes(@NonNull DataType... dataTypeArr) {
            this.zza = dataTypeArr;
            return this;
        }

        @NonNull
        public Builder setTimeoutSecs(int i2) {
            Preconditions.checkArgument(i2 > 0, "Stop time must be greater than zero");
            Preconditions.checkArgument(i2 <= 60, "Stop time must be less than 1 minute");
            this.zzc = i2;
            return this;
        }
    }

    StartBleScanRequest(List list, IBinder iBinder, int i2, IBinder iBinder2) {
        zzab zzzVar;
        this.zza = list;
        if (iBinder == null) {
            zzzVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.request.IBleScanCallback");
            zzzVar = iInterfaceQueryLocalInterface instanceof zzab ? (zzab) iInterfaceQueryLocalInterface : new zzz(iBinder);
        }
        this.zzb = zzzVar;
        this.zzc = i2;
        this.zzd = iBinder2 == null ? null : zzco.zzb(iBinder2);
        this.zze = null;
    }

    @NonNull
    public List<DataType> getDataTypes() {
        return Collections.unmodifiableList(this.zza);
    }

    public int getTimeoutSecs() {
        return this.zzc;
    }

    @NonNull
    public String toString() {
        return Objects.toStringHelper(this).add("dataTypes", this.zza).add("timeoutSecs", Integer.valueOf(this.zzc)).toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 1, getDataTypes(), false);
        zzab zzabVar = this.zzb;
        SafeParcelWriter.writeIBinder(parcel, 2, zzabVar == null ? null : zzabVar.asBinder(), false);
        SafeParcelWriter.writeInt(parcel, 3, getTimeoutSecs());
        zzcp zzcpVar = this.zzd;
        SafeParcelWriter.writeIBinder(parcel, 4, zzcpVar != null ? zzcpVar.asBinder() : null, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Nullable
    public final BleScanCallback zza() {
        return this.zze;
    }

    /* synthetic */ StartBleScanRequest(List list, BleScanCallback bleScanCallback, int i2, zzaz zzazVar) {
        this.zza = list;
        this.zzb = null;
        this.zzc = i2;
        this.zzd = null;
        this.zze = bleScanCallback;
    }

    public StartBleScanRequest(List list, @Nullable zzab zzabVar, int i2, @Nullable zzcp zzcpVar) {
        this.zza = list;
        this.zzb = zzabVar;
        this.zzc = i2;
        this.zzd = zzcpVar;
        this.zze = null;
    }
}
