package org.mozilla.javascript.xmlimpl;

import androidx.webkit.ProxyConfig;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import kotlin.text.Typography;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.EcmaError;
import org.mozilla.javascript.Kit;
import org.mozilla.javascript.Ref;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.xmlimpl.XmlNode;

/* loaded from: classes5.dex */
class XMLName extends Ref {
    static final long serialVersionUID = 3832176310755686977L;
    private boolean isAttributeName;
    private boolean isDescendants;
    private XmlNode.QName qname;
    private XMLObjectImpl xmlObject;

    private XMLName() {
    }

    static boolean accept(Object obj) {
        try {
            String string = ScriptRuntime.toString(obj);
            int length = string.length();
            if (length == 0 || !isNCNameStartChar(string.charAt(0))) {
                return false;
            }
            for (int i2 = 1; i2 != length; i2++) {
                if (!isNCNameChar(string.charAt(i2))) {
                    return false;
                }
            }
            return true;
        } catch (EcmaError e2) {
            if ("TypeError".equals(e2.getName())) {
                return false;
            }
            throw e2;
        }
    }

    private void addAttributes(XMLList xMLList, XML xml) {
        addMatchingAttributes(xMLList, xml);
    }

    private void addDescendantAttributes(XMLList xMLList, XML xml) {
        if (xml.isElement()) {
            addMatchingAttributes(xMLList, xml);
            for (XML xml2 : xml.getChildren()) {
                addDescendantAttributes(xMLList, xml2);
            }
        }
    }

    private void addDescendantChildren(XMLList xMLList, XML xml) {
        if (xml.isElement()) {
            XML[] children = xml.getChildren();
            for (int i2 = 0; i2 < children.length; i2++) {
                if (matches(children[i2])) {
                    xMLList.addToList(children[i2]);
                }
                addDescendantChildren(xMLList, children[i2]);
            }
        }
    }

    static XMLName create(String str, String str2) {
        if (str2 == null) {
            throw new IllegalArgumentException();
        }
        int length = str2.length();
        if (length != 0) {
            char cCharAt = str2.charAt(0);
            if (cCharAt == '*') {
                if (length == 1) {
                    return formStar();
                }
            } else if (cCharAt == '@') {
                XMLName xMLNameFormProperty = formProperty("", str2.substring(1));
                xMLNameFormProperty.setAttributeName();
                return xMLNameFormProperty;
            }
        }
        return formProperty(str, str2);
    }

    @Deprecated
    static XMLName formProperty(XmlNode.Namespace namespace, String str) {
        if (str != null && str.equals(ProxyConfig.MATCH_ALL_SCHEMES)) {
            str = null;
        }
        XMLName xMLName = new XMLName();
        xMLName.qname = XmlNode.QName.create(namespace, str);
        return xMLName;
    }

    static XMLName formStar() {
        XMLName xMLName = new XMLName();
        xMLName.qname = XmlNode.QName.create(null, null);
        return xMLName;
    }

    private static boolean isNCNameChar(int i2) {
        return (i2 & (-128)) == 0 ? i2 >= 97 ? i2 <= 122 : i2 >= 65 ? i2 <= 90 || i2 == 95 : i2 >= 48 ? i2 <= 57 : i2 == 45 || i2 == 46 : (i2 & (-8192)) == 0 ? isNCNameStartChar(i2) || i2 == 183 || (768 <= i2 && i2 <= 879) : isNCNameStartChar(i2) || (8255 <= i2 && i2 <= 8256);
    }

    private static boolean isNCNameStartChar(int i2) {
        if ((i2 & (-128)) == 0) {
            if (i2 >= 97) {
                return i2 <= 122;
            }
            if (i2 >= 65) {
                return i2 <= 90 || i2 == 95;
            }
        } else if ((i2 & (-8192)) == 0) {
            return (192 <= i2 && i2 <= 214) || (216 <= i2 && i2 <= 246) || ((248 <= i2 && i2 <= 767) || ((880 <= i2 && i2 <= 893) || 895 <= i2));
        }
        return (8204 <= i2 && i2 <= 8205) || (8304 <= i2 && i2 <= 8591) || ((11264 <= i2 && i2 <= 12271) || ((12289 <= i2 && i2 <= 55295) || ((63744 <= i2 && i2 <= 64975) || ((65008 <= i2 && i2 <= 65533) || (65536 <= i2 && i2 <= 983039)))));
    }

    void addDescendants(XMLList xMLList, XML xml) {
        if (isAttributeName()) {
            matchDescendantAttributes(xMLList, xml);
        } else {
            matchDescendantChildren(xMLList, xml);
        }
    }

    void addMatches(XMLList xMLList, XML xml) {
        if (isDescendants()) {
            addDescendants(xMLList, xml);
            return;
        }
        if (isAttributeName()) {
            addAttributes(xMLList, xml);
            return;
        }
        XML[] children = xml.getChildren();
        if (children != null) {
            for (int i2 = 0; i2 < children.length; i2++) {
                if (matches(children[i2])) {
                    xMLList.addToList(children[i2]);
                }
            }
        }
        xMLList.setTargets(xml, toQname());
    }

    void addMatchingAttributes(XMLList xMLList, XML xml) {
        if (xml.isElement()) {
            XML[] attributes = xml.getAttributes();
            for (int i2 = 0; i2 < attributes.length; i2++) {
                if (matches(attributes[i2])) {
                    xMLList.addToList(attributes[i2]);
                }
            }
        }
    }

    @Override // org.mozilla.javascript.Ref
    public boolean delete(Context context) {
        XMLObjectImpl xMLObjectImpl = this.xmlObject;
        if (xMLObjectImpl == null) {
            return true;
        }
        xMLObjectImpl.deleteXMLProperty(this);
        return !this.xmlObject.hasXMLProperty(this);
    }

    @Override // org.mozilla.javascript.Ref
    public Object get(Context context) {
        XMLObjectImpl xMLObjectImpl = this.xmlObject;
        if (xMLObjectImpl != null) {
            return xMLObjectImpl.getXMLProperty(this);
        }
        throw ScriptRuntime.undefReadError(Undefined.instance, toString());
    }

    XMLList getMyValueOn(XML xml) {
        XMLList xMLListNewXMLList = xml.newXMLList();
        addMatches(xMLListNewXMLList, xml);
        return xMLListNewXMLList;
    }

    @Override // org.mozilla.javascript.Ref
    public boolean has(Context context) {
        XMLObjectImpl xMLObjectImpl = this.xmlObject;
        if (xMLObjectImpl == null) {
            return false;
        }
        return xMLObjectImpl.hasXMLProperty(this);
    }

    void initXMLObject(XMLObjectImpl xMLObjectImpl) {
        if (xMLObjectImpl == null) {
            throw new IllegalArgumentException();
        }
        if (this.xmlObject != null) {
            throw new IllegalStateException();
        }
        this.xmlObject = xMLObjectImpl;
    }

    boolean isAttributeName() {
        return this.isAttributeName;
    }

    boolean isDescendants() {
        return this.isDescendants;
    }

    String localName() {
        return this.qname.getLocalName() == null ? ProxyConfig.MATCH_ALL_SCHEMES : this.qname.getLocalName();
    }

    XMLList matchDescendantAttributes(XMLList xMLList, XML xml) {
        xMLList.setTargets(xml, null);
        addDescendantAttributes(xMLList, xml);
        return xMLList;
    }

    XMLList matchDescendantChildren(XMLList xMLList, XML xml) {
        xMLList.setTargets(xml, null);
        addDescendantChildren(xMLList, xml);
        return xMLList;
    }

    final boolean matches(XML xml) {
        XmlNode.QName nodeQname = xml.getNodeQname();
        String uri = nodeQname.getNamespace() != null ? nodeQname.getNamespace().getUri() : null;
        if (this.isAttributeName) {
            if (xml.isAttribute()) {
                return (uri() == null || uri().equals(uri)) && (localName().equals(ProxyConfig.MATCH_ALL_SCHEMES) || localName().equals(nodeQname.getLocalName()));
            }
            return false;
        }
        if (uri() == null || (xml.isElement() && uri().equals(uri))) {
            if (localName().equals(ProxyConfig.MATCH_ALL_SCHEMES)) {
                return true;
            }
            if (xml.isElement() && localName().equals(nodeQname.getLocalName())) {
                return true;
            }
        }
        return false;
    }

    final boolean matchesElement(XmlNode.QName qName) {
        if (uri() == null || uri().equals(qName.getNamespace().getUri())) {
            return localName().equals(ProxyConfig.MATCH_ALL_SCHEMES) || localName().equals(qName.getLocalName());
        }
        return false;
    }

    final boolean matchesLocalName(String str) {
        return localName().equals(ProxyConfig.MATCH_ALL_SCHEMES) || localName().equals(str);
    }

    @Override // org.mozilla.javascript.Ref
    public Object set(Context context, Object obj) {
        XMLObjectImpl xMLObjectImpl = this.xmlObject;
        if (xMLObjectImpl == null) {
            throw ScriptRuntime.undefWriteError(Undefined.instance, toString(), obj);
        }
        if (this.isDescendants) {
            throw Kit.codeBug();
        }
        xMLObjectImpl.putXMLProperty(this, obj);
        return obj;
    }

    void setAttributeName() {
        this.isAttributeName = true;
    }

    @Deprecated
    void setIsDescendants() {
        this.isDescendants = true;
    }

    void setMyValueOn(XML xml, Object obj) {
        XMLObjectImpl xMLObjectImplMakeXmlFromString;
        if (obj == null) {
            obj = TmpConstant.GROUP_ROLE_UNKNOWN;
        } else if (obj instanceof Undefined) {
            obj = "undefined";
        }
        if (isAttributeName()) {
            xml.setAttribute(this, obj);
            return;
        }
        if (uri() == null && localName().equals(ProxyConfig.MATCH_ALL_SCHEMES)) {
            xml.setChildren(obj);
            return;
        }
        if (obj instanceof XMLObjectImpl) {
            xMLObjectImplMakeXmlFromString = (XMLObjectImpl) obj;
            if ((xMLObjectImplMakeXmlFromString instanceof XML) && ((XML) xMLObjectImplMakeXmlFromString).isAttribute()) {
                xMLObjectImplMakeXmlFromString = xml.makeXmlFromString(this, xMLObjectImplMakeXmlFromString.toString());
            }
            if (xMLObjectImplMakeXmlFromString instanceof XMLList) {
                for (int i2 = 0; i2 < xMLObjectImplMakeXmlFromString.length(); i2++) {
                    XMLList xMLList = (XMLList) xMLObjectImplMakeXmlFromString;
                    XML xmlItem = xMLList.item(i2);
                    if (xmlItem.isAttribute()) {
                        xMLList.replace(i2, xml.makeXmlFromString(this, xmlItem.toString()));
                    }
                }
            }
        } else {
            xMLObjectImplMakeXmlFromString = xml.makeXmlFromString(this, ScriptRuntime.toString(obj));
        }
        XMLList propertyList = xml.getPropertyList(this);
        if (propertyList.length() == 0) {
            xml.appendChild(xMLObjectImplMakeXmlFromString);
            return;
        }
        for (int i3 = 1; i3 < propertyList.length(); i3++) {
            xml.removeChild(propertyList.item(i3).childIndex());
        }
        xml.replace(propertyList.item(0).childIndex(), xMLObjectImplMakeXmlFromString);
    }

    final XmlNode.QName toQname() {
        return this.qname;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.isDescendants) {
            sb.append("..");
        }
        if (this.isAttributeName) {
            sb.append('@');
        }
        if (uri() == null) {
            sb.append('*');
            if (localName().equals(ProxyConfig.MATCH_ALL_SCHEMES)) {
                return sb.toString();
            }
        } else {
            sb.append(Typography.quote);
            sb.append(uri());
            sb.append(Typography.quote);
        }
        sb.append(':');
        sb.append(localName());
        return sb.toString();
    }

    String uri() {
        if (this.qname.getNamespace() == null) {
            return null;
        }
        return this.qname.getNamespace().getUri();
    }

    static XMLName formProperty(String str, String str2) {
        return formProperty(XmlNode.Namespace.create(str), str2);
    }

    static XMLName create(XmlNode.QName qName, boolean z2, boolean z3) {
        XMLName xMLName = new XMLName();
        xMLName.qname = qName;
        xMLName.isAttributeName = z2;
        xMLName.isDescendants = z3;
        return xMLName;
    }

    @Deprecated
    static XMLName create(XmlNode.QName qName) {
        return create(qName, false, false);
    }
}
