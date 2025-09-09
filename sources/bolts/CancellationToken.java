package bolts;

import java.util.Locale;
import java.util.concurrent.CancellationException;

/* loaded from: classes2.dex */
public class CancellationToken {
    private final CancellationTokenSource tokenSource;

    CancellationToken(CancellationTokenSource cancellationTokenSource) {
        this.tokenSource = cancellationTokenSource;
    }

    public boolean isCancellationRequested() {
        return this.tokenSource.isCancellationRequested();
    }

    public CancellationTokenRegistration register(Runnable runnable) {
        return this.tokenSource.c(runnable);
    }

    public void throwIfCancellationRequested() throws CancellationException {
        this.tokenSource.e();
    }

    public String toString() {
        return String.format(Locale.US, "%s@%s[cancellationRequested=%s]", getClass().getName(), Integer.toHexString(hashCode()), Boolean.toString(this.tokenSource.isCancellationRequested()));
    }
}
