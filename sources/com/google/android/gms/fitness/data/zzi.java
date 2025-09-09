package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public final class zzi implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        DataSource dataSource = null;
        ArrayList arrayListCreateTypedList = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 1) {
                dataSource = (DataSource) SafeParcelReader.createParcelable(parcel, header, DataSource.CREATOR);
            } else if (fieldId == 1000) {
                i2 = SafeParcelReader.readInt(parcel, header);
            } else if (fieldId == 3) {
                SafeParcelReader.readList(parcel, header, arrayList, zzi.class.getClassLoader());
            } else if (fieldId != 4) {
                SafeParcelReader.skipUnknownField(parcel, header);
            } else {
                arrayListCreateTypedList = SafeParcelReader.createTypedList(parcel, header, DataSource.CREATOR);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new DataSet(i2, dataSource, arrayList, arrayListCreateTypedList);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i2) {
        return new DataSet[i2];
    }
}
