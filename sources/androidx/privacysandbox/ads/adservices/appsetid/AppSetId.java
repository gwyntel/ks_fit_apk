package androidx.privacysandbox.ads.adservices.appsetid;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u000e\u001a\u00020\u0005H\u0016J\b\u0010\u000f\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0011"}, d2 = {"Landroidx/privacysandbox/ads/adservices/appsetid/AppSetId;", "", "id", "", "scope", "", "(Ljava/lang/String;I)V", "getId", "()Ljava/lang/String;", "getScope", "()I", "equals", "", "other", "hashCode", "toString", "Companion", "ads-adservices_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nAppSetId.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AppSetId.kt\nandroidx/privacysandbox/ads/adservices/appsetid/AppSetId\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,70:1\n1#2:71\n*E\n"})
/* loaded from: classes2.dex */
public final class AppSetId {
    public static final int SCOPE_APP = 1;
    public static final int SCOPE_DEVELOPER = 2;

    @NotNull
    private final String id;
    private final int scope;

    public AppSetId(@NotNull String id, int i2) {
        Intrinsics.checkNotNullParameter(id, "id");
        this.id = id;
        this.scope = i2;
        if (i2 != 1 && i2 != 2) {
            throw new IllegalArgumentException("Scope undefined.".toString());
        }
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AppSetId)) {
            return false;
        }
        AppSetId appSetId = (AppSetId) other;
        return Intrinsics.areEqual(this.id, appSetId.id) && this.scope == appSetId.scope;
    }

    @NotNull
    public final String getId() {
        return this.id;
    }

    public final int getScope() {
        return this.scope;
    }

    public int hashCode() {
        return (this.id.hashCode() * 31) + this.scope;
    }

    @NotNull
    public String toString() {
        return "AppSetId: id=" + this.id + ", scope=" + (this.scope == 1 ? "SCOPE_APP" : "SCOPE_DEVELOPER");
    }
}
