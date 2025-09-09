package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class GenericPowerLevelSetupServer extends SigModel {
    public static final Parcelable.Creator<GenericPowerLevelSetupServer> CREATOR = new Parcelable.Creator<GenericPowerLevelSetupServer>() { // from class: meshprovisioner.models.GenericPowerLevelSetupServer.1
        @Override // android.os.Parcelable.Creator
        public GenericPowerLevelSetupServer createFromParcel(Parcel parcel) {
            return new GenericPowerLevelSetupServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public GenericPowerLevelSetupServer[] newArray(int i2) {
            return new GenericPowerLevelSetupServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Generic Power Level Setup Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public GenericPowerLevelSetupServer() {
    }

    public GenericPowerLevelSetupServer(int i2) {
        super(i2);
    }

    public GenericPowerLevelSetupServer(Parcel parcel) {
        super(parcel);
    }
}
