package androidx.health.platform.client.impl.ipc;

import androidx.annotation.RestrictTo;
import java.util.concurrent.ExecutionException;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class ApiVersionException extends ExecutionException {
    private final int mMinVersion;
    private final int mRemoteVersion;

    public ApiVersionException(int i2, int i3) {
        super("Version requirements for calling the method was not met, remoteVersion: " + i2 + ", minVersion: " + i3);
        this.mRemoteVersion = i2;
        this.mMinVersion = i3;
    }

    public int getMinVersion() {
        return this.mMinVersion;
    }

    public int getRemoteVersion() {
        return this.mRemoteVersion;
    }
}
