package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class LightHslSetupServer extends SigModel {
    public static final Parcelable.Creator<LightHslSetupServer> CREATOR = new Parcelable.Creator<LightHslSetupServer>() { // from class: meshprovisioner.models.LightHslSetupServer.1
        @Override // android.os.Parcelable.Creator
        public LightHslSetupServer createFromParcel(Parcel parcel) {
            return new LightHslSetupServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public LightHslSetupServer[] newArray(int i2) {
            return new LightHslSetupServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Light HSL Setup Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public LightHslSetupServer() {
    }

    public LightHslSetupServer(int i2) {
        super(i2);
    }

    public LightHslSetupServer(Parcel parcel) {
        super(parcel);
    }
}
