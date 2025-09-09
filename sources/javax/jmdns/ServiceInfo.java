package javax.jmdns;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.Enumeration;
import java.util.Map;
import javax.jmdns.impl.ServiceInfoImpl;

/* loaded from: classes4.dex */
public abstract class ServiceInfo implements Cloneable {
    public static final byte[] NO_VALUE = new byte[0];

    public enum Fields {
        Domain,
        Protocol,
        Application,
        Instance,
        Subtype
    }

    public static ServiceInfo create(String str, String str2, int i2, String str3) {
        return new ServiceInfoImpl(str, str2, "", i2, 0, 0, false, str3);
    }

    @Deprecated
    public abstract InetAddress getAddress();

    public abstract String getApplication();

    public abstract String getDomain();

    @Deprecated
    public abstract String getHostAddress();

    public abstract String[] getHostAddresses();

    @Deprecated
    public abstract Inet4Address getInet4Address();

    public abstract Inet4Address[] getInet4Addresses();

    @Deprecated
    public abstract Inet6Address getInet6Address();

    public abstract Inet6Address[] getInet6Addresses();

    @Deprecated
    public abstract InetAddress getInetAddress();

    public abstract InetAddress[] getInetAddresses();

    public abstract String getKey();

    public abstract String getName();

    public abstract String getNiceTextString();

    public abstract int getPort();

    public abstract int getPriority();

    public abstract byte[] getPropertyBytes(String str);

    public abstract Enumeration<String> getPropertyNames();

    public abstract String getPropertyString(String str);

    public abstract String getProtocol();

    public abstract String getQualifiedName();

    public abstract Map<Fields, String> getQualifiedNameMap();

    public abstract String getServer();

    public abstract String getSubtype();

    public abstract byte[] getTextBytes();

    @Deprecated
    public abstract String getTextString();

    public abstract String getType();

    public abstract String getTypeWithSubtype();

    @Deprecated
    public abstract String getURL();

    @Deprecated
    public abstract String getURL(String str);

    public abstract String[] getURLs();

    public abstract String[] getURLs(String str);

    public abstract int getWeight();

    public abstract boolean hasData();

    public abstract boolean isPersistent();

    public abstract void setText(Map<String, ?> map) throws IllegalStateException;

    public abstract void setText(byte[] bArr) throws IllegalStateException;

    public static ServiceInfo create(String str, String str2, String str3, int i2, String str4) {
        return new ServiceInfoImpl(str, str2, str3, i2, 0, 0, false, str4);
    }

    @Override // 
    public ServiceInfo clone() {
        try {
            return (ServiceInfo) super.clone();
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    public static ServiceInfo create(String str, String str2, int i2, int i3, int i4, String str3) {
        return new ServiceInfoImpl(str, str2, "", i2, i3, i4, false, str3);
    }

    public static ServiceInfo create(String str, String str2, String str3, int i2, int i3, int i4, String str4) {
        return new ServiceInfoImpl(str, str2, str3, i2, i3, i4, false, str4);
    }

    public static ServiceInfo create(String str, String str2, int i2, int i3, int i4, Map<String, ?> map) {
        return new ServiceInfoImpl(str, str2, "", i2, i3, i4, false, map);
    }

    public static ServiceInfo create(String str, String str2, String str3, int i2, int i3, int i4, Map<String, ?> map) {
        return new ServiceInfoImpl(str, str2, str3, i2, i3, i4, false, map);
    }

    public static ServiceInfo create(String str, String str2, int i2, int i3, int i4, byte[] bArr) {
        return new ServiceInfoImpl(str, str2, "", i2, i3, i4, false, bArr);
    }

    public static ServiceInfo create(String str, String str2, String str3, int i2, int i3, int i4, byte[] bArr) {
        return new ServiceInfoImpl(str, str2, str3, i2, i3, i4, false, bArr);
    }

    public static ServiceInfo create(String str, String str2, int i2, int i3, int i4, boolean z2, String str3) {
        return new ServiceInfoImpl(str, str2, "", i2, i3, i4, z2, str3);
    }

    public static ServiceInfo create(String str, String str2, String str3, int i2, int i3, int i4, boolean z2, String str4) {
        return new ServiceInfoImpl(str, str2, str3, i2, i3, i4, z2, str4);
    }

    public static ServiceInfo create(String str, String str2, int i2, int i3, int i4, boolean z2, Map<String, ?> map) {
        return new ServiceInfoImpl(str, str2, "", i2, i3, i4, z2, map);
    }

    public static ServiceInfo create(String str, String str2, String str3, int i2, int i3, int i4, boolean z2, Map<String, ?> map) {
        return new ServiceInfoImpl(str, str2, str3, i2, i3, i4, z2, map);
    }

    public static ServiceInfo create(String str, String str2, int i2, int i3, int i4, boolean z2, byte[] bArr) {
        return new ServiceInfoImpl(str, str2, "", i2, i3, i4, z2, bArr);
    }

    public static ServiceInfo create(String str, String str2, String str3, int i2, int i3, int i4, boolean z2, byte[] bArr) {
        return new ServiceInfoImpl(str, str2, str3, i2, i3, i4, z2, bArr);
    }

    public static ServiceInfo create(Map<Fields, String> map, int i2, int i3, int i4, boolean z2, Map<String, ?> map2) {
        return new ServiceInfoImpl(map, i2, i3, i4, z2, map2);
    }
}
