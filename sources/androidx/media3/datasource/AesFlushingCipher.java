package androidx.media3.datasource;

import androidx.annotation.Nullable;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@UnstableApi
/* loaded from: classes.dex */
public final class AesFlushingCipher {
    private final int blockSize;
    private final Cipher cipher;
    private final byte[] flushedBlock;
    private int pendingXorBytes;
    private final byte[] zerosBlock;

    public AesFlushingCipher(int i2, byte[] bArr, @Nullable String str, long j2) {
        this(i2, bArr, getFNV64Hash(str), j2);
    }

    private static long getFNV64Hash(@Nullable String str) {
        long j2 = 0;
        if (str == null) {
            return 0L;
        }
        for (int i2 = 0; i2 < str.length(); i2++) {
            long jCharAt = j2 ^ str.charAt(i2);
            j2 = jCharAt + (jCharAt << 1) + (jCharAt << 4) + (jCharAt << 5) + (jCharAt << 7) + (jCharAt << 8) + (jCharAt << 40);
        }
        return j2;
    }

    private byte[] getInitializationVector(long j2, long j3) {
        return ByteBuffer.allocate(16).putLong(j2).putLong(j3).array();
    }

    private int nonFlushingUpdate(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        try {
            return this.cipher.update(bArr, i2, i3, bArr2, i4);
        } catch (ShortBufferException e2) {
            throw new RuntimeException(e2);
        }
    }

    public void update(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        int i5 = i2;
        do {
            int i6 = this.pendingXorBytes;
            if (i6 <= 0) {
                int iNonFlushingUpdate = nonFlushingUpdate(bArr, i5, i3, bArr2, i4);
                if (i3 == iNonFlushingUpdate) {
                    return;
                }
                int i7 = i3 - iNonFlushingUpdate;
                int i8 = 0;
                Assertions.checkState(i7 < this.blockSize);
                int i9 = i4 + iNonFlushingUpdate;
                int i10 = this.blockSize - i7;
                this.pendingXorBytes = i10;
                Assertions.checkState(nonFlushingUpdate(this.zerosBlock, 0, i10, this.flushedBlock, 0) == this.blockSize);
                while (i8 < i7) {
                    bArr2[i9] = this.flushedBlock[i8];
                    i8++;
                    i9++;
                }
                return;
            }
            bArr2[i4] = (byte) (bArr[i5] ^ this.flushedBlock[this.blockSize - i6]);
            i4++;
            i5++;
            this.pendingXorBytes = i6 - 1;
            i3--;
        } while (i3 != 0);
    }

    public void updateInPlace(byte[] bArr, int i2, int i3) {
        update(bArr, i2, i3, bArr, i2);
    }

    public AesFlushingCipher(int i2, byte[] bArr, long j2, long j3) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
            this.cipher = cipher;
            int blockSize = cipher.getBlockSize();
            this.blockSize = blockSize;
            this.zerosBlock = new byte[blockSize];
            this.flushedBlock = new byte[blockSize];
            long j4 = j3 / blockSize;
            int i3 = (int) (j3 % blockSize);
            cipher.init(i2, new SecretKeySpec(bArr, Util.splitAtFirst(cipher.getAlgorithm(), "/")[0]), new IvParameterSpec(getInitializationVector(j2, j4)));
            if (i3 != 0) {
                updateInPlace(new byte[i3], 0, i3);
            }
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e2) {
            throw new RuntimeException(e2);
        }
    }
}
