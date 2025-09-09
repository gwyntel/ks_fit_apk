package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SchedulerClient extends SigModel {
    public static final Parcelable.Creator<SchedulerClient> CREATOR = new Parcelable.Creator<SchedulerClient>() { // from class: meshprovisioner.models.SchedulerClient.1
        @Override // android.os.Parcelable.Creator
        public SchedulerClient createFromParcel(Parcel parcel) {
            return new SchedulerClient(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public SchedulerClient[] newArray(int i2) {
            return new SchedulerClient[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Scheduler Client";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public SchedulerClient() {
    }

    public SchedulerClient(int i2) {
        super(i2);
    }

    public SchedulerClient(Parcel parcel) {
        super(parcel);
    }
}
