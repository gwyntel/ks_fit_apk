package androidx.health.connect.client.records;

import androidx.health.connect.client.units.Length;
import androidx.media3.exoplayer.rtsp.SessionDescription;
import com.aliyun.alink.linksdk.tools.ut.AUserTrack;
import java.time.Instant;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0012"}, d2 = {"Landroidx/health/connect/client/records/ExerciseLap;", "", "startTime", "Ljava/time/Instant;", AUserTrack.UTKEY_END_TIME, SessionDescription.ATTR_LENGTH, "Landroidx/health/connect/client/units/Length;", "(Ljava/time/Instant;Ljava/time/Instant;Landroidx/health/connect/client/units/Length;)V", "getEndTime", "()Ljava/time/Instant;", "getLength", "()Landroidx/health/connect/client/units/Length;", "getStartTime", "equals", "", "other", "hashCode", "", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nExerciseLap.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ExerciseLap.kt\nandroidx/health/connect/client/records/ExerciseLap\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,64:1\n1#2:65\n*E\n"})
/* loaded from: classes.dex */
public final class ExerciseLap {

    @NotNull
    private final Instant endTime;

    @Nullable
    private final Length length;

    @NotNull
    private final Instant startTime;

    public ExerciseLap(@NotNull Instant startTime, @NotNull Instant endTime, @Nullable Length length) {
        Intrinsics.checkNotNullParameter(startTime, "startTime");
        Intrinsics.checkNotNullParameter(endTime, "endTime");
        this.startTime = startTime;
        this.endTime = endTime;
        this.length = length;
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("startTime must be before endTime.".toString());
        }
        if (length != null) {
            double meters = length.getMeters();
            if (0.0d > meters || meters > 1000000.0d) {
                throw new IllegalArgumentException("length valid range: 0-1000000.".toString());
            }
        }
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExerciseLap)) {
            return false;
        }
        ExerciseLap exerciseLap = (ExerciseLap) other;
        return Intrinsics.areEqual(this.startTime, exerciseLap.startTime) && Intrinsics.areEqual(this.endTime, exerciseLap.endTime) && Intrinsics.areEqual(this.length, exerciseLap.length);
    }

    @NotNull
    public final Instant getEndTime() {
        return this.endTime;
    }

    @Nullable
    public final Length getLength() {
        return this.length;
    }

    @NotNull
    public final Instant getStartTime() {
        return this.startTime;
    }

    public int hashCode() {
        int iHashCode = ((this.startTime.hashCode() * 31) + this.endTime.hashCode()) * 31;
        Length length = this.length;
        return iHashCode + (length != null ? length.hashCode() : 0);
    }

    public /* synthetic */ ExerciseLap(Instant instant, Instant instant2, Length length, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(instant, instant2, (i2 & 4) != 0 ? null : length);
    }
}
