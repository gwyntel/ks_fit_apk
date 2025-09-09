package io.flutter.plugin.editing;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStructure;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.view.inputmethod.EditorInfoCompat;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.DiskLruHelper;
import io.flutter.Log;
import io.flutter.embedding.android.KeyboardManager;
import io.flutter.embedding.engine.systemchannels.TextInputChannel;
import io.flutter.plugin.editing.ListenableEditingState;
import io.flutter.plugin.platform.PlatformViewsController;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class TextInputPlugin implements ListenableEditingState.EditingStateWatcher {
    private static final String TAG = "TextInputPlugin";

    @NonNull
    private final AutofillManager afm;

    @Nullable
    private SparseArray<TextInputChannel.Configuration> autofillConfiguration;

    @Nullable
    private TextInputChannel.Configuration configuration;
    private ImeSyncDeferringInsetsCallback imeSyncCallback;

    @NonNull
    private InputTarget inputTarget = new InputTarget(InputTarget.Type.NO_TARGET, 0);
    private boolean isInputConnectionLocked;

    @Nullable
    private Rect lastClientRect;

    @Nullable
    private InputConnection lastInputConnection;

    @NonNull
    private ListenableEditingState mEditable;

    @NonNull
    private final InputMethodManager mImm;
    private TextInputChannel.TextEditState mLastKnownFrameworkTextEditingState;
    private boolean mRestartInputPending;

    @NonNull
    private final View mView;

    @NonNull
    private PlatformViewsController platformViewsController;

    @NonNull
    private final TextInputChannel textInputChannel;

    private static class InputTarget {
        int id;

        @NonNull
        Type type;

        enum Type {
            NO_TARGET,
            FRAMEWORK_CLIENT,
            VIRTUAL_DISPLAY_PLATFORM_VIEW,
            PHYSICAL_DISPLAY_PLATFORM_VIEW
        }

        public InputTarget(@NonNull Type type, int i2) {
            this.type = type;
            this.id = i2;
        }
    }

    private interface MinMax {
        void inspect(double d2, double d3);
    }

    @SuppressLint({"NewApi"})
    public TextInputPlugin(@NonNull View view, @NonNull TextInputChannel textInputChannel, @NonNull PlatformViewsController platformViewsController) {
        this.mView = view;
        this.mEditable = new ListenableEditingState(null, view);
        this.mImm = (InputMethodManager) view.getContext().getSystemService("input_method");
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 26) {
            this.afm = com.facebook.internal.b.a(view.getContext().getSystemService(com.facebook.internal.a.a()));
        } else {
            this.afm = null;
        }
        if (i2 >= 30) {
            ImeSyncDeferringInsetsCallback imeSyncDeferringInsetsCallback = new ImeSyncDeferringInsetsCallback(view);
            this.imeSyncCallback = imeSyncDeferringInsetsCallback;
            imeSyncDeferringInsetsCallback.install();
        }
        this.textInputChannel = textInputChannel;
        textInputChannel.setTextInputMethodHandler(new TextInputChannel.TextInputMethodHandler() { // from class: io.flutter.plugin.editing.TextInputPlugin.1
            @Override // io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputMethodHandler
            public void clearClient() {
                TextInputPlugin.this.clearTextInputClient();
            }

            @Override // io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputMethodHandler
            public void finishAutofillContext(boolean z2) {
                if (Build.VERSION.SDK_INT < 26 || TextInputPlugin.this.afm == null) {
                    return;
                }
                if (z2) {
                    TextInputPlugin.this.afm.commit();
                } else {
                    TextInputPlugin.this.afm.cancel();
                }
            }

            @Override // io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputMethodHandler
            public void hide() {
                if (TextInputPlugin.this.inputTarget.type == InputTarget.Type.PHYSICAL_DISPLAY_PLATFORM_VIEW) {
                    TextInputPlugin.this.notifyViewExited();
                } else {
                    TextInputPlugin textInputPlugin = TextInputPlugin.this;
                    textInputPlugin.hideTextInput(textInputPlugin.mView);
                }
            }

            @Override // io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputMethodHandler
            public void requestAutofill() {
                TextInputPlugin.this.notifyViewEntered();
            }

            @Override // io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputMethodHandler
            public void sendAppPrivateCommand(String str, Bundle bundle) {
                TextInputPlugin.this.sendTextInputAppPrivateCommand(str, bundle);
            }

            @Override // io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputMethodHandler
            public void setClient(int i3, TextInputChannel.Configuration configuration) {
                TextInputPlugin.this.setTextInputClient(i3, configuration);
            }

            @Override // io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputMethodHandler
            public void setEditableSizeAndTransform(double d2, double d3, double[] dArr) {
                TextInputPlugin.this.saveEditableSizeAndTransform(d2, d3, dArr);
            }

            @Override // io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputMethodHandler
            public void setEditingState(TextInputChannel.TextEditState textEditState) {
                TextInputPlugin textInputPlugin = TextInputPlugin.this;
                textInputPlugin.setTextInputEditingState(textInputPlugin.mView, textEditState);
            }

            @Override // io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputMethodHandler
            public void setPlatformViewClient(int i3, boolean z2) {
                TextInputPlugin.this.setPlatformViewTextInputClient(i3, z2);
            }

            @Override // io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputMethodHandler
            public void show() {
                TextInputPlugin textInputPlugin = TextInputPlugin.this;
                textInputPlugin.showTextInput(textInputPlugin.mView);
            }
        });
        textInputChannel.requestExistingInputState();
        this.platformViewsController = platformViewsController;
        platformViewsController.attachTextInputPlugin(this);
    }

    private static boolean composingChanged(TextInputChannel.TextEditState textEditState, TextInputChannel.TextEditState textEditState2) {
        int i2 = textEditState.composingEnd - textEditState.composingStart;
        if (i2 != textEditState2.composingEnd - textEditState2.composingStart) {
            return true;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            if (textEditState.text.charAt(textEditState.composingStart + i3) != textEditState2.text.charAt(textEditState2.composingStart + i3)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideTextInput(View view) {
        notifyViewExited();
        this.mImm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x006d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int inputTypeFromTextInputType(io.flutter.embedding.engine.systemchannels.TextInputChannel.InputType r1, boolean r2, boolean r3, boolean r4, boolean r5, io.flutter.embedding.engine.systemchannels.TextInputChannel.TextCapitalization r6) {
        /*
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r5 = r1.type
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r0 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputType.DATETIME
            if (r5 != r0) goto L8
            r1 = 4
            return r1
        L8:
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r0 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputType.NUMBER
            if (r5 != r0) goto L1b
            boolean r2 = r1.isSigned
            if (r2 == 0) goto L13
            r2 = 4098(0x1002, float:5.743E-42)
            goto L14
        L13:
            r2 = 2
        L14:
            boolean r1 = r1.isDecimal
            if (r1 == 0) goto L1a
            r2 = r2 | 8192(0x2000, float:1.148E-41)
        L1a:
            return r2
        L1b:
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r1 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputType.PHONE
            if (r5 != r1) goto L21
            r1 = 3
            return r1
        L21:
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r1 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputType.NONE
            if (r5 != r1) goto L27
            r1 = 0
            return r1
        L27:
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r1 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputType.MULTILINE
            if (r5 != r1) goto L2f
            r1 = 131073(0x20001, float:1.83672E-40)
            goto L53
        L2f:
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r1 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputType.EMAIL_ADDRESS
            if (r5 != r1) goto L36
            r1 = 33
            goto L53
        L36:
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r1 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputType.URL
            if (r5 != r1) goto L3d
            r1 = 17
            goto L53
        L3d:
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r1 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputType.VISIBLE_PASSWORD
            if (r5 != r1) goto L44
            r1 = 145(0x91, float:2.03E-43)
            goto L53
        L44:
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r1 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputType.NAME
            if (r5 != r1) goto L4b
            r1 = 97
            goto L53
        L4b:
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r1 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputType.POSTAL_ADDRESS
            if (r5 != r1) goto L52
            r1 = 113(0x71, float:1.58E-43)
            goto L53
        L52:
            r1 = 1
        L53:
            if (r2 == 0) goto L5a
            r2 = 524416(0x80080, float:7.34863E-40)
        L58:
            r1 = r1 | r2
            goto L66
        L5a:
            if (r3 == 0) goto L60
            r2 = 32768(0x8000, float:4.5918E-41)
            r1 = r1 | r2
        L60:
            if (r4 != 0) goto L66
            r2 = 524432(0x80090, float:7.34886E-40)
            goto L58
        L66:
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextCapitalization r2 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextCapitalization.CHARACTERS
            if (r6 != r2) goto L6d
            r1 = r1 | 4096(0x1000, float:5.74E-42)
            goto L7a
        L6d:
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextCapitalization r2 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextCapitalization.WORDS
            if (r6 != r2) goto L74
            r1 = r1 | 8192(0x2000, float:1.148E-41)
            goto L7a
        L74:
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextCapitalization r2 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextCapitalization.SENTENCES
            if (r6 != r2) goto L7a
            r1 = r1 | 16384(0x4000, float:2.2959E-41)
        L7a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: io.flutter.plugin.editing.TextInputPlugin.inputTypeFromTextInputType(io.flutter.embedding.engine.systemchannels.TextInputChannel$InputType, boolean, boolean, boolean, boolean, io.flutter.embedding.engine.systemchannels.TextInputChannel$TextCapitalization):int");
    }

    private boolean needsAutofill() {
        return this.autofillConfiguration != null;
    }

    private void notifyValueChanged(String str) {
        if (Build.VERSION.SDK_INT < 26 || this.afm == null || !needsAutofill()) {
            return;
        }
        this.afm.notifyValueChanged(this.mView, this.configuration.autofill.uniqueIdentifier.hashCode(), AutofillValue.forText(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyViewEntered() {
        if (Build.VERSION.SDK_INT < 26 || this.afm == null || !needsAutofill()) {
            return;
        }
        String str = this.configuration.autofill.uniqueIdentifier;
        int[] iArr = new int[2];
        this.mView.getLocationOnScreen(iArr);
        Rect rect = new Rect(this.lastClientRect);
        rect.offset(iArr[0], iArr[1]);
        this.afm.notifyViewEntered(this.mView, str.hashCode(), rect);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyViewExited() {
        TextInputChannel.Configuration configuration;
        if (Build.VERSION.SDK_INT < 26 || this.afm == null || (configuration = this.configuration) == null || configuration.autofill == null || !needsAutofill()) {
            return;
        }
        this.afm.notifyViewExited(this.mView, this.configuration.autofill.uniqueIdentifier.hashCode());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveEditableSizeAndTransform(double d2, double d3, final double[] dArr) {
        final double[] dArr2 = new double[4];
        final boolean z2 = dArr[3] == 0.0d && dArr[7] == 0.0d && dArr[15] == 1.0d;
        double d4 = dArr[12];
        double d5 = dArr[15];
        double d6 = d4 / d5;
        dArr2[1] = d6;
        dArr2[0] = d6;
        double d7 = dArr[13] / d5;
        dArr2[3] = d7;
        dArr2[2] = d7;
        MinMax minMax = new MinMax() { // from class: io.flutter.plugin.editing.TextInputPlugin.2
            @Override // io.flutter.plugin.editing.TextInputPlugin.MinMax
            public void inspect(double d8, double d9) {
                double d10 = 1.0d;
                if (!z2) {
                    double[] dArr3 = dArr;
                    d10 = 1.0d / (((dArr3[3] * d8) + (dArr3[7] * d9)) + dArr3[15]);
                }
                double[] dArr4 = dArr;
                double d11 = ((dArr4[0] * d8) + (dArr4[4] * d9) + dArr4[12]) * d10;
                double d12 = ((dArr4[1] * d8) + (dArr4[5] * d9) + dArr4[13]) * d10;
                double[] dArr5 = dArr2;
                if (d11 < dArr5[0]) {
                    dArr5[0] = d11;
                } else if (d11 > dArr5[1]) {
                    dArr5[1] = d11;
                }
                if (d12 < dArr5[2]) {
                    dArr5[2] = d12;
                } else if (d12 > dArr5[3]) {
                    dArr5[3] = d12;
                }
            }
        };
        minMax.inspect(d2, 0.0d);
        minMax.inspect(d2, d3);
        minMax.inspect(0.0d, d3);
        double d8 = this.mView.getContext().getResources().getDisplayMetrics().density;
        this.lastClientRect = new Rect((int) (dArr2[0] * d8), (int) (dArr2[2] * d8), (int) Math.ceil(dArr2[1] * d8), (int) Math.ceil(dArr2[3] * d8));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPlatformViewTextInputClient(int i2, boolean z2) {
        if (!z2) {
            this.inputTarget = new InputTarget(InputTarget.Type.PHYSICAL_DISPLAY_PLATFORM_VIEW, i2);
            this.lastInputConnection = null;
        } else {
            this.mView.requestFocus();
            this.inputTarget = new InputTarget(InputTarget.Type.VIRTUAL_DISPLAY_PLATFORM_VIEW, i2);
            this.mImm.restartInput(this.mView);
            this.mRestartInputPending = false;
        }
    }

    private void updateAutofillConfigurationIfNeeded(TextInputChannel.Configuration configuration) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        if (configuration == null || configuration.autofill == null) {
            this.autofillConfiguration = null;
            return;
        }
        TextInputChannel.Configuration[] configurationArr = configuration.fields;
        SparseArray<TextInputChannel.Configuration> sparseArray = new SparseArray<>();
        this.autofillConfiguration = sparseArray;
        if (configurationArr == null) {
            sparseArray.put(configuration.autofill.uniqueIdentifier.hashCode(), configuration);
            return;
        }
        for (TextInputChannel.Configuration configuration2 : configurationArr) {
            TextInputChannel.Configuration.Autofill autofill = configuration2.autofill;
            if (autofill != null) {
                this.autofillConfiguration.put(autofill.uniqueIdentifier.hashCode(), configuration2);
                this.afm.notifyValueChanged(this.mView, autofill.uniqueIdentifier.hashCode(), AutofillValue.forText(autofill.editState.text));
            }
        }
    }

    public void autofill(@NonNull SparseArray<AutofillValue> sparseArray) {
        TextInputChannel.Configuration configuration;
        TextInputChannel.Configuration.Autofill autofill;
        TextInputChannel.Configuration.Autofill autofill2;
        if (Build.VERSION.SDK_INT < 26 || (configuration = this.configuration) == null || this.autofillConfiguration == null || (autofill = configuration.autofill) == null) {
            return;
        }
        HashMap<String, TextInputChannel.TextEditState> map = new HashMap<>();
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
            TextInputChannel.Configuration configuration2 = this.autofillConfiguration.get(sparseArray.keyAt(i2));
            if (configuration2 != null && (autofill2 = configuration2.autofill) != null) {
                String string = q.a(sparseArray.valueAt(i2)).getTextValue().toString();
                TextInputChannel.TextEditState textEditState = new TextInputChannel.TextEditState(string, string.length(), string.length(), -1, -1);
                if (autofill2.uniqueIdentifier.equals(autofill.uniqueIdentifier)) {
                    this.mEditable.setEditingState(textEditState);
                } else {
                    map.put(autofill2.uniqueIdentifier, textEditState);
                }
            }
        }
        this.textInputChannel.updateEditingStateWithTag(this.inputTarget.id, map);
    }

    public void clearPlatformViewClient(int i2) {
        InputTarget inputTarget = this.inputTarget;
        InputTarget.Type type = inputTarget.type;
        if ((type == InputTarget.Type.VIRTUAL_DISPLAY_PLATFORM_VIEW || type == InputTarget.Type.PHYSICAL_DISPLAY_PLATFORM_VIEW) && inputTarget.id == i2) {
            this.inputTarget = new InputTarget(InputTarget.Type.NO_TARGET, 0);
            notifyViewExited();
            this.mImm.hideSoftInputFromWindow(this.mView.getApplicationWindowToken(), 0);
            this.mImm.restartInput(this.mView);
            this.mRestartInputPending = false;
        }
    }

    @VisibleForTesting
    void clearTextInputClient() {
        if (this.inputTarget.type == InputTarget.Type.VIRTUAL_DISPLAY_PLATFORM_VIEW) {
            return;
        }
        this.mEditable.removeEditingStateListener(this);
        notifyViewExited();
        this.configuration = null;
        updateAutofillConfigurationIfNeeded(null);
        this.inputTarget = new InputTarget(InputTarget.Type.NO_TARGET, 0);
        unlockPlatformViewInputConnection();
        this.lastClientRect = null;
        this.mImm.restartInput(this.mView);
    }

    @Nullable
    public InputConnection createInputConnection(@NonNull View view, @NonNull KeyboardManager keyboardManager, @NonNull EditorInfo editorInfo) {
        InputTarget inputTarget = this.inputTarget;
        InputTarget.Type type = inputTarget.type;
        if (type == InputTarget.Type.NO_TARGET) {
            this.lastInputConnection = null;
            return null;
        }
        if (type == InputTarget.Type.PHYSICAL_DISPLAY_PLATFORM_VIEW) {
            return null;
        }
        if (type == InputTarget.Type.VIRTUAL_DISPLAY_PLATFORM_VIEW) {
            if (this.isInputConnectionLocked) {
                return this.lastInputConnection;
            }
            InputConnection inputConnectionOnCreateInputConnection = this.platformViewsController.getPlatformViewById(inputTarget.id).onCreateInputConnection(editorInfo);
            this.lastInputConnection = inputConnectionOnCreateInputConnection;
            return inputConnectionOnCreateInputConnection;
        }
        TextInputChannel.Configuration configuration = this.configuration;
        int iInputTypeFromTextInputType = inputTypeFromTextInputType(configuration.inputType, configuration.obscureText, configuration.autocorrect, configuration.enableSuggestions, configuration.enableIMEPersonalizedLearning, configuration.textCapitalization);
        editorInfo.inputType = iInputTypeFromTextInputType;
        editorInfo.imeOptions = DiskLruHelper.DEFAULT_MAXSIZE;
        if (Build.VERSION.SDK_INT >= 26 && !this.configuration.enableIMEPersonalizedLearning) {
            editorInfo.imeOptions = 33554432 | 16777216;
        }
        Integer num = this.configuration.inputAction;
        int iIntValue = num == null ? (iInputTypeFromTextInputType & 131072) != 0 ? 1 : 6 : num.intValue();
        TextInputChannel.Configuration configuration2 = this.configuration;
        String str = configuration2.actionLabel;
        if (str != null) {
            editorInfo.actionLabel = str;
            editorInfo.actionId = iIntValue;
        }
        editorInfo.imeOptions = iIntValue | editorInfo.imeOptions;
        String[] strArr = configuration2.contentCommitMimeTypes;
        if (strArr != null) {
            EditorInfoCompat.setContentMimeTypes(editorInfo, strArr);
        }
        InputConnectionAdaptor inputConnectionAdaptor = new InputConnectionAdaptor(view, this.inputTarget.id, this.textInputChannel, keyboardManager, this.mEditable, editorInfo);
        editorInfo.initialSelStart = this.mEditable.getSelectionStart();
        editorInfo.initialSelEnd = this.mEditable.getSelectionEnd();
        this.lastInputConnection = inputConnectionAdaptor;
        return inputConnectionAdaptor;
    }

    @SuppressLint({"NewApi"})
    public void destroy() {
        this.platformViewsController.detachTextInputPlugin();
        this.textInputChannel.setTextInputMethodHandler(null);
        notifyViewExited();
        this.mEditable.removeEditingStateListener(this);
        ImeSyncDeferringInsetsCallback imeSyncDeferringInsetsCallback = this.imeSyncCallback;
        if (imeSyncDeferringInsetsCallback != null) {
            imeSyncDeferringInsetsCallback.remove();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x004d, code lost:
    
        if (r7 == r1.composingEnd) goto L22;
     */
    @Override // io.flutter.plugin.editing.ListenableEditingState.EditingStateWatcher
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void didChangeEditingState(boolean r9, boolean r10, boolean r11) {
        /*
            r8 = this;
            if (r9 == 0) goto Lb
            io.flutter.plugin.editing.ListenableEditingState r9 = r8.mEditable
            java.lang.String r9 = r9.toString()
            r8.notifyValueChanged(r9)
        Lb:
            io.flutter.plugin.editing.ListenableEditingState r9 = r8.mEditable
            int r9 = r9.getSelectionStart()
            io.flutter.plugin.editing.ListenableEditingState r10 = r8.mEditable
            int r10 = r10.getSelectionEnd()
            io.flutter.plugin.editing.ListenableEditingState r11 = r8.mEditable
            int r11 = r11.getComposingStart()
            io.flutter.plugin.editing.ListenableEditingState r0 = r8.mEditable
            int r7 = r0.getComposingEnd()
            io.flutter.plugin.editing.ListenableEditingState r0 = r8.mEditable
            java.util.ArrayList r0 = r0.extractBatchTextEditingDeltas()
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextEditState r1 = r8.mLastKnownFrameworkTextEditingState
            if (r1 == 0) goto La7
            io.flutter.plugin.editing.ListenableEditingState r1 = r8.mEditable
            java.lang.String r1 = r1.toString()
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextEditState r2 = r8.mLastKnownFrameworkTextEditingState
            java.lang.String r2 = r2.text
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L50
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextEditState r1 = r8.mLastKnownFrameworkTextEditingState
            int r2 = r1.selectionStart
            if (r9 != r2) goto L50
            int r2 = r1.selectionEnd
            if (r10 != r2) goto L50
            int r2 = r1.composingStart
            if (r11 != r2) goto L50
            int r1 = r1.composingEnd
            if (r7 != r1) goto L50
            goto La7
        L50:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "send EditingState to flutter: "
            r1.append(r2)
            io.flutter.plugin.editing.ListenableEditingState r2 = r8.mEditable
            java.lang.String r2 = r2.toString()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "TextInputPlugin"
            io.flutter.Log.v(r2, r1)
            io.flutter.embedding.engine.systemchannels.TextInputChannel$Configuration r1 = r8.configuration
            boolean r1 = r1.enableDeltaModel
            if (r1 == 0) goto L81
            io.flutter.embedding.engine.systemchannels.TextInputChannel r1 = r8.textInputChannel
            io.flutter.plugin.editing.TextInputPlugin$InputTarget r2 = r8.inputTarget
            int r2 = r2.id
            r1.updateEditingStateWithDeltas(r2, r0)
            io.flutter.plugin.editing.ListenableEditingState r0 = r8.mEditable
            r0.clearBatchDeltas()
            goto L94
        L81:
            io.flutter.embedding.engine.systemchannels.TextInputChannel r0 = r8.textInputChannel
            io.flutter.plugin.editing.TextInputPlugin$InputTarget r1 = r8.inputTarget
            int r1 = r1.id
            io.flutter.plugin.editing.ListenableEditingState r2 = r8.mEditable
            java.lang.String r2 = r2.toString()
            r3 = r9
            r4 = r10
            r5 = r11
            r6 = r7
            r0.updateEditingState(r1, r2, r3, r4, r5, r6)
        L94:
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextEditState r6 = new io.flutter.embedding.engine.systemchannels.TextInputChannel$TextEditState
            io.flutter.plugin.editing.ListenableEditingState r0 = r8.mEditable
            java.lang.String r1 = r0.toString()
            r0 = r6
            r2 = r9
            r3 = r10
            r4 = r11
            r5 = r7
            r0.<init>(r1, r2, r3, r4, r5)
            r8.mLastKnownFrameworkTextEditingState = r6
            goto Lac
        La7:
            io.flutter.plugin.editing.ListenableEditingState r9 = r8.mEditable
            r9.clearBatchDeltas()
        Lac:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.flutter.plugin.editing.TextInputPlugin.didChangeEditingState(boolean, boolean, boolean):void");
    }

    @VisibleForTesting
    Editable getEditable() {
        return this.mEditable;
    }

    @VisibleForTesting
    ImeSyncDeferringInsetsCallback getImeSyncCallback() {
        return this.imeSyncCallback;
    }

    @NonNull
    public InputMethodManager getInputMethodManager() {
        return this.mImm;
    }

    @Nullable
    public InputConnection getLastInputConnection() {
        return this.lastInputConnection;
    }

    public boolean handleKeyEvent(@NonNull KeyEvent keyEvent) {
        InputConnection inputConnection;
        if (!getInputMethodManager().isAcceptingText() || (inputConnection = this.lastInputConnection) == null) {
            return false;
        }
        return inputConnection instanceof InputConnectionAdaptor ? ((InputConnectionAdaptor) inputConnection).handleKeyEvent(keyEvent) : inputConnection.sendKeyEvent(keyEvent);
    }

    public void lockPlatformViewInputConnection() {
        if (this.inputTarget.type == InputTarget.Type.VIRTUAL_DISPLAY_PLATFORM_VIEW) {
            this.isInputConnectionLocked = true;
        }
    }

    public void onProvideAutofillVirtualStructure(@NonNull ViewStructure viewStructure, int i2) {
        Rect rect;
        if (Build.VERSION.SDK_INT < 26 || !needsAutofill()) {
            return;
        }
        String str = this.configuration.autofill.uniqueIdentifier;
        AutofillId autofillId = viewStructure.getAutofillId();
        for (int i3 = 0; i3 < this.autofillConfiguration.size(); i3++) {
            int iKeyAt = this.autofillConfiguration.keyAt(i3);
            TextInputChannel.Configuration.Autofill autofill = this.autofillConfiguration.valueAt(i3).autofill;
            if (autofill != null) {
                viewStructure.addChildCount(1);
                ViewStructure viewStructureNewChild = viewStructure.newChild(i3);
                viewStructureNewChild.setAutofillId(autofillId, iKeyAt);
                String[] strArr = autofill.hints;
                if (strArr.length > 0) {
                    viewStructureNewChild.setAutofillHints(strArr);
                }
                viewStructureNewChild.setAutofillType(1);
                viewStructureNewChild.setVisibility(0);
                String str2 = autofill.hintText;
                if (str2 != null) {
                    viewStructureNewChild.setHint(str2);
                }
                if (str.hashCode() != iKeyAt || (rect = this.lastClientRect) == null) {
                    viewStructureNewChild.setDimens(0, 0, 0, 0, 1, 1);
                    viewStructureNewChild.setAutofillValue(AutofillValue.forText(autofill.editState.text));
                } else {
                    viewStructureNewChild.setDimens(rect.left, rect.top, 0, 0, rect.width(), this.lastClientRect.height());
                    viewStructureNewChild.setAutofillValue(AutofillValue.forText(this.mEditable));
                }
            }
        }
    }

    public void sendTextInputAppPrivateCommand(@NonNull String str, @NonNull Bundle bundle) {
        this.mImm.sendAppPrivateCommand(this.mView, str, bundle);
    }

    @VisibleForTesting
    void setTextInputClient(int i2, TextInputChannel.Configuration configuration) {
        notifyViewExited();
        this.configuration = configuration;
        this.inputTarget = new InputTarget(InputTarget.Type.FRAMEWORK_CLIENT, i2);
        this.mEditable.removeEditingStateListener(this);
        TextInputChannel.Configuration.Autofill autofill = configuration.autofill;
        this.mEditable = new ListenableEditingState(autofill != null ? autofill.editState : null, this.mView);
        updateAutofillConfigurationIfNeeded(configuration);
        this.mRestartInputPending = true;
        unlockPlatformViewInputConnection();
        this.lastClientRect = null;
        this.mEditable.addEditingStateListener(this);
    }

    @VisibleForTesting
    void setTextInputEditingState(View view, TextInputChannel.TextEditState textEditState) {
        TextInputChannel.TextEditState textEditState2;
        if (!this.mRestartInputPending && (textEditState2 = this.mLastKnownFrameworkTextEditingState) != null && textEditState2.hasComposing()) {
            boolean zComposingChanged = composingChanged(this.mLastKnownFrameworkTextEditingState, textEditState);
            this.mRestartInputPending = zComposingChanged;
            if (zComposingChanged) {
                Log.i(TAG, "Composing region changed by the framework. Restarting the input method.");
            }
        }
        this.mLastKnownFrameworkTextEditingState = textEditState;
        this.mEditable.setEditingState(textEditState);
        if (this.mRestartInputPending) {
            this.mImm.restartInput(view);
            this.mRestartInputPending = false;
        }
    }

    @VisibleForTesting
    void showTextInput(View view) {
        TextInputChannel.InputType inputType;
        TextInputChannel.Configuration configuration = this.configuration;
        if (configuration != null && (inputType = configuration.inputType) != null && inputType.type == TextInputChannel.TextInputType.NONE) {
            hideTextInput(view);
        } else {
            view.requestFocus();
            this.mImm.showSoftInput(view, 0);
        }
    }

    public void unlockPlatformViewInputConnection() {
        if (this.inputTarget.type == InputTarget.Type.VIRTUAL_DISPLAY_PLATFORM_VIEW) {
            this.isInputConnectionLocked = false;
        }
    }
}
