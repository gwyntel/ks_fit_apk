package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.fitness.data.Goal;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public final class zzs implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        ArrayList arrayList = new ArrayList();
        Goal.Recurrence recurrence = null;
        Goal.MetricObjective metricObjective = null;
        Goal.DurationObjective durationObjective = null;
        Goal.FrequencyObjective frequencyObjective = null;
        int i2 = 0;
        long j2 = 0;
        long j3 = 0;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(header)) {
                case 1:
                    j3 = SafeParcelReader.readLong(parcel, header);
                    break;
                case 2:
                    j2 = SafeParcelReader.readLong(parcel, header);
                    break;
                case 3:
                    SafeParcelReader.readList(parcel, header, arrayList, zzs.class.getClassLoader());
                    break;
                case 4:
                    recurrence = (Goal.Recurrence) SafeParcelReader.createParcelable(parcel, header, Goal.Recurrence.CREATOR);
                    break;
                case 5:
                    i2 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 6:
                    metricObjective = (Goal.MetricObjective) SafeParcelReader.createParcelable(parcel, header, Goal.MetricObjective.CREATOR);
                    break;
                case 7:
                    durationObjective = (Goal.DurationObjective) SafeParcelReader.createParcelable(parcel, header, Goal.DurationObjective.CREATOR);
                    break;
                case 8:
                    frequencyObjective = (Goal.FrequencyObjective) SafeParcelReader.createParcelable(parcel, header, Goal.FrequencyObjective.CREATOR);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, header);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new Goal(j3, j2, arrayList, recurrence, i2, metricObjective, durationObjective, frequencyObjective);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i2) {
        return new Goal[i2];
    }
}
