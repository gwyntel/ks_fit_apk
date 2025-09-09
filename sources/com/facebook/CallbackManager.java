package com.facebook;

import android.content.Intent;
import com.facebook.internal.CallbackManagerImpl;

/* loaded from: classes3.dex */
public interface CallbackManager {

    public static class Factory {
        public static CallbackManager create() {
            return new CallbackManagerImpl();
        }
    }

    boolean onActivityResult(int i2, int i3, Intent intent);
}
