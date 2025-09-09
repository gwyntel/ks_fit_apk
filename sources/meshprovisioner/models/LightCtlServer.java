package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class LightCtlServer extends SigModel {
    public static final Parcelable.Creator<LightCtlServer> CREATOR = new Parcelable.Creator<LightCtlServer>() { // from class: meshprovisioner.models.LightCtlServer.1
        @Override // android.os.Parcelable.Creator
        public LightCtlServer createFromParcel(Parcel parcel) {
            return new LightCtlServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public LightCtlServer[] newArray(int i2) {
            return new LightCtlServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Light Ctl Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public LightCtlServer() {
    }

    public LightCtlServer(int i2) {
        super(i2);
    }

    public LightCtlServer(Parcel parcel) {
        super(parcel);
    }
}
