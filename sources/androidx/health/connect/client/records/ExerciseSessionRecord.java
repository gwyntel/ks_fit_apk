package androidx.health.connect.client.records;

import androidx.annotation.RestrictTo;
import androidx.core.app.NotificationCompat;
import androidx.health.connect.client.aggregate.AggregateMetric;
import androidx.health.connect.client.records.ExerciseRoute;
import androidx.health.connect.client.records.ExerciseRouteResult;
import androidx.media3.extractor.text.ttml.TtmlNode;
import com.aliyun.alink.linksdk.tools.ut.AUserTrack;
import com.google.android.gms.fitness.FitnessActivities;
import com.google.android.gms.fitness.data.WorkoutExercises;
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
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.annotation.AnnotationRetention;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u0000 32\u00020\u0001:\u000234B\u0081\u0001\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u000b\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010\u0012\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0010\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0015¢\u0006\u0002\u0010\u0016B\u007f\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u000b\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010\u0012\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0010\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0018¢\u0006\u0002\u0010\u0019J\u0013\u0010.\u001a\u00020/2\b\u00100\u001a\u0004\u0018\u000101H\u0096\u0002J\b\u00102\u001a\u00020\tH\u0016R\u0014\u0010\u0006\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0016\u0010\u0007\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0017\u001a\u00020\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0017\u0010\b\u001a\u00020\t¢\u0006\u000e\n\u0000\u0012\u0004\b \u0010!\u001a\u0004\b\"\u0010#R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0010¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u0013\u0010\f\u001a\u0004\u0018\u00010\u000b¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010¢\u0006\b\n\u0000\u001a\u0004\b*\u0010%R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001bR\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001dR\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\b\n\u0000\u001a\u0004\b-\u0010)¨\u00065"}, d2 = {"Landroidx/health/connect/client/records/ExerciseSessionRecord;", "Landroidx/health/connect/client/records/IntervalRecord;", "startTime", "Ljava/time/Instant;", "startZoneOffset", "Ljava/time/ZoneOffset;", AUserTrack.UTKEY_END_TIME, "endZoneOffset", "exerciseType", "", "title", "", "notes", TtmlNode.TAG_METADATA, "Landroidx/health/connect/client/records/metadata/Metadata;", "segments", "", "Landroidx/health/connect/client/records/ExerciseSegment;", "laps", "Landroidx/health/connect/client/records/ExerciseLap;", "exerciseRoute", "Landroidx/health/connect/client/records/ExerciseRoute;", "(Ljava/time/Instant;Ljava/time/ZoneOffset;Ljava/time/Instant;Ljava/time/ZoneOffset;ILjava/lang/String;Ljava/lang/String;Landroidx/health/connect/client/records/metadata/Metadata;Ljava/util/List;Ljava/util/List;Landroidx/health/connect/client/records/ExerciseRoute;)V", "exerciseRouteResult", "Landroidx/health/connect/client/records/ExerciseRouteResult;", "(Ljava/time/Instant;Ljava/time/ZoneOffset;Ljava/time/Instant;Ljava/time/ZoneOffset;ILjava/lang/String;Ljava/lang/String;Landroidx/health/connect/client/records/metadata/Metadata;Ljava/util/List;Ljava/util/List;Landroidx/health/connect/client/records/ExerciseRouteResult;)V", "getEndTime", "()Ljava/time/Instant;", "getEndZoneOffset", "()Ljava/time/ZoneOffset;", "getExerciseRouteResult", "()Landroidx/health/connect/client/records/ExerciseRouteResult;", "getExerciseType$annotations", "()V", "getExerciseType", "()I", "getLaps", "()Ljava/util/List;", "getMetadata", "()Landroidx/health/connect/client/records/metadata/Metadata;", "getNotes", "()Ljava/lang/String;", "getSegments", "getStartTime", "getStartZoneOffset", "getTitle", "equals", "", "other", "", "hashCode", "Companion", "ExerciseTypes", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nExerciseSessionRecord.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ExerciseSessionRecord.kt\nandroidx/health/connect/client/records/ExerciseSessionRecord\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,435:1\n1#2:436\n2310#3,14:437\n1940#3,14:451\n1208#3,2:465\n1238#3,4:467\n*S KotlinDebug\n*F\n+ 1 ExerciseSessionRecord.kt\nandroidx/health/connect/client/records/ExerciseSessionRecord\n*L\n142#1:437,14\n143#1:451,14\n361#1:465,2\n361#1:467,4\n*E\n"})
/* loaded from: classes.dex */
public final class ExerciseSessionRecord implements IntervalRecord {

    @JvmField
    @NotNull
    public static final AggregateMetric<Duration> EXERCISE_DURATION_TOTAL = AggregateMetric.INSTANCE.durationMetric$connect_client_release("ActiveTime", AggregateMetric.AggregationType.TOTAL, "time");
    public static final int EXERCISE_TYPE_BADMINTON = 2;
    public static final int EXERCISE_TYPE_BASEBALL = 4;
    public static final int EXERCISE_TYPE_BASKETBALL = 5;
    public static final int EXERCISE_TYPE_BIKING = 8;
    public static final int EXERCISE_TYPE_BIKING_STATIONARY = 9;
    public static final int EXERCISE_TYPE_BOOT_CAMP = 10;
    public static final int EXERCISE_TYPE_BOXING = 11;
    public static final int EXERCISE_TYPE_CALISTHENICS = 13;
    public static final int EXERCISE_TYPE_CRICKET = 14;
    public static final int EXERCISE_TYPE_DANCING = 16;
    public static final int EXERCISE_TYPE_ELLIPTICAL = 25;
    public static final int EXERCISE_TYPE_EXERCISE_CLASS = 26;
    public static final int EXERCISE_TYPE_FENCING = 27;
    public static final int EXERCISE_TYPE_FOOTBALL_AMERICAN = 28;
    public static final int EXERCISE_TYPE_FOOTBALL_AUSTRALIAN = 29;
    public static final int EXERCISE_TYPE_FRISBEE_DISC = 31;
    public static final int EXERCISE_TYPE_GOLF = 32;
    public static final int EXERCISE_TYPE_GUIDED_BREATHING = 33;
    public static final int EXERCISE_TYPE_GYMNASTICS = 34;
    public static final int EXERCISE_TYPE_HANDBALL = 35;
    public static final int EXERCISE_TYPE_HIGH_INTENSITY_INTERVAL_TRAINING = 36;
    public static final int EXERCISE_TYPE_HIKING = 37;
    public static final int EXERCISE_TYPE_ICE_HOCKEY = 38;
    public static final int EXERCISE_TYPE_ICE_SKATING = 39;

    @JvmField
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final Map<Integer, String> EXERCISE_TYPE_INT_TO_STRING_MAP;
    public static final int EXERCISE_TYPE_MARTIAL_ARTS = 44;
    public static final int EXERCISE_TYPE_OTHER_WORKOUT = 0;
    public static final int EXERCISE_TYPE_PADDLING = 46;
    public static final int EXERCISE_TYPE_PARAGLIDING = 47;
    public static final int EXERCISE_TYPE_PILATES = 48;
    public static final int EXERCISE_TYPE_RACQUETBALL = 50;
    public static final int EXERCISE_TYPE_ROCK_CLIMBING = 51;
    public static final int EXERCISE_TYPE_ROLLER_HOCKEY = 52;
    public static final int EXERCISE_TYPE_ROWING = 53;
    public static final int EXERCISE_TYPE_ROWING_MACHINE = 54;
    public static final int EXERCISE_TYPE_RUGBY = 55;
    public static final int EXERCISE_TYPE_RUNNING = 56;
    public static final int EXERCISE_TYPE_RUNNING_TREADMILL = 57;
    public static final int EXERCISE_TYPE_SAILING = 58;
    public static final int EXERCISE_TYPE_SCUBA_DIVING = 59;
    public static final int EXERCISE_TYPE_SKATING = 60;
    public static final int EXERCISE_TYPE_SKIING = 61;
    public static final int EXERCISE_TYPE_SNOWBOARDING = 62;
    public static final int EXERCISE_TYPE_SNOWSHOEING = 63;
    public static final int EXERCISE_TYPE_SOCCER = 64;
    public static final int EXERCISE_TYPE_SOFTBALL = 65;
    public static final int EXERCISE_TYPE_SQUASH = 66;
    public static final int EXERCISE_TYPE_STAIR_CLIMBING = 68;
    public static final int EXERCISE_TYPE_STAIR_CLIMBING_MACHINE = 69;
    public static final int EXERCISE_TYPE_STRENGTH_TRAINING = 70;
    public static final int EXERCISE_TYPE_STRETCHING = 71;

    @JvmField
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final Map<String, Integer> EXERCISE_TYPE_STRING_TO_INT_MAP;
    public static final int EXERCISE_TYPE_SURFING = 72;
    public static final int EXERCISE_TYPE_SWIMMING_OPEN_WATER = 73;
    public static final int EXERCISE_TYPE_SWIMMING_POOL = 74;
    public static final int EXERCISE_TYPE_TABLE_TENNIS = 75;
    public static final int EXERCISE_TYPE_TENNIS = 76;
    public static final int EXERCISE_TYPE_VOLLEYBALL = 78;
    public static final int EXERCISE_TYPE_WALKING = 79;
    public static final int EXERCISE_TYPE_WATER_POLO = 80;
    public static final int EXERCISE_TYPE_WEIGHTLIFTING = 81;
    public static final int EXERCISE_TYPE_WHEELCHAIR = 82;
    public static final int EXERCISE_TYPE_YOGA = 83;

    @NotNull
    private final Instant endTime;

    @Nullable
    private final ZoneOffset endZoneOffset;

    @NotNull
    private final ExerciseRouteResult exerciseRouteResult;
    private final int exerciseType;

    @NotNull
    private final List<ExerciseLap> laps;

    @NotNull
    private final androidx.health.connect.client.records.metadata.Metadata metadata;

    @Nullable
    private final String notes;

    @NotNull
    private final List<ExerciseSegment> segments;

    @NotNull
    private final Instant startTime;

    @Nullable
    private final ZoneOffset startZoneOffset;

    @Nullable
    private final String title;

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Landroidx/health/connect/client/records/ExerciseSessionRecord$ExerciseTypes;", "", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @Retention(RetentionPolicy.SOURCE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface ExerciseTypes {
    }

    static {
        Map<String, Integer> mapMapOf = MapsKt.mapOf(TuplesKt.to(WorkoutExercises.BACK_EXTENSION, 13), TuplesKt.to(FitnessActivities.BADMINTON, 2), TuplesKt.to("barbell_shoulder_press", 70), TuplesKt.to(FitnessActivities.BASEBALL, 4), TuplesKt.to(FitnessActivities.BASKETBALL, 5), TuplesKt.to("bench_press", 70), TuplesKt.to("bench_sit_up", 13), TuplesKt.to(FitnessActivities.BIKING, 8), TuplesKt.to("biking_stationary", 9), TuplesKt.to("boot_camp", 10), TuplesKt.to(FitnessActivities.BOXING, 11), TuplesKt.to(WorkoutExercises.BURPEE, 13), TuplesKt.to(FitnessActivities.CRICKET, 14), TuplesKt.to(WorkoutExercises.CRUNCH, 13), TuplesKt.to(FitnessActivities.DANCING, 16), TuplesKt.to(WorkoutExercises.DEADLIFT, 70), TuplesKt.to("dumbbell_curl_left_arm", 70), TuplesKt.to("dumbbell_curl_right_arm", 70), TuplesKt.to("dumbbell_front_raise", 70), TuplesKt.to("dumbbell_lateral_raise", 70), TuplesKt.to("dumbbell_triceps_extension_left_arm", 70), TuplesKt.to("dumbbell_triceps_extension_right_arm", 70), TuplesKt.to("dumbbell_triceps_extension_two_arm", 70), TuplesKt.to(FitnessActivities.ELLIPTICAL, 25), TuplesKt.to("exercise_class", 26), TuplesKt.to(FitnessActivities.FENCING, 27), TuplesKt.to("football_american", 28), TuplesKt.to("football_australian", 29), TuplesKt.to("forward_twist", 13), TuplesKt.to(FitnessActivities.FRISBEE_DISC, 31), TuplesKt.to(FitnessActivities.GOLF, 32), TuplesKt.to(FitnessActivities.GUIDED_BREATHING, 33), TuplesKt.to(FitnessActivities.GYMNASTICS, 34), TuplesKt.to(FitnessActivities.HANDBALL, 35), TuplesKt.to(FitnessActivities.HIKING, 37), TuplesKt.to("ice_hockey", 38), TuplesKt.to(FitnessActivities.ICE_SKATING, 39), TuplesKt.to(WorkoutExercises.JUMPING_JACK, 36), TuplesKt.to(FitnessActivities.JUMP_ROPE, 36), TuplesKt.to("lat_pull_down", 70), TuplesKt.to(WorkoutExercises.LUNGE, 13), TuplesKt.to(FitnessActivities.MARTIAL_ARTS, 44), TuplesKt.to("paddling", 46), TuplesKt.to("para_gliding", 47), TuplesKt.to(FitnessActivities.PILATES, 48), TuplesKt.to(WorkoutExercises.PLANK, 13), TuplesKt.to(FitnessActivities.RACQUETBALL, 50), TuplesKt.to(FitnessActivities.ROCK_CLIMBING, 51), TuplesKt.to("roller_hockey", 52), TuplesKt.to(FitnessActivities.ROWING, 53), TuplesKt.to("rowing_machine", 54), TuplesKt.to(FitnessActivities.RUGBY, 55), TuplesKt.to(FitnessActivities.RUNNING, 56), TuplesKt.to("running_treadmill", 57), TuplesKt.to(FitnessActivities.SAILING, 58), TuplesKt.to(FitnessActivities.SCUBA_DIVING, 59), TuplesKt.to(FitnessActivities.SKATING, 60), TuplesKt.to(FitnessActivities.SKIING, 61), TuplesKt.to(FitnessActivities.SNOWBOARDING, 62), TuplesKt.to(FitnessActivities.SNOWSHOEING, 63), TuplesKt.to("soccer", 64), TuplesKt.to(FitnessActivities.SOFTBALL, 65), TuplesKt.to(FitnessActivities.SQUASH, 66), TuplesKt.to(WorkoutExercises.SQUAT, 13), TuplesKt.to(FitnessActivities.STAIR_CLIMBING, 68), TuplesKt.to("stair_climbing_machine", 69), TuplesKt.to("stretching", 71), TuplesKt.to(FitnessActivities.SURFING, 72), TuplesKt.to("swimming_open_water", 73), TuplesKt.to("swimming_pool", 74), TuplesKt.to(FitnessActivities.TABLE_TENNIS, 75), TuplesKt.to(FitnessActivities.TENNIS, 76), TuplesKt.to("upper_twist", 13), TuplesKt.to(FitnessActivities.VOLLEYBALL, 78), TuplesKt.to(FitnessActivities.WALKING, 79), TuplesKt.to(FitnessActivities.WATER_POLO, 80), TuplesKt.to(FitnessActivities.WEIGHTLIFTING, 81), TuplesKt.to(FitnessActivities.WHEELCHAIR, 82), TuplesKt.to(NotificationCompat.CATEGORY_WORKOUT, 0), TuplesKt.to(FitnessActivities.YOGA, 83), TuplesKt.to(FitnessActivities.CALISTHENICS, 13), TuplesKt.to("high_intensity_interval_training", 36), TuplesKt.to(FitnessActivities.STRENGTH_TRAINING, 70));
        EXERCISE_TYPE_STRING_TO_INT_MAP = mapMapOf;
        Set<Map.Entry<String, Integer>> setEntrySet = mapMapOf.entrySet();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10)), 16));
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            linkedHashMap.put(Integer.valueOf(((Number) entry.getValue()).intValue()), (String) entry.getKey());
        }
        EXERCISE_TYPE_INT_TO_STRING_MAP = linkedHashMap;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public ExerciseSessionRecord(@NotNull Instant startTime, @Nullable ZoneOffset zoneOffset, @NotNull Instant endTime, @Nullable ZoneOffset zoneOffset2, int i2) {
        this(startTime, zoneOffset, endTime, zoneOffset2, i2, (String) null, (String) null, (androidx.health.connect.client.records.metadata.Metadata) null, (List) null, (List) null, (ExerciseRoute) null, 2016, (DefaultConstructorMarker) null);
        Intrinsics.checkNotNullParameter(startTime, "startTime");
        Intrinsics.checkNotNullParameter(endTime, "endTime");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int _init_$lambda$2(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Number) tmp0.mo1invoke(obj, obj2)).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int _init_$lambda$7(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Number) tmp0.mo1invoke(obj, obj2)).intValue();
    }

    public static /* synthetic */ void getExerciseType$annotations() {
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExerciseSessionRecord)) {
            return false;
        }
        ExerciseSessionRecord exerciseSessionRecord = (ExerciseSessionRecord) other;
        return this.exerciseType == exerciseSessionRecord.exerciseType && Intrinsics.areEqual(this.title, exerciseSessionRecord.title) && Intrinsics.areEqual(this.notes, exerciseSessionRecord.notes) && Intrinsics.areEqual(getStartTime(), exerciseSessionRecord.getStartTime()) && Intrinsics.areEqual(getStartZoneOffset(), exerciseSessionRecord.getStartZoneOffset()) && Intrinsics.areEqual(getEndTime(), exerciseSessionRecord.getEndTime()) && Intrinsics.areEqual(getEndZoneOffset(), exerciseSessionRecord.getEndZoneOffset()) && Intrinsics.areEqual(getMetadata(), exerciseSessionRecord.getMetadata()) && Intrinsics.areEqual(this.segments, exerciseSessionRecord.segments) && Intrinsics.areEqual(this.laps, exerciseSessionRecord.laps) && Intrinsics.areEqual(this.exerciseRouteResult, exerciseSessionRecord.exerciseRouteResult);
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

    @NotNull
    public final ExerciseRouteResult getExerciseRouteResult() {
        return this.exerciseRouteResult;
    }

    public final int getExerciseType() {
        return this.exerciseType;
    }

    @NotNull
    public final List<ExerciseLap> getLaps() {
        return this.laps;
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
    public final List<ExerciseSegment> getSegments() {
        return this.segments;
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
        int i2 = this.exerciseType * 31;
        String str = this.title;
        int iHashCode = (i2 + (str != null ? str.hashCode() : 0)) * 31;
        String str2 = this.notes;
        int iHashCode2 = (iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        ZoneOffset startZoneOffset = getStartZoneOffset();
        int iHashCode3 = (((iHashCode2 + (startZoneOffset != null ? startZoneOffset.hashCode() : 0)) * 31) + getEndTime().hashCode()) * 31;
        ZoneOffset endZoneOffset = getEndZoneOffset();
        return ((((iHashCode3 + (endZoneOffset != null ? endZoneOffset.hashCode() : 0)) * 31) + getMetadata().hashCode()) * 31) + this.exerciseRouteResult.hashCode();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public ExerciseSessionRecord(@NotNull Instant startTime, @Nullable ZoneOffset zoneOffset, @NotNull Instant endTime, @Nullable ZoneOffset zoneOffset2, int i2, @Nullable String str) {
        this(startTime, zoneOffset, endTime, zoneOffset2, i2, str, (String) null, (androidx.health.connect.client.records.metadata.Metadata) null, (List) null, (List) null, (ExerciseRoute) null, 1984, (DefaultConstructorMarker) null);
        Intrinsics.checkNotNullParameter(startTime, "startTime");
        Intrinsics.checkNotNullParameter(endTime, "endTime");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public ExerciseSessionRecord(@NotNull Instant startTime, @Nullable ZoneOffset zoneOffset, @NotNull Instant endTime, @Nullable ZoneOffset zoneOffset2, int i2, @Nullable String str, @Nullable String str2) {
        this(startTime, zoneOffset, endTime, zoneOffset2, i2, str, str2, (androidx.health.connect.client.records.metadata.Metadata) null, (List) null, (List) null, (ExerciseRoute) null, 1920, (DefaultConstructorMarker) null);
        Intrinsics.checkNotNullParameter(startTime, "startTime");
        Intrinsics.checkNotNullParameter(endTime, "endTime");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public ExerciseSessionRecord(@NotNull Instant startTime, @Nullable ZoneOffset zoneOffset, @NotNull Instant endTime, @Nullable ZoneOffset zoneOffset2, int i2, @Nullable String str, @Nullable String str2, @NotNull androidx.health.connect.client.records.metadata.Metadata metadata) {
        this(startTime, zoneOffset, endTime, zoneOffset2, i2, str, str2, metadata, (List) null, (List) null, (ExerciseRoute) null, 1792, (DefaultConstructorMarker) null);
        Intrinsics.checkNotNullParameter(startTime, "startTime");
        Intrinsics.checkNotNullParameter(endTime, "endTime");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public ExerciseSessionRecord(@NotNull Instant startTime, @Nullable ZoneOffset zoneOffset, @NotNull Instant endTime, @Nullable ZoneOffset zoneOffset2, int i2, @Nullable String str, @Nullable String str2, @NotNull androidx.health.connect.client.records.metadata.Metadata metadata, @NotNull List<ExerciseSegment> segments) {
        this(startTime, zoneOffset, endTime, zoneOffset2, i2, str, str2, metadata, segments, (List) null, (ExerciseRoute) null, 1536, (DefaultConstructorMarker) null);
        Intrinsics.checkNotNullParameter(startTime, "startTime");
        Intrinsics.checkNotNullParameter(endTime, "endTime");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Intrinsics.checkNotNullParameter(segments, "segments");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public ExerciseSessionRecord(@NotNull Instant startTime, @Nullable ZoneOffset zoneOffset, @NotNull Instant endTime, @Nullable ZoneOffset zoneOffset2, int i2, @Nullable String str, @Nullable String str2, @NotNull androidx.health.connect.client.records.metadata.Metadata metadata, @NotNull List<ExerciseSegment> segments, @NotNull List<ExerciseLap> laps) {
        this(startTime, zoneOffset, endTime, zoneOffset2, i2, str, str2, metadata, segments, laps, (ExerciseRoute) null, 1024, (DefaultConstructorMarker) null);
        Intrinsics.checkNotNullParameter(startTime, "startTime");
        Intrinsics.checkNotNullParameter(endTime, "endTime");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Intrinsics.checkNotNullParameter(segments, "segments");
        Intrinsics.checkNotNullParameter(laps, "laps");
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public ExerciseSessionRecord(@NotNull Instant startTime, @Nullable ZoneOffset zoneOffset, @NotNull Instant endTime, @Nullable ZoneOffset zoneOffset2, int i2, @Nullable String str, @Nullable String str2, @NotNull androidx.health.connect.client.records.metadata.Metadata metadata, @NotNull List<ExerciseSegment> segments, @NotNull List<ExerciseLap> laps, @NotNull ExerciseRouteResult exerciseRouteResult) {
        Intrinsics.checkNotNullParameter(startTime, "startTime");
        Intrinsics.checkNotNullParameter(endTime, "endTime");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Intrinsics.checkNotNullParameter(segments, "segments");
        Intrinsics.checkNotNullParameter(laps, "laps");
        Intrinsics.checkNotNullParameter(exerciseRouteResult, "exerciseRouteResult");
        this.startTime = startTime;
        this.startZoneOffset = zoneOffset;
        this.endTime = endTime;
        this.endZoneOffset = zoneOffset2;
        this.exerciseType = i2;
        this.title = str;
        this.notes = str2;
        this.metadata = metadata;
        this.segments = segments;
        this.laps = laps;
        this.exerciseRouteResult = exerciseRouteResult;
        if (getStartTime().isBefore(getEndTime())) {
            int i3 = 0;
            if (!segments.isEmpty()) {
                final ExerciseSessionRecord$sortedSegments$1 exerciseSessionRecord$sortedSegments$1 = new Function2<ExerciseSegment, ExerciseSegment, Integer>() { // from class: androidx.health.connect.client.records.ExerciseSessionRecord$sortedSegments$1
                    @Override // kotlin.jvm.functions.Function2
                    @NotNull
                    /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
                    public final Integer mo1invoke(ExerciseSegment exerciseSegment, ExerciseSegment exerciseSegment2) {
                        return Integer.valueOf(exerciseSegment.getStartTime().compareTo(exerciseSegment2.getStartTime()));
                    }
                };
                List listSortedWith = CollectionsKt.sortedWith(segments, new Comparator() { // from class: androidx.health.connect.client.records.d
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return ExerciseSessionRecord._init_$lambda$2(exerciseSessionRecord$sortedSegments$1, obj, obj2);
                    }
                });
                int lastIndex = CollectionsKt.getLastIndex(listSortedWith);
                int i4 = 0;
                while (i4 < lastIndex) {
                    Instant endTime2 = ((ExerciseSegment) listSortedWith.get(i4)).getEndTime();
                    i4++;
                    if (!(!endTime2.isAfter(((ExerciseSegment) listSortedWith.get(i4)).getStartTime()))) {
                        throw new IllegalArgumentException("segments can not overlap.".toString());
                    }
                }
                if (!((ExerciseSegment) CollectionsKt.first(listSortedWith)).getStartTime().isBefore(getStartTime())) {
                    if (!((ExerciseSegment) CollectionsKt.last(listSortedWith)).getEndTime().isAfter(getEndTime())) {
                        Iterator it = listSortedWith.iterator();
                        while (it.hasNext()) {
                            if (!((ExerciseSegment) it.next()).isCompatibleWith$connect_client_release(this.exerciseType)) {
                                throw new IllegalArgumentException("segmentType and sessionType is not compatible.".toString());
                            }
                        }
                    } else {
                        throw new IllegalArgumentException("segments can not be out of parent time range.".toString());
                    }
                } else {
                    throw new IllegalArgumentException("segments can not be out of parent time range.".toString());
                }
            }
            if (!this.laps.isEmpty()) {
                List<ExerciseLap> list = this.laps;
                final ExerciseSessionRecord$sortedLaps$1 exerciseSessionRecord$sortedLaps$1 = new Function2<ExerciseLap, ExerciseLap, Integer>() { // from class: androidx.health.connect.client.records.ExerciseSessionRecord$sortedLaps$1
                    @Override // kotlin.jvm.functions.Function2
                    @NotNull
                    /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
                    public final Integer mo1invoke(ExerciseLap exerciseLap, ExerciseLap exerciseLap2) {
                        return Integer.valueOf(exerciseLap.getStartTime().compareTo(exerciseLap2.getStartTime()));
                    }
                };
                List listSortedWith2 = CollectionsKt.sortedWith(list, new Comparator() { // from class: androidx.health.connect.client.records.e
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return ExerciseSessionRecord._init_$lambda$7(exerciseSessionRecord$sortedLaps$1, obj, obj2);
                    }
                });
                int lastIndex2 = CollectionsKt.getLastIndex(listSortedWith2);
                while (i3 < lastIndex2) {
                    Instant endTime3 = ((ExerciseLap) listSortedWith2.get(i3)).getEndTime();
                    i3++;
                    if (!(!endTime3.isAfter(((ExerciseLap) listSortedWith2.get(i3)).getStartTime()))) {
                        throw new IllegalArgumentException("laps can not overlap.".toString());
                    }
                }
                if (!((ExerciseLap) CollectionsKt.first(listSortedWith2)).getStartTime().isBefore(getStartTime())) {
                    if (!(!((ExerciseLap) CollectionsKt.last(listSortedWith2)).getEndTime().isAfter(getEndTime()))) {
                        throw new IllegalArgumentException("laps can not be out of parent time range.".toString());
                    }
                } else {
                    throw new IllegalArgumentException("laps can not be out of parent time range.".toString());
                }
            }
            if ((this.exerciseRouteResult instanceof ExerciseRouteResult.Data) && (!((ExerciseRouteResult.Data) r2).getExerciseRoute().getRoute().isEmpty())) {
                List<ExerciseRoute.Location> route = ((ExerciseRouteResult.Data) this.exerciseRouteResult).getExerciseRoute().getRoute();
                Iterator<T> it2 = route.iterator();
                if (it2.hasNext()) {
                    Object next = it2.next();
                    if (it2.hasNext()) {
                        Instant time = ((ExerciseRoute.Location) next).getTime();
                        do {
                            Object next2 = it2.next();
                            Instant time2 = ((ExerciseRoute.Location) next2).getTime();
                            if (time.compareTo(time2) > 0) {
                                next = next2;
                                time = time2;
                            }
                        } while (it2.hasNext());
                    }
                    Instant time3 = ((ExerciseRoute.Location) next).getTime();
                    Iterator<T> it3 = route.iterator();
                    if (it3.hasNext()) {
                        Object next3 = it3.next();
                        if (it3.hasNext()) {
                            Instant time4 = ((ExerciseRoute.Location) next3).getTime();
                            do {
                                Object next4 = it3.next();
                                Instant time5 = ((ExerciseRoute.Location) next4).getTime();
                                if (time4.compareTo(time5) < 0) {
                                    next3 = next4;
                                    time4 = time5;
                                }
                            } while (it3.hasNext());
                        }
                        Instant time6 = ((ExerciseRoute.Location) next3).getTime();
                        if (time3.isBefore(getStartTime()) || !time6.isBefore(getEndTime())) {
                            throw new IllegalArgumentException("route can not be out of parent time range.".toString());
                        }
                        return;
                    }
                    throw new NoSuchElementException();
                }
                throw new NoSuchElementException();
            }
            return;
        }
        throw new IllegalArgumentException("startTime must be before endTime.".toString());
    }

    public /* synthetic */ ExerciseSessionRecord(Instant instant, ZoneOffset zoneOffset, Instant instant2, ZoneOffset zoneOffset2, int i2, String str, String str2, androidx.health.connect.client.records.metadata.Metadata metadata, List list, List list2, ExerciseRouteResult exerciseRouteResult, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(instant, zoneOffset, instant2, zoneOffset2, i2, (i3 & 32) != 0 ? null : str, (i3 & 64) != 0 ? null : str2, (i3 & 128) != 0 ? androidx.health.connect.client.records.metadata.Metadata.EMPTY : metadata, (List<ExerciseSegment>) ((i3 & 256) != 0 ? CollectionsKt.emptyList() : list), (List<ExerciseLap>) ((i3 & 512) != 0 ? CollectionsKt.emptyList() : list2), (i3 & 1024) != 0 ? new ExerciseRouteResult.NoData() : exerciseRouteResult);
    }

    public /* synthetic */ ExerciseSessionRecord(Instant instant, ZoneOffset zoneOffset, Instant instant2, ZoneOffset zoneOffset2, int i2, String str, String str2, androidx.health.connect.client.records.metadata.Metadata metadata, List list, List list2, ExerciseRoute exerciseRoute, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(instant, zoneOffset, instant2, zoneOffset2, i2, (i3 & 32) != 0 ? null : str, (i3 & 64) != 0 ? null : str2, (i3 & 128) != 0 ? androidx.health.connect.client.records.metadata.Metadata.EMPTY : metadata, (List<ExerciseSegment>) ((i3 & 256) != 0 ? CollectionsKt.emptyList() : list), (List<ExerciseLap>) ((i3 & 512) != 0 ? CollectionsKt.emptyList() : list2), (i3 & 1024) != 0 ? null : exerciseRoute);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public ExerciseSessionRecord(@NotNull Instant startTime, @Nullable ZoneOffset zoneOffset, @NotNull Instant endTime, @Nullable ZoneOffset zoneOffset2, int i2, @Nullable String str, @Nullable String str2, @NotNull androidx.health.connect.client.records.metadata.Metadata metadata, @NotNull List<ExerciseSegment> segments, @NotNull List<ExerciseLap> laps, @Nullable ExerciseRoute exerciseRoute) {
        this(startTime, zoneOffset, endTime, zoneOffset2, i2, str, str2, metadata, segments, laps, exerciseRoute != null ? new ExerciseRouteResult.Data(exerciseRoute) : new ExerciseRouteResult.NoData());
        Intrinsics.checkNotNullParameter(startTime, "startTime");
        Intrinsics.checkNotNullParameter(endTime, "endTime");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Intrinsics.checkNotNullParameter(segments, "segments");
        Intrinsics.checkNotNullParameter(laps, "laps");
    }
}
