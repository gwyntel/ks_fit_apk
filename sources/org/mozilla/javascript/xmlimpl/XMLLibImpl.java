package org.mozilla.javascript.xmlimpl;

import androidx.webkit.ProxyConfig;
import java.io.Serializable;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Kit;
import org.mozilla.javascript.Ref;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.Wrapper;
import org.mozilla.javascript.xml.XMLLib;
import org.mozilla.javascript.xml.XMLObject;
import org.mozilla.javascript.xmlimpl.XmlNode;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/* loaded from: classes5.dex */
public final class XMLLibImpl extends XMLLib implements Serializable {
    private static final long serialVersionUID = 1;
    private Scriptable globalScope;
    private Namespace namespacePrototype;
    private XmlProcessor options = new XmlProcessor();
    private QName qnamePrototype;
    private XMLList xmlListPrototype;
    private XML xmlPrototype;

    private XMLLibImpl(Scriptable scriptable) {
        this.globalScope = scriptable;
    }

    private static RuntimeException badXMLName(Object obj) {
        String str;
        if (obj instanceof Number) {
            str = "Can not construct XML name from number: ";
        } else if (obj instanceof Boolean) {
            str = "Can not construct XML name from boolean: ";
        } else {
            if (obj != Undefined.instance && obj != null) {
                throw new IllegalArgumentException(obj.toString());
            }
            str = "Can not construct XML name from ";
        }
        return ScriptRuntime.typeError(str + ScriptRuntime.toString(obj));
    }

    private void exportToScope(boolean z2) {
        this.xmlPrototype = newXML(XmlNode.createText(this.options, ""));
        this.xmlListPrototype = newXMLList();
        this.namespacePrototype = Namespace.create(this.globalScope, null, XmlNode.Namespace.GLOBAL);
        this.qnamePrototype = QName.create(this, this.globalScope, null, XmlNode.QName.create(XmlNode.Namespace.create(""), ""));
        this.xmlPrototype.exportAsJSClass(z2);
        this.xmlListPrototype.exportAsJSClass(z2);
        this.namespacePrototype.exportAsJSClass(z2);
        this.qnamePrototype.exportAsJSClass(z2);
    }

    private String getDefaultNamespaceURI(Context context) {
        return getDefaultNamespace(context).uri();
    }

    public static void init(Context context, Scriptable scriptable, boolean z2) {
        XMLLibImpl xMLLibImpl = new XMLLibImpl(scriptable);
        if (xMLLibImpl.bindToScope(scriptable) == xMLLibImpl) {
            xMLLibImpl.exportToScope(z2);
        }
    }

    private XML parse(String str) {
        try {
            return newXML(XmlNode.createElement(this.options, getDefaultNamespaceURI(Context.getCurrentContext()), str));
        } catch (SAXException e2) {
            throw ScriptRuntime.typeError("Cannot parse XML: " + e2.getMessage());
        }
    }

    public static Node toDomNode(Object obj) {
        if (obj instanceof XML) {
            return ((XML) obj).toDomNode();
        }
        throw new IllegalArgumentException("xmlObject is not an XML object in JavaScript.");
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x001b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:18:? A[LOOP:0: B:3:0x0001->B:18:?, LOOP_END, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private org.mozilla.javascript.Ref xmlPrimaryReference(org.mozilla.javascript.Context r3, org.mozilla.javascript.xmlimpl.XMLName r4, org.mozilla.javascript.Scriptable r5) {
        /*
            r2 = this;
            r3 = 0
        L1:
            boolean r0 = r5 instanceof org.mozilla.javascript.xmlimpl.XMLWithScope
            if (r0 == 0) goto L15
            org.mozilla.javascript.Scriptable r0 = r5.getPrototype()
            org.mozilla.javascript.xmlimpl.XMLObjectImpl r0 = (org.mozilla.javascript.xmlimpl.XMLObjectImpl) r0
            boolean r1 = r0.hasXMLProperty(r4)
            if (r1 == 0) goto L12
            goto L1c
        L12:
            if (r3 != 0) goto L15
            r3 = r0
        L15:
            org.mozilla.javascript.Scriptable r5 = r5.getParentScope()
            if (r5 != 0) goto L1
            r0 = r3
        L1c:
            if (r0 == 0) goto L21
            r4.initXMLObject(r0)
        L21:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.xmlimpl.XMLLibImpl.xmlPrimaryReference(org.mozilla.javascript.Context, org.mozilla.javascript.xmlimpl.XMLName, org.mozilla.javascript.Scriptable):org.mozilla.javascript.Ref");
    }

    Object addXMLObjects(Context context, XMLObject xMLObject, XMLObject xMLObject2) {
        XMLList xMLListNewXMLList = newXMLList();
        if (xMLObject instanceof XMLList) {
            XMLList xMLList = (XMLList) xMLObject;
            if (xMLList.length() == 1) {
                xMLListNewXMLList.addToList(xMLList.item(0));
            } else {
                xMLListNewXMLList = newXMLListFrom(xMLObject);
            }
        } else {
            xMLListNewXMLList.addToList(xMLObject);
        }
        if (xMLObject2 instanceof XMLList) {
            XMLList xMLList2 = (XMLList) xMLObject2;
            for (int i2 = 0; i2 < xMLList2.length(); i2++) {
                xMLListNewXMLList.addToList(xMLList2.item(i2));
            }
        } else if (xMLObject2 instanceof XML) {
            xMLListNewXMLList.addToList(xMLObject2);
        }
        return xMLListNewXMLList;
    }

    Namespace castToNamespace(Context context, Object obj) {
        return this.namespacePrototype.castToNamespace(obj);
    }

    QName castToQName(Context context, Object obj) {
        return this.qnamePrototype.castToQName(this, context, obj);
    }

    QName constructQName(Context context, Object obj, Object obj2) {
        return this.qnamePrototype.constructQName(this, context, obj, obj2);
    }

    Namespace[] createNamespaces(XmlNode.Namespace[] namespaceArr) {
        Namespace[] namespaceArr2 = new Namespace[namespaceArr.length];
        for (int i2 = 0; i2 < namespaceArr.length; i2++) {
            namespaceArr2[i2] = this.namespacePrototype.newNamespace(namespaceArr[i2].getPrefix(), namespaceArr[i2].getUri());
        }
        return namespaceArr2;
    }

    final XML ecmaToXml(Object obj) {
        if (obj == null || obj == Undefined.instance) {
            throw ScriptRuntime.typeError("Cannot convert " + obj + " to XML");
        }
        if (obj instanceof XML) {
            return (XML) obj;
        }
        if (obj instanceof XMLList) {
            XMLList xMLList = (XMLList) obj;
            if (xMLList.getXML() != null) {
                return xMLList.getXML();
            }
            throw ScriptRuntime.typeError("Cannot convert list of >1 element to XML");
        }
        if (obj instanceof Wrapper) {
            obj = ((Wrapper) obj).unwrap();
        }
        if (obj instanceof Node) {
            return newXML(XmlNode.createElementFromNode((Node) obj));
        }
        String string = ScriptRuntime.toString(obj);
        return (string.length() <= 0 || string.charAt(0) != '<') ? newXML(XmlNode.createText(this.options, string)) : parse(string);
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public String escapeAttributeValue(Object obj) {
        return this.options.escapeAttributeValue(obj);
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public String escapeTextValue(Object obj) {
        return this.options.escapeTextValue(obj);
    }

    Namespace getDefaultNamespace(Context context) {
        if (context == null && (context = Context.getCurrentContext()) == null) {
            return this.namespacePrototype;
        }
        Object objSearchDefaultNamespace = ScriptRuntime.searchDefaultNamespace(context);
        return objSearchDefaultNamespace == null ? this.namespacePrototype : objSearchDefaultNamespace instanceof Namespace ? (Namespace) objSearchDefaultNamespace : this.namespacePrototype;
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public int getPrettyIndent() {
        return this.options.getPrettyIndent();
    }

    XmlProcessor getProcessor() {
        return this.options;
    }

    @Deprecated
    Scriptable globalScope() {
        return this.globalScope;
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public boolean isIgnoreComments() {
        return this.options.isIgnoreComments();
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public boolean isIgnoreProcessingInstructions() {
        return this.options.isIgnoreProcessingInstructions();
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public boolean isIgnoreWhitespace() {
        return this.options.isIgnoreWhitespace();
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public boolean isPrettyPrinting() {
        return this.options.isPrettyPrinting();
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public boolean isXMLName(Context context, Object obj) {
        return XMLName.accept(obj);
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public Ref nameRef(Context context, Object obj, Scriptable scriptable, int i2) {
        if ((i2 & 2) != 0) {
            return xmlPrimaryReference(context, toAttributeName(context, obj), scriptable);
        }
        throw Kit.codeBug();
    }

    Namespace newNamespace(String str) {
        return this.namespacePrototype.newNamespace(str);
    }

    QName newQName(String str, String str2, String str3) {
        return this.qnamePrototype.newQName(this, str, str2, str3);
    }

    final XML newTextElementXML(XmlNode xmlNode, XmlNode.QName qName, String str) {
        return newXML(XmlNode.newElementWithText(this.options, xmlNode, qName, str));
    }

    XML newXML(XmlNode xmlNode) {
        return new XML(this, this.globalScope, this.xmlPrototype, xmlNode);
    }

    final XML newXMLFromJs(Object obj) {
        String xMLString = (obj == null || obj == Undefined.instance) ? "" : obj instanceof XMLObjectImpl ? ((XMLObjectImpl) obj).toXMLString() : ScriptRuntime.toString(obj);
        if (xMLString.trim().startsWith("<>")) {
            throw ScriptRuntime.typeError("Invalid use of XML object anonymous tags <></>.");
        }
        return xMLString.indexOf("<") == -1 ? newXML(XmlNode.createText(this.options, xMLString)) : parse(xMLString);
    }

    XMLList newXMLList() {
        return new XMLList(this, this.globalScope, this.xmlListPrototype);
    }

    final XMLList newXMLListFrom(Object obj) {
        XMLList xMLListNewXMLList = newXMLList();
        if (obj == null || (obj instanceof Undefined)) {
            return xMLListNewXMLList;
        }
        if (obj instanceof XML) {
            xMLListNewXMLList.getNodeList().add((XML) obj);
            return xMLListNewXMLList;
        }
        if (obj instanceof XMLList) {
            xMLListNewXMLList.getNodeList().add(((XMLList) obj).getNodeList());
            return xMLListNewXMLList;
        }
        String strTrim = ScriptRuntime.toString(obj).trim();
        if (!strTrim.startsWith("<>")) {
            strTrim = "<>" + strTrim + "</>";
        }
        String str = "<fragment>" + strTrim.substring(2);
        if (!str.endsWith("</>")) {
            throw ScriptRuntime.typeError("XML with anonymous tag missing end anonymous tag");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, str.length() - 3));
        sb.append("</fragment>");
        XMLList xMLListChildren = newXMLFromJs(sb.toString()).children();
        for (int i2 = 0; i2 < xMLListChildren.getNodeList().length(); i2++) {
            xMLListNewXMLList.getNodeList().add((XML) xMLListChildren.item(i2).copy());
        }
        return xMLListNewXMLList;
    }

    @Deprecated
    QName qnamePrototype() {
        return this.qnamePrototype;
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public void setIgnoreComments(boolean z2) {
        this.options.setIgnoreComments(z2);
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public void setIgnoreProcessingInstructions(boolean z2) {
        this.options.setIgnoreProcessingInstructions(z2);
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public void setIgnoreWhitespace(boolean z2) {
        this.options.setIgnoreWhitespace(z2);
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public void setPrettyIndent(int i2) {
        this.options.setPrettyIndent(i2);
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public void setPrettyPrinting(boolean z2) {
        this.options.setPrettyPrinting(z2);
    }

    @Deprecated
    XMLName toAttributeName(Context context, Object obj) {
        if (obj instanceof XMLName) {
            return (XMLName) obj;
        }
        if (obj instanceof QName) {
            return XMLName.create(((QName) obj).getDelegate(), true, false);
        }
        if ((obj instanceof Boolean) || (obj instanceof Number) || obj == Undefined.instance || obj == null) {
            throw badXMLName(obj);
        }
        String string = obj instanceof String ? (String) obj : ScriptRuntime.toString(obj);
        if (string != null && string.equals(ProxyConfig.MATCH_ALL_SCHEMES)) {
            string = null;
        }
        return XMLName.create(XmlNode.QName.create(XmlNode.Namespace.create(""), string), true, false);
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public Object toDefaultXmlNamespace(Context context, Object obj) {
        return this.namespacePrototype.constructNamespace(obj);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    org.mozilla.javascript.xmlimpl.XmlNode.QName toNodeQName(org.mozilla.javascript.Context r4, java.lang.Object r5, java.lang.Object r6) {
        /*
            r3 = this;
            boolean r0 = r6 instanceof org.mozilla.javascript.xmlimpl.QName
            if (r0 == 0) goto Lb
            org.mozilla.javascript.xmlimpl.QName r6 = (org.mozilla.javascript.xmlimpl.QName) r6
            java.lang.String r6 = r6.localName()
            goto Lf
        Lb:
            java.lang.String r6 = org.mozilla.javascript.ScriptRuntime.toString(r6)
        Lf:
            java.lang.Object r0 = org.mozilla.javascript.Undefined.instance
            java.lang.String r1 = "*"
            r2 = 0
            if (r5 != r0) goto L27
            boolean r5 = r1.equals(r6)
            if (r5 == 0) goto L1e
        L1c:
            r4 = r2
            goto L3f
        L1e:
            org.mozilla.javascript.xmlimpl.Namespace r4 = r3.getDefaultNamespace(r4)
            org.mozilla.javascript.xmlimpl.XmlNode$Namespace r4 = r4.getDelegate()
            goto L3f
        L27:
            if (r5 != 0) goto L2a
            goto L1c
        L2a:
            boolean r4 = r5 instanceof org.mozilla.javascript.xmlimpl.Namespace
            if (r4 == 0) goto L35
            org.mozilla.javascript.xmlimpl.Namespace r5 = (org.mozilla.javascript.xmlimpl.Namespace) r5
            org.mozilla.javascript.xmlimpl.XmlNode$Namespace r4 = r5.getDelegate()
            goto L3f
        L35:
            org.mozilla.javascript.xmlimpl.Namespace r4 = r3.namespacePrototype
            org.mozilla.javascript.xmlimpl.Namespace r4 = r4.constructNamespace(r5)
            org.mozilla.javascript.xmlimpl.XmlNode$Namespace r4 = r4.getDelegate()
        L3f:
            if (r6 == 0) goto L48
            boolean r5 = r6.equals(r1)
            if (r5 == 0) goto L48
            r6 = r2
        L48:
            org.mozilla.javascript.xmlimpl.XmlNode$QName r4 = org.mozilla.javascript.xmlimpl.XmlNode.QName.create(r4, r6)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.xmlimpl.XMLLibImpl.toNodeQName(org.mozilla.javascript.Context, java.lang.Object, java.lang.Object):org.mozilla.javascript.xmlimpl.XmlNode$QName");
    }

    XMLName toXMLName(Context context, Object obj) {
        if (obj instanceof XMLName) {
            return (XMLName) obj;
        }
        if (obj instanceof QName) {
            QName qName = (QName) obj;
            return XMLName.formProperty(qName.uri(), qName.localName());
        }
        if (obj instanceof String) {
            return toXMLNameFromString(context, (String) obj);
        }
        if ((obj instanceof Boolean) || (obj instanceof Number) || obj == Undefined.instance || obj == null) {
            throw badXMLName(obj);
        }
        return toXMLNameFromString(context, ScriptRuntime.toString(obj));
    }

    XMLName toXMLNameFromString(Context context, String str) {
        return XMLName.create(getDefaultNamespaceURI(context), str);
    }

    XMLName toXMLNameOrIndex(Context context, Object obj) {
        XMLName xMLNameFormProperty;
        if (obj instanceof XMLName) {
            return (XMLName) obj;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            long jTestUint32String = ScriptRuntime.testUint32String(str);
            if (jTestUint32String >= 0) {
                ScriptRuntime.storeUint32Result(context, jTestUint32String);
                return null;
            }
            xMLNameFormProperty = toXMLNameFromString(context, str);
            return xMLNameFormProperty;
        }
        if (obj instanceof Number) {
            double dDoubleValue = ((Number) obj).doubleValue();
            long j2 = (long) dDoubleValue;
            if (j2 != dDoubleValue || 0 > j2 || j2 > 4294967295L) {
                throw badXMLName(obj);
            }
            ScriptRuntime.storeUint32Result(context, j2);
        } else {
            if (obj instanceof QName) {
                QName qName = (QName) obj;
                String strUri = qName.uri();
                if (strUri != null && strUri.length() == 0) {
                    long jTestUint32String2 = ScriptRuntime.testUint32String(strUri);
                    if (jTestUint32String2 >= 0) {
                        ScriptRuntime.storeUint32Result(context, jTestUint32String2);
                    }
                }
                xMLNameFormProperty = XMLName.formProperty(strUri, qName.localName());
                return xMLNameFormProperty;
            }
            if ((obj instanceof Boolean) || obj == Undefined.instance || obj == null) {
                throw badXMLName(obj);
            }
            String string = ScriptRuntime.toString(obj);
            long jTestUint32String3 = ScriptRuntime.testUint32String(string);
            if (jTestUint32String3 < 0) {
                return toXMLNameFromString(context, string);
            }
            ScriptRuntime.storeUint32Result(context, jTestUint32String3);
        }
        return null;
    }

    QName constructQName(Context context, Object obj) {
        return this.qnamePrototype.constructQName(this, context, obj);
    }

    QName newQName(XmlNode.QName qName) {
        return QName.create(this, this.globalScope, this.qnamePrototype, qName);
    }

    @Override // org.mozilla.javascript.xml.XMLLib
    public Ref nameRef(Context context, Object obj, Object obj2, Scriptable scriptable, int i2) {
        XMLName xMLNameCreate = XMLName.create(toNodeQName(context, obj, obj2), false, false);
        if ((i2 & 2) != 0 && !xMLNameCreate.isAttributeName()) {
            xMLNameCreate.setAttributeName();
        }
        return xmlPrimaryReference(context, xMLNameCreate, scriptable);
    }

    XmlNode.QName toNodeQName(Context context, String str, boolean z2) {
        XmlNode.Namespace delegate = getDefaultNamespace(context).getDelegate();
        if (str != null && str.equals(ProxyConfig.MATCH_ALL_SCHEMES)) {
            return XmlNode.QName.create(null, null);
        }
        if (z2) {
            return XmlNode.QName.create(XmlNode.Namespace.GLOBAL, str);
        }
        return XmlNode.QName.create(delegate, str);
    }

    XmlNode.QName toNodeQName(Context context, Object obj, boolean z2) {
        String string;
        if (obj instanceof XMLName) {
            return ((XMLName) obj).toQname();
        }
        if (obj instanceof QName) {
            return ((QName) obj).getDelegate();
        }
        if (!(obj instanceof Boolean) && !(obj instanceof Number) && obj != Undefined.instance && obj != null) {
            if (obj instanceof String) {
                string = (String) obj;
            } else {
                string = ScriptRuntime.toString(obj);
            }
            return toNodeQName(context, string, z2);
        }
        throw badXMLName(obj);
    }
}
