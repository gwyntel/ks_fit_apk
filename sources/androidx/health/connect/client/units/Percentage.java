package androidx.health.connect.client.units;

import androidx.health.connect.client.records.c;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0011\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0000H\u0096\u0002J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\t\u001a\u0004\u0018\u00010\fH\u0096\u0002J\b\u0010\r\u001a\u00020\bH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Landroidx/health/connect/client/units/Percentage;", "", "value", "", "(D)V", "getValue", "()D", "compareTo", "", "other", "equals", "", "", "hashCode", "toString", "", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class Percentage implements Comparable<Percentage> {
    private final double value;

    public Percentage(double d2) {
        this.value = d2;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof Percentage) && this.value == ((Percentage) other).value;
    }

    public final double getValue() {
        return this.value;
    }

    public int hashCode() {
        return c.a(this.value);
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.value);
        sb.append('%');
        return sb.toString();
    }

    @Override // java.lang.Comparable
    public int compareTo(@NotNull Percentage other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return Double.compare(this.value, other.value);
    }
}
