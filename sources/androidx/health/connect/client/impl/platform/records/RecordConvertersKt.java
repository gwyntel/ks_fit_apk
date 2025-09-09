package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.ActiveCaloriesBurnedRecord;
import android.health.connect.datatypes.BasalBodyTemperatureRecord;
import android.health.connect.datatypes.BasalMetabolicRateRecord;
import android.health.connect.datatypes.BloodGlucoseRecord;
import android.health.connect.datatypes.BloodPressureRecord;
import android.health.connect.datatypes.BodyFatRecord;
import android.health.connect.datatypes.BodyTemperatureRecord;
import android.health.connect.datatypes.BodyWaterMassRecord;
import android.health.connect.datatypes.BoneMassRecord;
import android.health.connect.datatypes.CervicalMucusRecord;
import android.health.connect.datatypes.CyclingPedalingCadenceRecord;
import android.health.connect.datatypes.DistanceRecord;
import android.health.connect.datatypes.ElevationGainedRecord;
import android.health.connect.datatypes.ExerciseLap;
import android.health.connect.datatypes.ExerciseRoute;
import android.health.connect.datatypes.ExerciseSegment;
import android.health.connect.datatypes.ExerciseSessionRecord;
import android.health.connect.datatypes.FloorsClimbedRecord;
import android.health.connect.datatypes.HeartRateRecord;
import android.health.connect.datatypes.HeartRateVariabilityRmssdRecord;
import android.health.connect.datatypes.HeightRecord;
import android.health.connect.datatypes.HydrationRecord;
import android.health.connect.datatypes.IntermenstrualBleedingRecord;
import android.health.connect.datatypes.LeanBodyMassRecord;
import android.health.connect.datatypes.MenstruationFlowRecord;
import android.health.connect.datatypes.MenstruationPeriodRecord;
import android.health.connect.datatypes.NutritionRecord;
import android.health.connect.datatypes.OvulationTestRecord;
import android.health.connect.datatypes.OxygenSaturationRecord;
import android.health.connect.datatypes.PowerRecord;
import android.health.connect.datatypes.Record;
import android.health.connect.datatypes.RespiratoryRateRecord;
import android.health.connect.datatypes.RestingHeartRateRecord;
import android.health.connect.datatypes.SexualActivityRecord;
import android.health.connect.datatypes.SleepSessionRecord;
import android.health.connect.datatypes.SpeedRecord;
import android.health.connect.datatypes.StepsCadenceRecord;
import android.health.connect.datatypes.StepsRecord;
import android.health.connect.datatypes.TotalCaloriesBurnedRecord;
import android.health.connect.datatypes.Vo2MaxRecord;
import android.health.connect.datatypes.WeightRecord;
import android.health.connect.datatypes.WheelchairPushesRecord;
import android.health.connect.datatypes.units.BloodGlucose;
import android.health.connect.datatypes.units.Percentage;
import android.health.connect.datatypes.units.Power;
import android.health.connect.datatypes.units.Pressure;
import android.health.connect.datatypes.units.Temperature;
import android.health.connect.datatypes.units.Velocity;
import android.health.connect.datatypes.units.Volume;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.health.connect.client.records.CyclingPedalingCadenceRecord;
import androidx.health.connect.client.records.ExerciseRoute;
import androidx.health.connect.client.records.ExerciseRouteResult;
import androidx.health.connect.client.records.HeartRateRecord;
import androidx.health.connect.client.records.PowerRecord;
import androidx.health.connect.client.records.RespiratoryRateRecord;
import androidx.health.connect.client.records.RestingHeartRateRecord;
import androidx.health.connect.client.records.SexualActivityRecord;
import androidx.health.connect.client.records.SleepSessionRecord;
import androidx.health.connect.client.records.SpeedRecord;
import androidx.health.connect.client.records.StepsCadenceRecord;
import androidx.health.connect.client.records.StepsRecord;
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord;
import androidx.health.connect.client.records.Vo2MaxRecord;
import androidx.health.connect.client.records.WeightRecord;
import androidx.health.connect.client.records.WheelchairPushesRecord;
import androidx.health.connect.client.units.Energy;
import androidx.health.connect.client.units.Length;
import androidx.health.connect.client.units.Mass;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u008a\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0002\u001a\f\u0010\u0003\u001a\u00020\u0004*\u00020\u0005H\u0002\u001a\f\u0010\u0006\u001a\u00020\u0007*\u00020\bH\u0002\u001a\f\u0010\t\u001a\u00020\n*\u00020\u000bH\u0002\u001a\f\u0010\f\u001a\u00020\r*\u00020\u000eH\u0002\u001a\f\u0010\u000f\u001a\u00020\u0010*\u00020\u0011H\u0002\u001a\f\u0010\u0012\u001a\u00020\u0013*\u00020\u0014H\u0002\u001a\f\u0010\u0015\u001a\u00020\u0016*\u00020\u0017H\u0002\u001a\f\u0010\u0018\u001a\u00020\u0019*\u00020\u001aH\u0002\u001a\f\u0010\u001b\u001a\u00020\u001c*\u00020\u001dH\u0002\u001a\f\u0010\u001e\u001a\u00020\u001f*\u00020 H\u0002\u001a\u0010\u0010!\u001a\u00060\"j\u0002`#*\u00020$H\u0002\u001a\f\u0010%\u001a\u00020&*\u00020'H\u0002\u001a\f\u0010(\u001a\u00020)*\u00020*H\u0002\u001a\f\u0010+\u001a\u00020,*\u00020-H\u0002\u001a\u0010\u0010.\u001a\u00060/j\u0002`0*\u000201H\u0002\u001a\f\u00102\u001a\u000203*\u000204H\u0002\u001a\f\u00105\u001a\u000206*\u000207H\u0002\u001a\f\u00108\u001a\u000209*\u00020:H\u0002\u001a\f\u0010;\u001a\u00020<*\u00020=H\u0002\u001a\u0010\u0010>\u001a\u00060?j\u0002`@*\u00020AH\u0002\u001a\f\u0010B\u001a\u00020C*\u00020DH\u0002\u001a\f\u0010E\u001a\u00020F*\u00020GH\u0002\u001a\f\u0010H\u001a\u00020I*\u00020JH\u0002\u001a\f\u0010K\u001a\u00020L*\u00020MH\u0002\u001a\f\u0010N\u001a\u00020O*\u00020PH\u0002\u001a\f\u0010Q\u001a\u00020R*\u00020SH\u0002\u001a\f\u0010T\u001a\u00020U*\u00020VH\u0002\u001a\f\u0010W\u001a\u00020X*\u00020YH\u0002\u001a\f\u0010Z\u001a\u00020[*\u00020\\H\u0002\u001a\f\u0010]\u001a\u00020^*\u00020_H\u0002\u001a\f\u0010`\u001a\u00020a*\u00020bH\u0002\u001a\u0010\u0010c\u001a\u00060dj\u0002`e*\u00020fH\u0002\u001a\u000e\u0010g\u001a\u00060hj\u0002`i*\u00020j\u001a\u001e\u0010k\u001a\u000e\u0012\n\b\u0001\u0012\u00060hj\u0002`i0l*\n\u0012\u0006\b\u0001\u0012\u00020j0m\u001a\f\u0010n\u001a\u00020o*\u00020pH\u0002\u001a\f\u0010q\u001a\u00020r*\u00020sH\u0002\u001a\f\u0010t\u001a\u00020u*\u00020vH\u0002\u001a\f\u0010w\u001a\u00020x*\u00020yH\u0002\u001a\u0010\u0010z\u001a\u00060{j\u0002`|*\u00020}H\u0002\u001a\r\u0010~\u001a\u00020\u007f*\u00030\u0080\u0001H\u0002\u001a\u0014\u0010\u0081\u0001\u001a\b0\u0082\u0001j\u0003`\u0083\u0001*\u00030\u0084\u0001H\u0002\u001a\u000f\u0010\u0085\u0001\u001a\u00030\u0086\u0001*\u00030\u0087\u0001H\u0002\u001a\u0014\u0010\u0088\u0001\u001a\b0\u0089\u0001j\u0003`\u008a\u0001*\u00030\u008b\u0001H\u0002\u001a\u000f\u0010\u008c\u0001\u001a\u00030\u008d\u0001*\u00030\u008e\u0001H\u0002\u001a\u000f\u0010\u008f\u0001\u001a\u00030\u0090\u0001*\u00030\u0091\u0001H\u0002\u001a\u000f\u0010\u0092\u0001\u001a\u00030\u0093\u0001*\u00030\u0094\u0001H\u0002\u001a\u000f\u0010\u0095\u0001\u001a\u00030\u0096\u0001*\u00030\u0097\u0001H\u0002\u001a\u000f\u0010\u0098\u0001\u001a\u00030\u0099\u0001*\u00030\u009a\u0001H\u0002\u001a\u0012\u0010\u009b\u0001\u001a\u00020\u0002*\u00070\u0001j\u0003`\u009c\u0001H\u0002\u001a\u0012\u0010\u009d\u0001\u001a\u00020\u0005*\u00070\u0004j\u0003`\u009e\u0001H\u0002\u001a\u0012\u0010\u009f\u0001\u001a\u00020\b*\u00070\u0007j\u0003` \u0001H\u0002\u001a\u0012\u0010¡\u0001\u001a\u00020\u000b*\u00070\nj\u0003`¢\u0001H\u0002\u001a\u0012\u0010£\u0001\u001a\u00020\u000e*\u00070\rj\u0003`¤\u0001H\u0002\u001a\u0012\u0010¥\u0001\u001a\u00020\u0011*\u00070\u0010j\u0003`¦\u0001H\u0002\u001a\u0012\u0010§\u0001\u001a\u00020\u0014*\u00070\u0013j\u0003`¨\u0001H\u0002\u001a\u0012\u0010©\u0001\u001a\u00020\u0017*\u00070\u0016j\u0003`ª\u0001H\u0002\u001a\u0012\u0010«\u0001\u001a\u00020\u001a*\u00070\u0019j\u0003`¬\u0001H\u0002\u001a\u0012\u0010\u00ad\u0001\u001a\u00020\u001d*\u00070\u001cj\u0003`®\u0001H\u0002\u001a\u0012\u0010¯\u0001\u001a\u00020 *\u00070\u001fj\u0003`°\u0001H\u0002\u001a\u0011\u0010±\u0001\u001a\u00020$*\u00060\"j\u0002`#H\u0002\u001a\u0012\u0010²\u0001\u001a\u00020'*\u00070&j\u0003`³\u0001H\u0002\u001a\u0012\u0010´\u0001\u001a\u00020**\u00070)j\u0003`µ\u0001H\u0002\u001a\u0012\u0010¶\u0001\u001a\u00020-*\u00070,j\u0003`·\u0001H\u0000\u001a\u0011\u0010¸\u0001\u001a\u000201*\u00060/j\u0002`0H\u0000\u001a\u0012\u0010¹\u0001\u001a\u000204*\u000703j\u0003`º\u0001H\u0000\u001a\u0012\u0010»\u0001\u001a\u000207*\u000706j\u0003`¼\u0001H\u0002\u001a\u0012\u0010½\u0001\u001a\u00020:*\u000709j\u0003`¾\u0001H\u0002\u001a\u0012\u0010¿\u0001\u001a\u00020=*\u00070<j\u0003`À\u0001H\u0002\u001a\u0011\u0010Á\u0001\u001a\u00020A*\u00060?j\u0002`@H\u0002\u001a\u0012\u0010Â\u0001\u001a\u00020D*\u00070Cj\u0003`Ã\u0001H\u0002\u001a\u0012\u0010Ä\u0001\u001a\u00020G*\u00070Fj\u0003`Å\u0001H\u0002\u001a\u0012\u0010Æ\u0001\u001a\u00020J*\u00070Ij\u0003`Ç\u0001H\u0002\u001a\u0012\u0010È\u0001\u001a\u00020M*\u00070Lj\u0003`É\u0001H\u0002\u001a\u0012\u0010Ê\u0001\u001a\u00020P*\u00070Oj\u0003`Ë\u0001H\u0002\u001a\u0012\u0010Ì\u0001\u001a\u00020S*\u00070Rj\u0003`Í\u0001H\u0002\u001a\u0012\u0010Î\u0001\u001a\u00020V*\u00070Uj\u0003`Ï\u0001H\u0002\u001a\u0012\u0010Ð\u0001\u001a\u00020Y*\u00070Xj\u0003`Ñ\u0001H\u0002\u001a\u0012\u0010Ò\u0001\u001a\u00020\\*\u00070[j\u0003`Ó\u0001H\u0002\u001a\u0012\u0010Ô\u0001\u001a\u00020_*\u00070^j\u0003`Õ\u0001H\u0002\u001a\u0012\u0010Ö\u0001\u001a\u00020b*\u00070aj\u0003`×\u0001H\u0002\u001a\u0011\u0010Ø\u0001\u001a\u00020f*\u00060dj\u0002`eH\u0002\u001a\u000f\u0010Ù\u0001\u001a\u00020j*\u00060hj\u0002`i\u001a\u0012\u0010Ú\u0001\u001a\u00020p*\u00070oj\u0003`Û\u0001H\u0002\u001a\u0012\u0010Ü\u0001\u001a\u00020s*\u00070rj\u0003`Ý\u0001H\u0002\u001a\u0012\u0010Þ\u0001\u001a\u00020v*\u00070uj\u0003`ß\u0001H\u0002\u001a\u0012\u0010à\u0001\u001a\u00020y*\u00070xj\u0003`á\u0001H\u0002\u001a\u0011\u0010â\u0001\u001a\u00020}*\u00060{j\u0002`|H\u0002\u001a\u0013\u0010ã\u0001\u001a\u00030\u0080\u0001*\u00070\u007fj\u0003`ä\u0001H\u0002\u001a\u0014\u0010å\u0001\u001a\u00030\u0084\u0001*\b0\u0082\u0001j\u0003`\u0083\u0001H\u0002\u001a\u0014\u0010æ\u0001\u001a\u00030\u0087\u0001*\b0\u0086\u0001j\u0003`ç\u0001H\u0002\u001a\u0014\u0010è\u0001\u001a\u00030\u008b\u0001*\b0\u0089\u0001j\u0003`\u008a\u0001H\u0002\u001a\u0014\u0010é\u0001\u001a\u00030\u008e\u0001*\b0\u008d\u0001j\u0003`ê\u0001H\u0002\u001a\u0014\u0010ë\u0001\u001a\u00030\u0091\u0001*\b0\u0090\u0001j\u0003`ì\u0001H\u0002\u001a\u0014\u0010í\u0001\u001a\u00030\u0094\u0001*\b0\u0093\u0001j\u0003`î\u0001H\u0002\u001a\u0014\u0010ï\u0001\u001a\u00030\u0097\u0001*\b0\u0096\u0001j\u0003`ð\u0001H\u0002\u001a\u0014\u0010ñ\u0001\u001a\u00030\u009a\u0001*\b0\u0099\u0001j\u0003`ò\u0001H\u0002¨\u0006ó\u0001"}, d2 = {"toPlatformActiveCaloriesBurnedRecord", "Landroid/health/connect/datatypes/ActiveCaloriesBurnedRecord;", "Landroidx/health/connect/client/records/ActiveCaloriesBurnedRecord;", "toPlatformBasalBodyTemperatureRecord", "Landroid/health/connect/datatypes/BasalBodyTemperatureRecord;", "Landroidx/health/connect/client/records/BasalBodyTemperatureRecord;", "toPlatformBasalMetabolicRateRecord", "Landroid/health/connect/datatypes/BasalMetabolicRateRecord;", "Landroidx/health/connect/client/records/BasalMetabolicRateRecord;", "toPlatformBloodGlucoseRecord", "Landroid/health/connect/datatypes/BloodGlucoseRecord;", "Landroidx/health/connect/client/records/BloodGlucoseRecord;", "toPlatformBloodPressureRecord", "Landroid/health/connect/datatypes/BloodPressureRecord;", "Landroidx/health/connect/client/records/BloodPressureRecord;", "toPlatformBodyFatRecord", "Landroid/health/connect/datatypes/BodyFatRecord;", "Landroidx/health/connect/client/records/BodyFatRecord;", "toPlatformBodyTemperatureRecord", "Landroid/health/connect/datatypes/BodyTemperatureRecord;", "Landroidx/health/connect/client/records/BodyTemperatureRecord;", "toPlatformBodyWaterMassRecord", "Landroid/health/connect/datatypes/BodyWaterMassRecord;", "Landroidx/health/connect/client/records/BodyWaterMassRecord;", "toPlatformBoneMassRecord", "Landroid/health/connect/datatypes/BoneMassRecord;", "Landroidx/health/connect/client/records/BoneMassRecord;", "toPlatformCervicalMucusRecord", "Landroid/health/connect/datatypes/CervicalMucusRecord;", "Landroidx/health/connect/client/records/CervicalMucusRecord;", "toPlatformCyclingPedalingCadenceRecord", "Landroid/health/connect/datatypes/CyclingPedalingCadenceRecord;", "Landroidx/health/connect/client/records/CyclingPedalingCadenceRecord;", "toPlatformCyclingPedalingCadenceSample", "Landroid/health/connect/datatypes/CyclingPedalingCadenceRecord$CyclingPedalingCadenceRecordSample;", "Landroidx/health/connect/client/impl/platform/records/PlatformCyclingPedalingCadenceSample;", "Landroidx/health/connect/client/records/CyclingPedalingCadenceRecord$Sample;", "toPlatformDistanceRecord", "Landroid/health/connect/datatypes/DistanceRecord;", "Landroidx/health/connect/client/records/DistanceRecord;", "toPlatformElevationGainedRecord", "Landroid/health/connect/datatypes/ElevationGainedRecord;", "Landroidx/health/connect/client/records/ElevationGainedRecord;", "toPlatformExerciseLap", "Landroid/health/connect/datatypes/ExerciseLap;", "Landroidx/health/connect/client/records/ExerciseLap;", "toPlatformExerciseRoute", "Landroid/health/connect/datatypes/ExerciseRoute;", "Landroidx/health/connect/client/impl/platform/records/PlatformExerciseRoute;", "Landroidx/health/connect/client/records/ExerciseRoute;", "toPlatformExerciseSegment", "Landroid/health/connect/datatypes/ExerciseSegment;", "Landroidx/health/connect/client/records/ExerciseSegment;", "toPlatformExerciseSessionRecord", "Landroid/health/connect/datatypes/ExerciseSessionRecord;", "Landroidx/health/connect/client/records/ExerciseSessionRecord;", "toPlatformFloorsClimbedRecord", "Landroid/health/connect/datatypes/FloorsClimbedRecord;", "Landroidx/health/connect/client/records/FloorsClimbedRecord;", "toPlatformHeartRateRecord", "Landroid/health/connect/datatypes/HeartRateRecord;", "Landroidx/health/connect/client/records/HeartRateRecord;", "toPlatformHeartRateSample", "Landroid/health/connect/datatypes/HeartRateRecord$HeartRateSample;", "Landroidx/health/connect/client/impl/platform/records/PlatformHeartRateSample;", "Landroidx/health/connect/client/records/HeartRateRecord$Sample;", "toPlatformHeartRateVariabilityRmssdRecord", "Landroid/health/connect/datatypes/HeartRateVariabilityRmssdRecord;", "Landroidx/health/connect/client/records/HeartRateVariabilityRmssdRecord;", "toPlatformHeightRecord", "Landroid/health/connect/datatypes/HeightRecord;", "Landroidx/health/connect/client/records/HeightRecord;", "toPlatformHydrationRecord", "Landroid/health/connect/datatypes/HydrationRecord;", "Landroidx/health/connect/client/records/HydrationRecord;", "toPlatformIntermenstrualBleedingRecord", "Landroid/health/connect/datatypes/IntermenstrualBleedingRecord;", "Landroidx/health/connect/client/records/IntermenstrualBleedingRecord;", "toPlatformLeanBodyMassRecord", "Landroid/health/connect/datatypes/LeanBodyMassRecord;", "Landroidx/health/connect/client/records/LeanBodyMassRecord;", "toPlatformMenstruationFlowRecord", "Landroid/health/connect/datatypes/MenstruationFlowRecord;", "Landroidx/health/connect/client/records/MenstruationFlowRecord;", "toPlatformMenstruationPeriodRecord", "Landroid/health/connect/datatypes/MenstruationPeriodRecord;", "Landroidx/health/connect/client/records/MenstruationPeriodRecord;", "toPlatformNutritionRecord", "Landroid/health/connect/datatypes/NutritionRecord;", "Landroidx/health/connect/client/records/NutritionRecord;", "toPlatformOvulationTestRecord", "Landroid/health/connect/datatypes/OvulationTestRecord;", "Landroidx/health/connect/client/records/OvulationTestRecord;", "toPlatformOxygenSaturationRecord", "Landroid/health/connect/datatypes/OxygenSaturationRecord;", "Landroidx/health/connect/client/records/OxygenSaturationRecord;", "toPlatformPowerRecord", "Landroid/health/connect/datatypes/PowerRecord;", "Landroidx/health/connect/client/records/PowerRecord;", "toPlatformPowerRecordSample", "Landroid/health/connect/datatypes/PowerRecord$PowerRecordSample;", "Landroidx/health/connect/client/impl/platform/records/PlatformPowerRecordSample;", "Landroidx/health/connect/client/records/PowerRecord$Sample;", "toPlatformRecord", "Landroid/health/connect/datatypes/Record;", "Landroidx/health/connect/client/impl/platform/records/PlatformRecord;", "Landroidx/health/connect/client/records/Record;", "toPlatformRecordClass", "Ljava/lang/Class;", "Lkotlin/reflect/KClass;", "toPlatformRespiratoryRateRecord", "Landroid/health/connect/datatypes/RespiratoryRateRecord;", "Landroidx/health/connect/client/records/RespiratoryRateRecord;", "toPlatformRestingHeartRateRecord", "Landroid/health/connect/datatypes/RestingHeartRateRecord;", "Landroidx/health/connect/client/records/RestingHeartRateRecord;", "toPlatformSexualActivityRecord", "Landroid/health/connect/datatypes/SexualActivityRecord;", "Landroidx/health/connect/client/records/SexualActivityRecord;", "toPlatformSleepSessionRecord", "Landroid/health/connect/datatypes/SleepSessionRecord;", "Landroidx/health/connect/client/records/SleepSessionRecord;", "toPlatformSleepSessionStage", "Landroid/health/connect/datatypes/SleepSessionRecord$Stage;", "Landroidx/health/connect/client/impl/platform/records/PlatformSleepSessionStage;", "Landroidx/health/connect/client/records/SleepSessionRecord$Stage;", "toPlatformSpeedRecord", "Landroid/health/connect/datatypes/SpeedRecord;", "Landroidx/health/connect/client/records/SpeedRecord;", "toPlatformSpeedRecordSample", "Landroid/health/connect/datatypes/SpeedRecord$SpeedRecordSample;", "Landroidx/health/connect/client/impl/platform/records/PlatformSpeedSample;", "Landroidx/health/connect/client/records/SpeedRecord$Sample;", "toPlatformStepsCadenceRecord", "Landroid/health/connect/datatypes/StepsCadenceRecord;", "Landroidx/health/connect/client/records/StepsCadenceRecord;", "toPlatformStepsCadenceSample", "Landroid/health/connect/datatypes/StepsCadenceRecord$StepsCadenceRecordSample;", "Landroidx/health/connect/client/impl/platform/records/PlatformStepsCadenceSample;", "Landroidx/health/connect/client/records/StepsCadenceRecord$Sample;", "toPlatformStepsRecord", "Landroid/health/connect/datatypes/StepsRecord;", "Landroidx/health/connect/client/records/StepsRecord;", "toPlatformTotalCaloriesBurnedRecord", "Landroid/health/connect/datatypes/TotalCaloriesBurnedRecord;", "Landroidx/health/connect/client/records/TotalCaloriesBurnedRecord;", "toPlatformVo2MaxRecord", "Landroid/health/connect/datatypes/Vo2MaxRecord;", "Landroidx/health/connect/client/records/Vo2MaxRecord;", "toPlatformWeightRecord", "Landroid/health/connect/datatypes/WeightRecord;", "Landroidx/health/connect/client/records/WeightRecord;", "toPlatformWheelchairPushesRecord", "Landroid/health/connect/datatypes/WheelchairPushesRecord;", "Landroidx/health/connect/client/records/WheelchairPushesRecord;", "toSdkActiveCaloriesBurnedRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformActiveCaloriesBurnedRecord;", "toSdkBasalBodyTemperatureRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformBasalBodyTemperatureRecord;", "toSdkBasalMetabolicRateRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformBasalMetabolicRateRecord;", "toSdkBloodGlucoseRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformBloodGlucoseRecord;", "toSdkBloodPressureRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformBloodPressureRecord;", "toSdkBodyFatRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformBodyFatRecord;", "toSdkBodyTemperatureRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformBodyTemperatureRecord;", "toSdkBodyWaterMassRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformBodyWaterMassRecord;", "toSdkBoneMassRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformBoneMassRecord;", "toSdkCervicalMucusRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformCervicalMucusRecord;", "toSdkCyclingPedalingCadenceRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformCyclingPedalingCadenceRecord;", "toSdkCyclingPedalingCadenceSample", "toSdkDistanceRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformDistanceRecord;", "toSdkElevationGainedRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformElevationGainedRecord;", "toSdkExerciseLap", "Landroidx/health/connect/client/impl/platform/records/PlatformExerciseLap;", "toSdkExerciseRoute", "toSdkExerciseSegment", "Landroidx/health/connect/client/impl/platform/records/PlatformExerciseSegment;", "toSdkExerciseSessionRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformExerciseSessionRecord;", "toSdkFloorsClimbedRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformFloorsClimbedRecord;", "toSdkHeartRateRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformHeartRateRecord;", "toSdkHeartRateSample", "toSdkHeartRateVariabilityRmssdRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformHeartRateVariabilityRmssdRecord;", "toSdkHeightRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformHeightRecord;", "toSdkHydrationRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformHydrationRecord;", "toSdkIntermenstrualBleedingRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformIntermenstrualBleedingRecord;", "toSdkLeanBodyMassRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformLeanBodyMassRecord;", "toSdkMenstruationFlowRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformMenstruationFlowRecord;", "toSdkMenstruationPeriodRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformMenstruationPeriodRecord;", "toSdkNutritionRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformNutritionRecord;", "toSdkOvulationTestRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformOvulationTestRecord;", "toSdkOxygenSaturationRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformOxygenSaturationRecord;", "toSdkPowerRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformPowerRecord;", "toSdkPowerRecordSample", "toSdkRecord", "toSdkRespiratoryRateRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformRespiratoryRateRecord;", "toSdkRestingHeartRateRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformRestingHeartRateRecord;", "toSdkSexualActivityRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformSexualActivityRecord;", "toSdkSleepSessionRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformSleepSessionRecord;", "toSdkSleepSessionStage", "toSdkSpeedRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformSpeedRecord;", "toSdkSpeedSample", "toSdkStepsCadenceRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformStepsCadenceRecord;", "toSdkStepsCadenceSample", "toSdkStepsRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformStepsRecord;", "toSdkTotalCaloriesBurnedRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformTotalCaloriesBurnedRecord;", "toSdkVo2MaxRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformVo2MaxRecord;", "toSdkWeightRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformWeightRecord;", "toWheelchairPushesRecord", "Landroidx/health/connect/client/impl/platform/records/PlatformWheelchairPushesRecord;", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@RequiresApi(api = 34)
@RestrictTo({RestrictTo.Scope.LIBRARY})
@SourceDebugExtension({"SMAP\nRecordConverters.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RecordConverters.kt\nandroidx/health/connect/client/impl/platform/records/RecordConvertersKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,1058:1\n1549#2:1059\n1620#2,3:1060\n1045#2:1063\n1549#2:1064\n1620#2,3:1065\n1045#2:1068\n1549#2:1069\n1620#2,3:1070\n1045#2:1073\n1549#2:1075\n1620#2,3:1076\n1045#2:1079\n1549#2:1080\n1620#2,3:1081\n1045#2:1084\n1549#2:1085\n1620#2,3:1086\n1045#2:1089\n1549#2:1090\n1620#2,3:1091\n1045#2:1094\n1549#2:1095\n1620#2,3:1096\n1045#2:1099\n1549#2:1100\n1620#2,3:1101\n1549#2:1104\n1620#2,3:1105\n1549#2:1108\n1620#2,3:1109\n1549#2:1112\n1620#2,3:1113\n1549#2:1116\n1620#2,3:1117\n1549#2:1120\n1620#2,3:1121\n1549#2:1124\n1620#2,3:1125\n1549#2:1128\n1620#2,3:1129\n1549#2:1132\n1620#2,3:1133\n1549#2:1136\n1620#2,3:1137\n1#3:1074\n*S KotlinDebug\n*F\n+ 1 RecordConverters.kt\nandroidx/health/connect/client/impl/platform/records/RecordConvertersKt\n*L\n261#1:1059\n261#1:1060,3\n261#1:1063\n294#1:1064\n294#1:1065,3\n294#1:1068\n295#1:1069\n295#1:1070,3\n295#1:1073\n319#1:1075\n319#1:1076,3\n319#1:1079\n456#1:1080\n456#1:1081,3\n456#1:1084\n493#1:1085\n493#1:1086,3\n493#1:1089\n502#1:1090\n502#1:1091,3\n502#1:1094\n512#1:1095\n512#1:1096,3\n512#1:1099\n663#1:1100\n663#1:1101,3\n712#1:1104\n712#1:1105,3\n713#1:1108\n713#1:1109,3\n727#1:1112\n727#1:1113,3\n762#1:1116\n762#1:1117,3\n902#1:1120\n902#1:1121,3\n939#1:1124\n939#1:1125,3\n951#1:1128\n951#1:1129,3\n975#1:1132\n975#1:1133,3\n1041#1:1136\n1041#1:1137,3\n*E\n"})
/* loaded from: classes.dex */
public final class RecordConvertersKt {
    private static final ActiveCaloriesBurnedRecord toPlatformActiveCaloriesBurnedRecord(androidx.health.connect.client.records.ActiveCaloriesBurnedRecord activeCaloriesBurnedRecord) {
        bm.a();
        ActiveCaloriesBurnedRecord.Builder builderA = am.a(MetadataConvertersKt.toPlatformMetadata(activeCaloriesBurnedRecord.getMetadata()), activeCaloriesBurnedRecord.getStartTime(), activeCaloriesBurnedRecord.getEndTime(), UnitConvertersKt.toPlatformEnergy(activeCaloriesBurnedRecord.getEnergy()));
        ZoneOffset startZoneOffset = activeCaloriesBurnedRecord.getStartZoneOffset();
        if (startZoneOffset != null) {
            builderA.setStartZoneOffset(startZoneOffset);
        }
        ZoneOffset endZoneOffset = activeCaloriesBurnedRecord.getEndZoneOffset();
        if (endZoneOffset != null) {
            builderA.setEndZoneOffset(endZoneOffset);
        }
        ActiveCaloriesBurnedRecord activeCaloriesBurnedRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(activeCaloriesBurnedRecordBuild, "PlatformActiveCaloriesBu…       }\n        .build()");
        return activeCaloriesBurnedRecordBuild;
    }

    private static final BasalBodyTemperatureRecord toPlatformBasalBodyTemperatureRecord(androidx.health.connect.client.records.BasalBodyTemperatureRecord basalBodyTemperatureRecord) {
        zm.a();
        BasalBodyTemperatureRecord.Builder builderA = ym.a(MetadataConvertersKt.toPlatformMetadata(basalBodyTemperatureRecord.getMetadata()), basalBodyTemperatureRecord.getTime(), IntDefMappingsKt.toPlatformBodyTemperatureMeasurementLocation(basalBodyTemperatureRecord.getMeasurementLocation()), UnitConvertersKt.toPlatformTemperature(basalBodyTemperatureRecord.getTemperature()));
        ZoneOffset zoneOffset = basalBodyTemperatureRecord.getZoneOffset();
        if (zoneOffset != null) {
            builderA.setZoneOffset(zoneOffset);
        }
        BasalBodyTemperatureRecord basalBodyTemperatureRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(basalBodyTemperatureRecordBuild, "PlatformBasalBodyTempera…(it) } }\n        .build()");
        return basalBodyTemperatureRecordBuild;
    }

    private static final BasalMetabolicRateRecord toPlatformBasalMetabolicRateRecord(androidx.health.connect.client.records.BasalMetabolicRateRecord basalMetabolicRateRecord) {
        fl.a();
        BasalMetabolicRateRecord.Builder builderA = el.a(MetadataConvertersKt.toPlatformMetadata(basalMetabolicRateRecord.getMetadata()), basalMetabolicRateRecord.getTime(), UnitConvertersKt.toPlatformPower(basalMetabolicRateRecord.getBasalMetabolicRate()));
        ZoneOffset zoneOffset = basalMetabolicRateRecord.getZoneOffset();
        if (zoneOffset != null) {
            builderA.setZoneOffset(zoneOffset);
        }
        BasalMetabolicRateRecord basalMetabolicRateRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(basalMetabolicRateRecordBuild, "PlatformBasalMetabolicRa…(it) } }\n        .build()");
        return basalMetabolicRateRecordBuild;
    }

    private static final BloodGlucoseRecord toPlatformBloodGlucoseRecord(androidx.health.connect.client.records.BloodGlucoseRecord bloodGlucoseRecord) {
        bn.a();
        BloodGlucoseRecord.Builder builderA = an.a(MetadataConvertersKt.toPlatformMetadata(bloodGlucoseRecord.getMetadata()), bloodGlucoseRecord.getTime(), IntDefMappingsKt.toPlatformBloodGlucoseSpecimenSource(bloodGlucoseRecord.getSpecimenSource()), UnitConvertersKt.toPlatformBloodGlucose(bloodGlucoseRecord.getLevel()), IntDefMappingsKt.toPlatformBloodGlucoseRelationToMeal(bloodGlucoseRecord.getRelationToMeal()), IntDefMappingsKt.toPlatformMealType(bloodGlucoseRecord.getMealType()));
        ZoneOffset zoneOffset = bloodGlucoseRecord.getZoneOffset();
        if (zoneOffset != null) {
            builderA.setZoneOffset(zoneOffset);
        }
        BloodGlucoseRecord bloodGlucoseRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(bloodGlucoseRecordBuild, "PlatformBloodGlucoseReco…(it) } }\n        .build()");
        return bloodGlucoseRecordBuild;
    }

    private static final BloodPressureRecord toPlatformBloodPressureRecord(androidx.health.connect.client.records.BloodPressureRecord bloodPressureRecord) {
        hm.a();
        BloodPressureRecord.Builder builderA = gm.a(MetadataConvertersKt.toPlatformMetadata(bloodPressureRecord.getMetadata()), bloodPressureRecord.getTime(), IntDefMappingsKt.toPlatformBloodPressureMeasurementLocation(bloodPressureRecord.getMeasurementLocation()), UnitConvertersKt.toPlatformPressure(bloodPressureRecord.getSystolic()), UnitConvertersKt.toPlatformPressure(bloodPressureRecord.getDiastolic()), IntDefMappingsKt.toPlatformBloodPressureBodyPosition(bloodPressureRecord.getBodyPosition()));
        ZoneOffset zoneOffset = bloodPressureRecord.getZoneOffset();
        if (zoneOffset != null) {
            builderA.setZoneOffset(zoneOffset);
        }
        BloodPressureRecord bloodPressureRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(bloodPressureRecordBuild, "PlatformBloodPressureRec…(it) } }\n        .build()");
        return bloodPressureRecordBuild;
    }

    private static final BodyFatRecord toPlatformBodyFatRecord(androidx.health.connect.client.records.BodyFatRecord bodyFatRecord) {
        wl.a();
        BodyFatRecord.Builder builderA = vl.a(MetadataConvertersKt.toPlatformMetadata(bodyFatRecord.getMetadata()), bodyFatRecord.getTime(), UnitConvertersKt.toPlatformPercentage(bodyFatRecord.getPercentage()));
        ZoneOffset zoneOffset = bodyFatRecord.getZoneOffset();
        if (zoneOffset != null) {
            builderA.setZoneOffset(zoneOffset);
        }
        BodyFatRecord bodyFatRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(bodyFatRecordBuild, "PlatformBodyFatRecordBui…(it) } }\n        .build()");
        return bodyFatRecordBuild;
    }

    private static final BodyTemperatureRecord toPlatformBodyTemperatureRecord(androidx.health.connect.client.records.BodyTemperatureRecord bodyTemperatureRecord) {
        pk.a();
        BodyTemperatureRecord.Builder builderA = ok.a(MetadataConvertersKt.toPlatformMetadata(bodyTemperatureRecord.getMetadata()), bodyTemperatureRecord.getTime(), IntDefMappingsKt.toPlatformBodyTemperatureMeasurementLocation(bodyTemperatureRecord.getMeasurementLocation()), UnitConvertersKt.toPlatformTemperature(bodyTemperatureRecord.getTemperature()));
        ZoneOffset zoneOffset = bodyTemperatureRecord.getZoneOffset();
        if (zoneOffset != null) {
            builderA.setZoneOffset(zoneOffset);
        }
        BodyTemperatureRecord bodyTemperatureRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(bodyTemperatureRecordBuild, "PlatformBodyTemperatureR…(it) } }\n        .build()");
        return bodyTemperatureRecordBuild;
    }

    private static final BodyWaterMassRecord toPlatformBodyWaterMassRecord(androidx.health.connect.client.records.BodyWaterMassRecord bodyWaterMassRecord) {
        jk.a();
        BodyWaterMassRecord.Builder builderA = ik.a(MetadataConvertersKt.toPlatformMetadata(bodyWaterMassRecord.getMetadata()), bodyWaterMassRecord.getTime(), UnitConvertersKt.toPlatformMass(bodyWaterMassRecord.getMass()));
        ZoneOffset zoneOffset = bodyWaterMassRecord.getZoneOffset();
        if (zoneOffset != null) {
            builderA.setZoneOffset(zoneOffset);
        }
        BodyWaterMassRecord bodyWaterMassRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(bodyWaterMassRecordBuild, "PlatformBodyWaterMassRec…(it) } }\n        .build()");
        return bodyWaterMassRecordBuild;
    }

    private static final BoneMassRecord toPlatformBoneMassRecord(androidx.health.connect.client.records.BoneMassRecord boneMassRecord) {
        mn.a();
        BoneMassRecord.Builder builderA = ln.a(MetadataConvertersKt.toPlatformMetadata(boneMassRecord.getMetadata()), boneMassRecord.getTime(), UnitConvertersKt.toPlatformMass(boneMassRecord.getMass()));
        ZoneOffset zoneOffset = boneMassRecord.getZoneOffset();
        if (zoneOffset != null) {
            builderA.setZoneOffset(zoneOffset);
        }
        BoneMassRecord boneMassRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(boneMassRecordBuild, "PlatformBoneMassRecordBu…(it) } }\n        .build()");
        return boneMassRecordBuild;
    }

    private static final CervicalMucusRecord toPlatformCervicalMucusRecord(androidx.health.connect.client.records.CervicalMucusRecord cervicalMucusRecord) {
        fm.a();
        CervicalMucusRecord.Builder builderA = em.a(MetadataConvertersKt.toPlatformMetadata(cervicalMucusRecord.getMetadata()), cervicalMucusRecord.getTime(), IntDefMappingsKt.toPlatformCervicalMucusSensation(cervicalMucusRecord.getSensation()), IntDefMappingsKt.toPlatformCervicalMucusAppearance(cervicalMucusRecord.getAppearance()));
        ZoneOffset zoneOffset = cervicalMucusRecord.getZoneOffset();
        if (zoneOffset != null) {
            builderA.setZoneOffset(zoneOffset);
        }
        CervicalMucusRecord cervicalMucusRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(cervicalMucusRecordBuild, "PlatformCervicalMucusRec…(it) } }\n        .build()");
        return cervicalMucusRecordBuild;
    }

    private static final CyclingPedalingCadenceRecord toPlatformCyclingPedalingCadenceRecord(androidx.health.connect.client.records.CyclingPedalingCadenceRecord cyclingPedalingCadenceRecord) {
        android.health.connect.datatypes.Metadata platformMetadata = MetadataConvertersKt.toPlatformMetadata(cyclingPedalingCadenceRecord.getMetadata());
        Instant startTime = cyclingPedalingCadenceRecord.getStartTime();
        Instant endTime = cyclingPedalingCadenceRecord.getEndTime();
        List<CyclingPedalingCadenceRecord.Sample> samples = cyclingPedalingCadenceRecord.getSamples();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(samples, 10));
        Iterator<T> it = samples.iterator();
        while (it.hasNext()) {
            arrayList.add(toPlatformCyclingPedalingCadenceSample((CyclingPedalingCadenceRecord.Sample) it.next()));
        }
        CyclingPedalingCadenceRecord.Builder builderA = vn.a(platformMetadata, startTime, endTime, arrayList);
        ZoneOffset startZoneOffset = cyclingPedalingCadenceRecord.getStartZoneOffset();
        if (startZoneOffset != null) {
            builderA.setStartZoneOffset(startZoneOffset);
        }
        ZoneOffset endZoneOffset = cyclingPedalingCadenceRecord.getEndZoneOffset();
        if (endZoneOffset != null) {
            builderA.setEndZoneOffset(endZoneOffset);
        }
        android.health.connect.datatypes.CyclingPedalingCadenceRecord cyclingPedalingCadenceRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(cyclingPedalingCadenceRecordBuild, "PlatformCyclingPedalingC…       }\n        .build()");
        return cyclingPedalingCadenceRecordBuild;
    }

    private static final CyclingPedalingCadenceRecord.CyclingPedalingCadenceRecordSample toPlatformCyclingPedalingCadenceSample(CyclingPedalingCadenceRecord.Sample sample) {
        uk.a();
        return tk.a(sample.getRevolutionsPerMinute(), sample.getTime());
    }

    private static final DistanceRecord toPlatformDistanceRecord(androidx.health.connect.client.records.DistanceRecord distanceRecord) {
        qm.a();
        DistanceRecord.Builder builderA = pm.a(MetadataConvertersKt.toPlatformMetadata(distanceRecord.getMetadata()), distanceRecord.getStartTime(), distanceRecord.getEndTime(), UnitConvertersKt.toPlatformLength(distanceRecord.getDistance()));
        ZoneOffset startZoneOffset = distanceRecord.getStartZoneOffset();
        if (startZoneOffset != null) {
            builderA.setStartZoneOffset(startZoneOffset);
        }
        ZoneOffset endZoneOffset = distanceRecord.getEndZoneOffset();
        if (endZoneOffset != null) {
            builderA.setEndZoneOffset(endZoneOffset);
        }
        DistanceRecord distanceRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(distanceRecordBuild, "PlatformDistanceRecordBu…       }\n        .build()");
        return distanceRecordBuild;
    }

    private static final ElevationGainedRecord toPlatformElevationGainedRecord(androidx.health.connect.client.records.ElevationGainedRecord elevationGainedRecord) {
        kn.a();
        ElevationGainedRecord.Builder builderA = jn.a(MetadataConvertersKt.toPlatformMetadata(elevationGainedRecord.getMetadata()), elevationGainedRecord.getStartTime(), elevationGainedRecord.getEndTime(), UnitConvertersKt.toPlatformLength(elevationGainedRecord.getElevation()));
        ZoneOffset startZoneOffset = elevationGainedRecord.getStartZoneOffset();
        if (startZoneOffset != null) {
            builderA.setStartZoneOffset(startZoneOffset);
        }
        ZoneOffset endZoneOffset = elevationGainedRecord.getEndZoneOffset();
        if (endZoneOffset != null) {
            builderA.setEndZoneOffset(endZoneOffset);
        }
        ElevationGainedRecord elevationGainedRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(elevationGainedRecordBuild, "PlatformElevationGainedR…       }\n        .build()");
        return elevationGainedRecordBuild;
    }

    private static final ExerciseLap toPlatformExerciseLap(androidx.health.connect.client.records.ExerciseLap exerciseLap) {
        mm.a();
        ExerciseLap.Builder builderA = lm.a(exerciseLap.getStartTime(), exerciseLap.getEndTime());
        Length length = exerciseLap.getLength();
        if (length != null) {
            builderA.setLength(UnitConvertersKt.toPlatformLength(length));
        }
        ExerciseLap exerciseLapBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(exerciseLapBuild, "PlatformExerciseLapBuild…h()) } }\n        .build()");
        return exerciseLapBuild;
    }

    private static final ExerciseRoute toPlatformExerciseRoute(androidx.health.connect.client.records.ExerciseRoute exerciseRoute) {
        List<ExerciseRoute.Location> route = exerciseRoute.getRoute();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(route, 10));
        for (ExerciseRoute.Location location : route) {
            zn.a();
            ExerciseRoute.Location.Builder builderA = xn.a(location.getTime(), location.getLatitude(), location.getLongitude());
            Length horizontalAccuracy = location.getHorizontalAccuracy();
            if (horizontalAccuracy != null) {
                builderA.setHorizontalAccuracy(UnitConvertersKt.toPlatformLength(horizontalAccuracy));
            }
            Length verticalAccuracy = location.getVerticalAccuracy();
            if (verticalAccuracy != null) {
                builderA.setVerticalAccuracy(UnitConvertersKt.toPlatformLength(verticalAccuracy));
            }
            Length altitude = location.getAltitude();
            if (altitude != null) {
                builderA.setAltitude(UnitConvertersKt.toPlatformLength(altitude));
            }
            arrayList.add(builderA.build());
        }
        return yn.a(arrayList);
    }

    private static final ExerciseSegment toPlatformExerciseSegment(androidx.health.connect.client.records.ExerciseSegment exerciseSegment) {
        km.a();
        ExerciseSegment exerciseSegmentBuild = jm.a(exerciseSegment.getStartTime(), exerciseSegment.getEndTime(), IntDefMappingsKt.toPlatformExerciseSegmentType(exerciseSegment.getSegmentType())).setRepetitionsCount(exerciseSegment.getRepetitions()).build();
        Intrinsics.checkNotNullExpressionValue(exerciseSegmentBuild, "PlatformExerciseSegmentB…titions)\n        .build()");
        return exerciseSegmentBuild;
    }

    private static final ExerciseSessionRecord toPlatformExerciseSessionRecord(androidx.health.connect.client.records.ExerciseSessionRecord exerciseSessionRecord) {
        un.a();
        ExerciseSessionRecord.Builder builderA = tn.a(MetadataConvertersKt.toPlatformMetadata(exerciseSessionRecord.getMetadata()), exerciseSessionRecord.getStartTime(), exerciseSessionRecord.getEndTime(), IntDefMappingsKt.toPlatformExerciseSessionType(exerciseSessionRecord.getExerciseType()));
        ZoneOffset startZoneOffset = exerciseSessionRecord.getStartZoneOffset();
        if (startZoneOffset != null) {
            builderA.setStartZoneOffset(startZoneOffset);
        }
        ZoneOffset endZoneOffset = exerciseSessionRecord.getEndZoneOffset();
        if (endZoneOffset != null) {
            builderA.setEndZoneOffset(endZoneOffset);
        }
        String notes = exerciseSessionRecord.getNotes();
        if (notes != null) {
            builderA.setNotes(notes);
        }
        String title = exerciseSessionRecord.getTitle();
        if (title != null) {
            builderA.setTitle(title);
        }
        List<androidx.health.connect.client.records.ExerciseLap> laps = exerciseSessionRecord.getLaps();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(laps, 10));
        Iterator<T> it = laps.iterator();
        while (it.hasNext()) {
            arrayList.add(toPlatformExerciseLap((androidx.health.connect.client.records.ExerciseLap) it.next()));
        }
        builderA.setLaps(arrayList);
        List<androidx.health.connect.client.records.ExerciseSegment> segments = exerciseSessionRecord.getSegments();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(segments, 10));
        Iterator<T> it2 = segments.iterator();
        while (it2.hasNext()) {
            arrayList2.add(toPlatformExerciseSegment((androidx.health.connect.client.records.ExerciseSegment) it2.next()));
        }
        builderA.setSegments(arrayList2);
        if (exerciseSessionRecord.getExerciseRouteResult() instanceof ExerciseRouteResult.Data) {
            builderA.setRoute(toPlatformExerciseRoute(((ExerciseRouteResult.Data) exerciseSessionRecord.getExerciseRouteResult()).getExerciseRoute()));
        }
        ExerciseSessionRecord exerciseSessionRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(exerciseSessionRecordBuild, "PlatformExerciseSessionR…       }\n        .build()");
        return exerciseSessionRecordBuild;
    }

    private static final FloorsClimbedRecord toPlatformFloorsClimbedRecord(androidx.health.connect.client.records.FloorsClimbedRecord floorsClimbedRecord) {
        sk.a();
        FloorsClimbedRecord.Builder builderA = rk.a(MetadataConvertersKt.toPlatformMetadata(floorsClimbedRecord.getMetadata()), floorsClimbedRecord.getStartTime(), floorsClimbedRecord.getEndTime(), floorsClimbedRecord.getFloors());
        ZoneOffset startZoneOffset = floorsClimbedRecord.getStartZoneOffset();
        if (startZoneOffset != null) {
            builderA.setStartZoneOffset(startZoneOffset);
        }
        ZoneOffset endZoneOffset = floorsClimbedRecord.getEndZoneOffset();
        if (endZoneOffset != null) {
            builderA.setEndZoneOffset(endZoneOffset);
        }
        FloorsClimbedRecord floorsClimbedRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(floorsClimbedRecordBuild, "PlatformFloorsClimbedRec…       }\n        .build()");
        return floorsClimbedRecordBuild;
    }

    private static final HeartRateRecord toPlatformHeartRateRecord(androidx.health.connect.client.records.HeartRateRecord heartRateRecord) {
        android.health.connect.datatypes.Metadata platformMetadata = MetadataConvertersKt.toPlatformMetadata(heartRateRecord.getMetadata());
        Instant startTime = heartRateRecord.getStartTime();
        Instant endTime = heartRateRecord.getEndTime();
        List<HeartRateRecord.Sample> samples = heartRateRecord.getSamples();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(samples, 10));
        Iterator<T> it = samples.iterator();
        while (it.hasNext()) {
            arrayList.add(toPlatformHeartRateSample((HeartRateRecord.Sample) it.next()));
        }
        HeartRateRecord.Builder builderA = wn.a(platformMetadata, startTime, endTime, arrayList);
        ZoneOffset startZoneOffset = heartRateRecord.getStartZoneOffset();
        if (startZoneOffset != null) {
            builderA.setStartZoneOffset(startZoneOffset);
        }
        ZoneOffset endZoneOffset = heartRateRecord.getEndZoneOffset();
        if (endZoneOffset != null) {
            builderA.setEndZoneOffset(endZoneOffset);
        }
        android.health.connect.datatypes.HeartRateRecord heartRateRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(heartRateRecordBuild, "PlatformHeartRateRecordB…       }\n        .build()");
        return heartRateRecordBuild;
    }

    private static final HeartRateRecord.HeartRateSample toPlatformHeartRateSample(HeartRateRecord.Sample sample) {
        ol.a();
        return nl.a(sample.getBeatsPerMinute(), sample.getTime());
    }

    private static final HeartRateVariabilityRmssdRecord toPlatformHeartRateVariabilityRmssdRecord(androidx.health.connect.client.records.HeartRateVariabilityRmssdRecord heartRateVariabilityRmssdRecord) {
        dn.a();
        HeartRateVariabilityRmssdRecord.Builder builderA = cn.a(MetadataConvertersKt.toPlatformMetadata(heartRateVariabilityRmssdRecord.getMetadata()), heartRateVariabilityRmssdRecord.getTime(), heartRateVariabilityRmssdRecord.getHeartRateVariabilityMillis());
        ZoneOffset zoneOffset = heartRateVariabilityRmssdRecord.getZoneOffset();
        if (zoneOffset != null) {
            builderA.setZoneOffset(zoneOffset);
        }
        HeartRateVariabilityRmssdRecord heartRateVariabilityRmssdRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(heartRateVariabilityRmssdRecordBuild, "PlatformHeartRateVariabi…(it) } }\n        .build()");
        return heartRateVariabilityRmssdRecordBuild;
    }

    private static final HeightRecord toPlatformHeightRecord(androidx.health.connect.client.records.HeightRecord heightRecord) {
        om.a();
        HeightRecord.Builder builderA = nm.a(MetadataConvertersKt.toPlatformMetadata(heightRecord.getMetadata()), heightRecord.getTime(), UnitConvertersKt.toPlatformLength(heightRecord.getHeight()));
        ZoneOffset zoneOffset = heightRecord.getZoneOffset();
        if (zoneOffset != null) {
            builderA.setZoneOffset(zoneOffset);
        }
        HeightRecord heightRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(heightRecordBuild, "PlatformHeightRecordBuil…(it) } }\n        .build()");
        return heightRecordBuild;
    }

    private static final HydrationRecord toPlatformHydrationRecord(androidx.health.connect.client.records.HydrationRecord hydrationRecord) {
        sl.a();
        HydrationRecord.Builder builderA = rl.a(MetadataConvertersKt.toPlatformMetadata(hydrationRecord.getMetadata()), hydrationRecord.getStartTime(), hydrationRecord.getEndTime(), UnitConvertersKt.toPlatformVolume(hydrationRecord.getVolume()));
        ZoneOffset startZoneOffset = hydrationRecord.getStartZoneOffset();
        if (startZoneOffset != null) {
            builderA.setStartZoneOffset(startZoneOffset);
        }
        ZoneOffset endZoneOffset = hydrationRecord.getEndZoneOffset();
        if (endZoneOffset != null) {
            builderA.setEndZoneOffset(endZoneOffset);
        }
        HydrationRecord hydrationRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(hydrationRecordBuild, "PlatformHydrationRecordB…       }\n        .build()");
        return hydrationRecordBuild;
    }

    private static final IntermenstrualBleedingRecord toPlatformIntermenstrualBleedingRecord(androidx.health.connect.client.records.IntermenstrualBleedingRecord intermenstrualBleedingRecord) {
        wk.a();
        IntermenstrualBleedingRecord.Builder builderA = vk.a(MetadataConvertersKt.toPlatformMetadata(intermenstrualBleedingRecord.getMetadata()), intermenstrualBleedingRecord.getTime());
        ZoneOffset zoneOffset = intermenstrualBleedingRecord.getZoneOffset();
        if (zoneOffset != null) {
            builderA.setZoneOffset(zoneOffset);
        }
        IntermenstrualBleedingRecord intermenstrualBleedingRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(intermenstrualBleedingRecordBuild, "PlatformIntermenstrualBl…(it) } }\n        .build()");
        return intermenstrualBleedingRecordBuild;
    }

    private static final LeanBodyMassRecord toPlatformLeanBodyMassRecord(androidx.health.connect.client.records.LeanBodyMassRecord leanBodyMassRecord) {
        vm.a();
        LeanBodyMassRecord.Builder builderA = um.a(MetadataConvertersKt.toPlatformMetadata(leanBodyMassRecord.getMetadata()), leanBodyMassRecord.getTime(), UnitConvertersKt.toPlatformMass(leanBodyMassRecord.getMass()));
        ZoneOffset zoneOffset = leanBodyMassRecord.getZoneOffset();
        if (zoneOffset != null) {
            builderA.setZoneOffset(zoneOffset);
        }
        LeanBodyMassRecord leanBodyMassRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(leanBodyMassRecordBuild, "PlatformLeanBodyMassReco…(it) } }\n        .build()");
        return leanBodyMassRecordBuild;
    }

    private static final MenstruationFlowRecord toPlatformMenstruationFlowRecord(androidx.health.connect.client.records.MenstruationFlowRecord menstruationFlowRecord) {
        al.a();
        MenstruationFlowRecord.Builder builderA = zk.a(MetadataConvertersKt.toPlatformMetadata(menstruationFlowRecord.getMetadata()), menstruationFlowRecord.getTime(), IntDefMappingsKt.toPlatformMenstruationFlow(menstruationFlowRecord.getFlow()));
        ZoneOffset zoneOffset = menstruationFlowRecord.getZoneOffset();
        if (zoneOffset != null) {
            builderA.setZoneOffset(zoneOffset);
        }
        MenstruationFlowRecord menstruationFlowRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(menstruationFlowRecordBuild, "PlatformMenstruationFlow…(it) } }\n        .build()");
        return menstruationFlowRecordBuild;
    }

    private static final MenstruationPeriodRecord toPlatformMenstruationPeriodRecord(androidx.health.connect.client.records.MenstruationPeriodRecord menstruationPeriodRecord) {
        yk.a();
        MenstruationPeriodRecord.Builder builderA = xk.a(MetadataConvertersKt.toPlatformMetadata(menstruationPeriodRecord.getMetadata()), menstruationPeriodRecord.getStartTime(), menstruationPeriodRecord.getEndTime());
        ZoneOffset startZoneOffset = menstruationPeriodRecord.getStartZoneOffset();
        if (startZoneOffset != null) {
            builderA.setStartZoneOffset(startZoneOffset);
        }
        ZoneOffset endZoneOffset = menstruationPeriodRecord.getEndZoneOffset();
        if (endZoneOffset != null) {
            builderA.setEndZoneOffset(endZoneOffset);
        }
        MenstruationPeriodRecord menstruationPeriodRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(menstruationPeriodRecordBuild, "PlatformMenstruationPeri…       }\n        .build()");
        return menstruationPeriodRecordBuild;
    }

    private static final NutritionRecord toPlatformNutritionRecord(androidx.health.connect.client.records.NutritionRecord nutritionRecord) {
        dl.a();
        NutritionRecord.Builder mealType = cl.a(MetadataConvertersKt.toPlatformMetadata(nutritionRecord.getMetadata()), nutritionRecord.getStartTime(), nutritionRecord.getEndTime()).setMealType(IntDefMappingsKt.toPlatformMealType(nutritionRecord.getMealType()));
        ZoneOffset startZoneOffset = nutritionRecord.getStartZoneOffset();
        if (startZoneOffset != null) {
            mealType.setStartZoneOffset(startZoneOffset);
        }
        ZoneOffset endZoneOffset = nutritionRecord.getEndZoneOffset();
        if (endZoneOffset != null) {
            mealType.setEndZoneOffset(endZoneOffset);
        }
        Mass biotin = nutritionRecord.getBiotin();
        if (biotin != null) {
            mealType.setBiotin(UnitConvertersKt.toPlatformMass(biotin));
        }
        Mass caffeine = nutritionRecord.getCaffeine();
        if (caffeine != null) {
            mealType.setCaffeine(UnitConvertersKt.toPlatformMass(caffeine));
        }
        Mass calcium = nutritionRecord.getCalcium();
        if (calcium != null) {
            mealType.setCalcium(UnitConvertersKt.toPlatformMass(calcium));
        }
        Mass chloride = nutritionRecord.getChloride();
        if (chloride != null) {
            mealType.setChloride(UnitConvertersKt.toPlatformMass(chloride));
        }
        Mass cholesterol = nutritionRecord.getCholesterol();
        if (cholesterol != null) {
            mealType.setCholesterol(UnitConvertersKt.toPlatformMass(cholesterol));
        }
        Mass chromium = nutritionRecord.getChromium();
        if (chromium != null) {
            mealType.setChromium(UnitConvertersKt.toPlatformMass(chromium));
        }
        Mass copper = nutritionRecord.getCopper();
        if (copper != null) {
            mealType.setCopper(UnitConvertersKt.toPlatformMass(copper));
        }
        Mass dietaryFiber = nutritionRecord.getDietaryFiber();
        if (dietaryFiber != null) {
            mealType.setDietaryFiber(UnitConvertersKt.toPlatformMass(dietaryFiber));
        }
        Energy energy = nutritionRecord.getEnergy();
        if (energy != null) {
            mealType.setEnergy(UnitConvertersKt.toPlatformEnergy(energy));
        }
        Energy energyFromFat = nutritionRecord.getEnergyFromFat();
        if (energyFromFat != null) {
            mealType.setEnergyFromFat(UnitConvertersKt.toPlatformEnergy(energyFromFat));
        }
        Mass folate = nutritionRecord.getFolate();
        if (folate != null) {
            mealType.setFolate(UnitConvertersKt.toPlatformMass(folate));
        }
        Mass folicAcid = nutritionRecord.getFolicAcid();
        if (folicAcid != null) {
            mealType.setFolicAcid(UnitConvertersKt.toPlatformMass(folicAcid));
        }
        Mass iodine = nutritionRecord.getIodine();
        if (iodine != null) {
            mealType.setIodine(UnitConvertersKt.toPlatformMass(iodine));
        }
        Mass iron = nutritionRecord.getIron();
        if (iron != null) {
            mealType.setIron(UnitConvertersKt.toPlatformMass(iron));
        }
        Mass magnesium = nutritionRecord.getMagnesium();
        if (magnesium != null) {
            mealType.setMagnesium(UnitConvertersKt.toPlatformMass(magnesium));
        }
        Mass manganese = nutritionRecord.getManganese();
        if (manganese != null) {
            mealType.setManganese(UnitConvertersKt.toPlatformMass(manganese));
        }
        Mass molybdenum = nutritionRecord.getMolybdenum();
        if (molybdenum != null) {
            mealType.setMolybdenum(UnitConvertersKt.toPlatformMass(molybdenum));
        }
        Mass monounsaturatedFat = nutritionRecord.getMonounsaturatedFat();
        if (monounsaturatedFat != null) {
            mealType.setMonounsaturatedFat(UnitConvertersKt.toPlatformMass(monounsaturatedFat));
        }
        String name = nutritionRecord.getName();
        if (name != null) {
            mealType.setMealName(name);
        }
        Mass niacin = nutritionRecord.getNiacin();
        if (niacin != null) {
            mealType.setNiacin(UnitConvertersKt.toPlatformMass(niacin));
        }
        Mass pantothenicAcid = nutritionRecord.getPantothenicAcid();
        if (pantothenicAcid != null) {
            mealType.setPantothenicAcid(UnitConvertersKt.toPlatformMass(pantothenicAcid));
        }
        Mass phosphorus = nutritionRecord.getPhosphorus();
        if (phosphorus != null) {
            mealType.setPhosphorus(UnitConvertersKt.toPlatformMass(phosphorus));
        }
        Mass polyunsaturatedFat = nutritionRecord.getPolyunsaturatedFat();
        if (polyunsaturatedFat != null) {
            mealType.setPolyunsaturatedFat(UnitConvertersKt.toPlatformMass(polyunsaturatedFat));
        }
        Mass potassium = nutritionRecord.getPotassium();
        if (potassium != null) {
            mealType.setPotassium(UnitConvertersKt.toPlatformMass(potassium));
        }
        Mass protein = nutritionRecord.getProtein();
        if (protein != null) {
            mealType.setProtein(UnitConvertersKt.toPlatformMass(protein));
        }
        Mass riboflavin = nutritionRecord.getRiboflavin();
        if (riboflavin != null) {
            mealType.setRiboflavin(UnitConvertersKt.toPlatformMass(riboflavin));
        }
        Mass saturatedFat = nutritionRecord.getSaturatedFat();
        if (saturatedFat != null) {
            mealType.setSaturatedFat(UnitConvertersKt.toPlatformMass(saturatedFat));
        }
        Mass selenium = nutritionRecord.getSelenium();
        if (selenium != null) {
            mealType.setSelenium(UnitConvertersKt.toPlatformMass(selenium));
        }
        Mass sodium = nutritionRecord.getSodium();
        if (sodium != null) {
            mealType.setSodium(UnitConvertersKt.toPlatformMass(sodium));
        }
        Mass sugar = nutritionRecord.getSugar();
        if (sugar != null) {
            mealType.setSugar(UnitConvertersKt.toPlatformMass(sugar));
        }
        Mass thiamin = nutritionRecord.getThiamin();
        if (thiamin != null) {
            mealType.setThiamin(UnitConvertersKt.toPlatformMass(thiamin));
        }
        Mass totalCarbohydrate = nutritionRecord.getTotalCarbohydrate();
        if (totalCarbohydrate != null) {
            mealType.setTotalCarbohydrate(UnitConvertersKt.toPlatformMass(totalCarbohydrate));
        }
        Mass totalFat = nutritionRecord.getTotalFat();
        if (totalFat != null) {
            mealType.setTotalFat(UnitConvertersKt.toPlatformMass(totalFat));
        }
        Mass transFat = nutritionRecord.getTransFat();
        if (transFat != null) {
            mealType.setTransFat(UnitConvertersKt.toPlatformMass(transFat));
        }
        Mass unsaturatedFat = nutritionRecord.getUnsaturatedFat();
        if (unsaturatedFat != null) {
            mealType.setUnsaturatedFat(UnitConvertersKt.toPlatformMass(unsaturatedFat));
        }
        Mass vitaminA = nutritionRecord.getVitaminA();
        if (vitaminA != null) {
            mealType.setVitaminA(UnitConvertersKt.toPlatformMass(vitaminA));
        }
        Mass vitaminB6 = nutritionRecord.getVitaminB6();
        if (vitaminB6 != null) {
            mealType.setVitaminB6(UnitConvertersKt.toPlatformMass(vitaminB6));
        }
        Mass vitaminB12 = nutritionRecord.getVitaminB12();
        if (vitaminB12 != null) {
            mealType.setVitaminB12(UnitConvertersKt.toPlatformMass(vitaminB12));
        }
        Mass vitaminC = nutritionRecord.getVitaminC();
        if (vitaminC != null) {
            mealType.setVitaminC(UnitConvertersKt.toPlatformMass(vitaminC));
        }
        Mass vitaminD = nutritionRecord.getVitaminD();
        if (vitaminD != null) {
            mealType.setVitaminD(UnitConvertersKt.toPlatformMass(vitaminD));
        }
        Mass vitaminE = nutritionRecord.getVitaminE();
        if (vitaminE != null) {
            mealType.setVitaminE(UnitConvertersKt.toPlatformMass(vitaminE));
        }
        Mass vitaminK = nutritionRecord.getVitaminK();
        if (vitaminK != null) {
            mealType.setVitaminK(UnitConvertersKt.toPlatformMass(vitaminK));
        }
        Mass zinc = nutritionRecord.getZinc();
        if (zinc != null) {
            mealType.setZinc(UnitConvertersKt.toPlatformMass(zinc));
        }
        NutritionRecord nutritionRecordBuild = mealType.build();
        Intrinsics.checkNotNullExpressionValue(nutritionRecordBuild, "PlatformNutritionRecordB…       }\n        .build()");
        return nutritionRecordBuild;
    }

    private static final OvulationTestRecord toPlatformOvulationTestRecord(androidx.health.connect.client.records.OvulationTestRecord ovulationTestRecord) {
        ql.a();
        OvulationTestRecord.Builder builderA = pl.a(MetadataConvertersKt.toPlatformMetadata(ovulationTestRecord.getMetadata()), ovulationTestRecord.getTime(), IntDefMappingsKt.toPlatformOvulationTestResult(ovulationTestRecord.getResult()));
        ZoneOffset zoneOffset = ovulationTestRecord.getZoneOffset();
        if (zoneOffset != null) {
            builderA.setZoneOffset(zoneOffset);
        }
        OvulationTestRecord ovulationTestRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(ovulationTestRecordBuild, "PlatformOvulationTestRec…(it) } }\n        .build()");
        return ovulationTestRecordBuild;
    }

    private static final OxygenSaturationRecord toPlatformOxygenSaturationRecord(androidx.health.connect.client.records.OxygenSaturationRecord oxygenSaturationRecord) {
        dm.a();
        OxygenSaturationRecord.Builder builderA = cm.a(MetadataConvertersKt.toPlatformMetadata(oxygenSaturationRecord.getMetadata()), oxygenSaturationRecord.getTime(), UnitConvertersKt.toPlatformPercentage(oxygenSaturationRecord.getPercentage()));
        ZoneOffset zoneOffset = oxygenSaturationRecord.getZoneOffset();
        if (zoneOffset != null) {
            builderA.setZoneOffset(zoneOffset);
        }
        OxygenSaturationRecord oxygenSaturationRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(oxygenSaturationRecordBuild, "PlatformOxygenSaturation…(it) } }\n        .build()");
        return oxygenSaturationRecordBuild;
    }

    private static final PowerRecord toPlatformPowerRecord(androidx.health.connect.client.records.PowerRecord powerRecord) {
        android.health.connect.datatypes.Metadata platformMetadata = MetadataConvertersKt.toPlatformMetadata(powerRecord.getMetadata());
        Instant startTime = powerRecord.getStartTime();
        Instant endTime = powerRecord.getEndTime();
        List<PowerRecord.Sample> samples = powerRecord.getSamples();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(samples, 10));
        Iterator<T> it = samples.iterator();
        while (it.hasNext()) {
            arrayList.add(toPlatformPowerRecordSample((PowerRecord.Sample) it.next()));
        }
        PowerRecord.Builder builderA = nn.a(platformMetadata, startTime, endTime, arrayList);
        ZoneOffset startZoneOffset = powerRecord.getStartZoneOffset();
        if (startZoneOffset != null) {
            builderA.setStartZoneOffset(startZoneOffset);
        }
        ZoneOffset endZoneOffset = powerRecord.getEndZoneOffset();
        if (endZoneOffset != null) {
            builderA.setEndZoneOffset(endZoneOffset);
        }
        android.health.connect.datatypes.PowerRecord powerRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(powerRecordBuild, "PlatformPowerRecordBuild…       }\n        .build()");
        return powerRecordBuild;
    }

    private static final PowerRecord.PowerRecordSample toPlatformPowerRecordSample(PowerRecord.Sample sample) {
        ul.a();
        return tl.a(UnitConvertersKt.toPlatformPower(sample.getPower()), sample.getTime());
    }

    @NotNull
    public static final Record toPlatformRecord(@NotNull androidx.health.connect.client.records.Record record) {
        Intrinsics.checkNotNullParameter(record, "<this>");
        if (record instanceof androidx.health.connect.client.records.ActiveCaloriesBurnedRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformActiveCaloriesBurnedRecord((androidx.health.connect.client.records.ActiveCaloriesBurnedRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.BasalBodyTemperatureRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformBasalBodyTemperatureRecord((androidx.health.connect.client.records.BasalBodyTemperatureRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.BasalMetabolicRateRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformBasalMetabolicRateRecord((androidx.health.connect.client.records.BasalMetabolicRateRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.BloodGlucoseRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformBloodGlucoseRecord((androidx.health.connect.client.records.BloodGlucoseRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.BloodPressureRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformBloodPressureRecord((androidx.health.connect.client.records.BloodPressureRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.BodyFatRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformBodyFatRecord((androidx.health.connect.client.records.BodyFatRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.BodyTemperatureRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformBodyTemperatureRecord((androidx.health.connect.client.records.BodyTemperatureRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.BodyWaterMassRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformBodyWaterMassRecord((androidx.health.connect.client.records.BodyWaterMassRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.BoneMassRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformBoneMassRecord((androidx.health.connect.client.records.BoneMassRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.CervicalMucusRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformCervicalMucusRecord((androidx.health.connect.client.records.CervicalMucusRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.CyclingPedalingCadenceRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformCyclingPedalingCadenceRecord((androidx.health.connect.client.records.CyclingPedalingCadenceRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.DistanceRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformDistanceRecord((androidx.health.connect.client.records.DistanceRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.ElevationGainedRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformElevationGainedRecord((androidx.health.connect.client.records.ElevationGainedRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.ExerciseSessionRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformExerciseSessionRecord((androidx.health.connect.client.records.ExerciseSessionRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.FloorsClimbedRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformFloorsClimbedRecord((androidx.health.connect.client.records.FloorsClimbedRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.HeartRateRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformHeartRateRecord((androidx.health.connect.client.records.HeartRateRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.HeartRateVariabilityRmssdRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformHeartRateVariabilityRmssdRecord((androidx.health.connect.client.records.HeartRateVariabilityRmssdRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.HeightRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformHeightRecord((androidx.health.connect.client.records.HeightRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.HydrationRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformHydrationRecord((androidx.health.connect.client.records.HydrationRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.IntermenstrualBleedingRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformIntermenstrualBleedingRecord((androidx.health.connect.client.records.IntermenstrualBleedingRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.LeanBodyMassRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformLeanBodyMassRecord((androidx.health.connect.client.records.LeanBodyMassRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.MenstruationFlowRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformMenstruationFlowRecord((androidx.health.connect.client.records.MenstruationFlowRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.MenstruationPeriodRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformMenstruationPeriodRecord((androidx.health.connect.client.records.MenstruationPeriodRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.NutritionRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformNutritionRecord((androidx.health.connect.client.records.NutritionRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.OvulationTestRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformOvulationTestRecord((androidx.health.connect.client.records.OvulationTestRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.OxygenSaturationRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformOxygenSaturationRecord((androidx.health.connect.client.records.OxygenSaturationRecord) record));
        }
        if (record instanceof androidx.health.connect.client.records.PowerRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformPowerRecord((androidx.health.connect.client.records.PowerRecord) record));
        }
        if (record instanceof RespiratoryRateRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformRespiratoryRateRecord((RespiratoryRateRecord) record));
        }
        if (record instanceof RestingHeartRateRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformRestingHeartRateRecord((RestingHeartRateRecord) record));
        }
        if (record instanceof SexualActivityRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformSexualActivityRecord((SexualActivityRecord) record));
        }
        if (record instanceof SleepSessionRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformSleepSessionRecord((SleepSessionRecord) record));
        }
        if (record instanceof SpeedRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformSpeedRecord((SpeedRecord) record));
        }
        if (record instanceof StepsCadenceRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformStepsCadenceRecord((StepsCadenceRecord) record));
        }
        if (record instanceof StepsRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformStepsRecord((StepsRecord) record));
        }
        if (record instanceof TotalCaloriesBurnedRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformTotalCaloriesBurnedRecord((TotalCaloriesBurnedRecord) record));
        }
        if (record instanceof Vo2MaxRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformVo2MaxRecord((Vo2MaxRecord) record));
        }
        if (record instanceof WeightRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformWeightRecord((WeightRecord) record));
        }
        if (record instanceof WheelchairPushesRecord) {
            return androidx.health.connect.client.impl.w.a(toPlatformWheelchairPushesRecord((WheelchairPushesRecord) record));
        }
        throw new IllegalArgumentException("Unsupported record " + record);
    }

    @NotNull
    public static final Class<? extends Record> toPlatformRecordClass(@NotNull KClass<? extends androidx.health.connect.client.records.Record> kClass) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        Class<? extends Record> cls = RecordMappingsKt.getSDK_TO_PLATFORM_RECORD_CLASS().get(kClass);
        if (cls != null) {
            return cls;
        }
        throw new IllegalArgumentException("Unsupported record type " + kClass);
    }

    private static final android.health.connect.datatypes.RespiratoryRateRecord toPlatformRespiratoryRateRecord(RespiratoryRateRecord respiratoryRateRecord) {
        xm.a();
        RespiratoryRateRecord.Builder builderA = wm.a(MetadataConvertersKt.toPlatformMetadata(respiratoryRateRecord.getMetadata()), respiratoryRateRecord.getTime(), respiratoryRateRecord.getRate());
        ZoneOffset zoneOffset = respiratoryRateRecord.getZoneOffset();
        if (zoneOffset != null) {
            builderA.setZoneOffset(zoneOffset);
        }
        android.health.connect.datatypes.RespiratoryRateRecord respiratoryRateRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(respiratoryRateRecordBuild, "PlatformRespiratoryRateR…(it) } }\n        .build()");
        return respiratoryRateRecordBuild;
    }

    private static final android.health.connect.datatypes.RestingHeartRateRecord toPlatformRestingHeartRateRecord(RestingHeartRateRecord restingHeartRateRecord) {
        gn.a();
        RestingHeartRateRecord.Builder builderA = fn.a(MetadataConvertersKt.toPlatformMetadata(restingHeartRateRecord.getMetadata()), restingHeartRateRecord.getTime(), restingHeartRateRecord.getBeatsPerMinute());
        ZoneOffset zoneOffset = restingHeartRateRecord.getZoneOffset();
        if (zoneOffset != null) {
            builderA.setZoneOffset(zoneOffset);
        }
        android.health.connect.datatypes.RestingHeartRateRecord restingHeartRateRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(restingHeartRateRecordBuild, "PlatformRestingHeartRate…(it) } }\n        .build()");
        return restingHeartRateRecordBuild;
    }

    private static final android.health.connect.datatypes.SexualActivityRecord toPlatformSexualActivityRecord(SexualActivityRecord sexualActivityRecord) {
        ll.a();
        SexualActivityRecord.Builder builderA = kl.a(MetadataConvertersKt.toPlatformMetadata(sexualActivityRecord.getMetadata()), sexualActivityRecord.getTime(), IntDefMappingsKt.toPlatformSexualActivityProtectionUsed(sexualActivityRecord.getProtectionUsed()));
        ZoneOffset zoneOffset = sexualActivityRecord.getZoneOffset();
        if (zoneOffset != null) {
            builderA.setZoneOffset(zoneOffset);
        }
        android.health.connect.datatypes.SexualActivityRecord sexualActivityRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(sexualActivityRecordBuild, "PlatformSexualActivityRe…(it) } }\n        .build()");
        return sexualActivityRecordBuild;
    }

    private static final android.health.connect.datatypes.SleepSessionRecord toPlatformSleepSessionRecord(SleepSessionRecord sleepSessionRecord) {
        rn.a();
        SleepSessionRecord.Builder builderA = qn.a(MetadataConvertersKt.toPlatformMetadata(sleepSessionRecord.getMetadata()), sleepSessionRecord.getStartTime(), sleepSessionRecord.getEndTime());
        ZoneOffset startZoneOffset = sleepSessionRecord.getStartZoneOffset();
        if (startZoneOffset != null) {
            builderA.setStartZoneOffset(startZoneOffset);
        }
        ZoneOffset endZoneOffset = sleepSessionRecord.getEndZoneOffset();
        if (endZoneOffset != null) {
            builderA.setEndZoneOffset(endZoneOffset);
        }
        String notes = sleepSessionRecord.getNotes();
        if (notes != null) {
            builderA.setNotes(notes);
        }
        String title = sleepSessionRecord.getTitle();
        if (title != null) {
            builderA.setTitle(title);
        }
        List<SleepSessionRecord.Stage> stages = sleepSessionRecord.getStages();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(stages, 10));
        Iterator<T> it = stages.iterator();
        while (it.hasNext()) {
            arrayList.add(toPlatformSleepSessionStage((SleepSessionRecord.Stage) it.next()));
        }
        builderA.setStages(arrayList);
        android.health.connect.datatypes.SleepSessionRecord sleepSessionRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(sleepSessionRecordBuild, "PlatformSleepSessionReco…       }\n        .build()");
        return sleepSessionRecordBuild;
    }

    private static final SleepSessionRecord.Stage toPlatformSleepSessionStage(SleepSessionRecord.Stage stage) {
        hl.a();
        return gl.a(stage.getStartTime(), stage.getEndTime(), IntDefMappingsKt.toPlatformSleepStageType(stage.getStage()));
    }

    private static final android.health.connect.datatypes.SpeedRecord toPlatformSpeedRecord(SpeedRecord speedRecord) {
        android.health.connect.datatypes.Metadata platformMetadata = MetadataConvertersKt.toPlatformMetadata(speedRecord.getMetadata());
        Instant startTime = speedRecord.getStartTime();
        Instant endTime = speedRecord.getEndTime();
        List<SpeedRecord.Sample> samples = speedRecord.getSamples();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(samples, 10));
        Iterator<T> it = samples.iterator();
        while (it.hasNext()) {
            arrayList.add(toPlatformSpeedRecordSample((SpeedRecord.Sample) it.next()));
        }
        SpeedRecord.Builder builderA = sn.a(platformMetadata, startTime, endTime, arrayList);
        ZoneOffset startZoneOffset = speedRecord.getStartZoneOffset();
        if (startZoneOffset != null) {
            builderA.setStartZoneOffset(startZoneOffset);
        }
        ZoneOffset endZoneOffset = speedRecord.getEndZoneOffset();
        if (endZoneOffset != null) {
            builderA.setEndZoneOffset(endZoneOffset);
        }
        android.health.connect.datatypes.SpeedRecord speedRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(speedRecordBuild, "PlatformSpeedRecordBuild…       }\n        .build()");
        return speedRecordBuild;
    }

    private static final SpeedRecord.SpeedRecordSample toPlatformSpeedRecordSample(SpeedRecord.Sample sample) {
        lk.a();
        return kk.a(UnitConvertersKt.toPlatformVelocity(sample.getSpeed()), sample.getTime());
    }

    private static final android.health.connect.datatypes.StepsCadenceRecord toPlatformStepsCadenceRecord(StepsCadenceRecord stepsCadenceRecord) {
        android.health.connect.datatypes.Metadata platformMetadata = MetadataConvertersKt.toPlatformMetadata(stepsCadenceRecord.getMetadata());
        Instant startTime = stepsCadenceRecord.getStartTime();
        Instant endTime = stepsCadenceRecord.getEndTime();
        List<StepsCadenceRecord.Sample> samples = stepsCadenceRecord.getSamples();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(samples, 10));
        Iterator<T> it = samples.iterator();
        while (it.hasNext()) {
            arrayList.add(toPlatformStepsCadenceSample((StepsCadenceRecord.Sample) it.next()));
        }
        StepsCadenceRecord.Builder builderA = on.a(platformMetadata, startTime, endTime, arrayList);
        ZoneOffset startZoneOffset = stepsCadenceRecord.getStartZoneOffset();
        if (startZoneOffset != null) {
            builderA.setStartZoneOffset(startZoneOffset);
        }
        ZoneOffset endZoneOffset = stepsCadenceRecord.getEndZoneOffset();
        if (endZoneOffset != null) {
            builderA.setEndZoneOffset(endZoneOffset);
        }
        android.health.connect.datatypes.StepsCadenceRecord stepsCadenceRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(stepsCadenceRecordBuild, "PlatformStepsCadenceReco…       }\n        .build()");
        return stepsCadenceRecordBuild;
    }

    private static final StepsCadenceRecord.StepsCadenceRecordSample toPlatformStepsCadenceSample(StepsCadenceRecord.Sample sample) {
        nk.a();
        return mk.a(sample.getRate(), sample.getTime());
    }

    private static final android.health.connect.datatypes.StepsRecord toPlatformStepsRecord(StepsRecord stepsRecord) {
        zl.a();
        StepsRecord.Builder builderA = yl.a(MetadataConvertersKt.toPlatformMetadata(stepsRecord.getMetadata()), stepsRecord.getStartTime(), stepsRecord.getEndTime(), stepsRecord.getCount());
        ZoneOffset startZoneOffset = stepsRecord.getStartZoneOffset();
        if (startZoneOffset != null) {
            builderA.setStartZoneOffset(startZoneOffset);
        }
        ZoneOffset endZoneOffset = stepsRecord.getEndZoneOffset();
        if (endZoneOffset != null) {
            builderA.setEndZoneOffset(endZoneOffset);
        }
        android.health.connect.datatypes.StepsRecord stepsRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(stepsRecordBuild, "PlatformStepsRecordBuild…       }\n        .build()");
        return stepsRecordBuild;
    }

    private static final android.health.connect.datatypes.TotalCaloriesBurnedRecord toPlatformTotalCaloriesBurnedRecord(TotalCaloriesBurnedRecord totalCaloriesBurnedRecord) {
        in.a();
        TotalCaloriesBurnedRecord.Builder builderA = hn.a(MetadataConvertersKt.toPlatformMetadata(totalCaloriesBurnedRecord.getMetadata()), totalCaloriesBurnedRecord.getStartTime(), totalCaloriesBurnedRecord.getEndTime(), UnitConvertersKt.toPlatformEnergy(totalCaloriesBurnedRecord.getEnergy()));
        ZoneOffset startZoneOffset = totalCaloriesBurnedRecord.getStartZoneOffset();
        if (startZoneOffset != null) {
            builderA.setStartZoneOffset(startZoneOffset);
        }
        ZoneOffset endZoneOffset = totalCaloriesBurnedRecord.getEndZoneOffset();
        if (endZoneOffset != null) {
            builderA.setEndZoneOffset(endZoneOffset);
        }
        android.health.connect.datatypes.TotalCaloriesBurnedRecord totalCaloriesBurnedRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(totalCaloriesBurnedRecordBuild, "PlatformTotalCaloriesBur…       }\n        .build()");
        return totalCaloriesBurnedRecordBuild;
    }

    private static final android.health.connect.datatypes.Vo2MaxRecord toPlatformVo2MaxRecord(Vo2MaxRecord vo2MaxRecord) {
        jl.a();
        Vo2MaxRecord.Builder builderA = il.a(MetadataConvertersKt.toPlatformMetadata(vo2MaxRecord.getMetadata()), vo2MaxRecord.getTime(), IntDefMappingsKt.toPlatformVo2MaxMeasurementMethod(vo2MaxRecord.getMeasurementMethod()), vo2MaxRecord.getVo2MillilitersPerMinuteKilogram());
        ZoneOffset zoneOffset = vo2MaxRecord.getZoneOffset();
        if (zoneOffset != null) {
            builderA.setZoneOffset(zoneOffset);
        }
        android.health.connect.datatypes.Vo2MaxRecord vo2MaxRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(vo2MaxRecordBuild, "PlatformVo2MaxRecordBuil…(it) } }\n        .build()");
        return vo2MaxRecordBuild;
    }

    private static final android.health.connect.datatypes.WeightRecord toPlatformWeightRecord(WeightRecord weightRecord) {
        hk.a();
        WeightRecord.Builder builderA = gk.a(MetadataConvertersKt.toPlatformMetadata(weightRecord.getMetadata()), weightRecord.getTime(), UnitConvertersKt.toPlatformMass(weightRecord.getWeight()));
        ZoneOffset zoneOffset = weightRecord.getZoneOffset();
        if (zoneOffset != null) {
            builderA.setZoneOffset(zoneOffset);
        }
        android.health.connect.datatypes.WeightRecord weightRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(weightRecordBuild, "PlatformWeightRecordBuil…(it) } }\n        .build()");
        return weightRecordBuild;
    }

    private static final android.health.connect.datatypes.WheelchairPushesRecord toPlatformWheelchairPushesRecord(WheelchairPushesRecord wheelchairPushesRecord) {
        sm.a();
        WheelchairPushesRecord.Builder builderA = rm.a(MetadataConvertersKt.toPlatformMetadata(wheelchairPushesRecord.getMetadata()), wheelchairPushesRecord.getStartTime(), wheelchairPushesRecord.getEndTime(), wheelchairPushesRecord.getCount());
        ZoneOffset startZoneOffset = wheelchairPushesRecord.getStartZoneOffset();
        if (startZoneOffset != null) {
            builderA.setStartZoneOffset(startZoneOffset);
        }
        ZoneOffset endZoneOffset = wheelchairPushesRecord.getEndZoneOffset();
        if (endZoneOffset != null) {
            builderA.setEndZoneOffset(endZoneOffset);
        }
        android.health.connect.datatypes.WheelchairPushesRecord wheelchairPushesRecordBuild = builderA.build();
        Intrinsics.checkNotNullExpressionValue(wheelchairPushesRecordBuild, "PlatformWheelchairPushes…       }\n        .build()");
        return wheelchairPushesRecordBuild;
    }

    private static final androidx.health.connect.client.records.ActiveCaloriesBurnedRecord toSdkActiveCaloriesBurnedRecord(ActiveCaloriesBurnedRecord activeCaloriesBurnedRecord) {
        Instant startTime = activeCaloriesBurnedRecord.getStartTime();
        Intrinsics.checkNotNullExpressionValue(startTime, "startTime");
        ZoneOffset startZoneOffset = activeCaloriesBurnedRecord.getStartZoneOffset();
        Instant endTime = activeCaloriesBurnedRecord.getEndTime();
        Intrinsics.checkNotNullExpressionValue(endTime, "endTime");
        ZoneOffset endZoneOffset = activeCaloriesBurnedRecord.getEndZoneOffset();
        android.health.connect.datatypes.units.Energy energy = activeCaloriesBurnedRecord.getEnergy();
        Intrinsics.checkNotNullExpressionValue(energy, "energy");
        Energy sdkEnergy = UnitConvertersKt.toSdkEnergy(energy);
        android.health.connect.datatypes.Metadata metadata = activeCaloriesBurnedRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.ActiveCaloriesBurnedRecord(startTime, startZoneOffset, endTime, endZoneOffset, sdkEnergy, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.BasalBodyTemperatureRecord toSdkBasalBodyTemperatureRecord(BasalBodyTemperatureRecord basalBodyTemperatureRecord) {
        Instant time = basalBodyTemperatureRecord.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        ZoneOffset zoneOffset = basalBodyTemperatureRecord.getZoneOffset();
        Temperature temperature = basalBodyTemperatureRecord.getTemperature();
        Intrinsics.checkNotNullExpressionValue(temperature, "temperature");
        androidx.health.connect.client.units.Temperature sdkTemperature = UnitConvertersKt.toSdkTemperature(temperature);
        int measurementLocation = basalBodyTemperatureRecord.getMeasurementLocation();
        android.health.connect.datatypes.Metadata metadata = basalBodyTemperatureRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.BasalBodyTemperatureRecord(time, zoneOffset, sdkTemperature, measurementLocation, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.BasalMetabolicRateRecord toSdkBasalMetabolicRateRecord(BasalMetabolicRateRecord basalMetabolicRateRecord) {
        Instant time = basalMetabolicRateRecord.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        ZoneOffset zoneOffset = basalMetabolicRateRecord.getZoneOffset();
        Power basalMetabolicRate = basalMetabolicRateRecord.getBasalMetabolicRate();
        Intrinsics.checkNotNullExpressionValue(basalMetabolicRate, "basalMetabolicRate");
        androidx.health.connect.client.units.Power sdkPower = UnitConvertersKt.toSdkPower(basalMetabolicRate);
        android.health.connect.datatypes.Metadata metadata = basalMetabolicRateRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.BasalMetabolicRateRecord(time, zoneOffset, sdkPower, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.BloodGlucoseRecord toSdkBloodGlucoseRecord(BloodGlucoseRecord bloodGlucoseRecord) {
        Instant time = bloodGlucoseRecord.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        ZoneOffset zoneOffset = bloodGlucoseRecord.getZoneOffset();
        BloodGlucose level = bloodGlucoseRecord.getLevel();
        Intrinsics.checkNotNullExpressionValue(level, "level");
        androidx.health.connect.client.units.BloodGlucose sdkBloodGlucose = UnitConvertersKt.toSdkBloodGlucose(level);
        int sdkBloodGlucoseSpecimenSource = IntDefMappingsKt.toSdkBloodGlucoseSpecimenSource(bloodGlucoseRecord.getSpecimenSource());
        int sdkMealType = IntDefMappingsKt.toSdkMealType(bloodGlucoseRecord.getMealType());
        int sdkRelationToMeal = IntDefMappingsKt.toSdkRelationToMeal(bloodGlucoseRecord.getRelationToMeal());
        android.health.connect.datatypes.Metadata metadata = bloodGlucoseRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.BloodGlucoseRecord(time, zoneOffset, sdkBloodGlucose, sdkBloodGlucoseSpecimenSource, sdkMealType, sdkRelationToMeal, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.BloodPressureRecord toSdkBloodPressureRecord(BloodPressureRecord bloodPressureRecord) {
        Instant time = bloodPressureRecord.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        ZoneOffset zoneOffset = bloodPressureRecord.getZoneOffset();
        Pressure systolic = bloodPressureRecord.getSystolic();
        Intrinsics.checkNotNullExpressionValue(systolic, "systolic");
        androidx.health.connect.client.units.Pressure sdkPressure = UnitConvertersKt.toSdkPressure(systolic);
        Pressure diastolic = bloodPressureRecord.getDiastolic();
        Intrinsics.checkNotNullExpressionValue(diastolic, "diastolic");
        androidx.health.connect.client.units.Pressure sdkPressure2 = UnitConvertersKt.toSdkPressure(diastolic);
        int sdkBloodPressureBodyPosition = IntDefMappingsKt.toSdkBloodPressureBodyPosition(bloodPressureRecord.getBodyPosition());
        int sdkBloodPressureMeasurementLocation = IntDefMappingsKt.toSdkBloodPressureMeasurementLocation(bloodPressureRecord.getMeasurementLocation());
        android.health.connect.datatypes.Metadata metadata = bloodPressureRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.BloodPressureRecord(time, zoneOffset, sdkPressure, sdkPressure2, sdkBloodPressureBodyPosition, sdkBloodPressureMeasurementLocation, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.BodyFatRecord toSdkBodyFatRecord(BodyFatRecord bodyFatRecord) {
        Instant time = bodyFatRecord.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        ZoneOffset zoneOffset = bodyFatRecord.getZoneOffset();
        Percentage percentage = bodyFatRecord.getPercentage();
        Intrinsics.checkNotNullExpressionValue(percentage, "percentage");
        androidx.health.connect.client.units.Percentage sdkPercentage = UnitConvertersKt.toSdkPercentage(percentage);
        android.health.connect.datatypes.Metadata metadata = bodyFatRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.BodyFatRecord(time, zoneOffset, sdkPercentage, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.BodyTemperatureRecord toSdkBodyTemperatureRecord(BodyTemperatureRecord bodyTemperatureRecord) {
        Instant time = bodyTemperatureRecord.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        ZoneOffset zoneOffset = bodyTemperatureRecord.getZoneOffset();
        Temperature temperature = bodyTemperatureRecord.getTemperature();
        Intrinsics.checkNotNullExpressionValue(temperature, "temperature");
        androidx.health.connect.client.units.Temperature sdkTemperature = UnitConvertersKt.toSdkTemperature(temperature);
        int sdkBodyTemperatureMeasurementLocation = IntDefMappingsKt.toSdkBodyTemperatureMeasurementLocation(bodyTemperatureRecord.getMeasurementLocation());
        android.health.connect.datatypes.Metadata metadata = bodyTemperatureRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.BodyTemperatureRecord(time, zoneOffset, sdkTemperature, sdkBodyTemperatureMeasurementLocation, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.BodyWaterMassRecord toSdkBodyWaterMassRecord(BodyWaterMassRecord bodyWaterMassRecord) {
        Instant time = bodyWaterMassRecord.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        ZoneOffset zoneOffset = bodyWaterMassRecord.getZoneOffset();
        android.health.connect.datatypes.units.Mass bodyWaterMass = bodyWaterMassRecord.getBodyWaterMass();
        Intrinsics.checkNotNullExpressionValue(bodyWaterMass, "bodyWaterMass");
        Mass sdkMass = UnitConvertersKt.toSdkMass(bodyWaterMass);
        android.health.connect.datatypes.Metadata metadata = bodyWaterMassRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.BodyWaterMassRecord(time, zoneOffset, sdkMass, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.BoneMassRecord toSdkBoneMassRecord(BoneMassRecord boneMassRecord) {
        Instant time = boneMassRecord.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        ZoneOffset zoneOffset = boneMassRecord.getZoneOffset();
        android.health.connect.datatypes.units.Mass mass = boneMassRecord.getMass();
        Intrinsics.checkNotNullExpressionValue(mass, "mass");
        Mass sdkMass = UnitConvertersKt.toSdkMass(mass);
        android.health.connect.datatypes.Metadata metadata = boneMassRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.BoneMassRecord(time, zoneOffset, sdkMass, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.CervicalMucusRecord toSdkCervicalMucusRecord(CervicalMucusRecord cervicalMucusRecord) {
        Instant time = cervicalMucusRecord.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        ZoneOffset zoneOffset = cervicalMucusRecord.getZoneOffset();
        int sdkCervicalMucusAppearance = IntDefMappingsKt.toSdkCervicalMucusAppearance(cervicalMucusRecord.getAppearance());
        int sdkCervicalMucusSensation = IntDefMappingsKt.toSdkCervicalMucusSensation(cervicalMucusRecord.getSensation());
        android.health.connect.datatypes.Metadata metadata = cervicalMucusRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.CervicalMucusRecord(time, zoneOffset, sdkCervicalMucusAppearance, sdkCervicalMucusSensation, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.CyclingPedalingCadenceRecord toSdkCyclingPedalingCadenceRecord(android.health.connect.datatypes.CyclingPedalingCadenceRecord cyclingPedalingCadenceRecord) {
        Instant startTime = cyclingPedalingCadenceRecord.getStartTime();
        Intrinsics.checkNotNullExpressionValue(startTime, "startTime");
        ZoneOffset startZoneOffset = cyclingPedalingCadenceRecord.getStartZoneOffset();
        Instant endTime = cyclingPedalingCadenceRecord.getEndTime();
        Intrinsics.checkNotNullExpressionValue(endTime, "endTime");
        ZoneOffset endZoneOffset = cyclingPedalingCadenceRecord.getEndZoneOffset();
        List samples = cyclingPedalingCadenceRecord.getSamples();
        Intrinsics.checkNotNullExpressionValue(samples, "samples");
        List list = samples;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            CyclingPedalingCadenceRecord.CyclingPedalingCadenceRecordSample it2 = i8.a(it.next());
            Intrinsics.checkNotNullExpressionValue(it2, "it");
            arrayList.add(toSdkCyclingPedalingCadenceSample(it2));
        }
        List listSortedWith = CollectionsKt.sortedWith(arrayList, new Comparator() { // from class: androidx.health.connect.client.impl.platform.records.RecordConvertersKt$toSdkCyclingPedalingCadenceRecord$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t2, T t3) {
                return ComparisonsKt.compareValues(((CyclingPedalingCadenceRecord.Sample) t2).getTime(), ((CyclingPedalingCadenceRecord.Sample) t3).getTime());
            }
        });
        android.health.connect.datatypes.Metadata metadata = cyclingPedalingCadenceRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.CyclingPedalingCadenceRecord(startTime, startZoneOffset, endTime, endZoneOffset, listSortedWith, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final CyclingPedalingCadenceRecord.Sample toSdkCyclingPedalingCadenceSample(CyclingPedalingCadenceRecord.CyclingPedalingCadenceRecordSample cyclingPedalingCadenceRecordSample) {
        Instant time = cyclingPedalingCadenceRecordSample.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        return new CyclingPedalingCadenceRecord.Sample(time, cyclingPedalingCadenceRecordSample.getRevolutionsPerMinute());
    }

    private static final androidx.health.connect.client.records.DistanceRecord toSdkDistanceRecord(DistanceRecord distanceRecord) {
        Instant startTime = distanceRecord.getStartTime();
        Intrinsics.checkNotNullExpressionValue(startTime, "startTime");
        ZoneOffset startZoneOffset = distanceRecord.getStartZoneOffset();
        Instant endTime = distanceRecord.getEndTime();
        Intrinsics.checkNotNullExpressionValue(endTime, "endTime");
        ZoneOffset endZoneOffset = distanceRecord.getEndZoneOffset();
        android.health.connect.datatypes.units.Length distance = distanceRecord.getDistance();
        Intrinsics.checkNotNullExpressionValue(distance, "distance");
        Length sdkLength = UnitConvertersKt.toSdkLength(distance);
        android.health.connect.datatypes.Metadata metadata = distanceRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.DistanceRecord(startTime, startZoneOffset, endTime, endZoneOffset, sdkLength, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.ElevationGainedRecord toSdkElevationGainedRecord(ElevationGainedRecord elevationGainedRecord) {
        Instant startTime = elevationGainedRecord.getStartTime();
        Intrinsics.checkNotNullExpressionValue(startTime, "startTime");
        ZoneOffset startZoneOffset = elevationGainedRecord.getStartZoneOffset();
        Instant endTime = elevationGainedRecord.getEndTime();
        Intrinsics.checkNotNullExpressionValue(endTime, "endTime");
        ZoneOffset endZoneOffset = elevationGainedRecord.getEndZoneOffset();
        android.health.connect.datatypes.units.Length elevation = elevationGainedRecord.getElevation();
        Intrinsics.checkNotNullExpressionValue(elevation, "elevation");
        Length sdkLength = UnitConvertersKt.toSdkLength(elevation);
        android.health.connect.datatypes.Metadata metadata = elevationGainedRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.ElevationGainedRecord(startTime, startZoneOffset, endTime, endZoneOffset, sdkLength, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    @NotNull
    public static final androidx.health.connect.client.records.ExerciseLap toSdkExerciseLap(@NotNull ExerciseLap exerciseLap) {
        Intrinsics.checkNotNullParameter(exerciseLap, "<this>");
        Instant startTime = exerciseLap.getStartTime();
        Intrinsics.checkNotNullExpressionValue(startTime, "startTime");
        Instant endTime = exerciseLap.getEndTime();
        Intrinsics.checkNotNullExpressionValue(endTime, "endTime");
        android.health.connect.datatypes.units.Length length = exerciseLap.getLength();
        return new androidx.health.connect.client.records.ExerciseLap(startTime, endTime, length != null ? UnitConvertersKt.toSdkLength(length) : null);
    }

    @NotNull
    public static final androidx.health.connect.client.records.ExerciseRoute toSdkExerciseRoute(@NotNull android.health.connect.datatypes.ExerciseRoute exerciseRoute) {
        Length sdkLength;
        Length sdkLength2;
        Length sdkLength3;
        Intrinsics.checkNotNullParameter(exerciseRoute, "<this>");
        List routeLocations = exerciseRoute.getRouteLocations();
        Intrinsics.checkNotNullExpressionValue(routeLocations, "routeLocations");
        List list = routeLocations;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ExerciseRoute.Location locationA = jf.a(it.next());
            Instant time = locationA.getTime();
            Intrinsics.checkNotNullExpressionValue(time, "value.time");
            double latitude = locationA.getLatitude();
            double longitude = locationA.getLongitude();
            android.health.connect.datatypes.units.Length horizontalAccuracy = locationA.getHorizontalAccuracy();
            if (horizontalAccuracy != null) {
                Intrinsics.checkNotNullExpressionValue(horizontalAccuracy, "horizontalAccuracy");
                sdkLength = UnitConvertersKt.toSdkLength(horizontalAccuracy);
            } else {
                sdkLength = null;
            }
            android.health.connect.datatypes.units.Length verticalAccuracy = locationA.getVerticalAccuracy();
            if (verticalAccuracy != null) {
                Intrinsics.checkNotNullExpressionValue(verticalAccuracy, "verticalAccuracy");
                sdkLength2 = UnitConvertersKt.toSdkLength(verticalAccuracy);
            } else {
                sdkLength2 = null;
            }
            android.health.connect.datatypes.units.Length altitude = locationA.getAltitude();
            if (altitude != null) {
                Intrinsics.checkNotNullExpressionValue(altitude, "altitude");
                sdkLength3 = UnitConvertersKt.toSdkLength(altitude);
            } else {
                sdkLength3 = null;
            }
            arrayList.add(new ExerciseRoute.Location(time, latitude, longitude, sdkLength, sdkLength2, sdkLength3));
        }
        return new androidx.health.connect.client.records.ExerciseRoute(arrayList);
    }

    @NotNull
    public static final androidx.health.connect.client.records.ExerciseSegment toSdkExerciseSegment(@NotNull ExerciseSegment exerciseSegment) {
        Intrinsics.checkNotNullParameter(exerciseSegment, "<this>");
        Instant startTime = exerciseSegment.getStartTime();
        Intrinsics.checkNotNullExpressionValue(startTime, "startTime");
        Instant endTime = exerciseSegment.getEndTime();
        Intrinsics.checkNotNullExpressionValue(endTime, "endTime");
        return new androidx.health.connect.client.records.ExerciseSegment(startTime, endTime, IntDefMappingsKt.toSdkExerciseSegmentType(exerciseSegment.getSegmentType()), exerciseSegment.getRepetitionsCount());
    }

    private static final androidx.health.connect.client.records.ExerciseSessionRecord toSdkExerciseSessionRecord(ExerciseSessionRecord exerciseSessionRecord) {
        Instant startTime = exerciseSessionRecord.getStartTime();
        ZoneOffset startZoneOffset = exerciseSessionRecord.getStartZoneOffset();
        Instant endTime = exerciseSessionRecord.getEndTime();
        ZoneOffset endZoneOffset = exerciseSessionRecord.getEndZoneOffset();
        int sdkExerciseSessionType = IntDefMappingsKt.toSdkExerciseSessionType(exerciseSessionRecord.getExerciseType());
        CharSequence title = exerciseSessionRecord.getTitle();
        String string = title != null ? title.toString() : null;
        CharSequence notes = exerciseSessionRecord.getNotes();
        String string2 = notes != null ? notes.toString() : null;
        List laps = exerciseSessionRecord.getLaps();
        Intrinsics.checkNotNullExpressionValue(laps, "laps");
        List list = laps;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ExerciseLap it2 = ue.a(it.next());
            Intrinsics.checkNotNullExpressionValue(it2, "it");
            arrayList.add(toSdkExerciseLap(it2));
        }
        List listSortedWith = CollectionsKt.sortedWith(arrayList, new Comparator() { // from class: androidx.health.connect.client.impl.platform.records.RecordConvertersKt$toSdkExerciseSessionRecord$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t2, T t3) {
                return ComparisonsKt.compareValues(((androidx.health.connect.client.records.ExerciseLap) t2).getStartTime(), ((androidx.health.connect.client.records.ExerciseLap) t3).getStartTime());
            }
        });
        List segments = exerciseSessionRecord.getSegments();
        Intrinsics.checkNotNullExpressionValue(segments, "segments");
        List list2 = segments;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator it3 = list2.iterator();
        while (it3.hasNext()) {
            ExerciseSegment it4 = ie.a(it3.next());
            Intrinsics.checkNotNullExpressionValue(it4, "it");
            arrayList2.add(toSdkExerciseSegment(it4));
        }
        List listSortedWith2 = CollectionsKt.sortedWith(arrayList2, new Comparator() { // from class: androidx.health.connect.client.impl.platform.records.RecordConvertersKt$toSdkExerciseSessionRecord$$inlined$sortedBy$2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t2, T t3) {
                return ComparisonsKt.compareValues(((androidx.health.connect.client.records.ExerciseSegment) t2).getStartTime(), ((androidx.health.connect.client.records.ExerciseSegment) t3).getStartTime());
            }
        });
        android.health.connect.datatypes.Metadata metadata = exerciseSessionRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        androidx.health.connect.client.records.metadata.Metadata sdkMetadata = MetadataConvertersKt.toSdkMetadata(metadata);
        android.health.connect.datatypes.ExerciseRoute route = exerciseSessionRecord.getRoute();
        ExerciseRouteResult data = route != null ? new ExerciseRouteResult.Data(toSdkExerciseRoute(route)) : exerciseSessionRecord.hasRoute() ? new ExerciseRouteResult.ConsentRequired() : new ExerciseRouteResult.NoData();
        Intrinsics.checkNotNullExpressionValue(startTime, "startTime");
        Intrinsics.checkNotNullExpressionValue(endTime, "endTime");
        return new androidx.health.connect.client.records.ExerciseSessionRecord(startTime, startZoneOffset, endTime, endZoneOffset, sdkExerciseSessionType, string, string2, sdkMetadata, (List<androidx.health.connect.client.records.ExerciseSegment>) listSortedWith2, (List<androidx.health.connect.client.records.ExerciseLap>) listSortedWith, data);
    }

    private static final androidx.health.connect.client.records.FloorsClimbedRecord toSdkFloorsClimbedRecord(FloorsClimbedRecord floorsClimbedRecord) {
        Instant startTime = floorsClimbedRecord.getStartTime();
        Intrinsics.checkNotNullExpressionValue(startTime, "startTime");
        ZoneOffset startZoneOffset = floorsClimbedRecord.getStartZoneOffset();
        Instant endTime = floorsClimbedRecord.getEndTime();
        Intrinsics.checkNotNullExpressionValue(endTime, "endTime");
        ZoneOffset endZoneOffset = floorsClimbedRecord.getEndZoneOffset();
        double floors = floorsClimbedRecord.getFloors();
        android.health.connect.datatypes.Metadata metadata = floorsClimbedRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.FloorsClimbedRecord(startTime, startZoneOffset, endTime, endZoneOffset, floors, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.HeartRateRecord toSdkHeartRateRecord(android.health.connect.datatypes.HeartRateRecord heartRateRecord) {
        Instant startTime = heartRateRecord.getStartTime();
        Intrinsics.checkNotNullExpressionValue(startTime, "startTime");
        ZoneOffset startZoneOffset = heartRateRecord.getStartZoneOffset();
        Instant endTime = heartRateRecord.getEndTime();
        Intrinsics.checkNotNullExpressionValue(endTime, "endTime");
        ZoneOffset endZoneOffset = heartRateRecord.getEndZoneOffset();
        List samples = heartRateRecord.getSamples();
        Intrinsics.checkNotNullExpressionValue(samples, "samples");
        List list = samples;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            HeartRateRecord.HeartRateSample it2 = hh.a(it.next());
            Intrinsics.checkNotNullExpressionValue(it2, "it");
            arrayList.add(toSdkHeartRateSample(it2));
        }
        List listSortedWith = CollectionsKt.sortedWith(arrayList, new Comparator() { // from class: androidx.health.connect.client.impl.platform.records.RecordConvertersKt$toSdkHeartRateRecord$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t2, T t3) {
                return ComparisonsKt.compareValues(((HeartRateRecord.Sample) t2).getTime(), ((HeartRateRecord.Sample) t3).getTime());
            }
        });
        android.health.connect.datatypes.Metadata metadata = heartRateRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.HeartRateRecord(startTime, startZoneOffset, endTime, endZoneOffset, listSortedWith, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final HeartRateRecord.Sample toSdkHeartRateSample(HeartRateRecord.HeartRateSample heartRateSample) {
        Instant time = heartRateSample.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        return new HeartRateRecord.Sample(time, heartRateSample.getBeatsPerMinute());
    }

    private static final androidx.health.connect.client.records.HeartRateVariabilityRmssdRecord toSdkHeartRateVariabilityRmssdRecord(HeartRateVariabilityRmssdRecord heartRateVariabilityRmssdRecord) {
        Instant time = heartRateVariabilityRmssdRecord.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        ZoneOffset zoneOffset = heartRateVariabilityRmssdRecord.getZoneOffset();
        double heartRateVariabilityMillis = heartRateVariabilityRmssdRecord.getHeartRateVariabilityMillis();
        android.health.connect.datatypes.Metadata metadata = heartRateVariabilityRmssdRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.HeartRateVariabilityRmssdRecord(time, zoneOffset, heartRateVariabilityMillis, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.HeightRecord toSdkHeightRecord(HeightRecord heightRecord) {
        Instant time = heightRecord.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        ZoneOffset zoneOffset = heightRecord.getZoneOffset();
        android.health.connect.datatypes.units.Length height = heightRecord.getHeight();
        Intrinsics.checkNotNullExpressionValue(height, "height");
        Length sdkLength = UnitConvertersKt.toSdkLength(height);
        android.health.connect.datatypes.Metadata metadata = heightRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.HeightRecord(time, zoneOffset, sdkLength, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.HydrationRecord toSdkHydrationRecord(HydrationRecord hydrationRecord) {
        Instant startTime = hydrationRecord.getStartTime();
        Intrinsics.checkNotNullExpressionValue(startTime, "startTime");
        ZoneOffset startZoneOffset = hydrationRecord.getStartZoneOffset();
        Instant endTime = hydrationRecord.getEndTime();
        Intrinsics.checkNotNullExpressionValue(endTime, "endTime");
        ZoneOffset endZoneOffset = hydrationRecord.getEndZoneOffset();
        Volume volume = hydrationRecord.getVolume();
        Intrinsics.checkNotNullExpressionValue(volume, "volume");
        androidx.health.connect.client.units.Volume sdkVolume = UnitConvertersKt.toSdkVolume(volume);
        android.health.connect.datatypes.Metadata metadata = hydrationRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.HydrationRecord(startTime, startZoneOffset, endTime, endZoneOffset, sdkVolume, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.IntermenstrualBleedingRecord toSdkIntermenstrualBleedingRecord(IntermenstrualBleedingRecord intermenstrualBleedingRecord) {
        Instant time = intermenstrualBleedingRecord.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        ZoneOffset zoneOffset = intermenstrualBleedingRecord.getZoneOffset();
        android.health.connect.datatypes.Metadata metadata = intermenstrualBleedingRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.IntermenstrualBleedingRecord(time, zoneOffset, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.LeanBodyMassRecord toSdkLeanBodyMassRecord(LeanBodyMassRecord leanBodyMassRecord) {
        Instant time = leanBodyMassRecord.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        ZoneOffset zoneOffset = leanBodyMassRecord.getZoneOffset();
        android.health.connect.datatypes.units.Mass mass = leanBodyMassRecord.getMass();
        Intrinsics.checkNotNullExpressionValue(mass, "mass");
        Mass sdkMass = UnitConvertersKt.toSdkMass(mass);
        android.health.connect.datatypes.Metadata metadata = leanBodyMassRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.LeanBodyMassRecord(time, zoneOffset, sdkMass, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.MenstruationFlowRecord toSdkMenstruationFlowRecord(MenstruationFlowRecord menstruationFlowRecord) {
        Instant time = menstruationFlowRecord.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        ZoneOffset zoneOffset = menstruationFlowRecord.getZoneOffset();
        int sdkMenstruationFlow = IntDefMappingsKt.toSdkMenstruationFlow(menstruationFlowRecord.getFlow());
        android.health.connect.datatypes.Metadata metadata = menstruationFlowRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.MenstruationFlowRecord(time, zoneOffset, sdkMenstruationFlow, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.MenstruationPeriodRecord toSdkMenstruationPeriodRecord(MenstruationPeriodRecord menstruationPeriodRecord) {
        Instant startTime = menstruationPeriodRecord.getStartTime();
        Intrinsics.checkNotNullExpressionValue(startTime, "startTime");
        ZoneOffset startZoneOffset = menstruationPeriodRecord.getStartZoneOffset();
        Instant endTime = menstruationPeriodRecord.getEndTime();
        Intrinsics.checkNotNullExpressionValue(endTime, "endTime");
        ZoneOffset endZoneOffset = menstruationPeriodRecord.getEndZoneOffset();
        android.health.connect.datatypes.Metadata metadata = menstruationPeriodRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.MenstruationPeriodRecord(startTime, startZoneOffset, endTime, endZoneOffset, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.NutritionRecord toSdkNutritionRecord(NutritionRecord nutritionRecord) {
        Instant startTime = nutritionRecord.getStartTime();
        ZoneOffset startZoneOffset = nutritionRecord.getStartZoneOffset();
        Instant endTime = nutritionRecord.getEndTime();
        ZoneOffset endZoneOffset = nutritionRecord.getEndZoneOffset();
        String mealName = nutritionRecord.getMealName();
        int sdkMealType = IntDefMappingsKt.toSdkMealType(nutritionRecord.getMealType());
        android.health.connect.datatypes.Metadata metadata = nutritionRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        androidx.health.connect.client.records.metadata.Metadata sdkMetadata = MetadataConvertersKt.toSdkMetadata(metadata);
        android.health.connect.datatypes.units.Mass biotin = nutritionRecord.getBiotin();
        Mass nonDefaultSdkMass = biotin != null ? UnitConvertersKt.toNonDefaultSdkMass(biotin) : null;
        android.health.connect.datatypes.units.Mass caffeine = nutritionRecord.getCaffeine();
        Mass nonDefaultSdkMass2 = caffeine != null ? UnitConvertersKt.toNonDefaultSdkMass(caffeine) : null;
        android.health.connect.datatypes.units.Mass calcium = nutritionRecord.getCalcium();
        Mass nonDefaultSdkMass3 = calcium != null ? UnitConvertersKt.toNonDefaultSdkMass(calcium) : null;
        android.health.connect.datatypes.units.Energy energy = nutritionRecord.getEnergy();
        Energy nonDefaultSdkEnergy = energy != null ? UnitConvertersKt.toNonDefaultSdkEnergy(energy) : null;
        android.health.connect.datatypes.units.Energy energyFromFat = nutritionRecord.getEnergyFromFat();
        Energy nonDefaultSdkEnergy2 = energyFromFat != null ? UnitConvertersKt.toNonDefaultSdkEnergy(energyFromFat) : null;
        android.health.connect.datatypes.units.Mass chloride = nutritionRecord.getChloride();
        Mass nonDefaultSdkMass4 = chloride != null ? UnitConvertersKt.toNonDefaultSdkMass(chloride) : null;
        android.health.connect.datatypes.units.Mass cholesterol = nutritionRecord.getCholesterol();
        Mass nonDefaultSdkMass5 = cholesterol != null ? UnitConvertersKt.toNonDefaultSdkMass(cholesterol) : null;
        android.health.connect.datatypes.units.Mass chromium = nutritionRecord.getChromium();
        Mass nonDefaultSdkMass6 = chromium != null ? UnitConvertersKt.toNonDefaultSdkMass(chromium) : null;
        android.health.connect.datatypes.units.Mass copper = nutritionRecord.getCopper();
        Mass nonDefaultSdkMass7 = copper != null ? UnitConvertersKt.toNonDefaultSdkMass(copper) : null;
        android.health.connect.datatypes.units.Mass dietaryFiber = nutritionRecord.getDietaryFiber();
        Mass nonDefaultSdkMass8 = dietaryFiber != null ? UnitConvertersKt.toNonDefaultSdkMass(dietaryFiber) : null;
        android.health.connect.datatypes.units.Mass folate = nutritionRecord.getFolate();
        Mass nonDefaultSdkMass9 = folate != null ? UnitConvertersKt.toNonDefaultSdkMass(folate) : null;
        android.health.connect.datatypes.units.Mass folicAcid = nutritionRecord.getFolicAcid();
        Mass nonDefaultSdkMass10 = folicAcid != null ? UnitConvertersKt.toNonDefaultSdkMass(folicAcid) : null;
        android.health.connect.datatypes.units.Mass iodine = nutritionRecord.getIodine();
        Mass nonDefaultSdkMass11 = iodine != null ? UnitConvertersKt.toNonDefaultSdkMass(iodine) : null;
        android.health.connect.datatypes.units.Mass iron = nutritionRecord.getIron();
        Mass nonDefaultSdkMass12 = iron != null ? UnitConvertersKt.toNonDefaultSdkMass(iron) : null;
        android.health.connect.datatypes.units.Mass magnesium = nutritionRecord.getMagnesium();
        Mass nonDefaultSdkMass13 = magnesium != null ? UnitConvertersKt.toNonDefaultSdkMass(magnesium) : null;
        android.health.connect.datatypes.units.Mass manganese = nutritionRecord.getManganese();
        Mass nonDefaultSdkMass14 = manganese != null ? UnitConvertersKt.toNonDefaultSdkMass(manganese) : null;
        android.health.connect.datatypes.units.Mass molybdenum = nutritionRecord.getMolybdenum();
        Mass nonDefaultSdkMass15 = molybdenum != null ? UnitConvertersKt.toNonDefaultSdkMass(molybdenum) : null;
        android.health.connect.datatypes.units.Mass monounsaturatedFat = nutritionRecord.getMonounsaturatedFat();
        Mass nonDefaultSdkMass16 = monounsaturatedFat != null ? UnitConvertersKt.toNonDefaultSdkMass(monounsaturatedFat) : null;
        android.health.connect.datatypes.units.Mass niacin = nutritionRecord.getNiacin();
        Mass nonDefaultSdkMass17 = niacin != null ? UnitConvertersKt.toNonDefaultSdkMass(niacin) : null;
        android.health.connect.datatypes.units.Mass pantothenicAcid = nutritionRecord.getPantothenicAcid();
        Mass nonDefaultSdkMass18 = pantothenicAcid != null ? UnitConvertersKt.toNonDefaultSdkMass(pantothenicAcid) : null;
        android.health.connect.datatypes.units.Mass phosphorus = nutritionRecord.getPhosphorus();
        Mass nonDefaultSdkMass19 = phosphorus != null ? UnitConvertersKt.toNonDefaultSdkMass(phosphorus) : null;
        android.health.connect.datatypes.units.Mass polyunsaturatedFat = nutritionRecord.getPolyunsaturatedFat();
        Mass nonDefaultSdkMass20 = polyunsaturatedFat != null ? UnitConvertersKt.toNonDefaultSdkMass(polyunsaturatedFat) : null;
        android.health.connect.datatypes.units.Mass potassium = nutritionRecord.getPotassium();
        Mass nonDefaultSdkMass21 = potassium != null ? UnitConvertersKt.toNonDefaultSdkMass(potassium) : null;
        android.health.connect.datatypes.units.Mass protein = nutritionRecord.getProtein();
        Mass nonDefaultSdkMass22 = protein != null ? UnitConvertersKt.toNonDefaultSdkMass(protein) : null;
        android.health.connect.datatypes.units.Mass riboflavin = nutritionRecord.getRiboflavin();
        Mass nonDefaultSdkMass23 = riboflavin != null ? UnitConvertersKt.toNonDefaultSdkMass(riboflavin) : null;
        android.health.connect.datatypes.units.Mass saturatedFat = nutritionRecord.getSaturatedFat();
        Mass nonDefaultSdkMass24 = saturatedFat != null ? UnitConvertersKt.toNonDefaultSdkMass(saturatedFat) : null;
        android.health.connect.datatypes.units.Mass selenium = nutritionRecord.getSelenium();
        Mass nonDefaultSdkMass25 = selenium != null ? UnitConvertersKt.toNonDefaultSdkMass(selenium) : null;
        android.health.connect.datatypes.units.Mass sodium = nutritionRecord.getSodium();
        Mass nonDefaultSdkMass26 = sodium != null ? UnitConvertersKt.toNonDefaultSdkMass(sodium) : null;
        android.health.connect.datatypes.units.Mass sugar = nutritionRecord.getSugar();
        Mass nonDefaultSdkMass27 = sugar != null ? UnitConvertersKt.toNonDefaultSdkMass(sugar) : null;
        android.health.connect.datatypes.units.Mass thiamin = nutritionRecord.getThiamin();
        Mass nonDefaultSdkMass28 = thiamin != null ? UnitConvertersKt.toNonDefaultSdkMass(thiamin) : null;
        android.health.connect.datatypes.units.Mass totalCarbohydrate = nutritionRecord.getTotalCarbohydrate();
        Mass nonDefaultSdkMass29 = totalCarbohydrate != null ? UnitConvertersKt.toNonDefaultSdkMass(totalCarbohydrate) : null;
        android.health.connect.datatypes.units.Mass totalFat = nutritionRecord.getTotalFat();
        Mass nonDefaultSdkMass30 = totalFat != null ? UnitConvertersKt.toNonDefaultSdkMass(totalFat) : null;
        android.health.connect.datatypes.units.Mass transFat = nutritionRecord.getTransFat();
        Mass nonDefaultSdkMass31 = transFat != null ? UnitConvertersKt.toNonDefaultSdkMass(transFat) : null;
        android.health.connect.datatypes.units.Mass unsaturatedFat = nutritionRecord.getUnsaturatedFat();
        Mass nonDefaultSdkMass32 = unsaturatedFat != null ? UnitConvertersKt.toNonDefaultSdkMass(unsaturatedFat) : null;
        android.health.connect.datatypes.units.Mass vitaminA = nutritionRecord.getVitaminA();
        Mass nonDefaultSdkMass33 = vitaminA != null ? UnitConvertersKt.toNonDefaultSdkMass(vitaminA) : null;
        android.health.connect.datatypes.units.Mass vitaminB12 = nutritionRecord.getVitaminB12();
        Mass nonDefaultSdkMass34 = vitaminB12 != null ? UnitConvertersKt.toNonDefaultSdkMass(vitaminB12) : null;
        android.health.connect.datatypes.units.Mass vitaminB6 = nutritionRecord.getVitaminB6();
        Mass nonDefaultSdkMass35 = vitaminB6 != null ? UnitConvertersKt.toNonDefaultSdkMass(vitaminB6) : null;
        android.health.connect.datatypes.units.Mass vitaminC = nutritionRecord.getVitaminC();
        Mass nonDefaultSdkMass36 = vitaminC != null ? UnitConvertersKt.toNonDefaultSdkMass(vitaminC) : null;
        android.health.connect.datatypes.units.Mass vitaminD = nutritionRecord.getVitaminD();
        Mass nonDefaultSdkMass37 = vitaminD != null ? UnitConvertersKt.toNonDefaultSdkMass(vitaminD) : null;
        android.health.connect.datatypes.units.Mass vitaminE = nutritionRecord.getVitaminE();
        Mass nonDefaultSdkMass38 = vitaminE != null ? UnitConvertersKt.toNonDefaultSdkMass(vitaminE) : null;
        android.health.connect.datatypes.units.Mass vitaminK = nutritionRecord.getVitaminK();
        Mass nonDefaultSdkMass39 = vitaminK != null ? UnitConvertersKt.toNonDefaultSdkMass(vitaminK) : null;
        android.health.connect.datatypes.units.Mass zinc = nutritionRecord.getZinc();
        Mass nonDefaultSdkMass40 = zinc != null ? UnitConvertersKt.toNonDefaultSdkMass(zinc) : null;
        Intrinsics.checkNotNullExpressionValue(startTime, "startTime");
        Intrinsics.checkNotNullExpressionValue(endTime, "endTime");
        return new androidx.health.connect.client.records.NutritionRecord(startTime, startZoneOffset, endTime, endZoneOffset, nonDefaultSdkMass, nonDefaultSdkMass2, nonDefaultSdkMass3, nonDefaultSdkEnergy, nonDefaultSdkEnergy2, nonDefaultSdkMass4, nonDefaultSdkMass5, nonDefaultSdkMass6, nonDefaultSdkMass7, nonDefaultSdkMass8, nonDefaultSdkMass9, nonDefaultSdkMass10, nonDefaultSdkMass11, nonDefaultSdkMass12, nonDefaultSdkMass13, nonDefaultSdkMass14, nonDefaultSdkMass15, nonDefaultSdkMass16, nonDefaultSdkMass17, nonDefaultSdkMass18, nonDefaultSdkMass19, nonDefaultSdkMass20, nonDefaultSdkMass21, nonDefaultSdkMass22, nonDefaultSdkMass23, nonDefaultSdkMass24, nonDefaultSdkMass25, nonDefaultSdkMass26, nonDefaultSdkMass27, nonDefaultSdkMass28, nonDefaultSdkMass29, nonDefaultSdkMass30, nonDefaultSdkMass31, nonDefaultSdkMass32, nonDefaultSdkMass33, nonDefaultSdkMass34, nonDefaultSdkMass35, nonDefaultSdkMass36, nonDefaultSdkMass37, nonDefaultSdkMass38, nonDefaultSdkMass39, nonDefaultSdkMass40, mealName, sdkMealType, sdkMetadata);
    }

    private static final androidx.health.connect.client.records.OvulationTestRecord toSdkOvulationTestRecord(OvulationTestRecord ovulationTestRecord) {
        Instant time = ovulationTestRecord.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        ZoneOffset zoneOffset = ovulationTestRecord.getZoneOffset();
        int sdkOvulationTestResult = IntDefMappingsKt.toSdkOvulationTestResult(ovulationTestRecord.getResult());
        android.health.connect.datatypes.Metadata metadata = ovulationTestRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.OvulationTestRecord(time, zoneOffset, sdkOvulationTestResult, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.OxygenSaturationRecord toSdkOxygenSaturationRecord(OxygenSaturationRecord oxygenSaturationRecord) {
        Instant time = oxygenSaturationRecord.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        ZoneOffset zoneOffset = oxygenSaturationRecord.getZoneOffset();
        Percentage percentage = oxygenSaturationRecord.getPercentage();
        Intrinsics.checkNotNullExpressionValue(percentage, "percentage");
        androidx.health.connect.client.units.Percentage sdkPercentage = UnitConvertersKt.toSdkPercentage(percentage);
        android.health.connect.datatypes.Metadata metadata = oxygenSaturationRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.OxygenSaturationRecord(time, zoneOffset, sdkPercentage, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.PowerRecord toSdkPowerRecord(android.health.connect.datatypes.PowerRecord powerRecord) {
        Instant startTime = powerRecord.getStartTime();
        Intrinsics.checkNotNullExpressionValue(startTime, "startTime");
        ZoneOffset startZoneOffset = powerRecord.getStartZoneOffset();
        Instant endTime = powerRecord.getEndTime();
        Intrinsics.checkNotNullExpressionValue(endTime, "endTime");
        ZoneOffset endZoneOffset = powerRecord.getEndZoneOffset();
        List samples = powerRecord.getSamples();
        Intrinsics.checkNotNullExpressionValue(samples, "samples");
        List list = samples;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            PowerRecord.PowerRecordSample it2 = si.a(it.next());
            Intrinsics.checkNotNullExpressionValue(it2, "it");
            arrayList.add(toSdkPowerRecordSample(it2));
        }
        List listSortedWith = CollectionsKt.sortedWith(arrayList, new Comparator() { // from class: androidx.health.connect.client.impl.platform.records.RecordConvertersKt$toSdkPowerRecord$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t2, T t3) {
                return ComparisonsKt.compareValues(((PowerRecord.Sample) t2).getTime(), ((PowerRecord.Sample) t3).getTime());
            }
        });
        android.health.connect.datatypes.Metadata metadata = powerRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.PowerRecord(startTime, startZoneOffset, endTime, endZoneOffset, listSortedWith, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final PowerRecord.Sample toSdkPowerRecordSample(PowerRecord.PowerRecordSample powerRecordSample) {
        Instant time = powerRecordSample.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        Power power = powerRecordSample.getPower();
        Intrinsics.checkNotNullExpressionValue(power, "power");
        return new PowerRecord.Sample(time, UnitConvertersKt.toSdkPower(power));
    }

    @NotNull
    public static final androidx.health.connect.client.records.Record toSdkRecord(@NotNull Record record) {
        Intrinsics.checkNotNullParameter(record, "<this>");
        if (k8.a(record)) {
            return toSdkActiveCaloriesBurnedRecord(w8.a(record));
        }
        if (i9.a(record)) {
            return toSdkBasalBodyTemperatureRecord(u9.a(record));
        }
        if (ga.a(record)) {
            return toSdkBasalMetabolicRateRecord(sa.a(record));
        }
        if (eb.a(record)) {
            return toSdkBloodGlucoseRecord(nb.a(record));
        }
        if (ob.a(record)) {
            return toSdkBloodPressureRecord(pb.a(record));
        }
        if (l8.a(record)) {
            return toSdkBodyFatRecord(m8.a(record));
        }
        if (n8.a(record)) {
            return toSdkBodyTemperatureRecord(o8.a(record));
        }
        if (p8.a(record)) {
            return toSdkBodyWaterMassRecord(q8.a(record));
        }
        if (s8.a(record)) {
            return toSdkBoneMassRecord(t8.a(record));
        }
        if (u8.a(record)) {
            return toSdkCervicalMucusRecord(v8.a(record));
        }
        if (x8.a(record)) {
            return toSdkCyclingPedalingCadenceRecord(y8.a(record));
        }
        if (z8.a(record)) {
            return toSdkDistanceRecord(a9.a(record));
        }
        if (b9.a(record)) {
            return toSdkElevationGainedRecord(d9.a(record));
        }
        if (e9.a(record)) {
            return toSdkExerciseSessionRecord(f9.a(record));
        }
        if (g9.a(record)) {
            return toSdkFloorsClimbedRecord(h9.a(record));
        }
        if (j9.a(record)) {
            return toSdkHeartRateRecord(k9.a(record));
        }
        if (l9.a(record)) {
            return toSdkHeartRateVariabilityRmssdRecord(m9.a(record));
        }
        if (o9.a(record)) {
            return toSdkHeightRecord(p9.a(record));
        }
        if (q9.a(record)) {
            return toSdkHydrationRecord(r9.a(record));
        }
        if (s9.a(record)) {
            return toSdkIntermenstrualBleedingRecord(t9.a(record));
        }
        if (v9.a(record)) {
            return toSdkLeanBodyMassRecord(w9.a(record));
        }
        if (x9.a(record)) {
            return toSdkMenstruationFlowRecord(z9.a(record));
        }
        if (aa.a(record)) {
            return toSdkMenstruationPeriodRecord(ba.a(record));
        }
        if (ca.a(record)) {
            return toSdkNutritionRecord(da.a(record));
        }
        if (ea.a(record)) {
            return toSdkOvulationTestRecord(fa.a(record));
        }
        if (ha.a(record)) {
            return toSdkOxygenSaturationRecord(ia.a(record));
        }
        if (ka.a(record)) {
            return toSdkPowerRecord(la.a(record));
        }
        if (ma.a(record)) {
            return toSdkRespiratoryRateRecord(na.a(record));
        }
        if (oa.a(record)) {
            return toSdkRestingHeartRateRecord(pa.a(record));
        }
        if (qa.a(record)) {
            return toSdkSexualActivityRecord(ra.a(record));
        }
        if (ta.a(record)) {
            return toSdkSleepSessionRecord(va.a(record));
        }
        if (wa.a(record)) {
            return toSdkSpeedRecord(xa.a(record));
        }
        if (ya.a(record)) {
            return toSdkStepsCadenceRecord(za.a(record));
        }
        if (ab.a(record)) {
            return toSdkStepsRecord(bb.a(record));
        }
        if (cb.a(record)) {
            return toSdkTotalCaloriesBurnedRecord(db.a(record));
        }
        if (hb.a(record)) {
            return toSdkVo2MaxRecord(ib.a(record));
        }
        if (jb.a(record)) {
            return toSdkWeightRecord(kb.a(record));
        }
        if (lb.a(record)) {
            return toWheelchairPushesRecord(mb.a(record));
        }
        throw new IllegalArgumentException("Unsupported record " + record);
    }

    private static final androidx.health.connect.client.records.RespiratoryRateRecord toSdkRespiratoryRateRecord(android.health.connect.datatypes.RespiratoryRateRecord respiratoryRateRecord) {
        Instant time = respiratoryRateRecord.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        ZoneOffset zoneOffset = respiratoryRateRecord.getZoneOffset();
        double rate = respiratoryRateRecord.getRate();
        android.health.connect.datatypes.Metadata metadata = respiratoryRateRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.RespiratoryRateRecord(time, zoneOffset, rate, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.RestingHeartRateRecord toSdkRestingHeartRateRecord(android.health.connect.datatypes.RestingHeartRateRecord restingHeartRateRecord) {
        Instant time = restingHeartRateRecord.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        ZoneOffset zoneOffset = restingHeartRateRecord.getZoneOffset();
        long beatsPerMinute = restingHeartRateRecord.getBeatsPerMinute();
        android.health.connect.datatypes.Metadata metadata = restingHeartRateRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.RestingHeartRateRecord(time, zoneOffset, beatsPerMinute, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.SexualActivityRecord toSdkSexualActivityRecord(android.health.connect.datatypes.SexualActivityRecord sexualActivityRecord) {
        Instant time = sexualActivityRecord.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        ZoneOffset zoneOffset = sexualActivityRecord.getZoneOffset();
        int sdkProtectionUsed = IntDefMappingsKt.toSdkProtectionUsed(sexualActivityRecord.getProtectionUsed());
        android.health.connect.datatypes.Metadata metadata = sexualActivityRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.SexualActivityRecord(time, zoneOffset, sdkProtectionUsed, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.SleepSessionRecord toSdkSleepSessionRecord(android.health.connect.datatypes.SleepSessionRecord sleepSessionRecord) {
        Instant startTime = sleepSessionRecord.getStartTime();
        ZoneOffset startZoneOffset = sleepSessionRecord.getStartZoneOffset();
        Instant endTime = sleepSessionRecord.getEndTime();
        ZoneOffset endZoneOffset = sleepSessionRecord.getEndZoneOffset();
        android.health.connect.datatypes.Metadata metadata = sleepSessionRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        androidx.health.connect.client.records.metadata.Metadata sdkMetadata = MetadataConvertersKt.toSdkMetadata(metadata);
        CharSequence title = sleepSessionRecord.getTitle();
        String string = title != null ? title.toString() : null;
        CharSequence notes = sleepSessionRecord.getNotes();
        String string2 = notes != null ? notes.toString() : null;
        List stages = sleepSessionRecord.getStages();
        Intrinsics.checkNotNullExpressionValue(stages, "stages");
        List list = stages;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            SleepSessionRecord.Stage it2 = uh.a(it.next());
            Intrinsics.checkNotNullExpressionValue(it2, "it");
            arrayList.add(toSdkSleepSessionStage(it2));
        }
        List listSortedWith = CollectionsKt.sortedWith(arrayList, new Comparator() { // from class: androidx.health.connect.client.impl.platform.records.RecordConvertersKt$toSdkSleepSessionRecord$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t2, T t3) {
                return ComparisonsKt.compareValues(((SleepSessionRecord.Stage) t2).getStartTime(), ((SleepSessionRecord.Stage) t3).getStartTime());
            }
        });
        Intrinsics.checkNotNullExpressionValue(startTime, "startTime");
        Intrinsics.checkNotNullExpressionValue(endTime, "endTime");
        return new androidx.health.connect.client.records.SleepSessionRecord(startTime, startZoneOffset, endTime, endZoneOffset, string, string2, listSortedWith, sdkMetadata);
    }

    private static final SleepSessionRecord.Stage toSdkSleepSessionStage(SleepSessionRecord.Stage stage) {
        Instant startTime = stage.getStartTime();
        Intrinsics.checkNotNullExpressionValue(startTime, "startTime");
        Instant endTime = stage.getEndTime();
        Intrinsics.checkNotNullExpressionValue(endTime, "endTime");
        return new SleepSessionRecord.Stage(startTime, endTime, IntDefMappingsKt.toSdkSleepStageType(stage.getType()));
    }

    private static final androidx.health.connect.client.records.SpeedRecord toSdkSpeedRecord(android.health.connect.datatypes.SpeedRecord speedRecord) {
        Instant startTime = speedRecord.getStartTime();
        Intrinsics.checkNotNullExpressionValue(startTime, "startTime");
        ZoneOffset startZoneOffset = speedRecord.getStartZoneOffset();
        Instant endTime = speedRecord.getEndTime();
        Intrinsics.checkNotNullExpressionValue(endTime, "endTime");
        ZoneOffset endZoneOffset = speedRecord.getEndZoneOffset();
        List samples = speedRecord.getSamples();
        Intrinsics.checkNotNullExpressionValue(samples, "samples");
        List list = samples;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            SpeedRecord.SpeedRecordSample it2 = ij.a(it.next());
            Intrinsics.checkNotNullExpressionValue(it2, "it");
            arrayList.add(toSdkSpeedSample(it2));
        }
        List listSortedWith = CollectionsKt.sortedWith(arrayList, new Comparator() { // from class: androidx.health.connect.client.impl.platform.records.RecordConvertersKt$toSdkSpeedRecord$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t2, T t3) {
                return ComparisonsKt.compareValues(((SpeedRecord.Sample) t2).getTime(), ((SpeedRecord.Sample) t3).getTime());
            }
        });
        android.health.connect.datatypes.Metadata metadata = speedRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.SpeedRecord(startTime, startZoneOffset, endTime, endZoneOffset, listSortedWith, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final SpeedRecord.Sample toSdkSpeedSample(SpeedRecord.SpeedRecordSample speedRecordSample) {
        Instant time = speedRecordSample.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        Velocity speed = speedRecordSample.getSpeed();
        Intrinsics.checkNotNullExpressionValue(speed, "speed");
        return new SpeedRecord.Sample(time, UnitConvertersKt.toSdkVelocity(speed));
    }

    private static final androidx.health.connect.client.records.StepsCadenceRecord toSdkStepsCadenceRecord(android.health.connect.datatypes.StepsCadenceRecord stepsCadenceRecord) {
        Instant startTime = stepsCadenceRecord.getStartTime();
        Intrinsics.checkNotNullExpressionValue(startTime, "startTime");
        ZoneOffset startZoneOffset = stepsCadenceRecord.getStartZoneOffset();
        Instant endTime = stepsCadenceRecord.getEndTime();
        Intrinsics.checkNotNullExpressionValue(endTime, "endTime");
        ZoneOffset endZoneOffset = stepsCadenceRecord.getEndZoneOffset();
        List samples = stepsCadenceRecord.getSamples();
        Intrinsics.checkNotNullExpressionValue(samples, "samples");
        List list = samples;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            StepsCadenceRecord.StepsCadenceRecordSample it2 = y9.a(it.next());
            Intrinsics.checkNotNullExpressionValue(it2, "it");
            arrayList.add(toSdkStepsCadenceSample(it2));
        }
        List listSortedWith = CollectionsKt.sortedWith(arrayList, new Comparator() { // from class: androidx.health.connect.client.impl.platform.records.RecordConvertersKt$toSdkStepsCadenceRecord$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t2, T t3) {
                return ComparisonsKt.compareValues(((StepsCadenceRecord.Sample) t2).getTime(), ((StepsCadenceRecord.Sample) t3).getTime());
            }
        });
        android.health.connect.datatypes.Metadata metadata = stepsCadenceRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.StepsCadenceRecord(startTime, startZoneOffset, endTime, endZoneOffset, listSortedWith, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final StepsCadenceRecord.Sample toSdkStepsCadenceSample(StepsCadenceRecord.StepsCadenceRecordSample stepsCadenceRecordSample) {
        Instant time = stepsCadenceRecordSample.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        return new StepsCadenceRecord.Sample(time, stepsCadenceRecordSample.getRate());
    }

    private static final androidx.health.connect.client.records.StepsRecord toSdkStepsRecord(android.health.connect.datatypes.StepsRecord stepsRecord) {
        Instant startTime = stepsRecord.getStartTime();
        Intrinsics.checkNotNullExpressionValue(startTime, "startTime");
        ZoneOffset startZoneOffset = stepsRecord.getStartZoneOffset();
        Instant endTime = stepsRecord.getEndTime();
        Intrinsics.checkNotNullExpressionValue(endTime, "endTime");
        ZoneOffset endZoneOffset = stepsRecord.getEndZoneOffset();
        long count = stepsRecord.getCount();
        android.health.connect.datatypes.Metadata metadata = stepsRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.StepsRecord(startTime, startZoneOffset, endTime, endZoneOffset, count, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.TotalCaloriesBurnedRecord toSdkTotalCaloriesBurnedRecord(android.health.connect.datatypes.TotalCaloriesBurnedRecord totalCaloriesBurnedRecord) {
        Instant startTime = totalCaloriesBurnedRecord.getStartTime();
        Intrinsics.checkNotNullExpressionValue(startTime, "startTime");
        ZoneOffset startZoneOffset = totalCaloriesBurnedRecord.getStartZoneOffset();
        Instant endTime = totalCaloriesBurnedRecord.getEndTime();
        Intrinsics.checkNotNullExpressionValue(endTime, "endTime");
        ZoneOffset endZoneOffset = totalCaloriesBurnedRecord.getEndZoneOffset();
        android.health.connect.datatypes.units.Energy energy = totalCaloriesBurnedRecord.getEnergy();
        Intrinsics.checkNotNullExpressionValue(energy, "energy");
        Energy sdkEnergy = UnitConvertersKt.toSdkEnergy(energy);
        android.health.connect.datatypes.Metadata metadata = totalCaloriesBurnedRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.TotalCaloriesBurnedRecord(startTime, startZoneOffset, endTime, endZoneOffset, sdkEnergy, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.Vo2MaxRecord toSdkVo2MaxRecord(android.health.connect.datatypes.Vo2MaxRecord vo2MaxRecord) {
        Instant time = vo2MaxRecord.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        ZoneOffset zoneOffset = vo2MaxRecord.getZoneOffset();
        double vo2MillilitersPerMinuteKilogram = vo2MaxRecord.getVo2MillilitersPerMinuteKilogram();
        int sdkVo2MaxMeasurementMethod = IntDefMappingsKt.toSdkVo2MaxMeasurementMethod(vo2MaxRecord.getMeasurementMethod());
        android.health.connect.datatypes.Metadata metadata = vo2MaxRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.Vo2MaxRecord(time, zoneOffset, vo2MillilitersPerMinuteKilogram, sdkVo2MaxMeasurementMethod, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.WeightRecord toSdkWeightRecord(android.health.connect.datatypes.WeightRecord weightRecord) {
        Instant time = weightRecord.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        ZoneOffset zoneOffset = weightRecord.getZoneOffset();
        android.health.connect.datatypes.units.Mass weight = weightRecord.getWeight();
        Intrinsics.checkNotNullExpressionValue(weight, "weight");
        Mass sdkMass = UnitConvertersKt.toSdkMass(weight);
        android.health.connect.datatypes.Metadata metadata = weightRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.WeightRecord(time, zoneOffset, sdkMass, MetadataConvertersKt.toSdkMetadata(metadata));
    }

    private static final androidx.health.connect.client.records.WheelchairPushesRecord toWheelchairPushesRecord(android.health.connect.datatypes.WheelchairPushesRecord wheelchairPushesRecord) {
        Instant startTime = wheelchairPushesRecord.getStartTime();
        Intrinsics.checkNotNullExpressionValue(startTime, "startTime");
        ZoneOffset startZoneOffset = wheelchairPushesRecord.getStartZoneOffset();
        Instant endTime = wheelchairPushesRecord.getEndTime();
        Intrinsics.checkNotNullExpressionValue(endTime, "endTime");
        ZoneOffset endZoneOffset = wheelchairPushesRecord.getEndZoneOffset();
        long count = wheelchairPushesRecord.getCount();
        android.health.connect.datatypes.Metadata metadata = wheelchairPushesRecord.getMetadata();
        Intrinsics.checkNotNullExpressionValue(metadata, "metadata");
        return new androidx.health.connect.client.records.WheelchairPushesRecord(startTime, startZoneOffset, endTime, endZoneOffset, count, MetadataConvertersKt.toSdkMetadata(metadata));
    }
}
