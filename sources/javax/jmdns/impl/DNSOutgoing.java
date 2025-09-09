package javax.jmdns.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.jmdns.impl.constants.DNSConstants;

/* loaded from: classes4.dex */
public final class DNSOutgoing extends DNSMessage {
    private static final int HEADER_SIZE = 12;
    public static boolean USE_DOMAIN_NAME_COMPRESSION = true;
    private final MessageOutputStream _additionalsAnswersBytes;
    private final MessageOutputStream _answersBytes;
    private final MessageOutputStream _authoritativeAnswersBytes;
    private int _maxUDPPayload;
    private final MessageOutputStream _questionsBytes;

    /* renamed from: f, reason: collision with root package name */
    Map f25329f;

    public static class MessageOutputStream extends ByteArrayOutputStream {
        private final int _offset;
        private final DNSOutgoing _out;

        MessageOutputStream(int i2, DNSOutgoing dNSOutgoing) {
            this(i2, dNSOutgoing, 0);
        }

        void a(int i2) {
            write(i2 & 255);
        }

        void b(byte[] bArr, int i2, int i3) {
            for (int i4 = 0; i4 < i3; i4++) {
                a(bArr[i2 + i4]);
            }
        }

        void c(int i2) {
            h(i2 >> 16);
            h(i2);
        }

        void d(String str) {
            e(str, true);
        }

        void e(String str, boolean z2) {
            while (true) {
                int iIndexOf = str.indexOf(46);
                if (iIndexOf < 0) {
                    iIndexOf = str.length();
                }
                if (iIndexOf <= 0) {
                    a(0);
                    return;
                }
                String strSubstring = str.substring(0, iIndexOf);
                if (z2 && DNSOutgoing.USE_DOMAIN_NAME_COMPRESSION) {
                    Integer num = (Integer) this._out.f25329f.get(str);
                    if (num != null) {
                        int iIntValue = num.intValue();
                        a((iIntValue >> 8) | 192);
                        a(iIntValue & 255);
                        return;
                    }
                    this._out.f25329f.put(str, Integer.valueOf(size() + this._offset));
                    i(strSubstring, 0, strSubstring.length());
                } else {
                    i(strSubstring, 0, strSubstring.length());
                }
                str = str.substring(iIndexOf);
                if (str.startsWith(".")) {
                    str = str.substring(1);
                }
            }
        }

        void f(DNSQuestion dNSQuestion) {
            d(dNSQuestion.getName());
            h(dNSQuestion.getRecordType().indexValue());
            h(dNSQuestion.getRecordClass().indexValue());
        }

        void g(DNSRecord dNSRecord, long j2) throws IOException {
            d(dNSRecord.getName());
            h(dNSRecord.getRecordType().indexValue());
            h(dNSRecord.getRecordClass().indexValue() | ((dNSRecord.isUnique() && this._out.isMulticast()) ? 32768 : 0));
            c(j2 == 0 ? dNSRecord.getTTL() : dNSRecord.d(j2));
            MessageOutputStream messageOutputStream = new MessageOutputStream(512, this._out, this._offset + size() + 2);
            dNSRecord.m(messageOutputStream);
            byte[] byteArray = messageOutputStream.toByteArray();
            h(byteArray.length);
            write(byteArray, 0, byteArray.length);
        }

        void h(int i2) {
            a(i2 >> 8);
            a(i2);
        }

        void i(String str, int i2, int i3) {
            int i4 = 0;
            for (int i5 = 0; i5 < i3; i5++) {
                char cCharAt = str.charAt(i2 + i5);
                i4 = (cCharAt < 1 || cCharAt > 127) ? cCharAt > 2047 ? i4 + 3 : i4 + 2 : i4 + 1;
            }
            a(i4);
            for (int i6 = 0; i6 < i3; i6++) {
                char cCharAt2 = str.charAt(i2 + i6);
                if (cCharAt2 >= 1 && cCharAt2 <= 127) {
                    a(cCharAt2);
                } else if (cCharAt2 > 2047) {
                    a(((cCharAt2 >> '\f') & 15) | 224);
                    a(((cCharAt2 >> 6) & 63) | 128);
                    a((cCharAt2 & '?') | 128);
                } else {
                    a(((cCharAt2 >> 6) & 31) | 192);
                    a((cCharAt2 & '?') | 128);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // java.io.ByteArrayOutputStream
        public void writeBytes(byte[] bArr) {
            if (bArr != null) {
                b(bArr, 0, bArr.length);
            }
        }

        MessageOutputStream(int i2, DNSOutgoing dNSOutgoing, int i3) {
            super(i2);
            this._out = dNSOutgoing;
            this._offset = i3;
        }
    }

    public DNSOutgoing(int i2) {
        this(i2, true, DNSConstants.MAX_MSG_TYPICAL);
    }

    public void addAdditionalAnswer(DNSIncoming dNSIncoming, DNSRecord dNSRecord) throws IOException {
        MessageOutputStream messageOutputStream = new MessageOutputStream(512, this);
        messageOutputStream.g(dNSRecord, 0L);
        byte[] byteArray = messageOutputStream.toByteArray();
        if (byteArray.length >= availableSpace()) {
            throw new IOException("message full");
        }
        this.f25328e.add(dNSRecord);
        this._additionalsAnswersBytes.write(byteArray, 0, byteArray.length);
    }

    public void addAnswer(DNSIncoming dNSIncoming, DNSRecord dNSRecord) throws IOException {
        if (dNSIncoming == null || !dNSRecord.k(dNSIncoming)) {
            addAnswer(dNSRecord, 0L);
        }
    }

    public void addAuthorativeAnswer(DNSRecord dNSRecord) throws IOException {
        MessageOutputStream messageOutputStream = new MessageOutputStream(512, this);
        messageOutputStream.g(dNSRecord, 0L);
        byte[] byteArray = messageOutputStream.toByteArray();
        if (byteArray.length >= availableSpace()) {
            throw new IOException("message full");
        }
        this.f25327d.add(dNSRecord);
        this._authoritativeAnswersBytes.write(byteArray, 0, byteArray.length);
    }

    public void addQuestion(DNSQuestion dNSQuestion) throws IOException {
        MessageOutputStream messageOutputStream = new MessageOutputStream(512, this);
        messageOutputStream.f(dNSQuestion);
        byte[] byteArray = messageOutputStream.toByteArray();
        if (byteArray.length >= availableSpace()) {
            throw new IOException("message full");
        }
        this.f25325b.add(dNSQuestion);
        this._questionsBytes.write(byteArray, 0, byteArray.length);
    }

    public int availableSpace() {
        return ((((this._maxUDPPayload - 12) - this._questionsBytes.size()) - this._answersBytes.size()) - this._authoritativeAnswersBytes.size()) - this._additionalsAnswersBytes.size();
    }

    public byte[] data() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.f25329f.clear();
        MessageOutputStream messageOutputStream = new MessageOutputStream(this._maxUDPPayload, this);
        messageOutputStream.h(this.f25324a ? 0 : getId());
        messageOutputStream.h(getFlags());
        messageOutputStream.h(getNumberOfQuestions());
        messageOutputStream.h(getNumberOfAnswers());
        messageOutputStream.h(getNumberOfAuthorities());
        messageOutputStream.h(getNumberOfAdditionals());
        Iterator it = this.f25325b.iterator();
        while (it.hasNext()) {
            messageOutputStream.f((DNSQuestion) it.next());
        }
        Iterator it2 = this.f25326c.iterator();
        while (it2.hasNext()) {
            messageOutputStream.g((DNSRecord) it2.next(), jCurrentTimeMillis);
        }
        Iterator it3 = this.f25327d.iterator();
        while (it3.hasNext()) {
            messageOutputStream.g((DNSRecord) it3.next(), jCurrentTimeMillis);
        }
        Iterator it4 = this.f25328e.iterator();
        while (it4.hasNext()) {
            messageOutputStream.g((DNSRecord) it4.next(), jCurrentTimeMillis);
        }
        return messageOutputStream.toByteArray();
    }

    public int getMaxUDPPayload() {
        return this._maxUDPPayload;
    }

    @Override // javax.jmdns.impl.DNSMessage
    public boolean isQuery() {
        return (getFlags() & 32768) == 0;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(isQuery() ? "dns[query:" : "dns[response:");
        stringBuffer.append(" id=0x");
        stringBuffer.append(Integer.toHexString(getId()));
        if (getFlags() != 0) {
            stringBuffer.append(", flags=0x");
            stringBuffer.append(Integer.toHexString(getFlags()));
            if ((getFlags() & 32768) != 0) {
                stringBuffer.append(":r");
            }
            if ((getFlags() & 1024) != 0) {
                stringBuffer.append(":aa");
            }
            if ((getFlags() & 512) != 0) {
                stringBuffer.append(":tc");
            }
        }
        if (getNumberOfQuestions() > 0) {
            stringBuffer.append(", questions=");
            stringBuffer.append(getNumberOfQuestions());
        }
        if (getNumberOfAnswers() > 0) {
            stringBuffer.append(", answers=");
            stringBuffer.append(getNumberOfAnswers());
        }
        if (getNumberOfAuthorities() > 0) {
            stringBuffer.append(", authorities=");
            stringBuffer.append(getNumberOfAuthorities());
        }
        if (getNumberOfAdditionals() > 0) {
            stringBuffer.append(", additionals=");
            stringBuffer.append(getNumberOfAdditionals());
        }
        if (getNumberOfQuestions() > 0) {
            stringBuffer.append("\nquestions:");
            for (DNSQuestion dNSQuestion : this.f25325b) {
                stringBuffer.append("\n\t");
                stringBuffer.append(dNSQuestion);
            }
        }
        if (getNumberOfAnswers() > 0) {
            stringBuffer.append("\nanswers:");
            for (DNSRecord dNSRecord : this.f25326c) {
                stringBuffer.append("\n\t");
                stringBuffer.append(dNSRecord);
            }
        }
        if (getNumberOfAuthorities() > 0) {
            stringBuffer.append("\nauthorities:");
            for (DNSRecord dNSRecord2 : this.f25327d) {
                stringBuffer.append("\n\t");
                stringBuffer.append(dNSRecord2);
            }
        }
        if (getNumberOfAdditionals() > 0) {
            stringBuffer.append("\nadditionals:");
            for (DNSRecord dNSRecord3 : this.f25328e) {
                stringBuffer.append("\n\t");
                stringBuffer.append(dNSRecord3);
            }
        }
        stringBuffer.append("\nnames=");
        stringBuffer.append(this.f25329f);
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    public DNSOutgoing(int i2, boolean z2) {
        this(i2, z2, DNSConstants.MAX_MSG_TYPICAL);
    }

    public DNSOutgoing(int i2, boolean z2, int i3) {
        super(i2, 0, z2);
        this.f25329f = new HashMap();
        this._maxUDPPayload = i3 > 0 ? i3 : DNSConstants.MAX_MSG_TYPICAL;
        this._questionsBytes = new MessageOutputStream(i3, this);
        this._answersBytes = new MessageOutputStream(i3, this);
        this._authoritativeAnswersBytes = new MessageOutputStream(i3, this);
        this._additionalsAnswersBytes = new MessageOutputStream(i3, this);
    }

    public void addAnswer(DNSRecord dNSRecord, long j2) throws IOException {
        if (dNSRecord != null) {
            if (j2 == 0 || !dNSRecord.isExpired(j2)) {
                MessageOutputStream messageOutputStream = new MessageOutputStream(512, this);
                messageOutputStream.g(dNSRecord, j2);
                byte[] byteArray = messageOutputStream.toByteArray();
                if (byteArray.length < availableSpace()) {
                    this.f25326c.add(dNSRecord);
                    this._answersBytes.write(byteArray, 0, byteArray.length);
                    return;
                }
                throw new IOException("message full");
            }
        }
    }
}
