package androidx.health.platform.client.impl.ipc;

import android.os.IBinder;
import android.os.RemoteException;
import androidx.annotation.RestrictTo;
import com.google.common.util.concurrent.SettableFuture;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public interface ServiceOperation<R> {
    void execute(IBinder iBinder, SettableFuture<R> settableFuture) throws RemoteException;
}
