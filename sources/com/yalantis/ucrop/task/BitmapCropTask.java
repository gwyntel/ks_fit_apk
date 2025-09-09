package com.yalantis.ucrop.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.exifinterface.media.ExifInterface;
import com.yalantis.ucrop.callback.BitmapCropCallback;
import com.yalantis.ucrop.model.CropParameters;
import com.yalantis.ucrop.model.ExifInfo;
import com.yalantis.ucrop.model.ImageState;
import com.yalantis.ucrop.util.BitmapLoadUtils;
import com.yalantis.ucrop.util.FileUtils;
import com.yalantis.ucrop.util.ImageHeaderParser;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class BitmapCropTask extends AsyncTask<Void, Void, Throwable> {
    private static final String CONTENT_SCHEME = "content";
    private static final String TAG = "BitmapCropTask";
    private int cropOffsetX;
    private int cropOffsetY;
    private final Bitmap.CompressFormat mCompressFormat;
    private final int mCompressQuality;
    private final WeakReference<Context> mContext;
    private final BitmapCropCallback mCropCallback;
    private final RectF mCropRect;
    private int mCroppedImageHeight;
    private int mCroppedImageWidth;
    private float mCurrentAngle;
    private final RectF mCurrentImageRect;
    private float mCurrentScale;
    private final ExifInfo mExifInfo;
    private final String mImageInputPath;
    private final Uri mImageInputUri;
    private final String mImageOutputPath;
    private final Uri mImageOutputUri;
    private final int mMaxResultImageSizeX;
    private final int mMaxResultImageSizeY;
    private Bitmap mViewBitmap;

    public BitmapCropTask(@NonNull Context context, @Nullable Bitmap bitmap, @NonNull ImageState imageState, @NonNull CropParameters cropParameters, @Nullable BitmapCropCallback bitmapCropCallback) {
        this.mContext = new WeakReference<>(context);
        this.mViewBitmap = bitmap;
        this.mCropRect = imageState.getCropRect();
        this.mCurrentImageRect = imageState.getCurrentImageRect();
        this.mCurrentScale = imageState.getCurrentScale();
        this.mCurrentAngle = imageState.getCurrentAngle();
        this.mMaxResultImageSizeX = cropParameters.getMaxResultImageSizeX();
        this.mMaxResultImageSizeY = cropParameters.getMaxResultImageSizeY();
        this.mCompressFormat = cropParameters.getCompressFormat();
        this.mCompressQuality = cropParameters.getCompressQuality();
        this.mImageInputPath = cropParameters.getImageInputPath();
        this.mImageOutputPath = cropParameters.getImageOutputPath();
        this.mImageInputUri = cropParameters.getContentImageInputUri();
        this.mImageOutputUri = cropParameters.getContentImageOutputUri();
        this.mExifInfo = cropParameters.getExifInfo();
        this.mCropCallback = bitmapCropCallback;
    }

    private void copyExifForOutputFile(Context context) throws Throwable {
        boolean zHasContentScheme = BitmapLoadUtils.hasContentScheme(this.mImageInputUri);
        boolean zHasContentScheme2 = BitmapLoadUtils.hasContentScheme(this.mImageOutputUri);
        if (zHasContentScheme && zHasContentScheme2) {
            ImageHeaderParser.copyExif(context, this.mCroppedImageWidth, this.mCroppedImageHeight, this.mImageInputUri, this.mImageOutputUri);
            return;
        }
        if (zHasContentScheme) {
            ImageHeaderParser.copyExif(context, this.mCroppedImageWidth, this.mCroppedImageHeight, this.mImageInputUri, this.mImageOutputPath);
        } else if (zHasContentScheme2) {
            ImageHeaderParser.copyExif(context, new ExifInterface(this.mImageInputPath), this.mCroppedImageWidth, this.mCroppedImageHeight, this.mImageOutputUri);
        } else {
            ImageHeaderParser.copyExif(new ExifInterface(this.mImageInputPath), this.mCroppedImageWidth, this.mCroppedImageHeight, this.mImageOutputPath);
        }
    }

    private boolean crop() throws Throwable {
        Context context = this.mContext.get();
        if (context == null) {
            return false;
        }
        if (this.mMaxResultImageSizeX > 0 && this.mMaxResultImageSizeY > 0) {
            float fWidth = this.mCropRect.width() / this.mCurrentScale;
            float fHeight = this.mCropRect.height() / this.mCurrentScale;
            int i2 = this.mMaxResultImageSizeX;
            if (fWidth > i2 || fHeight > this.mMaxResultImageSizeY) {
                float fMin = Math.min(i2 / fWidth, this.mMaxResultImageSizeY / fHeight);
                Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(this.mViewBitmap, Math.round(r3.getWidth() * fMin), Math.round(this.mViewBitmap.getHeight() * fMin), false);
                Bitmap bitmap = this.mViewBitmap;
                if (bitmap != bitmapCreateScaledBitmap) {
                    bitmap.recycle();
                }
                this.mViewBitmap = bitmapCreateScaledBitmap;
                this.mCurrentScale /= fMin;
            }
        }
        if (this.mCurrentAngle != 0.0f) {
            Matrix matrix = new Matrix();
            matrix.setRotate(this.mCurrentAngle, this.mViewBitmap.getWidth() / 2, this.mViewBitmap.getHeight() / 2);
            Bitmap bitmap2 = this.mViewBitmap;
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), this.mViewBitmap.getHeight(), matrix, true);
            Bitmap bitmap3 = this.mViewBitmap;
            if (bitmap3 != bitmapCreateBitmap) {
                bitmap3.recycle();
            }
            this.mViewBitmap = bitmapCreateBitmap;
        }
        this.cropOffsetX = Math.round((this.mCropRect.left - this.mCurrentImageRect.left) / this.mCurrentScale);
        this.cropOffsetY = Math.round((this.mCropRect.top - this.mCurrentImageRect.top) / this.mCurrentScale);
        this.mCroppedImageWidth = Math.round(this.mCropRect.width() / this.mCurrentScale);
        int iRound = Math.round(this.mCropRect.height() / this.mCurrentScale);
        this.mCroppedImageHeight = iRound;
        boolean zShouldCrop = shouldCrop(this.mCroppedImageWidth, iRound);
        Log.i(TAG, "Should crop: " + zShouldCrop);
        if (!zShouldCrop) {
            FileUtils.copyFile(context, this.mImageInputUri, this.mImageOutputUri);
            return false;
        }
        saveImage(Bitmap.createBitmap(this.mViewBitmap, this.cropOffsetX, this.cropOffsetY, this.mCroppedImageWidth, this.mCroppedImageHeight));
        if (!this.mCompressFormat.equals(Bitmap.CompressFormat.JPEG)) {
            return true;
        }
        copyExifForOutputFile(context);
        return true;
    }

    private void saveImage(@NonNull Bitmap bitmap) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        OutputStream outputStreamOpenOutputStream;
        Context context = this.mContext.get();
        if (context == null) {
            return;
        }
        OutputStream outputStream = null;
        try {
            outputStreamOpenOutputStream = context.getContentResolver().openOutputStream(this.mImageOutputUri);
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
            } catch (IOException e2) {
                e = e2;
                byteArrayOutputStream = null;
            } catch (Throwable th) {
                th = th;
                byteArrayOutputStream = null;
            }
        } catch (IOException e3) {
            e = e3;
            byteArrayOutputStream = null;
        } catch (Throwable th2) {
            th = th2;
            byteArrayOutputStream = null;
        }
        try {
            bitmap.compress(this.mCompressFormat, this.mCompressQuality, byteArrayOutputStream);
            outputStreamOpenOutputStream.write(byteArrayOutputStream.toByteArray());
            bitmap.recycle();
            BitmapLoadUtils.close(outputStreamOpenOutputStream);
        } catch (IOException e4) {
            e = e4;
            outputStream = outputStreamOpenOutputStream;
            try {
                Log.e(TAG, e.getLocalizedMessage());
                BitmapLoadUtils.close(outputStream);
                BitmapLoadUtils.close(byteArrayOutputStream);
            } catch (Throwable th3) {
                th = th3;
                BitmapLoadUtils.close(outputStream);
                BitmapLoadUtils.close(byteArrayOutputStream);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            outputStream = outputStreamOpenOutputStream;
            BitmapLoadUtils.close(outputStream);
            BitmapLoadUtils.close(byteArrayOutputStream);
            throw th;
        }
        BitmapLoadUtils.close(byteArrayOutputStream);
    }

    private boolean shouldCrop(int i2, int i3) {
        int iRound = Math.round(Math.max(i2, i3) / 1000.0f) + 1;
        if (this.mMaxResultImageSizeX > 0 && this.mMaxResultImageSizeY > 0) {
            return true;
        }
        float f2 = iRound;
        return Math.abs(this.mCropRect.left - this.mCurrentImageRect.left) > f2 || Math.abs(this.mCropRect.top - this.mCurrentImageRect.top) > f2 || Math.abs(this.mCropRect.bottom - this.mCurrentImageRect.bottom) > f2 || Math.abs(this.mCropRect.right - this.mCurrentImageRect.right) > f2 || this.mCurrentAngle != 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public Throwable doInBackground(Void... voidArr) {
        Bitmap bitmap = this.mViewBitmap;
        if (bitmap == null) {
            return new NullPointerException("ViewBitmap is null");
        }
        if (bitmap.isRecycled()) {
            return new NullPointerException("ViewBitmap is recycled");
        }
        if (this.mCurrentImageRect.isEmpty()) {
            return new NullPointerException("CurrentImageRect is empty");
        }
        if (this.mImageOutputUri == null) {
            return new NullPointerException("ImageOutputUri is null");
        }
        try {
            crop();
            this.mViewBitmap = null;
            return null;
        } catch (Throwable th) {
            return th;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public void onPostExecute(Throwable th) {
        BitmapCropCallback bitmapCropCallback = this.mCropCallback;
        if (bitmapCropCallback != null) {
            if (th == null) {
                this.mCropCallback.onBitmapCropped(BitmapLoadUtils.hasContentScheme(this.mImageOutputUri) ? this.mImageOutputUri : Uri.fromFile(new File(this.mImageOutputPath)), this.cropOffsetX, this.cropOffsetY, this.mCroppedImageWidth, this.mCroppedImageHeight);
            } else {
                bitmapCropCallback.onCropFailure(th);
            }
        }
    }
}
