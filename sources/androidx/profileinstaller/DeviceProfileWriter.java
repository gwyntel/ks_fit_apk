package androidx.profileinstaller;

import android.content.res.AssetManager;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.profileinstaller.ProfileInstaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;

@RequiresApi(19)
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes2.dex */
public class DeviceProfileWriter {

    @NonNull
    private final String mApkName;

    @NonNull
    private final AssetManager mAssetManager;

    @NonNull
    private final File mCurProfile;

    @NonNull
    private final ProfileInstaller.DiagnosticsCallback mDiagnostics;

    @NonNull
    private final Executor mExecutor;

    @Nullable
    private DexProfileData[] mProfile;

    @NonNull
    private final String mProfileMetaSourceLocation;

    @NonNull
    private final String mProfileSourceLocation;

    @Nullable
    private byte[] mTranscodedProfile;
    private boolean mDeviceSupportsAotProfile = false;

    @Nullable
    private final byte[] mDesiredVersion = desiredVersion();

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public DeviceProfileWriter(@NonNull AssetManager assetManager, @NonNull Executor executor, @NonNull ProfileInstaller.DiagnosticsCallback diagnosticsCallback, @NonNull String str, @NonNull String str2, @NonNull String str3, @NonNull File file) {
        this.mAssetManager = assetManager;
        this.mExecutor = executor;
        this.mDiagnostics = diagnosticsCallback;
        this.mApkName = str;
        this.mProfileSourceLocation = str2;
        this.mProfileMetaSourceLocation = str3;
        this.mCurProfile = file;
    }

    @Nullable
    private DeviceProfileWriter addMetadata(DexProfileData[] dexProfileDataArr, byte[] bArr) throws IOException {
        InputStream inputStreamOpenStreamFromAssets;
        try {
            inputStreamOpenStreamFromAssets = openStreamFromAssets(this.mAssetManager, this.mProfileMetaSourceLocation);
        } catch (FileNotFoundException e2) {
            this.mDiagnostics.onResultReceived(9, e2);
        } catch (IOException e3) {
            this.mDiagnostics.onResultReceived(7, e3);
        } catch (IllegalStateException e4) {
            this.mProfile = null;
            this.mDiagnostics.onResultReceived(8, e4);
        }
        if (inputStreamOpenStreamFromAssets == null) {
            if (inputStreamOpenStreamFromAssets != null) {
                inputStreamOpenStreamFromAssets.close();
            }
            return null;
        }
        try {
            this.mProfile = ProfileTranscoder.b(inputStreamOpenStreamFromAssets, ProfileTranscoder.a(inputStreamOpenStreamFromAssets, ProfileTranscoder.f5755b), bArr, dexProfileDataArr);
            inputStreamOpenStreamFromAssets.close();
            return this;
        } catch (Throwable th) {
            try {
                inputStreamOpenStreamFromAssets.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private void assertDeviceAllowsProfileInstallerAotWritesCalled() {
        if (!this.mDeviceSupportsAotProfile) {
            throw new IllegalStateException("This device doesn't support aot. Did you call deviceSupportsAotProfile()?");
        }
    }

    @Nullable
    private static byte[] desiredVersion() {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 < 24 || i2 > 34) {
            return null;
        }
        switch (i2) {
            case 24:
            case 25:
                return ProfileVersion.f5765e;
            case 26:
                return ProfileVersion.f5764d;
            case 27:
                return ProfileVersion.f5763c;
            case 28:
            case 29:
            case 30:
                return ProfileVersion.f5762b;
            case 31:
            case 32:
            case 33:
            case 34:
                return ProfileVersion.f5761a;
            default:
                return null;
        }
    }

    @Nullable
    private InputStream getProfileInputStream(AssetManager assetManager) {
        try {
            return openStreamFromAssets(assetManager, this.mProfileSourceLocation);
        } catch (FileNotFoundException e2) {
            this.mDiagnostics.onResultReceived(6, e2);
            return null;
        } catch (IOException e3) {
            this.mDiagnostics.onResultReceived(7, e3);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$result$0(int i2, Object obj) {
        this.mDiagnostics.onResultReceived(i2, obj);
    }

    @Nullable
    private InputStream openStreamFromAssets(AssetManager assetManager, String str) throws IOException {
        try {
            return assetManager.openFd(str).createInputStream();
        } catch (FileNotFoundException e2) {
            String message = e2.getMessage();
            if (message != null && message.contains("compressed")) {
                this.mDiagnostics.onDiagnosticReceived(5, null);
            }
            return null;
        }
    }

    @Nullable
    private DexProfileData[] readProfileInternal(InputStream inputStream) throws IOException {
        try {
            try {
                try {
                    try {
                        DexProfileData[] dexProfileDataArrE = ProfileTranscoder.e(inputStream, ProfileTranscoder.a(inputStream, ProfileTranscoder.f5754a), this.mApkName);
                        try {
                            inputStream.close();
                            return dexProfileDataArrE;
                        } catch (IOException e2) {
                            this.mDiagnostics.onResultReceived(7, e2);
                            return dexProfileDataArrE;
                        }
                    } catch (IOException e3) {
                        this.mDiagnostics.onResultReceived(7, e3);
                        inputStream.close();
                        return null;
                    }
                } catch (IllegalStateException e4) {
                    this.mDiagnostics.onResultReceived(8, e4);
                    inputStream.close();
                    return null;
                }
            } catch (IOException e5) {
                this.mDiagnostics.onResultReceived(7, e5);
                return null;
            }
        } catch (Throwable th) {
            try {
                inputStream.close();
            } catch (IOException e6) {
                this.mDiagnostics.onResultReceived(7, e6);
            }
            throw th;
        }
    }

    private static boolean requiresMetadata() {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 < 24 || i2 > 34) {
            return false;
        }
        if (i2 != 24 && i2 != 25) {
            switch (i2) {
                case 31:
                case 32:
                case 33:
                case 34:
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    private void result(final int i2, @Nullable final Object obj) {
        this.mExecutor.execute(new Runnable() { // from class: androidx.profileinstaller.b
            @Override // java.lang.Runnable
            public final void run() {
                this.f5772a.lambda$result$0(i2, obj);
            }
        });
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public boolean deviceAllowsProfileInstallerAotWrites() throws IOException {
        if (this.mDesiredVersion == null) {
            result(3, Integer.valueOf(Build.VERSION.SDK_INT));
            return false;
        }
        if (!this.mCurProfile.exists()) {
            try {
                this.mCurProfile.createNewFile();
            } catch (IOException unused) {
                result(4, null);
                return false;
            }
        } else if (!this.mCurProfile.canWrite()) {
            result(4, null);
            return false;
        }
        this.mDeviceSupportsAotProfile = true;
        return true;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public DeviceProfileWriter read() {
        DeviceProfileWriter deviceProfileWriterAddMetadata;
        assertDeviceAllowsProfileInstallerAotWritesCalled();
        if (this.mDesiredVersion == null) {
            return this;
        }
        InputStream profileInputStream = getProfileInputStream(this.mAssetManager);
        if (profileInputStream != null) {
            this.mProfile = readProfileInternal(profileInputStream);
        }
        DexProfileData[] dexProfileDataArr = this.mProfile;
        return (dexProfileDataArr == null || !requiresMetadata() || (deviceProfileWriterAddMetadata = addMetadata(dexProfileDataArr, this.mDesiredVersion)) == null) ? this : deviceProfileWriterAddMetadata;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public DeviceProfileWriter transcodeIfNeeded() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream;
        DexProfileData[] dexProfileDataArr = this.mProfile;
        byte[] bArr = this.mDesiredVersion;
        if (dexProfileDataArr != null && bArr != null) {
            assertDeviceAllowsProfileInstallerAotWritesCalled();
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
            } catch (IOException e2) {
                this.mDiagnostics.onResultReceived(7, e2);
            } catch (IllegalStateException e3) {
                this.mDiagnostics.onResultReceived(8, e3);
            }
            try {
                ProfileTranscoder.g(byteArrayOutputStream, bArr);
                if (!ProfileTranscoder.f(byteArrayOutputStream, bArr, dexProfileDataArr)) {
                    this.mDiagnostics.onResultReceived(5, null);
                    this.mProfile = null;
                    byteArrayOutputStream.close();
                    return this;
                }
                this.mTranscodedProfile = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                this.mProfile = null;
            } catch (Throwable th) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        return this;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public boolean write() {
        byte[] bArr = this.mTranscodedProfile;
        if (bArr == null) {
            return false;
        }
        assertDeviceAllowsProfileInstallerAotWritesCalled();
        try {
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(this.mCurProfile);
                    try {
                        Encoding.l(byteArrayInputStream, fileOutputStream);
                        result(1, null);
                        fileOutputStream.close();
                        byteArrayInputStream.close();
                        return true;
                    } finally {
                    }
                } catch (Throwable th) {
                    try {
                        byteArrayInputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (FileNotFoundException e2) {
                result(6, e2);
                return false;
            } catch (IOException e3) {
                result(7, e3);
                return false;
            }
        } finally {
            this.mTranscodedProfile = null;
            this.mProfile = null;
        }
    }
}
