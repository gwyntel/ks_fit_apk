package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class LightXylServer extends SigModel {
    public static final Parcelable.Creator<LightXylServer> CREATOR = new Parcelable.Creator<LightXylServer>() { // from class: meshprovisioner.models.LightXylServer.1
        @Override // android.os.Parcelable.Creator
        public LightXylServer createFromParcel(Parcel parcel) {
            return new LightXylServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public LightXylServer[] newArray(int i2) {
            return new LightXylServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Light XYL Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public LightXylServer() {
    }

    public LightXylServer(int i2) {
        super(i2);
    }

    public LightXylServer(Parcel parcel) {
        super(parcel);
    }
}
