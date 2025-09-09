package aisble.response;

import aisble.callback.PhyCallback;
import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public class PhyResult implements PhyCallback, Parcelable {
    public static final Parcelable.Creator<PhyResult> CREATOR = new Parcelable.Creator<PhyResult>() { // from class: aisble.response.PhyResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhyResult createFromParcel(Parcel parcel) {
            return new PhyResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhyResult[] newArray(int i2) {
            return new PhyResult[i2];
        }
    };
    public BluetoothDevice device;
    public int rxPhy;
    public int txPhy;

    public PhyResult(Parcel parcel) {
        this.device = (BluetoothDevice) parcel.readParcelable(BluetoothDevice.class.getClassLoader());
        this.txPhy = parcel.readInt();
        this.rxPhy = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Nullable
    public BluetoothDevice getBluetoothDevice() {
        return this.device;
    }

    public int getRxPhy() {
        return this.rxPhy;
    }

    public int getTxPhy() {
        return this.txPhy;
    }

    @Override // aisble.callback.PhyCallback
    public void onPhyChanged(@NonNull BluetoothDevice bluetoothDevice, int i2, int i3) {
        this.device = bluetoothDevice;
        this.txPhy = i2;
        this.rxPhy = i3;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeParcelable(this.device, i2);
        parcel.writeInt(this.txPhy);
        parcel.writeInt(this.rxPhy);
    }
}
