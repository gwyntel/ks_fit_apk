package top.zibin.luban;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.luck.picture.lib.config.PictureMimeType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import top.zibin.luban.io.ArrayPoolProvide;

/* loaded from: classes5.dex */
public class Luban implements Handler.Callback {
    private static final String DEFAULT_DISK_CACHE_DIR = "luban_disk_cache";
    private static final String KEY_SOURCE = "source";
    private static final int MSG_COMPRESS_ERROR = 2;
    private static final int MSG_COMPRESS_START = 1;
    private static final int MSG_COMPRESS_SUCCESS = 0;
    private static final String TAG = "Luban";
    private boolean focusAlpha;
    private boolean isUseIOBufferPool;
    private OnCompressListener mCompressListener;
    private CompressionPredicate mCompressionPredicate;
    private Handler mHandler;
    private int mLeastCompressSize;
    private OnNewCompressListener mNewCompressListener;
    private OnRenameListener mRenameListener;
    private List<InputStreamProvider> mStreamProviders;
    private String mTargetDir;

    public static class Builder {
        private Context context;
        private boolean focusAlpha;
        private OnCompressListener mCompressListener;
        private CompressionPredicate mCompressionPredicate;
        private OnNewCompressListener mNewCompressListener;
        private OnRenameListener mRenameListener;
        private String mTargetDir;
        private boolean isUseBufferPool = true;
        private int mLeastCompressSize = 100;
        private List<InputStreamProvider> mStreamProviders = new ArrayList();

        Builder(Context context) {
            this.context = context;
        }

        private Luban build() {
            return new Luban(this);
        }

        public Builder filter(CompressionPredicate compressionPredicate) {
            this.mCompressionPredicate = compressionPredicate;
            return this;
        }

        public File get(String str) throws IOException {
            return get(str, 0);
        }

        public Builder ignoreBy(int i2) {
            this.mLeastCompressSize = i2;
            return this;
        }

        public Builder isUseIOBufferPool(boolean z2) {
            this.isUseBufferPool = z2;
            return this;
        }

        public void launch() {
            build().launch(this.context);
        }

        public Builder load(InputStreamProvider inputStreamProvider) {
            this.mStreamProviders.add(inputStreamProvider);
            return this;
        }

        @Deprecated
        public Builder putGear(int i2) {
            return this;
        }

        public Builder setCompressListener(OnCompressListener onCompressListener) {
            this.mCompressListener = onCompressListener;
            return this;
        }

        @Deprecated
        public Builder setFocusAlpha(boolean z2) {
            this.focusAlpha = z2;
            return this;
        }

        public Builder setRenameListener(OnRenameListener onRenameListener) {
            this.mRenameListener = onRenameListener;
            return this;
        }

        public Builder setTargetDir(String str) {
            this.mTargetDir = str;
            return this;
        }

        public File get(final String str, final int i2) throws IOException {
            return build().get(new InputStreamAdapter() { // from class: top.zibin.luban.Luban.Builder.4
                @Override // top.zibin.luban.InputStreamProvider
                public int getIndex() {
                    return i2;
                }

                @Override // top.zibin.luban.InputStreamProvider
                public String getPath() {
                    return str;
                }

                @Override // top.zibin.luban.InputStreamAdapter
                public InputStream openInternal() {
                    return ArrayPoolProvide.getInstance().openInputStream(str);
                }
            }, this.context);
        }

        public <T> Builder load(List<T> list) {
            int i2 = -1;
            for (T t2 : list) {
                i2++;
                if (t2 instanceof String) {
                    load((String) t2, i2);
                } else if (t2 instanceof File) {
                    load((File) t2, i2);
                } else {
                    if (!(t2 instanceof Uri)) {
                        throw new IllegalArgumentException("Incoming data type exception, it must be String, File, Uri or Bitmap");
                    }
                    load((Uri) t2, i2);
                }
            }
            return this;
        }

        public Builder setCompressListener(OnNewCompressListener onNewCompressListener) {
            this.mNewCompressListener = onNewCompressListener;
            return this;
        }

        public List<File> get() throws IOException {
            return build().get(this.context);
        }

        public Builder load(File file) {
            load(file, 0);
            return this;
        }

        private Builder load(final File file, final int i2) {
            this.mStreamProviders.add(new InputStreamAdapter() { // from class: top.zibin.luban.Luban.Builder.1
                @Override // top.zibin.luban.InputStreamProvider
                public int getIndex() {
                    return i2;
                }

                @Override // top.zibin.luban.InputStreamProvider
                public String getPath() {
                    return file.getAbsolutePath();
                }

                @Override // top.zibin.luban.InputStreamAdapter
                public InputStream openInternal() {
                    return ArrayPoolProvide.getInstance().openInputStream(file.getAbsolutePath());
                }
            });
            return this;
        }

        public Builder load(String str) {
            load(str, 0);
            return this;
        }

        private Builder load(final String str, final int i2) {
            this.mStreamProviders.add(new InputStreamAdapter() { // from class: top.zibin.luban.Luban.Builder.2
                @Override // top.zibin.luban.InputStreamProvider
                public int getIndex() {
                    return i2;
                }

                @Override // top.zibin.luban.InputStreamProvider
                public String getPath() {
                    return str;
                }

                @Override // top.zibin.luban.InputStreamAdapter
                public InputStream openInternal() {
                    return ArrayPoolProvide.getInstance().openInputStream(str);
                }
            });
            return this;
        }

        public Builder load(Uri uri) {
            load(uri, 0);
            return this;
        }

        private Builder load(final Uri uri, final int i2) {
            this.mStreamProviders.add(new InputStreamAdapter() { // from class: top.zibin.luban.Luban.Builder.3
                @Override // top.zibin.luban.InputStreamProvider
                public int getIndex() {
                    return i2;
                }

                @Override // top.zibin.luban.InputStreamProvider
                public String getPath() {
                    return Checker.isContent(uri.toString()) ? uri.toString() : uri.getPath();
                }

                @Override // top.zibin.luban.InputStreamAdapter
                public InputStream openInternal() throws IOException {
                    return Builder.this.isUseBufferPool ? ArrayPoolProvide.getInstance().openInputStream(Builder.this.context.getContentResolver(), uri) : Builder.this.context.getContentResolver().openInputStream(uri);
                }
            });
            return this;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public File compress(Context context, InputStreamProvider inputStreamProvider) throws IOException {
        try {
            return compressReal(context, inputStreamProvider);
        } finally {
            inputStreamProvider.close();
        }
    }

    private File compressReal(Context context, InputStreamProvider inputStreamProvider) throws IOException {
        Checker checker = Checker.SINGLE;
        File imageCacheFile = getImageCacheFile(context, checker.extSuffix(inputStreamProvider));
        String path = Checker.isContent(inputStreamProvider.getPath()) ? LubanUtils.getPath(context, Uri.parse(inputStreamProvider.getPath())) : inputStreamProvider.getPath();
        OnRenameListener onRenameListener = this.mRenameListener;
        if (onRenameListener != null) {
            imageCacheFile = getImageCustomFile(context, onRenameListener.rename(path));
        }
        CompressionPredicate compressionPredicate = this.mCompressionPredicate;
        return compressionPredicate != null ? (compressionPredicate.apply(path) && checker.needCompress(this.mLeastCompressSize, path)) ? new Engine(inputStreamProvider, imageCacheFile, this.focusAlpha).a() : new File(path) : checker.needCompress(this.mLeastCompressSize, path) ? new Engine(inputStreamProvider, imageCacheFile, this.focusAlpha).a() : new File(path);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public File get(InputStreamProvider inputStreamProvider, Context context) throws IOException {
        try {
            return new Engine(inputStreamProvider, getImageCacheFile(context, Checker.SINGLE.extSuffix(inputStreamProvider)), this.focusAlpha).a();
        } finally {
            inputStreamProvider.close();
        }
    }

    private File getImageCacheDir(Context context) {
        return getImageCacheDir(context, DEFAULT_DISK_CACHE_DIR);
    }

    private File getImageCacheFile(Context context, String str) {
        if (TextUtils.isEmpty(this.mTargetDir)) {
            this.mTargetDir = getImageCacheDir(context).getAbsolutePath();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.mTargetDir);
        sb.append("/");
        sb.append(System.currentTimeMillis());
        sb.append((int) (Math.random() * 1000.0d));
        if (TextUtils.isEmpty(str)) {
            str = PictureMimeType.JPG;
        }
        sb.append(str);
        return new File(sb.toString());
    }

    private File getImageCustomFile(Context context, String str) {
        if (TextUtils.isEmpty(this.mTargetDir)) {
            this.mTargetDir = getImageCacheDir(context).getAbsolutePath();
        }
        return new File(this.mTargetDir + "/" + str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launch(final Context context) {
        List<InputStreamProvider> list = this.mStreamProviders;
        if (list != null && list.size() != 0) {
            Iterator<InputStreamProvider> it = this.mStreamProviders.iterator();
            while (it.hasNext()) {
                final InputStreamProvider next = it.next();
                AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() { // from class: top.zibin.luban.Luban.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            Luban.this.mHandler.sendMessage(Luban.this.mHandler.obtainMessage(1));
                            File fileCompress = Luban.this.compress(context, next);
                            Message messageObtainMessage = Luban.this.mHandler.obtainMessage(0);
                            messageObtainMessage.arg1 = next.getIndex();
                            messageObtainMessage.obj = fileCompress;
                            Bundle bundle = new Bundle();
                            bundle.putString("source", next.getPath());
                            messageObtainMessage.setData(bundle);
                            Luban.this.mHandler.sendMessage(messageObtainMessage);
                        } catch (Exception unused) {
                            Message messageObtainMessage2 = Luban.this.mHandler.obtainMessage(2);
                            messageObtainMessage2.arg1 = next.getIndex();
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("source", next.getPath());
                            messageObtainMessage2.setData(bundle2);
                            Luban.this.mHandler.sendMessage(messageObtainMessage2);
                        }
                    }
                });
                it.remove();
            }
            return;
        }
        OnCompressListener onCompressListener = this.mCompressListener;
        if (onCompressListener != null) {
            onCompressListener.onError(-1, new NullPointerException("image file cannot be null"));
        }
        OnNewCompressListener onNewCompressListener = this.mNewCompressListener;
        if (onNewCompressListener != null) {
            onNewCompressListener.onError("", new NullPointerException("image file cannot be null"));
        }
    }

    public static Builder with(Context context) {
        return new Builder(context);
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        int i2 = message.what;
        if (i2 == 0) {
            OnCompressListener onCompressListener = this.mCompressListener;
            if (onCompressListener != null) {
                onCompressListener.onSuccess(message.arg1, (File) message.obj);
            }
            OnNewCompressListener onNewCompressListener = this.mNewCompressListener;
            if (onNewCompressListener == null) {
                return false;
            }
            onNewCompressListener.onSuccess(message.getData().getString("source"), (File) message.obj);
            return false;
        }
        if (i2 == 1) {
            OnCompressListener onCompressListener2 = this.mCompressListener;
            if (onCompressListener2 != null) {
                onCompressListener2.onStart();
            }
            OnNewCompressListener onNewCompressListener2 = this.mNewCompressListener;
            if (onNewCompressListener2 == null) {
                return false;
            }
            onNewCompressListener2.onStart();
            return false;
        }
        if (i2 != 2) {
            return false;
        }
        OnCompressListener onCompressListener3 = this.mCompressListener;
        if (onCompressListener3 != null) {
            onCompressListener3.onError(message.arg1, (Throwable) message.obj);
        }
        OnNewCompressListener onNewCompressListener3 = this.mNewCompressListener;
        if (onNewCompressListener3 == null) {
            return false;
        }
        onNewCompressListener3.onError(message.getData().getString("source"), (Throwable) message.obj);
        return false;
    }

    private Luban(Builder builder) {
        this.mTargetDir = builder.mTargetDir;
        this.focusAlpha = builder.focusAlpha;
        this.isUseIOBufferPool = builder.isUseBufferPool;
        this.mRenameListener = builder.mRenameListener;
        this.mStreamProviders = builder.mStreamProviders;
        this.mCompressListener = builder.mCompressListener;
        this.mNewCompressListener = builder.mNewCompressListener;
        this.mLeastCompressSize = builder.mLeastCompressSize;
        this.mCompressionPredicate = builder.mCompressionPredicate;
        this.mHandler = new Handler(Looper.getMainLooper(), this);
    }

    private static File getImageCacheDir(Context context, String str) {
        File externalCacheDir = context.getExternalCacheDir();
        if (externalCacheDir == null) {
            if (Log.isLoggable(TAG, 6)) {
                Log.e(TAG, "default disk cache dir is null");
            }
            return null;
        }
        File file = new File(externalCacheDir, str);
        if (file.mkdirs() || (file.exists() && file.isDirectory())) {
            return file;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<File> get(Context context) throws IOException {
        ArrayList arrayList = new ArrayList();
        Iterator<InputStreamProvider> it = this.mStreamProviders.iterator();
        while (it.hasNext()) {
            arrayList.add(compress(context, it.next()));
            it.remove();
        }
        return arrayList;
    }
}
