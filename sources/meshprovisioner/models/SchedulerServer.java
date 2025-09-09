package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SchedulerServer extends SigModel {
    public static final Parcelable.Creator<SchedulerServer> CREATOR = new Parcelable.Creator<SchedulerServer>() { // from class: meshprovisioner.models.SchedulerServer.1
        @Override // android.os.Parcelable.Creator
        public SchedulerServer createFromParcel(Parcel parcel) {
            return new SchedulerServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public SchedulerServer[] newArray(int i2) {
            return new SchedulerServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Scheduler Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public SchedulerServer() {
    }

    public SchedulerServer(int i2) {
        super(i2);
    }

    public SchedulerServer(Parcel parcel) {
        super(parcel);
    }
}
