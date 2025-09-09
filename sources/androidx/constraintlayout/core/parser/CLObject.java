package androidx.constraintlayout.core.parser;

import com.alipay.sdk.m.u.i;
import java.util.Iterator;

/* loaded from: classes.dex */
public class CLObject extends CLContainer implements Iterable<CLKey> {

    private class CLObjectIterator implements Iterator {

        /* renamed from: a, reason: collision with root package name */
        CLObject f2789a;

        /* renamed from: b, reason: collision with root package name */
        int f2790b = 0;

        public CLObjectIterator(CLObject cLObject) {
            this.f2789a = cLObject;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f2790b < this.f2789a.size();
        }

        @Override // java.util.Iterator
        public Object next() {
            CLKey cLKey = (CLKey) this.f2789a.f2782f.get(this.f2790b);
            this.f2790b++;
            return cLKey;
        }
    }

    public CLObject(char[] cArr) {
        super(cArr);
    }

    public static CLObject allocate(char[] cArr) {
        return new CLObject(cArr);
    }

    @Override // java.lang.Iterable
    public Iterator<CLKey> iterator() {
        return new CLObjectIterator(this);
    }

    public String toFormattedJSON() {
        return toFormattedJSON(0, 0);
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    public String toJSON() {
        StringBuilder sb = new StringBuilder(b() + "{ ");
        Iterator it = this.f2782f.iterator();
        boolean z2 = true;
        while (it.hasNext()) {
            CLElement cLElement = (CLElement) it.next();
            if (z2) {
                z2 = false;
            } else {
                sb.append(", ");
            }
            sb.append(cLElement.toJSON());
        }
        sb.append(" }");
        return sb.toString();
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    public String toFormattedJSON(int i2, int i3) {
        StringBuilder sb = new StringBuilder(b());
        sb.append("{\n");
        Iterator it = this.f2782f.iterator();
        boolean z2 = true;
        while (it.hasNext()) {
            CLElement cLElement = (CLElement) it.next();
            if (z2) {
                z2 = false;
            } else {
                sb.append(",\n");
            }
            sb.append(cLElement.toFormattedJSON(CLElement.f2784e + i2, i3 - 1));
        }
        sb.append("\n");
        a(sb, i2);
        sb.append(i.f9804d);
        return sb.toString();
    }
}
