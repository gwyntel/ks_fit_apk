package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class GenericDefaultTransitionTimeClient extends SigModel {
    public static final Parcelable.Creator<GenericDefaultTransitionTimeClient> CREATOR = new Parcelable.Creator<GenericDefaultTransitionTimeClient>() { // from class: meshprovisioner.models.GenericDefaultTransitionTimeClient.1
        @Override // android.os.Parcelable.Creator
        public GenericDefaultTransitionTimeClient createFromParcel(Parcel parcel) {
            return new GenericDefaultTransitionTimeClient(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public GenericDefaultTransitionTimeClient[] newArray(int i2) {
            return new GenericDefaultTransitionTimeClient[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Generic Default Transition Timer Client";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public GenericDefaultTransitionTimeClient() {
    }

    public GenericDefaultTransitionTimeClient(int i2) {
        super(i2);
    }

    public GenericDefaultTransitionTimeClient(Parcel parcel) {
        super(parcel);
    }
}
