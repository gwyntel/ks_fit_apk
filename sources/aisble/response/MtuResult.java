package aisble.response;

import aisble.callback.MtuCallback;
import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public class MtuResult implements MtuCallback, Parcelable {
    public static final Parcelable.Creator<MtuResult> CREATOR = new Parcelable.Creator<MtuResult>() { // from class: aisble.response.MtuResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MtuResult createFromParcel(Parcel parcel) {
            return new MtuResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MtuResult[] newArray(int i2) {
            return new MtuResult[i2];
        }
    };
    public BluetoothDevice device;

    @IntRange(from = 23, to = 517)
    public int mtu;

    public MtuResult(Parcel parcel) {
        this.device = (BluetoothDevice) parcel.readParcelable(BluetoothDevice.class.getClassLoader());
        this.mtu = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Nullable
    public BluetoothDevice getBluetoothDevice() {
        return this.device;
    }

    @IntRange(from = 23, to = 517)
    public int getMtu() {
        return this.mtu;
    }

    @Override // aisble.callback.MtuCallback
    public void onMtuChanged(@NonNull BluetoothDevice bluetoothDevice, @IntRange(from = 23, to = 517) int i2) {
        this.device = bluetoothDevice;
        this.mtu = i2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeParcelable(this.device, i2);
        parcel.writeInt(this.mtu);
    }
}
