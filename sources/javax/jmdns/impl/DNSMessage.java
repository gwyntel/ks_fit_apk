package javax.jmdns.impl;

import com.alipay.sdk.app.OpenAuthTask;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class DNSMessage {
    public static final boolean MULTICAST = true;
    public static final boolean UNICAST = false;
    private int _flags;
    private int _id;

    /* renamed from: a, reason: collision with root package name */
    boolean f25324a;

    /* renamed from: b, reason: collision with root package name */
    protected final List f25325b = Collections.synchronizedList(new LinkedList());

    /* renamed from: c, reason: collision with root package name */
    protected final List f25326c = Collections.synchronizedList(new LinkedList());

    /* renamed from: d, reason: collision with root package name */
    protected final List f25327d = Collections.synchronizedList(new LinkedList());

    /* renamed from: e, reason: collision with root package name */
    protected final List f25328e = Collections.synchronizedList(new LinkedList());

    protected DNSMessage(int i2, int i3, boolean z2) {
        this._flags = i2;
        this._id = i3;
        this.f25324a = z2;
    }

    String a() {
        StringBuffer stringBuffer = new StringBuffer(200);
        stringBuffer.append(toString());
        stringBuffer.append("\n");
        for (DNSQuestion dNSQuestion : this.f25325b) {
            stringBuffer.append("\tquestion:      ");
            stringBuffer.append(dNSQuestion);
            stringBuffer.append("\n");
        }
        for (DNSRecord dNSRecord : this.f25326c) {
            stringBuffer.append("\tanswer:        ");
            stringBuffer.append(dNSRecord);
            stringBuffer.append("\n");
        }
        for (DNSRecord dNSRecord2 : this.f25327d) {
            stringBuffer.append("\tauthoritative: ");
            stringBuffer.append(dNSRecord2);
            stringBuffer.append("\n");
        }
        for (DNSRecord dNSRecord3 : this.f25328e) {
            stringBuffer.append("\tadditional:    ");
            stringBuffer.append(dNSRecord3);
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }

    protected String b(byte[] bArr) {
        StringBuilder sb = new StringBuilder(OpenAuthTask.SYS_ERR);
        int length = bArr.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            int iMin = Math.min(32, length - i2);
            if (i2 < 16) {
                sb.append(' ');
            }
            if (i2 < 256) {
                sb.append(' ');
            }
            if (i2 < 4096) {
                sb.append(' ');
            }
            sb.append(Integer.toHexString(i2));
            sb.append(':');
            int i3 = 0;
            while (i3 < iMin) {
                if (i3 % 8 == 0) {
                    sb.append(' ');
                }
                int i4 = i2 + i3;
                sb.append(Integer.toHexString((bArr[i4] & 240) >> 4));
                sb.append(Integer.toHexString(bArr[i4] & 15));
                i3++;
            }
            if (i3 < 32) {
                while (i3 < 32) {
                    if (i3 % 8 == 0) {
                        sb.append(' ');
                    }
                    sb.append("  ");
                    i3++;
                }
            }
            sb.append("    ");
            for (int i5 = 0; i5 < iMin; i5++) {
                if (i5 % 8 == 0) {
                    sb.append(' ');
                }
                int i6 = bArr[i2 + i5] & 255;
                sb.append((i6 <= 32 || i6 >= 127) ? '.' : (char) i6);
            }
            sb.append("\n");
            i2 += 32;
            if (i2 >= 2048) {
                sb.append("....\n");
                break;
            }
        }
        return sb.toString();
    }

    public Collection<? extends DNSRecord> getAdditionals() {
        return this.f25328e;
    }

    public Collection<? extends DNSRecord> getAllAnswers() {
        ArrayList arrayList = new ArrayList(this.f25326c.size() + this.f25327d.size() + this.f25328e.size());
        arrayList.addAll(this.f25326c);
        arrayList.addAll(this.f25327d);
        arrayList.addAll(this.f25328e);
        return arrayList;
    }

    public Collection<? extends DNSRecord> getAnswers() {
        return this.f25326c;
    }

    public Collection<? extends DNSRecord> getAuthorities() {
        return this.f25327d;
    }

    public int getFlags() {
        return this._flags;
    }

    public int getId() {
        if (this.f25324a) {
            return 0;
        }
        return this._id;
    }

    public int getNumberOfAdditionals() {
        return getAdditionals().size();
    }

    public int getNumberOfAnswers() {
        return getAnswers().size();
    }

    public int getNumberOfAuthorities() {
        return getAuthorities().size();
    }

    public int getNumberOfQuestions() {
        return getQuestions().size();
    }

    public Collection<? extends DNSQuestion> getQuestions() {
        return this.f25325b;
    }

    public boolean isEmpty() {
        return ((getNumberOfQuestions() + getNumberOfAnswers()) + getNumberOfAuthorities()) + getNumberOfAdditionals() == 0;
    }

    public boolean isMulticast() {
        return this.f25324a;
    }

    public boolean isQuery() {
        return (this._flags & 32768) == 0;
    }

    public boolean isResponse() {
        return (this._flags & 32768) == 32768;
    }

    public boolean isTruncated() {
        return (this._flags & 512) != 0;
    }

    public void setFlags(int i2) {
        this._flags = i2;
    }

    public void setId(int i2) {
        this._id = i2;
    }
}
