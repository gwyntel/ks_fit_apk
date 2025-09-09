package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SceneClient extends SigModel {
    public static final Parcelable.Creator<SceneClient> CREATOR = new Parcelable.Creator<SceneClient>() { // from class: meshprovisioner.models.SceneClient.1
        @Override // android.os.Parcelable.Creator
        public SceneClient createFromParcel(Parcel parcel) {
            return new SceneClient(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public SceneClient[] newArray(int i2) {
            return new SceneClient[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Scene Client";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public SceneClient() {
    }

    public SceneClient(int i2) {
        super(i2);
    }

    public SceneClient(Parcel parcel) {
        super(parcel);
    }
}
