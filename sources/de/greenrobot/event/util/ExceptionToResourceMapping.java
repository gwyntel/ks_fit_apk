package de.greenrobot.event.util;

import android.util.Log;
import de.greenrobot.event.EventBus;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class ExceptionToResourceMapping {
    public final Map<Class<? extends Throwable>, Integer> throwableToMsgIdMap = new HashMap();

    protected Integer a(Throwable th) {
        Class<?> cls = th.getClass();
        Integer value = this.throwableToMsgIdMap.get(cls);
        if (value == null) {
            Class<? extends Throwable> cls2 = null;
            for (Map.Entry<Class<? extends Throwable>, Integer> entry : this.throwableToMsgIdMap.entrySet()) {
                Class<? extends Throwable> key = entry.getKey();
                if (key.isAssignableFrom(cls) && (cls2 == null || cls2.isAssignableFrom(key))) {
                    value = entry.getValue();
                    cls2 = key;
                }
            }
        }
        return value;
    }

    public ExceptionToResourceMapping addMapping(Class<? extends Throwable> cls, int i2) {
        this.throwableToMsgIdMap.put(cls, Integer.valueOf(i2));
        return this;
    }

    public Integer mapThrowable(Throwable th) {
        int i2 = 20;
        Throwable cause = th;
        do {
            Integer numA = a(cause);
            if (numA != null) {
                return numA;
            }
            cause = cause.getCause();
            i2--;
            if (i2 <= 0 || cause == th) {
                break;
            }
        } while (cause != null);
        Log.d(EventBus.TAG, "No specific message ressource ID found for " + th);
        return null;
    }
}
