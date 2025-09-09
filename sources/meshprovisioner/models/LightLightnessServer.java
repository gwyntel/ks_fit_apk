package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class LightLightnessServer extends SigModel {
    public static final Parcelable.Creator<LightLightnessServer> CREATOR = new Parcelable.Creator<LightLightnessServer>() { // from class: meshprovisioner.models.LightLightnessServer.1
        @Override // android.os.Parcelable.Creator
        public LightLightnessServer createFromParcel(Parcel parcel) {
            return new LightLightnessServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public LightLightnessServer[] newArray(int i2) {
            return new LightLightnessServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Light Lightness Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public LightLightnessServer() {
    }

    public LightLightnessServer(int i2) {
        super(i2);
    }

    public LightLightnessServer(Parcel parcel) {
        super(parcel);
    }
}
