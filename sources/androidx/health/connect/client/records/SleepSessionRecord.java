package androidx.health.connect.client.records;

import androidx.annotation.RestrictTo;
import androidx.health.connect.client.aggregate.AggregateMetric;
import androidx.health.connect.client.records.CervicalMucusRecord;
import androidx.health.connect.client.records.SleepSessionRecord;
import androidx.media3.extractor.text.ttml.TtmlNode;
import com.aliyun.alink.linksdk.tools.ut.AUserTrack;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.annotation.AnnotationRetention;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u0000 $2\u00020\u0001:\u0003$%&B[\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\t\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010J\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!H\u0096\u0002J\b\u0010\"\u001a\u00020#H\u0016R\u0014\u0010\u0006\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u000e\u001a\u00020\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\n\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0012R\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0014R\u0013\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0018¨\u0006'"}, d2 = {"Landroidx/health/connect/client/records/SleepSessionRecord;", "Landroidx/health/connect/client/records/IntervalRecord;", "startTime", "Ljava/time/Instant;", "startZoneOffset", "Ljava/time/ZoneOffset;", AUserTrack.UTKEY_END_TIME, "endZoneOffset", "title", "", "notes", "stages", "", "Landroidx/health/connect/client/records/SleepSessionRecord$Stage;", TtmlNode.TAG_METADATA, "Landroidx/health/connect/client/records/metadata/Metadata;", "(Ljava/time/Instant;Ljava/time/ZoneOffset;Ljava/time/Instant;Ljava/time/ZoneOffset;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Landroidx/health/connect/client/records/metadata/Metadata;)V", "getEndTime", "()Ljava/time/Instant;", "getEndZoneOffset", "()Ljava/time/ZoneOffset;", "getMetadata", "()Landroidx/health/connect/client/records/metadata/Metadata;", "getNotes", "()Ljava/lang/String;", "getStages", "()Ljava/util/List;", "getStartTime", "getStartZoneOffset", "getTitle", "equals", "", "other", "", "hashCode", "", "Companion", "Stage", "StageTypes", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nSleepSessionRecord.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SleepSessionRecord.kt\nandroidx/health/connect/client/records/SleepSessionRecord\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,198:1\n1#2:199\n1208#3,2:200\n1238#3,4:202\n*S KotlinDebug\n*F\n+ 1 SleepSessionRecord.kt\nandroidx/health/connect/client/records/SleepSessionRecord\n*L\n144#1:200,2\n144#1:202,4\n*E\n"})
/* loaded from: classes.dex */
public final class SleepSessionRecord implements IntervalRecord {

    @JvmField
    @NotNull
    public static final AggregateMetric<Duration> SLEEP_DURATION_TOTAL = AggregateMetric.INSTANCE.durationMetric$connect_client_release("SleepSession");
    public static final int STAGE_TYPE_AWAKE = 1;
    public static final int STAGE_TYPE_AWAKE_IN_BED = 7;
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

    @Nullable
    private final String notes;

    @NotNull
    private final List<Stage> stages;

    @NotNull
    private final Instant startTime;

    @Nullable
    private final ZoneOffset startZoneOffset;

    @Nullable
    private final String title;

    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0006H\u0016R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0005\u001a\u00020\u0006¢\u0006\u000e\n\u0000\u0012\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\t¨\u0006\u0013"}, d2 = {"Landroidx/health/connect/client/records/SleepSessionRecord$Stage;", "", "startTime", "Ljava/time/Instant;", AUserTrack.UTKEY_END_TIME, "stage", "", "(Ljava/time/Instant;Ljava/time/Instant;I)V", "getEndTime", "()Ljava/time/Instant;", "getStage$annotations", "()V", "getStage", "()I", "getStartTime", "equals", "", "other", "hashCode", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nSleepSessionRecord.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SleepSessionRecord.kt\nandroidx/health/connect/client/records/SleepSessionRecord$Stage\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,198:1\n1#2:199\n*E\n"})
    public static final class Stage {

        @NotNull
        private final Instant endTime;
        private final int stage;

        @NotNull
        private final Instant startTime;

        public Stage(@NotNull Instant startTime, @NotNull Instant endTime, int i2) {
            Intrinsics.checkNotNullParameter(startTime, "startTime");
            Intrinsics.checkNotNullParameter(endTime, "endTime");
            this.startTime = startTime;
            this.endTime = endTime;
            this.stage = i2;
            if (!startTime.isBefore(endTime)) {
                throw new IllegalArgumentException("startTime must be before endTime.".toString());
            }
        }

        public static /* synthetic */ void getStage$annotations() {
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Stage)) {
                return false;
            }
            Stage stage = (Stage) other;
            return this.stage == stage.stage && Intrinsics.areEqual(this.startTime, stage.startTime) && Intrinsics.areEqual(this.endTime, stage.endTime);
        }

        @NotNull
        public final Instant getEndTime() {
            return this.endTime;
        }

        public final int getStage() {
            return this.stage;
        }

        @NotNull
        public final Instant getStartTime() {
            return this.startTime;
        }

        public int hashCode() {
            return (((this.stage * 31) + this.startTime.hashCode()) * 31) + this.endTime.hashCode();
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Landroidx/health/connect/client/records/SleepSessionRecord$StageTypes;", "", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @Retention(RetentionPolicy.SOURCE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface StageTypes {
    }

    static {
        Map<String, Integer> mapMapOf = MapsKt.mapOf(TuplesKt.to("awake", 1), TuplesKt.to("sleeping", 2), TuplesKt.to("out_of_bed", 3), TuplesKt.to(CervicalMucusRecord.Sensation.LIGHT, 4), TuplesKt.to("deep", 5), TuplesKt.to("rem", 6), TuplesKt.to("awake_in_bed", 7), TuplesKt.to("unknown", 0));
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

    public SleepSessionRecord(@NotNull Instant startTime, @Nullable ZoneOffset zoneOffset, @NotNull Instant endTime, @Nullable ZoneOffset zoneOffset2, @Nullable String str, @Nullable String str2, @NotNull List<Stage> stages, @NotNull androidx.health.connect.client.records.metadata.Metadata metadata) {
        Intrinsics.checkNotNullParameter(startTime, "startTime");
        Intrinsics.checkNotNullParameter(endTime, "endTime");
        Intrinsics.checkNotNullParameter(stages, "stages");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        this.startTime = startTime;
        this.startZoneOffset = zoneOffset;
        this.endTime = endTime;
        this.endZoneOffset = zoneOffset2;
        this.title = str;
        this.notes = str2;
        this.stages = stages;
        this.metadata = metadata;
        if (!getStartTime().isBefore(getEndTime())) {
            throw new IllegalArgumentException("startTime must be before endTime.".toString());
        }
        if (!stages.isEmpty()) {
            final SleepSessionRecord$sortedStages$1 sleepSessionRecord$sortedStages$1 = new Function2<Stage, Stage, Integer>() { // from class: androidx.health.connect.client.records.SleepSessionRecord$sortedStages$1
                @Override // kotlin.jvm.functions.Function2
                @NotNull
                /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
                public final Integer mo1invoke(SleepSessionRecord.Stage stage, SleepSessionRecord.Stage stage2) {
                    return Integer.valueOf(stage.getStartTime().compareTo(stage2.getStartTime()));
                }
            };
            List listSortedWith = CollectionsKt.sortedWith(stages, new Comparator() { // from class: androidx.health.connect.client.records.l
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return SleepSessionRecord._init_$lambda$1(sleepSessionRecord$sortedStages$1, obj, obj2);
                }
            });
            int lastIndex = CollectionsKt.getLastIndex(listSortedWith);
            int i2 = 0;
            while (i2 < lastIndex) {
                Instant endTime2 = ((Stage) listSortedWith.get(i2)).getEndTime();
                i2++;
                if (!(!endTime2.isAfter(((Stage) listSortedWith.get(i2)).getStartTime()))) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
            }
            if (!(!((Stage) CollectionsKt.first(listSortedWith)).getStartTime().isBefore(getStartTime()))) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (!(!((Stage) CollectionsKt.last(listSortedWith)).getEndTime().isAfter(getEndTime()))) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int _init_$lambda$1(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Number) tmp0.mo1invoke(obj, obj2)).intValue();
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SleepSessionRecord)) {
            return false;
        }
        SleepSessionRecord sleepSessionRecord = (SleepSessionRecord) other;
        return Intrinsics.areEqual(this.title, sleepSessionRecord.title) && Intrinsics.areEqual(this.notes, sleepSessionRecord.notes) && Intrinsics.areEqual(this.stages, sleepSessionRecord.stages) && Intrinsics.areEqual(getStartTime(), sleepSessionRecord.getStartTime()) && Intrinsics.areEqual(getStartZoneOffset(), sleepSessionRecord.getStartZoneOffset()) && Intrinsics.areEqual(getEndTime(), sleepSessionRecord.getEndTime()) && Intrinsics.areEqual(getEndZoneOffset(), sleepSessionRecord.getEndZoneOffset()) && Intrinsics.areEqual(getMetadata(), sleepSessionRecord.getMetadata());
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

    @Nullable
    public final String getNotes() {
        return this.notes;
    }

    @NotNull
    public final List<Stage> getStages() {
        return this.stages;
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

    @Nullable
    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        String str = this.title;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.notes;
        int iHashCode2 = (((iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31) + this.stages.hashCode()) * 31;
        ZoneOffset startZoneOffset = getStartZoneOffset();
        int iHashCode3 = (((iHashCode2 + (startZoneOffset != null ? startZoneOffset.hashCode() : 0)) * 31) + getEndTime().hashCode()) * 31;
        ZoneOffset endZoneOffset = getEndZoneOffset();
        return ((iHashCode3 + (endZoneOffset != null ? endZoneOffset.hashCode() : 0)) * 31) + getMetadata().hashCode();
    }

    public /* synthetic */ SleepSessionRecord(Instant instant, ZoneOffset zoneOffset, Instant instant2, ZoneOffset zoneOffset2, String str, String str2, List list, androidx.health.connect.client.records.metadata.Metadata metadata, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(instant, zoneOffset, instant2, zoneOffset2, (i2 & 16) != 0 ? null : str, (i2 & 32) != 0 ? null : str2, (i2 & 64) != 0 ? CollectionsKt.emptyList() : list, (i2 & 128) != 0 ? androidx.health.connect.client.records.metadata.Metadata.EMPTY : metadata);
    }
}
