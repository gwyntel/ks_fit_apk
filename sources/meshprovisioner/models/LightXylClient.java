package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class LightXylClient extends SigModel {
    public static final Parcelable.Creator<LightXylClient> CREATOR = new Parcelable.Creator<LightXylClient>() { // from class: meshprovisioner.models.LightXylClient.1
        @Override // android.os.Parcelable.Creator
        public LightXylClient createFromParcel(Parcel parcel) {
            return new LightXylClient(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public LightXylClient[] newArray(int i2) {
            return new LightXylClient[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Light XYL Client";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public LightXylClient() {
    }

    public LightXylClient(int i2) {
        super(i2);
    }

    public LightXylClient(Parcel parcel) {
        super(parcel);
    }
}
