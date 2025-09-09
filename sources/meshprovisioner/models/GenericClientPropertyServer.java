package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class GenericClientPropertyServer extends SigModel {
    public static final Parcelable.Creator<GenericClientPropertyServer> CREATOR = new Parcelable.Creator<GenericClientPropertyServer>() { // from class: meshprovisioner.models.GenericClientPropertyServer.1
        @Override // android.os.Parcelable.Creator
        public GenericClientPropertyServer createFromParcel(Parcel parcel) {
            return new GenericClientPropertyServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public GenericClientPropertyServer[] newArray(int i2) {
            return new GenericClientPropertyServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Generic Client Property Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public GenericClientPropertyServer() {
    }

    public GenericClientPropertyServer(int i2) {
        super(i2);
    }

    public GenericClientPropertyServer(Parcel parcel) {
        super(parcel);
    }
}
