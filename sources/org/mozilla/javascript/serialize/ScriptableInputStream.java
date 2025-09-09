package org.mozilla.javascript.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.UniqueTag;
import org.mozilla.javascript.serialize.ScriptableOutputStream;

/* loaded from: classes5.dex */
public class ScriptableInputStream extends ObjectInputStream {
    private ClassLoader classLoader;
    private Scriptable scope;

    public ScriptableInputStream(InputStream inputStream, Scriptable scriptable) throws SecurityException, IOException {
        super(inputStream);
        this.scope = scriptable;
        enableResolveObject(true);
        Context currentContext = Context.getCurrentContext();
        if (currentContext != null) {
            this.classLoader = currentContext.getApplicationClassLoader();
        }
    }

    @Override // java.io.ObjectInputStream
    protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
        String name = objectStreamClass.getName();
        ClassLoader classLoader = this.classLoader;
        if (classLoader != null) {
            try {
                return classLoader.loadClass(name);
            } catch (ClassNotFoundException unused) {
            }
        }
        return super.resolveClass(objectStreamClass);
    }

    @Override // java.io.ObjectInputStream
    protected Object resolveObject(Object obj) throws IOException {
        if (!(obj instanceof ScriptableOutputStream.PendingLookup)) {
            return obj instanceof UniqueTag ? ((UniqueTag) obj).readResolve() : obj instanceof Undefined ? ((Undefined) obj).readResolve() : obj;
        }
        String name = ((ScriptableOutputStream.PendingLookup) obj).getName();
        Object objLookupQualifiedName = ScriptableOutputStream.lookupQualifiedName(this.scope, name);
        if (objLookupQualifiedName != Scriptable.NOT_FOUND) {
            return objLookupQualifiedName;
        }
        throw new IOException("Object " + name + " not found upon deserialization.");
    }
}
