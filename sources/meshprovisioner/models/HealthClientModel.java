package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class HealthClientModel extends SigModel {
    public static final Parcelable.Creator<HealthClientModel> CREATOR = new Parcelable.Creator<HealthClientModel>() { // from class: meshprovisioner.models.HealthClientModel.1
        @Override // android.os.Parcelable.Creator
        public HealthClientModel createFromParcel(Parcel parcel) {
            return new HealthClientModel(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public HealthClientModel[] newArray(int i2) {
            return new HealthClientModel[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Health Client";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public HealthClientModel() {
    }

    public HealthClientModel(int i2) {
        super(i2);
    }

    public HealthClientModel(Parcel parcel) {
        super(parcel);
    }
}
