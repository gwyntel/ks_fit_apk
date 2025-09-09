package com.google.android.gms.internal.identity;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ClientIdentity;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.location.LocationRequest;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public final class zzeh implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        long j2 = Long.MAX_VALUE;
        LocationRequest locationRequest = null;
        ArrayList arrayListCreateTypedList = null;
        String strCreateString = null;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 1) {
                locationRequest = (LocationRequest) SafeParcelReader.createParcelable(parcel, header, LocationRequest.CREATOR);
            } else if (fieldId == 5) {
                arrayListCreateTypedList = SafeParcelReader.createTypedList(parcel, header, ClientIdentity.CREATOR);
            } else if (fieldId == 8) {
                z2 = SafeParcelReader.readBoolean(parcel, header);
            } else if (fieldId != 9) {
                switch (fieldId) {
                    case 11:
                        z4 = SafeParcelReader.readBoolean(parcel, header);
                        break;
                    case 12:
                        z5 = SafeParcelReader.readBoolean(parcel, header);
                        break;
                    case 13:
                        strCreateString = SafeParcelReader.createString(parcel, header);
                        break;
                    case 14:
                        j2 = SafeParcelReader.readLong(parcel, header);
                        break;
                    default:
                        SafeParcelReader.skipUnknownField(parcel, header);
                        break;
                }
            } else {
                z3 = SafeParcelReader.readBoolean(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new zzeg(locationRequest, arrayListCreateTypedList, z2, z3, z4, z5, strCreateString, j2);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i2) {
        return new zzeg[i2];
    }
}
