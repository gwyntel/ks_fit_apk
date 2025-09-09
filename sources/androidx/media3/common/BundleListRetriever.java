package androidx.media3.common;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;

@UnstableApi
/* loaded from: classes.dex */
public final class BundleListRetriever extends Binder {
    private static final int REPLY_BREAK = 2;
    private static final int REPLY_CONTINUE = 1;
    private static final int REPLY_END_OF_LIST = 0;
    private static final int SUGGESTED_MAX_IPC_SIZE;
    private final ImmutableList<Bundle> list;

    static {
        SUGGESTED_MAX_IPC_SIZE = Util.SDK_INT >= 30 ? IBinder.getSuggestedMaxIpcSizeBytes() : 65536;
    }

    public BundleListRetriever(List<Bundle> list) {
        this.list = ImmutableList.copyOf((Collection) list);
    }

    static ImmutableList a(IBinder iBinder) {
        int i2;
        ImmutableList.Builder builder = ImmutableList.builder();
        int i3 = 0;
        int i4 = 1;
        while (i4 != 0) {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInt(i3);
                try {
                    iBinder.transact(1, parcelObtain, parcelObtain2, 0);
                    while (true) {
                        i2 = parcelObtain2.readInt();
                        if (i2 == 1) {
                            builder.add((ImmutableList.Builder) Assertions.checkNotNull(parcelObtain2.readBundle()));
                            i3++;
                        }
                    }
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    i4 = i2;
                } catch (RemoteException e2) {
                    throw new RuntimeException(e2);
                }
            } catch (Throwable th) {
                parcelObtain2.recycle();
                parcelObtain.recycle();
                throw th;
            }
        }
        return builder.build();
    }

    public static ImmutableList<Bundle> getList(IBinder iBinder) {
        return iBinder instanceof BundleListRetriever ? ((BundleListRetriever) iBinder).list : a(iBinder);
    }

    @Override // android.os.Binder
    protected boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) {
        if (i2 != 1) {
            return super.onTransact(i2, parcel, parcel2, i3);
        }
        if (parcel2 == null) {
            return false;
        }
        int size = this.list.size();
        int i4 = parcel.readInt();
        while (i4 < size && parcel2.dataSize() < SUGGESTED_MAX_IPC_SIZE) {
            parcel2.writeInt(1);
            parcel2.writeBundle(this.list.get(i4));
            i4++;
        }
        parcel2.writeInt(i4 < size ? 2 : 0);
        return true;
    }
}
