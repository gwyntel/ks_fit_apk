package org.mozilla.javascript.tools.shell;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.ErrorReporter;

/* loaded from: classes5.dex */
public class ShellContextFactory extends ContextFactory {
    private String characterEncoding;
    private ErrorReporter errorReporter;
    private boolean generatingDebug;
    private int optimizationLevel;
    private boolean strictMode;
    private boolean warningAsError;
    private int languageVersion = 180;
    private boolean allowReservedKeywords = true;

    public String getCharacterEncoding() {
        return this.characterEncoding;
    }

    @Override // org.mozilla.javascript.ContextFactory
    protected boolean hasFeature(Context context, int i2) {
        if (i2 == 3) {
            return this.allowReservedKeywords;
        }
        switch (i2) {
            case 8:
            case 9:
            case 11:
                return this.strictMode;
            case 10:
                return this.generatingDebug;
            case 12:
                return this.warningAsError;
            default:
                return super.hasFeature(context, i2);
        }
    }

    @Override // org.mozilla.javascript.ContextFactory
    protected void onContextCreated(Context context) {
        context.setLanguageVersion(this.languageVersion);
        context.setOptimizationLevel(this.optimizationLevel);
        ErrorReporter errorReporter = this.errorReporter;
        if (errorReporter != null) {
            context.setErrorReporter(errorReporter);
        }
        context.setGeneratingDebug(this.generatingDebug);
        super.onContextCreated(context);
    }

    public void setAllowReservedKeywords(boolean z2) {
        this.allowReservedKeywords = z2;
    }

    public void setCharacterEncoding(String str) {
        this.characterEncoding = str;
    }

    public void setErrorReporter(ErrorReporter errorReporter) {
        if (errorReporter == null) {
            throw new IllegalArgumentException();
        }
        this.errorReporter = errorReporter;
    }

    public void setGeneratingDebug(boolean z2) {
        this.generatingDebug = z2;
    }

    public void setLanguageVersion(int i2) {
        Context.checkLanguageVersion(i2);
        checkNotSealed();
        this.languageVersion = i2;
    }

    public void setOptimizationLevel(int i2) {
        Context.checkOptimizationLevel(i2);
        checkNotSealed();
        this.optimizationLevel = i2;
    }

    public void setStrictMode(boolean z2) {
        checkNotSealed();
        this.strictMode = z2;
    }

    public void setWarningAsError(boolean z2) {
        checkNotSealed();
        this.warningAsError = z2;
    }
}
