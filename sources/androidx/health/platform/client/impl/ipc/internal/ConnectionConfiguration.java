package androidx.health.platform.client.impl.ipc.internal;

import androidx.annotation.RestrictTo;
import com.google.common.base.Preconditions;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public final class ConnectionConfiguration {
    private final String mBindAction;
    private final String mClientName;
    private final String mPackageName;
    private final QueueOperation mRefreshVersionOperation;

    public ConnectionConfiguration(String str, String str2, String str3, QueueOperation queueOperation) {
        this.mPackageName = (String) Preconditions.checkNotNull(str);
        this.mClientName = (String) Preconditions.checkNotNull(str2);
        this.mBindAction = (String) Preconditions.checkNotNull(str3);
        this.mRefreshVersionOperation = (QueueOperation) Preconditions.checkNotNull(queueOperation);
    }

    String a() {
        return this.mBindAction;
    }

    String b() {
        return this.mClientName;
    }

    String c() {
        return String.format("%s#%s#%s", this.mClientName, this.mPackageName, this.mBindAction);
    }

    String d() {
        return this.mPackageName;
    }

    QueueOperation e() {
        return this.mRefreshVersionOperation;
    }
}
