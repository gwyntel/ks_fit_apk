package androidx.browser.trusted;

import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.Build;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.media3.common.C;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
class PackageIdentityUtils {
    private static final String TAG = "PackageIdentity";

    @RequiresApi(28)
    static class Api28Implementation implements SignaturesCompat {
        Api28Implementation() {
        }

        @Override // androidx.browser.trusted.PackageIdentityUtils.SignaturesCompat
        @Nullable
        public List<byte[]> getFingerprintsForPackage(String str, PackageManager packageManager) throws PackageManager.NameNotFoundException {
            PackageInfo packageInfo = packageManager.getPackageInfo(str, C.BUFFER_FLAG_FIRST_SAMPLE);
            ArrayList arrayList = new ArrayList();
            SigningInfo signingInfo = packageInfo.signingInfo;
            if (signingInfo.hasMultipleSigners()) {
                for (Signature signature : signingInfo.getApkContentsSigners()) {
                    arrayList.add(PackageIdentityUtils.a(signature));
                }
            } else {
                arrayList.add(PackageIdentityUtils.a(signingInfo.getSigningCertificateHistory()[0]));
            }
            return arrayList;
        }

        @Override // androidx.browser.trusted.PackageIdentityUtils.SignaturesCompat
        public boolean packageMatchesToken(String str, PackageManager packageManager, TokenContents tokenContents) throws PackageManager.NameNotFoundException, IOException {
            List<byte[]> fingerprintsForPackage;
            if (tokenContents.getPackageName().equals(str) && (fingerprintsForPackage = getFingerprintsForPackage(str, packageManager)) != null) {
                return fingerprintsForPackage.size() == 1 ? packageManager.hasSigningCertificate(str, tokenContents.getFingerprint(0), 1) : tokenContents.equals(TokenContents.b(str, fingerprintsForPackage));
            }
            return false;
        }
    }

    static class Pre28Implementation implements SignaturesCompat {
        Pre28Implementation() {
        }

        @Override // androidx.browser.trusted.PackageIdentityUtils.SignaturesCompat
        @Nullable
        @SuppressLint({"PackageManagerGetSignatures"})
        public List<byte[]> getFingerprintsForPackage(String str, PackageManager packageManager) throws PackageManager.NameNotFoundException {
            PackageInfo packageInfo = packageManager.getPackageInfo(str, 64);
            ArrayList arrayList = new ArrayList(packageInfo.signatures.length);
            for (Signature signature : packageInfo.signatures) {
                byte[] bArrA = PackageIdentityUtils.a(signature);
                if (bArrA == null) {
                    return null;
                }
                arrayList.add(bArrA);
            }
            return arrayList;
        }

        @Override // androidx.browser.trusted.PackageIdentityUtils.SignaturesCompat
        public boolean packageMatchesToken(String str, PackageManager packageManager, TokenContents tokenContents) throws PackageManager.NameNotFoundException, IOException {
            List<byte[]> fingerprintsForPackage;
            if (str.equals(tokenContents.getPackageName()) && (fingerprintsForPackage = getFingerprintsForPackage(str, packageManager)) != null) {
                return tokenContents.equals(TokenContents.b(str, fingerprintsForPackage));
            }
            return false;
        }
    }

    interface SignaturesCompat {
        @Nullable
        List<byte[]> getFingerprintsForPackage(String str, PackageManager packageManager) throws PackageManager.NameNotFoundException;

        boolean packageMatchesToken(String str, PackageManager packageManager, TokenContents tokenContents) throws PackageManager.NameNotFoundException, IOException;
    }

    private PackageIdentityUtils() {
    }

    static byte[] a(Signature signature) {
        try {
            return MessageDigest.getInstance("SHA256").digest(signature.toByteArray());
        } catch (NoSuchAlgorithmException unused) {
            return null;
        }
    }

    static List b(String str, PackageManager packageManager) {
        try {
            return getImpl().getFingerprintsForPackage(str, packageManager);
        } catch (PackageManager.NameNotFoundException e2) {
            Log.e(TAG, "Could not get fingerprint for package.", e2);
            return null;
        }
    }

    static boolean c(String str, PackageManager packageManager, TokenContents tokenContents) {
        try {
            return getImpl().packageMatchesToken(str, packageManager, tokenContents);
        } catch (PackageManager.NameNotFoundException | IOException e2) {
            Log.e(TAG, "Could not check if package matches token.", e2);
            return false;
        }
    }

    private static SignaturesCompat getImpl() {
        return Build.VERSION.SDK_INT >= 28 ? new Api28Implementation() : new Pre28Implementation();
    }
}
