package androidx.browser.trusted;

import android.content.pm.PackageManager;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.IOException;
import java.util.List;

/* loaded from: classes.dex */
public final class Token {
    private static final String TAG = "Token";

    @NonNull
    private final TokenContents mContents;

    private Token(@NonNull TokenContents tokenContents) {
        this.mContents = tokenContents;
    }

    @Nullable
    public static Token create(@NonNull String str, @NonNull PackageManager packageManager) {
        List listB = PackageIdentityUtils.b(str, packageManager);
        if (listB == null) {
            return null;
        }
        try {
            return new Token(TokenContents.b(str, listB));
        } catch (IOException e2) {
            Log.e(TAG, "Exception when creating token.", e2);
            return null;
        }
    }

    @NonNull
    public static Token deserialize(@NonNull byte[] bArr) {
        return new Token(TokenContents.c(bArr));
    }

    public boolean matches(@NonNull String str, @NonNull PackageManager packageManager) {
        return PackageIdentityUtils.c(str, packageManager, this.mContents);
    }

    @NonNull
    public byte[] serialize() {
        return this.mContents.serialize();
    }
}
