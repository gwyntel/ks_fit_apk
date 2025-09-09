package androidx.constraintlayout.core.parser;

import java.util.Iterator;

/* loaded from: classes.dex */
public class CLArray extends CLContainer {
    public CLArray(char[] cArr) {
        super(cArr);
    }

    public static CLElement allocate(char[] cArr) {
        return new CLArray(cArr);
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    protected String toFormattedJSON(int i2, int i3) {
        StringBuilder sb = new StringBuilder();
        String json = toJSON();
        if (i3 > 0 || json.length() + i2 >= CLElement.f2783d) {
            sb.append("[\n");
            Iterator it = this.f2782f.iterator();
            boolean z2 = true;
            while (it.hasNext()) {
                CLElement cLElement = (CLElement) it.next();
                if (z2) {
                    z2 = false;
                } else {
                    sb.append(",\n");
                }
                a(sb, CLElement.f2784e + i2);
                sb.append(cLElement.toFormattedJSON(CLElement.f2784e + i2, i3 - 1));
            }
            sb.append("\n");
            a(sb, i2);
            sb.append("]");
        } else {
            sb.append(json);
        }
        return sb.toString();
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    protected String toJSON() {
        StringBuilder sb = new StringBuilder(b() + "[");
        boolean z2 = true;
        for (int i2 = 0; i2 < this.f2782f.size(); i2++) {
            if (z2) {
                z2 = false;
            } else {
                sb.append(", ");
            }
            sb.append(((CLElement) this.f2782f.get(i2)).toJSON());
        }
        return ((Object) sb) + "]";
    }
}
