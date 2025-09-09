package androidx.health.platform.client.impl.data;

import android.os.Parcel;
import android.os.SharedMemory;
import android.system.OsConstants;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import com.taobao.accs.common.Constants;
import java.io.Closeable;
import java.nio.ByteBuffer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@RequiresApi(api = 27)
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J3\u0010\u0003\u001a\u0002H\u0004\"\b\b\u0000\u0010\u0004*\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u0002H\u00040\bH\u0007¢\u0006\u0002\u0010\nJ(\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0012H\u0007¨\u0006\u0013"}, d2 = {"Landroidx/health/platform/client/impl/data/SharedMemory27Impl;", "", "()V", "parseParcelUsingSharedMemory", "U", "source", "Landroid/os/Parcel;", "parser", "Lkotlin/Function1;", "", "(Landroid/os/Parcel;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "writeToParcelUsingSharedMemory", "", "name", "", "bytes", "dest", Constants.KEY_FLAGS, "", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class SharedMemory27Impl {

    @NotNull
    public static final SharedMemory27Impl INSTANCE = new SharedMemory27Impl();

    private SharedMemory27Impl() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v2, types: [byte[], java.lang.Object] */
    @DoNotInline
    @NotNull
    public final <U> U parseParcelUsingSharedMemory(@NotNull Parcel source, @NotNull Function1<? super byte[], ? extends U> parser) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(parser, "parser");
        Closeable closeable = (Closeable) SharedMemory.CREATOR.createFromParcel(source);
        try {
            ByteBuffer byteBufferMapReadOnly = b.a(closeable).mapReadOnly();
            Intrinsics.checkNotNullExpressionValue(byteBufferMapReadOnly, "memory.mapReadOnly()");
            ?? r1 = new byte[byteBufferMapReadOnly.remaining()];
            byteBufferMapReadOnly.get((byte[]) r1);
            U uInvoke = parser.invoke(r1);
            CloseableKt.closeFinally(closeable, null);
            return uInvoke;
        } finally {
        }
    }

    @DoNotInline
    public final void writeToParcelUsingSharedMemory(@NotNull String name, @NotNull byte[] bytes, @NotNull Parcel dest, int flags) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        Intrinsics.checkNotNullParameter(dest, "dest");
        SharedMemory sharedMemoryCreate = SharedMemory.create(name, bytes.length);
        try {
            SharedMemory sharedMemoryA = b.a(sharedMemoryCreate);
            int i2 = OsConstants.PROT_READ;
            sharedMemoryA.setProtect(OsConstants.PROT_WRITE | i2);
            sharedMemoryA.mapReadWrite().put(bytes);
            sharedMemoryA.setProtect(i2);
            sharedMemoryA.writeToParcel(dest, flags);
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(sharedMemoryCreate, null);
        } finally {
        }
    }
}
