package androidx.health.platform.client.impl.ipc;

import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class ClientConfiguration {
    private final String mApiClientName;
    private final String mBindAction;
    private final String mServicePackageName;

    public ClientConfiguration(String str, String str2, String str3) {
        this.mServicePackageName = str2;
        this.mBindAction = str3;
        this.mApiClientName = str;
    }

    public String getApiClientName() {
        return this.mApiClientName;
    }

    public String getBindAction() {
        return this.mBindAction;
    }

    public String getServicePackageName() {
        return this.mServicePackageName;
    }
}
