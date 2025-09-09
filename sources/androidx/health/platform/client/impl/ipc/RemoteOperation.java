package androidx.health.platform.client.impl.ipc;

import android.os.RemoteException;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public interface RemoteOperation<S, R> {
    R execute(S s2) throws RemoteException;
}
