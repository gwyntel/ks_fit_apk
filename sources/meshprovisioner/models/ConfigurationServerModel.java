package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class ConfigurationServerModel extends SigModel {
    public static final Parcelable.Creator<ConfigurationServerModel> CREATOR = new Parcelable.Creator<ConfigurationServerModel>() { // from class: meshprovisioner.models.ConfigurationServerModel.1
        @Override // android.os.Parcelable.Creator
        public ConfigurationServerModel createFromParcel(Parcel parcel) {
            return new ConfigurationServerModel(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public ConfigurationServerModel[] newArray(int i2) {
            return new ConfigurationServerModel[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Configuration Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public ConfigurationServerModel() {
    }

    public ConfigurationServerModel(int i2) {
        super(i2);
    }

    public ConfigurationServerModel(Parcel parcel) {
        super(parcel);
    }
}
