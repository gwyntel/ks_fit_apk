package androidx.constraintlayout.core.parser;

import anetwork.channel.util.RequestConstant;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;

/* loaded from: classes.dex */
public class CLToken extends CLElement {

    /* renamed from: f, reason: collision with root package name */
    int f2794f;

    /* renamed from: g, reason: collision with root package name */
    Type f2795g;

    /* renamed from: h, reason: collision with root package name */
    char[] f2796h;

    /* renamed from: i, reason: collision with root package name */
    char[] f2797i;

    /* renamed from: j, reason: collision with root package name */
    char[] f2798j;

    /* renamed from: androidx.constraintlayout.core.parser.CLToken$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f2799a;

        static {
            int[] iArr = new int[Type.values().length];
            f2799a = iArr;
            try {
                iArr[Type.TRUE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f2799a[Type.FALSE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f2799a[Type.NULL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f2799a[Type.UNKNOWN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    enum Type {
        UNKNOWN,
        TRUE,
        FALSE,
        NULL
    }

    public CLToken(char[] cArr) {
        super(cArr);
        this.f2794f = 0;
        this.f2795g = Type.UNKNOWN;
        this.f2796h = "true".toCharArray();
        this.f2797i = RequestConstant.FALSE.toCharArray();
        this.f2798j = TmpConstant.GROUP_ROLE_UNKNOWN.toCharArray();
    }

    public static CLElement allocate(char[] cArr) {
        return new CLToken(cArr);
    }

    public boolean getBoolean() throws CLParsingException {
        Type type = this.f2795g;
        if (type == Type.TRUE) {
            return true;
        }
        if (type == Type.FALSE) {
            return false;
        }
        throw new CLParsingException("this token is not a boolean: <" + content() + ">", this);
    }

    public Type getType() {
        return this.f2795g;
    }

    public boolean isNull() throws CLParsingException {
        if (this.f2795g == Type.NULL) {
            return true;
        }
        throw new CLParsingException("this token is not a null: <" + content() + ">", this);
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    protected String toFormattedJSON(int i2, int i3) {
        StringBuilder sb = new StringBuilder();
        a(sb, i2);
        sb.append(content());
        return sb.toString();
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    protected String toJSON() {
        if (!CLParser.f2792a) {
            return content();
        }
        return "<" + content() + ">";
    }

    public boolean validate(char c2, long j2) {
        int i2 = AnonymousClass1.f2799a[this.f2795g.ordinal()];
        if (i2 == 1) {
            char[] cArr = this.f2796h;
            int i3 = this.f2794f;
            z = cArr[i3] == c2;
            if (z && i3 + 1 == cArr.length) {
                setEnd(j2);
            }
        } else if (i2 == 2) {
            char[] cArr2 = this.f2797i;
            int i4 = this.f2794f;
            z = cArr2[i4] == c2;
            if (z && i4 + 1 == cArr2.length) {
                setEnd(j2);
            }
        } else if (i2 == 3) {
            char[] cArr3 = this.f2798j;
            int i5 = this.f2794f;
            z = cArr3[i5] == c2;
            if (z && i5 + 1 == cArr3.length) {
                setEnd(j2);
            }
        } else if (i2 == 4) {
            char[] cArr4 = this.f2796h;
            int i6 = this.f2794f;
            if (cArr4[i6] == c2) {
                this.f2795g = Type.TRUE;
            } else if (this.f2797i[i6] == c2) {
                this.f2795g = Type.FALSE;
            } else if (this.f2798j[i6] == c2) {
                this.f2795g = Type.NULL;
            }
            z = true;
        }
        this.f2794f++;
        return z;
    }
}
