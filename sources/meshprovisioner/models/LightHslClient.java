package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class LightHslClient extends SigModel {
    public static final Parcelable.Creator<LightHslClient> CREATOR = new Parcelable.Creator<LightHslClient>() { // from class: meshprovisioner.models.LightHslClient.1
        @Override // android.os.Parcelable.Creator
        public LightHslClient createFromParcel(Parcel parcel) {
            return new LightHslClient(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public LightHslClient[] newArray(int i2) {
            return new LightHslClient[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Light HSL Client";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public LightHslClient() {
    }

    public LightHslClient(int i2) {
        super(i2);
    }

    public LightHslClient(Parcel parcel) {
        super(parcel);
    }
}
