package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class GenericPowerOnOffServer extends SigModel {
    public static final Parcelable.Creator<GenericPowerOnOffServer> CREATOR = new Parcelable.Creator<GenericPowerOnOffServer>() { // from class: meshprovisioner.models.GenericPowerOnOffServer.1
        @Override // android.os.Parcelable.Creator
        public GenericPowerOnOffServer createFromParcel(Parcel parcel) {
            return new GenericPowerOnOffServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public GenericPowerOnOffServer[] newArray(int i2) {
            return new GenericPowerOnOffServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Generic Power On Off Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public GenericPowerOnOffServer() {
    }

    public GenericPowerOnOffServer(int i2) {
        super(i2);
    }

    public GenericPowerOnOffServer(Parcel parcel) {
        super(parcel);
    }
}
