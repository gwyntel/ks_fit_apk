package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class GenericDefaultTransitionTimeServer extends SigModel {
    public static final Parcelable.Creator<GenericDefaultTransitionTimeServer> CREATOR = new Parcelable.Creator<GenericDefaultTransitionTimeServer>() { // from class: meshprovisioner.models.GenericDefaultTransitionTimeServer.1
        @Override // android.os.Parcelable.Creator
        public GenericDefaultTransitionTimeServer createFromParcel(Parcel parcel) {
            return new GenericDefaultTransitionTimeServer(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public GenericDefaultTransitionTimeServer[] newArray(int i2) {
            return new GenericDefaultTransitionTimeServer[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Generic Default Transition Timer Server";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public GenericDefaultTransitionTimeServer() {
    }

    public GenericDefaultTransitionTimeServer(int i2) {
        super(i2);
    }

    public GenericDefaultTransitionTimeServer(Parcel parcel) {
        super(parcel);
    }
}
