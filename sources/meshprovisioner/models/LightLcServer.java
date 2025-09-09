package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class LightLcServer extends SigModel {
    public static final Parcelable.Creator<LightLcServer> CREATOR = new Parcelable.Creator<LightLcServer>() { // from class: meshprovisioner.models.LightLcServer.1
        @Override // android.os.Parcelable.Creator
        public LightLcServer createFromParcel(Parcel parcel) {
            return new LightLcServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public LightLcServer[] newArray(int i2) {
            return new LightLcServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Light LC Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public LightLcServer() {
    }

    public LightLcServer(int i2) {
        super(i2);
    }

    public LightLcServer(Parcel parcel) {
        super(parcel);
    }
}
