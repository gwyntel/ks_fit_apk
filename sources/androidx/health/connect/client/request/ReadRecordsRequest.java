package androidx.health.connect.client.request;

import androidx.exifinterface.media.ExifInterface;
import androidx.health.connect.client.records.Record;
import androidx.health.connect.client.records.metadata.DataOrigin;
import androidx.health.connect.client.time.TimeRangeFilter;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KClass;
import m.c;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0011\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003BK\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\u0002\u0010\u0011J\u0013\u0010\u001e\u001a\u00020\f2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0096\u0002J\b\u0010 \u001a\u00020\u000eH\u0016R\u0014\u0010\u000b\u001a\u00020\fX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\r\u001a\u00020\u000eX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0014\u0010\u0006\u001a\u00020\u0007X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001d¨\u0006!"}, d2 = {"Landroidx/health/connect/client/request/ReadRecordsRequest;", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/health/connect/client/records/Record;", "", "recordType", "Lkotlin/reflect/KClass;", "timeRangeFilter", "Landroidx/health/connect/client/time/TimeRangeFilter;", "dataOriginFilter", "", "Landroidx/health/connect/client/records/metadata/DataOrigin;", "ascendingOrder", "", AlinkConstants.KEY_PAGE_SIZE, "", "pageToken", "", "(Lkotlin/reflect/KClass;Landroidx/health/connect/client/time/TimeRangeFilter;Ljava/util/Set;ZILjava/lang/String;)V", "getAscendingOrder$connect_client_release", "()Z", "getDataOriginFilter$connect_client_release", "()Ljava/util/Set;", "getPageSize$connect_client_release", "()I", "getPageToken$connect_client_release", "()Ljava/lang/String;", "getRecordType$connect_client_release", "()Lkotlin/reflect/KClass;", "getTimeRangeFilter$connect_client_release", "()Landroidx/health/connect/client/time/TimeRangeFilter;", "equals", "other", "hashCode", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nReadRecordsRequest.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReadRecordsRequest.kt\nandroidx/health/connect/client/request/ReadRecordsRequest\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,106:1\n1#2:107\n*E\n"})
/* loaded from: classes.dex */
public final class ReadRecordsRequest<T extends Record> {
    private final boolean ascendingOrder;

    @NotNull
    private final Set<DataOrigin> dataOriginFilter;
    private final int pageSize;

    @Nullable
    private final String pageToken;

    @NotNull
    private final KClass<T> recordType;

    @NotNull
    private final TimeRangeFilter timeRangeFilter;

    public ReadRecordsRequest(@NotNull KClass<T> recordType, @NotNull TimeRangeFilter timeRangeFilter, @NotNull Set<DataOrigin> dataOriginFilter, boolean z2, int i2, @Nullable String str) {
        Intrinsics.checkNotNullParameter(recordType, "recordType");
        Intrinsics.checkNotNullParameter(timeRangeFilter, "timeRangeFilter");
        Intrinsics.checkNotNullParameter(dataOriginFilter, "dataOriginFilter");
        this.recordType = recordType;
        this.timeRangeFilter = timeRangeFilter;
        this.dataOriginFilter = dataOriginFilter;
        this.ascendingOrder = z2;
        this.pageSize = i2;
        this.pageToken = str;
        if (i2 <= 0) {
            throw new IllegalArgumentException("pageSize must be positive.".toString());
        }
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(ReadRecordsRequest.class, other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type androidx.health.connect.client.request.ReadRecordsRequest<*>");
        ReadRecordsRequest readRecordsRequest = (ReadRecordsRequest) other;
        return Intrinsics.areEqual(this.recordType, readRecordsRequest.recordType) && Intrinsics.areEqual(this.timeRangeFilter, readRecordsRequest.timeRangeFilter) && Intrinsics.areEqual(this.dataOriginFilter, readRecordsRequest.dataOriginFilter) && this.ascendingOrder == readRecordsRequest.ascendingOrder && this.pageSize == readRecordsRequest.pageSize && Intrinsics.areEqual(this.pageToken, readRecordsRequest.pageToken);
    }

    /* renamed from: getAscendingOrder$connect_client_release, reason: from getter */
    public final boolean getAscendingOrder() {
        return this.ascendingOrder;
    }

    @NotNull
    public final Set<DataOrigin> getDataOriginFilter$connect_client_release() {
        return this.dataOriginFilter;
    }

    /* renamed from: getPageSize$connect_client_release, reason: from getter */
    public final int getPageSize() {
        return this.pageSize;
    }

    @Nullable
    /* renamed from: getPageToken$connect_client_release, reason: from getter */
    public final String getPageToken() {
        return this.pageToken;
    }

    @NotNull
    public final KClass<T> getRecordType$connect_client_release() {
        return this.recordType;
    }

    @NotNull
    /* renamed from: getTimeRangeFilter$connect_client_release, reason: from getter */
    public final TimeRangeFilter getTimeRangeFilter() {
        return this.timeRangeFilter;
    }

    public int hashCode() {
        int iHashCode = ((((((((this.recordType.hashCode() * 31) + this.timeRangeFilter.hashCode()) * 31) + this.dataOriginFilter.hashCode()) * 31) + c.a(this.ascendingOrder)) * 31) + this.pageSize) * 31;
        String str = this.pageToken;
        return iHashCode + (str != null ? str.hashCode() : 0);
    }

    public /* synthetic */ ReadRecordsRequest(KClass kClass, TimeRangeFilter timeRangeFilter, Set set, boolean z2, int i2, String str, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(kClass, timeRangeFilter, (i3 & 4) != 0 ? SetsKt.emptySet() : set, (i3 & 8) != 0 ? true : z2, (i3 & 16) != 0 ? 1000 : i2, (i3 & 32) != 0 ? null : str);
    }
}
