package androidx.media3.datasource;

import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;

@UnstableApi
/* loaded from: classes.dex */
public final class UdpDataSource extends BaseDataSource {
    public static final int DEFAULT_MAX_PACKET_SIZE = 2000;
    public static final int DEFAULT_SOCKET_TIMEOUT_MILLIS = 8000;
    public static final int UDP_PORT_UNSET = -1;

    @Nullable
    private InetAddress address;

    @Nullable
    private MulticastSocket multicastSocket;
    private boolean opened;
    private final DatagramPacket packet;
    private final byte[] packetBuffer;
    private int packetRemaining;

    @Nullable
    private DatagramSocket socket;
    private final int socketTimeoutMillis;

    @Nullable
    private Uri uri;

    public static final class UdpDataSourceException extends DataSourceException {
        public UdpDataSourceException(Throwable th, int i2) {
            super(th, i2);
        }
    }

    public UdpDataSource() {
        this(2000);
    }

    @Override // androidx.media3.datasource.DataSource
    public void close() throws IOException {
        this.uri = null;
        MulticastSocket multicastSocket = this.multicastSocket;
        if (multicastSocket != null) {
            try {
                multicastSocket.leaveGroup((InetAddress) Assertions.checkNotNull(this.address));
            } catch (IOException unused) {
            }
            this.multicastSocket = null;
        }
        DatagramSocket datagramSocket = this.socket;
        if (datagramSocket != null) {
            datagramSocket.close();
            this.socket = null;
        }
        this.address = null;
        this.packetRemaining = 0;
        if (this.opened) {
            this.opened = false;
            b();
        }
    }

    public int getLocalPort() {
        DatagramSocket datagramSocket = this.socket;
        if (datagramSocket == null) {
            return -1;
        }
        return datagramSocket.getLocalPort();
    }

    @Override // androidx.media3.datasource.DataSource
    @Nullable
    public Uri getUri() {
        return this.uri;
    }

    @Override // androidx.media3.datasource.DataSource
    public long open(DataSpec dataSpec) throws IOException {
        Uri uri = dataSpec.uri;
        this.uri = uri;
        String str = (String) Assertions.checkNotNull(uri.getHost());
        int port = this.uri.getPort();
        c(dataSpec);
        try {
            this.address = InetAddress.getByName(str);
            InetSocketAddress inetSocketAddress = new InetSocketAddress(this.address, port);
            if (this.address.isMulticastAddress()) {
                MulticastSocket multicastSocket = new MulticastSocket(inetSocketAddress);
                this.multicastSocket = multicastSocket;
                multicastSocket.joinGroup(this.address);
                this.socket = this.multicastSocket;
            } else {
                this.socket = new DatagramSocket(inetSocketAddress);
            }
            this.socket.setSoTimeout(this.socketTimeoutMillis);
            this.opened = true;
            d(dataSpec);
            return -1L;
        } catch (IOException e2) {
            throw new UdpDataSourceException(e2, 2001);
        } catch (SecurityException e3) {
            throw new UdpDataSourceException(e3, PlaybackException.ERROR_CODE_IO_NO_PERMISSION);
        }
    }

    @Override // androidx.media3.common.DataReader
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        if (i3 == 0) {
            return 0;
        }
        if (this.packetRemaining == 0) {
            try {
                ((DatagramSocket) Assertions.checkNotNull(this.socket)).receive(this.packet);
                int length = this.packet.getLength();
                this.packetRemaining = length;
                a(length);
            } catch (SocketTimeoutException e2) {
                throw new UdpDataSourceException(e2, 2002);
            } catch (IOException e3) {
                throw new UdpDataSourceException(e3, 2001);
            }
        }
        int length2 = this.packet.getLength();
        int i4 = this.packetRemaining;
        int iMin = Math.min(i4, i3);
        System.arraycopy(this.packetBuffer, length2 - i4, bArr, i2, iMin);
        this.packetRemaining -= iMin;
        return iMin;
    }

    public UdpDataSource(int i2) {
        this(i2, 8000);
    }

    public UdpDataSource(int i2, int i3) {
        super(true);
        this.socketTimeoutMillis = i3;
        byte[] bArr = new byte[i2];
        this.packetBuffer = bArr;
        this.packet = new DatagramPacket(bArr, 0, i2);
    }
}
