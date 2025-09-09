package androidx.health.connect.client.impl.converters.records;

import androidx.annotation.RestrictTo;
import androidx.health.connect.client.records.ExerciseLap;
import androidx.health.connect.client.records.ExerciseRoute;
import androidx.health.connect.client.records.ExerciseSegment;
import androidx.health.connect.client.records.InstantaneousRecord;
import androidx.health.connect.client.records.IntervalRecord;
import androidx.health.connect.client.records.SleepSessionRecord;
import androidx.health.connect.client.records.metadata.Device;
import androidx.health.connect.client.units.Length;
import androidx.health.platform.client.proto.DataProto;
import androidx.media3.exoplayer.rtsp.SessionDescription;
import androidx.media3.extractor.text.ttml.TtmlNode;
import java.time.Instant;
import java.time.ZoneOffset;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000D\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0000\u001a\f\u0010\u0004\u001a\u00020\u0005*\u00020\u0006H\u0000\u001a\f\u0010\u0007\u001a\u00020\u0005*\u00020\bH\u0000\u001a\u0014\u0010\t\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\n\u001a\u00020\u000bH\u0002\u001a\f\u0010\f\u001a\u00020\r*\u00020\u000eH\u0000\u001a\f\u0010\f\u001a\u00020\r*\u00020\u000fH\u0000\u001a\f\u0010\f\u001a\u00020\r*\u00020\u0010H\u0000\u001a\f\u0010\f\u001a\u00020\r*\u00020\u0011H\u0000\u001a\f\u0010\f\u001a\u00020\u0012*\u00020\u0013H\u0000¨\u0006\u0014"}, d2 = {"protoDataType", "Landroidx/health/platform/client/proto/DataProto$DataType;", "dataTypeName", "", "instantaneousProto", "Landroidx/health/platform/client/proto/DataProto$DataPoint$Builder;", "Landroidx/health/connect/client/records/InstantaneousRecord;", "intervalProto", "Landroidx/health/connect/client/records/IntervalRecord;", "setMetadata", TtmlNode.TAG_METADATA, "Landroidx/health/connect/client/records/metadata/Metadata;", "toProto", "Landroidx/health/platform/client/proto/DataProto$SubTypeDataValue;", "Landroidx/health/connect/client/records/ExerciseLap;", "Landroidx/health/connect/client/records/ExerciseRoute$Location;", "Landroidx/health/connect/client/records/ExerciseSegment;", "Landroidx/health/connect/client/records/SleepSessionRecord$Stage;", "Landroidx/health/platform/client/proto/DataProto$Device;", "Landroidx/health/connect/client/records/metadata/Device;", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@RestrictTo({RestrictTo.Scope.LIBRARY})
@SourceDebugExtension({"SMAP\nRecordToProtoUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RecordToProtoUtils.kt\nandroidx/health/connect/client/impl/converters/records/RecordToProtoUtilsKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,137:1\n1#2:138\n*E\n"})
/* loaded from: classes.dex */
public final class RecordToProtoUtilsKt {
    @NotNull
    public static final DataProto.DataPoint.Builder instantaneousProto(@NotNull InstantaneousRecord instantaneousRecord) {
        Intrinsics.checkNotNullParameter(instantaneousRecord, "<this>");
        DataProto.DataPoint.Builder builderNewBuilder = DataProto.DataPoint.newBuilder();
        Intrinsics.checkNotNullExpressionValue(builderNewBuilder, "newBuilder()");
        DataProto.DataPoint.Builder builder = setMetadata(builderNewBuilder, instantaneousRecord.getMetadata()).setInstantTimeMillis(instantaneousRecord.getTime().toEpochMilli());
        ZoneOffset zoneOffset = instantaneousRecord.getZoneOffset();
        if (zoneOffset != null) {
            builder.setZoneOffsetSeconds(zoneOffset.getTotalSeconds());
        }
        Intrinsics.checkNotNullExpressionValue(builder, "builder");
        return builder;
    }

    @NotNull
    public static final DataProto.DataPoint.Builder intervalProto(@NotNull IntervalRecord intervalRecord) {
        Intrinsics.checkNotNullParameter(intervalRecord, "<this>");
        DataProto.DataPoint.Builder builderNewBuilder = DataProto.DataPoint.newBuilder();
        Intrinsics.checkNotNullExpressionValue(builderNewBuilder, "newBuilder()");
        DataProto.DataPoint.Builder builder = setMetadata(builderNewBuilder, intervalRecord.getMetadata()).setStartTimeMillis(intervalRecord.getStartTime().toEpochMilli()).setEndTimeMillis(intervalRecord.getEndTime().toEpochMilli());
        ZoneOffset startZoneOffset = intervalRecord.getStartZoneOffset();
        if (startZoneOffset != null) {
            builder.setStartZoneOffsetSeconds(startZoneOffset.getTotalSeconds());
        }
        ZoneOffset endZoneOffset = intervalRecord.getEndZoneOffset();
        if (endZoneOffset != null) {
            builder.setEndZoneOffsetSeconds(endZoneOffset.getTotalSeconds());
        }
        Intrinsics.checkNotNullExpressionValue(builder, "builder");
        return builder;
    }

    @NotNull
    public static final DataProto.DataType protoDataType(@NotNull String dataTypeName) {
        Intrinsics.checkNotNullParameter(dataTypeName, "dataTypeName");
        DataProto.DataType dataTypeBuild = DataProto.DataType.newBuilder().setName(dataTypeName).build();
        Intrinsics.checkNotNullExpressionValue(dataTypeBuild, "newBuilder().setName(dataTypeName).build()");
        return dataTypeBuild;
    }

    private static final DataProto.DataPoint.Builder setMetadata(DataProto.DataPoint.Builder builder, androidx.health.connect.client.records.metadata.Metadata metadata) {
        if (!Intrinsics.areEqual(metadata.getId(), "")) {
            builder.setUid(metadata.getId());
        }
        if (metadata.getDataOrigin().getPackageName().length() > 0) {
            builder.setDataOrigin(DataProto.DataOrigin.newBuilder().setApplicationId(metadata.getDataOrigin().getPackageName()).build());
        }
        if (metadata.getLastModifiedTime().isAfter(Instant.EPOCH)) {
            builder.setUpdateTimeMillis(metadata.getLastModifiedTime().toEpochMilli());
        }
        String clientRecordId = metadata.getClientRecordId();
        if (clientRecordId != null) {
            builder.setClientId(clientRecordId);
        }
        if (metadata.getClientRecordVersion() > 0) {
            builder.setClientVersion(metadata.getClientRecordVersion());
        }
        Device device = metadata.getDevice();
        if (device != null) {
            builder.setDevice(toProto(device));
        }
        if (metadata.getRecordingMethod() > 0) {
            builder.setRecordingMethod(metadata.getRecordingMethod());
        }
        return builder;
    }

    @NotNull
    public static final DataProto.Device toProto(@NotNull Device device) {
        Intrinsics.checkNotNullParameter(device, "<this>");
        DataProto.Device.Builder builderNewBuilder = DataProto.Device.newBuilder();
        String manufacturer = device.getManufacturer();
        if (manufacturer != null) {
            builderNewBuilder.setManufacturer(manufacturer);
        }
        String model = device.getModel();
        if (model != null) {
            builderNewBuilder.setModel(model);
        }
        builderNewBuilder.setType((String) DeviceTypeConvertersKt.getDEVICE_TYPE_INT_TO_STRING_MAP().getOrDefault(Integer.valueOf(device.getType()), "UNKNOWN"));
        DataProto.Device deviceBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(deviceBuild, "newBuilder()\n        .ap…       }\n        .build()");
        return deviceBuild;
    }

    @NotNull
    public static final DataProto.SubTypeDataValue toProto(@NotNull SleepSessionRecord.Stage stage) {
        Intrinsics.checkNotNullParameter(stage, "<this>");
        DataProto.SubTypeDataValue.Builder endTimeMillis = DataProto.SubTypeDataValue.newBuilder().setStartTimeMillis(stage.getStartTime().toEpochMilli()).setEndTimeMillis(stage.getEndTime().toEpochMilli());
        DataProto.Value valueEnumValFromInt = ValueExtKt.enumValFromInt(stage.getStage(), SleepSessionRecord.STAGE_TYPE_INT_TO_STRING_MAP);
        if (valueEnumValFromInt != null) {
            endTimeMillis.putValues("stage", valueEnumValFromInt);
        }
        DataProto.SubTypeDataValue subTypeDataValueBuild = endTimeMillis.build();
        Intrinsics.checkNotNullExpressionValue(subTypeDataValueBuild, "newBuilder()\n        .se…       }\n        .build()");
        return subTypeDataValueBuild;
    }

    @NotNull
    public static final DataProto.SubTypeDataValue toProto(@NotNull ExerciseSegment exerciseSegment) {
        Intrinsics.checkNotNullParameter(exerciseSegment, "<this>");
        DataProto.SubTypeDataValue subTypeDataValueBuild = DataProto.SubTypeDataValue.newBuilder().setStartTimeMillis(exerciseSegment.getStartTime().toEpochMilli()).setEndTimeMillis(exerciseSegment.getEndTime().toEpochMilli()).putValues("type", ValueExtKt.longVal(exerciseSegment.getSegmentType())).putValues("reps", ValueExtKt.longVal(exerciseSegment.getRepetitions())).build();
        Intrinsics.checkNotNullExpressionValue(subTypeDataValueBuild, "newBuilder()\n        .se…Long()))\n        .build()");
        return subTypeDataValueBuild;
    }

    @NotNull
    public static final DataProto.SubTypeDataValue toProto(@NotNull ExerciseLap exerciseLap) {
        Intrinsics.checkNotNullParameter(exerciseLap, "<this>");
        DataProto.SubTypeDataValue.Builder endTimeMillis = DataProto.SubTypeDataValue.newBuilder().setStartTimeMillis(exerciseLap.getStartTime().toEpochMilli()).setEndTimeMillis(exerciseLap.getEndTime().toEpochMilli());
        Length length = exerciseLap.getLength();
        if (length != null) {
            endTimeMillis.putValues(SessionDescription.ATTR_LENGTH, ValueExtKt.doubleVal(length.getMeters()));
        }
        DataProto.SubTypeDataValue subTypeDataValueBuild = endTimeMillis.build();
        Intrinsics.checkNotNullExpressionValue(subTypeDataValueBuild, "newBuilder()\n        .se…rs)) } }\n        .build()");
        return subTypeDataValueBuild;
    }

    @NotNull
    public static final DataProto.SubTypeDataValue toProto(@NotNull ExerciseRoute.Location location) {
        Intrinsics.checkNotNullParameter(location, "<this>");
        DataProto.SubTypeDataValue.Builder builderPutValues = DataProto.SubTypeDataValue.newBuilder().setStartTimeMillis(location.getTime().toEpochMilli()).setEndTimeMillis(location.getTime().toEpochMilli()).putValues("latitude", ValueExtKt.doubleVal(location.getLatitude())).putValues("longitude", ValueExtKt.doubleVal(location.getLongitude()));
        Length horizontalAccuracy = location.getHorizontalAccuracy();
        if (horizontalAccuracy != null) {
            builderPutValues.putValues("horizontal_accuracy", ValueExtKt.doubleVal(horizontalAccuracy.getMeters()));
        }
        Length verticalAccuracy = location.getVerticalAccuracy();
        if (verticalAccuracy != null) {
            builderPutValues.putValues("vertical_accuracy", ValueExtKt.doubleVal(verticalAccuracy.getMeters()));
        }
        Length altitude = location.getAltitude();
        if (altitude != null) {
            builderPutValues.putValues("altitude", ValueExtKt.doubleVal(altitude.getMeters()));
        }
        DataProto.SubTypeDataValue subTypeDataValueBuild = builderPutValues.build();
        Intrinsics.checkNotNullExpressionValue(subTypeDataValueBuild, "newBuilder()\n        .se…       }\n        .build()");
        return subTypeDataValueBuild;
    }
}
