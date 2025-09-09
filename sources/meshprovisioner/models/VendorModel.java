package meshprovisioner.models;

import a.a.a.a.b.m.a;
import android.os.Parcel;
import android.os.Parcelable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import meshprovisioner.configuration.MeshModel;
import meshprovisioner.utils.CompanyIdentifiers;

/* loaded from: classes5.dex */
public class VendorModel extends MeshModel {
    public static final Parcelable.Creator<VendorModel> CREATOR = new Parcelable.Creator<VendorModel>() { // from class: meshprovisioner.models.VendorModel.1
        @Override // android.os.Parcelable.Creator
        public VendorModel createFromParcel(Parcel parcel) {
            return new VendorModel(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public VendorModel[] newArray(int i2) {
            return new VendorModel[i2];
        }
    };
    public static final String TAG = "VendorModel";
    public short companyIdentifier;
    public String companyName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getCompanyIdentifier() {
        return this.companyIdentifier;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public int getModelId() {
        return this.mModelId;
    }

    @Override // meshprovisioner.configuration.MeshModel
    public String getModelName() {
        return "Vendor Model";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.parcelMeshModel(parcel, i2);
    }

    public VendorModel() {
    }

    public VendorModel(int i2) {
        super(i2);
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN);
        byteBufferOrder.putInt(i2);
        short s2 = byteBufferOrder.getShort(0);
        this.companyIdentifier = s2;
        this.companyName = CompanyIdentifiers.getCompanyName(s2);
        a.a(TAG, "Company name: " + this.companyName);
    }

    public VendorModel(Parcel parcel) {
        super(parcel);
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN);
        byteBufferOrder.putInt(this.mModelId);
        short s2 = byteBufferOrder.getShort(0);
        this.companyIdentifier = s2;
        this.companyName = CompanyIdentifiers.getCompanyName(s2);
    }
}
