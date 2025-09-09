package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* loaded from: classes3.dex */
public final class zzag implements Parcelable.Creator<zzad> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzad createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        String strCreateString = null;
        String strCreateString2 = null;
        zzmz zzmzVar = null;
        String strCreateString3 = null;
        zzbg zzbgVar = null;
        zzbg zzbgVar2 = null;
        zzbg zzbgVar3 = null;
        long j2 = 0;
        long j3 = 0;
        long j4 = 0;
        boolean z2 = false;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(header)) {
                case 2:
                    strCreateString = SafeParcelReader.createString(parcel, header);
                    break;
                case 3:
                    strCreateString2 = SafeParcelReader.createString(parcel, header);
                    break;
                case 4:
                    zzmzVar = (zzmz) SafeParcelReader.createParcelable(parcel, header, zzmz.CREATOR);
                    break;
                case 5:
                    j2 = SafeParcelReader.readLong(parcel, header);
                    break;
                case 6:
                    z2 = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 7:
                    strCreateString3 = SafeParcelReader.createString(parcel, header);
                    break;
                case 8:
                    zzbgVar = (zzbg) SafeParcelReader.createParcelable(parcel, header, zzbg.CREATOR);
                    break;
                case 9:
                    j3 = SafeParcelReader.readLong(parcel, header);
                    break;
                case 10:
                    zzbgVar2 = (zzbg) SafeParcelReader.createParcelable(parcel, header, zzbg.CREATOR);
                    break;
                case 11:
                    j4 = SafeParcelReader.readLong(parcel, header);
                    break;
                case 12:
                    zzbgVar3 = (zzbg) SafeParcelReader.createParcelable(parcel, header, zzbg.CREATOR);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, header);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new zzad(strCreateString, strCreateString2, zzmzVar, j2, z2, strCreateString3, zzbgVar, j3, zzbgVar2, j4, zzbgVar3);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzad[] newArray(int i2) {
        return new zzad[i2];
    }
}
