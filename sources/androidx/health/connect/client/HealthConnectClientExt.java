package androidx.health.connect.client;

import androidx.exifinterface.media.ExifInterface;
import androidx.health.connect.client.records.Record;
import androidx.health.connect.client.response.ReadRecordResponse;
import androidx.health.connect.client.time.TimeRangeFilter;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;

@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a)\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0086Hø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001a=\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u00020\u00042\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0086Hø\u0001\u0000¢\u0006\u0002\u0010\f\u001a/\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00020\u000e\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u00020\u00042\u0006\u0010\u000f\u001a\u00020\nH\u0086Hø\u0001\u0000¢\u0006\u0002\u0010\u0010\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0011"}, d2 = {"deleteRecords", "", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/health/connect/client/records/Record;", "Landroidx/health/connect/client/HealthConnectClient;", "timeRangeFilter", "Landroidx/health/connect/client/time/TimeRangeFilter;", "(Landroidx/health/connect/client/HealthConnectClient;Landroidx/health/connect/client/time/TimeRangeFilter;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "recordIdsList", "", "", "clientRecordIdsList", "(Landroidx/health/connect/client/HealthConnectClient;Ljava/util/List;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "readRecord", "Landroidx/health/connect/client/response/ReadRecordResponse;", "recordId", "(Landroidx/health/connect/client/HealthConnectClient;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@JvmName(name = "HealthConnectClientExt")
/* loaded from: classes.dex */
public final class HealthConnectClientExt {
    public static final /* synthetic */ <T extends Record> Object deleteRecords(HealthConnectClient healthConnectClient, List<String> list, List<String> list2, Continuation<? super Unit> continuation) {
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        KClass<? extends Record> orCreateKotlinClass = Reflection.getOrCreateKotlinClass(Record.class);
        InlineMarker.mark(0);
        healthConnectClient.deleteRecords(orCreateKotlinClass, list, list2, continuation);
        InlineMarker.mark(1);
        return Unit.INSTANCE;
    }

    public static final /* synthetic */ <T extends Record> Object readRecord(HealthConnectClient healthConnectClient, String str, Continuation<? super ReadRecordResponse<T>> continuation) {
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        KClass<T> orCreateKotlinClass = Reflection.getOrCreateKotlinClass(Record.class);
        InlineMarker.mark(0);
        Object record = healthConnectClient.readRecord(orCreateKotlinClass, str, continuation);
        InlineMarker.mark(1);
        return record;
    }

    public static final /* synthetic */ <T extends Record> Object deleteRecords(HealthConnectClient healthConnectClient, TimeRangeFilter timeRangeFilter, Continuation<? super Unit> continuation) {
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        KClass<? extends Record> orCreateKotlinClass = Reflection.getOrCreateKotlinClass(Record.class);
        InlineMarker.mark(0);
        healthConnectClient.deleteRecords(orCreateKotlinClass, timeRangeFilter, continuation);
        InlineMarker.mark(1);
        return Unit.INSTANCE;
    }
}
