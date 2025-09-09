package io.flutter.embedding.engine.loader;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import com.xiaomi.mipush.sdk.Constants;
import io.flutter.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/* loaded from: classes4.dex */
class ResourceExtractor {
    private static final String[] SUPPORTED_ABIS = Build.SUPPORTED_ABIS;
    private static final String TAG = "ResourceExtractor";
    private static final String TIMESTAMP_PREFIX = "res_timestamp-";

    @NonNull
    private final AssetManager mAssetManager;

    @NonNull
    private final String mDataDirPath;
    private ExtractTask mExtractTask;

    @NonNull
    private final PackageManager mPackageManager;

    @NonNull
    private final String mPackageName;

    @NonNull
    private final HashSet<String> mResources = new HashSet<>();

    private static class ExtractTask extends AsyncTask<Void, Void, Void> {

        @NonNull
        private final AssetManager mAssetManager;

        @NonNull
        private final String mDataDirPath;

        @NonNull
        private final PackageManager mPackageManager;

        @NonNull
        private final String mPackageName;

        @NonNull
        private final HashSet<String> mResources;

        ExtractTask(@NonNull String str, @NonNull HashSet<String> hashSet, @NonNull String str2, @NonNull PackageManager packageManager, @NonNull AssetManager assetManager) {
            this.mDataDirPath = str;
            this.mResources = hashSet;
            this.mAssetManager = assetManager;
            this.mPackageName = str2;
            this.mPackageManager = packageManager;
        }

        @WorkerThread
        private boolean extractAPK(@NonNull File file) throws IOException {
            Iterator<String> it = this.mResources.iterator();
            while (it.hasNext()) {
                String next = it.next();
                try {
                    File file2 = new File(file, next);
                    if (!file2.exists()) {
                        if (file2.getParentFile() != null) {
                            file2.getParentFile().mkdirs();
                        }
                        InputStream inputStreamOpen = this.mAssetManager.open(next);
                        try {
                            FileOutputStream fileOutputStream = new FileOutputStream(file2);
                            try {
                                ResourceExtractor.copy(inputStreamOpen, fileOutputStream);
                                fileOutputStream.close();
                                if (inputStreamOpen != null) {
                                    inputStreamOpen.close();
                                }
                            } catch (Throwable th) {
                                try {
                                    fileOutputStream.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                                throw th;
                            }
                        } catch (Throwable th3) {
                            if (inputStreamOpen != null) {
                                try {
                                    inputStreamOpen.close();
                                } catch (Throwable th4) {
                                    th3.addSuppressed(th4);
                                }
                            }
                            throw th3;
                        }
                    }
                } catch (FileNotFoundException unused) {
                    continue;
                } catch (IOException e2) {
                    Log.w(ResourceExtractor.TAG, "Exception unpacking resources: " + e2.getMessage());
                    ResourceExtractor.deleteFiles(this.mDataDirPath, this.mResources);
                    return false;
                }
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voidArr) throws PackageManager.NameNotFoundException, IOException {
            File file = new File(this.mDataDirPath);
            String strCheckTimestamp = ResourceExtractor.checkTimestamp(file, this.mPackageManager, this.mPackageName);
            if (strCheckTimestamp == null) {
                return null;
            }
            ResourceExtractor.deleteFiles(this.mDataDirPath, this.mResources);
            if (!extractAPK(file)) {
                return null;
            }
            try {
                new File(file, strCheckTimestamp).createNewFile();
            } catch (IOException unused) {
                Log.w(ResourceExtractor.TAG, "Failed to write resource timestamp");
            }
            return null;
        }
    }

    ResourceExtractor(@NonNull String str, @NonNull String str2, @NonNull PackageManager packageManager, @NonNull AssetManager assetManager) {
        this.mDataDirPath = str;
        this.mPackageName = str2;
        this.mPackageManager = packageManager;
        this.mAssetManager = assetManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String checkTimestamp(@NonNull File file, @NonNull PackageManager packageManager, @NonNull String str) throws PackageManager.NameNotFoundException {
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
            if (packageInfo == null) {
                return TIMESTAMP_PREFIX;
            }
            String str2 = TIMESTAMP_PREFIX + getVersionCode(packageInfo) + Constants.ACCEPT_TIME_SEPARATOR_SERVER + packageInfo.lastUpdateTime;
            String[] existingTimestamps = getExistingTimestamps(file);
            if (existingTimestamps != null && existingTimestamps.length == 1 && str2.equals(existingTimestamps[0])) {
                return null;
            }
            return str2;
        } catch (PackageManager.NameNotFoundException unused) {
            return TIMESTAMP_PREFIX;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void copy(@NonNull InputStream inputStream, @NonNull OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[16384];
        while (true) {
            int i2 = inputStream.read(bArr);
            if (i2 < 0) {
                return;
            } else {
                outputStream.write(bArr, 0, i2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void deleteFiles(@NonNull String str, @NonNull HashSet<String> hashSet) {
        File file = new File(str);
        Iterator<String> it = hashSet.iterator();
        while (it.hasNext()) {
            File file2 = new File(file, it.next());
            if (file2.exists()) {
                file2.delete();
            }
        }
        String[] existingTimestamps = getExistingTimestamps(file);
        if (existingTimestamps == null) {
            return;
        }
        for (String str2 : existingTimestamps) {
            new File(file, str2).delete();
        }
    }

    private static String[] getExistingTimestamps(File file) {
        return file.list(new FilenameFilter() { // from class: io.flutter.embedding.engine.loader.ResourceExtractor.1
            @Override // java.io.FilenameFilter
            public boolean accept(File file2, String str) {
                return str.startsWith(ResourceExtractor.TIMESTAMP_PREFIX);
            }
        });
    }

    static long getVersionCode(@NonNull PackageInfo packageInfo) {
        return Build.VERSION.SDK_INT >= 28 ? packageInfo.getLongVersionCode() : packageInfo.versionCode;
    }

    ResourceExtractor addResource(@NonNull String str) {
        this.mResources.add(str);
        return this;
    }

    ResourceExtractor addResources(@NonNull Collection<String> collection) {
        this.mResources.addAll(collection);
        return this;
    }

    ResourceExtractor start() {
        ExtractTask extractTask = new ExtractTask(this.mDataDirPath, this.mResources, this.mPackageName, this.mPackageManager, this.mAssetManager);
        this.mExtractTask = extractTask;
        extractTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        return this;
    }

    void waitForCompletion() throws ExecutionException, InterruptedException {
        ExtractTask extractTask = this.mExtractTask;
        if (extractTask == null) {
            return;
        }
        try {
            extractTask.get();
        } catch (InterruptedException | CancellationException | ExecutionException unused) {
            deleteFiles(this.mDataDirPath, this.mResources);
        }
    }
}
