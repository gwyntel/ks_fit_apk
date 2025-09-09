package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.identity.zzek;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SafeParcelable.Class(creator = "GeofencingRequestCreator")
@SafeParcelable.Reserved({3, 1000})
/* loaded from: classes3.dex */
public class GeofencingRequest extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<GeofencingRequest> CREATOR = new zzp();
    public static final int INITIAL_TRIGGER_DWELL = 4;
    public static final int INITIAL_TRIGGER_ENTER = 1;
    public static final int INITIAL_TRIGGER_EXIT = 2;

    @SafeParcelable.Field(getter = "getParcelableGeofences", id = 1)
    private final List zza;

    @InitialTrigger
    @SafeParcelable.Field(getter = "getInitialTrigger", id = 2)
    private final int zzb;

    @Nullable
    @SafeParcelable.Field(getter = "getAttributionTag", id = 4)
    private final String zzc;

    public static final class Builder {
        private final List zza = new ArrayList();

        @InitialTrigger
        private int zzb = 5;

        @NonNull
        public Builder addGeofence(@NonNull Geofence geofence) {
            Preconditions.checkArgument(geofence instanceof zzek, "Geofence must be created using Geofence.Builder.");
            this.zza.add((zzek) geofence);
            return this;
        }

        @NonNull
        public Builder addGeofences(@NonNull List<? extends Geofence> list) {
            Iterator<? extends Geofence> it = list.iterator();
            while (it.hasNext()) {
                addGeofence(it.next());
            }
            return this;
        }

        @NonNull
        public GeofencingRequest build() {
            Preconditions.checkArgument(!this.zza.isEmpty(), "No geofence has been added to this request.");
            return new GeofencingRequest(new ArrayList(this.zza), this.zzb, null);
        }

        @NonNull
        public Builder setInitialTrigger(@InitialTrigger int i2) {
            this.zzb = i2 & 7;
            return this;
        }
    }

    public @interface InitialTrigger {
    }

    GeofencingRequest(List list, int i2, String str) {
        this.zza = list;
        this.zzb = i2;
        this.zzc = str;
    }

    @NonNull
    public List<Geofence> getGeofences() {
        return new ArrayList(this.zza);
    }

    @InitialTrigger
    public int getInitialTrigger() {
        return this.zzb;
    }

    @NonNull
    public String toString() {
        String strValueOf = String.valueOf(this.zza);
        int length = strValueOf.length();
        int i2 = this.zzb;
        StringBuilder sb = new StringBuilder(length + 45 + String.valueOf(i2).length() + 1);
        sb.append("GeofencingRequest[geofences=");
        sb.append(strValueOf);
        sb.append(", initialTrigger=");
        sb.append(i2);
        sb.append("]");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        List list = this.zza;
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 1, list, false);
        SafeParcelWriter.writeInt(parcel, 2, getInitialTrigger());
        SafeParcelWriter.writeString(parcel, 4, this.zzc, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
