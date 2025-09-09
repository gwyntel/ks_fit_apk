package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SchedulerSetupServer extends SigModel {
    public static final Parcelable.Creator<SchedulerSetupServer> CREATOR = new Parcelable.Creator<SchedulerSetupServer>() { // from class: meshprovisioner.models.SchedulerSetupServer.1
        @Override // android.os.Parcelable.Creator
        public SchedulerSetupServer createFromParcel(Parcel parcel) {
            return new SchedulerSetupServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public SchedulerSetupServer[] newArray(int i2) {
            return new SchedulerSetupServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Scheduler Setup Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public SchedulerSetupServer() {
    }

    public SchedulerSetupServer(int i2) {
        super(i2);
    }

    public SchedulerSetupServer(Parcel parcel) {
        super(parcel);
    }
}
