package org.mozilla.javascript.xmlimpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptRuntime;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/* loaded from: classes5.dex */
class XmlProcessor implements Serializable {
    private static final long serialVersionUID = 6903514433204808713L;
    private transient LinkedBlockingDeque<DocumentBuilder> documentBuilderPool;
    private transient DocumentBuilderFactory dom;
    private RhinoSAXErrorHandler errorHandler = new RhinoSAXErrorHandler();
    private boolean ignoreComments;
    private boolean ignoreProcessingInstructions;
    private boolean ignoreWhitespace;
    private int prettyIndent;
    private boolean prettyPrint;
    private transient TransformerFactory xform;

    private static class RhinoSAXErrorHandler implements ErrorHandler, Serializable {
        private static final long serialVersionUID = 6918417235413084055L;

        private RhinoSAXErrorHandler() {
        }

        private void throwError(SAXParseException sAXParseException) {
            throw ScriptRuntime.constructError("TypeError", sAXParseException.getMessage(), sAXParseException.getLineNumber() - 1);
        }

        @Override // org.xml.sax.ErrorHandler
        public void error(SAXParseException sAXParseException) {
            throwError(sAXParseException);
        }

        @Override // org.xml.sax.ErrorHandler
        public void fatalError(SAXParseException sAXParseException) {
            throwError(sAXParseException);
        }

        @Override // org.xml.sax.ErrorHandler
        public void warning(SAXParseException sAXParseException) {
            Context.reportWarning(sAXParseException.getMessage());
        }
    }

    XmlProcessor() {
        setDefault();
        DocumentBuilderFactory documentBuilderFactoryNewInstance = DocumentBuilderFactory.newInstance();
        this.dom = documentBuilderFactoryNewInstance;
        documentBuilderFactoryNewInstance.setNamespaceAware(true);
        this.dom.setIgnoringComments(false);
        this.xform = TransformerFactory.newInstance();
        this.documentBuilderPool = new LinkedBlockingDeque<>(Runtime.getRuntime().availableProcessors() * 2);
    }

    private void addCommentsTo(List<Node> list, Node node) {
        if (node instanceof Comment) {
            list.add(node);
        }
        if (node.getChildNodes() != null) {
            for (int i2 = 0; i2 < node.getChildNodes().getLength(); i2++) {
                addProcessingInstructionsTo(list, node.getChildNodes().item(i2));
            }
        }
    }

    private void addProcessingInstructionsTo(List<Node> list, Node node) {
        if (node instanceof ProcessingInstruction) {
            list.add(node);
        }
        if (node.getChildNodes() != null) {
            for (int i2 = 0; i2 < node.getChildNodes().getLength(); i2++) {
                addProcessingInstructionsTo(list, node.getChildNodes().item(i2));
            }
        }
    }

    private void addTextNodesToRemoveAndTrim(List<Node> list, Node node) throws DOMException {
        if (node instanceof Text) {
            Text text = (Text) node;
            text.setData(text.getData().trim());
            if (text.getData().length() == 0) {
                list.add(node);
            }
        }
        if (node.getChildNodes() != null) {
            for (int i2 = 0; i2 < node.getChildNodes().getLength(); i2++) {
                addTextNodesToRemoveAndTrim(list, node.getChildNodes().item(i2));
            }
        }
    }

    private void beautifyElement(Element element, int i2) throws DOMException {
        StringBuilder sb = new StringBuilder();
        sb.append('\n');
        for (int i3 = 0; i3 < i2; i3++) {
            sb.append(' ');
        }
        String string = sb.toString();
        for (int i4 = 0; i4 < this.prettyIndent; i4++) {
            sb.append(' ');
        }
        String string2 = sb.toString();
        ArrayList arrayList = new ArrayList();
        boolean z2 = false;
        for (int i5 = 0; i5 < element.getChildNodes().getLength(); i5++) {
            if (i5 == 1) {
                z2 = true;
            }
            if (element.getChildNodes().item(i5) instanceof Text) {
                arrayList.add(element.getChildNodes().item(i5));
            } else {
                arrayList.add(element.getChildNodes().item(i5));
                z2 = true;
            }
        }
        if (z2) {
            for (int i6 = 0; i6 < arrayList.size(); i6++) {
                element.insertBefore(element.getOwnerDocument().createTextNode(string2), (Node) arrayList.get(i6));
            }
        }
        NodeList childNodes = element.getChildNodes();
        ArrayList arrayList2 = new ArrayList();
        for (int i7 = 0; i7 < childNodes.getLength(); i7++) {
            if (childNodes.item(i7) instanceof Element) {
                arrayList2.add((Element) childNodes.item(i7));
            }
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            beautifyElement((Element) it.next(), this.prettyIndent + i2);
        }
        if (z2) {
            element.appendChild(element.getOwnerDocument().createTextNode(string));
        }
    }

    private String elementToXmlString(Element element) throws DOMException {
        Element element2 = (Element) element.cloneNode(true);
        if (this.prettyPrint) {
            beautifyElement(element2, 0);
        }
        return toString(element2);
    }

    private String escapeElementValue(String str) {
        return escapeTextValue(str);
    }

    private DocumentBuilder getDocumentBuilderFromPool() throws ParserConfigurationException {
        DocumentBuilder documentBuilderPollFirst = this.documentBuilderPool.pollFirst();
        if (documentBuilderPollFirst == null) {
            documentBuilderPollFirst = getDomFactory().newDocumentBuilder();
        }
        documentBuilderPollFirst.setErrorHandler(this.errorHandler);
        return documentBuilderPollFirst;
    }

    private DocumentBuilderFactory getDomFactory() {
        return this.dom;
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        DocumentBuilderFactory documentBuilderFactoryNewInstance = DocumentBuilderFactory.newInstance();
        this.dom = documentBuilderFactoryNewInstance;
        documentBuilderFactoryNewInstance.setNamespaceAware(true);
        this.dom.setIgnoringComments(false);
        this.xform = TransformerFactory.newInstance();
        this.documentBuilderPool = new LinkedBlockingDeque<>(Runtime.getRuntime().availableProcessors() * 2);
    }

    private void returnDocumentBuilderToPool(DocumentBuilder documentBuilder) {
        try {
            documentBuilder.reset();
            this.documentBuilderPool.offerFirst(documentBuilder);
        } catch (UnsupportedOperationException unused) {
        }
    }

    private String toString(Node node) throws TransformerException, IllegalArgumentException {
        DOMSource dOMSource = new DOMSource(node);
        StringWriter stringWriter = new StringWriter();
        StreamResult streamResult = new StreamResult(stringWriter);
        try {
            Transformer transformerNewTransformer = this.xform.newTransformer();
            transformerNewTransformer.setOutputProperty("omit-xml-declaration", "yes");
            transformerNewTransformer.setOutputProperty("indent", "no");
            transformerNewTransformer.setOutputProperty("method", "xml");
            transformerNewTransformer.transform(dOMSource, streamResult);
            return toXmlNewlines(stringWriter.toString());
        } catch (TransformerConfigurationException e2) {
            throw new RuntimeException(e2);
        } catch (TransformerException e3) {
            throw new RuntimeException(e3);
        }
    }

    private String toXmlNewlines(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (str.charAt(i2) != '\r') {
                sb.append(str.charAt(i2));
            } else if (str.charAt(i2 + 1) != '\n') {
                sb.append('\n');
            }
        }
        return sb.toString();
    }

    final String ecmaToXmlString(Node node) throws DOMException {
        StringBuilder sb = new StringBuilder();
        if (node instanceof Text) {
            String data = ((Text) node).getData();
            if (this.prettyPrint) {
                data = data.trim();
            }
            sb.append(escapeElementValue(data));
            return sb.toString();
        }
        if (node instanceof Attr) {
            sb.append(escapeAttributeValue(((Attr) node).getValue()));
            return sb.toString();
        }
        if (node instanceof Comment) {
            sb.append("<!--" + ((Comment) node).getNodeValue() + "-->");
            return sb.toString();
        }
        if (!(node instanceof ProcessingInstruction)) {
            sb.append(elementToXmlString((Element) node));
            return sb.toString();
        }
        ProcessingInstruction processingInstruction = (ProcessingInstruction) node;
        sb.append("<?" + processingInstruction.getTarget() + " " + processingInstruction.getData() + "?>");
        return sb.toString();
    }

    String escapeAttributeValue(Object obj) throws TransformerException, DOMException, IllegalArgumentException {
        String string = ScriptRuntime.toString(obj);
        if (string.length() == 0) {
            return "";
        }
        Element elementCreateElement = newDocument().createElement("a");
        elementCreateElement.setAttribute("b", string);
        String string2 = toString(elementCreateElement);
        return string2.substring(string2.indexOf(34) + 1, string2.lastIndexOf(34));
    }

    String escapeTextValue(Object obj) throws TransformerException, DOMException, IllegalArgumentException {
        if (obj instanceof XMLObjectImpl) {
            return ((XMLObjectImpl) obj).toXMLString();
        }
        String string = ScriptRuntime.toString(obj);
        if (string.length() == 0) {
            return string;
        }
        Element elementCreateElement = newDocument().createElement("a");
        elementCreateElement.setTextContent(string);
        String string2 = toString(elementCreateElement);
        int iIndexOf = string2.indexOf(62) + 1;
        int iLastIndexOf = string2.lastIndexOf(60);
        return iIndexOf < iLastIndexOf ? string2.substring(iIndexOf, iLastIndexOf) : "";
    }

    final int getPrettyIndent() {
        return this.prettyIndent;
    }

    final boolean isIgnoreComments() {
        return this.ignoreComments;
    }

    final boolean isIgnoreProcessingInstructions() {
        return this.ignoreProcessingInstructions;
    }

    final boolean isIgnoreWhitespace() {
        return this.ignoreWhitespace;
    }

    final boolean isPrettyPrinting() {
        return this.prettyPrint;
    }

    Document newDocument() {
        DocumentBuilder documentBuilderFromPool = null;
        try {
            try {
                documentBuilderFromPool = getDocumentBuilderFromPool();
                Document documentNewDocument = documentBuilderFromPool.newDocument();
                returnDocumentBuilderToPool(documentBuilderFromPool);
                return documentNewDocument;
            } catch (ParserConfigurationException e2) {
                throw new RuntimeException(e2);
            }
        } catch (Throwable th) {
            if (documentBuilderFromPool != null) {
                returnDocumentBuilderToPool(documentBuilderFromPool);
            }
            throw th;
        }
    }

    final void setDefault() {
        setIgnoreComments(true);
        setIgnoreProcessingInstructions(true);
        setIgnoreWhitespace(true);
        setPrettyPrinting(true);
        setPrettyIndent(2);
    }

    final void setIgnoreComments(boolean z2) {
        this.ignoreComments = z2;
    }

    final void setIgnoreProcessingInstructions(boolean z2) {
        this.ignoreProcessingInstructions = z2;
    }

    final void setIgnoreWhitespace(boolean z2) {
        this.ignoreWhitespace = z2;
    }

    final void setPrettyIndent(int i2) {
        this.prettyIndent = i2;
    }

    final void setPrettyPrinting(boolean z2) {
        this.prettyPrint = z2;
    }

    final Node toXml(String str, String str2) throws SAXException {
        try {
            try {
                try {
                    DocumentBuilder documentBuilderFromPool = getDocumentBuilderFromPool();
                    Document document = documentBuilderFromPool.parse(new InputSource(new StringReader("<parent xmlns=\"" + str + "\">" + str2 + "</parent>")));
                    if (this.ignoreProcessingInstructions) {
                        ArrayList arrayList = new ArrayList();
                        addProcessingInstructionsTo(arrayList, document);
                        for (Node node : arrayList) {
                            node.getParentNode().removeChild(node);
                        }
                    }
                    if (this.ignoreComments) {
                        ArrayList arrayList2 = new ArrayList();
                        addCommentsTo(arrayList2, document);
                        for (Node node2 : arrayList2) {
                            node2.getParentNode().removeChild(node2);
                        }
                    }
                    if (this.ignoreWhitespace) {
                        ArrayList arrayList3 = new ArrayList();
                        addTextNodesToRemoveAndTrim(arrayList3, document);
                        for (Node node3 : arrayList3) {
                            node3.getParentNode().removeChild(node3);
                        }
                    }
                    NodeList childNodes = document.getDocumentElement().getChildNodes();
                    if (childNodes.getLength() > 1) {
                        throw ScriptRuntime.constructError("SyntaxError", "XML objects may contain at most one node.");
                    }
                    if (childNodes.getLength() == 0) {
                        Text textCreateTextNode = document.createTextNode("");
                        returnDocumentBuilderToPool(documentBuilderFromPool);
                        return textCreateTextNode;
                    }
                    Node nodeItem = childNodes.item(0);
                    document.getDocumentElement().removeChild(nodeItem);
                    returnDocumentBuilderToPool(documentBuilderFromPool);
                    return nodeItem;
                } catch (IOException unused) {
                    throw new RuntimeException("Unreachable.");
                }
            } catch (ParserConfigurationException e2) {
                throw new RuntimeException(e2);
            }
        } catch (Throwable th) {
            if (0 != 0) {
                returnDocumentBuilderToPool(null);
            }
            throw th;
        }
    }
}
