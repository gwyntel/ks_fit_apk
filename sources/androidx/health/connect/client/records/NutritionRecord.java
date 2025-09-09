package androidx.health.connect.client.records;

import androidx.health.connect.client.aggregate.AggregateMetric;
import androidx.health.connect.client.units.Energy;
import androidx.health.connect.client.units.EnergyKt;
import androidx.health.connect.client.units.Mass;
import androidx.health.connect.client.units.MassKt;
import androidx.media3.extractor.text.ttml.TtmlNode;
import com.aliyun.alink.linksdk.tools.ut.AUserTrack;
import com.google.android.gms.fitness.data.Field;
import java.time.Instant;
import java.time.ZoneOffset;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b'\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b<\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 z2\u00020\u0001:\u0001zBÁ\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010%\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010&\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010'\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010(\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010)\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010*\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010+\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010,\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010-\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010.\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010/\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u00100\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u00101\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u00102\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u00103\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u00104\u001a\u0004\u0018\u000105\u0012\b\b\u0002\u00106\u001a\u000207\u0012\b\b\u0002\u00108\u001a\u000209¢\u0006\u0002\u0010:J\u0013\u0010u\u001a\u00020v2\b\u0010w\u001a\u0004\u0018\u00010xH\u0096\u0002J\b\u0010y\u001a\u000207H\u0016R\u0013\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b;\u0010<R\u0013\u0010\n\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b=\u0010<R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b>\u0010<R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b?\u0010<R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b@\u0010<R\u0013\u0010\u0011\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bA\u0010<R\u0013\u0010\u0012\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bB\u0010<R\u0013\u0010\u0013\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bC\u0010<R\u0014\u0010\u0006\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bD\u0010ER\u0016\u0010\u0007\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bF\u0010GR\u0013\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\b\n\u0000\u001a\u0004\bH\u0010IR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\r¢\u0006\b\n\u0000\u001a\u0004\bJ\u0010IR\u0013\u0010\u0014\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bK\u0010<R\u0013\u0010\u0015\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bL\u0010<R\u0013\u0010\u0016\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bM\u0010<R\u0013\u0010\u0017\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bN\u0010<R\u0013\u0010\u0018\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bO\u0010<R\u0013\u0010\u0019\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bP\u0010<R\u0017\u00106\u001a\u000207¢\u0006\u000e\n\u0000\u0012\u0004\bQ\u0010R\u001a\u0004\bS\u0010TR\u0014\u00108\u001a\u000209X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bU\u0010VR\u0013\u0010\u001a\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bW\u0010<R\u0013\u0010\u001b\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bX\u0010<R\u0013\u00104\u001a\u0004\u0018\u000105¢\u0006\b\n\u0000\u001a\u0004\bY\u0010ZR\u0013\u0010\u001c\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b[\u0010<R\u0013\u0010\u001d\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\\\u0010<R\u0013\u0010\u001e\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b]\u0010<R\u0013\u0010\u001f\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b^\u0010<R\u0013\u0010 \u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b_\u0010<R\u0013\u0010!\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b`\u0010<R\u0013\u0010\"\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\ba\u0010<R\u0013\u0010#\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bb\u0010<R\u0013\u0010$\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bc\u0010<R\u0013\u0010%\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bd\u0010<R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\be\u0010ER\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bf\u0010GR\u0013\u0010&\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bg\u0010<R\u0013\u0010'\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bh\u0010<R\u0013\u0010(\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bi\u0010<R\u0013\u0010)\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bj\u0010<R\u0013\u0010*\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bk\u0010<R\u0013\u0010+\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bl\u0010<R\u0013\u0010,\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bm\u0010<R\u0013\u0010-\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bn\u0010<R\u0013\u0010.\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bo\u0010<R\u0013\u0010/\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bp\u0010<R\u0013\u00100\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bq\u0010<R\u0013\u00101\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\br\u0010<R\u0013\u00102\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bs\u0010<R\u0013\u00103\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\bt\u0010<¨\u0006{"}, d2 = {"Landroidx/health/connect/client/records/NutritionRecord;", "Landroidx/health/connect/client/records/IntervalRecord;", "startTime", "Ljava/time/Instant;", "startZoneOffset", "Ljava/time/ZoneOffset;", AUserTrack.UTKEY_END_TIME, "endZoneOffset", "biotin", "Landroidx/health/connect/client/units/Mass;", "caffeine", Field.NUTRIENT_CALCIUM, "energy", "Landroidx/health/connect/client/units/Energy;", "energyFromFat", "chloride", Field.NUTRIENT_CHOLESTEROL, "chromium", "copper", "dietaryFiber", "folate", "folicAcid", "iodine", Field.NUTRIENT_IRON, "magnesium", "manganese", "molybdenum", "monounsaturatedFat", "niacin", "pantothenicAcid", "phosphorus", "polyunsaturatedFat", Field.NUTRIENT_POTASSIUM, Field.NUTRIENT_PROTEIN, "riboflavin", "saturatedFat", "selenium", Field.NUTRIENT_SODIUM, Field.NUTRIENT_SUGAR, "thiamin", "totalCarbohydrate", "totalFat", "transFat", "unsaturatedFat", "vitaminA", "vitaminB12", "vitaminB6", "vitaminC", "vitaminD", "vitaminE", "vitaminK", "zinc", "name", "", "mealType", "", TtmlNode.TAG_METADATA, "Landroidx/health/connect/client/records/metadata/Metadata;", "(Ljava/time/Instant;Ljava/time/ZoneOffset;Ljava/time/Instant;Ljava/time/ZoneOffset;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Energy;Landroidx/health/connect/client/units/Energy;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Landroidx/health/connect/client/units/Mass;Ljava/lang/String;ILandroidx/health/connect/client/records/metadata/Metadata;)V", "getBiotin", "()Landroidx/health/connect/client/units/Mass;", "getCaffeine", "getCalcium", "getChloride", "getCholesterol", "getChromium", "getCopper", "getDietaryFiber", "getEndTime", "()Ljava/time/Instant;", "getEndZoneOffset", "()Ljava/time/ZoneOffset;", "getEnergy", "()Landroidx/health/connect/client/units/Energy;", "getEnergyFromFat", "getFolate", "getFolicAcid", "getIodine", "getIron", "getMagnesium", "getManganese", "getMealType$annotations", "()V", "getMealType", "()I", "getMetadata", "()Landroidx/health/connect/client/records/metadata/Metadata;", "getMolybdenum", "getMonounsaturatedFat", "getName", "()Ljava/lang/String;", "getNiacin", "getPantothenicAcid", "getPhosphorus", "getPolyunsaturatedFat", "getPotassium", "getProtein", "getRiboflavin", "getSaturatedFat", "getSelenium", "getSodium", "getStartTime", "getStartZoneOffset", "getSugar", "getThiamin", "getTotalCarbohydrate", "getTotalFat", "getTransFat", "getUnsaturatedFat", "getVitaminA", "getVitaminB12", "getVitaminB6", "getVitaminC", "getVitaminD", "getVitaminE", "getVitaminK", "getZinc", "equals", "", "other", "", "hashCode", "Companion", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nNutritionRecord.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NutritionRecord.kt\nandroidx/health/connect/client/records/NutritionRecord\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,641:1\n1#2:642\n*E\n"})
/* loaded from: classes.dex */
public final class NutritionRecord implements IntervalRecord {

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> BIOTIN_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> CAFFEINE_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> CALCIUM_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> CHLORIDE_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> CHOLESTEROL_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> CHROMIUM_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> COPPER_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> DIETARY_FIBER_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Energy> ENERGY_FROM_FAT_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Energy> ENERGY_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> FOLATE_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> FOLIC_ACID_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> IODINE_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> IRON_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> MAGNESIUM_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> MANGANESE_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> MOLYBDENUM_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> MONOUNSATURATED_FAT_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> NIACIN_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> PANTOTHENIC_ACID_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> PHOSPHORUS_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> POLYUNSATURATED_FAT_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> POTASSIUM_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> PROTEIN_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> RIBOFLAVIN_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> SATURATED_FAT_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> SELENIUM_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> SODIUM_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> SUGAR_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> THIAMIN_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> TOTAL_CARBOHYDRATE_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> TOTAL_FAT_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> TRANS_FAT_TOTAL;

    @NotNull
    private static final String TYPE_NAME = "Nutrition";

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> UNSATURATED_FAT_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> VITAMIN_A_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> VITAMIN_B12_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> VITAMIN_B6_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> VITAMIN_C_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> VITAMIN_D_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> VITAMIN_E_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> VITAMIN_K_TOTAL;

    @JvmField
    @NotNull
    public static final AggregateMetric<Mass> ZINC_TOTAL;

    @Nullable
    private final Mass biotin;

    @Nullable
    private final Mass caffeine;

    @Nullable
    private final Mass calcium;

    @Nullable
    private final Mass chloride;

    @Nullable
    private final Mass cholesterol;

    @Nullable
    private final Mass chromium;

    @Nullable
    private final Mass copper;

    @Nullable
    private final Mass dietaryFiber;

    @NotNull
    private final Instant endTime;

    @Nullable
    private final ZoneOffset endZoneOffset;

    @Nullable
    private final Energy energy;

    @Nullable
    private final Energy energyFromFat;

    @Nullable
    private final Mass folate;

    @Nullable
    private final Mass folicAcid;

    @Nullable
    private final Mass iodine;

    @Nullable
    private final Mass iron;

    @Nullable
    private final Mass magnesium;

    @Nullable
    private final Mass manganese;
    private final int mealType;

    @NotNull
    private final androidx.health.connect.client.records.metadata.Metadata metadata;

    @Nullable
    private final Mass molybdenum;

    @Nullable
    private final Mass monounsaturatedFat;

    @Nullable
    private final String name;

    @Nullable
    private final Mass niacin;

    @Nullable
    private final Mass pantothenicAcid;

    @Nullable
    private final Mass phosphorus;

    @Nullable
    private final Mass polyunsaturatedFat;

    @Nullable
    private final Mass potassium;

    @Nullable
    private final Mass protein;

    @Nullable
    private final Mass riboflavin;

    @Nullable
    private final Mass saturatedFat;

    @Nullable
    private final Mass selenium;

    @Nullable
    private final Mass sodium;

    @NotNull
    private final Instant startTime;

    @Nullable
    private final ZoneOffset startZoneOffset;

    @Nullable
    private final Mass sugar;

    @Nullable
    private final Mass thiamin;

    @Nullable
    private final Mass totalCarbohydrate;

    @Nullable
    private final Mass totalFat;

    @Nullable
    private final Mass transFat;

    @Nullable
    private final Mass unsaturatedFat;

    @Nullable
    private final Mass vitaminA;

    @Nullable
    private final Mass vitaminB12;

    @Nullable
    private final Mass vitaminB6;

    @Nullable
    private final Mass vitaminC;

    @Nullable
    private final Mass vitaminD;

    @Nullable
    private final Mass vitaminE;

    @Nullable
    private final Mass vitaminK;

    @Nullable
    private final Mass zinc;

    @NotNull
    private static final Mass MIN_MASS = MassKt.getGrams(0.0d);

    @NotNull
    private static final Mass MAX_MASS_100 = MassKt.getGrams(4.94E-322d);

    @NotNull
    private static final Mass MAX_MASS_100K = MassKt.getGrams(4.94066E-319d);

    @NotNull
    private static final Energy MIN_ENERGY = EnergyKt.getCalories(0.0d);

    @NotNull
    private static final Energy MAX_ENERGY = EnergyKt.getCalories(4.94065646E-316d);

    static {
        AggregateMetric.Companion companion = AggregateMetric.INSTANCE;
        AggregateMetric.AggregationType aggregationType = AggregateMetric.AggregationType.TOTAL;
        Mass.Companion companion2 = Mass.INSTANCE;
        BIOTIN_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "biotin", new NutritionRecord$Companion$BIOTIN_TOTAL$1(companion2));
        CAFFEINE_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "caffeine", new NutritionRecord$Companion$CAFFEINE_TOTAL$1(companion2));
        CALCIUM_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, Field.NUTRIENT_CALCIUM, new NutritionRecord$Companion$CALCIUM_TOTAL$1(companion2));
        Energy.Companion companion3 = Energy.INSTANCE;
        ENERGY_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, Field.NUTRIENT_CALORIES, new NutritionRecord$Companion$ENERGY_TOTAL$1(companion3));
        ENERGY_FROM_FAT_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "caloriesFromFat", new NutritionRecord$Companion$ENERGY_FROM_FAT_TOTAL$1(companion3));
        CHLORIDE_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "chloride", new NutritionRecord$Companion$CHLORIDE_TOTAL$1(companion2));
        CHOLESTEROL_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, Field.NUTRIENT_CHOLESTEROL, new NutritionRecord$Companion$CHOLESTEROL_TOTAL$1(companion2));
        CHROMIUM_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "chromium", new NutritionRecord$Companion$CHROMIUM_TOTAL$1(companion2));
        COPPER_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "copper", new NutritionRecord$Companion$COPPER_TOTAL$1(companion2));
        DIETARY_FIBER_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "dietaryFiber", new NutritionRecord$Companion$DIETARY_FIBER_TOTAL$1(companion2));
        FOLATE_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "folate", new NutritionRecord$Companion$FOLATE_TOTAL$1(companion2));
        FOLIC_ACID_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "folicAcid", new NutritionRecord$Companion$FOLIC_ACID_TOTAL$1(companion2));
        IODINE_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "iodine", new NutritionRecord$Companion$IODINE_TOTAL$1(companion2));
        IRON_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, Field.NUTRIENT_IRON, new NutritionRecord$Companion$IRON_TOTAL$1(companion2));
        MAGNESIUM_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "magnesium", new NutritionRecord$Companion$MAGNESIUM_TOTAL$1(companion2));
        MANGANESE_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "manganese", new NutritionRecord$Companion$MANGANESE_TOTAL$1(companion2));
        MOLYBDENUM_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "molybdenum", new NutritionRecord$Companion$MOLYBDENUM_TOTAL$1(companion2));
        MONOUNSATURATED_FAT_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "monounsaturatedFat", new NutritionRecord$Companion$MONOUNSATURATED_FAT_TOTAL$1(companion2));
        NIACIN_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "niacin", new NutritionRecord$Companion$NIACIN_TOTAL$1(companion2));
        PANTOTHENIC_ACID_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "pantothenicAcid", new NutritionRecord$Companion$PANTOTHENIC_ACID_TOTAL$1(companion2));
        PHOSPHORUS_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "phosphorus", new NutritionRecord$Companion$PHOSPHORUS_TOTAL$1(companion2));
        POLYUNSATURATED_FAT_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "polyunsaturatedFat", new NutritionRecord$Companion$POLYUNSATURATED_FAT_TOTAL$1(companion2));
        POTASSIUM_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, Field.NUTRIENT_POTASSIUM, new NutritionRecord$Companion$POTASSIUM_TOTAL$1(companion2));
        PROTEIN_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, Field.NUTRIENT_PROTEIN, new NutritionRecord$Companion$PROTEIN_TOTAL$1(companion2));
        RIBOFLAVIN_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "riboflavin", new NutritionRecord$Companion$RIBOFLAVIN_TOTAL$1(companion2));
        SATURATED_FAT_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "saturatedFat", new NutritionRecord$Companion$SATURATED_FAT_TOTAL$1(companion2));
        SELENIUM_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "selenium", new NutritionRecord$Companion$SELENIUM_TOTAL$1(companion2));
        SODIUM_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, Field.NUTRIENT_SODIUM, new NutritionRecord$Companion$SODIUM_TOTAL$1(companion2));
        SUGAR_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, Field.NUTRIENT_SUGAR, new NutritionRecord$Companion$SUGAR_TOTAL$1(companion2));
        THIAMIN_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "thiamin", new NutritionRecord$Companion$THIAMIN_TOTAL$1(companion2));
        TOTAL_CARBOHYDRATE_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "totalCarbohydrate", new NutritionRecord$Companion$TOTAL_CARBOHYDRATE_TOTAL$1(companion2));
        TOTAL_FAT_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "totalFat", new NutritionRecord$Companion$TOTAL_FAT_TOTAL$1(companion2));
        TRANS_FAT_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "transFat", new NutritionRecord$Companion$TRANS_FAT_TOTAL$1(companion2));
        UNSATURATED_FAT_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "unsaturatedFat", new NutritionRecord$Companion$UNSATURATED_FAT_TOTAL$1(companion2));
        VITAMIN_A_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "vitaminA", new NutritionRecord$Companion$VITAMIN_A_TOTAL$1(companion2));
        VITAMIN_B12_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "vitaminB12", new NutritionRecord$Companion$VITAMIN_B12_TOTAL$1(companion2));
        VITAMIN_B6_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "vitaminB6", new NutritionRecord$Companion$VITAMIN_B6_TOTAL$1(companion2));
        VITAMIN_C_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "vitaminC", new NutritionRecord$Companion$VITAMIN_C_TOTAL$1(companion2));
        VITAMIN_D_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "vitaminD", new NutritionRecord$Companion$VITAMIN_D_TOTAL$1(companion2));
        VITAMIN_E_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "vitaminE", new NutritionRecord$Companion$VITAMIN_E_TOTAL$1(companion2));
        VITAMIN_K_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "vitaminK", new NutritionRecord$Companion$VITAMIN_K_TOTAL$1(companion2));
        ZINC_TOTAL = companion.doubleMetric$connect_client_release(TYPE_NAME, aggregationType, "zinc", new NutritionRecord$Companion$ZINC_TOTAL$1(companion2));
    }

    public NutritionRecord(@NotNull Instant startTime, @Nullable ZoneOffset zoneOffset, @NotNull Instant endTime, @Nullable ZoneOffset zoneOffset2, @Nullable Mass mass, @Nullable Mass mass2, @Nullable Mass mass3, @Nullable Energy energy, @Nullable Energy energy2, @Nullable Mass mass4, @Nullable Mass mass5, @Nullable Mass mass6, @Nullable Mass mass7, @Nullable Mass mass8, @Nullable Mass mass9, @Nullable Mass mass10, @Nullable Mass mass11, @Nullable Mass mass12, @Nullable Mass mass13, @Nullable Mass mass14, @Nullable Mass mass15, @Nullable Mass mass16, @Nullable Mass mass17, @Nullable Mass mass18, @Nullable Mass mass19, @Nullable Mass mass20, @Nullable Mass mass21, @Nullable Mass mass22, @Nullable Mass mass23, @Nullable Mass mass24, @Nullable Mass mass25, @Nullable Mass mass26, @Nullable Mass mass27, @Nullable Mass mass28, @Nullable Mass mass29, @Nullable Mass mass30, @Nullable Mass mass31, @Nullable Mass mass32, @Nullable Mass mass33, @Nullable Mass mass34, @Nullable Mass mass35, @Nullable Mass mass36, @Nullable Mass mass37, @Nullable Mass mass38, @Nullable Mass mass39, @Nullable Mass mass40, @Nullable String str, int i2, @NotNull androidx.health.connect.client.records.metadata.Metadata metadata) {
        Intrinsics.checkNotNullParameter(startTime, "startTime");
        Intrinsics.checkNotNullParameter(endTime, "endTime");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        this.startTime = startTime;
        this.startZoneOffset = zoneOffset;
        this.endTime = endTime;
        this.endZoneOffset = zoneOffset2;
        this.biotin = mass;
        this.caffeine = mass2;
        this.calcium = mass3;
        this.energy = energy;
        this.energyFromFat = energy2;
        this.chloride = mass4;
        this.cholesterol = mass5;
        this.chromium = mass6;
        this.copper = mass7;
        this.dietaryFiber = mass8;
        this.folate = mass9;
        this.folicAcid = mass10;
        this.iodine = mass11;
        this.iron = mass12;
        this.magnesium = mass13;
        this.manganese = mass14;
        this.molybdenum = mass15;
        this.monounsaturatedFat = mass16;
        this.niacin = mass17;
        this.pantothenicAcid = mass18;
        this.phosphorus = mass19;
        this.polyunsaturatedFat = mass20;
        this.potassium = mass21;
        this.protein = mass22;
        this.riboflavin = mass23;
        this.saturatedFat = mass24;
        this.selenium = mass25;
        this.sodium = mass26;
        this.sugar = mass27;
        this.thiamin = mass28;
        this.totalCarbohydrate = mass29;
        this.totalFat = mass30;
        this.transFat = mass31;
        this.unsaturatedFat = mass32;
        this.vitaminA = mass33;
        this.vitaminB12 = mass34;
        this.vitaminB6 = mass35;
        this.vitaminC = mass36;
        this.vitaminD = mass37;
        this.vitaminE = mass38;
        this.vitaminK = mass39;
        this.zinc = mass40;
        this.name = str;
        this.mealType = i2;
        this.metadata = metadata;
        if (!getStartTime().isBefore(getEndTime())) {
            throw new IllegalArgumentException("startTime must be before endTime.".toString());
        }
        Mass mass41 = this.biotin;
        if (mass41 != null) {
            UtilsKt.requireInRange(mass41, MIN_MASS, MAX_MASS_100, "biotin");
        }
        Mass mass42 = this.caffeine;
        if (mass42 != null) {
            UtilsKt.requireInRange(mass42, MIN_MASS, MAX_MASS_100, "caffeine");
        }
        Mass mass43 = this.calcium;
        if (mass43 != null) {
            UtilsKt.requireInRange(mass43, MIN_MASS, MAX_MASS_100, Field.NUTRIENT_CALCIUM);
        }
        Energy energy3 = this.energy;
        if (energy3 != null) {
            UtilsKt.requireInRange(energy3, MIN_ENERGY, MAX_ENERGY, "energy");
        }
        Energy energy4 = this.energyFromFat;
        if (energy4 != null) {
            UtilsKt.requireInRange(energy4, MIN_ENERGY, MAX_ENERGY, "energyFromFat");
        }
        Mass mass44 = this.chloride;
        if (mass44 != null) {
            UtilsKt.requireInRange(mass44, MIN_MASS, MAX_MASS_100, "chloride");
        }
        Mass mass45 = this.cholesterol;
        if (mass45 != null) {
            UtilsKt.requireInRange(mass45, MIN_MASS, MAX_MASS_100, Field.NUTRIENT_CHOLESTEROL);
        }
        Mass mass46 = this.chromium;
        if (mass46 != null) {
            UtilsKt.requireInRange(mass46, MIN_MASS, MAX_MASS_100, "chromium");
        }
        Mass mass47 = this.copper;
        if (mass47 != null) {
            UtilsKt.requireInRange(mass47, MIN_MASS, MAX_MASS_100, "copper");
        }
        Mass mass48 = this.dietaryFiber;
        if (mass48 != null) {
            UtilsKt.requireInRange(mass48, MIN_MASS, MAX_MASS_100K, "dietaryFiber");
        }
        Mass mass49 = this.folate;
        if (mass49 != null) {
            UtilsKt.requireInRange(mass49, MIN_MASS, MAX_MASS_100, "chloride");
        }
        Mass mass50 = this.folicAcid;
        if (mass50 != null) {
            UtilsKt.requireInRange(mass50, MIN_MASS, MAX_MASS_100, "folicAcid");
        }
        Mass mass51 = this.iodine;
        if (mass51 != null) {
            UtilsKt.requireInRange(mass51, MIN_MASS, MAX_MASS_100, "iodine");
        }
        Mass mass52 = this.iron;
        if (mass52 != null) {
            UtilsKt.requireInRange(mass52, MIN_MASS, MAX_MASS_100, Field.NUTRIENT_IRON);
        }
        Mass mass53 = this.magnesium;
        if (mass53 != null) {
            UtilsKt.requireInRange(mass53, MIN_MASS, MAX_MASS_100, "magnesium");
        }
        Mass mass54 = this.manganese;
        if (mass54 != null) {
            UtilsKt.requireInRange(mass54, MIN_MASS, MAX_MASS_100, "manganese");
        }
        Mass mass55 = this.molybdenum;
        if (mass55 != null) {
            UtilsKt.requireInRange(mass55, MIN_MASS, MAX_MASS_100, "molybdenum");
        }
        if (mass16 != null) {
            UtilsKt.requireInRange(mass16, MIN_MASS, MAX_MASS_100K, "monounsaturatedFat");
        }
        if (mass17 != null) {
            UtilsKt.requireInRange(mass17, MIN_MASS, MAX_MASS_100, "niacin");
        }
        if (mass18 != null) {
            UtilsKt.requireInRange(mass18, MIN_MASS, MAX_MASS_100, "pantothenicAcid");
        }
        if (mass19 != null) {
            UtilsKt.requireInRange(mass19, MIN_MASS, MAX_MASS_100, "phosphorus");
        }
        if (mass20 != null) {
            UtilsKt.requireInRange(mass20, MIN_MASS, MAX_MASS_100K, "polyunsaturatedFat");
        }
        if (mass21 != null) {
            UtilsKt.requireInRange(mass21, MIN_MASS, MAX_MASS_100, Field.NUTRIENT_POTASSIUM);
        }
        if (mass22 != null) {
            UtilsKt.requireInRange(mass22, MIN_MASS, MAX_MASS_100K, Field.NUTRIENT_PROTEIN);
        }
        if (mass23 != null) {
            UtilsKt.requireInRange(mass23, MIN_MASS, MAX_MASS_100, "riboflavin");
        }
        if (mass24 != null) {
            UtilsKt.requireInRange(mass24, MIN_MASS, MAX_MASS_100K, "saturatedFat");
        }
        if (mass25 != null) {
            UtilsKt.requireInRange(mass25, MIN_MASS, MAX_MASS_100, "selenium");
        }
        if (mass26 != null) {
            UtilsKt.requireInRange(mass26, MIN_MASS, MAX_MASS_100, Field.NUTRIENT_SODIUM);
        }
        if (mass27 != null) {
            UtilsKt.requireInRange(mass27, MIN_MASS, MAX_MASS_100K, Field.NUTRIENT_SUGAR);
        }
        if (mass28 != null) {
            UtilsKt.requireInRange(mass28, MIN_MASS, MAX_MASS_100, "thiamin");
        }
        if (mass29 != null) {
            UtilsKt.requireInRange(mass29, MIN_MASS, MAX_MASS_100K, "totalCarbohydrate");
        }
        if (mass30 != null) {
            UtilsKt.requireInRange(mass30, MIN_MASS, MAX_MASS_100K, "totalFat");
        }
        if (mass31 != null) {
            UtilsKt.requireInRange(mass31, MIN_MASS, MAX_MASS_100K, "transFat");
        }
        if (mass32 != null) {
            UtilsKt.requireInRange(mass32, MIN_MASS, MAX_MASS_100K, "unsaturatedFat");
        }
        if (mass33 != null) {
            UtilsKt.requireInRange(mass33, MIN_MASS, MAX_MASS_100, "vitaminA");
        }
        if (mass34 != null) {
            UtilsKt.requireInRange(mass34, MIN_MASS, MAX_MASS_100, "vitaminB12");
        }
        if (mass35 != null) {
            UtilsKt.requireInRange(mass35, MIN_MASS, MAX_MASS_100, "vitaminB6");
        }
        if (mass36 != null) {
            UtilsKt.requireInRange(mass36, MIN_MASS, MAX_MASS_100, "vitaminC");
        }
        if (mass37 != null) {
            UtilsKt.requireInRange(mass37, MIN_MASS, MAX_MASS_100, "vitaminD");
        }
        if (mass38 != null) {
            UtilsKt.requireInRange(mass38, MIN_MASS, MAX_MASS_100, "vitaminE");
        }
        if (mass39 != null) {
            UtilsKt.requireInRange(mass39, MIN_MASS, MAX_MASS_100, "vitaminK");
        }
        if (mass40 != null) {
            UtilsKt.requireInRange(mass40, MIN_MASS, MAX_MASS_100, "zinc");
        }
    }

    public static /* synthetic */ void getMealType$annotations() {
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NutritionRecord)) {
            return false;
        }
        NutritionRecord nutritionRecord = (NutritionRecord) other;
        return Intrinsics.areEqual(this.biotin, nutritionRecord.biotin) && Intrinsics.areEqual(this.caffeine, nutritionRecord.caffeine) && Intrinsics.areEqual(this.calcium, nutritionRecord.calcium) && Intrinsics.areEqual(this.energy, nutritionRecord.energy) && Intrinsics.areEqual(this.energyFromFat, nutritionRecord.energyFromFat) && Intrinsics.areEqual(this.chloride, nutritionRecord.chloride) && Intrinsics.areEqual(this.cholesterol, nutritionRecord.cholesterol) && Intrinsics.areEqual(this.chromium, nutritionRecord.chromium) && Intrinsics.areEqual(this.copper, nutritionRecord.copper) && Intrinsics.areEqual(this.dietaryFiber, nutritionRecord.dietaryFiber) && Intrinsics.areEqual(this.folate, nutritionRecord.folate) && Intrinsics.areEqual(this.folicAcid, nutritionRecord.folicAcid) && Intrinsics.areEqual(this.iodine, nutritionRecord.iodine) && Intrinsics.areEqual(this.iron, nutritionRecord.iron) && Intrinsics.areEqual(this.magnesium, nutritionRecord.magnesium) && Intrinsics.areEqual(this.manganese, nutritionRecord.manganese) && Intrinsics.areEqual(this.molybdenum, nutritionRecord.molybdenum) && Intrinsics.areEqual(this.monounsaturatedFat, nutritionRecord.monounsaturatedFat) && Intrinsics.areEqual(this.niacin, nutritionRecord.niacin) && Intrinsics.areEqual(this.pantothenicAcid, nutritionRecord.pantothenicAcid) && Intrinsics.areEqual(this.phosphorus, nutritionRecord.phosphorus) && Intrinsics.areEqual(this.polyunsaturatedFat, nutritionRecord.polyunsaturatedFat) && Intrinsics.areEqual(this.potassium, nutritionRecord.potassium) && Intrinsics.areEqual(this.protein, nutritionRecord.protein) && Intrinsics.areEqual(this.riboflavin, nutritionRecord.riboflavin) && Intrinsics.areEqual(this.saturatedFat, nutritionRecord.saturatedFat) && Intrinsics.areEqual(this.selenium, nutritionRecord.selenium) && Intrinsics.areEqual(this.sodium, nutritionRecord.sodium) && Intrinsics.areEqual(this.sugar, nutritionRecord.sugar) && Intrinsics.areEqual(this.thiamin, nutritionRecord.thiamin) && Intrinsics.areEqual(this.totalCarbohydrate, nutritionRecord.totalCarbohydrate) && Intrinsics.areEqual(this.totalFat, nutritionRecord.totalFat) && Intrinsics.areEqual(this.transFat, nutritionRecord.transFat) && Intrinsics.areEqual(this.unsaturatedFat, nutritionRecord.unsaturatedFat) && Intrinsics.areEqual(this.vitaminA, nutritionRecord.vitaminA) && Intrinsics.areEqual(this.vitaminB12, nutritionRecord.vitaminB12) && Intrinsics.areEqual(this.vitaminB6, nutritionRecord.vitaminB6) && Intrinsics.areEqual(this.vitaminC, nutritionRecord.vitaminC) && Intrinsics.areEqual(this.vitaminD, nutritionRecord.vitaminD) && Intrinsics.areEqual(this.vitaminE, nutritionRecord.vitaminE) && Intrinsics.areEqual(this.vitaminK, nutritionRecord.vitaminK) && Intrinsics.areEqual(this.zinc, nutritionRecord.zinc) && Intrinsics.areEqual(this.name, nutritionRecord.name) && this.mealType == nutritionRecord.mealType && Intrinsics.areEqual(getStartTime(), nutritionRecord.getStartTime()) && Intrinsics.areEqual(getStartZoneOffset(), nutritionRecord.getStartZoneOffset()) && Intrinsics.areEqual(getEndTime(), nutritionRecord.getEndTime()) && Intrinsics.areEqual(getEndZoneOffset(), nutritionRecord.getEndZoneOffset()) && Intrinsics.areEqual(getMetadata(), nutritionRecord.getMetadata());
    }

    @Nullable
    public final Mass getBiotin() {
        return this.biotin;
    }

    @Nullable
    public final Mass getCaffeine() {
        return this.caffeine;
    }

    @Nullable
    public final Mass getCalcium() {
        return this.calcium;
    }

    @Nullable
    public final Mass getChloride() {
        return this.chloride;
    }

    @Nullable
    public final Mass getCholesterol() {
        return this.cholesterol;
    }

    @Nullable
    public final Mass getChromium() {
        return this.chromium;
    }

    @Nullable
    public final Mass getCopper() {
        return this.copper;
    }

    @Nullable
    public final Mass getDietaryFiber() {
        return this.dietaryFiber;
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

    @Nullable
    public final Energy getEnergy() {
        return this.energy;
    }

    @Nullable
    public final Energy getEnergyFromFat() {
        return this.energyFromFat;
    }

    @Nullable
    public final Mass getFolate() {
        return this.folate;
    }

    @Nullable
    public final Mass getFolicAcid() {
        return this.folicAcid;
    }

    @Nullable
    public final Mass getIodine() {
        return this.iodine;
    }

    @Nullable
    public final Mass getIron() {
        return this.iron;
    }

    @Nullable
    public final Mass getMagnesium() {
        return this.magnesium;
    }

    @Nullable
    public final Mass getManganese() {
        return this.manganese;
    }

    public final int getMealType() {
        return this.mealType;
    }

    @Override // androidx.health.connect.client.records.Record
    @NotNull
    public androidx.health.connect.client.records.metadata.Metadata getMetadata() {
        return this.metadata;
    }

    @Nullable
    public final Mass getMolybdenum() {
        return this.molybdenum;
    }

    @Nullable
    public final Mass getMonounsaturatedFat() {
        return this.monounsaturatedFat;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    @Nullable
    public final Mass getNiacin() {
        return this.niacin;
    }

    @Nullable
    public final Mass getPantothenicAcid() {
        return this.pantothenicAcid;
    }

    @Nullable
    public final Mass getPhosphorus() {
        return this.phosphorus;
    }

    @Nullable
    public final Mass getPolyunsaturatedFat() {
        return this.polyunsaturatedFat;
    }

    @Nullable
    public final Mass getPotassium() {
        return this.potassium;
    }

    @Nullable
    public final Mass getProtein() {
        return this.protein;
    }

    @Nullable
    public final Mass getRiboflavin() {
        return this.riboflavin;
    }

    @Nullable
    public final Mass getSaturatedFat() {
        return this.saturatedFat;
    }

    @Nullable
    public final Mass getSelenium() {
        return this.selenium;
    }

    @Nullable
    public final Mass getSodium() {
        return this.sodium;
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
    public final Mass getSugar() {
        return this.sugar;
    }

    @Nullable
    public final Mass getThiamin() {
        return this.thiamin;
    }

    @Nullable
    public final Mass getTotalCarbohydrate() {
        return this.totalCarbohydrate;
    }

    @Nullable
    public final Mass getTotalFat() {
        return this.totalFat;
    }

    @Nullable
    public final Mass getTransFat() {
        return this.transFat;
    }

    @Nullable
    public final Mass getUnsaturatedFat() {
        return this.unsaturatedFat;
    }

    @Nullable
    public final Mass getVitaminA() {
        return this.vitaminA;
    }

    @Nullable
    public final Mass getVitaminB12() {
        return this.vitaminB12;
    }

    @Nullable
    public final Mass getVitaminB6() {
        return this.vitaminB6;
    }

    @Nullable
    public final Mass getVitaminC() {
        return this.vitaminC;
    }

    @Nullable
    public final Mass getVitaminD() {
        return this.vitaminD;
    }

    @Nullable
    public final Mass getVitaminE() {
        return this.vitaminE;
    }

    @Nullable
    public final Mass getVitaminK() {
        return this.vitaminK;
    }

    @Nullable
    public final Mass getZinc() {
        return this.zinc;
    }

    public int hashCode() {
        Mass mass = this.biotin;
        int iHashCode = (mass != null ? mass.hashCode() : 0) * 31;
        Mass mass2 = this.caffeine;
        int iHashCode2 = (iHashCode + (mass2 != null ? mass2.hashCode() : 0)) * 31;
        Mass mass3 = this.calcium;
        int iHashCode3 = (iHashCode2 + (mass3 != null ? mass3.hashCode() : 0)) * 31;
        Energy energy = this.energy;
        int iHashCode4 = (iHashCode3 + (energy != null ? energy.hashCode() : 0)) * 31;
        Energy energy2 = this.energyFromFat;
        int iHashCode5 = (iHashCode4 + (energy2 != null ? energy2.hashCode() : 0)) * 31;
        Mass mass4 = this.chloride;
        int iHashCode6 = (iHashCode5 + (mass4 != null ? mass4.hashCode() : 0)) * 31;
        Mass mass5 = this.cholesterol;
        int iHashCode7 = (iHashCode6 + (mass5 != null ? mass5.hashCode() : 0)) * 31;
        Mass mass6 = this.chromium;
        int iHashCode8 = (iHashCode7 + (mass6 != null ? mass6.hashCode() : 0)) * 31;
        Mass mass7 = this.copper;
        int iHashCode9 = (iHashCode8 + (mass7 != null ? mass7.hashCode() : 0)) * 31;
        Mass mass8 = this.dietaryFiber;
        int iHashCode10 = (iHashCode9 + (mass8 != null ? mass8.hashCode() : 0)) * 31;
        Mass mass9 = this.folate;
        int iHashCode11 = (iHashCode10 + (mass9 != null ? mass9.hashCode() : 0)) * 31;
        Mass mass10 = this.folicAcid;
        int iHashCode12 = (iHashCode11 + (mass10 != null ? mass10.hashCode() : 0)) * 31;
        Mass mass11 = this.iodine;
        int iHashCode13 = (iHashCode12 + (mass11 != null ? mass11.hashCode() : 0)) * 31;
        Mass mass12 = this.iron;
        int iHashCode14 = (iHashCode13 + (mass12 != null ? mass12.hashCode() : 0)) * 31;
        Mass mass13 = this.magnesium;
        int iHashCode15 = (iHashCode14 + (mass13 != null ? mass13.hashCode() : 0)) * 31;
        Mass mass14 = this.manganese;
        int iHashCode16 = (iHashCode15 + (mass14 != null ? mass14.hashCode() : 0)) * 31;
        Mass mass15 = this.molybdenum;
        int iHashCode17 = (iHashCode16 + (mass15 != null ? mass15.hashCode() : 0)) * 31;
        Mass mass16 = this.monounsaturatedFat;
        int iHashCode18 = (iHashCode17 + (mass16 != null ? mass16.hashCode() : 0)) * 31;
        Mass mass17 = this.niacin;
        int iHashCode19 = (iHashCode18 + (mass17 != null ? mass17.hashCode() : 0)) * 31;
        Mass mass18 = this.pantothenicAcid;
        int iHashCode20 = (iHashCode19 + (mass18 != null ? mass18.hashCode() : 0)) * 31;
        Mass mass19 = this.phosphorus;
        int iHashCode21 = (iHashCode20 + (mass19 != null ? mass19.hashCode() : 0)) * 31;
        Mass mass20 = this.polyunsaturatedFat;
        int iHashCode22 = (iHashCode21 + (mass20 != null ? mass20.hashCode() : 0)) * 31;
        Mass mass21 = this.potassium;
        int iHashCode23 = (iHashCode22 + (mass21 != null ? mass21.hashCode() : 0)) * 31;
        Mass mass22 = this.protein;
        int iHashCode24 = (iHashCode23 + (mass22 != null ? mass22.hashCode() : 0)) * 31;
        Mass mass23 = this.riboflavin;
        int iHashCode25 = (iHashCode24 + (mass23 != null ? mass23.hashCode() : 0)) * 31;
        Mass mass24 = this.saturatedFat;
        int iHashCode26 = (iHashCode25 + (mass24 != null ? mass24.hashCode() : 0)) * 31;
        Mass mass25 = this.selenium;
        int iHashCode27 = (iHashCode26 + (mass25 != null ? mass25.hashCode() : 0)) * 31;
        Mass mass26 = this.sodium;
        int iHashCode28 = (iHashCode27 + (mass26 != null ? mass26.hashCode() : 0)) * 31;
        Mass mass27 = this.sugar;
        int iHashCode29 = (iHashCode28 + (mass27 != null ? mass27.hashCode() : 0)) * 31;
        Mass mass28 = this.thiamin;
        int iHashCode30 = (iHashCode29 + (mass28 != null ? mass28.hashCode() : 0)) * 31;
        Mass mass29 = this.totalCarbohydrate;
        int iHashCode31 = (iHashCode30 + (mass29 != null ? mass29.hashCode() : 0)) * 31;
        Mass mass30 = this.totalFat;
        int iHashCode32 = (iHashCode31 + (mass30 != null ? mass30.hashCode() : 0)) * 31;
        Mass mass31 = this.transFat;
        int iHashCode33 = (iHashCode32 + (mass31 != null ? mass31.hashCode() : 0)) * 31;
        Mass mass32 = this.unsaturatedFat;
        int iHashCode34 = (iHashCode33 + (mass32 != null ? mass32.hashCode() : 0)) * 31;
        Mass mass33 = this.vitaminA;
        int iHashCode35 = (iHashCode34 + (mass33 != null ? mass33.hashCode() : 0)) * 31;
        Mass mass34 = this.vitaminB12;
        int iHashCode36 = (iHashCode35 + (mass34 != null ? mass34.hashCode() : 0)) * 31;
        Mass mass35 = this.vitaminB6;
        int iHashCode37 = (iHashCode36 + (mass35 != null ? mass35.hashCode() : 0)) * 31;
        Mass mass36 = this.vitaminC;
        int iHashCode38 = (iHashCode37 + (mass36 != null ? mass36.hashCode() : 0)) * 31;
        Mass mass37 = this.vitaminD;
        int iHashCode39 = (iHashCode38 + (mass37 != null ? mass37.hashCode() : 0)) * 31;
        Mass mass38 = this.vitaminE;
        int iHashCode40 = (iHashCode39 + (mass38 != null ? mass38.hashCode() : 0)) * 31;
        Mass mass39 = this.vitaminK;
        int iHashCode41 = (iHashCode40 + (mass39 != null ? mass39.hashCode() : 0)) * 31;
        Mass mass40 = this.zinc;
        int iHashCode42 = (iHashCode41 + (mass40 != null ? mass40.hashCode() : 0)) * 31;
        String str = this.name;
        int iHashCode43 = (((((iHashCode42 + (str != null ? str.hashCode() : 0)) * 31) + this.mealType) * 31) + getStartTime().hashCode()) * 31;
        ZoneOffset startZoneOffset = getStartZoneOffset();
        int iHashCode44 = (((iHashCode43 + (startZoneOffset != null ? startZoneOffset.hashCode() : 0)) * 31) + getEndTime().hashCode()) * 31;
        ZoneOffset endZoneOffset = getEndZoneOffset();
        return ((iHashCode44 + (endZoneOffset != null ? endZoneOffset.hashCode() : 0)) * 31) + getMetadata().hashCode();
    }

    public /* synthetic */ NutritionRecord(Instant instant, ZoneOffset zoneOffset, Instant instant2, ZoneOffset zoneOffset2, Mass mass, Mass mass2, Mass mass3, Energy energy, Energy energy2, Mass mass4, Mass mass5, Mass mass6, Mass mass7, Mass mass8, Mass mass9, Mass mass10, Mass mass11, Mass mass12, Mass mass13, Mass mass14, Mass mass15, Mass mass16, Mass mass17, Mass mass18, Mass mass19, Mass mass20, Mass mass21, Mass mass22, Mass mass23, Mass mass24, Mass mass25, Mass mass26, Mass mass27, Mass mass28, Mass mass29, Mass mass30, Mass mass31, Mass mass32, Mass mass33, Mass mass34, Mass mass35, Mass mass36, Mass mass37, Mass mass38, Mass mass39, Mass mass40, String str, int i2, androidx.health.connect.client.records.metadata.Metadata metadata, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(instant, zoneOffset, instant2, zoneOffset2, (i3 & 16) != 0 ? null : mass, (i3 & 32) != 0 ? null : mass2, (i3 & 64) != 0 ? null : mass3, (i3 & 128) != 0 ? null : energy, (i3 & 256) != 0 ? null : energy2, (i3 & 512) != 0 ? null : mass4, (i3 & 1024) != 0 ? null : mass5, (i3 & 2048) != 0 ? null : mass6, (i3 & 4096) != 0 ? null : mass7, (i3 & 8192) != 0 ? null : mass8, (i3 & 16384) != 0 ? null : mass9, (i3 & 32768) != 0 ? null : mass10, (i3 & 65536) != 0 ? null : mass11, (131072 & i3) != 0 ? null : mass12, (262144 & i3) != 0 ? null : mass13, (524288 & i3) != 0 ? null : mass14, (1048576 & i3) != 0 ? null : mass15, (2097152 & i3) != 0 ? null : mass16, (4194304 & i3) != 0 ? null : mass17, (8388608 & i3) != 0 ? null : mass18, (16777216 & i3) != 0 ? null : mass19, (33554432 & i3) != 0 ? null : mass20, (67108864 & i3) != 0 ? null : mass21, (134217728 & i3) != 0 ? null : mass22, (268435456 & i3) != 0 ? null : mass23, (536870912 & i3) != 0 ? null : mass24, (1073741824 & i3) != 0 ? null : mass25, (i3 & Integer.MIN_VALUE) != 0 ? null : mass26, (i4 & 1) != 0 ? null : mass27, (i4 & 2) != 0 ? null : mass28, (i4 & 4) != 0 ? null : mass29, (i4 & 8) != 0 ? null : mass30, (i4 & 16) != 0 ? null : mass31, (i4 & 32) != 0 ? null : mass32, (i4 & 64) != 0 ? null : mass33, (i4 & 128) != 0 ? null : mass34, (i4 & 256) != 0 ? null : mass35, (i4 & 512) != 0 ? null : mass36, (i4 & 1024) != 0 ? null : mass37, (i4 & 2048) != 0 ? null : mass38, (i4 & 4096) != 0 ? null : mass39, (i4 & 8192) != 0 ? null : mass40, (i4 & 16384) != 0 ? null : str, (i4 & 32768) != 0 ? 0 : i2, (i4 & 65536) != 0 ? androidx.health.connect.client.records.metadata.Metadata.EMPTY : metadata);
    }
}
