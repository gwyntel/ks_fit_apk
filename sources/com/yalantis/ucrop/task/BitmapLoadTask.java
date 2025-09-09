package com.yalantis.ucrop.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.yalantis.ucrop.OkHttpClientStore;
import com.yalantis.ucrop.callback.BitmapLoadCallback;
import com.yalantis.ucrop.model.ExifInfo;
import com.yalantis.ucrop.util.BitmapLoadUtils;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;

/* loaded from: classes4.dex */
public class BitmapLoadTask extends AsyncTask<Void, Void, BitmapWorkerResult> {
    private static final int MAX_BITMAP_SIZE = 104857600;
    private static final String TAG = "BitmapWorkerTask";
    private final BitmapLoadCallback mBitmapLoadCallback;
    private final WeakReference<Context> mContext;
    private Uri mInputUri;
    private Uri mOutputUri;
    private final int mRequiredHeight;
    private final int mRequiredWidth;

    public BitmapLoadTask(@NonNull Context context, @NonNull Uri uri, @Nullable Uri uri2, int i2, int i3, BitmapLoadCallback bitmapLoadCallback) {
        this.mContext = new WeakReference<>(context);
        this.mInputUri = uri;
        this.mOutputUri = uri2;
        this.mRequiredWidth = i2;
        this.mRequiredHeight = i3;
        this.mBitmapLoadCallback = bitmapLoadCallback;
    }

    private boolean checkSize(Bitmap bitmap, BitmapFactory.Options options) {
        if ((bitmap != null ? bitmap.getByteCount() : 0) <= MAX_BITMAP_SIZE) {
            return false;
        }
        options.inSampleSize *= 2;
        return true;
    }

    private void copyFile(@NonNull Uri uri, @Nullable Uri uri2) throws Throwable {
        InputStream inputStreamOpenInputStream;
        Log.d(TAG, "copyFile");
        if (uri2 == null) {
            throw new NullPointerException("Output Uri is null - cannot copy image");
        }
        Context context = this.mContext.get();
        try {
            inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
        } catch (Throwable th) {
            th = th;
            inputStreamOpenInputStream = null;
        }
        try {
            if (inputStreamOpenInputStream == null) {
                throw new NullPointerException("InputStream for given input Uri is null");
            }
            OutputStream outputStreamOpenOutputStream = isContentUri(uri2) ? context.getContentResolver().openOutputStream(uri2) : new FileOutputStream(new File(uri2.getPath()));
            byte[] bArr = new byte[1024];
            while (true) {
                int i2 = inputStreamOpenInputStream.read(bArr);
                if (i2 <= 0) {
                    BitmapLoadUtils.close(outputStreamOpenOutputStream);
                    BitmapLoadUtils.close(inputStreamOpenInputStream);
                    this.mInputUri = this.mOutputUri;
                    return;
                }
                outputStreamOpenOutputStream.write(bArr, 0, i2);
            }
        } catch (Throwable th2) {
            th = th2;
            BitmapLoadUtils.close(null);
            BitmapLoadUtils.close(inputStreamOpenInputStream);
            this.mInputUri = this.mOutputUri;
            throw th;
        }
    }

    private void downloadFile(@NonNull Uri uri, @Nullable Uri uri2) throws Throwable {
        Closeable closeable;
        Response response;
        Log.d(TAG, "downloadFile");
        if (uri2 == null) {
            throw new NullPointerException("Output Uri is null - cannot download image");
        }
        Context context = this.mContext.get();
        if (context == null) {
            throw new NullPointerException("Context is null");
        }
        OkHttpClient client = OkHttpClientStore.INSTANCE.getClient();
        BufferedSource bufferedSource = null;
        try {
            Response responseExecute = client.newCall(new Request.Builder().url(uri.toString()).build()).execute();
            try {
                BufferedSource bufferedSourceSource = responseExecute.body().source();
                try {
                    OutputStream outputStreamOpenOutputStream = isContentUri(this.mOutputUri) ? context.getContentResolver().openOutputStream(uri2) : new FileOutputStream(new File(uri2.getPath()));
                    if (outputStreamOpenOutputStream == null) {
                        throw new NullPointerException("OutputStream for given output Uri is null");
                    }
                    Sink sink = Okio.sink(outputStreamOpenOutputStream);
                    bufferedSourceSource.readAll(sink);
                    BitmapLoadUtils.close(bufferedSourceSource);
                    BitmapLoadUtils.close(sink);
                    BitmapLoadUtils.close(responseExecute.body());
                    client.dispatcher().cancelAll();
                    this.mInputUri = this.mOutputUri;
                } catch (Throwable th) {
                    th = th;
                    response = responseExecute;
                    closeable = null;
                    bufferedSource = bufferedSourceSource;
                    BitmapLoadUtils.close(bufferedSource);
                    BitmapLoadUtils.close(closeable);
                    if (response != null) {
                        BitmapLoadUtils.close(response.body());
                    }
                    client.dispatcher().cancelAll();
                    this.mInputUri = this.mOutputUri;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                response = responseExecute;
                closeable = null;
            }
        } catch (Throwable th3) {
            th = th3;
            closeable = null;
            response = null;
        }
    }

    private boolean isContentUri(Uri uri) {
        return uri.getScheme().equals("content");
    }

    private boolean isDownloadUri(Uri uri) {
        String scheme = uri.getScheme();
        return scheme.equals("http") || scheme.equals("https");
    }

    private boolean isFileUri(Uri uri) {
        return uri.getScheme().equals("file");
    }

    private void processInputUri() throws IOException, NullPointerException {
        Log.d(TAG, "Uri scheme: " + this.mInputUri.getScheme());
        if (isDownloadUri(this.mInputUri)) {
            try {
                downloadFile(this.mInputUri, this.mOutputUri);
                return;
            } catch (IOException | NullPointerException e2) {
                Log.e(TAG, "Downloading failed", e2);
                throw e2;
            }
        }
        if (isContentUri(this.mInputUri)) {
            try {
                copyFile(this.mInputUri, this.mOutputUri);
                return;
            } catch (IOException | NullPointerException e3) {
                Log.e(TAG, "Copying failed", e3);
                throw e3;
            }
        }
        if (isFileUri(this.mInputUri)) {
            return;
        }
        String scheme = this.mInputUri.getScheme();
        Log.e(TAG, "Invalid Uri scheme " + scheme);
        throw new IllegalArgumentException("Invalid Uri scheme" + scheme);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public BitmapWorkerResult doInBackground(Void... voidArr) throws FileNotFoundException {
        InputStream inputStreamOpenInputStream;
        Context context = this.mContext.get();
        if (context == null) {
            return new BitmapWorkerResult(new NullPointerException("context is null"));
        }
        if (this.mInputUri == null) {
            return new BitmapWorkerResult(new NullPointerException("Input Uri cannot be null"));
        }
        try {
            processInputUri();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inSampleSize = BitmapLoadUtils.calculateInSampleSize(options, this.mRequiredWidth, this.mRequiredHeight);
            boolean z2 = false;
            options.inJustDecodeBounds = false;
            Bitmap bitmapDecodeStream = null;
            while (!z2) {
                try {
                    inputStreamOpenInputStream = context.getContentResolver().openInputStream(this.mInputUri);
                } catch (IOException e2) {
                    Log.e(TAG, "doInBackground: ImageDecoder.createSource: ", e2);
                    return new BitmapWorkerResult(new IllegalArgumentException("Bitmap could not be decoded from the Uri: [" + this.mInputUri + "]", e2));
                } catch (OutOfMemoryError e3) {
                    Log.e(TAG, "doInBackground: BitmapFactory.decodeFileDescriptor: ", e3);
                    options.inSampleSize *= 2;
                }
                try {
                    bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpenInputStream, null, options);
                    if (options.outWidth == -1 || options.outHeight == -1) {
                        return new BitmapWorkerResult(new IllegalArgumentException("Bounds for bitmap could not be retrieved from the Uri: [" + this.mInputUri + "]"));
                    }
                    BitmapLoadUtils.close(inputStreamOpenInputStream);
                    if (!checkSize(bitmapDecodeStream, options)) {
                        z2 = true;
                    }
                } finally {
                    BitmapLoadUtils.close(inputStreamOpenInputStream);
                }
            }
            if (bitmapDecodeStream == null) {
                return new BitmapWorkerResult(new IllegalArgumentException("Bitmap could not be decoded from the Uri: [" + this.mInputUri + "]"));
            }
            int exifOrientation = BitmapLoadUtils.getExifOrientation(context, this.mInputUri);
            int iExifToDegrees = BitmapLoadUtils.exifToDegrees(exifOrientation);
            int iExifToTranslation = BitmapLoadUtils.exifToTranslation(exifOrientation);
            ExifInfo exifInfo = new ExifInfo(exifOrientation, iExifToDegrees, iExifToTranslation);
            Matrix matrix = new Matrix();
            if (iExifToDegrees != 0) {
                matrix.preRotate(iExifToDegrees);
            }
            if (iExifToTranslation != 1) {
                matrix.postScale(iExifToTranslation, 1.0f);
            }
            return !matrix.isIdentity() ? new BitmapWorkerResult(BitmapLoadUtils.transformBitmap(bitmapDecodeStream, matrix), exifInfo) : new BitmapWorkerResult(bitmapDecodeStream, exifInfo);
        } catch (IOException | NullPointerException e4) {
            return new BitmapWorkerResult(e4);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public void onPostExecute(BitmapWorkerResult bitmapWorkerResult) {
        Exception exc = bitmapWorkerResult.f24663c;
        if (exc == null) {
            this.mBitmapLoadCallback.onBitmapLoaded(bitmapWorkerResult.f24661a, bitmapWorkerResult.f24662b, this.mInputUri, this.mOutputUri);
        } else {
            this.mBitmapLoadCallback.onFailure(exc);
        }
    }

    public static class BitmapWorkerResult {

        /* renamed from: a, reason: collision with root package name */
        Bitmap f24661a;

        /* renamed from: b, reason: collision with root package name */
        ExifInfo f24662b;

        /* renamed from: c, reason: collision with root package name */
        Exception f24663c;

        public BitmapWorkerResult(@NonNull Bitmap bitmap, @NonNull ExifInfo exifInfo) {
            this.f24661a = bitmap;
            this.f24662b = exifInfo;
        }

        public BitmapWorkerResult(@NonNull Exception exc) {
            this.f24663c = exc;
        }
    }
}
