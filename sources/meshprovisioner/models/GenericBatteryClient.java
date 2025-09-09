package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class GenericBatteryClient extends SigModel {
    public static final Parcelable.Creator<GenericBatteryClient> CREATOR = new Parcelable.Creator<GenericBatteryClient>() { // from class: meshprovisioner.models.GenericBatteryClient.1
        @Override // android.os.Parcelable.Creator
        public GenericBatteryClient createFromParcel(Parcel parcel) {
            return new GenericBatteryClient(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public GenericBatteryClient[] newArray(int i2) {
            return new GenericBatteryClient[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Generic Battery Client";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public GenericBatteryClient() {
    }

    public GenericBatteryClient(int i2) {
        super(i2);
    }

    public GenericBatteryClient(Parcel parcel) {
        super(parcel);
    }
}
