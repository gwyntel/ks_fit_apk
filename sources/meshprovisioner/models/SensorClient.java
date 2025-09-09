package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SensorClient extends SigModel {
    public static final Parcelable.Creator<SensorClient> CREATOR = new Parcelable.Creator<SensorClient>() { // from class: meshprovisioner.models.SensorClient.1
        @Override // android.os.Parcelable.Creator
        public SensorClient createFromParcel(Parcel parcel) {
            return new SensorClient(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public SensorClient[] newArray(int i2) {
            return new SensorClient[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Sensor Client";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public SensorClient() {
    }

    public SensorClient(int i2) {
        super(i2);
    }

    public SensorClient(Parcel parcel) {
        super(parcel);
    }
}
