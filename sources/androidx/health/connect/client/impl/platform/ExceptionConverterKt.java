package androidx.health.connect.client.impl.platform;

import android.health.connect.HealthConnectException;
import android.os.RemoteException;
import androidx.annotation.RequiresApi;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@RequiresApi(api = 34)
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u00060\u0001j\u0002`\u0002*\u00020\u0003H\u0000¨\u0006\u0004"}, d2 = {"toKtException", "Ljava/lang/Exception;", "Lkotlin/Exception;", "Landroid/health/connect/HealthConnectException;", "connect-client_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class ExceptionConverterKt {
    @NotNull
    public static final Exception toKtException(@NotNull HealthConnectException healthConnectException) {
        Intrinsics.checkNotNullParameter(healthConnectException, "<this>");
        int errorCode = healthConnectException.getErrorCode();
        return errorCode != 3 ? errorCode != 4 ? errorCode != 5 ? errorCode != 6 ? new IllegalStateException(healthConnectException) : new RemoteException(healthConnectException.getMessage()) : new SecurityException(healthConnectException) : new IOException(healthConnectException) : new IllegalArgumentException(healthConnectException);
    }
}
