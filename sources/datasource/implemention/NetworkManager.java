package datasource.implemention;

import com.alibaba.ailabs.tg.network.GeniusNetwork;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class NetworkManager {

    /* renamed from: a, reason: collision with root package name */
    public static HashMap<Class, Object> f24967a = new HashMap<>();

    public static <T> T getService(Class<T> cls) {
        Object objCreate = f24967a.get(cls);
        if (objCreate == null) {
            objCreate = new GeniusNetwork.Builder().build().create(cls);
            f24967a.put(cls, objCreate);
        }
        return cls.cast(objCreate);
    }
}
