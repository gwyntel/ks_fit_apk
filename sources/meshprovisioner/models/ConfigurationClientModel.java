package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class ConfigurationClientModel extends SigModel {
    public static final Parcelable.Creator<ConfigurationClientModel> CREATOR = new Parcelable.Creator<ConfigurationClientModel>() { // from class: meshprovisioner.models.ConfigurationClientModel.1
        @Override // android.os.Parcelable.Creator
        public ConfigurationClientModel createFromParcel(Parcel parcel) {
            return new ConfigurationClientModel(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public ConfigurationClientModel[] newArray(int i2) {
            return new ConfigurationClientModel[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Configuration Client";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public ConfigurationClientModel() {
    }

    public ConfigurationClientModel(int i2) {
        super(i2);
    }

    public ConfigurationClientModel(Parcel parcel) {
        super(parcel);
    }
}
