package com.squareup.okhttp.internal;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.squareup.okhttp.Protocol;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.net.ssl.SSLSocket;
import okio.Buffer;

/* loaded from: classes4.dex */
public class Platform {
    private static final Platform PLATFORM = findPlatform();

    private static class Android extends Platform {
        private final OptionalMethod<Socket> getAlpnSelectedProtocol;
        private final OptionalMethod<Socket> setAlpnProtocols;
        private final OptionalMethod<Socket> setHostname;
        private final OptionalMethod<Socket> setUseSessionTickets;
        private final Method trafficStatsTagSocket;
        private final Method trafficStatsUntagSocket;

        public Android(OptionalMethod<Socket> optionalMethod, OptionalMethod<Socket> optionalMethod2, Method method, Method method2, OptionalMethod<Socket> optionalMethod3, OptionalMethod<Socket> optionalMethod4) {
            this.setUseSessionTickets = optionalMethod;
            this.setHostname = optionalMethod2;
            this.trafficStatsTagSocket = method;
            this.trafficStatsUntagSocket = method2;
            this.getAlpnSelectedProtocol = optionalMethod3;
            this.setAlpnProtocols = optionalMethod4;
        }

        @Override // com.squareup.okhttp.internal.Platform
        public void configureTlsExtensions(SSLSocket sSLSocket, String str, List<Protocol> list) {
            if (str != null) {
                this.setUseSessionTickets.invokeOptionalWithoutCheckedException(sSLSocket, Boolean.TRUE);
                this.setHostname.invokeOptionalWithoutCheckedException(sSLSocket, str);
            }
            OptionalMethod<Socket> optionalMethod = this.setAlpnProtocols;
            if (optionalMethod == null || !optionalMethod.isSupported(sSLSocket)) {
                return;
            }
            this.setAlpnProtocols.invokeWithoutCheckedException(sSLSocket, Platform.a(list));
        }

        @Override // com.squareup.okhttp.internal.Platform
        public void connectSocket(Socket socket, InetSocketAddress inetSocketAddress, int i2) throws IOException {
            try {
                socket.connect(inetSocketAddress, i2);
            } catch (SecurityException e2) {
                IOException iOException = new IOException("Exception in connect");
                iOException.initCause(e2);
                throw iOException;
            }
        }

        @Override // com.squareup.okhttp.internal.Platform
        public String getSelectedProtocol(SSLSocket sSLSocket) {
            byte[] bArr;
            OptionalMethod<Socket> optionalMethod = this.getAlpnSelectedProtocol;
            if (optionalMethod == null || !optionalMethod.isSupported(sSLSocket) || (bArr = (byte[]) this.getAlpnSelectedProtocol.invokeWithoutCheckedException(sSLSocket, new Object[0])) == null) {
                return null;
            }
            return new String(bArr, Util.UTF_8);
        }

        @Override // com.squareup.okhttp.internal.Platform
        public void tagSocket(Socket socket) throws IllegalAccessException, SocketException, IllegalArgumentException, InvocationTargetException {
            Method method = this.trafficStatsTagSocket;
            if (method == null) {
                return;
            }
            try {
                method.invoke(null, socket);
            } catch (IllegalAccessException e2) {
                throw new RuntimeException(e2);
            } catch (InvocationTargetException e3) {
                throw new RuntimeException(e3.getCause());
            }
        }

        @Override // com.squareup.okhttp.internal.Platform
        public void untagSocket(Socket socket) throws IllegalAccessException, SocketException, IllegalArgumentException, InvocationTargetException {
            Method method = this.trafficStatsUntagSocket;
            if (method == null) {
                return;
            }
            try {
                method.invoke(null, socket);
            } catch (IllegalAccessException e2) {
                throw new RuntimeException(e2);
            } catch (InvocationTargetException e3) {
                throw new RuntimeException(e3.getCause());
            }
        }
    }

    private static class JdkWithJettyBootPlatform extends Platform {
        private final Class<?> clientProviderClass;
        private final Method getMethod;
        private final Method putMethod;
        private final Method removeMethod;
        private final Class<?> serverProviderClass;

        public JdkWithJettyBootPlatform(Method method, Method method2, Method method3, Class<?> cls, Class<?> cls2) {
            this.putMethod = method;
            this.getMethod = method2;
            this.removeMethod = method3;
            this.clientProviderClass = cls;
            this.serverProviderClass = cls2;
        }

        @Override // com.squareup.okhttp.internal.Platform
        public void afterHandshake(SSLSocket sSLSocket) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            try {
                this.removeMethod.invoke(null, sSLSocket);
            } catch (IllegalAccessException | InvocationTargetException unused) {
                throw new AssertionError();
            }
        }

        @Override // com.squareup.okhttp.internal.Platform
        public void configureTlsExtensions(SSLSocket sSLSocket, String str, List<Protocol> list) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            ArrayList arrayList = new ArrayList(list.size());
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                Protocol protocol = list.get(i2);
                if (protocol != Protocol.HTTP_1_0) {
                    arrayList.add(protocol.toString());
                }
            }
            try {
                this.putMethod.invoke(null, sSLSocket, Proxy.newProxyInstance(Platform.class.getClassLoader(), new Class[]{this.clientProviderClass, this.serverProviderClass}, new JettyNegoProvider(arrayList)));
            } catch (IllegalAccessException | InvocationTargetException e2) {
                throw new AssertionError(e2);
            }
        }

        @Override // com.squareup.okhttp.internal.Platform
        public String getSelectedProtocol(SSLSocket sSLSocket) {
            try {
                JettyNegoProvider jettyNegoProvider = (JettyNegoProvider) Proxy.getInvocationHandler(this.getMethod.invoke(null, sSLSocket));
                if (!jettyNegoProvider.unsupported && jettyNegoProvider.selected == null) {
                    Internal.logger.log(Level.INFO, "ALPN callback dropped: SPDY and HTTP/2 are disabled. Is alpn-boot on the boot class path?");
                    return null;
                }
                if (jettyNegoProvider.unsupported) {
                    return null;
                }
                return jettyNegoProvider.selected;
            } catch (IllegalAccessException | InvocationTargetException unused) {
                throw new AssertionError();
            }
        }
    }

    private static class JettyNegoProvider implements InvocationHandler {
        private final List<String> protocols;
        private String selected;
        private boolean unsupported;

        public JettyNegoProvider(List<String> list) {
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
                this.unsupported = true;
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
                            this.selected = str;
                            return str;
                        }
                    }
                    String str2 = this.protocols.get(0);
                    this.selected = str2;
                    return str2;
                }
            }
            if ((!name.equals("protocolSelected") && !name.equals("selected")) || objArr.length != 1) {
                return method.invoke(this, objArr);
            }
            this.selected = (String) objArr[0];
            return null;
        }
    }

    static byte[] a(List list) {
        Buffer buffer = new Buffer();
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            Protocol protocol = (Protocol) list.get(i2);
            if (protocol != Protocol.HTTP_1_0) {
                buffer.writeByte(protocol.toString().length());
                buffer.writeUtf8(protocol.toString());
            }
        }
        return buffer.readByteArray();
    }

    private static Platform findPlatform() throws NoSuchMethodException, ClassNotFoundException, SecurityException {
        Method method;
        OptionalMethod optionalMethod;
        Method method2;
        try {
            try {
                try {
                    Class.forName("com.android.org.conscrypt.OpenSSLSocketImpl");
                } catch (ClassNotFoundException unused) {
                    Class.forName("org.apache.harmony.xnet.provider.jsse.OpenSSLSocketImpl");
                }
                OptionalMethod optionalMethod2 = null;
                OptionalMethod optionalMethod3 = new OptionalMethod(null, "setUseSessionTickets", Boolean.TYPE);
                OptionalMethod optionalMethod4 = new OptionalMethod(null, "setHostname", String.class);
                try {
                    Class<?> cls = Class.forName("android.net.TrafficStats");
                    method2 = cls.getMethod("tagSocket", Socket.class);
                    try {
                        method = cls.getMethod("untagSocket", Socket.class);
                        try {
                            Class.forName("android.net.Network");
                            optionalMethod = new OptionalMethod(byte[].class, "getAlpnSelectedProtocol", new Class[0]);
                            try {
                                optionalMethod2 = new OptionalMethod(null, "setAlpnProtocols", byte[].class);
                            } catch (ClassNotFoundException | NoSuchMethodException unused2) {
                            }
                        } catch (ClassNotFoundException | NoSuchMethodException unused3) {
                            optionalMethod = null;
                        }
                    } catch (ClassNotFoundException | NoSuchMethodException unused4) {
                        method = null;
                        optionalMethod = null;
                    }
                } catch (ClassNotFoundException | NoSuchMethodException unused5) {
                    method = null;
                    optionalMethod = null;
                    method2 = null;
                }
                return new Android(optionalMethod3, optionalMethod4, method2, method, optionalMethod, optionalMethod2);
            } catch (ClassNotFoundException | NoSuchMethodException unused6) {
                return new Platform();
            }
        } catch (ClassNotFoundException unused7) {
            Class<?> cls2 = Class.forName("org.eclipse.jetty.alpn.ALPN");
            Class<?> cls3 = Class.forName("org.eclipse.jetty.alpn.ALPN$Provider");
            return new JdkWithJettyBootPlatform(cls2.getMethod("put", SSLSocket.class, cls3), cls2.getMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, SSLSocket.class), cls2.getMethod("remove", SSLSocket.class), Class.forName("org.eclipse.jetty.alpn.ALPN$ClientProvider"), Class.forName("org.eclipse.jetty.alpn.ALPN$ServerProvider"));
        }
    }

    public static Platform get() {
        return PLATFORM;
    }

    public void afterHandshake(SSLSocket sSLSocket) {
    }

    public void configureTlsExtensions(SSLSocket sSLSocket, String str, List<Protocol> list) {
    }

    public void connectSocket(Socket socket, InetSocketAddress inetSocketAddress, int i2) throws IOException {
        socket.connect(inetSocketAddress, i2);
    }

    public String getPrefix() {
        return "OkHttp";
    }

    public String getSelectedProtocol(SSLSocket sSLSocket) {
        return null;
    }

    public void logW(String str) {
        System.out.println(str);
    }

    public void tagSocket(Socket socket) throws SocketException {
    }

    public void untagSocket(Socket socket) throws SocketException {
    }
}
