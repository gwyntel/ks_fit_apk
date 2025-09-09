package com.google.android.gms.internal.identity;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* loaded from: classes3.dex */
public final class zzel implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i2 = -1;
        int i3 = 0;
        short s2 = 0;
        int i4 = 0;
        long j2 = 0;
        float f2 = 0.0f;
        double d2 = 0.0d;
        double d3 = 0.0d;
        String strCreateString = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(header)) {
                case 1:
                    strCreateString = SafeParcelReader.createString(parcel, header);
                    break;
                case 2:
                    j2 = SafeParcelReader.readLong(parcel, header);
                    break;
                case 3:
                    s2 = SafeParcelReader.readShort(parcel, header);
                    break;
                case 4:
                    d2 = SafeParcelReader.readDouble(parcel, header);
                    break;
                case 5:
                    d3 = SafeParcelReader.readDouble(parcel, header);
                    break;
                case 6:
                    f2 = SafeParcelReader.readFloat(parcel, header);
                    break;
                case 7:
                    i3 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 8:
                    i4 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 9:
                    i2 = SafeParcelReader.readInt(parcel, header);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, header);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new zzek(strCreateString, i3, s2, d2, d3, f2, j2, i4, i2);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i2) {
        return new zzek[i2];
    }
}
