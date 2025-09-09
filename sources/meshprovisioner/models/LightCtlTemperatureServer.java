package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class LightCtlTemperatureServer extends SigModel {
    public static final Parcelable.Creator<LightCtlTemperatureServer> CREATOR = new Parcelable.Creator<LightCtlTemperatureServer>() { // from class: meshprovisioner.models.LightCtlTemperatureServer.1
        @Override // android.os.Parcelable.Creator
        public LightCtlTemperatureServer createFromParcel(Parcel parcel) {
            return new LightCtlTemperatureServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public LightCtlTemperatureServer[] newArray(int i2) {
            return new LightCtlTemperatureServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Light Ctl Temperature Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public LightCtlTemperatureServer() {
    }

    public LightCtlTemperatureServer(int i2) {
        super(i2);
    }

    public LightCtlTemperatureServer(Parcel parcel) {
        super(parcel);
    }
}
