package org.mozilla.javascript.xmlimpl;

import androidx.webkit.ProxyConfig;
import com.facebook.share.internal.ShareConstants;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.xmlimpl.XmlNode;

/* loaded from: classes5.dex */
final class QName extends IdScriptableObject {
    private static final int Id_constructor = 1;
    private static final int Id_localName = 1;
    private static final int Id_toSource = 3;
    private static final int Id_toString = 2;
    private static final int Id_uri = 2;
    private static final int MAX_INSTANCE_ID = 2;
    private static final int MAX_PROTOTYPE_ID = 3;
    private static final Object QNAME_TAG = "QName";
    static final long serialVersionUID = 416745167693026750L;
    private XmlNode.QName delegate;
    private XMLLibImpl lib;
    private QName prototype;

    private QName() {
    }

    static QName create(XMLLibImpl xMLLibImpl, Scriptable scriptable, QName qName, XmlNode.QName qName2) {
        QName qName3 = new QName();
        qName3.lib = xMLLibImpl;
        qName3.setParentScope(scriptable);
        qName3.prototype = qName;
        qName3.setPrototype(qName);
        qName3.delegate = qName2;
        return qName3;
    }

    private Object jsConstructor(Context context, boolean z2, Object[] objArr) {
        return (z2 || objArr.length != 1) ? objArr.length == 0 ? constructQName(this.lib, context, Undefined.instance) : objArr.length == 1 ? constructQName(this.lib, context, objArr[0]) : constructQName(this.lib, context, objArr[0], objArr[1]) : castToQName(this.lib, context, objArr[0]);
    }

    private String js_toSource() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        toSourceImpl(uri(), localName(), prefix(), sb);
        sb.append(')');
        return sb.toString();
    }

    private QName realThis(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        if (scriptable instanceof QName) {
            return (QName) scriptable;
        }
        throw IdScriptableObject.incompatibleCallError(idFunctionObject);
    }

    private static void toSourceImpl(String str, String str2, String str3, StringBuilder sb) {
        sb.append("new QName(");
        if (str != null || str3 != null) {
            Namespace.toSourceImpl(str3, str, sb);
            sb.append(", ");
        } else if (!ProxyConfig.MATCH_ALL_SCHEMES.equals(str2)) {
            sb.append("null, ");
        }
        sb.append('\'');
        sb.append(ScriptRuntime.escapeString(str2, '\''));
        sb.append("')");
    }

    QName castToQName(XMLLibImpl xMLLibImpl, Context context, Object obj) {
        return obj instanceof QName ? (QName) obj : constructQName(xMLLibImpl, context, obj);
    }

    QName constructQName(XMLLibImpl xMLLibImpl, Context context, Object obj, Object obj2) {
        String strPrefix;
        if (obj2 instanceof QName) {
            if (obj == Undefined.instance) {
                return (QName) obj2;
            }
            ((QName) obj2).localName();
        }
        Object obj3 = Undefined.instance;
        String string = obj2 == obj3 ? "" : ScriptRuntime.toString(obj2);
        String strUri = null;
        if (obj == obj3) {
            obj = ProxyConfig.MATCH_ALL_SCHEMES.equals(string) ? null : xMLLibImpl.getDefaultNamespace(context);
        }
        Namespace namespaceNewNamespace = obj == null ? null : obj instanceof Namespace ? (Namespace) obj : xMLLibImpl.newNamespace(ScriptRuntime.toString(obj));
        if (obj == null) {
            strPrefix = null;
        } else {
            strUri = namespaceNewNamespace.uri();
            strPrefix = namespaceNewNamespace.prefix();
        }
        return newQName(xMLLibImpl, strUri, string, strPrefix);
    }

    public boolean equals(Object obj) {
        if (obj instanceof QName) {
            return equals((QName) obj);
        }
        return false;
    }

    @Override // org.mozilla.javascript.ScriptableObject
    protected Object equivalentValues(Object obj) {
        return !(obj instanceof QName) ? Scriptable.NOT_FOUND : equals((QName) obj) ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (!idFunctionObject.hasTag(QNAME_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int iMethodId = idFunctionObject.methodId();
        if (iMethodId == 1) {
            return jsConstructor(context, scriptable2 == null, objArr);
        }
        if (iMethodId == 2) {
            return realThis(scriptable2, idFunctionObject).toString();
        }
        if (iMethodId == 3) {
            return realThis(scriptable2, idFunctionObject).js_toSource();
        }
        throw new IllegalArgumentException(String.valueOf(iMethodId));
    }

    void exportAsJSClass(boolean z2) {
        exportAsJSClass(3, getParentScope(), z2);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findInstanceIdInfo(String str) {
        String str2;
        int i2;
        int length = str.length();
        if (length == 3) {
            str2 = ShareConstants.MEDIA_URI;
            i2 = 2;
        } else if (length == 9) {
            str2 = "localName";
            i2 = 1;
        } else {
            str2 = null;
            i2 = 0;
        }
        int i3 = (str2 == null || str2 == str || str2.equals(str)) ? i2 : 0;
        if (i3 == 0) {
            return super.findInstanceIdInfo(str);
        }
        if (i3 == 1 || i3 == 2) {
            return IdScriptableObject.instanceIdInfo(5, super.getMaxInstanceId() + i3);
        }
        throw new IllegalStateException();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0025  */
    @Override // org.mozilla.javascript.IdScriptableObject
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected int findPrototypeId(java.lang.String r5) {
        /*
            r4 = this;
            int r0 = r5.length()
            r1 = 8
            r2 = 0
            if (r0 != r1) goto L1d
            r0 = 3
            char r1 = r5.charAt(r0)
            r3 = 111(0x6f, float:1.56E-43)
            if (r1 != r3) goto L15
            java.lang.String r1 = "toSource"
            goto L27
        L15:
            r0 = 116(0x74, float:1.63E-43)
            if (r1 != r0) goto L25
            java.lang.String r1 = "toString"
            r0 = 2
            goto L27
        L1d:
            r1 = 11
            if (r0 != r1) goto L25
            java.lang.String r1 = "constructor"
            r0 = 1
            goto L27
        L25:
            r1 = 0
            r0 = r2
        L27:
            if (r1 == 0) goto L32
            if (r1 == r5) goto L32
            boolean r5 = r1.equals(r5)
            if (r5 != 0) goto L32
            goto L33
        L32:
            r2 = r0
        L33:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.xmlimpl.QName.findPrototypeId(java.lang.String):int");
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return "QName";
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public Object getDefaultValue(Class<?> cls) {
        return toString();
    }

    final XmlNode.QName getDelegate() {
        return this.delegate;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected String getInstanceIdName(int i2) {
        int maxInstanceId = i2 - super.getMaxInstanceId();
        return maxInstanceId != 1 ? maxInstanceId != 2 ? super.getInstanceIdName(i2) : ShareConstants.MEDIA_URI : "localName";
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected Object getInstanceIdValue(int i2) {
        int maxInstanceId = i2 - super.getMaxInstanceId();
        return maxInstanceId != 1 ? maxInstanceId != 2 ? super.getInstanceIdValue(i2) : uri() : localName();
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int getMaxInstanceId() {
        return super.getMaxInstanceId() + 2;
    }

    public int hashCode() {
        return this.delegate.hashCode();
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i2) {
        String str;
        int i3;
        if (i2 != 1) {
            i3 = 0;
            if (i2 == 2) {
                str = "toString";
            } else {
                if (i2 != 3) {
                    throw new IllegalArgumentException(String.valueOf(i2));
                }
                str = "toSource";
            }
        } else {
            str = "constructor";
            i3 = 2;
        }
        initPrototypeMethod(QNAME_TAG, i2, str, i3);
    }

    public String localName() {
        return this.delegate.getLocalName() == null ? ProxyConfig.MATCH_ALL_SCHEMES : this.delegate.getLocalName();
    }

    QName newQName(XMLLibImpl xMLLibImpl, String str, String str2, String str3) {
        QName qName = this.prototype;
        if (qName == null) {
            qName = this;
        }
        XmlNode.Namespace namespaceCreate = str3 != null ? XmlNode.Namespace.create(str3, str) : str != null ? XmlNode.Namespace.create(str) : null;
        if (str2 != null && str2.equals(ProxyConfig.MATCH_ALL_SCHEMES)) {
            str2 = null;
        }
        return create(xMLLibImpl, getParentScope(), qName, XmlNode.QName.create(namespaceCreate, str2));
    }

    String prefix() {
        if (this.delegate.getNamespace() == null) {
            return null;
        }
        return this.delegate.getNamespace().getPrefix();
    }

    @Deprecated
    final XmlNode.QName toNodeQname() {
        return this.delegate;
    }

    public String toString() {
        if (this.delegate.getNamespace() == null) {
            return "*::" + localName();
        }
        if (this.delegate.getNamespace().isGlobal()) {
            return localName();
        }
        return uri() + "::" + localName();
    }

    String uri() {
        if (this.delegate.getNamespace() == null) {
            return null;
        }
        return this.delegate.getNamespace().getUri();
    }

    private boolean equals(QName qName) {
        return this.delegate.equals(qName.delegate);
    }

    QName constructQName(XMLLibImpl xMLLibImpl, Context context, Object obj) {
        return constructQName(xMLLibImpl, context, Undefined.instance, obj);
    }
}
