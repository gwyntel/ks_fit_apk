package org.mozilla.javascript.serialize;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.UniqueTag;

/* loaded from: classes5.dex */
public class ScriptableOutputStream extends ObjectOutputStream {
    private Scriptable scope;
    private Map<Object, String> table;

    static class PendingLookup implements Serializable {
        static final long serialVersionUID = -2692990309789917727L;
        private String name;

        PendingLookup(String str) {
            this.name = str;
        }

        String getName() {
            return this.name;
        }
    }

    public ScriptableOutputStream(OutputStream outputStream, Scriptable scriptable) throws SecurityException, IOException {
        super(outputStream);
        this.scope = scriptable;
        HashMap map = new HashMap();
        this.table = map;
        map.put(scriptable, "");
        enableReplaceObject(true);
        excludeStandardObjectNames();
    }

    /* JADX WARN: Multi-variable type inference failed */
    static Object lookupQualifiedName(Scriptable scriptable, String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, ".");
        while (stringTokenizer.hasMoreTokens()) {
            scriptable = ScriptableObject.getProperty((Scriptable) scriptable, stringTokenizer.nextToken());
            if (scriptable == 0 || !(scriptable instanceof Scriptable)) {
                break;
            }
        }
        return scriptable;
    }

    public void addExcludedName(String str) {
        Object objLookupQualifiedName = lookupQualifiedName(this.scope, str);
        if (objLookupQualifiedName instanceof Scriptable) {
            this.table.put(objLookupQualifiedName, str);
            return;
        }
        throw new IllegalArgumentException("Object for excluded name " + str + " not found.");
    }

    public void addOptionalExcludedName(String str) {
        Object objLookupQualifiedName = lookupQualifiedName(this.scope, str);
        if (objLookupQualifiedName == null || objLookupQualifiedName == UniqueTag.NOT_FOUND) {
            return;
        }
        if (objLookupQualifiedName instanceof Scriptable) {
            this.table.put(objLookupQualifiedName, str);
            return;
        }
        throw new IllegalArgumentException("Object for excluded name " + str + " is not a Scriptable, it is " + objLookupQualifiedName.getClass().getName());
    }

    public void excludeAllIds(Object[] objArr) {
        for (Object obj : objArr) {
            if (obj instanceof String) {
                Scriptable scriptable = this.scope;
                String str = (String) obj;
                if (scriptable.get(str, scriptable) instanceof Scriptable) {
                    addExcludedName(str);
                }
            }
        }
    }

    public void excludeStandardObjectNames() {
        String[] strArr = {"Object", "Object.prototype", "Function", "Function.prototype", "String", "String.prototype", "Math", "Array", "Array.prototype", "Error", "Error.prototype", "Number", "Number.prototype", "Date", "Date.prototype", "RegExp", "RegExp.prototype", "Script", "Script.prototype", "Continuation", "Continuation.prototype"};
        for (int i2 = 0; i2 < 21; i2++) {
            addExcludedName(strArr[i2]);
        }
        String[] strArr2 = {"XML", "XML.prototype", "XMLList", "XMLList.prototype"};
        for (int i3 = 0; i3 < 4; i3++) {
            addOptionalExcludedName(strArr2[i3]);
        }
    }

    public boolean hasExcludedName(String str) {
        return this.table.get(str) != null;
    }

    public void removeExcludedName(String str) {
        this.table.remove(str);
    }

    @Override // java.io.ObjectOutputStream
    protected Object replaceObject(Object obj) throws IOException {
        String str = this.table.get(obj);
        return str == null ? obj : new PendingLookup(str);
    }
}
