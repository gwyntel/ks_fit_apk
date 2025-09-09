package androidx.health.connect.client.records;

import androidx.annotation.RestrictTo;
import androidx.health.connect.client.records.CervicalMucusRecord;
import androidx.media3.extractor.text.ttml.TtmlNode;
import com.aliyun.alink.linksdk.tools.ut.AUserTrack;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.annotation.AnnotationRetention;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0007\u0018\u0000 \u001e2\u00020\u0001:\u0002\u001e\u001fB;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0096\u0002J\b\u0010\u001d\u001a\u00020\tH\u0016R\u0014\u0010\u0006\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0016\u0010\u0007\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\b\u001a\u00020\t¢\u0006\u000e\n\u0000\u0012\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000eR\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0010¨\u0006 "}, d2 = {"Landroidx/health/connect/client/records/SleepStageRecord;", "Landroidx/health/connect/client/records/IntervalRecord;", "startTime", "Ljava/time/Instant;", "startZoneOffset", "Ljava/time/ZoneOffset;", AUserTrack.UTKEY_END_TIME, "endZoneOffset", "stage", "", TtmlNode.TAG_METADATA, "Landroidx/health/connect/client/records/metadata/Metadata;", "(Ljava/time/Instant;Ljava/time/ZoneOffset;Ljava/time/Instant;Ljava/time/ZoneOffset;ILandroidx/health/connect/client/records/metadata/Metadata;)V", "getEndTime", "()Ljava/time/Instant;", "getEndZoneOffset", "()Ljava/time/ZoneOffset;", "getMetadata", "()Landroidx/health/connect/client/records/metadata/Metadata;", "getStage$annotations", "()V", "getStage", "()I", "getStartTime", "getStartZoneOffset", "equals", "", "other", "", "hashCode", "Companion", "StageTypes", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@RestrictTo({RestrictTo.Scope.LIBRARY})
@SourceDebugExtension({"SMAP\nSleepStageRecord.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SleepStageRecord.kt\nandroidx/health/connect/client/records/SleepStageRecord\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,115:1\n1#2:116\n1208#3,2:117\n1238#3,4:119\n*S KotlinDebug\n*F\n+ 1 SleepStageRecord.kt\nandroidx/health/connect/client/records/SleepStageRecord\n*L\n93#1:117,2\n93#1:119,4\n*E\n"})
/* loaded from: classes.dex */
public final class SleepStageRecord implements IntervalRecord {
    public static final int STAGE_TYPE_AWAKE = 1;
    public static final int STAGE_TYPE_DEEP = 5;

    @JvmField
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final Map<Integer, String> STAGE_TYPE_INT_TO_STRING_MAP;
    public static final int STAGE_TYPE_LIGHT = 4;
    public static final int STAGE_TYPE_OUT_OF_BED = 3;
    public static final int STAGE_TYPE_REM = 6;
    public static final int STAGE_TYPE_SLEEPING = 2;

    @JvmField
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final Map<String, Integer> STAGE_TYPE_STRING_TO_INT_MAP;
    public static final int STAGE_TYPE_UNKNOWN = 0;

    @NotNull
    private final Instant endTime;

    @Nullable
    private final ZoneOffset endZoneOffset;

    @NotNull
    private final androidx.health.connect.client.records.metadata.Metadata metadata;
    private final int stage;

    @NotNull
    private final Instant startTime;

    @Nullable
    private final ZoneOffset startZoneOffset;

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Landroidx/health/connect/client/records/SleepStageRecord$StageTypes;", "", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @Retention(RetentionPolicy.SOURCE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface StageTypes {
    }

    static {
        Map<String, Integer> mapMapOf = MapsKt.mapOf(TuplesKt.to("awake", 1), TuplesKt.to("sleeping", 2), TuplesKt.to("out_of_bed", 3), TuplesKt.to(CervicalMucusRecord.Sensation.LIGHT, 4), TuplesKt.to("deep", 5), TuplesKt.to("rem", 6), TuplesKt.to("unknown", 0));
        STAGE_TYPE_STRING_TO_INT_MAP = mapMapOf;
        Set<Map.Entry<String, Integer>> setEntrySet = mapMapOf.entrySet();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10)), 16));
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            linkedHashMap.put(Integer.valueOf(((Number) entry.getValue()).intValue()), (String) entry.getKey());
        }
        STAGE_TYPE_INT_TO_STRING_MAP = linkedHashMap;
    }

    public SleepStageRecord(@NotNull Instant startTime, @Nullable ZoneOffset zoneOffset, @NotNull Instant endTime, @Nullable ZoneOffset zoneOffset2, int i2, @NotNull androidx.health.connect.client.records.metadata.Metadata metadata) {
        Intrinsics.checkNotNullParameter(startTime, "startTime");
        Intrinsics.checkNotNullParameter(endTime, "endTime");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        this.startTime = startTime;
        this.startZoneOffset = zoneOffset;
        this.endTime = endTime;
        this.endZoneOffset = zoneOffset2;
        this.stage = i2;
        this.metadata = metadata;
        if (!getStartTime().isBefore(getEndTime())) {
            throw new IllegalArgumentException("startTime must be before endTime.".toString());
        }
    }

    public static /* synthetic */ void getStage$annotations() {
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SleepStageRecord)) {
            return false;
        }
        SleepStageRecord sleepStageRecord = (SleepStageRecord) other;
        return this.stage == sleepStageRecord.stage && Intrinsics.areEqual(getStartTime(), sleepStageRecord.getStartTime()) && Intrinsics.areEqual(getStartZoneOffset(), sleepStageRecord.getStartZoneOffset()) && Intrinsics.areEqual(getEndTime(), sleepStageRecord.getEndTime()) && Intrinsics.areEqual(getEndZoneOffset(), sleepStageRecord.getEndZoneOffset()) && Intrinsics.areEqual(getMetadata(), sleepStageRecord.getMetadata());
    }

    @Override // androidx.health.connect.client.records.IntervalRecord
    @NotNull
    public Instant getEndTime() {
        return this.endTime;
    }

    @Override // androidx.health.connect.client.records.IntervalRecord
    @Nullable
    public ZoneOffset getEndZoneOffset() {
        return this.endZoneOffset;
    }

    @Override // androidx.health.connect.client.records.Record
    @NotNull
    public androidx.health.connect.client.records.metadata.Metadata getMetadata() {
        return this.metadata;
    }

    public final int getStage() {
        return this.stage;
    }

    @Override // androidx.health.connect.client.records.IntervalRecord
    @NotNull
    public Instant getStartTime() {
        return this.startTime;
    }

    @Override // androidx.health.connect.client.records.IntervalRecord
    @Nullable
    public ZoneOffset getStartZoneOffset() {
        return this.startZoneOffset;
    }

    public int hashCode() {
        int i2 = this.stage * 31;
        ZoneOffset startZoneOffset = getStartZoneOffset();
        int iHashCode = (((i2 + (startZoneOffset != null ? startZoneOffset.hashCode() : 0)) * 31) + getEndTime().hashCode()) * 31;
        ZoneOffset endZoneOffset = getEndZoneOffset();
        return ((iHashCode + (endZoneOffset != null ? endZoneOffset.hashCode() : 0)) * 31) + getMetadata().hashCode();
    }

    public /* synthetic */ SleepStageRecord(Instant instant, ZoneOffset zoneOffset, Instant instant2, ZoneOffset zoneOffset2, int i2, androidx.health.connect.client.records.metadata.Metadata metadata, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(instant, zoneOffset, instant2, zoneOffset2, i2, (i3 & 32) != 0 ? androidx.health.connect.client.records.metadata.Metadata.EMPTY : metadata);
    }
}
