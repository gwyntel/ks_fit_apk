package androidx.constraintlayout.core.parser;

import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class CLContainer extends CLElement {

    /* renamed from: f, reason: collision with root package name */
    ArrayList f2782f;

    public CLContainer(char[] cArr) {
        super(cArr);
        this.f2782f = new ArrayList();
    }

    public static CLElement allocate(char[] cArr) {
        return new CLContainer(cArr);
    }

    public void add(CLElement cLElement) {
        this.f2782f.add(cLElement);
        if (CLParser.f2792a) {
            System.out.println("added element " + cLElement + " to " + this);
        }
    }

    public CLElement get(String str) throws CLParsingException {
        Iterator it = this.f2782f.iterator();
        while (it.hasNext()) {
            CLKey cLKey = (CLKey) ((CLElement) it.next());
            if (cLKey.content().equals(str)) {
                return cLKey.getValue();
            }
        }
        throw new CLParsingException("no element for key <" + str + ">", this);
    }

    public CLArray getArray(String str) throws CLParsingException {
        CLElement cLElement = get(str);
        if (cLElement instanceof CLArray) {
            return (CLArray) cLElement;
        }
        throw new CLParsingException("no array found for key <" + str + ">, found [" + cLElement.c() + "] : " + cLElement, this);
    }

    public CLArray getArrayOrNull(String str) {
        CLElement orNull = getOrNull(str);
        if (orNull instanceof CLArray) {
            return (CLArray) orNull;
        }
        return null;
    }

    public boolean getBoolean(String str) throws CLParsingException {
        CLElement cLElement = get(str);
        if (cLElement instanceof CLToken) {
            return ((CLToken) cLElement).getBoolean();
        }
        throw new CLParsingException("no boolean found for key <" + str + ">, found [" + cLElement.c() + "] : " + cLElement, this);
    }

    public float getFloat(String str) throws CLParsingException {
        CLElement cLElement = get(str);
        if (cLElement != null) {
            return cLElement.getFloat();
        }
        throw new CLParsingException("no float found for key <" + str + ">, found [" + cLElement.c() + "] : " + cLElement, this);
    }

    public float getFloatOrNaN(String str) {
        CLElement orNull = getOrNull(str);
        if (orNull instanceof CLNumber) {
            return orNull.getFloat();
        }
        return Float.NaN;
    }

    public int getInt(String str) throws CLParsingException {
        CLElement cLElement = get(str);
        if (cLElement != null) {
            return cLElement.getInt();
        }
        throw new CLParsingException("no int found for key <" + str + ">, found [" + cLElement.c() + "] : " + cLElement, this);
    }

    public CLObject getObject(String str) throws CLParsingException {
        CLElement cLElement = get(str);
        if (cLElement instanceof CLObject) {
            return (CLObject) cLElement;
        }
        throw new CLParsingException("no object found for key <" + str + ">, found [" + cLElement.c() + "] : " + cLElement, this);
    }

    public CLObject getObjectOrNull(String str) {
        CLElement orNull = getOrNull(str);
        if (orNull instanceof CLObject) {
            return (CLObject) orNull;
        }
        return null;
    }

    public CLElement getOrNull(String str) {
        Iterator it = this.f2782f.iterator();
        while (it.hasNext()) {
            CLKey cLKey = (CLKey) ((CLElement) it.next());
            if (cLKey.content().equals(str)) {
                return cLKey.getValue();
            }
        }
        return null;
    }

    public String getString(String str) throws CLParsingException {
        CLElement cLElement = get(str);
        if (cLElement instanceof CLString) {
            return cLElement.content();
        }
        throw new CLParsingException("no string found for key <" + str + ">, found [" + (cLElement != null ? cLElement.c() : null) + "] : " + cLElement, this);
    }

    public String getStringOrNull(String str) {
        CLElement orNull = getOrNull(str);
        if (orNull instanceof CLString) {
            return orNull.content();
        }
        return null;
    }

    public boolean has(String str) {
        Iterator it = this.f2782f.iterator();
        while (it.hasNext()) {
            CLElement cLElement = (CLElement) it.next();
            if ((cLElement instanceof CLKey) && ((CLKey) cLElement).content().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> names() {
        ArrayList<String> arrayList = new ArrayList<>();
        Iterator it = this.f2782f.iterator();
        while (it.hasNext()) {
            CLElement cLElement = (CLElement) it.next();
            if (cLElement instanceof CLKey) {
                arrayList.add(((CLKey) cLElement).content());
            }
        }
        return arrayList;
    }

    public void put(String str, CLElement cLElement) {
        Iterator it = this.f2782f.iterator();
        while (it.hasNext()) {
            CLKey cLKey = (CLKey) ((CLElement) it.next());
            if (cLKey.content().equals(str)) {
                cLKey.set(cLElement);
                return;
            }
        }
        this.f2782f.add((CLKey) CLKey.allocate(str, cLElement));
    }

    public void putNumber(String str, float f2) {
        put(str, new CLNumber(f2));
    }

    public void remove(String str) {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.f2782f.iterator();
        while (it.hasNext()) {
            CLElement cLElement = (CLElement) it.next();
            if (((CLKey) cLElement).content().equals(str)) {
                arrayList.add(cLElement);
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            this.f2782f.remove((CLElement) it2.next());
        }
    }

    public int size() {
        return this.f2782f.size();
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator it = this.f2782f.iterator();
        while (it.hasNext()) {
            CLElement cLElement = (CLElement) it.next();
            if (sb.length() > 0) {
                sb.append("; ");
            }
            sb.append(cLElement);
        }
        return super.toString() + " = <" + ((Object) sb) + " >";
    }

    public String getStringOrNull(int i2) {
        CLElement orNull = getOrNull(i2);
        if (orNull instanceof CLString) {
            return orNull.content();
        }
        return null;
    }

    public float getFloat(int i2) throws CLParsingException {
        CLElement cLElement = get(i2);
        if (cLElement != null) {
            return cLElement.getFloat();
        }
        throw new CLParsingException("no float at index " + i2, this);
    }

    public int getInt(int i2) throws CLParsingException {
        CLElement cLElement = get(i2);
        if (cLElement != null) {
            return cLElement.getInt();
        }
        throw new CLParsingException("no int at index " + i2, this);
    }

    public CLElement getOrNull(int i2) {
        if (i2 < 0 || i2 >= this.f2782f.size()) {
            return null;
        }
        return (CLElement) this.f2782f.get(i2);
    }

    public CLElement get(int i2) throws CLParsingException {
        if (i2 >= 0 && i2 < this.f2782f.size()) {
            return (CLElement) this.f2782f.get(i2);
        }
        throw new CLParsingException("no element at index " + i2, this);
    }

    public CLArray getArray(int i2) throws CLParsingException {
        CLElement cLElement = get(i2);
        if (cLElement instanceof CLArray) {
            return (CLArray) cLElement;
        }
        throw new CLParsingException("no array at index " + i2, this);
    }

    public boolean getBoolean(int i2) throws CLParsingException {
        CLElement cLElement = get(i2);
        if (cLElement instanceof CLToken) {
            return ((CLToken) cLElement).getBoolean();
        }
        throw new CLParsingException("no boolean at index " + i2, this);
    }

    public CLObject getObject(int i2) throws CLParsingException {
        CLElement cLElement = get(i2);
        if (cLElement instanceof CLObject) {
            return (CLObject) cLElement;
        }
        throw new CLParsingException("no object at index " + i2, this);
    }

    public String getString(int i2) throws CLParsingException {
        CLElement cLElement = get(i2);
        if (cLElement instanceof CLString) {
            return cLElement.content();
        }
        throw new CLParsingException("no string at index " + i2, this);
    }
}
