package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.WorkSource;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.internal.identity.ClientIdentity;

/* loaded from: classes3.dex */
public final class zzaf implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        WorkSource workSource = new WorkSource();
        ClientIdentity clientIdentity = null;
        boolean z2 = false;
        int i2 = 0;
        int i3 = 0;
        boolean z3 = false;
        long j2 = -1;
        float f2 = 0.0f;
        int i4 = Integer.MAX_VALUE;
        long j3 = Long.MAX_VALUE;
        long j4 = Long.MAX_VALUE;
        long j5 = 0;
        long j6 = 600000;
        long j7 = 3600000;
        int i5 = 102;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(header)) {
                case 1:
                    i5 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 2:
                    j7 = SafeParcelReader.readLong(parcel, header);
                    break;
                case 3:
                    j6 = SafeParcelReader.readLong(parcel, header);
                    break;
                case 4:
                case 14:
                default:
                    SafeParcelReader.skipUnknownField(parcel, header);
                    break;
                case 5:
                    j3 = SafeParcelReader.readLong(parcel, header);
                    break;
                case 6:
                    i4 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 7:
                    f2 = SafeParcelReader.readFloat(parcel, header);
                    break;
                case 8:
                    j5 = SafeParcelReader.readLong(parcel, header);
                    break;
                case 9:
                    z2 = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 10:
                    j4 = SafeParcelReader.readLong(parcel, header);
                    break;
                case 11:
                    j2 = SafeParcelReader.readLong(parcel, header);
                    break;
                case 12:
                    i2 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 13:
                    i3 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 15:
                    z3 = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 16:
                    workSource = (WorkSource) SafeParcelReader.createParcelable(parcel, header, WorkSource.CREATOR);
                    break;
                case 17:
                    clientIdentity = (ClientIdentity) SafeParcelReader.createParcelable(parcel, header, ClientIdentity.CREATOR);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new LocationRequest(i5, j7, j6, j5, j3, j4, i4, f2, z2, j2, i2, i3, z3, workSource, clientIdentity);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i2) {
        return new LocationRequest[i2];
    }
}
