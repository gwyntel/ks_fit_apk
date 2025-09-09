package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public final class zzm implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        ArrayList arrayListCreateTypedList = null;
        ArrayList arrayListCreateTypedList2 = null;
        ArrayList arrayListCreateTypedList3 = null;
        ArrayList arrayListCreateTypedList4 = null;
        DataSource dataSource = null;
        IBinder iBinder = null;
        ArrayList<Long> arrayListCreateLongList = null;
        ArrayList<Long> arrayListCreateLongList2 = null;
        long j2 = 0;
        long j3 = 0;
        long j4 = 0;
        int i2 = 0;
        int i3 = 0;
        boolean z2 = false;
        boolean z3 = false;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(header)) {
                case 1:
                    arrayListCreateTypedList = SafeParcelReader.createTypedList(parcel, header, DataType.CREATOR);
                    break;
                case 2:
                    arrayListCreateTypedList2 = SafeParcelReader.createTypedList(parcel, header, DataSource.CREATOR);
                    break;
                case 3:
                    j2 = SafeParcelReader.readLong(parcel, header);
                    break;
                case 4:
                    j3 = SafeParcelReader.readLong(parcel, header);
                    break;
                case 5:
                    arrayListCreateTypedList3 = SafeParcelReader.createTypedList(parcel, header, DataType.CREATOR);
                    break;
                case 6:
                    arrayListCreateTypedList4 = SafeParcelReader.createTypedList(parcel, header, DataSource.CREATOR);
                    break;
                case 7:
                    i2 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 8:
                    j4 = SafeParcelReader.readLong(parcel, header);
                    break;
                case 9:
                    dataSource = (DataSource) SafeParcelReader.createParcelable(parcel, header, DataSource.CREATOR);
                    break;
                case 10:
                    i3 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 11:
                case 15:
                case 16:
                case 17:
                default:
                    SafeParcelReader.skipUnknownField(parcel, header);
                    break;
                case 12:
                    z2 = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 13:
                    z3 = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 14:
                    iBinder = SafeParcelReader.readIBinder(parcel, header);
                    break;
                case 18:
                    arrayListCreateLongList = SafeParcelReader.createLongList(parcel, header);
                    break;
                case 19:
                    arrayListCreateLongList2 = SafeParcelReader.createLongList(parcel, header);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new DataReadRequest(arrayListCreateTypedList, arrayListCreateTypedList2, j2, j3, arrayListCreateTypedList3, arrayListCreateTypedList4, i2, j4, dataSource, i3, z2, z3, iBinder, arrayListCreateLongList, arrayListCreateLongList2);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i2) {
        return new DataReadRequest[i2];
    }
}
