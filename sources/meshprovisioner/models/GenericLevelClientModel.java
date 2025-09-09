package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class GenericLevelClientModel extends SigModel {
    public static final Parcelable.Creator<GenericLevelClientModel> CREATOR = new Parcelable.Creator<GenericLevelClientModel>() { // from class: meshprovisioner.models.GenericLevelClientModel.1
        @Override // android.os.Parcelable.Creator
        public GenericLevelClientModel createFromParcel(Parcel parcel) {
            return new GenericLevelClientModel(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public GenericLevelClientModel[] newArray(int i2) {
            return new GenericLevelClientModel[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Generic Level Client";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public GenericLevelClientModel() {
    }

    public GenericLevelClientModel(int i2) {
        super(i2);
    }

    public GenericLevelClientModel(Parcel parcel) {
        super(parcel);
    }
}
