package xyz.luan.audioplayers.source;

import android.media.MediaDataSource;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@RequiresApi(23)
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\b\u0010\f\u001a\u00020\u000bH\u0016J(\u0010\r\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lxyz/luan/audioplayers/source/ByteDataSource;", "Landroid/media/MediaDataSource;", "data", "", "([B)V", "close", "", "computeRemainingSize", "", "size", RequestParameters.POSITION, "", "getSize", "readAt", "buffer", TypedValues.CycleType.S_WAVE_OFFSET, "audioplayers_android_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ByteDataSource extends MediaDataSource {

    @NotNull
    private final byte[] data;

    public ByteDataSource(@NotNull byte[] data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.data = data;
    }

    private final int computeRemainingSize(int size, long position) {
        long length = size;
        long j2 = position + length;
        byte[] bArr = this.data;
        if (j2 > bArr.length) {
            length -= j2 - bArr.length;
        }
        return (int) length;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() {
    }

    @Override // android.media.MediaDataSource
    public synchronized long getSize() {
        return this.data.length;
    }

    @Override // android.media.MediaDataSource
    public synchronized int readAt(long position, @NotNull byte[] buffer, int offset, int size) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        if (position >= this.data.length) {
            return -1;
        }
        int iComputeRemainingSize = computeRemainingSize(size, position);
        System.arraycopy(this.data, (int) position, buffer, offset, iComputeRemainingSize);
        return iComputeRemainingSize;
    }
}
