package androidx.health.connect.client.response;

import androidx.exifinterface.media.ExifInterface;
import androidx.health.connect.client.records.Record;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u000f\b\u0000\u0012\u0006\u0010\u0004\u001a\u00028\u0000¢\u0006\u0002\u0010\u0005R\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007¨\u0006\t"}, d2 = {"Landroidx/health/connect/client/response/ReadRecordResponse;", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/health/connect/client/records/Record;", "", "record", "(Landroidx/health/connect/client/records/Record;)V", "getRecord", "()Landroidx/health/connect/client/records/Record;", "Landroidx/health/connect/client/records/Record;", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class ReadRecordResponse<T extends Record> {

    @NotNull
    private final T record;

    public ReadRecordResponse(@NotNull T record) {
        Intrinsics.checkNotNullParameter(record, "record");
        this.record = record;
    }

    @NotNull
    public final T getRecord() {
        return this.record;
    }
}
