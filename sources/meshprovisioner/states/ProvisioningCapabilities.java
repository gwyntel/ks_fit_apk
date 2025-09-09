package meshprovisioner.states;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public final class ProvisioningCapabilities implements Parcelable {
    public static final Parcelable.Creator<ProvisioningCapabilities> CREATOR = new Parcelable.Creator<ProvisioningCapabilities>() { // from class: meshprovisioner.states.ProvisioningCapabilities.1
        @Override // android.os.Parcelable.Creator
        public ProvisioningCapabilities createFromParcel(Parcel parcel) {
            return new ProvisioningCapabilities(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public ProvisioningCapabilities[] newArray(int i2) {
            return new ProvisioningCapabilities[i2];
        }
    };
    public short algorithm;
    public short inputOOBAction;
    public byte inputOOBSize;
    public byte numberOfElements;
    public short outputOOBAction;
    public byte outputOOBSize;
    public byte publicKeyType;
    public byte[] rawCapabilities;
    public byte staticOOBType;

    public ProvisioningCapabilities() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public short getInputOOBAction() {
        return this.inputOOBAction;
    }

    public byte getInputOOBSize() {
        return this.inputOOBSize;
    }

    public byte getNumberOfElements() {
        return this.numberOfElements;
    }

    public short getOutputOOBAction() {
        return this.outputOOBAction;
    }

    public byte getOutputOOBSize() {
        return this.outputOOBSize;
    }

    public byte getPublicKeyType() {
        return this.publicKeyType;
    }

    public byte[] getRawCapabilities() {
        return this.rawCapabilities;
    }

    public byte getStaticOOBType() {
        return this.staticOOBType;
    }

    public short getSupportedAlgorithm() {
        return this.algorithm;
    }

    public void setAlgorithm(short s2) {
        this.algorithm = s2;
    }

    public void setInputOOBAction(short s2) {
        this.inputOOBAction = s2;
    }

    public void setInputOOBSize(byte b2) {
        this.inputOOBSize = b2;
    }

    public void setNumberOfElements(byte b2) {
        this.numberOfElements = b2;
    }

    public void setOutputOOBAction(short s2) {
        this.outputOOBAction = s2;
    }

    public void setOutputOOBSize(byte b2) {
        this.outputOOBSize = b2;
    }

    public void setPublicKeyType(byte b2) {
        this.publicKeyType = b2;
    }

    public void setRawCapabilities(byte[] bArr) {
        this.rawCapabilities = bArr;
    }

    public void setStaticOOBType(byte b2) {
        this.staticOOBType = b2;
    }

    public void setSupportedAlgorithm(short s2) {
        this.algorithm = s2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeByteArray(this.rawCapabilities);
        parcel.writeByte(this.numberOfElements);
        parcel.writeInt(this.algorithm);
        parcel.writeByte(this.publicKeyType);
        parcel.writeByte(this.staticOOBType);
        parcel.writeByte(this.outputOOBSize);
        parcel.writeInt(this.outputOOBAction);
        parcel.writeByte(this.inputOOBSize);
        parcel.writeInt(this.inputOOBAction);
    }

    public ProvisioningCapabilities(Parcel parcel) {
        this.rawCapabilities = parcel.createByteArray();
        this.numberOfElements = parcel.readByte();
        this.algorithm = (short) parcel.readInt();
        this.publicKeyType = parcel.readByte();
        this.staticOOBType = parcel.readByte();
        this.outputOOBSize = parcel.readByte();
        this.outputOOBAction = (short) parcel.readInt();
        this.inputOOBSize = parcel.readByte();
        this.inputOOBAction = (short) parcel.readInt();
    }
}
