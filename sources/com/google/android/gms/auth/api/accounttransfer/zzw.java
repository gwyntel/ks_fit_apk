package com.google.android.gms.auth.api.accounttransfer;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.collection.ArraySet;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.internal.auth.zzbz;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SafeParcelable.Class(creator = "AuthenticatorTransferInfoCreator")
/* loaded from: classes3.dex */
public final class zzw extends zzbz {
    public static final Parcelable.Creator<zzw> CREATOR = new zzx();
    private static final HashMap zzc;

    /* renamed from: a, reason: collision with root package name */
    final Set f12638a;

    /* renamed from: b, reason: collision with root package name */
    final int f12639b;

    @SafeParcelable.Field(getter = "getAccountType", id = 2)
    private String zzd;

    @SafeParcelable.Field(getter = "getStatus", id = 3)
    private int zze;

    @SafeParcelable.Field(getter = "getTransferBytes", id = 4)
    private byte[] zzf;

    @SafeParcelable.Field(getter = "getPendingIntent", id = 5)
    private PendingIntent zzg;

    @SafeParcelable.Field(getter = "getDeviceMetaData", id = 6)
    private DeviceMetaData zzh;

    static {
        HashMap map = new HashMap();
        zzc = map;
        map.put("accountType", FastJsonResponse.Field.forString("accountType", 2));
        map.put("status", FastJsonResponse.Field.forInteger("status", 3));
        map.put("transferBytes", FastJsonResponse.Field.forBase64("transferBytes", 4));
    }

    public zzw() {
        this.f12638a = new ArraySet(3);
        this.f12639b = 1;
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected final Object a(FastJsonResponse.Field field) {
        int safeParcelableFieldId = field.getSafeParcelableFieldId();
        if (safeParcelableFieldId == 1) {
            return Integer.valueOf(this.f12639b);
        }
        if (safeParcelableFieldId == 2) {
            return this.zzd;
        }
        if (safeParcelableFieldId == 3) {
            return Integer.valueOf(this.zze);
        }
        if (safeParcelableFieldId == 4) {
            return this.zzf;
        }
        throw new IllegalStateException("Unknown SafeParcelable id=" + field.getSafeParcelableFieldId());
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected final boolean b(FastJsonResponse.Field field) {
        return this.f12638a.contains(Integer.valueOf(field.getSafeParcelableFieldId()));
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected final void d(FastJsonResponse.Field field, String str, byte[] bArr) {
        int safeParcelableFieldId = field.getSafeParcelableFieldId();
        if (safeParcelableFieldId == 4) {
            this.zzf = bArr;
            this.f12638a.add(Integer.valueOf(safeParcelableFieldId));
        } else {
            throw new IllegalArgumentException("Field with id=" + safeParcelableFieldId + " is not known to be a byte array.");
        }
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected final void e(FastJsonResponse.Field field, String str, int i2) {
        int safeParcelableFieldId = field.getSafeParcelableFieldId();
        if (safeParcelableFieldId == 3) {
            this.zze = i2;
            this.f12638a.add(Integer.valueOf(safeParcelableFieldId));
        } else {
            throw new IllegalArgumentException("Field with id=" + safeParcelableFieldId + " is not known to be an int.");
        }
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected final void g(FastJsonResponse.Field field, String str, String str2) {
        int safeParcelableFieldId = field.getSafeParcelableFieldId();
        if (safeParcelableFieldId != 2) {
            throw new IllegalArgumentException(String.format("Field with id=%d is not known to be a string.", Integer.valueOf(safeParcelableFieldId)));
        }
        this.zzd = str2;
        this.f12638a.add(Integer.valueOf(safeParcelableFieldId));
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public final /* synthetic */ Map getFieldMappings() {
        return zzc;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        Set set = this.f12638a;
        if (set.contains(1)) {
            SafeParcelWriter.writeInt(parcel, 1, this.f12639b);
        }
        if (set.contains(2)) {
            SafeParcelWriter.writeString(parcel, 2, this.zzd, true);
        }
        if (set.contains(3)) {
            SafeParcelWriter.writeInt(parcel, 3, this.zze);
        }
        if (set.contains(4)) {
            SafeParcelWriter.writeByteArray(parcel, 4, this.zzf, true);
        }
        if (set.contains(5)) {
            SafeParcelWriter.writeParcelable(parcel, 5, this.zzg, i2, true);
        }
        if (set.contains(6)) {
            SafeParcelWriter.writeParcelable(parcel, 6, this.zzh, i2, true);
        }
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    zzw(Set set, int i2, String str, int i3, byte[] bArr, PendingIntent pendingIntent, DeviceMetaData deviceMetaData) {
        this.f12638a = set;
        this.f12639b = i2;
        this.zzd = str;
        this.zze = i3;
        this.zzf = bArr;
        this.zzg = pendingIntent;
        this.zzh = deviceMetaData;
    }
}
