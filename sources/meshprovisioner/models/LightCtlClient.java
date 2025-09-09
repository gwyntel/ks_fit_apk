package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class LightCtlClient extends SigModel {
    public static final Parcelable.Creator<LightCtlClient> CREATOR = new Parcelable.Creator<LightCtlClient>() { // from class: meshprovisioner.models.LightCtlClient.1
        @Override // android.os.Parcelable.Creator
        public LightCtlClient createFromParcel(Parcel parcel) {
            return new LightCtlClient(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public LightCtlClient[] newArray(int i2) {
            return new LightCtlClient[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Light Ctl Client";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public LightCtlClient() {
    }

    public LightCtlClient(int i2) {
        super(i2);
    }

    public LightCtlClient(Parcel parcel) {
        super(parcel);
    }
}
