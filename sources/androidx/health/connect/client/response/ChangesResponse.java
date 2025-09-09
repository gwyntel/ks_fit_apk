package androidx.health.connect.client.response;

import androidx.health.connect.client.changes.Change;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\u0018\u00002\u00020\u0001B-\b\u0000\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b¢\u0006\u0002\u0010\nR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\t\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0007\u001a\u00020\b8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u000eR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Landroidx/health/connect/client/response/ChangesResponse;", "", "changes", "", "Landroidx/health/connect/client/changes/Change;", "nextChangesToken", "", "hasMore", "", "changesTokenExpired", "(Ljava/util/List;Ljava/lang/String;ZZ)V", "getChanges", "()Ljava/util/List;", "getChangesTokenExpired", "()Z", "getNextChangesToken", "()Ljava/lang/String;", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class ChangesResponse {

    @NotNull
    private final List<Change> changes;
    private final boolean changesTokenExpired;
    private final boolean hasMore;

    @NotNull
    private final String nextChangesToken;

    /* JADX WARN: Multi-variable type inference failed */
    public ChangesResponse(@NotNull List<? extends Change> changes, @NotNull String nextChangesToken, boolean z2, boolean z3) {
        Intrinsics.checkNotNullParameter(changes, "changes");
        Intrinsics.checkNotNullParameter(nextChangesToken, "nextChangesToken");
        this.changes = changes;
        this.nextChangesToken = nextChangesToken;
        this.hasMore = z2;
        this.changesTokenExpired = z3;
    }

    @NotNull
    public final List<Change> getChanges() {
        return this.changes;
    }

    public final boolean getChangesTokenExpired() {
        return this.changesTokenExpired;
    }

    @NotNull
    public final String getNextChangesToken() {
        return this.nextChangesToken;
    }

    @JvmName(name = "hasMore")
    /* renamed from: hasMore, reason: from getter */
    public final boolean getHasMore() {
        return this.hasMore;
    }
}
