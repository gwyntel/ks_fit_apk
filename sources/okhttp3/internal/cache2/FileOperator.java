package okhttp3.internal.cache2;

import java.io.IOException;
import java.nio.channels.FileChannel;
import okio.Buffer;

/* loaded from: classes5.dex */
final class FileOperator {
    private final FileChannel fileChannel;

    FileOperator(FileChannel fileChannel) {
        this.fileChannel = fileChannel;
    }

    public void read(long j2, Buffer buffer, long j3) throws IOException {
        if (j3 < 0) {
            throw new IndexOutOfBoundsException();
        }
        while (j3 > 0) {
            long jTransferTo = this.fileChannel.transferTo(j2, j3, buffer);
            j2 += jTransferTo;
            j3 -= jTransferTo;
        }
    }

    public void write(long j2, Buffer buffer, long j3) throws IOException {
        if (j3 < 0 || j3 > buffer.size()) {
            throw new IndexOutOfBoundsException();
        }
        while (j3 > 0) {
            long jTransferFrom = this.fileChannel.transferFrom(buffer, j2, j3);
            j2 += jTransferFrom;
            j3 -= jTransferFrom;
        }
    }
}
