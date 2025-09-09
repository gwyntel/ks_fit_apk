package javax.jmdns.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.impl.constants.DNSConstants;
import javax.jmdns.impl.constants.DNSLabel;
import javax.jmdns.impl.constants.DNSOptionCode;
import javax.jmdns.impl.constants.DNSRecordClass;
import javax.jmdns.impl.constants.DNSRecordType;

/* loaded from: classes4.dex */
public final class DNSIncoming extends DNSMessage {
    private final MessageInputStream _messageInputStream;
    private final DatagramPacket _packet;
    private final long _receivedTime;
    private int _senderUDPPayload;
    private static Logger logger = Logger.getLogger(DNSIncoming.class.getName());
    public static boolean USE_DOMAIN_NAME_FORMAT_FOR_SRV_TARGET = true;
    private static final char[] _nibbleToHex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /* renamed from: javax.jmdns.impl.DNSIncoming$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f25320a;

        /* renamed from: b, reason: collision with root package name */
        static final /* synthetic */ int[] f25321b;

        /* renamed from: c, reason: collision with root package name */
        static final /* synthetic */ int[] f25322c;

        static {
            int[] iArr = new int[DNSRecordType.values().length];
            f25322c = iArr;
            try {
                iArr[DNSRecordType.TYPE_A.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f25322c[DNSRecordType.TYPE_AAAA.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f25322c[DNSRecordType.TYPE_CNAME.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f25322c[DNSRecordType.TYPE_PTR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f25322c[DNSRecordType.TYPE_TXT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f25322c[DNSRecordType.TYPE_SRV.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f25322c[DNSRecordType.TYPE_HINFO.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f25322c[DNSRecordType.TYPE_OPT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            int[] iArr2 = new int[DNSOptionCode.values().length];
            f25321b = iArr2;
            try {
                iArr2[DNSOptionCode.Owner.ordinal()] = 1;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f25321b[DNSOptionCode.LLQ.ordinal()] = 2;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f25321b[DNSOptionCode.NSID.ordinal()] = 3;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f25321b[DNSOptionCode.UL.ordinal()] = 4;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f25321b[DNSOptionCode.Unknown.ordinal()] = 5;
            } catch (NoSuchFieldError unused13) {
            }
            int[] iArr3 = new int[DNSLabel.values().length];
            f25320a = iArr3;
            try {
                iArr3[DNSLabel.Standard.ordinal()] = 1;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f25320a[DNSLabel.Compressed.ordinal()] = 2;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                f25320a[DNSLabel.Extended.ordinal()] = 3;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                f25320a[DNSLabel.Unknown.ordinal()] = 4;
            } catch (NoSuchFieldError unused17) {
            }
        }
    }

    public static class MessageInputStream extends ByteArrayInputStream {
        private static Logger logger1 = Logger.getLogger(MessageInputStream.class.getName());

        /* renamed from: a, reason: collision with root package name */
        final Map f25323a;

        public MessageInputStream(byte[] bArr, int i2) {
            this(bArr, 0, i2);
        }

        public int readByte() {
            return read();
        }

        public byte[] readBytes(int i2) throws IOException {
            byte[] bArr = new byte[i2];
            read(bArr, 0, i2);
            return bArr;
        }

        public int readInt() {
            return (readUnsignedShort() << 16) | readUnsignedShort();
        }

        public String readName() throws IOException {
            HashMap map = new HashMap();
            StringBuilder sb = new StringBuilder();
            boolean z2 = false;
            while (!z2) {
                int i2 = read();
                if (i2 == 0) {
                    break;
                }
                int i3 = AnonymousClass1.f25320a[DNSLabel.labelForByte(i2).ordinal()];
                if (i3 == 1) {
                    int i4 = ((ByteArrayInputStream) this).pos - 1;
                    String str = readUTF(i2) + ".";
                    sb.append(str);
                    Iterator it = map.values().iterator();
                    while (it.hasNext()) {
                        ((StringBuilder) it.next()).append(str);
                    }
                    map.put(Integer.valueOf(i4), new StringBuilder(str));
                } else if (i3 == 2) {
                    int iLabelValue = (DNSLabel.labelValue(i2) << 8) | read();
                    String str2 = (String) this.f25323a.get(Integer.valueOf(iLabelValue));
                    if (str2 == null) {
                        logger1.severe("bad domain name: possible circular name detected. Bad offset: 0x" + Integer.toHexString(iLabelValue) + " at 0x" + Integer.toHexString(((ByteArrayInputStream) this).pos - 2));
                        str2 = "";
                    }
                    sb.append(str2);
                    Iterator it2 = map.values().iterator();
                    while (it2.hasNext()) {
                        ((StringBuilder) it2.next()).append(str2);
                    }
                    z2 = true;
                } else if (i3 != 3) {
                    logger1.severe("unsupported dns label type: '" + Integer.toHexString(i2 & 192) + "'");
                } else {
                    logger1.severe("Extended label are not currently supported.");
                }
            }
            for (Integer num : map.keySet()) {
                this.f25323a.put(num, ((StringBuilder) map.get(num)).toString());
            }
            return sb.toString();
        }

        public String readNonNameString() {
            return readUTF(read());
        }

        public String readUTF(int i2) throws IOException {
            int i3;
            int i4;
            StringBuilder sb = new StringBuilder(i2);
            int i5 = 0;
            while (i5 < i2) {
                int i6 = read();
                switch (i6 >> 4) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        sb.append((char) i6);
                        i5++;
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    default:
                        i3 = (i6 & 63) << 4;
                        i4 = read() & 15;
                        break;
                    case 12:
                    case 13:
                        i3 = (i6 & 31) << 6;
                        i4 = read() & 63;
                        break;
                    case 14:
                        i6 = ((i6 & 15) << 12) | ((read() & 63) << 6) | (read() & 63);
                        i5 += 2;
                        continue;
                        sb.append((char) i6);
                        i5++;
                }
                i6 = i3 | i4;
                i5++;
                sb.append((char) i6);
                i5++;
            }
            return sb.toString();
        }

        public int readUnsignedShort() {
            return (read() << 8) | read();
        }

        public MessageInputStream(byte[] bArr, int i2, int i3) {
            super(bArr, i2, i3);
            this.f25323a = new HashMap();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DNSIncoming(DatagramPacket datagramPacket) throws IOException {
        super(0, 0, datagramPacket.getPort() == DNSConstants.MDNS_PORT);
        this._packet = datagramPacket;
        InetAddress address = datagramPacket.getAddress();
        MessageInputStream messageInputStream = new MessageInputStream(datagramPacket.getData(), datagramPacket.getLength());
        this._messageInputStream = messageInputStream;
        this._receivedTime = System.currentTimeMillis();
        this._senderUDPPayload = DNSConstants.MAX_MSG_TYPICAL;
        try {
            setId(messageInputStream.readUnsignedShort());
            setFlags(messageInputStream.readUnsignedShort());
            int unsignedShort = messageInputStream.readUnsignedShort();
            int unsignedShort2 = messageInputStream.readUnsignedShort();
            int unsignedShort3 = messageInputStream.readUnsignedShort();
            int unsignedShort4 = messageInputStream.readUnsignedShort();
            if (unsignedShort > 0) {
                for (int i2 = 0; i2 < unsignedShort; i2++) {
                    this.f25325b.add(readQuestion());
                }
            }
            if (unsignedShort2 > 0) {
                for (int i3 = 0; i3 < unsignedShort2; i3++) {
                    DNSRecord answer = readAnswer(address);
                    if (answer != null) {
                        this.f25326c.add(answer);
                    }
                }
            }
            if (unsignedShort3 > 0) {
                for (int i4 = 0; i4 < unsignedShort3; i4++) {
                    DNSRecord answer2 = readAnswer(address);
                    if (answer2 != null) {
                        this.f25327d.add(answer2);
                    }
                }
            }
            if (unsignedShort4 > 0) {
                for (int i5 = 0; i5 < unsignedShort4; i5++) {
                    DNSRecord answer3 = readAnswer(address);
                    if (answer3 != null) {
                        this.f25328e.add(answer3);
                    }
                }
            }
        } catch (Exception e2) {
            logger.log(Level.WARNING, "DNSIncoming() dump " + d(true) + "\n exception ", (Throwable) e2);
            IOException iOException = new IOException("DNSIncoming corrupted message");
            iOException.initCause(e2);
            throw iOException;
        }
    }

    private String _hexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b2 : bArr) {
            int i2 = b2 & 255;
            char[] cArr = _nibbleToHex;
            sb.append(cArr[i2 / 16]);
            sb.append(cArr[i2 % 16]);
        }
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:131:0x0245 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:133:0x00d4 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private javax.jmdns.impl.DNSRecord readAnswer(java.net.InetAddress r23) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 982
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: javax.jmdns.impl.DNSIncoming.readAnswer(java.net.InetAddress):javax.jmdns.impl.DNSRecord");
    }

    private DNSQuestion readQuestion() throws IOException {
        String name = this._messageInputStream.readName();
        DNSRecordType dNSRecordTypeTypeForIndex = DNSRecordType.typeForIndex(this._messageInputStream.readUnsignedShort());
        if (dNSRecordTypeTypeForIndex == DNSRecordType.TYPE_IGNORE) {
            logger.log(Level.SEVERE, "Could not find record type: " + d(true));
        }
        int unsignedShort = this._messageInputStream.readUnsignedShort();
        DNSRecordClass dNSRecordClassClassForIndex = DNSRecordClass.classForIndex(unsignedShort);
        return DNSQuestion.newQuestion(name, dNSRecordTypeTypeForIndex, dNSRecordClassClassForIndex, dNSRecordClassClassForIndex.isUnique(unsignedShort));
    }

    void c(DNSIncoming dNSIncoming) {
        if (!isQuery() || !isTruncated() || !dNSIncoming.isQuery()) {
            throw new IllegalArgumentException();
        }
        this.f25325b.addAll(dNSIncoming.getQuestions());
        this.f25326c.addAll(dNSIncoming.getAnswers());
        this.f25327d.addAll(dNSIncoming.getAuthorities());
        this.f25328e.addAll(dNSIncoming.getAdditionals());
    }

    String d(boolean z2) {
        StringBuilder sb = new StringBuilder();
        sb.append(a());
        if (z2) {
            int length = this._packet.getLength();
            byte[] bArr = new byte[length];
            System.arraycopy(this._packet.getData(), 0, bArr, 0, length);
            sb.append(b(bArr));
        }
        return sb.toString();
    }

    public int elapseSinceArrival() {
        return (int) (System.currentTimeMillis() - this._receivedTime);
    }

    public int getSenderUDPPayload() {
        return this._senderUDPPayload;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(isQuery() ? "dns[query," : "dns[response,");
        if (this._packet.getAddress() != null) {
            sb.append(this._packet.getAddress().getHostAddress());
        }
        sb.append(':');
        sb.append(this._packet.getPort());
        sb.append(", length=");
        sb.append(this._packet.getLength());
        sb.append(", id=0x");
        sb.append(Integer.toHexString(getId()));
        if (getFlags() != 0) {
            sb.append(", flags=0x");
            sb.append(Integer.toHexString(getFlags()));
            if ((getFlags() & 32768) != 0) {
                sb.append(":r");
            }
            if ((getFlags() & 1024) != 0) {
                sb.append(":aa");
            }
            if ((getFlags() & 512) != 0) {
                sb.append(":tc");
            }
        }
        if (getNumberOfQuestions() > 0) {
            sb.append(", questions=");
            sb.append(getNumberOfQuestions());
        }
        if (getNumberOfAnswers() > 0) {
            sb.append(", answers=");
            sb.append(getNumberOfAnswers());
        }
        if (getNumberOfAuthorities() > 0) {
            sb.append(", authorities=");
            sb.append(getNumberOfAuthorities());
        }
        if (getNumberOfAdditionals() > 0) {
            sb.append(", additionals=");
            sb.append(getNumberOfAdditionals());
        }
        if (getNumberOfQuestions() > 0) {
            sb.append("\nquestions:");
            for (DNSQuestion dNSQuestion : this.f25325b) {
                sb.append("\n\t");
                sb.append(dNSQuestion);
            }
        }
        if (getNumberOfAnswers() > 0) {
            sb.append("\nanswers:");
            for (DNSRecord dNSRecord : this.f25326c) {
                sb.append("\n\t");
                sb.append(dNSRecord);
            }
        }
        if (getNumberOfAuthorities() > 0) {
            sb.append("\nauthorities:");
            for (DNSRecord dNSRecord2 : this.f25327d) {
                sb.append("\n\t");
                sb.append(dNSRecord2);
            }
        }
        if (getNumberOfAdditionals() > 0) {
            sb.append("\nadditionals:");
            for (DNSRecord dNSRecord3 : this.f25328e) {
                sb.append("\n\t");
                sb.append(dNSRecord3);
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public DNSIncoming clone() {
        DNSIncoming dNSIncoming = new DNSIncoming(getFlags(), getId(), isMulticast(), this._packet, this._receivedTime);
        dNSIncoming._senderUDPPayload = this._senderUDPPayload;
        dNSIncoming.f25325b.addAll(this.f25325b);
        dNSIncoming.f25326c.addAll(this.f25326c);
        dNSIncoming.f25327d.addAll(this.f25327d);
        dNSIncoming.f25328e.addAll(this.f25328e);
        return dNSIncoming;
    }

    private DNSIncoming(int i2, int i3, boolean z2, DatagramPacket datagramPacket, long j2) {
        super(i2, i3, z2);
        this._packet = datagramPacket;
        this._messageInputStream = new MessageInputStream(datagramPacket.getData(), datagramPacket.getLength());
        this._receivedTime = j2;
    }
}
