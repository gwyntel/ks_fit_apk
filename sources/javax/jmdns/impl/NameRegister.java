package javax.jmdns.impl;

import java.net.InetAddress;

/* loaded from: classes4.dex */
public interface NameRegister {

    public static class Factory {
        private static volatile NameRegister _register;

        public static NameRegister getRegistry() {
            if (_register == null) {
                _register = new UniqueNamePerInterface();
            }
            return _register;
        }

        public static void setRegistry(NameRegister nameRegister) throws IllegalStateException {
            if (_register != null) {
                throw new IllegalStateException("The register can only be set once.");
            }
            if (nameRegister != null) {
                _register = nameRegister;
            }
        }
    }

    public enum NameType {
        HOST,
        SERVICE
    }

    public static class UniqueNameAcrossInterface implements NameRegister {
        @Override // javax.jmdns.impl.NameRegister
        public boolean checkName(InetAddress inetAddress, String str, NameType nameType) {
            return false;
        }

        @Override // javax.jmdns.impl.NameRegister
        public String incrementHostName(InetAddress inetAddress, String str, NameType nameType) {
            return null;
        }

        @Override // javax.jmdns.impl.NameRegister
        public void register(InetAddress inetAddress, String str, NameType nameType) {
        }
    }

    public static class UniqueNamePerInterface implements NameRegister {
        @Override // javax.jmdns.impl.NameRegister
        public boolean checkName(InetAddress inetAddress, String str, NameType nameType) {
            return false;
        }

        @Override // javax.jmdns.impl.NameRegister
        public String incrementHostName(InetAddress inetAddress, String str, NameType nameType) {
            return null;
        }

        @Override // javax.jmdns.impl.NameRegister
        public void register(InetAddress inetAddress, String str, NameType nameType) {
        }
    }

    boolean checkName(InetAddress inetAddress, String str, NameType nameType);

    String incrementHostName(InetAddress inetAddress, String str, NameType nameType);

    void register(InetAddress inetAddress, String str, NameType nameType);
}
