package org.mozilla.javascript.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.ast.ParseProblem;

/* loaded from: classes5.dex */
public class ErrorCollector implements IdeErrorReporter {
    private List<ParseProblem> errors = new ArrayList();

    @Override // org.mozilla.javascript.ErrorReporter
    public void error(String str, String str2, int i2, String str3, int i3) {
        throw new UnsupportedOperationException();
    }

    public List<ParseProblem> getErrors() {
        return this.errors;
    }

    @Override // org.mozilla.javascript.ErrorReporter
    public EvaluatorException runtimeError(String str, String str2, int i2, String str3, int i3) {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.errors.size() * 100);
        Iterator<ParseProblem> it = this.errors.iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override // org.mozilla.javascript.ErrorReporter
    public void warning(String str, String str2, int i2, String str3, int i3) {
        throw new UnsupportedOperationException();
    }

    @Override // org.mozilla.javascript.ast.IdeErrorReporter
    public void error(String str, String str2, int i2, int i3) {
        this.errors.add(new ParseProblem(ParseProblem.Type.Error, str, str2, i2, i3));
    }

    @Override // org.mozilla.javascript.ast.IdeErrorReporter
    public void warning(String str, String str2, int i2, int i3) {
        this.errors.add(new ParseProblem(ParseProblem.Type.Warning, str, str2, i2, i3));
    }
}
