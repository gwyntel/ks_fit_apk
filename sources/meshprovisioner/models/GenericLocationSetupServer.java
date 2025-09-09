package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class GenericLocationSetupServer extends SigModel {
    public static final Parcelable.Creator<GenericLocationSetupServer> CREATOR = new Parcelable.Creator<GenericLocationSetupServer>() { // from class: meshprovisioner.models.GenericLocationSetupServer.1
        @Override // android.os.Parcelable.Creator
        public GenericLocationSetupServer createFromParcel(Parcel parcel) {
            return new GenericLocationSetupServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public GenericLocationSetupServer[] newArray(int i2) {
            return new GenericLocationSetupServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Generic Location Setup Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public GenericLocationSetupServer() {
    }

    public GenericLocationSetupServer(int i2) {
        super(i2);
    }

    public GenericLocationSetupServer(Parcel parcel) {
        super(parcel);
    }
}
