package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class LightHslHueServer extends SigModel {
    public static final Parcelable.Creator<LightHslHueServer> CREATOR = new Parcelable.Creator<LightHslHueServer>() { // from class: meshprovisioner.models.LightHslHueServer.1
        @Override // android.os.Parcelable.Creator
        public LightHslHueServer createFromParcel(Parcel parcel) {
            return new LightHslHueServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public LightHslHueServer[] newArray(int i2) {
            return new LightHslHueServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Light HSL Hue Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public LightHslHueServer() {
    }

    public LightHslHueServer(int i2) {
        super(i2);
    }

    public LightHslHueServer(Parcel parcel) {
        super(parcel);
    }
}
