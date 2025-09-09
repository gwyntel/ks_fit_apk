package androidx.constraintlayout.core.parser;

import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes.dex */
public class CLElement {

    /* renamed from: d, reason: collision with root package name */
    protected static int f2783d = 80;

    /* renamed from: e, reason: collision with root package name */
    protected static int f2784e = 2;

    /* renamed from: a, reason: collision with root package name */
    protected long f2785a = -1;

    /* renamed from: b, reason: collision with root package name */
    protected long f2786b = Long.MAX_VALUE;

    /* renamed from: c, reason: collision with root package name */
    protected CLContainer f2787c;
    private int line;
    private final char[] mContent;

    public CLElement(char[] cArr) {
        this.mContent = cArr;
    }

    protected void a(StringBuilder sb, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            sb.append(' ');
        }
    }

    protected String b() {
        if (!CLParser.f2792a) {
            return "";
        }
        return c() + " -> ";
    }

    protected String c() {
        String string = getClass().toString();
        return string.substring(string.lastIndexOf(46) + 1);
    }

    public String content() {
        String str = new String(this.mContent);
        long j2 = this.f2786b;
        if (j2 != Long.MAX_VALUE) {
            long j3 = this.f2785a;
            if (j2 >= j3) {
                return str.substring((int) j3, ((int) j2) + 1);
            }
        }
        long j4 = this.f2785a;
        return str.substring((int) j4, ((int) j4) + 1);
    }

    public CLElement getContainer() {
        return this.f2787c;
    }

    public long getEnd() {
        return this.f2786b;
    }

    public float getFloat() {
        if (this instanceof CLNumber) {
            return ((CLNumber) this).getFloat();
        }
        return Float.NaN;
    }

    public int getInt() {
        if (this instanceof CLNumber) {
            return ((CLNumber) this).getInt();
        }
        return 0;
    }

    public int getLine() {
        return this.line;
    }

    public long getStart() {
        return this.f2785a;
    }

    public boolean isDone() {
        return this.f2786b != Long.MAX_VALUE;
    }

    public boolean isStarted() {
        return this.f2785a > -1;
    }

    public boolean notStarted() {
        return this.f2785a == -1;
    }

    public void setContainer(CLContainer cLContainer) {
        this.f2787c = cLContainer;
    }

    public void setEnd(long j2) {
        if (this.f2786b != Long.MAX_VALUE) {
            return;
        }
        this.f2786b = j2;
        if (CLParser.f2792a) {
            System.out.println("closing " + hashCode() + " -> " + this);
        }
        CLContainer cLContainer = this.f2787c;
        if (cLContainer != null) {
            cLContainer.add(this);
        }
    }

    public void setLine(int i2) {
        this.line = i2;
    }

    public void setStart(long j2) {
        this.f2785a = j2;
    }

    protected String toFormattedJSON(int i2, int i3) {
        return "";
    }

    protected String toJSON() {
        return "";
    }

    public String toString() {
        long j2 = this.f2785a;
        long j3 = this.f2786b;
        if (j2 > j3 || j3 == Long.MAX_VALUE) {
            return getClass() + " (INVALID, " + this.f2785a + Constants.ACCEPT_TIME_SEPARATOR_SERVER + this.f2786b + ")";
        }
        return c() + " (" + this.f2785a + " : " + this.f2786b + ") <<" + new String(this.mContent).substring((int) this.f2785a, ((int) this.f2786b) + 1) + ">>";
    }
}
