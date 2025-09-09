package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* loaded from: classes3.dex */
public final class zzn implements Parcelable.Creator {
    static void a(GetServiceRequest getServiceRequest, Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, getServiceRequest.f12815a);
        SafeParcelWriter.writeInt(parcel, 2, getServiceRequest.f12816b);
        SafeParcelWriter.writeInt(parcel, 3, getServiceRequest.f12817c);
        SafeParcelWriter.writeString(parcel, 4, getServiceRequest.f12818d, false);
        SafeParcelWriter.writeIBinder(parcel, 5, getServiceRequest.f12819e, false);
        SafeParcelWriter.writeTypedArray(parcel, 6, getServiceRequest.f12820f, i2, false);
        SafeParcelWriter.writeBundle(parcel, 7, getServiceRequest.f12821g, false);
        SafeParcelWriter.writeParcelable(parcel, 8, getServiceRequest.f12822h, i2, false);
        SafeParcelWriter.writeTypedArray(parcel, 10, getServiceRequest.f12823i, i2, false);
        SafeParcelWriter.writeTypedArray(parcel, 11, getServiceRequest.f12824j, i2, false);
        SafeParcelWriter.writeBoolean(parcel, 12, getServiceRequest.f12825k);
        SafeParcelWriter.writeInt(parcel, 13, getServiceRequest.f12826l);
        SafeParcelWriter.writeBoolean(parcel, 14, getServiceRequest.f12827m);
        SafeParcelWriter.writeString(parcel, 15, getServiceRequest.zza(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        Scope[] scopeArr = GetServiceRequest.f12813n;
        Bundle bundle = new Bundle();
        Feature[] featureArr = GetServiceRequest.f12814o;
        Feature[] featureArr2 = featureArr;
        String strCreateString = null;
        IBinder iBinder = null;
        Account account = null;
        String strCreateString2 = null;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        boolean z2 = false;
        int i5 = 0;
        boolean z3 = false;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(header)) {
                case 1:
                    i2 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 2:
                    i3 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 3:
                    i4 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 4:
                    strCreateString = SafeParcelReader.createString(parcel, header);
                    break;
                case 5:
                    iBinder = SafeParcelReader.readIBinder(parcel, header);
                    break;
                case 6:
                    scopeArr = (Scope[]) SafeParcelReader.createTypedArray(parcel, header, Scope.CREATOR);
                    break;
                case 7:
                    bundle = SafeParcelReader.createBundle(parcel, header);
                    break;
                case 8:
                    account = (Account) SafeParcelReader.createParcelable(parcel, header, Account.CREATOR);
                    break;
                case 9:
                default:
                    SafeParcelReader.skipUnknownField(parcel, header);
                    break;
                case 10:
                    featureArr = (Feature[]) SafeParcelReader.createTypedArray(parcel, header, Feature.CREATOR);
                    break;
                case 11:
                    featureArr2 = (Feature[]) SafeParcelReader.createTypedArray(parcel, header, Feature.CREATOR);
                    break;
                case 12:
                    z2 = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 13:
                    i5 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 14:
                    z3 = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 15:
                    strCreateString2 = SafeParcelReader.createString(parcel, header);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new GetServiceRequest(i2, i3, i4, strCreateString, iBinder, scopeArr, bundle, account, featureArr, featureArr2, z2, i5, z3, strCreateString2);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i2) {
        return new GetServiceRequest[i2];
    }
}
