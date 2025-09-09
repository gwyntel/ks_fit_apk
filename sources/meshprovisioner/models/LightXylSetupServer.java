package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class LightXylSetupServer extends SigModel {
    public static final Parcelable.Creator<LightXylSetupServer> CREATOR = new Parcelable.Creator<LightXylSetupServer>() { // from class: meshprovisioner.models.LightXylSetupServer.1
        @Override // android.os.Parcelable.Creator
        public LightXylSetupServer createFromParcel(Parcel parcel) {
            return new LightXylSetupServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public LightXylSetupServer[] newArray(int i2) {
            return new LightXylSetupServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Light XYL Setup Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public LightXylSetupServer() {
    }

    public LightXylSetupServer(int i2) {
        super(i2);
    }

    public LightXylSetupServer(Parcel parcel) {
        super(parcel);
    }
}
