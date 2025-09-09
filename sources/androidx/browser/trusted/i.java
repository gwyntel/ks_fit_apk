package androidx.browser.trusted;

import android.os.Bundle;
import androidx.browser.trusted.TrustedWebActivityDisplayMode;

/* loaded from: classes.dex */
public abstract /* synthetic */ class i {
    public static TrustedWebActivityDisplayMode a(Bundle bundle) {
        return bundle.getInt(TrustedWebActivityDisplayMode.KEY_ID) != 1 ? new TrustedWebActivityDisplayMode.DefaultMode() : TrustedWebActivityDisplayMode.ImmersiveMode.a(bundle);
    }
}
