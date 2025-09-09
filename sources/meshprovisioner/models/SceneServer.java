package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SceneServer extends SigModel {
    public static final Parcelable.Creator<SceneServer> CREATOR = new Parcelable.Creator<SceneServer>() { // from class: meshprovisioner.models.SceneServer.1
        @Override // android.os.Parcelable.Creator
        public SceneServer createFromParcel(Parcel parcel) {
            return new SceneServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public SceneServer[] newArray(int i2) {
            return new SceneServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Scene Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public SceneServer() {
    }

    public SceneServer(int i2) {
        super(i2);
    }

    public SceneServer(Parcel parcel) {
        super(parcel);
    }
}
