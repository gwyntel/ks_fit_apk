package com.bumptech.glide.gifdecoder;

import android.graphics.Bitmap;
import android.util.Log;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.gifdecoder.GifDecoder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class StandardGifDecoder implements GifDecoder {
    private static final int BYTES_PER_INTEGER = 4;

    @ColorInt
    private static final int COLOR_TRANSPARENT_BLACK = 0;
    private static final int INITIAL_FRAME_POINTER = -1;
    private static final int MASK_INT_LOWEST_BYTE = 255;
    private static final int MAX_STACK_SIZE = 4096;
    private static final int NULL_CODE = -1;
    private static final String TAG = "StandardGifDecoder";

    @ColorInt
    private int[] act;

    @NonNull
    private Bitmap.Config bitmapConfig;
    private final GifDecoder.BitmapProvider bitmapProvider;
    private byte[] block;
    private int downsampledHeight;
    private int downsampledWidth;
    private int framePointer;
    private GifHeader header;

    @Nullable
    private Boolean isFirstFrameTransparent;
    private byte[] mainPixels;

    @ColorInt
    private int[] mainScratch;
    private GifHeaderParser parser;

    @ColorInt
    private final int[] pct;
    private byte[] pixelStack;
    private short[] prefix;
    private Bitmap previousImage;
    private ByteBuffer rawData;
    private int sampleSize;
    private boolean savePrevious;
    private int status;
    private byte[] suffix;

    public StandardGifDecoder(@NonNull GifDecoder.BitmapProvider bitmapProvider, GifHeader gifHeader, ByteBuffer byteBuffer) {
        this(bitmapProvider, gifHeader, byteBuffer, 1);
    }

    @ColorInt
    private int averageColorsNear(int i2, int i3, int i4) {
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        for (int i10 = i2; i10 < this.sampleSize + i2; i10++) {
            byte[] bArr = this.mainPixels;
            if (i10 >= bArr.length || i10 >= i3) {
                break;
            }
            int i11 = this.act[bArr[i10] & 255];
            if (i11 != 0) {
                i5 += (i11 >> 24) & 255;
                i6 += (i11 >> 16) & 255;
                i7 += (i11 >> 8) & 255;
                i8 += i11 & 255;
                i9++;
            }
        }
        int i12 = i2 + i4;
        for (int i13 = i12; i13 < this.sampleSize + i12; i13++) {
            byte[] bArr2 = this.mainPixels;
            if (i13 >= bArr2.length || i13 >= i3) {
                break;
            }
            int i14 = this.act[bArr2[i13] & 255];
            if (i14 != 0) {
                i5 += (i14 >> 24) & 255;
                i6 += (i14 >> 16) & 255;
                i7 += (i14 >> 8) & 255;
                i8 += i14 & 255;
                i9++;
            }
        }
        if (i9 == 0) {
            return 0;
        }
        return ((i5 / i9) << 24) | ((i6 / i9) << 16) | ((i7 / i9) << 8) | (i8 / i9);
    }

    private void copyCopyIntoScratchRobust(GifFrame gifFrame) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int[] iArr = this.mainScratch;
        int i7 = gifFrame.f12274d;
        int i8 = this.sampleSize;
        int i9 = i7 / i8;
        int i10 = gifFrame.f12272b / i8;
        int i11 = gifFrame.f12273c / i8;
        int i12 = gifFrame.f12271a / i8;
        boolean z2 = this.framePointer == 0;
        int i13 = this.downsampledWidth;
        int i14 = this.downsampledHeight;
        byte[] bArr = this.mainPixels;
        int[] iArr2 = this.act;
        Boolean bool = this.isFirstFrameTransparent;
        int i15 = 8;
        int i16 = 0;
        int i17 = 0;
        int i18 = 1;
        while (i17 < i9) {
            Boolean bool2 = bool;
            if (gifFrame.f12275e) {
                if (i16 >= i9) {
                    int i19 = i18 + 1;
                    i2 = i9;
                    if (i19 == 2) {
                        i16 = 4;
                    } else if (i19 == 3) {
                        i15 = 4;
                        i18 = i19;
                        i16 = 2;
                    } else if (i19 == 4) {
                        i18 = i19;
                        i16 = 1;
                        i15 = 2;
                    }
                    i18 = i19;
                } else {
                    i2 = i9;
                }
                i3 = i16 + i15;
            } else {
                i2 = i9;
                i3 = i16;
                i16 = i17;
            }
            int i20 = i16 + i10;
            boolean z3 = i8 == 1;
            if (i20 < i14) {
                int i21 = i20 * i13;
                int i22 = i21 + i12;
                int i23 = i22 + i11;
                int i24 = i21 + i13;
                if (i24 < i23) {
                    i23 = i24;
                }
                i4 = i3;
                int i25 = i17 * i8 * gifFrame.f12273c;
                if (z3) {
                    int i26 = i22;
                    while (i26 < i23) {
                        int i27 = i10;
                        int i28 = iArr2[bArr[i25] & 255];
                        if (i28 != 0) {
                            iArr[i26] = i28;
                        } else if (z2 && bool2 == null) {
                            bool2 = Boolean.TRUE;
                        }
                        i25 += i8;
                        i26++;
                        i10 = i27;
                    }
                } else {
                    i6 = i10;
                    int i29 = ((i23 - i22) * i8) + i25;
                    int i30 = i22;
                    while (true) {
                        i5 = i11;
                        if (i30 >= i23) {
                            break;
                        }
                        int iAverageColorsNear = averageColorsNear(i25, i29, gifFrame.f12273c);
                        if (iAverageColorsNear != 0) {
                            iArr[i30] = iAverageColorsNear;
                        } else if (z2 && bool2 == null) {
                            bool2 = Boolean.TRUE;
                        }
                        i25 += i8;
                        i30++;
                        i11 = i5;
                    }
                    bool = bool2;
                    i17++;
                    i10 = i6;
                    i9 = i2;
                    i11 = i5;
                    i16 = i4;
                }
            } else {
                i4 = i3;
            }
            i6 = i10;
            i5 = i11;
            bool = bool2;
            i17++;
            i10 = i6;
            i9 = i2;
            i11 = i5;
            i16 = i4;
        }
        Boolean bool3 = bool;
        if (this.isFirstFrameTransparent == null) {
            this.isFirstFrameTransparent = Boolean.valueOf(bool3 == null ? false : bool3.booleanValue());
        }
    }

    private void copyIntoScratchFast(GifFrame gifFrame) {
        GifFrame gifFrame2 = gifFrame;
        int[] iArr = this.mainScratch;
        int i2 = gifFrame2.f12274d;
        int i3 = gifFrame2.f12272b;
        int i4 = gifFrame2.f12273c;
        int i5 = gifFrame2.f12271a;
        boolean z2 = this.framePointer == 0;
        int i6 = this.downsampledWidth;
        byte[] bArr = this.mainPixels;
        int[] iArr2 = this.act;
        int i7 = 0;
        byte b2 = -1;
        while (i7 < i2) {
            int i8 = (i7 + i3) * i6;
            int i9 = i8 + i5;
            int i10 = i9 + i4;
            int i11 = i8 + i6;
            if (i11 < i10) {
                i10 = i11;
            }
            int i12 = gifFrame2.f12273c * i7;
            int i13 = i9;
            while (i13 < i10) {
                byte b3 = bArr[i12];
                int i14 = i2;
                int i15 = b3 & 255;
                if (i15 != b2) {
                    int i16 = iArr2[i15];
                    if (i16 != 0) {
                        iArr[i13] = i16;
                    } else {
                        b2 = b3;
                    }
                }
                i12++;
                i13++;
                i2 = i14;
            }
            i7++;
            gifFrame2 = gifFrame;
        }
        Boolean bool = this.isFirstFrameTransparent;
        this.isFirstFrameTransparent = Boolean.valueOf((bool != null && bool.booleanValue()) || (this.isFirstFrameTransparent == null && z2 && b2 != -1));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v15, types: [short] */
    /* JADX WARN: Type inference failed for: r7v17 */
    private void decodeBitmapData(GifFrame gifFrame) {
        int i2;
        int i3;
        short s2;
        StandardGifDecoder standardGifDecoder = this;
        if (gifFrame != null) {
            standardGifDecoder.rawData.position(gifFrame.f12280j);
        }
        if (gifFrame == null) {
            GifHeader gifHeader = standardGifDecoder.header;
            i2 = gifHeader.f12287f;
            i3 = gifHeader.f12288g;
        } else {
            i2 = gifFrame.f12273c;
            i3 = gifFrame.f12274d;
        }
        int i4 = i2 * i3;
        byte[] bArr = standardGifDecoder.mainPixels;
        if (bArr == null || bArr.length < i4) {
            standardGifDecoder.mainPixels = standardGifDecoder.bitmapProvider.obtainByteArray(i4);
        }
        byte[] bArr2 = standardGifDecoder.mainPixels;
        if (standardGifDecoder.prefix == null) {
            standardGifDecoder.prefix = new short[4096];
        }
        short[] sArr = standardGifDecoder.prefix;
        if (standardGifDecoder.suffix == null) {
            standardGifDecoder.suffix = new byte[4096];
        }
        byte[] bArr3 = standardGifDecoder.suffix;
        if (standardGifDecoder.pixelStack == null) {
            standardGifDecoder.pixelStack = new byte[4097];
        }
        byte[] bArr4 = standardGifDecoder.pixelStack;
        int i5 = readByte();
        int i6 = 1 << i5;
        int i7 = i6 + 1;
        int i8 = i6 + 2;
        int i9 = i5 + 1;
        int i10 = (1 << i9) - 1;
        int i11 = 0;
        for (int i12 = 0; i12 < i6; i12++) {
            sArr[i12] = 0;
            bArr3[i12] = (byte) i12;
        }
        byte[] bArr5 = standardGifDecoder.block;
        int i13 = i9;
        int i14 = i8;
        int i15 = i10;
        int block = 0;
        int i16 = 0;
        int i17 = 0;
        int i18 = 0;
        int i19 = 0;
        int i20 = 0;
        int i21 = 0;
        int i22 = -1;
        while (true) {
            if (i11 >= i4) {
                break;
            }
            if (block == 0) {
                block = readBlock();
                if (block <= 0) {
                    standardGifDecoder.status = 3;
                    break;
                }
                i16 = 0;
            }
            i18 += (bArr5[i16] & 255) << i17;
            i16++;
            block--;
            int i23 = i17 + 8;
            int i24 = i14;
            int i25 = i13;
            int i26 = i22;
            int i27 = i9;
            int i28 = i20;
            while (true) {
                if (i23 < i25) {
                    i22 = i26;
                    i14 = i24;
                    i17 = i23;
                    standardGifDecoder = this;
                    i20 = i28;
                    i9 = i27;
                    i13 = i25;
                    break;
                }
                int i29 = i8;
                int i30 = i18 & i15;
                i18 >>= i25;
                i23 -= i25;
                if (i30 == i6) {
                    i15 = i10;
                    i25 = i27;
                    i24 = i29;
                    i8 = i24;
                    i26 = -1;
                } else {
                    if (i30 == i7) {
                        i17 = i23;
                        i20 = i28;
                        i14 = i24;
                        i9 = i27;
                        i8 = i29;
                        i22 = i26;
                        i13 = i25;
                        standardGifDecoder = this;
                        break;
                    }
                    if (i26 == -1) {
                        bArr2[i19] = bArr3[i30];
                        i19++;
                        i11++;
                        i26 = i30;
                        i28 = i26;
                        i8 = i29;
                        i23 = i23;
                    } else {
                        if (i30 >= i24) {
                            bArr4[i21] = (byte) i28;
                            i21++;
                            s2 = i26;
                        } else {
                            s2 = i30;
                        }
                        while (s2 >= i6) {
                            bArr4[i21] = bArr3[s2];
                            i21++;
                            s2 = sArr[s2];
                        }
                        i28 = bArr3[s2] & 255;
                        byte b2 = (byte) i28;
                        bArr2[i19] = b2;
                        while (true) {
                            i19++;
                            i11++;
                            if (i21 <= 0) {
                                break;
                            }
                            i21--;
                            bArr2[i19] = bArr4[i21];
                        }
                        byte[] bArr6 = bArr4;
                        if (i24 < 4096) {
                            sArr[i24] = (short) i26;
                            bArr3[i24] = b2;
                            i24++;
                            if ((i24 & i15) == 0 && i24 < 4096) {
                                i25++;
                                i15 += i24;
                            }
                        }
                        i26 = i30;
                        i8 = i29;
                        i23 = i23;
                        bArr4 = bArr6;
                    }
                }
            }
        }
        Arrays.fill(bArr2, i19, i4, (byte) 0);
    }

    @NonNull
    private GifHeaderParser getHeaderParser() {
        if (this.parser == null) {
            this.parser = new GifHeaderParser();
        }
        return this.parser;
    }

    private Bitmap getNextBitmap() {
        Boolean bool = this.isFirstFrameTransparent;
        Bitmap bitmapObtain = this.bitmapProvider.obtain(this.downsampledWidth, this.downsampledHeight, (bool == null || bool.booleanValue()) ? Bitmap.Config.ARGB_8888 : this.bitmapConfig);
        bitmapObtain.setHasAlpha(true);
        return bitmapObtain;
    }

    private int readBlock() {
        int i2 = readByte();
        if (i2 <= 0) {
            return i2;
        }
        ByteBuffer byteBuffer = this.rawData;
        byteBuffer.get(this.block, 0, Math.min(i2, byteBuffer.remaining()));
        return i2;
    }

    private int readByte() {
        return this.rawData.get() & 255;
    }

    private Bitmap setPixels(GifFrame gifFrame, GifFrame gifFrame2) {
        int i2;
        int i3;
        Bitmap bitmap;
        int[] iArr = this.mainScratch;
        int i4 = 0;
        if (gifFrame2 == null) {
            Bitmap bitmap2 = this.previousImage;
            if (bitmap2 != null) {
                this.bitmapProvider.release(bitmap2);
            }
            this.previousImage = null;
            Arrays.fill(iArr, 0);
        }
        if (gifFrame2 != null && gifFrame2.f12277g == 3 && this.previousImage == null) {
            Arrays.fill(iArr, 0);
        }
        if (gifFrame2 != null && (i3 = gifFrame2.f12277g) > 0) {
            if (i3 == 2) {
                if (!gifFrame.f12276f) {
                    GifHeader gifHeader = this.header;
                    int i5 = gifHeader.f12293l;
                    if (gifFrame.f12281k == null || gifHeader.f12291j != gifFrame.f12278h) {
                        i4 = i5;
                    }
                }
                int i6 = gifFrame2.f12274d;
                int i7 = this.sampleSize;
                int i8 = i6 / i7;
                int i9 = gifFrame2.f12272b / i7;
                int i10 = gifFrame2.f12273c / i7;
                int i11 = gifFrame2.f12271a / i7;
                int i12 = this.downsampledWidth;
                int i13 = (i9 * i12) + i11;
                int i14 = (i8 * i12) + i13;
                while (i13 < i14) {
                    int i15 = i13 + i10;
                    for (int i16 = i13; i16 < i15; i16++) {
                        iArr[i16] = i4;
                    }
                    i13 += this.downsampledWidth;
                }
            } else if (i3 == 3 && (bitmap = this.previousImage) != null) {
                int i17 = this.downsampledWidth;
                bitmap.getPixels(iArr, 0, i17, 0, 0, i17, this.downsampledHeight);
            }
        }
        decodeBitmapData(gifFrame);
        if (gifFrame.f12275e || this.sampleSize != 1) {
            copyCopyIntoScratchRobust(gifFrame);
        } else {
            copyIntoScratchFast(gifFrame);
        }
        if (this.savePrevious && ((i2 = gifFrame.f12277g) == 0 || i2 == 1)) {
            if (this.previousImage == null) {
                this.previousImage = getNextBitmap();
            }
            Bitmap bitmap3 = this.previousImage;
            int i18 = this.downsampledWidth;
            bitmap3.setPixels(iArr, 0, i18, 0, 0, i18, this.downsampledHeight);
        }
        Bitmap nextBitmap = getNextBitmap();
        int i19 = this.downsampledWidth;
        nextBitmap.setPixels(iArr, 0, i19, 0, 0, i19, this.downsampledHeight);
        return nextBitmap;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public void advance() {
        this.framePointer = (this.framePointer + 1) % this.header.f12284c;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public void clear() {
        this.header = null;
        byte[] bArr = this.mainPixels;
        if (bArr != null) {
            this.bitmapProvider.release(bArr);
        }
        int[] iArr = this.mainScratch;
        if (iArr != null) {
            this.bitmapProvider.release(iArr);
        }
        Bitmap bitmap = this.previousImage;
        if (bitmap != null) {
            this.bitmapProvider.release(bitmap);
        }
        this.previousImage = null;
        this.rawData = null;
        this.isFirstFrameTransparent = null;
        byte[] bArr2 = this.block;
        if (bArr2 != null) {
            this.bitmapProvider.release(bArr2);
        }
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getByteSize() {
        return this.rawData.limit() + this.mainPixels.length + (this.mainScratch.length * 4);
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getCurrentFrameIndex() {
        return this.framePointer;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    @NonNull
    public ByteBuffer getData() {
        return this.rawData;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getDelay(int i2) {
        if (i2 >= 0) {
            GifHeader gifHeader = this.header;
            if (i2 < gifHeader.f12284c) {
                return ((GifFrame) gifHeader.f12286e.get(i2)).f12279i;
            }
        }
        return -1;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getFrameCount() {
        return this.header.f12284c;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getHeight() {
        return this.header.f12288g;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    @Deprecated
    public int getLoopCount() {
        int i2 = this.header.f12294m;
        if (i2 == -1) {
            return 1;
        }
        return i2;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getNetscapeLoopCount() {
        return this.header.f12294m;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getNextDelay() {
        int i2;
        if (this.header.f12284c <= 0 || (i2 = this.framePointer) < 0) {
            return 0;
        }
        return getDelay(i2);
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    @Nullable
    public synchronized Bitmap getNextFrame() {
        try {
            if (this.header.f12284c <= 0 || this.framePointer < 0) {
                String str = TAG;
                if (Log.isLoggable(str, 3)) {
                    Log.d(str, "Unable to decode frame, frameCount=" + this.header.f12284c + ", framePointer=" + this.framePointer);
                }
                this.status = 1;
            }
            int i2 = this.status;
            if (i2 != 1 && i2 != 2) {
                this.status = 0;
                if (this.block == null) {
                    this.block = this.bitmapProvider.obtainByteArray(255);
                }
                GifFrame gifFrame = (GifFrame) this.header.f12286e.get(this.framePointer);
                int i3 = this.framePointer - 1;
                GifFrame gifFrame2 = i3 >= 0 ? (GifFrame) this.header.f12286e.get(i3) : null;
                int[] iArr = gifFrame.f12281k;
                if (iArr == null) {
                    iArr = this.header.f12282a;
                }
                this.act = iArr;
                if (iArr == null) {
                    String str2 = TAG;
                    if (Log.isLoggable(str2, 3)) {
                        Log.d(str2, "No valid color table found for frame #" + this.framePointer);
                    }
                    this.status = 1;
                    return null;
                }
                if (gifFrame.f12276f) {
                    System.arraycopy(iArr, 0, this.pct, 0, iArr.length);
                    int[] iArr2 = this.pct;
                    this.act = iArr2;
                    iArr2[gifFrame.f12278h] = 0;
                    if (gifFrame.f12277g == 2 && this.framePointer == 0) {
                        this.isFirstFrameTransparent = Boolean.TRUE;
                    }
                }
                return setPixels(gifFrame, gifFrame2);
            }
            String str3 = TAG;
            if (Log.isLoggable(str3, 3)) {
                Log.d(str3, "Unable to decode frame, status=" + this.status);
            }
            return null;
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getStatus() {
        return this.status;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getTotalIterationCount() {
        int i2 = this.header.f12294m;
        if (i2 == -1) {
            return 1;
        }
        if (i2 == 0) {
            return 0;
        }
        return i2 + 1;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getWidth() {
        return this.header.f12287f;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int read(@Nullable InputStream inputStream, int i2) throws IOException {
        if (inputStream != null) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(i2 > 0 ? i2 + 4096 : 16384);
                byte[] bArr = new byte[16384];
                while (true) {
                    int i3 = inputStream.read(bArr, 0, 16384);
                    if (i3 == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, i3);
                }
                byteArrayOutputStream.flush();
                read(byteArrayOutputStream.toByteArray());
            } catch (IOException e2) {
                Log.w(TAG, "Error reading data from stream", e2);
            }
        } else {
            this.status = 2;
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e3) {
                Log.w(TAG, "Error closing stream", e3);
            }
        }
        return this.status;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public void resetFrameIndex() {
        this.framePointer = -1;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public synchronized void setData(@NonNull GifHeader gifHeader, @NonNull byte[] bArr) {
        setData(gifHeader, ByteBuffer.wrap(bArr));
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public void setDefaultBitmapConfig(@NonNull Bitmap.Config config) {
        Bitmap.Config config2;
        Bitmap.Config config3 = Bitmap.Config.ARGB_8888;
        if (config == config3 || config == (config2 = Bitmap.Config.RGB_565)) {
            this.bitmapConfig = config;
            return;
        }
        throw new IllegalArgumentException("Unsupported format: " + config + ", must be one of " + config3 + " or " + config2);
    }

    public StandardGifDecoder(@NonNull GifDecoder.BitmapProvider bitmapProvider, GifHeader gifHeader, ByteBuffer byteBuffer, int i2) {
        this(bitmapProvider);
        setData(gifHeader, byteBuffer, i2);
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public synchronized void setData(@NonNull GifHeader gifHeader, @NonNull ByteBuffer byteBuffer) {
        setData(gifHeader, byteBuffer, 1);
    }

    public StandardGifDecoder(@NonNull GifDecoder.BitmapProvider bitmapProvider) {
        this.pct = new int[256];
        this.bitmapConfig = Bitmap.Config.ARGB_8888;
        this.bitmapProvider = bitmapProvider;
        this.header = new GifHeader();
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public synchronized void setData(@NonNull GifHeader gifHeader, @NonNull ByteBuffer byteBuffer, int i2) {
        try {
            if (i2 > 0) {
                int iHighestOneBit = Integer.highestOneBit(i2);
                this.status = 0;
                this.header = gifHeader;
                this.framePointer = -1;
                ByteBuffer byteBufferAsReadOnlyBuffer = byteBuffer.asReadOnlyBuffer();
                this.rawData = byteBufferAsReadOnlyBuffer;
                byteBufferAsReadOnlyBuffer.position(0);
                this.rawData.order(ByteOrder.LITTLE_ENDIAN);
                this.savePrevious = false;
                Iterator it = gifHeader.f12286e.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    } else if (((GifFrame) it.next()).f12277g == 3) {
                        this.savePrevious = true;
                        break;
                    }
                }
                this.sampleSize = iHighestOneBit;
                int i3 = gifHeader.f12287f;
                this.downsampledWidth = i3 / iHighestOneBit;
                int i4 = gifHeader.f12288g;
                this.downsampledHeight = i4 / iHighestOneBit;
                this.mainPixels = this.bitmapProvider.obtainByteArray(i3 * i4);
                this.mainScratch = this.bitmapProvider.obtainIntArray(this.downsampledWidth * this.downsampledHeight);
            } else {
                throw new IllegalArgumentException("Sample size must be >=0, not: " + i2);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public synchronized int read(@Nullable byte[] bArr) {
        try {
            GifHeader header = getHeaderParser().setData(bArr).parseHeader();
            this.header = header;
            if (bArr != null) {
                setData(header, bArr);
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.status;
    }
}
