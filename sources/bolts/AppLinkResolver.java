package bolts;

import android.net.Uri;

/* loaded from: classes2.dex */
public interface AppLinkResolver {
    Task<AppLink> getAppLinkFromUrlInBackground(Uri uri);
}
