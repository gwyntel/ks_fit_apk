package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class LightCtlSetupServer extends SigModel {
    public static final Parcelable.Creator<LightCtlSetupServer> CREATOR = new Parcelable.Creator<LightCtlSetupServer>() { // from class: meshprovisioner.models.LightCtlSetupServer.1
        @Override // android.os.Parcelable.Creator
        public LightCtlSetupServer createFromParcel(Parcel parcel) {
            return new LightCtlSetupServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public LightCtlSetupServer[] newArray(int i2) {
            return new LightCtlSetupServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Light Ctl Setup Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public LightCtlSetupServer() {
    }

    public LightCtlSetupServer(int i2) {
        super(i2);
    }

    public LightCtlSetupServer(Parcel parcel) {
        super(parcel);
    }
}
