package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* loaded from: classes3.dex */
public final class zzm implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        float f2 = 0.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        byte b2 = 0;
        long j2 = 0;
        float[] fArrCreateFloatArray = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId != 1) {
                switch (fieldId) {
                    case 4:
                        f2 = SafeParcelReader.readFloat(parcel, header);
                        break;
                    case 5:
                        f3 = SafeParcelReader.readFloat(parcel, header);
                        break;
                    case 6:
                        j2 = SafeParcelReader.readLong(parcel, header);
                        break;
                    case 7:
                        b2 = SafeParcelReader.readByte(parcel, header);
                        break;
                    case 8:
                        f4 = SafeParcelReader.readFloat(parcel, header);
                        break;
                    case 9:
                        f5 = SafeParcelReader.readFloat(parcel, header);
                        break;
                    default:
                        SafeParcelReader.skipUnknownField(parcel, header);
                        break;
                }
            } else {
                fArrCreateFloatArray = SafeParcelReader.createFloatArray(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new DeviceOrientation(fArrCreateFloatArray, f2, f3, j2, b2, f4, f5);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i2) {
        return new DeviceOrientation[i2];
    }
}
