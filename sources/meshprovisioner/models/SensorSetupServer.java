package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SensorSetupServer extends SigModel {
    public static final Parcelable.Creator<SensorSetupServer> CREATOR = new Parcelable.Creator<SensorSetupServer>() { // from class: meshprovisioner.models.SensorSetupServer.1
        @Override // android.os.Parcelable.Creator
        public SensorSetupServer createFromParcel(Parcel parcel) {
            return new SensorSetupServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public SensorSetupServer[] newArray(int i2) {
            return new SensorSetupServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Sensor Setup Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public SensorSetupServer() {
    }

    public SensorSetupServer(int i2) {
        super(i2);
    }

    public SensorSetupServer(Parcel parcel) {
        super(parcel);
    }
}
