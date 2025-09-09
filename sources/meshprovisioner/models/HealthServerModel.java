package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class HealthServerModel extends SigModel {
    public static final Parcelable.Creator<HealthServerModel> CREATOR = new Parcelable.Creator<HealthServerModel>() { // from class: meshprovisioner.models.HealthServerModel.1
        @Override // android.os.Parcelable.Creator
        public HealthServerModel createFromParcel(Parcel parcel) {
            return new HealthServerModel(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public HealthServerModel[] newArray(int i2) {
            return new HealthServerModel[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Health Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public HealthServerModel() {
    }

    public HealthServerModel(int i2) {
        super(i2);
    }

    public HealthServerModel(Parcel parcel) {
        super(parcel);
    }
}
