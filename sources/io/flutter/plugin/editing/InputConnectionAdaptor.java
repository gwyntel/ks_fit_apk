package io.flutter.plugin.editing;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.DynamicLayout;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.text.TextPaint;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputContentInfo;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.facebook.share.internal.ShareConstants;
import io.flutter.Log;
import io.flutter.embedding.engine.FlutterJNI;
import io.flutter.embedding.engine.systemchannels.TextInputChannel;
import io.flutter.plugin.editing.ListenableEditingState;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class InputConnectionAdaptor extends BaseInputConnection implements ListenableEditingState.EditingStateWatcher {
    private static final String TAG = "InputConnectionAdaptor";
    private int batchEditNestDepth;
    private FlutterTextUtils flutterTextUtils;
    private final KeyboardDelegate keyboardDelegate;
    private final int mClient;
    private CursorAnchorInfo.Builder mCursorAnchorInfoBuilder;
    private final ListenableEditingState mEditable;
    private final EditorInfo mEditorInfo;
    private ExtractedTextRequest mExtractRequest;
    private ExtractedText mExtractedText;
    private final View mFlutterView;
    private InputMethodManager mImm;
    private final Layout mLayout;
    private boolean mMonitorCursorUpdate;
    private final TextInputChannel textInputChannel;

    public interface KeyboardDelegate {
        boolean handleEvent(@NonNull KeyEvent keyEvent);
    }

    public InputConnectionAdaptor(View view, int i2, TextInputChannel textInputChannel, KeyboardDelegate keyboardDelegate, ListenableEditingState listenableEditingState, EditorInfo editorInfo, FlutterJNI flutterJNI) {
        super(view, true);
        this.mMonitorCursorUpdate = false;
        this.mExtractedText = new ExtractedText();
        this.batchEditNestDepth = 0;
        this.mFlutterView = view;
        this.mClient = i2;
        this.textInputChannel = textInputChannel;
        this.mEditable = listenableEditingState;
        listenableEditingState.addEditingStateListener(this);
        this.mEditorInfo = editorInfo;
        this.keyboardDelegate = keyboardDelegate;
        this.flutterTextUtils = new FlutterTextUtils(flutterJNI);
        this.mLayout = new DynamicLayout(listenableEditingState, new TextPaint(), Integer.MAX_VALUE, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        this.mImm = (InputMethodManager) view.getContext().getSystemService("input_method");
    }

    private static int clampIndexToEditable(int i2, Editable editable) {
        int iMax = Math.max(0, Math.min(editable.length(), i2));
        if (iMax != i2) {
            Log.d("flutter", "Text selection index was clamped (" + i2 + "->" + iMax + ") to remain in bounds. This may not be your fault, as some keyboards may select outside of bounds.");
        }
        return iMax;
    }

    private boolean doPerformContextMenuAction(int i2) {
        if (i2 == 16908319) {
            setSelection(0, this.mEditable.length());
            return true;
        }
        if (i2 == 16908320) {
            int selectionStart = Selection.getSelectionStart(this.mEditable);
            int selectionEnd = Selection.getSelectionEnd(this.mEditable);
            if (selectionStart != selectionEnd) {
                int iMin = Math.min(selectionStart, selectionEnd);
                int iMax = Math.max(selectionStart, selectionEnd);
                ((ClipboardManager) this.mFlutterView.getContext().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("text label?", this.mEditable.subSequence(iMin, iMax)));
                this.mEditable.delete(iMin, iMax);
                setSelection(iMin, iMin);
            }
            return true;
        }
        if (i2 == 16908321) {
            int selectionStart2 = Selection.getSelectionStart(this.mEditable);
            int selectionEnd2 = Selection.getSelectionEnd(this.mEditable);
            if (selectionStart2 != selectionEnd2) {
                ((ClipboardManager) this.mFlutterView.getContext().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("text label?", this.mEditable.subSequence(Math.min(selectionStart2, selectionEnd2), Math.max(selectionStart2, selectionEnd2))));
            }
            return true;
        }
        if (i2 != 16908322) {
            return false;
        }
        ClipData primaryClip = ((ClipboardManager) this.mFlutterView.getContext().getSystemService("clipboard")).getPrimaryClip();
        if (primaryClip != null) {
            CharSequence charSequenceCoerceToText = primaryClip.getItemAt(0).coerceToText(this.mFlutterView.getContext());
            int iMax2 = Math.max(0, Selection.getSelectionStart(this.mEditable));
            int iMax3 = Math.max(0, Selection.getSelectionEnd(this.mEditable));
            int iMin2 = Math.min(iMax2, iMax3);
            int iMax4 = Math.max(iMax2, iMax3);
            if (iMin2 != iMax4) {
                this.mEditable.delete(iMin2, iMax4);
            }
            this.mEditable.insert(iMin2, charSequenceCoerceToText);
            int length = iMin2 + charSequenceCoerceToText.length();
            setSelection(length, length);
        }
        return true;
    }

    private CursorAnchorInfo getCursorAnchorInfo() {
        CursorAnchorInfo.Builder builder = this.mCursorAnchorInfoBuilder;
        if (builder == null) {
            this.mCursorAnchorInfoBuilder = new CursorAnchorInfo.Builder();
        } else {
            builder.reset();
        }
        this.mCursorAnchorInfoBuilder.setSelectionRange(this.mEditable.getSelectionStart(), this.mEditable.getSelectionEnd());
        int composingStart = this.mEditable.getComposingStart();
        int composingEnd = this.mEditable.getComposingEnd();
        if (composingStart < 0 || composingEnd <= composingStart) {
            this.mCursorAnchorInfoBuilder.setComposingText(-1, "");
        } else {
            this.mCursorAnchorInfoBuilder.setComposingText(composingStart, this.mEditable.toString().subSequence(composingStart, composingEnd));
        }
        return this.mCursorAnchorInfoBuilder.build();
    }

    private ExtractedText getExtractedText(ExtractedTextRequest extractedTextRequest) {
        ExtractedText extractedText = this.mExtractedText;
        extractedText.startOffset = 0;
        extractedText.partialStartOffset = -1;
        extractedText.partialEndOffset = -1;
        extractedText.selectionStart = this.mEditable.getSelectionStart();
        this.mExtractedText.selectionEnd = this.mEditable.getSelectionEnd();
        this.mExtractedText.text = (extractedTextRequest == null || (extractedTextRequest.flags & 1) == 0) ? this.mEditable.toString() : this.mEditable;
        return this.mExtractedText;
    }

    private boolean handleHorizontalMovement(boolean z2, boolean z3) {
        int selectionStart = Selection.getSelectionStart(this.mEditable);
        int selectionEnd = Selection.getSelectionEnd(this.mEditable);
        if (selectionStart < 0 || selectionEnd < 0) {
            return false;
        }
        int iMax = z2 ? Math.max(this.flutterTextUtils.getOffsetBefore(this.mEditable, selectionEnd), 0) : Math.min(this.flutterTextUtils.getOffsetAfter(this.mEditable, selectionEnd), this.mEditable.length());
        if (selectionStart != selectionEnd || z3) {
            setSelection(selectionStart, iMax);
            return true;
        }
        setSelection(iMax, iMax);
        return true;
    }

    private boolean handleVerticalMovement(boolean z2, boolean z3) {
        int selectionStart = Selection.getSelectionStart(this.mEditable);
        int selectionEnd = Selection.getSelectionEnd(this.mEditable);
        boolean z4 = false;
        if (selectionStart < 0 || selectionEnd < 0) {
            return false;
        }
        if (selectionStart == selectionEnd && !z3) {
            z4 = true;
        }
        beginBatchEdit();
        if (z4) {
            if (z2) {
                Selection.moveUp(this.mEditable, this.mLayout);
            } else {
                Selection.moveDown(this.mEditable, this.mLayout);
            }
            int selectionStart2 = Selection.getSelectionStart(this.mEditable);
            setSelection(selectionStart2, selectionStart2);
        } else {
            if (z2) {
                Selection.extendUp(this.mEditable, this.mLayout);
            } else {
                Selection.extendDown(this.mEditable, this.mLayout);
            }
            setSelection(Selection.getSelectionStart(this.mEditable), Selection.getSelectionEnd(this.mEditable));
        }
        endBatchEdit();
        return true;
    }

    private byte[] readStreamFully(InputStream inputStream, int i2) throws IOException {
        int i3;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[i2];
        while (true) {
            try {
                i3 = inputStream.read(bArr);
            } catch (IOException unused) {
                i3 = -1;
            }
            if (i3 == -1) {
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(bArr, 0, i3);
        }
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean beginBatchEdit() {
        this.mEditable.beginBatchEdit();
        this.batchEditNestDepth++;
        return super.beginBatchEdit();
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean clearMetaKeyStates(int i2) {
        return super.clearMetaKeyStates(i2);
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public void closeConnection() {
        super.closeConnection();
        this.mEditable.removeEditingStateListener(this);
        while (this.batchEditNestDepth > 0) {
            endBatchEdit();
            this.batchEditNestDepth--;
        }
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    @RequiresApi(25)
    @TargetApi(25)
    public boolean commitContent(InputContentInfo inputContentInfo, int i2, Bundle bundle) throws IOException {
        if (Build.VERSION.SDK_INT >= 25 && (i2 & 1) != 0) {
            try {
                inputContentInfo.requestPermission();
                if (inputContentInfo.getDescription().getMimeTypeCount() > 0) {
                    inputContentInfo.requestPermission();
                    Uri contentUri = inputContentInfo.getContentUri();
                    String mimeType = inputContentInfo.getDescription().getMimeType(0);
                    Context context = this.mFlutterView.getContext();
                    if (contentUri != null) {
                        try {
                            InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(contentUri);
                            if (inputStreamOpenInputStream != null) {
                                byte[] streamFully = readStreamFully(inputStreamOpenInputStream, 65536);
                                HashMap map = new HashMap();
                                map.put("mimeType", mimeType);
                                map.put("data", streamFully);
                                map.put(ShareConstants.MEDIA_URI, contentUri.toString());
                                this.textInputChannel.commitContent(this.mClient, map);
                                inputContentInfo.releasePermission();
                                return true;
                            }
                        } catch (FileNotFoundException unused) {
                            inputContentInfo.releasePermission();
                            return false;
                        }
                    }
                    inputContentInfo.releasePermission();
                }
            } catch (Exception unused2) {
            }
        }
        return false;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean commitText(CharSequence charSequence, int i2) {
        return super.commitText(charSequence, i2);
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean deleteSurroundingText(int i2, int i3) {
        if (this.mEditable.getSelectionStart() == -1) {
            return true;
        }
        return super.deleteSurroundingText(i2, i3);
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean deleteSurroundingTextInCodePoints(int i2, int i3) {
        return super.deleteSurroundingTextInCodePoints(i2, i3);
    }

    @Override // io.flutter.plugin.editing.ListenableEditingState.EditingStateWatcher
    public void didChangeEditingState(boolean z2, boolean z3, boolean z4) {
        this.mImm.updateSelection(this.mFlutterView, this.mEditable.getSelectionStart(), this.mEditable.getSelectionEnd(), this.mEditable.getComposingStart(), this.mEditable.getComposingEnd());
        ExtractedTextRequest extractedTextRequest = this.mExtractRequest;
        if (extractedTextRequest != null) {
            this.mImm.updateExtractedText(this.mFlutterView, extractedTextRequest.token, getExtractedText(extractedTextRequest));
        }
        if (this.mMonitorCursorUpdate) {
            this.mImm.updateCursorAnchorInfo(this.mFlutterView, getCursorAnchorInfo());
        }
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean endBatchEdit() {
        boolean zEndBatchEdit = super.endBatchEdit();
        this.batchEditNestDepth--;
        this.mEditable.endBatchEdit();
        return zEndBatchEdit;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean finishComposingText() {
        return super.finishComposingText();
    }

    @Override // android.view.inputmethod.BaseInputConnection
    public Editable getEditable() {
        return this.mEditable;
    }

    public boolean handleKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            if (keyEvent.getKeyCode() == 21) {
                return handleHorizontalMovement(true, keyEvent.isShiftPressed());
            }
            if (keyEvent.getKeyCode() == 22) {
                return handleHorizontalMovement(false, keyEvent.isShiftPressed());
            }
            if (keyEvent.getKeyCode() == 19) {
                return handleVerticalMovement(true, keyEvent.isShiftPressed());
            }
            if (keyEvent.getKeyCode() == 20) {
                return handleVerticalMovement(false, keyEvent.isShiftPressed());
            }
            if (keyEvent.getKeyCode() == 66 || keyEvent.getKeyCode() == 160) {
                EditorInfo editorInfo = this.mEditorInfo;
                if ((editorInfo.inputType & 131072) == 0) {
                    performEditorAction(editorInfo.imeOptions & 255);
                    return true;
                }
            }
            int selectionStart = Selection.getSelectionStart(this.mEditable);
            int selectionEnd = Selection.getSelectionEnd(this.mEditable);
            int unicodeChar = keyEvent.getUnicodeChar();
            if (selectionStart >= 0 && selectionEnd >= 0 && unicodeChar != 0) {
                int iMin = Math.min(selectionStart, selectionEnd);
                int iMax = Math.max(selectionStart, selectionEnd);
                beginBatchEdit();
                if (iMin != iMax) {
                    this.mEditable.delete(iMin, iMax);
                }
                this.mEditable.insert(iMin, (CharSequence) String.valueOf((char) unicodeChar));
                int i2 = iMin + 1;
                setSelection(i2, i2);
                endBatchEdit();
                return true;
            }
        }
        return false;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean performContextMenuAction(int i2) {
        beginBatchEdit();
        boolean zDoPerformContextMenuAction = doPerformContextMenuAction(i2);
        endBatchEdit();
        return zDoPerformContextMenuAction;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean performEditorAction(int i2) {
        if (i2 == 0) {
            this.textInputChannel.unspecifiedAction(this.mClient);
        } else if (i2 == 1) {
            this.textInputChannel.newline(this.mClient);
        } else if (i2 == 2) {
            this.textInputChannel.go(this.mClient);
        } else if (i2 == 3) {
            this.textInputChannel.search(this.mClient);
        } else if (i2 == 4) {
            this.textInputChannel.send(this.mClient);
        } else if (i2 == 5) {
            this.textInputChannel.next(this.mClient);
        } else if (i2 != 7) {
            this.textInputChannel.done(this.mClient);
        } else {
            this.textInputChannel.previous(this.mClient);
        }
        return true;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean performPrivateCommand(String str, Bundle bundle) {
        this.textInputChannel.performPrivateCommand(this.mClient, str, bundle);
        return true;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean requestCursorUpdates(int i2) {
        if ((i2 & 1) != 0) {
            this.mImm.updateCursorAnchorInfo(this.mFlutterView, getCursorAnchorInfo());
        }
        boolean z2 = (i2 & 2) != 0;
        if (z2 != this.mMonitorCursorUpdate) {
            StringBuilder sb = new StringBuilder();
            sb.append("The input method toggled cursor monitoring ");
            sb.append(z2 ? "on" : "off");
            Log.d(TAG, sb.toString());
        }
        this.mMonitorCursorUpdate = z2;
        return true;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean sendKeyEvent(KeyEvent keyEvent) {
        return this.keyboardDelegate.handleEvent(keyEvent);
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean setComposingRegion(int i2, int i3) {
        return super.setComposingRegion(i2, i3);
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean setComposingText(CharSequence charSequence, int i2) {
        beginBatchEdit();
        boolean zCommitText = charSequence.length() == 0 ? super.commitText(charSequence, i2) : super.setComposingText(charSequence, i2);
        endBatchEdit();
        return zCommitText;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean setSelection(int i2, int i3) {
        beginBatchEdit();
        boolean selection = super.setSelection(i2, i3);
        endBatchEdit();
        return selection;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public ExtractedText getExtractedText(ExtractedTextRequest extractedTextRequest, int i2) {
        boolean z2 = (i2 & 1) != 0;
        if (z2 == (this.mExtractRequest == null)) {
            StringBuilder sb = new StringBuilder();
            sb.append("The input method toggled text monitoring ");
            sb.append(z2 ? "on" : "off");
            Log.d(TAG, sb.toString());
        }
        this.mExtractRequest = z2 ? extractedTextRequest : null;
        return getExtractedText(extractedTextRequest);
    }

    public InputConnectionAdaptor(View view, int i2, TextInputChannel textInputChannel, KeyboardDelegate keyboardDelegate, ListenableEditingState listenableEditingState, EditorInfo editorInfo) {
        this(view, i2, textInputChannel, keyboardDelegate, listenableEditingState, editorInfo, new FlutterJNI());
    }
}
