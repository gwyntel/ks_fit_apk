package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class GenericPropertyClient extends SigModel {
    public static final Parcelable.Creator<GenericPropertyClient> CREATOR = new Parcelable.Creator<GenericPropertyClient>() { // from class: meshprovisioner.models.GenericPropertyClient.1
        @Override // android.os.Parcelable.Creator
        public GenericPropertyClient createFromParcel(Parcel parcel) {
            return new GenericPropertyClient(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public GenericPropertyClient[] newArray(int i2) {
            return new GenericPropertyClient[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Generic User Property Client";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public GenericPropertyClient() {
    }

    public GenericPropertyClient(int i2) {
        super(i2);
    }

    public GenericPropertyClient(Parcel parcel) {
        super(parcel);
    }
}
