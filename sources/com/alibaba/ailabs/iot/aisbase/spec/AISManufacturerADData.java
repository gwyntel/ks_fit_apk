package com.alibaba.ailabs.iot.aisbase.spec;

import android.os.Parcel;
import android.os.Parcelable;
import com.alibaba.ailabs.iot.aisbase.Constants;
import com.alibaba.ailabs.iot.aisbase.Ha;
import com.alibaba.ailabs.iot.aisbase.Utils;
import com.alibaba.ailabs.tg.utils.ConvertUtils;
import com.alibaba.ailabs.tg.utils.LogUtils;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class AISManufacturerADData implements Parcelable {
    public static final Parcelable.Creator<AISManufacturerADData> CREATOR = new Ha();
    public static final String TAG = "AISManufacturerADData";
    public byte mBluetoothSubtype;
    public byte[] mCompanyId;
    public byte[] mExt;
    public byte mFMask;
    public byte[] mMacAddress;
    public byte[] mPId;
    public byte mVersionId;

    public interface BluetoothSubtype {
        public static final byte basic = 8;
        public static final byte beacon = 9;
        public static final byte gma = 10;
    }

    public AISManufacturerADData(Parcel parcel) {
        this.mMacAddress = new byte[6];
        this.mExt = new byte[2];
        this.mCompanyId = parcel.createByteArray();
        this.mBluetoothSubtype = parcel.readByte();
        this.mVersionId = parcel.readByte();
        this.mFMask = parcel.readByte();
        this.mPId = parcel.createByteArray();
        this.mMacAddress = parcel.createByteArray();
        this.mExt = parcel.createByteArray();
    }

    public static boolean isValidAISBluetoothDevice(byte[] bArr) {
        return bArr != null && bArr.length >= 14 && Arrays.equals(Constants.ALIBABA_CID, new byte[]{bArr[1], bArr[0]});
    }

    public static AISManufacturerADData parseFromBytes(byte[] bArr) {
        int i2 = 0;
        if (!isValidAISBluetoothDevice(bArr)) {
            return null;
        }
        AISManufacturerADData aISManufacturerADData = new AISManufacturerADData();
        byte b2 = bArr[2];
        aISManufacturerADData.mVersionId = (byte) (b2 & 15);
        byte b3 = (byte) ((b2 & 240) >> 4);
        aISManufacturerADData.mBluetoothSubtype = b3;
        aISManufacturerADData.mFMask = bArr[3];
        switch (b3) {
            case 8:
            case 10:
                aISManufacturerADData.mPId = new byte[]{bArr[7], bArr[6], bArr[5], bArr[4]};
                aISManufacturerADData.mMacAddress = new byte[6];
                while (i2 < 6) {
                    aISManufacturerADData.mMacAddress[i2] = bArr[13 - i2];
                    i2++;
                }
                break;
            case 9:
                aISManufacturerADData.mPId = new byte[]{0, 0, bArr[5], bArr[4]};
                aISManufacturerADData.mMacAddress = new byte[6];
                while (i2 < 6) {
                    aISManufacturerADData.mMacAddress[i2] = bArr[11 - i2];
                    i2++;
                }
                break;
            default:
                LogUtils.w(TAG, "unknown bluetooth subtype: " + ((int) aISManufacturerADData.mBluetoothSubtype));
                aISManufacturerADData.mPId = new byte[]{bArr[7], bArr[6], bArr[5], bArr[4]};
                aISManufacturerADData.mMacAddress = new byte[6];
                while (i2 < 6) {
                    aISManufacturerADData.mMacAddress[i2] = bArr[13 - i2];
                    i2++;
                }
                break;
        }
        if (bArr.length > 14) {
            aISManufacturerADData.mExt = Arrays.copyOfRange(bArr, 14, bArr.length);
        }
        return aISManufacturerADData;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public byte getBluetoothSubtype() {
        return this.mBluetoothSubtype;
    }

    public byte[] getCompanyId() {
        return this.mCompanyId;
    }

    public byte[] getExt() {
        return this.mExt;
    }

    public byte getFMask() {
        return this.mFMask;
    }

    public byte[] getMacAddress() {
        return this.mMacAddress;
    }

    public byte[] getPId() {
        return this.mPId;
    }

    public String getPidStr() {
        return String.valueOf(Utils.byteArray2Int(this.mPId));
    }

    public byte getVersionId() {
        return this.mVersionId;
    }

    public void setExt(byte[] bArr) {
        this.mExt = bArr;
    }

    public String toString() {
        return "Company Identifiers: 0x" + ConvertUtils.bytes2HexString(this.mCompanyId) + " Product Id: 0x" + ConvertUtils.bytes2HexString(this.mPId) + " Mac address: 0x" + ConvertUtils.bytes2HexString(this.mMacAddress);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeByteArray(this.mCompanyId);
        parcel.writeByte(this.mBluetoothSubtype);
        parcel.writeByte(this.mVersionId);
        parcel.writeByte(this.mFMask);
        parcel.writeByteArray(this.mPId);
        parcel.writeByteArray(this.mMacAddress);
        parcel.writeByteArray(this.mExt);
    }

    public AISManufacturerADData() {
        this.mMacAddress = new byte[6];
        this.mExt = new byte[2];
        this.mCompanyId = Constants.ALIBABA_CID;
    }
}
