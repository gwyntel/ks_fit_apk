package cachet.plugins.health;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.health.connect.client.HealthConnectClient;
import androidx.health.connect.client.PermissionController;
import androidx.health.connect.client.aggregate.AggregationResult;
import androidx.health.connect.client.permission.HealthPermission;
import androidx.health.connect.client.records.ActiveCaloriesBurnedRecord;
import androidx.health.connect.client.records.BasalMetabolicRateRecord;
import androidx.health.connect.client.records.BloodGlucoseRecord;
import androidx.health.connect.client.records.BloodPressureRecord;
import androidx.health.connect.client.records.BodyFatRecord;
import androidx.health.connect.client.records.BodyTemperatureRecord;
import androidx.health.connect.client.records.DistanceRecord;
import androidx.health.connect.client.records.ExerciseRoute;
import androidx.health.connect.client.records.ExerciseSessionRecord;
import androidx.health.connect.client.records.FloorsClimbedRecord;
import androidx.health.connect.client.records.HeartRateRecord;
import androidx.health.connect.client.records.HeightRecord;
import androidx.health.connect.client.records.HydrationRecord;
import androidx.health.connect.client.records.OxygenSaturationRecord;
import androidx.health.connect.client.records.Record;
import androidx.health.connect.client.records.RespiratoryRateRecord;
import androidx.health.connect.client.records.RestingHeartRateRecord;
import androidx.health.connect.client.records.SleepSessionRecord;
import androidx.health.connect.client.records.SleepStageRecord;
import androidx.health.connect.client.records.StepsRecord;
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord;
import androidx.health.connect.client.records.WeightRecord;
import androidx.health.connect.client.request.AggregateRequest;
import androidx.health.connect.client.time.TimeRangeFilter;
import androidx.health.connect.client.units.BloodGlucose;
import androidx.health.connect.client.units.Energy;
import androidx.health.connect.client.units.Length;
import androidx.health.connect.client.units.Mass;
import androidx.health.connect.client.units.Percentage;
import androidx.health.connect.client.units.Power;
import androidx.health.connect.client.units.Pressure;
import androidx.health.connect.client.units.Temperature;
import androidx.health.connect.client.units.Volume;
import androidx.media3.extractor.text.ttml.TtmlNode;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.aliyun.alink.linksdk.tools.ut.AUserTrack;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessActivities;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Device;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.HealthDataTypes;
import com.google.android.gms.fitness.data.HealthFields;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataDeleteRequest;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.SessionInsertRequest;
import com.google.android.gms.fitness.request.SessionReadRequest;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.fitness.result.SessionReadResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.leeson.image_pickers.activitys.SelectPicsActivity;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KClass;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.coroutines.debug.internal.DebugCoroutineInfoImplKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0082\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 ©\u00012\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u0005:\u0002©\u0001B\u0011\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010R\u001a\u00020S2\u0006\u0010T\u001a\u00020UH\u0002J\u0006\u0010V\u001a\u00020WJ(\u0010X\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020Z0M0Y2\u0006\u0010[\u001a\u00020Z2\u0006\u0010\\\u001a\u00020\nJ.\u0010]\u001a\u0010\u0012\f\u0012\n `*\u0004\u0018\u00010_0_0^2\u0006\u0010\\\u001a\u00020a2\u0006\u0010b\u001a\u00020c2\u0006\u0010d\u001a\u00020\u0003H\u0002J\u0018\u0010e\u001a\u00020W2\u0006\u0010T\u001a\u00020U2\u0006\u0010d\u001a\u00020\u0003H\u0002J\u0016\u0010f\u001a\u00020W2\u0006\u0010T\u001a\u00020U2\u0006\u0010d\u001a\u00020\u0003J\u0018\u0010g\u001a\u00020h2\u0006\u0010d\u001a\u00020\u00032\u0006\u0010i\u001a\u00020\nH\u0002J$\u0010j\u001a\u00020W2\u0006\u0010k\u001a\u00020\n2\b\u0010l\u001a\u0004\u0018\u00010\n2\b\u0010m\u001a\u0004\u0018\u00010ZH\u0016J\u0010\u0010n\u001a\u00020\n2\u0006\u0010o\u001a\u00020\nH\u0002J\u0018\u0010p\u001a\u00020W2\u0006\u0010T\u001a\u00020U2\u0006\u0010d\u001a\u00020\u0003H\u0002J\u0010\u0010q\u001a\u00020c2\u0006\u0010o\u001a\u00020\nH\u0002J\u0016\u0010r\u001a\u00020W2\u0006\u0010T\u001a\u00020U2\u0006\u0010d\u001a\u00020\u0003J\u0018\u0010s\u001a\u00020W2\u0006\u0010T\u001a\u00020U2\u0006\u0010d\u001a\u00020\u0003H\u0002J\u0018\u0010t\u001a\u00020Z2\u0006\u0010u\u001a\u00020v2\u0006\u0010b\u001a\u00020cH\u0002J \u0010w\u001a\u00020x2\u0006\u0010y\u001a\u00020z2\u0006\u0010{\u001a\u00020z2\u0006\u0010d\u001a\u00020\u0003H\u0002J6\u0010|\u001a\u0010\u0012\f\u0012\n `*\u0004\u0018\u00010_0_0^2\u0006\u0010y\u001a\u00020z2\u0006\u0010{\u001a\u00020z2\u0006\u0010}\u001a\u00020a2\u0006\u0010d\u001a\u00020\u0003H\u0002J\u0018\u0010~\u001a\u00020W2\u0006\u0010T\u001a\u00020U2\u0006\u0010d\u001a\u00020\u0003H\u0002J\u0018\u0010\u007f\u001a\u00020W2\u0006\u0010T\u001a\u00020U2\u0006\u0010d\u001a\u00020\u0003H\u0002J\u0019\u0010\u0080\u0001\u001a\u00020W2\u0006\u0010T\u001a\u00020U2\u0006\u0010d\u001a\u00020\u0003H\u0002J\u0016\u00106\u001a\u00020W2\u0006\u0010T\u001a\u00020U2\u0006\u0010d\u001a\u00020\u0003J\u0019\u0010\u0081\u0001\u001a\u00020W2\u0006\u0010T\u001a\u00020U2\u0006\u0010d\u001a\u00020\u0003H\u0002J\u001c\u0010\u0082\u0001\u001a\u0002072\b\u0010\u0083\u0001\u001a\u00030\u0084\u00012\u0007\u0010\u0085\u0001\u001a\u00020cH\u0002J\u0011\u0010\u0086\u0001\u001a\u00020a2\u0006\u0010o\u001a\u00020\nH\u0002J\t\u0010\u0087\u0001\u001a\u00020WH\u0016J'\u0010\u0088\u0001\u001a\u0002072\u0007\u0010\u0089\u0001\u001a\u00020\u001a2\u0007\u0010\u008a\u0001\u001a\u00020\u001a2\n\u0010\u008b\u0001\u001a\u0005\u0018\u00010\u008c\u0001H\u0016J\u0013\u0010\u008d\u0001\u001a\u00020W2\b\u0010\u008e\u0001\u001a\u00030\u008f\u0001H\u0016J\u0015\u0010\u0090\u0001\u001a\u00020W2\n\b\u0001\u0010\u0091\u0001\u001a\u00030\u0092\u0001H\u0016J\t\u0010\u0093\u0001\u001a\u00020WH\u0016J\t\u0010\u0094\u0001\u001a\u00020WH\u0016J\u0013\u0010\u0095\u0001\u001a\u00020W2\b\u0010\u008e\u0001\u001a\u00030\u0092\u0001H\u0016J\u0018\u0010\u0096\u0001\u001a\u00020W2\r\u0010\u0097\u0001\u001a\b\u0012\u0004\u0012\u00020\n0@H\u0002J\u0019\u0010\u0098\u0001\u001a\u00020W2\u0006\u0010T\u001a\u00020U2\u0006\u0010d\u001a\u00020\u0003H\u0016J\u0013\u0010\u0099\u0001\u001a\u00020W2\b\u0010\u008e\u0001\u001a\u00030\u008f\u0001H\u0016J\u0019\u0010\u009a\u0001\u001a\u00020W2\u0006\u0010T\u001a\u00020U2\u0006\u0010d\u001a\u00020\u0003H\u0002J\u0019\u0010\u009b\u0001\u001a\u00020W2\u0006\u0010T\u001a\u00020U2\u0006\u0010d\u001a\u00020\u0003H\u0002J\u0019\u0010\u009c\u0001\u001a\u00020W2\u0006\u0010T\u001a\u00020U2\u0006\u0010d\u001a\u00020\u0003H\u0002J)\u0010\u009d\u0001\u001a\u0012\u0012\u000e\u0012\f `*\u0005\u0018\u00010\u009e\u00010\u009e\u00010^2\u0006\u0010o\u001a\u00020\n2\u0006\u0010d\u001a\u00020\u0003H\u0002J\u0014\u0010\u009f\u0001\u001a\u00020W2\t\u0010 \u0001\u001a\u0004\u0018\u00010ZH\u0016J\u0016\u0010K\u001a\u00020W2\u0006\u0010T\u001a\u00020U2\u0006\u0010d\u001a\u00020\u0003J)\u0010¡\u0001\u001a\u0012\u0012\u000e\u0012\f `*\u0005\u0018\u00010\u009e\u00010\u009e\u00010^2\u0006\u0010o\u001a\u00020\n2\u0006\u0010d\u001a\u00020\u0003H\u0002J\u0019\u0010¢\u0001\u001a\u00020W2\u0006\u0010T\u001a\u00020U2\u0006\u0010d\u001a\u00020\u0003H\u0002J\u0019\u0010£\u0001\u001a\u00020W2\u0006\u0010T\u001a\u00020U2\u0006\u0010d\u001a\u00020\u0003H\u0002J\u0017\u0010¤\u0001\u001a\u00020W2\u0006\u0010T\u001a\u00020U2\u0006\u0010d\u001a\u00020\u0003J\u0019\u0010¥\u0001\u001a\u00020W2\u0006\u0010T\u001a\u00020U2\u0006\u0010d\u001a\u00020\u0003H\u0002J\u0017\u0010¦\u0001\u001a\u00020W2\u0006\u0010T\u001a\u00020U2\u0006\u0010d\u001a\u00020\u0003J\u0019\u0010§\u0001\u001a\u00020W2\u0006\u0010T\u001a\u00020U2\u0006\u0010d\u001a\u00020\u0003H\u0002J\u0017\u0010¨\u0001\u001a\u00020W2\u0006\u0010T\u001a\u00020U2\u0006\u0010d\u001a\u00020\u0003R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R-\u0010\u0018\u001a\u001e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\n0\u0019j\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\n`\u001b¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR=\u0010\u001e\u001a.\u0012\u0004\u0012\u00020\n\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020 0\u001f0\u0019j\u0016\u0012\u0004\u0012\u00020\n\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020 0\u001f`\u001b¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001dR\u000e\u0010\"\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00100\u001a\u0004\u0018\u000101X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00102\u001a\u0004\u0018\u000103X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00104\u001a\u0004\u0018\u000105X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u00106\u001a\u000207X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b8\u00109\"\u0004\b:\u0010;R\u000e\u0010<\u001a\u00020=X\u0082.¢\u0006\u0002\n\u0000R\u001c\u0010>\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0@\u0018\u00010?X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010A\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\u0010\u0010F\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010G\u001a\u00020HX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010I\u001a\u0004\u0018\u00010JX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010K\u001a\u000207X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010L\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0M¢\u0006\b\n\u0000\u001a\u0004\bN\u0010OR\u001d\u0010P\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u001a0M¢\u0006\b\n\u0000\u001a\u0004\bQ\u0010O¨\u0006ª\u0001"}, d2 = {"Lcachet/plugins/health/HealthPlugin;", "Lio/flutter/plugin/common/MethodChannel$MethodCallHandler;", "Lio/flutter/plugin/common/PluginRegistry$ActivityResultListener;", "Lio/flutter/plugin/common/MethodChannel$Result;", "Lio/flutter/embedding/engine/plugins/activity/ActivityAware;", "Lio/flutter/embedding/engine/plugins/FlutterPlugin;", "channel", "Lio/flutter/plugin/common/MethodChannel;", "(Lio/flutter/plugin/common/MethodChannel;)V", "ACTIVE_ENERGY_BURNED", "", "AGGREGATE_STEP_COUNT", "BASAL_ENERGY_BURNED", "BLOOD_GLUCOSE", "BLOOD_OXYGEN", "BLOOD_PRESSURE_DIASTOLIC", "BLOOD_PRESSURE_SYSTOLIC", "BODY_FAT_PERCENTAGE", "BODY_TEMPERATURE", "DISTANCE_DELTA", "FLIGHTS_CLIMBED", "HEART_RATE", SelectPicsActivity.HEIGHT, "MOVE_MINUTES", "MapSleepStageToType", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "getMapSleepStageToType", "()Ljava/util/HashMap;", "MapToHCType", "Lkotlin/reflect/KClass;", "Landroidx/health/connect/client/records/Record;", "getMapToHCType", "RESPIRATORY_RATE", "RESTING_HEART_RATE", "SLEEP_ASLEEP", "SLEEP_AWAKE", "SLEEP_DEEP", "SLEEP_IN_BED", "SLEEP_LIGHT", "SLEEP_OUT_OF_BED", "SLEEP_REM", "SLEEP_SESSION", "STEPS", "WATER", "WEIGHT", "WORKOUT", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", com.umeng.analytics.pro.f.X, "Landroid/content/Context;", "handler", "Landroid/os/Handler;", "healthConnectAvailable", "", "getHealthConnectAvailable", "()Z", "setHealthConnectAvailable", "(Z)V", "healthConnectClient", "Landroidx/health/connect/client/HealthConnectClient;", "healthConnectRequestPermissionsLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "healthConnectStatus", "getHealthConnectStatus", "()I", "setHealthConnectStatus", "(I)V", "mResult", "scope", "Lkotlinx/coroutines/CoroutineScope;", "threadPoolExecutor", "Ljava/util/concurrent/ExecutorService;", "useHealthConnectIfAvailable", "workoutTypeMap", "", "getWorkoutTypeMap", "()Ljava/util/Map;", "workoutTypeMapHealthConnect", "getWorkoutTypeMapHealthConnect", "callToHealthTypes", "Lcom/google/android/gms/fitness/FitnessOptions;", NotificationCompat.CATEGORY_CALL, "Lio/flutter/plugin/common/MethodCall;", "checkAvailability", "", "convertRecord", "", "", "record", "dataType", "dataHandler", "Lcom/google/android/gms/tasks/OnSuccessListener;", "Lcom/google/android/gms/fitness/result/DataReadResponse;", "kotlin.jvm.PlatformType", "Lcom/google/android/gms/fitness/data/DataType;", "field", "Lcom/google/android/gms/fitness/data/Field;", "result", RequestParameters.SUBRESOURCE_DELETE, "deleteHCData", "errHandler", "Lcom/google/android/gms/tasks/OnFailureListener;", "addMessage", "error", "errorCode", "errorMessage", "errorDetails", "getActivityType", "type", "getData", "getField", "getHCData", "getHealthConnectSdkStatus", "getHealthDataValue", "dataPoint", "Lcom/google/android/gms/fitness/data/DataPoint;", "getStepsHealthConnect", "Lkotlinx/coroutines/Job;", "start", "", TtmlNode.END, "getStepsInRange", "aggregatedDataType", "getTotalStepsInInterval", "hasPermissions", "hasPermissionsHC", "installHealthConnect", "isIntField", "dataSource", "Lcom/google/android/gms/fitness/data/DataSource;", "unit", "keyToHealthDataType", "notImplemented", "onActivityResult", "requestCode", com.taobao.agoo.a.a.b.JSON_ERRORCODE, "data", "Landroid/content/Intent;", "onAttachedToActivity", "binding", "Lio/flutter/embedding/engine/plugins/activity/ActivityPluginBinding;", "onAttachedToEngine", "flutterPluginBinding", "Lio/flutter/embedding/engine/plugins/FlutterPlugin$FlutterPluginBinding;", "onDetachedFromActivity", "onDetachedFromActivityForConfigChanges", "onDetachedFromEngine", "onHealthConnectPermissionCallback", "permissionGranted", "onMethodCall", "onReattachedToActivityForConfigChanges", "requestAuthorization", "requestAuthorizationHC", "revokePermissions", "sleepDataHandler", "Lcom/google/android/gms/fitness/result/SessionReadResponse;", "success", "p0", "workoutDataHandler", "writeBloodOxygen", "writeBloodPressure", "writeBloodPressureHC", "writeData", "writeHCData", "writeWorkoutData", "writeWorkoutHCData", "Companion", "health_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nHealthPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HealthPlugin.kt\ncachet/plugins/health/HealthPlugin\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,2295:1\n800#2,11:2296\n800#2,11:2307\n800#2,11:2318\n800#2,11:2329\n800#2,11:2340\n800#2,11:2351\n1549#2:2362\n1620#2,3:2363\n1559#2:2366\n1590#2,4:2367\n1#3:2371\n483#4,7:2372\n*S KotlinDebug\n*F\n+ 1 HealthPlugin.kt\ncachet/plugins/health/HealthPlugin\n*L\n1170#1:2296,11\n1171#1:2307,11\n1525#1:2318,11\n1526#1:2329,11\n1594#1:2340,11\n1595#1:2351,11\n1792#1:2362\n1792#1:2363,3\n1000#1:2366\n1000#1:2367,4\n1149#1:2372,7\n*E\n"})
/* loaded from: classes2.dex */
public final class HealthPlugin implements MethodChannel.MethodCallHandler, PluginRegistry.ActivityResultListener, MethodChannel.Result, ActivityAware, FlutterPlugin {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private String ACTIVE_ENERGY_BURNED;

    @NotNull
    private String AGGREGATE_STEP_COUNT;

    @NotNull
    private String BASAL_ENERGY_BURNED;

    @NotNull
    private String BLOOD_GLUCOSE;

    @NotNull
    private String BLOOD_OXYGEN;

    @NotNull
    private String BLOOD_PRESSURE_DIASTOLIC;

    @NotNull
    private String BLOOD_PRESSURE_SYSTOLIC;

    @NotNull
    private String BODY_FAT_PERCENTAGE;

    @NotNull
    private String BODY_TEMPERATURE;

    @NotNull
    private String DISTANCE_DELTA;

    @NotNull
    private String FLIGHTS_CLIMBED;

    @NotNull
    private String HEART_RATE;

    @NotNull
    private String HEIGHT;

    @NotNull
    private String MOVE_MINUTES;

    @NotNull
    private final HashMap<Integer, String> MapSleepStageToType;

    @NotNull
    private final HashMap<String, KClass<? extends Record>> MapToHCType;

    @NotNull
    private String RESPIRATORY_RATE;

    @NotNull
    private String RESTING_HEART_RATE;

    @NotNull
    private String SLEEP_ASLEEP;

    @NotNull
    private String SLEEP_AWAKE;

    @NotNull
    private String SLEEP_DEEP;

    @NotNull
    private String SLEEP_IN_BED;

    @NotNull
    private String SLEEP_LIGHT;

    @NotNull
    private String SLEEP_OUT_OF_BED;

    @NotNull
    private String SLEEP_REM;

    @NotNull
    private String SLEEP_SESSION;

    @NotNull
    private String STEPS;

    @NotNull
    private String WATER;

    @NotNull
    private String WEIGHT;

    @NotNull
    private String WORKOUT;

    @Nullable
    private Activity activity;

    @Nullable
    private MethodChannel channel;

    @Nullable
    private Context context;

    @Nullable
    private Handler handler;
    private boolean healthConnectAvailable;
    private HealthConnectClient healthConnectClient;

    @Nullable
    private ActivityResultLauncher<Set<String>> healthConnectRequestPermissionsLauncher;
    private int healthConnectStatus;

    @Nullable
    private MethodChannel.Result mResult;
    private CoroutineScope scope;

    @Nullable
    private ExecutorService threadPoolExecutor;
    private boolean useHealthConnectIfAvailable;

    @NotNull
    private final Map<String, String> workoutTypeMap;

    @NotNull
    private final Map<String, Integer> workoutTypeMapHealthConnect;

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lcachet/plugins/health/HealthPlugin$Companion;", "", "()V", "registerWith", "", "registrar", "Lio/flutter/plugin/common/PluginRegistry$Registrar;", "health_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        @JvmStatic
        public final void registerWith(@NotNull PluginRegistry.Registrar registrar) {
            Intrinsics.checkNotNullParameter(registrar, "registrar");
            MethodChannel methodChannel = new MethodChannel(registrar.messenger(), HealthPluginKt.CHANNEL_NAME);
            HealthPlugin healthPlugin = new HealthPlugin(methodChannel);
            registrar.addActivityResultListener(healthPlugin);
            methodChannel.setMethodCallHandler(healthPlugin);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "cachet.plugins.health.HealthPlugin$deleteHCData$1", f = "HealthPlugin.kt", i = {}, l = {2187, 2191, 2195}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: cachet.plugins.health.HealthPlugin$deleteHCData$1, reason: invalid class name and case insensitive filesystem */
    static final class C03761 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Instant $endTime;
        final /* synthetic */ MethodChannel.Result $result;
        final /* synthetic */ Instant $startTime;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03761(Instant instant, Instant instant2, MethodChannel.Result result, Continuation<? super C03761> continuation) {
            super(2, continuation);
            this.$startTime = instant;
            this.$endTime = instant2;
            this.$result = result;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return HealthPlugin.this.new C03761(this.$startTime, this.$endTime, this.$result, continuation);
        }

        /* JADX WARN: Removed duplicated region for block: B:29:0x008e A[Catch: Exception -> 0x00bc, TryCatch #0 {Exception -> 0x00bc, blocks: (B:7:0x0018, B:34:0x00b2, B:11:0x0025, B:27:0x0086, B:29:0x008e, B:31:0x0093, B:12:0x0029, B:21:0x005b, B:23:0x0063, B:24:0x0067, B:15:0x0030, B:17:0x0038, B:18:0x003c), top: B:39:0x0010 }] */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0092  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x00b1 A[RETURN] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r13) throws java.lang.Throwable {
            /*
                r12 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r12.label
                r2 = 3
                r3 = 2
                java.lang.String r4 = "$endTime"
                java.lang.String r5 = "$startTime"
                r6 = 0
                java.lang.String r7 = "healthConnectClient"
                r8 = 1
                if (r1 == 0) goto L2d
                if (r1 == r8) goto L29
                if (r1 == r3) goto L25
                if (r1 != r2) goto L1d
                kotlin.ResultKt.throwOnFailure(r13)     // Catch: java.lang.Exception -> Lbc
                goto Lb2
            L1d:
                java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r13.<init>(r0)
                throw r13
            L25:
                kotlin.ResultKt.throwOnFailure(r13)     // Catch: java.lang.Exception -> Lbc
                goto L86
            L29:
                kotlin.ResultKt.throwOnFailure(r13)     // Catch: java.lang.Exception -> Lbc
                goto L5b
            L2d:
                kotlin.ResultKt.throwOnFailure(r13)
                cachet.plugins.health.HealthPlugin r13 = cachet.plugins.health.HealthPlugin.this     // Catch: java.lang.Exception -> Lbc
                androidx.health.connect.client.HealthConnectClient r13 = cachet.plugins.health.HealthPlugin.access$getHealthConnectClient$p(r13)     // Catch: java.lang.Exception -> Lbc
                if (r13 != 0) goto L3c
                kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r7)     // Catch: java.lang.Exception -> Lbc
                r13 = r6
            L3c:
                java.lang.Class<androidx.health.connect.client.records.TotalCaloriesBurnedRecord> r1 = androidx.health.connect.client.records.TotalCaloriesBurnedRecord.class
                kotlin.reflect.KClass r1 = kotlin.jvm.internal.Reflection.getOrCreateKotlinClass(r1)     // Catch: java.lang.Exception -> Lbc
                androidx.health.connect.client.time.TimeRangeFilter$Companion r9 = androidx.health.connect.client.time.TimeRangeFilter.INSTANCE     // Catch: java.lang.Exception -> Lbc
                java.time.Instant r10 = r12.$startTime     // Catch: java.lang.Exception -> Lbc
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r10, r5)     // Catch: java.lang.Exception -> Lbc
                java.time.Instant r11 = r12.$endTime     // Catch: java.lang.Exception -> Lbc
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r11, r4)     // Catch: java.lang.Exception -> Lbc
                androidx.health.connect.client.time.TimeRangeFilter r9 = r9.between(r10, r11)     // Catch: java.lang.Exception -> Lbc
                r12.label = r8     // Catch: java.lang.Exception -> Lbc
                java.lang.Object r13 = r13.deleteRecords(r1, r9, r12)     // Catch: java.lang.Exception -> Lbc
                if (r13 != r0) goto L5b
                return r0
            L5b:
                cachet.plugins.health.HealthPlugin r13 = cachet.plugins.health.HealthPlugin.this     // Catch: java.lang.Exception -> Lbc
                androidx.health.connect.client.HealthConnectClient r13 = cachet.plugins.health.HealthPlugin.access$getHealthConnectClient$p(r13)     // Catch: java.lang.Exception -> Lbc
                if (r13 != 0) goto L67
                kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r7)     // Catch: java.lang.Exception -> Lbc
                r13 = r6
            L67:
                java.lang.Class<androidx.health.connect.client.records.DistanceRecord> r1 = androidx.health.connect.client.records.DistanceRecord.class
                kotlin.reflect.KClass r1 = kotlin.jvm.internal.Reflection.getOrCreateKotlinClass(r1)     // Catch: java.lang.Exception -> Lbc
                androidx.health.connect.client.time.TimeRangeFilter$Companion r9 = androidx.health.connect.client.time.TimeRangeFilter.INSTANCE     // Catch: java.lang.Exception -> Lbc
                java.time.Instant r10 = r12.$startTime     // Catch: java.lang.Exception -> Lbc
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r10, r5)     // Catch: java.lang.Exception -> Lbc
                java.time.Instant r11 = r12.$endTime     // Catch: java.lang.Exception -> Lbc
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r11, r4)     // Catch: java.lang.Exception -> Lbc
                androidx.health.connect.client.time.TimeRangeFilter r9 = r9.between(r10, r11)     // Catch: java.lang.Exception -> Lbc
                r12.label = r3     // Catch: java.lang.Exception -> Lbc
                java.lang.Object r13 = r13.deleteRecords(r1, r9, r12)     // Catch: java.lang.Exception -> Lbc
                if (r13 != r0) goto L86
                return r0
            L86:
                cachet.plugins.health.HealthPlugin r13 = cachet.plugins.health.HealthPlugin.this     // Catch: java.lang.Exception -> Lbc
                androidx.health.connect.client.HealthConnectClient r13 = cachet.plugins.health.HealthPlugin.access$getHealthConnectClient$p(r13)     // Catch: java.lang.Exception -> Lbc
                if (r13 != 0) goto L92
                kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r7)     // Catch: java.lang.Exception -> Lbc
                goto L93
            L92:
                r6 = r13
            L93:
                java.lang.Class<androidx.health.connect.client.records.ExerciseSessionRecord> r13 = androidx.health.connect.client.records.ExerciseSessionRecord.class
                kotlin.reflect.KClass r13 = kotlin.jvm.internal.Reflection.getOrCreateKotlinClass(r13)     // Catch: java.lang.Exception -> Lbc
                androidx.health.connect.client.time.TimeRangeFilter$Companion r1 = androidx.health.connect.client.time.TimeRangeFilter.INSTANCE     // Catch: java.lang.Exception -> Lbc
                java.time.Instant r3 = r12.$startTime     // Catch: java.lang.Exception -> Lbc
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r5)     // Catch: java.lang.Exception -> Lbc
                java.time.Instant r5 = r12.$endTime     // Catch: java.lang.Exception -> Lbc
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r4)     // Catch: java.lang.Exception -> Lbc
                androidx.health.connect.client.time.TimeRangeFilter r1 = r1.between(r3, r5)     // Catch: java.lang.Exception -> Lbc
                r12.label = r2     // Catch: java.lang.Exception -> Lbc
                java.lang.Object r13 = r6.deleteRecords(r13, r1, r12)     // Catch: java.lang.Exception -> Lbc
                if (r13 != r0) goto Lb2
                return r0
            Lb2:
                io.flutter.plugin.common.MethodChannel$Result r13 = r12.$result     // Catch: java.lang.Exception -> Lbc
                java.lang.Boolean r0 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r8)     // Catch: java.lang.Exception -> Lbc
                r13.success(r0)     // Catch: java.lang.Exception -> Lbc
                goto Lc6
            Lbc:
                io.flutter.plugin.common.MethodChannel$Result r13 = r12.$result
                r0 = 0
                java.lang.Boolean r0 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r0)
                r13.success(r0)
            Lc6:
                kotlin.Unit r13 = kotlin.Unit.INSTANCE
                return r13
            */
            throw new UnsupportedOperationException("Method not decompiled: cachet.plugins.health.HealthPlugin.C03761.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C03761) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "cachet.plugins.health.HealthPlugin$deleteHCData$2", f = "HealthPlugin.kt", i = {}, l = {2207}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: cachet.plugins.health.HealthPlugin$deleteHCData$2, reason: invalid class name */
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ KClass<? extends Record> $classType;
        final /* synthetic */ Instant $endTime;
        final /* synthetic */ MethodChannel.Result $result;
        final /* synthetic */ Instant $startTime;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(KClass<? extends Record> kClass, Instant instant, Instant instant2, MethodChannel.Result result, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$classType = kClass;
            this.$startTime = instant;
            this.$endTime = instant2;
            this.$result = result;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return HealthPlugin.this.new AnonymousClass2(this.$classType, this.$startTime, this.$endTime, this.$result, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    HealthConnectClient healthConnectClient = HealthPlugin.this.healthConnectClient;
                    if (healthConnectClient == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("healthConnectClient");
                        healthConnectClient = null;
                    }
                    KClass<? extends Record> kClass = this.$classType;
                    TimeRangeFilter.Companion companion = TimeRangeFilter.INSTANCE;
                    Instant startTime = this.$startTime;
                    Intrinsics.checkNotNullExpressionValue(startTime, "$startTime");
                    Instant endTime = this.$endTime;
                    Intrinsics.checkNotNullExpressionValue(endTime, "$endTime");
                    TimeRangeFilter timeRangeFilterBetween = companion.between(startTime, endTime);
                    this.label = 1;
                    if (healthConnectClient.deleteRecords(kClass, timeRangeFilterBetween, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                this.$result.success(Boxing.boxBoolean(true));
            } catch (Exception unused) {
                this.$result.success(Boxing.boxBoolean(false));
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "cachet.plugins.health.HealthPlugin$getHCData$1", f = "HealthPlugin.kt", i = {0, 1, 1, 2, 2, 2}, l = {1668, 1674, 1688}, m = "invokeSuspend", n = {"classType", "rec", "record", "rec", "record", "totalDistance"}, s = {"L$3", "L$3", "L$4", "L$3", "L$4", "D$0"})
    @SourceDebugExtension({"SMAP\nHealthPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HealthPlugin.kt\ncachet/plugins/health/HealthPlugin$getHCData$1\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,2295:1\n483#2,7:2296\n1#3:2303\n*S KotlinDebug\n*F\n+ 1 HealthPlugin.kt\ncachet/plugins/health/HealthPlugin$getHCData$1\n*L\n1708#1:2296,7\n*E\n"})
    /* renamed from: cachet.plugins.health.HealthPlugin$getHCData$1, reason: invalid class name and case insensitive filesystem */
    static final class C03771 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $dataType;
        final /* synthetic */ Instant $endTime;
        final /* synthetic */ List<Map<String, Object>> $healthConnectData;
        final /* synthetic */ MethodChannel.Result $result;
        final /* synthetic */ Instant $startTime;
        double D$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03771(String str, Instant instant, Instant instant2, List<Map<String, Object>> list, MethodChannel.Result result, Continuation<? super C03771> continuation) {
            super(2, continuation);
            this.$dataType = str;
            this.$startTime = instant;
            this.$endTime = instant2;
            this.$healthConnectData = list;
            this.$result = result;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return HealthPlugin.this.new C03771(this.$dataType, this.$startTime, this.$endTime, this.$healthConnectData, this.$result, continuation);
        }

        /* JADX WARN: Removed duplicated region for block: B:28:0x00fb  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x0164 A[LOOP:2: B:36:0x015e->B:38:0x0164, LOOP_END] */
        /* JADX WARN: Removed duplicated region for block: B:41:0x017a  */
        /* JADX WARN: Removed duplicated region for block: B:44:0x01b9 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:48:0x01cc A[LOOP:0: B:46:0x01c6->B:48:0x01cc, LOOP_END] */
        /* JADX WARN: Removed duplicated region for block: B:52:0x01f4  */
        /* JADX WARN: Removed duplicated region for block: B:58:0x0227  */
        /* JADX WARN: Removed duplicated region for block: B:61:0x0235  */
        /* JADX WARN: Removed duplicated region for block: B:62:0x0237  */
        /* JADX WARN: Removed duplicated region for block: B:65:0x024d  */
        /* JADX WARN: Removed duplicated region for block: B:66:0x024f  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:43:0x01b7 -> B:8:0x0030). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r34) throws java.lang.Throwable {
            /*
                Method dump skipped, instructions count: 863
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: cachet.plugins.health.HealthPlugin.C03771.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C03771) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "cachet.plugins.health.HealthPlugin$getStepsHealthConnect$1", f = "HealthPlugin.kt", i = {}, l = {1366}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: cachet.plugins.health.HealthPlugin$getStepsHealthConnect$1, reason: invalid class name and case insensitive filesystem */
    static final class C03781 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ long $end;
        final /* synthetic */ MethodChannel.Result $result;
        final /* synthetic */ long $start;
        int label;
        final /* synthetic */ HealthPlugin this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03781(long j2, long j3, HealthPlugin healthPlugin, MethodChannel.Result result, Continuation<? super C03781> continuation) {
            super(2, continuation);
            this.$start = j2;
            this.$end = j3;
            this.this$0 = healthPlugin;
            this.$result = result;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C03781(this.$start, this.$end, this.this$0, this.$result, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    Instant instantOfEpochMilli = Instant.ofEpochMilli(this.$start);
                    Instant instantOfEpochMilli2 = Instant.ofEpochMilli(this.$end);
                    HealthConnectClient healthConnectClient = this.this$0.healthConnectClient;
                    if (healthConnectClient == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("healthConnectClient");
                        healthConnectClient = null;
                    }
                    Set of = SetsKt.setOf(StepsRecord.COUNT_TOTAL);
                    TimeRangeFilter.Companion companion = TimeRangeFilter.INSTANCE;
                    Intrinsics.checkNotNull(instantOfEpochMilli);
                    Intrinsics.checkNotNull(instantOfEpochMilli2);
                    AggregateRequest aggregateRequest = new AggregateRequest(of, companion.between(instantOfEpochMilli, instantOfEpochMilli2), null, 4, null);
                    this.label = 1;
                    obj = healthConnectClient.aggregate(aggregateRequest, this);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                Long l2 = (Long) ((AggregationResult) obj).get(StepsRecord.COUNT_TOTAL);
                long jLongValue = l2 != null ? l2.longValue() : 0L;
                Log.i("FLUTTER_HEALTH::SUCCESS", "returning " + jLongValue + " steps");
                this.$result.success(Boxing.boxLong(jLongValue));
            } catch (Exception unused) {
                Log.i("FLUTTER_HEALTH::ERROR", "unable to return steps");
                this.$result.success(null);
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C03781) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "cachet.plugins.health.HealthPlugin$hasPermissionsHC$1", f = "HealthPlugin.kt", i = {}, l = {1579}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: cachet.plugins.health.HealthPlugin$hasPermissionsHC$1, reason: invalid class name and case insensitive filesystem */
    static final class C03791 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Ref.ObjectRef<List<String>> $permList;
        final /* synthetic */ MethodChannel.Result $result;
        Object L$0;
        int label;
        final /* synthetic */ HealthPlugin this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03791(MethodChannel.Result result, HealthPlugin healthPlugin, Ref.ObjectRef<List<String>> objectRef, Continuation<? super C03791> continuation) {
            super(2, continuation);
            this.$result = result;
            this.this$0 = healthPlugin;
            this.$permList = objectRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C03791(this.$result, this.this$0, this.$permList, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            MethodChannel.Result result;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    MethodChannel.Result result2 = this.$result;
                    HealthConnectClient healthConnectClient = this.this$0.healthConnectClient;
                    if (healthConnectClient == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("healthConnectClient");
                        healthConnectClient = null;
                    }
                    PermissionController permissionController = healthConnectClient.getPermissionController();
                    this.L$0 = result2;
                    this.label = 1;
                    Object grantedPermissions = permissionController.getGrantedPermissions(this);
                    if (grantedPermissions == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    result = result2;
                    obj = grantedPermissions;
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    result = (MethodChannel.Result) this.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                result.success(Boxing.boxBoolean(((Set) obj).containsAll(this.$permList.element)));
            } catch (Exception e2) {
                e2.printStackTrace();
                this.this$0.setHealthConnectAvailable(false);
                Log.e("FLUTTER_HEALTH::ERROR", "getGrantedPermissions Exception");
                this.$result.error("", e2.getMessage(), e2.getMessage());
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C03791) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "cachet.plugins.health.HealthPlugin$writeBloodPressureHC$1", f = "HealthPlugin.kt", i = {}, l = {2152}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: cachet.plugins.health.HealthPlugin$writeBloodPressureHC$1, reason: invalid class name and case insensitive filesystem */
    static final class C03831 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ double $diastolic;
        final /* synthetic */ MethodChannel.Result $result;
        final /* synthetic */ Instant $startTime;
        final /* synthetic */ double $systolic;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03831(double d2, double d3, Instant instant, MethodChannel.Result result, Continuation<? super C03831> continuation) {
            super(2, continuation);
            this.$systolic = d2;
            this.$diastolic = d3;
            this.$startTime = instant;
            this.$result = result;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return HealthPlugin.this.new C03831(this.$systolic, this.$diastolic, this.$startTime, this.$result, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    HealthConnectClient healthConnectClient = HealthPlugin.this.healthConnectClient;
                    if (healthConnectClient == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("healthConnectClient");
                        healthConnectClient = null;
                    }
                    Pressure.Companion companion = Pressure.INSTANCE;
                    Pressure pressureMillimetersOfMercury = companion.millimetersOfMercury(this.$systolic);
                    Pressure pressureMillimetersOfMercury2 = companion.millimetersOfMercury(this.$diastolic);
                    Instant startTime = this.$startTime;
                    Intrinsics.checkNotNullExpressionValue(startTime, "$startTime");
                    List<? extends Record> listListOf = CollectionsKt.listOf(new BloodPressureRecord(startTime, null, pressureMillimetersOfMercury, pressureMillimetersOfMercury2, 0, 0, null, 112, null));
                    this.label = 1;
                    if (healthConnectClient.insertRecords(listListOf, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                this.$result.success(Boxing.boxBoolean(true));
                Log.i("FLUTTER_HEALTH::SUCCESS", "[Health Connect] Blood pressure was successfully added!");
            } catch (Exception e2) {
                Log.w("FLUTTER_HEALTH::ERROR", "[Health Connect] There was an error adding the blood pressure");
                String message = e2.getMessage();
                if (message == null) {
                    message = "unknown error";
                }
                Log.w("FLUTTER_HEALTH::ERROR", message);
                Log.w("FLUTTER_HEALTH::ERROR", e2.getStackTrace().toString());
                this.$result.success(Boxing.boxBoolean(false));
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C03831) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "cachet.plugins.health.HealthPlugin$writeHCData$1", f = "HealthPlugin.kt", i = {}, l = {2076}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: cachet.plugins.health.HealthPlugin$writeHCData$1, reason: invalid class name and case insensitive filesystem */
    static final class C03851 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Record $record;
        final /* synthetic */ MethodChannel.Result $result;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03851(Record record, MethodChannel.Result result, Continuation<? super C03851> continuation) {
            super(2, continuation);
            this.$record = record;
            this.$result = result;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return HealthPlugin.this.new C03851(this.$record, this.$result, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    HealthConnectClient healthConnectClient = HealthPlugin.this.healthConnectClient;
                    if (healthConnectClient == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("healthConnectClient");
                        healthConnectClient = null;
                    }
                    List<? extends Record> listListOf = CollectionsKt.listOf(this.$record);
                    this.label = 1;
                    if (healthConnectClient.insertRecords(listListOf, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                this.$result.success(Boxing.boxBoolean(true));
            } catch (Exception unused) {
                this.$result.success(Boxing.boxBoolean(false));
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C03851) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "cachet.plugins.health.HealthPlugin$writeWorkoutHCData$1", f = "HealthPlugin.kt", i = {}, l = {2127}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: cachet.plugins.health.HealthPlugin$writeWorkoutHCData$1, reason: invalid class name and case insensitive filesystem */
    static final class C03871 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Instant $endTime;
        final /* synthetic */ MethodChannel.Result $result;
        final /* synthetic */ Instant $startTime;
        final /* synthetic */ Integer $totalDistance;
        final /* synthetic */ Integer $totalEnergyBurned;
        final /* synthetic */ String $type;
        final /* synthetic */ int $workoutType;
        int label;
        final /* synthetic */ HealthPlugin this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03871(Instant instant, Instant instant2, int i2, String str, Integer num, Integer num2, HealthPlugin healthPlugin, MethodChannel.Result result, Continuation<? super C03871> continuation) {
            super(2, continuation);
            this.$startTime = instant;
            this.$endTime = instant2;
            this.$workoutType = i2;
            this.$type = str;
            this.$totalDistance = num;
            this.$totalEnergyBurned = num2;
            this.this$0 = healthPlugin;
            this.$result = result;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C03871(this.$startTime, this.$endTime, this.$workoutType, this.$type, this.$totalDistance, this.$totalEnergyBurned, this.this$0, this.$result, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            boolean z2;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    ArrayList arrayList = new ArrayList();
                    Instant startTime = this.$startTime;
                    Intrinsics.checkNotNullExpressionValue(startTime, "$startTime");
                    Instant endTime = this.$endTime;
                    Intrinsics.checkNotNullExpressionValue(endTime, "$endTime");
                    arrayList.add(new ExerciseSessionRecord(startTime, (ZoneOffset) null, endTime, (ZoneOffset) null, this.$workoutType, this.$type, (String) null, (androidx.health.connect.client.records.metadata.Metadata) null, (List) null, (List) null, (ExerciseRoute) null, 1984, (DefaultConstructorMarker) null));
                    if (this.$totalDistance != null) {
                        Instant startTime2 = this.$startTime;
                        Intrinsics.checkNotNullExpressionValue(startTime2, "$startTime");
                        Instant endTime2 = this.$endTime;
                        Intrinsics.checkNotNullExpressionValue(endTime2, "$endTime");
                        arrayList.add(new DistanceRecord(startTime2, null, endTime2, null, Length.INSTANCE.meters(this.$totalDistance.intValue()), null, 32, null));
                    }
                    if (this.$totalEnergyBurned != null) {
                        Instant startTime3 = this.$startTime;
                        Intrinsics.checkNotNullExpressionValue(startTime3, "$startTime");
                        Instant endTime3 = this.$endTime;
                        Intrinsics.checkNotNullExpressionValue(endTime3, "$endTime");
                        arrayList.add(new TotalCaloriesBurnedRecord(startTime3, null, endTime3, null, Energy.INSTANCE.kilocalories(this.$totalEnergyBurned.intValue()), null, 32, null));
                    }
                    HealthConnectClient healthConnectClient = this.this$0.healthConnectClient;
                    if (healthConnectClient == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("healthConnectClient");
                        healthConnectClient = null;
                    }
                    z2 = true;
                    this.label = 1;
                    if (healthConnectClient.insertRecords(arrayList, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    z2 = true;
                }
                this.$result.success(Boxing.boxBoolean(z2));
                Log.i("FLUTTER_HEALTH::SUCCESS", "[Health Connect] Workout was successfully added!");
            } catch (Exception e2) {
                Log.w("FLUTTER_HEALTH::ERROR", "[Health Connect] There was an error adding the workout");
                String message = e2.getMessage();
                if (message == null) {
                    message = "unknown error";
                }
                Log.w("FLUTTER_HEALTH::ERROR", message);
                Log.w("FLUTTER_HEALTH::ERROR", e2.getStackTrace().toString());
                this.$result.success(Boxing.boxBoolean(false));
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C03871) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    public HealthPlugin() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    private final FitnessOptions callToHealthTypes(MethodCall call) {
        ArrayList<String> arrayList;
        FitnessOptions.Builder builder = FitnessOptions.builder();
        Intrinsics.checkNotNullExpressionValue(builder, "builder(...)");
        Object obj = call.arguments;
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type java.util.HashMap<*, *>");
        HashMap map = (HashMap) obj;
        Object obj2 = map.get("types");
        ArrayList arrayList2 = null;
        ArrayList arrayList3 = obj2 instanceof ArrayList ? (ArrayList) obj2 : null;
        if (arrayList3 != null) {
            arrayList = new ArrayList();
            for (Object obj3 : arrayList3) {
                if (obj3 instanceof String) {
                    arrayList.add(obj3);
                }
            }
        } else {
            arrayList = null;
        }
        Object obj4 = map.get(NativeProtocol.RESULT_ARGS_PERMISSIONS);
        ArrayList arrayList4 = obj4 instanceof ArrayList ? (ArrayList) obj4 : null;
        if (arrayList4 != null) {
            arrayList2 = new ArrayList();
            for (Object obj5 : arrayList4) {
                if (obj5 instanceof Integer) {
                    arrayList2.add(obj5);
                }
            }
        }
        Intrinsics.checkNotNull(arrayList);
        arrayList.size();
        Intrinsics.checkNotNull(arrayList2);
        arrayList2.size();
        int i2 = 0;
        for (String str : arrayList) {
            int i3 = i2 + 1;
            int iIntValue = ((Number) arrayList2.get(i2)).intValue();
            DataType dataTypeKeyToHealthDataType = keyToHealthDataType(str);
            if (iIntValue == 0) {
                builder.addDataType(dataTypeKeyToHealthDataType, 0);
            } else if (iIntValue == 1) {
                builder.addDataType(dataTypeKeyToHealthDataType, 1);
            } else {
                if (iIntValue != 2) {
                    throw new IllegalArgumentException("Unknown access type " + iIntValue);
                }
                builder.addDataType(dataTypeKeyToHealthDataType, 0);
                builder.addDataType(dataTypeKeyToHealthDataType, 1);
            }
            if (Intrinsics.areEqual(str, this.SLEEP_ASLEEP) || Intrinsics.areEqual(str, this.SLEEP_AWAKE) || Intrinsics.areEqual(str, this.SLEEP_IN_BED)) {
                builder.accessSleepSessions(0);
                if (iIntValue == 0) {
                    builder.accessSleepSessions(0);
                } else if (iIntValue == 1) {
                    builder.accessSleepSessions(1);
                } else {
                    if (iIntValue != 2) {
                        throw new IllegalArgumentException("Unknown access type " + iIntValue);
                    }
                    builder.accessSleepSessions(0);
                    builder.accessSleepSessions(1);
                }
            }
            if (Intrinsics.areEqual(str, this.WORKOUT)) {
                if (iIntValue == 0) {
                    builder.accessActivitySessions(0);
                } else if (iIntValue == 1) {
                    builder.accessActivitySessions(1);
                } else {
                    if (iIntValue != 2) {
                        throw new IllegalArgumentException("Unknown access type " + iIntValue);
                    }
                    builder.accessActivitySessions(0);
                    builder.accessActivitySessions(1);
                }
            }
            i2 = i3;
        }
        FitnessOptions fitnessOptionsBuild = builder.build();
        Intrinsics.checkNotNullExpressionValue(fitnessOptionsBuild, "build(...)");
        return fitnessOptionsBuild;
    }

    private final OnSuccessListener<DataReadResponse> dataHandler(final DataType dataType, final Field field, final MethodChannel.Result result) {
        return new OnSuccessListener() { // from class: cachet.plugins.health.f
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                HealthPlugin.dataHandler$lambda$10(dataType, this, field, result, (DataReadResponse) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void dataHandler$lambda$10(DataType dataType, HealthPlugin this$0, Field field, MethodChannel.Result result, DataReadResponse response) {
        Device device;
        Intrinsics.checkNotNullParameter(dataType, "$dataType");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(field, "$field");
        Intrinsics.checkNotNullParameter(result, "$result");
        Intrinsics.checkNotNullParameter(response, "response");
        DataSet dataSet = response.getDataSet(dataType);
        Intrinsics.checkNotNullExpressionValue(dataSet, "getDataSet(...)");
        List<DataPoint> dataPoints = dataSet.getDataPoints();
        Intrinsics.checkNotNullExpressionValue(dataPoints, "getDataPoints(...)");
        List<DataPoint> list = dataPoints;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        int i2 = 0;
        for (Object obj : list) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            DataPoint dataPoint = (DataPoint) obj;
            Intrinsics.checkNotNull(dataPoint);
            Pair pair = TuplesKt.to("value", this$0.getHealthDataValue(dataPoint, field));
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            Pair pair2 = TuplesKt.to("date_from", Long.valueOf(dataPoint.getStartTime(timeUnit)));
            Pair pair3 = TuplesKt.to("date_to", Long.valueOf(dataPoint.getEndTime(timeUnit)));
            String appPackageName = dataPoint.getOriginalDataSource().getAppPackageName();
            if (appPackageName == null && ((device = dataPoint.getOriginalDataSource().getDevice()) == null || (appPackageName = device.getModel()) == null)) {
                appPackageName = "";
            }
            arrayList.add(MapsKt.hashMapOf(pair, pair2, pair3, TuplesKt.to("source_name", appPackageName), TuplesKt.to("source_id", dataPoint.getOriginalDataSource().getStreamIdentifier())));
            i2 = i3;
        }
        Context context = this$0.context;
        Intrinsics.checkNotNull(context);
        new Handler(context.getMainLooper());
        result.success(arrayList);
    }

    private final void delete(MethodCall call, final MethodChannel.Result result) {
        if (this.useHealthConnectIfAvailable && this.healthConnectAvailable) {
            deleteHCData(call, result);
            return;
        }
        if (this.context == null) {
            result.success(Boolean.FALSE);
            return;
        }
        Object objArgument = call.argument("dataTypeKey");
        Intrinsics.checkNotNull(objArgument);
        String str = (String) objArgument;
        Object objArgument2 = call.argument("startTime");
        Intrinsics.checkNotNull(objArgument2);
        long jLongValue = ((Number) objArgument2).longValue();
        Object objArgument3 = call.argument(AUserTrack.UTKEY_END_TIME);
        Intrinsics.checkNotNull(objArgument3);
        long jLongValue2 = ((Number) objArgument3).longValue();
        DataType dataTypeKeyToHealthDataType = keyToHealthDataType(str);
        getField(str);
        FitnessOptions.Builder builder = FitnessOptions.builder();
        Intrinsics.checkNotNullExpressionValue(builder, "builder(...)");
        builder.addDataType(dataTypeKeyToHealthDataType, 1);
        DataDeleteRequest dataDeleteRequestBuild = new DataDeleteRequest.Builder().setTimeInterval(jLongValue, jLongValue2, TimeUnit.MILLISECONDS).addDataType(dataTypeKeyToHealthDataType).deleteAllSessions().build();
        Intrinsics.checkNotNullExpressionValue(dataDeleteRequestBuild, "build(...)");
        FitnessOptions fitnessOptionsBuild = builder.build();
        Intrinsics.checkNotNullExpressionValue(fitnessOptionsBuild, "build(...)");
        try {
            Context context = this.context;
            Intrinsics.checkNotNull(context);
            GoogleSignInAccount accountForExtension = GoogleSignIn.getAccountForExtension(context.getApplicationContext(), fitnessOptionsBuild);
            Intrinsics.checkNotNullExpressionValue(accountForExtension, "getAccountForExtension(...)");
            Context context2 = this.context;
            Intrinsics.checkNotNull(context2);
            Task<Void> taskDeleteData = Fitness.getHistoryClient(context2.getApplicationContext(), accountForExtension).deleteData(dataDeleteRequestBuild);
            final Function1<Void, Unit> function1 = new Function1<Void, Unit>() { // from class: cachet.plugins.health.HealthPlugin.delete.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Void r1) {
                    invoke2(r1);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Void r2) {
                    Log.i("FLUTTER_HEALTH::SUCCESS", "Dataset deleted successfully!");
                    result.success(Boolean.TRUE);
                }
            };
            taskDeleteData.addOnSuccessListener(new OnSuccessListener() { // from class: cachet.plugins.health.l
                @Override // com.google.android.gms.tasks.OnSuccessListener
                public final void onSuccess(Object obj) {
                    HealthPlugin.delete$lambda$3(function1, obj);
                }
            }).addOnFailureListener(errHandler(result, "There was an error deleting the dataset"));
        } catch (Exception unused) {
            result.success(Boolean.FALSE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void delete$lambda$3(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final OnFailureListener errHandler(final MethodChannel.Result result, final String addMessage) {
        return new OnFailureListener() { // from class: cachet.plugins.health.o
            @Override // com.google.android.gms.tasks.OnFailureListener
            public final void onFailure(Exception exc) {
                HealthPlugin.errHandler$lambda$12(this.f8153a, addMessage, result, exc);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void errHandler$lambda$12(HealthPlugin this$0, String addMessage, MethodChannel.Result result, Exception exception) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(addMessage, "$addMessage");
        Intrinsics.checkNotNullParameter(result, "$result");
        Intrinsics.checkNotNullParameter(exception, "exception");
        Context context = this$0.context;
        Intrinsics.checkNotNull(context);
        new Handler(context.getMainLooper());
        result.success(null);
        Log.w("FLUTTER_HEALTH::ERROR", addMessage);
        String message = exception.getMessage();
        if (message == null) {
            message = "unknown error";
        }
        Log.w("FLUTTER_HEALTH::ERROR", message);
        Log.w("FLUTTER_HEALTH::ERROR", exception.getStackTrace().toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void error$lambda$2(HealthPlugin this$0, String errorCode, String str, Object obj) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(errorCode, "$errorCode");
        MethodChannel.Result result = this$0.mResult;
        if (result != null) {
            result.error(errorCode, str, obj);
        }
    }

    private final String getActivityType(String type) {
        String str = this.workoutTypeMap.get(type);
        return str == null ? "unknown" : str;
    }

    private final void getData(MethodCall call, MethodChannel.Result result) {
        if (this.useHealthConnectIfAvailable && this.healthConnectAvailable) {
            getHCData(call, result);
            return;
        }
        if (this.context == null) {
            result.success(null);
            return;
        }
        Object objArgument = call.argument("dataTypeKey");
        Intrinsics.checkNotNull(objArgument);
        String str = (String) objArgument;
        Object objArgument2 = call.argument("startTime");
        Intrinsics.checkNotNull(objArgument2);
        long jLongValue = ((Number) objArgument2).longValue();
        Object objArgument3 = call.argument(AUserTrack.UTKEY_END_TIME);
        Intrinsics.checkNotNull(objArgument3);
        long jLongValue2 = ((Number) objArgument3).longValue();
        DataType dataTypeKeyToHealthDataType = keyToHealthDataType(str);
        Field field = getField(str);
        FitnessOptions.Builder builder = FitnessOptions.builder();
        Intrinsics.checkNotNullExpressionValue(builder, "builder(...)");
        builder.addDataType(dataTypeKeyToHealthDataType);
        DataType dataType = DataType.TYPE_SLEEP_SEGMENT;
        if (Intrinsics.areEqual(dataTypeKeyToHealthDataType, dataType)) {
            builder.accessSleepSessions(0);
        } else if (Intrinsics.areEqual(dataTypeKeyToHealthDataType, DataType.TYPE_ACTIVITY_SEGMENT)) {
            builder.accessActivitySessions(0).addDataType(DataType.TYPE_CALORIES_EXPENDED, 0).addDataType(DataType.TYPE_DISTANCE_DELTA, 0);
        }
        FitnessOptions fitnessOptionsBuild = builder.build();
        Intrinsics.checkNotNullExpressionValue(fitnessOptionsBuild, "build(...)");
        Context context = this.context;
        Intrinsics.checkNotNull(context);
        GoogleSignInAccount accountForExtension = GoogleSignIn.getAccountForExtension(context.getApplicationContext(), fitnessOptionsBuild);
        Intrinsics.checkNotNullExpressionValue(accountForExtension, "getAccountForExtension(...)");
        if (Intrinsics.areEqual(dataTypeKeyToHealthDataType, dataType)) {
            SessionReadRequest sessionReadRequestBuild = new SessionReadRequest.Builder().setTimeInterval(jLongValue, jLongValue2, TimeUnit.MILLISECONDS).enableServerQueries().readSessionsFromAllApps().includeSleepSessions().build();
            Intrinsics.checkNotNullExpressionValue(sessionReadRequestBuild, "build(...)");
            Context context2 = this.context;
            Intrinsics.checkNotNull(context2);
            Task<SessionReadResponse> session = Fitness.getSessionsClient(context2.getApplicationContext(), accountForExtension).readSession(sessionReadRequestBuild);
            ExecutorService executorService = this.threadPoolExecutor;
            Intrinsics.checkNotNull(executorService);
            session.addOnSuccessListener(executorService, sleepDataHandler(str, result)).addOnFailureListener(errHandler(result, "There was an error getting the sleeping data!"));
            return;
        }
        if (!Intrinsics.areEqual(dataTypeKeyToHealthDataType, DataType.TYPE_ACTIVITY_SEGMENT)) {
            Context context3 = this.context;
            Intrinsics.checkNotNull(context3);
            Task<DataReadResponse> data = Fitness.getHistoryClient(context3.getApplicationContext(), accountForExtension).readData(new DataReadRequest.Builder().read(dataTypeKeyToHealthDataType).setTimeRange(jLongValue, jLongValue2, TimeUnit.MILLISECONDS).build());
            ExecutorService executorService2 = this.threadPoolExecutor;
            Intrinsics.checkNotNull(executorService2);
            data.addOnSuccessListener(executorService2, dataHandler(dataTypeKeyToHealthDataType, field, result)).addOnFailureListener(errHandler(result, "There was an error getting the data!"));
            return;
        }
        SessionReadRequest.Builder builder2 = new SessionReadRequest.Builder().setTimeInterval(jLongValue, jLongValue2, TimeUnit.MILLISECONDS).enableServerQueries().readSessionsFromAllApps().includeActivitySessions().read(dataTypeKeyToHealthDataType).read(DataType.TYPE_CALORIES_EXPENDED);
        Intrinsics.checkNotNullExpressionValue(builder2, "read(...)");
        Context context4 = this.context;
        Intrinsics.checkNotNull(context4);
        if (ContextCompat.checkSelfPermission(context4.getApplicationContext(), "android.permission.ACCESS_FINE_LOCATION") == 0) {
            builder2.read(DataType.TYPE_DISTANCE_DELTA);
        }
        SessionReadRequest sessionReadRequestBuild2 = builder2.build();
        Intrinsics.checkNotNullExpressionValue(sessionReadRequestBuild2, "build(...)");
        Context context5 = this.context;
        Intrinsics.checkNotNull(context5);
        Task<SessionReadResponse> session2 = Fitness.getSessionsClient(context5.getApplicationContext(), accountForExtension).readSession(sessionReadRequestBuild2);
        ExecutorService executorService3 = this.threadPoolExecutor;
        Intrinsics.checkNotNull(executorService3);
        session2.addOnSuccessListener(executorService3, workoutDataHandler(str, result)).addOnFailureListener(errHandler(result, "There was an error getting the workout data!"));
    }

    private final Field getField(String type) {
        if (Intrinsics.areEqual(type, this.BODY_FAT_PERCENTAGE)) {
            Field FIELD_PERCENTAGE = Field.FIELD_PERCENTAGE;
            Intrinsics.checkNotNullExpressionValue(FIELD_PERCENTAGE, "FIELD_PERCENTAGE");
            return FIELD_PERCENTAGE;
        }
        if (Intrinsics.areEqual(type, this.HEIGHT)) {
            Field FIELD_HEIGHT = Field.FIELD_HEIGHT;
            Intrinsics.checkNotNullExpressionValue(FIELD_HEIGHT, "FIELD_HEIGHT");
            return FIELD_HEIGHT;
        }
        if (Intrinsics.areEqual(type, this.WEIGHT)) {
            Field FIELD_WEIGHT = Field.FIELD_WEIGHT;
            Intrinsics.checkNotNullExpressionValue(FIELD_WEIGHT, "FIELD_WEIGHT");
            return FIELD_WEIGHT;
        }
        if (Intrinsics.areEqual(type, this.STEPS)) {
            Field FIELD_STEPS = Field.FIELD_STEPS;
            Intrinsics.checkNotNullExpressionValue(FIELD_STEPS, "FIELD_STEPS");
            return FIELD_STEPS;
        }
        if (Intrinsics.areEqual(type, this.ACTIVE_ENERGY_BURNED)) {
            Field FIELD_CALORIES = Field.FIELD_CALORIES;
            Intrinsics.checkNotNullExpressionValue(FIELD_CALORIES, "FIELD_CALORIES");
            return FIELD_CALORIES;
        }
        if (Intrinsics.areEqual(type, this.HEART_RATE)) {
            Field FIELD_BPM = Field.FIELD_BPM;
            Intrinsics.checkNotNullExpressionValue(FIELD_BPM, "FIELD_BPM");
            return FIELD_BPM;
        }
        if (Intrinsics.areEqual(type, this.BODY_TEMPERATURE)) {
            Field FIELD_BODY_TEMPERATURE = HealthFields.FIELD_BODY_TEMPERATURE;
            Intrinsics.checkNotNullExpressionValue(FIELD_BODY_TEMPERATURE, "FIELD_BODY_TEMPERATURE");
            return FIELD_BODY_TEMPERATURE;
        }
        if (Intrinsics.areEqual(type, this.BLOOD_PRESSURE_SYSTOLIC)) {
            Field FIELD_BLOOD_PRESSURE_SYSTOLIC = HealthFields.FIELD_BLOOD_PRESSURE_SYSTOLIC;
            Intrinsics.checkNotNullExpressionValue(FIELD_BLOOD_PRESSURE_SYSTOLIC, "FIELD_BLOOD_PRESSURE_SYSTOLIC");
            return FIELD_BLOOD_PRESSURE_SYSTOLIC;
        }
        if (Intrinsics.areEqual(type, this.BLOOD_PRESSURE_DIASTOLIC)) {
            Field FIELD_BLOOD_PRESSURE_DIASTOLIC = HealthFields.FIELD_BLOOD_PRESSURE_DIASTOLIC;
            Intrinsics.checkNotNullExpressionValue(FIELD_BLOOD_PRESSURE_DIASTOLIC, "FIELD_BLOOD_PRESSURE_DIASTOLIC");
            return FIELD_BLOOD_PRESSURE_DIASTOLIC;
        }
        if (Intrinsics.areEqual(type, this.BLOOD_OXYGEN)) {
            Field FIELD_OXYGEN_SATURATION = HealthFields.FIELD_OXYGEN_SATURATION;
            Intrinsics.checkNotNullExpressionValue(FIELD_OXYGEN_SATURATION, "FIELD_OXYGEN_SATURATION");
            return FIELD_OXYGEN_SATURATION;
        }
        if (Intrinsics.areEqual(type, this.BLOOD_GLUCOSE)) {
            Field FIELD_BLOOD_GLUCOSE_LEVEL = HealthFields.FIELD_BLOOD_GLUCOSE_LEVEL;
            Intrinsics.checkNotNullExpressionValue(FIELD_BLOOD_GLUCOSE_LEVEL, "FIELD_BLOOD_GLUCOSE_LEVEL");
            return FIELD_BLOOD_GLUCOSE_LEVEL;
        }
        if (Intrinsics.areEqual(type, this.MOVE_MINUTES)) {
            Field FIELD_DURATION = Field.FIELD_DURATION;
            Intrinsics.checkNotNullExpressionValue(FIELD_DURATION, "FIELD_DURATION");
            return FIELD_DURATION;
        }
        if (Intrinsics.areEqual(type, this.DISTANCE_DELTA)) {
            Field FIELD_DISTANCE = Field.FIELD_DISTANCE;
            Intrinsics.checkNotNullExpressionValue(FIELD_DISTANCE, "FIELD_DISTANCE");
            return FIELD_DISTANCE;
        }
        if (Intrinsics.areEqual(type, this.WATER)) {
            Field FIELD_VOLUME = Field.FIELD_VOLUME;
            Intrinsics.checkNotNullExpressionValue(FIELD_VOLUME, "FIELD_VOLUME");
            return FIELD_VOLUME;
        }
        if (Intrinsics.areEqual(type, this.SLEEP_ASLEEP)) {
            Field FIELD_SLEEP_SEGMENT_TYPE = Field.FIELD_SLEEP_SEGMENT_TYPE;
            Intrinsics.checkNotNullExpressionValue(FIELD_SLEEP_SEGMENT_TYPE, "FIELD_SLEEP_SEGMENT_TYPE");
            return FIELD_SLEEP_SEGMENT_TYPE;
        }
        if (Intrinsics.areEqual(type, this.SLEEP_AWAKE)) {
            Field FIELD_SLEEP_SEGMENT_TYPE2 = Field.FIELD_SLEEP_SEGMENT_TYPE;
            Intrinsics.checkNotNullExpressionValue(FIELD_SLEEP_SEGMENT_TYPE2, "FIELD_SLEEP_SEGMENT_TYPE");
            return FIELD_SLEEP_SEGMENT_TYPE2;
        }
        if (Intrinsics.areEqual(type, this.SLEEP_IN_BED)) {
            Field FIELD_SLEEP_SEGMENT_TYPE3 = Field.FIELD_SLEEP_SEGMENT_TYPE;
            Intrinsics.checkNotNullExpressionValue(FIELD_SLEEP_SEGMENT_TYPE3, "FIELD_SLEEP_SEGMENT_TYPE");
            return FIELD_SLEEP_SEGMENT_TYPE3;
        }
        if (Intrinsics.areEqual(type, this.WORKOUT)) {
            Field FIELD_ACTIVITY = Field.FIELD_ACTIVITY;
            Intrinsics.checkNotNullExpressionValue(FIELD_ACTIVITY, "FIELD_ACTIVITY");
            return FIELD_ACTIVITY;
        }
        throw new IllegalArgumentException("Unsupported dataType: " + type);
    }

    private final void getHealthConnectSdkStatus(MethodCall call, MethodChannel.Result result) {
        checkAvailability();
        if (this.healthConnectAvailable) {
            HealthConnectClient.Companion companion = HealthConnectClient.INSTANCE;
            Context context = this.context;
            Intrinsics.checkNotNull(context);
            this.healthConnectClient = HealthConnectClient.Companion.getOrCreate$default(companion, context, null, 2, null);
        }
        result.success(Integer.valueOf(this.healthConnectStatus));
    }

    private final Object getHealthDataValue(DataPoint dataPoint, Field field) {
        Value value = dataPoint.getValue(field);
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        boolean zAreEqual = Intrinsics.areEqual(field, HealthFields.FIELD_BLOOD_GLUCOSE_LEVEL);
        int format = value.getFormat();
        if (format == 1) {
            return Integer.valueOf(value.asInt());
        }
        if (format == 2) {
            float fAsFloat = value.asFloat();
            return !zAreEqual ? Float.valueOf(fAsFloat) : Double.valueOf(fAsFloat * 18.0d);
        }
        if (format != 3) {
            return Integer.valueOf(Log.e("Unsupported format:", String.valueOf(value.getFormat())));
        }
        String strAsString = value.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }

    private final Job getStepsHealthConnect(long start, long end, MethodChannel.Result result) {
        CoroutineScope coroutineScope = this.scope;
        if (coroutineScope == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scope");
            coroutineScope = null;
        }
        return BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new C03781(start, end, this, result, null), 3, null);
    }

    private final OnSuccessListener<DataReadResponse> getStepsInRange(final long start, final long end, final DataType aggregatedDataType, final MethodChannel.Result result) {
        return new OnSuccessListener() { // from class: cachet.plugins.health.h
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                HealthPlugin.getStepsInRange$lambda$23(aggregatedDataType, start, end, this, result, (DataReadResponse) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getStepsInRange$lambda$23(DataType aggregatedDataType, long j2, long j3, HealthPlugin this$0, MethodChannel.Result result, DataReadResponse response) {
        List<DataPoint> dataPoints;
        Intrinsics.checkNotNullParameter(aggregatedDataType, "$aggregatedDataType");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(result, "$result");
        Intrinsics.checkNotNullParameter(response, "response");
        HashMap map = new HashMap();
        Iterator<Bucket> it = response.getBuckets().iterator();
        while (it.hasNext()) {
            List<DataSet> dataSets = it.next().getDataSets();
            Intrinsics.checkNotNullExpressionValue(dataSets, "getDataSets(...)");
            DataSet dataSet = (DataSet) CollectionsKt.firstOrNull((List) dataSets);
            DataPoint dataPoint = (dataSet == null || (dataPoints = dataSet.getDataPoints()) == null) ? null : (DataPoint) CollectionsKt.firstOrNull((List) dataPoints);
            if (dataPoint != null) {
                Value value = dataPoint.getValue(aggregatedDataType.getFields().get(0));
                Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
                TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                long startTime = dataPoint.getStartTime(timeUnit);
                Log.i("FLUTTER_HEALTH::SUCCESS", "returning " + value + " steps for " + new Date(startTime) + " - " + new Date(dataPoint.getEndTime(timeUnit)));
                map.put(Long.valueOf(startTime), Integer.valueOf(value.asInt()));
            } else {
                Log.i("FLUTTER_HEALTH::ERROR", "no steps for " + new Date(j2) + " - " + new Date(j3));
            }
        }
        map.size();
        Context context = this$0.context;
        Intrinsics.checkNotNull(context);
        new Handler(context.getMainLooper());
        Collection collectionValues = map.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        result.success(CollectionsKt.firstOrNull(collectionValues));
    }

    private final void getTotalStepsInInterval(MethodCall call, MethodChannel.Result result) {
        Object objArgument = call.argument("startTime");
        Intrinsics.checkNotNull(objArgument);
        long jLongValue = ((Number) objArgument).longValue();
        Object objArgument2 = call.argument(AUserTrack.UTKEY_END_TIME);
        Intrinsics.checkNotNull(objArgument2);
        long jLongValue2 = ((Number) objArgument2).longValue();
        if (this.useHealthConnectIfAvailable && this.healthConnectAvailable) {
            getStepsHealthConnect(jLongValue, jLongValue2, result);
            return;
        }
        Context context = this.context;
        if (context == null) {
            return;
        }
        DataType dataTypeKeyToHealthDataType = keyToHealthDataType(this.STEPS);
        DataType dataTypeKeyToHealthDataType2 = keyToHealthDataType(this.AGGREGATE_STEP_COUNT);
        FitnessOptions fitnessOptionsBuild = FitnessOptions.builder().addDataType(dataTypeKeyToHealthDataType).addDataType(dataTypeKeyToHealthDataType2).build();
        Intrinsics.checkNotNullExpressionValue(fitnessOptionsBuild, "build(...)");
        GoogleSignInAccount accountForExtension = GoogleSignIn.getAccountForExtension(context, fitnessOptionsBuild);
        Intrinsics.checkNotNullExpressionValue(accountForExtension, "getAccountForExtension(...)");
        DataSource dataSourceBuild = new DataSource.Builder().setAppPackageName("com.google.android.gms").setDataType(dataTypeKeyToHealthDataType).setType(1).setStreamName("estimated_steps").build();
        Intrinsics.checkNotNullExpressionValue(dataSourceBuild, "build(...)");
        DataReadRequest.Builder builderAggregate = new DataReadRequest.Builder().aggregate(dataSourceBuild);
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        DataReadRequest dataReadRequestBuild = builderAggregate.bucketByTime((int) (jLongValue2 - jLongValue), timeUnit).setTimeRange(jLongValue, jLongValue2, timeUnit).build();
        Intrinsics.checkNotNullExpressionValue(dataReadRequestBuild, "build(...)");
        Task<DataReadResponse> taskAddOnFailureListener = Fitness.getHistoryClient(context, accountForExtension).readData(dataReadRequestBuild).addOnFailureListener(errHandler(result, "There was an error getting the total steps in the interval!"));
        ExecutorService executorService = this.threadPoolExecutor;
        Intrinsics.checkNotNull(executorService);
        taskAddOnFailureListener.addOnSuccessListener(executorService, getStepsInRange(jLongValue, jLongValue2, dataTypeKeyToHealthDataType2, result));
    }

    private final void hasPermissions(MethodCall call, MethodChannel.Result result) {
        Log.w("FLUTTER_HEALTH::hasPermissions", "useHealthConnectIfAvailable : " + this.useHealthConnectIfAvailable + ", healthConnectAvailable: " + this.healthConnectAvailable);
        if (this.useHealthConnectIfAvailable && this.healthConnectAvailable) {
            hasPermissionsHC(call, result);
            return;
        }
        if (this.context == null) {
            result.success(Boolean.FALSE);
            return;
        }
        FitnessOptions fitnessOptionsCallToHealthTypes = callToHealthTypes(call);
        Context context = this.context;
        Intrinsics.checkNotNull(context);
        boolean zHasPermissions = GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(context), fitnessOptionsCallToHealthTypes);
        if (result != null) {
            result.success(Boolean.valueOf(zHasPermissions));
        }
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [T, java.util.ArrayList] */
    private final void hasPermissionsHC(MethodCall call, MethodChannel.Result result) {
        ArrayList<String> arrayList;
        ArrayList arrayList2;
        CoroutineScope coroutineScope;
        Object obj = call.arguments;
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type java.util.HashMap<*, *>");
        HashMap map = (HashMap) obj;
        Object obj2 = map.get("types");
        ArrayList arrayList3 = obj2 instanceof ArrayList ? (ArrayList) obj2 : null;
        if (arrayList3 != null) {
            arrayList = new ArrayList();
            for (Object obj3 : arrayList3) {
                if (obj3 instanceof String) {
                    arrayList.add(obj3);
                }
            }
        } else {
            arrayList = null;
        }
        Intrinsics.checkNotNull(arrayList);
        Object obj4 = map.get(NativeProtocol.RESULT_ARGS_PERMISSIONS);
        ArrayList arrayList4 = obj4 instanceof ArrayList ? (ArrayList) obj4 : null;
        if (arrayList4 != null) {
            arrayList2 = new ArrayList();
            for (Object obj5 : arrayList4) {
                if (obj5 instanceof Integer) {
                    arrayList2.add(obj5);
                }
            }
        } else {
            arrayList2 = null;
        }
        Intrinsics.checkNotNull(arrayList2);
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = new ArrayList();
        int i2 = 0;
        for (String str : arrayList) {
            int i3 = i2 + 1;
            Object obj6 = arrayList2.get(i2);
            Intrinsics.checkNotNull(obj6);
            int iIntValue = ((Number) obj6).intValue();
            KClass<? extends Record> kClass = this.MapToHCType.get(str);
            Intrinsics.checkNotNull(kClass);
            KClass<? extends Record> kClass2 = kClass;
            if (iIntValue == 0) {
                ((List) objectRef.element).add(HealthPermission.INSTANCE.getReadPermission(kClass2));
            } else if (iIntValue != 1) {
                List list = (List) objectRef.element;
                HealthPermission.Companion companion = HealthPermission.INSTANCE;
                list.addAll(CollectionsKt.listOf((Object[]) new String[]{companion.getReadPermission(kClass2), companion.getWritePermission(kClass2)}));
            } else {
                ((List) objectRef.element).add(HealthPermission.INSTANCE.getWritePermission(kClass2));
            }
            if (Intrinsics.areEqual(str, this.WORKOUT)) {
                if (iIntValue == 0) {
                    List list2 = (List) objectRef.element;
                    HealthPermission.Companion companion2 = HealthPermission.INSTANCE;
                    list2.addAll(CollectionsKt.listOf((Object[]) new String[]{companion2.getReadPermission(Reflection.getOrCreateKotlinClass(DistanceRecord.class)), companion2.getReadPermission(Reflection.getOrCreateKotlinClass(TotalCaloriesBurnedRecord.class))}));
                } else if (iIntValue != 1) {
                    List list3 = (List) objectRef.element;
                    HealthPermission.Companion companion3 = HealthPermission.INSTANCE;
                    list3.addAll(CollectionsKt.listOf((Object[]) new String[]{companion3.getReadPermission(Reflection.getOrCreateKotlinClass(DistanceRecord.class)), companion3.getReadPermission(Reflection.getOrCreateKotlinClass(TotalCaloriesBurnedRecord.class)), companion3.getWritePermission(Reflection.getOrCreateKotlinClass(DistanceRecord.class)), companion3.getWritePermission(Reflection.getOrCreateKotlinClass(TotalCaloriesBurnedRecord.class))}));
                } else {
                    List list4 = (List) objectRef.element;
                    HealthPermission.Companion companion4 = HealthPermission.INSTANCE;
                    list4.addAll(CollectionsKt.listOf((Object[]) new String[]{companion4.getWritePermission(Reflection.getOrCreateKotlinClass(DistanceRecord.class)), companion4.getWritePermission(Reflection.getOrCreateKotlinClass(TotalCaloriesBurnedRecord.class))}));
                }
            }
            i2 = i3;
        }
        CoroutineScope coroutineScope2 = this.scope;
        if (coroutineScope2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scope");
            coroutineScope = null;
        } else {
            coroutineScope = coroutineScope2;
        }
        BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new C03791(result, this, objectRef, null), 3, null);
    }

    private final void installHealthConnect(MethodCall call, MethodChannel.Result result) {
        Log.d("FLUTTER_HEALTH::DEBUG", "installHealthConnect context: " + this.activity + "uriString: https://play.google.com/store/apps/details?id=com.google.android.apps.healthdata");
        Activity activity = this.activity;
        Intrinsics.checkNotNull(activity);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setPackage("com.android.vending");
        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.healthdata"));
        intent.addFlags(268435456);
        intent.putExtra("overlay", true);
        Activity activity2 = this.activity;
        Intrinsics.checkNotNull(activity2);
        intent.putExtra("callerId", activity2.getPackageName());
        activity.startActivity(intent);
        result.success(null);
    }

    private final boolean isIntField(DataSource dataSource, Field unit) {
        DataPoint dataPointBuild = DataPoint.builder(dataSource).build();
        Intrinsics.checkNotNullExpressionValue(dataPointBuild, "build(...)");
        Value value = dataPointBuild.getValue(unit);
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return value.getFormat() == 1;
    }

    private final DataType keyToHealthDataType(String type) {
        if (Intrinsics.areEqual(type, this.BODY_FAT_PERCENTAGE)) {
            DataType TYPE_BODY_FAT_PERCENTAGE = DataType.TYPE_BODY_FAT_PERCENTAGE;
            Intrinsics.checkNotNullExpressionValue(TYPE_BODY_FAT_PERCENTAGE, "TYPE_BODY_FAT_PERCENTAGE");
            return TYPE_BODY_FAT_PERCENTAGE;
        }
        if (Intrinsics.areEqual(type, this.HEIGHT)) {
            DataType TYPE_HEIGHT = DataType.TYPE_HEIGHT;
            Intrinsics.checkNotNullExpressionValue(TYPE_HEIGHT, "TYPE_HEIGHT");
            return TYPE_HEIGHT;
        }
        if (Intrinsics.areEqual(type, this.WEIGHT)) {
            DataType TYPE_WEIGHT = DataType.TYPE_WEIGHT;
            Intrinsics.checkNotNullExpressionValue(TYPE_WEIGHT, "TYPE_WEIGHT");
            return TYPE_WEIGHT;
        }
        if (Intrinsics.areEqual(type, this.STEPS)) {
            DataType TYPE_STEP_COUNT_DELTA = DataType.TYPE_STEP_COUNT_DELTA;
            Intrinsics.checkNotNullExpressionValue(TYPE_STEP_COUNT_DELTA, "TYPE_STEP_COUNT_DELTA");
            return TYPE_STEP_COUNT_DELTA;
        }
        if (Intrinsics.areEqual(type, this.AGGREGATE_STEP_COUNT)) {
            DataType AGGREGATE_STEP_COUNT_DELTA = DataType.AGGREGATE_STEP_COUNT_DELTA;
            Intrinsics.checkNotNullExpressionValue(AGGREGATE_STEP_COUNT_DELTA, "AGGREGATE_STEP_COUNT_DELTA");
            return AGGREGATE_STEP_COUNT_DELTA;
        }
        if (Intrinsics.areEqual(type, this.ACTIVE_ENERGY_BURNED)) {
            DataType TYPE_CALORIES_EXPENDED = DataType.TYPE_CALORIES_EXPENDED;
            Intrinsics.checkNotNullExpressionValue(TYPE_CALORIES_EXPENDED, "TYPE_CALORIES_EXPENDED");
            return TYPE_CALORIES_EXPENDED;
        }
        if (Intrinsics.areEqual(type, this.HEART_RATE)) {
            DataType TYPE_HEART_RATE_BPM = DataType.TYPE_HEART_RATE_BPM;
            Intrinsics.checkNotNullExpressionValue(TYPE_HEART_RATE_BPM, "TYPE_HEART_RATE_BPM");
            return TYPE_HEART_RATE_BPM;
        }
        if (Intrinsics.areEqual(type, this.BODY_TEMPERATURE)) {
            DataType TYPE_BODY_TEMPERATURE = HealthDataTypes.TYPE_BODY_TEMPERATURE;
            Intrinsics.checkNotNullExpressionValue(TYPE_BODY_TEMPERATURE, "TYPE_BODY_TEMPERATURE");
            return TYPE_BODY_TEMPERATURE;
        }
        if (Intrinsics.areEqual(type, this.BLOOD_PRESSURE_SYSTOLIC)) {
            DataType TYPE_BLOOD_PRESSURE = HealthDataTypes.TYPE_BLOOD_PRESSURE;
            Intrinsics.checkNotNullExpressionValue(TYPE_BLOOD_PRESSURE, "TYPE_BLOOD_PRESSURE");
            return TYPE_BLOOD_PRESSURE;
        }
        if (Intrinsics.areEqual(type, this.BLOOD_PRESSURE_DIASTOLIC)) {
            DataType TYPE_BLOOD_PRESSURE2 = HealthDataTypes.TYPE_BLOOD_PRESSURE;
            Intrinsics.checkNotNullExpressionValue(TYPE_BLOOD_PRESSURE2, "TYPE_BLOOD_PRESSURE");
            return TYPE_BLOOD_PRESSURE2;
        }
        if (Intrinsics.areEqual(type, this.BLOOD_OXYGEN)) {
            DataType TYPE_OXYGEN_SATURATION = HealthDataTypes.TYPE_OXYGEN_SATURATION;
            Intrinsics.checkNotNullExpressionValue(TYPE_OXYGEN_SATURATION, "TYPE_OXYGEN_SATURATION");
            return TYPE_OXYGEN_SATURATION;
        }
        if (Intrinsics.areEqual(type, this.BLOOD_GLUCOSE)) {
            DataType TYPE_BLOOD_GLUCOSE = HealthDataTypes.TYPE_BLOOD_GLUCOSE;
            Intrinsics.checkNotNullExpressionValue(TYPE_BLOOD_GLUCOSE, "TYPE_BLOOD_GLUCOSE");
            return TYPE_BLOOD_GLUCOSE;
        }
        if (Intrinsics.areEqual(type, this.MOVE_MINUTES)) {
            DataType TYPE_MOVE_MINUTES = DataType.TYPE_MOVE_MINUTES;
            Intrinsics.checkNotNullExpressionValue(TYPE_MOVE_MINUTES, "TYPE_MOVE_MINUTES");
            return TYPE_MOVE_MINUTES;
        }
        if (Intrinsics.areEqual(type, this.DISTANCE_DELTA)) {
            DataType TYPE_DISTANCE_DELTA = DataType.TYPE_DISTANCE_DELTA;
            Intrinsics.checkNotNullExpressionValue(TYPE_DISTANCE_DELTA, "TYPE_DISTANCE_DELTA");
            return TYPE_DISTANCE_DELTA;
        }
        if (Intrinsics.areEqual(type, this.WATER)) {
            DataType TYPE_HYDRATION = DataType.TYPE_HYDRATION;
            Intrinsics.checkNotNullExpressionValue(TYPE_HYDRATION, "TYPE_HYDRATION");
            return TYPE_HYDRATION;
        }
        if (Intrinsics.areEqual(type, this.SLEEP_ASLEEP)) {
            DataType TYPE_SLEEP_SEGMENT = DataType.TYPE_SLEEP_SEGMENT;
            Intrinsics.checkNotNullExpressionValue(TYPE_SLEEP_SEGMENT, "TYPE_SLEEP_SEGMENT");
            return TYPE_SLEEP_SEGMENT;
        }
        if (Intrinsics.areEqual(type, this.SLEEP_AWAKE)) {
            DataType TYPE_SLEEP_SEGMENT2 = DataType.TYPE_SLEEP_SEGMENT;
            Intrinsics.checkNotNullExpressionValue(TYPE_SLEEP_SEGMENT2, "TYPE_SLEEP_SEGMENT");
            return TYPE_SLEEP_SEGMENT2;
        }
        if (Intrinsics.areEqual(type, this.SLEEP_IN_BED)) {
            DataType TYPE_SLEEP_SEGMENT3 = DataType.TYPE_SLEEP_SEGMENT;
            Intrinsics.checkNotNullExpressionValue(TYPE_SLEEP_SEGMENT3, "TYPE_SLEEP_SEGMENT");
            return TYPE_SLEEP_SEGMENT3;
        }
        if (Intrinsics.areEqual(type, this.WORKOUT)) {
            DataType TYPE_ACTIVITY_SEGMENT = DataType.TYPE_ACTIVITY_SEGMENT;
            Intrinsics.checkNotNullExpressionValue(TYPE_ACTIVITY_SEGMENT, "TYPE_ACTIVITY_SEGMENT");
            return TYPE_ACTIVITY_SEGMENT;
        }
        throw new IllegalArgumentException("Unsupported dataType: " + type);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void notImplemented$lambda$1(HealthPlugin this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        MethodChannel.Result result = this$0.mResult;
        if (result != null) {
            result.notImplemented();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onAttachedToActivity$lambda$24(HealthPlugin this$0, Set set) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNull(set);
        this$0.onHealthConnectPermissionCallback(set);
    }

    private final void onHealthConnectPermissionCallback(Set<String> permissionGranted) {
        if (permissionGranted.isEmpty()) {
            MethodChannel.Result result = this.mResult;
            if (result != null) {
                result.success(Boolean.FALSE);
            }
            Log.i("FLUTTER_HEALTH", "Access Denied (to Health Connect)!");
            return;
        }
        MethodChannel.Result result2 = this.mResult;
        if (result2 != null) {
            result2.success(Boolean.TRUE);
        }
        Log.i("FLUTTER_HEALTH", "Access Granted (to Health Connect)!");
    }

    @JvmStatic
    public static final void registerWith(@NotNull PluginRegistry.Registrar registrar) {
        INSTANCE.registerWith(registrar);
    }

    private final void requestAuthorization(MethodCall call, MethodChannel.Result result) {
        if (this.context == null) {
            result.success(Boolean.FALSE);
            return;
        }
        this.mResult = result;
        Log.i("Health", "requestAuthorization start , useHealthConnectIfAvailable: " + this.useHealthConnectIfAvailable + " healthConnectAvailable: " + this.healthConnectAvailable);
        if (this.useHealthConnectIfAvailable && this.healthConnectAvailable) {
            requestAuthorizationHC(call, result);
            return;
        }
        FitnessOptions fitnessOptionsCallToHealthTypes = callToHealthTypes(call);
        if (this.activity == null) {
            if (result != null) {
                result.success(Boolean.TRUE);
            }
        } else {
            Log.i("Health", "GoogleSignIn.requestPermissions");
            Activity activity = this.activity;
            Intrinsics.checkNotNull(activity);
            Context context = this.context;
            Intrinsics.checkNotNull(context);
            GoogleSignIn.requestPermissions(activity, 1111, GoogleSignIn.getLastSignedInAccount(context), fitnessOptionsCallToHealthTypes);
        }
    }

    private final void requestAuthorizationHC(MethodCall call, MethodChannel.Result result) {
        ArrayList<String> arrayList;
        Object obj = call.arguments;
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type java.util.HashMap<*, *>");
        HashMap map = (HashMap) obj;
        Object obj2 = map.get("types");
        ArrayList arrayList2 = null;
        ArrayList arrayList3 = obj2 instanceof ArrayList ? (ArrayList) obj2 : null;
        if (arrayList3 != null) {
            arrayList = new ArrayList();
            for (Object obj3 : arrayList3) {
                if (obj3 instanceof String) {
                    arrayList.add(obj3);
                }
            }
        } else {
            arrayList = null;
        }
        Intrinsics.checkNotNull(arrayList);
        Object obj4 = map.get(NativeProtocol.RESULT_ARGS_PERMISSIONS);
        ArrayList arrayList4 = obj4 instanceof ArrayList ? (ArrayList) obj4 : null;
        if (arrayList4 != null) {
            arrayList2 = new ArrayList();
            for (Object obj5 : arrayList4) {
                if (obj5 instanceof Integer) {
                    arrayList2.add(obj5);
                }
            }
        }
        Intrinsics.checkNotNull(arrayList2);
        ArrayList arrayList5 = new ArrayList();
        int i2 = 0;
        for (String str : arrayList) {
            int i3 = i2 + 1;
            Object obj6 = arrayList2.get(i2);
            Intrinsics.checkNotNull(obj6);
            int iIntValue = ((Number) obj6).intValue();
            KClass<? extends Record> kClass = this.MapToHCType.get(str);
            Intrinsics.checkNotNull(kClass);
            KClass<? extends Record> kClass2 = kClass;
            if (iIntValue == 0) {
                arrayList5.add(HealthPermission.INSTANCE.getReadPermission(kClass2));
            } else if (iIntValue != 1) {
                HealthPermission.Companion companion = HealthPermission.INSTANCE;
                arrayList5.addAll(CollectionsKt.listOf((Object[]) new String[]{companion.getReadPermission(kClass2), companion.getWritePermission(kClass2)}));
            } else {
                arrayList5.add(HealthPermission.INSTANCE.getWritePermission(kClass2));
            }
            if (Intrinsics.areEqual(str, this.WORKOUT)) {
                if (iIntValue == 0) {
                    HealthPermission.Companion companion2 = HealthPermission.INSTANCE;
                    arrayList5.addAll(CollectionsKt.listOf((Object[]) new String[]{companion2.getReadPermission(Reflection.getOrCreateKotlinClass(DistanceRecord.class)), companion2.getReadPermission(Reflection.getOrCreateKotlinClass(TotalCaloriesBurnedRecord.class))}));
                } else if (iIntValue != 1) {
                    HealthPermission.Companion companion3 = HealthPermission.INSTANCE;
                    arrayList5.addAll(CollectionsKt.listOf((Object[]) new String[]{companion3.getReadPermission(Reflection.getOrCreateKotlinClass(DistanceRecord.class)), companion3.getReadPermission(Reflection.getOrCreateKotlinClass(TotalCaloriesBurnedRecord.class)), companion3.getWritePermission(Reflection.getOrCreateKotlinClass(DistanceRecord.class)), companion3.getWritePermission(Reflection.getOrCreateKotlinClass(TotalCaloriesBurnedRecord.class))}));
                } else {
                    HealthPermission.Companion companion4 = HealthPermission.INSTANCE;
                    arrayList5.addAll(CollectionsKt.listOf((Object[]) new String[]{companion4.getWritePermission(Reflection.getOrCreateKotlinClass(DistanceRecord.class)), companion4.getWritePermission(Reflection.getOrCreateKotlinClass(TotalCaloriesBurnedRecord.class))}));
                }
            }
            i2 = i3;
        }
        Log.e("FLUTTER_HEALTH", "requestAuthorizationHC permList: " + arrayList5);
        ActivityResultLauncher<Set<String>> activityResultLauncher = this.healthConnectRequestPermissionsLauncher;
        if (activityResultLauncher == null) {
            result.success(Boolean.FALSE);
            Log.i("FLUTTER_HEALTH", "Permission launcher not found");
        } else {
            Intrinsics.checkNotNull(activityResultLauncher);
            activityResultLauncher.launch(CollectionsKt.toSet(arrayList5));
        }
    }

    private final void revokePermissions(MethodCall call, final MethodChannel.Result result) {
        String str;
        String str2;
        Log.i("Health", "revokePermissions start");
        if (this.useHealthConnectIfAvailable && this.healthConnectAvailable) {
            if (Build.VERSION.SDK_INT > 33) {
                str = "android.health.connect.action.HEALTH_HOME_SETTINGS";
                str2 = "com.android.healthconnect.controller";
            } else {
                str = "androidx.health.ACTION_HEALTH_CONNECT_SETTINGS";
                str2 = "com.google.android.apps.healthdata";
            }
            Intent intent = new Intent(str);
            intent.setPackage(str2);
            intent.addFlags(268435456);
            Context context = this.context;
            Intrinsics.checkNotNull(context);
            context.startActivity(intent);
            result.notImplemented();
            return;
        }
        if (this.context == null) {
            result.success(Boolean.FALSE);
            return;
        }
        Log.i("Health", "Fitness.getConfigClient");
        Activity activity = this.activity;
        Intrinsics.checkNotNull(activity);
        Context context2 = this.context;
        Intrinsics.checkNotNull(context2);
        GoogleSignInAccount lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(context2);
        Intrinsics.checkNotNull(lastSignedInAccount);
        Task<Void> taskDisableFit = Fitness.getConfigClient(activity, lastSignedInAccount).disableFit();
        final Function1<Void, Unit> function1 = new Function1<Void, Unit>() { // from class: cachet.plugins.health.HealthPlugin.revokePermissions.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Void r1) {
                invoke2(r1);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Void r2) {
                Log.i("Health", "Disabled Google Fit");
                result.success(Boolean.TRUE);
            }
        };
        taskDisableFit.addOnSuccessListener(new OnSuccessListener() { // from class: cachet.plugins.health.p
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                HealthPlugin.revokePermissions$lambda$19(function1, obj);
            }
        }).addOnFailureListener(new OnFailureListener() { // from class: cachet.plugins.health.q
            @Override // com.google.android.gms.tasks.OnFailureListener
            public final void onFailure(Exception exc) {
                HealthPlugin.revokePermissions$lambda$20(result, exc);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void revokePermissions$lambda$19(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void revokePermissions$lambda$20(MethodChannel.Result result, Exception e2) {
        Intrinsics.checkNotNullParameter(result, "$result");
        Intrinsics.checkNotNullParameter(e2, "e");
        Log.w("Health", "There was an error disabling Google Fit", e2);
        result.success(Boolean.FALSE);
    }

    private final OnSuccessListener<SessionReadResponse> sleepDataHandler(final String type, final MethodChannel.Result result) {
        return new OnSuccessListener() { // from class: cachet.plugins.health.g
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                HealthPlugin.sleepDataHandler$lambda$14(type, this, result, (SessionReadResponse) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sleepDataHandler$lambda$14(String str, HealthPlugin healthPlugin, MethodChannel.Result result, SessionReadResponse sessionReadResponse) {
        Object obj;
        Object obj2;
        Iterator<Session> it;
        Object obj3;
        String str2;
        Object obj4;
        String str3;
        Object obj5;
        Device device;
        String str4;
        Object obj6;
        Iterator<DataPoint> it2;
        String str5;
        Device device2;
        String type = str;
        HealthPlugin this$0 = healthPlugin;
        SessionReadResponse response = sessionReadResponse;
        Intrinsics.checkNotNullParameter(type, "$type");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(result, "$result");
        Intrinsics.checkNotNullParameter(response, "response");
        ArrayList arrayList = new ArrayList();
        Iterator<Session> it3 = sessionReadResponse.getSessions().iterator();
        while (it3.hasNext()) {
            Session next = it3.next();
            if (Intrinsics.areEqual(type, this$0.SLEEP_ASLEEP)) {
                TimeUnit timeUnit = TimeUnit.MINUTES;
                Pair pair = TuplesKt.to("value", Long.valueOf(next.getEndTime(timeUnit) - next.getStartTime(timeUnit)));
                TimeUnit timeUnit2 = TimeUnit.MILLISECONDS;
                it = it3;
                obj = "source_name";
                obj2 = "MINUTES";
                obj3 = "source_id";
                arrayList.add(MapsKt.hashMapOf(pair, TuplesKt.to("date_from", Long.valueOf(next.getStartTime(timeUnit2))), TuplesKt.to("date_to", Long.valueOf(next.getEndTime(timeUnit2))), TuplesKt.to("unit", "MINUTES"), TuplesKt.to("source_name", next.getAppPackageName()), TuplesKt.to("source_id", next.getIdentifier())));
            } else {
                obj = "source_name";
                obj2 = "MINUTES";
                it = it3;
                obj3 = "source_id";
            }
            String str6 = "unknown";
            String str7 = "getDataSet(...)";
            if (Intrinsics.areEqual(type, this$0.SLEEP_IN_BED)) {
                List<DataSet> dataSet = response.getDataSet(next);
                Intrinsics.checkNotNullExpressionValue(dataSet, "getDataSet(...)");
                if (!dataSet.isEmpty()) {
                    Iterator<DataSet> it4 = dataSet.iterator();
                    while (it4.hasNext()) {
                        Iterator<DataPoint> it5 = it4.next().getDataPoints().iterator();
                        while (it5.hasNext()) {
                            DataPoint next2 = it5.next();
                            if (next2.getValue(Field.FIELD_SLEEP_SEGMENT_TYPE).asInt() != 3) {
                                TimeUnit timeUnit3 = TimeUnit.MINUTES;
                                Pair pair2 = TuplesKt.to("value", Long.valueOf(next2.getEndTime(timeUnit3) - next2.getStartTime(timeUnit3)));
                                TimeUnit timeUnit4 = TimeUnit.MILLISECONDS;
                                Iterator<DataSet> it6 = it4;
                                Pair pair3 = TuplesKt.to("date_from", Long.valueOf(next2.getStartTime(timeUnit4)));
                                Pair pair4 = TuplesKt.to("date_to", Long.valueOf(next2.getEndTime(timeUnit4)));
                                String str8 = str6;
                                Object obj7 = obj2;
                                Pair pair5 = TuplesKt.to("unit", obj7);
                                String appPackageName = next2.getOriginalDataSource().getAppPackageName();
                                if (appPackageName == null && ((device2 = next2.getOriginalDataSource().getDevice()) == null || (appPackageName = device2.getModel()) == null)) {
                                    it2 = it5;
                                    str5 = str8;
                                } else {
                                    it2 = it5;
                                    str5 = appPackageName;
                                }
                                Object obj8 = obj;
                                str4 = str7;
                                obj6 = obj8;
                                arrayList.add(MapsKt.hashMapOf(pair2, pair3, pair4, pair5, TuplesKt.to(obj6, str5), TuplesKt.to(obj3, next2.getOriginalDataSource().getStreamIdentifier())));
                                obj2 = obj7;
                                it4 = it6;
                                str6 = str8;
                                it5 = it2;
                            } else {
                                Object obj9 = obj;
                                str4 = str7;
                                obj6 = obj9;
                            }
                            String str9 = str4;
                            obj = obj6;
                            str7 = str9;
                        }
                        obj = obj;
                        str7 = str7;
                    }
                    str2 = str6;
                    obj4 = obj2;
                    Object obj10 = obj;
                    str3 = str7;
                    obj5 = obj10;
                } else {
                    str2 = "unknown";
                    obj4 = obj2;
                    Object obj11 = obj;
                    str3 = "getDataSet(...)";
                    obj5 = obj11;
                    TimeUnit timeUnit5 = TimeUnit.MINUTES;
                    Pair pair6 = TuplesKt.to("value", Long.valueOf(next.getEndTime(timeUnit5) - next.getStartTime(timeUnit5)));
                    TimeUnit timeUnit6 = TimeUnit.MILLISECONDS;
                    arrayList.add(MapsKt.hashMapOf(pair6, TuplesKt.to("date_from", Long.valueOf(next.getStartTime(timeUnit6))), TuplesKt.to("date_to", Long.valueOf(next.getEndTime(timeUnit6))), TuplesKt.to("unit", obj4), TuplesKt.to(obj5, next.getAppPackageName()), TuplesKt.to(obj3, next.getIdentifier())));
                }
            } else {
                str2 = str6;
                obj4 = obj2;
                Object obj102 = obj;
                str3 = str7;
                obj5 = obj102;
            }
            if (Intrinsics.areEqual(str, healthPlugin.SLEEP_AWAKE)) {
                Object obj12 = obj3;
                List<DataSet> dataSet2 = sessionReadResponse.getDataSet(next);
                Intrinsics.checkNotNullExpressionValue(dataSet2, str3);
                Iterator<DataSet> it7 = dataSet2.iterator();
                while (it7.hasNext()) {
                    for (DataPoint dataPoint : it7.next().getDataPoints()) {
                        if (dataPoint.getValue(Field.FIELD_SLEEP_SEGMENT_TYPE).asInt() == 1) {
                            TimeUnit timeUnit7 = TimeUnit.MINUTES;
                            Pair pair7 = TuplesKt.to("value", Long.valueOf(dataPoint.getEndTime(timeUnit7) - dataPoint.getStartTime(timeUnit7)));
                            TimeUnit timeUnit8 = TimeUnit.MILLISECONDS;
                            Pair pair8 = TuplesKt.to("date_from", Long.valueOf(dataPoint.getStartTime(timeUnit8)));
                            Pair pair9 = TuplesKt.to("date_to", Long.valueOf(dataPoint.getEndTime(timeUnit8)));
                            Pair pair10 = TuplesKt.to("unit", obj4);
                            String appPackageName2 = dataPoint.getOriginalDataSource().getAppPackageName();
                            arrayList.add(MapsKt.hashMapOf(pair7, pair8, pair9, pair10, TuplesKt.to(obj5, (appPackageName2 == null && ((device = dataPoint.getOriginalDataSource().getDevice()) == null || (appPackageName2 = device.getModel()) == null)) ? str2 : appPackageName2), TuplesKt.to(obj12, dataPoint.getOriginalDataSource().getStreamIdentifier())));
                            obj12 = obj12;
                            obj4 = obj4;
                        }
                    }
                }
            }
            response = sessionReadResponse;
            this$0 = healthPlugin;
            it3 = it;
            type = str;
        }
        Context context = this$0.context;
        Intrinsics.checkNotNull(context);
        new Handler(context.getMainLooper());
        result.success(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void success$lambda$0(HealthPlugin this$0, Object obj) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        MethodChannel.Result result = this$0.mResult;
        if (result != null) {
            result.success(obj);
        }
    }

    private final OnSuccessListener<SessionReadResponse> workoutDataHandler(String type, final MethodChannel.Result result) {
        return new OnSuccessListener() { // from class: cachet.plugins.health.d
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                HealthPlugin.workoutDataHandler$lambda$17(this.f8128a, result, (SessionReadResponse) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void workoutDataHandler$lambda$17(HealthPlugin this$0, MethodChannel.Result result, SessionReadResponse response) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(result, "$result");
        Intrinsics.checkNotNullParameter(response, "response");
        ArrayList arrayList = new ArrayList();
        for (Session session : response.getSessions()) {
            double d2 = 0.0d;
            double d3 = 0.0d;
            for (DataSet dataSet : response.getDataSet(session)) {
                if (Intrinsics.areEqual(dataSet.getDataType(), DataType.TYPE_CALORIES_EXPENDED)) {
                    Iterator<DataPoint> it = dataSet.getDataPoints().iterator();
                    while (it.hasNext()) {
                        String string = it.next().getValue(Field.FIELD_CALORIES).toString();
                        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                        d2 += Double.parseDouble(string);
                    }
                }
                if (Intrinsics.areEqual(dataSet.getDataType(), DataType.TYPE_DISTANCE_DELTA)) {
                    Iterator<DataPoint> it2 = dataSet.getDataPoints().iterator();
                    while (it2.hasNext()) {
                        String string2 = it2.next().getValue(Field.FIELD_DISTANCE).toString();
                        Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
                        d3 += Double.parseDouble(string2);
                    }
                }
            }
            Map<String, String> map = this$0.workoutTypeMap;
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (Intrinsics.areEqual(entry.getValue(), session.getActivity())) {
                    linkedHashMap.put(entry.getKey(), entry.getValue());
                }
            }
            String str = (String) CollectionsKt.firstOrNull(linkedHashMap.keySet());
            if (str == null) {
                str = "OTHER";
            }
            Pair pair = TuplesKt.to("workoutActivityType", str);
            Double dValueOf = null;
            Pair pair2 = TuplesKt.to("totalEnergyBurned", d2 == 0.0d ? null : Double.valueOf(d2));
            Pair pair3 = TuplesKt.to("totalEnergyBurnedUnit", "KILOCALORIE");
            if (d3 != 0.0d) {
                dValueOf = Double.valueOf(d3);
            }
            Pair pair4 = TuplesKt.to("totalDistance", dValueOf);
            Pair pair5 = TuplesKt.to("totalDistanceUnit", "METER");
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            arrayList.add(MapsKt.hashMapOf(pair, pair2, pair3, pair4, pair5, TuplesKt.to("date_from", Long.valueOf(session.getStartTime(timeUnit))), TuplesKt.to("date_to", Long.valueOf(session.getEndTime(timeUnit))), TuplesKt.to("unit", "MINUTES"), TuplesKt.to("source_name", session.getAppPackageName()), TuplesKt.to("source_id", session.getIdentifier())));
        }
        Context context = this$0.context;
        Intrinsics.checkNotNull(context);
        new Handler(context.getMainLooper());
        result.success(arrayList);
    }

    private final void writeBloodOxygen(MethodCall call, final MethodChannel.Result result) {
        if (this.useHealthConnectIfAvailable && this.healthConnectAvailable) {
            writeHCData(call, result);
            return;
        }
        if (this.context == null) {
            result.success(Boolean.FALSE);
            return;
        }
        DataType TYPE_OXYGEN_SATURATION = HealthDataTypes.TYPE_OXYGEN_SATURATION;
        Intrinsics.checkNotNullExpressionValue(TYPE_OXYGEN_SATURATION, "TYPE_OXYGEN_SATURATION");
        Object objArgument = call.argument("startTime");
        Intrinsics.checkNotNull(objArgument);
        long jLongValue = ((Number) objArgument).longValue();
        Object objArgument2 = call.argument(AUserTrack.UTKEY_END_TIME);
        Intrinsics.checkNotNull(objArgument2);
        long jLongValue2 = ((Number) objArgument2).longValue();
        Object objArgument3 = call.argument("value");
        Intrinsics.checkNotNull(objArgument3);
        float fFloatValue = ((Number) objArgument3).floatValue();
        Object objArgument4 = call.argument("flowRate");
        Intrinsics.checkNotNull(objArgument4);
        float fFloatValue2 = ((Number) objArgument4).floatValue();
        FitnessOptions.Builder builder = FitnessOptions.builder();
        Intrinsics.checkNotNullExpressionValue(builder, "builder(...)");
        builder.addDataType(TYPE_OXYGEN_SATURATION, 1);
        DataSource.Builder type = new DataSource.Builder().setDataType(TYPE_OXYGEN_SATURATION).setType(0);
        Context context = this.context;
        Intrinsics.checkNotNull(context);
        DataSource.Builder device = type.setDevice(Device.getLocalDevice(context.getApplicationContext()));
        Context context2 = this.context;
        Intrinsics.checkNotNull(context2);
        DataSource dataSourceBuild = device.setAppPackageName(context2.getApplicationContext()).build();
        Intrinsics.checkNotNullExpressionValue(dataSourceBuild, "build(...)");
        DataPoint.Builder timestamp = jLongValue == jLongValue2 ? DataPoint.builder(dataSourceBuild).setTimestamp(jLongValue, TimeUnit.MILLISECONDS) : DataPoint.builder(dataSourceBuild).setTimeInterval(jLongValue, jLongValue2, TimeUnit.MILLISECONDS);
        Intrinsics.checkNotNull(timestamp);
        timestamp.setField(HealthFields.FIELD_SUPPLEMENTAL_OXYGEN_FLOW_RATE, fFloatValue2);
        timestamp.setField(HealthFields.FIELD_OXYGEN_SATURATION, fFloatValue);
        DataPoint dataPointBuild = timestamp.build();
        Intrinsics.checkNotNullExpressionValue(dataPointBuild, "build(...)");
        DataSet dataSetBuild = DataSet.builder(dataSourceBuild).add(dataPointBuild).build();
        Intrinsics.checkNotNullExpressionValue(dataSetBuild, "build(...)");
        FitnessOptions fitnessOptionsBuild = builder.build();
        Intrinsics.checkNotNullExpressionValue(fitnessOptionsBuild, "build(...)");
        try {
            Context context3 = this.context;
            Intrinsics.checkNotNull(context3);
            GoogleSignInAccount accountForExtension = GoogleSignIn.getAccountForExtension(context3.getApplicationContext(), fitnessOptionsBuild);
            Intrinsics.checkNotNullExpressionValue(accountForExtension, "getAccountForExtension(...)");
            Context context4 = this.context;
            Intrinsics.checkNotNull(context4);
            Task<Void> taskInsertData = Fitness.getHistoryClient(context4.getApplicationContext(), accountForExtension).insertData(dataSetBuild);
            final Function1<Void, Unit> function1 = new Function1<Void, Unit>() { // from class: cachet.plugins.health.HealthPlugin.writeBloodOxygen.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Void r1) {
                    invoke2(r1);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Void r2) {
                    Log.i("FLUTTER_HEALTH::SUCCESS", "Blood Oxygen added successfully!");
                    result.success(Boolean.TRUE);
                }
            };
            taskInsertData.addOnSuccessListener(new OnSuccessListener() { // from class: cachet.plugins.health.r
                @Override // com.google.android.gms.tasks.OnSuccessListener
                public final void onSuccess(Object obj) {
                    HealthPlugin.writeBloodOxygen$lambda$6(function1, obj);
                }
            }).addOnFailureListener(errHandler(result, "There was an error adding the blood oxygen data!"));
        } catch (Exception unused) {
            result.success(Boolean.FALSE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void writeBloodOxygen$lambda$6(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void writeBloodPressure(MethodCall call, final MethodChannel.Result result) {
        if (this.useHealthConnectIfAvailable && this.healthConnectAvailable) {
            writeBloodPressureHC(call, result);
            return;
        }
        if (this.context == null) {
            result.success(Boolean.FALSE);
            return;
        }
        DataType TYPE_BLOOD_PRESSURE = HealthDataTypes.TYPE_BLOOD_PRESSURE;
        Intrinsics.checkNotNullExpressionValue(TYPE_BLOOD_PRESSURE, "TYPE_BLOOD_PRESSURE");
        Object objArgument = call.argument("systolic");
        Intrinsics.checkNotNull(objArgument);
        float fFloatValue = ((Number) objArgument).floatValue();
        Object objArgument2 = call.argument("diastolic");
        Intrinsics.checkNotNull(objArgument2);
        float fFloatValue2 = ((Number) objArgument2).floatValue();
        Object objArgument3 = call.argument("startTime");
        Intrinsics.checkNotNull(objArgument3);
        long jLongValue = ((Number) objArgument3).longValue();
        Object objArgument4 = call.argument(AUserTrack.UTKEY_END_TIME);
        Intrinsics.checkNotNull(objArgument4);
        long jLongValue2 = ((Number) objArgument4).longValue();
        FitnessOptions.Builder builder = FitnessOptions.builder();
        Intrinsics.checkNotNullExpressionValue(builder, "builder(...)");
        builder.addDataType(TYPE_BLOOD_PRESSURE, 1);
        DataSource.Builder type = new DataSource.Builder().setDataType(TYPE_BLOOD_PRESSURE).setType(0);
        Context context = this.context;
        Intrinsics.checkNotNull(context);
        DataSource.Builder device = type.setDevice(Device.getLocalDevice(context.getApplicationContext()));
        Context context2 = this.context;
        Intrinsics.checkNotNull(context2);
        DataSource dataSourceBuild = device.setAppPackageName(context2.getApplicationContext()).build();
        Intrinsics.checkNotNullExpressionValue(dataSourceBuild, "build(...)");
        DataPoint dataPointBuild = DataPoint.builder(dataSourceBuild).setTimeInterval(jLongValue, jLongValue2, TimeUnit.MILLISECONDS).setField(HealthFields.FIELD_BLOOD_PRESSURE_SYSTOLIC, fFloatValue).setField(HealthFields.FIELD_BLOOD_PRESSURE_DIASTOLIC, fFloatValue2).build();
        Intrinsics.checkNotNullExpressionValue(dataPointBuild, "build(...)");
        DataSet dataSetBuild = DataSet.builder(dataSourceBuild).add(dataPointBuild).build();
        Intrinsics.checkNotNullExpressionValue(dataSetBuild, "build(...)");
        FitnessOptions fitnessOptionsBuild = builder.build();
        Intrinsics.checkNotNullExpressionValue(fitnessOptionsBuild, "build(...)");
        try {
            Context context3 = this.context;
            Intrinsics.checkNotNull(context3);
            GoogleSignInAccount accountForExtension = GoogleSignIn.getAccountForExtension(context3.getApplicationContext(), fitnessOptionsBuild);
            Intrinsics.checkNotNullExpressionValue(accountForExtension, "getAccountForExtension(...)");
            Context context4 = this.context;
            Intrinsics.checkNotNull(context4);
            Task<Void> taskInsertData = Fitness.getHistoryClient(context4.getApplicationContext(), accountForExtension).insertData(dataSetBuild);
            final Function1<Void, Unit> function1 = new Function1<Void, Unit>() { // from class: cachet.plugins.health.HealthPlugin.writeBloodPressure.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Void r1) {
                    invoke2(r1);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Void r2) {
                    Log.i("FLUTTER_HEALTH::SUCCESS", "Blood Pressure added successfully!");
                    result.success(Boolean.TRUE);
                }
            };
            taskInsertData.addOnSuccessListener(new OnSuccessListener() { // from class: cachet.plugins.health.e
                @Override // com.google.android.gms.tasks.OnSuccessListener
                public final void onSuccess(Object obj) {
                    HealthPlugin.writeBloodPressure$lambda$4(function1, obj);
                }
            }).addOnFailureListener(errHandler(result, "There was an error adding the blood pressure data!"));
        } catch (Exception unused) {
            result.success(Boolean.FALSE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void writeBloodPressure$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void writeData(MethodCall call, final MethodChannel.Result result) {
        DataPoint dataPointBuild;
        if (this.useHealthConnectIfAvailable && this.healthConnectAvailable) {
            writeHCData(call, result);
            return;
        }
        if (this.context == null) {
            result.success(Boolean.FALSE);
            return;
        }
        Object objArgument = call.argument("dataTypeKey");
        Intrinsics.checkNotNull(objArgument);
        String str = (String) objArgument;
        Object objArgument2 = call.argument("startTime");
        Intrinsics.checkNotNull(objArgument2);
        long jLongValue = ((Number) objArgument2).longValue();
        Object objArgument3 = call.argument(AUserTrack.UTKEY_END_TIME);
        Intrinsics.checkNotNull(objArgument3);
        long jLongValue2 = ((Number) objArgument3).longValue();
        Object objArgument4 = call.argument("value");
        Intrinsics.checkNotNull(objArgument4);
        float fFloatValue = ((Number) objArgument4).floatValue();
        DataType dataTypeKeyToHealthDataType = keyToHealthDataType(str);
        Field field = getField(str);
        FitnessOptions.Builder builder = FitnessOptions.builder();
        Intrinsics.checkNotNullExpressionValue(builder, "builder(...)");
        builder.addDataType(dataTypeKeyToHealthDataType, 1);
        DataSource.Builder type = new DataSource.Builder().setDataType(dataTypeKeyToHealthDataType).setType(0);
        Context context = this.context;
        Intrinsics.checkNotNull(context);
        DataSource.Builder device = type.setDevice(Device.getLocalDevice(context.getApplicationContext()));
        Context context2 = this.context;
        Intrinsics.checkNotNull(context2);
        DataSource dataSourceBuild = device.setAppPackageName(context2.getApplicationContext()).build();
        Intrinsics.checkNotNullExpressionValue(dataSourceBuild, "build(...)");
        DataPoint.Builder timestamp = jLongValue == jLongValue2 ? DataPoint.builder(dataSourceBuild).setTimestamp(jLongValue, TimeUnit.MILLISECONDS) : DataPoint.builder(dataSourceBuild).setTimeInterval(jLongValue, jLongValue2, TimeUnit.MILLISECONDS);
        Intrinsics.checkNotNull(timestamp);
        boolean zAreEqual = Intrinsics.areEqual(field, HealthFields.FIELD_BLOOD_GLUCOSE_LEVEL);
        if (isIntField(dataSourceBuild, field)) {
            dataPointBuild = timestamp.setField(field, (int) fFloatValue).build();
        } else {
            if (zAreEqual) {
                fFloatValue = (float) (fFloatValue / 18.0d);
            }
            dataPointBuild = timestamp.setField(field, fFloatValue).build();
        }
        Intrinsics.checkNotNull(dataPointBuild);
        DataSet dataSetBuild = DataSet.builder(dataSourceBuild).add(dataPointBuild).build();
        Intrinsics.checkNotNullExpressionValue(dataSetBuild, "build(...)");
        if (Intrinsics.areEqual(dataTypeKeyToHealthDataType, DataType.TYPE_SLEEP_SEGMENT)) {
            builder.accessSleepSessions(0);
        }
        FitnessOptions fitnessOptionsBuild = builder.build();
        Intrinsics.checkNotNullExpressionValue(fitnessOptionsBuild, "build(...)");
        try {
            Context context3 = this.context;
            Intrinsics.checkNotNull(context3);
            GoogleSignInAccount accountForExtension = GoogleSignIn.getAccountForExtension(context3.getApplicationContext(), fitnessOptionsBuild);
            Intrinsics.checkNotNullExpressionValue(accountForExtension, "getAccountForExtension(...)");
            Context context4 = this.context;
            Intrinsics.checkNotNull(context4);
            Task<Void> taskInsertData = Fitness.getHistoryClient(context4.getApplicationContext(), accountForExtension).insertData(dataSetBuild);
            final Function1<Void, Unit> function1 = new Function1<Void, Unit>() { // from class: cachet.plugins.health.HealthPlugin.writeData.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Void r1) {
                    invoke2(r1);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Void r2) {
                    Log.i("FLUTTER_HEALTH::SUCCESS", "Dataset added successfully!");
                    result.success(Boolean.TRUE);
                }
            };
            taskInsertData.addOnSuccessListener(new OnSuccessListener() { // from class: cachet.plugins.health.i
                @Override // com.google.android.gms.tasks.OnSuccessListener
                public final void onSuccess(Object obj) {
                    HealthPlugin.writeData$lambda$5(function1, obj);
                }
            }).addOnFailureListener(errHandler(result, "There was an error adding the dataset"));
        } catch (Exception unused) {
            result.success(Boolean.FALSE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void writeData$lambda$5(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void writeWorkoutData(MethodCall call, MethodChannel.Result result) {
        DataSet dataSet;
        DataSet dataSetBuild;
        DataSet dataSet2;
        final MethodChannel.Result result2;
        Task<Void> taskInsertSession;
        if (this.useHealthConnectIfAvailable && this.healthConnectAvailable) {
            writeWorkoutHCData(call, result);
            return;
        }
        if (this.context == null) {
            result.success(Boolean.FALSE);
            return;
        }
        Object objArgument = call.argument("activityType");
        Intrinsics.checkNotNull(objArgument);
        Object objArgument2 = call.argument("startTime");
        Intrinsics.checkNotNull(objArgument2);
        long jLongValue = ((Number) objArgument2).longValue();
        Object objArgument3 = call.argument(AUserTrack.UTKEY_END_TIME);
        Intrinsics.checkNotNull(objArgument3);
        long jLongValue2 = ((Number) objArgument3).longValue();
        Integer num = (Integer) call.argument("totalEnergyBurned");
        Integer num2 = (Integer) call.argument("totalDistance");
        String activityType = getActivityType((String) objArgument);
        DataSource.Builder builder = new DataSource.Builder();
        Context context = this.context;
        Intrinsics.checkNotNull(context);
        DataSource.Builder appPackageName = builder.setAppPackageName(context.getPackageName());
        DataType dataType = DataType.TYPE_ACTIVITY_SEGMENT;
        DataSource dataSourceBuild = appPackageName.setDataType(dataType).setStreamName("FLUTTER_HEALTH - Activity").setType(0).build();
        Intrinsics.checkNotNullExpressionValue(dataSourceBuild, "build(...)");
        DataPoint.Builder builder2 = DataPoint.builder(dataSourceBuild);
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        DataPoint dataPointBuild = builder2.setTimeInterval(jLongValue, jLongValue2, timeUnit).setActivityField(Field.FIELD_ACTIVITY, activityType).build();
        Intrinsics.checkNotNullExpressionValue(dataPointBuild, "build(...)");
        DataSet dataSetBuild2 = DataSet.builder(dataSourceBuild).add(dataPointBuild).build();
        Intrinsics.checkNotNullExpressionValue(dataSetBuild2, "build(...)");
        DataSet dataSetBuild3 = null;
        if (num2 != null) {
            DataSource.Builder builder3 = new DataSource.Builder();
            Context context2 = this.context;
            Intrinsics.checkNotNull(context2);
            DataSource dataSourceBuild2 = builder3.setAppPackageName(context2.getPackageName()).setDataType(DataType.TYPE_DISTANCE_DELTA).setStreamName("FLUTTER_HEALTH - Distance").setType(0).build();
            Intrinsics.checkNotNullExpressionValue(dataSourceBuild2, "build(...)");
            dataSet = dataSetBuild2;
            DataPoint dataPointBuild2 = DataPoint.builder(dataSourceBuild2).setTimeInterval(jLongValue, jLongValue2, timeUnit).setField(Field.FIELD_DISTANCE, num2.intValue()).build();
            Intrinsics.checkNotNullExpressionValue(dataPointBuild2, "build(...)");
            dataSetBuild = DataSet.builder(dataSourceBuild2).add(dataPointBuild2).build();
        } else {
            dataSet = dataSetBuild2;
            dataSetBuild = null;
        }
        if (num != null) {
            DataSource.Builder builder4 = new DataSource.Builder();
            Context context3 = this.context;
            Intrinsics.checkNotNull(context3);
            DataSource dataSourceBuild3 = builder4.setAppPackageName(context3.getPackageName()).setDataType(DataType.TYPE_CALORIES_EXPENDED).setStreamName("FLUTTER_HEALTH - Calories").setType(0).build();
            Intrinsics.checkNotNullExpressionValue(dataSourceBuild3, "build(...)");
            dataSet2 = dataSetBuild;
            DataPoint dataPointBuild3 = DataPoint.builder(dataSourceBuild3).setTimeInterval(jLongValue, jLongValue2, timeUnit).setField(Field.FIELD_CALORIES, num.intValue()).build();
            Intrinsics.checkNotNullExpressionValue(dataPointBuild3, "build(...)");
            dataSetBuild3 = DataSet.builder(dataSourceBuild3).add(dataPointBuild3).build();
        } else {
            dataSet2 = dataSetBuild;
        }
        DataSet dataSet3 = dataSetBuild3;
        Session sessionBuild = new Session.Builder().setName(activityType).setDescription("").setIdentifier(UUID.randomUUID().toString()).setActivity(activityType).setStartTime(jLongValue, timeUnit).setEndTime(jLongValue2, timeUnit).build();
        Intrinsics.checkNotNullExpressionValue(sessionBuild, "build(...)");
        SessionInsertRequest.Builder builderAddDataSet = new SessionInsertRequest.Builder().setSession(sessionBuild).addDataSet(dataSet);
        Intrinsics.checkNotNullExpressionValue(builderAddDataSet, "addDataSet(...)");
        if (num2 != null) {
            Intrinsics.checkNotNull(dataSet2);
            builderAddDataSet.addDataSet(dataSet2);
        }
        if (num != null) {
            Intrinsics.checkNotNull(dataSet3);
            builderAddDataSet.addDataSet(dataSet3);
        }
        SessionInsertRequest sessionInsertRequestBuild = builderAddDataSet.build();
        Intrinsics.checkNotNullExpressionValue(sessionInsertRequestBuild, "build(...)");
        FitnessOptions.Builder builderAddDataType = FitnessOptions.builder().addDataType(dataType, 1);
        Intrinsics.checkNotNullExpressionValue(builderAddDataType, "addDataType(...)");
        if (num2 != null) {
            builderAddDataType.addDataType(DataType.TYPE_DISTANCE_DELTA, 1);
        }
        if (num != null) {
            builderAddDataType.addDataType(DataType.TYPE_CALORIES_EXPENDED, 1);
        }
        FitnessOptions fitnessOptionsBuild = builderAddDataType.build();
        Intrinsics.checkNotNullExpressionValue(fitnessOptionsBuild, "build(...)");
        try {
            Context context4 = this.context;
            Intrinsics.checkNotNull(context4);
            GoogleSignInAccount accountForExtension = GoogleSignIn.getAccountForExtension(context4.getApplicationContext(), fitnessOptionsBuild);
            Intrinsics.checkNotNullExpressionValue(accountForExtension, "getAccountForExtension(...)");
            Context context5 = this.context;
            Intrinsics.checkNotNull(context5);
            taskInsertSession = Fitness.getSessionsClient(context5.getApplicationContext(), accountForExtension).insertSession(sessionInsertRequestBuild);
            result2 = result;
        } catch (Exception unused) {
            result2 = result;
        }
        try {
            final Function1<Void, Unit> function1 = new Function1<Void, Unit>() { // from class: cachet.plugins.health.HealthPlugin.writeWorkoutData.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Void r1) {
                    invoke2(r1);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Void r2) {
                    Log.i("FLUTTER_HEALTH::SUCCESS", "Workout was successfully added!");
                    result2.success(Boolean.TRUE);
                }
            };
            taskInsertSession.addOnSuccessListener(new OnSuccessListener() { // from class: cachet.plugins.health.m
                @Override // com.google.android.gms.tasks.OnSuccessListener
                public final void onSuccess(Object obj) {
                    HealthPlugin.writeWorkoutData$lambda$7(function1, obj);
                }
            }).addOnFailureListener(errHandler(result2, "There was an error adding the workout"));
        } catch (Exception unused2) {
            result2.success(Boolean.FALSE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void writeWorkoutData$lambda$7(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void checkAvailability() {
        HealthConnectClient.Companion companion = HealthConnectClient.INSTANCE;
        Context context = this.context;
        Intrinsics.checkNotNull(context);
        int sdkStatus$default = HealthConnectClient.Companion.getSdkStatus$default(companion, context, null, 2, null);
        this.healthConnectStatus = sdkStatus$default;
        this.healthConnectAvailable = sdkStatus$default == 3;
    }

    @NotNull
    public final List<Map<String, Object>> convertRecord(@NotNull Object record, @NotNull String dataType) {
        Intrinsics.checkNotNullParameter(record, "record");
        Intrinsics.checkNotNullParameter(dataType, "dataType");
        androidx.health.connect.client.records.metadata.Metadata metadata = ((Record) record).getMetadata();
        if (record instanceof WeightRecord) {
            WeightRecord weightRecord = (WeightRecord) record;
            return CollectionsKt.listOf(MapsKt.mapOf(TuplesKt.to("value", Double.valueOf(weightRecord.getWeight().getKilograms())), TuplesKt.to("date_from", Long.valueOf(weightRecord.getTime().toEpochMilli())), TuplesKt.to("date_to", Long.valueOf(weightRecord.getTime().toEpochMilli())), TuplesKt.to("source_id", ""), TuplesKt.to("source_name", metadata.getDataOrigin().getPackageName())));
        }
        if (record instanceof HeightRecord) {
            HeightRecord heightRecord = (HeightRecord) record;
            return CollectionsKt.listOf(MapsKt.mapOf(TuplesKt.to("value", Double.valueOf(heightRecord.getHeight().getMeters())), TuplesKt.to("date_from", Long.valueOf(heightRecord.getTime().toEpochMilli())), TuplesKt.to("date_to", Long.valueOf(heightRecord.getTime().toEpochMilli())), TuplesKt.to("source_id", ""), TuplesKt.to("source_name", metadata.getDataOrigin().getPackageName())));
        }
        if (record instanceof BodyFatRecord) {
            BodyFatRecord bodyFatRecord = (BodyFatRecord) record;
            return CollectionsKt.listOf(MapsKt.mapOf(TuplesKt.to("value", Double.valueOf(bodyFatRecord.getPercentage().getValue())), TuplesKt.to("date_from", Long.valueOf(bodyFatRecord.getTime().toEpochMilli())), TuplesKt.to("date_to", Long.valueOf(bodyFatRecord.getTime().toEpochMilli())), TuplesKt.to("source_id", ""), TuplesKt.to("source_name", metadata.getDataOrigin().getPackageName())));
        }
        if (record instanceof StepsRecord) {
            StepsRecord stepsRecord = (StepsRecord) record;
            return CollectionsKt.listOf(MapsKt.mapOf(TuplesKt.to("value", Long.valueOf(stepsRecord.getCount())), TuplesKt.to("date_from", Long.valueOf(stepsRecord.getStartTime().toEpochMilli())), TuplesKt.to("date_to", Long.valueOf(stepsRecord.getEndTime().toEpochMilli())), TuplesKt.to("source_id", ""), TuplesKt.to("source_name", metadata.getDataOrigin().getPackageName())));
        }
        if (record instanceof ActiveCaloriesBurnedRecord) {
            ActiveCaloriesBurnedRecord activeCaloriesBurnedRecord = (ActiveCaloriesBurnedRecord) record;
            return CollectionsKt.listOf(MapsKt.mapOf(TuplesKt.to("value", Double.valueOf(activeCaloriesBurnedRecord.getEnergy().getKilocalories())), TuplesKt.to("date_from", Long.valueOf(activeCaloriesBurnedRecord.getStartTime().toEpochMilli())), TuplesKt.to("date_to", Long.valueOf(activeCaloriesBurnedRecord.getEndTime().toEpochMilli())), TuplesKt.to("source_id", ""), TuplesKt.to("source_name", metadata.getDataOrigin().getPackageName())));
        }
        if (record instanceof HeartRateRecord) {
            List<HeartRateRecord.Sample> samples = ((HeartRateRecord) record).getSamples();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(samples, 10));
            for (Iterator it = samples.iterator(); it.hasNext(); it = it) {
                HeartRateRecord.Sample sample = (HeartRateRecord.Sample) it.next();
                arrayList.add(MapsKt.mapOf(TuplesKt.to("value", Long.valueOf(sample.getBeatsPerMinute())), TuplesKt.to("date_from", Long.valueOf(sample.getTime().toEpochMilli())), TuplesKt.to("date_to", Long.valueOf(sample.getTime().toEpochMilli())), TuplesKt.to("source_id", ""), TuplesKt.to("source_name", metadata.getDataOrigin().getPackageName())));
            }
            return arrayList;
        }
        if (record instanceof BodyTemperatureRecord) {
            BodyTemperatureRecord bodyTemperatureRecord = (BodyTemperatureRecord) record;
            return CollectionsKt.listOf(MapsKt.mapOf(TuplesKt.to("value", Double.valueOf(bodyTemperatureRecord.getTemperature().getCelsius())), TuplesKt.to("date_from", Long.valueOf(bodyTemperatureRecord.getTime().toEpochMilli())), TuplesKt.to("date_to", Long.valueOf(bodyTemperatureRecord.getTime().toEpochMilli())), TuplesKt.to("source_id", ""), TuplesKt.to("source_name", metadata.getDataOrigin().getPackageName())));
        }
        if (record instanceof BloodPressureRecord) {
            BloodPressureRecord bloodPressureRecord = (BloodPressureRecord) record;
            return CollectionsKt.listOf(MapsKt.mapOf(TuplesKt.to("value", Double.valueOf((Intrinsics.areEqual(dataType, this.BLOOD_PRESSURE_DIASTOLIC) ? ((BloodPressureRecord) record).getDiastolic() : ((BloodPressureRecord) record).getSystolic()).getValue())), TuplesKt.to("date_from", Long.valueOf(bloodPressureRecord.getTime().toEpochMilli())), TuplesKt.to("date_to", Long.valueOf(bloodPressureRecord.getTime().toEpochMilli())), TuplesKt.to("source_id", ""), TuplesKt.to("source_name", metadata.getDataOrigin().getPackageName())));
        }
        if (record instanceof OxygenSaturationRecord) {
            OxygenSaturationRecord oxygenSaturationRecord = (OxygenSaturationRecord) record;
            return CollectionsKt.listOf(MapsKt.mapOf(TuplesKt.to("value", Double.valueOf(oxygenSaturationRecord.getPercentage().getValue())), TuplesKt.to("date_from", Long.valueOf(oxygenSaturationRecord.getTime().toEpochMilli())), TuplesKt.to("date_to", Long.valueOf(oxygenSaturationRecord.getTime().toEpochMilli())), TuplesKt.to("source_id", ""), TuplesKt.to("source_name", metadata.getDataOrigin().getPackageName())));
        }
        if (record instanceof BloodGlucoseRecord) {
            BloodGlucoseRecord bloodGlucoseRecord = (BloodGlucoseRecord) record;
            return CollectionsKt.listOf(MapsKt.mapOf(TuplesKt.to("value", Double.valueOf(bloodGlucoseRecord.getLevel().getMilligramsPerDeciliter())), TuplesKt.to("date_from", Long.valueOf(bloodGlucoseRecord.getTime().toEpochMilli())), TuplesKt.to("date_to", Long.valueOf(bloodGlucoseRecord.getTime().toEpochMilli())), TuplesKt.to("source_id", ""), TuplesKt.to("source_name", metadata.getDataOrigin().getPackageName())));
        }
        if (record instanceof DistanceRecord) {
            DistanceRecord distanceRecord = (DistanceRecord) record;
            return CollectionsKt.listOf(MapsKt.mapOf(TuplesKt.to("value", Double.valueOf(distanceRecord.getDistance().getMeters())), TuplesKt.to("date_from", Long.valueOf(distanceRecord.getStartTime().toEpochMilli())), TuplesKt.to("date_to", Long.valueOf(distanceRecord.getEndTime().toEpochMilli())), TuplesKt.to("source_id", ""), TuplesKt.to("source_name", metadata.getDataOrigin().getPackageName())));
        }
        if (record instanceof HydrationRecord) {
            HydrationRecord hydrationRecord = (HydrationRecord) record;
            return CollectionsKt.listOf(MapsKt.mapOf(TuplesKt.to("value", Double.valueOf(hydrationRecord.getVolume().getLiters())), TuplesKt.to("date_from", Long.valueOf(hydrationRecord.getStartTime().toEpochMilli())), TuplesKt.to("date_to", Long.valueOf(hydrationRecord.getEndTime().toEpochMilli())), TuplesKt.to("source_id", ""), TuplesKt.to("source_name", metadata.getDataOrigin().getPackageName())));
        }
        if (record instanceof SleepSessionRecord) {
            SleepSessionRecord sleepSessionRecord = (SleepSessionRecord) record;
            return CollectionsKt.listOf(MapsKt.mapOf(TuplesKt.to("date_from", Long.valueOf(sleepSessionRecord.getStartTime().toEpochMilli())), TuplesKt.to("date_to", Long.valueOf(sleepSessionRecord.getEndTime().toEpochMilli())), TuplesKt.to("value", Long.valueOf(ChronoUnit.MINUTES.between(androidx.health.connect.client.records.h.a(sleepSessionRecord.getStartTime()), androidx.health.connect.client.records.h.a(sleepSessionRecord.getEndTime())))), TuplesKt.to("source_id", ""), TuplesKt.to("source_name", metadata.getDataOrigin().getPackageName())));
        }
        if (record instanceof SleepStageRecord) {
            SleepStageRecord sleepStageRecord = (SleepStageRecord) record;
            return CollectionsKt.listOf(MapsKt.mapOf(TuplesKt.to("stage", Integer.valueOf(sleepStageRecord.getStage())), TuplesKt.to("value", Long.valueOf(ChronoUnit.MINUTES.between(androidx.health.connect.client.records.h.a(sleepStageRecord.getStartTime()), androidx.health.connect.client.records.h.a(sleepStageRecord.getEndTime())))), TuplesKt.to("date_from", Long.valueOf(sleepStageRecord.getStartTime().toEpochMilli())), TuplesKt.to("date_to", Long.valueOf(sleepStageRecord.getEndTime().toEpochMilli())), TuplesKt.to("source_id", ""), TuplesKt.to("source_name", metadata.getDataOrigin().getPackageName())));
        }
        if (record instanceof RestingHeartRateRecord) {
            RestingHeartRateRecord restingHeartRateRecord = (RestingHeartRateRecord) record;
            return CollectionsKt.listOf(MapsKt.mapOf(TuplesKt.to("value", Long.valueOf(restingHeartRateRecord.getBeatsPerMinute())), TuplesKt.to("date_from", Long.valueOf(restingHeartRateRecord.getTime().toEpochMilli())), TuplesKt.to("date_to", Long.valueOf(restingHeartRateRecord.getTime().toEpochMilli())), TuplesKt.to("source_id", ""), TuplesKt.to("source_name", metadata.getDataOrigin().getPackageName())));
        }
        if (record instanceof BasalMetabolicRateRecord) {
            BasalMetabolicRateRecord basalMetabolicRateRecord = (BasalMetabolicRateRecord) record;
            return CollectionsKt.listOf(MapsKt.mapOf(TuplesKt.to("value", Double.valueOf(basalMetabolicRateRecord.getBasalMetabolicRate().getKilocaloriesPerDay())), TuplesKt.to("date_from", Long.valueOf(basalMetabolicRateRecord.getTime().toEpochMilli())), TuplesKt.to("date_to", Long.valueOf(basalMetabolicRateRecord.getTime().toEpochMilli())), TuplesKt.to("source_id", ""), TuplesKt.to("source_name", metadata.getDataOrigin().getPackageName())));
        }
        if (record instanceof FloorsClimbedRecord) {
            FloorsClimbedRecord floorsClimbedRecord = (FloorsClimbedRecord) record;
            return CollectionsKt.listOf(MapsKt.mapOf(TuplesKt.to("value", Double.valueOf(floorsClimbedRecord.getFloors())), TuplesKt.to("date_from", Long.valueOf(floorsClimbedRecord.getStartTime().toEpochMilli())), TuplesKt.to("date_to", Long.valueOf(floorsClimbedRecord.getEndTime().toEpochMilli())), TuplesKt.to("source_id", ""), TuplesKt.to("source_name", metadata.getDataOrigin().getPackageName())));
        }
        if (!(record instanceof RespiratoryRateRecord)) {
            throw new IllegalArgumentException("Health data type not supported");
        }
        RespiratoryRateRecord respiratoryRateRecord = (RespiratoryRateRecord) record;
        return CollectionsKt.listOf(MapsKt.mapOf(TuplesKt.to("value", Double.valueOf(respiratoryRateRecord.getRate())), TuplesKt.to("date_from", Long.valueOf(respiratoryRateRecord.getTime().toEpochMilli())), TuplesKt.to("date_to", Long.valueOf(respiratoryRateRecord.getTime().toEpochMilli())), TuplesKt.to("source_id", ""), TuplesKt.to("source_name", metadata.getDataOrigin().getPackageName())));
    }

    public final void deleteHCData(@NotNull MethodCall call, @NotNull MethodChannel.Result result) {
        CoroutineScope coroutineScope;
        CoroutineScope coroutineScope2;
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        Object objArgument = call.argument("dataTypeKey");
        Intrinsics.checkNotNull(objArgument);
        String str = (String) objArgument;
        Object objArgument2 = call.argument("startTime");
        Intrinsics.checkNotNull(objArgument2);
        Instant instantOfEpochMilli = Instant.ofEpochMilli(((Number) objArgument2).longValue());
        Object objArgument3 = call.argument(AUserTrack.UTKEY_END_TIME);
        Intrinsics.checkNotNull(objArgument3);
        Instant instantOfEpochMilli2 = Instant.ofEpochMilli(((Number) objArgument3).longValue());
        KClass<? extends Record> kClass = this.MapToHCType.get(str);
        Intrinsics.checkNotNull(kClass);
        KClass<? extends Record> kClass2 = kClass;
        if (Intrinsics.areEqual(str, this.WORKOUT)) {
            CoroutineScope coroutineScope3 = this.scope;
            if (coroutineScope3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("scope");
                coroutineScope2 = null;
            } else {
                coroutineScope2 = coroutineScope3;
            }
            BuildersKt__Builders_commonKt.launch$default(coroutineScope2, null, null, new C03761(instantOfEpochMilli, instantOfEpochMilli2, result, null), 3, null);
            return;
        }
        CoroutineScope coroutineScope4 = this.scope;
        if (coroutineScope4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scope");
            coroutineScope = null;
        } else {
            coroutineScope = coroutineScope4;
        }
        BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new AnonymousClass2(kClass2, instantOfEpochMilli, instantOfEpochMilli2, result, null), 3, null);
    }

    @Override // io.flutter.plugin.common.MethodChannel.Result
    public void error(@NotNull final String errorCode, @Nullable final String errorMessage, @Nullable final Object errorDetails) {
        Intrinsics.checkNotNullParameter(errorCode, "errorCode");
        Handler handler = this.handler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: cachet.plugins.health.k
                @Override // java.lang.Runnable
                public final void run() {
                    HealthPlugin.error$lambda$2(this.f8145a, errorCode, errorMessage, errorDetails);
                }
            });
        }
    }

    public final void getHCData(@NotNull MethodCall call, @NotNull MethodChannel.Result result) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        Object objArgument = call.argument("dataTypeKey");
        Intrinsics.checkNotNull(objArgument);
        String str = (String) objArgument;
        Object objArgument2 = call.argument("startTime");
        Intrinsics.checkNotNull(objArgument2);
        Instant instantOfEpochMilli = Instant.ofEpochMilli(((Number) objArgument2).longValue());
        Object objArgument3 = call.argument(AUserTrack.UTKEY_END_TIME);
        Intrinsics.checkNotNull(objArgument3);
        Instant instantOfEpochMilli2 = Instant.ofEpochMilli(((Number) objArgument3).longValue());
        ArrayList arrayList = new ArrayList();
        CoroutineScope coroutineScope = this.scope;
        if (coroutineScope == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scope");
            coroutineScope = null;
        }
        BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new C03771(str, instantOfEpochMilli, instantOfEpochMilli2, arrayList, result, null), 3, null);
    }

    public final boolean getHealthConnectAvailable() {
        return this.healthConnectAvailable;
    }

    public final int getHealthConnectStatus() {
        return this.healthConnectStatus;
    }

    @NotNull
    public final HashMap<Integer, String> getMapSleepStageToType() {
        return this.MapSleepStageToType;
    }

    @NotNull
    public final HashMap<String, KClass<? extends Record>> getMapToHCType() {
        return this.MapToHCType;
    }

    @NotNull
    public final Map<String, String> getWorkoutTypeMap() {
        return this.workoutTypeMap;
    }

    @NotNull
    public final Map<String, Integer> getWorkoutTypeMapHealthConnect() {
        return this.workoutTypeMapHealthConnect;
    }

    public final void healthConnectAvailable(@NotNull MethodCall call, @NotNull MethodChannel.Result result) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        result.success(Boolean.valueOf(this.healthConnectAvailable));
    }

    @Override // io.flutter.plugin.common.MethodChannel.Result
    public void notImplemented() {
        Handler handler = this.handler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: cachet.plugins.health.c
                @Override // java.lang.Runnable
                public final void run() {
                    HealthPlugin.notImplemented$lambda$1(this.f8127a);
                }
            });
        }
    }

    @Override // io.flutter.plugin.common.PluginRegistry.ActivityResultListener
    public boolean onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Bundle extras;
        if (requestCode == 1111) {
            if (resultCode == -1) {
                Log.i("FLUTTER_HEALTH", "Access Granted!");
                MethodChannel.Result result = this.mResult;
                if (result != null) {
                    result.success(Boolean.TRUE);
                }
            } else if (resultCode == 0) {
                Log.i("FLUTTER_HEALTH", "Access Denied!");
                MethodChannel.Result result2 = this.mResult;
                if (result2 != null) {
                    result2.success(Boolean.FALSE);
                }
            }
        }
        if (requestCode == 16969) {
            if (resultCode != -1) {
                if (resultCode == 0) {
                    Log.i("FLUTTER_HEALTH", "Access Denied (to Health Connect)!");
                    MethodChannel.Result result3 = this.mResult;
                    if (result3 != null) {
                        result3.success(Boolean.FALSE);
                    }
                }
            } else {
                if (data != null && (extras = data.getExtras()) != null && extras.containsKey("request_blocked")) {
                    Log.i("FLUTTER_HEALTH", "Access Denied (to Health Connect) due to too many requests!");
                    MethodChannel.Result result4 = this.mResult;
                    if (result4 != null) {
                        result4.success(Boolean.FALSE);
                    }
                    return false;
                }
                Log.i("FLUTTER_HEALTH", "Access Granted (to Health Connect)!");
                MethodChannel.Result result5 = this.mResult;
                if (result5 != null) {
                    result5.success(Boolean.TRUE);
                }
            }
        }
        return false;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(@NotNull ActivityPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        if (this.channel == null) {
            return;
        }
        binding.addActivityResultListener(this);
        this.activity = binding.getActivity();
        ActivityResultContract activityResultContractCreateRequestPermissionResultContract$default = PermissionController.Companion.createRequestPermissionResultContract$default(PermissionController.INSTANCE, null, 1, null);
        Activity activity = this.activity;
        Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type androidx.activity.ComponentActivity");
        this.healthConnectRequestPermissionsLauncher = ((ComponentActivity) activity).registerForActivityResult(activityResultContractCreateRequestPermissionResultContract$default, new ActivityResultCallback() { // from class: cachet.plugins.health.j
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                HealthPlugin.onAttachedToActivity$lambda$24(this.f8144a, (Set) obj);
            }
        });
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull @NotNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        Intrinsics.checkNotNullParameter(flutterPluginBinding, "flutterPluginBinding");
        this.scope = CoroutineScopeKt.CoroutineScope(SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null).plus(Dispatchers.getMain()));
        MethodChannel methodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), HealthPluginKt.CHANNEL_NAME);
        this.channel = methodChannel;
        methodChannel.setMethodCallHandler(this);
        this.context = flutterPluginBinding.getApplicationContext();
        this.threadPoolExecutor = Executors.newFixedThreadPool(4);
        checkAvailability();
        if (this.healthConnectAvailable) {
            HealthConnectClient.Companion companion = HealthConnectClient.INSTANCE;
            Context applicationContext = flutterPluginBinding.getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
            this.healthConnectClient = HealthConnectClient.Companion.getOrCreate$default(companion, applicationContext, null, 2, null);
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        if (this.channel == null) {
            return;
        }
        this.activity = null;
        this.healthConnectRequestPermissionsLauncher = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity();
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NotNull FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        this.channel = null;
        this.activity = null;
        ExecutorService executorService = this.threadPoolExecutor;
        Intrinsics.checkNotNull(executorService);
        executorService.shutdown();
        this.threadPoolExecutor = null;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(@NotNull MethodCall call, @NotNull MethodChannel.Result result) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        String str = call.method;
        if (str != null) {
            switch (str.hashCode()) {
                case -1776912268:
                    if (str.equals("getHealthConnectSdkStatus")) {
                        getHealthConnectSdkStatus(call, result);
                        return;
                    }
                    break;
                case -1664987429:
                    if (str.equals("writeBloodOxygen")) {
                        writeBloodOxygen(call, result);
                        return;
                    }
                    break;
                case -1521132002:
                    if (str.equals("revokePermissions")) {
                        revokePermissions(call, result);
                        return;
                    }
                    break;
                case -1406815191:
                    if (str.equals("writeData")) {
                        writeData(call, result);
                        return;
                    }
                    break;
                case -1335458389:
                    if (str.equals(RequestParameters.SUBRESOURCE_DELETE)) {
                        delete(call, result);
                        return;
                    }
                    break;
                case -184634333:
                    if (str.equals("getTotalStepsInInterval")) {
                        getTotalStepsInInterval(call, result);
                        return;
                    }
                    break;
                case -75605984:
                    if (str.equals("getData")) {
                        getData(call, result);
                        return;
                    }
                    break;
                case 2116357:
                    if (str.equals("useHealthConnectIfAvailable")) {
                        useHealthConnectIfAvailable(call, result);
                        return;
                    }
                    break;
                case 560506747:
                    if (str.equals("healthConnectAvailable")) {
                        healthConnectAvailable(call, result);
                        return;
                    }
                    break;
                case 1032406410:
                    if (str.equals("hasPermissions")) {
                        hasPermissions(call, result);
                        return;
                    }
                    break;
                case 1410731656:
                    if (str.equals("writeWorkoutData")) {
                        writeWorkoutData(call, result);
                        return;
                    }
                    break;
                case 2071735571:
                    if (str.equals("installHealthConnect")) {
                        installHealthConnect(call, result);
                        return;
                    }
                    break;
                case 2113338922:
                    if (str.equals("requestAuthorization")) {
                        requestAuthorization(call, result);
                        return;
                    }
                    break;
                case 2121564352:
                    if (str.equals("writeBloodPressure")) {
                        writeBloodPressure(call, result);
                        return;
                    }
                    break;
            }
        }
        result.notImplemented();
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(@NotNull ActivityPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        onAttachedToActivity(binding);
    }

    public final void setHealthConnectAvailable(boolean z2) {
        this.healthConnectAvailable = z2;
    }

    public final void setHealthConnectStatus(int i2) {
        this.healthConnectStatus = i2;
    }

    @Override // io.flutter.plugin.common.MethodChannel.Result
    public void success(@Nullable final Object p02) {
        Handler handler = this.handler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: cachet.plugins.health.n
                @Override // java.lang.Runnable
                public final void run() {
                    HealthPlugin.success$lambda$0(this.f8151a, p02);
                }
            });
        }
    }

    public final void useHealthConnectIfAvailable(@NotNull MethodCall call, @NotNull MethodChannel.Result result) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        this.useHealthConnectIfAvailable = true;
        result.success(null);
    }

    public final void writeBloodPressureHC(@NotNull MethodCall call, @NotNull MethodChannel.Result result) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        Object objArgument = call.argument("systolic");
        Intrinsics.checkNotNull(objArgument);
        double dDoubleValue = ((Number) objArgument).doubleValue();
        Object objArgument2 = call.argument("diastolic");
        Intrinsics.checkNotNull(objArgument2);
        double dDoubleValue2 = ((Number) objArgument2).doubleValue();
        Object objArgument3 = call.argument("startTime");
        Intrinsics.checkNotNull(objArgument3);
        Instant instantOfEpochMilli = Instant.ofEpochMilli(((Number) objArgument3).longValue());
        Object objArgument4 = call.argument(AUserTrack.UTKEY_END_TIME);
        Intrinsics.checkNotNull(objArgument4);
        Instant.ofEpochMilli(((Number) objArgument4).longValue());
        CoroutineScope coroutineScope = this.scope;
        if (coroutineScope == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scope");
            coroutineScope = null;
        }
        BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new C03831(dDoubleValue, dDoubleValue2, instantOfEpochMilli, result, null), 3, null);
    }

    public final void writeHCData(@NotNull MethodCall call, @NotNull MethodChannel.Result result) {
        Record respiratoryRateRecord;
        Record restingHeartRateRecord;
        CoroutineScope coroutineScope;
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        Object objArgument = call.argument("dataTypeKey");
        Intrinsics.checkNotNull(objArgument);
        String str = (String) objArgument;
        Object objArgument2 = call.argument("startTime");
        Intrinsics.checkNotNull(objArgument2);
        long jLongValue = ((Number) objArgument2).longValue();
        Object objArgument3 = call.argument(AUserTrack.UTKEY_END_TIME);
        Intrinsics.checkNotNull(objArgument3);
        long jLongValue2 = ((Number) objArgument3).longValue();
        Object objArgument4 = call.argument("value");
        Intrinsics.checkNotNull(objArgument4);
        double dDoubleValue = ((Number) objArgument4).doubleValue();
        if (Intrinsics.areEqual(str, this.BODY_FAT_PERCENTAGE)) {
            Instant instantOfEpochMilli = Instant.ofEpochMilli(jLongValue);
            Percentage percentage = new Percentage(dDoubleValue);
            Intrinsics.checkNotNull(instantOfEpochMilli);
            respiratoryRateRecord = new BodyFatRecord(instantOfEpochMilli, null, percentage, null, 8, null);
        } else if (Intrinsics.areEqual(str, this.HEIGHT)) {
            Instant instantOfEpochMilli2 = Instant.ofEpochMilli(jLongValue);
            Length lengthMeters = Length.INSTANCE.meters(dDoubleValue);
            Intrinsics.checkNotNull(instantOfEpochMilli2);
            respiratoryRateRecord = new HeightRecord(instantOfEpochMilli2, null, lengthMeters, null, 8, null);
        } else if (Intrinsics.areEqual(str, this.WEIGHT)) {
            Instant instantOfEpochMilli3 = Instant.ofEpochMilli(jLongValue);
            Mass massKilograms = Mass.INSTANCE.kilograms(dDoubleValue);
            Intrinsics.checkNotNull(instantOfEpochMilli3);
            respiratoryRateRecord = new WeightRecord(instantOfEpochMilli3, null, massKilograms, null, 8, null);
        } else {
            if (Intrinsics.areEqual(str, this.STEPS)) {
                Instant instantOfEpochMilli4 = Instant.ofEpochMilli(jLongValue);
                Instant instantOfEpochMilli5 = Instant.ofEpochMilli(jLongValue2);
                Intrinsics.checkNotNull(instantOfEpochMilli4);
                Intrinsics.checkNotNull(instantOfEpochMilli5);
                restingHeartRateRecord = new StepsRecord(instantOfEpochMilli4, null, instantOfEpochMilli5, null, (long) dDoubleValue, null, 32, null);
            } else if (Intrinsics.areEqual(str, this.ACTIVE_ENERGY_BURNED)) {
                Instant instantOfEpochMilli6 = Instant.ofEpochMilli(jLongValue);
                Instant instantOfEpochMilli7 = Instant.ofEpochMilli(jLongValue2);
                Energy energyKilocalories = Energy.INSTANCE.kilocalories(dDoubleValue);
                Intrinsics.checkNotNull(instantOfEpochMilli6);
                Intrinsics.checkNotNull(instantOfEpochMilli7);
                respiratoryRateRecord = new ActiveCaloriesBurnedRecord(instantOfEpochMilli6, null, instantOfEpochMilli7, null, energyKilocalories, null, 32, null);
            } else if (Intrinsics.areEqual(str, this.HEART_RATE)) {
                Instant instantOfEpochMilli8 = Instant.ofEpochMilli(jLongValue);
                Instant instantOfEpochMilli9 = Instant.ofEpochMilli(jLongValue2);
                Instant instantOfEpochMilli10 = Instant.ofEpochMilli(jLongValue);
                Intrinsics.checkNotNullExpressionValue(instantOfEpochMilli10, "ofEpochMilli(...)");
                List listListOf = CollectionsKt.listOf(new HeartRateRecord.Sample(instantOfEpochMilli10, (long) dDoubleValue));
                Intrinsics.checkNotNull(instantOfEpochMilli8);
                Intrinsics.checkNotNull(instantOfEpochMilli9);
                respiratoryRateRecord = new HeartRateRecord(instantOfEpochMilli8, null, instantOfEpochMilli9, null, listListOf, null, 32, null);
            } else if (Intrinsics.areEqual(str, this.BODY_TEMPERATURE)) {
                Instant instantOfEpochMilli11 = Instant.ofEpochMilli(jLongValue);
                Temperature temperatureCelsius = Temperature.INSTANCE.celsius(dDoubleValue);
                Intrinsics.checkNotNull(instantOfEpochMilli11);
                respiratoryRateRecord = new BodyTemperatureRecord(instantOfEpochMilli11, null, temperatureCelsius, 0, null, 24, null);
            } else if (Intrinsics.areEqual(str, this.BLOOD_OXYGEN)) {
                Instant instantOfEpochMilli12 = Instant.ofEpochMilli(jLongValue);
                Percentage percentage2 = new Percentage(dDoubleValue);
                Intrinsics.checkNotNull(instantOfEpochMilli12);
                respiratoryRateRecord = new OxygenSaturationRecord(instantOfEpochMilli12, null, percentage2, null, 8, null);
            } else if (Intrinsics.areEqual(str, this.BLOOD_GLUCOSE)) {
                Instant instantOfEpochMilli13 = Instant.ofEpochMilli(jLongValue);
                BloodGlucose bloodGlucoseMilligramsPerDeciliter = BloodGlucose.INSTANCE.milligramsPerDeciliter(dDoubleValue);
                Intrinsics.checkNotNull(instantOfEpochMilli13);
                respiratoryRateRecord = new BloodGlucoseRecord(instantOfEpochMilli13, null, bloodGlucoseMilligramsPerDeciliter, 0, 0, 0, null, 120, null);
            } else if (Intrinsics.areEqual(str, this.DISTANCE_DELTA)) {
                Instant instantOfEpochMilli14 = Instant.ofEpochMilli(jLongValue);
                Instant instantOfEpochMilli15 = Instant.ofEpochMilli(jLongValue2);
                Length lengthMeters2 = Length.INSTANCE.meters(dDoubleValue);
                Intrinsics.checkNotNull(instantOfEpochMilli14);
                Intrinsics.checkNotNull(instantOfEpochMilli15);
                respiratoryRateRecord = new DistanceRecord(instantOfEpochMilli14, null, instantOfEpochMilli15, null, lengthMeters2, null, 32, null);
            } else if (Intrinsics.areEqual(str, this.WATER)) {
                Instant instantOfEpochMilli16 = Instant.ofEpochMilli(jLongValue);
                Instant instantOfEpochMilli17 = Instant.ofEpochMilli(jLongValue2);
                Volume volumeLiters = Volume.INSTANCE.liters(dDoubleValue);
                Intrinsics.checkNotNull(instantOfEpochMilli16);
                Intrinsics.checkNotNull(instantOfEpochMilli17);
                respiratoryRateRecord = new HydrationRecord(instantOfEpochMilli16, null, instantOfEpochMilli17, null, volumeLiters, null, 32, null);
            } else if (Intrinsics.areEqual(str, this.SLEEP_ASLEEP)) {
                Instant instantOfEpochMilli18 = Instant.ofEpochMilli(jLongValue);
                Instant instantOfEpochMilli19 = Instant.ofEpochMilli(jLongValue2);
                Intrinsics.checkNotNull(instantOfEpochMilli18);
                Intrinsics.checkNotNull(instantOfEpochMilli19);
                respiratoryRateRecord = new SleepStageRecord(instantOfEpochMilli18, null, instantOfEpochMilli19, null, 2, null, 32, null);
            } else if (Intrinsics.areEqual(str, this.SLEEP_LIGHT)) {
                Instant instantOfEpochMilli20 = Instant.ofEpochMilli(jLongValue);
                Instant instantOfEpochMilli21 = Instant.ofEpochMilli(jLongValue2);
                Intrinsics.checkNotNull(instantOfEpochMilli20);
                Intrinsics.checkNotNull(instantOfEpochMilli21);
                respiratoryRateRecord = new SleepStageRecord(instantOfEpochMilli20, null, instantOfEpochMilli21, null, 4, null, 32, null);
            } else if (Intrinsics.areEqual(str, this.SLEEP_DEEP)) {
                Instant instantOfEpochMilli22 = Instant.ofEpochMilli(jLongValue);
                Instant instantOfEpochMilli23 = Instant.ofEpochMilli(jLongValue2);
                Intrinsics.checkNotNull(instantOfEpochMilli22);
                Intrinsics.checkNotNull(instantOfEpochMilli23);
                respiratoryRateRecord = new SleepStageRecord(instantOfEpochMilli22, null, instantOfEpochMilli23, null, 5, null, 32, null);
            } else if (Intrinsics.areEqual(str, this.SLEEP_REM)) {
                Instant instantOfEpochMilli24 = Instant.ofEpochMilli(jLongValue);
                Instant instantOfEpochMilli25 = Instant.ofEpochMilli(jLongValue2);
                Intrinsics.checkNotNull(instantOfEpochMilli24);
                Intrinsics.checkNotNull(instantOfEpochMilli25);
                respiratoryRateRecord = new SleepStageRecord(instantOfEpochMilli24, null, instantOfEpochMilli25, null, 6, null, 32, null);
            } else if (Intrinsics.areEqual(str, this.SLEEP_OUT_OF_BED)) {
                Instant instantOfEpochMilli26 = Instant.ofEpochMilli(jLongValue);
                Instant instantOfEpochMilli27 = Instant.ofEpochMilli(jLongValue2);
                Intrinsics.checkNotNull(instantOfEpochMilli26);
                Intrinsics.checkNotNull(instantOfEpochMilli27);
                respiratoryRateRecord = new SleepStageRecord(instantOfEpochMilli26, null, instantOfEpochMilli27, null, 3, null, 32, null);
            } else if (Intrinsics.areEqual(str, this.SLEEP_AWAKE)) {
                Instant instantOfEpochMilli28 = Instant.ofEpochMilli(jLongValue);
                Instant instantOfEpochMilli29 = Instant.ofEpochMilli(jLongValue2);
                Intrinsics.checkNotNull(instantOfEpochMilli28);
                Intrinsics.checkNotNull(instantOfEpochMilli29);
                respiratoryRateRecord = new SleepStageRecord(instantOfEpochMilli28, null, instantOfEpochMilli29, null, 1, null, 32, null);
            } else if (Intrinsics.areEqual(str, this.SLEEP_SESSION)) {
                Instant instantOfEpochMilli30 = Instant.ofEpochMilli(jLongValue);
                Instant instantOfEpochMilli31 = Instant.ofEpochMilli(jLongValue2);
                Intrinsics.checkNotNull(instantOfEpochMilli30);
                Intrinsics.checkNotNull(instantOfEpochMilli31);
                respiratoryRateRecord = new SleepSessionRecord(instantOfEpochMilli30, null, instantOfEpochMilli31, null, null, null, null, null, 240, null);
            } else if (Intrinsics.areEqual(str, this.RESTING_HEART_RATE)) {
                Instant instantOfEpochMilli32 = Instant.ofEpochMilli(jLongValue);
                Intrinsics.checkNotNull(instantOfEpochMilli32);
                restingHeartRateRecord = new RestingHeartRateRecord(instantOfEpochMilli32, null, (long) dDoubleValue, null, 8, null);
            } else if (Intrinsics.areEqual(str, this.BASAL_ENERGY_BURNED)) {
                Instant instantOfEpochMilli33 = Instant.ofEpochMilli(jLongValue);
                Power powerKilocaloriesPerDay = Power.INSTANCE.kilocaloriesPerDay(dDoubleValue);
                Intrinsics.checkNotNull(instantOfEpochMilli33);
                respiratoryRateRecord = new BasalMetabolicRateRecord(instantOfEpochMilli33, null, powerKilocaloriesPerDay, null, 8, null);
            } else if (Intrinsics.areEqual(str, this.FLIGHTS_CLIMBED)) {
                Instant instantOfEpochMilli34 = Instant.ofEpochMilli(jLongValue);
                Instant instantOfEpochMilli35 = Instant.ofEpochMilli(jLongValue2);
                Intrinsics.checkNotNull(instantOfEpochMilli34);
                Intrinsics.checkNotNull(instantOfEpochMilli35);
                respiratoryRateRecord = new FloorsClimbedRecord(instantOfEpochMilli34, null, instantOfEpochMilli35, null, dDoubleValue, null, 32, null);
            } else {
                if (!Intrinsics.areEqual(str, this.RESPIRATORY_RATE)) {
                    if (Intrinsics.areEqual(str, this.BLOOD_PRESSURE_SYSTOLIC)) {
                        throw new IllegalArgumentException("You must use the [writeBloodPressure] API ");
                    }
                    if (Intrinsics.areEqual(str, this.BLOOD_PRESSURE_DIASTOLIC)) {
                        throw new IllegalArgumentException("You must use the [writeBloodPressure] API ");
                    }
                    if (Intrinsics.areEqual(str, this.WORKOUT)) {
                        throw new IllegalArgumentException("You must use the [writeWorkoutData] API ");
                    }
                    throw new IllegalArgumentException("The type " + str + " was not supported by the Health plugin or you must use another API ");
                }
                Instant instantOfEpochMilli36 = Instant.ofEpochMilli(jLongValue);
                Intrinsics.checkNotNull(instantOfEpochMilli36);
                respiratoryRateRecord = new RespiratoryRateRecord(instantOfEpochMilli36, null, dDoubleValue, null, 8, null);
            }
            respiratoryRateRecord = restingHeartRateRecord;
        }
        Log.i("FLUTTER_HEALTH", "writeHCData: " + respiratoryRateRecord);
        CoroutineScope coroutineScope2 = this.scope;
        if (coroutineScope2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scope");
            coroutineScope = null;
        } else {
            coroutineScope = coroutineScope2;
        }
        BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new C03851(respiratoryRateRecord, result, null), 3, null);
    }

    public final void writeWorkoutHCData(@NotNull MethodCall call, @NotNull MethodChannel.Result result) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        Object objArgument = call.argument("activityType");
        Intrinsics.checkNotNull(objArgument);
        String str = (String) objArgument;
        Object objArgument2 = call.argument("startTime");
        Intrinsics.checkNotNull(objArgument2);
        Instant instantOfEpochMilli = Instant.ofEpochMilli(((Number) objArgument2).longValue());
        Object objArgument3 = call.argument(AUserTrack.UTKEY_END_TIME);
        Intrinsics.checkNotNull(objArgument3);
        Instant instantOfEpochMilli2 = Instant.ofEpochMilli(((Number) objArgument3).longValue());
        Integer num = (Integer) call.argument("totalEnergyBurned");
        Integer num2 = (Integer) call.argument("totalDistance");
        Integer num3 = this.workoutTypeMapHealthConnect.get(str);
        Intrinsics.checkNotNull(num3);
        int iIntValue = num3.intValue();
        CoroutineScope coroutineScope = this.scope;
        if (coroutineScope == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scope");
            coroutineScope = null;
        }
        BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new C03871(instantOfEpochMilli, instantOfEpochMilli2, iIntValue, str, num2, num, this, result, null), 3, null);
    }

    public HealthPlugin(@Nullable MethodChannel methodChannel) {
        this.channel = methodChannel;
        this.BODY_FAT_PERCENTAGE = "BODY_FAT_PERCENTAGE";
        this.HEIGHT = SelectPicsActivity.HEIGHT;
        this.WEIGHT = "WEIGHT";
        this.STEPS = "STEPS";
        this.AGGREGATE_STEP_COUNT = "AGGREGATE_STEP_COUNT";
        this.ACTIVE_ENERGY_BURNED = "ACTIVE_ENERGY_BURNED";
        this.HEART_RATE = "HEART_RATE";
        this.BODY_TEMPERATURE = "BODY_TEMPERATURE";
        this.BLOOD_PRESSURE_SYSTOLIC = "BLOOD_PRESSURE_SYSTOLIC";
        this.BLOOD_PRESSURE_DIASTOLIC = "BLOOD_PRESSURE_DIASTOLIC";
        this.BLOOD_OXYGEN = "BLOOD_OXYGEN";
        this.BLOOD_GLUCOSE = "BLOOD_GLUCOSE";
        this.MOVE_MINUTES = "MOVE_MINUTES";
        this.DISTANCE_DELTA = "DISTANCE_DELTA";
        this.WATER = "WATER";
        this.RESTING_HEART_RATE = "RESTING_HEART_RATE";
        this.BASAL_ENERGY_BURNED = "BASAL_ENERGY_BURNED";
        this.FLIGHTS_CLIMBED = "FLIGHTS_CLIMBED";
        this.RESPIRATORY_RATE = "RESPIRATORY_RATE";
        this.SLEEP_ASLEEP = "SLEEP_ASLEEP";
        this.SLEEP_AWAKE = "SLEEP_AWAKE";
        this.SLEEP_IN_BED = "SLEEP_IN_BED";
        this.SLEEP_SESSION = "SLEEP_SESSION";
        this.SLEEP_LIGHT = "SLEEP_LIGHT";
        this.SLEEP_DEEP = "SLEEP_DEEP";
        this.SLEEP_REM = "SLEEP_REM";
        this.SLEEP_OUT_OF_BED = "SLEEP_OUT_OF_BED";
        this.WORKOUT = "WORKOUT";
        this.workoutTypeMap = MapsKt.mapOf(TuplesKt.to("AEROBICS", FitnessActivities.AEROBICS), TuplesKt.to("AMERICAN_FOOTBALL", FitnessActivities.FOOTBALL_AMERICAN), TuplesKt.to("ARCHERY", FitnessActivities.ARCHERY), TuplesKt.to("AUSTRALIAN_FOOTBALL", FitnessActivities.FOOTBALL_AUSTRALIAN), TuplesKt.to("BADMINTON", FitnessActivities.BADMINTON), TuplesKt.to("BASEBALL", FitnessActivities.BASEBALL), TuplesKt.to("BASKETBALL", FitnessActivities.BASKETBALL), TuplesKt.to("BIATHLON", FitnessActivities.BIATHLON), TuplesKt.to("BIKING", FitnessActivities.BIKING), TuplesKt.to("BIKING_HAND", FitnessActivities.BIKING_HAND), TuplesKt.to("BIKING_MOUNTAIN", FitnessActivities.BIKING_MOUNTAIN), TuplesKt.to("BIKING_ROAD", FitnessActivities.BIKING_ROAD), TuplesKt.to("BIKING_SPINNING", FitnessActivities.BIKING_SPINNING), TuplesKt.to("BIKING_STATIONARY", FitnessActivities.BIKING_STATIONARY), TuplesKt.to("BIKING_UTILITY", FitnessActivities.BIKING_UTILITY), TuplesKt.to("BOXING", FitnessActivities.BOXING), TuplesKt.to("CALISTHENICS", FitnessActivities.CALISTHENICS), TuplesKt.to("CIRCUIT_TRAINING", FitnessActivities.CIRCUIT_TRAINING), TuplesKt.to("CRICKET", FitnessActivities.CRICKET), TuplesKt.to("CROSS_COUNTRY_SKIING", FitnessActivities.SKIING_CROSS_COUNTRY), TuplesKt.to("CROSS_FIT", FitnessActivities.CROSSFIT), TuplesKt.to("CURLING", FitnessActivities.CURLING), TuplesKt.to("DANCING", FitnessActivities.DANCING), TuplesKt.to("DIVING", FitnessActivities.DIVING), TuplesKt.to("DOWNHILL_SKIING", FitnessActivities.SKIING_DOWNHILL), TuplesKt.to("ELEVATOR", FitnessActivities.ELEVATOR), TuplesKt.to("ELLIPTICAL", FitnessActivities.ELLIPTICAL), TuplesKt.to("ERGOMETER", FitnessActivities.ERGOMETER), TuplesKt.to("ESCALATOR", FitnessActivities.ESCALATOR), TuplesKt.to("FENCING", FitnessActivities.FENCING), TuplesKt.to("FRISBEE_DISC", FitnessActivities.FRISBEE_DISC), TuplesKt.to("GARDENING", FitnessActivities.GARDENING), TuplesKt.to("GOLF", FitnessActivities.GOLF), TuplesKt.to("GUIDED_BREATHING", FitnessActivities.GUIDED_BREATHING), TuplesKt.to("GYMNASTICS", FitnessActivities.GYMNASTICS), TuplesKt.to("HANDBALL", FitnessActivities.HANDBALL), TuplesKt.to("HIGH_INTENSITY_INTERVAL_TRAINING", FitnessActivities.HIGH_INTENSITY_INTERVAL_TRAINING), TuplesKt.to("HIKING", FitnessActivities.HIKING), TuplesKt.to("HOCKEY", FitnessActivities.HOCKEY), TuplesKt.to("HORSEBACK_RIDING", FitnessActivities.HORSEBACK_RIDING), TuplesKt.to("HOUSEWORK", FitnessActivities.HOUSEWORK), TuplesKt.to("IN_VEHICLE", FitnessActivities.IN_VEHICLE), TuplesKt.to("ICE_SKATING", FitnessActivities.ICE_SKATING), TuplesKt.to("INTERVAL_TRAINING", FitnessActivities.INTERVAL_TRAINING), TuplesKt.to("JUMP_ROPE", FitnessActivities.JUMP_ROPE), TuplesKt.to("KAYAKING", FitnessActivities.KAYAKING), TuplesKt.to("KETTLEBELL_TRAINING", FitnessActivities.KETTLEBELL_TRAINING), TuplesKt.to("KICK_SCOOTER", FitnessActivities.KICK_SCOOTER), TuplesKt.to("KICKBOXING", FitnessActivities.KICKBOXING), TuplesKt.to("KITE_SURFING", FitnessActivities.KITESURFING), TuplesKt.to("MARTIAL_ARTS", FitnessActivities.MARTIAL_ARTS), TuplesKt.to("MEDITATION", FitnessActivities.MEDITATION), TuplesKt.to("MIXED_MARTIAL_ARTS", FitnessActivities.MIXED_MARTIAL_ARTS), TuplesKt.to("P90X", FitnessActivities.P90X), TuplesKt.to("PARAGLIDING", FitnessActivities.PARAGLIDING), TuplesKt.to("PILATES", FitnessActivities.PILATES), TuplesKt.to("POLO", FitnessActivities.POLO), TuplesKt.to("RACQUETBALL", FitnessActivities.RACQUETBALL), TuplesKt.to("ROCK_CLIMBING", FitnessActivities.ROCK_CLIMBING), TuplesKt.to("ROWING", FitnessActivities.ROWING), TuplesKt.to("ROWING_MACHINE", FitnessActivities.ROWING_MACHINE), TuplesKt.to("RUGBY", FitnessActivities.RUGBY), TuplesKt.to("RUNNING_JOGGING", FitnessActivities.RUNNING_JOGGING), TuplesKt.to("RUNNING_SAND", FitnessActivities.RUNNING_SAND), TuplesKt.to("RUNNING_TREADMILL", FitnessActivities.RUNNING_TREADMILL), TuplesKt.to(DebugCoroutineInfoImplKt.RUNNING, FitnessActivities.RUNNING), TuplesKt.to("SAILING", FitnessActivities.SAILING), TuplesKt.to("SCUBA_DIVING", FitnessActivities.SCUBA_DIVING), TuplesKt.to("SKATING_CROSS", FitnessActivities.SKATING_CROSS), TuplesKt.to("SKATING_INDOOR", FitnessActivities.SKATING_INDOOR), TuplesKt.to("SKATING_INLINE", FitnessActivities.SKATING_INLINE), TuplesKt.to("SKATING", FitnessActivities.SKATING), TuplesKt.to("SKIING", FitnessActivities.SKIING), TuplesKt.to("SKIING_BACK_COUNTRY", FitnessActivities.SKIING_BACK_COUNTRY), TuplesKt.to("SKIING_KITE", FitnessActivities.SKIING_KITE), TuplesKt.to("SKIING_ROLLER", FitnessActivities.SKIING_ROLLER), TuplesKt.to("SLEDDING", FitnessActivities.SLEDDING), TuplesKt.to("SNOWBOARDING", FitnessActivities.SNOWBOARDING), TuplesKt.to("SNOWMOBILE", FitnessActivities.SNOWMOBILE), TuplesKt.to("SNOWSHOEING", FitnessActivities.SNOWSHOEING), TuplesKt.to("SOCCER", FitnessActivities.FOOTBALL_SOCCER), TuplesKt.to("SOFTBALL", FitnessActivities.SOFTBALL), TuplesKt.to("SQUASH", FitnessActivities.SQUASH), TuplesKt.to("STAIR_CLIMBING_MACHINE", FitnessActivities.STAIR_CLIMBING_MACHINE), TuplesKt.to("STAIR_CLIMBING", FitnessActivities.STAIR_CLIMBING), TuplesKt.to("STANDUP_PADDLEBOARDING", FitnessActivities.STANDUP_PADDLEBOARDING), TuplesKt.to("STILL", FitnessActivities.STILL), TuplesKt.to("STRENGTH_TRAINING", FitnessActivities.STRENGTH_TRAINING), TuplesKt.to("SURFING", FitnessActivities.SURFING), TuplesKt.to("SWIMMING_OPEN_WATER", FitnessActivities.SWIMMING_OPEN_WATER), TuplesKt.to("SWIMMING_POOL", FitnessActivities.SWIMMING_POOL), TuplesKt.to("SWIMMING", FitnessActivities.SWIMMING), TuplesKt.to("TABLE_TENNIS", FitnessActivities.TABLE_TENNIS), TuplesKt.to("TEAM_SPORTS", FitnessActivities.TEAM_SPORTS), TuplesKt.to("TENNIS", FitnessActivities.TENNIS), TuplesKt.to("TILTING", FitnessActivities.TILTING), TuplesKt.to("VOLLEYBALL_BEACH", FitnessActivities.VOLLEYBALL_BEACH), TuplesKt.to("VOLLEYBALL_INDOOR", FitnessActivities.VOLLEYBALL_INDOOR), TuplesKt.to("VOLLEYBALL", FitnessActivities.VOLLEYBALL), TuplesKt.to("WAKEBOARDING", FitnessActivities.WAKEBOARDING), TuplesKt.to("WALKING_FITNESS", FitnessActivities.WALKING_FITNESS), TuplesKt.to("WALKING_PACED", FitnessActivities.WALKING_PACED), TuplesKt.to("WALKING_NORDIC", FitnessActivities.WALKING_NORDIC), TuplesKt.to("WALKING_STROLLER", FitnessActivities.WALKING_STROLLER), TuplesKt.to("WALKING_TREADMILL", FitnessActivities.WALKING_TREADMILL), TuplesKt.to("WALKING", FitnessActivities.WALKING), TuplesKt.to("WATER_POLO", FitnessActivities.WATER_POLO), TuplesKt.to("WEIGHTLIFTING", FitnessActivities.WEIGHTLIFTING), TuplesKt.to("WHEELCHAIR", FitnessActivities.WHEELCHAIR), TuplesKt.to("WINDSURFING", FitnessActivities.WINDSURFING), TuplesKt.to("YOGA", FitnessActivities.YOGA), TuplesKt.to("ZUMBA", FitnessActivities.ZUMBA), TuplesKt.to("OTHER", "other"));
        this.workoutTypeMapHealthConnect = MapsKt.mapOf(TuplesKt.to("AMERICAN_FOOTBALL", 28), TuplesKt.to("AUSTRALIAN_FOOTBALL", 29), TuplesKt.to("BADMINTON", 2), TuplesKt.to("BASEBALL", 4), TuplesKt.to("BASKETBALL", 5), TuplesKt.to("BIKING", 8), TuplesKt.to("BOXING", 11), TuplesKt.to("CALISTHENICS", 13), TuplesKt.to("CRICKET", 14), TuplesKt.to("DANCING", 16), TuplesKt.to("ELLIPTICAL", 25), TuplesKt.to("FENCING", 27), TuplesKt.to("FRISBEE_DISC", 31), TuplesKt.to("GOLF", 32), TuplesKt.to("GUIDED_BREATHING", 33), TuplesKt.to("GYMNASTICS", 34), TuplesKt.to("HANDBALL", 35), TuplesKt.to("HIGH_INTENSITY_INTERVAL_TRAINING", 36), TuplesKt.to("HIKING", 37), TuplesKt.to("ICE_SKATING", 39), TuplesKt.to("MARTIAL_ARTS", 44), TuplesKt.to("PARAGLIDING", 47), TuplesKt.to("PILATES", 48), TuplesKt.to("RACQUETBALL", 50), TuplesKt.to("ROCK_CLIMBING", 51), TuplesKt.to("ROWING", 53), TuplesKt.to("ROWING_MACHINE", 54), TuplesKt.to("RUGBY", 55), TuplesKt.to("RUNNING_TREADMILL", 57), TuplesKt.to(DebugCoroutineInfoImplKt.RUNNING, 56), TuplesKt.to("SAILING", 58), TuplesKt.to("SCUBA_DIVING", 59), TuplesKt.to("SKATING", 60), TuplesKt.to("SKIING", 61), TuplesKt.to("SNOWBOARDING", 62), TuplesKt.to("SNOWSHOEING", 63), TuplesKt.to("SOFTBALL", 65), TuplesKt.to("SQUASH", 66), TuplesKt.to("STAIR_CLIMBING_MACHINE", 69), TuplesKt.to("STAIR_CLIMBING", 68), TuplesKt.to("STRENGTH_TRAINING", 70), TuplesKt.to("SURFING", 72), TuplesKt.to("SWIMMING_OPEN_WATER", 73), TuplesKt.to("SWIMMING_POOL", 74), TuplesKt.to("TABLE_TENNIS", 75), TuplesKt.to("TENNIS", 76), TuplesKt.to("VOLLEYBALL", 78), TuplesKt.to("WALKING", 79), TuplesKt.to("WATER_POLO", 80), TuplesKt.to("WEIGHTLIFTING", 81), TuplesKt.to("WHEELCHAIR", 82), TuplesKt.to("YOGA", 83));
        this.healthConnectStatus = 1;
        this.MapSleepStageToType = MapsKt.hashMapOf(TuplesKt.to(1, this.SLEEP_AWAKE), TuplesKt.to(2, this.SLEEP_ASLEEP), TuplesKt.to(3, this.SLEEP_OUT_OF_BED), TuplesKt.to(4, this.SLEEP_LIGHT), TuplesKt.to(5, this.SLEEP_DEEP), TuplesKt.to(6, this.SLEEP_REM));
        this.MapToHCType = MapsKt.hashMapOf(TuplesKt.to(this.BODY_FAT_PERCENTAGE, Reflection.getOrCreateKotlinClass(BodyFatRecord.class)), TuplesKt.to(this.HEIGHT, Reflection.getOrCreateKotlinClass(HeightRecord.class)), TuplesKt.to(this.WEIGHT, Reflection.getOrCreateKotlinClass(WeightRecord.class)), TuplesKt.to(this.STEPS, Reflection.getOrCreateKotlinClass(StepsRecord.class)), TuplesKt.to(this.AGGREGATE_STEP_COUNT, Reflection.getOrCreateKotlinClass(StepsRecord.class)), TuplesKt.to(this.ACTIVE_ENERGY_BURNED, Reflection.getOrCreateKotlinClass(ActiveCaloriesBurnedRecord.class)), TuplesKt.to(this.HEART_RATE, Reflection.getOrCreateKotlinClass(HeartRateRecord.class)), TuplesKt.to(this.BODY_TEMPERATURE, Reflection.getOrCreateKotlinClass(BodyTemperatureRecord.class)), TuplesKt.to(this.BLOOD_PRESSURE_SYSTOLIC, Reflection.getOrCreateKotlinClass(BloodPressureRecord.class)), TuplesKt.to(this.BLOOD_PRESSURE_DIASTOLIC, Reflection.getOrCreateKotlinClass(BloodPressureRecord.class)), TuplesKt.to(this.BLOOD_OXYGEN, Reflection.getOrCreateKotlinClass(OxygenSaturationRecord.class)), TuplesKt.to(this.BLOOD_GLUCOSE, Reflection.getOrCreateKotlinClass(BloodGlucoseRecord.class)), TuplesKt.to(this.DISTANCE_DELTA, Reflection.getOrCreateKotlinClass(DistanceRecord.class)), TuplesKt.to(this.WATER, Reflection.getOrCreateKotlinClass(HydrationRecord.class)), TuplesKt.to(this.SLEEP_ASLEEP, Reflection.getOrCreateKotlinClass(SleepStageRecord.class)), TuplesKt.to(this.SLEEP_AWAKE, Reflection.getOrCreateKotlinClass(SleepStageRecord.class)), TuplesKt.to(this.SLEEP_LIGHT, Reflection.getOrCreateKotlinClass(SleepStageRecord.class)), TuplesKt.to(this.SLEEP_DEEP, Reflection.getOrCreateKotlinClass(SleepStageRecord.class)), TuplesKt.to(this.SLEEP_REM, Reflection.getOrCreateKotlinClass(SleepStageRecord.class)), TuplesKt.to(this.SLEEP_OUT_OF_BED, Reflection.getOrCreateKotlinClass(SleepStageRecord.class)), TuplesKt.to(this.SLEEP_SESSION, Reflection.getOrCreateKotlinClass(SleepSessionRecord.class)), TuplesKt.to(this.WORKOUT, Reflection.getOrCreateKotlinClass(ExerciseSessionRecord.class)), TuplesKt.to(this.RESTING_HEART_RATE, Reflection.getOrCreateKotlinClass(RestingHeartRateRecord.class)), TuplesKt.to(this.BASAL_ENERGY_BURNED, Reflection.getOrCreateKotlinClass(BasalMetabolicRateRecord.class)), TuplesKt.to(this.FLIGHTS_CLIMBED, Reflection.getOrCreateKotlinClass(FloorsClimbedRecord.class)), TuplesKt.to(this.RESPIRATORY_RATE, Reflection.getOrCreateKotlinClass(RespiratoryRateRecord.class)));
    }

    public /* synthetic */ HealthPlugin(MethodChannel methodChannel, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? null : methodChannel);
    }
}
