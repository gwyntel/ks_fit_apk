package javax.jmdns.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.impl.constants.DNSConstants;

/* loaded from: classes4.dex */
class SocketListener extends Thread {

    /* renamed from: a, reason: collision with root package name */
    static Logger f25384a = Logger.getLogger(SocketListener.class.getName());
    private final JmDNSImpl _jmDNSImpl;

    SocketListener(JmDNSImpl jmDNSImpl) {
        StringBuilder sb = new StringBuilder();
        sb.append("SocketListener(");
        sb.append(jmDNSImpl != null ? jmDNSImpl.getName() : "");
        sb.append(")");
        super(sb.toString());
        setDaemon(true);
        this._jmDNSImpl = jmDNSImpl;
    }

    public JmDNSImpl getDns() {
        return this._jmDNSImpl;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() throws IOException {
        try {
            DatagramPacket datagramPacket = new DatagramPacket(new byte[DNSConstants.MAX_MSG_ABSOLUTE], DNSConstants.MAX_MSG_ABSOLUTE);
            while (!this._jmDNSImpl.isCanceling() && !this._jmDNSImpl.isCanceled()) {
                datagramPacket.setLength(DNSConstants.MAX_MSG_ABSOLUTE);
                this._jmDNSImpl.getSocket().receive(datagramPacket);
                if (this._jmDNSImpl.isCanceling() || this._jmDNSImpl.isCanceled() || this._jmDNSImpl.isClosing() || this._jmDNSImpl.isClosed()) {
                    break;
                }
                try {
                    if (!this._jmDNSImpl.getLocalHost().f(datagramPacket)) {
                        DNSIncoming dNSIncoming = new DNSIncoming(datagramPacket);
                        if (f25384a.isLoggable(Level.FINEST)) {
                            f25384a.finest(getName() + ".run() JmDNS in:" + dNSIncoming.d(true));
                        }
                        if (dNSIncoming.isQuery()) {
                            int port = datagramPacket.getPort();
                            int i2 = DNSConstants.MDNS_PORT;
                            if (port != i2) {
                                this._jmDNSImpl.g(dNSIncoming, datagramPacket.getAddress(), datagramPacket.getPort());
                            }
                            JmDNSImpl jmDNSImpl = this._jmDNSImpl;
                            jmDNSImpl.g(dNSIncoming, jmDNSImpl.getGroup(), i2);
                        } else {
                            this._jmDNSImpl.i(dNSIncoming);
                        }
                    }
                } catch (IOException e2) {
                    f25384a.log(Level.WARNING, getName() + ".run() exception ", (Throwable) e2);
                }
            }
        } catch (IOException e3) {
            if (!this._jmDNSImpl.isCanceling() && !this._jmDNSImpl.isCanceled() && !this._jmDNSImpl.isClosing() && !this._jmDNSImpl.isClosed()) {
                f25384a.log(Level.WARNING, getName() + ".run() exception ", (Throwable) e3);
                this._jmDNSImpl.recover();
            }
        }
        if (f25384a.isLoggable(Level.FINEST)) {
            f25384a.finest(getName() + ".run() exiting.");
        }
    }
}
