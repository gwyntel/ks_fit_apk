package com.huawei.hms.mlsdk.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Pair;
import com.huawei.hms.ml.common.utils.ImageConvertUtils;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public class MLFrame {
    public static final int SCREEN_FIRST_QUADRANT = 0;
    public static final int SCREEN_FOURTH_QUADRANT = 3;
    public static final int SCREEN_SECOND_QUADRANT = 1;
    public static final int SCREEN_THIRD_QUADRANT = 2;
    private Bitmap bitmap;
    private ByteBuffer byteBuffer;
    private byte[] bytes;
    private volatile Boolean frameInit;
    private Property property;
    private int recMode;

    public static class Creator {
        private MLFrame frame = new MLFrame();

        public MLFrame create() {
            if (this.frame.byteBuffer == null && this.frame.bitmap == null) {
                throw new IllegalStateException("Failed to create image instance, both bitmap and byteBuffer data are not specified.");
            }
            return this.frame;
        }

        public Creator setBitmap(Bitmap bitmap) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            this.frame.bitmap = bitmap;
            Property propertyAcquireProperty = this.frame.acquireProperty();
            propertyAcquireProperty.width = width;
            propertyAcquireProperty.height = height;
            return this;
        }

        public Creator setFramePropertyExt(Property.Ext ext) {
            this.frame.acquireProperty().ext = ext;
            return this;
        }

        public Creator setItemIdentity(int i2) {
            this.frame.acquireProperty().itemIdentity = i2;
            return this;
        }

        public Creator setQuadrant(int i2) {
            this.frame.acquireProperty().quadrant = i2;
            return this;
        }

        public Creator setTimestamp(long j2) {
            this.frame.acquireProperty().timestamp = j2;
            return this;
        }

        public Creator writeByteBufferData(ByteBuffer byteBuffer, int i2, int i3, int i4) {
            if (byteBuffer == null) {
                throw new IllegalArgumentException("Parameter： data is not specified");
            }
            if (byteBuffer.capacity() < i2 * i3) {
                throw new IllegalArgumentException("Not enough capacity for image data size.");
            }
            if (i4 != 17 && i4 != 16 && i4 != 842094169) {
                throw new IllegalArgumentException("Parameter formatType:" + i4 + " is not supported");
            }
            this.frame.byteBuffer = byteBuffer;
            Property propertyAcquireProperty = this.frame.acquireProperty();
            if (propertyAcquireProperty != null) {
                propertyAcquireProperty.formatType = i4;
                propertyAcquireProperty.width = i2;
                propertyAcquireProperty.height = i3;
            }
            return this;
        }
    }

    public static class Property {
        public static final int IMAGE_FORMAT_NV21 = 17;
        public static final int IMAGE_FORMAT_YV12 = 842094169;
        public static final int SCREEN_FIRST_QUADRANT = 0;
        public static final int SCREEN_FOURTH_QUADRANT = 3;
        public static final int SCREEN_SECOND_QUADRANT = 1;
        public static final int SCREEN_THIRD_QUADRANT = 2;
        private Ext ext;
        private int formatType;
        private int height;
        private int itemIdentity;
        private int quadrant;
        private long timestamp;
        private int width;

        public static class Creator {
            private Ext ext;
            private int formatType;
            private int height;
            private int itemIdentity;
            private int quadrant;
            private long timestamp;
            private int width;

            public Property create() {
                return new Property(this.width, this.height, this.quadrant, this.formatType, this.itemIdentity, this.timestamp, this.ext);
            }

            public Creator setExt(Ext ext) {
                this.ext = ext;
                return this;
            }

            public Creator setFormatType(int i2) {
                this.formatType = i2;
                return this;
            }

            public Creator setHeight(int i2) {
                this.height = i2;
                return this;
            }

            public Creator setItemIdentity(int i2) {
                this.itemIdentity = i2;
                return this;
            }

            public Creator setQuadrant(int i2) {
                this.quadrant = i2;
                return this;
            }

            public Creator setTimestamp(int i2) {
                this.timestamp = i2;
                return this;
            }

            public Creator setWidth(int i2) {
                this.width = i2;
                return this;
            }
        }

        public static class Ext {
            private int lensId;
            private int maxZoom;
            private Rect rect;
            private int zoom;

            public static class Creator {
                private int maxZoom;
                private Rect rect;
                private int lensId = 0;
                private int zoom = 0;

                public Ext build() {
                    return new Ext(this.lensId, this.zoom, this.maxZoom, this.rect);
                }

                public Creator setLensId(int i2) {
                    this.lensId = i2;
                    return this;
                }

                public Creator setMaxZoom(int i2) {
                    this.maxZoom = i2;
                    return this;
                }

                public Creator setRect(Rect rect) {
                    this.rect = rect;
                    return this;
                }

                public Creator setZoom(int i2) {
                    this.zoom = i2;
                    return this;
                }
            }

            public int getLensId() {
                return this.lensId;
            }

            public int getMaxZoom() {
                return this.maxZoom;
            }

            public Rect getRect() {
                return this.rect;
            }

            public int getZoom() {
                return this.zoom;
            }

            private Ext(int i2, int i3, int i4, Rect rect) {
                this.lensId = i2;
                this.zoom = i3;
                this.maxZoom = i4;
                this.rect = rect;
            }
        }

        public Ext getExt() {
            return this.ext;
        }

        public int getFormatType() {
            return this.formatType;
        }

        public int getHeight() {
            return this.height;
        }

        public int getItemIdentity() {
            return this.itemIdentity;
        }

        public int getQuadrant() {
            return this.quadrant;
        }

        public long getTimestamp() {
            return this.timestamp;
        }

        public int getWidth() {
            return this.width;
        }

        public void resetWidthAndHeight() {
            if (this.quadrant % 2 != 0) {
                int i2 = this.width;
                this.width = this.height;
                this.height = i2;
            }
            this.quadrant = 0;
        }

        public Property() {
            this.quadrant = 0;
            this.formatType = -1;
            this.itemIdentity = -1;
            this.ext = new Ext.Creator().build();
        }

        public Property(Property property) {
            this.quadrant = 0;
            this.formatType = -1;
            this.itemIdentity = -1;
            this.width = property.getWidth();
            this.height = property.getHeight();
            this.formatType = property.getFormatType();
            this.quadrant = property.getQuadrant();
            this.itemIdentity = property.getItemIdentity();
            this.timestamp = property.getTimestamp();
            this.ext = property.getExt();
        }

        private Property(int i2, int i3, int i4, int i5, int i6, long j2, Ext ext) {
            this.width = i2;
            this.height = i3;
            this.quadrant = i4;
            this.formatType = i5;
            this.itemIdentity = i6;
            this.timestamp = j2;
            this.ext = ext;
        }
    }

    public MLFrame() {
        this.frameInit = Boolean.FALSE;
        this.property = new Property();
        this.byteBuffer = null;
        this.bitmap = null;
    }

    public static MLFrame fromBitmap(Bitmap bitmap) {
        return new MLFrame(bitmap);
    }

    public static MLFrame fromByteArray(byte[] bArr, Property property) {
        return new MLFrame(bArr, property);
    }

    public static MLFrame fromByteBuffer(ByteBuffer byteBuffer, Property property) {
        return new MLFrame(byteBuffer, property);
    }

    public static MLFrame fromFilePath(Context context, Uri uri) throws IOException {
        if (context == null) {
            throw new IllegalArgumentException("Parameter context is mandatory");
        }
        if (uri == null) {
            throw new IllegalArgumentException("Parameter uri is mandatory");
        }
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        if (bitmap != null) {
            return new MLFrame(bitmap);
        }
        throw new NullPointerException("Failed to load bitmap from uri");
    }

    @TargetApi(19)
    public static MLFrame fromMediaImage(Image image, int i2) {
        MLFrame mLFrame;
        Image.Plane plane;
        int format = image.getFormat();
        if (format != 256 && format != 35) {
            throw new IllegalArgumentException("Unsupported format: " + image.getFormat() + ", Only JPEG and YUV_420_888 are supported");
        }
        if (format == 256) {
            Image.Plane[] planes = image.getPlanes();
            if (planes == null || planes.length != 1 || (plane = planes[0]) == null || plane.getBuffer() == null) {
                mLFrame = null;
            } else {
                ByteBuffer buffer = planes[0].getBuffer();
                int iRemaining = buffer.remaining();
                byte[] bArr = new byte[iRemaining];
                buffer.get(bArr);
                mLFrame = i2 != 0 ? new MLFrame(rotate(BitmapFactory.decodeByteArray(bArr, 0, iRemaining), i2)) : new MLFrame(bArr);
            }
        } else {
            Property.Creator creator = new Property.Creator();
            creator.setFormatType(17).setWidth(image.getWidth()).setHeight(image.getHeight()).setQuadrant(i2);
            mLFrame = new MLFrame(ImageConvertUtils.getDataFromImage(image, 2), creator.create());
        }
        if (mLFrame != null) {
            return mLFrame;
        }
        throw new IllegalStateException("Failed to create frame from media image.");
    }

    private Pair<Integer, Integer> getPreviewSize() {
        Property property = this.property;
        if (property == null) {
            return null;
        }
        if (property.getItemIdentity() == -1) {
            return Pair.create(Integer.valueOf(wrapBitmap().getWidth()), Integer.valueOf(wrapBitmap().getHeight()));
        }
        boolean z2 = true;
        if (this.property.getQuadrant() != 1 && this.property.getQuadrant() != 3) {
            z2 = false;
        }
        Property property2 = this.property;
        return Pair.create(Integer.valueOf(z2 ? property2.getHeight() : property2.getWidth()), Integer.valueOf(z2 ? this.property.getWidth() : this.property.getHeight()));
    }

    private boolean isSupportedYuvFormat(int i2) {
        return i2 == 842094169 || i2 == 17;
    }

    public static Bitmap rotate(Bitmap bitmap, int i2) {
        if (i2 < 0 || i2 > 3) {
            StringBuilder sb = new StringBuilder(29);
            sb.append("Invalid quadrant: ");
            sb.append(i2);
            throw new IllegalArgumentException(sb.toString());
        }
        if (i2 == 0) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(i2 * 90);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private final Bitmap wrapBitmap() {
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            return bitmap;
        }
        if (this.property != null) {
            try {
                byte[] bArrWrapJpeg = wrapJpeg(false);
                Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(bArrWrapJpeg, 0, bArrWrapJpeg.length);
                if (this.property.getQuadrant() != 0) {
                    bitmapDecodeByteArray = rotate(bitmapDecodeByteArray, this.property.getQuadrant());
                }
                this.bitmap = bitmapDecodeByteArray;
            } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException | Exception unused) {
                return null;
            }
        }
        return this.bitmap;
    }

    public ByteBuffer acquireGrayByteBuffer() {
        ByteBuffer byteBuffer = this.byteBuffer;
        if (byteBuffer != null && this.property != null) {
            ImageConvertUtils.nv21ToGray(byteBuffer.array(), this.property.width, this.property.height);
        }
        return this.byteBuffer;
    }

    public Property acquireProperty() {
        return this.property;
    }

    public final Pair<byte[], Float> create(int i2, int i3) throws IOException {
        byte[] bArrBitmap2Jpeg;
        if (getPreviewSize() == null) {
            return null;
        }
        int iIntValue = ((Integer) getPreviewSize().first).intValue();
        int iIntValue2 = ((Integer) getPreviewSize().second).intValue();
        float fMin = Math.min(i2 / iIntValue, i3 / iIntValue2);
        float f2 = 1.0f;
        if (fMin >= 1.0f) {
            bArrBitmap2Jpeg = wrapJpeg(true);
        } else {
            Matrix matrix = new Matrix();
            matrix.postScale(fMin, fMin);
            f2 = fMin;
            bArrBitmap2Jpeg = ImageConvertUtils.bitmap2Jpeg(Bitmap.createBitmap(wrapBitmap(), 0, 0, iIntValue, iIntValue2, matrix, true), 100);
        }
        return Pair.create(bArrBitmap2Jpeg, Float.valueOf(f2));
    }

    public ByteBuffer getByteBuffer() {
        return this.byteBuffer;
    }

    public final synchronized MLFrame getFrame(boolean z2, boolean z3) {
        try {
            if (this.frameInit.booleanValue()) {
                return this;
            }
            if (!z2 && this.byteBuffer != null) {
                int formatType = this.property.getFormatType();
                if (z3 && formatType != 17) {
                    if (formatType == 842094169) {
                        this.byteBuffer = ByteBuffer.wrap(ImageConvertUtils.byteToNv21(ImageConvertUtils.buffer2Byte(this.byteBuffer)));
                    }
                    Property.Creator creator = new Property.Creator();
                    creator.setFormatType(17).setWidth(this.property.getWidth()).setHeight(this.property.getHeight()).setQuadrant(this.property.getQuadrant());
                    this.property = creator.create();
                    this.frameInit = Boolean.TRUE;
                    return this;
                }
                this.frameInit = Boolean.TRUE;
                return this;
            }
            this.bitmap = getPreviewBitmap();
            this.property = new Creator().setBitmap(readBitmap()).create().property;
            this.frameInit = Boolean.TRUE;
            return this;
        } catch (Throwable th) {
            throw th;
        }
    }

    public Bitmap getPreviewBitmap() {
        if (this.bytes == null && this.byteBuffer == null && this.bitmap == null) {
            throw new IllegalStateException("At least one of bytes, byteBuffer or bitmap should be not null");
        }
        return wrapBitmap();
    }

    public int getRecMode() {
        return this.recMode;
    }

    public final void initialize() {
        ByteBuffer byteBuffer = this.byteBuffer;
        if (byteBuffer != null) {
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(byteBuffer.capacity());
            byteBuffer.rewind();
            byteBufferAllocate.put(byteBuffer);
            byteBuffer.rewind();
            byteBufferAllocate.flip();
            this.byteBuffer = byteBufferAllocate;
        }
    }

    public Bitmap readBitmap() {
        return this.bitmap;
    }

    public void setRecMode(int i2) {
        this.recMode = i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0051  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final byte[] wrapJpeg(boolean r3) throws java.io.IOException {
        /*
            r2 = this;
            byte[] r0 = r2.bytes
            if (r0 == 0) goto L5
            return r0
        L5:
            java.nio.ByteBuffer r0 = r2.byteBuffer
            if (r0 == 0) goto L51
            com.huawei.hms.mlsdk.common.MLFrame$Property r0 = r2.property
            int r0 = r0.getFormatType()
            boolean r1 = r2.isSupportedYuvFormat(r0)
            if (r1 == 0) goto L49
            if (r3 == 0) goto L1f
            com.huawei.hms.mlsdk.common.MLFrame$Property r3 = r2.property
            int r3 = r3.getQuadrant()
            if (r3 != 0) goto L51
        L1f:
            java.nio.ByteBuffer r3 = r2.byteBuffer
            byte[] r3 = com.huawei.hms.ml.common.utils.ImageConvertUtils.buffer2Byte(r3)
            r1 = 842094169(0x32315659, float:1.0322389E-8)
            if (r1 != r0) goto L2e
            byte[] r3 = com.huawei.hms.ml.common.utils.ImageConvertUtils.byteToNv21(r3)
        L2e:
            com.huawei.hms.mlsdk.common.MLFrame$Property r0 = r2.property
            int r0 = r0.getWidth()
            com.huawei.hms.mlsdk.common.MLFrame$Property r1 = r2.property
            int r1 = r1.getHeight()
            byte[] r3 = com.huawei.hms.ml.common.utils.ImageConvertUtils.nv21ToJpeg(r3, r0, r1)
            com.huawei.hms.mlsdk.common.MLFrame$Property r0 = r2.property
            int r0 = r0.getQuadrant()
            if (r0 != 0) goto L52
            r2.bytes = r3
            goto L52
        L49:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.String r0 = "Only support NV21 or YV12"
            r3.<init>(r0)
            throw r3
        L51:
            r3 = 0
        L52:
            if (r3 != 0) goto L60
            android.graphics.Bitmap r3 = r2.wrapBitmap()
            r0 = 100
            byte[] r3 = com.huawei.hms.ml.common.utils.ImageConvertUtils.bitmap2Jpeg(r3, r0)
            r2.bytes = r3
        L60:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.mlsdk.common.MLFrame.wrapJpeg(boolean):byte[]");
    }

    private MLFrame(ByteBuffer byteBuffer, Property property) {
        this.frameInit = Boolean.FALSE;
        this.byteBuffer = byteBuffer;
        this.property = property == null ? new Property() : property;
    }

    private MLFrame(byte[] bArr, Property property) {
        this(ByteBuffer.wrap(bArr), property);
    }

    private MLFrame(Bitmap bitmap) {
        this.frameInit = Boolean.FALSE;
        this.bitmap = bitmap;
    }

    private MLFrame(Bitmap bitmap, Property property) {
        this.frameInit = Boolean.FALSE;
        this.bitmap = bitmap;
        this.property = property == null ? new Property() : property;
    }

    private MLFrame(byte[] bArr) {
        this.frameInit = Boolean.FALSE;
        this.bytes = bArr;
    }
}
