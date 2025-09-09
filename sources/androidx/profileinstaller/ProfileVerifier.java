package androidx.profileinstaller;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.WorkerThread;
import androidx.concurrent.futures.ResolvableFuture;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class ProfileVerifier {
    private static final String CUR_PROFILES_BASE_DIR = "/data/misc/profiles/cur/0/";
    private static final String PROFILE_FILE_NAME = "primary.prof";
    private static final String PROFILE_INSTALLED_CACHE_FILE_NAME = "profileInstalled";
    private static final String REF_PROFILES_BASE_DIR = "/data/misc/profiles/ref/";
    private static final String TAG = "ProfileVerifier";
    private static final ResolvableFuture<CompilationStatus> sFuture = ResolvableFuture.create();
    private static final Object SYNC_OBJ = new Object();

    @Nullable
    private static CompilationStatus sCompilationStatus = null;

    @RequiresApi(33)
    private static class Api33Impl {
        private Api33Impl() {
        }

        @DoNotInline
        static PackageInfo a(PackageManager packageManager, Context context) throws PackageManager.NameNotFoundException {
            return packageManager.getPackageInfo(context.getPackageName(), PackageManager.PackageInfoFlags.of(0L));
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    static class Cache {
        private static final int SCHEMA = 1;

        /* renamed from: a, reason: collision with root package name */
        final int f5756a;

        /* renamed from: b, reason: collision with root package name */
        final int f5757b;

        /* renamed from: c, reason: collision with root package name */
        final long f5758c;

        /* renamed from: d, reason: collision with root package name */
        final long f5759d;

        Cache(int i2, int i3, long j2, long j3) {
            this.f5756a = i2;
            this.f5757b = i3;
            this.f5758c = j2;
            this.f5759d = j3;
        }

        static Cache a(File file) throws IOException {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
            try {
                Cache cache = new Cache(dataInputStream.readInt(), dataInputStream.readInt(), dataInputStream.readLong(), dataInputStream.readLong());
                dataInputStream.close();
                return cache;
            } catch (Throwable th) {
                try {
                    dataInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }

        void b(File file) throws IOException {
            file.delete();
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
            try {
                dataOutputStream.writeInt(this.f5756a);
                dataOutputStream.writeInt(this.f5757b);
                dataOutputStream.writeLong(this.f5758c);
                dataOutputStream.writeLong(this.f5759d);
                dataOutputStream.close();
            } catch (Throwable th) {
                try {
                    dataOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof Cache)) {
                return false;
            }
            Cache cache = (Cache) obj;
            return this.f5757b == cache.f5757b && this.f5758c == cache.f5758c && this.f5756a == cache.f5756a && this.f5759d == cache.f5759d;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.f5757b), Long.valueOf(this.f5758c), Integer.valueOf(this.f5756a), Long.valueOf(this.f5759d));
        }
    }

    public static class CompilationStatus {
        public static final int RESULT_CODE_COMPILED_WITH_PROFILE = 1;
        public static final int RESULT_CODE_COMPILED_WITH_PROFILE_NON_MATCHING = 3;
        public static final int RESULT_CODE_ERROR_CACHE_FILE_EXISTS_BUT_CANNOT_BE_READ = 131072;
        public static final int RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE = 196608;
        private static final int RESULT_CODE_ERROR_CODE_BIT_SHIFT = 16;
        public static final int RESULT_CODE_ERROR_PACKAGE_NAME_DOES_NOT_EXIST = 65536;
        public static final int RESULT_CODE_ERROR_UNSUPPORTED_API_VERSION = 262144;
        public static final int RESULT_CODE_NO_PROFILE = 0;
        public static final int RESULT_CODE_PROFILE_ENQUEUED_FOR_COMPILATION = 2;

        /* renamed from: a, reason: collision with root package name */
        final int f5760a;
        private final boolean mHasCurrentProfile;
        private final boolean mHasReferenceProfile;

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public @interface ResultCode {
        }

        CompilationStatus(int i2, boolean z2, boolean z3) {
            this.f5760a = i2;
            this.mHasCurrentProfile = z3;
            this.mHasReferenceProfile = z2;
        }

        public int getProfileInstallResultCode() {
            return this.f5760a;
        }

        public boolean hasProfileEnqueuedForCompilation() {
            return this.mHasCurrentProfile;
        }

        public boolean isCompiledWithProfile() {
            return this.mHasReferenceProfile;
        }
    }

    private ProfileVerifier() {
    }

    static CompilationStatus a(Context context, boolean z2) {
        Cache cacheA;
        int i2;
        CompilationStatus compilationStatus;
        if (!z2 && (compilationStatus = sCompilationStatus) != null) {
            return compilationStatus;
        }
        synchronized (SYNC_OBJ) {
            if (!z2) {
                try {
                    CompilationStatus compilationStatus2 = sCompilationStatus;
                    if (compilationStatus2 != null) {
                        return compilationStatus2;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            int i3 = Build.VERSION.SDK_INT;
            int i4 = 0;
            if (i3 >= 28 && i3 != 30) {
                File file = new File(new File(REF_PROFILES_BASE_DIR, context.getPackageName()), PROFILE_FILE_NAME);
                long length = file.length();
                boolean z3 = file.exists() && length > 0;
                File file2 = new File(new File(CUR_PROFILES_BASE_DIR, context.getPackageName()), PROFILE_FILE_NAME);
                long length2 = file2.length();
                boolean z4 = file2.exists() && length2 > 0;
                try {
                    long packageLastUpdateTime = getPackageLastUpdateTime(context);
                    File file3 = new File(context.getFilesDir(), PROFILE_INSTALLED_CACHE_FILE_NAME);
                    if (file3.exists()) {
                        try {
                            cacheA = Cache.a(file3);
                        } catch (IOException unused) {
                            return setCompilationStatus(131072, z3, z4);
                        }
                    } else {
                        cacheA = null;
                    }
                    if (cacheA != null && cacheA.f5758c == packageLastUpdateTime && (i2 = cacheA.f5757b) != 2) {
                        i4 = i2;
                    } else if (z3) {
                        i4 = 1;
                    } else if (z4) {
                        i4 = 2;
                    }
                    if (z2 && z4 && i4 != 1) {
                        i4 = 2;
                    }
                    if (cacheA != null && cacheA.f5757b == 2 && i4 == 1 && length < cacheA.f5759d) {
                        i4 = 3;
                    }
                    Cache cache = new Cache(1, i4, packageLastUpdateTime, length2);
                    if (cacheA == null || !cacheA.equals(cache)) {
                        try {
                            cache.b(file3);
                        } catch (IOException unused2) {
                            i4 = CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE;
                        }
                    }
                    return setCompilationStatus(i4, z3, z4);
                } catch (PackageManager.NameNotFoundException unused3) {
                    return setCompilationStatus(65536, z3, z4);
                }
            }
            return setCompilationStatus(262144, false, false);
        }
    }

    @NonNull
    public static ListenableFuture<CompilationStatus> getCompilationStatusAsync() {
        return sFuture;
    }

    private static long getPackageLastUpdateTime(Context context) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        return Build.VERSION.SDK_INT >= 33 ? Api33Impl.a(packageManager, context).lastUpdateTime : packageManager.getPackageInfo(context.getPackageName(), 0).lastUpdateTime;
    }

    private static CompilationStatus setCompilationStatus(int i2, boolean z2, boolean z3) {
        CompilationStatus compilationStatus = new CompilationStatus(i2, z2, z3);
        sCompilationStatus = compilationStatus;
        sFuture.set(compilationStatus);
        return sCompilationStatus;
    }

    @NonNull
    @WorkerThread
    public static CompilationStatus writeProfileVerification(@NonNull Context context) {
        return a(context, false);
    }
}
