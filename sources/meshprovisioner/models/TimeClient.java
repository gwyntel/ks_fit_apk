package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class TimeClient extends SigModel {
    public static final Parcelable.Creator<TimeClient> CREATOR = new Parcelable.Creator<TimeClient>() { // from class: meshprovisioner.models.TimeClient.1
        @Override // android.os.Parcelable.Creator
        public TimeClient createFromParcel(Parcel parcel) {
            return new TimeClient(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public TimeClient[] newArray(int i2) {
            return new TimeClient[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Time Client";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public TimeClient() {
    }

    public TimeClient(int i2) {
        super(i2);
    }

    public TimeClient(Parcel parcel) {
        super(parcel);
    }
}
