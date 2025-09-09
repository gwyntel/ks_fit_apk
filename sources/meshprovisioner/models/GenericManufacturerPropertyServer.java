package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class GenericManufacturerPropertyServer extends SigModel {
    public static final Parcelable.Creator<GenericManufacturerPropertyServer> CREATOR = new Parcelable.Creator<GenericManufacturerPropertyServer>() { // from class: meshprovisioner.models.GenericManufacturerPropertyServer.1
        @Override // android.os.Parcelable.Creator
        public GenericManufacturerPropertyServer createFromParcel(Parcel parcel) {
            return new GenericManufacturerPropertyServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public GenericManufacturerPropertyServer[] newArray(int i2) {
            return new GenericManufacturerPropertyServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Generic Manufacturer Property Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public GenericManufacturerPropertyServer() {
    }

    public GenericManufacturerPropertyServer(int i2) {
        super(i2);
    }

    public GenericManufacturerPropertyServer(Parcel parcel) {
        super(parcel);
    }
}
