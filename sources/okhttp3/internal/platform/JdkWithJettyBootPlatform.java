package okhttp3.internal.platform;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import javax.annotation.Nullable;
import javax.net.ssl.SSLSocket;
import okhttp3.Protocol;
import okhttp3.internal.Util;

/* loaded from: classes5.dex */
class JdkWithJettyBootPlatform extends Platform {
    private final Class<?> clientProviderClass;
    private final Method getMethod;
    private final Method putMethod;
    private final Method removeMethod;
    private final Class<?> serverProviderClass;

    private static class JettyNegoProvider implements InvocationHandler {

        /* renamed from: a, reason: collision with root package name */
        boolean f26430a;

        /* renamed from: b, reason: collision with root package name */
        String f26431b;
        private final List<String> protocols;

        JettyNegoProvider(List list) {
            this.protocols = list;
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            String name = method.getName();
            Class<?> returnType = method.getReturnType();
            if (objArr == null) {
                objArr = Util.EMPTY_STRING_ARRAY;
            }
            if (name.equals("supports") && Boolean.TYPE == returnType) {
                return Boolean.TRUE;
            }
            if (name.equals("unsupported") && Void.TYPE == returnType) {
                this.f26430a = true;
                return null;
            }
            if (name.equals("protocols") && objArr.length == 0) {
                return this.protocols;
            }
            if ((name.equals("selectProtocol") || name.equals("select")) && String.class == returnType && objArr.length == 1) {
                Object obj2 = objArr[0];
                if (obj2 instanceof List) {
                    List list = (List) obj2;
                    int size = list.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        if (this.protocols.contains(list.get(i2))) {
                            String str = (String) list.get(i2);
                            this.f26431b = str;
                            return str;
                        }
                    }
                    String str2 = this.protocols.get(0);
                    this.f26431b = str2;
                    return str2;
                }
            }
            if ((!name.equals("protocolSelected") && !name.equals("selected")) || objArr.length != 1) {
                return method.invoke(this, objArr);
            }
            this.f26431b = (String) objArr[0];
            return null;
        }
    }

    JdkWithJettyBootPlatform(Method method, Method method2, Method method3, Class cls, Class cls2) {
        this.putMethod = method;
        this.getMethod = method2;
        this.removeMethod = method3;
        this.clientProviderClass = cls;
        this.serverProviderClass = cls2;
    }

    public static Platform buildIfSupported() {
        try {
            Class<?> cls = Class.forName("org.eclipse.jetty.alpn.ALPN");
            Class<?> cls2 = Class.forName("org.eclipse.jetty.alpn.ALPN$Provider");
            return new JdkWithJettyBootPlatform(cls.getMethod("put", SSLSocket.class, cls2), cls.getMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, SSLSocket.class), cls.getMethod("remove", SSLSocket.class), Class.forName("org.eclipse.jetty.alpn.ALPN$ClientProvider"), Class.forName("org.eclipse.jetty.alpn.ALPN$ServerProvider"));
        } catch (ClassNotFoundException | NoSuchMethodException unused) {
            return null;
        }
    }

    @Override // okhttp3.internal.platform.Platform
    public void afterHandshake(SSLSocket sSLSocket) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            this.removeMethod.invoke(null, sSLSocket);
        } catch (IllegalAccessException | InvocationTargetException e2) {
            throw Util.assertionError("unable to remove alpn", e2);
        }
    }

    @Override // okhttp3.internal.platform.Platform
    public void configureTlsExtensions(SSLSocket sSLSocket, String str, List<Protocol> list) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            this.putMethod.invoke(null, sSLSocket, Proxy.newProxyInstance(Platform.class.getClassLoader(), new Class[]{this.clientProviderClass, this.serverProviderClass}, new JettyNegoProvider(Platform.alpnProtocolNames(list))));
        } catch (IllegalAccessException | InvocationTargetException e2) {
            throw Util.assertionError("unable to set alpn", e2);
        }
    }

    @Override // okhttp3.internal.platform.Platform
    @Nullable
    public String getSelectedProtocol(SSLSocket sSLSocket) {
        try {
            JettyNegoProvider jettyNegoProvider = (JettyNegoProvider) Proxy.getInvocationHandler(this.getMethod.invoke(null, sSLSocket));
            boolean z2 = jettyNegoProvider.f26430a;
            if (!z2 && jettyNegoProvider.f26431b == null) {
                Platform.get().log(4, "ALPN callback dropped: HTTP/2 is disabled. Is alpn-boot on the boot class path?", null);
                return null;
            }
            if (z2) {
                return null;
            }
            return jettyNegoProvider.f26431b;
        } catch (IllegalAccessException e2) {
            e = e2;
            throw Util.assertionError("unable to get selected protocol", e);
        } catch (InvocationTargetException e3) {
            e = e3;
            throw Util.assertionError("unable to get selected protocol", e);
        }
    }
}
