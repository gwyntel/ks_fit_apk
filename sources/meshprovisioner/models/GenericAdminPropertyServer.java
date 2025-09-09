package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class GenericAdminPropertyServer extends SigModel {
    public static final Parcelable.Creator<GenericAdminPropertyServer> CREATOR = new Parcelable.Creator<GenericAdminPropertyServer>() { // from class: meshprovisioner.models.GenericAdminPropertyServer.1
        @Override // android.os.Parcelable.Creator
        public GenericAdminPropertyServer createFromParcel(Parcel parcel) {
            return new GenericAdminPropertyServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public GenericAdminPropertyServer[] newArray(int i2) {
            return new GenericAdminPropertyServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Generic Admin Property Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public GenericAdminPropertyServer() {
    }

    public GenericAdminPropertyServer(int i2) {
        super(i2);
    }

    public GenericAdminPropertyServer(Parcel parcel) {
        super(parcel);
    }
}
