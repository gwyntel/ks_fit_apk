package meshprovisioner.models;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class LightLightnessClient extends SigModel {
    public static final Parcelable.Creator<LightLightnessClient> CREATOR = new Parcelable.Creator<LightLightnessClient>() { // from class: meshprovisioner.models.LightLightnessClient.1
        @Override // android.os.Parcelable.Creator
        public LightLightnessClient createFromParcel(Parcel parcel) {
            return new LightLightnessClient(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public LightLightnessClient[] newArray(int i2) {
            return new LightLightnessClient[i2];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Light Lightness Client";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public LightLightnessClient() {
    }

    public LightLightnessClient(int i2) {
        super(i2);
    }

    public LightLightnessClient(Parcel parcel) {
        super(parcel);
    }
}
