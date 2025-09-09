package io.flutter.plugins.googlesignin;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;

/* loaded from: classes4.dex */
public class GoogleSignInWrapper {
    GoogleSignInClient getClient(Context context, GoogleSignInOptions googleSignInOptions) {
        return GoogleSignIn.getClient(context, googleSignInOptions);
    }

    GoogleSignInAccount getLastSignedInAccount(Context context) {
        return GoogleSignIn.getLastSignedInAccount(context);
    }

    boolean hasPermissions(GoogleSignInAccount googleSignInAccount, Scope scope) {
        return GoogleSignIn.hasPermissions(googleSignInAccount, scope);
    }

    void requestPermissions(Activity activity, int i2, GoogleSignInAccount googleSignInAccount, Scope[] scopeArr) {
        GoogleSignIn.requestPermissions(activity, i2, googleSignInAccount, scopeArr);
    }
}
