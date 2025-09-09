package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class GenericLocationClient extends SigModel {
    public static final Parcelable.Creator<GenericLocationClient> CREATOR = new Parcelable.Creator<GenericLocationClient>() { // from class: meshprovisioner.models.GenericLocationClient.1
        @Override // android.os.Parcelable.Creator
        public GenericLocationClient createFromParcel(Parcel parcel) {
            return new GenericLocationClient(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public GenericLocationClient[] newArray(int i2) {
            return new GenericLocationClient[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Generic Location Client";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public GenericLocationClient() {
    }

    public GenericLocationClient(int i2) {
        super(i2);
    }

    public GenericLocationClient(Parcel parcel) {
        super(parcel);
    }
}
