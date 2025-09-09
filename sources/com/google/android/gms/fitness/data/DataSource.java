package com.google.android.gms.fitness.data;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.alipay.sdk.m.u.i;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import java.util.Locale;

@SafeParcelable.Class(creator = "DataSourceCreator")
@SafeParcelable.Reserved({2, 7, 8, 1000})
/* loaded from: classes3.dex */
public class DataSource extends AbstractSafeParcelable {

    @NonNull
    @ShowFirstParty
    public static final Parcelable.Creator<DataSource> CREATOR;

    @NonNull
    public static final String EXTRA_DATA_SOURCE = "vnd.google.fitness.data_source";
    public static final int TYPE_DERIVED = 1;
    public static final int TYPE_RAW = 0;
    private static final String zza;
    private static final String zzb;

    @SafeParcelable.Field(getter = "getDataType", id = 1)
    private final DataType zzc;

    @SafeParcelable.Field(getter = "getType", id = 3)
    private final int zzd;

    @Nullable
    @SafeParcelable.Field(getter = "getDevice", id = 4)
    private final Device zze;

    @Nullable
    @SafeParcelable.Field(getter = "getApplication", id = 5)
    private final zzb zzf;

    @SafeParcelable.Field(getter = "getStreamName", id = 6)
    private final String zzg;
    private final String zzh;

    public static final class Builder {
        private DataType zza;
        private Device zzc;
        private zzb zzd;
        private int zzb = -1;
        private String zze = "";

        @NonNull
        public DataSource build() {
            Preconditions.checkState(this.zza != null, "Must set data type");
            Preconditions.checkState(this.zzb >= 0, "Must set data source type");
            return new DataSource(this.zza, this.zzb, this.zzc, this.zzd, this.zze);
        }

        @NonNull
        public Builder setAppPackageName(@NonNull Context context) {
            setAppPackageName(context.getPackageName());
            return this;
        }

        @NonNull
        public Builder setDataType(@NonNull DataType dataType) {
            this.zza = dataType;
            return this;
        }

        @NonNull
        public Builder setDevice(@NonNull Device device) {
            this.zzc = device;
            return this;
        }

        @NonNull
        public Builder setStreamName(@NonNull String str) {
            Preconditions.checkArgument(str != null, "Must specify a valid stream name");
            this.zze = str;
            return this;
        }

        @NonNull
        public Builder setType(int i2) {
            this.zzb = i2;
            return this;
        }

        @NonNull
        public Builder setAppPackageName(@NonNull String str) {
            this.zzd = zzb.zza(str);
            return this;
        }
    }

    static {
        Locale locale = Locale.ROOT;
        zza = "RAW".toLowerCase(locale);
        zzb = "DERIVED".toLowerCase(locale);
        CREATOR = new zzj();
    }

    @SafeParcelable.Constructor
    public DataSource(@SafeParcelable.Param(id = 1) DataType dataType, @SafeParcelable.Param(id = 3) int i2, @Nullable @SafeParcelable.Param(id = 4) Device device, @Nullable @SafeParcelable.Param(id = 5) zzb zzbVar, @SafeParcelable.Param(id = 6) String str) {
        this.zzc = dataType;
        this.zzd = i2;
        this.zze = device;
        this.zzf = zzbVar;
        this.zzg = str;
        StringBuilder sb = new StringBuilder();
        sb.append(zzc(i2));
        sb.append(":");
        sb.append(dataType.getName());
        if (zzbVar != null) {
            sb.append(":");
            sb.append(zzbVar.zzb());
        }
        if (device != null) {
            sb.append(":");
            sb.append(device.zza());
        }
        if (str != null) {
            sb.append(":");
            sb.append(str);
        }
        this.zzh = sb.toString();
    }

    @Nullable
    public static DataSource extract(@NonNull Intent intent) {
        if (intent == null) {
            return null;
        }
        return (DataSource) SafeParcelableSerializer.deserializeFromIntentExtra(intent, EXTRA_DATA_SOURCE, CREATOR);
    }

    private static String zzc(int i2) {
        return i2 != 0 ? i2 != 1 ? zzb : zzb : zza;
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof DataSource) {
            return this.zzh.equals(((DataSource) obj).zzh);
        }
        return false;
    }

    @Nullable
    public String getAppPackageName() {
        zzb zzbVar = this.zzf;
        if (zzbVar == null) {
            return null;
        }
        return zzbVar.zzb();
    }

    @NonNull
    public DataType getDataType() {
        return this.zzc;
    }

    @Nullable
    public Device getDevice() {
        return this.zze;
    }

    @NonNull
    public String getStreamIdentifier() {
        return this.zzh;
    }

    @NonNull
    public String getStreamName() {
        return this.zzg;
    }

    public int getType() {
        return this.zzd;
    }

    public int hashCode() {
        return this.zzh.hashCode();
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("DataSource{");
        sb.append(zzc(this.zzd));
        if (this.zzf != null) {
            sb.append(":");
            sb.append(this.zzf);
        }
        if (this.zze != null) {
            sb.append(":");
            sb.append(this.zze);
        }
        if (this.zzg != null) {
            sb.append(":");
            sb.append(this.zzg);
        }
        sb.append(":");
        sb.append(this.zzc);
        sb.append(i.f9804d);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, getDataType(), i2, false);
        SafeParcelWriter.writeInt(parcel, 3, getType());
        SafeParcelWriter.writeParcelable(parcel, 4, getDevice(), i2, false);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzf, i2, false);
        SafeParcelWriter.writeString(parcel, 6, getStreamName(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Nullable
    @ShowFirstParty
    public final zzb zza() {
        return this.zzf;
    }

    @NonNull
    @ShowFirstParty
    public final String zzb() {
        String str;
        int i2 = this.zzd;
        String str2 = i2 != 0 ? i2 != 1 ? "?" : "d" : "r";
        String strZzc = this.zzc.zzc();
        zzb zzbVar = this.zzf;
        String strConcat = zzbVar == null ? "" : zzbVar.equals(zzb.zza) ? ":gms" : ":".concat(String.valueOf(this.zzf.zzb()));
        Device device = this.zze;
        if (device != null) {
            str = ":" + device.getModel() + ":" + device.getUid();
        } else {
            str = "";
        }
        String str3 = this.zzg;
        return str2 + ":" + strZzc + strConcat + str + (str3 != null ? ":".concat(str3) : "");
    }
}
