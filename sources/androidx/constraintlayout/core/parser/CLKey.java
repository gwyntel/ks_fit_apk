package androidx.constraintlayout.core.parser;

import java.util.ArrayList;

/* loaded from: classes.dex */
public class CLKey extends CLContainer {
    private static ArrayList<String> sections;

    static {
        ArrayList<String> arrayList = new ArrayList<>();
        sections = arrayList;
        arrayList.add("ConstraintSets");
        sections.add("Variables");
        sections.add("Generate");
        sections.add("Transitions");
        sections.add("KeyFrames");
        sections.add("KeyAttributes");
        sections.add("KeyPositions");
        sections.add("KeyCycles");
    }

    public CLKey(char[] cArr) {
        super(cArr);
    }

    public static CLElement allocate(char[] cArr) {
        return new CLKey(cArr);
    }

    public String getName() {
        return content();
    }

    public CLElement getValue() {
        if (this.f2782f.size() > 0) {
            return (CLElement) this.f2782f.get(0);
        }
        return null;
    }

    public void set(CLElement cLElement) {
        if (this.f2782f.size() > 0) {
            this.f2782f.set(0, cLElement);
        } else {
            this.f2782f.add(cLElement);
        }
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    protected String toFormattedJSON(int i2, int i3) {
        StringBuilder sb = new StringBuilder(b());
        a(sb, i2);
        String strContent = content();
        if (this.f2782f.size() <= 0) {
            return strContent + ": <> ";
        }
        sb.append(strContent);
        sb.append(": ");
        if (sections.contains(strContent)) {
            i3 = 3;
        }
        if (i3 > 0) {
            sb.append(((CLElement) this.f2782f.get(0)).toFormattedJSON(i2, i3 - 1));
        } else {
            String json = ((CLElement) this.f2782f.get(0)).toJSON();
            if (json.length() + i2 < CLElement.f2783d) {
                sb.append(json);
            } else {
                sb.append(((CLElement) this.f2782f.get(0)).toFormattedJSON(i2, i3 - 1));
            }
        }
        return sb.toString();
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    protected String toJSON() {
        if (this.f2782f.size() <= 0) {
            return b() + content() + ": <> ";
        }
        return b() + content() + ": " + ((CLElement) this.f2782f.get(0)).toJSON();
    }

    public static CLElement allocate(String str, CLElement cLElement) {
        CLKey cLKey = new CLKey(str.toCharArray());
        cLKey.setStart(0L);
        cLKey.setEnd(str.length() - 1);
        cLKey.set(cLElement);
        return cLKey;
    }
}
