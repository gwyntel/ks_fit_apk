package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class GenericLocationServer extends SigModel {
    public static final Parcelable.Creator<GenericLocationServer> CREATOR = new Parcelable.Creator<GenericLocationServer>() { // from class: meshprovisioner.models.GenericLocationServer.1
        @Override // android.os.Parcelable.Creator
        public GenericLocationServer createFromParcel(Parcel parcel) {
            return new GenericLocationServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public GenericLocationServer[] newArray(int i2) {
            return new GenericLocationServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Generic Location Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public GenericLocationServer() {
    }

    public GenericLocationServer(int i2) {
        super(i2);
    }

    public GenericLocationServer(Parcel parcel) {
        super(parcel);
    }
}
