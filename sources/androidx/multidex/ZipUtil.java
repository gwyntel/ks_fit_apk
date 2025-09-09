package androidx.multidex;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.CRC32;
import java.util.zip.ZipException;

/* loaded from: classes2.dex */
final class ZipUtil {
    private static final int BUFFER_SIZE = 16384;
    private static final int ENDHDR = 22;
    private static final int ENDSIG = 101010256;

    static class CentralDirectory {

        /* renamed from: a, reason: collision with root package name */
        long f5639a;

        /* renamed from: b, reason: collision with root package name */
        long f5640b;

        CentralDirectory() {
        }
    }

    static long a(RandomAccessFile randomAccessFile, CentralDirectory centralDirectory) throws IOException {
        CRC32 crc32 = new CRC32();
        long j2 = centralDirectory.f5640b;
        randomAccessFile.seek(centralDirectory.f5639a);
        byte[] bArr = new byte[16384];
        int i2 = randomAccessFile.read(bArr, 0, (int) Math.min(PlaybackStateCompat.ACTION_PREPARE, j2));
        while (i2 != -1) {
            crc32.update(bArr, 0, i2);
            j2 -= i2;
            if (j2 == 0) {
                break;
            }
            i2 = randomAccessFile.read(bArr, 0, (int) Math.min(PlaybackStateCompat.ACTION_PREPARE, j2));
        }
        return crc32.getValue();
    }

    static CentralDirectory b(RandomAccessFile randomAccessFile) throws IOException {
        long length = randomAccessFile.length();
        long j2 = length - 22;
        if (j2 < 0) {
            throw new ZipException("File too short to be a zip file: " + randomAccessFile.length());
        }
        long j3 = length - 65558;
        long j4 = j3 >= 0 ? j3 : 0L;
        int iReverseBytes = Integer.reverseBytes(ENDSIG);
        do {
            randomAccessFile.seek(j2);
            if (randomAccessFile.readInt() == iReverseBytes) {
                randomAccessFile.skipBytes(2);
                randomAccessFile.skipBytes(2);
                randomAccessFile.skipBytes(2);
                randomAccessFile.skipBytes(2);
                CentralDirectory centralDirectory = new CentralDirectory();
                centralDirectory.f5640b = Integer.reverseBytes(randomAccessFile.readInt()) & 4294967295L;
                centralDirectory.f5639a = Integer.reverseBytes(randomAccessFile.readInt()) & 4294967295L;
                return centralDirectory;
            }
            j2--;
        } while (j2 >= j4);
        throw new ZipException("End Of Central Directory signature not found");
    }

    static long c(File file) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        try {
            return a(randomAccessFile, b(randomAccessFile));
        } finally {
            randomAccessFile.close();
        }
    }
}
