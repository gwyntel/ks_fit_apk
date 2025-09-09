package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class TimeServer extends SigModel {
    public static final Parcelable.Creator<TimeServer> CREATOR = new Parcelable.Creator<TimeServer>() { // from class: meshprovisioner.models.TimeServer.1
        @Override // android.os.Parcelable.Creator
        public TimeServer createFromParcel(Parcel parcel) {
            return new TimeServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public TimeServer[] newArray(int i2) {
            return new TimeServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Time Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public TimeServer() {
    }

    public TimeServer(int i2) {
        super(i2);
    }

    public TimeServer(Parcel parcel) {
        super(parcel);
    }
}
