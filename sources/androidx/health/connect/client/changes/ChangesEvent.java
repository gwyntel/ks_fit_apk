package androidx.health.connect.client.changes;

import androidx.annotation.RestrictTo;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u001d\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Landroidx/health/connect/client/changes/ChangesEvent;", "", "nextChangesToken", "", "changes", "", "Landroidx/health/connect/client/changes/Change;", "(Ljava/lang/String;Ljava/util/List;)V", "getChanges", "()Ljava/util/List;", "getNextChangesToken", "()Ljava/lang/String;", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public final class ChangesEvent {

    @NotNull
    private final List<Change> changes;

    @NotNull
    private final String nextChangesToken;

    /* JADX WARN: Multi-variable type inference failed */
    public ChangesEvent(@NotNull String nextChangesToken, @NotNull List<? extends Change> changes) {
        Intrinsics.checkNotNullParameter(nextChangesToken, "nextChangesToken");
        Intrinsics.checkNotNullParameter(changes, "changes");
        this.nextChangesToken = nextChangesToken;
        this.changes = changes;
    }

    @NotNull
    public final List<Change> getChanges() {
        return this.changes;
    }

    @NotNull
    public final String getNextChangesToken() {
        return this.nextChangesToken;
    }
}
