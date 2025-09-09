package androidx.media3.datasource;

import androidx.annotation.Nullable;
import androidx.media3.common.util.UnstableApi;
import java.io.IOException;

/* loaded from: classes.dex */
public class DataSourceException extends IOException {

    @UnstableApi
    @Deprecated
    public static final int POSITION_OUT_OF_RANGE = 2008;
    public final int reason;

    @UnstableApi
    public DataSourceException(int i2) {
        this.reason = i2;
    }

    @UnstableApi
    public static boolean isCausedByPositionOutOfRange(IOException iOException) {
        for (IOException cause = iOException; cause != null; cause = cause.getCause()) {
            if ((cause instanceof DataSourceException) && ((DataSourceException) cause).reason == 2008) {
                return true;
            }
        }
        return false;
    }

    @UnstableApi
    public DataSourceException(@Nullable Throwable th, int i2) {
        super(th);
        this.reason = i2;
    }

    @UnstableApi
    public DataSourceException(@Nullable String str, int i2) {
        super(str);
        this.reason = i2;
    }

    @UnstableApi
    public DataSourceException(@Nullable String str, @Nullable Throwable th, int i2) {
        super(str, th);
        this.reason = i2;
    }
}
