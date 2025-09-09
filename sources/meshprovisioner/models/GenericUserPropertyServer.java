package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class GenericUserPropertyServer extends SigModel {
    public static final Parcelable.Creator<GenericUserPropertyServer> CREATOR = new Parcelable.Creator<GenericUserPropertyServer>() { // from class: meshprovisioner.models.GenericUserPropertyServer.1
        @Override // android.os.Parcelable.Creator
        public GenericUserPropertyServer createFromParcel(Parcel parcel) {
            return new GenericUserPropertyServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public GenericUserPropertyServer[] newArray(int i2) {
            return new GenericUserPropertyServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Generic User Property Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public GenericUserPropertyServer() {
    }

    public GenericUserPropertyServer(int i2) {
        super(i2);
    }

    public GenericUserPropertyServer(Parcel parcel) {
        super(parcel);
    }
}
