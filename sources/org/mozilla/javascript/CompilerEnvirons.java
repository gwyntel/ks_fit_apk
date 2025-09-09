package org.mozilla.javascript;

import java.util.Set;
import org.mozilla.javascript.ast.ErrorCollector;

/* loaded from: classes5.dex */
public class CompilerEnvirons {
    Set<String> activationNames;
    private boolean ideMode;
    private boolean recordingComments;
    private boolean recordingLocalJsDocComments;
    private boolean recoverFromErrors;
    private boolean warnTrailingComma;
    private ErrorReporter errorReporter = DefaultErrorReporter.instance;
    private int languageVersion = 0;
    private boolean generateDebugInfo = true;
    private boolean reservedKeywordAsIdentifier = true;
    private boolean allowMemberExprAsFunctionName = false;
    private boolean xmlAvailable = true;
    private int optimizationLevel = 0;
    private boolean generatingSource = true;
    private boolean strictMode = false;
    private boolean warningAsError = false;
    private boolean generateObserverCount = false;
    private boolean allowSharpComments = false;

    public static CompilerEnvirons ideEnvirons() {
        CompilerEnvirons compilerEnvirons = new CompilerEnvirons();
        compilerEnvirons.setRecoverFromErrors(true);
        compilerEnvirons.setRecordingComments(true);
        compilerEnvirons.setStrictMode(true);
        compilerEnvirons.setWarnTrailingComma(true);
        compilerEnvirons.setLanguageVersion(170);
        compilerEnvirons.setReservedKeywordAsIdentifier(true);
        compilerEnvirons.setIdeMode(true);
        compilerEnvirons.setErrorReporter(new ErrorCollector());
        return compilerEnvirons;
    }

    public Set<String> getActivationNames() {
        return this.activationNames;
    }

    public boolean getAllowSharpComments() {
        return this.allowSharpComments;
    }

    public final ErrorReporter getErrorReporter() {
        return this.errorReporter;
    }

    public final int getLanguageVersion() {
        return this.languageVersion;
    }

    public final int getOptimizationLevel() {
        return this.optimizationLevel;
    }

    public boolean getWarnTrailingComma() {
        return this.warnTrailingComma;
    }

    public void initFromContext(Context context) {
        setErrorReporter(context.getErrorReporter());
        this.languageVersion = context.getLanguageVersion();
        this.generateDebugInfo = !context.isGeneratingDebugChanged() || context.isGeneratingDebug();
        this.reservedKeywordAsIdentifier = context.hasFeature(3);
        this.allowMemberExprAsFunctionName = context.hasFeature(2);
        this.strictMode = context.hasFeature(11);
        this.warningAsError = context.hasFeature(12);
        this.xmlAvailable = context.hasFeature(6);
        this.optimizationLevel = context.getOptimizationLevel();
        this.generatingSource = context.isGeneratingSource();
        this.activationNames = context.activationNames;
        this.generateObserverCount = context.generateObserverCount;
    }

    public final boolean isAllowMemberExprAsFunctionName() {
        return this.allowMemberExprAsFunctionName;
    }

    public final boolean isGenerateDebugInfo() {
        return this.generateDebugInfo;
    }

    public boolean isGenerateObserverCount() {
        return this.generateObserverCount;
    }

    public final boolean isGeneratingSource() {
        return this.generatingSource;
    }

    public boolean isIdeMode() {
        return this.ideMode;
    }

    public boolean isRecordingComments() {
        return this.recordingComments;
    }

    public boolean isRecordingLocalJsDocComments() {
        return this.recordingLocalJsDocComments;
    }

    public final boolean isReservedKeywordAsIdentifier() {
        return this.reservedKeywordAsIdentifier;
    }

    public final boolean isStrictMode() {
        return this.strictMode;
    }

    public final boolean isXmlAvailable() {
        return this.xmlAvailable;
    }

    public boolean recoverFromErrors() {
        return this.recoverFromErrors;
    }

    public final boolean reportWarningAsError() {
        return this.warningAsError;
    }

    public void setActivationNames(Set<String> set) {
        this.activationNames = set;
    }

    public void setAllowMemberExprAsFunctionName(boolean z2) {
        this.allowMemberExprAsFunctionName = z2;
    }

    public void setAllowSharpComments(boolean z2) {
        this.allowSharpComments = z2;
    }

    public void setErrorReporter(ErrorReporter errorReporter) {
        if (errorReporter == null) {
            throw new IllegalArgumentException();
        }
        this.errorReporter = errorReporter;
    }

    public void setGenerateDebugInfo(boolean z2) {
        this.generateDebugInfo = z2;
    }

    public void setGenerateObserverCount(boolean z2) {
        this.generateObserverCount = z2;
    }

    public void setGeneratingSource(boolean z2) {
        this.generatingSource = z2;
    }

    public void setIdeMode(boolean z2) {
        this.ideMode = z2;
    }

    public void setLanguageVersion(int i2) {
        Context.checkLanguageVersion(i2);
        this.languageVersion = i2;
    }

    public void setOptimizationLevel(int i2) {
        Context.checkOptimizationLevel(i2);
        this.optimizationLevel = i2;
    }

    public void setRecordingComments(boolean z2) {
        this.recordingComments = z2;
    }

    public void setRecordingLocalJsDocComments(boolean z2) {
        this.recordingLocalJsDocComments = z2;
    }

    public void setRecoverFromErrors(boolean z2) {
        this.recoverFromErrors = z2;
    }

    public void setReservedKeywordAsIdentifier(boolean z2) {
        this.reservedKeywordAsIdentifier = z2;
    }

    public void setStrictMode(boolean z2) {
        this.strictMode = z2;
    }

    public void setWarnTrailingComma(boolean z2) {
        this.warnTrailingComma = z2;
    }

    public void setXmlAvailable(boolean z2) {
        this.xmlAvailable = z2;
    }
}
