package anetwork.channel.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import anet.channel.bytes.ByteArray;
import anet.channel.bytes.a;
import anet.channel.request.BodyEntry;
import anetwork.channel.IBodyHandler;
import anetwork.channel.aidl.ParcelableBodyHandler;
import anetwork.channel.aidl.adapter.ParcelableBodyHandlerWrapper;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public class BodyHandlerEntry implements BodyEntry {
    public static final Parcelable.Creator<BodyHandlerEntry> CREATOR = new Parcelable.Creator<BodyHandlerEntry>() { // from class: anetwork.channel.entity.BodyHandlerEntry.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BodyHandlerEntry createFromParcel(Parcel parcel) {
            BodyHandlerEntry bodyHandlerEntry = new BodyHandlerEntry();
            bodyHandlerEntry.bodyHandler = ParcelableBodyHandler.Stub.asInterface(parcel.readStrongBinder());
            return bodyHandlerEntry;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BodyHandlerEntry[] newArray(int i2) {
            return new BodyHandlerEntry[i2];
        }
    };
    ParcelableBodyHandler bodyHandler;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // anet.channel.request.BodyEntry
    public String getContentType() {
        return null;
    }

    @Override // anet.channel.request.BodyEntry
    public int writeTo(OutputStream outputStream) throws IOException {
        try {
            ByteArray byteArrayA = a.C0009a.f6699a.a(2048);
            int i2 = 0;
            while (!this.bodyHandler.isCompleted()) {
                int i3 = this.bodyHandler.read(byteArrayA.getBuffer());
                outputStream.write(byteArrayA.getBuffer(), 0, i3);
                i2 += i3;
            }
            byteArrayA.recycle();
            return i2;
        } catch (RemoteException e2) {
            throw new IOException("RemoteException", e2);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeStrongInterface(this.bodyHandler);
    }

    public BodyHandlerEntry(IBodyHandler iBodyHandler) {
        this.bodyHandler = null;
        this.bodyHandler = new ParcelableBodyHandlerWrapper(iBodyHandler);
    }

    private BodyHandlerEntry() {
        this.bodyHandler = null;
    }
}
