package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SceneSetupServer extends SigModel {
    public static final Parcelable.Creator<SceneSetupServer> CREATOR = new Parcelable.Creator<SceneSetupServer>() { // from class: meshprovisioner.models.SceneSetupServer.1
        @Override // android.os.Parcelable.Creator
        public SceneSetupServer createFromParcel(Parcel parcel) {
            return new SceneSetupServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public SceneSetupServer[] newArray(int i2) {
            return new SceneSetupServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Scene Setup Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public SceneSetupServer() {
    }

    public SceneSetupServer(int i2) {
        super(i2);
    }

    public SceneSetupServer(Parcel parcel) {
        super(parcel);
    }
}
