package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class LightLcSetupServer extends SigModel {
    public static final Parcelable.Creator<LightLcSetupServer> CREATOR = new Parcelable.Creator<LightLcSetupServer>() { // from class: meshprovisioner.models.LightLcSetupServer.1
        @Override // android.os.Parcelable.Creator
        public LightLcSetupServer createFromParcel(Parcel parcel) {
            return new LightLcSetupServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public LightLcSetupServer[] newArray(int i2) {
            return new LightLcSetupServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Light LC Setup Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public LightLcSetupServer() {
    }

    public LightLcSetupServer(int i2) {
        super(i2);
    }

    public LightLcSetupServer(Parcel parcel) {
        super(parcel);
    }
}
