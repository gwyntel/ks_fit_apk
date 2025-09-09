package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class GenericPowerLevelServer extends SigModel {
    public static final Parcelable.Creator<GenericPowerLevelServer> CREATOR = new Parcelable.Creator<GenericPowerLevelServer>() { // from class: meshprovisioner.models.GenericPowerLevelServer.1
        @Override // android.os.Parcelable.Creator
        public GenericPowerLevelServer createFromParcel(Parcel parcel) {
            return new GenericPowerLevelServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public GenericPowerLevelServer[] newArray(int i2) {
            return new GenericPowerLevelServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Generic Power Level Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public GenericPowerLevelServer() {
    }

    public GenericPowerLevelServer(int i2) {
        super(i2);
    }

    public GenericPowerLevelServer(Parcel parcel) {
        super(parcel);
    }
}
