package androidx.health.connect.client.records;

import com.aliyun.alink.linksdk.tools.ut.AUserTrack;
import java.time.Instant;
import java.time.ZoneOffset;
import kotlin.Metadata;
import kotlin.PublishedApi;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\ba\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0005R\u0014\u0010\f\u001a\u0004\u0018\u00010\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\tø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000eÀ\u0006\u0001"}, d2 = {"Landroidx/health/connect/client/records/IntervalRecord;", "Landroidx/health/connect/client/records/Record;", AUserTrack.UTKEY_END_TIME, "Ljava/time/Instant;", "getEndTime", "()Ljava/time/Instant;", "endZoneOffset", "Ljava/time/ZoneOffset;", "getEndZoneOffset", "()Ljava/time/ZoneOffset;", "startTime", "getStartTime", "startZoneOffset", "getStartZoneOffset", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@PublishedApi
/* loaded from: classes.dex */
public interface IntervalRecord extends Record {
    @NotNull
    Instant getEndTime();

    @Nullable
    ZoneOffset getEndZoneOffset();

    @NotNull
    Instant getStartTime();

    @Nullable
    ZoneOffset getStartZoneOffset();
}
