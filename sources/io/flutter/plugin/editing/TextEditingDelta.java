package io.flutter.plugin.editing;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import io.flutter.Log;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class TextEditingDelta {
    private static final String TAG = "TextEditingDelta";
    private int deltaEnd;
    private int deltaStart;

    @NonNull
    private CharSequence deltaText;
    private int newComposingEnd;
    private int newComposingStart;
    private int newSelectionEnd;
    private int newSelectionStart;

    @NonNull
    private CharSequence oldText;

    public TextEditingDelta(@NonNull CharSequence charSequence, int i2, int i3, @NonNull CharSequence charSequence2, int i4, int i5, int i6, int i7) {
        this.newSelectionStart = i4;
        this.newSelectionEnd = i5;
        this.newComposingStart = i6;
        this.newComposingEnd = i7;
        setDeltas(charSequence, charSequence2.toString(), i2, i3);
    }

    private void setDeltas(@NonNull CharSequence charSequence, @NonNull CharSequence charSequence2, int i2, int i3) {
        this.oldText = charSequence;
        this.deltaText = charSequence2;
        this.deltaStart = i2;
        this.deltaEnd = i3;
    }

    @VisibleForTesting
    public int getDeltaEnd() {
        return this.deltaEnd;
    }

    @VisibleForTesting
    public int getDeltaStart() {
        return this.deltaStart;
    }

    @NonNull
    @VisibleForTesting
    public CharSequence getDeltaText() {
        return this.deltaText;
    }

    @VisibleForTesting
    public int getNewComposingEnd() {
        return this.newComposingEnd;
    }

    @VisibleForTesting
    public int getNewComposingStart() {
        return this.newComposingStart;
    }

    @VisibleForTesting
    public int getNewSelectionEnd() {
        return this.newSelectionEnd;
    }

    @VisibleForTesting
    public int getNewSelectionStart() {
        return this.newSelectionStart;
    }

    @NonNull
    @VisibleForTesting
    public CharSequence getOldText() {
        return this.oldText;
    }

    @NonNull
    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("oldText", this.oldText.toString());
            jSONObject.put("deltaText", this.deltaText.toString());
            jSONObject.put("deltaStart", this.deltaStart);
            jSONObject.put("deltaEnd", this.deltaEnd);
            jSONObject.put("selectionBase", this.newSelectionStart);
            jSONObject.put("selectionExtent", this.newSelectionEnd);
            jSONObject.put("composingBase", this.newComposingStart);
            jSONObject.put("composingExtent", this.newComposingEnd);
        } catch (JSONException e2) {
            Log.e(TAG, "unable to create JSONObject: " + e2);
        }
        return jSONObject;
    }

    public TextEditingDelta(@NonNull CharSequence charSequence, int i2, int i3, int i4, int i5) {
        this.newSelectionStart = i2;
        this.newSelectionEnd = i3;
        this.newComposingStart = i4;
        this.newComposingEnd = i5;
        setDeltas(charSequence, "", -1, -1);
    }
}
