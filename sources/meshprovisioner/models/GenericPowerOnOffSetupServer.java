package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class GenericPowerOnOffSetupServer extends SigModel {
    public static final Parcelable.Creator<GenericPowerOnOffSetupServer> CREATOR = new Parcelable.Creator<GenericPowerOnOffSetupServer>() { // from class: meshprovisioner.models.GenericPowerOnOffSetupServer.1
        @Override // android.os.Parcelable.Creator
        public GenericPowerOnOffSetupServer createFromParcel(Parcel parcel) {
            return new GenericPowerOnOffSetupServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public GenericPowerOnOffSetupServer[] newArray(int i2) {
            return new GenericPowerOnOffSetupServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Generic Power On Off Setup Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public GenericPowerOnOffSetupServer() {
    }

    public GenericPowerOnOffSetupServer(int i2) {
        super(i2);
    }

    public GenericPowerOnOffSetupServer(Parcel parcel) {
        super(parcel);
    }
}
