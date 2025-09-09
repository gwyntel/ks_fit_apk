package org.mozilla.javascript.xmlimpl;

import androidx.core.app.NotificationCompat;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.util.ArrayList;
import org.mozilla.javascript.Callable;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.xml.XMLObject;
import org.mozilla.javascript.xmlimpl.XmlNode;

/* loaded from: classes5.dex */
class XMLList extends XMLObjectImpl implements Function {
    static final long serialVersionUID = -4543618751670781135L;
    private XmlNode.InternalList _annos;
    private XMLObjectImpl targetObject;
    private XmlNode.QName targetProperty;

    XMLList(XMLLibImpl xMLLibImpl, Scriptable scriptable, XMLObject xMLObject) {
        super(xMLLibImpl, scriptable, xMLObject);
        this.targetObject = null;
        this.targetProperty = null;
        this._annos = new XmlNode.InternalList();
    }

    private Object applyOrCall(boolean z2, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        String str = z2 ? "apply" : NotificationCompat.CATEGORY_CALL;
        if (!(scriptable2 instanceof XMLList) || ((XMLList) scriptable2).targetProperty == null) {
            throw ScriptRuntime.typeError1("msg.isnt.function", str);
        }
        return ScriptRuntime.applyOrCall(z2, context, scriptable, scriptable2, objArr);
    }

    private XMLList getPropertyList(XMLName xMLName) {
        XMLList xMLListNewXMLList = newXMLList();
        xMLListNewXMLList.setTargets(this, (xMLName.isDescendants() || xMLName.isAttributeName()) ? null : xMLName.toQname());
        for (int i2 = 0; i2 < length(); i2++) {
            xMLListNewXMLList.addToList(getXmlFromAnnotation(i2).getPropertyList(xMLName));
        }
        return xMLListNewXMLList;
    }

    private XML getXmlFromAnnotation(int i2) {
        return getXML(this._annos, i2);
    }

    private void insert(int i2, XML xml) {
        if (i2 < length()) {
            XmlNode.InternalList internalList = new XmlNode.InternalList();
            internalList.add(this._annos, 0, i2);
            internalList.add(xml);
            internalList.add(this._annos, i2, length());
            this._annos = internalList;
        }
    }

    private void internalRemoveFromList(int i2) {
        this._annos.remove(i2);
    }

    private void replaceNode(XML xml, XML xml2) {
        xml.replaceWith(xml2);
    }

    private void setAttribute(XMLName xMLName, Object obj) {
        for (int i2 = 0; i2 < length(); i2++) {
            getXmlFromAnnotation(i2).setAttribute(xMLName, obj);
        }
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    void addMatches(XMLList xMLList, XMLName xMLName) {
        for (int i2 = 0; i2 < length(); i2++) {
            getXmlFromAnnotation(i2).addMatches(xMLList, xMLName);
        }
    }

    void addToList(Object obj) {
        this._annos.addToList(obj);
    }

    @Override // org.mozilla.javascript.Function, org.mozilla.javascript.Callable
    public Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Object functionProperty;
        Scriptable scriptable3;
        XMLObject xMLObject;
        XmlNode.QName qName = this.targetProperty;
        if (qName == null) {
            throw ScriptRuntime.notFunctionError(this);
        }
        String localName = qName.getLocalName();
        boolean zEquals = localName.equals("apply");
        if (zEquals || localName.equals(NotificationCompat.CATEGORY_CALL)) {
            return applyOrCall(zEquals, context, scriptable, scriptable2, objArr);
        }
        if (!(scriptable2 instanceof XMLObject)) {
            throw ScriptRuntime.typeError1("msg.incompat.call", localName);
        }
        Object property = null;
        loop0: while (true) {
            functionProperty = property;
            do {
                scriptable3 = scriptable2;
                while ((scriptable2 instanceof XMLObject) && (functionProperty = (xMLObject = (XMLObject) scriptable2).getFunctionProperty(context, localName)) == Scriptable.NOT_FOUND) {
                    scriptable2 = xMLObject.getExtraMethodSource(context);
                    if (scriptable2 != null) {
                    }
                }
                break loop0;
            } while (scriptable2 instanceof XMLObject);
            property = ScriptableObject.getProperty(scriptable2, localName);
        }
        if (functionProperty instanceof Callable) {
            return ((Callable) functionProperty).call(context, scriptable, scriptable3, objArr);
        }
        throw ScriptRuntime.notFunctionError(scriptable3, functionProperty, localName);
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    XMLList child(int i2) {
        XMLList xMLListNewXMLList = newXMLList();
        for (int i3 = 0; i3 < length(); i3++) {
            xMLListNewXMLList.addToList(getXmlFromAnnotation(i3).child(i2));
        }
        return xMLListNewXMLList;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    XMLList children() {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < length(); i2++) {
            XML xmlFromAnnotation = getXmlFromAnnotation(i2);
            if (xmlFromAnnotation != null) {
                XMLList xMLListChildren = xmlFromAnnotation.children();
                int length = xMLListChildren.length();
                for (int i3 = 0; i3 < length; i3++) {
                    arrayList.add(xMLListChildren.item(i3));
                }
            }
        }
        XMLList xMLListNewXMLList = newXMLList();
        int size = arrayList.size();
        for (int i4 = 0; i4 < size; i4++) {
            xMLListNewXMLList.addToList(arrayList.get(i4));
        }
        return xMLListNewXMLList;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    XMLList comments() {
        XMLList xMLListNewXMLList = newXMLList();
        for (int i2 = 0; i2 < length(); i2++) {
            xMLListNewXMLList.addToList(getXmlFromAnnotation(i2).comments());
        }
        return xMLListNewXMLList;
    }

    @Override // org.mozilla.javascript.Function
    public Scriptable construct(Context context, Scriptable scriptable, Object[] objArr) {
        throw ScriptRuntime.typeError1("msg.not.ctor", "XMLList");
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    boolean contains(Object obj) {
        for (int i2 = 0; i2 < length(); i2++) {
            if (getXmlFromAnnotation(i2).equivalentXml(obj)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    XMLObjectImpl copy() {
        XMLList xMLListNewXMLList = newXMLList();
        for (int i2 = 0; i2 < length(); i2++) {
            xMLListNewXMLList.addToList(getXmlFromAnnotation(i2).copy());
        }
        return xMLListNewXMLList;
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public void delete(int i2) {
        if (i2 < 0 || i2 >= length()) {
            return;
        }
        getXmlFromAnnotation(i2).remove();
        internalRemoveFromList(i2);
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    void deleteXMLProperty(XMLName xMLName) {
        for (int i2 = 0; i2 < length(); i2++) {
            XML xmlFromAnnotation = getXmlFromAnnotation(i2);
            if (xmlFromAnnotation.isElement()) {
                xmlFromAnnotation.deleteXMLProperty(xMLName);
            }
        }
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    XMLList elements(XMLName xMLName) {
        XMLList xMLListNewXMLList = newXMLList();
        for (int i2 = 0; i2 < length(); i2++) {
            xMLListNewXMLList.addToList(getXmlFromAnnotation(i2).elements(xMLName));
        }
        return xMLListNewXMLList;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    boolean equivalentXml(Object obj) {
        if ((obj instanceof Undefined) && length() == 0) {
            return true;
        }
        if (length() == 1) {
            return getXmlFromAnnotation(0).equivalentXml(obj);
        }
        if (obj instanceof XMLList) {
            XMLList xMLList = (XMLList) obj;
            if (xMLList.length() == length()) {
                for (int i2 = 0; i2 < length(); i2++) {
                    if (getXmlFromAnnotation(i2).equivalentXml(xMLList.getXmlFromAnnotation(i2))) {
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public Object get(int i2, Scriptable scriptable) {
        return (i2 < 0 || i2 >= length()) ? Scriptable.NOT_FOUND : getXmlFromAnnotation(i2);
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return "XMLList";
    }

    @Override // org.mozilla.javascript.xml.XMLObject
    public Scriptable getExtraMethodSource(Context context) {
        if (length() == 1) {
            return getXmlFromAnnotation(0);
        }
        return null;
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public Object[] getIds() {
        if (isPrototype()) {
            return new Object[0];
        }
        int length = length();
        Object[] objArr = new Object[length];
        for (int i2 = 0; i2 < length; i2++) {
            objArr[i2] = Integer.valueOf(i2);
        }
        return objArr;
    }

    public Object[] getIdsForDebug() {
        return getIds();
    }

    XmlNode.InternalList getNodeList() {
        return this._annos;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    XML getXML() {
        if (length() == 1) {
            return getXmlFromAnnotation(0);
        }
        return null;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    Object getXMLProperty(XMLName xMLName) {
        return getPropertyList(xMLName);
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public boolean has(int i2, Scriptable scriptable) {
        return i2 >= 0 && i2 < length();
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    boolean hasComplexContent() {
        int length = length();
        if (length == 0) {
            return false;
        }
        if (length == 1) {
            return getXmlFromAnnotation(0).hasComplexContent();
        }
        for (int i2 = 0; i2 < length; i2++) {
            if (getXmlFromAnnotation(i2).isElement()) {
                return true;
            }
        }
        return false;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    boolean hasOwnProperty(XMLName xMLName) {
        return isPrototype() ? findPrototypeId(xMLName.localName()) != 0 : getPropertyList(xMLName).length() > 0;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    boolean hasSimpleContent() {
        if (length() == 0) {
            return true;
        }
        if (length() == 1) {
            return getXmlFromAnnotation(0).hasSimpleContent();
        }
        for (int i2 = 0; i2 < length(); i2++) {
            if (getXmlFromAnnotation(i2).isElement()) {
                return false;
            }
        }
        return true;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    boolean hasXMLProperty(XMLName xMLName) {
        return getPropertyList(xMLName).length() > 0;
    }

    XML item(int i2) {
        return this._annos != null ? getXmlFromAnnotation(i2) : createEmptyXML();
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    protected Object jsConstructor(Context context, boolean z2, Object[] objArr) {
        if (objArr.length == 0) {
            return newXMLList();
        }
        Object obj = objArr[0];
        return (z2 || !(obj instanceof XMLList)) ? newXMLListFrom(obj) : obj;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    int length() {
        XmlNode.InternalList internalList = this._annos;
        if (internalList != null) {
            return internalList.length();
        }
        return 0;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    void normalize() {
        for (int i2 = 0; i2 < length(); i2++) {
            getXmlFromAnnotation(i2).normalize();
        }
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    Object parent() {
        if (length() == 0) {
            return Undefined.instance;
        }
        XML xml = null;
        for (int i2 = 0; i2 < length(); i2++) {
            Object objParent = getXmlFromAnnotation(i2).parent();
            if (!(objParent instanceof XML)) {
                return Undefined.instance;
            }
            XML xml2 = (XML) objParent;
            if (i2 == 0) {
                xml = xml2;
            } else if (!xml.is(xml2)) {
                return Undefined.instance;
            }
        }
        return xml;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    XMLList processingInstructions(XMLName xMLName) {
        XMLList xMLListNewXMLList = newXMLList();
        for (int i2 = 0; i2 < length(); i2++) {
            xMLListNewXMLList.addToList(getXmlFromAnnotation(i2).processingInstructions(xMLName));
        }
        return xMLListNewXMLList;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    boolean propertyIsEnumerable(Object obj) {
        long jTestUint32String;
        if (obj instanceof Integer) {
            jTestUint32String = ((Integer) obj).intValue();
        } else if (obj instanceof Number) {
            double dDoubleValue = ((Number) obj).doubleValue();
            long j2 = (long) dDoubleValue;
            if (j2 != dDoubleValue) {
                return false;
            }
            if (j2 == 0 && 1.0d / dDoubleValue < 0.0d) {
                return false;
            }
            jTestUint32String = j2;
        } else {
            jTestUint32String = ScriptRuntime.testUint32String(ScriptRuntime.toString(obj));
        }
        return 0 <= jTestUint32String && jTestUint32String < ((long) length());
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public void put(int i2, Scriptable scriptable, Object obj) {
        Object objNewXMLFromJs;
        XMLObjectImpl xMLObjectImpl;
        Object obj2 = Undefined.instance;
        if (obj == null) {
            obj = TmpConstant.GROUP_ROLE_UNKNOWN;
        } else if (obj instanceof Undefined) {
            obj = "undefined";
        }
        if (obj instanceof XMLObject) {
            objNewXMLFromJs = (XMLObject) obj;
        } else if (this.targetProperty == null) {
            objNewXMLFromJs = newXMLFromJs(obj.toString());
        } else {
            XMLObjectImpl xMLObjectImplItem = item(i2);
            if (xMLObjectImplItem == null) {
                XML xmlItem = item(0);
                xMLObjectImplItem = xmlItem == null ? newTextElementXML(null, this.targetProperty, null) : xmlItem.copy();
            }
            ((XML) xMLObjectImplItem).setChildren(obj);
            objNewXMLFromJs = xMLObjectImplItem;
        }
        Object objParent = i2 < length() ? item(i2).parent() : (length() != 0 || (xMLObjectImpl = this.targetObject) == null) ? parent() : xMLObjectImpl.getXML();
        if (!(objParent instanceof XML)) {
            if (i2 >= length()) {
                addToList(objNewXMLFromJs);
                return;
            }
            XML xml = getXML(this._annos, i2);
            if (objNewXMLFromJs instanceof XML) {
                replaceNode(xml, (XML) objNewXMLFromJs);
                replace(i2, xml);
                return;
            }
            if (objNewXMLFromJs instanceof XMLList) {
                XMLList xMLList = (XMLList) objNewXMLFromJs;
                if (xMLList.length() > 0) {
                    replaceNode(xml, xMLList.item(0));
                    replace(i2, xMLList.item(0));
                    for (int i3 = 1; i3 < xMLList.length(); i3++) {
                        insert(i2 + i3, xMLList.item(i3));
                    }
                    return;
                }
                return;
            }
            return;
        }
        XML xml2 = (XML) objParent;
        if (i2 >= length()) {
            xml2.appendChild(objNewXMLFromJs);
            addToList(xml2.getLastXmlChild());
            return;
        }
        XML xmlFromAnnotation = getXmlFromAnnotation(i2);
        if (objNewXMLFromJs instanceof XML) {
            replaceNode(xmlFromAnnotation, (XML) objNewXMLFromJs);
            replace(i2, xmlFromAnnotation);
            return;
        }
        if (objNewXMLFromJs instanceof XMLList) {
            XMLList xMLList2 = (XMLList) objNewXMLFromJs;
            if (xMLList2.length() > 0) {
                int iChildIndex = xmlFromAnnotation.childIndex();
                replaceNode(xmlFromAnnotation, xMLList2.item(0));
                replace(i2, xMLList2.item(0));
                for (int i4 = 1; i4 < xMLList2.length(); i4++) {
                    xml2.insertChildAfter(xml2.getXmlChild(iChildIndex), xMLList2.item(i4));
                    iChildIndex++;
                    insert(i2 + i4, xMLList2.item(i4));
                }
            }
        }
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    void putXMLProperty(XMLName xMLName, Object obj) {
        XmlNode.QName qName;
        if (obj == null) {
            obj = TmpConstant.GROUP_ROLE_UNKNOWN;
        } else if (obj instanceof Undefined) {
            obj = "undefined";
        }
        if (length() > 1) {
            throw ScriptRuntime.typeError("Assignment to lists with more than one item is not supported");
        }
        if (length() != 0) {
            if (xMLName.isAttributeName()) {
                setAttribute(xMLName, obj);
                return;
            } else {
                item(0).putXMLProperty(xMLName, obj);
                replace(0, item(0));
                return;
            }
        }
        if (this.targetObject == null || (qName = this.targetProperty) == null || qName.getLocalName() == null || this.targetProperty.getLocalName().length() <= 0) {
            throw ScriptRuntime.typeError("Assignment to empty XMLList without targets not supported");
        }
        addToList(newTextElementXML(null, this.targetProperty, null));
        if (xMLName.isAttributeName()) {
            setAttribute(xMLName, obj);
        } else {
            item(0).putXMLProperty(xMLName, obj);
            replace(0, item(0));
        }
        this.targetObject.putXMLProperty(XMLName.formProperty(this.targetProperty.getNamespace().getUri(), this.targetProperty.getLocalName()), this);
        replace(0, this.targetObject.getXML().getLastXmlChild());
    }

    void remove() {
        for (int length = length() - 1; length >= 0; length--) {
            XML xmlFromAnnotation = getXmlFromAnnotation(length);
            if (xmlFromAnnotation != null) {
                xmlFromAnnotation.remove();
                internalRemoveFromList(length);
            }
        }
    }

    void replace(int i2, XML xml) {
        if (i2 < length()) {
            XmlNode.InternalList internalList = new XmlNode.InternalList();
            internalList.add(this._annos, 0, i2);
            internalList.add(xml);
            internalList.add(this._annos, i2 + 1, length());
            this._annos = internalList;
        }
    }

    void setTargets(XMLObjectImpl xMLObjectImpl, XmlNode.QName qName) {
        this.targetObject = xMLObjectImpl;
        this.targetProperty = qName;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    XMLList text() {
        XMLList xMLListNewXMLList = newXMLList();
        for (int i2 = 0; i2 < length(); i2++) {
            xMLListNewXMLList.addToList(getXmlFromAnnotation(i2).text());
        }
        return xMLListNewXMLList;
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    String toSource(int i2) {
        return toXMLString();
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    public String toString() {
        if (!hasSimpleContent()) {
            return toXMLString();
        }
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < length(); i2++) {
            XML xmlFromAnnotation = getXmlFromAnnotation(i2);
            if (!xmlFromAnnotation.isComment() && !xmlFromAnnotation.isProcessingInstruction()) {
                sb.append(xmlFromAnnotation.toString());
            }
        }
        return sb.toString();
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    String toXMLString() {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < length(); i2++) {
            if (getProcessor().isPrettyPrinting() && i2 != 0) {
                sb.append('\n');
            }
            sb.append(getXmlFromAnnotation(i2).toXMLString());
        }
        return sb.toString();
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    Object valueOf() {
        return this;
    }

    private XML getXML(XmlNode.InternalList internalList, int i2) {
        if (i2 < 0 || i2 >= length()) {
            return null;
        }
        return xmlFromNode(internalList.item(i2));
    }

    @Override // org.mozilla.javascript.xmlimpl.XMLObjectImpl
    XMLList child(XMLName xMLName) {
        XMLList xMLListNewXMLList = newXMLList();
        for (int i2 = 0; i2 < length(); i2++) {
            xMLListNewXMLList.addToList(getXmlFromAnnotation(i2).child(xMLName));
        }
        return xMLListNewXMLList;
    }
}
