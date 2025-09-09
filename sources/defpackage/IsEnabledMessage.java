package defpackage;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0011\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\b\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0006J\u001a\u0010\t\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003HÆ\u0001¢\u0006\u0002\u0010\nJ\u0013\u0010\u000b\u001a\u00020\u00032\b\u0010\f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\u000e\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0010J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0014"}, d2 = {"LIsEnabledMessage;", "", "enabled", "", "(Ljava/lang/Boolean;)V", "getEnabled", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "component1", "copy", "(Ljava/lang/Boolean;)LIsEnabledMessage;", "equals", "other", "hashCode", "", "toList", "", "toString", "", "Companion", "wakelock_plus_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final /* data */ class IsEnabledMessage {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final Boolean enabled;

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0006¨\u0006\u0007"}, d2 = {"LIsEnabledMessage$Companion;", "", "()V", "fromList", "LIsEnabledMessage;", "pigeonVar_list", "", "wakelock_plus_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final IsEnabledMessage fromList(@NotNull List<? extends Object> pigeonVar_list) {
            Intrinsics.checkNotNullParameter(pigeonVar_list, "pigeonVar_list");
            return new IsEnabledMessage((Boolean) pigeonVar_list.get(0));
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public IsEnabledMessage() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public static /* synthetic */ IsEnabledMessage copy$default(IsEnabledMessage isEnabledMessage, Boolean bool, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            bool = isEnabledMessage.enabled;
        }
        return isEnabledMessage.copy(bool);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final Boolean getEnabled() {
        return this.enabled;
    }

    @NotNull
    public final IsEnabledMessage copy(@Nullable Boolean enabled) {
        return new IsEnabledMessage(enabled);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof IsEnabledMessage) && Intrinsics.areEqual(this.enabled, ((IsEnabledMessage) other).enabled);
    }

    @Nullable
    public final Boolean getEnabled() {
        return this.enabled;
    }

    public int hashCode() {
        Boolean bool = this.enabled;
        if (bool == null) {
            return 0;
        }
        return bool.hashCode();
    }

    @NotNull
    public final List<Object> toList() {
        return CollectionsKt.listOf(this.enabled);
    }

    @NotNull
    public String toString() {
        return "IsEnabledMessage(enabled=" + this.enabled + ")";
    }

    public IsEnabledMessage(@Nullable Boolean bool) {
        this.enabled = bool;
    }

    public /* synthetic */ IsEnabledMessage(Boolean bool, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? null : bool);
    }
}
