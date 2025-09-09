package com.yalantis.ucrop.util;

import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.RequiresApi;
import androidx.core.view.MotionEventCompat;
import androidx.exifinterface.media.ExifInterface;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

/* loaded from: classes4.dex */
public class ImageHeaderParser {
    private static final int EXIF_MAGIC_NUMBER = 65496;
    private static final int EXIF_SEGMENT_TYPE = 225;
    private static final int INTEL_TIFF_MAGIC_NUMBER = 18761;
    private static final int MARKER_EOI = 217;
    private static final int MOTOROLA_TIFF_MAGIC_NUMBER = 19789;
    private static final int ORIENTATION_TAG_TYPE = 274;
    private static final int SEGMENT_SOS = 218;
    private static final int SEGMENT_START_ID = 255;
    private static final String TAG = "ImageHeaderParser";
    public static final int UNKNOWN_ORIENTATION = -1;
    private final Reader reader;
    private static final String JPEG_EXIF_SEGMENT_PREAMBLE = "Exif\u0000\u0000";
    private static final byte[] JPEG_EXIF_SEGMENT_PREAMBLE_BYTES = JPEG_EXIF_SEGMENT_PREAMBLE.getBytes(Charset.forName("UTF-8"));
    private static final int[] BYTES_PER_FORMAT = {0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8};

    private static class RandomAccessReader {
        private final ByteBuffer data;

        public RandomAccessReader(byte[] bArr, int i2) {
            this.data = (ByteBuffer) ByteBuffer.wrap(bArr).order(ByteOrder.BIG_ENDIAN).limit(i2);
        }

        public short getInt16(int i2) {
            return this.data.getShort(i2);
        }

        public int getInt32(int i2) {
            return this.data.getInt(i2);
        }

        public int length() {
            return this.data.remaining();
        }

        public void order(ByteOrder byteOrder) {
            this.data.order(byteOrder);
        }
    }

    private interface Reader {
        int getUInt16() throws IOException;

        short getUInt8() throws IOException;

        int read(byte[] bArr, int i2) throws IOException;

        long skip(long j2) throws IOException;
    }

    private static class StreamReader implements Reader {
        private final InputStream is;

        public StreamReader(InputStream inputStream) {
            this.is = inputStream;
        }

        @Override // com.yalantis.ucrop.util.ImageHeaderParser.Reader
        public int getUInt16() throws IOException {
            return ((this.is.read() << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (this.is.read() & 255);
        }

        @Override // com.yalantis.ucrop.util.ImageHeaderParser.Reader
        public short getUInt8() throws IOException {
            return (short) (this.is.read() & 255);
        }

        @Override // com.yalantis.ucrop.util.ImageHeaderParser.Reader
        public int read(byte[] bArr, int i2) throws IOException {
            int i3 = i2;
            while (i3 > 0) {
                int i4 = this.is.read(bArr, i2 - i3, i3);
                if (i4 == -1) {
                    break;
                }
                i3 -= i4;
            }
            return i2 - i3;
        }

        @Override // com.yalantis.ucrop.util.ImageHeaderParser.Reader
        public long skip(long j2) throws IOException {
            if (j2 < 0) {
                return 0L;
            }
            long j3 = j2;
            while (j3 > 0) {
                long jSkip = this.is.skip(j3);
                if (jSkip <= 0) {
                    if (this.is.read() == -1) {
                        break;
                    }
                    jSkip = 1;
                }
                j3 -= jSkip;
            }
            return j2 - j3;
        }
    }

    public ImageHeaderParser(InputStream inputStream) {
        this.reader = new StreamReader(inputStream);
    }

    private static int calcTagOffset(int i2, int i3) {
        return i2 + 2 + (i3 * 12);
    }

    public static void copyExif(ExifInterface exifInterface, int i2, int i3, String str) throws Throwable {
        try {
            copyExifAttributes(exifInterface, new ExifInterface(str), i2, i3);
        } catch (IOException e2) {
            Log.d(TAG, e2.getMessage());
        }
    }

    private static void copyExifAttributes(ExifInterface exifInterface, ExifInterface exifInterface2, int i2, int i3) throws Throwable {
        String[] strArr = {ExifInterface.TAG_F_NUMBER, ExifInterface.TAG_DATETIME, ExifInterface.TAG_DATETIME_DIGITIZED, ExifInterface.TAG_EXPOSURE_TIME, ExifInterface.TAG_FLASH, ExifInterface.TAG_FOCAL_LENGTH, ExifInterface.TAG_GPS_ALTITUDE, ExifInterface.TAG_GPS_ALTITUDE_REF, ExifInterface.TAG_GPS_DATESTAMP, ExifInterface.TAG_GPS_LATITUDE, ExifInterface.TAG_GPS_LATITUDE_REF, ExifInterface.TAG_GPS_LONGITUDE, ExifInterface.TAG_GPS_LONGITUDE_REF, ExifInterface.TAG_GPS_PROCESSING_METHOD, ExifInterface.TAG_GPS_TIMESTAMP, ExifInterface.TAG_PHOTOGRAPHIC_SENSITIVITY, ExifInterface.TAG_MAKE, ExifInterface.TAG_MODEL, ExifInterface.TAG_SUBSEC_TIME, ExifInterface.TAG_SUBSEC_TIME_DIGITIZED, ExifInterface.TAG_SUBSEC_TIME_ORIGINAL, ExifInterface.TAG_WHITE_BALANCE};
        for (int i4 = 0; i4 < 22; i4++) {
            String str = strArr[i4];
            String attribute = exifInterface.getAttribute(str);
            if (!TextUtils.isEmpty(attribute)) {
                exifInterface2.setAttribute(str, attribute);
            }
        }
        exifInterface2.setAttribute(ExifInterface.TAG_IMAGE_WIDTH, String.valueOf(i2));
        exifInterface2.setAttribute(ExifInterface.TAG_IMAGE_LENGTH, String.valueOf(i3));
        exifInterface2.setAttribute(ExifInterface.TAG_ORIENTATION, "0");
        exifInterface2.saveAttributes();
    }

    private static boolean handles(int i2) {
        return (i2 & EXIF_MAGIC_NUMBER) == EXIF_MAGIC_NUMBER || i2 == MOTOROLA_TIFF_MAGIC_NUMBER || i2 == INTEL_TIFF_MAGIC_NUMBER;
    }

    private boolean hasJpegExifPreamble(byte[] bArr, int i2) {
        boolean z2 = bArr != null && i2 > JPEG_EXIF_SEGMENT_PREAMBLE_BYTES.length;
        if (z2) {
            int i3 = 0;
            while (true) {
                byte[] bArr2 = JPEG_EXIF_SEGMENT_PREAMBLE_BYTES;
                if (i3 >= bArr2.length) {
                    break;
                }
                if (bArr[i3] != bArr2[i3]) {
                    return false;
                }
                i3++;
            }
        }
        return z2;
    }

    private int moveToExifSegmentAndGetLength() throws IOException {
        short uInt8;
        int uInt16;
        long j2;
        long jSkip;
        do {
            short uInt82 = this.reader.getUInt8();
            if (uInt82 != 255) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Unknown segmentId=" + ((int) uInt82));
                }
                return -1;
            }
            uInt8 = this.reader.getUInt8();
            if (uInt8 == 218) {
                return -1;
            }
            if (uInt8 == 217) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Found MARKER_EOI in exif segment");
                }
                return -1;
            }
            uInt16 = this.reader.getUInt16() - 2;
            if (uInt8 == 225) {
                return uInt16;
            }
            j2 = uInt16;
            jSkip = this.reader.skip(j2);
        } while (jSkip == j2);
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "Unable to skip enough data, type: " + ((int) uInt8) + ", wanted to skip: " + uInt16 + ", but actually skipped: " + jSkip);
        }
        return -1;
    }

    private int parseExifSegment(byte[] bArr, int i2) throws IOException {
        int i3 = this.reader.read(bArr, i2);
        if (i3 == i2) {
            if (hasJpegExifPreamble(bArr, i2)) {
                return parseExifSegment(new RandomAccessReader(bArr, i2));
            }
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Missing jpeg exif preamble");
            }
            return -1;
        }
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "Unable to read exif segment data, length: " + i2 + ", actually read: " + i3);
        }
        return -1;
    }

    public int getOrientation() throws IOException {
        int uInt16 = this.reader.getUInt16();
        if (handles(uInt16)) {
            int iMoveToExifSegmentAndGetLength = moveToExifSegmentAndGetLength();
            if (iMoveToExifSegmentAndGetLength != -1) {
                return parseExifSegment(new byte[iMoveToExifSegmentAndGetLength], iMoveToExifSegmentAndGetLength);
            }
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Failed to parse exif segment length, or exif segment not found");
            }
            return -1;
        }
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "Parser doesn't handle magic number: " + uInt16);
        }
        return -1;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:12:0x0027 -> B:28:0x003e). Please report as a decompilation issue!!! */
    public static void copyExif(Context context, int i2, int i3, Uri uri, String str) throws IOException {
        if (context == null) {
            Log.d(TAG, "context is null");
            return;
        }
        InputStream inputStreamOpenInputStream = null;
        try {
            try {
                try {
                    inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
                    copyExifAttributes(new ExifInterface(inputStreamOpenInputStream), new ExifInterface(str), i2, i3);
                    if (inputStreamOpenInputStream != null) {
                        inputStreamOpenInputStream.close();
                    }
                } catch (Throwable th) {
                    if (inputStreamOpenInputStream != null) {
                        try {
                            inputStreamOpenInputStream.close();
                        } catch (IOException e2) {
                            Log.d(TAG, e2.getMessage(), e2);
                        }
                    }
                    throw th;
                }
            } catch (IOException e3) {
                Log.d(TAG, e3.getMessage(), e3);
                if (inputStreamOpenInputStream == null) {
                } else {
                    inputStreamOpenInputStream.close();
                }
            }
        } catch (IOException e4) {
            Log.d(TAG, e4.getMessage(), e4);
        }
    }

    private static int parseExifSegment(RandomAccessReader randomAccessReader) {
        ByteOrder byteOrder;
        short int16 = randomAccessReader.getInt16(6);
        if (int16 == MOTOROLA_TIFF_MAGIC_NUMBER) {
            byteOrder = ByteOrder.BIG_ENDIAN;
        } else if (int16 == INTEL_TIFF_MAGIC_NUMBER) {
            byteOrder = ByteOrder.LITTLE_ENDIAN;
        } else {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Unknown endianness = " + ((int) int16));
            }
            byteOrder = ByteOrder.BIG_ENDIAN;
        }
        randomAccessReader.order(byteOrder);
        int int32 = randomAccessReader.getInt32(10) + 6;
        short int162 = randomAccessReader.getInt16(int32);
        for (int i2 = 0; i2 < int162; i2++) {
            int iCalcTagOffset = calcTagOffset(int32, i2);
            short int163 = randomAccessReader.getInt16(iCalcTagOffset);
            if (int163 == 274) {
                short int164 = randomAccessReader.getInt16(iCalcTagOffset + 2);
                if (int164 >= 1 && int164 <= 12) {
                    int int322 = randomAccessReader.getInt32(iCalcTagOffset + 4);
                    if (int322 < 0) {
                        if (Log.isLoggable(TAG, 3)) {
                            Log.d(TAG, "Negative tiff component count");
                        }
                    } else {
                        if (Log.isLoggable(TAG, 3)) {
                            Log.d(TAG, "Got tagIndex=" + i2 + " tagType=" + ((int) int163) + " formatCode=" + ((int) int164) + " componentCount=" + int322);
                        }
                        int i3 = int322 + BYTES_PER_FORMAT[int164];
                        if (i3 > 4) {
                            if (Log.isLoggable(TAG, 3)) {
                                Log.d(TAG, "Got byte count > 4, not orientation, continuing, formatCode=" + ((int) int164));
                            }
                        } else {
                            int i4 = iCalcTagOffset + 8;
                            if (i4 >= 0 && i4 <= randomAccessReader.length()) {
                                if (i3 >= 0 && i3 + i4 <= randomAccessReader.length()) {
                                    return randomAccessReader.getInt16(i4);
                                }
                                if (Log.isLoggable(TAG, 3)) {
                                    Log.d(TAG, "Illegal number of bytes for TI tag data tagType=" + ((int) int163));
                                }
                            } else if (Log.isLoggable(TAG, 3)) {
                                Log.d(TAG, "Illegal tagValueOffset=" + i4 + " tagType=" + ((int) int163));
                            }
                        }
                    }
                } else if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Got invalid format code = " + ((int) int164));
                }
            }
        }
        return -1;
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x0074 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0082 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:64:? A[SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x0041 -> B:57:0x0070). Please report as a decompilation issue!!! */
    @androidx.annotation.RequiresApi(21)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void copyExif(android.content.Context r4, int r5, int r6, android.net.Uri r7, android.net.Uri r8) throws java.lang.Throwable {
        /*
            java.lang.String r0 = "ImageHeaderParser"
            if (r4 != 0) goto La
            java.lang.String r4 = "context is null"
            android.util.Log.d(r0, r4)
            return
        La:
            r1 = 0
            android.content.ContentResolver r2 = r4.getContentResolver()     // Catch: java.lang.Throwable -> L51 java.io.IOException -> L54
            java.io.InputStream r7 = r2.openInputStream(r7)     // Catch: java.lang.Throwable -> L51 java.io.IOException -> L54
            androidx.exifinterface.media.ExifInterface r2 = new androidx.exifinterface.media.ExifInterface     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
            r2.<init>(r7)     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
            android.content.ContentResolver r4 = r4.getContentResolver()     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
            java.lang.String r3 = "rw"
            android.os.ParcelFileDescriptor r1 = r4.openFileDescriptor(r8, r3)     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
            androidx.exifinterface.media.ExifInterface r4 = new androidx.exifinterface.media.ExifInterface     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
            java.io.FileDescriptor r8 = r1.getFileDescriptor()     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
            r4.<init>(r8)     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
            copyExifAttributes(r2, r4, r5, r6)     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
            if (r7 == 0) goto L3c
            r7.close()     // Catch: java.io.IOException -> L34
            goto L3c
        L34:
            r4 = move-exception
            java.lang.String r5 = r4.getMessage()
            android.util.Log.d(r0, r5, r4)
        L3c:
            r1.close()     // Catch: java.io.IOException -> L40
            goto L70
        L40:
            r4 = move-exception
            java.lang.String r5 = r4.getMessage()
            android.util.Log.d(r0, r5, r4)
            goto L70
        L49:
            r4 = move-exception
            r5 = r1
            r1 = r7
            goto L72
        L4d:
            r4 = move-exception
            r5 = r1
            r1 = r7
            goto L56
        L51:
            r4 = move-exception
            r5 = r1
            goto L72
        L54:
            r4 = move-exception
            r5 = r1
        L56:
            java.lang.String r6 = r4.getMessage()     // Catch: java.lang.Throwable -> L71
            android.util.Log.d(r0, r6, r4)     // Catch: java.lang.Throwable -> L71
            if (r1 == 0) goto L6b
            r1.close()     // Catch: java.io.IOException -> L63
            goto L6b
        L63:
            r4 = move-exception
            java.lang.String r6 = r4.getMessage()
            android.util.Log.d(r0, r6, r4)
        L6b:
            if (r5 == 0) goto L70
            r5.close()     // Catch: java.io.IOException -> L40
        L70:
            return
        L71:
            r4 = move-exception
        L72:
            if (r1 == 0) goto L80
            r1.close()     // Catch: java.io.IOException -> L78
            goto L80
        L78:
            r6 = move-exception
            java.lang.String r7 = r6.getMessage()
            android.util.Log.d(r0, r7, r6)
        L80:
            if (r5 == 0) goto L8e
            r5.close()     // Catch: java.io.IOException -> L86
            goto L8e
        L86:
            r5 = move-exception
            java.lang.String r6 = r5.getMessage()
            android.util.Log.d(r0, r6, r5)
        L8e:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yalantis.ucrop.util.ImageHeaderParser.copyExif(android.content.Context, int, int, android.net.Uri, android.net.Uri):void");
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:11:0x0026 -> B:25:0x003d). Please report as a decompilation issue!!! */
    @RequiresApi(21)
    public static void copyExif(Context context, ExifInterface exifInterface, int i2, int i3, Uri uri) throws IOException {
        if (context == null) {
            Log.d(TAG, "context is null");
            return;
        }
        ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = null;
        try {
            try {
                try {
                    parcelFileDescriptorOpenFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "rw");
                    copyExifAttributes(exifInterface, new ExifInterface(parcelFileDescriptorOpenFileDescriptor.getFileDescriptor()), i2, i3);
                    parcelFileDescriptorOpenFileDescriptor.close();
                } catch (Throwable th) {
                    if (parcelFileDescriptorOpenFileDescriptor != null) {
                        try {
                            parcelFileDescriptorOpenFileDescriptor.close();
                        } catch (IOException e2) {
                            Log.d(TAG, e2.getMessage(), e2);
                        }
                    }
                    throw th;
                }
            } catch (IOException e3) {
                Log.d(TAG, e3.getMessage());
                if (parcelFileDescriptorOpenFileDescriptor == null) {
                } else {
                    parcelFileDescriptorOpenFileDescriptor.close();
                }
            }
        } catch (IOException e4) {
            Log.d(TAG, e4.getMessage(), e4);
        }
    }
}
