package com.bumptech.glide.gifdecoder;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class GifHeaderParser {
    private static final int DESCRIPTOR_MASK_INTERLACE_FLAG = 64;
    private static final int DESCRIPTOR_MASK_LCT_FLAG = 128;
    private static final int DESCRIPTOR_MASK_LCT_SIZE = 7;
    private static final int EXTENSION_INTRODUCER = 33;
    private static final int GCE_DISPOSAL_METHOD_SHIFT = 2;
    private static final int GCE_MASK_DISPOSAL_METHOD = 28;
    private static final int GCE_MASK_TRANSPARENT_COLOR_FLAG = 1;
    private static final int IMAGE_SEPARATOR = 44;
    private static final int LABEL_APPLICATION_EXTENSION = 255;
    private static final int LABEL_COMMENT_EXTENSION = 254;
    private static final int LABEL_GRAPHIC_CONTROL_EXTENSION = 249;
    private static final int LABEL_PLAIN_TEXT_EXTENSION = 1;
    private static final int LSD_MASK_GCT_FLAG = 128;
    private static final int LSD_MASK_GCT_SIZE = 7;
    private static final int MASK_INT_LOWEST_BYTE = 255;
    private static final int MAX_BLOCK_SIZE = 256;
    private static final String TAG = "GifHeaderParser";
    private static final int TRAILER = 59;
    private final byte[] block = new byte[256];
    private int blockSize = 0;
    private GifHeader header;
    private ByteBuffer rawData;

    private boolean err() {
        return this.header.f12283b != 0;
    }

    private int read() {
        try {
            return this.rawData.get() & 255;
        } catch (Exception unused) {
            this.header.f12283b = 1;
            return 0;
        }
    }

    private void readBitmap() {
        this.header.f12285d.f12271a = readShort();
        this.header.f12285d.f12272b = readShort();
        this.header.f12285d.f12273c = readShort();
        this.header.f12285d.f12274d = readShort();
        int i2 = read();
        boolean z2 = (i2 & 128) != 0;
        int iPow = (int) Math.pow(2.0d, (i2 & 7) + 1);
        GifFrame gifFrame = this.header.f12285d;
        gifFrame.f12275e = (i2 & 64) != 0;
        if (z2) {
            gifFrame.f12281k = readColorTable(iPow);
        } else {
            gifFrame.f12281k = null;
        }
        this.header.f12285d.f12280j = this.rawData.position();
        skipImageData();
        if (err()) {
            return;
        }
        GifHeader gifHeader = this.header;
        gifHeader.f12284c++;
        gifHeader.f12286e.add(gifHeader.f12285d);
    }

    private void readBlock() {
        int i2 = read();
        this.blockSize = i2;
        if (i2 <= 0) {
            return;
        }
        int i3 = 0;
        int i4 = 0;
        while (true) {
            try {
                i4 = this.blockSize;
                if (i3 >= i4) {
                    return;
                }
                i4 -= i3;
                this.rawData.get(this.block, i3, i4);
                i3 += i4;
            } catch (Exception e2) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Error Reading Block n: " + i3 + " count: " + i4 + " blockSize: " + this.blockSize, e2);
                }
                this.header.f12283b = 1;
                return;
            }
        }
    }

    @Nullable
    private int[] readColorTable(int i2) {
        byte[] bArr = new byte[i2 * 3];
        int[] iArr = null;
        try {
            this.rawData.get(bArr);
            iArr = new int[256];
            int i3 = 0;
            int i4 = 0;
            while (i3 < i2) {
                int i5 = bArr[i4] & 255;
                int i6 = i4 + 2;
                int i7 = bArr[i4 + 1] & 255;
                i4 += 3;
                int i8 = i3 + 1;
                iArr[i3] = (i7 << 8) | (i5 << 16) | ViewCompat.MEASURED_STATE_MASK | (bArr[i6] & 255);
                i3 = i8;
            }
        } catch (BufferUnderflowException e2) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Format Error Reading Color Table", e2);
            }
            this.header.f12283b = 1;
        }
        return iArr;
    }

    private void readContents() {
        readContents(Integer.MAX_VALUE);
    }

    private void readGraphicControlExt() {
        read();
        int i2 = read();
        GifFrame gifFrame = this.header.f12285d;
        int i3 = (i2 & 28) >> 2;
        gifFrame.f12277g = i3;
        if (i3 == 0) {
            gifFrame.f12277g = 1;
        }
        gifFrame.f12276f = (i2 & 1) != 0;
        int i4 = readShort();
        if (i4 < 2) {
            i4 = 10;
        }
        GifFrame gifFrame2 = this.header.f12285d;
        gifFrame2.f12279i = i4 * 10;
        gifFrame2.f12278h = read();
        read();
    }

    private void readHeader() {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < 6; i2++) {
            sb.append((char) read());
        }
        if (!sb.toString().startsWith("GIF")) {
            this.header.f12283b = 1;
            return;
        }
        readLSD();
        if (!this.header.f12289h || err()) {
            return;
        }
        GifHeader gifHeader = this.header;
        gifHeader.f12282a = readColorTable(gifHeader.f12290i);
        GifHeader gifHeader2 = this.header;
        gifHeader2.f12293l = gifHeader2.f12282a[gifHeader2.f12291j];
    }

    private void readLSD() {
        this.header.f12287f = readShort();
        this.header.f12288g = readShort();
        int i2 = read();
        GifHeader gifHeader = this.header;
        gifHeader.f12289h = (i2 & 128) != 0;
        gifHeader.f12290i = (int) Math.pow(2.0d, (i2 & 7) + 1);
        this.header.f12291j = read();
        this.header.f12292k = read();
    }

    private void readNetscapeExt() {
        do {
            readBlock();
            byte[] bArr = this.block;
            if (bArr[0] == 1) {
                this.header.f12294m = ((bArr[2] & 255) << 8) | (bArr[1] & 255);
            }
            if (this.blockSize <= 0) {
                return;
            }
        } while (!err());
    }

    private int readShort() {
        return this.rawData.getShort();
    }

    private void reset() {
        this.rawData = null;
        Arrays.fill(this.block, (byte) 0);
        this.header = new GifHeader();
        this.blockSize = 0;
    }

    private void skip() {
        int i2;
        do {
            i2 = read();
            this.rawData.position(Math.min(this.rawData.position() + i2, this.rawData.limit()));
        } while (i2 > 0);
    }

    private void skipImageData() {
        read();
        skip();
    }

    public void clear() {
        this.rawData = null;
        this.header = null;
    }

    public boolean isAnimated() {
        readHeader();
        if (!err()) {
            readContents(2);
        }
        return this.header.f12284c > 1;
    }

    @NonNull
    public GifHeader parseHeader() {
        if (this.rawData == null) {
            throw new IllegalStateException("You must call setData() before parseHeader()");
        }
        if (err()) {
            return this.header;
        }
        readHeader();
        if (!err()) {
            readContents();
            GifHeader gifHeader = this.header;
            if (gifHeader.f12284c < 0) {
                gifHeader.f12283b = 1;
            }
        }
        return this.header;
    }

    public GifHeaderParser setData(@NonNull ByteBuffer byteBuffer) {
        reset();
        ByteBuffer byteBufferAsReadOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        this.rawData = byteBufferAsReadOnlyBuffer;
        byteBufferAsReadOnlyBuffer.position(0);
        this.rawData.order(ByteOrder.LITTLE_ENDIAN);
        return this;
    }

    private void readContents(int i2) {
        boolean z2 = false;
        while (!z2 && !err() && this.header.f12284c <= i2) {
            int i3 = read();
            if (i3 == 33) {
                int i4 = read();
                if (i4 == 1) {
                    skip();
                } else if (i4 == LABEL_GRAPHIC_CONTROL_EXTENSION) {
                    this.header.f12285d = new GifFrame();
                    readGraphicControlExt();
                } else if (i4 == 254) {
                    skip();
                } else if (i4 != 255) {
                    skip();
                } else {
                    readBlock();
                    StringBuilder sb = new StringBuilder();
                    for (int i5 = 0; i5 < 11; i5++) {
                        sb.append((char) this.block[i5]);
                    }
                    if (sb.toString().equals("NETSCAPE2.0")) {
                        readNetscapeExt();
                    } else {
                        skip();
                    }
                }
            } else if (i3 == 44) {
                GifHeader gifHeader = this.header;
                if (gifHeader.f12285d == null) {
                    gifHeader.f12285d = new GifFrame();
                }
                readBitmap();
            } else if (i3 != 59) {
                this.header.f12283b = 1;
            } else {
                z2 = true;
            }
        }
    }

    public GifHeaderParser setData(@Nullable byte[] bArr) {
        if (bArr != null) {
            setData(ByteBuffer.wrap(bArr));
        } else {
            this.rawData = null;
            this.header.f12283b = 2;
        }
        return this;
    }
}
