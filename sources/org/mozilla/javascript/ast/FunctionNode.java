package org.mozilla.javascript.ast;

import com.alipay.sdk.m.u.i;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.mozilla.javascript.Node;

/* loaded from: classes5.dex */
public class FunctionNode extends ScriptNode {
    public static final int ARROW_FUNCTION = 4;
    public static final int FUNCTION_EXPRESSION = 2;
    public static final int FUNCTION_EXPRESSION_STATEMENT = 3;
    public static final int FUNCTION_STATEMENT = 1;
    private static final List<AstNode> NO_PARAMS = Collections.unmodifiableList(new ArrayList());
    private AstNode body;
    private Form functionForm;
    private Name functionName;
    private int functionType;
    private List<Node> generatorResumePoints;
    private boolean isExpressionClosure;
    private boolean isGenerator;
    private Map<Node, int[]> liveLocals;
    private int lp;
    private AstNode memberExprNode;
    private boolean needsActivation;
    private List<AstNode> params;
    private int rp;

    public enum Form {
        FUNCTION,
        GETTER,
        SETTER,
        METHOD
    }

    public FunctionNode() {
        this.functionForm = Form.FUNCTION;
        this.lp = -1;
        this.rp = -1;
        this.type = 110;
    }

    @Override // org.mozilla.javascript.ast.ScriptNode
    public int addFunction(FunctionNode functionNode) {
        int iAddFunction = super.addFunction(functionNode);
        if (getFunctionCount() > 0) {
            this.needsActivation = true;
        }
        return iAddFunction;
    }

    public void addLiveLocals(Node node, int[] iArr) {
        if (this.liveLocals == null) {
            this.liveLocals = new HashMap();
        }
        this.liveLocals.put(node, iArr);
    }

    public void addParam(AstNode astNode) {
        assertNotNull(astNode);
        if (this.params == null) {
            this.params = new ArrayList();
        }
        this.params.add(astNode);
        astNode.setParent(this);
    }

    public void addResumptionPoint(Node node) {
        if (this.generatorResumePoints == null) {
            this.generatorResumePoints = new ArrayList();
        }
        this.generatorResumePoints.add(node);
    }

    public AstNode getBody() {
        return this.body;
    }

    public Name getFunctionName() {
        return this.functionName;
    }

    public int getFunctionType() {
        return this.functionType;
    }

    public Map<Node, int[]> getLiveLocals() {
        return this.liveLocals;
    }

    public int getLp() {
        return this.lp;
    }

    public AstNode getMemberExprNode() {
        return this.memberExprNode;
    }

    public String getName() {
        Name name = this.functionName;
        return name != null ? name.getIdentifier() : "";
    }

    public List<AstNode> getParams() {
        List<AstNode> list = this.params;
        return list != null ? list : NO_PARAMS;
    }

    public List<Node> getResumptionPoints() {
        return this.generatorResumePoints;
    }

    public int getRp() {
        return this.rp;
    }

    public boolean isExpressionClosure() {
        return this.isExpressionClosure;
    }

    public boolean isGenerator() {
        return this.isGenerator;
    }

    public boolean isGetterMethod() {
        return this.functionForm == Form.GETTER;
    }

    public boolean isMethod() {
        Form form = this.functionForm;
        return form == Form.GETTER || form == Form.SETTER || form == Form.METHOD;
    }

    public boolean isNormalMethod() {
        return this.functionForm == Form.METHOD;
    }

    public boolean isParam(AstNode astNode) {
        List<AstNode> list = this.params;
        if (list == null) {
            return false;
        }
        return list.contains(astNode);
    }

    public boolean isSetterMethod() {
        return this.functionForm == Form.SETTER;
    }

    public boolean requiresActivation() {
        return this.needsActivation;
    }

    public void setBody(AstNode astNode) {
        assertNotNull(astNode);
        this.body = astNode;
        if (Boolean.TRUE.equals(astNode.getProp(25))) {
            setIsExpressionClosure(true);
        }
        int position = astNode.getPosition() + astNode.getLength();
        astNode.setParent(this);
        setLength(position - this.position);
        setEncodedSourceBounds(this.position, position);
    }

    public void setFunctionIsGetterMethod() {
        this.functionForm = Form.GETTER;
    }

    public void setFunctionIsNormalMethod() {
        this.functionForm = Form.METHOD;
    }

    public void setFunctionIsSetterMethod() {
        this.functionForm = Form.SETTER;
    }

    public void setFunctionName(Name name) {
        this.functionName = name;
        if (name != null) {
            name.setParent(this);
        }
    }

    public void setFunctionType(int i2) {
        this.functionType = i2;
    }

    public void setIsExpressionClosure(boolean z2) {
        this.isExpressionClosure = z2;
    }

    public void setIsGenerator() {
        this.isGenerator = true;
    }

    public void setLp(int i2) {
        this.lp = i2;
    }

    public void setMemberExprNode(AstNode astNode) {
        this.memberExprNode = astNode;
        if (astNode != null) {
            astNode.setParent(this);
        }
    }

    public void setParams(List<AstNode> list) {
        if (list == null) {
            this.params = null;
            return;
        }
        List<AstNode> list2 = this.params;
        if (list2 != null) {
            list2.clear();
        }
        Iterator<AstNode> it = list.iterator();
        while (it.hasNext()) {
            addParam(it.next());
        }
    }

    public void setParens(int i2, int i3) {
        this.lp = i2;
        this.rp = i3;
    }

    public void setRequiresActivation() {
        this.needsActivation = true;
    }

    public void setRp(int i2) {
        this.rp = i2;
    }

    @Override // org.mozilla.javascript.ast.Scope, org.mozilla.javascript.ast.Jump, org.mozilla.javascript.ast.AstNode
    public String toSource(int i2) {
        StringBuilder sb = new StringBuilder();
        boolean z2 = this.functionType == 4;
        if (!isMethod()) {
            sb.append(makeIndent(i2));
            if (!z2) {
                sb.append("function");
            }
        }
        if (this.functionName != null) {
            sb.append(" ");
            sb.append(this.functionName.toSource(0));
        }
        List<AstNode> list = this.params;
        if (list == null) {
            sb.append("() ");
        } else if (z2 && this.lp == -1) {
            printList(list, sb);
            sb.append(" ");
        } else {
            sb.append("(");
            printList(this.params, sb);
            sb.append(") ");
        }
        if (z2) {
            sb.append("=> ");
        }
        if (this.isExpressionClosure) {
            AstNode body = getBody();
            if (body.getLastChild() instanceof ReturnStatement) {
                sb.append(((ReturnStatement) body.getLastChild()).getReturnValue().toSource(0));
                if (this.functionType == 1) {
                    sb.append(i.f9802b);
                }
            } else {
                sb.append(" ");
                sb.append(body.toSource(0));
            }
        } else {
            sb.append(getBody().toSource(i2).trim());
        }
        if (this.functionType == 1 || isMethod()) {
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override // org.mozilla.javascript.ast.ScriptNode, org.mozilla.javascript.ast.Scope, org.mozilla.javascript.ast.Jump, org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        AstNode astNode;
        if (nodeVisitor.visit(this)) {
            Name name = this.functionName;
            if (name != null) {
                name.visit(nodeVisitor);
            }
            Iterator<AstNode> it = getParams().iterator();
            while (it.hasNext()) {
                it.next().visit(nodeVisitor);
            }
            getBody().visit(nodeVisitor);
            if (this.isExpressionClosure || (astNode = this.memberExprNode) == null) {
                return;
            }
            astNode.visit(nodeVisitor);
        }
    }

    public FunctionNode(int i2) {
        super(i2);
        this.functionForm = Form.FUNCTION;
        this.lp = -1;
        this.rp = -1;
        this.type = 110;
    }

    public FunctionNode(int i2, Name name) {
        super(i2);
        this.functionForm = Form.FUNCTION;
        this.lp = -1;
        this.rp = -1;
        this.type = 110;
        setFunctionName(name);
    }
}
