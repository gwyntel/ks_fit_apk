package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class GenericOnOffServerModel extends SigModel {
    public static final Parcelable.Creator<GenericOnOffServerModel> CREATOR = new Parcelable.Creator<GenericOnOffServerModel>() { // from class: meshprovisioner.models.GenericOnOffServerModel.1
        @Override // android.os.Parcelable.Creator
        public GenericOnOffServerModel createFromParcel(Parcel parcel) {
            return new GenericOnOffServerModel(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public GenericOnOffServerModel[] newArray(int i2) {
            return new GenericOnOffServerModel[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Generic On Off Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public GenericOnOffServerModel() {
    }

    public GenericOnOffServerModel(int i2) {
        super(i2);
    }

    public GenericOnOffServerModel(Parcel parcel) {
        super(parcel);
    }
}
