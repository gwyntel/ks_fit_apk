package org.mozilla.javascript.xmlimpl;

import androidx.webkit.ProxyConfig;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mozilla.javascript.Undefined;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;
import org.xml.sax.SAXException;

/* loaded from: classes5.dex */
class XmlNode implements Serializable {
    private static final boolean DOM_LEVEL_3 = true;
    private static final String USER_DATA_XMLNODE_KEY = "org.mozilla.javascript.xmlimpl.XmlNode";
    private static final String XML_NAMESPACES_NAMESPACE_URI = "http://www.w3.org/2000/xmlns/";
    private static final long serialVersionUID = 1;
    private Node dom;
    private UserDataHandler events = new XmlNodeUserDataHandler();
    private XML xml;

    static abstract class Filter {
        static final Filter COMMENT = new Filter() { // from class: org.mozilla.javascript.xmlimpl.XmlNode.Filter.1
            @Override // org.mozilla.javascript.xmlimpl.XmlNode.Filter
            boolean accept(Node node) {
                return node.getNodeType() == 8;
            }
        };
        static final Filter TEXT = new Filter() { // from class: org.mozilla.javascript.xmlimpl.XmlNode.Filter.2
            @Override // org.mozilla.javascript.xmlimpl.XmlNode.Filter
            boolean accept(Node node) {
                return node.getNodeType() == 3;
            }
        };
        static Filter ELEMENT = new Filter() { // from class: org.mozilla.javascript.xmlimpl.XmlNode.Filter.4
            @Override // org.mozilla.javascript.xmlimpl.XmlNode.Filter
            boolean accept(Node node) {
                return node.getNodeType() == 1;
            }
        };
        static Filter TRUE = new Filter() { // from class: org.mozilla.javascript.xmlimpl.XmlNode.Filter.5
            @Override // org.mozilla.javascript.xmlimpl.XmlNode.Filter
            boolean accept(Node node) {
                return true;
            }
        };

        Filter() {
        }

        static Filter PROCESSING_INSTRUCTION(final XMLName xMLName) {
            return new Filter() { // from class: org.mozilla.javascript.xmlimpl.XmlNode.Filter.3
                @Override // org.mozilla.javascript.xmlimpl.XmlNode.Filter
                boolean accept(Node node) {
                    if (node.getNodeType() == 7) {
                        return xMLName.matchesLocalName(((ProcessingInstruction) node).getTarget());
                    }
                    return false;
                }
            };
        }

        abstract boolean accept(Node node);
    }

    private static class Namespaces {
        private Map<String, String> map = new HashMap();
        private Map<String, String> uriToPrefix = new HashMap();

        Namespaces() {
        }

        void declare(Namespace namespace) {
            if (this.map.get(namespace.prefix) == null) {
                this.map.put(namespace.prefix, namespace.uri);
            }
            if (this.uriToPrefix.get(namespace.uri) == null) {
                this.uriToPrefix.put(namespace.uri, namespace.prefix);
            }
        }

        Namespace getNamespace(String str) {
            if (this.map.get(str) == null) {
                return null;
            }
            return Namespace.create(str, this.map.get(str));
        }

        Namespace getNamespaceByUri(String str) {
            if (this.uriToPrefix.get(str) == null) {
                return null;
            }
            return Namespace.create(str, this.uriToPrefix.get(str));
        }

        Namespace[] getNamespaces() {
            ArrayList arrayList = new ArrayList();
            for (String str : this.map.keySet()) {
                Namespace namespaceCreate = Namespace.create(str, this.map.get(str));
                if (!namespaceCreate.isEmpty()) {
                    arrayList.add(namespaceCreate);
                }
            }
            return (Namespace[]) arrayList.toArray(new Namespace[arrayList.size()]);
        }
    }

    static class QName implements Serializable {
        private static final long serialVersionUID = -6587069811691451077L;
        private String localName;
        private Namespace namespace;

        private QName() {
        }

        static QName create(Namespace namespace, String str) {
            if (str != null && str.equals(ProxyConfig.MATCH_ALL_SCHEMES)) {
                throw new RuntimeException("* is not valid localName");
            }
            QName qName = new QName();
            qName.namespace = namespace;
            qName.localName = str;
            return qName;
        }

        private boolean equals(String str, String str2) {
            if (str == null && str2 == null) {
                return true;
            }
            if (str == null || str2 == null) {
                return false;
            }
            return str.equals(str2);
        }

        private boolean namespacesEqual(Namespace namespace, Namespace namespace2) {
            if (namespace == null && namespace2 == null) {
                return true;
            }
            if (namespace == null || namespace2 == null) {
                return false;
            }
            return equals(namespace.getUri(), namespace2.getUri());
        }

        static String qualify(String str, String str2) {
            if (str == null) {
                throw new IllegalArgumentException("prefix must not be null");
            }
            if (str.length() <= 0) {
                return str2;
            }
            return str + ":" + str2;
        }

        String getLocalName() {
            return this.localName;
        }

        Namespace getNamespace() {
            return this.namespace;
        }

        public int hashCode() {
            String str = this.localName;
            if (str == null) {
                return 0;
            }
            return str.hashCode();
        }

        void lookupPrefix(Node node) throws DOMException {
            if (node == null) {
                throw new IllegalArgumentException("node must not be null");
            }
            String strLookupPrefix = node.lookupPrefix(this.namespace.getUri());
            if (strLookupPrefix == null) {
                String strLookupNamespaceURI = node.lookupNamespaceURI(null);
                if (strLookupNamespaceURI == null) {
                    strLookupNamespaceURI = "";
                }
                if (this.namespace.getUri().equals(strLookupNamespaceURI)) {
                    strLookupPrefix = "";
                }
            }
            int i2 = 0;
            while (strLookupPrefix == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("e4x_");
                int i3 = i2 + 1;
                sb.append(i2);
                String string = sb.toString();
                if (node.lookupNamespaceURI(string) == null) {
                    Node parentNode = node;
                    while (parentNode.getParentNode() != null && (parentNode.getParentNode() instanceof Element)) {
                        parentNode = parentNode.getParentNode();
                    }
                    ((Element) parentNode).setAttributeNS(XmlNode.XML_NAMESPACES_NAMESPACE_URI, "xmlns:" + string, this.namespace.getUri());
                    strLookupPrefix = string;
                }
                i2 = i3;
            }
            this.namespace.setPrefix(strLookupPrefix);
        }

        void setAttribute(Element element, String str) throws DOMException {
            if (this.namespace.getPrefix() == null) {
                lookupPrefix(element);
            }
            element.setAttributeNS(this.namespace.getUri(), qualify(this.namespace.getPrefix(), this.localName), str);
        }

        public String toString() {
            return "XmlNode.QName [" + this.localName + "," + this.namespace + "]";
        }

        final boolean equals(QName qName) {
            return namespacesEqual(this.namespace, qName.namespace) && equals(this.localName, qName.localName);
        }

        String qualify(Node node) throws DOMException {
            if (this.namespace.getPrefix() == null) {
                if (node != null) {
                    lookupPrefix(node);
                } else if (this.namespace.getUri().equals("")) {
                    this.namespace.setPrefix("");
                } else {
                    this.namespace.setPrefix("");
                }
            }
            return qualify(this.namespace.getPrefix(), this.localName);
        }

        public boolean equals(Object obj) {
            if (obj instanceof QName) {
                return equals((QName) obj);
            }
            return false;
        }

        @Deprecated
        static QName create(String str, String str2, String str3) {
            return create(Namespace.create(str3, str), str2);
        }
    }

    static class XmlNodeUserDataHandler implements UserDataHandler, Serializable {
        private static final long serialVersionUID = 4666895518900769588L;

        XmlNodeUserDataHandler() {
        }

        @Override // org.w3c.dom.UserDataHandler
        public void handle(short s2, String str, Object obj, Node node, Node node2) {
        }
    }

    private XmlNode() {
    }

    private void addNamespaces(Namespaces namespaces, Element element) {
        if (element == null) {
            throw new RuntimeException("element must not be null");
        }
        String uri = toUri(element.lookupNamespaceURI(null));
        if (!uri.equals(element.getParentNode() != null ? toUri(element.getParentNode().lookupNamespaceURI(null)) : "") || !(element.getParentNode() instanceof Element)) {
            namespaces.declare(Namespace.create("", uri));
        }
        NamedNodeMap attributes = element.getAttributes();
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            Attr attr = (Attr) attributes.item(i2);
            if (attr.getPrefix() != null && attr.getPrefix().equals("xmlns")) {
                namespaces.declare(Namespace.create(attr.getLocalName(), attr.getValue()));
            }
        }
    }

    private static XmlNode copy(XmlNode xmlNode) {
        return createImpl(xmlNode.dom.cloneNode(true));
    }

    static XmlNode createElement(XmlProcessor xmlProcessor, String str, String str2) throws SAXException {
        return createImpl(xmlProcessor.toXml(str, str2));
    }

    static XmlNode createElementFromNode(Node node) {
        if (node instanceof Document) {
            node = ((Document) node).getDocumentElement();
        }
        return createImpl(node);
    }

    static XmlNode createEmpty(XmlProcessor xmlProcessor) {
        return createText(xmlProcessor, "");
    }

    private static XmlNode createImpl(Node node) {
        if (node instanceof Document) {
            throw new IllegalArgumentException();
        }
        if (getUserData(node) != null) {
            return getUserData(node);
        }
        XmlNode xmlNode = new XmlNode();
        xmlNode.dom = node;
        setUserData(node, xmlNode);
        return xmlNode;
    }

    static XmlNode createText(XmlProcessor xmlProcessor, String str) {
        return createImpl(xmlProcessor.newDocument().createTextNode(str));
    }

    private void declareNamespace(Element element, String str, String str2) throws DOMException {
        if (str.length() <= 0) {
            element.setAttribute("xmlns", str2);
            return;
        }
        element.setAttributeNS(XML_NAMESPACES_NAMESPACE_URI, "xmlns:" + str, str2);
    }

    private Namespaces getAllNamespaces() {
        Namespaces namespaces = new Namespaces();
        Node parentNode = this.dom;
        if (parentNode instanceof Attr) {
            parentNode = ((Attr) parentNode).getOwnerElement();
        }
        while (parentNode != null) {
            if (parentNode instanceof Element) {
                addNamespaces(namespaces, (Element) parentNode);
            }
            parentNode = parentNode.getParentNode();
        }
        namespaces.declare(Namespace.create("", ""));
        return namespaces;
    }

    private Namespace getDefaultNamespace() {
        return Namespace.create("", this.dom.lookupNamespaceURI(null) == null ? "" : this.dom.lookupNamespaceURI(null));
    }

    private String getExistingPrefixFor(Namespace namespace) {
        return getDefaultNamespace().getUri().equals(namespace.getUri()) ? "" : this.dom.lookupPrefix(namespace.getUri());
    }

    private Namespace getNodeNamespace() {
        String namespaceURI = this.dom.getNamespaceURI();
        String prefix = this.dom.getPrefix();
        if (namespaceURI == null) {
            namespaceURI = "";
        }
        if (prefix == null) {
            prefix = "";
        }
        return Namespace.create(prefix, namespaceURI);
    }

    private static XmlNode getUserData(Node node) {
        return (XmlNode) node.getUserData(USER_DATA_XMLNODE_KEY);
    }

    static XmlNode newElementWithText(XmlProcessor xmlProcessor, XmlNode xmlNode, QName qName, String str) throws DOMException {
        if (xmlNode instanceof Document) {
            throw new IllegalArgumentException("Cannot use Document node as reference");
        }
        Document ownerDocument = xmlNode != null ? xmlNode.dom.getOwnerDocument() : xmlProcessor.newDocument();
        Node node = xmlNode != null ? xmlNode.dom : null;
        Namespace namespace = qName.getNamespace();
        Element elementCreateElementNS = (namespace == null || namespace.getUri().length() == 0) ? ownerDocument.createElementNS(null, qName.getLocalName()) : ownerDocument.createElementNS(namespace.getUri(), qName.qualify(node));
        if (str != null) {
            elementCreateElementNS.appendChild(ownerDocument.createTextNode(str));
        }
        return createImpl(elementCreateElementNS);
    }

    private void setProcessingInstructionName(String str) throws DOMException {
        ProcessingInstruction processingInstruction = (ProcessingInstruction) this.dom;
        processingInstruction.getParentNode().replaceChild(processingInstruction, processingInstruction.getOwnerDocument().createProcessingInstruction(str, processingInstruction.getData()));
    }

    private static void setUserData(Node node, XmlNode xmlNode) {
        node.setUserData(USER_DATA_XMLNODE_KEY, xmlNode, xmlNode.events);
    }

    private String toUri(String str) {
        return str == null ? "" : str;
    }

    void addMatchingChildren(XMLList xMLList, Filter filter) {
        NodeList childNodes = this.dom.getChildNodes();
        for (int i2 = 0; i2 < childNodes.getLength(); i2++) {
            Node nodeItem = childNodes.item(i2);
            XmlNode xmlNodeCreateImpl = createImpl(nodeItem);
            if (filter.accept(nodeItem)) {
                xMLList.addToList(xmlNodeCreateImpl);
            }
        }
    }

    String debug() {
        XmlProcessor xmlProcessor = new XmlProcessor();
        xmlProcessor.setIgnoreComments(false);
        xmlProcessor.setIgnoreProcessingInstructions(false);
        xmlProcessor.setIgnoreWhitespace(false);
        xmlProcessor.setPrettyPrinting(false);
        return xmlProcessor.ecmaToXmlString(this.dom);
    }

    void deleteMe() {
        Node node = this.dom;
        if (node instanceof Attr) {
            Attr attr = (Attr) node;
            attr.getOwnerElement().getAttributes().removeNamedItemNS(attr.getNamespaceURI(), attr.getLocalName());
        } else if (node.getParentNode() != null) {
            this.dom.getParentNode().removeChild(this.dom);
        }
    }

    String ecmaToXMLString(XmlProcessor xmlProcessor) throws DOMException {
        if (!isElementType()) {
            return xmlProcessor.ecmaToXmlString(this.dom);
        }
        Element element = (Element) this.dom.cloneNode(true);
        Namespace[] inScopeNamespaces = getInScopeNamespaces();
        for (int i2 = 0; i2 < inScopeNamespaces.length; i2++) {
            declareNamespace(element, inScopeNamespaces[i2].getPrefix(), inScopeNamespaces[i2].getUri());
        }
        return xmlProcessor.ecmaToXmlString(element);
    }

    String ecmaValue() {
        if (isTextType()) {
            return ((Text) this.dom).getData();
        }
        if (isAttributeType()) {
            return ((Attr) this.dom).getValue();
        }
        if (isProcessingInstructionType()) {
            return ((ProcessingInstruction) this.dom).getData();
        }
        if (isCommentType()) {
            return ((Comment) this.dom).getNodeValue();
        }
        if (isElementType()) {
            throw new RuntimeException("Unimplemented ecmaValue() for elements.");
        }
        throw new RuntimeException("Unimplemented for node " + this.dom);
    }

    String getAttributeValue() {
        return ((Attr) this.dom).getValue();
    }

    XmlNode[] getAttributes() {
        NamedNodeMap attributes = this.dom.getAttributes();
        if (attributes == null) {
            throw new IllegalStateException("Must be element.");
        }
        XmlNode[] xmlNodeArr = new XmlNode[attributes.getLength()];
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            xmlNodeArr[i2] = createImpl(attributes.item(i2));
        }
        return xmlNodeArr;
    }

    XmlNode getChild(int i2) {
        return createImpl(this.dom.getChildNodes().item(i2));
    }

    int getChildCount() {
        return this.dom.getChildNodes().getLength();
    }

    int getChildIndex() {
        if (isAttributeType() || parent() == null) {
            return -1;
        }
        NodeList childNodes = this.dom.getParentNode().getChildNodes();
        for (int i2 = 0; i2 < childNodes.getLength(); i2++) {
            if (childNodes.item(i2) == this.dom) {
                return i2;
            }
        }
        throw new RuntimeException("Unreachable.");
    }

    Namespace[] getInScopeNamespaces() {
        return getAllNamespaces().getNamespaces();
    }

    XmlNode[] getMatchingChildren(Filter filter) {
        ArrayList arrayList = new ArrayList();
        NodeList childNodes = this.dom.getChildNodes();
        for (int i2 = 0; i2 < childNodes.getLength(); i2++) {
            Node nodeItem = childNodes.item(i2);
            if (filter.accept(nodeItem)) {
                arrayList.add(createImpl(nodeItem));
            }
        }
        return (XmlNode[]) arrayList.toArray(new XmlNode[arrayList.size()]);
    }

    Namespace getNamespace() {
        return getNodeNamespace();
    }

    Namespace getNamespaceDeclaration(String str) {
        return (str.equals("") && (this.dom instanceof Attr)) ? Namespace.create("", "") : getAllNamespaces().getNamespace(str);
    }

    Namespace[] getNamespaceDeclarations() {
        if (!(this.dom instanceof Element)) {
            return new Namespace[0];
        }
        Namespaces namespaces = new Namespaces();
        addNamespaces(namespaces, (Element) this.dom);
        return namespaces.getNamespaces();
    }

    final QName getQname() {
        return QName.create(this.dom.getNamespaceURI() == null ? "" : this.dom.getNamespaceURI(), this.dom.getLocalName(), this.dom.getPrefix() != null ? this.dom.getPrefix() : "");
    }

    XML getXml() {
        return this.xml;
    }

    boolean hasChildElement() {
        NodeList childNodes = this.dom.getChildNodes();
        for (int i2 = 0; i2 < childNodes.getLength(); i2++) {
            if (childNodes.item(i2).getNodeType() == 1) {
                return true;
            }
        }
        return false;
    }

    void insertChildAt(int i2, XmlNode xmlNode) throws DOMException {
        Node node = this.dom;
        Node nodeImportNode = node.getOwnerDocument().importNode(xmlNode.dom, true);
        if (node.getChildNodes().getLength() >= i2) {
            if (node.getChildNodes().getLength() == i2) {
                node.appendChild(nodeImportNode);
                return;
            } else {
                node.insertBefore(nodeImportNode, node.getChildNodes().item(i2));
                return;
            }
        }
        throw new IllegalArgumentException("index=" + i2 + " length=" + node.getChildNodes().getLength());
    }

    void insertChildrenAt(int i2, XmlNode[] xmlNodeArr) {
        for (int i3 = 0; i3 < xmlNodeArr.length; i3++) {
            insertChildAt(i2 + i3, xmlNodeArr[i3]);
        }
    }

    void invalidateNamespacePrefix() {
        Node node = this.dom;
        if (!(node instanceof Element)) {
            throw new IllegalStateException();
        }
        String prefix = node.getPrefix();
        renameNode(QName.create(this.dom.getNamespaceURI(), this.dom.getLocalName(), null));
        NamedNodeMap attributes = this.dom.getAttributes();
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            if (attributes.item(i2).getPrefix().equals(prefix)) {
                createImpl(attributes.item(i2)).renameNode(QName.create(attributes.item(i2).getNamespaceURI(), attributes.item(i2).getLocalName(), null));
            }
        }
    }

    final boolean isAttributeType() {
        return this.dom.getNodeType() == 2;
    }

    final boolean isCommentType() {
        return this.dom.getNodeType() == 8;
    }

    final boolean isElementType() {
        return this.dom.getNodeType() == 1;
    }

    final boolean isParentType() {
        return isElementType();
    }

    final boolean isProcessingInstructionType() {
        return this.dom.getNodeType() == 7;
    }

    boolean isSameNode(XmlNode xmlNode) {
        return this.dom == xmlNode.dom;
    }

    final boolean isTextType() {
        return this.dom.getNodeType() == 3 || this.dom.getNodeType() == 4;
    }

    void normalize() {
        this.dom.normalize();
    }

    XmlNode parent() {
        Node parentNode = this.dom.getParentNode();
        if ((parentNode instanceof Document) || parentNode == null) {
            return null;
        }
        return createImpl(parentNode);
    }

    void removeChild(int i2) {
        Node node = this.dom;
        node.removeChild(node.getChildNodes().item(i2));
    }

    void removeNamespace(Namespace namespace) {
        if (namespace.is(getNodeNamespace())) {
            return;
        }
        NamedNodeMap attributes = this.dom.getAttributes();
        for (int i2 = 0; i2 < attributes.getLength(); i2++) {
            if (namespace.is(createImpl(attributes.item(i2)).getNodeNamespace())) {
                return;
            }
        }
        String existingPrefixFor = getExistingPrefixFor(namespace);
        if (existingPrefixFor != null) {
            if (namespace.isUnspecifiedPrefix()) {
                declareNamespace(existingPrefixFor, getDefaultNamespace().getUri());
            } else if (existingPrefixFor.equals(namespace.getPrefix())) {
                declareNamespace(existingPrefixFor, getDefaultNamespace().getUri());
            }
        }
    }

    final void renameNode(QName qName) {
        this.dom = this.dom.getOwnerDocument().renameNode(this.dom, qName.getNamespace().getUri(), qName.qualify(this.dom));
    }

    void replaceWith(XmlNode xmlNode) {
        Node nodeImportNode = xmlNode.dom;
        if (nodeImportNode.getOwnerDocument() != this.dom.getOwnerDocument()) {
            nodeImportNode = this.dom.getOwnerDocument().importNode(nodeImportNode, true);
        }
        this.dom.getParentNode().replaceChild(nodeImportNode, this.dom);
    }

    void setAttribute(QName qName, String str) {
        Node node = this.dom;
        if (!(node instanceof Element)) {
            throw new IllegalStateException("Can only set attribute on elements.");
        }
        qName.setAttribute((Element) node, str);
    }

    final void setLocalName(String str) throws DOMException {
        Node node = this.dom;
        if (node instanceof ProcessingInstruction) {
            setProcessingInstructionName(str);
            return;
        }
        String prefix = node.getPrefix();
        if (prefix == null) {
            prefix = "";
        }
        Document ownerDocument = this.dom.getOwnerDocument();
        Node node2 = this.dom;
        this.dom = ownerDocument.renameNode(node2, node2.getNamespaceURI(), QName.qualify(prefix, str));
    }

    void setXml(XML xml) {
        this.xml = xml;
    }

    Node toDomNode() {
        return this.dom;
    }

    public String toString() {
        return "XmlNode: type=" + ((int) this.dom.getNodeType()) + " dom=" + this.dom.toString();
    }

    String toXmlString(XmlProcessor xmlProcessor) {
        return xmlProcessor.ecmaToXmlString(this.dom);
    }

    static class InternalList implements Serializable {
        private static final long serialVersionUID = -3633151157292048978L;
        private List<XmlNode> list = new ArrayList();

        InternalList() {
        }

        private void _add(XmlNode xmlNode) {
            this.list.add(xmlNode);
        }

        void add(InternalList internalList) {
            for (int i2 = 0; i2 < internalList.length(); i2++) {
                _add(internalList.item(i2));
            }
        }

        void addToList(Object obj) {
            if (obj instanceof Undefined) {
                return;
            }
            if (obj instanceof XMLList) {
                XMLList xMLList = (XMLList) obj;
                for (int i2 = 0; i2 < xMLList.length(); i2++) {
                    _add(xMLList.item(i2).getAnnotation());
                }
                return;
            }
            if (obj instanceof XML) {
                _add(((XML) obj).getAnnotation());
            } else if (obj instanceof XmlNode) {
                _add((XmlNode) obj);
            }
        }

        XmlNode item(int i2) {
            return this.list.get(i2);
        }

        int length() {
            return this.list.size();
        }

        void remove(int i2) {
            this.list.remove(i2);
        }

        void add(InternalList internalList, int i2, int i3) {
            while (i2 < i3) {
                _add(internalList.item(i2));
                i2++;
            }
        }

        void add(XmlNode xmlNode) {
            _add(xmlNode);
        }

        void add(XML xml) {
            _add(xml.getAnnotation());
        }
    }

    final XmlNode copy() {
        return copy(this);
    }

    void declareNamespace(String str, String str2) {
        Node node = this.dom;
        if (node instanceof Element) {
            if (node.lookupNamespaceURI(str2) == null || !this.dom.lookupNamespaceURI(str2).equals(str)) {
                declareNamespace((Element) this.dom, str, str2);
                return;
            }
            return;
        }
        throw new IllegalStateException();
    }

    static class Namespace implements Serializable {
        static final Namespace GLOBAL = create("", "");
        private static final long serialVersionUID = 4073904386884677090L;
        private String prefix;
        private String uri;

        private Namespace() {
        }

        static Namespace create(String str, String str2) {
            if (str == null) {
                throw new IllegalArgumentException("Empty string represents default namespace prefix");
            }
            if (str2 == null) {
                throw new IllegalArgumentException("Namespace may not lack a URI");
            }
            Namespace namespace = new Namespace();
            namespace.prefix = str;
            namespace.uri = str2;
            return namespace;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPrefix(String str) {
            if (str == null) {
                throw new IllegalArgumentException();
            }
            this.prefix = str;
        }

        String getPrefix() {
            return this.prefix;
        }

        String getUri() {
            return this.uri;
        }

        boolean is(Namespace namespace) {
            String str;
            String str2 = this.prefix;
            return str2 != null && (str = namespace.prefix) != null && str2.equals(str) && this.uri.equals(namespace.uri);
        }

        boolean isDefault() {
            String str = this.prefix;
            return str != null && str.equals("");
        }

        boolean isEmpty() {
            String str = this.prefix;
            return str != null && str.equals("") && this.uri.equals("");
        }

        boolean isGlobal() {
            String str = this.uri;
            return str != null && str.equals("");
        }

        boolean isUnspecifiedPrefix() {
            return this.prefix == null;
        }

        public String toString() {
            if (this.prefix == null) {
                return "XmlNode.Namespace [" + this.uri + "]";
            }
            return "XmlNode.Namespace [" + this.prefix + "{" + this.uri + "}]";
        }

        static Namespace create(String str) {
            Namespace namespace = new Namespace();
            namespace.uri = str;
            if (str == null || str.length() == 0) {
                namespace.prefix = "";
            }
            return namespace;
        }
    }

    Namespace getNamespaceDeclaration() {
        return this.dom.getPrefix() == null ? getNamespaceDeclaration("") : getNamespaceDeclaration(this.dom.getPrefix());
    }
}
