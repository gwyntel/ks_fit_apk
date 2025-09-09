package androidx.health.connect.client.impl.converters.records;

import androidx.annotation.RestrictTo;
import androidx.health.connect.client.records.ExerciseLap;
import androidx.health.connect.client.records.ExerciseRoute;
import androidx.health.connect.client.records.ExerciseSegment;
import androidx.health.connect.client.records.SleepSessionRecord;
import androidx.health.connect.client.records.metadata.DataOrigin;
import androidx.health.connect.client.records.metadata.Device;
import androidx.health.connect.client.units.Length;
import androidx.health.connect.client.units.LengthKt;
import androidx.health.platform.client.proto.DataProto;
import androidx.media3.exoplayer.rtsp.SessionDescription;
import androidx.media3.extractor.text.ttml.TtmlNode;
import com.aliyun.alink.linksdk.tools.ut.AUserTrack;
import com.kingsmith.miot.KsProperty;
import com.taobao.accs.AccsClientConfig;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000v\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0015\u001a\u00020\u0016*\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u0016H\u0000\u001a\u001e\u0010\u0015\u001a\u00020\u0016*\u00020\u001b2\u0006\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u0016H\u0000\u001a\u0016\u0010\u001c\u001a\u0004\u0018\u00010\u0019*\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0000\u001a\u0016\u0010\u001c\u001a\u0004\u0018\u00010\u0019*\u00020\u001b2\u0006\u0010\u0018\u001a\u00020\u0019H\u0000\u001a\u001e\u0010\u001d\u001a\u00020\u001e*\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u001eH\u0000\u001a\u001e\u0010\u001d\u001a\u00020\u001e*\u00020\u001b2\u0006\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u001eH\u0000\u001a\u0016\u0010\u001f\u001a\u0004\u0018\u00010\u0019*\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0000\u001a\u0016\u0010\u001f\u001a\u0004\u0018\u00010\u0019*\u00020\u001b2\u0006\u0010\u0018\u001a\u00020\u0019H\u0000\u001a0\u0010 \u001a\u00020!*\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020!0#2\u0006\u0010$\u001a\u00020!H\u0000\u001a\f\u0010%\u001a\u00020&*\u00020'H\u0000\u001a\u0012\u0010(\u001a\b\u0012\u0004\u0012\u00020*0)*\u00020+H\u0000\u001a\u0012\u0010,\u001a\b\u0012\u0004\u0012\u00020-0)*\u00020+H\u0000\u001a\u0012\u0010.\u001a\b\u0012\u0004\u0012\u00020/0)*\u00020+H\u0000\u001a\u0012\u00100\u001a\b\u0012\u0004\u0012\u0002010)*\u00020+H\u0000\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\"\u0018\u0010\t\u001a\u00020\n*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f\"\u0018\u0010\r\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0004\"\u001a\u0010\u000f\u001a\u0004\u0018\u00010\u0006*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\b\"\u0018\u0010\u0011\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0004\"\u001a\u0010\u0013\u001a\u0004\u0018\u00010\u0006*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\b¨\u00062"}, d2 = {AUserTrack.UTKEY_END_TIME, "Ljava/time/Instant;", "Landroidx/health/platform/client/proto/DataProto$DataPoint;", "getEndTime", "(Landroidx/health/platform/client/proto/DataProto$DataPoint;)Ljava/time/Instant;", "endZoneOffset", "Ljava/time/ZoneOffset;", "getEndZoneOffset", "(Landroidx/health/platform/client/proto/DataProto$DataPoint;)Ljava/time/ZoneOffset;", TtmlNode.TAG_METADATA, "Landroidx/health/connect/client/records/metadata/Metadata;", "getMetadata", "(Landroidx/health/platform/client/proto/DataProto$DataPoint;)Landroidx/health/connect/client/records/metadata/Metadata;", "startTime", "getStartTime", "startZoneOffset", "getStartZoneOffset", "time", "getTime", "zoneOffset", "getZoneOffset", "getDouble", "", "Landroidx/health/platform/client/proto/DataProto$DataPointOrBuilder;", KsProperty.Key, "", "defaultVal", "Landroidx/health/platform/client/proto/DataProto$SeriesValueOrBuilder;", "getEnum", "getLong", "", "getString", "mapEnum", "", "stringToIntMap", "", AccsClientConfig.DEFAULT_CONFIGTAG, "toDevice", "Landroidx/health/connect/client/records/metadata/Device;", "Landroidx/health/platform/client/proto/DataProto$Device;", "toLapList", "", "Landroidx/health/connect/client/records/ExerciseLap;", "Landroidx/health/platform/client/proto/DataProto$DataPoint$SubTypeDataList;", "toLocationList", "Landroidx/health/connect/client/records/ExerciseRoute$Location;", "toSegmentList", "Landroidx/health/connect/client/records/ExerciseSegment;", "toStageList", "Landroidx/health/connect/client/records/SleepSessionRecord$Stage;", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@RestrictTo({RestrictTo.Scope.LIBRARY})
@SourceDebugExtension({"SMAP\nProtoToRecordUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ProtoToRecordUtils.kt\nandroidx/health/connect/client/impl/converters/records/ProtoToRecordUtilsKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,160:1\n1549#2:161\n1620#2,3:162\n1549#2:165\n1620#2,3:166\n1549#2:169\n1620#2,3:170\n1549#2:173\n1620#2,3:174\n*S KotlinDebug\n*F\n+ 1 ProtoToRecordUtils.kt\nandroidx/health/connect/client/impl/converters/records/ProtoToRecordUtilsKt\n*L\n117#1:161\n117#1:162,3\n128#1:165\n128#1:166,3\n139#1:169\n139#1:170,3\n149#1:173\n149#1:174,3\n*E\n"})
/* loaded from: classes.dex */
public final class ProtoToRecordUtilsKt {
    public static final double getDouble(@NotNull DataProto.DataPointOrBuilder dataPointOrBuilder, @NotNull String key, double d2) {
        Intrinsics.checkNotNullParameter(dataPointOrBuilder, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        DataProto.Value value = dataPointOrBuilder.getValuesMap().get(key);
        return value != null ? value.getDoubleVal() : d2;
    }

    public static /* synthetic */ double getDouble$default(DataProto.DataPointOrBuilder dataPointOrBuilder, String str, double d2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            d2 = 0.0d;
        }
        return getDouble(dataPointOrBuilder, str, d2);
    }

    @NotNull
    public static final Instant getEndTime(@NotNull DataProto.DataPoint dataPoint) {
        Intrinsics.checkNotNullParameter(dataPoint, "<this>");
        Instant instantOfEpochMilli = Instant.ofEpochMilli(dataPoint.getEndTimeMillis());
        Intrinsics.checkNotNullExpressionValue(instantOfEpochMilli, "ofEpochMilli(endTimeMillis)");
        return instantOfEpochMilli;
    }

    @Nullable
    public static final ZoneOffset getEndZoneOffset(@NotNull DataProto.DataPoint dataPoint) {
        Intrinsics.checkNotNullParameter(dataPoint, "<this>");
        if (dataPoint.hasEndZoneOffsetSeconds()) {
            return ZoneOffset.ofTotalSeconds(dataPoint.getEndZoneOffsetSeconds());
        }
        return null;
    }

    @Nullable
    public static final String getEnum(@NotNull DataProto.DataPointOrBuilder dataPointOrBuilder, @NotNull String key) {
        Intrinsics.checkNotNullParameter(dataPointOrBuilder, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        DataProto.Value value = dataPointOrBuilder.getValuesMap().get(key);
        if (value != null) {
            return value.getEnumVal();
        }
        return null;
    }

    public static final long getLong(@NotNull DataProto.DataPointOrBuilder dataPointOrBuilder, @NotNull String key, long j2) {
        Intrinsics.checkNotNullParameter(dataPointOrBuilder, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        DataProto.Value value = dataPointOrBuilder.getValuesMap().get(key);
        return value != null ? value.getLongVal() : j2;
    }

    public static /* synthetic */ long getLong$default(DataProto.DataPointOrBuilder dataPointOrBuilder, String str, long j2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            j2 = 0;
        }
        return getLong(dataPointOrBuilder, str, j2);
    }

    @NotNull
    public static final androidx.health.connect.client.records.metadata.Metadata getMetadata(@NotNull DataProto.DataPoint dataPoint) {
        Device device;
        Intrinsics.checkNotNullParameter(dataPoint, "<this>");
        String uid = dataPoint.hasUid() ? dataPoint.getUid() : "";
        Intrinsics.checkNotNullExpressionValue(uid, "if (hasUid()) uid else Metadata.EMPTY_ID");
        String applicationId = dataPoint.getDataOrigin().getApplicationId();
        Intrinsics.checkNotNullExpressionValue(applicationId, "dataOrigin.applicationId");
        DataOrigin dataOrigin = new DataOrigin(applicationId);
        Instant instantOfEpochMilli = Instant.ofEpochMilli(dataPoint.getUpdateTimeMillis());
        Intrinsics.checkNotNullExpressionValue(instantOfEpochMilli, "ofEpochMilli(updateTimeMillis)");
        String clientId = dataPoint.hasClientId() ? dataPoint.getClientId() : null;
        long clientVersion = dataPoint.getClientVersion();
        if (dataPoint.hasDevice()) {
            DataProto.Device device2 = dataPoint.getDevice();
            Intrinsics.checkNotNullExpressionValue(device2, "device");
            device = toDevice(device2);
        } else {
            device = null;
        }
        return new androidx.health.connect.client.records.metadata.Metadata(uid, dataOrigin, instantOfEpochMilli, clientId, clientVersion, device, dataPoint.getRecordingMethod());
    }

    @NotNull
    public static final Instant getStartTime(@NotNull DataProto.DataPoint dataPoint) {
        Intrinsics.checkNotNullParameter(dataPoint, "<this>");
        Instant instantOfEpochMilli = Instant.ofEpochMilli(dataPoint.getStartTimeMillis());
        Intrinsics.checkNotNullExpressionValue(instantOfEpochMilli, "ofEpochMilli(startTimeMillis)");
        return instantOfEpochMilli;
    }

    @Nullable
    public static final ZoneOffset getStartZoneOffset(@NotNull DataProto.DataPoint dataPoint) {
        Intrinsics.checkNotNullParameter(dataPoint, "<this>");
        if (dataPoint.hasStartZoneOffsetSeconds()) {
            return ZoneOffset.ofTotalSeconds(dataPoint.getStartZoneOffsetSeconds());
        }
        return null;
    }

    @Nullable
    public static final String getString(@NotNull DataProto.DataPointOrBuilder dataPointOrBuilder, @NotNull String key) {
        Intrinsics.checkNotNullParameter(dataPointOrBuilder, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        DataProto.Value value = dataPointOrBuilder.getValuesMap().get(key);
        if (value != null) {
            return value.getStringVal();
        }
        return null;
    }

    @NotNull
    public static final Instant getTime(@NotNull DataProto.DataPoint dataPoint) {
        Intrinsics.checkNotNullParameter(dataPoint, "<this>");
        Instant instantOfEpochMilli = Instant.ofEpochMilli(dataPoint.getInstantTimeMillis());
        Intrinsics.checkNotNullExpressionValue(instantOfEpochMilli, "ofEpochMilli(instantTimeMillis)");
        return instantOfEpochMilli;
    }

    @Nullable
    public static final ZoneOffset getZoneOffset(@NotNull DataProto.DataPoint dataPoint) {
        Intrinsics.checkNotNullParameter(dataPoint, "<this>");
        if (dataPoint.hasZoneOffsetSeconds()) {
            return ZoneOffset.ofTotalSeconds(dataPoint.getZoneOffsetSeconds());
        }
        return null;
    }

    public static final int mapEnum(@NotNull DataProto.DataPointOrBuilder dataPointOrBuilder, @NotNull String key, @NotNull Map<String, Integer> stringToIntMap, int i2) {
        Intrinsics.checkNotNullParameter(dataPointOrBuilder, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(stringToIntMap, "stringToIntMap");
        String str = getEnum(dataPointOrBuilder, key);
        return str == null ? i2 : ((Number) stringToIntMap.getOrDefault(str, Integer.valueOf(i2))).intValue();
    }

    @NotNull
    public static final Device toDevice(@NotNull DataProto.Device device) {
        Intrinsics.checkNotNullParameter(device, "<this>");
        String manufacturer = device.hasManufacturer() ? device.getManufacturer() : null;
        String model = device.hasModel() ? device.getModel() : null;
        Map<String, Integer> device_type_string_to_int_map = DeviceTypeConvertersKt.getDEVICE_TYPE_STRING_TO_INT_MAP();
        String type = device.getType();
        Intrinsics.checkNotNullExpressionValue(type, "type");
        return new Device(manufacturer, model, ((Number) device_type_string_to_int_map.getOrDefault(type, 0)).intValue());
    }

    @NotNull
    public static final List<ExerciseLap> toLapList(@NotNull DataProto.DataPoint.SubTypeDataList subTypeDataList) {
        Intrinsics.checkNotNullParameter(subTypeDataList, "<this>");
        List<DataProto.SubTypeDataValue> valuesList = subTypeDataList.getValuesList();
        Intrinsics.checkNotNullExpressionValue(valuesList, "valuesList");
        List<DataProto.SubTypeDataValue> list = valuesList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (DataProto.SubTypeDataValue subTypeDataValue : list) {
            Instant instantOfEpochMilli = Instant.ofEpochMilli(subTypeDataValue.getStartTimeMillis());
            Intrinsics.checkNotNullExpressionValue(instantOfEpochMilli, "ofEpochMilli(it.startTimeMillis)");
            Instant instantOfEpochMilli2 = Instant.ofEpochMilli(subTypeDataValue.getEndTimeMillis());
            Intrinsics.checkNotNullExpressionValue(instantOfEpochMilli2, "ofEpochMilli(it.endTimeMillis)");
            DataProto.Value value = subTypeDataValue.getValuesMap().get(SessionDescription.ATTR_LENGTH);
            arrayList.add(new ExerciseLap(instantOfEpochMilli, instantOfEpochMilli2, value != null ? LengthKt.getMeters(value.getDoubleVal()) : null));
        }
        return arrayList;
    }

    @NotNull
    public static final List<ExerciseRoute.Location> toLocationList(@NotNull DataProto.DataPoint.SubTypeDataList subTypeDataList) {
        Intrinsics.checkNotNullParameter(subTypeDataList, "<this>");
        List<DataProto.SubTypeDataValue> valuesList = subTypeDataList.getValuesList();
        Intrinsics.checkNotNullExpressionValue(valuesList, "valuesList");
        List<DataProto.SubTypeDataValue> list = valuesList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (DataProto.SubTypeDataValue subTypeDataValue : list) {
            Instant instantOfEpochMilli = Instant.ofEpochMilli(subTypeDataValue.getStartTimeMillis());
            DataProto.Value value = subTypeDataValue.getValuesMap().get("latitude");
            double doubleVal = value != null ? value.getDoubleVal() : 0.0d;
            DataProto.Value value2 = subTypeDataValue.getValuesMap().get("longitude");
            double doubleVal2 = value2 != null ? value2.getDoubleVal() : 0.0d;
            DataProto.Value value3 = subTypeDataValue.getValuesMap().get("altitude");
            Length meters = value3 != null ? LengthKt.getMeters(value3.getDoubleVal()) : null;
            DataProto.Value value4 = subTypeDataValue.getValuesMap().get("horizontal_accuracy");
            Length meters2 = value4 != null ? LengthKt.getMeters(value4.getDoubleVal()) : null;
            DataProto.Value value5 = subTypeDataValue.getValuesMap().get("vertical_accuracy");
            Length meters3 = value5 != null ? LengthKt.getMeters(value5.getDoubleVal()) : null;
            Intrinsics.checkNotNullExpressionValue(instantOfEpochMilli, "ofEpochMilli(it.startTimeMillis)");
            arrayList.add(new ExerciseRoute.Location(instantOfEpochMilli, doubleVal, doubleVal2, meters2, meters3, meters));
        }
        return arrayList;
    }

    @NotNull
    public static final List<ExerciseSegment> toSegmentList(@NotNull DataProto.DataPoint.SubTypeDataList subTypeDataList) {
        Intrinsics.checkNotNullParameter(subTypeDataList, "<this>");
        List<DataProto.SubTypeDataValue> valuesList = subTypeDataList.getValuesList();
        Intrinsics.checkNotNullExpressionValue(valuesList, "valuesList");
        List<DataProto.SubTypeDataValue> list = valuesList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (DataProto.SubTypeDataValue subTypeDataValue : list) {
            Instant instantOfEpochMilli = Instant.ofEpochMilli(subTypeDataValue.getStartTimeMillis());
            Intrinsics.checkNotNullExpressionValue(instantOfEpochMilli, "ofEpochMilli(it.startTimeMillis)");
            Instant instantOfEpochMilli2 = Instant.ofEpochMilli(subTypeDataValue.getEndTimeMillis());
            Intrinsics.checkNotNullExpressionValue(instantOfEpochMilli2, "ofEpochMilli(it.endTimeMillis)");
            DataProto.Value value = subTypeDataValue.getValuesMap().get("type");
            int longVal = 0;
            int iIntValue = (value != null ? Long.valueOf(value.getLongVal()) : 0).intValue();
            DataProto.Value value2 = subTypeDataValue.getValuesMap().get("reps");
            if (value2 != null) {
                longVal = (int) value2.getLongVal();
            }
            arrayList.add(new ExerciseSegment(instantOfEpochMilli, instantOfEpochMilli2, iIntValue, longVal));
        }
        return arrayList;
    }

    @NotNull
    public static final List<SleepSessionRecord.Stage> toStageList(@NotNull DataProto.DataPoint.SubTypeDataList subTypeDataList) {
        Intrinsics.checkNotNullParameter(subTypeDataList, "<this>");
        List<DataProto.SubTypeDataValue> valuesList = subTypeDataList.getValuesList();
        Intrinsics.checkNotNullExpressionValue(valuesList, "valuesList");
        List<DataProto.SubTypeDataValue> list = valuesList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (DataProto.SubTypeDataValue subTypeDataValue : list) {
            Instant instantOfEpochMilli = Instant.ofEpochMilli(subTypeDataValue.getStartTimeMillis());
            Intrinsics.checkNotNullExpressionValue(instantOfEpochMilli, "ofEpochMilli(it.startTimeMillis)");
            Instant instantOfEpochMilli2 = Instant.ofEpochMilli(subTypeDataValue.getEndTimeMillis());
            Intrinsics.checkNotNullExpressionValue(instantOfEpochMilli2, "ofEpochMilli(it.endTimeMillis)");
            Map<String, Integer> map = SleepSessionRecord.STAGE_TYPE_STRING_TO_INT_MAP;
            DataProto.Value value = subTypeDataValue.getValuesMap().get("stage");
            Integer num = map.get(value != null ? value.getEnumVal() : null);
            arrayList.add(new SleepSessionRecord.Stage(instantOfEpochMilli, instantOfEpochMilli2, num != null ? num.intValue() : 0));
        }
        return arrayList;
    }

    public static final double getDouble(@NotNull DataProto.SeriesValueOrBuilder seriesValueOrBuilder, @NotNull String key, double d2) {
        Intrinsics.checkNotNullParameter(seriesValueOrBuilder, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        DataProto.Value value = seriesValueOrBuilder.getValuesMap().get(key);
        return value != null ? value.getDoubleVal() : d2;
    }

    public static /* synthetic */ double getDouble$default(DataProto.SeriesValueOrBuilder seriesValueOrBuilder, String str, double d2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            d2 = 0.0d;
        }
        return getDouble(seriesValueOrBuilder, str, d2);
    }

    @Nullable
    public static final String getEnum(@NotNull DataProto.SeriesValueOrBuilder seriesValueOrBuilder, @NotNull String key) {
        Intrinsics.checkNotNullParameter(seriesValueOrBuilder, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        DataProto.Value value = seriesValueOrBuilder.getValuesMap().get(key);
        if (value != null) {
            return value.getEnumVal();
        }
        return null;
    }

    public static final long getLong(@NotNull DataProto.SeriesValueOrBuilder seriesValueOrBuilder, @NotNull String key, long j2) {
        Intrinsics.checkNotNullParameter(seriesValueOrBuilder, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        DataProto.Value value = seriesValueOrBuilder.getValuesMap().get(key);
        return value != null ? value.getLongVal() : j2;
    }

    public static /* synthetic */ long getLong$default(DataProto.SeriesValueOrBuilder seriesValueOrBuilder, String str, long j2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            j2 = 0;
        }
        return getLong(seriesValueOrBuilder, str, j2);
    }

    @Nullable
    public static final String getString(@NotNull DataProto.SeriesValueOrBuilder seriesValueOrBuilder, @NotNull String key) {
        Intrinsics.checkNotNullParameter(seriesValueOrBuilder, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        DataProto.Value value = seriesValueOrBuilder.getValuesMap().get(key);
        if (value != null) {
            return value.getStringVal();
        }
        return null;
    }
}
