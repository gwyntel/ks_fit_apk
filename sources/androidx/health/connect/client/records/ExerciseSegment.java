package androidx.health.connect.client.records;

import androidx.annotation.RestrictTo;
import com.aliyun.alink.linksdk.tools.ut.AUserTrack;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.annotation.AnnotationRetention;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\bJ\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0014\u001a\u00020\u0006H\u0016J\u0015\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\u0017R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0005\u001a\u00020\u0006¢\u0006\u000e\n\u0000\u0012\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\n¨\u0006\u0019"}, d2 = {"Landroidx/health/connect/client/records/ExerciseSegment;", "", "startTime", "Ljava/time/Instant;", AUserTrack.UTKEY_END_TIME, "segmentType", "", "repetitions", "(Ljava/time/Instant;Ljava/time/Instant;II)V", "getEndTime", "()Ljava/time/Instant;", "getRepetitions", "()I", "getSegmentType$annotations", "()V", "getSegmentType", "getStartTime", "equals", "", "other", "hashCode", "isCompatibleWith", "sessionType", "isCompatibleWith$connect_client_release", "Companion", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nExerciseSegment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ExerciseSegment.kt\nandroidx/health/connect/client/records/ExerciseSegment\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,479:1\n1#2:480\n*E\n"})
/* loaded from: classes.dex */
public final class ExerciseSegment {

    @NotNull
    private static final Set<Integer> EXERCISE_SEGMENTS;
    public static final int EXERCISE_SEGMENT_TYPE_ARM_CURL = 1;
    public static final int EXERCISE_SEGMENT_TYPE_BACK_EXTENSION = 2;
    public static final int EXERCISE_SEGMENT_TYPE_BALL_SLAM = 3;
    public static final int EXERCISE_SEGMENT_TYPE_BARBELL_SHOULDER_PRESS = 4;
    public static final int EXERCISE_SEGMENT_TYPE_BENCH_PRESS = 5;
    public static final int EXERCISE_SEGMENT_TYPE_BENCH_SIT_UP = 6;
    public static final int EXERCISE_SEGMENT_TYPE_BIKING = 7;
    public static final int EXERCISE_SEGMENT_TYPE_BIKING_STATIONARY = 8;
    public static final int EXERCISE_SEGMENT_TYPE_BURPEE = 9;
    public static final int EXERCISE_SEGMENT_TYPE_CRUNCH = 10;
    public static final int EXERCISE_SEGMENT_TYPE_DEADLIFT = 11;
    public static final int EXERCISE_SEGMENT_TYPE_DOUBLE_ARM_TRICEPS_EXTENSION = 12;
    public static final int EXERCISE_SEGMENT_TYPE_DUMBBELL_CURL_LEFT_ARM = 13;
    public static final int EXERCISE_SEGMENT_TYPE_DUMBBELL_CURL_RIGHT_ARM = 14;
    public static final int EXERCISE_SEGMENT_TYPE_DUMBBELL_FRONT_RAISE = 15;
    public static final int EXERCISE_SEGMENT_TYPE_DUMBBELL_LATERAL_RAISE = 16;
    public static final int EXERCISE_SEGMENT_TYPE_DUMBBELL_ROW = 17;
    public static final int EXERCISE_SEGMENT_TYPE_DUMBBELL_TRICEPS_EXTENSION_LEFT_ARM = 18;
    public static final int EXERCISE_SEGMENT_TYPE_DUMBBELL_TRICEPS_EXTENSION_RIGHT_ARM = 19;
    public static final int EXERCISE_SEGMENT_TYPE_DUMBBELL_TRICEPS_EXTENSION_TWO_ARM = 20;
    public static final int EXERCISE_SEGMENT_TYPE_ELLIPTICAL = 21;
    public static final int EXERCISE_SEGMENT_TYPE_FORWARD_TWIST = 22;
    public static final int EXERCISE_SEGMENT_TYPE_FRONT_RAISE = 23;
    public static final int EXERCISE_SEGMENT_TYPE_HIGH_INTENSITY_INTERVAL_TRAINING = 24;
    public static final int EXERCISE_SEGMENT_TYPE_HIP_THRUST = 25;
    public static final int EXERCISE_SEGMENT_TYPE_HULA_HOOP = 26;
    public static final int EXERCISE_SEGMENT_TYPE_JUMPING_JACK = 27;
    public static final int EXERCISE_SEGMENT_TYPE_JUMP_ROPE = 28;
    public static final int EXERCISE_SEGMENT_TYPE_KETTLEBELL_SWING = 29;
    public static final int EXERCISE_SEGMENT_TYPE_LATERAL_RAISE = 30;
    public static final int EXERCISE_SEGMENT_TYPE_LAT_PULL_DOWN = 31;
    public static final int EXERCISE_SEGMENT_TYPE_LEG_CURL = 32;
    public static final int EXERCISE_SEGMENT_TYPE_LEG_EXTENSION = 33;
    public static final int EXERCISE_SEGMENT_TYPE_LEG_PRESS = 34;
    public static final int EXERCISE_SEGMENT_TYPE_LEG_RAISE = 35;
    public static final int EXERCISE_SEGMENT_TYPE_LUNGE = 36;
    public static final int EXERCISE_SEGMENT_TYPE_MOUNTAIN_CLIMBER = 37;
    public static final int EXERCISE_SEGMENT_TYPE_OTHER_WORKOUT = 38;
    public static final int EXERCISE_SEGMENT_TYPE_PAUSE = 39;
    public static final int EXERCISE_SEGMENT_TYPE_PILATES = 40;
    public static final int EXERCISE_SEGMENT_TYPE_PLANK = 41;
    public static final int EXERCISE_SEGMENT_TYPE_PULL_UP = 42;
    public static final int EXERCISE_SEGMENT_TYPE_PUNCH = 43;
    public static final int EXERCISE_SEGMENT_TYPE_REST = 44;
    public static final int EXERCISE_SEGMENT_TYPE_ROWING_MACHINE = 45;
    public static final int EXERCISE_SEGMENT_TYPE_RUNNING = 46;
    public static final int EXERCISE_SEGMENT_TYPE_RUNNING_TREADMILL = 47;
    public static final int EXERCISE_SEGMENT_TYPE_SHOULDER_PRESS = 48;
    public static final int EXERCISE_SEGMENT_TYPE_SINGLE_ARM_TRICEPS_EXTENSION = 49;
    public static final int EXERCISE_SEGMENT_TYPE_SIT_UP = 50;
    public static final int EXERCISE_SEGMENT_TYPE_SQUAT = 51;
    public static final int EXERCISE_SEGMENT_TYPE_STAIR_CLIMBING = 52;
    public static final int EXERCISE_SEGMENT_TYPE_STAIR_CLIMBING_MACHINE = 53;
    public static final int EXERCISE_SEGMENT_TYPE_STRETCHING = 54;
    public static final int EXERCISE_SEGMENT_TYPE_SWIMMING_BACKSTROKE = 55;
    public static final int EXERCISE_SEGMENT_TYPE_SWIMMING_BREASTSTROKE = 56;
    public static final int EXERCISE_SEGMENT_TYPE_SWIMMING_BUTTERFLY = 57;
    public static final int EXERCISE_SEGMENT_TYPE_SWIMMING_FREESTYLE = 58;
    public static final int EXERCISE_SEGMENT_TYPE_SWIMMING_MIXED = 59;
    public static final int EXERCISE_SEGMENT_TYPE_SWIMMING_OPEN_WATER = 60;
    public static final int EXERCISE_SEGMENT_TYPE_SWIMMING_OTHER = 61;
    public static final int EXERCISE_SEGMENT_TYPE_SWIMMING_POOL = 62;
    public static final int EXERCISE_SEGMENT_TYPE_UNKNOWN = 0;
    public static final int EXERCISE_SEGMENT_TYPE_UPPER_TWIST = 63;
    public static final int EXERCISE_SEGMENT_TYPE_WALKING = 64;
    public static final int EXERCISE_SEGMENT_TYPE_WEIGHTLIFTING = 65;
    public static final int EXERCISE_SEGMENT_TYPE_WHEELCHAIR = 66;
    public static final int EXERCISE_SEGMENT_TYPE_YOGA = 67;

    @NotNull
    private static final Map<Integer, Set<Integer>> SESSION_TO_SEGMENTS_MAPPING;

    @NotNull
    private static final Set<Integer> SWIMMING_SEGMENTS;

    @NotNull
    private final Instant endTime;
    private final int repetitions;
    private final int segmentType;

    @NotNull
    private final Instant startTime;

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final Set<Integer> UNIVERSAL_SESSION_TYPES = SetsKt.setOf((Object[]) new Integer[]{10, 36, 0});

    @NotNull
    private static final Set<Integer> UNIVERSAL_SEGMENTS = SetsKt.setOf((Object[]) new Integer[]{38, 39, 44, 54, 0});

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\b\n\u0002\bG\n\u0002\u0010$\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001TB\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00107\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00108\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010A\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010B\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010C\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010D\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010E\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010F\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010G\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010H\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010I\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010J\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010K\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R \u0010L\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040MX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010N\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\bO\u0010\u0007R\u001a\u0010P\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\bQ\u0010\u0007R\u001a\u0010R\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\bS\u0010\u0007¨\u0006U"}, d2 = {"Landroidx/health/connect/client/records/ExerciseSegment$Companion;", "", "()V", "EXERCISE_SEGMENTS", "", "", "getEXERCISE_SEGMENTS$connect_client_release", "()Ljava/util/Set;", "EXERCISE_SEGMENT_TYPE_ARM_CURL", "EXERCISE_SEGMENT_TYPE_BACK_EXTENSION", "EXERCISE_SEGMENT_TYPE_BALL_SLAM", "EXERCISE_SEGMENT_TYPE_BARBELL_SHOULDER_PRESS", "EXERCISE_SEGMENT_TYPE_BENCH_PRESS", "EXERCISE_SEGMENT_TYPE_BENCH_SIT_UP", "EXERCISE_SEGMENT_TYPE_BIKING", "EXERCISE_SEGMENT_TYPE_BIKING_STATIONARY", "EXERCISE_SEGMENT_TYPE_BURPEE", "EXERCISE_SEGMENT_TYPE_CRUNCH", "EXERCISE_SEGMENT_TYPE_DEADLIFT", "EXERCISE_SEGMENT_TYPE_DOUBLE_ARM_TRICEPS_EXTENSION", "EXERCISE_SEGMENT_TYPE_DUMBBELL_CURL_LEFT_ARM", "EXERCISE_SEGMENT_TYPE_DUMBBELL_CURL_RIGHT_ARM", "EXERCISE_SEGMENT_TYPE_DUMBBELL_FRONT_RAISE", "EXERCISE_SEGMENT_TYPE_DUMBBELL_LATERAL_RAISE", "EXERCISE_SEGMENT_TYPE_DUMBBELL_ROW", "EXERCISE_SEGMENT_TYPE_DUMBBELL_TRICEPS_EXTENSION_LEFT_ARM", "EXERCISE_SEGMENT_TYPE_DUMBBELL_TRICEPS_EXTENSION_RIGHT_ARM", "EXERCISE_SEGMENT_TYPE_DUMBBELL_TRICEPS_EXTENSION_TWO_ARM", "EXERCISE_SEGMENT_TYPE_ELLIPTICAL", "EXERCISE_SEGMENT_TYPE_FORWARD_TWIST", "EXERCISE_SEGMENT_TYPE_FRONT_RAISE", "EXERCISE_SEGMENT_TYPE_HIGH_INTENSITY_INTERVAL_TRAINING", "EXERCISE_SEGMENT_TYPE_HIP_THRUST", "EXERCISE_SEGMENT_TYPE_HULA_HOOP", "EXERCISE_SEGMENT_TYPE_JUMPING_JACK", "EXERCISE_SEGMENT_TYPE_JUMP_ROPE", "EXERCISE_SEGMENT_TYPE_KETTLEBELL_SWING", "EXERCISE_SEGMENT_TYPE_LATERAL_RAISE", "EXERCISE_SEGMENT_TYPE_LAT_PULL_DOWN", "EXERCISE_SEGMENT_TYPE_LEG_CURL", "EXERCISE_SEGMENT_TYPE_LEG_EXTENSION", "EXERCISE_SEGMENT_TYPE_LEG_PRESS", "EXERCISE_SEGMENT_TYPE_LEG_RAISE", "EXERCISE_SEGMENT_TYPE_LUNGE", "EXERCISE_SEGMENT_TYPE_MOUNTAIN_CLIMBER", "EXERCISE_SEGMENT_TYPE_OTHER_WORKOUT", "EXERCISE_SEGMENT_TYPE_PAUSE", "EXERCISE_SEGMENT_TYPE_PILATES", "EXERCISE_SEGMENT_TYPE_PLANK", "EXERCISE_SEGMENT_TYPE_PULL_UP", "EXERCISE_SEGMENT_TYPE_PUNCH", "EXERCISE_SEGMENT_TYPE_REST", "EXERCISE_SEGMENT_TYPE_ROWING_MACHINE", "EXERCISE_SEGMENT_TYPE_RUNNING", "EXERCISE_SEGMENT_TYPE_RUNNING_TREADMILL", "EXERCISE_SEGMENT_TYPE_SHOULDER_PRESS", "EXERCISE_SEGMENT_TYPE_SINGLE_ARM_TRICEPS_EXTENSION", "EXERCISE_SEGMENT_TYPE_SIT_UP", "EXERCISE_SEGMENT_TYPE_SQUAT", "EXERCISE_SEGMENT_TYPE_STAIR_CLIMBING", "EXERCISE_SEGMENT_TYPE_STAIR_CLIMBING_MACHINE", "EXERCISE_SEGMENT_TYPE_STRETCHING", "EXERCISE_SEGMENT_TYPE_SWIMMING_BACKSTROKE", "EXERCISE_SEGMENT_TYPE_SWIMMING_BREASTSTROKE", "EXERCISE_SEGMENT_TYPE_SWIMMING_BUTTERFLY", "EXERCISE_SEGMENT_TYPE_SWIMMING_FREESTYLE", "EXERCISE_SEGMENT_TYPE_SWIMMING_MIXED", "EXERCISE_SEGMENT_TYPE_SWIMMING_OPEN_WATER", "EXERCISE_SEGMENT_TYPE_SWIMMING_OTHER", "EXERCISE_SEGMENT_TYPE_SWIMMING_POOL", "EXERCISE_SEGMENT_TYPE_UNKNOWN", "EXERCISE_SEGMENT_TYPE_UPPER_TWIST", "EXERCISE_SEGMENT_TYPE_WALKING", "EXERCISE_SEGMENT_TYPE_WEIGHTLIFTING", "EXERCISE_SEGMENT_TYPE_WHEELCHAIR", "EXERCISE_SEGMENT_TYPE_YOGA", "SESSION_TO_SEGMENTS_MAPPING", "", "SWIMMING_SEGMENTS", "getSWIMMING_SEGMENTS$connect_client_release", "UNIVERSAL_SEGMENTS", "getUNIVERSAL_SEGMENTS$connect_client_release", "UNIVERSAL_SESSION_TYPES", "getUNIVERSAL_SESSION_TYPES$connect_client_release", "ExerciseSegmentTypes", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {

        @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Landroidx/health/connect/client/records/ExerciseSegment$Companion$ExerciseSegmentTypes;", "", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        @Retention(RetentionPolicy.SOURCE)
        @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public @interface ExerciseSegmentTypes {
        }

        private Companion() {
        }

        @NotNull
        public final Set<Integer> getEXERCISE_SEGMENTS$connect_client_release() {
            return ExerciseSegment.EXERCISE_SEGMENTS;
        }

        @NotNull
        public final Set<Integer> getSWIMMING_SEGMENTS$connect_client_release() {
            return ExerciseSegment.SWIMMING_SEGMENTS;
        }

        @NotNull
        public final Set<Integer> getUNIVERSAL_SEGMENTS$connect_client_release() {
            return ExerciseSegment.UNIVERSAL_SEGMENTS;
        }

        @NotNull
        public final Set<Integer> getUNIVERSAL_SESSION_TYPES$connect_client_release() {
            return ExerciseSegment.UNIVERSAL_SESSION_TYPES;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        Set<Integer> of = SetsKt.setOf((Object[]) new Integer[]{1, 2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 22, 23, 25, 26, 28, 27, 29, 30, 31, 32, 33, 34, 35, 36, 37, 41, 42, 43, 48, 49, 50, 51, 63, 65});
        EXERCISE_SEGMENTS = of;
        Set<Integer> of2 = SetsKt.setOf((Object[]) new Integer[]{55, 56, 58, 57, 59, 61});
        SWIMMING_SEGMENTS = of2;
        Pair pair = TuplesKt.to(8, SetsKt.setOf(7));
        Pair pair2 = TuplesKt.to(9, SetsKt.setOf(8));
        Pair pair3 = TuplesKt.to(13, of);
        Pair pair4 = TuplesKt.to(25, SetsKt.setOf(21));
        Pair pair5 = TuplesKt.to(26, SetsKt.setOf((Object[]) new Integer[]{67, 8, 40, 24}));
        Pair pair6 = TuplesKt.to(34, of);
        Pair pair7 = TuplesKt.to(37, SetsKt.setOf((Object[]) new Integer[]{64, 66}));
        Pair pair8 = TuplesKt.to(48, SetsKt.setOf(40));
        Pair pair9 = TuplesKt.to(54, SetsKt.setOf(45));
        Pair pair10 = TuplesKt.to(56, SetsKt.setOf((Object[]) new Integer[]{46, 64}));
        Pair pair11 = TuplesKt.to(57, SetsKt.setOf(47));
        Pair pair12 = TuplesKt.to(70, of);
        Pair pair13 = TuplesKt.to(68, SetsKt.setOf(52));
        Pair pair14 = TuplesKt.to(69, SetsKt.setOf(53));
        Set setCreateSetBuilder = SetsKt.createSetBuilder();
        setCreateSetBuilder.add(60);
        setCreateSetBuilder.addAll(of2);
        Unit unit = Unit.INSTANCE;
        Pair pair15 = TuplesKt.to(73, SetsKt.build(setCreateSetBuilder));
        Set setCreateSetBuilder2 = SetsKt.createSetBuilder();
        setCreateSetBuilder2.add(62);
        setCreateSetBuilder2.addAll(of2);
        SESSION_TO_SEGMENTS_MAPPING = MapsKt.mapOf(pair, pair2, pair3, pair4, pair5, pair6, pair7, pair8, pair9, pair10, pair11, pair12, pair13, pair14, pair15, TuplesKt.to(74, SetsKt.build(setCreateSetBuilder2)), TuplesKt.to(79, SetsKt.setOf(64)), TuplesKt.to(82, SetsKt.setOf(66)), TuplesKt.to(81, of), TuplesKt.to(83, SetsKt.setOf(67)));
    }

    public ExerciseSegment(@NotNull Instant startTime, @NotNull Instant endTime, int i2, int i3) {
        Intrinsics.checkNotNullParameter(startTime, "startTime");
        Intrinsics.checkNotNullParameter(endTime, "endTime");
        this.startTime = startTime;
        this.endTime = endTime;
        this.segmentType = i2;
        this.repetitions = i3;
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("startTime must be before endTime.".toString());
        }
        if (i3 < 0) {
            throw new IllegalArgumentException("repetitions can not be negative.".toString());
        }
    }

    public static /* synthetic */ void getSegmentType$annotations() {
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExerciseSegment)) {
            return false;
        }
        ExerciseSegment exerciseSegment = (ExerciseSegment) other;
        return Intrinsics.areEqual(this.startTime, exerciseSegment.startTime) && Intrinsics.areEqual(this.endTime, exerciseSegment.endTime) && this.segmentType == exerciseSegment.segmentType && this.repetitions == exerciseSegment.repetitions;
    }

    @NotNull
    public final Instant getEndTime() {
        return this.endTime;
    }

    public final int getRepetitions() {
        return this.repetitions;
    }

    public final int getSegmentType() {
        return this.segmentType;
    }

    @NotNull
    public final Instant getStartTime() {
        return this.startTime;
    }

    public int hashCode() {
        return (((((this.startTime.hashCode() * 31) + this.endTime.hashCode()) * 31) + this.segmentType) * 31) + this.repetitions;
    }

    public final boolean isCompatibleWith$connect_client_release(int sessionType) {
        if (UNIVERSAL_SESSION_TYPES.contains(Integer.valueOf(sessionType)) || UNIVERSAL_SEGMENTS.contains(Integer.valueOf(this.segmentType))) {
            return true;
        }
        Set<Integer> set = SESSION_TO_SEGMENTS_MAPPING.get(Integer.valueOf(sessionType));
        if (set != null) {
            return set.contains(Integer.valueOf(this.segmentType));
        }
        return false;
    }

    public /* synthetic */ ExerciseSegment(Instant instant, Instant instant2, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(instant, instant2, i2, (i4 & 8) != 0 ? 0 : i3);
    }
}
