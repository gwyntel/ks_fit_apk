package meshprovisioner.utils;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseIntArray;

/* loaded from: classes5.dex */
public class SparseIntArrayParcelable extends SparseIntArray implements Parcelable {
    public static Parcelable.Creator<SparseIntArrayParcelable> CREATOR = new Parcelable.Creator<SparseIntArrayParcelable>() { // from class: meshprovisioner.utils.SparseIntArrayParcelable.1
        @Override // android.os.Parcelable.Creator
        public SparseIntArrayParcelable createFromParcel(Parcel parcel) {
            SparseIntArrayParcelable sparseIntArrayParcelable = new SparseIntArrayParcelable();
            int i2 = parcel.readInt();
            int[] iArr = new int[i2];
            int[] iArr2 = new int[i2];
            parcel.readIntArray(iArr);
            parcel.readIntArray(iArr2);
            for (int i3 = 0; i3 < i2; i3++) {
                sparseIntArrayParcelable.put(iArr[i3], iArr2[i3]);
            }
            return sparseIntArrayParcelable;
        }

        @Override // android.os.Parcelable.Creator
        public SparseIntArrayParcelable[] newArray(int i2) {
            return new SparseIntArrayParcelable[i2];
        }
    };

    public SparseIntArrayParcelable() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        int[] iArr = new int[size()];
        int[] iArr2 = new int[size()];
        for (int i3 = 0; i3 < size(); i3++) {
            iArr[i3] = keyAt(i3);
            iArr2[i3] = valueAt(i3);
        }
        parcel.writeInt(size());
        parcel.writeIntArray(iArr);
        parcel.writeIntArray(iArr2);
    }

    public SparseIntArrayParcelable(SparseIntArray sparseIntArray) {
        for (int i2 = 0; i2 < sparseIntArray.size(); i2++) {
            put(sparseIntArray.keyAt(i2), sparseIntArray.valueAt(i2));
        }
    }
}
