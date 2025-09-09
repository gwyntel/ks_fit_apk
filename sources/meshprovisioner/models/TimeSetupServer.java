package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class TimeSetupServer extends SigModel {
    public static final Parcelable.Creator<TimeSetupServer> CREATOR = new Parcelable.Creator<TimeSetupServer>() { // from class: meshprovisioner.models.TimeSetupServer.1
        @Override // android.os.Parcelable.Creator
        public TimeSetupServer createFromParcel(Parcel parcel) {
            return new TimeSetupServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public TimeSetupServer[] newArray(int i2) {
            return new TimeSetupServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Time Setup Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public TimeSetupServer() {
    }

    public TimeSetupServer(int i2) {
        super(i2);
    }

    public TimeSetupServer(Parcel parcel) {
        super(parcel);
    }
}
