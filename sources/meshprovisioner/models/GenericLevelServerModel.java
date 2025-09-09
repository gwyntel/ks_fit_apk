package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class GenericLevelServerModel extends SigModel {
    public static final Parcelable.Creator<GenericLevelServerModel> CREATOR = new Parcelable.Creator<GenericLevelServerModel>() { // from class: meshprovisioner.models.GenericLevelServerModel.1
        @Override // android.os.Parcelable.Creator
        public GenericLevelServerModel createFromParcel(Parcel parcel) {
            return new GenericLevelServerModel(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public GenericLevelServerModel[] newArray(int i2) {
            return new GenericLevelServerModel[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Generic Level Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public GenericLevelServerModel() {
    }

    public GenericLevelServerModel(int i2) {
        super(i2);
    }

    public GenericLevelServerModel(Parcel parcel) {
        super(parcel);
    }
}
