package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class GenericOnOffClientModel extends SigModel {
    public static final Parcelable.Creator<GenericOnOffClientModel> CREATOR = new Parcelable.Creator<GenericOnOffClientModel>() { // from class: meshprovisioner.models.GenericOnOffClientModel.1
        @Override // android.os.Parcelable.Creator
        public GenericOnOffClientModel createFromParcel(Parcel parcel) {
            return new GenericOnOffClientModel(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public GenericOnOffClientModel[] newArray(int i2) {
            return new GenericOnOffClientModel[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Generic On Off Client";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public GenericOnOffClientModel() {
    }

    public GenericOnOffClientModel(Parcel parcel) {
        super(parcel);
    }

    public GenericOnOffClientModel(int i2) {
        super(i2);
    }
}
