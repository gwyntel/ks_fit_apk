package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class LightLightnessSetupServer extends SigModel {
    public static final Parcelable.Creator<LightLightnessSetupServer> CREATOR = new Parcelable.Creator<LightLightnessSetupServer>() { // from class: meshprovisioner.models.LightLightnessSetupServer.1
        @Override // android.os.Parcelable.Creator
        public LightLightnessSetupServer createFromParcel(Parcel parcel) {
            return new LightLightnessSetupServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public LightLightnessSetupServer[] newArray(int i2) {
            return new LightLightnessSetupServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Light Lightness Setup Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public LightLightnessSetupServer() {
    }

    public LightLightnessSetupServer(int i2) {
        super(i2);
    }

    public LightLightnessSetupServer(Parcel parcel) {
        super(parcel);
    }
}
