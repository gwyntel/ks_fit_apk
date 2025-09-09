package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class GenericPowerOnOffClient extends SigModel {
    public static final Parcelable.Creator<GenericPowerOnOffClient> CREATOR = new Parcelable.Creator<GenericPowerOnOffClient>() { // from class: meshprovisioner.models.GenericPowerOnOffClient.1
        @Override // android.os.Parcelable.Creator
        public GenericPowerOnOffClient createFromParcel(Parcel parcel) {
            return new GenericPowerOnOffClient(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public GenericPowerOnOffClient[] newArray(int i2) {
            return new GenericPowerOnOffClient[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Generic Power On Off Client";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public GenericPowerOnOffClient() {
    }

    public GenericPowerOnOffClient(int i2) {
        super(i2);
    }

    public GenericPowerOnOffClient(Parcel parcel) {
        super(parcel);
    }
}
