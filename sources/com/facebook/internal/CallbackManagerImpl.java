package com.facebook.internal;

import android.content.Intent;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public final class CallbackManagerImpl implements CallbackManager {
    private static final String TAG = "CallbackManagerImpl";
    private static Map<Integer, Callback> staticCallbacks = new HashMap();
    private Map<Integer, Callback> callbacks = new HashMap();

    public interface Callback {
        boolean onActivityResult(int i2, Intent intent);
    }

    public enum RequestCodeOffset {
        Login(0),
        Share(1),
        Message(2),
        Like(3),
        GameRequest(4),
        AppGroupCreate(5),
        AppGroupJoin(6),
        AppInvite(7),
        DeviceShare(8);

        private final int offset;

        RequestCodeOffset(int i2) {
            this.offset = i2;
        }

        public int toRequestCode() {
            return FacebookSdk.getCallbackRequestCodeOffset() + this.offset;
        }
    }

    private static synchronized Callback getStaticCallback(Integer num) {
        return staticCallbacks.get(num);
    }

    public static synchronized void registerStaticCallback(int i2, Callback callback) {
        Validate.notNull(callback, "callback");
        if (staticCallbacks.containsKey(Integer.valueOf(i2))) {
            return;
        }
        staticCallbacks.put(Integer.valueOf(i2), callback);
    }

    private static boolean runStaticCallback(int i2, int i3, Intent intent) {
        Callback staticCallback = getStaticCallback(Integer.valueOf(i2));
        if (staticCallback != null) {
            return staticCallback.onActivityResult(i3, intent);
        }
        return false;
    }

    @Override // com.facebook.CallbackManager
    public boolean onActivityResult(int i2, int i3, Intent intent) {
        Callback callback = this.callbacks.get(Integer.valueOf(i2));
        return callback != null ? callback.onActivityResult(i3, intent) : runStaticCallback(i2, i3, intent);
    }

    public void registerCallback(int i2, Callback callback) {
        Validate.notNull(callback, "callback");
        this.callbacks.put(Integer.valueOf(i2), callback);
    }

    public void unregisterCallback(int i2) {
        this.callbacks.remove(Integer.valueOf(i2));
    }
}
