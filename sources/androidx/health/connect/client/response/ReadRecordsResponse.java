package androidx.health.connect.client.response;

import androidx.exifinterface.media.ExifInterface;
import androidx.health.connect.client.records.Record;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u001f\b\u0000\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Landroidx/health/connect/client/response/ReadRecordsResponse;", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/health/connect/client/records/Record;", "", "records", "", "pageToken", "", "(Ljava/util/List;Ljava/lang/String;)V", "getPageToken", "()Ljava/lang/String;", "getRecords", "()Ljava/util/List;", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class ReadRecordsResponse<T extends Record> {

    @Nullable
    private final String pageToken;

    @NotNull
    private final List<T> records;

    /* JADX WARN: Multi-variable type inference failed */
    public ReadRecordsResponse(@NotNull List<? extends T> records, @Nullable String str) {
        Intrinsics.checkNotNullParameter(records, "records");
        this.records = records;
        this.pageToken = str;
    }

    @Nullable
    public final String getPageToken() {
        return this.pageToken;
    }

    @NotNull
    public final List<T> getRecords() {
        return this.records;
    }
}
